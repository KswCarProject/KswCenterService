package com.wits.pms.utils;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.res.Configuration;
import android.p007os.Build;
import android.p007os.LocaleList;
import android.p007os.RemoteException;
import com.wits.pms.mirror.ActivityManagerNative;
import com.wits.pms.mirror.ConfigurationMirror;
import com.wits.pms.mirror.IActivityManagerMirror;
import java.util.Locale;

/* loaded from: classes2.dex */
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
        IActivityManager activityManager = ActivityManagerNative.getDefault();
        IActivityManagerMirror activityManagerMirror = new IActivityManagerMirror(activityManager);
        Configuration configuration = activityManagerMirror.getConfiguration();
        configuration.setLocale(locale);
        new ConfigurationMirror(configuration).setUserSetLocale(true);
        activityManagerMirror.updateConfiguration(configuration);
        BackupManager.dataChanged("com.android.providers.settings");
    }
}
