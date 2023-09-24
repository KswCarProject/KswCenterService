package com.wits.pms.mcu.custom.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.p007os.PowerManager;
import android.util.Log;
import com.wits.pms.C3580R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class WlanFirmwareUpdate {
    private static final String TAG = "WlanFirmwareUpdate";
    private static final String destFilePath = "/vendor/firmware_mnt/image/wlanmdsp.mbn";
    private static final String srcFilePath = "/vendor/etc/wifi/wlanmdsp.mbn";

    public static void checkUpdata(Context context) {
        String srcVersion = getFirmwareVersion(srcFilePath);
        String destVersion = getFirmwareVersion(destFilePath);
        Log.m72d(TAG, "srcVersion=" + srcVersion + ",destVersion=" + destVersion);
        if (srcVersion != null && srcVersion.equals("WLAN.HL.3.2.4-01022") && srcVersion != null && !destVersion.equals("WLAN.HL.3.2.4-01022")) {
            Log.m72d(TAG, "wifi firmware update...");
            boolean result = copyFirmware(srcFilePath, destFilePath);
            if (result) {
                Log.m72d(TAG, "wifi firmware update success");
                showUpdateDialog(context);
            }
        }
    }

    public static String getFirmwareVersion(String path) {
        File filename;
        try {
            filename = new File(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!filename.exists()) {
            Log.m72d(TAG, path + " is not exits");
            return null;
        }
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
        byte[] temp = new byte[1024];
        byte[] result = new byte[19];
        int targetTotal = (2916456 / 1024) * 1024;
        int total = 0;
        do {
            int size = in.read(temp);
            if (size == -1) {
                return null;
            }
            total += size;
        } while (total <= targetTotal);
        for (int i = 0; i < 19; i++) {
            result[i] = temp[(2916456 - targetTotal) + i];
        }
        in.close();
        return new String(result);
    }

    public static boolean copyFirmware(String srcPath, String destPath) {
        try {
            File target = new File(destPath);
            if (target.exists()) {
                if (target.delete()) {
                    Log.m72d(TAG, "delete target file success...");
                } else {
                    Log.m72d(TAG, "delete target file failed...");
                }
            } else {
                Log.m72d(TAG, "target file not exits...");
            }
            if (target.createNewFile()) {
                Log.m72d(TAG, "create target file success...");
                InputStream inputStream = new FileInputStream(new File(srcPath));
                OutputStream outputStream = new FileOutputStream(target);
                byte[] data = new byte[1024];
                while (true) {
                    int size = inputStream.read(data);
                    if (size != -1) {
                        outputStream.write(data, 0, size);
                    } else {
                        inputStream.close();
                        outputStream.close();
                        return true;
                    }
                }
            } else {
                Log.m72d(TAG, "create target file failed...");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void showUpdateDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(true).setTitle(C3580R.string.wifi_firmware_update).setMessage(C3580R.string.wifi_firmware_update_message).setNegativeButton(C3580R.string.no_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$WlanFirmwareUpdate$3jnvdmReSm3shSDjSLsThXCyBtQ
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WlanFirmwareUpdate.lambda$showUpdateDialog$0(dialogInterface, i);
            }
        }).setPositiveButton(C3580R.string.yes_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$WlanFirmwareUpdate$i_9dqpYGl66ftSwAQCk0T6mixJ8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WlanFirmwareUpdate.lambda$showUpdateDialog$1(Context.this, dialogInterface, i);
            }
        }).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
    }

    static /* synthetic */ void lambda$showUpdateDialog$0(DialogInterface dialog, int which) {
        Log.m72d(TAG, "cancel reboot");
        dialog.dismiss();
    }

    static /* synthetic */ void lambda$showUpdateDialog$1(Context context, DialogInterface dialog, int which) {
        dialog.dismiss();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            pm.reboot("");
        }
    }
}
