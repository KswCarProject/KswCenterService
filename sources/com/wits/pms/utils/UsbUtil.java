package com.wits.pms.utils;

import android.p007os.Build;
import android.support.p014v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import com.wits.pms.C3580R;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.utils.SysConfigUtil;

/* loaded from: classes2.dex */
public class UsbUtil {
    private static final String USB_CHANGE_PATH = "/sys/kernel/mhl_8334/wits_mhl_mode";
    public static final int USB_HOST_MODE = 1;
    private static final String USB_IO_PATH = "/sys/kernel/mhl_8334/wits_mhl_io_mode";
    public static final int USB_SLAVE_MODE = 0;
    private static boolean forceSlaveMode;

    public static void updateUsbMode(boolean isHost) {
        updateUsbMode(isHost ? 1 : 0);
    }

    public static void updateUsbMode(final int hostMode) {
        if (hostMode == 1 || hostMode == 0) {
            if (forceSlaveMode && hostMode == 1) {
                return;
            }
            if (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M501B")) {
                if (("" + hostMode).equals(SysConfigUtil.getArg(USB_CHANGE_PATH))) {
                    if (("" + hostMode).equals(SysConfigUtil.getArg(USB_IO_PATH))) {
                        return;
                    }
                }
            } else {
                if (("" + hostMode).equals(SysConfigUtil.getArg(USB_CHANGE_PATH))) {
                    return;
                }
            }
            if (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M501B")) {
                SysConfigUtil.writeArg(hostMode + "", USB_IO_PATH);
            }
            SysConfigUtil.writeArg(hostMode + "", USB_CHANGE_PATH, new SysConfigUtil.WriteListener() { // from class: com.wits.pms.utils.UsbUtil.1
                private AlertDialog usbDialog;

                @Override // com.wits.pms.utils.SysConfigUtil.WriteListener
                public void start() {
                    ProgressBar progressBar = new ProgressBar(PowerManagerAppService.serviceContext);
                    this.usbDialog = DialogUtils.createAlert(PowerManagerAppService.serviceContext, progressBar, null, null, C3580R.C3583style.AlertTranCustom);
                    this.usbDialog.setCancelable(false);
                    this.usbDialog.show();
                }

                @Override // com.wits.pms.utils.SysConfigUtil.WriteListener
                public void success() {
                    if (hostMode == 0) {
                        SystemProperties.set("sys.usb.config", "diag,adb");
                        Log.m68i(UsbUtil.class.getSimpleName(), "updateUsbMode slave setUp sys.usb.config : diag,serial_cdev,rmnet,dpl,adb");
                    } else {
                        Log.m68i(UsbUtil.class.getSimpleName(), "updateUsbMode host");
                    }
                    if (this.usbDialog != null) {
                        this.usbDialog.dismiss();
                    }
                }

                @Override // com.wits.pms.utils.SysConfigUtil.WriteListener
                public void failed() {
                    Log.m70e(UsbUtil.class.getSimpleName(), "updateUsbMode failed");
                    if (this.usbDialog != null) {
                        this.usbDialog.dismiss();
                    }
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.utils.UsbUtil$2] */
    public static void forceSlave(boolean open) {
        if (open) {
            updateUsbMode(false);
        }
        new Thread() { // from class: com.wits.pms.utils.UsbUtil.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(2500L);
                    } catch (InterruptedException e) {
                    }
                    if (SysConfigUtil.getArg(UsbUtil.USB_CHANGE_PATH).contains("1")) {
                        Log.m68i("UsbUtil", "force Slave - updateUsbMode slave.");
                        UsbUtil.updateUsbMode(false);
                    }
                    i++;
                }
            }
        }.start();
        forceSlaveMode = open;
    }
}
