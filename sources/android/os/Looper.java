package android.os;

import android.annotation.UnsupportedAppUsage;
import android.util.Printer;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

public final class Looper {
    private static final String TAG = "Looper";
    @UnsupportedAppUsage
    private static Looper sMainLooper;
    private static Observer sObserver;
    @UnsupportedAppUsage
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    @UnsupportedAppUsage
    private Printer mLogging;
    @UnsupportedAppUsage
    final MessageQueue mQueue;
    private long mSlowDeliveryThresholdMs;
    private long mSlowDispatchThresholdMs;
    final Thread mThread = Thread.currentThread();
    private long mTraceTag;

    public interface Observer {
        void dispatchingThrewException(Object obj, Message message, Exception exc);

        Object messageDispatchStarting();

        void messageDispatched(Object obj, Message message);
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() == null) {
            sThreadLocal.set(new Looper(quitAllowed));
            return;
        }
        throw new RuntimeException("Only one Looper may be created per thread");
    }

    public static void prepareMainLooper() {
        prepare(false);
        synchronized (Looper.class) {
            if (sMainLooper == null) {
                sMainLooper = myLooper();
            } else {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
        }
    }

    public static Looper getMainLooper() {
        Looper looper;
        synchronized (Looper.class) {
            looper = sMainLooper;
        }
        return looper;
    }

    public static void setObserver(Observer observer) {
        sObserver = observer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ed A[SYNTHETIC, Splitter:B:44:0x00ed] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x010d A[Catch:{ Exception -> 0x00fe, all -> 0x00f1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x016e  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x017c  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0189  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01ac  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01b6  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0228 A[SYNTHETIC, Splitter:B:82:0x0228] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0236  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0202 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void loop() {
        /*
            android.os.Looper r1 = myLooper()
            if (r1 == 0) goto L_0x023a
            android.os.MessageQueue r2 = r1.mQueue
            android.os.Binder.clearCallingIdentity()
            long r3 = android.os.Binder.clearCallingIdentity()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r5 = "log.looper."
            r0.append(r5)
            int r5 = android.os.Process.myUid()
            r0.append(r5)
            java.lang.String r5 = "."
            r0.append(r5)
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            java.lang.String r5 = r5.getName()
            r0.append(r5)
            java.lang.String r5 = ".slow"
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            r5 = 0
            int r6 = android.os.SystemProperties.getInt(r0, r5)
            r0 = r5
        L_0x003f:
            r7 = r0
            android.os.Message r15 = r2.next()
            if (r15 != 0) goto L_0x0047
            return
        L_0x0047:
            android.util.Printer r14 = r1.mLogging
            if (r14 == 0) goto L_0x0075
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r8 = ">>>>> Dispatching to "
            r0.append(r8)
            android.os.Handler r8 = r15.target
            r0.append(r8)
            java.lang.String r8 = " "
            r0.append(r8)
            java.lang.Runnable r8 = r15.callback
            r0.append(r8)
            java.lang.String r8 = ": "
            r0.append(r8)
            int r8 = r15.what
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            r14.println(r0)
        L_0x0075:
            android.os.Looper$Observer r12 = sObserver
            long r10 = r1.mTraceTag
            long r8 = r1.mSlowDispatchThresholdMs
            r24 = r8
            long r8 = r1.mSlowDeliveryThresholdMs
            if (r6 <= 0) goto L_0x008a
            r26 = r1
            long r0 = (long) r6
            long r8 = (long) r6
            r27 = r0
            r24 = r8
            goto L_0x0090
        L_0x008a:
            r26 = r1
            r27 = r24
            r24 = r8
        L_0x0090:
            r8 = 0
            int r0 = (r24 > r8 ? 1 : (r24 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a0
            r29 = r2
            long r1 = r15.when
            int r0 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a2
            r0 = 1
            goto L_0x00a3
        L_0x00a0:
            r29 = r2
        L_0x00a2:
            r0 = r5
        L_0x00a3:
            r1 = r0
            int r0 = (r27 > r8 ? 1 : (r27 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x00aa
            r0 = 1
            goto L_0x00ab
        L_0x00aa:
            r0 = r5
        L_0x00ab:
            r2 = r0
            if (r1 != 0) goto L_0x00b4
            if (r2 == 0) goto L_0x00b1
            goto L_0x00b4
        L_0x00b1:
            r30 = r5
            goto L_0x00b6
        L_0x00b4:
            r30 = 1
        L_0x00b6:
            r31 = r2
            int r0 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x00cb
            boolean r0 = android.os.Trace.isTagEnabled(r10)
            if (r0 == 0) goto L_0x00cb
            android.os.Handler r0 = r15.target
            java.lang.String r0 = r0.getTraceName(r15)
            android.os.Trace.traceBegin(r10, r0)
        L_0x00cb:
            if (r30 == 0) goto L_0x00d2
            long r16 = android.os.SystemClock.uptimeMillis()
            goto L_0x00d4
        L_0x00d2:
            r16 = r8
        L_0x00d4:
            r32 = r16
            r0 = 0
            if (r12 == 0) goto L_0x00dd
            java.lang.Object r0 = r12.messageDispatchStarting()
        L_0x00dd:
            r13 = r0
            int r0 = r15.workSourceUid
            long r16 = android.os.ThreadLocalWorkSource.setUid(r0)
            r34 = r16
            android.os.Handler r0 = r15.target     // Catch:{ Exception -> 0x021b, all -> 0x020f }
            r0.dispatchMessage(r15)     // Catch:{ Exception -> 0x021b, all -> 0x020f }
            if (r12 == 0) goto L_0x010b
            r12.messageDispatched(r13, r15)     // Catch:{ Exception -> 0x00fe, all -> 0x00f1 }
            goto L_0x010b
        L_0x00f1:
            r0 = move-exception
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r10 = r15
            goto L_0x022f
        L_0x00fe:
            r0 = move-exception
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r10 = r15
            goto L_0x0226
        L_0x010b:
            if (r31 == 0) goto L_0x0114
            long r16 = android.os.SystemClock.uptimeMillis()     // Catch:{ Exception -> 0x00fe, all -> 0x00f1 }
            r20 = r16
            goto L_0x0116
        L_0x0114:
            r20 = r8
        L_0x0116:
            android.os.ThreadLocalWorkSource.restore(r34)
            int r0 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x0120
            android.os.Trace.traceEnd(r10)
        L_0x0120:
            if (r1 == 0) goto L_0x016e
            if (r7 == 0) goto L_0x014e
            long r8 = r15.when
            long r8 = r32 - r8
            r16 = 10
            int r0 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1))
            if (r0 > 0) goto L_0x0142
            java.lang.String r0 = "Looper"
            java.lang.String r8 = "Drained"
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r8)
            r0 = 0
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r40 = r15
            goto L_0x017a
        L_0x0142:
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r40 = r15
            goto L_0x0179
        L_0x014e:
            long r8 = r15.when
            java.lang.String r0 = "delivery"
            r16 = r8
            r8 = r24
            r36 = r10
            r10 = r16
            r38 = r1
            r5 = r12
            r1 = r13
            r12 = r32
            r39 = r6
            r6 = r14
            r14 = r0
            r40 = r15
            boolean r0 = showSlowLog(r8, r10, r12, r14, r15)
            if (r0 == 0) goto L_0x0179
            r0 = 1
            goto L_0x017a
        L_0x016e:
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r40 = r15
        L_0x0179:
            r0 = r7
        L_0x017a:
            if (r2 == 0) goto L_0x0187
            java.lang.String r22 = "dispatch"
            r16 = r27
            r18 = r32
            r23 = r40
            showSlowLog(r16, r18, r20, r22, r23)
        L_0x0187:
            if (r6 == 0) goto L_0x01ac
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "<<<<< Finished to "
            r7.append(r8)
            r10 = r40
            android.os.Handler r8 = r10.target
            r7.append(r8)
            java.lang.String r8 = " "
            r7.append(r8)
            java.lang.Runnable r8 = r10.callback
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.println(r7)
            goto L_0x01ae
        L_0x01ac:
            r10 = r40
        L_0x01ae:
            long r7 = android.os.Binder.clearCallingIdentity()
            int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r9 == 0) goto L_0x0202
            java.lang.String r9 = "Looper"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Thread identity changed from 0x"
            r11.append(r12)
            java.lang.String r12 = java.lang.Long.toHexString(r3)
            r11.append(r12)
            java.lang.String r12 = " to 0x"
            r11.append(r12)
            java.lang.String r12 = java.lang.Long.toHexString(r7)
            r11.append(r12)
            java.lang.String r12 = " while dispatching to "
            r11.append(r12)
            android.os.Handler r12 = r10.target
            java.lang.Class r12 = r12.getClass()
            java.lang.String r12 = r12.getName()
            r11.append(r12)
            java.lang.String r12 = " "
            r11.append(r12)
            java.lang.Runnable r12 = r10.callback
            r11.append(r12)
            java.lang.String r12 = " what="
            r11.append(r12)
            int r12 = r10.what
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            android.util.Log.wtf((java.lang.String) r9, (java.lang.String) r11)
        L_0x0202:
            r10.recycleUnchecked()
            r1 = r26
            r2 = r29
            r6 = r39
            r5 = 0
            goto L_0x003f
        L_0x020f:
            r0 = move-exception
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r10 = r15
            goto L_0x022f
        L_0x021b:
            r0 = move-exception
            r38 = r1
            r39 = r6
            r36 = r10
            r5 = r12
            r1 = r13
            r6 = r14
            r10 = r15
        L_0x0226:
            if (r5 == 0) goto L_0x022e
            r5.dispatchingThrewException(r1, r10, r0)     // Catch:{ all -> 0x022c }
            goto L_0x022e
        L_0x022c:
            r0 = move-exception
            goto L_0x022f
        L_0x022e:
            throw r0     // Catch:{ all -> 0x022c }
        L_0x022f:
            android.os.ThreadLocalWorkSource.restore(r34)
            int r8 = (r36 > r8 ? 1 : (r36 == r8 ? 0 : -1))
            if (r8 == 0) goto L_0x0239
            android.os.Trace.traceEnd(r36)
        L_0x0239:
            throw r0
        L_0x023a:
            r26 = r1
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "No Looper; Looper.prepare() wasn't called on this thread."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.Looper.loop():void");
    }

    private static boolean showSlowLog(long threshold, long measureStart, long measureEnd, String what, Message msg) {
        long actualTime = measureEnd - measureStart;
        if (actualTime < threshold) {
            return false;
        }
        Slog.w(TAG, "Slow " + what + " took " + actualTime + "ms " + Thread.currentThread().getName() + " h=" + msg.target.getClass().getName() + " c=" + msg.callback + " m=" + msg.what);
        return true;
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static MessageQueue myQueue() {
        return myLooper().mQueue;
    }

    private Looper(boolean quitAllowed) {
        this.mQueue = new MessageQueue(quitAllowed);
    }

    public boolean isCurrentThread() {
        return Thread.currentThread() == this.mThread;
    }

    public void setMessageLogging(Printer printer) {
        this.mLogging = printer;
    }

    @UnsupportedAppUsage
    public void setTraceTag(long traceTag) {
        this.mTraceTag = traceTag;
    }

    public void setSlowLogThresholdMs(long slowDispatchThresholdMs, long slowDeliveryThresholdMs) {
        this.mSlowDispatchThresholdMs = slowDispatchThresholdMs;
        this.mSlowDeliveryThresholdMs = slowDeliveryThresholdMs;
    }

    public void quit() {
        this.mQueue.quit(false);
    }

    public void quitSafely() {
        this.mQueue.quit(true);
    }

    public Thread getThread() {
        return this.mThread;
    }

    public MessageQueue getQueue() {
        return this.mQueue;
    }

    public void dump(Printer pw, String prefix) {
        pw.println(prefix + toString());
        MessageQueue messageQueue = this.mQueue;
        messageQueue.dump(pw, prefix + "  ", (Handler) null);
    }

    public void dump(Printer pw, String prefix, Handler handler) {
        pw.println(prefix + toString());
        MessageQueue messageQueue = this.mQueue;
        messageQueue.dump(pw, prefix + "  ", handler);
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId) {
        long looperToken = proto.start(fieldId);
        proto.write(1138166333441L, this.mThread.getName());
        proto.write(1112396529666L, this.mThread.getId());
        if (this.mQueue != null) {
            this.mQueue.writeToProto(proto, 1146756268035L);
        }
        proto.end(looperToken);
    }

    public String toString() {
        return "Looper (" + this.mThread.getName() + ", tid " + this.mThread.getId() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
