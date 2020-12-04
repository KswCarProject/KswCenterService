package android.service.contentcapture;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.contentcapture.IContentCaptureService;
import android.service.contentcapture.IContentCaptureServiceCallback;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.StatsLog;
import android.view.contentcapture.ContentCaptureCondition;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ContentCaptureSessionId;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.IContentCaptureDirectManager;
import android.view.contentcapture.MainContentCaptureSession;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@SystemApi
public abstract class ContentCaptureService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.contentcapture.ContentCaptureService";
    public static final String SERVICE_META_DATA = "android.content_capture";
    private static final String TAG = ContentCaptureService.class.getSimpleName();
    private IContentCaptureServiceCallback mCallback;
    private long mCallerMismatchTimeout = 1000;
    private final IContentCaptureDirectManager mClientInterface = new IContentCaptureDirectManager.Stub() {
        public void sendEvents(ParceledListSlice events, int reason, ContentCaptureOptions options) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$2$nqaNcni5MOtmyGkMJfxu_qUHOk4.INSTANCE, ContentCaptureService.this, Integer.valueOf(Binder.getCallingUid()), events, Integer.valueOf(reason), options));
        }
    };
    /* access modifiers changed from: private */
    public Handler mHandler;
    private long mLastCallerMismatchLog;
    private final IContentCaptureService mServerInterface = new IContentCaptureService.Stub() {
        public void onConnected(IBinder callback, boolean verbose, boolean debug) {
            ContentCaptureHelper.sVerbose = verbose;
            ContentCaptureHelper.sDebug = debug;
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$iP7RXM_Va9lafd6bT9eXRx_D47Q.INSTANCE, ContentCaptureService.this, callback));
        }

        public void onDisconnected() {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$wPMOb7AM5rkHmuyl3SBSylaH1A.INSTANCE, ContentCaptureService.this));
        }

        public void onSessionStarted(ContentCaptureContext context, int sessionId, int uid, IResultReceiver clientReceiver, int initialState) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$PaMsQkJwdUJ1lCgOOaLG9Bm09t8.INSTANCE, ContentCaptureService.this, context, Integer.valueOf(sessionId), Integer.valueOf(uid), clientReceiver, Integer.valueOf(initialState)));
        }

        public void onActivitySnapshot(int sessionId, SnapshotData snapshotData) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$NhSHlL57JqxWNJ8QcsuGxEhxv1Y.INSTANCE, ContentCaptureService.this, Integer.valueOf(sessionId), snapshotData));
        }

        public void onSessionFinished(int sessionId) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$jkZQ77YuBlPDClOdklQb8tj8Kpw.INSTANCE, ContentCaptureService.this, Integer.valueOf(sessionId)));
        }

        public void onDataRemovalRequest(DataRemovalRequest request) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$sJuAS4AaQcXaSFkQpSEmVLBqyvw.INSTANCE, ContentCaptureService.this, request));
        }

        public void onActivityEvent(ActivityEvent event) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$ContentCaptureService$1$V1mxGgTDjVVHroIjJrHvYfUHCKE.INSTANCE, ContentCaptureService.this, event));
        }
    };
    private final SparseIntArray mSessionUids = new SparseIntArray();

    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), (Handler.Callback) null, true);
    }

    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mServerInterface.asBinder();
        }
        String str = TAG;
        Log.w(str, "Tried to bind to wrong intent (should be android.service.contentcapture.ContentCaptureService: " + intent);
        return null;
    }

    public final void setContentCaptureWhitelist(Set<String> packages, Set<ComponentName> activities) {
        IContentCaptureServiceCallback callback = this.mCallback;
        if (callback == null) {
            Log.w(TAG, "setContentCaptureWhitelist(): no server callback");
            return;
        }
        try {
            callback.setContentCaptureWhitelist(ContentCaptureHelper.toList(packages), ContentCaptureHelper.toList(activities));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void setContentCaptureConditions(String packageName, Set<ContentCaptureCondition> conditions) {
        IContentCaptureServiceCallback callback = this.mCallback;
        if (callback == null) {
            Log.w(TAG, "setContentCaptureConditions(): no server callback");
            return;
        }
        try {
            callback.setContentCaptureConditions(packageName, ContentCaptureHelper.toList(conditions));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onConnected() {
        String str = TAG;
        Slog.i(str, "bound to " + getClass().getName());
    }

    public void onCreateContentCaptureSession(ContentCaptureContext context, ContentCaptureSessionId sessionId) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "onCreateContentCaptureSession(id=" + sessionId + ", ctx=" + context + ")");
        }
    }

    public void onContentCaptureEvent(ContentCaptureSessionId sessionId, ContentCaptureEvent event) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "onContentCaptureEventsRequest(id=" + sessionId + ")");
        }
    }

    public void onDataRemovalRequest(DataRemovalRequest request) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDataRemovalRequest()");
        }
    }

    public void onActivitySnapshot(ContentCaptureSessionId sessionId, SnapshotData snapshotData) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "onActivitySnapshot(id=" + sessionId + ")");
        }
    }

    public void onActivityEvent(ActivityEvent event) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "onActivityEvent(): " + event);
        }
    }

    public void onDestroyContentCaptureSession(ContentCaptureSessionId sessionId) {
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            Log.v(str, "onDestroyContentCaptureSession(id=" + sessionId + ")");
        }
    }

    public final void disableSelf() {
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "disableSelf()");
        }
        IContentCaptureServiceCallback callback = this.mCallback;
        if (callback == null) {
            Log.w(TAG, "disableSelf(): no server callback");
            return;
        }
        try {
            callback.disableSelf();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onDisconnected() {
        String str = TAG;
        Slog.i(str, "unbinding from " + getClass().getName());
    }

    /* access modifiers changed from: protected */
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("Debug: ");
        pw.print(ContentCaptureHelper.sDebug);
        pw.print(" Verbose: ");
        pw.println(ContentCaptureHelper.sVerbose);
        int size = this.mSessionUids.size();
        pw.print("Number sessions: ");
        pw.println(size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                pw.print("  ");
                pw.print(this.mSessionUids.keyAt(i));
                pw.print(": uid=");
                pw.println(this.mSessionUids.valueAt(i));
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleOnConnected(IBinder callback) {
        this.mCallback = IContentCaptureServiceCallback.Stub.asInterface(callback);
        onConnected();
    }

    /* access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
        this.mCallback = null;
    }

    /* access modifiers changed from: private */
    public void handleOnCreateSession(ContentCaptureContext context, int sessionId, int uid, IResultReceiver clientReceiver, int initialState) {
        int stateFlags;
        this.mSessionUids.put(sessionId, uid);
        onCreateContentCaptureSession(context, new ContentCaptureSessionId(sessionId));
        int clientFlags = context.getFlags();
        int stateFlags2 = 0;
        if ((clientFlags & 2) != 0) {
            stateFlags2 = 0 | 32;
        }
        if ((clientFlags & 1) != 0) {
            stateFlags2 |= 64;
        }
        if (stateFlags2 == 0) {
            stateFlags = initialState;
        } else {
            stateFlags = stateFlags2 | 4;
        }
        setClientState(clientReceiver, stateFlags, this.mClientInterface.asBinder());
    }

    /* access modifiers changed from: private */
    public void handleSendEvents(int uid, ParceledListSlice<ContentCaptureEvent> parceledEvents, int reason, ContentCaptureOptions options) {
        int i = uid;
        List<ContentCaptureEvent> events = parceledEvents.getList();
        if (events.isEmpty()) {
            Log.w(TAG, "handleSendEvents() received empty list of events");
            return;
        }
        FlushMetrics metrics = new FlushMetrics();
        int i2 = 0;
        ComponentName activityComponent = null;
        int lastSessionId = 0;
        ContentCaptureSessionId sessionId = null;
        while (true) {
            int i3 = i2;
            if (i3 < events.size()) {
                ContentCaptureEvent event = events.get(i3);
                if (handleIsRightCallerFor(event, i)) {
                    int sessionIdInt = event.getSessionId();
                    if (sessionIdInt != lastSessionId) {
                        sessionId = new ContentCaptureSessionId(sessionIdInt);
                        int lastSessionId2 = sessionIdInt;
                        if (i3 != 0) {
                            writeFlushMetrics(lastSessionId2, activityComponent, metrics, options, reason);
                            metrics.reset();
                        }
                        lastSessionId = lastSessionId2;
                    }
                    ContentCaptureContext clientContext = event.getContentCaptureContext();
                    if (activityComponent == null && clientContext != null) {
                        activityComponent = clientContext.getActivityComponent();
                    }
                    switch (event.getType()) {
                        case -2:
                            this.mSessionUids.delete(sessionIdInt);
                            onDestroyContentCaptureSession(sessionId);
                            metrics.sessionFinished++;
                            break;
                        case -1:
                            clientContext.setParentSessionId(event.getParentSessionId());
                            this.mSessionUids.put(sessionIdInt, i);
                            onCreateContentCaptureSession(clientContext, sessionId);
                            metrics.sessionStarted++;
                            break;
                        case 1:
                            onContentCaptureEvent(sessionId, event);
                            metrics.viewAppearedCount++;
                            break;
                        case 2:
                            onContentCaptureEvent(sessionId, event);
                            metrics.viewDisappearedCount++;
                            break;
                        case 3:
                            onContentCaptureEvent(sessionId, event);
                            metrics.viewTextChangedCount++;
                            break;
                        default:
                            onContentCaptureEvent(sessionId, event);
                            break;
                    }
                }
                i2 = i3 + 1;
            } else {
                writeFlushMetrics(lastSessionId, activityComponent, metrics, options, reason);
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleOnActivitySnapshot(int sessionId, SnapshotData snapshotData) {
        onActivitySnapshot(new ContentCaptureSessionId(sessionId), snapshotData);
    }

    /* access modifiers changed from: private */
    public void handleFinishSession(int sessionId) {
        this.mSessionUids.delete(sessionId);
        onDestroyContentCaptureSession(new ContentCaptureSessionId(sessionId));
    }

    /* access modifiers changed from: private */
    public void handleOnDataRemovalRequest(DataRemovalRequest request) {
        onDataRemovalRequest(request);
    }

    /* access modifiers changed from: private */
    public void handleOnActivityEvent(ActivityEvent event) {
        onActivityEvent(event);
    }

    private boolean handleIsRightCallerFor(ContentCaptureEvent event, int uid) {
        int sessionId;
        switch (event.getType()) {
            case -2:
            case -1:
                sessionId = event.getParentSessionId();
                break;
            default:
                sessionId = event.getSessionId();
                break;
        }
        if (this.mSessionUids.indexOfKey(sessionId) < 0) {
            if (ContentCaptureHelper.sVerbose) {
                String str = TAG;
                Log.v(str, "handleIsRightCallerFor(" + event + "): no session for " + sessionId + ": " + this.mSessionUids);
            }
            return false;
        }
        int rightUid = this.mSessionUids.get(sessionId);
        if (rightUid == uid) {
            return true;
        }
        String str2 = TAG;
        Log.e(str2, "invalid call from UID " + uid + ": session " + sessionId + " belongs to " + rightUid);
        long now = System.currentTimeMillis();
        if (now - this.mLastCallerMismatchLog > this.mCallerMismatchTimeout) {
            StatsLog.write(206, getPackageManager().getNameForUid(rightUid), getPackageManager().getNameForUid(uid));
            this.mLastCallerMismatchLog = now;
        }
        return false;
    }

    public static void setClientState(IResultReceiver clientReceiver, int sessionState, IBinder binder) {
        Bundle extras;
        if (binder != null) {
            try {
                extras = new Bundle();
                extras.putBinder(MainContentCaptureSession.EXTRA_BINDER, binder);
            } catch (RemoteException e) {
                String str = TAG;
                Slog.w(str, "Error async reporting result to client: " + e);
                return;
            }
        } else {
            extras = null;
        }
        clientReceiver.send(sessionState, extras);
    }

    private void writeFlushMetrics(int sessionId, ComponentName app, FlushMetrics flushMetrics, ContentCaptureOptions options, int flushReason) {
        if (this.mCallback == null) {
            Log.w(TAG, "writeSessionFlush(): no server callback");
            return;
        }
        try {
            this.mCallback.writeSessionFlush(sessionId, app, flushMetrics, options, flushReason);
        } catch (RemoteException e) {
            String str = TAG;
            Log.e(str, "failed to write flush metrics: " + e);
        }
    }
}
