package com.wits.pms.utils;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.os.RemoteException;
import com.wits.pms.mirror.ActivityManagerNative;
import com.wits.pms.mirror.ConfigurationMirror;
import com.wits.pms.mirror.IActivityManagerMirror;
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
        IActivityManagerMirror activityManagerMirror = new IActivityManagerMirror(ActivityManagerNative.getDefault());
        Configuration configuration = activityManagerMirror.getConfiguration();
        configuration.setLocale(locale);
        new ConfigurationMirror(configuration).setUserSetLocale(true);
        activityManagerMirror.updateConfiguration(configuration);
        BackupManager.dataChanged("com.android.providers.settings");
    }
}
