package com.wits.pms.mirror;

import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

/* loaded from: classes2.dex */
public class SystemProperties {
    public static final String TAG = SystemProperties.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load(SystemProperties.class, "android.os.SystemProperties");
    @MethodName(name = "get", params = {String.class})
    public static RefMethod<String> get;
    @MethodName(name = "set", params = {String.class, String.class})
    public static RefMethod<Void> set;

    public static String get(String key) {
        return get.call(null, key);
    }

    public static void set(String key, String value) {
        set.call(null, key, value);
    }
}
