package com.wits.pms.interfaces;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.util.Log;
import com.wits.pms.utils.AmsUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class MemoryKiller {
    private static final String TAG = "MemoryKiller";
    protected List<AppProcess> mAppList;
    protected Context mContext;
    protected List<String> mImportantProcess = new ArrayList<String>() {
        {
            add("system");
            add("com.txznet.txz");
            add(".qtidataservices");
            add(".dataservices");
        }
    };
    protected List<ApplicationInfo> mSystemApps;

    /* access modifiers changed from: protected */
    public abstract void clearMemoryForCustom();

    public MemoryKiller(Context mContext2) {
        this.mContext = mContext2;
        batteryAndMemoryFix();
    }

    private void batteryAndMemoryFix() {
        for (ApplicationInfo app : this.mContext.getPackageManager().getInstalledApplications(8192)) {
            if ((app.flags & 1) <= 0 && !app.packageName.contains("wits")) {
                AmsUtil.setForceAppStandby(this.mContext, app.packageName, 1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void kill(String pkgName) {
        AmsUtil.forceStopPackage(this.mContext, pkgName);
    }

    /* access modifiers changed from: protected */
    public void setWhiteListPkg(List<String> whiteList) {
        for (AppProcess process : this.mAppList) {
            process.setWhiteListApp(whiteList.contains(process.pkgName));
        }
    }

    /* access modifiers changed from: protected */
    public void setMediaAppPkg(String pkg) {
        for (AppProcess process : this.mAppList) {
            process.setUsingMediaApp(process.pkgName.equals(pkg));
        }
    }

    public void clearMemoryWits() {
        initAppProcessList();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void initAppProcessList() {
        this.mAppList = new ArrayList();
        ActivityManager am = (ActivityManager) this.mContext.getSystemService("activity");
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : am.getRunningAppProcesses()) {
            int pid = runningAppProcessInfo.pid;
            String processName = runningAppProcessInfo.processName;
            int memorySize = 0;
            try {
                memorySize = ((Integer) Debug.MemoryInfo.class.getMethod("getTotalUss", new Class[0]).invoke(am.getProcessMemoryInfo(new int[]{pid})[0], new Object[0])).intValue();
            } catch (Exception e) {
            }
            String pkgName = processName.split(":")[0];
            if (!this.mImportantProcess.contains(pkgName) && !pkgName.contains("android")) {
                AppProcess appProcess = null;
                Iterator<AppProcess> it = this.mAppList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        AppProcess appP = it.next();
                        if (appP.pkgName.equals(pkgName)) {
                            int unused = appP.usageMemory = appP.usageMemory + memorySize;
                            break;
                        }
                    } else {
                        if (0 == 0) {
                            appProcess = new AppProcess(memorySize, pkgName);
                        }
                        this.mAppList.add(appProcess);
                    }
                }
            }
        }
        this.mAppList.sort(MemoryKiller$$Lambda$0.$instance);
        for (AppProcess appP2 : this.mAppList) {
            Log.i(TAG, "pkgName:" + appP2.pkgName + " - usageMemory:" + appP2.usageMemory);
        }
    }

    static final /* synthetic */ int lambda$initAppProcessList$0$MemoryKiller(AppProcess o1, AppProcess o2) {
        return o2.usageMemory - o1.usageMemory;
    }

    protected class AppProcess {
        private boolean isUsingMediaApp;
        private boolean isWhiteListApp;
        /* access modifiers changed from: private */
        public String pkgName;
        /* access modifiers changed from: private */
        public int usageMemory;

        public AppProcess(int usageMemory2, String pkgName2) {
            this.usageMemory = usageMemory2;
            this.pkgName = pkgName2;
        }

        public boolean isUsingMediaApp() {
            return this.isUsingMediaApp;
        }

        public void setUsingMediaApp(boolean usingMediaApp) {
            this.isUsingMediaApp = usingMediaApp;
        }

        public boolean isWhiteListApp() {
            return this.isWhiteListApp;
        }

        public void setWhiteListApp(boolean whiteListApp) {
            this.isWhiteListApp = whiteListApp;
        }

        public int getUsageMemory() {
            return this.usageMemory;
        }

        public String getPkgName() {
            return this.pkgName;
        }
    }
}
