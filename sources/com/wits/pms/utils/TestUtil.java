package com.wits.pms.utils;

import android.util.Log;

public class TestUtil {
    private static final String TAG = "TESTLOG";

    public static void printStack(String content) {
        try {
            throw new Exception("");
        } catch (Exception e) {
            Log.e(TAG, content, e);
        }
    }
}
