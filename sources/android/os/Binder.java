package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.util.ExceptionUtils;
import android.util.Log;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FunctionalUtils;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import libcore.io.IoUtils;
import libcore.util.NativeAllocationRegistry;

public class Binder implements IBinder {
    public static final boolean CHECK_PARCEL_SIZE = false;
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    public static boolean LOG_RUNTIME_EXCEPTION = false;
    private static final int NATIVE_ALLOCATION_SIZE = 500;
    static final String TAG = "Binder";
    public static final int UNSET_WORKSOURCE = -1;
    private static volatile String sDumpDisabled = null;
    private static BinderInternal.Observer sObserver = null;
    private static volatile boolean sTracingEnabled = false;
    private static volatile TransactionTracker sTransactionTracker = null;
    static volatile boolean sWarnOnBlocking = false;
    private static volatile BinderInternal.WorkSourceProvider sWorkSourceProvider = $$Lambda$Binder$IYUHVkWouPK_9CG2s8VwyWBt5_I.INSTANCE;
    private String mDescriptor;
    @UnsupportedAppUsage
    private final long mObject;
    private IInterface mOwner;

    @SystemApi
    public interface ProxyTransactListener {
        void onTransactEnded(Object obj);

        Object onTransactStarted(IBinder iBinder, int i);
    }

    public static final native void blockUntilThreadAvailable();

    public static final native long clearCallingIdentity();

    public static final native long clearCallingWorkSource();

    public static final native void flushPendingCommands();

    public static final native int getCallingPid();

    public static final native int getCallingUid();

    public static final native int getCallingWorkSourceUid();

    private static native long getFinalizer();

    private static native long getNativeBBinderHolder();

    /* access modifiers changed from: private */
    public static native long getNativeFinalizer();

    public static final native int getThreadStrictModePolicy();

    public static final native boolean isHandlingTransaction();

    public static final native void restoreCallingIdentity(long j);

    public static final native void restoreCallingWorkSource(long j);

    public static final native long setCallingWorkSourceUid(int i);

    public static final native void setThreadStrictModePolicy(int i);

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Binder.class.getClassLoader(), Binder.getNativeFinalizer(), 500);

        private NoImagePreloadHolder() {
        }
    }

    public static void enableTracing() {
        sTracingEnabled = true;
    }

    public static void disableTracing() {
        sTracingEnabled = false;
    }

    public static boolean isTracingEnabled() {
        return sTracingEnabled;
    }

    public static synchronized TransactionTracker getTransactionTracker() {
        TransactionTracker transactionTracker;
        synchronized (Binder.class) {
            if (sTransactionTracker == null) {
                sTransactionTracker = new TransactionTracker();
            }
            transactionTracker = sTransactionTracker;
        }
        return transactionTracker;
    }

    public static void setObserver(BinderInternal.Observer observer) {
        sObserver = observer;
    }

    public static void setWarnOnBlocking(boolean warnOnBlocking) {
        sWarnOnBlocking = warnOnBlocking;
    }

    public static IBinder allowBlocking(IBinder binder) {
        try {
            if (binder instanceof BinderProxy) {
                ((BinderProxy) binder).mWarnOnBlocking = false;
            } else if (!(binder == null || binder.getInterfaceDescriptor() == null || binder.queryLocalInterface(binder.getInterfaceDescriptor()) != null)) {
                Log.w(TAG, "Unable to allow blocking on interface " + binder);
            }
        } catch (RemoteException e) {
        }
        return binder;
    }

    public static IBinder defaultBlocking(IBinder binder) {
        if (binder instanceof BinderProxy) {
            ((BinderProxy) binder).mWarnOnBlocking = sWarnOnBlocking;
        }
        return binder;
    }

    public static void copyAllowBlocking(IBinder fromBinder, IBinder toBinder) {
        if ((fromBinder instanceof BinderProxy) && (toBinder instanceof BinderProxy)) {
            ((BinderProxy) toBinder).mWarnOnBlocking = ((BinderProxy) fromBinder).mWarnOnBlocking;
        }
    }

    public static final int getCallingUidOrThrow() {
        if (isHandlingTransaction()) {
            return getCallingUid();
        }
        throw new IllegalStateException("Thread is not in a binder transcation");
    }

    public static final UserHandle getCallingUserHandle() {
        return UserHandle.of(UserHandle.getUserId(getCallingUid()));
    }

    public static final void withCleanCallingIdentity(FunctionalUtils.ThrowingRunnable action) {
        long callingIdentity = clearCallingIdentity();
        try {
            action.runOrThrow();
            restoreCallingIdentity(callingIdentity);
            if (0 != 0) {
                throw ExceptionUtils.propagate((Throwable) null);
            }
        } catch (Throwable th) {
            restoreCallingIdentity(callingIdentity);
            if (0 != 0) {
                throw ExceptionUtils.propagate((Throwable) null);
            }
            throw th;
        }
    }

    public static final <T> T withCleanCallingIdentity(FunctionalUtils.ThrowingSupplier<T> action) {
        long callingIdentity = clearCallingIdentity();
        try {
            T orThrow = action.getOrThrow();
            restoreCallingIdentity(callingIdentity);
            if (0 == 0) {
                return orThrow;
            }
            throw ExceptionUtils.propagate((Throwable) null);
        } catch (Throwable th) {
            restoreCallingIdentity(callingIdentity);
            if (0 != 0) {
                throw ExceptionUtils.propagate((Throwable) null);
            }
            throw th;
        }
    }

    public static final void joinThreadPool() {
        BinderInternal.joinThreadPool();
    }

    public static final boolean isProxy(IInterface iface) {
        return iface.asBinder() != iface;
    }

    public Binder() {
        this((String) null);
    }

    public Binder(String descriptor) {
        this.mObject = getNativeBBinderHolder();
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mObject);
        this.mDescriptor = descriptor;
    }

    public void attachInterface(IInterface owner, String descriptor) {
        this.mOwner = owner;
        this.mDescriptor = descriptor;
    }

    public String getInterfaceDescriptor() {
        return this.mDescriptor;
    }

    public boolean pingBinder() {
        return true;
    }

    public boolean isBinderAlive() {
        return true;
    }

    public IInterface queryLocalInterface(String descriptor) {
        if (this.mDescriptor == null || !this.mDescriptor.equals(descriptor)) {
            return null;
        }
        return this.mOwner;
    }

    public static void setDumpDisabled(String msg) {
        sDumpDisabled = msg;
    }

    public static class PropagateWorkSourceTransactListener implements ProxyTransactListener {
        public Object onTransactStarted(IBinder binder, int transactionCode) {
            int uid = ThreadLocalWorkSource.getUid();
            if (uid != -1) {
                return Long.valueOf(Binder.setCallingWorkSourceUid(uid));
            }
            return null;
        }

        public void onTransactEnded(Object session) {
            if (session != null) {
                Binder.restoreCallingWorkSource(((Long) session).longValue());
            }
        }
    }

    @SystemApi
    public static void setProxyTransactListener(ProxyTransactListener listener) {
        BinderProxy.setTransactListener(listener);
    }

    /* access modifiers changed from: protected */
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        FileDescriptor fileDescriptor;
        int i = code;
        Parcel parcel = data;
        Parcel parcel2 = reply;
        if (i == 1598968902) {
            parcel2.writeString(getInterfaceDescriptor());
            return true;
        } else if (i == 1598311760) {
            ParcelFileDescriptor fd = data.readFileDescriptor();
            String[] args = data.readStringArray();
            if (fd != null) {
                try {
                    try {
                        dump(fd.getFileDescriptor(), args);
                        IoUtils.closeQuietly(fd);
                    } catch (Throwable th) {
                        th = th;
                        IoUtils.closeQuietly(fd);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    IoUtils.closeQuietly(fd);
                    throw th;
                }
            }
            if (parcel2 != null) {
                reply.writeNoException();
            } else {
                StrictMode.clearGatheredViolations();
            }
            return true;
        } else {
            if (i != 1598246212) {
                return false;
            }
            ParcelFileDescriptor in = data.readFileDescriptor();
            ParcelFileDescriptor out = data.readFileDescriptor();
            ParcelFileDescriptor err = data.readFileDescriptor();
            String[] args2 = data.readStringArray();
            ShellCallback shellCallback = ShellCallback.CREATOR.createFromParcel(parcel);
            ResultReceiver resultReceiver = ResultReceiver.CREATOR.createFromParcel(parcel);
            if (out != null) {
                if (in != null) {
                    try {
                        fileDescriptor = in.getFileDescriptor();
                    } catch (Throwable th3) {
                        IoUtils.closeQuietly(in);
                        IoUtils.closeQuietly(out);
                        IoUtils.closeQuietly(err);
                        if (parcel2 != null) {
                            reply.writeNoException();
                        } else {
                            StrictMode.clearGatheredViolations();
                        }
                        throw th3;
                    }
                } else {
                    fileDescriptor = null;
                }
                shellCommand(fileDescriptor, out.getFileDescriptor(), err != null ? err.getFileDescriptor() : out.getFileDescriptor(), args2, shellCallback, resultReceiver);
            }
            IoUtils.closeQuietly(in);
            IoUtils.closeQuietly(out);
            IoUtils.closeQuietly(err);
            if (parcel2 != null) {
                reply.writeNoException();
            } else {
                StrictMode.clearGatheredViolations();
            }
            return true;
        }
    }

    public String getTransactionName(int transactionCode) {
        return null;
    }

    public void dump(FileDescriptor fd, String[] args) {
        PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(fd));
        try {
            doDump(fd, pw, args);
        } finally {
            pw.flush();
        }
    }

    /* access modifiers changed from: package-private */
    public void doDump(FileDescriptor fd, PrintWriter pw, String[] args) {
        if (sDumpDisabled == null) {
            try {
                dump(fd, pw, args);
            } catch (SecurityException e) {
                pw.println("Security exception: " + e.getMessage());
                throw e;
            } catch (Throwable e2) {
                pw.println();
                pw.println("Exception occurred while dumping:");
                e2.printStackTrace(pw);
            }
        } else {
            pw.println(sDumpDisabled);
        }
    }

    public void dumpAsync(FileDescriptor fd, String[] args) {
        final PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(fd));
        final FileDescriptor fileDescriptor = fd;
        final String[] strArr = args;
        new Thread("Binder.dumpAsync") {
            public void run() {
                try {
                    Binder.this.dump(fileDescriptor, pw, strArr);
                } finally {
                    pw.flush();
                }
            }
        }.start();
    }

    /* access modifiers changed from: protected */
    public void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
    }

    public void shellCommand(FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, ResultReceiver resultReceiver) throws RemoteException {
        onShellCommand(in, out, err, args, callback, resultReceiver);
    }

    public void onShellCommand(FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, ResultReceiver resultReceiver) throws RemoteException {
        PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(err != null ? err : out));
        pw.println("No shell command implementation.");
        pw.flush();
        resultReceiver.send(0, (Bundle) null);
    }

    public final boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (data != null) {
            data.setDataPosition(0);
        }
        boolean r = onTransact(code, data, reply, flags);
        if (reply != null) {
            reply.setDataPosition(0);
        }
        return r;
    }

    public void linkToDeath(IBinder.DeathRecipient recipient, int flags) {
    }

    public boolean unlinkToDeath(IBinder.DeathRecipient recipient, int flags) {
        return true;
    }

    static void checkParcel(IBinder obj, int code, Parcel parcel, String msg) {
    }

    public static void setWorkSourceProvider(BinderInternal.WorkSourceProvider workSourceProvider) {
        if (workSourceProvider != null) {
            sWorkSourceProvider = workSourceProvider;
            return;
        }
        throw new IllegalArgumentException("workSourceProvider cannot be null");
    }

    @UnsupportedAppUsage
    private boolean execTransact(int code, long dataObj, long replyObj, int flags) {
        int callingUid = getCallingUid();
        long origWorkSource = ThreadLocalWorkSource.setUid(callingUid);
        try {
            boolean execTransactInternal = execTransactInternal(code, dataObj, replyObj, flags, callingUid);
            ThreadLocalWorkSource.restore(origWorkSource);
            return execTransactInternal;
        } catch (Throwable th) {
            Throwable th2 = th;
            ThreadLocalWorkSource.restore(origWorkSource);
            throw th2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005b, code lost:
        if (r4 != null) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005d, code lost:
        r4.callEnded(r5, r6.dataSize(), r7.dataSize(), sWorkSourceProvider.resolveWorkSourceUid(r6.readCallingWorkSourceUid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ac, code lost:
        if (r4 != null) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00af, code lost:
        checkParcel(r13, r14, r7, "Unreasonably large binder reply buffer");
        r7.recycle();
        r6.recycle();
        android.os.StrictMode.clearGatheredViolations();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00be, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean execTransactInternal(int r14, long r15, long r17, int r19, int r20) {
        /*
            r13 = this;
            r1 = r13
            r2 = r14
            r3 = r19
            com.android.internal.os.BinderInternal$Observer r4 = sObserver
            if (r4 == 0) goto L_0x000e
            r0 = -1
            com.android.internal.os.BinderInternal$CallSession r0 = r4.callStarted(r13, r14, r0)
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            r5 = r0
            android.os.Parcel r6 = android.os.Parcel.obtain((long) r15)
            android.os.Parcel r7 = android.os.Parcel.obtain((long) r17)
            boolean r0 = isTracingEnabled()
            r8 = r0
            r9 = 1
            if (r8 == 0) goto L_0x0052
            java.lang.String r0 = r13.getTransactionName(r14)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            r11.<init>()     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            java.lang.Class r12 = r13.getClass()     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            java.lang.String r12 = r12.getName()     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            r11.append(r12)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            java.lang.String r12 = ":"
            r11.append(r12)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            if (r0 == 0) goto L_0x003e
            r12 = r0
            goto L_0x0042
        L_0x003e:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r14)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
        L_0x0042:
            r11.append(r12)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            java.lang.String r11 = r11.toString()     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            android.os.Trace.traceBegin(r9, r11)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            goto L_0x0052
        L_0x004d:
            r0 = move-exception
            goto L_0x00bf
        L_0x0050:
            r0 = move-exception
            goto L_0x0073
        L_0x0052:
            boolean r0 = r13.onTransact(r14, r6, r7, r3)     // Catch:{ RemoteException | RuntimeException -> 0x0050 }
            if (r8 == 0) goto L_0x005b
            android.os.Trace.traceEnd(r9)
        L_0x005b:
            if (r4 == 0) goto L_0x00af
        L_0x005d:
            com.android.internal.os.BinderInternal$WorkSourceProvider r9 = sWorkSourceProvider
            int r10 = r6.readCallingWorkSourceUid()
            int r9 = r9.resolveWorkSourceUid(r10)
            int r10 = r6.dataSize()
            int r11 = r7.dataSize()
            r4.callEnded(r5, r10, r11, r9)
            goto L_0x00af
        L_0x0073:
            if (r4 == 0) goto L_0x0079
            r4.callThrewException(r5, r0)     // Catch:{ all -> 0x004d }
        L_0x0079:
            boolean r11 = LOG_RUNTIME_EXCEPTION     // Catch:{ all -> 0x004d }
            if (r11 == 0) goto L_0x0084
            java.lang.String r11 = "Binder"
            java.lang.String r12 = "Caught a RuntimeException from the binder stub implementation."
            android.util.Log.w(r11, r12, r0)     // Catch:{ all -> 0x004d }
        L_0x0084:
            r11 = r3 & 1
            if (r11 == 0) goto L_0x009c
            boolean r11 = r0 instanceof android.os.RemoteException     // Catch:{ all -> 0x004d }
            if (r11 == 0) goto L_0x0094
            java.lang.String r11 = "Binder"
            java.lang.String r12 = "Binder call failed."
            android.util.Log.w(r11, r12, r0)     // Catch:{ all -> 0x004d }
            goto L_0x00a6
        L_0x0094:
            java.lang.String r11 = "Binder"
            java.lang.String r12 = "Caught a RuntimeException from the binder stub implementation."
            android.util.Log.w(r11, r12, r0)     // Catch:{ all -> 0x004d }
            goto L_0x00a6
        L_0x009c:
            r11 = 0
            r7.setDataSize(r11)     // Catch:{ all -> 0x004d }
            r7.setDataPosition(r11)     // Catch:{ all -> 0x004d }
            r7.writeException(r0)     // Catch:{ all -> 0x004d }
        L_0x00a6:
            r0 = 1
            if (r8 == 0) goto L_0x00ac
            android.os.Trace.traceEnd(r9)
        L_0x00ac:
            if (r4 == 0) goto L_0x00af
            goto L_0x005d
        L_0x00af:
            java.lang.String r9 = "Unreasonably large binder reply buffer"
            checkParcel(r13, r14, r7, r9)
            r7.recycle()
            r6.recycle()
            android.os.StrictMode.clearGatheredViolations()
            return r0
        L_0x00bf:
            if (r8 == 0) goto L_0x00c4
            android.os.Trace.traceEnd(r9)
        L_0x00c4:
            if (r4 == 0) goto L_0x00db
            com.android.internal.os.BinderInternal$WorkSourceProvider r9 = sWorkSourceProvider
            int r10 = r6.readCallingWorkSourceUid()
            int r9 = r9.resolveWorkSourceUid(r10)
            int r10 = r6.dataSize()
            int r11 = r7.dataSize()
            r4.callEnded(r5, r10, r11, r9)
        L_0x00db:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.Binder.execTransactInternal(int, long, long, int, int):boolean");
    }
}
