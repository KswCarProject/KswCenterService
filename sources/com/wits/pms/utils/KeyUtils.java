package com.wits.pms.utils;

import android.app.Instrumentation;

public class KeyUtils {
    static final boolean DBG = false;
    static final String TAG = "KeyUtils";

    public static void pressKey(final int keycode) {
        new Thread() {
            public void run() {
                new Instrumentation().sendKeyDownUpSync(keycode);
            }
        }.start();
    }
}
