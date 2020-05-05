package com.wits.pms.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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
        final WindowManager windowManager = (WindowManager) context.getSystemService("window");
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
                            new Handler(Looper.getMainLooper()).post(new TouchControl$1$$Lambda$0(listener, windowManager));
                            return false;
                        }

                        static final /* synthetic */ void lambda$onTouch$0$TouchControl$1(OnScreenStatusListener listener, WindowManager windowManager) {
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
                new Handler(Looper.getMainLooper()).post(new TouchControl$$Lambda$1(windowManager, lp));
            } catch (Exception e) {
                Log.w(TAG, "addObserverListener interceptView failed.", e);
            }
        } else if (mInterceptView != null && wasAdded) {
            new Handler(Looper.getMainLooper()).post(new TouchControl$$Lambda$0(windowManager));
        }
    }

    static final /* synthetic */ void lambda$opInterceptView$0$TouchControl(WindowManager windowManager) {
        wasAdded = false;
        try {
            windowManager.removeViewImmediate(mInterceptView);
        } catch (Exception e) {
            Log.w(TAG, "removeObserverListener interceptView failed.", e);
        }
    }

    static final /* synthetic */ void lambda$opInterceptView$1$TouchControl(WindowManager windowManager, WindowManager.LayoutParams lp) {
        if (wasAdded) {
            windowManager.removeViewImmediate(mInterceptView);
        }
        wasAdded = true;
        windowManager.addView(mInterceptView, lp);
    }
}
