package com.wits.pms.custom;

import android.content.Context;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.statuscontrol.PowerManagerApp;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class WitsMemoryController extends MemoryKiller {
    private static final String TAG = "MemoryKiller";

    public WitsMemoryController(Context mContext) {
        super(mContext);
        upgradeSystemProcessList();
        List<String> whiteList = new ArrayList<String>() { // from class: com.wits.pms.custom.WitsMemoryController.1
            {
                add("net.easyconn");
                add("zlink");
                add("txznet");
            }
        };
        setWhiteListPkg(whiteList);
    }

    private void upgradeSystemProcessList() {
    }

    @Override // com.wits.pms.interfaces.MemoryKiller
    public void clearMemoryForCustom() {
        for (MemoryKiller.AppProcess appProcess : this.mAppList) {
            if (!appProcess.getPkgName().contains(PowerManagerApp.getSettingsString("NaviApp"))) {
                if (!appProcess.isUsingMediaApp() && !appProcess.isWhiteListApp()) {
                    kill(appProcess);
                }
            }
        }
    }
}
