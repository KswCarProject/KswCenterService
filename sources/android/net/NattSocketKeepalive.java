package android.net;

import android.net.SocketKeepalive;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.util.Log;
import java.net.InetAddress;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
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

    @Override // android.net.SocketKeepalive
    void startImpl(final int intervalSec) {
        this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$NattSocketKeepalive$7nsE-7bVdhw33oN4gmk8WVi-r9U
            @Override // java.lang.Runnable
            public final void run() {
                NattSocketKeepalive.lambda$startImpl$0(NattSocketKeepalive.this, intervalSec);
            }
        });
    }

    public static /* synthetic */ void lambda$startImpl$0(NattSocketKeepalive nattSocketKeepalive, int intervalSec) {
        try {
            nattSocketKeepalive.mService.startNattKeepaliveWithFd(nattSocketKeepalive.mNetwork, nattSocketKeepalive.mPfd.getFileDescriptor(), nattSocketKeepalive.mResourceId, intervalSec, nattSocketKeepalive.mCallback, nattSocketKeepalive.mSource.getHostAddress(), nattSocketKeepalive.mDestination.getHostAddress());
        } catch (RemoteException e) {
            Log.m69e("SocketKeepalive", "Error starting socket keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.net.SocketKeepalive
    void stopImpl() {
        this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$NattSocketKeepalive$60CcdfQ34rdXme76td_j4bbtPHU
            @Override // java.lang.Runnable
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
            Log.m69e("SocketKeepalive", "Error stopping socket keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }
}
