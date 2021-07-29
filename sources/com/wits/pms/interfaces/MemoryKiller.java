package com.wits.pms.interfaces;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.os.RemoteException;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TimeUtils;
import com.wits.pms.IContentObserver;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.utils.AmsUtil;
import com.wits.pms.utils.ServiceManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class MemoryKiller {
    private static final int RECENT_TASK_LIMIT = 3;
    private static final String TAG = "MemoryKiller";
    private final boolean DEBUG;
    private final int DEFAULT_MEMORY_LIMIT;
    private ActivityManager mAm;
    protected List<AppProcess> mAppList;
    protected Context mContext;
    protected List<String> mImportantProcess;
    /* access modifiers changed from: private */
    public PackageManager mPm;
    protected List<String> mWhiteList;
    private int memoryKillPercent;
    /* access modifiers changed from: private */
    public List<String> topAppList;

    /* access modifiers changed from: protected */
    public abstract void clearMemoryForCustom();

    public MemoryKiller(Context mContext2) {
        this.DEBUG = false;
        this.mAppList = new ArrayList();
        this.mWhiteList = new ArrayList();
        this.DEFAULT_MEMORY_LIMIT = 20;
        this.memoryKillPercent = 20;
        this.topAppList = new ArrayList();
        this.mImportantProcess = new ArrayList<String>() {
            {
                add("system");
                add("com.txznet.txz");
                add(".qtidataservices");
                add(".dataservices");
                add("com.android.sdvdplayer");
                add("wits");
                add("speed");
                add("player");
                add("com.google.process.gapps");
                add("com.qualcomm.qti.telephonyservice");
                add("com.qualcomm.telephony");
                add("com.qualcomm.qti.services.secureui");
                add("vendor.qti.hardware.cacert.server");
                add("com.qualcomm.qti.uceShimService");
                add("adups");
            }
        };
        this.mContext = mContext2;
        this.mAm = (ActivityManager) mContext2.getSystemService(Context.ACTIVITY_SERVICE);
        this.mPm = mContext2.getPackageManager();
        batteryAndMemoryFix();
        PowerManagerApp.registerIContentObserver("topApp", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                String pkgName = PowerManagerApp.getStatusString("topApp");
                try {
                    if (MemoryKiller.this.mPm.getPackageInfo(pkgName, 0).applicationInfo.isSystemApp()) {
                        return;
                    }
                } catch (Exception e) {
                }
                if (MemoryKiller.this.topAppList.size() < 3) {
                    MemoryKiller.this.topAppList.add(pkgName);
                    return;
                }
                MemoryKiller.this.topAppList.remove(2);
                MemoryKiller.this.topAppList.add(0, pkgName);
            }
        });
    }

    public MemoryKiller(Context mContext2, int percent) {
        this(mContext2);
        this.memoryKillPercent = percent;
    }

    private void batteryAndMemoryFix() {
        for (ApplicationInfo app : this.mContext.getPackageManager().getInstalledApplications(8192)) {
            if ((app.flags & 1) <= 0 && !app.packageName.contains("wits")) {
                AmsUtil.setForceAppStandby(this.mContext, app.packageName, 1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void kill(AppProcess appProcess) {
        for (String pkg : this.mImportantProcess) {
            try {
                if (appProcess.getPkgName().contains(pkg)) {
                    return;
                }
            } catch (Exception e) {
            }
        }
        for (String pkg2 : this.topAppList) {
            try {
                if (appProcess.getPkgName().contains(pkg2)) {
                    return;
                }
            } catch (Exception e2) {
            }
        }
        IActivityManager activity = IActivityManager.Stub.asInterface(ServiceManager.getService(Context.ACTIVITY_SERVICE));
        int[] pids = new int[appProcess.pids.size()];
        for (int i = 0; i < pids.length; i++) {
            pids[i] = appProcess.pids.get(i).intValue();
        }
        AmsUtil.forceStopPackage(this.mContext, appProcess.getPkgName());
        try {
            boolean killPids = activity.killPids(pids, "By MemoryKiller", false);
            Log.i(TAG, "killed appProcess:" + appProcess.toString());
        } catch (Exception e3) {
        }
    }

    /* access modifiers changed from: protected */
    public void setWhiteListPkg(List<String> whiteList) {
        this.mWhiteList = whiteList;
    }

    /* access modifiers changed from: protected */
    public void setMediaAppPkg(String pkg) {
        for (AppProcess process : this.mAppList) {
            process.setUsingMediaApp(process.pkgName.equals(pkg));
        }
    }

    public void clearMemoryWits() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        this.mAm.getMemoryInfo(memoryInfo);
        long usageMem = memoryInfo.availMem / TimeUtils.NANOS_PER_MS;
        long totalMem = memoryInfo.totalMem / TimeUtils.NANOS_PER_MS;
        int percent = (int) (((((float) usageMem) * 100.0f) / ((float) totalMem)) * 1.0f);
        if (percent < this.memoryKillPercent) {
            Log.d(TAG, "current Memory percent:" + usageMem + "MB/" + totalMem + "MB - usage percent:" + percent);
            initAppProcessList();
            clearMemoryForCustom();
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void initAppProcessList() {
        this.mAppList = new ArrayList();
        ActivityManager am = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : am.getRunningAppProcesses()) {
            int pid = runningAppProcessInfo.pid;
            String str = runningAppProcessInfo.processName;
            String pkgName = runningAppProcessInfo.pkgList[0];
            try {
                if (this.mPm.getPackageInfo(pkgName, 0).applicationInfo.isSystemApp()) {
                }
            } catch (Exception e) {
            }
            int memorySize = 0;
            try {
                memorySize = ((Integer) Debug.MemoryInfo.class.getMethod("getTotalUss", new Class[0]).invoke(am.getProcessMemoryInfo(new int[]{pid})[0], new Object[0])).intValue();
            } catch (Exception e2) {
            }
            Iterator<AppProcess> it = this.mAppList.iterator();
            while (true) {
                if (it.hasNext()) {
                    AppProcess appP = it.next();
                    if (appP.pkgName.equals(pkgName)) {
                        appP.pids.add(Integer.valueOf(pid));
                        int unused = appP.usageMemory = appP.usageMemory + memorySize;
                        break;
                    }
                } else {
                    AppProcess appProcess = new AppProcess(memorySize, pkgName);
                    if (!this.mImportantProcess.contains(pkgName)) {
                        this.mAppList.add(appProcess);
                    }
                }
            }
        }
        this.mAppList.sort($$Lambda$MemoryKiller$PQLIQ9rfzc_JHIqsFJn7Tk9ubo8.INSTANCE);
    }

    static /* synthetic */ int lambda$initAppProcessList$0(AppProcess o1, AppProcess o2) {
        return o2.usageMemory - o1.usageMemory;
    }

    protected class AppProcess {
        private boolean isUsingMediaApp;
        private boolean isWhiteListApp;
        public List<Integer> pids = new ArrayList();
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
            if (this.pkgName.contains("wits") || this.pkgName.contains("android.sdvd")) {
                return true;
            }
            for (String pkg : MemoryKiller.this.mWhiteList) {
                if (this.pkgName.contains(pkg)) {
                    return true;
                }
            }
            return false;
        }

        public int getUsageMemory() {
            return this.usageMemory;
        }

        public String getPkgName() {
            return this.pkgName;
        }

        public String toString() {
            return "AppProcess{usageMemory=" + this.usageMemory + ", pkgName='" + this.pkgName + DateFormat.QUOTE + ", isUsingMediaApp=" + this.isUsingMediaApp + ", isWhiteListApp=" + this.isWhiteListApp + '}';
        }
    }
}
