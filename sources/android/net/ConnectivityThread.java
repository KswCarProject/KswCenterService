package android.net;

import android.p007os.HandlerThread;
import android.p007os.Looper;

/* loaded from: classes3.dex */
public final class ConnectivityThread extends HandlerThread {
    static /* synthetic */ ConnectivityThread access$000() {
        return createInstance();
    }

    /* loaded from: classes3.dex */
    private static class Singleton {
        private static final ConnectivityThread INSTANCE = ConnectivityThread.access$000();

        private Singleton() {
        }
    }

    private ConnectivityThread() {
        super("ConnectivityThread");
    }

    private static ConnectivityThread createInstance() {
        ConnectivityThread t = new ConnectivityThread();
        t.start();
        return t;
    }

    public static ConnectivityThread get() {
        return Singleton.INSTANCE;
    }

    public static Looper getInstanceLooper() {
        return Singleton.INSTANCE.getLooper();
    }
}
