package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.util.Log;
import com.wits.pms.BuildConfig;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.EcarMessage;
import com.wits.pms.bean.TxzMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;

public class ReceiverHandler {
    public static void init(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TxzReceiver.TXZ_DIMISS_ACTION);
        intentFilter.addAction(TxzReceiver.TXZ_SHOW_ACTION);
        context.registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (TxzReceiver.TXZ_SHOW_ACTION.equals(intent.getAction())) {
                    KswMcuSender.getSender().sendMessage(105, new byte[]{19, 1, 2});
                }
                if (TxzReceiver.TXZ_DIMISS_ACTION.equals(intent.getAction())) {
                    KswMcuSender.getSender().sendMessage(105, new byte[]{19, 0, 2});
                    try {
                        if (PowerManagerApp.getStatusString("topApp").equals("com.wits.ksw.bt")) {
                            WitsCommand.sendCommand(3, 114, BuildConfig.FLAVOR);
                        }
                    } catch (RemoteException e) {
                    }
                }
            }
        }, intentFilter);
        IntentFilter zlinkFilter = new IntentFilter();
        zlinkFilter.addAction(ZlinkMessage.ZLINK_NORMAL_ACTION);
        context.registerReceiver(new ZlinkReceiver(), zlinkFilter);
        IntentFilter ecarFilter = new IntentFilter();
        ecarFilter.addAction(EcarMessage.ECAR_NORMAL_ACTION);
        context.registerReceiver(new EcarReceiver(), ecarFilter);
        IntentFilter autoKixFilter = new IntentFilter();
        autoKixFilter.addAction("cn.manstep.phonemirrorBox.AUTO_BOX_MODE_CHANGE_EVT");
        context.registerReceiver(new AutoKitReceiver(), autoKixFilter);
    }

    public static void handle(TxzMessage msg) {
        Log.v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }

    public static void handle(ZlinkMessage msg) {
        Log.v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }

    public static void handle(EcarMessage msg) {
        Log.v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }

    public static void handle(AutoKitMessage msg) {
        Log.v("ReceiverHandler", msg.toString());
        if (CenterControlImpl.getImpl() == null) {
            Log.e("ReceiverHandler", "system no ready");
        } else {
            CenterControlImpl.getImpl().handle(msg);
        }
    }
}
