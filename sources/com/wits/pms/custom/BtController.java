package com.wits.pms.custom;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.Message;
import android.provider.Settings;
import android.util.Log;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mirror.SystemProperties;
import java.io.PrintStream;

/* loaded from: classes2.dex */
public class BtController {
    private static final String TAG = "BtController";
    private static Handler mKswBtHandler;
    private static String mRealBtAddress;
    private static boolean startBt;
    private static Thread startGocSdkThread;

    public static void rebootBt() {
        if (CenterControlImpl.getImpl().isUsingCarPlay() || CenterControlImpl.getImpl().isUsingUsbCarPlay()) {
            return;
        }
        Log.m72d(TAG, "reboot gocsdk");
        SystemProperties.set("vendor.init.ksw.bt_address", "0");
        SystemProperties.set("vendor.disable.bt", "0");
        SystemProperties.set("vendor.disable.gocsdk", "1");
        SystemProperties.set("vendor.disable.gocsdk", "0");
    }

    public static void fixBt(Context context) {
        startBt = false;
        startGocSdkThread = new Thread(new StartGocSdkThread());
        if (Build.VERSION.SDK_INT <= 28) {
            fixBt_P(context);
        } else {
            fixBt_Q(context);
        }
    }

    @SuppressLint({"HandlerLeak"})
    private static void fixBt_P(Context context) {
        Log.m66v(TAG, "fixBt start  007");
        Settings.System.putInt(context.getContentResolver(), "btSwitch", 1);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.wits.pms.custom.BtController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 10);
                    Log.m66v(BtController.TAG, "fixBt----Start----gocsdk state=" + state + " btEnable=" + BluetoothAdapter.getDefaultAdapter().isEnabled());
                    if (state == 12) {
                        String unused = BtController.mRealBtAddress = BluetoothAdapter.getDefaultAdapter().getAddress();
                        Log.m66v(BtController.TAG, "fixBt-----gocsdk get btAddr: " + BtController.mRealBtAddress);
                        BluetoothAdapter.getDefaultAdapter().disable();
                    }
                    if (state == 13) {
                        Log.m66v(BtController.TAG, "fixBt-----STATE_TURNING_OFF ");
                        SystemProperties.set("vendor.disable.bt", "1");
                    }
                    Log.m66v(BtController.TAG, "fixBt----End----gocsdk state=" + state + " btEnable=" + BluetoothAdapter.getDefaultAdapter().isEnabled());
                    if (!BtController.startBt && state == 10 && !"1".equals(SystemProperties.get("vendor.init.ksw.bt_address"))) {
                        Settings.System.putInt(context2.getContentResolver(), "ksw_bt_boot", 1);
                        Log.m66v(BtController.TAG, "fixBt-----gocsdk PowerManagerAppService start gocsdk bt");
                        SystemProperties.set("vendor.init.ksw.bt_address", "1");
                        boolean unused2 = BtController.startBt = true;
                    }
                }
            }
        }, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        mKswBtHandler = new Handler() { // from class: com.wits.pms.custom.BtController.2
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                boolean enable = BluetoothAdapter.getDefaultAdapter().enable();
                if (!enable) {
                    sendEmptyMessageDelayed(0, 2000L);
                }
                PrintStream printStream = System.out;
                printStream.println("-----gocsdk start qcom bt" + enable);
                Log.m66v(BtController.TAG, "fixBt-----gocsdk start qcom bt" + enable);
            }
        };
        if (!"1".equals(SystemProperties.get("vendor.init.ksw.bt_address"))) {
            SystemProperties.set("vendor.init.ksw.bt_address", "0");
            mKswBtHandler.sendEmptyMessageDelayed(0, Settings.System.getInt(context.getContentResolver(), "ksw_bt_boot", 0) == 0 ? 15000L : 0L);
        }
    }

    @SuppressLint({"HandlerLeak"})
    public static void fixBt_Q(Context context) {
        Log.m72d(TAG, "fixBt start  007");
        Settings.System.putInt(context.getContentResolver(), "btSwitch", 1);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.wits.pms.custom.BtController.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 10);
                    Log.m72d(BtController.TAG, "fixBt----Start----gocsdk state=" + state + " btEnable=" + BluetoothAdapter.getDefaultAdapter().isEnabled());
                    if (state == 12) {
                        String unused = BtController.mRealBtAddress = BluetoothAdapter.getDefaultAdapter().getAddress();
                        Log.m72d(BtController.TAG, "fixBt-----gocsdk get btAddr: " + BtController.mRealBtAddress);
                        SystemProperties.set("vendor.disable.bt", "1");
                    }
                    if (state == 13) {
                        Log.m72d(BtController.TAG, "fixBt-----STATE_TURNING_OFF ");
                    }
                    Log.m72d(BtController.TAG, "fixBt----End----gocsdk state=" + state + " btEnable=" + BluetoothAdapter.getDefaultAdapter().isEnabled());
                    if (state == 10) {
                        Log.m72d(BtController.TAG, "fixBt-----STATE_OFF ");
                        if (!BtController.startBt && !"1".equals(SystemProperties.get("vendor.init.ksw.bt_address"))) {
                            Settings.System.putInt(PowerManagerAppService.serviceContext.getContentResolver(), "ksw_bt_boot", 1);
                            Log.m72d(BtController.TAG, "fixBt-----gocsdk PowerManagerAppService start gocsdk bt");
                            SystemProperties.set("vendor.init.ksw.bt_address", "1");
                            boolean unused2 = BtController.startBt = true;
                        }
                    }
                }
            }
        }, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        mKswBtHandler = new Handler() { // from class: com.wits.pms.custom.BtController.4
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                boolean enable = BluetoothAdapter.getDefaultAdapter().enable();
                if (!enable) {
                    sendEmptyMessageDelayed(0, 2000L);
                }
                PrintStream printStream = System.out;
                printStream.println("-----gocsdk start qcom bt" + enable);
                Log.m72d(BtController.TAG, "fixBt-----gocsdk start qcom bt" + enable);
            }
        };
        if (!"1".equals(SystemProperties.get("vendor.init.ksw.bt_address"))) {
            SystemProperties.set("vendor.init.ksw.bt_address", "0");
            mKswBtHandler.sendEmptyMessageDelayed(0, Settings.System.getInt(context.getContentResolver(), "ksw_bt_boot", 0) == 0 ? 15000L : 0L);
        }
    }

    /* loaded from: classes2.dex */
    private static class StartGocSdkThread implements Runnable {
        private StartGocSdkThread() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }
}
