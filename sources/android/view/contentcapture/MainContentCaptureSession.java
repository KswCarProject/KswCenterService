package android.view.contentcapture;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.LocalLog;
import android.util.Log;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.contentcapture.IContentCaptureDirectManager;
import android.view.contentcapture.ViewNode;
import com.android.internal.os.IResultReceiver;
import com.ibm.icu.text.PluralRules;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MainContentCaptureSession extends ContentCaptureSession {
    public static final String EXTRA_BINDER = "binder";
    public static final String EXTRA_ENABLED_STATE = "enabled";
    private static final boolean FORCE_FLUSH = true;
    private static final int MSG_FLUSH = 1;
    /* access modifiers changed from: private */
    public static final String TAG = MainContentCaptureSession.class.getSimpleName();
    private IBinder mApplicationToken;
    private ComponentName mComponentName;
    private final Context mContext;
    private IContentCaptureDirectManager mDirectServiceInterface;
    private IBinder.DeathRecipient mDirectServiceVulture;
    /* access modifiers changed from: private */
    public final AtomicBoolean mDisabled = new AtomicBoolean(false);
    private ArrayList<ContentCaptureEvent> mEvents;
    private final LocalLog mFlushHistory;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    private final ContentCaptureManager mManager;
    private long mNextFlush;
    private boolean mNextFlushForTextChanged = false;
    private final IResultReceiver.Stub mSessionStateReceiver;
    private int mState = 0;
    private final IContentCaptureManager mSystemServerInterface;

    protected MainContentCaptureSession(Context context, ContentCaptureManager manager, Handler handler, IContentCaptureManager systemServerInterface) {
        this.mContext = context;
        this.mManager = manager;
        this.mHandler = handler;
        this.mSystemServerInterface = systemServerInterface;
        int logHistorySize = this.mManager.mOptions.logHistorySize;
        this.mFlushHistory = logHistorySize > 0 ? new LocalLog(logHistorySize) : null;
        this.mSessionStateReceiver = new IResultReceiver.Stub() {
            public void send(int resultCode, Bundle resultData) {
                IBinder binder;
                if (resultData == null) {
                    binder = null;
                } else if (resultData.getBoolean("enabled")) {
                    MainContentCaptureSession.this.mDisabled.set(resultCode == 2);
                    return;
                } else {
                    binder = resultData.getBinder(MainContentCaptureSession.EXTRA_BINDER);
                    if (binder == null) {
                        Log.wtf(MainContentCaptureSession.TAG, "No binder extra result");
                        MainContentCaptureSession.this.mHandler.post(new Runnable() {
                            public final void run() {
                                MainContentCaptureSession.this.resetSession(260);
                            }
                        });
                        return;
                    }
                }
                MainContentCaptureSession.this.mHandler.post(new Runnable(resultCode, binder) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ IBinder f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        MainContentCaptureSession.this.onSessionStarted(this.f$1, this.f$2);
                    }
                });
            }
        };
    }

    /* access modifiers changed from: package-private */
    public MainContentCaptureSession getMainCaptureSession() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ContentCaptureSession newChild(ContentCaptureContext clientContext) {
        ContentCaptureSession child = new ChildContentCaptureSession(this, clientContext);
        notifyChildSessionStarted(this.mId, child.mId, clientContext);
        return child;
    }

    /* access modifiers changed from: package-private */
    public void start(IBinder token, ComponentName component, int flags) {
        if (isContentCaptureEnabled()) {
            if (ContentCaptureHelper.sVerbose) {
                String str = TAG;
                Log.v(str, "start(): token=" + token + ", comp=" + ComponentName.flattenToShortString(component));
            }
            if (!hasStarted()) {
                this.mState = 1;
                this.mApplicationToken = token;
                this.mComponentName = component;
                if (ContentCaptureHelper.sVerbose) {
                    String str2 = TAG;
                    Log.v(str2, "handleStartSession(): token=" + token + ", act=" + getDebugState() + ", id=" + this.mId);
                }
                try {
                    this.mSystemServerInterface.startSession(this.mApplicationToken, component, this.mId, flags, this.mSessionStateReceiver);
                } catch (RemoteException e) {
                    String str3 = TAG;
                    Log.w(str3, "Error starting session for " + component.flattenToShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e);
                }
            } else if (ContentCaptureHelper.sDebug) {
                String str4 = TAG;
                Log.d(str4, "ignoring handleStartSession(" + token + "/" + ComponentName.flattenToShortString(component) + " while on state " + getStateAsString(this.mState));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.post(new Runnable() {
            public final void run() {
                MainContentCaptureSession.this.destroySession();
            }
        });
    }

    /* access modifiers changed from: private */
    public void onSessionStarted(int resultCode, IBinder binder) {
        int i = 0;
        if (binder != null) {
            this.mDirectServiceInterface = IContentCaptureDirectManager.Stub.asInterface(binder);
            this.mDirectServiceVulture = new IBinder.DeathRecipient() {
                public final void binderDied() {
                    MainContentCaptureSession.lambda$onSessionStarted$1(MainContentCaptureSession.this);
                }
            };
            try {
                binder.linkToDeath(this.mDirectServiceVulture, 0);
            } catch (RemoteException e) {
                String str = TAG;
                Log.w(str, "Failed to link to death on " + binder + PluralRules.KEYWORD_RULE_SEPARATOR + e);
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
            if (this.mEvents != null) {
                i = this.mEvents.size();
            }
            sb.append(i);
            Log.v(str2, sb.toString());
        }
    }

    public static /* synthetic */ void lambda$onSessionStarted$1(MainContentCaptureSession mainContentCaptureSession) {
        String str = TAG;
        Log.w(str, "Keeping session " + mainContentCaptureSession.mId + " when service died");
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
            Log.v(str, "handleSendEvent(" + getDebugState() + "): " + event);
        }
        if (!hasStarted() && eventType != -1 && eventType != 6) {
            String str2 = TAG;
            Log.v(str2, "handleSendEvent(" + getDebugState() + ", " + ContentCaptureEvent.getTypeAsString(eventType) + "): dropping because session not started yet");
        } else if (!this.mDisabled.get()) {
            int maxBufferSize = this.mManager.mOptions.maxBufferSize;
            if (this.mEvents == null) {
                if (ContentCaptureHelper.sVerbose) {
                    String str3 = TAG;
                    Log.v(str3, "handleSendEvent(): creating buffer for " + maxBufferSize + " events");
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
                        Log.v(str4, "Buffering VIEW_TEXT_CHANGED event, updated text=" + ContentCaptureHelper.getSanitizedString(event.getText()));
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
                        Log.v(str5, "Buffering TYPE_VIEW_DISAPPEARED events for session " + lastEvent2.getSessionId());
                    }
                    lastEvent2.mergeEvent(event);
                    addEvent = false;
                }
            }
            if (addEvent) {
                this.mEvents.add(event);
            }
            int numberEvents = this.mEvents.size();
            if ((numberEvents < maxBufferSize) && !forceFlush) {
                if (eventType == 3) {
                    this.mNextFlushForTextChanged = true;
                    flushReason = 6;
                } else if (!this.mNextFlushForTextChanged) {
                    flushReason = 5;
                } else if (ContentCaptureHelper.sVerbose) {
                    Log.i(TAG, "Not scheduling flush because next flush is for text changed");
                    return;
                } else {
                    return;
                }
                scheduleFlush(flushReason, true);
            } else if (this.mState == 2 || numberEvents < maxBufferSize) {
                switch (eventType) {
                    case -2:
                        flushReason2 = 4;
                        break;
                    case -1:
                        flushReason2 = 3;
                        break;
                }
                flush(flushReason2);
            } else {
                if (ContentCaptureHelper.sDebug) {
                    String str6 = TAG;
                    Log.d(str6, "Closing session for " + getDebugState() + " after " + numberEvents + " delayed events");
                }
                resetSession(132);
            }
        } else if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "handleSendEvent(): ignoring when disabled");
        }
    }

    private boolean hasStarted() {
        return this.mState != 0;
    }

    private void scheduleFlush(int reason, boolean checkExisting) {
        int flushFrequencyMs;
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "handleScheduleFlush(" + getDebugState(reason) + ", checkExisting=" + checkExisting);
        }
        if (!hasStarted()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleScheduleFlush(): session not started yet");
            }
        } else if (this.mDisabled.get()) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("handleScheduleFlush(");
            sb.append(getDebugState(reason));
            sb.append("): should not be called when disabled. events=");
            sb.append(this.mEvents == null ? null : Integer.valueOf(this.mEvents.size()));
            Log.e(str2, sb.toString());
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
                Log.e(str3, "handleScheduleFlush(" + getDebugState(reason) + "): not called with a timeout reason.");
                return;
            }
            this.mNextFlush = System.currentTimeMillis() + ((long) flushFrequencyMs);
            if (ContentCaptureHelper.sVerbose) {
                String str4 = TAG;
                Log.v(str4, "handleScheduleFlush(): scheduled to flush in " + flushFrequencyMs + "ms: " + TimeUtils.logTimeOfDay(this.mNextFlush));
            }
            this.mHandler.postDelayed((Runnable) new Runnable(reason) {
                private final /* synthetic */ int f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    MainContentCaptureSession.this.flushIfNeeded(this.f$1);
                }
            }, 1, (long) flushFrequencyMs);
        }
    }

    /* access modifiers changed from: private */
    public void flushIfNeeded(int reason) {
        if (this.mEvents != null && !this.mEvents.isEmpty()) {
            flush(reason);
        } else if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "Nothing to flush");
        }
    }

    /* access modifiers changed from: package-private */
    public void flush(int reason) {
        if (this.mEvents != null) {
            if (this.mDisabled.get()) {
                String str = TAG;
                Log.e(str, "handleForceFlush(" + getDebugState(reason) + "): should not be when disabled");
            } else if (this.mDirectServiceInterface == null) {
                if (ContentCaptureHelper.sVerbose) {
                    String str2 = TAG;
                    Log.v(str2, "handleForceFlush(" + getDebugState(reason) + "): hold your horses, client not ready: " + this.mEvents);
                }
                if (!this.mHandler.hasMessages(1)) {
                    scheduleFlush(reason, false);
                }
            } else {
                int numberEvents = this.mEvents.size();
                String reasonString = getFlushReasonAsString(reason);
                if (ContentCaptureHelper.sDebug) {
                    String str3 = TAG;
                    Log.d(str3, "Flushing " + numberEvents + " event(s) for " + getDebugState(reason));
                }
                if (this.mFlushHistory != null) {
                    this.mFlushHistory.log("r=" + reasonString + " s=" + numberEvents + " m=" + this.mManager.mOptions.maxBufferSize + " i=" + this.mManager.mOptions.idleFlushingFrequencyMs);
                }
                try {
                    this.mHandler.removeMessages(1);
                    if (reason == 6) {
                        this.mNextFlushForTextChanged = false;
                    }
                    this.mDirectServiceInterface.sendEvents(clearEvents(), reason, this.mManager.mOptions);
                } catch (RemoteException e) {
                    String str4 = TAG;
                    Log.w(str4, "Error sending " + numberEvents + " for " + getDebugState() + PluralRules.KEYWORD_RULE_SEPARATOR + e);
                }
            }
        }
    }

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

    /* access modifiers changed from: private */
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
            Log.d(str, sb.toString());
        }
        try {
            this.mSystemServerInterface.finishSession(this.mId);
        } catch (RemoteException e) {
            String str2 = TAG;
            Log.e(str2, "Error destroying system-service session " + this.mId + " for " + getDebugState() + PluralRules.KEYWORD_RULE_SEPARATOR + e);
        }
    }

    /* access modifiers changed from: private */
    public void resetSession(int newState) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "handleResetSession(" + getActivityName() + "): from " + getStateAsString(this.mState) + " to " + getStateAsString(newState));
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

    /* access modifiers changed from: package-private */
    public void internalNotifyViewAppeared(ViewNode.ViewStructureImpl node) {
        notifyViewAppeared(this.mId, node);
    }

    /* access modifiers changed from: package-private */
    public void internalNotifyViewDisappeared(AutofillId id) {
        notifyViewDisappeared(this.mId, id);
    }

    /* access modifiers changed from: package-private */
    public void internalNotifyViewTextChanged(AutofillId id, CharSequence text) {
        notifyViewTextChanged(this.mId, id, text);
    }

    public void internalNotifyViewTreeEvent(boolean started) {
        notifyViewTreeEvent(this.mId, started);
    }

    /* access modifiers changed from: package-private */
    public boolean isContentCaptureEnabled() {
        return super.isContentCaptureEnabled() && this.mManager.isContentCaptureEnabled();
    }

    /* access modifiers changed from: package-private */
    public boolean isDisabled() {
        return this.mDisabled.get();
    }

    /* access modifiers changed from: package-private */
    public boolean setDisabled(boolean disabled) {
        return this.mDisabled.compareAndSet(!disabled, disabled);
    }

    /* access modifiers changed from: package-private */
    public void notifyChildSessionStarted(int parentSessionId, int childSessionId, ContentCaptureContext clientContext) {
        sendEvent(new ContentCaptureEvent(childSessionId, -1).setParentSessionId(parentSessionId).setClientContext(clientContext), true);
    }

    /* access modifiers changed from: package-private */
    public void notifyChildSessionFinished(int parentSessionId, int childSessionId) {
        sendEvent(new ContentCaptureEvent(childSessionId, -2).setParentSessionId(parentSessionId), true);
    }

    /* access modifiers changed from: package-private */
    public void notifyViewAppeared(int sessionId, ViewNode.ViewStructureImpl node) {
        sendEvent(new ContentCaptureEvent(sessionId, 1).setViewNode(node.mNode));
    }

    public void notifyViewDisappeared(int sessionId, AutofillId id) {
        sendEvent(new ContentCaptureEvent(sessionId, 2).setAutofillId(id));
    }

    /* access modifiers changed from: package-private */
    public void notifyViewTextChanged(int sessionId, AutofillId id, CharSequence text) {
        sendEvent(new ContentCaptureEvent(sessionId, 3).setAutofillId(id).setText(text));
    }

    public void notifyViewTreeEvent(int sessionId, boolean started) {
        sendEvent(new ContentCaptureEvent(sessionId, started ? 4 : 5), true);
    }

    public void notifySessionLifecycle(boolean started) {
        sendEvent(new ContentCaptureEvent(this.mId, started ? 7 : 8), true);
    }

    /* access modifiers changed from: package-private */
    public void notifyContextUpdated(int sessionId, ContentCaptureContext context) {
        sendEvent(new ContentCaptureEvent(sessionId, 6).setClientContext(context));
    }

    /* access modifiers changed from: package-private */
    public void dump(String prefix, PrintWriter pw) {
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
                    pw.print(prefix3);
                    pw.print(i);
                    pw.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                    this.mEvents.get(i).dump(pw);
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
            this.mFlushHistory.reverseDump((FileDescriptor) null, pw, (String[]) null);
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
