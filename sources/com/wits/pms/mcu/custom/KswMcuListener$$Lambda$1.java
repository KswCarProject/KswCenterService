package com.wits.pms.mcu.custom;

import com.wits.pms.statuscontrol.SystemStatus;

final /* synthetic */ class KswMcuListener$$Lambda$1 implements Runnable {
    private final KswMcuListener arg$1;
    private final SystemStatus arg$2;
    private final byte[] arg$3;

    KswMcuListener$$Lambda$1(KswMcuListener kswMcuListener, SystemStatus systemStatus, byte[] bArr) {
        this.arg$1 = kswMcuListener;
        this.arg$2 = systemStatus;
        this.arg$3 = bArr;
    }

    public void run() {
        this.arg$1.lambda$onMcuMessage$0$KswMcuListener(this.arg$2, this.arg$3);
    }
}
