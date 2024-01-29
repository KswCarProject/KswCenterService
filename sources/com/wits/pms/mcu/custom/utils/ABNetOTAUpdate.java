package com.wits.pms.mcu.custom.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.PowerManager;
import android.p007os.RemoteException;
import android.p007os.SystemProperties;
import android.p007os.UpdateEngine;
import android.p007os.UpdateEngineCallback;
import android.p007os.UserHandle;
import android.support.p014v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.FileUtil;
import java.io.File;
import java.text.DecimalFormat;

/* loaded from: classes2.dex */
public class ABNetOTAUpdate {
    private static final int MSG_REBOOT_AFTER_UPDATE = 4;
    private static final int MSG_UPDATE_FAIL = 2;
    private static final int MSG_UPDATE_PROGRESS = 1;
    private static final int MSG_UPDATE_RETRY = 5;
    private static final int MSG_UPDATE_SUCCESS = 3;
    private static final int MSG_UPDATE_VERSION_ERROR = 6;
    private static AlertDialog alertDialog1 = null;
    private static boolean isUpdating = false;
    private static Handler mHandler = null;
    private static final String metadataFileName = "/metadata";
    @SuppressLint({"SdCardPath"})
    private static final String otaFilePatch = "/sdcard/otapackage";
    private static final String payloadFileName = "/payload_properties.txt";
    private static final String targetDir = "/mnt/vendor/persist/ota";
    public static File tempFile;
    public static final String TAG = ABNetOTAUpdate.class.getSimpleName();
    private static int retryCount = 0;
    private static boolean wipeData = false;
    private static boolean forceUpdate = false;

    static /* synthetic */ int access$108() {
        int i = retryCount;
        retryCount = i + 1;
        return i;
    }

    public static boolean checkFile(Context context) {
        File parent = new File(otaFilePatch);
        String str = TAG;
        Log.m68i(str, "checkFile - /sdcard/otapackage  isUpdating = " + isUpdating);
        if (isUpdating) {
            return false;
        }
        int status = checkDirHasOTA(parent);
        if (status >= 1) {
            isUpdating = true;
            retryCount = 0;
            try {
                if (upPackZip() && checkFile()) {
                    startUpgrade(context);
                    return true;
                }
                isUpdating = false;
                Log.m72d(TAG, "checkFile: check ota file fail");
                WitsCommand.sendCommand(9, 102);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                isUpdating = false;
                Log.m64w(TAG, "checkFile: exception");
                WitsCommand.sendCommand(9, 102);
                return false;
            }
        }
        Log.m64w(TAG, "checkFile: no ota file");
        WitsCommand.sendCommand(9, 102);
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

    /* JADX WARN: Type inference failed for: r0v2, types: [com.wits.pms.mcu.custom.utils.ABNetOTAUpdate$2] */
    public static void startUpgrade(final Context context) {
        if (mHandler == null) {
            mHandler = new Handler(context.getMainLooper()) { // from class: com.wits.pms.mcu.custom.utils.ABNetOTAUpdate.1
                @Override // android.p007os.Handler
                public void handleMessage(Message msg) {
                    String str = ABNetOTAUpdate.TAG;
                    Log.m72d(str, "handleMessage   msg " + msg.what);
                    switch (msg.what) {
                        case 1:
                            WitsCommand.sendCommand(9, 104, "" + msg.arg1);
                            return;
                        case 2:
                            WitsCommand.sendCommand(9, 106);
                            Toast.makeText(context, context.getText(C3580R.string.update_fail), 1).show();
                            try {
                                PowerManagerApp.setBooleanStatus("ota_net_updated", false);
                                return;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return;
                            }
                        case 3:
                            SystemProperties.set("persist.wits.ota", "true");
                            Toast.makeText(context, context.getText(C3580R.string.update_success), 1).show();
                            WitsCommand.sendCommand(9, 105);
                            try {
                                PowerManagerApp.setBooleanStatus("ota_net_updated", true);
                                return;
                            } catch (RemoteException e2) {
                                e2.printStackTrace();
                                return;
                            }
                        case 4:
                            if (ABNetOTAUpdate.wipeData) {
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
                            ABNetOTAUpdate.access$108();
                            if (ABNetOTAUpdate.retryCount < 100) {
                                ABNetOTAUpdate.update();
                                return;
                            } else {
                                sendEmptyMessage(2);
                                return;
                            }
                        case 6:
                            Toast.makeText(context, (int) C3580R.string.update_version_error, 1).show();
                            sendEmptyMessage(2);
                            return;
                        default:
                            return;
                    }
                }
            };
        }
        Log.m72d(TAG, "startUpgrade: ");
        new Thread() { // from class: com.wits.pms.mcu.custom.utils.ABNetOTAUpdate.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                ABNetOTAUpdate.update();
                Looper.loop();
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void update() {
        try {
            if (!checkFile()) {
                Log.m72d(TAG, "update: checkfile return");
                mHandler.obtainMessage(2).sendToTarget();
                return;
            }
            String[] properties = FileUtil.getPayloadProperties("/mnt/vendor/persist/ota/payload_properties.txt");
            if (properties == null) {
                Log.m70e(TAG, "getPayloadProperties is null");
                mHandler.obtainMessage(2).sendToTarget();
                return;
            }
            String[] meta_properties = FileUtil.getMetaDataProperties("/mnt/vendor/persist/ota/metadata");
            if (properties == null) {
                Log.m70e(TAG, "getMetaDataProperties is null");
                mHandler.obtainMessage(2).sendToTarget();
                return;
            }
            final UpdateEngine updateEngine = new UpdateEngine();
            UpdateEngineCallback mUpdateEngineCallback = new UpdateEngineCallback() { // from class: com.wits.pms.mcu.custom.utils.ABNetOTAUpdate.3
                @Override // android.p007os.UpdateEngineCallback
                public void onStatusUpdate(int i, float v) {
                    String str = ABNetOTAUpdate.TAG;
                    Log.m72d(str, "onStatusUpdate   i = " + i + "   v = " + v);
                    if (i == 3) {
                        DecimalFormat df = new DecimalFormat("#");
                        String progress = df.format(100.0f * v);
                        String str2 = ABNetOTAUpdate.TAG;
                        Log.m72d(str2, "update progress: " + progress);
                        if (ABNetOTAUpdate.mHandler != null) {
                            ABNetOTAUpdate.mHandler.obtainMessage(1, Integer.parseInt(progress), 3).sendToTarget();
                        }
                    }
                }

                @Override // android.p007os.UpdateEngineCallback
                public void onPayloadApplicationComplete(int i) {
                    String str = ABNetOTAUpdate.TAG;
                    Log.m72d(str, "onPayloadApplicationComplete  i = " + i);
                    UpdateEngine.this.unbind();
                    if (i == 0) {
                        Log.m72d(ABNetOTAUpdate.TAG, "UPDATE SUCCESS!");
                        boolean unused = ABNetOTAUpdate.isUpdating = false;
                        ABNetOTAUpdate.mHandler.obtainMessage(3).sendToTarget();
                    } else if (i != 21) {
                        boolean unused2 = ABNetOTAUpdate.isUpdating = false;
                        ABNetOTAUpdate.mHandler.obtainMessage(2).sendToTarget();
                    } else {
                        Log.m72d(ABNetOTAUpdate.TAG, "onPayloadApplicationComplete: MSG_UPDATE_RETRY");
                        ABNetOTAUpdate.mHandler.obtainMessage(5).sendToTarget();
                    }
                }
            };
            updateEngine.bind(mUpdateEngineCallback);
            String[] first = meta_properties[0].split("=");
            long offset = Long.parseLong(first[1]);
            String[] second = meta_properties[1].split("=");
            long size = Long.parseLong(second[1]);
            String[] third = meta_properties[2].split("=");
            int version = Integer.parseInt(third[1]);
            String str = TAG;
            Log.m72d(str, "update from " + Integer.parseInt(Build.VERSION.RELEASE) + " to " + version);
            if (version < Integer.parseInt(Build.VERSION.RELEASE)) {
                mHandler.obtainMessage(6).sendToTarget();
                return;
            }
            String str2 = TAG;
            Log.m72d(str2, "size = " + size + "  tempFile = " + tempFile.getPath() + "  retryCount = " + retryCount);
            StringBuilder sb = new StringBuilder();
            sb.append("file://");
            sb.append(tempFile.getPath());
            updateEngine.applyPayload(sb.toString(), offset, size, properties);
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.obtainMessage(2).sendToTarget();
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.wits.pms.mcu.custom.utils.ABNetOTAUpdate$6] */
    private static void showUpdateDialog(final Context context) {
        final ProgressBar progressBar = new ProgressBar(context, null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        mHandler = new Handler(context.getMainLooper()) { // from class: com.wits.pms.mcu.custom.utils.ABNetOTAUpdate.4
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                String str = ABNetOTAUpdate.TAG;
                Log.m72d(str, "handleMessage   msg " + msg.what);
                switch (msg.what) {
                    case 1:
                        progressBar.setProgress(msg.arg1);
                        return;
                    case 2:
                        if (ABNetOTAUpdate.alertDialog1.isShowing()) {
                            ABNetOTAUpdate.alertDialog1.dismiss();
                        }
                        Toast.makeText(context, context.getText(C3580R.string.update_fail), 1).show();
                        return;
                    case 3:
                        if (ABNetOTAUpdate.alertDialog1.isShowing()) {
                            ABNetOTAUpdate.alertDialog1.dismiss();
                        }
                        SystemProperties.set("persist.wits.ota", "true");
                        Toast.makeText(context, context.getText(C3580R.string.update_success), 1).show();
                        ABNetOTAUpdate.mHandler.sendEmptyMessageDelayed(4, 2000L);
                        return;
                    case 4:
                        if (ABNetOTAUpdate.wipeData) {
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
                        ABNetOTAUpdate.access$108();
                        if (ABNetOTAUpdate.retryCount < 100) {
                            ABNetOTAUpdate.update();
                            return;
                        } else {
                            sendEmptyMessage(2);
                            return;
                        }
                    case 6:
                        Toast.makeText(context, (int) C3580R.string.update_version_error, 1).show();
                        sendEmptyMessage(2);
                        return;
                    default:
                        return;
                }
            }
        };
        if (!forceUpdate) {
            AlertDialog alertDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(true).setTitle(C3580R.string.ota_update).setMessage(C3580R.string.ota_update_message).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ABNetOTAUpdate$KoV_wtRcan2UdnRZRWm_a0OUrLU
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    ABNetOTAUpdate.isUpdating = false;
                }
            }).setNegativeButton(C3580R.string.no_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ABNetOTAUpdate$jouXG_SU1gJ7u7EsSA_eunGg5js
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ABNetOTAUpdate.lambda$showUpdateDialog$1(dialogInterface, i);
                }
            }).setPositiveButton(C3580R.string.yes_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ABNetOTAUpdate$-5GKR-f0m8ZtziZr7msN-kEH_Po
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ABNetOTAUpdate.lambda$showUpdateDialog$2(Context.this, progressBar, dialogInterface, i);
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
        new Thread() { // from class: com.wits.pms.mcu.custom.utils.ABNetOTAUpdate.6
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                ABNetOTAUpdate.update();
                Looper.loop();
            }
        }.start();
    }

    static /* synthetic */ void lambda$showUpdateDialog$1(DialogInterface dialog, int which) {
        dialog.dismiss();
        isUpdating = false;
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.wits.pms.mcu.custom.utils.ABNetOTAUpdate$5] */
    static /* synthetic */ void lambda$showUpdateDialog$2(Context context, ProgressBar progressBar, DialogInterface dialog, int which) {
        dialog.dismiss();
        alertDialog1 = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(false).setTitle(C3580R.string.ota_update).setView(progressBar).create();
        alertDialog1.getWindow().setType(2003);
        alertDialog1.show();
        new Thread() { // from class: com.wits.pms.mcu.custom.utils.ABNetOTAUpdate.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                ABNetOTAUpdate.update();
                Looper.loop();
            }
        }.start();
    }
}
