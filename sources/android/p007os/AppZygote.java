package android.p007os;

import android.content.p002pm.ApplicationInfo;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;

/* renamed from: android.os.AppZygote */
/* loaded from: classes3.dex */
public class AppZygote {
    private static final String LOG_TAG = "AppZygote";
    private final ApplicationInfo mAppInfo;
    private final Object mLock = new Object();
    @GuardedBy({"mLock"})
    private ChildZygoteProcess mZygote;
    private final int mZygoteUid;
    private final int mZygoteUidGidMax;
    private final int mZygoteUidGidMin;

    public AppZygote(ApplicationInfo appInfo, int zygoteUid, int uidGidMin, int uidGidMax) {
        this.mAppInfo = appInfo;
        this.mZygoteUid = zygoteUid;
        this.mZygoteUidGidMin = uidGidMin;
        this.mZygoteUidGidMax = uidGidMax;
    }

    public ChildZygoteProcess getProcess() {
        synchronized (this.mLock) {
            if (this.mZygote != null) {
                return this.mZygote;
            }
            connectToZygoteIfNeededLocked();
            return this.mZygote;
        }
    }

    public void stopZygote() {
        synchronized (this.mLock) {
            stopZygoteLocked();
        }
    }

    public ApplicationInfo getAppInfo() {
        return this.mAppInfo;
    }

    @GuardedBy({"mLock"})
    private void stopZygoteLocked() {
        if (this.mZygote != null) {
            this.mZygote.close();
            Process.killProcess(this.mZygote.getPid());
            this.mZygote = null;
        }
    }

    @GuardedBy({"mLock"})
    private void connectToZygoteIfNeededLocked() {
        String abi = this.mAppInfo.primaryCpuAbi != null ? this.mAppInfo.primaryCpuAbi : Build.SUPPORTED_ABIS[0];
        try {
            ZygoteProcess zygoteProcess = Process.ZYGOTE_PROCESS;
            this.mZygote = zygoteProcess.startChildZygote("com.android.internal.os.AppZygoteInit", this.mAppInfo.processName + "_zygote", this.mZygoteUid, this.mZygoteUid, null, 0, "app_zygote", abi, abi, null, this.mZygoteUidGidMin, this.mZygoteUidGidMax);
            ZygoteProcess.waitForConnectionToZygote(this.mZygote.getPrimarySocketAddress());
            Log.m68i(LOG_TAG, "Starting application preload.");
            this.mZygote.preloadApp(this.mAppInfo, abi);
            Log.m68i(LOG_TAG, "Application preload done.");
        } catch (Exception e) {
            Log.m69e(LOG_TAG, "Error connecting to app zygote", e);
            stopZygoteLocked();
        }
    }
}