package android.os;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.IUpdateLock;
import android.util.Log;

public class UpdateLock {
    private static final boolean DEBUG = false;
    @UnsupportedAppUsage
    public static final String NOW_IS_CONVENIENT = "nowisconvenient";
    private static final String TAG = "UpdateLock";
    @UnsupportedAppUsage
    public static final String TIMESTAMP = "timestamp";
    @UnsupportedAppUsage
    public static final String UPDATE_LOCK_CHANGED = "android.os.UpdateLock.UPDATE_LOCK_CHANGED";
    private static IUpdateLock sService;
    int mCount = 0;
    boolean mHeld = false;
    boolean mRefCounted = true;
    final String mTag;
    IBinder mToken;

    private static void checkService() {
        if (sService == null) {
            sService = IUpdateLock.Stub.asInterface(ServiceManager.getService(Context.UPDATE_LOCK_SERVICE));
        }
    }

    public UpdateLock(String tag) {
        this.mTag = tag;
        this.mToken = new Binder();
    }

    public void setReferenceCounted(boolean isRefCounted) {
        this.mRefCounted = isRefCounted;
    }

    @UnsupportedAppUsage
    public boolean isHeld() {
        boolean z;
        synchronized (this.mToken) {
            z = this.mHeld;
        }
        return z;
    }

    @UnsupportedAppUsage
    public void acquire() {
        checkService();
        synchronized (this.mToken) {
            acquireLocked();
        }
    }

    private void acquireLocked() {
        if (this.mRefCounted) {
            int i = this.mCount;
            this.mCount = i + 1;
            if (i != 0) {
                return;
            }
        }
        if (sService != null) {
            try {
                sService.acquireUpdateLock(this.mToken, this.mTag);
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to contact service to acquire");
            }
        }
        this.mHeld = true;
    }

    @UnsupportedAppUsage
    public void release() {
        checkService();
        synchronized (this.mToken) {
            releaseLocked();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000a, code lost:
        if (r0 == 0) goto L_0x000c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void releaseLocked() {
        /*
            r3 = this;
            boolean r0 = r3.mRefCounted
            if (r0 == 0) goto L_0x000c
            int r0 = r3.mCount
            int r0 = r0 + -1
            r3.mCount = r0
            if (r0 != 0) goto L_0x0023
        L_0x000c:
            android.os.IUpdateLock r0 = sService
            if (r0 == 0) goto L_0x0020
            android.os.IUpdateLock r0 = sService     // Catch:{ RemoteException -> 0x0018 }
            android.os.IBinder r1 = r3.mToken     // Catch:{ RemoteException -> 0x0018 }
            r0.releaseUpdateLock(r1)     // Catch:{ RemoteException -> 0x0018 }
            goto L_0x0020
        L_0x0018:
            r0 = move-exception
            java.lang.String r1 = "UpdateLock"
            java.lang.String r2 = "Unable to contact service to release"
            android.util.Log.e(r1, r2)
        L_0x0020:
            r0 = 0
            r3.mHeld = r0
        L_0x0023:
            int r0 = r3.mCount
            if (r0 < 0) goto L_0x0028
            return
        L_0x0028:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "UpdateLock under-locked"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.UpdateLock.releaseLocked():void");
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        synchronized (this.mToken) {
            if (this.mHeld) {
                Log.wtf(TAG, "UpdateLock finalized while still held");
                try {
                    sService.releaseUpdateLock(this.mToken);
                } catch (RemoteException e) {
                    Log.e(TAG, "Unable to contact service to release");
                }
            }
        }
    }
}
