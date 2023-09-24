package android.net;

import android.net.SocketKeepalive;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.util.Log;
import java.io.FileDescriptor;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class TcpSocketKeepalive extends SocketKeepalive {
    TcpSocketKeepalive(IConnectivityManager service, Network network, ParcelFileDescriptor pfd, Executor executor, SocketKeepalive.Callback callback) {
        super(service, network, pfd, executor, callback);
    }

    @Override // android.net.SocketKeepalive
    void startImpl(final int intervalSec) {
        this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$TcpSocketKeepalive$E1MP45uBTM6jPfrxAAqXFllEmAA
            @Override // java.lang.Runnable
            public final void run() {
                TcpSocketKeepalive.lambda$startImpl$0(TcpSocketKeepalive.this, intervalSec);
            }
        });
    }

    public static /* synthetic */ void lambda$startImpl$0(TcpSocketKeepalive tcpSocketKeepalive, int intervalSec) {
        try {
            FileDescriptor fd = tcpSocketKeepalive.mPfd.getFileDescriptor();
            tcpSocketKeepalive.mService.startTcpKeepalive(tcpSocketKeepalive.mNetwork, fd, intervalSec, tcpSocketKeepalive.mCallback);
        } catch (RemoteException e) {
            Log.m69e("SocketKeepalive", "Error starting packet keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.net.SocketKeepalive
    void stopImpl() {
        this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$TcpSocketKeepalive$XcFd1FxqMQjF6WWgzFIVR4hV2xk
            @Override // java.lang.Runnable
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
            Log.m69e("SocketKeepalive", "Error stopping packet keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }
}
