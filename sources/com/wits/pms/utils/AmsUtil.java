package com.wits.pms.utils;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.p002pm.PackageManager;
import android.util.Log;
import com.wits.pms.mirror.ActivityManagerMirror;
import com.wits.pms.mirror.AppOpsManagerMirror;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class AmsUtil {
    public static void forceStopPackage(Context context, String pkgName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        new ActivityManagerMirror(am).forceStopPackage(pkgName);
    }

    public static int getPid(String psName) {
        String[] inf;
        try {
            String[] cmd = {"/system/bin/sh", "-c", "ps -e| grep -w " + psName};
            Log.m72d("zjqtest", "getPid  cmd = " + cmd);
            Process ps = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            Log.m72d("zjqtest", "getPid  01 ");
            while (true) {
                String line = in.readLine();
                if (line != null) {
                    Log.m72d("zjqtest", "getPid  02 ");
                    if (line.isEmpty()) {
                        throw new IOException("No such process: " + psName);
                    }
                    try {
                        Log.m72d("zjqtest", "getPid  03 ");
                        inf = line.split(" +");
                    } catch (Exception e) {
                        Log.m67i("GetPid", "line error", e);
                    }
                    if (psName.equals(inf[inf.length - 1])) {
                        in.close();
                        int pid = Integer.parseInt(line.split(" +")[1]);
                        return pid;
                    }
                    continue;
                } else {
                    Log.m72d("zjqtest", "getPid  04 ");
                    in.close();
                    break;
                }
            }
        } catch (Exception e2) {
            Log.m67i("GetPid", "Error finding PID of process: " + psName + "\n", e2);
        }
        return -1;
    }

    public static boolean catchLog(String content) {
        BufferedReader in;
        String line;
        try {
            String[] cmd = {"/system/bin/sh", "-c", "logcat -d -s goc | grep -w " + content};
            Process ps = Runtime.getRuntime().exec(cmd);
            in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        } catch (Exception e) {
        }
        do {
            line = in.readLine();
            if (line != null) {
                if (line.isEmpty()) {
                    throw new IOException("No such process: " + content);
                }
            } else {
                in.close();
                return false;
            }
        } while (!line.contains(content));
        return true;
    }

    public static void setForceAppStandby(Context context, String packageName, int mode) {
        int uid = -1;
        if (packageName != null) {
            try {
                uid = context.getPackageManager().getPackageUid(packageName, 128);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        new AppOpsManagerMirror(appOpsManager).setMode(70, uid, packageName, mode);
        Log.m66v("AmsUtil", "setForceAppStandby - package: " + packageName);
    }
}
