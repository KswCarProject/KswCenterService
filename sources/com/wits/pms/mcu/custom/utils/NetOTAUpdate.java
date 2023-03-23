package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.PowerManagerApp;
import com.wits.pms.R;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NetOTAUpdate {
    private static final int MSG_REBOOT_AFTER_UPDATE = 4;
    private static final int MSG_UPDATE_FAIL = 2;
    private static final int MSG_UPDATE_PROGRESS = 1;
    private static final int MSG_UPDATE_RETRY = 5;
    private static final int MSG_UPDATE_SUCCESS = 3;
    private static String OSVersion = Build.VERSION.RELEASE;
    private static final String OTAFileName = (OSVersion.equals("9") ? OTAFileName9 : OTAFileName10);
    private static final String OTAFileName10;
    private static final String OTAFileName9;
    public static final String TAG = NetOTAUpdate.class.getName();
    private static String device = Build.DEVICE;
    /* access modifiers changed from: private */
    public static boolean isChecking = false;
    /* access modifiers changed from: private */
    public static boolean isUpdating = false;
    /* access modifiers changed from: private */
    public static Handler mHandler = null;
    private static final String otaFilePatch = "/sdcard/otapackage";
    /* access modifiers changed from: private */
    public static int retryCount = 0;
    public static File tempFile;
    public static boolean test = false;
    private static String version = Build.DISPLAY;
    /* access modifiers changed from: private */
    public static boolean wipeData = false;

    static /* synthetic */ int access$108() {
        int i = retryCount;
        retryCount = i + 1;
        return i;
    }

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
    }

    private static int checkDirHasOTA(File parent) {
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        String versionCode = Build.DISPLAY.replace("Ksw-Q-Userdebug OS v", "").replace(".", "");
        for (File subFile : parent.listFiles()) {
            Log.d(TAG, "checkDirHasOTA   subFile = " + subFile.getName() + "  OTAFileName = " + OTAFileName);
            if (subFile.getName().contains(OTAFileName)) {
                String targetVersion = getVersion(subFile);
                tempFile = subFile;
                Log.v(TAG, "has update file: targetVersion - " + targetVersion + " - " + versionCode);
                return 1;
            }
        }
        return -1;
    }

    private static String getVersion(File file) {
        String version2 = file.getName().replace(OTAFileName, "").replace("-ota.zip", "").replace(".", "");
        String str = TAG;
        Log.i(str, "getVersion" + version2);
        return version2;
    }

    public static boolean checkFile(Context context) {
        File parent = new File(otaFilePatch);
        Log.i(TAG, "checkFile - /sdcard/otapackage");
        if (isUpdating) {
            return false;
        }
        if (checkDirHasOTA(parent) >= 1) {
            isUpdating = true;
            startUpgrade(context);
            return true;
        }
        String version2 = Settings.System.getString(context.getContentResolver(), "ota_update");
        if (!TextUtils.isEmpty(version2)) {
            File updateFile = new File("/storage/emulated/0/" + OTAFileName + version2 + "-ota.zip");
            if (updateFile.exists()) {
                updateFile.delete();
            }
            reinstallApk();
            Toast.makeText(context, (int) R.string.update_success, 0).show();
            Settings.System.putString(context.getContentResolver(), "ota_update", "");
        }
        if (tempFile != null && tempFile.getParent().contains("/0")) {
            tempFile.delete();
        }
        Log.w(TAG, "checkFile: no ota file");
        WitsCommand.sendCommand(9, 102);
        return false;
    }

    public static void reinstallApk() {
        Log.i(TAG, "OTA Update apk. Reinstall!");
        SystemProperties.set("persist.rebuild.apk", "0");
        SystemProperties.set("persist.rebuild.apk", "1");
    }

    public static void startUpgrade(final Context context) {
        if (mHandler == null) {
            mHandler = new Handler(context.getMainLooper()) {
                public void handleMessage(Message msg) {
                    String str = NetOTAUpdate.TAG;
                    Log.d(str, "handleMessage   msg " + msg.what);
                    switch (msg.what) {
                        case 1:
                            WitsCommand.sendCommand(9, 104, "" + msg.arg1);
                            return;
                        case 2:
                            WitsCommand.sendCommand(9, 106);
                            Toast.makeText(context, context.getText(R.string.update_fail), 1).show();
                            try {
                                PowerManagerApp.setBooleanStatus("ota_net_updated", false);
                                return;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return;
                            }
                        case 3:
                            android.os.SystemProperties.set("persist.wits.ota", "true");
                            Toast.makeText(context, context.getText(R.string.update_success), 1).show();
                            WitsCommand.sendCommand(9, 105);
                            try {
                                PowerManagerApp.setBooleanStatus("ota_net_updated", true);
                                return;
                            } catch (RemoteException e2) {
                                e2.printStackTrace();
                                return;
                            }
                        case 4:
                            if (NetOTAUpdate.wipeData) {
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
                            NetOTAUpdate.access$108();
                            if (NetOTAUpdate.retryCount < 100) {
                                NetOTAUpdate.update(context, NetOTAUpdate.tempFile);
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
        }
        Log.d(TAG, "startUpgrade: ");
        new Thread() {
            public void run() {
                Looper.prepare();
                NetOTAUpdate.update(context, NetOTAUpdate.tempFile);
                if (NetOTAUpdate.test) {
                    NetOTAUpdate.mHandler.obtainMessage(0).sendToTarget();
                    boolean unused = NetOTAUpdate.isUpdating = false;
                }
                Looper.loop();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public static void update(Context context, File tempFile2) {
        File realPath;
        try {
            if (tempFile2.getAbsolutePath().contains("storage/emulated")) {
                realPath = new File("/data/media/0/" + tempFile2.getName());
            } else if (tempFile2.getAbsolutePath().contains("sdcard/otapackage")) {
                realPath = new File("/data/media/0/otapackage/" + tempFile2.getName());
            } else {
                realPath = new File("/dev/ota_mnt/" + tempFile2.getName());
            }
            android.os.SystemProperties.set("persist.wits.ota", "true");
            RecoverySystem.installPackage(context, realPath);
        } catch (IOException e) {
            e.printStackTrace();
            mHandler.obtainMessage(2).sendToTarget();
        }
    }

    private static boolean copyToUpdate(File file) {
        int byteread;
        Log.i(TAG, "Ready. copy to /data/media/0/");
        File target = new File("/data/media/0/" + file.getName());
        tempFile = target;
        int i = 0;
        if (!target.exists() || test) {
            try {
                long count = file.length();
                if (file.exists()) {
                    Log.i(TAG, "start copy" + file.getName() + " to /data/media/0/");
                    InputStream inStream = new FileInputStream(file.getPath());
                    FileOutputStream fs = new FileOutputStream("/storage/emulated/0/" + file.getName());
                    FileDescriptor fd = fs.getFD();
                    byte[] buffer = new byte[1024];
                    int bytesum = 0;
                    int progress = -1;
                    while (true) {
                        int read = inStream.read(buffer);
                        byteread = read;
                        if (read == -1) {
                            break;
                        }
                        bytesum += byteread;
                        fs.write(buffer, i, byteread);
                        if (!(progress == ((int) (((long) bytesum) / (count / 100))) || progress == 100)) {
                            progress = (int) (((long) bytesum) / (count / 100));
                            Log.i(TAG, "progress: " + progress);
                            mHandler.obtainMessage(99, progress, 0).sendToTarget();
                        }
                        i = 0;
                    }
                    inStream.close();
                    fs.flush();
                    fd.sync();
                    if (!test) {
                        int i2 = byteread;
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
                    NetOTAUpdate.showUpdateDialog(context);
                }
                if (msg.what == 99) {
                    int progress = msg.arg1;
                    String str = NetOTAUpdate.TAG;
                    Log.v(str, "copying file in progress: " + progress);
                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        try {
                            RecoverySystem.installPackage(context, NetOTAUpdate.tempFile);
                            boolean unused = NetOTAUpdate.isChecking = false;
                        } catch (IOException e) {
                        }
                    }
                }
            }
        };
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(true).setTitle((int) R.string.ota_update).setMessage((int) R.string.ota_update_message).setOnDismissListener($$Lambda$NetOTAUpdate$HyYqoMWJQx4CfGjFkRF69dicwA4.INSTANCE).setNegativeButton((int) R.string.no_update, (DialogInterface.OnClickListener) $$Lambda$NetOTAUpdate$6x2TJJkCVxACTwniKROYXYSvbU8.INSTANCE).setPositiveButton((int) R.string.yes_update, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener(progressBar) {
            private final /* synthetic */ ProgressBar f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(DialogInterface dialogInterface, int i) {
                NetOTAUpdate.lambda$showUpdateDialog$2(Context.this, this.f$1, dialogInterface, i);
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
        Settings.System.putString(context.getContentResolver(), "ota_update", getVersion(tempFile));
        dialog.dismiss();
        AlertDialog alertDialog1 = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(false).setTitle((int) R.string.copy_file).setView((View) progressBar).create();
        alertDialog1.getWindow().setType(2003);
        alertDialog1.show();
        new Thread() {
            public void run() {
                Looper.prepare();
                NetOTAUpdate.update(context, NetOTAUpdate.tempFile);
                if (NetOTAUpdate.test) {
                    NetOTAUpdate.mHandler.obtainMessage(0).sendToTarget();
                    boolean unused = NetOTAUpdate.isUpdating = false;
                }
                Looper.loop();
            }
        }.start();
    }
}
