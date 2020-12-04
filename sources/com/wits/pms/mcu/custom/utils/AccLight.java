package com.wits.pms.mcu.custom.utils;

import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AccLight {
    private static final String ACC_CHANGE_PATH = "/sys/class/leds/led_p/brightness";
    private static Runnable accLightRunnable = new Runnable() {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(AccLight.mDelay);
                } catch (InterruptedException e) {
                }
                int i = 255;
                if (AccLight.last == 255) {
                    i = 0;
                }
                int unused = AccLight.last = i;
                try {
                    FileWriter fr = new FileWriter(new File(AccLight.ACC_CHANGE_PATH));
                    fr.write(AccLight.last + "");
                    fr.close();
                } catch (IOException e2) {
                    Log.e("AccLight", "write failed. cause:", e2);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public static int last = 0;
    public static long mDelay;
    public static boolean showing;

    public static void show(long delay) {
        mDelay = delay;
        if (!showing) {
            showing = true;
            new Thread(accLightRunnable).start();
        }
    }
}
