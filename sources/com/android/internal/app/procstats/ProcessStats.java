package com.android.internal.app.procstats;

import android.content.ComponentName;
import android.p007os.Debug;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.Process;
import android.p007os.SystemClock;
import android.p007os.SystemProperties;
import android.p007os.UserHandle;
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

/* loaded from: classes4.dex */
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
    static final boolean DEBUG = false;
    static final boolean DEBUG_PARCEL = false;
    public static final int FLAG_COMPLETE = 1;
    public static final int FLAG_SHUTDOWN = 2;
    public static final int FLAG_SYSPROPS = 4;
    private static final long INVERSE_PROC_STATE_WARNING_MIN_INTERVAL_MS = 10000;
    private static final int MAGIC = 1347638356;
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
    public int mMemFactor;
    public final long[] mMemFactorDurations;
    private long mNextInverseProcStateWarningUptime;
    public final ProcessMap<LongSparseArray<PackageState>> mPackages;
    private final ArrayList<String> mPageTypeLabels;
    private final ArrayList<Integer> mPageTypeNodes;
    private final ArrayList<int[]> mPageTypeSizes;
    private final ArrayList<String> mPageTypeZones;
    public final ProcessMap<ProcessState> mProcesses;
    public String mReadError;
    boolean mRunning;
    String mRuntime;
    private int mSkippedInverseProcStateWarningCount;
    public long mStartTime;
    public final SysMemUsageTable mSysMemUsage;
    public final long[] mSysMemUsageArgs;
    public final SparseMappingTable mTableData;
    public long mTimePeriodEndRealtime;
    public long mTimePeriodEndUptime;
    public long mTimePeriodStartClock;
    public String mTimePeriodStartClockStr;
    public long mTimePeriodStartRealtime;
    public long mTimePeriodStartUptime;
    public final ArrayList<AssociationState.SourceState> mTrackingAssociations;
    public static long COMMIT_PERIOD = 10800000;
    public static long COMMIT_UPTIME_PERIOD = 3600000;
    public static final int[] ALL_MEM_ADJ = {0, 1, 2, 3};
    public static final int[] ALL_SCREEN_ADJ = {0, 4};
    public static final int[] NON_CACHED_PROC_STATES = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    public static final int[] BACKGROUND_PROC_STATES = {2, 3, 4, 8, 5, 6, 7};
    public static final int[] ALL_PROC_STATES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    public static final int[] OPTIONS = {1, 2, 4, 8, 14, 15};
    public static final String[] OPTIONS_STR = {"proc", "pkg-proc", "pkg-svc", "pkg-asc", "pkg-all", "all"};
    private static final Pattern sPageTypeRegex = Pattern.compile("^Node\\s+(\\d+),.* zone\\s+(\\w+),.* type\\s+(\\w+)\\s+([\\s\\d]+?)\\s*$");
    public static final Parcelable.Creator<ProcessStats> CREATOR = new Parcelable.Creator<ProcessStats>() { // from class: com.android.internal.app.procstats.ProcessStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ProcessStats createFromParcel(Parcel in) {
            return new ProcessStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ProcessStats[] newArray(int size) {
            return new ProcessStats[size];
        }
    };
    static final int[] BAD_TABLE = new int[0];

    public ProcessStats(boolean running) {
        this.mPackages = new ProcessMap<>();
        this.mProcesses = new ProcessMap<>();
        this.mTrackingAssociations = new ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mTableData = new SparseMappingTable();
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new SysMemUsageTable(this.mTableData);
        this.mPageTypeNodes = new ArrayList<>();
        this.mPageTypeZones = new ArrayList<>();
        this.mPageTypeLabels = new ArrayList<>();
        this.mPageTypeSizes = new ArrayList<>();
        this.mRunning = running;
        reset();
        if (running) {
            Debug.MemoryInfo info = new Debug.MemoryInfo();
            Debug.getMemoryInfo(Process.myPid(), info);
            this.mHasSwappedOutPss = info.hasSwappedOutPss();
        }
    }

    public ProcessStats(Parcel in) {
        this.mPackages = new ProcessMap<>();
        this.mProcesses = new ProcessMap<>();
        this.mTrackingAssociations = new ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mTableData = new SparseMappingTable();
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new SysMemUsageTable(this.mTableData);
        this.mPageTypeNodes = new ArrayList<>();
        this.mPageTypeZones = new ArrayList<>();
        this.mPageTypeLabels = new ArrayList<>();
        this.mPageTypeSizes = new ArrayList<>();
        reset();
        readFromParcel(in);
    }

    public void add(ProcessStats other) {
        ArrayMap<String, SparseArray<ProcessState>> procMap;
        ProcessState thisProc;
        LongSparseArray<PackageState> versions;
        PackageState otherState;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap;
        SparseArray<LongSparseArray<PackageState>> uids;
        int NSRVS;
        int NPROCS;
        int NSRVS2;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = other.mPackages.getMap();
        int ip = 0;
        while (true) {
            int ip2 = ip;
            int ip3 = pkgMap2.size();
            if (ip2 >= ip3) {
                break;
            }
            String pkgName = pkgMap2.keyAt(ip2);
            SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap2.valueAt(ip2);
            int iu = 0;
            while (true) {
                int iu2 = iu;
                int iu3 = uids2.size();
                if (iu2 < iu3) {
                    int uid = uids2.keyAt(iu2);
                    LongSparseArray<PackageState> versions2 = uids2.valueAt(iu2);
                    int iv = 0;
                    while (true) {
                        int iv2 = iv;
                        int iv3 = versions2.size();
                        if (iv2 < iv3) {
                            long vers = versions2.keyAt(iv2);
                            PackageState otherState2 = versions2.valueAt(iv2);
                            int NPROCS2 = otherState2.mProcesses.size();
                            int NSRVS3 = otherState2.mServices.size();
                            int NASCS = otherState2.mAssociations.size();
                            int iproc = 0;
                            while (true) {
                                int iproc2 = iproc;
                                versions = versions2;
                                if (iproc2 >= NPROCS2) {
                                    break;
                                }
                                int NSRVS4 = NSRVS3;
                                ProcessState otherProc = otherState2.mProcesses.valueAt(iproc2);
                                int NPROCS3 = NPROCS2;
                                if (otherProc.getCommonProcess() == otherProc) {
                                    otherState = otherState2;
                                    pkgMap = pkgMap2;
                                    uids = uids2;
                                    NSRVS = NSRVS4;
                                    NPROCS = NPROCS3;
                                    NSRVS2 = iv2;
                                } else {
                                    pkgMap = pkgMap2;
                                    uids = uids2;
                                    NPROCS = NPROCS3;
                                    NSRVS = NSRVS4;
                                    otherState = otherState2;
                                    long vers2 = vers;
                                    NSRVS2 = iv2;
                                    ProcessState thisProc2 = getProcessStateLocked(pkgName, uid, vers, otherProc.getName());
                                    if (thisProc2.getCommonProcess() == thisProc2) {
                                        thisProc2.setMultiPackage(true);
                                        long now = SystemClock.uptimeMillis();
                                        vers = vers2;
                                        PackageState pkgState = getPackageStateLocked(pkgName, uid, vers);
                                        thisProc2 = thisProc2.clone(now);
                                        pkgState.mProcesses.put(thisProc2.getName(), thisProc2);
                                    } else {
                                        vers = vers2;
                                    }
                                    thisProc2.add(otherProc);
                                }
                                iproc = iproc2 + 1;
                                otherState2 = otherState;
                                NSRVS3 = NSRVS;
                                NPROCS2 = NPROCS;
                                versions2 = versions;
                                iv2 = NSRVS2;
                                pkgMap2 = pkgMap;
                                uids2 = uids;
                            }
                            PackageState otherState3 = otherState2;
                            int iv4 = iv2;
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
                                ServiceState thisSvc = getServiceStateLocked(pkgName, uid, vers, otherSvc.getProcessName(), otherSvc.getName());
                                thisSvc.add(otherSvc);
                                isvc = isvc2 + 1;
                                NSRVS5 = NSRVS5;
                            }
                            long vers3 = vers;
                            int iasc = 0;
                            while (true) {
                                int iasc2 = iasc;
                                if (iasc2 < NASCS) {
                                    AssociationState otherAsc = otherState3.mAssociations.valueAt(iasc2);
                                    AssociationState thisAsc = getAssociationStateLocked(pkgName, uid, vers3, otherAsc.getProcessName(), otherAsc.getName());
                                    thisAsc.add(otherAsc);
                                    iasc = iasc2 + 1;
                                }
                            }
                            iv = iv4 + 1;
                            versions2 = versions;
                            pkgMap2 = pkgMap3;
                            uids2 = uids3;
                        }
                    }
                    iu = iu2 + 1;
                }
            }
            ip = ip2 + 1;
        }
        ArrayMap<String, SparseArray<ProcessState>> procMap2 = other.mProcesses.getMap();
        int ip4 = 0;
        while (true) {
            int ip5 = ip4;
            int ip6 = procMap2.size();
            if (ip5 >= ip6) {
                break;
            }
            SparseArray<ProcessState> uids4 = procMap2.valueAt(ip5);
            int iu4 = 0;
            while (true) {
                int iu5 = iu4;
                int iu6 = uids4.size();
                if (iu5 < iu6) {
                    int uid2 = uids4.keyAt(iu5);
                    ProcessState otherProc2 = uids4.valueAt(iu5);
                    String name = otherProc2.getName();
                    String pkg = otherProc2.getPackage();
                    long vers4 = otherProc2.getVersion();
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
                        thisProc = thisProc3;
                    }
                    thisProc.add(otherProc2);
                    iu4 = iu5 + 1;
                    procMap2 = procMap;
                }
            }
            ip4 = ip5 + 1;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= 8) {
                break;
            }
            long[] jArr = this.mMemFactorDurations;
            jArr[i2] = jArr[i2] + other.mMemFactorDurations[i2];
            i = i2 + 1;
        }
        this.mSysMemUsage.mergeStats(other.mSysMemUsage);
        if (other.mTimePeriodStartClock < this.mTimePeriodStartClock) {
            this.mTimePeriodStartClock = other.mTimePeriodStartClock;
            this.mTimePeriodStartClockStr = other.mTimePeriodStartClockStr;
        }
        this.mTimePeriodEndRealtime += other.mTimePeriodEndRealtime - other.mTimePeriodStartRealtime;
        this.mTimePeriodEndUptime += other.mTimePeriodEndUptime - other.mTimePeriodStartUptime;
        this.mInternalSinglePssCount += other.mInternalSinglePssCount;
        this.mInternalSinglePssTime += other.mInternalSinglePssTime;
        this.mInternalAllMemPssCount += other.mInternalAllMemPssCount;
        this.mInternalAllMemPssTime += other.mInternalAllMemPssTime;
        this.mInternalAllPollPssCount += other.mInternalAllPollPssCount;
        this.mInternalAllPollPssTime += other.mInternalAllPollPssTime;
        this.mExternalPssCount += other.mExternalPssCount;
        this.mExternalPssTime += other.mExternalPssTime;
        this.mExternalSlowPssCount += other.mExternalSlowPssCount;
        this.mExternalSlowPssTime += other.mExternalSlowPssTime;
        this.mHasSwappedOutPss |= other.mHasSwappedOutPss;
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
        long j = now;
        data.totalTime = 0L;
        int i = 0;
        for (int i2 = 0; i2 < 14; i2++) {
            data.processStateWeight[i2] = 0.0d;
            data.processStatePss[i2] = 0;
            data.processStateTime[i2] = 0;
            data.processStateSamples[i2] = 0;
        }
        for (int i3 = 0; i3 < 16; i3++) {
            data.sysMemUsage[i3] = 0;
        }
        data.sysMemCachedWeight = 0.0d;
        data.sysMemFreeWeight = 0.0d;
        data.sysMemZRamWeight = 0.0d;
        data.sysMemKernelWeight = 0.0d;
        data.sysMemNativeWeight = 0.0d;
        data.sysMemSamples = 0;
        long[] totalMemUsage2 = this.mSysMemUsage.getTotalMemUsage();
        int is = 0;
        while (is < data.screenStates.length) {
            int im = i;
            while (im < data.memStates.length) {
                int memBucket = data.screenStates[is] + data.memStates[im];
                int stateBucket = memBucket * 14;
                long memTime = this.mMemFactorDurations[memBucket];
                if (this.mMemFactor == memBucket) {
                    memTime += j - this.mStartTime;
                }
                data.totalTime += memTime;
                int sysKey = this.mSysMemUsage.getKey((byte) stateBucket);
                long[] longs = totalMemUsage2;
                int idx = 0;
                if (sysKey != -1) {
                    long[] tmpLongs = this.mSysMemUsage.getArrayForKey(sysKey);
                    int tmpIndex = SparseMappingTable.getIndexFromKey(sysKey);
                    if (tmpLongs[tmpIndex + 0] >= 3) {
                        totalMemUsage = totalMemUsage2;
                        long[] totalMemUsage3 = data.sysMemUsage;
                        SysMemUsageTable.mergeSysMemUsage(totalMemUsage3, i, longs, 0);
                        longs = tmpLongs;
                        idx = tmpIndex;
                        data.sysMemCachedWeight += longs[idx + 2] * memTime;
                        data.sysMemFreeWeight += longs[idx + 5] * memTime;
                        data.sysMemZRamWeight += longs[idx + 8] * memTime;
                        data.sysMemKernelWeight += longs[idx + 11] * memTime;
                        data.sysMemNativeWeight += longs[idx + 14] * memTime;
                        data.sysMemSamples = (int) (data.sysMemSamples + longs[idx + 0]);
                        im++;
                        totalMemUsage2 = totalMemUsage;
                        j = now;
                        i = 0;
                    }
                }
                totalMemUsage = totalMemUsage2;
                data.sysMemCachedWeight += longs[idx + 2] * memTime;
                data.sysMemFreeWeight += longs[idx + 5] * memTime;
                data.sysMemZRamWeight += longs[idx + 8] * memTime;
                data.sysMemKernelWeight += longs[idx + 11] * memTime;
                data.sysMemNativeWeight += longs[idx + 14] * memTime;
                data.sysMemSamples = (int) (data.sysMemSamples + longs[idx + 0]);
                im++;
                totalMemUsage2 = totalMemUsage;
                j = now;
                i = 0;
            }
            is++;
            j = now;
            i = 0;
        }
        data.hasSwappedOutPss = this.mHasSwappedOutPss;
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        for (int iproc = 0; iproc < procMap.size(); iproc++) {
            SparseArray<ProcessState> uids = procMap.valueAt(iproc);
            for (int iu = 0; iu < uids.size(); iu++) {
                ProcessState proc = uids.valueAt(iu);
                proc.aggregatePss(data, now);
            }
        }
    }

    public void reset() {
        resetCommon();
        this.mPackages.getMap().clear();
        this.mProcesses.getMap().clear();
        this.mMemFactor = -1;
        this.mStartTime = 0L;
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
                int iv2 = vpkgs.size();
                if (iv2 <= 0) {
                    uids2.removeAt(iu2);
                }
            }
            int iu3 = uids2.size();
            if (iu3 <= 0) {
                pkgMap.removeAt(ip2);
            }
        }
        int ip3 = procMap.size();
        for (int ip4 = ip3 - 1; ip4 >= 0; ip4--) {
            SparseArray<ProcessState> uids3 = procMap.valueAt(ip4);
            for (int iu4 = uids3.size() - 1; iu4 >= 0; iu4--) {
                ProcessState ps2 = uids3.valueAt(iu4);
                if (ps2.isInUse() || ps2.tmpNumInUse > 0) {
                    if (!ps2.isActive() && ps2.isMultiPackage() && ps2.tmpNumInUse == 1) {
                        ProcessState ps3 = ps2.tmpFoundSubProc;
                        ps3.makeStandalone();
                        uids3.setValueAt(iu4, ps3);
                    } else {
                        ps2.resetSafely(now);
                    }
                } else {
                    ps2.makeDead();
                    uids3.removeAt(iu4);
                }
            }
            int iu5 = uids3.size();
            if (iu5 <= 0) {
                procMap.removeAt(ip4);
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
        this.mInternalSinglePssCount = 0L;
        this.mInternalSinglePssTime = 0L;
        this.mInternalAllMemPssCount = 0L;
        this.mInternalAllMemPssTime = 0L;
        this.mInternalAllPollPssCount = 0L;
        this.mInternalAllPollPssTime = 0L;
        this.mExternalPssCount = 0L;
        this.mExternalPssTime = 0L;
        this.mExternalSlowPssCount = 0L;
        this.mExternalSlowPssTime = 0L;
        this.mTableData.reset();
        Arrays.fill(this.mMemFactorDurations, 0L);
        this.mSysMemUsage.resetTable();
        this.mStartTime = 0L;
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
        this.mTimePeriodStartClockStr = DateFormat.format("yyyy-MM-dd-HH-mm-ss", this.mTimePeriodStartClock).toString();
    }

    public void updateFragmentation() {
        Integer node;
        BufferedReader reader = null;
        try {
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
                    }
                    matcher.reset(line);
                    if (matcher.matches() && (node = Integer.valueOf(matcher.group(1), 10)) != null) {
                        this.mPageTypeNodes.add(node);
                        this.mPageTypeZones.add(matcher.group(2));
                        this.mPageTypeLabels.add(matcher.group(3));
                        this.mPageTypeSizes.add(splitAndParseNumbers(matcher.group(4)));
                    }
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            this.mPageTypeNodes.clear();
            this.mPageTypeZones.clear();
            this.mPageTypeLabels.clear();
            this.mPageTypeSizes.clear();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                }
            }
        }
    }

    private static int[] splitAndParseNumbers(String s) {
        int count = 0;
        int N = s.length();
        boolean digit = false;
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                if (!digit) {
                    digit = true;
                    count++;
                }
            } else {
                digit = false;
            }
        }
        int[] result = new int[count];
        int p = 0;
        int val = 0;
        for (int i2 = 0; i2 < N; i2++) {
            char c2 = s.charAt(i2);
            if (c2 >= '0' && c2 <= '9') {
                if (!digit) {
                    digit = true;
                    val = c2 - '0';
                } else {
                    val = (val * 10) + (c2 - '0');
                }
            } else if (digit) {
                digit = false;
                result[p] = val;
                p++;
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
                Slog.m50w(TAG, "Time val negative: " + val);
                val = 0;
            }
            if (val > 2147483647L) {
                int top = ~((int) (2147483647L & (val >> 32)));
                int bottom = (int) (4294967295L & val);
                out.writeInt(top);
                out.writeInt(bottom);
            } else {
                out.writeInt((int) val);
            }
        }
    }

    private void readCompactedLongArray(Parcel in, int version, long[] array, int num) {
        if (version <= 10) {
            in.readLongArray(array);
            return;
        }
        int alen = array.length;
        if (num > alen) {
            throw new RuntimeException("bad array lengths: got " + num + " array is " + alen);
        }
        int i = 0;
        while (i < num) {
            int val = in.readInt();
            if (val >= 0) {
                array[i] = val;
            } else {
                int bottom = in.readInt();
                array[i] = ((~val) << 32) | bottom;
            }
            i++;
        }
        while (i < alen) {
            array[i] = 0;
            i++;
        }
    }

    void writeCommonString(Parcel out, String name) {
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

    String readCommonString(Parcel in, int version) {
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
            this.mIndexToCommonString.add(null);
        }
        this.mIndexToCommonString.set(index2, name);
        return name;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        writeToParcel(out, SystemClock.uptimeMillis(), flags);
    }

    public void writeToParcel(Parcel out, long now, int flags) {
        int NUID;
        out.writeInt(MAGIC);
        out.writeInt(36);
        out.writeInt(14);
        out.writeInt(8);
        out.writeInt(10);
        out.writeInt(16);
        out.writeInt(4096);
        this.mCommonStringToIndex = new ArrayMap<>(this.mProcesses.size());
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        int NPROC = procMap.size();
        for (int ip = 0; ip < NPROC; ip++) {
            SparseArray<ProcessState> uids = procMap.valueAt(ip);
            int NUID2 = uids.size();
            for (int iu = 0; iu < NUID2; iu++) {
                uids.valueAt(iu).commitStateTime(now);
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
                        int iproc3 = iproc2;
                        NUID = iproc;
                        if (iproc3 >= NPROCS) {
                            break;
                        }
                        int NPROCS2 = NPROCS;
                        ProcessState proc = pkgState.mProcesses.valueAt(iproc3);
                        LongSparseArray<PackageState> vpkgs2 = vpkgs;
                        if (proc.getCommonProcess() != proc) {
                            proc.commitStateTime(now);
                        }
                        iproc2 = iproc3 + 1;
                        iproc = NUID;
                        NPROCS = NPROCS2;
                        vpkgs = vpkgs2;
                    }
                    LongSparseArray<PackageState> vpkgs3 = vpkgs;
                    int NSRVS = pkgState.mServices.size();
                    for (int isvc = 0; isvc < NSRVS; isvc++) {
                        pkgState.mServices.valueAt(isvc).commitStateTime(now);
                    }
                    int NASCS = pkgState.mAssociations.size();
                    int iasc = 0;
                    while (iasc < NASCS) {
                        pkgState.mAssociations.valueAt(iasc).commitStateTime(now);
                        iasc++;
                        NSRVS = NSRVS;
                    }
                    iv++;
                    uids2 = uids3;
                    iproc = NUID;
                    vpkgs = vpkgs3;
                }
            }
        }
        out.writeLong(this.mTimePeriodStartClock);
        out.writeLong(this.mTimePeriodStartRealtime);
        out.writeLong(this.mTimePeriodEndRealtime);
        out.writeLong(this.mTimePeriodStartUptime);
        out.writeLong(this.mTimePeriodEndUptime);
        out.writeLong(this.mInternalSinglePssCount);
        out.writeLong(this.mInternalSinglePssTime);
        out.writeLong(this.mInternalAllMemPssCount);
        out.writeLong(this.mInternalAllMemPssTime);
        out.writeLong(this.mInternalAllPollPssCount);
        out.writeLong(this.mInternalAllPollPssTime);
        out.writeLong(this.mExternalPssCount);
        out.writeLong(this.mExternalPssTime);
        out.writeLong(this.mExternalSlowPssCount);
        out.writeLong(this.mExternalSlowPssTime);
        out.writeString(this.mRuntime);
        out.writeInt(this.mHasSwappedOutPss ? 1 : 0);
        out.writeInt(this.mFlags);
        this.mTableData.writeToParcel(out);
        if (this.mMemFactor != -1) {
            long[] jArr = this.mMemFactorDurations;
            int i = this.mMemFactor;
            jArr[i] = jArr[i] + (now - this.mStartTime);
            this.mStartTime = now;
        }
        writeCompactedLongArray(out, this.mMemFactorDurations, this.mMemFactorDurations.length);
        this.mSysMemUsage.writeToParcel(out);
        out.writeInt(NPROC);
        for (int ip3 = 0; ip3 < NPROC; ip3++) {
            writeCommonString(out, procMap.keyAt(ip3));
            SparseArray<ProcessState> uids4 = procMap.valueAt(ip3);
            int NUID3 = uids4.size();
            out.writeInt(NUID3);
            for (int iu3 = 0; iu3 < NUID3; iu3++) {
                out.writeInt(uids4.keyAt(iu3));
                ProcessState proc2 = uids4.valueAt(iu3);
                writeCommonString(out, proc2.getPackage());
                out.writeLong(proc2.getVersion());
                proc2.writeToParcel(out, now);
            }
        }
        out.writeInt(NPKG);
        for (int ip4 = 0; ip4 < NPKG; ip4++) {
            writeCommonString(out, pkgMap.keyAt(ip4));
            SparseArray<LongSparseArray<PackageState>> uids5 = pkgMap.valueAt(ip4);
            int NUID4 = uids5.size();
            out.writeInt(NUID4);
            for (int iu4 = 0; iu4 < NUID4; iu4++) {
                out.writeInt(uids5.keyAt(iu4));
                LongSparseArray<PackageState> vpkgs4 = uids5.valueAt(iu4);
                int NVERS2 = vpkgs4.size();
                out.writeInt(NVERS2);
                int iv2 = 0;
                while (iv2 < NVERS2) {
                    ArrayMap<String, SparseArray<ProcessState>> procMap2 = procMap;
                    int NPROC2 = NPROC;
                    out.writeLong(vpkgs4.keyAt(iv2));
                    PackageState pkgState2 = vpkgs4.valueAt(iv2);
                    int NPROCS3 = pkgState2.mProcesses.size();
                    out.writeInt(NPROCS3);
                    int iproc4 = 0;
                    while (iproc4 < NPROCS3) {
                        int NPROCS4 = NPROCS3;
                        writeCommonString(out, pkgState2.mProcesses.keyAt(iproc4));
                        ProcessState proc3 = pkgState2.mProcesses.valueAt(iproc4);
                        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = pkgMap;
                        if (proc3.getCommonProcess() == proc3) {
                            out.writeInt(0);
                        } else {
                            out.writeInt(1);
                            proc3.writeToParcel(out, now);
                        }
                        iproc4++;
                        NPROCS3 = NPROCS4;
                        pkgMap = pkgMap2;
                    }
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap3 = pkgMap;
                    int NSRVS2 = pkgState2.mServices.size();
                    out.writeInt(NSRVS2);
                    int isvc2 = 0;
                    while (isvc2 < NSRVS2) {
                        out.writeString(pkgState2.mServices.keyAt(isvc2));
                        ServiceState svc = pkgState2.mServices.valueAt(isvc2);
                        writeCommonString(out, svc.getProcessName());
                        svc.writeToParcel(out, now);
                        isvc2++;
                        NSRVS2 = NSRVS2;
                    }
                    int NASCS2 = pkgState2.mAssociations.size();
                    out.writeInt(NASCS2);
                    int iasc2 = 0;
                    while (iasc2 < NASCS2) {
                        writeCommonString(out, pkgState2.mAssociations.keyAt(iasc2));
                        AssociationState asc = pkgState2.mAssociations.valueAt(iasc2);
                        writeCommonString(out, asc.getProcessName());
                        asc.writeToParcel(this, out, now);
                        iasc2++;
                        pkgState2 = pkgState2;
                    }
                    iv2++;
                    procMap = procMap2;
                    NPROC = NPROC2;
                    pkgMap = pkgMap3;
                }
            }
        }
        int NPAGETYPES = this.mPageTypeLabels.size();
        out.writeInt(NPAGETYPES);
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= NPAGETYPES) {
                this.mCommonStringToIndex = null;
                return;
            }
            out.writeInt(this.mPageTypeNodes.get(i3).intValue());
            out.writeString(this.mPageTypeZones.get(i3));
            out.writeString(this.mPageTypeLabels.get(i3));
            out.writeIntArray(this.mPageTypeSizes.get(i3));
            i2 = i3 + 1;
        }
    }

    private boolean readCheckedInt(Parcel in, int val, String what) {
        int got = in.readInt();
        if (got != val) {
            this.mReadError = "bad " + what + PluralRules.KEYWORD_RULE_SEPARATOR + got;
            return false;
        }
        return true;
    }

    static byte[] readFully(InputStream stream, int[] outLen) throws IOException {
        int pos = 0;
        int initialAvail = stream.available();
        byte[] data = new byte[initialAvail > 0 ? initialAvail + 1 : 16384];
        while (true) {
            int amt = stream.read(data, pos, data.length - pos);
            if (amt < 0) {
                outLen[0] = pos;
                return data;
            }
            pos += amt;
            if (pos >= data.length) {
                byte[] newData = new byte[pos + 16384];
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
        long vers;
        int NSRVS;
        LongSparseArray<PackageState> vpkg;
        PackageState pkgState;
        ServiceState serv;
        int uid;
        boolean hadData = this.mPackages.getMap().size() > 0 || this.mProcesses.getMap().size() > 0;
        if (hadData) {
            resetSafely();
        }
        if (!readCheckedInt(in, MAGIC, "magic number")) {
            return;
        }
        int version = in.readInt();
        if (version == 36) {
            if (readCheckedInt(in, 14, "state count") && readCheckedInt(in, 8, "adj count") && readCheckedInt(in, 10, "pss count") && readCheckedInt(in, 16, "sys mem usage count") && readCheckedInt(in, 4096, "longs size")) {
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
                this.mTableData.readFromParcel(in);
                readCompactedLongArray(in, version, this.mMemFactorDurations, this.mMemFactorDurations.length);
                if (!this.mSysMemUsage.readFromParcel(in)) {
                    return;
                }
                int NPROC = in.readInt();
                if (NPROC < 0) {
                    this.mReadError = "bad process count: " + NPROC;
                    return;
                }
                int NPROC2 = NPROC;
                while (NPROC2 > 0) {
                    int NPROC3 = NPROC2 - 1;
                    String procName = readCommonString(in, version);
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
                        String pkgName = readCommonString(in, version);
                        if (pkgName == null) {
                            this.mReadError = "bad process package name";
                            return;
                        }
                        long vers2 = in.readLong();
                        ProcessState proc = hadData ? this.mProcesses.get(procName, uid2) : null;
                        if (proc != null) {
                            if (!proc.readFromParcel(in, false)) {
                                return;
                            }
                            uid = uid2;
                        } else {
                            uid = uid2;
                            proc = new ProcessState(this, pkgName, uid2, vers2, procName);
                            if (!proc.readFromParcel(in, true)) {
                                return;
                            }
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
                    String pkgName2 = readCommonString(in, version);
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
                                String procName2 = readCommonString(in, version);
                                if (procName2 == null) {
                                    this.mReadError = "bad package process name";
                                    return;
                                }
                                int hasProc = in.readInt();
                                ProcessState commonProc = this.mProcesses.get(procName2, uid4);
                                if (commonProc == null) {
                                    this.mReadError = "no common proc: " + procName2;
                                    return;
                                }
                                LongSparseArray<PackageState> vpkg4 = vpkg3;
                                int NPROC4 = NPROC2;
                                if (hasProc != 0) {
                                    ProcessState proc2 = hadData ? pkgState2.mProcesses.get(procName2) : null;
                                    if (proc2 != null) {
                                        if (!proc2.readFromParcel(in, false)) {
                                            return;
                                        }
                                    } else {
                                        proc2 = new ProcessState(commonProc, pkgName2, uid4, vers4, procName2, 0L);
                                        if (!proc2.readFromParcel(in, true)) {
                                            return;
                                        }
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
                            for (int NSRVS3 = NSRVS2; NSRVS3 > 0; NSRVS3 = NSRVS) {
                                int NSRVS4 = NSRVS3 - 1;
                                String serviceName = in.readString();
                                if (serviceName == null) {
                                    this.mReadError = "bad package service name";
                                    return;
                                }
                                String processName = version > 9 ? readCommonString(in, version) : null;
                                ServiceState serv2 = hadData ? pkgState2.mServices.get(serviceName) : null;
                                if (serv2 == null) {
                                    vers = vers4;
                                    vpkg = vpkg5;
                                    NSRVS = NSRVS4;
                                    pkgState = pkgState2;
                                    serv = new ServiceState(this, pkgName2, serviceName, processName, null);
                                } else {
                                    vers = vers4;
                                    NSRVS = NSRVS4;
                                    vpkg = vpkg5;
                                    pkgState = pkgState2;
                                    serv = serv2;
                                }
                                if (!serv.readFromParcel(in)) {
                                    return;
                                }
                                pkgState.mServices.put(serviceName, serv);
                                pkgState2 = pkgState;
                                vpkg5 = vpkg;
                                vers4 = vers;
                            }
                            PackageState pkgState3 = pkgState2;
                            int NASCS2 = in.readInt();
                            if (NASCS2 < 0) {
                                this.mReadError = "bad package association count: " + NASCS2;
                                return;
                            }
                            while (NASCS2 > 0) {
                                int NASCS3 = NASCS2 - 1;
                                String associationName2 = readCommonString(in, version);
                                if (associationName2 == null) {
                                    this.mReadError = "bad package association name";
                                    return;
                                }
                                String processName2 = readCommonString(in, version);
                                AssociationState asc2 = hadData ? pkgState3.mAssociations.get(associationName2) : null;
                                if (asc2 == null) {
                                    NASCS = NASCS3;
                                    associationName = associationName2;
                                    asc = new AssociationState(this, pkgState3, associationName2, processName2, null);
                                } else {
                                    NASCS = NASCS3;
                                    associationName = associationName2;
                                    asc = asc2;
                                }
                                String errorMsg = asc.readFromParcel(this, in, version);
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
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= NPAGETYPES) {
                        this.mIndexToCommonString = null;
                        return;
                    }
                    this.mPageTypeNodes.add(Integer.valueOf(in.readInt()));
                    this.mPageTypeZones.add(in.readString());
                    this.mPageTypeLabels.add(in.readString());
                    this.mPageTypeSizes.add(in.createIntArray());
                    i = i2 + 1;
                }
            }
        } else {
            this.mReadError = "bad version: " + version;
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
        ProcessState ps2 = pkgState.mProcesses.get(processName);
        if (ps2 != null) {
            return ps2;
        }
        ProcessState commonProc2 = this.mProcesses.get(processName, pkgState.mUid);
        if (commonProc2 == null) {
            commonProc = new ProcessState(this, pkgState.mPackageName, pkgState.mUid, pkgState.mVersionCode, processName);
            this.mProcesses.put(processName, pkgState.mUid, commonProc);
        } else {
            commonProc = commonProc2;
        }
        if (!commonProc.isMultiPackage()) {
            if (pkgState.mPackageName.equals(commonProc.getPackage()) && pkgState.mVersionCode == commonProc.getVersion()) {
                ps = commonProc;
                str = processName;
            } else {
                commonProc.setMultiPackage(true);
                long now = SystemClock.uptimeMillis();
                PackageState commonPkgState = getPackageStateLocked(commonProc.getPackage(), pkgState.mUid, commonProc.getVersion());
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
                    Slog.m50w(TAG, "Cloning proc state: no package state " + commonProc.getPackage() + "/" + pkgState.mUid + " for proc " + commonProc.getName());
                }
                str = processName;
                ps = new ProcessState(commonProc, pkgState.mPackageName, pkgState.mUid, pkgState.mVersionCode, processName, now);
            }
        } else {
            str = processName;
            ps = new ProcessState(commonProc, pkgState.mPackageName, pkgState.mUid, pkgState.mVersionCode, processName, SystemClock.uptimeMillis());
        }
        pkgState.mProcesses.put(str, ps);
        return ps;
    }

    public ServiceState getServiceStateLocked(String packageName, int uid, long vers, String processName, String className) {
        PackageState as = getPackageStateLocked(packageName, uid, vers);
        ServiceState ss = as.mServices.get(className);
        if (ss != null) {
            return ss;
        }
        ProcessState ps = processName != null ? getProcessStateLocked(packageName, uid, vers, processName) : null;
        ServiceState ss2 = new ServiceState(this, packageName, className, processName, ps);
        as.mServices.put(className, ss2);
        return ss2;
    }

    public AssociationState getAssociationStateLocked(String packageName, int uid, long vers, String processName, String className) {
        PackageState pkgs = getPackageStateLocked(packageName, uid, vers);
        AssociationState as = pkgs.mAssociations.get(className);
        if (as != null) {
            return as;
        }
        ProcessState procs = processName != null ? getProcessStateLocked(packageName, uid, vers, processName) : null;
        AssociationState as2 = new AssociationState(this, pkgs, className, processName, procs);
        pkgs.mAssociations.put(className, as2);
        return as2;
    }

    public void updateTrackingAssociationsLocked(int curSeq, long now) {
        int NUM = this.mTrackingAssociations.size();
        for (int i = NUM - 1; i >= 0; i--) {
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
                                Slog.m50w(TAG, "Tracking association " + act + " whose proc state " + act.mProcState + " is better than process " + proc + " proc state " + procState + " (" + this.mSkippedInverseProcStateWarningCount + " skipped)");
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
        String str;
        boolean z;
        PrintWriter printWriter;
        SparseArray<ProcessState> uids;
        int iu;
        int ip;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap;
        boolean sepNeeded2;
        int NSRVS;
        int uid;
        PackageState pkgState;
        int NASCS;
        int iu2;
        SparseArray<LongSparseArray<PackageState>> uids2;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2;
        String pkgName;
        int iasc;
        int isvc;
        int NSRVS2;
        int NPROCS;
        long vers;
        long totalTime = DumpUtils.dumpSingleTime(null, null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now);
        boolean sepNeeded3 = false;
        if (this.mSysMemUsage.getKeyCount() > 0) {
            pw.println("System memory usage:");
            this.mSysMemUsage.dump(pw, "  ", ALL_SCREEN_ADJ, ALL_MEM_ADJ);
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
                    int iu5 = uids3.size();
                    if (iu4 < iu5) {
                        int uid2 = uids3.keyAt(iu4);
                        LongSparseArray<PackageState> vpkgs = uids3.valueAt(iu4);
                        int iv = 0;
                        while (true) {
                            iu = iu4;
                            int iu6 = vpkgs.size();
                            if (iv < iu6) {
                                long vers2 = vpkgs.keyAt(iv);
                                PackageState pkgState2 = vpkgs.valueAt(iv);
                                int iv2 = iv;
                                int NPROCS2 = pkgState2.mProcesses.size();
                                LongSparseArray<PackageState> vpkgs2 = vpkgs;
                                int NSRVS3 = pkgState2.mServices.size();
                                SparseArray<LongSparseArray<PackageState>> uids4 = uids3;
                                int NASCS2 = pkgState2.mAssociations.size();
                                boolean pkgMatch = reqPackage == null || reqPackage.equals(pkgName2);
                                boolean onlyAssociations = false;
                                if (pkgMatch) {
                                    ip = ip2;
                                    pkgMap = pkgMap3;
                                } else {
                                    boolean procMatch = false;
                                    int iproc = 0;
                                    while (true) {
                                        int iproc2 = iproc;
                                        pkgMap = pkgMap3;
                                        if (iproc2 >= NPROCS2) {
                                            ip = ip2;
                                            break;
                                        }
                                        ip = ip2;
                                        if (reqPackage.equals(pkgState2.mProcesses.valueAt(iproc2).getName())) {
                                            procMatch = true;
                                            break;
                                        }
                                        iproc = iproc2 + 1;
                                        pkgMap3 = pkgMap;
                                        ip2 = ip;
                                    }
                                    if (!procMatch) {
                                        int iasc2 = 0;
                                        while (true) {
                                            if (iasc2 >= NASCS2) {
                                                break;
                                            } else if (pkgState2.mAssociations.valueAt(iasc2).hasProcessOrPackage(reqPackage)) {
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
                                            pkgMap2 = pkgMap;
                                            pkgName = pkgName2;
                                            iv = iv2 + 1;
                                            pkgMap3 = pkgMap2;
                                            vpkgs = vpkgs2;
                                            pkgName2 = pkgName;
                                            ip2 = ip;
                                            uid2 = uid;
                                            iu4 = iu2;
                                            uids3 = uids2;
                                        }
                                    }
                                }
                                boolean ip3 = onlyAssociations;
                                if (NPROCS2 > 0 || NSRVS3 > 0 || NASCS2 > 0) {
                                    if (!printedHeader2) {
                                        if (sepNeeded) {
                                            pw.println();
                                        }
                                        pw.println("Per-Package Stats:");
                                        printedHeader2 = true;
                                        sepNeeded = true;
                                    }
                                    pw.print("  * ");
                                    pw.print(pkgName2);
                                    pw.print(" / ");
                                    UserHandle.formatUid(pw, uid2);
                                    pw.print(" / v");
                                    pw.print(vers2);
                                    pw.println(SettingsStringUtil.DELIMITER);
                                }
                                boolean sepNeeded4 = sepNeeded;
                                boolean printedHeader3 = printedHeader2;
                                if ((section & 2) != 0 && !ip3) {
                                    if (!dumpSummary) {
                                        sepNeeded2 = sepNeeded4;
                                        NPROCS = NPROCS2;
                                        NSRVS = NSRVS3;
                                        uid = uid2;
                                        pkgState = pkgState2;
                                        NASCS = NASCS2;
                                        iu2 = iu;
                                        uids2 = uids4;
                                        pkgMap2 = pkgMap;
                                        pkgName = pkgName2;
                                    } else if (dumpAll) {
                                        sepNeeded2 = sepNeeded4;
                                        NPROCS = NPROCS2;
                                        NSRVS = NSRVS3;
                                        uid = uid2;
                                        pkgState = pkgState2;
                                        NASCS = NASCS2;
                                        iu2 = iu;
                                        uids2 = uids4;
                                        pkgMap2 = pkgMap;
                                        pkgName = pkgName2;
                                    } else {
                                        ArrayList<ProcessState> procs = new ArrayList<>();
                                        int iproc3 = 0;
                                        while (iproc3 < NPROCS2) {
                                            ProcessState proc = pkgState2.mProcesses.valueAt(iproc3);
                                            if (pkgMatch) {
                                                vers = vers2;
                                            } else {
                                                vers = vers2;
                                                if (!reqPackage.equals(proc.getName())) {
                                                    iproc3++;
                                                    vers2 = vers;
                                                }
                                            }
                                            if (!activeOnly || proc.isInUse()) {
                                                procs.add(proc);
                                            }
                                            iproc3++;
                                            vers2 = vers;
                                        }
                                        sepNeeded2 = sepNeeded4;
                                        NSRVS = NSRVS3;
                                        uid = uid2;
                                        pkgState = pkgState2;
                                        iu2 = iu;
                                        NASCS = NASCS2;
                                        uids2 = uids4;
                                        pkgName = pkgName2;
                                        pkgMap2 = pkgMap;
                                        DumpUtils.dumpProcessSummaryLocked(pw, "      ", "Prc ", procs, ALL_SCREEN_ADJ, ALL_MEM_ADJ, NON_CACHED_PROC_STATES, now, totalTime);
                                    }
                                    int iproc4 = 0;
                                    while (true) {
                                        int iproc5 = iproc4;
                                        if (iproc5 >= NPROCS) {
                                            break;
                                        }
                                        ProcessState proc2 = pkgState.mProcesses.valueAt(iproc5);
                                        if (pkgMatch || reqPackage.equals(proc2.getName())) {
                                            if (!activeOnly || proc2.isInUse()) {
                                                pw.print("      Process ");
                                                pw.print(pkgState.mProcesses.keyAt(iproc5));
                                                if (proc2.getCommonProcess().isMultiPackage()) {
                                                    pw.print(" (multi, ");
                                                } else {
                                                    pw.print(" (unique, ");
                                                }
                                                pw.print(proc2.getDurationsBucketCount());
                                                pw.print(" entries)");
                                                pw.println(SettingsStringUtil.DELIMITER);
                                                proc2.dumpProcessState(pw, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, now);
                                                proc2.dumpPss(pw, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, now);
                                                proc2.dumpInternalLocked(pw, "        ", dumpAll);
                                            } else {
                                                pw.print("      (Not active: ");
                                                pw.print(pkgState.mProcesses.keyAt(iproc5));
                                                pw.println(")");
                                            }
                                        }
                                        iproc4 = iproc5 + 1;
                                    }
                                } else {
                                    sepNeeded2 = sepNeeded4;
                                    NSRVS = NSRVS3;
                                    uid = uid2;
                                    pkgState = pkgState2;
                                    NASCS = NASCS2;
                                    iu2 = iu;
                                    uids2 = uids4;
                                    pkgMap2 = pkgMap;
                                    pkgName = pkgName2;
                                }
                                if ((section & 4) != 0 && !ip3) {
                                    int isvc2 = 0;
                                    while (true) {
                                        int isvc3 = isvc2;
                                        int NSRVS4 = NSRVS;
                                        if (isvc3 >= NSRVS4) {
                                            break;
                                        }
                                        ServiceState svc = pkgState.mServices.valueAt(isvc3);
                                        if (pkgMatch || reqPackage.equals(svc.getProcessName())) {
                                            if (!activeOnly || svc.isInUse()) {
                                                if (dumpAll) {
                                                    pw.print("      Service ");
                                                } else {
                                                    pw.print("      * Svc ");
                                                }
                                                pw.print(pkgState.mServices.keyAt(isvc3));
                                                pw.println(SettingsStringUtil.DELIMITER);
                                                pw.print("        Process: ");
                                                pw.println(svc.getProcessName());
                                                isvc = isvc3;
                                                NSRVS2 = NSRVS4;
                                                svc.dumpStats(pw, "        ", "          ", "    ", now, totalTime, dumpSummary, dumpAll);
                                                isvc2 = isvc + 1;
                                                NSRVS = NSRVS2;
                                            } else {
                                                pw.print("      (Not active service: ");
                                                pw.print(pkgState.mServices.keyAt(isvc3));
                                                pw.println(")");
                                            }
                                        }
                                        isvc = isvc3;
                                        NSRVS2 = NSRVS4;
                                        isvc2 = isvc + 1;
                                        NSRVS = NSRVS2;
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
                                        if (pkgMatch || reqPackage.equals(asc.getProcessName()) || (ip3 && asc.hasProcessOrPackage(reqPackage))) {
                                            if (!activeOnly || asc.isInUse()) {
                                                if (dumpAll) {
                                                    pw.print("      Association ");
                                                } else {
                                                    pw.print("      * Asc ");
                                                }
                                                pw.print(pkgState.mAssociations.keyAt(iasc4));
                                                pw.println(SettingsStringUtil.DELIMITER);
                                                pw.print("        Process: ");
                                                pw.println(asc.getProcessName());
                                                iasc = iasc4;
                                                asc.dumpStats(pw, "        ", "          ", "    ", now, totalTime, ip3 ? reqPackage : null, dumpDetails, dumpAll);
                                                iasc3 = iasc + 1;
                                            } else {
                                                pw.print("      (Not active association: ");
                                                pw.print(pkgState.mAssociations.keyAt(iasc4));
                                                pw.println(")");
                                            }
                                        }
                                        iasc = iasc4;
                                        iasc3 = iasc + 1;
                                    }
                                }
                                printedHeader2 = printedHeader3;
                                sepNeeded = sepNeeded2;
                                iv = iv2 + 1;
                                pkgMap3 = pkgMap2;
                                vpkgs = vpkgs2;
                                pkgName2 = pkgName;
                                ip2 = ip;
                                uid2 = uid;
                                iu4 = iu2;
                                uids3 = uids2;
                            }
                        }
                        iu3 = iu + 1;
                    }
                }
                ip2++;
                printedHeader = printedHeader2;
            }
        } else {
            sepNeeded = sepNeeded3;
        }
        if ((section & 1) != 0) {
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
                int numShownProcs3 = 0;
                while (numShownProcs3 < uids5.size()) {
                    int uid3 = uids5.keyAt(numShownProcs3);
                    int numTotalProcs2 = numTotalProcs + 1;
                    ProcessState proc3 = uids5.valueAt(numShownProcs3);
                    if (proc3.hasAnyData() && proc3.isMultiPackage() && (reqPackage == null || reqPackage.equals(procName) || reqPackage.equals(proc3.getPackage()))) {
                        int numShownProcs4 = numShownProcs2 + 1;
                        if (sepNeeded6) {
                            pw.println();
                        }
                        if (!printedHeader4) {
                            pw.println("Multi-Package Common Processes:");
                            printedHeader4 = true;
                        }
                        boolean printedHeader5 = printedHeader4;
                        if (!activeOnly || proc3.isInUse()) {
                            pw.print("  * ");
                            pw.print(procName);
                            pw.print(" / ");
                            UserHandle.formatUid(pw, uid3);
                            pw.print(" (");
                            pw.print(proc3.getDurationsBucketCount());
                            pw.print(" entries)");
                            pw.println(SettingsStringUtil.DELIMITER);
                            uids = uids5;
                            proc3.dumpProcessState(pw, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, now);
                            proc3.dumpPss(pw, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, now);
                            proc3.dumpInternalLocked(pw, "        ", dumpAll);
                        } else {
                            pw.print("      (Not active: ");
                            pw.print(procName);
                            pw.println(")");
                            uids = uids5;
                        }
                        numShownProcs2 = numShownProcs4;
                        sepNeeded6 = true;
                        printedHeader4 = printedHeader5;
                    } else {
                        uids = uids5;
                    }
                    numShownProcs3++;
                    numTotalProcs = numTotalProcs2;
                    uids5 = uids;
                }
                ip4++;
                numShownProcs = numShownProcs2;
                sepNeeded = sepNeeded6;
                sepNeeded5 = printedHeader4;
            }
            pw.print("  Total procs: ");
            pw.print(numShownProcs);
            pw.print(" shown of ");
            pw.print(numTotalProcs);
            pw.println(" total");
        } else {
            processStats = this;
        }
        if (dumpAll) {
            if (sepNeeded) {
                pw.println();
            }
            if (processStats.mTrackingAssociations.size() > 0) {
                pw.println();
                pw.println("Tracking associations:");
                while (true) {
                    int i2 = i;
                    if (i2 >= processStats.mTrackingAssociations.size()) {
                        break;
                    }
                    AssociationState.SourceState src = processStats.mTrackingAssociations.get(i2);
                    AssociationState asc2 = src.getAssociationState();
                    pw.print("  #");
                    pw.print(i2);
                    pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                    pw.print(asc2.getProcessName());
                    pw.print("/");
                    UserHandle.formatUid(pw, asc2.getUid());
                    pw.print(" <- ");
                    pw.print(src.getProcessName());
                    pw.print("/");
                    UserHandle.formatUid(pw, src.getUid());
                    pw.println(SettingsStringUtil.DELIMITER);
                    pw.print("    Tracking for: ");
                    TimeUtils.formatDuration(now - src.mTrackingUptime, pw);
                    pw.println();
                    pw.print("    Component: ");
                    pw.print(new ComponentName(asc2.getPackage(), asc2.getName()).flattenToShortString());
                    pw.println();
                    pw.print("    Proc state: ");
                    if (src.mProcState != -1) {
                        pw.print(DumpUtils.STATE_NAMES[src.mProcState]);
                    } else {
                        pw.print("--");
                    }
                    pw.print(" #");
                    pw.println(src.mProcStateSeq);
                    pw.print("    Process: ");
                    pw.println(asc2.getProcess());
                    if (src.mActiveCount > 0) {
                        pw.print("    Active count ");
                        pw.print(src.mActiveCount);
                        pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                        asc2.dumpActiveDurationSummary(pw, src, totalTime, now, dumpAll);
                        pw.println();
                    }
                    i = i2 + 1;
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
            pw.println("Process summary:");
            str = reqPackage;
            z = dumpAll;
            printWriter = pw;
            dumpSummaryLocked(pw, reqPackage, now, activeOnly);
        } else {
            str = reqPackage;
            z = dumpAll;
            printWriter = pw;
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
        long totalTime = DumpUtils.dumpSingleTime(null, null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now);
        dumpFilteredSummaryLocked(pw, null, "  ", null, ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, NON_CACHED_PROC_STATES, now, totalTime, reqPackage, activeOnly);
        pw.println();
        dumpTotalsLocked(pw, now);
    }

    private void dumpFragmentationLocked(PrintWriter pw) {
        pw.println();
        pw.println("Available pages by page size:");
        int NPAGETYPES = this.mPageTypeLabels.size();
        for (int i = 0; i < NPAGETYPES; i++) {
            pw.format("Node %3d Zone %7s  %14s ", this.mPageTypeNodes.get(i), this.mPageTypeZones.get(i), this.mPageTypeLabels.get(i));
            int[] sizes = this.mPageTypeSizes.get(i);
            int N = sizes == null ? 0 : sizes.length;
            for (int j = 0; j < N; j++) {
                pw.format("%6d", Integer.valueOf(sizes[j]));
            }
            pw.println();
        }
    }

    long printMemoryCategory(PrintWriter pw, String prefix, String label, double memWeight, long totalTime, long curTotalMem, int samples) {
        if (memWeight != 0.0d) {
            long mem = (long) ((1024.0d * memWeight) / totalTime);
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
        return curTotalMem;
    }

    void dumpTotalsLocked(PrintWriter pw, long now) {
        int i;
        pw.println("Run time Stats:");
        DumpUtils.dumpSingleTime(pw, "  ", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now);
        pw.println();
        pw.println("Memory usage:");
        TotalMemoryUseCollection totalMem = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMem, now);
        long totalPss = printMemoryCategory(pw, "  ", "Kernel ", totalMem.sysMemKernelWeight, totalMem.totalTime, 0L, totalMem.sysMemSamples);
        int i2 = 0;
        long totalPss2 = printMemoryCategory(pw, "  ", "Native ", totalMem.sysMemNativeWeight, totalMem.totalTime, totalPss, totalMem.sysMemSamples);
        while (true) {
            int i3 = i2;
            if (i3 >= 14) {
                break;
            }
            if (i3 == 6) {
                i = i3;
            } else {
                i = i3;
                totalPss2 = printMemoryCategory(pw, "  ", DumpUtils.STATE_NAMES[i3], totalMem.processStateWeight[i3], totalMem.totalTime, totalPss2, totalMem.processStateSamples[i3]);
            }
            i2 = i + 1;
        }
        long totalPss3 = printMemoryCategory(pw, "  ", "Z-Ram  ", totalMem.sysMemZRamWeight, totalMem.totalTime, printMemoryCategory(pw, "  ", "Free   ", totalMem.sysMemFreeWeight, totalMem.totalTime, printMemoryCategory(pw, "  ", "Cached ", totalMem.sysMemCachedWeight, totalMem.totalTime, totalPss2, totalMem.sysMemSamples), totalMem.sysMemSamples), totalMem.sysMemSamples);
        pw.print("  TOTAL  : ");
        DebugUtils.printSizeValue(pw, totalPss3);
        pw.println();
        printMemoryCategory(pw, "  ", DumpUtils.STATE_NAMES[6], totalMem.processStateWeight[6], totalMem.totalTime, totalPss3, totalMem.processStateSamples[6]);
        pw.println();
        pw.println("PSS collection stats:");
        pw.print("  Internal Single: ");
        pw.print(this.mInternalSinglePssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mInternalSinglePssTime, pw);
        pw.println();
        pw.print("  Internal All Procs (Memory Change): ");
        pw.print(this.mInternalAllMemPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllMemPssTime, pw);
        pw.println();
        pw.print("  Internal All Procs (Polling): ");
        pw.print(this.mInternalAllPollPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllPollPssTime, pw);
        pw.println();
        pw.print("  External: ");
        pw.print(this.mExternalPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mExternalPssTime, pw);
        pw.println();
        pw.print("  External Slow: ");
        pw.print(this.mExternalSlowPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mExternalSlowPssTime, pw);
        pw.println();
        pw.println();
        pw.print("          Start time: ");
        pw.print(DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mTimePeriodStartClock));
        pw.println();
        pw.print("        Total uptime: ");
        TimeUtils.formatDuration((this.mRunning ? SystemClock.uptimeMillis() : this.mTimePeriodEndUptime) - this.mTimePeriodStartUptime, pw);
        pw.println();
        pw.print("  Total elapsed time: ");
        TimeUtils.formatDuration((this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime) - this.mTimePeriodStartRealtime, pw);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            pw.print(" (shutdown)");
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            pw.print(" (sysprops)");
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            pw.print(" (complete)");
            partial = false;
        }
        if (partial) {
            pw.print(" (partial)");
        }
        if (this.mHasSwappedOutPss) {
            pw.print(" (swapped-out-pss)");
        }
        pw.print(' ');
        pw.print(this.mRuntime);
        pw.println();
    }

    void dumpFilteredSummaryLocked(PrintWriter pw, String header, String prefix, String prcLabel, int[] screenStates, int[] memStates, int[] procStates, int[] sortProcStates, long now, long totalTime, String reqPackage, boolean activeOnly) {
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
                }
                i = i2 + 1;
            } else {
                Collections.sort(outProcs, ProcessState.COMPARATOR);
                return outProcs;
            }
        }
    }

    public void dumpCheckinLocked(PrintWriter pw, String reqPackage, int section) {
        ProcessStats processStats;
        String str = reqPackage;
        long now = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
        pw.println("vers,5");
        pw.print("period,");
        pw.print(this.mTimePeriodStartClockStr);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(this.mTimePeriodStartRealtime);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            pw.print(",shutdown");
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            pw.print(",sysprops");
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            pw.print(",complete");
            partial = false;
        }
        if (partial) {
            pw.print(",partial");
        }
        if (this.mHasSwappedOutPss) {
            pw.print(",swapped-out-pss");
        }
        pw.println();
        pw.print("config,");
        pw.println(this.mRuntime);
        if ((section & 14) != 0) {
            int ip = 0;
            while (true) {
                int ip2 = ip;
                int ip3 = pkgMap.size();
                if (ip2 >= ip3) {
                    break;
                }
                String pkgName = pkgMap.keyAt(ip2);
                if (str == null || str.equals(pkgName)) {
                    SparseArray<LongSparseArray<PackageState>> uids = pkgMap.valueAt(ip2);
                    int iu = 0;
                    while (true) {
                        int iu2 = iu;
                        int iu3 = uids.size();
                        if (iu2 < iu3) {
                            int uid = uids.keyAt(iu2);
                            LongSparseArray<PackageState> vpkgs = uids.valueAt(iu2);
                            int iv = 0;
                            while (true) {
                                int iv2 = iv;
                                int iv3 = vpkgs.size();
                                if (iv2 < iv3) {
                                    long vers = vpkgs.keyAt(iv2);
                                    PackageState pkgState = vpkgs.valueAt(iv2);
                                    int NPROCS = pkgState.mProcesses.size();
                                    int NSRVS = pkgState.mServices.size();
                                    int NASCS = pkgState.mAssociations.size();
                                    if ((section & 2) != 0) {
                                        int iproc = 0;
                                        while (true) {
                                            int iproc2 = iproc;
                                            if (iproc2 >= NPROCS) {
                                                break;
                                            }
                                            ProcessState proc = pkgState.mProcesses.valueAt(iproc2);
                                            proc.dumpPackageProcCheckin(pw, pkgName, uid, vers, pkgState.mProcesses.keyAt(iproc2), now);
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
                                        }
                                    }
                                    int NASCS2 = NASCS;
                                    LongSparseArray<PackageState> vpkgs2 = vpkgs;
                                    SparseArray<LongSparseArray<PackageState>> uids2 = uids;
                                    int iu4 = iu2;
                                    String pkgName2 = pkgName;
                                    int NSRVS2 = NSRVS;
                                    int ip4 = ip2;
                                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = pkgMap;
                                    PackageState pkgState2 = pkgState;
                                    if ((section & 4) != 0) {
                                        int isvc = 0;
                                        while (true) {
                                            int isvc2 = isvc;
                                            if (isvc2 >= NSRVS2) {
                                                break;
                                            }
                                            String serviceName = DumpUtils.collapseString(pkgName2, pkgState2.mServices.keyAt(isvc2));
                                            ServiceState svc = pkgState2.mServices.valueAt(isvc2);
                                            svc.dumpTimesCheckin(pw, pkgName2, uid, vers, serviceName, now);
                                            isvc = isvc2 + 1;
                                        }
                                    }
                                    if ((section & 8) != 0) {
                                        int iasc = 0;
                                        while (true) {
                                            int iasc2 = iasc;
                                            int NASCS3 = NASCS2;
                                            if (iasc2 < NASCS3) {
                                                String associationName = DumpUtils.collapseString(pkgName2, pkgState2.mAssociations.keyAt(iasc2));
                                                AssociationState asc = pkgState2.mAssociations.valueAt(iasc2);
                                                NASCS2 = NASCS3;
                                                asc.dumpTimesCheckin(pw, pkgName2, uid, vers, associationName, now);
                                                iasc = iasc2 + 1;
                                            }
                                        }
                                    }
                                    iv = iv2 + 1;
                                    pkgName = pkgName2;
                                    ip2 = ip4;
                                    pkgMap = pkgMap2;
                                    vpkgs = vpkgs2;
                                    uids = uids2;
                                    iu2 = iu4;
                                }
                            }
                            iu = iu2 + 1;
                        }
                    }
                }
                ip = ip2 + 1;
                pkgMap = pkgMap;
                str = reqPackage;
            }
        }
        if ((section & 1) != 0) {
            processStats = this;
            ArrayMap<String, SparseArray<ProcessState>> procMap = processStats.mProcesses.getMap();
            int ip5 = 0;
            while (true) {
                int ip6 = ip5;
                int ip7 = procMap.size();
                if (ip6 >= ip7) {
                    break;
                }
                String procName = procMap.keyAt(ip6);
                SparseArray<ProcessState> uids3 = procMap.valueAt(ip6);
                int iu5 = 0;
                while (true) {
                    int iu6 = iu5;
                    int iu7 = uids3.size();
                    if (iu6 < iu7) {
                        int uid2 = uids3.keyAt(iu6);
                        ProcessState procState = uids3.valueAt(iu6);
                        procState.dumpProcCheckin(pw, procName, uid2, now);
                        iu5 = iu6 + 1;
                    }
                }
                ip5 = ip6 + 1;
            }
        } else {
            processStats = this;
        }
        pw.print("total");
        DumpUtils.dumpAdjTimesCheckin(pw, SmsManager.REGEX_PREFIX_DELIMITER, processStats.mMemFactorDurations, processStats.mMemFactor, processStats.mStartTime, now);
        pw.println();
        int sysMemUsageCount = processStats.mSysMemUsage.getKeyCount();
        if (sysMemUsageCount > 0) {
            pw.print("sysmemusage");
            for (int i = 0; i < sysMemUsageCount; i++) {
                int key = processStats.mSysMemUsage.getKeyAt(i);
                int type = SparseMappingTable.getIdFromKey(key);
                pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
                DumpUtils.printProcStateTag(pw, type);
                for (int j = 0; j < 16; j++) {
                    if (j > 1) {
                        pw.print(SettingsStringUtil.DELIMITER);
                    }
                    pw.print(processStats.mSysMemUsage.getValue(key, j));
                }
            }
        }
        pw.println();
        TotalMemoryUseCollection totalMem = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        processStats.computeTotalMemoryUse(totalMem, now);
        pw.print("weights,");
        pw.print(totalMem.totalTime);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(totalMem.sysMemCachedWeight);
        pw.print(SettingsStringUtil.DELIMITER);
        pw.print(totalMem.sysMemSamples);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(totalMem.sysMemFreeWeight);
        pw.print(SettingsStringUtil.DELIMITER);
        pw.print(totalMem.sysMemSamples);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(totalMem.sysMemZRamWeight);
        pw.print(SettingsStringUtil.DELIMITER);
        pw.print(totalMem.sysMemSamples);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(totalMem.sysMemKernelWeight);
        pw.print(SettingsStringUtil.DELIMITER);
        pw.print(totalMem.sysMemSamples);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(totalMem.sysMemNativeWeight);
        pw.print(SettingsStringUtil.DELIMITER);
        pw.print(totalMem.sysMemSamples);
        for (int i2 = 0; i2 < 14; i2++) {
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(totalMem.processStateWeight[i2]);
            pw.print(SettingsStringUtil.DELIMITER);
            pw.print(totalMem.processStateSamples[i2]);
        }
        pw.println();
        int NPAGETYPES = processStats.mPageTypeLabels.size();
        for (int i3 = 0; i3 < NPAGETYPES; i3++) {
            pw.print("availablepages,");
            pw.print(processStats.mPageTypeLabels.get(i3));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(processStats.mPageTypeZones.get(i3));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            int[] sizes = processStats.mPageTypeSizes.get(i3);
            int N = sizes == null ? 0 : sizes.length;
            for (int j2 = 0; j2 < N; j2++) {
                if (j2 != 0) {
                    pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
                }
                pw.print(sizes[j2]);
            }
            pw.println();
        }
    }

    public void writeToProto(ProtoOutputStream proto, long now, int section) {
        proto.write(1112396529665L, this.mTimePeriodStartRealtime);
        proto.write(1112396529666L, this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        proto.write(1112396529667L, this.mTimePeriodStartUptime);
        proto.write(1112396529668L, this.mTimePeriodEndUptime);
        proto.write(1138166333445L, this.mRuntime);
        proto.write(1133871366150L, this.mHasSwappedOutPss);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            proto.write(2259152797703L, 3);
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            proto.write(2259152797703L, 4);
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            proto.write(2259152797703L, 1);
            partial = false;
        }
        if (partial) {
            proto.write(2259152797703L, 2);
        }
        int NPAGETYPES = this.mPageTypeLabels.size();
        for (int i = 0; i < NPAGETYPES; i++) {
            long token = proto.start(2246267895818L);
            proto.write(1120986464257L, this.mPageTypeNodes.get(i).intValue());
            proto.write(1138166333442L, this.mPageTypeZones.get(i));
            proto.write(1138166333443L, this.mPageTypeLabels.get(i));
            int[] sizes = this.mPageTypeSizes.get(i);
            int N = sizes == null ? 0 : sizes.length;
            for (int j = 0; j < N; j++) {
                proto.write(2220498092036L, sizes[j]);
            }
            proto.end(token);
        }
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        if ((section & 1) != 0) {
            int ip = 0;
            while (true) {
                int ip2 = ip;
                int ip3 = procMap.size();
                if (ip2 >= ip3) {
                    break;
                }
                String procName = procMap.keyAt(ip2);
                SparseArray<ProcessState> uids = procMap.valueAt(ip2);
                int iu = 0;
                while (true) {
                    int iu2 = iu;
                    int iu3 = uids.size();
                    if (iu2 < iu3) {
                        int uid = uids.keyAt(iu2);
                        ProcessState procState = uids.valueAt(iu2);
                        procState.writeToProto(proto, 2246267895816L, procName, uid, now);
                        iu = iu2 + 1;
                        uids = uids;
                    }
                }
                ip = ip2 + 1;
            }
        }
        if ((section & 14) != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap = this.mPackages.getMap();
            int ip4 = 0;
            while (true) {
                int ip5 = ip4;
                int ip6 = pkgMap.size();
                if (ip5 < ip6) {
                    SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip5);
                    int iu4 = 0;
                    while (true) {
                        int iu5 = iu4;
                        int iu6 = uids2.size();
                        if (iu5 < iu6) {
                            LongSparseArray<PackageState> vers = uids2.valueAt(iu5);
                            int iv = 0;
                            while (true) {
                                int iv2 = iv;
                                int iv3 = vers.size();
                                if (iv2 < iv3) {
                                    PackageState pkgState = vers.valueAt(iv2);
                                    pkgState.writeToProto(proto, 2246267895817L, now, section);
                                    iv = iv2 + 1;
                                    iu5 = iu5;
                                    vers = vers;
                                    uids2 = uids2;
                                }
                            }
                            iu4 = iu5 + 1;
                        }
                    }
                    ip4 = ip5 + 1;
                } else {
                    return;
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class ProcessStateHolder {
        public final long appVersion;
        public PackageState pkg;
        public ProcessState state;

        public ProcessStateHolder(long _appVersion) {
            this.appVersion = _appVersion;
        }
    }

    /* loaded from: classes4.dex */
    public static final class PackageState {
        public final String mPackageName;
        public final ProcessStats mProcessStats;
        public final int mUid;
        public final long mVersionCode;
        public final ArrayMap<String, ProcessState> mProcesses = new ArrayMap<>();
        public final ArrayMap<String, ServiceState> mServices = new ArrayMap<>();
        public final ArrayMap<String, AssociationState> mAssociations = new ArrayMap<>();

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
            long token = proto.start(fieldId);
            proto.write(1138166333441L, this.mPackageName);
            proto.write(1120986464258L, this.mUid);
            proto.write(1112396529667L, this.mVersionCode);
            int ia = 0;
            if ((section & 2) != 0) {
                int ip = 0;
                while (true) {
                    int ip2 = ip;
                    if (ip2 >= this.mProcesses.size()) {
                        break;
                    }
                    String procName = this.mProcesses.keyAt(ip2);
                    ProcessState procState = this.mProcesses.valueAt(ip2);
                    procState.writeToProto(proto, 2246267895812L, procName, this.mUid, now);
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
                    ServiceState serviceState = this.mServices.valueAt(is2);
                    serviceState.writeToProto(proto, 2246267895813L, now);
                    is = is2 + 1;
                }
            }
            if ((section & 8) != 0) {
                while (true) {
                    int ia2 = ia;
                    if (ia2 >= this.mAssociations.size()) {
                        break;
                    }
                    AssociationState ascState = this.mAssociations.valueAt(ia2);
                    ascState.writeToProto(proto, 2246267895814L, now);
                    ia = ia2 + 1;
                }
            }
            proto.end(token);
        }
    }

    /* loaded from: classes4.dex */
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

        void print(PrintWriter pw, long overallTime, boolean full) {
            if (this.totalTime > overallTime) {
                pw.print("*");
            }
            DumpUtils.printPercent(pw, this.totalTime / overallTime);
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

    /* loaded from: classes4.dex */
    public static class TotalMemoryUseCollection {
        public boolean hasSwappedOutPss;
        final int[] memStates;
        final int[] screenStates;
        public double sysMemCachedWeight;
        public double sysMemFreeWeight;
        public double sysMemKernelWeight;
        public double sysMemNativeWeight;
        public int sysMemSamples;
        public double sysMemZRamWeight;
        public long totalTime;
        public long[] processStatePss = new long[14];
        public double[] processStateWeight = new double[14];
        public long[] processStateTime = new long[14];
        public int[] processStateSamples = new int[14];
        public long[] sysMemUsage = new long[16];

        public TotalMemoryUseCollection(int[] _screenStates, int[] _memStates) {
            this.screenStates = _screenStates;
            this.memStates = _memStates;
        }
    }
}
