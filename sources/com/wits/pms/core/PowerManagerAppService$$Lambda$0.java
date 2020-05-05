package com.wits.pms.core;

final /* synthetic */ class PowerManagerAppService$$Lambda$0 implements Runnable {
    private final PowerManagerAppService arg$1;
    private final boolean arg$2;

    PowerManagerAppService$$Lambda$0(PowerManagerAppService powerManagerAppService, boolean z) {
        this.arg$1 = powerManagerAppService;
        this.arg$2 = z;
    }

    public void run() {
        this.arg$1.lambda$boot$0$PowerManagerAppService(this.arg$2);
    }
}
