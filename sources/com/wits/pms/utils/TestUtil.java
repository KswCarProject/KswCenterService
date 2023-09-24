package com.wits.pms.utils;

import android.util.Log;

/* loaded from: classes2.dex */
public class TestUtil {
    private static final String TAG = "TESTLOG";

    public static void printStack(String content) {
        try {
            throw new Exception("");
        } catch (Exception e) {
            Log.m69e(TAG, content, e);
        }
    }
}
