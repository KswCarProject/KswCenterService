package com.wits.pms.core;

import com.wits.pms.mcu.custom.KswMcuSender;

/* renamed from: com.wits.pms.core.-$$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0 implements Runnable {
    public static final /* synthetic */ $$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0 INSTANCE = new $$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0();

    private /* synthetic */ $$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0() {
    }

    public final void run() {
        KswMcuSender.getSender().sendMessage(105, new byte[]{19, 0, 0});
    }
}
