package com.wits.pms.utils;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.location.LocationManager;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telecom.Logging.Session;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.ibm.icu.text.DateFormat;
import com.wits.pms.BuildConfig;
import com.wits.pms.bean.APKInfo;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class Utils {
    private static final String TAG = "Utils";
    private static final String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", FullBackup.APK_TREE_TOKEN, "b", FullBackup.CACHE_TREE_TOKEN, DateFormat.DAY, "e", FullBackup.FILES_TREE_TOKEN};

    public static String getInstalledVersionName(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            String name = info.versionName;
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = 28)
    public static long getInstalledVersionCode(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            long name = info.getLongVersionCode();
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @RequiresApi(api = 28)
    public static APKInfo getApkInfo(Context context, String path) {
        APKInfo apkInfo = new APKInfo();
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, 1);
        apkInfo.setApkPath(path);
        if (info != null) {
            long versionCode = info.getLongVersionCode();
            apkInfo.setVersionName(info.versionName);
            apkInfo.setPkgName(info.packageName);
            apkInfo.setVersionCode(info.getLongVersionCode());
            Log.m72d(TAG, "getAPKVersion: pkname = " + info.packageName + " versionCode =" + versionCode + " path =" + path);
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
            Log.m72d(TAG, "compareVersion: installVersion =" + installVersion + "  apkFileVersion =" + apkFileVersion);
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
            if (diff != 0) {
                return diff > 0 ? 1 : -1;
            }
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
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x007b, code lost:
        if (r0 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x007d, code lost:
        r0.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a9, code lost:
        if (r0 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ac, code lost:
        android.util.Log.m70e(com.wits.pms.utils.Utils.TAG, "" + r4.toString());
        android.util.Log.m72d(com.wits.pms.utils.Utils.TAG, "installApp end");
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d7, code lost:
        return r3.toString().equalsIgnoreCase("success");
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean installApp(String apkPath) {
        Log.m72d(TAG, "installApp start");
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
            process = new ProcessBuilder("pm", "install", "-i", BuildConfig.APPLICATION_ID, "-r", apkPath).start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while (true) {
                String s = successResult.readLine();
                if (s == null) {
                    break;
                }
                successMsg.append(s);
            }
            while (true) {
                String s2 = errorResult.readLine();
                if (s2 != null) {
                    errorMsg.append(s2);
                } else {
                    try {
                        break;
                    } catch (Exception e) {
                    }
                }
            }
            successResult.close();
            errorResult.close();
        } catch (Exception e2) {
            if (successResult != null) {
                try {
                    successResult.close();
                } catch (Exception e3) {
                }
            }
            if (errorResult != null) {
                errorResult.close();
            }
        } catch (Throwable th) {
            if (successResult != null) {
                try {
                    successResult.close();
                } catch (Exception e4) {
                    if (process != null) {
                        process.destroy();
                    }
                    throw th;
                }
            }
            if (errorResult != null) {
                errorResult.close();
            }
            if (process != null) {
            }
            throw th;
        }
    }

    public static void updateLocationEnabled(Context context, boolean enabled, int userId, int source) {
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.LOCATION_CHANGER, source, userId);
        LocationManager locationManager = (LocationManager) context.getSystemService(LocationManager.class);
        locationManager.setLocationEnabledForUser(enabled, UserHandle.m110of(userId));
    }

    public static int getIndexValue2DataNew(byte data, int index, int length) {
        if (index < 0 || index > 7) {
            return data;
        }
        if (length < 1) {
            return data;
        }
        int calcLength = length <= 8 ? length : 8;
        int calcData = 0;
        for (int i = 0; i < calcLength; i++) {
            calcData |= 1 << i;
        }
        int result = ((data & 255) >> index) & calcData;
        return result;
    }

    public static String byteToHex(int n) {
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return "0x" + hexArray[d1] + hexArray[d2];
    }

    public static void setApplicationEnabledSetting(Context context, int newState) {
        PackageManager mPm = context.getPackageManager();
        mPm.setApplicationEnabledSetting("com.google.android.apps.googleassistant", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.ims", newState, 0);
        mPm.setApplicationEnabledSetting("com.android.vending", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.apps.maps", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.partnersetup", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.gms", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.inputmethod.latin", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.googlequicksearchbox", newState, 0);
        mPm.setApplicationEnabledSetting("com.google.android.onetimeinitializer", newState, 0);
    }

    public static boolean isAppPakExist(Context context, String packageName) {
        boolean isExist = false;
        PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.m68i(TAG, "isAppPakExist: app not installed, packageName" + packageName);
        }
        if (applicationInfo != null) {
            isExist = true;
        }
        Log.m68i(TAG, "isAppPakExist: isExist = " + isExist);
        return isExist;
    }
}
