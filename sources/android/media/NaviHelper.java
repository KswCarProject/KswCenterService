package android.media;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.wits.pms.PowerManagerApp;

public class NaviHelper {
    public NaviHandler mHandler;

    public NaviHelper() {
        new Thread() {
            public void run() {
                Looper.prepare();
                NaviHelper.this.mHandler = new NaviHandler();
                Looper.loop();
            }
        }.start();
    }

    public static class NaviHandler extends Handler {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                PowerManagerApp.sendCommand("{\"command\":1,\"jsonArg\":\"false\",\"subCommand\":608}");
            }
        }
    }

    public void start() {
        StackTraceElement[] element = Thread.currentThread().getStackTrace();
        for (int i = 0; i < element.length; i++) {
            if (element[i].toString().contains("com.cyberon") || element[i].toString().contains("com.mactiontech.cvr") || element[i].toString().contains("navngo.igo")) {
                PowerManagerApp.sendCommand("{\"command\":1,\"jsonArg\":\"true\",\"subCommand\":608}");
                if (element[i].toString().contains("navngo.igo")) {
                    this.mHandler.removeMessages(0);
                    this.mHandler.sendEmptyMessageDelayed(0, 2000);
                    return;
                }
                return;
            }
        }
    }

    public void stop() {
        StackTraceElement[] element = Thread.currentThread().getStackTrace();
        for (int i = 0; i < element.length; i++) {
            if (element[i].toString().contains("com.cyberon") || element[i].toString().contains("com.mactiontech.cvr") || element[i].toString().contains("navngo.igo")) {
                PowerManagerApp.sendCommand("{\"command\":1,\"jsonArg\":\"false\",\"subCommand\":608}");
                return;
            }
        }
    }
}
