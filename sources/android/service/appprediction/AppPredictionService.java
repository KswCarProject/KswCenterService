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
import android.content.pm.ParceledListSlice;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.appprediction.AppPredictionService;
import android.service.appprediction.IPredictionService;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SystemApi
public abstract class AppPredictionService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.appprediction.AppPredictionService";
    private static final String TAG = "AppPredictionService";
    /* access modifiers changed from: private */
    public Handler mHandler;
    private final IPredictionService mInterface = new IPredictionService.Stub() {
        public void onCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AppPredictionService$1$dlPwi16n_6u5po2eN8wlW4I1bRw.INSTANCE, AppPredictionService.this, context, sessionId));
        }

        public void notifyAppTargetEvent(AppPredictionSessionId sessionId, AppTargetEvent event) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0.INSTANCE, AppPredictionService.this, sessionId, event));
        }

        public void notifyLaunchLocationShown(AppPredictionSessionId sessionId, String launchLocation, ParceledListSlice targetIds) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y.INSTANCE, AppPredictionService.this, sessionId, launchLocation, targetIds.getList()));
        }

        public void sortAppTargets(AppPredictionSessionId sessionId, ParceledListSlice targets, IPredictionCallback callback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk.INSTANCE, AppPredictionService.this, sessionId, targets.getList(), null, new CallbackWrapper(callback, (Consumer<CallbackWrapper>) null)));
        }

        public void registerPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AppPredictionService$1$CDfn7BNaxDP2sak07muIxqD0XM.INSTANCE, AppPredictionService.this, sessionId, callback));
        }

        public void unregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik.INSTANCE, AppPredictionService.this, sessionId, callback));
        }

        public void requestPredictionUpdate(AppPredictionSessionId sessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AppPredictionService$1$oaGU8LD9Stlihi_KoW_pb0jZjQk.INSTANCE, AppPredictionService.this, sessionId));
        }

        public void onDestroyPredictionSession(AppPredictionSessionId sessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AppPredictionService$1$oZsrXgV2j_8Zo7GiDdpYvbTz4h8.INSTANCE, AppPredictionService.this, sessionId));
        }
    };
    private final ArrayMap<AppPredictionSessionId, ArrayList<CallbackWrapper>> mSessionCallbacks = new ArrayMap<>();

    public abstract void onAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent);

    public abstract void onLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, List<AppTargetId> list);

    public abstract void onRequestPredictionUpdate(AppPredictionSessionId appPredictionSessionId);

    public abstract void onSortAppTargets(AppPredictionSessionId appPredictionSessionId, List<AppTarget> list, CancellationSignal cancellationSignal, Consumer<List<AppTarget>> consumer);

    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), (Handler.Callback) null, true);
    }

    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.appprediction.AppPredictionService: " + intent);
        return null;
    }

    /* access modifiers changed from: private */
    public void doCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) {
        this.mSessionCallbacks.put(sessionId, new ArrayList());
        onCreatePredictionSession(context, sessionId);
    }

    public void onCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) {
    }

    /* access modifiers changed from: private */
    public void doRegisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.e(TAG, "Failed to register for updates for unknown session: " + sessionId);
        } else if (findCallbackWrapper(callbacks, callback) == null) {
            callbacks.add(new CallbackWrapper(callback, new Consumer(callbacks) {
                private final /* synthetic */ ArrayList f$1;

                {
                    this.f$1 = r2;
                }

                public final void accept(Object obj) {
                    AppPredictionService.this.mHandler.post(new Runnable(this.f$1, (AppPredictionService.CallbackWrapper) obj) {
                        private final /* synthetic */ ArrayList f$1;
                        private final /* synthetic */ AppPredictionService.CallbackWrapper f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            AppPredictionService.this.removeCallbackWrapper(this.f$1, this.f$2);
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

    /* access modifiers changed from: private */
    public void doUnregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.e(TAG, "Failed to unregister for updates for unknown session: " + sessionId);
            return;
        }
        CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
        if (wrapper != null) {
            removeCallbackWrapper(callbacks, wrapper);
        }
    }

    /* access modifiers changed from: private */
    public void removeCallbackWrapper(ArrayList<CallbackWrapper> callbacks, CallbackWrapper wrapper) {
        if (callbacks != null) {
            callbacks.remove(wrapper);
            if (callbacks.isEmpty()) {
                onStopPredictionUpdates();
            }
        }
    }

    public void onStopPredictionUpdates() {
    }

    /* access modifiers changed from: private */
    public void doRequestPredictionUpdate(AppPredictionSessionId sessionId) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks != null && !callbacks.isEmpty()) {
            onRequestPredictionUpdate(sessionId);
        }
    }

    /* access modifiers changed from: private */
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

    private static final class CallbackWrapper implements Consumer<List<AppTarget>>, IBinder.DeathRecipient {
        private IPredictionCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(IPredictionCallback callback, Consumer<CallbackWrapper> onBinderDied) {
            this.mCallback = callback;
            this.mOnBinderDied = onBinderDied;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.e(AppPredictionService.TAG, "Failed to link to death: " + e);
            }
        }

        public boolean isCallback(IPredictionCallback callback) {
            if (this.mCallback != null) {
                return this.mCallback.equals(callback);
            }
            Slog.e(AppPredictionService.TAG, "Callback is null, likely the binder has died.");
            return false;
        }

        public void accept(List<AppTarget> ts) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new ParceledListSlice(ts));
                }
            } catch (RemoteException e) {
                Slog.e(AppPredictionService.TAG, "Error sending result:" + e);
            }
        }

        public void binderDied() {
            this.mCallback = null;
            if (this.mOnBinderDied != null) {
                this.mOnBinderDied.accept(this);
            }
        }
    }
}
