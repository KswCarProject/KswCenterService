package com.wits.pms.mcu.custom.utils;

import android.util.Log;
import com.wits.pms.mirror.SystemProperties;

/* loaded from: classes2.dex */
public class SymlinkHelper {
    public static void setUp(String path) {
        if (path.contains("/storage/emulated")) {
            return;
        }
        SystemProperties.set("persist.current.sd.path", path);
        Log.m68i("SymlinkHelper", "Set current path :" + System.getProperty("persist.current.sd.path"));
        SystemProperties.set("persist.ln.sdcard", "1");
        Log.m68i("SymlinkHelper", "ln setup complete");
    }

    public static void cleanUp() {
        SystemProperties.set("persist.current.sd.path", "");
        SystemProperties.set("persist.ln.sdcard", "0");
        Log.m68i("SymlinkHelper", "ln cleanup complete");
    }
}
