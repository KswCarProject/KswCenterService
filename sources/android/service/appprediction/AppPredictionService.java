package android.service.appprediction;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.app.prediction.IPredictionCallback;
import android.content.Intent;
import android.content.p002pm.ParceledListSlice;
import android.p007os.CancellationSignal;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.RemoteException;
import android.service.appprediction.AppPredictionService;
import android.service.appprediction.IPredictionService;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class AppPredictionService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.appprediction.AppPredictionService";
    private static final String TAG = "AppPredictionService";
    private Handler mHandler;
    private final ArrayMap<AppPredictionSessionId, ArrayList<CallbackWrapper>> mSessionCallbacks = new ArrayMap<>();
    private final IPredictionService mInterface = new BinderC17231();

    public abstract void onAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent);

    public abstract void onLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, List<AppTargetId> list);

    public abstract void onRequestPredictionUpdate(AppPredictionSessionId appPredictionSessionId);

    public abstract void onSortAppTargets(AppPredictionSessionId appPredictionSessionId, List<AppTarget> list, CancellationSignal cancellationSignal, Consumer<List<AppTarget>> consumer);

    /* renamed from: android.service.appprediction.AppPredictionService$1 */
    /* loaded from: classes3.dex */
    class BinderC17231 extends IPredictionService.Stub {
        BinderC17231() {
        }

        @Override // android.service.appprediction.IPredictionService
        public void onCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$1$dlPwi16n_6u5po2eN8wlW4I1bRw
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AppPredictionService) obj).doCreatePredictionSession((AppPredictionContext) obj2, (AppPredictionSessionId) obj3);
                }
            }, AppPredictionService.this, context, sessionId));
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyAppTargetEvent(AppPredictionSessionId sessionId, AppTargetEvent event) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.appprediction.-$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AppPredictionService) obj).onAppTargetEvent((AppPredictionSessionId) obj2, (AppTargetEvent) obj3);
                }
            }, AppPredictionService.this, sessionId, event));
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyLaunchLocationShown(AppPredictionSessionId sessionId, String launchLocation, ParceledListSlice targetIds) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.appprediction.-$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((AppPredictionService) obj).onLaunchLocationShown((AppPredictionSessionId) obj2, (String) obj3, (List) obj4);
                }
            }, AppPredictionService.this, sessionId, launchLocation, targetIds.getList()));
        }

        @Override // android.service.appprediction.IPredictionService
        public void sortAppTargets(AppPredictionSessionId sessionId, ParceledListSlice targets, IPredictionCallback callback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.service.appprediction.-$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((AppPredictionService) obj).onSortAppTargets((AppPredictionSessionId) obj2, (List) obj3, (CancellationSignal) obj4, (AppPredictionService.CallbackWrapper) obj5);
                }
            }, AppPredictionService.this, sessionId, targets.getList(), null, new CallbackWrapper(callback, null)));
        }

        @Override // android.service.appprediction.IPredictionService
        public void registerPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$1$CDfn7BNaxDP2sak-07muIxqD0XM
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AppPredictionService) obj).doRegisterPredictionUpdates((AppPredictionSessionId) obj2, (IPredictionCallback) obj3);
                }
            }, AppPredictionService.this, sessionId, callback));
        }

        @Override // android.service.appprediction.IPredictionService
        public void unregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AppPredictionService) obj).doUnregisterPredictionUpdates((AppPredictionSessionId) obj2, (IPredictionCallback) obj3);
                }
            }, AppPredictionService.this, sessionId, callback));
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestPredictionUpdate(AppPredictionSessionId sessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$1$oaGU8LD9Stlihi_KoW_pb0jZjQk
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((AppPredictionService) obj).doRequestPredictionUpdate((AppPredictionSessionId) obj2);
                }
            }, AppPredictionService.this, sessionId));
        }

        @Override // android.service.appprediction.IPredictionService
        public void onDestroyPredictionSession(AppPredictionSessionId sessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$1$oZsrXgV2j_8Zo7GiDdpYvbTz4h8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((AppPredictionService) obj).doDestroyPredictionSession((AppPredictionSessionId) obj2);
                }
            }, AppPredictionService.this, sessionId));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.m64w(TAG, "Tried to bind to wrong intent (should be android.service.appprediction.AppPredictionService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) {
        this.mSessionCallbacks.put(sessionId, new ArrayList<>());
        onCreatePredictionSession(context, sessionId);
    }

    public void onCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
        final ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.m56e(TAG, "Failed to register for updates for unknown session: " + sessionId);
            return;
        }
        CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
        if (wrapper == null) {
            callbacks.add(new CallbackWrapper(callback, new Consumer() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$BU3RVDaz_RDf_0tC58L6QbapMAs
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    r0.mHandler.post(new Runnable() { // from class: android.service.appprediction.-$$Lambda$AppPredictionService$QdiGSCeMaWGP0DGJNn4uhqgT9ZA
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppPredictionService.this.removeCallbackWrapper(r2, r3);
                        }
                    });
                }
            }));
            if (callbacks.size() == 1) {
                onStartPredictionUpdates();
            }
        }
    }

    public void onStartPredictionUpdates() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.m56e(TAG, "Failed to unregister for updates for unknown session: " + sessionId);
            return;
        }
        CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
        if (wrapper != null) {
            removeCallbackWrapper(callbacks, wrapper);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCallbackWrapper(ArrayList<CallbackWrapper> callbacks, CallbackWrapper wrapper) {
        if (callbacks == null) {
            return;
        }
        callbacks.remove(wrapper);
        if (callbacks.isEmpty()) {
            onStopPredictionUpdates();
        }
    }

    public void onStopPredictionUpdates() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRequestPredictionUpdate(AppPredictionSessionId sessionId) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks != null && !callbacks.isEmpty()) {
            onRequestPredictionUpdate(sessionId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroyPredictionSession(AppPredictionSessionId sessionId) {
        this.mSessionCallbacks.remove(sessionId);
        onDestroyPredictionSession(sessionId);
    }

    public void onDestroyPredictionSession(AppPredictionSessionId sessionId) {
    }

    public final void updatePredictions(AppPredictionSessionId sessionId, List<AppTarget> targets) {
        List<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks != null) {
            for (CallbackWrapper callback : callbacks) {
                callback.accept(targets);
            }
        }
    }

    private CallbackWrapper findCallbackWrapper(ArrayList<CallbackWrapper> callbacks, IPredictionCallback callback) {
        for (int i = callbacks.size() - 1; i >= 0; i--) {
            if (callbacks.get(i).isCallback(callback)) {
                return callbacks.get(i);
            }
        }
        return null;
    }

    /* loaded from: classes3.dex */
    private static final class CallbackWrapper implements Consumer<List<AppTarget>>, IBinder.DeathRecipient {
        private IPredictionCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(IPredictionCallback callback, Consumer<CallbackWrapper> onBinderDied) {
            this.mCallback = callback;
            this.mOnBinderDied = onBinderDied;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.m56e(AppPredictionService.TAG, "Failed to link to death: " + e);
            }
        }

        public boolean isCallback(IPredictionCallback callback) {
            if (this.mCallback == null) {
                Slog.m56e(AppPredictionService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return this.mCallback.equals(callback);
        }

        @Override // java.util.function.Consumer
        public void accept(List<AppTarget> ts) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new ParceledListSlice(ts));
                }
            } catch (RemoteException e) {
                Slog.m56e(AppPredictionService.TAG, "Error sending result:" + e);
            }
        }

        @Override // android.p007os.IBinder.DeathRecipient
        public void binderDied() {
            this.mCallback = null;
            if (this.mOnBinderDied != null) {
                this.mOnBinderDied.accept(this);
            }
        }
    }
}
