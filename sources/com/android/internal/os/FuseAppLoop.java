package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ProxyFileDescriptorCallback;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class FuseAppLoop implements Handler.Callback {
    private static final int ARGS_POOL_SIZE = 50;
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final int FUSE_FSYNC = 20;
    private static final int FUSE_GETATTR = 3;
    private static final int FUSE_LOOKUP = 1;
    private static final int FUSE_MAX_WRITE = 131072;
    private static final int FUSE_OK = 0;
    private static final int FUSE_OPEN = 14;
    private static final int FUSE_READ = 15;
    private static final int FUSE_RELEASE = 18;
    private static final int FUSE_WRITE = 16;
    private static final int MIN_INODE = 2;
    public static final int ROOT_INODE = 1;
    private static final String TAG = "FuseAppLoop";
    private static final ThreadFactory sDefaultThreadFactory = new ThreadFactory() {
        public Thread newThread(Runnable r) {
            return new Thread(r, FuseAppLoop.TAG);
        }
    };
    @GuardedBy({"mLock"})
    private final LinkedList<Args> mArgsPool = new LinkedList<>();
    @GuardedBy({"mLock"})
    private final BytesMap mBytesMap = new BytesMap();
    @GuardedBy({"mLock"})
    private final SparseArray<CallbackEntry> mCallbackMap = new SparseArray<>();
    @GuardedBy({"mLock"})
    private long mInstance;
    private final Object mLock = new Object();
    private final int mMountPointId;
    @GuardedBy({"mLock"})
    private int mNextInode = 2;
    private final Thread mThread;

    public static class UnmountedException extends Exception {
    }

    /* access modifiers changed from: package-private */
    public native void native_delete(long j);

    /* access modifiers changed from: package-private */
    public native long native_new(int i);

    /* access modifiers changed from: package-private */
    public native void native_replyGetAttr(long j, long j2, long j3, long j4);

    /* access modifiers changed from: package-private */
    public native void native_replyLookup(long j, long j2, long j3, long j4);

    /* access modifiers changed from: package-private */
    public native void native_replyOpen(long j, long j2, long j3);

    /* access modifiers changed from: package-private */
    public native void native_replyRead(long j, long j2, int i, byte[] bArr);

    /* access modifiers changed from: package-private */
    public native void native_replySimple(long j, long j2, int i);

    /* access modifiers changed from: package-private */
    public native void native_replyWrite(long j, long j2, int i);

    /* access modifiers changed from: package-private */
    public native void native_start(long j);

    public FuseAppLoop(int mountPointId, ParcelFileDescriptor fd, ThreadFactory factory) {
        this.mMountPointId = mountPointId;
        factory = factory == null ? sDefaultThreadFactory : factory;
        this.mInstance = native_new(fd.detachFd());
        this.mThread = factory.newThread(new Runnable() {
            public final void run() {
                FuseAppLoop.lambda$new$0(FuseAppLoop.this);
            }
        });
        this.mThread.start();
    }

    public static /* synthetic */ void lambda$new$0(FuseAppLoop fuseAppLoop) {
        fuseAppLoop.native_start(fuseAppLoop.mInstance);
        synchronized (fuseAppLoop.mLock) {
            fuseAppLoop.native_delete(fuseAppLoop.mInstance);
            fuseAppLoop.mInstance = 0;
            fuseAppLoop.mBytesMap.clear();
        }
    }

    public int registerCallback(ProxyFileDescriptorCallback callback, Handler handler) throws FuseUnavailableMountException {
        int id;
        synchronized (this.mLock) {
            Preconditions.checkNotNull(callback);
            Preconditions.checkNotNull(handler);
            boolean z = false;
            Preconditions.checkState(this.mCallbackMap.size() < 2147483645, "Too many opened files.");
            if (Thread.currentThread().getId() != handler.getLooper().getThread().getId()) {
                z = true;
            }
            Preconditions.checkArgument(z, "Handler must be different from the current thread");
            if (this.mInstance != 0) {
                do {
                    id = this.mNextInode;
                    this.mNextInode++;
                    if (this.mNextInode < 0) {
                        this.mNextInode = 2;
                    }
                } while (this.mCallbackMap.get(id) != null);
                this.mCallbackMap.put(id, new CallbackEntry(callback, new Handler(handler.getLooper(), (Handler.Callback) this)));
            } else {
                throw new FuseUnavailableMountException(this.mMountPointId);
            }
        }
        return id;
    }

    public void unregisterCallback(int id) {
        synchronized (this.mLock) {
            this.mCallbackMap.remove(id);
        }
    }

    public int getMountPointId() {
        return this.mMountPointId;
    }

    /* JADX WARNING: type inference failed for: r16v0 */
    /* JADX WARNING: type inference failed for: r16v1 */
    /* JADX WARNING: type inference failed for: r16v7 */
    /* JADX WARNING: type inference failed for: r16v13 */
    /* JADX WARNING: type inference failed for: r16v19 */
    /* JADX WARNING: type inference failed for: r16v26 */
    /* JADX WARNING: type inference failed for: r16v29 */
    /* JADX WARNING: type inference failed for: r16v32 */
    /* JADX WARNING: type inference failed for: r16v35 */
    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        	at java.util.ArrayList.get(ArrayList.java:433)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
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
    /* JADX WARNING: Multi-variable type inference failed */
    public boolean handleMessage(android.os.Message r26) {
        /*
            r25 = this;
            r10 = r25
            r11 = r26
            java.lang.Object r0 = r11.obj
            r12 = r0
            com.android.internal.os.FuseAppLoop$Args r12 = (com.android.internal.os.FuseAppLoop.Args) r12
            com.android.internal.os.FuseAppLoop$CallbackEntry r13 = r12.entry
            long r14 = r12.inode
            long r8 = r12.unique
            int r7 = r12.size
            long r4 = r12.offset
            byte[] r0 = r12.data
            r2 = r0
            r3 = 1
            int r0 = r11.what     // Catch:{ Exception -> 0x01c0 }
            r16 = 0
            if (r0 == r3) goto L_0x0185
            r1 = 3
            if (r0 == r1) goto L_0x0143
            r1 = 18
            if (r0 == r1) goto L_0x010e
            r1 = 20
            if (r0 == r1) goto L_0x00e7
            switch(r0) {
                case 15: goto L_0x00a7;
                case 16: goto L_0x0051;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x0044 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0044 }
            r1.<init>()     // Catch:{ Exception -> 0x0044 }
            java.lang.String r6 = "Unknown FUSE command: "
            r1.append(r6)     // Catch:{ Exception -> 0x0044 }
            int r6 = r11.what     // Catch:{ Exception -> 0x0044 }
            r1.append(r6)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0044 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0044 }
            throw r0     // Catch:{ Exception -> 0x0044 }
        L_0x0044:
            r0 = move-exception
            r11 = r2
            r20 = r3
            r18 = r7
            r23 = r8
            r21 = r14
            r14 = r4
            goto L_0x01cb
        L_0x0051:
            android.os.ProxyFileDescriptorCallback r0 = r13.callback     // Catch:{ Exception -> 0x009a }
            int r6 = r0.onWrite(r4, r7, r2)     // Catch:{ Exception -> 0x009a }
            java.lang.Object r1 = r10.mLock     // Catch:{ Exception -> 0x009a }
            monitor-enter(r1)     // Catch:{ Exception -> 0x009a }
            r18 = r4
            long r3 = r10.mInstance     // Catch:{ all -> 0x0087 }
            int r0 = (r3 > r16 ? 1 : (r3 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x0076
            long r3 = r10.mInstance     // Catch:{ all -> 0x0087 }
            r16 = r1
            r1 = r25
            r5 = r2
            r20 = 1
            r2 = r3
            r11 = r5
            r21 = r14
            r14 = r18
            r4 = r8
            r1.native_replyWrite(r2, r4, r6)     // Catch:{ all -> 0x0098 }
            goto L_0x007f
        L_0x0076:
            r16 = r1
            r11 = r2
            r21 = r14
            r14 = r18
            r20 = 1
        L_0x007f:
            r10.recycleLocked(r12)     // Catch:{ all -> 0x0098 }
            monitor-exit(r16)     // Catch:{ all -> 0x0098 }
            r18 = r7
            goto L_0x00d3
        L_0x0087:
            r0 = move-exception
            r16 = r1
            r11 = r2
            r21 = r14
            r14 = r18
            r20 = 1
        L_0x0091:
            monitor-exit(r16)     // Catch:{ all -> 0x0098 }
            throw r0     // Catch:{ Exception -> 0x0093 }
        L_0x0093:
            r0 = move-exception
            r18 = r7
            goto L_0x013f
        L_0x0098:
            r0 = move-exception
            goto L_0x0091
        L_0x009a:
            r0 = move-exception
            r11 = r2
            r20 = r3
            r21 = r14
            r14 = r4
            r18 = r7
            r23 = r8
            goto L_0x01cb
        L_0x00a7:
            r11 = r2
            r20 = r3
            r21 = r14
            r14 = r4
            android.os.ProxyFileDescriptorCallback r0 = r13.callback     // Catch:{ Exception -> 0x00e0 }
            int r6 = r0.onRead(r14, r7, r11)     // Catch:{ Exception -> 0x00e0 }
            java.lang.Object r4 = r10.mLock     // Catch:{ Exception -> 0x00e0 }
            monitor-enter(r4)     // Catch:{ Exception -> 0x00e0 }
            long r0 = r10.mInstance     // Catch:{ all -> 0x00d7 }
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x00ca
            long r2 = r10.mInstance     // Catch:{ all -> 0x00d7 }
            r1 = r25
            r16 = r4
            r4 = r8
            r18 = r7
            r7 = r11
            r1.native_replyRead(r2, r4, r6, r7)     // Catch:{ all -> 0x00de }
            goto L_0x00ce
        L_0x00ca:
            r16 = r4
            r18 = r7
        L_0x00ce:
            r10.recycleLocked(r12)     // Catch:{ all -> 0x00de }
            monitor-exit(r16)     // Catch:{ all -> 0x00de }
        L_0x00d3:
            r23 = r8
            goto L_0x01b3
        L_0x00d7:
            r0 = move-exception
            r16 = r4
            r18 = r7
        L_0x00dc:
            monitor-exit(r16)     // Catch:{ all -> 0x00de }
            throw r0     // Catch:{ Exception -> 0x013e }
        L_0x00de:
            r0 = move-exception
            goto L_0x00dc
        L_0x00e0:
            r0 = move-exception
            r18 = r7
            r23 = r8
            goto L_0x01cb
        L_0x00e7:
            r11 = r2
            r20 = r3
            r18 = r7
            r21 = r14
            r14 = r4
            android.os.ProxyFileDescriptorCallback r0 = r13.callback     // Catch:{ Exception -> 0x013e }
            r0.onFsync()     // Catch:{ Exception -> 0x013e }
            java.lang.Object r7 = r10.mLock     // Catch:{ Exception -> 0x013e }
            monitor-enter(r7)     // Catch:{ Exception -> 0x013e }
            long r0 = r10.mInstance     // Catch:{ all -> 0x010b }
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x0106
            long r2 = r10.mInstance     // Catch:{ all -> 0x010b }
            r6 = 0
            r1 = r25
            r4 = r8
            r1.native_replySimple(r2, r4, r6)     // Catch:{ all -> 0x010b }
        L_0x0106:
            r10.recycleLocked(r12)     // Catch:{ all -> 0x010b }
            monitor-exit(r7)     // Catch:{ all -> 0x010b }
            goto L_0x00d3
        L_0x010b:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x010b }
            throw r0     // Catch:{ Exception -> 0x013e }
        L_0x010e:
            r11 = r2
            r20 = r3
            r18 = r7
            r21 = r14
            r14 = r4
            android.os.ProxyFileDescriptorCallback r0 = r13.callback     // Catch:{ Exception -> 0x013e }
            r0.onRelease()     // Catch:{ Exception -> 0x013e }
            java.lang.Object r7 = r10.mLock     // Catch:{ Exception -> 0x013e }
            monitor-enter(r7)     // Catch:{ Exception -> 0x013e }
            long r0 = r10.mInstance     // Catch:{ all -> 0x013b }
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x012d
            long r2 = r10.mInstance     // Catch:{ all -> 0x013b }
            r6 = 0
            r1 = r25
            r4 = r8
            r1.native_replySimple(r2, r4, r6)     // Catch:{ all -> 0x013b }
        L_0x012d:
            com.android.internal.os.FuseAppLoop$BytesMap r0 = r10.mBytesMap     // Catch:{ all -> 0x013b }
            long r1 = r13.getThreadId()     // Catch:{ all -> 0x013b }
            r0.stopUsing(r1)     // Catch:{ all -> 0x013b }
            r10.recycleLocked(r12)     // Catch:{ all -> 0x013b }
            monitor-exit(r7)     // Catch:{ all -> 0x013b }
            goto L_0x00d3
        L_0x013b:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x013b }
            throw r0     // Catch:{ Exception -> 0x013e }
        L_0x013e:
            r0 = move-exception
        L_0x013f:
            r23 = r8
            goto L_0x01cb
        L_0x0143:
            r11 = r2
            r20 = r3
            r18 = r7
            r21 = r14
            r14 = r4
            android.os.ProxyFileDescriptorCallback r0 = r13.callback     // Catch:{ Exception -> 0x0181 }
            long r0 = r0.onGetSize()     // Catch:{ Exception -> 0x0181 }
            r6 = r8
            r8 = r0
            java.lang.Object r4 = r10.mLock     // Catch:{ Exception -> 0x017d }
            monitor-enter(r4)     // Catch:{ Exception -> 0x017d }
            long r0 = r10.mInstance     // Catch:{ all -> 0x0174 }
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x016b
            long r2 = r10.mInstance     // Catch:{ all -> 0x0174 }
            r1 = r25
            r16 = r4
            r4 = r6
            r23 = r6
            r6 = r21
            r1.native_replyGetAttr(r2, r4, r6, r8)     // Catch:{ all -> 0x017b }
            goto L_0x016f
        L_0x016b:
            r16 = r4
            r23 = r6
        L_0x016f:
            r10.recycleLocked(r12)     // Catch:{ all -> 0x017b }
            monitor-exit(r16)     // Catch:{ all -> 0x017b }
            goto L_0x01b3
        L_0x0174:
            r0 = move-exception
            r16 = r4
            r23 = r6
        L_0x0179:
            monitor-exit(r16)     // Catch:{ all -> 0x017b }
            throw r0     // Catch:{ Exception -> 0x01be }
        L_0x017b:
            r0 = move-exception
            goto L_0x0179
        L_0x017d:
            r0 = move-exception
            r23 = r6
            goto L_0x01cb
        L_0x0181:
            r0 = move-exception
            r23 = r8
            goto L_0x01cb
        L_0x0185:
            r11 = r2
            r20 = r3
            r18 = r7
            r23 = r8
            r21 = r14
            r14 = r4
            android.os.ProxyFileDescriptorCallback r0 = r13.callback     // Catch:{ Exception -> 0x01be }
            long r8 = r0.onGetSize()     // Catch:{ Exception -> 0x01be }
            java.lang.Object r6 = r10.mLock     // Catch:{ Exception -> 0x01be }
            monitor-enter(r6)     // Catch:{ Exception -> 0x01be }
            long r0 = r10.mInstance     // Catch:{ all -> 0x01b7 }
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x01ac
            long r2 = r10.mInstance     // Catch:{ all -> 0x01b7 }
            r1 = r25
            r4 = r23
            r16 = r6
            r6 = r21
            r1.native_replyLookup(r2, r4, r6, r8)     // Catch:{ all -> 0x01bc }
            goto L_0x01ae
        L_0x01ac:
            r16 = r6
        L_0x01ae:
            r10.recycleLocked(r12)     // Catch:{ all -> 0x01bc }
            monitor-exit(r16)     // Catch:{ all -> 0x01bc }
        L_0x01b3:
            r3 = r23
            goto L_0x01e3
        L_0x01b7:
            r0 = move-exception
            r16 = r6
        L_0x01ba:
            monitor-exit(r16)     // Catch:{ all -> 0x01bc }
            throw r0     // Catch:{ Exception -> 0x01be }
        L_0x01bc:
            r0 = move-exception
            goto L_0x01ba
        L_0x01be:
            r0 = move-exception
            goto L_0x01cb
        L_0x01c0:
            r0 = move-exception
            r11 = r2
            r20 = r3
            r18 = r7
            r23 = r8
            r21 = r14
            r14 = r4
        L_0x01cb:
            r1 = r0
            java.lang.Object r2 = r10.mLock
            monitor-enter(r2)
            java.lang.String r0 = "FuseAppLoop"
            java.lang.String r3 = ""
            android.util.Log.e(r0, r3, r1)     // Catch:{ all -> 0x01e4 }
            int r0 = getError(r1)     // Catch:{ all -> 0x01e4 }
            r3 = r23
            r10.replySimpleLocked(r3, r0)     // Catch:{ all -> 0x01e9 }
            r10.recycleLocked(r12)     // Catch:{ all -> 0x01e9 }
            monitor-exit(r2)     // Catch:{ all -> 0x01e9 }
        L_0x01e3:
            return r20
        L_0x01e4:
            r0 = move-exception
            r3 = r23
        L_0x01e7:
            monitor-exit(r2)     // Catch:{ all -> 0x01e9 }
            throw r0
        L_0x01e9:
            r0 = move-exception
            goto L_0x01e7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.FuseAppLoop.handleMessage(android.os.Message):boolean");
    }

    @UnsupportedAppUsage
    private void onCommand(int command, long unique, long inode, long offset, int size, byte[] data) {
        Args args;
        synchronized (this.mLock) {
            try {
                if (this.mArgsPool.size() == 0) {
                    args = new Args();
                } else {
                    args = this.mArgsPool.pop();
                }
                args.unique = unique;
                args.inode = inode;
                args.offset = offset;
                args.size = size;
                args.data = data;
                args.entry = getCallbackEntryOrThrowLocked(inode);
                if (!args.entry.handler.sendMessage(Message.obtain(args.entry.handler, command, 0, 0, args))) {
                    throw new ErrnoException("onCommand", OsConstants.EBADF);
                }
            } catch (Exception error) {
                replySimpleLocked(unique, getError(error));
            }
        }
    }

    @UnsupportedAppUsage
    private byte[] onOpen(long unique, long inode) {
        synchronized (this.mLock) {
            try {
                CallbackEntry entry = getCallbackEntryOrThrowLocked(inode);
                if (!entry.opened) {
                    if (this.mInstance != 0) {
                        native_replyOpen(this.mInstance, unique, inode);
                        entry.opened = true;
                        byte[] startUsing = this.mBytesMap.startUsing(entry.getThreadId());
                        return startUsing;
                    }
                    return null;
                }
                throw new ErrnoException("onOpen", OsConstants.EMFILE);
            } catch (ErrnoException error) {
                replySimpleLocked(unique, getError(error));
            }
        }
    }

    private static int getError(Exception error) {
        int errno;
        if (!(error instanceof ErrnoException) || (errno = ((ErrnoException) error).errno) == OsConstants.ENOSYS) {
            return -OsConstants.EBADF;
        }
        return -errno;
    }

    @GuardedBy({"mLock"})
    private CallbackEntry getCallbackEntryOrThrowLocked(long inode) throws ErrnoException {
        CallbackEntry entry = this.mCallbackMap.get(checkInode(inode));
        if (entry != null) {
            return entry;
        }
        throw new ErrnoException("getCallbackEntryOrThrowLocked", OsConstants.ENOENT);
    }

    @GuardedBy({"mLock"})
    private void recycleLocked(Args args) {
        if (this.mArgsPool.size() < 50) {
            this.mArgsPool.add(args);
        }
    }

    @GuardedBy({"mLock"})
    private void replySimpleLocked(long unique, int result) {
        if (this.mInstance != 0) {
            native_replySimple(this.mInstance, unique, result);
        }
    }

    private static int checkInode(long inode) {
        Preconditions.checkArgumentInRange(inode, 2, 2147483647L, "checkInode");
        return (int) inode;
    }

    private static class CallbackEntry {
        final ProxyFileDescriptorCallback callback;
        final Handler handler;
        boolean opened;

        CallbackEntry(ProxyFileDescriptorCallback callback2, Handler handler2) {
            this.callback = (ProxyFileDescriptorCallback) Preconditions.checkNotNull(callback2);
            this.handler = (Handler) Preconditions.checkNotNull(handler2);
        }

        /* access modifiers changed from: package-private */
        public long getThreadId() {
            return this.handler.getLooper().getThread().getId();
        }
    }

    private static class BytesMapEntry {
        byte[] bytes;
        int counter;

        private BytesMapEntry() {
            this.counter = 0;
            this.bytes = new byte[131072];
        }
    }

    private static class BytesMap {
        final Map<Long, BytesMapEntry> mEntries;

        private BytesMap() {
            this.mEntries = new HashMap();
        }

        /* access modifiers changed from: package-private */
        public byte[] startUsing(long threadId) {
            BytesMapEntry entry = this.mEntries.get(Long.valueOf(threadId));
            if (entry == null) {
                entry = new BytesMapEntry();
                this.mEntries.put(Long.valueOf(threadId), entry);
            }
            entry.counter++;
            return entry.bytes;
        }

        /* access modifiers changed from: package-private */
        public void stopUsing(long threadId) {
            BytesMapEntry entry = this.mEntries.get(Long.valueOf(threadId));
            Preconditions.checkNotNull(entry);
            entry.counter--;
            if (entry.counter <= 0) {
                this.mEntries.remove(Long.valueOf(threadId));
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.mEntries.clear();
        }
    }

    private static class Args {
        byte[] data;
        CallbackEntry entry;
        long inode;
        long offset;
        int size;
        long unique;

        private Args() {
        }
    }
}
