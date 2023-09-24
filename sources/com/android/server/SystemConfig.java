package com.android.server;

import android.accounts.GrantCredentialsPermissionActivity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.p002pm.FeatureInfo;
import android.content.p002pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.p007os.Build;
import android.p007os.Environment;
import android.p007os.Process;
import android.p007os.SystemProperties;
import android.p007os.storage.StorageManager;
import android.permission.PermissionManager;
import android.provider.SettingsStringUtil;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
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
    int[] mGlobalGids;
    final SparseArray<ArraySet<String>> mSystemPermissions = new SparseArray<>();
    final ArrayList<PermissionManager.SplitPermissionInfo> mSplitPermissions = new ArrayList<>();
    final ArrayMap<String, SharedLibraryEntry> mSharedLibraries = new ArrayMap<>();
    final ArrayMap<String, FeatureInfo> mAvailableFeatures = new ArrayMap<>();
    final ArraySet<String> mUnavailableFeatures = new ArraySet<>();
    final ArrayMap<String, PermissionEntry> mPermissions = new ArrayMap<>();
    final ArraySet<String> mAllowInPowerSaveExceptIdle = new ArraySet<>();
    final ArraySet<String> mAllowInPowerSave = new ArraySet<>();
    final ArraySet<String> mAllowInDataUsageSave = new ArraySet<>();
    final ArraySet<String> mAllowUnthrottledLocation = new ArraySet<>();
    final ArraySet<String> mAllowIgnoreLocationSettings = new ArraySet<>();
    final ArraySet<String> mAllowImplicitBroadcasts = new ArraySet<>();
    final ArraySet<String> mLinkedApps = new ArraySet<>();
    final ArraySet<String> mSystemUserWhitelistedApps = new ArraySet<>();
    final ArraySet<String> mSystemUserBlacklistedApps = new ArraySet<>();
    final ArraySet<ComponentName> mDefaultVrComponents = new ArraySet<>();
    final ArraySet<ComponentName> mBackupTransportWhitelist = new ArraySet<>();
    final ArraySet<String> mHiddenApiPackageWhitelist = new ArraySet<>();
    final ArraySet<String> mDisabledUntilUsedPreinstalledCarrierApps = new ArraySet<>();
    final ArrayMap<String, List<String>> mDisabledUntilUsedPreinstalledCarrierAssociatedApps = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mVendorPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mVendorPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductServicesPrivAppPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mProductServicesPrivAppDenyPermissions = new ArrayMap<>();
    final ArrayMap<String, ArrayMap<String, Boolean>> mOemPermissions = new ArrayMap<>();
    final ArrayMap<String, ArraySet<String>> mAllowedAssociations = new ArrayMap<>();
    private final ArraySet<String> mBugreportWhitelistedPackages = new ArraySet<>();

    /* loaded from: classes4.dex */
    public static final class SharedLibraryEntry {
        public final String[] dependencies;
        public final String filename;
        public final String name;

        SharedLibraryEntry(String name, String filename, String[] dependencies) {
            this.name = name;
            this.filename = filename;
            this.dependencies = dependencies;
        }
    }

    /* loaded from: classes4.dex */
    public static final class PermissionEntry {
        public int[] gids;
        public final String name;
        public boolean perUser;

        PermissionEntry(String name, boolean perUser) {
            this.name = name;
            this.perUser = perUser;
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

    void readPermissions(File libraryDir, int permissionFlag) {
        File[] listFiles;
        if (!libraryDir.exists() || !libraryDir.isDirectory()) {
            if (permissionFlag == -1) {
                Slog.m50w(TAG, "No directory " + libraryDir + ", skipping");
            }
        } else if (!libraryDir.canRead()) {
            Slog.m50w(TAG, "Directory " + libraryDir + " cannot be read");
        } else {
            File platformFile = null;
            for (File f : libraryDir.listFiles()) {
                if (f.isFile()) {
                    if (f.getPath().endsWith("etc/permissions/platform.xml")) {
                        platformFile = f;
                    } else if (!f.getPath().endsWith(".xml")) {
                        Slog.m54i(TAG, "Non-xml file " + f + " in " + libraryDir + " directory, ignoring");
                    } else if (!f.canRead()) {
                        Slog.m50w(TAG, "Permissions library file " + f + " cannot be read");
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
        Slog.m50w(TAG, "<" + name + "> not allowed in partition of " + permFile + " at " + parser.getPositionDescription());
    }

    /* JADX WARN: Removed duplicated region for block: B:185:0x03fa A[Catch: all -> 0x0b7a, IOException -> 0x0b7d, XmlPullParserException -> 0x0b87, TryCatch #2 {IOException -> 0x0b7d, blocks: (B:5:0x0013, B:6:0x001a, B:11:0x0028, B:13:0x0035, B:16:0x0042, B:17:0x0069, B:22:0x0070, B:26:0x0077, B:30:0x007e, B:34:0x0087, B:38:0x0090, B:42:0x0099, B:46:0x00a2, B:50:0x00ab, B:54:0x00b2, B:57:0x00bd, B:59:0x00c3, B:60:0x00cd, B:61:0x00d1, B:139:0x01f8, B:395:0x0b3a, B:141:0x0203, B:143:0x020c, B:145:0x023c, B:144:0x0237, B:149:0x0248, B:151:0x0252, B:152:0x0280, B:154:0x0289, B:155:0x02b9, B:157:0x02cf, B:158:0x02da, B:160:0x0305, B:159:0x02fe, B:163:0x0310, B:165:0x031a, B:169:0x034e, B:166:0x0345, B:168:0x034b, B:172:0x0359, B:173:0x035e, B:176:0x036c, B:178:0x038f, B:183:0x03b6, B:185:0x03fa, B:187:0x0404, B:189:0x040e, B:190:0x0416, B:192:0x041f, B:195:0x042d, B:197:0x0437, B:201:0x046b, B:198:0x0462, B:200:0x0468, B:204:0x0476, B:208:0x048a, B:210:0x0494, B:211:0x049f, B:215:0x04d1, B:212:0x04a3, B:214:0x04ce, B:218:0x04dc, B:220:0x04e6, B:227:0x0553, B:221:0x0511, B:223:0x0517, B:224:0x054a, B:226:0x0550, B:230:0x055e, B:232:0x056f, B:238:0x05d5, B:234:0x059c, B:235:0x05c7, B:237:0x05d2, B:241:0x05e0, B:243:0x05ea, B:247:0x061e, B:244:0x0615, B:246:0x061b, B:250:0x0629, B:252:0x0633, B:256:0x0667, B:253:0x065e, B:255:0x0664, B:259:0x0672, B:261:0x067c, B:265:0x06b0, B:262:0x06a7, B:264:0x06ad, B:268:0x06bb, B:270:0x06c4, B:274:0x06f8, B:271:0x06ef, B:273:0x06f5, B:277:0x0703, B:279:0x070d, B:283:0x0741, B:280:0x0738, B:282:0x073e, B:286:0x074c, B:288:0x0756, B:292:0x078a, B:289:0x0781, B:291:0x0787, B:295:0x0795, B:297:0x079f, B:301:0x07d3, B:298:0x07ca, B:300:0x07d0, B:304:0x07de, B:306:0x07e8, B:310:0x081c, B:307:0x0813, B:309:0x0819, B:313:0x0827, B:315:0x0831, B:319:0x0865, B:316:0x085c, B:318:0x0862, B:322:0x0870, B:324:0x087a, B:328:0x08ae, B:325:0x08a5, B:327:0x08ab, B:331:0x08b9, B:336:0x08e2, B:341:0x0916, B:338:0x090f, B:334:0x08ce, B:340:0x0913, B:344:0x0921, B:346:0x0938, B:356:0x09aa, B:348:0x0965, B:349:0x0990, B:351:0x0994, B:353:0x099e, B:352:0x0998, B:355:0x09a7, B:359:0x09b4, B:361:0x09ba, B:364:0x09c7, B:366:0x09d1, B:367:0x09ff, B:369:0x0a09, B:370:0x0a37, B:372:0x0a3d, B:373:0x0a74, B:375:0x0a83, B:376:0x0a8e, B:378:0x0a95, B:377:0x0a92, B:381:0x0aa0, B:383:0x0aaa, B:384:0x0ad9, B:385:0x0ae3, B:388:0x0af1, B:390:0x0afa, B:394:0x0b36, B:391:0x0b07, B:393:0x0b32, B:63:0x00d6, B:66:0x00e2, B:69:0x00ee, B:72:0x00fb, B:75:0x0107, B:78:0x0113, B:81:0x011f, B:84:0x012b, B:87:0x0138, B:90:0x0143, B:93:0x014f, B:96:0x015c, B:99:0x0168, B:102:0x0174, B:105:0x017f, B:108:0x018a, B:111:0x0195, B:114:0x01a0, B:117:0x01aa, B:120:0x01b5, B:123:0x01c1, B:126:0x01cb, B:129:0x01d6, B:132:0x01e1, B:135:0x01ec, B:398:0x0b70, B:399:0x0b79), top: B:430:0x0013, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0402  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readPermissionsFromXml(File permFile, int permissionFlag) {
        int i;
        XmlPullParser parser;
        int type;
        int i2;
        char c;
        boolean allowAssociations;
        int type2;
        boolean allowed;
        boolean vendor;
        String str = null;
        try {
            FileReader permReader = new FileReader(permFile);
            boolean lowRam = ActivityManager.isLowRamDeviceStatic();
            try {
                try {
                    try {
                        parser = Xml.newPullParser();
                        parser.setInput(permReader);
                        while (true) {
                            int next = parser.next();
                            type = next;
                            i2 = 1;
                            if (next == 2 || type == 1) {
                                break;
                            }
                        }
                    } catch (XmlPullParserException e) {
                        Slog.m49w(TAG, "Got exception parsing permissions.", e);
                    }
                } catch (IOException e2) {
                    Slog.m49w(TAG, "Got exception parsing permissions.", e2);
                }
                if (type != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!parser.getName().equals("permissions") && !parser.getName().equals("config")) {
                    throw new XmlPullParserException("Unexpected start tag in " + permFile + ": found " + parser.getName() + ", expected 'permissions' or 'config'");
                }
                boolean allowAll = permissionFlag == -1;
                boolean allowLibs = (permissionFlag & 2) != 0;
                boolean allowFeatures = (permissionFlag & 1) != 0;
                boolean allowPermissions = (permissionFlag & 4) != 0;
                boolean allowAppConfigs = (permissionFlag & 8) != 0;
                boolean allowPrivappPermissions = (permissionFlag & 16) != 0;
                boolean allowOemPermissions = (permissionFlag & 32) != 0;
                boolean allowApiWhitelisting = (permissionFlag & 64) != 0;
                boolean allowAssociations2 = (permissionFlag & 128) != 0;
                while (true) {
                    XmlUtils.nextElement(parser);
                    if (parser.getEventType() != i2) {
                        String name = parser.getName();
                        if (name == null) {
                            XmlUtils.skipCurrentTag(parser);
                            allowAssociations = allowAssociations2;
                            type2 = type;
                        } else {
                            switch (name.hashCode()) {
                                case -2040330235:
                                    if (name.equals("allow-unthrottled-location")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -1882490007:
                                    if (name.equals("allow-in-power-save")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -1005864890:
                                    if (name.equals("disabled-until-used-preinstalled-carrier-app")) {
                                        c = 19;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -980620291:
                                    if (name.equals("allow-association")) {
                                        c = 23;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -979207434:
                                    if (name.equals("feature")) {
                                        c = 5;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -851582420:
                                    if (name.equals("system-user-blacklisted-app")) {
                                        c = 15;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -828905863:
                                    if (name.equals("unavailable-feature")) {
                                        c = 6;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -642819164:
                                    if (name.equals("allow-in-power-save-except-idle")) {
                                        c = 7;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -560717308:
                                    if (name.equals("allow-ignore-location-settings")) {
                                        c = 11;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case -517618225:
                                    if (name.equals("permission")) {
                                        c = 1;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 98629247:
                                    if (name.equals(WifiConfiguration.GroupCipher.varName)) {
                                        c = 0;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 166208699:
                                    if (name.equals("library")) {
                                        c = 4;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 180165796:
                                    if (name.equals("hidden-api-whitelisted-app")) {
                                        c = 22;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 347247519:
                                    if (name.equals("backup-transport-whitelisted-service")) {
                                        c = 17;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 508457430:
                                    if (name.equals("system-user-whitelisted-app")) {
                                        c = 14;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 802332808:
                                    if (name.equals("allow-in-data-usage-save")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 953292141:
                                    if (name.equals("assign-permission")) {
                                        c = 2;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1044015374:
                                    if (name.equals("oem-permissions")) {
                                        c = 21;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1121420326:
                                    if (name.equals("app-link")) {
                                        c = '\r';
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1269564002:
                                    if (name.equals("split-permission")) {
                                        c = 3;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1567330472:
                                    if (name.equals("default-enabled-vr-app")) {
                                        c = 16;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1633270165:
                                    if (name.equals("disabled-until-used-preinstalled-carrier-associated-app")) {
                                        c = 18;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1723146313:
                                    if (name.equals("privapp-permissions")) {
                                        c = 20;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1723586945:
                                    if (name.equals("bugreport-whitelisted")) {
                                        c = 24;
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                case 1954925533:
                                    if (name.equals("allow-implicit-broadcast")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = '\uffff';
                                    break;
                                default:
                                    c = '\uffff';
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        str = null;
                                        String gidStr = parser.getAttributeValue(null, "gid");
                                        if (gidStr != null) {
                                            int gid = Process.getGidForName(gidStr);
                                            this.mGlobalGids = ArrayUtils.appendInt(this.mGlobalGids, gid);
                                        } else {
                                            Slog.m50w(TAG, "<" + name + "> without gid in " + permFile + " at " + parser.getPositionDescription());
                                        }
                                    } else {
                                        str = null;
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    break;
                                case 1:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowPermissions) {
                                        String perm = parser.getAttributeValue(null, "name");
                                        if (perm == null) {
                                            Slog.m50w(TAG, "<" + name + "> without name in " + permFile + " at " + parser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(parser);
                                        } else {
                                            readPermission(parser, perm.intern());
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                        XmlUtils.skipCurrentTag(parser);
                                    }
                                    str = null;
                                    break;
                                case 2:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowPermissions) {
                                        String perm2 = parser.getAttributeValue(null, "name");
                                        if (perm2 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without name in " + permFile + " at " + parser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(parser);
                                        } else {
                                            String uidStr = parser.getAttributeValue(null, GrantCredentialsPermissionActivity.EXTRAS_REQUESTING_UID);
                                            if (uidStr == null) {
                                                Slog.m50w(TAG, "<" + name + "> without uid in " + permFile + " at " + parser.getPositionDescription());
                                                XmlUtils.skipCurrentTag(parser);
                                            } else {
                                                int uid = Process.getUidForName(uidStr);
                                                if (uid < 0) {
                                                    Slog.m50w(TAG, "<" + name + "> with unknown uid \"" + uidStr + "  in " + permFile + " at " + parser.getPositionDescription());
                                                    XmlUtils.skipCurrentTag(parser);
                                                } else {
                                                    String perm3 = perm2.intern();
                                                    ArraySet<String> perms = this.mSystemPermissions.get(uid);
                                                    if (perms == null) {
                                                        perms = new ArraySet<>();
                                                        this.mSystemPermissions.put(uid, perms);
                                                    }
                                                    perms.add(perm3);
                                                }
                                            }
                                        }
                                        str = null;
                                        break;
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                case 3:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowPermissions) {
                                        readSplitPermission(parser, permFile);
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                        XmlUtils.skipCurrentTag(parser);
                                    }
                                    str = null;
                                    break;
                                case 4:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowLibs) {
                                        String lname = parser.getAttributeValue(null, "name");
                                        String lfile = parser.getAttributeValue(null, ContentResolver.SCHEME_FILE);
                                        String ldependency = parser.getAttributeValue(null, "dependency");
                                        if (lname == null) {
                                            Slog.m50w(TAG, "<" + name + "> without name in " + permFile + " at " + parser.getPositionDescription());
                                        } else if (lfile == null) {
                                            Slog.m50w(TAG, "<" + name + "> without file in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            SharedLibraryEntry entry = new SharedLibraryEntry(lname, lfile, ldependency == null ? new String[0] : ldependency.split(SettingsStringUtil.DELIMITER));
                                            this.mSharedLibraries.put(lname, entry);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 5:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowFeatures) {
                                        String fname = parser.getAttributeValue(null, "name");
                                        int fversion = XmlUtils.readIntAttribute(parser, "version", 0);
                                        if (!lowRam) {
                                            allowed = true;
                                        } else {
                                            String notLowRam = parser.getAttributeValue(null, "notLowRam");
                                            allowed = !"true".equals(notLowRam);
                                        }
                                        if (fname == null) {
                                            Slog.m50w(TAG, "<" + name + "> without name in " + permFile + " at " + parser.getPositionDescription());
                                        } else if (allowed) {
                                            addFeature(fname, fversion);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 6:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowFeatures) {
                                        String fname2 = parser.getAttributeValue(null, "name");
                                        if (fname2 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without name in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mUnavailableFeatures.add(fname2);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 7:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        String pkgname = parser.getAttributeValue(null, "package");
                                        if (pkgname == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mAllowInPowerSaveExceptIdle.add(pkgname);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case '\b':
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        String pkgname2 = parser.getAttributeValue(null, "package");
                                        if (pkgname2 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mAllowInPowerSave.add(pkgname2);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case '\t':
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        String pkgname3 = parser.getAttributeValue(null, "package");
                                        if (pkgname3 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mAllowInDataUsageSave.add(pkgname3);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case '\n':
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        String pkgname4 = parser.getAttributeValue(null, "package");
                                        if (pkgname4 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mAllowUnthrottledLocation.add(pkgname4);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 11:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        String pkgname5 = parser.getAttributeValue(null, "package");
                                        if (pkgname5 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mAllowIgnoreLocationSettings.add(pkgname5);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case '\f':
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAll) {
                                        String action = parser.getAttributeValue(null, "action");
                                        if (action == null) {
                                            Slog.m50w(TAG, "<" + name + "> without action in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mAllowImplicitBroadcasts.add(action);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case '\r':
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAppConfigs) {
                                        String pkgname6 = parser.getAttributeValue(null, "package");
                                        if (pkgname6 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mLinkedApps.add(pkgname6);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 14:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAppConfigs) {
                                        String pkgname7 = parser.getAttributeValue(null, "package");
                                        if (pkgname7 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mSystemUserWhitelistedApps.add(pkgname7);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 15:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAppConfigs) {
                                        String pkgname8 = parser.getAttributeValue(null, "package");
                                        if (pkgname8 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mSystemUserBlacklistedApps.add(pkgname8);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 16:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAppConfigs) {
                                        String pkgname9 = parser.getAttributeValue(null, "package");
                                        String clsname = parser.getAttributeValue(null, "class");
                                        if (pkgname9 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else if (clsname == null) {
                                            Slog.m50w(TAG, "<" + name + "> without class in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mDefaultVrComponents.add(new ComponentName(pkgname9, clsname));
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 17:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowFeatures) {
                                        String serviceName = parser.getAttributeValue(null, "service");
                                        if (serviceName == null) {
                                            Slog.m50w(TAG, "<" + name + "> without service in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            ComponentName cn = ComponentName.unflattenFromString(serviceName);
                                            if (cn == null) {
                                                Slog.m50w(TAG, "<" + name + "> with invalid service name " + serviceName + " in " + permFile + " at " + parser.getPositionDescription());
                                            } else {
                                                this.mBackupTransportWhitelist.add(cn);
                                            }
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 18:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAppConfigs) {
                                        String pkgname10 = parser.getAttributeValue(null, "package");
                                        String carrierPkgname = parser.getAttributeValue(null, "carrierAppPackage");
                                        if (pkgname10 != null && carrierPkgname != null) {
                                            List<String> associatedPkgs = this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps.get(carrierPkgname);
                                            if (associatedPkgs == null) {
                                                associatedPkgs = new ArrayList();
                                                this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps.put(carrierPkgname, associatedPkgs);
                                            }
                                            associatedPkgs.add(pkgname10);
                                        }
                                        Slog.m50w(TAG, "<" + name + "> without package or carrierAppPackage in " + permFile + " at " + parser.getPositionDescription());
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 19:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowAppConfigs) {
                                        String pkgname11 = parser.getAttributeValue(null, "package");
                                        if (pkgname11 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mDisabledUntilUsedPreinstalledCarrierApps.add(pkgname11);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 20:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowPrivappPermissions) {
                                        if (!permFile.toPath().startsWith(Environment.getVendorDirectory().toPath() + "/")) {
                                            if (!permFile.toPath().startsWith(Environment.getOdmDirectory().toPath() + "/")) {
                                                vendor = false;
                                                boolean product = permFile.toPath().startsWith(Environment.getProductDirectory().toPath() + "/");
                                                boolean productServices = permFile.toPath().startsWith(Environment.getProductServicesDirectory().toPath() + "/");
                                                if (!vendor) {
                                                    readPrivAppPermissions(parser, this.mVendorPrivAppPermissions, this.mVendorPrivAppDenyPermissions);
                                                } else if (product) {
                                                    readPrivAppPermissions(parser, this.mProductPrivAppPermissions, this.mProductPrivAppDenyPermissions);
                                                } else if (productServices) {
                                                    readPrivAppPermissions(parser, this.mProductServicesPrivAppPermissions, this.mProductServicesPrivAppDenyPermissions);
                                                } else {
                                                    readPrivAppPermissions(parser, this.mPrivAppPermissions, this.mPrivAppDenyPermissions);
                                                }
                                            }
                                        }
                                        vendor = true;
                                        boolean product2 = permFile.toPath().startsWith(Environment.getProductDirectory().toPath() + "/");
                                        boolean productServices2 = permFile.toPath().startsWith(Environment.getProductServicesDirectory().toPath() + "/");
                                        if (!vendor) {
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                        XmlUtils.skipCurrentTag(parser);
                                    }
                                    str = null;
                                    break;
                                case 21:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowOemPermissions) {
                                        readOemPermissions(parser);
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                        XmlUtils.skipCurrentTag(parser);
                                    }
                                    str = null;
                                    break;
                                case 22:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    if (allowApiWhitelisting) {
                                        String pkgname12 = parser.getAttributeValue(null, "package");
                                        if (pkgname12 == null) {
                                            Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                        } else {
                                            this.mHiddenApiPackageWhitelist.add(pkgname12);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                    break;
                                case 23:
                                    if (allowAssociations2) {
                                        String target = parser.getAttributeValue(null, "target");
                                        if (target == null) {
                                            Slog.m50w(TAG, "<" + name + "> without target in " + permFile + " at " + parser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(parser);
                                            allowAssociations = allowAssociations2;
                                            type2 = type;
                                            str = null;
                                            break;
                                        } else {
                                            String allowed2 = parser.getAttributeValue(null, "allowed");
                                            if (allowed2 == null) {
                                                StringBuilder sb = new StringBuilder();
                                                allowAssociations = allowAssociations2;
                                                sb.append("<");
                                                sb.append(name);
                                                sb.append("> without allowed in ");
                                                sb.append(permFile);
                                                sb.append(" at ");
                                                sb.append(parser.getPositionDescription());
                                                Slog.m50w(TAG, sb.toString());
                                                XmlUtils.skipCurrentTag(parser);
                                                type2 = type;
                                                str = null;
                                            } else {
                                                allowAssociations = allowAssociations2;
                                                String target2 = target.intern();
                                                String allowed3 = allowed2.intern();
                                                ArraySet<String> associations = this.mAllowedAssociations.get(target2);
                                                if (associations == null) {
                                                    associations = new ArraySet<>();
                                                    this.mAllowedAssociations.put(target2, associations);
                                                }
                                                StringBuilder sb2 = new StringBuilder();
                                                type2 = type;
                                                sb2.append("Adding association: ");
                                                sb2.append(target2);
                                                sb2.append(" <- ");
                                                sb2.append(allowed3);
                                                Slog.m54i(TAG, sb2.toString());
                                                associations.add(allowed3);
                                            }
                                        }
                                    } else {
                                        allowAssociations = allowAssociations2;
                                        type2 = type;
                                        logNotAllowedInPartition(name, permFile, parser);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    str = null;
                                case 24:
                                    String pkgname13 = parser.getAttributeValue(str, "package");
                                    if (pkgname13 == null) {
                                        Slog.m50w(TAG, "<" + name + "> without package in " + permFile + " at " + parser.getPositionDescription());
                                    } else {
                                        this.mBugreportWhitelistedPackages.add(pkgname13);
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    str = null;
                                    break;
                                default:
                                    allowAssociations = allowAssociations2;
                                    type2 = type;
                                    Slog.m50w(TAG, "Tag " + name + " is unknown in " + permFile + " at " + parser.getPositionDescription());
                                    XmlUtils.skipCurrentTag(parser);
                                    break;
                            }
                        }
                        allowAssociations2 = allowAssociations;
                        type = type2;
                        i2 = 1;
                    } else {
                        IoUtils.closeQuietly(permReader);
                        if (StorageManager.isFileEncryptedNativeOnly()) {
                            i = 0;
                            addFeature(PackageManager.FEATURE_FILE_BASED_ENCRYPTION, 0);
                            addFeature(PackageManager.FEATURE_SECURELY_REMOVES_USERS, 0);
                        } else {
                            i = 0;
                        }
                        if (StorageManager.hasAdoptable()) {
                            addFeature(PackageManager.FEATURE_ADOPTABLE_STORAGE, i);
                        }
                        if (ActivityManager.isLowRamDeviceStatic()) {
                            addFeature(PackageManager.FEATURE_RAM_LOW, i);
                        } else {
                            addFeature(PackageManager.FEATURE_RAM_NORMAL, i);
                        }
                        Iterator<String> it = this.mUnavailableFeatures.iterator();
                        while (it.hasNext()) {
                            String featureName = it.next();
                            removeFeature(featureName);
                        }
                        return;
                    }
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(permReader);
                throw th;
            }
        } catch (FileNotFoundException e3) {
            Slog.m50w(TAG, "Couldn't find or open permissions file " + permFile);
        }
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
            Slog.m58d(TAG, "Removed unavailable feature " + name);
        }
    }

    void readPermission(XmlPullParser parser, String name) throws IOException, XmlPullParserException {
        if (this.mPermissions.containsKey(name)) {
            throw new IllegalStateException("Duplicate permission definition for " + name);
        }
        boolean perUser = XmlUtils.readBooleanAttribute(parser, "perUser", false);
        PermissionEntry perm = new PermissionEntry(name, perUser);
        this.mPermissions.put(name, perm);
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1) {
                if (type != 3 || parser.getDepth() > outerDepth) {
                    if (type != 3 && type != 4) {
                        String tagName = parser.getName();
                        if (WifiConfiguration.GroupCipher.varName.equals(tagName)) {
                            String gidStr = parser.getAttributeValue(null, "gid");
                            if (gidStr != null) {
                                int gid = Process.getGidForName(gidStr);
                                perm.gids = ArrayUtils.appendInt(perm.gids, gid);
                            } else {
                                Slog.m50w(TAG, "<group> without gid at " + parser.getPositionDescription());
                            }
                        }
                        XmlUtils.skipCurrentTag(parser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void readPrivAppPermissions(XmlPullParser parser, ArrayMap<String, ArraySet<String>> grantMap, ArrayMap<String, ArraySet<String>> denyMap) throws IOException, XmlPullParserException {
        String packageName = parser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(packageName)) {
            Slog.m50w(TAG, "package is required for <privapp-permissions> in " + parser.getPositionDescription());
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
                String permName = parser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(permName)) {
                    Slog.m50w(TAG, "name is required for <permission> in " + parser.getPositionDescription());
                } else {
                    permissions.add(permName);
                }
            } else if ("deny-permission".equals(name)) {
                String permName2 = parser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(permName2)) {
                    Slog.m50w(TAG, "name is required for <deny-permission> in " + parser.getPositionDescription());
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

    void readOemPermissions(XmlPullParser parser) throws IOException, XmlPullParserException {
        String packageName = parser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(packageName)) {
            Slog.m50w(TAG, "package is required for <oem-permissions> in " + parser.getPositionDescription());
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
                String permName = parser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(permName)) {
                    Slog.m50w(TAG, "name is required for <permission> in " + parser.getPositionDescription());
                } else {
                    permissions.put(permName, Boolean.TRUE);
                }
            } else if ("deny-permission".equals(name)) {
                String permName2 = parser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(permName2)) {
                    Slog.m50w(TAG, "name is required for <deny-permission> in " + parser.getPositionDescription());
                } else {
                    permissions.put(permName2, Boolean.FALSE);
                }
            }
        }
        this.mOemPermissions.put(packageName, permissions);
    }

    private void readSplitPermission(XmlPullParser parser, File permFile) throws IOException, XmlPullParserException {
        String splitPerm = parser.getAttributeValue(null, "name");
        if (splitPerm == null) {
            Slog.m50w(TAG, "<split-permission> without name in " + permFile + " at " + parser.getPositionDescription());
            XmlUtils.skipCurrentTag(parser);
            return;
        }
        String targetSdkStr = parser.getAttributeValue(null, "targetSdk");
        int targetSdk = 10001;
        if (!TextUtils.isEmpty(targetSdkStr)) {
            try {
                targetSdk = Integer.parseInt(targetSdkStr);
            } catch (NumberFormatException e) {
                Slog.m50w(TAG, "<split-permission> targetSdk not an integer in " + permFile + " at " + parser.getPositionDescription());
                XmlUtils.skipCurrentTag(parser);
                return;
            }
        }
        int depth = parser.getDepth();
        List<String> newPermissions = new ArrayList<>();
        while (XmlUtils.nextElementWithin(parser, depth)) {
            String name = parser.getName();
            if ("new-permission".equals(name)) {
                String newName = parser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(newName)) {
                    Slog.m50w(TAG, "name is required for <new-permission> in " + parser.getPositionDescription());
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
