package com.wits.pms.core;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.p007os.SystemProperties;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes2.dex */
public class TempControllerService extends IntentService {
    private static final String DEBUG_PATH = "/sys/class/leds/lcd-backlight/brightness";
    private static final String TEMP_PATH = "/sys/devices/virtual/thermal/thermal_zone4/temp";
    boolean debug;
    private int lastTemperature;
    AudioManager mAudioManager;
    int temperature;
    int volume;
    private static final String TAG = TempControllerService.class.getSimpleName();
    static boolean tmpHight = false;
    static boolean tmpLower = false;

    public TempControllerService() {
        super("TempControllerService");
        this.volume = 16;
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            handleTemp(getTemperature());
        }
    }

    private int getTemperature() {
        String tempStr;
        if (SystemProperties.get("vendor.wits.temp_debug", "0").equals("1")) {
            this.debug = true;
        } else {
            this.debug = false;
        }
        if (this.debug) {
            tempStr = onTempRead(DEBUG_PATH);
        } else {
            tempStr = onTempRead(TEMP_PATH);
        }
        if (this.debug) {
            this.temperature = Integer.parseInt(tempStr);
        } else {
            this.temperature = Integer.parseInt(tempStr);
        }
        String str = TAG;
        Log.m72d(str, "getTemperature   tempStr = " + tempStr + "  temperature = " + this.temperature);
        return this.temperature;
    }

    private String onTempRead(String path) {
        File awakeTimeFile = new File(path);
        FileReader fr = null;
        BufferedReader bufr = null;
        String result = new String();
        try {
            fr = new FileReader(awakeTimeFile);
            bufr = new BufferedReader(fr);
            while (true) {
                String str = bufr.readLine();
                if (str != null) {
                    result = str;
                } else {
                    bufr.close();
                    fr.close();
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (bufr != null) {
                try {
                    bufr.close();
                } catch (Exception e2) {
                    Log.m72d(TAG, "close bufr failed");
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                    return null;
                } catch (Exception e3) {
                    Log.m72d(TAG, "close fr failed");
                    return null;
                }
            }
            return null;
        }
    }

    private void handleTemp(int temp) {
        try {
            String str = TAG;
            Log.m72d(str, "handleTemp  temp = " + temp);
            if (temp > 80) {
                getActivityManagerService().setProcessLimit(1);
            } else if (temp < 70) {
                getActivityManagerService().setProcessLimit(-1);
            }
        } catch (Exception e) {
            Log.m70e(TAG, "handleTemp   error ");
            e.printStackTrace();
        }
    }

    IActivityManager getActivityManagerService() {
        return ActivityManager.getService();
    }
}
