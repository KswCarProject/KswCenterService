package com.wits.pms.custom;

import com.wits.pms.utils.SystemProperties;

final /* synthetic */ class KswSettings$$Lambda$0 implements Runnable {
    static final Runnable $instance = new KswSettings$$Lambda$0();

    private KswSettings$$Lambda$0() {
    }

    public void run() {
        SystemProperties.set("sys.powerctl", "reboot");
    }
}
