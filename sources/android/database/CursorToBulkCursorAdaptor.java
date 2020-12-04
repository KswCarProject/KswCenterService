package android.database;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

public final class CursorToBulkCursorAdaptor extends BulkCursorNative implements IBinder.DeathRecipient {
    private static final String TAG = "Cursor";
    private CrossProcessCursor mCursor;
    private CursorWindow mFilledWindow;
    private final Object mLock = new Object();
    private ContentObserverProxy mObserver;
    private final String mProviderName;

    private static final class ContentObserverProxy extends ContentObserver {
        protected IContentObserver mRemote;

        public ContentObserverProxy(IContentObserver remoteObserver, IBinder.DeathRecipient recipient) {
            super((Handler) null);
            this.mRemote = remoteObserver;
            try {
                remoteObserver.asBinder().linkToDeath(recipient, 0);
            } catch (RemoteException e) {
            }
        }

        public boolean unlinkToDeath(IBinder.DeathRecipient recipient) {
            return this.mRemote.asBinder().unlinkToDeath(recipient, 0);
        }

        public boolean deliverSelfNotifications() {
            return false;
        }

        public void onChange(boolean selfChange, Uri uri) {
            try {
                this.mRemote.onChange(selfChange, uri, Process.myUid());
            } catch (RemoteException e) {
            }
        }
    }

    public CursorToBulkCursorAdaptor(Cursor cursor, IContentObserver observer, String providerName) {
        if (cursor instanceof CrossProcessCursor) {
            this.mCursor = (CrossProcessCursor) cursor;
        } else {
            this.mCursor = new CrossProcessCursorWrapper(cursor);
        }
        this.mProviderName = providerName;
        synchronized (this.mLock) {
            createAndRegisterObserverProxyLocked(observer);
        }
    }

    private void closeFilledWindowLocked() {
        if (this.mFilledWindow != null) {
            this.mFilledWindow.close();
            this.mFilledWindow = null;
        }
    }

    private void disposeLocked() {
        if (this.mCursor != null) {
            unregisterObserverProxyLocked();
            this.mCursor.close();
            this.mCursor = null;
        }
        closeFilledWindowLocked();
    }

    private void throwIfCursorIsClosed() {
        if (this.mCursor == null) {
            throw new StaleDataException("Attempted to access a cursor after it has been closed.");
        }
    }

    public void binderDied() {
        synchronized (this.mLock) {
            disposeLocked();
        }
    }

    public BulkCursorDescriptor getBulkCursorDescriptor() {
        BulkCursorDescriptor d;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            d = new BulkCursorDescriptor();
            d.cursor = this;
            d.columnNames = this.mCursor.getColumnNames();
            d.wantsAllOnMoveCalls = this.mCursor.getWantsAllOnMoveCalls();
            d.count = this.mCursor.getCount();
            d.window = this.mCursor.getWindow();
            if (d.window != null) {
                d.window.acquireReference();
            }
        }
        return d;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0051, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.database.CursorWindow getWindow(int r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            r4.throwIfCursorIsClosed()     // Catch:{ all -> 0x0052 }
            android.database.CrossProcessCursor r1 = r4.mCursor     // Catch:{ all -> 0x0052 }
            boolean r1 = r1.moveToPosition(r5)     // Catch:{ all -> 0x0052 }
            if (r1 != 0) goto L_0x0014
            r4.closeFilledWindowLocked()     // Catch:{ all -> 0x0052 }
            r1 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            return r1
        L_0x0014:
            android.database.CrossProcessCursor r1 = r4.mCursor     // Catch:{ all -> 0x0052 }
            android.database.CursorWindow r1 = r1.getWindow()     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x0020
            r4.closeFilledWindowLocked()     // Catch:{ all -> 0x0052 }
            goto L_0x004b
        L_0x0020:
            android.database.CursorWindow r2 = r4.mFilledWindow     // Catch:{ all -> 0x0052 }
            r1 = r2
            if (r1 != 0) goto L_0x0032
            android.database.CursorWindow r2 = new android.database.CursorWindow     // Catch:{ all -> 0x0052 }
            java.lang.String r3 = r4.mProviderName     // Catch:{ all -> 0x0052 }
            r2.<init>((java.lang.String) r3)     // Catch:{ all -> 0x0052 }
            r4.mFilledWindow = r2     // Catch:{ all -> 0x0052 }
            android.database.CursorWindow r2 = r4.mFilledWindow     // Catch:{ all -> 0x0052 }
            r1 = r2
            goto L_0x0046
        L_0x0032:
            int r2 = r1.getStartPosition()     // Catch:{ all -> 0x0052 }
            if (r5 < r2) goto L_0x0043
            int r2 = r1.getStartPosition()     // Catch:{ all -> 0x0052 }
            int r3 = r1.getNumRows()     // Catch:{ all -> 0x0052 }
            int r2 = r2 + r3
            if (r5 < r2) goto L_0x0046
        L_0x0043:
            r1.clear()     // Catch:{ all -> 0x0052 }
        L_0x0046:
            android.database.CrossProcessCursor r2 = r4.mCursor     // Catch:{ all -> 0x0052 }
            r2.fillWindow(r5, r1)     // Catch:{ all -> 0x0052 }
        L_0x004b:
            if (r1 == 0) goto L_0x0050
            r1.acquireReference()     // Catch:{ all -> 0x0052 }
        L_0x0050:
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            return r1
        L_0x0052:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.CursorToBulkCursorAdaptor.getWindow(int):android.database.CursorWindow");
    }

    public void onMove(int position) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            this.mCursor.onMove(this.mCursor.getPosition(), position);
        }
    }

    public void deactivate() {
        synchronized (this.mLock) {
            if (this.mCursor != null) {
                unregisterObserverProxyLocked();
                this.mCursor.deactivate();
            }
            closeFilledWindowLocked();
        }
    }

    public void close() {
        synchronized (this.mLock) {
            disposeLocked();
        }
    }

    public int requery(IContentObserver observer) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            closeFilledWindowLocked();
            try {
                if (!this.mCursor.requery()) {
                    return -1;
                }
                unregisterObserverProxyLocked();
                createAndRegisterObserverProxyLocked(observer);
                int count = this.mCursor.getCount();
                return count;
            } catch (IllegalStateException e) {
                throw new IllegalStateException(this.mProviderName + " Requery misuse db, mCursor isClosed:" + this.mCursor.isClosed(), e);
            }
        }
    }

    private void createAndRegisterObserverProxyLocked(IContentObserver observer) {
        if (this.mObserver == null) {
            this.mObserver = new ContentObserverProxy(observer, this);
            this.mCursor.registerContentObserver(this.mObserver);
            return;
        }
        throw new IllegalStateException("an observer is already registered");
    }

    private void unregisterObserverProxyLocked() {
        if (this.mObserver != null) {
            this.mCursor.unregisterContentObserver(this.mObserver);
            this.mObserver.unlinkToDeath(this);
            this.mObserver = null;
        }
    }

    public Bundle getExtras() {
        Bundle extras;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            extras = this.mCursor.getExtras();
        }
        return extras;
    }

    public Bundle respond(Bundle extras) {
        Bundle respond;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            respond = this.mCursor.respond(extras);
        }
        return respond;
    }
}
