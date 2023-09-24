package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RecoverySystem;
import android.provider.Settings;
import android.support.p014v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.mirror.SystemProperties;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class OTAUpdate {
    private static final String OTAFileName;
    private static final String OTAFileName10;
    private static final String OTAFileName9;
    private static boolean isChecking;
    private static boolean isUpdating;
    private static Handler mHandler;
    public static File tempFile;
    public static final String TAG = OTAUpdate.class.getName();
    public static boolean test = false;
    private static String device = Build.DEVICE;
    private static String OSVersion = Build.VERSION.RELEASE;
    private static String version = Build.DISPLAY;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("Ksw-P-");
        sb.append(device.contains("8937") ? "S-" : "");
        sb.append("Userdebug_OS_v");
        OTAFileName9 = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Ksw-Q-");
        sb2.append(device.contains("8937") ? version.contains("8937") ? "8937-" : "S-" : "");
        sb2.append("Userdebug_OS_v");
        OTAFileName10 = sb2.toString();
        OTAFileName = OSVersion.equals("9") ? OTAFileName9 : OTAFileName10;
    }

    private static int checkDirHasOTA(File parent) {
        File[] listFiles;
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        String phoneId = Build.DISPLAY;
        String versionCode = phoneId.replace("Ksw-Q-Userdebug OS v", "").replace(".", "");
        for (File subFile : parent.listFiles()) {
            Log.m72d(TAG, "checkDirHasOTA   subFile = " + subFile.getName() + "  OTAFileName = " + OTAFileName);
            if (subFile.getName().contains(OTAFileName) && subFile.getName().contains("ota.zip")) {
                String targetVersion = getVersion(subFile);
                tempFile = subFile;
                Log.m66v(TAG, "has update file: targetVersion - " + targetVersion + " - " + versionCode);
                return 1;
            }
        }
        return -1;
    }

    private static String getVersion(File file) {
        String version2 = file.getName().replace(OTAFileName, "").replace("-ota.zip", "").replace(".", "");
        String str = TAG;
        Log.m68i(str, "getVersion" + version2);
        return version2;
    }

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.m68i(str, "checkFile - " + path);
        if (isUpdating) {
            return false;
        }
        int status = checkDirHasOTA(parent);
        if (status >= 1) {
            isUpdating = true;
            showUpdateDialog(context);
            return true;
        }
        String version2 = Settings.System.getString(context.getContentResolver(), "ota_update");
        if (!TextUtils.isEmpty(version2)) {
            File updateFile = new File("/storage/emulated/0/" + OTAFileName + version2 + "-ota.zip");
            if (updateFile.exists()) {
                updateFile.delete();
            }
            reinstallApk();
            Toast.makeText(context, (int) C3580R.string.update_success, 0).show();
            Settings.System.putString(context.getContentResolver(), "ota_update", "");
        }
        if (tempFile != null && tempFile.getParent().contains("/0")) {
            tempFile.delete();
        }
        return false;
    }

    public static void reinstallApk() {
        Log.m68i(TAG, "OTA Update apk. Reinstall!");
        SystemProperties.set("persist.rebuild.apk", "0");
        SystemProperties.set("persist.rebuild.apk", "1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void update(Context context, File tempFile2) {
        File realPath;
        try {
            if (tempFile2.getAbsolutePath().contains("storage/emulated")) {
                realPath = new File("/data/media/0/" + tempFile2.getName());
            } else {
                realPath = new File("/dev/ota_mnt/" + tempFile2.getName());
            }
            android.p007os.SystemProperties.set("persist.wits.ota", "true");
            RecoverySystem.installPackage(context, realPath);
        } catch (IOException e) {
        }
    }

    private static boolean copyToUpdate(File file) {
        Log.m68i(TAG, "Ready. copy to /data/media/0/");
        File target = new File("/data/media/0/" + file.getName());
        tempFile = target;
        int i = 0;
        if (target.exists() && !test) {
            Log.m68i(TAG, "ota file already exist. show update now");
            mHandler.obtainMessage(99, 100, 0).sendToTarget();
            return true;
        }
        try {
            long count = file.length();
            if (file.exists()) {
                Log.m68i(TAG, "start copy" + file.getName() + " to /data/media/0/");
                InputStream inStream = new FileInputStream(file.getPath());
                FileOutputStream fs = new FileOutputStream("/storage/emulated/0/" + file.getName());
                FileDescriptor fd = fs.getFD();
                byte[] buffer = new byte[1024];
                int bytesum = 0;
                int progress = -1;
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread == -1) {
                        break;
                    }
                    bytesum += byteread;
                    fs.write(buffer, i, byteread);
                    if (progress != ((int) (bytesum / (count / 100))) && progress != 100) {
                        progress = (int) (bytesum / (count / 100));
                        Log.m68i(TAG, "progress: " + progress);
                        mHandler.obtainMessage(99, progress, 0).sendToTarget();
                    }
                    i = 0;
                }
                inStream.close();
                fs.flush();
                fd.sync();
                fs.close();
                if (!test) {
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(99, 100, 0), 60000L);
                }
            }
            Log.m68i(TAG, "copy success, update now");
            return true;
        } catch (Exception e) {
            Log.m69e(TAG, "error", e);
            if (target.exists()) {
                target.delete();
                return false;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showUpdateDialog(final Context context) {
        final ProgressBar progressBar = new ProgressBar(context, null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        mHandler = new Handler(context.getMainLooper()) { // from class: com.wits.pms.mcu.custom.utils.OTAUpdate.1
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    OTAUpdate.showUpdateDialog(context);
                }
                if (msg.what == 99) {
                    int progress = msg.arg1;
                    String str = OTAUpdate.TAG;
                    Log.m66v(str, "copying file in progress: " + progress);
                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        try {
                            RecoverySystem.installPackage(context, OTAUpdate.tempFile);
                            boolean unused = OTAUpdate.isChecking = false;
                        } catch (IOException e) {
                        }
                    }
                }
            }
        };
        AlertDialog alertDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(true).setTitle(C3580R.string.ota_update).setMessage(C3580R.string.ota_update_message).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$OTAUpdate$6FQxO82I90YiE1q7Sjy_xP3o7Pg
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                OTAUpdate.isUpdating = false;
            }
        }).setNegativeButton(C3580R.string.no_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$OTAUpdate$TGn6-oUqs-Xc_FFgiDcru9tSAyk
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                OTAUpdate.lambda$showUpdateDialog$1(dialogInterface, i);
            }
        }).setPositiveButton(C3580R.string.yes_update, new DialogInterface.OnClickListener() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$OTAUpdate$8Avz8ppopcm10SyzVVk1PHFblss
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                OTAUpdate.lambda$showUpdateDialog$2(Context.this, progressBar, dialogInterface, i);
            }
        }).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
    }

    static /* synthetic */ void lambda$showUpdateDialog$1(DialogInterface dialog, int which) {
        dialog.dismiss();
        isUpdating = false;
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.wits.pms.mcu.custom.utils.OTAUpdate$2] */
    static /* synthetic */ void lambda$showUpdateDialog$2(final Context context, ProgressBar progressBar, DialogInterface dialog, int which) {
        Settings.System.putString(context.getContentResolver(), "ota_update", getVersion(tempFile));
        dialog.dismiss();
        AlertDialog alertDialog1 = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(false).setTitle(C3580R.string.copy_file).setView(progressBar).create();
        alertDialog1.getWindow().setType(2003);
        alertDialog1.show();
        new Thread() { // from class: com.wits.pms.mcu.custom.utils.OTAUpdate.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                OTAUpdate.update(Context.this, OTAUpdate.tempFile);
                if (OTAUpdate.test) {
                    OTAUpdate.mHandler.obtainMessage(0).sendToTarget();
                    boolean unused = OTAUpdate.isUpdating = false;
                }
                Looper.loop();
            }
        }.start();
    }
}
