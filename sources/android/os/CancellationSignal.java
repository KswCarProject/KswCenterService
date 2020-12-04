package android.os;

import android.os.ICancellationSignal;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;
    private ICancellationSignal mRemote;

    public interface OnCancelListener {
        void onCancel();
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.mIsCanceled;
        }
        return z;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.onCancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        if (r1 == null) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r1.cancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r4.mCancelInProgress = false;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0027, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r0 == null) goto L_0x001a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.mIsCanceled     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x0038 }
            return
        L_0x0007:
            r0 = 1
            r4.mIsCanceled = r0     // Catch:{ all -> 0x0038 }
            r4.mCancelInProgress = r0     // Catch:{ all -> 0x0038 }
            android.os.CancellationSignal$OnCancelListener r0 = r4.mOnCancelListener     // Catch:{ all -> 0x0038 }
            android.os.ICancellationSignal r1 = r4.mRemote     // Catch:{ all -> 0x0038 }
            monitor-exit(r4)     // Catch:{ all -> 0x0038 }
            r2 = 0
            if (r0 == 0) goto L_0x001a
            r0.onCancel()     // Catch:{ all -> 0x0018 }
            goto L_0x001a
        L_0x0018:
            r3 = move-exception
            goto L_0x0020
        L_0x001a:
            if (r1 == 0) goto L_0x002c
            r1.cancel()     // Catch:{ RemoteException -> 0x002b }
            goto L_0x002c
        L_0x0020:
            monitor-enter(r4)
            r4.mCancelInProgress = r2     // Catch:{ all -> 0x0028 }
            r4.notifyAll()     // Catch:{ all -> 0x0028 }
            monitor-exit(r4)     // Catch:{ all -> 0x0028 }
            throw r3
        L_0x0028:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0028 }
            throw r2
        L_0x002b:
            r3 = move-exception
        L_0x002c:
            monitor-enter(r4)
            r4.mCancelInProgress = r2     // Catch:{ all -> 0x0035 }
            r4.notifyAll()     // Catch:{ all -> 0x0035 }
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            return
        L_0x0035:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            throw r2
        L_0x0038:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0038 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.CancellationSignal.cancel():void");
    }

    public void setOnCancelListener(OnCancelListener listener) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mOnCancelListener != listener) {
                this.mOnCancelListener = listener;
                if (this.mIsCanceled) {
                    if (listener != null) {
                        listener.onCancel();
                    }
                }
            }
        }
    }

    public void setRemote(ICancellationSignal remote) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mRemote != remote) {
                this.mRemote = remote;
                if (this.mIsCanceled) {
                    if (remote != null) {
                        try {
                            remote.cancel();
                        } catch (RemoteException e) {
                        }
                    }
                }
            }
        }
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static ICancellationSignal createTransport() {
        return new Transport();
    }

    public static CancellationSignal fromTransport(ICancellationSignal transport) {
        if (transport instanceof Transport) {
            return ((Transport) transport).mCancellationSignal;
        }
        return null;
    }

    private static final class Transport extends ICancellationSignal.Stub {
        final CancellationSignal mCancellationSignal;

        private Transport() {
            this.mCancellationSignal = new CancellationSignal();
        }

        public void cancel() throws RemoteException {
            this.mCancellationSignal.cancel();
        }
    }
}
