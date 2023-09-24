package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.wits.pms.bean.ZlinkMessage;

/* loaded from: classes2.dex */
public class ZlinkReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ZlinkMessage.ZLINK_NORMAL_ACTION)) {
            handleReceive(intent);
        }
    }

    private void handleReceive(Intent intent) {
        ZlinkMessage zlinkMessage = new ZlinkMessage(intent);
        ReceiverHandler.handle(zlinkMessage);
    }
}
