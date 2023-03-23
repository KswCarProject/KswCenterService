package com.wits.pms.mirror;

import android.os.IBinder;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

public class ServiceManager {
    public static final String TAG = ServiceManager.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load((Class<?>) ServiceManager.class, "android.os.ServiceManager");
    @MethodName(name = "addService", params = {String.class, IBinder.class})
    public static RefMethod<Void> addService;
    @MethodName(name = "getService", params = {String.class})
    public static RefMethod<IBinder> getService;

    public static IBinder getService(String serviceName) {
        return getService.call((Object) null, serviceName);
    }

    public static void addService(String serviceName, IBinder binder) {
        addService.call((Object) null, serviceName, binder);
    }
}
