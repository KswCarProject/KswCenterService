package com.android.internal.infra;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class AbstractRemoteService<S extends AbstractRemoteService<S, I>, I extends IInterface> implements IBinder.DeathRecipient {
    protected static final int LAST_PRIVATE_MSG = 2;
    private static final int MSG_BIND = 1;
    private static final int MSG_UNBIND = 2;
    public static final long PERMANENT_BOUND_TIMEOUT_MS = 0;
    /* access modifiers changed from: private */
    public boolean mBinding;
    private final int mBindingFlags;
    private boolean mCompleted;
    protected final ComponentName mComponentName;
    private final Context mContext;
    /* access modifiers changed from: private */
    public boolean mDestroyed;
    protected final Handler mHandler;
    private final Intent mIntent;
    private long mNextUnbind;
    protected I mService;
    private final ServiceConnection mServiceConnection = new RemoteServiceConnection();
    /* access modifiers changed from: private */
    public boolean mServiceDied;
    protected final String mTag = getClass().getSimpleName();
    private final ArrayList<BasePendingRequest<S, I>> mUnfinishedRequests = new ArrayList<>();
    private final int mUserId;
    public final boolean mVerbose;
    private final VultureCallback<S> mVultureCallback;

    public interface AsyncRequest<I extends IInterface> {
        void run(I i) throws RemoteException;
    }

    public interface VultureCallback<T> {
        void onServiceDied(T t);
    }

    /* access modifiers changed from: protected */
    public abstract I getServiceInterface(IBinder iBinder);

    /* access modifiers changed from: protected */
    public abstract long getTimeoutIdleBindMillis();

    /* access modifiers changed from: package-private */
    public abstract void handleBindFailure();

    /* access modifiers changed from: protected */
    public abstract void handleOnDestroy();

    /* access modifiers changed from: package-private */
    public abstract void handlePendingRequestWhileUnBound(BasePendingRequest<S, I> basePendingRequest);

    /* access modifiers changed from: package-private */
    public abstract void handlePendingRequests();

    AbstractRemoteService(Context context, String serviceInterface, ComponentName componentName, int userId, VultureCallback<S> callback, Handler handler, int bindingFlags, boolean verbose) {
        this.mContext = context;
        this.mVultureCallback = callback;
        this.mVerbose = verbose;
        this.mComponentName = componentName;
        this.mIntent = new Intent(serviceInterface).setComponent(this.mComponentName);
        this.mUserId = userId;
        this.mHandler = new Handler(handler.getLooper());
        this.mBindingFlags = bindingFlags;
    }

    public final void destroy() {
        this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AbstractRemoteService$9IBVTCLLZgndvH7fu1P14PW1_1o.INSTANCE, this));
    }

    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    /* access modifiers changed from: private */
    public void handleOnConnectedStateChangedInternal(boolean connected) {
        handleOnConnectedStateChanged(connected);
        if (connected) {
            handlePendingRequests();
        }
    }

    /* access modifiers changed from: protected */
    public void handleOnConnectedStateChanged(boolean state) {
    }

    /* access modifiers changed from: protected */
    public long getRemoteRequestMillis() {
        throw new UnsupportedOperationException("not implemented by " + getClass());
    }

    public final I getServiceInterface() {
        return this.mService;
    }

    /* access modifiers changed from: private */
    public void handleDestroy() {
        if (!checkIfDestroyed()) {
            handleOnDestroy();
            handleEnsureUnbound();
            this.mDestroyed = true;
        }
    }

    public void binderDied() {
        this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AbstractRemoteService$ocrHd68Md9x6FfAzVQ6w8MAjFqY.INSTANCE, this));
    }

    /* access modifiers changed from: private */
    public void handleBinderDied() {
        if (!checkIfDestroyed()) {
            if (this.mService != null) {
                this.mService.asBinder().unlinkToDeath(this, 0);
            }
            this.mService = null;
            this.mServiceDied = true;
            cancelScheduledUnbind();
            this.mVultureCallback.onServiceDied(this);
            handleBindFailure();
        }
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.append(prefix).append("service:").println();
        pw.append(prefix).append("  ").append("userId=").append(String.valueOf(this.mUserId)).println();
        pw.append(prefix).append("  ").append("componentName=").append(this.mComponentName.flattenToString()).println();
        pw.append(prefix).append("  ").append("destroyed=").append(String.valueOf(this.mDestroyed)).println();
        pw.append(prefix).append("  ").append("numUnfinishedRequests=").append(String.valueOf(this.mUnfinishedRequests.size())).println();
        boolean bound = handleIsBound();
        pw.append(prefix).append("  ").append("bound=").append(String.valueOf(bound));
        long idleTimeout = getTimeoutIdleBindMillis();
        if (bound) {
            if (idleTimeout > 0) {
                pw.append(" (unbind in : ");
                TimeUtils.formatDuration(this.mNextUnbind - SystemClock.elapsedRealtime(), pw);
                pw.append(")");
            } else {
                pw.append(" (permanently bound)");
            }
        }
        pw.println();
        pw.append(prefix).append("mBindingFlags=").println(this.mBindingFlags);
        pw.append(prefix).append("idleTimeout=").append(Long.toString(idleTimeout / 1000)).append("s\n");
        pw.append(prefix).append("requestTimeout=");
        try {
            pw.append(Long.toString(getRemoteRequestMillis() / 1000)).append("s\n");
        } catch (UnsupportedOperationException e) {
            pw.append("not supported\n");
        }
        pw.println();
    }

    /* access modifiers changed from: protected */
    public void scheduleRequest(BasePendingRequest<S, I> pendingRequest) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$7CJJfrUZBVuXZyYFEWBNh8Mky8.INSTANCE, this, pendingRequest));
    }

    /* access modifiers changed from: package-private */
    public void finishRequest(BasePendingRequest<S, I> finshedRequest) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AbstractRemoteService$6FcEKfZ7TXLg6dcCU8EMuMNAy4.INSTANCE, this, finshedRequest));
    }

    /* access modifiers changed from: private */
    public void handleFinishRequest(BasePendingRequest<S, I> finshedRequest) {
        this.mUnfinishedRequests.remove(finshedRequest);
        if (this.mUnfinishedRequests.isEmpty()) {
            scheduleUnbind();
        }
    }

    /* access modifiers changed from: protected */
    public void scheduleAsyncRequest(AsyncRequest<I> request) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$EbzSql2RHkXox5Myj8A7kLC4_A.INSTANCE, this, new MyAsyncPendingRequest<>(this, request)));
    }

    private void cancelScheduledUnbind() {
        this.mHandler.removeMessages(2);
    }

    /* access modifiers changed from: protected */
    public void scheduleBind() {
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AbstractRemoteService$YSUzqqi1Pbrg2dlwMGMtKWbGXck.INSTANCE, this).setWhat(1));
        } else if (this.mVerbose) {
            Slog.v(this.mTag, "scheduleBind(): already scheduled");
        }
    }

    /* access modifiers changed from: protected */
    public void scheduleUnbind() {
        scheduleUnbind(true);
    }

    /* access modifiers changed from: private */
    public void scheduleUnbind(boolean delay) {
        long unbindDelay = getTimeoutIdleBindMillis();
        if (unbindDelay > 0) {
            if (!delay) {
                unbindDelay = 0;
            }
            cancelScheduledUnbind();
            this.mNextUnbind = SystemClock.elapsedRealtime() + unbindDelay;
            if (this.mVerbose) {
                String str = this.mTag;
                Slog.v(str, "unbinding in " + unbindDelay + "ms: " + this.mNextUnbind);
            }
            this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage($$Lambda$AbstractRemoteService$MDW40b8CzodE5xRowI9wDEyXEnw.INSTANCE, this).setWhat(2), unbindDelay);
        } else if (this.mVerbose) {
            String str2 = this.mTag;
            Slog.v(str2, "not scheduling unbind when value is " + unbindDelay);
        }
    }

    /* access modifiers changed from: private */
    public void handleUnbind() {
        if (!checkIfDestroyed()) {
            handleEnsureUnbound();
        }
    }

    /* access modifiers changed from: protected */
    public final void handlePendingRequest(BasePendingRequest<S, I> pendingRequest) {
        if (!checkIfDestroyed() && !this.mCompleted) {
            if (!handleIsBound()) {
                if (this.mVerbose) {
                    String str = this.mTag;
                    Slog.v(str, "handlePendingRequest(): queuing " + pendingRequest);
                }
                handlePendingRequestWhileUnBound(pendingRequest);
                handleEnsureBound();
                return;
            }
            if (this.mVerbose) {
                String str2 = this.mTag;
                Slog.v(str2, "handlePendingRequest(): " + pendingRequest);
            }
            this.mUnfinishedRequests.add(pendingRequest);
            cancelScheduledUnbind();
            pendingRequest.run();
            if (pendingRequest.isFinal()) {
                this.mCompleted = true;
            }
        }
    }

    private boolean handleIsBound() {
        return this.mService != null;
    }

    /* access modifiers changed from: private */
    public void handleEnsureBound() {
        if (!handleIsBound() && !this.mBinding) {
            if (this.mVerbose) {
                Slog.v(this.mTag, "ensureBound()");
            }
            this.mBinding = true;
            int flags = 67108865 | this.mBindingFlags;
            if (!this.mContext.bindServiceAsUser(this.mIntent, this.mServiceConnection, flags, this.mHandler, new UserHandle(this.mUserId))) {
                String str = this.mTag;
                Slog.w(str, "could not bind to " + this.mIntent + " using flags " + flags);
                this.mBinding = false;
                if (!this.mServiceDied) {
                    handleBinderDied();
                }
            }
        }
    }

    private void handleEnsureUnbound() {
        if (handleIsBound() || this.mBinding) {
            if (this.mVerbose) {
                Slog.v(this.mTag, "ensureUnbound()");
            }
            this.mBinding = false;
            if (handleIsBound()) {
                handleOnConnectedStateChangedInternal(false);
                if (this.mService != null) {
                    this.mService.asBinder().unlinkToDeath(this, 0);
                    this.mService = null;
                }
            }
            this.mNextUnbind = 0;
            this.mContext.unbindService(this.mServiceConnection);
        }
    }

    private class RemoteServiceConnection implements ServiceConnection {
        private RemoteServiceConnection() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            if (AbstractRemoteService.this.mVerbose) {
                Slog.v(AbstractRemoteService.this.mTag, "onServiceConnected()");
            }
            if (AbstractRemoteService.this.mDestroyed || !AbstractRemoteService.this.mBinding) {
                Slog.wtf(AbstractRemoteService.this.mTag, "onServiceConnected() was dispatched after unbindService.");
                return;
            }
            boolean unused = AbstractRemoteService.this.mBinding = false;
            try {
                service.linkToDeath(AbstractRemoteService.this, 0);
                AbstractRemoteService.this.mService = AbstractRemoteService.this.getServiceInterface(service);
                AbstractRemoteService.this.handleOnConnectedStateChangedInternal(true);
                boolean unused2 = AbstractRemoteService.this.mServiceDied = false;
            } catch (RemoteException e) {
                AbstractRemoteService.this.handleBinderDied();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            if (AbstractRemoteService.this.mVerbose) {
                Slog.v(AbstractRemoteService.this.mTag, "onServiceDisconnected()");
            }
            boolean unused = AbstractRemoteService.this.mBinding = true;
            AbstractRemoteService.this.mService = null;
        }

        public void onBindingDied(ComponentName name) {
            if (AbstractRemoteService.this.mVerbose) {
                Slog.v(AbstractRemoteService.this.mTag, "onBindingDied()");
            }
            AbstractRemoteService.this.scheduleUnbind(false);
        }
    }

    private boolean checkIfDestroyed() {
        if (this.mDestroyed && this.mVerbose) {
            String str = this.mTag;
            Slog.v(str, "Not handling operation as service for " + this.mComponentName + " is already destroyed");
        }
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[");
        sb.append(this.mComponentName);
        sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        sb.append(System.identityHashCode(this));
        sb.append(this.mService != null ? " (bound)" : " (unbound)");
        sb.append(this.mDestroyed ? " (destroyed)" : "");
        sb.append("]");
        return sb.toString();
    }

    public static abstract class BasePendingRequest<S extends AbstractRemoteService<S, I>, I extends IInterface> implements Runnable {
        @GuardedBy({"mLock"})
        boolean mCancelled;
        @GuardedBy({"mLock"})
        boolean mCompleted;
        protected final Object mLock = new Object();
        protected final String mTag = getClass().getSimpleName();
        final WeakReference<S> mWeakService;

        BasePendingRequest(S service) {
            this.mWeakService = new WeakReference<>(service);
        }

        /* access modifiers changed from: protected */
        public final S getService() {
            return (AbstractRemoteService) this.mWeakService.get();
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
            r0 = (com.android.internal.infra.AbstractRemoteService) r2.mWeakService.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
            if (r0 == null) goto L_0x001d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
            r0.finishRequest(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
            onFinished();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
            return true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean finish() {
            /*
                r2 = this;
                java.lang.Object r0 = r2.mLock
                monitor-enter(r0)
                boolean r1 = r2.mCompleted     // Catch:{ all -> 0x0024 }
                if (r1 != 0) goto L_0x0021
                boolean r1 = r2.mCancelled     // Catch:{ all -> 0x0024 }
                if (r1 == 0) goto L_0x000c
                goto L_0x0021
            L_0x000c:
                r1 = 1
                r2.mCompleted = r1     // Catch:{ all -> 0x0024 }
                monitor-exit(r0)     // Catch:{ all -> 0x0024 }
                java.lang.ref.WeakReference<S> r0 = r2.mWeakService
                java.lang.Object r0 = r0.get()
                com.android.internal.infra.AbstractRemoteService r0 = (com.android.internal.infra.AbstractRemoteService) r0
                if (r0 == 0) goto L_0x001d
                r0.finishRequest(r2)
            L_0x001d:
                r2.onFinished()
                return r1
            L_0x0021:
                r1 = 0
                monitor-exit(r0)     // Catch:{ all -> 0x0024 }
                return r1
            L_0x0024:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0024 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.infra.AbstractRemoteService.BasePendingRequest.finish():boolean");
        }

        /* access modifiers changed from: package-private */
        public void onFinished() {
        }

        /* access modifiers changed from: protected */
        public void onFailed() {
        }

        /* access modifiers changed from: protected */
        @GuardedBy({"mLock"})
        public final boolean isCancelledLocked() {
            return this.mCancelled;
        }

        public boolean cancel() {
            synchronized (this.mLock) {
                if (!this.mCancelled) {
                    if (!this.mCompleted) {
                        this.mCancelled = true;
                        onCancel();
                        return true;
                    }
                }
                return false;
            }
        }

        /* access modifiers changed from: package-private */
        public void onCancel() {
        }

        /* access modifiers changed from: protected */
        public boolean isFinal() {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean isRequestCompleted() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mCompleted;
            }
            return z;
        }
    }

    public static abstract class PendingRequest<S extends AbstractRemoteService<S, I>, I extends IInterface> extends BasePendingRequest<S, I> {
        private final Handler mServiceHandler;
        private final Runnable mTimeoutTrigger;

        /* access modifiers changed from: protected */
        public abstract void onTimeout(S s);

        protected PendingRequest(S service) {
            super(service);
            this.mServiceHandler = service.mHandler;
            this.mTimeoutTrigger = new Runnable(service) {
                private final /* synthetic */ AbstractRemoteService f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    AbstractRemoteService.PendingRequest.lambda$new$0(AbstractRemoteService.PendingRequest.this, this.f$1);
                }
            };
            this.mServiceHandler.postAtTime(this.mTimeoutTrigger, SystemClock.uptimeMillis() + service.getRemoteRequestMillis());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
            if (r0 == null) goto L_0x003e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
            r1 = r5.mTag;
            android.util.Slog.w(r1, "timed out after " + r6.getRemoteRequestMillis() + " ms");
            r0.finishRequest(r5);
            r5.onTimeout(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x003e, code lost:
            android.util.Slog.w(r5.mTag, "timed out (no service)");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x000d, code lost:
            r0 = (com.android.internal.infra.AbstractRemoteService) r5.mWeakService.get();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static /* synthetic */ void lambda$new$0(com.android.internal.infra.AbstractRemoteService.PendingRequest r5, com.android.internal.infra.AbstractRemoteService r6) {
            /*
                java.lang.Object r0 = r5.mLock
                monitor-enter(r0)
                boolean r1 = r5.mCancelled     // Catch:{ all -> 0x0047 }
                if (r1 == 0) goto L_0x0009
                monitor-exit(r0)     // Catch:{ all -> 0x0047 }
                return
            L_0x0009:
                r1 = 1
                r5.mCompleted = r1     // Catch:{ all -> 0x0047 }
                monitor-exit(r0)     // Catch:{ all -> 0x0047 }
                java.lang.ref.WeakReference r0 = r5.mWeakService
                java.lang.Object r0 = r0.get()
                com.android.internal.infra.AbstractRemoteService r0 = (com.android.internal.infra.AbstractRemoteService) r0
                if (r0 == 0) goto L_0x003e
                java.lang.String r1 = r5.mTag
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "timed out after "
                r2.append(r3)
                long r3 = r6.getRemoteRequestMillis()
                r2.append(r3)
                java.lang.String r3 = " ms"
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
                r0.finishRequest(r5)
                r5.onTimeout(r0)
                goto L_0x0046
            L_0x003e:
                java.lang.String r1 = r5.mTag
                java.lang.String r2 = "timed out (no service)"
                android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            L_0x0046:
                return
            L_0x0047:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0047 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.infra.AbstractRemoteService.PendingRequest.lambda$new$0(com.android.internal.infra.AbstractRemoteService$PendingRequest, com.android.internal.infra.AbstractRemoteService):void");
        }

        /* access modifiers changed from: package-private */
        public final void onFinished() {
            this.mServiceHandler.removeCallbacks(this.mTimeoutTrigger);
        }

        /* access modifiers changed from: package-private */
        public final void onCancel() {
            this.mServiceHandler.removeCallbacks(this.mTimeoutTrigger);
        }
    }

    private static final class MyAsyncPendingRequest<S extends AbstractRemoteService<S, I>, I extends IInterface> extends BasePendingRequest<S, I> {
        private static final String TAG = MyAsyncPendingRequest.class.getSimpleName();
        private final AsyncRequest<I> mRequest;

        protected MyAsyncPendingRequest(S service, AsyncRequest<I> request) {
            super(service);
            this.mRequest = request;
        }

        public void run() {
            S remoteService = getService();
            if (remoteService != null) {
                try {
                    this.mRequest.run(remoteService.mService);
                } catch (RemoteException e) {
                    String str = TAG;
                    Slog.w(str, "exception handling async request (" + this + "): " + e);
                } catch (Throwable th) {
                    finish();
                    throw th;
                }
                finish();
            }
        }
    }
}
