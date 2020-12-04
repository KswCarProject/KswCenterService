package com.wits.pms.utils;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AmsUtil {
    public static void forceStopPackage(Context context, String pkgName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", new Class[]{String.class}).invoke(am, new Object[]{pkgName});
        } catch (Exception e) {
        }
    }

    public static int getPid(String psName) {
        try {
            String[] cmd = {"/system/bin/sh", "-c", "ps -e| grep -w " + psName};
            Log.d("zjqtest", "getPid  cmd = " + cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));
            Log.d("zjqtest", "getPid  01 ");
            while (true) {
                String readLine = in.readLine();
                String line = readLine;
                if (readLine == null) {
                    Log.d("zjqtest", "getPid  04 ");
                    in.close();
                    break;
                }
                Log.d("zjqtest", "getPid  02 ");
                if (!line.isEmpty()) {
                    try {
                        Log.d("zjqtest", "getPid  03 ");
                        String[] inf = line.split(" +");
                        if (psName.equals(inf[inf.length - 1])) {
                            in.close();
                            return Integer.parseInt(line.split(" +")[1]);
                        }
                    } catch (Exception e) {
                        Log.i("GetPid", "line error", e);
                    }
                } else {
                    throw new IOException("No such process: " + psName);
                }
            }
        } catch (Exception e2) {
            Log.i("GetPid", "Error finding PID of process: " + psName + "\n", e2);
        }
        return -1;
    }

    public static boolean catchLog(String content) {
        String line;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "logcat -d -s goc | grep -w " + content}).getInputStream()));
            do {
                String readLine = in.readLine();
                line = readLine;
                if (readLine == null) {
                    in.close();
                    return false;
                } else if (line.isEmpty()) {
                    throw new IOException("No such process: " + content);
                }
            } while (!line.contains(content));
            return true;
        } catch (Exception e) {
        }
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
        try {
            Class.forName("android.app.AppOpsManager").getMethod("setMode", new Class[]{Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE}).invoke(appOpsManager, new Object[]{70, Integer.valueOf(uid), packageName, Integer.valueOf(mode)});
            Log.v("AmsUtil", "setForceAppStandby - package: " + packageName);
        } catch (Exception e2) {
        }
    }
}
