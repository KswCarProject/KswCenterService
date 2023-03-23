package com.wits.pms.mirror;

import android.app.IActivityManager;
import android.content.res.Configuration;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

public class IActivityManagerMirror {
    public static final String TAG = IActivityManagerMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load((Class<?>) IActivityManagerMirror.class, "android.app.IActivityManager");
    @MethodName(name = "getConfiguration", params = {})
    public static RefMethod<Configuration> getConfiguration;
    @MethodName(name = "updateConfiguration", params = {Configuration.class})
    public static RefMethod<Boolean> updateConfiguration;
    public IActivityManager mActivityManager;

    public IActivityManagerMirror(IActivityManager base) {
        this.mActivityManager = base;
    }

    public Configuration getConfiguration() {
        return getConfiguration.call(this.mActivityManager, new Object[0]);
    }

    public boolean updateConfiguration(Configuration configuration) {
        return updateConfiguration.call(this.mActivityManager, configuration).booleanValue();
    }
}
