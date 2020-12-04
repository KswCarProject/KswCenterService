package com.wits.pms.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.wits.pms.utils.TouchControl;

public class TouchControl {
    private static final String TAG = "TouchControl";
    /* access modifiers changed from: private */
    public static View mInterceptView;
    /* access modifiers changed from: private */
    public static boolean wasAdded;

    public interface OnScreenStatusListener {
        void openScreen();
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
                                    TouchControl.AnonymousClass1.lambda$onTouch$0(TouchControl.OnScreenStatusListener.this, this.f$1);
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
