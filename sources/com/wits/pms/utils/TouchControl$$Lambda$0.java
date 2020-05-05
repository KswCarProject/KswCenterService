package com.wits.pms.utils;

import android.view.WindowManager;

final /* synthetic */ class TouchControl$$Lambda$0 implements Runnable {
    private final WindowManager arg$1;

    TouchControl$$Lambda$0(WindowManager windowManager) {
        this.arg$1 = windowManager;
    }

    public void run() {
        TouchControl.lambda$opInterceptView$0$TouchControl(this.arg$1);
    }
}
