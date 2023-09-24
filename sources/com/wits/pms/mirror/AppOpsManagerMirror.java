package com.wits.pms.mirror;

import android.app.AppOpsManager;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

/* loaded from: classes2.dex */
public class AppOpsManagerMirror {
    public static final String TAG = AppOpsManagerMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load(AppOpsManagerMirror.class, "android.app.AppOpsManager");
    @MethodName(name = "setMode", params = {int.class, int.class, String.class, int.class})
    public static RefMethod<Void> setMode;
    public AppOpsManager mAppOpsManager;

    public AppOpsManagerMirror(AppOpsManager base) {
        this.mAppOpsManager = base;
    }

    public void setMode(int code, int uid, String packageName, int mode) {
        setMode.call(this.mAppOpsManager, Integer.valueOf(code), Integer.valueOf(uid), packageName, Integer.valueOf(mode));
    }
}
