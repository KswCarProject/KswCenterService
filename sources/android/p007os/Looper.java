package android.p007os;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.WifiEnterpriseConfig;
import android.provider.Telephony;
import android.util.Log;
import android.util.Printer;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.ibm.icu.text.PluralRules;

/* renamed from: android.os.Looper */
/* loaded from: classes3.dex */
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

    /* renamed from: android.os.Looper$Observer */
    /* loaded from: classes3.dex */
    public interface Observer {
        void dispatchingThrewException(Object obj, Message message, Exception exc);

        Object messageDispatchStarting();

        void messageDispatched(Object obj, Message message);
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }

    public static void prepareMainLooper() {
        prepare(false);
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x0202 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010d A[Catch: all -> 0x00f1, Exception -> 0x00fe, TRY_LEAVE, TryCatch #5 {Exception -> 0x00fe, all -> 0x00f1, blocks: (B:46:0x00ed, B:52:0x010d), top: B:95:0x00ed }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void loop() {
        Looper me;
        long slowDispatchThresholdMs;
        long slowDispatchThresholdMs2;
        MessageQueue queue;
        boolean z;
        boolean logSlowDelivery;
        boolean logSlowDispatch;
        Object token;
        long traceTag;
        Observer observer;
        Object token2;
        Message msg;
        int thresholdOverride;
        Printer logging;
        Message msg2;
        Message msg3;
        long newIdent;
        Looper me2 = myLooper();
        if (me2 == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        MessageQueue queue2 = me2.mQueue;
        Binder.clearCallingIdentity();
        long ident = Binder.clearCallingIdentity();
        boolean z2 = false;
        int thresholdOverride2 = SystemProperties.getInt("log.looper." + Process.myUid() + "." + Thread.currentThread().getName() + ".slow", 0);
        boolean slowDeliveryDetected = false;
        while (true) {
            boolean slowDeliveryDetected2 = slowDeliveryDetected;
            Message msg4 = queue2.next();
            if (msg4 == null) {
                return;
            }
            Printer logging2 = me2.mLogging;
            if (logging2 != null) {
                logging2.println(">>>>> Dispatching to " + msg4.target + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + msg4.callback + PluralRules.KEYWORD_RULE_SEPARATOR + msg4.what);
            }
            Observer observer2 = sObserver;
            long traceTag2 = me2.mTraceTag;
            long slowDispatchThresholdMs3 = me2.mSlowDispatchThresholdMs;
            long slowDispatchThresholdMs4 = me2.mSlowDeliveryThresholdMs;
            if (thresholdOverride2 > 0) {
                me = me2;
                long slowDispatchThresholdMs5 = thresholdOverride2;
                long slowDeliveryThresholdMs = thresholdOverride2;
                slowDispatchThresholdMs = slowDispatchThresholdMs5;
                slowDispatchThresholdMs2 = slowDeliveryThresholdMs;
            } else {
                me = me2;
                slowDispatchThresholdMs = slowDispatchThresholdMs3;
                slowDispatchThresholdMs2 = slowDispatchThresholdMs4;
            }
            try {
                if (slowDispatchThresholdMs2 > 0) {
                    queue = queue2;
                    if (msg4.when > 0) {
                        z = true;
                        logSlowDelivery = z;
                        logSlowDispatch = slowDispatchThresholdMs <= 0 ? true : z2;
                        boolean needStartTime = (!logSlowDelivery || logSlowDispatch) ? true : z2;
                        if (traceTag2 != 0 && Trace.isTagEnabled(traceTag2)) {
                            Trace.traceBegin(traceTag2, msg4.target.getTraceName(msg4));
                        }
                        long dispatchStart = !needStartTime ? SystemClock.uptimeMillis() : 0L;
                        token = observer2 != null ? observer2.messageDispatchStarting() : null;
                        long origWorkSource = ThreadLocalWorkSource.setUid(msg4.workSourceUid);
                        msg4.target.dispatchMessage(msg4);
                        if (observer2 != null) {
                            try {
                                observer2.messageDispatched(token, msg4);
                            } catch (Exception e) {
                                exception = e;
                                traceTag = traceTag2;
                                observer = observer2;
                                token2 = token;
                                msg = msg4;
                                if (observer != null) {
                                    try {
                                        observer.dispatchingThrewException(token2, msg, exception);
                                    } catch (Throwable th) {
                                        exception = th;
                                        ThreadLocalWorkSource.restore(origWorkSource);
                                        if (traceTag != 0) {
                                            Trace.traceEnd(traceTag);
                                        }
                                        throw exception;
                                    }
                                }
                                throw exception;
                            } catch (Throwable th2) {
                                exception = th2;
                                traceTag = traceTag2;
                                ThreadLocalWorkSource.restore(origWorkSource);
                                if (traceTag != 0) {
                                }
                                throw exception;
                            }
                        }
                        long dispatchEnd = !logSlowDispatch ? SystemClock.uptimeMillis() : 0L;
                        ThreadLocalWorkSource.restore(origWorkSource);
                        if (traceTag2 != 0) {
                            Trace.traceEnd(traceTag2);
                        }
                        if (logSlowDelivery) {
                            thresholdOverride = thresholdOverride2;
                            logging = logging2;
                            msg2 = msg4;
                        } else {
                            if (!slowDeliveryDetected2) {
                                thresholdOverride = thresholdOverride2;
                                logging = logging2;
                                msg2 = msg4;
                                if (showSlowLog(slowDispatchThresholdMs2, msg4.when, dispatchStart, Telephony.RcsColumns.RcsMessageDeliveryColumns.DELIVERY_URI_PART, msg4)) {
                                    slowDeliveryDetected = true;
                                }
                            } else if (dispatchStart - msg4.when <= 10) {
                                Slog.m50w(TAG, "Drained");
                                slowDeliveryDetected = false;
                                thresholdOverride = thresholdOverride2;
                                logging = logging2;
                                msg2 = msg4;
                            } else {
                                thresholdOverride = thresholdOverride2;
                                logging = logging2;
                                msg2 = msg4;
                            }
                            if (logSlowDispatch) {
                                showSlowLog(slowDispatchThresholdMs, dispatchStart, dispatchEnd, "dispatch", msg2);
                            }
                            if (logging != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("<<<<< Finished to ");
                                msg3 = msg2;
                                sb.append(msg3.target);
                                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                                sb.append(msg3.callback);
                                logging.println(sb.toString());
                            } else {
                                msg3 = msg2;
                            }
                            newIdent = Binder.clearCallingIdentity();
                            if (ident != newIdent) {
                                Log.wtf(TAG, "Thread identity changed from 0x" + Long.toHexString(ident) + " to 0x" + Long.toHexString(newIdent) + " while dispatching to " + msg3.target.getClass().getName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + msg3.callback + " what=" + msg3.what);
                            }
                            msg3.recycleUnchecked();
                            me2 = me;
                            queue2 = queue;
                            thresholdOverride2 = thresholdOverride;
                            z2 = false;
                        }
                        slowDeliveryDetected = slowDeliveryDetected2;
                        if (logSlowDispatch) {
                        }
                        if (logging != null) {
                        }
                        newIdent = Binder.clearCallingIdentity();
                        if (ident != newIdent) {
                        }
                        msg3.recycleUnchecked();
                        me2 = me;
                        queue2 = queue;
                        thresholdOverride2 = thresholdOverride;
                        z2 = false;
                    }
                } else {
                    queue = queue2;
                }
                msg4.target.dispatchMessage(msg4);
                if (observer2 != null) {
                }
                if (!logSlowDispatch) {
                }
                ThreadLocalWorkSource.restore(origWorkSource);
                if (traceTag2 != 0) {
                }
                if (logSlowDelivery) {
                }
                slowDeliveryDetected = slowDeliveryDetected2;
                if (logSlowDispatch) {
                }
                if (logging != null) {
                }
                newIdent = Binder.clearCallingIdentity();
                if (ident != newIdent) {
                }
                msg3.recycleUnchecked();
                me2 = me;
                queue2 = queue;
                thresholdOverride2 = thresholdOverride;
                z2 = false;
            } catch (Exception e2) {
                exception = e2;
                traceTag = traceTag2;
                observer = observer2;
                token2 = token;
                msg = msg4;
            } catch (Throwable th3) {
                exception = th3;
                traceTag = traceTag2;
            }
            z = z2;
            logSlowDelivery = z;
            logSlowDispatch = slowDispatchThresholdMs <= 0 ? true : z2;
            if (logSlowDelivery) {
            }
            if (traceTag2 != 0) {
                Trace.traceBegin(traceTag2, msg4.target.getTraceName(msg4));
            }
            long dispatchStart2 = !needStartTime ? SystemClock.uptimeMillis() : 0L;
            token = observer2 != null ? observer2.messageDispatchStarting() : null;
            long origWorkSource2 = ThreadLocalWorkSource.setUid(msg4.workSourceUid);
        }
    }

    private static boolean showSlowLog(long threshold, long measureStart, long measureEnd, String what, Message msg) {
        long actualTime = measureEnd - measureStart;
        if (actualTime < threshold) {
            return false;
        }
        Slog.m50w(TAG, "Slow " + what + " took " + actualTime + "ms " + Thread.currentThread().getName() + " h=" + msg.target.getClass().getName() + " c=" + msg.callback + " m=" + msg.what);
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
        messageQueue.dump(pw, prefix + "  ", null);
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
