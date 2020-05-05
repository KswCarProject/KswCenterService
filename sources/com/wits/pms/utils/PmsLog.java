package com.wits.pms.utils;

import android.util.Log;

public class PmsLog {
    private static final boolean DEBUG = true;

    public static void i(String Tag, String msg) {
        Log.i(Tag, msg);
    }

    public static void e(String Tag, String msg) {
        Log.e(Tag, msg);
    }

    public static void e(String Tag, String msg, Exception e) {
        Log.e(Tag, msg, e);
    }
}
