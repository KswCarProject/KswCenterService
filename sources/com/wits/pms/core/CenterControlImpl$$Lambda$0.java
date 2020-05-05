package com.wits.pms.core;

import com.wits.pms.mcu.custom.KswMcuSender;

final /* synthetic */ class CenterControlImpl$$Lambda$0 implements Runnable {
    static final Runnable $instance = new CenterControlImpl$$Lambda$0();

    private CenterControlImpl$$Lambda$0() {
    }

    public void run() {
        KswMcuSender.getSender().sendMessage(105, new byte[]{19, 0, 0});
    }
}
