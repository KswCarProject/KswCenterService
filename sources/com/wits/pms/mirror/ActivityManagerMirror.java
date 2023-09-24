package com.wits.pms.mirror;

import android.app.ActivityManager;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

/* loaded from: classes2.dex */
public class ActivityManagerMirror {
    public static final String TAG = ActivityManagerMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load(ActivityManagerMirror.class, "android.app.ActivityManager");
    @MethodName(name = "forceStopPackage", params = {String.class})
    public static RefMethod<Void> forceStopPackage;
    public ActivityManager mActivityManager;

    public ActivityManagerMirror(ActivityManager base) {
        this.mActivityManager = base;
    }

    public void forceStopPackage(String pkgName) {
        forceStopPackage.call(this.mActivityManager, pkgName);
    }
}
