package com.wits.pms.custom;

import com.wits.pms.mcu.custom.KswMcuSender;

final /* synthetic */ class KswStatusHandler$2$$Lambda$0 implements Runnable {
    static final Runnable $instance = new KswStatusHandler$2$$Lambda$0();

    private KswStatusHandler$2$$Lambda$0() {
    }

    public void run() {
        KswMcuSender.getSender().sendMessage(99, new byte[]{1, 3});
    }
}
