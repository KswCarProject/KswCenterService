package android.content;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.QueuedWork;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
public abstract class BroadcastReceiver {
    private boolean mDebugUnregister;
    @UnsupportedAppUsage
    private PendingResult mPendingResult;

    public abstract void onReceive(Context context, Intent intent);

    /* loaded from: classes.dex */
    public static class PendingResult {
        public static final int TYPE_COMPONENT = 0;
        public static final int TYPE_REGISTERED = 1;
        public static final int TYPE_UNREGISTERED = 2;
        @UnsupportedAppUsage
        boolean mAbortBroadcast;
        @UnsupportedAppUsage
        boolean mFinished;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        final int mFlags;
        @UnsupportedAppUsage
        final boolean mInitialStickyHint;
        @UnsupportedAppUsage
        final boolean mOrderedHint;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        int mResultCode;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        String mResultData;
        @UnsupportedAppUsage
        Bundle mResultExtras;
        @UnsupportedAppUsage
        final int mSendingUser;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        final IBinder mToken;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        final int mType;

        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public PendingResult(int resultCode, String resultData, Bundle resultExtras, int type, boolean ordered, boolean sticky, IBinder token, int userId, int flags) {
            this.mResultCode = resultCode;
            this.mResultData = resultData;
            this.mResultExtras = resultExtras;
            this.mType = type;
            this.mOrderedHint = ordered;
            this.mInitialStickyHint = sticky;
            this.mToken = token;
            this.mSendingUser = userId;
            this.mFlags = flags;
        }

        public final void setResultCode(int code) {
            checkSynchronousHint();
            this.mResultCode = code;
        }

        public final int getResultCode() {
            return this.mResultCode;
        }

        public final void setResultData(String data) {
            checkSynchronousHint();
            this.mResultData = data;
        }

        public final String getResultData() {
            return this.mResultData;
        }

        public final void setResultExtras(Bundle extras) {
            checkSynchronousHint();
            this.mResultExtras = extras;
        }

        public final Bundle getResultExtras(boolean makeMap) {
            Bundle e = this.mResultExtras;
            if (makeMap) {
                if (e == null) {
                    Bundle e2 = new Bundle();
                    this.mResultExtras = e2;
                    return e2;
                }
                return e;
            }
            return e;
        }

        public final void setResult(int code, String data, Bundle extras) {
            checkSynchronousHint();
            this.mResultCode = code;
            this.mResultData = data;
            this.mResultExtras = extras;
        }

        public final boolean getAbortBroadcast() {
            return this.mAbortBroadcast;
        }

        public final void abortBroadcast() {
            checkSynchronousHint();
            this.mAbortBroadcast = true;
        }

        public final void clearAbortBroadcast() {
            this.mAbortBroadcast = false;
        }

        public final void finish() {
            if (this.mType != 0) {
                if (this.mOrderedHint && this.mType != 2) {
                    sendFinished(ActivityManager.getService());
                    return;
                }
                return;
            }
            final IActivityManager mgr = ActivityManager.getService();
            if (QueuedWork.hasPendingWork()) {
                QueuedWork.queue(new Runnable() { // from class: android.content.BroadcastReceiver.PendingResult.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PendingResult.this.sendFinished(mgr);
                    }
                }, false);
            } else {
                sendFinished(mgr);
            }
        }

        public void setExtrasClassLoader(ClassLoader cl) {
            if (this.mResultExtras != null) {
                this.mResultExtras.setClassLoader(cl);
            }
        }

        public void sendFinished(IActivityManager am) {
            synchronized (this) {
                if (this.mFinished) {
                    throw new IllegalStateException("Broadcast already finished");
                }
                this.mFinished = true;
                try {
                    if (this.mResultExtras != null) {
                        this.mResultExtras.setAllowFds(false);
                    }
                    if (this.mOrderedHint) {
                        am.finishReceiver(this.mToken, this.mResultCode, this.mResultData, this.mResultExtras, this.mAbortBroadcast, this.mFlags);
                    } else {
                        am.finishReceiver(this.mToken, 0, null, null, false, this.mFlags);
                    }
                } catch (RemoteException e) {
                }
            }
        }

        public int getSendingUserId() {
            return this.mSendingUser;
        }

        void checkSynchronousHint() {
            if (this.mOrderedHint || this.mInitialStickyHint) {
                return;
            }
            RuntimeException e = new RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
            e.fillInStackTrace();
            Log.m69e("BroadcastReceiver", e.getMessage(), e);
        }
    }

    public final PendingResult goAsync() {
        PendingResult res = this.mPendingResult;
        this.mPendingResult = null;
        return res;
    }

    public IBinder peekService(Context myContext, Intent service) {
        IActivityManager am = ActivityManager.getService();
        try {
            service.prepareToLeaveProcess(myContext);
            IBinder binder = am.peekService(service, service.resolveTypeIfNeeded(myContext.getContentResolver()), myContext.getOpPackageName());
            return binder;
        } catch (RemoteException e) {
            return null;
        }
    }

    public final void setResultCode(int code) {
        checkSynchronousHint();
        this.mPendingResult.mResultCode = code;
    }

    public final int getResultCode() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mResultCode;
        }
        return 0;
    }

    public final void setResultData(String data) {
        checkSynchronousHint();
        this.mPendingResult.mResultData = data;
    }

    public final String getResultData() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mResultData;
        }
        return null;
    }

    public final void setResultExtras(Bundle extras) {
        checkSynchronousHint();
        this.mPendingResult.mResultExtras = extras;
    }

    public final Bundle getResultExtras(boolean makeMap) {
        if (this.mPendingResult == null) {
            return null;
        }
        Bundle e = this.mPendingResult.mResultExtras;
        if (makeMap) {
            if (e == null) {
                PendingResult pendingResult = this.mPendingResult;
                Bundle e2 = new Bundle();
                pendingResult.mResultExtras = e2;
                return e2;
            }
            return e;
        }
        return e;
    }

    public final void setResult(int code, String data, Bundle extras) {
        checkSynchronousHint();
        this.mPendingResult.mResultCode = code;
        this.mPendingResult.mResultData = data;
        this.mPendingResult.mResultExtras = extras;
    }

    public final boolean getAbortBroadcast() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mAbortBroadcast;
        }
        return false;
    }

    public final void abortBroadcast() {
        checkSynchronousHint();
        this.mPendingResult.mAbortBroadcast = true;
    }

    public final void clearAbortBroadcast() {
        if (this.mPendingResult != null) {
            this.mPendingResult.mAbortBroadcast = false;
        }
    }

    public final boolean isOrderedBroadcast() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mOrderedHint;
        }
        return false;
    }

    public final boolean isInitialStickyBroadcast() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mInitialStickyHint;
        }
        return false;
    }

    public final void setOrderedHint(boolean isOrdered) {
    }

    @UnsupportedAppUsage
    public final void setPendingResult(PendingResult result) {
        this.mPendingResult = result;
    }

    @UnsupportedAppUsage
    public final PendingResult getPendingResult() {
        return this.mPendingResult;
    }

    public int getSendingUserId() {
        return this.mPendingResult.mSendingUser;
    }

    public final void setDebugUnregister(boolean debug) {
        this.mDebugUnregister = debug;
    }

    public final boolean getDebugUnregister() {
        return this.mDebugUnregister;
    }

    void checkSynchronousHint() {
        if (this.mPendingResult == null) {
            throw new IllegalStateException("Call while result is not pending");
        }
        if (this.mPendingResult.mOrderedHint || this.mPendingResult.mInitialStickyHint) {
            return;
        }
        RuntimeException e = new RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
        e.fillInStackTrace();
        Log.m69e("BroadcastReceiver", e.getMessage(), e);
    }
}
