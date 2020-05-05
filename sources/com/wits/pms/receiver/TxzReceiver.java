package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.wits.pms.bean.TxzMessage;

public class TxzReceiver extends BroadcastReceiver {
    public static final String TXZ_DIMISS_ACTION = "com.txznet.txz.record.dismiss";
    public static final String TXZ_SEND_ACTION = "com.txznet.adapter.send";
    public static final String TXZ_SHOW_ACTION = "com.txznet.txz.record.show";

    public void onReceive(Context context, Intent intent) {
        if (TXZ_SEND_ACTION.equals(intent.getAction())) {
            handleReceive(intent);
        }
    }

    private void handleReceive(Intent intent) {
        ReceiverHandler.handle(new TxzMessage(intent));
    }
}
