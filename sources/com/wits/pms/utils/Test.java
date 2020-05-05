package com.wits.pms.utils;

import android.content.Context;

public class Test {
    public static void testAction(Context context) {
        SysConfigUtil.writeArg("7", "/sys/kernel/mhl_camera_config/mhl_camera_config");
    }

    public static void test() {
    }
}
