package com.wits.pms.core;

import com.google.gson.Gson;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.statuscontrol.WitsStatus;

/* loaded from: classes2.dex */
public class CommandFactory {
    public static void sendOut(SystemStatus systemStatus) {
        PowerManagerApp.sendStatus(new WitsStatus(1, new Gson().toJson(systemStatus)));
    }

    public static void sendOut(McuStatus mcuStatus) {
        String jsonArg = new Gson().toJson(mcuStatus);
        PowerManagerApp.sendStatus(new WitsStatus(5, jsonArg));
    }
}
