package com.wits.pms.mcu;

/* loaded from: classes2.dex */
public interface McuSender {
    void send(McuMessage mcuMessage);

    void send(byte[] bArr);
}
