package android.hardware.camera2.legacy;

import android.p007os.ConditionVariable;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.MessageQueue;

/* loaded from: classes.dex */
public class RequestHandlerThread extends HandlerThread {
    public static final int MSG_POKE_IDLE_HANDLER = -1;
    private Handler.Callback mCallback;
    private volatile Handler mHandler;
    private final ConditionVariable mIdle;
    private final MessageQueue.IdleHandler mIdleHandler;
    private final ConditionVariable mStarted;

    public RequestHandlerThread(String name, Handler.Callback callback) {
        super(name, 10);
        this.mStarted = new ConditionVariable(false);
        this.mIdle = new ConditionVariable(true);
        this.mIdleHandler = new MessageQueue.IdleHandler() { // from class: android.hardware.camera2.legacy.RequestHandlerThread.1
            @Override // android.p007os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                RequestHandlerThread.this.mIdle.open();
                return false;
            }
        };
        this.mCallback = callback;
    }

    @Override // android.p007os.HandlerThread
    protected void onLooperPrepared() {
        this.mHandler = new Handler(getLooper(), this.mCallback);
        this.mStarted.open();
    }

    public void waitUntilStarted() {
        this.mStarted.block();
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public Handler waitAndGetHandler() {
        waitUntilStarted();
        return getHandler();
    }

    public boolean hasAnyMessages(int[] what) {
        synchronized (this.mHandler.getLooper().getQueue()) {
            for (int i : what) {
                if (this.mHandler.hasMessages(i)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void removeMessages(int[] what) {
        synchronized (this.mHandler.getLooper().getQueue()) {
            for (int i : what) {
                this.mHandler.removeMessages(i);
            }
        }
    }

    public void waitUntilIdle() {
        Handler handler = waitAndGetHandler();
        MessageQueue queue = handler.getLooper().getQueue();
        if (queue.isIdle()) {
            return;
        }
        this.mIdle.close();
        queue.addIdleHandler(this.mIdleHandler);
        handler.sendEmptyMessage(-1);
        if (queue.isIdle()) {
            return;
        }
        this.mIdle.block();
    }
}
