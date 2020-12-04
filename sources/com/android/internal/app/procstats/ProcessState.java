package com.android.internal.app.procstats;

import android.app.job.JobInfo;
import android.bluetooth.BluetoothHidDevice;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Parcel;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.content.NativeLibraryHelper;
import java.io.PrintWriter;
import java.util.Comparator;

public final class ProcessState {
    public static final Comparator<ProcessState> COMPARATOR = new Comparator<ProcessState>() {
        public int compare(ProcessState lhs, ProcessState rhs) {
            if (lhs.mTmpTotalTime < rhs.mTmpTotalTime) {
                return -1;
            }
            if (lhs.mTmpTotalTime > rhs.mTmpTotalTime) {
                return 1;
            }
            return 0;
        }
    };
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_PARCEL = false;
    static final int[] PROCESS_STATE_TO_STATE = {0, 0, 1, 2, 2, 2, 2, 2, 3, 3, 4, 5, 7, 1, 8, 9, 10, 11, 12, 11, 13};
    private static final String TAG = "ProcessStats";
    private boolean mActive;
    private long mAvgCachedKillPss;
    private ProcessState mCommonProcess;
    private int mCurCombinedState = -1;
    private boolean mDead;
    private final DurationsTable mDurations;
    private int mLastPssState = -1;
    private long mLastPssTime;
    private long mMaxCachedKillPss;
    private long mMinCachedKillPss;
    private boolean mMultiPackage;
    private final String mName;
    private int mNumActiveServices;
    private int mNumCachedKill;
    private int mNumExcessiveCpu;
    private int mNumStartedServices;
    private final String mPackage;
    private final PssTable mPssTable;
    private long mStartTime;
    private final ProcessStats mStats;
    /* access modifiers changed from: private */
    public long mTmpTotalTime;
    private long mTotalRunningDuration;
    private final long[] mTotalRunningPss = new long[10];
    private long mTotalRunningStartTime;
    private final int mUid;
    private final long mVersion;
    public ProcessState tmpFoundSubProc;
    public int tmpNumInUse;

    static class PssAggr {
        long pss = 0;
        long samples = 0;

        PssAggr() {
        }

        /* access modifiers changed from: package-private */
        public void add(long newPss, long newSamples) {
            this.pss = ((long) ((((double) this.pss) * ((double) this.samples)) + (((double) newPss) * ((double) newSamples)))) / (this.samples + newSamples);
            this.samples += newSamples;
        }
    }

    public ProcessState(ProcessStats processStats, String pkg, int uid, long vers, String name) {
        this.mStats = processStats;
        this.mName = name;
        this.mCommonProcess = this;
        this.mPackage = pkg;
        this.mUid = uid;
        this.mVersion = vers;
        this.mDurations = new DurationsTable(processStats.mTableData);
        this.mPssTable = new PssTable(processStats.mTableData);
    }

    public ProcessState(ProcessState commonProcess, String pkg, int uid, long vers, String name, long now) {
        this.mStats = commonProcess.mStats;
        this.mName = name;
        this.mCommonProcess = commonProcess;
        this.mPackage = pkg;
        this.mUid = uid;
        this.mVersion = vers;
        this.mCurCombinedState = commonProcess.mCurCombinedState;
        this.mStartTime = now;
        if (this.mCurCombinedState != -1) {
            this.mTotalRunningStartTime = now;
        }
        this.mDurations = new DurationsTable(commonProcess.mStats.mTableData);
        this.mPssTable = new PssTable(commonProcess.mStats.mTableData);
    }

    public ProcessState clone(long now) {
        ProcessState pnew = new ProcessState(this, this.mPackage, this.mUid, this.mVersion, this.mName, now);
        pnew.mDurations.addDurations(this.mDurations);
        pnew.mPssTable.copyFrom(this.mPssTable, 10);
        System.arraycopy(this.mTotalRunningPss, 0, pnew.mTotalRunningPss, 0, 10);
        pnew.mTotalRunningDuration = getTotalRunningDuration(now);
        pnew.mNumExcessiveCpu = this.mNumExcessiveCpu;
        pnew.mNumCachedKill = this.mNumCachedKill;
        pnew.mMinCachedKillPss = this.mMinCachedKillPss;
        pnew.mAvgCachedKillPss = this.mAvgCachedKillPss;
        pnew.mMaxCachedKillPss = this.mMaxCachedKillPss;
        pnew.mActive = this.mActive;
        pnew.mNumActiveServices = this.mNumActiveServices;
        pnew.mNumStartedServices = this.mNumStartedServices;
        return pnew;
    }

    public String getName() {
        return this.mName;
    }

    public ProcessState getCommonProcess() {
        return this.mCommonProcess;
    }

    public void makeStandalone() {
        this.mCommonProcess = this;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getVersion() {
        return this.mVersion;
    }

    public boolean isMultiPackage() {
        return this.mMultiPackage;
    }

    public void setMultiPackage(boolean val) {
        this.mMultiPackage = val;
    }

    public int getDurationsBucketCount() {
        return this.mDurations.getKeyCount();
    }

    public void add(ProcessState other) {
        this.mDurations.addDurations(other.mDurations);
        this.mPssTable.mergeStats(other.mPssTable);
        this.mNumExcessiveCpu += other.mNumExcessiveCpu;
        if (other.mNumCachedKill > 0) {
            addCachedKill(other.mNumCachedKill, other.mMinCachedKillPss, other.mAvgCachedKillPss, other.mMaxCachedKillPss);
        }
    }

    public void resetSafely(long now) {
        this.mDurations.resetTable();
        this.mPssTable.resetTable();
        this.mStartTime = now;
        this.mLastPssState = -1;
        this.mLastPssTime = 0;
        this.mNumExcessiveCpu = 0;
        this.mNumCachedKill = 0;
        this.mMaxCachedKillPss = 0;
        this.mAvgCachedKillPss = 0;
        this.mMinCachedKillPss = 0;
    }

    public void makeDead() {
        this.mDead = true;
    }

    private void ensureNotDead() {
        if (this.mDead) {
            Slog.w("ProcessStats", "ProcessState dead: name=" + this.mName + " pkg=" + this.mPackage + " uid=" + this.mUid + " common.name=" + this.mCommonProcess.mName);
        }
    }

    public void writeToParcel(Parcel out, long now) {
        out.writeInt(this.mMultiPackage ? 1 : 0);
        this.mDurations.writeToParcel(out);
        this.mPssTable.writeToParcel(out);
        for (int i = 0; i < 10; i++) {
            out.writeLong(this.mTotalRunningPss[i]);
        }
        out.writeLong(getTotalRunningDuration(now));
        out.writeInt(0);
        out.writeInt(this.mNumExcessiveCpu);
        out.writeInt(this.mNumCachedKill);
        if (this.mNumCachedKill > 0) {
            out.writeLong(this.mMinCachedKillPss);
            out.writeLong(this.mAvgCachedKillPss);
            out.writeLong(this.mMaxCachedKillPss);
        }
    }

    public boolean readFromParcel(Parcel in, boolean fully) {
        boolean multiPackage = in.readInt() != 0;
        if (fully) {
            this.mMultiPackage = multiPackage;
        }
        if (!this.mDurations.readFromParcel(in) || !this.mPssTable.readFromParcel(in)) {
            return false;
        }
        for (int i = 0; i < 10; i++) {
            this.mTotalRunningPss[i] = in.readLong();
        }
        this.mTotalRunningDuration = in.readLong();
        in.readInt();
        this.mNumExcessiveCpu = in.readInt();
        this.mNumCachedKill = in.readInt();
        if (this.mNumCachedKill > 0) {
            this.mMinCachedKillPss = in.readLong();
            this.mAvgCachedKillPss = in.readLong();
            this.mMaxCachedKillPss = in.readLong();
        } else {
            this.mMaxCachedKillPss = 0;
            this.mAvgCachedKillPss = 0;
            this.mMinCachedKillPss = 0;
        }
        return true;
    }

    public void makeActive() {
        ensureNotDead();
        this.mActive = true;
    }

    public void makeInactive() {
        this.mActive = false;
    }

    public boolean isInUse() {
        return this.mActive || this.mNumActiveServices > 0 || this.mNumStartedServices > 0 || this.mCurCombinedState != -1;
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean hasAnyData() {
        if (this.mDurations.getKeyCount() == 0 && this.mCurCombinedState == -1 && this.mPssTable.getKeyCount() == 0 && this.mTotalRunningPss[0] == 0) {
            return false;
        }
        return true;
    }

    public void setState(int state, int memFactor, long now, ArrayMap<String, ProcessStats.ProcessStateHolder> pkgList) {
        int state2;
        if (state < 0) {
            state2 = this.mNumStartedServices > 0 ? (memFactor * 14) + 6 : -1;
        } else {
            state2 = PROCESS_STATE_TO_STATE[state] + (memFactor * 14);
        }
        this.mCommonProcess.setCombinedState(state2, now);
        if (this.mCommonProcess.mMultiPackage && pkgList != null) {
            for (int ip = pkgList.size() - 1; ip >= 0; ip--) {
                pullFixedProc(pkgList, ip).setCombinedState(state2, now);
            }
        }
    }

    public void setCombinedState(int state, long now) {
        ensureNotDead();
        if (!this.mDead && this.mCurCombinedState != state) {
            commitStateTime(now);
            if (state == -1) {
                this.mTotalRunningDuration += now - this.mTotalRunningStartTime;
                this.mTotalRunningStartTime = 0;
            } else if (this.mCurCombinedState == -1) {
                this.mTotalRunningDuration = 0;
                this.mTotalRunningStartTime = now;
                for (int i = 9; i >= 0; i--) {
                    this.mTotalRunningPss[i] = 0;
                }
            }
            this.mCurCombinedState = state;
        }
    }

    public int getCombinedState() {
        return this.mCurCombinedState;
    }

    public void commitStateTime(long now) {
        if (this.mCurCombinedState != -1) {
            long dur = now - this.mStartTime;
            if (dur > 0) {
                this.mDurations.addDuration(this.mCurCombinedState, dur);
            }
            this.mTotalRunningDuration += now - this.mTotalRunningStartTime;
            this.mTotalRunningStartTime = now;
        }
        this.mStartTime = now;
    }

    public void incActiveServices(String serviceName) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.incActiveServices(serviceName);
        }
        this.mNumActiveServices++;
    }

    public void decActiveServices(String serviceName) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.decActiveServices(serviceName);
        }
        this.mNumActiveServices--;
        if (this.mNumActiveServices < 0) {
            Slog.wtfStack("ProcessStats", "Proc active services underrun: pkg=" + this.mPackage + " uid=" + this.mUid + " proc=" + this.mName + " service=" + serviceName);
            this.mNumActiveServices = 0;
        }
    }

    public void incStartedServices(int memFactor, long now, String serviceName) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.incStartedServices(memFactor, now, serviceName);
        }
        this.mNumStartedServices++;
        if (this.mNumStartedServices == 1 && this.mCurCombinedState == -1) {
            setCombinedState((memFactor * 14) + 6, now);
        }
    }

    public void decStartedServices(int memFactor, long now, String serviceName) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.decStartedServices(memFactor, now, serviceName);
        }
        this.mNumStartedServices--;
        if (this.mNumStartedServices == 0 && this.mCurCombinedState % 14 == 6) {
            setCombinedState(-1, now);
        } else if (this.mNumStartedServices < 0) {
            Slog.wtfStack("ProcessStats", "Proc started services underrun: pkg=" + this.mPackage + " uid=" + this.mUid + " name=" + this.mName);
            this.mNumStartedServices = 0;
        }
    }

    public void addPss(long pss, long uss, long rss, boolean always, int type, long duration, ArrayMap<String, ProcessStats.ProcessStateHolder> pkgList) {
        ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap = pkgList;
        ensureNotDead();
        switch (type) {
            case 0:
                this.mStats.mInternalSinglePssCount++;
                this.mStats.mInternalSinglePssTime += duration;
                break;
            case 1:
                this.mStats.mInternalAllMemPssCount++;
                this.mStats.mInternalAllMemPssTime += duration;
                break;
            case 2:
                this.mStats.mInternalAllPollPssCount++;
                this.mStats.mInternalAllPollPssTime += duration;
                break;
            case 3:
                this.mStats.mExternalPssCount++;
                this.mStats.mExternalPssTime += duration;
                break;
            case 4:
                this.mStats.mExternalSlowPssCount++;
                this.mStats.mExternalSlowPssTime += duration;
                break;
        }
        if (always || this.mLastPssState != this.mCurCombinedState || SystemClock.uptimeMillis() >= this.mLastPssTime + JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS) {
            this.mLastPssState = this.mCurCombinedState;
            this.mLastPssTime = SystemClock.uptimeMillis();
            if (this.mCurCombinedState != -1) {
                long j = pss;
                long j2 = pss;
                long j3 = pss;
                long j4 = uss;
                long j5 = uss;
                long j6 = uss;
                long j7 = rss;
                long j8 = rss;
                long j9 = rss;
                this.mCommonProcess.mPssTable.mergeStats(this.mCurCombinedState, 1, j, j2, j3, j4, j5, j6, j7, j8, j9);
                PssTable.mergeStats(this.mCommonProcess.mTotalRunningPss, 0, 1, j, j2, j3, j4, j5, j6, j7, j8, j9);
                if (this.mCommonProcess.mMultiPackage && arrayMap != null) {
                    for (int ip = pkgList.size() - 1; ip >= 0; ip--) {
                        ProcessState fixedProc = pullFixedProc(arrayMap, ip);
                        fixedProc.mPssTable.mergeStats(this.mCurCombinedState, 1, pss, pss, pss, uss, uss, uss, rss, rss, rss);
                        PssTable.mergeStats(fixedProc.mTotalRunningPss, 0, 1, pss, pss, pss, uss, uss, uss, rss, rss, rss);
                    }
                }
            }
        }
    }

    public void reportExcessiveCpu(ArrayMap<String, ProcessStats.ProcessStateHolder> pkgList) {
        ensureNotDead();
        this.mCommonProcess.mNumExcessiveCpu++;
        if (this.mCommonProcess.mMultiPackage) {
            for (int ip = pkgList.size() - 1; ip >= 0; ip--) {
                pullFixedProc(pkgList, ip).mNumExcessiveCpu++;
            }
        }
    }

    private void addCachedKill(int num, long minPss, long avgPss, long maxPss) {
        if (this.mNumCachedKill <= 0) {
            this.mNumCachedKill = num;
            this.mMinCachedKillPss = minPss;
            this.mAvgCachedKillPss = avgPss;
            this.mMaxCachedKillPss = maxPss;
            return;
        }
        if (minPss < this.mMinCachedKillPss) {
            this.mMinCachedKillPss = minPss;
        }
        if (maxPss > this.mMaxCachedKillPss) {
            this.mMaxCachedKillPss = maxPss;
        }
        this.mAvgCachedKillPss = (long) (((((double) this.mAvgCachedKillPss) * ((double) this.mNumCachedKill)) + ((double) avgPss)) / ((double) (this.mNumCachedKill + num)));
        this.mNumCachedKill += num;
    }

    public void reportCachedKill(ArrayMap<String, ProcessStats.ProcessStateHolder> pkgList, long pss) {
        ensureNotDead();
        this.mCommonProcess.addCachedKill(1, pss, pss, pss);
        if (this.mCommonProcess.mMultiPackage) {
            for (int ip = pkgList.size() - 1; ip >= 0; ip--) {
                pullFixedProc(pkgList, ip).addCachedKill(1, pss, pss, pss);
            }
        }
    }

    public ProcessState pullFixedProc(String pkgName) {
        if (!this.mMultiPackage) {
            return this;
        }
        LongSparseArray<ProcessStats.PackageState> vpkg = this.mStats.mPackages.get(pkgName, this.mUid);
        if (vpkg != null) {
            ProcessStats.PackageState pkg = vpkg.get(this.mVersion);
            if (pkg != null) {
                ProcessState proc = pkg.mProcesses.get(this.mName);
                if (proc != null) {
                    return proc;
                }
                throw new IllegalStateException("Didn't create per-package process " + this.mName + " in pkg " + pkgName + " / " + this.mUid + " vers " + this.mVersion);
            }
            throw new IllegalStateException("Didn't find package " + pkgName + " / " + this.mUid + " vers " + this.mVersion);
        }
        throw new IllegalStateException("Didn't find package " + pkgName + " / " + this.mUid);
    }

    private ProcessState pullFixedProc(ArrayMap<String, ProcessStats.ProcessStateHolder> pkgList, int index) {
        ProcessStats.ProcessStateHolder holder = pkgList.valueAt(index);
        ProcessState proc = holder.state;
        if (this.mDead && proc.mCommonProcess != proc) {
            Log.wtf("ProcessStats", "Pulling dead proc: name=" + this.mName + " pkg=" + this.mPackage + " uid=" + this.mUid + " common.name=" + this.mCommonProcess.mName);
            proc = this.mStats.getProcessStateLocked(proc.mPackage, proc.mUid, proc.mVersion, proc.mName);
        }
        if (proc.mMultiPackage) {
            LongSparseArray<ProcessStats.PackageState> vpkg = this.mStats.mPackages.get(pkgList.keyAt(index), proc.mUid);
            if (vpkg != null) {
                ProcessStats.PackageState expkg = vpkg.get(proc.mVersion);
                if (expkg != null) {
                    String savedName = proc.mName;
                    proc = expkg.mProcesses.get(proc.mName);
                    if (proc != null) {
                        holder.state = proc;
                    } else {
                        throw new IllegalStateException("Didn't create per-package process " + savedName + " in pkg " + expkg.mPackageName + "/" + expkg.mUid);
                    }
                } else {
                    throw new IllegalStateException("No existing package " + pkgList.keyAt(index) + "/" + proc.mUid + " for multi-proc " + proc.mName + " version " + proc.mVersion);
                }
            } else {
                throw new IllegalStateException("No existing package " + pkgList.keyAt(index) + "/" + proc.mUid + " for multi-proc " + proc.mName);
            }
        }
        return proc;
    }

    public long getTotalRunningDuration(long now) {
        long j = this.mTotalRunningDuration;
        long j2 = 0;
        if (this.mTotalRunningStartTime != 0) {
            j2 = now - this.mTotalRunningStartTime;
        }
        return j + j2;
    }

    public long getDuration(int state, long now) {
        long time = this.mDurations.getValueForId((byte) state);
        if (this.mCurCombinedState == state) {
            return time + (now - this.mStartTime);
        }
        return time;
    }

    public long getPssSampleCount(int state) {
        return this.mPssTable.getValueForId((byte) state, 0);
    }

    public long getPssMinimum(int state) {
        return this.mPssTable.getValueForId((byte) state, 1);
    }

    public long getPssAverage(int state) {
        return this.mPssTable.getValueForId((byte) state, 2);
    }

    public long getPssMaximum(int state) {
        return this.mPssTable.getValueForId((byte) state, 3);
    }

    public long getPssUssMinimum(int state) {
        return this.mPssTable.getValueForId((byte) state, 4);
    }

    public long getPssUssAverage(int state) {
        return this.mPssTable.getValueForId((byte) state, 5);
    }

    public long getPssUssMaximum(int state) {
        return this.mPssTable.getValueForId((byte) state, 6);
    }

    public long getPssRssMinimum(int state) {
        return this.mPssTable.getValueForId((byte) state, 7);
    }

    public long getPssRssAverage(int state) {
        return this.mPssTable.getValueForId((byte) state, 8);
    }

    public long getPssRssMaximum(int state) {
        return this.mPssTable.getValueForId((byte) state, 9);
    }

    public void aggregatePss(ProcessStats.TotalMemoryUseCollection data, long now) {
        long time;
        long avg;
        long samples;
        ProcessState processState = this;
        ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection = data;
        PssAggr fgPss = new PssAggr();
        PssAggr bgPss = new PssAggr();
        PssAggr cachedPss = new PssAggr();
        boolean havePss = false;
        for (int i = 0; i < processState.mDurations.getKeyCount(); i++) {
            int type = SparseMappingTable.getIdFromKey(processState.mDurations.getKeyAt(i));
            int procState = type % 14;
            boolean havePss2 = havePss;
            long samples2 = processState.getPssSampleCount(type);
            if (samples2 > 0) {
                long avg2 = processState.getPssAverage(type);
                havePss2 = true;
                if (procState <= 2) {
                    fgPss.add(avg2, samples2);
                } else if (procState <= 7) {
                    bgPss.add(avg2, samples2);
                } else {
                    cachedPss.add(avg2, samples2);
                }
            }
            havePss = havePss2;
        }
        if (havePss) {
            boolean fgHasBg = false;
            boolean fgHasCached = false;
            boolean bgHasCached = false;
            if (fgPss.samples < 3 && bgPss.samples > 0) {
                fgHasBg = true;
                fgPss.add(bgPss.pss, bgPss.samples);
            }
            if (fgPss.samples < 3 && cachedPss.samples > 0) {
                fgHasCached = true;
                fgPss.add(cachedPss.pss, cachedPss.samples);
            }
            if (bgPss.samples < 3 && cachedPss.samples > 0) {
                bgHasCached = true;
                bgPss.add(cachedPss.pss, cachedPss.samples);
            }
            if (bgPss.samples < 3 && !fgHasBg && fgPss.samples > 0) {
                bgPss.add(fgPss.pss, fgPss.samples);
            }
            if (cachedPss.samples < 3 && !bgHasCached && bgPss.samples > 0) {
                cachedPss.add(bgPss.pss, bgPss.samples);
            }
            if (cachedPss.samples < 3 && !fgHasCached && fgPss.samples > 0) {
                cachedPss.add(fgPss.pss, fgPss.samples);
            }
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 < processState.mDurations.getKeyCount()) {
                    int key = processState.mDurations.getKeyAt(i3);
                    byte idFromKey = SparseMappingTable.getIdFromKey(key);
                    long time2 = processState.mDurations.getValue(key);
                    if (processState.mCurCombinedState == idFromKey) {
                        time2 += now - processState.mStartTime;
                    }
                    int procState2 = idFromKey % BluetoothHidDevice.ERROR_RSP_UNKNOWN;
                    long[] jArr = totalMemoryUseCollection.processStateTime;
                    jArr[procState2] = jArr[procState2] + time2;
                    long samples3 = processState.getPssSampleCount(idFromKey);
                    if (samples3 > 0) {
                        time = time2;
                        samples = samples3;
                        avg = processState.getPssAverage(idFromKey);
                    } else if (procState2 <= 2) {
                        time = time2;
                        samples = fgPss.samples;
                        avg = fgPss.pss;
                    } else {
                        time = time2;
                        if (procState2 <= 7) {
                            avg = bgPss.pss;
                            samples = bgPss.samples;
                        } else {
                            samples = cachedPss.samples;
                            avg = cachedPss.pss;
                        }
                    }
                    PssAggr fgPss2 = fgPss;
                    double newAvg = ((((double) totalMemoryUseCollection.processStatePss[procState2]) * ((double) totalMemoryUseCollection.processStateSamples[procState2])) + (((double) avg) * ((double) samples))) / ((double) (((long) totalMemoryUseCollection.processStateSamples[procState2]) + samples));
                    totalMemoryUseCollection.processStatePss[procState2] = (long) newAvg;
                    int[] iArr = totalMemoryUseCollection.processStateSamples;
                    iArr[procState2] = (int) (((long) iArr[procState2]) + samples);
                    double[] dArr = totalMemoryUseCollection.processStateWeight;
                    double d = newAvg;
                    int i4 = key;
                    byte b = idFromKey;
                    dArr[procState2] = dArr[procState2] + (((double) avg) * ((double) time));
                    i2 = i3 + 1;
                    fgPss = fgPss2;
                    bgPss = bgPss;
                    cachedPss = cachedPss;
                    fgHasBg = fgHasBg;
                    fgHasCached = fgHasCached;
                    bgHasCached = bgHasCached;
                    processState = this;
                    totalMemoryUseCollection = data;
                } else {
                    PssAggr pssAggr = bgPss;
                    PssAggr pssAggr2 = cachedPss;
                    boolean z = fgHasBg;
                    boolean z2 = fgHasCached;
                    boolean z3 = bgHasCached;
                    return;
                }
            }
        }
    }

    public long computeProcessTimeLocked(int[] screenStates, int[] memStates, int[] procStates, long now) {
        long totalTime = 0;
        for (int is = 0; is < screenStates.length; is++) {
            int im = 0;
            while (im < memStates.length) {
                long totalTime2 = totalTime;
                for (int i : procStates) {
                    totalTime2 += getDuration(((screenStates[is] + memStates[im]) * 14) + i, now);
                }
                im++;
                totalTime = totalTime2;
            }
        }
        this.mTmpTotalTime = totalTime;
        return totalTime;
    }

    public void dumpSummary(PrintWriter pw, String prefix, String header, int[] screenStates, int[] memStates, int[] procStates, long now, long totalTime) {
        PrintWriter printWriter = pw;
        String str = header;
        pw.print(prefix);
        printWriter.print("* ");
        if (str != null) {
            printWriter.print(str);
        }
        printWriter.print(this.mName);
        printWriter.print(" / ");
        UserHandle.formatUid(printWriter, this.mUid);
        printWriter.print(" / v");
        printWriter.print(this.mVersion);
        printWriter.println(SettingsStringUtil.DELIMITER);
        PrintWriter printWriter2 = pw;
        String str2 = prefix;
        int[] iArr = screenStates;
        int[] iArr2 = memStates;
        long j = now;
        long j2 = totalTime;
        dumpProcessSummaryDetails(printWriter2, str2, DumpUtils.STATE_LABEL_TOTAL, iArr, iArr2, procStates, j, j2, true);
        dumpProcessSummaryDetails(printWriter2, str2, DumpUtils.STATE_LABELS[0], iArr, iArr2, new int[]{0}, j, j2, true);
        dumpProcessSummaryDetails(printWriter2, str2, DumpUtils.STATE_LABELS[1], iArr, iArr2, new int[]{1}, j, j2, true);
        PrintWriter printWriter3 = pw;
        dumpProcessSummaryDetails(printWriter3, str2, DumpUtils.STATE_LABELS[2], iArr, iArr2, new int[]{2}, j, j2, true);
        dumpProcessSummaryDetails(printWriter3, str2, DumpUtils.STATE_LABELS[3], iArr, iArr2, new int[]{3}, j, j2, true);
        dumpProcessSummaryDetails(pw, str2, DumpUtils.STATE_LABELS[4], iArr, iArr2, new int[]{4}, j, j2, true);
        dumpProcessSummaryDetails(pw, str2, DumpUtils.STATE_LABELS[5], iArr, iArr2, new int[]{5}, j, j2, true);
        dumpProcessSummaryDetails(pw, str2, DumpUtils.STATE_LABELS[6], iArr, iArr2, new int[]{6}, j, j2, true);
        dumpProcessSummaryDetails(pw, str2, DumpUtils.STATE_LABELS[7], iArr, iArr2, new int[]{7}, j, j2, true);
        dumpProcessSummaryDetails(pw, str2, DumpUtils.STATE_LABELS[8], iArr, iArr2, new int[]{8}, j, j2, true);
        dumpProcessSummaryDetails(pw, str2, DumpUtils.STATE_LABELS[9], iArr, iArr2, new int[]{9}, j, j2, true);
        PrintWriter printWriter4 = pw;
        dumpProcessSummaryDetails(printWriter4, str2, DumpUtils.STATE_LABELS[10], iArr, iArr2, new int[]{10}, j, j2, true);
        dumpProcessSummaryDetails(printWriter4, str2, DumpUtils.STATE_LABEL_CACHED, iArr, iArr2, new int[]{11, 12, 13}, j, j2, true);
    }

    public void dumpProcessState(PrintWriter pw, String prefix, int[] screenStates, int[] memStates, int[] procStates, long now) {
        int i;
        int im;
        int is;
        String running;
        PrintWriter printWriter = pw;
        int[] iArr = screenStates;
        int[] iArr2 = memStates;
        int[] iArr3 = procStates;
        int printedScreen = -1;
        long totalTime = 0;
        int is2 = 0;
        while (is2 < iArr.length) {
            int printedMem = -1;
            long totalTime2 = totalTime;
            int im2 = 0;
            while (im2 < iArr2.length) {
                int ip = 0;
                while (ip < iArr3.length) {
                    int iscreen = iArr[is2];
                    int imem = iArr2[im2];
                    int bucket = ((iscreen + imem) * 14) + iArr3[ip];
                    long time = this.mDurations.getValueForId((byte) bucket);
                    String running2 = "";
                    if (this.mCurCombinedState == bucket) {
                        running = " (running)";
                        is = is2;
                        im = im2;
                        time += now - this.mStartTime;
                    } else {
                        is = is2;
                        im = im2;
                        running = running2;
                    }
                    if (time != 0) {
                        pw.print(prefix);
                        if (iArr.length > 1) {
                            DumpUtils.printScreenLabel(printWriter, printedScreen != iscreen ? iscreen : -1);
                            printedScreen = iscreen;
                        }
                        if (iArr2.length > 1) {
                            DumpUtils.printMemLabel(printWriter, printedMem != imem ? imem : -1, '/');
                            printedMem = imem;
                        }
                        printWriter.print(DumpUtils.STATE_LABELS[iArr3[ip]]);
                        printWriter.print(": ");
                        TimeUtils.formatDuration(time, printWriter);
                        printWriter.println(running);
                        totalTime2 += time;
                    }
                    ip++;
                    is2 = is;
                    im2 = im;
                }
                im2++;
            }
            is2++;
            totalTime = totalTime2;
        }
        if (totalTime != 0) {
            pw.print(prefix);
            if (iArr.length > 1) {
                i = -1;
                DumpUtils.printScreenLabel(printWriter, -1);
            } else {
                i = -1;
            }
            if (iArr2.length > 1) {
                DumpUtils.printMemLabel(printWriter, i, '/');
            }
            printWriter.print(DumpUtils.STATE_LABEL_TOTAL);
            printWriter.print(": ");
            TimeUtils.formatDuration(totalTime, printWriter);
            pw.println();
        }
    }

    public void dumpPss(PrintWriter pw, String prefix, int[] screenStates, int[] memStates, int[] procStates, long now) {
        PrintWriter printWriter = pw;
        int[] iArr = screenStates;
        int[] iArr2 = memStates;
        int[] iArr3 = procStates;
        int printedScreen = -1;
        boolean printedScreen2 = false;
        int is = 0;
        while (is < iArr.length) {
            int printedMem = -1;
            int printedScreen3 = printedScreen;
            boolean printedScreen4 = printedScreen2;
            int im = 0;
            while (im < iArr2.length) {
                int printedScreen5 = printedScreen3;
                boolean printedHeader = printedScreen4;
                int ip = 0;
                while (ip < iArr3.length) {
                    int iscreen = iArr[is];
                    int imem = iArr2[im];
                    int bucket = ((iscreen + imem) * 14) + iArr3[ip];
                    int key = this.mPssTable.getKey((byte) bucket);
                    if (key != -1) {
                        long[] table = this.mPssTable.getArrayForKey(key);
                        int i = bucket;
                        int bucket2 = SparseMappingTable.getIndexFromKey(key);
                        if (!printedHeader) {
                            pw.print(prefix);
                            int i2 = key;
                            printWriter.print("PSS/USS (");
                            printWriter.print(this.mPssTable.getKeyCount());
                            printWriter.println(" entries):");
                            printedHeader = true;
                        }
                        pw.print(prefix);
                        printWriter.print("  ");
                        if (iArr.length > 1) {
                            DumpUtils.printScreenLabel(printWriter, printedScreen5 != iscreen ? iscreen : -1);
                            printedScreen5 = iscreen;
                        }
                        if (iArr2.length > 1) {
                            DumpUtils.printMemLabel(printWriter, printedMem != imem ? imem : -1, '/');
                            printedMem = imem;
                        }
                        printWriter.print(DumpUtils.STATE_LABELS[iArr3[ip]]);
                        printWriter.print(": ");
                        dumpPssSamples(printWriter, table, bucket2);
                        pw.println();
                    }
                    ip++;
                    iArr = screenStates;
                }
                im++;
                printedScreen4 = printedHeader;
                printedScreen3 = printedScreen5;
                iArr = screenStates;
            }
            is++;
            printedScreen2 = printedScreen4;
            printedScreen = printedScreen3;
            iArr = screenStates;
        }
        long totalRunningDuration = getTotalRunningDuration(now);
        if (totalRunningDuration != 0) {
            pw.print(prefix);
            printWriter.print("Cur time ");
            TimeUtils.formatDuration(totalRunningDuration, printWriter);
            if (this.mTotalRunningStartTime != 0) {
                printWriter.print(" (running)");
            }
            if (this.mTotalRunningPss[0] != 0) {
                printWriter.print(": ");
                dumpPssSamples(printWriter, this.mTotalRunningPss, 0);
            }
            pw.println();
        }
        if (this.mNumExcessiveCpu != 0) {
            pw.print(prefix);
            printWriter.print("Killed for excessive CPU use: ");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.println(" times");
        }
        if (this.mNumCachedKill != 0) {
            pw.print(prefix);
            printWriter.print("Killed from cached state: ");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(" times from pss ");
            DebugUtils.printSizeValue(printWriter, this.mMinCachedKillPss * 1024);
            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            DebugUtils.printSizeValue(printWriter, this.mAvgCachedKillPss * 1024);
            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            DebugUtils.printSizeValue(printWriter, this.mMaxCachedKillPss * 1024);
            pw.println();
        }
    }

    public static void dumpPssSamples(PrintWriter pw, long[] table, int offset) {
        DebugUtils.printSizeValue(pw, table[offset + 1] * 1024);
        pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(pw, table[offset + 2] * 1024);
        pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(pw, table[offset + 3] * 1024);
        pw.print("/");
        DebugUtils.printSizeValue(pw, table[offset + 4] * 1024);
        pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(pw, table[offset + 5] * 1024);
        pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(pw, table[offset + 6] * 1024);
        pw.print("/");
        DebugUtils.printSizeValue(pw, table[offset + 7] * 1024);
        pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(pw, table[offset + 8] * 1024);
        pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(pw, table[offset + 9] * 1024);
        pw.print(" over ");
        pw.print(table[offset + 0]);
    }

    private void dumpProcessSummaryDetails(PrintWriter pw, String prefix, String label, int[] screenStates, int[] memStates, int[] procStates, long now, long totalTime, boolean full) {
        PrintWriter printWriter = pw;
        String str = label;
        long j = totalTime;
        ProcessStats.ProcessDataCollection totals = new ProcessStats.ProcessDataCollection(screenStates, memStates, procStates);
        computeProcessData(totals, now);
        if ((((double) totals.totalTime) / ((double) j)) * 100.0d >= 0.005d || totals.numPss != 0) {
            if (prefix != null) {
                pw.print(prefix);
            }
            if (str != null) {
                printWriter.print("  ");
                printWriter.print(str);
                printWriter.print(": ");
            }
            totals.print(printWriter, j, full);
            if (prefix != null) {
                pw.println();
                return;
            }
            return;
        }
        boolean z = full;
    }

    public void dumpInternalLocked(PrintWriter pw, String prefix, boolean dumpAll) {
        if (dumpAll) {
            pw.print(prefix);
            pw.print("myID=");
            pw.print(Integer.toHexString(System.identityHashCode(this)));
            pw.print(" mCommonProcess=");
            pw.print(Integer.toHexString(System.identityHashCode(this.mCommonProcess)));
            pw.print(" mPackage=");
            pw.println(this.mPackage);
            if (this.mMultiPackage) {
                pw.print(prefix);
                pw.print("mMultiPackage=");
                pw.println(this.mMultiPackage);
            }
            if (this != this.mCommonProcess) {
                pw.print(prefix);
                pw.print("Common Proc: ");
                pw.print(this.mCommonProcess.mName);
                pw.print("/");
                pw.print(this.mCommonProcess.mUid);
                pw.print(" pkg=");
                pw.println(this.mCommonProcess.mPackage);
            }
        }
        if (this.mActive) {
            pw.print(prefix);
            pw.print("mActive=");
            pw.println(this.mActive);
        }
        if (this.mDead) {
            pw.print(prefix);
            pw.print("mDead=");
            pw.println(this.mDead);
        }
        if (this.mNumActiveServices != 0 || this.mNumStartedServices != 0) {
            pw.print(prefix);
            pw.print("mNumActiveServices=");
            pw.print(this.mNumActiveServices);
            pw.print(" mNumStartedServices=");
            pw.println(this.mNumStartedServices);
        }
    }

    public void computeProcessData(ProcessStats.ProcessDataCollection data, long now) {
        int ip;
        int im;
        long j;
        int is;
        long avgPss;
        long maxUss;
        long minRss;
        ProcessStats.ProcessDataCollection processDataCollection = data;
        long maxRss = 0;
        processDataCollection.totalTime = 0;
        processDataCollection.maxRss = 0;
        processDataCollection.avgRss = 0;
        processDataCollection.minRss = 0;
        processDataCollection.maxUss = 0;
        processDataCollection.avgUss = 0;
        processDataCollection.minUss = 0;
        processDataCollection.maxPss = 0;
        processDataCollection.avgPss = 0;
        processDataCollection.minPss = 0;
        processDataCollection.numPss = 0;
        int is2 = 0;
        while (is2 < processDataCollection.screenStates.length) {
            int im2 = 0;
            while (im2 < processDataCollection.memStates.length) {
                int ip2 = 0;
                while (ip2 < processDataCollection.procStates.length) {
                    int bucket = ((processDataCollection.screenStates[is2] + processDataCollection.memStates[im2]) * 14) + processDataCollection.procStates[ip2];
                    processDataCollection.totalTime += getDuration(bucket, now);
                    long samples = getPssSampleCount(bucket);
                    if (samples > maxRss) {
                        long minPss = getPssMinimum(bucket);
                        is = is2;
                        long avgPss2 = getPssAverage(bucket);
                        long maxPss = getPssMaximum(bucket);
                        long minUss = getPssUssMinimum(bucket);
                        im = im2;
                        ip = ip2;
                        long avgUss = getPssUssAverage(bucket);
                        long samples2 = samples;
                        long maxUss2 = getPssUssMaximum(bucket);
                        long minRss2 = getPssRssMinimum(bucket);
                        long avgRss = getPssRssAverage(bucket);
                        int i = bucket;
                        long maxRss2 = getPssRssMaximum(bucket);
                        j = 0;
                        if (processDataCollection.numPss == 0) {
                            processDataCollection.minPss = minPss;
                            processDataCollection.avgPss = avgPss2;
                            processDataCollection.maxPss = maxPss;
                            processDataCollection.minUss = minUss;
                            processDataCollection.avgUss = avgUss;
                            long maxUss3 = maxUss2;
                            processDataCollection.maxUss = maxUss3;
                            long maxUss4 = maxUss3;
                            long maxUss5 = minRss2;
                            processDataCollection.minRss = maxUss5;
                            long minRss3 = maxUss5;
                            long minRss4 = avgRss;
                            processDataCollection.avgRss = minRss4;
                            long avgRss2 = minRss4;
                            long maxRss3 = maxRss2;
                            processDataCollection.maxRss = maxRss3;
                            long j2 = maxPss;
                            long j3 = avgPss2;
                            long maxPss2 = maxRss3;
                            long j4 = minPss;
                            avgPss = samples2;
                            long j5 = maxUss4;
                            long j6 = minRss3;
                            long j7 = avgRss2;
                            long maxRss4 = avgUss;
                        } else {
                            long maxUss6 = maxUss2;
                            long minRss5 = minRss2;
                            long avgRss3 = avgRss;
                            long maxRss5 = maxRss2;
                            if (minPss < processDataCollection.minPss) {
                                processDataCollection.minPss = minPss;
                            }
                            long j8 = minPss;
                            double d = (double) avgPss2;
                            long j9 = avgPss2;
                            long avgUss2 = avgUss;
                            avgPss = samples2;
                            processDataCollection.avgPss = (long) (((((double) processDataCollection.avgPss) * ((double) processDataCollection.numPss)) + (d * ((double) avgPss))) / ((double) (processDataCollection.numPss + avgPss)));
                            if (maxPss > processDataCollection.maxPss) {
                                processDataCollection.maxPss = maxPss;
                            }
                            if (minUss < processDataCollection.minUss) {
                                processDataCollection.minUss = minUss;
                            }
                            long j10 = maxPss;
                            processDataCollection.avgUss = (long) (((((double) processDataCollection.avgUss) * ((double) processDataCollection.numPss)) + (((double) avgUss2) * ((double) avgPss))) / ((double) (processDataCollection.numPss + avgPss)));
                            if (maxUss6 > processDataCollection.maxUss) {
                                maxUss = maxUss6;
                                processDataCollection.maxUss = maxUss;
                            } else {
                                maxUss = maxUss6;
                            }
                            if (minRss5 < processDataCollection.minRss) {
                                minRss = minRss5;
                                processDataCollection.minRss = minRss;
                            } else {
                                minRss = minRss5;
                            }
                            long j11 = maxUss;
                            long j12 = minRss;
                            long avgRss4 = avgRss3;
                            long j13 = avgRss4;
                            processDataCollection.avgRss = (long) (((((double) processDataCollection.avgRss) * ((double) processDataCollection.numPss)) + (((double) avgRss4) * ((double) avgPss))) / ((double) (processDataCollection.numPss + avgPss)));
                            if (maxRss5 > processDataCollection.maxRss) {
                                processDataCollection.maxRss = maxRss5;
                            }
                        }
                        processDataCollection.numPss += avgPss;
                    } else {
                        j = maxRss;
                        is = is2;
                        im = im2;
                        ip = ip2;
                    }
                    ip2 = ip + 1;
                    is2 = is;
                    maxRss = j;
                    im2 = im;
                }
                long j14 = maxRss;
                int i2 = is2;
                im2++;
            }
            long j15 = maxRss;
            is2++;
        }
    }

    public void dumpCsv(PrintWriter pw, boolean sepScreenStates, int[] screenStates, boolean sepMemStates, int[] memStates, boolean sepProcStates, int[] procStates, long now) {
        int NSS;
        int NSS2;
        int iss;
        PrintWriter printWriter = pw;
        int[] iArr = screenStates;
        int[] iArr2 = memStates;
        int[] iArr3 = procStates;
        int NSS3 = sepScreenStates ? iArr.length : 1;
        int NMS = sepMemStates ? iArr2.length : 1;
        int NPS = sepProcStates ? iArr3.length : 1;
        int isa = 0;
        while (isa < NSS3) {
            int ims = 0;
            while (ims < NMS) {
                int ips = 0;
                while (ips < NPS) {
                    int vsscreen = sepScreenStates ? iArr[isa] : 0;
                    int vsmem = sepMemStates ? iArr2[ims] : 0;
                    int vsproc = sepProcStates ? iArr3[ips] : 0;
                    int NSA = sepScreenStates ? 1 : iArr.length;
                    int NMA = sepMemStates ? 1 : iArr2.length;
                    if (sepProcStates) {
                        NSS = NSS3;
                        NSS2 = 1;
                    } else {
                        NSS = NSS3;
                        NSS2 = iArr3.length;
                    }
                    int NMS2 = NMS;
                    int NPS2 = NPS;
                    long totalTime = 0;
                    int isa2 = 0;
                    while (true) {
                        iss = isa;
                        int iss2 = isa2;
                        if (iss2 >= NSA) {
                            break;
                        }
                        long totalTime2 = totalTime;
                        int ima = 0;
                        while (ima < NMA) {
                            int ipa = 0;
                            while (ipa < NSS2) {
                                totalTime2 += getDuration(((vsscreen + (sepScreenStates ? 0 : iArr[iss2]) + vsmem + (sepMemStates ? 0 : iArr2[ima])) * 14) + vsproc + (sepProcStates ? 0 : iArr3[ipa]), now);
                                ipa++;
                                iArr = screenStates;
                                iArr2 = memStates;
                            }
                            long j = now;
                            ima++;
                            iArr = screenStates;
                            iArr2 = memStates;
                        }
                        long j2 = now;
                        int i = iss2 + 1;
                        totalTime = totalTime2;
                        isa = iss;
                        iArr = screenStates;
                        iArr2 = memStates;
                        isa2 = i;
                    }
                    long j3 = now;
                    printWriter.print("\t");
                    printWriter.print(totalTime);
                    ips++;
                    NSS3 = NSS;
                    NMS = NMS2;
                    NPS = NPS2;
                    isa = iss;
                    iArr = screenStates;
                    iArr2 = memStates;
                }
                long j4 = now;
                int i2 = NSS3;
                int i3 = NMS;
                int i4 = NPS;
                int i5 = isa;
                ims++;
                iArr = screenStates;
                iArr2 = memStates;
            }
            long j5 = now;
            int i6 = NSS3;
            int i7 = NMS;
            int i8 = NPS;
            isa++;
            iArr = screenStates;
            iArr2 = memStates;
        }
        long j6 = now;
        int i9 = NSS3;
        int i10 = NMS;
        int i11 = NPS;
    }

    public void dumpPackageProcCheckin(PrintWriter pw, String pkgName, int uid, long vers, String itemName, long now) {
        pw.print("pkgproc,");
        pw.print(pkgName);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(uid);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(vers);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(DumpUtils.collapseString(pkgName, itemName));
        dumpAllStateCheckin(pw, now);
        pw.println();
        if (this.mPssTable.getKeyCount() > 0) {
            pw.print("pkgpss,");
            pw.print(pkgName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(vers);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(DumpUtils.collapseString(pkgName, itemName));
            dumpAllPssCheckin(pw);
            pw.println();
        }
        if (this.mTotalRunningPss[0] != 0) {
            pw.print("pkgrun,");
            pw.print(pkgName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(vers);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(DumpUtils.collapseString(pkgName, itemName));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(getTotalRunningDuration(now));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            dumpPssSamplesCheckin(pw, this.mTotalRunningPss, 0);
            pw.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            pw.print("pkgkills,");
            pw.print(pkgName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(vers);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(DumpUtils.collapseString(pkgName, itemName));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print("0");
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(this.mNumExcessiveCpu);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(this.mNumCachedKill);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(this.mMinCachedKillPss);
            pw.print(SettingsStringUtil.DELIMITER);
            pw.print(this.mAvgCachedKillPss);
            pw.print(SettingsStringUtil.DELIMITER);
            pw.print(this.mMaxCachedKillPss);
            pw.println();
        }
    }

    public void dumpProcCheckin(PrintWriter pw, String procName, int uid, long now) {
        if (this.mDurations.getKeyCount() > 0) {
            pw.print("proc,");
            pw.print(procName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            dumpAllStateCheckin(pw, now);
            pw.println();
        }
        if (this.mPssTable.getKeyCount() > 0) {
            pw.print("pss,");
            pw.print(procName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            dumpAllPssCheckin(pw);
            pw.println();
        }
        if (this.mTotalRunningPss[0] != 0) {
            pw.print("procrun,");
            pw.print(procName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(getTotalRunningDuration(now));
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            dumpPssSamplesCheckin(pw, this.mTotalRunningPss, 0);
            pw.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            pw.print("kills,");
            pw.print(procName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print("0");
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(this.mNumExcessiveCpu);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(this.mNumCachedKill);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(this.mMinCachedKillPss);
            pw.print(SettingsStringUtil.DELIMITER);
            pw.print(this.mAvgCachedKillPss);
            pw.print(SettingsStringUtil.DELIMITER);
            pw.print(this.mMaxCachedKillPss);
            pw.println();
        }
    }

    public void dumpAllStateCheckin(PrintWriter pw, long now) {
        boolean didCurState = false;
        for (int i = 0; i < this.mDurations.getKeyCount(); i++) {
            int key = this.mDurations.getKeyAt(i);
            int type = SparseMappingTable.getIdFromKey(key);
            long time = this.mDurations.getValue(key);
            if (this.mCurCombinedState == type) {
                didCurState = true;
                time += now - this.mStartTime;
            }
            DumpUtils.printProcStateTagAndValue(pw, type, time);
        }
        if (!didCurState && this.mCurCombinedState != -1) {
            DumpUtils.printProcStateTagAndValue(pw, this.mCurCombinedState, now - this.mStartTime);
        }
    }

    public void dumpAllPssCheckin(PrintWriter pw) {
        int N = this.mPssTable.getKeyCount();
        for (int i = 0; i < N; i++) {
            int key = this.mPssTable.getKeyAt(i);
            int type = SparseMappingTable.getIdFromKey(key);
            pw.print(',');
            DumpUtils.printProcStateTag(pw, type);
            pw.print(':');
            dumpPssSamplesCheckin(pw, this.mPssTable.getArrayForKey(key), SparseMappingTable.getIndexFromKey(key));
        }
    }

    public static void dumpPssSamplesCheckin(PrintWriter pw, long[] table, int offset) {
        pw.print(table[offset + 0]);
        pw.print(':');
        pw.print(table[offset + 1]);
        pw.print(':');
        pw.print(table[offset + 2]);
        pw.print(':');
        pw.print(table[offset + 3]);
        pw.print(':');
        pw.print(table[offset + 4]);
        pw.print(':');
        pw.print(table[offset + 5]);
        pw.print(':');
        pw.print(table[offset + 6]);
        pw.print(':');
        pw.print(table[offset + 7]);
        pw.print(':');
        pw.print(table[offset + 8]);
        pw.print(':');
        pw.print(table[offset + 9]);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("ProcessState{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        sb.append(this.mName);
        sb.append("/");
        sb.append(this.mUid);
        sb.append(" pkg=");
        sb.append(this.mPackage);
        if (this.mMultiPackage) {
            sb.append(" (multi)");
        }
        if (this.mCommonProcess != this) {
            sb.append(" (sub)");
        }
        sb.append("}");
        return sb.toString();
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId, String procName, int uid, long now) {
        long j;
        long j2;
        int i;
        SparseLongArray durationByState;
        ProtoOutputStream protoOutputStream = proto;
        long j3 = now;
        long token = proto.start(fieldId);
        protoOutputStream.write(1138166333441L, procName);
        protoOutputStream.write(1120986464258L, uid);
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            long killToken = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, this.mNumExcessiveCpu);
            protoOutputStream.write(1120986464258L, this.mNumCachedKill);
            ProtoUtils.toAggStatsProto(proto, 1146756268035L, this.mMinCachedKillPss, this.mAvgCachedKillPss, this.mMaxCachedKillPss);
            protoOutputStream.end(killToken);
        }
        SparseLongArray durationByState2 = new SparseLongArray();
        boolean didCurState = false;
        for (int i2 = 0; i2 < this.mDurations.getKeyCount(); i2++) {
            int key = this.mDurations.getKeyAt(i2);
            int type = SparseMappingTable.getIdFromKey(key);
            long time = this.mDurations.getValue(key);
            if (this.mCurCombinedState == type) {
                durationByState = durationByState2;
                time += j3 - this.mStartTime;
                didCurState = true;
            } else {
                durationByState = durationByState2;
            }
            durationByState2 = durationByState;
            durationByState2.put(type, time);
        }
        if (!didCurState && this.mCurCombinedState != -1) {
            durationByState2.put(this.mCurCombinedState, j3 - this.mStartTime);
        }
        int type2 = 0;
        while (true) {
            int i3 = type2;
            j = 2246267895813L;
            j2 = 1112396529668L;
            if (i3 >= this.mPssTable.getKeyCount()) {
                break;
            }
            int key2 = this.mPssTable.getKeyAt(i3);
            int idFromKey = SparseMappingTable.getIdFromKey(key2);
            if (durationByState2.indexOfKey(idFromKey) < 0) {
                i = i3;
            } else {
                int key3 = key2;
                int type3 = idFromKey;
                i = i3;
                DumpUtils.printProcStateTagProto(proto, 1159641169921L, 1159641169922L, 1159641169923L, type3);
                int type4 = type3;
                long duration = durationByState2.get(type4);
                durationByState2.delete(type4);
                protoOutputStream.write(1112396529668L, duration);
                this.mPssTable.writeStatsToProtoForKey(protoOutputStream, key3);
                protoOutputStream.end(protoOutputStream.start(2246267895813L));
            }
            type2 = i + 1;
            String str = procName;
        }
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 >= durationByState2.size()) {
                break;
            }
            long stateToken = protoOutputStream.start(j);
            int i6 = i5;
            DumpUtils.printProcStateTagProto(proto, 1159641169921L, 1159641169922L, 1159641169923L, durationByState2.keyAt(i5));
            protoOutputStream.write(1112396529668L, durationByState2.valueAt(i6));
            protoOutputStream.end(stateToken);
            i4 = i6 + 1;
            j2 = 1112396529668L;
            j = j;
        }
        long j4 = j2;
        long totalRunningDuration = getTotalRunningDuration(j3);
        if (totalRunningDuration > 0) {
            long stateToken2 = protoOutputStream.start(1146756268038L);
            protoOutputStream.write(j4, totalRunningDuration);
            if (this.mTotalRunningPss[0] != 0) {
                PssTable.writeStatsToProto(protoOutputStream, this.mTotalRunningPss, 0);
            }
            protoOutputStream.end(stateToken2);
        }
        protoOutputStream.end(token);
    }
}
