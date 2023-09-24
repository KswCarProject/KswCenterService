package com.wits.pms.core;

import android.app.slice.SliceItem;
import android.p007os.ShellCommand;
import android.provider.SettingsStringUtil;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
class PowerManagerAppCommand extends ShellCommand {
    private final PowerManagerImpl mPmAppService;

    public PowerManagerAppCommand(PowerManagerImpl pmAppService) {
        this.mPmAppService = pmAppService;
    }

    private void showWitsCommandHelp() {
        PrintWriter out = getOutPrintWriter();
        out.println("use: input wits [\u7c7b\u578b] [\u53c2\u6570]");
        out.println("type:");
        out.println("       cmd");
        out.println("       status\n");
        out.println("cmd arg: command subcommand jsonArg");
        out.println("cmd eg.");
        out.println("       wits cmd 1 105 true\n");
        out.println("status arg: [\u65b9\u6cd5\u7c7b\u578b] [\u6570\u636e\u7c7b\u578b] key value");
        out.println("method type:");
        out.println("       get");
        out.println("       set");
        out.println("data type:");
        out.println("       int");
        out.println("       bool");
        out.println("       string");
        out.println("status eg.");
        out.println("       wits status set int callStatus 7");
        out.println("       wits status set bool isConnected true");
        out.println("       wits status set string musicName '\u8292\u79cd'");
        out.println("       wits status get int callStatus");
        out.println("       wits status get bool isConnected");
        out.println("       wits status get string musicName");
    }

    private int witsCommand(PrintWriter out) {
        try {
            int command = Integer.parseInt(getNextArg());
            int subCommand = Integer.parseInt(getNextArg());
            String arg = getNextArg();
            if (WitsCommand.sendCommandWithBack(command, subCommand, arg)) {
                out.println("cmd result false");
                return 1;
            }
            return 0;
        } catch (Exception e) {
            out.println("args error");
            return 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005a A[Catch: Exception -> 0x0118, TryCatch #0 {Exception -> 0x0118, blocks: (B:5:0x0008, B:7:0x0027, B:22:0x0054, B:28:0x0070, B:24:0x005a, B:25:0x005e, B:26:0x0066, B:12:0x0036, B:15:0x0040, B:18:0x004a, B:30:0x0074, B:32:0x007c, B:47:0x00a5, B:52:0x00ff, B:49:0x00ab, B:50:0x00c7, B:51:0x00e3, B:37:0x0087, B:40:0x0091, B:43:0x009b, B:54:0x0103), top: B:60:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005e A[Catch: Exception -> 0x0118, TryCatch #0 {Exception -> 0x0118, blocks: (B:5:0x0008, B:7:0x0027, B:22:0x0054, B:28:0x0070, B:24:0x005a, B:25:0x005e, B:26:0x0066, B:12:0x0036, B:15:0x0040, B:18:0x004a, B:30:0x0074, B:32:0x007c, B:47:0x00a5, B:52:0x00ff, B:49:0x00ab, B:50:0x00c7, B:51:0x00e3, B:37:0x0087, B:40:0x0091, B:43:0x009b, B:54:0x0103), top: B:60:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0066 A[Catch: Exception -> 0x0118, TryCatch #0 {Exception -> 0x0118, blocks: (B:5:0x0008, B:7:0x0027, B:22:0x0054, B:28:0x0070, B:24:0x005a, B:25:0x005e, B:26:0x0066, B:12:0x0036, B:15:0x0040, B:18:0x004a, B:30:0x0074, B:32:0x007c, B:47:0x00a5, B:52:0x00ff, B:49:0x00ab, B:50:0x00c7, B:51:0x00e3, B:37:0x0087, B:40:0x0091, B:43:0x009b, B:54:0x0103), top: B:60:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ab A[Catch: Exception -> 0x0118, TryCatch #0 {Exception -> 0x0118, blocks: (B:5:0x0008, B:7:0x0027, B:22:0x0054, B:28:0x0070, B:24:0x005a, B:25:0x005e, B:26:0x0066, B:12:0x0036, B:15:0x0040, B:18:0x004a, B:30:0x0074, B:32:0x007c, B:47:0x00a5, B:52:0x00ff, B:49:0x00ab, B:50:0x00c7, B:51:0x00e3, B:37:0x0087, B:40:0x0091, B:43:0x009b, B:54:0x0103), top: B:60:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c7 A[Catch: Exception -> 0x0118, TryCatch #0 {Exception -> 0x0118, blocks: (B:5:0x0008, B:7:0x0027, B:22:0x0054, B:28:0x0070, B:24:0x005a, B:25:0x005e, B:26:0x0066, B:12:0x0036, B:15:0x0040, B:18:0x004a, B:30:0x0074, B:32:0x007c, B:47:0x00a5, B:52:0x00ff, B:49:0x00ab, B:50:0x00c7, B:51:0x00e3, B:37:0x0087, B:40:0x0091, B:43:0x009b, B:54:0x0103), top: B:60:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e3 A[Catch: Exception -> 0x0118, TryCatch #0 {Exception -> 0x0118, blocks: (B:5:0x0008, B:7:0x0027, B:22:0x0054, B:28:0x0070, B:24:0x005a, B:25:0x005e, B:26:0x0066, B:12:0x0036, B:15:0x0040, B:18:0x004a, B:30:0x0074, B:32:0x007c, B:47:0x00a5, B:52:0x00ff, B:49:0x00ab, B:50:0x00c7, B:51:0x00e3, B:37:0x0087, B:40:0x0091, B:43:0x009b, B:54:0x0103), top: B:60:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int witsStatus(PrintWriter out) {
        if (PowerManagerApp.getManager() != null) {
            try {
                String method = getNextArg();
                String type = getNextArg();
                String key = getNextArg();
                char c = 2;
                if (method.equals("set")) {
                    String valueString = getNextArg();
                    int hashCode = type.hashCode();
                    if (hashCode == -891985903) {
                        if (type.equals("string")) {
                            switch (c) {
                            }
                        }
                        c = '\uffff';
                        switch (c) {
                        }
                    } else if (hashCode != 104431) {
                        if (hashCode == 3029738 && type.equals("bool")) {
                            c = 1;
                            switch (c) {
                                case 0:
                                    int valueInt = Integer.parseInt(valueString);
                                    PowerManagerApp.setStatusInt(key, valueInt);
                                    break;
                                case 1:
                                    boolean valueBool = Boolean.parseBoolean(valueString);
                                    PowerManagerApp.setBooleanStatus(key, valueBool);
                                    break;
                                case 2:
                                    PowerManagerApp.setStatusString(key, valueString);
                                    break;
                                default:
                                    out.println("status result false");
                                    return 1;
                            }
                        }
                        c = '\uffff';
                        switch (c) {
                        }
                    } else {
                        if (type.equals(SliceItem.FORMAT_INT)) {
                            c = 0;
                            switch (c) {
                            }
                        }
                        c = '\uffff';
                        switch (c) {
                        }
                    }
                } else if (method.equals("get")) {
                    int hashCode2 = type.hashCode();
                    if (hashCode2 == -891985903) {
                        if (type.equals("string")) {
                            switch (c) {
                            }
                        }
                        c = '\uffff';
                        switch (c) {
                        }
                    } else if (hashCode2 != 104431) {
                        if (hashCode2 == 3029738 && type.equals("bool")) {
                            c = 1;
                            switch (c) {
                                case 0:
                                    int statusInt = PowerManagerApp.getStatusInt(key);
                                    out.println(key + SettingsStringUtil.DELIMITER + statusInt);
                                    break;
                                case 1:
                                    boolean statusBoolean = PowerManagerApp.getStatusBoolean(key);
                                    out.println(key + SettingsStringUtil.DELIMITER + statusBoolean);
                                    break;
                                case 2:
                                    String statusString = PowerManagerApp.getStatusString(key);
                                    out.println(key + SettingsStringUtil.DELIMITER + statusString);
                                    break;
                                default:
                                    out.println("status result false");
                                    return 1;
                            }
                        }
                        c = '\uffff';
                        switch (c) {
                        }
                    } else {
                        if (type.equals(SliceItem.FORMAT_INT)) {
                            c = 0;
                            switch (c) {
                            }
                        }
                        c = '\uffff';
                        switch (c) {
                        }
                    }
                } else {
                    out.println("args error method:" + method);
                }
            } catch (Exception e) {
                out.println(e.getCause().toString());
                out.println("args error");
                return 1;
            }
        }
        return 0;
    }

    @Override // android.p007os.ShellCommand
    public int onCommand(String cmd) {
        if (cmd == null) {
            return handleDefaultCommands(cmd);
        }
        PrintWriter out = getOutPrintWriter();
        char c = '\uffff';
        int hashCode = cmd.hashCode();
        if (hashCode != -892481550) {
            if (hashCode == 98618 && cmd.equals("cmd")) {
                c = 0;
            }
        } else if (cmd.equals("status")) {
            c = 1;
        }
        switch (c) {
            case 0:
                return witsCommand(out);
            case 1:
                return witsStatus(out);
            default:
                return handleDefaultCommands(cmd);
        }
    }

    @Override // android.p007os.ShellCommand
    public void onHelp() {
        showWitsCommandHelp();
    }
}
