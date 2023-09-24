package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.wits.pms.bean.TxzMessage;

/* loaded from: classes2.dex */
public class TxzReceiver extends BroadcastReceiver {
    public static final String TXZ_DIMISS_ACTION = "com.txznet.txz.record.dismiss";
    public static final String TXZ_SEND_ACTION = "com.txznet.adapter.send";
    public static final String TXZ_SHOW_ACTION = "com.txznet.txz.record.show";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (TXZ_SEND_ACTION.equals(intent.getAction())) {
            handleReceive(intent);
        }
    }

    private void handleReceive(Intent intent) {
        TxzMessage txzMessage = new TxzMessage(intent);
        ReceiverHandler.handle(txzMessage);
    }
}
