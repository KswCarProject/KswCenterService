package com.wits.pms.core;

import com.wits.pms.ICmdListener;
import com.wits.pms.core.PowerManagerImpl;

final /* synthetic */ class PowerManagerImpl$1$$Lambda$0 implements Runnable {
    private final ICmdListener arg$1;
    private final String arg$2;

    PowerManagerImpl$1$$Lambda$0(ICmdListener iCmdListener, String str) {
        this.arg$1 = iCmdListener;
        this.arg$2 = str;
    }

    public void run() {
        PowerManagerImpl.AnonymousClass1.lambda$handleMessage$0$PowerManagerImpl$1(this.arg$1, this.arg$2);
    }
}
