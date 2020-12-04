package com.wits.pms.custom;

import android.content.Context;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.statuscontrol.PowerManagerApp;

public class KswMemoryController extends MemoryKiller {
    public KswMemoryController(Context mContext) {
        super(mContext);
        upgradeSystemProcessList();
    }

    private void upgradeSystemProcessList() {
        this.mImportantProcess.add("com.wits.ksw");
        this.mImportantProcess.add("com.wits.ksw.bt");
        this.mImportantProcess.add("com.wits.ksw.airc");
        try {
            this.mImportantProcess.add(PowerManagerApp.getSettingsString("NaviApp"));
        } catch (Exception e) {
        }
    }

    public void clearMemoryForCustom() {
        for (MemoryKiller.AppProcess appProcess : this.mAppList) {
            if (!appProcess.isUsingMediaApp() && appProcess.isWhiteListApp()) {
            }
        }
    }
}
