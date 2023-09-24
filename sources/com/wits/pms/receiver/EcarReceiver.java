package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.wits.pms.bean.EcarMessage;

/* loaded from: classes2.dex */
public class EcarReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(EcarMessage.ECAR_NORMAL_ACTION)) {
            handleReceive(intent);
        }
    }

    private void handleReceive(Intent intent) {
        EcarMessage ecarMessage = new EcarMessage(intent);
        ReceiverHandler.handle(ecarMessage);
    }
}
