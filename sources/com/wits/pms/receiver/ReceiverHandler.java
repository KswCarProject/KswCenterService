package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.EcarMessage;
import com.wits.pms.bean.TxzMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mirror.SystemProperties;

/* loaded from: classes2.dex */
public class ReceiverHandler {
    public static void init(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TxzReceiver.TXZ_DIMISS_ACTION);
        intentFilter.addAction(TxzReceiver.TXZ_SHOW_ACTION);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.wits.pms.receiver.ReceiverHandler.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (TxzReceiver.TXZ_SHOW_ACTION.equals(intent.getAction())) {
                    KswMcuSender.getSender().sendMessage(105, new byte[]{19, 1, 2});
                    SystemProperties.set(TxzMessage.TXZ_SHOW_STATUS, "1");
                }
                if (TxzReceiver.TXZ_DIMISS_ACTION.equals(intent.getAction())) {
                    KswMcuSender.getSender().sendMessage(105, new byte[]{19, 0, 2});
                    SystemProperties.set(TxzMessage.TXZ_SHOW_STATUS, "0");
                }
            }
        }, intentFilter);
        IntentFilter zlinkFilter = new IntentFilter();
        zlinkFilter.addAction(ZlinkMessage.ZLINK_NORMAL_ACTION);
        ZlinkReceiver zlinkReceiver = new ZlinkReceiver();
        context.registerReceiver(zlinkReceiver, zlinkFilter);
        IntentFilter ecarFilter = new IntentFilter();
        ecarFilter.addAction(EcarMessage.ECAR_NORMAL_ACTION);
        EcarReceiver ecarReceiver = new EcarReceiver();
        context.registerReceiver(ecarReceiver, ecarFilter);
        IntentFilter autoKixFilter = new IntentFilter();
        autoKixFilter.addAction("cn.manstep.phonemirrorBox.AUTO_BOX_MODE_CHANGE_EVT");
        AutoKitReceiver autoKixReceiver = new AutoKitReceiver();
        context.registerReceiver(autoKixReceiver, autoKixFilter);
        IntentFilter txzFilter = new IntentFilter();
        txzFilter.addAction(TxzReceiver.TXZ_SEND_ACTION);
        TxzReceiver txzReceiver = new TxzReceiver();
        context.registerReceiver(txzReceiver, txzFilter);
    }

    public static void handle(TxzMessage msg) {
        Log.m66v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.m70e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }

    public static void handle(ZlinkMessage msg) {
        Log.m66v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.m70e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }

    public static void handle(EcarMessage msg) {
        Log.m66v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.m70e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }

    public static void handle(AutoKitMessage msg) {
        Log.m66v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.m70e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }
}
