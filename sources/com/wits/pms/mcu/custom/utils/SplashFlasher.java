package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.StatFs;
import android.util.Log;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.utils.CopyFile;
import java.io.File;

/* loaded from: classes2.dex */
public class SplashFlasher {
    /* JADX WARN: Type inference failed for: r7v3, types: [com.wits.pms.mcu.custom.utils.SplashFlasher$2] */
    public static void check(final Context context, String path) {
        File[] listFiles;
        File vendorBootanimation;
        for (File subFile : new File(path).listFiles()) {
            if (subFile.getName().toLowerCase().equals("oem") && subFile.isDirectory()) {
                if (Integer.parseInt(Build.VERSION.RELEASE) == 11 && Build.DISPLAY.contains("M600")) {
                    File file = new File(subFile + "/imagefv.elf");
                    if (file.exists() && file.getName().equals("imagefv.elf")) {
                        Log.m68i("SplashFlasher", "Checking imagefv.elf try to flash");
                        flashLogo(file.getAbsolutePath());
                    }
                } else if (Integer.parseInt(Build.VERSION.RELEASE) > 11 && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
                    File file2 = new File(subFile + "/imagefv.bmp");
                    if (file2.exists() && file2.getName().equals("imagefv.bmp")) {
                        Log.m68i("SplashFlasher", "Checking imagefv.bmp try to flash");
                        flashLogo(file2.getAbsolutePath());
                    }
                } else {
                    File file3 = new File(subFile + "/splash.img");
                    if (file3.exists() && file3.getName().equals("splash.img")) {
                        Log.m68i("SplashFlasher", "Checking splash.img try to flash");
                        flashLogo(file3.getAbsolutePath());
                    }
                }
            }
        }
        final Handler handler = new Handler(context.getMainLooper()) { // from class: com.wits.pms.mcu.custom.utils.SplashFlasher.1
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                Toast.makeText(context, context.getString(C3580R.string.bootanimation_success), 1).show();
            }
        };
        File clearBootanimation = new File(path + "/OEM/bootanimation_clear.zip");
        if (clearBootanimation.exists() && clearBootanimation.getName().equals("bootanimation_clear.zip")) {
            Log.m68i("SplashFlasher", "Checking bootanimation_clear.zip try to clear");
            File cacheBootanimation = new File("/cache/bootanimation.zip");
            if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
                vendorBootanimation = new File("/mnt/vendor/persist/OEM/bootanimation.zip");
            } else {
                vendorBootanimation = new File("/mnt/vendor/persist/bootanimation.zip");
            }
            if (cacheBootanimation.exists()) {
                boolean cacheDelete = cacheBootanimation.delete();
                Log.m72d("SplashFlasher", "cache bootanimation delete = " + cacheDelete);
            }
            boolean cacheDelete2 = vendorBootanimation.exists();
            if (cacheDelete2) {
                boolean vendorDelete = vendorBootanimation.delete();
                Log.m72d("SplashFlasher", "vendor bootanimation delete =" + vendorDelete);
                return;
            }
            return;
        }
        final File bootAnimation = new File(path + "/OEM/bootanimation.zip");
        if (bootAnimation.exists() && bootAnimation.getName().equals("bootanimation.zip")) {
            Log.m68i("SplashFlasher", "Checking bootanimation.zip try to copy");
            long fileLength = bootAnimation.length();
            long availableBlocks = getAvailableBlocks("/mnt/vendor/persist");
            Log.m72d("FileUtils", "copyFile fileLength=" + fileLength + " availableBlocks=" + availableBlocks);
            if (availableBlocks - fileLength < 3145728) {
                Log.m70e("SplashFlasher", "too large bootanimation");
            } else {
                new Thread() { // from class: com.wits.pms.mcu.custom.utils.SplashFlasher.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        Looper.prepare();
                        boolean success = CopyFile.copyTo(bootAnimation, new File("/cache/bootanimation.zip"));
                        if (success) {
                            SystemProperties.set("persist.bootanimation.update", "1");
                            handler.sendEmptyMessage(0);
                            Toast.makeText(context, context.getString(C3580R.string.bootanimation_success), 1).show();
                        }
                        Looper.loop();
                    }
                }.start();
            }
        }
    }

    public static long getAvailableBlocks(String path) {
        StatFs sf = new StatFs(path);
        long totalBytes = sf.getTotalBytes();
        long freeBytes = sf.getFreeBytes();
        Log.m72d("FileUtils", "getAvailableBlocks totalBytes=" + totalBytes + " freeBytes=" + freeBytes);
        return freeBytes;
    }

    public static void flashLogo(String absolutePath) {
        if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
            if (absolutePath.contains("splash")) {
                return;
            }
        } else if (absolutePath.contains("imagefv")) {
            return;
        }
        SystemProperties.set("persist.splash.path", absolutePath);
        SystemProperties.set("persist.splash.switch", "0");
        SystemProperties.set("persist.splash.switch", "1");
        Log.m68i("SplashFlasher", "flashing");
    }
}
