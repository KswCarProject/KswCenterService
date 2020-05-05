package com.wits.pms.utils;

import android.content.res.Configuration;
import android.os.LocaleList;
import java.util.Locale;

public class LanguageUtil {
    public static void changeSystemLanguage(Locale locale) {
        if (locale != null) {
            try {
                Class classActivityManagerNative = Class.forName("android.app.ActivityManagerNative");
                Object objIActivityManager = classActivityManagerNative.getDeclaredMethod("getDefault", new Class[0]).invoke(classActivityManagerNative, new Object[0]);
                Class classIActivityManager = Class.forName("android.app.IActivityManager");
                Configuration config = (Configuration) classIActivityManager.getDeclaredMethod("getConfiguration", new Class[0]).invoke(objIActivityManager, new Object[0]);
                config.setLocales(new LocaleList(new Locale[]{locale}));
                classIActivityManager.getDeclaredMethod("updatePersistentConfiguration", new Class[]{Configuration.class}).invoke(objIActivityManager, new Object[]{config});
            } catch (Exception e) {
            }
        }
    }
}
