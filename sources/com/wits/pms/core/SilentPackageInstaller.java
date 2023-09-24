package com.wits.pms.core;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageInstaller;
import android.content.p002pm.PackageManager;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.TimedRemoteCaller;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class SilentPackageInstaller {
    private static final String INSTALL_COMPLETE = "persist.vendor.wits.installcomplete";
    private static final int MSG_UPDATE_INSTALL_PROGRESS = 0;
    private static final String TAG = "SilentPackageInstaller";
    public static final boolean isUsed;
    private Context mContext;
    private Handler mHandler;
    private int mInstalledNum;
    private PackageInstaller mPackageInstaller;
    private PackageManager mPackageManger;
    private ArrayList<String> mSilentCommonApk = new ArrayList<>(Arrays.asList(new String[0]));
    private ArrayList<String> mSilentForeignApk = new ArrayList<>(Arrays.asList(new String[0]));
    private ArrayList<String> mSilentChineseApk = new ArrayList<>(Arrays.asList(new String[0]));
    private ArrayList<String> mSilentAllApk = new ArrayList<>();
    private Map<String, String> mSilentAllApkPkg = new HashMap();
    private int mInstalledCount = 0;

    static {
        isUsed = Integer.parseInt(Build.VERSION.RELEASE) > 12 && Build.DISPLAY.contains("M600");
    }

    public SilentPackageInstaller(Context context) {
        int j = 0;
        this.mInstalledNum = 0;
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper()) { // from class: com.wits.pms.core.SilentPackageInstaller.1
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    SilentPackageInstaller.this.updateInstalledProgress();
                }
            }
        };
        this.mPackageManger = context.getPackageManager();
        this.mPackageInstaller = this.mPackageManger.getPackageInstaller();
        IntentFilter packageInstallerFilter = new IntentFilter();
        packageInstallerFilter.addAction(PackageInstallerReceiver.WITS_PACKAGE_ADDED_ACTION);
        PackageInstallerReceiver packageInstallerReceiver = new PackageInstallerReceiver();
        context.registerReceiver(packageInstallerReceiver, packageInstallerFilter);
        for (int i = 0; i < this.mSilentCommonApk.size(); i++) {
            this.mSilentAllApk.add(this.mSilentCommonApk.get(i));
        }
        if ("chinese".equals(SystemProperties.get("persist.install.type"))) {
            while (j < this.mSilentChineseApk.size()) {
                this.mSilentAllApk.add(this.mSilentChineseApk.get(j));
                j++;
            }
        } else {
            while (j < this.mSilentForeignApk.size()) {
                this.mSilentAllApk.add(this.mSilentForeignApk.get(j));
                j++;
            }
        }
        this.mInstalledNum = this.mSilentAllApk.size();
        if (SystemProperties.get(INSTALL_COMPLETE).equals("true")) {
            return;
        }
        long delayDefault = this.mInstalledNum == 0 ? TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS : 2000L;
        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.SilentPackageInstaller.2
            @Override // java.lang.Runnable
            public void run() {
                if (SilentPackageInstaller.this.mInstalledNum == 0) {
                    Log.m72d(SilentPackageInstaller.TAG, "finish,not need install");
                    SystemProperties.set(SilentPackageInstaller.INSTALL_COMPLETE, "true");
                    Settings.System.putInt(SilentPackageInstaller.this.mContext.getContentResolver(), "wits_firstTime_boot", 1);
                    return;
                }
                WitsCommand.sendCommand(1, 126, "0");
                SilentPackageInstaller.this.installPackage();
            }
        }, delayDefault);
    }

    /* loaded from: classes2.dex */
    public class PackageInstallerReceiver extends BroadcastReceiver {
        public static final String TAG = "PackageInstallerReceiver";
        public static final String WITS_PACKAGE_ADDED_ACTION = "com.wits.intent.PACKAGE_ADDED";
        public static final String WITS_PACKAGE_ADDED_EXTRAS = "APP_PACKAGE";

        public PackageInstallerReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (WITS_PACKAGE_ADDED_ACTION.equals(intent.getAction())) {
                String pkgName = intent.getExtras().getString(WITS_PACKAGE_ADDED_EXTRAS);
                Log.m72d(TAG, "pkg=" + intent.getExtras().getString(WITS_PACKAGE_ADDED_EXTRAS));
                if (SilentPackageInstaller.this.mSilentAllApkPkg.containsValue(pkgName)) {
                    SilentPackageInstaller.this.updateInstalledProgress();
                }
            }
        }
    }

    public void updateInstalledProgress() {
        this.mInstalledCount++;
        int progress = (this.mInstalledCount * 100) / this.mInstalledNum;
        Log.m72d(TAG, "installing app progress=  " + progress);
        if (progress == 100) {
            SystemProperties.set(INSTALL_COMPLETE, "true");
            Log.m72d(TAG, "finish install ");
            Settings.System.putInt(this.mContext.getContentResolver(), "wits_firstTime_boot", 1);
            WitsCommand.sendCommand(1, 127, "");
            return;
        }
        WitsCommand.sendCommand(1, 126, "" + progress);
    }

    public void installPackage() {
        new Thread(new Runnable() { // from class: com.wits.pms.core.SilentPackageInstaller.3
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < SilentPackageInstaller.this.mSilentAllApk.size(); i++) {
                    SilentPackageInstaller.this.installPackage((String) SilentPackageInstaller.this.mSilentAllApk.get(i));
                }
            }
        }).start();
    }

    public synchronized void installPackage(String apk) {
        String apkFilePath = "/system/PreInstall/" + apk;
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            Log.m70e(TAG, apkFilePath + " is not exists...");
            this.mSilentAllApkPkg.put(apk, null);
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        PackageInfo packageInfo = this.mPackageManger.getPackageArchiveInfo(apkFilePath, 5);
        if (packageInfo != null) {
            String packageName = packageInfo.packageName;
            this.mSilentAllApkPkg.put(apk, packageName);
            List<PackageInfo> packageInfoList = this.mPackageManger.getInstalledPackages(8192);
            for (PackageInfo info : packageInfoList) {
                if (info.packageName.equals(packageName)) {
                    this.mHandler.sendEmptyMessage(0);
                    Log.m72d(TAG, apkFilePath + " had installed");
                    return;
                }
            }
            String installSessionId = packageName + "_back.apk";
            try {
                installPackage(this.mContext, apkFile, packageName, installSessionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        this.mSilentAllApkPkg.put(apk, null);
        this.mHandler.sendEmptyMessage(0);
        Log.m72d(TAG, apkFilePath + " not find package");
    }

    public void installPackage(Context context, File apkFile, String packageName, String installSessionId) throws IOException {
        int sessionId;
        Intent intent;
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(1);
        params.setAppPackageName(packageName);
        params.setSize(apkFile.length());
        PackageInstaller.Session session = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            try {
                sessionId = this.mPackageInstaller.createSession(params);
                session = this.mPackageInstaller.openSession(sessionId);
            } catch (Exception e) {
                e = e;
            } catch (Throwable th) {
                e = th;
            }
            try {
                in = new FileInputStream(apkFile);
                out = session.openWrite(installSessionId, 0L, apkFile.length());
                byte[] buffer = new byte[1024];
                int count = 0;
                while (true) {
                    int length = in.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    out.write(buffer, 0, length);
                    count += length;
                }
                session.fsync(out);
                out.close();
                in.close();
                intent = new Intent(PackageInstallerReceiver.WITS_PACKAGE_ADDED_ACTION);
                intent.putExtra(PackageInstallerReceiver.WITS_PACKAGE_ADDED_EXTRAS, packageName);
            } catch (Exception e2) {
                e = e2;
            } catch (Throwable th2) {
                e = th2;
                if (session != null) {
                    session.close();
                }
                throw e;
            }
            try {
                session.commit(PendingIntent.getBroadcast(context, sessionId, intent, 201326592).getIntentSender());
                if (session == null) {
                    return;
                }
            } catch (Exception e3) {
                e = e3;
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                e.printStackTrace();
                if (session == null) {
                    return;
                }
                session.close();
            }
            session.close();
        } catch (Throwable th3) {
            e = th3;
        }
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("SilentPackageInstaller dump");
        pw.println("mSilentAllApkPkg is ");
        if (!this.mSilentAllApkPkg.isEmpty()) {
            Set<String> keySet = this.mSilentAllApkPkg.keySet();
            for (String key : keySet) {
                String value = this.mSilentAllApkPkg.get(key);
                pw.println("    " + key + " --> " + value);
            }
            pw.println("");
        }
    }
}
