package android.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.backup.FullBackup;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.LocationManager;
import android.net.TrafficStats;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiScanner;
import android.provider.SettingsStringUtil;
import android.telephony.SignalStrength;
import android.telephony.SmsManager;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.MutableBoolean;
import android.util.Pair;
import android.util.Printer;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.BatteryStatsHelper;
import com.android.internal.telephony.PhoneConstants;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.PluralRules;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BatteryStats implements Parcelable {
    private static final String AGGREGATED_WAKELOCK_DATA = "awl";
    public static final int AGGREGATED_WAKE_TYPE_PARTIAL = 20;
    private static final String APK_DATA = "apk";
    private static final String AUDIO_DATA = "aud";
    public static final int AUDIO_TURNED_ON = 15;
    private static final String BATTERY_DATA = "bt";
    private static final String BATTERY_DISCHARGE_DATA = "dc";
    private static final String BATTERY_LEVEL_DATA = "lv";
    private static final int BATTERY_STATS_CHECKIN_VERSION = 9;
    private static final String BLUETOOTH_CONTROLLER_DATA = "ble";
    private static final String BLUETOOTH_MISC_DATA = "blem";
    public static final int BLUETOOTH_SCAN_ON = 19;
    public static final int BLUETOOTH_UNOPTIMIZED_SCAN_ON = 21;
    private static final long BYTES_PER_GB = 1073741824;
    private static final long BYTES_PER_KB = 1024;
    private static final long BYTES_PER_MB = 1048576;
    private static final String CAMERA_DATA = "cam";
    public static final int CAMERA_TURNED_ON = 17;
    private static final String CELLULAR_CONTROLLER_NAME = "Cellular";
    private static final String CHARGE_STEP_DATA = "csd";
    private static final String CHARGE_TIME_REMAIN_DATA = "ctr";
    static final int CHECKIN_VERSION = 34;
    private static final String CPU_DATA = "cpu";
    private static final String CPU_TIMES_AT_FREQ_DATA = "ctf";
    private static final String DATA_CONNECTION_COUNT_DATA = "dcc";
    static final String[] DATA_CONNECTION_NAMES = {"none", "gprs", "edge", "umts", "cdma", "evdo_0", "evdo_A", "1xrtt", "hsdpa", "hsupa", "hspa", "iden", "evdo_b", "lte", "ehrpd", "hspap", "gsm", "td_scdma", "iwlan", "lte_ca", "nr", "other"};
    public static final int DATA_CONNECTION_NONE = 0;
    public static final int DATA_CONNECTION_OTHER = 21;
    private static final String DATA_CONNECTION_TIME_DATA = "dct";
    public static final int DEVICE_IDLE_MODE_DEEP = 2;
    public static final int DEVICE_IDLE_MODE_LIGHT = 1;
    public static final int DEVICE_IDLE_MODE_OFF = 0;
    private static final String DISCHARGE_STEP_DATA = "dsd";
    private static final String DISCHARGE_TIME_REMAIN_DATA = "dtr";
    public static final int DUMP_CHARGED_ONLY = 2;
    public static final int DUMP_DAILY_ONLY = 4;
    public static final int DUMP_DEVICE_WIFI_ONLY = 64;
    public static final int DUMP_HISTORY_ONLY = 8;
    public static final int DUMP_INCLUDE_HISTORY = 16;
    public static final int DUMP_VERBOSE = 32;
    private static final String FLASHLIGHT_DATA = "fla";
    public static final int FLASHLIGHT_TURNED_ON = 16;
    public static final int FOREGROUND_ACTIVITY = 10;
    private static final String FOREGROUND_ACTIVITY_DATA = "fg";
    public static final int FOREGROUND_SERVICE = 22;
    private static final String FOREGROUND_SERVICE_DATA = "fgs";
    public static final int FULL_WIFI_LOCK = 5;
    private static final String GLOBAL_BLUETOOTH_CONTROLLER_DATA = "gble";
    private static final String GLOBAL_CPU_FREQ_DATA = "gcf";
    private static final String GLOBAL_MODEM_CONTROLLER_DATA = "gmcd";
    private static final String GLOBAL_NETWORK_DATA = "gn";
    private static final String GLOBAL_WIFI_CONTROLLER_DATA = "gwfcd";
    private static final String GLOBAL_WIFI_DATA = "gwfl";
    private static final String HISTORY_DATA = "h";
    public static final String[] HISTORY_EVENT_CHECKIN_NAMES = {"Enl", "Epr", "Efg", "Etp", "Esy", "Ewl", "Ejb", "Eur", "Euf", "Ecn", "Eac", "Epi", "Epu", "Eal", "Est", "Eai", "Eaa", "Etw", "Esw", "Ewa", "Elw", "Eec"};
    public static final IntToString[] HISTORY_EVENT_INT_FORMATTERS = {sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sIntToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sIntToString};
    public static final String[] HISTORY_EVENT_NAMES = {"null", "proc", FOREGROUND_ACTIVITY_DATA, "top", "sync", "wake_lock_in", "job", "user", "userfg", "conn", "active", "pkginst", "pkgunin", "alarm", Context.STATS_MANAGER, "pkginactive", "pkgactive", "tmpwhitelist", "screenwake", "wakeupap", "longwake", "est_capacity"};
    public static final BitDescription[] HISTORY_STATE2_DESCRIPTIONS = {new BitDescription(Integer.MIN_VALUE, "power_save", "ps"), new BitDescription(1073741824, "video", "v"), new BitDescription(536870912, "wifi_running", "Ww"), new BitDescription(268435456, "wifi", "W"), new BitDescription(134217728, "flashlight", "fl"), new BitDescription(HistoryItem.STATE2_DEVICE_IDLE_MASK, 25, "device_idle", "di", new String[]{"off", "light", "full", "???"}, new String[]{"off", "light", "full", "???"}), new BitDescription(16777216, "charging", "ch"), new BitDescription(262144, "usb_data", "Ud"), new BitDescription(8388608, "phone_in_call", "Pcl"), new BitDescription(4194304, "bluetooth", "b"), new BitDescription(112, 4, "wifi_signal_strength", "Wss", new String[]{"0", "1", "2", "3", "4"}, new String[]{"0", "1", "2", "3", "4"}), new BitDescription(15, 0, "wifi_suppl", "Wsp", WIFI_SUPPL_STATE_NAMES, WIFI_SUPPL_STATE_SHORT_NAMES), new BitDescription(2097152, Context.CAMERA_SERVICE, "ca"), new BitDescription(1048576, "ble_scan", "bles"), new BitDescription(524288, "cellular_high_tx_power", "Chtp"), new BitDescription(128, 7, "gps_signal_quality", "Gss", new String[]{"poor", "good"}, new String[]{"poor", "good"})};
    public static final BitDescription[] HISTORY_STATE_DESCRIPTIONS = {new BitDescription(Integer.MIN_VALUE, "running", "r"), new BitDescription(1073741824, "wake_lock", "w"), new BitDescription(8388608, Context.SENSOR_SERVICE, DateFormat.SECOND), new BitDescription(536870912, LocationManager.GPS_PROVIDER, "g"), new BitDescription(268435456, "wifi_full_lock", "Wl"), new BitDescription(134217728, "wifi_scan", "Ws"), new BitDescription(65536, "wifi_multicast", "Wm"), new BitDescription(67108864, "wifi_radio", "Wr"), new BitDescription(33554432, "mobile_radio", "Pr"), new BitDescription(2097152, "phone_scanning", "Psc"), new BitDescription(4194304, "audio", FullBackup.APK_TREE_TOKEN), new BitDescription(1048576, "screen", "S"), new BitDescription(524288, BatteryManager.EXTRA_PLUGGED, "BP"), new BitDescription(262144, "screen_doze", "Sd"), new BitDescription(HistoryItem.STATE_DATA_CONNECTION_MASK, 9, "data_conn", "Pcn", DATA_CONNECTION_NAMES, DATA_CONNECTION_NAMES), new BitDescription(448, 6, "phone_state", "Pst", new String[]{"in", "out", PhoneConstants.APN_TYPE_EMERGENCY, "off"}, new String[]{"in", "out", "em", "off"}), new BitDescription(56, 3, "phone_signal_strength", "Pss", SignalStrength.SIGNAL_STRENGTH_NAMES, new String[]{"0", "1", "2", "3", "4"}), new BitDescription(7, 0, "brightness", "Sb", SCREEN_BRIGHTNESS_NAMES, SCREEN_BRIGHTNESS_SHORT_NAMES)};
    private static final String HISTORY_STRING_POOL = "hsp";
    public static final int JOB = 14;
    private static final String JOBS_DEFERRED_DATA = "jbd";
    private static final String JOB_COMPLETION_DATA = "jbc";
    private static final String JOB_DATA = "jb";
    public static final long[] JOB_FRESHNESS_BUCKETS = {3600000, 7200000, 14400000, 28800000, Long.MAX_VALUE};
    private static final String KERNEL_WAKELOCK_DATA = "kwl";
    private static final boolean LOCAL_LOGV = false;
    public static final int MAX_TRACKED_SCREEN_STATE = 4;
    public static final double MILLISECONDS_IN_HOUR = 3600000.0d;
    private static final String MISC_DATA = "m";
    private static final String MODEM_CONTROLLER_DATA = "mcd";
    public static final int NETWORK_BT_RX_DATA = 4;
    public static final int NETWORK_BT_TX_DATA = 5;
    private static final String NETWORK_DATA = "nt";
    public static final int NETWORK_MOBILE_BG_RX_DATA = 6;
    public static final int NETWORK_MOBILE_BG_TX_DATA = 7;
    public static final int NETWORK_MOBILE_RX_DATA = 0;
    public static final int NETWORK_MOBILE_TX_DATA = 1;
    public static final int NETWORK_WIFI_BG_RX_DATA = 8;
    public static final int NETWORK_WIFI_BG_TX_DATA = 9;
    public static final int NETWORK_WIFI_RX_DATA = 2;
    public static final int NETWORK_WIFI_TX_DATA = 3;
    @UnsupportedAppUsage
    public static final int NUM_DATA_CONNECTION_TYPES = 22;
    public static final int NUM_NETWORK_ACTIVITY_TYPES = 10;
    @UnsupportedAppUsage
    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;
    public static final int NUM_WIFI_SIGNAL_STRENGTH_BINS = 5;
    public static final int NUM_WIFI_STATES = 8;
    public static final int NUM_WIFI_SUPPL_STATES = 13;
    private static final String POWER_USE_ITEM_DATA = "pwi";
    private static final String POWER_USE_SUMMARY_DATA = "pws";
    private static final String PROCESS_DATA = "pr";
    public static final int PROCESS_STATE = 12;
    private static final String RESOURCE_POWER_MANAGER_DATA = "rpm";
    public static final String RESULT_RECEIVER_CONTROLLER_KEY = "controller_activity";
    public static final int SCREEN_BRIGHTNESS_BRIGHT = 4;
    public static final int SCREEN_BRIGHTNESS_DARK = 0;
    private static final String SCREEN_BRIGHTNESS_DATA = "br";
    public static final int SCREEN_BRIGHTNESS_DIM = 1;
    public static final int SCREEN_BRIGHTNESS_LIGHT = 3;
    public static final int SCREEN_BRIGHTNESS_MEDIUM = 2;
    static final String[] SCREEN_BRIGHTNESS_NAMES = {"dark", "dim", "medium", "light", "bright"};
    static final String[] SCREEN_BRIGHTNESS_SHORT_NAMES = {"0", "1", "2", "3", "4"};
    protected static final boolean SCREEN_OFF_RPM_STATS_ENABLED = false;
    public static final int SENSOR = 3;
    private static final String SENSOR_DATA = "sr";
    public static final String SERVICE_NAME = "batterystats";
    private static final String SIGNAL_SCANNING_TIME_DATA = "sst";
    private static final String SIGNAL_STRENGTH_COUNT_DATA = "sgc";
    private static final String SIGNAL_STRENGTH_TIME_DATA = "sgt";
    private static final String STATE_TIME_DATA = "st";
    @Deprecated
    @UnsupportedAppUsage
    public static final int STATS_CURRENT = 1;
    public static final int STATS_SINCE_CHARGED = 0;
    @Deprecated
    public static final int STATS_SINCE_UNPLUGGED = 2;
    private static final String[] STAT_NAMES = {"l", FullBackup.CACHE_TREE_TOKEN, "u"};
    public static final long STEP_LEVEL_INITIAL_MODE_MASK = 71776119061217280L;
    public static final int STEP_LEVEL_INITIAL_MODE_SHIFT = 48;
    public static final long STEP_LEVEL_LEVEL_MASK = 280375465082880L;
    public static final int STEP_LEVEL_LEVEL_SHIFT = 40;
    public static final int[] STEP_LEVEL_MODES_OF_INTEREST = {7, 15, 11, 7, 7, 7, 7, 7, 15, 11};
    public static final int STEP_LEVEL_MODE_DEVICE_IDLE = 8;
    public static final String[] STEP_LEVEL_MODE_LABELS = {"screen off", "screen off power save", "screen off device idle", "screen on", "screen on power save", "screen doze", "screen doze power save", "screen doze-suspend", "screen doze-suspend power save", "screen doze-suspend device idle"};
    public static final int STEP_LEVEL_MODE_POWER_SAVE = 4;
    public static final int STEP_LEVEL_MODE_SCREEN_STATE = 3;
    public static final int[] STEP_LEVEL_MODE_VALUES = {0, 4, 8, 1, 5, 2, 6, 3, 7, 11};
    public static final long STEP_LEVEL_MODIFIED_MODE_MASK = -72057594037927936L;
    public static final int STEP_LEVEL_MODIFIED_MODE_SHIFT = 56;
    public static final long STEP_LEVEL_TIME_MASK = 1099511627775L;
    public static final int SYNC = 13;
    private static final String SYNC_DATA = "sy";
    private static final String TAG = "BatteryStats";
    private static final String UID_DATA = "uid";
    @VisibleForTesting
    public static final String UID_TIMES_TYPE_ALL = "A";
    private static final String USER_ACTIVITY_DATA = "ua";
    private static final String VERSION_DATA = "vers";
    private static final String VIBRATOR_DATA = "vib";
    public static final int VIBRATOR_ON = 9;
    private static final String VIDEO_DATA = "vid";
    public static final int VIDEO_TURNED_ON = 8;
    private static final String WAKELOCK_DATA = "wl";
    private static final String WAKEUP_ALARM_DATA = "wua";
    private static final String WAKEUP_REASON_DATA = "wr";
    public static final int WAKE_TYPE_DRAW = 18;
    public static final int WAKE_TYPE_FULL = 1;
    @UnsupportedAppUsage
    public static final int WAKE_TYPE_PARTIAL = 0;
    public static final int WAKE_TYPE_WINDOW = 2;
    public static final int WIFI_AGGREGATE_MULTICAST_ENABLED = 23;
    public static final int WIFI_BATCHED_SCAN = 11;
    private static final String WIFI_CONTROLLER_DATA = "wfcd";
    private static final String WIFI_CONTROLLER_NAME = "WiFi";
    private static final String WIFI_DATA = "wfl";
    private static final String WIFI_MULTICAST_DATA = "wmc";
    public static final int WIFI_MULTICAST_ENABLED = 7;
    private static final String WIFI_MULTICAST_TOTAL_DATA = "wmct";
    public static final int WIFI_RUNNING = 4;
    public static final int WIFI_SCAN = 6;
    private static final String WIFI_SIGNAL_STRENGTH_COUNT_DATA = "wsgc";
    private static final String WIFI_SIGNAL_STRENGTH_TIME_DATA = "wsgt";
    private static final String WIFI_STATE_COUNT_DATA = "wsc";
    static final String[] WIFI_STATE_NAMES = {"off", "scanning", "no_net", "disconn", "sta", "p2p", "sta_p2p", "soft_ap"};
    public static final int WIFI_STATE_OFF = 0;
    public static final int WIFI_STATE_OFF_SCANNING = 1;
    public static final int WIFI_STATE_ON_CONNECTED_P2P = 5;
    public static final int WIFI_STATE_ON_CONNECTED_STA = 4;
    public static final int WIFI_STATE_ON_CONNECTED_STA_P2P = 6;
    public static final int WIFI_STATE_ON_DISCONNECTED = 3;
    public static final int WIFI_STATE_ON_NO_NETWORKS = 2;
    public static final int WIFI_STATE_SOFT_AP = 7;
    private static final String WIFI_STATE_TIME_DATA = "wst";
    public static final int WIFI_SUPPL_STATE_ASSOCIATED = 7;
    public static final int WIFI_SUPPL_STATE_ASSOCIATING = 6;
    public static final int WIFI_SUPPL_STATE_AUTHENTICATING = 5;
    public static final int WIFI_SUPPL_STATE_COMPLETED = 10;
    private static final String WIFI_SUPPL_STATE_COUNT_DATA = "wssc";
    public static final int WIFI_SUPPL_STATE_DISCONNECTED = 1;
    public static final int WIFI_SUPPL_STATE_DORMANT = 11;
    public static final int WIFI_SUPPL_STATE_FOUR_WAY_HANDSHAKE = 8;
    public static final int WIFI_SUPPL_STATE_GROUP_HANDSHAKE = 9;
    public static final int WIFI_SUPPL_STATE_INACTIVE = 3;
    public static final int WIFI_SUPPL_STATE_INTERFACE_DISABLED = 2;
    public static final int WIFI_SUPPL_STATE_INVALID = 0;
    static final String[] WIFI_SUPPL_STATE_NAMES = {"invalid", "disconn", "disabled", "inactive", "scanning", "authenticating", "associating", "associated", "4-way-handshake", "group-handshake", "completed", "dormant", "uninit"};
    public static final int WIFI_SUPPL_STATE_SCANNING = 4;
    static final String[] WIFI_SUPPL_STATE_SHORT_NAMES = {"inv", "dsc", "dis", "inact", "scan", "auth", "ascing", "asced", "4-way", WifiConfiguration.GroupCipher.varName, "compl", "dorm", "uninit"};
    private static final String WIFI_SUPPL_STATE_TIME_DATA = "wsst";
    public static final int WIFI_SUPPL_STATE_UNINITIALIZED = 12;
    private static final IntToString sIntToString = $$Lambda$q1UvBdLgHRZVzc68BxdksTmbuCw.INSTANCE;
    private static final IntToString sUidToString = $$Lambda$IyvVQC0mKtsfXbnO0kDL64hrk0.INSTANCE;
    private final StringBuilder mFormatBuilder = new StringBuilder(32);
    private final Formatter mFormatter = new Formatter(this.mFormatBuilder);

    public static abstract class ControllerActivityCounter {
        public abstract LongCounter getIdleTimeCounter();

        public abstract LongCounter getMonitoredRailChargeConsumedMaMs();

        public abstract LongCounter getPowerCounter();

        public abstract LongCounter getRxTimeCounter();

        public abstract LongCounter getScanTimeCounter();

        public abstract LongCounter getSleepTimeCounter();

        public abstract LongCounter[] getTxTimeCounters();
    }

    public static abstract class Counter {
        @UnsupportedAppUsage
        public abstract int getCountLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public static final class DailyItem {
        public LevelStepTracker mChargeSteps;
        public LevelStepTracker mDischargeSteps;
        public long mEndTime;
        public ArrayList<PackageChange> mPackageChanges;
        public long mStartTime;
    }

    @FunctionalInterface
    public interface IntToString {
        String applyAsString(int i);
    }

    public static abstract class LongCounter {
        public abstract long getCountLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public static abstract class LongCounterArray {
        public abstract long[] getCountsLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public static final class PackageChange {
        public String mPackageName;
        public boolean mUpdate;
        public long mVersionCode;
    }

    public abstract void commitCurrentHistoryBatchLocked();

    @UnsupportedAppUsage
    public abstract long computeBatteryRealtime(long j, int i);

    public abstract long computeBatteryScreenOffRealtime(long j, int i);

    public abstract long computeBatteryScreenOffUptime(long j, int i);

    @UnsupportedAppUsage
    public abstract long computeBatteryTimeRemaining(long j);

    @UnsupportedAppUsage
    public abstract long computeBatteryUptime(long j, int i);

    @UnsupportedAppUsage
    public abstract long computeChargeTimeRemaining(long j);

    public abstract long computeRealtime(long j, int i);

    public abstract long computeUptime(long j, int i);

    public abstract void finishIteratingHistoryLocked();

    public abstract void finishIteratingOldHistoryLocked();

    public abstract long getBatteryRealtime(long j);

    @UnsupportedAppUsage
    public abstract long getBatteryUptime(long j);

    public abstract ControllerActivityCounter getBluetoothControllerActivity();

    public abstract long getBluetoothScanTime(long j, int i);

    public abstract long getCameraOnTime(long j, int i);

    public abstract LevelStepTracker getChargeLevelStepTracker();

    public abstract long[] getCpuFreqs();

    public abstract long getCurrentDailyStartTime();

    public abstract LevelStepTracker getDailyChargeLevelStepTracker();

    public abstract LevelStepTracker getDailyDischargeLevelStepTracker();

    public abstract DailyItem getDailyItemLocked(int i);

    public abstract ArrayList<PackageChange> getDailyPackageChanges();

    public abstract int getDeviceIdleModeCount(int i, int i2);

    public abstract long getDeviceIdleModeTime(int i, long j, int i2);

    public abstract int getDeviceIdlingCount(int i, int i2);

    public abstract long getDeviceIdlingTime(int i, long j, int i2);

    public abstract int getDischargeAmount(int i);

    public abstract int getDischargeAmountScreenDoze();

    public abstract int getDischargeAmountScreenDozeSinceCharge();

    public abstract int getDischargeAmountScreenOff();

    public abstract int getDischargeAmountScreenOffSinceCharge();

    public abstract int getDischargeAmountScreenOn();

    public abstract int getDischargeAmountScreenOnSinceCharge();

    public abstract int getDischargeCurrentLevel();

    public abstract LevelStepTracker getDischargeLevelStepTracker();

    public abstract int getDischargeStartLevel();

    public abstract String getEndPlatformVersion();

    public abstract int getEstimatedBatteryCapacity();

    public abstract long getFlashlightOnCount(int i);

    public abstract long getFlashlightOnTime(long j, int i);

    @UnsupportedAppUsage
    public abstract long getGlobalWifiRunningTime(long j, int i);

    public abstract long getGpsBatteryDrainMaMs();

    public abstract long getGpsSignalQualityTime(int i, long j, int i2);

    public abstract int getHighDischargeAmountSinceCharge();

    public abstract long getHistoryBaseTime();

    public abstract int getHistoryStringPoolBytes();

    public abstract int getHistoryStringPoolSize();

    public abstract String getHistoryTagPoolString(int i);

    public abstract int getHistoryTagPoolUid(int i);

    public abstract int getHistoryTotalSize();

    public abstract int getHistoryUsedSize();

    public abstract long getInteractiveTime(long j, int i);

    public abstract boolean getIsOnBattery();

    public abstract LongSparseArray<? extends Timer> getKernelMemoryStats();

    public abstract Map<String, ? extends Timer> getKernelWakelockStats();

    public abstract long getLongestDeviceIdleModeTime(int i);

    public abstract int getLowDischargeAmountSinceCharge();

    public abstract int getMaxLearnedBatteryCapacity();

    public abstract int getMinLearnedBatteryCapacity();

    public abstract long getMobileRadioActiveAdjustedTime(int i);

    public abstract int getMobileRadioActiveCount(int i);

    public abstract long getMobileRadioActiveTime(long j, int i);

    public abstract int getMobileRadioActiveUnknownCount(int i);

    public abstract long getMobileRadioActiveUnknownTime(int i);

    public abstract ControllerActivityCounter getModemControllerActivity();

    public abstract long getNetworkActivityBytes(int i, int i2);

    public abstract long getNetworkActivityPackets(int i, int i2);

    @UnsupportedAppUsage
    public abstract boolean getNextHistoryLocked(HistoryItem historyItem);

    public abstract long getNextMaxDailyDeadline();

    public abstract long getNextMinDailyDeadline();

    public abstract boolean getNextOldHistoryLocked(HistoryItem historyItem);

    public abstract int getNumConnectivityChange(int i);

    public abstract int getParcelVersion();

    public abstract int getPhoneDataConnectionCount(int i, int i2);

    public abstract long getPhoneDataConnectionTime(int i, long j, int i2);

    public abstract Timer getPhoneDataConnectionTimer(int i);

    public abstract int getPhoneOnCount(int i);

    @UnsupportedAppUsage
    public abstract long getPhoneOnTime(long j, int i);

    public abstract long getPhoneSignalScanningTime(long j, int i);

    public abstract Timer getPhoneSignalScanningTimer();

    public abstract int getPhoneSignalStrengthCount(int i, int i2);

    @UnsupportedAppUsage
    public abstract long getPhoneSignalStrengthTime(int i, long j, int i2);

    /* access modifiers changed from: protected */
    public abstract Timer getPhoneSignalStrengthTimer(int i);

    public abstract int getPowerSaveModeEnabledCount(int i);

    public abstract long getPowerSaveModeEnabledTime(long j, int i);

    public abstract Map<String, ? extends Timer> getRpmStats();

    @UnsupportedAppUsage
    public abstract long getScreenBrightnessTime(int i, long j, int i2);

    public abstract Timer getScreenBrightnessTimer(int i);

    public abstract int getScreenDozeCount(int i);

    public abstract long getScreenDozeTime(long j, int i);

    public abstract Map<String, ? extends Timer> getScreenOffRpmStats();

    public abstract int getScreenOnCount(int i);

    @UnsupportedAppUsage
    public abstract long getScreenOnTime(long j, int i);

    public abstract long getStartClockTime();

    public abstract int getStartCount();

    public abstract String getStartPlatformVersion();

    public abstract long getUahDischarge(int i);

    public abstract long getUahDischargeDeepDoze(int i);

    public abstract long getUahDischargeLightDoze(int i);

    public abstract long getUahDischargeScreenDoze(int i);

    public abstract long getUahDischargeScreenOff(int i);

    @UnsupportedAppUsage
    public abstract SparseArray<? extends Uid> getUidStats();

    public abstract Map<String, ? extends Timer> getWakeupReasonStats();

    public abstract long getWifiActiveTime(long j, int i);

    public abstract ControllerActivityCounter getWifiControllerActivity();

    public abstract int getWifiMulticastWakelockCount(int i);

    public abstract long getWifiMulticastWakelockTime(long j, int i);

    @UnsupportedAppUsage
    public abstract long getWifiOnTime(long j, int i);

    public abstract int getWifiSignalStrengthCount(int i, int i2);

    public abstract long getWifiSignalStrengthTime(int i, long j, int i2);

    public abstract Timer getWifiSignalStrengthTimer(int i);

    public abstract int getWifiStateCount(int i, int i2);

    public abstract long getWifiStateTime(int i, long j, int i2);

    public abstract Timer getWifiStateTimer(int i);

    public abstract int getWifiSupplStateCount(int i, int i2);

    public abstract long getWifiSupplStateTime(int i, long j, int i2);

    public abstract Timer getWifiSupplStateTimer(int i);

    public abstract boolean hasBluetoothActivityReporting();

    public abstract boolean hasModemActivityReporting();

    public abstract boolean hasWifiActivityReporting();

    @UnsupportedAppUsage
    public abstract boolean startIteratingHistoryLocked();

    public abstract boolean startIteratingOldHistoryLocked();

    public abstract void writeToParcelWithoutUids(Parcel parcel, int i);

    public static abstract class Timer {
        @UnsupportedAppUsage
        public abstract int getCountLocked(int i);

        public abstract long getTimeSinceMarkLocked(long j);

        @UnsupportedAppUsage
        public abstract long getTotalTimeLocked(long j, int i);

        public abstract void logState(Printer printer, String str);

        public long getMaxDurationMsLocked(long elapsedRealtimeMs) {
            return -1;
        }

        public long getCurrentDurationMsLocked(long elapsedRealtimeMs) {
            return -1;
        }

        public long getTotalDurationMsLocked(long elapsedRealtimeMs) {
            return -1;
        }

        public Timer getSubTimer() {
            return null;
        }

        public boolean isRunningLocked() {
            return false;
        }
    }

    public static int mapToInternalProcessState(int procState) {
        if (procState == 21) {
            return 21;
        }
        if (procState == 2) {
            return 0;
        }
        if (ActivityManager.isForegroundService(procState)) {
            return 1;
        }
        if (procState <= 7) {
            return 2;
        }
        if (procState <= 12) {
            return 3;
        }
        if (procState <= 13) {
            return 4;
        }
        if (procState <= 14) {
            return 5;
        }
        return 6;
    }

    public static abstract class Uid {
        public static final int[] CRITICAL_PROC_STATES = {0, 3, 4, 1, 2};
        public static final int NUM_PROCESS_STATE = 7;
        public static final int NUM_USER_ACTIVITY_TYPES = USER_ACTIVITY_TYPES.length;
        public static final int NUM_WIFI_BATCHED_SCAN_BINS = 5;
        public static final int PROCESS_STATE_BACKGROUND = 3;
        public static final int PROCESS_STATE_CACHED = 6;
        public static final int PROCESS_STATE_FOREGROUND = 2;
        public static final int PROCESS_STATE_FOREGROUND_SERVICE = 1;
        public static final int PROCESS_STATE_HEAVY_WEIGHT = 5;
        static final String[] PROCESS_STATE_NAMES = {"Top", "Fg Service", "Foreground", "Background", "Top Sleeping", "Heavy Weight", "Cached"};
        public static final int PROCESS_STATE_TOP = 0;
        public static final int PROCESS_STATE_TOP_SLEEPING = 4;
        @VisibleForTesting
        public static final String[] UID_PROCESS_TYPES = {"T", "FS", "F", "B", "TS", "HW", "C"};
        static final String[] USER_ACTIVITY_TYPES = {"other", "button", "touch", Context.ACCESSIBILITY_SERVICE, Context.ATTENTION_SERVICE};

        public static abstract class Pkg {

            public static abstract class Serv {
                @UnsupportedAppUsage
                public abstract int getLaunches(int i);

                @UnsupportedAppUsage
                public abstract long getStartTime(long j, int i);

                @UnsupportedAppUsage
                public abstract int getStarts(int i);
            }

            @UnsupportedAppUsage
            public abstract ArrayMap<String, ? extends Serv> getServiceStats();

            @UnsupportedAppUsage
            public abstract ArrayMap<String, ? extends Counter> getWakeupAlarmStats();
        }

        public static abstract class Proc {

            public static class ExcessivePower {
                public static final int TYPE_CPU = 2;
                public static final int TYPE_WAKE = 1;
                @UnsupportedAppUsage
                public long overTime;
                @UnsupportedAppUsage
                public int type;
                @UnsupportedAppUsage
                public long usedTime;
            }

            @UnsupportedAppUsage
            public abstract int countExcessivePowers();

            @UnsupportedAppUsage
            public abstract ExcessivePower getExcessivePower(int i);

            @UnsupportedAppUsage
            public abstract long getForegroundTime(int i);

            public abstract int getNumAnrs(int i);

            public abstract int getNumCrashes(int i);

            @UnsupportedAppUsage
            public abstract int getStarts(int i);

            @UnsupportedAppUsage
            public abstract long getSystemTime(int i);

            @UnsupportedAppUsage
            public abstract long getUserTime(int i);

            public abstract boolean isActive();
        }

        public static abstract class Sensor {
            @UnsupportedAppUsage
            public static final int GPS = -10000;

            @UnsupportedAppUsage
            public abstract int getHandle();

            public abstract Timer getSensorBackgroundTime();

            @UnsupportedAppUsage
            public abstract Timer getSensorTime();
        }

        public static abstract class Wakelock {
            @UnsupportedAppUsage
            public abstract Timer getWakeTime(int i);
        }

        public abstract Timer getAggregatedPartialWakelockTimer();

        @UnsupportedAppUsage
        public abstract Timer getAudioTurnedOnTimer();

        public abstract ControllerActivityCounter getBluetoothControllerActivity();

        public abstract Timer getBluetoothScanBackgroundTimer();

        public abstract Counter getBluetoothScanResultBgCounter();

        public abstract Counter getBluetoothScanResultCounter();

        public abstract Timer getBluetoothScanTimer();

        public abstract Timer getBluetoothUnoptimizedScanBackgroundTimer();

        public abstract Timer getBluetoothUnoptimizedScanTimer();

        public abstract Timer getCameraTurnedOnTimer();

        public abstract long getCpuActiveTime();

        public abstract long[] getCpuClusterTimes();

        public abstract long[] getCpuFreqTimes(int i);

        public abstract long[] getCpuFreqTimes(int i, int i2);

        public abstract void getDeferredJobsCheckinLineLocked(StringBuilder sb, int i);

        public abstract void getDeferredJobsLineLocked(StringBuilder sb, int i);

        public abstract Timer getFlashlightTurnedOnTimer();

        public abstract Timer getForegroundActivityTimer();

        public abstract Timer getForegroundServiceTimer();

        @UnsupportedAppUsage
        public abstract long getFullWifiLockTime(long j, int i);

        public abstract ArrayMap<String, SparseIntArray> getJobCompletionStats();

        public abstract ArrayMap<String, ? extends Timer> getJobStats();

        public abstract int getMobileRadioActiveCount(int i);

        @UnsupportedAppUsage
        public abstract long getMobileRadioActiveTime(int i);

        public abstract long getMobileRadioApWakeupCount(int i);

        public abstract ControllerActivityCounter getModemControllerActivity();

        public abstract Timer getMulticastWakelockStats();

        @UnsupportedAppUsage
        public abstract long getNetworkActivityBytes(int i, int i2);

        public abstract long getNetworkActivityPackets(int i, int i2);

        @UnsupportedAppUsage
        public abstract ArrayMap<String, ? extends Pkg> getPackageStats();

        public abstract SparseArray<? extends Pid> getPidStats();

        public abstract long getProcessStateTime(int i, long j, int i2);

        public abstract Timer getProcessStateTimer(int i);

        @UnsupportedAppUsage
        public abstract ArrayMap<String, ? extends Proc> getProcessStats();

        public abstract long[] getScreenOffCpuFreqTimes(int i);

        public abstract long[] getScreenOffCpuFreqTimes(int i, int i2);

        @UnsupportedAppUsage
        public abstract SparseArray<? extends Sensor> getSensorStats();

        public abstract ArrayMap<String, ? extends Timer> getSyncStats();

        public abstract long getSystemCpuTimeUs(int i);

        public abstract long getTimeAtCpuSpeed(int i, int i2, int i3);

        @UnsupportedAppUsage
        public abstract int getUid();

        public abstract int getUserActivityCount(int i, int i2);

        public abstract long getUserCpuTimeUs(int i);

        public abstract Timer getVibratorOnTimer();

        @UnsupportedAppUsage
        public abstract Timer getVideoTurnedOnTimer();

        @UnsupportedAppUsage
        public abstract ArrayMap<String, ? extends Wakelock> getWakelockStats();

        public abstract int getWifiBatchedScanCount(int i, int i2);

        @UnsupportedAppUsage
        public abstract long getWifiBatchedScanTime(int i, long j, int i2);

        public abstract ControllerActivityCounter getWifiControllerActivity();

        @UnsupportedAppUsage
        public abstract long getWifiMulticastTime(long j, int i);

        public abstract long getWifiRadioApWakeupCount(int i);

        @UnsupportedAppUsage
        public abstract long getWifiRunningTime(long j, int i);

        public abstract long getWifiScanActualTime(long j);

        public abstract int getWifiScanBackgroundCount(int i);

        public abstract long getWifiScanBackgroundTime(long j);

        public abstract Timer getWifiScanBackgroundTimer();

        public abstract int getWifiScanCount(int i);

        @UnsupportedAppUsage
        public abstract long getWifiScanTime(long j, int i);

        public abstract Timer getWifiScanTimer();

        public abstract boolean hasNetworkActivity();

        public abstract boolean hasUserActivity();

        public abstract void noteActivityPausedLocked(long j);

        public abstract void noteActivityResumedLocked(long j);

        public abstract void noteFullWifiLockAcquiredLocked(long j);

        public abstract void noteFullWifiLockReleasedLocked(long j);

        public abstract void noteUserActivityLocked(int i);

        public abstract void noteWifiBatchedScanStartedLocked(int i, long j);

        public abstract void noteWifiBatchedScanStoppedLocked(long j);

        public abstract void noteWifiMulticastDisabledLocked(long j);

        public abstract void noteWifiMulticastEnabledLocked(long j);

        public abstract void noteWifiRunningLocked(long j);

        public abstract void noteWifiScanStartedLocked(long j);

        public abstract void noteWifiScanStoppedLocked(long j);

        public abstract void noteWifiStoppedLocked(long j);

        public class Pid {
            public int mWakeNesting;
            public long mWakeStartMs;
            public long mWakeSumMs;

            public Pid() {
            }
        }
    }

    public static final class LevelStepTracker {
        public long mLastStepTime = -1;
        public int mNumStepDurations;
        public final long[] mStepDurations;

        public LevelStepTracker(int maxLevelSteps) {
            this.mStepDurations = new long[maxLevelSteps];
        }

        public LevelStepTracker(int numSteps, long[] steps) {
            this.mNumStepDurations = numSteps;
            this.mStepDurations = new long[numSteps];
            System.arraycopy(steps, 0, this.mStepDurations, 0, numSteps);
        }

        public long getDurationAt(int index) {
            return this.mStepDurations[index] & BatteryStats.STEP_LEVEL_TIME_MASK;
        }

        public int getLevelAt(int index) {
            return (int) ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_LEVEL_MASK) >> 40);
        }

        public int getInitModeAt(int index) {
            return (int) ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48);
        }

        public int getModModeAt(int index) {
            return (int) ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56);
        }

        private void appendHex(long val, int topOffset, StringBuilder out) {
            boolean hasData = false;
            while (topOffset >= 0) {
                int digit = (int) ((val >> topOffset) & 15);
                topOffset -= 4;
                if (hasData || digit != 0) {
                    hasData = true;
                    if (digit < 0 || digit > 9) {
                        out.append((char) ((digit + 97) - 10));
                    } else {
                        out.append((char) (digit + 48));
                    }
                }
            }
        }

        public void encodeEntryAt(int index, StringBuilder out) {
            long item = this.mStepDurations[index];
            long duration = BatteryStats.STEP_LEVEL_TIME_MASK & item;
            int level = (int) ((BatteryStats.STEP_LEVEL_LEVEL_MASK & item) >> 40);
            int initMode = (int) ((BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & item) >> 48);
            int modMode = (int) ((BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & item) >> 56);
            switch ((initMode & 3) + 1) {
                case 1:
                    out.append('f');
                    break;
                case 2:
                    out.append('o');
                    break;
                case 3:
                    out.append(android.text.format.DateFormat.DATE);
                    break;
                case 4:
                    out.append(android.text.format.DateFormat.TIME_ZONE);
                    break;
            }
            if ((initMode & 4) != 0) {
                out.append('p');
            }
            if ((initMode & 8) != 0) {
                out.append('i');
            }
            switch ((modMode & 3) + 1) {
                case 1:
                    out.append('F');
                    break;
                case 2:
                    out.append('O');
                    break;
                case 3:
                    out.append('D');
                    break;
                case 4:
                    out.append('Z');
                    break;
            }
            if ((modMode & 4) != 0) {
                out.append('P');
            }
            if ((modMode & 8) != 0) {
                out.append('I');
            }
            out.append('-');
            appendHex((long) level, 4, out);
            out.append('-');
            appendHex(duration, 36, out);
        }

        public void decodeEntryAt(int index, String value) {
            char c;
            char c2;
            long level;
            String str = value;
            int N = value.length();
            int i = 0;
            long out = 0;
            while (true) {
                c = '-';
                if (i < N) {
                    char charAt = str.charAt(i);
                    char c3 = charAt;
                    if (charAt != '-') {
                        i++;
                        switch (c3) {
                            case 'D':
                                out |= 144115188075855872L;
                                break;
                            case 'F':
                                out |= 0;
                                break;
                            case 'I':
                                out |= 576460752303423488L;
                                break;
                            case 'O':
                                out |= 72057594037927936L;
                                break;
                            case 'P':
                                out |= 288230376151711744L;
                                break;
                            case 'Z':
                                out |= 216172782113783808L;
                                break;
                            case 'd':
                                out |= 562949953421312L;
                                break;
                            case 'f':
                                out |= 0;
                                break;
                            case 'i':
                                out |= 2251799813685248L;
                                break;
                            case 'o':
                                out |= 281474976710656L;
                                break;
                            case 'p':
                                out |= TrafficStats.PB_IN_BYTES;
                                break;
                            case 'z':
                                out |= 844424930131968L;
                                break;
                        }
                    }
                }
            }
            int i2 = i + 1;
            long level2 = 0;
            while (true) {
                c2 = '0';
                if (i2 < N) {
                    char charAt2 = str.charAt(i2);
                    char c4 = charAt2;
                    if (charAt2 != '-') {
                        i2++;
                        level2 <<= 4;
                        char c5 = c4;
                        if (c5 >= '0' && c5 <= '9') {
                            level2 += (long) (c5 - '0');
                        } else if (c5 >= 'a' && c5 <= 'f') {
                            level2 += (long) ((c5 - 'a') + 10);
                        } else if (c5 >= 'A' && c5 <= 'F') {
                            level2 += (long) ((c5 - 'A') + 10);
                        }
                    }
                }
            }
            int i3 = i2 + 1;
            long out2 = out | ((level2 << 40) & BatteryStats.STEP_LEVEL_LEVEL_MASK);
            long duration = 0;
            while (i3 < N) {
                char charAt3 = str.charAt(i3);
                char c6 = charAt3;
                if (charAt3 != c) {
                    i3++;
                    duration <<= 4;
                    char c7 = c6;
                    if (c7 < c2 || c7 > '9') {
                        level = level2;
                        if (c7 >= 'a' && c7 <= 'f') {
                            duration += (long) ((c7 - 'a') + 10);
                        } else if (c7 >= 'A' && c7 <= 'F') {
                            duration += (long) ((c7 - 'A') + 10);
                        }
                    } else {
                        level = level2;
                        duration += (long) (c7 - '0');
                    }
                    level2 = level;
                    c2 = '0';
                    c = '-';
                } else {
                    this.mStepDurations[index] = (duration & BatteryStats.STEP_LEVEL_TIME_MASK) | out2;
                }
            }
            this.mStepDurations[index] = (duration & BatteryStats.STEP_LEVEL_TIME_MASK) | out2;
        }

        public void init() {
            this.mLastStepTime = -1;
            this.mNumStepDurations = 0;
        }

        public void clearTime() {
            this.mLastStepTime = -1;
        }

        public long computeTimePerLevel() {
            long[] steps = this.mStepDurations;
            int numSteps = this.mNumStepDurations;
            if (numSteps <= 0) {
                return -1;
            }
            long total = 0;
            for (int i = 0; i < numSteps; i++) {
                total += steps[i] & BatteryStats.STEP_LEVEL_TIME_MASK;
            }
            return total / ((long) numSteps);
        }

        public long computeTimeEstimate(long modesOfInterest, long modeValues, int[] outNumOfInterest) {
            long[] steps = this.mStepDurations;
            int count = this.mNumStepDurations;
            if (count <= 0) {
                return -1;
            }
            int numOfInterest = 0;
            long total = 0;
            for (int i = 0; i < count; i++) {
                long initMode = (steps[i] & BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48;
                if ((((steps[i] & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56) & modesOfInterest) == 0 && (initMode & modesOfInterest) == modeValues) {
                    numOfInterest++;
                    total += steps[i] & BatteryStats.STEP_LEVEL_TIME_MASK;
                }
            }
            if (numOfInterest <= 0) {
                return -1;
            }
            if (outNumOfInterest != null) {
                outNumOfInterest[0] = numOfInterest;
            }
            return (total / ((long) numOfInterest)) * 100;
        }

        public void addLevelSteps(int numStepLevels, long modeBits, long elapsedRealtime) {
            int i = numStepLevels;
            long j = elapsedRealtime;
            int stepCount = this.mNumStepDurations;
            long lastStepTime = this.mLastStepTime;
            if (lastStepTime >= 0 && i > 0) {
                long[] steps = this.mStepDurations;
                long duration = j - lastStepTime;
                for (int i2 = 0; i2 < i; i2++) {
                    System.arraycopy(steps, 0, steps, 1, steps.length - 1);
                    long thisDuration = duration / ((long) (i - i2));
                    duration -= thisDuration;
                    if (thisDuration > BatteryStats.STEP_LEVEL_TIME_MASK) {
                        thisDuration = BatteryStats.STEP_LEVEL_TIME_MASK;
                    }
                    steps[0] = thisDuration | modeBits;
                }
                stepCount += i;
                if (stepCount > steps.length) {
                    stepCount = steps.length;
                }
            }
            this.mNumStepDurations = stepCount;
            this.mLastStepTime = j;
        }

        public void readFromParcel(Parcel in) {
            int N = in.readInt();
            if (N <= this.mStepDurations.length) {
                this.mNumStepDurations = N;
                for (int i = 0; i < N; i++) {
                    this.mStepDurations[i] = in.readLong();
                }
                return;
            }
            throw new ParcelFormatException("more step durations than available: " + N);
        }

        public void writeToParcel(Parcel out) {
            int N = this.mNumStepDurations;
            out.writeInt(N);
            for (int i = 0; i < N; i++) {
                out.writeLong(this.mStepDurations[i]);
            }
        }
    }

    public static final class HistoryTag {
        public int poolIdx;
        public String string;
        public int uid;

        public void setTo(HistoryTag o) {
            this.string = o.string;
            this.uid = o.uid;
            this.poolIdx = o.poolIdx;
        }

        public void setTo(String _string, int _uid) {
            this.string = _string;
            this.uid = _uid;
            this.poolIdx = -1;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.string);
            dest.writeInt(this.uid);
        }

        public void readFromParcel(Parcel src) {
            this.string = src.readString();
            this.uid = src.readInt();
            this.poolIdx = -1;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HistoryTag that = (HistoryTag) o;
            if (this.uid == that.uid && this.string.equals(that.string)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.string.hashCode() * 31) + this.uid;
        }
    }

    public static final class HistoryStepDetails {
        public int appCpuSTime1;
        public int appCpuSTime2;
        public int appCpuSTime3;
        public int appCpuUTime1;
        public int appCpuUTime2;
        public int appCpuUTime3;
        public int appCpuUid1;
        public int appCpuUid2;
        public int appCpuUid3;
        public int statIOWaitTime;
        public int statIdlTime;
        public int statIrqTime;
        public String statPlatformIdleState;
        public int statSoftIrqTime;
        public String statSubsystemPowerState;
        public int statSystemTime;
        public int statUserTime;
        public int systemTime;
        public int userTime;

        public HistoryStepDetails() {
            clear();
        }

        public void clear() {
            this.systemTime = 0;
            this.userTime = 0;
            this.appCpuUid3 = -1;
            this.appCpuUid2 = -1;
            this.appCpuUid1 = -1;
            this.appCpuSTime3 = 0;
            this.appCpuUTime3 = 0;
            this.appCpuSTime2 = 0;
            this.appCpuUTime2 = 0;
            this.appCpuSTime1 = 0;
            this.appCpuUTime1 = 0;
        }

        public void writeToParcel(Parcel out) {
            out.writeInt(this.userTime);
            out.writeInt(this.systemTime);
            out.writeInt(this.appCpuUid1);
            out.writeInt(this.appCpuUTime1);
            out.writeInt(this.appCpuSTime1);
            out.writeInt(this.appCpuUid2);
            out.writeInt(this.appCpuUTime2);
            out.writeInt(this.appCpuSTime2);
            out.writeInt(this.appCpuUid3);
            out.writeInt(this.appCpuUTime3);
            out.writeInt(this.appCpuSTime3);
            out.writeInt(this.statUserTime);
            out.writeInt(this.statSystemTime);
            out.writeInt(this.statIOWaitTime);
            out.writeInt(this.statIrqTime);
            out.writeInt(this.statSoftIrqTime);
            out.writeInt(this.statIdlTime);
            out.writeString(this.statPlatformIdleState);
            out.writeString(this.statSubsystemPowerState);
        }

        public void readFromParcel(Parcel in) {
            this.userTime = in.readInt();
            this.systemTime = in.readInt();
            this.appCpuUid1 = in.readInt();
            this.appCpuUTime1 = in.readInt();
            this.appCpuSTime1 = in.readInt();
            this.appCpuUid2 = in.readInt();
            this.appCpuUTime2 = in.readInt();
            this.appCpuSTime2 = in.readInt();
            this.appCpuUid3 = in.readInt();
            this.appCpuUTime3 = in.readInt();
            this.appCpuSTime3 = in.readInt();
            this.statUserTime = in.readInt();
            this.statSystemTime = in.readInt();
            this.statIOWaitTime = in.readInt();
            this.statIrqTime = in.readInt();
            this.statSoftIrqTime = in.readInt();
            this.statIdlTime = in.readInt();
            this.statPlatformIdleState = in.readString();
            this.statSubsystemPowerState = in.readString();
        }
    }

    public static final class HistoryItem implements Parcelable {
        public static final byte CMD_CURRENT_TIME = 5;
        public static final byte CMD_NULL = -1;
        public static final byte CMD_OVERFLOW = 6;
        public static final byte CMD_RESET = 7;
        public static final byte CMD_SHUTDOWN = 8;
        public static final byte CMD_START = 4;
        @UnsupportedAppUsage
        public static final byte CMD_UPDATE = 0;
        public static final int EVENT_ACTIVE = 10;
        public static final int EVENT_ALARM = 13;
        public static final int EVENT_ALARM_FINISH = 16397;
        public static final int EVENT_ALARM_START = 32781;
        public static final int EVENT_COLLECT_EXTERNAL_STATS = 14;
        public static final int EVENT_CONNECTIVITY_CHANGED = 9;
        public static final int EVENT_COUNT = 22;
        public static final int EVENT_FLAG_FINISH = 16384;
        public static final int EVENT_FLAG_START = 32768;
        public static final int EVENT_FOREGROUND = 2;
        public static final int EVENT_FOREGROUND_FINISH = 16386;
        public static final int EVENT_FOREGROUND_START = 32770;
        public static final int EVENT_JOB = 6;
        public static final int EVENT_JOB_FINISH = 16390;
        public static final int EVENT_JOB_START = 32774;
        public static final int EVENT_LONG_WAKE_LOCK = 20;
        public static final int EVENT_LONG_WAKE_LOCK_FINISH = 16404;
        public static final int EVENT_LONG_WAKE_LOCK_START = 32788;
        public static final int EVENT_NONE = 0;
        public static final int EVENT_PACKAGE_ACTIVE = 16;
        public static final int EVENT_PACKAGE_INACTIVE = 15;
        public static final int EVENT_PACKAGE_INSTALLED = 11;
        public static final int EVENT_PACKAGE_UNINSTALLED = 12;
        public static final int EVENT_PROC = 1;
        public static final int EVENT_PROC_FINISH = 16385;
        public static final int EVENT_PROC_START = 32769;
        public static final int EVENT_SCREEN_WAKE_UP = 18;
        public static final int EVENT_SYNC = 4;
        public static final int EVENT_SYNC_FINISH = 16388;
        public static final int EVENT_SYNC_START = 32772;
        public static final int EVENT_TEMP_WHITELIST = 17;
        public static final int EVENT_TEMP_WHITELIST_FINISH = 16401;
        public static final int EVENT_TEMP_WHITELIST_START = 32785;
        public static final int EVENT_TOP = 3;
        public static final int EVENT_TOP_FINISH = 16387;
        public static final int EVENT_TOP_START = 32771;
        public static final int EVENT_TYPE_MASK = -49153;
        public static final int EVENT_USER_FOREGROUND = 8;
        public static final int EVENT_USER_FOREGROUND_FINISH = 16392;
        public static final int EVENT_USER_FOREGROUND_START = 32776;
        public static final int EVENT_USER_RUNNING = 7;
        public static final int EVENT_USER_RUNNING_FINISH = 16391;
        public static final int EVENT_USER_RUNNING_START = 32775;
        public static final int EVENT_WAKEUP_AP = 19;
        public static final int EVENT_WAKE_LOCK = 5;
        public static final int EVENT_WAKE_LOCK_FINISH = 16389;
        public static final int EVENT_WAKE_LOCK_START = 32773;
        public static final int MOST_INTERESTING_STATES = 1835008;
        public static final int MOST_INTERESTING_STATES2 = -1749024768;
        public static final int SETTLE_TO_ZERO_STATES = -1900544;
        public static final int SETTLE_TO_ZERO_STATES2 = 1748959232;
        public static final int STATE2_BLUETOOTH_ON_FLAG = 4194304;
        public static final int STATE2_BLUETOOTH_SCAN_FLAG = 1048576;
        public static final int STATE2_CAMERA_FLAG = 2097152;
        public static final int STATE2_CELLULAR_HIGH_TX_POWER_FLAG = 524288;
        public static final int STATE2_CHARGING_FLAG = 16777216;
        public static final int STATE2_DEVICE_IDLE_MASK = 100663296;
        public static final int STATE2_DEVICE_IDLE_SHIFT = 25;
        public static final int STATE2_FLASHLIGHT_FLAG = 134217728;
        public static final int STATE2_GPS_SIGNAL_QUALITY_MASK = 128;
        public static final int STATE2_GPS_SIGNAL_QUALITY_SHIFT = 7;
        public static final int STATE2_PHONE_IN_CALL_FLAG = 8388608;
        public static final int STATE2_POWER_SAVE_FLAG = Integer.MIN_VALUE;
        public static final int STATE2_USB_DATA_LINK_FLAG = 262144;
        public static final int STATE2_VIDEO_ON_FLAG = 1073741824;
        public static final int STATE2_WIFI_ON_FLAG = 268435456;
        public static final int STATE2_WIFI_RUNNING_FLAG = 536870912;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_MASK = 112;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_SHIFT = 4;
        public static final int STATE2_WIFI_SUPPL_STATE_MASK = 15;
        public static final int STATE2_WIFI_SUPPL_STATE_SHIFT = 0;
        public static final int STATE_AUDIO_ON_FLAG = 4194304;
        public static final int STATE_BATTERY_PLUGGED_FLAG = 524288;
        public static final int STATE_BRIGHTNESS_MASK = 7;
        public static final int STATE_BRIGHTNESS_SHIFT = 0;
        public static final int STATE_CPU_RUNNING_FLAG = Integer.MIN_VALUE;
        public static final int STATE_DATA_CONNECTION_MASK = 15872;
        public static final int STATE_DATA_CONNECTION_SHIFT = 9;
        public static final int STATE_GPS_ON_FLAG = 536870912;
        public static final int STATE_MOBILE_RADIO_ACTIVE_FLAG = 33554432;
        public static final int STATE_PHONE_SCANNING_FLAG = 2097152;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_MASK = 56;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_SHIFT = 3;
        public static final int STATE_PHONE_STATE_MASK = 448;
        public static final int STATE_PHONE_STATE_SHIFT = 6;
        private static final int STATE_RESERVED_0 = 16777216;
        public static final int STATE_SCREEN_DOZE_FLAG = 262144;
        public static final int STATE_SCREEN_ON_FLAG = 1048576;
        public static final int STATE_SENSOR_ON_FLAG = 8388608;
        public static final int STATE_WAKE_LOCK_FLAG = 1073741824;
        public static final int STATE_WIFI_FULL_LOCK_FLAG = 268435456;
        public static final int STATE_WIFI_MULTICAST_ON_FLAG = 65536;
        public static final int STATE_WIFI_RADIO_ACTIVE_FLAG = 67108864;
        public static final int STATE_WIFI_SCAN_FLAG = 134217728;
        public int batteryChargeUAh;
        @UnsupportedAppUsage
        public byte batteryHealth;
        @UnsupportedAppUsage
        public byte batteryLevel;
        @UnsupportedAppUsage
        public byte batteryPlugType;
        @UnsupportedAppUsage
        public byte batteryStatus;
        public short batteryTemperature;
        @UnsupportedAppUsage
        public char batteryVoltage;
        @UnsupportedAppUsage
        public byte cmd = -1;
        public long currentTime;
        public int eventCode;
        public HistoryTag eventTag;
        public final HistoryTag localEventTag = new HistoryTag();
        public final HistoryTag localWakeReasonTag = new HistoryTag();
        public final HistoryTag localWakelockTag = new HistoryTag();
        public double modemRailChargeMah;
        public HistoryItem next;
        public int numReadInts;
        @UnsupportedAppUsage
        public int states;
        @UnsupportedAppUsage
        public int states2;
        public HistoryStepDetails stepDetails;
        @UnsupportedAppUsage
        public long time;
        public HistoryTag wakeReasonTag;
        public HistoryTag wakelockTag;
        public double wifiRailChargeMah;

        public boolean isDeltaData() {
            return this.cmd == 0;
        }

        @UnsupportedAppUsage
        public HistoryItem() {
        }

        public HistoryItem(long time2, Parcel src) {
            this.time = time2;
            this.numReadInts = 2;
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.time);
            int i = 0;
            int i2 = (this.cmd & 255) | ((this.batteryLevel << 8) & 65280) | ((this.batteryStatus << WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) & SurfaceControl.FX_SURFACE_MASK) | ((this.batteryHealth << 20) & 15728640) | ((this.batteryPlugType << 24) & 251658240) | (this.wakelockTag != null ? 268435456 : 0) | (this.wakeReasonTag != null ? 536870912 : 0);
            if (this.eventCode != 0) {
                i = 1073741824;
            }
            dest.writeInt(i2 | i);
            dest.writeInt((this.batteryTemperature & 65535) | ((this.batteryVoltage << 16) & -65536));
            dest.writeInt(this.batteryChargeUAh);
            dest.writeDouble(this.modemRailChargeMah);
            dest.writeDouble(this.wifiRailChargeMah);
            dest.writeInt(this.states);
            dest.writeInt(this.states2);
            if (this.wakelockTag != null) {
                this.wakelockTag.writeToParcel(dest, flags);
            }
            if (this.wakeReasonTag != null) {
                this.wakeReasonTag.writeToParcel(dest, flags);
            }
            if (this.eventCode != 0) {
                dest.writeInt(this.eventCode);
                this.eventTag.writeToParcel(dest, flags);
            }
            if (this.cmd == 5 || this.cmd == 7) {
                dest.writeLong(this.currentTime);
            }
        }

        public void readFromParcel(Parcel src) {
            int start = src.dataPosition();
            int bat = src.readInt();
            this.cmd = (byte) (bat & 255);
            this.batteryLevel = (byte) ((bat >> 8) & 255);
            this.batteryStatus = (byte) ((bat >> 16) & 15);
            this.batteryHealth = (byte) ((bat >> 20) & 15);
            this.batteryPlugType = (byte) ((bat >> 24) & 15);
            int bat2 = src.readInt();
            this.batteryTemperature = (short) (bat2 & 65535);
            this.batteryVoltage = (char) (65535 & (bat2 >> 16));
            this.batteryChargeUAh = src.readInt();
            this.modemRailChargeMah = src.readDouble();
            this.wifiRailChargeMah = src.readDouble();
            this.states = src.readInt();
            this.states2 = src.readInt();
            if ((268435456 & bat) != 0) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.readFromParcel(src);
            } else {
                this.wakelockTag = null;
            }
            if ((536870912 & bat) != 0) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.readFromParcel(src);
            } else {
                this.wakeReasonTag = null;
            }
            if ((1073741824 & bat) != 0) {
                this.eventCode = src.readInt();
                this.eventTag = this.localEventTag;
                this.eventTag.readFromParcel(src);
            } else {
                this.eventCode = 0;
                this.eventTag = null;
            }
            if (this.cmd == 5 || this.cmd == 7) {
                this.currentTime = src.readLong();
            } else {
                this.currentTime = 0;
            }
            this.numReadInts += (src.dataPosition() - start) / 4;
        }

        public void clear() {
            this.time = 0;
            this.cmd = -1;
            this.batteryLevel = 0;
            this.batteryStatus = 0;
            this.batteryHealth = 0;
            this.batteryPlugType = 0;
            this.batteryTemperature = 0;
            this.batteryVoltage = 0;
            this.batteryChargeUAh = 0;
            this.modemRailChargeMah = 0.0d;
            this.wifiRailChargeMah = 0.0d;
            this.states = 0;
            this.states2 = 0;
            this.wakelockTag = null;
            this.wakeReasonTag = null;
            this.eventCode = 0;
            this.eventTag = null;
        }

        public void setTo(HistoryItem o) {
            this.time = o.time;
            this.cmd = o.cmd;
            setToCommon(o);
        }

        public void setTo(long time2, byte cmd2, HistoryItem o) {
            this.time = time2;
            this.cmd = cmd2;
            setToCommon(o);
        }

        private void setToCommon(HistoryItem o) {
            this.batteryLevel = o.batteryLevel;
            this.batteryStatus = o.batteryStatus;
            this.batteryHealth = o.batteryHealth;
            this.batteryPlugType = o.batteryPlugType;
            this.batteryTemperature = o.batteryTemperature;
            this.batteryVoltage = o.batteryVoltage;
            this.batteryChargeUAh = o.batteryChargeUAh;
            this.modemRailChargeMah = o.modemRailChargeMah;
            this.wifiRailChargeMah = o.wifiRailChargeMah;
            this.states = o.states;
            this.states2 = o.states2;
            if (o.wakelockTag != null) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.setTo(o.wakelockTag);
            } else {
                this.wakelockTag = null;
            }
            if (o.wakeReasonTag != null) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.setTo(o.wakeReasonTag);
            } else {
                this.wakeReasonTag = null;
            }
            this.eventCode = o.eventCode;
            if (o.eventTag != null) {
                this.eventTag = this.localEventTag;
                this.eventTag.setTo(o.eventTag);
            } else {
                this.eventTag = null;
            }
            this.currentTime = o.currentTime;
        }

        public boolean sameNonEvent(HistoryItem o) {
            return this.batteryLevel == o.batteryLevel && this.batteryStatus == o.batteryStatus && this.batteryHealth == o.batteryHealth && this.batteryPlugType == o.batteryPlugType && this.batteryTemperature == o.batteryTemperature && this.batteryVoltage == o.batteryVoltage && this.batteryChargeUAh == o.batteryChargeUAh && this.modemRailChargeMah == o.modemRailChargeMah && this.wifiRailChargeMah == o.wifiRailChargeMah && this.states == o.states && this.states2 == o.states2 && this.currentTime == o.currentTime;
        }

        public boolean same(HistoryItem o) {
            if (!sameNonEvent(o) || this.eventCode != o.eventCode) {
                return false;
            }
            if (this.wakelockTag != o.wakelockTag && (this.wakelockTag == null || o.wakelockTag == null || !this.wakelockTag.equals(o.wakelockTag))) {
                return false;
            }
            if (this.wakeReasonTag != o.wakeReasonTag && (this.wakeReasonTag == null || o.wakeReasonTag == null || !this.wakeReasonTag.equals(o.wakeReasonTag))) {
                return false;
            }
            if (this.eventTag == o.eventTag) {
                return true;
            }
            if (this.eventTag == null || o.eventTag == null || !this.eventTag.equals(o.eventTag)) {
                return false;
            }
            return true;
        }
    }

    public static final class HistoryEventTracker {
        private final HashMap<String, SparseIntArray>[] mActiveEvents = new HashMap[22];

        public boolean updateState(int code, String name, int uid, int poolIdx) {
            SparseIntArray uids;
            int idx;
            if ((32768 & code) != 0) {
                int idx2 = code & HistoryItem.EVENT_TYPE_MASK;
                HashMap<String, SparseIntArray> active = this.mActiveEvents[idx2];
                if (active == null) {
                    active = new HashMap<>();
                    this.mActiveEvents[idx2] = active;
                }
                SparseIntArray uids2 = active.get(name);
                if (uids2 == null) {
                    uids2 = new SparseIntArray();
                    active.put(name, uids2);
                }
                if (uids2.indexOfKey(uid) >= 0) {
                    return false;
                }
                uids2.put(uid, poolIdx);
                return true;
            } else if ((code & 16384) == 0) {
                return true;
            } else {
                HashMap<String, SparseIntArray> active2 = this.mActiveEvents[code & HistoryItem.EVENT_TYPE_MASK];
                if (active2 == null || (uids = active2.get(name)) == null || (idx = uids.indexOfKey(uid)) < 0) {
                    return false;
                }
                uids.removeAt(idx);
                if (uids.size() > 0) {
                    return true;
                }
                active2.remove(name);
                return true;
            }
        }

        public void removeEvents(int code) {
            this.mActiveEvents[-49153 & code] = null;
        }

        public HashMap<String, SparseIntArray> getStateForEvent(int code) {
            return this.mActiveEvents[code];
        }
    }

    public static final class BitDescription {
        public final int mask;
        public final String name;
        public final int shift;
        public final String shortName;
        public final String[] shortValues;
        public final String[] values;

        public BitDescription(int mask2, String name2, String shortName2) {
            this.mask = mask2;
            this.shift = -1;
            this.name = name2;
            this.shortName = shortName2;
            this.values = null;
            this.shortValues = null;
        }

        public BitDescription(int mask2, int shift2, String name2, String shortName2, String[] values2, String[] shortValues2) {
            this.mask = mask2;
            this.shift = shift2;
            this.name = name2;
            this.shortName = shortName2;
            this.values = values2;
            this.shortValues = shortValues2;
        }
    }

    private static final void formatTimeRaw(StringBuilder out, long seconds) {
        long days = seconds / 86400;
        if (days != 0) {
            out.append(days);
            out.append("d ");
        }
        long used = days * 60 * 60 * 24;
        long hours = (seconds - used) / 3600;
        if (!(hours == 0 && used == 0)) {
            out.append(hours);
            out.append("h ");
        }
        long used2 = used + (hours * 60 * 60);
        long mins = (seconds - used2) / 60;
        if (!(mins == 0 && used2 == 0)) {
            out.append(mins);
            out.append("m ");
        }
        long used3 = used2 + (60 * mins);
        if (seconds != 0 || used3 != 0) {
            out.append(seconds - used3);
            out.append("s ");
        }
    }

    public static final void formatTimeMs(StringBuilder sb, long time) {
        long sec = time / 1000;
        formatTimeRaw(sb, sec);
        sb.append(time - (1000 * sec));
        sb.append("ms ");
    }

    public static final void formatTimeMsNoSpace(StringBuilder sb, long time) {
        long sec = time / 1000;
        formatTimeRaw(sb, sec);
        sb.append(time - (1000 * sec));
        sb.append(DateFormat.MINUTE_SECOND);
    }

    public final String formatRatioLocked(long num, long den) {
        if (den == 0) {
            return "--%";
        }
        this.mFormatBuilder.setLength(0);
        this.mFormatter.format("%.1f%%", new Object[]{Float.valueOf((((float) num) / ((float) den)) * 100.0f)});
        return this.mFormatBuilder.toString();
    }

    /* access modifiers changed from: package-private */
    public final String formatBytesLocked(long bytes) {
        this.mFormatBuilder.setLength(0);
        if (bytes < 1024) {
            return bytes + "B";
        } else if (bytes < 1048576) {
            this.mFormatter.format("%.2fKB", new Object[]{Double.valueOf(((double) bytes) / 1024.0d)});
            return this.mFormatBuilder.toString();
        } else if (bytes < 1073741824) {
            this.mFormatter.format("%.2fMB", new Object[]{Double.valueOf(((double) bytes) / 1048576.0d)});
            return this.mFormatBuilder.toString();
        } else {
            this.mFormatter.format("%.2fGB", new Object[]{Double.valueOf(((double) bytes) / 1.073741824E9d)});
            return this.mFormatBuilder.toString();
        }
    }

    private static long roundUsToMs(long timeUs) {
        return (500 + timeUs) / 1000;
    }

    private static long computeWakeLock(Timer timer, long elapsedRealtimeUs, int which) {
        if (timer != null) {
            return (500 + timer.getTotalTimeLocked(elapsedRealtimeUs, which)) / 1000;
        }
        return 0;
    }

    private static final String printWakeLock(StringBuilder sb, Timer timer, long elapsedRealtimeUs, String name, int which, String linePrefix) {
        StringBuilder sb2 = sb;
        Timer timer2 = timer;
        long j = elapsedRealtimeUs;
        String str = name;
        int i = which;
        String str2 = linePrefix;
        if (timer2 != null) {
            long totalTimeMillis = computeWakeLock(timer2, j, i);
            int count = timer2.getCountLocked(i);
            if (totalTimeMillis != 0) {
                sb2.append(str2);
                formatTimeMs(sb2, totalTimeMillis);
                if (str != null) {
                    sb2.append(str);
                    sb2.append(' ');
                }
                sb2.append('(');
                sb2.append(count);
                sb2.append(" times)");
                long maxDurationMs = timer2.getMaxDurationMsLocked(j / 1000);
                if (maxDurationMs >= 0) {
                    sb2.append(" max=");
                    sb2.append(maxDurationMs);
                }
                long totalDurMs = timer2.getTotalDurationMsLocked(j / 1000);
                if (totalDurMs > totalTimeMillis) {
                    sb2.append(" actual=");
                    sb2.append(totalDurMs);
                }
                if (!timer.isRunningLocked()) {
                    return ", ";
                }
                long currentMs = timer2.getCurrentDurationMsLocked(j / 1000);
                if (currentMs >= 0) {
                    sb2.append(" (running for ");
                    sb2.append(currentMs);
                    sb2.append("ms)");
                    return ", ";
                }
                sb2.append(" (running)");
                return ", ";
            }
        }
        return str2;
    }

    private static final boolean printTimer(PrintWriter pw, StringBuilder sb, Timer timer, long rawRealtimeUs, int which, String prefix, String type) {
        StringBuilder sb2 = sb;
        Timer timer2 = timer;
        if (timer2 != null) {
            long totalTimeMs = (timer.getTotalTimeLocked(rawRealtimeUs, which) + 500) / 1000;
            int count = timer2.getCountLocked(which);
            if (totalTimeMs != 0) {
                sb2.setLength(0);
                sb2.append(prefix);
                sb2.append("    ");
                sb2.append(type);
                sb2.append(PluralRules.KEYWORD_RULE_SEPARATOR);
                formatTimeMs(sb2, totalTimeMs);
                sb2.append("realtime (");
                sb2.append(count);
                sb2.append(" times)");
                long maxDurationMs = timer2.getMaxDurationMsLocked(rawRealtimeUs / 1000);
                if (maxDurationMs >= 0) {
                    sb2.append(" max=");
                    sb2.append(maxDurationMs);
                }
                if (timer.isRunningLocked()) {
                    long currentMs = timer2.getCurrentDurationMsLocked(rawRealtimeUs / 1000);
                    if (currentMs >= 0) {
                        sb2.append(" (running for ");
                        sb2.append(currentMs);
                        sb2.append("ms)");
                    } else {
                        sb2.append(" (running)");
                    }
                }
                PrintWriter printWriter = pw;
                pw.println(sb.toString());
                return true;
            }
            PrintWriter printWriter2 = pw;
        } else {
            PrintWriter printWriter3 = pw;
            int i = which;
        }
        String str = prefix;
        String str2 = type;
        return false;
    }

    private static final String printWakeLockCheckin(StringBuilder sb, Timer timer, long elapsedRealtimeUs, String name, int which, String linePrefix) {
        long totalTimeMicros;
        String str;
        StringBuilder sb2 = sb;
        Timer timer2 = timer;
        long j = elapsedRealtimeUs;
        String str2 = name;
        int i = which;
        int count = 0;
        long max = 0;
        long current = 0;
        long totalDuration = 0;
        if (timer2 != null) {
            long totalTimeMicros2 = timer2.getTotalTimeLocked(j, i);
            count = timer2.getCountLocked(i);
            totalTimeMicros = totalTimeMicros2;
            current = timer2.getCurrentDurationMsLocked(j / 1000);
            max = timer2.getMaxDurationMsLocked(j / 1000);
            totalDuration = timer2.getTotalDurationMsLocked(j / 1000);
        } else {
            totalTimeMicros = 0;
        }
        sb2.append(linePrefix);
        sb2.append((totalTimeMicros + 500) / 1000);
        sb2.append(',');
        if (str2 != null) {
            str = str2 + SmsManager.REGEX_PREFIX_DELIMITER;
        } else {
            str = "";
        }
        sb2.append(str);
        sb2.append(count);
        sb2.append(',');
        sb2.append(current);
        sb2.append(',');
        sb2.append(max);
        if (str2 == null) {
            return SmsManager.REGEX_PREFIX_DELIMITER;
        }
        sb2.append(',');
        sb2.append(totalDuration);
        return SmsManager.REGEX_PREFIX_DELIMITER;
    }

    private static final void dumpLineHeader(PrintWriter pw, int uid, String category, String type) {
        pw.print(9);
        pw.print(',');
        pw.print(uid);
        pw.print(',');
        pw.print(category);
        pw.print(',');
        pw.print(type);
    }

    @UnsupportedAppUsage
    private static final void dumpLine(PrintWriter pw, int uid, String category, String type, Object... args) {
        dumpLineHeader(pw, uid, category, type);
        for (Object arg : args) {
            pw.print(',');
            pw.print(arg);
        }
        pw.println();
    }

    private static final void dumpTimer(PrintWriter pw, int uid, String category, String type, Timer timer, long rawRealtime, int which) {
        if (timer != null) {
            long totalTime = roundUsToMs(timer.getTotalTimeLocked(rawRealtime, which));
            int count = timer.getCountLocked(which);
            if (totalTime != 0 || count != 0) {
                dumpLine(pw, uid, category, type, Long.valueOf(totalTime), Integer.valueOf(count));
            }
        }
    }

    private static void dumpTimer(ProtoOutputStream proto, long fieldId, Timer timer, long rawRealtimeUs, int which) {
        ProtoOutputStream protoOutputStream = proto;
        Timer timer2 = timer;
        if (timer2 != null) {
            long timeMs = roundUsToMs(timer.getTotalTimeLocked(rawRealtimeUs, which));
            int count = timer2.getCountLocked(which);
            long maxDurationMs = timer2.getMaxDurationMsLocked(rawRealtimeUs / 1000);
            long curDurationMs = timer2.getCurrentDurationMsLocked(rawRealtimeUs / 1000);
            long totalDurationMs = timer2.getTotalDurationMsLocked(rawRealtimeUs / 1000);
            if (timeMs != 0 || count != 0 || maxDurationMs != -1 || curDurationMs != -1 || totalDurationMs != -1) {
                long token = proto.start(fieldId);
                protoOutputStream.write(1112396529665L, timeMs);
                protoOutputStream.write(1112396529666L, count);
                if (maxDurationMs != -1) {
                    protoOutputStream.write(1112396529667L, maxDurationMs);
                }
                if (curDurationMs != -1) {
                    protoOutputStream.write(1112396529668L, curDurationMs);
                }
                if (totalDurationMs != -1) {
                    protoOutputStream.write(1112396529669L, totalDurationMs);
                }
                protoOutputStream.end(token);
            }
        }
    }

    private static boolean controllerActivityHasData(ControllerActivityCounter counter, int which) {
        if (counter == null) {
            return false;
        }
        if (counter.getIdleTimeCounter().getCountLocked(which) != 0 || counter.getRxTimeCounter().getCountLocked(which) != 0 || counter.getPowerCounter().getCountLocked(which) != 0 || counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which) != 0) {
            return true;
        }
        for (LongCounter c : counter.getTxTimeCounters()) {
            if (c.getCountLocked(which) != 0) {
                return true;
            }
        }
        return false;
    }

    private static final void dumpControllerActivityLine(PrintWriter pw, int uid, String category, String type, ControllerActivityCounter counter, int which) {
        if (controllerActivityHasData(counter, which)) {
            dumpLineHeader(pw, uid, category, type);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(counter.getIdleTimeCounter().getCountLocked(which));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(counter.getRxTimeCounter().getCountLocked(which));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(((double) counter.getPowerCounter().getCountLocked(which)) / 3600000.0d);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(((double) counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which)) / 3600000.0d);
            for (LongCounter c : counter.getTxTimeCounters()) {
                pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
                pw.print(c.getCountLocked(which));
            }
            pw.println();
        }
    }

    private static void dumpControllerActivityProto(ProtoOutputStream proto, long fieldId, ControllerActivityCounter counter, int which) {
        if (controllerActivityHasData(counter, which)) {
            long cToken = proto.start(fieldId);
            proto.write(1112396529665L, counter.getIdleTimeCounter().getCountLocked(which));
            proto.write(1112396529666L, counter.getRxTimeCounter().getCountLocked(which));
            proto.write(1112396529667L, ((double) counter.getPowerCounter().getCountLocked(which)) / 3600000.0d);
            proto.write(1103806595077L, ((double) counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which)) / 3600000.0d);
            LongCounter[] txCounters = counter.getTxTimeCounters();
            for (int i = 0; i < txCounters.length; i++) {
                LongCounter c = txCounters[i];
                long tToken = proto.start(2246267895812L);
                proto.write(1120986464257L, i);
                proto.write(1112396529666L, c.getCountLocked(which));
                proto.end(tToken);
            }
            proto.end(cToken);
        }
    }

    private final void printControllerActivityIfInteresting(PrintWriter pw, StringBuilder sb, String prefix, String controllerName, ControllerActivityCounter counter, int which) {
        if (controllerActivityHasData(counter, which)) {
            printControllerActivity(pw, sb, prefix, controllerName, counter, which);
        }
    }

    private final void printControllerActivity(PrintWriter pw, StringBuilder sb, String prefix, String controllerName, ControllerActivityCounter counter, int which) {
        String[] powerLevel;
        PrintWriter printWriter = pw;
        StringBuilder sb2 = sb;
        String str = controllerName;
        int i = which;
        long idleTimeMs = counter.getIdleTimeCounter().getCountLocked(i);
        long rxTimeMs = counter.getRxTimeCounter().getCountLocked(i);
        long powerDrainMaMs = counter.getPowerCounter().getCountLocked(i);
        long monitoredRailChargeConsumedMaMs = counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i);
        long totalControllerActivityTimeMs = computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000, i) / 1000;
        long totalTxTimeMs = 0;
        LongCounter[] txTimeCounters = counter.getTxTimeCounters();
        long monitoredRailChargeConsumedMaMs2 = monitoredRailChargeConsumedMaMs;
        int i2 = 0;
        for (int length = txTimeCounters.length; i2 < length; length = length) {
            totalTxTimeMs += txTimeCounters[i2].getCountLocked(i);
            i2++;
        }
        if (str.equals(WIFI_CONTROLLER_NAME)) {
            long scanTimeMs = counter.getScanTimeCounter().getCountLocked(i);
            sb2.setLength(0);
            sb.append(prefix);
            sb2.append("     ");
            sb2.append(str);
            sb2.append(" Scan time:  ");
            formatTimeMs(sb2, scanTimeMs);
            sb2.append("(");
            sb2.append(formatRatioLocked(scanTimeMs, totalControllerActivityTimeMs));
            sb2.append(")");
            printWriter.println(sb.toString());
            long j = scanTimeMs;
            long sleepTimeMs = totalControllerActivityTimeMs - ((idleTimeMs + rxTimeMs) + totalTxTimeMs);
            sb2.setLength(0);
            sb.append(prefix);
            sb2.append("     ");
            sb2.append(str);
            sb2.append(" Sleep time:  ");
            formatTimeMs(sb2, sleepTimeMs);
            sb2.append("(");
            sb2.append(formatRatioLocked(sleepTimeMs, totalControllerActivityTimeMs));
            sb2.append(")");
            printWriter.println(sb.toString());
        }
        if (str.equals(CELLULAR_CONTROLLER_NAME)) {
            long sleepTimeMs2 = counter.getSleepTimeCounter().getCountLocked(i);
            sb2.setLength(0);
            sb.append(prefix);
            sb2.append("     ");
            sb2.append(str);
            sb2.append(" Sleep time:  ");
            formatTimeMs(sb2, sleepTimeMs2);
            sb2.append("(");
            sb2.append(formatRatioLocked(sleepTimeMs2, totalControllerActivityTimeMs));
            sb2.append(")");
            printWriter.println(sb.toString());
        }
        sb2.setLength(0);
        sb.append(prefix);
        sb2.append("     ");
        sb2.append(str);
        sb2.append(" Idle time:   ");
        formatTimeMs(sb2, idleTimeMs);
        sb2.append("(");
        sb2.append(formatRatioLocked(idleTimeMs, totalControllerActivityTimeMs));
        sb2.append(")");
        printWriter.println(sb.toString());
        sb2.setLength(0);
        sb.append(prefix);
        sb2.append("     ");
        sb2.append(str);
        sb2.append(" Rx time:     ");
        formatTimeMs(sb2, rxTimeMs);
        sb2.append("(");
        sb2.append(formatRatioLocked(rxTimeMs, totalControllerActivityTimeMs));
        sb2.append(")");
        printWriter.println(sb.toString());
        sb2.setLength(0);
        sb.append(prefix);
        sb2.append("     ");
        sb2.append(str);
        sb2.append(" Tx time:     ");
        char c = 65535;
        if (controllerName.hashCode() == -851952246 && str.equals(CELLULAR_CONTROLLER_NAME)) {
            c = 0;
        }
        if (c != 0) {
            long j2 = idleTimeMs;
            powerLevel = new String[]{"[0]", "[1]", "[2]", "[3]", "[4]"};
        } else {
            powerLevel = new String[]{"   less than 0dBm: ", "   0dBm to 8dBm: ", "   8dBm to 15dBm: ", "   15dBm to 20dBm: ", "   above 20dBm: "};
        }
        int numTxLvls = Math.min(counter.getTxTimeCounters().length, powerLevel.length);
        if (numTxLvls > 1) {
            printWriter.println(sb.toString());
            int lvl = 0;
            while (lvl < numTxLvls) {
                int numTxLvls2 = numTxLvls;
                long txLvlTimeMs = counter.getTxTimeCounters()[lvl].getCountLocked(i);
                sb2.setLength(0);
                sb.append(prefix);
                sb2.append("    ");
                sb2.append(powerLevel[lvl]);
                sb2.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                formatTimeMs(sb2, txLvlTimeMs);
                sb2.append("(");
                sb2.append(formatRatioLocked(txLvlTimeMs, totalControllerActivityTimeMs));
                sb2.append(")");
                printWriter.println(sb.toString());
                lvl++;
                rxTimeMs = rxTimeMs;
                numTxLvls = numTxLvls2;
            }
            long j3 = rxTimeMs;
        } else {
            long j4 = rxTimeMs;
            long txLvlTimeMs2 = counter.getTxTimeCounters()[0].getCountLocked(i);
            formatTimeMs(sb2, txLvlTimeMs2);
            sb2.append("(");
            sb2.append(formatRatioLocked(txLvlTimeMs2, totalControllerActivityTimeMs));
            sb2.append(")");
            printWriter.println(sb.toString());
        }
        if (powerDrainMaMs > 0) {
            sb2.setLength(0);
            sb.append(prefix);
            sb2.append("     ");
            sb2.append(str);
            sb2.append(" Battery drain: ");
            sb2.append(BatteryStatsHelper.makemAh(((double) powerDrainMaMs) / 3600000.0d));
            sb2.append("mAh");
            printWriter.println(sb.toString());
        }
        if (monitoredRailChargeConsumedMaMs2 > 0) {
            sb2.setLength(0);
            sb.append(prefix);
            sb2.append("     ");
            sb2.append(str);
            sb2.append(" Monitored rail energy drain: ");
            sb2.append(new DecimalFormat("#.##").format(((double) monitoredRailChargeConsumedMaMs2) / 3600000.0d));
            sb2.append(" mAh");
            printWriter.println(sb.toString());
            return;
        }
    }

    public final void dumpCheckinLocked(Context context, PrintWriter pw, int which, int reqUid) {
        dumpCheckinLocked(context, pw, which, reqUid, BatteryStatsHelper.checkWifiOnly(context));
    }

    /* JADX WARNING: Removed duplicated region for block: B:228:0x0bf2  */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x0c1d  */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x0c4f  */
    /* JADX WARNING: Removed duplicated region for block: B:249:0x0c60  */
    /* JADX WARNING: Removed duplicated region for block: B:479:0x0d11 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dumpCheckinLocked(android.content.Context r237, java.io.PrintWriter r238, int r239, int r240, boolean r241) {
        /*
            r236 = this;
            r0 = r236
            r9 = r238
            r10 = r239
            r11 = r240
            r12 = 1
            r13 = 0
            if (r10 == 0) goto L_0x0030
            java.lang.String[] r1 = STAT_NAMES
            r1 = r1[r10]
            java.lang.String r2 = "err"
            java.lang.Object[] r3 = new java.lang.Object[r12]
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "ERROR: BatteryStats.dumpCheckin called for which type "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r5 = " but only STATS_SINCE_CHARGED is supported."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3[r13] = r4
            dumpLine(r9, r13, r1, r2, r3)
            return
        L_0x0030:
            long r1 = android.os.SystemClock.uptimeMillis()
            r14 = 1000(0x3e8, double:4.94E-321)
            long r7 = r1 * r14
            long r5 = android.os.SystemClock.elapsedRealtime()
            long r3 = r5 * r14
            long r1 = r0.getBatteryUptime(r7)
            long r16 = r0.computeBatteryUptime(r7, r10)
            long r18 = r0.computeBatteryRealtime(r3, r10)
            long r20 = r0.computeBatteryScreenOffUptime(r7, r10)
            long r22 = r0.computeBatteryScreenOffRealtime(r3, r10)
            long r24 = r0.computeRealtime(r3, r10)
            long r26 = r0.computeUptime(r7, r10)
            long r28 = r0.getScreenOnTime(r3, r10)
            long r30 = r0.getScreenDozeTime(r3, r10)
            long r32 = r0.getInteractiveTime(r3, r10)
            long r34 = r0.getPowerSaveModeEnabledTime(r3, r10)
            long r36 = r0.getDeviceIdleModeTime(r12, r3, r10)
            r38 = r1
            r2 = 2
            long r40 = r0.getDeviceIdleModeTime(r2, r3, r10)
            long r42 = r0.getDeviceIdlingTime(r12, r3, r10)
            long r44 = r0.getDeviceIdlingTime(r2, r3, r10)
            int r46 = r0.getNumConnectivityChange(r10)
            long r47 = r0.getPhoneOnTime(r3, r10)
            long r49 = r0.getUahDischarge(r10)
            long r51 = r0.getUahDischargeScreenOff(r10)
            long r53 = r0.getUahDischargeScreenDoze(r10)
            long r55 = r0.getUahDischargeLightDoze(r10)
            long r57 = r0.getUahDischargeDeepDoze(r10)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = 128(0x80, float:1.794E-43)
            r1.<init>(r2)
            r2 = r1
            android.util.SparseArray r1 = r236.getUidStats()
            r60 = r2
            int r2 = r1.size()
            java.lang.String[] r61 = STAT_NAMES
            r12 = r61[r10]
            java.lang.String r14 = "bt"
            r15 = 12
            java.lang.Object[] r15 = new java.lang.Object[r15]
            if (r10 != 0) goto L_0x00c0
            int r61 = r236.getStartCount()
            java.lang.Integer r61 = java.lang.Integer.valueOf(r61)
            goto L_0x00c2
        L_0x00c0:
            java.lang.String r61 = "N/A"
        L_0x00c2:
            r15[r13] = r61
            r63 = 1000(0x3e8, double:4.94E-321)
            long r65 = r18 / r63
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r62 = 1
            r15[r62] = r61
            long r65 = r16 / r63
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r59 = 2
            r15[r59] = r61
            long r65 = r24 / r63
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r13 = 3
            r15[r13] = r61
            long r65 = r26 / r63
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r13 = 4
            r15[r13] = r61
            long r65 = r236.getStartClockTime()
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r13 = 5
            r15[r13] = r61
            long r65 = r22 / r63
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r13 = 6
            r15[r13] = r61
            long r65 = r20 / r63
            java.lang.Long r61 = java.lang.Long.valueOf(r65)
            r13 = 7
            r15[r13] = r61
            int r61 = r236.getEstimatedBatteryCapacity()
            java.lang.Integer r61 = java.lang.Integer.valueOf(r61)
            r13 = 8
            r15[r13] = r61
            int r61 = r236.getMinLearnedBatteryCapacity()
            java.lang.Integer r61 = java.lang.Integer.valueOf(r61)
            r13 = 9
            r15[r13] = r61
            int r61 = r236.getMaxLearnedBatteryCapacity()
            java.lang.Integer r61 = java.lang.Integer.valueOf(r61)
            r13 = 10
            r15[r13] = r61
            r61 = 11
            r63 = 1000(0x3e8, double:4.94E-321)
            long r65 = r30 / r63
            java.lang.Long r65 = java.lang.Long.valueOf(r65)
            r15[r61] = r65
            r13 = 0
            dumpLine(r9, r13, r12, r14, r15)
            r13 = 0
            r65 = 0
            r14 = r13
            r13 = 0
        L_0x0143:
            if (r13 >= r2) goto L_0x019a
            java.lang.Object r61 = r1.valueAt(r13)
            android.os.BatteryStats$Uid r61 = (android.os.BatteryStats.Uid) r61
            r76 = r1
            android.util.ArrayMap r1 = r61.getWakelockStats()
            int r77 = r1.size()
            r78 = r2
            r2 = 1
            int r77 = r77 + -1
        L_0x015b:
            r79 = r77
            r2 = r79
            if (r2 < 0) goto L_0x0191
            java.lang.Object r62 = r1.valueAt(r2)
            r81 = r1
            r1 = r62
            android.os.BatteryStats$Uid$Wakelock r1 = (android.os.BatteryStats.Uid.Wakelock) r1
            r82 = r5
            r5 = 1
            android.os.BatteryStats$Timer r6 = r1.getWakeTime(r5)
            if (r6 == 0) goto L_0x017a
            long r79 = r6.getTotalTimeLocked(r3, r10)
            long r14 = r14 + r79
        L_0x017a:
            r84 = r6
            r5 = 0
            android.os.BatteryStats$Timer r6 = r1.getWakeTime(r5)
            if (r6 == 0) goto L_0x0189
            long r79 = r6.getTotalTimeLocked(r3, r10)
            long r65 = r65 + r79
        L_0x0189:
            int r77 = r2 + -1
            r1 = r81
            r5 = r82
            r2 = 1
            goto L_0x015b
        L_0x0191:
            r82 = r5
            int r13 = r13 + 1
            r1 = r76
            r2 = r78
            goto L_0x0143
        L_0x019a:
            r76 = r1
            r78 = r2
            r82 = r5
            r1 = 0
            long r79 = r0.getNetworkActivityBytes(r1, r10)
            r2 = 1
            long r84 = r0.getNetworkActivityBytes(r2, r10)
            r5 = 2
            long r86 = r0.getNetworkActivityBytes(r5, r10)
            r6 = 3
            long r88 = r0.getNetworkActivityBytes(r6, r10)
            long r90 = r0.getNetworkActivityPackets(r1, r10)
            long r92 = r0.getNetworkActivityPackets(r2, r10)
            long r94 = r0.getNetworkActivityPackets(r5, r10)
            long r96 = r0.getNetworkActivityPackets(r6, r10)
            r1 = 4
            long r98 = r0.getNetworkActivityBytes(r1, r10)
            r1 = 5
            long r100 = r0.getNetworkActivityBytes(r1, r10)
            java.lang.String r1 = "gn"
            r2 = 10
            java.lang.Object[] r5 = new java.lang.Object[r2]
            java.lang.Long r2 = java.lang.Long.valueOf(r79)
            r6 = 0
            r5[r6] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r84)
            r6 = 1
            r5[r6] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r86)
            r6 = 2
            r5[r6] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r88)
            r13 = 3
            r5[r13] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r90)
            r13 = 4
            r5[r13] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r92)
            r13 = 5
            r5[r13] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r94)
            r13 = 6
            r5[r13] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r96)
            r13 = 7
            r5[r13] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r98)
            r13 = 8
            r5[r13] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r100)
            r13 = 9
            r5[r13] = r2
            r2 = 0
            dumpLine(r9, r2, r12, r1, r5)
            r2 = 0
            java.lang.String r5 = "gmcd"
            android.os.BatteryStats$ControllerActivityCounter r13 = r236.getModemControllerActivity()
            r104 = r7
            r102 = r38
            r8 = r76
            r1 = r238
            r106 = r8
            r7 = r60
            r8 = r6
            r6 = r78
            r8 = r3
            r3 = r12
            r4 = r5
            r108 = r82
            r5 = r13
            r13 = r6
            r6 = r239
            dumpControllerActivityLine(r1, r2, r3, r4, r5, r6)
            long r38 = r0.getWifiOnTime(r8, r10)
            long r59 = r0.getGlobalWifiRunningTime(r8, r10)
            java.lang.String r1 = "gwfl"
            r2 = 5
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r4 = 1000(0x3e8, double:4.94E-321)
            long r63 = r38 / r4
            java.lang.Long r2 = java.lang.Long.valueOf(r63)
            r6 = 0
            r3[r6] = r2
            long r76 = r59 / r4
            java.lang.Long r2 = java.lang.Long.valueOf(r76)
            r4 = 1
            r3[r4] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
            r4 = 2
            r3[r4] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
            r4 = 3
            r3[r4] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
            r4 = 4
            r3[r4] = r2
            r4 = r8
            r9 = r238
            dumpLine(r9, r6, r12, r1, r3)
            r2 = 0
            java.lang.String r6 = "gwfcd"
            android.os.BatteryStats$ControllerActivityCounter r8 = r236.getWifiControllerActivity()
            r1 = r238
            r3 = r12
            r110 = r4
            r4 = r6
            r5 = r8
            r6 = r239
            dumpControllerActivityLine(r1, r2, r3, r4, r5, r6)
            java.lang.String r4 = "gble"
            android.os.BatteryStats$ControllerActivityCounter r5 = r236.getBluetoothControllerActivity()
            dumpControllerActivityLine(r1, r2, r3, r4, r5, r6)
            java.lang.String r1 = "m"
            r2 = 21
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 1000(0x3e8, double:4.94E-321)
            long r5 = r28 / r3
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            r6 = 0
            r2[r6] = r5
            long r5 = r47 / r3
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            r6 = 1
            r2[r6] = r5
            long r5 = r14 / r3
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            r6 = 2
            r2[r6] = r5
            long r5 = r65 / r3
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            r6 = 3
            r2[r6] = r5
            r5 = r110
            long r63 = r0.getMobileRadioActiveTime(r5, r10)
            long r63 = r63 / r3
            java.lang.Long r8 = java.lang.Long.valueOf(r63)
            r61 = 4
            r2[r61] = r8
            long r63 = r0.getMobileRadioActiveAdjustedTime(r10)
            long r63 = r63 / r3
            java.lang.Long r8 = java.lang.Long.valueOf(r63)
            r61 = 5
            r2[r61] = r8
            long r63 = r32 / r3
            java.lang.Long r8 = java.lang.Long.valueOf(r63)
            r61 = 6
            r2[r61] = r8
            long r63 = r34 / r3
            java.lang.Long r8 = java.lang.Long.valueOf(r63)
            r61 = 7
            r2[r61] = r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r46)
            r61 = 8
            r2[r61] = r8
            long r63 = r40 / r3
            java.lang.Long r8 = java.lang.Long.valueOf(r63)
            r61 = 9
            r2[r61] = r8
            r8 = 2
            int r61 = r0.getDeviceIdleModeCount(r8, r10)
            java.lang.Integer r61 = java.lang.Integer.valueOf(r61)
            r63 = 10
            r2[r63] = r61
            r61 = 11
            long r76 = r44 / r3
            java.lang.Long r3 = java.lang.Long.valueOf(r76)
            r2[r61] = r3
            r3 = 12
            int r4 = r0.getDeviceIdlingCount(r8, r10)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2[r3] = r4
            r3 = 13
            int r4 = r0.getMobileRadioActiveCount(r10)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2[r3] = r4
            r3 = 14
            long r76 = r0.getMobileRadioActiveUnknownTime(r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r76 = r76 / r63
            java.lang.Long r4 = java.lang.Long.valueOf(r76)
            r2[r3] = r4
            r3 = 15
            long r76 = r36 / r63
            java.lang.Long r4 = java.lang.Long.valueOf(r76)
            r2[r3] = r4
            r3 = 16
            r4 = 1
            int r8 = r0.getDeviceIdleModeCount(r4, r10)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r2[r3] = r8
            r3 = 17
            long r61 = r42 / r63
            java.lang.Long r8 = java.lang.Long.valueOf(r61)
            r2[r3] = r8
            r3 = 18
            int r8 = r0.getDeviceIdlingCount(r4, r10)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r2[r3] = r8
            r3 = 19
            long r76 = r0.getLongestDeviceIdleModeTime(r4)
            java.lang.Long r4 = java.lang.Long.valueOf(r76)
            r2[r3] = r4
            r3 = 20
            r4 = 2
            long r76 = r0.getLongestDeviceIdleModeTime(r4)
            java.lang.Long r4 = java.lang.Long.valueOf(r76)
            r2[r3] = r4
            r3 = 0
            dumpLine(r9, r3, r12, r1, r2)
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
        L_0x0397:
            if (r3 >= r1) goto L_0x03ab
            long r76 = r0.getScreenBrightnessTime(r3, r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r76 = r76 / r63
            java.lang.Long r1 = java.lang.Long.valueOf(r76)
            r2[r3] = r1
            int r3 = r3 + 1
            r1 = 5
            goto L_0x0397
        L_0x03ab:
            java.lang.String r1 = "br"
            r3 = 0
            dumpLine(r9, r3, r12, r1, r2)
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
        L_0x03b5:
            if (r3 >= r1) goto L_0x03c9
            long r76 = r0.getPhoneSignalStrengthTime(r3, r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r76 = r76 / r63
            java.lang.Long r1 = java.lang.Long.valueOf(r76)
            r2[r3] = r1
            int r3 = r3 + 1
            r1 = 5
            goto L_0x03b5
        L_0x03c9:
            java.lang.String r1 = "sgt"
            r3 = 0
            dumpLine(r9, r3, r12, r1, r2)
            java.lang.String r1 = "sst"
            r4 = 1
            java.lang.Object[] r8 = new java.lang.Object[r4]
            long r76 = r0.getPhoneSignalScanningTime(r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r76 = r76 / r63
            java.lang.Long r4 = java.lang.Long.valueOf(r76)
            r8[r3] = r4
            dumpLine(r9, r3, r12, r1, r8)
            r1 = 0
        L_0x03e8:
            r3 = 5
            if (r1 >= r3) goto L_0x03f8
            int r3 = r0.getPhoneSignalStrengthCount(r1, r10)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2[r1] = r3
            int r1 = r1 + 1
            goto L_0x03e8
        L_0x03f8:
            java.lang.String r1 = "sgc"
            r3 = 0
            dumpLine(r9, r3, r12, r1, r2)
            r1 = 22
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
        L_0x0404:
            r3 = 22
            if (r2 >= r3) goto L_0x0419
            long r3 = r0.getPhoneDataConnectionTime(r2, r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 / r63
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x0404
        L_0x0419:
            java.lang.String r2 = "dct"
            r3 = 0
            dumpLine(r9, r3, r12, r2, r1)
            r2 = 0
        L_0x0420:
            r3 = 22
            if (r2 >= r3) goto L_0x0431
            int r3 = r0.getPhoneDataConnectionCount(r2, r10)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x0420
        L_0x0431:
            java.lang.String r2 = "dcc"
            r3 = 0
            dumpLine(r9, r3, r12, r2, r1)
            r2 = 8
            java.lang.Object[] r1 = new java.lang.Object[r2]
            r3 = 0
        L_0x043c:
            if (r3 >= r2) goto L_0x0451
            long r76 = r0.getWifiStateTime(r3, r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r76 = r76 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r76)
            r1[r3] = r2
            int r3 = r3 + 1
            r2 = 8
            goto L_0x043c
        L_0x0451:
            java.lang.String r2 = "wst"
            r3 = 0
            dumpLine(r9, r3, r12, r2, r1)
            r2 = 0
        L_0x0459:
            r3 = 8
            if (r2 >= r3) goto L_0x046a
            int r3 = r0.getWifiStateCount(r2, r10)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x0459
        L_0x046a:
            java.lang.String r2 = "wsc"
            r3 = 0
            dumpLine(r9, r3, r12, r2, r1)
            r2 = 13
            java.lang.Object[] r1 = new java.lang.Object[r2]
            r2 = 0
        L_0x0476:
            r3 = 13
            if (r2 >= r3) goto L_0x048b
            long r3 = r0.getWifiSupplStateTime(r2, r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 / r63
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x0476
        L_0x048b:
            java.lang.String r2 = "wsst"
            r3 = 0
            dumpLine(r9, r3, r12, r2, r1)
            r2 = 0
        L_0x0493:
            r3 = 13
            if (r2 >= r3) goto L_0x04a4
            int r3 = r0.getWifiSupplStateCount(r2, r10)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x0493
        L_0x04a4:
            java.lang.String r2 = "wssc"
            r3 = 0
            dumpLine(r9, r3, r12, r2, r1)
            r2 = 5
            java.lang.Object[] r8 = new java.lang.Object[r2]
            r1 = 0
        L_0x04af:
            if (r1 >= r2) goto L_0x04c3
            long r2 = r0.getWifiSignalStrengthTime(r1, r5, r10)
            r63 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            r8[r1] = r2
            int r1 = r1 + 1
            r2 = 5
            goto L_0x04af
        L_0x04c3:
            java.lang.String r1 = "wsgt"
            r2 = 0
            dumpLine(r9, r2, r12, r1, r8)
            r1 = 0
        L_0x04cb:
            r2 = 5
            if (r1 >= r2) goto L_0x04db
            int r2 = r0.getWifiSignalStrengthCount(r1, r10)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r8[r1] = r2
            int r1 = r1 + 1
            goto L_0x04cb
        L_0x04db:
            java.lang.String r1 = "wsgc"
            r2 = 0
            dumpLine(r9, r2, r12, r1, r8)
            long r76 = r0.getWifiMulticastWakelockTime(r5, r10)
            int r61 = r0.getWifiMulticastWakelockCount(r10)
            java.lang.String r1 = "wmct"
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r63 = 1000(0x3e8, double:4.94E-321)
            long r81 = r76 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r81)
            r4 = 0
            r3[r4] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r61)
            r62 = 1
            r3[r62] = r2
            dumpLine(r9, r4, r12, r1, r3)
            java.lang.String r1 = "dc"
            r2 = 10
            java.lang.Object[] r3 = new java.lang.Object[r2]
            int r2 = r236.getLowDischargeAmountSinceCharge()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3[r4] = r2
            int r2 = r236.getHighDischargeAmountSinceCharge()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r4 = 1
            r3[r4] = r2
            int r2 = r236.getDischargeAmountScreenOnSinceCharge()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r4 = 2
            r3[r4] = r2
            int r2 = r236.getDischargeAmountScreenOffSinceCharge()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r4 = 3
            r3[r4] = r2
            r63 = 1000(0x3e8, double:4.94E-321)
            long r81 = r49 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r81)
            r4 = 4
            r3[r4] = r2
            long r81 = r51 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r81)
            r4 = 5
            r3[r4] = r2
            int r2 = r236.getDischargeAmountScreenDozeSinceCharge()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r4 = 6
            r3[r4] = r2
            long r81 = r53 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r81)
            r4 = 7
            r3[r4] = r2
            long r81 = r55 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r81)
            r4 = 8
            r3[r4] = r2
            long r81 = r57 / r63
            java.lang.Long r2 = java.lang.Long.valueOf(r81)
            r4 = 9
            r3[r4] = r2
            r2 = 0
            dumpLine(r9, r2, r12, r1, r3)
            r81 = 500(0x1f4, double:2.47E-321)
            if (r11 >= 0) goto L_0x0676
            java.util.Map r78 = r236.getKernelWakelockStats()
            int r1 = r78.size()
            if (r1 <= 0) goto L_0x05f0
            java.util.Set r1 = r78.entrySet()
            java.util.Iterator r83 = r1.iterator()
        L_0x058e:
            boolean r1 = r83.hasNext()
            if (r1 == 0) goto L_0x05f0
            java.lang.Object r1 = r83.next()
            r110 = r1
            java.util.Map$Entry r110 = (java.util.Map.Entry) r110
            r1 = 0
            r7.setLength(r1)
            java.lang.Object r1 = r110.getValue()
            r2 = r1
            android.os.BatteryStats$Timer r2 = (android.os.BatteryStats.Timer) r2
            r111 = 0
            java.lang.String r112 = ""
            r1 = r7
            r3 = r5
            r113 = r14
            r14 = r5
            r5 = r111
            r6 = r239
            r115 = r7
            r7 = r112
            printWakeLockCheckin(r1, r2, r3, r5, r6, r7)
            java.lang.String r1 = "kwl"
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "\""
            r2.append(r4)
            java.lang.Object r4 = r110.getKey()
            java.lang.String r4 = (java.lang.String) r4
            r2.append(r4)
            java.lang.String r4 = "\""
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r4 = 0
            r3[r4] = r2
            java.lang.String r2 = r115.toString()
            r5 = 1
            r3[r5] = r2
            dumpLine(r9, r4, r12, r1, r3)
            r5 = r14
            r14 = r113
            r7 = r115
            goto L_0x058e
        L_0x05f0:
            r115 = r7
            r113 = r14
            r14 = r5
            java.util.Map r1 = r236.getWakeupReasonStats()
            int r2 = r1.size()
            if (r2 <= 0) goto L_0x0673
            java.util.Set r2 = r1.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0607:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0673
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getValue()
            android.os.BatteryStats$Timer r4 = (android.os.BatteryStats.Timer) r4
            long r4 = r4.getTotalTimeLocked(r14, r10)
            java.lang.Object r6 = r3.getValue()
            android.os.BatteryStats$Timer r6 = (android.os.BatteryStats.Timer) r6
            int r6 = r6.getCountLocked(r10)
            java.lang.String r7 = "wr"
            r116 = r1
            r117 = r2
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r118 = r8
            java.lang.String r8 = "\""
            r1.append(r8)
            java.lang.Object r8 = r3.getKey()
            java.lang.String r8 = (java.lang.String) r8
            r1.append(r8)
            java.lang.String r8 = "\""
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            r8 = 0
            r2[r8] = r1
            long r110 = r4 + r81
            r63 = 1000(0x3e8, double:4.94E-321)
            long r110 = r110 / r63
            java.lang.Long r1 = java.lang.Long.valueOf(r110)
            r62 = 1
            r2[r62] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r6)
            r67 = 2
            r2[r67] = r1
            dumpLine(r9, r8, r12, r7, r2)
            r1 = r116
            r2 = r117
            r8 = r118
            goto L_0x0607
        L_0x0673:
            r118 = r8
            goto L_0x067d
        L_0x0676:
            r115 = r7
            r118 = r8
            r113 = r14
            r14 = r5
        L_0x067d:
            java.util.Map r78 = r236.getRpmStats()
            java.util.Map r8 = r236.getScreenOffRpmStats()
            int r1 = r78.size()
            r110 = 0
            if (r1 <= 0) goto L_0x0728
            java.util.Set r1 = r78.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0695:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0728
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            r7 = r115
            r3 = 0
            r7.setLength(r3)
            java.lang.Object r3 = r2.getValue()
            android.os.BatteryStats$Timer r3 = (android.os.BatteryStats.Timer) r3
            long r4 = r3.getTotalTimeLocked(r14, r10)
            long r4 = r4 + r81
            r63 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r63
            int r6 = r3.getCountLocked(r10)
            r119 = r1
            java.lang.Object r1 = r2.getKey()
            java.lang.Object r1 = r8.get(r1)
            android.os.BatteryStats$Timer r1 = (android.os.BatteryStats.Timer) r1
            if (r1 == 0) goto L_0x06d4
            long r115 = r1.getTotalTimeLocked(r14, r10)
            long r115 = r115 + r81
            r63 = 1000(0x3e8, double:4.94E-321)
            long r115 = r115 / r63
            goto L_0x06d6
        L_0x06d4:
            r115 = r110
        L_0x06d6:
            if (r1 == 0) goto L_0x06dd
            int r83 = r1.getCountLocked(r10)
            goto L_0x06df
        L_0x06dd:
            r83 = 0
        L_0x06df:
            r120 = r1
            java.lang.String r1 = "rpm"
            r121 = r3
            r122 = r8
            r3 = 3
            java.lang.Object[] r8 = new java.lang.Object[r3]
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r123 = r14
            java.lang.String r14 = "\""
            r3.append(r14)
            java.lang.Object r14 = r2.getKey()
            java.lang.String r14 = (java.lang.String) r14
            r3.append(r14)
            java.lang.String r14 = "\""
            r3.append(r14)
            java.lang.String r3 = r3.toString()
            r14 = 0
            r8[r14] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r4)
            r15 = 1
            r8[r15] = r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            r15 = 2
            r8[r15] = r3
            dumpLine(r9, r14, r12, r1, r8)
            r115 = r7
            r1 = r119
            r8 = r122
            r14 = r123
            goto L_0x0695
        L_0x0728:
            r122 = r8
            r123 = r14
            r7 = r115
            r14 = 0
            com.android.internal.os.BatteryStatsHelper r1 = new com.android.internal.os.BatteryStatsHelper
            r15 = r237
            r8 = r241
            r1.<init>(r15, r14, r8)
            r14 = r1
            r14.create((android.os.BatteryStats) r0)
            r6 = -1
            r14.refreshStats((int) r10, (int) r6)
            java.util.List r5 = r14.getUsageList()
            if (r5 == 0) goto L_0x0826
            int r1 = r5.size()
            if (r1 <= 0) goto L_0x0826
            java.lang.String r1 = "pws"
            r2 = 4
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.android.internal.os.PowerProfile r2 = r14.getPowerProfile()
            double r115 = r2.getBatteryCapacity()
            java.lang.String r2 = com.android.internal.os.BatteryStatsHelper.makemAh(r115)
            r4 = 0
            r3[r4] = r2
            double r115 = r14.getComputedPower()
            java.lang.String r2 = com.android.internal.os.BatteryStatsHelper.makemAh(r115)
            r4 = 1
            r3[r4] = r2
            double r115 = r14.getMinDrainedPower()
            java.lang.String r2 = com.android.internal.os.BatteryStatsHelper.makemAh(r115)
            r4 = 2
            r3[r4] = r2
            double r115 = r14.getMaxDrainedPower()
            java.lang.String r2 = com.android.internal.os.BatteryStatsHelper.makemAh(r115)
            r4 = 3
            r3[r4] = r2
            r2 = 0
            dumpLine(r9, r2, r12, r1, r3)
            r1 = 0
            r2 = r1
            r1 = 0
        L_0x0789:
            int r3 = r5.size()
            if (r1 >= r3) goto L_0x0826
            java.lang.Object r3 = r5.get(r1)
            com.android.internal.os.BatterySipper r3 = (com.android.internal.os.BatterySipper) r3
            int[] r4 = android.os.BatteryStats.AnonymousClass2.$SwitchMap$com$android$internal$os$BatterySipper$DrainType
            com.android.internal.os.BatterySipper$DrainType r6 = r3.drainType
            int r6 = r6.ordinal()
            r4 = r4[r6]
            switch(r4) {
                case 1: goto L_0x07e1;
                case 2: goto L_0x07de;
                case 3: goto L_0x07db;
                case 4: goto L_0x07d7;
                case 5: goto L_0x07d3;
                case 6: goto L_0x07d0;
                case 7: goto L_0x07cc;
                case 8: goto L_0x07c9;
                case 9: goto L_0x07bf;
                case 10: goto L_0x07b4;
                case 11: goto L_0x07b0;
                case 12: goto L_0x07ac;
                case 13: goto L_0x07a9;
                case 14: goto L_0x07a5;
                default: goto L_0x07a2;
            }
        L_0x07a2:
            java.lang.String r4 = "???"
            goto L_0x07e4
        L_0x07a5:
            java.lang.String r4 = "memory"
            goto L_0x07e4
        L_0x07a9:
            java.lang.String r4 = "camera"
            goto L_0x07e4
        L_0x07ac:
            java.lang.String r4 = "over"
            goto L_0x07e4
        L_0x07b0:
            java.lang.String r4 = "unacc"
            goto L_0x07e4
        L_0x07b4:
            int r4 = r3.userId
            r6 = 0
            int r2 = android.os.UserHandle.getUid(r4, r6)
            java.lang.String r4 = "user"
            goto L_0x07e4
        L_0x07bf:
            android.os.BatteryStats$Uid r4 = r3.uidObj
            int r2 = r4.getUid()
            java.lang.String r4 = "uid"
            goto L_0x07e4
        L_0x07c9:
            java.lang.String r4 = "flashlight"
            goto L_0x07e4
        L_0x07cc:
            java.lang.String r4 = "scrn"
            goto L_0x07e4
        L_0x07d0:
            java.lang.String r4 = "blue"
            goto L_0x07e4
        L_0x07d3:
            java.lang.String r4 = "wifi"
            goto L_0x07e4
        L_0x07d7:
            java.lang.String r4 = "phone"
            goto L_0x07e4
        L_0x07db:
            java.lang.String r4 = "cell"
            goto L_0x07e4
        L_0x07de:
            java.lang.String r4 = "idle"
            goto L_0x07e4
        L_0x07e1:
            java.lang.String r4 = "ambi"
        L_0x07e4:
            java.lang.String r6 = "pwi"
            r126 = r5
            r0 = 5
            java.lang.Object[] r5 = new java.lang.Object[r0]
            r0 = 0
            r5[r0] = r4
            r127 = r14
            double r14 = r3.totalPowerMah
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r14)
            r14 = 1
            r5[r14] = r0
            boolean r0 = r3.shouldHide
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r14 = 2
            r5[r14] = r0
            double r14 = r3.screenPowerMah
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r14)
            r14 = 3
            r5[r14] = r0
            double r14 = r3.proportionalSmearMah
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r14)
            r14 = 4
            r5[r14] = r0
            dumpLine(r9, r2, r12, r6, r5)
            int r1 = r1 + 1
            r5 = r126
            r14 = r127
            r0 = r236
            r6 = -1
            r15 = r237
            goto L_0x0789
        L_0x0826:
            r126 = r5
            r127 = r14
            long[] r0 = r236.getCpuFreqs()
            if (r0 == 0) goto L_0x0865
            r1 = 0
            r7.setLength(r1)
            r1 = 0
        L_0x0835:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x0856
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            if (r1 != 0) goto L_0x0842
            java.lang.String r3 = ""
            goto L_0x0844
        L_0x0842:
            java.lang.String r3 = ","
        L_0x0844:
            r2.append(r3)
            r3 = r0[r1]
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r7.append(r2)
            int r1 = r1 + 1
            goto L_0x0835
        L_0x0856:
            java.lang.String r1 = "gcf"
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.String r2 = r7.toString()
            r4 = 0
            r3[r4] = r2
            dumpLine(r9, r4, r12, r1, r3)
        L_0x0865:
            r1 = 0
        L_0x0866:
            r14 = r1
            if (r14 >= r13) goto L_0x13a8
            r15 = r106
            int r6 = r15.keyAt(r14)
            if (r11 < 0) goto L_0x08a1
            if (r6 == r11) goto L_0x08a1
            r227 = r0
            r228 = r7
            r166 = r13
            r174 = r14
            r230 = r108
            r109 = r122
            r225 = r123
            r112 = r126
            r5 = 6
            r62 = 1
            r63 = 1000(0x3e8, double:4.94E-321)
            r67 = 0
            r68 = 3
            r69 = 4
            r70 = 5
            r73 = 8
            r74 = 9
            r106 = -1
            r107 = 10
            r173 = 2
            r108 = r15
            r14 = r102
            goto L_0x138e
        L_0x08a1:
            java.lang.Object r1 = r15.valueAt(r14)
            r5 = r1
            android.os.BatteryStats$Uid r5 = (android.os.BatteryStats.Uid) r5
            r1 = 0
            long r115 = r5.getNetworkActivityBytes(r1, r10)
            r2 = 1
            long r119 = r5.getNetworkActivityBytes(r2, r10)
            r3 = 2
            long r128 = r5.getNetworkActivityBytes(r3, r10)
            r3 = 3
            long r130 = r5.getNetworkActivityBytes(r3, r10)
            long r132 = r5.getNetworkActivityPackets(r1, r10)
            long r134 = r5.getNetworkActivityPackets(r2, r10)
            long r136 = r5.getMobileRadioActiveTime(r10)
            int r83 = r5.getMobileRadioActiveCount(r10)
            long r138 = r5.getMobileRadioApWakeupCount(r10)
            r1 = 2
            long r140 = r5.getNetworkActivityPackets(r1, r10)
            r1 = 3
            long r142 = r5.getNetworkActivityPackets(r1, r10)
            long r144 = r5.getWifiRadioApWakeupCount(r10)
            r1 = 4
            long r146 = r5.getNetworkActivityBytes(r1, r10)
            r1 = 5
            long r148 = r5.getNetworkActivityBytes(r1, r10)
            r1 = 6
            long r150 = r5.getNetworkActivityBytes(r1, r10)
            r2 = 7
            long r152 = r5.getNetworkActivityBytes(r2, r10)
            r3 = 8
            long r154 = r5.getNetworkActivityBytes(r3, r10)
            r4 = 9
            long r156 = r5.getNetworkActivityBytes(r4, r10)
            long r158 = r5.getNetworkActivityPackets(r1, r10)
            long r160 = r5.getNetworkActivityPackets(r2, r10)
            long r162 = r5.getNetworkActivityPackets(r3, r10)
            long r164 = r5.getNetworkActivityPackets(r4, r10)
            int r1 = (r115 > r110 ? 1 : (r115 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r119 > r110 ? 1 : (r119 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r128 > r110 ? 1 : (r128 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r130 > r110 ? 1 : (r130 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r132 > r110 ? 1 : (r132 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r134 > r110 ? 1 : (r134 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r140 > r110 ? 1 : (r140 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r142 > r110 ? 1 : (r142 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r136 > r110 ? 1 : (r136 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            if (r83 > 0) goto L_0x0964
            int r1 = (r146 > r110 ? 1 : (r146 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r148 > r110 ? 1 : (r148 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r138 > r110 ? 1 : (r138 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r144 > r110 ? 1 : (r144 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r150 > r110 ? 1 : (r150 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r152 > r110 ? 1 : (r152 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r154 > r110 ? 1 : (r154 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r156 > r110 ? 1 : (r156 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r158 > r110 ? 1 : (r158 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r160 > r110 ? 1 : (r160 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r162 > r110 ? 1 : (r162 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x0964
            int r1 = (r164 > r110 ? 1 : (r164 == r110 ? 0 : -1))
            if (r1 <= 0) goto L_0x0a16
        L_0x0964:
            java.lang.String r1 = "nt"
            r2 = 22
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Long r3 = java.lang.Long.valueOf(r115)
            r4 = 0
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r119)
            r4 = 1
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r128)
            r4 = 2
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r130)
            r4 = 3
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r132)
            r4 = 4
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r134)
            r4 = 5
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r140)
            r4 = 6
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r142)
            r4 = 7
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r136)
            r4 = 8
            r2[r4] = r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r83)
            r4 = 9
            r2[r4] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r146)
            r4 = 10
            r2[r4] = r3
            r3 = 11
            java.lang.Long r4 = java.lang.Long.valueOf(r148)
            r2[r3] = r4
            r3 = 12
            java.lang.Long r4 = java.lang.Long.valueOf(r138)
            r2[r3] = r4
            r3 = 13
            java.lang.Long r4 = java.lang.Long.valueOf(r144)
            r2[r3] = r4
            r3 = 14
            java.lang.Long r4 = java.lang.Long.valueOf(r150)
            r2[r3] = r4
            r3 = 15
            java.lang.Long r4 = java.lang.Long.valueOf(r152)
            r2[r3] = r4
            r3 = 16
            java.lang.Long r4 = java.lang.Long.valueOf(r154)
            r2[r3] = r4
            r3 = 17
            java.lang.Long r4 = java.lang.Long.valueOf(r156)
            r2[r3] = r4
            r3 = 18
            java.lang.Long r4 = java.lang.Long.valueOf(r158)
            r2[r3] = r4
            r3 = 19
            java.lang.Long r4 = java.lang.Long.valueOf(r160)
            r2[r3] = r4
            r3 = 20
            java.lang.Long r4 = java.lang.Long.valueOf(r162)
            r2[r3] = r4
            r3 = 21
            java.lang.Long r4 = java.lang.Long.valueOf(r164)
            r2[r3] = r4
            dumpLine(r9, r6, r12, r1, r2)
        L_0x0a16:
            java.lang.String r4 = "mcd"
            android.os.BatteryStats$ControllerActivityCounter r106 = r5.getModemControllerActivity()
            r1 = r238
            r2 = r6
            r3 = r12
            r11 = r5
            r112 = r126
            r5 = r106
            r166 = r13
            r106 = -1
            r13 = r6
            r6 = r239
            dumpControllerActivityLine(r1, r2, r3, r4, r5, r6)
            r5 = r123
            long r123 = r11.getFullWifiLockTime(r5, r10)
            long r125 = r11.getWifiScanTime(r5, r10)
            int r117 = r11.getWifiScanCount(r10)
            int r121 = r11.getWifiScanBackgroundCount(r10)
            long r1 = r11.getWifiScanActualTime(r5)
            long r1 = r1 + r81
            r3 = 1000(0x3e8, double:4.94E-321)
            long r167 = r1 / r3
            long r1 = r11.getWifiScanBackgroundTime(r5)
            long r1 = r1 + r81
            long r169 = r1 / r3
            long r171 = r11.getWifiRunningTime(r5, r10)
            int r1 = (r123 > r110 ? 1 : (r123 == r110 ? 0 : -1))
            if (r1 != 0) goto L_0x0a70
            int r1 = (r125 > r110 ? 1 : (r125 == r110 ? 0 : -1))
            if (r1 != 0) goto L_0x0a70
            if (r117 != 0) goto L_0x0a70
            if (r121 != 0) goto L_0x0a70
            int r1 = (r167 > r110 ? 1 : (r167 == r110 ? 0 : -1))
            if (r1 != 0) goto L_0x0a70
            int r1 = (r169 > r110 ? 1 : (r169 == r110 ? 0 : -1))
            if (r1 != 0) goto L_0x0a70
            int r1 = (r171 > r110 ? 1 : (r171 == r110 ? 0 : -1))
            if (r1 == 0) goto L_0x0ac7
        L_0x0a70:
            java.lang.String r1 = "wfl"
            r2 = 10
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Long r2 = java.lang.Long.valueOf(r123)
            r4 = 0
            r3[r4] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r125)
            r62 = 1
            r3[r62] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r171)
            r67 = 2
            r3[r67] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r117)
            r67 = 3
            r3[r67] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r67 = 4
            r3[r67] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r67 = 5
            r3[r67] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r4 = 6
            r3[r4] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r121)
            r4 = 7
            r3[r4] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r167)
            r4 = 8
            r3[r4] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r169)
            r4 = 9
            r3[r4] = r2
            dumpLine(r9, r13, r12, r1, r3)
        L_0x0ac7:
            java.lang.String r4 = "wfcd"
            android.os.BatteryStats$ControllerActivityCounter r173 = r11.getWifiControllerActivity()
            r1 = r238
            r2 = r13
            r3 = r12
            r174 = r14
            r175 = r15
            r14 = r5
            r5 = r173
            r6 = r239
            dumpControllerActivityLine(r1, r2, r3, r4, r5, r6)
            android.os.BatteryStats$Timer r6 = r11.getBluetoothScanTimer()
            if (r6 == 0) goto L_0x0bcf
            long r1 = r6.getTotalTimeLocked(r14, r10)
            long r1 = r1 + r81
            r3 = 1000(0x3e8, double:4.94E-321)
            long r1 = r1 / r3
            int r3 = (r1 > r110 ? 1 : (r1 == r110 ? 0 : -1))
            if (r3 == 0) goto L_0x0bcf
            int r3 = r6.getCountLocked(r10)
            android.os.BatteryStats$Timer r4 = r11.getBluetoothScanBackgroundTimer()
            if (r4 == 0) goto L_0x0b00
            int r5 = r4.getCountLocked(r10)
            goto L_0x0b01
        L_0x0b00:
            r5 = 0
        L_0x0b01:
            r176 = r14
            r14 = r108
            long r108 = r6.getTotalDurationMsLocked(r14)
            if (r4 == 0) goto L_0x0b10
            long r178 = r4.getTotalDurationMsLocked(r14)
            goto L_0x0b12
        L_0x0b10:
            r178 = r110
        L_0x0b12:
            android.os.BatteryStats$Counter r173 = r11.getBluetoothScanResultCounter()
            if (r173 == 0) goto L_0x0b23
            r180 = r4
            android.os.BatteryStats$Counter r4 = r11.getBluetoothScanResultCounter()
            int r4 = r4.getCountLocked(r10)
            goto L_0x0b26
        L_0x0b23:
            r180 = r4
            r4 = 0
        L_0x0b26:
            android.os.BatteryStats$Counter r173 = r11.getBluetoothScanResultBgCounter()
            if (r173 == 0) goto L_0x0b37
            r181 = r6
            android.os.BatteryStats$Counter r6 = r11.getBluetoothScanResultBgCounter()
            int r6 = r6.getCountLocked(r10)
            goto L_0x0b3a
        L_0x0b37:
            r181 = r6
            r6 = 0
        L_0x0b3a:
            android.os.BatteryStats$Timer r8 = r11.getBluetoothUnoptimizedScanTimer()
            if (r8 == 0) goto L_0x0b45
            long r182 = r8.getTotalDurationMsLocked(r14)
            goto L_0x0b47
        L_0x0b45:
            r182 = r110
        L_0x0b47:
            if (r8 == 0) goto L_0x0b4e
            long r184 = r8.getMaxDurationMsLocked(r14)
            goto L_0x0b50
        L_0x0b4e:
            r184 = r110
        L_0x0b50:
            r186 = r8
            android.os.BatteryStats$Timer r8 = r11.getBluetoothUnoptimizedScanBackgroundTimer()
            if (r8 == 0) goto L_0x0b5e
            long r187 = r8.getTotalDurationMsLocked(r14)
            goto L_0x0b60
        L_0x0b5e:
            r187 = r110
        L_0x0b60:
            if (r8 == 0) goto L_0x0b67
            long r189 = r8.getMaxDurationMsLocked(r14)
            goto L_0x0b69
        L_0x0b67:
            r189 = r110
        L_0x0b69:
            r191 = r8
            java.lang.String r8 = "blem"
            r192 = r0
            r0 = 11
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Long r173 = java.lang.Long.valueOf(r1)
            r67 = 0
            r0[r67] = r173
            java.lang.Integer r173 = java.lang.Integer.valueOf(r3)
            r62 = 1
            r0[r62] = r173
            java.lang.Integer r173 = java.lang.Integer.valueOf(r5)
            r107 = 2
            r0[r107] = r173
            java.lang.Long r173 = java.lang.Long.valueOf(r108)
            r68 = 3
            r0[r68] = r173
            java.lang.Long r173 = java.lang.Long.valueOf(r178)
            r69 = 4
            r0[r69] = r173
            java.lang.Integer r173 = java.lang.Integer.valueOf(r4)
            r70 = 5
            r0[r70] = r173
            java.lang.Integer r173 = java.lang.Integer.valueOf(r6)
            r71 = 6
            r0[r71] = r173
            java.lang.Long r173 = java.lang.Long.valueOf(r182)
            r72 = 7
            r0[r72] = r173
            java.lang.Long r173 = java.lang.Long.valueOf(r187)
            r73 = 8
            r0[r73] = r173
            java.lang.Long r173 = java.lang.Long.valueOf(r184)
            r74 = 9
            r0[r74] = r173
            java.lang.Long r173 = java.lang.Long.valueOf(r189)
            r75 = 10
            r0[r75] = r173
            dumpLine(r9, r13, r12, r8, r0)
            goto L_0x0bdb
        L_0x0bcf:
            r192 = r0
            r181 = r6
            r176 = r14
            r14 = r108
            r73 = 8
            r74 = 9
        L_0x0bdb:
            java.lang.String r4 = "ble"
            android.os.BatteryStats$ControllerActivityCounter r5 = r11.getBluetoothControllerActivity()
            r1 = r238
            r2 = r13
            r3 = r12
            r0 = r181
            r6 = r239
            dumpControllerActivityLine(r1, r2, r3, r4, r5, r6)
            boolean r1 = r11.hasUserActivity()
            if (r1 == 0) goto L_0x0c17
            int r1 = android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            r3 = r2
            r2 = 0
        L_0x0bf9:
            int r4 = android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES
            if (r2 >= r4) goto L_0x0c0d
            int r4 = r11.getUserActivityCount(r2, r10)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r1[r2] = r5
            if (r4 == 0) goto L_0x0c0a
            r3 = 1
        L_0x0c0a:
            int r2 = r2 + 1
            goto L_0x0bf9
        L_0x0c0d:
            if (r3 == 0) goto L_0x0c15
            java.lang.String r2 = "ua"
            dumpLine(r9, r13, r12, r2, r1)
        L_0x0c15:
            r118 = r1
        L_0x0c17:
            android.os.BatteryStats$Timer r1 = r11.getAggregatedPartialWakelockTimer()
            if (r1 == 0) goto L_0x0c4f
            android.os.BatteryStats$Timer r1 = r11.getAggregatedPartialWakelockTimer()
            long r2 = r1.getTotalDurationMsLocked(r14)
            android.os.BatteryStats$Timer r4 = r1.getSubTimer()
            if (r4 == 0) goto L_0x0c30
            long r5 = r4.getTotalDurationMsLocked(r14)
            goto L_0x0c32
        L_0x0c30:
            r5 = r110
        L_0x0c32:
            java.lang.String r8 = "awl"
            r193 = r0
            r194 = r1
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r0 = java.lang.Long.valueOf(r2)
            r67 = 0
            r1[r67] = r0
            java.lang.Long r0 = java.lang.Long.valueOf(r5)
            r62 = 1
            r1[r62] = r0
            dumpLine(r9, r13, r12, r8, r1)
            goto L_0x0c53
        L_0x0c4f:
            r193 = r0
            r62 = 1
        L_0x0c53:
            android.util.ArrayMap r0 = r11.getWakelockStats()
            int r1 = r0.size()
            int r1 = r1 + -1
        L_0x0c5d:
            r8 = r1
            if (r8 < 0) goto L_0x0d11
            java.lang.Object r1 = r0.valueAt(r8)
            r6 = r1
            android.os.BatteryStats$Uid$Wakelock r6 = (android.os.BatteryStats.Uid.Wakelock) r6
            java.lang.String r108 = ""
            r1 = 0
            r7.setLength(r1)
            r1 = 1
            android.os.BatteryStats$Timer r2 = r6.getWakeTime(r1)
            java.lang.String r5 = "f"
            r1 = r7
            r3 = r176
            r195 = r14
            r14 = r6
            r6 = r239
            r15 = r7
            r7 = r108
            java.lang.String r108 = printWakeLockCheckin(r1, r2, r3, r5, r6, r7)
            r1 = 0
            android.os.BatteryStats$Timer r109 = r14.getWakeTime(r1)
            java.lang.String r5 = "p"
            r1 = r15
            r2 = r109
            r7 = r108
            java.lang.String r108 = printWakeLockCheckin(r1, r2, r3, r5, r6, r7)
            if (r109 == 0) goto L_0x0c9c
            android.os.BatteryStats$Timer r1 = r109.getSubTimer()
        L_0x0c9a:
            r2 = r1
            goto L_0x0c9e
        L_0x0c9c:
            r1 = 0
            goto L_0x0c9a
        L_0x0c9e:
            java.lang.String r5 = "bp"
            r1 = r15
            r3 = r176
            r6 = r239
            r7 = r108
            java.lang.String r108 = printWakeLockCheckin(r1, r2, r3, r5, r6, r7)
            r1 = 2
            android.os.BatteryStats$Timer r2 = r14.getWakeTime(r1)
            java.lang.String r5 = "w"
            r1 = r15
            r7 = r108
            java.lang.String r1 = printWakeLockCheckin(r1, r2, r3, r5, r6, r7)
            int r2 = r15.length()
            if (r2 <= 0) goto L_0x0d08
            java.lang.Object r2 = r0.keyAt(r8)
            java.lang.String r2 = (java.lang.String) r2
            r3 = 44
            int r3 = r2.indexOf(r3)
            if (r3 < 0) goto L_0x0cd6
            r3 = 44
            r4 = 95
            java.lang.String r2 = r2.replace(r3, r4)
        L_0x0cd6:
            r6 = 10
            int r3 = r2.indexOf(r6)
            if (r3 < 0) goto L_0x0ce4
            r3 = 95
            java.lang.String r2 = r2.replace(r6, r3)
        L_0x0ce4:
            r3 = 13
            int r3 = r2.indexOf(r3)
            if (r3 < 0) goto L_0x0cf4
            r3 = 13
            r4 = 95
            java.lang.String r2 = r2.replace(r3, r4)
        L_0x0cf4:
            java.lang.String r3 = "wl"
            r4 = 2
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r4 = 0
            r5[r4] = r2
            java.lang.String r4 = r15.toString()
            r7 = 1
            r5[r7] = r4
            dumpLine(r9, r13, r12, r3, r5)
            goto L_0x0d0a
        L_0x0d08:
            r6 = 10
        L_0x0d0a:
            int r1 = r8 + -1
            r7 = r15
            r14 = r195
            goto L_0x0c5d
        L_0x0d11:
            r195 = r14
            r6 = 10
            r15 = r7
            android.os.BatteryStats$Timer r14 = r11.getMulticastWakelockStats()
            if (r14 == 0) goto L_0x0d4b
            r7 = r176
            long r1 = r14.getTotalTimeLocked(r7, r10)
            r3 = 1000(0x3e8, double:4.94E-321)
            long r1 = r1 / r3
            int r3 = r14.getCountLocked(r10)
            int r4 = (r1 > r110 ? 1 : (r1 == r110 ? 0 : -1))
            if (r4 <= 0) goto L_0x0d48
            java.lang.String r4 = "wmc"
            r5 = 2
            java.lang.Object[] r6 = new java.lang.Object[r5]
            java.lang.Long r5 = java.lang.Long.valueOf(r1)
            r67 = 0
            r6[r67] = r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            r62 = 1
            r6[r62] = r5
            dumpLine(r9, r13, r12, r4, r6)
            goto L_0x0d4f
        L_0x0d48:
            r62 = 1
            goto L_0x0d4f
        L_0x0d4b:
            r7 = r176
            r62 = 1
        L_0x0d4f:
            android.util.ArrayMap r6 = r11.getSyncStats()
            int r1 = r6.size()
            int r1 = r1 + -1
        L_0x0d59:
            if (r1 < 0) goto L_0x0df0
            java.lang.Object r2 = r6.valueAt(r1)
            android.os.BatteryStats$Timer r2 = (android.os.BatteryStats.Timer) r2
            long r3 = r2.getTotalTimeLocked(r7, r10)
            long r3 = r3 + r81
            r63 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 / r63
            int r5 = r2.getCountLocked(r10)
            r197 = r0
            android.os.BatteryStats$Timer r0 = r2.getSubTimer()
            if (r0 == 0) goto L_0x0d82
            r199 = r14
            r198 = r15
            r14 = r195
            long r108 = r0.getTotalDurationMsLocked(r14)
            goto L_0x0d8a
        L_0x0d82:
            r199 = r14
            r198 = r15
            r14 = r195
            r108 = -1
        L_0x0d8a:
            if (r0 == 0) goto L_0x0d91
            int r75 = r0.getCountLocked(r10)
            goto L_0x0d93
        L_0x0d91:
            r75 = r106
        L_0x0d93:
            int r173 = (r3 > r110 ? 1 : (r3 == r110 ? 0 : -1))
            if (r173 == 0) goto L_0x0de2
            r200 = r0
            java.lang.String r0 = "sy"
            r201 = r2
            r202 = r14
            r2 = 5
            java.lang.Object[] r14 = new java.lang.Object[r2]
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r15 = "\""
            r2.append(r15)
            java.lang.Object r15 = r6.keyAt(r1)
            java.lang.String r15 = (java.lang.String) r15
            r2.append(r15)
            java.lang.String r15 = "\""
            r2.append(r15)
            java.lang.String r2 = r2.toString()
            r15 = 0
            r14[r15] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r3)
            r15 = 1
            r14[r15] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            r15 = 2
            r14[r15] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r108)
            r15 = 3
            r14[r15] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r75)
            r15 = 4
            r14[r15] = r2
            dumpLine(r9, r13, r12, r0, r14)
            goto L_0x0de4
        L_0x0de2:
            r202 = r14
        L_0x0de4:
            int r1 = r1 + -1
            r0 = r197
            r15 = r198
            r14 = r199
            r195 = r202
            goto L_0x0d59
        L_0x0df0:
            r197 = r0
            r199 = r14
            r198 = r15
            r202 = r195
            android.util.ArrayMap r0 = r11.getJobStats()
            int r1 = r0.size()
            r2 = 1
            int r1 = r1 - r2
        L_0x0e02:
            if (r1 < 0) goto L_0x0e91
            java.lang.Object r2 = r0.valueAt(r1)
            android.os.BatteryStats$Timer r2 = (android.os.BatteryStats.Timer) r2
            long r3 = r2.getTotalTimeLocked(r7, r10)
            long r3 = r3 + r81
            r14 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 / r14
            int r5 = r2.getCountLocked(r10)
            android.os.BatteryStats$Timer r14 = r2.getSubTimer()
            if (r14 == 0) goto L_0x0e28
            r206 = r6
            r204 = r7
            r6 = r202
            long r108 = r14.getTotalDurationMsLocked(r6)
            goto L_0x0e30
        L_0x0e28:
            r206 = r6
            r204 = r7
            r6 = r202
            r108 = -1
        L_0x0e30:
            if (r14 == 0) goto L_0x0e37
            int r8 = r14.getCountLocked(r10)
            goto L_0x0e39
        L_0x0e37:
            r8 = r106
        L_0x0e39:
            int r15 = (r3 > r110 ? 1 : (r3 == r110 ? 0 : -1))
            if (r15 == 0) goto L_0x0e85
            java.lang.String r15 = "jb"
            r207 = r2
            r208 = r6
            r2 = 5
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r7 = "\""
            r2.append(r7)
            java.lang.Object r7 = r0.keyAt(r1)
            java.lang.String r7 = (java.lang.String) r7
            r2.append(r7)
            java.lang.String r7 = "\""
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            r7 = 0
            r6[r7] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r3)
            r7 = 1
            r6[r7] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            r7 = 2
            r6[r7] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r108)
            r7 = 3
            r6[r7] = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)
            r7 = 4
            r6[r7] = r2
            dumpLine(r9, r13, r12, r15, r6)
            goto L_0x0e87
        L_0x0e85:
            r208 = r6
        L_0x0e87:
            int r1 = r1 + -1
            r7 = r204
            r6 = r206
            r202 = r208
            goto L_0x0e02
        L_0x0e91:
            r206 = r6
            r204 = r7
            r208 = r202
            android.util.ArrayMap r14 = r11.getJobCompletionStats()
            int r1 = r14.size()
            r2 = 1
            int r1 = r1 - r2
        L_0x0ea1:
            if (r1 < 0) goto L_0x0f0e
            java.lang.Object r2 = r14.valueAt(r1)
            android.util.SparseIntArray r2 = (android.util.SparseIntArray) r2
            if (r2 == 0) goto L_0x0f0a
            java.lang.String r3 = "jbc"
            r4 = 6
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "\""
            r4.append(r6)
            java.lang.Object r6 = r14.keyAt(r1)
            java.lang.String r6 = (java.lang.String) r6
            r4.append(r6)
            java.lang.String r6 = "\""
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r6 = 0
            r5[r6] = r4
            int r4 = r2.get(r6, r6)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r7 = 1
            r5[r7] = r4
            int r4 = r2.get(r7, r6)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r8 = 2
            r5[r8] = r4
            int r4 = r2.get(r8, r6)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r7 = 3
            r5[r7] = r4
            int r4 = r2.get(r7, r6)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r7 = 4
            r5[r7] = r4
            int r4 = r2.get(r7, r6)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r6 = 5
            r5[r6] = r4
            dumpLine(r9, r13, r12, r3, r5)
            goto L_0x0f0b
        L_0x0f0a:
            r8 = 2
        L_0x0f0b:
            int r1 = r1 + -1
            goto L_0x0ea1
        L_0x0f0e:
            r8 = 2
            r15 = r198
            r11.getDeferredJobsCheckinLineLocked(r15, r10)
            int r1 = r15.length()
            if (r1 <= 0) goto L_0x0f29
            java.lang.String r1 = "jbd"
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.String r2 = r15.toString()
            r4 = 0
            r3[r4] = r2
            dumpLine(r9, r13, r12, r1, r3)
        L_0x0f29:
            java.lang.String r4 = "fla"
            android.os.BatteryStats$Timer r5 = r11.getFlashlightTurnedOnTimer()
            r1 = r238
            r2 = r13
            r3 = r12
            r210 = r14
            r211 = r15
            r212 = r204
            r75 = r206
            r14 = r208
            r107 = 10
            r6 = r212
            r214 = r0
            r0 = r8
            r109 = r122
            r108 = r175
            r8 = r239
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            java.lang.String r4 = "cam"
            android.os.BatteryStats$Timer r5 = r11.getCameraTurnedOnTimer()
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            java.lang.String r4 = "vid"
            android.os.BatteryStats$Timer r5 = r11.getVideoTurnedOnTimer()
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            java.lang.String r4 = "aud"
            android.os.BatteryStats$Timer r5 = r11.getAudioTurnedOnTimer()
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            android.util.SparseArray r8 = r11.getSensorStats()
            int r6 = r8.size()
            r1 = 0
        L_0x0f72:
            if (r1 >= r6) goto L_0x1000
            java.lang.Object r2 = r8.valueAt(r1)
            android.os.BatteryStats$Uid$Sensor r2 = (android.os.BatteryStats.Uid.Sensor) r2
            int r3 = r8.keyAt(r1)
            android.os.BatteryStats$Timer r4 = r2.getSensorTime()
            if (r4 == 0) goto L_0x0ff5
            r215 = r1
            r0 = r212
            long r175 = r4.getTotalTimeLocked(r0, r10)
            long r175 = r175 + r81
            r63 = 1000(0x3e8, double:4.94E-321)
            long r175 = r175 / r63
            int r5 = (r175 > r110 ? 1 : (r175 == r110 ? 0 : -1))
            if (r5 == 0) goto L_0x0ff2
            int r5 = r4.getCountLocked(r10)
            android.os.BatteryStats$Timer r7 = r2.getSensorBackgroundTime()
            if (r7 == 0) goto L_0x0fa5
            int r122 = r7.getCountLocked(r10)
            goto L_0x0fa7
        L_0x0fa5:
            r122 = 0
        L_0x0fa7:
            long r177 = r4.getTotalDurationMsLocked(r14)
            if (r7 == 0) goto L_0x0fb2
            long r179 = r7.getTotalDurationMsLocked(r14)
            goto L_0x0fb4
        L_0x0fb2:
            r179 = r110
        L_0x0fb4:
            r216 = r0
            java.lang.String r0 = "sr"
            r218 = r2
            r1 = 6
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            r67 = 0
            r2[r67] = r1
            java.lang.Long r1 = java.lang.Long.valueOf(r175)
            r62 = 1
            r2[r62] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            r173 = 2
            r2[r173] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r122)
            r68 = 3
            r2[r68] = r1
            java.lang.Long r1 = java.lang.Long.valueOf(r177)
            r69 = 4
            r2[r69] = r1
            java.lang.Long r1 = java.lang.Long.valueOf(r179)
            r70 = 5
            r2[r70] = r1
            dumpLine(r9, r13, r12, r0, r2)
            goto L_0x0ff9
        L_0x0ff2:
            r216 = r0
            goto L_0x0ff9
        L_0x0ff5:
            r215 = r1
            r216 = r212
        L_0x0ff9:
            int r1 = r215 + 1
            r212 = r216
            r0 = 2
            goto L_0x0f72
        L_0x1000:
            r216 = r212
            java.lang.String r4 = "vib"
            android.os.BatteryStats$Timer r5 = r11.getVibratorOnTimer()
            r2 = r216
            r1 = r238
            r219 = r2
            r2 = r13
            r3 = r12
            r0 = r6
            r6 = r219
            r122 = r8
            r8 = r239
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            java.lang.String r4 = "fg"
            android.os.BatteryStats$Timer r5 = r11.getForegroundActivityTimer()
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            java.lang.String r4 = "fgs"
            android.os.BatteryStats$Timer r5 = r11.getForegroundServiceTimer()
            dumpTimer(r1, r2, r3, r4, r5, r6, r8)
            r1 = 7
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r4 = r3
            r3 = 0
        L_0x1034:
            if (r3 >= r1) goto L_0x104e
            r6 = r219
            long r175 = r11.getProcessStateTime(r3, r6, r10)
            long r4 = r4 + r175
            long r177 = r175 + r81
            r63 = 1000(0x3e8, double:4.94E-321)
            long r177 = r177 / r63
            java.lang.Long r1 = java.lang.Long.valueOf(r177)
            r2[r3] = r1
            int r3 = r3 + 1
            r1 = 7
            goto L_0x1034
        L_0x104e:
            r6 = r219
            int r1 = (r4 > r110 ? 1 : (r4 == r110 ? 0 : -1))
            if (r1 <= 0) goto L_0x105a
            java.lang.String r1 = "st"
            dumpLine(r9, r13, r12, r1, r2)
        L_0x105a:
            long r175 = r11.getUserCpuTimeUs(r10)
            long r177 = r11.getSystemCpuTimeUs(r10)
            int r1 = (r175 > r110 ? 1 : (r175 == r110 ? 0 : -1))
            if (r1 > 0) goto L_0x106a
            int r1 = (r177 > r110 ? 1 : (r177 == r110 ? 0 : -1))
            if (r1 <= 0) goto L_0x1090
        L_0x106a:
            java.lang.String r1 = "cpu"
            r3 = 3
            java.lang.Object[] r8 = new java.lang.Object[r3]
            r63 = 1000(0x3e8, double:4.94E-321)
            long r179 = r175 / r63
            java.lang.Long r3 = java.lang.Long.valueOf(r179)
            r67 = 0
            r8[r67] = r3
            long r179 = r177 / r63
            java.lang.Long r3 = java.lang.Long.valueOf(r179)
            r62 = 1
            r8[r62] = r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r67)
            r173 = 2
            r8[r173] = r3
            dumpLine(r9, r13, r12, r1, r8)
        L_0x1090:
            if (r192 == 0) goto L_0x11d5
            long[] r1 = r11.getCpuFreqTimes(r10)
            if (r1 == 0) goto L_0x1133
            int r3 = r1.length
            r221 = r0
            r8 = r192
            int r0 = r8.length
            if (r3 != r0) goto L_0x112a
            r0 = r211
            r3 = 0
            r0.setLength(r3)
            r3 = 0
        L_0x10a7:
            r222 = r2
            int r2 = r1.length
            if (r3 >= r2) goto L_0x10d3
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            if (r3 != 0) goto L_0x10ba
            java.lang.String r173 = ""
        L_0x10b5:
            r223 = r4
            r4 = r173
            goto L_0x10bd
        L_0x10ba:
            java.lang.String r173 = ","
            goto L_0x10b5
        L_0x10bd:
            r2.append(r4)
            r4 = r1[r3]
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.append(r2)
            int r3 = r3 + 1
            r2 = r222
            r4 = r223
            goto L_0x10a7
        L_0x10d3:
            r223 = r4
            long[] r2 = r11.getScreenOffCpuFreqTimes(r10)
            if (r2 == 0) goto L_0x10ff
            r3 = 0
        L_0x10dc:
            int r4 = r2.length
            if (r3 >= r4) goto L_0x10fc
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = ","
            r4.append(r5)
            r225 = r6
            r5 = r2[r3]
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.append(r4)
            int r3 = r3 + 1
            r6 = r225
            goto L_0x10dc
        L_0x10fc:
            r225 = r6
            goto L_0x110d
        L_0x10ff:
            r225 = r6
            r3 = 0
        L_0x1102:
            int r4 = r1.length
            if (r3 >= r4) goto L_0x110d
            java.lang.String r4 = ",0"
            r0.append(r4)
            int r3 = r3 + 1
            goto L_0x1102
        L_0x110d:
            java.lang.String r3 = "ctf"
            r4 = 3
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.String r4 = "A"
            r6 = 0
            r5[r6] = r4
            int r4 = r1.length
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r6 = 1
            r5[r6] = r4
            java.lang.String r4 = r0.toString()
            r6 = 2
            r5[r6] = r4
            dumpLine(r9, r13, r12, r3, r5)
            goto L_0x113f
        L_0x112a:
            r222 = r2
            r223 = r4
            r225 = r6
            r0 = r211
            goto L_0x113f
        L_0x1133:
            r221 = r0
            r222 = r2
            r223 = r4
            r225 = r6
            r8 = r192
            r0 = r211
        L_0x113f:
            r2 = 0
        L_0x1140:
            r3 = 7
            if (r2 >= r3) goto L_0x11d2
            long[] r3 = r11.getCpuFreqTimes(r10, r2)
            if (r3 == 0) goto L_0x11ca
            int r4 = r3.length
            int r5 = r8.length
            if (r4 != r5) goto L_0x11ca
            r4 = 0
            r0.setLength(r4)
            r4 = 0
        L_0x1152:
            int r5 = r3.length
            if (r4 >= r5) goto L_0x1173
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            if (r4 != 0) goto L_0x115f
            java.lang.String r6 = ""
            goto L_0x1161
        L_0x115f:
            java.lang.String r6 = ","
        L_0x1161:
            r5.append(r6)
            r6 = r3[r4]
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.append(r5)
            int r4 = r4 + 1
            goto L_0x1152
        L_0x1173:
            long[] r4 = r11.getScreenOffCpuFreqTimes(r10, r2)
            if (r4 == 0) goto L_0x119d
            r5 = 0
        L_0x117a:
            int r6 = r4.length
            if (r5 >= r6) goto L_0x119a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = ","
            r6.append(r7)
            r227 = r8
            r7 = r4[r5]
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.append(r6)
            int r5 = r5 + 1
            r8 = r227
            goto L_0x117a
        L_0x119a:
            r227 = r8
            goto L_0x11ab
        L_0x119d:
            r227 = r8
            r5 = 0
        L_0x11a0:
            int r6 = r3.length
            if (r5 >= r6) goto L_0x11ab
            java.lang.String r6 = ",0"
            r0.append(r6)
            int r5 = r5 + 1
            goto L_0x11a0
        L_0x11ab:
            java.lang.String r5 = "ctf"
            r6 = 3
            java.lang.Object[] r7 = new java.lang.Object[r6]
            java.lang.String[] r6 = android.os.BatteryStats.Uid.UID_PROCESS_TYPES
            r6 = r6[r2]
            r8 = 0
            r7[r8] = r6
            int r6 = r3.length
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r8 = 1
            r7[r8] = r6
            java.lang.String r6 = r0.toString()
            r8 = 2
            r7[r8] = r6
            dumpLine(r9, r13, r12, r5, r7)
            goto L_0x11cc
        L_0x11ca:
            r227 = r8
        L_0x11cc:
            int r2 = r2 + 1
            r8 = r227
            goto L_0x1140
        L_0x11d2:
            r227 = r8
            goto L_0x11e1
        L_0x11d5:
            r221 = r0
            r222 = r2
            r223 = r4
            r225 = r6
            r227 = r192
            r0 = r211
        L_0x11e1:
            android.util.ArrayMap r1 = r11.getProcessStats()
            int r2 = r1.size()
            r3 = 1
            int r2 = r2 - r3
        L_0x11eb:
            if (r2 < 0) goto L_0x1283
            java.lang.Object r3 = r1.valueAt(r2)
            android.os.BatteryStats$Uid$Proc r3 = (android.os.BatteryStats.Uid.Proc) r3
            long r4 = r3.getUserTime(r10)
            long r6 = r3.getSystemTime(r10)
            long r179 = r3.getForegroundTime(r10)
            int r8 = r3.getStarts(r10)
            int r173 = r3.getNumCrashes(r10)
            int r181 = r3.getNumAnrs(r10)
            int r182 = (r4 > r110 ? 1 : (r4 == r110 ? 0 : -1))
            if (r182 != 0) goto L_0x1223
            int r182 = (r6 > r110 ? 1 : (r6 == r110 ? 0 : -1))
            if (r182 != 0) goto L_0x1223
            int r182 = (r179 > r110 ? 1 : (r179 == r110 ? 0 : -1))
            if (r182 != 0) goto L_0x1223
            if (r8 != 0) goto L_0x1223
            if (r181 != 0) goto L_0x1223
            if (r173 == 0) goto L_0x121e
            goto L_0x1223
        L_0x121e:
            r228 = r0
            r230 = r14
            goto L_0x127b
        L_0x1223:
            r228 = r0
            java.lang.String r0 = "pr"
            r229 = r3
            r230 = r14
            r3 = 7
            java.lang.Object[] r14 = new java.lang.Object[r3]
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r3 = "\""
            r15.append(r3)
            java.lang.Object r3 = r1.keyAt(r2)
            java.lang.String r3 = (java.lang.String) r3
            r15.append(r3)
            java.lang.String r3 = "\""
            r15.append(r3)
            java.lang.String r3 = r15.toString()
            r15 = 0
            r14[r15] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r4)
            r15 = 1
            r14[r15] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r6)
            r15 = 2
            r14[r15] = r3
            java.lang.Long r3 = java.lang.Long.valueOf(r179)
            r15 = 3
            r14[r15] = r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            r15 = 4
            r14[r15] = r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r181)
            r15 = 5
            r14[r15] = r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r173)
            r15 = 6
            r14[r15] = r3
            dumpLine(r9, r13, r12, r0, r14)
        L_0x127b:
            int r2 = r2 + -1
            r0 = r228
            r14 = r230
            goto L_0x11eb
        L_0x1283:
            r228 = r0
            r230 = r14
            android.util.ArrayMap r0 = r11.getPackageStats()
            int r2 = r0.size()
            r3 = 1
            int r2 = r2 - r3
        L_0x1291:
            if (r2 < 0) goto L_0x137d
            java.lang.Object r3 = r0.valueAt(r2)
            android.os.BatteryStats$Uid$Pkg r3 = (android.os.BatteryStats.Uid.Pkg) r3
            r4 = 0
            android.util.ArrayMap r5 = r3.getWakeupAlarmStats()
            int r6 = r5.size()
            r7 = 1
            int r6 = r6 - r7
        L_0x12a4:
            if (r6 < 0) goto L_0x12da
            java.lang.Object r7 = r5.valueAt(r6)
            android.os.BatteryStats$Counter r7 = (android.os.BatteryStats.Counter) r7
            int r7 = r7.getCountLocked(r10)
            int r4 = r4 + r7
            java.lang.Object r8 = r5.keyAt(r6)
            java.lang.String r8 = (java.lang.String) r8
            r14 = 44
            r15 = 95
            java.lang.String r8 = r8.replace(r14, r15)
            java.lang.String r14 = "wua"
            r232 = r1
            r15 = 2
            java.lang.Object[] r1 = new java.lang.Object[r15]
            r15 = 0
            r1[r15] = r8
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)
            r62 = 1
            r1[r62] = r15
            dumpLine(r9, r13, r12, r14, r1)
            int r6 = r6 + -1
            r1 = r232
            goto L_0x12a4
        L_0x12da:
            r232 = r1
            r62 = 1
            android.util.ArrayMap r1 = r3.getServiceStats()
            int r6 = r1.size()
            int r6 = r6 + -1
        L_0x12e8:
            if (r6 < 0) goto L_0x1366
            java.lang.Object r7 = r1.valueAt(r6)
            android.os.BatteryStats$Uid$Pkg$Serv r7 = (android.os.BatteryStats.Uid.Pkg.Serv) r7
            r14 = r102
            long r102 = r7.getStartTime(r14, r10)
            int r8 = r7.getStarts(r10)
            int r72 = r7.getLaunches(r10)
            int r173 = (r102 > r110 ? 1 : (r102 == r110 ? 0 : -1))
            if (r173 != 0) goto L_0x131b
            if (r8 != 0) goto L_0x131b
            if (r72 == 0) goto L_0x1307
            goto L_0x131b
        L_0x1307:
            r233 = r3
            r234 = r5
            r5 = 6
            r62 = 1
            r63 = 1000(0x3e8, double:4.94E-321)
            r67 = 0
            r68 = 3
            r69 = 4
            r70 = 5
            r173 = 2
            goto L_0x135d
        L_0x131b:
            r233 = r3
            java.lang.String r3 = "apk"
            r234 = r5
            r235 = r7
            r5 = 6
            java.lang.Object[] r7 = new java.lang.Object[r5]
            java.lang.Integer r71 = java.lang.Integer.valueOf(r4)
            r67 = 0
            r7[r67] = r71
            java.lang.Object r71 = r0.keyAt(r2)
            r62 = 1
            r7[r62] = r71
            java.lang.Object r71 = r1.keyAt(r6)
            r173 = 2
            r7[r173] = r71
            r63 = 1000(0x3e8, double:4.94E-321)
            long r179 = r102 / r63
            java.lang.Long r71 = java.lang.Long.valueOf(r179)
            r68 = 3
            r7[r68] = r71
            java.lang.Integer r71 = java.lang.Integer.valueOf(r8)
            r69 = 4
            r7[r69] = r71
            java.lang.Integer r71 = java.lang.Integer.valueOf(r72)
            r70 = 5
            r7[r70] = r71
            dumpLine(r9, r13, r12, r3, r7)
        L_0x135d:
            int r6 = r6 + -1
            r102 = r14
            r3 = r233
            r5 = r234
            goto L_0x12e8
        L_0x1366:
            r14 = r102
            r5 = 6
            r62 = 1
            r63 = 1000(0x3e8, double:4.94E-321)
            r67 = 0
            r68 = 3
            r69 = 4
            r70 = 5
            r173 = 2
            int r2 = r2 + -1
            r1 = r232
            goto L_0x1291
        L_0x137d:
            r14 = r102
            r5 = 6
            r62 = 1
            r63 = 1000(0x3e8, double:4.94E-321)
            r67 = 0
            r68 = 3
            r69 = 4
            r70 = 5
            r173 = 2
        L_0x138e:
            int r1 = r174 + 1
            r8 = r241
            r102 = r14
            r106 = r108
            r122 = r109
            r126 = r112
            r13 = r166
            r123 = r225
            r0 = r227
            r7 = r228
            r108 = r230
            r11 = r240
            goto L_0x0866
        L_0x13a8:
            r227 = r0
            r228 = r7
            r166 = r13
            r14 = r102
            r230 = r108
            r109 = r122
            r225 = r123
            r112 = r126
            r108 = r106
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpCheckinLocked(android.content.Context, java.io.PrintWriter, int, int, boolean):void");
    }

    static final class TimerEntry {
        final int mId;
        final String mName;
        final long mTime;
        final Timer mTimer;

        TimerEntry(String name, int id, Timer timer, long time) {
            this.mName = name;
            this.mId = id;
            this.mTimer = timer;
            this.mTime = time;
        }
    }

    private void printmAh(PrintWriter printer, double power) {
        printer.print(BatteryStatsHelper.makemAh(power));
    }

    private void printmAh(StringBuilder sb, double power) {
        sb.append(BatteryStatsHelper.makemAh(power));
    }

    public final void dumpLocked(Context context, PrintWriter pw, String prefix, int which, int reqUid) {
        dumpLocked(context, pw, prefix, which, reqUid, BatteryStatsHelper.checkWifiOnly(context));
    }

    /* JADX WARNING: Removed duplicated region for block: B:286:0x1074  */
    /* JADX WARNING: Removed duplicated region for block: B:291:0x10d9  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x10e5  */
    /* JADX WARNING: Removed duplicated region for block: B:391:0x16a3  */
    /* JADX WARNING: Removed duplicated region for block: B:392:0x16bd  */
    /* JADX WARNING: Removed duplicated region for block: B:397:0x1707  */
    /* JADX WARNING: Removed duplicated region for block: B:398:0x170c  */
    /* JADX WARNING: Removed duplicated region for block: B:401:0x1736  */
    /* JADX WARNING: Removed duplicated region for block: B:474:0x1930  */
    /* JADX WARNING: Removed duplicated region for block: B:477:0x1949  */
    /* JADX WARNING: Removed duplicated region for block: B:490:0x198f  */
    /* JADX WARNING: Removed duplicated region for block: B:494:0x19b7  */
    /* JADX WARNING: Removed duplicated region for block: B:823:0x1aaa A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dumpLocked(android.content.Context r417, java.io.PrintWriter r418, java.lang.String r419, int r420, int r421, boolean r422) {
        /*
            r416 = this;
            r7 = r416
            r15 = r418
            r14 = r419
            r13 = r420
            r11 = r421
            if (r13 == 0) goto L_0x0026
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "ERROR: BatteryStats.dump called for which type "
            r0.append(r1)
            r0.append(r13)
            java.lang.String r1 = " but only STATS_SINCE_CHARGED is supported"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r15.println(r0)
            return
        L_0x0026:
            long r0 = android.os.SystemClock.uptimeMillis()
            r16 = 1000(0x3e8, double:4.94E-321)
            long r9 = r0 * r16
            long r0 = android.os.SystemClock.elapsedRealtime()
            long r5 = r0 * r16
            r18 = 500(0x1f4, double:2.47E-321)
            long r0 = r5 + r18
            long r3 = r0 / r16
            long r1 = r7.getBatteryUptime(r9)
            r20 = r3
            long r3 = r7.computeBatteryUptime(r9, r13)
            r22 = r1
            long r1 = r7.computeBatteryRealtime(r5, r13)
            long r11 = r7.computeRealtime(r5, r13)
            long r24 = r7.computeUptime(r9, r13)
            r26 = r3
            long r3 = r7.computeBatteryScreenOffUptime(r9, r13)
            r28 = r9
            long r9 = r7.computeBatteryScreenOffRealtime(r5, r13)
            long r30 = r7.computeBatteryTimeRemaining(r5)
            long r32 = r7.computeChargeTimeRemaining(r5)
            r34 = r3
            long r3 = r7.getScreenDozeTime(r5, r13)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r8 = 128(0x80, float:1.794E-43)
            r0.<init>(r8)
            r8 = r0
            android.util.SparseArray r0 = r416.getUidStats()
            r36 = r5
            int r5 = r0.size()
            int r6 = r416.getEstimatedBatteryCapacity()
            r38 = r0
            r0 = 0
            if (r6 <= 0) goto L_0x00a9
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Estimated battery capacity: "
            r8.append(r0)
            r40 = r3
            double r3 = (double) r6
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r3)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x00ab
        L_0x00a9:
            r40 = r3
        L_0x00ab:
            int r4 = r416.getMinLearnedBatteryCapacity()
            if (r4 <= 0) goto L_0x00d6
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Min learned battery capacity: "
            r8.append(r0)
            int r0 = r4 / 1000
            r42 = r4
            double r3 = (double) r0
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r3)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x00d8
        L_0x00d6:
            r42 = r4
        L_0x00d8:
            int r4 = r416.getMaxLearnedBatteryCapacity()
            if (r4 <= 0) goto L_0x0103
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Max learned battery capacity: "
            r8.append(r0)
            int r0 = r4 / 1000
            r43 = r4
            double r3 = (double) r0
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r3)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x0105
        L_0x0103:
            r43 = r4
        L_0x0105:
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Time on battery: "
            r8.append(r0)
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r1, r11)
            r8.append(r0)
            java.lang.String r0 = ") realtime, "
            r8.append(r0)
            long r3 = r26 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            r3 = r26
            java.lang.String r0 = r7.formatRatioLocked(r3, r1)
            r8.append(r0)
            java.lang.String r0 = ") uptime"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Time on battery screen off: "
            r8.append(r0)
            r44 = r3
            long r3 = r9 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r9, r1)
            r8.append(r0)
            java.lang.String r0 = ") realtime, "
            r8.append(r0)
            long r3 = r34 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            r3 = r34
            java.lang.String r0 = r7.formatRatioLocked(r3, r1)
            r8.append(r0)
            java.lang.String r0 = ") uptime"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Time on battery screen doze: "
            r8.append(r0)
            r46 = r3
            long r3 = r40 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            r3 = r40
            java.lang.String r0 = r7.formatRatioLocked(r3, r1)
            r8.append(r0)
            java.lang.String r0 = ")"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Total run time: "
            r8.append(r0)
            r48 = r3
            long r3 = r11 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "realtime, "
            r8.append(r0)
            long r3 = r24 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "uptime"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r26 = 0
            int r0 = (r30 > r26 ? 1 : (r30 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x01ff
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Battery time remaining: "
            r8.append(r0)
            long r3 = r30 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
        L_0x01ff:
            int r0 = (r32 > r26 ? 1 : (r32 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x021b
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Charge time remaining: "
            r8.append(r0)
            long r3 = r32 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
        L_0x021b:
            long r3 = r7.getUahDischarge(r13)
            int r0 = (r3 > r26 ? 1 : (r3 == r26 ? 0 : -1))
            r34 = 4652007308841189376(0x408f400000000000, double:1000.0)
            if (r0 < 0) goto L_0x024d
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Discharge: "
            r8.append(r0)
            r50 = r9
            double r9 = (double) r3
            double r9 = r9 / r34
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r9)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x024f
        L_0x024d:
            r50 = r9
        L_0x024f:
            long r9 = r7.getUahDischargeScreenOff(r13)
            int r0 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x027c
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Screen off discharge: "
            r8.append(r0)
            r52 = r11
            double r11 = (double) r9
            double r11 = r11 / r34
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r11)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x027e
        L_0x027c:
            r52 = r11
        L_0x027e:
            long r11 = r7.getUahDischargeScreenDoze(r13)
            int r0 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x02ad
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Screen doze discharge: "
            r8.append(r0)
            r54 = r5
            r55 = r6
            double r5 = (double) r11
            double r5 = r5 / r34
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r5)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x02b1
        L_0x02ad:
            r54 = r5
            r55 = r6
        L_0x02b1:
            long r5 = r3 - r9
            int r0 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x02dc
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Screen on discharge: "
            r8.append(r0)
            r56 = r3
            double r3 = (double) r5
            double r3 = r3 / r34
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r3)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x02de
        L_0x02dc:
            r56 = r3
        L_0x02de:
            long r3 = r7.getUahDischargeLightDoze(r13)
            int r0 = (r3 > r26 ? 1 : (r3 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x030b
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Device light doze discharge: "
            r8.append(r0)
            r58 = r5
            double r5 = (double) r3
            double r5 = r5 / r34
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r5)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x030d
        L_0x030b:
            r58 = r5
        L_0x030d:
            long r5 = r7.getUahDischargeDeepDoze(r13)
            int r0 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r0 < 0) goto L_0x033a
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Device deep doze discharge: "
            r8.append(r0)
            r60 = r3
            double r3 = (double) r5
            double r3 = r3 / r34
            java.lang.String r0 = com.android.internal.os.BatteryStatsHelper.makemAh(r3)
            r8.append(r0)
            java.lang.String r0 = " mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x033c
        L_0x033a:
            r60 = r3
        L_0x033c:
            java.lang.String r0 = "  Start clock time: "
            r15.print(r0)
            java.lang.String r0 = "yyyy-MM-dd-HH-mm-ss"
            long r3 = r416.getStartClockTime()
            java.lang.CharSequence r0 = android.text.format.DateFormat.format((java.lang.CharSequence) r0, (long) r3)
            java.lang.String r0 = r0.toString()
            r15.println(r0)
            r62 = r11
            r3 = r36
            long r11 = r7.getScreenOnTime(r3, r13)
            r64 = r9
            long r9 = r7.getInteractiveTime(r3, r13)
            r66 = r5
            long r5 = r7.getPowerSaveModeEnabledTime(r3, r13)
            r0 = 1
            r68 = r5
            long r5 = r7.getDeviceIdleModeTime(r0, r3, r13)
            r0 = 2
            r71 = r5
            long r5 = r7.getDeviceIdleModeTime(r0, r3, r13)
            r74 = r5
            r0 = 1
            long r5 = r7.getDeviceIdlingTime(r0, r3, r13)
            r76 = r5
            r0 = 2
            long r5 = r7.getDeviceIdlingTime(r0, r3, r13)
            r78 = r5
            long r5 = r7.getPhoneOnTime(r3, r13)
            long r34 = r7.getGlobalWifiRunningTime(r3, r13)
            long r36 = r7.getWifiOnTime(r3, r13)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Screen on: "
            r8.append(r0)
            r80 = r5
            long r5 = r11 / r16
            formatTimeMs(r8, r5)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r11, r1)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            int r0 = r7.getScreenOnCount(r13)
            r8.append(r0)
            java.lang.String r0 = "x, Interactive: "
            r8.append(r0)
            long r5 = r9 / r16
            formatTimeMs(r8, r5)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r9, r1)
            r8.append(r0)
            java.lang.String r0 = ")"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Screen brightnesses:"
            r8.append(r0)
            r0 = 0
            r40 = r0
            r0 = 0
        L_0x03ef:
            r6 = 5
            if (r0 >= r6) goto L_0x0434
            long r5 = r7.getScreenBrightnessTime(r0, r3, r13)
            int r41 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r41 != 0) goto L_0x03fe
            r82 = r9
            goto L_0x042f
        L_0x03fe:
            r82 = r9
            java.lang.String r9 = "\n    "
            r8.append(r9)
            r8.append(r14)
            r9 = 1
            java.lang.String[] r10 = SCREEN_BRIGHTNESS_NAMES
            r10 = r10[r0]
            r8.append(r10)
            java.lang.String r10 = " "
            r8.append(r10)
            r84 = r9
            long r9 = r5 / r16
            formatTimeMs(r8, r9)
            java.lang.String r9 = "("
            r8.append(r9)
            java.lang.String r9 = r7.formatRatioLocked(r5, r11)
            r8.append(r9)
            java.lang.String r9 = ")"
            r8.append(r9)
            r40 = r84
        L_0x042f:
            int r0 = r0 + 1
            r9 = r82
            goto L_0x03ef
        L_0x0434:
            r82 = r9
            if (r40 != 0) goto L_0x043d
            java.lang.String r0 = " (no activity)"
            r8.append(r0)
        L_0x043d:
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            int r0 = (r68 > r26 ? 1 : (r68 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x0474
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Power save mode enabled: "
            r8.append(r0)
            long r9 = r68 / r16
            formatTimeMs(r8, r9)
            java.lang.String r0 = "("
            r8.append(r0)
            r9 = r68
            java.lang.String r0 = r7.formatRatioLocked(r9, r1)
            r8.append(r0)
            java.lang.String r0 = ")"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x0476
        L_0x0474:
            r9 = r68
        L_0x0476:
            int r0 = (r76 > r26 ? 1 : (r76 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x04b8
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Device light idling: "
            r8.append(r0)
            long r6 = r76 / r16
            formatTimeMs(r8, r6)
            java.lang.String r0 = "("
            r8.append(r0)
            r5 = r76
            r7 = r416
            java.lang.String r0 = r7.formatRatioLocked(r5, r1)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r86 = r5
            r0 = 1
            int r5 = r7.getDeviceIdlingCount(r0, r13)
            r8.append(r5)
            java.lang.String r0 = "x"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x04ba
        L_0x04b8:
            r86 = r76
        L_0x04ba:
            int r0 = (r71 > r26 ? 1 : (r71 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x0506
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Idle mode light time: "
            r8.append(r0)
            long r5 = r71 / r16
            formatTimeMs(r8, r5)
            java.lang.String r0 = "("
            r8.append(r0)
            r5 = r71
            java.lang.String r0 = r7.formatRatioLocked(r5, r1)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r88 = r5
            r0 = 1
            int r5 = r7.getDeviceIdleModeCount(r0, r13)
            r8.append(r5)
            java.lang.String r5 = "x"
            r8.append(r5)
            java.lang.String r5 = " -- longest "
            r8.append(r5)
            long r5 = r7.getLongestDeviceIdleModeTime(r0)
            formatTimeMs(r8, r5)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x0508
        L_0x0506:
            r88 = r71
        L_0x0508:
            int r0 = (r78 > r26 ? 1 : (r78 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x0548
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Device full idling: "
            r8.append(r0)
            long r5 = r78 / r16
            formatTimeMs(r8, r5)
            java.lang.String r0 = "("
            r8.append(r0)
            r5 = r78
            java.lang.String r0 = r7.formatRatioLocked(r5, r1)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r90 = r5
            r0 = 2
            int r5 = r7.getDeviceIdlingCount(r0, r13)
            r8.append(r5)
            java.lang.String r0 = "x"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x054a
        L_0x0548:
            r90 = r78
        L_0x054a:
            int r0 = (r74 > r26 ? 1 : (r74 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x0596
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Idle mode full time: "
            r8.append(r0)
            long r5 = r74 / r16
            formatTimeMs(r8, r5)
            java.lang.String r0 = "("
            r8.append(r0)
            r5 = r74
            java.lang.String r0 = r7.formatRatioLocked(r5, r1)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r92 = r5
            r0 = 2
            int r5 = r7.getDeviceIdleModeCount(r0, r13)
            r8.append(r5)
            java.lang.String r5 = "x"
            r8.append(r5)
            java.lang.String r5 = " -- longest "
            r8.append(r5)
            long r5 = r7.getLongestDeviceIdleModeTime(r0)
            formatTimeMs(r8, r5)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x0598
        L_0x0596:
            r92 = r74
        L_0x0598:
            int r0 = (r80 > r26 ? 1 : (r80 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x05ce
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Active phone call: "
            r8.append(r0)
            long r5 = r80 / r16
            formatTimeMs(r8, r5)
            java.lang.String r0 = "("
            r8.append(r0)
            r5 = r80
            java.lang.String r0 = r7.formatRatioLocked(r5, r1)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            int r0 = r7.getPhoneOnCount(r13)
            r8.append(r0)
            java.lang.String r0 = "x"
            r8.append(r0)
            goto L_0x05d0
        L_0x05ce:
            r5 = r80
        L_0x05d0:
            int r0 = r7.getNumConnectivityChange(r13)
            if (r0 == 0) goto L_0x05e4
            r418.print(r419)
            r94 = r5
            java.lang.String r5 = "  Connectivity changes: "
            r15.print(r5)
            r15.println(r0)
            goto L_0x05e6
        L_0x05e4:
            r94 = r5
        L_0x05e6:
            r5 = 0
            r68 = 0
            java.util.ArrayList r41 = new java.util.ArrayList
            r41.<init>()
            r96 = r41
            r71 = r68
            r68 = r5
            r5 = 0
        L_0x05f6:
            r6 = r54
            if (r5 >= r6) goto L_0x0690
            r97 = r0
            r0 = r38
            java.lang.Object r38 = r0.valueAt(r5)
            android.os.BatteryStats$Uid r38 = (android.os.BatteryStats.Uid) r38
            r98 = r0
            android.util.ArrayMap r0 = r38.getWakelockStats()
            int r41 = r0.size()
            r99 = r6
            r6 = 1
            int r41 = r41 + -1
        L_0x0614:
            r100 = r41
            r6 = r100
            if (r6 < 0) goto L_0x067c
            java.lang.Object r41 = r0.valueAt(r6)
            r102 = r9
            r9 = r41
            android.os.BatteryStats$Uid$Wakelock r9 = (android.os.BatteryStats.Uid.Wakelock) r9
            r104 = r11
            r10 = 1
            android.os.BatteryStats$Timer r11 = r9.getWakeTime(r10)
            if (r11 == 0) goto L_0x0633
            long r74 = r11.getTotalTimeLocked(r3, r13)
            long r68 = r68 + r74
        L_0x0633:
            r10 = 0
            android.os.BatteryStats$Timer r12 = r9.getWakeTime(r10)
            if (r12 == 0) goto L_0x066c
            long r80 = r12.getTotalTimeLocked(r3, r13)
            int r10 = (r80 > r26 ? 1 : (r80 == r26 ? 0 : -1))
            if (r10 <= 0) goto L_0x066c
            r41 = r421
            if (r41 >= 0) goto L_0x0665
            android.os.BatteryStats$TimerEntry r10 = new android.os.BatteryStats$TimerEntry
            java.lang.Object r54 = r0.keyAt(r6)
            r75 = r54
            java.lang.String r75 = (java.lang.String) r75
            int r76 = r38.getUid()
            r74 = r10
            r77 = r12
            r78 = r80
            r74.<init>(r75, r76, r77, r78)
            r106 = r11
            r11 = r96
            r11.add(r10)
            goto L_0x0669
        L_0x0665:
            r106 = r11
            r11 = r96
        L_0x0669:
            long r71 = r71 + r80
            goto L_0x0670
        L_0x066c:
            r11 = r96
            r41 = r421
        L_0x0670:
            int r6 = r6 + -1
            r41 = r6
            r96 = r11
            r9 = r102
            r11 = r104
            r6 = 1
            goto L_0x0614
        L_0x067c:
            r102 = r9
            r104 = r11
            r11 = r96
            r41 = r421
            int r5 = r5 + 1
            r0 = r97
            r38 = r98
            r54 = r99
            r11 = r104
            goto L_0x05f6
        L_0x0690:
            r97 = r0
            r99 = r6
            r102 = r9
            r104 = r11
            r98 = r38
            r11 = r96
            r41 = r421
            r0 = 0
            long r9 = r7.getNetworkActivityBytes(r0, r13)
            r107 = r1
            r5 = 1
            long r0 = r7.getNetworkActivityBytes(r5, r13)
            r2 = 2
            long r5 = r7.getNetworkActivityBytes(r2, r13)
            r12 = 3
            r111 = r3
            long r2 = r7.getNetworkActivityBytes(r12, r13)
            r114 = r9
            r4 = 0
            long r9 = r7.getNetworkActivityPackets(r4, r13)
            r116 = r9
            r4 = 1
            long r9 = r7.getNetworkActivityPackets(r4, r13)
            r119 = r9
            r4 = 2
            long r9 = r7.getNetworkActivityPackets(r4, r13)
            r121 = r9
            long r9 = r7.getNetworkActivityPackets(r12, r13)
            r12 = 4
            r124 = r9
            long r9 = r7.getNetworkActivityBytes(r12, r13)
            r127 = r9
            r12 = 5
            long r9 = r7.getNetworkActivityBytes(r12, r13)
            int r38 = (r68 > r26 ? 1 : (r68 == r26 ? 0 : -1))
            if (r38 == 0) goto L_0x06fd
            r4 = 0
            r8.setLength(r4)
            r8.append(r14)
            java.lang.String r4 = "  Total full wakelock time: "
            r8.append(r4)
            long r73 = r68 + r18
            long r12 = r73 / r16
            formatTimeMsNoSpace(r8, r12)
            java.lang.String r4 = r8.toString()
            r15.println(r4)
        L_0x06fd:
            int r4 = (r71 > r26 ? 1 : (r71 == r26 ? 0 : -1))
            if (r4 == 0) goto L_0x071b
            r4 = 0
            r8.setLength(r4)
            r8.append(r14)
            java.lang.String r4 = "  Total partial wakelock time: "
            r8.append(r4)
            long r12 = r71 + r18
            long r12 = r12 / r16
            formatTimeMsNoSpace(r8, r12)
            java.lang.String r4 = r8.toString()
            r15.println(r4)
        L_0x071b:
            r131 = r2
            r3 = r111
            r13 = r420
            long r73 = r7.getWifiMulticastWakelockTime(r3, r13)
            int r12 = r7.getWifiMulticastWakelockCount(r13)
            int r2 = (r73 > r26 ? 1 : (r73 == r26 ? 0 : -1))
            if (r2 == 0) goto L_0x0761
            r2 = 0
            r8.setLength(r2)
            r8.append(r14)
            java.lang.String r2 = "  Total WiFi Multicast wakelock Count: "
            r8.append(r2)
            r8.append(r12)
            java.lang.String r2 = r8.toString()
            r15.println(r2)
            r2 = 0
            r8.setLength(r2)
            r8.append(r14)
            java.lang.String r2 = "  Total WiFi Multicast wakelock time: "
            r8.append(r2)
            long r75 = r73 + r18
            r133 = r0
            long r0 = r75 / r16
            formatTimeMsNoSpace(r8, r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            goto L_0x0763
        L_0x0761:
            r133 = r0
        L_0x0763:
            java.lang.String r0 = ""
            r15.println(r0)
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  CONNECTIVITY POWER SUMMARY START"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Logging duration for connectivity statistics: "
            r8.append(r0)
            long r1 = r107 / r16
            formatTimeMs(r8, r1)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Cellular Statistics:"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r1 = "     Cellular kernel active time: "
            r8.append(r1)
            long r1 = r7.getMobileRadioActiveTime(r3, r13)
            r135 = r3
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r3 = "("
            r8.append(r3)
            r3 = r107
            java.lang.String r0 = r7.formatRatioLocked(r1, r3)
            r8.append(r0)
            java.lang.String r0 = ")"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            java.lang.String r38 = "Cellular"
            android.os.BatteryStats$ControllerActivityCounter r39 = r416.getModemControllerActivity()
            r141 = r9
            r139 = r11
            r140 = r12
            r54 = r97
            r138 = r98
            r11 = r133
            r9 = 0
            r10 = 1
            r0 = r416
            r146 = r1
            r144 = r3
            r3 = r22
            r1 = r418
            r148 = r131
            r2 = r8
            r152 = r3
            r150 = r20
            r22 = r44
            r44 = r46
            r46 = r48
            r48 = r56
            r56 = r60
            r20 = r135
            r3 = r419
            r4 = r38
            r156 = r5
            r154 = r20
            r20 = r58
            r58 = r66
            r77 = r86
            r66 = r88
            r79 = r90
            r75 = r92
            r84 = r94
            r6 = r99
            r60 = r102
            r5 = r39
            r158 = r6
            r38 = r55
            r6 = r420
            r0.printControllerActivity(r1, r2, r3, r4, r5, r6)
            java.lang.String r0 = "     Cellular data received: "
            r15.print(r0)
            r5 = r114
            java.lang.String r0 = r7.formatBytesLocked(r5)
            r15.println(r0)
            java.lang.String r0 = "     Cellular data sent: "
            r15.print(r0)
            java.lang.String r0 = r7.formatBytesLocked(r11)
            r15.println(r0)
            java.lang.String r0 = "     Cellular packets received: "
            r15.print(r0)
            r3 = r116
            r15.println(r3)
            java.lang.String r0 = "     Cellular packets sent: "
            r15.print(r0)
            r1 = r119
            r15.println(r1)
            r8.setLength(r9)
            r8.append(r14)
            java.lang.String r0 = "     Cellular Radio Access Technology:"
            r8.append(r0)
            r0 = 0
            r39 = r0
            r0 = r9
        L_0x0873:
            r10 = 22
            if (r0 >= r10) goto L_0x08d7
            r162 = r1
            r9 = r154
            long r1 = r7.getPhoneDataConnectionTime(r0, r9, r13)
            int r40 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r40 != 0) goto L_0x088b
            r164 = r3
            r167 = r5
            r3 = r144
            goto L_0x08c8
        L_0x088b:
            r164 = r3
            java.lang.String r3 = "\n       "
            r8.append(r3)
            r8.append(r14)
            r3 = 1
            java.lang.String[] r4 = DATA_CONNECTION_NAMES
            int r4 = r4.length
            if (r0 >= r4) goto L_0x08a0
            java.lang.String[] r4 = DATA_CONNECTION_NAMES
            r4 = r4[r0]
            goto L_0x08a2
        L_0x08a0:
            java.lang.String r4 = "ERROR"
        L_0x08a2:
            r8.append(r4)
            java.lang.String r4 = " "
            r8.append(r4)
            r166 = r3
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r3 = "("
            r8.append(r3)
            r167 = r5
            r3 = r144
            java.lang.String r5 = r7.formatRatioLocked(r1, r3)
            r8.append(r5)
            java.lang.String r5 = ") "
            r8.append(r5)
            r39 = r166
        L_0x08c8:
            int r0 = r0 + 1
            r144 = r3
            r154 = r9
            r1 = r162
            r3 = r164
            r5 = r167
            r9 = 0
            r10 = 1
            goto L_0x0873
        L_0x08d7:
            r162 = r1
            r164 = r3
            r167 = r5
            r3 = r144
            r9 = r154
            if (r39 != 0) goto L_0x08e8
            java.lang.String r0 = " (no activity)"
            r8.append(r0)
        L_0x08e8:
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     Cellular Rx signal strength (RSRP):"
            r8.append(r0)
            java.lang.String r0 = "very poor (less than -128dBm): "
            java.lang.String r1 = "poor (-128dBm to -118dBm): "
            java.lang.String r2 = "moderate (-118dBm to -108dBm): "
            java.lang.String r5 = "good (-108dBm to -98dBm): "
            java.lang.String r6 = "great (greater than -98dBm): "
            java.lang.String[] r0 = new java.lang.String[]{r0, r1, r2, r5, r6}
            r6 = r0
            r0 = 0
            int r1 = r6.length
            r5 = 5
            int r2 = java.lang.Math.min(r5, r1)
            r39 = r0
            r0 = 0
        L_0x0917:
            if (r0 >= r2) goto L_0x095e
            r169 = r6
            long r5 = r7.getPhoneSignalStrengthTime(r0, r9, r13)
            int r1 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r1 != 0) goto L_0x0927
            r172 = r2
            goto L_0x0956
        L_0x0927:
            java.lang.String r1 = "\n       "
            r8.append(r1)
            r8.append(r14)
            r1 = 1
            r171 = r1
            r1 = r169[r0]
            r8.append(r1)
            java.lang.String r1 = " "
            r8.append(r1)
            r172 = r2
            long r1 = r5 / r16
            formatTimeMs(r8, r1)
            java.lang.String r1 = "("
            r8.append(r1)
            java.lang.String r1 = r7.formatRatioLocked(r5, r3)
            r8.append(r1)
            java.lang.String r1 = ") "
            r8.append(r1)
            r39 = r171
        L_0x0956:
            int r0 = r0 + 1
            r6 = r169
            r2 = r172
            r5 = 5
            goto L_0x0917
        L_0x095e:
            r172 = r2
            r169 = r6
            if (r39 != 0) goto L_0x0969
            java.lang.String r0 = " (no activity)"
            r8.append(r0)
        L_0x0969:
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Wifi Statistics:"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     Wifi kernel active time: "
            r8.append(r0)
            long r5 = r7.getWifiActiveTime(r9, r13)
            long r0 = r5 / r16
            formatTimeMs(r8, r0)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r5, r3)
            r8.append(r0)
            java.lang.String r0 = ")"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            java.lang.String r40 = "WiFi"
            android.os.BatteryStats$ControllerActivityCounter r55 = r416.getWifiControllerActivity()
            r0 = r416
            r86 = r162
            r1 = r418
            r70 = r172
            r2 = r8
            r173 = r11
            r88 = r164
            r11 = r3
            r3 = r419
            r4 = r40
            r92 = r5
            r90 = r167
            r6 = 5
            r5 = r55
            r40 = r169
            r6 = r420
            r0.printControllerActivity(r1, r2, r3, r4, r5, r6)
            java.lang.String r0 = "     Wifi data received: "
            r15.print(r0)
            r5 = r156
            java.lang.String r0 = r7.formatBytesLocked(r5)
            r15.println(r0)
            java.lang.String r0 = "     Wifi data sent: "
            r15.print(r0)
            r3 = r148
            java.lang.String r0 = r7.formatBytesLocked(r3)
            r15.println(r0)
            java.lang.String r0 = "     Wifi packets received: "
            r15.print(r0)
            r1 = r121
            r15.println(r1)
            java.lang.String r0 = "     Wifi packets sent: "
            r15.print(r0)
            r176 = r5
            r5 = r124
            r15.println(r5)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     Wifi states:"
            r8.append(r0)
            r0 = 0
            r39 = r0
            r0 = 0
        L_0x0a1e:
            r178 = r1
            r1 = 8
            if (r0 >= r1) goto L_0x0a65
            long r1 = r7.getWifiStateTime(r0, r9, r13)
            int r55 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r55 != 0) goto L_0x0a30
            r180 = r3
            goto L_0x0a5e
        L_0x0a30:
            r180 = r3
            java.lang.String r3 = "\n       "
            r8.append(r3)
            r3 = 1
            java.lang.String[] r4 = WIFI_STATE_NAMES
            r4 = r4[r0]
            r8.append(r4)
            java.lang.String r4 = " "
            r8.append(r4)
            r182 = r3
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r3 = "("
            r8.append(r3)
            java.lang.String r3 = r7.formatRatioLocked(r1, r11)
            r8.append(r3)
            java.lang.String r3 = ") "
            r8.append(r3)
            r39 = r182
        L_0x0a5e:
            int r0 = r0 + 1
            r1 = r178
            r3 = r180
            goto L_0x0a1e
        L_0x0a65:
            r180 = r3
            if (r39 != 0) goto L_0x0a6e
            java.lang.String r0 = " (no activity)"
            r8.append(r0)
        L_0x0a6e:
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     Wifi supplicant states:"
            r8.append(r0)
            r0 = 0
            r1 = r0
            r0 = 0
        L_0x0a84:
            r2 = 13
            if (r0 >= r2) goto L_0x0ac3
            long r2 = r7.getWifiSupplStateTime(r0, r9, r13)
            int r4 = (r2 > r26 ? 1 : (r2 == r26 ? 0 : -1))
            if (r4 != 0) goto L_0x0a94
            r183 = r5
            goto L_0x0abe
        L_0x0a94:
            java.lang.String r4 = "\n       "
            r8.append(r4)
            r1 = 1
            java.lang.String[] r4 = WIFI_SUPPL_STATE_NAMES
            r4 = r4[r0]
            r8.append(r4)
            java.lang.String r4 = " "
            r8.append(r4)
            r183 = r5
            long r4 = r2 / r16
            formatTimeMs(r8, r4)
            java.lang.String r4 = "("
            r8.append(r4)
            java.lang.String r4 = r7.formatRatioLocked(r2, r11)
            r8.append(r4)
            java.lang.String r4 = ") "
            r8.append(r4)
        L_0x0abe:
            int r0 = r0 + 1
            r5 = r183
            goto L_0x0a84
        L_0x0ac3:
            r183 = r5
            if (r1 != 0) goto L_0x0acc
            java.lang.String r0 = " (no activity)"
            r8.append(r0)
        L_0x0acc:
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     Wifi Rx signal strength (RSSI):"
            r8.append(r0)
            java.lang.String r0 = "very poor (less than -88.75dBm): "
            java.lang.String r2 = "poor (-88.75 to -77.5dBm): "
            java.lang.String r3 = "moderate (-77.5dBm to -66.25dBm): "
            java.lang.String r4 = "good (-66.25dBm to -55dBm): "
            java.lang.String r5 = "great (greater than -55dBm): "
            java.lang.String[] r0 = new java.lang.String[]{r0, r2, r3, r4, r5}
            r6 = r0
            r0 = 0
            int r1 = r6.length
            r5 = 5
            int r4 = java.lang.Math.min(r5, r1)
            r39 = r0
            r0 = 0
        L_0x0afb:
            if (r0 >= r4) goto L_0x0b3e
            long r1 = r7.getWifiSignalStrengthTime(r0, r9, r13)
            int r3 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r3 != 0) goto L_0x0b09
            r186 = r4
            goto L_0x0b38
        L_0x0b09:
            java.lang.String r3 = "\n    "
            r8.append(r3)
            r8.append(r14)
            r3 = 1
            java.lang.String r5 = "     "
            r8.append(r5)
            r5 = r6[r0]
            r8.append(r5)
            r187 = r3
            r186 = r4
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r3 = "("
            r8.append(r3)
            java.lang.String r3 = r7.formatRatioLocked(r1, r11)
            r8.append(r3)
            java.lang.String r3 = ") "
            r8.append(r3)
            r39 = r187
        L_0x0b38:
            int r0 = r0 + 1
            r4 = r186
            r5 = 5
            goto L_0x0afb
        L_0x0b3e:
            r186 = r4
            if (r39 != 0) goto L_0x0b47
            java.lang.String r0 = " (no activity)"
            r8.append(r0)
        L_0x0b47:
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r1 = "  GPS Statistics:"
            r8.append(r1)
            java.lang.String r1 = r8.toString()
            r15.println(r1)
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     GPS signal quality (Top 4 Average CN0):"
            r8.append(r0)
            java.lang.String r0 = "poor (less than 20 dBHz): "
            java.lang.String r1 = "good (greater than 20 dBHz): "
            java.lang.String[] r0 = new java.lang.String[]{r0, r1}
            r5 = r0
            int r0 = r5.length
            r4 = 2
            int r3 = java.lang.Math.min(r4, r0)
            r0 = 0
        L_0x0b80:
            if (r0 >= r3) goto L_0x0bb6
            long r1 = r7.getGpsSignalQualityTime(r0, r9, r13)
            java.lang.String r4 = "\n    "
            r8.append(r4)
            r8.append(r14)
            java.lang.String r4 = "  "
            r8.append(r4)
            r4 = r5[r0]
            r8.append(r4)
            r188 = r3
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r3 = "("
            r8.append(r3)
            java.lang.String r3 = r7.formatRatioLocked(r1, r11)
            r8.append(r3)
            java.lang.String r3 = ") "
            r8.append(r3)
            int r0 = r0 + 1
            r3 = r188
            r4 = 2
            goto L_0x0b80
        L_0x0bb6:
            r188 = r3
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            long r3 = r416.getGpsBatteryDrainMaMs()
            int r0 = (r3 > r26 ? 1 : (r3 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x0bf8
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "     GPS Battery Drain: "
            r8.append(r0)
            java.text.DecimalFormat r0 = new java.text.DecimalFormat
            java.lang.String r1 = "#.##"
            r0.<init>(r1)
            double r1 = (double) r3
            r94 = 4704985352480227328(0x414b774000000000, double:3600000.0)
            double r1 = r1 / r94
            java.lang.String r0 = r0.format(r1)
            r8.append(r0)
            java.lang.String r0 = "mAh"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
        L_0x0bf8:
            r418.print(r419)
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  CONNECTIVITY POWER SUMMARY END"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            java.lang.String r0 = ""
            r15.println(r0)
            r418.print(r419)
            java.lang.String r0 = "  Bluetooth total received: "
            r15.print(r0)
            r1 = r127
            java.lang.String r0 = r7.formatBytesLocked(r1)
            r15.print(r0)
            java.lang.String r0 = ", sent: "
            r15.print(r0)
            r189 = r11
            r11 = r141
            java.lang.String r0 = r7.formatBytesLocked(r11)
            r15.println(r0)
            long r94 = r7.getBluetoothScanTime(r9, r13)
            r191 = r11
            long r11 = r94 / r16
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Bluetooth scan time: "
            r8.append(r0)
            formatTimeMs(r8, r11)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            java.lang.String r55 = "Bluetooth"
            android.os.BatteryStats$ControllerActivityCounter r81 = r416.getBluetoothControllerActivity()
            r0 = r416
            r96 = r1
            r94 = r178
            r1 = r418
            r2 = r8
            r101 = r3
            r98 = r180
            r100 = r188
            r3 = r419
            r103 = r186
            r4 = r55
            r55 = r5
            r106 = r176
            r108 = r183
            r5 = r81
            r81 = r6
            r6 = r420
            r0.printControllerActivity(r1, r2, r3, r4, r5, r6)
            r418.println()
            r418.print(r419)
            java.lang.String r0 = "  Device battery use since last full charge"
            r15.println(r0)
            r418.print(r419)
            java.lang.String r0 = "    Amount discharged (lower bound): "
            r15.print(r0)
            int r0 = r416.getLowDischargeAmountSinceCharge()
            r15.println(r0)
            r418.print(r419)
            java.lang.String r0 = "    Amount discharged (upper bound): "
            r15.print(r0)
            int r0 = r416.getHighDischargeAmountSinceCharge()
            r15.println(r0)
            r418.print(r419)
            java.lang.String r0 = "    Amount discharged while screen on: "
            r15.print(r0)
            int r0 = r416.getDischargeAmountScreenOnSinceCharge()
            r15.println(r0)
            r418.print(r419)
            java.lang.String r0 = "    Amount discharged while screen off: "
            r15.print(r0)
            int r0 = r416.getDischargeAmountScreenOffSinceCharge()
            r15.println(r0)
            r418.print(r419)
            java.lang.String r0 = "    Amount discharged while screen doze: "
            r15.print(r0)
            int r0 = r416.getDischargeAmountScreenDozeSinceCharge()
            r15.println(r0)
            r418.println()
            com.android.internal.os.BatteryStatsHelper r0 = new com.android.internal.os.BatteryStatsHelper
            r6 = r417
            r5 = r422
            r1 = 0
            r0.<init>(r6, r1, r5)
            r4 = r0
            r4.create((android.os.BatteryStats) r7)
            r2 = -1
            r4.refreshStats((int) r13, (int) r2)
            java.util.List r0 = r4.getUsageList()
            if (r0 == 0) goto L_0x0ee8
            int r1 = r0.size()
            if (r1 <= 0) goto L_0x0ee8
            r418.print(r419)
            java.lang.String r1 = "  Estimated power use (mAh):"
            r15.println(r1)
            r418.print(r419)
            java.lang.String r1 = "    Capacity: "
            r15.print(r1)
            com.android.internal.os.PowerProfile r1 = r4.getPowerProfile()
            double r2 = r1.getBatteryCapacity()
            r7.printmAh((java.io.PrintWriter) r15, (double) r2)
            java.lang.String r1 = ", Computed drain: "
            r15.print(r1)
            double r1 = r4.getComputedPower()
            r7.printmAh((java.io.PrintWriter) r15, (double) r1)
            java.lang.String r1 = ", actual drain: "
            r15.print(r1)
            double r1 = r4.getMinDrainedPower()
            r7.printmAh((java.io.PrintWriter) r15, (double) r1)
            double r1 = r4.getMinDrainedPower()
            double r110 = r4.getMaxDrainedPower()
            int r1 = (r1 > r110 ? 1 : (r1 == r110 ? 0 : -1))
            if (r1 == 0) goto L_0x0d3e
            java.lang.String r1 = "-"
            r15.print(r1)
            double r1 = r4.getMaxDrainedPower()
            r7.printmAh((java.io.PrintWriter) r15, (double) r1)
        L_0x0d3e:
            r418.println()
            r1 = 0
        L_0x0d42:
            int r2 = r0.size()
            if (r1 >= r2) goto L_0x0ee0
            java.lang.Object r2 = r0.get(r1)
            com.android.internal.os.BatterySipper r2 = (com.android.internal.os.BatterySipper) r2
            r418.print(r419)
            int[] r3 = android.os.BatteryStats.AnonymousClass2.$SwitchMap$com$android$internal$os$BatterySipper$DrainType
            r193 = r0
            com.android.internal.os.BatterySipper$DrainType r0 = r2.drainType
            int r0 = r0.ordinal()
            r0 = r3[r0]
            switch(r0) {
                case 1: goto L_0x0dc7;
                case 2: goto L_0x0dc1;
                case 3: goto L_0x0dbb;
                case 4: goto L_0x0db5;
                case 5: goto L_0x0daf;
                case 6: goto L_0x0da9;
                case 7: goto L_0x0da3;
                case 8: goto L_0x0d9d;
                case 9: goto L_0x0d89;
                case 10: goto L_0x0d79;
                case 11: goto L_0x0d73;
                case 12: goto L_0x0d6d;
                case 13: goto L_0x0d67;
                default: goto L_0x0d60;
            }
        L_0x0d60:
            java.lang.String r0 = "    ???: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0d67:
            java.lang.String r0 = "    Camera: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0d6d:
            java.lang.String r0 = "    Over-counted: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0d73:
            java.lang.String r0 = "    Unaccounted: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0d79:
            java.lang.String r0 = "    User "
            r15.print(r0)
            int r0 = r2.userId
            r15.print(r0)
            java.lang.String r0 = ": "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0d89:
            java.lang.String r0 = "    Uid "
            r15.print(r0)
            android.os.BatteryStats$Uid r0 = r2.uidObj
            int r0 = r0.getUid()
            android.os.UserHandle.formatUid((java.io.PrintWriter) r15, (int) r0)
            java.lang.String r0 = ": "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0d9d:
            java.lang.String r0 = "    Flashlight: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0da3:
            java.lang.String r0 = "    Screen: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0da9:
            java.lang.String r0 = "    Bluetooth: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0daf:
            java.lang.String r0 = "    Wifi: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0db5:
            java.lang.String r0 = "    Phone calls: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0dbb:
            java.lang.String r0 = "    Cell standby: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0dc1:
            java.lang.String r0 = "    Idle: "
            r15.print(r0)
            goto L_0x0dcd
        L_0x0dc7:
            java.lang.String r0 = "    Ambient display: "
            r15.print(r0)
        L_0x0dcd:
            double r5 = r2.totalPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r5)
            double r5 = r2.usagePowerMah
            r194 = r11
            double r11 = r2.totalPowerMah
            int r0 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            r5 = 0
            if (r0 == 0) goto L_0x0e88
            java.lang.String r0 = " ("
            r15.print(r0)
            double r11 = r2.usagePowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0df3
            java.lang.String r0 = " usage="
            r15.print(r0)
            double r11 = r2.usagePowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0df3:
            double r11 = r2.cpuPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e03
            java.lang.String r0 = " cpu="
            r15.print(r0)
            double r11 = r2.cpuPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e03:
            double r11 = r2.wakeLockPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e13
            java.lang.String r0 = " wake="
            r15.print(r0)
            double r11 = r2.wakeLockPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e13:
            double r11 = r2.mobileRadioPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e23
            java.lang.String r0 = " radio="
            r15.print(r0)
            double r11 = r2.mobileRadioPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e23:
            double r11 = r2.wifiPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e33
            java.lang.String r0 = " wifi="
            r15.print(r0)
            double r11 = r2.wifiPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e33:
            double r11 = r2.bluetoothPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e43
            java.lang.String r0 = " bt="
            r15.print(r0)
            double r11 = r2.bluetoothPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e43:
            double r11 = r2.gpsPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e53
            java.lang.String r0 = " gps="
            r15.print(r0)
            double r11 = r2.gpsPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e53:
            double r11 = r2.sensorPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e63
            java.lang.String r0 = " sensor="
            r15.print(r0)
            double r11 = r2.sensorPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e63:
            double r11 = r2.cameraPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e73
            java.lang.String r0 = " camera="
            r15.print(r0)
            double r11 = r2.cameraPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e73:
            double r11 = r2.flashlightPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0e83
            java.lang.String r0 = " flash="
            r15.print(r0)
            double r11 = r2.flashlightPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r11)
        L_0x0e83:
            java.lang.String r0 = " )"
            r15.print(r0)
        L_0x0e88:
            double r11 = r2.totalSmearedPowerMah
            double r5 = r2.totalPowerMah
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0ec8
            java.lang.String r0 = " Including smearing: "
            r15.print(r0)
            double r5 = r2.totalSmearedPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r5)
            java.lang.String r0 = " ("
            r15.print(r0)
            double r5 = r2.screenPowerMah
            r11 = 0
            int r0 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x0eb1
            java.lang.String r0 = " screen="
            r15.print(r0)
            double r5 = r2.screenPowerMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r5)
        L_0x0eb1:
            double r5 = r2.proportionalSmearMah
            r11 = 0
            int r0 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x0ec3
            java.lang.String r0 = " proportional="
            r15.print(r0)
            double r5 = r2.proportionalSmearMah
            r7.printmAh((java.io.PrintWriter) r15, (double) r5)
        L_0x0ec3:
            java.lang.String r0 = " )"
            r15.print(r0)
        L_0x0ec8:
            boolean r0 = r2.shouldHide
            if (r0 == 0) goto L_0x0ed1
            java.lang.String r0 = " Excluded from smearing"
            r15.print(r0)
        L_0x0ed1:
            r418.println()
            int r1 = r1 + 1
            r0 = r193
            r11 = r194
            r5 = r422
            r6 = r417
            goto L_0x0d42
        L_0x0ee0:
            r193 = r0
            r194 = r11
            r418.println()
            goto L_0x0eec
        L_0x0ee8:
            r193 = r0
            r194 = r11
        L_0x0eec:
            java.util.List r11 = r4.getMobilemsppList()
            if (r11 == 0) goto L_0x0f9a
            int r0 = r11.size()
            if (r0 <= 0) goto L_0x0f9a
            r418.print(r419)
            java.lang.String r0 = "  Per-app mobile ms per packet:"
            r15.println(r0)
            r0 = 0
            r1 = r0
            r0 = 0
        L_0x0f04:
            int r3 = r11.size()
            if (r0 >= r3) goto L_0x0f6b
            java.lang.Object r3 = r11.get(r0)
            com.android.internal.os.BatterySipper r3 = (com.android.internal.os.BatterySipper) r3
            r5 = 0
            r8.setLength(r5)
            r8.append(r14)
            java.lang.String r5 = "    Uid "
            r8.append(r5)
            android.os.BatteryStats$Uid r5 = r3.uidObj
            int r5 = r5.getUid()
            android.os.UserHandle.formatUid((java.lang.StringBuilder) r8, (int) r5)
            java.lang.String r5 = ": "
            r8.append(r5)
            double r5 = r3.mobilemspp
            java.lang.String r5 = com.android.internal.os.BatteryStatsHelper.makemAh(r5)
            r8.append(r5)
            java.lang.String r5 = " ("
            r8.append(r5)
            long r5 = r3.mobileRxPackets
            r196 = r11
            long r11 = r3.mobileTxPackets
            long r5 = r5 + r11
            r8.append(r5)
            java.lang.String r5 = " packets over "
            r8.append(r5)
            long r5 = r3.mobileActive
            formatTimeMsNoSpace(r8, r5)
            java.lang.String r5 = ") "
            r8.append(r5)
            int r5 = r3.mobileActiveCount
            r8.append(r5)
            java.lang.String r5 = "x"
            r8.append(r5)
            java.lang.String r5 = r8.toString()
            r15.println(r5)
            long r5 = r3.mobileActive
            long r1 = r1 + r5
            int r0 = r0 + 1
            r11 = r196
            goto L_0x0f04
        L_0x0f6b:
            r196 = r11
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "    TOTAL TIME: "
            r8.append(r0)
            formatTimeMs(r8, r1)
            java.lang.String r0 = "("
            r8.append(r0)
            r11 = r189
            java.lang.String r0 = r7.formatRatioLocked(r1, r11)
            r8.append(r0)
            java.lang.String r0 = ")"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            r418.println()
            goto L_0x0f9e
        L_0x0f9a:
            r196 = r11
            r11 = r189
        L_0x0f9e:
            android.os.BatteryStats$1 r0 = new android.os.BatteryStats$1
            r0.<init>()
            r6 = r0
            if (r41 >= 0) goto L_0x117e
            java.util.Map r110 = r416.getKernelWakelockStats()
            int r0 = r110.size()
            if (r0 <= 0) goto L_0x1069
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r5 = r0
            java.util.Set r0 = r110.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0fbf:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0ff2
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getValue()
            android.os.BatteryStats$Timer r2 = (android.os.BatteryStats.Timer) r2
            long r117 = computeWakeLock(r2, r9, r13)
            int r3 = (r117 > r26 ? 1 : (r117 == r26 ? 0 : -1))
            if (r3 <= 0) goto L_0x0ff1
            android.os.BatteryStats$TimerEntry r3 = new android.os.BatteryStats$TimerEntry
            java.lang.Object r111 = r1.getKey()
            r112 = r111
            java.lang.String r112 = (java.lang.String) r112
            r113 = 0
            r111 = r3
            r114 = r2
            r115 = r117
            r111.<init>(r112, r113, r114, r115)
            r5.add(r3)
        L_0x0ff1:
            goto L_0x0fbf
        L_0x0ff2:
            int r0 = r5.size()
            if (r0 <= 0) goto L_0x1069
            java.util.Collections.sort(r5, r6)
            r418.print(r419)
            java.lang.String r0 = "  All kernel wake locks:"
            r15.println(r0)
            r0 = 0
        L_0x1004:
            r2 = r0
            int r0 = r5.size()
            if (r2 >= r0) goto L_0x105e
            java.lang.Object r0 = r5.get(r2)
            r3 = r0
            android.os.BatteryStats$TimerEntry r3 = (android.os.BatteryStats.TimerEntry) r3
            java.lang.String r111 = ": "
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Kernel Wake lock "
            r8.append(r0)
            java.lang.String r0 = r3.mName
            r8.append(r0)
            android.os.BatteryStats$Timer r1 = r3.mTimer
            r112 = 0
            r0 = r8
            r113 = r2
            r114 = r3
            r115 = -1
            r2 = r9
            r116 = r4
            r4 = r112
            r112 = r5
            r5 = r420
            r7 = r6
            r6 = r111
            java.lang.String r0 = printWakeLock(r0, r1, r2, r4, r5, r6)
            java.lang.String r1 = ": "
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x1054
            java.lang.String r1 = " realtime"
            r8.append(r1)
            java.lang.String r1 = r8.toString()
            r15.println(r1)
        L_0x1054:
            int r0 = r113 + 1
            r6 = r7
            r5 = r112
            r4 = r116
            r7 = r416
            goto L_0x1004
        L_0x105e:
            r116 = r4
            r112 = r5
            r7 = r6
            r115 = -1
            r418.println()
            goto L_0x106e
        L_0x1069:
            r116 = r4
            r7 = r6
            r115 = -1
        L_0x106e:
            int r0 = r139.size()
            if (r0 <= 0) goto L_0x10d9
            r6 = r139
            java.util.Collections.sort(r6, r7)
            r418.print(r419)
            java.lang.String r0 = "  All partial wake locks:"
            r15.println(r0)
            r0 = 0
        L_0x1082:
            r5 = r0
            int r0 = r6.size()
            if (r5 >= r0) goto L_0x10d0
            java.lang.Object r0 = r6.get(r5)
            r4 = r0
            android.os.BatteryStats$TimerEntry r4 = (android.os.BatteryStats.TimerEntry) r4
            r0 = 0
            r8.setLength(r0)
            java.lang.String r0 = "  Wake lock "
            r8.append(r0)
            int r0 = r4.mId
            android.os.UserHandle.formatUid((java.lang.StringBuilder) r8, (int) r0)
            java.lang.String r0 = " "
            r8.append(r0)
            java.lang.String r0 = r4.mName
            r8.append(r0)
            android.os.BatteryStats$Timer r1 = r4.mTimer
            r111 = 0
            java.lang.String r112 = ": "
            r0 = r8
            r2 = r9
            r113 = r4
            r4 = r111
            r111 = r5
            r5 = r420
            r114 = r6
            r6 = r112
            printWakeLock(r0, r1, r2, r4, r5, r6)
            java.lang.String r0 = " realtime"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            int r0 = r111 + 1
            r6 = r114
            goto L_0x1082
        L_0x10d0:
            r114 = r6
            r114.clear()
            r418.println()
            goto L_0x10db
        L_0x10d9:
            r114 = r139
        L_0x10db:
            java.util.Map r111 = r416.getWakeupReasonStats()
            int r0 = r111.size()
            if (r0 <= 0) goto L_0x1185
            r418.print(r419)
            java.lang.String r0 = "  All wakeup reasons:"
            r15.println(r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r6 = r0
            java.util.Set r0 = r111.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x10fb:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x112b
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getValue()
            android.os.BatteryStats$Timer r2 = (android.os.BatteryStats.Timer) r2
            android.os.BatteryStats$TimerEntry r3 = new android.os.BatteryStats$TimerEntry
            java.lang.Object r4 = r1.getKey()
            r118 = r4
            java.lang.String r118 = (java.lang.String) r118
            r119 = 0
            int r4 = r2.getCountLocked(r13)
            long r4 = (long) r4
            r117 = r3
            r120 = r2
            r121 = r4
            r117.<init>(r118, r119, r120, r121)
            r6.add(r3)
            goto L_0x10fb
        L_0x112b:
            java.util.Collections.sort(r6, r7)
            r0 = 0
        L_0x112f:
            r5 = r0
            int r0 = r6.size()
            if (r5 >= r0) goto L_0x1178
            java.lang.Object r0 = r6.get(r5)
            r4 = r0
            android.os.BatteryStats$TimerEntry r4 = (android.os.BatteryStats.TimerEntry) r4
            java.lang.String r112 = ": "
            r0 = 0
            r8.setLength(r0)
            r8.append(r14)
            java.lang.String r0 = "  Wakeup reason "
            r8.append(r0)
            java.lang.String r0 = r4.mName
            r8.append(r0)
            android.os.BatteryStats$Timer r1 = r4.mTimer
            r113 = 0
            java.lang.String r117 = ": "
            r0 = r8
            r2 = r9
            r118 = r4
            r4 = r113
            r113 = r5
            r5 = r420
            r119 = r6
            r6 = r117
            printWakeLock(r0, r1, r2, r4, r5, r6)
            java.lang.String r0 = " realtime"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r15.println(r0)
            int r0 = r113 + 1
            r6 = r119
            goto L_0x112f
        L_0x1178:
            r119 = r6
            r418.println()
            goto L_0x1185
        L_0x117e:
            r116 = r4
            r7 = r6
            r114 = r139
            r115 = -1
        L_0x1185:
            android.util.LongSparseArray r6 = r416.getKernelMemoryStats()
            int r0 = r6.size()
            if (r0 <= 0) goto L_0x11cc
            java.lang.String r0 = "  Memory Stats"
            r15.println(r0)
            r0 = 0
        L_0x1195:
            int r1 = r6.size()
            if (r0 >= r1) goto L_0x11c7
            r1 = 0
            r8.setLength(r1)
            java.lang.String r2 = "  Bandwidth "
            r8.append(r2)
            long r2 = r6.keyAt(r0)
            r8.append(r2)
            java.lang.String r2 = " Time "
            r8.append(r2)
            java.lang.Object r2 = r6.valueAt(r0)
            android.os.BatteryStats$Timer r2 = (android.os.BatteryStats.Timer) r2
            long r2 = r2.getTotalTimeLocked(r9, r13)
            r8.append(r2)
            java.lang.String r2 = r8.toString()
            r15.println(r2)
            int r0 = r0 + 1
            goto L_0x1195
        L_0x11c7:
            r1 = 0
            r418.println()
            goto L_0x11cd
        L_0x11cc:
            r1 = 0
        L_0x11cd:
            java.util.Map r110 = r416.getRpmStats()
            int r0 = r110.size()
            if (r0 <= 0) goto L_0x1270
            r418.print(r419)
            java.lang.String r0 = "  Resource Power Manager Stats"
            r15.println(r0)
            int r0 = r110.size()
            if (r0 <= 0) goto L_0x124d
            java.util.Set r0 = r110.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x11ed:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x124d
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r4 = r2.getValue()
            android.os.BatteryStats$Timer r4 = (android.os.BatteryStats.Timer) r4
            r5 = r8
            r8 = r418
            r197 = r9
            r111 = r191
            r412 = r96
            r96 = r108
            r108 = r412
            r414 = r86
            r86 = r90
            r90 = r414
            r9 = r5
            r10 = r4
            r199 = r11
            r41 = r114
            r117 = r140
            r113 = r173
            r118 = r194
            r120 = r196
            r11 = r197
            r13 = r420
            r14 = r419
            r15 = r3
            printTimer(r8, r9, r10, r11, r13, r14, r15)
            r8 = r5
            r9 = r197
            r11 = r199
            r15 = r418
            r114 = r41
            r41 = r421
            r412 = r96
            r96 = r108
            r108 = r412
            r414 = r86
            r86 = r90
            r90 = r414
            goto L_0x11ed
        L_0x124d:
            r5 = r8
            r197 = r9
            r199 = r11
            r41 = r114
            r117 = r140
            r113 = r173
            r111 = r191
            r118 = r194
            r120 = r196
            r412 = r96
            r96 = r108
            r108 = r412
            r414 = r86
            r86 = r90
            r90 = r414
            r418.println()
            goto L_0x128f
        L_0x1270:
            r5 = r8
            r197 = r9
            r199 = r11
            r41 = r114
            r117 = r140
            r113 = r173
            r111 = r191
            r118 = r194
            r120 = r196
            r412 = r96
            r96 = r108
            r108 = r412
            r414 = r86
            r86 = r90
            r90 = r414
        L_0x128f:
            long[] r15 = r416.getCpuFreqs()
            if (r15 == 0) goto L_0x12c7
            r5.setLength(r1)
            java.lang.String r0 = "  CPU freqs:"
            r5.append(r0)
            r0 = r1
        L_0x129e:
            int r2 = r15.length
            if (r0 >= r2) goto L_0x12ba
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = " "
            r2.append(r3)
            r3 = r15[r0]
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r5.append(r2)
            int r0 = r0 + 1
            goto L_0x129e
        L_0x12ba:
            java.lang.String r0 = r5.toString()
            r14 = r418
            r14.println(r0)
            r418.println()
            goto L_0x12c9
        L_0x12c7:
            r14 = r418
        L_0x12c9:
            r0 = r1
        L_0x12ca:
            r13 = r0
            r11 = r158
            if (r13 >= r11) goto L_0x24f3
            r12 = r138
            int r10 = r12.keyAt(r13)
            r9 = r421
            if (r9 < 0) goto L_0x1307
            if (r10 == r9) goto L_0x1307
            r0 = 1000(0x3e8, float:1.401E-42)
            if (r10 == r0) goto L_0x1307
            r4 = r5
            r134 = r6
            r122 = r7
            r144 = r11
            r143 = r12
            r3 = r14
            r180 = r15
            r125 = r146
            r182 = r150
            r387 = r197
            r174 = r199
            r5 = r419
            r11 = r420
            r131 = 3
            r132 = 4
            r133 = 5
            r173 = 1
            r147 = r13
            r13 = r152
            goto L_0x24d8
        L_0x1307:
            java.lang.Object r0 = r12.valueAt(r13)
            r8 = r0
            android.os.BatteryStats$Uid r8 = (android.os.BatteryStats.Uid) r8
            r418.print(r419)
            java.lang.String r0 = "  "
            r14.print(r0)
            android.os.UserHandle.formatUid((java.io.PrintWriter) r14, (int) r10)
            java.lang.String r0 = ":"
            r14.println(r0)
            r121 = 0
            r4 = r420
            long r2 = r8.getNetworkActivityBytes(r1, r4)
            r201 = r2
            r0 = 1
            long r1 = r8.getNetworkActivityBytes(r0, r4)
            r203 = r1
            r3 = 2
            long r0 = r8.getNetworkActivityBytes(r3, r4)
            r205 = r11
            r206 = r12
            r2 = 3
            long r11 = r8.getNetworkActivityBytes(r2, r4)
            r207 = r10
            r2 = 4
            long r9 = r8.getNetworkActivityBytes(r2, r4)
            r208 = r9
            r3 = 5
            long r9 = r8.getNetworkActivityBytes(r3, r4)
            r210 = r9
            r2 = 0
            long r9 = r8.getNetworkActivityPackets(r2, r4)
            r212 = r11
            r2 = 1
            long r11 = r8.getNetworkActivityPackets(r2, r4)
            r215 = r6
            r214 = r7
            r3 = 2
            long r6 = r8.getNetworkActivityPackets(r3, r4)
            r216 = r6
            r2 = 3
            long r6 = r8.getNetworkActivityPackets(r2, r4)
            r218 = r6
            long r6 = r8.getMobileRadioActiveTime(r4)
            r220 = r15
            int r15 = r8.getMobileRadioActiveCount(r4)
            r221 = r0
            r2 = r197
            long r0 = r8.getFullWifiLockTime(r2, r4)
            r223 = r0
            long r0 = r8.getWifiScanTime(r2, r4)
            r225 = r13
            int r13 = r8.getWifiScanCount(r4)
            r226 = r13
            int r13 = r8.getWifiScanBackgroundCount(r4)
            r227 = r0
            long r0 = r8.getWifiScanActualTime(r2)
            r229 = r0
            long r0 = r8.getWifiScanBackgroundTime(r2)
            r231 = r0
            long r0 = r8.getWifiRunningTime(r2, r4)
            r233 = r2
            long r2 = r8.getMobileRadioApWakeupCount(r4)
            r235 = r0
            long r0 = r8.getWifiRadioApWakeupCount(r4)
            int r122 = (r201 > r26 ? 1 : (r201 == r26 ? 0 : -1))
            if (r122 > 0) goto L_0x13cb
            int r122 = (r203 > r26 ? 1 : (r203 == r26 ? 0 : -1))
            if (r122 > 0) goto L_0x13cb
            int r122 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r122 > 0) goto L_0x13cb
            int r122 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r122 <= 0) goto L_0x13be
            goto L_0x13cb
        L_0x13be:
            r237 = r0
            r239 = r2
            r241 = r201
            r2 = r203
            r122 = r214
            r1 = r416
            goto L_0x1409
        L_0x13cb:
            r418.print(r419)
            r237 = r0
            java.lang.String r0 = "    Mobile network: "
            r14.print(r0)
            r239 = r2
            r2 = r201
            r122 = r214
            r1 = r416
            java.lang.String r0 = r1.formatBytesLocked(r2)
            r14.print(r0)
            java.lang.String r0 = " received, "
            r14.print(r0)
            r241 = r2
            r2 = r203
            java.lang.String r0 = r1.formatBytesLocked(r2)
            r14.print(r0)
            java.lang.String r0 = " sent (packets "
            r14.print(r0)
            r14.print(r9)
            java.lang.String r0 = " received, "
            r14.print(r0)
            r14.print(r11)
            java.lang.String r0 = " sent)"
            r14.println(r0)
        L_0x1409:
            int r0 = (r6 > r26 ? 1 : (r6 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x1419
            if (r15 <= 0) goto L_0x1410
            goto L_0x1419
        L_0x1410:
            r243 = r2
            r247 = r6
            r245 = r146
            r0 = r419
            goto L_0x1475
        L_0x1419:
            r0 = 0
            r5.setLength(r0)
            r0 = r419
            r5.append(r0)
            r243 = r2
            java.lang.String r2 = "    Mobile radio active: "
            r5.append(r2)
            long r2 = r6 / r16
            formatTimeMs(r5, r2)
            java.lang.String r2 = "("
            r5.append(r2)
            r2 = r146
            java.lang.String r4 = r1.formatRatioLocked(r6, r2)
            r5.append(r4)
            java.lang.String r4 = ") "
            r5.append(r4)
            r5.append(r15)
            java.lang.String r4 = "x"
            r5.append(r4)
            long r123 = r9 + r11
            int r4 = (r123 > r26 ? 1 : (r123 == r26 ? 0 : -1))
            if (r4 != 0) goto L_0x1452
            r123 = 1
        L_0x1452:
            r245 = r2
            r1 = r123
            java.lang.String r3 = " @ "
            r5.append(r3)
            long r3 = r6 / r16
            double r3 = (double) r3
            r247 = r6
            double r6 = (double) r1
            double r3 = r3 / r6
            java.lang.String r3 = com.android.internal.os.BatteryStatsHelper.makemAh(r3)
            r5.append(r3)
            java.lang.String r3 = " mspp"
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r14.println(r3)
        L_0x1475:
            r2 = r239
            int r1 = (r2 > r26 ? 1 : (r2 == r26 ? 0 : -1))
            if (r1 <= 0) goto L_0x1492
            r1 = 0
            r5.setLength(r1)
            r5.append(r0)
            java.lang.String r4 = "    Mobile radio AP wakeups: "
            r5.append(r4)
            r5.append(r2)
            java.lang.String r4 = r5.toString()
            r14.println(r4)
            goto L_0x1493
        L_0x1492:
            r1 = 0
        L_0x1493:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            java.lang.String r6 = "  "
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.lang.String r6 = "Cellular"
            android.os.BatteryStats$ControllerActivityCounter r7 = r8.getModemControllerActivity()
            r252 = r9
            r250 = r11
            r249 = r15
            r11 = r221
            r9 = r223
            r254 = r227
            r256 = r229
            r258 = r231
            r260 = r235
            r262 = r237
            r15 = r0
            r0 = r416
            r15 = r1
            r264 = r8
            r123 = r243
            r8 = r416
            r1 = r418
            r129 = r2
            r265 = r233
            r127 = r241
            r125 = r245
            r131 = 3
            r132 = 4
            r2 = r5
            r133 = 5
            r3 = r4
            r4 = r6
            r6 = r5
            r5 = r7
            r269 = r6
            r267 = r9
            r134 = r215
            r9 = r216
            r7 = r218
            r135 = r247
            r6 = r420
            r0.printControllerActivityIfInteresting(r1, r2, r3, r4, r5, r6)
            int r0 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x150d
            int r0 = (r212 > r26 ? 1 : (r212 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x150d
            int r0 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x150d
            int r0 = (r7 > r26 ? 1 : (r7 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x1507
            goto L_0x150d
        L_0x1507:
            r5 = r7
            r3 = r212
            r7 = r416
            goto L_0x1542
        L_0x150d:
            r418.print(r419)
            java.lang.String r0 = "    Wi-Fi network: "
            r14.print(r0)
            r5 = r7
            r7 = r416
            java.lang.String r0 = r7.formatBytesLocked(r11)
            r14.print(r0)
            java.lang.String r0 = " received, "
            r14.print(r0)
            r3 = r212
            java.lang.String r0 = r7.formatBytesLocked(r3)
            r14.print(r0)
            java.lang.String r0 = " sent (packets "
            r14.print(r0)
            r14.print(r9)
            java.lang.String r0 = " received, "
            r14.print(r0)
            r14.print(r5)
            java.lang.String r0 = " sent)"
            r14.println(r0)
        L_0x1542:
            r1 = r267
            int r0 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r0 != 0) goto L_0x15a9
            r270 = r11
            r11 = r254
            int r0 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r0 != 0) goto L_0x1599
            if (r226 != 0) goto L_0x1599
            if (r13 != 0) goto L_0x1599
            r272 = r9
            r9 = r256
            int r0 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r0 != 0) goto L_0x158e
            r274 = r5
            r5 = r258
            int r0 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r0 != 0) goto L_0x1588
            r276 = r3
            r3 = r260
            int r0 = (r3 > r26 ? 1 : (r3 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x1571
            goto L_0x15bd
        L_0x1571:
            r281 = r1
            r279 = r3
            r283 = r11
            r11 = r13
            r12 = r14
            r13 = r199
            r4 = r226
            r2 = r265
            r8 = r269
            r15 = r419
            goto L_0x169d
        L_0x1588:
            r276 = r3
            r3 = r260
            goto L_0x15bd
        L_0x158e:
            r276 = r3
            r274 = r5
            r5 = r258
            r3 = r260
            goto L_0x15bd
        L_0x1599:
            r276 = r3
            r274 = r5
            r272 = r9
            r9 = r256
            r5 = r258
            r3 = r260
            goto L_0x15bd
        L_0x15a9:
            r276 = r3
            r274 = r5
            r272 = r9
            r270 = r11
            r11 = r254
            r9 = r256
            r5 = r258
            r3 = r260
        L_0x15bd:
            r8 = r269
            r8.setLength(r15)
            r0 = r15
            r15 = r419
            r8.append(r15)
            java.lang.String r0 = "    Wifi Running: "
            r8.append(r0)
            r278 = r13
            long r13 = r3 / r16
            formatTimeMs(r8, r13)
            java.lang.String r0 = "("
            r8.append(r0)
            r13 = r199
            java.lang.String r0 = r7.formatRatioLocked(r3, r13)
            r8.append(r0)
            java.lang.String r0 = ")\n"
            r8.append(r0)
            r8.append(r15)
            java.lang.String r0 = "    Full Wifi Lock: "
            r8.append(r0)
            r279 = r3
            long r3 = r1 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r1, r13)
            r8.append(r0)
            java.lang.String r0 = ")\n"
            r8.append(r0)
            r8.append(r15)
            java.lang.String r0 = "    Wifi Scan (blamed): "
            r8.append(r0)
            long r3 = r11 / r16
            formatTimeMs(r8, r3)
            java.lang.String r0 = "("
            r8.append(r0)
            java.lang.String r0 = r7.formatRatioLocked(r11, r13)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r4 = r226
            r8.append(r4)
            java.lang.String r0 = "x\n"
            r8.append(r0)
            r8.append(r15)
            java.lang.String r0 = "    Wifi Scan (actual): "
            r8.append(r0)
            r281 = r1
            long r0 = r9 / r16
            formatTimeMs(r8, r0)
            java.lang.String r0 = "("
            r8.append(r0)
            r283 = r11
            r2 = r265
            r0 = 0
            long r11 = r7.computeBatteryRealtime(r2, r0)
            java.lang.String r0 = r7.formatRatioLocked(r9, r11)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r8.append(r4)
            java.lang.String r0 = "x\n"
            r8.append(r0)
            r8.append(r15)
            java.lang.String r0 = "    Background Wifi Scan: "
            r8.append(r0)
            long r0 = r5 / r16
            formatTimeMs(r8, r0)
            java.lang.String r0 = "("
            r8.append(r0)
            r0 = 0
            long r11 = r7.computeBatteryRealtime(r2, r0)
            java.lang.String r0 = r7.formatRatioLocked(r5, r11)
            r8.append(r0)
            java.lang.String r0 = ") "
            r8.append(r0)
            r11 = r278
            r8.append(r11)
            java.lang.String r0 = "x"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r12 = r418
            r12.println(r0)
        L_0x169d:
            r0 = r262
            int r137 = (r0 > r26 ? 1 : (r0 == r26 ? 0 : -1))
            if (r137 <= 0) goto L_0x16bd
            r285 = r2
            r2 = 0
            r8.setLength(r2)
            r8.append(r15)
            java.lang.String r3 = "    WiFi AP wakeups: "
            r8.append(r3)
            r8.append(r0)
            java.lang.String r3 = r8.toString()
            r12.println(r3)
            goto L_0x16c1
        L_0x16bd:
            r285 = r2
            r2 = 0
        L_0x16c1:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r15)
            java.lang.String r2 = "  "
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            java.lang.String r137 = "WiFi"
            android.os.BatteryStats$ControllerActivityCounter r138 = r264.getWifiControllerActivity()
            r139 = r0
            r2 = 0
            r0 = r416
            r141 = r281
            r1 = r418
            r289 = r11
            r287 = r13
            r13 = r285
            r11 = r2
            r2 = r8
            r143 = r276
            r145 = r279
            r147 = r4
            r4 = r137
            r154 = r5
            r148 = r274
            r5 = r138
            r6 = r420
            r0.printControllerActivityIfInteresting(r1, r2, r3, r4, r5, r6)
            int r0 = (r208 > r26 ? 1 : (r208 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x170c
            int r0 = (r210 > r26 ? 1 : (r210 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x1707
            goto L_0x170c
        L_0x1707:
            r5 = r208
            r2 = r210
            goto L_0x1730
        L_0x170c:
            r418.print(r419)
            java.lang.String r0 = "    Bluetooth network: "
            r12.print(r0)
            r5 = r208
            java.lang.String r0 = r7.formatBytesLocked(r5)
            r12.print(r0)
            java.lang.String r0 = " received, "
            r12.print(r0)
            r2 = r210
            java.lang.String r0 = r7.formatBytesLocked(r2)
            r12.print(r0)
            java.lang.String r0 = " sent"
            r12.println(r0)
        L_0x1730:
            android.os.BatteryStats$Timer r4 = r264.getBluetoothScanTimer()
            if (r4 == 0) goto L_0x1930
            r1 = r420
            long r137 = r4.getTotalTimeLocked(r13, r1)
            long r137 = r137 + r18
            long r11 = r137 / r16
            int r0 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x191b
            int r0 = r4.getCountLocked(r1)
            r290 = r2
            android.os.BatteryStats$Timer r2 = r264.getBluetoothScanBackgroundTimer()
            if (r2 == 0) goto L_0x1756
            int r3 = r2.getCountLocked(r1)
            goto L_0x1757
        L_0x1756:
            r3 = 0
        L_0x1757:
            r294 = r5
            r292 = r9
            r9 = r150
            long r5 = r4.getTotalDurationMsLocked(r9)
            if (r2 == 0) goto L_0x176a
            long r137 = r2.getTotalDurationMsLocked(r9)
            goto L_0x176c
        L_0x176a:
            r137 = r26
        L_0x176c:
            r296 = r137
            android.os.BatteryStats$Counter r137 = r264.getBluetoothScanResultCounter()
            if (r137 == 0) goto L_0x177e
            android.os.BatteryStats$Counter r7 = r264.getBluetoothScanResultCounter()
            int r7 = r7.getCountLocked(r1)
            goto L_0x177f
        L_0x177e:
            r7 = 0
        L_0x177f:
            android.os.BatteryStats$Counter r137 = r264.getBluetoothScanResultBgCounter()
            if (r137 == 0) goto L_0x1791
            r298 = r13
            android.os.BatteryStats$Counter r13 = r264.getBluetoothScanResultBgCounter()
            int r13 = r13.getCountLocked(r1)
            goto L_0x1795
        L_0x1791:
            r298 = r13
            r13 = 0
        L_0x1795:
            android.os.BatteryStats$Timer r14 = r264.getBluetoothUnoptimizedScanTimer()
            if (r14 == 0) goto L_0x17a0
            long r137 = r14.getTotalDurationMsLocked(r9)
            goto L_0x17a2
        L_0x17a0:
            r137 = r26
        L_0x17a2:
            r300 = r137
            if (r14 == 0) goto L_0x17ac
            long r137 = r14.getMaxDurationMsLocked(r9)
            goto L_0x17ae
        L_0x17ac:
            r137 = r26
        L_0x17ae:
            r302 = r137
            android.os.BatteryStats$Timer r1 = r264.getBluetoothUnoptimizedScanBackgroundTimer()
            if (r1 == 0) goto L_0x17bd
            long r137 = r1.getTotalDurationMsLocked(r9)
            goto L_0x17bf
        L_0x17bd:
            r137 = r26
        L_0x17bf:
            r304 = r137
            if (r1 == 0) goto L_0x17c9
            long r137 = r1.getMaxDurationMsLocked(r9)
            goto L_0x17cb
        L_0x17c9:
            r137 = r26
        L_0x17cb:
            r306 = r137
            r308 = r9
            r9 = 0
            r8.setLength(r9)
            int r9 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r9 == 0) goto L_0x1801
            r8.append(r15)
            java.lang.String r9 = "    Bluetooth Scan (total blamed realtime): "
            r8.append(r9)
            formatTimeMs(r8, r11)
            java.lang.String r9 = " ("
            r8.append(r9)
            r8.append(r0)
            java.lang.String r9 = " times)"
            r8.append(r9)
            boolean r9 = r4.isRunningLocked()
            if (r9 == 0) goto L_0x17fc
            java.lang.String r9 = " (currently running)"
            r8.append(r9)
        L_0x17fc:
            java.lang.String r9 = "\n"
            r8.append(r9)
        L_0x1801:
            r8.append(r15)
            java.lang.String r9 = "    Bluetooth Scan (total actual realtime): "
            r8.append(r9)
            formatTimeMs(r8, r5)
            java.lang.String r9 = " ("
            r8.append(r9)
            r8.append(r0)
            java.lang.String r9 = " times)"
            r8.append(r9)
            boolean r9 = r4.isRunningLocked()
            if (r9 == 0) goto L_0x1824
            java.lang.String r9 = " (currently running)"
            r8.append(r9)
        L_0x1824:
            java.lang.String r9 = "\n"
            r8.append(r9)
            r9 = r296
            int r137 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r137 > 0) goto L_0x1836
            if (r3 <= 0) goto L_0x1832
            goto L_0x1836
        L_0x1832:
            r310 = r0
            goto L_0x1863
        L_0x1836:
            r8.append(r15)
            r310 = r0
            java.lang.String r0 = "    Bluetooth Scan (background realtime): "
            r8.append(r0)
            formatTimeMs(r8, r9)
            java.lang.String r0 = " ("
            r8.append(r0)
            r8.append(r3)
            java.lang.String r0 = " times)"
            r8.append(r0)
            if (r2 == 0) goto L_0x185e
            boolean r0 = r2.isRunningLocked()
            if (r0 == 0) goto L_0x185e
            java.lang.String r0 = " (currently running in background)"
            r8.append(r0)
        L_0x185e:
            java.lang.String r0 = "\n"
            r8.append(r0)
        L_0x1863:
            r8.append(r15)
            java.lang.String r0 = "    Bluetooth Scan Results: "
            r8.append(r0)
            r8.append(r7)
            java.lang.String r0 = " ("
            r8.append(r0)
            r8.append(r13)
            java.lang.String r0 = " in background)"
            r8.append(r0)
            r311 = r2
            r312 = r3
            r2 = r300
            int r0 = (r2 > r26 ? 1 : (r2 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x189d
            r313 = r4
            r314 = r5
            r4 = r304
            int r0 = (r4 > r26 ? 1 : (r4 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x1894
            goto L_0x18a5
        L_0x1894:
            r316 = r2
            r318 = r302
            r2 = r306
            goto L_0x190d
        L_0x189d:
            r313 = r4
            r314 = r5
            r4 = r304
        L_0x18a5:
            java.lang.String r0 = "\n"
            r8.append(r0)
            r8.append(r15)
            java.lang.String r0 = "    Unoptimized Bluetooth Scan (realtime): "
            r8.append(r0)
            formatTimeMs(r8, r2)
            java.lang.String r0 = " (max "
            r8.append(r0)
            r316 = r2
            r2 = r302
            formatTimeMs(r8, r2)
            java.lang.String r0 = ")"
            r8.append(r0)
            if (r14 == 0) goto L_0x18d4
            boolean r0 = r14.isRunningLocked()
            if (r0 == 0) goto L_0x18d4
            java.lang.String r0 = " (currently running unoptimized)"
            r8.append(r0)
        L_0x18d4:
            if (r1 == 0) goto L_0x1908
            int r0 = (r4 > r26 ? 1 : (r4 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x1908
            java.lang.String r0 = "\n"
            r8.append(r0)
            r8.append(r15)
            java.lang.String r0 = "    Unoptimized Bluetooth Scan (background realtime): "
            r8.append(r0)
            formatTimeMs(r8, r4)
            java.lang.String r0 = " (max "
            r8.append(r0)
            r318 = r2
            r2 = r306
            formatTimeMs(r8, r2)
            java.lang.String r0 = ")"
            r8.append(r0)
            boolean r0 = r1.isRunningLocked()
            if (r0 == 0) goto L_0x190d
            java.lang.String r0 = " (currently running unoptimized in background)"
            r8.append(r0)
            goto L_0x190d
        L_0x1908:
            r318 = r2
            r2 = r306
        L_0x190d:
            java.lang.String r0 = r8.toString()
            r137 = r11
            r11 = r418
            r11.println(r0)
            r121 = 1
            goto L_0x1943
        L_0x191b:
            r290 = r2
            r313 = r4
            r294 = r5
            r292 = r9
            r298 = r13
            r308 = r150
            r11 = r418
            goto L_0x1943
        L_0x1930:
            r290 = r2
            r313 = r4
            r294 = r5
            r292 = r9
            r11 = r12
            r298 = r13
            r308 = r150
        L_0x1943:
            boolean r0 = r264.hasUserActivity()
            if (r0 == 0) goto L_0x198f
            r0 = 0
            r1 = r0
            r0 = 0
        L_0x194c:
            int r2 = android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES
            if (r0 >= r2) goto L_0x1981
            r7 = r264
            r2 = r420
            int r3 = r7.getUserActivityCount(r0, r2)
            if (r3 == 0) goto L_0x197b
            if (r1 != 0) goto L_0x1967
            r4 = 0
            r8.setLength(r4)
            java.lang.String r4 = "    User activity: "
            r8.append(r4)
            r1 = 1
            goto L_0x196c
        L_0x1967:
            java.lang.String r4 = ", "
            r8.append(r4)
        L_0x196c:
            r8.append(r3)
            java.lang.String r4 = " "
            r8.append(r4)
            java.lang.String[] r4 = android.os.BatteryStats.Uid.USER_ACTIVITY_TYPES
            r4 = r4[r0]
            r8.append(r4)
        L_0x197b:
            int r0 = r0 + 1
            r264 = r7
            goto L_0x194c
        L_0x1981:
            r7 = r264
            r2 = r420
            if (r1 == 0) goto L_0x1993
            java.lang.String r0 = r8.toString()
            r11.println(r0)
            goto L_0x1993
        L_0x198f:
            r7 = r264
            r2 = r420
        L_0x1993:
            android.util.ArrayMap r14 = r7.getWakelockStats()
            r0 = 0
            r3 = 0
            r5 = 0
            r9 = 0
            r12 = 0
            int r13 = r14.size()
            r320 = r12
            r12 = 1
            int r13 = r13 - r12
            r321 = r9
            r10 = r320
            r412 = r0
            r0 = r5
            r5 = r412
        L_0x19b4:
            r9 = r13
            if (r9 < 0) goto L_0x1aaa
            java.lang.Object r13 = r14.valueAt(r9)
            android.os.BatteryStats$Uid$Wakelock r13 = (android.os.BatteryStats.Uid.Wakelock) r13
            java.lang.String r137 = ": "
            r12 = 0
            r8.setLength(r12)
            r8.append(r15)
            java.lang.String r12 = "    Wake lock "
            r8.append(r12)
            java.lang.Object r12 = r14.keyAt(r9)
            java.lang.String r12 = (java.lang.String) r12
            r8.append(r12)
            r12 = 1
            android.os.BatteryStats$Timer r138 = r13.getWakeTime(r12)
            java.lang.String r12 = "full"
            r323 = r14
            r14 = r0
            r0 = r8
            r1 = r138
            r324 = r14
            r150 = r290
            r14 = r3
            r4 = r2
            r2 = r298
            r326 = r7
            r138 = r313
            r7 = r4
            r4 = r12
            r327 = r14
            r156 = r294
            r14 = r5
            r5 = r420
            r6 = r137
            java.lang.String r12 = printWakeLock(r0, r1, r2, r4, r5, r6)
            r0 = 0
            android.os.BatteryStats$Timer r137 = r13.getWakeTime(r0)
            java.lang.String r4 = "partial"
            r0 = r8
            r1 = r137
            r6 = r12
            java.lang.String r12 = printWakeLock(r0, r1, r2, r4, r5, r6)
            if (r137 == 0) goto L_0x1a18
            android.os.BatteryStats$Timer r0 = r137.getSubTimer()
        L_0x1a16:
            r1 = r0
            goto L_0x1a1a
        L_0x1a18:
            r0 = 0
            goto L_0x1a16
        L_0x1a1a:
            java.lang.String r4 = "background partial"
            r0 = r8
            r2 = r298
            r5 = r420
            r6 = r12
            java.lang.String r12 = printWakeLock(r0, r1, r2, r4, r5, r6)
            r6 = 2
            android.os.BatteryStats$Timer r1 = r13.getWakeTime(r6)
            java.lang.String r4 = "window"
            r329 = r9
            r9 = r6
            r6 = r12
            java.lang.String r12 = printWakeLock(r0, r1, r2, r4, r5, r6)
            r6 = 18
            android.os.BatteryStats$Timer r1 = r13.getWakeTime(r6)
            java.lang.String r4 = "draw"
            r6 = r12
            java.lang.String r0 = printWakeLock(r0, r1, r2, r4, r5, r6)
            java.lang.String r1 = " realtime"
            r8.append(r1)
            java.lang.String r1 = r8.toString()
            r11.println(r1)
            r121 = 1
            int r10 = r10 + 1
            r1 = 1
            android.os.BatteryStats$Timer r2 = r13.getWakeTime(r1)
            r3 = r298
            long r1 = computeWakeLock(r2, r3, r7)
            long r5 = r14 + r1
            r1 = 0
            android.os.BatteryStats$Timer r2 = r13.getWakeTime(r1)
            long r1 = computeWakeLock(r2, r3, r7)
            r14 = r327
            long r1 = r1 + r14
            android.os.BatteryStats$Timer r12 = r13.getWakeTime(r9)
            long r14 = computeWakeLock(r12, r3, r7)
            r158 = r324
            long r14 = r158 + r14
            r12 = 18
            android.os.BatteryStats$Timer r12 = r13.getWakeTime(r12)
            long r158 = computeWakeLock(r12, r3, r7)
            r330 = r0
            r331 = r1
            r0 = r321
            long r0 = r0 + r158
            r2 = r329
            int r13 = r2 + -1
            r321 = r0
            r2 = r7
            r0 = r14
            r313 = r138
            r290 = r150
            r294 = r156
            r14 = r323
            r7 = r326
            r3 = r331
            r12 = 1
            r15 = r419
            goto L_0x19b4
        L_0x1aaa:
            r158 = r0
            r326 = r7
            r323 = r14
            r150 = r290
            r156 = r294
            r138 = r313
            r0 = r321
            r9 = 2
            r7 = r2
            r14 = r5
            r5 = r3
            r3 = r298
            r2 = 1
            if (r10 <= r2) goto L_0x1c05
            r12 = 0
            r160 = 0
            android.os.BatteryStats$Timer r2 = r326.getAggregatedPartialWakelockTimer()
            if (r2 == 0) goto L_0x1af5
            android.os.BatteryStats$Timer r2 = r326.getAggregatedPartialWakelockTimer()
            r333 = r10
            r9 = r308
            long r12 = r2.getTotalDurationMsLocked(r9)
            r334 = r12
            android.os.BatteryStats$Timer r12 = r2.getSubTimer()
            if (r12 == 0) goto L_0x1ae9
            long r162 = r12.getTotalDurationMsLocked(r9)
            goto L_0x1aeb
        L_0x1ae9:
            r162 = r26
        L_0x1aeb:
            r160 = r162
            r336 = r9
            r9 = r160
            r12 = r334
            goto L_0x1afd
        L_0x1af5:
            r333 = r10
            r9 = r160
            r336 = r308
        L_0x1afd:
            int r2 = (r12 > r26 ? 1 : (r12 == r26 ? 0 : -1))
            if (r2 != 0) goto L_0x1b1c
            int r2 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r2 != 0) goto L_0x1b1c
            int r2 = (r14 > r26 ? 1 : (r14 == r26 ? 0 : -1))
            if (r2 != 0) goto L_0x1b1c
            int r2 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r2 != 0) goto L_0x1b1c
            int r2 = (r158 > r26 ? 1 : (r158 == r26 ? 0 : -1))
            if (r2 == 0) goto L_0x1b12
            goto L_0x1b1c
        L_0x1b12:
            r350 = r0
            r338 = r3
            r3 = r158
            goto L_0x1c13
        L_0x1b1c:
            r2 = 0
            r8.setLength(r2)
            r338 = r3
            r3 = r158
            r2 = r419
            r8.append(r2)
            java.lang.String r2 = "    TOTAL wake: "
            r8.append(r2)
            r2 = 0
            int r137 = (r14 > r26 ? 1 : (r14 == r26 ? 0 : -1))
            if (r137 == 0) goto L_0x1b42
            r2 = 1
            formatTimeMs(r8, r14)
            r340 = r2
            java.lang.String r2 = "full"
            r8.append(r2)
            r2 = r340
        L_0x1b42:
            int r137 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r137 == 0) goto L_0x1b63
            if (r2 == 0) goto L_0x1b51
            r341 = r2
            java.lang.String r2 = ", "
            r8.append(r2)
            goto L_0x1b54
        L_0x1b51:
            r341 = r2
        L_0x1b54:
            r2 = 1
            formatTimeMs(r8, r5)
            r342 = r2
            java.lang.String r2 = "blamed partial"
            r8.append(r2)
            r2 = r342
            goto L_0x1b66
        L_0x1b63:
            r341 = r2
        L_0x1b66:
            int r137 = (r12 > r26 ? 1 : (r12 == r26 ? 0 : -1))
            if (r137 == 0) goto L_0x1b87
            if (r2 == 0) goto L_0x1b75
            r343 = r2
            java.lang.String r2 = ", "
            r8.append(r2)
            goto L_0x1b78
        L_0x1b75:
            r343 = r2
        L_0x1b78:
            r2 = 1
            formatTimeMs(r8, r12)
            r344 = r2
            java.lang.String r2 = "actual partial"
            r8.append(r2)
            r2 = r344
            goto L_0x1b8a
        L_0x1b87:
            r343 = r2
        L_0x1b8a:
            int r137 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r137 == 0) goto L_0x1bab
            if (r2 == 0) goto L_0x1b99
            r345 = r2
            java.lang.String r2 = ", "
            r8.append(r2)
            goto L_0x1b9c
        L_0x1b99:
            r345 = r2
        L_0x1b9c:
            r2 = 1
            formatTimeMs(r8, r9)
            r346 = r2
            java.lang.String r2 = "actual background partial"
            r8.append(r2)
            r2 = r346
            goto L_0x1bae
        L_0x1bab:
            r345 = r2
        L_0x1bae:
            int r137 = (r3 > r26 ? 1 : (r3 == r26 ? 0 : -1))
            if (r137 == 0) goto L_0x1bd0
            if (r2 == 0) goto L_0x1bbd
            r347 = r2
            java.lang.String r2 = ", "
            r8.append(r2)
            goto L_0x1bc0
        L_0x1bbd:
            r347 = r2
        L_0x1bc0:
            r2 = 1
            formatTimeMs(r8, r3)
            r348 = r2
            java.lang.String r2 = "window"
            r8.append(r2)
            r2 = r348
            goto L_0x1bd3
        L_0x1bd0:
            r347 = r2
        L_0x1bd3:
            int r137 = (r0 > r26 ? 1 : (r0 == r26 ? 0 : -1))
            if (r137 == 0) goto L_0x1bf2
            if (r2 == 0) goto L_0x1be2
            r349 = r2
            java.lang.String r2 = ","
            r8.append(r2)
            goto L_0x1be5
        L_0x1be2:
            r349 = r2
        L_0x1be5:
            r2 = 1
            formatTimeMs(r8, r0)
            r350 = r0
            java.lang.String r0 = "draw"
            r8.append(r0)
            goto L_0x1bf8
        L_0x1bf2:
            r350 = r0
            r349 = r2
        L_0x1bf8:
            java.lang.String r0 = " realtime"
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r11.println(r0)
            goto L_0x1c13
        L_0x1c05:
            r350 = r0
            r338 = r3
            r333 = r10
            r3 = r158
            r336 = r308
        L_0x1c13:
            android.os.BatteryStats$Timer r0 = r326.getMulticastWakelockStats()
            if (r0 == 0) goto L_0x1c60
            r1 = r338
            long r9 = r0.getTotalTimeLocked(r1, r7)
            int r12 = r0.getCountLocked(r7)
            int r13 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r13 <= 0) goto L_0x1c57
            r13 = 0
            r8.setLength(r13)
            r13 = r419
            r8.append(r13)
            r352 = r0
            java.lang.String r0 = "    WiFi Multicast Wakelock"
            r8.append(r0)
            java.lang.String r0 = " count = "
            r8.append(r0)
            r8.append(r12)
            java.lang.String r0 = " time = "
            r8.append(r0)
            long r158 = r9 + r18
            r353 = r3
            long r3 = r158 / r16
            formatTimeMsNoSpace(r8, r3)
            java.lang.String r0 = r8.toString()
            r11.println(r0)
            goto L_0x1c6a
        L_0x1c57:
            r352 = r0
            r353 = r3
            r13 = r419
            goto L_0x1c6a
        L_0x1c60:
            r352 = r0
            r353 = r3
            r1 = r338
            r13 = r419
        L_0x1c6a:
            android.util.ArrayMap r0 = r326.getSyncStats()
            int r3 = r0.size()
            r4 = 1
            int r3 = r3 - r4
        L_0x1c74:
            r9 = -1
            if (r3 < 0) goto L_0x1d2f
            java.lang.Object r4 = r0.valueAt(r3)
            android.os.BatteryStats$Timer r4 = (android.os.BatteryStats.Timer) r4
            long r158 = r4.getTotalTimeLocked(r1, r7)
            long r158 = r158 + r18
            r355 = r9
            long r9 = r158 / r16
            int r12 = r4.getCountLocked(r7)
            r357 = r5
            android.os.BatteryStats$Timer r5 = r4.getSubTimer()
            if (r5 == 0) goto L_0x1ca3
            r359 = r14
            r14 = r336
            long r158 = r5.getTotalDurationMsLocked(r14)
            r355 = r158
            goto L_0x1ca8
        L_0x1ca3:
            r359 = r14
            r14 = r336
        L_0x1ca8:
            r361 = r355
            if (r5 == 0) goto L_0x1cb2
            int r6 = r5.getCountLocked(r7)
            goto L_0x1cb4
        L_0x1cb2:
            r6 = r115
        L_0x1cb4:
            r363 = r4
            r4 = 0
            r8.setLength(r4)
            r8.append(r13)
            java.lang.String r4 = "    Sync "
            r8.append(r4)
            java.lang.Object r4 = r0.keyAt(r3)
            java.lang.String r4 = (java.lang.String) r4
            r8.append(r4)
            java.lang.String r4 = ": "
            r8.append(r4)
            int r4 = (r9 > r26 ? 1 : (r9 == r26 ? 0 : -1))
            if (r4 == 0) goto L_0x1d0c
            formatTimeMs(r8, r9)
            java.lang.String r4 = "realtime ("
            r8.append(r4)
            r8.append(r12)
            java.lang.String r4 = " times)"
            r8.append(r4)
            r364 = r5
            r4 = r361
            int r137 = (r4 > r26 ? 1 : (r4 == r26 ? 0 : -1))
            if (r137 <= 0) goto L_0x1d08
            r365 = r0
            java.lang.String r0 = ", "
            r8.append(r0)
            formatTimeMs(r8, r4)
            java.lang.String r0 = "background ("
            r8.append(r0)
            r8.append(r6)
            java.lang.String r0 = " times)"
            r8.append(r0)
            goto L_0x1d19
        L_0x1d08:
            r365 = r0
            goto L_0x1d19
        L_0x1d0c:
            r365 = r0
            r364 = r5
            r4 = r361
            java.lang.String r0 = "(not used)"
            r8.append(r0)
        L_0x1d19:
            java.lang.String r0 = r8.toString()
            r11.println(r0)
            r121 = 1
            int r3 = r3 + -1
            r336 = r14
            r5 = r357
            r14 = r359
            r0 = r365
            goto L_0x1c74
        L_0x1d2f:
            r365 = r0
            r357 = r5
            r355 = r9
            r359 = r14
            r14 = r336
            android.util.ArrayMap r0 = r326.getJobStats()
            int r3 = r0.size()
            r4 = 1
            int r3 = r3 - r4
        L_0x1d47:
            if (r3 < 0) goto L_0x1ddc
            java.lang.Object r4 = r0.valueAt(r3)
            android.os.BatteryStats$Timer r4 = (android.os.BatteryStats.Timer) r4
            long r5 = r4.getTotalTimeLocked(r1, r7)
            long r5 = r5 + r18
            long r5 = r5 / r16
            int r9 = r4.getCountLocked(r7)
            android.os.BatteryStats$Timer r10 = r4.getSubTimer()
            if (r10 == 0) goto L_0x1d66
            long r158 = r10.getTotalDurationMsLocked(r14)
            goto L_0x1d68
        L_0x1d66:
            r158 = r355
        L_0x1d68:
            r366 = r158
            if (r10 == 0) goto L_0x1d72
            int r12 = r10.getCountLocked(r7)
            goto L_0x1d74
        L_0x1d72:
            r12 = r115
        L_0x1d74:
            r368 = r4
            r4 = 0
            r8.setLength(r4)
            r8.append(r13)
            java.lang.String r4 = "    Job "
            r8.append(r4)
            java.lang.Object r4 = r0.keyAt(r3)
            java.lang.String r4 = (java.lang.String) r4
            r8.append(r4)
            java.lang.String r4 = ": "
            r8.append(r4)
            int r4 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r4 == 0) goto L_0x1dc5
            formatTimeMs(r8, r5)
            java.lang.String r4 = "realtime ("
            r8.append(r4)
            r8.append(r9)
            java.lang.String r4 = " times)"
            r8.append(r4)
            r369 = r5
            r4 = r366
            int r6 = (r4 > r26 ? 1 : (r4 == r26 ? 0 : -1))
            if (r6 <= 0) goto L_0x1dcf
            java.lang.String r6 = ", "
            r8.append(r6)
            formatTimeMs(r8, r4)
            java.lang.String r6 = "background ("
            r8.append(r6)
            r8.append(r12)
            java.lang.String r6 = " times)"
            r8.append(r6)
            goto L_0x1dcf
        L_0x1dc5:
            r369 = r5
            r4 = r366
            java.lang.String r6 = "(not used)"
            r8.append(r6)
        L_0x1dcf:
            java.lang.String r6 = r8.toString()
            r11.println(r6)
            r121 = 1
            int r3 = r3 + -1
            goto L_0x1d47
        L_0x1ddc:
            android.util.ArrayMap r3 = r326.getJobCompletionStats()
            int r4 = r3.size()
            r5 = 1
            int r4 = r4 - r5
        L_0x1de6:
            if (r4 < 0) goto L_0x1e38
            java.lang.Object r6 = r3.valueAt(r4)
            android.util.SparseIntArray r6 = (android.util.SparseIntArray) r6
            if (r6 == 0) goto L_0x1e35
            r418.print(r419)
            java.lang.String r9 = "    Job Completions "
            r11.print(r9)
            java.lang.Object r9 = r3.keyAt(r4)
            java.lang.String r9 = (java.lang.String) r9
            r11.print(r9)
            java.lang.String r9 = ":"
            r11.print(r9)
            r9 = 0
        L_0x1e07:
            int r10 = r6.size()
            if (r9 >= r10) goto L_0x1e32
            java.lang.String r10 = " "
            r11.print(r10)
            int r10 = r6.keyAt(r9)
            java.lang.String r10 = android.app.job.JobParameters.getReasonName(r10)
            r11.print(r10)
            java.lang.String r10 = "("
            r11.print(r10)
            int r10 = r6.valueAt(r9)
            r11.print(r10)
            java.lang.String r10 = "x)"
            r11.print(r10)
            int r9 = r9 + 1
            goto L_0x1e07
        L_0x1e32:
            r418.println()
        L_0x1e35:
            int r4 = r4 + -1
            goto L_0x1de6
        L_0x1e38:
            r4 = r326
            r4.getDeferredJobsLineLocked(r8, r7)
            int r6 = r8.length()
            if (r6 <= 0) goto L_0x1e4f
            java.lang.String r6 = "    Jobs deferred on launch "
            r11.print(r6)
            java.lang.String r6 = r8.toString()
            r11.println(r6)
        L_0x1e4f:
            android.os.BatteryStats$Timer r10 = r4.getFlashlightTurnedOnTimer()
            java.lang.String r6 = "Flashlight"
            r12 = r4
            r4 = r8
            r8 = r418
            r160 = r141
            r141 = r156
            r156 = r252
            r158 = r272
            r162 = r292
            r5 = 2
            r9 = r4
            r137 = r207
            r320 = r333
            r371 = r12
            r166 = r143
            r144 = r205
            r143 = r206
            r168 = r250
            r164 = r270
            r170 = r283
            r172 = r289
            r5 = 0
            r173 = 1
            r11 = r1
            r176 = r172
            r174 = r287
            r172 = r147
            r147 = r225
            r13 = r420
            r372 = r14
            r177 = r323
            r331 = r357
            r178 = r359
            r15 = r418
            r14 = r419
            r180 = r220
            r181 = r249
            r15 = r6
            boolean r6 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r6 = r121 | r6
            android.os.BatteryStats$Timer r10 = r371.getCameraTurnedOnTimer()
            java.lang.String r15 = "Camera"
            boolean r8 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r6 = r6 | r8
            android.os.BatteryStats$Timer r10 = r371.getVideoTurnedOnTimer()
            java.lang.String r15 = "Video"
            r8 = r418
            boolean r8 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r6 = r6 | r8
            android.os.BatteryStats$Timer r10 = r371.getAudioTurnedOnTimer()
            java.lang.String r15 = "Audio"
            r8 = r418
            boolean r8 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r6 = r6 | r8
            android.util.SparseArray r15 = r371.getSensorStats()
            int r14 = r15.size()
            r121 = r6
            r6 = r5
        L_0x1ed2:
            if (r6 >= r14) goto L_0x1fe8
            java.lang.Object r8 = r15.valueAt(r6)
            android.os.BatteryStats$Uid$Sensor r8 = (android.os.BatteryStats.Uid.Sensor) r8
            int r9 = r15.keyAt(r6)
            r4.setLength(r5)
            r13 = r419
            r4.append(r13)
            java.lang.String r10 = "    Sensor "
            r4.append(r10)
            int r10 = r8.getHandle()
            r11 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r10 != r11) goto L_0x1ef9
            java.lang.String r11 = "GPS"
            r4.append(r11)
            goto L_0x1efc
        L_0x1ef9:
            r4.append(r10)
        L_0x1efc:
            java.lang.String r11 = ": "
            r4.append(r11)
            android.os.BatteryStats$Timer r11 = r8.getSensorTime()
            if (r11 == 0) goto L_0x1faf
            long r182 = r11.getTotalTimeLocked(r1, r7)
            long r182 = r182 + r18
            r374 = r6
            long r5 = r182 / r16
            int r12 = r11.getCountLocked(r7)
            r375 = r0
            android.os.BatteryStats$Timer r0 = r8.getSensorBackgroundTime()
            if (r0 == 0) goto L_0x1f24
            int r182 = r0.getCountLocked(r7)
            goto L_0x1f26
        L_0x1f24:
            r182 = 0
        L_0x1f26:
            r376 = r182
            r379 = r8
            r380 = r9
            r378 = r14
            r377 = r15
            r14 = r372
            long r8 = r11.getTotalDurationMsLocked(r14)
            if (r0 == 0) goto L_0x1f42
            long r182 = r0.getTotalDurationMsLocked(r14)
            goto L_0x1f44
        L_0x1f42:
            r182 = r26
        L_0x1f44:
            r381 = r182
            int r182 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r182 == 0) goto L_0x1f9c
            int r182 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r182 == 0) goto L_0x1f5b
            formatTimeMs(r4, r5)
            r383 = r0
            java.lang.String r0 = "blamed realtime, "
            r4.append(r0)
            goto L_0x1f5e
        L_0x1f5b:
            r383 = r0
        L_0x1f5e:
            formatTimeMs(r4, r8)
            java.lang.String r0 = "realtime ("
            r4.append(r0)
            r4.append(r12)
            java.lang.String r0 = " times)"
            r4.append(r0)
            r384 = r5
            r5 = r381
            int r0 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r0 != 0) goto L_0x1f81
            r0 = r376
            if (r0 <= 0) goto L_0x1f7d
            goto L_0x1f83
        L_0x1f7d:
            r386 = r3
            goto L_0x1fae
        L_0x1f81:
            r0 = r376
        L_0x1f83:
            r386 = r3
            java.lang.String r3 = ", "
            r4.append(r3)
            formatTimeMs(r4, r5)
            java.lang.String r3 = "background ("
            r4.append(r3)
            r4.append(r0)
            java.lang.String r3 = " times)"
            r4.append(r3)
            goto L_0x1fae
        L_0x1f9c:
            r383 = r0
            r386 = r3
            r384 = r5
            r0 = r376
            r5 = r381
            java.lang.String r3 = "(not used)"
            r4.append(r3)
        L_0x1fae:
            goto L_0x1fcb
        L_0x1faf:
            r375 = r0
            r386 = r3
            r374 = r6
            r379 = r8
            r380 = r9
            r378 = r14
            r377 = r15
            r14 = r372
            java.lang.String r0 = "(not used)"
            r4.append(r0)
        L_0x1fcb:
            java.lang.String r0 = r4.toString()
            r3 = r418
            r3.println(r0)
            r121 = 1
            r0 = r374
            int r6 = r0 + 1
            r372 = r14
            r0 = r375
            r15 = r377
            r14 = r378
            r3 = r386
            r5 = 0
            goto L_0x1ed2
        L_0x1fe8:
            r375 = r0
            r386 = r3
            r378 = r14
            r377 = r15
            r14 = r372
            r3 = r418
            r13 = r419
            android.os.BatteryStats$Timer r10 = r371.getVibratorOnTimer()
            java.lang.String r0 = "Vibrator"
            r8 = r418
            r9 = r4
            r11 = r1
            r5 = r13
            r13 = r420
            r182 = r14
            r6 = r378
            r14 = r419
            r184 = r377
            r15 = r0
            boolean r0 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r0 = r121 | r0
            android.os.BatteryStats$Timer r10 = r371.getForegroundActivityTimer()
            java.lang.String r15 = "Foreground activities"
            boolean r8 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r0 = r0 | r8
            android.os.BatteryStats$Timer r10 = r371.getForegroundServiceTimer()
            java.lang.String r15 = "Foreground services"
            r8 = r418
            boolean r8 = printTimer(r8, r9, r10, r11, r13, r14, r15)
            r0 = r0 | r8
            r8 = 0
            r10 = r0
            r0 = 0
        L_0x2032:
            r11 = 7
            if (r0 >= r11) goto L_0x206d
            r11 = r371
            long r12 = r11.getProcessStateTime(r0, r1, r7)
            int r14 = (r12 > r26 ? 1 : (r12 == r26 ? 0 : -1))
            if (r14 <= 0) goto L_0x2067
            long r8 = r8 + r12
            r14 = 0
            r4.setLength(r14)
            r4.append(r5)
            java.lang.String r14 = "    "
            r4.append(r14)
            java.lang.String[] r14 = android.os.BatteryStats.Uid.PROCESS_STATE_NAMES
            r14 = r14[r0]
            r4.append(r14)
            java.lang.String r14 = " for: "
            r4.append(r14)
            long r14 = r12 + r18
            long r14 = r14 / r16
            formatTimeMs(r4, r14)
            java.lang.String r14 = r4.toString()
            r3.println(r14)
            r10 = 1
        L_0x2067:
            int r0 = r0 + 1
            r371 = r11
            goto L_0x2032
        L_0x206d:
            r11 = r371
            int r0 = (r8 > r26 ? 1 : (r8 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x208d
            r0 = 0
            r4.setLength(r0)
            r4.append(r5)
            java.lang.String r0 = "    Total running: "
            r4.append(r0)
            long r12 = r8 + r18
            long r12 = r12 / r16
            formatTimeMs(r4, r12)
            java.lang.String r0 = r4.toString()
            r3.println(r0)
        L_0x208d:
            long r12 = r11.getUserCpuTimeUs(r7)
            long r14 = r11.getSystemCpuTimeUs(r7)
            int r0 = (r12 > r26 ? 1 : (r12 == r26 ? 0 : -1))
            if (r0 > 0) goto L_0x20a2
            int r0 = (r14 > r26 ? 1 : (r14 == r26 ? 0 : -1))
            if (r0 <= 0) goto L_0x209e
            goto L_0x20a2
        L_0x209e:
            r387 = r1
            goto L_0x20c8
        L_0x20a2:
            r0 = 0
            r4.setLength(r0)
            r4.append(r5)
            java.lang.String r0 = "    Total cpu time: u="
            r4.append(r0)
            r387 = r1
            long r0 = r12 / r16
            formatTimeMs(r4, r0)
            java.lang.String r0 = "s="
            r4.append(r0)
            long r0 = r14 / r16
            formatTimeMs(r4, r0)
            java.lang.String r0 = r4.toString()
            r3.println(r0)
        L_0x20c8:
            long[] r0 = r11.getCpuFreqTimes(r7)
            if (r0 == 0) goto L_0x210c
            r1 = 0
            r4.setLength(r1)
            java.lang.String r1 = "    Total cpu time per freq:"
            r4.append(r1)
            r1 = 0
        L_0x20d8:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x20fe
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r389 = r6
            java.lang.String r6 = " "
            r2.append(r6)
            r390 = r8
            r8 = r0[r1]
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            r4.append(r2)
            int r1 = r1 + 1
            r6 = r389
            r8 = r390
            goto L_0x20d8
        L_0x20fe:
            r389 = r6
            r390 = r8
            java.lang.String r1 = r4.toString()
            r3.println(r1)
            goto L_0x2112
        L_0x210c:
            r389 = r6
            r390 = r8
        L_0x2112:
            long[] r1 = r11.getScreenOffCpuFreqTimes(r7)
            if (r1 == 0) goto L_0x2145
            r2 = 0
            r4.setLength(r2)
            java.lang.String r2 = "    Total screen-off cpu time per freq:"
            r4.append(r2)
            r2 = 0
        L_0x2122:
            int r6 = r1.length
            if (r2 >= r6) goto L_0x213e
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r8 = " "
            r6.append(r8)
            r8 = r1[r2]
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            r4.append(r6)
            int r2 = r2 + 1
            goto L_0x2122
        L_0x213e:
            java.lang.String r2 = r4.toString()
            r3.println(r2)
        L_0x2145:
            r2 = 0
        L_0x2146:
            r6 = 7
            if (r2 >= r6) goto L_0x220c
            long[] r6 = r11.getCpuFreqTimes(r7, r2)
            if (r6 == 0) goto L_0x21a5
            r8 = 0
            r4.setLength(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "    Cpu times per freq at state "
            r8.append(r9)
            java.lang.String[] r9 = android.os.BatteryStats.Uid.PROCESS_STATE_NAMES
            r9 = r9[r2]
            r8.append(r9)
            java.lang.String r9 = ":"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r4.append(r8)
            r8 = 0
        L_0x2171:
            int r9 = r6.length
            if (r8 >= r9) goto L_0x2197
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r392 = r0
            java.lang.String r0 = " "
            r9.append(r0)
            r393 = r1
            r0 = r6[r8]
            r9.append(r0)
            java.lang.String r0 = r9.toString()
            r4.append(r0)
            int r8 = r8 + 1
            r0 = r392
            r1 = r393
            goto L_0x2171
        L_0x2197:
            r392 = r0
            r393 = r1
            java.lang.String r0 = r4.toString()
            r3.println(r0)
            goto L_0x21ab
        L_0x21a5:
            r392 = r0
            r393 = r1
        L_0x21ab:
            long[] r0 = r11.getScreenOffCpuFreqTimes(r7, r2)
            if (r0 == 0) goto L_0x21ff
            r1 = 0
            r4.setLength(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r8 = "   Screen-off cpu times per freq at state "
            r1.append(r8)
            java.lang.String[] r8 = android.os.BatteryStats.Uid.PROCESS_STATE_NAMES
            r8 = r8[r2]
            r1.append(r8)
            java.lang.String r8 = ":"
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            r4.append(r1)
            r1 = 0
        L_0x21d3:
            int r8 = r0.length
            if (r1 >= r8) goto L_0x21f4
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = " "
            r8.append(r9)
            r394 = r10
            r9 = r0[r1]
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r4.append(r8)
            int r1 = r1 + 1
            r10 = r394
            goto L_0x21d3
        L_0x21f4:
            r394 = r10
            java.lang.String r1 = r4.toString()
            r3.println(r1)
            goto L_0x2202
        L_0x21ff:
            r394 = r10
        L_0x2202:
            int r2 = r2 + 1
            r0 = r392
            r1 = r393
            r10 = r394
            goto L_0x2146
        L_0x220c:
            r392 = r0
            r393 = r1
            r394 = r10
            android.util.ArrayMap r0 = r11.getProcessStats()
            int r1 = r0.size()
            int r1 = r1 + -1
        L_0x221f:
            if (r1 < 0) goto L_0x23a8
            java.lang.Object r2 = r0.valueAt(r1)
            android.os.BatteryStats$Uid$Proc r2 = (android.os.BatteryStats.Uid.Proc) r2
            long r8 = r2.getUserTime(r7)
            r395 = r12
            long r12 = r2.getSystemTime(r7)
            r397 = r14
            long r14 = r2.getForegroundTime(r7)
            int r6 = r2.getStarts(r7)
            int r10 = r2.getNumCrashes(r7)
            r399 = r11
            int r11 = r2.getNumAnrs(r7)
            if (r7 != 0) goto L_0x224f
            int r121 = r2.countExcessivePowers()
            goto L_0x2251
        L_0x224f:
            r121 = 0
        L_0x2251:
            r400 = r121
            int r121 = (r8 > r26 ? 1 : (r8 == r26 ? 0 : -1))
            if (r121 != 0) goto L_0x2270
            int r121 = (r12 > r26 ? 1 : (r12 == r26 ? 0 : -1))
            if (r121 != 0) goto L_0x2270
            int r121 = (r14 > r26 ? 1 : (r14 == r26 ? 0 : -1))
            if (r121 != 0) goto L_0x2270
            if (r6 != 0) goto L_0x2270
            r7 = r400
            if (r7 != 0) goto L_0x2272
            if (r10 != 0) goto L_0x2272
            if (r11 == 0) goto L_0x226b
            goto L_0x2272
        L_0x226b:
            r402 = r0
            goto L_0x239a
        L_0x2270:
            r7 = r400
        L_0x2272:
            r401 = r2
            r2 = 0
            r4.setLength(r2)
            r4.append(r5)
            java.lang.String r2 = "    Proc "
            r4.append(r2)
            java.lang.Object r2 = r0.keyAt(r1)
            java.lang.String r2 = (java.lang.String) r2
            r4.append(r2)
            java.lang.String r2 = ":\n"
            r4.append(r2)
            r4.append(r5)
            java.lang.String r2 = "      CPU: "
            r4.append(r2)
            formatTimeMs(r4, r8)
            java.lang.String r2 = "usr + "
            r4.append(r2)
            formatTimeMs(r4, r12)
            java.lang.String r2 = "krn ; "
            r4.append(r2)
            formatTimeMs(r4, r14)
            java.lang.String r2 = "fg"
            r4.append(r2)
            if (r6 != 0) goto L_0x22bb
            if (r10 != 0) goto L_0x22bb
            if (r11 == 0) goto L_0x22b7
            goto L_0x22bb
        L_0x22b7:
            r402 = r0
            goto L_0x22fe
        L_0x22bb:
            java.lang.String r2 = "\n"
            r4.append(r2)
            r4.append(r5)
            java.lang.String r2 = "      "
            r4.append(r2)
            r2 = 0
            if (r6 == 0) goto L_0x22d8
            r2 = 1
            r4.append(r6)
            r402 = r0
            java.lang.String r0 = " starts"
            r4.append(r0)
            goto L_0x22db
        L_0x22d8:
            r402 = r0
        L_0x22db:
            if (r10 == 0) goto L_0x22ed
            if (r2 == 0) goto L_0x22e4
            java.lang.String r0 = ", "
            r4.append(r0)
        L_0x22e4:
            r2 = 1
            r4.append(r10)
            java.lang.String r0 = " crashes"
            r4.append(r0)
        L_0x22ed:
            if (r11 == 0) goto L_0x22fe
            if (r2 == 0) goto L_0x22f6
            java.lang.String r0 = ", "
            r4.append(r0)
        L_0x22f6:
            r4.append(r11)
            java.lang.String r0 = " anrs"
            r4.append(r0)
        L_0x22fe:
            java.lang.String r0 = r4.toString()
            r3.println(r0)
            r0 = 0
        L_0x2306:
            if (r0 >= r7) goto L_0x2387
            r403 = r6
            r2 = r401
            android.os.BatteryStats$Uid$Proc$ExcessivePower r6 = r2.getExcessivePower(r0)
            if (r6 == 0) goto L_0x236d
            r418.print(r419)
            r404 = r2
            java.lang.String r2 = "      * Killed for "
            r3.print(r2)
            int r2 = r6.type
            r405 = r7
            r7 = 2
            if (r2 != r7) goto L_0x232c
            java.lang.String r2 = "cpu"
            r3.print(r2)
            goto L_0x2332
        L_0x232c:
            java.lang.String r2 = "unknown"
            r3.print(r2)
        L_0x2332:
            java.lang.String r2 = " use: "
            r3.print(r2)
            r406 = r8
            long r7 = r6.usedTime
            android.util.TimeUtils.formatDuration((long) r7, (java.io.PrintWriter) r3)
            java.lang.String r2 = " over "
            r3.print(r2)
            long r7 = r6.overTime
            android.util.TimeUtils.formatDuration((long) r7, (java.io.PrintWriter) r3)
            long r7 = r6.overTime
            int r2 = (r7 > r26 ? 1 : (r7 == r26 ? 0 : -1))
            if (r2 == 0) goto L_0x2369
            java.lang.String r2 = " ("
            r3.print(r2)
            long r7 = r6.usedTime
            r185 = 100
            long r7 = r7 * r185
            r408 = r10
            long r9 = r6.overTime
            long r7 = r7 / r9
            r3.print(r7)
            java.lang.String r2 = "%)"
            r3.println(r2)
            goto L_0x2379
        L_0x2369:
            r408 = r10
            goto L_0x2379
        L_0x236d:
            r404 = r2
            r405 = r7
            r406 = r8
            r408 = r10
        L_0x2379:
            int r0 = r0 + 1
            r6 = r403
            r401 = r404
            r7 = r405
            r8 = r406
            r10 = r408
            goto L_0x2306
        L_0x2387:
            r403 = r6
            r405 = r7
            r406 = r8
            r408 = r10
            r404 = r401
            r0 = 1
            r394 = r0
        L_0x239a:
            int r1 = r1 + -1
            r12 = r395
            r14 = r397
            r11 = r399
            r0 = r402
            r7 = r420
            goto L_0x221f
        L_0x23a8:
            r402 = r0
            r399 = r11
            r395 = r12
            r397 = r14
            android.util.ArrayMap r0 = r399.getPackageStats()
            int r1 = r0.size()
            int r1 = r1 + -1
            r2 = r394
        L_0x23c0:
            if (r1 < 0) goto L_0x24c7
            r418.print(r419)
            java.lang.String r6 = "    Apk "
            r3.print(r6)
            java.lang.Object r6 = r0.keyAt(r1)
            java.lang.String r6 = (java.lang.String) r6
            r3.print(r6)
            java.lang.String r6 = ":"
            r3.println(r6)
            r6 = 0
            java.lang.Object r7 = r0.valueAt(r1)
            android.os.BatteryStats$Uid$Pkg r7 = (android.os.BatteryStats.Uid.Pkg) r7
            android.util.ArrayMap r8 = r7.getWakeupAlarmStats()
            int r9 = r8.size()
            int r9 = r9 + -1
        L_0x23e9:
            if (r9 < 0) goto L_0x2419
            r418.print(r419)
            java.lang.String r10 = "      Wakeup alarm "
            r3.print(r10)
            java.lang.Object r10 = r8.keyAt(r9)
            java.lang.String r10 = (java.lang.String) r10
            r3.print(r10)
            java.lang.String r10 = ": "
            r3.print(r10)
            java.lang.Object r10 = r8.valueAt(r9)
            android.os.BatteryStats$Counter r10 = (android.os.BatteryStats.Counter) r10
            r11 = r420
            int r10 = r10.getCountLocked(r11)
            r3.print(r10)
            java.lang.String r10 = " times"
            r3.println(r10)
            r6 = 1
            int r9 = r9 + -1
            goto L_0x23e9
        L_0x2419:
            r11 = r420
            android.util.ArrayMap r9 = r7.getServiceStats()
            int r10 = r9.size()
            int r10 = r10 + -1
        L_0x2425:
            if (r10 < 0) goto L_0x24a9
            java.lang.Object r12 = r9.valueAt(r10)
            android.os.BatteryStats$Uid$Pkg$Serv r12 = (android.os.BatteryStats.Uid.Pkg.Serv) r12
            r13 = r152
            long r152 = r12.getStartTime(r13, r11)
            int r15 = r12.getStarts(r11)
            r409 = r0
            int r0 = r12.getLaunches(r11)
            int r121 = (r152 > r26 ? 1 : (r152 == r26 ? 0 : -1))
            if (r121 != 0) goto L_0x244e
            if (r15 != 0) goto L_0x244e
            if (r0 == 0) goto L_0x2447
            goto L_0x244e
        L_0x2447:
            r410 = r7
            r411 = r8
            goto L_0x249d
        L_0x244e:
            r410 = r7
            r7 = 0
            r4.setLength(r7)
            r4.append(r5)
            java.lang.String r7 = "      Service "
            r4.append(r7)
            java.lang.Object r7 = r9.keyAt(r10)
            java.lang.String r7 = (java.lang.String) r7
            r4.append(r7)
            java.lang.String r7 = ":\n"
            r4.append(r7)
            r4.append(r5)
            java.lang.String r7 = "        Created for: "
            r4.append(r7)
            r411 = r8
            long r7 = r152 / r16
            formatTimeMs(r4, r7)
            java.lang.String r7 = "uptime\n"
            r4.append(r7)
            r4.append(r5)
            java.lang.String r7 = "        Starts: "
            r4.append(r7)
            r4.append(r15)
            java.lang.String r7 = ", launches: "
            r4.append(r7)
            r4.append(r0)
            java.lang.String r7 = r4.toString()
            r3.println(r7)
            r0 = 1
            r6 = r0
        L_0x249d:
            int r10 = r10 + -1
            r152 = r13
            r0 = r409
            r7 = r410
            r8 = r411
            goto L_0x2425
        L_0x24a9:
            r409 = r0
            r410 = r7
            r411 = r8
            r13 = r152
            if (r6 != 0) goto L_0x24be
            r418.print(r419)
            java.lang.String r0 = "      (nothing executed)"
            r3.println(r0)
        L_0x24be:
            r2 = 1
            int r1 = r1 + -1
            r152 = r13
            r0 = r409
            goto L_0x23c0
        L_0x24c7:
            r409 = r0
            r13 = r152
            r11 = r420
            if (r2 != 0) goto L_0x24d8
            r418.print(r419)
            java.lang.String r0 = "    (nothing executed)"
            r3.println(r0)
        L_0x24d8:
            int r0 = r147 + 1
            r5 = r4
            r152 = r13
            r7 = r122
            r146 = r125
            r6 = r134
            r138 = r143
            r158 = r144
            r199 = r174
            r15 = r180
            r150 = r182
            r197 = r387
            r1 = 0
            r14 = r3
            goto L_0x12ca
        L_0x24f3:
            r4 = r5
            r134 = r6
            r122 = r7
            r144 = r11
            r3 = r14
            r180 = r15
            r143 = r138
            r125 = r146
            r182 = r150
            r13 = r152
            r387 = r197
            r174 = r199
            r5 = r419
            r11 = r420
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpLocked(android.content.Context, java.io.PrintWriter, java.lang.String, int, int, boolean):void");
    }

    static void printBitDescriptions(StringBuilder sb, int oldval, int newval, HistoryTag wakelockTag, BitDescription[] descriptions, boolean longNames) {
        int diff = oldval ^ newval;
        if (diff != 0) {
            boolean didWake = false;
            for (BitDescription bd : descriptions) {
                if ((bd.mask & diff) != 0) {
                    sb.append(longNames ? WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER : SmsManager.REGEX_PREFIX_DELIMITER);
                    if (bd.shift < 0) {
                        sb.append((bd.mask & newval) != 0 ? "+" : NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        sb.append(longNames ? bd.name : bd.shortName);
                        if (bd.mask == 1073741824 && wakelockTag != null) {
                            didWake = true;
                            sb.append("=");
                            if (longNames) {
                                UserHandle.formatUid(sb, wakelockTag.uid);
                                sb.append(":\"");
                                sb.append(wakelockTag.string);
                                sb.append("\"");
                            } else {
                                sb.append(wakelockTag.poolIdx);
                            }
                        }
                    } else {
                        sb.append(longNames ? bd.name : bd.shortName);
                        sb.append("=");
                        int val = (bd.mask & newval) >> bd.shift;
                        if (bd.values == null || val < 0 || val >= bd.values.length) {
                            sb.append(val);
                        } else {
                            sb.append(longNames ? bd.values[val] : bd.shortValues[val]);
                        }
                    }
                }
            }
            if (!didWake && wakelockTag != null) {
                sb.append(longNames ? " wake_lock=" : ",w=");
                if (longNames) {
                    UserHandle.formatUid(sb, wakelockTag.uid);
                    sb.append(":\"");
                    sb.append(wakelockTag.string);
                    sb.append("\"");
                    return;
                }
                sb.append(wakelockTag.poolIdx);
            }
        }
    }

    public void prepareForDumpLocked() {
    }

    public static class HistoryPrinter {
        long lastTime = -1;
        int oldChargeMAh = -1;
        int oldHealth = -1;
        int oldLevel = -1;
        double oldModemRailChargeMah = -1.0d;
        int oldPlug = -1;
        int oldState = 0;
        int oldState2 = 0;
        int oldStatus = -1;
        int oldTemp = -1;
        int oldVolt = -1;
        double oldWifiRailChargeMah = -1.0d;

        /* access modifiers changed from: package-private */
        public void reset() {
            this.oldState2 = 0;
            this.oldState = 0;
            this.oldLevel = -1;
            this.oldStatus = -1;
            this.oldHealth = -1;
            this.oldPlug = -1;
            this.oldTemp = -1;
            this.oldVolt = -1;
            this.oldChargeMAh = -1;
            this.oldModemRailChargeMah = -1.0d;
            this.oldWifiRailChargeMah = -1.0d;
        }

        public void printNextItem(PrintWriter pw, HistoryItem rec, long baseTime, boolean checkin, boolean verbose) {
            pw.print(printNextItem(rec, baseTime, checkin, verbose));
        }

        public void printNextItem(ProtoOutputStream proto, HistoryItem rec, long baseTime, boolean verbose) {
            for (String line : printNextItem(rec, baseTime, true, verbose).split("\n")) {
                proto.write(2237677961222L, line);
            }
        }

        private String printNextItem(HistoryItem rec, long baseTime, boolean checkin, boolean verbose) {
            String[] eventNames;
            StringBuilder item = new StringBuilder();
            if (!checkin) {
                item.append("  ");
                TimeUtils.formatDuration(rec.time - baseTime, item, 19);
                item.append(" (");
                item.append(rec.numReadInts);
                item.append(") ");
            } else {
                item.append(9);
                item.append(',');
                item.append(BatteryStats.HISTORY_DATA);
                item.append(',');
                if (this.lastTime < 0) {
                    item.append(rec.time - baseTime);
                } else {
                    item.append(rec.time - this.lastTime);
                }
                this.lastTime = rec.time;
            }
            if (rec.cmd == 4) {
                if (checkin) {
                    item.append(SettingsStringUtil.DELIMITER);
                }
                item.append("START\n");
                reset();
            } else if (rec.cmd == 5 || rec.cmd == 7) {
                if (checkin) {
                    item.append(SettingsStringUtil.DELIMITER);
                }
                if (rec.cmd == 7) {
                    item.append("RESET:");
                    reset();
                }
                item.append("TIME:");
                if (checkin) {
                    item.append(rec.currentTime);
                    item.append("\n");
                } else {
                    item.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                    item.append(android.text.format.DateFormat.format((CharSequence) "yyyy-MM-dd-HH-mm-ss", rec.currentTime).toString());
                    item.append("\n");
                }
            } else if (rec.cmd == 8) {
                if (checkin) {
                    item.append(SettingsStringUtil.DELIMITER);
                }
                item.append("SHUTDOWN\n");
            } else if (rec.cmd == 6) {
                if (checkin) {
                    item.append(SettingsStringUtil.DELIMITER);
                }
                item.append("*OVERFLOW*\n");
            } else {
                if (!checkin) {
                    if (rec.batteryLevel < 10) {
                        item.append("00");
                    } else if (rec.batteryLevel < 100) {
                        item.append("0");
                    }
                    item.append(rec.batteryLevel);
                    if (verbose) {
                        item.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                        if (rec.states >= 0) {
                            if (rec.states < 16) {
                                item.append("0000000");
                            } else if (rec.states < 256) {
                                item.append("000000");
                            } else if (rec.states < 4096) {
                                item.append("00000");
                            } else if (rec.states < 65536) {
                                item.append("0000");
                            } else if (rec.states < 1048576) {
                                item.append("000");
                            } else if (rec.states < 16777216) {
                                item.append("00");
                            } else if (rec.states < 268435456) {
                                item.append("0");
                            }
                        }
                        item.append(Integer.toHexString(rec.states));
                    }
                } else if (this.oldLevel != rec.batteryLevel) {
                    this.oldLevel = rec.batteryLevel;
                    item.append(",Bl=");
                    item.append(rec.batteryLevel);
                }
                if (this.oldStatus != rec.batteryStatus) {
                    this.oldStatus = rec.batteryStatus;
                    item.append(checkin ? ",Bs=" : " status=");
                    switch (this.oldStatus) {
                        case 1:
                            item.append(checkin ? "?" : "unknown");
                            break;
                        case 2:
                            item.append(checkin ? FullBackup.CACHE_TREE_TOKEN : "charging");
                            break;
                        case 3:
                            item.append(checkin ? DateFormat.DAY : "discharging");
                            break;
                        case 4:
                            item.append(checkin ? "n" : "not-charging");
                            break;
                        case 5:
                            item.append(checkin ? FullBackup.FILES_TREE_TOKEN : "full");
                            break;
                        default:
                            item.append(this.oldStatus);
                            break;
                    }
                }
                if (this.oldHealth != rec.batteryHealth) {
                    this.oldHealth = rec.batteryHealth;
                    item.append(checkin ? ",Bh=" : " health=");
                    switch (this.oldHealth) {
                        case 1:
                            item.append(checkin ? "?" : "unknown");
                            break;
                        case 2:
                            item.append(checkin ? "g" : "good");
                            break;
                        case 3:
                            item.append(checkin ? BatteryStats.HISTORY_DATA : "overheat");
                            break;
                        case 4:
                            item.append(checkin ? DateFormat.DAY : "dead");
                            break;
                        case 5:
                            item.append(checkin ? "v" : "over-voltage");
                            break;
                        case 6:
                            item.append(checkin ? FullBackup.FILES_TREE_TOKEN : "failure");
                            break;
                        case 7:
                            item.append(checkin ? FullBackup.CACHE_TREE_TOKEN : "cold");
                            break;
                        default:
                            item.append(this.oldHealth);
                            break;
                    }
                }
                if (this.oldPlug != rec.batteryPlugType) {
                    this.oldPlug = rec.batteryPlugType;
                    item.append(checkin ? ",Bp=" : " plug=");
                    int i = this.oldPlug;
                    if (i != 4) {
                        switch (i) {
                            case 0:
                                item.append(checkin ? "n" : "none");
                                break;
                            case 1:
                                item.append(checkin ? FullBackup.APK_TREE_TOKEN : "ac");
                                break;
                            case 2:
                                item.append(checkin ? "u" : Context.USB_SERVICE);
                                break;
                            default:
                                item.append(this.oldPlug);
                                break;
                        }
                    } else {
                        item.append(checkin ? "w" : "wireless");
                    }
                }
                if (this.oldTemp != rec.batteryTemperature) {
                    this.oldTemp = rec.batteryTemperature;
                    item.append(checkin ? ",Bt=" : " temp=");
                    item.append(this.oldTemp);
                }
                if (this.oldVolt != rec.batteryVoltage) {
                    this.oldVolt = rec.batteryVoltage;
                    item.append(checkin ? ",Bv=" : " volt=");
                    item.append(this.oldVolt);
                }
                int chargeMAh = rec.batteryChargeUAh / 1000;
                if (this.oldChargeMAh != chargeMAh) {
                    this.oldChargeMAh = chargeMAh;
                    item.append(checkin ? ",Bcc=" : " charge=");
                    item.append(this.oldChargeMAh);
                }
                if (this.oldModemRailChargeMah != rec.modemRailChargeMah) {
                    this.oldModemRailChargeMah = rec.modemRailChargeMah;
                    item.append(checkin ? ",Mrc=" : " modemRailChargemAh=");
                    item.append(new DecimalFormat("#.##").format(this.oldModemRailChargeMah));
                }
                if (this.oldWifiRailChargeMah != rec.wifiRailChargeMah) {
                    this.oldWifiRailChargeMah = rec.wifiRailChargeMah;
                    item.append(checkin ? ",Wrc=" : " wifiRailChargemAh=");
                    item.append(new DecimalFormat("#.##").format(this.oldWifiRailChargeMah));
                }
                BatteryStats.printBitDescriptions(item, this.oldState, rec.states, rec.wakelockTag, BatteryStats.HISTORY_STATE_DESCRIPTIONS, !checkin);
                BatteryStats.printBitDescriptions(item, this.oldState2, rec.states2, (HistoryTag) null, BatteryStats.HISTORY_STATE2_DESCRIPTIONS, !checkin);
                if (rec.wakeReasonTag != null) {
                    if (checkin) {
                        item.append(",wr=");
                        item.append(rec.wakeReasonTag.poolIdx);
                    } else {
                        item.append(" wake_reason=");
                        item.append(rec.wakeReasonTag.uid);
                        item.append(":\"");
                        item.append(rec.wakeReasonTag.string);
                        item.append("\"");
                    }
                }
                if (rec.eventCode != 0) {
                    item.append(checkin ? SmsManager.REGEX_PREFIX_DELIMITER : WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                    if ((rec.eventCode & 32768) != 0) {
                        item.append("+");
                    } else if ((rec.eventCode & 16384) != 0) {
                        item.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    }
                    if (checkin) {
                        eventNames = BatteryStats.HISTORY_EVENT_CHECKIN_NAMES;
                    } else {
                        eventNames = BatteryStats.HISTORY_EVENT_NAMES;
                    }
                    int idx = rec.eventCode & HistoryItem.EVENT_TYPE_MASK;
                    if (idx < 0 || idx >= eventNames.length) {
                        item.append(checkin ? "Ev" : "event");
                        item.append(idx);
                    } else {
                        item.append(eventNames[idx]);
                    }
                    item.append("=");
                    if (checkin) {
                        item.append(rec.eventTag.poolIdx);
                    } else {
                        item.append(BatteryStats.HISTORY_EVENT_INT_FORMATTERS[idx].applyAsString(rec.eventTag.uid));
                        item.append(":\"");
                        item.append(rec.eventTag.string);
                        item.append("\"");
                    }
                }
                item.append("\n");
                if (rec.stepDetails != null) {
                    if (!checkin) {
                        item.append("                 Details: cpu=");
                        item.append(rec.stepDetails.userTime);
                        item.append("u+");
                        item.append(rec.stepDetails.systemTime);
                        item.append(DateFormat.SECOND);
                        if (rec.stepDetails.appCpuUid1 >= 0) {
                            item.append(" (");
                            printStepCpuUidDetails(item, rec.stepDetails.appCpuUid1, rec.stepDetails.appCpuUTime1, rec.stepDetails.appCpuSTime1);
                            if (rec.stepDetails.appCpuUid2 >= 0) {
                                item.append(", ");
                                printStepCpuUidDetails(item, rec.stepDetails.appCpuUid2, rec.stepDetails.appCpuUTime2, rec.stepDetails.appCpuSTime2);
                            }
                            if (rec.stepDetails.appCpuUid3 >= 0) {
                                item.append(", ");
                                printStepCpuUidDetails(item, rec.stepDetails.appCpuUid3, rec.stepDetails.appCpuUTime3, rec.stepDetails.appCpuSTime3);
                            }
                            item.append(')');
                        }
                        item.append("\n");
                        item.append("                          /proc/stat=");
                        item.append(rec.stepDetails.statUserTime);
                        item.append(" usr, ");
                        item.append(rec.stepDetails.statSystemTime);
                        item.append(" sys, ");
                        item.append(rec.stepDetails.statIOWaitTime);
                        item.append(" io, ");
                        item.append(rec.stepDetails.statIrqTime);
                        item.append(" irq, ");
                        item.append(rec.stepDetails.statSoftIrqTime);
                        item.append(" sirq, ");
                        item.append(rec.stepDetails.statIdlTime);
                        item.append(" idle");
                        int totalRun = rec.stepDetails.statUserTime + rec.stepDetails.statSystemTime + rec.stepDetails.statIOWaitTime + rec.stepDetails.statIrqTime + rec.stepDetails.statSoftIrqTime;
                        int total = rec.stepDetails.statIdlTime + totalRun;
                        if (total > 0) {
                            item.append(" (");
                            item.append(String.format("%.1f%%", new Object[]{Float.valueOf((((float) totalRun) / ((float) total)) * 100.0f)}));
                            item.append(" of ");
                            StringBuilder sb = new StringBuilder(64);
                            BatteryStats.formatTimeMsNoSpace(sb, (long) (total * 10));
                            item.append(sb);
                            item.append(")");
                        }
                        item.append(", PlatformIdleStat ");
                        item.append(rec.stepDetails.statPlatformIdleState);
                        item.append("\n");
                        item.append(", SubsystemPowerState ");
                        item.append(rec.stepDetails.statSubsystemPowerState);
                        item.append("\n");
                    } else {
                        item.append(9);
                        item.append(',');
                        item.append(BatteryStats.HISTORY_DATA);
                        item.append(",0,Dcpu=");
                        item.append(rec.stepDetails.userTime);
                        item.append(SettingsStringUtil.DELIMITER);
                        item.append(rec.stepDetails.systemTime);
                        if (rec.stepDetails.appCpuUid1 >= 0) {
                            printStepCpuUidCheckinDetails(item, rec.stepDetails.appCpuUid1, rec.stepDetails.appCpuUTime1, rec.stepDetails.appCpuSTime1);
                            if (rec.stepDetails.appCpuUid2 >= 0) {
                                printStepCpuUidCheckinDetails(item, rec.stepDetails.appCpuUid2, rec.stepDetails.appCpuUTime2, rec.stepDetails.appCpuSTime2);
                            }
                            if (rec.stepDetails.appCpuUid3 >= 0) {
                                printStepCpuUidCheckinDetails(item, rec.stepDetails.appCpuUid3, rec.stepDetails.appCpuUTime3, rec.stepDetails.appCpuSTime3);
                            }
                        }
                        item.append("\n");
                        item.append(9);
                        item.append(',');
                        item.append(BatteryStats.HISTORY_DATA);
                        item.append(",0,Dpst=");
                        item.append(rec.stepDetails.statUserTime);
                        item.append(',');
                        item.append(rec.stepDetails.statSystemTime);
                        item.append(',');
                        item.append(rec.stepDetails.statIOWaitTime);
                        item.append(',');
                        item.append(rec.stepDetails.statIrqTime);
                        item.append(',');
                        item.append(rec.stepDetails.statSoftIrqTime);
                        item.append(',');
                        item.append(rec.stepDetails.statIdlTime);
                        item.append(',');
                        if (rec.stepDetails.statPlatformIdleState != null) {
                            item.append(rec.stepDetails.statPlatformIdleState);
                            if (rec.stepDetails.statSubsystemPowerState != null) {
                                item.append(',');
                            }
                        }
                        if (rec.stepDetails.statSubsystemPowerState != null) {
                            item.append(rec.stepDetails.statSubsystemPowerState);
                        }
                        item.append("\n");
                    }
                }
                this.oldState = rec.states;
                this.oldState2 = rec.states2;
            }
            return item.toString();
        }

        private void printStepCpuUidDetails(StringBuilder sb, int uid, int utime, int stime) {
            UserHandle.formatUid(sb, uid);
            sb.append("=");
            sb.append(utime);
            sb.append("u+");
            sb.append(stime);
            sb.append(DateFormat.SECOND);
        }

        private void printStepCpuUidCheckinDetails(StringBuilder sb, int uid, int utime, int stime) {
            sb.append('/');
            sb.append(uid);
            sb.append(SettingsStringUtil.DELIMITER);
            sb.append(utime);
            sb.append(SettingsStringUtil.DELIMITER);
            sb.append(stime);
        }
    }

    private void printSizeValue(PrintWriter pw, long size) {
        float result = (float) size;
        String suffix = "";
        if (result >= 10240.0f) {
            suffix = "KB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "MB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "GB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "TB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "PB";
            result /= 1024.0f;
        }
        pw.print((int) result);
        pw.print(suffix);
    }

    private static boolean dumpTimeEstimate(PrintWriter pw, String label1, String label2, String label3, long estimatedTime) {
        if (estimatedTime < 0) {
            return false;
        }
        pw.print(label1);
        pw.print(label2);
        pw.print(label3);
        StringBuilder sb = new StringBuilder(64);
        formatTimeMs(sb, estimatedTime);
        pw.print(sb);
        pw.println();
        return true;
    }

    private static boolean dumpDurationSteps(PrintWriter pw, String prefix, String header, LevelStepTracker steps, boolean checkin) {
        int count;
        int count2;
        PrintWriter printWriter = pw;
        String str = header;
        LevelStepTracker levelStepTracker = steps;
        char c = 0;
        if (levelStepTracker == null || (count = levelStepTracker.mNumStepDurations) <= 0) {
            return false;
        }
        if (!checkin) {
            printWriter.println(str);
        }
        String[] lineArgs = new String[5];
        int i = 0;
        while (i < count) {
            long duration = levelStepTracker.getDurationAt(i);
            int level = levelStepTracker.getLevelAt(i);
            long initMode = (long) levelStepTracker.getInitModeAt(i);
            long modMode = (long) levelStepTracker.getModModeAt(i);
            if (checkin) {
                lineArgs[c] = Long.toString(duration);
                lineArgs[1] = Integer.toString(level);
                if ((modMode & 3) == 0) {
                    count2 = count;
                    switch (((int) (initMode & 3)) + 1) {
                        case 1:
                            lineArgs[2] = "s-";
                            break;
                        case 2:
                            lineArgs[2] = "s+";
                            break;
                        case 3:
                            lineArgs[2] = "sd";
                            break;
                        case 4:
                            lineArgs[2] = "sds";
                            break;
                        default:
                            lineArgs[2] = "?";
                            break;
                    }
                } else {
                    count2 = count;
                    lineArgs[2] = "";
                }
                if ((modMode & 4) == 0) {
                    lineArgs[3] = (initMode & 4) != 0 ? "p+" : "p-";
                } else {
                    lineArgs[3] = "";
                }
                if ((modMode & 8) == 0) {
                    lineArgs[4] = (8 & initMode) != 0 ? "i+" : "i-";
                } else {
                    lineArgs[4] = "";
                }
                dumpLine(printWriter, 0, "i", str, (Object[]) lineArgs);
            } else {
                count2 = count;
                pw.print(prefix);
                printWriter.print("#");
                printWriter.print(i);
                printWriter.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                TimeUtils.formatDuration(duration, printWriter);
                printWriter.print(" to ");
                printWriter.print(level);
                boolean haveModes = false;
                if ((modMode & 3) == 0) {
                    printWriter.print(" (");
                    switch (((int) (initMode & 3)) + 1) {
                        case 1:
                            printWriter.print("screen-off");
                            break;
                        case 2:
                            printWriter.print("screen-on");
                            break;
                        case 3:
                            printWriter.print("screen-doze");
                            break;
                        case 4:
                            printWriter.print("screen-doze-suspend");
                            break;
                        default:
                            printWriter.print("screen-?");
                            break;
                    }
                    haveModes = true;
                }
                if ((modMode & 4) == 0) {
                    printWriter.print(haveModes ? ", " : " (");
                    printWriter.print((initMode & 4) != 0 ? "power-save-on" : "power-save-off");
                    haveModes = true;
                }
                if ((modMode & 8) == 0) {
                    printWriter.print(haveModes ? ", " : " (");
                    printWriter.print((initMode & 8) != 0 ? "device-idle-on" : "device-idle-off");
                    haveModes = true;
                }
                if (haveModes) {
                    printWriter.print(")");
                }
                pw.println();
            }
            i++;
            count = count2;
            str = header;
            levelStepTracker = steps;
            c = 0;
        }
        return true;
    }

    private static void dumpDurationSteps(ProtoOutputStream proto, long fieldId, LevelStepTracker steps) {
        ProtoOutputStream protoOutputStream = proto;
        LevelStepTracker levelStepTracker = steps;
        if (levelStepTracker != null) {
            int count = levelStepTracker.mNumStepDurations;
            for (int i = 0; i < count; i++) {
                long token = proto.start(fieldId);
                protoOutputStream.write(1112396529665L, levelStepTracker.getDurationAt(i));
                protoOutputStream.write(1120986464258L, levelStepTracker.getLevelAt(i));
                long initMode = (long) levelStepTracker.getInitModeAt(i);
                long modMode = (long) levelStepTracker.getModModeAt(i);
                int ds = 0;
                int i2 = 1;
                if ((modMode & 3) == 0) {
                    switch (((int) (3 & initMode)) + 1) {
                        case 1:
                            ds = 2;
                            break;
                        case 2:
                            ds = 1;
                            break;
                        case 3:
                            ds = 3;
                            break;
                        case 4:
                            ds = 4;
                            break;
                        default:
                            ds = 5;
                            break;
                    }
                }
                protoOutputStream.write(1159641169923L, ds);
                int psm = 0;
                int i3 = 2;
                if ((modMode & 4) == 0) {
                    if ((4 & initMode) == 0) {
                        i2 = 2;
                    }
                    psm = i2;
                }
                protoOutputStream.write(1159641169924L, psm);
                int im = 0;
                if ((modMode & 8) == 0) {
                    if ((8 & initMode) == 0) {
                        i3 = 3;
                    }
                    im = i3;
                }
                protoOutputStream.write(1159641169925L, im);
                protoOutputStream.end(token);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0160  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void dumpHistoryLocked(java.io.PrintWriter r29, int r30, long r31, boolean r33) {
        /*
            r28 = this;
            r8 = r29
            android.os.BatteryStats$HistoryPrinter r0 = new android.os.BatteryStats$HistoryPrinter
            r0.<init>()
            android.os.BatteryStats$HistoryItem r1 = new android.os.BatteryStats$HistoryItem
            r1.<init>()
            r9 = r1
            r1 = -1
            r3 = -1
            r5 = 0
            r6 = r5
            r4 = r3
            r2 = r1
            r1 = 0
        L_0x0016:
            r11 = r1
            r12 = r28
            boolean r1 = r12.getNextHistoryLocked(r9)
            r13 = 0
            if (r1 == 0) goto L_0x018d
            long r2 = r9.time
            int r1 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r1 >= 0) goto L_0x0028
            r4 = r2
        L_0x0028:
            r15 = r4
            long r4 = r9.time
            int r1 = (r4 > r31 ? 1 : (r4 == r31 ? 0 : -1))
            if (r1 < 0) goto L_0x0186
            int r1 = (r31 > r13 ? 1 : (r31 == r13 ? 0 : -1))
            r17 = 1
            if (r1 < 0) goto L_0x0164
            if (r6 != 0) goto L_0x0164
            byte r1 = r9.cmd
            r4 = 5
            if (r1 == r4) goto L_0x0074
            byte r1 = r9.cmd
            r5 = 7
            if (r1 == r5) goto L_0x0074
            byte r1 = r9.cmd
            r5 = 4
            if (r1 == r5) goto L_0x0074
            byte r1 = r9.cmd
            r5 = 8
            if (r1 != r5) goto L_0x004e
            r13 = r2
            goto L_0x0075
        L_0x004e:
            long r7 = r9.currentTime
            int r1 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r1 == 0) goto L_0x0070
            r7 = 1
            byte r8 = r9.cmd
            r9.cmd = r4
            r1 = r30 & 32
            if (r1 == 0) goto L_0x0060
            r6 = r17
            goto L_0x0061
        L_0x0060:
            r6 = 0
        L_0x0061:
            r1 = r29
            r13 = r2
            r2 = r9
            r3 = r15
            r5 = r33
            r0.printNextItem(r1, r2, r3, r5, r6)
            r9.cmd = r8
            r8 = r7
            r10 = 0
            goto L_0x008b
        L_0x0070:
            r13 = r2
            r8 = r6
            r10 = 0
            goto L_0x008b
        L_0x0074:
            r13 = r2
        L_0x0075:
            r8 = 1
            r1 = r30 & 32
            if (r1 == 0) goto L_0x007d
            r7 = r17
            goto L_0x007e
        L_0x007d:
            r7 = 0
        L_0x007e:
            r1 = r0
            r2 = r29
            r3 = r9
            r4 = r15
            r6 = r33
            r10 = 0
            r1.printNextItem(r2, r3, r4, r6, r7)
            r9.cmd = r10
        L_0x008b:
            if (r11 == 0) goto L_0x0160
            byte r1 = r9.cmd
            if (r1 == 0) goto L_0x00a5
            r1 = r30 & 32
            if (r1 == 0) goto L_0x0098
            r7 = r17
            goto L_0x0099
        L_0x0098:
            r7 = r10
        L_0x0099:
            r1 = r0
            r2 = r29
            r3 = r9
            r4 = r15
            r6 = r33
            r1.printNextItem(r2, r3, r4, r6, r7)
            r9.cmd = r10
        L_0x00a5:
            int r7 = r9.eventCode
            android.os.BatteryStats$HistoryTag r6 = r9.eventTag
            android.os.BatteryStats$HistoryTag r1 = new android.os.BatteryStats$HistoryTag
            r1.<init>()
            r9.eventTag = r1
            r1 = r10
        L_0x00b1:
            r4 = r1
            r1 = 22
            if (r4 >= r1) goto L_0x0154
            java.util.HashMap r18 = r11.getStateForEvent(r4)
            if (r18 != 0) goto L_0x00c7
            r23 = r4
            r10 = r6
            r26 = r8
            r8 = r7
            r7 = 0
            goto L_0x014b
        L_0x00c7:
            java.util.Set r1 = r18.entrySet()
            java.util.Iterator r19 = r1.iterator()
        L_0x00cf:
            boolean r1 = r19.hasNext()
            if (r1 == 0) goto L_0x0144
            java.lang.Object r1 = r19.next()
            r20 = r1
            java.util.Map$Entry r20 = (java.util.Map.Entry) r20
            java.lang.Object r1 = r20.getValue()
            r5 = r1
            android.util.SparseIntArray r5 = (android.util.SparseIntArray) r5
            r1 = r10
        L_0x00e5:
            r3 = r1
            int r1 = r5.size()
            if (r3 >= r1) goto L_0x0138
            r9.eventCode = r4
            android.os.BatteryStats$HistoryTag r1 = r9.eventTag
            java.lang.Object r2 = r20.getKey()
            java.lang.String r2 = (java.lang.String) r2
            r1.string = r2
            android.os.BatteryStats$HistoryTag r1 = r9.eventTag
            int r2 = r5.keyAt(r3)
            r1.uid = r2
            android.os.BatteryStats$HistoryTag r1 = r9.eventTag
            int r2 = r5.valueAt(r3)
            r1.poolIdx = r2
            r1 = r30 & 32
            if (r1 == 0) goto L_0x010f
            r21 = r17
            goto L_0x0111
        L_0x010f:
            r21 = r10
        L_0x0111:
            r1 = r0
            r2 = r29
            r22 = r3
            r3 = r9
            r23 = r4
            r24 = r5
            r4 = r15
            r10 = r6
            r6 = r33
            r26 = r8
            r8 = r7
            r7 = r21
            r1.printNextItem(r2, r3, r4, r6, r7)
            r7 = 0
            r9.wakeReasonTag = r7
            r9.wakelockTag = r7
            int r1 = r22 + 1
            r7 = r8
            r6 = r10
            r4 = r23
            r5 = r24
            r8 = r26
            r10 = 0
            goto L_0x00e5
        L_0x0138:
            r23 = r4
            r10 = r6
            r26 = r8
            r8 = r7
            r7 = 0
            r7 = r8
            r8 = r26
            r10 = 0
            goto L_0x00cf
        L_0x0144:
            r23 = r4
            r10 = r6
            r26 = r8
            r8 = r7
            r7 = 0
        L_0x014b:
            int r1 = r23 + 1
            r7 = r8
            r6 = r10
            r8 = r26
            r10 = 0
            goto L_0x00b1
        L_0x0154:
            r10 = r6
            r26 = r8
            r8 = r7
            r7 = 0
            r9.eventCode = r8
            r9.eventTag = r10
            r1 = 0
            r11 = r1
            goto L_0x0168
        L_0x0160:
            r26 = r8
            r7 = 0
            goto L_0x0168
        L_0x0164:
            r13 = r2
            r7 = 0
            r26 = r6
        L_0x0168:
            r1 = r30 & 32
            if (r1 == 0) goto L_0x016f
            r25 = r17
            goto L_0x0171
        L_0x016f:
            r25 = 0
        L_0x0171:
            r1 = r0
            r2 = r29
            r3 = r9
            r4 = r15
            r6 = r33
            r8 = r7
            r7 = r25
            r1.printNextItem(r2, r3, r4, r6, r7)
            r1 = r11
            r2 = r13
            r6 = r26
            r8 = r29
            goto L_0x0016
        L_0x0186:
            r13 = r2
            r1 = r11
            r4 = r15
            r8 = r29
            goto L_0x0016
        L_0x018d:
            int r1 = (r31 > r13 ? 1 : (r31 == r13 ? 0 : -1))
            if (r1 < 0) goto L_0x01aa
            r28.commitCurrentHistoryBatchLocked()
            if (r33 == 0) goto L_0x0199
            java.lang.String r7 = "NEXT: "
            goto L_0x019b
        L_0x0199:
            java.lang.String r7 = "  NEXT: "
        L_0x019b:
            r8 = r29
            r8.print(r7)
            r13 = 1
            r27 = r0
            long r0 = r2 + r13
            r8.println(r0)
            goto L_0x01ae
        L_0x01aa:
            r27 = r0
            r8 = r29
        L_0x01ae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpHistoryLocked(java.io.PrintWriter, int, long, boolean):void");
    }

    private void dumpDailyLevelStepSummary(PrintWriter pw, String prefix, String label, LevelStepTracker steps, StringBuilder tmpSb, int[] tmpOutInt) {
        PrintWriter printWriter = pw;
        String str = label;
        StringBuilder sb = tmpSb;
        if (steps != null) {
            long timeRemaining = steps.computeTimeEstimate(0, 0, tmpOutInt);
            if (timeRemaining >= 0) {
                pw.print(prefix);
                printWriter.print(str);
                printWriter.print(" total time: ");
                sb.setLength(0);
                formatTimeMs(sb, timeRemaining);
                printWriter.print(sb);
                printWriter.print(" (from ");
                printWriter.print(tmpOutInt[0]);
                printWriter.println(" steps)");
            }
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < STEP_LEVEL_MODES_OF_INTEREST.length) {
                    int i3 = i2;
                    long estimatedTime = steps.computeTimeEstimate((long) STEP_LEVEL_MODES_OF_INTEREST[i2], (long) STEP_LEVEL_MODE_VALUES[i2], tmpOutInt);
                    if (estimatedTime > 0) {
                        pw.print(prefix);
                        printWriter.print(str);
                        printWriter.print(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                        printWriter.print(STEP_LEVEL_MODE_LABELS[i3]);
                        printWriter.print(" time: ");
                        sb.setLength(0);
                        formatTimeMs(sb, estimatedTime);
                        printWriter.print(sb);
                        printWriter.print(" (from ");
                        printWriter.print(tmpOutInt[0]);
                        printWriter.println(" steps)");
                    }
                    i = i3 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private void dumpDailyPackageChanges(PrintWriter pw, String prefix, ArrayList<PackageChange> changes) {
        if (changes != null) {
            pw.print(prefix);
            pw.println("Package changes:");
            for (int i = 0; i < changes.size(); i++) {
                PackageChange pc = changes.get(i);
                if (pc.mUpdate) {
                    pw.print(prefix);
                    pw.print("  Update ");
                    pw.print(pc.mPackageName);
                    pw.print(" vers=");
                    pw.println(pc.mVersionCode);
                } else {
                    pw.print(prefix);
                    pw.print("  Uninstall ");
                    pw.println(pc.mPackageName);
                }
            }
        }
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dumpLocked(android.content.Context r28, java.io.PrintWriter r29, int r30, int r31, long r32) {
        /*
            r27 = this;
            r14 = r27
            r15 = r29
            r27.prepareForDumpLocked()
            r0 = r30 & 14
            if (r0 == 0) goto L_0x000d
            r0 = 1
            goto L_0x000e
        L_0x000d:
            r0 = 0
        L_0x000e:
            r16 = r0
            r0 = r30 & 8
            r8 = 0
            if (r0 != 0) goto L_0x0018
            if (r16 != 0) goto L_0x00c8
        L_0x0018:
            int r0 = r27.getHistoryTotalSize()
            long r10 = (long) r0
            int r0 = r27.getHistoryUsedSize()
            long r6 = (long) r0
            boolean r0 = r27.startIteratingHistoryLocked()
            if (r0 == 0) goto L_0x0081
            java.lang.String r0 = "Battery History ("
            r15.print(r0)     // Catch:{ all -> 0x007a }
            r0 = 100
            long r0 = r0 * r6
            long r0 = r0 / r10
            r15.print(r0)     // Catch:{ all -> 0x007a }
            java.lang.String r0 = "% used, "
            r15.print(r0)     // Catch:{ all -> 0x007a }
            r14.printSizeValue(r15, r6)     // Catch:{ all -> 0x007a }
            java.lang.String r0 = " used of "
            r15.print(r0)     // Catch:{ all -> 0x007a }
            r14.printSizeValue(r15, r10)     // Catch:{ all -> 0x007a }
            java.lang.String r0 = ", "
            r15.print(r0)     // Catch:{ all -> 0x007a }
            int r0 = r27.getHistoryStringPoolSize()     // Catch:{ all -> 0x007a }
            r15.print(r0)     // Catch:{ all -> 0x007a }
            java.lang.String r0 = " strings using "
            r15.print(r0)     // Catch:{ all -> 0x007a }
            int r0 = r27.getHistoryStringPoolBytes()     // Catch:{ all -> 0x007a }
            long r0 = (long) r0     // Catch:{ all -> 0x007a }
            r14.printSizeValue(r15, r0)     // Catch:{ all -> 0x007a }
            java.lang.String r0 = "):"
            r15.println(r0)     // Catch:{ all -> 0x007a }
            r0 = 0
            r1 = r27
            r2 = r29
            r3 = r30
            r4 = r32
            r17 = r6
            r6 = r0
            r1.dumpHistoryLocked(r2, r3, r4, r6)     // Catch:{ all -> 0x0078 }
            r29.println()     // Catch:{ all -> 0x0078 }
            r27.finishIteratingHistoryLocked()
            goto L_0x0083
        L_0x0078:
            r0 = move-exception
            goto L_0x007d
        L_0x007a:
            r0 = move-exception
            r17 = r6
        L_0x007d:
            r27.finishIteratingHistoryLocked()
            throw r0
        L_0x0081:
            r17 = r6
        L_0x0083:
            boolean r0 = r27.startIteratingOldHistoryLocked()
            if (r0 == 0) goto L_0x00c8
            android.os.BatteryStats$HistoryItem r0 = new android.os.BatteryStats$HistoryItem     // Catch:{ all -> 0x00c3 }
            r0.<init>()     // Catch:{ all -> 0x00c3 }
            java.lang.String r1 = "Old battery History:"
            r15.println(r1)     // Catch:{ all -> 0x00c3 }
            android.os.BatteryStats$HistoryPrinter r1 = new android.os.BatteryStats$HistoryPrinter     // Catch:{ all -> 0x00c3 }
            r1.<init>()     // Catch:{ all -> 0x00c3 }
            r2 = -1
        L_0x009a:
            boolean r4 = r14.getNextOldHistoryLocked(r0)     // Catch:{ all -> 0x00c3 }
            if (r4 == 0) goto L_0x00bc
            int r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r4 >= 0) goto L_0x00a7
            long r4 = r0.time     // Catch:{ all -> 0x00c3 }
            r2 = r4
        L_0x00a7:
            r19 = r2
            r6 = 0
            r2 = r30 & 32
            if (r2 == 0) goto L_0x00b0
            r7 = 1
            goto L_0x00b1
        L_0x00b0:
            r7 = 0
        L_0x00b1:
            r2 = r29
            r3 = r0
            r4 = r19
            r1.printNextItem(r2, r3, r4, r6, r7)     // Catch:{ all -> 0x00c3 }
            r2 = r19
            goto L_0x009a
        L_0x00bc:
            r29.println()     // Catch:{ all -> 0x00c3 }
            r27.finishIteratingOldHistoryLocked()
            goto L_0x00c8
        L_0x00c3:
            r0 = move-exception
            r27.finishIteratingOldHistoryLocked()
            throw r0
        L_0x00c8:
            if (r16 == 0) goto L_0x00cf
            r0 = r30 & 6
            if (r0 != 0) goto L_0x00cf
            return
        L_0x00cf:
            if (r16 != 0) goto L_0x013b
            android.util.SparseArray r0 = r27.getUidStats()
            int r1 = r0.size()
            r2 = 0
            long r3 = android.os.SystemClock.elapsedRealtime()
            r5 = r2
            r2 = 0
        L_0x00e0:
            if (r2 >= r1) goto L_0x0136
            java.lang.Object r6 = r0.valueAt(r2)
            android.os.BatteryStats$Uid r6 = (android.os.BatteryStats.Uid) r6
            android.util.SparseArray r7 = r6.getPidStats()
            if (r7 == 0) goto L_0x0131
            r10 = r5
            r5 = 0
        L_0x00f0:
            int r11 = r7.size()
            if (r5 >= r11) goto L_0x0130
            java.lang.Object r11 = r7.valueAt(r5)
            android.os.BatteryStats$Uid$Pid r11 = (android.os.BatteryStats.Uid.Pid) r11
            if (r10 != 0) goto L_0x0104
            java.lang.String r13 = "Per-PID Stats:"
            r15.println(r13)
            r10 = 1
        L_0x0104:
            long r8 = r11.mWakeSumMs
            int r13 = r11.mWakeNesting
            if (r13 <= 0) goto L_0x010f
            long r12 = r11.mWakeStartMs
            long r12 = r3 - r12
            goto L_0x0111
        L_0x010f:
            r12 = 0
        L_0x0111:
            long r8 = r8 + r12
            java.lang.String r12 = "  PID "
            r15.print(r12)
            int r12 = r7.keyAt(r5)
            r15.print(r12)
            java.lang.String r12 = " wake time: "
            r15.print(r12)
            android.util.TimeUtils.formatDuration((long) r8, (java.io.PrintWriter) r15)
            java.lang.String r12 = ""
            r15.println(r12)
            int r5 = r5 + 1
            r8 = 0
            goto L_0x00f0
        L_0x0130:
            r5 = r10
        L_0x0131:
            int r2 = r2 + 1
            r8 = 0
            goto L_0x00e0
        L_0x0136:
            if (r5 == 0) goto L_0x013b
            r29.println()
        L_0x013b:
            if (r16 == 0) goto L_0x0145
            r0 = r30 & 2
            if (r0 == 0) goto L_0x0142
            goto L_0x0145
        L_0x0142:
            r12 = 0
            goto L_0x01d6
        L_0x0145:
            java.lang.String r0 = "  "
            java.lang.String r1 = "Discharge step durations:"
            android.os.BatteryStats$LevelStepTracker r2 = r27.getDischargeLevelStepTracker()
            r3 = 0
            boolean r0 = dumpDurationSteps(r15, r0, r1, r2, r3)
            r7 = 1000(0x3e8, double:4.94E-321)
            if (r0 == 0) goto L_0x01a7
            long r0 = android.os.SystemClock.elapsedRealtime()
            long r0 = r0 * r7
            long r9 = r14.computeBatteryTimeRemaining(r0)
            r0 = 0
            int r2 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r2 < 0) goto L_0x0173
            java.lang.String r0 = "  Estimated discharge time remaining: "
            r15.print(r0)
            long r0 = r9 / r7
            android.util.TimeUtils.formatDuration((long) r0, (java.io.PrintWriter) r15)
            r29.println()
        L_0x0173:
            android.os.BatteryStats$LevelStepTracker r0 = r27.getDischargeLevelStepTracker()
            r1 = 0
        L_0x0178:
            r11 = r1
            int[] r1 = STEP_LEVEL_MODES_OF_INTEREST
            int r1 = r1.length
            if (r11 >= r1) goto L_0x01a4
            java.lang.String r2 = "  Estimated "
            java.lang.String[] r1 = STEP_LEVEL_MODE_LABELS
            r3 = r1[r11]
            java.lang.String r4 = " time: "
            int[] r1 = STEP_LEVEL_MODES_OF_INTEREST
            r1 = r1[r11]
            long r5 = (long) r1
            int[] r1 = STEP_LEVEL_MODE_VALUES
            r1 = r1[r11]
            long r12 = (long) r1
            r26 = 0
            r21 = r0
            r22 = r5
            r24 = r12
            long r5 = r21.computeTimeEstimate(r22, r24, r26)
            r1 = r29
            dumpTimeEstimate(r1, r2, r3, r4, r5)
            int r1 = r11 + 1
            goto L_0x0178
        L_0x01a4:
            r29.println()
        L_0x01a7:
            java.lang.String r0 = "  "
            java.lang.String r1 = "Charge step durations:"
            android.os.BatteryStats$LevelStepTracker r2 = r27.getChargeLevelStepTracker()
            r12 = 0
            boolean r0 = dumpDurationSteps(r15, r0, r1, r2, r12)
            if (r0 == 0) goto L_0x01d6
            long r0 = android.os.SystemClock.elapsedRealtime()
            long r0 = r0 * r7
            long r0 = r14.computeChargeTimeRemaining(r0)
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x01d3
            java.lang.String r2 = "  Estimated charge time remaining: "
            r15.print(r2)
            long r2 = r0 / r7
            android.util.TimeUtils.formatDuration((long) r2, (java.io.PrintWriter) r15)
            r29.println()
        L_0x01d3:
            r29.println()
        L_0x01d6:
            if (r16 == 0) goto L_0x01e0
            r0 = r30 & 4
            if (r0 == 0) goto L_0x01dd
            goto L_0x01e0
        L_0x01dd:
            r4 = r12
            goto L_0x036c
        L_0x01e0:
            java.lang.String r0 = "Daily stats:"
            r15.println(r0)
            java.lang.String r0 = "  Current start time: "
            r15.print(r0)
            java.lang.String r0 = "yyyy-MM-dd-HH-mm-ss"
            long r1 = r27.getCurrentDailyStartTime()
            java.lang.CharSequence r0 = android.text.format.DateFormat.format((java.lang.CharSequence) r0, (long) r1)
            java.lang.String r0 = r0.toString()
            r15.println(r0)
            java.lang.String r0 = "  Next min deadline: "
            r15.print(r0)
            java.lang.String r0 = "yyyy-MM-dd-HH-mm-ss"
            long r1 = r27.getNextMinDailyDeadline()
            java.lang.CharSequence r0 = android.text.format.DateFormat.format((java.lang.CharSequence) r0, (long) r1)
            java.lang.String r0 = r0.toString()
            r15.println(r0)
            java.lang.String r0 = "  Next max deadline: "
            r15.print(r0)
            java.lang.String r0 = "yyyy-MM-dd-HH-mm-ss"
            long r1 = r27.getNextMaxDailyDeadline()
            java.lang.CharSequence r0 = android.text.format.DateFormat.format((java.lang.CharSequence) r0, (long) r1)
            java.lang.String r0 = r0.toString()
            r15.println(r0)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r0 = 64
            r6.<init>(r0)
            r0 = 1
            int[] r13 = new int[r0]
            android.os.BatteryStats$LevelStepTracker r11 = r27.getDailyDischargeLevelStepTracker()
            android.os.BatteryStats$LevelStepTracker r10 = r27.getDailyChargeLevelStepTracker()
            java.util.ArrayList r9 = r27.getDailyPackageChanges()
            int r1 = r11.mNumStepDurations
            if (r1 > 0) goto L_0x0253
            int r1 = r10.mNumStepDurations
            if (r1 > 0) goto L_0x0253
            if (r9 == 0) goto L_0x024b
            goto L_0x0253
        L_0x024b:
            r5 = r0
            r3 = r9
            r1 = r10
            r2 = r11
            r4 = r12
            r0 = r13
            goto L_0x02c3
        L_0x0253:
            r1 = r30 & 4
            if (r1 != 0) goto L_0x0288
            if (r16 != 0) goto L_0x0260
            r5 = r0
            r3 = r9
            r1 = r10
            r2 = r11
            r4 = r12
            r0 = r13
            goto L_0x028e
        L_0x0260:
            java.lang.String r1 = "  Current daily steps:"
            r15.println(r1)
            java.lang.String r3 = "    "
            java.lang.String r4 = "Discharge"
            r1 = r27
            r2 = r29
            r5 = r11
            r7 = r13
            r1.dumpDailyLevelStepSummary(r2, r3, r4, r5, r6, r7)
            java.lang.String r1 = "    "
            java.lang.String r2 = "Charge"
            r7 = r27
            r8 = r29
            r3 = r9
            r9 = r1
            r1 = r10
            r10 = r2
            r2 = r11
            r11 = r1
            r4 = r12
            r12 = r6
            r5 = r0
            r0 = r13
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
            goto L_0x02c3
        L_0x0288:
            r5 = r0
            r3 = r9
            r1 = r10
            r2 = r11
            r4 = r12
            r0 = r13
        L_0x028e:
            java.lang.String r7 = "    "
            java.lang.String r8 = "  Current daily discharge step durations:"
            boolean r7 = dumpDurationSteps(r15, r7, r8, r2, r4)
            if (r7 == 0) goto L_0x02a6
            java.lang.String r9 = "      "
            java.lang.String r10 = "Discharge"
            r7 = r27
            r8 = r29
            r11 = r2
            r12 = r6
            r13 = r0
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
        L_0x02a6:
            java.lang.String r7 = "    "
            java.lang.String r8 = "  Current daily charge step durations:"
            boolean r7 = dumpDurationSteps(r15, r7, r8, r1, r4)
            if (r7 == 0) goto L_0x02be
            java.lang.String r9 = "      "
            java.lang.String r10 = "Charge"
            r7 = r27
            r8 = r29
            r11 = r1
            r12 = r6
            r13 = r0
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
        L_0x02be:
            java.lang.String r7 = "    "
            r14.dumpDailyPackageChanges(r15, r7, r3)
        L_0x02c3:
            r7 = r4
        L_0x02c4:
            android.os.BatteryStats$DailyItem r8 = r14.getDailyItemLocked(r7)
            r13 = r8
            if (r8 == 0) goto L_0x0368
            int r17 = r7 + 1
            r7 = r30 & 4
            if (r7 == 0) goto L_0x02d4
            r29.println()
        L_0x02d4:
            java.lang.String r7 = "  Daily from "
            r15.print(r7)
            java.lang.String r7 = "yyyy-MM-dd-HH-mm-ss"
            long r8 = r13.mStartTime
            java.lang.CharSequence r7 = android.text.format.DateFormat.format((java.lang.CharSequence) r7, (long) r8)
            java.lang.String r7 = r7.toString()
            r15.print(r7)
            java.lang.String r7 = " to "
            r15.print(r7)
            java.lang.String r7 = "yyyy-MM-dd-HH-mm-ss"
            long r8 = r13.mEndTime
            java.lang.CharSequence r7 = android.text.format.DateFormat.format((java.lang.CharSequence) r7, (long) r8)
            java.lang.String r7 = r7.toString()
            r15.print(r7)
            java.lang.String r7 = ":"
            r15.println(r7)
            r7 = r30 & 4
            if (r7 != 0) goto L_0x0325
            if (r16 != 0) goto L_0x030b
            r5 = r13
            goto L_0x0326
        L_0x030b:
            java.lang.String r9 = "    "
            java.lang.String r10 = "Discharge"
            android.os.BatteryStats$LevelStepTracker r11 = r13.mDischargeSteps
            r7 = r27
            r8 = r29
            r12 = r6
            r5 = r13
            r13 = r0
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
            java.lang.String r9 = "    "
            java.lang.String r10 = "Charge"
            android.os.BatteryStats$LevelStepTracker r11 = r5.mChargeSteps
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
            goto L_0x0363
        L_0x0325:
            r5 = r13
        L_0x0326:
            java.lang.String r7 = "      "
            java.lang.String r8 = "    Discharge step durations:"
            android.os.BatteryStats$LevelStepTracker r9 = r5.mDischargeSteps
            boolean r7 = dumpDurationSteps(r15, r7, r8, r9, r4)
            if (r7 == 0) goto L_0x0341
            java.lang.String r9 = "        "
            java.lang.String r10 = "Discharge"
            android.os.BatteryStats$LevelStepTracker r11 = r5.mDischargeSteps
            r7 = r27
            r8 = r29
            r12 = r6
            r13 = r0
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
        L_0x0341:
            java.lang.String r7 = "      "
            java.lang.String r8 = "    Charge step durations:"
            android.os.BatteryStats$LevelStepTracker r9 = r5.mChargeSteps
            boolean r7 = dumpDurationSteps(r15, r7, r8, r9, r4)
            if (r7 == 0) goto L_0x035c
            java.lang.String r9 = "        "
            java.lang.String r10 = "Charge"
            android.os.BatteryStats$LevelStepTracker r11 = r5.mChargeSteps
            r7 = r27
            r8 = r29
            r12 = r6
            r13 = r0
            r7.dumpDailyLevelStepSummary(r8, r9, r10, r11, r12, r13)
        L_0x035c:
            java.lang.String r7 = "    "
            java.util.ArrayList<android.os.BatteryStats$PackageChange> r8 = r5.mPackageChanges
            r14.dumpDailyPackageChanges(r15, r7, r8)
        L_0x0363:
            r7 = r17
            r5 = 1
            goto L_0x02c4
        L_0x0368:
            r5 = r13
            r29.println()
        L_0x036c:
            if (r16 == 0) goto L_0x0372
            r0 = r30 & 2
            if (r0 == 0) goto L_0x03b4
        L_0x0372:
            java.lang.String r0 = "Statistics since last charge:"
            r15.println(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "  System starts: "
            r0.append(r1)
            int r1 = r27.getStartCount()
            r0.append(r1)
            java.lang.String r1 = ", currently on battery: "
            r0.append(r1)
            boolean r1 = r27.getIsOnBattery()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r15.println(r0)
            java.lang.String r0 = ""
            r5 = 0
            r1 = r30 & 64
            if (r1 == 0) goto L_0x03a4
            r7 = 1
            goto L_0x03a5
        L_0x03a4:
            r7 = r4
        L_0x03a5:
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r0
            r6 = r31
            r1.dumpLocked(r2, r3, r4, r5, r6, r7)
            r29.println()
        L_0x03b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpLocked(android.content.Context, java.io.PrintWriter, int, int, long):void");
    }

    public void dumpCheckinLocked(Context context, PrintWriter pw, List<ApplicationInfo> apps, int flags, long histStart) {
        PrintWriter printWriter = pw;
        List<ApplicationInfo> list = apps;
        prepareForDumpLocked();
        boolean z = true;
        dumpLine(printWriter, 0, "i", VERSION_DATA, 34, Integer.valueOf(getParcelVersion()), getStartPlatformVersion(), getEndPlatformVersion());
        long historyBaseTime = getHistoryBaseTime() + SystemClock.elapsedRealtime();
        if ((flags & 24) != 0 && startIteratingHistoryLocked()) {
            int i = 0;
            while (i < getHistoryStringPoolSize()) {
                try {
                    printWriter.print(9);
                    printWriter.print(',');
                    printWriter.print(HISTORY_STRING_POOL);
                    printWriter.print(',');
                    printWriter.print(i);
                    printWriter.print(SmsManager.REGEX_PREFIX_DELIMITER);
                    printWriter.print(getHistoryTagPoolUid(i));
                    printWriter.print(",\"");
                    printWriter.print(getHistoryTagPoolString(i).replace("\\", "\\\\").replace("\"", "\\\""));
                    printWriter.print("\"");
                    pw.println();
                    i++;
                } finally {
                    finishIteratingHistoryLocked();
                }
            }
            dumpHistoryLocked(pw, flags, histStart, true);
        }
        if ((flags & 8) == 0) {
            if (list != null) {
                SparseArray<Pair<ArrayList<String>, MutableBoolean>> uids = new SparseArray<>();
                for (int i2 = 0; i2 < apps.size(); i2++) {
                    ApplicationInfo ai = list.get(i2);
                    Pair<ArrayList<String>, MutableBoolean> pkgs = uids.get(UserHandle.getAppId(ai.uid));
                    if (pkgs == null) {
                        pkgs = new Pair<>(new ArrayList(), new MutableBoolean(false));
                        uids.put(UserHandle.getAppId(ai.uid), pkgs);
                    }
                    ((ArrayList) pkgs.first).add(ai.packageName);
                }
                SparseArray<? extends Uid> uidStats = getUidStats();
                int NU = uidStats.size();
                String[] lineArgs = new String[2];
                int i3 = 0;
                while (i3 < NU) {
                    int uid = UserHandle.getAppId(uidStats.keyAt(i3));
                    Pair<ArrayList<String>, MutableBoolean> pkgs2 = uids.get(uid);
                    if (pkgs2 != null && !((MutableBoolean) pkgs2.second).value) {
                        ((MutableBoolean) pkgs2.second).value = z;
                        int j = 0;
                        while (j < ((ArrayList) pkgs2.first).size()) {
                            lineArgs[0] = Integer.toString(uid);
                            lineArgs[1] = (String) ((ArrayList) pkgs2.first).get(j);
                            dumpLine(printWriter, 0, "i", "uid", (Object[]) lineArgs);
                            j++;
                            uids = uids;
                            uidStats = uidStats;
                        }
                    }
                    i3++;
                    uids = uids;
                    uidStats = uidStats;
                    z = true;
                }
            }
            if ((flags & 4) == 0) {
                dumpDurationSteps(printWriter, "", DISCHARGE_STEP_DATA, getDischargeLevelStepTracker(), true);
                String[] lineArgs2 = new String[1];
                long timeRemaining = computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (timeRemaining >= 0) {
                    lineArgs2[0] = Long.toString(timeRemaining);
                    dumpLine(printWriter, 0, "i", DISCHARGE_TIME_REMAIN_DATA, (Object[]) lineArgs2);
                }
                dumpDurationSteps(printWriter, "", CHARGE_STEP_DATA, getChargeLevelStepTracker(), true);
                long timeRemaining2 = computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (timeRemaining2 >= 0) {
                    lineArgs2[0] = Long.toString(timeRemaining2);
                    dumpLine(printWriter, 0, "i", CHARGE_TIME_REMAIN_DATA, (Object[]) lineArgs2);
                }
                dumpCheckinLocked(context, pw, 0, -1, (flags & 64) != 0);
            }
        }
    }

    public void dumpProtoLocked(Context context, FileDescriptor fd, List<ApplicationInfo> apps, int flags, long histStart) {
        ProtoOutputStream proto = new ProtoOutputStream(fd);
        prepareForDumpLocked();
        if ((flags & 24) != 0) {
            dumpProtoHistoryLocked(proto, flags, histStart);
            proto.flush();
            return;
        }
        long bToken = proto.start(1146756268033L);
        proto.write(1120986464257L, 34);
        proto.write(1112396529666L, getParcelVersion());
        proto.write(1138166333443L, getStartPlatformVersion());
        proto.write(1138166333444L, getEndPlatformVersion());
        if ((flags & 4) == 0) {
            BatteryStatsHelper helper = new BatteryStatsHelper(context, false, (flags & 64) != 0);
            helper.create(this);
            helper.refreshStats(0, -1);
            dumpProtoAppsLocked(proto, helper, apps);
            dumpProtoSystemLocked(proto, helper);
        }
        proto.end(bToken);
        proto.flush();
    }

    private void dumpProtoAppsLocked(ProtoOutputStream proto, BatteryStatsHelper helper, List<ApplicationInfo> apps) {
        ArrayList<String> pkgs;
        ArrayList<String> pkgs2;
        long rawRealtimeMs;
        int n;
        int n2;
        SparseArray<? extends Uid.Sensor> sensors;
        int uid;
        BatterySipper bs;
        int[] reasons;
        long cpuToken;
        ArrayMap<String, ? extends Uid.Pkg> packageStats;
        long[] cpuFreqTimeMs;
        int i;
        int i2;
        Uid u;
        long rawRealtimeMs2;
        long rawRealtimeUs;
        long batteryUptimeUs;
        SparseArray<ArrayList<String>> aidToPackages;
        long rawUptimeUs;
        SparseArray<? extends Uid> uidStats;
        int which;
        boolean z;
        ArrayList<String> pkgs3;
        Uid u2;
        long rawRealtimeMs3;
        ArrayList<String> pkgs4;
        ProtoOutputStream protoOutputStream = proto;
        List<ApplicationInfo> list = apps;
        int which2 = false;
        long rawUptimeUs2 = SystemClock.uptimeMillis() * 1000;
        long rawRealtimeMs4 = SystemClock.elapsedRealtime();
        long rawRealtimeUs2 = rawRealtimeMs4 * 1000;
        long batteryUptimeUs2 = getBatteryUptime(rawUptimeUs2);
        SparseArray<ArrayList<String>> aidToPackages2 = new SparseArray<>();
        if (list != null) {
            int i3 = 0;
            while (i3 < apps.size()) {
                ApplicationInfo ai = list.get(i3);
                int aid = UserHandle.getAppId(ai.uid);
                ArrayList<String> pkgs5 = aidToPackages2.get(aid);
                if (pkgs5 == null) {
                    pkgs4 = new ArrayList<>();
                    aidToPackages2.put(aid, pkgs4);
                } else {
                    pkgs4 = pkgs5;
                }
                int i4 = aid;
                pkgs4.add(ai.packageName);
                i3++;
            }
        }
        SparseArray<BatterySipper> uidToSipper = new SparseArray<>();
        List<BatterySipper> sippers = helper.getUsageList();
        if (sippers != null) {
            int i5 = 0;
            while (i5 < sippers.size()) {
                BatterySipper bs2 = sippers.get(i5);
                List<BatterySipper> sippers2 = sippers;
                if (bs2.drainType == BatterySipper.DrainType.APP) {
                    uidToSipper.put(bs2.uidObj.getUid(), bs2);
                }
                i5++;
                sippers = sippers2;
                List<ApplicationInfo> list2 = apps;
            }
        }
        List<BatterySipper> sippers3 = sippers;
        SparseArray<? extends Uid> uidStats2 = getUidStats();
        int n3 = uidStats2.size();
        int iu = 0;
        while (true) {
            int iu2 = iu;
            if (iu2 < n3) {
                int n4 = n3;
                long uTkn = protoOutputStream.start(2246267895813L);
                SparseArray<BatterySipper> uidToSipper2 = uidToSipper;
                Uid u3 = (Uid) uidStats2.valueAt(iu2);
                long uTkn2 = uTkn;
                int uid2 = uidStats2.keyAt(iu2);
                int iu3 = iu2;
                protoOutputStream.write(1120986464257L, uid2);
                SparseArray<ArrayList<String>> aidToPackages3 = aidToPackages2;
                ArrayList<String> pkgs6 = aidToPackages3.get(UserHandle.getAppId(uid2));
                if (pkgs6 == null) {
                    pkgs = new ArrayList<>();
                } else {
                    pkgs = pkgs6;
                }
                ArrayMap<String, ? extends Uid.Pkg> packageStats2 = u3.getPackageStats();
                int uid3 = uid2;
                int ipkg = packageStats2.size() - 1;
                while (true) {
                    pkgs2 = pkgs;
                    int ipkg2 = ipkg;
                    if (ipkg2 < 0) {
                        break;
                    }
                    String pkg = packageStats2.keyAt(ipkg2);
                    ArrayMap<String, ? extends Uid.Pkg> packageStats3 = packageStats2;
                    ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = ((Uid.Pkg) packageStats2.valueAt(ipkg2)).getServiceStats();
                    if (serviceStats.size() == 0) {
                        aidToPackages = aidToPackages3;
                        batteryUptimeUs = batteryUptimeUs2;
                        u = u3;
                        uidStats = uidStats2;
                        which = which2;
                        rawUptimeUs = rawUptimeUs2;
                        rawRealtimeMs2 = rawRealtimeMs4;
                        rawRealtimeUs = rawRealtimeUs2;
                        pkgs3 = pkgs2;
                        z = true;
                    } else {
                        uidStats = uidStats2;
                        which = which2;
                        rawUptimeUs = rawUptimeUs2;
                        long pToken = protoOutputStream.start(2246267895810L);
                        protoOutputStream.write(1138166333441L, pkg);
                        pkgs3 = pkgs2;
                        pkgs3.remove(pkg);
                        z = true;
                        int isvc = serviceStats.size() - 1;
                        while (isvc >= 0) {
                            String pkg2 = pkg;
                            Uid.Pkg.Serv ss = (Uid.Pkg.Serv) serviceStats.valueAt(isvc);
                            SparseArray<ArrayList<String>> aidToPackages4 = aidToPackages3;
                            long batteryUptimeUs3 = batteryUptimeUs2;
                            long batteryUptimeUs4 = roundUsToMs(ss.getStartTime(batteryUptimeUs2, 0));
                            long rawRealtimeUs3 = rawRealtimeUs2;
                            int starts = ss.getStarts(0);
                            int launches = ss.getLaunches(0);
                            if (batteryUptimeUs4 == 0 && starts == 0 && launches == 0) {
                                u2 = u3;
                                rawRealtimeMs3 = rawRealtimeMs4;
                            } else {
                                rawRealtimeMs3 = rawRealtimeMs4;
                                long rawRealtimeMs5 = protoOutputStream.start(2246267895810L);
                                protoOutputStream.write(1138166333441L, serviceStats.keyAt(isvc));
                                u2 = u3;
                                protoOutputStream.write(1112396529666L, batteryUptimeUs4);
                                protoOutputStream.write(1120986464259L, starts);
                                protoOutputStream.write(1120986464260L, launches);
                                protoOutputStream.end(rawRealtimeMs5);
                            }
                            isvc--;
                            pkg = pkg2;
                            aidToPackages3 = aidToPackages4;
                            batteryUptimeUs2 = batteryUptimeUs3;
                            rawRealtimeUs2 = rawRealtimeUs3;
                            rawRealtimeMs4 = rawRealtimeMs3;
                            u3 = u2;
                        }
                        aidToPackages = aidToPackages3;
                        batteryUptimeUs = batteryUptimeUs2;
                        u = u3;
                        rawRealtimeMs2 = rawRealtimeMs4;
                        rawRealtimeUs = rawRealtimeUs2;
                        protoOutputStream.end(pToken);
                    }
                    ipkg = ipkg2 - 1;
                    pkgs = pkgs3;
                    int ipkg3 = z;
                    packageStats2 = packageStats3;
                    which2 = which;
                    uidStats2 = uidStats;
                    rawUptimeUs2 = rawUptimeUs;
                    aidToPackages3 = aidToPackages;
                    batteryUptimeUs2 = batteryUptimeUs;
                    rawRealtimeUs2 = rawRealtimeUs;
                    rawRealtimeMs4 = rawRealtimeMs2;
                    u3 = u;
                }
                ArrayMap<String, ? extends Uid.Pkg> packageStats4 = packageStats2;
                SparseArray<ArrayList<String>> aidToPackages5 = aidToPackages3;
                long batteryUptimeUs5 = batteryUptimeUs2;
                Uid u4 = u3;
                SparseArray<? extends Uid> uidStats3 = uidStats2;
                int which3 = which2;
                long rawUptimeUs3 = rawUptimeUs2;
                long rawRealtimeMs6 = rawRealtimeMs4;
                long rawRealtimeUs4 = rawRealtimeUs2;
                ArrayList<String> pkgs7 = pkgs2;
                Iterator<String> it = pkgs7.iterator();
                while (it.hasNext()) {
                    long pToken2 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1138166333441L, it.next());
                    protoOutputStream.end(pToken2);
                }
                if (u4.getAggregatedPartialWakelockTimer() != null) {
                    Timer timer = u4.getAggregatedPartialWakelockTimer();
                    rawRealtimeMs = rawRealtimeMs6;
                    long totTimeMs = timer.getTotalDurationMsLocked(rawRealtimeMs);
                    Timer bgTimer = timer.getSubTimer();
                    long bgTimeMs = bgTimer != null ? bgTimer.getTotalDurationMsLocked(rawRealtimeMs) : 0;
                    long awToken = protoOutputStream.start(1146756268056L);
                    protoOutputStream.write(1112396529665L, totTimeMs);
                    protoOutputStream.write(1112396529666L, bgTimeMs);
                    protoOutputStream.end(awToken);
                } else {
                    rawRealtimeMs = rawRealtimeMs6;
                }
                long uTkn3 = uTkn2;
                ArrayMap<String, ? extends Uid.Pkg> packageStats5 = packageStats4;
                ArrayList<String> arrayList = pkgs7;
                int n5 = n4;
                int iu4 = iu3;
                SparseArray<ArrayList<String>> aidToPackages6 = aidToPackages5;
                int uid4 = uid3;
                long batteryUptimeUs6 = batteryUptimeUs5;
                List<BatterySipper> sippers4 = sippers3;
                SparseArray<BatterySipper> uidToSipper3 = uidToSipper2;
                Uid u5 = u4;
                dumpTimer(proto, 1146756268040L, u4.getAudioTurnedOnTimer(), rawRealtimeUs4, 0);
                dumpControllerActivityProto(protoOutputStream, 1146756268035L, u5.getBluetoothControllerActivity(), 0);
                Timer bleTimer = u5.getBluetoothScanTimer();
                if (bleTimer != null) {
                    ProtoOutputStream protoOutputStream2 = proto;
                    long bmToken = protoOutputStream.start(1146756268038L);
                    long bmToken2 = rawRealtimeUs4;
                    n = n5;
                    n2 = 0;
                    dumpTimer(protoOutputStream2, 1146756268033L, bleTimer, bmToken2, 0);
                    dumpTimer(protoOutputStream2, 1146756268034L, u5.getBluetoothScanBackgroundTimer(), bmToken2, 0);
                    dumpTimer(protoOutputStream2, 1146756268035L, u5.getBluetoothUnoptimizedScanTimer(), bmToken2, 0);
                    dumpTimer(protoOutputStream2, 1146756268036L, u5.getBluetoothUnoptimizedScanBackgroundTimer(), bmToken2, 0);
                    if (u5.getBluetoothScanResultCounter() != null) {
                        i = u5.getBluetoothScanResultCounter().getCountLocked(0);
                    } else {
                        i = 0;
                    }
                    protoOutputStream.write(1120986464261L, i);
                    if (u5.getBluetoothScanResultBgCounter() != null) {
                        i2 = u5.getBluetoothScanResultBgCounter().getCountLocked(0);
                    } else {
                        i2 = 0;
                    }
                    protoOutputStream.write(1120986464262L, i2);
                    protoOutputStream.end(bmToken);
                } else {
                    n = n5;
                    n2 = 0;
                }
                dumpTimer(proto, 1146756268041L, u5.getCameraTurnedOnTimer(), rawRealtimeUs4, 0);
                long cpuToken2 = protoOutputStream.start(1146756268039L);
                Uid u6 = u5;
                protoOutputStream.write(1112396529665L, roundUsToMs(u6.getUserCpuTimeUs(n2)));
                protoOutputStream.write(1112396529666L, roundUsToMs(u6.getSystemCpuTimeUs(n2)));
                long[] cpuFreqs = getCpuFreqs();
                if (!(cpuFreqs == null || (cpuFreqTimeMs = u6.getCpuFreqTimes(n2)) == null || cpuFreqTimeMs.length != cpuFreqs.length)) {
                    long[] screenOffCpuFreqTimeMs = u6.getScreenOffCpuFreqTimes(n2);
                    if (screenOffCpuFreqTimeMs == null) {
                        screenOffCpuFreqTimeMs = new long[cpuFreqTimeMs.length];
                    }
                    int ic = n2;
                    while (ic < cpuFreqTimeMs.length) {
                        long rawRealtimeMs7 = rawRealtimeMs;
                        long cToken = protoOutputStream.start(2246267895811L);
                        protoOutputStream.write(1120986464257L, ic + 1);
                        protoOutputStream.write(1112396529666L, cpuFreqTimeMs[ic]);
                        protoOutputStream.write(1112396529667L, screenOffCpuFreqTimeMs[ic]);
                        protoOutputStream.end(cToken);
                        ic++;
                        rawRealtimeMs = rawRealtimeMs7;
                        uTkn3 = uTkn3;
                        iu4 = iu4;
                    }
                }
                long uTkn4 = uTkn3;
                int iu5 = iu4;
                long rawRealtimeMs8 = rawRealtimeMs;
                int procState = 0;
                while (procState < 7) {
                    long[] timesMs = u6.getCpuFreqTimes(0, procState);
                    if (timesMs == null || timesMs.length != cpuFreqs.length) {
                        cpuToken = cpuToken2;
                        packageStats = packageStats5;
                    } else {
                        long[] screenOffTimesMs = u6.getScreenOffCpuFreqTimes(0, procState);
                        if (screenOffTimesMs == null) {
                            screenOffTimesMs = new long[timesMs.length];
                        }
                        long procToken = protoOutputStream.start(2246267895812L);
                        protoOutputStream.write(1159641169921L, procState);
                        int ic2 = 0;
                        while (ic2 < timesMs.length) {
                            long cToken2 = protoOutputStream.start(2246267895810L);
                            protoOutputStream.write(1120986464257L, ic2 + 1);
                            protoOutputStream.write(1112396529666L, timesMs[ic2]);
                            protoOutputStream.write(1112396529667L, screenOffTimesMs[ic2]);
                            protoOutputStream.end(cToken2);
                            ic2++;
                            packageStats5 = packageStats5;
                            cpuToken2 = cpuToken2;
                        }
                        cpuToken = cpuToken2;
                        packageStats = packageStats5;
                        protoOutputStream.end(procToken);
                    }
                    procState++;
                    packageStats5 = packageStats;
                    cpuToken2 = cpuToken;
                }
                ArrayMap<String, ? extends Uid.Pkg> packageStats6 = packageStats5;
                long j = 2246267895810L;
                protoOutputStream.end(cpuToken2);
                ProtoOutputStream protoOutputStream3 = proto;
                long[] cpuFreqs2 = cpuFreqs;
                long j2 = cpuToken2;
                long cpuToken3 = rawRealtimeUs4;
                Uid u7 = u6;
                dumpTimer(protoOutputStream3, 1146756268042L, u6.getFlashlightTurnedOnTimer(), cpuToken3, 0);
                dumpTimer(protoOutputStream3, 1146756268043L, u7.getForegroundActivityTimer(), cpuToken3, 0);
                dumpTimer(protoOutputStream3, 1146756268044L, u7.getForegroundServiceTimer(), cpuToken3, 0);
                ArrayMap<String, SparseIntArray> completions = u7.getJobCompletionStats();
                int[] reasons2 = {0, 1, 2, 3, 4};
                int ic3 = 0;
                while (ic3 < completions.size()) {
                    SparseIntArray types = completions.valueAt(ic3);
                    if (types != null) {
                        long jcToken = protoOutputStream.start(2246267895824L);
                        protoOutputStream.write(1138166333441L, completions.keyAt(ic3));
                        int length = reasons2.length;
                        int i6 = 0;
                        while (i6 < length) {
                            int r = reasons2[i6];
                            int[] reasons3 = reasons2;
                            long rToken = protoOutputStream.start(j);
                            protoOutputStream.write(1159641169921L, r);
                            int i7 = r;
                            protoOutputStream.write(1120986464258L, types.get(r, 0));
                            protoOutputStream.end(rToken);
                            i6++;
                            reasons2 = reasons3;
                            length = length;
                            j = 2246267895810L;
                        }
                        reasons = reasons2;
                        protoOutputStream.end(jcToken);
                    } else {
                        reasons = reasons2;
                    }
                    ic3++;
                    reasons2 = reasons;
                    j = 2246267895810L;
                }
                int[] reasons4 = reasons2;
                ArrayMap<String, ? extends Timer> jobs = u7.getJobStats();
                int ij = jobs.size() - 1;
                while (true) {
                    int ij2 = ij;
                    if (ij2 < 0) {
                        break;
                    }
                    Timer timer2 = (Timer) jobs.valueAt(ij2);
                    Timer bgTimer2 = timer2.getSubTimer();
                    long jToken = protoOutputStream.start(2246267895823L);
                    protoOutputStream.write(1138166333441L, jobs.keyAt(ij2));
                    ProtoOutputStream protoOutputStream4 = proto;
                    long jToken2 = jToken;
                    int[] iArr = reasons4;
                    long jToken3 = rawRealtimeUs4;
                    dumpTimer(protoOutputStream4, 1146756268034L, timer2, jToken3, 0);
                    dumpTimer(protoOutputStream4, 1146756268035L, bgTimer2, jToken3, 0);
                    protoOutputStream.end(jToken2);
                    ij = ij2 - 1;
                    completions = completions;
                }
                int[] iArr2 = reasons4;
                dumpControllerActivityProto(protoOutputStream, 1146756268036L, u7.getModemControllerActivity(), 0);
                long nToken = protoOutputStream.start(1146756268049L);
                protoOutputStream.write(1112396529665L, u7.getNetworkActivityBytes(0, 0));
                protoOutputStream.write(1112396529666L, u7.getNetworkActivityBytes(1, 0));
                protoOutputStream.write(1112396529667L, u7.getNetworkActivityBytes(2, 0));
                protoOutputStream.write(1112396529668L, u7.getNetworkActivityBytes(3, 0));
                protoOutputStream.write(1112396529669L, u7.getNetworkActivityBytes(4, 0));
                protoOutputStream.write(1112396529670L, u7.getNetworkActivityBytes(5, 0));
                protoOutputStream.write(1112396529671L, u7.getNetworkActivityPackets(0, 0));
                protoOutputStream.write(1112396529672L, u7.getNetworkActivityPackets(1, 0));
                protoOutputStream.write(1112396529673L, u7.getNetworkActivityPackets(2, 0));
                protoOutputStream.write(1112396529674L, u7.getNetworkActivityPackets(3, 0));
                protoOutputStream.write(1112396529675L, roundUsToMs(u7.getMobileRadioActiveTime(0)));
                protoOutputStream.write(1120986464268L, u7.getMobileRadioActiveCount(0));
                protoOutputStream.write(1120986464269L, u7.getMobileRadioApWakeupCount(0));
                protoOutputStream.write(1120986464270L, u7.getWifiRadioApWakeupCount(0));
                protoOutputStream.write(1112396529679L, u7.getNetworkActivityBytes(6, 0));
                protoOutputStream.write(1112396529680L, u7.getNetworkActivityBytes(7, 0));
                protoOutputStream.write(1112396529681L, u7.getNetworkActivityBytes(8, 0));
                protoOutputStream.write(1112396529682L, u7.getNetworkActivityBytes(9, 0));
                protoOutputStream.write(1112396529683L, u7.getNetworkActivityPackets(6, 0));
                protoOutputStream.write(1112396529684L, u7.getNetworkActivityPackets(7, 0));
                protoOutputStream.write(1112396529685L, u7.getNetworkActivityPackets(8, 0));
                protoOutputStream.write(1112396529686L, u7.getNetworkActivityPackets(9, 0));
                protoOutputStream.end(nToken);
                int uid5 = uid4;
                BatterySipper bs3 = uidToSipper3.get(uid5);
                if (bs3 != null) {
                    long bsToken = protoOutputStream.start(1146756268050L);
                    long j3 = nToken;
                    protoOutputStream.write(1103806595073L, bs3.totalPowerMah);
                    protoOutputStream.write(1133871366146L, bs3.shouldHide);
                    protoOutputStream.write(1103806595075L, bs3.screenPowerMah);
                    protoOutputStream.write(1103806595076L, bs3.proportionalSmearMah);
                    protoOutputStream.end(bsToken);
                }
                ArrayMap<String, ? extends Uid.Proc> processStats = u7.getProcessStats();
                int ipr = processStats.size() - 1;
                while (ipr >= 0) {
                    Uid.Proc ps = (Uid.Proc) processStats.valueAt(ipr);
                    long prToken = protoOutputStream.start(2246267895827L);
                    protoOutputStream.write(1138166333441L, processStats.keyAt(ipr));
                    protoOutputStream.write(1112396529666L, ps.getUserTime(0));
                    protoOutputStream.write(1112396529667L, ps.getSystemTime(0));
                    protoOutputStream.write(1112396529668L, ps.getForegroundTime(0));
                    protoOutputStream.write(1120986464261L, ps.getStarts(0));
                    protoOutputStream.write(1120986464262L, ps.getNumAnrs(0));
                    protoOutputStream.write(1120986464263L, ps.getNumCrashes(0));
                    protoOutputStream.end(prToken);
                    ipr--;
                    uid5 = uid5;
                    uidToSipper3 = uidToSipper3;
                    cpuFreqs2 = cpuFreqs2;
                }
                int uid6 = uid5;
                SparseArray<BatterySipper> uidToSipper4 = uidToSipper3;
                long[] jArr = cpuFreqs2;
                SparseArray<? extends Uid.Sensor> sensors2 = u7.getSensorStats();
                int ise = 0;
                while (true) {
                    int ise2 = ise;
                    if (ise2 >= sensors2.size()) {
                        break;
                    }
                    Uid.Sensor se = (Uid.Sensor) sensors2.valueAt(ise2);
                    Timer timer3 = se.getSensorTime();
                    if (timer3 == null) {
                        bs = bs3;
                        uid = uid6;
                    } else {
                        Timer bgTimer3 = se.getSensorBackgroundTime();
                        int sensorNumber = sensors2.keyAt(ise2);
                        protoOutputStream.write(1120986464257L, sensorNumber);
                        ProtoOutputStream protoOutputStream5 = proto;
                        bs = bs3;
                        long j4 = rawRealtimeUs4;
                        int i8 = sensorNumber;
                        uid = uid6;
                        dumpTimer(protoOutputStream5, 1146756268034L, timer3, j4, 0);
                        dumpTimer(protoOutputStream5, 1146756268035L, bgTimer3, j4, 0);
                        protoOutputStream.end(protoOutputStream.start(2246267895829L));
                    }
                    ise = ise2 + 1;
                    bs3 = bs;
                    uid6 = uid;
                }
                int i9 = uid6;
                int ips = 0;
                while (ips < 7) {
                    long rawRealtimeUs5 = rawRealtimeUs4;
                    long durMs = roundUsToMs(u7.getProcessStateTime(ips, rawRealtimeUs5, 0));
                    if (durMs == 0) {
                        sensors = sensors2;
                    } else {
                        long stToken = protoOutputStream.start(2246267895828L);
                        protoOutputStream.write(1159641169921L, ips);
                        sensors = sensors2;
                        protoOutputStream.write(1112396529666L, durMs);
                        protoOutputStream.end(stToken);
                    }
                    ips++;
                    rawRealtimeUs4 = rawRealtimeUs5;
                    sensors2 = sensors;
                }
                long rawRealtimeUs6 = rawRealtimeUs4;
                ArrayMap<String, ? extends Timer> syncs = u7.getSyncStats();
                int isy = syncs.size() - 1;
                while (true) {
                    int isy2 = isy;
                    if (isy2 < 0) {
                        break;
                    }
                    Timer timer4 = (Timer) syncs.valueAt(isy2);
                    Timer bgTimer4 = timer4.getSubTimer();
                    long syToken = protoOutputStream.start(2246267895830L);
                    protoOutputStream.write(1138166333441L, syncs.keyAt(isy2));
                    ProtoOutputStream protoOutputStream6 = proto;
                    int isy3 = isy2;
                    long j5 = rawRealtimeUs6;
                    dumpTimer(protoOutputStream6, 1146756268034L, timer4, j5, 0);
                    dumpTimer(protoOutputStream6, 1146756268035L, bgTimer4, j5, 0);
                    protoOutputStream.end(syToken);
                    isy = isy3 - 1;
                    syncs = syncs;
                }
                ArrayMap<String, ? extends Timer> syncs2 = syncs;
                if (u7.hasUserActivity()) {
                    for (int i10 = 0; i10 < Uid.NUM_USER_ACTIVITY_TYPES; i10++) {
                        int val = u7.getUserActivityCount(i10, 0);
                        if (val != 0) {
                            long uaToken = protoOutputStream.start(2246267895831L);
                            protoOutputStream.write(1159641169921L, i10);
                            protoOutputStream.write(1120986464258L, val);
                            protoOutputStream.end(uaToken);
                        }
                    }
                }
                ProtoOutputStream protoOutputStream7 = proto;
                long j6 = rawRealtimeUs6;
                dumpTimer(protoOutputStream7, 1146756268045L, u7.getVibratorOnTimer(), j6, 0);
                dumpTimer(protoOutputStream7, 1146756268046L, u7.getVideoTurnedOnTimer(), j6, 0);
                ArrayMap<String, ? extends Uid.Wakelock> wakelocks = u7.getWakelockStats();
                int iw = wakelocks.size() - 1;
                while (true) {
                    int iw2 = iw;
                    if (iw2 < 0) {
                        break;
                    }
                    Uid.Wakelock wl = (Uid.Wakelock) wakelocks.valueAt(iw2);
                    long wToken = protoOutputStream.start(2246267895833L);
                    protoOutputStream.write(1138166333441L, wakelocks.keyAt(iw2));
                    long wToken2 = wToken;
                    Uid.Wakelock wl2 = wl;
                    int iw3 = iw2;
                    dumpTimer(proto, 1146756268034L, wl.getWakeTime(1), rawRealtimeUs6, 0);
                    Timer pTimer = wl2.getWakeTime(0);
                    if (pTimer != null) {
                        ProtoOutputStream protoOutputStream8 = proto;
                        long j7 = rawRealtimeUs6;
                        dumpTimer(protoOutputStream8, 1146756268035L, pTimer, j7, 0);
                        dumpTimer(protoOutputStream8, 1146756268036L, pTimer.getSubTimer(), j7, 0);
                    }
                    dumpTimer(proto, 1146756268037L, wl2.getWakeTime(2), rawRealtimeUs6, 0);
                    protoOutputStream.end(wToken2);
                    iw = iw3 - 1;
                }
                dumpTimer(proto, 1146756268060L, u7.getMulticastWakelockStats(), rawRealtimeUs6, 0);
                int ipkg4 = packageStats6.size() - 1;
                while (ipkg4 >= 0) {
                    ArrayMap<String, ? extends Uid.Pkg> packageStats7 = packageStats6;
                    ArrayMap<String, ? extends Counter> alarms = ((Uid.Pkg) packageStats7.valueAt(ipkg4)).getWakeupAlarmStats();
                    int iwa = alarms.size() - 1;
                    while (iwa >= 0) {
                        long waToken = protoOutputStream.start(2246267895834L);
                        protoOutputStream.write(1138166333441L, alarms.keyAt(iwa));
                        protoOutputStream.write(1120986464258L, ((Counter) alarms.valueAt(iwa)).getCountLocked(0));
                        protoOutputStream.end(waToken);
                        iwa--;
                        syncs2 = syncs2;
                        wakelocks = wakelocks;
                    }
                    ipkg4--;
                    packageStats6 = packageStats7;
                    syncs2 = syncs2;
                    wakelocks = wakelocks;
                }
                ArrayMap<String, ? extends Timer> arrayMap = syncs2;
                ArrayMap<String, ? extends Uid.Pkg> arrayMap2 = packageStats6;
                dumpControllerActivityProto(protoOutputStream, 1146756268037L, u7.getWifiControllerActivity(), 0);
                long wToken3 = protoOutputStream.start(1146756268059L);
                protoOutputStream.write(1112396529665L, roundUsToMs(u7.getFullWifiLockTime(rawRealtimeUs6, 0)));
                long j8 = rawRealtimeUs6;
                dumpTimer(proto, 1146756268035L, u7.getWifiScanTimer(), j8, 0);
                protoOutputStream.write(1112396529666L, roundUsToMs(u7.getWifiRunningTime(rawRealtimeUs6, 0)));
                dumpTimer(proto, 1146756268036L, u7.getWifiScanBackgroundTimer(), j8, 0);
                protoOutputStream.end(wToken3);
                protoOutputStream.end(uTkn4);
                iu = iu5 + 1;
                rawRealtimeUs2 = rawRealtimeUs6;
                aidToPackages2 = aidToPackages6;
                batteryUptimeUs2 = batteryUptimeUs6;
                which2 = which3;
                uidStats2 = uidStats3;
                rawUptimeUs2 = rawUptimeUs3;
                sippers3 = sippers4;
                n3 = n;
                rawRealtimeMs4 = rawRealtimeMs8;
                uidToSipper = uidToSipper4;
            } else {
                SparseArray<ArrayList<String>> sparseArray = aidToPackages2;
                SparseArray<BatterySipper> sparseArray2 = uidToSipper;
                SparseArray<? extends Uid> sparseArray3 = uidStats2;
                int i11 = which2;
                long j9 = rawUptimeUs2;
                long j10 = rawRealtimeMs4;
                long j11 = rawRealtimeUs2;
                List<BatterySipper> list3 = sippers3;
                long j12 = batteryUptimeUs2;
                return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f2 A[Catch:{ all -> 0x0237 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01d7 A[Catch:{ all -> 0x0237 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void dumpProtoHistoryLocked(android.util.proto.ProtoOutputStream r27, int r28, long r29) {
        /*
            r26 = this;
            r1 = r26
            r9 = r27
            boolean r0 = r26.startIteratingHistoryLocked()
            if (r0 != 0) goto L_0x000b
            return
        L_0x000b:
            r0 = 34
            r2 = 1120986464257(0x10500000001, double:5.538409014424E-312)
            r9.write((long) r2, (int) r0)
            r4 = 1112396529666(0x10300000002, double:5.49596909861E-312)
            int r0 = r26.getParcelVersion()
            r9.write((long) r4, (int) r0)
            java.lang.String r0 = r26.getStartPlatformVersion()
            r4 = 1138166333443(0x10900000003, double:5.623288846073E-312)
            r9.write((long) r4, (java.lang.String) r0)
            r6 = 1138166333444(0x10900000004, double:5.62328884608E-312)
            java.lang.String r0 = r26.getEndPlatformVersion()
            r9.write((long) r6, (java.lang.String) r0)
            r6 = 0
        L_0x003b:
            int r7 = r26.getHistoryStringPoolSize()     // Catch:{ all -> 0x0237 }
            if (r6 >= r7) goto L_0x0067
            r7 = 2246267895813(0x20b00000005, double:1.1098037986773E-311)
            long r7 = r9.start(r7)     // Catch:{ all -> 0x0237 }
            r9.write((long) r2, (int) r6)     // Catch:{ all -> 0x0237 }
            r10 = 1120986464258(0x10500000002, double:5.53840901443E-312)
            int r12 = r1.getHistoryTagPoolUid(r6)     // Catch:{ all -> 0x0237 }
            r9.write((long) r10, (int) r12)     // Catch:{ all -> 0x0237 }
            java.lang.String r10 = r1.getHistoryTagPoolString(r6)     // Catch:{ all -> 0x0237 }
            r9.write((long) r4, (java.lang.String) r10)     // Catch:{ all -> 0x0237 }
            r9.end(r7)     // Catch:{ all -> 0x0237 }
            int r6 = r6 + 1
            goto L_0x003b
        L_0x0067:
            android.os.BatteryStats$HistoryPrinter r2 = new android.os.BatteryStats$HistoryPrinter     // Catch:{ all -> 0x0237 }
            r2.<init>()     // Catch:{ all -> 0x0237 }
            android.os.BatteryStats$HistoryItem r3 = new android.os.BatteryStats$HistoryItem     // Catch:{ all -> 0x0237 }
            r3.<init>()     // Catch:{ all -> 0x0237 }
            r10 = r3
            r3 = -1
            r5 = -1
            r7 = 0
            r12 = r5
            r4 = r3
            r3 = 0
        L_0x007a:
            r8 = r3
            boolean r3 = r1.getNextHistoryLocked(r10)     // Catch:{ all -> 0x0237 }
            r14 = 0
            if (r3 == 0) goto L_0x020a
            long r0 = r10.time     // Catch:{ all -> 0x0237 }
            int r3 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r3 >= 0) goto L_0x008b
            r3 = r0
            r12 = r3
        L_0x008b:
            long r3 = r10.time     // Catch:{ all -> 0x0237 }
            int r3 = (r3 > r29 ? 1 : (r3 == r29 ? 0 : -1))
            if (r3 < 0) goto L_0x01fe
            int r3 = (r29 > r14 ? 1 : (r29 == r14 ? 0 : -1))
            r17 = 1
            if (r3 < 0) goto L_0x01dd
            if (r7 != 0) goto L_0x01dd
            byte r3 = r10.cmd     // Catch:{ all -> 0x0237 }
            r4 = 5
            if (r3 == r4) goto L_0x00d8
            byte r3 = r10.cmd     // Catch:{ all -> 0x0237 }
            r5 = 7
            if (r3 == r5) goto L_0x00d8
            byte r3 = r10.cmd     // Catch:{ all -> 0x0237 }
            r5 = 4
            if (r3 == r5) goto L_0x00d8
            byte r3 = r10.cmd     // Catch:{ all -> 0x0237 }
            r5 = 8
            if (r3 != r5) goto L_0x00af
            goto L_0x00d8
        L_0x00af:
            long r5 = r10.currentTime     // Catch:{ all -> 0x0237 }
            int r3 = (r5 > r14 ? 1 : (r5 == r14 ? 0 : -1))
            if (r3 == 0) goto L_0x00d3
            r14 = 1
            byte r3 = r10.cmd     // Catch:{ all -> 0x0237 }
            r7 = r3
            r10.cmd = r4     // Catch:{ all -> 0x0237 }
            r3 = r28 & 32
            if (r3 == 0) goto L_0x00c2
            r15 = r17
            goto L_0x00c3
        L_0x00c2:
            r15 = 0
        L_0x00c3:
            r3 = r27
            r4 = r10
            r5 = r12
            r11 = r7
            r7 = r15
            r2.printNextItem((android.util.proto.ProtoOutputStream) r3, (android.os.BatteryStats.HistoryItem) r4, (long) r5, (boolean) r7)     // Catch:{ all -> 0x0237 }
            r10.cmd = r11     // Catch:{ all -> 0x0237 }
            r18 = r0
            r0 = r8
            r11 = r14
            goto L_0x00f0
        L_0x00d3:
            r18 = r0
            r11 = r7
            r0 = r8
            goto L_0x00f0
        L_0x00d8:
            r11 = 1
            r3 = r28 & 32
            if (r3 == 0) goto L_0x00e0
            r14 = r17
            goto L_0x00e1
        L_0x00e0:
            r14 = 0
        L_0x00e1:
            r3 = r2
            r4 = r27
            r5 = r10
            r6 = r12
            r18 = r0
            r0 = r8
            r8 = r14
            r3.printNextItem((android.util.proto.ProtoOutputStream) r4, (android.os.BatteryStats.HistoryItem) r5, (long) r6, (boolean) r8)     // Catch:{ all -> 0x0237 }
            r1 = 0
            r10.cmd = r1     // Catch:{ all -> 0x0237 }
        L_0x00f0:
            if (r0 == 0) goto L_0x01d7
            byte r1 = r10.cmd     // Catch:{ all -> 0x0237 }
            if (r1 == 0) goto L_0x010a
            r1 = r28 & 32
            if (r1 == 0) goto L_0x00fd
            r8 = r17
            goto L_0x00fe
        L_0x00fd:
            r8 = 0
        L_0x00fe:
            r3 = r2
            r4 = r27
            r5 = r10
            r6 = r12
            r3.printNextItem((android.util.proto.ProtoOutputStream) r4, (android.os.BatteryStats.HistoryItem) r5, (long) r6, (boolean) r8)     // Catch:{ all -> 0x0237 }
            r1 = 0
            r10.cmd = r1     // Catch:{ all -> 0x0237 }
            goto L_0x010b
        L_0x010a:
            r1 = 0
        L_0x010b:
            int r3 = r10.eventCode     // Catch:{ all -> 0x0237 }
            r8 = r3
            android.os.BatteryStats$HistoryTag r3 = r10.eventTag     // Catch:{ all -> 0x0237 }
            r6 = r3
            android.os.BatteryStats$HistoryTag r3 = new android.os.BatteryStats$HistoryTag     // Catch:{ all -> 0x0237 }
            r3.<init>()     // Catch:{ all -> 0x0237 }
            r10.eventTag = r3     // Catch:{ all -> 0x0237 }
            r3 = r1
        L_0x0119:
            r7 = r3
            r3 = 22
            if (r7 >= r3) goto L_0x01c8
            java.util.HashMap r3 = r0.getStateForEvent(r7)     // Catch:{ all -> 0x0237 }
            r14 = r3
            if (r14 != 0) goto L_0x0132
            r22 = r0
            r0 = r6
            r23 = r7
            r24 = r11
            r1 = 0
            r11 = r8
            goto L_0x01bd
        L_0x0132:
            java.util.Set r3 = r14.entrySet()     // Catch:{ all -> 0x0237 }
            java.util.Iterator r15 = r3.iterator()     // Catch:{ all -> 0x0237 }
        L_0x013a:
            boolean r3 = r15.hasNext()     // Catch:{ all -> 0x0237 }
            if (r3 == 0) goto L_0x01b4
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0237 }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x0237 }
            r16 = r3
            java.lang.Object r3 = r16.getValue()     // Catch:{ all -> 0x0237 }
            android.util.SparseIntArray r3 = (android.util.SparseIntArray) r3     // Catch:{ all -> 0x0237 }
            r5 = r3
            r3 = r1
        L_0x0150:
            r4 = r3
            int r3 = r5.size()     // Catch:{ all -> 0x0237 }
            if (r4 >= r3) goto L_0x01a5
            r10.eventCode = r7     // Catch:{ all -> 0x0237 }
            android.os.BatteryStats$HistoryTag r3 = r10.eventTag     // Catch:{ all -> 0x0237 }
            java.lang.Object r20 = r16.getKey()     // Catch:{ all -> 0x0237 }
            r1 = r20
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0237 }
            r3.string = r1     // Catch:{ all -> 0x0237 }
            android.os.BatteryStats$HistoryTag r1 = r10.eventTag     // Catch:{ all -> 0x0237 }
            int r3 = r5.keyAt(r4)     // Catch:{ all -> 0x0237 }
            r1.uid = r3     // Catch:{ all -> 0x0237 }
            android.os.BatteryStats$HistoryTag r1 = r10.eventTag     // Catch:{ all -> 0x0237 }
            int r3 = r5.valueAt(r4)     // Catch:{ all -> 0x0237 }
            r1.poolIdx = r3     // Catch:{ all -> 0x0237 }
            r1 = r28 & 32
            if (r1 == 0) goto L_0x017c
            r1 = r17
            goto L_0x017d
        L_0x017c:
            r1 = 0
        L_0x017d:
            r3 = r2
            r20 = r4
            r4 = r27
            r21 = r5
            r5 = r10
            r22 = r0
            r0 = r6
            r23 = r7
            r6 = r12
            r24 = r11
            r11 = r8
            r8 = r1
            r3.printNextItem((android.util.proto.ProtoOutputStream) r4, (android.os.BatteryStats.HistoryItem) r5, (long) r6, (boolean) r8)     // Catch:{ all -> 0x0237 }
            r1 = 0
            r10.wakeReasonTag = r1     // Catch:{ all -> 0x0237 }
            r10.wakelockTag = r1     // Catch:{ all -> 0x0237 }
            int r3 = r20 + 1
            r6 = r0
            r8 = r11
            r5 = r21
            r0 = r22
            r7 = r23
            r11 = r24
            r1 = 0
            goto L_0x0150
        L_0x01a5:
            r22 = r0
            r0 = r6
            r23 = r7
            r24 = r11
            r1 = 0
            r11 = r8
            r0 = r22
            r11 = r24
            r1 = 0
            goto L_0x013a
        L_0x01b4:
            r22 = r0
            r0 = r6
            r23 = r7
            r24 = r11
            r1 = 0
            r11 = r8
        L_0x01bd:
            int r3 = r23 + 1
            r6 = r0
            r8 = r11
            r0 = r22
            r11 = r24
            r1 = 0
            goto L_0x0119
        L_0x01c8:
            r22 = r0
            r0 = r6
            r24 = r11
            r1 = 0
            r11 = r8
            r10.eventCode = r11     // Catch:{ all -> 0x0237 }
            r10.eventTag = r0     // Catch:{ all -> 0x0237 }
            r0 = 0
            r22 = r0
            goto L_0x01e4
        L_0x01d7:
            r22 = r0
            r24 = r11
            r1 = 0
            goto L_0x01e4
        L_0x01dd:
            r18 = r0
            r22 = r8
            r1 = 0
            r24 = r7
        L_0x01e4:
            r0 = r28 & 32
            if (r0 == 0) goto L_0x01eb
            r8 = r17
            goto L_0x01ec
        L_0x01eb:
            r8 = 0
        L_0x01ec:
            r3 = r2
            r4 = r27
            r5 = r10
            r6 = r12
            r3.printNextItem((android.util.proto.ProtoOutputStream) r4, (android.os.BatteryStats.HistoryItem) r5, (long) r6, (boolean) r8)     // Catch:{ all -> 0x0237 }
            r4 = r18
            r3 = r22
            r7 = r24
            r1 = r26
            goto L_0x007a
        L_0x01fe:
            r18 = r0
            r22 = r8
            r4 = r18
            r3 = r22
            r1 = r26
            goto L_0x007a
        L_0x020a:
            r22 = r8
            int r0 = (r29 > r14 ? 1 : (r29 == r14 ? 0 : -1))
            if (r0 < 0) goto L_0x0232
            r26.commitCurrentHistoryBatchLocked()     // Catch:{ all -> 0x0237 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0237 }
            r3.<init>()     // Catch:{ all -> 0x0237 }
            java.lang.String r6 = "NEXT: "
            r3.append(r6)     // Catch:{ all -> 0x0237 }
            r14 = 1
            long r0 = r4 + r14
            r3.append(r0)     // Catch:{ all -> 0x0237 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0237 }
            r25 = r2
            r1 = 2237677961222(0x20900000006, double:1.105559807096E-311)
            r9.write((long) r1, (java.lang.String) r0)     // Catch:{ all -> 0x0237 }
        L_0x0232:
            r26.finishIteratingHistoryLocked()
            return
        L_0x0237:
            r0 = move-exception
            r26.finishIteratingHistoryLocked()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpProtoHistoryLocked(android.util.proto.ProtoOutputStream, int, long):void");
    }

    private void dumpProtoSystemLocked(ProtoOutputStream proto, BatteryStatsHelper helper) {
        long rawRealtimeUs;
        int i;
        long pdcToken;
        ProtoOutputStream protoOutputStream = proto;
        long sToken = protoOutputStream.start(1146756268038L);
        long rawUptimeUs = SystemClock.uptimeMillis() * 1000;
        long rawRealtimeUs2 = SystemClock.elapsedRealtime() * 1000;
        long bToken = protoOutputStream.start(1146756268033L);
        long sToken2 = sToken;
        protoOutputStream.write(1112396529665L, getStartClockTime());
        protoOutputStream.write(1112396529666L, getStartCount());
        protoOutputStream.write(1112396529667L, computeRealtime(rawRealtimeUs2, 0) / 1000);
        protoOutputStream.write(1112396529668L, computeUptime(rawUptimeUs, 0) / 1000);
        protoOutputStream.write(1112396529669L, computeBatteryRealtime(rawRealtimeUs2, 0) / 1000);
        protoOutputStream.write(1112396529670L, computeBatteryUptime(rawUptimeUs, 0) / 1000);
        protoOutputStream.write(1112396529671L, computeBatteryScreenOffRealtime(rawRealtimeUs2, 0) / 1000);
        protoOutputStream.write(1112396529672L, computeBatteryScreenOffUptime(rawUptimeUs, 0) / 1000);
        protoOutputStream.write(1112396529673L, getScreenDozeTime(rawRealtimeUs2, 0) / 1000);
        protoOutputStream.write(1112396529674L, getEstimatedBatteryCapacity());
        protoOutputStream.write(1112396529675L, getMinLearnedBatteryCapacity());
        protoOutputStream.write(1112396529676L, getMaxLearnedBatteryCapacity());
        protoOutputStream.end(bToken);
        long bdToken = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, getLowDischargeAmountSinceCharge());
        protoOutputStream.write(1120986464258L, getHighDischargeAmountSinceCharge());
        protoOutputStream.write(1120986464259L, getDischargeAmountScreenOnSinceCharge());
        protoOutputStream.write(1120986464260L, getDischargeAmountScreenOffSinceCharge());
        protoOutputStream.write(1120986464261L, getDischargeAmountScreenDozeSinceCharge());
        long j = bToken;
        protoOutputStream.write(1112396529670L, getUahDischarge(0) / 1000);
        protoOutputStream.write(1112396529671L, getUahDischargeScreenOff(0) / 1000);
        protoOutputStream.write(1112396529672L, getUahDischargeScreenDoze(0) / 1000);
        protoOutputStream.write(1112396529673L, getUahDischargeLightDoze(0) / 1000);
        protoOutputStream.write(1112396529674L, getUahDischargeDeepDoze(0) / 1000);
        protoOutputStream.end(bdToken);
        long timeRemainingUs = computeChargeTimeRemaining(rawRealtimeUs2);
        if (timeRemainingUs >= 0) {
            protoOutputStream.write(1112396529667L, timeRemainingUs / 1000);
        } else {
            timeRemainingUs = computeBatteryTimeRemaining(rawRealtimeUs2);
            if (timeRemainingUs >= 0) {
                protoOutputStream.write(1112396529668L, timeRemainingUs / 1000);
            } else {
                protoOutputStream.write(1112396529668L, -1);
            }
        }
        long timeRemainingUs2 = timeRemainingUs;
        dumpDurationSteps(protoOutputStream, 2246267895813L, getChargeLevelStepTracker());
        int i2 = 0;
        while (true) {
            int i3 = i2;
            boolean isNone = true;
            if (i3 >= 22) {
                break;
            }
            if (i3 != 0) {
                isNone = false;
            }
            int telephonyNetworkType = i3;
            if (i3 == 21) {
                telephonyNetworkType = 0;
            }
            int telephonyNetworkType2 = telephonyNetworkType;
            long rawRealtimeUs3 = rawRealtimeUs2;
            long pdcToken2 = protoOutputStream.start(2246267895816L);
            if (isNone) {
                pdcToken = pdcToken2;
                protoOutputStream.write(1133871366146L, isNone);
            } else {
                pdcToken = pdcToken2;
                protoOutputStream.write(1159641169921L, telephonyNetworkType2);
            }
            int i4 = telephonyNetworkType2;
            rawRealtimeUs2 = rawRealtimeUs3;
            boolean z = isNone;
            dumpTimer(proto, 1146756268035L, getPhoneDataConnectionTimer(i3), rawRealtimeUs2, 0);
            protoOutputStream.end(pdcToken);
            i2 = i3 + 1;
            bdToken = bdToken;
        }
        long rawRealtimeUs4 = rawRealtimeUs2;
        long j2 = bdToken;
        dumpDurationSteps(protoOutputStream, 2246267895814L, getDischargeLevelStepTracker());
        long[] cpuFreqs = getCpuFreqs();
        if (cpuFreqs != null) {
            for (long i5 : cpuFreqs) {
                protoOutputStream.write((long) SystemProto.CPU_FREQUENCY, i5);
            }
        }
        dumpControllerActivityProto(protoOutputStream, 1146756268041L, getBluetoothControllerActivity(), 0);
        dumpControllerActivityProto(protoOutputStream, 1146756268042L, getModemControllerActivity(), 0);
        long gnToken = protoOutputStream.start(1146756268044L);
        protoOutputStream.write(1112396529665L, getNetworkActivityBytes(0, 0));
        protoOutputStream.write(1112396529666L, getNetworkActivityBytes(1, 0));
        protoOutputStream.write(1112396529669L, getNetworkActivityPackets(0, 0));
        protoOutputStream.write(1112396529670L, getNetworkActivityPackets(1, 0));
        protoOutputStream.write(1112396529667L, getNetworkActivityBytes(2, 0));
        protoOutputStream.write(1112396529668L, getNetworkActivityBytes(3, 0));
        protoOutputStream.write(1112396529671L, getNetworkActivityPackets(2, 0));
        protoOutputStream.write(1112396529672L, getNetworkActivityPackets(3, 0));
        protoOutputStream.write(1112396529673L, getNetworkActivityBytes(4, 0));
        protoOutputStream.write(1112396529674L, getNetworkActivityBytes(5, 0));
        long gnToken2 = gnToken;
        protoOutputStream.end(gnToken2);
        dumpControllerActivityProto(protoOutputStream, 1146756268043L, getWifiControllerActivity(), 0);
        long gwToken = protoOutputStream.start(1146756268045L);
        long rawRealtimeUs5 = rawRealtimeUs4;
        long[] jArr = cpuFreqs;
        long gnToken3 = gnToken2;
        long j3 = timeRemainingUs2;
        long rawRealtimeUs6 = rawRealtimeUs5;
        ProtoOutputStream protoOutputStream2 = proto;
        protoOutputStream2.write(1112396529665L, getWifiOnTime(rawRealtimeUs5, 0) / 1000);
        protoOutputStream2.write(1112396529666L, getGlobalWifiRunningTime(rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.end(gwToken);
        Map<String, ? extends Timer> kernelWakelocks = getKernelWakelockStats();
        for (Map.Entry<String, ? extends Timer> ent : kernelWakelocks.entrySet()) {
            long kwToken = protoOutputStream2.start(2246267895822L);
            protoOutputStream2.write(1138166333441L, ent.getKey());
            long j4 = gnToken3;
            dumpTimer(proto, 1146756268034L, (Timer) ent.getValue(), rawRealtimeUs6, 0);
            protoOutputStream2.end(kwToken);
            gwToken = gwToken;
            kernelWakelocks = kernelWakelocks;
        }
        Map<String, ? extends Timer> map = kernelWakelocks;
        long j5 = gnToken3;
        int i6 = 1;
        SparseArray<? extends Uid> uidStats = getUidStats();
        long fullWakeLockTimeTotalUs = 0;
        long partialWakeLockTimeTotalUs = 0;
        int iu = 0;
        while (iu < uidStats.size()) {
            ArrayMap<String, ? extends Uid.Wakelock> wakelocks = ((Uid) uidStats.valueAt(iu)).getWakelockStats();
            int iw = wakelocks.size() - i6;
            while (iw >= 0) {
                Uid.Wakelock wl = (Uid.Wakelock) wakelocks.valueAt(iw);
                Timer fullWakeTimer = wl.getWakeTime(i6);
                if (fullWakeTimer != null) {
                    i = 0;
                    fullWakeLockTimeTotalUs += fullWakeTimer.getTotalTimeLocked(rawRealtimeUs6, 0);
                } else {
                    i = 0;
                }
                Timer partialWakeTimer = wl.getWakeTime(i);
                if (partialWakeTimer != null) {
                    partialWakeLockTimeTotalUs += partialWakeTimer.getTotalTimeLocked(rawRealtimeUs6, i);
                }
                iw--;
                i6 = 1;
            }
            iu++;
            i6 = 1;
        }
        long mToken = protoOutputStream2.start(1146756268047L);
        SparseArray<? extends Uid> sparseArray = uidStats;
        protoOutputStream2.write(1112396529665L, getScreenOnTime(rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1112396529666L, getPhoneOnTime(rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1112396529667L, fullWakeLockTimeTotalUs / 1000);
        protoOutputStream2.write(1112396529668L, partialWakeLockTimeTotalUs / 1000);
        protoOutputStream2.write(1112396529669L, getMobileRadioActiveTime(rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1112396529670L, getMobileRadioActiveAdjustedTime(0) / 1000);
        protoOutputStream2.write(1120986464263L, getMobileRadioActiveCount(0));
        protoOutputStream2.write(1120986464264L, getMobileRadioActiveUnknownTime(0) / 1000);
        protoOutputStream2.write(1112396529673L, getInteractiveTime(rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1112396529674L, getPowerSaveModeEnabledTime(rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1120986464267L, getNumConnectivityChange(0));
        protoOutputStream2.write(1112396529676L, getDeviceIdleModeTime(2, rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1120986464269L, getDeviceIdleModeCount(2, 0));
        protoOutputStream2.write(1112396529678L, getDeviceIdlingTime(2, rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1120986464271L, getDeviceIdlingCount(2, 0));
        protoOutputStream2.write(1112396529680L, getLongestDeviceIdleModeTime(2));
        protoOutputStream2.write(1112396529681L, getDeviceIdleModeTime(1, rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1120986464274L, getDeviceIdleModeCount(1, 0));
        protoOutputStream2.write(1112396529683L, getDeviceIdlingTime(1, rawRealtimeUs6, 0) / 1000);
        protoOutputStream2.write(1120986464276L, getDeviceIdlingCount(1, 0));
        protoOutputStream2.write(1112396529685L, getLongestDeviceIdleModeTime(1));
        protoOutputStream2.end(mToken);
        long multicastWakeLockTimeTotalUs = getWifiMulticastWakelockTime(rawRealtimeUs6, 0);
        int multicastWakeLockCountTotal = getWifiMulticastWakelockCount(0);
        long wmctToken = protoOutputStream2.start(1146756268055L);
        long mToken2 = mToken;
        long j6 = multicastWakeLockTimeTotalUs;
        protoOutputStream2.write(1112396529665L, multicastWakeLockTimeTotalUs / 1000);
        protoOutputStream2.write(1120986464258L, multicastWakeLockCountTotal);
        protoOutputStream2.end(wmctToken);
        List<BatterySipper> sippers = helper.getUsageList();
        if (sippers != null) {
            int i7 = 0;
            while (i7 < sippers.size()) {
                BatterySipper bs = sippers.get(i7);
                int n = 0;
                int uid = 0;
                long wmctToken2 = wmctToken;
                switch (bs.drainType) {
                    case AMBIENT_DISPLAY:
                        n = 13;
                        break;
                    case IDLE:
                        n = 1;
                        break;
                    case CELL:
                        n = 2;
                        break;
                    case PHONE:
                        n = 3;
                        break;
                    case WIFI:
                        n = 4;
                        break;
                    case BLUETOOTH:
                        n = 5;
                        break;
                    case SCREEN:
                        n = 7;
                        break;
                    case FLASHLIGHT:
                        n = 6;
                        break;
                    case APP:
                        rawRealtimeUs = rawRealtimeUs6;
                        continue;
                    case USER:
                        n = 8;
                        uid = UserHandle.getUid(bs.userId, 0);
                        break;
                    case UNACCOUNTED:
                        n = 9;
                        break;
                    case OVERCOUNTED:
                        n = 10;
                        break;
                    case CAMERA:
                        n = 11;
                        break;
                    case MEMORY:
                        n = 12;
                        break;
                }
                long puiToken = protoOutputStream2.start(2246267895825L);
                rawRealtimeUs = rawRealtimeUs6;
                protoOutputStream2.write(1159641169921L, n);
                protoOutputStream2.write(1120986464258L, uid);
                int i8 = n;
                int i9 = uid;
                protoOutputStream2.write(1103806595075L, bs.totalPowerMah);
                protoOutputStream2.write(1133871366148L, bs.shouldHide);
                protoOutputStream2.write(1103806595077L, bs.screenPowerMah);
                protoOutputStream2.write(1103806595078L, bs.proportionalSmearMah);
                protoOutputStream2.end(puiToken);
                i7++;
                wmctToken = wmctToken2;
                rawRealtimeUs6 = rawRealtimeUs;
            }
        }
        long wmctToken3 = wmctToken;
        long rawRealtimeUs7 = rawRealtimeUs6;
        long pusToken = protoOutputStream2.start(1146756268050L);
        protoOutputStream2.write(1103806595073L, helper.getPowerProfile().getBatteryCapacity());
        protoOutputStream2.write(1103806595074L, helper.getComputedPower());
        protoOutputStream2.write(1103806595075L, helper.getMinDrainedPower());
        protoOutputStream2.write(1103806595076L, helper.getMaxDrainedPower());
        protoOutputStream2.end(pusToken);
        Map<String, ? extends Timer> rpmStats = getRpmStats();
        Map<String, ? extends Timer> screenOffRpmStats = getScreenOffRpmStats();
        for (Map.Entry<String, ? extends Timer> ent2 : rpmStats.entrySet()) {
            protoOutputStream2.write(1138166333441L, ent2.getKey());
            long j7 = wmctToken3;
            Map<String, ? extends Timer> screenOffRpmStats2 = screenOffRpmStats;
            long j8 = mToken2;
            long j9 = rawRealtimeUs7;
            int multicastWakeLockCountTotal2 = multicastWakeLockCountTotal;
            dumpTimer(proto, 1146756268034L, (Timer) ent2.getValue(), j9, 0);
            Map<String, ? extends Timer> screenOffRpmStats3 = screenOffRpmStats2;
            Map<String, ? extends Timer> screenOffRpmStats4 = screenOffRpmStats3;
            dumpTimer(proto, 1146756268035L, (Timer) screenOffRpmStats3.get(ent2.getKey()), j9, 0);
            protoOutputStream2.end(protoOutputStream2.start(2246267895827L));
            multicastWakeLockCountTotal = multicastWakeLockCountTotal2;
            screenOffRpmStats = screenOffRpmStats4;
        }
        int i10 = multicastWakeLockCountTotal;
        long j10 = mToken2;
        long j11 = wmctToken3;
        int i11 = 0;
        while (true) {
            int i12 = i11;
            if (i12 < 5) {
                long sbToken = protoOutputStream2.start(2246267895828L);
                protoOutputStream2.write(1159641169921L, i12);
                dumpTimer(proto, 1146756268034L, getScreenBrightnessTimer(i12), rawRealtimeUs7, 0);
                protoOutputStream2.end(sbToken);
                i11 = i12 + 1;
            } else {
                dumpTimer(proto, 1146756268053L, getPhoneSignalScanningTimer(), rawRealtimeUs7, 0);
                int i13 = 0;
                while (true) {
                    int i14 = i13;
                    if (i14 < 5) {
                        long pssToken = protoOutputStream2.start(2246267895824L);
                        protoOutputStream2.write(1159641169921L, i14);
                        dumpTimer(proto, 1146756268034L, getPhoneSignalStrengthTimer(i14), rawRealtimeUs7, 0);
                        protoOutputStream2.end(pssToken);
                        i13 = i14 + 1;
                    } else {
                        for (Map.Entry<String, ? extends Timer> ent3 : getWakeupReasonStats().entrySet()) {
                            long wrToken = protoOutputStream2.start(2246267895830L);
                            protoOutputStream2.write(1138166333441L, ent3.getKey());
                            dumpTimer(proto, 1146756268034L, (Timer) ent3.getValue(), rawRealtimeUs7, 0);
                            protoOutputStream2.end(wrToken);
                        }
                        int i15 = 0;
                        while (true) {
                            int i16 = i15;
                            if (i16 < 5) {
                                long wssToken = protoOutputStream2.start(2246267895832L);
                                protoOutputStream2.write(1159641169921L, i16);
                                dumpTimer(proto, 1146756268034L, getWifiSignalStrengthTimer(i16), rawRealtimeUs7, 0);
                                protoOutputStream2.end(wssToken);
                                i15 = i16 + 1;
                            } else {
                                int i17 = 0;
                                while (true) {
                                    int i18 = i17;
                                    if (i18 < 8) {
                                        long wsToken = protoOutputStream2.start(2246267895833L);
                                        protoOutputStream2.write(1159641169921L, i18);
                                        dumpTimer(proto, 1146756268034L, getWifiStateTimer(i18), rawRealtimeUs7, 0);
                                        protoOutputStream2.end(wsToken);
                                        i17 = i18 + 1;
                                    } else {
                                        int i19 = 0;
                                        while (true) {
                                            int i20 = i19;
                                            if (i20 < 13) {
                                                long wssToken2 = protoOutputStream2.start(2246267895834L);
                                                protoOutputStream2.write(1159641169921L, i20);
                                                dumpTimer(proto, 1146756268034L, getWifiSupplStateTimer(i20), rawRealtimeUs7, 0);
                                                protoOutputStream2.end(wssToken2);
                                                i19 = i20 + 1;
                                            } else {
                                                protoOutputStream2.end(sToken2);
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
