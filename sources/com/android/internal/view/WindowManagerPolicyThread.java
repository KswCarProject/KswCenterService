package com.android.internal.view;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Looper;

/* loaded from: classes4.dex */
public class WindowManagerPolicyThread {
    static Looper mLooper;
    static Thread mThread;

    public static void set(Thread thread, Looper looper) {
        mThread = thread;
        mLooper = looper;
    }

    public static Thread getThread() {
        return mThread;
    }

    @UnsupportedAppUsage
    public static Looper getLooper() {
        return mLooper;
    }
}
