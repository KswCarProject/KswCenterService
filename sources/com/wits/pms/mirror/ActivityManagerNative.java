package com.wits.pms.mirror;

import android.app.IActivityManager;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

public class ActivityManagerNative {
    public static final String TAG = ActivityManagerNative.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load((Class<?>) ActivityManagerNative.class, "android.app.ActivityManagerNative");
    @MethodName(name = "getDefault", params = {})
    public static RefMethod<IActivityManager> getDefault;

    public static IActivityManager getDefault() {
        return getDefault.call((Object) null, new Object[0]);
    }
}
