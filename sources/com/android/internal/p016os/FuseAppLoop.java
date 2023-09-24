package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Handler;
import android.p007os.Message;
import android.p007os.ParcelFileDescriptor;
import android.p007os.ProxyFileDescriptorCallback;
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

/* renamed from: com.android.internal.os.FuseAppLoop */
/* loaded from: classes4.dex */
public class FuseAppLoop implements Handler.Callback {
    private static final int ARGS_POOL_SIZE = 50;
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
    @GuardedBy({"mLock"})
    private long mInstance;
    private final int mMountPointId;
    private final Thread mThread;
    private static final String TAG = "FuseAppLoop";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final ThreadFactory sDefaultThreadFactory = new ThreadFactory() { // from class: com.android.internal.os.FuseAppLoop.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable r) {
            return new Thread(r, FuseAppLoop.TAG);
        }
    };
    private final Object mLock = new Object();
    @GuardedBy({"mLock"})
    private final SparseArray<CallbackEntry> mCallbackMap = new SparseArray<>();
    @GuardedBy({"mLock"})
    private final BytesMap mBytesMap = new BytesMap();
    @GuardedBy({"mLock"})
    private final LinkedList<Args> mArgsPool = new LinkedList<>();
    @GuardedBy({"mLock"})
    private int mNextInode = 2;

    /* renamed from: com.android.internal.os.FuseAppLoop$UnmountedException */
    /* loaded from: classes4.dex */
    public static class UnmountedException extends Exception {
    }

    native void native_delete(long j);

    native long native_new(int i);

    native void native_replyGetAttr(long j, long j2, long j3, long j4);

    native void native_replyLookup(long j, long j2, long j3, long j4);

    native void native_replyOpen(long j, long j2, long j3);

    native void native_replyRead(long j, long j2, int i, byte[] bArr);

    native void native_replySimple(long j, long j2, int i);

    native void native_replyWrite(long j, long j2, int i);

    native void native_start(long j);

    public FuseAppLoop(int mountPointId, ParcelFileDescriptor fd, ThreadFactory factory) {
        this.mMountPointId = mountPointId;
        factory = factory == null ? sDefaultThreadFactory : factory;
        this.mInstance = native_new(fd.detachFd());
        this.mThread = factory.newThread(new Runnable() { // from class: com.android.internal.os.-$$Lambda$FuseAppLoop$e9Yru2f_btesWlxIgerkPnHibpg
            @Override // java.lang.Runnable
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
            fuseAppLoop.mInstance = 0L;
            fuseAppLoop.mBytesMap.clear();
        }
    }

    public int registerCallback(ProxyFileDescriptorCallback callback, Handler handler) throws FuseUnavailableMountException {
        int id;
        synchronized (this.mLock) {
            Preconditions.checkNotNull(callback);
            Preconditions.checkNotNull(handler);
            Preconditions.checkState(this.mCallbackMap.size() < 2147483645, "Too many opened files.");
            Preconditions.checkArgument(Thread.currentThread().getId() != handler.getLooper().getThread().getId(), "Handler must be different from the current thread");
            if (this.mInstance == 0) {
                throw new FuseUnavailableMountException(this.mMountPointId);
            }
            do {
                id = this.mNextInode;
                this.mNextInode++;
                if (this.mNextInode < 0) {
                    this.mNextInode = 2;
                }
            } while (this.mCallbackMap.get(id) != null);
            this.mCallbackMap.put(id, new CallbackEntry(callback, new Handler(handler.getLooper(), this)));
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01cf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v11 */
    /* JADX WARN: Type inference failed for: r16v12 */
    /* JADX WARN: Type inference failed for: r16v16 */
    /* JADX WARN: Type inference failed for: r16v17 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v21 */
    /* JADX WARN: Type inference failed for: r16v22 */
    /* JADX WARN: Type inference failed for: r16v23 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    @Override // android.p007os.Handler.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean handleMessage(Message msg) {
        boolean z;
        long unique;
        int i;
        ?? r16;
        long unique2;
        Object obj;
        ?? r162;
        Object obj2;
        ?? r163;
        Object obj3;
        ?? r164;
        Object obj4;
        Args args = (Args) msg.obj;
        CallbackEntry entry = args.entry;
        long inode = args.inode;
        long unique3 = args.unique;
        int size = args.size;
        long offset = args.offset;
        byte[] data = args.data;
        try {
            i = msg.what;
            r16 = 0;
            r164 = 0;
            r163 = 0;
            r162 = 0;
            try {
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            z = true;
            unique = unique3;
        }
        if (i == 1) {
            z = true;
            unique2 = unique3;
            long fileSize = entry.callback.onGetSize();
            Object obj5 = this.mLock;
            try {
                synchronized (obj5) {
                    try {
                        if (this.mInstance != 0) {
                            obj = obj5;
                            native_replyLookup(this.mInstance, unique2, inode, fileSize);
                        } else {
                            obj = obj5;
                        }
                        recycleLocked(args);
                    } catch (Throwable th) {
                        th = th;
                        r16 = obj5;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else if (i != 3) {
            try {
            } catch (Exception e3) {
                e = e3;
            }
            if (i == 18) {
                z = true;
                entry.callback.onRelease();
                synchronized (this.mLock) {
                    if (this.mInstance != 0) {
                        native_replySimple(this.mInstance, unique3, 0);
                    }
                    this.mBytesMap.stopUsing(entry.getThreadId());
                    recycleLocked(args);
                }
            } else if (i != 20) {
                switch (i) {
                    case 15:
                        z = true;
                        try {
                            int readSize = entry.callback.onRead(offset, size, data);
                            Object obj6 = this.mLock;
                            try {
                                synchronized (obj6) {
                                    try {
                                        if (this.mInstance != 0) {
                                            obj4 = obj6;
                                            native_replyRead(this.mInstance, unique3, readSize, data);
                                        } else {
                                            obj4 = obj6;
                                        }
                                        recycleLocked(args);
                                        break;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        r164 = obj6;
                                        throw th;
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            unique = unique3;
                            break;
                        }
                    case 16:
                        try {
                            int writeSize = entry.callback.onWrite(offset, size, data);
                            Object obj7 = this.mLock;
                            synchronized (obj7) {
                                try {
                                    try {
                                        if (this.mInstance != 0) {
                                            obj3 = obj7;
                                            z = true;
                                            native_replyWrite(this.mInstance, unique3, writeSize);
                                        } else {
                                            obj3 = obj7;
                                            z = true;
                                        }
                                        recycleLocked(args);
                                        break;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        r163 = obj7;
                                        z = true;
                                        try {
                                            throw th;
                                        } catch (Exception e5) {
                                            e = e5;
                                            unique = unique3;
                                            Exception error = e;
                                            synchronized (this.mLock) {
                                            }
                                        }
                                    }
                                } catch (Throwable th6) {
                                    th = th6;
                                    throw th;
                                }
                            }
                        } catch (Exception e6) {
                            e = e6;
                            z = true;
                            unique = unique3;
                        }
                        break;
                    default:
                        try {
                            throw new IllegalArgumentException("Unknown FUSE command: " + msg.what);
                        } catch (Exception e7) {
                            e = e7;
                            z = true;
                            unique = unique3;
                            break;
                        }
                }
                Exception error2 = e;
                synchronized (this.mLock) {
                    try {
                        try {
                            Log.m69e(TAG, "", error2);
                            replySimpleLocked(unique, getError(error2));
                            recycleLocked(args);
                            return z;
                        } catch (Throwable th7) {
                            th = th7;
                            throw th;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        throw th;
                    }
                }
            } else {
                z = true;
                entry.callback.onFsync();
                synchronized (this.mLock) {
                    if (this.mInstance != 0) {
                        native_replySimple(this.mInstance, unique3, 0);
                    }
                    recycleLocked(args);
                }
            }
            unique2 = unique3;
        } else {
            z = true;
            try {
                long fileSize2 = entry.callback.onGetSize();
                try {
                    Object obj8 = this.mLock;
                    try {
                        synchronized (obj8) {
                            try {
                                if (this.mInstance != 0) {
                                    obj2 = obj8;
                                    unique2 = unique3;
                                    native_replyGetAttr(this.mInstance, unique3, inode, fileSize2);
                                } else {
                                    obj2 = obj8;
                                    unique2 = unique3;
                                }
                                recycleLocked(args);
                            } catch (Throwable th9) {
                                th = th9;
                                r162 = obj8;
                                throw th;
                            }
                        }
                    } catch (Throwable th10) {
                        th = th10;
                    }
                } catch (Exception e8) {
                    e = e8;
                    unique = unique3;
                }
            } catch (Exception e9) {
                e = e9;
                unique = unique3;
            }
        }
        return z;
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
            } catch (Exception error) {
                replySimpleLocked(unique, getError(error));
            }
            if (!args.entry.handler.sendMessage(Message.obtain(args.entry.handler, command, 0, 0, args))) {
                throw new ErrnoException("onCommand", OsConstants.EBADF);
            }
        }
    }

    @UnsupportedAppUsage
    private byte[] onOpen(long unique, long inode) {
        CallbackEntry entry;
        synchronized (this.mLock) {
            try {
                entry = getCallbackEntryOrThrowLocked(inode);
            } catch (ErrnoException error) {
                replySimpleLocked(unique, getError(error));
            }
            if (entry.opened) {
                throw new ErrnoException("onOpen", OsConstants.EMFILE);
            }
            if (this.mInstance != 0) {
                native_replyOpen(this.mInstance, unique, inode);
                entry.opened = true;
                return this.mBytesMap.startUsing(entry.getThreadId());
            }
            return null;
        }
    }

    private static int getError(Exception error) {
        int errno;
        if ((error instanceof ErrnoException) && (errno = ((ErrnoException) error).errno) != OsConstants.ENOSYS) {
            return -errno;
        }
        return -OsConstants.EBADF;
    }

    @GuardedBy({"mLock"})
    private CallbackEntry getCallbackEntryOrThrowLocked(long inode) throws ErrnoException {
        CallbackEntry entry = this.mCallbackMap.get(checkInode(inode));
        if (entry == null) {
            throw new ErrnoException("getCallbackEntryOrThrowLocked", OsConstants.ENOENT);
        }
        return entry;
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
        Preconditions.checkArgumentInRange(inode, 2L, 2147483647L, "checkInode");
        return (int) inode;
    }

    /* renamed from: com.android.internal.os.FuseAppLoop$CallbackEntry */
    /* loaded from: classes4.dex */
    private static class CallbackEntry {
        final ProxyFileDescriptorCallback callback;
        final Handler handler;
        boolean opened;

        CallbackEntry(ProxyFileDescriptorCallback callback, Handler handler) {
            this.callback = (ProxyFileDescriptorCallback) Preconditions.checkNotNull(callback);
            this.handler = (Handler) Preconditions.checkNotNull(handler);
        }

        long getThreadId() {
            return this.handler.getLooper().getThread().getId();
        }
    }

    /* renamed from: com.android.internal.os.FuseAppLoop$BytesMapEntry */
    /* loaded from: classes4.dex */
    private static class BytesMapEntry {
        byte[] bytes;
        int counter;

        private BytesMapEntry() {
            this.counter = 0;
            this.bytes = new byte[131072];
        }
    }

    /* renamed from: com.android.internal.os.FuseAppLoop$BytesMap */
    /* loaded from: classes4.dex */
    private static class BytesMap {
        final Map<Long, BytesMapEntry> mEntries;

        private BytesMap() {
            this.mEntries = new HashMap();
        }

        byte[] startUsing(long threadId) {
            BytesMapEntry entry = this.mEntries.get(Long.valueOf(threadId));
            if (entry == null) {
                entry = new BytesMapEntry();
                this.mEntries.put(Long.valueOf(threadId), entry);
            }
            entry.counter++;
            return entry.bytes;
        }

        void stopUsing(long threadId) {
            BytesMapEntry entry = this.mEntries.get(Long.valueOf(threadId));
            Preconditions.checkNotNull(entry);
            entry.counter--;
            if (entry.counter <= 0) {
                this.mEntries.remove(Long.valueOf(threadId));
            }
        }

        void clear() {
            this.mEntries.clear();
        }
    }

    /* renamed from: com.android.internal.os.FuseAppLoop$Args */
    /* loaded from: classes4.dex */
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
