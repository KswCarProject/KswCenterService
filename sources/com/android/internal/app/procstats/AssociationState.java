package com.android.internal.app.procstats;

import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Parcel;
import android.p007os.SystemClock;
import android.p007os.UserHandle;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.ProcessStats;
import com.ibm.icu.text.PluralRules;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class AssociationState {
    private static final boolean DEBUG = false;
    private static final String TAG = "ProcessStats";
    private final String mName;
    private int mNumActive;
    private final ProcessStats.PackageState mPackageState;
    private ProcessState mProc;
    private final String mProcessName;
    private final ProcessStats mProcessStats;
    private final ArrayMap<SourceKey, SourceState> mSources = new ArrayMap<>();
    private final SourceKey mTmpSourceKey = new SourceKey(0, null, null);

    static /* synthetic */ int access$110(AssociationState x0) {
        int i = x0.mNumActive;
        x0.mNumActive = i - 1;
        return i;
    }

    /* loaded from: classes4.dex */
    public final class SourceState {
        int mActiveCount;
        long mActiveDuration;
        long mActiveStartUptime;
        int mCount;
        long mDuration;
        DurationsTable mDurations;
        boolean mInTrackingList;
        final SourceKey mKey;
        int mNesting;
        long mStartUptime;
        long mTrackingUptime;
        int mProcStateSeq = -1;
        int mProcState = -1;
        int mActiveProcState = -1;

        SourceState(SourceKey key) {
            this.mKey = key;
        }

        public AssociationState getAssociationState() {
            return AssociationState.this;
        }

        public String getProcessName() {
            return this.mKey.mProcess;
        }

        public int getUid() {
            return this.mKey.mUid;
        }

        public void trackProcState(int procState, int seq, long now) {
            int procState2 = ProcessState.PROCESS_STATE_TO_STATE[procState];
            if (seq != this.mProcStateSeq) {
                this.mProcStateSeq = seq;
                this.mProcState = procState2;
            } else if (procState2 < this.mProcState) {
                this.mProcState = procState2;
            }
            if (procState2 < 9 && !this.mInTrackingList) {
                this.mInTrackingList = true;
                this.mTrackingUptime = now;
                AssociationState.this.mProcessStats.mTrackingAssociations.add(this);
            }
        }

        public void stop() {
            this.mNesting--;
            if (this.mNesting == 0) {
                this.mDuration += SystemClock.uptimeMillis() - this.mStartUptime;
                AssociationState.access$110(AssociationState.this);
                stopTracking(SystemClock.uptimeMillis());
            }
        }

        void startActive(long now) {
            if (this.mInTrackingList) {
                if (this.mActiveStartUptime == 0) {
                    this.mActiveStartUptime = now;
                    this.mActiveCount++;
                }
                if (this.mActiveProcState != this.mProcState) {
                    if (this.mActiveProcState != -1) {
                        long duration = (this.mActiveDuration + now) - this.mActiveStartUptime;
                        if (duration != 0) {
                            if (this.mDurations == null) {
                                makeDurations();
                            }
                            this.mDurations.addDuration(this.mActiveProcState, duration);
                            this.mActiveDuration = 0L;
                        }
                        this.mActiveStartUptime = now;
                    }
                    this.mActiveProcState = this.mProcState;
                    return;
                }
                return;
            }
            Slog.wtf("ProcessStats", "startActive while not tracking: " + this);
        }

        void stopActive(long now) {
            if (this.mActiveStartUptime != 0) {
                if (!this.mInTrackingList) {
                    Slog.wtf("ProcessStats", "stopActive while not tracking: " + this);
                }
                long duration = (this.mActiveDuration + now) - this.mActiveStartUptime;
                if (this.mDurations != null) {
                    this.mDurations.addDuration(this.mActiveProcState, duration);
                } else {
                    this.mActiveDuration = duration;
                }
                this.mActiveStartUptime = 0L;
            }
        }

        void makeDurations() {
            this.mDurations = new DurationsTable(AssociationState.this.mProcessStats.mTableData);
        }

        void stopTracking(long now) {
            stopActive(now);
            if (this.mInTrackingList) {
                this.mInTrackingList = false;
                this.mProcState = -1;
                ArrayList<SourceState> list = AssociationState.this.mProcessStats.mTrackingAssociations;
                for (int i = list.size() - 1; i >= 0; i--) {
                    if (list.get(i) == this) {
                        list.remove(i);
                        return;
                    }
                }
                Slog.wtf("ProcessStats", "Stop tracking didn't find in tracking list: " + this);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("SourceState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            sb.append(this.mKey.mProcess);
            sb.append("/");
            sb.append(this.mKey.mUid);
            if (this.mProcState != -1) {
                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                sb.append(DumpUtils.STATE_NAMES[this.mProcState]);
                sb.append(" #");
                sb.append(this.mProcStateSeq);
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /* loaded from: classes4.dex */
    private static final class SourceKey {
        String mPackage;
        String mProcess;
        int mUid;

        SourceKey(int uid, String process, String pkg) {
            this.mUid = uid;
            this.mProcess = process;
            this.mPackage = pkg;
        }

        public boolean equals(Object o) {
            if (o instanceof SourceKey) {
                SourceKey s = (SourceKey) o;
                return s.mUid == this.mUid && Objects.equals(s.mProcess, this.mProcess) && Objects.equals(s.mPackage, this.mPackage);
            }
            return false;
        }

        public int hashCode() {
            return (Integer.hashCode(this.mUid) ^ (this.mProcess == null ? 0 : this.mProcess.hashCode())) ^ (this.mPackage != null ? this.mPackage.hashCode() * 33 : 0);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("SourceKey{");
            UserHandle.formatUid(sb, this.mUid);
            sb.append(' ');
            sb.append(this.mProcess);
            sb.append(' ');
            sb.append(this.mPackage);
            sb.append('}');
            return sb.toString();
        }
    }

    public AssociationState(ProcessStats processStats, ProcessStats.PackageState packageState, String name, String processName, ProcessState proc) {
        this.mProcessStats = processStats;
        this.mPackageState = packageState;
        this.mName = name;
        this.mProcessName = processName;
        this.mProc = proc;
    }

    public int getUid() {
        return this.mPackageState.mUid;
    }

    public String getPackage() {
        return this.mPackageState.mPackageName;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public String getName() {
        return this.mName;
    }

    public ProcessState getProcess() {
        return this.mProc;
    }

    public void setProcess(ProcessState proc) {
        this.mProc = proc;
    }

    public SourceState startSource(int uid, String processName, String packageName) {
        this.mTmpSourceKey.mUid = uid;
        this.mTmpSourceKey.mProcess = processName;
        this.mTmpSourceKey.mPackage = packageName;
        SourceState src = this.mSources.get(this.mTmpSourceKey);
        if (src == null) {
            SourceKey key = new SourceKey(uid, processName, packageName);
            src = new SourceState(key);
            this.mSources.put(key, src);
        }
        src.mNesting++;
        if (src.mNesting == 1) {
            src.mCount++;
            src.mStartUptime = SystemClock.uptimeMillis();
            this.mNumActive++;
        }
        return src;
    }

    public void add(AssociationState other) {
        for (int isrc = other.mSources.size() - 1; isrc >= 0; isrc--) {
            SourceKey key = other.mSources.keyAt(isrc);
            SourceState otherSrc = other.mSources.valueAt(isrc);
            SourceState mySrc = this.mSources.get(key);
            if (mySrc == null) {
                mySrc = new SourceState(key);
                this.mSources.put(key, mySrc);
            }
            mySrc.mCount += otherSrc.mCount;
            mySrc.mDuration += otherSrc.mDuration;
            mySrc.mActiveCount += otherSrc.mActiveCount;
            if (otherSrc.mActiveDuration != 0 || otherSrc.mDurations != null) {
                if (mySrc.mDurations != null) {
                    if (otherSrc.mDurations != null) {
                        mySrc.mDurations.addDurations(otherSrc.mDurations);
                    } else {
                        mySrc.mDurations.addDuration(otherSrc.mActiveProcState, otherSrc.mActiveDuration);
                    }
                } else if (otherSrc.mDurations != null) {
                    mySrc.makeDurations();
                    mySrc.mDurations.addDurations(otherSrc.mDurations);
                    if (mySrc.mActiveDuration != 0) {
                        mySrc.mDurations.addDuration(mySrc.mActiveProcState, mySrc.mActiveDuration);
                        mySrc.mActiveDuration = 0L;
                        mySrc.mActiveProcState = -1;
                    }
                } else if (mySrc.mActiveDuration != 0) {
                    if (mySrc.mActiveProcState == otherSrc.mActiveProcState) {
                        mySrc.mDuration += otherSrc.mDuration;
                    } else {
                        mySrc.makeDurations();
                        mySrc.mDurations.addDuration(mySrc.mActiveProcState, mySrc.mActiveDuration);
                        mySrc.mDurations.addDuration(otherSrc.mActiveProcState, otherSrc.mActiveDuration);
                        mySrc.mActiveDuration = 0L;
                        mySrc.mActiveProcState = -1;
                    }
                } else {
                    mySrc.mActiveProcState = otherSrc.mActiveProcState;
                    mySrc.mActiveDuration = otherSrc.mActiveDuration;
                }
            }
        }
    }

    public boolean isInUse() {
        return this.mNumActive > 0;
    }

    public void resetSafely(long now) {
        if (!isInUse()) {
            this.mSources.clear();
            return;
        }
        for (int isrc = this.mSources.size() - 1; isrc >= 0; isrc--) {
            SourceState src = this.mSources.valueAt(isrc);
            if (src.mNesting > 0) {
                src.mCount = 1;
                src.mStartUptime = now;
                src.mDuration = 0L;
                if (src.mActiveStartUptime > 0) {
                    src.mActiveCount = 1;
                    src.mActiveStartUptime = now;
                } else {
                    src.mActiveCount = 0;
                }
                src.mActiveDuration = 0L;
                src.mDurations = null;
            } else {
                this.mSources.removeAt(isrc);
            }
        }
    }

    public void writeToParcel(ProcessStats stats, Parcel out, long nowUptime) {
        int NSRC = this.mSources.size();
        out.writeInt(NSRC);
        for (int isrc = 0; isrc < NSRC; isrc++) {
            SourceKey key = this.mSources.keyAt(isrc);
            SourceState src = this.mSources.valueAt(isrc);
            out.writeInt(key.mUid);
            stats.writeCommonString(out, key.mProcess);
            stats.writeCommonString(out, key.mPackage);
            out.writeInt(src.mCount);
            out.writeLong(src.mDuration);
            out.writeInt(src.mActiveCount);
            if (src.mDurations != null) {
                out.writeInt(1);
                src.mDurations.writeToParcel(out);
            } else {
                out.writeInt(0);
                out.writeInt(src.mActiveProcState);
                out.writeLong(src.mActiveDuration);
            }
        }
    }

    public String readFromParcel(ProcessStats stats, Parcel in, int parcelVersion) {
        int NSRC = in.readInt();
        if (NSRC < 0 || NSRC > 100000) {
            return "Association with bad src count: " + NSRC;
        }
        for (int isrc = 0; isrc < NSRC; isrc++) {
            int uid = in.readInt();
            String procName = stats.readCommonString(in, parcelVersion);
            String pkgName = stats.readCommonString(in, parcelVersion);
            SourceKey key = new SourceKey(uid, procName, pkgName);
            SourceState src = new SourceState(key);
            src.mCount = in.readInt();
            src.mDuration = in.readLong();
            src.mActiveCount = in.readInt();
            if (in.readInt() != 0) {
                src.makeDurations();
                if (!src.mDurations.readFromParcel(in)) {
                    return "Duration table corrupt: " + key + " <- " + src;
                }
            } else {
                src.mActiveProcState = in.readInt();
                src.mActiveDuration = in.readLong();
            }
            this.mSources.put(key, src);
        }
        return null;
    }

    public void commitStateTime(long nowUptime) {
        if (isInUse()) {
            for (int isrc = this.mSources.size() - 1; isrc >= 0; isrc--) {
                SourceState src = this.mSources.valueAt(isrc);
                if (src.mNesting > 0) {
                    src.mDuration += nowUptime - src.mStartUptime;
                    src.mStartUptime = nowUptime;
                }
                if (src.mActiveStartUptime > 0) {
                    long duration = (src.mActiveDuration + nowUptime) - src.mActiveStartUptime;
                    if (src.mDurations != null) {
                        src.mDurations.addDuration(src.mActiveProcState, duration);
                    } else {
                        src.mActiveDuration = duration;
                    }
                    src.mActiveStartUptime = nowUptime;
                }
            }
        }
    }

    public boolean hasProcessOrPackage(String procName) {
        int NSRC = this.mSources.size();
        for (int isrc = 0; isrc < NSRC; isrc++) {
            SourceKey key = this.mSources.keyAt(isrc);
            if (procName.equals(key.mProcess) || procName.equals(key.mPackage)) {
                return true;
            }
        }
        return false;
    }

    public void dumpStats(PrintWriter pw, String prefix, String prefixInner, String headerPrefix, long now, long totalTime, String reqPackage, boolean dumpDetails, boolean dumpAll) {
        SourceState src;
        int i;
        int isrc;
        AssociationState associationState = this;
        if (dumpAll) {
            pw.print(prefix);
            pw.print("mNumActive=");
            pw.println(associationState.mNumActive);
        }
        int NSRC = associationState.mSources.size();
        int isrc2 = 0;
        while (true) {
            int isrc3 = isrc2;
            if (isrc3 < NSRC) {
                SourceKey key = associationState.mSources.keyAt(isrc3);
                SourceState src2 = associationState.mSources.valueAt(isrc3);
                if (reqPackage != null && !reqPackage.equals(key.mProcess) && !reqPackage.equals(key.mPackage)) {
                    isrc = isrc3;
                } else {
                    pw.print(prefixInner);
                    pw.print("<- ");
                    pw.print(key.mProcess);
                    pw.print("/");
                    UserHandle.formatUid(pw, key.mUid);
                    if (key.mPackage != null) {
                        pw.print(" (");
                        pw.print(key.mPackage);
                        pw.print(")");
                    }
                    pw.println(SettingsStringUtil.DELIMITER);
                    pw.print(prefixInner);
                    pw.print("   Total count ");
                    pw.print(src2.mCount);
                    long duration = src2.mDuration;
                    if (src2.mNesting > 0) {
                        duration += now - src2.mStartUptime;
                    }
                    long duration2 = duration;
                    if (dumpAll) {
                        pw.print(": Duration ");
                        TimeUtils.formatDuration(duration2, pw);
                        pw.print(" / ");
                    } else {
                        pw.print(": time ");
                    }
                    DumpUtils.printPercent(pw, duration2 / totalTime);
                    if (src2.mNesting > 0) {
                        pw.print(" (running");
                        if (src2.mProcState != -1) {
                            pw.print(" / ");
                            pw.print(DumpUtils.STATE_NAMES[src2.mProcState]);
                            pw.print(" #");
                            pw.print(src2.mProcStateSeq);
                        }
                        pw.print(")");
                    }
                    pw.println();
                    if (src2.mActiveCount > 0 || src2.mDurations != null || src2.mActiveDuration != 0 || src2.mActiveStartUptime != 0) {
                        pw.print(prefixInner);
                        pw.print("   Active count ");
                        pw.print(src2.mActiveCount);
                        if (dumpDetails) {
                            if (dumpAll) {
                                pw.print(src2.mDurations != null ? " (multi-field)" : " (inline)");
                            }
                            pw.println(SettingsStringUtil.DELIMITER);
                            src = src2;
                            i = -1;
                            isrc = isrc3;
                            dumpTime(pw, prefixInner, src2, totalTime, now, dumpDetails, dumpAll);
                        } else {
                            src = src2;
                            i = -1;
                            isrc = isrc3;
                            pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                            dumpActiveDurationSummary(pw, src, totalTime, now, dumpAll);
                            pw.println();
                        }
                    } else {
                        src = src2;
                        i = -1;
                        isrc = isrc3;
                    }
                    if (dumpAll) {
                        SourceState src3 = src;
                        if (src3.mInTrackingList) {
                            pw.print(prefixInner);
                            pw.print("   mInTrackingList=");
                            pw.println(src3.mInTrackingList);
                        }
                        if (src3.mProcState != i) {
                            pw.print(prefixInner);
                            pw.print("   mProcState=");
                            pw.print(DumpUtils.STATE_NAMES[src3.mProcState]);
                            pw.print(" mProcStateSeq=");
                            pw.println(src3.mProcStateSeq);
                        }
                    }
                }
                isrc2 = isrc + 1;
                associationState = this;
            } else {
                return;
            }
        }
    }

    void dumpActiveDurationSummary(PrintWriter pw, SourceState src, long totalTime, long now, boolean dumpAll) {
        long duration = dumpTime(null, null, src, totalTime, now, false, false);
        boolean isRunning = duration < 0;
        if (isRunning) {
            duration = -duration;
        }
        if (dumpAll) {
            pw.print("Duration ");
            TimeUtils.formatDuration(duration, pw);
            pw.print(" / ");
        } else {
            pw.print("time ");
        }
        DumpUtils.printPercent(pw, duration / totalTime);
        if (src.mActiveStartUptime > 0) {
            pw.print(" (running)");
        }
        pw.println();
    }

    long dumpTime(PrintWriter pw, String prefix, SourceState src, long overallTime, long now, boolean dumpDetails, boolean dumpAll) {
        long time;
        String running;
        long totalTime = 0;
        boolean isRunning = false;
        int iprocstate = 0;
        while (iprocstate < 14) {
            if (src.mDurations != null) {
                time = src.mDurations.getValueForId((byte) iprocstate);
            } else {
                time = src.mActiveProcState == iprocstate ? src.mDuration : 0L;
            }
            if (src.mActiveStartUptime != 0 && src.mActiveProcState == iprocstate) {
                running = " (running)";
                isRunning = true;
                time += now - src.mActiveStartUptime;
            } else {
                running = null;
            }
            if (time != 0) {
                if (pw != null) {
                    pw.print(prefix);
                    pw.print("  ");
                    pw.print(DumpUtils.STATE_LABELS[iprocstate]);
                    pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                    if (dumpAll) {
                        pw.print("Duration ");
                        TimeUtils.formatDuration(time, pw);
                        pw.print(" / ");
                    } else {
                        pw.print("time ");
                    }
                    DumpUtils.printPercent(pw, time / overallTime);
                    if (running != null) {
                        pw.print(running);
                    }
                    pw.println();
                }
                totalTime += time;
            }
            iprocstate++;
        }
        int iprocstate2 = (totalTime > 0L ? 1 : (totalTime == 0L ? 0 : -1));
        if (iprocstate2 != 0 && pw != null) {
            pw.print(prefix);
            pw.print("  ");
            pw.print(DumpUtils.STATE_LABEL_TOTAL);
            pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
            if (dumpAll) {
                pw.print("Duration ");
                TimeUtils.formatDuration(totalTime, pw);
                pw.print(" / ");
            } else {
                pw.print("time ");
            }
            DumpUtils.printPercent(pw, totalTime / overallTime);
            pw.println();
        }
        return isRunning ? -totalTime : totalTime;
    }

    public void dumpTimesCheckin(PrintWriter pw, String pkgName, int uid, long vers, String associationName, long now) {
        int isrc;
        int NSRC;
        AssociationState associationState = this;
        int NSRC2 = associationState.mSources.size();
        int isrc2 = 0;
        while (isrc2 < NSRC2) {
            SourceKey key = associationState.mSources.keyAt(isrc2);
            SourceState src = associationState.mSources.valueAt(isrc2);
            pw.print("pkgasc");
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(pkgName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(uid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(vers);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(associationName);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(key.mProcess);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(key.mUid);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(src.mCount);
            long duration = src.mDuration;
            if (src.mNesting > 0) {
                isrc = isrc2;
                duration += now - src.mStartUptime;
            } else {
                isrc = isrc2;
            }
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(duration);
            pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
            pw.print(src.mActiveCount);
            long timeNow = src.mActiveStartUptime != 0 ? now - src.mActiveStartUptime : 0L;
            if (src.mDurations != null) {
                int N = src.mDurations.getKeyCount();
                long duration2 = duration;
                int i = 0;
                while (i < N) {
                    int dkey = src.mDurations.getKeyAt(i);
                    long duration3 = src.mDurations.getValue(dkey);
                    if (dkey == src.mActiveProcState) {
                        duration3 += timeNow;
                    }
                    long duration4 = duration3;
                    int procState = SparseMappingTable.getIdFromKey(dkey);
                    pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
                    DumpUtils.printArrayEntry(pw, DumpUtils.STATE_TAGS, procState, 1);
                    pw.print(':');
                    pw.print(duration4);
                    i++;
                    duration2 = duration4;
                    NSRC2 = NSRC2;
                    key = key;
                }
                NSRC = NSRC2;
            } else {
                NSRC = NSRC2;
                long duration5 = src.mActiveDuration + timeNow;
                if (duration5 != 0) {
                    pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
                    DumpUtils.printArrayEntry(pw, DumpUtils.STATE_TAGS, src.mActiveProcState, 1);
                    pw.print(':');
                    pw.print(duration5);
                }
            }
            pw.println();
            isrc2 = isrc + 1;
            NSRC2 = NSRC;
            associationState = this;
        }
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId, long now) {
        int isrc;
        long sourceToken;
        AssociationState associationState = this;
        long token = proto.start(fieldId);
        proto.write(1138166333441L, associationState.mName);
        int NSRC = associationState.mSources.size();
        int isrc2 = 0;
        while (true) {
            int isrc3 = isrc2;
            if (isrc3 < NSRC) {
                SourceKey key = associationState.mSources.keyAt(isrc3);
                SourceState src = associationState.mSources.valueAt(isrc3);
                long sourceToken2 = proto.start(2246267895810L);
                proto.write(1138166333442L, key.mProcess);
                proto.write(1138166333447L, key.mPackage);
                proto.write(1120986464257L, key.mUid);
                proto.write(1120986464259L, src.mCount);
                long duration = src.mDuration;
                if (src.mNesting > 0) {
                    isrc = isrc3;
                    duration += now - src.mStartUptime;
                } else {
                    isrc = isrc3;
                }
                proto.write(1112396529668L, duration);
                if (src.mActiveCount != 0) {
                    proto.write(1120986464261L, src.mActiveCount);
                }
                long timeNow = src.mActiveStartUptime != 0 ? now - src.mActiveStartUptime : 0L;
                if (src.mDurations != null) {
                    int N = src.mDurations.getKeyCount();
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= N) {
                            break;
                        }
                        int dkey = src.mDurations.getKeyAt(i2);
                        long duration2 = src.mDurations.getValue(dkey);
                        if (dkey == src.mActiveProcState) {
                            duration2 += timeNow;
                        }
                        int procState = SparseMappingTable.getIdFromKey(dkey);
                        int N2 = N;
                        long stateToken = proto.start(2246267895814L);
                        DumpUtils.printProto(proto, 1159641169921L, DumpUtils.STATE_PROTO_ENUMS, procState, 1);
                        proto.write(1112396529666L, duration2);
                        proto.end(stateToken);
                        i = i2 + 1;
                        src = src;
                        N = N2;
                        sourceToken2 = sourceToken2;
                    }
                    sourceToken = sourceToken2;
                } else {
                    sourceToken = sourceToken2;
                    long duration3 = src.mActiveDuration + timeNow;
                    if (duration3 != 0) {
                        long stateToken2 = proto.start(2246267895814L);
                        DumpUtils.printProto(proto, 1159641169921L, DumpUtils.STATE_PROTO_ENUMS, src.mActiveProcState, 1);
                        proto.write(1112396529666L, duration3);
                        proto.end(stateToken2);
                    }
                }
                proto.end(sourceToken);
                isrc2 = isrc + 1;
                associationState = this;
            } else {
                proto.end(token);
                return;
            }
        }
    }

    public String toString() {
        return "AssociationState{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.mName + " pkg=" + this.mPackageState.mPackageName + " proc=" + Integer.toHexString(System.identityHashCode(this.mProc)) + "}";
    }
}
