package com.wits.pms.utils;

import android.app.Instrumentation;

/* loaded from: classes2.dex */
public class KeyUtils {
    static final boolean DBG = false;
    static final String TAG = "KeyUtils";

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.utils.KeyUtils$1] */
    public static void pressKey(final int keycode) {
        new Thread() { // from class: com.wits.pms.utils.KeyUtils.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                new Instrumentation().sendKeyDownUpSync(keycode);
            }
        }.start();
    }
}
