package android.content;

import android.accounts.Account;
import android.content.ISyncAdapter;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractThreadedSyncAdapter {
    /* access modifiers changed from: private */
    public static final boolean ENABLE_LOG = (Build.IS_DEBUGGABLE && Log.isLoggable(TAG, 3));
    @Deprecated
    public static final int LOG_SYNC_DETAILS = 2743;
    private static final String TAG = "SyncAdapter";
    /* access modifiers changed from: private */
    public boolean mAllowParallelSyncs;
    /* access modifiers changed from: private */
    public final boolean mAutoInitialize;
    /* access modifiers changed from: private */
    public final Context mContext;
    private final ISyncAdapterImpl mISyncAdapterImpl;
    /* access modifiers changed from: private */
    public final AtomicInteger mNumSyncStarts;
    /* access modifiers changed from: private */
    public final Object mSyncThreadLock;
    /* access modifiers changed from: private */
    public final HashMap<Account, SyncThread> mSyncThreads;

    public abstract void onPerformSync(Account account, Bundle bundle, String str, ContentProviderClient contentProviderClient, SyncResult syncResult);

    public AbstractThreadedSyncAdapter(Context context, boolean autoInitialize) {
        this(context, autoInitialize, false);
    }

    public AbstractThreadedSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        this.mSyncThreads = new HashMap<>();
        this.mSyncThreadLock = new Object();
        this.mContext = context;
        this.mISyncAdapterImpl = new ISyncAdapterImpl();
        this.mNumSyncStarts = new AtomicInteger(0);
        this.mAutoInitialize = autoInitialize;
        this.mAllowParallelSyncs = allowParallelSyncs;
    }

    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: private */
    public Account toSyncKey(Account account) {
        if (this.mAllowParallelSyncs) {
            return account;
        }
        return null;
    }

    private class ISyncAdapterImpl extends ISyncAdapter.Stub {
        private ISyncAdapterImpl() {
        }

        public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback cb) {
            Handler.getMain().sendMessage(PooledLambda.obtainMessage($$Lambda$AbstractThreadedSyncAdapter$ISyncAdapterImpl$L6ZtOCe8gjKwJj0908ytPlrD8Rc.INSTANCE, AbstractThreadedSyncAdapter.this, cb));
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0087, code lost:
            if (android.content.AbstractThreadedSyncAdapter.access$100() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0089, code lost:
            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e5, code lost:
            if (r0 == false) goto L_0x00ec;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
            r14.onFinished(android.content.SyncResult.ALREADY_IN_PROGRESS);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00f0, code lost:
            if (android.content.AbstractThreadedSyncAdapter.access$100() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f2, code lost:
            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
            return;
         */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x011e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void startSync(android.content.ISyncContext r18, java.lang.String r19, android.accounts.Account r20, android.os.Bundle r21) {
            /*
                r17 = this;
                r1 = r17
                r10 = r19
                r11 = r20
                r12 = r21
                boolean r0 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r0 == 0) goto L_0x003a
                if (r12 == 0) goto L_0x0013
                r21.size()
            L_0x0013:
                java.lang.String r0 = "SyncAdapter"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "startSync() start "
                r2.append(r3)
                r2.append(r10)
                java.lang.String r3 = " "
                r2.append(r3)
                r2.append(r11)
                java.lang.String r3 = " "
                r2.append(r3)
                r2.append(r12)
                java.lang.String r2 = r2.toString()
                android.util.Log.d(r0, r2)
            L_0x003a:
                android.content.SyncContext r0 = new android.content.SyncContext     // Catch:{ Error | RuntimeException -> 0x0106, all -> 0x0102 }
                r13 = r18
                r0.<init>(r13)     // Catch:{ Error | RuntimeException -> 0x0100 }
                r14 = r0
                android.content.AbstractThreadedSyncAdapter r0 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ Error | RuntimeException -> 0x0100 }
                android.accounts.Account r0 = r0.toSyncKey(r11)     // Catch:{ Error | RuntimeException -> 0x0100 }
                r15 = r0
                android.content.AbstractThreadedSyncAdapter r0 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ Error | RuntimeException -> 0x0100 }
                java.lang.Object r16 = r0.mSyncThreadLock     // Catch:{ Error | RuntimeException -> 0x0100 }
                monitor-enter(r16)     // Catch:{ Error | RuntimeException -> 0x0100 }
                android.content.AbstractThreadedSyncAdapter r0 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00fb }
                java.util.HashMap r0 = r0.mSyncThreads     // Catch:{ all -> 0x00fb }
                boolean r0 = r0.containsKey(r15)     // Catch:{ all -> 0x00fb }
                r2 = 1
                if (r0 != 0) goto L_0x00d5
                android.content.AbstractThreadedSyncAdapter r0 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00fb }
                boolean r0 = r0.mAutoInitialize     // Catch:{ all -> 0x00fb }
                if (r0 == 0) goto L_0x009c
                if (r12 == 0) goto L_0x009c
                java.lang.String r0 = "initialize"
                r3 = 0
                boolean r0 = r12.getBoolean(r0, r3)     // Catch:{ all -> 0x00fb }
                if (r0 == 0) goto L_0x009c
                int r0 = android.content.ContentResolver.getIsSyncable(r11, r10)     // Catch:{ all -> 0x0092 }
                if (r0 >= 0) goto L_0x0079
                android.content.ContentResolver.setIsSyncable(r11, r10, r2)     // Catch:{ all -> 0x0092 }
            L_0x0079:
                android.content.SyncResult r0 = new android.content.SyncResult     // Catch:{ all -> 0x00fb }
                r0.<init>()     // Catch:{ all -> 0x00fb }
                r14.onFinished(r0)     // Catch:{ all -> 0x00fb }
                monitor-exit(r16)     // Catch:{ all -> 0x00fb }
                boolean r0 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r0 == 0) goto L_0x0091
                java.lang.String r0 = "SyncAdapter"
                java.lang.String r2 = "startSync() finishing"
                android.util.Log.d(r0, r2)
            L_0x0091:
                return
            L_0x0092:
                r0 = move-exception
                android.content.SyncResult r2 = new android.content.SyncResult     // Catch:{ all -> 0x00fb }
                r2.<init>()     // Catch:{ all -> 0x00fb }
                r14.onFinished(r2)     // Catch:{ all -> 0x00fb }
                throw r0     // Catch:{ all -> 0x00fb }
            L_0x009c:
                android.content.AbstractThreadedSyncAdapter$SyncThread r0 = new android.content.AbstractThreadedSyncAdapter$SyncThread     // Catch:{ all -> 0x00fb }
                android.content.AbstractThreadedSyncAdapter r3 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00fb }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fb }
                r2.<init>()     // Catch:{ all -> 0x00fb }
                java.lang.String r4 = "SyncAdapterThread-"
                r2.append(r4)     // Catch:{ all -> 0x00fb }
                android.content.AbstractThreadedSyncAdapter r4 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00fb }
                java.util.concurrent.atomic.AtomicInteger r4 = r4.mNumSyncStarts     // Catch:{ all -> 0x00fb }
                int r4 = r4.incrementAndGet()     // Catch:{ all -> 0x00fb }
                r2.append(r4)     // Catch:{ all -> 0x00fb }
                java.lang.String r4 = r2.toString()     // Catch:{ all -> 0x00fb }
                r9 = 0
                r2 = r0
                r5 = r14
                r6 = r19
                r7 = r20
                r8 = r21
                r2.<init>(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00fb }
                android.content.AbstractThreadedSyncAdapter r2 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00fb }
                java.util.HashMap r2 = r2.mSyncThreads     // Catch:{ all -> 0x00fb }
                r2.put(r15, r0)     // Catch:{ all -> 0x00fb }
                r0.start()     // Catch:{ all -> 0x00fb }
                r2 = 0
                goto L_0x00e3
            L_0x00d5:
                boolean r0 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ all -> 0x00fb }
                if (r0 == 0) goto L_0x00e2
                java.lang.String r0 = "SyncAdapter"
                java.lang.String r3 = "  alreadyInProgress"
                android.util.Log.d(r0, r3)     // Catch:{ all -> 0x00fb }
            L_0x00e2:
            L_0x00e3:
                r0 = r2
                monitor-exit(r16)     // Catch:{ all -> 0x00fb }
                if (r0 == 0) goto L_0x00ec
                android.content.SyncResult r2 = android.content.SyncResult.ALREADY_IN_PROGRESS     // Catch:{ Error | RuntimeException -> 0x0100 }
                r14.onFinished(r2)     // Catch:{ Error | RuntimeException -> 0x0100 }
            L_0x00ec:
                boolean r0 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r0 == 0) goto L_0x00fa
                java.lang.String r0 = "SyncAdapter"
                java.lang.String r2 = "startSync() finishing"
                android.util.Log.d(r0, r2)
            L_0x00fa:
                return
            L_0x00fb:
                r0 = move-exception
                monitor-exit(r16)     // Catch:{ all -> 0x00fb }
                throw r0     // Catch:{ Error | RuntimeException -> 0x0100 }
            L_0x00fe:
                r0 = move-exception
                goto L_0x0118
            L_0x0100:
                r0 = move-exception
                goto L_0x0109
            L_0x0102:
                r0 = move-exception
                r13 = r18
                goto L_0x0118
            L_0x0106:
                r0 = move-exception
                r13 = r18
            L_0x0109:
                boolean r2 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ all -> 0x00fe }
                if (r2 == 0) goto L_0x0117
                java.lang.String r2 = "SyncAdapter"
                java.lang.String r3 = "startSync() caught exception"
                android.util.Log.d(r2, r3, r0)     // Catch:{ all -> 0x00fe }
            L_0x0117:
                throw r0     // Catch:{ all -> 0x00fe }
            L_0x0118:
                boolean r2 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r2 == 0) goto L_0x0126
                java.lang.String r2 = "SyncAdapter"
                java.lang.String r3 = "startSync() finishing"
                android.util.Log.d(r2, r3)
            L_0x0126:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.AbstractThreadedSyncAdapter.ISyncAdapterImpl.startSync(android.content.ISyncContext, java.lang.String, android.accounts.Account, android.os.Bundle):void");
        }

        public void cancelSync(ISyncContext syncContext) {
            SyncThread info = null;
            try {
                synchronized (AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                    Iterator it = AbstractThreadedSyncAdapter.this.mSyncThreads.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SyncThread current = (SyncThread) it.next();
                        if (current.mSyncContext.getSyncContextBinder() == syncContext.asBinder()) {
                            info = current;
                            break;
                        }
                    }
                }
                if (info != null) {
                    if (AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        Log.d(AbstractThreadedSyncAdapter.TAG, "cancelSync() " + info.mAuthority + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + info.mAccount);
                    }
                    if (AbstractThreadedSyncAdapter.this.mAllowParallelSyncs) {
                        AbstractThreadedSyncAdapter.this.onSyncCanceled(info);
                    } else {
                        AbstractThreadedSyncAdapter.this.onSyncCanceled();
                    }
                } else if (AbstractThreadedSyncAdapter.ENABLE_LOG) {
                    Log.w(AbstractThreadedSyncAdapter.TAG, "cancelSync() unknown context");
                }
                if (AbstractThreadedSyncAdapter.ENABLE_LOG) {
                    Log.d(AbstractThreadedSyncAdapter.TAG, "cancelSync() finishing");
                }
            } catch (Error | RuntimeException th) {
                try {
                    if (AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        Log.d(AbstractThreadedSyncAdapter.TAG, "cancelSync() caught exception", th);
                    }
                    throw th;
                } catch (Throwable th2) {
                    if (AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        Log.d(AbstractThreadedSyncAdapter.TAG, "cancelSync() finishing");
                    }
                    throw th2;
                }
            }
        }
    }

    private class SyncThread extends Thread {
        /* access modifiers changed from: private */
        public final Account mAccount;
        /* access modifiers changed from: private */
        public final String mAuthority;
        private final Bundle mExtras;
        /* access modifiers changed from: private */
        public final SyncContext mSyncContext;
        private final Account mThreadsKey;

        private SyncThread(String name, SyncContext syncContext, String authority, Account account, Bundle extras) {
            super(name);
            this.mSyncContext = syncContext;
            this.mAuthority = authority;
            this.mAccount = account;
            this.mExtras = extras;
            this.mThreadsKey = AbstractThreadedSyncAdapter.this.toSyncKey(account);
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
            	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
            	at java.util.ArrayList.get(ArrayList.java:435)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
            	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
            */
        /* JADX WARNING: Removed duplicated region for block: B:76:0x0102 A[Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec, all -> 0x00ea }] */
        /* JADX WARNING: Removed duplicated region for block: B:80:0x011b  */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x0124  */
        public void run() {
            /*
                r11 = this;
                r0 = 10
                android.os.Process.setThreadPriority(r0)
                boolean r0 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r0 == 0) goto L_0x0012
                java.lang.String r0 = "SyncAdapter"
                java.lang.String r1 = "Thread started"
                android.util.Log.d(r0, r1)
            L_0x0012:
                java.lang.String r0 = r11.mAuthority
                r1 = 128(0x80, double:6.32E-322)
                android.os.Trace.traceBegin(r1, r0)
                android.content.SyncResult r0 = new android.content.SyncResult
                r0.<init>()
                r3 = 0
                r9 = 1
                boolean r4 = r11.isCanceled()     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                if (r4 == 0) goto L_0x006a
                boolean r4 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                if (r4 == 0) goto L_0x0033
                java.lang.String r4 = "SyncAdapter"
                java.lang.String r5 = "Already canceled"
                android.util.Log.d(r4, r5)     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
            L_0x0033:
                android.os.Trace.traceEnd(r1)
                if (r3 == 0) goto L_0x003b
                r3.release()
            L_0x003b:
                boolean r1 = r11.isCanceled()
                if (r1 != 0) goto L_0x0046
                android.content.SyncContext r1 = r11.mSyncContext
                r1.onFinished(r0)
            L_0x0046:
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this
                java.lang.Object r4 = r1.mSyncThreadLock
                monitor-enter(r4)
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x0067 }
                java.util.HashMap r1 = r1.mSyncThreads     // Catch:{ all -> 0x0067 }
                android.accounts.Account r2 = r11.mThreadsKey     // Catch:{ all -> 0x0067 }
                r1.remove(r2)     // Catch:{ all -> 0x0067 }
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                boolean r1 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r1 == 0) goto L_0x0066
                java.lang.String r1 = "SyncAdapter"
                java.lang.String r2 = "Thread finished"
                android.util.Log.d(r1, r2)
            L_0x0066:
                return
            L_0x0067:
                r1 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                throw r1
            L_0x006a:
                boolean r4 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                if (r4 == 0) goto L_0x0077
                java.lang.String r4 = "SyncAdapter"
                java.lang.String r5 = "Calling onPerformSync..."
                android.util.Log.d(r4, r5)     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
            L_0x0077:
                android.content.AbstractThreadedSyncAdapter r4 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                android.content.Context r4 = r4.mContext     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                android.content.ContentResolver r4 = r4.getContentResolver()     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                java.lang.String r5 = r11.mAuthority     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                android.content.ContentProviderClient r4 = r4.acquireContentProviderClient((java.lang.String) r5)     // Catch:{ SecurityException -> 0x00fb, Error | RuntimeException -> 0x00ec }
                r10 = r4
                if (r10 == 0) goto L_0x00a2
                android.content.AbstractThreadedSyncAdapter r3 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
                android.accounts.Account r4 = r11.mAccount     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
                android.os.Bundle r5 = r11.mExtras     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
                java.lang.String r6 = r11.mAuthority     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
                r7 = r10
                r8 = r0
                r3.onPerformSync(r4, r5, r6, r7, r8)     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
                goto L_0x00a4
            L_0x0098:
                r4 = move-exception
                r3 = r10
                goto L_0x014d
            L_0x009c:
                r4 = move-exception
                r3 = r10
                goto L_0x00ed
            L_0x009f:
                r4 = move-exception
                r3 = r10
                goto L_0x00fc
            L_0x00a2:
                r0.databaseError = r9     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
            L_0x00a4:
                boolean r3 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
                if (r3 == 0) goto L_0x00b2
                java.lang.String r3 = "SyncAdapter"
                java.lang.String r4 = "onPerformSync done"
                android.util.Log.d(r3, r4)     // Catch:{ SecurityException -> 0x009f, Error | RuntimeException -> 0x009c, all -> 0x0098 }
            L_0x00b2:
                android.os.Trace.traceEnd(r1)
                if (r10 == 0) goto L_0x00ba
                r10.release()
            L_0x00ba:
                boolean r1 = r11.isCanceled()
                if (r1 != 0) goto L_0x00c5
                android.content.SyncContext r1 = r11.mSyncContext
                r1.onFinished(r0)
            L_0x00c5:
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this
                java.lang.Object r3 = r1.mSyncThreadLock
                monitor-enter(r3)
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00e7 }
                java.util.HashMap r1 = r1.mSyncThreads     // Catch:{ all -> 0x00e7 }
                android.accounts.Account r2 = r11.mThreadsKey     // Catch:{ all -> 0x00e7 }
                r1.remove(r2)     // Catch:{ all -> 0x00e7 }
                monitor-exit(r3)     // Catch:{ all -> 0x00e7 }
                boolean r1 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r1 == 0) goto L_0x00e5
                java.lang.String r1 = "SyncAdapter"
                java.lang.String r2 = "Thread finished"
                android.util.Log.d(r1, r2)
            L_0x00e5:
                r3 = r10
                goto L_0x0149
            L_0x00e7:
                r1 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x00e7 }
                throw r1
            L_0x00ea:
                r4 = move-exception
                goto L_0x014d
            L_0x00ec:
                r4 = move-exception
            L_0x00ed:
                boolean r5 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ all -> 0x00ea }
                if (r5 == 0) goto L_0x00fa
                java.lang.String r5 = "SyncAdapter"
                java.lang.String r6 = "caught exception"
                android.util.Log.d(r5, r6, r4)     // Catch:{ all -> 0x00ea }
            L_0x00fa:
                throw r4     // Catch:{ all -> 0x00ea }
            L_0x00fb:
                r4 = move-exception
            L_0x00fc:
                boolean r5 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG     // Catch:{ all -> 0x00ea }
                if (r5 == 0) goto L_0x0109
                java.lang.String r5 = "SyncAdapter"
                java.lang.String r6 = "SecurityException"
                android.util.Log.d(r5, r6, r4)     // Catch:{ all -> 0x00ea }
            L_0x0109:
                android.content.AbstractThreadedSyncAdapter r5 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x00ea }
                android.accounts.Account r6 = r11.mAccount     // Catch:{ all -> 0x00ea }
                android.os.Bundle r7 = r11.mExtras     // Catch:{ all -> 0x00ea }
                java.lang.String r8 = r11.mAuthority     // Catch:{ all -> 0x00ea }
                r5.onSecurityException(r6, r7, r8, r0)     // Catch:{ all -> 0x00ea }
                r0.databaseError = r9     // Catch:{ all -> 0x00ea }
                android.os.Trace.traceEnd(r1)
                if (r3 == 0) goto L_0x011e
                r3.release()
            L_0x011e:
                boolean r1 = r11.isCanceled()
                if (r1 != 0) goto L_0x0129
                android.content.SyncContext r1 = r11.mSyncContext
                r1.onFinished(r0)
            L_0x0129:
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this
                java.lang.Object r4 = r1.mSyncThreadLock
                monitor-enter(r4)
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x014a }
                java.util.HashMap r1 = r1.mSyncThreads     // Catch:{ all -> 0x014a }
                android.accounts.Account r2 = r11.mThreadsKey     // Catch:{ all -> 0x014a }
                r1.remove(r2)     // Catch:{ all -> 0x014a }
                monitor-exit(r4)     // Catch:{ all -> 0x014a }
                boolean r1 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r1 == 0) goto L_0x0149
                java.lang.String r1 = "SyncAdapter"
                java.lang.String r2 = "Thread finished"
                android.util.Log.d(r1, r2)
            L_0x0149:
                return
            L_0x014a:
                r1 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x014a }
                throw r1
            L_0x014d:
                android.os.Trace.traceEnd(r1)
                if (r3 == 0) goto L_0x0155
                r3.release()
            L_0x0155:
                boolean r1 = r11.isCanceled()
                if (r1 != 0) goto L_0x0160
                android.content.SyncContext r1 = r11.mSyncContext
                r1.onFinished(r0)
            L_0x0160:
                android.content.AbstractThreadedSyncAdapter r1 = android.content.AbstractThreadedSyncAdapter.this
                java.lang.Object r1 = r1.mSyncThreadLock
                monitor-enter(r1)
                android.content.AbstractThreadedSyncAdapter r2 = android.content.AbstractThreadedSyncAdapter.this     // Catch:{ all -> 0x0181 }
                java.util.HashMap r2 = r2.mSyncThreads     // Catch:{ all -> 0x0181 }
                android.accounts.Account r5 = r11.mThreadsKey     // Catch:{ all -> 0x0181 }
                r2.remove(r5)     // Catch:{ all -> 0x0181 }
                monitor-exit(r1)     // Catch:{ all -> 0x0181 }
                boolean r1 = android.content.AbstractThreadedSyncAdapter.ENABLE_LOG
                if (r1 == 0) goto L_0x0180
                java.lang.String r1 = "SyncAdapter"
                java.lang.String r2 = "Thread finished"
                android.util.Log.d(r1, r2)
            L_0x0180:
                throw r4
            L_0x0181:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0181 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.AbstractThreadedSyncAdapter.SyncThread.run():void");
        }

        private boolean isCanceled() {
            return Thread.currentThread().isInterrupted();
        }
    }

    public final IBinder getSyncAdapterBinder() {
        return this.mISyncAdapterImpl.asBinder();
    }

    /* access modifiers changed from: private */
    public void handleOnUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback cb) {
        boolean doSync;
        try {
            doSync = onUnsyncableAccount();
        } catch (RuntimeException e) {
            Log.e(TAG, "Exception while calling onUnsyncableAccount, assuming 'true'", e);
            doSync = true;
        }
        try {
            cb.onUnsyncableAccountDone(doSync);
        } catch (RemoteException e2) {
            Log.e(TAG, "Could not report result of onUnsyncableAccount", e2);
        }
    }

    public boolean onUnsyncableAccount() {
        return true;
    }

    public void onSecurityException(Account account, Bundle extras, String authority, SyncResult syncResult) {
    }

    public void onSyncCanceled() {
        SyncThread syncThread;
        synchronized (this.mSyncThreadLock) {
            syncThread = this.mSyncThreads.get((Object) null);
        }
        if (syncThread != null) {
            syncThread.interrupt();
        }
    }

    public void onSyncCanceled(Thread thread) {
        thread.interrupt();
    }
}
