package com.wits.pms.interfaces;

import com.wits.pms.interfaces.MemoryKiller;
import java.util.Comparator;

final /* synthetic */ class MemoryKiller$$Lambda$0 implements Comparator {
    static final Comparator $instance = new MemoryKiller$$Lambda$0();

    private MemoryKiller$$Lambda$0() {
    }

    public int compare(Object obj, Object obj2) {
        return MemoryKiller.lambda$initAppProcessList$0$MemoryKiller((MemoryKiller.AppProcess) obj, (MemoryKiller.AppProcess) obj2);
    }
}
