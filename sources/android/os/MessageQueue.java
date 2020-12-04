package android.os;

import android.annotation.UnsupportedAppUsage;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class MessageQueue {
    private static final boolean DEBUG = false;
    private static final String TAG = "MessageQueue";
    private boolean mBlocked;
    private SparseArray<FileDescriptorRecord> mFileDescriptorRecords;
    @UnsupportedAppUsage
    private final ArrayList<IdleHandler> mIdleHandlers = new ArrayList<>();
    @UnsupportedAppUsage
    Message mMessages;
    @UnsupportedAppUsage
    private int mNextBarrierToken;
    private IdleHandler[] mPendingIdleHandlers;
    @UnsupportedAppUsage
    private long mPtr;
    @UnsupportedAppUsage
    private final boolean mQuitAllowed;
    private boolean mQuitting;

    public interface IdleHandler {
        boolean queueIdle();
    }

    public interface OnFileDescriptorEventListener {
        public static final int EVENT_ERROR = 4;
        public static final int EVENT_INPUT = 1;
        public static final int EVENT_OUTPUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Events {
        }

        int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i);
    }

    private static native void nativeDestroy(long j);

    private static native long nativeInit();

    private static native boolean nativeIsPolling(long j);

    @UnsupportedAppUsage
    private native void nativePollOnce(long j, int i);

    private static native void nativeSetFileDescriptorEvents(long j, int i, int i2);

    private static native void nativeWake(long j);

    MessageQueue(boolean quitAllowed) {
        this.mQuitAllowed = quitAllowed;
        this.mPtr = nativeInit();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mPtr != 0) {
            nativeDestroy(this.mPtr);
            this.mPtr = 0;
        }
    }

    public boolean isIdle() {
        boolean z;
        synchronized (this) {
            long now = SystemClock.uptimeMillis();
            if (this.mMessages != null) {
                if (now >= this.mMessages.when) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public void addIdleHandler(IdleHandler handler) {
        if (handler != null) {
            synchronized (this) {
                this.mIdleHandlers.add(handler);
            }
            return;
        }
        throw new NullPointerException("Can't add a null IdleHandler");
    }

    public void removeIdleHandler(IdleHandler handler) {
        synchronized (this) {
            this.mIdleHandlers.remove(handler);
        }
    }

    public boolean isPolling() {
        boolean isPollingLocked;
        synchronized (this) {
            isPollingLocked = isPollingLocked();
        }
        return isPollingLocked;
    }

    private boolean isPollingLocked() {
        return !this.mQuitting && nativeIsPolling(this.mPtr);
    }

    public void addOnFileDescriptorEventListener(FileDescriptor fd, int events, OnFileDescriptorEventListener listener) {
        if (fd == null) {
            throw new IllegalArgumentException("fd must not be null");
        } else if (listener != null) {
            synchronized (this) {
                updateOnFileDescriptorEventListenerLocked(fd, events, listener);
            }
        } else {
            throw new IllegalArgumentException("listener must not be null");
        }
    }

    public void removeOnFileDescriptorEventListener(FileDescriptor fd) {
        if (fd != null) {
            synchronized (this) {
                updateOnFileDescriptorEventListenerLocked(fd, 0, (OnFileDescriptorEventListener) null);
            }
            return;
        }
        throw new IllegalArgumentException("fd must not be null");
    }

    private void updateOnFileDescriptorEventListenerLocked(FileDescriptor fd, int events, OnFileDescriptorEventListener listener) {
        int fdNum = fd.getInt$();
        int index = -1;
        FileDescriptorRecord record = null;
        if (this.mFileDescriptorRecords != null && (index = this.mFileDescriptorRecords.indexOfKey(fdNum)) >= 0 && (record = this.mFileDescriptorRecords.valueAt(index)) != null && record.mEvents == events) {
            return;
        }
        if (events != 0) {
            int events2 = events | 4;
            if (record == null) {
                if (this.mFileDescriptorRecords == null) {
                    this.mFileDescriptorRecords = new SparseArray<>();
                }
                this.mFileDescriptorRecords.put(fdNum, new FileDescriptorRecord(fd, events2, listener));
            } else {
                record.mListener = listener;
                record.mEvents = events2;
                record.mSeq++;
            }
            nativeSetFileDescriptorEvents(this.mPtr, fdNum, events2);
        } else if (record != null) {
            record.mEvents = 0;
            this.mFileDescriptorRecords.removeAt(index);
            nativeSetFileDescriptorEvents(this.mPtr, fdNum, 0);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        r4 = r2.onFileDescriptorEvents(r0.mDescriptor, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        if (r4 == 0) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        r4 = r4 | 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0024, code lost:
        if (r4 == r1) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0026, code lost:
        monitor-enter(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r5 = r7.mFileDescriptorRecords.indexOfKey(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002d, code lost:
        if (r5 < 0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0035, code lost:
        if (r7.mFileDescriptorRecords.valueAt(r5) != r0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0039, code lost:
        if (r0.mSeq != r3) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003b, code lost:
        r0.mEvents = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003d, code lost:
        if (r4 != 0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003f, code lost:
        r7.mFileDescriptorRecords.removeAt(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0044, code lost:
        monitor-exit(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0049, code lost:
        return r4;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int dispatchEvents(int r8, int r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            android.util.SparseArray<android.os.MessageQueue$FileDescriptorRecord> r0 = r7.mFileDescriptorRecords     // Catch:{ all -> 0x004a }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x004a }
            android.os.MessageQueue$FileDescriptorRecord r0 = (android.os.MessageQueue.FileDescriptorRecord) r0     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x000e
            r1 = 0
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            return r1
        L_0x000e:
            int r1 = r0.mEvents     // Catch:{ all -> 0x004a }
            r9 = r9 & r1
            if (r9 != 0) goto L_0x0015
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            return r1
        L_0x0015:
            android.os.MessageQueue$OnFileDescriptorEventListener r2 = r0.mListener     // Catch:{ all -> 0x004a }
            int r3 = r0.mSeq     // Catch:{ all -> 0x004a }
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            java.io.FileDescriptor r4 = r0.mDescriptor
            int r4 = r2.onFileDescriptorEvents(r4, r9)
            if (r4 == 0) goto L_0x0024
            r4 = r4 | 4
        L_0x0024:
            if (r4 == r1) goto L_0x0049
            monitor-enter(r7)
            android.util.SparseArray<android.os.MessageQueue$FileDescriptorRecord> r5 = r7.mFileDescriptorRecords     // Catch:{ all -> 0x0046 }
            int r5 = r5.indexOfKey(r8)     // Catch:{ all -> 0x0046 }
            if (r5 < 0) goto L_0x0044
            android.util.SparseArray<android.os.MessageQueue$FileDescriptorRecord> r6 = r7.mFileDescriptorRecords     // Catch:{ all -> 0x0046 }
            java.lang.Object r6 = r6.valueAt(r5)     // Catch:{ all -> 0x0046 }
            if (r6 != r0) goto L_0x0044
            int r6 = r0.mSeq     // Catch:{ all -> 0x0046 }
            if (r6 != r3) goto L_0x0044
            r0.mEvents = r4     // Catch:{ all -> 0x0046 }
            if (r4 != 0) goto L_0x0044
            android.util.SparseArray<android.os.MessageQueue$FileDescriptorRecord> r6 = r7.mFileDescriptorRecords     // Catch:{ all -> 0x0046 }
            r6.removeAt(r5)     // Catch:{ all -> 0x0046 }
        L_0x0044:
            monitor-exit(r7)     // Catch:{ all -> 0x0046 }
            goto L_0x0049
        L_0x0046:
            r5 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0046 }
            throw r5
        L_0x0049:
            return r4
        L_0x004a:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.dispatchEvents(int, int):int");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0098, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0099, code lost:
        if (r6 >= r5) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x009b, code lost:
        r7 = r14.mPendingIdleHandlers[r6];
        r14.mPendingIdleHandlers[r6] = null;
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a8, code lost:
        r8 = r7.queueIdle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00aa, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ab, code lost:
        android.util.Log.wtf(TAG, "IdleHandler threw exception", r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c2, code lost:
        r5 = 0;
        r2 = 0;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Message next() {
        /*
            r14 = this;
            long r0 = r14.mPtr
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r3 = 0
            if (r2 != 0) goto L_0x000a
            return r3
        L_0x000a:
            r2 = -1
            r4 = 0
            r5 = r2
            r2 = r4
        L_0x000e:
            if (r2 == 0) goto L_0x0013
            android.os.Binder.flushPendingCommands()
        L_0x0013:
            r14.nativePollOnce(r0, r2)
            monitor-enter(r14)
            long r6 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00c6 }
            r8 = 0
            android.os.Message r9 = r14.mMessages     // Catch:{ all -> 0x00c6 }
            if (r9 == 0) goto L_0x0030
            android.os.Handler r10 = r9.target     // Catch:{ all -> 0x00c6 }
            if (r10 != 0) goto L_0x0030
        L_0x0024:
            r8 = r9
            android.os.Message r10 = r9.next     // Catch:{ all -> 0x00c6 }
            r9 = r10
            if (r9 == 0) goto L_0x0030
            boolean r10 = r9.isAsynchronous()     // Catch:{ all -> 0x00c6 }
            if (r10 == 0) goto L_0x0024
        L_0x0030:
            if (r9 == 0) goto L_0x0058
            long r10 = r9.when     // Catch:{ all -> 0x00c6 }
            int r10 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r10 >= 0) goto L_0x0044
            long r10 = r9.when     // Catch:{ all -> 0x00c6 }
            long r10 = r10 - r6
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            long r10 = java.lang.Math.min(r10, r12)     // Catch:{ all -> 0x00c6 }
            int r2 = (int) r10     // Catch:{ all -> 0x00c6 }
            goto L_0x0059
        L_0x0044:
            r14.mBlocked = r4     // Catch:{ all -> 0x00c6 }
            if (r8 == 0) goto L_0x004d
            android.os.Message r4 = r9.next     // Catch:{ all -> 0x00c6 }
            r8.next = r4     // Catch:{ all -> 0x00c6 }
            goto L_0x0051
        L_0x004d:
            android.os.Message r4 = r9.next     // Catch:{ all -> 0x00c6 }
            r14.mMessages = r4     // Catch:{ all -> 0x00c6 }
        L_0x0051:
            r9.next = r3     // Catch:{ all -> 0x00c6 }
            r9.markInUse()     // Catch:{ all -> 0x00c6 }
            monitor-exit(r14)     // Catch:{ all -> 0x00c6 }
            return r9
        L_0x0058:
            r2 = -1
        L_0x0059:
            boolean r10 = r14.mQuitting     // Catch:{ all -> 0x00c6 }
            if (r10 == 0) goto L_0x0062
            r14.dispose()     // Catch:{ all -> 0x00c6 }
            monitor-exit(r14)     // Catch:{ all -> 0x00c6 }
            return r3
        L_0x0062:
            if (r5 >= 0) goto L_0x0077
            android.os.Message r10 = r14.mMessages     // Catch:{ all -> 0x00c6 }
            if (r10 == 0) goto L_0x0070
            android.os.Message r10 = r14.mMessages     // Catch:{ all -> 0x00c6 }
            long r10 = r10.when     // Catch:{ all -> 0x00c6 }
            int r10 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r10 >= 0) goto L_0x0077
        L_0x0070:
            java.util.ArrayList<android.os.MessageQueue$IdleHandler> r10 = r14.mIdleHandlers     // Catch:{ all -> 0x00c6 }
            int r10 = r10.size()     // Catch:{ all -> 0x00c6 }
            r5 = r10
        L_0x0077:
            if (r5 > 0) goto L_0x007e
            r10 = 1
            r14.mBlocked = r10     // Catch:{ all -> 0x00c6 }
            monitor-exit(r14)     // Catch:{ all -> 0x00c6 }
            goto L_0x000e
        L_0x007e:
            android.os.MessageQueue$IdleHandler[] r10 = r14.mPendingIdleHandlers     // Catch:{ all -> 0x00c6 }
            if (r10 != 0) goto L_0x008b
            r10 = 4
            int r10 = java.lang.Math.max(r5, r10)     // Catch:{ all -> 0x00c6 }
            android.os.MessageQueue$IdleHandler[] r10 = new android.os.MessageQueue.IdleHandler[r10]     // Catch:{ all -> 0x00c6 }
            r14.mPendingIdleHandlers = r10     // Catch:{ all -> 0x00c6 }
        L_0x008b:
            java.util.ArrayList<android.os.MessageQueue$IdleHandler> r10 = r14.mIdleHandlers     // Catch:{ all -> 0x00c6 }
            android.os.MessageQueue$IdleHandler[] r11 = r14.mPendingIdleHandlers     // Catch:{ all -> 0x00c6 }
            java.lang.Object[] r10 = r10.toArray(r11)     // Catch:{ all -> 0x00c6 }
            android.os.MessageQueue$IdleHandler[] r10 = (android.os.MessageQueue.IdleHandler[]) r10     // Catch:{ all -> 0x00c6 }
            r14.mPendingIdleHandlers = r10     // Catch:{ all -> 0x00c6 }
            monitor-exit(r14)     // Catch:{ all -> 0x00c6 }
            r6 = r4
        L_0x0099:
            if (r6 >= r5) goto L_0x00c2
            android.os.MessageQueue$IdleHandler[] r7 = r14.mPendingIdleHandlers
            r7 = r7[r6]
            android.os.MessageQueue$IdleHandler[] r8 = r14.mPendingIdleHandlers
            r8[r6] = r3
            r8 = r4
            boolean r9 = r7.queueIdle()     // Catch:{ Throwable -> 0x00aa }
            r8 = r9
            goto L_0x00b2
        L_0x00aa:
            r9 = move-exception
            java.lang.String r10 = "MessageQueue"
            java.lang.String r11 = "IdleHandler threw exception"
            android.util.Log.wtf(r10, r11, r9)
        L_0x00b2:
            if (r8 != 0) goto L_0x00bf
            monitor-enter(r14)
            java.util.ArrayList<android.os.MessageQueue$IdleHandler> r9 = r14.mIdleHandlers     // Catch:{ all -> 0x00bc }
            r9.remove(r7)     // Catch:{ all -> 0x00bc }
            monitor-exit(r14)     // Catch:{ all -> 0x00bc }
            goto L_0x00bf
        L_0x00bc:
            r3 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00bc }
            throw r3
        L_0x00bf:
            int r6 = r6 + 1
            goto L_0x0099
        L_0x00c2:
            r5 = 0
            r2 = 0
            goto L_0x000e
        L_0x00c6:
            r3 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00c6 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.next():android.os.Message");
    }

    /* access modifiers changed from: package-private */
    public void quit(boolean safe) {
        if (this.mQuitAllowed) {
            synchronized (this) {
                if (!this.mQuitting) {
                    this.mQuitting = true;
                    if (safe) {
                        removeAllFutureMessagesLocked();
                    } else {
                        removeAllMessagesLocked();
                    }
                    nativeWake(this.mPtr);
                    return;
                }
                return;
            }
        }
        throw new IllegalStateException("Main thread not allowed to quit.");
    }

    public int postSyncBarrier() {
        return postSyncBarrier(SystemClock.uptimeMillis());
    }

    private int postSyncBarrier(long when) {
        int token;
        synchronized (this) {
            token = this.mNextBarrierToken;
            this.mNextBarrierToken = token + 1;
            Message msg = Message.obtain();
            msg.markInUse();
            msg.when = when;
            msg.arg1 = token;
            Message prev = null;
            Message p = this.mMessages;
            if (when != 0) {
                while (p != null && p.when <= when) {
                    prev = p;
                    p = p.next;
                }
            }
            if (prev != null) {
                msg.next = p;
                prev.next = msg;
            } else {
                msg.next = p;
                this.mMessages = msg;
            }
        }
        return token;
    }

    public void removeSyncBarrier(int token) {
        boolean needWake;
        synchronized (this) {
            Message prev = null;
            Message p = this.mMessages;
            while (p != null && (p.target != null || p.arg1 != token)) {
                prev = p;
                p = p.next;
            }
            if (p != null) {
                if (prev != null) {
                    prev.next = p.next;
                    needWake = false;
                } else {
                    this.mMessages = p.next;
                    if (this.mMessages != null) {
                        if (this.mMessages.target == null) {
                            needWake = false;
                        }
                    }
                    needWake = true;
                }
                p.recycleUnchecked();
                if (needWake && !this.mQuitting) {
                    nativeWake(this.mPtr);
                }
            } else {
                throw new IllegalStateException("The specified message queue synchronization  barrier token has not been posted or has already been removed.");
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0087, code lost:
        return true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0081  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enqueueMessage(android.os.Message r7, long r8) {
        /*
            r6 = this;
            android.os.Handler r0 = r7.target
            if (r0 == 0) goto L_0x00a2
            boolean r0 = r7.isInUse()
            if (r0 != 0) goto L_0x008b
            monitor-enter(r6)
            boolean r0 = r6.mQuitting     // Catch:{ all -> 0x0088 }
            r1 = 0
            if (r0 == 0) goto L_0x0036
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0088 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0088 }
            r2.<init>()     // Catch:{ all -> 0x0088 }
            android.os.Handler r3 = r7.target     // Catch:{ all -> 0x0088 }
            r2.append(r3)     // Catch:{ all -> 0x0088 }
            java.lang.String r3 = " sending message to a Handler on a dead thread"
            r2.append(r3)     // Catch:{ all -> 0x0088 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0088 }
            r0.<init>(r2)     // Catch:{ all -> 0x0088 }
            java.lang.String r2 = "MessageQueue"
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x0088 }
            android.util.Log.w(r2, r3, r0)     // Catch:{ all -> 0x0088 }
            r7.recycle()     // Catch:{ all -> 0x0088 }
            monitor-exit(r6)     // Catch:{ all -> 0x0088 }
            return r1
        L_0x0036:
            r7.markInUse()     // Catch:{ all -> 0x0088 }
            r7.when = r8     // Catch:{ all -> 0x0088 }
            android.os.Message r0 = r6.mMessages     // Catch:{ all -> 0x0088 }
            r2 = 1
            if (r0 == 0) goto L_0x0079
            r3 = 0
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x0079
            long r3 = r0.when     // Catch:{ all -> 0x0088 }
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x004d
            goto L_0x0079
        L_0x004d:
            boolean r3 = r6.mBlocked     // Catch:{ all -> 0x0088 }
            if (r3 == 0) goto L_0x005d
            android.os.Handler r3 = r0.target     // Catch:{ all -> 0x0088 }
            if (r3 != 0) goto L_0x005d
            boolean r3 = r7.isAsynchronous()     // Catch:{ all -> 0x0088 }
            if (r3 == 0) goto L_0x005d
            r1 = r2
        L_0x005d:
            r3 = r0
            android.os.Message r4 = r0.next     // Catch:{ all -> 0x0088 }
            r0 = r4
            if (r0 == 0) goto L_0x0074
            long r4 = r0.when     // Catch:{ all -> 0x0088 }
            int r4 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r4 >= 0) goto L_0x006a
            goto L_0x0074
        L_0x006a:
            if (r1 == 0) goto L_0x005d
            boolean r4 = r0.isAsynchronous()     // Catch:{ all -> 0x0088 }
            if (r4 == 0) goto L_0x005d
            r1 = 0
            goto L_0x005d
        L_0x0074:
            r7.next = r0     // Catch:{ all -> 0x0088 }
            r3.next = r7     // Catch:{ all -> 0x0088 }
            goto L_0x007f
        L_0x0079:
            r7.next = r0     // Catch:{ all -> 0x0088 }
            r6.mMessages = r7     // Catch:{ all -> 0x0088 }
            boolean r1 = r6.mBlocked     // Catch:{ all -> 0x0088 }
        L_0x007f:
            if (r1 == 0) goto L_0x0086
            long r3 = r6.mPtr     // Catch:{ all -> 0x0088 }
            nativeWake(r3)     // Catch:{ all -> 0x0088 }
        L_0x0086:
            monitor-exit(r6)     // Catch:{ all -> 0x0088 }
            return r2
        L_0x0088:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0088 }
            throw r0
        L_0x008b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r7)
            java.lang.String r2 = " This message is already in use."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00a2:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Message must have a target."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.enqueueMessage(android.os.Message, long):boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean hasMessages(Handler h, int what, Object object) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h && p.what == what && (object == null || p.obj == object)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean hasMessages(Handler h, Runnable r, Object object) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h && p.callback == r && (object == null || p.obj == object)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasMessages(Handler h) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void removeMessages(Handler h, int what, Object object) {
        if (h != null) {
            synchronized (this) {
                Message p = this.mMessages;
                while (p != null && p.target == h && p.what == what && (object == null || p.obj == object)) {
                    Message n = p.next;
                    this.mMessages = n;
                    p.recycleUnchecked();
                    p = n;
                }
                while (p != null) {
                    Message n2 = p.next;
                    if (n2 != null && n2.target == h && n2.what == what && (object == null || n2.obj == object)) {
                        Message nn = n2.next;
                        n2.recycleUnchecked();
                        p.next = nn;
                    } else {
                        p = n2;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeMessages(Handler h, Runnable r, Object object) {
        if (h != null && r != null) {
            synchronized (this) {
                Message p = this.mMessages;
                while (p != null && p.target == h && p.callback == r && (object == null || p.obj == object)) {
                    Message n = p.next;
                    this.mMessages = n;
                    p.recycleUnchecked();
                    p = n;
                }
                while (p != null) {
                    Message n2 = p.next;
                    if (n2 != null && n2.target == h && n2.callback == r && (object == null || n2.obj == object)) {
                        Message nn = n2.next;
                        n2.recycleUnchecked();
                        p.next = nn;
                    } else {
                        p = n2;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeCallbacksAndMessages(Handler h, Object object) {
        if (h != null) {
            synchronized (this) {
                Message p = this.mMessages;
                while (p != null && p.target == h && (object == null || p.obj == object)) {
                    Message n = p.next;
                    this.mMessages = n;
                    p.recycleUnchecked();
                    p = n;
                }
                while (p != null) {
                    Message n2 = p.next;
                    if (n2 != null && n2.target == h && (object == null || n2.obj == object)) {
                        Message nn = n2.next;
                        n2.recycleUnchecked();
                        p.next = nn;
                    } else {
                        p = n2;
                    }
                }
            }
        }
    }

    private void removeAllMessagesLocked() {
        Message p = this.mMessages;
        while (p != null) {
            Message n = p.next;
            p.recycleUnchecked();
            p = n;
        }
        this.mMessages = null;
    }

    private void removeAllFutureMessagesLocked() {
        long now = SystemClock.uptimeMillis();
        Message p = this.mMessages;
        if (p == null) {
            return;
        }
        if (p.when > now) {
            removeAllMessagesLocked();
            return;
        }
        while (true) {
            Message n = p.next;
            if (n != null) {
                if (n.when > now) {
                    p.next = null;
                    do {
                        Message p2 = n;
                        n = p2.next;
                        p2.recycleUnchecked();
                    } while (n != null);
                    return;
                }
                p = n;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dump(Printer pw, String prefix, Handler h) {
        synchronized (this) {
            long now = SystemClock.uptimeMillis();
            int n = 0;
            for (Message msg = this.mMessages; msg != null; msg = msg.next) {
                if (h == null || h == msg.target) {
                    pw.println(prefix + "Message " + n + ": " + msg.toString(now));
                }
                n++;
            }
            pw.println(prefix + "(Total messages: " + n + ", polling=" + isPollingLocked() + ", quitting=" + this.mQuitting + ")");
        }
    }

    /* access modifiers changed from: package-private */
    public void writeToProto(ProtoOutputStream proto, long fieldId) {
        long messageQueueToken = proto.start(fieldId);
        synchronized (this) {
            for (Message msg = this.mMessages; msg != null; msg = msg.next) {
                msg.writeToProto(proto, 2246267895809L);
            }
            proto.write(1133871366146L, isPollingLocked());
            proto.write(1133871366147L, this.mQuitting);
        }
        proto.end(messageQueueToken);
    }

    private static final class FileDescriptorRecord {
        public final FileDescriptor mDescriptor;
        public int mEvents;
        public OnFileDescriptorEventListener mListener;
        public int mSeq;

        public FileDescriptorRecord(FileDescriptor descriptor, int events, OnFileDescriptorEventListener listener) {
            this.mDescriptor = descriptor;
            this.mEvents = events;
            this.mListener = listener;
        }
    }
}
