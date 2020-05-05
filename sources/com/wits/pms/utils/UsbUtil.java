package com.wits.pms.utils;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import com.wits.pms.BuildConfig;
import com.wits.pms.R;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.utils.SysConfigUtil;

public class UsbUtil {
    private static final String USB_CHANGE_PATH = "/sys/kernel/mhl_8334/wits_mhl_mode";
    public static final int USB_HOST_MODE = 1;
    public static final int USB_SLAVE_MODE = 0;

    public static void updateUsbMode(boolean isHost) {
        updateUsbMode((int) isHost);
    }

    public static void updateUsbMode(final int hostMode) {
        if (hostMode == 1 || hostMode == 0) {
            if (!(BuildConfig.FLAVOR + hostMode).equals(SysConfigUtil.getArg(USB_CHANGE_PATH))) {
                SysConfigUtil.writeArg(hostMode + BuildConfig.FLAVOR, USB_CHANGE_PATH, new SysConfigUtil.WriteListener() {
                    private AlertDialog usbDialog;

                    public void start() {
                        this.usbDialog = DialogUtils.createAlert(PowerManagerAppService.serviceContext, new ProgressBar(PowerManagerAppService.serviceContext), (String) null, (String) null, R.style.AlertTranCustom);
                        this.usbDialog.setCancelable(false);
                        this.usbDialog.show();
                    }

                    public void success() {
                        if (hostMode == 0) {
                            SystemProperties.set("sys.usb.config", "diag,adb");
                            Log.i(UsbUtil.class.getSimpleName(), "updateUsbMode slave setUp sys.usb.config : diag,serial_cdev,rmnet,dpl,adb");
                        } else {
                            Log.i(UsbUtil.class.getSimpleName(), "updateUsbMode host");
                        }
                        if (this.usbDialog != null) {
                            this.usbDialog.dismiss();
                        }
                    }

                    public void failed() {
                        Log.e(UsbUtil.class.getSimpleName(), "updateUsbMode failed");
                        if (this.usbDialog != null) {
                            this.usbDialog.dismiss();
                        }
                    }
                });
            }
        }
    }
}
