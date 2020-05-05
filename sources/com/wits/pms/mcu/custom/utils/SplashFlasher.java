package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.utils.CopyFile;
import com.wits.pms.utils.SystemProperties;
import java.io.File;

public class SplashFlasher {
    public static void check(final Context context, String path) {
        for (File subFile : new File(path).listFiles()) {
            if (subFile.getName().toLowerCase().equals("oem") && subFile.isDirectory()) {
                File file = new File(subFile + "/splash.img");
                if (file.exists() && file.getName().equals("splash.img")) {
                    Log.i("SplashFlasher", "Checking splash.img try to flash");
                    flashLogo(file.getAbsolutePath());
                }
            }
        }
        final Handler handler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                Toast.makeText(context, context.getString(R.string.bootanimation_success), 1).show();
            }
        };
        final File bootAnimation = new File(path + "/OEM/bootanimation.zip");
        if (bootAnimation.exists() && bootAnimation.getName().equals("bootanimation.zip")) {
            Log.i("SplashFlasher", "Checking bootanimation.zip try to copy");
            new Thread() {
                public void run() {
                    Looper.prepare();
                    if (CopyFile.copyTo(bootAnimation, new File("/cache/bootanimation.zip"))) {
                        SystemProperties.set("persist.bootanimation.update", "1");
                        handler.sendEmptyMessage(0);
                        Toast.makeText(context, context.getString(R.string.bootanimation_success), 1).show();
                    }
                    Looper.loop();
                }
            }.start();
        }
    }

    public static void flashLogo(String absolutePath) {
        SystemProperties.set("persist.splash.path", absolutePath);
        SystemProperties.set("persist.splash.switch", "0");
        SystemProperties.set("persist.splash.switch", "1");
        Log.i("SplashFlasher", "flashing");
    }
}
