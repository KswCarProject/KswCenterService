package com.wits.pms.utils;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telecom.Logging.Session;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.ibm.icu.text.DateFormat;
import com.wits.pms.bean.APKInfo;

public class Utils {
    private static final String TAG = "Utils";
    private static final String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", FullBackup.APK_TREE_TOKEN, "b", FullBackup.CACHE_TREE_TOKEN, DateFormat.DAY, "e", FullBackup.FILES_TREE_TOKEN};

    public static String getInstalledVersionName(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = 28)
    public static long getInstalledVersionCode(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequiresApi(api = 28)
    public static APKInfo getApkInfo(Context context, String path) {
        APKInfo apkInfo = new APKInfo();
        PackageInfo info = context.getPackageManager().getPackageArchiveInfo(path, 1);
        apkInfo.setApkPath(path);
        if (info != null) {
            long versionCode = info.getLongVersionCode();
            apkInfo.setVersionName(info.versionName);
            apkInfo.setPkgName(info.packageName);
            apkInfo.setVersionCode(info.getLongVersionCode());
            Log.d(TAG, "getAPKVersion: pkname = " + info.packageName + " versionCode =" + versionCode + " path =" + path);
        }
        return apkInfo;
    }

    public static int compareVersion(String installVersion, String apkFileVersion) {
        if (installVersion.equals(apkFileVersion)) {
            return 0;
        }
        try {
            if (installVersion.contains(NativeLibraryHelper.CLEAR_ABI_OVERRIDE) || installVersion.contains(Session.SESSION_SEPARATION_CHAR_CHILD)) {
                installVersion = installVersion.replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ".").replace(Session.SESSION_SEPARATION_CHAR_CHILD, ".");
            }
            if (apkFileVersion.contains(NativeLibraryHelper.CLEAR_ABI_OVERRIDE) || apkFileVersion.contains(Session.SESSION_SEPARATION_CHAR_CHILD)) {
                apkFileVersion = apkFileVersion.replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ".").replace(Session.SESSION_SEPARATION_CHAR_CHILD, ".");
            }
            Log.d(TAG, "compareVersion: installVersion =" + installVersion + "  apkFileVersion =" + apkFileVersion);
            String[] version1Array = apkFileVersion.split("\\.");
            String[] version2Array = installVersion.split("\\.");
            int minLen = Math.min(version1Array.length, version2Array.length);
            int index = 0;
            int diff = 0;
            while (index < minLen) {
                int parseInt = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index]);
                diff = parseInt;
                if (parseInt != 0) {
                    break;
                }
                index++;
            }
            if (diff == 0) {
                for (int i = index; i < version1Array.length; i++) {
                    if (Integer.parseInt(version1Array[i]) > 0) {
                        return 1;
                    }
                }
                for (int i2 = index; i2 < version2Array.length; i2++) {
                    if (Integer.parseInt(version2Array[i2]) > 0) {
                        return -1;
                    }
                }
                return 0;
            } else if (diff > 0) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x007b, code lost:
        if (r0 == null) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x007d, code lost:
        r0.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a9, code lost:
        if (r0 != null) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ac, code lost:
        android.util.Log.e(TAG, "" + r4.toString());
        android.util.Log.d(TAG, "installApp end");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d7, code lost:
        return r3.toString().equalsIgnoreCase("success");
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean installApp(java.lang.String r9) {
        /*
            java.lang.String r0 = "Utils"
            java.lang.String r1 = "installApp start"
            android.util.Log.d(r0, r1)
            r0 = 0
            r1 = 0
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.ProcessBuilder r5 = new java.lang.ProcessBuilder     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r6 = 6
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r7 = 0
            java.lang.String r8 = "pm"
            r6[r7] = r8     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r7 = 1
            java.lang.String r8 = "install"
            r6[r7] = r8     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r7 = 2
            java.lang.String r8 = "-i"
            r6[r7] = r8     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r7 = 3
            java.lang.String r8 = "com.wits.pms"
            r6[r7] = r8     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r7 = 4
            java.lang.String r8 = "-r"
            r6[r7] = r8     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r7 = 5
            r6[r7] = r9     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            java.lang.Process r5 = r5.start()     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r0 = r5
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            java.io.InputStream r7 = r0.getInputStream()     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r1 = r5
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            java.io.InputStream r7 = r0.getErrorStream()     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r2 = r5
        L_0x005b:
            java.lang.String r5 = r1.readLine()     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r6 = r5
            if (r5 == 0) goto L_0x0066
            r3.append(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            goto L_0x005b
        L_0x0066:
            java.lang.String r5 = r2.readLine()     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            r6 = r5
            if (r5 == 0) goto L_0x0071
            r4.append(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0081 }
            goto L_0x0066
        L_0x0071:
            r1.close()     // Catch:{ Exception -> 0x007a }
            r2.close()     // Catch:{ Exception -> 0x007a }
            goto L_0x007b
        L_0x007a:
            r5 = move-exception
        L_0x007b:
            if (r0 == 0) goto L_0x00ac
        L_0x007d:
            r0.destroy()
            goto L_0x00ac
        L_0x0081:
            r5 = move-exception
            if (r1 == 0) goto L_0x008a
            r1.close()     // Catch:{ Exception -> 0x0088 }
            goto L_0x008a
        L_0x0088:
            r6 = move-exception
            goto L_0x0090
        L_0x008a:
            if (r2 == 0) goto L_0x0091
            r2.close()     // Catch:{ Exception -> 0x0088 }
            goto L_0x0091
        L_0x0090:
            goto L_0x0092
        L_0x0091:
        L_0x0092:
            if (r0 == 0) goto L_0x0097
            r0.destroy()
        L_0x0097:
            throw r5
        L_0x0098:
            r5 = move-exception
            if (r1 == 0) goto L_0x00a1
            r1.close()     // Catch:{ Exception -> 0x009f }
            goto L_0x00a1
        L_0x009f:
            r5 = move-exception
            goto L_0x00a7
        L_0x00a1:
            if (r2 == 0) goto L_0x00a8
            r2.close()     // Catch:{ Exception -> 0x009f }
            goto L_0x00a8
        L_0x00a7:
            goto L_0x00a9
        L_0x00a8:
        L_0x00a9:
            if (r0 == 0) goto L_0x00ac
            goto L_0x007d
        L_0x00ac:
            java.lang.String r5 = "Utils"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = ""
            r6.append(r7)
            java.lang.String r7 = r4.toString()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            android.util.Log.e(r5, r6)
            java.lang.String r5 = "Utils"
            java.lang.String r6 = "installApp end"
            android.util.Log.d(r5, r6)
            java.lang.String r5 = r3.toString()
            java.lang.String r6 = "success"
            boolean r5 = r5.equalsIgnoreCase(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.utils.Utils.installApp(java.lang.String):boolean");
    }

    public static void updateLocationEnabled(Context context, boolean enabled, int userId, int source) {
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.LOCATION_CHANGER, source, userId);
        ((LocationManager) context.getSystemService(LocationManager.class)).setLocationEnabledForUser(enabled, UserHandle.of(userId));
    }

    public static int getIndexValue2DataNew(byte data, int index, int length) {
        if (index < 0 || index > 7 || length < 1) {
            return data;
        }
        int calcLength = 8;
        if (length <= 8) {
            calcLength = length;
        }
        int calcData = 0;
        for (int i = 0; i < calcLength; i++) {
            calcData |= 1 << i;
        }
        return ((data & 255) >> index) & calcData;
    }

    public static String byteToHex(int n) {
        if (n < 0) {
            n += 256;
        }
        return "0x" + hexArray[n / 16] + hexArray[n % 16];
    }
}
