package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.utils.CopyFile;
import java.io.File;

public class SplashFlasher {
    public static void check(final Context context, String path) {
        File vendorBootanimation;
        for (File subFile : new File(path).listFiles()) {
            if (subFile.getName().toLowerCase().equals("oem") && subFile.isDirectory()) {
                if (Integer.parseInt(Build.VERSION.RELEASE) == 11 && Build.DISPLAY.contains("M600")) {
                    File file = new File(subFile + "/imagefv.elf");
                    if (file.exists() && file.getName().equals("imagefv.elf")) {
                        Log.i("SplashFlasher", "Checking imagefv.elf try to flash");
                        flashLogo(file.getAbsolutePath());
                    }
                } else if (Integer.parseInt(Build.VERSION.RELEASE) <= 11 || !Build.DISPLAY.contains("M600")) {
                    File file2 = new File(subFile + "/splash.img");
                    if (file2.exists() && file2.getName().equals("splash.img")) {
                        Log.i("SplashFlasher", "Checking splash.img try to flash");
                        flashLogo(file2.getAbsolutePath());
                    }
                } else {
                    File file3 = new File(subFile + "/imagefv.bmp");
                    if (file3.exists() && file3.getName().equals("imagefv.bmp")) {
                        Log.i("SplashFlasher", "Checking imagefv.bmp try to flash");
                        flashLogo(file3.getAbsolutePath());
                    }
                }
            }
        }
        final Handler handler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                Toast.makeText(context, (CharSequence) context.getString(R.string.bootanimation_success), 1).show();
            }
        };
        File clearBootanimation = new File(path + "/OEM/bootanimation_clear.zip");
        if (!clearBootanimation.exists() || !clearBootanimation.getName().equals("bootanimation_clear.zip")) {
            final File bootAnimation = new File(path + "/OEM/bootanimation.zip");
            if (bootAnimation.exists() && bootAnimation.getName().equals("bootanimation.zip")) {
                Log.i("SplashFlasher", "Checking bootanimation.zip try to copy");
                long fileLength = bootAnimation.length();
                long availableBlocks = getAvailableBlocks("/mnt/vendor/persist");
                Log.d("FileUtils", "copyFile fileLength=" + fileLength + " availableBlocks=" + availableBlocks);
                if (availableBlocks - fileLength < 3145728) {
                    Log.e("SplashFlasher", "too large bootanimation");
                } else {
                    new Thread() {
                        public void run() {
                            Looper.prepare();
                            if (CopyFile.copyTo(bootAnimation, new File("/cache/bootanimation.zip"))) {
                                SystemProperties.set("persist.bootanimation.update", "1");
                                handler.sendEmptyMessage(0);
                                Toast.makeText(context, (CharSequence) context.getString(R.string.bootanimation_success), 1).show();
                            }
                            Looper.loop();
                        }
                    }.start();
                }
            }
        } else {
            Log.i("SplashFlasher", "Checking bootanimation_clear.zip try to clear");
            File cacheBootanimation = new File("/cache/bootanimation.zip");
            if (Integer.parseInt(Build.VERSION.RELEASE) <= 10 || !Build.DISPLAY.contains("M600")) {
                vendorBootanimation = new File("/mnt/vendor/persist/bootanimation.zip");
            } else {
                vendorBootanimation = new File("/mnt/vendor/persist/OEM/bootanimation.zip");
            }
            if (cacheBootanimation.exists()) {
                Log.d("SplashFlasher", "cache bootanimation delete = " + cacheBootanimation.delete());
            }
            if (vendorBootanimation.exists()) {
                Log.d("SplashFlasher", "vendor bootanimation delete =" + vendorBootanimation.delete());
            }
        }
    }

    public static long getAvailableBlocks(String path) {
        StatFs sf = new StatFs(path);
        long totalBytes = sf.getTotalBytes();
        long freeBytes = sf.getFreeBytes();
        Log.d("FileUtils", "getAvailableBlocks totalBytes=" + totalBytes + " freeBytes=" + freeBytes);
        return freeBytes;
    }

    public static void flashLogo(String absolutePath) {
        if (Integer.parseInt(Build.VERSION.RELEASE) <= 10 || !Build.DISPLAY.contains("M600")) {
            if (absolutePath.contains("imagefv")) {
                return;
            }
        } else if (absolutePath.contains("splash")) {
            return;
        }
        SystemProperties.set("persist.splash.path", absolutePath);
        SystemProperties.set("persist.splash.switch", "0");
        SystemProperties.set("persist.splash.switch", "1");
        Log.i("SplashFlasher", "flashing");
    }
}
