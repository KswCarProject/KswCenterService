package com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import java.io.PrintWriter;

public class BinderDeathDispatcher<T extends IInterface> {
    private static final String TAG = "BinderDeathDispatcher";
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public final ArrayMap<IBinder, BinderDeathDispatcher<T>.RecipientsInfo> mTargets = new ArrayMap<>();

    @VisibleForTesting
    class RecipientsInfo implements IBinder.DeathRecipient {
        @GuardedBy({"mLock"})
        ArraySet<IBinder.DeathRecipient> mRecipients;
        final IBinder mTarget;

        private RecipientsInfo(IBinder target) {
            this.mRecipients = new ArraySet<>();
            this.mTarget = target;
        }

        public void binderDied() {
            ArraySet<IBinder.DeathRecipient> copy;
            synchronized (BinderDeathDispatcher.this.mLock) {
                copy = this.mRecipients;
                this.mRecipients = null;
                BinderDeathDispatcher.this.mTargets.remove(this.mTarget);
            }
            if (copy != null) {
                int size = copy.size();
                for (int i = 0; i < size; i++) {
                    copy.valueAt(i).binderDied();
                }
            }
        }
    }

    public int linkToDeath(T target, IBinder.DeathRecipient recipient) {
        int size;
        IBinder ib = target.asBinder();
        synchronized (this.mLock) {
            BinderDeathDispatcher<T>.RecipientsInfo info = this.mTargets.get(ib);
            if (info == null) {
                info = new RecipientsInfo(ib);
                try {
                    ib.linkToDeath(info, 0);
                    this.mTargets.put(ib, info);
                } catch (RemoteException e) {
                    return -1;
                }
            }
            info.mRecipients.add(recipient);
            size = info.mRecipients.size();
        }
        return size;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0031, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void unlinkToDeath(T r6, android.os.IBinder.DeathRecipient r7) {
        /*
            r5 = this;
            android.os.IBinder r0 = r6.asBinder()
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            android.util.ArrayMap<android.os.IBinder, com.android.internal.os.BinderDeathDispatcher<T>$RecipientsInfo> r2 = r5.mTargets     // Catch:{ all -> 0x0032 }
            java.lang.Object r2 = r2.get(r0)     // Catch:{ all -> 0x0032 }
            com.android.internal.os.BinderDeathDispatcher$RecipientsInfo r2 = (com.android.internal.os.BinderDeathDispatcher.RecipientsInfo) r2     // Catch:{ all -> 0x0032 }
            if (r2 != 0) goto L_0x0013
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            return
        L_0x0013:
            android.util.ArraySet<android.os.IBinder$DeathRecipient> r3 = r2.mRecipients     // Catch:{ all -> 0x0032 }
            boolean r3 = r3.remove(r7)     // Catch:{ all -> 0x0032 }
            if (r3 == 0) goto L_0x0030
            android.util.ArraySet<android.os.IBinder$DeathRecipient> r3 = r2.mRecipients     // Catch:{ all -> 0x0032 }
            int r3 = r3.size()     // Catch:{ all -> 0x0032 }
            if (r3 != 0) goto L_0x0030
            android.os.IBinder r3 = r2.mTarget     // Catch:{ all -> 0x0032 }
            r4 = 0
            r3.unlinkToDeath(r2, r4)     // Catch:{ all -> 0x0032 }
            android.util.ArrayMap<android.os.IBinder, com.android.internal.os.BinderDeathDispatcher<T>$RecipientsInfo> r3 = r5.mTargets     // Catch:{ all -> 0x0032 }
            android.os.IBinder r4 = r2.mTarget     // Catch:{ all -> 0x0032 }
            r3.remove(r4)     // Catch:{ all -> 0x0032 }
        L_0x0030:
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            return
        L_0x0032:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderDeathDispatcher.unlinkToDeath(android.os.IInterface, android.os.IBinder$DeathRecipient):void");
    }

    public void dump(PrintWriter pw, String indent) {
        synchronized (this.mLock) {
            pw.print(indent);
            pw.print("# of watched binders: ");
            pw.println(this.mTargets.size());
            pw.print(indent);
            pw.print("# of death recipients: ");
            int n = 0;
            for (BinderDeathDispatcher<T>.RecipientsInfo info : this.mTargets.values()) {
                n += info.mRecipients.size();
            }
            pw.println(n);
        }
    }

    @VisibleForTesting
    public ArrayMap<IBinder, BinderDeathDispatcher<T>.RecipientsInfo> getTargetsForTest() {
        return this.mTargets;
    }
}
