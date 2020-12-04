package com.wits.pms.core;

import android.os.ShellCommand;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.PrintWriter;

class PowerManagerAppCommand extends ShellCommand {
    private final PowerManagerImpl mPmAppService;

    public PowerManagerAppCommand(PowerManagerImpl pmAppService) {
        this.mPmAppService = pmAppService;
    }

    private void showWitsCommandHelp() {
        PrintWriter out = getOutPrintWriter();
        out.println("use: input wits [类型] [参数]");
        out.println("type:");
        out.println("       cmd");
        out.println("       status\n");
        out.println("cmd arg: command subcommand jsonArg");
        out.println("cmd eg.");
        out.println("       wits cmd 1 105 true\n");
        out.println("status arg: [方法类型] [数据类型] key value");
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
        out.println("       wits status set string musicName '芒种'");
        out.println("       wits status get int callStatus");
        out.println("       wits status get bool isConnected");
        out.println("       wits status get string musicName");
    }

    private int witsCommand(PrintWriter out) {
        try {
            if (!WitsCommand.sendCommandWithBack(Integer.parseInt(getNextArg()), Integer.parseInt(getNextArg()), getNextArg())) {
                return 0;
            }
            out.println("cmd result false");
            return 1;
        } catch (Exception e) {
            out.println("args error");
            return 1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0057 A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x005a A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005e A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0066 A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a8 A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ab A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c7 A[Catch:{ Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e3 A[Catch:{ Exception -> 0x0118 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int witsStatus(java.io.PrintWriter r13) {
        /*
            r12 = this;
            com.wits.pms.IPowerManagerAppService r0 = com.wits.pms.statuscontrol.PowerManagerApp.getManager()
            r1 = 0
            if (r0 == 0) goto L_0x012a
            r0 = 1
            java.lang.String r2 = r12.getNextArg()     // Catch:{ Exception -> 0x0118 }
            java.lang.String r3 = r12.getNextArg()     // Catch:{ Exception -> 0x0118 }
            java.lang.String r4 = r12.getNextArg()     // Catch:{ Exception -> 0x0118 }
            java.lang.String r5 = "set"
            boolean r5 = r2.equals(r5)     // Catch:{ Exception -> 0x0118 }
            r6 = 2
            r7 = 3029738(0x2e3aea, float:4.245567E-39)
            r8 = 104431(0x197ef, float:1.46339E-40)
            r9 = -891985903(0xffffffffcad56011, float:-6991880.5)
            r10 = -1
            if (r5 == 0) goto L_0x0074
            java.lang.String r5 = r12.getNextArg()     // Catch:{ Exception -> 0x0118 }
            int r11 = r3.hashCode()     // Catch:{ Exception -> 0x0118 }
            if (r11 == r9) goto L_0x004a
            if (r11 == r8) goto L_0x0040
            if (r11 == r7) goto L_0x0036
            goto L_0x0053
        L_0x0036:
            java.lang.String r6 = "bool"
            boolean r6 = r3.equals(r6)     // Catch:{ Exception -> 0x0118 }
            if (r6 == 0) goto L_0x0053
            r6 = r0
            goto L_0x0054
        L_0x0040:
            java.lang.String r6 = "int"
            boolean r6 = r3.equals(r6)     // Catch:{ Exception -> 0x0118 }
            if (r6 == 0) goto L_0x0053
            r6 = r1
            goto L_0x0054
        L_0x004a:
            java.lang.String r7 = "string"
            boolean r7 = r3.equals(r7)     // Catch:{ Exception -> 0x0118 }
            if (r7 == 0) goto L_0x0053
            goto L_0x0054
        L_0x0053:
            r6 = r10
        L_0x0054:
            switch(r6) {
                case 0: goto L_0x0066;
                case 1: goto L_0x005e;
                case 2: goto L_0x005a;
                default: goto L_0x0057;
            }     // Catch:{ Exception -> 0x0118 }
        L_0x0057:
            java.lang.String r1 = "status result false"
            goto L_0x0070
        L_0x005a:
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r4, r5)     // Catch:{ Exception -> 0x0118 }
            goto L_0x006e
        L_0x005e:
            boolean r6 = java.lang.Boolean.parseBoolean(r5)     // Catch:{ Exception -> 0x0118 }
            com.wits.pms.statuscontrol.PowerManagerApp.setBooleanStatus(r4, r6)     // Catch:{ Exception -> 0x0118 }
            goto L_0x006e
        L_0x0066:
            int r6 = java.lang.Integer.parseInt(r5)     // Catch:{ Exception -> 0x0118 }
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusInt(r4, r6)     // Catch:{ Exception -> 0x0118 }
        L_0x006e:
            goto L_0x0117
        L_0x0070:
            r13.println(r1)     // Catch:{ Exception -> 0x0118 }
            return r0
        L_0x0074:
            java.lang.String r5 = "get"
            boolean r5 = r2.equals(r5)     // Catch:{ Exception -> 0x0118 }
            if (r5 == 0) goto L_0x0103
            int r5 = r3.hashCode()     // Catch:{ Exception -> 0x0118 }
            if (r5 == r9) goto L_0x009b
            if (r5 == r8) goto L_0x0091
            if (r5 == r7) goto L_0x0087
            goto L_0x00a4
        L_0x0087:
            java.lang.String r5 = "bool"
            boolean r5 = r3.equals(r5)     // Catch:{ Exception -> 0x0118 }
            if (r5 == 0) goto L_0x00a4
            r6 = r0
            goto L_0x00a5
        L_0x0091:
            java.lang.String r5 = "int"
            boolean r5 = r3.equals(r5)     // Catch:{ Exception -> 0x0118 }
            if (r5 == 0) goto L_0x00a4
            r6 = r1
            goto L_0x00a5
        L_0x009b:
            java.lang.String r5 = "string"
            boolean r5 = r3.equals(r5)     // Catch:{ Exception -> 0x0118 }
            if (r5 == 0) goto L_0x00a4
            goto L_0x00a5
        L_0x00a4:
            r6 = r10
        L_0x00a5:
            switch(r6) {
                case 0: goto L_0x00e3;
                case 1: goto L_0x00c7;
                case 2: goto L_0x00ab;
                default: goto L_0x00a8;
            }     // Catch:{ Exception -> 0x0118 }
        L_0x00a8:
            java.lang.String r1 = "status result false"
            goto L_0x00ff
        L_0x00ab:
            java.lang.String r5 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusString(r4)     // Catch:{ Exception -> 0x0118 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0118 }
            r6.<init>()     // Catch:{ Exception -> 0x0118 }
            r6.append(r4)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r7 = ":"
            r6.append(r7)     // Catch:{ Exception -> 0x0118 }
            r6.append(r5)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0118 }
            r13.println(r6)     // Catch:{ Exception -> 0x0118 }
            goto L_0x0117
        L_0x00c7:
            boolean r5 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusBoolean(r4)     // Catch:{ Exception -> 0x0118 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0118 }
            r6.<init>()     // Catch:{ Exception -> 0x0118 }
            r6.append(r4)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r7 = ":"
            r6.append(r7)     // Catch:{ Exception -> 0x0118 }
            r6.append(r5)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0118 }
            r13.println(r6)     // Catch:{ Exception -> 0x0118 }
            goto L_0x0117
        L_0x00e3:
            int r5 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r4)     // Catch:{ Exception -> 0x0118 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0118 }
            r6.<init>()     // Catch:{ Exception -> 0x0118 }
            r6.append(r4)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r7 = ":"
            r6.append(r7)     // Catch:{ Exception -> 0x0118 }
            r6.append(r5)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0118 }
            r13.println(r6)     // Catch:{ Exception -> 0x0118 }
            goto L_0x0117
        L_0x00ff:
            r13.println(r1)     // Catch:{ Exception -> 0x0118 }
            return r0
        L_0x0103:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0118 }
            r5.<init>()     // Catch:{ Exception -> 0x0118 }
            java.lang.String r6 = "args error method:"
            r5.append(r6)     // Catch:{ Exception -> 0x0118 }
            r5.append(r2)     // Catch:{ Exception -> 0x0118 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0118 }
            r13.println(r5)     // Catch:{ Exception -> 0x0118 }
        L_0x0117:
            goto L_0x012a
        L_0x0118:
            r1 = move-exception
            java.lang.Throwable r2 = r1.getCause()
            java.lang.String r2 = r2.toString()
            r13.println(r2)
            java.lang.String r2 = "args error"
            r13.println(r2)
            return r0
        L_0x012a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.core.PowerManagerAppCommand.witsStatus(java.io.PrintWriter):int");
    }

    public int onCommand(String cmd) {
        if (cmd == null) {
            return handleDefaultCommands(cmd);
        }
        PrintWriter out = getOutPrintWriter();
        char c = 65535;
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

    public void onHelp() {
        showWitsCommandHelp();
    }
}
