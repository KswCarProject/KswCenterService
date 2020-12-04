package com.wits.pms.utils;

import android.os.Handler;
import android.util.Log;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.utils.SysConfigUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SysConfigUtil {
    public static final String AccConfig = "/sys/devices/soc/soc:gpio_keys/acc_int";
    public static final String BrightnessConfig = "/sys/devices/soc/1a00000.qcom,mdss_mdp/1a00000.qcom,mdss_mdp:qcom,mdss_fb_primary/leds/lcd-backlight/brightness";
    public static final String CddConfig = "/sys/devices/soc/soc:gpio_keys/ccd_int";
    public static final String EpbConfig = "/sys/devices/soc/soc:gpio_keys/break_det";
    public static final String IllConfig = "/sys/devices/soc/soc:gpio_keys/ill_int";
    public static final String RLightConfig = "/sys/devices/soc/soc:gpio_keys/rlight_int";
    private static Thread sysIoThread;

    public interface WriteListener {
        void failed();

        void start();

        void success();
    }

    private static class WriteRunnable implements Runnable {
        private WriteListener listener;
        private final Handler mHandler = new Handler(PowerManagerAppService.serviceContext.getMainLooper());
        private String path;
        private String value;

        public WriteRunnable(String value2, String path2, WriteListener listener2) {
            this.value = value2;
            this.path = path2;
            this.listener = listener2;
        }

        public void run() {
            this.mHandler.post(new Runnable() {
                public final void run() {
                    SysConfigUtil.WriteRunnable.lambda$run$0(SysConfigUtil.WriteRunnable.this);
                }
            });
            try {
                FileWriter fr = new FileWriter(new File(this.path));
                if (this.value != null) {
                    fr.write(this.value + "\n");
                }
                fr.flush();
                fr.close();
                this.mHandler.post(new Runnable() {
                    public final void run() {
                        SysConfigUtil.WriteRunnable.lambda$run$1(SysConfigUtil.WriteRunnable.this);
                    }
                });
            } catch (IOException e) {
                Log.e(SysConfigUtil.class.getName(), "save failed. cause:", e);
                this.mHandler.post(new Runnable() {
                    public final void run() {
                        SysConfigUtil.WriteRunnable.lambda$run$2(SysConfigUtil.WriteRunnable.this);
                    }
                });
            }
        }

        public static /* synthetic */ void lambda$run$0(WriteRunnable writeRunnable) {
            if (writeRunnable.listener != null) {
                writeRunnable.listener.start();
            }
        }

        public static /* synthetic */ void lambda$run$1(WriteRunnable writeRunnable) {
            if (writeRunnable.listener != null) {
                writeRunnable.listener.success();
            }
        }

        public static /* synthetic */ void lambda$run$2(WriteRunnable writeRunnable) {
            if (writeRunnable.listener != null) {
                writeRunnable.listener.failed();
            }
        }
    }

    public static void writeArg(String value, String path) {
        writeArg(value, path, (WriteListener) null);
    }

    public static void writeArg(String value, String path, WriteListener listener) {
        if (sysIoThread != null) {
            sysIoThread.interrupt();
        }
        sysIoThread = new Thread(new WriteRunnable(value, path, listener));
        sysIoThread.start();
    }

    public static String getArg(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                Log.e(SysConfigUtil.class.getName(), "no exists file");
            }
            return new BufferedReader(new FileReader(file)).readLine();
        } catch (FileNotFoundException | IOException e) {
            return "";
        }
    }
}
