package android.inputmethodservice;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.MultiClientInputMethodServiceDelegate;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.util.Log;
import android.view.InputChannel;
import android.view.KeyEvent;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IMultiClientInputMethod;
import com.android.internal.inputmethod.IMultiClientInputMethodPrivilegedOperations;
import com.android.internal.inputmethod.MultiClientInputMethodPrivilegedOperations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
final class MultiClientInputMethodServiceDelegateImpl {
    private static final String TAG = "MultiClientInputMethodServiceDelegateImpl";
    private final Context mContext;
    private final MultiClientInputMethodServiceDelegate.ServiceCallback mServiceCallback;
    private final Object mLock = new Object();
    private final MultiClientInputMethodPrivilegedOperations mPrivOps = new MultiClientInputMethodPrivilegedOperations();
    @GuardedBy({"mLock"})
    private int mInitializationPhase = 1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    private @interface InitializationPhase {
        public static final int INITIALIZE_CALLED = 3;
        public static final int INSTANTIATED = 1;
        public static final int ON_BIND_CALLED = 2;
        public static final int ON_DESTROY_CALLED = 5;
        public static final int ON_UNBIND_CALLED = 4;
    }

    MultiClientInputMethodServiceDelegateImpl(Context context, MultiClientInputMethodServiceDelegate.ServiceCallback serviceCallback) {
        this.mContext = context;
        this.mServiceCallback = serviceCallback;
    }

    void onDestroy() {
        synchronized (this.mLock) {
            int i = this.mInitializationPhase;
            if (i == 1 || i == 4) {
                this.mInitializationPhase = 5;
            } else {
                Log.m70e(TAG, "unexpected state=" + this.mInitializationPhase);
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class ServiceImpl extends IMultiClientInputMethod.Stub {
        private final WeakReference<MultiClientInputMethodServiceDelegateImpl> mImpl;

        ServiceImpl(MultiClientInputMethodServiceDelegateImpl service) {
            this.mImpl = new WeakReference<>(service);
        }

        @Override // com.android.internal.inputmethod.IMultiClientInputMethod
        public void initialize(IMultiClientInputMethodPrivilegedOperations privOps) {
            MultiClientInputMethodServiceDelegateImpl service = this.mImpl.get();
            if (service != null) {
                synchronized (service.mLock) {
                    if (service.mInitializationPhase != 2) {
                        Log.m70e(MultiClientInputMethodServiceDelegateImpl.TAG, "unexpected state=" + service.mInitializationPhase);
                    } else {
                        service.mPrivOps.set(privOps);
                        service.mInitializationPhase = 3;
                        service.mServiceCallback.initialized();
                    }
                }
            }
        }

        @Override // com.android.internal.inputmethod.IMultiClientInputMethod
        public void addClient(int clientId, int uid, int pid, int selfReportedDisplayId) {
            MultiClientInputMethodServiceDelegateImpl service = this.mImpl.get();
            if (service != null) {
                service.mServiceCallback.addClient(clientId, uid, pid, selfReportedDisplayId);
            }
        }

        @Override // com.android.internal.inputmethod.IMultiClientInputMethod
        public void removeClient(int clientId) {
            MultiClientInputMethodServiceDelegateImpl service = this.mImpl.get();
            if (service != null) {
                service.mServiceCallback.removeClient(clientId);
            }
        }
    }

    IBinder onBind(Intent intent) {
        synchronized (this.mLock) {
            if (this.mInitializationPhase == 1) {
                this.mInitializationPhase = 2;
                return new ServiceImpl(this);
            }
            Log.m70e(TAG, "unexpected state=" + this.mInitializationPhase);
            return null;
        }
    }

    boolean onUnbind(Intent intent) {
        synchronized (this.mLock) {
            switch (this.mInitializationPhase) {
                case 2:
                case 3:
                    this.mInitializationPhase = 4;
                    this.mPrivOps.dispose();
                    break;
                default:
                    Log.m70e(TAG, "unexpected state=" + this.mInitializationPhase);
                    break;
            }
        }
        return false;
    }

    IBinder createInputMethodWindowToken(int displayId) {
        return this.mPrivOps.createInputMethodWindowToken(displayId);
    }

    void acceptClient(int clientId, MultiClientInputMethodServiceDelegate.ClientCallback clientCallback, KeyEvent.DispatcherState dispatcherState, Looper looper) {
        InputChannel[] channels = InputChannel.openInputChannelPair("MSIMS-session");
        InputChannel writeChannel = channels[0];
        InputChannel readChannel = channels[1];
        try {
            MultiClientInputMethodClientCallbackAdaptor callbackAdaptor = new MultiClientInputMethodClientCallbackAdaptor(clientCallback, looper, dispatcherState, readChannel);
            this.mPrivOps.acceptClient(clientId, callbackAdaptor.createIInputMethodSession(), callbackAdaptor.createIMultiClientInputMethodSession(), writeChannel);
        } finally {
            writeChannel.dispose();
        }
    }

    void reportImeWindowTarget(int clientId, int targetWindowHandle, IBinder imeWindowToken) {
        this.mPrivOps.reportImeWindowTarget(clientId, targetWindowHandle, imeWindowToken);
    }

    boolean isUidAllowedOnDisplay(int displayId, int uid) {
        return this.mPrivOps.isUidAllowedOnDisplay(displayId, uid);
    }

    void setActive(int clientId, boolean active) {
        this.mPrivOps.setActive(clientId, active);
    }
}
