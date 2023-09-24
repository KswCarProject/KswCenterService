package com.wits.pms.mcu.custom;

import com.wits.pms.statuscontrol.WitsCommand;

/* loaded from: classes2.dex */
public class McuCommandListener {
    public static final int TYPE_MCU = 5;

    public static void handleCommand(WitsCommand witsCommand) {
        witsCommand.getSubCommand();
    }
}
