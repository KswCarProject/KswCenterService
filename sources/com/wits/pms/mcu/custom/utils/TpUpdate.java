package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.util.Log;
import android.util.TimedRemoteCaller;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.utils.SystemProperties;
import java.io.File;

public class TpUpdate {
    public static void check(final Context context, String path) {
        Log.d("TpUpdate", "check   path = " + path);
        File tpFile = new File(path + "/tp_param.cfg");
        if (tpFile.exists()) {
            tpUpdate(tpFile.getAbsolutePath());
            new Thread() {
                public void run() {
                    try {
                        sleep(TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String state = SystemProperties.get("ksw.tp.update.state");
                    Log.d("TpUpdate", "state = " + state);
                    if (state.equals("true")) {
                        Toast.makeText(context, (CharSequence) context.getString(R.string.tp_update_success), 1).show();
                    } else {
                        Toast.makeText(context, (CharSequence) context.getString(R.string.tp_update_fail), 1).show();
                    }
                }
            }.run();
        }
    }

    private static void tpUpdate(String path) {
        Log.d("TpUpdate", "tpUpdate   path = " + path);
        SystemProperties.set("ksw.tp.update.state", "false");
        SystemProperties.set("persist.update_tp.path", path);
        SystemProperties.set("persist.updatetp.switch", "0");
        SystemProperties.set("persist.updatetp.switch", "1");
    }
}
