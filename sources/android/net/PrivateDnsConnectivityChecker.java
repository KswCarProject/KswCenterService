package android.net;

import android.util.Log;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class PrivateDnsConnectivityChecker {
    private static final int CONNECTION_TIMEOUT_MS = 5000;
    private static final int PRIVATE_DNS_PORT = 853;
    private static final String TAG = "NetworkUtils";

    private PrivateDnsConnectivityChecker() {
    }

    public static boolean canConnectToPrivateDnsServer(String hostname) {
        SSLSocket socket;
        SocketFactory factory = SSLSocketFactory.getDefault();
        TrafficStats.setThreadStatsTag(TrafficStats.TAG_SYSTEM_APP);
        try {
            socket = (SSLSocket) factory.createSocket();
            socket.setSoTimeout(5000);
            socket.connect(new InetSocketAddress(hostname, 853));
            if (!socket.isConnected()) {
                Log.w(TAG, String.format("Connection to %s failed.", new Object[]{hostname}));
                if (socket != null) {
                    socket.close();
                }
                return false;
            }
            socket.startHandshake();
            Log.w(TAG, String.format("TLS handshake to %s succeeded.", new Object[]{hostname}));
            if (socket != null) {
                socket.close();
            }
            return true;
        } catch (IOException e) {
            Log.w(TAG, String.format("TLS handshake to %s failed.", new Object[]{hostname}), e);
            return false;
        } catch (Throwable th) {
            r4.addSuppressed(th);
        }
        throw th;
    }
}
