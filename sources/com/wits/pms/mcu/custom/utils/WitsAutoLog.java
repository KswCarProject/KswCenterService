package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.util.Log;
import com.wits.pms.utils.SystemProperties;
import java.io.File;

public class WitsAutoLog {
    private static final String AutoFileName = "WitsAutoFlag";
    public static final String TAG = WitsAutoLog.class.getName();
    private static boolean isUpdating = false;

    private static int checkDirHasAutoFlag(File parent) {
        if (parent.listFiles() == null || parent.listFiles().length == 0) {
            return -1;
        }
        for (File subFile : parent.listFiles()) {
            Log.d(TAG, "checkDirHasAutoFlag   subFile = " + subFile.getName() + "  AutoFileName = " + AutoFileName);
            if (subFile.getName().contains(AutoFileName)) {
                Log.v(TAG, "has found witsAutoBackLog flag file: targetVersion   AutoFileName = WitsAutoFlag");
                return 1;
            }
        }
        return -1;
    }

    public static boolean checkFile(Context context, String path) {
        File parent = new File(path);
        String str = TAG;
        Log.i(str, "checkFile - " + path);
        if (isUpdating) {
            return false;
        }
        if (checkDirHasAutoFlag(parent) >= 1) {
            isUpdating = true;
            starAuto();
            return true;
        }
        Log.i(TAG, "auto catch log is running in background. ");
        return false;
    }

    public static void starAuto() {
        SystemProperties.set("vendor.disable.witsautolog", "1");
        Log.i(TAG, "starAuto");
    }
}
