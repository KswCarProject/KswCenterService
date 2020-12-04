package com.wits.pms.custom;

import android.content.Context;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.statuscontrol.PowerManagerApp;
import java.util.ArrayList;

public class WitsMemoryController extends MemoryKiller {
    private static final String TAG = "MemoryKiller";

    public WitsMemoryController(Context mContext) {
        super(mContext);
        upgradeSystemProcessList();
        setWhiteListPkg(new ArrayList<String>() {
            {
                add("net.easyconn");
                add("zlink");
                add("txznet");
            }
        });
    }

    private void upgradeSystemProcessList() {
    }

    public void clearMemoryForCustom() {
        for (MemoryKiller.AppProcess appProcess : this.mAppList) {
            try {
                if (appProcess.getPkgName().contains(PowerManagerApp.getSettingsString("NaviApp"))) {
                }
            } catch (Exception e) {
            }
            if (!appProcess.isUsingMediaApp() && !appProcess.isWhiteListApp()) {
                kill(appProcess);
            }
        }
    }
}
