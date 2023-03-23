package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.os.UserHandle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.utils.FileUtil;
import java.io.File;
import java.text.DecimalFormat;

public class ABOTAUpdate {
    private static final int MSG_REBOOT_AFTER_UPDATE = 4;
    private static final int MSG_UPDATE_FAIL = 2;
    private static final int MSG_UPDATE_PROGRESS = 1;
    private static final int MSG_UPDATE_RETRY = 5;
    private static final int MSG_UPDATE_SUCCESS = 3;
    public static final String TAG = ABOTAUpdate.class.getSimpleName();
    /* access modifiers changed from: private */
    public static AlertDialog alertDialog1 = null;
    private static boolean forceUpdate = false;
    /* access modifiers changed from: private */
    public static boolean isUpdating = false;
    /* access modifiers changed from: private */
    public static Handler mHandler = null;
    private static final String metadataFileName = "/metadata";
    private static final String payloadFileName = "/payload_properties.txt";
    /* access modifiers changed from: private */
    public static int retryCount = 0;
    private static final String targetDir = "/mnt/vendor/persist/ota";
    public static File tempFile;
    /* access modifiers changed from: private */
    public static boolean wipeData = false;

    static /* synthetic */ int access$308() {
        int i = retryCount;
        retryCount = i + 1;
        return i;
    }

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.i(str, "checkFile - " + path + "  isUpdating = " + isUpdating);
        if (!isUpdating && checkDirHasOTA(parent) >= 1) {
            isUpdating = true;
            retryCount = 0;
            try {
                if (!upPackZip() || !checkFile()) {
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
        for (File subFile : parent.listFiles()) {
            Log.d(TAG, "checkDirHasOTA   subFile = " + subFile.getName());
            if (FileUtil.checkOtaFile(subFile.getName())) {
                if (subFile.getName().contains("reset-data")) {
                    wipeData = true;
                } else {
                    wipeData = false;
                }
                if (subFile.getName().contains("force-update")) {
                    forceUpdate = true;
                } else {
                    forceUpdate = false;
                }
                tempFile = subFile;
                return 1;
            }
        }
        return -1;
    }

    private static boolean upPackZip() {
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return FileUtil.upPackZip(tempFile, "payload_properties.txt", new File("/mnt/vendor/persist/ota/payload_properties.txt")) & FileUtil.upPackZip(tempFile, "META-INF/com/android/metadata", new File("/mnt/vendor/persist/ota/metadata"));
    }

    private static boolean checkFile() {
        return FileUtil.checkFile("/mnt/vendor/persist/ota/payload_properties.txt") & FileUtil.checkFile("/mnt/vendor/persist/ota/metadata");
    }

    /* access modifiers changed from: private */
    public static void update(Context context) {
        try {
            if (checkFile()) {
                String[] properties = FileUtil.getPayloadProperties("/mnt/vendor/persist/ota/payload_properties.txt");
                if (properties == null) {
                    Log.e(TAG, "getPayloadProperties is null");
                    return;
                }
                String[] meta_properties = FileUtil.getMetaDataProperties("/mnt/vendor/persist/ota/metadata");
                if (properties == null) {
                    Log.e(TAG, "getMetaDataProperties is null");
                    return;
                }
                final UpdateEngine updateEngine = new UpdateEngine();
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
                        updateEngine.unbind();
                        if (i == 0) {
                            Log.d(ABOTAUpdate.TAG, "UPDATE SUCCESS!");
                            ABOTAUpdate.mHandler.obtainMessage(3).sendToTarget();
                        } else if (i == 21) {
                            ABOTAUpdate.mHandler.obtainMessage(5).sendToTarget();
                        } else {
                            ABOTAUpdate.mHandler.obtainMessage(2).sendToTarget();
                        }
                    }
                });
                long offset = Long.parseLong(meta_properties[0].split("=")[1]);
                long size = Long.parseLong(meta_properties[1].split("=")[1]);
                String str = TAG;
                Log.d(str, "size = " + size + " offset=" + offset + "  tempFile = " + tempFile.getPath() + "  retryCount = " + retryCount);
                StringBuilder sb = new StringBuilder();
                sb.append("file://");
                sb.append(tempFile.getPath());
                long j = size;
                updateEngine.applyPayload(sb.toString(), offset, size, properties);
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
                        SystemProperties.set("persist.wits.ota", "true");
                        Toast.makeText(context, context.getText(R.string.update_success), 1).show();
                        ABOTAUpdate.mHandler.sendEmptyMessageDelayed(4, 2000);
                        return;
                    case 4:
                        if (ABOTAUpdate.wipeData) {
                            Intent intent = new Intent(Intent.ACTION_FACTORY_RESET);
                            intent.setPackage("android");
                            intent.addFlags(268435456);
                            intent.putExtra(Intent.EXTRA_REASON, "MasterClearConfirm");
                            intent.putExtra(Intent.EXTRA_WIPE_EXTERNAL_STORAGE, false);
                            intent.putExtra(Intent.EXTRA_WIPE_ESIMS, false);
                            context.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(context.getApplicationInfo().uid));
                            return;
                        }
                        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                        if (pm != null) {
                            pm.reboot("");
                            return;
                        }
                        return;
                    case 5:
                        ABOTAUpdate.access$308();
                        if (ABOTAUpdate.retryCount < 100) {
                            ABOTAUpdate.update(context);
                            return;
                        } else {
                            sendEmptyMessage(2);
                            return;
                        }
                    default:
                        return;
                }
            }
        };
        if (!forceUpdate) {
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
            return;
        }
        if (alertDialog1 == null) {
            alertDialog1 = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(false).setTitle((int) R.string.ota_update).setView((View) progressBar).create();
            alertDialog1.getWindow().setType(2003);
            alertDialog1.show();
        }
        new Thread() {
            public void run() {
                Looper.prepare();
                ABOTAUpdate.update(context);
                Looper.loop();
            }
        }.start();
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
