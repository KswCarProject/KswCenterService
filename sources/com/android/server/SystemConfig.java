package com.android.server;

import android.content.ComponentName;
import android.content.pm.FeatureInfo;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemProperties;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SystemConfig {
    private static final int ALLOW_ALL = -1;
    private static final int ALLOW_APP_CONFIGS = 8;
    private static final int ALLOW_ASSOCIATIONS = 128;
    private static final int ALLOW_FEATURES = 1;
    private static final int ALLOW_HIDDENAPI_WHITELISTING = 64;
    private static final int ALLOW_LIBS = 2;
    private static final int ALLOW_OEM_PERMISSIONS = 32;
    private static final int ALLOW_PERMISSIONS = 4;
    private static final int ALLOW_PRIVAPP_PERMISSIONS = 16;
    private static final String SKU_PROPERTY = "ro.boot.product.hardware.sku";
    static final String TAG = "SystemConfig";
    static SystemConfig sInstance;
    final ArraySet<String> mAllowIgnoreLocationSettings = new ArraySet<>();
    final ArraySet<String> mAllowImplicitBroadcasts = new ArraySet<>();
    final ArraySet<String> mAllowInDataUsageSave = new ArraySet<>();
    final ArraySet<String> mAllowInPowerSave = new ArraySet<>();
    final ArraySet<String> mAllowInPowerSaveExceptIdle = new ArraySet<>();
    final ArraySet<String> mAllowUnthrottledLocation = new ArraySet<>();
    final ArrayMap<String, ArraySet<String>> mAllowedAssociations = new ArrayMap<>();
    final ArrayMap<String, FeatureInfo> mAvailableFeatures = new ArrayMap<>();
    final ArraySet<ComponentName> mBackupTransportWhitelist = new ArraySet<>();
    private final ArraySet<String> mBugreportWhitelistedPackages = new ArraySet<>();
    final ArraySet<ComponentName> mDefaultVrComponents = new ArraySet<>();
    final ArraySet<String> mDisabledUntilUsedPreinstalledCarrierApps = new ArraySet<>();
    final ArrayMap<String, List<String>> mDisabledUntilUsedPreinstalledCarrierAssociatedApps = new ArrayMap<>();
    int[] mGlobalGids;
    final ArraySet<String> mHiddenApiPackageWhitelist = new ArraySet<>();
    final ArraySet<String> mLinkedApps = new ArraySet<>();
    final ArrayMap<String, ArrayMap<String, Boolean>> mOemPermissions = new ArrayMap<>();
    final ArrayMap<String, PermissionEntry> mPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductServicesPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductServicesPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, SharedLibraryEntry> mSharedLibraries = new ArrayMap<>();
    final ArrayList<PermissionManager.SplitPermissionInfo> mSplitPermissions = new ArrayList<>();
    final SparseArray<ArraySet<String>> mSystemPermissions = new SparseArray<>();
    final ArraySet<String> mSystemUserBlacklistedApps = new ArraySet<>();
    final ArraySet<String> mSystemUserWhitelistedApps = new ArraySet<>();
    final ArraySet<String> mUnavailableFeatures = new ArraySet<>();
    final ArrayMap<String, ArraySet<String>> mVendorPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mVendorPrivAppPermissions = new ArrayMap<>();

    public static final class SharedLibraryEntry {
        public final String[] dependencies;
        public final String filename;
        public final String name;

        SharedLibraryEntry(String name2, String filename2, String[] dependencies2) {
            this.name = name2;
            this.filename = filename2;
            this.dependencies = dependencies2;
        }
    }

    public static final class PermissionEntry {
        public int[] gids;
        public final String name;
        public boolean perUser;

        PermissionEntry(String name2, boolean perUser2) {
            this.name = name2;
            this.perUser = perUser2;
        }
    }

    public static SystemConfig getInstance() {
        SystemConfig systemConfig;
        synchronized (SystemConfig.class) {
            if (sInstance == null) {
                sInstance = new SystemConfig();
            }
            systemConfig = sInstance;
        }
        return systemConfig;
    }

    public int[] getGlobalGids() {
        return this.mGlobalGids;
    }

    public SparseArray<ArraySet<String>> getSystemPermissions() {
        return this.mSystemPermissions;
    }

    public ArrayList<PermissionManager.SplitPermissionInfo> getSplitPermissions() {
        return this.mSplitPermissions;
    }

    public ArrayMap<String, SharedLibraryEntry> getSharedLibraries() {
        return this.mSharedLibraries;
    }

    public ArrayMap<String, FeatureInfo> getAvailableFeatures() {
        return this.mAvailableFeatures;
    }

    public ArrayMap<String, PermissionEntry> getPermissions() {
        return this.mPermissions;
    }

    public ArraySet<String> getAllowImplicitBroadcasts() {
        return this.mAllowImplicitBroadcasts;
    }

    public ArraySet<String> getAllowInPowerSaveExceptIdle() {
        return this.mAllowInPowerSaveExceptIdle;
    }

    public ArraySet<String> getAllowInPowerSave() {
        return this.mAllowInPowerSave;
    }

    public ArraySet<String> getAllowInDataUsageSave() {
        return this.mAllowInDataUsageSave;
    }

    public ArraySet<String> getAllowUnthrottledLocation() {
        return this.mAllowUnthrottledLocation;
    }

    public ArraySet<String> getAllowIgnoreLocationSettings() {
        return this.mAllowIgnoreLocationSettings;
    }

    public ArraySet<String> getLinkedApps() {
        return this.mLinkedApps;
    }

    public ArraySet<String> getSystemUserWhitelistedApps() {
        return this.mSystemUserWhitelistedApps;
    }

    public ArraySet<String> getSystemUserBlacklistedApps() {
        return this.mSystemUserBlacklistedApps;
    }

    public ArraySet<String> getHiddenApiWhitelistedApps() {
        return this.mHiddenApiPackageWhitelist;
    }

    public ArraySet<ComponentName> getDefaultVrComponents() {
        return this.mDefaultVrComponents;
    }

    public ArraySet<ComponentName> getBackupTransportWhitelist() {
        return this.mBackupTransportWhitelist;
    }

    public ArraySet<String> getDisabledUntilUsedPreinstalledCarrierApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierApps;
    }

    public ArrayMap<String, List<String>> getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps;
    }

    public ArraySet<String> getPrivAppPermissions(String packageName) {
        return this.mPrivAppPermissions.get(packageName);
    }

    public ArraySet<String> getPrivAppDenyPermissions(String packageName) {
        return this.mPrivAppDenyPermissions.get(packageName);
    }

    public ArraySet<String> getVendorPrivAppPermissions(String packageName) {
        return this.mVendorPrivAppPermissions.get(packageName);
    }

    public ArraySet<String> getVendorPrivAppDenyPermissions(String packageName) {
        return this.mVendorPrivAppDenyPermissions.get(packageName);
    }

    public ArraySet<String> getProductPrivAppPermissions(String packageName) {
        return this.mProductPrivAppPermissions.get(packageName);
    }

    public ArraySet<String> getProductPrivAppDenyPermissions(String packageName) {
        return this.mProductPrivAppDenyPermissions.get(packageName);
    }

    public ArraySet<String> getProductServicesPrivAppPermissions(String packageName) {
        return this.mProductServicesPrivAppPermissions.get(packageName);
    }

    public ArraySet<String> getProductServicesPrivAppDenyPermissions(String packageName) {
        return this.mProductServicesPrivAppDenyPermissions.get(packageName);
    }

    public Map<String, Boolean> getOemPermissions(String packageName) {
        Map<String, Boolean> oemPermissions = this.mOemPermissions.get(packageName);
        if (oemPermissions != null) {
            return oemPermissions;
        }
        return Collections.emptyMap();
    }

    public ArrayMap<String, ArraySet<String>> getAllowedAssociations() {
        return this.mAllowedAssociations;
    }

    public ArraySet<String> getBugreportWhitelistedPackages() {
        return this.mBugreportWhitelistedPackages;
    }

    SystemConfig() {
        readPermissions(Environment.buildPath(Environment.getRootDirectory(), "etc", "sysconfig"), -1);
        readPermissions(Environment.buildPath(Environment.getRootDirectory(), "etc", "permissions"), -1);
        int vendorPermissionFlag = Build.VERSION.FIRST_SDK_INT <= 27 ? 147 | 12 : 147;
        readPermissions(Environment.buildPath(Environment.getVendorDirectory(), "etc", "sysconfig"), vendorPermissionFlag);
        readPermissions(Environment.buildPath(Environment.getVendorDirectory(), "etc", "permissions"), vendorPermissionFlag);
        int odmPermissionFlag = vendorPermissionFlag;
        readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "sysconfig"), odmPermissionFlag);
        readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "permissions"), odmPermissionFlag);
        String skuProperty = SystemProperties.get(SKU_PROPERTY, "");
        if (!skuProperty.isEmpty()) {
            String skuDir = "sku_" + skuProperty;
            readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "sysconfig", skuDir), odmPermissionFlag);
            readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "permissions", skuDir), odmPermissionFlag);
        }
        readPermissions(Environment.buildPath(Environment.getOemDirectory(), "etc", "sysconfig"), 161);
        readPermissions(Environment.buildPath(Environment.getOemDirectory(), "etc", "permissions"), 161);
        readPermissions(Environment.buildPath(Environment.getProductDirectory(), "etc", "sysconfig"), -1);
        readPermissions(Environment.buildPath(Environment.getProductDirectory(), "etc", "permissions"), -1);
        readPermissions(Environment.buildPath(Environment.getProductServicesDirectory(), "etc", "sysconfig"), -1);
        readPermissions(Environment.buildPath(Environment.getProductServicesDirectory(), "etc", "permissions"), -1);
    }

    /* access modifiers changed from: package-private */
    public void readPermissions(File libraryDir, int permissionFlag) {
        if (!libraryDir.exists() || !libraryDir.isDirectory()) {
            if (permissionFlag == -1) {
                Slog.w(TAG, "No directory " + libraryDir + ", skipping");
            }
        } else if (!libraryDir.canRead()) {
            Slog.w(TAG, "Directory " + libraryDir + " cannot be read");
        } else {
            File platformFile = null;
            for (File f : libraryDir.listFiles()) {
                if (f.isFile()) {
                    if (f.getPath().endsWith("etc/permissions/platform.xml")) {
                        platformFile = f;
                    } else if (!f.getPath().endsWith(".xml")) {
                        Slog.i(TAG, "Non-xml file " + f + " in " + libraryDir + " directory, ignoring");
                    } else if (!f.canRead()) {
                        Slog.w(TAG, "Permissions library file " + f + " cannot be read");
                    } else {
                        readPermissionsFromXml(f, permissionFlag);
                    }
                }
            }
            if (platformFile != null) {
                readPermissionsFromXml(platformFile, permissionFlag);
            }
        }
    }

    private void logNotAllowedInPartition(String name, File permFile, XmlPullParser parser) {
        Slog.w(TAG, "<" + name + "> not allowed in partition of " + permFile + " at " + parser.getPositionDescription());
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0240, code lost:
        r22 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0242, code lost:
        r23 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:360:0x09b7, code lost:
        r4 = null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0028 A[Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d, all -> 0x0b7a }] */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x03fa A[Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d, all -> 0x0b7a }] */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x0402 A[Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d, all -> 0x0b7a }] */
    /* JADX WARNING: Removed duplicated region for block: B:397:0x0b70 A[Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d, all -> 0x0b7a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readPermissionsFromXml(java.io.File r25, int r26) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            r3 = r26
            r4 = 0
            r5 = r4
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0bde }
            r6.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0bde }
            r5 = r6
            boolean r6 = android.app.ActivityManager.isLowRamDeviceStatic()
            org.xmlpull.v1.XmlPullParser r8 = android.util.Xml.newPullParser()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r8.setInput(r5)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x001a:
            int r9 = r8.next()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10 = r9
            r11 = 2
            r12 = 1
            if (r9 == r11) goto L_0x0026
            if (r10 == r12) goto L_0x0026
            goto L_0x001a
        L_0x0026:
            if (r10 != r11) goto L_0x0b70
            java.lang.String r9 = r8.getName()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r13 = "permissions"
            boolean r9 = r9.equals(r13)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r9 != 0) goto L_0x006a
            java.lang.String r9 = r8.getName()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r13 = "config"
            boolean r9 = r9.equals(r13)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r9 == 0) goto L_0x0042
            goto L_0x006a
        L_0x0042:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r11 = "Unexpected start tag in "
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r11 = ": found "
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r11 = r8.getName()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r11 = ", expected 'permissions' or 'config'"
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.<init>(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            throw r4     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x006a:
            r9 = -1
            if (r3 != r9) goto L_0x006f
            r13 = r12
            goto L_0x0070
        L_0x006f:
            r13 = 0
        L_0x0070:
            r14 = r3 & 2
            if (r14 == 0) goto L_0x0076
            r14 = r12
            goto L_0x0077
        L_0x0076:
            r14 = 0
        L_0x0077:
            r15 = r3 & 1
            if (r15 == 0) goto L_0x007d
            r15 = r12
            goto L_0x007e
        L_0x007d:
            r15 = 0
        L_0x007e:
            r16 = r3 & 4
            if (r16 == 0) goto L_0x0085
            r16 = r12
            goto L_0x0087
        L_0x0085:
            r16 = 0
        L_0x0087:
            r17 = r3 & 8
            if (r17 == 0) goto L_0x008e
            r17 = r12
            goto L_0x0090
        L_0x008e:
            r17 = 0
        L_0x0090:
            r18 = r3 & 16
            if (r18 == 0) goto L_0x0097
            r18 = r12
            goto L_0x0099
        L_0x0097:
            r18 = 0
        L_0x0099:
            r19 = r3 & 32
            if (r19 == 0) goto L_0x00a0
            r19 = r12
            goto L_0x00a2
        L_0x00a0:
            r19 = 0
        L_0x00a2:
            r20 = r3 & 64
            if (r20 == 0) goto L_0x00a9
            r20 = r12
            goto L_0x00ab
        L_0x00a9:
            r20 = 0
        L_0x00ab:
            r9 = r3 & 128(0x80, float:1.794E-43)
            if (r9 == 0) goto L_0x00b1
            r9 = r12
            goto L_0x00b2
        L_0x00b1:
            r9 = 0
        L_0x00b2:
            com.android.internal.util.XmlUtils.nextElement(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            int r11 = r8.getEventType()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r11 != r12) goto L_0x00bd
            goto L_0x0b90
        L_0x00bd:
            java.lang.String r11 = r8.getName()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r11 != 0) goto L_0x00cd
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r22 = r9
            r23 = r10
            goto L_0x0b66
        L_0x00cd:
            int r21 = r11.hashCode()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            switch(r21) {
                case -2040330235: goto L_0x01ec;
                case -1882490007: goto L_0x01e1;
                case -1005864890: goto L_0x01d6;
                case -980620291: goto L_0x01cb;
                case -979207434: goto L_0x01c1;
                case -851582420: goto L_0x01b5;
                case -828905863: goto L_0x01aa;
                case -642819164: goto L_0x01a0;
                case -560717308: goto L_0x0195;
                case -517618225: goto L_0x018a;
                case 98629247: goto L_0x017f;
                case 166208699: goto L_0x0174;
                case 180165796: goto L_0x0168;
                case 347247519: goto L_0x015c;
                case 508457430: goto L_0x014f;
                case 802332808: goto L_0x0143;
                case 953292141: goto L_0x0138;
                case 1044015374: goto L_0x012b;
                case 1121420326: goto L_0x011f;
                case 1269564002: goto L_0x0113;
                case 1567330472: goto L_0x0107;
                case 1633270165: goto L_0x00fb;
                case 1723146313: goto L_0x00ee;
                case 1723586945: goto L_0x00e2;
                case 1954925533: goto L_0x00d6;
                default: goto L_0x00d4;
            }     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x00d4:
            goto L_0x01f7
        L_0x00d6:
            java.lang.String r12 = "allow-implicit-broadcast"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 12
            goto L_0x01f8
        L_0x00e2:
            java.lang.String r12 = "bugreport-whitelisted"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 24
            goto L_0x01f8
        L_0x00ee:
            java.lang.String r12 = "privapp-permissions"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 20
            goto L_0x01f8
        L_0x00fb:
            java.lang.String r12 = "disabled-until-used-preinstalled-carrier-associated-app"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 18
            goto L_0x01f8
        L_0x0107:
            java.lang.String r12 = "default-enabled-vr-app"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 16
            goto L_0x01f8
        L_0x0113:
            java.lang.String r12 = "split-permission"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 3
            goto L_0x01f8
        L_0x011f:
            java.lang.String r12 = "app-link"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 13
            goto L_0x01f8
        L_0x012b:
            java.lang.String r12 = "oem-permissions"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 21
            goto L_0x01f8
        L_0x0138:
            java.lang.String r12 = "assign-permission"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 2
            goto L_0x01f8
        L_0x0143:
            java.lang.String r12 = "allow-in-data-usage-save"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 9
            goto L_0x01f8
        L_0x014f:
            java.lang.String r12 = "system-user-whitelisted-app"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 14
            goto L_0x01f8
        L_0x015c:
            java.lang.String r12 = "backup-transport-whitelisted-service"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 17
            goto L_0x01f8
        L_0x0168:
            java.lang.String r12 = "hidden-api-whitelisted-app"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 22
            goto L_0x01f8
        L_0x0174:
            java.lang.String r12 = "library"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 4
            goto L_0x01f8
        L_0x017f:
            java.lang.String r12 = "group"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 0
            goto L_0x01f8
        L_0x018a:
            java.lang.String r12 = "permission"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 1
            goto L_0x01f8
        L_0x0195:
            java.lang.String r12 = "allow-ignore-location-settings"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 11
            goto L_0x01f8
        L_0x01a0:
            java.lang.String r12 = "allow-in-power-save-except-idle"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 7
            goto L_0x01f8
        L_0x01aa:
            java.lang.String r12 = "unavailable-feature"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 6
            goto L_0x01f8
        L_0x01b5:
            java.lang.String r12 = "system-user-blacklisted-app"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 15
            goto L_0x01f8
        L_0x01c1:
            java.lang.String r12 = "feature"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 5
            goto L_0x01f8
        L_0x01cb:
            java.lang.String r12 = "allow-association"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 23
            goto L_0x01f8
        L_0x01d6:
            java.lang.String r12 = "disabled-until-used-preinstalled-carrier-app"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 19
            goto L_0x01f8
        L_0x01e1:
            java.lang.String r12 = "allow-in-power-save"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 8
            goto L_0x01f8
        L_0x01ec:
            java.lang.String r12 = "allow-unthrottled-location"
            boolean r12 = r11.equals(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 == 0) goto L_0x01f7
            r12 = 10
            goto L_0x01f8
        L_0x01f7:
            r12 = -1
        L_0x01f8:
            switch(r12) {
                case 0: goto L_0x0aeb;
                case 1: goto L_0x0a9a;
                case 2: goto L_0x09c1;
                case 3: goto L_0x09ae;
                case 4: goto L_0x091b;
                case 5: goto L_0x08b3;
                case 6: goto L_0x086a;
                case 7: goto L_0x0821;
                case 8: goto L_0x07d8;
                case 9: goto L_0x078f;
                case 10: goto L_0x0746;
                case 11: goto L_0x06fd;
                case 12: goto L_0x06b5;
                case 13: goto L_0x066c;
                case 14: goto L_0x0623;
                case 15: goto L_0x05da;
                case 16: goto L_0x0558;
                case 17: goto L_0x04d6;
                case 18: goto L_0x0470;
                case 19: goto L_0x0427;
                case 20: goto L_0x0366;
                case 21: goto L_0x0353;
                case 22: goto L_0x030a;
                case 23: goto L_0x0246;
                case 24: goto L_0x0203;
                default: goto L_0x01fb;
            }     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x01fb:
            r22 = r9
            r23 = r10
            java.lang.String r3 = "SystemConfig"
            goto L_0x0b3a
        L_0x0203:
            java.lang.String r12 = "package"
            java.lang.String r12 = r8.getAttributeValue(r4, r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r12 != 0) goto L_0x0237
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r3 = "<"
            r4.append(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r3 = "> without package in "
            r4.append(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r3 = " at "
            r4.append(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r3 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.append(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r3 = r4.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x023c
        L_0x0237:
            android.util.ArraySet<java.lang.String> r3 = r1.mBugreportWhitelistedPackages     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r3.add(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x023c:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0240:
            r22 = r9
        L_0x0242:
            r23 = r10
            goto L_0x09b7
        L_0x0246:
            if (r9 == 0) goto L_0x02fe
            java.lang.String r3 = "target"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0280
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "<"
            r7.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "> without target in "
            r7.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = " at "
            r7.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0240
        L_0x0280:
            java.lang.String r4 = "allowed"
            r7 = 0
            java.lang.String r4 = r8.getAttributeValue(r7, r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r4 != 0) goto L_0x02b9
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r22 = r9
            java.lang.String r9 = "<"
            r12.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without allowed in "
            r12.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r12.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r12.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0242
        L_0x02b9:
            r22 = r9
            java.lang.String r7 = r3.intern()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r3 = r7
            java.lang.String r7 = r4.intern()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4 = r7
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r7 = r1.mAllowedAssociations     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.Object r7 = r7.get(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArraySet r7 = (android.util.ArraySet) r7     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r7 != 0) goto L_0x02da
            android.util.ArraySet r9 = new android.util.ArraySet     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7 = r9
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r9 = r1.mAllowedAssociations     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.put(r3, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x02da:
            java.lang.String r9 = "SystemConfig"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r23 = r10
            java.lang.String r10 = "Adding association: "
            r12.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " <- "
            r12.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r12.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.i(r9, r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.add(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0305
        L_0x02fe:
            r22 = r9
            r23 = r10
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0305:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x030a:
            r22 = r9
            r23 = r10
            if (r20 == 0) goto L_0x034b
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0345
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x034a
        L_0x0345:
            android.util.ArraySet<java.lang.String> r4 = r1.mHiddenApiPackageWhitelist     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x034a:
            goto L_0x034e
        L_0x034b:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x034e:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0353:
            r22 = r9
            r23 = r10
            if (r19 == 0) goto L_0x035e
            r1.readOemPermissions(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x035e:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0366:
            r22 = r9
            r23 = r10
            if (r18 == 0) goto L_0x041f
            java.nio.file.Path r3 = r25.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.io.File r7 = android.os.Environment.getVendorDirectory()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.nio.file.Path r7 = r7.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.append(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = "/"
            r4.append(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            boolean r3 = r3.startsWith(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x03b5
            java.nio.file.Path r3 = r25.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.io.File r7 = android.os.Environment.getOdmDirectory()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.nio.file.Path r7 = r7.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.append(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = "/"
            r4.append(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            boolean r3 = r3.startsWith(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 == 0) goto L_0x03b3
            goto L_0x03b5
        L_0x03b3:
            r3 = 0
            goto L_0x03b6
        L_0x03b5:
            r3 = 1
        L_0x03b6:
            java.nio.file.Path r4 = r25.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.io.File r9 = android.os.Environment.getProductDirectory()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.nio.file.Path r9 = r9.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "/"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            boolean r4 = r4.startsWith(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.nio.file.Path r7 = r25.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.io.File r10 = android.os.Environment.getProductServicesDirectory()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.nio.file.Path r10 = r10.toPath()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "/"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            boolean r7 = r7.startsWith(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 == 0) goto L_0x0402
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r9 = r1.mVendorPrivAppPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r10 = r1.mVendorPrivAppDenyPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r1.readPrivAppPermissions(r8, r9, r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x041d
        L_0x0402:
            if (r4 == 0) goto L_0x040c
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r9 = r1.mProductPrivAppPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r10 = r1.mProductPrivAppDenyPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r1.readPrivAppPermissions(r8, r9, r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x041d
        L_0x040c:
            if (r7 == 0) goto L_0x0416
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r9 = r1.mProductServicesPrivAppPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r10 = r1.mProductServicesPrivAppDenyPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r1.readPrivAppPermissions(r8, r9, r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x041d
        L_0x0416:
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r9 = r1.mPrivAppPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r10 = r1.mPrivAppDenyPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r1.readPrivAppPermissions(r8, r9, r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x041d:
            goto L_0x09b7
        L_0x041f:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0427:
            r22 = r9
            r23 = r10
            if (r17 == 0) goto L_0x0468
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0462
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0467
        L_0x0462:
            android.util.ArraySet<java.lang.String> r4 = r1.mDisabledUntilUsedPreinstalledCarrierApps     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0467:
            goto L_0x046b
        L_0x0468:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x046b:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0470:
            r22 = r9
            r23 = r10
            if (r17 == 0) goto L_0x04ce
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = "carrierAppPackage"
            java.lang.String r7 = r8.getAttributeValue(r4, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4 = r7
            if (r3 == 0) goto L_0x04a3
            if (r4 != 0) goto L_0x048a
            goto L_0x04a3
        L_0x048a:
            android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> r7 = r1.mDisabledUntilUsedPreinstalledCarrierAssociatedApps     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.Object r7 = r7.get(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.util.List r7 = (java.util.List) r7     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r7 != 0) goto L_0x049f
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7 = r9
            android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> r9 = r1.mDisabledUntilUsedPreinstalledCarrierAssociatedApps     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.put(r4, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x049f:
            r7.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x04cd
        L_0x04a3:
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> without package or carrierAppPackage in "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x04cd:
            goto L_0x04d1
        L_0x04ce:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x04d1:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x04d6:
            r22 = r9
            r23 = r10
            if (r15 == 0) goto L_0x0550
            java.lang.String r3 = "service"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0511
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without service in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x054f
        L_0x0511:
            android.content.ComponentName r4 = android.content.ComponentName.unflattenFromString(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r4 != 0) goto L_0x054a
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> with invalid service name "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " in "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x054f
        L_0x054a:
            android.util.ArraySet<android.content.ComponentName> r7 = r1.mBackupTransportWhitelist     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.add(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x054f:
            goto L_0x0553
        L_0x0550:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0553:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0558:
            r22 = r9
            r23 = r10
            if (r17 == 0) goto L_0x05d2
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = "class"
            java.lang.String r7 = r8.getAttributeValue(r4, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4 = r7
            if (r3 != 0) goto L_0x059a
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> without package in "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x05d1
        L_0x059a:
            if (r4 != 0) goto L_0x05c7
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> without class in "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x05d1
        L_0x05c7:
            android.util.ArraySet<android.content.ComponentName> r7 = r1.mDefaultVrComponents     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.content.ComponentName r9 = new android.content.ComponentName     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.add(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x05d1:
            goto L_0x05d5
        L_0x05d2:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x05d5:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x05da:
            r22 = r9
            r23 = r10
            if (r17 == 0) goto L_0x061b
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0615
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x061a
        L_0x0615:
            android.util.ArraySet<java.lang.String> r4 = r1.mSystemUserBlacklistedApps     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x061a:
            goto L_0x061e
        L_0x061b:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x061e:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0623:
            r22 = r9
            r23 = r10
            if (r17 == 0) goto L_0x0664
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x065e
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0663
        L_0x065e:
            android.util.ArraySet<java.lang.String> r4 = r1.mSystemUserWhitelistedApps     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0663:
            goto L_0x0667
        L_0x0664:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0667:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x066c:
            r22 = r9
            r23 = r10
            if (r17 == 0) goto L_0x06ad
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x06a7
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x06ac
        L_0x06a7:
            android.util.ArraySet<java.lang.String> r4 = r1.mLinkedApps     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x06ac:
            goto L_0x06b0
        L_0x06ad:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x06b0:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x06b5:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x06f5
            java.lang.String r3 = "action"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x06ef
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without action in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x06f4
        L_0x06ef:
            android.util.ArraySet<java.lang.String> r4 = r1.mAllowImplicitBroadcasts     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x06f4:
            goto L_0x06f8
        L_0x06f5:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x06f8:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x06fd:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x073e
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0738
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x073d
        L_0x0738:
            android.util.ArraySet<java.lang.String> r4 = r1.mAllowIgnoreLocationSettings     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x073d:
            goto L_0x0741
        L_0x073e:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0741:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0746:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x0787
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0781
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0786
        L_0x0781:
            android.util.ArraySet<java.lang.String> r4 = r1.mAllowUnthrottledLocation     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0786:
            goto L_0x078a
        L_0x0787:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x078a:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x078f:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x07d0
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x07ca
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x07cf
        L_0x07ca:
            android.util.ArraySet<java.lang.String> r4 = r1.mAllowInDataUsageSave     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x07cf:
            goto L_0x07d3
        L_0x07d0:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x07d3:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x07d8:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x0819
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0813
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0818
        L_0x0813:
            android.util.ArraySet<java.lang.String> r4 = r1.mAllowInPowerSave     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0818:
            goto L_0x081c
        L_0x0819:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x081c:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0821:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x0862
            java.lang.String r3 = "package"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x085c
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without package in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0861
        L_0x085c:
            android.util.ArraySet<java.lang.String> r4 = r1.mAllowInPowerSaveExceptIdle     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0861:
            goto L_0x0865
        L_0x0862:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0865:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x086a:
            r22 = r9
            r23 = r10
            if (r15 == 0) goto L_0x08ab
            java.lang.String r3 = "name"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x08a5
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without name in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x08aa
        L_0x08a5:
            android.util.ArraySet<java.lang.String> r4 = r1.mUnavailableFeatures     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x08aa:
            goto L_0x08ae
        L_0x08ab:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x08ae:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x08b3:
            r22 = r9
            r23 = r10
            if (r15 == 0) goto L_0x0913
            java.lang.String r3 = "name"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r4 = "version"
            r7 = 0
            int r4 = com.android.internal.util.XmlUtils.readIntAttribute(r8, r4, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r6 != 0) goto L_0x08ce
            r7 = 1
            r10 = 1
            goto L_0x08e0
        L_0x08ce:
            java.lang.String r7 = "notLowRam"
            r9 = 0
            java.lang.String r7 = r8.getAttributeValue(r9, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "true"
            boolean r9 = r9.equals(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10 = 1
            r9 = r9 ^ r10
            r7 = r9
        L_0x08e0:
            if (r3 != 0) goto L_0x090d
            java.lang.String r9 = "SystemConfig"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r12.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> without name in "
            r12.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r12.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r12.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r12.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r9, (java.lang.String) r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0912
        L_0x090d:
            if (r7 == 0) goto L_0x0912
            r1.addFeature(r3, r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0912:
            goto L_0x0916
        L_0x0913:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0916:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x091b:
            r22 = r9
            r23 = r10
            if (r14 == 0) goto L_0x09a7
            java.lang.String r3 = "name"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = "file"
            java.lang.String r7 = r8.getAttributeValue(r4, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "dependency"
            java.lang.String r9 = r8.getAttributeValue(r4, r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r4 = r9
            if (r3 != 0) goto L_0x0963
            java.lang.String r9 = "SystemConfig"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "<"
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "> without name in "
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = " at "
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r9, (java.lang.String) r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09a6
        L_0x0963:
            if (r7 != 0) goto L_0x0990
            java.lang.String r9 = "SystemConfig"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "<"
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "> without file in "
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = " at "
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r9, (java.lang.String) r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09a6
        L_0x0990:
            com.android.server.SystemConfig$SharedLibraryEntry r9 = new com.android.server.SystemConfig$SharedLibraryEntry     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r4 != 0) goto L_0x0998
            r10 = 0
            java.lang.String[] r12 = new java.lang.String[r10]     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x099e
        L_0x0998:
            java.lang.String r10 = ":"
            java.lang.String[] r12 = r4.split(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x099e:
            r9.<init>(r3, r7, r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig$SharedLibraryEntry> r10 = r1.mSharedLibraries     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.put(r3, r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x09a6:
            goto L_0x09aa
        L_0x09a7:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x09aa:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x09ae:
            r22 = r9
            r23 = r10
            if (r16 == 0) goto L_0x09ba
            r1.readSplitPermission(r8, r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x09b7:
            r4 = 0
            goto L_0x0b65
        L_0x09ba:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x09c1:
            r22 = r9
            r23 = r10
            if (r16 == 0) goto L_0x0a92
            java.lang.String r3 = "name"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x09ff
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without name in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x09ff:
            java.lang.String r4 = "uid"
            r7 = 0
            java.lang.String r4 = r8.getAttributeValue(r7, r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r4 != 0) goto L_0x0a37
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> without uid in "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0a37:
            int r7 = android.os.Process.getUidForName(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r7 >= 0) goto L_0x0a74
            java.lang.String r9 = "SystemConfig"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "<"
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "> with unknown uid \""
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = "  in "
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = " at "
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r12 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.append(r12)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r9, (java.lang.String) r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0a74:
            java.lang.String r9 = r3.intern()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r3 = r9
            android.util.SparseArray<android.util.ArraySet<java.lang.String>> r9 = r1.mSystemPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.Object r9 = r9.get(r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.ArraySet r9 = (android.util.ArraySet) r9     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r9 != 0) goto L_0x0a8e
            android.util.ArraySet r10 = new android.util.ArraySet     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9 = r10
            android.util.SparseArray<android.util.ArraySet<java.lang.String>> r10 = r1.mSystemPermissions     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r10.put(r7, r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0a8e:
            r9.add(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0a95
        L_0x0a92:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0a95:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0a9a:
            r22 = r9
            r23 = r10
            if (r16 == 0) goto L_0x0ae3
            java.lang.String r3 = "name"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 != 0) goto L_0x0ad9
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "<"
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "> without name in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0ad9:
            java.lang.String r4 = r3.intern()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r3 = r4
            r1.readPermission(r8, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0ae3:
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x09b7
        L_0x0aeb:
            r22 = r9
            r23 = r10
            if (r13 == 0) goto L_0x0b32
            java.lang.String r3 = "gid"
            r4 = 0
            java.lang.String r3 = r8.getAttributeValue(r4, r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            if (r3 == 0) goto L_0x0b07
            int r7 = android.os.Process.getGidForName(r3)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            int[] r9 = r1.mGlobalGids     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            int[] r9 = com.android.internal.util.ArrayUtils.appendInt(r9, r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r1.mGlobalGids = r9     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0b31
        L_0x0b07:
            java.lang.String r7 = "SystemConfig"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "<"
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = "> without gid in "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = " at "
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r10 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0b31:
            goto L_0x0b36
        L_0x0b32:
            r4 = 0
            r1.logNotAllowedInPartition(r11, r2, r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0b36:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            goto L_0x0b65
        L_0x0b3a:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = "Tag "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r11)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " is unknown in "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r2)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = " at "
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r9 = r8.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            r7.append(r9)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            android.util.Slog.w((java.lang.String) r3, (java.lang.String) r7)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0b65:
        L_0x0b66:
            r9 = r22
            r10 = r23
            r3 = r26
            r11 = 2
            r12 = 1
            goto L_0x00b2
        L_0x0b70:
            r23 = r10
            org.xmlpull.v1.XmlPullParserException r3 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            java.lang.String r4 = "No start tag found"
            r3.<init>(r4)     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
            throw r3     // Catch:{ XmlPullParserException -> 0x0b87, IOException -> 0x0b7d }
        L_0x0b7a:
            r0 = move-exception
            r3 = r0
            goto L_0x0bda
        L_0x0b7d:
            r0 = move-exception
            r3 = r0
            java.lang.String r4 = "SystemConfig"
            java.lang.String r7 = "Got exception parsing permissions."
            android.util.Slog.w(r4, r7, r3)     // Catch:{ all -> 0x0b7a }
            goto L_0x0b90
        L_0x0b87:
            r0 = move-exception
            r3 = r0
            java.lang.String r4 = "SystemConfig"
            java.lang.String r7 = "Got exception parsing permissions."
            android.util.Slog.w(r4, r7, r3)     // Catch:{ all -> 0x0b7a }
        L_0x0b90:
            libcore.io.IoUtils.closeQuietly(r5)
            boolean r3 = android.os.storage.StorageManager.isFileEncryptedNativeOnly()
            if (r3 == 0) goto L_0x0ba6
            java.lang.String r3 = "android.software.file_based_encryption"
            r4 = 0
            r1.addFeature(r3, r4)
            java.lang.String r3 = "android.software.securely_removes_users"
            r1.addFeature(r3, r4)
            goto L_0x0ba7
        L_0x0ba6:
            r4 = 0
        L_0x0ba7:
            boolean r3 = android.os.storage.StorageManager.hasAdoptable()
            if (r3 == 0) goto L_0x0bb2
            java.lang.String r3 = "android.software.adoptable_storage"
            r1.addFeature(r3, r4)
        L_0x0bb2:
            boolean r3 = android.app.ActivityManager.isLowRamDeviceStatic()
            if (r3 == 0) goto L_0x0bbe
            java.lang.String r3 = "android.hardware.ram.low"
            r1.addFeature(r3, r4)
            goto L_0x0bc3
        L_0x0bbe:
            java.lang.String r3 = "android.hardware.ram.normal"
            r1.addFeature(r3, r4)
        L_0x0bc3:
            android.util.ArraySet<java.lang.String> r3 = r1.mUnavailableFeatures
            java.util.Iterator r3 = r3.iterator()
        L_0x0bc9:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0bd9
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            r1.removeFeature(r4)
            goto L_0x0bc9
        L_0x0bd9:
            return
        L_0x0bda:
            libcore.io.IoUtils.closeQuietly(r5)
            throw r3
        L_0x0bde:
            r0 = move-exception
            r3 = r0
            java.lang.String r4 = "SystemConfig"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Couldn't find or open permissions file "
            r6.append(r7)
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SystemConfig.readPermissionsFromXml(java.io.File, int):void");
    }

    private void addFeature(String name, int version) {
        FeatureInfo fi = this.mAvailableFeatures.get(name);
        if (fi == null) {
            FeatureInfo fi2 = new FeatureInfo();
            fi2.name = name;
            fi2.version = version;
            this.mAvailableFeatures.put(name, fi2);
            return;
        }
        fi.version = Math.max(fi.version, version);
    }

    private void removeFeature(String name) {
        if (this.mAvailableFeatures.remove(name) != null) {
            Slog.d(TAG, "Removed unavailable feature " + name);
        }
    }

    /* access modifiers changed from: package-private */
    public void readPermission(XmlPullParser parser, String name) throws IOException, XmlPullParserException {
        if (!this.mPermissions.containsKey(name)) {
            PermissionEntry perm = new PermissionEntry(name, XmlUtils.readBooleanAttribute(parser, "perUser", false));
            this.mPermissions.put(name, perm);
            int outerDepth = parser.getDepth();
            while (true) {
                int next = parser.next();
                int type = next;
                if (next == 1) {
                    return;
                }
                if (type == 3 && parser.getDepth() <= outerDepth) {
                    return;
                }
                if (!(type == 3 || type == 4)) {
                    if (WifiConfiguration.GroupCipher.varName.equals(parser.getName())) {
                        String gidStr = parser.getAttributeValue((String) null, "gid");
                        if (gidStr != null) {
                            perm.gids = ArrayUtils.appendInt(perm.gids, Process.getGidForName(gidStr));
                        } else {
                            Slog.w(TAG, "<group> without gid at " + parser.getPositionDescription());
                        }
                    }
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        } else {
            throw new IllegalStateException("Duplicate permission definition for " + name);
        }
    }

    private void readPrivAppPermissions(XmlPullParser parser, ArrayMap<String, ArraySet<String>> grantMap, ArrayMap<String, ArraySet<String>> denyMap) throws IOException, XmlPullParserException {
        String packageName = parser.getAttributeValue((String) null, "package");
        if (TextUtils.isEmpty(packageName)) {
            Slog.w(TAG, "package is required for <privapp-permissions> in " + parser.getPositionDescription());
            return;
        }
        ArraySet<String> permissions = grantMap.get(packageName);
        if (permissions == null) {
            permissions = new ArraySet<>();
        }
        ArraySet<String> denyPermissions = denyMap.get(packageName);
        int depth = parser.getDepth();
        while (XmlUtils.nextElementWithin(parser, depth)) {
            String name = parser.getName();
            if ("permission".equals(name)) {
                String permName = parser.getAttributeValue((String) null, "name");
                if (TextUtils.isEmpty(permName)) {
                    Slog.w(TAG, "name is required for <permission> in " + parser.getPositionDescription());
                } else {
                    permissions.add(permName);
                }
            } else if ("deny-permission".equals(name)) {
                String permName2 = parser.getAttributeValue((String) null, "name");
                if (TextUtils.isEmpty(permName2)) {
                    Slog.w(TAG, "name is required for <deny-permission> in " + parser.getPositionDescription());
                } else {
                    if (denyPermissions == null) {
                        denyPermissions = new ArraySet<>();
                    }
                    denyPermissions.add(permName2);
                }
            }
        }
        grantMap.put(packageName, permissions);
        if (denyPermissions != null) {
            denyMap.put(packageName, denyPermissions);
        }
    }

    /* access modifiers changed from: package-private */
    public void readOemPermissions(XmlPullParser parser) throws IOException, XmlPullParserException {
        String packageName = parser.getAttributeValue((String) null, "package");
        if (TextUtils.isEmpty(packageName)) {
            Slog.w(TAG, "package is required for <oem-permissions> in " + parser.getPositionDescription());
            return;
        }
        ArrayMap<String, Boolean> permissions = this.mOemPermissions.get(packageName);
        if (permissions == null) {
            permissions = new ArrayMap<>();
        }
        int depth = parser.getDepth();
        while (XmlUtils.nextElementWithin(parser, depth)) {
            String name = parser.getName();
            if ("permission".equals(name)) {
                String permName = parser.getAttributeValue((String) null, "name");
                if (TextUtils.isEmpty(permName)) {
                    Slog.w(TAG, "name is required for <permission> in " + parser.getPositionDescription());
                } else {
                    permissions.put(permName, Boolean.TRUE);
                }
            } else if ("deny-permission".equals(name)) {
                String permName2 = parser.getAttributeValue((String) null, "name");
                if (TextUtils.isEmpty(permName2)) {
                    Slog.w(TAG, "name is required for <deny-permission> in " + parser.getPositionDescription());
                } else {
                    permissions.put(permName2, Boolean.FALSE);
                }
            }
        }
        this.mOemPermissions.put(packageName, permissions);
    }

    private void readSplitPermission(XmlPullParser parser, File permFile) throws IOException, XmlPullParserException {
        String splitPerm = parser.getAttributeValue((String) null, "name");
        if (splitPerm == null) {
            Slog.w(TAG, "<split-permission> without name in " + permFile + " at " + parser.getPositionDescription());
            XmlUtils.skipCurrentTag(parser);
            return;
        }
        String targetSdkStr = parser.getAttributeValue((String) null, "targetSdk");
        int targetSdk = 10001;
        if (!TextUtils.isEmpty(targetSdkStr)) {
            try {
                targetSdk = Integer.parseInt(targetSdkStr);
            } catch (NumberFormatException e) {
                Slog.w(TAG, "<split-permission> targetSdk not an integer in " + permFile + " at " + parser.getPositionDescription());
                XmlUtils.skipCurrentTag(parser);
                return;
            }
        }
        int depth = parser.getDepth();
        List<String> newPermissions = new ArrayList<>();
        while (XmlUtils.nextElementWithin(parser, depth)) {
            if ("new-permission".equals(parser.getName())) {
                String newName = parser.getAttributeValue((String) null, "name");
                if (TextUtils.isEmpty(newName)) {
                    Slog.w(TAG, "name is required for <new-permission> in " + parser.getPositionDescription());
                } else {
                    newPermissions.add(newName);
                }
            } else {
                XmlUtils.skipCurrentTag(parser);
            }
        }
        if (!newPermissions.isEmpty()) {
            this.mSplitPermissions.add(new PermissionManager.SplitPermissionInfo(splitPerm, newPermissions, targetSdk));
        }
    }
}
