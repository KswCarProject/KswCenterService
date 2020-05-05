package com.wits.pms.core;

import com.google.gson.Gson;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.statuscontrol.WitsStatus;

public class CommandFactory {
    public static void sendOut(SystemStatus systemStatus) {
        PowerManagerApp.sendStatus(new WitsStatus(1, new Gson().toJson((Object) systemStatus)));
    }

    public static void sendOut(McuStatus mcuStatus) {
        PowerManagerApp.sendStatus(new WitsStatus(5, new Gson().toJson((Object) mcuStatus)));
    }
}
