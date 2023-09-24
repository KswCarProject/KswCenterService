package com.wits.pms.interfaces;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.p007os.Debug;
import android.p007os.RemoteException;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TimeUtils;
import com.wits.pms.IContentObserver;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.mirror.MemoryInfoMirror;
import com.wits.pms.mirror.ServiceManager;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.utils.AmsUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class MemoryKiller {
    private static final int RECENT_TASK_LIMIT = 3;
    private static final String TAG = "MemoryKiller";
    private final boolean DEBUG;
    private final int DEFAULT_MEMORY_LIMIT;
    private ActivityManager mAm;
    protected List<AppProcess> mAppList;
    protected Context mContext;
    protected List<String> mImportantProcess;
    private PackageManager mPm;
    protected List<String> mWhiteList;
    private int memoryKillPercent;
    private List<String> topAppList;

    protected abstract void clearMemoryForCustom();

    public MemoryKiller(Context mContext) {
        this.DEBUG = false;
        this.mAppList = new ArrayList();
        this.mWhiteList = new ArrayList();
        this.DEFAULT_MEMORY_LIMIT = 20;
        this.memoryKillPercent = 20;
        this.topAppList = new ArrayList();
        this.mImportantProcess = new ArrayList<String>() { // from class: com.wits.pms.interfaces.MemoryKiller.2
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
        this.mContext = mContext;
        this.mAm = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        this.mPm = mContext.getPackageManager();
        batteryAndMemoryFix();
        PowerManagerApp.registerIContentObserver("topApp", new IContentObserver.Stub() { // from class: com.wits.pms.interfaces.MemoryKiller.1
            @Override // com.wits.pms.IContentObserver
            public void onChange() throws RemoteException {
                String pkgName = PowerManagerApp.getStatusString("topApp");
                try {
                    PackageInfo packageInfo = MemoryKiller.this.mPm.getPackageInfo(pkgName, 0);
                    boolean systemApp = packageInfo.applicationInfo.isSystemApp();
                    if (systemApp) {
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

    public MemoryKiller(Context mContext, int percent) {
        this(mContext);
        this.memoryKillPercent = percent;
    }

    private void batteryAndMemoryFix() {
        List<ApplicationInfo> apps = this.mContext.getPackageManager().getInstalledApplications(8192);
        for (ApplicationInfo app : apps) {
            if ((app.flags & 1) > 0 || app.packageName.contains("wits")) {
            }
        }
    }

    protected void kill(AppProcess appProcess) {
        for (String pkg : this.mImportantProcess) {
            if (appProcess.getPkgName().contains(pkg)) {
                return;
            }
        }
        for (String pkg2 : this.topAppList) {
            if (appProcess.getPkgName().contains(pkg2)) {
                return;
            }
        }
        IActivityManager activity = IActivityManager.Stub.asInterface(ServiceManager.getService(Context.ACTIVITY_SERVICE));
        int[] pids = new int[appProcess.pids.size()];
        for (int i = 0; i < pids.length; i++) {
            pids[i] = appProcess.pids.get(i).intValue();
        }
        AmsUtil.forceStopPackage(this.mContext, appProcess.getPkgName());
        try {
            activity.killPids(pids, "By MemoryKiller", false);
            Log.m68i(TAG, "killed appProcess:" + appProcess.toString());
        } catch (Exception e) {
        }
    }

    protected void setWhiteListPkg(List<String> whiteList) {
        this.mWhiteList = whiteList;
    }

    protected void setMediaAppPkg(String pkg) {
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
            Log.m72d(TAG, "current Memory percent:" + usageMem + "MB/" + totalMem + "MB - usage percent:" + percent);
            initAppProcessList();
            clearMemoryForCustom();
        }
    }

    @SuppressLint({"NewApi"})
    protected void initAppProcessList() {
        boolean systemApp;
        this.mAppList = new ArrayList();
        ActivityManager am = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : am.getRunningAppProcesses()) {
            int pid = runningAppProcessInfo.pid;
            String str = runningAppProcessInfo.processName;
            String pkgName = runningAppProcessInfo.pkgList[0];
            try {
                PackageInfo packageInfo = this.mPm.getPackageInfo(pkgName, 0);
                systemApp = packageInfo.applicationInfo.isSystemApp();
            } catch (Exception e) {
            }
            if (!systemApp) {
                int[] pids = {pid};
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(pids);
                int memorySize = new MemoryInfoMirror(memoryInfo[0]).getTotalUss();
                Log.m72d(TAG, "memorySize=" + memorySize);
                Iterator<AppProcess> it = this.mAppList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        AppProcess appProcess = new AppProcess(memorySize, pkgName);
                        if (!this.mImportantProcess.contains(pkgName)) {
                            this.mAppList.add(appProcess);
                        }
                    } else {
                        AppProcess appP = it.next();
                        if (appP.pkgName.equals(pkgName)) {
                            appP.pids.add(Integer.valueOf(pid));
                            appP.usageMemory += memorySize;
                            break;
                        }
                    }
                }
            }
        }
        this.mAppList.sort(new Comparator() { // from class: com.wits.pms.interfaces.-$$Lambda$MemoryKiller$PQLIQ9rfzc_JHIqsFJn7Tk9ubo8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MemoryKiller.lambda$initAppProcessList$0((MemoryKiller.AppProcess) obj, (MemoryKiller.AppProcess) obj2);
            }
        });
    }

    static /* synthetic */ int lambda$initAppProcessList$0(AppProcess o1, AppProcess o2) {
        return o2.usageMemory - o1.usageMemory;
    }

    /* loaded from: classes2.dex */
    protected class AppProcess {
        private boolean isUsingMediaApp;
        private boolean isWhiteListApp;
        public List<Integer> pids = new ArrayList();
        private String pkgName;
        private int usageMemory;

        public AppProcess(int usageMemory, String pkgName) {
            this.usageMemory = usageMemory;
            this.pkgName = pkgName;
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
