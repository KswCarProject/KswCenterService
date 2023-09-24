package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.wits.pms.bean.AutoKitMessage;

/* loaded from: classes2.dex */
public class AutoKitReceiver extends BroadcastReceiver {
    public static final String AUTO_BOX_MODE_CHANGE_EVT = "cn.manstep.phonemirrorBox.AUTO_BOX_MODE_CHANGE_EVT";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("cn.manstep.phonemirrorBox.AUTO_BOX_MODE_CHANGE_EVT".equals(intent.getAction())) {
            handleReceive(intent);
        }
    }

    private void handleReceive(Intent intent) {
        AutoKitMessage autoKitMessage = new AutoKitMessage(intent);
        ReceiverHandler.handle(autoKitMessage);
    }
}
