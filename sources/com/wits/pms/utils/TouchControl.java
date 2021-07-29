package com.wits.pms.utils;

import android.app.job.JobInfo;
import android.content.Context;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wits.pms.R;
import com.wits.pms.utils.TouchControl;

public class TouchControl {
    private static final int MSG_ADD = 0;
    private static final int MSG_HIDE_POINT = 4;
    private static final int MSG_REMOVE = 2;
    private static final int MSG_SHOW_POINT = 3;
    private static final int MSG_UPDATE = 1;
    private static final String TAG = "TouchControl";
    /* access modifiers changed from: private */
    public static View mInterceptView;
    /* access modifiers changed from: private */
    public static boolean wasAdded;
    private final long AUTO_HIDE_TIME = JobInfo.MIN_BACKOFF_MILLIS;
    private final Context mContext;
    private long mDownTime;
    private final Handler mHandler;
    private final InputManager mInputManager;
    /* access modifiers changed from: private */
    public WindowManager.LayoutParams mPointerLayoutParams;
    /* access modifiers changed from: private */
    public ImageView mPointerView;
    private int mScreenHeight;
    private int mScreenWidth;
    /* access modifiers changed from: private */
    public final WindowManager mWindowManager;

    public interface OnScreenStatusListener {
        void openScreen();
    }

    public TouchControl(Context context) {
        this.mContext = context;
        this.mInputManager = InputManager.getInstance();
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        this.mWindowManager.getDefaultDisplay().getSize(outSize);
        this.mScreenWidth = outSize.x;
        this.mScreenHeight = outSize.y;
        Log.i(TAG, "init width:" + outSize.x + " - height:" + outSize.y);
        this.mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        TouchControl.this.mWindowManager.addView(TouchControl.this.mPointerView, TouchControl.this.mPointerLayoutParams);
                        return;
                    case 1:
                        TouchControl.this.mWindowManager.updateViewLayout(TouchControl.this.mPointerView, TouchControl.this.mPointerLayoutParams);
                        return;
                    case 3:
                        TouchControl.this.mPointerView.setVisibility(0);
                        return;
                    case 4:
                        TouchControl.this.mPointerView.setVisibility(8);
                        return;
                    default:
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
        int i = (this.mScreenWidth * x) / 255;
        layoutParams.x = i;
        int x2 = i;
        WindowManager.LayoutParams layoutParams2 = this.mPointerLayoutParams;
        int i2 = (this.mScreenHeight * y) / 255;
        layoutParams2.y = i2;
        int y2 = i2;
        Log.i(TAG, "mPointerLayoutParams x:" + this.mPointerLayoutParams.x + "-y:" + this.mPointerLayoutParams.y);
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
        this.mPointerLayoutParams.x = 0;
        this.mPointerLayoutParams.y = 0;
        this.mPointerView = new ImageView(context);
        this.mPointerView.setImageResource(R.mipmap.pointer);
        this.mPointerView.setLayoutParams(new FrameLayout.LayoutParams(38, 38));
        this.mPointerView.setVisibility(0);
        this.mHandler.sendEmptyMessage(0);
    }

    public boolean isDown() {
        return this.mDownTime != 0;
    }

    private boolean touchDown(int x, int y) {
        this.mDownTime = SystemClock.uptimeMillis();
        return this.mInputManager.injectInputEvent(getMotionEvent(this.mDownTime, this.mDownTime, 0, (float) x, (float) y), 2);
    }

    private boolean touchUp(int x, int y) {
        MotionEvent event = getMotionEvent(this.mDownTime, SystemClock.uptimeMillis(), 1, (float) x, (float) y);
        this.mDownTime = 0;
        return this.mInputManager.injectInputEvent(event, 2);
    }

    private boolean touchMove(int x, int y) {
        if (this.mDownTime == 0) {
            return false;
        }
        return this.mInputManager.injectInputEvent(getMotionEvent(this.mDownTime, SystemClock.uptimeMillis(), 2, (float) x, (float) y), 2);
    }

    private static MotionEvent getMotionEvent(long downTime, long eventTime, int action, float x, float y) {
        MotionEvent.PointerProperties properties = new MotionEvent.PointerProperties();
        properties.id = 0;
        properties.toolType = 3;
        MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
        coords.pressure = 1.0f;
        coords.size = 1.0f;
        coords.x = x;
        coords.y = y;
        return MotionEvent.obtain(downTime, eventTime, action, 1, new MotionEvent.PointerProperties[]{properties}, new MotionEvent.PointerCoords[]{coords}, 0, 0, 1.0f, 1.0f, 0, 0, 4098, 0);
    }

    public static void opInterceptView(Context context, boolean intercept, final OnScreenStatusListener listener) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (intercept) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = 2010;
            lp.flags = lp.flags | 1024 | 262144 | 524288;
            lp.height = -1;
            lp.width = -1;
            lp.format = 1;
            lp.alpha = 1.0f;
            lp.x = 0;
            lp.y = 0;
            if (mInterceptView == null) {
                mInterceptView = new View(context);
                mInterceptView.setClickable(true);
                mInterceptView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                if (listener != null) {
                    mInterceptView.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            new Handler(Looper.getMainLooper()).post(new Runnable(windowManager) {
                                private final /* synthetic */ WindowManager f$1;

                                {
                                    this.f$1 = r2;
                                }

                                public final void run() {
                                    TouchControl.AnonymousClass2.lambda$onTouch$0(TouchControl.OnScreenStatusListener.this, this.f$1);
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
                                Log.w(TouchControl.TAG, "removeObserverListener interceptView failed.", e);
                            }
                        }
                    });
                }
            }
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable(lp) {
                    private final /* synthetic */ WindowManager.LayoutParams f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        TouchControl.lambda$opInterceptView$1(WindowManager.this, this.f$1);
                    }
                });
            } catch (Exception e) {
                Log.w(TAG, "addObserverListener interceptView failed.", e);
            }
        } else if (mInterceptView != null && wasAdded) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public final void run() {
                    TouchControl.lambda$opInterceptView$0(WindowManager.this);
                }
            });
        }
    }

    static /* synthetic */ void lambda$opInterceptView$0(WindowManager windowManager) {
        wasAdded = false;
        try {
            windowManager.removeViewImmediate(mInterceptView);
        } catch (Exception e) {
            Log.w(TAG, "removeObserverListener interceptView failed.", e);
        }
    }

    static /* synthetic */ void lambda$opInterceptView$1(WindowManager windowManager, WindowManager.LayoutParams lp) {
        if (wasAdded) {
            windowManager.removeViewImmediate(mInterceptView);
        }
        wasAdded = true;
        windowManager.addView(mInterceptView, lp);
    }
}
