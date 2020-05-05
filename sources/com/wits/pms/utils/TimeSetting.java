package com.wits.pms.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.util.Calendar;

public class TimeSetting {
    public static void setTimeTo12(Context context) {
        Settings.System.putString(context.getContentResolver(), "time_12_24", "12");
    }

    public static void setTimeTo24(Context context) {
        Settings.System.putString(context.getContentResolver(), "time_12_24", "24");
    }

    public static void setTime(Context context, Calendar calendar) {
        long when = calendar.getTimeInMillis();
        if (when / 1000 < 2147483647L) {
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setTime(when);
        }
    }

    public static void setTime(Context context, int year, int month, int day, int hour, int min, int sec) {
        Log.i("TimeSettings", (year + 2000) + "/" + month + "/" + day + " " + hour + ":" + min + ":" + sec);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year + 2000, month + -1, day, hour, min, sec);
        setTime(context, calendar);
    }
}
