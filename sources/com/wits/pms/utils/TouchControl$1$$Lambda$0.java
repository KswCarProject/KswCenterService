package com.wits.pms.utils;

import android.view.WindowManager;
import com.wits.pms.utils.TouchControl;

final /* synthetic */ class TouchControl$1$$Lambda$0 implements Runnable {
    private final TouchControl.OnScreenStatusListener arg$1;
    private final WindowManager arg$2;

    TouchControl$1$$Lambda$0(TouchControl.OnScreenStatusListener onScreenStatusListener, WindowManager windowManager) {
        this.arg$1 = onScreenStatusListener;
        this.arg$2 = windowManager;
    }

    public void run() {
        TouchControl.AnonymousClass1.lambda$onTouch$0$TouchControl$1(this.arg$1, this.arg$2);
    }
}
