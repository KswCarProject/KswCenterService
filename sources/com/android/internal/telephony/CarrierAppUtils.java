package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.res.Resources;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.server.SystemConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "CarrierAppUtils";

    private CarrierAppUtils() {
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String callingPackage, IPackageManager packageManager, TelephonyManager telephonyManager, ContentResolver contentResolver, int userId) {
        synchronized (CarrierAppUtils.class) {
            SystemConfig config = SystemConfig.getInstance();
            disableCarrierAppsUntilPrivileged(callingPackage, packageManager, telephonyManager, contentResolver, userId, config.getDisabledUntilUsedPreinstalledCarrierApps(), config.getDisabledUntilUsedPreinstalledCarrierAssociatedApps());
        }
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String callingPackage, IPackageManager packageManager, ContentResolver contentResolver, int userId) {
        synchronized (CarrierAppUtils.class) {
            SystemConfig config = SystemConfig.getInstance();
            disableCarrierAppsUntilPrivileged(callingPackage, packageManager, (TelephonyManager) null, contentResolver, userId, config.getDisabledUntilUsedPreinstalledCarrierApps(), config.getDisabledUntilUsedPreinstalledCarrierAssociatedApps());
        }
    }

    @VisibleForTesting
    public static void disableCarrierAppsUntilPrivileged(String callingPackage, IPackageManager packageManager, TelephonyManager telephonyManager, ContentResolver contentResolver, int userId, ArraySet<String> systemCarrierAppsDisabledUntilUsed, ArrayMap<String, List<String>> systemCarrierAssociatedAppsDisabledUntilUsed) {
        boolean z;
        ApplicationInfo ai;
        int i;
        IPackageManager iPackageManager = packageManager;
        TelephonyManager telephonyManager2 = telephonyManager;
        ContentResolver contentResolver2 = contentResolver;
        int i2 = userId;
        List<ApplicationInfo> candidates = getDefaultCarrierAppCandidatesHelper(iPackageManager, i2, systemCarrierAppsDisabledUntilUsed);
        if (candidates == null || candidates.isEmpty()) {
            ArrayMap<String, List<String>> arrayMap = systemCarrierAssociatedAppsDisabledUntilUsed;
            return;
        }
        Map<String, List<ApplicationInfo>> associatedApps = getDefaultCarrierAssociatedAppsHelper(iPackageManager, i2, systemCarrierAssociatedAppsDisabledUntilUsed);
        List<String> enabledCarrierPackages = new ArrayList<>();
        boolean z2 = false;
        boolean z3 = true;
        boolean hasRunOnce = Settings.Secure.getIntForUser(contentResolver2, Settings.Secure.CARRIER_APPS_HANDLED, 0, i2) == 1;
        try {
            Iterator<ApplicationInfo> it = candidates.iterator();
            while (it.hasNext()) {
                ApplicationInfo ai2 = it.next();
                String packageName = ai2.packageName;
                String[] restrictedCarrierApps = Resources.getSystem().getStringArray(R.array.config_restrictedPreinstalledCarrierApps);
                boolean hasPrivileges = (telephonyManager2 == null || telephonyManager2.checkCarrierPrivilegesForPackageAnyPhone(packageName) != z3 || ArrayUtils.contains((T[]) restrictedCarrierApps, packageName)) ? z2 : z3;
                iPackageManager.setSystemAppHiddenUntilInstalled(packageName, z3);
                List<ApplicationInfo> associatedAppList = associatedApps.get(packageName);
                if (associatedAppList != null) {
                    for (ApplicationInfo associatedApp : associatedAppList) {
                        iPackageManager.setSystemAppHiddenUntilInstalled(associatedApp.packageName, true);
                        it = it;
                    }
                }
                Iterator<ApplicationInfo> it2 = it;
                if (hasPrivileges) {
                    if (!ai2.isUpdatedSystemApp()) {
                        if (!(ai2.enabledSetting == 0 || ai2.enabledSetting == 4)) {
                            if ((ai2.flags & 8388608) != 0) {
                                String[] strArr = restrictedCarrierApps;
                                String str = packageName;
                                ai = ai2;
                                i = 4;
                            }
                        }
                        Slog.i(TAG, "Update state(" + packageName + "): ENABLED for user " + i2);
                        iPackageManager.setSystemAppInstallState(packageName, true, i2);
                        String[] strArr2 = restrictedCarrierApps;
                        String str2 = packageName;
                        ai = ai2;
                        i = 4;
                        packageManager.setApplicationEnabledSetting(packageName, 1, 1, userId, callingPackage);
                    } else {
                        String str3 = packageName;
                        ai = ai2;
                        i = 4;
                    }
                    if (associatedAppList != null) {
                        for (ApplicationInfo associatedApp2 : associatedAppList) {
                            if (!(associatedApp2.enabledSetting == 0 || associatedApp2.enabledSetting == i)) {
                                if ((associatedApp2.flags & 8388608) != 0) {
                                    i = 4;
                                }
                            }
                            Slog.i(TAG, "Update associated state(" + associatedApp2.packageName + "): ENABLED for user " + i2);
                            iPackageManager.setSystemAppInstallState(associatedApp2.packageName, true, i2);
                            ApplicationInfo applicationInfo = associatedApp2;
                            packageManager.setApplicationEnabledSetting(associatedApp2.packageName, 1, 1, userId, callingPackage);
                            i = 4;
                        }
                    }
                    enabledCarrierPackages.add(ai.packageName);
                    z = false;
                } else {
                    String packageName2 = packageName;
                    ApplicationInfo ai3 = ai2;
                    if (ai3.isUpdatedSystemApp() || ai3.enabledSetting != 0 || (ai3.flags & 8388608) == 0) {
                        z = false;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Update state(");
                        String packageName3 = packageName2;
                        sb.append(packageName3);
                        sb.append("): DISABLED_UNTIL_USED for user ");
                        sb.append(i2);
                        Slog.i(TAG, sb.toString());
                        z = false;
                        iPackageManager.setSystemAppInstallState(packageName3, false, i2);
                    }
                    if (!hasRunOnce && associatedAppList != null) {
                        for (ApplicationInfo associatedApp3 : associatedAppList) {
                            if (associatedApp3.enabledSetting == 0) {
                                if ((associatedApp3.flags & 8388608) != 0) {
                                    Slog.i(TAG, "Update associated state(" + associatedApp3.packageName + "): DISABLED_UNTIL_USED for user " + i2);
                                    z = false;
                                    iPackageManager.setSystemAppInstallState(associatedApp3.packageName, false, i2);
                                }
                            }
                        }
                    }
                }
                z2 = z;
                it = it2;
                z3 = true;
            }
            if (!hasRunOnce) {
                Settings.Secure.putIntForUser(contentResolver2, Settings.Secure.CARRIER_APPS_HANDLED, 1, i2);
            }
            if (!enabledCarrierPackages.isEmpty()) {
                String[] packageNames = new String[enabledCarrierPackages.size()];
                enabledCarrierPackages.toArray(packageNames);
                iPackageManager.grantDefaultPermissionsToEnabledCarrierApps(packageNames, i2);
            }
        } catch (RemoteException e) {
            Slog.w(TAG, "Could not reach PackageManager", e);
        }
    }

    public static List<ApplicationInfo> getDefaultCarrierApps(IPackageManager packageManager, TelephonyManager telephonyManager, int userId) {
        List<ApplicationInfo> candidates = getDefaultCarrierAppCandidates(packageManager, userId);
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        for (int i = candidates.size() - 1; i >= 0; i--) {
            if (!(telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(candidates.get(i).packageName) == 1)) {
                candidates.remove(i);
            }
        }
        return candidates;
    }

    public static List<ApplicationInfo> getDefaultCarrierAppCandidates(IPackageManager packageManager, int userId) {
        return getDefaultCarrierAppCandidatesHelper(packageManager, userId, SystemConfig.getInstance().getDisabledUntilUsedPreinstalledCarrierApps());
    }

    private static List<ApplicationInfo> getDefaultCarrierAppCandidatesHelper(IPackageManager packageManager, int userId, ArraySet<String> systemCarrierAppsDisabledUntilUsed) {
        int size;
        if (systemCarrierAppsDisabledUntilUsed == null || (size = systemCarrierAppsDisabledUntilUsed.size()) == 0) {
            return null;
        }
        List<ApplicationInfo> apps = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ApplicationInfo ai = getApplicationInfoIfSystemApp(packageManager, userId, systemCarrierAppsDisabledUntilUsed.valueAt(i));
            if (ai != null) {
                apps.add(ai);
            }
        }
        return apps;
    }

    private static Map<String, List<ApplicationInfo>> getDefaultCarrierAssociatedAppsHelper(IPackageManager packageManager, int userId, ArrayMap<String, List<String>> systemCarrierAssociatedAppsDisabledUntilUsed) {
        int size = systemCarrierAssociatedAppsDisabledUntilUsed.size();
        Map<String, List<ApplicationInfo>> associatedApps = new ArrayMap<>(size);
        for (int i = 0; i < size; i++) {
            String carrierAppPackage = systemCarrierAssociatedAppsDisabledUntilUsed.keyAt(i);
            List<String> associatedAppPackages = systemCarrierAssociatedAppsDisabledUntilUsed.valueAt(i);
            for (int j = 0; j < associatedAppPackages.size(); j++) {
                ApplicationInfo ai = getApplicationInfoIfSystemApp(packageManager, userId, associatedAppPackages.get(j));
                if (ai != null && !ai.isUpdatedSystemApp()) {
                    List<ApplicationInfo> appList = associatedApps.get(carrierAppPackage);
                    if (appList == null) {
                        appList = new ArrayList<>();
                        associatedApps.put(carrierAppPackage, appList);
                    }
                    appList.add(ai);
                }
            }
        }
        return associatedApps;
    }

    private static ApplicationInfo getApplicationInfoIfSystemApp(IPackageManager packageManager, int userId, String packageName) {
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(packageName, 536903680, userId);
            if (ai == null || !ai.isSystemApp()) {
                return null;
            }
            return ai;
        } catch (RemoteException e) {
            Slog.w(TAG, "Could not reach PackageManager", e);
            return null;
        }
    }
}
