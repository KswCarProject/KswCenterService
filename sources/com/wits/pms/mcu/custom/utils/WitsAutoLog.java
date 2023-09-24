package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.util.Log;
import com.wits.pms.mirror.SystemProperties;
import java.io.File;

/* loaded from: classes2.dex */
public class WitsAutoLog {
    private static final String AutoFileName = "WitsAutoFlag";
    public static final String TAG = WitsAutoLog.class.getName();
    private static boolean isUpdating = false;

    private static int checkDirHasAutoFlag(File parent) {
        File[] listFiles;
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        for (File subFile : parent.listFiles()) {
            Log.m72d(TAG, "checkDirHasAutoFlag   subFile = " + subFile.getName() + "  AutoFileName = " + AutoFileName);
            if (subFile.getName().contains(AutoFileName)) {
                Log.m66v(TAG, "has found witsAutoBackLog flag file: targetVersion   AutoFileName = WitsAutoFlag");
                return 1;
            }
        }
        return -1;
    }

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.m68i(str, "checkFile - " + path);
        if (isUpdating) {
            return false;
        }
        int status = checkDirHasAutoFlag(parent);
        if (status >= 1) {
            isUpdating = true;
            starAuto();
            return true;
        }
        Log.m68i(TAG, "auto catch log is running in background. ");
        return false;
    }

    public static void starAuto() {
        SystemProperties.set("vendor.disable.witsautolog", "1");
        Log.m68i(TAG, "starAuto");
    }
}
