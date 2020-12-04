package android.net;

import android.net.SocketKeepalive;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import java.net.InetAddress;
import java.util.concurrent.Executor;

public final class NattSocketKeepalive extends SocketKeepalive {
    public static final int NATT_PORT = 4500;
    private final InetAddress mDestination;
    private final int mResourceId;
    private final InetAddress mSource;

    NattSocketKeepalive(IConnectivityManager service, Network network, ParcelFileDescriptor pfd, int resourceId, InetAddress source, InetAddress destination, Executor executor, SocketKeepalive.Callback callback) {
        super(service, network, pfd, executor, callback);
        this.mSource = source;
        this.mDestination = destination;
        this.mResourceId = resourceId;
    }

    /* access modifiers changed from: package-private */
    public void startImpl(int intervalSec) {
        this.mExecutor.execute(new Runnable(intervalSec) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                NattSocketKeepalive.lambda$startImpl$0(NattSocketKeepalive.this, this.f$1);
            }
        });
    }

    public static /* synthetic */ void lambda$startImpl$0(NattSocketKeepalive nattSocketKeepalive, int intervalSec) {
        try {
            nattSocketKeepalive.mService.startNattKeepaliveWithFd(nattSocketKeepalive.mNetwork, nattSocketKeepalive.mPfd.getFileDescriptor(), nattSocketKeepalive.mResourceId, intervalSec, nattSocketKeepalive.mCallback, nattSocketKeepalive.mSource.getHostAddress(), nattSocketKeepalive.mDestination.getHostAddress());
        } catch (RemoteException e) {
            Log.e("SocketKeepalive", "Error starting socket keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* access modifiers changed from: package-private */
    public void stopImpl() {
        this.mExecutor.execute(new Runnable() {
            public final void run() {
                NattSocketKeepalive.lambda$stopImpl$1(NattSocketKeepalive.this);
            }
        });
    }

    public static /* synthetic */ void lambda$stopImpl$1(NattSocketKeepalive nattSocketKeepalive) {
        try {
            if (nattSocketKeepalive.mSlot != null) {
                nattSocketKeepalive.mService.stopKeepalive(nattSocketKeepalive.mNetwork, nattSocketKeepalive.mSlot.intValue());
            }
        } catch (RemoteException e) {
            Log.e("SocketKeepalive", "Error stopping socket keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }
}
