package com.wits.pms.utils;

import android.app.job.JobInfo;
import android.content.Context;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wits.pms.C3580R;
import com.wits.pms.utils.TouchControl;

/* loaded from: classes2.dex */
public class TouchControl {
    private static final int MSG_ADD = 0;
    private static final int MSG_HIDE_POINT = 4;
    private static final int MSG_REMOVE = 2;
    private static final int MSG_SHOW_POINT = 3;
    private static final int MSG_UPDATE = 1;
    private static final String TAG = "TouchControl";
    private static View mInterceptView;
    private static boolean wasAdded;
    private final Context mContext;
    private long mDownTime;
    private final Handler mHandler;
    private WindowManager.LayoutParams mPointerLayoutParams;
    private ImageView mPointerView;
    private int mScreenHeight;
    private int mScreenWidth;
    private final WindowManager mWindowManager;
    private final long AUTO_HIDE_TIME = JobInfo.MIN_BACKOFF_MILLIS;
    private final InputManager mInputManager = InputManager.getInstance();

    /* loaded from: classes2.dex */
    public interface OnScreenStatusListener {
        void openScreen();
    }

    public TouchControl(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        this.mWindowManager.getDefaultDisplay().getSize(outSize);
        this.mScreenWidth = outSize.f59x;
        this.mScreenHeight = outSize.f60y;
        Log.m68i(TAG, "init width:" + outSize.f59x + " - height:" + outSize.f60y);
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.wits.pms.utils.TouchControl.1
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        TouchControl.this.mWindowManager.addView(TouchControl.this.mPointerView, TouchControl.this.mPointerLayoutParams);
                        return;
                    case 1:
                        TouchControl.this.mWindowManager.updateViewLayout(TouchControl.this.mPointerView, TouchControl.this.mPointerLayoutParams);
                        return;
                    case 2:
                    default:
                        return;
                    case 3:
                        TouchControl.this.mPointerView.setVisibility(0);
                        return;
                    case 4:
                        TouchControl.this.mPointerView.setVisibility(8);
                        return;
                }
            }
        };
    }

    public synchronized void opPointerEvent(int x, int y, int action) {
        if (this.mPointerView == null) {
            obtainPointerView(this.mContext);
        }
        WindowManager.LayoutParams layoutParams = this.mPointerLayoutParams;
        int x2 = (this.mScreenWidth * x) / 255;
        layoutParams.f2425x = x2;
        WindowManager.LayoutParams layoutParams2 = this.mPointerLayoutParams;
        int y2 = (this.mScreenHeight * y) / 255;
        layoutParams2.f2426y = y2;
        Log.m68i(TAG, "mPointerLayoutParams x:" + this.mPointerLayoutParams.f2425x + "-y:" + this.mPointerLayoutParams.f2426y);
        this.mHandler.sendEmptyMessage(1);
        if (this.mHandler.hasMessages(4)) {
            this.mHandler.removeMessages(4);
        }
        this.mHandler.sendEmptyMessage(3);
        switch (action) {
            case 0:
                touchDown(x2, y2);
                break;
            case 1:
                touchUp(x2, y2);
                this.mHandler.sendEmptyMessageDelayed(4, JobInfo.MIN_BACKOFF_MILLIS);
                break;
            case 2:
                touchMove(x2, y2);
                if (!isDown()) {
                    this.mHandler.sendEmptyMessageDelayed(4, JobInfo.MIN_BACKOFF_MILLIS);
                    break;
                }
                break;
        }
    }

    private void obtainPointerView(Context context) {
        this.mPointerLayoutParams = new WindowManager.LayoutParams();
        this.mPointerLayoutParams.type = 2006;
        this.mPointerLayoutParams.height = 38;
        this.mPointerLayoutParams.width = 38;
        this.mPointerLayoutParams.flags |= 776;
        this.mPointerLayoutParams.format = 1;
        this.mPointerLayoutParams.gravity = 51;
        this.mPointerLayoutParams.f2425x = 0;
        this.mPointerLayoutParams.f2426y = 0;
        this.mPointerView = new ImageView(context);
        this.mPointerView.setImageResource(C3580R.mipmap.pointer);
        this.mPointerView.setLayoutParams(new FrameLayout.LayoutParams(38, 38));
        this.mPointerView.setVisibility(0);
        this.mHandler.sendEmptyMessage(0);
    }

    public boolean isDown() {
        return this.mDownTime != 0;
    }

    private boolean touchDown(int x, int y) {
        this.mDownTime = SystemClock.uptimeMillis();
        MotionEvent event = getMotionEvent(this.mDownTime, this.mDownTime, 0, x, y);
        return this.mInputManager.injectInputEvent(event, 2);
    }

    private boolean touchUp(int x, int y) {
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent event = getMotionEvent(this.mDownTime, eventTime, 1, x, y);
        this.mDownTime = 0L;
        return this.mInputManager.injectInputEvent(event, 2);
    }

    private boolean touchMove(int x, int y) {
        if (this.mDownTime == 0) {
            return false;
        }
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent event = getMotionEvent(this.mDownTime, eventTime, 2, x, y);
        return this.mInputManager.injectInputEvent(event, 0);
    }

    private static MotionEvent getMotionEvent(long downTime, long eventTime, int action, float x, float y) {
        MotionEvent.PointerProperties properties = new MotionEvent.PointerProperties();
        properties.f2403id = 0;
        properties.toolType = 3;
        MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
        coords.pressure = 1.0f;
        coords.size = 1.0f;
        coords.f2401x = x;
        coords.f2402y = y;
        return MotionEvent.obtain(downTime, eventTime, action, 1, new MotionEvent.PointerProperties[]{properties}, new MotionEvent.PointerCoords[]{coords}, 0, 0, 1.0f, 1.0f, 0, 0, 4098, 0);
    }

    public static void opInterceptView(Context context, boolean intercept, OnScreenStatusListener listener) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (!intercept) {
            if (mInterceptView != null && wasAdded) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$TouchControl$Wx2GICbV97ZLC2IwPaYrdIdbVy8
                    @Override // java.lang.Runnable
                    public final void run() {
                        TouchControl.lambda$opInterceptView$0(WindowManager.this);
                    }
                });
                return;
            }
            return;
        }
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = 2010;
        lp.flags = lp.flags | 1024 | 262144 | 524288;
        lp.height = -1;
        lp.width = -1;
        lp.format = 1;
        lp.alpha = 1.0f;
        lp.f2425x = 0;
        lp.f2426y = 0;
        if (mInterceptView == null) {
            mInterceptView = new View(context);
            mInterceptView.setClickable(true);
            mInterceptView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            if (listener != null) {
                mInterceptView.setOnTouchListener(new View$OnTouchListenerC36802(listener, windowManager));
            }
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$TouchControl$Yt2NWbUYe1lb_G4ERXvVcnI9vZ4
            @Override // java.lang.Runnable
            public final void run() {
                TouchControl.lambda$opInterceptView$1(WindowManager.this, lp);
            }
        });
    }

    static /* synthetic */ void lambda$opInterceptView$0(WindowManager windowManager) {
        wasAdded = false;
        try {
            windowManager.removeViewImmediate(mInterceptView);
        } catch (Exception e) {
            Log.m63w(TAG, "removeObserverListener interceptView failed.", e);
        }
    }

    /* renamed from: com.wits.pms.utils.TouchControl$2 */
    /* loaded from: classes2.dex */
    static class View$OnTouchListenerC36802 implements View.OnTouchListener {
        final /* synthetic */ OnScreenStatusListener val$listener;
        final /* synthetic */ WindowManager val$windowManager;

        View$OnTouchListenerC36802(OnScreenStatusListener onScreenStatusListener, WindowManager windowManager) {
            this.val$listener = onScreenStatusListener;
            this.val$windowManager = windowManager;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            Handler handler = new Handler(Looper.getMainLooper());
            final OnScreenStatusListener onScreenStatusListener = this.val$listener;
            final WindowManager windowManager = this.val$windowManager;
            handler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$TouchControl$2$YLyUrKOl3oxQyK8zL-Zf9MMAgMU
                @Override // java.lang.Runnable
                public final void run() {
                    TouchControl.View$OnTouchListenerC36802.lambda$onTouch$0(TouchControl.OnScreenStatusListener.this, windowManager);
                }
            });
            return false;
        }

        static /* synthetic */ void lambda$onTouch$0(OnScreenStatusListener listener, WindowManager windowManager) {
            boolean unused = TouchControl.wasAdded = false;
            try {
                listener.openScreen();
                windowManager.removeViewImmediate(TouchControl.mInterceptView);
            } catch (Exception e) {
                Log.m63w(TouchControl.TAG, "removeObserverListener interceptView failed.", e);
            }
        }
    }

    static /* synthetic */ void lambda$opInterceptView$1(WindowManager windowManager, WindowManager.LayoutParams lp) {
        try {
            if (wasAdded) {
                windowManager.removeViewImmediate(mInterceptView);
            }
            wasAdded = true;
            windowManager.addView(mInterceptView, lp);
        } catch (Exception e) {
            Log.m63w(TAG, "addObserverListener interceptView failed.", e);
        }
    }
}
