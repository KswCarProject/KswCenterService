package com.wits.pms.custom;

import com.wits.pms.mcu.custom.KswMcuSender;

/* renamed from: com.wits.pms.custom.-$$Lambda$KswStatusHandler$2$Am8OeGekegrncO4C3B7L_UB3F6Y  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$KswStatusHandler$2$Am8OeGekegrncO4C3B7L_UB3F6Y implements Runnable {
    public static final /* synthetic */ $$Lambda$KswStatusHandler$2$Am8OeGekegrncO4C3B7L_UB3F6Y INSTANCE = new $$Lambda$KswStatusHandler$2$Am8OeGekegrncO4C3B7L_UB3F6Y();

    private /* synthetic */ $$Lambda$KswStatusHandler$2$Am8OeGekegrncO4C3B7L_UB3F6Y() {
    }

    public final void run() {
        KswMcuSender.getSender().sendMessage(99, new byte[]{1, 3});
    }
}
