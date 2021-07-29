package com.android.internal.app.procstats;

import android.content.ComponentName;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.ProcessMap;
import com.android.internal.app.procstats.AssociationState;
import com.android.internal.content.NativeLibraryHelper;
import com.ibm.icu.text.PluralRules;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProcessStats implements Parcelable {
    public static final int ADD_PSS_EXTERNAL = 3;
    public static final int ADD_PSS_EXTERNAL_SLOW = 4;
    public static final int ADD_PSS_INTERNAL_ALL_MEM = 1;
    public static final int ADD_PSS_INTERNAL_ALL_POLL = 2;
    public static final int ADD_PSS_INTERNAL_SINGLE = 0;
    public static final int ADJ_COUNT = 8;
    public static final int ADJ_MEM_FACTOR_COUNT = 4;
    public static final int ADJ_MEM_FACTOR_CRITICAL = 3;
    public static final int ADJ_MEM_FACTOR_LOW = 2;
    public static final int ADJ_MEM_FACTOR_MODERATE = 1;
    public static final int ADJ_MEM_FACTOR_NORMAL = 0;
    public static final int ADJ_NOTHING = -1;
    public static final int ADJ_SCREEN_MOD = 4;
    public static final int ADJ_SCREEN_OFF = 0;
    public static final int ADJ_SCREEN_ON = 4;
    public static final int[] ALL_MEM_ADJ = {0, 1, 2, 3};
    public static final int[] ALL_PROC_STATES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    public static final int[] ALL_SCREEN_ADJ = {0, 4};
    public static final int[] BACKGROUND_PROC_STATES = {2, 3, 4, 8, 5, 6, 7};
    static final int[] BAD_TABLE = new int[0];
    public static long COMMIT_PERIOD = 10800000;
    public static long COMMIT_UPTIME_PERIOD = 3600000;
    public static final Parcelable.Creator<ProcessStats> CREATOR = new Parcelable.Creator<ProcessStats>() {
        public ProcessStats createFromParcel(Parcel in) {
            return new ProcessStats(in);
        }

        public ProcessStats[] newArray(int size) {
            return new ProcessStats[size];
        }
    };
    static final boolean DEBUG = false;
    static final boolean DEBUG_PARCEL = false;
    public static final int FLAG_COMPLETE = 1;
    public static final int FLAG_SHUTDOWN = 2;
    public static final int FLAG_SYSPROPS = 4;
    private static final long INVERSE_PROC_STATE_WARNING_MIN_INTERVAL_MS = 10000;
    private static final int MAGIC = 1347638356;
    public static final int[] NON_CACHED_PROC_STATES = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    public static final int[] OPTIONS = {1, 2, 4, 8, 14, 15};
    public static final String[] OPTIONS_STR = {"proc", "pkg-proc", "pkg-svc", "pkg-asc", "pkg-all", "all"};
    private static final int PARCEL_VERSION = 36;
    public static final int PSS_AVERAGE = 2;
    public static final int PSS_COUNT = 10;
    public static final int PSS_MAXIMUM = 3;
    public static final int PSS_MINIMUM = 1;
    public static final int PSS_RSS_AVERAGE = 8;
    public static final int PSS_RSS_MAXIMUM = 9;
    public static final int PSS_RSS_MINIMUM = 7;
    public static final int PSS_SAMPLE_COUNT = 0;
    public static final int PSS_USS_AVERAGE = 5;
    public static final int PSS_USS_MAXIMUM = 6;
    public static final int PSS_USS_MINIMUM = 4;
    public static final int REPORT_ALL = 15;
    public static final int REPORT_PKG_ASC_STATS = 8;
    public static final int REPORT_PKG_PROC_STATS = 2;
    public static final int REPORT_PKG_STATS = 14;
    public static final int REPORT_PKG_SVC_STATS = 4;
    public static final int REPORT_PROC_STATS = 1;
    public static final String SERVICE_NAME = "procstats";
    public static final int STATE_BACKUP = 4;
    public static final int STATE_CACHED_ACTIVITY = 11;
    public static final int STATE_CACHED_ACTIVITY_CLIENT = 12;
    public static final int STATE_CACHED_EMPTY = 13;
    public static final int STATE_COUNT = 14;
    public static final int STATE_HEAVY_WEIGHT = 8;
    public static final int STATE_HOME = 9;
    public static final int STATE_IMPORTANT_BACKGROUND = 3;
    public static final int STATE_IMPORTANT_FOREGROUND = 2;
    public static final int STATE_LAST_ACTIVITY = 10;
    public static final int STATE_NOTHING = -1;
    public static final int STATE_PERSISTENT = 0;
    public static final int STATE_RECEIVER = 7;
    public static final int STATE_SERVICE = 5;
    public static final int STATE_SERVICE_RESTARTING = 6;
    public static final int STATE_TOP = 1;
    public static final int SYS_MEM_USAGE_CACHED_AVERAGE = 2;
    public static final int SYS_MEM_USAGE_CACHED_MAXIMUM = 3;
    public static final int SYS_MEM_USAGE_CACHED_MINIMUM = 1;
    public static final int SYS_MEM_USAGE_COUNT = 16;
    public static final int SYS_MEM_USAGE_FREE_AVERAGE = 5;
    public static final int SYS_MEM_USAGE_FREE_MAXIMUM = 6;
    public static final int SYS_MEM_USAGE_FREE_MINIMUM = 4;
    public static final int SYS_MEM_USAGE_KERNEL_AVERAGE = 11;
    public static final int SYS_MEM_USAGE_KERNEL_MAXIMUM = 12;
    public static final int SYS_MEM_USAGE_KERNEL_MINIMUM = 10;
    public static final int SYS_MEM_USAGE_NATIVE_AVERAGE = 14;
    public static final int SYS_MEM_USAGE_NATIVE_MAXIMUM = 15;
    public static final int SYS_MEM_USAGE_NATIVE_MINIMUM = 13;
    public static final int SYS_MEM_USAGE_SAMPLE_COUNT = 0;
    public static final int SYS_MEM_USAGE_ZRAM_AVERAGE = 8;
    public static final int SYS_MEM_USAGE_ZRAM_MAXIMUM = 9;
    public static final int SYS_MEM_USAGE_ZRAM_MINIMUM = 7;
    public static final String TAG = "ProcessStats";
    private static final Pattern sPageTypeRegex = Pattern.compile("^Node\\s+(\\d+),.* zone\\s+(\\w+),.* type\\s+(\\w+)\\s+([\\s\\d]+?)\\s*$");
    ArrayMap<String, Integer> mCommonStringToIndex;
    public long mExternalPssCount;
    public long mExternalPssTime;
    public long mExternalSlowPssCount;
    public long mExternalSlowPssTime;
    public int mFlags;
    boolean mHasSwappedOutPss;
    ArrayList<String> mIndexToCommonString;
    public long mInternalAllMemPssCount;
    public long mInternalAllMemPssTime;
    public long mInternalAllPollPssCount;
    public long mInternalAllPollPssTime;
    public long mInternalSinglePssCount;
    public long mInternalSinglePssTime;
    public int mMemFactor = -1;
    public final long[] mMemFactorDurations = new long[8];
    private long mNextInverseProcStateWarningUptime;
    public final ProcessMap<LongSparseArray<PackageState>> mPackages = new ProcessMap<>();
    private final ArrayList<String> mPageTypeLabels = new ArrayList<>();
    private final ArrayList<Integer> mPageTypeNodes = new ArrayList<>();
    private final ArrayList<int[]> mPageTypeSizes = new ArrayList<>();
    private final ArrayList<String> mPageTypeZones = new ArrayList<>();
    public final ProcessMap<ProcessState> mProcesses = new ProcessMap<>();
    public String mReadError;
    boolean mRunning;
    String mRuntime;
    private int mSkippedInverseProcStateWarningCount;
    public long mStartTime;
    public final SysMemUsageTable mSysMemUsage = new SysMemUsageTable(this.mTableData);
    public final long[] mSysMemUsageArgs = new long[16];
    public final SparseMappingTable mTableData = new SparseMappingTable();
    public long mTimePeriodEndRealtime;
    public long mTimePeriodEndUptime;
    public long mTimePeriodStartClock;
    public String mTimePeriodStartClockStr;
    public long mTimePeriodStartRealtime;
    public long mTimePeriodStartUptime;
    public final ArrayList<AssociationState.SourceState> mTrackingAssociations = new ArrayList<>();

    public ProcessStats(boolean running) {
        this.mRunning = running;
        reset();
        if (running) {
            Debug.MemoryInfo info = new Debug.MemoryInfo();
            Debug.getMemoryInfo(Process.myPid(), info);
            this.mHasSwappedOutPss = info.hasSwappedOutPss();
        }
    }

    public ProcessStats(Parcel in) {
        reset();
        readFromParcel(in);
    }

    public void add(ProcessStats other) {
        ArrayMap<String, SparseArray<ProcessState>> procMap;
        ProcessState thisProc;
        LongSparseArray<PackageState> versions;
        SparseArray<LongSparseArray<PackageState>> uids;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap;
        int NSRVS;
        int NPROCS;
        int NSRVS2;
        PackageState otherState;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = other.mPackages.getMap();
        int ip = 0;
        while (true) {
            int ip2 = ip;
            if (ip2 >= pkgMap2.size()) {
                break;
            }
            String pkgName = pkgMap2.keyAt(ip2);
            SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap2.valueAt(ip2);
            int iu = 0;
            while (true) {
                int iu2 = iu;
                if (iu2 >= uids2.size()) {
                    break;
                }
                int uid = uids2.keyAt(iu2);
                LongSparseArray<PackageState> versions2 = uids2.valueAt(iu2);
                int iv = 0;
                while (true) {
                    int iv2 = iv;
                    if (iv2 >= versions2.size()) {
                        break;
                    }
                    long vers = versions2.keyAt(iv2);
                    PackageState otherState2 = versions2.valueAt(iv2);
                    int NPROCS2 = otherState2.mProcesses.size();
                    int NSRVS3 = otherState2.mServices.size();
                    int NASCS = otherState2.mAssociations.size();
                    int iproc = 0;
                    while (true) {
                        versions = versions2;
                        int iproc2 = iproc;
                        if (iproc2 >= NPROCS2) {
                            break;
                        }
                        int NSRVS4 = NSRVS3;
                        ProcessState otherProc = otherState2.mProcesses.valueAt(iproc2);
                        int NPROCS3 = NPROCS2;
                        if (otherProc.getCommonProcess() != otherProc) {
                            pkgMap = pkgMap2;
                            ProcessState otherProc2 = otherProc;
                            uids = uids2;
                            NPROCS = NPROCS3;
                            NSRVS2 = NSRVS4;
                            otherState = otherState2;
                            long vers2 = vers;
                            NSRVS = iv2;
                            ProcessState thisProc2 = getProcessStateLocked(pkgName, uid, vers, otherProc.getName());
                            if (thisProc2.getCommonProcess() == thisProc2) {
                                thisProc2.setMultiPackage(true);
                                long now = SystemClock.uptimeMillis();
                                vers = vers2;
                                PackageState pkgState = getPackageStateLocked(pkgName, uid, vers);
                                thisProc2 = thisProc2.clone(now);
                                long j = now;
                                pkgState.mProcesses.put(thisProc2.getName(), thisProc2);
                            } else {
                                vers = vers2;
                            }
                            thisProc2.add(otherProc2);
                        } else {
                            otherState = otherState2;
                            pkgMap = pkgMap2;
                            uids = uids2;
                            NSRVS2 = NSRVS4;
                            NPROCS = NPROCS3;
                            NSRVS = iv2;
                        }
                        iproc = iproc2 + 1;
                        otherState2 = otherState;
                        NSRVS3 = NSRVS2;
                        NPROCS2 = NPROCS;
                        versions2 = versions;
                        iv2 = NSRVS;
                        pkgMap2 = pkgMap;
                        uids2 = uids;
                        ProcessStats processStats = other;
                    }
                    PackageState otherState3 = otherState2;
                    int iv3 = iv2;
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap3 = pkgMap2;
                    SparseArray<LongSparseArray<PackageState>> uids3 = uids2;
                    int NSRVS5 = NSRVS3;
                    int isvc = 0;
                    while (true) {
                        int isvc2 = isvc;
                        if (isvc2 >= NSRVS5) {
                            break;
                        }
                        ServiceState otherSvc = otherState3.mServices.valueAt(isvc2);
                        long j2 = vers;
                        int NSRVS6 = NSRVS5;
                        getServiceStateLocked(pkgName, uid, vers, otherSvc.getProcessName(), otherSvc.getName()).add(otherSvc);
                        isvc = isvc2 + 1;
                        NSRVS5 = NSRVS6;
                    }
                    long vers3 = vers;
                    int i = NSRVS5;
                    int iasc = 0;
                    while (true) {
                        int iasc2 = iasc;
                        if (iasc2 >= NASCS) {
                            break;
                        }
                        AssociationState otherAsc = otherState3.mAssociations.valueAt(iasc2);
                        getAssociationStateLocked(pkgName, uid, vers3, otherAsc.getProcessName(), otherAsc.getName()).add(otherAsc);
                        iasc = iasc2 + 1;
                    }
                    iv = iv3 + 1;
                    versions2 = versions;
                    pkgMap2 = pkgMap3;
                    uids2 = uids3;
                    ProcessStats processStats2 = other;
                }
                SparseArray<LongSparseArray<PackageState>> sparseArray = uids2;
                iu = iu2 + 1;
                ProcessStats processStats3 = other;
            }
            ip = ip2 + 1;
            ProcessStats processStats4 = other;
        }
        ProcessStats processStats5 = other;
        ArrayMap<String, SparseArray<ProcessState>> procMap2 = processStats5.mProcesses.getMap();
        int ip3 = 0;
        while (true) {
            int ip4 = ip3;
            if (ip4 >= procMap2.size()) {
                break;
            }
            SparseArray<ProcessState> uids4 = procMap2.valueAt(ip4);
            int iu3 = 0;
            while (true) {
                int iu4 = iu3;
                if (iu4 >= uids4.size()) {
                    break;
                }
                int uid2 = uids4.keyAt(iu4);
                ProcessState otherProc3 = uids4.valueAt(iu4);
                String name = otherProc3.getName();
                String pkg = otherProc3.getPackage();
                long vers4 = otherProc3.getVersion();
                ProcessState thisProc3 = this.mProcesses.get(name, uid2);
                if (thisProc3 == null) {
                    procMap = procMap2;
                    thisProc = new ProcessState(this, pkg, uid2, vers4, name);
                    this.mProcesses.put(name, uid2, thisProc);
                    PackageState thisState = getPackageStateLocked(pkg, uid2, vers4);
                    if (!thisState.mProcesses.containsKey(name)) {
                        thisState.mProcesses.put(name, thisProc);
                    }
                } else {
                    procMap = procMap2;
                    String str = pkg;
                    thisProc = thisProc3;
                }
                thisProc.add(otherProc3);
                iu3 = iu4 + 1;
                procMap2 = procMap;
            }
            ip3 = ip4 + 1;
        }
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= 8) {
                break;
            }
            long[] jArr = this.mMemFactorDurations;
            jArr[i3] = jArr[i3] + processStats5.mMemFactorDurations[i3];
            i2 = i3 + 1;
        }
        this.mSysMemUsage.mergeStats(processStats5.mSysMemUsage);
        if (processStats5.mTimePeriodStartClock < this.mTimePeriodStartClock) {
            this.mTimePeriodStartClock = processStats5.mTimePeriodStartClock;
            this.mTimePeriodStartClockStr = processStats5.mTimePeriodStartClockStr;
        }
        this.mTimePeriodEndRealtime += processStats5.mTimePeriodEndRealtime - processStats5.mTimePeriodStartRealtime;
        this.mTimePeriodEndUptime += processStats5.mTimePeriodEndUptime - processStats5.mTimePeriodStartUptime;
        this.mInternalSinglePssCount += processStats5.mInternalSinglePssCount;
        this.mInternalSinglePssTime += processStats5.mInternalSinglePssTime;
        this.mInternalAllMemPssCount += processStats5.mInternalAllMemPssCount;
        this.mInternalAllMemPssTime += processStats5.mInternalAllMemPssTime;
        this.mInternalAllPollPssCount += processStats5.mInternalAllPollPssCount;
        this.mInternalAllPollPssTime += processStats5.mInternalAllPollPssTime;
        this.mExternalPssCount += processStats5.mExternalPssCount;
        this.mExternalPssTime += processStats5.mExternalPssTime;
        this.mExternalSlowPssCount += processStats5.mExternalSlowPssCount;
        this.mExternalSlowPssTime += processStats5.mExternalSlowPssTime;
        this.mHasSwappedOutPss |= processStats5.mHasSwappedOutPss;
    }

    public void addSysMemUsage(long cachedMem, long freeMem, long zramMem, long kernelMem, long nativeMem) {
        if (this.mMemFactor != -1) {
            int state = this.mMemFactor * 14;
            this.mSysMemUsageArgs[0] = 1;
            for (int i = 0; i < 3; i++) {
                this.mSysMemUsageArgs[i + 1] = cachedMem;
                this.mSysMemUsageArgs[i + 4] = freeMem;
                this.mSysMemUsageArgs[i + 7] = zramMem;
                this.mSysMemUsageArgs[i + 10] = kernelMem;
                this.mSysMemUsageArgs[i + 13] = nativeMem;
            }
            this.mSysMemUsage.mergeStats(state, this.mSysMemUsageArgs, 0);
        }
    }

    public void computeTotalMemoryUse(TotalMemoryUseCollection data, long now) {
        long[] totalMemUsage;
        TotalMemoryUseCollection totalMemoryUseCollection = data;
        long j = now;
        totalMemoryUseCollection.totalTime = 0;
        int i = 0;
        for (int i2 = 0; i2 < 14; i2++) {
            totalMemoryUseCollection.processStateWeight[i2] = 0.0d;
            totalMemoryUseCollection.processStatePss[i2] = 0;
            totalMemoryUseCollection.processStateTime[i2] = 0;
            totalMemoryUseCollection.processStateSamples[i2] = 0;
        }
        for (int i3 = 0; i3 < 16; i3++) {
            totalMemoryUseCollection.sysMemUsage[i3] = 0;
        }
        totalMemoryUseCollection.sysMemCachedWeight = 0.0d;
        totalMemoryUseCollection.sysMemFreeWeight = 0.0d;
        totalMemoryUseCollection.sysMemZRamWeight = 0.0d;
        totalMemoryUseCollection.sysMemKernelWeight = 0.0d;
        totalMemoryUseCollection.sysMemNativeWeight = 0.0d;
        totalMemoryUseCollection.sysMemSamples = 0;
        long[] totalMemUsage2 = this.mSysMemUsage.getTotalMemUsage();
        int is = 0;
        while (is < totalMemoryUseCollection.screenStates.length) {
            int im = i;
            while (im < totalMemoryUseCollection.memStates.length) {
                int memBucket = totalMemoryUseCollection.screenStates[is] + totalMemoryUseCollection.memStates[im];
                int stateBucket = memBucket * 14;
                long memTime = this.mMemFactorDurations[memBucket];
                if (this.mMemFactor == memBucket) {
                    memTime += j - this.mStartTime;
                }
                totalMemoryUseCollection.totalTime += memTime;
                int sysKey = this.mSysMemUsage.getKey((byte) stateBucket);
                long[] longs = totalMemUsage2;
                int idx = 0;
                if (sysKey != -1) {
                    long[] tmpLongs = this.mSysMemUsage.getArrayForKey(sysKey);
                    int tmpIndex = SparseMappingTable.getIndexFromKey(sysKey);
                    if (tmpLongs[tmpIndex + 0] >= 3) {
                        totalMemUsage = totalMemUsage2;
                        SysMemUsageTable.mergeSysMemUsage(totalMemoryUseCollection.sysMemUsage, i, longs, 0);
                        longs = tmpLongs;
                        idx = tmpIndex;
                        int i4 = memBucket;
                        int i5 = stateBucket;
                        totalMemoryUseCollection.sysMemCachedWeight += ((double) longs[idx + 2]) * ((double) memTime);
                        totalMemoryUseCollection.sysMemFreeWeight += ((double) longs[idx + 5]) * ((double) memTime);
                        totalMemoryUseCollection.sysMemZRamWeight += ((double) longs[idx + 8]) * ((double) memTime);
                        totalMemoryUseCollection.sysMemKernelWeight += ((double) longs[idx + 11]) * ((double) memTime);
                        totalMemoryUseCollection.sysMemNativeWeight += ((double) longs[idx + 14]) * ((double) memTime);
                        totalMemoryUseCollection.sysMemSamples = (int) (((long) totalMemoryUseCollection.sysMemSamples) + longs[idx + 0]);
                        im++;
                        totalMemUsage2 = totalMemUsage;
                        j = now;
                        i = 0;
                    }
                }
                totalMemUsage = totalMemUsage2;
                int i42 = memBucket;
                int i52 = stateBucket;
                totalMemoryUseCollection.sysMemCachedWeight += ((double) longs[idx + 2]) * ((double) memTime);
                totalMemoryUseCollection.sysMemFreeWeight += ((double) longs[idx + 5]) * ((double) memTime);
                totalMemoryUseCollection.sysMemZRamWeight += ((double) longs[idx + 8]) * ((double) memTime);
                totalMemoryUseCollection.sysMemKernelWeight += ((double) longs[idx + 11]) * ((double) memTime);
                totalMemoryUseCollection.sysMemNativeWeight += ((double) longs[idx + 14]) * ((double) memTime);
                totalMemoryUseCollection.sysMemSamples = (int) (((long) totalMemoryUseCollection.sysMemSamples) + longs[idx + 0]);
                im++;
                totalMemUsage2 = totalMemUsage;
                j = now;
                i = 0;
            }
            is++;
            j = now;
            i = 0;
        }
        totalMemoryUseCollection.hasSwappedOutPss = this.mHasSwappedOutPss;
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        for (int iproc = 0; iproc < procMap.size(); iproc++) {
            SparseArray<ProcessState> uids = procMap.valueAt(iproc);
            for (int iu = 0; iu < uids.size(); iu++) {
                uids.valueAt(iu).aggregatePss(totalMemoryUseCollection, now);
            }
            long j2 = now;
        }
        long j3 = now;
    }

    public void reset() {
        resetCommon();
        this.mPackages.getMap().clear();
        this.mProcesses.getMap().clear();
        this.mMemFactor = -1;
        this.mStartTime = 0;
    }

    public void resetSafely() {
        resetCommon();
        long now = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        for (int ip = procMap.size() - 1; ip >= 0; ip--) {
            SparseArray<ProcessState> uids = procMap.valueAt(ip);
            for (int iu = uids.size() - 1; iu >= 0; iu--) {
                uids.valueAt(iu).tmpNumInUse = 0;
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
        for (int ip2 = pkgMap.size() - 1; ip2 >= 0; ip2--) {
            SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip2);
            for (int iu2 = uids2.size() - 1; iu2 >= 0; iu2--) {
                LongSparseArray<PackageState> vpkgs = uids2.valueAt(iu2);
                for (int iv = vpkgs.size() - 1; iv >= 0; iv--) {
                    PackageState pkgState = vpkgs.valueAt(iv);
                    for (int iproc = pkgState.mProcesses.size() - 1; iproc >= 0; iproc--) {
                        ProcessState ps = pkgState.mProcesses.valueAt(iproc);
                        if (ps.isInUse()) {
                            ps.resetSafely(now);
                            ps.getCommonProcess().tmpNumInUse++;
                            ps.getCommonProcess().tmpFoundSubProc = ps;
                        } else {
                            pkgState.mProcesses.valueAt(iproc).makeDead();
                            pkgState.mProcesses.removeAt(iproc);
                        }
                    }
                    for (int isvc = pkgState.mServices.size() - 1; isvc >= 0; isvc--) {
                        ServiceState ss = pkgState.mServices.valueAt(isvc);
                        if (ss.isInUse()) {
                            ss.resetSafely(now);
                        } else {
                            pkgState.mServices.removeAt(isvc);
                        }
                    }
                    for (int iasc = pkgState.mAssociations.size() - 1; iasc >= 0; iasc--) {
                        AssociationState as = pkgState.mAssociations.valueAt(iasc);
                        if (as.isInUse()) {
                            as.resetSafely(now);
                        } else {
                            pkgState.mAssociations.removeAt(iasc);
                        }
                    }
                    if (pkgState.mProcesses.size() <= 0 && pkgState.mServices.size() <= 0 && pkgState.mAssociations.size() <= 0) {
                        vpkgs.removeAt(iv);
                    }
                }
                if (vpkgs.size() <= 0) {
                    uids2.removeAt(iu2);
                }
            }
            if (uids2.size() <= 0) {
                pkgMap.removeAt(ip2);
            }
        }
        for (int ip3 = procMap.size() - 1; ip3 >= 0; ip3--) {
            SparseArray<ProcessState> uids3 = procMap.valueAt(ip3);
            for (int iu3 = uids3.size() - 1; iu3 >= 0; iu3--) {
                ProcessState ps2 = uids3.valueAt(iu3);
                if (!ps2.isInUse() && ps2.tmpNumInUse <= 0) {
                    ps2.makeDead();
                    uids3.removeAt(iu3);
                } else if (ps2.isActive() || !ps2.isMultiPackage() || ps2.tmpNumInUse != 1) {
                    ps2.resetSafely(now);
                } else {
                    ProcessState ps3 = ps2.tmpFoundSubProc;
                    ps3.makeStandalone();
                    uids3.setValueAt(iu3, ps3);
                }
            }
            if (uids3.size() <= 0) {
                procMap.removeAt(ip3);
            }
        }
        this.mStartTime = now;
    }

    private void resetCommon() {
        this.mTimePeriodStartClock = System.currentTimeMillis();
        buildTimePeriodStartClockStr();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mTimePeriodEndRealtime = elapsedRealtime;
        this.mTimePeriodStartRealtime = elapsedRealtime;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mTimePeriodEndUptime = uptimeMillis;
        this.mTimePeriodStartUptime = uptimeMillis;
        this.mInternalSinglePssCount = 0;
        this.mInternalSinglePssTime = 0;
        this.mInternalAllMemPssCount = 0;
        this.mInternalAllMemPssTime = 0;
        this.mInternalAllPollPssCount = 0;
        this.mInternalAllPollPssTime = 0;
        this.mExternalPssCount = 0;
        this.mExternalPssTime = 0;
        this.mExternalSlowPssCount = 0;
        this.mExternalSlowPssTime = 0;
        this.mTableData.reset();
        Arrays.fill(this.mMemFactorDurations, 0);
        this.mSysMemUsage.resetTable();
        this.mStartTime = 0;
        this.mReadError = null;
        this.mFlags = 0;
        evaluateSystemProperties(true);
        updateFragmentation();
    }

    public boolean evaluateSystemProperties(boolean update) {
        boolean changed = false;
        String runtime = SystemProperties.get("persist.sys.dalvik.vm.lib.2", VMRuntime.getRuntime().vmLibrary());
        if (!Objects.equals(runtime, this.mRuntime)) {
            changed = true;
            if (update) {
                this.mRuntime = runtime;
            }
        }
        return changed;
    }

    private void buildTimePeriodStartClockStr() {
        this.mTimePeriodStartClockStr = DateFormat.format((CharSequence) "yyyy-MM-dd-HH-mm-ss", this.mTimePeriodStartClock).toString();
    }

    public void updateFragmentation() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/pagetypeinfo"));
            Matcher matcher = sPageTypeRegex.matcher("");
            this.mPageTypeNodes.clear();
            this.mPageTypeZones.clear();
            this.mPageTypeLabels.clear();
            this.mPageTypeSizes.clear();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    try {
                        reader.close();
                        return;
                    } catch (IOException e) {
                        return;
                    }
                } else {
                    matcher.reset(line);
                    if (matcher.matches()) {
                        Integer node = Integer.valueOf(matcher.group(1), 10);
                        if (node != null) {
                            this.mPageTypeNodes.add(node);
                            this.mPageTypeZones.add(matcher.group(2));
                            this.mPageTypeLabels.add(matcher.group(3));
                            this.mPageTypeSizes.add(splitAndParseNumbers(matcher.group(4)));
                        }
                    }
                }
            }
        } catch (IOException e2) {
            this.mPageTypeNodes.clear();
            this.mPageTypeZones.clear();
            this.mPageTypeLabels.clear();
            this.mPageTypeSizes.clear();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                }
            }
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
    }

    private static int[] splitAndParseNumbers(String s) {
        int count = 0;
        int N = s.length();
        boolean digit = false;
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                digit = false;
            } else if (!digit) {
                digit = true;
                count++;
            }
        }
        int[] result = new int[count];
        int p = 0;
        int val = 0;
        for (int i2 = 0; i2 < N; i2++) {
            char c2 = s.charAt(i2);
            if (c2 < '0' || c2 > '9') {
                if (digit) {
                    digit = false;
                    result[p] = val;
                    p++;
                }
            } else if (!digit) {
                digit = true;
                val = c2 - '0';
            } else {
                val = (val * 10) + (c2 - '0');
            }
        }
        if (count > 0) {
            result[count - 1] = val;
        }
        return result;
    }

    private void writeCompactedLongArray(Parcel out, long[] array, int num) {
        for (int i = 0; i < num; i++) {
            long val = array[i];
            if (val < 0) {
                Slog.w(TAG, "Time val negative: " + val);
                val = 0;
            }
            if (val <= 2147483647L) {
                out.writeInt((int) val);
            } else {
                out.writeInt(~((int) (2147483647L & (val >> 32))));
                out.writeInt((int) (4294967295L & val));
            }
        }
    }

    private void readCompactedLongArray(Parcel in, int version, long[] array, int num) {
        if (version <= 10) {
            in.readLongArray(array);
            return;
        }
        int alen = array.length;
        if (num <= alen) {
            int i = 0;
            while (i < num) {
                int val = in.readInt();
                if (val >= 0) {
                    array[i] = (long) val;
                } else {
                    array[i] = (((long) (~val)) << 32) | ((long) in.readInt());
                }
                i++;
            }
            while (i < alen) {
                array[i] = 0;
                i++;
            }
            return;
        }
        throw new RuntimeException("bad array lengths: got " + num + " array is " + alen);
    }

    /* access modifiers changed from: package-private */
    public void writeCommonString(Parcel out, String name) {
        Integer index = this.mCommonStringToIndex.get(name);
        if (index != null) {
            out.writeInt(index.intValue());
            return;
        }
        Integer index2 = Integer.valueOf(this.mCommonStringToIndex.size());
        this.mCommonStringToIndex.put(name, index2);
        out.writeInt(~index2.intValue());
        out.writeString(name);
    }

    /* access modifiers changed from: package-private */
    public String readCommonString(Parcel in, int version) {
        if (version <= 9) {
            return in.readString();
        }
        int index = in.readInt();
        if (index >= 0) {
            return this.mIndexToCommonString.get(index);
        }
        int index2 = ~index;
        String name = in.readString();
        while (this.mIndexToCommonString.size() <= index2) {
            this.mIndexToCommonString.add((Object) null);
        }
        this.mIndexToCommonString.set(index2, name);
        return name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        writeToParcel(out, SystemClock.uptimeMillis(), flags);
    }

    public void writeToParcel(Parcel out, long now, int flags) {
        int NUID;
        Parcel parcel = out;
        long j = now;
        parcel.writeInt(MAGIC);
        parcel.writeInt(36);
        parcel.writeInt(14);
        parcel.writeInt(8);
        parcel.writeInt(10);
        parcel.writeInt(16);
        parcel.writeInt(4096);
        this.mCommonStringToIndex = new ArrayMap<>(this.mProcesses.size());
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        int NPROC = procMap.size();
        for (int ip = 0; ip < NPROC; ip++) {
            SparseArray<ProcessState> uids = procMap.valueAt(ip);
            int NUID2 = uids.size();
            for (int iu = 0; iu < NUID2; iu++) {
                uids.valueAt(iu).commitStateTime(j);
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
        int NPKG = pkgMap.size();
        for (int ip2 = 0; ip2 < NPKG; ip2++) {
            SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip2);
            int iproc = uids2.size();
            for (int iu2 = 0; iu2 < iproc; iu2++) {
                LongSparseArray<PackageState> vpkgs = uids2.valueAt(iu2);
                int NVERS = vpkgs.size();
                int iv = 0;
                while (iv < NVERS) {
                    PackageState pkgState = vpkgs.valueAt(iv);
                    SparseArray<LongSparseArray<PackageState>> uids3 = uids2;
                    int NPROCS = pkgState.mProcesses.size();
                    int iproc2 = 0;
                    while (true) {
                        NUID = iproc;
                        int iproc3 = iproc2;
                        if (iproc3 >= NPROCS) {
                            break;
                        }
                        int NPROCS2 = NPROCS;
                        ProcessState proc = pkgState.mProcesses.valueAt(iproc3);
                        LongSparseArray<PackageState> vpkgs2 = vpkgs;
                        if (proc.getCommonProcess() != proc) {
                            proc.commitStateTime(j);
                        }
                        iproc2 = iproc3 + 1;
                        iproc = NUID;
                        NPROCS = NPROCS2;
                        vpkgs = vpkgs2;
                    }
                    LongSparseArray<PackageState> vpkgs3 = vpkgs;
                    int NSRVS = pkgState.mServices.size();
                    for (int isvc = 0; isvc < NSRVS; isvc++) {
                        pkgState.mServices.valueAt(isvc).commitStateTime(j);
                    }
                    int NASCS = pkgState.mAssociations.size();
                    int iasc = 0;
                    while (iasc < NASCS) {
                        pkgState.mAssociations.valueAt(iasc).commitStateTime(j);
                        iasc++;
                        NSRVS = NSRVS;
                    }
                    iv++;
                    uids2 = uids3;
                    iproc = NUID;
                    vpkgs = vpkgs3;
                }
                int i = iproc;
            }
        }
        parcel.writeLong(this.mTimePeriodStartClock);
        parcel.writeLong(this.mTimePeriodStartRealtime);
        parcel.writeLong(this.mTimePeriodEndRealtime);
        parcel.writeLong(this.mTimePeriodStartUptime);
        parcel.writeLong(this.mTimePeriodEndUptime);
        parcel.writeLong(this.mInternalSinglePssCount);
        parcel.writeLong(this.mInternalSinglePssTime);
        parcel.writeLong(this.mInternalAllMemPssCount);
        parcel.writeLong(this.mInternalAllMemPssTime);
        parcel.writeLong(this.mInternalAllPollPssCount);
        parcel.writeLong(this.mInternalAllPollPssTime);
        parcel.writeLong(this.mExternalPssCount);
        parcel.writeLong(this.mExternalPssTime);
        parcel.writeLong(this.mExternalSlowPssCount);
        parcel.writeLong(this.mExternalSlowPssTime);
        parcel.writeString(this.mRuntime);
        parcel.writeInt(this.mHasSwappedOutPss ? 1 : 0);
        parcel.writeInt(this.mFlags);
        this.mTableData.writeToParcel(parcel);
        if (this.mMemFactor != -1) {
            long[] jArr = this.mMemFactorDurations;
            int i2 = this.mMemFactor;
            jArr[i2] = jArr[i2] + (j - this.mStartTime);
            this.mStartTime = j;
        }
        writeCompactedLongArray(parcel, this.mMemFactorDurations, this.mMemFactorDurations.length);
        this.mSysMemUsage.writeToParcel(parcel);
        parcel.writeInt(NPROC);
        for (int ip3 = 0; ip3 < NPROC; ip3++) {
            writeCommonString(parcel, procMap.keyAt(ip3));
            SparseArray<ProcessState> uids4 = procMap.valueAt(ip3);
            int NUID3 = uids4.size();
            parcel.writeInt(NUID3);
            for (int iu3 = 0; iu3 < NUID3; iu3++) {
                parcel.writeInt(uids4.keyAt(iu3));
                ProcessState proc2 = uids4.valueAt(iu3);
                writeCommonString(parcel, proc2.getPackage());
                parcel.writeLong(proc2.getVersion());
                proc2.writeToParcel(parcel, j);
            }
        }
        parcel.writeInt(NPKG);
        for (int ip4 = 0; ip4 < NPKG; ip4++) {
            writeCommonString(parcel, pkgMap.keyAt(ip4));
            SparseArray<LongSparseArray<PackageState>> uids5 = pkgMap.valueAt(ip4);
            int NUID4 = uids5.size();
            parcel.writeInt(NUID4);
            for (int iu4 = 0; iu4 < NUID4; iu4++) {
                parcel.writeInt(uids5.keyAt(iu4));
                LongSparseArray<PackageState> vpkgs4 = uids5.valueAt(iu4);
                int NVERS2 = vpkgs4.size();
                parcel.writeInt(NVERS2);
                int iv2 = 0;
                while (iv2 < NVERS2) {
                    ArrayMap<String, SparseArray<ProcessState>> procMap2 = procMap;
                    int NPROC2 = NPROC;
                    parcel.writeLong(vpkgs4.keyAt(iv2));
                    PackageState pkgState2 = vpkgs4.valueAt(iv2);
                    int NPROCS3 = pkgState2.mProcesses.size();
                    parcel.writeInt(NPROCS3);
                    int iproc4 = 0;
                    while (iproc4 < NPROCS3) {
                        int NPROCS4 = NPROCS3;
                        writeCommonString(parcel, pkgState2.mProcesses.keyAt(iproc4));
                        ProcessState proc3 = pkgState2.mProcesses.valueAt(iproc4);
                        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = pkgMap;
                        if (proc3.getCommonProcess() == proc3) {
                            parcel.writeInt(0);
                        } else {
                            parcel.writeInt(1);
                            proc3.writeToParcel(parcel, j);
                        }
                        iproc4++;
                        NPROCS3 = NPROCS4;
                        pkgMap = pkgMap2;
                    }
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap3 = pkgMap;
                    int NSRVS2 = pkgState2.mServices.size();
                    parcel.writeInt(NSRVS2);
                    int isvc2 = 0;
                    while (isvc2 < NSRVS2) {
                        parcel.writeString(pkgState2.mServices.keyAt(isvc2));
                        ServiceState svc = pkgState2.mServices.valueAt(isvc2);
                        writeCommonString(parcel, svc.getProcessName());
                        svc.writeToParcel(parcel, j);
                        isvc2++;
                        NSRVS2 = NSRVS2;
                    }
                    int NASCS2 = pkgState2.mAssociations.size();
                    parcel.writeInt(NASCS2);
                    int iasc2 = 0;
                    while (iasc2 < NASCS2) {
                        writeCommonString(parcel, pkgState2.mAssociations.keyAt(iasc2));
                        AssociationState asc = pkgState2.mAssociations.valueAt(iasc2);
                        writeCommonString(parcel, asc.getProcessName());
                        asc.writeToParcel(this, parcel, j);
                        iasc2++;
                        pkgState2 = pkgState2;
                    }
                    iv2++;
                    procMap = procMap2;
                    NPROC = NPROC2;
                    pkgMap = pkgMap3;
                }
                int i3 = NPROC;
                ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap = pkgMap;
            }
            int i4 = NPROC;
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap2 = pkgMap;
        }
        int i5 = NPROC;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap3 = pkgMap;
        int NPAGETYPES = this.mPageTypeLabels.size();
        parcel.writeInt(NPAGETYPES);
        int i6 = 0;
        while (true) {
            int i7 = i6;
            if (i7 < NPAGETYPES) {
                parcel.writeInt(this.mPageTypeNodes.get(i7).intValue());
                parcel.writeString(this.mPageTypeZones.get(i7));
                parcel.writeString(this.mPageTypeLabels.get(i7));
                parcel.writeIntArray(this.mPageTypeSizes.get(i7));
                i6 = i7 + 1;
            } else {
                this.mCommonStringToIndex = null;
                return;
            }
        }
    }

    private boolean readCheckedInt(Parcel in, int val, String what) {
        int readInt = in.readInt();
        int got = readInt;
        if (readInt == val) {
            return true;
        }
        this.mReadError = "bad " + what + PluralRules.KEYWORD_RULE_SEPARATOR + got;
        return false;
    }

    static byte[] readFully(InputStream stream, int[] outLen) throws IOException {
        int pos = 0;
        int initialAvail = stream.available();
        byte[] data = new byte[(initialAvail > 0 ? initialAvail + 1 : 16384)];
        while (true) {
            int amt = stream.read(data, pos, data.length - pos);
            if (amt < 0) {
                outLen[0] = pos;
                return data;
            }
            pos += amt;
            if (pos >= data.length) {
                byte[] newData = new byte[(pos + 16384)];
                System.arraycopy(data, 0, newData, 0, pos);
                data = newData;
            }
        }
    }

    public void read(InputStream stream) {
        try {
            int[] len = new int[1];
            byte[] raw = readFully(stream, len);
            Parcel in = Parcel.obtain();
            in.unmarshall(raw, 0, len[0]);
            in.setDataPosition(0);
            stream.close();
            readFromParcel(in);
        } catch (IOException e) {
            this.mReadError = "caught exception: " + e;
        }
    }

    public void readFromParcel(Parcel in) {
        int NASCS;
        String associationName;
        AssociationState asc;
        int NSRVS;
        long vers;
        LongSparseArray<PackageState> vpkg;
        PackageState pkgState;
        ServiceState serv;
        int uid;
        Parcel parcel = in;
        boolean hadData = this.mPackages.getMap().size() > 0 || this.mProcesses.getMap().size() > 0;
        if (hadData) {
            resetSafely();
        }
        if (readCheckedInt(parcel, MAGIC, "magic number")) {
            int version = in.readInt();
            if (version != 36) {
                this.mReadError = "bad version: " + version;
            } else if (readCheckedInt(parcel, 14, "state count") && readCheckedInt(parcel, 8, "adj count") && readCheckedInt(parcel, 10, "pss count") && readCheckedInt(parcel, 16, "sys mem usage count") && readCheckedInt(parcel, 4096, "longs size")) {
                this.mIndexToCommonString = new ArrayList<>();
                this.mTimePeriodStartClock = in.readLong();
                buildTimePeriodStartClockStr();
                this.mTimePeriodStartRealtime = in.readLong();
                this.mTimePeriodEndRealtime = in.readLong();
                this.mTimePeriodStartUptime = in.readLong();
                this.mTimePeriodEndUptime = in.readLong();
                this.mInternalSinglePssCount = in.readLong();
                this.mInternalSinglePssTime = in.readLong();
                this.mInternalAllMemPssCount = in.readLong();
                this.mInternalAllMemPssTime = in.readLong();
                this.mInternalAllPollPssCount = in.readLong();
                this.mInternalAllPollPssTime = in.readLong();
                this.mExternalPssCount = in.readLong();
                this.mExternalPssTime = in.readLong();
                this.mExternalSlowPssCount = in.readLong();
                this.mExternalSlowPssTime = in.readLong();
                this.mRuntime = in.readString();
                this.mHasSwappedOutPss = in.readInt() != 0;
                this.mFlags = in.readInt();
                this.mTableData.readFromParcel(parcel);
                readCompactedLongArray(parcel, version, this.mMemFactorDurations, this.mMemFactorDurations.length);
                if (this.mSysMemUsage.readFromParcel(parcel)) {
                    int NPROC = in.readInt();
                    if (NPROC < 0) {
                        this.mReadError = "bad process count: " + NPROC;
                        return;
                    }
                    int NPROC2 = NPROC;
                    while (NPROC2 > 0) {
                        int NPROC3 = NPROC2 - 1;
                        String procName = readCommonString(parcel, version);
                        if (procName == null) {
                            this.mReadError = "bad process name";
                            return;
                        }
                        int NUID = in.readInt();
                        if (NUID < 0) {
                            this.mReadError = "bad uid count: " + NUID;
                            return;
                        }
                        while (NUID > 0) {
                            int NUID2 = NUID - 1;
                            int uid2 = in.readInt();
                            if (uid2 < 0) {
                                this.mReadError = "bad uid: " + uid2;
                                return;
                            }
                            String pkgName = readCommonString(parcel, version);
                            if (pkgName == null) {
                                this.mReadError = "bad process package name";
                                return;
                            }
                            long vers2 = in.readLong();
                            ProcessState proc = hadData ? this.mProcesses.get(procName, uid2) : null;
                            if (proc == null) {
                                ProcessState processState = proc;
                                uid = uid2;
                                proc = new ProcessState(this, pkgName, uid2, vers2, procName);
                                if (!proc.readFromParcel(parcel, true)) {
                                    return;
                                }
                            } else if (proc.readFromParcel(parcel, false)) {
                                uid = uid2;
                            } else {
                                return;
                            }
                            this.mProcesses.put(procName, uid, proc);
                            NUID = NUID2;
                        }
                        NPROC2 = NPROC3;
                    }
                    int NPKG = in.readInt();
                    if (NPKG < 0) {
                        this.mReadError = "bad package count: " + NPKG;
                        return;
                    }
                    while (NPKG > 0) {
                        int NPKG2 = NPKG - 1;
                        String pkgName2 = readCommonString(parcel, version);
                        if (pkgName2 == null) {
                            this.mReadError = "bad package name";
                            return;
                        }
                        int NUID3 = in.readInt();
                        if (NUID3 < 0) {
                            this.mReadError = "bad uid count: " + NUID3;
                            return;
                        }
                        while (NUID3 > 0) {
                            int NUID4 = NUID3 - 1;
                            int uid3 = in.readInt();
                            if (uid3 < 0) {
                                this.mReadError = "bad uid: " + uid3;
                                return;
                            }
                            int NVERS = in.readInt();
                            if (NVERS < 0) {
                                this.mReadError = "bad versions count: " + NVERS;
                                return;
                            }
                            while (NVERS > 0) {
                                int NVERS2 = NVERS - 1;
                                long vers3 = in.readLong();
                                int uid4 = uid3;
                                PackageState pkgState2 = new PackageState(this, pkgName2, uid3, vers3);
                                LongSparseArray<PackageState> vpkg2 = this.mPackages.get(pkgName2, uid4);
                                if (vpkg2 == null) {
                                    vpkg2 = new LongSparseArray<>();
                                    this.mPackages.put(pkgName2, uid4, vpkg2);
                                }
                                LongSparseArray<PackageState> vpkg3 = vpkg2;
                                long vers4 = vers3;
                                vpkg3.put(vers4, pkgState2);
                                int NPROCS = in.readInt();
                                if (NPROCS < 0) {
                                    this.mReadError = "bad package process count: " + NPROCS;
                                    return;
                                }
                                int NPROCS2 = NPROCS;
                                while (NPROCS2 > 0) {
                                    NPROCS2--;
                                    String procName2 = readCommonString(parcel, version);
                                    if (procName2 == null) {
                                        this.mReadError = "bad package process name";
                                        return;
                                    }
                                    int hasProc = in.readInt();
                                    ProcessState commonProc = this.mProcesses.get(procName2, uid4);
                                    if (commonProc == null) {
                                        LongSparseArray<PackageState> longSparseArray = vpkg3;
                                        StringBuilder sb = new StringBuilder();
                                        int i = NPROC2;
                                        sb.append("no common proc: ");
                                        sb.append(procName2);
                                        this.mReadError = sb.toString();
                                        return;
                                    }
                                    LongSparseArray<PackageState> vpkg4 = vpkg3;
                                    int NPROC4 = NPROC2;
                                    if (hasProc != 0) {
                                        ProcessState proc2 = hadData ? pkgState2.mProcesses.get(procName2) : null;
                                        if (proc2 == null) {
                                            proc2 = new ProcessState(commonProc, pkgName2, uid4, vers4, procName2, 0);
                                            if (!proc2.readFromParcel(parcel, true)) {
                                                return;
                                            }
                                        } else if (!proc2.readFromParcel(parcel, false)) {
                                            return;
                                        }
                                        pkgState2.mProcesses.put(procName2, proc2);
                                    } else {
                                        pkgState2.mProcesses.put(procName2, commonProc);
                                    }
                                    vpkg3 = vpkg4;
                                    NPROC2 = NPROC4;
                                }
                                LongSparseArray<PackageState> vpkg5 = vpkg3;
                                int NPROC5 = NPROC2;
                                int NSRVS2 = in.readInt();
                                if (NSRVS2 < 0) {
                                    this.mReadError = "bad package service count: " + NSRVS2;
                                    return;
                                }
                                int NSRVS3 = NSRVS2;
                                while (NSRVS3 > 0) {
                                    int NSRVS4 = NSRVS3 - 1;
                                    String serviceName = in.readString();
                                    if (serviceName == null) {
                                        this.mReadError = "bad package service name";
                                        return;
                                    }
                                    String processName = version > 9 ? readCommonString(parcel, version) : null;
                                    ServiceState serv2 = hadData ? pkgState2.mServices.get(serviceName) : null;
                                    if (serv2 == null) {
                                        vers = vers4;
                                        vpkg = vpkg5;
                                        NSRVS = NSRVS4;
                                        pkgState = pkgState2;
                                        serv = new ServiceState(this, pkgName2, serviceName, processName, (ProcessState) null);
                                    } else {
                                        vers = vers4;
                                        NSRVS = NSRVS4;
                                        vpkg = vpkg5;
                                        pkgState = pkgState2;
                                        serv = serv2;
                                    }
                                    if (serv.readFromParcel(parcel)) {
                                        pkgState.mServices.put(serviceName, serv);
                                        pkgState2 = pkgState;
                                        vpkg5 = vpkg;
                                        vers4 = vers;
                                        NSRVS3 = NSRVS;
                                    } else {
                                        return;
                                    }
                                }
                                int i2 = NSRVS3;
                                LongSparseArray<PackageState> longSparseArray2 = vpkg5;
                                PackageState pkgState3 = pkgState2;
                                int NASCS2 = in.readInt();
                                if (NASCS2 < 0) {
                                    this.mReadError = "bad package association count: " + NASCS2;
                                    return;
                                }
                                while (NASCS2 > 0) {
                                    int NASCS3 = NASCS2 - 1;
                                    String associationName2 = readCommonString(parcel, version);
                                    if (associationName2 == null) {
                                        this.mReadError = "bad package association name";
                                        return;
                                    }
                                    String processName2 = readCommonString(parcel, version);
                                    AssociationState asc2 = hadData ? pkgState3.mAssociations.get(associationName2) : null;
                                    if (asc2 == null) {
                                        NASCS = NASCS3;
                                        associationName = associationName2;
                                        asc = new AssociationState(this, pkgState3, associationName2, processName2, (ProcessState) null);
                                    } else {
                                        NASCS = NASCS3;
                                        associationName = associationName2;
                                        asc = asc2;
                                    }
                                    String errorMsg = asc.readFromParcel(this, parcel, version);
                                    if (errorMsg != null) {
                                        this.mReadError = errorMsg;
                                        return;
                                    } else {
                                        pkgState3.mAssociations.put(associationName, asc);
                                        NASCS2 = NASCS;
                                    }
                                }
                                uid3 = uid4;
                                NVERS = NVERS2;
                                NPROC2 = NPROC5;
                            }
                            NUID3 = NUID4;
                        }
                        NPKG = NPKG2;
                    }
                    int NPAGETYPES = in.readInt();
                    this.mPageTypeNodes.clear();
                    this.mPageTypeNodes.ensureCapacity(NPAGETYPES);
                    this.mPageTypeZones.clear();
                    this.mPageTypeZones.ensureCapacity(NPAGETYPES);
                    this.mPageTypeLabels.clear();
                    this.mPageTypeLabels.ensureCapacity(NPAGETYPES);
                    this.mPageTypeSizes.clear();
                    this.mPageTypeSizes.ensureCapacity(NPAGETYPES);
                    int i3 = 0;
                    while (true) {
                        int i4 = i3;
                        if (i4 < NPAGETYPES) {
                            this.mPageTypeNodes.add(Integer.valueOf(in.readInt()));
                            this.mPageTypeZones.add(in.readString());
                            this.mPageTypeLabels.add(in.readString());
                            this.mPageTypeSizes.add(in.createIntArray());
                            i3 = i4 + 1;
                        } else {
                            this.mIndexToCommonString = null;
                            return;
                        }
                    }
                }
            }
        }
    }

    public PackageState getPackageStateLocked(String packageName, int uid, long vers) {
        LongSparseArray<PackageState> vpkg = this.mPackages.get(packageName, uid);
        if (vpkg == null) {
            vpkg = new LongSparseArray<>();
            this.mPackages.put(packageName, uid, vpkg);
        }
        PackageState as = vpkg.get(vers);
        if (as != null) {
            return as;
        }
        PackageState as2 = new PackageState(this, packageName, uid, vers);
        vpkg.put(vers, as2);
        return as2;
    }

    public ProcessState getProcessStateLocked(String packageName, int uid, long vers, String processName) {
        return getProcessStateLocked(getPackageStateLocked(packageName, uid, vers), processName);
    }

    public ProcessState getProcessStateLocked(PackageState pkgState, String processName) {
        ProcessState commonProc;
        String str;
        ProcessState ps;
        PackageState packageState = pkgState;
        String str2 = processName;
        ProcessState ps2 = packageState.mProcesses.get(str2);
        if (ps2 != null) {
            return ps2;
        }
        ProcessState commonProc2 = this.mProcesses.get(str2, packageState.mUid);
        if (commonProc2 == null) {
            commonProc = new ProcessState(this, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, processName);
            this.mProcesses.put(str2, packageState.mUid, commonProc);
        } else {
            commonProc = commonProc2;
        }
        if (commonProc.isMultiPackage()) {
            str = str2;
            ps = new ProcessState(commonProc, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, processName, SystemClock.uptimeMillis());
        } else if (!packageState.mPackageName.equals(commonProc.getPackage()) || packageState.mVersionCode != commonProc.getVersion()) {
            commonProc.setMultiPackage(true);
            long now = SystemClock.uptimeMillis();
            PackageState commonPkgState = getPackageStateLocked(commonProc.getPackage(), packageState.mUid, commonProc.getVersion());
            if (commonPkgState != null) {
                ProcessState cloned = commonProc.clone(now);
                commonPkgState.mProcesses.put(commonProc.getName(), cloned);
                for (int i = commonPkgState.mServices.size() - 1; i >= 0; i--) {
                    ServiceState ss = commonPkgState.mServices.valueAt(i);
                    if (ss.getProcess() == commonProc) {
                        ss.setProcess(cloned);
                    }
                }
                int i2 = commonPkgState.mAssociations.size() - 1;
                while (true) {
                    int i3 = i2;
                    if (i3 < 0) {
                        break;
                    }
                    AssociationState as = commonPkgState.mAssociations.valueAt(i3);
                    if (as.getProcess() == commonProc) {
                        as.setProcess(cloned);
                    }
                    i2 = i3 - 1;
                }
            } else {
                Slog.w(TAG, "Cloning proc state: no package state " + commonProc.getPackage() + "/" + packageState.mUid + " for proc " + commonProc.getName());
            }
            str = str2;
            ps = new ProcessState(commonProc, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, processName, now);
        } else {
            ps = commonProc;
            str = str2;
        }
        packageState.mProcesses.put(str, ps);
        return ps;
    }

    public ServiceState getServiceStateLocked(String packageName, int uid, long vers, String processName, String className) {
        PackageState as = getPackageStateLocked(packageName, uid, vers);
        ServiceState ss = as.mServices.get(className);
        if (ss != null) {
            return ss;
        }
        ServiceState ss2 = new ServiceState(this, packageName, className, processName, processName != null ? getProcessStateLocked(packageName, uid, vers, processName) : null);
        as.mServices.put(className, ss2);
        return ss2;
    }

    public AssociationState getAssociationStateLocked(String packageName, int uid, long vers, String processName, String className) {
        PackageState pkgs = getPackageStateLocked(packageName, uid, vers);
        AssociationState as = pkgs.mAssociations.get(className);
        if (as != null) {
            return as;
        }
        AssociationState as2 = new AssociationState(this, pkgs, className, processName, processName != null ? getProcessStateLocked(packageName, uid, vers, processName) : null);
        pkgs.mAssociations.put(className, as2);
        return as2;
    }

    public void updateTrackingAssociationsLocked(int curSeq, long now) {
        for (int i = this.mTrackingAssociations.size() - 1; i >= 0; i--) {
            AssociationState.SourceState act = this.mTrackingAssociations.get(i);
            if (act.mProcStateSeq != curSeq || act.mProcState >= 9) {
                act.stopActive(now);
                act.mInTrackingList = false;
                act.mProcState = -1;
                this.mTrackingAssociations.remove(i);
            } else {
                ProcessState proc = act.getAssociationState().getProcess();
                if (proc != null) {
                    int procState = proc.getCombinedState() % 14;
                    if (act.mProcState == procState) {
                        act.startActive(now);
                    } else {
                        act.stopActive(now);
                        if (act.mProcState < procState) {
                            long nowUptime = SystemClock.uptimeMillis();
                            if (this.mNextInverseProcStateWarningUptime > nowUptime) {
                                this.mSkippedInverseProcStateWarningCount++;
                            } else {
                                Slog.w(TAG, "Tracking association " + act + " whose proc state " + act.mProcState + " is better than process " + proc + " proc state " + procState + " (" + this.mSkippedInverseProcStateWarningCount + " skipped)");
                                this.mSkippedInverseProcStateWarningCount = 0;
                                this.mNextInverseProcStateWarningUptime = 10000 + nowUptime;
                            }
                        }
                    }
                } else {
                    Slog.wtf(TAG, "Tracking association without process: " + act + " in " + act.getAssociationState());
                }
            }
        }
    }

    public void dumpLocked(PrintWriter pw, String reqPackage, long now, boolean dumpSummary, boolean dumpDetails, boolean dumpAll, boolean activeOnly, int section) {
        boolean sepNeeded;
        ProcessStats processStats;
        long j;
        PrintWriter printWriter;
        boolean z;
        String str;
        SparseArray<ProcessState> uids;
        int iu;
        SparseArray<LongSparseArray<PackageState>> uids2;
        int iu2;
        int uid;
        int ip;
        String pkgName;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2;
        int NSRVS;
        boolean sepNeeded2;
        int NASCS;
        PackageState pkgState;
        int iasc;
        int NSRVS2;
        int isvc;
        int NSRVS3;
        int NPROCS;
        long vers;
        PrintWriter printWriter2 = pw;
        String str2 = reqPackage;
        long j2 = now;
        boolean z2 = dumpAll;
        long totalTime = DumpUtils.dumpSingleTime((PrintWriter) null, (String) null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now);
        boolean sepNeeded3 = false;
        if (this.mSysMemUsage.getKeyCount() > 0) {
            printWriter2.println("System memory usage:");
            this.mSysMemUsage.dump(printWriter2, "  ", ALL_SCREEN_ADJ, ALL_MEM_ADJ);
            sepNeeded3 = true;
        }
        boolean printedHeader = false;
        int i = 0;
        if ((section & 14) != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap3 = this.mPackages.getMap();
            sepNeeded = sepNeeded3;
            int ip2 = 0;
            while (ip2 < pkgMap3.size()) {
                String pkgName2 = pkgMap3.keyAt(ip2);
                SparseArray<LongSparseArray<PackageState>> uids3 = pkgMap3.valueAt(ip2);
                boolean printedHeader2 = printedHeader;
                int iu3 = 0;
                while (true) {
                    int iu4 = iu3;
                    if (iu4 >= uids3.size()) {
                        break;
                    }
                    int uid2 = uids3.keyAt(iu4);
                    LongSparseArray<PackageState> vpkgs = uids3.valueAt(iu4);
                    int iv = 0;
                    while (true) {
                        iu = iu4;
                        if (iv >= vpkgs.size()) {
                            break;
                        }
                        long vers2 = vpkgs.keyAt(iv);
                        PackageState pkgState2 = vpkgs.valueAt(iv);
                        int iv2 = iv;
                        int NPROCS2 = pkgState2.mProcesses.size();
                        LongSparseArray<PackageState> vpkgs2 = vpkgs;
                        int NSRVS4 = pkgState2.mServices.size();
                        SparseArray<LongSparseArray<PackageState>> uids4 = uids3;
                        int NASCS2 = pkgState2.mAssociations.size();
                        boolean pkgMatch = str2 == null || str2.equals(pkgName2);
                        boolean onlyAssociations = false;
                        if (!pkgMatch) {
                            boolean procMatch = false;
                            int iproc = 0;
                            while (true) {
                                pkgMap2 = pkgMap3;
                                int iproc2 = iproc;
                                if (iproc2 >= NPROCS2) {
                                    ip = ip2;
                                    break;
                                }
                                ip = ip2;
                                if (str2.equals(pkgState2.mProcesses.valueAt(iproc2).getName())) {
                                    procMatch = true;
                                    break;
                                }
                                iproc = iproc2 + 1;
                                pkgMap3 = pkgMap2;
                                ip2 = ip;
                            }
                            if (!procMatch) {
                                int iasc2 = 0;
                                while (true) {
                                    if (iasc2 >= NASCS2) {
                                        break;
                                    } else if (pkgState2.mAssociations.valueAt(iasc2).hasProcessOrPackage(str2)) {
                                        onlyAssociations = true;
                                        break;
                                    } else {
                                        iasc2++;
                                    }
                                }
                                if (!onlyAssociations) {
                                    uid = uid2;
                                    iu2 = iu;
                                    uids2 = uids4;
                                    pkgMap = pkgMap2;
                                    pkgName = pkgName2;
                                    iv = iv2 + 1;
                                    pkgMap3 = pkgMap;
                                    vpkgs = vpkgs2;
                                    pkgName2 = pkgName;
                                    ip2 = ip;
                                    uid2 = uid;
                                    iu4 = iu2;
                                    uids3 = uids2;
                                    long j3 = now;
                                }
                            }
                        } else {
                            ip = ip2;
                            pkgMap2 = pkgMap3;
                        }
                        boolean ip3 = onlyAssociations;
                        if (NPROCS2 > 0 || NSRVS4 > 0 || NASCS2 > 0) {
                            if (!printedHeader2) {
                                if (sepNeeded) {
                                    pw.println();
                                }
                                printWriter2.println("Per-Package Stats:");
                                printedHeader2 = true;
                                sepNeeded = true;
                            }
                            printWriter2.print("  * ");
                            printWriter2.print(pkgName2);
                            printWriter2.print(" / ");
                            UserHandle.formatUid(printWriter2, uid2);
                            printWriter2.print(" / v");
                            printWriter2.print(vers2);
                            printWriter2.println(SettingsStringUtil.DELIMITER);
                        }
                        boolean sepNeeded4 = sepNeeded;
                        boolean printedHeader3 = printedHeader2;
                        if ((section != false && true) && !ip3) {
                            if (!dumpSummary) {
                                sepNeeded2 = sepNeeded4;
                                NPROCS = NPROCS2;
                                NSRVS = NSRVS4;
                                uid = uid2;
                                pkgState = pkgState2;
                                NASCS = NASCS2;
                                iu2 = iu;
                                uids2 = uids4;
                                pkgMap = pkgMap2;
                                pkgName = pkgName2;
                            } else if (z2) {
                                long j4 = vers2;
                                sepNeeded2 = sepNeeded4;
                                NPROCS = NPROCS2;
                                NSRVS = NSRVS4;
                                uid = uid2;
                                pkgState = pkgState2;
                                NASCS = NASCS2;
                                iu2 = iu;
                                uids2 = uids4;
                                pkgMap = pkgMap2;
                                pkgName = pkgName2;
                            } else {
                                ArrayList<ProcessState> procs = new ArrayList<>();
                                int iproc3 = 0;
                                while (iproc3 < NPROCS2) {
                                    ProcessState proc = pkgState2.mProcesses.valueAt(iproc3);
                                    if (!pkgMatch) {
                                        vers = vers2;
                                        if (!str2.equals(proc.getName())) {
                                            iproc3++;
                                            vers2 = vers;
                                        }
                                    } else {
                                        vers = vers2;
                                    }
                                    if (!activeOnly || proc.isInUse()) {
                                        procs.add(proc);
                                        iproc3++;
                                        vers2 = vers;
                                    } else {
                                        iproc3++;
                                        vers2 = vers;
                                    }
                                }
                                sepNeeded2 = sepNeeded4;
                                int i2 = NPROCS2;
                                NSRVS = NSRVS4;
                                uid = uid2;
                                pkgState = pkgState2;
                                iu2 = iu;
                                NASCS = NASCS2;
                                uids2 = uids4;
                                ArrayList<ProcessState> arrayList = procs;
                                pkgName = pkgName2;
                                pkgMap = pkgMap2;
                                DumpUtils.dumpProcessSummaryLocked(pw, "      ", "Prc ", procs, ALL_SCREEN_ADJ, ALL_MEM_ADJ, NON_CACHED_PROC_STATES, now, totalTime);
                            }
                            int iproc4 = 0;
                            while (true) {
                                int iproc5 = iproc4;
                                if (iproc5 >= NPROCS) {
                                    break;
                                }
                                ProcessState proc2 = pkgState.mProcesses.valueAt(iproc5);
                                if (pkgMatch || str2.equals(proc2.getName())) {
                                    if (!activeOnly || proc2.isInUse()) {
                                        printWriter2.print("      Process ");
                                        printWriter2.print(pkgState.mProcesses.keyAt(iproc5));
                                        if (proc2.getCommonProcess().isMultiPackage()) {
                                            printWriter2.print(" (multi, ");
                                        } else {
                                            printWriter2.print(" (unique, ");
                                        }
                                        printWriter2.print(proc2.getDurationsBucketCount());
                                        printWriter2.print(" entries)");
                                        printWriter2.println(SettingsStringUtil.DELIMITER);
                                        ProcessState processState = proc2;
                                        PrintWriter printWriter3 = pw;
                                        ProcessState proc3 = proc2;
                                        long j5 = now;
                                        processState.dumpProcessState(printWriter3, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j5);
                                        proc3.dumpPss(printWriter3, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j5);
                                        proc3.dumpInternalLocked(printWriter2, "        ", z2);
                                    } else {
                                        printWriter2.print("      (Not active: ");
                                        printWriter2.print(pkgState.mProcesses.keyAt(iproc5));
                                        printWriter2.println(")");
                                    }
                                }
                                iproc4 = iproc5 + 1;
                            }
                        } else {
                            sepNeeded2 = sepNeeded4;
                            int i3 = NPROCS2;
                            NSRVS = NSRVS4;
                            uid = uid2;
                            pkgState = pkgState2;
                            NASCS = NASCS2;
                            iu2 = iu;
                            uids2 = uids4;
                            pkgMap = pkgMap2;
                            pkgName = pkgName2;
                        }
                        if ((section & 4) == 0 || ip3) {
                        } else {
                            int isvc2 = 0;
                            while (true) {
                                int isvc3 = isvc2;
                                NSRVS2 = NSRVS;
                                if (isvc3 >= NSRVS2) {
                                    break;
                                }
                                ServiceState svc = pkgState.mServices.valueAt(isvc3);
                                if (pkgMatch || str2.equals(svc.getProcessName())) {
                                    if (!activeOnly || svc.isInUse()) {
                                        if (z2) {
                                            printWriter2.print("      Service ");
                                        } else {
                                            printWriter2.print("      * Svc ");
                                        }
                                        printWriter2.print(pkgState.mServices.keyAt(isvc3));
                                        printWriter2.println(SettingsStringUtil.DELIMITER);
                                        printWriter2.print("        Process: ");
                                        printWriter2.println(svc.getProcessName());
                                        isvc = isvc3;
                                        NSRVS3 = NSRVS2;
                                        svc.dumpStats(pw, "        ", "          ", "    ", now, totalTime, dumpSummary, dumpAll);
                                        isvc2 = isvc + 1;
                                        NSRVS = NSRVS3;
                                    } else {
                                        printWriter2.print("      (Not active service: ");
                                        printWriter2.print(pkgState.mServices.keyAt(isvc3));
                                        printWriter2.println(")");
                                    }
                                }
                                isvc = isvc3;
                                NSRVS3 = NSRVS2;
                                isvc2 = isvc + 1;
                                NSRVS = NSRVS3;
                            }
                        }
                        if ((section & 8) != 0) {
                            int iasc3 = 0;
                            while (true) {
                                int iasc4 = iasc3;
                                if (iasc4 >= NASCS) {
                                    break;
                                }
                                AssociationState asc = pkgState.mAssociations.valueAt(iasc4);
                                if (pkgMatch || str2.equals(asc.getProcessName()) || (ip3 && asc.hasProcessOrPackage(str2))) {
                                    if (!activeOnly || asc.isInUse()) {
                                        if (z2) {
                                            printWriter2.print("      Association ");
                                        } else {
                                            printWriter2.print("      * Asc ");
                                        }
                                        printWriter2.print(pkgState.mAssociations.keyAt(iasc4));
                                        printWriter2.println(SettingsStringUtil.DELIMITER);
                                        printWriter2.print("        Process: ");
                                        printWriter2.println(asc.getProcessName());
                                        iasc = iasc4;
                                        AssociationState associationState = asc;
                                        asc.dumpStats(pw, "        ", "          ", "    ", now, totalTime, ip3 ? str2 : null, dumpDetails, dumpAll);
                                        iasc3 = iasc + 1;
                                    } else {
                                        printWriter2.print("      (Not active association: ");
                                        printWriter2.print(pkgState.mAssociations.keyAt(iasc4));
                                        printWriter2.println(")");
                                    }
                                }
                                iasc = iasc4;
                                iasc3 = iasc + 1;
                            }
                        }
                        printedHeader2 = printedHeader3;
                        sepNeeded = sepNeeded2;
                        iv = iv2 + 1;
                        pkgMap3 = pkgMap;
                        vpkgs = vpkgs2;
                        pkgName2 = pkgName;
                        ip2 = ip;
                        uid2 = uid;
                        iu4 = iu2;
                        uids3 = uids2;
                        long j32 = now;
                    }
                    SparseArray<LongSparseArray<PackageState>> sparseArray = uids3;
                    String str3 = pkgName2;
                    int iu5 = iu;
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap = pkgMap3;
                    iu3 = iu5 + 1;
                    long j6 = now;
                }
                ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap2 = pkgMap3;
                ip2++;
                printedHeader = printedHeader2;
                long j7 = now;
            }
        } else {
            sepNeeded = sepNeeded3;
        }
        if (section != false && true) {
            processStats = this;
            ArrayMap<String, SparseArray<ProcessState>> procMap = processStats.mProcesses.getMap();
            int numShownProcs = 0;
            int numTotalProcs = 0;
            boolean sepNeeded5 = false;
            int ip4 = 0;
            while (ip4 < procMap.size()) {
                String procName = procMap.keyAt(ip4);
                SparseArray<ProcessState> uids5 = procMap.valueAt(ip4);
                boolean printedHeader4 = sepNeeded5;
                boolean sepNeeded6 = sepNeeded;
                int numShownProcs2 = numShownProcs;
                int iu6 = 0;
                while (iu6 < uids5.size()) {
                    int uid3 = uids5.keyAt(iu6);
                    int numTotalProcs2 = numTotalProcs + 1;
                    ProcessState proc4 = uids5.valueAt(iu6);
                    if (proc4.hasAnyData() && proc4.isMultiPackage() && (str2 == null || str2.equals(procName) || str2.equals(proc4.getPackage()))) {
                        int numShownProcs3 = numShownProcs2 + 1;
                        if (sepNeeded6) {
                            pw.println();
                        }
                        if (!printedHeader4) {
                            printWriter2.println("Multi-Package Common Processes:");
                            printedHeader4 = true;
                        }
                        boolean printedHeader5 = printedHeader4;
                        if (!activeOnly || proc4.isInUse()) {
                            printWriter2.print("  * ");
                            printWriter2.print(procName);
                            printWriter2.print(" / ");
                            UserHandle.formatUid(printWriter2, uid3);
                            printWriter2.print(" (");
                            printWriter2.print(proc4.getDurationsBucketCount());
                            printWriter2.print(" entries)");
                            printWriter2.println(SettingsStringUtil.DELIMITER);
                            PrintWriter printWriter4 = pw;
                            ProcessState proc5 = proc4;
                            uids = uids5;
                            int i4 = uid3;
                            long j8 = now;
                            proc4.dumpProcessState(printWriter4, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j8);
                            proc5.dumpPss(printWriter4, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j8);
                            proc5.dumpInternalLocked(printWriter2, "        ", z2);
                        } else {
                            printWriter2.print("      (Not active: ");
                            printWriter2.print(procName);
                            printWriter2.println(")");
                            uids = uids5;
                        }
                        numShownProcs2 = numShownProcs3;
                        sepNeeded6 = true;
                        printedHeader4 = printedHeader5;
                    } else {
                        uids = uids5;
                    }
                    iu6++;
                    numTotalProcs = numTotalProcs2;
                    uids5 = uids;
                }
                ip4++;
                numShownProcs = numShownProcs2;
                sepNeeded = sepNeeded6;
                sepNeeded5 = printedHeader4;
            }
            printWriter2.print("  Total procs: ");
            printWriter2.print(numShownProcs);
            printWriter2.print(" shown of ");
            printWriter2.print(numTotalProcs);
            printWriter2.println(" total");
            boolean z3 = sepNeeded5;
        } else {
            processStats = this;
            boolean z4 = printedHeader;
        }
        if (z2) {
            if (sepNeeded) {
                pw.println();
            }
            if (processStats.mTrackingAssociations.size() > 0) {
                pw.println();
                printWriter2.println("Tracking associations:");
                while (true) {
                    int i5 = i;
                    if (i5 >= processStats.mTrackingAssociations.size()) {
                        break;
                    }
                    AssociationState.SourceState src = processStats.mTrackingAssociations.get(i5);
                    AssociationState asc2 = src.getAssociationState();
                    printWriter2.print("  #");
                    printWriter2.print(i5);
                    printWriter2.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                    printWriter2.print(asc2.getProcessName());
                    printWriter2.print("/");
                    UserHandle.formatUid(printWriter2, asc2.getUid());
                    printWriter2.print(" <- ");
                    printWriter2.print(src.getProcessName());
                    printWriter2.print("/");
                    UserHandle.formatUid(printWriter2, src.getUid());
                    printWriter2.println(SettingsStringUtil.DELIMITER);
                    printWriter2.print("    Tracking for: ");
                    TimeUtils.formatDuration(now - src.mTrackingUptime, printWriter2);
                    pw.println();
                    printWriter2.print("    Component: ");
                    printWriter2.print(new ComponentName(asc2.getPackage(), asc2.getName()).flattenToShortString());
                    pw.println();
                    printWriter2.print("    Proc state: ");
                    if (src.mProcState != -1) {
                        printWriter2.print(DumpUtils.STATE_NAMES[src.mProcState]);
                    } else {
                        printWriter2.print("--");
                    }
                    printWriter2.print(" #");
                    printWriter2.println(src.mProcStateSeq);
                    printWriter2.print("    Process: ");
                    printWriter2.println(asc2.getProcess());
                    if (src.mActiveCount > 0) {
                        printWriter2.print("    Active count ");
                        printWriter2.print(src.mActiveCount);
                        printWriter2.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                        asc2.dumpActiveDurationSummary(pw, src, totalTime, now, dumpAll);
                        pw.println();
                    }
                    i = i5 + 1;
                }
            }
            j = now;
            sepNeeded = true;
        } else {
            j = now;
        }
        if (sepNeeded) {
            pw.println();
        }
        if (dumpSummary) {
            printWriter2.println("Process summary:");
            long j9 = j;
            str = str2;
            z = z2;
            printWriter = printWriter2;
            dumpSummaryLocked(pw, reqPackage, now, activeOnly);
        } else {
            str = str2;
            z = z2;
            printWriter = printWriter2;
            processStats.dumpTotalsLocked(printWriter, j);
        }
        if (z) {
            pw.println();
            printWriter.println("Internal state:");
            printWriter.print("  mRunning=");
            printWriter.println(processStats.mRunning);
        }
        if (str == null) {
            dumpFragmentationLocked(pw);
        }
    }

    public void dumpSummaryLocked(PrintWriter pw, String reqPackage, long now, boolean activeOnly) {
        PrintWriter printWriter = pw;
        dumpFilteredSummaryLocked(printWriter, (String) null, "  ", (String) null, ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, NON_CACHED_PROC_STATES, now, DumpUtils.dumpSingleTime((PrintWriter) null, (String) null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now), reqPackage, activeOnly);
        pw.println();
        dumpTotalsLocked(pw, now);
    }

    private void dumpFragmentationLocked(PrintWriter pw) {
        pw.println();
        pw.println("Available pages by page size:");
        int NPAGETYPES = this.mPageTypeLabels.size();
        for (int i = 0; i < NPAGETYPES; i++) {
            pw.format("Node %3d Zone %7s  %14s ", new Object[]{this.mPageTypeNodes.get(i), this.mPageTypeZones.get(i), this.mPageTypeLabels.get(i)});
            int[] sizes = this.mPageTypeSizes.get(i);
            int N = sizes == null ? 0 : sizes.length;
            for (int j = 0; j < N; j++) {
                pw.format("%6d", new Object[]{Integer.valueOf(sizes[j])});
            }
            pw.println();
        }
    }

    /* access modifiers changed from: package-private */
    public long printMemoryCategory(PrintWriter pw, String prefix, String label, double memWeight, long totalTime, long curTotalMem, int samples) {
        if (memWeight == 0.0d) {
            return curTotalMem;
        }
        long mem = (long) ((1024.0d * memWeight) / ((double) totalTime));
        pw.print(prefix);
        pw.print(label);
        pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
        DebugUtils.printSizeValue(pw, mem);
        pw.print(" (");
        pw.print(samples);
        pw.print(" samples)");
        pw.println();
        return curTotalMem + mem;
    }

    /* access modifiers changed from: package-private */
    public void dumpTotalsLocked(PrintWriter pw, long now) {
        int i;
        PrintWriter printWriter = pw;
        printWriter.println("Run time Stats:");
        DumpUtils.dumpSingleTime(pw, "  ", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now);
        pw.println();
        printWriter.println("Memory usage:");
        TotalMemoryUseCollection totalMem = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMem, now);
        PrintWriter printWriter2 = pw;
        int i2 = 0;
        long totalPss = printMemoryCategory(printWriter2, "  ", "Native ", totalMem.sysMemNativeWeight, totalMem.totalTime, printMemoryCategory(printWriter2, "  ", "Kernel ", totalMem.sysMemKernelWeight, totalMem.totalTime, 0, totalMem.sysMemSamples), totalMem.sysMemSamples);
        while (true) {
            int i3 = i2;
            if (i3 >= 14) {
                break;
            }
            if (i3 != 6) {
                i = i3;
                totalPss = printMemoryCategory(pw, "  ", DumpUtils.STATE_NAMES[i3], totalMem.processStateWeight[i3], totalMem.totalTime, totalPss, totalMem.processStateSamples[i3]);
            } else {
                i = i3;
            }
            i2 = i + 1;
        }
        PrintWriter printWriter3 = pw;
        long totalPss2 = printMemoryCategory(printWriter3, "  ", "Z-Ram  ", totalMem.sysMemZRamWeight, totalMem.totalTime, printMemoryCategory(printWriter3, "  ", "Free   ", totalMem.sysMemFreeWeight, totalMem.totalTime, printMemoryCategory(printWriter3, "  ", "Cached ", totalMem.sysMemCachedWeight, totalMem.totalTime, totalPss, totalMem.sysMemSamples), totalMem.sysMemSamples), totalMem.sysMemSamples);
        printWriter.print("  TOTAL  : ");
        DebugUtils.printSizeValue(printWriter, totalPss2);
        pw.println();
        long j = totalPss2;
        printMemoryCategory(printWriter3, "  ", DumpUtils.STATE_NAMES[6], totalMem.processStateWeight[6], totalMem.totalTime, totalPss2, totalMem.processStateSamples[6]);
        pw.println();
        printWriter.println("PSS collection stats:");
        printWriter.print("  Internal Single: ");
        printWriter.print(this.mInternalSinglePssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalSinglePssTime, printWriter);
        pw.println();
        printWriter.print("  Internal All Procs (Memory Change): ");
        printWriter.print(this.mInternalAllMemPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllMemPssTime, printWriter);
        pw.println();
        printWriter.print("  Internal All Procs (Polling): ");
        printWriter.print(this.mInternalAllPollPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllPollPssTime, printWriter);
        pw.println();
        printWriter.print("  External: ");
        printWriter.print(this.mExternalPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mExternalPssTime, printWriter);
        pw.println();
        printWriter.print("  External Slow: ");
        printWriter.print(this.mExternalSlowPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mExternalSlowPssTime, printWriter);
        pw.println();
        pw.println();
        printWriter.print("          Start time: ");
        printWriter.print(DateFormat.format((CharSequence) "yyyy-MM-dd HH:mm:ss", this.mTimePeriodStartClock));
        pw.println();
        printWriter.print("        Total uptime: ");
        TimeUtils.formatDuration((this.mRunning ? SystemClock.uptimeMillis() : this.mTimePeriodEndUptime) - this.mTimePeriodStartUptime, printWriter);
        pw.println();
        printWriter.print("  Total elapsed time: ");
        TimeUtils.formatDuration((this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime) - this.mTimePeriodStartRealtime, printWriter);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            printWriter.print(" (shutdown)");
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            printWriter.print(" (sysprops)");
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            printWriter.print(" (complete)");
            partial = false;
        }
        if (partial) {
            printWriter.print(" (partial)");
        }
        if (this.mHasSwappedOutPss) {
            printWriter.print(" (swapped-out-pss)");
        }
        printWriter.print(' ');
        printWriter.print(this.mRuntime);
        pw.println();
    }

    /* access modifiers changed from: package-private */
    public void dumpFilteredSummaryLocked(PrintWriter pw, String header, String prefix, String prcLabel, int[] screenStates, int[] memStates, int[] procStates, int[] sortProcStates, long now, long totalTime, String reqPackage, boolean activeOnly) {
        ArrayList<ProcessState> procs = collectProcessesLocked(screenStates, memStates, procStates, sortProcStates, now, reqPackage, activeOnly);
        if (procs.size() > 0) {
            if (header != null) {
                pw.println();
                pw.println(header);
            }
            DumpUtils.dumpProcessSummaryLocked(pw, prefix, prcLabel, procs, screenStates, memStates, sortProcStates, now, totalTime);
        }
    }

    public ArrayList<ProcessState> collectProcessesLocked(int[] screenStates, int[] memStates, int[] procStates, int[] sortProcStates, long now, String reqPackage, boolean activeOnly) {
        String str = reqPackage;
        ArraySet<ProcessState> foundProcs = new ArraySet<>();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
        int ip = 0;
        while (ip < pkgMap.size()) {
            String pkgName = pkgMap.keyAt(ip);
            SparseArray<LongSparseArray<PackageState>> procs = pkgMap.valueAt(ip);
            int iu = 0;
            while (iu < procs.size()) {
                LongSparseArray<PackageState> vpkgs = procs.valueAt(iu);
                int NVERS = vpkgs.size();
                int iv = 0;
                while (iv < NVERS) {
                    PackageState state = vpkgs.valueAt(iv);
                    int NPROCS = state.mProcesses.size();
                    boolean pkgMatch = str == null || str.equals(pkgName);
                    int iproc = 0;
                    while (iproc < NPROCS) {
                        ProcessState proc = state.mProcesses.valueAt(iproc);
                        if ((pkgMatch || str.equals(proc.getName())) && (!activeOnly || proc.isInUse())) {
                            foundProcs.add(proc.getCommonProcess());
                        }
                        iproc++;
                        str = reqPackage;
                    }
                    iv++;
                    str = reqPackage;
                }
                iu++;
                str = reqPackage;
            }
            ip++;
            str = reqPackage;
        }
        ArrayList<ProcessState> outProcs = new ArrayList<>(foundProcs.size());
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < foundProcs.size()) {
                ProcessState proc2 = foundProcs.valueAt(i2);
                if (proc2.computeProcessTimeLocked(screenStates, memStates, procStates, now) > 0) {
                    outProcs.add(proc2);
                    if (procStates != sortProcStates) {
                        proc2.computeProcessTimeLocked(screenStates, memStates, sortProcStates, now);
                    }
                } else {
                    int[] iArr = procStates;
                    int[] iArr2 = sortProcStates;
                }
                i = i2 + 1;
            } else {
                int[] iArr3 = procStates;
                int[] iArr4 = sortProcStates;
                Collections.sort(outProcs, ProcessState.COMPARATOR);
                return outProcs;
            }
        }
    }

    public void dumpCheckinLocked(PrintWriter pw, String reqPackage, int section) {
        ProcessStats processStats;
        PrintWriter printWriter = pw;
        String str = reqPackage;
        long now = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
        printWriter.println("vers,5");
        printWriter.print("period,");
        printWriter.print(this.mTimePeriodStartClockStr);
        printWriter.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter.print(this.mTimePeriodStartRealtime);
        printWriter.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter.print(this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            printWriter.print(",shutdown");
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            printWriter.print(",sysprops");
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            printWriter.print(",complete");
            partial = false;
        }
        if (partial) {
            printWriter.print(",partial");
        }
        if (this.mHasSwappedOutPss) {
            printWriter.print(",swapped-out-pss");
        }
        pw.println();
        printWriter.print("config,");
        printWriter.println(this.mRuntime);
        if ((section & 14) != 0) {
            int ip = 0;
            while (true) {
                int ip2 = ip;
                if (ip2 >= pkgMap.size()) {
                    break;
                }
                String pkgName = pkgMap.keyAt(ip2);
                if (str == null || str.equals(pkgName)) {
                    SparseArray<LongSparseArray<PackageState>> uids = pkgMap.valueAt(ip2);
                    int iu = 0;
                    while (true) {
                        int iu2 = iu;
                        if (iu2 >= uids.size()) {
                            break;
                        }
                        int uid = uids.keyAt(iu2);
                        LongSparseArray<PackageState> vpkgs = uids.valueAt(iu2);
                        int iv = 0;
                        while (true) {
                            int iv2 = iv;
                            if (iv2 >= vpkgs.size()) {
                                break;
                            }
                            long vers = vpkgs.keyAt(iv2);
                            PackageState pkgState = vpkgs.valueAt(iv2);
                            int NPROCS = pkgState.mProcesses.size();
                            int NSRVS = pkgState.mServices.size();
                            int iv3 = iv2;
                            int NASCS = pkgState.mAssociations.size();
                            if ((section & 2) != 0) {
                                int iproc = 0;
                                while (true) {
                                    int iproc2 = iproc;
                                    if (iproc2 >= NPROCS) {
                                        break;
                                    }
                                    pkgState.mProcesses.valueAt(iproc2).dumpPackageProcCheckin(pw, pkgName, uid, vers, pkgState.mProcesses.keyAt(iproc2), now);
                                    iproc = iproc2 + 1;
                                    pkgName = pkgName;
                                    NSRVS = NSRVS;
                                    pkgState = pkgState;
                                    ip2 = ip2;
                                    NPROCS = NPROCS;
                                    pkgMap = pkgMap;
                                    vpkgs = vpkgs;
                                    uids = uids;
                                    iu2 = iu2;
                                    NASCS = NASCS;
                                    PrintWriter printWriter2 = pw;
                                    String str2 = reqPackage;
                                }
                            }
                            int NASCS2 = NASCS;
                            LongSparseArray<PackageState> vpkgs2 = vpkgs;
                            SparseArray<LongSparseArray<PackageState>> uids2 = uids;
                            int iu3 = iu2;
                            String pkgName2 = pkgName;
                            int NSRVS2 = NSRVS;
                            int ip3 = ip2;
                            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = pkgMap;
                            PackageState pkgState2 = pkgState;
                            if ((section & 4) != 0) {
                                int isvc = 0;
                                while (true) {
                                    int isvc2 = isvc;
                                    if (isvc2 >= NSRVS2) {
                                        break;
                                    }
                                    pkgState2.mServices.valueAt(isvc2).dumpTimesCheckin(pw, pkgName2, uid, vers, DumpUtils.collapseString(pkgName2, pkgState2.mServices.keyAt(isvc2)), now);
                                    isvc = isvc2 + 1;
                                }
                            }
                            if ((section & 8) != 0) {
                                int iasc = 0;
                                while (true) {
                                    int iasc2 = iasc;
                                    int NASCS3 = NASCS2;
                                    if (iasc2 >= NASCS3) {
                                        break;
                                    }
                                    NASCS2 = NASCS3;
                                    pkgState2.mAssociations.valueAt(iasc2).dumpTimesCheckin(pw, pkgName2, uid, vers, DumpUtils.collapseString(pkgName2, pkgState2.mAssociations.keyAt(iasc2)), now);
                                    iasc = iasc2 + 1;
                                }
                            }
                            iv = iv3 + 1;
                            pkgName = pkgName2;
                            ip2 = ip3;
                            pkgMap = pkgMap2;
                            vpkgs = vpkgs2;
                            uids = uids2;
                            iu2 = iu3;
                            PrintWriter printWriter3 = pw;
                            String str3 = reqPackage;
                        }
                        String str4 = pkgName;
                        int i = ip2;
                        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap = pkgMap;
                        iu = iu2 + 1;
                        PrintWriter printWriter4 = pw;
                        String str5 = reqPackage;
                    }
                }
                ip = ip2 + 1;
                pkgMap = pkgMap;
                PrintWriter printWriter5 = pw;
                str = reqPackage;
            }
        }
        if ((section & 1) != 0) {
            processStats = this;
            ArrayMap<String, SparseArray<ProcessState>> procMap = processStats.mProcesses.getMap();
            int ip4 = 0;
            while (true) {
                int ip5 = ip4;
                if (ip5 >= procMap.size()) {
                    break;
                }
                String procName = procMap.keyAt(ip5);
                SparseArray<ProcessState> uids3 = procMap.valueAt(ip5);
                int iu4 = 0;
                while (true) {
                    int iu5 = iu4;
                    if (iu5 >= uids3.size()) {
                        break;
                    }
                    uids3.valueAt(iu5).dumpProcCheckin(pw, procName, uids3.keyAt(iu5), now);
                    iu4 = iu5 + 1;
                }
                ip4 = ip5 + 1;
            }
        } else {
            processStats = this;
        }
        PrintWriter printWriter6 = pw;
        printWriter6.print("total");
        DumpUtils.dumpAdjTimesCheckin(pw, SmsManager.REGEX_PREFIX_DELIMITER, processStats.mMemFactorDurations, processStats.mMemFactor, processStats.mStartTime, now);
        pw.println();
        int sysMemUsageCount = processStats.mSysMemUsage.getKeyCount();
        if (sysMemUsageCount > 0) {
            printWriter6.print("sysmemusage");
            for (int i2 = 0; i2 < sysMemUsageCount; i2++) {
                int key = processStats.mSysMemUsage.getKeyAt(i2);
                int type = SparseMappingTable.getIdFromKey(key);
                printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
                DumpUtils.printProcStateTag(printWriter6, type);
                for (int j = 0; j < 16; j++) {
                    if (j > 1) {
                        printWriter6.print(SettingsStringUtil.DELIMITER);
                    }
                    printWriter6.print(processStats.mSysMemUsage.getValue(key, j));
                }
            }
        }
        pw.println();
        TotalMemoryUseCollection totalMem = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        processStats.computeTotalMemoryUse(totalMem, now);
        printWriter6.print("weights,");
        printWriter6.print(totalMem.totalTime);
        printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter6.print(totalMem.sysMemCachedWeight);
        printWriter6.print(SettingsStringUtil.DELIMITER);
        printWriter6.print(totalMem.sysMemSamples);
        printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter6.print(totalMem.sysMemFreeWeight);
        printWriter6.print(SettingsStringUtil.DELIMITER);
        printWriter6.print(totalMem.sysMemSamples);
        printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter6.print(totalMem.sysMemZRamWeight);
        printWriter6.print(SettingsStringUtil.DELIMITER);
        printWriter6.print(totalMem.sysMemSamples);
        printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter6.print(totalMem.sysMemKernelWeight);
        printWriter6.print(SettingsStringUtil.DELIMITER);
        printWriter6.print(totalMem.sysMemSamples);
        printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
        printWriter6.print(totalMem.sysMemNativeWeight);
        printWriter6.print(SettingsStringUtil.DELIMITER);
        printWriter6.print(totalMem.sysMemSamples);
        for (int i3 = 0; i3 < 14; i3++) {
            printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
            printWriter6.print(totalMem.processStateWeight[i3]);
            printWriter6.print(SettingsStringUtil.DELIMITER);
            printWriter6.print(totalMem.processStateSamples[i3]);
        }
        pw.println();
        int NPAGETYPES = processStats.mPageTypeLabels.size();
        for (int i4 = 0; i4 < NPAGETYPES; i4++) {
            printWriter6.print("availablepages,");
            printWriter6.print(processStats.mPageTypeLabels.get(i4));
            printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
            printWriter6.print(processStats.mPageTypeZones.get(i4));
            printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
            int[] sizes = processStats.mPageTypeSizes.get(i4);
            int N = sizes == null ? 0 : sizes.length;
            for (int j2 = 0; j2 < N; j2++) {
                if (j2 != 0) {
                    printWriter6.print(SmsManager.REGEX_PREFIX_DELIMITER);
                }
                printWriter6.print(sizes[j2]);
            }
            pw.println();
        }
    }

    public void writeToProto(ProtoOutputStream proto, long now, int section) {
        ProtoOutputStream protoOutputStream = proto;
        protoOutputStream.write(1112396529665L, this.mTimePeriodStartRealtime);
        protoOutputStream.write(1112396529666L, this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        protoOutputStream.write(1112396529667L, this.mTimePeriodStartUptime);
        protoOutputStream.write(1112396529668L, this.mTimePeriodEndUptime);
        protoOutputStream.write(1138166333445L, this.mRuntime);
        protoOutputStream.write(1133871366150L, this.mHasSwappedOutPss);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            protoOutputStream.write(2259152797703L, 3);
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            protoOutputStream.write(2259152797703L, 4);
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            protoOutputStream.write(2259152797703L, 1);
            partial = false;
        }
        if (partial) {
            protoOutputStream.write(2259152797703L, 2);
        }
        int NPAGETYPES = this.mPageTypeLabels.size();
        for (int i = 0; i < NPAGETYPES; i++) {
            long token = protoOutputStream.start(2246267895818L);
            protoOutputStream.write(1120986464257L, this.mPageTypeNodes.get(i).intValue());
            protoOutputStream.write(1138166333442L, this.mPageTypeZones.get(i));
            protoOutputStream.write(1138166333443L, this.mPageTypeLabels.get(i));
            int[] sizes = this.mPageTypeSizes.get(i);
            int N = sizes == null ? 0 : sizes.length;
            for (int j = 0; j < N; j++) {
                protoOutputStream.write(2220498092036L, sizes[j]);
            }
            protoOutputStream.end(token);
        }
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        if ((section & 1) != 0) {
            int ip = 0;
            while (true) {
                int ip2 = ip;
                if (ip2 >= procMap.size()) {
                    break;
                }
                String procName = procMap.keyAt(ip2);
                SparseArray<ProcessState> uids = procMap.valueAt(ip2);
                int iu = 0;
                while (true) {
                    int iu2 = iu;
                    if (iu2 >= uids.size()) {
                        break;
                    }
                    uids.valueAt(iu2).writeToProto(proto, 2246267895816L, procName, uids.keyAt(iu2), now);
                    iu = iu2 + 1;
                    uids = uids;
                }
                ip = ip2 + 1;
            }
        }
        if ((section & 14) != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
            int ip3 = 0;
            while (true) {
                int ip4 = ip3;
                if (ip4 < pkgMap.size()) {
                    SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip4);
                    int iu3 = 0;
                    while (true) {
                        int iu4 = iu3;
                        if (iu4 >= uids2.size()) {
                            break;
                        }
                        LongSparseArray<PackageState> vers = uids2.valueAt(iu4);
                        int iv = 0;
                        while (true) {
                            int iv2 = iv;
                            if (iv2 >= vers.size()) {
                                break;
                            }
                            vers.valueAt(iv2).writeToProto(proto, 2246267895817L, now, section);
                            iv = iv2 + 1;
                            iu4 = iu4;
                            vers = vers;
                            uids2 = uids2;
                        }
                        SparseArray<LongSparseArray<PackageState>> sparseArray = uids2;
                        iu3 = iu4 + 1;
                    }
                    ip3 = ip4 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public static final class ProcessStateHolder {
        public final long appVersion;
        public PackageState pkg;
        public ProcessState state;

        public ProcessStateHolder(long _appVersion) {
            this.appVersion = _appVersion;
        }
    }

    public static final class PackageState {
        public final ArrayMap<String, AssociationState> mAssociations = new ArrayMap<>();
        public final String mPackageName;
        public final ProcessStats mProcessStats;
        public final ArrayMap<String, ProcessState> mProcesses = new ArrayMap<>();
        public final ArrayMap<String, ServiceState> mServices = new ArrayMap<>();
        public final int mUid;
        public final long mVersionCode;

        public PackageState(ProcessStats procStats, String packageName, int uid, long versionCode) {
            this.mProcessStats = procStats;
            this.mUid = uid;
            this.mPackageName = packageName;
            this.mVersionCode = versionCode;
        }

        public AssociationState getAssociationStateLocked(ProcessState proc, String className) {
            AssociationState as = this.mAssociations.get(className);
            if (as != null) {
                if (proc != null) {
                    as.setProcess(proc);
                }
                return as;
            }
            AssociationState as2 = new AssociationState(this.mProcessStats, this, className, proc.getName(), proc);
            this.mAssociations.put(className, as2);
            return as2;
        }

        public void writeToProto(ProtoOutputStream proto, long fieldId, long now, int section) {
            ProtoOutputStream protoOutputStream = proto;
            long token = proto.start(fieldId);
            protoOutputStream.write(1138166333441L, this.mPackageName);
            protoOutputStream.write(1120986464258L, this.mUid);
            protoOutputStream.write(1112396529667L, this.mVersionCode);
            int ia = 0;
            if ((section & 2) != 0) {
                int ip = 0;
                while (true) {
                    int ip2 = ip;
                    if (ip2 >= this.mProcesses.size()) {
                        break;
                    }
                    this.mProcesses.valueAt(ip2).writeToProto(proto, 2246267895812L, this.mProcesses.keyAt(ip2), this.mUid, now);
                    ip = ip2 + 1;
                }
            }
            if ((section & 4) != 0) {
                int is = 0;
                while (true) {
                    int is2 = is;
                    if (is2 >= this.mServices.size()) {
                        break;
                    }
                    this.mServices.valueAt(is2).writeToProto(proto, 2246267895813L, now);
                    is = is2 + 1;
                }
            }
            if ((section & 8) != 0) {
                while (true) {
                    int ia2 = ia;
                    if (ia2 >= this.mAssociations.size()) {
                        break;
                    }
                    this.mAssociations.valueAt(ia2).writeToProto(proto, 2246267895814L, now);
                    ia = ia2 + 1;
                }
            }
            protoOutputStream.end(token);
        }
    }

    public static final class ProcessDataCollection {
        public long avgPss;
        public long avgRss;
        public long avgUss;
        public long maxPss;
        public long maxRss;
        public long maxUss;
        final int[] memStates;
        public long minPss;
        public long minRss;
        public long minUss;
        public long numPss;
        final int[] procStates;
        final int[] screenStates;
        public long totalTime;

        public ProcessDataCollection(int[] _screenStates, int[] _memStates, int[] _procStates) {
            this.screenStates = _screenStates;
            this.memStates = _memStates;
            this.procStates = _procStates;
        }

        /* access modifiers changed from: package-private */
        public void print(PrintWriter pw, long overallTime, boolean full) {
            if (this.totalTime > overallTime) {
                pw.print("*");
            }
            DumpUtils.printPercent(pw, ((double) this.totalTime) / ((double) overallTime));
            if (this.numPss > 0) {
                pw.print(" (");
                DebugUtils.printSizeValue(pw, this.minPss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.avgPss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.maxPss * 1024);
                pw.print("/");
                DebugUtils.printSizeValue(pw, this.minUss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.avgUss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.maxUss * 1024);
                pw.print("/");
                DebugUtils.printSizeValue(pw, this.minRss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.avgRss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.maxRss * 1024);
                if (full) {
                    pw.print(" over ");
                    pw.print(this.numPss);
                }
                pw.print(")");
            }
        }
    }

    public static class TotalMemoryUseCollection {
        public boolean hasSwappedOutPss;
        final int[] memStates;
        public long[] processStatePss = new long[14];
        public int[] processStateSamples = new int[14];
        public long[] processStateTime = new long[14];
        public double[] processStateWeight = new double[14];
        final int[] screenStates;
        public double sysMemCachedWeight;
        public double sysMemFreeWeight;
        public double sysMemKernelWeight;
        public double sysMemNativeWeight;
        public int sysMemSamples;
        public long[] sysMemUsage = new long[16];
        public double sysMemZRamWeight;
        public long totalTime;

        public TotalMemoryUseCollection(int[] _screenStates, int[] _memStates) {
            this.screenStates = _screenStates;
            this.memStates = _memStates;
        }
    }
}
