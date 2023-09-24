package com.wits.pms.mcu.custom.utils;

import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes2.dex */
public class AccLight {
    private static final String ACC_CHANGE_PATH = "/sys/class/leds/led_p/brightness";
    public static long mDelay;
    public static boolean showing;
    private static int last = 0;
    private static Runnable accLightRunnable = new Runnable() { // from class: com.wits.pms.mcu.custom.utils.AccLight.1
        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    Thread.sleep(AccLight.mDelay);
                } catch (InterruptedException e) {
                }
                int unused = AccLight.last = AccLight.last == 255 ? 0 : 255;
                File awakeTimeFile = new File(AccLight.ACC_CHANGE_PATH);
                try {
                    FileWriter fr = new FileWriter(awakeTimeFile);
                    fr.write(AccLight.last + "");
                    fr.close();
                } catch (IOException e2) {
                    Log.m69e("AccLight", "write failed. cause:", e2);
                }
            }
        }
    };

    public static void show(long delay) {
        mDelay = delay;
        if (!showing) {
            showing = true;
            new Thread(accLightRunnable).start();
        }
    }
}
