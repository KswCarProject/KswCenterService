package android.net;

import android.net.ISocketKeepaliveCallback;
import android.net.SocketKeepalive;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import com.android.internal.util.FunctionalUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

public abstract class SocketKeepalive implements AutoCloseable {
    public static final int BINDER_DIED = -10;
    public static final int DATA_RECEIVED = -2;
    public static final int ERROR_HARDWARE_ERROR = -31;
    public static final int ERROR_HARDWARE_UNSUPPORTED = -30;
    public static final int ERROR_INSUFFICIENT_RESOURCES = -32;
    public static final int ERROR_INVALID_INTERVAL = -24;
    public static final int ERROR_INVALID_IP_ADDRESS = -21;
    public static final int ERROR_INVALID_LENGTH = -23;
    public static final int ERROR_INVALID_NETWORK = -20;
    public static final int ERROR_INVALID_PORT = -22;
    public static final int ERROR_INVALID_SOCKET = -25;
    public static final int ERROR_SOCKET_NOT_IDLE = -26;
    public static final int ERROR_UNSUPPORTED = -30;
    public static final int MAX_INTERVAL_SEC = 3600;
    public static final int MIN_INTERVAL_SEC = 10;
    public static final int NO_KEEPALIVE = -1;
    public static final int SUCCESS = 0;
    static final String TAG = "SocketKeepalive";
    final ISocketKeepaliveCallback mCallback;
    final Executor mExecutor;
    final Network mNetwork;
    final ParcelFileDescriptor mPfd;
    final IConnectivityManager mService;
    Integer mSlot;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    /* access modifiers changed from: package-private */
    public abstract void startImpl(int i);

    /* access modifiers changed from: package-private */
    public abstract void stopImpl();

    public static class ErrorCodeException extends Exception {
        public final int error;

        public ErrorCodeException(int error2, Throwable e) {
            super(e);
            this.error = error2;
        }

        public ErrorCodeException(int error2) {
            this.error = error2;
        }
    }

    public static class InvalidSocketException extends ErrorCodeException {
        public InvalidSocketException(int error, Throwable e) {
            super(error, e);
        }

        public InvalidSocketException(int error) {
            super(error);
        }
    }

    public static class InvalidPacketException extends ErrorCodeException {
        public InvalidPacketException(int error) {
            super(error);
        }
    }

    SocketKeepalive(IConnectivityManager service, Network network, ParcelFileDescriptor pfd, final Executor executor, final Callback callback) {
        this.mService = service;
        this.mNetwork = network;
        this.mPfd = pfd;
        this.mExecutor = executor;
        this.mCallback = new ISocketKeepaliveCallback.Stub() {
            public void onStarted(int slot) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(slot, callback) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ SocketKeepalive.Callback f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        SocketKeepalive.this.mExecutor.execute(new Runnable(this.f$1, this.f$2) {
                            private final /* synthetic */ int f$1;
                            private final /* synthetic */ SocketKeepalive.Callback f$2;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                            }

                            public final void run() {
                                SocketKeepalive.AnonymousClass1.lambda$onStarted$0(SocketKeepalive.AnonymousClass1.this, this.f$1, this.f$2);
                            }
                        });
                    }
                });
            }

            public static /* synthetic */ void lambda$onStarted$0(AnonymousClass1 r2, int slot, Callback callback) {
                SocketKeepalive.this.mSlot = Integer.valueOf(slot);
                callback.onStarted();
            }

            public void onStopped() {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, callback) {
                    private final /* synthetic */ Executor f$1;
                    private final /* synthetic */ SocketKeepalive.Callback f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        this.f$1.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ SocketKeepalive.Callback f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                SocketKeepalive.AnonymousClass1.lambda$onStopped$2(SocketKeepalive.AnonymousClass1.this, this.f$1);
                            }
                        });
                    }
                });
            }

            public static /* synthetic */ void lambda$onStopped$2(AnonymousClass1 r2, Callback callback) {
                SocketKeepalive.this.mSlot = null;
                callback.onStopped();
            }

            public void onError(int error) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, callback, error) {
                    private final /* synthetic */ Executor f$1;
                    private final /* synthetic */ SocketKeepalive.Callback f$2;
                    private final /* synthetic */ int f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void runOrThrow() {
                        this.f$1.execute(new Runnable(this.f$2, this.f$3) {
                            private final /* synthetic */ SocketKeepalive.Callback f$1;
                            private final /* synthetic */ int f$2;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                            }

                            public final void run() {
                                SocketKeepalive.AnonymousClass1.lambda$onError$4(SocketKeepalive.AnonymousClass1.this, this.f$1, this.f$2);
                            }
                        });
                    }
                });
            }

            public static /* synthetic */ void lambda$onError$4(AnonymousClass1 r2, Callback callback, int error) {
                SocketKeepalive.this.mSlot = null;
                callback.onError(error);
            }

            public void onDataReceived() {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, callback) {
                    private final /* synthetic */ Executor f$1;
                    private final /* synthetic */ SocketKeepalive.Callback f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        this.f$1.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ SocketKeepalive.Callback f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                SocketKeepalive.AnonymousClass1.lambda$onDataReceived$6(SocketKeepalive.AnonymousClass1.this, this.f$1);
                            }
                        });
                    }
                });
            }

            public static /* synthetic */ void lambda$onDataReceived$6(AnonymousClass1 r2, Callback callback) {
                SocketKeepalive.this.mSlot = null;
                callback.onDataReceived();
            }
        };
    }

    public final void start(int intervalSec) {
        startImpl(intervalSec);
    }

    public final void stop() {
        stopImpl();
    }

    public final void close() {
        stop();
        try {
            this.mPfd.close();
        } catch (IOException e) {
        }
    }

    public static class Callback {
        public void onStarted() {
        }

        public void onStopped() {
        }

        public void onError(int error) {
        }

        public void onDataReceived() {
        }
    }
}
