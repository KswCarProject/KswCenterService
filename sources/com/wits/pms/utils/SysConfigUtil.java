package com.wits.pms.utils;

import android.p007os.Handler;
import android.util.Log;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.utils.SysConfigUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SysConfigUtil {
    public static final String AccConfig = "/sys/devices/soc/soc:gpio_keys/acc_int";
    public static final String BrightnessConfig = "/sys/devices/soc/1a00000.qcom,mdss_mdp/1a00000.qcom,mdss_mdp:qcom,mdss_fb_primary/leds/lcd-backlight/brightness";
    public static final String CddConfig = "/sys/devices/soc/soc:gpio_keys/ccd_int";
    public static final String EpbConfig = "/sys/devices/soc/soc:gpio_keys/break_det";
    public static final String IllConfig = "/sys/devices/soc/soc:gpio_keys/ill_int";
    public static final String RLightConfig = "/sys/devices/soc/soc:gpio_keys/rlight_int";
    static final String TAG = "SysConfigUtil";
    private static Thread sysIoThread;

    /* loaded from: classes2.dex */
    public interface WriteListener {
        void failed();

        void start();

        void success();
    }

    /* loaded from: classes2.dex */
    private static class WriteRunnable implements Runnable {
        private WriteListener listener;
        private final Handler mHandler = new Handler(PowerManagerAppService.serviceContext.getMainLooper());
        private String path;
        private String value;

        public WriteRunnable(String value, String path, WriteListener listener) {
            this.value = value;
            this.path = path;
            this.listener = listener;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mHandler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$SysConfigUtil$WriteRunnable$9OvvvEa1UlouCwi-V_sPzEqhqDI
                @Override // java.lang.Runnable
                public final void run() {
                    SysConfigUtil.WriteRunnable.lambda$run$0(SysConfigUtil.WriteRunnable.this);
                }
            });
            File awakeTimeFile = new File(this.path);
            try {
                if (this.path.equals(KswSettings.getSettings().getUiSavePath()) && this.value != null) {
                    Log.m68i(SysConfigUtil.TAG, "#13115 -- write uiSave then force sync");
                    FileOutputStream fos = new FileOutputStream(awakeTimeFile);
                    fos.write(this.value.getBytes());
                    fos.write("\n".getBytes());
                    fos.flush();
                    fos.getFD().sync();
                    fos.close();
                    this.mHandler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$SysConfigUtil$WriteRunnable$jiMq1Ww-g8m-9a2vZj9Qk-HgaI8
                        @Override // java.lang.Runnable
                        public final void run() {
                            SysConfigUtil.WriteRunnable.lambda$run$1(SysConfigUtil.WriteRunnable.this);
                        }
                    });
                    return;
                }
                FileWriter fr = new FileWriter(awakeTimeFile);
                if (this.value != null) {
                    fr.write(this.value + "\n");
                }
                fr.flush();
                fr.close();
                this.mHandler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$SysConfigUtil$WriteRunnable$tj814tvIGz-tF2u09O8Iq6-p-5Y
                    @Override // java.lang.Runnable
                    public final void run() {
                        SysConfigUtil.WriteRunnable.lambda$run$2(SysConfigUtil.WriteRunnable.this);
                    }
                });
            } catch (IOException e) {
                Log.m69e(SysConfigUtil.class.getName(), "save failed. cause:", e);
                this.mHandler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$SysConfigUtil$WriteRunnable$J7cxswu9ICAOKkZjpLOCVPo9_vc
                    @Override // java.lang.Runnable
                    public final void run() {
                        SysConfigUtil.WriteRunnable.lambda$run$3(SysConfigUtil.WriteRunnable.this);
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
                writeRunnable.listener.success();
            }
        }

        public static /* synthetic */ void lambda$run$3(WriteRunnable writeRunnable) {
            if (writeRunnable.listener != null) {
                writeRunnable.listener.failed();
            }
        }
    }

    public static void writeArg(String value, String path) {
        writeArg(value, path, null);
    }

    public static void writeArg(String value, String path, WriteListener listener) {
        if (sysIoThread != null) {
            sysIoThread.interrupt();
        }
        sysIoThread = new Thread(new WriteRunnable(value, path, listener));
        sysIoThread.start();
    }

    public static String getArg(String path) {
        FileReader fileReader = null;
        BufferedReader br = null;
        String result = "";
        try {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    Log.m70e(SysConfigUtil.class.getName(), "no exists file");
                }
                fileReader = new FileReader(file);
                br = new BufferedReader(fileReader);
                result = br.readLine();
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fileReader.close();
            } catch (FileNotFoundException e2) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e4) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (Throwable th) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        return result;
    }
}
