package com.wits.pms.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.p007os.Build;
import android.p007os.RemoteException;
import android.p007os.SystemClock;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.telecom.ParcelableCallAnalytics;
import android.util.Log;
import android.util.TimedRemoteCaller;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.TempControllerService;
import com.wits.pms.mcu.custom.utils.WlanFirmwareUpdate;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.utils.AmsUtil;

/* loaded from: classes2.dex */
public class BootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.m72d("centerService", "started");
            if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && Build.DISPLAY.contains("M600")) {
                WlanFirmwareUpdate.checkUpdata(context);
            }
            Intent txz = context.getPackageManager().getLaunchIntentForPackage("com.txznet.adapter");
            context.getPackageManager().getLaunchIntentForPackage("com.txznet.smartadapter");
            if (txz != null) {
                try {
                    int txzStatus = PowerManagerApp.getSettingsInt("Support_TXZ");
                    Log.m72d("centerService", "boot start txz != null  set Support_TXZ " + txzStatus);
                    CenterControlImpl.getImpl().setTxzSwitch(txzStatus == 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                Log.m72d("centerService", "NOT boot start txz == null");
            }
            Intent accIntent = new Intent("com.wits.ksw.ACC_ON");
            accIntent.addFlags(16777216);
            context.sendBroadcastAsUser(accIntent, UserHandle.getUserHandleForUid(context.getApplicationInfo().uid));
            long triggerAtTime = SystemClock.elapsedRealtime() + TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS;
            if (Build.DISPLAY.contains("8937")) {
                AlarmManager mgr = (AlarmManager) context.getSystemService("alarm");
                PendingIntent tempControllerIntent = PendingIntent.getService(context, 0, new Intent(context, TempControllerService.class), 134217728);
                mgr.setRepeating(2, triggerAtTime, ParcelableCallAnalytics.MILLIS_IN_5_MINUTES, tempControllerIntent);
            }
            int zlink_hicar = Settings.System.getInt(context.getContentResolver(), "zlink_hicar", 0);
            int zlink_auto_start = Settings.System.getInt(context.getContentResolver(), "zlink_auto_start", 0);
            if (zlink_hicar == 1 || zlink_auto_start == 1) {
                CenterControlImpl.getImpl().enterZlink();
            }
            Log.m68i("BootReceiver", "start com.txznet.ota/.service.TXZService");
            Intent otaintent = new Intent();
            otaintent.setComponent(new ComponentName("com.txznet.ota", "com.txznet.ota.service.TXZService"));
            context.startService(otaintent);
        }
        "com.wits.boot.Start".equals(intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
            AmsUtil.forceStopPackage(context, "com.nng.igo.primong.igoworld");
            AmsUtil.forceStopPackage(context, "com.nng.igoprimoisrael.javaclient");
            AmsUtil.forceStopPackage(context, "com.nng.igo.primong.hun10th");
            AmsUtil.forceStopPackage(context, "com.estrongs.android.pop");
        }
        if (intent.getAction().equals("com.wits.pms.APPLY_MCU_CHECK_CAR")) {
            CenterControlImpl.getImpl().checkCarToMcu();
        }
    }
}
