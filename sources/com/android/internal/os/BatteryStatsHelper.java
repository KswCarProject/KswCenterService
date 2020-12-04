package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.BatteryStats;
import android.os.Bundle;
import android.os.MemoryFile;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseLongArray;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IBatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class BatteryStatsHelper {
    static final boolean DEBUG = false;
    private static final String TAG = BatteryStatsHelper.class.getSimpleName();
    private static Intent sBatteryBroadcastXfer;
    private static ArrayMap<File, BatteryStats> sFileXfer = new ArrayMap<>();
    private static BatteryStats sStatsXfer;
    private Intent mBatteryBroadcast;
    @UnsupportedAppUsage
    private IBatteryStats mBatteryInfo;
    long mBatteryRealtimeUs;
    long mBatteryTimeRemainingUs;
    long mBatteryUptimeUs;
    PowerCalculator mBluetoothPowerCalculator;
    private final List<BatterySipper> mBluetoothSippers;
    PowerCalculator mCameraPowerCalculator;
    long mChargeTimeRemainingUs;
    private final boolean mCollectBatteryBroadcast;
    private double mComputedPower;
    private final Context mContext;
    PowerCalculator mCpuPowerCalculator;
    PowerCalculator mFlashlightPowerCalculator;
    boolean mHasBluetoothPowerReporting;
    boolean mHasWifiPowerReporting;
    private double mMaxDrainedPower;
    private double mMaxPower;
    private double mMaxRealPower;
    PowerCalculator mMediaPowerCalculator;
    PowerCalculator mMemoryPowerCalculator;
    private double mMinDrainedPower;
    MobileRadioPowerCalculator mMobileRadioPowerCalculator;
    private final List<BatterySipper> mMobilemsppList;
    private PackageManager mPackageManager;
    @UnsupportedAppUsage
    private PowerProfile mPowerProfile;
    long mRawRealtimeUs;
    long mRawUptimeUs;
    PowerCalculator mSensorPowerCalculator;
    private String[] mServicepackageArray;
    private BatteryStats mStats;
    private long mStatsPeriod;
    private int mStatsType;
    private String[] mSystemPackageArray;
    private double mTotalPower;
    long mTypeBatteryRealtimeUs;
    long mTypeBatteryUptimeUs;
    @UnsupportedAppUsage
    private final List<BatterySipper> mUsageList;
    private final SparseArray<List<BatterySipper>> mUserSippers;
    PowerCalculator mWakelockPowerCalculator;
    private final boolean mWifiOnly;
    PowerCalculator mWifiPowerCalculator;
    private final List<BatterySipper> mWifiSippers;

    public static boolean checkWifiOnly(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm == null) {
            return false;
        }
        return !cm.isNetworkSupported(0);
    }

    public static boolean checkHasWifiPowerReporting(BatteryStats stats, PowerProfile profile) {
        return (!stats.hasWifiActivityReporting() || profile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_IDLE) == 0.0d || profile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_RX) == 0.0d || profile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_TX) == 0.0d) ? false : true;
    }

    public static boolean checkHasBluetoothPowerReporting(BatteryStats stats, PowerProfile profile) {
        return (!stats.hasBluetoothActivityReporting() || profile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_IDLE) == 0.0d || profile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_RX) == 0.0d || profile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_TX) == 0.0d) ? false : true;
    }

    @UnsupportedAppUsage
    public BatteryStatsHelper(Context context) {
        this(context, true);
    }

    @UnsupportedAppUsage
    public BatteryStatsHelper(Context context, boolean collectBatteryBroadcast) {
        this(context, collectBatteryBroadcast, checkWifiOnly(context));
    }

    @UnsupportedAppUsage
    public BatteryStatsHelper(Context context, boolean collectBatteryBroadcast, boolean wifiOnly) {
        this.mUsageList = new ArrayList();
        this.mWifiSippers = new ArrayList();
        this.mBluetoothSippers = new ArrayList();
        this.mUserSippers = new SparseArray<>();
        this.mMobilemsppList = new ArrayList();
        this.mStatsType = 0;
        this.mStatsPeriod = 0;
        this.mMaxPower = 1.0d;
        this.mMaxRealPower = 1.0d;
        this.mHasWifiPowerReporting = false;
        this.mHasBluetoothPowerReporting = false;
        this.mContext = context;
        this.mCollectBatteryBroadcast = collectBatteryBroadcast;
        this.mWifiOnly = wifiOnly;
        this.mPackageManager = context.getPackageManager();
        Resources resources = context.getResources();
        this.mSystemPackageArray = resources.getStringArray(R.array.config_batteryPackageTypeSystem);
        this.mServicepackageArray = resources.getStringArray(R.array.config_batteryPackageTypeService);
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:17:0x0043=Splitter:B:17:0x0043, B:24:0x004c=Splitter:B:24:0x004c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void storeStatsHistoryInFile(java.lang.String r7) {
        /*
            r6 = this;
            android.util.ArrayMap<java.io.File, android.os.BatteryStats> r0 = sFileXfer
            monitor-enter(r0)
            android.content.Context r1 = r6.mContext     // Catch:{ all -> 0x004d }
            java.io.File r1 = makeFilePath(r1, r7)     // Catch:{ all -> 0x004d }
            android.util.ArrayMap<java.io.File, android.os.BatteryStats> r2 = sFileXfer     // Catch:{ all -> 0x004d }
            android.os.BatteryStats r3 = r6.getStats()     // Catch:{ all -> 0x004d }
            r2.put(r1, r3)     // Catch:{ all -> 0x004d }
            r2 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0033 }
            r3.<init>(r1)     // Catch:{ IOException -> 0x0033 }
            r2 = r3
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch:{ IOException -> 0x0033 }
            android.os.BatteryStats r4 = r6.getStats()     // Catch:{ IOException -> 0x0033 }
            r5 = 0
            r4.writeToParcelWithoutUids(r3, r5)     // Catch:{ IOException -> 0x0033 }
            byte[] r4 = r3.marshall()     // Catch:{ IOException -> 0x0033 }
            r2.write(r4)     // Catch:{ IOException -> 0x0033 }
            r2.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x0040
        L_0x0031:
            r3 = move-exception
            goto L_0x0045
        L_0x0033:
            r3 = move-exception
            java.lang.String r4 = TAG     // Catch:{ all -> 0x0031 }
            java.lang.String r5 = "Unable to write history to file"
            android.util.Log.w(r4, r5, r3)     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x0043
            r2.close()     // Catch:{ IOException -> 0x0041 }
        L_0x0040:
            goto L_0x0043
        L_0x0041:
            r3 = move-exception
            goto L_0x0040
        L_0x0043:
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x0045:
            if (r2 == 0) goto L_0x004c
            r2.close()     // Catch:{ IOException -> 0x004b }
            goto L_0x004c
        L_0x004b:
            r4 = move-exception
        L_0x004c:
            throw r3     // Catch:{ all -> 0x004d }
        L_0x004d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsHelper.storeStatsHistoryInFile(java.lang.String):void");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:32:0x0063=Splitter:B:32:0x0063, B:13:0x0038=Splitter:B:13:0x0038, B:24:0x004c=Splitter:B:24:0x004c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.BatteryStats statsFromFile(android.content.Context r8, java.lang.String r9) {
        /*
            android.util.ArrayMap<java.io.File, android.os.BatteryStats> r0 = sFileXfer
            monitor-enter(r0)
            java.io.File r1 = makeFilePath(r8, r9)     // Catch:{ all -> 0x0064 }
            android.util.ArrayMap<java.io.File, android.os.BatteryStats> r2 = sFileXfer     // Catch:{ all -> 0x0064 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0064 }
            android.os.BatteryStats r2 = (android.os.BatteryStats) r2     // Catch:{ all -> 0x0064 }
            if (r2 == 0) goto L_0x0013
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            return r2
        L_0x0013:
            r3 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x003c }
            r4.<init>(r1)     // Catch:{ IOException -> 0x003c }
            r3 = r4
            byte[] r4 = readFully(r3)     // Catch:{ IOException -> 0x003c }
            android.os.Parcel r5 = android.os.Parcel.obtain()     // Catch:{ IOException -> 0x003c }
            int r6 = r4.length     // Catch:{ IOException -> 0x003c }
            r7 = 0
            r5.unmarshall(r4, r7, r6)     // Catch:{ IOException -> 0x003c }
            r5.setDataPosition(r7)     // Catch:{ IOException -> 0x003c }
            android.os.Parcelable$Creator<com.android.internal.os.BatteryStatsImpl> r6 = com.android.internal.os.BatteryStatsImpl.CREATOR     // Catch:{ IOException -> 0x003c }
            java.lang.Object r6 = r6.createFromParcel(r5)     // Catch:{ IOException -> 0x003c }
            android.os.BatteryStats r6 = (android.os.BatteryStats) r6     // Catch:{ IOException -> 0x003c }
            r3.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x0038
        L_0x0037:
            r7 = move-exception
        L_0x0038:
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            return r6
        L_0x003a:
            r4 = move-exception
            goto L_0x005c
        L_0x003c:
            r4 = move-exception
            java.lang.String r5 = TAG     // Catch:{ all -> 0x003a }
            java.lang.String r6 = "Unable to read history to file"
            android.util.Log.w(r5, r6, r4)     // Catch:{ all -> 0x003a }
            if (r3 == 0) goto L_0x004c
            r3.close()     // Catch:{ IOException -> 0x004a }
        L_0x0049:
            goto L_0x004c
        L_0x004a:
            r4 = move-exception
            goto L_0x0049
        L_0x004c:
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            java.lang.String r0 = "batterystats"
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)
            com.android.internal.app.IBatteryStats r0 = com.android.internal.app.IBatteryStats.Stub.asInterface(r0)
            com.android.internal.os.BatteryStatsImpl r0 = getStats(r0)
            return r0
        L_0x005c:
            if (r3 == 0) goto L_0x0063
            r3.close()     // Catch:{ IOException -> 0x0062 }
            goto L_0x0063
        L_0x0062:
            r5 = move-exception
        L_0x0063:
            throw r4     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BatteryStatsHelper.statsFromFile(android.content.Context, java.lang.String):android.os.BatteryStats");
    }

    @UnsupportedAppUsage
    public static void dropFile(Context context, String fname) {
        makeFilePath(context, fname).delete();
    }

    private static File makeFilePath(Context context, String fname) {
        return new File(context.getFilesDir(), fname);
    }

    @UnsupportedAppUsage
    public void clearStats() {
        this.mStats = null;
    }

    @UnsupportedAppUsage
    public BatteryStats getStats() {
        if (this.mStats == null) {
            load();
        }
        return this.mStats;
    }

    @UnsupportedAppUsage
    public Intent getBatteryBroadcast() {
        if (this.mBatteryBroadcast == null && this.mCollectBatteryBroadcast) {
            load();
        }
        return this.mBatteryBroadcast;
    }

    public PowerProfile getPowerProfile() {
        return this.mPowerProfile;
    }

    public void create(BatteryStats stats) {
        this.mPowerProfile = new PowerProfile(this.mContext);
        this.mStats = stats;
    }

    @UnsupportedAppUsage
    public void create(Bundle icicle) {
        if (icicle != null) {
            this.mStats = sStatsXfer;
            this.mBatteryBroadcast = sBatteryBroadcastXfer;
        }
        this.mBatteryInfo = IBatteryStats.Stub.asInterface(ServiceManager.getService(BatteryStats.SERVICE_NAME));
        this.mPowerProfile = new PowerProfile(this.mContext);
    }

    @UnsupportedAppUsage
    public void storeState() {
        sStatsXfer = this.mStats;
        sBatteryBroadcastXfer = this.mBatteryBroadcast;
    }

    public static String makemAh(double power) {
        String format;
        if (power == 0.0d) {
            return "0";
        }
        if (power < 1.0E-5d) {
            format = "%.8f";
        } else if (power < 1.0E-4d) {
            format = "%.7f";
        } else if (power < 0.001d) {
            format = "%.6f";
        } else if (power < 0.01d) {
            format = "%.5f";
        } else if (power < 0.1d) {
            format = "%.4f";
        } else if (power < 1.0d) {
            format = "%.3f";
        } else if (power < 10.0d) {
            format = "%.2f";
        } else if (power < 100.0d) {
            format = "%.1f";
        } else {
            format = "%.0f";
        }
        return String.format(Locale.ENGLISH, format, new Object[]{Double.valueOf(power)});
    }

    @UnsupportedAppUsage
    public void refreshStats(int statsType, int asUser) {
        SparseArray<UserHandle> users = new SparseArray<>(1);
        users.put(asUser, new UserHandle(asUser));
        refreshStats(statsType, users);
    }

    @UnsupportedAppUsage
    public void refreshStats(int statsType, List<UserHandle> asUsers) {
        int n = asUsers.size();
        SparseArray<UserHandle> users = new SparseArray<>(n);
        for (int i = 0; i < n; i++) {
            UserHandle userHandle = asUsers.get(i);
            users.put(userHandle.getIdentifier(), userHandle);
        }
        refreshStats(statsType, users);
    }

    @UnsupportedAppUsage
    public void refreshStats(int statsType, SparseArray<UserHandle> asUsers) {
        refreshStats(statsType, asUsers, SystemClock.elapsedRealtime() * 1000, SystemClock.uptimeMillis() * 1000);
    }

    public void refreshStats(int statsType, SparseArray<UserHandle> asUsers, long rawRealtimeUs, long rawUptimeUs) {
        int size;
        PowerCalculator powerCalculator;
        BatteryStatsHelper batteryStatsHelper = this;
        int i = statsType;
        long j = rawRealtimeUs;
        long j2 = rawUptimeUs;
        if (i != 0) {
            Log.w(TAG, "refreshStats called for statsType " + i + " but only STATS_SINCE_CHARGED is supported. Using STATS_SINCE_CHARGED instead.");
        }
        getStats();
        batteryStatsHelper.mMaxPower = 0.0d;
        batteryStatsHelper.mMaxRealPower = 0.0d;
        batteryStatsHelper.mComputedPower = 0.0d;
        batteryStatsHelper.mTotalPower = 0.0d;
        batteryStatsHelper.mUsageList.clear();
        batteryStatsHelper.mWifiSippers.clear();
        batteryStatsHelper.mBluetoothSippers.clear();
        batteryStatsHelper.mUserSippers.clear();
        batteryStatsHelper.mMobilemsppList.clear();
        if (batteryStatsHelper.mStats != null) {
            if (batteryStatsHelper.mCpuPowerCalculator == null) {
                batteryStatsHelper.mCpuPowerCalculator = new CpuPowerCalculator(batteryStatsHelper.mPowerProfile);
            }
            batteryStatsHelper.mCpuPowerCalculator.reset();
            if (batteryStatsHelper.mMemoryPowerCalculator == null) {
                batteryStatsHelper.mMemoryPowerCalculator = new MemoryPowerCalculator(batteryStatsHelper.mPowerProfile);
            }
            batteryStatsHelper.mMemoryPowerCalculator.reset();
            if (batteryStatsHelper.mWakelockPowerCalculator == null) {
                batteryStatsHelper.mWakelockPowerCalculator = new WakelockPowerCalculator(batteryStatsHelper.mPowerProfile);
            }
            batteryStatsHelper.mWakelockPowerCalculator.reset();
            if (batteryStatsHelper.mMobileRadioPowerCalculator == null) {
                batteryStatsHelper.mMobileRadioPowerCalculator = new MobileRadioPowerCalculator(batteryStatsHelper.mPowerProfile, batteryStatsHelper.mStats);
            }
            batteryStatsHelper.mMobileRadioPowerCalculator.reset(batteryStatsHelper.mStats);
            boolean hasWifiPowerReporting = checkHasWifiPowerReporting(batteryStatsHelper.mStats, batteryStatsHelper.mPowerProfile);
            if (batteryStatsHelper.mWifiPowerCalculator == null || hasWifiPowerReporting != batteryStatsHelper.mHasWifiPowerReporting) {
                if (hasWifiPowerReporting) {
                    powerCalculator = new WifiPowerCalculator(batteryStatsHelper.mPowerProfile);
                } else {
                    powerCalculator = new WifiPowerEstimator(batteryStatsHelper.mPowerProfile);
                }
                batteryStatsHelper.mWifiPowerCalculator = powerCalculator;
                batteryStatsHelper.mHasWifiPowerReporting = hasWifiPowerReporting;
            }
            batteryStatsHelper.mWifiPowerCalculator.reset();
            boolean hasBluetoothPowerReporting = checkHasBluetoothPowerReporting(batteryStatsHelper.mStats, batteryStatsHelper.mPowerProfile);
            if (batteryStatsHelper.mBluetoothPowerCalculator == null || hasBluetoothPowerReporting != batteryStatsHelper.mHasBluetoothPowerReporting) {
                batteryStatsHelper.mBluetoothPowerCalculator = new BluetoothPowerCalculator(batteryStatsHelper.mPowerProfile);
                batteryStatsHelper.mHasBluetoothPowerReporting = hasBluetoothPowerReporting;
            }
            batteryStatsHelper.mBluetoothPowerCalculator.reset();
            SensorPowerCalculator sensorPowerCalculator = r1;
            boolean z = hasBluetoothPowerReporting;
            SensorPowerCalculator sensorPowerCalculator2 = new SensorPowerCalculator(batteryStatsHelper.mPowerProfile, (SensorManager) batteryStatsHelper.mContext.getSystemService(Context.SENSOR_SERVICE), batteryStatsHelper.mStats, rawRealtimeUs, statsType);
            batteryStatsHelper.mSensorPowerCalculator = sensorPowerCalculator;
            batteryStatsHelper.mSensorPowerCalculator.reset();
            if (batteryStatsHelper.mCameraPowerCalculator == null) {
                batteryStatsHelper.mCameraPowerCalculator = new CameraPowerCalculator(batteryStatsHelper.mPowerProfile);
            }
            batteryStatsHelper.mCameraPowerCalculator.reset();
            if (batteryStatsHelper.mFlashlightPowerCalculator == null) {
                batteryStatsHelper.mFlashlightPowerCalculator = new FlashlightPowerCalculator(batteryStatsHelper.mPowerProfile);
            }
            batteryStatsHelper.mFlashlightPowerCalculator.reset();
            if (batteryStatsHelper.mMediaPowerCalculator == null) {
                batteryStatsHelper.mMediaPowerCalculator = new MediaPowerCalculator(batteryStatsHelper.mPowerProfile);
            }
            batteryStatsHelper.mMediaPowerCalculator.reset();
            batteryStatsHelper.mStatsType = i;
            batteryStatsHelper.mRawUptimeUs = j2;
            batteryStatsHelper.mRawRealtimeUs = j;
            batteryStatsHelper.mBatteryUptimeUs = batteryStatsHelper.mStats.getBatteryUptime(j2);
            batteryStatsHelper.mBatteryRealtimeUs = batteryStatsHelper.mStats.getBatteryRealtime(j);
            batteryStatsHelper.mTypeBatteryUptimeUs = batteryStatsHelper.mStats.computeBatteryUptime(j2, batteryStatsHelper.mStatsType);
            batteryStatsHelper.mTypeBatteryRealtimeUs = batteryStatsHelper.mStats.computeBatteryRealtime(j, batteryStatsHelper.mStatsType);
            batteryStatsHelper.mBatteryTimeRemainingUs = batteryStatsHelper.mStats.computeBatteryTimeRemaining(j);
            batteryStatsHelper.mChargeTimeRemainingUs = batteryStatsHelper.mStats.computeChargeTimeRemaining(j);
            batteryStatsHelper.mMinDrainedPower = (((double) batteryStatsHelper.mStats.getLowDischargeAmountSinceCharge()) * batteryStatsHelper.mPowerProfile.getBatteryCapacity()) / 100.0d;
            batteryStatsHelper.mMaxDrainedPower = (((double) batteryStatsHelper.mStats.getHighDischargeAmountSinceCharge()) * batteryStatsHelper.mPowerProfile.getBatteryCapacity()) / 100.0d;
            batteryStatsHelper.processAppUsage(asUsers);
            for (int i2 = 0; i2 < batteryStatsHelper.mUsageList.size(); i2++) {
                BatterySipper bs = batteryStatsHelper.mUsageList.get(i2);
                bs.computeMobilemspp();
                if (bs.mobilemspp != 0.0d) {
                    batteryStatsHelper.mMobilemsppList.add(bs);
                }
            }
            int i3 = 0;
            while (i3 < batteryStatsHelper.mUserSippers.size()) {
                List<BatterySipper> user = batteryStatsHelper.mUserSippers.valueAt(i3);
                int j3 = 0;
                while (j3 < user.size()) {
                    BatterySipper bs2 = user.get(j3);
                    bs2.computeMobilemspp();
                    int i4 = i3;
                    if (bs2.mobilemspp != 0.0d) {
                        batteryStatsHelper.mMobilemsppList.add(bs2);
                    }
                    j3++;
                    i3 = i4;
                }
                i3++;
            }
            Collections.sort(batteryStatsHelper.mMobilemsppList, new Comparator<BatterySipper>() {
                public int compare(BatterySipper lhs, BatterySipper rhs) {
                    return Double.compare(rhs.mobilemspp, lhs.mobilemspp);
                }
            });
            processMiscUsage();
            Collections.sort(batteryStatsHelper.mUsageList);
            if (!batteryStatsHelper.mUsageList.isEmpty()) {
                double d = batteryStatsHelper.mUsageList.get(0).totalPowerMah;
                batteryStatsHelper.mMaxPower = d;
                batteryStatsHelper.mMaxRealPower = d;
                int usageListCount = batteryStatsHelper.mUsageList.size();
                for (int i5 = 0; i5 < usageListCount; i5++) {
                    batteryStatsHelper.mComputedPower += batteryStatsHelper.mUsageList.get(i5).totalPowerMah;
                }
            }
            batteryStatsHelper.mTotalPower = batteryStatsHelper.mComputedPower;
            if (batteryStatsHelper.mStats.getLowDischargeAmountSinceCharge() > 1) {
                if (batteryStatsHelper.mMinDrainedPower > batteryStatsHelper.mComputedPower) {
                    double amount = batteryStatsHelper.mMinDrainedPower - batteryStatsHelper.mComputedPower;
                    batteryStatsHelper.mTotalPower = batteryStatsHelper.mMinDrainedPower;
                    BatterySipper bs3 = new BatterySipper(BatterySipper.DrainType.UNACCOUNTED, (BatteryStats.Uid) null, amount);
                    int index = Collections.binarySearch(batteryStatsHelper.mUsageList, bs3);
                    if (index < 0) {
                        index = -(index + 1);
                    }
                    batteryStatsHelper.mUsageList.add(index, bs3);
                    batteryStatsHelper.mMaxPower = Math.max(batteryStatsHelper.mMaxPower, amount);
                } else if (batteryStatsHelper.mMaxDrainedPower < batteryStatsHelper.mComputedPower) {
                    double amount2 = batteryStatsHelper.mComputedPower - batteryStatsHelper.mMaxDrainedPower;
                    BatterySipper bs4 = new BatterySipper(BatterySipper.DrainType.OVERCOUNTED, (BatteryStats.Uid) null, amount2);
                    int index2 = Collections.binarySearch(batteryStatsHelper.mUsageList, bs4);
                    if (index2 < 0) {
                        index2 = -(index2 + 1);
                    }
                    batteryStatsHelper.mUsageList.add(index2, bs4);
                    batteryStatsHelper.mMaxPower = Math.max(batteryStatsHelper.mMaxPower, amount2);
                }
            }
            double hiddenPowerMah = batteryStatsHelper.removeHiddenBatterySippers(batteryStatsHelper.mUsageList);
            double totalRemainingPower = getTotalPower() - hiddenPowerMah;
            if (Math.abs(totalRemainingPower) > 0.001d) {
                int i6 = 0;
                int size2 = batteryStatsHelper.mUsageList.size();
                while (i6 < size2) {
                    BatterySipper sipper = batteryStatsHelper.mUsageList.get(i6);
                    if (!sipper.shouldHide) {
                        size = size2;
                        sipper.proportionalSmearMah = ((sipper.totalPowerMah + sipper.screenPowerMah) / totalRemainingPower) * hiddenPowerMah;
                        sipper.sumPower();
                    } else {
                        size = size2;
                    }
                    i6++;
                    size2 = size;
                    batteryStatsHelper = this;
                    SparseArray<UserHandle> sparseArray = asUsers;
                    int i7 = statsType;
                }
            }
        }
    }

    private void processAppUsage(SparseArray<UserHandle> asUsers) {
        BatterySipper app;
        SparseArray<UserHandle> sparseArray = asUsers;
        int iu = 0;
        boolean forAllUsers = sparseArray.get(-1) != null;
        this.mStatsPeriod = this.mTypeBatteryRealtimeUs;
        BatterySipper osSipper = null;
        SparseArray<? extends BatteryStats.Uid> uidStats = this.mStats.getUidStats();
        int NU = uidStats.size();
        while (iu < NU) {
            BatteryStats.Uid u = (BatteryStats.Uid) uidStats.valueAt(iu);
            BatterySipper app2 = new BatterySipper(BatterySipper.DrainType.APP, u, 0.0d);
            SparseArray<? extends BatteryStats.Uid> uidStats2 = uidStats;
            BatteryStats.Uid uid = u;
            BatterySipper app3 = app2;
            this.mCpuPowerCalculator.calculateApp(app2, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            BatterySipper batterySipper = app3;
            this.mWakelockPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mMobileRadioPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mWifiPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mBluetoothPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mSensorPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mCameraPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mFlashlightPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            this.mMediaPowerCalculator.calculateApp(batterySipper, uid, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            if (app3.sumPower() != 0.0d || u.getUid() == 0) {
                int uid2 = app3.getUid();
                int userId = UserHandle.getUserId(uid2);
                if (uid2 == 1010) {
                    app = app3;
                    this.mWifiSippers.add(app);
                } else {
                    app = app3;
                    if (uid2 == 1002) {
                        this.mBluetoothSippers.add(app);
                    } else if (forAllUsers || sparseArray.get(userId) != null || UserHandle.getAppId(uid2) < 10000) {
                        this.mUsageList.add(app);
                    } else {
                        List<BatterySipper> list = this.mUserSippers.get(userId);
                        if (list == null) {
                            list = new ArrayList<>();
                            this.mUserSippers.put(userId, list);
                        }
                        list.add(app);
                    }
                }
                if (uid2 == 0) {
                    osSipper = app;
                }
            }
            iu++;
            uidStats = uidStats2;
        }
        if (osSipper != null) {
            this.mWakelockPowerCalculator.calculateRemaining(osSipper, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            osSipper.sumPower();
        }
    }

    private void addPhoneUsage() {
        long phoneOnTimeMs = this.mStats.getPhoneOnTime(this.mRawRealtimeUs, this.mStatsType) / 1000;
        double phoneOnPower = (this.mPowerProfile.getAveragePower(PowerProfile.POWER_RADIO_ACTIVE) * ((double) phoneOnTimeMs)) / 3600000.0d;
        if (phoneOnPower != 0.0d) {
            addEntry(BatterySipper.DrainType.PHONE, phoneOnTimeMs, phoneOnPower);
        }
    }

    private void addScreenUsage() {
        long j = 1000;
        long screenOnTimeMs = this.mStats.getScreenOnTime(this.mRawRealtimeUs, this.mStatsType) / 1000;
        double power = 0.0d + (((double) screenOnTimeMs) * this.mPowerProfile.getAveragePower(PowerProfile.POWER_SCREEN_ON));
        double screenFullPower = this.mPowerProfile.getAveragePower(PowerProfile.POWER_SCREEN_FULL);
        int i = 0;
        while (i < 5) {
            power += ((double) (this.mStats.getScreenBrightnessTime(i, this.mRawRealtimeUs, this.mStatsType) / j)) * ((((double) (((float) i) + 0.5f)) * screenFullPower) / 5.0d);
            i++;
            j = 1000;
        }
        double power2 = power / 3600000.0d;
        if (power2 != 0.0d) {
            addEntry(BatterySipper.DrainType.SCREEN, screenOnTimeMs, power2);
        }
    }

    private void addAmbientDisplayUsage() {
        long ambientDisplayMs = this.mStats.getScreenDozeTime(this.mRawRealtimeUs, this.mStatsType) / 1000;
        double power = (this.mPowerProfile.getAveragePower(PowerProfile.POWER_AMBIENT_DISPLAY) * ((double) ambientDisplayMs)) / 3600000.0d;
        if (power > 0.0d) {
            addEntry(BatterySipper.DrainType.AMBIENT_DISPLAY, ambientDisplayMs, power);
        }
    }

    private void addRadioUsage() {
        BatterySipper radio = new BatterySipper(BatterySipper.DrainType.CELL, (BatteryStats.Uid) null, 0.0d);
        this.mMobileRadioPowerCalculator.calculateRemaining(radio, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        radio.sumPower();
        if (radio.totalPowerMah > 0.0d) {
            this.mUsageList.add(radio);
        }
    }

    private void aggregateSippers(BatterySipper bs, List<BatterySipper> from, String tag) {
        for (int i = 0; i < from.size(); i++) {
            bs.add(from.get(i));
        }
        bs.computeMobilemspp();
        bs.sumPower();
    }

    private void addIdleUsage() {
        double totalPowerMah = ((((double) (this.mTypeBatteryRealtimeUs / 1000)) * this.mPowerProfile.getAveragePower(PowerProfile.POWER_CPU_SUSPEND)) + (((double) (this.mTypeBatteryUptimeUs / 1000)) * this.mPowerProfile.getAveragePower(PowerProfile.POWER_CPU_IDLE))) / 3600000.0d;
        if (totalPowerMah != 0.0d) {
            addEntry(BatterySipper.DrainType.IDLE, this.mTypeBatteryRealtimeUs / 1000, totalPowerMah);
        }
    }

    private void addWiFiUsage() {
        BatterySipper bs = new BatterySipper(BatterySipper.DrainType.WIFI, (BatteryStats.Uid) null, 0.0d);
        this.mWifiPowerCalculator.calculateRemaining(bs, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        aggregateSippers(bs, this.mWifiSippers, "WIFI");
        if (bs.totalPowerMah > 0.0d) {
            this.mUsageList.add(bs);
        }
    }

    private void addBluetoothUsage() {
        BatterySipper bs = new BatterySipper(BatterySipper.DrainType.BLUETOOTH, (BatteryStats.Uid) null, 0.0d);
        this.mBluetoothPowerCalculator.calculateRemaining(bs, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        aggregateSippers(bs, this.mBluetoothSippers, "Bluetooth");
        if (bs.totalPowerMah > 0.0d) {
            this.mUsageList.add(bs);
        }
    }

    private void addUserUsage() {
        for (int i = 0; i < this.mUserSippers.size(); i++) {
            int userId = this.mUserSippers.keyAt(i);
            BatterySipper bs = new BatterySipper(BatterySipper.DrainType.USER, (BatteryStats.Uid) null, 0.0d);
            bs.userId = userId;
            aggregateSippers(bs, this.mUserSippers.valueAt(i), "User");
            this.mUsageList.add(bs);
        }
    }

    private void addMemoryUsage() {
        BatterySipper memory = new BatterySipper(BatterySipper.DrainType.MEMORY, (BatteryStats.Uid) null, 0.0d);
        this.mMemoryPowerCalculator.calculateRemaining(memory, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        memory.sumPower();
        if (memory.totalPowerMah > 0.0d) {
            this.mUsageList.add(memory);
        }
    }

    private void processMiscUsage() {
        addUserUsage();
        addPhoneUsage();
        addScreenUsage();
        addAmbientDisplayUsage();
        addWiFiUsage();
        addBluetoothUsage();
        addMemoryUsage();
        addIdleUsage();
        if (!this.mWifiOnly) {
            addRadioUsage();
        }
    }

    private BatterySipper addEntry(BatterySipper.DrainType drainType, long time, double power) {
        BatterySipper bs = new BatterySipper(drainType, (BatteryStats.Uid) null, 0.0d);
        bs.usagePowerMah = power;
        bs.usageTimeMs = time;
        bs.sumPower();
        this.mUsageList.add(bs);
        return bs;
    }

    @UnsupportedAppUsage
    public List<BatterySipper> getUsageList() {
        return this.mUsageList;
    }

    public List<BatterySipper> getMobilemsppList() {
        return this.mMobilemsppList;
    }

    public long getStatsPeriod() {
        return this.mStatsPeriod;
    }

    public int getStatsType() {
        return this.mStatsType;
    }

    @UnsupportedAppUsage
    public double getMaxPower() {
        return this.mMaxPower;
    }

    public double getMaxRealPower() {
        return this.mMaxRealPower;
    }

    @UnsupportedAppUsage
    public double getTotalPower() {
        return this.mTotalPower;
    }

    public double getComputedPower() {
        return this.mComputedPower;
    }

    public double getMinDrainedPower() {
        return this.mMinDrainedPower;
    }

    public double getMaxDrainedPower() {
        return this.mMaxDrainedPower;
    }

    public static byte[] readFully(FileInputStream stream) throws IOException {
        return readFully(stream, stream.available());
    }

    public static byte[] readFully(FileInputStream stream, int avail) throws IOException {
        int pos = 0;
        byte[] data = new byte[avail];
        while (true) {
            int amt = stream.read(data, pos, data.length - pos);
            if (amt <= 0) {
                return data;
            }
            pos += amt;
            int avail2 = stream.available();
            if (avail2 > data.length - pos) {
                byte[] newData = new byte[(pos + avail2)];
                System.arraycopy(data, 0, newData, 0, pos);
                data = newData;
            }
        }
    }

    public double removeHiddenBatterySippers(List<BatterySipper> sippers) {
        double proportionalSmearPowerMah = 0.0d;
        BatterySipper screenSipper = null;
        for (int i = sippers.size() - 1; i >= 0; i--) {
            BatterySipper sipper = sippers.get(i);
            sipper.shouldHide = shouldHideSipper(sipper);
            if (!(!sipper.shouldHide || sipper.drainType == BatterySipper.DrainType.OVERCOUNTED || sipper.drainType == BatterySipper.DrainType.SCREEN || sipper.drainType == BatterySipper.DrainType.AMBIENT_DISPLAY || sipper.drainType == BatterySipper.DrainType.UNACCOUNTED || sipper.drainType == BatterySipper.DrainType.BLUETOOTH || sipper.drainType == BatterySipper.DrainType.WIFI || sipper.drainType == BatterySipper.DrainType.IDLE)) {
                proportionalSmearPowerMah += sipper.totalPowerMah;
            }
            if (sipper.drainType == BatterySipper.DrainType.SCREEN) {
                screenSipper = sipper;
            }
        }
        smearScreenBatterySipper(sippers, screenSipper);
        return proportionalSmearPowerMah;
    }

    public void smearScreenBatterySipper(List<BatterySipper> sippers, BatterySipper screenSipper) {
        long totalActivityTimeMs = 0;
        SparseLongArray activityTimeArray = new SparseLongArray();
        int size = sippers.size();
        for (int i = 0; i < size; i++) {
            BatteryStats.Uid uid = sippers.get(i).uidObj;
            if (uid != null) {
                long timeMs = getProcessForegroundTimeMs(uid, 0);
                activityTimeArray.put(uid.getUid(), timeMs);
                totalActivityTimeMs += timeMs;
            }
        }
        if (screenSipper != null && totalActivityTimeMs >= 600000) {
            double screenPowerMah = screenSipper.totalPowerMah;
            int size2 = sippers.size();
            for (int i2 = 0; i2 < size2; i2++) {
                BatterySipper sipper = sippers.get(i2);
                sipper.screenPowerMah = (((double) activityTimeArray.get(sipper.getUid(), 0)) * screenPowerMah) / ((double) totalActivityTimeMs);
            }
        }
    }

    public boolean shouldHideSipper(BatterySipper sipper) {
        BatterySipper.DrainType drainType = sipper.drainType;
        return drainType == BatterySipper.DrainType.IDLE || drainType == BatterySipper.DrainType.CELL || drainType == BatterySipper.DrainType.SCREEN || drainType == BatterySipper.DrainType.AMBIENT_DISPLAY || drainType == BatterySipper.DrainType.UNACCOUNTED || drainType == BatterySipper.DrainType.OVERCOUNTED || isTypeService(sipper) || isTypeSystem(sipper);
    }

    public boolean isTypeService(BatterySipper sipper) {
        String[] packages = this.mPackageManager.getPackagesForUid(sipper.getUid());
        if (packages == null) {
            return false;
        }
        for (String packageName : packages) {
            if (ArrayUtils.contains((T[]) this.mServicepackageArray, packageName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTypeSystem(BatterySipper sipper) {
        int uid = sipper.uidObj == null ? -1 : sipper.getUid();
        sipper.mPackages = this.mPackageManager.getPackagesForUid(uid);
        if (uid >= 0 && uid < 10000) {
            return true;
        }
        if (sipper.mPackages != null) {
            for (String packageName : sipper.mPackages) {
                if (ArrayUtils.contains((T[]) this.mSystemPackageArray, packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public long convertUsToMs(long timeUs) {
        return timeUs / 1000;
    }

    public long convertMsToUs(long timeMs) {
        return 1000 * timeMs;
    }

    @VisibleForTesting
    public long getForegroundActivityTotalTimeUs(BatteryStats.Uid uid, long rawRealtimeUs) {
        BatteryStats.Timer timer = uid.getForegroundActivityTimer();
        if (timer != null) {
            return timer.getTotalTimeLocked(rawRealtimeUs, 0);
        }
        return 0;
    }

    @VisibleForTesting
    public long getProcessForegroundTimeMs(BatteryStats.Uid uid, int which) {
        long rawRealTimeUs = convertMsToUs(SystemClock.elapsedRealtime());
        long timeUs = 0;
        for (int type : new int[]{0}) {
            timeUs += uid.getProcessStateTime(type, rawRealTimeUs, which);
        }
        return convertUsToMs(Math.min(timeUs, getForegroundActivityTotalTimeUs(uid, rawRealTimeUs)));
    }

    @VisibleForTesting
    public void setPackageManager(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    @VisibleForTesting
    public void setSystemPackageArray(String[] array) {
        this.mSystemPackageArray = array;
    }

    @VisibleForTesting
    public void setServicePackageArray(String[] array) {
        this.mServicepackageArray = array;
    }

    @UnsupportedAppUsage
    private void load() {
        if (this.mBatteryInfo != null) {
            this.mStats = getStats(this.mBatteryInfo);
            if (this.mCollectBatteryBroadcast) {
                this.mBatteryBroadcast = this.mContext.registerReceiver((BroadcastReceiver) null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        }
    }

    private static BatteryStatsImpl getStats(IBatteryStats service) {
        FileInputStream fis;
        try {
            ParcelFileDescriptor pfd = service.getStatisticsStream();
            if (pfd != null) {
                try {
                    fis = new ParcelFileDescriptor.AutoCloseInputStream(pfd);
                    byte[] data = readFully(fis, MemoryFile.getSize(pfd.getFileDescriptor()));
                    Parcel parcel = Parcel.obtain();
                    parcel.unmarshall(data, 0, data.length);
                    parcel.setDataPosition(0);
                    BatteryStatsImpl stats = BatteryStatsImpl.CREATOR.createFromParcel(parcel);
                    fis.close();
                    return stats;
                } catch (IOException e) {
                    Log.w(TAG, "Unable to read statistics stream", e);
                } catch (Throwable th) {
                    r2.addSuppressed(th);
                }
            }
        } catch (RemoteException e2) {
            Log.w(TAG, "RemoteException:", e2);
        }
        return new BatteryStatsImpl();
        throw th;
    }
}
