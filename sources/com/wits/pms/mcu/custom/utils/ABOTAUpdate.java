package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.utils.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ABOTAUpdate {
    private static final int MSG_REBOOT_AFTER_UPDATE = 4;
    private static final int MSG_UPDATE_FAIL = 2;
    private static final int MSG_UPDATE_PROGRESS = 1;
    private static final int MSG_UPDATE_SUCCESS = 3;
    private static final String OTAFileName = "Ksw-R-M600_OS_v";
    public static final String TAG = ABOTAUpdate.class.getSimpleName();
    /* access modifiers changed from: private */
    public static AlertDialog alertDialog1 = null;
    /* access modifiers changed from: private */
    public static boolean isUpdating = false;
    /* access modifiers changed from: private */
    public static Handler mHandler = null;
    private static final String payloadDir = "/mnt/vendor/persist/";
    private static final String payloadFileName = "payload_properties.txt";
    public static File tempFile;

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.i(str, "checkFile - " + path + "  isUpdating = " + isUpdating);
        if (!isUpdating && checkDirHasOTA(parent) >= 1) {
            isUpdating = true;
            try {
                if (!upPackZip() || !checkPayloadFile()) {
                    isUpdating = false;
                } else {
                    showUpdateDialog(context);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                isUpdating = false;
            }
        }
        return false;
    }

    private static int checkDirHasOTA(File parent) {
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        File[] listFiles = parent.listFiles();
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            File subFile = listFiles[i];
            String str = TAG;
            Log.d(str, "checkDirHasOTA   subFile = " + subFile.getName() + "  OTAFileName = " + OTAFileName);
            if (!subFile.getName().contains(OTAFileName) || !subFile.getName().contains("ota.zip")) {
                i++;
            } else {
                tempFile = subFile;
                return 1;
            }
        }
        return -1;
    }

    private static boolean upPackZip() {
        try {
            ZipFile zip = new ZipFile(tempFile, Charset.forName("GBK"));
            ZipEntry zipEntry = zip.getEntry(payloadFileName);
            if (zipEntry == null) {
                return false;
            }
            File payloadFile = new File("/mnt/vendor/persist/payload_properties.txt");
            InputStream in = zip.getInputStream(zipEntry);
            if (payloadFile.exists()) {
                payloadFile.delete();
            }
            OutputStream out = new FileOutputStream(payloadFile);
            byte[] buf1 = new byte[1024];
            while (true) {
                int read = in.read(buf1);
                int len = read;
                if (read > 0) {
                    out.write(buf1, 0, len);
                } else {
                    in.close();
                    out.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean checkPayloadFile() {
        if (new File("/mnt/vendor/persist/payload_properties.txt").exists()) {
            return true;
        }
        Log.e(TAG, "payloadPath not exist");
        return false;
    }

    /* access modifiers changed from: private */
    public static void update(Context context) {
        try {
            if (checkPayloadFile()) {
                String[] properties = FileUtil.getPayloadProperties("/mnt/vendor/persist/payload_properties.txt");
                if (properties == null) {
                    Log.e(TAG, "getPayloadProperties is null");
                    return;
                }
                UpdateEngine updateEngine = new UpdateEngine();
                updateEngine.bind(new UpdateEngineCallback() {
                    public void onStatusUpdate(int i, float v) {
                        String str = ABOTAUpdate.TAG;
                        Log.d(str, "onStatusUpdate   i = " + i + "   v = " + v);
                        if (i == 3) {
                            String progress = new DecimalFormat("#").format((double) (100.0f * v));
                            String str2 = ABOTAUpdate.TAG;
                            Log.d(str2, "update progress: " + progress);
                            if (ABOTAUpdate.mHandler != null) {
                                ABOTAUpdate.mHandler.obtainMessage(1, Integer.parseInt(progress), 3).sendToTarget();
                            }
                        }
                    }

                    public void onPayloadApplicationComplete(int i) {
                        String str = ABOTAUpdate.TAG;
                        Log.d(str, "onPayloadApplicationComplete  i = " + i);
                        if (i == 0) {
                            Log.d(ABOTAUpdate.TAG, "UPDATE SUCCESS!");
                            ABOTAUpdate.mHandler.obtainMessage(3).sendToTarget();
                            return;
                        }
                        ABOTAUpdate.mHandler.obtainMessage(2).sendToTarget();
                    }
                });
                long size = Long.parseLong(properties[1].split("=")[1]);
                String str = TAG;
                Log.d(str, "size = " + size + "  tempFile = " + tempFile.getPath());
                StringBuilder sb = new StringBuilder();
                sb.append("file://");
                sb.append(tempFile.getPath());
                updateEngine.applyPayload(sb.toString(), 649, size, properties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showUpdateDialog(final Context context) {
        final ProgressBar progressBar = new ProgressBar(context, (AttributeSet) null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                String str = ABOTAUpdate.TAG;
                Log.d(str, "handleMessage   msg " + msg.what);
                switch (msg.what) {
                    case 1:
                        progressBar.setProgress(msg.arg1);
                        return;
                    case 2:
                        if (ABOTAUpdate.alertDialog1.isShowing()) {
                            ABOTAUpdate.alertDialog1.dismiss();
                        }
                        Toast.makeText(context, context.getText(R.string.update_fail), 1).show();
                        return;
                    case 3:
                        if (ABOTAUpdate.alertDialog1.isShowing()) {
                            ABOTAUpdate.alertDialog1.dismiss();
                        }
                        Toast.makeText(context, context.getText(R.string.update_success), 1).show();
                        ABOTAUpdate.mHandler.sendEmptyMessageDelayed(4, 2000);
                        return;
                    case 4:
                        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                        if (pm != null) {
                            pm.reboot("");
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(true).setTitle((int) R.string.ota_update).setMessage((int) R.string.ota_update_message).setOnDismissListener($$Lambda$ABOTAUpdate$2cXJdYPUxzrQMtgEennVqfBXrE.INSTANCE).setNegativeButton((int) R.string.no_update, (DialogInterface.OnClickListener) $$Lambda$ABOTAUpdate$rl3SEb30bi7FgPTvXI3AYWkfOus.INSTANCE).setPositiveButton((int) R.string.yes_update, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener(progressBar) {
            private final /* synthetic */ ProgressBar f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(DialogInterface dialogInterface, int i) {
                ABOTAUpdate.lambda$showUpdateDialog$2(Context.this, this.f$1, dialogInterface, i);
            }
        }).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
    }

    static /* synthetic */ void lambda$showUpdateDialog$1(DialogInterface dialog, int which) {
        dialog.dismiss();
        isUpdating = false;
    }

    static /* synthetic */ void lambda$showUpdateDialog$2(final Context context, ProgressBar progressBar, DialogInterface dialog, int which) {
        dialog.dismiss();
        alertDialog1 = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(false).setTitle((int) R.string.ota_update).setView((View) progressBar).create();
        alertDialog1.getWindow().setType(2003);
        alertDialog1.show();
        new Thread() {
            public void run() {
                Looper.prepare();
                ABOTAUpdate.update(context);
                Looper.loop();
            }
        }.start();
    }
}
