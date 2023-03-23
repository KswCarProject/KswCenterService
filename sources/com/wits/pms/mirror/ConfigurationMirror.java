package com.wits.pms.mirror;

import android.content.res.Configuration;
import com.wits.reflect.RefBoolean;
import com.wits.reflect.RefClass;

public class ConfigurationMirror {
    public static final String TAG = ConfigurationMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load((Class<?>) ConfigurationMirror.class, "android.content.res.Configuration");
    public static RefBoolean userSetLocale;
    public Configuration mConfiguration;

    public ConfigurationMirror(Configuration base) {
        this.mConfiguration = base;
    }

    public void setUserSetLocale(boolean value) {
        userSetLocale.set(this.mConfiguration, Boolean.valueOf(value));
    }
}
