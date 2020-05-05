package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RecoverySystem;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.BuildConfig;
import com.wits.pms.R;
import com.wits.pms.utils.SystemProperties;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OTAUpdate {
    private static String OSVersion = Build.VERSION.RELEASE;
    private static final String OTAFileName = (OSVersion.equals("9") ? OTAFileName9 : OTAFileName10);
    private static final String OTAFileName10;
    private static final String OTAFileName9;
    public static final String TAG = OTAUpdate.class.getName();
    private static String device = Build.DEVICE;
    /* access modifiers changed from: private */
    public static boolean isChecking;
    /* access modifiers changed from: private */
    public static boolean isUpdating;
    /* access modifiers changed from: private */
    public static Handler mHandler;
    public static File tempFile;
    public static boolean test = false;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("Ksw-P-");
        sb.append(device.contains("8937") ? "S-" : BuildConfig.FLAVOR);
        sb.append("Userdebug_OS_v");
        OTAFileName9 = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Ksw-Q-");
        sb2.append(device.contains("8937") ? "S-" : BuildConfig.FLAVOR);
        sb2.append("Userdebug_OS_v");
        OTAFileName10 = sb2.toString();
    }

    private static int checkDirHasOTA(File parent) {
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        String versionCode = Build.DISPLAY.replace("Ksw-Q-Userdebug OS v", BuildConfig.FLAVOR).replace(".", BuildConfig.FLAVOR);
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
                String targetVersion = getVersion(subFile);
                tempFile = subFile;
                String str2 = TAG;
                Log.v(str2, "has update file: targetVersion - " + targetVersion + " - " + versionCode);
                return 1;
            }
        }
        return -1;
    }

    private static String getVersion(File file) {
        String version = file.getName().replace(OTAFileName, BuildConfig.FLAVOR).replace("-ota.zip", BuildConfig.FLAVOR).replace(".", BuildConfig.FLAVOR);
        String str = TAG;
        Log.i(str, "getVersion" + version);
        return version;
    }

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.i(str, "checkFile - " + path);
        if (isUpdating) {
            return false;
        }
        if (checkDirHasOTA(parent) >= 1) {
            isUpdating = true;
            showUpdateDialog(context);
            return true;
        }
        String version = Settings.System.getString(context.getContentResolver(), "ota_update");
        if (!TextUtils.isEmpty(version)) {
            File updateFile = new File("/storage/emulated/0/" + OTAFileName + version + "-ota.zip");
            if (updateFile.exists()) {
                updateFile.delete();
            }
            reinstallApk();
            Toast.makeText(context, R.string.update_success, 0).show();
            Settings.System.putString(context.getContentResolver(), "ota_update", BuildConfig.FLAVOR);
        }
        if (tempFile != null && tempFile.getParent().contains("/0")) {
            tempFile.delete();
        }
        return false;
    }

    public static void reinstallApk() {
        Log.i(TAG, "OTA Update apk. Reinstall!");
        SystemProperties.set("persist.rebuild.apk", "0");
        SystemProperties.set("persist.rebuild.apk", "1");
    }

    /* access modifiers changed from: private */
    public static void update(Context context, File tempFile2) {
        File realPath;
        try {
            if (tempFile2.getAbsolutePath().contains("storage/emulated")) {
                realPath = new File("/data/media/0/" + tempFile2.getName());
            } else {
                realPath = new File("/dev/ota_mnt/" + tempFile2.getName());
            }
            RecoverySystem.installPackage(context, realPath);
        } catch (IOException e) {
        }
    }

    private static boolean copyToUpdate(File file) {
        Log.i(TAG, "Ready. copy to /data/media/0/");
        File target = new File("/data/media/0/" + file.getName());
        tempFile = target;
        int i = 0;
        if (!target.exists() || test) {
            try {
                long count = file.length();
                if (file.exists()) {
                    String str = TAG;
                    Log.i(str, "start copy" + file.getName() + " to /data/media/0/");
                    InputStream inStream = new FileInputStream(file.getPath());
                    FileOutputStream fs = new FileOutputStream("/storage/emulated/0/" + file.getName());
                    FileDescriptor fd = fs.getFD();
                    byte[] buffer = new byte[1024];
                    int bytesum = 0;
                    int progress = -1;
                    while (true) {
                        int read = inStream.read(buffer);
                        int byteread = read;
                        if (read == -1) {
                            break;
                        }
                        bytesum += byteread;
                        fs.write(buffer, i, byteread);
                        if (!(progress == ((int) (((long) bytesum) / (count / 100))) || progress == 100)) {
                            progress = (int) (((long) bytesum) / (count / 100));
                            String str2 = TAG;
                            Log.i(str2, "progress: " + progress);
                            mHandler.obtainMessage(99, progress, 0).sendToTarget();
                        }
                        i = 0;
                    }
                    inStream.close();
                    fs.flush();
                    fd.sync();
                    if (!test) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(99, 100, 0), 60000);
                    }
                }
                Log.i(TAG, "copy success, update now");
                return true;
            } catch (Exception e) {
                Log.e(TAG, "error", e);
                if (!target.exists()) {
                    return false;
                }
                target.delete();
                return false;
            }
        } else {
            Log.i(TAG, "ota file already exist. show update now");
            mHandler.obtainMessage(99, 100, 0).sendToTarget();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public static void showUpdateDialog(final Context context) {
        final ProgressBar progressBar = new ProgressBar(context, (AttributeSet) null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    OTAUpdate.showUpdateDialog(context);
                }
                if (msg.what == 99) {
                    int progress = msg.arg1;
                    String str = OTAUpdate.TAG;
                    Log.v(str, "copying file in progress: " + progress);
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
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(true).setTitle((int) R.string.ota_update).setMessage((int) R.string.ota_update_message).setOnDismissListener(OTAUpdate$$Lambda$0.$instance).setNegativeButton((int) R.string.no_update, OTAUpdate$$Lambda$1.$instance).setPositiveButton((int) R.string.yes_update, (DialogInterface.OnClickListener) new OTAUpdate$$Lambda$2(context, progressBar)).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
    }

    static final /* synthetic */ void lambda$showUpdateDialog$1$OTAUpdate(DialogInterface dialog, int which) {
        dialog.dismiss();
        isUpdating = false;
    }

    static final /* synthetic */ void lambda$showUpdateDialog$2$OTAUpdate(final Context context, ProgressBar progressBar, DialogInterface dialog, int which) {
        Settings.System.putString(context.getContentResolver(), "ota_update", getVersion(tempFile));
        dialog.dismiss();
        AlertDialog alertDialog1 = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(false).setTitle((int) R.string.copy_file).setView((View) progressBar).create();
        alertDialog1.getWindow().setType(2003);
        alertDialog1.show();
        new Thread() {
            public void run() {
                Looper.prepare();
                OTAUpdate.update(context, OTAUpdate.tempFile);
                if (OTAUpdate.test) {
                    OTAUpdate.mHandler.obtainMessage(0).sendToTarget();
                    boolean unused = OTAUpdate.isUpdating = false;
                }
                Looper.loop();
            }
        }.start();
    }
}
