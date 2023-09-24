package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.PowerManager;
import android.p007os.SystemProperties;
import android.p007os.UpdateEngine;
import android.p007os.UpdateEngineCallback;
import android.p007os.UserHandle;
import android.support.p014v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.utils.FileUtil;
import java.io.File;
import java.text.DecimalFormat;

/* loaded from: classes2.dex */
public class ABOTAUpdate {
    private static final int MSG_REBOOT_AFTER_UPDATE = 4;
    private static final int MSG_UPDATE_FAIL = 2;
    private static final int MSG_UPDATE_PROGRESS = 1;
    private static final int MSG_UPDATE_RETRY = 5;
    private static final int MSG_UPDATE_SUCCESS = 3;
    private static AlertDialog alertDialog1 = null;
    private static boolean isUpdating = false;
    private static Handler mHandler = null;
    private static final String metadataFileName = "/metadata";
    private static final String payloadFileName = "/payload_properties.txt";
    private static final String targetDir = "/mnt/vendor/persist/ota";
    public static File tempFile;
    public static final String TAG = ABOTAUpdate.class.getSimpleName();
    private static int retryCount = 0;
    private static boolean wipeData = false;
    private static boolean forceUpdate = false;

    static /* synthetic */ int access$308() {
        int i = retryCount;
        retryCount = i + 1;
        return i;
    }

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.m68i(str, "checkFile - " + path + "  isUpdating = " + isUpdating);
        if (isUpdating) {
            return false;
        }
        int status = checkDirHasOTA(parent);
        if (status >= 1) {
            isUpdating = true;
            retryCount = 0;
            try {
                if (upPackZip() && checkFile()) {
                    showUpdateDialog(context);
                    return true;
                }
                isUpdating = false;
            } catch (Exception e) {
                e.printStackTrace();
                isUpdating = false;
            }
        }
        return false;
    }

    private static int checkDirHasOTA(File parent) {
        File[] listFiles;
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        for (File subFile : parent.listFiles()) {
            Log.m72d(TAG, "checkDirHasOTA   subFile = " + subFile.getName());
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
        boolean result = FileUtil.upPackZip(tempFile, "payload_properties.txt", new File("/mnt/vendor/persist/ota/payload_properties.txt"));
        return result & FileUtil.upPackZip(tempFile, "META-INF/com/android/metadata", new File("/mnt/vendor/persist/ota/metadata"));
    }

    private static boolean checkFile() {
        boolean result = FileUtil.checkFile("/mnt/vendor/persist/ota/payload_properties.txt");
        return result & FileUtil.checkFile("/mnt/vendor/persist/ota/metadata");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void update(Context context) {
        try {
            if (!checkFile()) {
                return;
            }
            String[] properties = FileUtil.getPayloadProperties("/mnt/vendor/persist/ota/payload_properties.txt");
            if (properties == null) {
                Log.m70e(TAG, "getPayloadProperties is null");
                return;
            }
            String[] meta_properties = FileUtil.getMetaDataProperties("/mnt/vendor/persist/ota/metadata");
            if (properties == null) {
                Log.m70e(TAG, "getMetaDataProperties is null");
                return;
            }
            final UpdateEngine updateEngine = new UpdateEngine();
            UpdateEngineCallback mUpdateEngineCallback = new UpdateEngineCallback() { // from class: com.wits.pms.mcu.custom.utils.ABOTAUpdate.1
                @Override // android.p007os.UpdateEngineCallback
                public void onStatusUpdate(int i, float v) {
                    String str = ABOTAUpdate.TAG;
                    Log.m72d(str, "onStatusUpdate   i = " + i + "   v = " + v);
                    if (i == 3) {
                        DecimalFormat df = new DecimalFormat("#");
                        String progress = df.format(100.0f * v);
                        String str2 = ABOTAUpdate.TAG;
                        Log.m72d(str2, "update progress: " + progress);
                        if (ABOTAUpdate.mHandler != null) {
                            ABOTAUpdate.mHandler.obtainMessage(1, Integer.parseInt(progress), 3).sendToTarget();
                        }
                    }
                }

                @Override // android.p007os.UpdateEngineCallback
                public void onPayloadApplicationComplete(int i) {
                    String str = ABOTAUpdate.TAG;
                    Log.m72d(str, "onPayloadApplicationComplete  i = " + i);
                    UpdateEngine.this.unbind();
                    if (i == 0) {
                        Log.m72d(ABOTAUpdate.TAG, "UPDATE SUCCESS!");
                        ABOTAUpdate.mHandler.obtainMessage(3).sendToTarget();
                    } else if (i == 21) {
                        ABOTAUpdate.mHandler.obtainMessage(5).sendToTarget();
                    } else {
                        ABOTAUpdate.mHandler.obtainMessage(2).sendToTarget();
                    }
                }
            };
            updateEngine.bind(mUpdateEngineCallback);
            String[] first = meta_properties[0].split("=");
            long offset = Long.parseLong(first[1]);
            String[] second = meta_properties[1].split("=");
            long size = Long.parseLong(second[1]);
            String str = TAG;
            Log.m72d(str, "size = " + size + " offset=" + offset + "  tempFile = " + tempFile.getPath() + "  retryCount = " + retryCount);
            StringBuilder sb = new StringBuilder();
            sb.append("file://");
            sb.append(tempFile.getPath());
            updateEngine.applyPayload(sb.toString(), offset, size, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.wits.pms.mcu.custom.utils.ABOTAUpdate$4] */
    private static void showUpdateDialog(final Context context) {
        final ProgressBar progressBar = new ProgressBar(context, null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        mHandler = new Handler(context.getMainLooper()) { // from class: com.wits.pms.mcu.custom.utils.ABOTAUpdate.2
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                String str = ABOTAUpdate.TAG;
                Log.m72d(str, "handleMessage   msg " + msg.what);
                switch (msg.what) {
                    case 1:
                        progressBar.setProgress(msg.arg1);
                        return;
                    case 2:
                        if (ABOTAUpdate.alertDialog1.isShowing()) {
                            ABOTAUpdate.alertDialog1.dismiss();
                        }
                        Toast.makeText(context, context.getText(C3580R.string.update_fail), 1).show();
                        return;
                    case 3:
                        if (ABOTAUpdate.alertDialog1.isShowing()) {
                            ABOTAUpdate.alertDialog1.dismiss();
                        }
                        SystemProperties.set("persist.wits.ota", "true");
                        Toast.makeText(context, context.getText(C3580R.string.update_success), 1).show();
                        ABOTAUpdate.mHandler.sendEmptyMessageDelayed(4, 2000L);
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
            AlertDialog alertDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(true).setTitle(C3580R.string.ota_update).setMessage(C3580R.string.ota_update_message).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ABOTAUpdate$2cXJ-dYPUxzrQMtgEennVqfBXrE
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    ABOTAUpdate.isUpdating = false;
                }
            }).setNegativeButton(C3580R.string.no_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ABOTAUpdate$rl3SEb30bi7FgPTvXI3AYWkfOus
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ABOTAUpdate.lambda$showUpdateDialog$1(dialogInterface, i);
                }
            }).setPositiveButton(C3580R.string.yes_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ABOTAUpdate$vXJRepbMybTCgE2po94b72OyTwE
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ABOTAUpdate.lambda$showUpdateDialog$2(Context.this, progressBar, dialogInterface, i);
                }
            }).create();
            alertDialog.getWindow().setType(2003);
            alertDialog.show();
            return;
        }
        if (alertDialog1 == null) {
            alertDialog1 = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(false).setTitle(C3580R.string.ota_update).setView(progressBar).create();
            alertDialog1.getWindow().setType(2003);
            alertDialog1.show();
        }
        new Thread() { // from class: com.wits.pms.mcu.custom.utils.ABOTAUpdate.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                ABOTAUpdate.update(Context.this);
                Looper.loop();
            }
        }.start();
    }

    static /* synthetic */ void lambda$showUpdateDialog$1(DialogInterface dialog, int which) {
        dialog.dismiss();
        isUpdating = false;
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.wits.pms.mcu.custom.utils.ABOTAUpdate$3] */
    static /* synthetic */ void lambda$showUpdateDialog$2(final Context context, ProgressBar progressBar, DialogInterface dialog, int which) {
        dialog.dismiss();
        alertDialog1 = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(false).setTitle(C3580R.string.ota_update).setView(progressBar).create();
        alertDialog1.getWindow().setType(2003);
        alertDialog1.show();
        new Thread() { // from class: com.wits.pms.mcu.custom.utils.ABOTAUpdate.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                ABOTAUpdate.update(Context.this);
                Looper.loop();
            }
        }.start();
    }
}
