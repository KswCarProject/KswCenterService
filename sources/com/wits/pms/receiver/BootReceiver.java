package com.wits.pms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.utils.AmsUtil;

public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.d("centerService", "started");
            if (context.getPackageManager().getLaunchIntentForPackage("com.txznet.adapter") != null) {
                try {
                    int txzStatus = PowerManagerApp.getSettingsInt("Support_TXZ");
                    Log.d("centerService", "boot start txz != null  set Support_TXZ " + txzStatus);
                    PowerManagerApp.setSettingsInt("Support_TXZ", txzStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            Intent accIntent = new Intent("com.wits.ksw.ACC_ON");
            accIntent.addFlags(16777216);
            context.sendBroadcastAsUser(accIntent, UserHandle.getUserHandleForUid(context.getApplicationInfo().uid));
        }
        "com.wits.boot.Start".equals(intent.getAction());
        if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
            AmsUtil.forceStopPackage(context, "com.nng.igo.primong.igoworld");
            AmsUtil.forceStopPackage(context, "com.nng.igoprimoisrael.javaclient");
            AmsUtil.forceStopPackage(context, "com.nng.igo.primong.hun10th");
            AmsUtil.forceStopPackage(context, "com.estrongs.android.pop");
        }
    }
}
