package android.telecom.Logging;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Iterator;

public class Session {
    public static final String CONTINUE_SUBSESSION = "CONTINUE_SUBSESSION";
    public static final String CREATE_SUBSESSION = "CREATE_SUBSESSION";
    public static final String END_SESSION = "END_SESSION";
    public static final String END_SUBSESSION = "END_SUBSESSION";
    public static final String EXTERNAL_INDICATOR = "E-";
    public static final String SESSION_SEPARATION_CHAR_CHILD = "_";
    public static final String START_EXTERNAL_SESSION = "START_EXTERNAL_SESSION";
    public static final String START_SESSION = "START_SESSION";
    public static final String SUBSESSION_SEPARATION_CHAR = "->";
    public static final String TRUNCATE_STRING = "...";
    public static final int UNDEFINED = -1;
    private int mChildCounter = 0;
    private ArrayList<Session> mChildSessions;
    private long mExecutionEndTimeMs = -1;
    private long mExecutionStartTimeMs;
    private String mFullMethodPathCache;
    private boolean mIsCompleted = false;
    private boolean mIsExternal = false;
    private boolean mIsStartedFromActiveSession = false;
    private String mOwnerInfo;
    private Session mParentSession;
    private String mSessionId;
    private String mShortMethodName;

    public static class Info implements Parcelable {
        public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
            public Info createFromParcel(Parcel source) {
                return new Info(source.readString(), source.readString());
            }

            public Info[] newArray(int size) {
                return new Info[size];
            }
        };
        public final String methodPath;
        public final String sessionId;

        private Info(String id, String path) {
            this.sessionId = id;
            this.methodPath = path;
        }

        public static Info getInfo(Session s) {
            return new Info(s.getFullSessionId(), s.getFullMethodPath(!Log.DEBUG && s.isSessionExternal()));
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel destination, int flags) {
            destination.writeString(this.sessionId);
            destination.writeString(this.methodPath);
        }
    }

    public Session(String sessionId, String shortMethodName, long startTimeMs, boolean isStartedFromActiveSession, String ownerInfo) {
        setSessionId(sessionId);
        setShortMethodName(shortMethodName);
        this.mExecutionStartTimeMs = startTimeMs;
        this.mParentSession = null;
        this.mChildSessions = new ArrayList<>(5);
        this.mIsStartedFromActiveSession = isStartedFromActiveSession;
        this.mOwnerInfo = ownerInfo;
    }

    public void setSessionId(String sessionId) {
        if (sessionId == null) {
            this.mSessionId = "?";
        }
        this.mSessionId = sessionId;
    }

    public String getShortMethodName() {
        return this.mShortMethodName;
    }

    public void setShortMethodName(String shortMethodName) {
        if (shortMethodName == null) {
            shortMethodName = "";
        }
        this.mShortMethodName = shortMethodName;
    }

    public void setIsExternal(boolean isExternal) {
        this.mIsExternal = isExternal;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public void setParentSession(Session parentSession) {
        this.mParentSession = parentSession;
    }

    public void addChild(Session childSession) {
        if (childSession != null) {
            this.mChildSessions.add(childSession);
        }
    }

    public void removeChild(Session child) {
        if (child != null) {
            this.mChildSessions.remove(child);
        }
    }

    public long getExecutionStartTimeMilliseconds() {
        return this.mExecutionStartTimeMs;
    }

    public void setExecutionStartTimeMs(long startTimeMs) {
        this.mExecutionStartTimeMs = startTimeMs;
    }

    public Session getParentSession() {
        return this.mParentSession;
    }

    public ArrayList<Session> getChildSessions() {
        return this.mChildSessions;
    }

    public boolean isSessionCompleted() {
        return this.mIsCompleted;
    }

    public boolean isStartedFromActiveSession() {
        return this.mIsStartedFromActiveSession;
    }

    public Info getInfo() {
        return Info.getInfo(this);
    }

    @VisibleForTesting
    public String getSessionId() {
        return this.mSessionId;
    }

    public void markSessionCompleted(long executionEndTimeMs) {
        this.mExecutionEndTimeMs = executionEndTimeMs;
        this.mIsCompleted = true;
    }

    public long getLocalExecutionTime() {
        if (this.mExecutionEndTimeMs == -1) {
            return -1;
        }
        return this.mExecutionEndTimeMs - this.mExecutionStartTimeMs;
    }

    public synchronized String getNextChildId() {
        int i;
        i = this.mChildCounter;
        this.mChildCounter = i + 1;
        return String.valueOf(i);
    }

    /* access modifiers changed from: private */
    public String getFullSessionId() {
        Session parentSession = this.mParentSession;
        if (parentSession == null) {
            return this.mSessionId;
        }
        if (!Log.VERBOSE) {
            return parentSession.getFullSessionId();
        }
        return parentSession.getFullSessionId() + SESSION_SEPARATION_CHAR_CHILD + this.mSessionId;
    }

    public String printFullSessionTree() {
        Session topNode = this;
        while (topNode.getParentSession() != null) {
            topNode = topNode.getParentSession();
        }
        return topNode.printSessionTree();
    }

    public String printSessionTree() {
        StringBuilder sb = new StringBuilder();
        printSessionTree(0, sb);
        return sb.toString();
    }

    private void printSessionTree(int tabI, StringBuilder sb) {
        sb.append(toString());
        Iterator<Session> it = this.mChildSessions.iterator();
        while (it.hasNext()) {
            Session child = it.next();
            sb.append("\n");
            for (int i = 0; i <= tabI; i++) {
                sb.append("\t");
            }
            child.printSessionTree(tabI + 1, sb);
        }
    }

    public String getFullMethodPath(boolean truncatePath) {
        StringBuilder sb = new StringBuilder();
        getFullMethodPath(sb, truncatePath);
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void getFullMethodPath(java.lang.StringBuilder r5, boolean r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r0 = r4.mFullMethodPathCache     // Catch:{ all -> 0x005b }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x005b }
            if (r0 != 0) goto L_0x0012
            if (r6 != 0) goto L_0x0012
            java.lang.String r0 = r4.mFullMethodPathCache     // Catch:{ all -> 0x005b }
            r5.append(r0)     // Catch:{ all -> 0x005b }
            monitor-exit(r4)
            return
        L_0x0012:
            android.telecom.Logging.Session r0 = r4.getParentSession()     // Catch:{ all -> 0x005b }
            r1 = 0
            if (r0 == 0) goto L_0x002c
            java.lang.String r2 = r4.mShortMethodName     // Catch:{ all -> 0x005b }
            java.lang.String r3 = r0.mShortMethodName     // Catch:{ all -> 0x005b }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x005b }
            r2 = r2 ^ 1
            r1 = r2
            r0.getFullMethodPath(r5, r6)     // Catch:{ all -> 0x005b }
            java.lang.String r2 = "->"
            r5.append(r2)     // Catch:{ all -> 0x005b }
        L_0x002c:
            boolean r2 = r4.isExternal()     // Catch:{ all -> 0x005b }
            if (r2 == 0) goto L_0x004a
            if (r6 == 0) goto L_0x003a
            java.lang.String r2 = "..."
            r5.append(r2)     // Catch:{ all -> 0x005b }
            goto L_0x004f
        L_0x003a:
            java.lang.String r2 = "("
            r5.append(r2)     // Catch:{ all -> 0x005b }
            java.lang.String r2 = r4.mShortMethodName     // Catch:{ all -> 0x005b }
            r5.append(r2)     // Catch:{ all -> 0x005b }
            java.lang.String r2 = ")"
            r5.append(r2)     // Catch:{ all -> 0x005b }
            goto L_0x004f
        L_0x004a:
            java.lang.String r2 = r4.mShortMethodName     // Catch:{ all -> 0x005b }
            r5.append(r2)     // Catch:{ all -> 0x005b }
        L_0x004f:
            if (r1 == 0) goto L_0x0059
            if (r6 != 0) goto L_0x0059
            java.lang.String r2 = r5.toString()     // Catch:{ all -> 0x005b }
            r4.mFullMethodPathCache = r2     // Catch:{ all -> 0x005b }
        L_0x0059:
            monitor-exit(r4)
            return
        L_0x005b:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telecom.Logging.Session.getFullMethodPath(java.lang.StringBuilder, boolean):void");
    }

    /* access modifiers changed from: private */
    public boolean isSessionExternal() {
        if (getParentSession() == null) {
            return isExternal();
        }
        return getParentSession().isSessionExternal();
    }

    public int hashCode() {
        int i = 0;
        int result = (((((((((((((((((this.mSessionId != null ? this.mSessionId.hashCode() : 0) * 31) + (this.mShortMethodName != null ? this.mShortMethodName.hashCode() : 0)) * 31) + ((int) (this.mExecutionStartTimeMs ^ (this.mExecutionStartTimeMs >>> 32)))) * 31) + ((int) (this.mExecutionEndTimeMs ^ (this.mExecutionEndTimeMs >>> 32)))) * 31) + (this.mParentSession != null ? this.mParentSession.hashCode() : 0)) * 31) + (this.mChildSessions != null ? this.mChildSessions.hashCode() : 0)) * 31) + (this.mIsCompleted ? 1 : 0)) * 31) + this.mChildCounter) * 31) + (this.mIsStartedFromActiveSession ? 1 : 0)) * 31;
        if (this.mOwnerInfo != null) {
            i = this.mOwnerInfo.hashCode();
        }
        return result + i;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        if (this.mExecutionStartTimeMs != session.mExecutionStartTimeMs || this.mExecutionEndTimeMs != session.mExecutionEndTimeMs || this.mIsCompleted != session.mIsCompleted || this.mChildCounter != session.mChildCounter || this.mIsStartedFromActiveSession != session.mIsStartedFromActiveSession) {
            return false;
        }
        if (this.mSessionId == null ? session.mSessionId != null : !this.mSessionId.equals(session.mSessionId)) {
            return false;
        }
        if (this.mShortMethodName == null ? session.mShortMethodName != null : !this.mShortMethodName.equals(session.mShortMethodName)) {
            return false;
        }
        if (this.mParentSession == null ? session.mParentSession != null : !this.mParentSession.equals(session.mParentSession)) {
            return false;
        }
        if (this.mChildSessions == null ? session.mChildSessions != null : !this.mChildSessions.equals(session.mChildSessions)) {
            return false;
        }
        if (this.mOwnerInfo != null) {
            return this.mOwnerInfo.equals(session.mOwnerInfo);
        }
        if (session.mOwnerInfo == null) {
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.mParentSession != null && this.mIsStartedFromActiveSession) {
            return this.mParentSession.toString();
        }
        StringBuilder methodName = new StringBuilder();
        methodName.append(getFullMethodPath(false));
        if (this.mOwnerInfo != null && !this.mOwnerInfo.isEmpty()) {
            methodName.append("(InCall package: ");
            methodName.append(this.mOwnerInfo);
            methodName.append(")");
        }
        return methodName.toString() + "@" + getFullSessionId();
    }
}
