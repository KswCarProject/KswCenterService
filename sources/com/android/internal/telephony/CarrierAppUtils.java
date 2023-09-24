package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.IPackageManager;
import android.content.res.Resources;
import android.p007os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.C3132R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.server.SystemConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "CarrierAppUtils";

    private CarrierAppUtils() {
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String callingPackage, IPackageManager packageManager, TelephonyManager telephonyManager, ContentResolver contentResolver, int userId) {
        synchronized (CarrierAppUtils.class) {
            SystemConfig config = SystemConfig.getInstance();
            ArraySet<String> systemCarrierAppsDisabledUntilUsed = config.getDisabledUntilUsedPreinstalledCarrierApps();
            ArrayMap<String, List<String>> systemCarrierAssociatedAppsDisabledUntilUsed = config.getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
            disableCarrierAppsUntilPrivileged(callingPackage, packageManager, telephonyManager, contentResolver, userId, systemCarrierAppsDisabledUntilUsed, systemCarrierAssociatedAppsDisabledUntilUsed);
        }
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String callingPackage, IPackageManager packageManager, ContentResolver contentResolver, int userId) {
        synchronized (CarrierAppUtils.class) {
            SystemConfig config = SystemConfig.getInstance();
            ArraySet<String> systemCarrierAppsDisabledUntilUsed = config.getDisabledUntilUsedPreinstalledCarrierApps();
            ArrayMap<String, List<String>> systemCarrierAssociatedAppsDisabledUntilUsed = config.getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
            disableCarrierAppsUntilPrivileged(callingPackage, packageManager, null, contentResolver, userId, systemCarrierAppsDisabledUntilUsed, systemCarrierAssociatedAppsDisabledUntilUsed);
        }
    }

    @VisibleForTesting
    public static void disableCarrierAppsUntilPrivileged(String callingPackage, IPackageManager packageManager, TelephonyManager telephonyManager, ContentResolver contentResolver, int userId, ArraySet<String> systemCarrierAppsDisabledUntilUsed, ArrayMap<String, List<String>> systemCarrierAssociatedAppsDisabledUntilUsed) {
        boolean z;
        ApplicationInfo ai;
        int i;
        List<ApplicationInfo> candidates = getDefaultCarrierAppCandidatesHelper(packageManager, userId, systemCarrierAppsDisabledUntilUsed);
        if (candidates == null || candidates.isEmpty()) {
            return;
        }
        Map<String, List<ApplicationInfo>> associatedApps = getDefaultCarrierAssociatedAppsHelper(packageManager, userId, systemCarrierAssociatedAppsDisabledUntilUsed);
        List<String> enabledCarrierPackages = new ArrayList<>();
        boolean z2 = false;
        boolean z3 = true;
        boolean hasRunOnce = Settings.Secure.getIntForUser(contentResolver, Settings.Secure.CARRIER_APPS_HANDLED, 0, userId) == 1;
        try {
            Iterator<ApplicationInfo> it = candidates.iterator();
            while (it.hasNext()) {
                ApplicationInfo ai2 = it.next();
                String packageName = ai2.packageName;
                String[] restrictedCarrierApps = Resources.getSystem().getStringArray(C3132R.array.config_restrictedPreinstalledCarrierApps);
                boolean hasPrivileges = (telephonyManager == null || telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(packageName) != z3 || ArrayUtils.contains(restrictedCarrierApps, packageName)) ? z2 : z3;
                packageManager.setSystemAppHiddenUntilInstalled(packageName, z3);
                List<ApplicationInfo> associatedAppList = associatedApps.get(packageName);
                if (associatedAppList != null) {
                    for (ApplicationInfo associatedApp : associatedAppList) {
                        packageManager.setSystemAppHiddenUntilInstalled(associatedApp.packageName, true);
                        it = it;
                    }
                }
                Iterator<ApplicationInfo> it2 = it;
                if (hasPrivileges) {
                    if (ai2.isUpdatedSystemApp()) {
                        ai = ai2;
                        i = 4;
                    } else {
                        if (ai2.enabledSetting != 0 && ai2.enabledSetting != 4 && (ai2.flags & 8388608) != 0) {
                            ai = ai2;
                            i = 4;
                        }
                        Slog.m54i(TAG, "Update state(" + packageName + "): ENABLED for user " + userId);
                        packageManager.setSystemAppInstallState(packageName, true, userId);
                        ai = ai2;
                        i = 4;
                        packageManager.setApplicationEnabledSetting(packageName, 1, 1, userId, callingPackage);
                    }
                    if (associatedAppList != null) {
                        for (ApplicationInfo associatedApp2 : associatedAppList) {
                            if (associatedApp2.enabledSetting != 0 && associatedApp2.enabledSetting != i && (associatedApp2.flags & 8388608) != 0) {
                                i = 4;
                            }
                            Slog.m54i(TAG, "Update associated state(" + associatedApp2.packageName + "): ENABLED for user " + userId);
                            packageManager.setSystemAppInstallState(associatedApp2.packageName, true, userId);
                            packageManager.setApplicationEnabledSetting(associatedApp2.packageName, 1, 1, userId, callingPackage);
                            i = 4;
                        }
                    }
                    enabledCarrierPackages.add(ai.packageName);
                    z = false;
                } else {
                    if (ai2.isUpdatedSystemApp() || ai2.enabledSetting != 0 || (ai2.flags & 8388608) == 0) {
                        z = false;
                    } else {
                        Slog.m54i(TAG, "Update state(" + packageName + "): DISABLED_UNTIL_USED for user " + userId);
                        z = false;
                        packageManager.setSystemAppInstallState(packageName, false, userId);
                    }
                    if (!hasRunOnce && associatedAppList != null) {
                        for (ApplicationInfo associatedApp3 : associatedAppList) {
                            if (associatedApp3.enabledSetting == 0 && (associatedApp3.flags & 8388608) != 0) {
                                Slog.m54i(TAG, "Update associated state(" + associatedApp3.packageName + "): DISABLED_UNTIL_USED for user " + userId);
                                z = false;
                                packageManager.setSystemAppInstallState(associatedApp3.packageName, false, userId);
                            }
                        }
                    }
                }
                z2 = z;
                it = it2;
                z3 = true;
            }
            if (!hasRunOnce) {
                Settings.Secure.putIntForUser(contentResolver, Settings.Secure.CARRIER_APPS_HANDLED, 1, userId);
            }
            if (!enabledCarrierPackages.isEmpty()) {
                String[] packageNames = new String[enabledCarrierPackages.size()];
                enabledCarrierPackages.toArray(packageNames);
                packageManager.grantDefaultPermissionsToEnabledCarrierApps(packageNames, userId);
            }
        } catch (RemoteException e) {
            Slog.m49w(TAG, "Could not reach PackageManager", e);
        }
    }

    public static List<ApplicationInfo> getDefaultCarrierApps(IPackageManager packageManager, TelephonyManager telephonyManager, int userId) {
        List<ApplicationInfo> candidates = getDefaultCarrierAppCandidates(packageManager, userId);
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        for (int i = candidates.size() - 1; i >= 0; i--) {
            ApplicationInfo ai = candidates.get(i);
            String packageName = ai.packageName;
            boolean hasPrivileges = telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(packageName) == 1;
            if (!hasPrivileges) {
                candidates.remove(i);
            }
        }
        return candidates;
    }

    public static List<ApplicationInfo> getDefaultCarrierAppCandidates(IPackageManager packageManager, int userId) {
        ArraySet<String> systemCarrierAppsDisabledUntilUsed = SystemConfig.getInstance().getDisabledUntilUsedPreinstalledCarrierApps();
        return getDefaultCarrierAppCandidatesHelper(packageManager, userId, systemCarrierAppsDisabledUntilUsed);
    }

    private static List<ApplicationInfo> getDefaultCarrierAppCandidatesHelper(IPackageManager packageManager, int userId, ArraySet<String> systemCarrierAppsDisabledUntilUsed) {
        int size;
        if (systemCarrierAppsDisabledUntilUsed == null || (size = systemCarrierAppsDisabledUntilUsed.size()) == 0) {
            return null;
        }
        List<ApplicationInfo> apps = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String packageName = systemCarrierAppsDisabledUntilUsed.valueAt(i);
            ApplicationInfo ai = getApplicationInfoIfSystemApp(packageManager, userId, packageName);
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
            if (ai != null) {
                if (ai.isSystemApp()) {
                    return ai;
                }
                return null;
            }
            return null;
        } catch (RemoteException e) {
            Slog.m49w(TAG, "Could not reach PackageManager", e);
            return null;
        }
    }
}
