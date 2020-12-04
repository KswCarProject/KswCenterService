package com.wits.pms.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.net.wifi.WifiEnterpriseConfig;
import android.provider.Settings;
import android.provider.SettingsStringUtil;
import android.util.Log;
import java.util.Calendar;

public class TimeSetting {
    public static void setTimeTo12(Context context) {
        Settings.System.putString(context.getContentResolver(), Settings.System.TIME_12_24, "12");
    }

    public static void setTimeTo24(Context context) {
        Settings.System.putString(context.getContentResolver(), Settings.System.TIME_12_24, "24");
    }

    public static void setTime(Context context, Calendar calendar) {
        long when = calendar.getTimeInMillis();
        if (when / 1000 < 2147483647L) {
            ((AlarmManager) context.getSystemService("alarm")).setTime(when);
        }
    }

    public static void setTime(Context context, int year, int month, int day, int hour, int min, int sec) {
        Log.i("TimeSettings", (year + 2000) + "/" + month + "/" + day + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + hour + SettingsStringUtil.DELIMITER + min + SettingsStringUtil.DELIMITER + sec);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year + 2000, month + -1, day, hour, min, sec);
        setTime(context, calendar);
    }
}
