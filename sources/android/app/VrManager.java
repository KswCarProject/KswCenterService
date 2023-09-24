package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.VrManager;
import android.content.ComponentName;
import android.p007os.RemoteException;
import android.service.p010vr.IPersistentVrStateCallbacks;
import android.service.p010vr.IVrManager;
import android.service.p010vr.IVrStateCallbacks;
import android.util.ArrayMap;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public class VrManager {
    private Map<VrStateCallback, CallbackEntry> mCallbackMap = new ArrayMap();
    @UnsupportedAppUsage
    private final IVrManager mService;

    /* loaded from: classes.dex */
    private static class CallbackEntry {
        final VrStateCallback mCallback;
        final Executor mExecutor;
        final IVrStateCallbacks mStateCallback = new BinderC03241();
        final IPersistentVrStateCallbacks mPersistentStateCallback = new BinderC03252();

        /* renamed from: android.app.VrManager$CallbackEntry$1 */
        /* loaded from: classes.dex */
        class BinderC03241 extends IVrStateCallbacks.Stub {
            BinderC03241() {
            }

            @Override // android.service.p010vr.IVrStateCallbacks
            public void onVrStateChanged(final boolean enabled) {
                CallbackEntry.this.mExecutor.execute(new Runnable() { // from class: android.app.-$$Lambda$VrManager$CallbackEntry$1$rgUBVVG1QhelpvAp8W3UQHDHJdU
                    @Override // java.lang.Runnable
                    public final void run() {
                        VrManager.CallbackEntry.this.mCallback.onVrStateChanged(enabled);
                    }
                });
            }
        }

        /* renamed from: android.app.VrManager$CallbackEntry$2 */
        /* loaded from: classes.dex */
        class BinderC03252 extends IPersistentVrStateCallbacks.Stub {
            BinderC03252() {
            }

            @Override // android.service.p010vr.IPersistentVrStateCallbacks
            public void onPersistentVrStateChanged(final boolean enabled) {
                CallbackEntry.this.mExecutor.execute(new Runnable() { // from class: android.app.-$$Lambda$VrManager$CallbackEntry$2$KvHLIXm3-7igcOqTEl46YdjhHMk
                    @Override // java.lang.Runnable
                    public final void run() {
                        VrManager.CallbackEntry.this.mCallback.onPersistentVrStateChanged(enabled);
                    }
                });
            }
        }

        CallbackEntry(VrStateCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }
    }

    public VrManager(IVrManager service) {
        this.mService = service;
    }

    public void registerVrStateCallback(Executor executor, VrStateCallback callback) {
        if (callback == null || this.mCallbackMap.containsKey(callback)) {
            return;
        }
        CallbackEntry entry = new CallbackEntry(callback, executor);
        this.mCallbackMap.put(callback, entry);
        try {
            this.mService.registerListener(entry.mStateCallback);
            this.mService.registerPersistentVrStateListener(entry.mPersistentStateCallback);
        } catch (RemoteException e) {
            try {
                unregisterVrStateCallback(callback);
            } catch (Exception e2) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterVrStateCallback(VrStateCallback callback) {
        CallbackEntry entry = this.mCallbackMap.remove(callback);
        if (entry != null) {
            try {
                this.mService.unregisterListener(entry.mStateCallback);
            } catch (RemoteException e) {
            }
            try {
                this.mService.unregisterPersistentVrStateListener(entry.mPersistentStateCallback);
            } catch (RemoteException e2) {
            }
        }
    }

    public boolean isVrModeEnabled() {
        try {
            return this.mService.getVrModeState();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isPersistentVrModeEnabled() {
        try {
            return this.mService.getPersistentVrModeEnabled();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void setPersistentVrModeEnabled(boolean enabled) {
        try {
            this.mService.setPersistentVrModeEnabled(enabled);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProp) {
        try {
            this.mService.setVr2dDisplayProperties(vr2dDisplayProp);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setAndBindVrCompositor(ComponentName componentName) {
        try {
            this.mService.setAndBindCompositor(componentName == null ? null : componentName.flattenToString());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setStandbyEnabled(boolean standby) {
        try {
            this.mService.setStandbyEnabled(standby);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setVrInputMethod(ComponentName componentName) {
    }

    public int getVr2dDisplayId() {
        try {
            return this.mService.getVr2dDisplayId();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }
}
