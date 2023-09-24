package android.net.lowpan;

import android.net.IpPrefix;
import android.net.lowpan.ILowpanInterfaceListener;
import android.net.lowpan.LowpanCommissioningSession;
import android.p007os.DeadObjectException;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public class LowpanCommissioningSession {
    private final LowpanBeaconInfo mBeaconInfo;
    private final ILowpanInterface mBinder;
    private Handler mHandler;
    private final Looper mLooper;
    private final ILowpanInterfaceListener mInternalCallback = new InternalCallback();
    private Callback mCallback = null;
    private volatile boolean mIsClosed = false;

    /* loaded from: classes3.dex */
    public static abstract class Callback {
        public void onReceiveFromCommissioner(byte[] packet) {
        }

        public void onClosed() {
        }
    }

    /* loaded from: classes3.dex */
    private class InternalCallback extends ILowpanInterfaceListener.Stub {
        private InternalCallback() {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onStateChanged(String value) {
            if (!LowpanCommissioningSession.this.mIsClosed) {
                char c = '\uffff';
                int hashCode = value.hashCode();
                if (hashCode != -1548612125) {
                    if (hashCode == 97204770 && value.equals("fault")) {
                        c = 1;
                    }
                } else if (value.equals("offline")) {
                    c = 0;
                }
                switch (c) {
                    case 0:
                    case 1:
                        synchronized (LowpanCommissioningSession.this) {
                            LowpanCommissioningSession.this.lockedCleanup();
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onReceiveFromCommissioner(final byte[] packet) {
            LowpanCommissioningSession.this.mHandler.post(new Runnable() { // from class: android.net.lowpan.-$$Lambda$LowpanCommissioningSession$InternalCallback$TrrmDykqIWeXNdgrXO7t2-rqCTo
                @Override // java.lang.Runnable
                public final void run() {
                    LowpanCommissioningSession.InternalCallback.lambda$onReceiveFromCommissioner$0(LowpanCommissioningSession.InternalCallback.this, packet);
                }
            });
        }

        public static /* synthetic */ void lambda$onReceiveFromCommissioner$0(InternalCallback internalCallback, byte[] packet) {
            synchronized (LowpanCommissioningSession.this) {
                if (!LowpanCommissioningSession.this.mIsClosed && LowpanCommissioningSession.this.mCallback != null) {
                    LowpanCommissioningSession.this.mCallback.onReceiveFromCommissioner(packet);
                }
            }
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onEnabledChanged(boolean value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onConnectedChanged(boolean value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onUpChanged(boolean value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onRoleChanged(String value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onLowpanIdentityChanged(LowpanIdentity value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onLinkNetworkAdded(IpPrefix value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onLinkNetworkRemoved(IpPrefix value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onLinkAddressAdded(String value) {
        }

        @Override // android.net.lowpan.ILowpanInterfaceListener
        public void onLinkAddressRemoved(String value) {
        }
    }

    LowpanCommissioningSession(ILowpanInterface binder, LowpanBeaconInfo beaconInfo, Looper looper) {
        this.mBinder = binder;
        this.mBeaconInfo = beaconInfo;
        this.mLooper = looper;
        if (this.mLooper != null) {
            this.mHandler = new Handler(this.mLooper);
        } else {
            this.mHandler = new Handler();
        }
        try {
            this.mBinder.addListener(this.mInternalCallback);
        } catch (RemoteException x) {
            throw x.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockedCleanup() {
        if (!this.mIsClosed) {
            try {
                this.mBinder.removeListener(this.mInternalCallback);
            } catch (DeadObjectException e) {
            } catch (RemoteException x) {
                throw x.rethrowAsRuntimeException();
            }
            if (this.mCallback != null) {
                this.mHandler.post(new Runnable() { // from class: android.net.lowpan.-$$Lambda$LowpanCommissioningSession$jqpl-iUq-e7YuWqkG33P8PNe7Ag
                    @Override // java.lang.Runnable
                    public final void run() {
                        LowpanCommissioningSession.this.mCallback.onClosed();
                    }
                });
            }
        }
        this.mCallback = null;
        this.mIsClosed = true;
    }

    public LowpanBeaconInfo getBeaconInfo() {
        return this.mBeaconInfo;
    }

    public void sendToCommissioner(byte[] packet) {
        if (!this.mIsClosed) {
            try {
                this.mBinder.sendToCommissioner(packet);
            } catch (DeadObjectException e) {
            } catch (RemoteException x) {
                throw x.rethrowAsRuntimeException();
            }
        }
    }

    public synchronized void setCallback(Callback cb, Handler handler) {
        if (!this.mIsClosed) {
            if (handler != null) {
                this.mHandler = handler;
            } else if (this.mLooper != null) {
                this.mHandler = new Handler(this.mLooper);
            } else {
                this.mHandler = new Handler();
            }
            this.mCallback = cb;
        }
    }

    public synchronized void close() {
        if (!this.mIsClosed) {
            try {
                this.mBinder.closeCommissioningSession();
                lockedCleanup();
            } catch (DeadObjectException e) {
            } catch (RemoteException x) {
                throw x.rethrowAsRuntimeException();
            }
        }
    }
}
