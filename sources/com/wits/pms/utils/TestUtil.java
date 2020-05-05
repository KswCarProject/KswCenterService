package com.wits.pms.utils;

import android.util.Log;
import com.wits.pms.BuildConfig;

public class TestUtil {
    private static final String TAG = "TESTLOG";

    public static void printStack(String content) {
        try {
            throw new Exception(BuildConfig.FLAVOR);
        } catch (Exception e) {
            Log.e(TAG, content, e);
        }
    }
}
