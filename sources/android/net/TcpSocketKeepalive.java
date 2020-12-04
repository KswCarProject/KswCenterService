package android.net;

import android.net.SocketKeepalive;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.Executor;

final class TcpSocketKeepalive extends SocketKeepalive {
    TcpSocketKeepalive(IConnectivityManager service, Network network, ParcelFileDescriptor pfd, Executor executor, SocketKeepalive.Callback callback) {
        super(service, network, pfd, executor, callback);
    }

    /* access modifiers changed from: package-private */
    public void startImpl(int intervalSec) {
        this.mExecutor.execute(new Runnable(intervalSec) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TcpSocketKeepalive.lambda$startImpl$0(TcpSocketKeepalive.this, this.f$1);
            }
        });
    }

    public static /* synthetic */ void lambda$startImpl$0(TcpSocketKeepalive tcpSocketKeepalive, int intervalSec) {
        try {
            tcpSocketKeepalive.mService.startTcpKeepalive(tcpSocketKeepalive.mNetwork, tcpSocketKeepalive.mPfd.getFileDescriptor(), intervalSec, tcpSocketKeepalive.mCallback);
        } catch (RemoteException e) {
            Log.e("SocketKeepalive", "Error starting packet keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* access modifiers changed from: package-private */
    public void stopImpl() {
        this.mExecutor.execute(new Runnable() {
            public final void run() {
                TcpSocketKeepalive.lambda$stopImpl$1(TcpSocketKeepalive.this);
            }
        });
    }

    public static /* synthetic */ void lambda$stopImpl$1(TcpSocketKeepalive tcpSocketKeepalive) {
        try {
            if (tcpSocketKeepalive.mSlot != null) {
                tcpSocketKeepalive.mService.stopKeepalive(tcpSocketKeepalive.mNetwork, tcpSocketKeepalive.mSlot.intValue());
            }
        } catch (RemoteException e) {
            Log.e("SocketKeepalive", "Error stopping packet keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }
}
