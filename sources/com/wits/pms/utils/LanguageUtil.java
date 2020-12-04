package com.wits.pms.utils;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.os.RemoteException;
import java.util.Locale;

public class LanguageUtil {
    public static void changeSystemLanguage(Locale locale) {
        try {
            IActivityManager am = ActivityManager.getService();
            Configuration config = am.getConfiguration();
            if (Build.DISPLAY.contains("8937")) {
                locale = new Locale("zh", "CN");
            }
            config.setLocales(new LocaleList(locale));
            config.userSetLocale = true;
            am.updatePersistentConfiguration(config);
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (RemoteException e) {
        }
    }

    public static void changeSystemLanguageComb(Locale locale) {
        try {
            Class iActivityManager = Class.forName("android.app.IActivityManager");
            Class activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Object objIActMag = activityManagerNative.getDeclaredMethod("getDefault", new Class[0]).invoke(activityManagerNative, new Object[0]);
            Configuration config = (Configuration) iActivityManager.getDeclaredMethod("getConfiguration", new Class[0]).invoke(objIActMag, new Object[0]);
            config.locale = locale;
            Class.forName("android.content.res.Configuration").getField("userSetLocale").set(config, true);
            iActivityManager.getDeclaredMethod("updateConfiguration", new Class[]{Configuration.class}).invoke(objIActMag, new Object[]{config});
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
