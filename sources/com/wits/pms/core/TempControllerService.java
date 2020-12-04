package com.wits.pms.core;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.os.SystemProperties;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TempControllerService extends IntentService {
    private static final String DEBUG_PATH = "/sys/class/leds/lcd-backlight/brightness";
    private static final String TAG = TempControllerService.class.getSimpleName();
    private static final String TEMP_PATH = "/sys/devices/virtual/thermal/thermal_zone4/temp";
    static boolean tmpHight = false;
    static boolean tmpLower = false;
    boolean debug;
    private int lastTemperature;
    AudioManager mAudioManager;
    int temperature;
    int volume = 16;

    public TempControllerService() {
        super("TempControllerService");
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
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
        Log.d(str, "getTemperature   tempStr = " + tempStr + "  temperature = " + this.temperature);
        return this.temperature;
    }

    private String onTempRead(String path) {
        File awakeTimeFile = new File(path);
        String result = new String();
        try {
            FileReader fr = new FileReader(awakeTimeFile);
            BufferedReader bufr = new BufferedReader(fr);
            while (true) {
                String readLine = bufr.readLine();
                String str = readLine;
                if (readLine != null) {
                    result = str;
                } else {
                    fr.close();
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleTemp(int temp) {
        try {
            String str = TAG;
            Log.d(str, "handleTemp  temp = " + temp);
            if (temp > 80) {
                getActivityManagerService().setProcessLimit(1);
            } else if (temp < 70) {
                getActivityManagerService().setProcessLimit(-1);
            }
        } catch (Exception e) {
            Log.e(TAG, "handleTemp   error ");
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public IActivityManager getActivityManagerService() {
        return ActivityManager.getService();
    }
}
