package com.wits.pms.utils;

import android.view.WindowManager;

final /* synthetic */ class TouchControl$$Lambda$1 implements Runnable {
    private final WindowManager arg$1;
    private final WindowManager.LayoutParams arg$2;

    TouchControl$$Lambda$1(WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
        this.arg$1 = windowManager;
        this.arg$2 = layoutParams;
    }

    public void run() {
        TouchControl.lambda$opInterceptView$1$TouchControl(this.arg$1, this.arg$2);
    }
}
