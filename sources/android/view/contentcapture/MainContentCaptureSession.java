package android.view.contentcapture;

import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.ParceledListSlice;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.util.LocalLog;
import android.util.Log;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.contentcapture.IContentCaptureDirectManager;
import android.view.contentcapture.ViewNode;
import com.android.internal.p016os.IResultReceiver;
import com.ibm.icu.text.PluralRules;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class MainContentCaptureSession extends ContentCaptureSession {
    public static final String EXTRA_BINDER = "binder";
    public static final String EXTRA_ENABLED_STATE = "enabled";
    private static final boolean FORCE_FLUSH = true;
    private static final int MSG_FLUSH = 1;
    private static final String TAG = MainContentCaptureSession.class.getSimpleName();
    private IBinder mApplicationToken;
    private ComponentName mComponentName;
    private final Context mContext;
    private IContentCaptureDirectManager mDirectServiceInterface;
    private IBinder.DeathRecipient mDirectServiceVulture;
    private ArrayList<ContentCaptureEvent> mEvents;
    private final LocalLog mFlushHistory;
    private final Handler mHandler;
    private final ContentCaptureManager mManager;
    private long mNextFlush;
    private final IResultReceiver.Stub mSessionStateReceiver;
    private final IContentCaptureManager mSystemServerInterface;
    private final AtomicBoolean mDisabled = new AtomicBoolean(false);
    private int mState = 0;
    private boolean mNextFlushForTextChanged = false;

    protected MainContentCaptureSession(Context context, ContentCaptureManager manager, Handler handler, IContentCaptureManager systemServerInterface) {
        this.mContext = context;
        this.mManager = manager;
        this.mHandler = handler;
        this.mSystemServerInterface = systemServerInterface;
        int logHistorySize = this.mManager.mOptions.logHistorySize;
        this.mFlushHistory = logHistorySize > 0 ? new LocalLog(logHistorySize) : null;
        this.mSessionStateReceiver = new BinderC28001();
    }

    /* renamed from: android.view.contentcapture.MainContentCaptureSession$1 */
    /* loaded from: classes4.dex */
    class BinderC28001 extends IResultReceiver.Stub {
        BinderC28001() {
        }

        @Override // com.android.internal.p016os.IResultReceiver
        public void send(final int resultCode, Bundle resultData) {
            IBinder binder;
            if (resultData != null) {
                boolean hasEnabled = resultData.getBoolean("enabled");
                if (hasEnabled) {
                    boolean disabled = resultCode == 2;
                    MainContentCaptureSession.this.mDisabled.set(disabled);
                    return;
                }
                binder = resultData.getBinder(MainContentCaptureSession.EXTRA_BINDER);
                if (binder == null) {
                    Log.wtf(MainContentCaptureSession.TAG, "No binder extra result");
                    MainContentCaptureSession.this.mHandler.post(new Runnable() { // from class: android.view.contentcapture.-$$Lambda$MainContentCaptureSession$1$JPRO-nNGZpgXrKr4QC_iQiTbQx0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainContentCaptureSession.this.resetSession(260);
                        }
                    });
                    return;
                }
            } else {
                binder = null;
            }
            final IBinder binder2 = binder;
            MainContentCaptureSession.this.mHandler.post(new Runnable() { // from class: android.view.contentcapture.-$$Lambda$MainContentCaptureSession$1$Xhq3WJibbalS1G_W3PRC2m7muhM
                @Override // java.lang.Runnable
                public final void run() {
                    MainContentCaptureSession.this.onSessionStarted(resultCode, binder2);
                }
            });
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    MainContentCaptureSession getMainCaptureSession() {
        return this;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession newChild(ContentCaptureContext clientContext) {
        ContentCaptureSession child = new ChildContentCaptureSession(this, clientContext);
        notifyChildSessionStarted(this.mId, child.mId, clientContext);
        return child;
    }

    void start(IBinder token, ComponentName component, int flags) {
        if (isContentCaptureEnabled()) {
            if (ContentCaptureHelper.sVerbose) {
                String str = TAG;
                Log.m66v(str, "start(): token=" + token + ", comp=" + ComponentName.flattenToShortString(component));
            }
            if (hasStarted()) {
                if (ContentCaptureHelper.sDebug) {
                    String str2 = TAG;
                    Log.m72d(str2, "ignoring handleStartSession(" + token + "/" + ComponentName.flattenToShortString(component) + " while on state " + getStateAsString(this.mState));
                    return;
                }
                return;
            }
            this.mState = 1;
            this.mApplicationToken = token;
            this.mComponentName = component;
            if (ContentCaptureHelper.sVerbose) {
                String str3 = TAG;
                Log.m66v(str3, "handleStartSession(): token=" + token + ", act=" + getDebugState() + ", id=" + this.mId);
            }
            try {
                this.mSystemServerInterface.startSession(this.mApplicationToken, component, this.mId, flags, this.mSessionStateReceiver);
            } catch (RemoteException e) {
                String str4 = TAG;
                Log.m64w(str4, "Error starting session for " + component.flattenToShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e);
            }
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.post(new Runnable() { // from class: android.view.contentcapture.-$$Lambda$MainContentCaptureSession$HTmdDf687TPcaTnLyPp3wo0gI60
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.destroySession();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSessionStarted(int resultCode, IBinder binder) {
        if (binder != null) {
            this.mDirectServiceInterface = IContentCaptureDirectManager.Stub.asInterface(binder);
            this.mDirectServiceVulture = new IBinder.DeathRecipient() { // from class: android.view.contentcapture.-$$Lambda$MainContentCaptureSession$UWslDbWedtPhv49PtRsvG4TlYWw
                @Override // android.p007os.IBinder.DeathRecipient
                public final void binderDied() {
                    MainContentCaptureSession.lambda$onSessionStarted$1(MainContentCaptureSession.this);
                }
            };
            try {
                binder.linkToDeath(this.mDirectServiceVulture, 0);
            } catch (RemoteException e) {
                String str = TAG;
                Log.m64w(str, "Failed to link to death on " + binder + PluralRules.KEYWORD_RULE_SEPARATOR + e);
            }
        }
        if ((resultCode & 4) != 0) {
            resetSession(resultCode);
        } else {
            this.mState = resultCode;
            this.mDisabled.set(false);
        }
        if (ContentCaptureHelper.sVerbose) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("handleSessionStarted() result: id=");
            sb.append(this.mId);
            sb.append(" resultCode=");
            sb.append(resultCode);
            sb.append(", state=");
            sb.append(getStateAsString(this.mState));
            sb.append(", disabled=");
            sb.append(this.mDisabled.get());
            sb.append(", binder=");
            sb.append(binder);
            sb.append(", events=");
            sb.append(this.mEvents != null ? this.mEvents.size() : 0);
            Log.m66v(str2, sb.toString());
        }
    }

    public static /* synthetic */ void lambda$onSessionStarted$1(MainContentCaptureSession mainContentCaptureSession) {
        String str = TAG;
        Log.m64w(str, "Keeping session " + mainContentCaptureSession.mId + " when service died");
        mainContentCaptureSession.mState = 1024;
        mainContentCaptureSession.mDisabled.set(true);
    }

    private void sendEvent(ContentCaptureEvent event) {
        sendEvent(event, false);
    }

    private void sendEvent(ContentCaptureEvent event, boolean forceFlush) {
        int flushReason;
        int eventType = event.getType();
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.m66v(str, "handleSendEvent(" + getDebugState() + "): " + event);
        }
        if (!hasStarted() && eventType != -1 && eventType != 6) {
            String str2 = TAG;
            Log.m66v(str2, "handleSendEvent(" + getDebugState() + ", " + ContentCaptureEvent.getTypeAsString(eventType) + "): dropping because session not started yet");
        } else if (this.mDisabled.get()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.m66v(TAG, "handleSendEvent(): ignoring when disabled");
            }
        } else {
            int maxBufferSize = this.mManager.mOptions.maxBufferSize;
            if (this.mEvents == null) {
                if (ContentCaptureHelper.sVerbose) {
                    String str3 = TAG;
                    Log.m66v(str3, "handleSendEvent(): creating buffer for " + maxBufferSize + " events");
                }
                this.mEvents = new ArrayList<>(maxBufferSize);
            }
            boolean addEvent = true;
            int flushReason2 = 1;
            if (!this.mEvents.isEmpty() && eventType == 3) {
                ContentCaptureEvent lastEvent = this.mEvents.get(this.mEvents.size() - 1);
                if (lastEvent.getType() == 3 && lastEvent.getId().equals(event.getId())) {
                    if (ContentCaptureHelper.sVerbose) {
                        String str4 = TAG;
                        Log.m66v(str4, "Buffering VIEW_TEXT_CHANGED event, updated text=" + ContentCaptureHelper.getSanitizedString(event.getText()));
                    }
                    lastEvent.mergeEvent(event);
                    addEvent = false;
                }
            }
            if (!this.mEvents.isEmpty() && eventType == 2) {
                ContentCaptureEvent lastEvent2 = this.mEvents.get(this.mEvents.size() - 1);
                if (lastEvent2.getType() == 2 && event.getSessionId() == lastEvent2.getSessionId()) {
                    if (ContentCaptureHelper.sVerbose) {
                        String str5 = TAG;
                        Log.m66v(str5, "Buffering TYPE_VIEW_DISAPPEARED events for session " + lastEvent2.getSessionId());
                    }
                    lastEvent2.mergeEvent(event);
                    addEvent = false;
                }
            }
            if (addEvent) {
                this.mEvents.add(event);
            }
            int numberEvents = this.mEvents.size();
            boolean bufferEvent = numberEvents < maxBufferSize;
            if (bufferEvent && !forceFlush) {
                if (eventType == 3) {
                    this.mNextFlushForTextChanged = true;
                    flushReason = 6;
                } else if (this.mNextFlushForTextChanged) {
                    if (ContentCaptureHelper.sVerbose) {
                        Log.m68i(TAG, "Not scheduling flush because next flush is for text changed");
                        return;
                    }
                    return;
                } else {
                    flushReason = 5;
                }
                scheduleFlush(flushReason, true);
                return;
            }
            int flushReason3 = this.mState;
            if (flushReason3 != 2 && numberEvents >= maxBufferSize) {
                if (ContentCaptureHelper.sDebug) {
                    String str6 = TAG;
                    Log.m72d(str6, "Closing session for " + getDebugState() + " after " + numberEvents + " delayed events");
                }
                resetSession(132);
                return;
            }
            switch (eventType) {
                case -2:
                    flushReason2 = 4;
                    break;
                case -1:
                    flushReason2 = 3;
                    break;
            }
            flush(flushReason2);
        }
    }

    private boolean hasStarted() {
        return this.mState != 0;
    }

    private void scheduleFlush(final int reason, boolean checkExisting) {
        int flushFrequencyMs;
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.m66v(str, "handleScheduleFlush(" + getDebugState(reason) + ", checkExisting=" + checkExisting);
        }
        if (!hasStarted()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.m66v(TAG, "handleScheduleFlush(): session not started yet");
            }
        } else if (this.mDisabled.get()) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("handleScheduleFlush(");
            sb.append(getDebugState(reason));
            sb.append("): should not be called when disabled. events=");
            sb.append(this.mEvents == null ? null : Integer.valueOf(this.mEvents.size()));
            Log.m70e(str2, sb.toString());
        } else {
            if (checkExisting && this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            if (reason == 5) {
                flushFrequencyMs = this.mManager.mOptions.idleFlushingFrequencyMs;
            } else if (reason == 6) {
                flushFrequencyMs = this.mManager.mOptions.textChangeFlushingFrequencyMs;
            } else {
                String str3 = TAG;
                Log.m70e(str3, "handleScheduleFlush(" + getDebugState(reason) + "): not called with a timeout reason.");
                return;
            }
            this.mNextFlush = System.currentTimeMillis() + flushFrequencyMs;
            if (ContentCaptureHelper.sVerbose) {
                String str4 = TAG;
                Log.m66v(str4, "handleScheduleFlush(): scheduled to flush in " + flushFrequencyMs + "ms: " + TimeUtils.logTimeOfDay(this.mNextFlush));
            }
            this.mHandler.postDelayed(new Runnable() { // from class: android.view.contentcapture.-$$Lambda$MainContentCaptureSession$49zT7C2BXrEdkyggyGk1Qs4d46k
                @Override // java.lang.Runnable
                public final void run() {
                    MainContentCaptureSession.this.flushIfNeeded(reason);
                }
            }, 1, flushFrequencyMs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flushIfNeeded(int reason) {
        if (this.mEvents == null || this.mEvents.isEmpty()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.m66v(TAG, "Nothing to flush");
                return;
            }
            return;
        }
        flush(reason);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void flush(int reason) {
        if (this.mEvents == null) {
            return;
        }
        if (this.mDisabled.get()) {
            Log.m70e(TAG, "handleForceFlush(" + getDebugState(reason) + "): should not be when disabled");
        } else if (this.mDirectServiceInterface == null) {
            if (ContentCaptureHelper.sVerbose) {
                Log.m66v(TAG, "handleForceFlush(" + getDebugState(reason) + "): hold your horses, client not ready: " + this.mEvents);
            }
            if (!this.mHandler.hasMessages(1)) {
                scheduleFlush(reason, false);
            }
        } else {
            int numberEvents = this.mEvents.size();
            String reasonString = getFlushReasonAsString(reason);
            if (ContentCaptureHelper.sDebug) {
                Log.m72d(TAG, "Flushing " + numberEvents + " event(s) for " + getDebugState(reason));
            }
            if (this.mFlushHistory != null) {
                String logRecord = "r=" + reasonString + " s=" + numberEvents + " m=" + this.mManager.mOptions.maxBufferSize + " i=" + this.mManager.mOptions.idleFlushingFrequencyMs;
                this.mFlushHistory.log(logRecord);
            }
            try {
                this.mHandler.removeMessages(1);
                if (reason == 6) {
                    this.mNextFlushForTextChanged = false;
                }
                ParceledListSlice<ContentCaptureEvent> events = clearEvents();
                this.mDirectServiceInterface.sendEvents(events, reason, this.mManager.mOptions);
            } catch (RemoteException e) {
                Log.m64w(TAG, "Error sending " + numberEvents + " for " + getDebugState() + PluralRules.KEYWORD_RULE_SEPARATOR + e);
            }
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(ContentCaptureContext context) {
        notifyContextUpdated(this.mId, context);
    }

    private ParceledListSlice<ContentCaptureEvent> clearEvents() {
        List<ContentCaptureEvent> events;
        if (this.mEvents == null) {
            events = Collections.emptyList();
        } else {
            events = this.mEvents;
        }
        this.mEvents = null;
        return new ParceledListSlice<>(events);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroySession() {
        if (ContentCaptureHelper.sDebug) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Destroying session (ctx=");
            sb.append(this.mContext);
            sb.append(", id=");
            sb.append(this.mId);
            sb.append(") with ");
            sb.append(this.mEvents == null ? 0 : this.mEvents.size());
            sb.append(" event(s) for ");
            sb.append(getDebugState());
            Log.m72d(str, sb.toString());
        }
        try {
            this.mSystemServerInterface.finishSession(this.mId);
        } catch (RemoteException e) {
            String str2 = TAG;
            Log.m70e(str2, "Error destroying system-service session " + this.mId + " for " + getDebugState() + PluralRules.KEYWORD_RULE_SEPARATOR + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetSession(int newState) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.m66v(str, "handleResetSession(" + getActivityName() + "): from " + getStateAsString(this.mState) + " to " + getStateAsString(newState));
        }
        this.mState = newState;
        this.mDisabled.set((newState & 4) != 0);
        this.mApplicationToken = null;
        this.mComponentName = null;
        this.mEvents = null;
        if (this.mDirectServiceInterface != null) {
            this.mDirectServiceInterface.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
        }
        this.mDirectServiceInterface = null;
        this.mHandler.removeMessages(1);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(ViewNode.ViewStructureImpl node) {
        notifyViewAppeared(this.mId, node);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(AutofillId id) {
        notifyViewDisappeared(this.mId, id);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(AutofillId id, CharSequence text) {
        notifyViewTextChanged(this.mId, id, text);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(boolean started) {
        notifyViewTreeEvent(this.mId, started);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return super.isContentCaptureEnabled() && this.mManager.isContentCaptureEnabled();
    }

    boolean isDisabled() {
        return this.mDisabled.get();
    }

    boolean setDisabled(boolean disabled) {
        return this.mDisabled.compareAndSet(!disabled, disabled);
    }

    void notifyChildSessionStarted(int parentSessionId, int childSessionId, ContentCaptureContext clientContext) {
        sendEvent(new ContentCaptureEvent(childSessionId, -1).setParentSessionId(parentSessionId).setClientContext(clientContext), true);
    }

    void notifyChildSessionFinished(int parentSessionId, int childSessionId) {
        sendEvent(new ContentCaptureEvent(childSessionId, -2).setParentSessionId(parentSessionId), true);
    }

    void notifyViewAppeared(int sessionId, ViewNode.ViewStructureImpl node) {
        sendEvent(new ContentCaptureEvent(sessionId, 1).setViewNode(node.mNode));
    }

    public void notifyViewDisappeared(int sessionId, AutofillId id) {
        sendEvent(new ContentCaptureEvent(sessionId, 2).setAutofillId(id));
    }

    void notifyViewTextChanged(int sessionId, AutofillId id, CharSequence text) {
        sendEvent(new ContentCaptureEvent(sessionId, 3).setAutofillId(id).setText(text));
    }

    public void notifyViewTreeEvent(int sessionId, boolean started) {
        int type = started ? 4 : 5;
        sendEvent(new ContentCaptureEvent(sessionId, type), true);
    }

    public void notifySessionLifecycle(boolean started) {
        int type = started ? 7 : 8;
        sendEvent(new ContentCaptureEvent(this.mId, type), true);
    }

    void notifyContextUpdated(int sessionId, ContentCaptureContext context) {
        sendEvent(new ContentCaptureEvent(sessionId, 6).setClientContext(context));
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void dump(String prefix, PrintWriter pw) {
        super.dump(prefix, pw);
        pw.print(prefix);
        pw.print("mContext: ");
        pw.println(this.mContext);
        pw.print(prefix);
        pw.print("user: ");
        pw.println(this.mContext.getUserId());
        if (this.mDirectServiceInterface != null) {
            pw.print(prefix);
            pw.print("mDirectServiceInterface: ");
            pw.println(this.mDirectServiceInterface);
        }
        pw.print(prefix);
        pw.print("mDisabled: ");
        pw.println(this.mDisabled.get());
        pw.print(prefix);
        pw.print("isEnabled(): ");
        pw.println(isContentCaptureEnabled());
        pw.print(prefix);
        pw.print("state: ");
        pw.println(getStateAsString(this.mState));
        if (this.mApplicationToken != null) {
            pw.print(prefix);
            pw.print("app token: ");
            pw.println(this.mApplicationToken);
        }
        if (this.mComponentName != null) {
            pw.print(prefix);
            pw.print("component name: ");
            pw.println(this.mComponentName.flattenToShortString());
        }
        if (this.mEvents != null && !this.mEvents.isEmpty()) {
            int numberEvents = this.mEvents.size();
            pw.print(prefix);
            pw.print("buffered events: ");
            pw.print(numberEvents);
            pw.print('/');
            pw.println(this.mManager.mOptions.maxBufferSize);
            if (ContentCaptureHelper.sVerbose && numberEvents > 0) {
                String prefix3 = prefix + "  ";
                for (int i = 0; i < numberEvents; i++) {
                    ContentCaptureEvent event = this.mEvents.get(i);
                    pw.print(prefix3);
                    pw.print(i);
                    pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                    event.dump(pw);
                    pw.println();
                }
            }
            pw.print(prefix);
            pw.print("mNextFlushForTextChanged: ");
            pw.println(this.mNextFlushForTextChanged);
            pw.print(prefix);
            pw.print("flush frequency: ");
            if (this.mNextFlushForTextChanged) {
                pw.println(this.mManager.mOptions.textChangeFlushingFrequencyMs);
            } else {
                pw.println(this.mManager.mOptions.idleFlushingFrequencyMs);
            }
            pw.print(prefix);
            pw.print("next flush: ");
            TimeUtils.formatDuration(this.mNextFlush - System.currentTimeMillis(), pw);
            pw.print(" (");
            pw.print(TimeUtils.logTimeOfDay(this.mNextFlush));
            pw.println(")");
        }
        if (this.mFlushHistory != null) {
            pw.print(prefix);
            pw.println("flush history:");
            this.mFlushHistory.reverseDump(null, pw, null);
            pw.println();
        } else {
            pw.print(prefix);
            pw.println("not logging flush history");
        }
        super.dump(prefix, pw);
    }

    private String getActivityName() {
        if (this.mComponentName == null) {
            return "pkg:" + this.mContext.getPackageName();
        }
        return "act:" + this.mComponentName.flattenToShortString();
    }

    private String getDebugState() {
        return getActivityName() + " [state=" + getStateAsString(this.mState) + ", disabled=" + this.mDisabled.get() + "]";
    }

    private String getDebugState(int reason) {
        return getDebugState() + ", reason=" + getFlushReasonAsString(reason);
    }
}
