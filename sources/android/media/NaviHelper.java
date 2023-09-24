package android.media;

import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import com.wits.pms.PowerManagerApp;

/* loaded from: classes3.dex */
public class NaviHelper {
    public NaviHandler mHandler;

    /* JADX WARN: Type inference failed for: r0v0, types: [android.media.NaviHelper$1] */
    public NaviHelper() {
        new Thread() { // from class: android.media.NaviHelper.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                NaviHelper.this.mHandler = new NaviHandler();
                Looper.loop();
            }
        }.start();
    }

    /* loaded from: classes3.dex */
    public static class NaviHandler extends Handler {
        @Override // android.p007os.Handler
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
                    this.mHandler.sendEmptyMessageDelayed(0, 2000L);
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
