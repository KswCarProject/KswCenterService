package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.UidTraffic;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.INetworkStatsService;
import android.net.NetworkStats;
import android.net.Uri;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.BatteryStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBatteryPropertiesRegistrar;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiBatteryStats;
import android.provider.Settings;
import android.provider.Telephony;
import android.telecom.ParcelableCallAnalytics;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.LogWriter;
import android.util.LongSparseArray;
import android.util.LongSparseLongArray;
import android.util.MutableInt;
import android.util.Pools;
import android.util.Printer;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.StatsLog;
import android.util.TimeUtils;
import android.util.Xml;
import com.android.ims.ImsConfig;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.EventLogTags;
import com.android.internal.os.KernelCpuUidTimeReader;
import com.android.internal.os.KernelWakelockStats;
import com.android.internal.os.RpmStats;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class BatteryStatsImpl extends BatteryStats {
    static final int BATTERY_DELTA_LEVEL_FLAG = 1;
    public static final int BATTERY_PLUGGED_NONE = 0;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<BatteryStatsImpl> CREATOR = new Parcelable.Creator<BatteryStatsImpl>() {
        public BatteryStatsImpl createFromParcel(Parcel in) {
            return new BatteryStatsImpl(in);
        }

        public BatteryStatsImpl[] newArray(int size) {
            return new BatteryStatsImpl[size];
        }
    };
    private static final boolean DEBUG = false;
    public static final boolean DEBUG_ENERGY = false;
    private static final boolean DEBUG_ENERGY_CPU = false;
    private static final boolean DEBUG_HISTORY = false;
    private static final boolean DEBUG_MEMORY = false;
    static final long DELAY_UPDATE_WAKELOCKS = 5000;
    static final int DELTA_BATTERY_CHARGE_FLAG = 16777216;
    static final int DELTA_BATTERY_LEVEL_FLAG = 524288;
    static final int DELTA_EVENT_FLAG = 8388608;
    static final int DELTA_STATE2_FLAG = 2097152;
    static final int DELTA_STATE_FLAG = 1048576;
    static final int DELTA_STATE_MASK = -33554432;
    static final int DELTA_TIME_ABS = 524285;
    static final int DELTA_TIME_INT = 524286;
    static final int DELTA_TIME_LONG = 524287;
    static final int DELTA_TIME_MASK = 524287;
    static final int DELTA_WAKELOCK_FLAG = 4194304;
    private static final int MAGIC = -1166707595;
    static final int MAX_DAILY_ITEMS = 10;
    static final int MAX_LEVEL_STEPS = 200;
    /* access modifiers changed from: private */
    public static final int MAX_WAKELOCKS_PER_UID;
    private static final double MILLISECONDS_IN_HOUR = 3600000.0d;
    private static final long MILLISECONDS_IN_YEAR = 31536000000L;
    static final int MSG_REPORT_CHARGING = 3;
    static final int MSG_REPORT_CPU_UPDATE_NEEDED = 1;
    static final int MSG_REPORT_POWER_CHANGE = 2;
    static final int MSG_REPORT_RESET_STATS = 4;
    private static final int NUM_BT_TX_LEVELS = 1;
    private static final int NUM_WIFI_TX_LEVELS = 1;
    private static final long RPM_STATS_UPDATE_FREQ_MS = 1000;
    static final int STATE_BATTERY_HEALTH_MASK = 7;
    static final int STATE_BATTERY_HEALTH_SHIFT = 26;
    static final int STATE_BATTERY_MASK = -16777216;
    static final int STATE_BATTERY_PLUG_MASK = 3;
    static final int STATE_BATTERY_PLUG_SHIFT = 24;
    static final int STATE_BATTERY_STATUS_MASK = 7;
    static final int STATE_BATTERY_STATUS_SHIFT = 29;
    private static final String TAG = "BatteryStatsImpl";
    private static final int USB_DATA_CONNECTED = 2;
    private static final int USB_DATA_DISCONNECTED = 1;
    private static final int USB_DATA_UNKNOWN = 0;
    private static final boolean USE_OLD_HISTORY = false;
    static final int VERSION = 186;
    @VisibleForTesting
    public static final int WAKE_LOCK_WEIGHT = 50;
    final BatteryStats.HistoryEventTracker mActiveEvents;
    int mActiveHistoryStates;
    int mActiveHistoryStates2;
    int mAudioOnNesting;
    StopwatchTimer mAudioOnTimer;
    final ArrayList<StopwatchTimer> mAudioTurnedOnTimers;
    final BatteryStatsHistory mBatteryStatsHistory;
    ControllerActivityCounterImpl mBluetoothActivity;
    int mBluetoothScanNesting;
    final ArrayList<StopwatchTimer> mBluetoothScanOnTimers;
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    protected StopwatchTimer mBluetoothScanTimer;
    /* access modifiers changed from: private */
    public BatteryCallback mCallback;
    int mCameraOnNesting;
    StopwatchTimer mCameraOnTimer;
    final ArrayList<StopwatchTimer> mCameraTurnedOnTimers;
    int mChangedStates;
    int mChangedStates2;
    final BatteryStats.LevelStepTracker mChargeStepTracker;
    boolean mCharging;
    public final AtomicFile mCheckinFile;
    protected Clocks mClocks;
    @GuardedBy({"this"})
    final Constants mConstants;
    private long[] mCpuFreqs;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public long mCpuTimeReadsTrackingStartTime;
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader mCpuUidActiveTimeReader;
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader mCpuUidClusterTimeReader;
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader mCpuUidFreqTimeReader;
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader mCpuUidUserSysTimeReader;
    final BatteryStats.HistoryStepDetails mCurHistoryStepDetails;
    long mCurStepCpuSystemTime;
    long mCurStepCpuUserTime;
    int mCurStepMode;
    long mCurStepStatIOWaitTime;
    long mCurStepStatIdleTime;
    long mCurStepStatIrqTime;
    long mCurStepStatSoftIrqTime;
    long mCurStepStatSystemTime;
    long mCurStepStatUserTime;
    int mCurrentBatteryLevel;
    final BatteryStats.LevelStepTracker mDailyChargeStepTracker;
    final BatteryStats.LevelStepTracker mDailyDischargeStepTracker;
    public final AtomicFile mDailyFile;
    final ArrayList<BatteryStats.DailyItem> mDailyItems;
    ArrayList<BatteryStats.PackageChange> mDailyPackageChanges;
    long mDailyStartTime;
    private final Runnable mDeferSetCharging;
    int mDeviceIdleMode;
    StopwatchTimer mDeviceIdleModeFullTimer;
    StopwatchTimer mDeviceIdleModeLightTimer;
    boolean mDeviceIdling;
    StopwatchTimer mDeviceIdlingTimer;
    boolean mDeviceLightIdling;
    StopwatchTimer mDeviceLightIdlingTimer;
    int mDischargeAmountScreenDoze;
    int mDischargeAmountScreenDozeSinceCharge;
    int mDischargeAmountScreenOff;
    int mDischargeAmountScreenOffSinceCharge;
    int mDischargeAmountScreenOn;
    int mDischargeAmountScreenOnSinceCharge;
    private LongSamplingCounter mDischargeCounter;
    int mDischargeCurrentLevel;
    private LongSamplingCounter mDischargeDeepDozeCounter;
    private LongSamplingCounter mDischargeLightDozeCounter;
    int mDischargePlugLevel;
    private LongSamplingCounter mDischargeScreenDozeCounter;
    int mDischargeScreenDozeUnplugLevel;
    private LongSamplingCounter mDischargeScreenOffCounter;
    int mDischargeScreenOffUnplugLevel;
    int mDischargeScreenOnUnplugLevel;
    int mDischargeStartLevel;
    final BatteryStats.LevelStepTracker mDischargeStepTracker;
    int mDischargeUnplugLevel;
    boolean mDistributeWakelockCpu;
    final ArrayList<StopwatchTimer> mDrawTimers;
    String mEndPlatformVersion;
    private int mEstimatedBatteryCapacity;
    /* access modifiers changed from: private */
    public ExternalStatsSync mExternalSync;
    int mFlashlightOnNesting;
    StopwatchTimer mFlashlightOnTimer;
    final ArrayList<StopwatchTimer> mFlashlightTurnedOnTimers;
    @UnsupportedAppUsage
    final ArrayList<StopwatchTimer> mFullTimers;
    final ArrayList<StopwatchTimer> mFullWifiLockTimers;
    boolean mGlobalWifiRunning;
    StopwatchTimer mGlobalWifiRunningTimer;
    int mGpsNesting;
    int mGpsSignalQualityBin;
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    protected final StopwatchTimer[] mGpsSignalQualityTimer;
    public Handler mHandler;
    boolean mHasBluetoothReporting;
    boolean mHasModemReporting;
    boolean mHasWifiReporting;
    protected boolean mHaveBatteryLevel;
    int mHighDischargeAmountSinceCharge;
    BatteryStats.HistoryItem mHistory;
    final BatteryStats.HistoryItem mHistoryAddTmp;
    long mHistoryBaseTime;
    final Parcel mHistoryBuffer;
    int mHistoryBufferLastPos;
    BatteryStats.HistoryItem mHistoryCache;
    final BatteryStats.HistoryItem mHistoryCur;
    BatteryStats.HistoryItem mHistoryEnd;
    private BatteryStats.HistoryItem mHistoryIterator;
    BatteryStats.HistoryItem mHistoryLastEnd;
    final BatteryStats.HistoryItem mHistoryLastLastWritten;
    final BatteryStats.HistoryItem mHistoryLastWritten;
    final BatteryStats.HistoryItem mHistoryReadTmp;
    final HashMap<BatteryStats.HistoryTag, Integer> mHistoryTagPool;
    int mInitStepMode;
    private String mInitialAcquireWakeName;
    private int mInitialAcquireWakeUid;
    boolean mInteractive;
    StopwatchTimer mInteractiveTimer;
    boolean mIsCellularTxPowerHigh;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public boolean mIsPerProcessStateCpuDataStale;
    final SparseIntArray mIsolatedUids;
    private boolean mIteratingHistory;
    @VisibleForTesting
    protected KernelCpuSpeedReader[] mKernelCpuSpeedReaders;
    private final KernelMemoryBandwidthStats mKernelMemoryBandwidthStats;
    private final LongSparseArray<SamplingTimer> mKernelMemoryStats;
    @VisibleForTesting
    protected KernelSingleUidTimeReader mKernelSingleUidTimeReader;
    private final KernelWakelockReader mKernelWakelockReader;
    private final HashMap<String, SamplingTimer> mKernelWakelockStats;
    private final BluetoothActivityInfoCache mLastBluetoothActivityInfo;
    int mLastChargeStepLevel;
    int mLastChargingStateLevel;
    int mLastDischargeStepLevel;
    long mLastHistoryElapsedRealtime;
    BatteryStats.HistoryStepDetails mLastHistoryStepDetails;
    byte mLastHistoryStepLevel;
    long mLastIdleTimeStart;
    private ModemActivityInfo mLastModemActivityInfo;
    @GuardedBy({"mModemNetworkLock"})
    private NetworkStats mLastModemNetworkStats;
    @VisibleForTesting
    protected ArrayList<StopwatchTimer> mLastPartialTimers;
    private long mLastRpmStatsUpdateTimeMs;
    long mLastStepCpuSystemTime;
    long mLastStepCpuUserTime;
    long mLastStepStatIOWaitTime;
    long mLastStepStatIdleTime;
    long mLastStepStatIrqTime;
    long mLastStepStatSoftIrqTime;
    long mLastStepStatSystemTime;
    long mLastStepStatUserTime;
    String mLastWakeupReason;
    long mLastWakeupUptimeMs;
    @GuardedBy({"mWifiNetworkLock"})
    private NetworkStats mLastWifiNetworkStats;
    long mLastWriteTime;
    long mLongestFullIdleTime;
    long mLongestLightIdleTime;
    int mLowDischargeAmountSinceCharge;
    int mMaxChargeStepLevel;
    private int mMaxLearnedBatteryCapacity;
    int mMinDischargeStepLevel;
    private int mMinLearnedBatteryCapacity;
    LongSamplingCounter mMobileRadioActiveAdjustedTime;
    StopwatchTimer mMobileRadioActivePerAppTimer;
    long mMobileRadioActiveStartTime;
    StopwatchTimer mMobileRadioActiveTimer;
    LongSamplingCounter mMobileRadioActiveUnknownCount;
    LongSamplingCounter mMobileRadioActiveUnknownTime;
    int mMobileRadioPowerState;
    int mModStepMode;
    ControllerActivityCounterImpl mModemActivity;
    @GuardedBy({"mModemNetworkLock"})
    private String[] mModemIfaces;
    private final Object mModemNetworkLock;
    final LongSamplingCounter[] mNetworkByteActivityCounters;
    final LongSamplingCounter[] mNetworkPacketActivityCounters;
    private final Pools.Pool<NetworkStats> mNetworkStatsPool;
    int mNextHistoryTagIdx;
    long mNextMaxDailyDeadline;
    long mNextMinDailyDeadline;
    boolean mNoAutoReset;
    @GuardedBy({"this"})
    private int mNumAllUidCpuTimeReads;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public long mNumBatchedSingleUidCpuTimeReads;
    private int mNumConnectivityChange;
    int mNumHistoryItems;
    int mNumHistoryTagChars;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public long mNumSingleUidCpuTimeReads;
    @GuardedBy({"this"})
    private int mNumUidsRemoved;
    boolean mOnBattery;
    @VisibleForTesting
    protected boolean mOnBatteryInternal;
    protected final TimeBase mOnBatteryScreenOffTimeBase;
    protected final TimeBase mOnBatteryTimeBase;
    @VisibleForTesting
    @UnsupportedAppUsage
    protected ArrayList<StopwatchTimer> mPartialTimers;
    @GuardedBy({"this"})
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    protected Queue<UidToRemove> mPendingRemovedUids;
    @GuardedBy({"this"})
    @VisibleForTesting
    protected final SparseIntArray mPendingUids;
    @GuardedBy({"this"})
    public boolean mPerProcStateCpuTimesAvailable;
    int mPhoneDataConnectionType;
    final StopwatchTimer[] mPhoneDataConnectionsTimer;
    boolean mPhoneOn;
    StopwatchTimer mPhoneOnTimer;
    private int mPhoneServiceState;
    private int mPhoneServiceStateRaw;
    StopwatchTimer mPhoneSignalScanningTimer;
    int mPhoneSignalStrengthBin;
    int mPhoneSignalStrengthBinRaw;
    final StopwatchTimer[] mPhoneSignalStrengthsTimer;
    private int mPhoneSimStateRaw;
    private final PlatformIdleStateCallback mPlatformIdleStateCallback;
    @VisibleForTesting
    protected PowerProfile mPowerProfile;
    boolean mPowerSaveModeEnabled;
    StopwatchTimer mPowerSaveModeEnabledTimer;
    boolean mPretendScreenOff;
    public final RailEnergyDataCallback mRailEnergyDataCallback;
    int mReadHistoryChars;
    final BatteryStats.HistoryStepDetails mReadHistoryStepDetails;
    String[] mReadHistoryStrings;
    int[] mReadHistoryUids;
    private boolean mReadOverflow;
    long mRealtime;
    long mRealtimeStart;
    public boolean mRecordAllHistory;
    protected boolean mRecordingHistory;
    private final HashMap<String, SamplingTimer> mRpmStats;
    int mScreenBrightnessBin;
    final StopwatchTimer[] mScreenBrightnessTimer;
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    protected StopwatchTimer mScreenDozeTimer;
    private final HashMap<String, SamplingTimer> mScreenOffRpmStats;
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    protected StopwatchTimer mScreenOnTimer;
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    protected int mScreenState;
    int mSensorNesting;
    final SparseArray<ArrayList<StopwatchTimer>> mSensorTimers;
    boolean mShuttingDown;
    long mStartClockTime;
    int mStartCount;
    String mStartPlatformVersion;
    private final AtomicFile mStatsFile;
    long mTempTotalCpuSystemTimeUs;
    long mTempTotalCpuUserTimeUs;
    final BatteryStats.HistoryStepDetails mTmpHistoryStepDetails;
    private final RailStats mTmpRailStats;
    private final RpmStats mTmpRpmStats;
    private final KernelWakelockStats mTmpWakelockStats;
    long mTrackRunningHistoryElapsedRealtime;
    long mTrackRunningHistoryUptime;
    final SparseArray<Uid> mUidStats;
    long mUptime;
    long mUptimeStart;
    int mUsbDataState;
    @VisibleForTesting
    protected UserInfoProvider mUserInfoProvider;
    int mVideoOnNesting;
    StopwatchTimer mVideoOnTimer;
    final ArrayList<StopwatchTimer> mVideoTurnedOnTimers;
    long[][] mWakeLockAllocationsUs;
    boolean mWakeLockImportant;
    int mWakeLockNesting;
    private final HashMap<String, SamplingTimer> mWakeupReasonStats;
    StopwatchTimer mWifiActiveTimer;
    ControllerActivityCounterImpl mWifiActivity;
    final SparseArray<ArrayList<StopwatchTimer>> mWifiBatchedScanTimers;
    int mWifiFullLockNesting;
    @GuardedBy({"mWifiNetworkLock"})
    private String[] mWifiIfaces;
    int mWifiMulticastNesting;
    final ArrayList<StopwatchTimer> mWifiMulticastTimers;
    StopwatchTimer mWifiMulticastWakelockTimer;
    private final Object mWifiNetworkLock;
    boolean mWifiOn;
    StopwatchTimer mWifiOnTimer;
    int mWifiRadioPowerState;
    final ArrayList<StopwatchTimer> mWifiRunningTimers;
    int mWifiScanNesting;
    final ArrayList<StopwatchTimer> mWifiScanTimers;
    int mWifiSignalStrengthBin;
    final StopwatchTimer[] mWifiSignalStrengthsTimer;
    int mWifiState;
    final StopwatchTimer[] mWifiStateTimer;
    int mWifiSupplState;
    final StopwatchTimer[] mWifiSupplStateTimer;
    @UnsupportedAppUsage
    final ArrayList<StopwatchTimer> mWindowTimers;
    final ReentrantLock mWriteLock;

    public interface BatteryCallback {
        void batteryNeedsCpuUpdate();

        void batteryPowerChanged(boolean z);

        void batterySendBroadcast(Intent intent);

        void batteryStatsReset();
    }

    public interface Clocks {
        long elapsedRealtime();

        long uptimeMillis();
    }

    public interface ExternalStatsSync {
        public static final int UPDATE_ALL = 31;
        public static final int UPDATE_BT = 8;
        public static final int UPDATE_CPU = 1;
        public static final int UPDATE_RADIO = 4;
        public static final int UPDATE_RPM = 16;
        public static final int UPDATE_WIFI = 2;

        void cancelCpuSyncDueToWakelockChange();

        Future<?> scheduleCopyFromAllUidsCpuTimes(boolean z, boolean z2);

        Future<?> scheduleCpuSyncDueToRemovedUid(int i);

        Future<?> scheduleCpuSyncDueToScreenStateChange(boolean z, boolean z2);

        Future<?> scheduleCpuSyncDueToSettingChange();

        Future<?> scheduleCpuSyncDueToWakelockChange(long j);

        Future<?> scheduleReadProcStateCpuTimes(boolean z, boolean z2, long j);

        Future<?> scheduleSync(String str, int i);

        Future<?> scheduleSyncDueToBatteryLevelChange(long j);
    }

    public interface PlatformIdleStateCallback {
        void fillLowPowerStats(RpmStats rpmStats);

        String getPlatformLowPowerStats();

        String getSubsystemLowPowerStats();
    }

    public interface RailEnergyDataCallback {
        void fillRailDataStats(RailStats railStats);
    }

    public interface TimeBaseObs {
        void detach();

        void onTimeStarted(long j, long j2, long j3);

        void onTimeStopped(long j, long j2, long j3);

        boolean reset(boolean z);
    }

    static /* synthetic */ int access$008(BatteryStatsImpl x0) {
        int i = x0.mNumUidsRemoved;
        x0.mNumUidsRemoved = i + 1;
        return i;
    }

    static /* synthetic */ long access$1708(BatteryStatsImpl x0) {
        long j = x0.mNumSingleUidCpuTimeReads;
        x0.mNumSingleUidCpuTimeReads = 1 + j;
        return j;
    }

    static /* synthetic */ long access$1808(BatteryStatsImpl x0) {
        long j = x0.mNumBatchedSingleUidCpuTimeReads;
        x0.mNumBatchedSingleUidCpuTimeReads = 1 + j;
        return j;
    }

    static {
        if (ActivityManager.isLowRamDeviceStatic()) {
            MAX_WAKELOCKS_PER_UID = 40;
        } else {
            MAX_WAKELOCKS_PER_UID = 200;
        }
    }

    public LongSparseArray<SamplingTimer> getKernelMemoryStats() {
        return this.mKernelMemoryStats;
    }

    @VisibleForTesting
    public final class UidToRemove {
        int endUid;
        int startUid;
        long timeAddedInQueue;

        public UidToRemove(BatteryStatsImpl this$02, int uid, long timestamp) {
            this(uid, uid, timestamp);
        }

        public UidToRemove(int startUid2, int endUid2, long timestamp) {
            this.startUid = startUid2;
            this.endUid = endUid2;
            this.timeAddedInQueue = timestamp;
        }

        /* access modifiers changed from: package-private */
        public void remove() {
            if (this.startUid == this.endUid) {
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.removeUid(this.startUid);
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.removeUid(this.startUid);
                if (BatteryStatsImpl.this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                    BatteryStatsImpl.this.mCpuUidActiveTimeReader.removeUid(this.startUid);
                    BatteryStatsImpl.this.mCpuUidClusterTimeReader.removeUid(this.startUid);
                }
                if (BatteryStatsImpl.this.mKernelSingleUidTimeReader != null) {
                    BatteryStatsImpl.this.mKernelSingleUidTimeReader.removeUid(this.startUid);
                }
                BatteryStatsImpl.access$008(BatteryStatsImpl.this);
            } else if (this.startUid < this.endUid) {
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.removeUidsInRange(this.startUid, this.endUid);
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.removeUidsInRange(this.startUid, this.endUid);
                if (BatteryStatsImpl.this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                    BatteryStatsImpl.this.mCpuUidActiveTimeReader.removeUidsInRange(this.startUid, this.endUid);
                    BatteryStatsImpl.this.mCpuUidClusterTimeReader.removeUidsInRange(this.startUid, this.endUid);
                }
                if (BatteryStatsImpl.this.mKernelSingleUidTimeReader != null) {
                    BatteryStatsImpl.this.mKernelSingleUidTimeReader.removeUidsInRange(this.startUid, this.endUid);
                }
                BatteryStatsImpl.access$008(BatteryStatsImpl.this);
            } else {
                Slog.w(BatteryStatsImpl.TAG, "End UID " + this.endUid + " is smaller than start UID " + this.startUid);
            }
        }
    }

    public static abstract class UserInfoProvider {
        private int[] userIds;

        /* access modifiers changed from: protected */
        public abstract int[] getUserIds();

        @VisibleForTesting
        public final void refreshUserIds() {
            this.userIds = getUserIds();
        }

        @VisibleForTesting
        public boolean exists(int userId) {
            if (this.userIds != null) {
                return ArrayUtils.contains(this.userIds, userId);
            }
            return true;
        }
    }

    final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper, (Handler.Callback) null, true);
        }

        public void handleMessage(Message msg) {
            String action;
            BatteryCallback cb = BatteryStatsImpl.this.mCallback;
            switch (msg.what) {
                case 1:
                    if (cb != null) {
                        cb.batteryNeedsCpuUpdate();
                        return;
                    }
                    return;
                case 2:
                    if (cb != null) {
                        cb.batteryPowerChanged(msg.arg1 != 0);
                        return;
                    }
                    return;
                case 3:
                    if (cb != null) {
                        synchronized (BatteryStatsImpl.this) {
                            if (BatteryStatsImpl.this.mCharging) {
                                action = BatteryManager.ACTION_CHARGING;
                            } else {
                                action = BatteryManager.ACTION_DISCHARGING;
                            }
                        }
                        Intent intent = new Intent(action);
                        intent.addFlags(67108864);
                        cb.batterySendBroadcast(intent);
                        return;
                    }
                    return;
                case 4:
                    if (cb != null) {
                        cb.batteryStatsReset();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void postBatteryNeedsCpuUpdateMsg() {
        this.mHandler.sendEmptyMessage(1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0032, code lost:
        r1 = r0.size() - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0038, code lost:
        if (r1 < 0) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003a, code lost:
        r2 = r0.keyAt(r1);
        r3 = r0.valueAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0042, code lost:
        monitor-enter(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r4 = getAvailableUidStatsLocked(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        if (r4 != null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0049, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004d, code lost:
        if (r4.mChildUids != null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004f, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0051, code lost:
        r5 = r4.mChildUids.toArray();
        r6 = r5.length - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005a, code lost:
        if (r6 < 0) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005c, code lost:
        r5[r6] = r4.mChildUids.get(r6);
        r6 = r6 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0067, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0068, code lost:
        r6 = r10.mKernelSingleUidTimeReader.readDeltaMs(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006e, code lost:
        if (r5 == null) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0070, code lost:
        r7 = r5.length - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0073, code lost:
        if (r7 < 0) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0075, code lost:
        r6 = addCpuTimes(r6, r10.mKernelSingleUidTimeReader.readDeltaMs(r5[r7]));
        r7 = r7 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0084, code lost:
        if (r11 == false) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0086, code lost:
        if (r6 == null) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0088, code lost:
        monitor-enter(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        com.android.internal.os.BatteryStatsImpl.Uid.access$200(r4, r3, r6, r11);
        com.android.internal.os.BatteryStatsImpl.Uid.access$300(r4, r3, r6, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x008f, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0094, code lost:
        r1 = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x009a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateProcStateCpuTimes(boolean r11, boolean r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.android.internal.os.BatteryStatsImpl$Constants r0 = r10.mConstants     // Catch:{ all -> 0x009b }
            boolean r0 = r0.TRACK_CPU_TIMES_BY_PROC_STATE     // Catch:{ all -> 0x009b }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r10)     // Catch:{ all -> 0x009b }
            return
        L_0x0009:
            boolean r0 = r10.initKernelSingleUidTimeReaderLocked()     // Catch:{ all -> 0x009b }
            if (r0 != 0) goto L_0x0011
            monitor-exit(r10)     // Catch:{ all -> 0x009b }
            return
        L_0x0011:
            boolean r0 = r10.mIsPerProcessStateCpuDataStale     // Catch:{ all -> 0x009b }
            if (r0 == 0) goto L_0x001c
            android.util.SparseIntArray r0 = r10.mPendingUids     // Catch:{ all -> 0x009b }
            r0.clear()     // Catch:{ all -> 0x009b }
            monitor-exit(r10)     // Catch:{ all -> 0x009b }
            return
        L_0x001c:
            android.util.SparseIntArray r0 = r10.mPendingUids     // Catch:{ all -> 0x009b }
            int r0 = r0.size()     // Catch:{ all -> 0x009b }
            if (r0 != 0) goto L_0x0026
            monitor-exit(r10)     // Catch:{ all -> 0x009b }
            return
        L_0x0026:
            android.util.SparseIntArray r0 = r10.mPendingUids     // Catch:{ all -> 0x009b }
            android.util.SparseIntArray r0 = r0.clone()     // Catch:{ all -> 0x009b }
            android.util.SparseIntArray r1 = r10.mPendingUids     // Catch:{ all -> 0x009b }
            r1.clear()     // Catch:{ all -> 0x009b }
            monitor-exit(r10)     // Catch:{ all -> 0x009b }
            int r1 = r0.size()
            int r1 = r1 + -1
        L_0x0038:
            if (r1 < 0) goto L_0x009a
            int r2 = r0.keyAt(r1)
            int r3 = r0.valueAt(r1)
            monitor-enter(r10)
            com.android.internal.os.BatteryStatsImpl$Uid r4 = r10.getAvailableUidStatsLocked(r2)     // Catch:{ all -> 0x0097 }
            if (r4 != 0) goto L_0x004b
            monitor-exit(r10)     // Catch:{ all -> 0x0097 }
            goto L_0x0094
        L_0x004b:
            android.util.IntArray r5 = r4.mChildUids     // Catch:{ all -> 0x0097 }
            if (r5 != 0) goto L_0x0051
            r5 = 0
            goto L_0x0067
        L_0x0051:
            android.util.IntArray r5 = r4.mChildUids     // Catch:{ all -> 0x0097 }
            int[] r5 = r5.toArray()     // Catch:{ all -> 0x0097 }
            int r6 = r5.length     // Catch:{ all -> 0x0097 }
            int r6 = r6 + -1
        L_0x005a:
            if (r6 < 0) goto L_0x0067
            android.util.IntArray r7 = r4.mChildUids     // Catch:{ all -> 0x0097 }
            int r7 = r7.get(r6)     // Catch:{ all -> 0x0097 }
            r5[r6] = r7     // Catch:{ all -> 0x0097 }
            int r6 = r6 + -1
            goto L_0x005a
        L_0x0067:
            monitor-exit(r10)     // Catch:{ all -> 0x0097 }
            com.android.internal.os.KernelSingleUidTimeReader r6 = r10.mKernelSingleUidTimeReader
            long[] r6 = r6.readDeltaMs(r2)
            if (r5 == 0) goto L_0x0084
            int r7 = r5.length
            int r7 = r7 + -1
        L_0x0073:
            if (r7 < 0) goto L_0x0084
            com.android.internal.os.KernelSingleUidTimeReader r8 = r10.mKernelSingleUidTimeReader
            r9 = r5[r7]
            long[] r8 = r8.readDeltaMs(r9)
            long[] r6 = r10.addCpuTimes(r6, r8)
            int r7 = r7 + -1
            goto L_0x0073
        L_0x0084:
            if (r11 == 0) goto L_0x0094
            if (r6 == 0) goto L_0x0094
            monitor-enter(r10)
            r4.addProcStateTimesMs(r3, r6, r11)     // Catch:{ all -> 0x0091 }
            r4.addProcStateScreenOffTimesMs(r3, r6, r12)     // Catch:{ all -> 0x0091 }
            monitor-exit(r10)     // Catch:{ all -> 0x0091 }
            goto L_0x0094
        L_0x0091:
            r7 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0091 }
            throw r7
        L_0x0094:
            int r1 = r1 + -1
            goto L_0x0038
        L_0x0097:
            r4 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0097 }
            throw r4
        L_0x009a:
            return
        L_0x009b:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x009b }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.updateProcStateCpuTimes(boolean, boolean):void");
    }

    public void clearPendingRemovedUids() {
        long cutOffTime = this.mClocks.elapsedRealtime() - this.mConstants.UID_REMOVE_DELAY_MS;
        while (!this.mPendingRemovedUids.isEmpty() && this.mPendingRemovedUids.peek().timeAddedInQueue < cutOffTime) {
            this.mPendingRemovedUids.poll().remove();
        }
    }

    public void copyFromAllUidsCpuTimes() {
        synchronized (this) {
            copyFromAllUidsCpuTimes(this.mOnBatteryTimeBase.isRunning(), this.mOnBatteryScreenOffTimeBase.isRunning());
        }
    }

    public void copyFromAllUidsCpuTimes(boolean onBattery, boolean onBatteryScreenOff) {
        int procState;
        synchronized (this) {
            if (this.mConstants.TRACK_CPU_TIMES_BY_PROC_STATE) {
                if (initKernelSingleUidTimeReaderLocked()) {
                    SparseArray<long[]> allUidCpuFreqTimesMs = this.mCpuUidFreqTimeReader.getAllUidCpuFreqTimeMs();
                    if (this.mIsPerProcessStateCpuDataStale) {
                        this.mKernelSingleUidTimeReader.setAllUidsCpuTimesMs(allUidCpuFreqTimesMs);
                        this.mIsPerProcessStateCpuDataStale = false;
                        this.mPendingUids.clear();
                        return;
                    }
                    for (int i = allUidCpuFreqTimesMs.size() - 1; i >= 0; i--) {
                        int uid = allUidCpuFreqTimesMs.keyAt(i);
                        Uid u = getAvailableUidStatsLocked(mapUid(uid));
                        if (u != null) {
                            long[] cpuTimesMs = allUidCpuFreqTimesMs.valueAt(i);
                            if (cpuTimesMs != null) {
                                long[] deltaTimesMs = this.mKernelSingleUidTimeReader.computeDelta(uid, (long[]) cpuTimesMs.clone());
                                if (onBattery && deltaTimesMs != null) {
                                    int idx = this.mPendingUids.indexOfKey(uid);
                                    if (idx >= 0) {
                                        procState = this.mPendingUids.valueAt(idx);
                                        this.mPendingUids.removeAt(idx);
                                    } else {
                                        procState = u.mProcessState;
                                    }
                                    if (procState >= 0 && procState < 7) {
                                        u.addProcStateTimesMs(procState, deltaTimesMs, onBattery);
                                        u.addProcStateScreenOffTimesMs(procState, deltaTimesMs, onBatteryScreenOff);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @VisibleForTesting
    public long[] addCpuTimes(long[] timesA, long[] timesB) {
        if (timesA != null && timesB != null) {
            for (int i = timesA.length - 1; i >= 0; i--) {
                timesA[i] = timesA[i] + timesB[i];
            }
            return timesA;
        } else if (timesA != null) {
            return timesA;
        } else {
            if (timesB == null) {
                return null;
            }
            return timesB;
        }
    }

    @GuardedBy({"this"})
    private boolean initKernelSingleUidTimeReaderLocked() {
        boolean z = false;
        if (this.mKernelSingleUidTimeReader == null) {
            if (this.mPowerProfile == null) {
                return false;
            }
            if (this.mCpuFreqs == null) {
                this.mCpuFreqs = this.mCpuUidFreqTimeReader.readFreqs(this.mPowerProfile);
            }
            if (this.mCpuFreqs != null) {
                this.mKernelSingleUidTimeReader = new KernelSingleUidTimeReader(this.mCpuFreqs.length);
            } else {
                this.mPerProcStateCpuTimesAvailable = this.mCpuUidFreqTimeReader.allUidTimesAvailable();
                return false;
            }
        }
        if (this.mCpuUidFreqTimeReader.allUidTimesAvailable() && this.mKernelSingleUidTimeReader.singleUidCpuTimesAvailable()) {
            z = true;
        }
        this.mPerProcStateCpuTimesAvailable = z;
        return true;
    }

    public static class SystemClocks implements Clocks {
        public long elapsedRealtime() {
            return SystemClock.elapsedRealtime();
        }

        public long uptimeMillis() {
            return SystemClock.uptimeMillis();
        }
    }

    public Map<String, ? extends Timer> getRpmStats() {
        return this.mRpmStats;
    }

    public Map<String, ? extends Timer> getScreenOffRpmStats() {
        return this.mScreenOffRpmStats;
    }

    @UnsupportedAppUsage
    public Map<String, ? extends Timer> getKernelWakelockStats() {
        return this.mKernelWakelockStats;
    }

    public Map<String, ? extends Timer> getWakeupReasonStats() {
        return this.mWakeupReasonStats;
    }

    public long getUahDischarge(int which) {
        return this.mDischargeCounter.getCountLocked(which);
    }

    public long getUahDischargeScreenOff(int which) {
        return this.mDischargeScreenOffCounter.getCountLocked(which);
    }

    public long getUahDischargeScreenDoze(int which) {
        return this.mDischargeScreenDozeCounter.getCountLocked(which);
    }

    public long getUahDischargeLightDoze(int which) {
        return this.mDischargeLightDozeCounter.getCountLocked(which);
    }

    public long getUahDischargeDeepDoze(int which) {
        return this.mDischargeDeepDozeCounter.getCountLocked(which);
    }

    public int getEstimatedBatteryCapacity() {
        return this.mEstimatedBatteryCapacity;
    }

    public int getMinLearnedBatteryCapacity() {
        return this.mMinLearnedBatteryCapacity;
    }

    public int getMaxLearnedBatteryCapacity() {
        return this.mMaxLearnedBatteryCapacity;
    }

    public BatteryStatsImpl() {
        this((Clocks) new SystemClocks());
    }

    public BatteryStatsImpl(Clocks clocks) {
        this.mKernelWakelockReader = new KernelWakelockReader();
        this.mTmpWakelockStats = new KernelWakelockStats();
        this.mCpuUidUserSysTimeReader = new KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(true);
        this.mCpuUidFreqTimeReader = new KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(true);
        this.mCpuUidActiveTimeReader = new KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(true);
        this.mCpuUidClusterTimeReader = new KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(true);
        this.mKernelMemoryBandwidthStats = new KernelMemoryBandwidthStats();
        this.mKernelMemoryStats = new LongSparseArray<>();
        this.mPerProcStateCpuTimesAvailable = true;
        this.mPendingUids = new SparseIntArray();
        this.mCpuTimeReadsTrackingStartTime = SystemClock.uptimeMillis();
        this.mTmpRpmStats = new RpmStats();
        this.mLastRpmStatsUpdateTimeMs = -1000;
        this.mTmpRailStats = new RailStats();
        this.mPendingRemovedUids = new LinkedList();
        this.mDeferSetCharging = new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    com.android.internal.os.BatteryStatsImpl r0 = com.android.internal.os.BatteryStatsImpl.this
                    monitor-enter(r0)
                    com.android.internal.os.BatteryStatsImpl r1 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    boolean r1 = r1.mOnBattery     // Catch:{ all -> 0x002b }
                    if (r1 == 0) goto L_0x000b
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    return
                L_0x000b:
                    com.android.internal.os.BatteryStatsImpl r1 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    r2 = 1
                    boolean r1 = r1.setChargingLocked(r2)     // Catch:{ all -> 0x002b }
                    if (r1 == 0) goto L_0x0029
                    com.android.internal.os.BatteryStatsImpl r2 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl$Clocks r2 = r2.mClocks     // Catch:{ all -> 0x002b }
                    long r2 = r2.uptimeMillis()     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl r4 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl$Clocks r4 = r4.mClocks     // Catch:{ all -> 0x002b }
                    long r4 = r4.elapsedRealtime()     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl r6 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    r6.addHistoryRecordLocked(r4, r2)     // Catch:{ all -> 0x002b }
                L_0x0029:
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    return
                L_0x002b:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.AnonymousClass1.run():void");
            }
        };
        this.mExternalSync = null;
        this.mUserInfoProvider = null;
        this.mIsolatedUids = new SparseIntArray();
        this.mUidStats = new SparseArray<>();
        this.mPartialTimers = new ArrayList<>();
        this.mFullTimers = new ArrayList<>();
        this.mWindowTimers = new ArrayList<>();
        this.mDrawTimers = new ArrayList<>();
        this.mSensorTimers = new SparseArray<>();
        this.mWifiRunningTimers = new ArrayList<>();
        this.mFullWifiLockTimers = new ArrayList<>();
        this.mWifiMulticastTimers = new ArrayList<>();
        this.mWifiScanTimers = new ArrayList<>();
        this.mWifiBatchedScanTimers = new SparseArray<>();
        this.mAudioTurnedOnTimers = new ArrayList<>();
        this.mVideoTurnedOnTimers = new ArrayList<>();
        this.mFlashlightTurnedOnTimers = new ArrayList<>();
        this.mCameraTurnedOnTimers = new ArrayList<>();
        this.mBluetoothScanOnTimers = new ArrayList<>();
        this.mLastPartialTimers = new ArrayList<>();
        this.mOnBatteryTimeBase = new TimeBase(true);
        this.mOnBatteryScreenOffTimeBase = new TimeBase(true);
        this.mActiveEvents = new BatteryStats.HistoryEventTracker();
        this.mHaveBatteryLevel = false;
        this.mRecordingHistory = false;
        this.mHistoryBuffer = Parcel.obtain();
        this.mHistoryLastWritten = new BatteryStats.HistoryItem();
        this.mHistoryLastLastWritten = new BatteryStats.HistoryItem();
        this.mHistoryReadTmp = new BatteryStats.HistoryItem();
        this.mHistoryAddTmp = new BatteryStats.HistoryItem();
        this.mHistoryTagPool = new HashMap<>();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mActiveHistoryStates = -1;
        this.mActiveHistoryStates2 = -1;
        this.mLastHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryUptime = 0;
        this.mHistoryCur = new BatteryStats.HistoryItem();
        this.mLastHistoryStepDetails = null;
        this.mLastHistoryStepLevel = 0;
        this.mCurHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mReadHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mTmpHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mScreenState = 0;
        this.mScreenBrightnessBin = -1;
        this.mScreenBrightnessTimer = new StopwatchTimer[5];
        this.mUsbDataState = 0;
        this.mGpsSignalQualityBin = -1;
        this.mGpsSignalQualityTimer = new StopwatchTimer[2];
        this.mPhoneSignalStrengthBin = -1;
        this.mPhoneSignalStrengthBinRaw = -1;
        this.mPhoneSignalStrengthsTimer = new StopwatchTimer[5];
        this.mPhoneDataConnectionType = -1;
        this.mPhoneDataConnectionsTimer = new StopwatchTimer[22];
        this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
        this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
        this.mHasWifiReporting = false;
        this.mHasBluetoothReporting = false;
        this.mHasModemReporting = false;
        this.mWifiState = -1;
        this.mWifiStateTimer = new StopwatchTimer[8];
        this.mWifiSupplState = -1;
        this.mWifiSupplStateTimer = new StopwatchTimer[13];
        this.mWifiSignalStrengthBin = -1;
        this.mWifiSignalStrengthsTimer = new StopwatchTimer[5];
        this.mIsCellularTxPowerHigh = false;
        this.mMobileRadioPowerState = 1;
        this.mWifiRadioPowerState = 1;
        this.mCharging = true;
        this.mInitStepMode = 0;
        this.mCurStepMode = 0;
        this.mModStepMode = 0;
        this.mDischargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyDischargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mChargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyChargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mDailyStartTime = 0;
        this.mNextMinDailyDeadline = 0;
        this.mNextMaxDailyDeadline = 0;
        this.mDailyItems = new ArrayList<>();
        this.mLastWriteTime = 0;
        this.mPhoneServiceState = -1;
        this.mPhoneServiceStateRaw = -1;
        this.mPhoneSimStateRaw = -1;
        this.mEstimatedBatteryCapacity = -1;
        this.mMinLearnedBatteryCapacity = -1;
        this.mMaxLearnedBatteryCapacity = -1;
        this.mRpmStats = new HashMap<>();
        this.mScreenOffRpmStats = new HashMap<>();
        this.mKernelWakelockStats = new HashMap<>();
        this.mLastWakeupReason = null;
        this.mLastWakeupUptimeMs = 0;
        this.mWakeupReasonStats = new HashMap<>();
        this.mChangedStates = 0;
        this.mChangedStates2 = 0;
        this.mInitialAcquireWakeUid = -1;
        this.mWifiFullLockNesting = 0;
        this.mWifiScanNesting = 0;
        this.mWifiMulticastNesting = 0;
        this.mNetworkStatsPool = new Pools.SynchronizedPool(6);
        this.mWifiNetworkLock = new Object();
        this.mWifiIfaces = EmptyArray.STRING;
        this.mLastWifiNetworkStats = new NetworkStats(0, -1);
        this.mModemNetworkLock = new Object();
        this.mModemIfaces = EmptyArray.STRING;
        this.mLastModemNetworkStats = new NetworkStats(0, -1);
        this.mLastModemActivityInfo = new ModemActivityInfo(0, 0, 0, new int[0], 0, 0);
        this.mLastBluetoothActivityInfo = new BluetoothActivityInfoCache();
        this.mWriteLock = new ReentrantLock();
        init(clocks);
        this.mStatsFile = null;
        this.mCheckinFile = null;
        this.mDailyFile = null;
        this.mBatteryStatsHistory = null;
        this.mHandler = null;
        this.mPlatformIdleStateCallback = null;
        this.mRailEnergyDataCallback = null;
        this.mUserInfoProvider = null;
        this.mConstants = new Constants(this.mHandler);
        clearHistoryLocked();
    }

    private void init(Clocks clocks) {
        this.mClocks = clocks;
    }

    public static class TimeBase {
        protected final Collection<TimeBaseObs> mObservers;
        protected long mPastRealtime;
        protected long mPastUptime;
        protected long mRealtime;
        protected long mRealtimeStart;
        protected boolean mRunning;
        protected long mUnpluggedRealtime;
        protected long mUnpluggedUptime;
        protected long mUptime;
        protected long mUptimeStart;

        public void dump(PrintWriter pw, String prefix) {
            StringBuilder sb = new StringBuilder(128);
            pw.print(prefix);
            pw.print("mRunning=");
            pw.println(this.mRunning);
            sb.setLength(0);
            sb.append(prefix);
            sb.append("mUptime=");
            BatteryStats.formatTimeMs(sb, this.mUptime / 1000);
            pw.println(sb.toString());
            sb.setLength(0);
            sb.append(prefix);
            sb.append("mRealtime=");
            BatteryStats.formatTimeMs(sb, this.mRealtime / 1000);
            pw.println(sb.toString());
            sb.setLength(0);
            sb.append(prefix);
            sb.append("mPastUptime=");
            BatteryStats.formatTimeMs(sb, this.mPastUptime / 1000);
            sb.append("mUptimeStart=");
            BatteryStats.formatTimeMs(sb, this.mUptimeStart / 1000);
            sb.append("mUnpluggedUptime=");
            BatteryStats.formatTimeMs(sb, this.mUnpluggedUptime / 1000);
            pw.println(sb.toString());
            sb.setLength(0);
            sb.append(prefix);
            sb.append("mPastRealtime=");
            BatteryStats.formatTimeMs(sb, this.mPastRealtime / 1000);
            sb.append("mRealtimeStart=");
            BatteryStats.formatTimeMs(sb, this.mRealtimeStart / 1000);
            sb.append("mUnpluggedRealtime=");
            BatteryStats.formatTimeMs(sb, this.mUnpluggedRealtime / 1000);
            pw.println(sb.toString());
        }

        public TimeBase(boolean isLongList) {
            this.mObservers = isLongList ? new HashSet<>() : new ArrayList<>();
        }

        public TimeBase() {
            this(false);
        }

        public void add(TimeBaseObs observer) {
            this.mObservers.add(observer);
        }

        public void remove(TimeBaseObs observer) {
            this.mObservers.remove(observer);
        }

        public boolean hasObserver(TimeBaseObs observer) {
            return this.mObservers.contains(observer);
        }

        public void init(long uptime, long realtime) {
            this.mRealtime = 0;
            this.mUptime = 0;
            this.mPastUptime = 0;
            this.mPastRealtime = 0;
            this.mUptimeStart = uptime;
            this.mRealtimeStart = realtime;
            this.mUnpluggedUptime = getUptime(this.mUptimeStart);
            this.mUnpluggedRealtime = getRealtime(this.mRealtimeStart);
        }

        public void reset(long uptime, long realtime) {
            if (!this.mRunning) {
                this.mPastUptime = 0;
                this.mPastRealtime = 0;
                return;
            }
            this.mUptimeStart = uptime;
            this.mRealtimeStart = realtime;
            this.mUnpluggedUptime = getUptime(uptime);
            this.mUnpluggedRealtime = getRealtime(realtime);
        }

        public long computeUptime(long curTime, int which) {
            return this.mUptime + getUptime(curTime);
        }

        public long computeRealtime(long curTime, int which) {
            return this.mRealtime + getRealtime(curTime);
        }

        public long getUptime(long curTime) {
            long time = this.mPastUptime;
            if (this.mRunning) {
                return time + (curTime - this.mUptimeStart);
            }
            return time;
        }

        public long getRealtime(long curTime) {
            long time = this.mPastRealtime;
            if (this.mRunning) {
                return time + (curTime - this.mRealtimeStart);
            }
            return time;
        }

        public long getUptimeStart() {
            return this.mUptimeStart;
        }

        public long getRealtimeStart() {
            return this.mRealtimeStart;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public boolean setRunning(boolean running, long uptime, long realtime) {
            boolean z = running;
            long j = uptime;
            long j2 = realtime;
            if (this.mRunning == z) {
                return false;
            }
            this.mRunning = z;
            if (z) {
                this.mUptimeStart = j;
                this.mRealtimeStart = j2;
                long batteryUptime = getUptime(j);
                this.mUnpluggedUptime = batteryUptime;
                long batteryRealtime = getRealtime(j2);
                this.mUnpluggedRealtime = batteryRealtime;
                Iterator<TimeBaseObs> iter = this.mObservers.iterator();
                while (true) {
                    Iterator<TimeBaseObs> iter2 = iter;
                    if (!iter2.hasNext()) {
                        return true;
                    }
                    iter2.next().onTimeStarted(realtime, batteryUptime, batteryRealtime);
                    iter = iter2;
                }
            } else {
                this.mPastUptime += j - this.mUptimeStart;
                this.mPastRealtime += j2 - this.mRealtimeStart;
                long batteryUptime2 = getUptime(j);
                long batteryRealtime2 = getRealtime(j2);
                Iterator<TimeBaseObs> iter3 = this.mObservers.iterator();
                while (true) {
                    Iterator<TimeBaseObs> iter4 = iter3;
                    if (!iter4.hasNext()) {
                        return true;
                    }
                    iter4.next().onTimeStopped(realtime, batteryUptime2, batteryRealtime2);
                    iter3 = iter4;
                }
            }
        }

        public void readSummaryFromParcel(Parcel in) {
            this.mUptime = in.readLong();
            this.mRealtime = in.readLong();
        }

        public void writeSummaryToParcel(Parcel out, long uptime, long realtime) {
            out.writeLong(computeUptime(uptime, 0));
            out.writeLong(computeRealtime(realtime, 0));
        }

        public void readFromParcel(Parcel in) {
            this.mRunning = false;
            this.mUptime = in.readLong();
            this.mPastUptime = in.readLong();
            this.mUptimeStart = in.readLong();
            this.mRealtime = in.readLong();
            this.mPastRealtime = in.readLong();
            this.mRealtimeStart = in.readLong();
            this.mUnpluggedUptime = in.readLong();
            this.mUnpluggedRealtime = in.readLong();
        }

        public void writeToParcel(Parcel out, long uptime, long realtime) {
            long runningUptime = getUptime(uptime);
            long runningRealtime = getRealtime(realtime);
            out.writeLong(this.mUptime);
            out.writeLong(runningUptime);
            out.writeLong(this.mUptimeStart);
            out.writeLong(this.mRealtime);
            out.writeLong(runningRealtime);
            out.writeLong(this.mRealtimeStart);
            out.writeLong(this.mUnpluggedUptime);
            out.writeLong(this.mUnpluggedRealtime);
        }
    }

    public static class Counter extends BatteryStats.Counter implements TimeBaseObs {
        @UnsupportedAppUsage
        final AtomicInteger mCount = new AtomicInteger();
        final TimeBase mTimeBase;

        public Counter(TimeBase timeBase, Parcel in) {
            this.mTimeBase = timeBase;
            this.mCount.set(in.readInt());
            timeBase.add(this);
        }

        public Counter(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public void writeToParcel(Parcel out) {
            out.writeInt(this.mCount.get());
        }

        public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
        }

        public static void writeCounterToParcel(Parcel out, Counter counter) {
            if (counter == null) {
                out.writeInt(0);
                return;
            }
            out.writeInt(1);
            counter.writeToParcel(out);
        }

        public static Counter readCounterFromParcel(TimeBase timeBase, Parcel in) {
            if (in.readInt() == 0) {
                return null;
            }
            return new Counter(timeBase, in);
        }

        public int getCountLocked(int which) {
            return this.mCount.get();
        }

        public void logState(Printer pw, String prefix) {
            pw.println(prefix + "mCount=" + this.mCount.get());
        }

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public void stepAtomic() {
            if (this.mTimeBase.isRunning()) {
                this.mCount.incrementAndGet();
            }
        }

        /* access modifiers changed from: package-private */
        public void addAtomic(int delta) {
            if (this.mTimeBase.isRunning()) {
                this.mCount.addAndGet(delta);
            }
        }

        public boolean reset(boolean detachIfReset) {
            this.mCount.set(0);
            if (!detachIfReset) {
                return true;
            }
            detach();
            return true;
        }

        public void detach() {
            this.mTimeBase.remove(this);
        }

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public void writeSummaryFromParcelLocked(Parcel out) {
            out.writeInt(this.mCount.get());
        }

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public void readSummaryFromParcelLocked(Parcel in) {
            this.mCount.set(in.readInt());
        }
    }

    @VisibleForTesting
    public static class LongSamplingCounterArray extends BatteryStats.LongCounterArray implements TimeBaseObs {
        public long[] mCounts;
        final TimeBase mTimeBase;

        private LongSamplingCounterArray(TimeBase timeBase, Parcel in) {
            this.mTimeBase = timeBase;
            this.mCounts = in.createLongArray();
            timeBase.add(this);
        }

        public LongSamplingCounterArray(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        /* access modifiers changed from: private */
        public void writeToParcel(Parcel out) {
            out.writeLongArray(this.mCounts);
        }

        public void onTimeStarted(long elapsedRealTime, long baseUptime, long baseRealtime) {
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
        }

        public long[] getCountsLocked(int which) {
            if (this.mCounts == null) {
                return null;
            }
            return Arrays.copyOf(this.mCounts, this.mCounts.length);
        }

        public void logState(Printer pw, String prefix) {
            pw.println(prefix + "mCounts=" + Arrays.toString(this.mCounts));
        }

        public void addCountLocked(long[] counts) {
            addCountLocked(counts, this.mTimeBase.isRunning());
        }

        public void addCountLocked(long[] counts, boolean isRunning) {
            if (counts != null && isRunning) {
                if (this.mCounts == null) {
                    this.mCounts = new long[counts.length];
                }
                for (int i = 0; i < counts.length; i++) {
                    long[] jArr = this.mCounts;
                    jArr[i] = jArr[i] + counts[i];
                }
            }
        }

        public int getSize() {
            if (this.mCounts == null) {
                return 0;
            }
            return this.mCounts.length;
        }

        public boolean reset(boolean detachIfReset) {
            if (this.mCounts != null) {
                Arrays.fill(this.mCounts, 0);
            }
            if (!detachIfReset) {
                return true;
            }
            detach();
            return true;
        }

        public void detach() {
            this.mTimeBase.remove(this);
        }

        /* access modifiers changed from: private */
        public void writeSummaryToParcelLocked(Parcel out) {
            out.writeLongArray(this.mCounts);
        }

        /* access modifiers changed from: private */
        public void readSummaryFromParcelLocked(Parcel in) {
            this.mCounts = in.createLongArray();
        }

        public static void writeToParcel(Parcel out, LongSamplingCounterArray counterArray) {
            if (counterArray != null) {
                out.writeInt(1);
                counterArray.writeToParcel(out);
                return;
            }
            out.writeInt(0);
        }

        public static LongSamplingCounterArray readFromParcel(Parcel in, TimeBase timeBase) {
            if (in.readInt() != 0) {
                return new LongSamplingCounterArray(timeBase, in);
            }
            return null;
        }

        public static void writeSummaryToParcelLocked(Parcel out, LongSamplingCounterArray counterArray) {
            if (counterArray != null) {
                out.writeInt(1);
                counterArray.writeSummaryToParcelLocked(out);
                return;
            }
            out.writeInt(0);
        }

        public static LongSamplingCounterArray readSummaryFromParcelLocked(Parcel in, TimeBase timeBase) {
            if (in.readInt() == 0) {
                return null;
            }
            LongSamplingCounterArray counterArray = new LongSamplingCounterArray(timeBase);
            counterArray.readSummaryFromParcelLocked(in);
            return counterArray;
        }
    }

    @VisibleForTesting
    public static class LongSamplingCounter extends BatteryStats.LongCounter implements TimeBaseObs {
        private long mCount;
        final TimeBase mTimeBase;

        public LongSamplingCounter(TimeBase timeBase, Parcel in) {
            this.mTimeBase = timeBase;
            this.mCount = in.readLong();
            timeBase.add(this);
        }

        public LongSamplingCounter(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public void writeToParcel(Parcel out) {
            out.writeLong(this.mCount);
        }

        public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
        }

        public long getCountLocked(int which) {
            return this.mCount;
        }

        public void logState(Printer pw, String prefix) {
            pw.println(prefix + "mCount=" + this.mCount);
        }

        public void addCountLocked(long count) {
            addCountLocked(count, this.mTimeBase.isRunning());
        }

        public void addCountLocked(long count, boolean isRunning) {
            if (isRunning) {
                this.mCount += count;
            }
        }

        public boolean reset(boolean detachIfReset) {
            this.mCount = 0;
            if (!detachIfReset) {
                return true;
            }
            detach();
            return true;
        }

        public void detach() {
            this.mTimeBase.remove(this);
        }

        public void writeSummaryFromParcelLocked(Parcel out) {
            out.writeLong(this.mCount);
        }

        public void readSummaryFromParcelLocked(Parcel in) {
            this.mCount = in.readLong();
        }
    }

    public static abstract class Timer extends BatteryStats.Timer implements TimeBaseObs {
        protected final Clocks mClocks;
        protected int mCount;
        protected final TimeBase mTimeBase;
        protected long mTimeBeforeMark;
        protected long mTotalTime;
        protected final int mType;

        /* access modifiers changed from: protected */
        public abstract int computeCurrentCountLocked();

        /* access modifiers changed from: protected */
        public abstract long computeRunTimeLocked(long j);

        public Timer(Clocks clocks, int type, TimeBase timeBase, Parcel in) {
            this.mClocks = clocks;
            this.mType = type;
            this.mTimeBase = timeBase;
            this.mCount = in.readInt();
            this.mTotalTime = in.readLong();
            this.mTimeBeforeMark = in.readLong();
            timeBase.add(this);
        }

        public Timer(Clocks clocks, int type, TimeBase timeBase) {
            this.mClocks = clocks;
            this.mType = type;
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public void writeToParcel(Parcel out, long elapsedRealtimeUs) {
            out.writeInt(computeCurrentCountLocked());
            out.writeLong(computeRunTimeLocked(this.mTimeBase.getRealtime(elapsedRealtimeUs)));
            out.writeLong(this.mTimeBeforeMark);
        }

        public boolean reset(boolean detachIfReset) {
            this.mTimeBeforeMark = 0;
            this.mTotalTime = 0;
            this.mCount = 0;
            if (!detachIfReset) {
                return true;
            }
            detach();
            return true;
        }

        public void detach() {
            this.mTimeBase.remove(this);
        }

        public void onTimeStarted(long elapsedRealtime, long timeBaseUptime, long baseRealtime) {
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
            this.mTotalTime = computeRunTimeLocked(baseRealtime);
            this.mCount = computeCurrentCountLocked();
        }

        @UnsupportedAppUsage
        public static void writeTimerToParcel(Parcel out, Timer timer, long elapsedRealtimeUs) {
            if (timer == null) {
                out.writeInt(0);
                return;
            }
            out.writeInt(1);
            timer.writeToParcel(out, elapsedRealtimeUs);
        }

        @UnsupportedAppUsage
        public long getTotalTimeLocked(long elapsedRealtimeUs, int which) {
            return computeRunTimeLocked(this.mTimeBase.getRealtime(elapsedRealtimeUs));
        }

        @UnsupportedAppUsage
        public int getCountLocked(int which) {
            return computeCurrentCountLocked();
        }

        public long getTimeSinceMarkLocked(long elapsedRealtimeUs) {
            return computeRunTimeLocked(this.mTimeBase.getRealtime(elapsedRealtimeUs)) - this.mTimeBeforeMark;
        }

        public void logState(Printer pw, String prefix) {
            pw.println(prefix + "mCount=" + this.mCount);
            pw.println(prefix + "mTotalTime=" + this.mTotalTime);
        }

        public void writeSummaryFromParcelLocked(Parcel out, long elapsedRealtimeUs) {
            out.writeLong(computeRunTimeLocked(this.mTimeBase.getRealtime(elapsedRealtimeUs)));
            out.writeInt(computeCurrentCountLocked());
        }

        public void readSummaryFromParcelLocked(Parcel in) {
            this.mTotalTime = in.readLong();
            this.mCount = in.readInt();
            this.mTimeBeforeMark = this.mTotalTime;
        }
    }

    public static class SamplingTimer extends Timer {
        int mCurrentReportedCount;
        long mCurrentReportedTotalTime;
        boolean mTimeBaseRunning;
        boolean mTrackingReportedValues;
        int mUnpluggedReportedCount;
        long mUnpluggedReportedTotalTime;
        int mUpdateVersion;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        @VisibleForTesting
        public SamplingTimer(Clocks clocks, TimeBase timeBase, Parcel in) {
            super(clocks, 0, timeBase, in);
            boolean z = false;
            this.mCurrentReportedCount = in.readInt();
            this.mUnpluggedReportedCount = in.readInt();
            this.mCurrentReportedTotalTime = in.readLong();
            this.mUnpluggedReportedTotalTime = in.readLong();
            this.mTrackingReportedValues = in.readInt() == 1 ? true : z;
            this.mTimeBaseRunning = timeBase.isRunning();
        }

        @VisibleForTesting
        public SamplingTimer(Clocks clocks, TimeBase timeBase) {
            super(clocks, 0, timeBase);
            this.mTrackingReportedValues = false;
            this.mTimeBaseRunning = timeBase.isRunning();
        }

        public void endSample() {
            this.mTotalTime = computeRunTimeLocked(0);
            this.mCount = computeCurrentCountLocked();
            this.mCurrentReportedTotalTime = 0;
            this.mUnpluggedReportedTotalTime = 0;
            this.mCurrentReportedCount = 0;
            this.mUnpluggedReportedCount = 0;
        }

        public void setUpdateVersion(int version) {
            this.mUpdateVersion = version;
        }

        public int getUpdateVersion() {
            return this.mUpdateVersion;
        }

        public void update(long totalTime, int count) {
            if (this.mTimeBaseRunning && !this.mTrackingReportedValues) {
                this.mUnpluggedReportedTotalTime = totalTime;
                this.mUnpluggedReportedCount = count;
            }
            this.mTrackingReportedValues = true;
            if (totalTime < this.mCurrentReportedTotalTime || count < this.mCurrentReportedCount) {
                endSample();
            }
            this.mCurrentReportedTotalTime = totalTime;
            this.mCurrentReportedCount = count;
        }

        public void add(long deltaTime, int deltaCount) {
            update(this.mCurrentReportedTotalTime + deltaTime, this.mCurrentReportedCount + deltaCount);
        }

        public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
            super.onTimeStarted(elapsedRealtime, baseUptime, baseRealtime);
            if (this.mTrackingReportedValues) {
                this.mUnpluggedReportedTotalTime = this.mCurrentReportedTotalTime;
                this.mUnpluggedReportedCount = this.mCurrentReportedCount;
            }
            this.mTimeBaseRunning = true;
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
            super.onTimeStopped(elapsedRealtime, baseUptime, baseRealtime);
            this.mTimeBaseRunning = false;
        }

        public void logState(Printer pw, String prefix) {
            super.logState(pw, prefix);
            pw.println(prefix + "mCurrentReportedCount=" + this.mCurrentReportedCount + " mUnpluggedReportedCount=" + this.mUnpluggedReportedCount + " mCurrentReportedTotalTime=" + this.mCurrentReportedTotalTime + " mUnpluggedReportedTotalTime=" + this.mUnpluggedReportedTotalTime);
        }

        /* access modifiers changed from: protected */
        public long computeRunTimeLocked(long curBatteryRealtime) {
            return this.mTotalTime + ((!this.mTimeBaseRunning || !this.mTrackingReportedValues) ? 0 : this.mCurrentReportedTotalTime - this.mUnpluggedReportedTotalTime);
        }

        /* access modifiers changed from: protected */
        public int computeCurrentCountLocked() {
            return this.mCount + ((!this.mTimeBaseRunning || !this.mTrackingReportedValues) ? 0 : this.mCurrentReportedCount - this.mUnpluggedReportedCount);
        }

        public void writeToParcel(Parcel out, long elapsedRealtimeUs) {
            super.writeToParcel(out, elapsedRealtimeUs);
            out.writeInt(this.mCurrentReportedCount);
            out.writeInt(this.mUnpluggedReportedCount);
            out.writeLong(this.mCurrentReportedTotalTime);
            out.writeLong(this.mUnpluggedReportedTotalTime);
            out.writeInt(this.mTrackingReportedValues ? 1 : 0);
        }

        public boolean reset(boolean detachIfReset) {
            super.reset(detachIfReset);
            this.mTrackingReportedValues = false;
            this.mUnpluggedReportedTotalTime = 0;
            this.mUnpluggedReportedCount = 0;
            return true;
        }
    }

    public static class BatchTimer extends Timer {
        boolean mInDischarge;
        long mLastAddedDuration;
        long mLastAddedTime;
        final Uid mUid;

        BatchTimer(Clocks clocks, Uid uid, int type, TimeBase timeBase, Parcel in) {
            super(clocks, type, timeBase, in);
            this.mUid = uid;
            this.mLastAddedTime = in.readLong();
            this.mLastAddedDuration = in.readLong();
            this.mInDischarge = timeBase.isRunning();
        }

        BatchTimer(Clocks clocks, Uid uid, int type, TimeBase timeBase) {
            super(clocks, type, timeBase);
            this.mUid = uid;
            this.mInDischarge = timeBase.isRunning();
        }

        public void writeToParcel(Parcel out, long elapsedRealtimeUs) {
            super.writeToParcel(out, elapsedRealtimeUs);
            out.writeLong(this.mLastAddedTime);
            out.writeLong(this.mLastAddedDuration);
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
            recomputeLastDuration(this.mClocks.elapsedRealtime() * 1000, false);
            this.mInDischarge = false;
            super.onTimeStopped(elapsedRealtime, baseUptime, baseRealtime);
        }

        public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
            recomputeLastDuration(elapsedRealtime, false);
            this.mInDischarge = true;
            if (this.mLastAddedTime == elapsedRealtime) {
                this.mTotalTime += this.mLastAddedDuration;
            }
            super.onTimeStarted(elapsedRealtime, baseUptime, baseRealtime);
        }

        public void logState(Printer pw, String prefix) {
            super.logState(pw, prefix);
            pw.println(prefix + "mLastAddedTime=" + this.mLastAddedTime + " mLastAddedDuration=" + this.mLastAddedDuration);
        }

        private long computeOverage(long curTime) {
            if (this.mLastAddedTime > 0) {
                return this.mLastAddedDuration - curTime;
            }
            return 0;
        }

        private void recomputeLastDuration(long curTime, boolean abort) {
            long overage = computeOverage(curTime);
            if (overage > 0) {
                if (this.mInDischarge) {
                    this.mTotalTime -= overage;
                }
                if (abort) {
                    this.mLastAddedTime = 0;
                    return;
                }
                this.mLastAddedTime = curTime;
                this.mLastAddedDuration -= overage;
            }
        }

        public void addDuration(BatteryStatsImpl stats, long durationMillis) {
            long now = this.mClocks.elapsedRealtime() * 1000;
            recomputeLastDuration(now, true);
            this.mLastAddedTime = now;
            this.mLastAddedDuration = 1000 * durationMillis;
            if (this.mInDischarge) {
                this.mTotalTime += this.mLastAddedDuration;
                this.mCount++;
            }
        }

        public void abortLastDuration(BatteryStatsImpl stats) {
            recomputeLastDuration(this.mClocks.elapsedRealtime() * 1000, true);
        }

        /* access modifiers changed from: protected */
        public int computeCurrentCountLocked() {
            return this.mCount;
        }

        /* access modifiers changed from: protected */
        public long computeRunTimeLocked(long curBatteryRealtime) {
            long overage = computeOverage(this.mClocks.elapsedRealtime() * 1000);
            if (overage <= 0) {
                return this.mTotalTime;
            }
            this.mTotalTime = overage;
            return overage;
        }

        public boolean reset(boolean detachIfReset) {
            long now = this.mClocks.elapsedRealtime() * 1000;
            recomputeLastDuration(now, true);
            boolean stillActive = this.mLastAddedTime == now;
            super.reset(!stillActive && detachIfReset);
            if (!stillActive) {
                return true;
            }
            return false;
        }
    }

    public static class DurationTimer extends StopwatchTimer {
        long mCurrentDurationMs;
        long mMaxDurationMs;
        long mStartTimeMs = -1;
        long mTotalDurationMs;

        public DurationTimer(Clocks clocks, Uid uid, int type, ArrayList<StopwatchTimer> timerPool, TimeBase timeBase, Parcel in) {
            super(clocks, uid, type, timerPool, timeBase, in);
            this.mMaxDurationMs = in.readLong();
            this.mTotalDurationMs = in.readLong();
            this.mCurrentDurationMs = in.readLong();
        }

        public DurationTimer(Clocks clocks, Uid uid, int type, ArrayList<StopwatchTimer> timerPool, TimeBase timeBase) {
            super(clocks, uid, type, timerPool, timeBase);
        }

        public void writeToParcel(Parcel out, long elapsedRealtimeUs) {
            super.writeToParcel(out, elapsedRealtimeUs);
            out.writeLong(getMaxDurationMsLocked(elapsedRealtimeUs / 1000));
            out.writeLong(this.mTotalDurationMs);
            out.writeLong(getCurrentDurationMsLocked(elapsedRealtimeUs / 1000));
        }

        public void writeSummaryFromParcelLocked(Parcel out, long elapsedRealtimeUs) {
            super.writeSummaryFromParcelLocked(out, elapsedRealtimeUs);
            out.writeLong(getMaxDurationMsLocked(elapsedRealtimeUs / 1000));
            out.writeLong(getTotalDurationMsLocked(elapsedRealtimeUs / 1000));
        }

        public void readSummaryFromParcelLocked(Parcel in) {
            super.readSummaryFromParcelLocked(in);
            this.mMaxDurationMs = in.readLong();
            this.mTotalDurationMs = in.readLong();
            this.mStartTimeMs = -1;
            this.mCurrentDurationMs = 0;
        }

        public void onTimeStarted(long elapsedRealtimeUs, long baseUptime, long baseRealtime) {
            super.onTimeStarted(elapsedRealtimeUs, baseUptime, baseRealtime);
            if (this.mNesting > 0) {
                this.mStartTimeMs = baseRealtime / 1000;
            }
        }

        public void onTimeStopped(long elapsedRealtimeUs, long baseUptime, long baseRealtimeUs) {
            super.onTimeStopped(elapsedRealtimeUs, baseUptime, baseRealtimeUs);
            if (this.mNesting > 0) {
                this.mCurrentDurationMs += (baseRealtimeUs / 1000) - this.mStartTimeMs;
            }
            this.mStartTimeMs = -1;
        }

        public void logState(Printer pw, String prefix) {
            super.logState(pw, prefix);
        }

        public void startRunningLocked(long elapsedRealtimeMs) {
            super.startRunningLocked(elapsedRealtimeMs);
            if (this.mNesting == 1 && this.mTimeBase.isRunning()) {
                this.mStartTimeMs = this.mTimeBase.getRealtime(elapsedRealtimeMs * 1000) / 1000;
            }
        }

        public void stopRunningLocked(long elapsedRealtimeMs) {
            if (this.mNesting == 1) {
                long durationMs = getCurrentDurationMsLocked(elapsedRealtimeMs);
                this.mTotalDurationMs += durationMs;
                if (durationMs > this.mMaxDurationMs) {
                    this.mMaxDurationMs = durationMs;
                }
                this.mStartTimeMs = -1;
                this.mCurrentDurationMs = 0;
            }
            super.stopRunningLocked(elapsedRealtimeMs);
        }

        public boolean reset(boolean detachIfReset) {
            boolean result = super.reset(detachIfReset);
            this.mMaxDurationMs = 0;
            this.mTotalDurationMs = 0;
            this.mCurrentDurationMs = 0;
            if (this.mNesting > 0) {
                this.mStartTimeMs = this.mTimeBase.getRealtime(this.mClocks.elapsedRealtime() * 1000) / 1000;
            } else {
                this.mStartTimeMs = -1;
            }
            return result;
        }

        public long getMaxDurationMsLocked(long elapsedRealtimeMs) {
            if (this.mNesting > 0) {
                long durationMs = getCurrentDurationMsLocked(elapsedRealtimeMs);
                if (durationMs > this.mMaxDurationMs) {
                    return durationMs;
                }
            }
            return this.mMaxDurationMs;
        }

        public long getCurrentDurationMsLocked(long elapsedRealtimeMs) {
            long durationMs = this.mCurrentDurationMs;
            if (this.mNesting <= 0 || !this.mTimeBase.isRunning()) {
                return durationMs;
            }
            return durationMs + ((this.mTimeBase.getRealtime(elapsedRealtimeMs * 1000) / 1000) - this.mStartTimeMs);
        }

        public long getTotalDurationMsLocked(long elapsedRealtimeMs) {
            return this.mTotalDurationMs + getCurrentDurationMsLocked(elapsedRealtimeMs);
        }
    }

    public static class StopwatchTimer extends Timer {
        long mAcquireTime = -1;
        @VisibleForTesting
        public boolean mInList;
        int mNesting;
        long mTimeout;
        final ArrayList<StopwatchTimer> mTimerPool;
        final Uid mUid;
        long mUpdateTime;

        public StopwatchTimer(Clocks clocks, Uid uid, int type, ArrayList<StopwatchTimer> timerPool, TimeBase timeBase, Parcel in) {
            super(clocks, type, timeBase, in);
            this.mUid = uid;
            this.mTimerPool = timerPool;
            this.mUpdateTime = in.readLong();
        }

        public StopwatchTimer(Clocks clocks, Uid uid, int type, ArrayList<StopwatchTimer> timerPool, TimeBase timeBase) {
            super(clocks, type, timeBase);
            this.mUid = uid;
            this.mTimerPool = timerPool;
        }

        public void setTimeout(long timeout) {
            this.mTimeout = timeout;
        }

        public void writeToParcel(Parcel out, long elapsedRealtimeUs) {
            super.writeToParcel(out, elapsedRealtimeUs);
            out.writeLong(this.mUpdateTime);
        }

        public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
            if (this.mNesting > 0) {
                super.onTimeStopped(elapsedRealtime, baseUptime, baseRealtime);
                this.mUpdateTime = baseRealtime;
            }
        }

        public void logState(Printer pw, String prefix) {
            super.logState(pw, prefix);
            pw.println(prefix + "mNesting=" + this.mNesting + " mUpdateTime=" + this.mUpdateTime + " mAcquireTime=" + this.mAcquireTime);
        }

        public void startRunningLocked(long elapsedRealtimeMs) {
            int i = this.mNesting;
            this.mNesting = i + 1;
            if (i == 0) {
                long batteryRealtime = this.mTimeBase.getRealtime(1000 * elapsedRealtimeMs);
                this.mUpdateTime = batteryRealtime;
                if (this.mTimerPool != null) {
                    refreshTimersLocked(batteryRealtime, this.mTimerPool, (StopwatchTimer) null);
                    this.mTimerPool.add(this);
                }
                if (this.mTimeBase.isRunning()) {
                    this.mCount++;
                    this.mAcquireTime = this.mTotalTime;
                    return;
                }
                this.mAcquireTime = -1;
            }
        }

        public boolean isRunningLocked() {
            return this.mNesting > 0;
        }

        public void stopRunningLocked(long elapsedRealtimeMs) {
            if (this.mNesting != 0) {
                int i = this.mNesting - 1;
                this.mNesting = i;
                if (i == 0) {
                    long batteryRealtime = this.mTimeBase.getRealtime(1000 * elapsedRealtimeMs);
                    if (this.mTimerPool != null) {
                        refreshTimersLocked(batteryRealtime, this.mTimerPool, (StopwatchTimer) null);
                        this.mTimerPool.remove(this);
                    } else {
                        this.mNesting = 1;
                        this.mTotalTime = computeRunTimeLocked(batteryRealtime);
                        this.mNesting = 0;
                    }
                    if (this.mAcquireTime >= 0 && this.mTotalTime == this.mAcquireTime) {
                        this.mCount--;
                    }
                }
            }
        }

        public void stopAllRunningLocked(long elapsedRealtimeMs) {
            if (this.mNesting > 0) {
                this.mNesting = 1;
                stopRunningLocked(elapsedRealtimeMs);
            }
        }

        private static long refreshTimersLocked(long batteryRealtime, ArrayList<StopwatchTimer> pool, StopwatchTimer self) {
            long selfTime = 0;
            int N = pool.size();
            for (int i = N - 1; i >= 0; i--) {
                StopwatchTimer t = pool.get(i);
                long heldTime = batteryRealtime - t.mUpdateTime;
                if (heldTime > 0) {
                    long myTime = heldTime / ((long) N);
                    if (t == self) {
                        selfTime = myTime;
                    }
                    t.mTotalTime += myTime;
                }
                t.mUpdateTime = batteryRealtime;
            }
            return selfTime;
        }

        /* access modifiers changed from: protected */
        public long computeRunTimeLocked(long curBatteryRealtime) {
            long j = 0;
            if (this.mTimeout > 0 && curBatteryRealtime > this.mUpdateTime + this.mTimeout) {
                curBatteryRealtime = this.mUpdateTime + this.mTimeout;
            }
            long j2 = this.mTotalTime;
            if (this.mNesting > 0) {
                j = (curBatteryRealtime - this.mUpdateTime) / ((long) (this.mTimerPool != null ? this.mTimerPool.size() : 1));
            }
            return j2 + j;
        }

        /* access modifiers changed from: protected */
        public int computeCurrentCountLocked() {
            return this.mCount;
        }

        public boolean reset(boolean detachIfReset) {
            boolean z = false;
            boolean canDetach = this.mNesting <= 0;
            if (canDetach && detachIfReset) {
                z = true;
            }
            super.reset(z);
            if (this.mNesting > 0) {
                this.mUpdateTime = this.mTimeBase.getRealtime(this.mClocks.elapsedRealtime() * 1000);
            }
            this.mAcquireTime = -1;
            return canDetach;
        }

        @UnsupportedAppUsage
        public void detach() {
            super.detach();
            if (this.mTimerPool != null) {
                this.mTimerPool.remove(this);
            }
        }

        public void readSummaryFromParcelLocked(Parcel in) {
            super.readSummaryFromParcelLocked(in);
            this.mNesting = 0;
        }

        public void setMark(long elapsedRealtimeMs) {
            long batteryRealtime = this.mTimeBase.getRealtime(1000 * elapsedRealtimeMs);
            if (this.mNesting > 0) {
                if (this.mTimerPool != null) {
                    refreshTimersLocked(batteryRealtime, this.mTimerPool, this);
                } else {
                    this.mTotalTime += batteryRealtime - this.mUpdateTime;
                    this.mUpdateTime = batteryRealtime;
                }
            }
            this.mTimeBeforeMark = this.mTotalTime;
        }
    }

    public static class DualTimer extends DurationTimer {
        private final DurationTimer mSubTimer;

        public DualTimer(Clocks clocks, Uid uid, int type, ArrayList<StopwatchTimer> timerPool, TimeBase timeBase, TimeBase subTimeBase, Parcel in) {
            super(clocks, uid, type, timerPool, timeBase, in);
            this.mSubTimer = new DurationTimer(clocks, uid, type, (ArrayList<StopwatchTimer>) null, subTimeBase, in);
        }

        public DualTimer(Clocks clocks, Uid uid, int type, ArrayList<StopwatchTimer> timerPool, TimeBase timeBase, TimeBase subTimeBase) {
            super(clocks, uid, type, timerPool, timeBase);
            this.mSubTimer = new DurationTimer(clocks, uid, type, (ArrayList<StopwatchTimer>) null, subTimeBase);
        }

        public DurationTimer getSubTimer() {
            return this.mSubTimer;
        }

        public void startRunningLocked(long elapsedRealtimeMs) {
            super.startRunningLocked(elapsedRealtimeMs);
            this.mSubTimer.startRunningLocked(elapsedRealtimeMs);
        }

        public void stopRunningLocked(long elapsedRealtimeMs) {
            super.stopRunningLocked(elapsedRealtimeMs);
            this.mSubTimer.stopRunningLocked(elapsedRealtimeMs);
        }

        public void stopAllRunningLocked(long elapsedRealtimeMs) {
            super.stopAllRunningLocked(elapsedRealtimeMs);
            this.mSubTimer.stopAllRunningLocked(elapsedRealtimeMs);
        }

        public boolean reset(boolean detachIfReset) {
            if (!(false | (!this.mSubTimer.reset(false))) && !(!super.reset(detachIfReset))) {
                return true;
            }
            return false;
        }

        public void detach() {
            this.mSubTimer.detach();
            super.detach();
        }

        public void writeToParcel(Parcel out, long elapsedRealtimeUs) {
            super.writeToParcel(out, elapsedRealtimeUs);
            this.mSubTimer.writeToParcel(out, elapsedRealtimeUs);
        }

        public void writeSummaryFromParcelLocked(Parcel out, long elapsedRealtimeUs) {
            super.writeSummaryFromParcelLocked(out, elapsedRealtimeUs);
            this.mSubTimer.writeSummaryFromParcelLocked(out, elapsedRealtimeUs);
        }

        public void readSummaryFromParcelLocked(Parcel in) {
            super.readSummaryFromParcelLocked(in);
            this.mSubTimer.readSummaryFromParcelLocked(in);
        }
    }

    public abstract class OverflowArrayMap<T> {
        private static final String OVERFLOW_NAME = "*overflow*";
        ArrayMap<String, MutableInt> mActiveOverflow;
        T mCurOverflow;
        long mLastCleanupTime;
        long mLastClearTime;
        long mLastOverflowFinishTime;
        long mLastOverflowTime;
        final ArrayMap<String, T> mMap = new ArrayMap<>();
        final int mUid;

        public abstract T instantiateObject();

        public OverflowArrayMap(int uid) {
            this.mUid = uid;
        }

        public ArrayMap<String, T> getMap() {
            return this.mMap;
        }

        public void clear() {
            this.mLastClearTime = SystemClock.elapsedRealtime();
            this.mMap.clear();
            this.mCurOverflow = null;
            this.mActiveOverflow = null;
        }

        public void add(String name, T obj) {
            if (name == null) {
                name = "";
            }
            this.mMap.put(name, obj);
            if (OVERFLOW_NAME.equals(name)) {
                this.mCurOverflow = obj;
            }
        }

        public void cleanup() {
            this.mLastCleanupTime = SystemClock.elapsedRealtime();
            if (this.mActiveOverflow != null && this.mActiveOverflow.size() == 0) {
                this.mActiveOverflow = null;
            }
            if (this.mActiveOverflow == null) {
                if (this.mMap.containsKey(OVERFLOW_NAME)) {
                    Slog.wtf(BatteryStatsImpl.TAG, "Cleaning up with no active overflow, but have overflow entry " + this.mMap.get(OVERFLOW_NAME));
                    this.mMap.remove(OVERFLOW_NAME);
                }
                this.mCurOverflow = null;
            } else if (this.mCurOverflow == null || !this.mMap.containsKey(OVERFLOW_NAME)) {
                Slog.wtf(BatteryStatsImpl.TAG, "Cleaning up with active overflow, but no overflow entry: cur=" + this.mCurOverflow + " map=" + this.mMap.get(OVERFLOW_NAME));
            }
        }

        public T startObject(String name) {
            MutableInt over;
            if (name == null) {
                name = "";
            }
            T obj = this.mMap.get(name);
            if (obj != null) {
                return obj;
            }
            if (this.mActiveOverflow != null && (over = this.mActiveOverflow.get(name)) != null) {
                T obj2 = this.mCurOverflow;
                if (obj2 == null) {
                    Slog.wtf(BatteryStatsImpl.TAG, "Have active overflow " + name + " but null overflow");
                    T instantiateObject = instantiateObject();
                    this.mCurOverflow = instantiateObject;
                    obj2 = instantiateObject;
                    this.mMap.put(OVERFLOW_NAME, obj2);
                }
                over.value++;
                return obj2;
            } else if (this.mMap.size() >= BatteryStatsImpl.MAX_WAKELOCKS_PER_UID) {
                T obj3 = this.mCurOverflow;
                if (obj3 == null) {
                    T instantiateObject2 = instantiateObject();
                    this.mCurOverflow = instantiateObject2;
                    obj3 = instantiateObject2;
                    this.mMap.put(OVERFLOW_NAME, obj3);
                }
                if (this.mActiveOverflow == null) {
                    this.mActiveOverflow = new ArrayMap<>();
                }
                this.mActiveOverflow.put(name, new MutableInt(1));
                this.mLastOverflowTime = SystemClock.elapsedRealtime();
                return obj3;
            } else {
                T obj4 = instantiateObject();
                this.mMap.put(name, obj4);
                return obj4;
            }
        }

        public T stopObject(String name) {
            MutableInt over;
            T obj;
            if (name == null) {
                name = "";
            }
            T obj2 = this.mMap.get(name);
            if (obj2 != null) {
                return obj2;
            }
            if (this.mActiveOverflow == null || (over = this.mActiveOverflow.get(name)) == null || (obj = this.mCurOverflow) == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to find object for ");
                sb.append(name);
                sb.append(" in uid ");
                sb.append(this.mUid);
                sb.append(" mapsize=");
                sb.append(this.mMap.size());
                sb.append(" activeoverflow=");
                sb.append(this.mActiveOverflow);
                sb.append(" curoverflow=");
                sb.append(this.mCurOverflow);
                long now = SystemClock.elapsedRealtime();
                if (this.mLastOverflowTime != 0) {
                    sb.append(" lastOverflowTime=");
                    TimeUtils.formatDuration(this.mLastOverflowTime - now, sb);
                }
                if (this.mLastOverflowFinishTime != 0) {
                    sb.append(" lastOverflowFinishTime=");
                    TimeUtils.formatDuration(this.mLastOverflowFinishTime - now, sb);
                }
                if (this.mLastClearTime != 0) {
                    sb.append(" lastClearTime=");
                    TimeUtils.formatDuration(this.mLastClearTime - now, sb);
                }
                if (this.mLastCleanupTime != 0) {
                    sb.append(" lastCleanupTime=");
                    TimeUtils.formatDuration(this.mLastCleanupTime - now, sb);
                }
                Slog.wtf(BatteryStatsImpl.TAG, sb.toString());
                return null;
            }
            over.value--;
            if (over.value <= 0) {
                this.mActiveOverflow.remove(name);
                this.mLastOverflowFinishTime = SystemClock.elapsedRealtime();
            }
            return obj;
        }
    }

    public static class ControllerActivityCounterImpl extends BatteryStats.ControllerActivityCounter implements Parcelable {
        private final LongSamplingCounter mIdleTimeMillis;
        private final LongSamplingCounter mMonitoredRailChargeConsumedMaMs;
        private final LongSamplingCounter mPowerDrainMaMs;
        private final LongSamplingCounter mRxTimeMillis;
        private final LongSamplingCounter mScanTimeMillis;
        private final LongSamplingCounter mSleepTimeMillis;
        private final LongSamplingCounter[] mTxTimeMillis;

        public ControllerActivityCounterImpl(TimeBase timeBase, int numTxStates) {
            this.mIdleTimeMillis = new LongSamplingCounter(timeBase);
            this.mScanTimeMillis = new LongSamplingCounter(timeBase);
            this.mSleepTimeMillis = new LongSamplingCounter(timeBase);
            this.mRxTimeMillis = new LongSamplingCounter(timeBase);
            this.mTxTimeMillis = new LongSamplingCounter[numTxStates];
            for (int i = 0; i < numTxStates; i++) {
                this.mTxTimeMillis[i] = new LongSamplingCounter(timeBase);
            }
            this.mPowerDrainMaMs = new LongSamplingCounter(timeBase);
            this.mMonitoredRailChargeConsumedMaMs = new LongSamplingCounter(timeBase);
        }

        public ControllerActivityCounterImpl(TimeBase timeBase, int numTxStates, Parcel in) {
            this.mIdleTimeMillis = new LongSamplingCounter(timeBase, in);
            this.mScanTimeMillis = new LongSamplingCounter(timeBase, in);
            this.mSleepTimeMillis = new LongSamplingCounter(timeBase, in);
            this.mRxTimeMillis = new LongSamplingCounter(timeBase, in);
            if (in.readInt() == numTxStates) {
                this.mTxTimeMillis = new LongSamplingCounter[numTxStates];
                for (int i = 0; i < numTxStates; i++) {
                    this.mTxTimeMillis[i] = new LongSamplingCounter(timeBase, in);
                }
                this.mPowerDrainMaMs = new LongSamplingCounter(timeBase, in);
                this.mMonitoredRailChargeConsumedMaMs = new LongSamplingCounter(timeBase, in);
                return;
            }
            throw new ParcelFormatException("inconsistent tx state lengths");
        }

        public void readSummaryFromParcel(Parcel in) {
            this.mIdleTimeMillis.readSummaryFromParcelLocked(in);
            this.mScanTimeMillis.readSummaryFromParcelLocked(in);
            this.mSleepTimeMillis.readSummaryFromParcelLocked(in);
            this.mRxTimeMillis.readSummaryFromParcelLocked(in);
            if (in.readInt() == this.mTxTimeMillis.length) {
                for (LongSamplingCounter counter : this.mTxTimeMillis) {
                    counter.readSummaryFromParcelLocked(in);
                }
                this.mPowerDrainMaMs.readSummaryFromParcelLocked(in);
                this.mMonitoredRailChargeConsumedMaMs.readSummaryFromParcelLocked(in);
                return;
            }
            throw new ParcelFormatException("inconsistent tx state lengths");
        }

        public int describeContents() {
            return 0;
        }

        public void writeSummaryToParcel(Parcel dest) {
            this.mIdleTimeMillis.writeSummaryFromParcelLocked(dest);
            this.mScanTimeMillis.writeSummaryFromParcelLocked(dest);
            this.mSleepTimeMillis.writeSummaryFromParcelLocked(dest);
            this.mRxTimeMillis.writeSummaryFromParcelLocked(dest);
            dest.writeInt(this.mTxTimeMillis.length);
            for (LongSamplingCounter counter : this.mTxTimeMillis) {
                counter.writeSummaryFromParcelLocked(dest);
            }
            this.mPowerDrainMaMs.writeSummaryFromParcelLocked(dest);
            this.mMonitoredRailChargeConsumedMaMs.writeSummaryFromParcelLocked(dest);
        }

        public void writeToParcel(Parcel dest, int flags) {
            this.mIdleTimeMillis.writeToParcel(dest);
            this.mScanTimeMillis.writeToParcel(dest);
            this.mSleepTimeMillis.writeToParcel(dest);
            this.mRxTimeMillis.writeToParcel(dest);
            dest.writeInt(this.mTxTimeMillis.length);
            for (LongSamplingCounter counter : this.mTxTimeMillis) {
                counter.writeToParcel(dest);
            }
            this.mPowerDrainMaMs.writeToParcel(dest);
            this.mMonitoredRailChargeConsumedMaMs.writeToParcel(dest);
        }

        public void reset(boolean detachIfReset) {
            this.mIdleTimeMillis.reset(detachIfReset);
            this.mScanTimeMillis.reset(detachIfReset);
            this.mSleepTimeMillis.reset(detachIfReset);
            this.mRxTimeMillis.reset(detachIfReset);
            for (LongSamplingCounter counter : this.mTxTimeMillis) {
                counter.reset(detachIfReset);
            }
            this.mPowerDrainMaMs.reset(detachIfReset);
            this.mMonitoredRailChargeConsumedMaMs.reset(detachIfReset);
        }

        public void detach() {
            this.mIdleTimeMillis.detach();
            this.mScanTimeMillis.detach();
            this.mSleepTimeMillis.detach();
            this.mRxTimeMillis.detach();
            for (LongSamplingCounter counter : this.mTxTimeMillis) {
                counter.detach();
            }
            this.mPowerDrainMaMs.detach();
            this.mMonitoredRailChargeConsumedMaMs.detach();
        }

        public LongSamplingCounter getIdleTimeCounter() {
            return this.mIdleTimeMillis;
        }

        public LongSamplingCounter getScanTimeCounter() {
            return this.mScanTimeMillis;
        }

        public LongSamplingCounter getSleepTimeCounter() {
            return this.mSleepTimeMillis;
        }

        public LongSamplingCounter getRxTimeCounter() {
            return this.mRxTimeMillis;
        }

        public LongSamplingCounter[] getTxTimeCounters() {
            return this.mTxTimeMillis;
        }

        public LongSamplingCounter getPowerCounter() {
            return this.mPowerDrainMaMs;
        }

        public LongSamplingCounter getMonitoredRailChargeConsumedMaMs() {
            return this.mMonitoredRailChargeConsumedMaMs;
        }
    }

    public SamplingTimer getRpmTimerLocked(String name) {
        SamplingTimer rpmt = this.mRpmStats.get(name);
        if (rpmt != null) {
            return rpmt;
        }
        SamplingTimer rpmt2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
        this.mRpmStats.put(name, rpmt2);
        return rpmt2;
    }

    public SamplingTimer getScreenOffRpmTimerLocked(String name) {
        SamplingTimer rpmt = this.mScreenOffRpmStats.get(name);
        if (rpmt != null) {
            return rpmt;
        }
        SamplingTimer rpmt2 = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase);
        this.mScreenOffRpmStats.put(name, rpmt2);
        return rpmt2;
    }

    public SamplingTimer getWakeupReasonTimerLocked(String name) {
        SamplingTimer timer = this.mWakeupReasonStats.get(name);
        if (timer != null) {
            return timer;
        }
        SamplingTimer timer2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
        this.mWakeupReasonStats.put(name, timer2);
        return timer2;
    }

    public SamplingTimer getKernelWakelockTimerLocked(String name) {
        SamplingTimer kwlt = this.mKernelWakelockStats.get(name);
        if (kwlt != null) {
            return kwlt;
        }
        SamplingTimer kwlt2 = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase);
        this.mKernelWakelockStats.put(name, kwlt2);
        return kwlt2;
    }

    public SamplingTimer getKernelMemoryTimerLocked(long bucket) {
        SamplingTimer kmt = this.mKernelMemoryStats.get(bucket);
        if (kmt != null) {
            return kmt;
        }
        SamplingTimer kmt2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
        this.mKernelMemoryStats.put(bucket, kmt2);
        return kmt2;
    }

    private int writeHistoryTag(BatteryStats.HistoryTag tag) {
        Integer idxObj = this.mHistoryTagPool.get(tag);
        if (idxObj != null) {
            return idxObj.intValue();
        }
        int idx = this.mNextHistoryTagIdx;
        BatteryStats.HistoryTag key = new BatteryStats.HistoryTag();
        key.setTo(tag);
        tag.poolIdx = idx;
        this.mHistoryTagPool.put(key, Integer.valueOf(idx));
        this.mNextHistoryTagIdx++;
        this.mNumHistoryTagChars += key.string.length() + 1;
        return idx;
    }

    private void readHistoryTag(int index, BatteryStats.HistoryTag tag) {
        if (index < this.mReadHistoryStrings.length) {
            tag.string = this.mReadHistoryStrings[index];
            tag.uid = this.mReadHistoryUids[index];
        } else {
            tag.string = null;
            tag.uid = 0;
        }
        tag.poolIdx = index;
    }

    public void writeHistoryDelta(Parcel dest, BatteryStats.HistoryItem cur, BatteryStats.HistoryItem last) {
        int deltaTimeToken;
        int wakeLockIndex;
        int wakeReasonIndex;
        Parcel parcel = dest;
        BatteryStats.HistoryItem historyItem = cur;
        BatteryStats.HistoryItem historyItem2 = last;
        if (historyItem2 == null || historyItem.cmd != 0) {
            parcel.writeInt(DELTA_TIME_ABS);
            historyItem.writeToParcel(parcel, 0);
            return;
        }
        long deltaTime = historyItem.time - historyItem2.time;
        int lastBatteryLevelInt = buildBatteryLevelInt(historyItem2);
        int lastStateInt = buildStateInt(historyItem2);
        if (deltaTime < 0 || deltaTime > 2147483647L) {
            deltaTimeToken = EventLogTags.SYSUI_VIEW_VISIBILITY;
        } else if (deltaTime >= 524285) {
            deltaTimeToken = DELTA_TIME_INT;
        } else {
            deltaTimeToken = (int) deltaTime;
        }
        int firstToken = (historyItem.states & DELTA_STATE_MASK) | deltaTimeToken;
        int includeStepDetails = this.mLastHistoryStepLevel > historyItem.batteryLevel ? 1 : 0;
        boolean computeStepDetails = includeStepDetails != 0 || this.mLastHistoryStepDetails == null;
        int batteryLevelInt = buildBatteryLevelInt(historyItem) | includeStepDetails;
        boolean batteryLevelIntChanged = batteryLevelInt != lastBatteryLevelInt;
        if (batteryLevelIntChanged) {
            firstToken |= 524288;
        }
        int stateInt = buildStateInt(historyItem);
        boolean stateIntChanged = stateInt != lastStateInt;
        if (stateIntChanged) {
            firstToken |= 1048576;
        }
        int i = lastBatteryLevelInt;
        boolean state2IntChanged = historyItem.states2 != historyItem2.states2;
        if (state2IntChanged) {
            firstToken |= 2097152;
        }
        if (!(historyItem.wakelockTag == null && historyItem.wakeReasonTag == null)) {
            firstToken |= 4194304;
        }
        if (historyItem.eventCode != 0) {
            firstToken |= 8388608;
        }
        int i2 = lastStateInt;
        boolean batteryChargeChanged = historyItem.batteryChargeUAh != historyItem2.batteryChargeUAh;
        if (batteryChargeChanged) {
            firstToken |= 16777216;
        }
        parcel.writeInt(firstToken);
        if (deltaTimeToken >= DELTA_TIME_INT) {
            if (deltaTimeToken == DELTA_TIME_INT) {
                parcel.writeInt((int) deltaTime);
            } else {
                parcel.writeLong(deltaTime);
            }
        }
        if (batteryLevelIntChanged) {
            parcel.writeInt(batteryLevelInt);
        }
        if (stateIntChanged) {
            parcel.writeInt(stateInt);
        }
        if (state2IntChanged) {
            parcel.writeInt(historyItem.states2);
        }
        if (!(historyItem.wakelockTag == null && historyItem.wakeReasonTag == null)) {
            if (historyItem.wakelockTag != null) {
                wakeLockIndex = writeHistoryTag(historyItem.wakelockTag);
            } else {
                wakeLockIndex = 65535;
            }
            if (historyItem.wakeReasonTag != null) {
                wakeReasonIndex = writeHistoryTag(historyItem.wakeReasonTag);
            } else {
                wakeReasonIndex = 65535;
            }
            int i3 = wakeReasonIndex;
            parcel.writeInt((wakeReasonIndex << 16) | wakeLockIndex);
        }
        if (historyItem.eventCode != 0) {
            parcel.writeInt((historyItem.eventCode & 65535) | (writeHistoryTag(historyItem.eventTag) << 16));
        }
        if (computeStepDetails) {
            if (this.mPlatformIdleStateCallback != null) {
                this.mCurHistoryStepDetails.statPlatformIdleState = this.mPlatformIdleStateCallback.getPlatformLowPowerStats();
                this.mCurHistoryStepDetails.statSubsystemPowerState = this.mPlatformIdleStateCallback.getSubsystemLowPowerStats();
            }
            computeHistoryStepDetails(this.mCurHistoryStepDetails, this.mLastHistoryStepDetails);
            if (includeStepDetails != 0) {
                this.mCurHistoryStepDetails.writeToParcel(parcel);
            }
            historyItem.stepDetails = this.mCurHistoryStepDetails;
            this.mLastHistoryStepDetails = this.mCurHistoryStepDetails;
        } else {
            historyItem.stepDetails = null;
        }
        if (this.mLastHistoryStepLevel < historyItem.batteryLevel) {
            this.mLastHistoryStepDetails = null;
        }
        this.mLastHistoryStepLevel = historyItem.batteryLevel;
        if (batteryChargeChanged) {
            parcel.writeInt(historyItem.batteryChargeUAh);
        }
        boolean z = state2IntChanged;
        parcel.writeDouble(historyItem.modemRailChargeMah);
        parcel.writeDouble(historyItem.wifiRailChargeMah);
    }

    private int buildBatteryLevelInt(BatteryStats.HistoryItem h) {
        return ((h.batteryLevel << 25) & DELTA_STATE_MASK) | ((h.batteryTemperature << 15) & 33521664) | ((h.batteryVoltage << 1) & 32766);
    }

    private void readBatteryLevelInt(int batteryLevelInt, BatteryStats.HistoryItem out) {
        out.batteryLevel = (byte) ((DELTA_STATE_MASK & batteryLevelInt) >>> 25);
        out.batteryTemperature = (short) ((33521664 & batteryLevelInt) >>> 15);
        out.batteryVoltage = (char) ((batteryLevelInt & 32766) >>> 1);
    }

    private int buildStateInt(BatteryStats.HistoryItem h) {
        int plugType = 0;
        if ((h.batteryPlugType & 1) != 0) {
            plugType = 1;
        } else if ((h.batteryPlugType & 2) != 0) {
            plugType = 2;
        } else if ((h.batteryPlugType & 4) != 0) {
            plugType = 3;
        }
        return ((h.batteryStatus & 7) << 29) | ((h.batteryHealth & 7) << 26) | ((plugType & 3) << 24) | (h.states & 16777215);
    }

    private void computeHistoryStepDetails(BatteryStats.HistoryStepDetails out, BatteryStats.HistoryStepDetails last) {
        BatteryStats.HistoryStepDetails tmp = last != null ? this.mTmpHistoryStepDetails : out;
        requestImmediateCpuUpdate();
        int i = 0;
        if (last == null) {
            int NU = this.mUidStats.size();
            while (i < NU) {
                Uid uid = this.mUidStats.valueAt(i);
                uid.mLastStepUserTime = uid.mCurStepUserTime;
                uid.mLastStepSystemTime = uid.mCurStepSystemTime;
                i++;
            }
            this.mLastStepCpuUserTime = this.mCurStepCpuUserTime;
            this.mLastStepCpuSystemTime = this.mCurStepCpuSystemTime;
            this.mLastStepStatUserTime = this.mCurStepStatUserTime;
            this.mLastStepStatSystemTime = this.mCurStepStatSystemTime;
            this.mLastStepStatIOWaitTime = this.mCurStepStatIOWaitTime;
            this.mLastStepStatIrqTime = this.mCurStepStatIrqTime;
            this.mLastStepStatSoftIrqTime = this.mCurStepStatSoftIrqTime;
            this.mLastStepStatIdleTime = this.mCurStepStatIdleTime;
            tmp.clear();
            return;
        }
        out.userTime = (int) (this.mCurStepCpuUserTime - this.mLastStepCpuUserTime);
        out.systemTime = (int) (this.mCurStepCpuSystemTime - this.mLastStepCpuSystemTime);
        out.statUserTime = (int) (this.mCurStepStatUserTime - this.mLastStepStatUserTime);
        out.statSystemTime = (int) (this.mCurStepStatSystemTime - this.mLastStepStatSystemTime);
        out.statIOWaitTime = (int) (this.mCurStepStatIOWaitTime - this.mLastStepStatIOWaitTime);
        out.statIrqTime = (int) (this.mCurStepStatIrqTime - this.mLastStepStatIrqTime);
        out.statSoftIrqTime = (int) (this.mCurStepStatSoftIrqTime - this.mLastStepStatSoftIrqTime);
        out.statIdlTime = (int) (this.mCurStepStatIdleTime - this.mLastStepStatIdleTime);
        out.appCpuUid3 = -1;
        out.appCpuUid2 = -1;
        out.appCpuUid1 = -1;
        out.appCpuUTime3 = 0;
        out.appCpuUTime2 = 0;
        out.appCpuUTime1 = 0;
        out.appCpuSTime3 = 0;
        out.appCpuSTime2 = 0;
        out.appCpuSTime1 = 0;
        int NU2 = this.mUidStats.size();
        while (i < NU2) {
            Uid uid2 = this.mUidStats.valueAt(i);
            int totalUTime = (int) (uid2.mCurStepUserTime - uid2.mLastStepUserTime);
            int totalSTime = (int) (uid2.mCurStepSystemTime - uid2.mLastStepSystemTime);
            int totalTime = totalUTime + totalSTime;
            uid2.mLastStepUserTime = uid2.mCurStepUserTime;
            uid2.mLastStepSystemTime = uid2.mCurStepSystemTime;
            if (totalTime > out.appCpuUTime3 + out.appCpuSTime3) {
                if (totalTime <= out.appCpuUTime2 + out.appCpuSTime2) {
                    out.appCpuUid3 = uid2.mUid;
                    out.appCpuUTime3 = totalUTime;
                    out.appCpuSTime3 = totalSTime;
                } else {
                    out.appCpuUid3 = out.appCpuUid2;
                    out.appCpuUTime3 = out.appCpuUTime2;
                    out.appCpuSTime3 = out.appCpuSTime2;
                    if (totalTime <= out.appCpuUTime1 + out.appCpuSTime1) {
                        out.appCpuUid2 = uid2.mUid;
                        out.appCpuUTime2 = totalUTime;
                        out.appCpuSTime2 = totalSTime;
                    } else {
                        out.appCpuUid2 = out.appCpuUid1;
                        out.appCpuUTime2 = out.appCpuUTime1;
                        out.appCpuSTime2 = out.appCpuSTime1;
                        out.appCpuUid1 = uid2.mUid;
                        out.appCpuUTime1 = totalUTime;
                        out.appCpuSTime1 = totalSTime;
                    }
                }
            }
            i++;
        }
        this.mLastStepCpuUserTime = this.mCurStepCpuUserTime;
        this.mLastStepCpuSystemTime = this.mCurStepCpuSystemTime;
        this.mLastStepStatUserTime = this.mCurStepStatUserTime;
        this.mLastStepStatSystemTime = this.mCurStepStatSystemTime;
        this.mLastStepStatIOWaitTime = this.mCurStepStatIOWaitTime;
        this.mLastStepStatIrqTime = this.mCurStepStatIrqTime;
        this.mLastStepStatSoftIrqTime = this.mCurStepStatSoftIrqTime;
        this.mLastStepStatIdleTime = this.mCurStepStatIdleTime;
    }

    public void readHistoryDelta(Parcel src, BatteryStats.HistoryItem cur) {
        int batteryLevelInt;
        int firstToken = src.readInt();
        int deltaTimeToken = 524287 & firstToken;
        cur.cmd = 0;
        cur.numReadInts = 1;
        if (deltaTimeToken < DELTA_TIME_ABS) {
            cur.time += (long) deltaTimeToken;
        } else if (deltaTimeToken == DELTA_TIME_ABS) {
            cur.time = src.readLong();
            cur.numReadInts += 2;
            cur.readFromParcel(src);
            return;
        } else if (deltaTimeToken == DELTA_TIME_INT) {
            cur.time += (long) src.readInt();
            cur.numReadInts++;
        } else {
            cur.time += src.readLong();
            cur.numReadInts += 2;
        }
        if ((524288 & firstToken) != 0) {
            batteryLevelInt = src.readInt();
            readBatteryLevelInt(batteryLevelInt, cur);
            cur.numReadInts++;
        } else {
            batteryLevelInt = 0;
        }
        if ((1048576 & firstToken) != 0) {
            int stateInt = src.readInt();
            cur.states = (16777215 & stateInt) | (DELTA_STATE_MASK & firstToken);
            cur.batteryStatus = (byte) ((stateInt >> 29) & 7);
            cur.batteryHealth = (byte) ((stateInt >> 26) & 7);
            cur.batteryPlugType = (byte) ((stateInt >> 24) & 3);
            switch (cur.batteryPlugType) {
                case 1:
                    cur.batteryPlugType = 1;
                    break;
                case 2:
                    cur.batteryPlugType = 2;
                    break;
                case 3:
                    cur.batteryPlugType = 4;
                    break;
            }
            cur.numReadInts++;
        } else {
            cur.states = (firstToken & DELTA_STATE_MASK) | (cur.states & 16777215);
        }
        if ((2097152 & firstToken) != 0) {
            cur.states2 = src.readInt();
        }
        if ((4194304 & firstToken) != 0) {
            int indexes = src.readInt();
            int wakeLockIndex = indexes & 65535;
            int wakeReasonIndex = (indexes >> 16) & 65535;
            if (wakeLockIndex != 65535) {
                cur.wakelockTag = cur.localWakelockTag;
                readHistoryTag(wakeLockIndex, cur.wakelockTag);
            } else {
                cur.wakelockTag = null;
            }
            if (wakeReasonIndex != 65535) {
                cur.wakeReasonTag = cur.localWakeReasonTag;
                readHistoryTag(wakeReasonIndex, cur.wakeReasonTag);
            } else {
                cur.wakeReasonTag = null;
            }
            cur.numReadInts++;
        } else {
            cur.wakelockTag = null;
            cur.wakeReasonTag = null;
        }
        if ((8388608 & firstToken) != 0) {
            cur.eventTag = cur.localEventTag;
            int codeAndIndex = src.readInt();
            cur.eventCode = codeAndIndex & 65535;
            readHistoryTag((codeAndIndex >> 16) & 65535, cur.eventTag);
            cur.numReadInts++;
        } else {
            cur.eventCode = 0;
        }
        if ((batteryLevelInt & 1) != 0) {
            cur.stepDetails = this.mReadHistoryStepDetails;
            cur.stepDetails.readFromParcel(src);
        } else {
            cur.stepDetails = null;
        }
        if ((16777216 & firstToken) != 0) {
            cur.batteryChargeUAh = src.readInt();
        }
        cur.modemRailChargeMah = src.readDouble();
        cur.wifiRailChargeMah = src.readDouble();
    }

    public void commitCurrentHistoryBatchLocked() {
        this.mHistoryLastWritten.cmd = -1;
    }

    public void createFakeHistoryEvents(long numEvents) {
        for (long i = 0; i < numEvents; i++) {
            noteLongPartialWakelockStart("name1", "historyName1", 1000);
            noteLongPartialWakelockFinish("name1", "historyName1", 1000);
        }
    }

    /* access modifiers changed from: package-private */
    public void addHistoryBufferLocked(long elapsedRealtimeMs, BatteryStats.HistoryItem cur) {
        long elapsedRealtimeMs2;
        BatteryStats.HistoryItem historyItem = cur;
        if (this.mHaveBatteryLevel && this.mRecordingHistory) {
            long timeDiff = (this.mHistoryBaseTime + elapsedRealtimeMs) - this.mHistoryLastWritten.time;
            int diffStates = this.mHistoryLastWritten.states ^ (historyItem.states & this.mActiveHistoryStates);
            int diffStates2 = this.mHistoryLastWritten.states2 ^ (historyItem.states2 & this.mActiveHistoryStates2);
            int lastDiffStates = this.mHistoryLastWritten.states ^ this.mHistoryLastLastWritten.states;
            int lastDiffStates2 = this.mHistoryLastWritten.states2 ^ this.mHistoryLastLastWritten.states2;
            if (this.mHistoryBufferLastPos >= 0 && this.mHistoryLastWritten.cmd == 0 && timeDiff < 1000 && (diffStates & lastDiffStates) == 0 && (diffStates2 & lastDiffStates2) == 0 && ((this.mHistoryLastWritten.wakelockTag == null || historyItem.wakelockTag == null) && ((this.mHistoryLastWritten.wakeReasonTag == null || historyItem.wakeReasonTag == null) && this.mHistoryLastWritten.stepDetails == null && ((this.mHistoryLastWritten.eventCode == 0 || historyItem.eventCode == 0) && this.mHistoryLastWritten.batteryLevel == historyItem.batteryLevel && this.mHistoryLastWritten.batteryStatus == historyItem.batteryStatus && this.mHistoryLastWritten.batteryHealth == historyItem.batteryHealth && this.mHistoryLastWritten.batteryPlugType == historyItem.batteryPlugType && this.mHistoryLastWritten.batteryTemperature == historyItem.batteryTemperature && this.mHistoryLastWritten.batteryVoltage == historyItem.batteryVoltage)))) {
                this.mHistoryBuffer.setDataSize(this.mHistoryBufferLastPos);
                this.mHistoryBuffer.setDataPosition(this.mHistoryBufferLastPos);
                this.mHistoryBufferLastPos = -1;
                long elapsedRealtimeMs3 = this.mHistoryLastWritten.time - this.mHistoryBaseTime;
                if (this.mHistoryLastWritten.wakelockTag != null) {
                    historyItem.wakelockTag = historyItem.localWakelockTag;
                    historyItem.wakelockTag.setTo(this.mHistoryLastWritten.wakelockTag);
                }
                if (this.mHistoryLastWritten.wakeReasonTag != null) {
                    historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
                    historyItem.wakeReasonTag.setTo(this.mHistoryLastWritten.wakeReasonTag);
                }
                if (this.mHistoryLastWritten.eventCode != 0) {
                    historyItem.eventCode = this.mHistoryLastWritten.eventCode;
                    historyItem.eventTag = historyItem.localEventTag;
                    historyItem.eventTag.setTo(this.mHistoryLastWritten.eventTag);
                }
                this.mHistoryLastWritten.setTo(this.mHistoryLastLastWritten);
                elapsedRealtimeMs2 = elapsedRealtimeMs3;
            } else {
                elapsedRealtimeMs2 = elapsedRealtimeMs;
            }
            int dataSize = this.mHistoryBuffer.dataSize();
            if (dataSize >= this.mConstants.MAX_HISTORY_BUFFER) {
                long uptimeMillis = SystemClock.uptimeMillis();
                writeHistoryLocked(true);
                this.mBatteryStatsHistory.startNextFile();
                this.mHistoryBuffer.setDataSize(0);
                this.mHistoryBuffer.setDataPosition(0);
                this.mHistoryBuffer.setDataCapacity(this.mConstants.MAX_HISTORY_BUFFER / 2);
                this.mHistoryBufferLastPos = -1;
                long elapsedRealtime = this.mClocks.elapsedRealtime();
                long uptime = this.mClocks.uptimeMillis();
                BatteryStats.HistoryItem newItem = new BatteryStats.HistoryItem();
                newItem.setTo(historyItem);
                long j = timeDiff;
                int i = dataSize;
                startRecordingHistory(elapsedRealtime, uptime, false);
                addHistoryBufferLocked(elapsedRealtimeMs2, (byte) 0, newItem);
                return;
            }
            long j2 = timeDiff;
            if (dataSize == 0) {
                historyItem.currentTime = System.currentTimeMillis();
                addHistoryBufferLocked(elapsedRealtimeMs2, (byte) 7, historyItem);
            }
            addHistoryBufferLocked(elapsedRealtimeMs2, (byte) 0, historyItem);
        }
    }

    private void addHistoryBufferLocked(long elapsedRealtimeMs, byte cmd, BatteryStats.HistoryItem cur) {
        if (!this.mIteratingHistory) {
            this.mHistoryBufferLastPos = this.mHistoryBuffer.dataPosition();
            this.mHistoryLastLastWritten.setTo(this.mHistoryLastWritten);
            this.mHistoryLastWritten.setTo(this.mHistoryBaseTime + elapsedRealtimeMs, cmd, cur);
            this.mHistoryLastWritten.states &= this.mActiveHistoryStates;
            this.mHistoryLastWritten.states2 &= this.mActiveHistoryStates2;
            writeHistoryDelta(this.mHistoryBuffer, this.mHistoryLastWritten, this.mHistoryLastLastWritten);
            this.mLastHistoryElapsedRealtime = elapsedRealtimeMs;
            cur.wakelockTag = null;
            cur.wakeReasonTag = null;
            cur.eventCode = 0;
            cur.eventTag = null;
            return;
        }
        throw new IllegalStateException("Can't do this while iterating history!");
    }

    /* access modifiers changed from: package-private */
    public void addHistoryRecordLocked(long elapsedRealtimeMs, long uptimeMs) {
        if (this.mTrackRunningHistoryElapsedRealtime != 0) {
            long diffElapsed = elapsedRealtimeMs - this.mTrackRunningHistoryElapsedRealtime;
            long diffUptime = uptimeMs - this.mTrackRunningHistoryUptime;
            if (diffUptime < diffElapsed - 20) {
                this.mHistoryAddTmp.setTo(this.mHistoryLastWritten);
                this.mHistoryAddTmp.wakelockTag = null;
                this.mHistoryAddTmp.wakeReasonTag = null;
                this.mHistoryAddTmp.eventCode = 0;
                this.mHistoryAddTmp.states &= Integer.MAX_VALUE;
                addHistoryRecordInnerLocked(elapsedRealtimeMs - (diffElapsed - diffUptime), this.mHistoryAddTmp);
            }
        }
        this.mHistoryCur.states |= Integer.MIN_VALUE;
        this.mTrackRunningHistoryElapsedRealtime = elapsedRealtimeMs;
        this.mTrackRunningHistoryUptime = uptimeMs;
        addHistoryRecordInnerLocked(elapsedRealtimeMs, this.mHistoryCur);
    }

    /* access modifiers changed from: package-private */
    public void addHistoryRecordInnerLocked(long elapsedRealtimeMs, BatteryStats.HistoryItem cur) {
        addHistoryBufferLocked(elapsedRealtimeMs, cur);
    }

    public void addHistoryEventLocked(long elapsedRealtimeMs, long uptimeMs, int code, String name, int uid) {
        this.mHistoryCur.eventCode = code;
        this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
        this.mHistoryCur.eventTag.string = name;
        this.mHistoryCur.eventTag.uid = uid;
        addHistoryRecordLocked(elapsedRealtimeMs, uptimeMs);
    }

    /* access modifiers changed from: package-private */
    public void addHistoryRecordLocked(long elapsedRealtimeMs, long uptimeMs, byte cmd, BatteryStats.HistoryItem cur) {
        BatteryStats.HistoryItem rec = this.mHistoryCache;
        if (rec != null) {
            this.mHistoryCache = rec.next;
        } else {
            rec = new BatteryStats.HistoryItem();
        }
        rec.setTo(this.mHistoryBaseTime + elapsedRealtimeMs, cmd, cur);
        addHistoryRecordLocked(rec);
    }

    /* access modifiers changed from: package-private */
    public void addHistoryRecordLocked(BatteryStats.HistoryItem rec) {
        this.mNumHistoryItems++;
        rec.next = null;
        this.mHistoryLastEnd = this.mHistoryEnd;
        if (this.mHistoryEnd != null) {
            this.mHistoryEnd.next = rec;
            this.mHistoryEnd = rec;
            return;
        }
        this.mHistoryEnd = rec;
        this.mHistory = rec;
    }

    /* access modifiers changed from: package-private */
    public void clearHistoryLocked() {
        this.mHistoryBaseTime = 0;
        this.mLastHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryUptime = 0;
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mConstants.MAX_HISTORY_BUFFER / 2);
        this.mHistoryLastLastWritten.clear();
        this.mHistoryLastWritten.clear();
        this.mHistoryTagPool.clear();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mActiveHistoryStates = -1;
        this.mActiveHistoryStates2 = -1;
    }

    @GuardedBy({"this"})
    public void updateTimeBasesLocked(boolean unplugged, int screenState, long uptime, long realtime) {
        boolean z = unplugged;
        long j = uptime;
        long j2 = realtime;
        boolean screenOff = !isScreenOn(screenState);
        boolean updateOnBatteryTimeBase = z != this.mOnBatteryTimeBase.isRunning();
        boolean updateOnBatteryScreenOffTimeBase = (z && screenOff) != this.mOnBatteryScreenOffTimeBase.isRunning();
        if (updateOnBatteryScreenOffTimeBase || updateOnBatteryTimeBase) {
            if (updateOnBatteryScreenOffTimeBase) {
                updateKernelWakelocksLocked();
                updateBatteryPropertiesLocked();
            }
            if (updateOnBatteryTimeBase) {
                updateRpmStatsLocked();
            }
            this.mOnBatteryTimeBase.setRunning(unplugged, uptime, realtime);
            if (updateOnBatteryTimeBase) {
                for (int i = this.mUidStats.size() - 1; i >= 0; i--) {
                    this.mUidStats.valueAt(i).updateOnBatteryBgTimeBase(j, j2);
                }
            }
            if (updateOnBatteryScreenOffTimeBase) {
                this.mOnBatteryScreenOffTimeBase.setRunning(z && screenOff, uptime, realtime);
                for (int i2 = this.mUidStats.size() - 1; i2 >= 0; i2--) {
                    this.mUidStats.valueAt(i2).updateOnBatteryScreenOffBgTimeBase(j, j2);
                }
            }
        }
    }

    private void updateBatteryPropertiesLocked() {
        try {
            IBatteryPropertiesRegistrar registrar = IBatteryPropertiesRegistrar.Stub.asInterface(ServiceManager.getService("batteryproperties"));
            if (registrar != null) {
                registrar.scheduleUpdate();
            }
        } catch (RemoteException e) {
        }
    }

    public void addIsolatedUidLocked(int isolatedUid, int appUid) {
        this.mIsolatedUids.put(isolatedUid, appUid);
        getUidStatsLocked(appUid).addIsolatedUid(isolatedUid);
    }

    public void scheduleRemoveIsolatedUidLocked(int isolatedUid, int appUid) {
        if (this.mIsolatedUids.get(isolatedUid, -1) == appUid && this.mExternalSync != null) {
            this.mExternalSync.scheduleCpuSyncDueToRemovedUid(isolatedUid);
        }
    }

    @GuardedBy({"this"})
    public void removeIsolatedUidLocked(int isolatedUid) {
        int idx = this.mIsolatedUids.indexOfKey(isolatedUid);
        if (idx >= 0) {
            getUidStatsLocked(this.mIsolatedUids.valueAt(idx)).removeIsolatedUid(isolatedUid);
            this.mIsolatedUids.removeAt(idx);
        }
        this.mPendingRemovedUids.add(new UidToRemove(this, isolatedUid, this.mClocks.elapsedRealtime()));
    }

    public int mapUid(int uid) {
        int isolated = this.mIsolatedUids.get(uid, -1);
        return isolated > 0 ? isolated : uid;
    }

    public void noteEventLocked(int code, String name, int uid) {
        int uid2 = mapUid(uid);
        if (this.mActiveEvents.updateState(code, name, uid2, 0)) {
            addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), code, name, uid2);
        }
    }

    public void noteCurrentTimeChangedLocked() {
        recordCurrentTimeChangeLocked(System.currentTimeMillis(), this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
    }

    public void noteProcessStartLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        if (isOnBattery()) {
            getUidStatsLocked(uid2).getProcessStatsLocked(name).incStartsLocked();
        }
        if (this.mActiveEvents.updateState(32769, name, uid2, 0) && this.mRecordAllHistory) {
            addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 32769, name, uid2);
        }
    }

    public void noteProcessCrashLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        if (isOnBattery()) {
            getUidStatsLocked(uid2).getProcessStatsLocked(name).incNumCrashesLocked();
        }
    }

    public void noteProcessAnrLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        if (isOnBattery()) {
            getUidStatsLocked(uid2).getProcessStatsLocked(name).incNumAnrsLocked();
        }
    }

    public void noteUidProcessStateLocked(int uid, int state) {
        if (uid == mapUid(uid)) {
            getUidStatsLocked(uid).updateUidProcessStateLocked(state);
        }
    }

    public void noteProcessFinishLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        if (this.mActiveEvents.updateState(16385, name, uid2, 0) && this.mRecordAllHistory) {
            addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 16385, name, uid2);
        }
    }

    public void noteSyncStartLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        getUidStatsLocked(uid2).noteStartSyncLocked(name, elapsedRealtime);
        if (this.mActiveEvents.updateState(32772, name, uid2, 0)) {
            addHistoryEventLocked(elapsedRealtime, uptime, 32772, name, uid2);
        }
    }

    public void noteSyncFinishLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        getUidStatsLocked(uid2).noteStopSyncLocked(name, elapsedRealtime);
        if (this.mActiveEvents.updateState(16388, name, uid2, 0)) {
            addHistoryEventLocked(elapsedRealtime, uptime, 16388, name, uid2);
        }
    }

    public void noteJobStartLocked(String name, int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        getUidStatsLocked(uid2).noteStartJobLocked(name, elapsedRealtime);
        if (this.mActiveEvents.updateState(32774, name, uid2, 0)) {
            addHistoryEventLocked(elapsedRealtime, uptime, 32774, name, uid2);
        }
    }

    public void noteJobFinishLocked(String name, int uid, int stopReason) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        getUidStatsLocked(uid2).noteStopJobLocked(name, elapsedRealtime, stopReason);
        if (this.mActiveEvents.updateState(16390, name, uid2, 0)) {
            addHistoryEventLocked(elapsedRealtime, uptime, 16390, name, uid2);
        }
    }

    public void noteJobsDeferredLocked(int uid, int numDeferred, long sinceLast) {
        getUidStatsLocked(mapUid(uid)).noteJobsDeferredLocked(numDeferred, sinceLast);
    }

    public void noteAlarmStartLocked(String name, WorkSource workSource, int uid) {
        noteAlarmStartOrFinishLocked(BatteryStats.HistoryItem.EVENT_ALARM_START, name, workSource, uid);
    }

    public void noteAlarmFinishLocked(String name, WorkSource workSource, int uid) {
        noteAlarmStartOrFinishLocked(16397, name, workSource, uid);
    }

    private void noteAlarmStartOrFinishLocked(int historyItem, String name, WorkSource workSource, int uid) {
        List<WorkSource.WorkChain> workChains;
        int i;
        int uid2;
        int i2;
        int uid3;
        int i3;
        int i4 = historyItem;
        String str = name;
        WorkSource workSource2 = workSource;
        if (this.mRecordAllHistory) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            int i5 = 0;
            if (workSource2 != null) {
                int uid4 = uid;
                int i6 = 0;
                while (true) {
                    int i7 = i6;
                    if (i7 >= workSource.size()) {
                        break;
                    }
                    int uid5 = mapUid(workSource2.get(i7));
                    if (this.mActiveEvents.updateState(i4, str, uid5, i5)) {
                        uid3 = uid5;
                        i2 = i7;
                        i3 = i5;
                        addHistoryEventLocked(elapsedRealtime, uptime, historyItem, name, uid3);
                    } else {
                        uid3 = uid5;
                        i2 = i7;
                        i3 = i5;
                    }
                    i6 = i2 + 1;
                    i5 = i3;
                    uid4 = uid3;
                    workSource2 = workSource;
                }
                int i8 = i5;
                List<WorkSource.WorkChain> workChains2 = workSource.getWorkChains();
                if (workChains2 != null) {
                    int i9 = i8;
                    while (true) {
                        int i10 = i9;
                        if (i10 >= workChains2.size()) {
                            break;
                        }
                        int uid6 = mapUid(workChains2.get(i10).getAttributionUid());
                        if (this.mActiveEvents.updateState(i4, str, uid6, i8)) {
                            uid2 = uid6;
                            i = i10;
                            workChains = workChains2;
                            addHistoryEventLocked(elapsedRealtime, uptime, historyItem, name, uid2);
                        } else {
                            uid2 = uid6;
                            i = i10;
                            workChains = workChains2;
                        }
                        i9 = i + 1;
                        uid4 = uid2;
                        workChains2 = workChains;
                    }
                }
                int i11 = uid4;
                return;
            }
            int uid7 = mapUid(uid);
            if (this.mActiveEvents.updateState(i4, str, uid7, 0)) {
                int i12 = uid7;
                addHistoryEventLocked(elapsedRealtime, uptime, historyItem, name, uid7);
                return;
            }
        }
    }

    public void noteWakupAlarmLocked(String packageName, int uid, WorkSource workSource, String tag) {
        if (workSource != null) {
            int uid2 = uid;
            for (int i = 0; i < workSource.size(); i++) {
                uid2 = workSource.get(i);
                String workSourceName = workSource.getName(i);
                if (isOnBattery()) {
                    getPackageStatsLocked(uid2, workSourceName != null ? workSourceName : packageName).noteWakeupAlarmLocked(tag);
                }
            }
            ArrayList<WorkSource.WorkChain> workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    uid2 = workChains.get(i2).getAttributionUid();
                    if (isOnBattery()) {
                        getPackageStatsLocked(uid2, packageName).noteWakeupAlarmLocked(tag);
                    }
                }
            }
            int i3 = uid2;
        } else if (isOnBattery()) {
            getPackageStatsLocked(uid, packageName).noteWakeupAlarmLocked(tag);
        }
    }

    private void requestWakelockCpuUpdate() {
        this.mExternalSync.scheduleCpuSyncDueToWakelockChange(5000);
    }

    private void requestImmediateCpuUpdate() {
        this.mExternalSync.scheduleCpuSyncDueToWakelockChange(0);
    }

    public void setRecordAllHistoryLocked(boolean enabled) {
        boolean z = enabled;
        this.mRecordAllHistory = z;
        if (!z) {
            this.mActiveEvents.removeEvents(5);
            this.mActiveEvents.removeEvents(13);
            HashMap<String, SparseIntArray> active = this.mActiveEvents.getStateForEvent(1);
            if (active != null) {
                long mSecRealtime = this.mClocks.elapsedRealtime();
                long mSecUptime = this.mClocks.uptimeMillis();
                for (Map.Entry<String, SparseIntArray> ent : active.entrySet()) {
                    SparseIntArray uids = ent.getValue();
                    int j = 0;
                    while (true) {
                        int j2 = j;
                        if (j2 < uids.size()) {
                            addHistoryEventLocked(mSecRealtime, mSecUptime, 16385, ent.getKey(), uids.keyAt(j2));
                            j = j2 + 1;
                            uids = uids;
                        }
                    }
                }
                return;
            }
            return;
        }
        HashMap<String, SparseIntArray> active2 = this.mActiveEvents.getStateForEvent(1);
        if (active2 != null) {
            long mSecRealtime2 = this.mClocks.elapsedRealtime();
            long mSecUptime2 = this.mClocks.uptimeMillis();
            for (Map.Entry<String, SparseIntArray> ent2 : active2.entrySet()) {
                SparseIntArray uids2 = ent2.getValue();
                int j3 = 0;
                while (true) {
                    int j4 = j3;
                    if (j4 < uids2.size()) {
                        addHistoryEventLocked(mSecRealtime2, mSecUptime2, 32769, ent2.getKey(), uids2.keyAt(j4));
                        j3 = j4 + 1;
                        uids2 = uids2;
                    }
                }
            }
        }
    }

    public void setNoAutoReset(boolean enabled) {
        this.mNoAutoReset = enabled;
    }

    public void setPretendScreenOff(boolean pretendScreenOff) {
        if (this.mPretendScreenOff != pretendScreenOff) {
            this.mPretendScreenOff = pretendScreenOff;
            noteScreenStateLocked(pretendScreenOff ? 1 : 2);
        }
    }

    public void noteStartWakeLocked(int uid, int pid, WorkSource.WorkChain wc, String name, String historyName, int type, boolean unimportantForLogging, long elapsedRealtime, long uptime) {
        String historyName2;
        int i = type;
        long j = elapsedRealtime;
        long j2 = uptime;
        int uid2 = mapUid(uid);
        if (i == 0) {
            aggregateLastWakeupUptimeLocked(j2);
            String historyName3 = historyName == null ? name : historyName;
            if (!this.mRecordAllHistory || !this.mActiveEvents.updateState(32773, historyName3, uid2, 0)) {
                historyName2 = historyName3;
            } else {
                historyName2 = historyName3;
                addHistoryEventLocked(elapsedRealtime, uptime, 32773, historyName3, uid2);
            }
            if (this.mWakeLockNesting == 0) {
                this.mHistoryCur.states |= 1073741824;
                this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
                BatteryStats.HistoryTag historyTag = this.mHistoryCur.wakelockTag;
                this.mInitialAcquireWakeName = historyName2;
                historyTag.string = historyName2;
                BatteryStats.HistoryTag historyTag2 = this.mHistoryCur.wakelockTag;
                this.mInitialAcquireWakeUid = uid2;
                historyTag2.uid = uid2;
                this.mWakeLockImportant = !unimportantForLogging;
                addHistoryRecordLocked(j, j2);
            } else if (!this.mWakeLockImportant && !unimportantForLogging && this.mHistoryLastWritten.cmd == 0) {
                if (this.mHistoryLastWritten.wakelockTag != null) {
                    this.mHistoryLastWritten.wakelockTag = null;
                    this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
                    BatteryStats.HistoryTag historyTag3 = this.mHistoryCur.wakelockTag;
                    this.mInitialAcquireWakeName = historyName2;
                    historyTag3.string = historyName2;
                    BatteryStats.HistoryTag historyTag4 = this.mHistoryCur.wakelockTag;
                    this.mInitialAcquireWakeUid = uid2;
                    historyTag4.uid = uid2;
                    addHistoryRecordLocked(j, j2);
                }
                this.mWakeLockImportant = true;
            }
            this.mWakeLockNesting++;
        }
        if (uid2 >= 0) {
            if (this.mOnBatteryScreenOffTimeBase.isRunning()) {
                requestWakelockCpuUpdate();
            }
            getUidStatsLocked(uid2).noteStartWakeLocked(pid, name, type, elapsedRealtime);
            if (wc != null) {
                StatsLog.write(10, wc.getUids(), wc.getTags(), getPowerManagerWakeLockLevel(type), name, 1);
            } else {
                StatsLog.write_non_chained(10, uid2, (String) null, getPowerManagerWakeLockLevel(type), name, 1);
            }
        } else {
            int i2 = type;
        }
    }

    public void noteStopWakeLocked(int uid, int pid, WorkSource.WorkChain wc, String name, String historyName, int type, long elapsedRealtime, long uptime) {
        int i = type;
        int uid2 = mapUid(uid);
        if (i == 0) {
            this.mWakeLockNesting--;
            if (this.mRecordAllHistory) {
                String historyName2 = historyName == null ? name : historyName;
                if (this.mActiveEvents.updateState(16389, historyName2, uid2, 0)) {
                    addHistoryEventLocked(elapsedRealtime, uptime, 16389, historyName2, uid2);
                }
            }
            if (this.mWakeLockNesting == 0) {
                this.mHistoryCur.states &= -1073741825;
                this.mInitialAcquireWakeName = null;
                this.mInitialAcquireWakeUid = -1;
                addHistoryRecordLocked(elapsedRealtime, uptime);
            } else {
                long j = elapsedRealtime;
                long j2 = uptime;
            }
        } else {
            long j3 = elapsedRealtime;
            long j4 = uptime;
            String str = historyName;
        }
        if (uid2 >= 0) {
            if (this.mOnBatteryScreenOffTimeBase.isRunning()) {
                requestWakelockCpuUpdate();
            }
            getUidStatsLocked(uid2).noteStopWakeLocked(pid, name, type, elapsedRealtime);
            if (wc != null) {
                StatsLog.write(10, wc.getUids(), wc.getTags(), getPowerManagerWakeLockLevel(i), name, 0);
            } else {
                StatsLog.write_non_chained(10, uid2, (String) null, getPowerManagerWakeLockLevel(i), name, 0);
            }
        }
    }

    private int getPowerManagerWakeLockLevel(int battertStatsWakelockType) {
        if (battertStatsWakelockType == 18) {
            return 128;
        }
        switch (battertStatsWakelockType) {
            case 0:
                return 1;
            case 1:
                return 26;
            case 2:
                Slog.e(TAG, "Illegal window wakelock type observed in batterystats.");
                return -1;
            default:
                Slog.e(TAG, "Illegal wakelock type in batterystats: " + battertStatsWakelockType);
                return -1;
        }
    }

    public void noteStartWakeFromSourceLocked(WorkSource ws, int pid, String name, String historyName, int type, boolean unimportantForLogging) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        int N = ws.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= N) {
                break;
            }
            noteStartWakeLocked(ws.get(i3), pid, (WorkSource.WorkChain) null, name, historyName, type, unimportantForLogging, elapsedRealtime, uptime);
            i2 = i3 + 1;
            N = N;
        }
        List<WorkSource.WorkChain> wcs = ws.getWorkChains();
        if (wcs != null) {
            while (true) {
                int i4 = i;
                if (i4 >= wcs.size()) {
                    break;
                }
                WorkSource.WorkChain wc = wcs.get(i4);
                noteStartWakeLocked(wc.getAttributionUid(), pid, wc, name, historyName, type, unimportantForLogging, elapsedRealtime, uptime);
                i = i4 + 1;
                wcs = wcs;
            }
        }
    }

    public void noteChangeWakelockFromSourceLocked(WorkSource ws, int pid, String name, String historyName, int type, WorkSource newWs, int newPid, String newName, String newHistoryName, int newType, boolean newUnimportantForLogging) {
        List<WorkSource.WorkChain> goneChains;
        List<WorkSource.WorkChain> newChains;
        WorkSource workSource = ws;
        WorkSource workSource2 = newWs;
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        List<WorkSource.WorkChain>[] wcs = WorkSource.diffChains(workSource, workSource2);
        int NN = newWs.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= NN) {
                break;
            }
            noteStartWakeLocked(workSource2.get(i3), newPid, (WorkSource.WorkChain) null, newName, newHistoryName, newType, newUnimportantForLogging, elapsedRealtime, uptime);
            i2 = i3 + 1;
            NN = NN;
        }
        if (wcs != null && (newChains = wcs[0]) != null) {
            int i4 = 0;
            while (true) {
                int i5 = i4;
                if (i5 >= newChains.size()) {
                    break;
                }
                WorkSource.WorkChain newChain = newChains.get(i5);
                noteStartWakeLocked(newChain.getAttributionUid(), newPid, newChain, newName, newHistoryName, newType, newUnimportantForLogging, elapsedRealtime, uptime);
                i4 = i5 + 1;
                newChains = newChains;
            }
        }
        int NO = ws.size();
        int i6 = 0;
        while (true) {
            int i7 = i6;
            if (i7 >= NO) {
                break;
            }
            noteStopWakeLocked(workSource.get(i7), pid, (WorkSource.WorkChain) null, name, historyName, type, elapsedRealtime, uptime);
            i6 = i7 + 1;
        }
        if (wcs != null && (goneChains = wcs[1]) != null) {
            while (true) {
                int i8 = i;
                if (i8 < goneChains.size()) {
                    WorkSource.WorkChain goneChain = goneChains.get(i8);
                    noteStopWakeLocked(goneChain.getAttributionUid(), pid, goneChain, name, historyName, type, elapsedRealtime, uptime);
                    i = i8 + 1;
                    goneChains = goneChains;
                } else {
                    return;
                }
            }
        }
    }

    public void noteStopWakeFromSourceLocked(WorkSource ws, int pid, String name, String historyName, int type) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        int N = ws.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= N) {
                break;
            }
            noteStopWakeLocked(ws.get(i3), pid, (WorkSource.WorkChain) null, name, historyName, type, elapsedRealtime, uptime);
            i2 = i3 + 1;
            N = N;
        }
        List<WorkSource.WorkChain> wcs = ws.getWorkChains();
        if (wcs != null) {
            while (true) {
                int i4 = i;
                if (i4 >= wcs.size()) {
                    break;
                }
                WorkSource.WorkChain wc = wcs.get(i4);
                noteStopWakeLocked(wc.getAttributionUid(), pid, wc, name, historyName, type, elapsedRealtime, uptime);
                i = i4 + 1;
                wcs = wcs;
            }
        }
    }

    public void noteLongPartialWakelockStart(String name, String historyName, int uid) {
        noteLongPartialWakeLockStartInternal(name, historyName, mapUid(uid));
    }

    public void noteLongPartialWakelockStartFromSource(String name, String historyName, WorkSource workSource) {
        int N = workSource.size();
        for (int i = 0; i < N; i++) {
            noteLongPartialWakeLockStartInternal(name, historyName, mapUid(workSource.get(i)));
        }
        ArrayList<WorkSource.WorkChain> workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteLongPartialWakeLockStartInternal(name, historyName, workChains.get(i2).getAttributionUid());
            }
        }
    }

    private void noteLongPartialWakeLockStartInternal(String name, String historyName, int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        String historyName2 = historyName == null ? name : historyName;
        if (this.mActiveEvents.updateState(BatteryStats.HistoryItem.EVENT_LONG_WAKE_LOCK_START, historyName2, uid, 0)) {
            addHistoryEventLocked(elapsedRealtime, uptime, BatteryStats.HistoryItem.EVENT_LONG_WAKE_LOCK_START, historyName2, uid);
        }
    }

    public void noteLongPartialWakelockFinish(String name, String historyName, int uid) {
        noteLongPartialWakeLockFinishInternal(name, historyName, mapUid(uid));
    }

    public void noteLongPartialWakelockFinishFromSource(String name, String historyName, WorkSource workSource) {
        int N = workSource.size();
        for (int i = 0; i < N; i++) {
            noteLongPartialWakeLockFinishInternal(name, historyName, mapUid(workSource.get(i)));
        }
        ArrayList<WorkSource.WorkChain> workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteLongPartialWakeLockFinishInternal(name, historyName, workChains.get(i2).getAttributionUid());
            }
        }
    }

    private void noteLongPartialWakeLockFinishInternal(String name, String historyName, int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        String historyName2 = historyName == null ? name : historyName;
        if (this.mActiveEvents.updateState(BatteryStats.HistoryItem.EVENT_LONG_WAKE_LOCK_FINISH, historyName2, uid, 0)) {
            addHistoryEventLocked(elapsedRealtime, uptime, BatteryStats.HistoryItem.EVENT_LONG_WAKE_LOCK_FINISH, historyName2, uid);
        }
    }

    /* access modifiers changed from: package-private */
    public void aggregateLastWakeupUptimeLocked(long uptimeMs) {
        if (this.mLastWakeupReason != null) {
            long deltaUptime = uptimeMs - this.mLastWakeupUptimeMs;
            getWakeupReasonTimerLocked(this.mLastWakeupReason).add(deltaUptime * 1000, 1);
            StatsLog.write(36, this.mLastWakeupReason, 1000 * deltaUptime);
            this.mLastWakeupReason = null;
        }
    }

    public void noteWakeupReasonLocked(String reason) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        aggregateLastWakeupUptimeLocked(uptime);
        this.mHistoryCur.wakeReasonTag = this.mHistoryCur.localWakeReasonTag;
        this.mHistoryCur.wakeReasonTag.string = reason;
        this.mHistoryCur.wakeReasonTag.uid = 0;
        this.mLastWakeupReason = reason;
        this.mLastWakeupUptimeMs = uptime;
        addHistoryRecordLocked(elapsedRealtime, uptime);
    }

    public boolean startAddingCpuLocked() {
        this.mExternalSync.cancelCpuSyncDueToWakelockChange();
        return this.mOnBatteryInternal;
    }

    public void finishAddingCpuLocked(int totalUTime, int totalSTime, int statUserTime, int statSystemTime, int statIOWaitTime, int statIrqTime, int statSoftIrqTime, int statIdleTime) {
        this.mCurStepCpuUserTime += (long) totalUTime;
        this.mCurStepCpuSystemTime += (long) totalSTime;
        this.mCurStepStatUserTime += (long) statUserTime;
        this.mCurStepStatSystemTime += (long) statSystemTime;
        this.mCurStepStatIOWaitTime += (long) statIOWaitTime;
        this.mCurStepStatIrqTime += (long) statIrqTime;
        this.mCurStepStatSoftIrqTime += (long) statSoftIrqTime;
        this.mCurStepStatIdleTime += (long) statIdleTime;
    }

    public void noteProcessDiedLocked(int uid, int pid) {
        Uid u = this.mUidStats.get(mapUid(uid));
        if (u != null) {
            u.mPids.remove(pid);
        }
    }

    public long getProcessWakeTime(int uid, int pid, long realtime) {
        BatteryStats.Uid.Pid p;
        Uid u = this.mUidStats.get(mapUid(uid));
        long j = 0;
        if (u == null || (p = u.mPids.get(pid)) == null) {
            return 0;
        }
        long j2 = p.mWakeSumMs;
        if (p.mWakeNesting > 0) {
            j = realtime - p.mWakeStartMs;
        }
        return j2 + j;
    }

    public void reportExcessiveCpuLocked(int uid, String proc, long overTime, long usedTime) {
        Uid u = this.mUidStats.get(mapUid(uid));
        if (u != null) {
            u.reportExcessiveCpuLocked(proc, overTime, usedTime);
        }
    }

    public void noteStartSensorLocked(int uid, int sensor) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mSensorNesting == 0) {
            this.mHistoryCur.states |= 8388608;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        this.mSensorNesting++;
        getUidStatsLocked(uid2).noteStartSensor(sensor, elapsedRealtime);
    }

    public void noteStopSensorLocked(int uid, int sensor) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        this.mSensorNesting--;
        if (this.mSensorNesting == 0) {
            this.mHistoryCur.states &= -8388609;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        getUidStatsLocked(uid2).noteStopSensor(sensor, elapsedRealtime);
    }

    public void noteGpsChangedLocked(WorkSource oldWs, WorkSource newWs) {
        for (int i = 0; i < newWs.size(); i++) {
            noteStartGpsLocked(newWs.get(i), (WorkSource.WorkChain) null);
        }
        for (int i2 = 0; i2 < oldWs.size(); i2++) {
            noteStopGpsLocked(oldWs.get(i2), (WorkSource.WorkChain) null);
        }
        List<WorkSource.WorkChain>[] wcs = WorkSource.diffChains(oldWs, newWs);
        if (wcs != null) {
            if (wcs[0] != null) {
                List<WorkSource.WorkChain> newChains = wcs[0];
                for (int i3 = 0; i3 < newChains.size(); i3++) {
                    noteStartGpsLocked(-1, newChains.get(i3));
                }
            }
            if (wcs[1] != null) {
                List<WorkSource.WorkChain> goneChains = wcs[1];
                for (int i4 = 0; i4 < goneChains.size(); i4++) {
                    noteStopGpsLocked(-1, goneChains.get(i4));
                }
            }
        }
    }

    private void noteStartGpsLocked(int uid, WorkSource.WorkChain workChain) {
        int uid2 = getAttributionUid(uid, workChain);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mGpsNesting == 0) {
            this.mHistoryCur.states |= 536870912;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        this.mGpsNesting++;
        if (workChain == null) {
            StatsLog.write_non_chained(6, uid2, (String) null, 1);
        } else {
            StatsLog.write(6, workChain.getUids(), workChain.getTags(), 1);
        }
        getUidStatsLocked(uid2).noteStartGps(elapsedRealtime);
    }

    private void noteStopGpsLocked(int uid, WorkSource.WorkChain workChain) {
        int uid2 = getAttributionUid(uid, workChain);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        this.mGpsNesting--;
        if (this.mGpsNesting == 0) {
            this.mHistoryCur.states &= -536870913;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            stopAllGpsSignalQualityTimersLocked(-1);
            this.mGpsSignalQualityBin = -1;
        }
        if (workChain == null) {
            StatsLog.write_non_chained(6, uid2, (String) null, 0);
        } else {
            StatsLog.write(6, workChain.getUids(), workChain.getTags(), 0);
        }
        getUidStatsLocked(uid2).noteStopGps(elapsedRealtime);
    }

    public void noteGpsSignalQualityLocked(int signalLevel) {
        if (this.mGpsNesting != 0) {
            if (signalLevel < 0 || signalLevel >= 2) {
                stopAllGpsSignalQualityTimersLocked(-1);
                return;
            }
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            if (this.mGpsSignalQualityBin != signalLevel) {
                if (this.mGpsSignalQualityBin >= 0) {
                    this.mGpsSignalQualityTimer[this.mGpsSignalQualityBin].stopRunningLocked(elapsedRealtime);
                }
                if (!this.mGpsSignalQualityTimer[signalLevel].isRunningLocked()) {
                    this.mGpsSignalQualityTimer[signalLevel].startRunningLocked(elapsedRealtime);
                }
                this.mHistoryCur.states2 = (this.mHistoryCur.states2 & -129) | (signalLevel << 7);
                addHistoryRecordLocked(elapsedRealtime, uptime);
                this.mGpsSignalQualityBin = signalLevel;
            }
        }
    }

    @GuardedBy({"this"})
    public void noteScreenStateLocked(int state) {
        int state2 = this.mPretendScreenOff ? 1 : state;
        if (state2 > 4) {
            if (state2 != 5) {
                Slog.wtf(TAG, "Unknown screen state (not mapped): " + state2);
            } else {
                state2 = 2;
            }
        }
        int state3 = state2;
        if (this.mScreenState != state3) {
            recordDailyStatsIfNeededLocked(true);
            int oldState = this.mScreenState;
            this.mScreenState = state3;
            if (state3 != 0) {
                int stepState = state3 - 1;
                if ((stepState & 3) == stepState) {
                    this.mModStepMode |= (this.mCurStepMode & 3) ^ stepState;
                    this.mCurStepMode = (this.mCurStepMode & -4) | stepState;
                } else {
                    Slog.wtf(TAG, "Unexpected screen state: " + state3);
                }
            }
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            boolean updateHistory = false;
            if (isScreenDoze(state3)) {
                this.mHistoryCur.states |= 262144;
                this.mScreenDozeTimer.startRunningLocked(elapsedRealtime);
                updateHistory = true;
            } else if (isScreenDoze(oldState)) {
                this.mHistoryCur.states &= -262145;
                this.mScreenDozeTimer.stopRunningLocked(elapsedRealtime);
                updateHistory = true;
            }
            if (isScreenOn(state3)) {
                this.mHistoryCur.states |= 1048576;
                this.mScreenOnTimer.startRunningLocked(elapsedRealtime);
                if (this.mScreenBrightnessBin >= 0) {
                    this.mScreenBrightnessTimer[this.mScreenBrightnessBin].startRunningLocked(elapsedRealtime);
                }
                updateHistory = true;
            } else if (isScreenOn(oldState)) {
                this.mHistoryCur.states &= -1048577;
                this.mScreenOnTimer.stopRunningLocked(elapsedRealtime);
                if (this.mScreenBrightnessBin >= 0) {
                    this.mScreenBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(elapsedRealtime);
                }
                updateHistory = true;
            }
            if (updateHistory) {
                addHistoryRecordLocked(elapsedRealtime, uptime);
            }
            this.mExternalSync.scheduleCpuSyncDueToScreenStateChange(this.mOnBatteryTimeBase.isRunning(), this.mOnBatteryScreenOffTimeBase.isRunning());
            if (isScreenOn(state3)) {
                updateTimeBasesLocked(this.mOnBatteryTimeBase.isRunning(), state3, this.mClocks.uptimeMillis() * 1000, elapsedRealtime * 1000);
                long j = elapsedRealtime;
                noteStartWakeLocked(-1, -1, (WorkSource.WorkChain) null, "screen", (String) null, 0, false, elapsedRealtime, uptime);
            } else {
                long uptime2 = uptime;
                long elapsedRealtime2 = elapsedRealtime;
                if (isScreenOn(oldState)) {
                    noteStopWakeLocked(-1, -1, (WorkSource.WorkChain) null, "screen", "screen", 0, elapsedRealtime2, uptime2);
                    updateTimeBasesLocked(this.mOnBatteryTimeBase.isRunning(), state3, this.mClocks.uptimeMillis() * 1000, elapsedRealtime2 * 1000);
                }
            }
            if (this.mOnBatteryInternal) {
                updateDischargeScreenLevelsLocked(oldState, state3);
            }
        }
    }

    @UnsupportedAppUsage
    public void noteScreenBrightnessLocked(int brightness) {
        int bin = brightness / 51;
        if (bin < 0) {
            bin = 0;
        } else if (bin >= 5) {
            bin = 4;
        }
        if (this.mScreenBrightnessBin != bin) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states = (this.mHistoryCur.states & -8) | (bin << 0);
            addHistoryRecordLocked(elapsedRealtime, uptime);
            if (this.mScreenState == 2) {
                if (this.mScreenBrightnessBin >= 0) {
                    this.mScreenBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(elapsedRealtime);
                }
                this.mScreenBrightnessTimer[bin].startRunningLocked(elapsedRealtime);
            }
            this.mScreenBrightnessBin = bin;
        }
    }

    @UnsupportedAppUsage
    public void noteUserActivityLocked(int uid, int event) {
        if (this.mOnBatteryInternal) {
            getUidStatsLocked(mapUid(uid)).noteUserActivityLocked(event);
        }
    }

    public void noteWakeUpLocked(String reason, int reasonUid) {
        addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 18, reason, reasonUid);
    }

    public void noteInteractiveLocked(boolean interactive) {
        if (this.mInteractive != interactive) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            this.mInteractive = interactive;
            if (interactive) {
                this.mInteractiveTimer.startRunningLocked(elapsedRealtime);
            } else {
                this.mInteractiveTimer.stopRunningLocked(elapsedRealtime);
            }
        }
    }

    public void noteConnectivityChangedLocked(int type, String extra) {
        addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 9, extra, type);
        this.mNumConnectivityChange++;
    }

    private void noteMobileRadioApWakeupLocked(long elapsedRealtimeMillis, long uptimeMillis, int uid) {
        int uid2 = mapUid(uid);
        addHistoryEventLocked(elapsedRealtimeMillis, uptimeMillis, 19, "", uid2);
        getUidStatsLocked(uid2).noteMobileRadioApWakeupLocked();
    }

    public boolean noteMobileRadioPowerStateLocked(int powerState, long timestampNs, int uid) {
        long realElapsedRealtimeMs;
        int i = powerState;
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mMobileRadioPowerState == i) {
            return false;
        }
        boolean active = i == 2 || i == 3;
        if (active) {
            if (uid > 0) {
                noteMobileRadioApWakeupLocked(elapsedRealtime, uptime, uid);
            }
            long j = timestampNs / TimeUtils.NANOS_PER_MS;
            realElapsedRealtimeMs = j;
            this.mMobileRadioActiveStartTime = j;
            this.mHistoryCur.states |= 33554432;
        } else {
            long realElapsedRealtimeMs2 = timestampNs / TimeUtils.NANOS_PER_MS;
            long lastUpdateTimeMs = this.mMobileRadioActiveStartTime;
            if (realElapsedRealtimeMs2 < lastUpdateTimeMs) {
                Slog.wtf(TAG, "Data connection inactive timestamp " + realElapsedRealtimeMs2 + " is before start time " + lastUpdateTimeMs);
                realElapsedRealtimeMs2 = elapsedRealtime;
                long j2 = lastUpdateTimeMs;
            } else if (realElapsedRealtimeMs2 < elapsedRealtime) {
                long j3 = lastUpdateTimeMs;
                this.mMobileRadioActiveAdjustedTime.addCountLocked(elapsedRealtime - realElapsedRealtimeMs2);
            }
            realElapsedRealtimeMs = realElapsedRealtimeMs2;
            this.mHistoryCur.states &= -33554433;
        }
        addHistoryRecordLocked(elapsedRealtime, uptime);
        this.mMobileRadioPowerState = i;
        if (active) {
            this.mMobileRadioActiveTimer.startRunningLocked(elapsedRealtime);
            this.mMobileRadioActivePerAppTimer.startRunningLocked(elapsedRealtime);
            return false;
        }
        this.mMobileRadioActiveTimer.stopRunningLocked(realElapsedRealtimeMs);
        this.mMobileRadioActivePerAppTimer.stopRunningLocked(realElapsedRealtimeMs);
        return true;
    }

    public void notePowerSaveModeLocked(boolean enabled) {
        if (this.mPowerSaveModeEnabled != enabled) {
            int i = 0;
            int stepState = enabled ? 4 : 0;
            this.mModStepMode = ((4 & this.mCurStepMode) ^ stepState) | this.mModStepMode;
            this.mCurStepMode = (this.mCurStepMode & -5) | stepState;
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mPowerSaveModeEnabled = enabled;
            if (enabled) {
                this.mHistoryCur.states2 |= Integer.MIN_VALUE;
                this.mPowerSaveModeEnabledTimer.startRunningLocked(elapsedRealtime);
            } else {
                this.mHistoryCur.states2 &= Integer.MAX_VALUE;
                this.mPowerSaveModeEnabledTimer.stopRunningLocked(elapsedRealtime);
            }
            addHistoryRecordLocked(elapsedRealtime, uptime);
            if (enabled) {
                i = 1;
            }
            StatsLog.write(20, i);
        }
    }

    public void noteDeviceIdleModeLocked(int mode, String activeReason, int activeUid) {
        boolean nowIdling;
        boolean nowLightIdling;
        int i;
        int statsmode;
        int i2 = mode;
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        int i3 = 0;
        boolean nowIdling2 = i2 == 2;
        if (this.mDeviceIdling && !nowIdling2 && activeReason == null) {
            nowIdling2 = true;
        }
        boolean nowIdling3 = nowIdling2;
        boolean nowLightIdling2 = i2 == 1;
        if (this.mDeviceLightIdling && !nowLightIdling2 && !nowIdling3 && activeReason == null) {
            nowLightIdling2 = true;
        }
        boolean nowLightIdling3 = nowLightIdling2;
        if (activeReason == null) {
            nowLightIdling = nowLightIdling3;
            nowIdling = nowIdling3;
            i = 1;
        } else if (this.mDeviceIdling || this.mDeviceLightIdling) {
            nowLightIdling = nowLightIdling3;
            nowIdling = nowIdling3;
            i = 1;
            addHistoryEventLocked(elapsedRealtime, uptime, 10, activeReason, activeUid);
        } else {
            nowLightIdling = nowLightIdling3;
            nowIdling = nowIdling3;
            i = 1;
        }
        boolean nowIdling4 = nowIdling;
        if (!(this.mDeviceIdling == nowIdling4 && this.mDeviceLightIdling == nowLightIdling)) {
            if (nowIdling4) {
                statsmode = 2;
            } else if (nowLightIdling) {
                statsmode = 1;
            } else {
                statsmode = 0;
            }
            StatsLog.write(22, statsmode);
        }
        if (this.mDeviceIdling != nowIdling4) {
            this.mDeviceIdling = nowIdling4;
            if (nowIdling4) {
                i3 = 8;
            }
            int stepState = i3;
            this.mModStepMode = ((8 & this.mCurStepMode) ^ stepState) | this.mModStepMode;
            this.mCurStepMode = (this.mCurStepMode & -9) | stepState;
            if (nowIdling4) {
                this.mDeviceIdlingTimer.startRunningLocked(elapsedRealtime);
            } else {
                this.mDeviceIdlingTimer.stopRunningLocked(elapsedRealtime);
            }
        }
        if (this.mDeviceLightIdling != nowLightIdling) {
            this.mDeviceLightIdling = nowLightIdling;
            if (nowLightIdling) {
                this.mDeviceLightIdlingTimer.startRunningLocked(elapsedRealtime);
            } else {
                this.mDeviceLightIdlingTimer.stopRunningLocked(elapsedRealtime);
            }
        }
        if (this.mDeviceIdleMode != i2) {
            this.mHistoryCur.states2 = (this.mHistoryCur.states2 & -100663297) | (i2 << 25);
            addHistoryRecordLocked(elapsedRealtime, uptime);
            long lastDuration = elapsedRealtime - this.mLastIdleTimeStart;
            this.mLastIdleTimeStart = elapsedRealtime;
            if (this.mDeviceIdleMode == i) {
                if (lastDuration > this.mLongestLightIdleTime) {
                    this.mLongestLightIdleTime = lastDuration;
                }
                this.mDeviceIdleModeLightTimer.stopRunningLocked(elapsedRealtime);
            } else if (this.mDeviceIdleMode == 2) {
                if (lastDuration > this.mLongestFullIdleTime) {
                    this.mLongestFullIdleTime = lastDuration;
                }
                this.mDeviceIdleModeFullTimer.stopRunningLocked(elapsedRealtime);
            }
            if (i2 == i) {
                this.mDeviceIdleModeLightTimer.startRunningLocked(elapsedRealtime);
            } else if (i2 == 2) {
                this.mDeviceIdleModeFullTimer.startRunningLocked(elapsedRealtime);
            }
            this.mDeviceIdleMode = i2;
            StatsLog.write(21, i2);
        }
    }

    public void notePackageInstalledLocked(String pkgName, long versionCode) {
        long j = versionCode;
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        addHistoryEventLocked(elapsedRealtime, this.mClocks.uptimeMillis(), 11, pkgName, (int) j);
        BatteryStats.PackageChange pc = new BatteryStats.PackageChange();
        pc.mPackageName = pkgName;
        pc.mUpdate = true;
        pc.mVersionCode = j;
        addPackageChange(pc);
    }

    public void notePackageUninstalledLocked(String pkgName) {
        addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 12, pkgName, 0);
        BatteryStats.PackageChange pc = new BatteryStats.PackageChange();
        pc.mPackageName = pkgName;
        pc.mUpdate = true;
        addPackageChange(pc);
    }

    private void addPackageChange(BatteryStats.PackageChange pc) {
        if (this.mDailyPackageChanges == null) {
            this.mDailyPackageChanges = new ArrayList<>();
        }
        this.mDailyPackageChanges.add(pc);
    }

    /* access modifiers changed from: package-private */
    public void stopAllGpsSignalQualityTimersLocked(int except) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        for (int i = 0; i < 2; i++) {
            if (i != except) {
                while (this.mGpsSignalQualityTimer[i].isRunningLocked()) {
                    this.mGpsSignalQualityTimer[i].stopRunningLocked(elapsedRealtime);
                }
            }
        }
    }

    @UnsupportedAppUsage
    public void notePhoneOnLocked() {
        if (!this.mPhoneOn) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states2 |= 8388608;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mPhoneOn = true;
            this.mPhoneOnTimer.startRunningLocked(elapsedRealtime);
        }
    }

    @UnsupportedAppUsage
    public void notePhoneOffLocked() {
        if (this.mPhoneOn) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states2 &= -8388609;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mPhoneOn = false;
            this.mPhoneOnTimer.stopRunningLocked(elapsedRealtime);
        }
    }

    private void registerUsbStateReceiver(Context context) {
        IntentFilter usbStateFilter = new IntentFilter();
        usbStateFilter.addAction(UsbManager.ACTION_USB_STATE);
        context.registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                boolean state = intent.getBooleanExtra("connected", false);
                synchronized (BatteryStatsImpl.this) {
                    BatteryStatsImpl.this.noteUsbConnectionStateLocked(state);
                }
            }
        }, usbStateFilter);
        synchronized (this) {
            if (this.mUsbDataState == 0) {
                Intent usbState = context.registerReceiver((BroadcastReceiver) null, usbStateFilter);
                boolean initState = false;
                if (usbState != null && usbState.getBooleanExtra("connected", false)) {
                    initState = true;
                }
                noteUsbConnectionStateLocked(initState);
            }
        }
    }

    /* access modifiers changed from: private */
    public void noteUsbConnectionStateLocked(boolean connected) {
        int newState = connected ? 2 : 1;
        if (this.mUsbDataState != newState) {
            this.mUsbDataState = newState;
            if (connected) {
                this.mHistoryCur.states2 |= 262144;
            } else {
                this.mHistoryCur.states2 &= -262145;
            }
            addHistoryRecordLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
        }
    }

    /* access modifiers changed from: package-private */
    public void stopAllPhoneSignalStrengthTimersLocked(int except) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        for (int i = 0; i < 5; i++) {
            if (i != except) {
                while (this.mPhoneSignalStrengthsTimer[i].isRunningLocked()) {
                    this.mPhoneSignalStrengthsTimer[i].stopRunningLocked(elapsedRealtime);
                }
            }
        }
    }

    private int fixPhoneServiceState(int state, int signalBin) {
        if (this.mPhoneSimStateRaw == 1 && state == 1 && signalBin > 0) {
            return 0;
        }
        return state;
    }

    private void updateAllPhoneStateLocked(int state, int simState, int strengthBin) {
        boolean scanning = false;
        boolean newHistory = false;
        this.mPhoneServiceStateRaw = state;
        this.mPhoneSimStateRaw = simState;
        this.mPhoneSignalStrengthBinRaw = strengthBin;
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (simState == 1 && state == 1 && strengthBin > 0) {
            state = 0;
        }
        if (state == 3) {
            strengthBin = -1;
        } else if (state != 0 && state == 1) {
            scanning = true;
            strengthBin = 0;
            if (!this.mPhoneSignalScanningTimer.isRunningLocked()) {
                this.mHistoryCur.states |= 2097152;
                newHistory = true;
                this.mPhoneSignalScanningTimer.startRunningLocked(elapsedRealtime);
                StatsLog.write(94, state, simState, 0);
            }
        }
        if (!scanning && this.mPhoneSignalScanningTimer.isRunningLocked()) {
            this.mHistoryCur.states &= -2097153;
            newHistory = true;
            this.mPhoneSignalScanningTimer.stopRunningLocked(elapsedRealtime);
            StatsLog.write(94, state, simState, strengthBin);
        }
        if (this.mPhoneServiceState != state) {
            this.mHistoryCur.states = (this.mHistoryCur.states & -449) | (state << 6);
            newHistory = true;
            this.mPhoneServiceState = state;
        }
        if (this.mPhoneSignalStrengthBin != strengthBin) {
            if (this.mPhoneSignalStrengthBin >= 0) {
                this.mPhoneSignalStrengthsTimer[this.mPhoneSignalStrengthBin].stopRunningLocked(elapsedRealtime);
            }
            if (strengthBin >= 0) {
                if (!this.mPhoneSignalStrengthsTimer[strengthBin].isRunningLocked()) {
                    this.mPhoneSignalStrengthsTimer[strengthBin].startRunningLocked(elapsedRealtime);
                }
                this.mHistoryCur.states = (this.mHistoryCur.states & -57) | (strengthBin << 3);
                newHistory = true;
                StatsLog.write(40, strengthBin);
            } else {
                stopAllPhoneSignalStrengthTimersLocked(-1);
            }
            this.mPhoneSignalStrengthBin = strengthBin;
        }
        if (newHistory) {
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
    }

    public void notePhoneStateLocked(int state, int simState) {
        updateAllPhoneStateLocked(state, simState, this.mPhoneSignalStrengthBinRaw);
    }

    @UnsupportedAppUsage
    public void notePhoneSignalStrengthLocked(SignalStrength signalStrength) {
        updateAllPhoneStateLocked(this.mPhoneServiceStateRaw, this.mPhoneSimStateRaw, signalStrength.getLevel());
    }

    @UnsupportedAppUsage
    public void notePhoneDataConnectionStateLocked(int dataType, boolean hasData) {
        int bin = 0;
        if (hasData) {
            if (dataType <= 0 || dataType > 20) {
                bin = 21;
            } else {
                bin = dataType;
            }
        }
        if (this.mPhoneDataConnectionType != bin) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states = (this.mHistoryCur.states & -15873) | (bin << 9);
            addHistoryRecordLocked(elapsedRealtime, uptime);
            if (this.mPhoneDataConnectionType >= 0) {
                this.mPhoneDataConnectionsTimer[this.mPhoneDataConnectionType].stopRunningLocked(elapsedRealtime);
            }
            this.mPhoneDataConnectionType = bin;
            this.mPhoneDataConnectionsTimer[bin].startRunningLocked(elapsedRealtime);
        }
    }

    public void noteWifiOnLocked() {
        if (!this.mWifiOn) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states2 |= 268435456;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mWifiOn = true;
            this.mWifiOnTimer.startRunningLocked(elapsedRealtime);
            scheduleSyncExternalStatsLocked("wifi-off", 2);
        }
    }

    public void noteWifiOffLocked() {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mWifiOn) {
            this.mHistoryCur.states2 &= -268435457;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mWifiOn = false;
            this.mWifiOnTimer.stopRunningLocked(elapsedRealtime);
            scheduleSyncExternalStatsLocked("wifi-on", 2);
        }
    }

    @UnsupportedAppUsage
    public void noteAudioOnLocked(int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mAudioOnNesting == 0) {
            this.mHistoryCur.states |= 4194304;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mAudioOnTimer.startRunningLocked(elapsedRealtime);
        }
        this.mAudioOnNesting++;
        getUidStatsLocked(uid2).noteAudioTurnedOnLocked(elapsedRealtime);
    }

    @UnsupportedAppUsage
    public void noteAudioOffLocked(int uid) {
        if (this.mAudioOnNesting != 0) {
            int uid2 = mapUid(uid);
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            int i = this.mAudioOnNesting - 1;
            this.mAudioOnNesting = i;
            if (i == 0) {
                this.mHistoryCur.states &= -4194305;
                addHistoryRecordLocked(elapsedRealtime, uptime);
                this.mAudioOnTimer.stopRunningLocked(elapsedRealtime);
            }
            getUidStatsLocked(uid2).noteAudioTurnedOffLocked(elapsedRealtime);
        }
    }

    @UnsupportedAppUsage
    public void noteVideoOnLocked(int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mVideoOnNesting == 0) {
            this.mHistoryCur.states2 |= 1073741824;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mVideoOnTimer.startRunningLocked(elapsedRealtime);
        }
        this.mVideoOnNesting++;
        getUidStatsLocked(uid2).noteVideoTurnedOnLocked(elapsedRealtime);
    }

    @UnsupportedAppUsage
    public void noteVideoOffLocked(int uid) {
        if (this.mVideoOnNesting != 0) {
            int uid2 = mapUid(uid);
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            int i = this.mVideoOnNesting - 1;
            this.mVideoOnNesting = i;
            if (i == 0) {
                this.mHistoryCur.states2 &= -1073741825;
                addHistoryRecordLocked(elapsedRealtime, uptime);
                this.mVideoOnTimer.stopRunningLocked(elapsedRealtime);
            }
            getUidStatsLocked(uid2).noteVideoTurnedOffLocked(elapsedRealtime);
        }
    }

    public void noteResetAudioLocked() {
        if (this.mAudioOnNesting > 0) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mAudioOnNesting = 0;
            this.mHistoryCur.states &= -4194305;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mAudioOnTimer.stopAllRunningLocked(elapsedRealtime);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetAudioLocked(elapsedRealtime);
            }
        }
    }

    public void noteResetVideoLocked() {
        if (this.mVideoOnNesting > 0) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mAudioOnNesting = 0;
            this.mHistoryCur.states2 &= -1073741825;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mVideoOnTimer.stopAllRunningLocked(elapsedRealtime);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetVideoLocked(elapsedRealtime);
            }
        }
    }

    public void noteActivityResumedLocked(int uid) {
        getUidStatsLocked(mapUid(uid)).noteActivityResumedLocked(this.mClocks.elapsedRealtime());
    }

    public void noteActivityPausedLocked(int uid) {
        getUidStatsLocked(mapUid(uid)).noteActivityPausedLocked(this.mClocks.elapsedRealtime());
    }

    public void noteVibratorOnLocked(int uid, long durationMillis) {
        getUidStatsLocked(mapUid(uid)).noteVibratorOnLocked(durationMillis);
    }

    public void noteVibratorOffLocked(int uid) {
        getUidStatsLocked(mapUid(uid)).noteVibratorOffLocked();
    }

    public void noteFlashlightOnLocked(int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        int i = this.mFlashlightOnNesting;
        this.mFlashlightOnNesting = i + 1;
        if (i == 0) {
            this.mHistoryCur.states2 |= 134217728;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mFlashlightOnTimer.startRunningLocked(elapsedRealtime);
        }
        getUidStatsLocked(uid2).noteFlashlightTurnedOnLocked(elapsedRealtime);
    }

    public void noteFlashlightOffLocked(int uid) {
        if (this.mFlashlightOnNesting != 0) {
            int uid2 = mapUid(uid);
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            int i = this.mFlashlightOnNesting - 1;
            this.mFlashlightOnNesting = i;
            if (i == 0) {
                this.mHistoryCur.states2 &= -134217729;
                addHistoryRecordLocked(elapsedRealtime, uptime);
                this.mFlashlightOnTimer.stopRunningLocked(elapsedRealtime);
            }
            getUidStatsLocked(uid2).noteFlashlightTurnedOffLocked(elapsedRealtime);
        }
    }

    public void noteCameraOnLocked(int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        int i = this.mCameraOnNesting;
        this.mCameraOnNesting = i + 1;
        if (i == 0) {
            this.mHistoryCur.states2 |= 2097152;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mCameraOnTimer.startRunningLocked(elapsedRealtime);
        }
        getUidStatsLocked(uid2).noteCameraTurnedOnLocked(elapsedRealtime);
    }

    public void noteCameraOffLocked(int uid) {
        if (this.mCameraOnNesting != 0) {
            int uid2 = mapUid(uid);
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            int i = this.mCameraOnNesting - 1;
            this.mCameraOnNesting = i;
            if (i == 0) {
                this.mHistoryCur.states2 &= -2097153;
                addHistoryRecordLocked(elapsedRealtime, uptime);
                this.mCameraOnTimer.stopRunningLocked(elapsedRealtime);
            }
            getUidStatsLocked(uid2).noteCameraTurnedOffLocked(elapsedRealtime);
        }
    }

    public void noteResetCameraLocked() {
        if (this.mCameraOnNesting > 0) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mCameraOnNesting = 0;
            this.mHistoryCur.states2 &= -2097153;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mCameraOnTimer.stopAllRunningLocked(elapsedRealtime);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetCameraLocked(elapsedRealtime);
            }
        }
    }

    public void noteResetFlashlightLocked() {
        if (this.mFlashlightOnNesting > 0) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mFlashlightOnNesting = 0;
            this.mHistoryCur.states2 &= -134217729;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mFlashlightOnTimer.stopAllRunningLocked(elapsedRealtime);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetFlashlightLocked(elapsedRealtime);
            }
        }
    }

    private void noteBluetoothScanStartedLocked(WorkSource.WorkChain workChain, int uid, boolean isUnoptimized) {
        int uid2 = getAttributionUid(uid, workChain);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mBluetoothScanNesting == 0) {
            this.mHistoryCur.states2 |= 1048576;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mBluetoothScanTimer.startRunningLocked(elapsedRealtime);
        }
        this.mBluetoothScanNesting++;
        getUidStatsLocked(uid2).noteBluetoothScanStartedLocked(elapsedRealtime, isUnoptimized);
    }

    public void noteBluetoothScanStartedFromSourceLocked(WorkSource ws, boolean isUnoptimized) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteBluetoothScanStartedLocked((WorkSource.WorkChain) null, ws.get(i), isUnoptimized);
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteBluetoothScanStartedLocked(workChains.get(i2), -1, isUnoptimized);
            }
        }
    }

    private void noteBluetoothScanStoppedLocked(WorkSource.WorkChain workChain, int uid, boolean isUnoptimized) {
        int uid2 = getAttributionUid(uid, workChain);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        this.mBluetoothScanNesting--;
        if (this.mBluetoothScanNesting == 0) {
            this.mHistoryCur.states2 &= -1048577;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mBluetoothScanTimer.stopRunningLocked(elapsedRealtime);
        }
        getUidStatsLocked(uid2).noteBluetoothScanStoppedLocked(elapsedRealtime, isUnoptimized);
    }

    private int getAttributionUid(int uid, WorkSource.WorkChain workChain) {
        if (workChain != null) {
            return mapUid(workChain.getAttributionUid());
        }
        return mapUid(uid);
    }

    public void noteBluetoothScanStoppedFromSourceLocked(WorkSource ws, boolean isUnoptimized) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteBluetoothScanStoppedLocked((WorkSource.WorkChain) null, ws.get(i), isUnoptimized);
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteBluetoothScanStoppedLocked(workChains.get(i2), -1, isUnoptimized);
            }
        }
    }

    public void noteResetBluetoothScanLocked() {
        if (this.mBluetoothScanNesting > 0) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mBluetoothScanNesting = 0;
            this.mHistoryCur.states2 &= -1048577;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mBluetoothScanTimer.stopAllRunningLocked(elapsedRealtime);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetBluetoothScanLocked(elapsedRealtime);
            }
        }
    }

    public void noteBluetoothScanResultsFromSourceLocked(WorkSource ws, int numNewResults) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            getUidStatsLocked(mapUid(ws.get(i))).noteBluetoothScanResultsLocked(numNewResults);
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                getUidStatsLocked(mapUid(workChains.get(i2).getAttributionUid())).noteBluetoothScanResultsLocked(numNewResults);
            }
        }
    }

    private void noteWifiRadioApWakeupLocked(long elapsedRealtimeMillis, long uptimeMillis, int uid) {
        int uid2 = mapUid(uid);
        addHistoryEventLocked(elapsedRealtimeMillis, uptimeMillis, 19, "", uid2);
        getUidStatsLocked(uid2).noteWifiRadioApWakeupLocked();
    }

    public void noteWifiRadioPowerState(int powerState, long timestampNs, int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mWifiRadioPowerState != powerState) {
            if (powerState == 2 || powerState == 3) {
                if (uid > 0) {
                    noteWifiRadioApWakeupLocked(elapsedRealtime, uptime, uid);
                }
                this.mHistoryCur.states |= 67108864;
                this.mWifiActiveTimer.startRunningLocked(elapsedRealtime);
            } else {
                this.mHistoryCur.states &= -67108865;
                this.mWifiActiveTimer.stopRunningLocked(timestampNs / TimeUtils.NANOS_PER_MS);
            }
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mWifiRadioPowerState = powerState;
        }
    }

    public void noteWifiRunningLocked(WorkSource ws) {
        if (!this.mGlobalWifiRunning) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states2 |= 536870912;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mGlobalWifiRunning = true;
            this.mGlobalWifiRunningTimer.startRunningLocked(elapsedRealtime);
            int N = ws.size();
            for (int i = 0; i < N; i++) {
                getUidStatsLocked(mapUid(ws.get(i))).noteWifiRunningLocked(elapsedRealtime);
            }
            List<WorkSource.WorkChain> workChains = ws.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    getUidStatsLocked(mapUid(workChains.get(i2).getAttributionUid())).noteWifiRunningLocked(elapsedRealtime);
                }
            }
            scheduleSyncExternalStatsLocked("wifi-running", 2);
            return;
        }
        Log.w(TAG, "noteWifiRunningLocked -- called while WIFI running");
    }

    public void noteWifiRunningChangedLocked(WorkSource oldWs, WorkSource newWs) {
        if (this.mGlobalWifiRunning) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            int N = oldWs.size();
            for (int i = 0; i < N; i++) {
                getUidStatsLocked(mapUid(oldWs.get(i))).noteWifiStoppedLocked(elapsedRealtime);
            }
            List<WorkSource.WorkChain> workChains = oldWs.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    getUidStatsLocked(mapUid(workChains.get(i2).getAttributionUid())).noteWifiStoppedLocked(elapsedRealtime);
                }
            }
            int N2 = newWs.size();
            for (int i3 = 0; i3 < N2; i3++) {
                getUidStatsLocked(mapUid(newWs.get(i3))).noteWifiRunningLocked(elapsedRealtime);
            }
            List<WorkSource.WorkChain> workChains2 = newWs.getWorkChains();
            if (workChains2 != null) {
                for (int i4 = 0; i4 < workChains2.size(); i4++) {
                    getUidStatsLocked(mapUid(workChains2.get(i4).getAttributionUid())).noteWifiRunningLocked(elapsedRealtime);
                }
                return;
            }
            return;
        }
        Log.w(TAG, "noteWifiRunningChangedLocked -- called while WIFI not running");
    }

    public void noteWifiStoppedLocked(WorkSource ws) {
        if (this.mGlobalWifiRunning) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            this.mHistoryCur.states2 &= -536870913;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            this.mGlobalWifiRunning = false;
            this.mGlobalWifiRunningTimer.stopRunningLocked(elapsedRealtime);
            int N = ws.size();
            for (int i = 0; i < N; i++) {
                getUidStatsLocked(mapUid(ws.get(i))).noteWifiStoppedLocked(elapsedRealtime);
            }
            List<WorkSource.WorkChain> workChains = ws.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    getUidStatsLocked(mapUid(workChains.get(i2).getAttributionUid())).noteWifiStoppedLocked(elapsedRealtime);
                }
            }
            scheduleSyncExternalStatsLocked("wifi-stopped", 2);
            return;
        }
        Log.w(TAG, "noteWifiStoppedLocked -- called while WIFI not running");
    }

    public void noteWifiStateLocked(int wifiState, String accessPoint) {
        if (this.mWifiState != wifiState) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            if (this.mWifiState >= 0) {
                this.mWifiStateTimer[this.mWifiState].stopRunningLocked(elapsedRealtime);
            }
            this.mWifiState = wifiState;
            this.mWifiStateTimer[wifiState].startRunningLocked(elapsedRealtime);
            scheduleSyncExternalStatsLocked("wifi-state", 2);
        }
    }

    public void noteWifiSupplicantStateChangedLocked(int supplState, boolean failedAuth) {
        if (this.mWifiSupplState != supplState) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            if (this.mWifiSupplState >= 0) {
                this.mWifiSupplStateTimer[this.mWifiSupplState].stopRunningLocked(elapsedRealtime);
            }
            this.mWifiSupplState = supplState;
            this.mWifiSupplStateTimer[supplState].startRunningLocked(elapsedRealtime);
            this.mHistoryCur.states2 = (this.mHistoryCur.states2 & -16) | (supplState << 0);
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
    }

    /* access modifiers changed from: package-private */
    public void stopAllWifiSignalStrengthTimersLocked(int except) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        for (int i = 0; i < 5; i++) {
            if (i != except) {
                while (this.mWifiSignalStrengthsTimer[i].isRunningLocked()) {
                    this.mWifiSignalStrengthsTimer[i].stopRunningLocked(elapsedRealtime);
                }
            }
        }
    }

    public void noteWifiRssiChangedLocked(int newRssi) {
        int strengthBin = WifiManager.calculateSignalLevel(newRssi, 5);
        if (this.mWifiSignalStrengthBin != strengthBin) {
            long elapsedRealtime = this.mClocks.elapsedRealtime();
            long uptime = this.mClocks.uptimeMillis();
            if (this.mWifiSignalStrengthBin >= 0) {
                this.mWifiSignalStrengthsTimer[this.mWifiSignalStrengthBin].stopRunningLocked(elapsedRealtime);
            }
            if (strengthBin >= 0) {
                if (!this.mWifiSignalStrengthsTimer[strengthBin].isRunningLocked()) {
                    this.mWifiSignalStrengthsTimer[strengthBin].startRunningLocked(elapsedRealtime);
                }
                this.mHistoryCur.states2 = (this.mHistoryCur.states2 & -113) | (strengthBin << 4);
                addHistoryRecordLocked(elapsedRealtime, uptime);
            } else {
                stopAllWifiSignalStrengthTimersLocked(-1);
            }
            this.mWifiSignalStrengthBin = strengthBin;
        }
    }

    @UnsupportedAppUsage
    public void noteFullWifiLockAcquiredLocked(int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mWifiFullLockNesting == 0) {
            this.mHistoryCur.states |= 268435456;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        this.mWifiFullLockNesting++;
        getUidStatsLocked(uid).noteFullWifiLockAcquiredLocked(elapsedRealtime);
    }

    @UnsupportedAppUsage
    public void noteFullWifiLockReleasedLocked(int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        this.mWifiFullLockNesting--;
        if (this.mWifiFullLockNesting == 0) {
            this.mHistoryCur.states &= -268435457;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        getUidStatsLocked(uid).noteFullWifiLockReleasedLocked(elapsedRealtime);
    }

    public void noteWifiScanStartedLocked(int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mWifiScanNesting == 0) {
            this.mHistoryCur.states |= 134217728;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        this.mWifiScanNesting++;
        getUidStatsLocked(uid).noteWifiScanStartedLocked(elapsedRealtime);
    }

    public void noteWifiScanStoppedLocked(int uid) {
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        this.mWifiScanNesting--;
        if (this.mWifiScanNesting == 0) {
            this.mHistoryCur.states &= -134217729;
            addHistoryRecordLocked(elapsedRealtime, uptime);
        }
        getUidStatsLocked(uid).noteWifiScanStoppedLocked(elapsedRealtime);
    }

    public void noteWifiBatchedScanStartedLocked(int uid, int csph) {
        int uid2 = mapUid(uid);
        getUidStatsLocked(uid2).noteWifiBatchedScanStartedLocked(csph, this.mClocks.elapsedRealtime());
    }

    public void noteWifiBatchedScanStoppedLocked(int uid) {
        int uid2 = mapUid(uid);
        getUidStatsLocked(uid2).noteWifiBatchedScanStoppedLocked(this.mClocks.elapsedRealtime());
    }

    @UnsupportedAppUsage
    public void noteWifiMulticastEnabledLocked(int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        if (this.mWifiMulticastNesting == 0) {
            this.mHistoryCur.states |= 65536;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            if (!this.mWifiMulticastWakelockTimer.isRunningLocked()) {
                this.mWifiMulticastWakelockTimer.startRunningLocked(elapsedRealtime);
            }
        }
        this.mWifiMulticastNesting++;
        getUidStatsLocked(uid2).noteWifiMulticastEnabledLocked(elapsedRealtime);
    }

    @UnsupportedAppUsage
    public void noteWifiMulticastDisabledLocked(int uid) {
        int uid2 = mapUid(uid);
        long elapsedRealtime = this.mClocks.elapsedRealtime();
        long uptime = this.mClocks.uptimeMillis();
        this.mWifiMulticastNesting--;
        if (this.mWifiMulticastNesting == 0) {
            this.mHistoryCur.states &= -65537;
            addHistoryRecordLocked(elapsedRealtime, uptime);
            if (this.mWifiMulticastWakelockTimer.isRunningLocked()) {
                this.mWifiMulticastWakelockTimer.stopRunningLocked(elapsedRealtime);
            }
        }
        getUidStatsLocked(uid2).noteWifiMulticastDisabledLocked(elapsedRealtime);
    }

    public void noteFullWifiLockAcquiredFromSourceLocked(WorkSource ws) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteFullWifiLockAcquiredLocked(mapUid(ws.get(i)));
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteFullWifiLockAcquiredLocked(mapUid(workChains.get(i2).getAttributionUid()));
            }
        }
    }

    public void noteFullWifiLockReleasedFromSourceLocked(WorkSource ws) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteFullWifiLockReleasedLocked(mapUid(ws.get(i)));
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteFullWifiLockReleasedLocked(mapUid(workChains.get(i2).getAttributionUid()));
            }
        }
    }

    public void noteWifiScanStartedFromSourceLocked(WorkSource ws) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteWifiScanStartedLocked(mapUid(ws.get(i)));
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiScanStartedLocked(mapUid(workChains.get(i2).getAttributionUid()));
            }
        }
    }

    public void noteWifiScanStoppedFromSourceLocked(WorkSource ws) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteWifiScanStoppedLocked(mapUid(ws.get(i)));
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiScanStoppedLocked(mapUid(workChains.get(i2).getAttributionUid()));
            }
        }
    }

    public void noteWifiBatchedScanStartedFromSourceLocked(WorkSource ws, int csph) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteWifiBatchedScanStartedLocked(ws.get(i), csph);
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiBatchedScanStartedLocked(workChains.get(i2).getAttributionUid(), csph);
            }
        }
    }

    public void noteWifiBatchedScanStoppedFromSourceLocked(WorkSource ws) {
        int N = ws.size();
        for (int i = 0; i < N; i++) {
            noteWifiBatchedScanStoppedLocked(ws.get(i));
        }
        List<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiBatchedScanStoppedLocked(workChains.get(i2).getAttributionUid());
            }
        }
    }

    private static String[] includeInStringArray(String[] array, String str) {
        if (ArrayUtils.indexOf(array, str) >= 0) {
            return array;
        }
        String[] newArray = new String[(array.length + 1)];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = str;
        return newArray;
    }

    private static String[] excludeFromStringArray(String[] array, String str) {
        int index = ArrayUtils.indexOf(array, str);
        if (index < 0) {
            return array;
        }
        String[] newArray = new String[(array.length - 1)];
        if (index > 0) {
            System.arraycopy(array, 0, newArray, 0, index);
        }
        if (index < array.length - 1) {
            System.arraycopy(array, index + 1, newArray, index, (array.length - index) - 1);
        }
        return newArray;
    }

    public void noteNetworkInterfaceTypeLocked(String iface, int networkType) {
        if (!TextUtils.isEmpty(iface)) {
            synchronized (this.mModemNetworkLock) {
                if (ConnectivityManager.isNetworkTypeMobile(networkType)) {
                    this.mModemIfaces = includeInStringArray(this.mModemIfaces, iface);
                } else {
                    this.mModemIfaces = excludeFromStringArray(this.mModemIfaces, iface);
                }
            }
            synchronized (this.mWifiNetworkLock) {
                if (ConnectivityManager.isNetworkTypeWifi(networkType)) {
                    this.mWifiIfaces = includeInStringArray(this.mWifiIfaces, iface);
                } else {
                    this.mWifiIfaces = excludeFromStringArray(this.mWifiIfaces, iface);
                }
            }
        }
    }

    public String[] getWifiIfaces() {
        String[] strArr;
        synchronized (this.mWifiNetworkLock) {
            strArr = this.mWifiIfaces;
        }
        return strArr;
    }

    public String[] getMobileIfaces() {
        String[] strArr;
        synchronized (this.mModemNetworkLock) {
            strArr = this.mModemIfaces;
        }
        return strArr;
    }

    @UnsupportedAppUsage
    public long getScreenOnTime(long elapsedRealtimeUs, int which) {
        return this.mScreenOnTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getScreenOnCount(int which) {
        return this.mScreenOnTimer.getCountLocked(which);
    }

    public long getScreenDozeTime(long elapsedRealtimeUs, int which) {
        return this.mScreenDozeTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getScreenDozeCount(int which) {
        return this.mScreenDozeTimer.getCountLocked(which);
    }

    @UnsupportedAppUsage
    public long getScreenBrightnessTime(int brightnessBin, long elapsedRealtimeUs, int which) {
        return this.mScreenBrightnessTimer[brightnessBin].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public Timer getScreenBrightnessTimer(int brightnessBin) {
        return this.mScreenBrightnessTimer[brightnessBin];
    }

    public long getInteractiveTime(long elapsedRealtimeUs, int which) {
        return this.mInteractiveTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public long getPowerSaveModeEnabledTime(long elapsedRealtimeUs, int which) {
        return this.mPowerSaveModeEnabledTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getPowerSaveModeEnabledCount(int which) {
        return this.mPowerSaveModeEnabledTimer.getCountLocked(which);
    }

    public long getDeviceIdleModeTime(int mode, long elapsedRealtimeUs, int which) {
        switch (mode) {
            case 1:
                return this.mDeviceIdleModeLightTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
            case 2:
                return this.mDeviceIdleModeFullTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
            default:
                return 0;
        }
    }

    public int getDeviceIdleModeCount(int mode, int which) {
        switch (mode) {
            case 1:
                return this.mDeviceIdleModeLightTimer.getCountLocked(which);
            case 2:
                return this.mDeviceIdleModeFullTimer.getCountLocked(which);
            default:
                return 0;
        }
    }

    public long getLongestDeviceIdleModeTime(int mode) {
        switch (mode) {
            case 1:
                return this.mLongestLightIdleTime;
            case 2:
                return this.mLongestFullIdleTime;
            default:
                return 0;
        }
    }

    public long getDeviceIdlingTime(int mode, long elapsedRealtimeUs, int which) {
        switch (mode) {
            case 1:
                return this.mDeviceLightIdlingTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
            case 2:
                return this.mDeviceIdlingTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
            default:
                return 0;
        }
    }

    public int getDeviceIdlingCount(int mode, int which) {
        switch (mode) {
            case 1:
                return this.mDeviceLightIdlingTimer.getCountLocked(which);
            case 2:
                return this.mDeviceIdlingTimer.getCountLocked(which);
            default:
                return 0;
        }
    }

    public int getNumConnectivityChange(int which) {
        return this.mNumConnectivityChange;
    }

    public long getGpsSignalQualityTime(int strengthBin, long elapsedRealtimeUs, int which) {
        if (strengthBin < 0 || strengthBin >= 2) {
            return 0;
        }
        return this.mGpsSignalQualityTimer[strengthBin].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public long getGpsBatteryDrainMaMs() {
        if (this.mPowerProfile.getAveragePower(PowerProfile.POWER_GPS_OPERATING_VOLTAGE) / 1000.0d == 0.0d) {
            return 0;
        }
        long rawRealtime = SystemClock.elapsedRealtime() * 1000;
        int i = 0;
        double energyUsedMaMs = 0.0d;
        int i2 = 0;
        while (i2 < 2) {
            energyUsedMaMs += this.mPowerProfile.getAveragePower(PowerProfile.POWER_GPS_SIGNAL_QUALITY_BASED, i2) * ((double) (getGpsSignalQualityTime(i2, rawRealtime, i) / 1000));
            i2++;
            i = 0;
        }
        return (long) energyUsedMaMs;
    }

    @UnsupportedAppUsage
    public long getPhoneOnTime(long elapsedRealtimeUs, int which) {
        return this.mPhoneOnTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getPhoneOnCount(int which) {
        return this.mPhoneOnTimer.getCountLocked(which);
    }

    @UnsupportedAppUsage
    public long getPhoneSignalStrengthTime(int strengthBin, long elapsedRealtimeUs, int which) {
        return this.mPhoneSignalStrengthsTimer[strengthBin].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    @UnsupportedAppUsage
    public long getPhoneSignalScanningTime(long elapsedRealtimeUs, int which) {
        return this.mPhoneSignalScanningTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public Timer getPhoneSignalScanningTimer() {
        return this.mPhoneSignalScanningTimer;
    }

    @UnsupportedAppUsage
    public int getPhoneSignalStrengthCount(int strengthBin, int which) {
        return this.mPhoneSignalStrengthsTimer[strengthBin].getCountLocked(which);
    }

    public Timer getPhoneSignalStrengthTimer(int strengthBin) {
        return this.mPhoneSignalStrengthsTimer[strengthBin];
    }

    @UnsupportedAppUsage
    public long getPhoneDataConnectionTime(int dataType, long elapsedRealtimeUs, int which) {
        return this.mPhoneDataConnectionsTimer[dataType].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    @UnsupportedAppUsage
    public int getPhoneDataConnectionCount(int dataType, int which) {
        return this.mPhoneDataConnectionsTimer[dataType].getCountLocked(which);
    }

    public Timer getPhoneDataConnectionTimer(int dataType) {
        return this.mPhoneDataConnectionsTimer[dataType];
    }

    @UnsupportedAppUsage
    public long getMobileRadioActiveTime(long elapsedRealtimeUs, int which) {
        return this.mMobileRadioActiveTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getMobileRadioActiveCount(int which) {
        return this.mMobileRadioActiveTimer.getCountLocked(which);
    }

    public long getMobileRadioActiveAdjustedTime(int which) {
        return this.mMobileRadioActiveAdjustedTime.getCountLocked(which);
    }

    public long getMobileRadioActiveUnknownTime(int which) {
        return this.mMobileRadioActiveUnknownTime.getCountLocked(which);
    }

    public int getMobileRadioActiveUnknownCount(int which) {
        return (int) this.mMobileRadioActiveUnknownCount.getCountLocked(which);
    }

    public long getWifiMulticastWakelockTime(long elapsedRealtimeUs, int which) {
        return this.mWifiMulticastWakelockTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getWifiMulticastWakelockCount(int which) {
        return this.mWifiMulticastWakelockTimer.getCountLocked(which);
    }

    @UnsupportedAppUsage
    public long getWifiOnTime(long elapsedRealtimeUs, int which) {
        return this.mWifiOnTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public long getWifiActiveTime(long elapsedRealtimeUs, int which) {
        return this.mWifiActiveTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    @UnsupportedAppUsage
    public long getGlobalWifiRunningTime(long elapsedRealtimeUs, int which) {
        return this.mGlobalWifiRunningTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public long getWifiStateTime(int wifiState, long elapsedRealtimeUs, int which) {
        return this.mWifiStateTimer[wifiState].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getWifiStateCount(int wifiState, int which) {
        return this.mWifiStateTimer[wifiState].getCountLocked(which);
    }

    public Timer getWifiStateTimer(int wifiState) {
        return this.mWifiStateTimer[wifiState];
    }

    public long getWifiSupplStateTime(int state, long elapsedRealtimeUs, int which) {
        return this.mWifiSupplStateTimer[state].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getWifiSupplStateCount(int state, int which) {
        return this.mWifiSupplStateTimer[state].getCountLocked(which);
    }

    public Timer getWifiSupplStateTimer(int state) {
        return this.mWifiSupplStateTimer[state];
    }

    public long getWifiSignalStrengthTime(int strengthBin, long elapsedRealtimeUs, int which) {
        return this.mWifiSignalStrengthsTimer[strengthBin].getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public int getWifiSignalStrengthCount(int strengthBin, int which) {
        return this.mWifiSignalStrengthsTimer[strengthBin].getCountLocked(which);
    }

    public Timer getWifiSignalStrengthTimer(int strengthBin) {
        return this.mWifiSignalStrengthsTimer[strengthBin];
    }

    public BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
        return this.mBluetoothActivity;
    }

    public BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
        return this.mWifiActivity;
    }

    public BatteryStats.ControllerActivityCounter getModemControllerActivity() {
        return this.mModemActivity;
    }

    public boolean hasBluetoothActivityReporting() {
        return this.mHasBluetoothReporting;
    }

    public boolean hasWifiActivityReporting() {
        return this.mHasWifiReporting;
    }

    public boolean hasModemActivityReporting() {
        return this.mHasModemReporting;
    }

    public long getFlashlightOnTime(long elapsedRealtimeUs, int which) {
        return this.mFlashlightOnTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public long getFlashlightOnCount(int which) {
        return (long) this.mFlashlightOnTimer.getCountLocked(which);
    }

    public long getCameraOnTime(long elapsedRealtimeUs, int which) {
        return this.mCameraOnTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    public long getBluetoothScanTime(long elapsedRealtimeUs, int which) {
        return this.mBluetoothScanTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
    }

    @UnsupportedAppUsage
    public long getNetworkActivityBytes(int type, int which) {
        if (type < 0 || type >= this.mNetworkByteActivityCounters.length) {
            return 0;
        }
        return this.mNetworkByteActivityCounters[type].getCountLocked(which);
    }

    public long getNetworkActivityPackets(int type, int which) {
        if (type < 0 || type >= this.mNetworkPacketActivityCounters.length) {
            return 0;
        }
        return this.mNetworkPacketActivityCounters[type].getCountLocked(which);
    }

    public long getStartClockTime() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime <= MILLISECONDS_IN_YEAR || this.mStartClockTime >= currentTime - MILLISECONDS_IN_YEAR) && this.mStartClockTime <= currentTime) {
            return this.mStartClockTime;
        }
        recordCurrentTimeChangeLocked(currentTime, this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
        return currentTime - (this.mClocks.elapsedRealtime() - (this.mRealtimeStart / 1000));
    }

    public String getStartPlatformVersion() {
        return this.mStartPlatformVersion;
    }

    public String getEndPlatformVersion() {
        return this.mEndPlatformVersion;
    }

    public int getParcelVersion() {
        return 186;
    }

    public boolean getIsOnBattery() {
        return this.mOnBattery;
    }

    @UnsupportedAppUsage
    public SparseArray<? extends BatteryStats.Uid> getUidStats() {
        return this.mUidStats;
    }

    /* access modifiers changed from: private */
    public static <T extends TimeBaseObs> boolean resetIfNotNull(T t, boolean detachIfReset) {
        if (t != null) {
            return t.reset(detachIfReset);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static <T extends TimeBaseObs> boolean resetIfNotNull(T[] t, boolean detachIfReset) {
        if (t == null) {
            return true;
        }
        boolean ret = true;
        for (T resetIfNotNull : t) {
            ret &= resetIfNotNull(resetIfNotNull, detachIfReset);
        }
        return ret;
    }

    /* access modifiers changed from: private */
    public static <T extends TimeBaseObs> boolean resetIfNotNull(T[][] t, boolean detachIfReset) {
        if (t == null) {
            return true;
        }
        boolean ret = true;
        for (T[] resetIfNotNull : t) {
            ret &= resetIfNotNull(resetIfNotNull, detachIfReset);
        }
        return ret;
    }

    /* access modifiers changed from: private */
    public static boolean resetIfNotNull(ControllerActivityCounterImpl counter, boolean detachIfReset) {
        if (counter == null) {
            return true;
        }
        counter.reset(detachIfReset);
        return true;
    }

    /* access modifiers changed from: private */
    public static <T extends TimeBaseObs> void detachIfNotNull(T t) {
        if (t != null) {
            t.detach();
        }
    }

    /* access modifiers changed from: private */
    public static <T extends TimeBaseObs> void detachIfNotNull(T[] t) {
        if (t != null) {
            for (T detachIfNotNull : t) {
                detachIfNotNull(detachIfNotNull);
            }
        }
    }

    /* access modifiers changed from: private */
    public static <T extends TimeBaseObs> void detachIfNotNull(T[][] t) {
        if (t != null) {
            for (T[] detachIfNotNull : t) {
                detachIfNotNull(detachIfNotNull);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void detachIfNotNull(ControllerActivityCounterImpl counter) {
        if (counter != null) {
            counter.detach();
        }
    }

    public static class Uid extends BatteryStats.Uid {
        static final int NO_BATCHED_SCAN_STARTED = -1;
        DualTimer mAggregatedPartialWakelockTimer;
        StopwatchTimer mAudioTurnedOnTimer;
        private ControllerActivityCounterImpl mBluetoothControllerActivity;
        Counter mBluetoothScanResultBgCounter;
        Counter mBluetoothScanResultCounter;
        DualTimer mBluetoothScanTimer;
        DualTimer mBluetoothUnoptimizedScanTimer;
        protected BatteryStatsImpl mBsi;
        StopwatchTimer mCameraTurnedOnTimer;
        IntArray mChildUids;
        LongSamplingCounter mCpuActiveTimeMs;
        LongSamplingCounter[][] mCpuClusterSpeedTimesUs;
        LongSamplingCounterArray mCpuClusterTimesMs;
        LongSamplingCounterArray mCpuFreqTimeMs;
        long mCurStepSystemTime;
        long mCurStepUserTime;
        StopwatchTimer mFlashlightTurnedOnTimer;
        StopwatchTimer mForegroundActivityTimer;
        StopwatchTimer mForegroundServiceTimer;
        boolean mFullWifiLockOut;
        StopwatchTimer mFullWifiLockTimer;
        boolean mInForegroundService = false;
        final ArrayMap<String, SparseIntArray> mJobCompletions = new ArrayMap<>();
        final OverflowArrayMap<DualTimer> mJobStats;
        Counter mJobsDeferredCount;
        Counter mJobsDeferredEventCount;
        final Counter[] mJobsFreshnessBuckets;
        LongSamplingCounter mJobsFreshnessTimeMs;
        long mLastStepSystemTime;
        long mLastStepUserTime;
        LongSamplingCounter mMobileRadioActiveCount;
        LongSamplingCounter mMobileRadioActiveTime;
        /* access modifiers changed from: private */
        public LongSamplingCounter mMobileRadioApWakeupCount;
        private ControllerActivityCounterImpl mModemControllerActivity;
        LongSamplingCounter[] mNetworkByteActivityCounters;
        LongSamplingCounter[] mNetworkPacketActivityCounters;
        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public final TimeBase mOnBatteryBackgroundTimeBase;
        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public final TimeBase mOnBatteryScreenOffBackgroundTimeBase;
        final ArrayMap<String, Pkg> mPackageStats = new ArrayMap<>();
        final SparseArray<BatteryStats.Uid.Pid> mPids = new SparseArray<>();
        LongSamplingCounterArray[] mProcStateScreenOffTimeMs;
        LongSamplingCounterArray[] mProcStateTimeMs;
        int mProcessState = 21;
        StopwatchTimer[] mProcessStateTimer;
        final ArrayMap<String, Proc> mProcessStats = new ArrayMap<>();
        LongSamplingCounterArray mScreenOffCpuFreqTimeMs;
        final SparseArray<Sensor> mSensorStats = new SparseArray<>();
        final OverflowArrayMap<DualTimer> mSyncStats;
        LongSamplingCounter mSystemCpuTime;
        final int mUid;
        Counter[] mUserActivityCounters;
        LongSamplingCounter mUserCpuTime;
        BatchTimer mVibratorOnTimer;
        StopwatchTimer mVideoTurnedOnTimer;
        final OverflowArrayMap<Wakelock> mWakelockStats;
        int mWifiBatchedScanBinStarted = -1;
        StopwatchTimer[] mWifiBatchedScanTimer;
        private ControllerActivityCounterImpl mWifiControllerActivity;
        StopwatchTimer mWifiMulticastTimer;
        int mWifiMulticastWakelockCount;
        /* access modifiers changed from: private */
        public LongSamplingCounter mWifiRadioApWakeupCount;
        boolean mWifiRunning;
        StopwatchTimer mWifiRunningTimer;
        boolean mWifiScanStarted;
        DualTimer mWifiScanTimer;

        public Uid(BatteryStatsImpl bsi, int uid) {
            this.mBsi = bsi;
            this.mUid = uid;
            this.mOnBatteryBackgroundTimeBase = new TimeBase(false);
            this.mOnBatteryBackgroundTimeBase.init(this.mBsi.mClocks.uptimeMillis() * 1000, this.mBsi.mClocks.elapsedRealtime() * 1000);
            this.mOnBatteryScreenOffBackgroundTimeBase = new TimeBase(false);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(this.mBsi.mClocks.uptimeMillis() * 1000, this.mBsi.mClocks.elapsedRealtime() * 1000);
            this.mUserCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mSystemCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mCpuActiveTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mCpuClusterTimesMs = new LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase);
            BatteryStatsImpl batteryStatsImpl = this.mBsi;
            Objects.requireNonNull(batteryStatsImpl);
            this.mWakelockStats = new OverflowArrayMap<Wakelock>(batteryStatsImpl, uid) {
                {
                    Objects.requireNonNull(x0);
                }

                public Wakelock instantiateObject() {
                    return new Wakelock(Uid.this.mBsi, Uid.this);
                }
            };
            BatteryStatsImpl batteryStatsImpl2 = this.mBsi;
            Objects.requireNonNull(batteryStatsImpl2);
            this.mSyncStats = new OverflowArrayMap<DualTimer>(batteryStatsImpl2, uid) {
                {
                    Objects.requireNonNull(x0);
                }

                public DualTimer instantiateObject() {
                    return new DualTimer(Uid.this.mBsi.mClocks, Uid.this, 13, (ArrayList<StopwatchTimer>) null, Uid.this.mBsi.mOnBatteryTimeBase, Uid.this.mOnBatteryBackgroundTimeBase);
                }
            };
            BatteryStatsImpl batteryStatsImpl3 = this.mBsi;
            Objects.requireNonNull(batteryStatsImpl3);
            this.mJobStats = new OverflowArrayMap<DualTimer>(batteryStatsImpl3, uid) {
                {
                    Objects.requireNonNull(x0);
                }

                public DualTimer instantiateObject() {
                    return new DualTimer(Uid.this.mBsi.mClocks, Uid.this, 14, (ArrayList<StopwatchTimer>) null, Uid.this.mBsi.mOnBatteryTimeBase, Uid.this.mOnBatteryBackgroundTimeBase);
                }
            };
            this.mWifiRunningTimer = new StopwatchTimer(this.mBsi.mClocks, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase);
            this.mFullWifiLockTimer = new StopwatchTimer(this.mBsi.mClocks, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase);
            this.mWifiScanTimer = new DualTimer(this.mBsi.mClocks, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            this.mWifiBatchedScanTimer = new StopwatchTimer[5];
            this.mWifiMulticastTimer = new StopwatchTimer(this.mBsi.mClocks, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase);
            this.mProcessStateTimer = new StopwatchTimer[7];
            this.mJobsDeferredEventCount = new Counter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsDeferredCount = new Counter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsFreshnessTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsFreshnessBuckets = new Counter[BatteryStats.JOB_FRESHNESS_BUCKETS.length];
        }

        @VisibleForTesting
        public void setProcessStateForTest(int procState) {
            this.mProcessState = procState;
        }

        public long[] getCpuFreqTimes(int which) {
            return nullIfAllZeros(this.mCpuFreqTimeMs, which);
        }

        public long[] getScreenOffCpuFreqTimes(int which) {
            return nullIfAllZeros(this.mScreenOffCpuFreqTimeMs, which);
        }

        public long getCpuActiveTime() {
            return this.mCpuActiveTimeMs.getCountLocked(0);
        }

        public long[] getCpuClusterTimes() {
            return nullIfAllZeros(this.mCpuClusterTimesMs, 0);
        }

        public long[] getCpuFreqTimes(int which, int procState) {
            if (which < 0 || which >= 7 || this.mProcStateTimeMs == null) {
                return null;
            }
            if (this.mBsi.mPerProcStateCpuTimesAvailable) {
                return nullIfAllZeros(this.mProcStateTimeMs[procState], which);
            }
            this.mProcStateTimeMs = null;
            return null;
        }

        public long[] getScreenOffCpuFreqTimes(int which, int procState) {
            if (which < 0 || which >= 7 || this.mProcStateScreenOffTimeMs == null) {
                return null;
            }
            if (this.mBsi.mPerProcStateCpuTimesAvailable) {
                return nullIfAllZeros(this.mProcStateScreenOffTimeMs[procState], which);
            }
            this.mProcStateScreenOffTimeMs = null;
            return null;
        }

        public void addIsolatedUid(int isolatedUid) {
            if (this.mChildUids == null) {
                this.mChildUids = new IntArray();
            } else if (this.mChildUids.indexOf(isolatedUid) >= 0) {
                return;
            }
            this.mChildUids.add(isolatedUid);
        }

        public void removeIsolatedUid(int isolatedUid) {
            int idx = this.mChildUids == null ? -1 : this.mChildUids.indexOf(isolatedUid);
            if (idx >= 0) {
                this.mChildUids.remove(idx);
            }
        }

        private long[] nullIfAllZeros(LongSamplingCounterArray cpuTimesMs, int which) {
            long[] counts;
            if (cpuTimesMs == null || (counts = cpuTimesMs.getCountsLocked(which)) == null) {
                return null;
            }
            for (int i = counts.length - 1; i >= 0; i--) {
                if (counts[i] != 0) {
                    return counts;
                }
            }
            return null;
        }

        /* access modifiers changed from: private */
        public void addProcStateTimesMs(int procState, long[] cpuTimesMs, boolean onBattery) {
            if (this.mProcStateTimeMs == null) {
                this.mProcStateTimeMs = new LongSamplingCounterArray[7];
            }
            if (this.mProcStateTimeMs[procState] == null || this.mProcStateTimeMs[procState].getSize() != cpuTimesMs.length) {
                BatteryStatsImpl.detachIfNotNull(this.mProcStateTimeMs[procState]);
                this.mProcStateTimeMs[procState] = new LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase);
            }
            this.mProcStateTimeMs[procState].addCountLocked(cpuTimesMs, onBattery);
        }

        /* access modifiers changed from: private */
        public void addProcStateScreenOffTimesMs(int procState, long[] cpuTimesMs, boolean onBatteryScreenOff) {
            if (this.mProcStateScreenOffTimeMs == null) {
                this.mProcStateScreenOffTimeMs = new LongSamplingCounterArray[7];
            }
            if (this.mProcStateScreenOffTimeMs[procState] == null || this.mProcStateScreenOffTimeMs[procState].getSize() != cpuTimesMs.length) {
                BatteryStatsImpl.detachIfNotNull(this.mProcStateScreenOffTimeMs[procState]);
                this.mProcStateScreenOffTimeMs[procState] = new LongSamplingCounterArray(this.mBsi.mOnBatteryScreenOffTimeBase);
            }
            this.mProcStateScreenOffTimeMs[procState].addCountLocked(cpuTimesMs, onBatteryScreenOff);
        }

        public Timer getAggregatedPartialWakelockTimer() {
            return this.mAggregatedPartialWakelockTimer;
        }

        @UnsupportedAppUsage
        public ArrayMap<String, ? extends BatteryStats.Uid.Wakelock> getWakelockStats() {
            return this.mWakelockStats.getMap();
        }

        public Timer getMulticastWakelockStats() {
            return this.mWifiMulticastTimer;
        }

        public ArrayMap<String, ? extends BatteryStats.Timer> getSyncStats() {
            return this.mSyncStats.getMap();
        }

        public ArrayMap<String, ? extends BatteryStats.Timer> getJobStats() {
            return this.mJobStats.getMap();
        }

        public ArrayMap<String, SparseIntArray> getJobCompletionStats() {
            return this.mJobCompletions;
        }

        @UnsupportedAppUsage
        public SparseArray<? extends BatteryStats.Uid.Sensor> getSensorStats() {
            return this.mSensorStats;
        }

        @UnsupportedAppUsage
        public ArrayMap<String, ? extends BatteryStats.Uid.Proc> getProcessStats() {
            return this.mProcessStats;
        }

        public ArrayMap<String, ? extends BatteryStats.Uid.Pkg> getPackageStats() {
            return this.mPackageStats;
        }

        @UnsupportedAppUsage
        public int getUid() {
            return this.mUid;
        }

        public void noteWifiRunningLocked(long elapsedRealtimeMs) {
            if (!this.mWifiRunning) {
                this.mWifiRunning = true;
                if (this.mWifiRunningTimer == null) {
                    this.mWifiRunningTimer = new StopwatchTimer(this.mBsi.mClocks, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mWifiRunningTimer.startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteWifiStoppedLocked(long elapsedRealtimeMs) {
            if (this.mWifiRunning) {
                this.mWifiRunning = false;
                this.mWifiRunningTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteFullWifiLockAcquiredLocked(long elapsedRealtimeMs) {
            if (!this.mFullWifiLockOut) {
                this.mFullWifiLockOut = true;
                if (this.mFullWifiLockTimer == null) {
                    this.mFullWifiLockTimer = new StopwatchTimer(this.mBsi.mClocks, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mFullWifiLockTimer.startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteFullWifiLockReleasedLocked(long elapsedRealtimeMs) {
            if (this.mFullWifiLockOut) {
                this.mFullWifiLockOut = false;
                this.mFullWifiLockTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteWifiScanStartedLocked(long elapsedRealtimeMs) {
            if (!this.mWifiScanStarted) {
                this.mWifiScanStarted = true;
                if (this.mWifiScanTimer == null) {
                    this.mWifiScanTimer = new DualTimer(this.mBsi.mClocks, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
                }
                this.mWifiScanTimer.startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteWifiScanStoppedLocked(long elapsedRealtimeMs) {
            if (this.mWifiScanStarted) {
                this.mWifiScanStarted = false;
                this.mWifiScanTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteWifiBatchedScanStartedLocked(int csph, long elapsedRealtimeMs) {
            int bin = 0;
            while (csph > 8 && bin < 4) {
                csph >>= 3;
                bin++;
            }
            if (this.mWifiBatchedScanBinStarted != bin) {
                if (this.mWifiBatchedScanBinStarted != -1) {
                    this.mWifiBatchedScanTimer[this.mWifiBatchedScanBinStarted].stopRunningLocked(elapsedRealtimeMs);
                }
                this.mWifiBatchedScanBinStarted = bin;
                if (this.mWifiBatchedScanTimer[bin] == null) {
                    makeWifiBatchedScanBin(bin, (Parcel) null);
                }
                this.mWifiBatchedScanTimer[bin].startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteWifiBatchedScanStoppedLocked(long elapsedRealtimeMs) {
            if (this.mWifiBatchedScanBinStarted != -1) {
                this.mWifiBatchedScanTimer[this.mWifiBatchedScanBinStarted].stopRunningLocked(elapsedRealtimeMs);
                this.mWifiBatchedScanBinStarted = -1;
            }
        }

        public void noteWifiMulticastEnabledLocked(long elapsedRealtimeMs) {
            if (this.mWifiMulticastWakelockCount == 0) {
                if (this.mWifiMulticastTimer == null) {
                    this.mWifiMulticastTimer = new StopwatchTimer(this.mBsi.mClocks, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mWifiMulticastTimer.startRunningLocked(elapsedRealtimeMs);
            }
            this.mWifiMulticastWakelockCount++;
        }

        public void noteWifiMulticastDisabledLocked(long elapsedRealtimeMs) {
            if (this.mWifiMulticastWakelockCount != 0) {
                this.mWifiMulticastWakelockCount--;
                if (this.mWifiMulticastWakelockCount == 0) {
                    this.mWifiMulticastTimer.stopRunningLocked(elapsedRealtimeMs);
                }
            }
        }

        public BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
            return this.mWifiControllerActivity;
        }

        public BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
            return this.mBluetoothControllerActivity;
        }

        public BatteryStats.ControllerActivityCounter getModemControllerActivity() {
            return this.mModemControllerActivity;
        }

        public ControllerActivityCounterImpl getOrCreateWifiControllerActivityLocked() {
            if (this.mWifiControllerActivity == null) {
                this.mWifiControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1);
            }
            return this.mWifiControllerActivity;
        }

        public ControllerActivityCounterImpl getOrCreateBluetoothControllerActivityLocked() {
            if (this.mBluetoothControllerActivity == null) {
                this.mBluetoothControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1);
            }
            return this.mBluetoothControllerActivity;
        }

        public ControllerActivityCounterImpl getOrCreateModemControllerActivityLocked() {
            if (this.mModemControllerActivity == null) {
                this.mModemControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 5);
            }
            return this.mModemControllerActivity;
        }

        public StopwatchTimer createAudioTurnedOnTimerLocked() {
            if (this.mAudioTurnedOnTimer == null) {
                this.mAudioTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 15, this.mBsi.mAudioTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mAudioTurnedOnTimer;
        }

        public void noteAudioTurnedOnLocked(long elapsedRealtimeMs) {
            createAudioTurnedOnTimerLocked().startRunningLocked(elapsedRealtimeMs);
        }

        public void noteAudioTurnedOffLocked(long elapsedRealtimeMs) {
            if (this.mAudioTurnedOnTimer != null) {
                this.mAudioTurnedOnTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteResetAudioLocked(long elapsedRealtimeMs) {
            if (this.mAudioTurnedOnTimer != null) {
                this.mAudioTurnedOnTimer.stopAllRunningLocked(elapsedRealtimeMs);
            }
        }

        public StopwatchTimer createVideoTurnedOnTimerLocked() {
            if (this.mVideoTurnedOnTimer == null) {
                this.mVideoTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 8, this.mBsi.mVideoTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mVideoTurnedOnTimer;
        }

        public void noteVideoTurnedOnLocked(long elapsedRealtimeMs) {
            createVideoTurnedOnTimerLocked().startRunningLocked(elapsedRealtimeMs);
        }

        public void noteVideoTurnedOffLocked(long elapsedRealtimeMs) {
            if (this.mVideoTurnedOnTimer != null) {
                this.mVideoTurnedOnTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteResetVideoLocked(long elapsedRealtimeMs) {
            if (this.mVideoTurnedOnTimer != null) {
                this.mVideoTurnedOnTimer.stopAllRunningLocked(elapsedRealtimeMs);
            }
        }

        public StopwatchTimer createFlashlightTurnedOnTimerLocked() {
            if (this.mFlashlightTurnedOnTimer == null) {
                this.mFlashlightTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 16, this.mBsi.mFlashlightTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mFlashlightTurnedOnTimer;
        }

        public void noteFlashlightTurnedOnLocked(long elapsedRealtimeMs) {
            createFlashlightTurnedOnTimerLocked().startRunningLocked(elapsedRealtimeMs);
        }

        public void noteFlashlightTurnedOffLocked(long elapsedRealtimeMs) {
            if (this.mFlashlightTurnedOnTimer != null) {
                this.mFlashlightTurnedOnTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteResetFlashlightLocked(long elapsedRealtimeMs) {
            if (this.mFlashlightTurnedOnTimer != null) {
                this.mFlashlightTurnedOnTimer.stopAllRunningLocked(elapsedRealtimeMs);
            }
        }

        public StopwatchTimer createCameraTurnedOnTimerLocked() {
            if (this.mCameraTurnedOnTimer == null) {
                this.mCameraTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 17, this.mBsi.mCameraTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mCameraTurnedOnTimer;
        }

        public void noteCameraTurnedOnLocked(long elapsedRealtimeMs) {
            createCameraTurnedOnTimerLocked().startRunningLocked(elapsedRealtimeMs);
        }

        public void noteCameraTurnedOffLocked(long elapsedRealtimeMs) {
            if (this.mCameraTurnedOnTimer != null) {
                this.mCameraTurnedOnTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteResetCameraLocked(long elapsedRealtimeMs) {
            if (this.mCameraTurnedOnTimer != null) {
                this.mCameraTurnedOnTimer.stopAllRunningLocked(elapsedRealtimeMs);
            }
        }

        public StopwatchTimer createForegroundActivityTimerLocked() {
            if (this.mForegroundActivityTimer == null) {
                this.mForegroundActivityTimer = new StopwatchTimer(this.mBsi.mClocks, this, 10, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mForegroundActivityTimer;
        }

        public StopwatchTimer createForegroundServiceTimerLocked() {
            if (this.mForegroundServiceTimer == null) {
                this.mForegroundServiceTimer = new StopwatchTimer(this.mBsi.mClocks, this, 22, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mForegroundServiceTimer;
        }

        public DualTimer createAggregatedPartialWakelockTimerLocked() {
            if (this.mAggregatedPartialWakelockTimer == null) {
                this.mAggregatedPartialWakelockTimer = new DualTimer(this.mBsi.mClocks, this, 20, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
            }
            return this.mAggregatedPartialWakelockTimer;
        }

        public DualTimer createBluetoothScanTimerLocked() {
            if (this.mBluetoothScanTimer == null) {
                this.mBluetoothScanTimer = new DualTimer(this.mBsi.mClocks, this, 19, this.mBsi.mBluetoothScanOnTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothScanTimer;
        }

        public DualTimer createBluetoothUnoptimizedScanTimerLocked() {
            if (this.mBluetoothUnoptimizedScanTimer == null) {
                this.mBluetoothUnoptimizedScanTimer = new DualTimer(this.mBsi.mClocks, this, 21, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothUnoptimizedScanTimer;
        }

        public void noteBluetoothScanStartedLocked(long elapsedRealtimeMs, boolean isUnoptimized) {
            createBluetoothScanTimerLocked().startRunningLocked(elapsedRealtimeMs);
            if (isUnoptimized) {
                createBluetoothUnoptimizedScanTimerLocked().startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteBluetoothScanStoppedLocked(long elapsedRealtimeMs, boolean isUnoptimized) {
            if (this.mBluetoothScanTimer != null) {
                this.mBluetoothScanTimer.stopRunningLocked(elapsedRealtimeMs);
            }
            if (isUnoptimized && this.mBluetoothUnoptimizedScanTimer != null) {
                this.mBluetoothUnoptimizedScanTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteResetBluetoothScanLocked(long elapsedRealtimeMs) {
            if (this.mBluetoothScanTimer != null) {
                this.mBluetoothScanTimer.stopAllRunningLocked(elapsedRealtimeMs);
            }
            if (this.mBluetoothUnoptimizedScanTimer != null) {
                this.mBluetoothUnoptimizedScanTimer.stopAllRunningLocked(elapsedRealtimeMs);
            }
        }

        public Counter createBluetoothScanResultCounterLocked() {
            if (this.mBluetoothScanResultCounter == null) {
                this.mBluetoothScanResultCounter = new Counter(this.mBsi.mOnBatteryTimeBase);
            }
            return this.mBluetoothScanResultCounter;
        }

        public Counter createBluetoothScanResultBgCounterLocked() {
            if (this.mBluetoothScanResultBgCounter == null) {
                this.mBluetoothScanResultBgCounter = new Counter(this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothScanResultBgCounter;
        }

        public void noteBluetoothScanResultsLocked(int numNewResults) {
            createBluetoothScanResultCounterLocked().addAtomic(numNewResults);
            createBluetoothScanResultBgCounterLocked().addAtomic(numNewResults);
        }

        public void noteActivityResumedLocked(long elapsedRealtimeMs) {
            createForegroundActivityTimerLocked().startRunningLocked(elapsedRealtimeMs);
        }

        public void noteActivityPausedLocked(long elapsedRealtimeMs) {
            if (this.mForegroundActivityTimer != null) {
                this.mForegroundActivityTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteForegroundServiceResumedLocked(long elapsedRealtimeMs) {
            createForegroundServiceTimerLocked().startRunningLocked(elapsedRealtimeMs);
        }

        public void noteForegroundServicePausedLocked(long elapsedRealtimeMs) {
            if (this.mForegroundServiceTimer != null) {
                this.mForegroundServiceTimer.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public BatchTimer createVibratorOnTimerLocked() {
            if (this.mVibratorOnTimer == null) {
                this.mVibratorOnTimer = new BatchTimer(this.mBsi.mClocks, this, 9, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mVibratorOnTimer;
        }

        public void noteVibratorOnLocked(long durationMillis) {
            createVibratorOnTimerLocked().addDuration(this.mBsi, durationMillis);
        }

        public void noteVibratorOffLocked() {
            if (this.mVibratorOnTimer != null) {
                this.mVibratorOnTimer.abortLastDuration(this.mBsi);
            }
        }

        @UnsupportedAppUsage
        public long getWifiRunningTime(long elapsedRealtimeUs, int which) {
            if (this.mWifiRunningTimer == null) {
                return 0;
            }
            return this.mWifiRunningTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
        }

        public long getFullWifiLockTime(long elapsedRealtimeUs, int which) {
            if (this.mFullWifiLockTimer == null) {
                return 0;
            }
            return this.mFullWifiLockTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
        }

        @UnsupportedAppUsage
        public long getWifiScanTime(long elapsedRealtimeUs, int which) {
            if (this.mWifiScanTimer == null) {
                return 0;
            }
            return this.mWifiScanTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
        }

        public int getWifiScanCount(int which) {
            if (this.mWifiScanTimer == null) {
                return 0;
            }
            return this.mWifiScanTimer.getCountLocked(which);
        }

        public Timer getWifiScanTimer() {
            return this.mWifiScanTimer;
        }

        public int getWifiScanBackgroundCount(int which) {
            if (this.mWifiScanTimer == null || this.mWifiScanTimer.getSubTimer() == null) {
                return 0;
            }
            return this.mWifiScanTimer.getSubTimer().getCountLocked(which);
        }

        public long getWifiScanActualTime(long elapsedRealtimeUs) {
            if (this.mWifiScanTimer == null) {
                return 0;
            }
            return this.mWifiScanTimer.getTotalDurationMsLocked((500 + elapsedRealtimeUs) / 1000) * 1000;
        }

        public long getWifiScanBackgroundTime(long elapsedRealtimeUs) {
            if (this.mWifiScanTimer == null || this.mWifiScanTimer.getSubTimer() == null) {
                return 0;
            }
            return this.mWifiScanTimer.getSubTimer().getTotalDurationMsLocked((500 + elapsedRealtimeUs) / 1000) * 1000;
        }

        public Timer getWifiScanBackgroundTimer() {
            if (this.mWifiScanTimer == null) {
                return null;
            }
            return this.mWifiScanTimer.getSubTimer();
        }

        public long getWifiBatchedScanTime(int csphBin, long elapsedRealtimeUs, int which) {
            if (csphBin < 0 || csphBin >= 5 || this.mWifiBatchedScanTimer[csphBin] == null) {
                return 0;
            }
            return this.mWifiBatchedScanTimer[csphBin].getTotalTimeLocked(elapsedRealtimeUs, which);
        }

        public int getWifiBatchedScanCount(int csphBin, int which) {
            if (csphBin < 0 || csphBin >= 5 || this.mWifiBatchedScanTimer[csphBin] == null) {
                return 0;
            }
            return this.mWifiBatchedScanTimer[csphBin].getCountLocked(which);
        }

        public long getWifiMulticastTime(long elapsedRealtimeUs, int which) {
            if (this.mWifiMulticastTimer == null) {
                return 0;
            }
            return this.mWifiMulticastTimer.getTotalTimeLocked(elapsedRealtimeUs, which);
        }

        public Timer getAudioTurnedOnTimer() {
            return this.mAudioTurnedOnTimer;
        }

        public Timer getVideoTurnedOnTimer() {
            return this.mVideoTurnedOnTimer;
        }

        public Timer getFlashlightTurnedOnTimer() {
            return this.mFlashlightTurnedOnTimer;
        }

        public Timer getCameraTurnedOnTimer() {
            return this.mCameraTurnedOnTimer;
        }

        public Timer getForegroundActivityTimer() {
            return this.mForegroundActivityTimer;
        }

        public Timer getForegroundServiceTimer() {
            return this.mForegroundServiceTimer;
        }

        public Timer getBluetoothScanTimer() {
            return this.mBluetoothScanTimer;
        }

        public Timer getBluetoothScanBackgroundTimer() {
            if (this.mBluetoothScanTimer == null) {
                return null;
            }
            return this.mBluetoothScanTimer.getSubTimer();
        }

        public Timer getBluetoothUnoptimizedScanTimer() {
            return this.mBluetoothUnoptimizedScanTimer;
        }

        public Timer getBluetoothUnoptimizedScanBackgroundTimer() {
            if (this.mBluetoothUnoptimizedScanTimer == null) {
                return null;
            }
            return this.mBluetoothUnoptimizedScanTimer.getSubTimer();
        }

        public Counter getBluetoothScanResultCounter() {
            return this.mBluetoothScanResultCounter;
        }

        public Counter getBluetoothScanResultBgCounter() {
            return this.mBluetoothScanResultBgCounter;
        }

        /* access modifiers changed from: package-private */
        public void makeProcessState(int i, Parcel in) {
            if (i >= 0 && i < 7) {
                BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer[i]);
                if (in == null) {
                    this.mProcessStateTimer[i] = new StopwatchTimer(this.mBsi.mClocks, this, 12, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase);
                    return;
                }
                this.mProcessStateTimer[i] = new StopwatchTimer(this.mBsi.mClocks, this, 12, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, in);
            }
        }

        public long getProcessStateTime(int state, long elapsedRealtimeUs, int which) {
            if (state < 0 || state >= 7 || this.mProcessStateTimer[state] == null) {
                return 0;
            }
            return this.mProcessStateTimer[state].getTotalTimeLocked(elapsedRealtimeUs, which);
        }

        public Timer getProcessStateTimer(int state) {
            if (state < 0 || state >= 7) {
                return null;
            }
            return this.mProcessStateTimer[state];
        }

        public Timer getVibratorOnTimer() {
            return this.mVibratorOnTimer;
        }

        public void noteUserActivityLocked(int type) {
            if (this.mUserActivityCounters == null) {
                initUserActivityLocked();
            }
            if (type < 0 || type >= NUM_USER_ACTIVITY_TYPES) {
                Slog.w(BatteryStatsImpl.TAG, "Unknown user activity type " + type + " was specified.", new Throwable());
                return;
            }
            this.mUserActivityCounters[type].stepAtomic();
        }

        public boolean hasUserActivity() {
            return this.mUserActivityCounters != null;
        }

        public int getUserActivityCount(int type, int which) {
            if (this.mUserActivityCounters == null) {
                return 0;
            }
            return this.mUserActivityCounters[type].getCountLocked(which);
        }

        /* access modifiers changed from: package-private */
        public void makeWifiBatchedScanBin(int i, Parcel in) {
            if (i >= 0 && i < 5) {
                ArrayList<StopwatchTimer> collected = this.mBsi.mWifiBatchedScanTimers.get(i);
                if (collected == null) {
                    collected = new ArrayList<>();
                    this.mBsi.mWifiBatchedScanTimers.put(i, collected);
                }
                BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer[i]);
                if (in == null) {
                    this.mWifiBatchedScanTimer[i] = new StopwatchTimer(this.mBsi.mClocks, this, 11, collected, this.mBsi.mOnBatteryTimeBase);
                    return;
                }
                this.mWifiBatchedScanTimer[i] = new StopwatchTimer(this.mBsi.mClocks, this, 11, collected, this.mBsi.mOnBatteryTimeBase, in);
            }
        }

        /* access modifiers changed from: package-private */
        public void initUserActivityLocked() {
            BatteryStatsImpl.detachIfNotNull((T[]) this.mUserActivityCounters);
            this.mUserActivityCounters = new Counter[NUM_USER_ACTIVITY_TYPES];
            for (int i = 0; i < NUM_USER_ACTIVITY_TYPES; i++) {
                this.mUserActivityCounters[i] = new Counter(this.mBsi.mOnBatteryTimeBase);
            }
        }

        /* access modifiers changed from: package-private */
        public void noteNetworkActivityLocked(int type, long deltaBytes, long deltaPackets) {
            if (this.mNetworkByteActivityCounters == null) {
                initNetworkActivityLocked();
            }
            if (type < 0 || type >= 10) {
                Slog.w(BatteryStatsImpl.TAG, "Unknown network activity type " + type + " was specified.", new Throwable());
                return;
            }
            this.mNetworkByteActivityCounters[type].addCountLocked(deltaBytes);
            this.mNetworkPacketActivityCounters[type].addCountLocked(deltaPackets);
        }

        /* access modifiers changed from: package-private */
        public void noteMobileRadioActiveTimeLocked(long batteryUptime) {
            if (this.mNetworkByteActivityCounters == null) {
                initNetworkActivityLocked();
            }
            this.mMobileRadioActiveTime.addCountLocked(batteryUptime);
            this.mMobileRadioActiveCount.addCountLocked(1);
        }

        public boolean hasNetworkActivity() {
            return this.mNetworkByteActivityCounters != null;
        }

        public long getNetworkActivityBytes(int type, int which) {
            if (this.mNetworkByteActivityCounters == null || type < 0 || type >= this.mNetworkByteActivityCounters.length) {
                return 0;
            }
            return this.mNetworkByteActivityCounters[type].getCountLocked(which);
        }

        public long getNetworkActivityPackets(int type, int which) {
            if (this.mNetworkPacketActivityCounters == null || type < 0 || type >= this.mNetworkPacketActivityCounters.length) {
                return 0;
            }
            return this.mNetworkPacketActivityCounters[type].getCountLocked(which);
        }

        public long getMobileRadioActiveTime(int which) {
            if (this.mMobileRadioActiveTime != null) {
                return this.mMobileRadioActiveTime.getCountLocked(which);
            }
            return 0;
        }

        public int getMobileRadioActiveCount(int which) {
            if (this.mMobileRadioActiveCount != null) {
                return (int) this.mMobileRadioActiveCount.getCountLocked(which);
            }
            return 0;
        }

        public long getUserCpuTimeUs(int which) {
            return this.mUserCpuTime.getCountLocked(which);
        }

        public long getSystemCpuTimeUs(int which) {
            return this.mSystemCpuTime.getCountLocked(which);
        }

        public long getTimeAtCpuSpeed(int cluster, int step, int which) {
            LongSamplingCounter[] cpuSpeedTimesUs;
            LongSamplingCounter c;
            if (this.mCpuClusterSpeedTimesUs == null || cluster < 0 || cluster >= this.mCpuClusterSpeedTimesUs.length || (cpuSpeedTimesUs = this.mCpuClusterSpeedTimesUs[cluster]) == null || step < 0 || step >= cpuSpeedTimesUs.length || (c = cpuSpeedTimesUs[step]) == null) {
                return 0;
            }
            return c.getCountLocked(which);
        }

        public void noteMobileRadioApWakeupLocked() {
            if (this.mMobileRadioApWakeupCount == null) {
                this.mMobileRadioApWakeupCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mMobileRadioApWakeupCount.addCountLocked(1);
        }

        public long getMobileRadioApWakeupCount(int which) {
            if (this.mMobileRadioApWakeupCount != null) {
                return this.mMobileRadioApWakeupCount.getCountLocked(which);
            }
            return 0;
        }

        public void noteWifiRadioApWakeupLocked() {
            if (this.mWifiRadioApWakeupCount == null) {
                this.mWifiRadioApWakeupCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mWifiRadioApWakeupCount.addCountLocked(1);
        }

        public long getWifiRadioApWakeupCount(int which) {
            if (this.mWifiRadioApWakeupCount != null) {
                return this.mWifiRadioApWakeupCount.getCountLocked(which);
            }
            return 0;
        }

        public void getDeferredJobsCheckinLineLocked(StringBuilder sb, int which) {
            sb.setLength(0);
            int deferredEventCount = this.mJobsDeferredEventCount.getCountLocked(which);
            if (deferredEventCount != 0) {
                int deferredCount = this.mJobsDeferredCount.getCountLocked(which);
                long totalLatency = this.mJobsFreshnessTimeMs.getCountLocked(which);
                sb.append(deferredEventCount);
                sb.append(',');
                sb.append(deferredCount);
                sb.append(',');
                sb.append(totalLatency);
                for (int i = 0; i < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i++) {
                    if (this.mJobsFreshnessBuckets[i] == null) {
                        sb.append(",0");
                    } else {
                        sb.append(SmsManager.REGEX_PREFIX_DELIMITER);
                        sb.append(this.mJobsFreshnessBuckets[i].getCountLocked(which));
                    }
                }
            }
        }

        public void getDeferredJobsLineLocked(StringBuilder sb, int which) {
            sb.setLength(0);
            int deferredEventCount = this.mJobsDeferredEventCount.getCountLocked(which);
            if (deferredEventCount != 0) {
                int deferredCount = this.mJobsDeferredCount.getCountLocked(which);
                long totalLatency = this.mJobsFreshnessTimeMs.getCountLocked(which);
                sb.append("times=");
                sb.append(deferredEventCount);
                sb.append(", ");
                sb.append("count=");
                sb.append(deferredCount);
                sb.append(", ");
                sb.append("totalLatencyMs=");
                sb.append(totalLatency);
                sb.append(", ");
                for (int i = 0; i < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i++) {
                    sb.append("<");
                    sb.append(BatteryStats.JOB_FRESHNESS_BUCKETS[i]);
                    sb.append("ms=");
                    if (this.mJobsFreshnessBuckets[i] == null) {
                        sb.append("0");
                    } else {
                        sb.append(this.mJobsFreshnessBuckets[i].getCountLocked(which));
                    }
                    sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void initNetworkActivityLocked() {
            BatteryStatsImpl.detachIfNotNull((T[]) this.mNetworkByteActivityCounters);
            this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
            BatteryStatsImpl.detachIfNotNull((T[]) this.mNetworkPacketActivityCounters);
            this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
            for (int i = 0; i < 10; i++) {
                this.mNetworkByteActivityCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
                this.mNetworkPacketActivityCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveTime);
            this.mMobileRadioActiveTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveCount);
            this.mMobileRadioActiveCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
        }

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public boolean reset(long uptime, long realtime) {
            boolean active = false;
            this.mOnBatteryBackgroundTimeBase.init(uptime, realtime);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(uptime, realtime);
            if (this.mWifiRunningTimer != null) {
                active = false | (!this.mWifiRunningTimer.reset(false)) | this.mWifiRunning;
            }
            if (this.mFullWifiLockTimer != null) {
                active = active | (!this.mFullWifiLockTimer.reset(false)) | this.mFullWifiLockOut;
            }
            if (this.mWifiScanTimer != null) {
                active = active | (!this.mWifiScanTimer.reset(false)) | this.mWifiScanStarted;
            }
            if (this.mWifiBatchedScanTimer != null) {
                boolean active2 = active;
                for (int i = 0; i < 5; i++) {
                    if (this.mWifiBatchedScanTimer[i] != null) {
                        active2 |= !this.mWifiBatchedScanTimer[i].reset(false);
                    }
                }
                active = (this.mWifiBatchedScanBinStarted != -1) | active2;
            }
            if (this.mWifiMulticastTimer != null) {
                active = active | (!this.mWifiMulticastTimer.reset(false)) | (this.mWifiMulticastWakelockCount > 0);
            }
            boolean active3 = active | (!BatteryStatsImpl.resetIfNotNull(this.mAudioTurnedOnTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mVideoTurnedOnTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mFlashlightTurnedOnTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mCameraTurnedOnTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mForegroundActivityTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mForegroundServiceTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mAggregatedPartialWakelockTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanTimer, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mBluetoothUnoptimizedScanTimer, false));
            boolean unused = BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultCounter, false);
            boolean unused2 = BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultBgCounter, false);
            if (this.mProcessStateTimer != null) {
                boolean active4 = active3;
                for (int i2 = 0; i2 < 7; i2++) {
                    active4 |= !BatteryStatsImpl.resetIfNotNull(this.mProcessStateTimer[i2], false);
                }
                active3 = (this.mProcessState != 21) | active4;
            }
            if (this.mVibratorOnTimer != null) {
                if (this.mVibratorOnTimer.reset(false)) {
                    this.mVibratorOnTimer.detach();
                    this.mVibratorOnTimer = null;
                } else {
                    active3 = true;
                }
            }
            boolean unused3 = BatteryStatsImpl.resetIfNotNull((T[]) this.mUserActivityCounters, false);
            boolean unused4 = BatteryStatsImpl.resetIfNotNull((T[]) this.mNetworkByteActivityCounters, false);
            boolean unused5 = BatteryStatsImpl.resetIfNotNull((T[]) this.mNetworkPacketActivityCounters, false);
            boolean unused6 = BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveTime, false);
            boolean unused7 = BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveCount, false);
            boolean unused8 = BatteryStatsImpl.resetIfNotNull(this.mWifiControllerActivity, false);
            boolean unused9 = BatteryStatsImpl.resetIfNotNull(this.mBluetoothControllerActivity, false);
            boolean unused10 = BatteryStatsImpl.resetIfNotNull(this.mModemControllerActivity, false);
            boolean unused11 = BatteryStatsImpl.resetIfNotNull(this.mUserCpuTime, false);
            boolean unused12 = BatteryStatsImpl.resetIfNotNull(this.mSystemCpuTime, false);
            boolean unused13 = BatteryStatsImpl.resetIfNotNull((T[][]) this.mCpuClusterSpeedTimesUs, false);
            boolean unused14 = BatteryStatsImpl.resetIfNotNull(this.mCpuFreqTimeMs, false);
            boolean unused15 = BatteryStatsImpl.resetIfNotNull(this.mScreenOffCpuFreqTimeMs, false);
            boolean unused16 = BatteryStatsImpl.resetIfNotNull(this.mCpuActiveTimeMs, false);
            boolean unused17 = BatteryStatsImpl.resetIfNotNull(this.mCpuClusterTimesMs, false);
            boolean unused18 = BatteryStatsImpl.resetIfNotNull((T[]) this.mProcStateTimeMs, false);
            boolean unused19 = BatteryStatsImpl.resetIfNotNull((T[]) this.mProcStateScreenOffTimeMs, false);
            boolean unused20 = BatteryStatsImpl.resetIfNotNull(this.mMobileRadioApWakeupCount, false);
            boolean unused21 = BatteryStatsImpl.resetIfNotNull(this.mWifiRadioApWakeupCount, false);
            ArrayMap<String, Wakelock> wakeStats = this.mWakelockStats.getMap();
            for (int iw = wakeStats.size() - 1; iw >= 0; iw--) {
                if (wakeStats.valueAt(iw).reset()) {
                    wakeStats.removeAt(iw);
                } else {
                    active3 = true;
                }
            }
            this.mWakelockStats.cleanup();
            ArrayMap<String, DualTimer> syncStats = this.mSyncStats.getMap();
            for (int is = syncStats.size() - 1; is >= 0; is--) {
                DualTimer timer = syncStats.valueAt(is);
                if (timer.reset(false)) {
                    syncStats.removeAt(is);
                    timer.detach();
                } else {
                    active3 = true;
                }
            }
            this.mSyncStats.cleanup();
            ArrayMap<String, DualTimer> jobStats = this.mJobStats.getMap();
            for (int ij = jobStats.size() - 1; ij >= 0; ij--) {
                DualTimer timer2 = jobStats.valueAt(ij);
                if (timer2.reset(false)) {
                    jobStats.removeAt(ij);
                    timer2.detach();
                } else {
                    active3 = true;
                }
            }
            this.mJobStats.cleanup();
            this.mJobCompletions.clear();
            boolean unused22 = BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredEventCount, false);
            boolean unused23 = BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredCount, false);
            boolean unused24 = BatteryStatsImpl.resetIfNotNull(this.mJobsFreshnessTimeMs, false);
            boolean unused25 = BatteryStatsImpl.resetIfNotNull((T[]) this.mJobsFreshnessBuckets, false);
            for (int ise = this.mSensorStats.size() - 1; ise >= 0; ise--) {
                if (this.mSensorStats.valueAt(ise).reset()) {
                    this.mSensorStats.removeAt(ise);
                } else {
                    active3 = true;
                }
            }
            for (int ip = this.mProcessStats.size() - 1; ip >= 0; ip--) {
                this.mProcessStats.valueAt(ip).detach();
            }
            this.mProcessStats.clear();
            for (int i3 = this.mPids.size() - 1; i3 >= 0; i3--) {
                if (this.mPids.valueAt(i3).mWakeNesting > 0) {
                    active3 = true;
                } else {
                    this.mPids.removeAt(i3);
                }
            }
            for (int i4 = this.mPackageStats.size() - 1; i4 >= 0; i4--) {
                this.mPackageStats.valueAt(i4).detach();
            }
            this.mPackageStats.clear();
            this.mLastStepSystemTime = 0;
            this.mLastStepUserTime = 0;
            this.mCurStepSystemTime = 0;
            this.mCurStepUserTime = 0;
            if (!active3) {
                return true;
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void detachFromTimeBase() {
            BatteryStatsImpl.detachIfNotNull(this.mWifiRunningTimer);
            BatteryStatsImpl.detachIfNotNull(this.mFullWifiLockTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiScanTimer);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mWifiBatchedScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiMulticastTimer);
            BatteryStatsImpl.detachIfNotNull(this.mAudioTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mVideoTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mFlashlightTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mCameraTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mForegroundActivityTimer);
            BatteryStatsImpl.detachIfNotNull(this.mForegroundServiceTimer);
            BatteryStatsImpl.detachIfNotNull(this.mAggregatedPartialWakelockTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothUnoptimizedScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultCounter);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultBgCounter);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mProcessStateTimer);
            BatteryStatsImpl.detachIfNotNull(this.mVibratorOnTimer);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mUserActivityCounters);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mNetworkByteActivityCounters);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mNetworkPacketActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveTime);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveCount);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioApWakeupCount);
            BatteryStatsImpl.detachIfNotNull(this.mWifiRadioApWakeupCount);
            BatteryStatsImpl.detachIfNotNull(this.mWifiControllerActivity);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothControllerActivity);
            BatteryStatsImpl.detachIfNotNull(this.mModemControllerActivity);
            this.mPids.clear();
            BatteryStatsImpl.detachIfNotNull(this.mUserCpuTime);
            BatteryStatsImpl.detachIfNotNull(this.mSystemCpuTime);
            BatteryStatsImpl.detachIfNotNull((T[][]) this.mCpuClusterSpeedTimesUs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuActiveTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuFreqTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mScreenOffCpuFreqTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuClusterTimesMs);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mProcStateTimeMs);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mProcStateScreenOffTimeMs);
            ArrayMap<String, Wakelock> wakeStats = this.mWakelockStats.getMap();
            for (int iw = wakeStats.size() - 1; iw >= 0; iw--) {
                wakeStats.valueAt(iw).detachFromTimeBase();
            }
            ArrayMap<String, DualTimer> syncStats = this.mSyncStats.getMap();
            for (int is = syncStats.size() - 1; is >= 0; is--) {
                BatteryStatsImpl.detachIfNotNull(syncStats.valueAt(is));
            }
            ArrayMap<String, DualTimer> jobStats = this.mJobStats.getMap();
            for (int ij = jobStats.size() - 1; ij >= 0; ij--) {
                BatteryStatsImpl.detachIfNotNull(jobStats.valueAt(ij));
            }
            BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredEventCount);
            BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredCount);
            BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessTimeMs);
            BatteryStatsImpl.detachIfNotNull((T[]) this.mJobsFreshnessBuckets);
            for (int ise = this.mSensorStats.size() - 1; ise >= 0; ise--) {
                this.mSensorStats.valueAt(ise).detachFromTimeBase();
            }
            for (int ip = this.mProcessStats.size() - 1; ip >= 0; ip--) {
                this.mProcessStats.valueAt(ip).detach();
            }
            this.mProcessStats.clear();
            for (int i = this.mPackageStats.size() - 1; i >= 0; i--) {
                this.mPackageStats.valueAt(i).detach();
            }
            this.mPackageStats.clear();
        }

        /* access modifiers changed from: package-private */
        public void writeJobCompletionsToParcelLocked(Parcel out) {
            int NJC = this.mJobCompletions.size();
            out.writeInt(NJC);
            for (int ijc = 0; ijc < NJC; ijc++) {
                out.writeString(this.mJobCompletions.keyAt(ijc));
                SparseIntArray types = this.mJobCompletions.valueAt(ijc);
                int NT = types.size();
                out.writeInt(NT);
                for (int it = 0; it < NT; it++) {
                    out.writeInt(types.keyAt(it));
                    out.writeInt(types.valueAt(it));
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void writeToParcelLocked(Parcel out, long uptimeUs, long elapsedRealtimeUs) {
            ArrayMap<String, DualTimer> syncStats;
            int NW;
            ArrayMap<String, Wakelock> wakeStats;
            ArrayMap<String, DualTimer> syncStats2;
            Parcel parcel = out;
            long j = elapsedRealtimeUs;
            Parcel parcel2 = out;
            long j2 = uptimeUs;
            long j3 = elapsedRealtimeUs;
            this.mOnBatteryBackgroundTimeBase.writeToParcel(parcel2, j2, j3);
            this.mOnBatteryScreenOffBackgroundTimeBase.writeToParcel(parcel2, j2, j3);
            ArrayMap<String, Wakelock> wakeStats2 = this.mWakelockStats.getMap();
            int NW2 = wakeStats2.size();
            parcel.writeInt(NW2);
            int i = 0;
            for (int iw = 0; iw < NW2; iw++) {
                parcel.writeString(wakeStats2.keyAt(iw));
                wakeStats2.valueAt(iw).writeToParcelLocked(parcel, j);
            }
            ArrayMap<String, DualTimer> syncStats3 = this.mSyncStats.getMap();
            int NS = syncStats3.size();
            parcel.writeInt(NS);
            for (int is = 0; is < NS; is++) {
                parcel.writeString(syncStats3.keyAt(is));
                Timer.writeTimerToParcel(parcel, syncStats3.valueAt(is), j);
            }
            ArrayMap<String, DualTimer> jobStats = this.mJobStats.getMap();
            int NJ = jobStats.size();
            parcel.writeInt(NJ);
            for (int ij = 0; ij < NJ; ij++) {
                parcel.writeString(jobStats.keyAt(ij));
                Timer.writeTimerToParcel(parcel, jobStats.valueAt(ij), j);
            }
            writeJobCompletionsToParcelLocked(out);
            this.mJobsDeferredEventCount.writeToParcel(parcel);
            this.mJobsDeferredCount.writeToParcel(parcel);
            this.mJobsFreshnessTimeMs.writeToParcel(parcel);
            for (int i2 = 0; i2 < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i2++) {
                Counter.writeCounterToParcel(parcel, this.mJobsFreshnessBuckets[i2]);
            }
            int NSE = this.mSensorStats.size();
            parcel.writeInt(NSE);
            for (int ise = 0; ise < NSE; ise++) {
                parcel.writeInt(this.mSensorStats.keyAt(ise));
                this.mSensorStats.valueAt(ise).writeToParcelLocked(parcel, j);
            }
            int NP = this.mProcessStats.size();
            parcel.writeInt(NP);
            for (int ip = 0; ip < NP; ip++) {
                parcel.writeString(this.mProcessStats.keyAt(ip));
                this.mProcessStats.valueAt(ip).writeToParcelLocked(parcel);
            }
            parcel.writeInt(this.mPackageStats.size());
            for (Map.Entry<String, Pkg> pkgEntry : this.mPackageStats.entrySet()) {
                parcel.writeString(pkgEntry.getKey());
                pkgEntry.getValue().writeToParcelLocked(parcel);
            }
            if (this.mWifiRunningTimer != null) {
                parcel.writeInt(1);
                this.mWifiRunningTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mFullWifiLockTimer != null) {
                parcel.writeInt(1);
                this.mFullWifiLockTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mWifiScanTimer != null) {
                parcel.writeInt(1);
                this.mWifiScanTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            for (int i3 = 0; i3 < 5; i3++) {
                if (this.mWifiBatchedScanTimer[i3] != null) {
                    parcel.writeInt(1);
                    this.mWifiBatchedScanTimer[i3].writeToParcel(parcel, j);
                } else {
                    parcel.writeInt(0);
                }
            }
            if (this.mWifiMulticastTimer != null) {
                parcel.writeInt(1);
                this.mWifiMulticastTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mAudioTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mAudioTurnedOnTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mVideoTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mVideoTurnedOnTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mFlashlightTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mFlashlightTurnedOnTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mCameraTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mCameraTurnedOnTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mForegroundActivityTimer != null) {
                parcel.writeInt(1);
                this.mForegroundActivityTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mForegroundServiceTimer != null) {
                parcel.writeInt(1);
                this.mForegroundServiceTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mAggregatedPartialWakelockTimer != null) {
                parcel.writeInt(1);
                this.mAggregatedPartialWakelockTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothScanTimer != null) {
                parcel.writeInt(1);
                this.mBluetoothScanTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothUnoptimizedScanTimer != null) {
                parcel.writeInt(1);
                this.mBluetoothUnoptimizedScanTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothScanResultCounter != null) {
                parcel.writeInt(1);
                this.mBluetoothScanResultCounter.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothScanResultBgCounter != null) {
                parcel.writeInt(1);
                this.mBluetoothScanResultBgCounter.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            for (int i4 = 0; i4 < 7; i4++) {
                if (this.mProcessStateTimer[i4] != null) {
                    parcel.writeInt(1);
                    this.mProcessStateTimer[i4].writeToParcel(parcel, j);
                } else {
                    parcel.writeInt(0);
                }
            }
            if (this.mVibratorOnTimer != null) {
                parcel.writeInt(1);
                this.mVibratorOnTimer.writeToParcel(parcel, j);
            } else {
                parcel.writeInt(0);
            }
            if (this.mUserActivityCounters != null) {
                parcel.writeInt(1);
                for (int i5 = 0; i5 < NUM_USER_ACTIVITY_TYPES; i5++) {
                    this.mUserActivityCounters[i5].writeToParcel(parcel);
                }
            } else {
                parcel.writeInt(0);
            }
            if (this.mNetworkByteActivityCounters != null) {
                parcel.writeInt(1);
                for (int i6 = 0; i6 < 10; i6++) {
                    this.mNetworkByteActivityCounters[i6].writeToParcel(parcel);
                    this.mNetworkPacketActivityCounters[i6].writeToParcel(parcel);
                }
                this.mMobileRadioActiveTime.writeToParcel(parcel);
                this.mMobileRadioActiveCount.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (this.mWifiControllerActivity != null) {
                parcel.writeInt(1);
                this.mWifiControllerActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothControllerActivity != null) {
                parcel.writeInt(1);
                this.mBluetoothControllerActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (this.mModemControllerActivity != null) {
                parcel.writeInt(1);
                this.mModemControllerActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mUserCpuTime.writeToParcel(parcel);
            this.mSystemCpuTime.writeToParcel(parcel);
            if (this.mCpuClusterSpeedTimesUs != null) {
                parcel.writeInt(1);
                parcel.writeInt(this.mCpuClusterSpeedTimesUs.length);
                LongSamplingCounter[][] longSamplingCounterArr = this.mCpuClusterSpeedTimesUs;
                int length = longSamplingCounterArr.length;
                while (i < length) {
                    LongSamplingCounter[] cpuSpeeds = longSamplingCounterArr[i];
                    if (cpuSpeeds != null) {
                        wakeStats = wakeStats2;
                        parcel.writeInt(1);
                        parcel.writeInt(cpuSpeeds.length);
                        int length2 = cpuSpeeds.length;
                        NW = NW2;
                        int NW3 = 0;
                        while (NW3 < length2) {
                            int i7 = length2;
                            LongSamplingCounter c = cpuSpeeds[NW3];
                            if (c != null) {
                                syncStats2 = syncStats3;
                                parcel.writeInt(1);
                                c.writeToParcel(parcel);
                            } else {
                                syncStats2 = syncStats3;
                                parcel.writeInt(0);
                            }
                            NW3++;
                            length2 = i7;
                            syncStats3 = syncStats2;
                        }
                        syncStats = syncStats3;
                    } else {
                        wakeStats = wakeStats2;
                        NW = NW2;
                        syncStats = syncStats3;
                        parcel.writeInt(0);
                    }
                    i++;
                    wakeStats2 = wakeStats;
                    NW2 = NW;
                    syncStats3 = syncStats;
                }
                int i8 = NW2;
                ArrayMap<String, DualTimer> arrayMap = syncStats3;
            } else {
                int i9 = NW2;
                ArrayMap<String, DualTimer> arrayMap2 = syncStats3;
                parcel.writeInt(0);
            }
            LongSamplingCounterArray.writeToParcel(parcel, this.mCpuFreqTimeMs);
            LongSamplingCounterArray.writeToParcel(parcel, this.mScreenOffCpuFreqTimeMs);
            this.mCpuActiveTimeMs.writeToParcel(parcel);
            this.mCpuClusterTimesMs.writeToParcel(parcel);
            if (this.mProcStateTimeMs != null) {
                parcel.writeInt(this.mProcStateTimeMs.length);
                for (LongSamplingCounterArray counters : this.mProcStateTimeMs) {
                    LongSamplingCounterArray.writeToParcel(parcel, counters);
                }
            } else {
                parcel.writeInt(0);
            }
            if (this.mProcStateScreenOffTimeMs != null) {
                parcel.writeInt(this.mProcStateScreenOffTimeMs.length);
                for (LongSamplingCounterArray counters2 : this.mProcStateScreenOffTimeMs) {
                    LongSamplingCounterArray.writeToParcel(parcel, counters2);
                }
            } else {
                parcel.writeInt(0);
            }
            if (this.mMobileRadioApWakeupCount != null) {
                parcel.writeInt(1);
                this.mMobileRadioApWakeupCount.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (this.mWifiRadioApWakeupCount != null) {
                parcel.writeInt(1);
                this.mWifiRadioApWakeupCount.writeToParcel(parcel);
                return;
            }
            parcel.writeInt(0);
        }

        /* access modifiers changed from: package-private */
        public void readJobCompletionsFromParcelLocked(Parcel in) {
            int numJobCompletions = in.readInt();
            this.mJobCompletions.clear();
            for (int j = 0; j < numJobCompletions; j++) {
                String jobName = in.readString();
                int numTypes = in.readInt();
                if (numTypes > 0) {
                    SparseIntArray types = new SparseIntArray();
                    for (int k = 0; k < numTypes; k++) {
                        types.put(in.readInt(), in.readInt());
                    }
                    this.mJobCompletions.put(jobName, types);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void readFromParcelLocked(TimeBase timeBase, TimeBase screenOffTimeBase, Parcel in) {
            DualTimer dualTimer;
            DualTimer dualTimer2;
            int procState;
            int numProcs;
            int numJobs;
            int numWakelocks;
            Parcel parcel = in;
            this.mOnBatteryBackgroundTimeBase.readFromParcel(parcel);
            this.mOnBatteryScreenOffBackgroundTimeBase.readFromParcel(parcel);
            int numWakelocks2 = in.readInt();
            this.mWakelockStats.clear();
            for (int j = 0; j < numWakelocks2; j++) {
                String wakelockName = in.readString();
                Wakelock wakelock = new Wakelock(this.mBsi, this);
                wakelock.readFromParcelLocked(timeBase, screenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase, parcel);
                this.mWakelockStats.add(wakelockName, wakelock);
            }
            TimeBase timeBase2 = timeBase;
            TimeBase timeBase3 = screenOffTimeBase;
            int numSyncs = in.readInt();
            this.mSyncStats.clear();
            int j2 = 0;
            while (true) {
                int j3 = j2;
                if (j3 >= numSyncs) {
                    break;
                }
                String syncName = in.readString();
                if (in.readInt() != 0) {
                    OverflowArrayMap<DualTimer> overflowArrayMap = this.mSyncStats;
                    DualTimer dualTimer3 = r0;
                    numWakelocks = numWakelocks2;
                    DualTimer dualTimer4 = new DualTimer(this.mBsi.mClocks, this, 13, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, in);
                    overflowArrayMap.add(syncName, dualTimer3);
                } else {
                    numWakelocks = numWakelocks2;
                }
                j2 = j3 + 1;
                numWakelocks2 = numWakelocks;
            }
            int numJobs2 = in.readInt();
            this.mJobStats.clear();
            int j4 = 0;
            while (true) {
                int j5 = j4;
                if (j5 >= numJobs2) {
                    break;
                }
                String jobName = in.readString();
                if (in.readInt() != 0) {
                    OverflowArrayMap<DualTimer> overflowArrayMap2 = this.mJobStats;
                    DualTimer dualTimer5 = r0;
                    numJobs = numJobs2;
                    DualTimer dualTimer6 = new DualTimer(this.mBsi.mClocks, this, 14, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, in);
                    overflowArrayMap2.add(jobName, dualTimer5);
                } else {
                    numJobs = numJobs2;
                }
                j4 = j5 + 1;
                numJobs2 = numJobs;
            }
            readJobCompletionsFromParcelLocked(parcel);
            this.mJobsDeferredEventCount = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mJobsDeferredCount = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mJobsFreshnessTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            for (int i = 0; i < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i++) {
                this.mJobsFreshnessBuckets[i] = Counter.readCounterFromParcel(this.mBsi.mOnBatteryTimeBase, parcel);
            }
            int numSensors = in.readInt();
            this.mSensorStats.clear();
            for (int k = 0; k < numSensors; k++) {
                int sensorNumber = in.readInt();
                Sensor sensor = new Sensor(this.mBsi, this, sensorNumber);
                sensor.readFromParcelLocked(this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel);
                this.mSensorStats.put(sensorNumber, sensor);
            }
            int numProcs2 = in.readInt();
            this.mProcessStats.clear();
            for (int k2 = 0; k2 < numProcs2; k2++) {
                String processName = in.readString();
                Proc proc = new Proc(this.mBsi, processName);
                proc.readFromParcelLocked(parcel);
                this.mProcessStats.put(processName, proc);
            }
            int numPkgs = in.readInt();
            this.mPackageStats.clear();
            for (int l = 0; l < numPkgs; l++) {
                String packageName = in.readString();
                Pkg pkg = new Pkg(this.mBsi);
                pkg.readFromParcelLocked(parcel);
                this.mPackageStats.put(packageName, pkg);
            }
            this.mWifiRunning = false;
            if (in.readInt() != 0) {
                StopwatchTimer stopwatchTimer = r0;
                StopwatchTimer stopwatchTimer2 = new StopwatchTimer(this.mBsi.mClocks, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase, in);
                this.mWifiRunningTimer = stopwatchTimer;
            } else {
                this.mWifiRunningTimer = null;
            }
            this.mFullWifiLockOut = false;
            if (in.readInt() != 0) {
                this.mFullWifiLockTimer = new StopwatchTimer(this.mBsi.mClocks, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase, in);
                dualTimer = null;
            } else {
                dualTimer = null;
                this.mFullWifiLockTimer = null;
            }
            this.mWifiScanStarted = false;
            if (in.readInt() != 0) {
                DualTimer dualTimer7 = r0;
                int i2 = numSensors;
                dualTimer2 = dualTimer;
                DualTimer dualTimer8 = new DualTimer(this.mBsi.mClocks, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, in);
                this.mWifiScanTimer = dualTimer7;
            } else {
                dualTimer2 = dualTimer;
                this.mWifiScanTimer = dualTimer2;
            }
            this.mWifiBatchedScanBinStarted = -1;
            for (int i3 = 0; i3 < 5; i3++) {
                if (in.readInt() != 0) {
                    makeWifiBatchedScanBin(i3, parcel);
                } else {
                    this.mWifiBatchedScanTimer[i3] = dualTimer2;
                }
            }
            this.mWifiMulticastWakelockCount = 0;
            if (in.readInt() != 0) {
                StopwatchTimer stopwatchTimer3 = r0;
                procState = 0;
                StopwatchTimer stopwatchTimer4 = new StopwatchTimer(this.mBsi.mClocks, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase, in);
                this.mWifiMulticastTimer = stopwatchTimer3;
            } else {
                procState = 0;
                this.mWifiMulticastTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                this.mAudioTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 15, this.mBsi.mAudioTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mAudioTurnedOnTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                this.mVideoTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 8, this.mBsi.mVideoTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mVideoTurnedOnTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                this.mFlashlightTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 16, this.mBsi.mFlashlightTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mFlashlightTurnedOnTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                this.mCameraTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 17, this.mBsi.mCameraTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mCameraTurnedOnTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                this.mForegroundActivityTimer = new StopwatchTimer(this.mBsi.mClocks, this, 10, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mForegroundActivityTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                this.mForegroundServiceTimer = new StopwatchTimer(this.mBsi.mClocks, this, 22, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mForegroundServiceTimer = dualTimer2;
            }
            if (in.readInt() != 0) {
                DualTimer dualTimer9 = r0;
                int i4 = numProcs2;
                numProcs = 5;
                DualTimer dualTimer10 = new DualTimer(this.mBsi.mClocks, this, 20, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase, in);
                this.mAggregatedPartialWakelockTimer = dualTimer9;
            } else {
                numProcs = 5;
                this.mAggregatedPartialWakelockTimer = null;
            }
            if (in.readInt() != 0) {
                this.mBluetoothScanTimer = new DualTimer(this.mBsi.mClocks, this, 19, this.mBsi.mBluetoothScanOnTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, in);
            } else {
                this.mBluetoothScanTimer = null;
            }
            if (in.readInt() != 0) {
                this.mBluetoothUnoptimizedScanTimer = new DualTimer(this.mBsi.mClocks, this, 21, (ArrayList<StopwatchTimer>) null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, in);
            } else {
                this.mBluetoothUnoptimizedScanTimer = null;
            }
            if (in.readInt() != 0) {
                this.mBluetoothScanResultCounter = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
            } else {
                this.mBluetoothScanResultCounter = null;
            }
            if (in.readInt() != 0) {
                this.mBluetoothScanResultBgCounter = new Counter(this.mOnBatteryBackgroundTimeBase, parcel);
            } else {
                this.mBluetoothScanResultBgCounter = null;
            }
            this.mProcessState = 21;
            for (int i5 = procState; i5 < 7; i5++) {
                if (in.readInt() != 0) {
                    makeProcessState(i5, parcel);
                } else {
                    this.mProcessStateTimer[i5] = null;
                }
            }
            if (in.readInt() != 0) {
                this.mVibratorOnTimer = new BatchTimer(this.mBsi.mClocks, this, 9, this.mBsi.mOnBatteryTimeBase, in);
            } else {
                this.mVibratorOnTimer = null;
            }
            if (in.readInt() != 0) {
                this.mUserActivityCounters = new Counter[NUM_USER_ACTIVITY_TYPES];
                for (int i6 = procState; i6 < NUM_USER_ACTIVITY_TYPES; i6++) {
                    this.mUserActivityCounters[i6] = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
                }
            } else {
                this.mUserActivityCounters = null;
            }
            if (in.readInt() != 0) {
                this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
                this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
                for (int i7 = procState; i7 < 10; i7++) {
                    this.mNetworkByteActivityCounters[i7] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                    this.mNetworkPacketActivityCounters[i7] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                }
                this.mMobileRadioActiveTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                this.mMobileRadioActiveCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            } else {
                this.mNetworkByteActivityCounters = null;
                this.mNetworkPacketActivityCounters = null;
            }
            if (in.readInt() != 0) {
                this.mWifiControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1, parcel);
            } else {
                this.mWifiControllerActivity = null;
            }
            if (in.readInt() != 0) {
                this.mBluetoothControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1, parcel);
            } else {
                this.mBluetoothControllerActivity = null;
            }
            if (in.readInt() != 0) {
                this.mModemControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, numProcs, parcel);
            } else {
                this.mModemControllerActivity = null;
            }
            this.mUserCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mSystemCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            if (in.readInt() != 0) {
                int numCpuClusters = in.readInt();
                if (this.mBsi.mPowerProfile == null || this.mBsi.mPowerProfile.getNumCpuClusters() == numCpuClusters) {
                    this.mCpuClusterSpeedTimesUs = new LongSamplingCounter[numCpuClusters][];
                    for (int cluster = procState; cluster < numCpuClusters; cluster++) {
                        if (in.readInt() != 0) {
                            int numSpeeds = in.readInt();
                            if (this.mBsi.mPowerProfile == null || this.mBsi.mPowerProfile.getNumSpeedStepsInCpuCluster(cluster) == numSpeeds) {
                                LongSamplingCounter[] cpuSpeeds = new LongSamplingCounter[numSpeeds];
                                this.mCpuClusterSpeedTimesUs[cluster] = cpuSpeeds;
                                for (int speed = procState; speed < numSpeeds; speed++) {
                                    if (in.readInt() != 0) {
                                        cpuSpeeds[speed] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                                    }
                                }
                            } else {
                                throw new ParcelFormatException("Incompatible number of cpu speeds");
                            }
                        } else {
                            this.mCpuClusterSpeedTimesUs[cluster] = null;
                        }
                    }
                } else {
                    throw new ParcelFormatException("Incompatible number of cpu clusters");
                }
            } else {
                this.mCpuClusterSpeedTimesUs = null;
            }
            this.mCpuFreqTimeMs = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryTimeBase);
            this.mScreenOffCpuFreqTimeMs = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryScreenOffTimeBase);
            this.mCpuActiveTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mCpuClusterTimesMs = new LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase, parcel);
            int length = in.readInt();
            if (length == 7) {
                this.mProcStateTimeMs = new LongSamplingCounterArray[length];
                for (int procState2 = procState; procState2 < length; procState2++) {
                    this.mProcStateTimeMs[procState2] = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryTimeBase);
                }
            } else {
                this.mProcStateTimeMs = null;
            }
            int length2 = in.readInt();
            if (length2 == 7) {
                this.mProcStateScreenOffTimeMs = new LongSamplingCounterArray[length2];
                while (true) {
                    int procState3 = procState;
                    if (procState3 >= length2) {
                        break;
                    }
                    this.mProcStateScreenOffTimeMs[procState3] = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryScreenOffTimeBase);
                    procState = procState3 + 1;
                }
            } else {
                this.mProcStateScreenOffTimeMs = null;
            }
            if (in.readInt() != 0) {
                this.mMobileRadioApWakeupCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            } else {
                this.mMobileRadioApWakeupCount = null;
            }
            if (in.readInt() != 0) {
                this.mWifiRadioApWakeupCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            } else {
                this.mWifiRadioApWakeupCount = null;
            }
        }

        public void noteJobsDeferredLocked(int numDeferred, long sinceLast) {
            this.mJobsDeferredEventCount.addAtomic(1);
            this.mJobsDeferredCount.addAtomic(numDeferred);
            if (sinceLast != 0) {
                this.mJobsFreshnessTimeMs.addCountLocked(sinceLast);
                for (int i = 0; i < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i++) {
                    if (sinceLast < BatteryStats.JOB_FRESHNESS_BUCKETS[i]) {
                        if (this.mJobsFreshnessBuckets[i] == null) {
                            this.mJobsFreshnessBuckets[i] = new Counter(this.mBsi.mOnBatteryTimeBase);
                        }
                        this.mJobsFreshnessBuckets[i].addAtomic(1);
                        return;
                    }
                }
            }
        }

        public static class Wakelock extends BatteryStats.Uid.Wakelock {
            protected BatteryStatsImpl mBsi;
            StopwatchTimer mTimerDraw;
            StopwatchTimer mTimerFull;
            DualTimer mTimerPartial;
            StopwatchTimer mTimerWindow;
            protected Uid mUid;

            public Wakelock(BatteryStatsImpl bsi, Uid uid) {
                this.mBsi = bsi;
                this.mUid = uid;
            }

            private StopwatchTimer readStopwatchTimerFromParcel(int type, ArrayList<StopwatchTimer> pool, TimeBase timeBase, Parcel in) {
                if (in.readInt() == 0) {
                    return null;
                }
                return new StopwatchTimer(this.mBsi.mClocks, this.mUid, type, pool, timeBase, in);
            }

            private DualTimer readDualTimerFromParcel(int type, ArrayList<StopwatchTimer> pool, TimeBase timeBase, TimeBase bgTimeBase, Parcel in) {
                if (in.readInt() == 0) {
                    return null;
                }
                return new DualTimer(this.mBsi.mClocks, this.mUid, type, pool, timeBase, bgTimeBase, in);
            }

            /* access modifiers changed from: package-private */
            public boolean reset() {
                boolean wlactive = false | (!BatteryStatsImpl.resetIfNotNull(this.mTimerFull, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mTimerPartial, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mTimerWindow, false)) | (!BatteryStatsImpl.resetIfNotNull(this.mTimerDraw, false));
                if (!wlactive) {
                    BatteryStatsImpl.detachIfNotNull(this.mTimerFull);
                    this.mTimerFull = null;
                    BatteryStatsImpl.detachIfNotNull(this.mTimerPartial);
                    this.mTimerPartial = null;
                    BatteryStatsImpl.detachIfNotNull(this.mTimerWindow);
                    this.mTimerWindow = null;
                    BatteryStatsImpl.detachIfNotNull(this.mTimerDraw);
                    this.mTimerDraw = null;
                }
                if (!wlactive) {
                    return true;
                }
                return false;
            }

            /* access modifiers changed from: package-private */
            public void readFromParcelLocked(TimeBase timeBase, TimeBase screenOffTimeBase, TimeBase screenOffBgTimeBase, Parcel in) {
                this.mTimerPartial = readDualTimerFromParcel(0, this.mBsi.mPartialTimers, screenOffTimeBase, screenOffBgTimeBase, in);
                this.mTimerFull = readStopwatchTimerFromParcel(1, this.mBsi.mFullTimers, timeBase, in);
                this.mTimerWindow = readStopwatchTimerFromParcel(2, this.mBsi.mWindowTimers, timeBase, in);
                this.mTimerDraw = readStopwatchTimerFromParcel(18, this.mBsi.mDrawTimers, timeBase, in);
            }

            /* access modifiers changed from: package-private */
            public void writeToParcelLocked(Parcel out, long elapsedRealtimeUs) {
                Timer.writeTimerToParcel(out, this.mTimerPartial, elapsedRealtimeUs);
                Timer.writeTimerToParcel(out, this.mTimerFull, elapsedRealtimeUs);
                Timer.writeTimerToParcel(out, this.mTimerWindow, elapsedRealtimeUs);
                Timer.writeTimerToParcel(out, this.mTimerDraw, elapsedRealtimeUs);
            }

            @UnsupportedAppUsage
            public Timer getWakeTime(int type) {
                if (type == 18) {
                    return this.mTimerDraw;
                }
                switch (type) {
                    case 0:
                        return this.mTimerPartial;
                    case 1:
                        return this.mTimerFull;
                    case 2:
                        return this.mTimerWindow;
                    default:
                        throw new IllegalArgumentException("type = " + type);
                }
            }

            public void detachFromTimeBase() {
                BatteryStatsImpl.detachIfNotNull(this.mTimerPartial);
                BatteryStatsImpl.detachIfNotNull(this.mTimerFull);
                BatteryStatsImpl.detachIfNotNull(this.mTimerWindow);
                BatteryStatsImpl.detachIfNotNull(this.mTimerDraw);
            }
        }

        public static class Sensor extends BatteryStats.Uid.Sensor {
            protected BatteryStatsImpl mBsi;
            final int mHandle;
            DualTimer mTimer;
            protected Uid mUid;

            public Sensor(BatteryStatsImpl bsi, Uid uid, int handle) {
                this.mBsi = bsi;
                this.mUid = uid;
                this.mHandle = handle;
            }

            private DualTimer readTimersFromParcel(TimeBase timeBase, TimeBase bgTimeBase, Parcel in) {
                if (in.readInt() == 0) {
                    return null;
                }
                ArrayList<StopwatchTimer> pool = this.mBsi.mSensorTimers.get(this.mHandle);
                if (pool == null) {
                    pool = new ArrayList<>();
                    this.mBsi.mSensorTimers.put(this.mHandle, pool);
                }
                return new DualTimer(this.mBsi.mClocks, this.mUid, 0, pool, timeBase, bgTimeBase, in);
            }

            /* access modifiers changed from: package-private */
            public boolean reset() {
                if (!this.mTimer.reset(true)) {
                    return false;
                }
                this.mTimer = null;
                return true;
            }

            /* access modifiers changed from: package-private */
            public void readFromParcelLocked(TimeBase timeBase, TimeBase bgTimeBase, Parcel in) {
                this.mTimer = readTimersFromParcel(timeBase, bgTimeBase, in);
            }

            /* access modifiers changed from: package-private */
            public void writeToParcelLocked(Parcel out, long elapsedRealtimeUs) {
                Timer.writeTimerToParcel(out, this.mTimer, elapsedRealtimeUs);
            }

            @UnsupportedAppUsage
            public Timer getSensorTime() {
                return this.mTimer;
            }

            public Timer getSensorBackgroundTime() {
                if (this.mTimer == null) {
                    return null;
                }
                return this.mTimer.getSubTimer();
            }

            @UnsupportedAppUsage
            public int getHandle() {
                return this.mHandle;
            }

            public void detachFromTimeBase() {
                BatteryStatsImpl.detachIfNotNull(this.mTimer);
            }
        }

        public static class Proc extends BatteryStats.Uid.Proc implements TimeBaseObs {
            boolean mActive = true;
            protected BatteryStatsImpl mBsi;
            ArrayList<BatteryStats.Uid.Proc.ExcessivePower> mExcessivePower;
            long mForegroundTime;
            final String mName;
            int mNumAnrs;
            int mNumCrashes;
            int mStarts;
            long mSystemTime;
            long mUserTime;

            public Proc(BatteryStatsImpl bsi, String name) {
                this.mBsi = bsi;
                this.mName = name;
                this.mBsi.mOnBatteryTimeBase.add(this);
            }

            public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
            }

            public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
            }

            public boolean reset(boolean detachIfReset) {
                if (!detachIfReset) {
                    return true;
                }
                detach();
                return true;
            }

            public void detach() {
                this.mActive = false;
                this.mBsi.mOnBatteryTimeBase.remove(this);
            }

            public int countExcessivePowers() {
                if (this.mExcessivePower != null) {
                    return this.mExcessivePower.size();
                }
                return 0;
            }

            public BatteryStats.Uid.Proc.ExcessivePower getExcessivePower(int i) {
                if (this.mExcessivePower != null) {
                    return this.mExcessivePower.get(i);
                }
                return null;
            }

            public void addExcessiveCpu(long overTime, long usedTime) {
                if (this.mExcessivePower == null) {
                    this.mExcessivePower = new ArrayList<>();
                }
                BatteryStats.Uid.Proc.ExcessivePower ew = new BatteryStats.Uid.Proc.ExcessivePower();
                ew.type = 2;
                ew.overTime = overTime;
                ew.usedTime = usedTime;
                this.mExcessivePower.add(ew);
            }

            /* access modifiers changed from: package-private */
            public void writeExcessivePowerToParcelLocked(Parcel out) {
                if (this.mExcessivePower == null) {
                    out.writeInt(0);
                    return;
                }
                int N = this.mExcessivePower.size();
                out.writeInt(N);
                for (int i = 0; i < N; i++) {
                    BatteryStats.Uid.Proc.ExcessivePower ew = this.mExcessivePower.get(i);
                    out.writeInt(ew.type);
                    out.writeLong(ew.overTime);
                    out.writeLong(ew.usedTime);
                }
            }

            /* access modifiers changed from: package-private */
            public void readExcessivePowerFromParcelLocked(Parcel in) {
                int N = in.readInt();
                if (N == 0) {
                    this.mExcessivePower = null;
                } else if (N <= 10000) {
                    this.mExcessivePower = new ArrayList<>();
                    for (int i = 0; i < N; i++) {
                        BatteryStats.Uid.Proc.ExcessivePower ew = new BatteryStats.Uid.Proc.ExcessivePower();
                        ew.type = in.readInt();
                        ew.overTime = in.readLong();
                        ew.usedTime = in.readLong();
                        this.mExcessivePower.add(ew);
                    }
                } else {
                    throw new ParcelFormatException("File corrupt: too many excessive power entries " + N);
                }
            }

            /* access modifiers changed from: package-private */
            public void writeToParcelLocked(Parcel out) {
                out.writeLong(this.mUserTime);
                out.writeLong(this.mSystemTime);
                out.writeLong(this.mForegroundTime);
                out.writeInt(this.mStarts);
                out.writeInt(this.mNumCrashes);
                out.writeInt(this.mNumAnrs);
                writeExcessivePowerToParcelLocked(out);
            }

            /* access modifiers changed from: package-private */
            public void readFromParcelLocked(Parcel in) {
                this.mUserTime = in.readLong();
                this.mSystemTime = in.readLong();
                this.mForegroundTime = in.readLong();
                this.mStarts = in.readInt();
                this.mNumCrashes = in.readInt();
                this.mNumAnrs = in.readInt();
                readExcessivePowerFromParcelLocked(in);
            }

            @UnsupportedAppUsage
            public void addCpuTimeLocked(int utime, int stime) {
                addCpuTimeLocked(utime, stime, this.mBsi.mOnBatteryTimeBase.isRunning());
            }

            public void addCpuTimeLocked(int utime, int stime, boolean isRunning) {
                if (isRunning) {
                    this.mUserTime += (long) utime;
                    this.mSystemTime += (long) stime;
                }
            }

            @UnsupportedAppUsage
            public void addForegroundTimeLocked(long ttime) {
                this.mForegroundTime += ttime;
            }

            @UnsupportedAppUsage
            public void incStartsLocked() {
                this.mStarts++;
            }

            public void incNumCrashesLocked() {
                this.mNumCrashes++;
            }

            public void incNumAnrsLocked() {
                this.mNumAnrs++;
            }

            public boolean isActive() {
                return this.mActive;
            }

            @UnsupportedAppUsage
            public long getUserTime(int which) {
                return this.mUserTime;
            }

            @UnsupportedAppUsage
            public long getSystemTime(int which) {
                return this.mSystemTime;
            }

            @UnsupportedAppUsage
            public long getForegroundTime(int which) {
                return this.mForegroundTime;
            }

            @UnsupportedAppUsage
            public int getStarts(int which) {
                return this.mStarts;
            }

            public int getNumCrashes(int which) {
                return this.mNumCrashes;
            }

            public int getNumAnrs(int which) {
                return this.mNumAnrs;
            }
        }

        public static class Pkg extends BatteryStats.Uid.Pkg implements TimeBaseObs {
            protected BatteryStatsImpl mBsi;
            final ArrayMap<String, Serv> mServiceStats = new ArrayMap<>();
            ArrayMap<String, Counter> mWakeupAlarms = new ArrayMap<>();

            public Pkg(BatteryStatsImpl bsi) {
                this.mBsi = bsi;
                this.mBsi.mOnBatteryScreenOffTimeBase.add(this);
            }

            public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
            }

            public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
            }

            public boolean reset(boolean detachIfReset) {
                if (!detachIfReset) {
                    return true;
                }
                detach();
                return true;
            }

            public void detach() {
                this.mBsi.mOnBatteryScreenOffTimeBase.remove(this);
                for (int j = this.mWakeupAlarms.size() - 1; j >= 0; j--) {
                    BatteryStatsImpl.detachIfNotNull(this.mWakeupAlarms.valueAt(j));
                }
                for (int j2 = this.mServiceStats.size() - 1; j2 >= 0; j2--) {
                    BatteryStatsImpl.detachIfNotNull(this.mServiceStats.valueAt(j2));
                }
            }

            /* access modifiers changed from: package-private */
            public void readFromParcelLocked(Parcel in) {
                int numWA = in.readInt();
                this.mWakeupAlarms.clear();
                for (int i = 0; i < numWA; i++) {
                    this.mWakeupAlarms.put(in.readString(), new Counter(this.mBsi.mOnBatteryScreenOffTimeBase, in));
                }
                int numServs = in.readInt();
                this.mServiceStats.clear();
                for (int m = 0; m < numServs; m++) {
                    String serviceName = in.readString();
                    Serv serv = new Serv(this.mBsi);
                    this.mServiceStats.put(serviceName, serv);
                    serv.readFromParcelLocked(in);
                }
            }

            /* access modifiers changed from: package-private */
            public void writeToParcelLocked(Parcel out) {
                int numWA = this.mWakeupAlarms.size();
                out.writeInt(numWA);
                for (int i = 0; i < numWA; i++) {
                    out.writeString(this.mWakeupAlarms.keyAt(i));
                    this.mWakeupAlarms.valueAt(i).writeToParcel(out);
                }
                int NS = this.mServiceStats.size();
                out.writeInt(NS);
                for (int i2 = 0; i2 < NS; i2++) {
                    out.writeString(this.mServiceStats.keyAt(i2));
                    this.mServiceStats.valueAt(i2).writeToParcelLocked(out);
                }
            }

            public ArrayMap<String, ? extends BatteryStats.Counter> getWakeupAlarmStats() {
                return this.mWakeupAlarms;
            }

            public void noteWakeupAlarmLocked(String tag) {
                Counter c = this.mWakeupAlarms.get(tag);
                if (c == null) {
                    c = new Counter(this.mBsi.mOnBatteryScreenOffTimeBase);
                    this.mWakeupAlarms.put(tag, c);
                }
                c.stepAtomic();
            }

            public ArrayMap<String, ? extends BatteryStats.Uid.Pkg.Serv> getServiceStats() {
                return this.mServiceStats;
            }

            public static class Serv extends BatteryStats.Uid.Pkg.Serv implements TimeBaseObs {
                protected BatteryStatsImpl mBsi;
                protected boolean mLaunched;
                protected long mLaunchedSince;
                protected long mLaunchedTime;
                protected int mLaunches;
                protected Pkg mPkg;
                protected boolean mRunning;
                protected long mRunningSince;
                protected long mStartTime;
                protected int mStarts;

                public Serv(BatteryStatsImpl bsi) {
                    this.mBsi = bsi;
                    this.mBsi.mOnBatteryTimeBase.add(this);
                }

                public void onTimeStarted(long elapsedRealtime, long baseUptime, long baseRealtime) {
                }

                public void onTimeStopped(long elapsedRealtime, long baseUptime, long baseRealtime) {
                }

                public boolean reset(boolean detachIfReset) {
                    if (!detachIfReset) {
                        return true;
                    }
                    detach();
                    return true;
                }

                public void detach() {
                    this.mBsi.mOnBatteryTimeBase.remove(this);
                }

                public void readFromParcelLocked(Parcel in) {
                    this.mStartTime = in.readLong();
                    this.mRunningSince = in.readLong();
                    boolean z = false;
                    this.mRunning = in.readInt() != 0;
                    this.mStarts = in.readInt();
                    this.mLaunchedTime = in.readLong();
                    this.mLaunchedSince = in.readLong();
                    if (in.readInt() != 0) {
                        z = true;
                    }
                    this.mLaunched = z;
                    this.mLaunches = in.readInt();
                }

                public void writeToParcelLocked(Parcel out) {
                    out.writeLong(this.mStartTime);
                    out.writeLong(this.mRunningSince);
                    out.writeInt(this.mRunning ? 1 : 0);
                    out.writeInt(this.mStarts);
                    out.writeLong(this.mLaunchedTime);
                    out.writeLong(this.mLaunchedSince);
                    out.writeInt(this.mLaunched ? 1 : 0);
                    out.writeInt(this.mLaunches);
                }

                public long getLaunchTimeToNowLocked(long batteryUptime) {
                    if (!this.mLaunched) {
                        return this.mLaunchedTime;
                    }
                    return (this.mLaunchedTime + batteryUptime) - this.mLaunchedSince;
                }

                public long getStartTimeToNowLocked(long batteryUptime) {
                    if (!this.mRunning) {
                        return this.mStartTime;
                    }
                    return (this.mStartTime + batteryUptime) - this.mRunningSince;
                }

                @UnsupportedAppUsage
                public void startLaunchedLocked() {
                    if (!this.mLaunched) {
                        this.mLaunches++;
                        this.mLaunchedSince = this.mBsi.getBatteryUptimeLocked();
                        this.mLaunched = true;
                    }
                }

                @UnsupportedAppUsage
                public void stopLaunchedLocked() {
                    if (this.mLaunched) {
                        long time = this.mBsi.getBatteryUptimeLocked() - this.mLaunchedSince;
                        if (time > 0) {
                            this.mLaunchedTime += time;
                        } else {
                            this.mLaunches--;
                        }
                        this.mLaunched = false;
                    }
                }

                @UnsupportedAppUsage
                public void startRunningLocked() {
                    if (!this.mRunning) {
                        this.mStarts++;
                        this.mRunningSince = this.mBsi.getBatteryUptimeLocked();
                        this.mRunning = true;
                    }
                }

                @UnsupportedAppUsage
                public void stopRunningLocked() {
                    if (this.mRunning) {
                        long time = this.mBsi.getBatteryUptimeLocked() - this.mRunningSince;
                        if (time > 0) {
                            this.mStartTime += time;
                        } else {
                            this.mStarts--;
                        }
                        this.mRunning = false;
                    }
                }

                @UnsupportedAppUsage
                public BatteryStatsImpl getBatteryStats() {
                    return this.mBsi;
                }

                public int getLaunches(int which) {
                    return this.mLaunches;
                }

                public long getStartTime(long now, int which) {
                    return getStartTimeToNowLocked(now);
                }

                public int getStarts(int which) {
                    return this.mStarts;
                }
            }

            /* access modifiers changed from: package-private */
            public final Serv newServiceStatsLocked() {
                return new Serv(this.mBsi);
            }
        }

        public Proc getProcessStatsLocked(String name) {
            Proc ps = this.mProcessStats.get(name);
            if (ps != null) {
                return ps;
            }
            Proc ps2 = new Proc(this.mBsi, name);
            this.mProcessStats.put(name, ps2);
            return ps2;
        }

        @GuardedBy({"mBsi"})
        public void updateUidProcessStateLocked(int procState) {
            boolean userAwareService = ActivityManager.isForegroundService(procState);
            int uidRunningState = BatteryStats.mapToInternalProcessState(procState);
            if (this.mProcessState != uidRunningState || userAwareService != this.mInForegroundService) {
                long elapsedRealtimeMs = this.mBsi.mClocks.elapsedRealtime();
                if (this.mProcessState != uidRunningState) {
                    long uptimeMs = this.mBsi.mClocks.uptimeMillis();
                    if (this.mProcessState != 21) {
                        this.mProcessStateTimer[this.mProcessState].stopRunningLocked(elapsedRealtimeMs);
                        if (this.mBsi.trackPerProcStateCpuTimes()) {
                            if (this.mBsi.mPendingUids.size() == 0) {
                                this.mBsi.mExternalSync.scheduleReadProcStateCpuTimes(this.mBsi.mOnBatteryTimeBase.isRunning(), this.mBsi.mOnBatteryScreenOffTimeBase.isRunning(), this.mBsi.mConstants.PROC_STATE_CPU_TIMES_READ_DELAY_MS);
                                BatteryStatsImpl.access$1708(this.mBsi);
                            } else {
                                BatteryStatsImpl.access$1808(this.mBsi);
                            }
                            if (this.mBsi.mPendingUids.indexOfKey(this.mUid) < 0 || ArrayUtils.contains(CRITICAL_PROC_STATES, this.mProcessState)) {
                                this.mBsi.mPendingUids.put(this.mUid, this.mProcessState);
                            }
                        } else {
                            this.mBsi.mPendingUids.clear();
                        }
                    }
                    this.mProcessState = uidRunningState;
                    if (uidRunningState != 21) {
                        if (this.mProcessStateTimer[uidRunningState] == null) {
                            makeProcessState(uidRunningState, (Parcel) null);
                        }
                        this.mProcessStateTimer[uidRunningState].startRunningLocked(elapsedRealtimeMs);
                    }
                    updateOnBatteryBgTimeBase(uptimeMs * 1000, elapsedRealtimeMs * 1000);
                    updateOnBatteryScreenOffBgTimeBase(uptimeMs * 1000, 1000 * elapsedRealtimeMs);
                }
                if (userAwareService != this.mInForegroundService) {
                    if (userAwareService) {
                        noteForegroundServiceResumedLocked(elapsedRealtimeMs);
                    } else {
                        noteForegroundServicePausedLocked(elapsedRealtimeMs);
                    }
                    this.mInForegroundService = userAwareService;
                }
            }
        }

        public boolean isInBackground() {
            return this.mProcessState >= 3;
        }

        public boolean updateOnBatteryBgTimeBase(long uptimeUs, long realtimeUs) {
            return this.mOnBatteryBackgroundTimeBase.setRunning(this.mBsi.mOnBatteryTimeBase.isRunning() && isInBackground(), uptimeUs, realtimeUs);
        }

        public boolean updateOnBatteryScreenOffBgTimeBase(long uptimeUs, long realtimeUs) {
            return this.mOnBatteryScreenOffBackgroundTimeBase.setRunning(this.mBsi.mOnBatteryScreenOffTimeBase.isRunning() && isInBackground(), uptimeUs, realtimeUs);
        }

        public SparseArray<? extends BatteryStats.Uid.Pid> getPidStats() {
            return this.mPids;
        }

        public BatteryStats.Uid.Pid getPidStatsLocked(int pid) {
            BatteryStats.Uid.Pid p = this.mPids.get(pid);
            if (p != null) {
                return p;
            }
            BatteryStats.Uid.Pid p2 = new BatteryStats.Uid.Pid();
            this.mPids.put(pid, p2);
            return p2;
        }

        public Pkg getPackageStatsLocked(String name) {
            Pkg ps = this.mPackageStats.get(name);
            if (ps != null) {
                return ps;
            }
            Pkg ps2 = new Pkg(this.mBsi);
            this.mPackageStats.put(name, ps2);
            return ps2;
        }

        public Pkg.Serv getServiceStatsLocked(String pkg, String serv) {
            Pkg ps = getPackageStatsLocked(pkg);
            Pkg.Serv ss = ps.mServiceStats.get(serv);
            if (ss != null) {
                return ss;
            }
            Pkg.Serv ss2 = ps.newServiceStatsLocked();
            ps.mServiceStats.put(serv, ss2);
            return ss2;
        }

        public void readSyncSummaryFromParcelLocked(String name, Parcel in) {
            DualTimer timer = this.mSyncStats.instantiateObject();
            timer.readSummaryFromParcelLocked(in);
            this.mSyncStats.add(name, timer);
        }

        public void readJobSummaryFromParcelLocked(String name, Parcel in) {
            DualTimer timer = this.mJobStats.instantiateObject();
            timer.readSummaryFromParcelLocked(in);
            this.mJobStats.add(name, timer);
        }

        public void readWakeSummaryFromParcelLocked(String wlName, Parcel in) {
            Wakelock wl = new Wakelock(this.mBsi, this);
            this.mWakelockStats.add(wlName, wl);
            if (in.readInt() != 0) {
                getWakelockTimerLocked(wl, 1).readSummaryFromParcelLocked(in);
            }
            if (in.readInt() != 0) {
                getWakelockTimerLocked(wl, 0).readSummaryFromParcelLocked(in);
            }
            if (in.readInt() != 0) {
                getWakelockTimerLocked(wl, 2).readSummaryFromParcelLocked(in);
            }
            if (in.readInt() != 0) {
                getWakelockTimerLocked(wl, 18).readSummaryFromParcelLocked(in);
            }
        }

        public DualTimer getSensorTimerLocked(int sensor, boolean create) {
            Sensor se = this.mSensorStats.get(sensor);
            if (se == null) {
                if (!create) {
                    return null;
                }
                se = new Sensor(this.mBsi, this, sensor);
                this.mSensorStats.put(sensor, se);
            }
            DualTimer t = se.mTimer;
            if (t != null) {
                return t;
            }
            ArrayList<StopwatchTimer> timers = this.mBsi.mSensorTimers.get(sensor);
            if (timers == null) {
                timers = new ArrayList<>();
                this.mBsi.mSensorTimers.put(sensor, timers);
            }
            DualTimer t2 = new DualTimer(this.mBsi.mClocks, this, 3, timers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            se.mTimer = t2;
            return t2;
        }

        public void noteStartSyncLocked(String name, long elapsedRealtimeMs) {
            DualTimer t = this.mSyncStats.startObject(name);
            if (t != null) {
                t.startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteStopSyncLocked(String name, long elapsedRealtimeMs) {
            DualTimer t = this.mSyncStats.stopObject(name);
            if (t != null) {
                t.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteStartJobLocked(String name, long elapsedRealtimeMs) {
            DualTimer t = this.mJobStats.startObject(name);
            if (t != null) {
                t.startRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteStopJobLocked(String name, long elapsedRealtimeMs, int stopReason) {
            DualTimer t = this.mJobStats.stopObject(name);
            if (t != null) {
                t.stopRunningLocked(elapsedRealtimeMs);
            }
            if (this.mBsi.mOnBatteryTimeBase.isRunning()) {
                SparseIntArray types = this.mJobCompletions.get(name);
                if (types == null) {
                    types = new SparseIntArray();
                    this.mJobCompletions.put(name, types);
                }
                types.put(stopReason, types.get(stopReason, 0) + 1);
            }
        }

        public StopwatchTimer getWakelockTimerLocked(Wakelock wl, int type) {
            if (wl == null) {
                return null;
            }
            if (type != 18) {
                switch (type) {
                    case 0:
                        StopwatchTimer t = wl.mTimerPartial;
                        if (t != null) {
                            return t;
                        }
                        DualTimer t2 = new DualTimer(this.mBsi.mClocks, this, 0, this.mBsi.mPartialTimers, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
                        wl.mTimerPartial = t2;
                        return t2;
                    case 1:
                        StopwatchTimer t3 = wl.mTimerFull;
                        if (t3 != null) {
                            return t3;
                        }
                        StopwatchTimer t4 = new StopwatchTimer(this.mBsi.mClocks, this, 1, this.mBsi.mFullTimers, this.mBsi.mOnBatteryTimeBase);
                        wl.mTimerFull = t4;
                        return t4;
                    case 2:
                        StopwatchTimer t5 = wl.mTimerWindow;
                        if (t5 != null) {
                            return t5;
                        }
                        StopwatchTimer t6 = new StopwatchTimer(this.mBsi.mClocks, this, 2, this.mBsi.mWindowTimers, this.mBsi.mOnBatteryTimeBase);
                        wl.mTimerWindow = t6;
                        return t6;
                    default:
                        throw new IllegalArgumentException("type=" + type);
                }
            } else {
                StopwatchTimer t7 = wl.mTimerDraw;
                if (t7 != null) {
                    return t7;
                }
                StopwatchTimer t8 = new StopwatchTimer(this.mBsi.mClocks, this, 18, this.mBsi.mDrawTimers, this.mBsi.mOnBatteryTimeBase);
                wl.mTimerDraw = t8;
                return t8;
            }
        }

        public void noteStartWakeLocked(int pid, String name, int type, long elapsedRealtimeMs) {
            Wakelock wl = this.mWakelockStats.startObject(name);
            if (wl != null) {
                getWakelockTimerLocked(wl, type).startRunningLocked(elapsedRealtimeMs);
            }
            if (type == 0) {
                createAggregatedPartialWakelockTimerLocked().startRunningLocked(elapsedRealtimeMs);
                if (pid >= 0) {
                    BatteryStats.Uid.Pid p = getPidStatsLocked(pid);
                    int i = p.mWakeNesting;
                    p.mWakeNesting = i + 1;
                    if (i == 0) {
                        p.mWakeStartMs = elapsedRealtimeMs;
                    }
                }
            }
        }

        public void noteStopWakeLocked(int pid, String name, int type, long elapsedRealtimeMs) {
            BatteryStats.Uid.Pid p;
            Wakelock wl = this.mWakelockStats.stopObject(name);
            if (wl != null) {
                getWakelockTimerLocked(wl, type).stopRunningLocked(elapsedRealtimeMs);
            }
            if (type == 0) {
                if (this.mAggregatedPartialWakelockTimer != null) {
                    this.mAggregatedPartialWakelockTimer.stopRunningLocked(elapsedRealtimeMs);
                }
                if (pid >= 0 && (p = this.mPids.get(pid)) != null && p.mWakeNesting > 0) {
                    int i = p.mWakeNesting;
                    p.mWakeNesting = i - 1;
                    if (i == 1) {
                        p.mWakeSumMs += elapsedRealtimeMs - p.mWakeStartMs;
                        p.mWakeStartMs = 0;
                    }
                }
            }
        }

        public void reportExcessiveCpuLocked(String proc, long overTime, long usedTime) {
            Proc p = getProcessStatsLocked(proc);
            if (p != null) {
                p.addExcessiveCpu(overTime, usedTime);
            }
        }

        public void noteStartSensor(int sensor, long elapsedRealtimeMs) {
            getSensorTimerLocked(sensor, true).startRunningLocked(elapsedRealtimeMs);
        }

        public void noteStopSensor(int sensor, long elapsedRealtimeMs) {
            DualTimer t = getSensorTimerLocked(sensor, false);
            if (t != null) {
                t.stopRunningLocked(elapsedRealtimeMs);
            }
        }

        public void noteStartGps(long elapsedRealtimeMs) {
            noteStartSensor(-10000, elapsedRealtimeMs);
        }

        public void noteStopGps(long elapsedRealtimeMs) {
            noteStopSensor(-10000, elapsedRealtimeMs);
        }

        public BatteryStatsImpl getBatteryStats() {
            return this.mBsi;
        }
    }

    public long[] getCpuFreqs() {
        return this.mCpuFreqs;
    }

    public BatteryStatsImpl(File systemDir, Handler handler, PlatformIdleStateCallback cb, RailEnergyDataCallback railStatsCb, UserInfoProvider userInfoProvider) {
        this(new SystemClocks(), systemDir, handler, cb, railStatsCb, userInfoProvider);
    }

    private BatteryStatsImpl(Clocks clocks, File systemDir, Handler handler, PlatformIdleStateCallback cb, RailEnergyDataCallback railStatsCb, UserInfoProvider userInfoProvider) {
        File file = systemDir;
        this.mKernelWakelockReader = new KernelWakelockReader();
        this.mTmpWakelockStats = new KernelWakelockStats();
        this.mCpuUidUserSysTimeReader = new KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(true);
        this.mCpuUidFreqTimeReader = new KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(true);
        this.mCpuUidActiveTimeReader = new KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(true);
        this.mCpuUidClusterTimeReader = new KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(true);
        this.mKernelMemoryBandwidthStats = new KernelMemoryBandwidthStats();
        this.mKernelMemoryStats = new LongSparseArray<>();
        this.mPerProcStateCpuTimesAvailable = true;
        this.mPendingUids = new SparseIntArray();
        this.mCpuTimeReadsTrackingStartTime = SystemClock.uptimeMillis();
        this.mTmpRpmStats = new RpmStats();
        this.mLastRpmStatsUpdateTimeMs = -1000;
        this.mTmpRailStats = new RailStats();
        this.mPendingRemovedUids = new LinkedList();
        this.mDeferSetCharging = new Runnable() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    com.android.internal.os.BatteryStatsImpl r0 = com.android.internal.os.BatteryStatsImpl.this
                    monitor-enter(r0)
                    com.android.internal.os.BatteryStatsImpl r1 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    boolean r1 = r1.mOnBattery     // Catch:{ all -> 0x002b }
                    if (r1 == 0) goto L_0x000b
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    return
                L_0x000b:
                    com.android.internal.os.BatteryStatsImpl r1 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    r2 = 1
                    boolean r1 = r1.setChargingLocked(r2)     // Catch:{ all -> 0x002b }
                    if (r1 == 0) goto L_0x0029
                    com.android.internal.os.BatteryStatsImpl r2 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl$Clocks r2 = r2.mClocks     // Catch:{ all -> 0x002b }
                    long r2 = r2.uptimeMillis()     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl r4 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl$Clocks r4 = r4.mClocks     // Catch:{ all -> 0x002b }
                    long r4 = r4.elapsedRealtime()     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl r6 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    r6.addHistoryRecordLocked(r4, r2)     // Catch:{ all -> 0x002b }
                L_0x0029:
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    return
                L_0x002b:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.AnonymousClass1.run():void");
            }
        };
        this.mExternalSync = null;
        this.mUserInfoProvider = null;
        this.mIsolatedUids = new SparseIntArray();
        this.mUidStats = new SparseArray<>();
        this.mPartialTimers = new ArrayList<>();
        this.mFullTimers = new ArrayList<>();
        this.mWindowTimers = new ArrayList<>();
        this.mDrawTimers = new ArrayList<>();
        this.mSensorTimers = new SparseArray<>();
        this.mWifiRunningTimers = new ArrayList<>();
        this.mFullWifiLockTimers = new ArrayList<>();
        this.mWifiMulticastTimers = new ArrayList<>();
        this.mWifiScanTimers = new ArrayList<>();
        this.mWifiBatchedScanTimers = new SparseArray<>();
        this.mAudioTurnedOnTimers = new ArrayList<>();
        this.mVideoTurnedOnTimers = new ArrayList<>();
        this.mFlashlightTurnedOnTimers = new ArrayList<>();
        this.mCameraTurnedOnTimers = new ArrayList<>();
        this.mBluetoothScanOnTimers = new ArrayList<>();
        this.mLastPartialTimers = new ArrayList<>();
        this.mOnBatteryTimeBase = new TimeBase(true);
        this.mOnBatteryScreenOffTimeBase = new TimeBase(true);
        this.mActiveEvents = new BatteryStats.HistoryEventTracker();
        this.mHaveBatteryLevel = false;
        this.mRecordingHistory = false;
        this.mHistoryBuffer = Parcel.obtain();
        this.mHistoryLastWritten = new BatteryStats.HistoryItem();
        this.mHistoryLastLastWritten = new BatteryStats.HistoryItem();
        this.mHistoryReadTmp = new BatteryStats.HistoryItem();
        this.mHistoryAddTmp = new BatteryStats.HistoryItem();
        this.mHistoryTagPool = new HashMap<>();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mActiveHistoryStates = -1;
        this.mActiveHistoryStates2 = -1;
        this.mLastHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryUptime = 0;
        this.mHistoryCur = new BatteryStats.HistoryItem();
        this.mLastHistoryStepDetails = null;
        this.mLastHistoryStepLevel = 0;
        this.mCurHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mReadHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mTmpHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mScreenState = 0;
        this.mScreenBrightnessBin = -1;
        this.mScreenBrightnessTimer = new StopwatchTimer[5];
        this.mUsbDataState = 0;
        this.mGpsSignalQualityBin = -1;
        this.mGpsSignalQualityTimer = new StopwatchTimer[2];
        this.mPhoneSignalStrengthBin = -1;
        this.mPhoneSignalStrengthBinRaw = -1;
        this.mPhoneSignalStrengthsTimer = new StopwatchTimer[5];
        this.mPhoneDataConnectionType = -1;
        this.mPhoneDataConnectionsTimer = new StopwatchTimer[22];
        this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
        this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
        this.mHasWifiReporting = false;
        this.mHasBluetoothReporting = false;
        this.mHasModemReporting = false;
        this.mWifiState = -1;
        this.mWifiStateTimer = new StopwatchTimer[8];
        this.mWifiSupplState = -1;
        this.mWifiSupplStateTimer = new StopwatchTimer[13];
        this.mWifiSignalStrengthBin = -1;
        this.mWifiSignalStrengthsTimer = new StopwatchTimer[5];
        this.mIsCellularTxPowerHigh = false;
        this.mMobileRadioPowerState = 1;
        this.mWifiRadioPowerState = 1;
        this.mCharging = true;
        this.mInitStepMode = 0;
        this.mCurStepMode = 0;
        this.mModStepMode = 0;
        this.mDischargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyDischargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mChargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyChargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mDailyStartTime = 0;
        this.mNextMinDailyDeadline = 0;
        this.mNextMaxDailyDeadline = 0;
        this.mDailyItems = new ArrayList<>();
        this.mLastWriteTime = 0;
        this.mPhoneServiceState = -1;
        this.mPhoneServiceStateRaw = -1;
        this.mPhoneSimStateRaw = -1;
        this.mEstimatedBatteryCapacity = -1;
        this.mMinLearnedBatteryCapacity = -1;
        this.mMaxLearnedBatteryCapacity = -1;
        this.mRpmStats = new HashMap<>();
        this.mScreenOffRpmStats = new HashMap<>();
        this.mKernelWakelockStats = new HashMap<>();
        this.mLastWakeupReason = null;
        this.mLastWakeupUptimeMs = 0;
        this.mWakeupReasonStats = new HashMap<>();
        this.mChangedStates = 0;
        this.mChangedStates2 = 0;
        this.mInitialAcquireWakeUid = -1;
        this.mWifiFullLockNesting = 0;
        this.mWifiScanNesting = 0;
        this.mWifiMulticastNesting = 0;
        this.mNetworkStatsPool = new Pools.SynchronizedPool(6);
        this.mWifiNetworkLock = new Object();
        this.mWifiIfaces = EmptyArray.STRING;
        this.mLastWifiNetworkStats = new NetworkStats(0, -1);
        this.mModemNetworkLock = new Object();
        this.mModemIfaces = EmptyArray.STRING;
        this.mLastModemNetworkStats = new NetworkStats(0, -1);
        this.mLastModemActivityInfo = new ModemActivityInfo(0, 0, 0, new int[0], 0, 0);
        this.mLastBluetoothActivityInfo = new BluetoothActivityInfoCache();
        this.mWriteLock = new ReentrantLock();
        init(clocks);
        if (file == null) {
            this.mStatsFile = null;
            this.mBatteryStatsHistory = new BatteryStatsHistory(this, this.mHistoryBuffer);
        } else {
            this.mStatsFile = new AtomicFile(new File(file, "batterystats.bin"));
            this.mBatteryStatsHistory = new BatteryStatsHistory(this, file, this.mHistoryBuffer);
        }
        this.mCheckinFile = new AtomicFile(new File(file, "batterystats-checkin.bin"));
        this.mDailyFile = new AtomicFile(new File(file, "batterystats-daily.xml"));
        this.mHandler = new MyHandler(handler.getLooper());
        this.mConstants = new Constants(this.mHandler);
        this.mStartCount++;
        this.mScreenOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -1, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mScreenDozeTimer = new StopwatchTimer(this.mClocks, (Uid) null, -1, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        for (int i = 0; i < 5; i++) {
            this.mScreenBrightnessTimer[i] = new StopwatchTimer(this.mClocks, (Uid) null, -100 - i, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        this.mInteractiveTimer = new StopwatchTimer(this.mClocks, (Uid) null, -10, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mPowerSaveModeEnabledTimer = new StopwatchTimer(this.mClocks, (Uid) null, -2, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeLightTimer = new StopwatchTimer(this.mClocks, (Uid) null, -11, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeFullTimer = new StopwatchTimer(this.mClocks, (Uid) null, -14, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mDeviceLightIdlingTimer = new StopwatchTimer(this.mClocks, (Uid) null, -15, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mDeviceIdlingTimer = new StopwatchTimer(this.mClocks, (Uid) null, -12, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mPhoneOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -3, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        for (int i2 = 0; i2 < 5; i2++) {
            this.mPhoneSignalStrengthsTimer[i2] = new StopwatchTimer(this.mClocks, (Uid) null, -200 - i2, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        this.mPhoneSignalScanningTimer = new StopwatchTimer(this.mClocks, (Uid) null, -199, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        for (int i3 = 0; i3 < 22; i3++) {
            this.mPhoneDataConnectionsTimer[i3] = new StopwatchTimer(this.mClocks, (Uid) null, -300 - i3, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        for (int i4 = 0; i4 < 10; i4++) {
            this.mNetworkByteActivityCounters[i4] = new LongSamplingCounter(this.mOnBatteryTimeBase);
            this.mNetworkPacketActivityCounters[i4] = new LongSamplingCounter(this.mOnBatteryTimeBase);
        }
        this.mWifiActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1);
        this.mBluetoothActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1);
        this.mModemActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 5);
        this.mMobileRadioActiveTimer = new StopwatchTimer(this.mClocks, (Uid) null, -400, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mMobileRadioActivePerAppTimer = new StopwatchTimer(this.mClocks, (Uid) null, -401, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mMobileRadioActiveAdjustedTime = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownTime = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownCount = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mWifiMulticastWakelockTimer = new StopwatchTimer(this.mClocks, (Uid) null, 23, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mWifiOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -4, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mGlobalWifiRunningTimer = new StopwatchTimer(this.mClocks, (Uid) null, -5, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        for (int i5 = 0; i5 < 8; i5++) {
            this.mWifiStateTimer[i5] = new StopwatchTimer(this.mClocks, (Uid) null, -600 - i5, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        for (int i6 = 0; i6 < 13; i6++) {
            this.mWifiSupplStateTimer[i6] = new StopwatchTimer(this.mClocks, (Uid) null, -700 - i6, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        for (int i7 = 0; i7 < 5; i7++) {
            this.mWifiSignalStrengthsTimer[i7] = new StopwatchTimer(this.mClocks, (Uid) null, -800 - i7, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        this.mWifiActiveTimer = new StopwatchTimer(this.mClocks, (Uid) null, -900, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        for (int i8 = 0; i8 < 2; i8++) {
            this.mGpsSignalQualityTimer[i8] = new StopwatchTimer(this.mClocks, (Uid) null, -1000 - i8, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        }
        this.mAudioOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -7, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mVideoOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -8, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mFlashlightOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -9, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mCameraOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -13, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mBluetoothScanTimer = new StopwatchTimer(this.mClocks, (Uid) null, -14, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
        this.mDischargeScreenOffCounter = new LongSamplingCounter(this.mOnBatteryScreenOffTimeBase);
        this.mDischargeScreenDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeLightDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeDeepDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mOnBatteryInternal = false;
        this.mOnBattery = false;
        initTimes(this.mClocks.uptimeMillis() * 1000, this.mClocks.elapsedRealtime() * 1000);
        String str = Build.ID;
        this.mEndPlatformVersion = str;
        this.mStartPlatformVersion = str;
        this.mDischargeStartLevel = 0;
        this.mDischargeUnplugLevel = 0;
        this.mDischargePlugLevel = -1;
        this.mDischargeCurrentLevel = 0;
        this.mCurrentBatteryLevel = 0;
        initDischarge();
        clearHistoryLocked();
        updateDailyDeadlineLocked();
        this.mPlatformIdleStateCallback = cb;
        this.mRailEnergyDataCallback = railStatsCb;
        this.mUserInfoProvider = userInfoProvider;
    }

    @UnsupportedAppUsage
    public BatteryStatsImpl(Parcel p) {
        this(new SystemClocks(), p);
    }

    public BatteryStatsImpl(Clocks clocks, Parcel p) {
        this.mKernelWakelockReader = new KernelWakelockReader();
        this.mTmpWakelockStats = new KernelWakelockStats();
        this.mCpuUidUserSysTimeReader = new KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(true);
        this.mCpuUidFreqTimeReader = new KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(true);
        this.mCpuUidActiveTimeReader = new KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(true);
        this.mCpuUidClusterTimeReader = new KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(true);
        this.mKernelMemoryBandwidthStats = new KernelMemoryBandwidthStats();
        this.mKernelMemoryStats = new LongSparseArray<>();
        this.mPerProcStateCpuTimesAvailable = true;
        this.mPendingUids = new SparseIntArray();
        this.mCpuTimeReadsTrackingStartTime = SystemClock.uptimeMillis();
        this.mTmpRpmStats = new RpmStats();
        this.mLastRpmStatsUpdateTimeMs = -1000;
        this.mTmpRailStats = new RailStats();
        this.mPendingRemovedUids = new LinkedList();
        this.mDeferSetCharging = new Runnable() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    com.android.internal.os.BatteryStatsImpl r0 = com.android.internal.os.BatteryStatsImpl.this
                    monitor-enter(r0)
                    com.android.internal.os.BatteryStatsImpl r1 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    boolean r1 = r1.mOnBattery     // Catch:{ all -> 0x002b }
                    if (r1 == 0) goto L_0x000b
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    return
                L_0x000b:
                    com.android.internal.os.BatteryStatsImpl r1 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    r2 = 1
                    boolean r1 = r1.setChargingLocked(r2)     // Catch:{ all -> 0x002b }
                    if (r1 == 0) goto L_0x0029
                    com.android.internal.os.BatteryStatsImpl r2 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl$Clocks r2 = r2.mClocks     // Catch:{ all -> 0x002b }
                    long r2 = r2.uptimeMillis()     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl r4 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl$Clocks r4 = r4.mClocks     // Catch:{ all -> 0x002b }
                    long r4 = r4.elapsedRealtime()     // Catch:{ all -> 0x002b }
                    com.android.internal.os.BatteryStatsImpl r6 = com.android.internal.os.BatteryStatsImpl.this     // Catch:{ all -> 0x002b }
                    r6.addHistoryRecordLocked(r4, r2)     // Catch:{ all -> 0x002b }
                L_0x0029:
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    return
                L_0x002b:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x002b }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.AnonymousClass1.run():void");
            }
        };
        this.mExternalSync = null;
        this.mUserInfoProvider = null;
        this.mIsolatedUids = new SparseIntArray();
        this.mUidStats = new SparseArray<>();
        this.mPartialTimers = new ArrayList<>();
        this.mFullTimers = new ArrayList<>();
        this.mWindowTimers = new ArrayList<>();
        this.mDrawTimers = new ArrayList<>();
        this.mSensorTimers = new SparseArray<>();
        this.mWifiRunningTimers = new ArrayList<>();
        this.mFullWifiLockTimers = new ArrayList<>();
        this.mWifiMulticastTimers = new ArrayList<>();
        this.mWifiScanTimers = new ArrayList<>();
        this.mWifiBatchedScanTimers = new SparseArray<>();
        this.mAudioTurnedOnTimers = new ArrayList<>();
        this.mVideoTurnedOnTimers = new ArrayList<>();
        this.mFlashlightTurnedOnTimers = new ArrayList<>();
        this.mCameraTurnedOnTimers = new ArrayList<>();
        this.mBluetoothScanOnTimers = new ArrayList<>();
        this.mLastPartialTimers = new ArrayList<>();
        this.mOnBatteryTimeBase = new TimeBase(true);
        this.mOnBatteryScreenOffTimeBase = new TimeBase(true);
        this.mActiveEvents = new BatteryStats.HistoryEventTracker();
        this.mHaveBatteryLevel = false;
        this.mRecordingHistory = false;
        this.mHistoryBuffer = Parcel.obtain();
        this.mHistoryLastWritten = new BatteryStats.HistoryItem();
        this.mHistoryLastLastWritten = new BatteryStats.HistoryItem();
        this.mHistoryReadTmp = new BatteryStats.HistoryItem();
        this.mHistoryAddTmp = new BatteryStats.HistoryItem();
        this.mHistoryTagPool = new HashMap<>();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mActiveHistoryStates = -1;
        this.mActiveHistoryStates2 = -1;
        this.mLastHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryElapsedRealtime = 0;
        this.mTrackRunningHistoryUptime = 0;
        this.mHistoryCur = new BatteryStats.HistoryItem();
        this.mLastHistoryStepDetails = null;
        this.mLastHistoryStepLevel = 0;
        this.mCurHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mReadHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mTmpHistoryStepDetails = new BatteryStats.HistoryStepDetails();
        this.mScreenState = 0;
        this.mScreenBrightnessBin = -1;
        this.mScreenBrightnessTimer = new StopwatchTimer[5];
        this.mUsbDataState = 0;
        this.mGpsSignalQualityBin = -1;
        this.mGpsSignalQualityTimer = new StopwatchTimer[2];
        this.mPhoneSignalStrengthBin = -1;
        this.mPhoneSignalStrengthBinRaw = -1;
        this.mPhoneSignalStrengthsTimer = new StopwatchTimer[5];
        this.mPhoneDataConnectionType = -1;
        this.mPhoneDataConnectionsTimer = new StopwatchTimer[22];
        this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
        this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
        this.mHasWifiReporting = false;
        this.mHasBluetoothReporting = false;
        this.mHasModemReporting = false;
        this.mWifiState = -1;
        this.mWifiStateTimer = new StopwatchTimer[8];
        this.mWifiSupplState = -1;
        this.mWifiSupplStateTimer = new StopwatchTimer[13];
        this.mWifiSignalStrengthBin = -1;
        this.mWifiSignalStrengthsTimer = new StopwatchTimer[5];
        this.mIsCellularTxPowerHigh = false;
        this.mMobileRadioPowerState = 1;
        this.mWifiRadioPowerState = 1;
        this.mCharging = true;
        this.mInitStepMode = 0;
        this.mCurStepMode = 0;
        this.mModStepMode = 0;
        this.mDischargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyDischargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mChargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyChargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mDailyStartTime = 0;
        this.mNextMinDailyDeadline = 0;
        this.mNextMaxDailyDeadline = 0;
        this.mDailyItems = new ArrayList<>();
        this.mLastWriteTime = 0;
        this.mPhoneServiceState = -1;
        this.mPhoneServiceStateRaw = -1;
        this.mPhoneSimStateRaw = -1;
        this.mEstimatedBatteryCapacity = -1;
        this.mMinLearnedBatteryCapacity = -1;
        this.mMaxLearnedBatteryCapacity = -1;
        this.mRpmStats = new HashMap<>();
        this.mScreenOffRpmStats = new HashMap<>();
        this.mKernelWakelockStats = new HashMap<>();
        this.mLastWakeupReason = null;
        this.mLastWakeupUptimeMs = 0;
        this.mWakeupReasonStats = new HashMap<>();
        this.mChangedStates = 0;
        this.mChangedStates2 = 0;
        this.mInitialAcquireWakeUid = -1;
        this.mWifiFullLockNesting = 0;
        this.mWifiScanNesting = 0;
        this.mWifiMulticastNesting = 0;
        this.mNetworkStatsPool = new Pools.SynchronizedPool(6);
        this.mWifiNetworkLock = new Object();
        this.mWifiIfaces = EmptyArray.STRING;
        this.mLastWifiNetworkStats = new NetworkStats(0, -1);
        this.mModemNetworkLock = new Object();
        this.mModemIfaces = EmptyArray.STRING;
        this.mLastModemNetworkStats = new NetworkStats(0, -1);
        this.mLastModemActivityInfo = new ModemActivityInfo(0, 0, 0, new int[0], 0, 0);
        this.mLastBluetoothActivityInfo = new BluetoothActivityInfoCache();
        this.mWriteLock = new ReentrantLock();
        init(clocks);
        this.mStatsFile = null;
        this.mCheckinFile = null;
        this.mDailyFile = null;
        this.mHandler = null;
        this.mExternalSync = null;
        this.mConstants = new Constants(this.mHandler);
        clearHistoryLocked();
        this.mBatteryStatsHistory = new BatteryStatsHistory(this, this.mHistoryBuffer);
        readFromParcel(p);
        this.mPlatformIdleStateCallback = null;
        this.mRailEnergyDataCallback = null;
    }

    public void setPowerProfileLocked(PowerProfile profile) {
        this.mPowerProfile = profile;
        int numClusters = this.mPowerProfile.getNumCpuClusters();
        this.mKernelCpuSpeedReaders = new KernelCpuSpeedReader[numClusters];
        int firstCpuOfCluster = 0;
        for (int i = 0; i < numClusters; i++) {
            this.mKernelCpuSpeedReaders[i] = new KernelCpuSpeedReader(firstCpuOfCluster, this.mPowerProfile.getNumSpeedStepsInCpuCluster(i));
            firstCpuOfCluster += this.mPowerProfile.getNumCoresInCpuCluster(i);
        }
        if (this.mEstimatedBatteryCapacity == -1) {
            this.mEstimatedBatteryCapacity = (int) this.mPowerProfile.getBatteryCapacity();
        }
    }

    public void setCallback(BatteryCallback cb) {
        this.mCallback = cb;
    }

    public void setRadioScanningTimeoutLocked(long timeout) {
        if (this.mPhoneSignalScanningTimer != null) {
            this.mPhoneSignalScanningTimer.setTimeout(timeout);
        }
    }

    public void setExternalStatsSyncLocked(ExternalStatsSync sync) {
        this.mExternalSync = sync;
    }

    public void updateDailyDeadlineLocked() {
        long currentTime = System.currentTimeMillis();
        this.mDailyStartTime = currentTime;
        Calendar calDeadline = Calendar.getInstance();
        calDeadline.setTimeInMillis(currentTime);
        calDeadline.set(6, calDeadline.get(6) + 1);
        calDeadline.set(14, 0);
        calDeadline.set(13, 0);
        calDeadline.set(12, 0);
        calDeadline.set(11, 1);
        this.mNextMinDailyDeadline = calDeadline.getTimeInMillis();
        calDeadline.set(11, 3);
        this.mNextMaxDailyDeadline = calDeadline.getTimeInMillis();
    }

    public void recordDailyStatsIfNeededLocked(boolean settled) {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= this.mNextMaxDailyDeadline) {
            recordDailyStatsLocked();
        } else if (settled && currentTime >= this.mNextMinDailyDeadline) {
            recordDailyStatsLocked();
        } else if (currentTime < this.mDailyStartTime - 86400000) {
            recordDailyStatsLocked();
        }
    }

    public void recordDailyStatsLocked() {
        BatteryStats.DailyItem item = new BatteryStats.DailyItem();
        item.mStartTime = this.mDailyStartTime;
        item.mEndTime = System.currentTimeMillis();
        boolean hasData = false;
        if (this.mDailyDischargeStepTracker.mNumStepDurations > 0) {
            hasData = true;
            item.mDischargeSteps = new BatteryStats.LevelStepTracker(this.mDailyDischargeStepTracker.mNumStepDurations, this.mDailyDischargeStepTracker.mStepDurations);
        }
        if (this.mDailyChargeStepTracker.mNumStepDurations > 0) {
            hasData = true;
            item.mChargeSteps = new BatteryStats.LevelStepTracker(this.mDailyChargeStepTracker.mNumStepDurations, this.mDailyChargeStepTracker.mStepDurations);
        }
        if (this.mDailyPackageChanges != null) {
            hasData = true;
            item.mPackageChanges = this.mDailyPackageChanges;
            this.mDailyPackageChanges = null;
        }
        this.mDailyDischargeStepTracker.init();
        this.mDailyChargeStepTracker.init();
        updateDailyDeadlineLocked();
        if (hasData) {
            long startTime = SystemClock.uptimeMillis();
            this.mDailyItems.add(item);
            while (this.mDailyItems.size() > 10) {
                this.mDailyItems.remove(0);
            }
            final ByteArrayOutputStream memStream = new ByteArrayOutputStream();
            try {
                XmlSerializer out = new FastXmlSerializer();
                out.setOutput(memStream, StandardCharsets.UTF_8.name());
                writeDailyItemsLocked(out);
                final long initialTime = SystemClock.uptimeMillis() - startTime;
                BackgroundThread.getHandler().post(new Runnable() {
                    public void run() {
                        synchronized (BatteryStatsImpl.this.mCheckinFile) {
                            long startTime2 = SystemClock.uptimeMillis();
                            FileOutputStream stream = null;
                            try {
                                stream = BatteryStatsImpl.this.mDailyFile.startWrite();
                                memStream.writeTo(stream);
                                stream.flush();
                                BatteryStatsImpl.this.mDailyFile.finishWrite(stream);
                                EventLogTags.writeCommitSysConfigFile("batterystats-daily", (initialTime + SystemClock.uptimeMillis()) - startTime2);
                            } catch (IOException e) {
                                Slog.w("BatteryStats", "Error writing battery daily items", e);
                                BatteryStatsImpl.this.mDailyFile.failWrite(stream);
                            }
                        }
                    }
                });
            } catch (IOException e) {
            }
        }
    }

    private void writeDailyItemsLocked(XmlSerializer out) throws IOException {
        StringBuilder sb = new StringBuilder(64);
        out.startDocument((String) null, true);
        out.startTag((String) null, "daily-items");
        for (int i = 0; i < this.mDailyItems.size(); i++) {
            BatteryStats.DailyItem dit = this.mDailyItems.get(i);
            out.startTag((String) null, ImsConfig.EXTRA_CHANGED_ITEM);
            out.attribute((String) null, Telephony.BaseMmsColumns.START, Long.toString(dit.mStartTime));
            out.attribute((String) null, "end", Long.toString(dit.mEndTime));
            writeDailyLevelSteps(out, "dis", dit.mDischargeSteps, sb);
            writeDailyLevelSteps(out, "chg", dit.mChargeSteps, sb);
            if (dit.mPackageChanges != null) {
                for (int j = 0; j < dit.mPackageChanges.size(); j++) {
                    BatteryStats.PackageChange pc = dit.mPackageChanges.get(j);
                    if (pc.mUpdate) {
                        out.startTag((String) null, "upd");
                        out.attribute((String) null, "pkg", pc.mPackageName);
                        out.attribute((String) null, "ver", Long.toString(pc.mVersionCode));
                        out.endTag((String) null, "upd");
                    } else {
                        out.startTag((String) null, "rem");
                        out.attribute((String) null, "pkg", pc.mPackageName);
                        out.endTag((String) null, "rem");
                    }
                }
            }
            out.endTag((String) null, ImsConfig.EXTRA_CHANGED_ITEM);
        }
        out.endTag((String) null, "daily-items");
        out.endDocument();
    }

    private void writeDailyLevelSteps(XmlSerializer out, String tag, BatteryStats.LevelStepTracker steps, StringBuilder tmpBuilder) throws IOException {
        if (steps != null) {
            out.startTag((String) null, tag);
            out.attribute((String) null, "n", Integer.toString(steps.mNumStepDurations));
            for (int i = 0; i < steps.mNumStepDurations; i++) {
                out.startTag((String) null, "s");
                tmpBuilder.setLength(0);
                steps.encodeEntryAt(i, tmpBuilder);
                out.attribute((String) null, Telephony.BaseMmsColumns.MMS_VERSION, tmpBuilder.toString());
                out.endTag((String) null, "s");
            }
            out.endTag((String) null, tag);
        }
    }

    public void readDailyStatsLocked() {
        Slog.d(TAG, "Reading daily items from " + this.mDailyFile.getBaseFile());
        this.mDailyItems.clear();
        try {
            FileInputStream stream = this.mDailyFile.openRead();
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, StandardCharsets.UTF_8.name());
                readDailyItemsLocked(parser);
                try {
                    stream.close();
                } catch (IOException e) {
                }
            } catch (XmlPullParserException e2) {
                stream.close();
            } catch (Throwable th) {
                try {
                    stream.close();
                } catch (IOException e3) {
                }
                throw th;
            }
        } catch (FileNotFoundException e4) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0056 A[Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }] */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x000e A[Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readDailyItemsLocked(org.xmlpull.v1.XmlPullParser r8) {
        /*
            r7 = this;
        L_0x0000:
            int r0 = r8.next()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            r1 = r0
            r2 = 1
            r3 = 2
            if (r0 == r3) goto L_0x000c
            if (r1 == r2) goto L_0x000c
            goto L_0x0000
        L_0x000c:
            if (r1 != r3) goto L_0x0056
            int r0 = r8.getDepth()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
        L_0x0012:
            int r3 = r8.next()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            r1 = r3
            if (r3 == r2) goto L_0x00ef
            r3 = 3
            if (r1 != r3) goto L_0x0022
            int r4 = r8.getDepth()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            if (r4 <= r0) goto L_0x00ef
        L_0x0022:
            if (r1 == r3) goto L_0x0012
            r3 = 4
            if (r1 != r3) goto L_0x0028
            goto L_0x0012
        L_0x0028:
            java.lang.String r3 = r8.getName()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            java.lang.String r4 = "item"
            boolean r4 = r3.equals(r4)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            if (r4 == 0) goto L_0x0038
            r7.readDailyItemTagLocked(r8)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            goto L_0x0055
        L_0x0038:
            java.lang.String r4 = "BatteryStatsImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            r5.<init>()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            java.lang.String r6 = "Unknown element under <daily-items>: "
            r5.append(r6)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            java.lang.String r6 = r8.getName()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            r5.append(r6)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            java.lang.String r5 = r5.toString()     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
        L_0x0055:
            goto L_0x0012
        L_0x0056:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            java.lang.String r2 = "no start tag found"
            r0.<init>(r2)     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
            throw r0     // Catch:{ IllegalStateException -> 0x00d8, NullPointerException -> 0x00c0, NumberFormatException -> 0x00a8, XmlPullParserException -> 0x0090, IOException -> 0x0078, IndexOutOfBoundsException -> 0x005f }
        L_0x005f:
            r0 = move-exception
            java.lang.String r1 = "BatteryStatsImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed parsing daily "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            goto L_0x00f0
        L_0x0078:
            r0 = move-exception
            java.lang.String r1 = "BatteryStatsImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed parsing daily "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            goto L_0x00ef
        L_0x0090:
            r0 = move-exception
            java.lang.String r1 = "BatteryStatsImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed parsing daily "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            goto L_0x00ef
        L_0x00a8:
            r0 = move-exception
            java.lang.String r1 = "BatteryStatsImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed parsing daily "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            goto L_0x00ef
        L_0x00c0:
            r0 = move-exception
            java.lang.String r1 = "BatteryStatsImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed parsing daily "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            goto L_0x00ef
        L_0x00d8:
            r0 = move-exception
            java.lang.String r1 = "BatteryStatsImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed parsing daily "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
        L_0x00ef:
        L_0x00f0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.readDailyItemsLocked(org.xmlpull.v1.XmlPullParser):void");
    }

    /* access modifiers changed from: package-private */
    public void readDailyItemTagLocked(XmlPullParser parser) throws NumberFormatException, XmlPullParserException, IOException {
        BatteryStats.DailyItem dit = new BatteryStats.DailyItem();
        String attr = parser.getAttributeValue((String) null, Telephony.BaseMmsColumns.START);
        if (attr != null) {
            dit.mStartTime = Long.parseLong(attr);
        }
        String attr2 = parser.getAttributeValue((String) null, "end");
        if (attr2 != null) {
            dit.mEndTime = Long.parseLong(attr2);
        }
        int outerDepth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                this.mDailyItems.add(dit);
            } else if (!(type == 3 || type == 4)) {
                String tagName = parser.getName();
                if (tagName.equals("dis")) {
                    readDailyItemTagDetailsLocked(parser, dit, false, "dis");
                } else if (tagName.equals("chg")) {
                    readDailyItemTagDetailsLocked(parser, dit, true, "chg");
                } else if (tagName.equals("upd")) {
                    if (dit.mPackageChanges == null) {
                        dit.mPackageChanges = new ArrayList<>();
                    }
                    BatteryStats.PackageChange pc = new BatteryStats.PackageChange();
                    pc.mUpdate = true;
                    pc.mPackageName = parser.getAttributeValue((String) null, "pkg");
                    String verStr = parser.getAttributeValue((String) null, "ver");
                    pc.mVersionCode = verStr != null ? Long.parseLong(verStr) : 0;
                    dit.mPackageChanges.add(pc);
                    XmlUtils.skipCurrentTag(parser);
                } else if (tagName.equals("rem")) {
                    if (dit.mPackageChanges == null) {
                        dit.mPackageChanges = new ArrayList<>();
                    }
                    BatteryStats.PackageChange pc2 = new BatteryStats.PackageChange();
                    pc2.mUpdate = false;
                    pc2.mPackageName = parser.getAttributeValue((String) null, "pkg");
                    dit.mPackageChanges.add(pc2);
                    XmlUtils.skipCurrentTag(parser);
                } else {
                    Slog.w(TAG, "Unknown element under <item>: " + parser.getName());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
        this.mDailyItems.add(dit);
    }

    /* access modifiers changed from: package-private */
    public void readDailyItemTagDetailsLocked(XmlPullParser parser, BatteryStats.DailyItem dit, boolean isCharge, String tag) throws NumberFormatException, XmlPullParserException, IOException {
        String valueAttr;
        String numAttr = parser.getAttributeValue((String) null, "n");
        if (numAttr == null) {
            Slog.w(TAG, "Missing 'n' attribute at " + parser.getPositionDescription());
            XmlUtils.skipCurrentTag(parser);
            return;
        }
        int num = Integer.parseInt(numAttr);
        BatteryStats.LevelStepTracker steps = new BatteryStats.LevelStepTracker(num);
        if (isCharge) {
            dit.mChargeSteps = steps;
        } else {
            dit.mDischargeSteps = steps;
        }
        int i = 0;
        int outerDepth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                steps.mNumStepDurations = i;
            } else if (!(type == 3 || type == 4)) {
                if (!"s".equals(parser.getName())) {
                    Slog.w(TAG, "Unknown element under <" + tag + ">: " + parser.getName());
                    XmlUtils.skipCurrentTag(parser);
                } else if (i < num && (valueAttr = parser.getAttributeValue((String) null, Telephony.BaseMmsColumns.MMS_VERSION)) != null) {
                    steps.decodeEntryAt(i, valueAttr);
                    i++;
                }
            }
        }
        steps.mNumStepDurations = i;
    }

    public BatteryStats.DailyItem getDailyItemLocked(int daysAgo) {
        int index = (this.mDailyItems.size() - 1) - daysAgo;
        if (index >= 0) {
            return this.mDailyItems.get(index);
        }
        return null;
    }

    public long getCurrentDailyStartTime() {
        return this.mDailyStartTime;
    }

    public long getNextMinDailyDeadline() {
        return this.mNextMinDailyDeadline;
    }

    public long getNextMaxDailyDeadline() {
        return this.mNextMaxDailyDeadline;
    }

    public boolean startIteratingOldHistoryLocked() {
        BatteryStats.HistoryItem historyItem = this.mHistory;
        this.mHistoryIterator = historyItem;
        if (historyItem == null) {
            return false;
        }
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryReadTmp.clear();
        this.mReadOverflow = false;
        this.mIteratingHistory = true;
        return true;
    }

    public boolean getNextOldHistoryLocked(BatteryStats.HistoryItem out) {
        boolean end = this.mHistoryBuffer.dataPosition() >= this.mHistoryBuffer.dataSize();
        if (!end) {
            readHistoryDelta(this.mHistoryBuffer, this.mHistoryReadTmp);
            this.mReadOverflow |= this.mHistoryReadTmp.cmd == 6;
        }
        BatteryStats.HistoryItem cur = this.mHistoryIterator;
        if (cur == null) {
            if (!this.mReadOverflow && !end) {
                Slog.w(TAG, "Old history ends before new history!");
            }
            return false;
        }
        out.setTo(cur);
        this.mHistoryIterator = cur.next;
        if (!this.mReadOverflow) {
            if (end) {
                Slog.w(TAG, "New history ends before old history!");
            } else if (!out.same(this.mHistoryReadTmp)) {
                FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) new LogWriter(5, TAG));
                fastPrintWriter.println("Histories differ!");
                fastPrintWriter.println("Old history:");
                FastPrintWriter fastPrintWriter2 = fastPrintWriter;
                new BatteryStats.HistoryPrinter().printNextItem(fastPrintWriter2, out, 0, false, true);
                fastPrintWriter.println("New history:");
                new BatteryStats.HistoryPrinter().printNextItem(fastPrintWriter2, this.mHistoryReadTmp, 0, false, true);
                fastPrintWriter.flush();
            }
        }
        return true;
    }

    public void finishIteratingOldHistoryLocked() {
        this.mIteratingHistory = false;
        this.mHistoryBuffer.setDataPosition(this.mHistoryBuffer.dataSize());
        this.mHistoryIterator = null;
    }

    public int getHistoryTotalSize() {
        return this.mConstants.MAX_HISTORY_BUFFER * this.mConstants.MAX_HISTORY_FILES;
    }

    public int getHistoryUsedSize() {
        return this.mBatteryStatsHistory.getHistoryUsedSize();
    }

    @UnsupportedAppUsage
    public boolean startIteratingHistoryLocked() {
        this.mBatteryStatsHistory.startIteratingHistory();
        this.mReadOverflow = false;
        this.mIteratingHistory = true;
        this.mReadHistoryStrings = new String[this.mHistoryTagPool.size()];
        this.mReadHistoryUids = new int[this.mHistoryTagPool.size()];
        this.mReadHistoryChars = 0;
        for (Map.Entry<BatteryStats.HistoryTag, Integer> ent : this.mHistoryTagPool.entrySet()) {
            BatteryStats.HistoryTag tag = ent.getKey();
            int idx = ent.getValue().intValue();
            this.mReadHistoryStrings[idx] = tag.string;
            this.mReadHistoryUids[idx] = tag.uid;
            this.mReadHistoryChars += tag.string.length() + 1;
        }
        return true;
    }

    public int getHistoryStringPoolSize() {
        return this.mReadHistoryStrings.length;
    }

    public int getHistoryStringPoolBytes() {
        return (this.mReadHistoryStrings.length * 12) + (this.mReadHistoryChars * 2);
    }

    public String getHistoryTagPoolString(int index) {
        return this.mReadHistoryStrings[index];
    }

    public int getHistoryTagPoolUid(int index) {
        return this.mReadHistoryUids[index];
    }

    @UnsupportedAppUsage
    public boolean getNextHistoryLocked(BatteryStats.HistoryItem out) {
        Parcel p = this.mBatteryStatsHistory.getNextParcel(out);
        if (p == null) {
            return false;
        }
        long lastRealtime = out.time;
        long lastWalltime = out.currentTime;
        readHistoryDelta(p, out);
        if (out.cmd == 5 || out.cmd == 7 || lastWalltime == 0) {
            return true;
        }
        out.currentTime = (out.time - lastRealtime) + lastWalltime;
        return true;
    }

    public void finishIteratingHistoryLocked() {
        this.mBatteryStatsHistory.finishIteratingHistory();
        this.mIteratingHistory = false;
        this.mReadHistoryStrings = null;
        this.mReadHistoryUids = null;
    }

    public long getHistoryBaseTime() {
        return this.mHistoryBaseTime;
    }

    public int getStartCount() {
        return this.mStartCount;
    }

    @UnsupportedAppUsage
    public boolean isOnBattery() {
        return this.mOnBattery;
    }

    public boolean isCharging() {
        return this.mCharging;
    }

    public boolean isScreenOn(int state) {
        return state == 2 || state == 5 || state == 6;
    }

    public boolean isScreenOff(int state) {
        return state == 1;
    }

    public boolean isScreenDoze(int state) {
        return state == 3 || state == 4;
    }

    /* access modifiers changed from: package-private */
    public void initTimes(long uptime, long realtime) {
        this.mStartClockTime = System.currentTimeMillis();
        this.mOnBatteryTimeBase.init(uptime, realtime);
        this.mOnBatteryScreenOffTimeBase.init(uptime, realtime);
        this.mRealtime = 0;
        this.mUptime = 0;
        this.mRealtimeStart = realtime;
        this.mUptimeStart = uptime;
    }

    /* access modifiers changed from: package-private */
    public void initDischarge() {
        this.mLowDischargeAmountSinceCharge = 0;
        this.mHighDischargeAmountSinceCharge = 0;
        this.mDischargeAmountScreenOn = 0;
        this.mDischargeAmountScreenOnSinceCharge = 0;
        this.mDischargeAmountScreenOff = 0;
        this.mDischargeAmountScreenOffSinceCharge = 0;
        this.mDischargeAmountScreenDoze = 0;
        this.mDischargeAmountScreenDozeSinceCharge = 0;
        this.mDischargeStepTracker.init();
        this.mChargeStepTracker.init();
        this.mDischargeScreenOffCounter.reset(false);
        this.mDischargeScreenDozeCounter.reset(false);
        this.mDischargeLightDozeCounter.reset(false);
        this.mDischargeDeepDozeCounter.reset(false);
        this.mDischargeCounter.reset(false);
    }

    public void resetAllStatsCmdLocked() {
        resetAllStatsLocked();
        long mSecUptime = this.mClocks.uptimeMillis();
        long uptime = mSecUptime * 1000;
        long mSecRealtime = this.mClocks.elapsedRealtime();
        long realtime = 1000 * mSecRealtime;
        this.mDischargeStartLevel = this.mHistoryCur.batteryLevel;
        pullPendingStateUpdatesLocked();
        addHistoryRecordLocked(mSecRealtime, mSecUptime);
        byte b = this.mHistoryCur.batteryLevel;
        this.mCurrentBatteryLevel = b;
        this.mDischargePlugLevel = b;
        this.mDischargeUnplugLevel = b;
        this.mDischargeCurrentLevel = b;
        this.mOnBatteryTimeBase.reset(uptime, realtime);
        this.mOnBatteryScreenOffTimeBase.reset(uptime, realtime);
        if ((this.mHistoryCur.states & 524288) == 0) {
            if (isScreenOn(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = this.mHistoryCur.batteryLevel;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else if (isScreenDoze(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = this.mHistoryCur.batteryLevel;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = this.mHistoryCur.batteryLevel;
            }
            this.mDischargeAmountScreenOn = 0;
            this.mDischargeAmountScreenOff = 0;
            this.mDischargeAmountScreenDoze = 0;
        }
        initActiveHistoryEventsLocked(mSecRealtime, mSecUptime);
    }

    private void resetAllStatsLocked() {
        long uptimeMillis = this.mClocks.uptimeMillis();
        long elapsedRealtimeMillis = this.mClocks.elapsedRealtime();
        this.mStartCount = 0;
        initTimes(uptimeMillis * 1000, elapsedRealtimeMillis * 1000);
        this.mScreenOnTimer.reset(false);
        this.mScreenDozeTimer.reset(false);
        for (int i = 0; i < 5; i++) {
            this.mScreenBrightnessTimer[i].reset(false);
        }
        if (this.mPowerProfile != null) {
            this.mEstimatedBatteryCapacity = (int) this.mPowerProfile.getBatteryCapacity();
        } else {
            this.mEstimatedBatteryCapacity = -1;
        }
        this.mMinLearnedBatteryCapacity = -1;
        this.mMaxLearnedBatteryCapacity = -1;
        this.mInteractiveTimer.reset(false);
        this.mPowerSaveModeEnabledTimer.reset(false);
        this.mLastIdleTimeStart = elapsedRealtimeMillis;
        this.mLongestLightIdleTime = 0;
        this.mLongestFullIdleTime = 0;
        this.mDeviceIdleModeLightTimer.reset(false);
        this.mDeviceIdleModeFullTimer.reset(false);
        this.mDeviceLightIdlingTimer.reset(false);
        this.mDeviceIdlingTimer.reset(false);
        this.mPhoneOnTimer.reset(false);
        this.mAudioOnTimer.reset(false);
        this.mVideoOnTimer.reset(false);
        this.mFlashlightOnTimer.reset(false);
        this.mCameraOnTimer.reset(false);
        this.mBluetoothScanTimer.reset(false);
        for (int i2 = 0; i2 < 5; i2++) {
            this.mPhoneSignalStrengthsTimer[i2].reset(false);
        }
        this.mPhoneSignalScanningTimer.reset(false);
        for (int i3 = 0; i3 < 22; i3++) {
            this.mPhoneDataConnectionsTimer[i3].reset(false);
        }
        for (int i4 = 0; i4 < 10; i4++) {
            this.mNetworkByteActivityCounters[i4].reset(false);
            this.mNetworkPacketActivityCounters[i4].reset(false);
        }
        this.mMobileRadioActiveTimer.reset(false);
        this.mMobileRadioActivePerAppTimer.reset(false);
        this.mMobileRadioActiveAdjustedTime.reset(false);
        this.mMobileRadioActiveUnknownTime.reset(false);
        this.mMobileRadioActiveUnknownCount.reset(false);
        this.mWifiOnTimer.reset(false);
        this.mGlobalWifiRunningTimer.reset(false);
        for (int i5 = 0; i5 < 8; i5++) {
            this.mWifiStateTimer[i5].reset(false);
        }
        for (int i6 = 0; i6 < 13; i6++) {
            this.mWifiSupplStateTimer[i6].reset(false);
        }
        for (int i7 = 0; i7 < 5; i7++) {
            this.mWifiSignalStrengthsTimer[i7].reset(false);
        }
        this.mWifiMulticastWakelockTimer.reset(false);
        this.mWifiActiveTimer.reset(false);
        this.mWifiActivity.reset(false);
        for (int i8 = 0; i8 < 2; i8++) {
            this.mGpsSignalQualityTimer[i8].reset(false);
        }
        this.mBluetoothActivity.reset(false);
        this.mModemActivity.reset(false);
        this.mNumConnectivityChange = 0;
        int i9 = 0;
        while (i9 < this.mUidStats.size()) {
            if (this.mUidStats.valueAt(i9).reset(uptimeMillis * 1000, elapsedRealtimeMillis * 1000)) {
                this.mUidStats.valueAt(i9).detachFromTimeBase();
                this.mUidStats.remove(this.mUidStats.keyAt(i9));
                i9--;
            }
            i9++;
        }
        if (this.mRpmStats.size() > 0) {
            for (SamplingTimer timer : this.mRpmStats.values()) {
                this.mOnBatteryTimeBase.remove(timer);
            }
            this.mRpmStats.clear();
        }
        if (this.mScreenOffRpmStats.size() > 0) {
            for (SamplingTimer timer2 : this.mScreenOffRpmStats.values()) {
                this.mOnBatteryScreenOffTimeBase.remove(timer2);
            }
            this.mScreenOffRpmStats.clear();
        }
        if (this.mKernelWakelockStats.size() > 0) {
            for (SamplingTimer timer3 : this.mKernelWakelockStats.values()) {
                this.mOnBatteryScreenOffTimeBase.remove(timer3);
            }
            this.mKernelWakelockStats.clear();
        }
        if (this.mKernelMemoryStats.size() > 0) {
            for (int i10 = 0; i10 < this.mKernelMemoryStats.size(); i10++) {
                this.mOnBatteryTimeBase.remove(this.mKernelMemoryStats.valueAt(i10));
            }
            this.mKernelMemoryStats.clear();
        }
        if (this.mWakeupReasonStats.size() > 0) {
            for (SamplingTimer timer4 : this.mWakeupReasonStats.values()) {
                this.mOnBatteryTimeBase.remove(timer4);
            }
            this.mWakeupReasonStats.clear();
        }
        this.mTmpRailStats.reset();
        this.mLastHistoryStepDetails = null;
        this.mLastStepCpuSystemTime = 0;
        this.mLastStepCpuUserTime = 0;
        this.mCurStepCpuSystemTime = 0;
        this.mCurStepCpuUserTime = 0;
        this.mCurStepCpuUserTime = 0;
        this.mLastStepCpuUserTime = 0;
        this.mCurStepCpuSystemTime = 0;
        this.mLastStepCpuSystemTime = 0;
        this.mCurStepStatUserTime = 0;
        this.mLastStepStatUserTime = 0;
        this.mCurStepStatSystemTime = 0;
        this.mLastStepStatSystemTime = 0;
        this.mCurStepStatIOWaitTime = 0;
        this.mLastStepStatIOWaitTime = 0;
        this.mCurStepStatIrqTime = 0;
        this.mLastStepStatIrqTime = 0;
        this.mCurStepStatSoftIrqTime = 0;
        this.mLastStepStatSoftIrqTime = 0;
        this.mCurStepStatIdleTime = 0;
        this.mLastStepStatIdleTime = 0;
        this.mNumAllUidCpuTimeReads = 0;
        this.mNumUidsRemoved = 0;
        initDischarge();
        clearHistoryLocked();
        this.mBatteryStatsHistory.resetAllFiles();
        this.mHandler.sendEmptyMessage(4);
    }

    private void initActiveHistoryEventsLocked(long elapsedRealtimeMs, long uptimeMs) {
        HashMap<String, SparseIntArray> active;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < 22) {
                if ((this.mRecordAllHistory || i2 != 1) && (active = this.mActiveEvents.getStateForEvent(i2)) != null) {
                    for (Map.Entry<String, SparseIntArray> ent : active.entrySet()) {
                        SparseIntArray uids = ent.getValue();
                        int j = 0;
                        while (true) {
                            int j2 = j;
                            if (j2 < uids.size()) {
                                addHistoryEventLocked(elapsedRealtimeMs, uptimeMs, i2, ent.getKey(), uids.keyAt(j2));
                                j = j2 + 1;
                            }
                        }
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateDischargeScreenLevelsLocked(int oldState, int newState) {
        updateOldDischargeScreenLevelLocked(oldState);
        updateNewDischargeScreenLevelLocked(newState);
    }

    private void updateOldDischargeScreenLevelLocked(int state) {
        int diff;
        if (isScreenOn(state)) {
            int diff2 = this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
            if (diff2 > 0) {
                this.mDischargeAmountScreenOn += diff2;
                this.mDischargeAmountScreenOnSinceCharge += diff2;
            }
        } else if (isScreenDoze(state)) {
            int diff3 = this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
            if (diff3 > 0) {
                this.mDischargeAmountScreenDoze += diff3;
                this.mDischargeAmountScreenDozeSinceCharge += diff3;
            }
        } else if (isScreenOff(state) && (diff = this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel) > 0) {
            this.mDischargeAmountScreenOff += diff;
            this.mDischargeAmountScreenOffSinceCharge += diff;
        }
    }

    private void updateNewDischargeScreenLevelLocked(int state) {
        if (isScreenOn(state)) {
            this.mDischargeScreenOnUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
        } else if (isScreenDoze(state)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
        } else if (isScreenOff(state)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
            this.mDischargeScreenOffUnplugLevel = this.mDischargeCurrentLevel;
        }
    }

    public void pullPendingStateUpdatesLocked() {
        if (this.mOnBatteryInternal) {
            updateDischargeScreenLevelsLocked(this.mScreenState, this.mScreenState);
        }
    }

    private NetworkStats readNetworkStatsLocked(String[] ifaces) {
        try {
            if (ArrayUtils.isEmpty((T[]) ifaces)) {
                return null;
            }
            INetworkStatsService statsService = INetworkStatsService.Stub.asInterface(ServiceManager.getService(Context.NETWORK_STATS_SERVICE));
            if (statsService != null) {
                return statsService.getDetailedUidStats(ifaces);
            }
            Slog.e(TAG, "Failed to get networkStatsService ");
            return null;
        } catch (RemoteException e) {
            Slog.e(TAG, "failed to read network stats for ifaces: " + Arrays.toString(ifaces) + e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0035, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x02fc, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateWifiState(android.net.wifi.WifiActivityEnergyInfo r54) {
        /*
            r53 = this;
            r1 = r53
            r2 = 0
            java.lang.Object r3 = r1.mWifiNetworkLock
            monitor-enter(r3)
            java.lang.String[] r0 = r1.mWifiIfaces     // Catch:{ all -> 0x0304 }
            android.net.NetworkStats r0 = r1.readNetworkStatsLocked(r0)     // Catch:{ all -> 0x0304 }
            if (r0 == 0) goto L_0x0027
            android.net.NetworkStats r4 = r1.mLastWifiNetworkStats     // Catch:{ all -> 0x0304 }
            android.util.Pools$Pool<android.net.NetworkStats> r5 = r1.mNetworkStatsPool     // Catch:{ all -> 0x0304 }
            java.lang.Object r5 = r5.acquire()     // Catch:{ all -> 0x0304 }
            android.net.NetworkStats r5 = (android.net.NetworkStats) r5     // Catch:{ all -> 0x0304 }
            r6 = 0
            android.net.NetworkStats r4 = android.net.NetworkStats.subtract(r0, r4, r6, r6, r5)     // Catch:{ all -> 0x0304 }
            r2 = r4
            android.util.Pools$Pool<android.net.NetworkStats> r4 = r1.mNetworkStatsPool     // Catch:{ all -> 0x0304 }
            android.net.NetworkStats r5 = r1.mLastWifiNetworkStats     // Catch:{ all -> 0x0304 }
            r4.release(r5)     // Catch:{ all -> 0x0304 }
            r1.mLastWifiNetworkStats = r0     // Catch:{ all -> 0x0304 }
        L_0x0027:
            monitor-exit(r3)     // Catch:{ all -> 0x0304 }
            monitor-enter(r53)
            boolean r0 = r1.mOnBatteryInternal     // Catch:{ all -> 0x0301 }
            if (r0 != 0) goto L_0x0036
            if (r2 == 0) goto L_0x0034
            android.util.Pools$Pool<android.net.NetworkStats> r0 = r1.mNetworkStatsPool     // Catch:{ all -> 0x0301 }
            r0.release(r2)     // Catch:{ all -> 0x0301 }
        L_0x0034:
            monitor-exit(r53)     // Catch:{ all -> 0x0301 }
            return
        L_0x0036:
            com.android.internal.os.BatteryStatsImpl$Clocks r0 = r1.mClocks     // Catch:{ all -> 0x0301 }
            long r3 = r0.elapsedRealtime()     // Catch:{ all -> 0x0301 }
            android.util.SparseLongArray r0 = new android.util.SparseLongArray     // Catch:{ all -> 0x0301 }
            r0.<init>()     // Catch:{ all -> 0x0301 }
            android.util.SparseLongArray r5 = new android.util.SparseLongArray     // Catch:{ all -> 0x0301 }
            r5.<init>()     // Catch:{ all -> 0x0301 }
            r6 = 0
            r8 = 0
            if (r2 == 0) goto L_0x0115
            android.net.NetworkStats$Entry r13 = new android.net.NetworkStats$Entry     // Catch:{ all -> 0x0301 }
            r13.<init>()     // Catch:{ all -> 0x0301 }
            int r14 = r2.size()     // Catch:{ all -> 0x0301 }
            r15 = r8
            r7 = r6
            r6 = 0
        L_0x0058:
            if (r6 >= r14) goto L_0x010d
            android.net.NetworkStats$Entry r9 = r2.getValues(r6, r13)     // Catch:{ all -> 0x0301 }
            r13 = r9
            long r10 = r13.rxBytes     // Catch:{ all -> 0x0301 }
            r17 = 0
            int r9 = (r10 > r17 ? 1 : (r10 == r17 ? 0 : -1))
            if (r9 != 0) goto L_0x006f
            long r9 = r13.txBytes     // Catch:{ all -> 0x0301 }
            int r9 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            if (r9 != 0) goto L_0x006f
            goto L_0x0109
        L_0x006f:
            int r9 = r13.uid     // Catch:{ all -> 0x0301 }
            int r9 = r1.mapUid(r9)     // Catch:{ all -> 0x0301 }
            com.android.internal.os.BatteryStatsImpl$Uid r19 = r1.getUidStatsLocked(r9)     // Catch:{ all -> 0x0301 }
            long r9 = r13.rxBytes     // Catch:{ all -> 0x0301 }
            r17 = 0
            int r9 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            if (r9 == 0) goto L_0x00c0
            r20 = 2
            long r9 = r13.rxBytes     // Catch:{ all -> 0x0301 }
            long r11 = r13.rxPackets     // Catch:{ all -> 0x0301 }
            r21 = r9
            r23 = r11
            r19.noteNetworkActivityLocked(r20, r21, r23)     // Catch:{ all -> 0x0301 }
            int r9 = r13.set     // Catch:{ all -> 0x0301 }
            if (r9 != 0) goto L_0x00a1
            r27 = 8
            long r9 = r13.rxBytes     // Catch:{ all -> 0x0301 }
            long r11 = r13.rxPackets     // Catch:{ all -> 0x0301 }
            r26 = r19
            r28 = r9
            r30 = r11
            r26.noteNetworkActivityLocked(r27, r28, r30)     // Catch:{ all -> 0x0301 }
        L_0x00a1:
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r9 = r1.mNetworkByteActivityCounters     // Catch:{ all -> 0x0301 }
            r10 = 2
            r9 = r9[r10]     // Catch:{ all -> 0x0301 }
            long r11 = r13.rxBytes     // Catch:{ all -> 0x0301 }
            r9.addCountLocked(r11)     // Catch:{ all -> 0x0301 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r9 = r1.mNetworkPacketActivityCounters     // Catch:{ all -> 0x0301 }
            r9 = r9[r10]     // Catch:{ all -> 0x0301 }
            long r10 = r13.rxPackets     // Catch:{ all -> 0x0301 }
            r9.addCountLocked(r10)     // Catch:{ all -> 0x0301 }
            int r9 = r19.getUid()     // Catch:{ all -> 0x0301 }
            long r10 = r13.rxPackets     // Catch:{ all -> 0x0301 }
            r0.put(r9, r10)     // Catch:{ all -> 0x0301 }
            long r9 = r13.rxPackets     // Catch:{ all -> 0x0301 }
            long r15 = r15 + r9
        L_0x00c0:
            long r9 = r13.txBytes     // Catch:{ all -> 0x0301 }
            r11 = 0
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 == 0) goto L_0x0109
            r27 = 3
            long r9 = r13.txBytes     // Catch:{ all -> 0x0301 }
            long r11 = r13.txPackets     // Catch:{ all -> 0x0301 }
            r26 = r19
            r28 = r9
            r30 = r11
            r26.noteNetworkActivityLocked(r27, r28, r30)     // Catch:{ all -> 0x0301 }
            int r9 = r13.set     // Catch:{ all -> 0x0301 }
            if (r9 != 0) goto L_0x00ea
            r27 = 9
            long r9 = r13.txBytes     // Catch:{ all -> 0x0301 }
            long r11 = r13.txPackets     // Catch:{ all -> 0x0301 }
            r26 = r19
            r28 = r9
            r30 = r11
            r26.noteNetworkActivityLocked(r27, r28, r30)     // Catch:{ all -> 0x0301 }
        L_0x00ea:
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r9 = r1.mNetworkByteActivityCounters     // Catch:{ all -> 0x0301 }
            r10 = 3
            r9 = r9[r10]     // Catch:{ all -> 0x0301 }
            long r11 = r13.txBytes     // Catch:{ all -> 0x0301 }
            r9.addCountLocked(r11)     // Catch:{ all -> 0x0301 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r9 = r1.mNetworkPacketActivityCounters     // Catch:{ all -> 0x0301 }
            r9 = r9[r10]     // Catch:{ all -> 0x0301 }
            long r10 = r13.txPackets     // Catch:{ all -> 0x0301 }
            r9.addCountLocked(r10)     // Catch:{ all -> 0x0301 }
            int r9 = r19.getUid()     // Catch:{ all -> 0x0301 }
            long r10 = r13.txPackets     // Catch:{ all -> 0x0301 }
            r5.put(r9, r10)     // Catch:{ all -> 0x0301 }
            long r9 = r13.txPackets     // Catch:{ all -> 0x0301 }
            long r7 = r7 + r9
        L_0x0109:
            int r6 = r6 + 1
            goto L_0x0058
        L_0x010d:
            android.util.Pools$Pool<android.net.NetworkStats> r6 = r1.mNetworkStatsPool     // Catch:{ all -> 0x0301 }
            r6.release(r2)     // Catch:{ all -> 0x0301 }
            r2 = 0
            r6 = r7
            goto L_0x0116
        L_0x0115:
            r15 = r8
        L_0x0116:
            if (r54 == 0) goto L_0x02f9
            r9 = 1
            r1.mHasWifiReporting = r9     // Catch:{ all -> 0x02f5 }
            long r9 = r54.getControllerTxTimeMillis()     // Catch:{ all -> 0x02f5 }
            long r11 = r54.getControllerRxTimeMillis()     // Catch:{ all -> 0x02f5 }
            long r13 = r54.getControllerScanTimeMillis()     // Catch:{ all -> 0x02f5 }
            long r19 = r54.getControllerIdleTimeMillis()     // Catch:{ all -> 0x02f5 }
            long r21 = r9 + r11
            long r21 = r21 + r19
            r23 = r11
            r26 = r9
            r28 = 0
            r30 = 0
            r32 = r2
            android.util.SparseArray<com.android.internal.os.BatteryStatsImpl$Uid> r2 = r1.mUidStats     // Catch:{ all -> 0x02fd }
            int r2 = r2.size()     // Catch:{ all -> 0x02fd }
            r33 = r28
            r28 = 0
        L_0x0143:
            r35 = r28
            r28 = 1000(0x3e8, double:4.94E-321)
            r8 = r35
            if (r8 >= r2) goto L_0x0178
            r36 = r13
            android.util.SparseArray<com.android.internal.os.BatteryStatsImpl$Uid> r13 = r1.mUidStats     // Catch:{ all -> 0x02fd }
            java.lang.Object r13 = r13.valueAt(r8)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$Uid r13 = (com.android.internal.os.BatteryStatsImpl.Uid) r13     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$DualTimer r14 = r13.mWifiScanTimer     // Catch:{ all -> 0x02fd }
            r38 = r6
            long r6 = r3 * r28
            long r6 = r14.getTimeSinceMarkLocked(r6)     // Catch:{ all -> 0x02fd }
            long r6 = r6 / r28
            long r30 = r30 + r6
            com.android.internal.os.BatteryStatsImpl$StopwatchTimer r6 = r13.mFullWifiLockTimer     // Catch:{ all -> 0x02fd }
            r40 = r13
            long r13 = r3 * r28
            long r6 = r6.getTimeSinceMarkLocked(r13)     // Catch:{ all -> 0x02fd }
            long r6 = r6 / r28
            long r33 = r33 + r6
            int r28 = r8 + 1
            r13 = r36
            r6 = r38
            goto L_0x0143
        L_0x0178:
            r38 = r6
            r36 = r13
            r6 = 0
        L_0x017d:
            if (r6 >= r2) goto L_0x0211
            android.util.SparseArray<com.android.internal.os.BatteryStatsImpl$Uid> r7 = r1.mUidStats     // Catch:{ all -> 0x02fd }
            java.lang.Object r7 = r7.valueAt(r6)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$Uid r7 = (com.android.internal.os.BatteryStatsImpl.Uid) r7     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$DualTimer r8 = r7.mWifiScanTimer     // Catch:{ all -> 0x02fd }
            long r13 = r3 * r28
            long r13 = r8.getTimeSinceMarkLocked(r13)     // Catch:{ all -> 0x02fd }
            long r13 = r13 / r28
            r17 = 0
            int r8 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r8 <= 0) goto L_0x01d9
            com.android.internal.os.BatteryStatsImpl$DualTimer r8 = r7.mWifiScanTimer     // Catch:{ all -> 0x02fd }
            r8.setMark(r3)     // Catch:{ all -> 0x02fd }
            r40 = r13
            r42 = r13
            int r8 = (r30 > r11 ? 1 : (r30 == r11 ? 0 : -1))
            if (r8 <= 0) goto L_0x01aa
            long r44 = r11 * r40
            long r44 = r44 / r30
            r40 = r44
        L_0x01aa:
            r46 = r11
            r11 = r40
            int r8 = (r30 > r9 ? 1 : (r30 == r9 ? 0 : -1))
            if (r8 <= 0) goto L_0x01b8
            long r40 = r9 * r42
            long r40 = r40 / r30
            r42 = r40
        L_0x01b8:
            r48 = r9
            r8 = r42
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r10 = r7.getOrCreateWifiControllerActivityLocked()     // Catch:{ all -> 0x02fd }
            r50 = r2
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r10.getRxTimeCounter()     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r11)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r2 = r10.getTxTimeCounters()     // Catch:{ all -> 0x02fd }
            r25 = 0
            r2 = r2[r25]     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r8)     // Catch:{ all -> 0x02fd }
            long r23 = r23 - r11
            long r26 = r26 - r8
            goto L_0x01df
        L_0x01d9:
            r50 = r2
            r48 = r9
            r46 = r11
        L_0x01df:
            com.android.internal.os.BatteryStatsImpl$StopwatchTimer r2 = r7.mFullWifiLockTimer     // Catch:{ all -> 0x02fd }
            long r8 = r3 * r28
            long r8 = r2.getTimeSinceMarkLocked(r8)     // Catch:{ all -> 0x02fd }
            long r8 = r8 / r28
            r10 = 0
            int r2 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r2 <= 0) goto L_0x0207
            com.android.internal.os.BatteryStatsImpl$StopwatchTimer r2 = r7.mFullWifiLockTimer     // Catch:{ all -> 0x02fd }
            r2.setMark(r3)     // Catch:{ all -> 0x02fd }
            long r17 = r8 * r19
            long r17 = r17 / r33
            r51 = r17
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r7.getOrCreateWifiControllerActivityLocked()     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r2.getIdleTimeCounter()     // Catch:{ all -> 0x02fd }
            r10 = r51
            r2.addCountLocked(r10)     // Catch:{ all -> 0x02fd }
        L_0x0207:
            int r6 = r6 + 1
            r11 = r46
            r9 = r48
            r2 = r50
            goto L_0x017d
        L_0x0211:
            r50 = r2
            r48 = r9
            r46 = r11
            r2 = 0
        L_0x0218:
            int r6 = r5.size()     // Catch:{ all -> 0x02fd }
            if (r2 >= r6) goto L_0x023f
            int r6 = r5.keyAt(r2)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$Uid r6 = r1.getUidStatsLocked(r6)     // Catch:{ all -> 0x02fd }
            long r7 = r5.valueAt(r2)     // Catch:{ all -> 0x02fd }
            long r7 = r7 * r26
            long r7 = r7 / r38
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r9 = r6.getOrCreateWifiControllerActivityLocked()     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r9 = r9.getTxTimeCounters()     // Catch:{ all -> 0x02fd }
            r10 = 0
            r9 = r9[r10]     // Catch:{ all -> 0x02fd }
            r9.addCountLocked(r7)     // Catch:{ all -> 0x02fd }
            int r2 = r2 + 1
            goto L_0x0218
        L_0x023f:
            r2 = 0
        L_0x0240:
            int r6 = r0.size()     // Catch:{ all -> 0x02fd }
            if (r2 >= r6) goto L_0x0263
            int r6 = r0.keyAt(r2)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$Uid r6 = r1.getUidStatsLocked(r6)     // Catch:{ all -> 0x02fd }
            long r7 = r0.valueAt(r2)     // Catch:{ all -> 0x02fd }
            long r7 = r7 * r23
            long r7 = r7 / r15
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r9 = r6.getOrCreateWifiControllerActivityLocked()     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r9 = r9.getRxTimeCounter()     // Catch:{ all -> 0x02fd }
            r9.addCountLocked(r7)     // Catch:{ all -> 0x02fd }
            int r2 = r2 + 1
            goto L_0x0240
        L_0x0263:
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r1.mWifiActivity     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r2.getRxTimeCounter()     // Catch:{ all -> 0x02fd }
            long r6 = r54.getControllerRxTimeMillis()     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r6)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r1.mWifiActivity     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r2 = r2.getTxTimeCounters()     // Catch:{ all -> 0x02fd }
            r6 = 0
            r2 = r2[r6]     // Catch:{ all -> 0x02fd }
            long r6 = r54.getControllerTxTimeMillis()     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r6)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r1.mWifiActivity     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r2.getScanTimeCounter()     // Catch:{ all -> 0x02fd }
            long r6 = r54.getControllerScanTimeMillis()     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r6)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r1.mWifiActivity     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r2.getIdleTimeCounter()     // Catch:{ all -> 0x02fd }
            long r6 = r54.getControllerIdleTimeMillis()     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r6)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.PowerProfile r2 = r1.mPowerProfile     // Catch:{ all -> 0x02fd }
            java.lang.String r6 = "wifi.controller.voltage"
            double r6 = r2.getAveragePower(r6)     // Catch:{ all -> 0x02fd }
            r8 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r6 = r6 / r8
            r8 = 0
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 == 0) goto L_0x02bf
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r1.mWifiActivity     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r2.getPowerCounter()     // Catch:{ all -> 0x02fd }
            long r8 = r54.getControllerEnergyUsed()     // Catch:{ all -> 0x02fd }
            double r8 = (double) r8     // Catch:{ all -> 0x02fd }
            double r8 = r8 / r6
            long r8 = (long) r8     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r8)     // Catch:{ all -> 0x02fd }
        L_0x02bf:
            com.android.internal.os.RailStats r2 = r1.mTmpRailStats     // Catch:{ all -> 0x02fd }
            long r8 = r2.getWifiTotalEnergyUseduWs()     // Catch:{ all -> 0x02fd }
            double r8 = (double) r8     // Catch:{ all -> 0x02fd }
            double r8 = r8 / r6
            long r8 = (long) r8     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r2 = r1.mWifiActivity     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r2 = r2.getMonitoredRailChargeConsumedMaMs()     // Catch:{ all -> 0x02fd }
            r2.addCountLocked(r8)     // Catch:{ all -> 0x02fd }
            android.os.BatteryStats$HistoryItem r2 = r1.mHistoryCur     // Catch:{ all -> 0x02fd }
            double r10 = r2.wifiRailChargeMah     // Catch:{ all -> 0x02fd }
            double r12 = (double) r8     // Catch:{ all -> 0x02fd }
            r17 = 4704985352480227328(0x414b774000000000, double:3600000.0)
            double r12 = r12 / r17
            double r10 = r10 + r12
            r2.wifiRailChargeMah = r10     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$Clocks r2 = r1.mClocks     // Catch:{ all -> 0x02fd }
            long r10 = r2.elapsedRealtime()     // Catch:{ all -> 0x02fd }
            com.android.internal.os.BatteryStatsImpl$Clocks r2 = r1.mClocks     // Catch:{ all -> 0x02fd }
            long r12 = r2.uptimeMillis()     // Catch:{ all -> 0x02fd }
            r1.addHistoryRecordLocked(r10, r12)     // Catch:{ all -> 0x02fd }
            com.android.internal.os.RailStats r2 = r1.mTmpRailStats     // Catch:{ all -> 0x02fd }
            r2.resetWifiTotalEnergyUsed()     // Catch:{ all -> 0x02fd }
            goto L_0x02fb
        L_0x02f5:
            r0 = move-exception
            r32 = r2
            goto L_0x0302
        L_0x02f9:
            r32 = r2
        L_0x02fb:
            monitor-exit(r53)     // Catch:{ all -> 0x02fd }
            return
        L_0x02fd:
            r0 = move-exception
            r2 = r32
            goto L_0x0302
        L_0x0301:
            r0 = move-exception
        L_0x0302:
            monitor-exit(r53)     // Catch:{ all -> 0x0301 }
            throw r0
        L_0x0304:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0304 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.updateWifiState(android.net.wifi.WifiActivityEnergyInfo):void");
    }

    private ModemActivityInfo getDeltaModemActivityInfo(ModemActivityInfo activityInfo) {
        if (activityInfo == null) {
            return null;
        }
        int[] txTimeMs = new int[5];
        for (int i = 0; i < 5; i++) {
            txTimeMs[i] = activityInfo.getTxTimeMillis()[i] - this.mLastModemActivityInfo.getTxTimeMillis()[i];
        }
        ModemActivityInfo modemActivityInfo = new ModemActivityInfo(activityInfo.getTimestamp(), activityInfo.getSleepTimeMillis() - this.mLastModemActivityInfo.getSleepTimeMillis(), activityInfo.getIdleTimeMillis() - this.mLastModemActivityInfo.getIdleTimeMillis(), txTimeMs, activityInfo.getRxTimeMillis() - this.mLastModemActivityInfo.getRxTimeMillis(), activityInfo.getEnergyUsed() - this.mLastModemActivityInfo.getEnergyUsed());
        this.mLastModemActivityInfo = activityInfo;
        return modemActivityInfo;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0297, code lost:
        r45 = r2;
        r43 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x029b, code lost:
        r4 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x029d, code lost:
        r0 = r40 + 1;
        r14 = r37;
        r15 = r38;
        r2 = r45;
        r1 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x02a9, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x02aa, code lost:
        r45 = r2;
        r1 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02af, code lost:
        r45 = r2;
        r37 = r14;
        r38 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02b7, code lost:
        if (r7 <= 0) goto L_0x02c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02b9, code lost:
        r1 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:?, code lost:
        r1.mMobileRadioActiveUnknownTime.addCountLocked(r7);
        r1.mMobileRadioActiveUnknownCount.addCountLocked(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02c8, code lost:
        r1 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02ca, code lost:
        r1.mNetworkStatsPool.release(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02d1, code lost:
        r45 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02d3, code lost:
        monitor-exit(r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02d4, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02d5, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x02d6, code lost:
        r45 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x02d8, code lost:
        monitor-exit(r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x02d9, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x02da, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
        monitor-enter(r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0038, code lost:
        if (r1.mOnBatteryInternal != false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003a, code lost:
        if (r3 == null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.mNetworkStatsPool.release(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        r45 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        monitor-exit(r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        if (r2 == null) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        r1.mHasModemReporting = true;
        r1.mModemActivity.getIdleTimeCounter().addCountLocked((long) r2.getIdleTimeMillis());
        r1.mModemActivity.getSleepTimeCounter().addCountLocked((long) r2.getSleepTimeMillis());
        r1.mModemActivity.getRxTimeCounter().addCountLocked((long) r2.getRxTimeMillis());
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007a, code lost:
        if (r6 >= 5) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007c, code lost:
        r1.mModemActivity.getTxTimeCounters()[r6].addCountLocked((long) r2.getTxTimeMillis()[r6]);
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0091, code lost:
        r6 = r1.mPowerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE) / 1000.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a4, code lost:
        if (r6 == 0.0d) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a6, code lost:
        r8 = ((((double) r2.getSleepTimeMillis()) * r1.mPowerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_MODEM_CONTROLLER_SLEEP)) + (((double) r2.getIdleTimeMillis()) * r1.mPowerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_MODEM_CONTROLLER_IDLE))) + (((double) r2.getRxTimeMillis()) * r1.mPowerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_MODEM_CONTROLLER_RX));
        r10 = r2.getTxTimeMillis();
        r11 = r8;
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e1, code lost:
        if (r8 >= java.lang.Math.min(r10.length, 5)) goto L_0x00f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e3, code lost:
        r11 = r11 + (((double) r10[r8]) * r1.mPowerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_MODEM_CONTROLLER_TX, r8));
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00f4, code lost:
        r1.mModemActivity.getPowerCounter().addCountLocked((long) r11);
        r8 = (long) (((double) r1.mTmpRailStats.getCellularTotalEnergyUseduWs()) / r6);
        r1.mModemActivity.getMonitoredRailChargeConsumedMaMs().addCountLocked(r8);
        r17 = r6;
        r1.mHistoryCur.modemRailChargeMah += ((double) r8) / 3600000.0d;
        r1.addHistoryRecordLocked(r1.mClocks.elapsedRealtime(), r1.mClocks.uptimeMillis());
        r1.mTmpRailStats.resetCellularTotalEnergyUsed();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r5 = r1.mClocks.elapsedRealtime();
        r7 = r1.mMobileRadioActivePerAppTimer.getTimeSinceMarkLocked(1000 * r5);
        r1.mMobileRadioActivePerAppTimer.setMark(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x014d, code lost:
        if (r3 == null) goto L_0x02d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x014f, code lost:
        r13 = new android.net.NetworkStats.Entry();
        r14 = r3.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0158, code lost:
        r15 = 0;
        r10 = 0;
        r9 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x015d, code lost:
        if (r9 >= r14) goto L_0x01fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r13 = r3.getValues(r9, r13);
        r21 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x016a, code lost:
        if (r13.rxPackets != 0) goto L_0x0174;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0170, code lost:
        if (r13.txPackets != 0) goto L_0x0174;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0174, code lost:
        r10 = r10 + r13.rxPackets;
        r15 = r15 + r13.txPackets;
        r23 = r1.getUidStatsLocked(r1.mapUid(r13.uid));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r23.noteNetworkActivityLocked(0, r13.rxBytes, r13.rxPackets);
        r23.noteNetworkActivityLocked(1, r13.txBytes, r13.txPackets);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01a2, code lost:
        if (r13.set != 0) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01a4, code lost:
        r23.noteNetworkActivityLocked(6, r13.rxBytes, r13.rxPackets);
        r23.noteNetworkActivityLocked(7, r13.txBytes, r13.txPackets);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x01c2, code lost:
        r1 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r1.mNetworkByteActivityCounters[0].addCountLocked(r13.rxBytes);
        r1.mNetworkByteActivityCounters[1].addCountLocked(r13.txBytes);
        r1.mNetworkPacketActivityCounters[0].addCountLocked(r13.rxPackets);
        r1.mNetworkPacketActivityCounters[1].addCountLocked(r13.txPackets);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01ec, code lost:
        r9 = r9 + 1;
        r5 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01f3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01f4, code lost:
        r45 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01f6, code lost:
        r1 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01fa, code lost:
        r21 = r5;
        r4 = r10 + r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0200, code lost:
        if (r4 <= 0) goto L_0x02af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0202, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0203, code lost:
        if (r0 >= r14) goto L_0x02af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        r13 = r3.getValues(r0, r13);
        r37 = r14;
        r38 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0212, code lost:
        if (r13.rxPackets != 0) goto L_0x0221;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0218, code lost:
        if (r13.txPackets != 0) goto L_0x0221;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x021a, code lost:
        r40 = r0;
        r45 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        r6 = r1.getUidStatsLocked(r1.mapUid(r13.uid));
        r40 = r0;
        r14 = r13.rxPackets + r13.txPackets;
        r0 = (r7 * r14) / r4;
        r6.noteMobileRadioActiveTimeLocked(r0);
        r7 = r7 - r0;
        r4 = r4 - r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x023a, code lost:
        if (r2 == null) goto L_0x0297;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x023c, code lost:
        r9 = r6.getOrCreateModemControllerActivityLocked();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0243, code lost:
        if (r10 <= 0) goto L_0x0263;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0245, code lost:
        r41 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x024b, code lost:
        if (r13.rxPackets <= 0) goto L_0x0260;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x024d, code lost:
        r43 = r4;
        r9.getRxTimeCounter().addCountLocked((r13.rxPackets * ((long) r2.getRxTimeMillis())) / r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0260, code lost:
        r43 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0263, code lost:
        r41 = r0;
        r43 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0269, code lost:
        if (r38 <= 0) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x026f, code lost:
        if (r13.txPackets <= 0) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0271, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0273, code lost:
        if (r0 >= 5) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x027d, code lost:
        r45 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        r9.getTxTimeCounters()[r0].addCountLocked((r13.txPackets * ((long) r2.getTxTimeMillis()[r0])) / r38);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x028c, code lost:
        r0 = r0 + 1;
        r2 = r45;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0291, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0294, code lost:
        r45 = r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateMobileRadioState(android.telephony.ModemActivityInfo r47) {
        /*
            r46 = this;
            r1 = r46
            android.telephony.ModemActivityInfo r2 = r46.getDeltaModemActivityInfo(r47)
            r1.addModemTxPowerToHistory(r2)
            r3 = 0
            java.lang.Object r4 = r1.mModemNetworkLock
            monitor-enter(r4)
            java.lang.String[] r0 = r1.mModemIfaces     // Catch:{ all -> 0x02dc }
            android.net.NetworkStats r0 = r1.readNetworkStatsLocked(r0)     // Catch:{ all -> 0x02dc }
            if (r0 == 0) goto L_0x0034
            android.net.NetworkStats r5 = r1.mLastModemNetworkStats     // Catch:{ all -> 0x002f }
            android.util.Pools$Pool<android.net.NetworkStats> r6 = r1.mNetworkStatsPool     // Catch:{ all -> 0x002f }
            java.lang.Object r6 = r6.acquire()     // Catch:{ all -> 0x002f }
            android.net.NetworkStats r6 = (android.net.NetworkStats) r6     // Catch:{ all -> 0x002f }
            r7 = 0
            android.net.NetworkStats r5 = android.net.NetworkStats.subtract(r0, r5, r7, r7, r6)     // Catch:{ all -> 0x002f }
            r3 = r5
            android.util.Pools$Pool<android.net.NetworkStats> r5 = r1.mNetworkStatsPool     // Catch:{ all -> 0x002f }
            android.net.NetworkStats r6 = r1.mLastModemNetworkStats     // Catch:{ all -> 0x002f }
            r5.release(r6)     // Catch:{ all -> 0x002f }
            r1.mLastModemNetworkStats = r0     // Catch:{ all -> 0x002f }
            goto L_0x0034
        L_0x002f:
            r0 = move-exception
            r45 = r2
            goto L_0x02df
        L_0x0034:
            monitor-exit(r4)     // Catch:{ all -> 0x02dc }
            monitor-enter(r46)
            boolean r0 = r1.mOnBatteryInternal     // Catch:{ all -> 0x02d5 }
            if (r0 != 0) goto L_0x0049
            if (r3 == 0) goto L_0x0047
            android.util.Pools$Pool<android.net.NetworkStats> r0 = r1.mNetworkStatsPool     // Catch:{ all -> 0x0042 }
            r0.release(r3)     // Catch:{ all -> 0x0042 }
            goto L_0x0047
        L_0x0042:
            r0 = move-exception
            r45 = r2
            goto L_0x02d8
        L_0x0047:
            monitor-exit(r46)     // Catch:{ all -> 0x0042 }
            return
        L_0x0049:
            r0 = 5
            r5 = 1
            if (r2 == 0) goto L_0x0135
            r1.mHasModemReporting = r5     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r6 = r1.mModemActivity     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r6 = r6.getIdleTimeCounter()     // Catch:{ all -> 0x0042 }
            int r7 = r2.getIdleTimeMillis()     // Catch:{ all -> 0x0042 }
            long r7 = (long) r7     // Catch:{ all -> 0x0042 }
            r6.addCountLocked(r7)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r6 = r1.mModemActivity     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r6 = r6.getSleepTimeCounter()     // Catch:{ all -> 0x0042 }
            int r7 = r2.getSleepTimeMillis()     // Catch:{ all -> 0x0042 }
            long r7 = (long) r7     // Catch:{ all -> 0x0042 }
            r6.addCountLocked(r7)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r6 = r1.mModemActivity     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r6 = r6.getRxTimeCounter()     // Catch:{ all -> 0x0042 }
            int r7 = r2.getRxTimeMillis()     // Catch:{ all -> 0x0042 }
            long r7 = (long) r7     // Catch:{ all -> 0x0042 }
            r6.addCountLocked(r7)     // Catch:{ all -> 0x0042 }
            r6 = 0
        L_0x007a:
            if (r6 >= r0) goto L_0x0091
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r7 = r1.mModemActivity     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r7 = r7.getTxTimeCounters()     // Catch:{ all -> 0x0042 }
            r7 = r7[r6]     // Catch:{ all -> 0x0042 }
            int[] r8 = r2.getTxTimeMillis()     // Catch:{ all -> 0x0042 }
            r8 = r8[r6]     // Catch:{ all -> 0x0042 }
            long r8 = (long) r8     // Catch:{ all -> 0x0042 }
            r7.addCountLocked(r8)     // Catch:{ all -> 0x0042 }
            int r6 = r6 + 1
            goto L_0x007a
        L_0x0091:
            com.android.internal.os.PowerProfile r6 = r1.mPowerProfile     // Catch:{ all -> 0x0042 }
            java.lang.String r7 = "modem.controller.voltage"
            double r6 = r6.getAveragePower(r7)     // Catch:{ all -> 0x0042 }
            r8 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r6 = r6 / r8
            r8 = 0
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 == 0) goto L_0x0135
            int r8 = r2.getSleepTimeMillis()     // Catch:{ all -> 0x0042 }
            double r8 = (double) r8     // Catch:{ all -> 0x0042 }
            com.android.internal.os.PowerProfile r10 = r1.mPowerProfile     // Catch:{ all -> 0x0042 }
            java.lang.String r11 = "modem.controller.sleep"
            double r10 = r10.getAveragePower(r11)     // Catch:{ all -> 0x0042 }
            double r8 = r8 * r10
            int r10 = r2.getIdleTimeMillis()     // Catch:{ all -> 0x0042 }
            double r10 = (double) r10     // Catch:{ all -> 0x0042 }
            com.android.internal.os.PowerProfile r12 = r1.mPowerProfile     // Catch:{ all -> 0x0042 }
            java.lang.String r13 = "modem.controller.idle"
            double r12 = r12.getAveragePower(r13)     // Catch:{ all -> 0x0042 }
            double r10 = r10 * r12
            double r8 = r8 + r10
            int r10 = r2.getRxTimeMillis()     // Catch:{ all -> 0x0042 }
            double r10 = (double) r10     // Catch:{ all -> 0x0042 }
            com.android.internal.os.PowerProfile r12 = r1.mPowerProfile     // Catch:{ all -> 0x0042 }
            java.lang.String r13 = "modem.controller.rx"
            double r12 = r12.getAveragePower(r13)     // Catch:{ all -> 0x0042 }
            double r10 = r10 * r12
            double r8 = r8 + r10
            int[] r10 = r2.getTxTimeMillis()     // Catch:{ all -> 0x0042 }
            r11 = r8
            r8 = 0
        L_0x00dc:
            int r9 = r10.length     // Catch:{ all -> 0x0042 }
            int r9 = java.lang.Math.min(r9, r0)     // Catch:{ all -> 0x0042 }
            if (r8 >= r9) goto L_0x00f4
            r9 = r10[r8]     // Catch:{ all -> 0x0042 }
            double r13 = (double) r9     // Catch:{ all -> 0x0042 }
            com.android.internal.os.PowerProfile r9 = r1.mPowerProfile     // Catch:{ all -> 0x0042 }
            java.lang.String r15 = "modem.controller.tx"
            double r15 = r9.getAveragePower(r15, r8)     // Catch:{ all -> 0x0042 }
            double r13 = r13 * r15
            double r11 = r11 + r13
            int r8 = r8 + 1
            goto L_0x00dc
        L_0x00f4:
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r8 = r1.mModemActivity     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r8 = r8.getPowerCounter()     // Catch:{ all -> 0x0042 }
            long r13 = (long) r11     // Catch:{ all -> 0x0042 }
            r8.addCountLocked(r13)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.RailStats r8 = r1.mTmpRailStats     // Catch:{ all -> 0x0042 }
            long r8 = r8.getCellularTotalEnergyUseduWs()     // Catch:{ all -> 0x0042 }
            double r8 = (double) r8     // Catch:{ all -> 0x0042 }
            double r8 = r8 / r6
            long r8 = (long) r8     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r13 = r1.mModemActivity     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r13 = r13.getMonitoredRailChargeConsumedMaMs()     // Catch:{ all -> 0x0042 }
            r13.addCountLocked(r8)     // Catch:{ all -> 0x0042 }
            android.os.BatteryStats$HistoryItem r13 = r1.mHistoryCur     // Catch:{ all -> 0x0042 }
            double r14 = r13.modemRailChargeMah     // Catch:{ all -> 0x0042 }
            r17 = r6
            double r5 = (double) r8     // Catch:{ all -> 0x0042 }
            r19 = 4704985352480227328(0x414b774000000000, double:3600000.0)
            double r5 = r5 / r19
            double r14 = r14 + r5
            r13.modemRailChargeMah = r14     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$Clocks r5 = r1.mClocks     // Catch:{ all -> 0x0042 }
            long r5 = r5.elapsedRealtime()     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$Clocks r7 = r1.mClocks     // Catch:{ all -> 0x0042 }
            long r13 = r7.uptimeMillis()     // Catch:{ all -> 0x0042 }
            r1.addHistoryRecordLocked(r5, r13)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.RailStats r5 = r1.mTmpRailStats     // Catch:{ all -> 0x0042 }
            r5.resetCellularTotalEnergyUsed()     // Catch:{ all -> 0x0042 }
        L_0x0135:
            com.android.internal.os.BatteryStatsImpl$Clocks r5 = r1.mClocks     // Catch:{ all -> 0x02d5 }
            long r5 = r5.elapsedRealtime()     // Catch:{ all -> 0x02d5 }
            com.android.internal.os.BatteryStatsImpl$StopwatchTimer r7 = r1.mMobileRadioActivePerAppTimer     // Catch:{ all -> 0x02d5 }
            r8 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 * r5
            long r7 = r7.getTimeSinceMarkLocked(r8)     // Catch:{ all -> 0x02d5 }
            com.android.internal.os.BatteryStatsImpl$StopwatchTimer r9 = r1.mMobileRadioActivePerAppTimer     // Catch:{ all -> 0x02d5 }
            r9.setMark(r5)     // Catch:{ all -> 0x02d5 }
            r9 = 0
            r11 = 0
            if (r3 == 0) goto L_0x02d1
            android.net.NetworkStats$Entry r13 = new android.net.NetworkStats$Entry     // Catch:{ all -> 0x02d5 }
            r13.<init>()     // Catch:{ all -> 0x02d5 }
            int r14 = r3.size()     // Catch:{ all -> 0x02d5 }
            r15 = r11
            r10 = r9
            r9 = 0
        L_0x015b:
            r17 = 0
            if (r9 >= r14) goto L_0x01fa
            android.net.NetworkStats$Entry r12 = r3.getValues(r9, r13)     // Catch:{ all -> 0x0042 }
            r13 = r12
            r21 = r5
            long r4 = r13.rxPackets     // Catch:{ all -> 0x0042 }
            int r4 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r4 != 0) goto L_0x0174
            long r4 = r13.txPackets     // Catch:{ all -> 0x0042 }
            int r4 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r4 != 0) goto L_0x0174
            goto L_0x01ec
        L_0x0174:
            long r4 = r13.rxPackets     // Catch:{ all -> 0x0042 }
            long r10 = r10 + r4
            long r4 = r13.txPackets     // Catch:{ all -> 0x0042 }
            long r15 = r15 + r4
            int r4 = r13.uid     // Catch:{ all -> 0x0042 }
            int r4 = r1.mapUid(r4)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$Uid r23 = r1.getUidStatsLocked(r4)     // Catch:{ all -> 0x0042 }
            r24 = 0
            long r4 = r13.rxBytes     // Catch:{ all -> 0x0042 }
            long r0 = r13.rxPackets     // Catch:{ all -> 0x01f3 }
            r25 = r4
            r27 = r0
            r23.noteNetworkActivityLocked(r24, r25, r27)     // Catch:{ all -> 0x01f3 }
            r30 = 1
            long r0 = r13.txBytes     // Catch:{ all -> 0x01f3 }
            long r4 = r13.txPackets     // Catch:{ all -> 0x01f3 }
            r29 = r23
            r31 = r0
            r33 = r4
            r29.noteNetworkActivityLocked(r30, r31, r33)     // Catch:{ all -> 0x01f3 }
            int r0 = r13.set     // Catch:{ all -> 0x01f3 }
            if (r0 != 0) goto L_0x01c2
            r30 = 6
            long r0 = r13.rxBytes     // Catch:{ all -> 0x01f3 }
            long r4 = r13.rxPackets     // Catch:{ all -> 0x01f3 }
            r29 = r23
            r31 = r0
            r33 = r4
            r29.noteNetworkActivityLocked(r30, r31, r33)     // Catch:{ all -> 0x01f3 }
            r32 = 7
            long r0 = r13.txBytes     // Catch:{ all -> 0x01f3 }
            long r4 = r13.txPackets     // Catch:{ all -> 0x01f3 }
            r31 = r23
            r33 = r0
            r35 = r4
            r31.noteNetworkActivityLocked(r32, r33, r35)     // Catch:{ all -> 0x01f3 }
        L_0x01c2:
            r1 = r46
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r0 = r1.mNetworkByteActivityCounters     // Catch:{ all -> 0x0042 }
            r4 = 0
            r0 = r0[r4]     // Catch:{ all -> 0x0042 }
            long r4 = r13.rxBytes     // Catch:{ all -> 0x0042 }
            r0.addCountLocked(r4)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r0 = r1.mNetworkByteActivityCounters     // Catch:{ all -> 0x0042 }
            r4 = 1
            r0 = r0[r4]     // Catch:{ all -> 0x0042 }
            long r4 = r13.txBytes     // Catch:{ all -> 0x0042 }
            r0.addCountLocked(r4)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r0 = r1.mNetworkPacketActivityCounters     // Catch:{ all -> 0x0042 }
            r4 = 0
            r0 = r0[r4]     // Catch:{ all -> 0x0042 }
            long r5 = r13.rxPackets     // Catch:{ all -> 0x0042 }
            r0.addCountLocked(r5)     // Catch:{ all -> 0x0042 }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r0 = r1.mNetworkPacketActivityCounters     // Catch:{ all -> 0x0042 }
            r5 = 1
            r0 = r0[r5]     // Catch:{ all -> 0x0042 }
            long r4 = r13.txPackets     // Catch:{ all -> 0x0042 }
            r0.addCountLocked(r4)     // Catch:{ all -> 0x0042 }
        L_0x01ec:
            int r9 = r9 + 1
            r5 = r21
            r0 = 5
            goto L_0x015b
        L_0x01f3:
            r0 = move-exception
            r45 = r2
        L_0x01f6:
            r1 = r46
            goto L_0x02d8
        L_0x01fa:
            r21 = r5
            long r4 = r10 + r15
            int r0 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x02af
            r0 = 0
        L_0x0203:
            if (r0 >= r14) goto L_0x02af
            android.net.NetworkStats$Entry r6 = r3.getValues(r0, r13)     // Catch:{ all -> 0x02a9 }
            r13 = r6
            r37 = r14
            r38 = r15
            long r14 = r13.rxPackets     // Catch:{ all -> 0x02a9 }
            int r6 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r6 != 0) goto L_0x0221
            long r14 = r13.txPackets     // Catch:{ all -> 0x0042 }
            int r6 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r6 != 0) goto L_0x0221
            r40 = r0
            r45 = r2
            goto L_0x029d
        L_0x0221:
            int r6 = r13.uid     // Catch:{ all -> 0x02a9 }
            int r6 = r1.mapUid(r6)     // Catch:{ all -> 0x02a9 }
            com.android.internal.os.BatteryStatsImpl$Uid r6 = r1.getUidStatsLocked(r6)     // Catch:{ all -> 0x02a9 }
            long r14 = r13.rxPackets     // Catch:{ all -> 0x02a9 }
            r40 = r0
            long r0 = r13.txPackets     // Catch:{ all -> 0x02a9 }
            long r14 = r14 + r0
            long r0 = r7 * r14
            long r0 = r0 / r4
            r6.noteMobileRadioActiveTimeLocked(r0)     // Catch:{ all -> 0x02a9 }
            long r7 = r7 - r0
            long r4 = r4 - r14
            if (r2 == 0) goto L_0x0297
            com.android.internal.os.BatteryStatsImpl$ControllerActivityCounterImpl r9 = r6.getOrCreateModemControllerActivityLocked()     // Catch:{ all -> 0x02a9 }
            int r12 = (r10 > r17 ? 1 : (r10 == r17 ? 0 : -1))
            if (r12 <= 0) goto L_0x0263
            r41 = r0
            long r0 = r13.rxPackets     // Catch:{ all -> 0x01f3 }
            int r0 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x0260
            long r0 = r13.rxPackets     // Catch:{ all -> 0x01f3 }
            int r12 = r2.getRxTimeMillis()     // Catch:{ all -> 0x01f3 }
            r43 = r4
            long r4 = (long) r12     // Catch:{ all -> 0x01f3 }
            long r0 = r0 * r4
            long r0 = r0 / r10
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r4 = r9.getRxTimeCounter()     // Catch:{ all -> 0x01f3 }
            r4.addCountLocked(r0)     // Catch:{ all -> 0x01f3 }
            goto L_0x0267
        L_0x0260:
            r43 = r4
            goto L_0x0267
        L_0x0263:
            r41 = r0
            r43 = r4
        L_0x0267:
            int r0 = (r38 > r17 ? 1 : (r38 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x0294
            long r0 = r13.txPackets     // Catch:{ all -> 0x02a9 }
            int r0 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x0294
            r0 = 0
        L_0x0272:
            r1 = 5
            if (r0 >= r1) goto L_0x0294
            long r4 = r13.txPackets     // Catch:{ all -> 0x02a9 }
            int[] r12 = r2.getTxTimeMillis()     // Catch:{ all -> 0x02a9 }
            r12 = r12[r0]     // Catch:{ all -> 0x02a9 }
            r45 = r2
            long r1 = (long) r12
            long r4 = r4 * r1
            long r1 = r4 / r38
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter[] r4 = r9.getTxTimeCounters()     // Catch:{ all -> 0x0291 }
            r4 = r4[r0]     // Catch:{ all -> 0x0291 }
            r4.addCountLocked(r1)     // Catch:{ all -> 0x0291 }
            int r0 = r0 + 1
            r2 = r45
            goto L_0x0272
        L_0x0291:
            r0 = move-exception
            goto L_0x01f6
        L_0x0294:
            r45 = r2
            goto L_0x029b
        L_0x0297:
            r45 = r2
            r43 = r4
        L_0x029b:
            r4 = r43
        L_0x029d:
            int r0 = r40 + 1
            r14 = r37
            r15 = r38
            r2 = r45
            r1 = r46
            goto L_0x0203
        L_0x02a9:
            r0 = move-exception
            r45 = r2
            r1 = r46
            goto L_0x02d8
        L_0x02af:
            r45 = r2
            r37 = r14
            r38 = r15
            int r0 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x02c8
            r1 = r46
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r0 = r1.mMobileRadioActiveUnknownTime     // Catch:{ all -> 0x02da }
            r0.addCountLocked(r7)     // Catch:{ all -> 0x02da }
            com.android.internal.os.BatteryStatsImpl$LongSamplingCounter r0 = r1.mMobileRadioActiveUnknownCount     // Catch:{ all -> 0x02da }
            r14 = 1
            r0.addCountLocked(r14)     // Catch:{ all -> 0x02da }
            goto L_0x02ca
        L_0x02c8:
            r1 = r46
        L_0x02ca:
            android.util.Pools$Pool<android.net.NetworkStats> r0 = r1.mNetworkStatsPool     // Catch:{ all -> 0x02da }
            r0.release(r3)     // Catch:{ all -> 0x02da }
            r3 = 0
            goto L_0x02d3
        L_0x02d1:
            r45 = r2
        L_0x02d3:
            monitor-exit(r46)     // Catch:{ all -> 0x02da }
            return
        L_0x02d5:
            r0 = move-exception
            r45 = r2
        L_0x02d8:
            monitor-exit(r46)     // Catch:{ all -> 0x02da }
            throw r0
        L_0x02da:
            r0 = move-exception
            goto L_0x02d8
        L_0x02dc:
            r0 = move-exception
            r45 = r2
        L_0x02df:
            monitor-exit(r4)     // Catch:{ all -> 0x02e1 }
            throw r0
        L_0x02e1:
            r0 = move-exception
            goto L_0x02df
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.updateMobileRadioState(android.telephony.ModemActivityInfo):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0043, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0059, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void addModemTxPowerToHistory(android.telephony.ModemActivityInfo r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            if (r11 != 0) goto L_0x0005
            monitor-exit(r10)
            return
        L_0x0005:
            int[] r0 = r11.getTxTimeMillis()     // Catch:{ all -> 0x005c }
            if (r0 == 0) goto L_0x005a
            int r1 = r0.length     // Catch:{ all -> 0x005c }
            r2 = 5
            if (r1 == r2) goto L_0x0010
            goto L_0x005a
        L_0x0010:
            com.android.internal.os.BatteryStatsImpl$Clocks r1 = r10.mClocks     // Catch:{ all -> 0x005c }
            long r1 = r1.elapsedRealtime()     // Catch:{ all -> 0x005c }
            com.android.internal.os.BatteryStatsImpl$Clocks r3 = r10.mClocks     // Catch:{ all -> 0x005c }
            long r3 = r3.uptimeMillis()     // Catch:{ all -> 0x005c }
            r5 = 0
            r6 = 1
            r7 = r5
            r5 = r6
        L_0x0020:
            int r8 = r0.length     // Catch:{ all -> 0x005c }
            if (r5 >= r8) goto L_0x002d
            r8 = r0[r5]     // Catch:{ all -> 0x005c }
            r9 = r0[r7]     // Catch:{ all -> 0x005c }
            if (r8 <= r9) goto L_0x002a
            r7 = r5
        L_0x002a:
            int r5 = r5 + 1
            goto L_0x0020
        L_0x002d:
            r5 = 4
            if (r7 != r5) goto L_0x0044
            boolean r5 = r10.mIsCellularTxPowerHigh     // Catch:{ all -> 0x005c }
            if (r5 != 0) goto L_0x0042
            android.os.BatteryStats$HistoryItem r5 = r10.mHistoryCur     // Catch:{ all -> 0x005c }
            int r8 = r5.states2     // Catch:{ all -> 0x005c }
            r9 = 524288(0x80000, float:7.34684E-40)
            r8 = r8 | r9
            r5.states2 = r8     // Catch:{ all -> 0x005c }
            r10.addHistoryRecordLocked(r1, r3)     // Catch:{ all -> 0x005c }
            r10.mIsCellularTxPowerHigh = r6     // Catch:{ all -> 0x005c }
        L_0x0042:
            monitor-exit(r10)
            return
        L_0x0044:
            boolean r5 = r10.mIsCellularTxPowerHigh     // Catch:{ all -> 0x005c }
            if (r5 == 0) goto L_0x0058
            android.os.BatteryStats$HistoryItem r5 = r10.mHistoryCur     // Catch:{ all -> 0x005c }
            int r6 = r5.states2     // Catch:{ all -> 0x005c }
            r8 = -524289(0xfffffffffff7ffff, float:NaN)
            r6 = r6 & r8
            r5.states2 = r6     // Catch:{ all -> 0x005c }
            r10.addHistoryRecordLocked(r1, r3)     // Catch:{ all -> 0x005c }
            r5 = 0
            r10.mIsCellularTxPowerHigh = r5     // Catch:{ all -> 0x005c }
        L_0x0058:
            monitor-exit(r10)
            return
        L_0x005a:
            monitor-exit(r10)
            return
        L_0x005c:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsImpl.addModemTxPowerToHistory(android.telephony.ModemActivityInfo):void");
    }

    private final class BluetoothActivityInfoCache {
        long energy;
        long idleTimeMs;
        long rxTimeMs;
        long txTimeMs;
        SparseLongArray uidRxBytes;
        SparseLongArray uidTxBytes;

        private BluetoothActivityInfoCache() {
            this.uidRxBytes = new SparseLongArray();
            this.uidTxBytes = new SparseLongArray();
        }

        /* access modifiers changed from: package-private */
        public void set(BluetoothActivityEnergyInfo info) {
            this.idleTimeMs = info.getControllerIdleTimeMillis();
            this.rxTimeMs = info.getControllerRxTimeMillis();
            this.txTimeMs = info.getControllerTxTimeMillis();
            this.energy = info.getControllerEnergyUsed();
            if (info.getUidTraffic() != null) {
                for (UidTraffic traffic : info.getUidTraffic()) {
                    this.uidRxBytes.put(traffic.getUid(), traffic.getRxBytes());
                    this.uidTxBytes.put(traffic.getUid(), traffic.getTxBytes());
                }
            }
        }
    }

    public void updateBluetoothStateLocked(BluetoothActivityEnergyInfo info) {
        long elapsedRealtimeMs;
        boolean normalizeScanRxTime;
        long idleTimeMs;
        BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo = info;
        if (bluetoothActivityEnergyInfo == null || !this.mOnBatteryInternal) {
            BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo2 = bluetoothActivityEnergyInfo;
            return;
        }
        this.mHasBluetoothReporting = true;
        long elapsedRealtimeMs2 = this.mClocks.elapsedRealtime();
        long rxTimeMs = info.getControllerRxTimeMillis() - this.mLastBluetoothActivityInfo.rxTimeMs;
        long txTimeMs = info.getControllerTxTimeMillis() - this.mLastBluetoothActivityInfo.txTimeMs;
        long idleTimeMs2 = info.getControllerIdleTimeMillis() - this.mLastBluetoothActivityInfo.idleTimeMs;
        int uidCount = this.mUidStats.size();
        long totalScanTimeMs = 0;
        for (int i = 0; i < uidCount; i++) {
            Uid u = this.mUidStats.valueAt(i);
            if (u.mBluetoothScanTimer != null) {
                totalScanTimeMs += u.mBluetoothScanTimer.getTimeSinceMarkLocked(elapsedRealtimeMs2 * 1000) / 1000;
            }
        }
        long totalScanTimeMs2 = totalScanTimeMs;
        boolean normalizeScanRxTime2 = totalScanTimeMs2 > rxTimeMs;
        boolean normalizeScanTxTime = totalScanTimeMs2 > txTimeMs;
        long leftOverRxTimeMs = rxTimeMs;
        long leftOverTxTimeMs = txTimeMs;
        int i2 = 0;
        while (i2 < uidCount) {
            int uidCount2 = uidCount;
            Uid u2 = this.mUidStats.valueAt(i2);
            if (u2.mBluetoothScanTimer == null) {
                normalizeScanRxTime = normalizeScanRxTime2;
                elapsedRealtimeMs = elapsedRealtimeMs2;
                idleTimeMs = idleTimeMs2;
            } else {
                idleTimeMs = idleTimeMs2;
                long scanTimeSinceMarkMs = u2.mBluetoothScanTimer.getTimeSinceMarkLocked(elapsedRealtimeMs2 * 1000) / 1000;
                if (scanTimeSinceMarkMs > 0) {
                    u2.mBluetoothScanTimer.setMark(elapsedRealtimeMs2);
                    long scanTimeRxSinceMarkMs = scanTimeSinceMarkMs;
                    long scanTimeTxSinceMarkMs = scanTimeSinceMarkMs;
                    if (normalizeScanRxTime2) {
                        scanTimeRxSinceMarkMs = (rxTimeMs * scanTimeRxSinceMarkMs) / totalScanTimeMs2;
                    }
                    normalizeScanRxTime = normalizeScanRxTime2;
                    long scanTimeRxSinceMarkMs2 = scanTimeRxSinceMarkMs;
                    if (normalizeScanTxTime) {
                        scanTimeTxSinceMarkMs = (txTimeMs * scanTimeTxSinceMarkMs) / totalScanTimeMs2;
                    }
                    elapsedRealtimeMs = elapsedRealtimeMs2;
                    long scanTimeTxSinceMarkMs2 = scanTimeTxSinceMarkMs;
                    ControllerActivityCounterImpl counter = u2.getOrCreateBluetoothControllerActivityLocked();
                    long j = scanTimeSinceMarkMs;
                    counter.getRxTimeCounter().addCountLocked(scanTimeRxSinceMarkMs2);
                    counter.getTxTimeCounters()[0].addCountLocked(scanTimeTxSinceMarkMs2);
                    leftOverRxTimeMs -= scanTimeRxSinceMarkMs2;
                    leftOverTxTimeMs -= scanTimeTxSinceMarkMs2;
                } else {
                    normalizeScanRxTime = normalizeScanRxTime2;
                    elapsedRealtimeMs = elapsedRealtimeMs2;
                }
            }
            i2++;
            uidCount = uidCount2;
            idleTimeMs2 = idleTimeMs;
            normalizeScanRxTime2 = normalizeScanRxTime;
            elapsedRealtimeMs2 = elapsedRealtimeMs;
            BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo3 = info;
        }
        long j2 = elapsedRealtimeMs2;
        long idleTimeMs3 = idleTimeMs2;
        int i3 = uidCount;
        long totalRxBytes = 0;
        UidTraffic[] uidTraffic = info.getUidTraffic();
        int numUids = uidTraffic != null ? uidTraffic.length : 0;
        long totalTxBytes = 0;
        int i4 = 0;
        while (i4 < numUids) {
            UidTraffic traffic = uidTraffic[i4];
            long txTimeMs2 = txTimeMs;
            long rxBytes = traffic.getRxBytes() - this.mLastBluetoothActivityInfo.uidRxBytes.get(traffic.getUid());
            long rxTimeMs2 = rxTimeMs;
            long txBytes = traffic.getTxBytes() - this.mLastBluetoothActivityInfo.uidTxBytes.get(traffic.getUid());
            this.mNetworkByteActivityCounters[4].addCountLocked(rxBytes);
            this.mNetworkByteActivityCounters[5].addCountLocked(txBytes);
            Uid uidStatsLocked = getUidStatsLocked(mapUid(traffic.getUid()));
            uidStatsLocked.noteNetworkActivityLocked(4, rxBytes, 0);
            uidStatsLocked.noteNetworkActivityLocked(5, txBytes, 0);
            totalRxBytes += rxBytes;
            totalTxBytes += txBytes;
            i4++;
            normalizeScanTxTime = normalizeScanTxTime;
            txTimeMs = txTimeMs2;
            rxTimeMs = rxTimeMs2;
        }
        long rxTimeMs3 = rxTimeMs;
        long txTimeMs3 = txTimeMs;
        boolean z = normalizeScanTxTime;
        if (!((totalTxBytes == 0 && totalRxBytes == 0) || (leftOverRxTimeMs == 0 && leftOverTxTimeMs == 0))) {
            for (int i5 = 0; i5 < numUids; i5++) {
                UidTraffic traffic2 = uidTraffic[i5];
                int uid = traffic2.getUid();
                long rxBytes2 = traffic2.getRxBytes() - this.mLastBluetoothActivityInfo.uidRxBytes.get(uid);
                long txBytes2 = traffic2.getTxBytes() - this.mLastBluetoothActivityInfo.uidTxBytes.get(uid);
                ControllerActivityCounterImpl counter2 = getUidStatsLocked(mapUid(uid)).getOrCreateBluetoothControllerActivityLocked();
                if (totalRxBytes <= 0 || rxBytes2 <= 0) {
                    long j3 = rxBytes2;
                } else {
                    int i6 = uid;
                    long j4 = rxBytes2;
                    counter2.getRxTimeCounter().addCountLocked((leftOverRxTimeMs * rxBytes2) / totalRxBytes);
                }
                if (totalTxBytes > 0 && txBytes2 > 0) {
                    counter2.getTxTimeCounters()[0].addCountLocked((leftOverTxTimeMs * txBytes2) / totalTxBytes);
                }
            }
        }
        this.mBluetoothActivity.getRxTimeCounter().addCountLocked(rxTimeMs3);
        this.mBluetoothActivity.getTxTimeCounters()[0].addCountLocked(txTimeMs3);
        long j5 = totalRxBytes;
        long idleTimeMs4 = idleTimeMs3;
        this.mBluetoothActivity.getIdleTimeCounter().addCountLocked(idleTimeMs4);
        double opVolt = this.mPowerProfile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE) / 1000.0d;
        if (opVolt != 0.0d) {
            long j6 = idleTimeMs4;
            this.mBluetoothActivity.getPowerCounter().addCountLocked((long) (((double) (info.getControllerEnergyUsed() - this.mLastBluetoothActivityInfo.energy)) / opVolt));
        }
        this.mLastBluetoothActivityInfo.set(info);
    }

    public void updateRpmStatsLocked() {
        if (this.mPlatformIdleStateCallback != null) {
            long now = SystemClock.elapsedRealtime();
            long j = 1000;
            if (now - this.mLastRpmStatsUpdateTimeMs >= 1000) {
                this.mPlatformIdleStateCallback.fillLowPowerStats(this.mTmpRpmStats);
                this.mLastRpmStatsUpdateTimeMs = now;
            }
            for (Map.Entry<String, RpmStats.PowerStatePlatformSleepState> pstate : this.mTmpRpmStats.mPlatformLowPowerStats.entrySet()) {
                String pName = pstate.getKey();
                getRpmTimerLocked(pName).update(pstate.getValue().mTimeMs * j, pstate.getValue().mCount);
                for (Map.Entry<String, RpmStats.PowerStateElement> voter : pstate.getValue().mVoters.entrySet()) {
                    getRpmTimerLocked(pName + "." + voter.getKey()).update(voter.getValue().mTimeMs * j, voter.getValue().mCount);
                    j = 1000;
                }
                j = 1000;
            }
            for (Map.Entry<String, RpmStats.PowerStateSubsystem> subsys : this.mTmpRpmStats.mSubsystemLowPowerStats.entrySet()) {
                String subsysName = subsys.getKey();
                for (Map.Entry<String, RpmStats.PowerStateElement> sstate : subsys.getValue().mStates.entrySet()) {
                    int count = sstate.getValue().mCount;
                    getRpmTimerLocked(subsysName + "." + sstate.getKey()).update(sstate.getValue().mTimeMs * 1000, count);
                }
            }
        }
    }

    public void updateRailStatsLocked() {
        if (this.mRailEnergyDataCallback != null && this.mTmpRailStats.isRailStatsAvailable()) {
            this.mRailEnergyDataCallback.fillRailDataStats(this.mTmpRailStats);
        }
    }

    public void updateKernelWakelocksLocked() {
        KernelWakelockStats wakelockStats = this.mKernelWakelockReader.readKernelWakelockStats(this.mTmpWakelockStats);
        if (wakelockStats == null) {
            Slog.w(TAG, "Couldn't get kernel wake lock stats");
            return;
        }
        for (Map.Entry<String, KernelWakelockStats.Entry> ent : wakelockStats.entrySet()) {
            String name = ent.getKey();
            KernelWakelockStats.Entry kws = ent.getValue();
            SamplingTimer kwlt = this.mKernelWakelockStats.get(name);
            if (kwlt == null) {
                kwlt = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase);
                this.mKernelWakelockStats.put(name, kwlt);
            }
            kwlt.update(kws.mTotalTime, kws.mCount);
            kwlt.setUpdateVersion(kws.mVersion);
        }
        int numWakelocksSetStale = 0;
        for (Map.Entry<String, SamplingTimer> ent2 : this.mKernelWakelockStats.entrySet()) {
            SamplingTimer st = ent2.getValue();
            if (st.getUpdateVersion() != wakelockStats.kernelWakelockVersion) {
                st.endSample();
                numWakelocksSetStale++;
            }
        }
        if (wakelockStats.isEmpty()) {
            Slog.wtf(TAG, "All kernel wakelocks had time of zero");
        }
        if (numWakelocksSetStale == this.mKernelWakelockStats.size()) {
            Slog.wtf(TAG, "All kernel wakelocks were set stale. new version=" + wakelockStats.kernelWakelockVersion);
        }
    }

    public void updateKernelMemoryBandwidthLocked() {
        SamplingTimer timer;
        this.mKernelMemoryBandwidthStats.updateStats();
        LongSparseLongArray bandwidthEntries = this.mKernelMemoryBandwidthStats.getBandwidthEntries();
        int bandwidthEntryCount = bandwidthEntries.size();
        for (int i = 0; i < bandwidthEntryCount; i++) {
            int indexOfKey = this.mKernelMemoryStats.indexOfKey(bandwidthEntries.keyAt(i));
            int index = indexOfKey;
            if (indexOfKey >= 0) {
                timer = this.mKernelMemoryStats.valueAt(index);
            } else {
                timer = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
                this.mKernelMemoryStats.put(bandwidthEntries.keyAt(i), timer);
            }
            timer.update(bandwidthEntries.valueAt(i), 1);
        }
    }

    public boolean isOnBatteryLocked() {
        return this.mOnBatteryTimeBase.isRunning();
    }

    public boolean isOnBatteryScreenOffLocked() {
        return this.mOnBatteryScreenOffTimeBase.isRunning();
    }

    @GuardedBy({"this"})
    public void updateCpuTimeLocked(boolean onBattery, boolean onBatteryScreenOff) {
        if (this.mPowerProfile != null) {
            if (this.mCpuFreqs == null) {
                this.mCpuFreqs = this.mCpuUidFreqTimeReader.readFreqs(this.mPowerProfile);
            }
            ArrayList<StopwatchTimer> partialTimersToConsider = null;
            if (onBatteryScreenOff) {
                partialTimersToConsider = new ArrayList<>();
                for (int i = this.mPartialTimers.size() - 1; i >= 0; i--) {
                    StopwatchTimer timer = this.mPartialTimers.get(i);
                    if (!(!timer.mInList || timer.mUid == null || timer.mUid.mUid == 1000)) {
                        partialTimersToConsider.add(timer);
                    }
                }
            }
            markPartialTimersAsEligible();
            SparseLongArray updatedUids = null;
            if (!onBattery) {
                this.mCpuUidUserSysTimeReader.readDelta((KernelCpuUidTimeReader.Callback) null);
                this.mCpuUidFreqTimeReader.readDelta((KernelCpuUidTimeReader.Callback) null);
                this.mNumAllUidCpuTimeReads += 2;
                if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                    this.mCpuUidActiveTimeReader.readDelta((KernelCpuUidTimeReader.Callback) null);
                    this.mCpuUidClusterTimeReader.readDelta((KernelCpuUidTimeReader.Callback) null);
                    this.mNumAllUidCpuTimeReads += 2;
                }
                for (int cluster = this.mKernelCpuSpeedReaders.length - 1; cluster >= 0; cluster--) {
                    this.mKernelCpuSpeedReaders[cluster].readDelta();
                }
                return;
            }
            this.mUserInfoProvider.refreshUserIds();
            if (!this.mCpuUidFreqTimeReader.perClusterTimesAvailable()) {
                updatedUids = new SparseLongArray();
            }
            readKernelUidCpuTimesLocked(partialTimersToConsider, updatedUids, onBattery);
            if (updatedUids != null) {
                updateClusterSpeedTimes(updatedUids, onBattery);
            }
            readKernelUidCpuFreqTimesLocked(partialTimersToConsider, onBattery, onBatteryScreenOff);
            this.mNumAllUidCpuTimeReads += 2;
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                readKernelUidCpuActiveTimesLocked(onBattery);
                readKernelUidCpuClusterTimesLocked(onBattery);
                this.mNumAllUidCpuTimeReads += 2;
            }
        }
    }

    @VisibleForTesting
    public void markPartialTimersAsEligible() {
        int i;
        if (ArrayUtils.referenceEquals(this.mPartialTimers, this.mLastPartialTimers)) {
            for (int i2 = this.mPartialTimers.size() - 1; i2 >= 0; i2--) {
                this.mPartialTimers.get(i2).mInList = true;
            }
            return;
        }
        int i3 = this.mLastPartialTimers.size() - 1;
        while (true) {
            if (i3 < 0) {
                break;
            }
            this.mLastPartialTimers.get(i3).mInList = false;
            i3--;
        }
        this.mLastPartialTimers.clear();
        int numPartialTimers = this.mPartialTimers.size();
        for (i = 0; i < numPartialTimers; i++) {
            StopwatchTimer timer = this.mPartialTimers.get(i);
            timer.mInList = true;
            this.mLastPartialTimers.add(timer);
        }
    }

    @VisibleForTesting
    public void updateClusterSpeedTimes(SparseLongArray updatedUids, boolean onBattery) {
        BatteryStatsImpl batteryStatsImpl = this;
        SparseLongArray sparseLongArray = updatedUids;
        long[][] clusterSpeedTimesMs = new long[batteryStatsImpl.mKernelCpuSpeedReaders.length][];
        long totalCpuClustersTimeMs = 0;
        for (int cluster = 0; cluster < batteryStatsImpl.mKernelCpuSpeedReaders.length; cluster++) {
            clusterSpeedTimesMs[cluster] = batteryStatsImpl.mKernelCpuSpeedReaders[cluster].readDelta();
            if (clusterSpeedTimesMs[cluster] != null) {
                for (int speed = clusterSpeedTimesMs[cluster].length - 1; speed >= 0; speed--) {
                    totalCpuClustersTimeMs += clusterSpeedTimesMs[cluster][speed];
                }
            }
        }
        if (totalCpuClustersTimeMs != 0) {
            int updatedUidsCount = updatedUids.size();
            int i = 0;
            while (i < updatedUidsCount) {
                Uid u = batteryStatsImpl.getUidStatsLocked(sparseLongArray.keyAt(i));
                long appCpuTimeUs = sparseLongArray.valueAt(i);
                int numClusters = batteryStatsImpl.mPowerProfile.getNumCpuClusters();
                if (u.mCpuClusterSpeedTimesUs == null || u.mCpuClusterSpeedTimesUs.length != numClusters) {
                    u.mCpuClusterSpeedTimesUs = new LongSamplingCounter[numClusters][];
                }
                int cluster2 = 0;
                while (cluster2 < clusterSpeedTimesMs.length) {
                    int speedsInCluster = clusterSpeedTimesMs[cluster2].length;
                    if (u.mCpuClusterSpeedTimesUs[cluster2] == null || speedsInCluster != u.mCpuClusterSpeedTimesUs[cluster2].length) {
                        u.mCpuClusterSpeedTimesUs[cluster2] = new LongSamplingCounter[speedsInCluster];
                    }
                    LongSamplingCounter[] cpuSpeeds = u.mCpuClusterSpeedTimesUs[cluster2];
                    int speed2 = 0;
                    while (speed2 < speedsInCluster) {
                        if (cpuSpeeds[speed2] == null) {
                            cpuSpeeds[speed2] = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
                        }
                        cpuSpeeds[speed2].addCountLocked((clusterSpeedTimesMs[cluster2][speed2] * appCpuTimeUs) / totalCpuClustersTimeMs, onBattery);
                        speed2++;
                        clusterSpeedTimesMs = clusterSpeedTimesMs;
                        batteryStatsImpl = this;
                        SparseLongArray sparseLongArray2 = updatedUids;
                    }
                    boolean z = onBattery;
                    long[][] jArr = clusterSpeedTimesMs;
                    cluster2++;
                    batteryStatsImpl = this;
                    SparseLongArray sparseLongArray3 = updatedUids;
                }
                boolean z2 = onBattery;
                long[][] jArr2 = clusterSpeedTimesMs;
                i++;
                batteryStatsImpl = this;
                sparseLongArray = updatedUids;
            }
        }
        boolean z3 = onBattery;
        long[][] jArr3 = clusterSpeedTimesMs;
    }

    @VisibleForTesting
    public void readKernelUidCpuTimesLocked(ArrayList<StopwatchTimer> partialTimers, SparseLongArray updatedUids, boolean onBattery) {
        ArrayList<StopwatchTimer> arrayList = partialTimers;
        SparseLongArray sparseLongArray = updatedUids;
        boolean z = onBattery;
        this.mTempTotalCpuSystemTimeUs = 0;
        this.mTempTotalCpuUserTimeUs = 0;
        int numWakelocks = arrayList == null ? 0 : partialTimers.size();
        long startTimeMs = this.mClocks.uptimeMillis();
        this.mCpuUidUserSysTimeReader.readDelta(new KernelCpuUidTimeReader.Callback(numWakelocks, z, sparseLongArray) {
            private final /* synthetic */ int f$1;
            private final /* synthetic */ boolean f$2;
            private final /* synthetic */ SparseLongArray f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void onUidCpuTime(int i, Object obj) {
                BatteryStatsImpl.lambda$readKernelUidCpuTimesLocked$0(BatteryStatsImpl.this, this.f$1, this.f$2, this.f$3, i, (long[]) obj);
            }
        });
        long elapsedTimeMs = this.mClocks.uptimeMillis() - startTimeMs;
        if (elapsedTimeMs >= 100) {
            Slog.d(TAG, "Reading cpu stats took " + elapsedTimeMs + "ms");
        }
        if (numWakelocks > 0) {
            this.mTempTotalCpuUserTimeUs = (this.mTempTotalCpuUserTimeUs * 50) / 100;
            this.mTempTotalCpuSystemTimeUs = (this.mTempTotalCpuSystemTimeUs * 50) / 100;
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= numWakelocks) {
                    break;
                }
                StopwatchTimer timer = arrayList.get(i2);
                int userTimeUs = (int) (this.mTempTotalCpuUserTimeUs / ((long) (numWakelocks - i2)));
                int numWakelocks2 = numWakelocks;
                long startTimeMs2 = startTimeMs;
                int systemTimeUs = (int) (this.mTempTotalCpuSystemTimeUs / ((long) (numWakelocks - i2)));
                timer.mUid.mUserCpuTime.addCountLocked((long) userTimeUs, z);
                timer.mUid.mSystemCpuTime.addCountLocked((long) systemTimeUs, z);
                if (sparseLongArray != null) {
                    int uid = timer.mUid.getUid();
                    sparseLongArray.put(uid, sparseLongArray.get(uid, 0) + ((long) userTimeUs) + ((long) systemTimeUs));
                }
                timer.mUid.getProcessStatsLocked("*wakelock*").addCpuTimeLocked(userTimeUs / 1000, systemTimeUs / 1000, z);
                this.mTempTotalCpuUserTimeUs -= (long) userTimeUs;
                this.mTempTotalCpuSystemTimeUs -= (long) systemTimeUs;
                i = i2 + 1;
                numWakelocks = numWakelocks2;
                startTimeMs = startTimeMs2;
            }
        }
        long j = startTimeMs;
    }

    public static /* synthetic */ void lambda$readKernelUidCpuTimesLocked$0(BatteryStatsImpl batteryStatsImpl, int numWakelocks, boolean onBattery, SparseLongArray updatedUids, int uid, long[] timesUs) {
        BatteryStatsImpl batteryStatsImpl2 = batteryStatsImpl;
        boolean z = onBattery;
        SparseLongArray sparseLongArray = updatedUids;
        long userTimeUs = timesUs[0];
        long systemTimeUs = timesUs[1];
        int uid2 = batteryStatsImpl2.mapUid(uid);
        if (Process.isIsolated(uid2)) {
            batteryStatsImpl2.mCpuUidUserSysTimeReader.removeUid(uid2);
            Slog.d(TAG, "Got readings for an isolated uid with no mapping: " + uid2);
        } else if (!batteryStatsImpl2.mUserInfoProvider.exists(UserHandle.getUserId(uid2))) {
            Slog.d(TAG, "Got readings for an invalid user's uid " + uid2);
            batteryStatsImpl2.mCpuUidUserSysTimeReader.removeUid(uid2);
        } else {
            Uid u = batteryStatsImpl2.getUidStatsLocked(uid2);
            batteryStatsImpl2.mTempTotalCpuUserTimeUs += userTimeUs;
            batteryStatsImpl2.mTempTotalCpuSystemTimeUs += systemTimeUs;
            StringBuilder sb = null;
            if (numWakelocks > 0) {
                userTimeUs = (userTimeUs * 50) / 100;
                systemTimeUs = (50 * systemTimeUs) / 100;
            }
            if (sb != null) {
                sb.append("  adding to uid=");
                sb.append(u.mUid);
                sb.append(": u=");
                TimeUtils.formatDuration(userTimeUs / 1000, sb);
                sb.append(" s=");
                TimeUtils.formatDuration(systemTimeUs / 1000, sb);
                Slog.d(TAG, sb.toString());
            }
            u.mUserCpuTime.addCountLocked(userTimeUs, z);
            u.mSystemCpuTime.addCountLocked(systemTimeUs, z);
            if (sparseLongArray != null) {
                sparseLongArray.put(u.getUid(), userTimeUs + systemTimeUs);
            }
        }
    }

    @VisibleForTesting
    public void readKernelUidCpuFreqTimesLocked(ArrayList<StopwatchTimer> partialTimers, boolean onBattery, boolean onBatteryScreenOff) {
        long elapsedTimeMs;
        ArrayList<StopwatchTimer> arrayList = partialTimers;
        boolean perClusterTimesAvailable = this.mCpuUidFreqTimeReader.perClusterTimesAvailable();
        int numWakelocks = arrayList == null ? 0 : partialTimers.size();
        int numClusters = this.mPowerProfile.getNumCpuClusters();
        this.mWakeLockAllocationsUs = null;
        long startTimeMs = this.mClocks.uptimeMillis();
        KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader kernelCpuUidFreqTimeReader = this.mCpuUidFreqTimeReader;
        $$Lambda$BatteryStatsImpl$BTmZhQb712ePnuJTxvMe7PYwQ r10 = r0;
        $$Lambda$BatteryStatsImpl$BTmZhQb712ePnuJTxvMe7PYwQ r0 = new KernelCpuUidTimeReader.Callback(onBattery, onBatteryScreenOff, perClusterTimesAvailable, numClusters, numWakelocks) {
            private final /* synthetic */ boolean f$1;
            private final /* synthetic */ boolean f$2;
            private final /* synthetic */ boolean f$3;
            private final /* synthetic */ int f$4;
            private final /* synthetic */ int f$5;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
                this.f$5 = r6;
            }

            public final void onUidCpuTime(int i, Object obj) {
                BatteryStatsImpl.lambda$readKernelUidCpuFreqTimesLocked$1(BatteryStatsImpl.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, i, (long[]) obj);
            }
        };
        kernelCpuUidFreqTimeReader.readDelta(r10);
        long elapsedTimeMs2 = this.mClocks.uptimeMillis() - startTimeMs;
        if (elapsedTimeMs2 >= 100) {
            Slog.d(TAG, "Reading cpu freq times took " + elapsedTimeMs2 + "ms");
        }
        if (this.mWakeLockAllocationsUs != null) {
            for (int i = 0; i < numWakelocks; i++) {
                Uid u = arrayList.get(i).mUid;
                if (u.mCpuClusterSpeedTimesUs == null || u.mCpuClusterSpeedTimesUs.length != numClusters) {
                    detachIfNotNull((T[][]) u.mCpuClusterSpeedTimesUs);
                    u.mCpuClusterSpeedTimesUs = new LongSamplingCounter[numClusters][];
                }
                int cluster = 0;
                while (cluster < numClusters) {
                    int speedsInCluster = this.mPowerProfile.getNumSpeedStepsInCpuCluster(cluster);
                    if (u.mCpuClusterSpeedTimesUs[cluster] == null || u.mCpuClusterSpeedTimesUs[cluster].length != speedsInCluster) {
                        detachIfNotNull((T[]) u.mCpuClusterSpeedTimesUs[cluster]);
                        u.mCpuClusterSpeedTimesUs[cluster] = new LongSamplingCounter[speedsInCluster];
                    }
                    LongSamplingCounter[] cpuTimeUs = u.mCpuClusterSpeedTimesUs[cluster];
                    int speed = 0;
                    while (speed < speedsInCluster) {
                        if (cpuTimeUs[speed] == null) {
                            elapsedTimeMs = elapsedTimeMs2;
                            cpuTimeUs[speed] = new LongSamplingCounter(this.mOnBatteryTimeBase);
                        } else {
                            elapsedTimeMs = elapsedTimeMs2;
                        }
                        long allocationUs = this.mWakeLockAllocationsUs[cluster][speed] / ((long) (numWakelocks - i));
                        cpuTimeUs[speed].addCountLocked(allocationUs, onBattery);
                        long[] jArr = this.mWakeLockAllocationsUs[cluster];
                        jArr[speed] = jArr[speed] - allocationUs;
                        speed++;
                        elapsedTimeMs2 = elapsedTimeMs;
                        u = u;
                    }
                    Uid u2 = u;
                    boolean z = onBattery;
                    cluster++;
                    u = u2;
                }
                boolean z2 = onBattery;
                long j = elapsedTimeMs2;
            }
        }
        boolean z3 = onBattery;
        long j2 = elapsedTimeMs2;
    }

    public static /* synthetic */ void lambda$readKernelUidCpuFreqTimesLocked$1(BatteryStatsImpl batteryStatsImpl, boolean onBattery, boolean onBatteryScreenOff, boolean perClusterTimesAvailable, int numClusters, int numWakelocks, int uid, long[] cpuFreqTimeMs) {
        long appAllocationUs;
        BatteryStatsImpl batteryStatsImpl2 = batteryStatsImpl;
        boolean z = onBattery;
        int i = numClusters;
        long[] jArr = cpuFreqTimeMs;
        int uid2 = batteryStatsImpl2.mapUid(uid);
        if (Process.isIsolated(uid2)) {
            batteryStatsImpl2.mCpuUidFreqTimeReader.removeUid(uid2);
            Slog.d(TAG, "Got freq readings for an isolated uid with no mapping: " + uid2);
        } else if (!batteryStatsImpl2.mUserInfoProvider.exists(UserHandle.getUserId(uid2))) {
            Slog.d(TAG, "Got freq readings for an invalid user's uid " + uid2);
            batteryStatsImpl2.mCpuUidFreqTimeReader.removeUid(uid2);
        } else {
            Uid u = batteryStatsImpl2.getUidStatsLocked(uid2);
            if (u.mCpuFreqTimeMs == null || u.mCpuFreqTimeMs.getSize() != jArr.length) {
                detachIfNotNull(u.mCpuFreqTimeMs);
                u.mCpuFreqTimeMs = new LongSamplingCounterArray(batteryStatsImpl2.mOnBatteryTimeBase);
            }
            u.mCpuFreqTimeMs.addCountLocked(jArr, z);
            if (u.mScreenOffCpuFreqTimeMs == null || u.mScreenOffCpuFreqTimeMs.getSize() != jArr.length) {
                detachIfNotNull(u.mScreenOffCpuFreqTimeMs);
                u.mScreenOffCpuFreqTimeMs = new LongSamplingCounterArray(batteryStatsImpl2.mOnBatteryScreenOffTimeBase);
            }
            u.mScreenOffCpuFreqTimeMs.addCountLocked(jArr, onBatteryScreenOff);
            if (perClusterTimesAvailable) {
                if (u.mCpuClusterSpeedTimesUs == null || u.mCpuClusterSpeedTimesUs.length != i) {
                    detachIfNotNull((T[][]) u.mCpuClusterSpeedTimesUs);
                    u.mCpuClusterSpeedTimesUs = new LongSamplingCounter[i][];
                }
                if (numWakelocks > 0 && batteryStatsImpl2.mWakeLockAllocationsUs == null) {
                    batteryStatsImpl2.mWakeLockAllocationsUs = new long[i][];
                }
                int freqIndex = 0;
                int cluster = 0;
                while (cluster < i) {
                    int speedsInCluster = batteryStatsImpl2.mPowerProfile.getNumSpeedStepsInCpuCluster(cluster);
                    if (u.mCpuClusterSpeedTimesUs[cluster] == null || u.mCpuClusterSpeedTimesUs[cluster].length != speedsInCluster) {
                        detachIfNotNull((T[]) u.mCpuClusterSpeedTimesUs[cluster]);
                        u.mCpuClusterSpeedTimesUs[cluster] = new LongSamplingCounter[speedsInCluster];
                    }
                    if (numWakelocks > 0 && batteryStatsImpl2.mWakeLockAllocationsUs[cluster] == null) {
                        batteryStatsImpl2.mWakeLockAllocationsUs[cluster] = new long[speedsInCluster];
                    }
                    LongSamplingCounter[] cpuTimesUs = u.mCpuClusterSpeedTimesUs[cluster];
                    int freqIndex2 = freqIndex;
                    int speed = 0;
                    while (speed < speedsInCluster) {
                        if (cpuTimesUs[speed] == null) {
                            cpuTimesUs[speed] = new LongSamplingCounter(batteryStatsImpl2.mOnBatteryTimeBase);
                        }
                        if (batteryStatsImpl2.mWakeLockAllocationsUs != null) {
                            appAllocationUs = ((jArr[freqIndex2] * 1000) * 50) / 100;
                            long[] jArr2 = batteryStatsImpl2.mWakeLockAllocationsUs[cluster];
                            jArr2[speed] = jArr2[speed] + ((jArr[freqIndex2] * 1000) - appAllocationUs);
                        } else {
                            appAllocationUs = jArr[freqIndex2] * 1000;
                        }
                        cpuTimesUs[speed].addCountLocked(appAllocationUs, z);
                        freqIndex2++;
                        speed++;
                        int i2 = numClusters;
                    }
                    cluster++;
                    freqIndex = freqIndex2;
                    i = numClusters;
                }
            }
        }
    }

    @VisibleForTesting
    public void readKernelUidCpuActiveTimesLocked(boolean onBattery) {
        long startTimeMs = this.mClocks.uptimeMillis();
        this.mCpuUidActiveTimeReader.readDelta(new KernelCpuUidTimeReader.Callback(onBattery) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void onUidCpuTime(int i, Object obj) {
                BatteryStatsImpl.lambda$readKernelUidCpuActiveTimesLocked$2(BatteryStatsImpl.this, this.f$1, i, (Long) obj);
            }
        });
        long elapsedTimeMs = this.mClocks.uptimeMillis() - startTimeMs;
        if (elapsedTimeMs >= 100) {
            Slog.d(TAG, "Reading cpu active times took " + elapsedTimeMs + "ms");
        }
    }

    public static /* synthetic */ void lambda$readKernelUidCpuActiveTimesLocked$2(BatteryStatsImpl batteryStatsImpl, boolean onBattery, int uid, Long cpuActiveTimesMs) {
        int uid2 = batteryStatsImpl.mapUid(uid);
        if (Process.isIsolated(uid2)) {
            batteryStatsImpl.mCpuUidActiveTimeReader.removeUid(uid2);
            Slog.w(TAG, "Got active times for an isolated uid with no mapping: " + uid2);
        } else if (!batteryStatsImpl.mUserInfoProvider.exists(UserHandle.getUserId(uid2))) {
            Slog.w(TAG, "Got active times for an invalid user's uid " + uid2);
            batteryStatsImpl.mCpuUidActiveTimeReader.removeUid(uid2);
        } else {
            batteryStatsImpl.getUidStatsLocked(uid2).mCpuActiveTimeMs.addCountLocked(cpuActiveTimesMs.longValue(), onBattery);
        }
    }

    @VisibleForTesting
    public void readKernelUidCpuClusterTimesLocked(boolean onBattery) {
        long startTimeMs = this.mClocks.uptimeMillis();
        this.mCpuUidClusterTimeReader.readDelta(new KernelCpuUidTimeReader.Callback(onBattery) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void onUidCpuTime(int i, Object obj) {
                BatteryStatsImpl.lambda$readKernelUidCpuClusterTimesLocked$3(BatteryStatsImpl.this, this.f$1, i, (long[]) obj);
            }
        });
        long elapsedTimeMs = this.mClocks.uptimeMillis() - startTimeMs;
        if (elapsedTimeMs >= 100) {
            Slog.d(TAG, "Reading cpu cluster times took " + elapsedTimeMs + "ms");
        }
    }

    public static /* synthetic */ void lambda$readKernelUidCpuClusterTimesLocked$3(BatteryStatsImpl batteryStatsImpl, boolean onBattery, int uid, long[] cpuClusterTimesMs) {
        int uid2 = batteryStatsImpl.mapUid(uid);
        if (Process.isIsolated(uid2)) {
            batteryStatsImpl.mCpuUidClusterTimeReader.removeUid(uid2);
            Slog.w(TAG, "Got cluster times for an isolated uid with no mapping: " + uid2);
        } else if (!batteryStatsImpl.mUserInfoProvider.exists(UserHandle.getUserId(uid2))) {
            Slog.w(TAG, "Got cluster times for an invalid user's uid " + uid2);
            batteryStatsImpl.mCpuUidClusterTimeReader.removeUid(uid2);
        } else {
            batteryStatsImpl.getUidStatsLocked(uid2).mCpuClusterTimesMs.addCountLocked(cpuClusterTimesMs, onBattery);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean setChargingLocked(boolean charging) {
        this.mHandler.removeCallbacks(this.mDeferSetCharging);
        if (this.mCharging == charging) {
            return false;
        }
        this.mCharging = charging;
        if (charging) {
            this.mHistoryCur.states2 |= 16777216;
        } else {
            this.mHistoryCur.states2 &= -16777217;
        }
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    /* access modifiers changed from: protected */
    @GuardedBy({"this"})
    public void setOnBatteryLocked(long mSecRealtime, long mSecUptime, boolean onBattery, int oldStatus, int level, int chargeUAh) {
        boolean reset;
        int i;
        boolean z = onBattery;
        int i2 = oldStatus;
        int i3 = level;
        int i4 = chargeUAh;
        boolean doWrite = false;
        Message m = this.mHandler.obtainMessage(2);
        m.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(m);
        long uptime = mSecUptime * 1000;
        long realtime = mSecRealtime * 1000;
        int screenState = this.mScreenState;
        if (z) {
            boolean reset2 = false;
            if (!this.mNoAutoReset) {
                if (i2 == 5 || i3 >= 90 || (this.mDischargeCurrentLevel < 20 && i3 >= 80)) {
                    Slog.i(TAG, "Resetting battery stats: level=" + i3 + " status=" + i2 + " dischargeLevel=" + this.mDischargeCurrentLevel + " lowAmount=" + getLowDischargeAmountSinceCharge() + " highAmount=" + getHighDischargeAmountSinceCharge());
                    if (getLowDischargeAmountSinceCharge() >= 20) {
                        long startTime = SystemClock.uptimeMillis();
                        final Parcel parcel = Parcel.obtain();
                        writeSummaryToParcel(parcel, true);
                        final long initialTime = SystemClock.uptimeMillis() - startTime;
                        long j = startTime;
                        BackgroundThread.getHandler().post(new Runnable() {
                            public void run() {
                                Parcel parcel;
                                synchronized (BatteryStatsImpl.this.mCheckinFile) {
                                    long startTime2 = SystemClock.uptimeMillis();
                                    FileOutputStream stream = null;
                                    try {
                                        stream = BatteryStatsImpl.this.mCheckinFile.startWrite();
                                        stream.write(parcel.marshall());
                                        stream.flush();
                                        BatteryStatsImpl.this.mCheckinFile.finishWrite(stream);
                                        EventLogTags.writeCommitSysConfigFile("batterystats-checkin", (initialTime + SystemClock.uptimeMillis()) - startTime2);
                                        parcel = parcel;
                                    } catch (IOException e) {
                                        try {
                                            Slog.w("BatteryStats", "Error writing checkin battery statistics", e);
                                            BatteryStatsImpl.this.mCheckinFile.failWrite(stream);
                                            parcel = parcel;
                                        } catch (Throwable th) {
                                            parcel.recycle();
                                            throw th;
                                        }
                                    }
                                    parcel.recycle();
                                }
                            }
                        });
                    }
                    resetAllStatsLocked();
                    if (i4 > 0 && i3 > 0) {
                        this.mEstimatedBatteryCapacity = (int) (((double) (i4 / 1000)) / (((double) i3) / 100.0d));
                    }
                    this.mDischargeStartLevel = i3;
                    reset2 = true;
                    this.mDischargeStepTracker.init();
                    doWrite = true;
                }
                reset = reset2;
            } else {
                reset = false;
            }
            if (this.mCharging) {
                setChargingLocked(false);
            }
            this.mLastChargingStateLevel = i3;
            this.mOnBatteryInternal = true;
            this.mOnBattery = true;
            this.mLastDischargeStepLevel = i3;
            this.mMinDischargeStepLevel = i3;
            this.mDischargeStepTracker.clearTime();
            this.mDailyDischargeStepTracker.clearTime();
            this.mInitStepMode = this.mCurStepMode;
            this.mModStepMode = 0;
            pullPendingStateUpdatesLocked();
            this.mHistoryCur.batteryLevel = (byte) i3;
            this.mHistoryCur.states &= -524289;
            if (reset) {
                this.mRecordingHistory = true;
                i = 0;
                startRecordingHistory(mSecRealtime, mSecUptime, reset);
            } else {
                i = 0;
            }
            addHistoryRecordLocked(mSecRealtime, mSecUptime);
            this.mDischargeUnplugLevel = i3;
            this.mDischargeCurrentLevel = i3;
            if (isScreenOn(screenState)) {
                this.mDischargeScreenOnUnplugLevel = i3;
                this.mDischargeScreenDozeUnplugLevel = i;
                this.mDischargeScreenOffUnplugLevel = i;
            } else if (isScreenDoze(screenState)) {
                this.mDischargeScreenOnUnplugLevel = i;
                this.mDischargeScreenDozeUnplugLevel = i3;
                this.mDischargeScreenOffUnplugLevel = i;
            } else {
                this.mDischargeScreenOnUnplugLevel = i;
                this.mDischargeScreenDozeUnplugLevel = i;
                this.mDischargeScreenOffUnplugLevel = i3;
            }
            this.mDischargeAmountScreenOn = i;
            this.mDischargeAmountScreenDoze = i;
            this.mDischargeAmountScreenOff = i;
            updateTimeBasesLocked(true, screenState, uptime, realtime);
            int i5 = screenState;
        } else {
            int screenState2 = screenState;
            this.mLastChargingStateLevel = i3;
            this.mOnBatteryInternal = false;
            this.mOnBattery = false;
            pullPendingStateUpdatesLocked();
            this.mHistoryCur.batteryLevel = (byte) i3;
            this.mHistoryCur.states |= 524288;
            addHistoryRecordLocked(mSecRealtime, mSecUptime);
            this.mDischargePlugLevel = i3;
            this.mDischargeCurrentLevel = i3;
            if (i3 < this.mDischargeUnplugLevel) {
                this.mLowDischargeAmountSinceCharge += (this.mDischargeUnplugLevel - i3) - 1;
                this.mHighDischargeAmountSinceCharge += this.mDischargeUnplugLevel - i3;
            }
            updateDischargeScreenLevelsLocked(screenState2, screenState2);
            int i6 = screenState2;
            updateTimeBasesLocked(false, screenState2, uptime, realtime);
            this.mChargeStepTracker.init();
            this.mLastChargeStepLevel = i3;
            this.mMaxChargeStepLevel = i3;
            this.mInitStepMode = this.mCurStepMode;
            this.mModStepMode = 0;
        }
        if ((doWrite || this.mLastWriteTime + 60000 < mSecRealtime) && this.mStatsFile != null && this.mBatteryStatsHistory.getActiveFile() != null) {
            writeAsyncLocked();
        }
    }

    private void startRecordingHistory(long elapsedRealtimeMs, long uptimeMs, boolean reset) {
        this.mRecordingHistory = true;
        this.mHistoryCur.currentTime = System.currentTimeMillis();
        addHistoryBufferLocked(elapsedRealtimeMs, reset ? (byte) 7 : 5, this.mHistoryCur);
        this.mHistoryCur.currentTime = 0;
        if (reset) {
            initActiveHistoryEventsLocked(elapsedRealtimeMs, uptimeMs);
        }
    }

    private void recordCurrentTimeChangeLocked(long currentTime, long elapsedRealtimeMs, long uptimeMs) {
        if (this.mRecordingHistory) {
            this.mHistoryCur.currentTime = currentTime;
            addHistoryBufferLocked(elapsedRealtimeMs, (byte) 5, this.mHistoryCur);
            this.mHistoryCur.currentTime = 0;
        }
    }

    private void recordShutdownLocked(long elapsedRealtimeMs, long uptimeMs) {
        if (this.mRecordingHistory) {
            this.mHistoryCur.currentTime = System.currentTimeMillis();
            addHistoryBufferLocked(elapsedRealtimeMs, (byte) 8, this.mHistoryCur);
            this.mHistoryCur.currentTime = 0;
        }
    }

    private void scheduleSyncExternalStatsLocked(String reason, int updateFlags) {
        if (this.mExternalSync != null) {
            this.mExternalSync.scheduleSync(reason, updateFlags);
        }
    }

    @GuardedBy({"this"})
    public void setBatteryStateLocked(int status, int health, int plugType, int level, int temp, int volt, int chargeUAh, int chargeFullUAh) {
        boolean onBattery;
        long uptime;
        long elapsedRealtime;
        boolean onBattery2;
        boolean z;
        int i;
        int i2 = status;
        int i3 = health;
        int i4 = plugType;
        int i5 = level;
        int i6 = volt;
        int i7 = chargeUAh;
        int i8 = chargeFullUAh;
        int temp2 = Math.max(0, temp);
        reportChangesToStatsLog(this.mHaveBatteryLevel ? this.mHistoryCur : null, i2, i4, i5);
        boolean onBattery3 = isOnBattery(i4, i2);
        long uptime2 = this.mClocks.uptimeMillis();
        long elapsedRealtime2 = this.mClocks.elapsedRealtime();
        if (!this.mHaveBatteryLevel) {
            this.mHaveBatteryLevel = true;
            if (onBattery3 == this.mOnBattery) {
                if (onBattery3) {
                    this.mHistoryCur.states &= -524289;
                } else {
                    this.mHistoryCur.states |= 524288;
                }
            }
            this.mHistoryCur.states2 |= 16777216;
            this.mHistoryCur.batteryStatus = (byte) i2;
            this.mHistoryCur.batteryLevel = (byte) i5;
            this.mHistoryCur.batteryChargeUAh = i7;
            this.mLastDischargeStepLevel = i5;
            this.mLastChargeStepLevel = i5;
            this.mMinDischargeStepLevel = i5;
            this.mMaxChargeStepLevel = i5;
            this.mLastChargingStateLevel = i5;
        } else if (!(this.mCurrentBatteryLevel == i5 && this.mOnBattery == onBattery3)) {
            recordDailyStatsIfNeededLocked(i5 >= 100 && onBattery3);
        }
        int temp3 = this.mHistoryCur.batteryStatus;
        if (onBattery3) {
            this.mDischargeCurrentLevel = i5;
            if (!this.mRecordingHistory) {
                this.mRecordingHistory = true;
                elapsedRealtime = elapsedRealtime2;
                uptime = uptime2;
                onBattery = onBattery3;
                startRecordingHistory(elapsedRealtime2, uptime2, true);
            } else {
                elapsedRealtime = elapsedRealtime2;
                uptime = uptime2;
                onBattery = onBattery3;
            }
        } else {
            elapsedRealtime = elapsedRealtime2;
            uptime = uptime2;
            onBattery = onBattery3;
            if (i5 < 96 && i2 != 1 && !this.mRecordingHistory) {
                this.mRecordingHistory = true;
                startRecordingHistory(elapsedRealtime, uptime, true);
            }
        }
        this.mCurrentBatteryLevel = i5;
        if (this.mDischargePlugLevel < 0) {
            this.mDischargePlugLevel = i5;
        }
        boolean onBattery4 = onBattery;
        if (onBattery4 != this.mOnBattery) {
            this.mHistoryCur.batteryLevel = (byte) i5;
            this.mHistoryCur.batteryStatus = (byte) i2;
            this.mHistoryCur.batteryHealth = (byte) i3;
            this.mHistoryCur.batteryPlugType = (byte) i4;
            this.mHistoryCur.batteryTemperature = (short) temp2;
            this.mHistoryCur.batteryVoltage = (char) i6;
            if (i7 < this.mHistoryCur.batteryChargeUAh) {
                long chargeDiff = (long) (this.mHistoryCur.batteryChargeUAh - i7);
                this.mDischargeCounter.addCountLocked(chargeDiff);
                this.mDischargeScreenOffCounter.addCountLocked(chargeDiff);
                if (isScreenDoze(this.mScreenState)) {
                    this.mDischargeScreenDozeCounter.addCountLocked(chargeDiff);
                }
                if (this.mDeviceIdleMode == 1) {
                    this.mDischargeLightDozeCounter.addCountLocked(chargeDiff);
                } else if (this.mDeviceIdleMode == 2) {
                    this.mDischargeDeepDozeCounter.addCountLocked(chargeDiff);
                }
            }
            this.mHistoryCur.batteryChargeUAh = i7;
            onBattery2 = onBattery4;
            byte b = temp3;
            setOnBatteryLocked(elapsedRealtime, uptime, onBattery4, temp3, level, chargeUAh);
            int i9 = temp2;
            long j = elapsedRealtime;
            z = true;
        } else {
            onBattery2 = onBattery4;
            int temp4 = temp2;
            int i10 = temp3;
            boolean changed = false;
            if (this.mHistoryCur.batteryLevel != i5) {
                this.mHistoryCur.batteryLevel = (byte) i5;
                changed = true;
                this.mExternalSync.scheduleSyncDueToBatteryLevelChange(this.mConstants.BATTERY_LEVEL_COLLECTION_DELAY_MS);
            }
            if (this.mHistoryCur.batteryStatus != i2) {
                this.mHistoryCur.batteryStatus = (byte) i2;
                changed = true;
            }
            if (this.mHistoryCur.batteryHealth != i3) {
                this.mHistoryCur.batteryHealth = (byte) i3;
                changed = true;
            }
            if (this.mHistoryCur.batteryPlugType != i4) {
                this.mHistoryCur.batteryPlugType = (byte) i4;
                changed = true;
            }
            if (temp4 >= this.mHistoryCur.batteryTemperature + 10 || temp4 <= this.mHistoryCur.batteryTemperature - 10) {
                this.mHistoryCur.batteryTemperature = (short) temp4;
                changed = true;
            }
            if (i6 > this.mHistoryCur.batteryVoltage + 20 || i6 < this.mHistoryCur.batteryVoltage - 20) {
                this.mHistoryCur.batteryVoltage = (char) i6;
                changed = true;
            }
            int temp5 = temp4;
            int i11 = chargeUAh;
            if (i11 >= this.mHistoryCur.batteryChargeUAh + 10 || i11 <= this.mHistoryCur.batteryChargeUAh - 10) {
                if (i11 < this.mHistoryCur.batteryChargeUAh) {
                    long chargeDiff2 = (long) (this.mHistoryCur.batteryChargeUAh - i11);
                    this.mDischargeCounter.addCountLocked(chargeDiff2);
                    this.mDischargeScreenOffCounter.addCountLocked(chargeDiff2);
                    if (isScreenDoze(this.mScreenState)) {
                        this.mDischargeScreenDozeCounter.addCountLocked(chargeDiff2);
                    }
                    z = true;
                    if (this.mDeviceIdleMode == 1) {
                        this.mDischargeLightDozeCounter.addCountLocked(chargeDiff2);
                    } else if (this.mDeviceIdleMode == 2) {
                        this.mDischargeDeepDozeCounter.addCountLocked(chargeDiff2);
                    }
                } else {
                    z = true;
                }
                this.mHistoryCur.batteryChargeUAh = i11;
                changed = true;
            } else {
                z = true;
            }
            long modeBits = (((long) this.mInitStepMode) << 48) | (((long) this.mModStepMode) << 56) | (((long) (i5 & 255)) << 40);
            if (onBattery2) {
                changed |= setChargingLocked(false);
                if (this.mLastDischargeStepLevel != i5 && this.mMinDischargeStepLevel > i5) {
                    long j2 = modeBits;
                    long j3 = elapsedRealtime;
                    this.mDischargeStepTracker.addLevelSteps(this.mLastDischargeStepLevel - i5, j2, j3);
                    this.mDailyDischargeStepTracker.addLevelSteps(this.mLastDischargeStepLevel - i5, j2, j3);
                    this.mLastDischargeStepLevel = i5;
                    this.mMinDischargeStepLevel = i5;
                    this.mInitStepMode = this.mCurStepMode;
                    this.mModStepMode = 0;
                }
                int i12 = temp5;
            } else {
                if (i5 >= 90) {
                    changed |= setChargingLocked(z);
                    int i13 = temp5;
                } else if (this.mCharging) {
                    if (this.mLastChargeStepLevel > i5) {
                        changed |= setChargingLocked(false);
                    }
                } else if (this.mLastChargeStepLevel >= i5) {
                    if (this.mLastChargeStepLevel > i5) {
                        this.mHandler.removeCallbacks(this.mDeferSetCharging);
                    }
                } else if (!this.mHandler.hasCallbacks(this.mDeferSetCharging)) {
                    int i14 = temp5;
                    this.mHandler.postDelayed(this.mDeferSetCharging, (long) this.mConstants.BATTERY_CHARGED_DELAY_MS);
                }
                if (this.mLastChargeStepLevel != i5 && this.mMaxChargeStepLevel < i5) {
                    long j4 = modeBits;
                    long j5 = elapsedRealtime;
                    this.mChargeStepTracker.addLevelSteps(i5 - this.mLastChargeStepLevel, j4, j5);
                    this.mDailyChargeStepTracker.addLevelSteps(i5 - this.mLastChargeStepLevel, j4, j5);
                    this.mMaxChargeStepLevel = i5;
                    this.mInitStepMode = this.mCurStepMode;
                    this.mModStepMode = 0;
                }
                this.mLastChargeStepLevel = i5;
            }
            if (changed) {
                addHistoryRecordLocked(elapsedRealtime, uptime);
            } else {
                long j6 = uptime;
            }
        }
        if (!onBattery2 && (i2 == 5 || i2 == z)) {
            this.mRecordingHistory = false;
        }
        if (this.mMinLearnedBatteryCapacity == -1) {
            i = chargeFullUAh;
            this.mMinLearnedBatteryCapacity = i;
        } else {
            i = chargeFullUAh;
            this.mMinLearnedBatteryCapacity = Math.min(this.mMinLearnedBatteryCapacity, i);
        }
        this.mMaxLearnedBatteryCapacity = Math.max(this.mMaxLearnedBatteryCapacity, i);
    }

    public static boolean isOnBattery(int plugType, int status) {
        return plugType == 0 && status != 1;
    }

    private void reportChangesToStatsLog(BatteryStats.HistoryItem recentPast, int status, int plugType, int level) {
        if (recentPast == null || recentPast.batteryStatus != status) {
            StatsLog.write(31, status);
        }
        if (recentPast == null || recentPast.batteryPlugType != plugType) {
            StatsLog.write(32, plugType);
        }
        if (recentPast == null || recentPast.batteryLevel != level) {
            StatsLog.write(30, level);
        }
    }

    @UnsupportedAppUsage
    public long getAwakeTimeBattery() {
        return getBatteryUptimeLocked();
    }

    @UnsupportedAppUsage
    public long getAwakeTimePlugged() {
        return (this.mClocks.uptimeMillis() * 1000) - getAwakeTimeBattery();
    }

    public long computeUptime(long curTime, int which) {
        return this.mUptime + (curTime - this.mUptimeStart);
    }

    public long computeRealtime(long curTime, int which) {
        return this.mRealtime + (curTime - this.mRealtimeStart);
    }

    @UnsupportedAppUsage
    public long computeBatteryUptime(long curTime, int which) {
        return this.mOnBatteryTimeBase.computeUptime(curTime, which);
    }

    @UnsupportedAppUsage
    public long computeBatteryRealtime(long curTime, int which) {
        return this.mOnBatteryTimeBase.computeRealtime(curTime, which);
    }

    public long computeBatteryScreenOffUptime(long curTime, int which) {
        return this.mOnBatteryScreenOffTimeBase.computeUptime(curTime, which);
    }

    public long computeBatteryScreenOffRealtime(long curTime, int which) {
        return this.mOnBatteryScreenOffTimeBase.computeRealtime(curTime, which);
    }

    private long computeTimePerLevel(long[] steps, int numSteps) {
        if (numSteps <= 0) {
            return -1;
        }
        long total = 0;
        for (int i = 0; i < numSteps; i++) {
            total += steps[i] & BatteryStats.STEP_LEVEL_TIME_MASK;
        }
        return total / ((long) numSteps);
    }

    @UnsupportedAppUsage
    public long computeBatteryTimeRemaining(long curTime) {
        if (!this.mOnBattery || this.mDischargeStepTracker.mNumStepDurations < 1) {
            return -1;
        }
        long msPerLevel = this.mDischargeStepTracker.computeTimePerLevel();
        if (msPerLevel <= 0) {
            return -1;
        }
        return ((long) this.mCurrentBatteryLevel) * msPerLevel * 1000;
    }

    public BatteryStats.LevelStepTracker getDischargeLevelStepTracker() {
        return this.mDischargeStepTracker;
    }

    public BatteryStats.LevelStepTracker getDailyDischargeLevelStepTracker() {
        return this.mDailyDischargeStepTracker;
    }

    public long computeChargeTimeRemaining(long curTime) {
        if (this.mOnBattery || this.mChargeStepTracker.mNumStepDurations < 1) {
            return -1;
        }
        long msPerLevel = this.mChargeStepTracker.computeTimePerLevel();
        if (msPerLevel <= 0) {
            return -1;
        }
        return ((long) (100 - this.mCurrentBatteryLevel)) * msPerLevel * 1000;
    }

    public CellularBatteryStats getCellularBatteryStats() {
        long monitoredRailChargeConsumedMaMs;
        CellularBatteryStats s = new CellularBatteryStats();
        int i = 0;
        long rawRealTime = SystemClock.elapsedRealtime() * 1000;
        BatteryStats.ControllerActivityCounter counter = getModemControllerActivity();
        long sleepTimeMs = counter.getSleepTimeCounter().getCountLocked(0);
        long idleTimeMs = counter.getIdleTimeCounter().getCountLocked(0);
        long rxTimeMs = counter.getRxTimeCounter().getCountLocked(0);
        long energyConsumedMaMs = counter.getPowerCounter().getCountLocked(0);
        long monitoredRailChargeConsumedMaMs2 = counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(0);
        long[] timeInRatMs = new long[22];
        int i2 = 0;
        while (true) {
            int which = i;
            int which2 = i2;
            if (which2 >= timeInRatMs.length) {
                break;
            }
            timeInRatMs[which2] = getPhoneDataConnectionTime(which2, rawRealTime, 0) / 1000;
            i2 = which2 + 1;
            i = which;
        }
        long[] timeInRxSignalStrengthLevelMs = new long[5];
        int i3 = 0;
        while (true) {
            monitoredRailChargeConsumedMaMs = monitoredRailChargeConsumedMaMs2;
            int i4 = i3;
            if (i4 >= timeInRxSignalStrengthLevelMs.length) {
                break;
            }
            timeInRxSignalStrengthLevelMs[i4] = getPhoneSignalStrengthTime(i4, rawRealTime, 0) / 1000;
            i3 = i4 + 1;
            monitoredRailChargeConsumedMaMs2 = monitoredRailChargeConsumedMaMs;
        }
        long[] txTimeMs = new long[Math.min(5, counter.getTxTimeCounters().length)];
        long totalTxTimeMs = 0;
        int i5 = 0;
        while (i5 < txTimeMs.length) {
            txTimeMs[i5] = counter.getTxTimeCounters()[i5].getCountLocked(0);
            totalTxTimeMs += txTimeMs[i5];
            i5++;
            counter = counter;
        }
        s.setLoggingDurationMs(computeBatteryRealtime(rawRealTime, 0) / 1000);
        s.setKernelActiveTimeMs(getMobileRadioActiveTime(rawRealTime, 0) / 1000);
        long j = rawRealTime;
        s.setNumPacketsTx(getNetworkActivityPackets(1, 0));
        s.setNumBytesTx(getNetworkActivityBytes(1, 0));
        s.setNumPacketsRx(getNetworkActivityPackets(0, 0));
        s.setNumBytesRx(getNetworkActivityBytes(0, 0));
        s.setSleepTimeMs(sleepTimeMs);
        s.setIdleTimeMs(idleTimeMs);
        s.setRxTimeMs(rxTimeMs);
        s.setEnergyConsumedMaMs(energyConsumedMaMs);
        s.setTimeInRatMs(timeInRatMs);
        s.setTimeInRxSignalStrengthLevelMs(timeInRxSignalStrengthLevelMs);
        s.setTxTimeMs(txTimeMs);
        s.setMonitoredRailChargeConsumedMaMs(monitoredRailChargeConsumedMaMs);
        return s;
    }

    public WifiBatteryStats getWifiBatteryStats() {
        WifiBatteryStats s = new WifiBatteryStats();
        int which = 0;
        long rawRealTime = SystemClock.elapsedRealtime() * 1000;
        BatteryStats.ControllerActivityCounter counter = getWifiControllerActivity();
        long idleTimeMs = counter.getIdleTimeCounter().getCountLocked(0);
        long scanTimeMs = counter.getScanTimeCounter().getCountLocked(0);
        long rxTimeMs = counter.getRxTimeCounter().getCountLocked(0);
        long txTimeMs = counter.getTxTimeCounters()[0].getCountLocked(0);
        long scanTimeMs2 = scanTimeMs;
        long totalControllerActivityTimeMs = computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000, 0) / 1000;
        long idleTimeMs2 = idleTimeMs;
        long sleepTimeMs = totalControllerActivityTimeMs - ((idleTimeMs + rxTimeMs) + txTimeMs);
        long j = totalControllerActivityTimeMs;
        long energyConsumedMaMs = counter.getPowerCounter().getCountLocked(0);
        BatteryStats.ControllerActivityCounter controllerActivityCounter = counter;
        long monitoredRailChargeConsumedMaMs = counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(0);
        long numAppScanRequest = 0;
        int i = 0;
        while (true) {
            int which2 = which;
            if (i >= this.mUidStats.size()) {
                break;
            }
            numAppScanRequest += (long) this.mUidStats.valueAt(i).mWifiScanTimer.getCountLocked(0);
            i++;
            which = which2;
            energyConsumedMaMs = energyConsumedMaMs;
        }
        long energyConsumedMaMs2 = energyConsumedMaMs;
        long[] timeInStateMs = new long[8];
        for (int i2 = 0; i2 < 8; i2++) {
            timeInStateMs[i2] = getWifiStateTime(i2, rawRealTime, 0) / 1000;
        }
        long[] timeInSupplStateMs = new long[13];
        int i3 = 0;
        for (int i4 = 13; i3 < i4; i4 = 13) {
            timeInSupplStateMs[i3] = getWifiSupplStateTime(i3, rawRealTime, 0) / 1000;
            i3++;
        }
        int i5 = 5;
        long[] timeSignalStrengthTimeMs = new long[5];
        int i6 = 0;
        while (true) {
            long[] timeInSupplStateMs2 = timeInSupplStateMs;
            int i7 = i6;
            if (i7 < i5) {
                timeSignalStrengthTimeMs[i7] = getWifiSignalStrengthTime(i7, rawRealTime, 0) / 1000;
                i6 = i7 + 1;
                timeInSupplStateMs = timeInSupplStateMs2;
                i5 = 5;
            } else {
                s.setLoggingDurationMs(computeBatteryRealtime(rawRealTime, 0) / 1000);
                long rawRealTime2 = rawRealTime;
                long j2 = rawRealTime2;
                s.setKernelActiveTimeMs(getWifiActiveTime(rawRealTime2, 0) / 1000);
                s.setNumPacketsTx(getNetworkActivityPackets(3, 0));
                s.setNumBytesTx(getNetworkActivityBytes(3, 0));
                s.setNumPacketsRx(getNetworkActivityPackets(2, 0));
                s.setNumBytesRx(getNetworkActivityBytes(2, 0));
                s.setSleepTimeMs(sleepTimeMs);
                long idleTimeMs3 = idleTimeMs2;
                s.setIdleTimeMs(idleTimeMs3);
                s.setRxTimeMs(rxTimeMs);
                s.setTxTimeMs(txTimeMs);
                s.setScanTimeMs(scanTimeMs2);
                long j3 = idleTimeMs3;
                long idleTimeMs4 = energyConsumedMaMs2;
                s.setEnergyConsumedMaMs(idleTimeMs4);
                s.setNumAppScanRequest(numAppScanRequest);
                s.setTimeInStateMs(timeInStateMs);
                s.setTimeInSupplicantStateMs(timeInSupplStateMs2);
                s.setTimeInRxSignalStrengthLevelMs(timeSignalStrengthTimeMs);
                long j4 = idleTimeMs4;
                s.setMonitoredRailChargeConsumedMaMs(monitoredRailChargeConsumedMaMs);
                return s;
            }
        }
    }

    public GpsBatteryStats getGpsBatteryStats() {
        GpsBatteryStats s = new GpsBatteryStats();
        long rawRealTime = SystemClock.elapsedRealtime() * 1000;
        s.setLoggingDurationMs(computeBatteryRealtime(rawRealTime, 0) / 1000);
        s.setEnergyConsumedMaMs(getGpsBatteryDrainMaMs());
        long[] time = new long[2];
        for (int i = 0; i < time.length; i++) {
            time[i] = getGpsSignalQualityTime(i, rawRealTime, 0) / 1000;
        }
        s.setTimeInGpsSignalQualityLevel(time);
        return s;
    }

    public BatteryStats.LevelStepTracker getChargeLevelStepTracker() {
        return this.mChargeStepTracker;
    }

    public BatteryStats.LevelStepTracker getDailyChargeLevelStepTracker() {
        return this.mDailyChargeStepTracker;
    }

    public ArrayList<BatteryStats.PackageChange> getDailyPackageChanges() {
        return this.mDailyPackageChanges;
    }

    /* access modifiers changed from: protected */
    public long getBatteryUptimeLocked() {
        return this.mOnBatteryTimeBase.getUptime(this.mClocks.uptimeMillis() * 1000);
    }

    public long getBatteryUptime(long curTime) {
        return this.mOnBatteryTimeBase.getUptime(curTime);
    }

    @UnsupportedAppUsage
    public long getBatteryRealtime(long curTime) {
        return this.mOnBatteryTimeBase.getRealtime(curTime);
    }

    @UnsupportedAppUsage
    public int getDischargeStartLevel() {
        int dischargeStartLevelLocked;
        synchronized (this) {
            dischargeStartLevelLocked = getDischargeStartLevelLocked();
        }
        return dischargeStartLevelLocked;
    }

    public int getDischargeStartLevelLocked() {
        return this.mDischargeUnplugLevel;
    }

    @UnsupportedAppUsage
    public int getDischargeCurrentLevel() {
        int dischargeCurrentLevelLocked;
        synchronized (this) {
            dischargeCurrentLevelLocked = getDischargeCurrentLevelLocked();
        }
        return dischargeCurrentLevelLocked;
    }

    public int getDischargeCurrentLevelLocked() {
        return this.mDischargeCurrentLevel;
    }

    public int getLowDischargeAmountSinceCharge() {
        int val;
        synchronized (this) {
            val = this.mLowDischargeAmountSinceCharge;
            if (this.mOnBattery && this.mDischargeCurrentLevel < this.mDischargeUnplugLevel) {
                val += (this.mDischargeUnplugLevel - this.mDischargeCurrentLevel) - 1;
            }
        }
        return val;
    }

    public int getHighDischargeAmountSinceCharge() {
        int val;
        synchronized (this) {
            val = this.mHighDischargeAmountSinceCharge;
            if (this.mOnBattery && this.mDischargeCurrentLevel < this.mDischargeUnplugLevel) {
                val += this.mDischargeUnplugLevel - this.mDischargeCurrentLevel;
            }
        }
        return val;
    }

    @UnsupportedAppUsage
    public int getDischargeAmount(int which) {
        int dischargeAmount;
        if (which == 0) {
            dischargeAmount = getHighDischargeAmountSinceCharge();
        } else {
            dischargeAmount = getDischargeStartLevel() - getDischargeCurrentLevel();
        }
        if (dischargeAmount < 0) {
            return 0;
        }
        return dischargeAmount;
    }

    @UnsupportedAppUsage
    public int getDischargeAmountScreenOn() {
        int val;
        synchronized (this) {
            val = this.mDischargeAmountScreenOn;
            if (this.mOnBattery && isScreenOn(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOnUnplugLevel) {
                val += this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
            }
        }
        return val;
    }

    public int getDischargeAmountScreenOnSinceCharge() {
        int val;
        synchronized (this) {
            val = this.mDischargeAmountScreenOnSinceCharge;
            if (this.mOnBattery && isScreenOn(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOnUnplugLevel) {
                val += this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
            }
        }
        return val;
    }

    @UnsupportedAppUsage
    public int getDischargeAmountScreenOff() {
        int dischargeAmountScreenDoze;
        synchronized (this) {
            int val = this.mDischargeAmountScreenOff;
            if (this.mOnBattery && isScreenOff(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOffUnplugLevel) {
                val += this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel;
            }
            dischargeAmountScreenDoze = getDischargeAmountScreenDoze() + val;
        }
        return dischargeAmountScreenDoze;
    }

    public int getDischargeAmountScreenOffSinceCharge() {
        int dischargeAmountScreenDozeSinceCharge;
        synchronized (this) {
            int val = this.mDischargeAmountScreenOffSinceCharge;
            if (this.mOnBattery && isScreenOff(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOffUnplugLevel) {
                val += this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel;
            }
            dischargeAmountScreenDozeSinceCharge = getDischargeAmountScreenDozeSinceCharge() + val;
        }
        return dischargeAmountScreenDozeSinceCharge;
    }

    public int getDischargeAmountScreenDoze() {
        int val;
        synchronized (this) {
            val = this.mDischargeAmountScreenDoze;
            if (this.mOnBattery && isScreenDoze(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenDozeUnplugLevel) {
                val += this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
            }
        }
        return val;
    }

    public int getDischargeAmountScreenDozeSinceCharge() {
        int val;
        synchronized (this) {
            val = this.mDischargeAmountScreenDozeSinceCharge;
            if (this.mOnBattery && isScreenDoze(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenDozeUnplugLevel) {
                val += this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
            }
        }
        return val;
    }

    @UnsupportedAppUsage
    public Uid getUidStatsLocked(int uid) {
        Uid u = this.mUidStats.get(uid);
        if (u != null) {
            return u;
        }
        Uid u2 = new Uid(this, uid);
        this.mUidStats.put(uid, u2);
        return u2;
    }

    public Uid getAvailableUidStatsLocked(int uid) {
        return this.mUidStats.get(uid);
    }

    public void onCleanupUserLocked(int userId) {
        int firstUidForUser = UserHandle.getUid(userId, 0);
        this.mPendingRemovedUids.add(new UidToRemove(firstUidForUser, UserHandle.getUid(userId, 99999), this.mClocks.elapsedRealtime()));
    }

    public void onUserRemovedLocked(int userId) {
        int firstUidForUser = UserHandle.getUid(userId, 0);
        int lastUidForUser = UserHandle.getUid(userId, 99999);
        this.mUidStats.put(firstUidForUser, null);
        this.mUidStats.put(lastUidForUser, null);
        int firstIndex = this.mUidStats.indexOfKey(firstUidForUser);
        int lastIndex = this.mUidStats.indexOfKey(lastUidForUser);
        for (int i = firstIndex; i <= lastIndex; i++) {
            Uid uid = this.mUidStats.valueAt(i);
            if (uid != null) {
                uid.detachFromTimeBase();
            }
        }
        this.mUidStats.removeAtRange(firstIndex, (lastIndex - firstIndex) + 1);
    }

    @UnsupportedAppUsage
    public void removeUidStatsLocked(int uid) {
        Uid u = this.mUidStats.get(uid);
        if (u != null) {
            u.detachFromTimeBase();
        }
        this.mUidStats.remove(uid);
        this.mPendingRemovedUids.add(new UidToRemove(this, uid, this.mClocks.elapsedRealtime()));
    }

    @UnsupportedAppUsage
    public Uid.Proc getProcessStatsLocked(int uid, String name) {
        return getUidStatsLocked(mapUid(uid)).getProcessStatsLocked(name);
    }

    @UnsupportedAppUsage
    public Uid.Pkg getPackageStatsLocked(int uid, String pkg) {
        return getUidStatsLocked(mapUid(uid)).getPackageStatsLocked(pkg);
    }

    @UnsupportedAppUsage
    public Uid.Pkg.Serv getServiceStatsLocked(int uid, String pkg, String name) {
        return getUidStatsLocked(mapUid(uid)).getServiceStatsLocked(pkg, name);
    }

    public void shutdownLocked() {
        recordShutdownLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
        writeSyncLocked();
        this.mShuttingDown = true;
    }

    public boolean trackPerProcStateCpuTimes() {
        return this.mConstants.TRACK_CPU_TIMES_BY_PROC_STATE && this.mPerProcStateCpuTimesAvailable;
    }

    public void systemServicesReady(Context context) {
        this.mConstants.startObserving(context.getContentResolver());
        registerUsbStateReceiver(context);
    }

    @VisibleForTesting
    public final class Constants extends ContentObserver {
        private static final int DEFAULT_BATTERY_CHARGED_DELAY_MS = 900000;
        private static final long DEFAULT_BATTERY_LEVEL_COLLECTION_DELAY_MS = 300000;
        private static final long DEFAULT_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = 600000;
        private static final long DEFAULT_KERNEL_UID_READERS_THROTTLE_TIME = 1000;
        private static final int DEFAULT_MAX_HISTORY_BUFFER_KB = 128;
        private static final int DEFAULT_MAX_HISTORY_BUFFER_LOW_RAM_DEVICE_KB = 64;
        private static final int DEFAULT_MAX_HISTORY_FILES = 32;
        private static final int DEFAULT_MAX_HISTORY_FILES_LOW_RAM_DEVICE = 64;
        private static final long DEFAULT_PROC_STATE_CPU_TIMES_READ_DELAY_MS = 5000;
        private static final boolean DEFAULT_TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
        private static final boolean DEFAULT_TRACK_CPU_TIMES_BY_PROC_STATE = false;
        private static final long DEFAULT_UID_REMOVE_DELAY_MS = 300000;
        public static final String KEY_BATTERY_CHARGED_DELAY_MS = "battery_charged_delay_ms";
        public static final String KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS = "battery_level_collection_delay_ms";
        public static final String KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = "external_stats_collection_rate_limit_ms";
        public static final String KEY_KERNEL_UID_READERS_THROTTLE_TIME = "kernel_uid_readers_throttle_time";
        public static final String KEY_MAX_HISTORY_BUFFER_KB = "max_history_buffer_kb";
        public static final String KEY_MAX_HISTORY_FILES = "max_history_files";
        public static final String KEY_PROC_STATE_CPU_TIMES_READ_DELAY_MS = "proc_state_cpu_times_read_delay_ms";
        public static final String KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME = "track_cpu_active_cluster_time";
        public static final String KEY_TRACK_CPU_TIMES_BY_PROC_STATE = "track_cpu_times_by_proc_state";
        public static final String KEY_UID_REMOVE_DELAY_MS = "uid_remove_delay_ms";
        public int BATTERY_CHARGED_DELAY_MS = DEFAULT_BATTERY_CHARGED_DELAY_MS;
        public long BATTERY_LEVEL_COLLECTION_DELAY_MS = ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
        public long EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = DEFAULT_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        public long KERNEL_UID_READERS_THROTTLE_TIME;
        public int MAX_HISTORY_BUFFER;
        public int MAX_HISTORY_FILES;
        public long PROC_STATE_CPU_TIMES_READ_DELAY_MS = 5000;
        public boolean TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
        public boolean TRACK_CPU_TIMES_BY_PROC_STATE = false;
        public long UID_REMOVE_DELAY_MS = ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
        private final KeyValueListParser mParser = new KeyValueListParser(',');
        private ContentResolver mResolver;

        public Constants(Handler handler) {
            super(handler);
            if (ActivityManager.isLowRamDeviceStatic()) {
                this.MAX_HISTORY_FILES = 64;
                this.MAX_HISTORY_BUFFER = 65536;
                return;
            }
            this.MAX_HISTORY_FILES = 32;
            this.MAX_HISTORY_BUFFER = 131072;
        }

        public void startObserving(ContentResolver resolver) {
            this.mResolver = resolver;
            this.mResolver.registerContentObserver(Settings.Global.getUriFor(Settings.Global.BATTERY_STATS_CONSTANTS), false, this);
            this.mResolver.registerContentObserver(Settings.Global.getUriFor(Settings.Global.BATTERY_CHARGING_STATE_UPDATE_DELAY), false, this);
            updateConstants();
        }

        public void onChange(boolean selfChange, Uri uri) {
            if (uri.equals(Settings.Global.getUriFor(Settings.Global.BATTERY_CHARGING_STATE_UPDATE_DELAY))) {
                synchronized (BatteryStatsImpl.this) {
                    updateBatteryChargedDelayMsLocked();
                }
                return;
            }
            updateConstants();
        }

        private void updateConstants() {
            int i;
            synchronized (BatteryStatsImpl.this) {
                try {
                    this.mParser.setString(Settings.Global.getString(this.mResolver, Settings.Global.BATTERY_STATS_CONSTANTS));
                } catch (IllegalArgumentException e) {
                    Slog.e(BatteryStatsImpl.TAG, "Bad batterystats settings", e);
                }
                updateTrackCpuTimesByProcStateLocked(this.TRACK_CPU_TIMES_BY_PROC_STATE, this.mParser.getBoolean(KEY_TRACK_CPU_TIMES_BY_PROC_STATE, false));
                this.TRACK_CPU_ACTIVE_CLUSTER_TIME = this.mParser.getBoolean(KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME, true);
                updateProcStateCpuTimesReadDelayMs(this.PROC_STATE_CPU_TIMES_READ_DELAY_MS, this.mParser.getLong(KEY_PROC_STATE_CPU_TIMES_READ_DELAY_MS, 5000));
                updateKernelUidReadersThrottleTime(this.KERNEL_UID_READERS_THROTTLE_TIME, this.mParser.getLong(KEY_KERNEL_UID_READERS_THROTTLE_TIME, 1000));
                updateUidRemoveDelay(this.mParser.getLong(KEY_UID_REMOVE_DELAY_MS, ParcelableCallAnalytics.MILLIS_IN_5_MINUTES));
                this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = this.mParser.getLong(KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS, DEFAULT_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
                this.BATTERY_LEVEL_COLLECTION_DELAY_MS = this.mParser.getLong(KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS, ParcelableCallAnalytics.MILLIS_IN_5_MINUTES);
                KeyValueListParser keyValueListParser = this.mParser;
                int i2 = 64;
                if (ActivityManager.isLowRamDeviceStatic()) {
                    i = 64;
                } else {
                    i = 32;
                }
                this.MAX_HISTORY_FILES = keyValueListParser.getInt(KEY_MAX_HISTORY_FILES, i);
                KeyValueListParser keyValueListParser2 = this.mParser;
                if (!ActivityManager.isLowRamDeviceStatic()) {
                    i2 = 128;
                }
                this.MAX_HISTORY_BUFFER = keyValueListParser2.getInt(KEY_MAX_HISTORY_BUFFER_KB, i2) * 1024;
                updateBatteryChargedDelayMsLocked();
            }
        }

        private void updateBatteryChargedDelayMsLocked() {
            int delay = Settings.Global.getInt(this.mResolver, Settings.Global.BATTERY_CHARGING_STATE_UPDATE_DELAY, -1);
            this.BATTERY_CHARGED_DELAY_MS = delay >= 0 ? delay : this.mParser.getInt(KEY_BATTERY_CHARGED_DELAY_MS, DEFAULT_BATTERY_CHARGED_DELAY_MS);
        }

        private void updateTrackCpuTimesByProcStateLocked(boolean wasEnabled, boolean isEnabled) {
            this.TRACK_CPU_TIMES_BY_PROC_STATE = isEnabled;
            if (isEnabled && !wasEnabled) {
                boolean unused = BatteryStatsImpl.this.mIsPerProcessStateCpuDataStale = true;
                BatteryStatsImpl.this.mExternalSync.scheduleCpuSyncDueToSettingChange();
                long unused2 = BatteryStatsImpl.this.mNumSingleUidCpuTimeReads = 0;
                long unused3 = BatteryStatsImpl.this.mNumBatchedSingleUidCpuTimeReads = 0;
                long unused4 = BatteryStatsImpl.this.mCpuTimeReadsTrackingStartTime = BatteryStatsImpl.this.mClocks.uptimeMillis();
            }
        }

        private void updateProcStateCpuTimesReadDelayMs(long oldDelayMillis, long newDelayMillis) {
            this.PROC_STATE_CPU_TIMES_READ_DELAY_MS = newDelayMillis;
            if (oldDelayMillis != newDelayMillis) {
                long unused = BatteryStatsImpl.this.mNumSingleUidCpuTimeReads = 0;
                long unused2 = BatteryStatsImpl.this.mNumBatchedSingleUidCpuTimeReads = 0;
                long unused3 = BatteryStatsImpl.this.mCpuTimeReadsTrackingStartTime = BatteryStatsImpl.this.mClocks.uptimeMillis();
            }
        }

        private void updateKernelUidReadersThrottleTime(long oldTimeMs, long newTimeMs) {
            this.KERNEL_UID_READERS_THROTTLE_TIME = newTimeMs;
            if (oldTimeMs != newTimeMs) {
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidActiveTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidClusterTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
            }
        }

        private void updateUidRemoveDelay(long newTimeMs) {
            this.UID_REMOVE_DELAY_MS = newTimeMs;
            BatteryStatsImpl.this.clearPendingRemovedUids();
        }

        public void dumpLocked(PrintWriter pw) {
            pw.print(KEY_TRACK_CPU_TIMES_BY_PROC_STATE);
            pw.print("=");
            pw.println(this.TRACK_CPU_TIMES_BY_PROC_STATE);
            pw.print(KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME);
            pw.print("=");
            pw.println(this.TRACK_CPU_ACTIVE_CLUSTER_TIME);
            pw.print(KEY_PROC_STATE_CPU_TIMES_READ_DELAY_MS);
            pw.print("=");
            pw.println(this.PROC_STATE_CPU_TIMES_READ_DELAY_MS);
            pw.print(KEY_KERNEL_UID_READERS_THROTTLE_TIME);
            pw.print("=");
            pw.println(this.KERNEL_UID_READERS_THROTTLE_TIME);
            pw.print(KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
            pw.print("=");
            pw.println(this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
            pw.print(KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS);
            pw.print("=");
            pw.println(this.BATTERY_LEVEL_COLLECTION_DELAY_MS);
            pw.print(KEY_MAX_HISTORY_FILES);
            pw.print("=");
            pw.println(this.MAX_HISTORY_FILES);
            pw.print(KEY_MAX_HISTORY_BUFFER_KB);
            pw.print("=");
            pw.println(this.MAX_HISTORY_BUFFER / 1024);
            pw.print(KEY_BATTERY_CHARGED_DELAY_MS);
            pw.print("=");
            pw.println(this.BATTERY_CHARGED_DELAY_MS);
        }
    }

    public long getExternalStatsCollectionRateLimitMs() {
        long j;
        synchronized (this) {
            j = this.mConstants.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        }
        return j;
    }

    @GuardedBy({"this"})
    public void dumpConstantsLocked(PrintWriter pw) {
        this.mConstants.dumpLocked(pw);
    }

    @GuardedBy({"this"})
    public void dumpCpuStatsLocked(PrintWriter pw) {
        int size = this.mUidStats.size();
        pw.println("Per UID CPU user & system time in ms:");
        for (int i = 0; i < size; i++) {
            int u = this.mUidStats.keyAt(i);
            Uid uid = this.mUidStats.get(u);
            pw.print("  ");
            pw.print(u);
            pw.print(": ");
            pw.print(uid.getUserCpuTimeUs(0) / 1000);
            pw.print(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            pw.println(uid.getSystemCpuTimeUs(0) / 1000);
        }
        pw.println("Per UID CPU active time in ms:");
        for (int i2 = 0; i2 < size; i2++) {
            int u2 = this.mUidStats.keyAt(i2);
            Uid uid2 = this.mUidStats.get(u2);
            if (uid2.getCpuActiveTime() > 0) {
                pw.print("  ");
                pw.print(u2);
                pw.print(": ");
                pw.println(uid2.getCpuActiveTime());
            }
        }
        pw.println("Per UID CPU cluster time in ms:");
        for (int i3 = 0; i3 < size; i3++) {
            int u3 = this.mUidStats.keyAt(i3);
            long[] times = this.mUidStats.get(u3).getCpuClusterTimes();
            if (times != null) {
                pw.print("  ");
                pw.print(u3);
                pw.print(": ");
                pw.println(Arrays.toString(times));
            }
        }
        pw.println("Per UID CPU frequency time in ms:");
        for (int i4 = 0; i4 < size; i4++) {
            int u4 = this.mUidStats.keyAt(i4);
            long[] times2 = this.mUidStats.get(u4).getCpuFreqTimes(0);
            if (times2 != null) {
                pw.print("  ");
                pw.print(u4);
                pw.print(": ");
                pw.println(Arrays.toString(times2));
            }
        }
    }

    public void writeAsyncLocked() {
        writeStatsLocked(false);
        writeHistoryLocked(false);
    }

    public void writeSyncLocked() {
        writeStatsLocked(true);
        writeHistoryLocked(true);
    }

    /* access modifiers changed from: package-private */
    public void writeStatsLocked(boolean sync) {
        if (this.mStatsFile == null) {
            Slog.w(TAG, "writeStatsLocked: no file associated with this instance");
        } else if (!this.mShuttingDown) {
            Parcel p = Parcel.obtain();
            long uptimeMillis = SystemClock.uptimeMillis();
            writeSummaryToParcel(p, false);
            this.mLastWriteTime = this.mClocks.elapsedRealtime();
            writeParcelToFileLocked(p, this.mStatsFile, sync);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeHistoryLocked(boolean sync) {
        if (this.mBatteryStatsHistory.getActiveFile() == null) {
            Slog.w(TAG, "writeHistoryLocked: no history file associated with this instance");
        } else if (!this.mShuttingDown) {
            Parcel p = Parcel.obtain();
            long uptimeMillis = SystemClock.uptimeMillis();
            writeHistoryBuffer(p, true, true);
            writeParcelToFileLocked(p, this.mBatteryStatsHistory.getActiveFile(), sync);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeParcelToFileLocked(final Parcel p, final AtomicFile file, boolean sync) {
        if (sync) {
            commitPendingDataToDisk(p, file);
        } else {
            BackgroundThread.getHandler().post(new Runnable() {
                public void run() {
                    BatteryStatsImpl.this.commitPendingDataToDisk(p, file);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void commitPendingDataToDisk(Parcel p, AtomicFile file) {
        this.mWriteLock.lock();
        FileOutputStream fos = null;
        try {
            long startTime = SystemClock.uptimeMillis();
            fos = file.startWrite();
            fos.write(p.marshall());
            fos.flush();
            file.finishWrite(fos);
            EventLogTags.writeCommitSysConfigFile(BatteryStats.SERVICE_NAME, SystemClock.uptimeMillis() - startTime);
        } catch (IOException e) {
            Slog.w(TAG, "Error writing battery statistics", e);
            file.failWrite(fos);
        } catch (Throwable th) {
            p.recycle();
            this.mWriteLock.unlock();
            throw th;
        }
        p.recycle();
        this.mWriteLock.unlock();
    }

    @UnsupportedAppUsage
    public void readLocked() {
        if (this.mDailyFile != null) {
            readDailyStatsLocked();
        }
        if (this.mStatsFile == null) {
            Slog.w(TAG, "readLocked: no file associated with this instance");
        } else if (this.mBatteryStatsHistory.getActiveFile() == null) {
            Slog.w(TAG, "readLocked: no history file associated with this instance");
        } else {
            this.mUidStats.clear();
            Parcel stats = Parcel.obtain();
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                byte[] raw = this.mStatsFile.readFully();
                stats.unmarshall(raw, 0, raw.length);
                stats.setDataPosition(0);
                readSummaryFromParcel(stats);
            } catch (Exception e) {
                Slog.e(TAG, "Error reading battery statistics", e);
                resetAllStatsLocked();
            } catch (Throwable th) {
                stats.recycle();
                throw th;
            }
            stats.recycle();
            Parcel history = Parcel.obtain();
            try {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                byte[] raw2 = this.mBatteryStatsHistory.getActiveFile().readFully();
                if (raw2.length > 0) {
                    history.unmarshall(raw2, 0, raw2.length);
                    history.setDataPosition(0);
                    readHistoryBuffer(history, true);
                }
            } catch (Exception e2) {
                Slog.e(TAG, "Error reading battery history", e2);
                clearHistoryLocked();
                this.mBatteryStatsHistory.resetAllFiles();
            } catch (Throwable th2) {
                history.recycle();
                throw th2;
            }
            history.recycle();
            this.mEndPlatformVersion = Build.ID;
            if (this.mHistoryBuffer.dataPosition() > 0 || this.mBatteryStatsHistory.getFilesNumbers().size() > 1) {
                this.mRecordingHistory = true;
                long elapsedRealtime = this.mClocks.elapsedRealtime();
                long uptime = this.mClocks.uptimeMillis();
                addHistoryBufferLocked(elapsedRealtime, (byte) 4, this.mHistoryCur);
                startRecordingHistory(elapsedRealtime, uptime, false);
            }
            recordDailyStatsIfNeededLocked(false);
        }
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void readHistoryBuffer(Parcel in, boolean andOldHistory) throws ParcelFormatException {
        int version = in.readInt();
        if (version != 186) {
            Slog.w("BatteryStats", "readHistoryBuffer: version got " + version + ", expected " + 186 + "; erasing old stats");
            return;
        }
        long historyBaseTime = in.readLong();
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        int bufSize = in.readInt();
        int curPos = in.dataPosition();
        if (bufSize >= this.mConstants.MAX_HISTORY_BUFFER * 100) {
            throw new ParcelFormatException("File corrupt: history data buffer too large " + bufSize);
        } else if ((bufSize & -4) == bufSize) {
            this.mHistoryBuffer.appendFrom(in, curPos, bufSize);
            in.setDataPosition(curPos + bufSize);
            if (andOldHistory) {
                readOldHistory(in);
            }
            this.mHistoryBaseTime = historyBaseTime;
            if (this.mHistoryBaseTime > 0) {
                this.mHistoryBaseTime = (this.mHistoryBaseTime - this.mClocks.elapsedRealtime()) + 1;
            }
        } else {
            throw new ParcelFormatException("File corrupt: history data buffer not aligned " + bufSize);
        }
    }

    /* access modifiers changed from: package-private */
    public void readOldHistory(Parcel in) {
    }

    /* access modifiers changed from: package-private */
    public void writeHistoryBuffer(Parcel out, boolean inclData, boolean andOldHistory) {
        out.writeInt(186);
        out.writeLong(this.mHistoryBaseTime + this.mLastHistoryElapsedRealtime);
        if (!inclData) {
            out.writeInt(0);
            out.writeInt(0);
            return;
        }
        out.writeInt(this.mHistoryBuffer.dataSize());
        out.appendFrom(this.mHistoryBuffer, 0, this.mHistoryBuffer.dataSize());
        if (andOldHistory) {
            writeOldHistory(out);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeOldHistory(Parcel out) {
    }

    public void readSummaryFromParcel(Parcel in) throws ParcelFormatException {
        int NPKG;
        int numTags;
        boolean inclHistory;
        int version;
        int uid;
        int NMS;
        int length;
        int NPKG2;
        int numTags2;
        int numClusters;
        boolean inclHistory2;
        int NPKG3;
        int NWR;
        BatteryStatsImpl batteryStatsImpl = this;
        Parcel parcel = in;
        int version2 = in.readInt();
        if (version2 != 186) {
            Slog.w("BatteryStats", "readFromParcel: version got " + version2 + ", expected " + 186 + "; erasing old stats");
            return;
        }
        boolean inclHistory3 = in.readBoolean();
        if (inclHistory3) {
            batteryStatsImpl.readHistoryBuffer(parcel, true);
            batteryStatsImpl.mBatteryStatsHistory.readFromParcel(parcel);
        }
        batteryStatsImpl.mHistoryTagPool.clear();
        boolean z = false;
        batteryStatsImpl.mNextHistoryTagIdx = 0;
        batteryStatsImpl.mNumHistoryTagChars = 0;
        int numTags3 = in.readInt();
        int i = 0;
        while (i < numTags3) {
            int idx = in.readInt();
            String str = in.readString();
            if (str != null) {
                int uid2 = in.readInt();
                BatteryStats.HistoryTag tag = new BatteryStats.HistoryTag();
                tag.string = str;
                tag.uid = uid2;
                tag.poolIdx = idx;
                batteryStatsImpl.mHistoryTagPool.put(tag, Integer.valueOf(idx));
                if (idx >= batteryStatsImpl.mNextHistoryTagIdx) {
                    batteryStatsImpl.mNextHistoryTagIdx = idx + 1;
                }
                batteryStatsImpl.mNumHistoryTagChars += tag.string.length() + 1;
                i++;
            } else {
                throw new ParcelFormatException("null history tag string");
            }
        }
        batteryStatsImpl.mStartCount = in.readInt();
        batteryStatsImpl.mUptime = in.readLong();
        batteryStatsImpl.mRealtime = in.readLong();
        batteryStatsImpl.mStartClockTime = in.readLong();
        batteryStatsImpl.mStartPlatformVersion = in.readString();
        batteryStatsImpl.mEndPlatformVersion = in.readString();
        batteryStatsImpl.mOnBatteryTimeBase.readSummaryFromParcel(parcel);
        batteryStatsImpl.mOnBatteryScreenOffTimeBase.readSummaryFromParcel(parcel);
        batteryStatsImpl.mDischargeUnplugLevel = in.readInt();
        batteryStatsImpl.mDischargePlugLevel = in.readInt();
        batteryStatsImpl.mDischargeCurrentLevel = in.readInt();
        batteryStatsImpl.mCurrentBatteryLevel = in.readInt();
        batteryStatsImpl.mEstimatedBatteryCapacity = in.readInt();
        batteryStatsImpl.mMinLearnedBatteryCapacity = in.readInt();
        batteryStatsImpl.mMaxLearnedBatteryCapacity = in.readInt();
        batteryStatsImpl.mLowDischargeAmountSinceCharge = in.readInt();
        batteryStatsImpl.mHighDischargeAmountSinceCharge = in.readInt();
        batteryStatsImpl.mDischargeAmountScreenOnSinceCharge = in.readInt();
        batteryStatsImpl.mDischargeAmountScreenOffSinceCharge = in.readInt();
        batteryStatsImpl.mDischargeAmountScreenDozeSinceCharge = in.readInt();
        batteryStatsImpl.mDischargeStepTracker.readFromParcel(parcel);
        batteryStatsImpl.mChargeStepTracker.readFromParcel(parcel);
        batteryStatsImpl.mDailyDischargeStepTracker.readFromParcel(parcel);
        batteryStatsImpl.mDailyChargeStepTracker.readFromParcel(parcel);
        batteryStatsImpl.mDischargeCounter.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeScreenOffCounter.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeScreenDozeCounter.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeLightDozeCounter.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeDeepDozeCounter.readSummaryFromParcelLocked(parcel);
        int NPKG4 = in.readInt();
        if (NPKG4 > 0) {
            batteryStatsImpl.mDailyPackageChanges = new ArrayList<>(NPKG4);
            while (NPKG4 > 0) {
                NPKG4--;
                BatteryStats.PackageChange pc = new BatteryStats.PackageChange();
                pc.mPackageName = in.readString();
                pc.mUpdate = in.readInt() != 0;
                pc.mVersionCode = in.readLong();
                batteryStatsImpl.mDailyPackageChanges.add(pc);
            }
        } else {
            batteryStatsImpl.mDailyPackageChanges = null;
        }
        batteryStatsImpl.mDailyStartTime = in.readLong();
        batteryStatsImpl.mNextMinDailyDeadline = in.readLong();
        batteryStatsImpl.mNextMaxDailyDeadline = in.readLong();
        batteryStatsImpl.mStartCount++;
        batteryStatsImpl.mScreenState = 0;
        batteryStatsImpl.mScreenOnTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mScreenDozeTimer.readSummaryFromParcelLocked(parcel);
        for (int i2 = 0; i2 < 5; i2++) {
            batteryStatsImpl.mScreenBrightnessTimer[i2].readSummaryFromParcelLocked(parcel);
        }
        batteryStatsImpl.mInteractive = false;
        batteryStatsImpl.mInteractiveTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mPhoneOn = false;
        batteryStatsImpl.mPowerSaveModeEnabledTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mLongestLightIdleTime = in.readLong();
        batteryStatsImpl.mLongestFullIdleTime = in.readLong();
        batteryStatsImpl.mDeviceIdleModeLightTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDeviceIdleModeFullTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDeviceLightIdlingTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDeviceIdlingTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mPhoneOnTimer.readSummaryFromParcelLocked(parcel);
        for (int i3 = 0; i3 < 5; i3++) {
            batteryStatsImpl.mPhoneSignalStrengthsTimer[i3].readSummaryFromParcelLocked(parcel);
        }
        batteryStatsImpl.mPhoneSignalScanningTimer.readSummaryFromParcelLocked(parcel);
        for (int i4 = 0; i4 < 22; i4++) {
            batteryStatsImpl.mPhoneDataConnectionsTimer[i4].readSummaryFromParcelLocked(parcel);
        }
        for (int i5 = 0; i5 < 10; i5++) {
            batteryStatsImpl.mNetworkByteActivityCounters[i5].readSummaryFromParcelLocked(parcel);
            batteryStatsImpl.mNetworkPacketActivityCounters[i5].readSummaryFromParcelLocked(parcel);
        }
        batteryStatsImpl.mMobileRadioPowerState = 1;
        batteryStatsImpl.mMobileRadioActiveTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mMobileRadioActivePerAppTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mMobileRadioActiveAdjustedTime.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mMobileRadioActiveUnknownTime.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mMobileRadioActiveUnknownCount.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mWifiMulticastWakelockTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mWifiRadioPowerState = 1;
        batteryStatsImpl.mWifiOn = false;
        batteryStatsImpl.mWifiOnTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mGlobalWifiRunning = false;
        batteryStatsImpl.mGlobalWifiRunningTimer.readSummaryFromParcelLocked(parcel);
        for (int i6 = 0; i6 < 8; i6++) {
            batteryStatsImpl.mWifiStateTimer[i6].readSummaryFromParcelLocked(parcel);
        }
        for (int i7 = 0; i7 < 13; i7++) {
            batteryStatsImpl.mWifiSupplStateTimer[i7].readSummaryFromParcelLocked(parcel);
        }
        for (int i8 = 0; i8 < 5; i8++) {
            batteryStatsImpl.mWifiSignalStrengthsTimer[i8].readSummaryFromParcelLocked(parcel);
        }
        batteryStatsImpl.mWifiActiveTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mWifiActivity.readSummaryFromParcel(parcel);
        for (int i9 = 0; i9 < 2; i9++) {
            batteryStatsImpl.mGpsSignalQualityTimer[i9].readSummaryFromParcelLocked(parcel);
        }
        batteryStatsImpl.mBluetoothActivity.readSummaryFromParcel(parcel);
        batteryStatsImpl.mModemActivity.readSummaryFromParcel(parcel);
        batteryStatsImpl.mHasWifiReporting = in.readInt() != 0;
        batteryStatsImpl.mHasBluetoothReporting = in.readInt() != 0;
        batteryStatsImpl.mHasModemReporting = in.readInt() != 0;
        batteryStatsImpl.mNumConnectivityChange = in.readInt();
        batteryStatsImpl.mFlashlightOnNesting = 0;
        batteryStatsImpl.mFlashlightOnTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mCameraOnNesting = 0;
        batteryStatsImpl.mCameraOnTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mBluetoothScanNesting = 0;
        batteryStatsImpl.mBluetoothScanTimer.readSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mIsCellularTxPowerHigh = false;
        int NRPMS = in.readInt();
        if (NRPMS <= 10000) {
            for (int irpm = 0; irpm < NRPMS; irpm++) {
                if (in.readInt() != 0) {
                    batteryStatsImpl.getRpmTimerLocked(in.readString()).readSummaryFromParcelLocked(parcel);
                }
            }
            int NSORPMS = in.readInt();
            if (NSORPMS <= 10000) {
                for (int irpm2 = 0; irpm2 < NSORPMS; irpm2++) {
                    if (in.readInt() != 0) {
                        batteryStatsImpl.getScreenOffRpmTimerLocked(in.readString()).readSummaryFromParcelLocked(parcel);
                    }
                }
                int NKW = in.readInt();
                if (NKW <= 10000) {
                    for (int ikw = 0; ikw < NKW; ikw++) {
                        if (in.readInt() != 0) {
                            batteryStatsImpl.getKernelWakelockTimerLocked(in.readString()).readSummaryFromParcelLocked(parcel);
                        }
                    }
                    int NWR2 = in.readInt();
                    if (NWR2 <= 10000) {
                        for (int iwr = 0; iwr < NWR2; iwr++) {
                            if (in.readInt() != 0) {
                                batteryStatsImpl.getWakeupReasonTimerLocked(in.readString()).readSummaryFromParcelLocked(parcel);
                            }
                        }
                        int NMS2 = in.readInt();
                        int ims = 0;
                        while (ims < NMS2) {
                            if (in.readInt() != 0) {
                                NWR = NWR2;
                                batteryStatsImpl.getKernelMemoryTimerLocked(in.readLong()).readSummaryFromParcelLocked(parcel);
                            } else {
                                NWR = NWR2;
                            }
                            ims++;
                            NWR2 = NWR;
                        }
                        int NU = in.readInt();
                        if (NU <= 10000) {
                            int iu = 0;
                            while (iu < NU) {
                                int uid3 = in.readInt();
                                Uid u = new Uid(batteryStatsImpl, uid3);
                                batteryStatsImpl.mUidStats.put(uid3, u);
                                u.mOnBatteryBackgroundTimeBase.readSummaryFromParcel(parcel);
                                u.mOnBatteryScreenOffBackgroundTimeBase.readSummaryFromParcel(parcel);
                                u.mWifiRunning = z;
                                if (in.readInt() != 0) {
                                    u.mWifiRunningTimer.readSummaryFromParcelLocked(parcel);
                                }
                                u.mFullWifiLockOut = z;
                                if (in.readInt() != 0) {
                                    u.mFullWifiLockTimer.readSummaryFromParcelLocked(parcel);
                                }
                                u.mWifiScanStarted = z;
                                if (in.readInt() != 0) {
                                    u.mWifiScanTimer.readSummaryFromParcelLocked(parcel);
                                }
                                u.mWifiBatchedScanBinStarted = -1;
                                for (int i10 = z; i10 < 5; i10++) {
                                    if (in.readInt() != 0) {
                                        u.makeWifiBatchedScanBin(i10, (Parcel) null);
                                        u.mWifiBatchedScanTimer[i10].readSummaryFromParcelLocked(parcel);
                                    }
                                }
                                u.mWifiMulticastWakelockCount = 0;
                                if (in.readInt() != 0) {
                                    u.mWifiMulticastTimer.readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createAudioTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createVideoTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createFlashlightTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createCameraTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createForegroundActivityTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createForegroundServiceTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createAggregatedPartialWakelockTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createBluetoothScanTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createBluetoothUnoptimizedScanTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createBluetoothScanResultCounterLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    u.createBluetoothScanResultBgCounterLocked().readSummaryFromParcelLocked(parcel);
                                }
                                u.mProcessState = 21;
                                for (int i11 = 0; i11 < 7; i11++) {
                                    if (in.readInt() != 0) {
                                        u.makeProcessState(i11, (Parcel) null);
                                        u.mProcessStateTimer[i11].readSummaryFromParcelLocked(parcel);
                                    }
                                }
                                if (in.readInt() != 0) {
                                    u.createVibratorOnTimerLocked().readSummaryFromParcelLocked(parcel);
                                }
                                if (in.readInt() != 0) {
                                    if (u.mUserActivityCounters == null) {
                                        u.initUserActivityLocked();
                                    }
                                    for (int i12 = 0; i12 < Uid.NUM_USER_ACTIVITY_TYPES; i12++) {
                                        u.mUserActivityCounters[i12].readSummaryFromParcelLocked(parcel);
                                    }
                                }
                                if (in.readInt() != 0) {
                                    if (u.mNetworkByteActivityCounters == null) {
                                        u.initNetworkActivityLocked();
                                    }
                                    for (int i13 = 0; i13 < 10; i13++) {
                                        u.mNetworkByteActivityCounters[i13].readSummaryFromParcelLocked(parcel);
                                        u.mNetworkPacketActivityCounters[i13].readSummaryFromParcelLocked(parcel);
                                    }
                                    u.mMobileRadioActiveTime.readSummaryFromParcelLocked(parcel);
                                    u.mMobileRadioActiveCount.readSummaryFromParcelLocked(parcel);
                                }
                                u.mUserCpuTime.readSummaryFromParcelLocked(parcel);
                                u.mSystemCpuTime.readSummaryFromParcelLocked(parcel);
                                if (in.readInt() != 0) {
                                    int numClusters2 = in.readInt();
                                    if (batteryStatsImpl.mPowerProfile == null) {
                                        version = version2;
                                    } else if (batteryStatsImpl.mPowerProfile.getNumCpuClusters() == numClusters2) {
                                        version = version2;
                                    } else {
                                        int i14 = version2;
                                        throw new ParcelFormatException("Incompatible cpu cluster arrangement");
                                    }
                                    detachIfNotNull((T[][]) u.mCpuClusterSpeedTimesUs);
                                    u.mCpuClusterSpeedTimesUs = new LongSamplingCounter[numClusters2][];
                                    int cluster = 0;
                                    while (cluster < numClusters2) {
                                        if (in.readInt() != 0) {
                                            int NSB = in.readInt();
                                            inclHistory2 = inclHistory3;
                                            if (batteryStatsImpl.mPowerProfile == null) {
                                                numClusters = numClusters2;
                                                numTags2 = numTags3;
                                            } else if (batteryStatsImpl.mPowerProfile.getNumSpeedStepsInCpuCluster(cluster) == NSB) {
                                                numClusters = numClusters2;
                                                numTags2 = numTags3;
                                            } else {
                                                int i15 = numClusters2;
                                                StringBuilder sb = new StringBuilder();
                                                int i16 = numTags3;
                                                sb.append("File corrupt: too many speed bins ");
                                                sb.append(NSB);
                                                throw new ParcelFormatException(sb.toString());
                                            }
                                            u.mCpuClusterSpeedTimesUs[cluster] = new LongSamplingCounter[NSB];
                                            int speed = 0;
                                            while (speed < NSB) {
                                                if (in.readInt() != 0) {
                                                    NPKG3 = NPKG4;
                                                    u.mCpuClusterSpeedTimesUs[cluster][speed] = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
                                                    u.mCpuClusterSpeedTimesUs[cluster][speed].readSummaryFromParcelLocked(parcel);
                                                } else {
                                                    NPKG3 = NPKG4;
                                                }
                                                speed++;
                                                NPKG4 = NPKG3;
                                            }
                                            NPKG2 = NPKG4;
                                        } else {
                                            inclHistory2 = inclHistory3;
                                            numClusters = numClusters2;
                                            numTags2 = numTags3;
                                            NPKG2 = NPKG4;
                                            u.mCpuClusterSpeedTimesUs[cluster] = null;
                                        }
                                        cluster++;
                                        inclHistory3 = inclHistory2;
                                        numClusters2 = numClusters;
                                        numTags3 = numTags2;
                                        NPKG4 = NPKG2;
                                    }
                                    inclHistory = inclHistory3;
                                    numTags = numTags3;
                                    NPKG = NPKG4;
                                } else {
                                    version = version2;
                                    inclHistory = inclHistory3;
                                    numTags = numTags3;
                                    NPKG = NPKG4;
                                    detachIfNotNull((T[][]) u.mCpuClusterSpeedTimesUs);
                                    u.mCpuClusterSpeedTimesUs = null;
                                }
                                detachIfNotNull(u.mCpuFreqTimeMs);
                                u.mCpuFreqTimeMs = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, batteryStatsImpl.mOnBatteryTimeBase);
                                detachIfNotNull(u.mScreenOffCpuFreqTimeMs);
                                u.mScreenOffCpuFreqTimeMs = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, batteryStatsImpl.mOnBatteryScreenOffTimeBase);
                                u.mCpuActiveTimeMs.readSummaryFromParcelLocked(parcel);
                                u.mCpuClusterTimesMs.readSummaryFromParcelLocked(parcel);
                                int length2 = in.readInt();
                                if (length2 == 7) {
                                    detachIfNotNull((T[]) u.mProcStateTimeMs);
                                    u.mProcStateTimeMs = new LongSamplingCounterArray[length2];
                                    for (int procState = 0; procState < length2; procState++) {
                                        u.mProcStateTimeMs[procState] = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, batteryStatsImpl.mOnBatteryTimeBase);
                                    }
                                } else {
                                    detachIfNotNull((T[]) u.mProcStateTimeMs);
                                    u.mProcStateTimeMs = null;
                                }
                                int length3 = in.readInt();
                                if (length3 == 7) {
                                    detachIfNotNull((T[]) u.mProcStateScreenOffTimeMs);
                                    u.mProcStateScreenOffTimeMs = new LongSamplingCounterArray[length3];
                                    for (int procState2 = 0; procState2 < length3; procState2++) {
                                        u.mProcStateScreenOffTimeMs[procState2] = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, batteryStatsImpl.mOnBatteryScreenOffTimeBase);
                                    }
                                } else {
                                    detachIfNotNull((T[]) u.mProcStateScreenOffTimeMs);
                                    u.mProcStateScreenOffTimeMs = null;
                                }
                                if (in.readInt() != 0) {
                                    detachIfNotNull(u.mMobileRadioApWakeupCount);
                                    LongSamplingCounter unused = u.mMobileRadioApWakeupCount = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
                                    u.mMobileRadioApWakeupCount.readSummaryFromParcelLocked(parcel);
                                } else {
                                    detachIfNotNull(u.mMobileRadioApWakeupCount);
                                    LongSamplingCounter unused2 = u.mMobileRadioApWakeupCount = null;
                                }
                                if (in.readInt() != 0) {
                                    detachIfNotNull(u.mWifiRadioApWakeupCount);
                                    LongSamplingCounter unused3 = u.mWifiRadioApWakeupCount = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
                                    u.mWifiRadioApWakeupCount.readSummaryFromParcelLocked(parcel);
                                } else {
                                    detachIfNotNull(u.mWifiRadioApWakeupCount);
                                    LongSamplingCounter unused4 = u.mWifiRadioApWakeupCount = null;
                                }
                                int NW = in.readInt();
                                if (NW <= MAX_WAKELOCKS_PER_UID + 1) {
                                    for (int iw = 0; iw < NW; iw++) {
                                        u.readWakeSummaryFromParcelLocked(in.readString(), parcel);
                                    }
                                    int NS = in.readInt();
                                    if (NS <= MAX_WAKELOCKS_PER_UID + 1) {
                                        for (int is = 0; is < NS; is++) {
                                            u.readSyncSummaryFromParcelLocked(in.readString(), parcel);
                                        }
                                        int NJ = in.readInt();
                                        if (NJ <= MAX_WAKELOCKS_PER_UID + 1) {
                                            for (int ij = 0; ij < NJ; ij++) {
                                                u.readJobSummaryFromParcelLocked(in.readString(), parcel);
                                            }
                                            u.readJobCompletionsFromParcelLocked(parcel);
                                            u.mJobsDeferredEventCount.readSummaryFromParcelLocked(parcel);
                                            u.mJobsDeferredCount.readSummaryFromParcelLocked(parcel);
                                            u.mJobsFreshnessTimeMs.readSummaryFromParcelLocked(parcel);
                                            detachIfNotNull((T[]) u.mJobsFreshnessBuckets);
                                            int i17 = 0;
                                            while (i17 < JOB_FRESHNESS_BUCKETS.length) {
                                                if (in.readInt() != 0) {
                                                    length = length3;
                                                    NMS = NMS2;
                                                    u.mJobsFreshnessBuckets[i17] = new Counter(u.mBsi.mOnBatteryTimeBase);
                                                    u.mJobsFreshnessBuckets[i17].readSummaryFromParcelLocked(parcel);
                                                } else {
                                                    length = length3;
                                                    NMS = NMS2;
                                                }
                                                i17++;
                                                length3 = length;
                                                NMS2 = NMS;
                                            }
                                            int NMS3 = NMS2;
                                            int length4 = in.readInt();
                                            if (length4 <= 1000) {
                                                int is2 = 0;
                                                while (is2 < length4) {
                                                    int seNumber = in.readInt();
                                                    if (in.readInt() != 0) {
                                                        uid = uid3;
                                                        u.getSensorTimerLocked(seNumber, true).readSummaryFromParcelLocked(parcel);
                                                    } else {
                                                        uid = uid3;
                                                    }
                                                    is2++;
                                                    uid3 = uid;
                                                }
                                                int NP = in.readInt();
                                                if (NP <= 1000) {
                                                    int ip = 0;
                                                    while (ip < NP) {
                                                        Uid.Proc p = u.getProcessStatsLocked(in.readString());
                                                        p.mUserTime = in.readLong();
                                                        p.mSystemTime = in.readLong();
                                                        p.mForegroundTime = in.readLong();
                                                        p.mStarts = in.readInt();
                                                        p.mNumCrashes = in.readInt();
                                                        p.mNumAnrs = in.readInt();
                                                        p.readExcessivePowerFromParcelLocked(parcel);
                                                        ip++;
                                                        NKW = NKW;
                                                    }
                                                    int NKW2 = NKW;
                                                    int NP2 = in.readInt();
                                                    if (NP2 <= 10000) {
                                                        int ip2 = 0;
                                                        while (ip2 < NP2) {
                                                            String pkgName = in.readString();
                                                            detachIfNotNull(u.mPackageStats.get(pkgName));
                                                            Uid.Pkg p2 = u.getPackageStatsLocked(pkgName);
                                                            int NWA = in.readInt();
                                                            if (NWA <= 10000) {
                                                                p2.mWakeupAlarms.clear();
                                                                int iwa = 0;
                                                                while (iwa < NWA) {
                                                                    int NS2 = NS;
                                                                    String tag2 = in.readString();
                                                                    int NRPMS2 = NRPMS;
                                                                    Counter c = new Counter(batteryStatsImpl.mOnBatteryScreenOffTimeBase);
                                                                    c.readSummaryFromParcelLocked(parcel);
                                                                    p2.mWakeupAlarms.put(tag2, c);
                                                                    iwa++;
                                                                    NS = NS2;
                                                                    NRPMS = NRPMS2;
                                                                    NSORPMS = NSORPMS;
                                                                }
                                                                int NRPMS3 = NRPMS;
                                                                int NSORPMS2 = NSORPMS;
                                                                NS = in.readInt();
                                                                if (NS <= 10000) {
                                                                    int is3 = 0;
                                                                    while (is3 < NS) {
                                                                        Uid.Pkg.Serv s = u.getServiceStatsLocked(pkgName, in.readString());
                                                                        s.mStartTime = in.readLong();
                                                                        s.mStarts = in.readInt();
                                                                        s.mLaunches = in.readInt();
                                                                        is3++;
                                                                        Parcel parcel2 = in;
                                                                    }
                                                                    ip2++;
                                                                    NRPMS = NRPMS3;
                                                                    NSORPMS = NSORPMS2;
                                                                    batteryStatsImpl = this;
                                                                    parcel = in;
                                                                } else {
                                                                    throw new ParcelFormatException("File corrupt: too many services " + NS);
                                                                }
                                                            } else {
                                                                int i18 = NS;
                                                                int i19 = NRPMS;
                                                                int i20 = NSORPMS;
                                                                throw new ParcelFormatException("File corrupt: too many wakeup alarms " + NWA);
                                                            }
                                                        }
                                                        int i21 = NSORPMS;
                                                        iu++;
                                                        version2 = version;
                                                        inclHistory3 = inclHistory;
                                                        numTags3 = numTags;
                                                        NPKG4 = NPKG;
                                                        NMS2 = NMS3;
                                                        NKW = NKW2;
                                                        batteryStatsImpl = this;
                                                        parcel = in;
                                                        z = false;
                                                    } else {
                                                        int i22 = NSORPMS;
                                                        throw new ParcelFormatException("File corrupt: too many packages " + NP2);
                                                    }
                                                } else {
                                                    int i23 = NRPMS;
                                                    int i24 = NSORPMS;
                                                    throw new ParcelFormatException("File corrupt: too many processes " + NP);
                                                }
                                            } else {
                                                int i25 = NRPMS;
                                                int i26 = NSORPMS;
                                                int i27 = uid3;
                                                throw new ParcelFormatException("File corrupt: too many sensors " + length4);
                                            }
                                        } else {
                                            int i28 = NKW;
                                            int i29 = NMS2;
                                            int i30 = NRPMS;
                                            int i31 = NSORPMS;
                                            int i32 = uid3;
                                            throw new ParcelFormatException("File corrupt: too many job timers " + NJ);
                                        }
                                    } else {
                                        int i33 = NKW;
                                        int i34 = NMS2;
                                        int i35 = NRPMS;
                                        int i36 = NSORPMS;
                                        int i37 = uid3;
                                        throw new ParcelFormatException("File corrupt: too many syncs " + NS);
                                    }
                                } else {
                                    int i38 = NKW;
                                    int i39 = NMS2;
                                    int i40 = NRPMS;
                                    int i41 = NSORPMS;
                                    int i42 = uid3;
                                    throw new ParcelFormatException("File corrupt: too many wake locks " + NW);
                                }
                            }
                            boolean z2 = inclHistory3;
                            int i43 = NKW;
                            int i44 = numTags3;
                            int i45 = NPKG4;
                            int i46 = NMS2;
                            int i47 = NRPMS;
                            int i48 = NSORPMS;
                            return;
                        }
                        boolean z3 = inclHistory3;
                        int i49 = NKW;
                        int i50 = numTags3;
                        int i51 = NPKG4;
                        int i52 = NMS2;
                        int i53 = NRPMS;
                        int i54 = NSORPMS;
                        throw new ParcelFormatException("File corrupt: too many uids " + NU);
                    }
                    boolean z4 = inclHistory3;
                    int i55 = NKW;
                    int i56 = numTags3;
                    int i57 = NPKG4;
                    int i58 = NRPMS;
                    int i59 = NSORPMS;
                    throw new ParcelFormatException("File corrupt: too many wakeup reasons " + NWR2);
                }
                boolean z5 = inclHistory3;
                int i60 = numTags3;
                int i61 = NPKG4;
                int i62 = NRPMS;
                int i63 = NSORPMS;
                throw new ParcelFormatException("File corrupt: too many kernel wake locks " + NKW);
            }
            boolean z6 = inclHistory3;
            int i64 = numTags3;
            int i65 = NPKG4;
            int i66 = NRPMS;
            throw new ParcelFormatException("File corrupt: too many screen-off rpm stats " + NSORPMS);
        }
        boolean z7 = inclHistory3;
        int i67 = numTags3;
        int i68 = NPKG4;
        throw new ParcelFormatException("File corrupt: too many rpm stats " + NRPMS);
    }

    public void writeSummaryToParcel(Parcel out, boolean inclHistory) {
        int NP;
        int i;
        int i2;
        BatteryStatsImpl batteryStatsImpl = this;
        Parcel parcel = out;
        pullPendingStateUpdatesLocked();
        getStartClockTime();
        long NOW_SYS = batteryStatsImpl.mClocks.uptimeMillis() * 1000;
        long NOWREAL_SYS = batteryStatsImpl.mClocks.elapsedRealtime() * 1000;
        parcel.writeInt(186);
        out.writeBoolean(inclHistory);
        int i3 = 1;
        if (inclHistory) {
            batteryStatsImpl.writeHistoryBuffer(parcel, true, true);
            batteryStatsImpl.mBatteryStatsHistory.writeToParcel(parcel);
        }
        parcel.writeInt(batteryStatsImpl.mHistoryTagPool.size());
        for (Map.Entry<BatteryStats.HistoryTag, Integer> ent : batteryStatsImpl.mHistoryTagPool.entrySet()) {
            BatteryStats.HistoryTag tag = ent.getKey();
            parcel.writeInt(ent.getValue().intValue());
            parcel.writeString(tag.string);
            parcel.writeInt(tag.uid);
        }
        parcel.writeInt(batteryStatsImpl.mStartCount);
        int i4 = 0;
        parcel.writeLong(batteryStatsImpl.computeUptime(NOW_SYS, 0));
        parcel.writeLong(batteryStatsImpl.computeRealtime(NOWREAL_SYS, 0));
        parcel.writeLong(batteryStatsImpl.mStartClockTime);
        parcel.writeString(batteryStatsImpl.mStartPlatformVersion);
        parcel.writeString(batteryStatsImpl.mEndPlatformVersion);
        Parcel parcel2 = out;
        long j = NOW_SYS;
        long j2 = NOWREAL_SYS;
        batteryStatsImpl.mOnBatteryTimeBase.writeSummaryToParcel(parcel2, j, j2);
        batteryStatsImpl.mOnBatteryScreenOffTimeBase.writeSummaryToParcel(parcel2, j, j2);
        parcel.writeInt(batteryStatsImpl.mDischargeUnplugLevel);
        parcel.writeInt(batteryStatsImpl.mDischargePlugLevel);
        parcel.writeInt(batteryStatsImpl.mDischargeCurrentLevel);
        parcel.writeInt(batteryStatsImpl.mCurrentBatteryLevel);
        parcel.writeInt(batteryStatsImpl.mEstimatedBatteryCapacity);
        parcel.writeInt(batteryStatsImpl.mMinLearnedBatteryCapacity);
        parcel.writeInt(batteryStatsImpl.mMaxLearnedBatteryCapacity);
        parcel.writeInt(getLowDischargeAmountSinceCharge());
        parcel.writeInt(getHighDischargeAmountSinceCharge());
        parcel.writeInt(getDischargeAmountScreenOnSinceCharge());
        parcel.writeInt(getDischargeAmountScreenOffSinceCharge());
        parcel.writeInt(getDischargeAmountScreenDozeSinceCharge());
        batteryStatsImpl.mDischargeStepTracker.writeToParcel(parcel);
        batteryStatsImpl.mChargeStepTracker.writeToParcel(parcel);
        batteryStatsImpl.mDailyDischargeStepTracker.writeToParcel(parcel);
        batteryStatsImpl.mDailyChargeStepTracker.writeToParcel(parcel);
        batteryStatsImpl.mDischargeCounter.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeScreenOffCounter.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeScreenDozeCounter.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeLightDozeCounter.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mDischargeDeepDozeCounter.writeSummaryFromParcelLocked(parcel);
        if (batteryStatsImpl.mDailyPackageChanges != null) {
            int NPKG = batteryStatsImpl.mDailyPackageChanges.size();
            parcel.writeInt(NPKG);
            for (int i5 = 0; i5 < NPKG; i5++) {
                BatteryStats.PackageChange pc = batteryStatsImpl.mDailyPackageChanges.get(i5);
                parcel.writeString(pc.mPackageName);
                parcel.writeInt(pc.mUpdate ? 1 : 0);
                parcel.writeLong(pc.mVersionCode);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(batteryStatsImpl.mDailyStartTime);
        parcel.writeLong(batteryStatsImpl.mNextMinDailyDeadline);
        parcel.writeLong(batteryStatsImpl.mNextMaxDailyDeadline);
        batteryStatsImpl.mScreenOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mScreenDozeTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        for (int i6 = 0; i6 < 5; i6++) {
            batteryStatsImpl.mScreenBrightnessTimer[i6].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        batteryStatsImpl.mInteractiveTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mPowerSaveModeEnabledTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        parcel.writeLong(batteryStatsImpl.mLongestLightIdleTime);
        parcel.writeLong(batteryStatsImpl.mLongestFullIdleTime);
        batteryStatsImpl.mDeviceIdleModeLightTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mDeviceIdleModeFullTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mDeviceLightIdlingTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mDeviceIdlingTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mPhoneOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        for (int i7 = 0; i7 < 5; i7++) {
            batteryStatsImpl.mPhoneSignalStrengthsTimer[i7].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        batteryStatsImpl.mPhoneSignalScanningTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        for (int i8 = 0; i8 < 22; i8++) {
            batteryStatsImpl.mPhoneDataConnectionsTimer[i8].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        for (int i9 = 0; i9 < 10; i9++) {
            batteryStatsImpl.mNetworkByteActivityCounters[i9].writeSummaryFromParcelLocked(parcel);
            batteryStatsImpl.mNetworkPacketActivityCounters[i9].writeSummaryFromParcelLocked(parcel);
        }
        batteryStatsImpl.mMobileRadioActiveTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mMobileRadioActivePerAppTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mMobileRadioActiveAdjustedTime.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mMobileRadioActiveUnknownTime.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mMobileRadioActiveUnknownCount.writeSummaryFromParcelLocked(parcel);
        batteryStatsImpl.mWifiMulticastWakelockTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mWifiOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mGlobalWifiRunningTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        for (int i10 = 0; i10 < 8; i10++) {
            batteryStatsImpl.mWifiStateTimer[i10].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        for (int i11 = 0; i11 < 13; i11++) {
            batteryStatsImpl.mWifiSupplStateTimer[i11].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        for (int i12 = 0; i12 < 5; i12++) {
            batteryStatsImpl.mWifiSignalStrengthsTimer[i12].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        batteryStatsImpl.mWifiActiveTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mWifiActivity.writeSummaryToParcel(parcel);
        for (int i13 = 0; i13 < 2; i13++) {
            batteryStatsImpl.mGpsSignalQualityTimer[i13].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        }
        batteryStatsImpl.mBluetoothActivity.writeSummaryToParcel(parcel);
        batteryStatsImpl.mModemActivity.writeSummaryToParcel(parcel);
        parcel.writeInt(batteryStatsImpl.mHasWifiReporting ? 1 : 0);
        parcel.writeInt(batteryStatsImpl.mHasBluetoothReporting ? 1 : 0);
        parcel.writeInt(batteryStatsImpl.mHasModemReporting ? 1 : 0);
        parcel.writeInt(batteryStatsImpl.mNumConnectivityChange);
        batteryStatsImpl.mFlashlightOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mCameraOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        batteryStatsImpl.mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
        parcel.writeInt(batteryStatsImpl.mRpmStats.size());
        for (Map.Entry<String, SamplingTimer> ent2 : batteryStatsImpl.mRpmStats.entrySet()) {
            Timer rpmt = ent2.getValue();
            if (rpmt != null) {
                parcel.writeInt(1);
                parcel.writeString(ent2.getKey());
                rpmt.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(batteryStatsImpl.mScreenOffRpmStats.size());
        for (Map.Entry<String, SamplingTimer> ent3 : batteryStatsImpl.mScreenOffRpmStats.entrySet()) {
            Timer rpmt2 = ent3.getValue();
            if (rpmt2 != null) {
                parcel.writeInt(1);
                parcel.writeString(ent3.getKey());
                rpmt2.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(batteryStatsImpl.mKernelWakelockStats.size());
        for (Map.Entry<String, SamplingTimer> ent4 : batteryStatsImpl.mKernelWakelockStats.entrySet()) {
            Timer kwlt = ent4.getValue();
            if (kwlt != null) {
                parcel.writeInt(1);
                parcel.writeString(ent4.getKey());
                kwlt.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(batteryStatsImpl.mWakeupReasonStats.size());
        for (Map.Entry<String, SamplingTimer> ent5 : batteryStatsImpl.mWakeupReasonStats.entrySet()) {
            SamplingTimer timer = ent5.getValue();
            if (timer != null) {
                parcel.writeInt(1);
                parcel.writeString(ent5.getKey());
                timer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(batteryStatsImpl.mKernelMemoryStats.size());
        for (int i14 = 0; i14 < batteryStatsImpl.mKernelMemoryStats.size(); i14++) {
            Timer kmt = batteryStatsImpl.mKernelMemoryStats.valueAt(i14);
            if (kmt != null) {
                parcel.writeInt(1);
                parcel.writeLong(batteryStatsImpl.mKernelMemoryStats.keyAt(i14));
                kmt.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
            } else {
                parcel.writeInt(0);
            }
        }
        int NU = batteryStatsImpl.mUidStats.size();
        parcel.writeInt(NU);
        int iu = 0;
        while (true) {
            int iu2 = iu;
            if (iu2 < NU) {
                parcel.writeInt(batteryStatsImpl.mUidStats.keyAt(iu2));
                Uid u = batteryStatsImpl.mUidStats.valueAt(iu2);
                Parcel parcel3 = out;
                int iu3 = iu2;
                Uid u2 = u;
                long j3 = NOW_SYS;
                int NU2 = NU;
                long j4 = NOWREAL_SYS;
                u.mOnBatteryBackgroundTimeBase.writeSummaryToParcel(parcel3, j3, j4);
                u2.mOnBatteryScreenOffBackgroundTimeBase.writeSummaryToParcel(parcel3, j3, j4);
                if (u2.mWifiRunningTimer != null) {
                    parcel.writeInt(i3);
                    u2.mWifiRunningTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mFullWifiLockTimer != null) {
                    parcel.writeInt(i3);
                    u2.mFullWifiLockTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mWifiScanTimer != null) {
                    parcel.writeInt(i3);
                    u2.mWifiScanTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                for (int i15 = i4; i15 < 5; i15++) {
                    if (u2.mWifiBatchedScanTimer[i15] != null) {
                        parcel.writeInt(i3);
                        u2.mWifiBatchedScanTimer[i15].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(i4);
                    }
                }
                if (u2.mWifiMulticastTimer != null) {
                    parcel.writeInt(i3);
                    u2.mWifiMulticastTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mAudioTurnedOnTimer != null) {
                    parcel.writeInt(i3);
                    u2.mAudioTurnedOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mVideoTurnedOnTimer != null) {
                    parcel.writeInt(i3);
                    u2.mVideoTurnedOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mFlashlightTurnedOnTimer != null) {
                    parcel.writeInt(i3);
                    u2.mFlashlightTurnedOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mCameraTurnedOnTimer != null) {
                    parcel.writeInt(i3);
                    u2.mCameraTurnedOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mForegroundActivityTimer != null) {
                    parcel.writeInt(i3);
                    u2.mForegroundActivityTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mForegroundServiceTimer != null) {
                    parcel.writeInt(i3);
                    u2.mForegroundServiceTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mAggregatedPartialWakelockTimer != null) {
                    parcel.writeInt(i3);
                    u2.mAggregatedPartialWakelockTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mBluetoothScanTimer != null) {
                    parcel.writeInt(i3);
                    u2.mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mBluetoothUnoptimizedScanTimer != null) {
                    parcel.writeInt(i3);
                    u2.mBluetoothUnoptimizedScanTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mBluetoothScanResultCounter != null) {
                    parcel.writeInt(i3);
                    u2.mBluetoothScanResultCounter.writeSummaryFromParcelLocked(parcel);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mBluetoothScanResultBgCounter != null) {
                    parcel.writeInt(i3);
                    u2.mBluetoothScanResultBgCounter.writeSummaryFromParcelLocked(parcel);
                } else {
                    parcel.writeInt(i4);
                }
                for (int i16 = i4; i16 < 7; i16++) {
                    if (u2.mProcessStateTimer[i16] != null) {
                        parcel.writeInt(i3);
                        u2.mProcessStateTimer[i16].writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(i4);
                    }
                }
                if (u2.mVibratorOnTimer != null) {
                    parcel.writeInt(i3);
                    u2.mVibratorOnTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                } else {
                    parcel.writeInt(i4);
                }
                if (u2.mUserActivityCounters == null) {
                    parcel.writeInt(i4);
                } else {
                    parcel.writeInt(i3);
                    for (int i17 = i4; i17 < Uid.NUM_USER_ACTIVITY_TYPES; i17++) {
                        u2.mUserActivityCounters[i17].writeSummaryFromParcelLocked(parcel);
                    }
                }
                if (u2.mNetworkByteActivityCounters == null) {
                    parcel.writeInt(i4);
                } else {
                    parcel.writeInt(i3);
                    for (int i18 = i4; i18 < 10; i18++) {
                        u2.mNetworkByteActivityCounters[i18].writeSummaryFromParcelLocked(parcel);
                        u2.mNetworkPacketActivityCounters[i18].writeSummaryFromParcelLocked(parcel);
                    }
                    u2.mMobileRadioActiveTime.writeSummaryFromParcelLocked(parcel);
                    u2.mMobileRadioActiveCount.writeSummaryFromParcelLocked(parcel);
                }
                u2.mUserCpuTime.writeSummaryFromParcelLocked(parcel);
                u2.mSystemCpuTime.writeSummaryFromParcelLocked(parcel);
                if (u2.mCpuClusterSpeedTimesUs != null) {
                    parcel.writeInt(i3);
                    parcel.writeInt(u2.mCpuClusterSpeedTimesUs.length);
                    LongSamplingCounter[][] longSamplingCounterArr = u2.mCpuClusterSpeedTimesUs;
                    int length = longSamplingCounterArr.length;
                    int i19 = i4;
                    while (i19 < length) {
                        LongSamplingCounter[] cpuSpeeds = longSamplingCounterArr[i19];
                        if (cpuSpeeds != null) {
                            parcel.writeInt(i3);
                            parcel.writeInt(cpuSpeeds.length);
                            int length2 = cpuSpeeds.length;
                            int i20 = i4;
                            while (i20 < length2) {
                                LongSamplingCounter c = cpuSpeeds[i20];
                                if (c != null) {
                                    parcel.writeInt(i3);
                                    c.writeSummaryFromParcelLocked(parcel);
                                    i2 = 0;
                                } else {
                                    i2 = 0;
                                    parcel.writeInt(0);
                                }
                                i20++;
                                i4 = i2;
                                i3 = 1;
                            }
                            i = i4;
                        } else {
                            i = i4;
                            parcel.writeInt(i);
                        }
                        i19++;
                        i4 = i;
                        i3 = 1;
                    }
                } else {
                    parcel.writeInt(i4);
                }
                LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, u2.mCpuFreqTimeMs);
                LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, u2.mScreenOffCpuFreqTimeMs);
                u2.mCpuActiveTimeMs.writeSummaryFromParcelLocked(parcel);
                u2.mCpuClusterTimesMs.writeSummaryToParcelLocked(parcel);
                if (u2.mProcStateTimeMs != null) {
                    parcel.writeInt(u2.mProcStateTimeMs.length);
                    for (LongSamplingCounterArray counters : u2.mProcStateTimeMs) {
                        LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, counters);
                    }
                } else {
                    parcel.writeInt(0);
                }
                if (u2.mProcStateScreenOffTimeMs != null) {
                    parcel.writeInt(u2.mProcStateScreenOffTimeMs.length);
                    for (LongSamplingCounterArray counters2 : u2.mProcStateScreenOffTimeMs) {
                        LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, counters2);
                    }
                } else {
                    parcel.writeInt(0);
                }
                if (u2.mMobileRadioApWakeupCount != null) {
                    parcel.writeInt(1);
                    u2.mMobileRadioApWakeupCount.writeSummaryFromParcelLocked(parcel);
                } else {
                    parcel.writeInt(0);
                }
                if (u2.mWifiRadioApWakeupCount != null) {
                    parcel.writeInt(1);
                    u2.mWifiRadioApWakeupCount.writeSummaryFromParcelLocked(parcel);
                } else {
                    parcel.writeInt(0);
                }
                ArrayMap<String, Uid.Wakelock> wakeStats = u2.mWakelockStats.getMap();
                int NW = wakeStats.size();
                parcel.writeInt(NW);
                for (int iw = 0; iw < NW; iw++) {
                    parcel.writeString(wakeStats.keyAt(iw));
                    Uid.Wakelock wl = wakeStats.valueAt(iw);
                    if (wl.mTimerFull != null) {
                        parcel.writeInt(1);
                        wl.mTimerFull.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (wl.mTimerPartial != null) {
                        parcel.writeInt(1);
                        wl.mTimerPartial.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (wl.mTimerWindow != null) {
                        parcel.writeInt(1);
                        wl.mTimerWindow.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (wl.mTimerDraw != null) {
                        parcel.writeInt(1);
                        wl.mTimerDraw.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(0);
                    }
                }
                ArrayMap<String, DualTimer> syncStats = u2.mSyncStats.getMap();
                int NS = syncStats.size();
                parcel.writeInt(NS);
                for (int is = 0; is < NS; is++) {
                    parcel.writeString(syncStats.keyAt(is));
                    syncStats.valueAt(is).writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                }
                ArrayMap<String, DualTimer> jobStats = u2.mJobStats.getMap();
                int NJ = jobStats.size();
                parcel.writeInt(NJ);
                for (int ij = 0; ij < NJ; ij++) {
                    parcel.writeString(jobStats.keyAt(ij));
                    jobStats.valueAt(ij).writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                }
                u2.writeJobCompletionsToParcelLocked(parcel);
                u2.mJobsDeferredEventCount.writeSummaryFromParcelLocked(parcel);
                u2.mJobsDeferredCount.writeSummaryFromParcelLocked(parcel);
                u2.mJobsFreshnessTimeMs.writeSummaryFromParcelLocked(parcel);
                for (int i21 = 0; i21 < JOB_FRESHNESS_BUCKETS.length; i21++) {
                    if (u2.mJobsFreshnessBuckets[i21] != null) {
                        parcel.writeInt(1);
                        u2.mJobsFreshnessBuckets[i21].writeSummaryFromParcelLocked(parcel);
                    } else {
                        parcel.writeInt(0);
                    }
                }
                int NSE = u2.mSensorStats.size();
                parcel.writeInt(NSE);
                int ise = 0;
                while (ise < NSE) {
                    ArrayMap<String, Uid.Wakelock> wakeStats2 = wakeStats;
                    parcel.writeInt(u2.mSensorStats.keyAt(ise));
                    Uid.Sensor se = u2.mSensorStats.valueAt(ise);
                    int NW2 = NW;
                    if (se.mTimer != null) {
                        parcel.writeInt(1);
                        se.mTimer.writeSummaryFromParcelLocked(parcel, NOWREAL_SYS);
                    } else {
                        parcel.writeInt(0);
                    }
                    ise++;
                    wakeStats = wakeStats2;
                    NW = NW2;
                }
                int i22 = NW;
                int NP2 = u2.mProcessStats.size();
                parcel.writeInt(NP2);
                int ip = 0;
                while (ip < NP2) {
                    parcel.writeString(u2.mProcessStats.keyAt(ip));
                    Uid.Proc ps = u2.mProcessStats.valueAt(ip);
                    parcel.writeLong(ps.mUserTime);
                    parcel.writeLong(ps.mSystemTime);
                    parcel.writeLong(ps.mForegroundTime);
                    parcel.writeInt(ps.mStarts);
                    parcel.writeInt(ps.mNumCrashes);
                    parcel.writeInt(ps.mNumAnrs);
                    ps.writeExcessivePowerToParcelLocked(parcel);
                    ip++;
                    syncStats = syncStats;
                    NS = NS;
                }
                int i23 = NS;
                int iwa = u2.mPackageStats.size();
                parcel.writeInt(iwa);
                if (iwa > 0) {
                    Iterator<Map.Entry<String, Uid.Pkg>> it = u2.mPackageStats.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Uid.Pkg> ent6 = it.next();
                        parcel.writeString(ent6.getKey());
                        Uid.Pkg ps2 = ent6.getValue();
                        int NWA = ps2.mWakeupAlarms.size();
                        parcel.writeInt(NWA);
                        int iwa2 = 0;
                        while (true) {
                            NP = iwa;
                            int iwa3 = iwa2;
                            if (iwa3 >= NWA) {
                                break;
                            }
                            parcel.writeString(ps2.mWakeupAlarms.keyAt(iwa3));
                            ps2.mWakeupAlarms.valueAt(iwa3).writeSummaryFromParcelLocked(parcel);
                            iwa2 = iwa3 + 1;
                            iwa = NP;
                            it = it;
                        }
                        Iterator<Map.Entry<String, Uid.Pkg>> it2 = it;
                        int NS2 = ps2.mServiceStats.size();
                        parcel.writeInt(NS2);
                        int is2 = 0;
                        while (is2 < NS2) {
                            int NS3 = NS2;
                            parcel.writeString(ps2.mServiceStats.keyAt(is2));
                            Uid.Pkg.Serv ss = ps2.mServiceStats.valueAt(is2);
                            parcel.writeLong(ss.getStartTimeToNowLocked(batteryStatsImpl.mOnBatteryTimeBase.getUptime(NOW_SYS)));
                            parcel.writeInt(ss.mStarts);
                            parcel.writeInt(ss.mLaunches);
                            is2++;
                            NS2 = NS3;
                            ent6 = ent6;
                            ps2 = ps2;
                            batteryStatsImpl = this;
                        }
                        int NS4 = NS2;
                        iwa = NP;
                        it = it2;
                        int i24 = NS4;
                        batteryStatsImpl = this;
                    }
                }
                iu = iu3 + 1;
                NU = NU2;
                batteryStatsImpl = this;
                i3 = 1;
                i4 = 0;
            } else {
                return;
            }
        }
    }

    public void readFromParcel(Parcel in) {
        readFromParcelLocked(in);
    }

    /* access modifiers changed from: package-private */
    public void readFromParcelLocked(Parcel in) {
        Parcel parcel = in;
        int magic = in.readInt();
        if (magic == MAGIC) {
            int uid = 0;
            readHistoryBuffer(parcel, false);
            this.mBatteryStatsHistory.readFromParcel(parcel);
            this.mStartCount = in.readInt();
            this.mStartClockTime = in.readLong();
            this.mStartPlatformVersion = in.readString();
            this.mEndPlatformVersion = in.readString();
            this.mUptime = in.readLong();
            this.mUptimeStart = in.readLong();
            this.mRealtime = in.readLong();
            this.mRealtimeStart = in.readLong();
            boolean z = true;
            this.mOnBattery = in.readInt() != 0;
            this.mEstimatedBatteryCapacity = in.readInt();
            this.mMinLearnedBatteryCapacity = in.readInt();
            this.mMaxLearnedBatteryCapacity = in.readInt();
            this.mOnBatteryInternal = false;
            this.mOnBatteryTimeBase.readFromParcel(parcel);
            this.mOnBatteryScreenOffTimeBase.readFromParcel(parcel);
            this.mScreenState = 0;
            Parcel parcel2 = in;
            this.mScreenOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -1, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel2);
            this.mScreenDozeTimer = new StopwatchTimer(this.mClocks, (Uid) null, -1, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel2);
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= 5) {
                    break;
                }
                this.mScreenBrightnessTimer[i2] = new StopwatchTimer(this.mClocks, (Uid) null, -100 - i2, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i = i2 + 1;
            }
            this.mInteractive = false;
            Parcel parcel3 = in;
            this.mInteractiveTimer = new StopwatchTimer(this.mClocks, (Uid) null, -10, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            this.mPhoneOn = false;
            this.mPowerSaveModeEnabledTimer = new StopwatchTimer(this.mClocks, (Uid) null, -2, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            this.mLongestLightIdleTime = in.readLong();
            this.mLongestFullIdleTime = in.readLong();
            this.mDeviceIdleModeLightTimer = new StopwatchTimer(this.mClocks, (Uid) null, -14, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            this.mDeviceIdleModeFullTimer = new StopwatchTimer(this.mClocks, (Uid) null, -11, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            this.mDeviceLightIdlingTimer = new StopwatchTimer(this.mClocks, (Uid) null, -15, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            this.mDeviceIdlingTimer = new StopwatchTimer(this.mClocks, (Uid) null, -12, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            this.mPhoneOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -3, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel3);
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 >= 5) {
                    break;
                }
                this.mPhoneSignalStrengthsTimer[i4] = new StopwatchTimer(this.mClocks, (Uid) null, -200 - i4, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i3 = i4 + 1;
            }
            this.mPhoneSignalScanningTimer = new StopwatchTimer(this.mClocks, (Uid) null, -199, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
            int i5 = 0;
            while (true) {
                int i6 = i5;
                if (i6 >= 22) {
                    break;
                }
                this.mPhoneDataConnectionsTimer[i6] = new StopwatchTimer(this.mClocks, (Uid) null, -300 - i6, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i5 = i6 + 1;
            }
            for (int i7 = 0; i7 < 10; i7++) {
                this.mNetworkByteActivityCounters[i7] = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
                this.mNetworkPacketActivityCounters[i7] = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            }
            this.mMobileRadioPowerState = 1;
            Parcel parcel4 = in;
            this.mMobileRadioActiveTimer = new StopwatchTimer(this.mClocks, (Uid) null, -400, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel4);
            this.mMobileRadioActivePerAppTimer = new StopwatchTimer(this.mClocks, (Uid) null, -401, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel4);
            this.mMobileRadioActiveAdjustedTime = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mMobileRadioActiveUnknownTime = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mMobileRadioActiveUnknownCount = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mWifiMulticastWakelockTimer = new StopwatchTimer(this.mClocks, (Uid) null, -4, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel4);
            this.mWifiRadioPowerState = 1;
            this.mWifiOn = false;
            this.mWifiOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -4, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel4);
            this.mGlobalWifiRunning = false;
            this.mGlobalWifiRunningTimer = new StopwatchTimer(this.mClocks, (Uid) null, -5, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel4);
            int i8 = 0;
            while (true) {
                int i9 = i8;
                if (i9 >= 8) {
                    break;
                }
                this.mWifiStateTimer[i9] = new StopwatchTimer(this.mClocks, (Uid) null, -600 - i9, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i8 = i9 + 1;
            }
            int i10 = 0;
            while (true) {
                int i11 = i10;
                if (i11 >= 13) {
                    break;
                }
                this.mWifiSupplStateTimer[i11] = new StopwatchTimer(this.mClocks, (Uid) null, -700 - i11, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i10 = i11 + 1;
            }
            int i12 = 0;
            while (true) {
                int i13 = i12;
                if (i13 >= 5) {
                    break;
                }
                this.mWifiSignalStrengthsTimer[i13] = new StopwatchTimer(this.mClocks, (Uid) null, -800 - i13, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i12 = i13 + 1;
            }
            this.mWifiActiveTimer = new StopwatchTimer(this.mClocks, (Uid) null, -900, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
            this.mWifiActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1, parcel);
            int i14 = 0;
            while (true) {
                int i15 = i14;
                if (i15 >= 2) {
                    break;
                }
                this.mGpsSignalQualityTimer[i15] = new StopwatchTimer(this.mClocks, (Uid) null, -1000 - i15, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, in);
                i14 = i15 + 1;
            }
            this.mBluetoothActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1, parcel);
            this.mModemActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 5, parcel);
            this.mHasWifiReporting = in.readInt() != 0;
            this.mHasBluetoothReporting = in.readInt() != 0;
            if (in.readInt() == 0) {
                z = false;
            }
            this.mHasModemReporting = z;
            this.mNumConnectivityChange = in.readInt();
            this.mAudioOnNesting = 0;
            this.mAudioOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -7, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
            this.mVideoOnNesting = 0;
            this.mVideoOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -8, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase);
            this.mFlashlightOnNesting = 0;
            Parcel parcel5 = in;
            this.mFlashlightOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -9, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel5);
            this.mCameraOnNesting = 0;
            this.mCameraOnTimer = new StopwatchTimer(this.mClocks, (Uid) null, -13, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel5);
            this.mBluetoothScanNesting = 0;
            this.mBluetoothScanTimer = new StopwatchTimer(this.mClocks, (Uid) null, -14, (ArrayList<StopwatchTimer>) null, this.mOnBatteryTimeBase, parcel5);
            this.mIsCellularTxPowerHigh = false;
            this.mDischargeUnplugLevel = in.readInt();
            this.mDischargePlugLevel = in.readInt();
            this.mDischargeCurrentLevel = in.readInt();
            this.mCurrentBatteryLevel = in.readInt();
            this.mLowDischargeAmountSinceCharge = in.readInt();
            this.mHighDischargeAmountSinceCharge = in.readInt();
            this.mDischargeAmountScreenOn = in.readInt();
            this.mDischargeAmountScreenOnSinceCharge = in.readInt();
            this.mDischargeAmountScreenOff = in.readInt();
            this.mDischargeAmountScreenOffSinceCharge = in.readInt();
            this.mDischargeAmountScreenDoze = in.readInt();
            this.mDischargeAmountScreenDozeSinceCharge = in.readInt();
            this.mDischargeStepTracker.readFromParcel(parcel);
            this.mChargeStepTracker.readFromParcel(parcel);
            this.mDischargeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mDischargeScreenOffCounter = new LongSamplingCounter(this.mOnBatteryScreenOffTimeBase, parcel);
            this.mDischargeScreenDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mDischargeLightDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mDischargeDeepDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, parcel);
            this.mLastWriteTime = in.readLong();
            this.mRpmStats.clear();
            int NRPMS = in.readInt();
            for (int irpm = 0; irpm < NRPMS; irpm++) {
                if (in.readInt() != 0) {
                    this.mRpmStats.put(in.readString(), new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase, parcel));
                }
            }
            this.mScreenOffRpmStats.clear();
            int NSORPMS = in.readInt();
            for (int irpm2 = 0; irpm2 < NSORPMS; irpm2++) {
                if (in.readInt() != 0) {
                    this.mScreenOffRpmStats.put(in.readString(), new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase, parcel));
                }
            }
            this.mKernelWakelockStats.clear();
            int NKW = in.readInt();
            for (int ikw = 0; ikw < NKW; ikw++) {
                if (in.readInt() != 0) {
                    this.mKernelWakelockStats.put(in.readString(), new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase, parcel));
                }
            }
            this.mWakeupReasonStats.clear();
            int NWR = in.readInt();
            for (int iwr = 0; iwr < NWR; iwr++) {
                if (in.readInt() != 0) {
                    this.mWakeupReasonStats.put(in.readString(), new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase, parcel));
                }
            }
            this.mKernelMemoryStats.clear();
            int nmt = in.readInt();
            for (int imt = 0; imt < nmt; imt++) {
                if (in.readInt() != 0) {
                    this.mKernelMemoryStats.put(Long.valueOf(in.readLong()).longValue(), new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase, parcel));
                }
            }
            this.mPartialTimers.clear();
            this.mFullTimers.clear();
            this.mWindowTimers.clear();
            this.mWifiRunningTimers.clear();
            this.mFullWifiLockTimers.clear();
            this.mWifiScanTimers.clear();
            this.mWifiBatchedScanTimers.clear();
            this.mWifiMulticastTimers.clear();
            this.mAudioTurnedOnTimers.clear();
            this.mVideoTurnedOnTimers.clear();
            this.mFlashlightTurnedOnTimers.clear();
            this.mCameraTurnedOnTimers.clear();
            int numUids = in.readInt();
            this.mUidStats.clear();
            while (true) {
                int i16 = uid;
                if (i16 < numUids) {
                    int uid2 = in.readInt();
                    Uid u = new Uid(this, uid2);
                    u.readFromParcelLocked(this.mOnBatteryTimeBase, this.mOnBatteryScreenOffTimeBase, parcel);
                    this.mUidStats.append(uid2, u);
                    uid = i16 + 1;
                } else {
                    return;
                }
            }
        } else {
            throw new ParcelFormatException("Bad magic number: #" + Integer.toHexString(magic));
        }
    }

    public void writeToParcel(Parcel out, int flags) {
        writeToParcelLocked(out, true, flags);
    }

    public void writeToParcelWithoutUids(Parcel out, int flags) {
        writeToParcelLocked(out, false, flags);
    }

    /* access modifiers changed from: package-private */
    public void writeToParcelLocked(Parcel out, boolean inclUids, int flags) {
        Parcel parcel = out;
        pullPendingStateUpdatesLocked();
        getStartClockTime();
        long uSecUptime = this.mClocks.uptimeMillis() * 1000;
        long uSecRealtime = this.mClocks.elapsedRealtime() * 1000;
        long batteryRealtime = this.mOnBatteryTimeBase.getRealtime(uSecRealtime);
        long realtime = this.mOnBatteryScreenOffTimeBase.getRealtime(uSecRealtime);
        parcel.writeInt(MAGIC);
        writeHistoryBuffer(parcel, true, false);
        this.mBatteryStatsHistory.writeToParcel(parcel);
        parcel.writeInt(this.mStartCount);
        parcel.writeLong(this.mStartClockTime);
        parcel.writeString(this.mStartPlatformVersion);
        parcel.writeString(this.mEndPlatformVersion);
        parcel.writeLong(this.mUptime);
        parcel.writeLong(this.mUptimeStart);
        parcel.writeLong(this.mRealtime);
        parcel.writeLong(this.mRealtimeStart);
        parcel.writeInt(this.mOnBattery ? 1 : 0);
        parcel.writeInt(this.mEstimatedBatteryCapacity);
        parcel.writeInt(this.mMinLearnedBatteryCapacity);
        parcel.writeInt(this.mMaxLearnedBatteryCapacity);
        Parcel parcel2 = out;
        long j = uSecUptime;
        long j2 = batteryRealtime;
        long j3 = uSecRealtime;
        this.mOnBatteryTimeBase.writeToParcel(parcel2, j, j3);
        this.mOnBatteryScreenOffTimeBase.writeToParcel(parcel2, j, j3);
        this.mScreenOnTimer.writeToParcel(parcel, uSecRealtime);
        this.mScreenDozeTimer.writeToParcel(parcel, uSecRealtime);
        for (int i = 0; i < 5; i++) {
            this.mScreenBrightnessTimer[i].writeToParcel(parcel, uSecRealtime);
        }
        this.mInteractiveTimer.writeToParcel(parcel, uSecRealtime);
        this.mPowerSaveModeEnabledTimer.writeToParcel(parcel, uSecRealtime);
        parcel.writeLong(this.mLongestLightIdleTime);
        parcel.writeLong(this.mLongestFullIdleTime);
        this.mDeviceIdleModeLightTimer.writeToParcel(parcel, uSecRealtime);
        this.mDeviceIdleModeFullTimer.writeToParcel(parcel, uSecRealtime);
        this.mDeviceLightIdlingTimer.writeToParcel(parcel, uSecRealtime);
        this.mDeviceIdlingTimer.writeToParcel(parcel, uSecRealtime);
        this.mPhoneOnTimer.writeToParcel(parcel, uSecRealtime);
        for (int i2 = 0; i2 < 5; i2++) {
            this.mPhoneSignalStrengthsTimer[i2].writeToParcel(parcel, uSecRealtime);
        }
        this.mPhoneSignalScanningTimer.writeToParcel(parcel, uSecRealtime);
        for (int i3 = 0; i3 < 22; i3++) {
            this.mPhoneDataConnectionsTimer[i3].writeToParcel(parcel, uSecRealtime);
        }
        for (int i4 = 0; i4 < 10; i4++) {
            this.mNetworkByteActivityCounters[i4].writeToParcel(parcel);
            this.mNetworkPacketActivityCounters[i4].writeToParcel(parcel);
        }
        this.mMobileRadioActiveTimer.writeToParcel(parcel, uSecRealtime);
        this.mMobileRadioActivePerAppTimer.writeToParcel(parcel, uSecRealtime);
        this.mMobileRadioActiveAdjustedTime.writeToParcel(parcel);
        this.mMobileRadioActiveUnknownTime.writeToParcel(parcel);
        this.mMobileRadioActiveUnknownCount.writeToParcel(parcel);
        this.mWifiMulticastWakelockTimer.writeToParcel(parcel, uSecRealtime);
        this.mWifiOnTimer.writeToParcel(parcel, uSecRealtime);
        this.mGlobalWifiRunningTimer.writeToParcel(parcel, uSecRealtime);
        for (int i5 = 0; i5 < 8; i5++) {
            this.mWifiStateTimer[i5].writeToParcel(parcel, uSecRealtime);
        }
        for (int i6 = 0; i6 < 13; i6++) {
            this.mWifiSupplStateTimer[i6].writeToParcel(parcel, uSecRealtime);
        }
        for (int i7 = 0; i7 < 5; i7++) {
            this.mWifiSignalStrengthsTimer[i7].writeToParcel(parcel, uSecRealtime);
        }
        this.mWifiActiveTimer.writeToParcel(parcel, uSecRealtime);
        this.mWifiActivity.writeToParcel(parcel, 0);
        for (int i8 = 0; i8 < 2; i8++) {
            this.mGpsSignalQualityTimer[i8].writeToParcel(parcel, uSecRealtime);
        }
        this.mBluetoothActivity.writeToParcel(parcel, 0);
        this.mModemActivity.writeToParcel(parcel, 0);
        parcel.writeInt(this.mHasWifiReporting ? 1 : 0);
        parcel.writeInt(this.mHasBluetoothReporting ? 1 : 0);
        parcel.writeInt(this.mHasModemReporting ? 1 : 0);
        parcel.writeInt(this.mNumConnectivityChange);
        this.mFlashlightOnTimer.writeToParcel(parcel, uSecRealtime);
        this.mCameraOnTimer.writeToParcel(parcel, uSecRealtime);
        this.mBluetoothScanTimer.writeToParcel(parcel, uSecRealtime);
        parcel.writeInt(this.mDischargeUnplugLevel);
        parcel.writeInt(this.mDischargePlugLevel);
        parcel.writeInt(this.mDischargeCurrentLevel);
        parcel.writeInt(this.mCurrentBatteryLevel);
        parcel.writeInt(this.mLowDischargeAmountSinceCharge);
        parcel.writeInt(this.mHighDischargeAmountSinceCharge);
        parcel.writeInt(this.mDischargeAmountScreenOn);
        parcel.writeInt(this.mDischargeAmountScreenOnSinceCharge);
        parcel.writeInt(this.mDischargeAmountScreenOff);
        parcel.writeInt(this.mDischargeAmountScreenOffSinceCharge);
        parcel.writeInt(this.mDischargeAmountScreenDoze);
        parcel.writeInt(this.mDischargeAmountScreenDozeSinceCharge);
        this.mDischargeStepTracker.writeToParcel(parcel);
        this.mChargeStepTracker.writeToParcel(parcel);
        this.mDischargeCounter.writeToParcel(parcel);
        this.mDischargeScreenOffCounter.writeToParcel(parcel);
        this.mDischargeScreenDozeCounter.writeToParcel(parcel);
        this.mDischargeLightDozeCounter.writeToParcel(parcel);
        this.mDischargeDeepDozeCounter.writeToParcel(parcel);
        parcel.writeLong(this.mLastWriteTime);
        parcel.writeInt(this.mRpmStats.size());
        for (Map.Entry<String, SamplingTimer> ent : this.mRpmStats.entrySet()) {
            SamplingTimer rpmt = ent.getValue();
            if (rpmt != null) {
                parcel.writeInt(1);
                parcel.writeString(ent.getKey());
                rpmt.writeToParcel(parcel, uSecRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mScreenOffRpmStats.size());
        for (Map.Entry<String, SamplingTimer> ent2 : this.mScreenOffRpmStats.entrySet()) {
            SamplingTimer rpmt2 = ent2.getValue();
            if (rpmt2 != null) {
                parcel.writeInt(1);
                parcel.writeString(ent2.getKey());
                rpmt2.writeToParcel(parcel, uSecRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        if (inclUids) {
            parcel.writeInt(this.mKernelWakelockStats.size());
            for (Map.Entry<String, SamplingTimer> ent3 : this.mKernelWakelockStats.entrySet()) {
                SamplingTimer kwlt = ent3.getValue();
                if (kwlt != null) {
                    parcel.writeInt(1);
                    parcel.writeString(ent3.getKey());
                    kwlt.writeToParcel(parcel, uSecRealtime);
                } else {
                    parcel.writeInt(0);
                }
            }
            parcel.writeInt(this.mWakeupReasonStats.size());
            for (Map.Entry<String, SamplingTimer> ent4 : this.mWakeupReasonStats.entrySet()) {
                SamplingTimer timer = ent4.getValue();
                if (timer != null) {
                    parcel.writeInt(1);
                    parcel.writeString(ent4.getKey());
                    timer.writeToParcel(parcel, uSecRealtime);
                } else {
                    parcel.writeInt(0);
                }
            }
        } else {
            parcel.writeInt(0);
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mKernelMemoryStats.size());
        for (int i9 = 0; i9 < this.mKernelMemoryStats.size(); i9++) {
            SamplingTimer kmt = this.mKernelMemoryStats.valueAt(i9);
            if (kmt != null) {
                parcel.writeInt(1);
                parcel.writeLong(this.mKernelMemoryStats.keyAt(i9));
                kmt.writeToParcel(parcel, uSecRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        if (inclUids) {
            int size = this.mUidStats.size();
            parcel.writeInt(size);
            for (int i10 = 0; i10 < size; i10++) {
                parcel.writeInt(this.mUidStats.keyAt(i10));
                this.mUidStats.valueAt(i10).writeToParcelLocked(out, uSecUptime, uSecRealtime);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public void prepareForDumpLocked() {
        pullPendingStateUpdatesLocked();
        getStartClockTime();
    }

    public void dumpLocked(Context context, PrintWriter pw, int flags, int reqUid, long histStart) {
        super.dumpLocked(context, pw, flags, reqUid, histStart);
        pw.print("Total cpu time reads: ");
        pw.println(this.mNumSingleUidCpuTimeReads);
        pw.print("Batched cpu time reads: ");
        pw.println(this.mNumBatchedSingleUidCpuTimeReads);
        pw.print("Batching Duration (min): ");
        pw.println((this.mClocks.uptimeMillis() - this.mCpuTimeReadsTrackingStartTime) / 60000);
        pw.print("All UID cpu time reads since the later of device start or stats reset: ");
        pw.println(this.mNumAllUidCpuTimeReads);
        pw.print("UIDs removed since the later of device start or stats reset: ");
        pw.println(this.mNumUidsRemoved);
    }
}
