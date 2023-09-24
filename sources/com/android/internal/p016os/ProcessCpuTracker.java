package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Process;
import android.p007os.StrictMode;
import android.p007os.SystemClock;
import android.provider.MediaStore;
import android.provider.SettingsStringUtil;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.p016os.BatteryStatsImpl;
import com.android.internal.util.FastPrintWriter;
import com.ibm.icu.text.PluralRules;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/* renamed from: com.android.internal.os.ProcessCpuTracker */
/* loaded from: classes4.dex */
public class ProcessCpuTracker {
    private static final boolean DEBUG = false;
    static final int PROCESS_FULL_STAT_MAJOR_FAULTS = 2;
    static final int PROCESS_FULL_STAT_MINOR_FAULTS = 1;
    static final int PROCESS_FULL_STAT_STIME = 4;
    static final int PROCESS_FULL_STAT_UTIME = 3;
    static final int PROCESS_FULL_STAT_VSIZE = 5;
    static final int PROCESS_STAT_MAJOR_FAULTS = 1;
    static final int PROCESS_STAT_MINOR_FAULTS = 0;
    static final int PROCESS_STAT_STIME = 3;
    static final int PROCESS_STAT_UTIME = 2;
    private static final String TAG = "ProcessCpuTracker";
    private static final boolean localLOGV = false;
    private long mBaseIdleTime;
    private long mBaseIoWaitTime;
    private long mBaseIrqTime;
    private long mBaseSoftIrqTime;
    private long mBaseSystemTime;
    private long mBaseUserTime;
    private int[] mCurPids;
    private int[] mCurThreadPids;
    private long mCurrentSampleRealTime;
    private long mCurrentSampleTime;
    private long mCurrentSampleWallTime;
    private final boolean mIncludeThreads;
    private final long mJiffyMillis;
    private long mLastSampleRealTime;
    private long mLastSampleTime;
    private long mLastSampleWallTime;
    private int mRelIdleTime;
    private int mRelIoWaitTime;
    private int mRelIrqTime;
    private int mRelSoftIrqTime;
    private boolean mRelStatsAreGood;
    private int mRelSystemTime;
    private int mRelUserTime;
    private boolean mWorkingProcsSorted;
    private static final int[] PROCESS_STATS_FORMAT = {32, MetricsProto.MetricsEvent.DIALOG_WIFI_SKIP, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224};
    private static final int[] PROCESS_FULL_STATS_FORMAT = {32, 4640, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 32, 32, 32, 8224};
    private static final int[] SYSTEM_CPU_FORMAT = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    private static final int[] LOAD_AVERAGE_FORMAT = {16416, 16416, 16416};
    private static final Comparator<Stats> sLoadComparator = new Comparator<Stats>() { // from class: com.android.internal.os.ProcessCpuTracker.1
        @Override // java.util.Comparator
        public final int compare(Stats sta, Stats stb) {
            int ta = sta.rel_utime + sta.rel_stime;
            int tb = stb.rel_utime + stb.rel_stime;
            if (ta != tb) {
                return ta > tb ? -1 : 1;
            } else if (sta.added != stb.added) {
                return sta.added ? -1 : 1;
            } else if (sta.removed != stb.removed) {
                return sta.added ? -1 : 1;
            } else {
                return 0;
            }
        }
    };
    private final long[] mProcessStatsData = new long[4];
    private final long[] mSinglePidStatsData = new long[4];
    private final String[] mProcessFullStatsStringData = new String[6];
    private final long[] mProcessFullStatsData = new long[6];
    private final long[] mSystemCpuData = new long[7];
    private final float[] mLoadAverageData = new float[3];
    private float mLoad1 = 0.0f;
    private float mLoad5 = 0.0f;
    private float mLoad15 = 0.0f;
    private final ArrayList<Stats> mProcStats = new ArrayList<>();
    private final ArrayList<Stats> mWorkingProcs = new ArrayList<>();
    private boolean mFirst = true;

    /* renamed from: com.android.internal.os.ProcessCpuTracker$FilterStats */
    /* loaded from: classes4.dex */
    public interface FilterStats {
        boolean needed(Stats stats);
    }

    /* renamed from: com.android.internal.os.ProcessCpuTracker$Stats */
    /* loaded from: classes4.dex */
    public static class Stats {
        public boolean active;
        public boolean added;
        public String baseName;
        public long base_majfaults;
        public long base_minfaults;
        public long base_stime;
        public long base_uptime;
        public long base_utime;
        public BatteryStatsImpl.Uid.Proc batteryStats;
        final String cmdlineFile;
        public boolean interesting;
        @UnsupportedAppUsage
        public String name;
        public int nameWidth;
        public final int pid;
        public int rel_majfaults;
        public int rel_minfaults;
        @UnsupportedAppUsage
        public int rel_stime;
        @UnsupportedAppUsage
        public long rel_uptime;
        @UnsupportedAppUsage
        public int rel_utime;
        public boolean removed;
        final String statFile;
        final ArrayList<Stats> threadStats;
        final String threadsDir;
        public final int uid;
        public long vsize;
        public boolean working;
        final ArrayList<Stats> workingThreads;

        Stats(int _pid, int parentPid, boolean includeThreads) {
            this.pid = _pid;
            if (parentPid < 0) {
                File procDir = new File("/proc", Integer.toString(this.pid));
                this.uid = getUid(procDir.toString());
                this.statFile = new File(procDir, "stat").toString();
                this.cmdlineFile = new File(procDir, "cmdline").toString();
                this.threadsDir = new File(procDir, "task").toString();
                if (includeThreads) {
                    this.threadStats = new ArrayList<>();
                    this.workingThreads = new ArrayList<>();
                    return;
                }
                this.threadStats = null;
                this.workingThreads = null;
                return;
            }
            File taskDir = new File(new File(new File("/proc", Integer.toString(parentPid)), "task"), Integer.toString(this.pid));
            this.uid = getUid(taskDir.toString());
            this.statFile = new File(taskDir, "stat").toString();
            this.cmdlineFile = null;
            this.threadsDir = null;
            this.threadStats = null;
            this.workingThreads = null;
        }

        private static int getUid(String path) {
            try {
                return Os.stat(path).st_uid;
            } catch (ErrnoException e) {
                Slog.m50w(ProcessCpuTracker.TAG, "Failed to stat(" + path + "): " + e);
                return -1;
            }
        }
    }

    @UnsupportedAppUsage
    public ProcessCpuTracker(boolean includeThreads) {
        this.mIncludeThreads = includeThreads;
        long jiffyHz = Os.sysconf(OsConstants._SC_CLK_TCK);
        this.mJiffyMillis = 1000 / jiffyHz;
    }

    public void onLoadChanged(float load1, float load5, float load15) {
    }

    public int onMeasureProcessName(String name) {
        return 0;
    }

    public void init() {
        this.mFirst = true;
        update();
    }

    @UnsupportedAppUsage
    public void update() {
        long nowUptime;
        long nowRealtime;
        long nowWallTime;
        long nowUptime2 = SystemClock.uptimeMillis();
        long nowRealtime2 = SystemClock.elapsedRealtime();
        long nowWallTime2 = System.currentTimeMillis();
        long[] sysCpu = this.mSystemCpuData;
        char c = 1;
        if (Process.readProcFile("/proc/stat", SYSTEM_CPU_FORMAT, null, sysCpu, null)) {
            long usertime = (sysCpu[0] + sysCpu[1]) * this.mJiffyMillis;
            long systemtime = sysCpu[2] * this.mJiffyMillis;
            nowWallTime = nowWallTime2;
            long idletime = sysCpu[3] * this.mJiffyMillis;
            nowRealtime = nowRealtime2;
            long iowaittime = sysCpu[4] * this.mJiffyMillis;
            nowUptime = nowUptime2;
            long irqtime = sysCpu[5] * this.mJiffyMillis;
            long softirqtime = sysCpu[6] * this.mJiffyMillis;
            this.mRelUserTime = (int) (usertime - this.mBaseUserTime);
            this.mRelSystemTime = (int) (systemtime - this.mBaseSystemTime);
            this.mRelIoWaitTime = (int) (iowaittime - this.mBaseIoWaitTime);
            this.mRelIrqTime = (int) (irqtime - this.mBaseIrqTime);
            this.mRelSoftIrqTime = (int) (softirqtime - this.mBaseSoftIrqTime);
            this.mRelIdleTime = (int) (idletime - this.mBaseIdleTime);
            c = 1;
            this.mRelStatsAreGood = true;
            this.mBaseUserTime = usertime;
            this.mBaseSystemTime = systemtime;
            this.mBaseIoWaitTime = iowaittime;
            this.mBaseIrqTime = irqtime;
            this.mBaseSoftIrqTime = softirqtime;
            this.mBaseIdleTime = idletime;
        } else {
            nowUptime = nowUptime2;
            nowRealtime = nowRealtime2;
            nowWallTime = nowWallTime2;
        }
        this.mLastSampleTime = this.mCurrentSampleTime;
        this.mCurrentSampleTime = nowUptime;
        this.mLastSampleRealTime = this.mCurrentSampleRealTime;
        this.mCurrentSampleRealTime = nowRealtime;
        this.mLastSampleWallTime = this.mCurrentSampleWallTime;
        this.mCurrentSampleWallTime = nowWallTime;
        StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
        try {
            char c2 = c;
            this.mCurPids = collectStats("/proc", -1, this.mFirst, this.mCurPids, this.mProcStats);
            StrictMode.setThreadPolicy(savedPolicy);
            float[] loadAverages = this.mLoadAverageData;
            if (Process.readProcFile("/proc/loadavg", LOAD_AVERAGE_FORMAT, null, null, loadAverages)) {
                float load1 = loadAverages[0];
                float load5 = loadAverages[c2];
                float load15 = loadAverages[2];
                if (load1 != this.mLoad1 || load5 != this.mLoad5 || load15 != this.mLoad15) {
                    this.mLoad1 = load1;
                    this.mLoad5 = load5;
                    this.mLoad15 = load15;
                    onLoadChanged(load1, load5, load15);
                }
            }
            this.mWorkingProcsSorted = false;
            this.mFirst = false;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(savedPolicy);
            throw th;
        }
    }

    private int[] collectStats(String statsFile, int parentPid, boolean first, int[] curPids, ArrayList<Stats> allProcs) {
        int[] pids;
        boolean z;
        int[] pids2;
        int NP;
        int i;
        int pid;
        int i2;
        int NS;
        boolean z2;
        Stats st;
        long majfaults;
        boolean z3;
        long uptime;
        long minfaults;
        long majfaults2;
        int i3 = parentPid;
        ArrayList<Stats> arrayList = allProcs;
        int[] pids3 = Process.getPids(statsFile, curPids);
        boolean z4 = false;
        int NP2 = pids3 == null ? 0 : pids3.length;
        int NS2 = allProcs.size();
        int curStatsIndex = 0;
        int NS3 = NS2;
        int NS4 = 0;
        while (true) {
            int i4 = NS4;
            if (i4 >= NP2) {
                pids = pids3;
                z = true;
                break;
            }
            int pid2 = pids3[i4];
            if (pid2 < 0) {
                pids = pids3;
                z = true;
                break;
            }
            Stats st2 = curStatsIndex < NS3 ? arrayList.get(curStatsIndex) : null;
            if (st2 == null || st2.pid != pid2) {
                pids2 = pids3;
                NP = NP2;
                int NS5 = NS3;
                i = i4;
                if (st2 != null) {
                    pid = pid2;
                    if (st2.pid > pid) {
                        arrayList = allProcs;
                    } else {
                        st2.rel_utime = 0;
                        st2.rel_stime = 0;
                        st2.rel_minfaults = 0;
                        st2.rel_majfaults = 0;
                        st2.removed = true;
                        st2.working = true;
                        arrayList = allProcs;
                        arrayList.remove(curStatsIndex);
                        NS3 = NS5 - 1;
                        i--;
                    }
                } else {
                    pid = pid2;
                    arrayList = allProcs;
                }
                i2 = parentPid;
                Stats st3 = new Stats(pid, i2, this.mIncludeThreads);
                arrayList.add(curStatsIndex, st3);
                int curStatsIndex2 = curStatsIndex + 1;
                NS3 = NS5 + 1;
                String[] procStatsString = this.mProcessFullStatsStringData;
                long[] procStats = this.mProcessFullStatsData;
                st3.base_uptime = SystemClock.uptimeMillis();
                String path = st3.statFile.toString();
                if (Process.readProcFile(path, PROCESS_FULL_STATS_FORMAT, procStatsString, procStats, null)) {
                    st3.vsize = procStats[5];
                    st3.interesting = true;
                    st3.baseName = procStatsString[0];
                    st3.base_minfaults = procStats[1];
                    st3.base_majfaults = procStats[2];
                    st3.base_utime = procStats[3] * this.mJiffyMillis;
                    st3.base_stime = procStats[4] * this.mJiffyMillis;
                } else {
                    Slog.m50w(TAG, "Skipping unknown process pid " + pid);
                    st3.baseName = MediaStore.UNKNOWN_STRING;
                    st3.base_stime = 0L;
                    st3.base_utime = 0L;
                    st3.base_majfaults = 0L;
                    st3.base_minfaults = 0L;
                }
                if (i2 < 0) {
                    getName(st3, st3.cmdlineFile);
                    if (st3.threadStats != null) {
                        this.mCurThreadPids = collectStats(st3.threadsDir, pid, true, this.mCurThreadPids, st3.threadStats);
                    }
                } else if (st3.interesting) {
                    st3.name = st3.baseName;
                    st3.nameWidth = onMeasureProcessName(st3.name);
                }
                st3.rel_utime = 0;
                st3.rel_stime = 0;
                st3.rel_minfaults = 0;
                st3.rel_majfaults = 0;
                st3.added = true;
                if (!first && st3.interesting) {
                    st3.working = true;
                }
                curStatsIndex = curStatsIndex2;
                NS4 = i + 1;
                i3 = i2;
                NP2 = NP;
                pids3 = pids2;
                z4 = false;
            } else {
                st2.added = z4;
                st2.working = z4;
                int curStatsIndex3 = curStatsIndex + 1;
                if (st2.interesting) {
                    long uptime2 = SystemClock.uptimeMillis();
                    long[] procStats2 = this.mProcessStatsData;
                    if (Process.readProcFile(st2.statFile.toString(), PROCESS_STATS_FORMAT, null, procStats2, null)) {
                        long minfaults2 = procStats2[0];
                        long majfaults3 = procStats2[1];
                        long utime = procStats2[2] * this.mJiffyMillis;
                        NP = NP2;
                        long stime = this.mJiffyMillis * procStats2[3];
                        NS = NS3;
                        i = i4;
                        if (utime == st2.base_utime && stime == st2.base_stime) {
                            st2.rel_utime = 0;
                            st2.rel_stime = 0;
                            st2.rel_minfaults = 0;
                            st2.rel_majfaults = 0;
                            if (st2.active) {
                                st2.active = false;
                            }
                            pids2 = pids3;
                        } else {
                            if (st2.active) {
                                z2 = true;
                            } else {
                                z2 = true;
                                st2.active = true;
                            }
                            if (i3 < 0) {
                                getName(st2, st2.cmdlineFile);
                                if (st2.threadStats != null) {
                                    majfaults = majfaults3;
                                    uptime = uptime2;
                                    minfaults = minfaults2;
                                    majfaults2 = utime;
                                    st = st2;
                                    pids2 = pids3;
                                    z3 = true;
                                    this.mCurThreadPids = collectStats(st2.threadsDir, pid2, false, this.mCurThreadPids, st2.threadStats);
                                    st.rel_uptime = uptime - st.base_uptime;
                                    st.base_uptime = uptime;
                                    st.rel_utime = (int) (majfaults2 - st.base_utime);
                                    st.rel_stime = (int) (stime - st.base_stime);
                                    st.base_utime = majfaults2;
                                    st.base_stime = stime;
                                    st.rel_minfaults = (int) (minfaults - st.base_minfaults);
                                    st.rel_majfaults = (int) (majfaults - st.base_majfaults);
                                    st.base_minfaults = minfaults;
                                    st.base_majfaults = majfaults;
                                    st.working = z3;
                                }
                            }
                            st = st2;
                            majfaults = majfaults3;
                            pids2 = pids3;
                            z3 = z2;
                            uptime = uptime2;
                            minfaults = minfaults2;
                            majfaults2 = utime;
                            st.rel_uptime = uptime - st.base_uptime;
                            st.base_uptime = uptime;
                            st.rel_utime = (int) (majfaults2 - st.base_utime);
                            st.rel_stime = (int) (stime - st.base_stime);
                            st.base_utime = majfaults2;
                            st.base_stime = stime;
                            st.rel_minfaults = (int) (minfaults - st.base_minfaults);
                            st.rel_majfaults = (int) (majfaults - st.base_majfaults);
                            st.base_minfaults = minfaults;
                            st.base_majfaults = majfaults;
                            st.working = z3;
                        }
                    } else {
                        pids2 = pids3;
                        NP = NP2;
                        NS = NS3;
                        i = i4;
                    }
                } else {
                    pids2 = pids3;
                    NP = NP2;
                    NS = NS3;
                    i = i4;
                }
                curStatsIndex = curStatsIndex3;
                NS3 = NS;
                arrayList = allProcs;
            }
            i2 = parentPid;
            NS4 = i + 1;
            i3 = i2;
            NP2 = NP;
            pids3 = pids2;
            z4 = false;
        }
        while (curStatsIndex < NS3) {
            Stats st4 = arrayList.get(curStatsIndex);
            st4.rel_utime = 0;
            st4.rel_stime = 0;
            st4.rel_minfaults = 0;
            st4.rel_majfaults = 0;
            st4.removed = z;
            st4.working = z;
            arrayList.remove(curStatsIndex);
            NS3--;
        }
        return pids;
    }

    public long getCpuTimeForPid(int pid) {
        synchronized (this.mSinglePidStatsData) {
            String statFile = "/proc/" + pid + "/stat";
            long[] statsData = this.mSinglePidStatsData;
            if (Process.readProcFile(statFile, PROCESS_STATS_FORMAT, null, statsData, null)) {
                long time = statsData[2] + statsData[3];
                return this.mJiffyMillis * time;
            }
            return 0L;
        }
    }

    public final int getLastUserTime() {
        return this.mRelUserTime;
    }

    public final int getLastSystemTime() {
        return this.mRelSystemTime;
    }

    public final int getLastIoWaitTime() {
        return this.mRelIoWaitTime;
    }

    public final int getLastIrqTime() {
        return this.mRelIrqTime;
    }

    public final int getLastSoftIrqTime() {
        return this.mRelSoftIrqTime;
    }

    public final int getLastIdleTime() {
        return this.mRelIdleTime;
    }

    public final boolean hasGoodLastStats() {
        return this.mRelStatsAreGood;
    }

    public final float getTotalCpuPercent() {
        int denom = this.mRelUserTime + this.mRelSystemTime + this.mRelIrqTime + this.mRelIdleTime;
        if (denom <= 0) {
            return 0.0f;
        }
        return (((this.mRelUserTime + this.mRelSystemTime) + this.mRelIrqTime) * 100.0f) / denom;
    }

    final void buildWorkingProcs() {
        if (!this.mWorkingProcsSorted) {
            this.mWorkingProcs.clear();
            int N = this.mProcStats.size();
            for (int i = 0; i < N; i++) {
                Stats stats = this.mProcStats.get(i);
                if (stats.working) {
                    this.mWorkingProcs.add(stats);
                    if (stats.threadStats != null && stats.threadStats.size() > 1) {
                        stats.workingThreads.clear();
                        int M = stats.threadStats.size();
                        for (int j = 0; j < M; j++) {
                            Stats tstats = stats.threadStats.get(j);
                            if (tstats.working) {
                                stats.workingThreads.add(tstats);
                            }
                        }
                        Collections.sort(stats.workingThreads, sLoadComparator);
                    }
                }
            }
            Collections.sort(this.mWorkingProcs, sLoadComparator);
            this.mWorkingProcsSorted = true;
        }
    }

    public final int countStats() {
        return this.mProcStats.size();
    }

    public final Stats getStats(int index) {
        return this.mProcStats.get(index);
    }

    public final List<Stats> getStats(FilterStats filter) {
        ArrayList<Stats> statses = new ArrayList<>(this.mProcStats.size());
        int N = this.mProcStats.size();
        for (int p = 0; p < N; p++) {
            Stats stats = this.mProcStats.get(p);
            if (filter.needed(stats)) {
                statses.add(stats);
            }
        }
        return statses;
    }

    @UnsupportedAppUsage
    public final int countWorkingStats() {
        buildWorkingProcs();
        return this.mWorkingProcs.size();
    }

    @UnsupportedAppUsage
    public final Stats getWorkingStats(int index) {
        return this.mWorkingProcs.get(index);
    }

    public final String printCurrentLoad() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new FastPrintWriter((Writer) sw, false, 128);
        pw.print("Load: ");
        pw.print(this.mLoad1);
        pw.print(" / ");
        pw.print(this.mLoad5);
        pw.print(" / ");
        pw.println(this.mLoad15);
        pw.flush();
        return sw.toString();
    }

    public final String printCurrentState(long now) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        buildWorkingProcs();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new FastPrintWriter((Writer) sw, false, 1024);
        pw.print("CPU usage from ");
        if (now > this.mLastSampleTime) {
            pw.print(now - this.mLastSampleTime);
            pw.print("ms to ");
            pw.print(now - this.mCurrentSampleTime);
            pw.print("ms ago");
        } else {
            pw.print(this.mLastSampleTime - now);
            pw.print("ms to ");
            pw.print(this.mCurrentSampleTime - now);
            pw.print("ms later");
        }
        pw.print(" (");
        pw.print(sdf.format(new Date(this.mLastSampleWallTime)));
        pw.print(" to ");
        pw.print(sdf.format(new Date(this.mCurrentSampleWallTime)));
        pw.print(")");
        long sampleTime = this.mCurrentSampleTime - this.mLastSampleTime;
        long sampleRealTime = this.mCurrentSampleRealTime - this.mLastSampleRealTime;
        long percAwake = sampleRealTime > 0 ? (sampleTime * 100) / sampleRealTime : 0L;
        if (percAwake != 100) {
            pw.print(" with ");
            pw.print(percAwake);
            pw.print("% awake");
        }
        pw.println(SettingsStringUtil.DELIMITER);
        int totalTime = this.mRelUserTime + this.mRelSystemTime + this.mRelIoWaitTime + this.mRelIrqTime + this.mRelSoftIrqTime + this.mRelIdleTime;
        int N = this.mWorkingProcs.size();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= N) {
                PrintWriter pw2 = pw;
                printProcessCPU(pw2, "", -1, "TOTAL", totalTime, this.mRelUserTime, this.mRelSystemTime, this.mRelIoWaitTime, this.mRelIrqTime, this.mRelSoftIrqTime, 0, 0);
                pw2.flush();
                return sw.toString();
            }
            Stats st = this.mWorkingProcs.get(i2);
            StringWriter sw2 = sw;
            long percAwake2 = percAwake;
            int N2 = N;
            PrintWriter pw3 = pw;
            SimpleDateFormat sdf2 = sdf;
            printProcessCPU(pw, st.added ? " +" : st.removed ? " -" : "  ", st.pid, st.name, (int) st.rel_uptime, st.rel_utime, st.rel_stime, 0, 0, 0, st.rel_minfaults, st.rel_majfaults);
            Stats st2 = st;
            if (!st2.removed && st2.workingThreads != null) {
                int M = st2.workingThreads.size();
                int j = 0;
                while (true) {
                    int j2 = j;
                    if (j2 < M) {
                        Stats tst = st2.workingThreads.get(j2);
                        printProcessCPU(pw3, tst.added ? "   +" : tst.removed ? "   -" : "    ", tst.pid, tst.name, (int) st2.rel_uptime, tst.rel_utime, tst.rel_stime, 0, 0, 0, 0, 0);
                        j = j2 + 1;
                        M = M;
                        st2 = st2;
                    }
                }
            }
            i = i2 + 1;
            sw = sw2;
            pw = pw3;
            sdf = sdf2;
            percAwake = percAwake2;
            N = N2;
        }
    }

    private void printRatio(PrintWriter pw, long numerator, long denominator) {
        long thousands = (1000 * numerator) / denominator;
        long hundreds = thousands / 10;
        pw.print(hundreds);
        if (hundreds < 10) {
            long remainder = thousands - (10 * hundreds);
            if (remainder != 0) {
                pw.print('.');
                pw.print(remainder);
            }
        }
    }

    private void printProcessCPU(PrintWriter pw, String prefix, int pid, String label, int totalTime, int user, int system, int iowait, int irq, int softIrq, int minFaults, int majFaults) {
        pw.print(prefix);
        int totalTime2 = totalTime == 0 ? 1 : totalTime;
        printRatio(pw, user + system + iowait + irq + softIrq, totalTime2);
        pw.print("% ");
        if (pid >= 0) {
            pw.print(pid);
            pw.print("/");
        }
        pw.print(label);
        pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
        printRatio(pw, user, totalTime2);
        pw.print("% user + ");
        printRatio(pw, system, totalTime2);
        pw.print("% kernel");
        if (iowait > 0) {
            pw.print(" + ");
            printRatio(pw, iowait, totalTime2);
            pw.print("% iowait");
        }
        if (irq > 0) {
            pw.print(" + ");
            printRatio(pw, irq, totalTime2);
            pw.print("% irq");
        }
        if (softIrq > 0) {
            pw.print(" + ");
            printRatio(pw, softIrq, totalTime2);
            pw.print("% softirq");
        }
        if (minFaults > 0 || majFaults > 0) {
            pw.print(" / faults:");
            if (minFaults > 0) {
                pw.print(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                pw.print(minFaults);
                pw.print(" minor");
            }
            if (majFaults > 0) {
                pw.print(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                pw.print(majFaults);
                pw.print(" major");
            }
        }
        pw.println();
    }

    private void getName(Stats st, String cmdlineFile) {
        int i;
        String newName = st.name;
        if (st.name == null || st.name.equals("app_process") || st.name.equals("<pre-initialized>")) {
            String cmdName = ProcStatsUtil.readTerminatedProcFile(cmdlineFile, (byte) 0);
            if (cmdName != null && cmdName.length() > 1 && (i = (newName = cmdName).lastIndexOf("/")) > 0 && i < newName.length() - 1) {
                newName = newName.substring(i + 1);
            }
            if (newName == null) {
                newName = st.baseName;
            }
        }
        if (st.name == null || !newName.equals(st.name)) {
            st.name = newName;
            st.nameWidth = onMeasureProcessName(st.name);
        }
    }
}