package com.wits.pms.core;

import java.util.List;

final /* synthetic */ class PowerManagerImpl$$Lambda$0 implements Runnable {
    private final PowerManagerImpl arg$1;
    private final List arg$2;

    PowerManagerImpl$$Lambda$0(PowerManagerImpl powerManagerImpl, List list) {
        this.arg$1 = powerManagerImpl;
        this.arg$2 = list;
    }

    public void run() {
        this.arg$1.lambda$handlerStatus$0$PowerManagerImpl(this.arg$2);
    }
}
