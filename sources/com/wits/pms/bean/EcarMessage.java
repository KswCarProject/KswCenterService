package com.wits.pms.bean;

import android.content.Intent;

/* loaded from: classes2.dex */
public class EcarMessage {
    public static final String ECAR_NORMAL_ACTION = "com.android.ecar.send";
    public String cmdType;
    public String ecarSendKey;

    public EcarMessage(Intent intent) {
        this.cmdType = intent.getStringExtra("cmdType");
        this.ecarSendKey = intent.getStringExtra("ecarSendKey");
    }

    public String toString() {
        return "EcarMessage   cmdType : " + this.cmdType + "   ecarSendKey : " + this.ecarSendKey;
    }
}
