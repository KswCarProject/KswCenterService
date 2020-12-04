package com.wits.pms.mcu.custom.utils;

import android.util.Log;
import com.wits.pms.utils.SystemProperties;

public class SymlinkHelper {
    public static void setUp(String path) {
        if (!path.contains("/storage/emulated")) {
            SystemProperties.set("persist.current.sd.path", path);
            Log.i("SymlinkHelper", "Set current path :" + System.getProperty("persist.current.sd.path"));
            SystemProperties.set("persist.ln.sdcard", "1");
            Log.i("SymlinkHelper", "ln setup complete");
        }
    }

    public static void cleanUp() {
        SystemProperties.set("persist.current.sd.path", "");
        SystemProperties.set("persist.ln.sdcard", "0");
        Log.i("SymlinkHelper", "ln cleanup complete");
    }
}
