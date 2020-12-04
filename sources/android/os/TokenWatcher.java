package android.os;

import android.os.IBinder;
import android.util.Log;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class TokenWatcher {
    private volatile boolean mAcquired = false;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public int mNotificationQueue = -1;
    private Runnable mNotificationTask = new Runnable() {
        public void run() {
            int value;
            synchronized (TokenWatcher.this.mTokens) {
                value = TokenWatcher.this.mNotificationQueue;
                int unused = TokenWatcher.this.mNotificationQueue = -1;
            }
            if (value == 1) {
                TokenWatcher.this.acquired();
            } else if (value == 0) {
                TokenWatcher.this.released();
            }
        }
    };
    /* access modifiers changed from: private */
    public String mTag;
    /* access modifiers changed from: private */
    public WeakHashMap<IBinder, Death> mTokens = new WeakHashMap<>();

    public abstract void acquired();

    public abstract void released();

    public TokenWatcher(Handler h, String tag) {
        this.mHandler = h;
        this.mTag = tag != null ? tag : "TokenWatcher";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void acquire(android.os.IBinder r5, java.lang.String r6) {
        /*
            r4 = this;
            java.util.WeakHashMap<android.os.IBinder, android.os.TokenWatcher$Death> r0 = r4.mTokens
            monitor-enter(r0)
            java.util.WeakHashMap<android.os.IBinder, android.os.TokenWatcher$Death> r1 = r4.mTokens     // Catch:{ all -> 0x0033 }
            boolean r1 = r1.containsKey(r5)     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x000d:
            java.util.WeakHashMap<android.os.IBinder, android.os.TokenWatcher$Death> r1 = r4.mTokens     // Catch:{ all -> 0x0033 }
            int r1 = r1.size()     // Catch:{ all -> 0x0033 }
            android.os.TokenWatcher$Death r2 = new android.os.TokenWatcher$Death     // Catch:{ all -> 0x0033 }
            r2.<init>(r5, r6)     // Catch:{ all -> 0x0033 }
            r3 = 0
            r5.linkToDeath(r2, r3)     // Catch:{ RemoteException -> 0x0030 }
            java.util.WeakHashMap<android.os.IBinder, android.os.TokenWatcher$Death> r3 = r4.mTokens     // Catch:{ all -> 0x0033 }
            r3.put(r5, r2)     // Catch:{ all -> 0x0033 }
            if (r1 != 0) goto L_0x002e
            boolean r3 = r4.mAcquired     // Catch:{ all -> 0x0033 }
            if (r3 != 0) goto L_0x002e
            r3 = 1
            r4.sendNotificationLocked(r3)     // Catch:{ all -> 0x0033 }
            r4.mAcquired = r3     // Catch:{ all -> 0x0033 }
        L_0x002e:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x0030:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x0033:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.TokenWatcher.acquire(android.os.IBinder, java.lang.String):void");
    }

    public void cleanup(IBinder token, boolean unlink) {
        synchronized (this.mTokens) {
            Death d = this.mTokens.remove(token);
            if (unlink && d != null) {
                d.token.unlinkToDeath(d, 0);
                d.token = null;
            }
            if (this.mTokens.size() == 0 && this.mAcquired) {
                sendNotificationLocked(false);
                this.mAcquired = false;
            }
        }
    }

    public void release(IBinder token) {
        cleanup(token, true);
    }

    public boolean isAcquired() {
        boolean z;
        synchronized (this.mTokens) {
            z = this.mAcquired;
        }
        return z;
    }

    public void dump() {
        Iterator<String> it = dumpInternal().iterator();
        while (it.hasNext()) {
            Log.i(this.mTag, it.next());
        }
    }

    public void dump(PrintWriter pw) {
        Iterator<String> it = dumpInternal().iterator();
        while (it.hasNext()) {
            pw.println(it.next());
        }
    }

    private ArrayList<String> dumpInternal() {
        ArrayList<String> a = new ArrayList<>();
        synchronized (this.mTokens) {
            Set<IBinder> keys = this.mTokens.keySet();
            a.add("Token count: " + this.mTokens.size());
            int i = 0;
            for (IBinder b : keys) {
                a.add("[" + i + "] " + this.mTokens.get(b).tag + " - " + b);
                i++;
            }
        }
        return a;
    }

    private void sendNotificationLocked(boolean on) {
        int value = on;
        if (this.mNotificationQueue == -1) {
            this.mNotificationQueue = (int) value;
            this.mHandler.post(this.mNotificationTask);
        } else if (this.mNotificationQueue != value) {
            this.mNotificationQueue = -1;
            this.mHandler.removeCallbacks(this.mNotificationTask);
        }
    }

    private class Death implements IBinder.DeathRecipient {
        String tag;
        IBinder token;

        Death(IBinder token2, String tag2) {
            this.token = token2;
            this.tag = tag2;
        }

        public void binderDied() {
            TokenWatcher.this.cleanup(this.token, false);
        }

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            try {
                if (this.token != null) {
                    String access$200 = TokenWatcher.this.mTag;
                    Log.w(access$200, "cleaning up leaked reference: " + this.tag);
                    TokenWatcher.this.release(this.token);
                }
            } finally {
                super.finalize();
            }
        }
    }
}
