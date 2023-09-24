package android.content.p002pm;

import android.Manifest;
import android.annotation.UnsupportedAppUsage;
import android.apex.ApexInfo;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.PackageParserCacheHelper;
import android.content.p002pm.split.DefaultSplitAssetLoader;
import android.content.p002pm.split.SplitAssetDependencyLoader;
import android.content.p002pm.split.SplitAssetLoader;
import android.content.p002pm.split.SplitDependencyLoader;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.media.TtmlUtils;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Build;
import android.p007os.Bundle;
import android.p007os.FileUtils;
import android.p007os.IncidentManager;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.PatternMatcher;
import android.p007os.RemoteException;
import android.p007os.SystemClock;
import android.p007os.SystemProperties;
import android.p007os.Trace;
import android.p007os.UserHandle;
import android.p007os.storage.StorageManager;
import android.provider.SettingsStringUtil;
import android.security.keystore.KeyProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.ByteStringUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.PackageUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.C3132R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.DumpHeapActivity;
import com.android.internal.p016os.ClassLoaderFactory;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.ibm.icu.text.PluralRules;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.content.pm.PackageParser */
/* loaded from: classes.dex */
public class PackageParser {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    public static final String APK_FILE_EXTENSION = ".apk";
    private static final Set<String> CHILD_PACKAGE_TAGS;
    private static final boolean DEBUG_BACKUP = false;
    private static final boolean DEBUG_JAR = false;
    private static final boolean DEBUG_PARSER = false;
    private static final int DEFAULT_MIN_SDK_VERSION = 1;
    private static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 3.0f;
    private static final float DEFAULT_PRE_Q_MIN_ASPECT_RATIO = 1.333f;
    private static final float DEFAULT_PRE_Q_MIN_ASPECT_RATIO_WATCH = 1.0f;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    private static final boolean LOG_PARSE_TIMINGS = Build.IS_DEBUGGABLE;
    private static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    private static final boolean LOG_UNSAFE_BROADCASTS = false;
    private static final String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    private static final String MNT_EXPAND = "/mnt/expand/";
    private static final boolean MULTI_PACKAGE_APK_ENABLED;
    @UnsupportedAppUsage
    public static final NewPermissionInfo[] NEW_PERMISSIONS;
    public static final int PARSE_CHATTY = Integer.MIN_VALUE;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    private static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    private static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    public static final int PARSE_ENFORCE_CODE = 64;
    public static final int PARSE_EXTERNAL_STORAGE = 8;
    public static final int PARSE_IGNORE_PROCESSES = 2;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_MUST_BE_APK = 1;
    private static final String PROPERTY_CHILD_PACKAGES_ENABLED = "persist.sys.child_packages_enabled";
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    private static final boolean RIGID_PARSER = false;
    private static final Set<String> SAFE_BROADCASTS;
    private static final String[] SDK_CODENAMES;
    private static final int SDK_VERSION;
    private static final String TAG = "PackageParser";
    private static final String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    private static final String TAG_APPLICATION = "application";
    private static final String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    private static final String TAG_EAT_COMMENT = "eat-comment";
    private static final String TAG_FEATURE_GROUP = "feature-group";
    private static final String TAG_INSTRUMENTATION = "instrumentation";
    private static final String TAG_KEY_SETS = "key-sets";
    private static final String TAG_MANIFEST = "manifest";
    private static final String TAG_ORIGINAL_PACKAGE = "original-package";
    private static final String TAG_OVERLAY = "overlay";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    private static final String TAG_PERMISSION = "permission";
    private static final String TAG_PERMISSION_GROUP = "permission-group";
    private static final String TAG_PERMISSION_TREE = "permission-tree";
    private static final String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    private static final String TAG_RESTRICT_UPDATE = "restrict-update";
    private static final String TAG_SUPPORTS_INPUT = "supports-input";
    private static final String TAG_SUPPORT_SCREENS = "supports-screens";
    private static final String TAG_USES_CONFIGURATION = "uses-configuration";
    private static final String TAG_USES_FEATURE = "uses-feature";
    private static final String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    private static final String TAG_USES_PERMISSION = "uses-permission";
    private static final String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    private static final String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    private static final String TAG_USES_SDK = "uses-sdk";
    private static final String TAG_USES_SPLIT = "uses-split";
    public static final AtomicInteger sCachedPackageReadCount;
    private static boolean sCompatibilityModeEnabled;
    private static final Comparator<String> sSplitNameComparator;
    private static boolean sUseRoundIcon;
    @Deprecated
    private String mArchiveSourcePath;
    private File mCacheDir;
    @UnsupportedAppUsage
    private Callback mCallback;
    private boolean mOnlyCoreApps;
    private ParsePackageItemArgs mParseInstrumentationArgs;
    private String[] mSeparateProcesses;
    private int mParseError = 1;
    private DisplayMetrics mMetrics = new DisplayMetrics();

    /* renamed from: android.content.pm.PackageParser$Callback */
    /* loaded from: classes.dex */
    public interface Callback {
        String[] getOverlayApks(String str);

        String[] getOverlayPaths(String str, String str2);

        boolean hasFeature(String str);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.content.pm.PackageParser$ParseFlags */
    /* loaded from: classes.dex */
    public @interface ParseFlags {
    }

    static {
        MULTI_PACKAGE_APK_ENABLED = Build.IS_DEBUGGABLE && SystemProperties.getBoolean(PROPERTY_CHILD_PACKAGES_ENABLED, false);
        CHILD_PACKAGE_TAGS = new ArraySet();
        CHILD_PACKAGE_TAGS.add(TAG_APPLICATION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_PERMISSION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_PERMISSION_SDK_M);
        CHILD_PACKAGE_TAGS.add(TAG_USES_PERMISSION_SDK_23);
        CHILD_PACKAGE_TAGS.add(TAG_USES_CONFIGURATION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_FEATURE);
        CHILD_PACKAGE_TAGS.add(TAG_FEATURE_GROUP);
        CHILD_PACKAGE_TAGS.add(TAG_USES_SDK);
        CHILD_PACKAGE_TAGS.add(TAG_SUPPORT_SCREENS);
        CHILD_PACKAGE_TAGS.add(TAG_INSTRUMENTATION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_GL_TEXTURE);
        CHILD_PACKAGE_TAGS.add(TAG_COMPATIBLE_SCREENS);
        CHILD_PACKAGE_TAGS.add(TAG_SUPPORTS_INPUT);
        CHILD_PACKAGE_TAGS.add(TAG_EAT_COMMENT);
        sCachedPackageReadCount = new AtomicInteger();
        SAFE_BROADCASTS = new ArraySet();
        SAFE_BROADCASTS.add(Intent.ACTION_BOOT_COMPLETED);
        NEW_PERMISSIONS = new NewPermissionInfo[]{new NewPermissionInfo(Manifest.C0000permission.WRITE_EXTERNAL_STORAGE, 4, 0), new NewPermissionInfo(Manifest.C0000permission.READ_PHONE_STATE, 4, 0)};
        SDK_VERSION = Build.VERSION.SDK_INT;
        SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;
        sCompatibilityModeEnabled = true;
        sUseRoundIcon = false;
        sSplitNameComparator = new SplitNameComparator();
    }

    /* renamed from: android.content.pm.PackageParser$NewPermissionInfo */
    /* loaded from: classes.dex */
    public static class NewPermissionInfo {
        public final int fileVersion;
        @UnsupportedAppUsage
        public final String name;
        @UnsupportedAppUsage
        public final int sdkVersion;

        public NewPermissionInfo(String name, int sdkVersion, int fileVersion) {
            this.name = name;
            this.sdkVersion = sdkVersion;
            this.fileVersion = fileVersion;
        }
    }

    /* renamed from: android.content.pm.PackageParser$ParsePackageItemArgs */
    /* loaded from: classes.dex */
    static class ParsePackageItemArgs {
        final int bannerRes;
        final int iconRes;
        final int labelRes;
        final int logoRes;
        final int nameRes;
        final String[] outError;
        final Package owner;
        final int roundIconRes;

        /* renamed from: sa */
        TypedArray f29sa;
        String tag;

        ParsePackageItemArgs(Package _owner, String[] _outError, int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes, int _bannerRes) {
            this.owner = _owner;
            this.outError = _outError;
            this.nameRes = _nameRes;
            this.labelRes = _labelRes;
            this.iconRes = _iconRes;
            this.logoRes = _logoRes;
            this.bannerRes = _bannerRes;
            this.roundIconRes = _roundIconRes;
        }
    }

    @VisibleForTesting
    /* renamed from: android.content.pm.PackageParser$ParseComponentArgs */
    /* loaded from: classes.dex */
    public static class ParseComponentArgs extends ParsePackageItemArgs {
        final int descriptionRes;
        final int enabledRes;
        int flags;
        final int processRes;
        final String[] sepProcesses;

        public ParseComponentArgs(Package _owner, String[] _outError, int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes, int _bannerRes, String[] _sepProcesses, int _processRes, int _descriptionRes, int _enabledRes) {
            super(_owner, _outError, _nameRes, _labelRes, _iconRes, _roundIconRes, _logoRes, _bannerRes);
            this.sepProcesses = _sepProcesses;
            this.processRes = _processRes;
            this.descriptionRes = _descriptionRes;
            this.enabledRes = _enabledRes;
        }
    }

    /* renamed from: android.content.pm.PackageParser$PackageLite */
    /* loaded from: classes.dex */
    public static class PackageLite {
        public final String baseCodePath;
        public final int baseRevisionCode;
        public final String codePath;
        public final String[] configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        @UnsupportedAppUsage
        public final int installLocation;
        public final boolean[] isFeatureSplits;
        public final boolean isolatedSplits;
        public final boolean multiArch;
        @UnsupportedAppUsage
        public final String packageName;
        public final String[] splitCodePaths;
        public final String[] splitNames;
        public final int[] splitRevisionCodes;
        public final boolean use32bitAbi;
        public final String[] usesSplitNames;
        public final VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public PackageLite(String codePath, ApkLite baseApk, String[] splitNames, boolean[] isFeatureSplits, String[] usesSplitNames, String[] configForSplit, String[] splitCodePaths, int[] splitRevisionCodes) {
            this.packageName = baseApk.packageName;
            this.versionCode = baseApk.versionCode;
            this.versionCodeMajor = baseApk.versionCodeMajor;
            this.installLocation = baseApk.installLocation;
            this.verifiers = baseApk.verifiers;
            this.splitNames = splitNames;
            this.isFeatureSplits = isFeatureSplits;
            this.usesSplitNames = usesSplitNames;
            this.configForSplit = configForSplit;
            this.codePath = codePath;
            this.baseCodePath = baseApk.codePath;
            this.splitCodePaths = splitCodePaths;
            this.baseRevisionCode = baseApk.revisionCode;
            this.splitRevisionCodes = splitRevisionCodes;
            this.coreApp = baseApk.coreApp;
            this.debuggable = baseApk.debuggable;
            this.multiArch = baseApk.multiArch;
            this.use32bitAbi = baseApk.use32bitAbi;
            this.extractNativeLibs = baseApk.extractNativeLibs;
            this.isolatedSplits = baseApk.isolatedSplits;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList<>();
            paths.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }
            return paths;
        }
    }

    /* renamed from: android.content.pm.PackageParser$ApkLite */
    /* loaded from: classes.dex */
    public static class ApkLite {
        public final String codePath;
        public final String configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        public final int installLocation;
        public boolean isFeatureSplit;
        public final boolean isSplitRequired;
        public final boolean isolatedSplits;
        public final int minSdkVersion;
        public final boolean multiArch;
        public final String packageName;
        public final int revisionCode;
        public final SigningDetails signingDetails;
        public final String splitName;
        public final int targetSdkVersion;
        public final boolean use32bitAbi;
        public final boolean useEmbeddedDex;
        public final String usesSplitName;
        public final VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public ApkLite(String codePath, String packageName, String splitName, boolean isFeatureSplit, String configForSplit, String usesSplitName, boolean isSplitRequired, int versionCode, int versionCodeMajor, int revisionCode, int installLocation, List<VerifierInfo> verifiers, SigningDetails signingDetails, boolean coreApp, boolean debuggable, boolean multiArch, boolean use32bitAbi, boolean useEmbeddedDex, boolean extractNativeLibs, boolean isolatedSplits, int minSdkVersion, int targetSdkVersion) {
            this.codePath = codePath;
            this.packageName = packageName;
            this.splitName = splitName;
            this.isFeatureSplit = isFeatureSplit;
            this.configForSplit = configForSplit;
            this.usesSplitName = usesSplitName;
            this.versionCode = versionCode;
            this.versionCodeMajor = versionCodeMajor;
            this.revisionCode = revisionCode;
            this.installLocation = installLocation;
            this.signingDetails = signingDetails;
            this.verifiers = (VerifierInfo[]) verifiers.toArray(new VerifierInfo[verifiers.size()]);
            this.coreApp = coreApp;
            this.debuggable = debuggable;
            this.multiArch = multiArch;
            this.use32bitAbi = use32bitAbi;
            this.useEmbeddedDex = useEmbeddedDex;
            this.extractNativeLibs = extractNativeLibs;
            this.isolatedSplits = isolatedSplits;
            this.isSplitRequired = isSplitRequired;
            this.minSdkVersion = minSdkVersion;
            this.targetSdkVersion = targetSdkVersion;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

    /* renamed from: android.content.pm.PackageParser$CachedComponentArgs */
    /* loaded from: classes.dex */
    private static class CachedComponentArgs {
        ParseComponentArgs mActivityAliasArgs;
        ParseComponentArgs mActivityArgs;
        ParseComponentArgs mProviderArgs;
        ParseComponentArgs mServiceArgs;

        private CachedComponentArgs() {
        }
    }

    @UnsupportedAppUsage
    public PackageParser() {
        this.mMetrics.setToDefaults();
    }

    @UnsupportedAppUsage
    public void setSeparateProcesses(String[] procs) {
        this.mSeparateProcesses = procs;
    }

    public void setOnlyCoreApps(boolean onlyCoreApps) {
        this.mOnlyCoreApps = onlyCoreApps;
    }

    public void setDisplayMetrics(DisplayMetrics metrics) {
        this.mMetrics = metrics;
    }

    public void setCacheDir(File cacheDir) {
        this.mCacheDir = cacheDir;
    }

    /* renamed from: android.content.pm.PackageParser$CallbackImpl */
    /* loaded from: classes.dex */
    public static final class CallbackImpl implements Callback {
        private final PackageManager mPm;

        public CallbackImpl(PackageManager pm) {
            this.mPm = pm;
        }

        @Override // android.content.p002pm.PackageParser.Callback
        public boolean hasFeature(String feature) {
            return this.mPm.hasSystemFeature(feature);
        }

        @Override // android.content.p002pm.PackageParser.Callback
        public String[] getOverlayPaths(String targetPackageName, String targetPath) {
            return null;
        }

        @Override // android.content.p002pm.PackageParser.Callback
        public String[] getOverlayApks(String targetPackageName) {
            return null;
        }
    }

    public void setCallback(Callback cb) {
        this.mCallback = cb;
    }

    public static final boolean isApkFile(File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(String path) {
        return path.endsWith(APK_FILE_EXTENSION);
    }

    @UnsupportedAppUsage
    public static PackageInfo generatePackageInfo(Package p, int[] gids, int flags, long firstInstallTime, long lastUpdateTime, Set<String> grantedPermissions, PackageUserState state) {
        return generatePackageInfo(p, gids, flags, firstInstallTime, lastUpdateTime, grantedPermissions, state, UserHandle.getCallingUserId());
    }

    private static boolean checkUseInstalledOrHidden(int flags, PackageUserState state, ApplicationInfo appInfo) {
        if ((flags & 536870912) != 0 || state.installed || appInfo == null || !appInfo.hiddenUntilInstalled) {
            if (!state.isAvailable(flags)) {
                if (appInfo == null || !appInfo.isSystemApp()) {
                    return false;
                }
                if ((4202496 & flags) == 0 && (536870912 & flags) == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isAvailable(PackageUserState state) {
        return checkUseInstalledOrHidden(0, state, null);
    }

    @UnsupportedAppUsage
    public static PackageInfo generatePackageInfo(Package p, int[] gids, int flags, long firstInstallTime, long lastUpdateTime, Set<String> grantedPermissions, PackageUserState state, int userId) {
        int N;
        int N2;
        int N3;
        int N4;
        int N5;
        if (checkUseInstalledOrHidden(flags, state, p.applicationInfo) && p.isMatch(flags)) {
            PackageInfo pi = new PackageInfo();
            pi.packageName = p.packageName;
            pi.splitNames = p.splitNames;
            pi.versionCode = p.mVersionCode;
            pi.versionCodeMajor = p.mVersionCodeMajor;
            pi.baseRevisionCode = p.baseRevisionCode;
            pi.splitRevisionCodes = p.splitRevisionCodes;
            pi.versionName = p.mVersionName;
            pi.sharedUserId = p.mSharedUserId;
            pi.sharedUserLabel = p.mSharedUserLabel;
            pi.applicationInfo = generateApplicationInfo(p, flags, state, userId);
            pi.installLocation = p.installLocation;
            pi.isStub = p.isStub;
            pi.coreApp = p.coreApp;
            if ((pi.applicationInfo.flags & 1) != 0 || (pi.applicationInfo.flags & 128) != 0) {
                pi.requiredForAllUsers = p.mRequiredForAllUsers;
            }
            pi.restrictedAccountType = p.mRestrictedAccountType;
            pi.requiredAccountType = p.mRequiredAccountType;
            pi.overlayTarget = p.mOverlayTarget;
            pi.targetOverlayableName = p.mOverlayTargetName;
            pi.overlayCategory = p.mOverlayCategory;
            pi.overlayPriority = p.mOverlayPriority;
            pi.mOverlayIsStatic = p.mOverlayIsStatic;
            pi.compileSdkVersion = p.mCompileSdkVersion;
            pi.compileSdkVersionCodename = p.mCompileSdkVersionCodename;
            pi.firstInstallTime = firstInstallTime;
            pi.lastUpdateTime = lastUpdateTime;
            if ((flags & 256) != 0) {
                pi.gids = gids;
            }
            if ((flags & 16384) != 0) {
                int N6 = p.configPreferences != null ? p.configPreferences.size() : 0;
                if (N6 > 0) {
                    pi.configPreferences = new ConfigurationInfo[N6];
                    p.configPreferences.toArray(pi.configPreferences);
                }
                int N7 = p.reqFeatures != null ? p.reqFeatures.size() : 0;
                if (N7 > 0) {
                    pi.reqFeatures = new FeatureInfo[N7];
                    p.reqFeatures.toArray(pi.reqFeatures);
                }
                int N8 = p.featureGroups != null ? p.featureGroups.size() : 0;
                if (N8 > 0) {
                    pi.featureGroups = new FeatureGroupInfo[N8];
                    p.featureGroups.toArray(pi.featureGroups);
                }
            }
            int N9 = flags & 1;
            if (N9 != 0 && (N5 = p.activities.size()) > 0) {
                ActivityInfo[] res = new ActivityInfo[N5];
                int num = 0;
                int num2 = 0;
                while (num2 < N5) {
                    Activity a = p.activities.get(num2);
                    int N10 = N5;
                    if (state.isMatch(a.info, flags) && !PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(a.className)) {
                        res[num] = generateActivityInfo(a, flags, state, userId);
                        num++;
                    }
                    num2++;
                    N5 = N10;
                }
                pi.activities = (ActivityInfo[]) ArrayUtils.trimToSize(res, num);
            }
            if ((flags & 2) != 0 && (N4 = p.receivers.size()) > 0) {
                ActivityInfo[] res2 = new ActivityInfo[N4];
                int num3 = 0;
                for (int num4 = 0; num4 < N4; num4++) {
                    Activity a2 = p.receivers.get(num4);
                    if (state.isMatch(a2.info, flags)) {
                        res2[num3] = generateActivityInfo(a2, flags, state, userId);
                        num3++;
                    }
                }
                pi.receivers = (ActivityInfo[]) ArrayUtils.trimToSize(res2, num3);
            }
            int N11 = flags & 4;
            if (N11 != 0 && (N3 = p.services.size()) > 0) {
                ServiceInfo[] res3 = new ServiceInfo[N3];
                int num5 = 0;
                for (int num6 = 0; num6 < N3; num6++) {
                    Service s = p.services.get(num6);
                    if (state.isMatch(s.info, flags)) {
                        res3[num5] = generateServiceInfo(s, flags, state, userId);
                        num5++;
                    }
                }
                pi.services = (ServiceInfo[]) ArrayUtils.trimToSize(res3, num5);
            }
            int N12 = flags & 8;
            if (N12 != 0 && (N2 = p.providers.size()) > 0) {
                ProviderInfo[] res4 = new ProviderInfo[N2];
                int num7 = 0;
                for (int num8 = 0; num8 < N2; num8++) {
                    Provider pr = p.providers.get(num8);
                    if (state.isMatch(pr.info, flags)) {
                        res4[num7] = generateProviderInfo(pr, flags, state, userId);
                        num7++;
                    }
                }
                pi.providers = (ProviderInfo[]) ArrayUtils.trimToSize(res4, num7);
            }
            int N13 = flags & 16;
            if (N13 != 0 && (N = p.instrumentation.size()) > 0) {
                pi.instrumentation = new InstrumentationInfo[N];
                for (int i = 0; i < N; i++) {
                    pi.instrumentation[i] = generateInstrumentationInfo(p.instrumentation.get(i), flags);
                }
            }
            int N14 = flags & 4096;
            if (N14 != 0) {
                int N15 = p.permissions.size();
                if (N15 > 0) {
                    pi.permissions = new PermissionInfo[N15];
                    for (int i2 = 0; i2 < N15; i2++) {
                        pi.permissions[i2] = generatePermissionInfo(p.permissions.get(i2), flags);
                    }
                }
                int N16 = p.requestedPermissions.size();
                if (N16 > 0) {
                    pi.requestedPermissions = new String[N16];
                    pi.requestedPermissionsFlags = new int[N16];
                    for (int i3 = 0; i3 < N16; i3++) {
                        String perm = p.requestedPermissions.get(i3);
                        pi.requestedPermissions[i3] = perm;
                        int[] iArr = pi.requestedPermissionsFlags;
                        iArr[i3] = iArr[i3] | 1;
                        if (grantedPermissions != null && grantedPermissions.contains(perm)) {
                            int[] iArr2 = pi.requestedPermissionsFlags;
                            iArr2[i3] = iArr2[i3] | 2;
                        }
                    }
                }
            }
            int N17 = flags & 64;
            if (N17 != 0) {
                if (p.mSigningDetails.hasPastSigningCertificates()) {
                    pi.signatures = new Signature[1];
                    pi.signatures[0] = p.mSigningDetails.pastSigningCertificates[0];
                } else if (p.mSigningDetails.hasSignatures()) {
                    int numberOfSigs = p.mSigningDetails.signatures.length;
                    pi.signatures = new Signature[numberOfSigs];
                    System.arraycopy(p.mSigningDetails.signatures, 0, pi.signatures, 0, numberOfSigs);
                }
            }
            if ((134217728 & flags) != 0) {
                if (p.mSigningDetails != SigningDetails.UNKNOWN) {
                    pi.signingInfo = new SigningInfo(p.mSigningDetails);
                } else {
                    pi.signingInfo = null;
                }
            }
            return pi;
        }
        return null;
    }

    /* renamed from: android.content.pm.PackageParser$SplitNameComparator */
    /* loaded from: classes.dex */
    private static class SplitNameComparator implements Comparator<String> {
        private SplitNameComparator() {
        }

        @Override // java.util.Comparator
        public int compare(String lhs, String rhs) {
            if (lhs == null) {
                return -1;
            }
            if (rhs == null) {
                return 1;
            }
            return lhs.compareTo(rhs);
        }
    }

    @UnsupportedAppUsage
    public static PackageLite parsePackageLite(File packageFile, int flags) throws PackageParserException {
        if (packageFile.isDirectory()) {
            return parseClusterPackageLite(packageFile, flags);
        }
        return parseMonolithicPackageLite(packageFile, flags);
    }

    private static PackageLite parseMonolithicPackageLite(File packageFile, int flags) throws PackageParserException {
        Trace.traceBegin(262144L, "parseApkLite");
        ApkLite baseApk = parseApkLite(packageFile, flags);
        String packagePath = packageFile.getAbsolutePath();
        Trace.traceEnd(262144L);
        return new PackageLite(packagePath, baseApk, null, null, null, null, null, null);
    }

    static PackageLite parseClusterPackageLite(File packageDir, int flags) throws PackageParserException {
        File[] files = packageDir.listFiles();
        if (ArrayUtils.isEmpty(files)) {
            throw new PackageParserException(-100, "No packages found in split");
        }
        Trace.traceBegin(262144L, "parseApkLite");
        ArrayMap<String, ApkLite> apks = new ArrayMap<>();
        int versionCode = 0;
        String packageName = null;
        for (File file : files) {
            if (isApkFile(file)) {
                ApkLite lite = parseApkLite(file, flags);
                if (packageName == null) {
                    packageName = lite.packageName;
                    versionCode = lite.versionCode;
                } else if (!packageName.equals(lite.packageName)) {
                    throw new PackageParserException(-101, "Inconsistent package " + lite.packageName + " in " + file + "; expected " + packageName);
                } else if (versionCode != lite.versionCode) {
                    throw new PackageParserException(-101, "Inconsistent version " + lite.versionCode + " in " + file + "; expected " + versionCode);
                }
                if (apks.put(lite.splitName, lite) != null) {
                    throw new PackageParserException(-101, "Split name " + lite.splitName + " defined more than once; most recent was " + file);
                }
            }
        }
        Trace.traceEnd(262144L);
        ApkLite baseApk = apks.remove(null);
        if (baseApk == null) {
            throw new PackageParserException(-101, "Missing base APK in " + packageDir);
        }
        int size = apks.size();
        String[] splitNames = null;
        boolean[] isFeatureSplits = null;
        String[] usesSplitNames = null;
        String[] configForSplits = null;
        String[] splitCodePaths = null;
        int[] splitRevisionCodes = null;
        if (size > 0) {
            String[] splitNames2 = new String[size];
            isFeatureSplits = new boolean[size];
            usesSplitNames = new String[size];
            configForSplits = new String[size];
            splitCodePaths = new String[size];
            splitRevisionCodes = new int[size];
            splitNames = (String[]) apks.keySet().toArray(splitNames2);
            Arrays.sort(splitNames, sSplitNameComparator);
            for (int i = 0; i < size; i++) {
                ApkLite apk = apks.get(splitNames[i]);
                usesSplitNames[i] = apk.usesSplitName;
                isFeatureSplits[i] = apk.isFeatureSplit;
                configForSplits[i] = apk.configForSplit;
                splitCodePaths[i] = apk.codePath;
                splitRevisionCodes[i] = apk.revisionCode;
            }
        }
        String codePath = packageDir.getAbsolutePath();
        return new PackageLite(codePath, baseApk, splitNames, isFeatureSplits, usesSplitNames, configForSplits, splitCodePaths, splitRevisionCodes);
    }

    @UnsupportedAppUsage
    public Package parsePackage(File packageFile, int flags, boolean useCaches) throws PackageParserException {
        Package parsed;
        Package parsed2 = useCaches ? getCachedResult(packageFile, flags) : null;
        if (parsed2 != null) {
            return parsed2;
        }
        long parseTime = LOG_PARSE_TIMINGS ? SystemClock.uptimeMillis() : 0L;
        if (packageFile.isDirectory()) {
            parsed = parseClusterPackage(packageFile, flags);
        } else {
            parsed = parseMonolithicPackage(packageFile, flags);
        }
        long cacheTime = LOG_PARSE_TIMINGS ? SystemClock.uptimeMillis() : 0L;
        cacheResult(packageFile, flags, parsed);
        if (LOG_PARSE_TIMINGS) {
            long parseTime2 = cacheTime - parseTime;
            long cacheTime2 = SystemClock.uptimeMillis() - cacheTime;
            if (parseTime2 + cacheTime2 > 100) {
                Slog.m54i(TAG, "Parse times for '" + packageFile + "': parse=" + parseTime2 + "ms, update_cache=" + cacheTime2 + " ms");
            }
        }
        return parsed;
    }

    @UnsupportedAppUsage
    public Package parsePackage(File packageFile, int flags) throws PackageParserException {
        return parsePackage(packageFile, flags, false);
    }

    private String getCacheKey(File packageFile, int flags) {
        return packageFile.getName() + '-' + flags;
    }

    @VisibleForTesting
    protected Package fromCacheEntry(byte[] bytes) {
        return fromCacheEntryStatic(bytes);
    }

    @VisibleForTesting
    public static Package fromCacheEntryStatic(byte[] bytes) {
        Parcel p = Parcel.obtain();
        p.unmarshall(bytes, 0, bytes.length);
        p.setDataPosition(0);
        PackageParserCacheHelper.ReadHelper helper = new PackageParserCacheHelper.ReadHelper(p);
        helper.startAndInstall();
        Package pkg = new Package(p);
        p.recycle();
        sCachedPackageReadCount.incrementAndGet();
        return pkg;
    }

    @VisibleForTesting
    protected byte[] toCacheEntry(Package pkg) {
        return toCacheEntryStatic(pkg);
    }

    @VisibleForTesting
    public static byte[] toCacheEntryStatic(Package pkg) {
        Parcel p = Parcel.obtain();
        PackageParserCacheHelper.WriteHelper helper = new PackageParserCacheHelper.WriteHelper(p);
        pkg.writeToParcel(p, 0);
        helper.finishAndUninstall();
        byte[] serialized = p.marshall();
        p.recycle();
        return serialized;
    }

    private static boolean isCacheUpToDate(File packageFile, File cacheFile) {
        try {
            StructStat pkg = Os.stat(packageFile.getAbsolutePath());
            StructStat cache = Os.stat(cacheFile.getAbsolutePath());
            return pkg.st_mtime < cache.st_mtime;
        } catch (ErrnoException ee) {
            if (ee.errno != OsConstants.ENOENT) {
                Slog.m48w("Error while stating package cache : ", ee);
            }
            return false;
        }
    }

    private Package getCachedResult(File packageFile, int flags) {
        String[] overlayApks;
        if (this.mCacheDir == null) {
            return null;
        }
        String cacheKey = getCacheKey(packageFile, flags);
        File cacheFile = new File(this.mCacheDir, cacheKey);
        Slog.m56e(TAG, "wits getCachedResult packageFile=" + packageFile + "ms, cacheFile=" + cacheFile.getAbsolutePath());
        try {
            if (isCacheUpToDate(packageFile, cacheFile)) {
                byte[] bytes = IoUtils.readFileAsByteArray(cacheFile.getAbsolutePath());
                Package p = fromCacheEntry(bytes);
                if (this.mCallback != null && (overlayApks = this.mCallback.getOverlayApks(p.packageName)) != null && overlayApks.length > 0) {
                    for (String overlayApk : overlayApks) {
                        if (!isCacheUpToDate(new File(overlayApk), cacheFile)) {
                            return null;
                        }
                    }
                }
                return p;
            }
            return null;
        } catch (Throwable e) {
            Slog.m49w(TAG, "Error reading package cache: ", e);
            cacheFile.delete();
            return null;
        }
    }

    private void cacheResult(File packageFile, int flags, Package parsed) {
        if (this.mCacheDir == null) {
            return;
        }
        try {
            String cacheKey = getCacheKey(packageFile, flags);
            File cacheFile = new File(this.mCacheDir, cacheKey);
            if (cacheFile.exists() && !cacheFile.delete()) {
                Slog.m56e(TAG, "Unable to delete cache file: " + cacheFile);
            }
            byte[] cacheEntry = toCacheEntry(parsed);
            if (cacheEntry == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(cacheFile);
                try {
                    fos.write(cacheEntry);
                    fos.close();
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        if (th != null) {
                            try {
                                fos.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        } else {
                            fos.close();
                        }
                        throw th2;
                    }
                }
            } catch (IOException ioe) {
                Slog.m49w(TAG, "Error writing cache entry.", ioe);
                cacheFile.delete();
            }
        } catch (Throwable e) {
            Slog.m49w(TAG, "Error saving package cache.", e);
        }
    }

    private Package parseClusterPackage(File packageDir, int flags) throws PackageParserException {
        SplitAssetLoader assetLoader;
        PackageLite lite = parseClusterPackageLite(packageDir, 0);
        if (this.mOnlyCoreApps && !lite.coreApp) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Not a coreApp: " + packageDir);
        }
        SparseArray<int[]> splitDependencies = null;
        if (lite.isolatedSplits && !ArrayUtils.isEmpty(lite.splitNames)) {
            try {
                splitDependencies = SplitAssetDependencyLoader.createDependenciesFromPackage(lite);
                assetLoader = new SplitAssetDependencyLoader(lite, splitDependencies, flags);
            } catch (SplitDependencyLoader.IllegalDependencyException e) {
                throw new PackageParserException(-101, e.getMessage());
            }
        } else {
            assetLoader = new DefaultSplitAssetLoader(lite, flags);
        }
        try {
            try {
                AssetManager assets = assetLoader.getBaseAssetManager();
                File baseApk = new File(lite.baseCodePath);
                Package pkg = parseBaseApk(baseApk, assets, flags);
                if (pkg == null) {
                    throw new PackageParserException(-100, "Failed to parse base APK: " + baseApk);
                }
                if (!ArrayUtils.isEmpty(lite.splitNames)) {
                    int num = lite.splitNames.length;
                    pkg.splitNames = lite.splitNames;
                    pkg.splitCodePaths = lite.splitCodePaths;
                    pkg.splitRevisionCodes = lite.splitRevisionCodes;
                    pkg.splitFlags = new int[num];
                    pkg.splitPrivateFlags = new int[num];
                    pkg.applicationInfo.splitNames = pkg.splitNames;
                    pkg.applicationInfo.splitDependencies = splitDependencies;
                    pkg.applicationInfo.splitClassLoaderNames = new String[num];
                    for (int i = 0; i < num; i++) {
                        AssetManager splitAssets = assetLoader.getSplitAssetManager(i);
                        parseSplitApk(pkg, i, splitAssets, flags);
                    }
                }
                pkg.setCodePath(packageDir.getCanonicalPath());
                pkg.setUse32bitAbi(lite.use32bitAbi);
                return pkg;
            } catch (IOException e2) {
                throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to get path: " + lite.baseCodePath, e2);
            }
        } finally {
            IoUtils.closeQuietly(assetLoader);
        }
    }

    @UnsupportedAppUsage
    @Deprecated
    public Package parseMonolithicPackage(File apkFile, int flags) throws PackageParserException {
        PackageLite lite = parseMonolithicPackageLite(apkFile, flags);
        if (this.mOnlyCoreApps && !lite.coreApp) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Not a coreApp: " + apkFile);
        }
        SplitAssetLoader assetLoader = new DefaultSplitAssetLoader(lite, flags);
        try {
            try {
                Package pkg = parseBaseApk(apkFile, assetLoader.getBaseAssetManager(), flags);
                pkg.setCodePath(apkFile.getCanonicalPath());
                pkg.setUse32bitAbi(lite.use32bitAbi);
                return pkg;
            } catch (IOException e) {
                throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to get path: " + apkFile, e);
            }
        } finally {
            IoUtils.closeQuietly(assetLoader);
        }
    }

    private Package parseBaseApk(File apkFile, AssetManager assets, int flags) throws PackageParserException {
        XmlResourceParser parser;
        String apkPath = apkFile.getAbsolutePath();
        String volumeUuid = null;
        if (apkPath.startsWith(MNT_EXPAND)) {
            int end = apkPath.indexOf(47, MNT_EXPAND.length());
            volumeUuid = apkPath.substring(MNT_EXPAND.length(), end);
        }
        String volumeUuid2 = volumeUuid;
        this.mParseError = 1;
        this.mArchiveSourcePath = apkFile.getAbsolutePath();
        try {
            try {
                int cookie = assets.findCookieForPath(apkPath);
                if (cookie == 0) {
                    throw new PackageParserException(-101, "Failed adding asset path: " + apkPath);
                }
                parser = assets.openXmlResourceParser(cookie, ANDROID_MANIFEST_FILENAME);
                try {
                    Resources res = new Resources(assets, this.mMetrics, null);
                    String[] outError = new String[1];
                    Package pkg = parseBaseApk(apkPath, res, parser, flags, outError);
                    if (pkg != null) {
                        pkg.setVolumeUuid(volumeUuid2);
                        pkg.setApplicationVolumeUuid(volumeUuid2);
                        pkg.setBaseCodePath(apkPath);
                        pkg.setSigningDetails(SigningDetails.UNKNOWN);
                        IoUtils.closeQuietly(parser);
                        return pkg;
                    }
                    throw new PackageParserException(this.mParseError, apkPath + " (at " + parser.getPositionDescription() + "): " + outError[0]);
                } catch (PackageParserException e) {
                    throw e;
                } catch (Exception e2) {
                    e = e2;
                    throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
                } catch (Throwable th) {
                    e = th;
                    IoUtils.closeQuietly(parser);
                    throw e;
                }
            } catch (PackageParserException e3) {
                throw e3;
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            e = th2;
            parser = null;
        }
    }

    private void parseSplitApk(Package pkg, int splitIndex, AssetManager assets, int flags) throws PackageParserException {
        String apkPath = pkg.splitCodePaths[splitIndex];
        this.mParseError = 1;
        this.mArchiveSourcePath = apkPath;
        XmlResourceParser parser = null;
        try {
            try {
                int cookie = assets.findCookieForPath(apkPath);
                if (cookie == 0) {
                    throw new PackageParserException(-101, "Failed adding asset path: " + apkPath);
                }
                XmlResourceParser parser2 = assets.openXmlResourceParser(cookie, ANDROID_MANIFEST_FILENAME);
                try {
                    Resources res = new Resources(assets, this.mMetrics, null);
                    String[] outError = new String[1];
                    if (parseSplitApk(pkg, res, parser2, flags, splitIndex, outError) != null) {
                        IoUtils.closeQuietly(parser2);
                        return;
                    }
                    try {
                        throw new PackageParserException(this.mParseError, apkPath + " (at " + parser2.getPositionDescription() + "): " + outError[0]);
                    } catch (PackageParserException e) {
                        e = e;
                    } catch (Exception e2) {
                        e = e2;
                        throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
                    } catch (Throwable th) {
                        e = th;
                        parser = parser2;
                        IoUtils.closeQuietly(parser);
                        throw e;
                    }
                } catch (PackageParserException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                } catch (Throwable th2) {
                    e = th2;
                }
            } catch (PackageParserException e5) {
                throw e5;
            } catch (Exception e6) {
                e = e6;
            }
        } catch (Throwable th3) {
            e = th3;
        }
    }

    private Package parseSplitApk(Package pkg, Resources res, XmlResourceParser parser, int flags, int splitIndex, String[] outError) throws XmlPullParserException, IOException, PackageParserException {
        parsePackageSplitNames(parser, parser);
        this.mParseInstrumentationArgs = null;
        boolean foundApp = false;
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (type != 3 && type != 4) {
                String tagName = parser.getName();
                if (tagName.equals(TAG_APPLICATION)) {
                    if (foundApp) {
                        Slog.m50w(TAG, "<manifest> has more than one <application>");
                        XmlUtils.skipCurrentTag(parser);
                    } else {
                        foundApp = true;
                        if (!parseSplitApplication(pkg, res, parser, flags, splitIndex, outError)) {
                            return null;
                        }
                    }
                } else {
                    Slog.m50w(TAG, "Unknown element under <manifest>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
        if (!foundApp) {
            outError[0] = "<manifest> does not contain an <application>";
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
        }
        return pkg;
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] signatures) throws CertificateException {
        ArraySet<PublicKey> keys = new ArraySet<>(signatures.length);
        for (Signature signature : signatures) {
            keys.add(signature.getPublicKey());
        }
        return keys;
    }

    @UnsupportedAppUsage
    public static void collectCertificates(Package pkg, boolean skipVerify) throws PackageParserException {
        collectCertificatesInternal(pkg, skipVerify);
        int childCount = pkg.childPackages != null ? pkg.childPackages.size() : 0;
        for (int i = 0; i < childCount; i++) {
            Package childPkg = pkg.childPackages.get(i);
            childPkg.mSigningDetails = pkg.mSigningDetails;
        }
    }

    private static void collectCertificatesInternal(Package pkg, boolean skipVerify) throws PackageParserException {
        pkg.mSigningDetails = SigningDetails.UNKNOWN;
        Trace.traceBegin(262144L, "collectCertificates");
        try {
            collectCertificates(pkg, new File(pkg.baseCodePath), skipVerify);
            if (!ArrayUtils.isEmpty(pkg.splitCodePaths)) {
                for (int i = 0; i < pkg.splitCodePaths.length; i++) {
                    collectCertificates(pkg, new File(pkg.splitCodePaths[i]), skipVerify);
                }
            }
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    @UnsupportedAppUsage
    private static void collectCertificates(Package pkg, File apkFile, boolean skipVerify) throws PackageParserException {
        SigningDetails verified;
        String apkPath = apkFile.getAbsolutePath();
        int minSignatureScheme = 1;
        if (pkg.applicationInfo.isStaticSharedLibrary()) {
            minSignatureScheme = 2;
        }
        if (skipVerify) {
            verified = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(apkPath, minSignatureScheme);
        } else {
            verified = ApkSignatureVerifier.verify(apkPath, minSignatureScheme);
        }
        if (pkg.mSigningDetails == SigningDetails.UNKNOWN) {
            pkg.mSigningDetails = verified;
        } else if (!Signature.areExactMatch(pkg.mSigningDetails.signatures, verified.signatures)) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES, apkPath + " has mismatched certificates");
        }
    }

    private static AssetManager newConfiguredAssetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        return assetManager;
    }

    public static ApkLite parseApkLite(File apkFile, int flags) throws PackageParserException {
        return parseApkLiteInner(apkFile, null, null, flags);
    }

    public static ApkLite parseApkLite(FileDescriptor fd, String debugPathName, int flags) throws PackageParserException {
        return parseApkLiteInner(null, fd, debugPathName, flags);
    }

    private static ApkLite parseApkLiteInner(File apkFile, FileDescriptor fd, String debugPathName, int flags) throws PackageParserException {
        ApkAssets loadFromPath;
        SigningDetails signingDetails;
        String apkPath = fd != null ? debugPathName : apkFile.getAbsolutePath();
        ApkAssets apkAssets = null;
        try {
            try {
                try {
                    if (fd != null) {
                        loadFromPath = ApkAssets.loadFromFd(fd, debugPathName, false, false);
                    } else {
                        loadFromPath = ApkAssets.loadFromPath(apkPath);
                    }
                    ApkAssets apkAssets2 = loadFromPath;
                    XmlResourceParser parser = apkAssets2.openXml(ANDROID_MANIFEST_FILENAME);
                    if ((flags & 32) != 0) {
                        Package tempPkg = new Package((String) null);
                        boolean skipVerify = (flags & 16) != 0;
                        Trace.traceBegin(262144L, "collectCertificates");
                        try {
                            collectCertificates(tempPkg, apkFile, skipVerify);
                            Trace.traceEnd(262144L);
                            signingDetails = tempPkg.mSigningDetails;
                        } catch (Throwable th) {
                            Trace.traceEnd(262144L);
                            throw th;
                        }
                    } else {
                        signingDetails = SigningDetails.UNKNOWN;
                    }
                    ApkLite parseApkLite = parseApkLite(apkPath, parser, parser, signingDetails);
                    IoUtils.closeQuietly(parser);
                    if (apkAssets2 != null) {
                        try {
                            apkAssets2.close();
                        } catch (Throwable th2) {
                        }
                    }
                    return parseApkLite;
                } catch (IOException | RuntimeException | XmlPullParserException e) {
                    Slog.m49w(TAG, "Failed to parse " + apkPath, e);
                    throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to parse " + apkPath, e);
                }
            } catch (Throwable th3) {
                IoUtils.closeQuietly((AutoCloseable) null);
                if (0 != 0) {
                    try {
                        apkAssets.close();
                    } catch (Throwable th4) {
                    }
                }
                throw th3;
            }
        } catch (IOException e2) {
            throw new PackageParserException(-100, "Failed to parse " + apkPath);
        }
    }

    private static String validateName(String name, boolean requireSeparator, boolean requireFilename) {
        int N = name.length();
        boolean hasSep = false;
        boolean front = true;
        for (int i = 0; i < N; i++) {
            char c = name.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                front = false;
            } else if (front || ((c < '0' || c > '9') && c != '_')) {
                if (c != '.') {
                    return "bad character '" + c + "'";
                }
                hasSep = true;
                front = true;
            }
        }
        if (requireFilename && !FileUtils.isValidExtFilename(name)) {
            return "Invalid filename";
        }
        if (hasSep || !requireSeparator) {
            return null;
        }
        return "must have at least one '.' separator";
    }

    private static Pair<String, String> parsePackageSplitNames(XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException, PackageParserException {
        int type;
        String error;
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No start tag found");
        }
        if (!parser.getName().equals(TAG_MANIFEST)) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No <manifest> tag");
        }
        String packageName = attrs.getAttributeValue(null, "package");
        if (!"android".equals(packageName) && (error = validateName(packageName, true, true)) != null) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest package: " + error);
        }
        String splitName = attrs.getAttributeValue(null, "split");
        if (splitName != null) {
            if (splitName.length() == 0) {
                splitName = null;
            } else {
                String error2 = validateName(splitName, false, false);
                if (error2 != null) {
                    throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest split: " + error2);
                }
            }
        }
        return Pair.create(packageName.intern(), splitName != null ? splitName.intern() : splitName);
    }

    private static ApkLite parseApkLite(String codePath, XmlPullParser parser, AttributeSet attrs, SigningDetails signingDetails) throws IOException, XmlPullParserException, PackageParserException {
        int minSdkVersion;
        int type;
        int searchDepth;
        Pair<String, String> packageSplit = parsePackageSplitNames(parser, attrs);
        int targetSdkVersion = 0;
        int minSdkVersion2 = 1;
        boolean debuggable = false;
        boolean multiArch = false;
        boolean use32bitAbi = false;
        boolean extractNativeLibs = true;
        boolean useEmbeddedDex = false;
        String configForSplit = null;
        String usesSplitName = null;
        boolean isFeatureSplit = false;
        boolean isSplitRequired = false;
        boolean isFeatureSplit2 = false;
        boolean isSplitRequired2 = false;
        int versionCodeMajor = 0;
        int revisionCode = 0;
        int installLocation = -1;
        int revisionCode2 = 0;
        int versionCode = 0;
        while (versionCode < attrs.getAttributeCount()) {
            String attr = attrs.getAttributeName(versionCode);
            int targetSdkVersion2 = targetSdkVersion;
            if (attr.equals("installLocation")) {
                installLocation = attrs.getAttributeIntValue(versionCode, -1);
            } else if (attr.equals("versionCode")) {
                revisionCode2 = attrs.getAttributeIntValue(versionCode, 0);
            } else if (attr.equals("versionCodeMajor")) {
                versionCodeMajor = attrs.getAttributeIntValue(versionCode, 0);
            } else if (attr.equals("revisionCode")) {
                revisionCode = attrs.getAttributeIntValue(versionCode, 0);
            } else if (attr.equals("coreApp")) {
                isFeatureSplit2 = attrs.getAttributeBooleanValue(versionCode, false);
            } else if (attr.equals("isolatedSplits")) {
                isSplitRequired2 = attrs.getAttributeBooleanValue(versionCode, false);
            } else if (attr.equals("configForSplit")) {
                configForSplit = attrs.getAttributeValue(versionCode);
            } else if (attr.equals("isFeatureSplit")) {
                isFeatureSplit = attrs.getAttributeBooleanValue(versionCode, false);
            } else if (attr.equals("isSplitRequired")) {
                isSplitRequired = attrs.getAttributeBooleanValue(versionCode, false);
            }
            versionCode++;
            targetSdkVersion = targetSdkVersion2;
        }
        int targetSdkVersion3 = targetSdkVersion;
        int type2 = 1;
        int searchDepth2 = parser.getDepth() + 1;
        List<VerifierInfo> verifiers = new ArrayList<>();
        while (true) {
            minSdkVersion = minSdkVersion2;
            int type3 = parser.next();
            if (type3 != type2 && ((type = type3) != 3 || parser.getDepth() >= searchDepth2)) {
                if (type == 3) {
                    searchDepth = searchDepth2;
                } else if (type != 4 && parser.getDepth() == searchDepth2) {
                    searchDepth = searchDepth2;
                    if (TAG_PACKAGE_VERIFIER.equals(parser.getName())) {
                        VerifierInfo verifier = parseVerifier(attrs);
                        if (verifier != null) {
                            verifiers.add(verifier);
                        }
                    } else if (TAG_APPLICATION.equals(parser.getName())) {
                        int i = 0;
                        while (i < attrs.getAttributeCount()) {
                            String attr2 = attrs.getAttributeName(i);
                            int type4 = type;
                            if ("debuggable".equals(attr2)) {
                                debuggable = attrs.getAttributeBooleanValue(i, false);
                            }
                            if ("multiArch".equals(attr2)) {
                                multiArch = attrs.getAttributeBooleanValue(i, false);
                            }
                            if ("use32bitAbi".equals(attr2)) {
                                use32bitAbi = attrs.getAttributeBooleanValue(i, false);
                            }
                            if ("extractNativeLibs".equals(attr2)) {
                                extractNativeLibs = attrs.getAttributeBooleanValue(i, true);
                            }
                            if ("useEmbeddedDex".equals(attr2)) {
                                useEmbeddedDex = attrs.getAttributeBooleanValue(i, false);
                            }
                            i++;
                            type = type4;
                        }
                        minSdkVersion2 = minSdkVersion;
                        searchDepth2 = searchDepth;
                        type2 = 1;
                    } else if (TAG_USES_SPLIT.equals(parser.getName())) {
                        if (usesSplitName != null) {
                            Slog.m50w(TAG, "Only one <uses-split> permitted. Ignoring others.");
                        } else {
                            usesSplitName = attrs.getAttributeValue(ANDROID_RESOURCES, "name");
                            if (usesSplitName == null) {
                                throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "<uses-split> tag requires 'android:name' attribute");
                            }
                            minSdkVersion2 = minSdkVersion;
                            searchDepth2 = searchDepth;
                            type2 = 1;
                        }
                    } else if (TAG_USES_SDK.equals(parser.getName())) {
                        minSdkVersion2 = minSdkVersion;
                        int i2 = 0;
                        while (i2 < attrs.getAttributeCount()) {
                            String attr3 = attrs.getAttributeName(i2);
                            int minSdkVersion3 = minSdkVersion2;
                            if ("targetSdkVersion".equals(attr3)) {
                                targetSdkVersion3 = attrs.getAttributeIntValue(i2, 0);
                            }
                            int minSdkVersion4 = "minSdkVersion".equals(attr3) ? attrs.getAttributeIntValue(i2, 1) : minSdkVersion3;
                            i2++;
                            minSdkVersion2 = minSdkVersion4;
                        }
                        searchDepth2 = searchDepth;
                        type2 = 1;
                    }
                } else {
                    searchDepth = searchDepth2;
                }
                type2 = 1;
                minSdkVersion2 = minSdkVersion;
                searchDepth2 = searchDepth;
            }
        }
        return new ApkLite(codePath, packageSplit.first, packageSplit.second, isFeatureSplit, configForSplit, usesSplitName, isSplitRequired, revisionCode2, versionCodeMajor, revisionCode, installLocation, verifiers, signingDetails, isFeatureSplit2, debuggable, multiArch, use32bitAbi, useEmbeddedDex, extractNativeLibs, isSplitRequired2, minSdkVersion, targetSdkVersion3);
    }

    private boolean parseBaseApkChild(Package parentPkg, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        String childPackageName = parser.getAttributeValue(null, "package");
        if (validateName(childPackageName, true, false) != null) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
            return false;
        } else if (childPackageName.equals(parentPkg.packageName)) {
            String message = "Child package name cannot be equal to parent package name: " + parentPkg.packageName;
            Slog.m50w(TAG, message);
            outError[0] = message;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        } else if (parentPkg.hasChildPackage(childPackageName)) {
            String message2 = "Duplicate child package:" + childPackageName;
            Slog.m50w(TAG, message2);
            outError[0] = message2;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        } else {
            Package childPkg = new Package(childPackageName);
            childPkg.mVersionCode = parentPkg.mVersionCode;
            childPkg.baseRevisionCode = parentPkg.baseRevisionCode;
            childPkg.mVersionName = parentPkg.mVersionName;
            childPkg.applicationInfo.targetSdkVersion = parentPkg.applicationInfo.targetSdkVersion;
            childPkg.applicationInfo.minSdkVersion = parentPkg.applicationInfo.minSdkVersion;
            Package childPkg2 = parseBaseApkCommon(childPkg, CHILD_PACKAGE_TAGS, res, parser, flags, outError);
            if (childPkg2 == null) {
                return false;
            }
            if (parentPkg.childPackages == null) {
                parentPkg.childPackages = new ArrayList<>();
            }
            parentPkg.childPackages.add(childPkg2);
            childPkg2.parentPackage = parentPkg;
            return true;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private Package parseBaseApk(String apkPath, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        String[] overlayPaths;
        try {
            Pair<String, String> packageSplit = parsePackageSplitNames(parser, parser);
            String pkgName = packageSplit.first;
            String splitName = packageSplit.second;
            if (!TextUtils.isEmpty(splitName)) {
                outError[0] = "Expected base APK, but found split " + splitName;
                this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
                return null;
            }
            if (this.mCallback != null && (overlayPaths = this.mCallback.getOverlayPaths(pkgName, apkPath)) != null && overlayPaths.length > 0) {
                for (String overlayPath : overlayPaths) {
                    res.getAssets().addOverlayPath(overlayPath);
                }
            }
            Package pkg = new Package(pkgName);
            TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifest);
            pkg.mVersionCode = sa.getInteger(1, 0);
            pkg.mVersionCodeMajor = sa.getInteger(11, 0);
            pkg.applicationInfo.setVersionCode(pkg.getLongVersionCode());
            pkg.baseRevisionCode = sa.getInteger(5, 0);
            pkg.mVersionName = sa.getNonConfigurationString(2, 0);
            if (pkg.mVersionName != null) {
                pkg.mVersionName = pkg.mVersionName.intern();
            }
            pkg.coreApp = parser.getAttributeBooleanValue(null, "coreApp", false);
            pkg.mCompileSdkVersion = sa.getInteger(9, 0);
            pkg.applicationInfo.compileSdkVersion = pkg.mCompileSdkVersion;
            pkg.mCompileSdkVersionCodename = sa.getNonConfigurationString(10, 0);
            if (pkg.mCompileSdkVersionCodename != null) {
                pkg.mCompileSdkVersionCodename = pkg.mCompileSdkVersionCodename.intern();
            }
            pkg.applicationInfo.compileSdkVersionCodename = pkg.mCompileSdkVersionCodename;
            sa.recycle();
            return parseBaseApkCommon(pkg, null, res, parser, flags, outError);
        } catch (PackageParserException e) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:272:0x0773, code lost:
        if (r18 != false) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x077b, code lost:
        if (r47.instrumentation.size() != 0) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x077d, code lost:
        r2 = 0;
        r52[0] = "<manifest> does not contain an <application> or <instrumentation>";
        r46.mParseError = android.content.p002pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x0787, code lost:
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x0788, code lost:
        r0 = android.content.p002pm.PackageParser.NEW_PERMISSIONS.length;
        r3 = null;
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x078e, code lost:
        if (r1 >= r0) goto L358;
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x0790, code lost:
        r4 = android.content.p002pm.PackageParser.NEW_PERMISSIONS[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x079a, code lost:
        if (r47.applicationInfo.targetSdkVersion < r4.sdkVersion) goto L294;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x07a5, code lost:
        if (r47.requestedPermissions.contains(r4.name) != false) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x07a7, code lost:
        if (r3 != null) goto L301;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x07a9, code lost:
        r3 = new java.lang.StringBuilder(128);
        r3.append(r47.packageName);
        r3.append(": compat added ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x07bc, code lost:
        r3.append(' ');
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x07c1, code lost:
        r3.append(r4.name);
        r47.requestedPermissions.add(r4.name);
        r47.implicitPermissions.add(r4.name);
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x07d4, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x07d7, code lost:
        if (r3 == null) goto L306;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x07d9, code lost:
        android.util.Slog.m54i(android.content.p002pm.PackageParser.TAG, r3.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x07e2, code lost:
        r1 = android.permission.PermissionManager.SPLIT_PERMISSIONS.size();
        r4 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x07e9, code lost:
        if (r4 >= r1) goto L325;
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x07eb, code lost:
        r5 = android.permission.PermissionManager.SPLIT_PERMISSIONS.get(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x07fb, code lost:
        if (r47.applicationInfo.targetSdkVersion >= r5.getTargetSdk()) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x0807, code lost:
        if (r47.requestedPermissions.contains(r5.getSplitPermission()) != false) goto L312;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x080d, code lost:
        r11 = r5.getNewPermissions();
        r13 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x0816, code lost:
        if (r13 >= r11.size()) goto L321;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x0818, code lost:
        r2 = r11.get(r13);
        r43 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:302:0x0826, code lost:
        if (r47.requestedPermissions.contains(r2) != false) goto L320;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x0828, code lost:
        r47.requestedPermissions.add(r2);
        r47.implicitPermissions.add(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x0832, code lost:
        r13 = r13 + 1;
        r0 = r43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x083a, code lost:
        r4 = r4 + 1;
        r0 = r0;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x0842, code lost:
        if (r15 < 0) goto L357;
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x0844, code lost:
        if (r15 <= 0) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x084b, code lost:
        if (r47.applicationInfo.targetSdkVersion < 4) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x084d, code lost:
        r47.applicationInfo.flags |= 512;
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x0855, code lost:
        if (r14 == 0) goto L333;
     */
    /* JADX WARN: Code restructure failed: missing block: B:314:0x0857, code lost:
        r47.applicationInfo.flags |= 1024;
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x085f, code lost:
        if (r8 < 0) goto L356;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x0861, code lost:
        if (r8 <= 0) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x0868, code lost:
        if (r47.applicationInfo.targetSdkVersion < 4) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x086a, code lost:
        r47.applicationInfo.flags |= 2048;
     */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x0872, code lost:
        if (r16 < 0) goto L355;
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x0874, code lost:
        if (r16 <= 0) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x087c, code lost:
        if (r47.applicationInfo.targetSdkVersion < 9) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x087e, code lost:
        r47.applicationInfo.flags |= 524288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x0887, code lost:
        if (r21 < 0) goto L354;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x0889, code lost:
        if (r21 <= 0) goto L345;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x0890, code lost:
        if (r47.applicationInfo.targetSdkVersion < 4) goto L345;
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x0892, code lost:
        r47.applicationInfo.flags |= 4096;
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:0x089a, code lost:
        if (r40 < 0) goto L353;
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x089c, code lost:
        if (r40 <= 0) goto L349;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x08a3, code lost:
        if (r47.applicationInfo.targetSdkVersion < 4) goto L349;
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x08a5, code lost:
        r47.applicationInfo.flags |= 8192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x08b3, code lost:
        if (r47.applicationInfo.usesCompatibilityMode() == false) goto L352;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x08b5, code lost:
        adjustPackageToBeUnresizeableAndUnpipable(r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x08b8, code lost:
        return r47;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Package parseBaseApkCommon(Package pkg, Set<String> acceptedTags, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        int supportsXLargeScreens;
        int supportsLargeScreens;
        int type;
        int resizeable;
        int anyDensity;
        int supportsSmallScreens;
        int outerDepth;
        int supportsXLargeScreens2;
        int supportsLargeScreens2;
        int type2;
        int i;
        int outerDepth2;
        int targetSandboxVersion;
        String str;
        int resizeable2;
        int anyDensity2;
        int supportsSmallScreens2;
        int supportsXLargeScreens3;
        int supportsLargeScreens3;
        int supportsXLargeScreens4;
        int anyDensity3;
        TypedArray sa;
        TypedArray sa2;
        int targetVers;
        int type3;
        int innerDepth;
        Set<String> set = acceptedTags;
        this.mParseInstrumentationArgs = null;
        TypedArray sa3 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifest);
        String str2 = sa3.getNonConfigurationString(0, 0);
        int i2 = 3;
        if (str2 != null && str2.length() > 0) {
            String nameError = validateName(str2, true, true);
            if (nameError != null && !"android".equals(pkg.packageName)) {
                outError[0] = "<manifest> specifies bad sharedUserId name \"" + str2 + "\": " + nameError;
                this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
                return null;
            }
            pkg.mSharedUserId = str2.intern();
            pkg.mSharedUserLabel = sa3.getResourceId(3, 0);
        }
        pkg.installLocation = sa3.getInteger(4, -1);
        pkg.applicationInfo.installLocation = pkg.installLocation;
        int targetSandboxVersion2 = sa3.getInteger(7, 1);
        pkg.applicationInfo.targetSandboxVersion = targetSandboxVersion2;
        if ((flags & 8) != 0) {
            pkg.applicationInfo.flags |= 262144;
        }
        if (sa3.getBoolean(6, false)) {
            pkg.applicationInfo.privateFlags |= 32768;
        }
        int outerDepth3 = parser.getDepth();
        int resizeable3 = 1;
        int anyDensity4 = 1;
        boolean foundApp = false;
        int supportsXLargeScreens5 = 1;
        TypedArray sa4 = sa3;
        int supportsLargeScreens4 = 1;
        int supportsSmallScreens3 = 1;
        int supportsNormalScreens = 1;
        while (true) {
            int outerDepth4 = outerDepth3;
            int type4 = parser.next();
            if (type4 == 1) {
                supportsXLargeScreens = supportsXLargeScreens5;
                supportsLargeScreens = supportsLargeScreens4;
                type = supportsNormalScreens;
                resizeable = resizeable3;
                anyDensity = anyDensity4;
                supportsSmallScreens = supportsSmallScreens3;
                break;
            }
            if (type4 == i2) {
                outerDepth = outerDepth4;
                if (parser.getDepth() <= outerDepth) {
                    supportsXLargeScreens = supportsXLargeScreens5;
                    supportsLargeScreens = supportsLargeScreens4;
                    resizeable = resizeable3;
                    anyDensity = anyDensity4;
                    type = supportsNormalScreens;
                    supportsSmallScreens = supportsSmallScreens3;
                    break;
                }
            } else {
                outerDepth = outerDepth4;
            }
            if (type4 != 3) {
                if (type4 == 4) {
                    supportsXLargeScreens2 = supportsXLargeScreens5;
                    supportsLargeScreens2 = supportsLargeScreens4;
                    type2 = supportsNormalScreens;
                    outerDepth2 = outerDepth;
                    targetSandboxVersion = targetSandboxVersion2;
                    str = str2;
                    resizeable2 = resizeable3;
                    anyDensity2 = anyDensity4;
                } else {
                    targetSandboxVersion = targetSandboxVersion2;
                    String tagName = parser.getName();
                    if (set == null || set.contains(tagName)) {
                        supportsXLargeScreens3 = supportsXLargeScreens5;
                        supportsLargeScreens3 = supportsLargeScreens4;
                        if (!tagName.equals(TAG_APPLICATION)) {
                            outerDepth2 = outerDepth;
                            str = str2;
                            supportsXLargeScreens4 = supportsXLargeScreens3;
                            supportsLargeScreens2 = supportsLargeScreens3;
                            int i3 = 1;
                            type2 = supportsNormalScreens;
                            supportsSmallScreens2 = supportsSmallScreens3;
                            if (tagName.equals("overlay")) {
                                TypedArray sa5 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestResourceOverlay);
                                pkg.mOverlayTarget = sa5.getString(1);
                                pkg.mOverlayTargetName = sa5.getString(3);
                                pkg.mOverlayCategory = sa5.getString(2);
                                pkg.mOverlayPriority = sa5.getInt(0, 0);
                                pkg.mOverlayIsStatic = sa5.getBoolean(4, false);
                                String propName = sa5.getString(5);
                                String propValue = sa5.getString(6);
                                sa5.recycle();
                                if (pkg.mOverlayTarget == null) {
                                    outError[0] = "<overlay> does not specify a target package";
                                    this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                    return null;
                                } else if (pkg.mOverlayPriority < 0 || pkg.mOverlayPriority > 9999) {
                                    break;
                                } else if (!checkOverlayRequiredSystemProperty(propName, propValue)) {
                                    Slog.m54i(TAG, "Skipping target and overlay pair " + pkg.mOverlayTarget + " and " + pkg.baseCodePath + ": overlay ignored due to required system property: " + propName + " with value: " + propValue);
                                    return null;
                                } else {
                                    pkg.applicationInfo.privateFlags |= 268435456;
                                    XmlUtils.skipCurrentTag(parser);
                                    sa4 = sa5;
                                }
                            } else {
                                if (tagName.equals(TAG_KEY_SETS)) {
                                    if (!parseKeySets(pkg, res, parser, outError)) {
                                        return null;
                                    }
                                } else if (tagName.equals(TAG_PERMISSION_GROUP)) {
                                    if (!parsePermissionGroup(pkg, flags, res, parser, outError)) {
                                        return null;
                                    }
                                    resizeable2 = resizeable3;
                                    anyDensity3 = anyDensity4;
                                    supportsXLargeScreens2 = supportsXLargeScreens4;
                                    i = 3;
                                    supportsLargeScreens4 = supportsLargeScreens2;
                                    supportsNormalScreens = type2;
                                    supportsSmallScreens3 = supportsSmallScreens2;
                                    supportsXLargeScreens4 = supportsXLargeScreens2;
                                    resizeable3 = resizeable2;
                                    anyDensity4 = anyDensity3;
                                    outerDepth3 = outerDepth2;
                                    i2 = i;
                                    targetSandboxVersion2 = targetSandboxVersion;
                                    str2 = str;
                                    supportsXLargeScreens5 = supportsXLargeScreens4;
                                    set = acceptedTags;
                                } else if (tagName.equals("permission")) {
                                    if (!parsePermission(pkg, res, parser, outError)) {
                                        return null;
                                    }
                                } else if (tagName.equals(TAG_PERMISSION_TREE)) {
                                    if (!parsePermissionTree(pkg, res, parser, outError)) {
                                        return null;
                                    }
                                } else if (!tagName.equals(TAG_USES_PERMISSION)) {
                                    if (tagName.equals(TAG_USES_PERMISSION_SDK_M)) {
                                        resizeable2 = resizeable3;
                                        anyDensity3 = anyDensity4;
                                        supportsXLargeScreens2 = supportsXLargeScreens4;
                                        i = 3;
                                    } else if (tagName.equals(TAG_USES_PERMISSION_SDK_23)) {
                                        resizeable2 = resizeable3;
                                        anyDensity3 = anyDensity4;
                                        supportsXLargeScreens2 = supportsXLargeScreens4;
                                        i = 3;
                                    } else if (tagName.equals(TAG_USES_CONFIGURATION)) {
                                        ConfigurationInfo cPref = new ConfigurationInfo();
                                        TypedArray sa6 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestUsesConfiguration);
                                        cPref.reqTouchScreen = sa6.getInt(0, 0);
                                        cPref.reqKeyboardType = sa6.getInt(1, 0);
                                        if (sa6.getBoolean(2, false)) {
                                            cPref.reqInputFeatures |= 1;
                                        }
                                        cPref.reqNavigation = sa6.getInt(3, 0);
                                        if (sa6.getBoolean(4, false)) {
                                            cPref.reqInputFeatures = 2 | cPref.reqInputFeatures;
                                        }
                                        sa6.recycle();
                                        pkg.configPreferences = ArrayUtils.add(pkg.configPreferences, cPref);
                                        XmlUtils.skipCurrentTag(parser);
                                        sa4 = sa6;
                                        i = 3;
                                        supportsLargeScreens4 = supportsLargeScreens2;
                                        supportsNormalScreens = type2;
                                        supportsSmallScreens3 = supportsSmallScreens2;
                                        outerDepth3 = outerDepth2;
                                        i2 = i;
                                        targetSandboxVersion2 = targetSandboxVersion;
                                        str2 = str;
                                        supportsXLargeScreens5 = supportsXLargeScreens4;
                                        set = acceptedTags;
                                    } else {
                                        int i4 = 4;
                                        int i5 = 3;
                                        if (tagName.equals(TAG_USES_FEATURE)) {
                                            FeatureInfo fi = parseUsesFeature(res, parser);
                                            pkg.reqFeatures = ArrayUtils.add(pkg.reqFeatures, fi);
                                            if (fi.name == null) {
                                                ConfigurationInfo cPref2 = new ConfigurationInfo();
                                                cPref2.reqGlEsVersion = fi.reqGlEsVersion;
                                                pkg.configPreferences = ArrayUtils.add(pkg.configPreferences, cPref2);
                                            }
                                            XmlUtils.skipCurrentTag(parser);
                                            i = 3;
                                            resizeable2 = resizeable3;
                                            anyDensity3 = anyDensity4;
                                            supportsXLargeScreens2 = supportsXLargeScreens4;
                                            supportsLargeScreens4 = supportsLargeScreens2;
                                            supportsNormalScreens = type2;
                                            supportsSmallScreens3 = supportsSmallScreens2;
                                            supportsXLargeScreens4 = supportsXLargeScreens2;
                                            resizeable3 = resizeable2;
                                            anyDensity4 = anyDensity3;
                                            outerDepth3 = outerDepth2;
                                            i2 = i;
                                            targetSandboxVersion2 = targetSandboxVersion;
                                            str2 = str;
                                            supportsXLargeScreens5 = supportsXLargeScreens4;
                                            set = acceptedTags;
                                        } else if (tagName.equals(TAG_FEATURE_GROUP)) {
                                            FeatureGroupInfo group = new FeatureGroupInfo();
                                            ArrayList<FeatureInfo> features = null;
                                            int innerDepth2 = parser.getDepth();
                                            while (true) {
                                                int type5 = parser.next();
                                                if (type5 == i3) {
                                                    type3 = type5;
                                                    break;
                                                }
                                                type3 = type5;
                                                if (type3 == i5 && parser.getDepth() <= innerDepth2) {
                                                    break;
                                                }
                                                if (type3 == i5) {
                                                    innerDepth = innerDepth2;
                                                } else if (type3 == i4) {
                                                    innerDepth = innerDepth2;
                                                } else {
                                                    String innerTagName = parser.getName();
                                                    if (innerTagName.equals(TAG_USES_FEATURE)) {
                                                        FeatureInfo featureInfo = parseUsesFeature(res, parser);
                                                        featureInfo.flags |= 1;
                                                        features = ArrayUtils.add(features, featureInfo);
                                                        innerDepth = innerDepth2;
                                                    } else {
                                                        StringBuilder sb = new StringBuilder();
                                                        innerDepth = innerDepth2;
                                                        sb.append("Unknown element under <feature-group>: ");
                                                        sb.append(innerTagName);
                                                        sb.append(" at ");
                                                        sb.append(this.mArchiveSourcePath);
                                                        sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                                                        sb.append(parser.getPositionDescription());
                                                        Slog.m50w(TAG, sb.toString());
                                                    }
                                                    XmlUtils.skipCurrentTag(parser);
                                                }
                                                innerDepth2 = innerDepth;
                                                i4 = 4;
                                                i5 = 3;
                                                i3 = 1;
                                            }
                                            if (features != null) {
                                                group.features = new FeatureInfo[features.size()];
                                                group.features = (FeatureInfo[]) features.toArray(group.features);
                                            }
                                            pkg.featureGroups = ArrayUtils.add(pkg.featureGroups, group);
                                        } else if (tagName.equals(TAG_USES_SDK)) {
                                            if (SDK_VERSION > 0) {
                                                TypedArray sa7 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestUsesSdk);
                                                int minVers = 1;
                                                String minCode = null;
                                                String targetCode = null;
                                                TypedValue val = sa7.peekValue(0);
                                                if (val != null) {
                                                    if (val.type != 3 || val.string == null) {
                                                        minVers = val.data;
                                                    } else {
                                                        minCode = val.string.toString();
                                                        minVers = 1;
                                                    }
                                                }
                                                TypedValue val2 = sa7.peekValue(1);
                                                if (val2 == null) {
                                                    targetVers = minVers;
                                                    targetCode = minCode;
                                                } else if (val2.type != 3 || val2.string == null) {
                                                    targetVers = val2.data;
                                                } else {
                                                    targetCode = val2.string.toString();
                                                    if (minCode == null) {
                                                        minCode = targetCode;
                                                    }
                                                    targetVers = 0;
                                                }
                                                sa7.recycle();
                                                int minSdkVersion = computeMinSdkVersion(minVers, minCode, SDK_VERSION, SDK_CODENAMES, outError);
                                                if (minSdkVersion < 0) {
                                                    this.mParseError = -12;
                                                    return null;
                                                }
                                                int targetSdkVersion = computeTargetSdkVersion(targetVers, targetCode, SDK_CODENAMES, outError);
                                                if (targetSdkVersion < 0) {
                                                    this.mParseError = -12;
                                                    return null;
                                                }
                                                pkg.applicationInfo.minSdkVersion = minSdkVersion;
                                                pkg.applicationInfo.targetSdkVersion = targetSdkVersion;
                                                sa4 = sa7;
                                            }
                                            XmlUtils.skipCurrentTag(parser);
                                        } else {
                                            if (tagName.equals(TAG_SUPPORT_SCREENS)) {
                                                TypedArray sa8 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestSupportsScreens);
                                                pkg.applicationInfo.requiresSmallestWidthDp = sa8.getInteger(6, 0);
                                                pkg.applicationInfo.compatibleWidthLimitDp = sa8.getInteger(7, 0);
                                                pkg.applicationInfo.largestWidthLimitDp = sa8.getInteger(8, 0);
                                                supportsSmallScreens3 = sa8.getInteger(1, supportsSmallScreens2);
                                                supportsNormalScreens = sa8.getInteger(2, type2);
                                                int supportsLargeScreens5 = sa8.getInteger(3, supportsLargeScreens2);
                                                int supportsXLargeScreens6 = sa8.getInteger(5, supportsXLargeScreens4);
                                                int supportsXLargeScreens7 = resizeable3;
                                                int resizeable4 = sa8.getInteger(4, supportsXLargeScreens7);
                                                int anyDensity5 = sa8.getInteger(0, anyDensity4);
                                                sa8.recycle();
                                                XmlUtils.skipCurrentTag(parser);
                                                sa4 = sa8;
                                                supportsXLargeScreens4 = supportsXLargeScreens6;
                                                supportsLargeScreens4 = supportsLargeScreens5;
                                                anyDensity4 = anyDensity5;
                                                resizeable3 = resizeable4;
                                            } else {
                                                resizeable2 = resizeable3;
                                                int anyDensity6 = anyDensity4;
                                                supportsXLargeScreens2 = supportsXLargeScreens4;
                                                if (tagName.equals(TAG_PROTECTED_BROADCAST)) {
                                                    sa2 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestProtectedBroadcast);
                                                    String name = sa2.getNonResourceString(0);
                                                    sa2.recycle();
                                                    if (name != null) {
                                                        if (pkg.protectedBroadcasts == null) {
                                                            pkg.protectedBroadcasts = new ArrayList<>();
                                                        }
                                                        if (!pkg.protectedBroadcasts.contains(name)) {
                                                            pkg.protectedBroadcasts.add(name.intern());
                                                        }
                                                    }
                                                    XmlUtils.skipCurrentTag(parser);
                                                } else if (tagName.equals(TAG_INSTRUMENTATION)) {
                                                    if (parseInstrumentation(pkg, res, parser, outError) == null) {
                                                        return null;
                                                    }
                                                    anyDensity3 = anyDensity6;
                                                    i = 3;
                                                    supportsLargeScreens4 = supportsLargeScreens2;
                                                    supportsNormalScreens = type2;
                                                    supportsSmallScreens3 = supportsSmallScreens2;
                                                    supportsXLargeScreens4 = supportsXLargeScreens2;
                                                    resizeable3 = resizeable2;
                                                    anyDensity4 = anyDensity3;
                                                    outerDepth3 = outerDepth2;
                                                    i2 = i;
                                                    targetSandboxVersion2 = targetSandboxVersion;
                                                    str2 = str;
                                                    supportsXLargeScreens5 = supportsXLargeScreens4;
                                                    set = acceptedTags;
                                                } else if (tagName.equals(TAG_ORIGINAL_PACKAGE)) {
                                                    sa2 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestOriginalPackage);
                                                    String orig = sa2.getNonConfigurationString(0, 0);
                                                    if (!pkg.packageName.equals(orig)) {
                                                        if (pkg.mOriginalPackages == null) {
                                                            pkg.mOriginalPackages = new ArrayList<>();
                                                            pkg.mRealPackage = pkg.packageName;
                                                        }
                                                        pkg.mOriginalPackages.add(orig);
                                                    }
                                                    sa2.recycle();
                                                    XmlUtils.skipCurrentTag(parser);
                                                } else if (tagName.equals(TAG_ADOPT_PERMISSIONS)) {
                                                    sa2 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestOriginalPackage);
                                                    String name2 = sa2.getNonConfigurationString(0, 0);
                                                    sa2.recycle();
                                                    if (name2 != null) {
                                                        if (pkg.mAdoptPermissions == null) {
                                                            pkg.mAdoptPermissions = new ArrayList<>();
                                                        }
                                                        pkg.mAdoptPermissions.add(name2);
                                                    }
                                                    XmlUtils.skipCurrentTag(parser);
                                                } else {
                                                    if (tagName.equals(TAG_USES_GL_TEXTURE)) {
                                                        XmlUtils.skipCurrentTag(parser);
                                                    } else if (tagName.equals(TAG_COMPATIBLE_SCREENS)) {
                                                        XmlUtils.skipCurrentTag(parser);
                                                    } else if (tagName.equals(TAG_SUPPORTS_INPUT)) {
                                                        XmlUtils.skipCurrentTag(parser);
                                                    } else if (tagName.equals(TAG_EAT_COMMENT)) {
                                                        XmlUtils.skipCurrentTag(parser);
                                                    } else if (!tagName.equals("package")) {
                                                        anyDensity2 = anyDensity6;
                                                        i = 3;
                                                        if (tagName.equals(TAG_RESTRICT_UPDATE)) {
                                                            if ((flags & 16) != 0) {
                                                                TypedArray sa9 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestRestrictUpdate);
                                                                String hash = sa9.getNonConfigurationString(0, 0);
                                                                sa9.recycle();
                                                                pkg.restrictUpdateHash = null;
                                                                if (hash != null) {
                                                                    int hashLength = hash.length();
                                                                    byte[] hashBytes = new byte[hashLength / 2];
                                                                    int i6 = 0;
                                                                    while (true) {
                                                                        int i7 = i6;
                                                                        if (i7 >= hashLength) {
                                                                            break;
                                                                        }
                                                                        hashBytes[i7 / 2] = (byte) ((Character.digit(hash.charAt(i7), 16) << 4) + Character.digit(hash.charAt(i7 + 1), 16));
                                                                        i6 = i7 + 2;
                                                                        sa9 = sa9;
                                                                    }
                                                                    sa = sa9;
                                                                    pkg.restrictUpdateHash = hashBytes;
                                                                } else {
                                                                    sa = sa9;
                                                                }
                                                            } else {
                                                                sa = sa4;
                                                            }
                                                            XmlUtils.skipCurrentTag(parser);
                                                            supportsLargeScreens4 = supportsLargeScreens2;
                                                            supportsNormalScreens = type2;
                                                            supportsSmallScreens3 = supportsSmallScreens2;
                                                            supportsXLargeScreens4 = supportsXLargeScreens2;
                                                            resizeable3 = resizeable2;
                                                            anyDensity4 = anyDensity2;
                                                            sa4 = sa;
                                                            outerDepth3 = outerDepth2;
                                                            i2 = i;
                                                            targetSandboxVersion2 = targetSandboxVersion;
                                                            str2 = str;
                                                            supportsXLargeScreens5 = supportsXLargeScreens4;
                                                            set = acceptedTags;
                                                        } else {
                                                            Slog.m50w(TAG, "Unknown element under <manifest>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                                            XmlUtils.skipCurrentTag(parser);
                                                        }
                                                    } else if (MULTI_PACKAGE_APK_ENABLED) {
                                                        anyDensity3 = anyDensity6;
                                                        i = 3;
                                                        if (!parseBaseApkChild(pkg, res, parser, flags, outError)) {
                                                            return null;
                                                        }
                                                        supportsLargeScreens4 = supportsLargeScreens2;
                                                        supportsNormalScreens = type2;
                                                        supportsSmallScreens3 = supportsSmallScreens2;
                                                        supportsXLargeScreens4 = supportsXLargeScreens2;
                                                        resizeable3 = resizeable2;
                                                        anyDensity4 = anyDensity3;
                                                        outerDepth3 = outerDepth2;
                                                        i2 = i;
                                                        targetSandboxVersion2 = targetSandboxVersion;
                                                        str2 = str;
                                                        supportsXLargeScreens5 = supportsXLargeScreens4;
                                                        set = acceptedTags;
                                                    } else {
                                                        XmlUtils.skipCurrentTag(parser);
                                                    }
                                                    anyDensity2 = anyDensity6;
                                                    i = 3;
                                                }
                                                sa4 = sa2;
                                                supportsLargeScreens4 = supportsLargeScreens2;
                                                anyDensity4 = anyDensity6;
                                                supportsNormalScreens = type2;
                                                supportsSmallScreens3 = supportsSmallScreens2;
                                                supportsXLargeScreens4 = supportsXLargeScreens2;
                                                resizeable3 = resizeable2;
                                            }
                                            i = 3;
                                            outerDepth3 = outerDepth2;
                                            i2 = i;
                                            targetSandboxVersion2 = targetSandboxVersion;
                                            str2 = str;
                                            supportsXLargeScreens5 = supportsXLargeScreens4;
                                            set = acceptedTags;
                                        }
                                    }
                                    if (!parseUsesPermission(pkg, res, parser)) {
                                        return null;
                                    }
                                    supportsLargeScreens4 = supportsLargeScreens2;
                                    supportsNormalScreens = type2;
                                    supportsSmallScreens3 = supportsSmallScreens2;
                                    supportsXLargeScreens4 = supportsXLargeScreens2;
                                    resizeable3 = resizeable2;
                                    anyDensity4 = anyDensity3;
                                    outerDepth3 = outerDepth2;
                                    i2 = i;
                                    targetSandboxVersion2 = targetSandboxVersion;
                                    str2 = str;
                                    supportsXLargeScreens5 = supportsXLargeScreens4;
                                    set = acceptedTags;
                                } else if (!parseUsesPermission(pkg, res, parser)) {
                                    return null;
                                }
                                resizeable2 = resizeable3;
                                anyDensity3 = anyDensity4;
                                supportsXLargeScreens2 = supportsXLargeScreens4;
                                i = 3;
                                supportsLargeScreens4 = supportsLargeScreens2;
                                supportsNormalScreens = type2;
                                supportsSmallScreens3 = supportsSmallScreens2;
                                supportsXLargeScreens4 = supportsXLargeScreens2;
                                resizeable3 = resizeable2;
                                anyDensity4 = anyDensity3;
                                outerDepth3 = outerDepth2;
                                i2 = i;
                                targetSandboxVersion2 = targetSandboxVersion;
                                str2 = str;
                                supportsXLargeScreens5 = supportsXLargeScreens4;
                                set = acceptedTags;
                            }
                            supportsLargeScreens4 = supportsLargeScreens2;
                            supportsNormalScreens = type2;
                            supportsSmallScreens3 = supportsSmallScreens2;
                        } else if (foundApp) {
                            Slog.m50w(TAG, "<manifest> has more than one <application>");
                            XmlUtils.skipCurrentTag(parser);
                        } else {
                            int type6 = supportsNormalScreens;
                            str = str2;
                            int supportsSmallScreens4 = supportsSmallScreens3;
                            supportsXLargeScreens4 = supportsXLargeScreens3;
                            outerDepth2 = outerDepth;
                            if (!parseBaseApplication(pkg, res, parser, flags, outError)) {
                                return null;
                            }
                            supportsLargeScreens4 = supportsLargeScreens3;
                            supportsNormalScreens = type6;
                            supportsSmallScreens3 = supportsSmallScreens4;
                            foundApp = true;
                        }
                        i = 3;
                        outerDepth3 = outerDepth2;
                        i2 = i;
                        targetSandboxVersion2 = targetSandboxVersion;
                        str2 = str;
                        supportsXLargeScreens5 = supportsXLargeScreens4;
                        set = acceptedTags;
                    } else {
                        supportsXLargeScreens3 = supportsXLargeScreens5;
                        StringBuilder sb2 = new StringBuilder();
                        supportsLargeScreens3 = supportsLargeScreens4;
                        sb2.append("Skipping unsupported element under <manifest>: ");
                        sb2.append(tagName);
                        sb2.append(" at ");
                        sb2.append(this.mArchiveSourcePath);
                        sb2.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                        sb2.append(parser.getPositionDescription());
                        Slog.m50w(TAG, sb2.toString());
                        XmlUtils.skipCurrentTag(parser);
                    }
                    type2 = supportsNormalScreens;
                    outerDepth2 = outerDepth;
                    str = str2;
                    resizeable2 = resizeable3;
                    anyDensity2 = anyDensity4;
                    supportsXLargeScreens2 = supportsXLargeScreens3;
                    supportsLargeScreens2 = supportsLargeScreens3;
                }
                i = 3;
                supportsSmallScreens2 = supportsSmallScreens3;
            } else {
                supportsXLargeScreens2 = supportsXLargeScreens5;
                supportsLargeScreens2 = supportsLargeScreens4;
                type2 = supportsNormalScreens;
                i = 3;
                outerDepth2 = outerDepth;
                targetSandboxVersion = targetSandboxVersion2;
                str = str2;
                resizeable2 = resizeable3;
                anyDensity2 = anyDensity4;
                supportsSmallScreens2 = supportsSmallScreens3;
            }
            supportsLargeScreens4 = supportsLargeScreens2;
            supportsNormalScreens = type2;
            supportsSmallScreens3 = supportsSmallScreens2;
            supportsXLargeScreens5 = supportsXLargeScreens2;
            outerDepth3 = outerDepth2;
            i2 = i;
            resizeable3 = resizeable2;
            targetSandboxVersion2 = targetSandboxVersion;
            str2 = str;
            anyDensity4 = anyDensity2;
            set = acceptedTags;
        }
        outError[0] = "<overlay> priority must be between 0 and 9999";
        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        return null;
    }

    private boolean checkOverlayRequiredSystemProperty(String propName, String propValue) {
        if (TextUtils.isEmpty(propName) || TextUtils.isEmpty(propValue)) {
            if (TextUtils.isEmpty(propName) && TextUtils.isEmpty(propValue)) {
                return true;
            }
            Slog.m50w(TAG, "Disabling overlay - incomplete property :'" + propName + "=" + propValue + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
            return false;
        }
        String currValue = SystemProperties.get(propName);
        return currValue != null && currValue.equals(propValue);
    }

    private void adjustPackageToBeUnresizeableAndUnpipable(Package pkg) {
        Iterator<Activity> it = pkg.activities.iterator();
        while (it.hasNext()) {
            Activity a = it.next();
            a.info.resizeMode = 0;
            a.info.flags &= -4194305;
        }
    }

    private static boolean matchTargetCode(String[] codeNames, String targetCode) {
        String targetCodeName;
        int targetCodeIdx = targetCode.indexOf(46);
        if (targetCodeIdx == -1) {
            targetCodeName = targetCode;
        } else {
            targetCodeName = targetCode.substring(0, targetCodeIdx);
        }
        return ArrayUtils.contains(codeNames, targetCodeName);
    }

    public static int computeTargetSdkVersion(int targetVers, String targetCode, String[] platformSdkCodenames, String[] outError) {
        if (targetCode == null) {
            return targetVers;
        }
        if (matchTargetCode(platformSdkCodenames, targetCode)) {
            return 10000;
        }
        if (platformSdkCodenames.length > 0) {
            outError[0] = "Requires development platform " + targetCode + " (current platform is any of " + Arrays.toString(platformSdkCodenames) + ")";
            return -1;
        }
        outError[0] = "Requires development platform " + targetCode + " but this is a release platform.";
        return -1;
    }

    public static int computeMinSdkVersion(int minVers, String minCode, int platformSdkVersion, String[] platformSdkCodenames, String[] outError) {
        if (minCode == null) {
            if (minVers <= platformSdkVersion) {
                return minVers;
            }
            outError[0] = "Requires newer sdk version #" + minVers + " (current version is #" + platformSdkVersion + ")";
            return -1;
        } else if (matchTargetCode(platformSdkCodenames, minCode)) {
            return 10000;
        } else {
            if (platformSdkCodenames.length > 0) {
                outError[0] = "Requires development platform " + minCode + " (current platform is any of " + Arrays.toString(platformSdkCodenames) + ")";
            } else {
                outError[0] = "Requires development platform " + minCode + " but this is a release platform.";
            }
            return -1;
        }
    }

    private FeatureInfo parseUsesFeature(Resources res, AttributeSet attrs) {
        FeatureInfo fi = new FeatureInfo();
        TypedArray sa = res.obtainAttributes(attrs, C3132R.styleable.AndroidManifestUsesFeature);
        fi.name = sa.getNonResourceString(0);
        fi.version = sa.getInt(3, 0);
        if (fi.name == null) {
            fi.reqGlEsVersion = sa.getInt(1, 0);
        }
        if (sa.getBoolean(2, true)) {
            fi.flags |= 1;
        }
        sa.recycle();
        return fi;
    }

    private boolean parseUsesStaticLibrary(Package pkg, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestUsesStaticLibrary);
        String lname = sa.getNonResourceString(0);
        int version = sa.getInt(1, -1);
        String certSha256Digest = sa.getNonResourceString(2);
        sa.recycle();
        if (lname == null || version < 0 || certSha256Digest == null) {
            outError[0] = "Bad uses-static-library declaration name: " + lname + " version: " + version + " certDigest" + certSha256Digest;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            XmlUtils.skipCurrentTag(parser);
            return false;
        } else if (pkg.usesStaticLibraries != null && pkg.usesStaticLibraries.contains(lname)) {
            outError[0] = "Depending on multiple versions of static library " + lname;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            XmlUtils.skipCurrentTag(parser);
            return false;
        } else {
            String lname2 = lname.intern();
            String certSha256Digest2 = certSha256Digest.replace(SettingsStringUtil.DELIMITER, "").toLowerCase();
            String[] additionalCertSha256Digests = EmptyArray.STRING;
            if (pkg.applicationInfo.targetSdkVersion >= 27) {
                additionalCertSha256Digests = parseAdditionalCertificates(res, parser, outError);
                if (additionalCertSha256Digests == null) {
                    return false;
                }
            } else {
                XmlUtils.skipCurrentTag(parser);
            }
            String[] certSha256Digests = new String[additionalCertSha256Digests.length + 1];
            certSha256Digests[0] = certSha256Digest2;
            System.arraycopy(additionalCertSha256Digests, 0, certSha256Digests, 1, additionalCertSha256Digests.length);
            pkg.usesStaticLibraries = ArrayUtils.add(pkg.usesStaticLibraries, lname2);
            pkg.usesStaticLibrariesVersions = ArrayUtils.appendLong(pkg.usesStaticLibrariesVersions, version, true);
            pkg.usesStaticLibrariesCertDigests = (String[][]) ArrayUtils.appendElement(String[].class, pkg.usesStaticLibrariesCertDigests, certSha256Digests, true);
            return true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0076, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String[] parseAdditionalCertificates(Resources resources, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        String[] certSha256Digests = EmptyArray.STRING;
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (type != 3 && type != 4) {
                String nodeName = parser.getName();
                if (nodeName.equals("additional-certificate")) {
                    TypedArray sa = resources.obtainAttributes(parser, C3132R.styleable.AndroidManifestAdditionalCertificate);
                    String certSha256Digest = sa.getNonResourceString(0);
                    sa.recycle();
                    if (TextUtils.isEmpty(certSha256Digest)) {
                        outError[0] = "Bad additional-certificate declaration with empty certDigest:" + certSha256Digest;
                        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        XmlUtils.skipCurrentTag(parser);
                        sa.recycle();
                        return null;
                    }
                    certSha256Digests = (String[]) ArrayUtils.appendElement(String.class, certSha256Digests, certSha256Digest.replace(SettingsStringUtil.DELIMITER, "").toLowerCase());
                } else {
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
    }

    private boolean parseUsesPermission(Package pkg, Resources res, XmlResourceParser parser) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestUsesPermission);
        String name = sa.getNonResourceString(0);
        int maxSdkVersion = 0;
        TypedValue val = sa.peekValue(1);
        if (val != null && val.type >= 16 && val.type <= 31) {
            maxSdkVersion = val.data;
        }
        String requiredFeature = sa.getNonConfigurationString(2, 0);
        String requiredNotfeature = sa.getNonConfigurationString(3, 0);
        sa.recycle();
        XmlUtils.skipCurrentTag(parser);
        if (name == null) {
            return true;
        }
        if (maxSdkVersion != 0 && maxSdkVersion < Build.VERSION.RESOURCES_SDK_INT) {
            return true;
        }
        if (requiredFeature != null && this.mCallback != null && !this.mCallback.hasFeature(requiredFeature)) {
            return true;
        }
        if (requiredNotfeature != null && this.mCallback != null && this.mCallback.hasFeature(requiredNotfeature)) {
            return true;
        }
        int index = pkg.requestedPermissions.indexOf(name);
        if (index == -1) {
            pkg.requestedPermissions.add(name.intern());
        } else {
            Slog.m50w(TAG, "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + name + " in package: " + pkg.packageName + " at: " + parser.getPositionDescription());
        }
        return true;
    }

    private static String buildClassName(String pkg, CharSequence clsSeq, String[] outError) {
        if (clsSeq == null || clsSeq.length() <= 0) {
            outError[0] = "Empty class name in package " + pkg;
            return null;
        }
        String cls = clsSeq.toString();
        char c = cls.charAt(0);
        if (c == '.') {
            return pkg + cls;
        } else if (cls.indexOf(46) < 0) {
            return pkg + '.' + cls;
        } else {
            return cls;
        }
    }

    private static String buildCompoundName(String pkg, CharSequence procSeq, String type, String[] outError) {
        String proc = procSeq.toString();
        char c = proc.charAt(0);
        if (pkg != null && c == ':') {
            if (proc.length() < 2) {
                outError[0] = "Bad " + type + " name " + proc + " in package " + pkg + ": must be at least two characters";
                return null;
            }
            String subName = proc.substring(1);
            String nameError = validateName(subName, false, false);
            if (nameError != null) {
                outError[0] = "Invalid " + type + " name " + proc + " in package " + pkg + PluralRules.KEYWORD_RULE_SEPARATOR + nameError;
                return null;
            }
            return pkg + proc;
        }
        String nameError2 = validateName(proc, true, false);
        if (nameError2 != null && !"system".equals(proc)) {
            outError[0] = "Invalid " + type + " name " + proc + " in package " + pkg + PluralRules.KEYWORD_RULE_SEPARATOR + nameError2;
            return null;
        }
        return proc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String buildProcessName(String pkg, String defProc, CharSequence procSeq, int flags, String[] separateProcesses, String[] outError) {
        if ((flags & 2) != 0 && !"system".equals(procSeq)) {
            return defProc != null ? defProc : pkg;
        }
        if (separateProcesses != null) {
            for (int i = separateProcesses.length - 1; i >= 0; i--) {
                String sp = separateProcesses[i];
                if (sp.equals(pkg) || sp.equals(defProc) || sp.equals(procSeq)) {
                    return pkg;
                }
            }
        }
        if (procSeq == null || procSeq.length() <= 0) {
            return defProc;
        }
        return TextUtils.safeIntern(buildCompoundName(pkg, procSeq, DumpHeapActivity.KEY_PROCESS, outError));
    }

    private static String buildTaskAffinityName(String pkg, String defProc, CharSequence procSeq, String[] outError) {
        if (procSeq == null) {
            return defProc;
        }
        if (procSeq.length() <= 0) {
            return null;
        }
        return buildCompoundName(pkg, procSeq, "taskAffinity", outError);
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0208, code lost:
        r1 = r7.keySet();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0214, code lost:
        if (r1.removeAll(r9.keySet()) == false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0216, code lost:
        r26[0] = "Package" + r23.packageName + " AndroidManifext.xml 'key-set' and 'public-key' names must be distinct.";
        r22.mParseError = android.content.p002pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0237, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0238, code lost:
        r23.mKeySetMapping = new android.util.ArrayMap<>();
        r4 = r9.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x024d, code lost:
        if (r4.hasNext() == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x024f, code lost:
        r11 = r4.next();
        r12 = r11.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0265, code lost:
        if (r11.getValue().size() != 0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0267, code lost:
        r14 = new java.lang.StringBuilder();
        r20 = r1;
        r14.append("Package");
        r14.append(r23.packageName);
        r14.append(" AndroidManifext.xml 'key-set' ");
        r14.append(r12);
        r14.append(" has no valid associated 'public-key'. Not including in package's defined key-sets.");
        android.util.Slog.m50w(android.content.p002pm.PackageParser.TAG, r14.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x028f, code lost:
        r1 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0292, code lost:
        r20 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0298, code lost:
        if (r10.contains(r12) == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x029a, code lost:
        android.util.Slog.m50w(android.content.p002pm.PackageParser.TAG, "Package" + r23.packageName + " AndroidManifext.xml 'key-set' " + r12 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x02c0, code lost:
        r23.mKeySetMapping.put(r12, new android.util.ArraySet<>());
        r1 = r11.getValue().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x02d8, code lost:
        if (r1.hasNext() == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x02da, code lost:
        r13 = r1.next();
        r23.mKeySetMapping.get(r12).add(r7.get(r13));
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0306, code lost:
        if (r23.mKeySetMapping.keySet().containsAll(r8) == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0308, code lost:
        r23.mUpgradeKeySets = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x030b, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x030c, code lost:
        r26[0] = "Package" + r23.packageName + " AndroidManifext.xml does not define all 'upgrade-key-set's .";
        r22.mParseError = android.content.p002pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x032b, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseKeySets(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        int outerDepth;
        int currentKeySetDepth;
        int outerDepth2 = parser.getDepth();
        int currentKeySetDepth2 = -1;
        String currentKeySet = null;
        ArrayMap<String, PublicKey> publicKeys = new ArrayMap<>();
        ArraySet<String> upgradeKeySets = new ArraySet<>();
        ArrayMap<String, ArraySet<String>> definedKeySets = new ArrayMap<>();
        ArraySet<String> improperKeySets = new ArraySet<>();
        while (true) {
            int type = parser.next();
            if (type != 1 && (type != 3 || parser.getDepth() > outerDepth2)) {
                if (type != 3) {
                    String tagName = parser.getName();
                    if (!tagName.equals("key-set")) {
                        outerDepth = outerDepth2;
                        if (!tagName.equals("public-key")) {
                            currentKeySetDepth = currentKeySetDepth2;
                            if (tagName.equals("upgrade-key-set")) {
                                TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestUpgradeKeySet);
                                String name = sa.getNonResourceString(0);
                                upgradeKeySets.add(name);
                                sa.recycle();
                                XmlUtils.skipCurrentTag(parser);
                                currentKeySetDepth2 = currentKeySetDepth;
                            } else {
                                Slog.m50w(TAG, "Unknown element under <key-sets>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                XmlUtils.skipCurrentTag(parser);
                                outerDepth2 = outerDepth;
                                currentKeySetDepth2 = currentKeySetDepth;
                            }
                        } else if (currentKeySet == null) {
                            outError[0] = "Improperly nested 'key-set' tag at " + parser.getPositionDescription();
                            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return false;
                        } else {
                            TypedArray sa2 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestPublicKey);
                            String publicKeyName = sa2.getNonResourceString(0);
                            String encodedKey = sa2.getNonResourceString(1);
                            if (encodedKey == null && publicKeys.get(publicKeyName) == null) {
                                outError[0] = "'public-key' " + publicKeyName + " must define a public-key value on first use at " + parser.getPositionDescription();
                                this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                sa2.recycle();
                                return false;
                            }
                            currentKeySetDepth = currentKeySetDepth2;
                            if (encodedKey != null) {
                                PublicKey currentKey = parsePublicKey(encodedKey);
                                if (currentKey == null) {
                                    Slog.m50w(TAG, "No recognized valid key in 'public-key' tag at " + parser.getPositionDescription() + " key-set " + currentKeySet + " will not be added to the package's defined key-sets.");
                                    sa2.recycle();
                                    improperKeySets.add(currentKeySet);
                                    XmlUtils.skipCurrentTag(parser);
                                    outerDepth2 = outerDepth;
                                    currentKeySetDepth2 = currentKeySetDepth;
                                } else if (publicKeys.get(publicKeyName) != null && !publicKeys.get(publicKeyName).equals(currentKey)) {
                                    outError[0] = "Value of 'public-key' " + publicKeyName + " conflicts with previously defined value at " + parser.getPositionDescription();
                                    this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                    sa2.recycle();
                                    return false;
                                } else {
                                    publicKeys.put(publicKeyName, currentKey);
                                }
                            }
                            definedKeySets.get(currentKeySet).add(publicKeyName);
                            sa2.recycle();
                            XmlUtils.skipCurrentTag(parser);
                            currentKeySetDepth2 = currentKeySetDepth;
                        }
                    } else if (currentKeySet != null) {
                        outError[0] = "Improperly nested 'key-set' tag at " + parser.getPositionDescription();
                        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        return false;
                    } else {
                        TypedArray sa3 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestKeySet);
                        String keysetName = sa3.getNonResourceString(0);
                        outerDepth = outerDepth2;
                        definedKeySets.put(keysetName, new ArraySet<>());
                        currentKeySetDepth2 = parser.getDepth();
                        sa3.recycle();
                        currentKeySet = keysetName;
                    }
                    outerDepth2 = outerDepth;
                } else if (parser.getDepth() == currentKeySetDepth2) {
                    currentKeySet = null;
                    currentKeySetDepth2 = -1;
                } else {
                    outerDepth = outerDepth2;
                    currentKeySetDepth = currentKeySetDepth2;
                    outerDepth2 = outerDepth;
                    currentKeySetDepth2 = currentKeySetDepth;
                }
            }
        }
    }

    private boolean parsePermissionGroup(Package owner, int flags, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestPermissionGroup);
        int requestDetailResourceId = sa.getResourceId(12, 0);
        int backgroundRequestResourceId = sa.getResourceId(9, 0);
        int backgroundRequestDetailResourceId = sa.getResourceId(10, 0);
        PermissionGroup perm = new PermissionGroup(owner, requestDetailResourceId, backgroundRequestResourceId, backgroundRequestDetailResourceId);
        if (!parsePackageItemInfo(owner, perm.info, outError, "<permission-group>", sa, true, 2, 0, 1, 8, 5, 7)) {
            sa.recycle();
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        perm.info.descriptionRes = sa.getResourceId(4, 0);
        perm.info.requestRes = sa.getResourceId(11, 0);
        perm.info.flags = sa.getInt(6, 0);
        perm.info.priority = sa.getInt(3, 0);
        sa.recycle();
        if (parseAllMetaData(res, parser, "<permission-group>", perm, outError)) {
            owner.permissionGroups.add(perm);
            return true;
        }
        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        return false;
    }

    private boolean parsePermission(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestPermission);
        String backgroundPermission = null;
        if (sa.hasValue(10)) {
            if ("android".equals(owner.packageName)) {
                backgroundPermission = sa.getNonResourceString(10);
            } else {
                Slog.m50w(TAG, owner.packageName + " defines a background permission. Only the 'android' package can do that.");
            }
        }
        Permission perm = new Permission(owner, backgroundPermission);
        if (!parsePackageItemInfo(owner, perm.info, outError, "<permission>", sa, true, 2, 0, 1, 9, 6, 8)) {
            sa.recycle();
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        perm.info.group = sa.getNonResourceString(4);
        if (perm.info.group != null) {
            perm.info.group = perm.info.group.intern();
        }
        perm.info.descriptionRes = sa.getResourceId(5, 0);
        perm.info.requestRes = sa.getResourceId(11, 0);
        perm.info.protectionLevel = sa.getInt(3, 0);
        perm.info.flags = sa.getInt(7, 0);
        if (!perm.info.isRuntime() || !"android".equals(perm.info.packageName)) {
            perm.info.flags &= -5;
            perm.info.flags &= -9;
        } else if ((perm.info.flags & 4) != 0 && (perm.info.flags & 8) != 0) {
            throw new IllegalStateException("Permission cannot be both soft and hard restricted: " + perm.info.name);
        }
        sa.recycle();
        if (perm.info.protectionLevel == -1) {
            outError[0] = "<permission> does not specify protectionLevel";
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        perm.info.protectionLevel = PermissionInfo.fixProtectionLevel(perm.info.protectionLevel);
        if (perm.info.getProtectionFlags() != 0 && (perm.info.protectionLevel & 4096) == 0 && (perm.info.protectionLevel & 8192) == 0 && (perm.info.protectionLevel & 15) != 2) {
            outError[0] = "<permission>  protectionLevel specifies a non-instant flag but is not based on signature type";
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        } else if (!parseAllMetaData(res, parser, "<permission>", perm, outError)) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        } else {
            owner.permissions.add(perm);
            return true;
        }
    }

    private boolean parsePermissionTree(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        Permission perm = new Permission(owner, (String) null);
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestPermissionTree);
        if (!parsePackageItemInfo(owner, perm.info, outError, "<permission-tree>", sa, true, 2, 0, 1, 5, 3, 4)) {
            sa.recycle();
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        sa.recycle();
        int index = perm.info.name.indexOf(46);
        if (index > 0) {
            index = perm.info.name.indexOf(46, index + 1);
        }
        if (index < 0) {
            outError[0] = "<permission-tree> name has less than three segments: " + perm.info.name;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        perm.info.descriptionRes = 0;
        perm.info.requestRes = 0;
        perm.info.protectionLevel = 0;
        perm.tree = true;
        if (!parseAllMetaData(res, parser, "<permission-tree>", perm, outError)) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        owner.permissions.add(perm);
        return true;
    }

    private Instrumentation parseInstrumentation(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestInstrumentation);
        if (this.mParseInstrumentationArgs == null) {
            this.mParseInstrumentationArgs = new ParsePackageItemArgs(owner, outError, 2, 0, 1, 8, 6, 7);
            this.mParseInstrumentationArgs.tag = "<instrumentation>";
        }
        this.mParseInstrumentationArgs.f29sa = sa;
        Instrumentation a = new Instrumentation(this.mParseInstrumentationArgs, new InstrumentationInfo());
        if (outError[0] != null) {
            sa.recycle();
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        }
        String str = sa.getNonResourceString(3);
        a.info.targetPackage = str != null ? str.intern() : null;
        String str2 = sa.getNonResourceString(9);
        a.info.targetProcesses = str2 != null ? str2.intern() : null;
        a.info.handleProfiling = sa.getBoolean(4, false);
        a.info.functionalTest = sa.getBoolean(5, false);
        sa.recycle();
        if (a.info.targetPackage == null) {
            outError[0] = "<instrumentation> does not specify targetPackage";
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        } else if (!parseAllMetaData(res, parser, "<instrumentation>", a, outError)) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        } else {
            owner.instrumentation.add(a);
            return a;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:282:0x064f, code lost:
        r9[0] = "Bad static-library declaration name: " + r10 + " version: " + r13;
        r0.mParseError = android.content.p002pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        com.android.internal.util.XmlUtils.skipCurrentTag(r38);
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x0672, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x0786, code lost:
        if (android.text.TextUtils.isEmpty(r14.staticSharedLibName) == false) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x0788, code lost:
        r14.activities.add(r0.generateAppDetailsHiddenActivity(r14, r39, r9, r14.baseHardwareAccelerated));
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x0798, code lost:
        if (r23 == 0) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x079a, code lost:
        java.util.Collections.sort(r14.activities, android.content.p002pm.$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4.INSTANCE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:0x07a1, code lost:
        if (r24 == false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x07a3, code lost:
        java.util.Collections.sort(r14.receivers, android.content.p002pm.$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw.INSTANCE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x07aa, code lost:
        if (r26 == false) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x07ac, code lost:
        java.util.Collections.sort(r14.services, android.content.p002pm.$$Lambda$PackageParser$M9fHqS_eEp1oYkuKJhRHOGUxf8.INSTANCE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x07b3, code lost:
        setMaxAspectRatio(r36);
        setMinAspectRatio(r36);
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x07bd, code lost:
        if (hasDomainURLs(r36) == false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x07bf, code lost:
        r14.applicationInfo.privateFlags |= 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x07c8, code lost:
        r14.applicationInfo.privateFlags &= -17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x07d0, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:?, code lost:
        return true;
     */
    /* JADX WARN: Type inference failed for: r1v105 */
    /* JADX WARN: Type inference failed for: r1v68 */
    /* JADX WARN: Type inference failed for: r1v69, types: [boolean] */
    @UnsupportedAppUsage
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseBaseApplication(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        String pkgName;
        String pkgName2;
        int i;
        int i2;
        ?? r1;
        String[] strArr;
        PackageParser packageParser;
        int innerDepth;
        ApplicationInfo ai;
        String str;
        String[] strArr2;
        PackageParser packageParser2;
        String str2;
        XmlResourceParser xmlResourceParser;
        TypedArray sa;
        String requiredFeature;
        PackageParser packageParser3 = this;
        Package r14 = owner;
        XmlResourceParser xmlResourceParser2 = parser;
        ApplicationInfo ai2 = r14.applicationInfo;
        String pkgName3 = r14.applicationInfo.packageName;
        TypedArray sa2 = res.obtainAttributes(xmlResourceParser2, C3132R.styleable.AndroidManifestApplication);
        ai2.iconRes = sa2.getResourceId(2, 0);
        ai2.roundIconRes = sa2.getResourceId(42, 0);
        String[] strArr3 = outError;
        if (!parsePackageItemInfo(owner, ai2, outError, "<application>", sa2, false, 3, 1, 2, 42, 22, 30)) {
            sa2.recycle();
            packageParser3.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        ApplicationInfo ai3 = ai2;
        if (ai3.name != null) {
            ai3.className = ai3.name;
        }
        String manageSpaceActivity = sa2.getNonConfigurationString(4, 1024);
        if (manageSpaceActivity != null) {
            pkgName = pkgName3;
            ai3.manageSpaceActivityName = buildClassName(pkgName, manageSpaceActivity, strArr3);
        } else {
            pkgName = pkgName3;
        }
        boolean allowBackup = sa2.getBoolean(17, true);
        if (allowBackup) {
            ai3.flags |= 32768;
            String backupAgent = sa2.getNonConfigurationString(16, 1024);
            if (backupAgent != null) {
                ai3.backupAgentName = buildClassName(pkgName, backupAgent, strArr3);
                if (sa2.getBoolean(18, true)) {
                    ai3.flags |= 65536;
                }
                if (sa2.getBoolean(21, false)) {
                    ai3.flags |= 131072;
                }
                if (sa2.getBoolean(32, false)) {
                    ai3.flags |= 67108864;
                }
                if (sa2.getBoolean(40, false)) {
                    ai3.privateFlags |= 8192;
                }
            }
            TypedValue v = sa2.peekValue(35);
            if (v != null) {
                int i3 = v.resourceId;
                ai3.fullBackupContent = i3;
                if (i3 == 0) {
                    ai3.fullBackupContent = v.data == 0 ? -1 : 0;
                }
            }
        }
        ai3.theme = sa2.getResourceId(0, 0);
        ai3.descriptionRes = sa2.getResourceId(13, 0);
        if (sa2.getBoolean(8, false) && ((requiredFeature = sa2.getNonResourceString(45)) == null || packageParser3.mCallback.hasFeature(requiredFeature))) {
            ai3.flags |= 8;
        }
        if (sa2.getBoolean(27, false)) {
            r14.mRequiredForAllUsers = true;
        }
        String restrictedAccountType = sa2.getString(28);
        if (restrictedAccountType != null && restrictedAccountType.length() > 0) {
            r14.mRestrictedAccountType = restrictedAccountType;
        }
        String requiredAccountType = sa2.getString(29);
        if (requiredAccountType != null && requiredAccountType.length() > 0) {
            r14.mRequiredAccountType = requiredAccountType;
        }
        if (sa2.getBoolean(10, false)) {
            ai3.flags |= 2;
            ai3.privateFlags |= 8388608;
        }
        if (sa2.getBoolean(20, false)) {
            ai3.flags |= 16384;
        }
        r14.baseHardwareAccelerated = sa2.getBoolean(23, r14.applicationInfo.targetSdkVersion >= 14);
        if (r14.baseHardwareAccelerated) {
            ai3.flags |= 536870912;
        }
        if (sa2.getBoolean(7, true)) {
            ai3.flags |= 4;
        }
        if (sa2.getBoolean(14, false)) {
            ai3.flags |= 32;
        }
        if (sa2.getBoolean(5, true)) {
            ai3.flags |= 64;
        }
        if (r14.parentPackage == null && sa2.getBoolean(15, false)) {
            ai3.flags |= 256;
        }
        if (sa2.getBoolean(24, false)) {
            ai3.flags |= 1048576;
        }
        if (sa2.getBoolean(36, r14.applicationInfo.targetSdkVersion < 28)) {
            ai3.flags |= 134217728;
        }
        if (sa2.getBoolean(26, false)) {
            ai3.flags |= 4194304;
        }
        if (sa2.getBoolean(33, false)) {
            ai3.flags |= Integer.MIN_VALUE;
        }
        if (sa2.getBoolean(34, true)) {
            ai3.flags |= 268435456;
        }
        if (sa2.getBoolean(53, false)) {
            ai3.privateFlags |= 33554432;
        }
        if (sa2.getBoolean(38, false)) {
            ai3.privateFlags |= 32;
        }
        if (sa2.getBoolean(39, false)) {
            ai3.privateFlags |= 64;
        }
        if (sa2.hasValueOrEmpty(37)) {
            if (sa2.getBoolean(37, true)) {
                ai3.privateFlags |= 1024;
            } else {
                ai3.privateFlags |= 2048;
            }
        } else if (r14.applicationInfo.targetSdkVersion >= 24) {
            ai3.privateFlags |= 4096;
        }
        if (sa2.getBoolean(54, true)) {
            ai3.privateFlags |= 67108864;
        }
        if (sa2.getBoolean(55, r14.applicationInfo.targetSdkVersion >= 29)) {
            ai3.privateFlags |= 134217728;
        }
        if (sa2.getBoolean(56, r14.applicationInfo.targetSdkVersion < 29)) {
            ai3.privateFlags |= 536870912;
        }
        ai3.maxAspectRatio = sa2.getFloat(44, 0.0f);
        ai3.minAspectRatio = sa2.getFloat(51, 0.0f);
        ai3.networkSecurityConfigRes = sa2.getResourceId(41, 0);
        ai3.category = sa2.getInt(43, -1);
        String str3 = sa2.getNonConfigurationString(6, 0);
        ai3.permission = (str3 == null || str3.length() <= 0) ? null : str3.intern();
        String str4 = r14.applicationInfo.targetSdkVersion >= 8 ? sa2.getNonConfigurationString(12, 1024) : sa2.getNonResourceString(12);
        ai3.taskAffinity = buildTaskAffinityName(ai3.packageName, ai3.packageName, str4, strArr3);
        String factory = sa2.getNonResourceString(48);
        if (factory != null) {
            ai3.appComponentFactory = buildClassName(ai3.packageName, factory, strArr3);
        }
        if (sa2.getBoolean(49, false)) {
            ai3.privateFlags |= 4194304;
        }
        if (sa2.getBoolean(50, false)) {
            ai3.privateFlags |= 16777216;
        }
        if (strArr3[0] == null) {
            CharSequence pname = r14.applicationInfo.targetSdkVersion >= 8 ? sa2.getNonConfigurationString(11, 1024) : sa2.getNonResourceString(11);
            i2 = 1;
            pkgName2 = pkgName;
            ai3.processName = buildProcessName(ai3.packageName, null, pname, flags, packageParser3.mSeparateProcesses, outError);
            ai3.enabled = sa2.getBoolean(9, true);
            if (sa2.getBoolean(31, false)) {
                ai3.flags |= 33554432;
            }
            if (sa2.getBoolean(47, false)) {
                ai3.privateFlags |= 2;
                if (ai3.processName == null || ai3.processName.equals(ai3.packageName)) {
                    i = 0;
                } else {
                    i = 0;
                    strArr3[0] = "cantSaveState applications can not use custom processes";
                }
            } else {
                i = 0;
            }
        } else {
            pkgName2 = pkgName;
            i = 0;
            i2 = 1;
        }
        ai3.uiOptions = sa2.getInt(25, i);
        ai3.classLoaderName = sa2.getString(46);
        if (ai3.classLoaderName == null || ClassLoaderFactory.isValidClassLoaderName(ai3.classLoaderName)) {
            r1 = 0;
        } else {
            r1 = 0;
            strArr3[0] = "Invalid class loader name: " + ai3.classLoaderName;
        }
        ai3.zygotePreloadName = sa2.getString(52);
        sa2.recycle();
        if (strArr3[r1] == null) {
            int innerDepth2 = parser.getDepth();
            CachedComponentArgs cachedArgs = new CachedComponentArgs();
            int i4 = 0;
            boolean hasReceiverOrder = false;
            boolean hasServiceOrder = false;
            while (true) {
                boolean hasServiceOrder2 = hasServiceOrder;
                int type = parser.next();
                if (type == i2) {
                    strArr = strArr3;
                    packageParser = packageParser3;
                    break;
                } else if (type == 3 && parser.getDepth() <= innerDepth2) {
                    strArr = strArr3;
                    packageParser = packageParser3;
                    break;
                } else {
                    if (type == 3) {
                        innerDepth = innerDepth2;
                        ai = ai3;
                        str = str4;
                        strArr2 = strArr3;
                        packageParser2 = packageParser3;
                        str2 = pkgName2;
                        xmlResourceParser = xmlResourceParser2;
                    } else if (type == 4) {
                        innerDepth = innerDepth2;
                        ai = ai3;
                        str = str4;
                        strArr2 = strArr3;
                        packageParser2 = packageParser3;
                        str2 = pkgName2;
                        xmlResourceParser = xmlResourceParser2;
                    } else {
                        String tagName = parser.getName();
                        if (tagName.equals(Context.ACTIVITY_SERVICE)) {
                            innerDepth = innerDepth2;
                            String pkgName4 = pkgName2;
                            Activity a = parseActivity(owner, res, parser, flags, outError, cachedArgs, false, r14.baseHardwareAccelerated);
                            if (a == null) {
                                packageParser3.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                return false;
                            }
                            int i5 = a.order != 0 ? i2 : 0;
                            r14.activities.add(a);
                            i4 |= i5;
                            ai = ai3;
                            str = str4;
                            xmlResourceParser = xmlResourceParser2;
                            strArr2 = strArr3;
                            packageParser2 = packageParser3;
                            str2 = pkgName4;
                        } else {
                            innerDepth = innerDepth2;
                            String pkgName5 = pkgName2;
                            if (tagName.equals(IncidentManager.URI_PARAM_RECEIVER_CLASS)) {
                                ai = ai3;
                                str = str4;
                                packageParser2 = packageParser3;
                                Activity a2 = parseActivity(owner, res, parser, flags, outError, cachedArgs, true, false);
                                if (a2 == null) {
                                    packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                    return false;
                                }
                                boolean z = a2.order != 0;
                                r14 = owner;
                                r14.receivers.add(a2);
                                hasReceiverOrder |= z;
                            } else {
                                ai = ai3;
                                str = str4;
                                packageParser2 = packageParser3;
                                if (tagName.equals("service")) {
                                    Service s = parseService(owner, res, parser, flags, outError, cachedArgs);
                                    if (s == null) {
                                        packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                        return false;
                                    }
                                    boolean z2 = s.order != 0;
                                    r14.services.add(s);
                                    hasServiceOrder2 |= z2;
                                } else if (tagName.equals("provider")) {
                                    Provider p = parseProvider(owner, res, parser, flags, outError, cachedArgs);
                                    if (p == null) {
                                        packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                        return false;
                                    }
                                    r14.providers.add(p);
                                    str2 = pkgName5;
                                    xmlResourceParser = parser;
                                    strArr2 = outError;
                                } else if (tagName.equals("activity-alias")) {
                                    Activity a3 = parseActivityAlias(owner, res, parser, flags, outError, cachedArgs);
                                    if (a3 == null) {
                                        packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                        return false;
                                    }
                                    int i6 = a3.order != 0 ? 1 : 0;
                                    r14.activities.add(a3);
                                    i4 |= i6;
                                } else if (parser.getName().equals("meta-data")) {
                                    xmlResourceParser = parser;
                                    strArr2 = outError;
                                    Bundle parseMetaData = packageParser2.parseMetaData(res, xmlResourceParser, r14.mAppMetaData, strArr2);
                                    r14.mAppMetaData = parseMetaData;
                                    if (parseMetaData == null) {
                                        packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                        return false;
                                    }
                                    str2 = pkgName5;
                                } else {
                                    xmlResourceParser = parser;
                                    strArr2 = outError;
                                    if (tagName.equals("static-library")) {
                                        TypedArray sa3 = res.obtainAttributes(xmlResourceParser, C3132R.styleable.AndroidManifestStaticLibrary);
                                        String lname = sa3.getNonResourceString(0);
                                        int version = sa3.getInt(1, -1);
                                        int versionMajor = sa3.getInt(2, 0);
                                        sa3.recycle();
                                        if (lname != null && version >= 0) {
                                            if (r14.mSharedUserId != null) {
                                                strArr2[0] = "sharedUserId not allowed in static shared library";
                                                packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
                                                XmlUtils.skipCurrentTag(parser);
                                                return false;
                                            } else if (r14.staticSharedLibName != null) {
                                                strArr2[0] = "Multiple static-shared libs for package " + pkgName5;
                                                packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                                XmlUtils.skipCurrentTag(parser);
                                                return false;
                                            } else {
                                                str2 = pkgName5;
                                                r14.staticSharedLibName = lname.intern();
                                                if (version >= 0) {
                                                    r14.staticSharedLibVersion = PackageInfo.composeLongVersionCode(versionMajor, version);
                                                } else {
                                                    r14.staticSharedLibVersion = version;
                                                }
                                                ai.privateFlags |= 16384;
                                                XmlUtils.skipCurrentTag(parser);
                                            }
                                        }
                                    } else {
                                        str2 = pkgName5;
                                        if (tagName.equals("library")) {
                                            sa = res.obtainAttributes(xmlResourceParser, C3132R.styleable.AndroidManifestLibrary);
                                            String lname2 = sa.getNonResourceString(0);
                                            sa.recycle();
                                            if (lname2 != null) {
                                                String lname3 = lname2.intern();
                                                if (!ArrayUtils.contains(r14.libraryNames, lname3)) {
                                                    r14.libraryNames = ArrayUtils.add(r14.libraryNames, lname3);
                                                }
                                            }
                                            XmlUtils.skipCurrentTag(parser);
                                        } else if (tagName.equals("uses-static-library")) {
                                            if (!packageParser2.parseUsesStaticLibrary(r14, res, xmlResourceParser, strArr2)) {
                                                return false;
                                            }
                                        } else if (tagName.equals("uses-library")) {
                                            sa = res.obtainAttributes(xmlResourceParser, C3132R.styleable.AndroidManifestUsesLibrary);
                                            String lname4 = sa.getNonResourceString(0);
                                            boolean req = sa.getBoolean(1, true);
                                            sa.recycle();
                                            if (lname4 != null) {
                                                String lname5 = lname4.intern();
                                                if (req) {
                                                    r14.usesLibraries = ArrayUtils.add(r14.usesLibraries, lname5);
                                                } else {
                                                    r14.usesOptionalLibraries = ArrayUtils.add(r14.usesOptionalLibraries, lname5);
                                                }
                                            }
                                            XmlUtils.skipCurrentTag(parser);
                                        } else if (tagName.equals("uses-package")) {
                                            XmlUtils.skipCurrentTag(parser);
                                        } else if (tagName.equals("profileable")) {
                                            if (res.obtainAttributes(xmlResourceParser, C3132R.styleable.AndroidManifestProfileable).getBoolean(0, false)) {
                                                ai.privateFlags |= 8388608;
                                            }
                                            XmlUtils.skipCurrentTag(parser);
                                        } else {
                                            Slog.m50w(TAG, "Unknown element under <application>: " + tagName + " at " + packageParser2.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(parser);
                                        }
                                    }
                                }
                            }
                            str2 = pkgName5;
                            xmlResourceParser = parser;
                            strArr2 = outError;
                        }
                    }
                    packageParser3 = packageParser2;
                    xmlResourceParser2 = xmlResourceParser;
                    strArr3 = strArr2;
                    pkgName2 = str2;
                    hasServiceOrder = hasServiceOrder2;
                    str4 = str;
                    innerDepth2 = innerDepth;
                    i2 = 1;
                    ai3 = ai;
                }
            }
        } else {
            packageParser3.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return r1;
        }
    }

    private static boolean hasDomainURLs(Package pkg) {
        if (pkg == null || pkg.activities == null) {
            return false;
        }
        ArrayList<Activity> activities = pkg.activities;
        int countActivities = activities.size();
        for (int n = 0; n < countActivities; n++) {
            Activity activity = activities.get(n);
            ArrayList<II> arrayList = activity.intents;
            if (arrayList != 0) {
                int countFilters = arrayList.size();
                for (int m = 0; m < countFilters; m++) {
                    ActivityIntentInfo aii = (ActivityIntentInfo) arrayList.get(m);
                    if (aii.hasAction("android.intent.action.VIEW") && aii.hasAction("android.intent.action.VIEW") && (aii.hasDataScheme(IntentFilter.SCHEME_HTTP) || aii.hasDataScheme(IntentFilter.SCHEME_HTTPS))) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    private boolean parseSplitApplication(Package owner, Resources res, XmlResourceParser parser, int flags, int splitIndex, String[] outError) throws XmlPullParserException, IOException {
        int innerDepth;
        String classLoaderName;
        XmlResourceParser xmlResourceParser;
        Package r1;
        PackageParser packageParser;
        boolean z;
        int i;
        String[] strArr;
        Resources resources;
        ComponentInfo parsedComponent;
        PackageParser packageParser2 = this;
        Package r14 = owner;
        Resources resources2 = res;
        XmlResourceParser xmlResourceParser2 = parser;
        String[] strArr2 = outError;
        TypedArray sa = resources2.obtainAttributes(xmlResourceParser2, C3132R.styleable.AndroidManifestApplication);
        int i2 = 1;
        int i3 = 4;
        if (sa.getBoolean(7, true)) {
            int[] iArr = r14.splitFlags;
            iArr[splitIndex] = iArr[splitIndex] | 4;
        }
        String classLoaderName2 = sa.getString(46);
        int i4 = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        boolean z2 = false;
        if (classLoaderName2 != null && !ClassLoaderFactory.isValidClassLoaderName(classLoaderName2)) {
            strArr2[0] = "Invalid class loader name: " + classLoaderName2;
            packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        r14.applicationInfo.splitClassLoaderNames[splitIndex] = classLoaderName2;
        int innerDepth2 = parser.getDepth();
        while (true) {
            int innerDepth3 = innerDepth2;
            int type = parser.next();
            if (type == i2) {
                return true;
            }
            if (type == 3 && parser.getDepth() <= innerDepth3) {
                return true;
            }
            if (type == 3) {
                innerDepth = innerDepth3;
                classLoaderName = classLoaderName2;
                xmlResourceParser = xmlResourceParser2;
                r1 = r14;
                packageParser = packageParser2;
                z = z2;
                i = i4;
                strArr = strArr2;
                resources = resources2;
            } else if (type == i3) {
                innerDepth = innerDepth3;
                classLoaderName = classLoaderName2;
                xmlResourceParser = xmlResourceParser2;
                r1 = r14;
                packageParser = packageParser2;
                z = z2;
                i = i4;
                strArr = strArr2;
                resources = resources2;
            } else {
                CachedComponentArgs cachedArgs = new CachedComponentArgs();
                String tagName = parser.getName();
                if (tagName.equals(Context.ACTIVITY_SERVICE)) {
                    innerDepth = innerDepth3;
                    int i5 = i4;
                    classLoaderName = classLoaderName2;
                    Activity a = parseActivity(owner, res, parser, flags, outError, cachedArgs, false, r14.baseHardwareAccelerated);
                    if (a == null) {
                        packageParser2.mParseError = i5;
                        return false;
                    }
                    r14.activities.add(a);
                    ComponentInfo parsedComponent2 = a.info;
                    parsedComponent = parsedComponent2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    packageParser = packageParser2;
                    resources = res;
                    z = false;
                    r1 = r14;
                    i = i5;
                } else {
                    innerDepth = innerDepth3;
                    boolean z3 = z2;
                    int i6 = i4;
                    classLoaderName = classLoaderName2;
                    if (tagName.equals(IncidentManager.URI_PARAM_RECEIVER_CLASS)) {
                        xmlResourceParser = xmlResourceParser2;
                        resources = res;
                        r1 = r14;
                        packageParser = packageParser2;
                        Activity a2 = parseActivity(owner, res, parser, flags, outError, cachedArgs, true, false);
                        if (a2 == null) {
                            packageParser.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return false;
                        }
                        i = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        z = false;
                        r1.receivers.add(a2);
                        parsedComponent = a2.info;
                    } else {
                        xmlResourceParser = xmlResourceParser2;
                        packageParser = packageParser2;
                        resources = res;
                        z = z3;
                        r1 = r14;
                        i = i6;
                        if (tagName.equals("service")) {
                            Service s = parseService(owner, res, parser, flags, outError, cachedArgs);
                            if (s == null) {
                                packageParser.mParseError = i;
                                return z;
                            }
                            r1.services.add(s);
                            parsedComponent = s.info;
                        } else if (tagName.equals("provider")) {
                            Provider p = parseProvider(owner, res, parser, flags, outError, cachedArgs);
                            if (p == null) {
                                packageParser.mParseError = i;
                                return z;
                            }
                            r1.providers.add(p);
                            parsedComponent = p.info;
                        } else if (tagName.equals("activity-alias")) {
                            Activity a3 = parseActivityAlias(owner, res, parser, flags, outError, cachedArgs);
                            if (a3 == null) {
                                packageParser.mParseError = i;
                                return z;
                            }
                            r1.activities.add(a3);
                            parsedComponent = a3.info;
                        } else {
                            if (parser.getName().equals("meta-data")) {
                                strArr = outError;
                                Bundle parseMetaData = packageParser.parseMetaData(resources, xmlResourceParser, r1.mAppMetaData, strArr);
                                r1.mAppMetaData = parseMetaData;
                                if (parseMetaData == null) {
                                    packageParser.mParseError = i;
                                    return z;
                                }
                            } else {
                                strArr = outError;
                                if (tagName.equals("uses-static-library")) {
                                    if (!packageParser.parseUsesStaticLibrary(r1, resources, xmlResourceParser, strArr)) {
                                        return z;
                                    }
                                } else if (tagName.equals("uses-library")) {
                                    TypedArray sa2 = resources.obtainAttributes(xmlResourceParser, C3132R.styleable.AndroidManifestUsesLibrary);
                                    String lname = sa2.getNonResourceString(z ? 1 : 0);
                                    boolean req = sa2.getBoolean(1, true);
                                    sa2.recycle();
                                    if (lname != null) {
                                        String lname2 = lname.intern();
                                        if (req) {
                                            r1.usesLibraries = ArrayUtils.add(r1.usesLibraries, lname2);
                                            r1.usesOptionalLibraries = ArrayUtils.remove(r1.usesOptionalLibraries, lname2);
                                        } else if (!ArrayUtils.contains(r1.usesLibraries, lname2)) {
                                            r1.usesOptionalLibraries = ArrayUtils.add(r1.usesOptionalLibraries, lname2);
                                        }
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                } else if (tagName.equals("uses-package")) {
                                    XmlUtils.skipCurrentTag(parser);
                                } else {
                                    Slog.m50w(TAG, "Unknown element under <application>: " + tagName + " at " + packageParser.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                    XmlUtils.skipCurrentTag(parser);
                                }
                            }
                            parsedComponent = null;
                        }
                    }
                    strArr = outError;
                }
                if (parsedComponent != null && parsedComponent.splitName == null) {
                    parsedComponent.splitName = r1.splitNames[splitIndex];
                }
            }
            xmlResourceParser2 = xmlResourceParser;
            resources2 = resources;
            strArr2 = strArr;
            i4 = i;
            z2 = z;
            classLoaderName2 = classLoaderName;
            i3 = 4;
            i2 = 1;
            packageParser2 = packageParser;
            r14 = r1;
            innerDepth2 = innerDepth;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean parsePackageItemInfo(Package owner, PackageItemInfo outInfo, String[] outError, String tag, TypedArray sa, boolean nameRequired, int nameRes, int labelRes, int iconRes, int roundIconRes, int logoRes, int bannerRes) {
        if (sa == null) {
            outError[0] = tag + " does not contain any attributes";
            return false;
        }
        String name = sa.getNonConfigurationString(nameRes, 0);
        if (name == null) {
            if (nameRequired) {
                outError[0] = tag + " does not specify android:name";
                return false;
            }
        } else {
            String outInfoName = buildClassName(owner.applicationInfo.packageName, name, outError);
            if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(outInfoName)) {
                outError[0] = tag + " invalid android:name";
                return false;
            }
            outInfo.name = outInfoName;
            if (outInfoName == null) {
                return false;
            }
        }
        int roundIconVal = sUseRoundIcon ? sa.getResourceId(roundIconRes, 0) : 0;
        if (roundIconVal != 0) {
            outInfo.icon = roundIconVal;
            outInfo.nonLocalizedLabel = null;
        } else {
            int iconVal = sa.getResourceId(iconRes, 0);
            if (iconVal != 0) {
                outInfo.icon = iconVal;
                outInfo.nonLocalizedLabel = null;
            }
        }
        int logoVal = sa.getResourceId(logoRes, 0);
        if (logoVal != 0) {
            outInfo.logo = logoVal;
        }
        int bannerVal = sa.getResourceId(bannerRes, 0);
        if (bannerVal != 0) {
            outInfo.banner = bannerVal;
        }
        TypedValue v = sa.peekValue(labelRes);
        if (v != null) {
            int i = v.resourceId;
            outInfo.labelRes = i;
            if (i == 0) {
                outInfo.nonLocalizedLabel = v.coerceToString();
            }
        }
        outInfo.packageName = owner.packageName;
        return true;
    }

    private Activity generateAppDetailsHiddenActivity(Package owner, int flags, String[] outError, boolean hardwareAccelerated) {
        Activity a = new Activity(owner, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, new ActivityInfo());
        a.owner = owner;
        a.setPackageName(owner.packageName);
        a.info.theme = 16973909;
        a.info.exported = true;
        a.info.name = PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME;
        a.info.processName = owner.applicationInfo.processName;
        a.info.uiOptions = a.info.applicationInfo.uiOptions;
        a.info.taskAffinity = buildTaskAffinityName(owner.packageName, owner.packageName, ":app_details", outError);
        a.info.enabled = true;
        a.info.launchMode = 0;
        a.info.documentLaunchMode = 0;
        a.info.maxRecents = ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        a.info.configChanges = getActivityConfigChanges(0, 0);
        a.info.softInputMode = 0;
        a.info.persistableMode = 1;
        a.info.screenOrientation = -1;
        a.info.resizeMode = 4;
        a.info.lockTaskLaunchMode = 0;
        ActivityInfo activityInfo = a.info;
        a.info.directBootAware = false;
        activityInfo.encryptionAware = false;
        a.info.rotationAnimation = -1;
        a.info.colorMode = 0;
        if (hardwareAccelerated) {
            a.info.flags |= 512;
        }
        return a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:224:0x0656, code lost:
        if (r12 != false) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x0658, code lost:
        r0 = r0.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x0660, code lost:
        if (r0.intents.size() <= 0) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x0662, code lost:
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0664, code lost:
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0665, code lost:
        r0.exported = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x0667, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Activity parseActivity(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs, boolean receiver, boolean hardwareAccelerated) throws XmlPullParserException, IOException {
        String str;
        char c;
        int outerDepth;
        TypedArray sa;
        String[] strArr;
        XmlResourceParser xmlResourceParser;
        Resources resources;
        Package r10;
        String str2;
        char c2;
        Package r6 = owner;
        Resources resources2 = res;
        XmlResourceParser xmlResourceParser2 = parser;
        String[] strArr2 = outError;
        TypedArray sa2 = resources2.obtainAttributes(xmlResourceParser2, C3132R.styleable.AndroidManifestActivity);
        if (cachedArgs.mActivityArgs == null) {
            cachedArgs.mActivityArgs = new ParseComponentArgs(owner, outError, 3, 1, 2, 44, 23, 30, this.mSeparateProcesses, 7, 17, 5);
        }
        cachedArgs.mActivityArgs.tag = receiver ? "<receiver>" : "<activity>";
        cachedArgs.mActivityArgs.f29sa = sa2;
        cachedArgs.mActivityArgs.flags = flags;
        Activity a = new Activity(cachedArgs.mActivityArgs, new ActivityInfo());
        int i = 0;
        if (strArr2[0] != null) {
            sa2.recycle();
            return null;
        }
        boolean setExported = sa2.hasValue(6);
        if (setExported) {
            a.info.exported = sa2.getBoolean(6, false);
        }
        a.info.theme = sa2.getResourceId(0, 0);
        a.info.uiOptions = sa2.getInt(26, a.info.applicationInfo.uiOptions);
        String parentName = sa2.getNonConfigurationString(27, 1024);
        if (parentName != null) {
            String parentClassName = buildClassName(a.info.packageName, parentName, strArr2);
            if (strArr2[0] == null) {
                a.info.parentActivityName = parentClassName;
            } else {
                Log.m70e(TAG, "Activity " + a.info.name + " specified invalid parentActivityName " + parentName);
                i = 0;
                strArr2[0] = null;
            }
        }
        String str3 = sa2.getNonConfigurationString(4, i);
        if (str3 == null) {
            a.info.permission = r6.applicationInfo.permission;
        } else {
            a.info.permission = str3.length() > 0 ? str3.toString().intern() : null;
        }
        String str4 = sa2.getNonConfigurationString(8, 1024);
        a.info.taskAffinity = buildTaskAffinityName(r6.applicationInfo.packageName, r6.applicationInfo.taskAffinity, str4, strArr2);
        a.info.splitName = sa2.getNonConfigurationString(48, 0);
        a.info.flags = 0;
        if (sa2.getBoolean(9, false)) {
            a.info.flags |= 1;
        }
        if (sa2.getBoolean(10, false)) {
            a.info.flags |= 2;
        }
        if (sa2.getBoolean(11, false)) {
            a.info.flags |= 4;
        }
        if (sa2.getBoolean(21, false)) {
            a.info.flags |= 128;
        }
        if (sa2.getBoolean(18, false)) {
            a.info.flags |= 8;
        }
        if (sa2.getBoolean(12, false)) {
            a.info.flags |= 16;
        }
        if (sa2.getBoolean(13, false)) {
            a.info.flags |= 32;
        }
        if (sa2.getBoolean(19, (r6.applicationInfo.flags & 32) != 0)) {
            a.info.flags |= 64;
        }
        if (sa2.getBoolean(22, false)) {
            a.info.flags |= 256;
        }
        if (sa2.getBoolean(29, false) || sa2.getBoolean(39, false)) {
            a.info.flags |= 1024;
        }
        if (sa2.getBoolean(24, false)) {
            a.info.flags |= 2048;
        }
        if (sa2.getBoolean(56, false)) {
            a.info.flags |= 536870912;
        }
        if (receiver) {
            str = str4;
            a.info.launchMode = 0;
            a.info.configChanges = 0;
            if (sa2.getBoolean(28, false)) {
                a.info.flags |= 1073741824;
            }
            ActivityInfo activityInfo = a.info;
            ActivityInfo activityInfo2 = a.info;
            boolean z = sa2.getBoolean(42, false);
            activityInfo2.directBootAware = z;
            activityInfo.encryptionAware = z;
        } else {
            if (sa2.getBoolean(25, hardwareAccelerated)) {
                a.info.flags |= 512;
            }
            a.info.launchMode = sa2.getInt(14, 0);
            a.info.documentLaunchMode = sa2.getInt(33, 0);
            a.info.maxRecents = sa2.getInt(34, ActivityTaskManager.getDefaultAppRecentsLimitStatic());
            str = str4;
            a.info.configChanges = getActivityConfigChanges(sa2.getInt(16, 0), sa2.getInt(47, 0));
            a.info.softInputMode = sa2.getInt(20, 0);
            a.info.persistableMode = sa2.getInteger(32, 0);
            if (sa2.getBoolean(31, false)) {
                a.info.flags |= Integer.MIN_VALUE;
            }
            if (sa2.getBoolean(35, false)) {
                a.info.flags |= 8192;
            }
            if (sa2.getBoolean(36, false)) {
                a.info.flags |= 4096;
            }
            if (sa2.getBoolean(37, false)) {
                a.info.flags |= 16384;
            }
            a.info.screenOrientation = sa2.getInt(15, -1);
            setActivityResizeMode(a.info, sa2, r6);
            if (sa2.getBoolean(41, false)) {
                a.info.flags |= 4194304;
            }
            if (sa2.getBoolean(55, false)) {
                a.info.flags |= 262144;
            }
            if (sa2.hasValue(50) && sa2.getType(50) == 4) {
                a.setMaxAspectRatio(sa2.getFloat(50, 0.0f));
            }
            if (sa2.hasValue(53) && sa2.getType(53) == 4) {
                a.setMinAspectRatio(sa2.getFloat(53, 0.0f));
            }
            a.info.lockTaskLaunchMode = sa2.getInt(38, 0);
            ActivityInfo activityInfo3 = a.info;
            ActivityInfo activityInfo4 = a.info;
            boolean z2 = sa2.getBoolean(42, false);
            activityInfo4.directBootAware = z2;
            activityInfo3.encryptionAware = z2;
            a.info.requestedVrComponent = sa2.getString(43);
            a.info.rotationAnimation = sa2.getInt(46, -1);
            a.info.colorMode = sa2.getInt(49, 0);
            if (sa2.getBoolean(51, false)) {
                a.info.flags |= 8388608;
            }
            if (sa2.getBoolean(52, false)) {
                a.info.flags |= 16777216;
            }
            if (sa2.getBoolean(54, false)) {
                a.info.privateFlags |= 1;
            }
        }
        if (a.info.directBootAware) {
            r6.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa2.getBoolean(45, false);
        if (visibleToEphemeral) {
            a.info.flags |= 1048576;
            r6.visibleToInstantApps = true;
        }
        sa2.recycle();
        if (receiver && (r6.applicationInfo.privateFlags & 2) != 0 && a.info.processName == r6.packageName) {
            c = 0;
            strArr2[0] = "Heavy-weight applications can not have receivers in main process";
        } else {
            c = 0;
        }
        if (strArr2[c] != null) {
            return null;
        }
        int outerDepth2 = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1 && (type != 3 || parser.getDepth() > outerDepth2)) {
                if (type != 3) {
                    if (type == 4) {
                        outerDepth = outerDepth2;
                        sa = sa2;
                        strArr = strArr2;
                        xmlResourceParser = xmlResourceParser2;
                        resources = resources2;
                        r10 = r6;
                        str2 = str;
                    } else {
                        int outerDepth3 = outerDepth2;
                        if (parser.getName().equals("intent-filter")) {
                            ActivityIntentInfo intent = new ActivityIntentInfo(a);
                            str2 = str;
                            outerDepth = outerDepth3;
                            sa = sa2;
                            strArr = strArr2;
                            r10 = r6;
                            if (!parseIntent(res, parser, true, true, intent, outError)) {
                                return null;
                            }
                            if (intent.countActions() == 0) {
                                Slog.m50w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                            } else {
                                a.order = Math.max(intent.getOrder(), a.order);
                                a.intents.add(intent);
                            }
                            int visibility = visibleToEphemeral ? 1 : (receiver || !isImplicitlyExposedIntent(intent)) ? 0 : 2;
                            intent.setVisibilityToInstantApp(visibility);
                            if (intent.isVisibleToInstantApp()) {
                                a.info.flags |= 1048576;
                            }
                            if (intent.isImplicitlyVisibleToInstantApp()) {
                                a.info.flags |= 2097152;
                            }
                            resources = res;
                            xmlResourceParser = parser;
                        } else {
                            sa = sa2;
                            strArr = strArr2;
                            str2 = str;
                            outerDepth = outerDepth3;
                            r10 = r6;
                            if (receiver || !parser.getName().equals("preferred")) {
                                c2 = 0;
                                if (parser.getName().equals("meta-data")) {
                                    resources = res;
                                    xmlResourceParser = parser;
                                    Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, a.metaData, strArr);
                                    a.metaData = parseMetaData;
                                    if (parseMetaData == null) {
                                        return null;
                                    }
                                } else {
                                    resources = res;
                                    xmlResourceParser = parser;
                                    if (receiver || !parser.getName().equals(TtmlUtils.TAG_LAYOUT)) {
                                        Slog.m50w(TAG, "Problem in package " + this.mArchiveSourcePath + SettingsStringUtil.DELIMITER);
                                        if (receiver) {
                                            Slog.m50w(TAG, "Unknown element under <receiver>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                        } else {
                                            Slog.m50w(TAG, "Unknown element under <activity>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                        }
                                        XmlUtils.skipCurrentTag(parser);
                                    } else {
                                        parseLayout(resources, xmlResourceParser, a);
                                    }
                                }
                            } else {
                                ActivityIntentInfo intent2 = new ActivityIntentInfo(a);
                                if (!parseIntent(res, parser, false, false, intent2, outError)) {
                                    return null;
                                }
                                if (intent2.countActions() == 0) {
                                    Slog.m50w(TAG, "No actions in preferred at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                } else {
                                    if (r10.preferredActivityFilters == null) {
                                        r10.preferredActivityFilters = new ArrayList<>();
                                    }
                                    r10.preferredActivityFilters.add(intent2);
                                }
                                int visibility2 = visibleToEphemeral ? 1 : (receiver || !isImplicitlyExposedIntent(intent2)) ? 0 : 2;
                                intent2.setVisibilityToInstantApp(visibility2);
                                if (intent2.isVisibleToInstantApp()) {
                                    c2 = 0;
                                    a.info.flags |= 1048576;
                                } else {
                                    c2 = 0;
                                }
                                if (intent2.isImplicitlyVisibleToInstantApp()) {
                                    a.info.flags |= 2097152;
                                }
                                resources = res;
                                xmlResourceParser = parser;
                            }
                        }
                    }
                    c2 = 0;
                } else {
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    resources = resources2;
                    r10 = r6;
                    str2 = str;
                    c2 = 0;
                }
                resources2 = resources;
                xmlResourceParser2 = xmlResourceParser;
                r6 = r10;
                str = str2;
                outerDepth2 = outerDepth;
                sa2 = sa;
                strArr2 = strArr;
            }
        }
    }

    private void setActivityResizeMode(ActivityInfo aInfo, TypedArray sa, Package owner) {
        boolean appExplicitDefault = (owner.applicationInfo.privateFlags & 3072) != 0;
        if (sa.hasValue(40) || appExplicitDefault) {
            boolean appResizeable = (owner.applicationInfo.privateFlags & 1024) != 0;
            if (sa.getBoolean(40, appResizeable)) {
                aInfo.resizeMode = 2;
            } else {
                aInfo.resizeMode = 0;
            }
        } else if ((owner.applicationInfo.privateFlags & 4096) != 0) {
            aInfo.resizeMode = 1;
        } else if (aInfo.isFixedOrientationPortrait()) {
            aInfo.resizeMode = 6;
        } else if (aInfo.isFixedOrientationLandscape()) {
            aInfo.resizeMode = 5;
        } else if (aInfo.isFixedOrientation()) {
            aInfo.resizeMode = 7;
        } else {
            aInfo.resizeMode = 4;
        }
    }

    private void setMaxAspectRatio(Package owner) {
        float activityAspectRatio;
        float maxAspectRatio = owner.applicationInfo.targetSdkVersion < 26 ? 3.0f : 0.0f;
        if (owner.applicationInfo.maxAspectRatio != 0.0f) {
            maxAspectRatio = owner.applicationInfo.maxAspectRatio;
        } else if (owner.mAppMetaData != null && owner.mAppMetaData.containsKey(METADATA_MAX_ASPECT_RATIO)) {
            maxAspectRatio = owner.mAppMetaData.getFloat(METADATA_MAX_ASPECT_RATIO, maxAspectRatio);
        }
        String pkgName = owner.applicationInfo.packageName;
        Log.m72d("debugWindow", "PackageParser  pkgName = " + pkgName);
        Iterator<Activity> it = owner.activities.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (!activity.hasMaxAspectRatio()) {
                if (activity.metaData != null) {
                    activityAspectRatio = activity.metaData.getFloat(METADATA_MAX_ASPECT_RATIO, maxAspectRatio);
                } else {
                    activityAspectRatio = maxAspectRatio;
                }
                Log.m72d("debugWindow", "PackageParser  setMaxAspectRatio(owner)  activityAspectRatio = " + activityAspectRatio);
                if (!pkgName.contains("com.android") && !pkgName.contains("com.google") && !pkgName.contains("com.wits") && !pkgName.contains("com.qcom") && activityAspectRatio < 3.0f) {
                    Log.m72d("debugWindow", "PackageParser  setMaxAspectRatio(owner)  force setMaxAspectRatio(DEFAULT_PRE_O_MAX_ASPECT_RATIO)");
                    activity.setMaxAspectRatio(3.0f);
                } else {
                    activity.setMaxAspectRatio(activityAspectRatio);
                }
            }
        }
    }

    private void setMinAspectRatio(Package owner) {
        float minAspectRatio;
        float f = 0.0f;
        if (owner.applicationInfo.minAspectRatio != 0.0f) {
            minAspectRatio = owner.applicationInfo.minAspectRatio;
        } else {
            if (owner.applicationInfo.targetSdkVersion < 29) {
                if (this.mCallback != null && this.mCallback.hasFeature(PackageManager.FEATURE_WATCH)) {
                    f = 1.0f;
                } else {
                    f = DEFAULT_PRE_Q_MIN_ASPECT_RATIO;
                }
            }
            minAspectRatio = f;
        }
        Iterator<Activity> it = owner.activities.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (!activity.hasMinAspectRatio()) {
                activity.setMinAspectRatio(minAspectRatio);
            }
        }
    }

    public static int getActivityConfigChanges(int configChanges, int recreateOnConfigChanges) {
        return ((~recreateOnConfigChanges) & 3) | configChanges;
    }

    private void parseLayout(Resources res, AttributeSet attrs, Activity a) {
        TypedArray sw = res.obtainAttributes(attrs, C3132R.styleable.AndroidManifestLayout);
        int width = -1;
        float widthFraction = -1.0f;
        int height = -1;
        float heightFraction = -1.0f;
        int widthType = sw.getType(3);
        if (widthType == 6) {
            widthFraction = sw.getFraction(3, 1, 1, -1.0f);
        } else if (widthType == 5) {
            width = sw.getDimensionPixelSize(3, -1);
        }
        int heightType = sw.getType(4);
        if (heightType == 6) {
            heightFraction = sw.getFraction(4, 1, 1, -1.0f);
        } else if (heightType == 5) {
            height = sw.getDimensionPixelSize(4, -1);
        }
        int gravity = sw.getInt(0, 17);
        int minWidth = sw.getDimensionPixelSize(1, -1);
        int minHeight = sw.getDimensionPixelSize(2, -1);
        sw.recycle();
        a.info.windowLayout = new ActivityInfo.WindowLayout(width, widthFraction, height, heightFraction, gravity, minWidth, minHeight);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x037e, code lost:
        r0 = r0.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0386, code lost:
        if (r0.intents.size() <= 0) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0388, code lost:
        r3 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x038b, code lost:
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x038d, code lost:
        r0.exported = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x038f, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x037c, code lost:
        if (r14 != false) goto L68;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Activity parseActivityAlias(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        String targetActivity;
        boolean z;
        int outerDepth;
        TypedArray sa;
        String[] strArr;
        Resources resources;
        String targetActivity2;
        char c;
        boolean z2;
        ActivityIntentInfo intent;
        Resources resources2 = res;
        String[] strArr2 = outError;
        TypedArray sa2 = resources2.obtainAttributes(parser, C3132R.styleable.AndroidManifestActivityAlias);
        String targetActivity3 = sa2.getNonConfigurationString(7, 1024);
        if (targetActivity3 != null) {
            String targetActivity4 = buildClassName(owner.applicationInfo.packageName, targetActivity3, strArr2);
            if (targetActivity4 != null) {
                if (cachedArgs.mActivityAliasArgs == null) {
                    targetActivity = targetActivity4;
                    cachedArgs.mActivityAliasArgs = new ParseComponentArgs(owner, outError, 2, 0, 1, 11, 8, 10, this.mSeparateProcesses, 0, 6, 4);
                    cachedArgs.mActivityAliasArgs.tag = "<activity-alias>";
                } else {
                    targetActivity = targetActivity4;
                }
                cachedArgs.mActivityAliasArgs.f29sa = sa2;
                cachedArgs.mActivityAliasArgs.flags = flags;
                Activity target = null;
                int NA = owner.activities.size();
                int i = 0;
                while (true) {
                    if (i >= NA) {
                        break;
                    }
                    Activity t = owner.activities.get(i);
                    if (targetActivity.equals(t.info.name)) {
                        target = t;
                        break;
                    }
                    i++;
                }
                Activity target2 = target;
                if (target2 != null) {
                    ActivityInfo info = new ActivityInfo();
                    info.targetActivity = targetActivity;
                    info.configChanges = target2.info.configChanges;
                    info.flags = target2.info.flags;
                    info.privateFlags = target2.info.privateFlags;
                    info.icon = target2.info.icon;
                    info.logo = target2.info.logo;
                    info.banner = target2.info.banner;
                    info.labelRes = target2.info.labelRes;
                    info.nonLocalizedLabel = target2.info.nonLocalizedLabel;
                    info.launchMode = target2.info.launchMode;
                    info.lockTaskLaunchMode = target2.info.lockTaskLaunchMode;
                    info.processName = target2.info.processName;
                    if (info.descriptionRes == 0) {
                        info.descriptionRes = target2.info.descriptionRes;
                    }
                    info.screenOrientation = target2.info.screenOrientation;
                    info.taskAffinity = target2.info.taskAffinity;
                    info.theme = target2.info.theme;
                    info.softInputMode = target2.info.softInputMode;
                    info.uiOptions = target2.info.uiOptions;
                    info.parentActivityName = target2.info.parentActivityName;
                    info.maxRecents = target2.info.maxRecents;
                    info.windowLayout = target2.info.windowLayout;
                    info.resizeMode = target2.info.resizeMode;
                    info.maxAspectRatio = target2.info.maxAspectRatio;
                    info.minAspectRatio = target2.info.minAspectRatio;
                    info.requestedVrComponent = target2.info.requestedVrComponent;
                    boolean z3 = target2.info.directBootAware;
                    info.directBootAware = z3;
                    info.encryptionAware = z3;
                    Activity a = new Activity(cachedArgs.mActivityAliasArgs, info);
                    if (strArr2[0] == null) {
                        boolean setExported = sa2.hasValue(5);
                        if (setExported) {
                            a.info.exported = sa2.getBoolean(5, false);
                        }
                        String str = sa2.getNonConfigurationString(3, 0);
                        if (str != null) {
                            a.info.permission = str.length() > 0 ? str.toString().intern() : null;
                        }
                        String parentName = sa2.getNonConfigurationString(9, 1024);
                        if (parentName != null) {
                            String parentClassName = buildClassName(a.info.packageName, parentName, strArr2);
                            if (strArr2[0] == null) {
                                a.info.parentActivityName = parentClassName;
                            } else {
                                Log.m70e(TAG, "Activity alias " + a.info.name + " specified invalid parentActivityName " + parentName);
                                strArr2[0] = null;
                            }
                        }
                        boolean z4 = true;
                        boolean visibleToEphemeral = (a.info.flags & 1048576) != 0;
                        sa2.recycle();
                        if (strArr2[0] == null) {
                            int outerDepth2 = parser.getDepth();
                            while (true) {
                                int type = parser.next();
                                if (type == z4) {
                                    z = z4;
                                    break;
                                }
                                String targetActivity5 = targetActivity;
                                if (type == 3 && parser.getDepth() <= outerDepth2) {
                                    z = true;
                                    break;
                                }
                                if (type == 3) {
                                    outerDepth = outerDepth2;
                                    sa = sa2;
                                    strArr = strArr2;
                                    resources = resources2;
                                    targetActivity2 = targetActivity5;
                                    c = 0;
                                    z2 = true;
                                } else if (type == 4) {
                                    outerDepth = outerDepth2;
                                    sa = sa2;
                                    strArr = strArr2;
                                    resources = resources2;
                                    targetActivity2 = targetActivity5;
                                    c = 0;
                                    z2 = true;
                                } else if (parser.getName().equals("intent-filter")) {
                                    ActivityIntentInfo intent2 = new ActivityIntentInfo(a);
                                    outerDepth = outerDepth2;
                                    sa = sa2;
                                    z2 = true;
                                    strArr = strArr2;
                                    targetActivity2 = targetActivity5;
                                    if (!parseIntent(res, parser, true, true, intent2, outError)) {
                                        return null;
                                    }
                                    if (intent2.countActions() == 0) {
                                        Slog.m50w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                        intent = intent2;
                                    } else {
                                        a.order = Math.max(intent2.getOrder(), a.order);
                                        intent = intent2;
                                        a.intents.add(intent);
                                    }
                                    int visibility = visibleToEphemeral ? 1 : isImplicitlyExposedIntent(intent) ? 2 : 0;
                                    intent.setVisibilityToInstantApp(visibility);
                                    if (intent.isVisibleToInstantApp()) {
                                        c = 0;
                                        a.info.flags |= 1048576;
                                    } else {
                                        c = 0;
                                    }
                                    if (intent.isImplicitlyVisibleToInstantApp()) {
                                        a.info.flags |= 2097152;
                                    }
                                    resources = res;
                                } else {
                                    outerDepth = outerDepth2;
                                    sa = sa2;
                                    strArr = strArr2;
                                    targetActivity2 = targetActivity5;
                                    c = 0;
                                    z2 = true;
                                    if (parser.getName().equals("meta-data")) {
                                        resources = res;
                                        Bundle parseMetaData = parseMetaData(resources, parser, a.metaData, strArr);
                                        a.metaData = parseMetaData;
                                        if (parseMetaData == null) {
                                            return null;
                                        }
                                    } else {
                                        resources = res;
                                        Slog.m50w(TAG, "Unknown element under <activity-alias>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                        XmlUtils.skipCurrentTag(parser);
                                    }
                                }
                                resources2 = resources;
                                strArr2 = strArr;
                                targetActivity = targetActivity2;
                                outerDepth2 = outerDepth;
                                sa2 = sa;
                                z4 = z2;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        sa2.recycle();
                        return null;
                    }
                } else {
                    strArr2[0] = "<activity-alias> target activity " + targetActivity + " not found in manifest";
                    sa2.recycle();
                    return null;
                }
            } else {
                sa2.recycle();
                return null;
            }
        } else {
            strArr2[0] = "<activity-alias> does not specify android:targetActivity";
            sa2.recycle();
            return null;
        }
    }

    private Provider parseProvider(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        TypedArray sa;
        TypedArray sa2 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestProvider);
        if (cachedArgs.mProviderArgs == null) {
            sa = sa2;
            cachedArgs.mProviderArgs = new ParseComponentArgs(owner, outError, 2, 0, 1, 19, 15, 17, this.mSeparateProcesses, 8, 14, 6);
            cachedArgs.mProviderArgs.tag = "<provider>";
        } else {
            sa = sa2;
        }
        TypedArray sa3 = sa;
        cachedArgs.mProviderArgs.f29sa = sa3;
        cachedArgs.mProviderArgs.flags = flags;
        Provider p = new Provider(cachedArgs.mProviderArgs, new ProviderInfo());
        if (outError[0] != null) {
            sa3.recycle();
            return null;
        }
        boolean providerExportedDefault = false;
        if (owner.applicationInfo.targetSdkVersion < 17) {
            providerExportedDefault = true;
        }
        p.info.exported = sa3.getBoolean(7, providerExportedDefault);
        String cpname = sa3.getNonConfigurationString(10, 0);
        p.info.isSyncable = sa3.getBoolean(11, false);
        String permission = sa3.getNonConfigurationString(3, 0);
        String str = sa3.getNonConfigurationString(4, 0);
        if (str == null) {
            str = permission;
        }
        if (str == null) {
            p.info.readPermission = owner.applicationInfo.permission;
        } else {
            p.info.readPermission = str.length() > 0 ? str.toString().intern() : null;
        }
        String str2 = sa3.getNonConfigurationString(5, 0);
        if (str2 == null) {
            str2 = permission;
        }
        String str3 = str2;
        if (str3 == null) {
            p.info.writePermission = owner.applicationInfo.permission;
        } else {
            p.info.writePermission = str3.length() > 0 ? str3.toString().intern() : null;
        }
        p.info.grantUriPermissions = sa3.getBoolean(13, false);
        p.info.forceUriPermissions = sa3.getBoolean(22, false);
        p.info.multiprocess = sa3.getBoolean(9, false);
        p.info.initOrder = sa3.getInt(12, 0);
        p.info.splitName = sa3.getNonConfigurationString(21, 0);
        p.info.flags = 0;
        if (sa3.getBoolean(16, false)) {
            p.info.flags |= 1073741824;
        }
        ProviderInfo providerInfo = p.info;
        ProviderInfo providerInfo2 = p.info;
        boolean z = sa3.getBoolean(18, false);
        providerInfo2.directBootAware = z;
        providerInfo.encryptionAware = z;
        if (p.info.directBootAware) {
            owner.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa3.getBoolean(20, false);
        if (visibleToEphemeral) {
            p.info.flags |= 1048576;
            owner.visibleToInstantApps = true;
        }
        sa3.recycle();
        if ((owner.applicationInfo.privateFlags & 2) != 0 && p.info.processName == owner.packageName) {
            outError[0] = "Heavy-weight applications can not have providers in main process";
            return null;
        } else if (cpname == null) {
            outError[0] = "<provider> does not include authorities attribute";
            return null;
        } else if (cpname.length() <= 0) {
            outError[0] = "<provider> has empty authorities attribute";
            return null;
        } else {
            p.info.authority = cpname.intern();
            if (parseProviderTags(res, parser, visibleToEphemeral, p, outError)) {
                return p;
            }
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x0283, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseProviderTags(Resources res, XmlResourceParser parser, boolean visibleToEphemeral, Provider outInfo, String[] outError) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (true) {
            int outerDepth2 = outerDepth;
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth2)) {
                break;
            }
            if (type != 3 && type != 4) {
                if (parser.getName().equals("intent-filter")) {
                    ProviderIntentInfo intent = new ProviderIntentInfo(outInfo);
                    if (!parseIntent(res, parser, true, false, intent, outError)) {
                        return false;
                    }
                    if (visibleToEphemeral) {
                        intent.setVisibilityToInstantApp(1);
                        outInfo.info.flags |= 1048576;
                    }
                    outInfo.order = Math.max(intent.getOrder(), outInfo.order);
                    outInfo.intents.add(intent);
                } else {
                    if (parser.getName().equals("meta-data")) {
                        Bundle parseMetaData = parseMetaData(res, parser, outInfo.metaData, outError);
                        outInfo.metaData = parseMetaData;
                        if (parseMetaData == null) {
                            return false;
                        }
                    } else if (parser.getName().equals("grant-uri-permission")) {
                        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestGrantUriPermission);
                        PatternMatcher pa = null;
                        String str = sa.getNonConfigurationString(0, 0);
                        if (str != null) {
                            pa = new PatternMatcher(str, 0);
                        }
                        String str2 = sa.getNonConfigurationString(1, 0);
                        if (str2 != null) {
                            pa = new PatternMatcher(str2, 1);
                        }
                        String str3 = sa.getNonConfigurationString(2, 0);
                        if (str3 != null) {
                            pa = new PatternMatcher(str3, 2);
                        }
                        sa.recycle();
                        if (pa != null) {
                            if (outInfo.info.uriPermissionPatterns == null) {
                                outInfo.info.uriPermissionPatterns = new PatternMatcher[1];
                                outInfo.info.uriPermissionPatterns[0] = pa;
                            } else {
                                int N = outInfo.info.uriPermissionPatterns.length;
                                PatternMatcher[] newp = new PatternMatcher[N + 1];
                                System.arraycopy(outInfo.info.uriPermissionPatterns, 0, newp, 0, N);
                                newp[N] = pa;
                                outInfo.info.uriPermissionPatterns = newp;
                            }
                            outInfo.info.grantUriPermissions = true;
                            XmlUtils.skipCurrentTag(parser);
                        } else {
                            Slog.m50w(TAG, "Unknown element under <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                            XmlUtils.skipCurrentTag(parser);
                        }
                    } else if (parser.getName().equals("path-permission")) {
                        TypedArray sa2 = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestPathPermission);
                        PathPermission pa2 = null;
                        String permission = sa2.getNonConfigurationString(0, 0);
                        String readPermission = sa2.getNonConfigurationString(1, 0);
                        if (readPermission == null) {
                            readPermission = permission;
                        }
                        String writePermission = sa2.getNonConfigurationString(2, 0);
                        if (writePermission == null) {
                            writePermission = permission;
                        }
                        boolean havePerm = false;
                        if (readPermission != null) {
                            readPermission = readPermission.intern();
                            havePerm = true;
                        }
                        String readPermission2 = readPermission;
                        if (writePermission != null) {
                            writePermission = writePermission.intern();
                            havePerm = true;
                        }
                        String writePermission2 = writePermission;
                        if (!havePerm) {
                            Slog.m50w(TAG, "No readPermission or writePermssion for <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                            XmlUtils.skipCurrentTag(parser);
                        } else {
                            String path = sa2.getNonConfigurationString(3, 0);
                            if (path != null) {
                                pa2 = new PathPermission(path, 0, readPermission2, writePermission2);
                            }
                            String path2 = sa2.getNonConfigurationString(4, 0);
                            if (path2 != null) {
                                pa2 = new PathPermission(path2, 1, readPermission2, writePermission2);
                            }
                            String path3 = sa2.getNonConfigurationString(5, 0);
                            if (path3 != null) {
                                pa2 = new PathPermission(path3, 2, readPermission2, writePermission2);
                            }
                            String path4 = sa2.getNonConfigurationString(6, 0);
                            if (path4 != null) {
                                pa2 = new PathPermission(path4, 3, readPermission2, writePermission2);
                            }
                            sa2.recycle();
                            if (pa2 != null) {
                                if (outInfo.info.pathPermissions == null) {
                                    outInfo.info.pathPermissions = new PathPermission[1];
                                    outInfo.info.pathPermissions[0] = pa2;
                                } else {
                                    int N2 = outInfo.info.pathPermissions.length;
                                    PathPermission[] newp2 = new PathPermission[N2 + 1];
                                    System.arraycopy(outInfo.info.pathPermissions, 0, newp2, 0, N2);
                                    newp2[N2] = pa2;
                                    outInfo.info.pathPermissions = newp2;
                                }
                                XmlUtils.skipCurrentTag(parser);
                            } else {
                                Slog.m50w(TAG, "No path, pathPrefix, or pathPattern for <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                XmlUtils.skipCurrentTag(parser);
                            }
                        }
                    } else {
                        Slog.m50w(TAG, "Unknown element under <provider>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                        XmlUtils.skipCurrentTag(parser);
                    }
                    outerDepth = outerDepth2;
                }
            }
            outerDepth = outerDepth2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x0253, code lost:
        if (r12 != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0255, code lost:
        r1 = r0.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x025d, code lost:
        if (r0.intents.size() <= 0) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0260, code lost:
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0261, code lost:
        r1.exported = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0263, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Service parseService(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        boolean z;
        int outerDepth;
        TypedArray sa;
        String[] strArr;
        XmlResourceParser xmlResourceParser;
        Resources resources;
        boolean z2;
        char c;
        Resources resources2 = res;
        XmlResourceParser xmlResourceParser2 = parser;
        String[] strArr2 = outError;
        TypedArray sa2 = resources2.obtainAttributes(xmlResourceParser2, C3132R.styleable.AndroidManifestService);
        if (cachedArgs.mServiceArgs == null) {
            cachedArgs.mServiceArgs = new ParseComponentArgs(owner, outError, 2, 0, 1, 15, 8, 12, this.mSeparateProcesses, 6, 7, 4);
            cachedArgs.mServiceArgs.tag = "<service>";
        }
        cachedArgs.mServiceArgs.f29sa = sa2;
        cachedArgs.mServiceArgs.flags = flags;
        Service s = new Service(cachedArgs.mServiceArgs, new ServiceInfo());
        if (strArr2[0] != null) {
            sa2.recycle();
            return null;
        }
        boolean setExported = sa2.hasValue(5);
        if (setExported) {
            s.info.exported = sa2.getBoolean(5, false);
        }
        String str = sa2.getNonConfigurationString(3, 0);
        if (str == null) {
            s.info.permission = owner.applicationInfo.permission;
        } else {
            s.info.permission = str.length() > 0 ? str.toString().intern() : null;
        }
        s.info.splitName = sa2.getNonConfigurationString(17, 0);
        s.info.mForegroundServiceType = sa2.getInt(19, 0);
        s.info.flags = 0;
        boolean z3 = true;
        if (sa2.getBoolean(9, false)) {
            s.info.flags |= 1;
        }
        if (sa2.getBoolean(10, false)) {
            s.info.flags |= 2;
        }
        if (sa2.getBoolean(14, false)) {
            s.info.flags |= 4;
        }
        if (sa2.getBoolean(18, false)) {
            s.info.flags |= 8;
        }
        if (sa2.getBoolean(11, false)) {
            s.info.flags |= 1073741824;
        }
        ServiceInfo serviceInfo = s.info;
        ServiceInfo serviceInfo2 = s.info;
        boolean z4 = sa2.getBoolean(13, false);
        serviceInfo2.directBootAware = z4;
        serviceInfo.encryptionAware = z4;
        if (s.info.directBootAware) {
            owner.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa2.getBoolean(16, false);
        if (visibleToEphemeral) {
            s.info.flags |= 1048576;
            owner.visibleToInstantApps = true;
        }
        sa2.recycle();
        if ((owner.applicationInfo.privateFlags & 2) != 0 && s.info.processName == owner.packageName) {
            strArr2[0] = "Heavy-weight applications can not have services in main process";
            return null;
        }
        int outerDepth2 = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == z3) {
                z = z3;
                break;
            } else if (type == 3 && parser.getDepth() <= outerDepth2) {
                z = true;
                break;
            } else {
                if (type == 3) {
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    resources = resources2;
                    z2 = true;
                    c = 0;
                } else if (type == 4) {
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    resources = resources2;
                    z2 = true;
                    c = 0;
                } else if (parser.getName().equals("intent-filter")) {
                    ServiceIntentInfo intent = new ServiceIntentInfo(s);
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    if (!parseIntent(res, parser, true, false, intent, outError)) {
                        return null;
                    }
                    if (visibleToEphemeral) {
                        z2 = true;
                        intent.setVisibilityToInstantApp(1);
                        c = 0;
                        s.info.flags |= 1048576;
                    } else {
                        z2 = true;
                        c = 0;
                    }
                    s.order = Math.max(intent.getOrder(), s.order);
                    s.intents.add(intent);
                    resources = res;
                } else {
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    z2 = true;
                    c = 0;
                    if (parser.getName().equals("meta-data")) {
                        resources = res;
                        Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, s.metaData, strArr);
                        s.metaData = parseMetaData;
                        if (parseMetaData == null) {
                            return null;
                        }
                    } else {
                        resources = res;
                        Slog.m50w(TAG, "Unknown element under <service>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                        XmlUtils.skipCurrentTag(parser);
                    }
                }
                resources2 = resources;
                strArr2 = strArr;
                xmlResourceParser2 = xmlResourceParser;
                sa2 = sa;
                z3 = z2;
                outerDepth2 = outerDepth;
            }
        }
    }

    private boolean isImplicitlyExposedIntent(IntentInfo intent) {
        return intent.hasCategory(Intent.CATEGORY_BROWSABLE) || intent.hasAction(Intent.ACTION_SEND) || intent.hasAction(Intent.ACTION_SENDTO) || intent.hasAction(Intent.ACTION_SEND_MULTIPLE);
    }

    private boolean parseAllMetaData(Resources res, XmlResourceParser parser, String tag, Component<?> outInfo, String[] outError) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (type != 3 && type != 4) {
                if (parser.getName().equals("meta-data")) {
                    Bundle parseMetaData = parseMetaData(res, parser, outInfo.metaData, outError);
                    outInfo.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return false;
                    }
                } else {
                    Slog.m50w(TAG, "Unknown element under " + tag + PluralRules.KEYWORD_RULE_SEPARATOR + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
        return true;
    }

    private Bundle parseMetaData(Resources res, XmlResourceParser parser, Bundle data, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, C3132R.styleable.AndroidManifestMetaData);
        if (data == null) {
            data = new Bundle();
        }
        boolean z = false;
        String name = sa.getNonConfigurationString(0, 0);
        if (name == null) {
            outError[0] = "<meta-data> requires an android:name attribute";
            sa.recycle();
            return null;
        }
        String name2 = name.intern();
        TypedValue v = sa.peekValue(2);
        if (v != null && v.resourceId != 0) {
            data.putInt(name2, v.resourceId);
        } else {
            TypedValue v2 = sa.peekValue(1);
            if (v2 == null) {
                outError[0] = "<meta-data> requires an android:value or android:resource attribute";
                data = null;
            } else if (v2.type == 3) {
                CharSequence cs = v2.coerceToString();
                data.putString(name2, cs != null ? cs.toString() : null);
            } else if (v2.type == 18) {
                if (v2.data != 0) {
                    z = true;
                }
                data.putBoolean(name2, z);
            } else if (v2.type >= 16 && v2.type <= 31) {
                data.putInt(name2, v2.data);
            } else if (v2.type == 4) {
                data.putFloat(name2, v2.getFloat());
            } else {
                Slog.m50w(TAG, "<meta-data> only supports string, integer, float, color, boolean, and resource reference types: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
            }
        }
        sa.recycle();
        XmlUtils.skipCurrentTag(parser);
        return data;
    }

    private static VerifierInfo parseVerifier(AttributeSet attrs) {
        String packageName = null;
        String encodedPublicKey = null;
        int attrCount = attrs.getAttributeCount();
        for (int i = 0; i < attrCount; i++) {
            int attrResId = attrs.getAttributeNameResource(i);
            if (attrResId == 16842755) {
                packageName = attrs.getAttributeValue(i);
            } else if (attrResId == 16843686) {
                encodedPublicKey = attrs.getAttributeValue(i);
            }
        }
        if (packageName == null || packageName.length() == 0) {
            Slog.m54i(TAG, "verifier package name was null; skipping");
            return null;
        }
        PublicKey publicKey = parsePublicKey(encodedPublicKey);
        if (publicKey == null) {
            Slog.m54i(TAG, "Unable to parse verifier public key for " + packageName);
            return null;
        }
        return new VerifierInfo(packageName, publicKey);
    }

    public static final PublicKey parsePublicKey(String encodedPublicKey) {
        if (encodedPublicKey == null) {
            Slog.m50w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            byte[] encoded = Base64.decode(encodedPublicKey, 0);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
                return keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    KeyFactory keyFactory2 = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC);
                    return keyFactory2.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException e2) {
                    Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        KeyFactory keyFactory3 = KeyFactory.getInstance("DSA");
                        return keyFactory3.generatePublic(keySpec);
                    } catch (NoSuchAlgorithmException e3) {
                        Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (InvalidKeySpecException e4) {
                        return null;
                    }
                } catch (InvalidKeySpecException e5) {
                    KeyFactory keyFactory32 = KeyFactory.getInstance("DSA");
                    return keyFactory32.generatePublic(keySpec);
                }
            } catch (InvalidKeySpecException e6) {
                KeyFactory keyFactory22 = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC);
                return keyFactory22.generatePublic(keySpec);
            }
        } catch (IllegalArgumentException e7) {
            Slog.m50w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a6, code lost:
        r24[r5] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00aa, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c9, code lost:
        r24[r5] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00cd, code lost:
        return r5;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseIntent(Resources res, XmlResourceParser parser, boolean allowGlobs, boolean allowAutoVerify, IntentInfo outInfo, String[] outError) throws XmlPullParserException, IOException {
        int i;
        Resources resources = res;
        TypedArray sa = resources.obtainAttributes(parser, C3132R.styleable.AndroidManifestIntentFilter);
        ?? r5 = 0;
        int priority = sa.getInt(2, 0);
        outInfo.setPriority(priority);
        int i2 = 3;
        int order = sa.getInt(3, 0);
        outInfo.setOrder(order);
        TypedValue v = sa.peekValue(0);
        if (v != null) {
            int i3 = v.resourceId;
            outInfo.labelRes = i3;
            if (i3 == 0) {
                outInfo.nonLocalizedLabel = v.coerceToString();
            }
        }
        int roundIconVal = sUseRoundIcon ? sa.getResourceId(7, 0) : 0;
        int i4 = 1;
        if (roundIconVal != 0) {
            outInfo.icon = roundIconVal;
        } else {
            outInfo.icon = sa.getResourceId(1, 0);
        }
        int i5 = 4;
        outInfo.logo = sa.getResourceId(4, 0);
        outInfo.banner = sa.getResourceId(5, 0);
        if (allowAutoVerify) {
            outInfo.setAutoVerify(sa.getBoolean(6, false));
        }
        sa.recycle();
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != i4 && (type != i2 || parser.getDepth() > outerDepth)) {
                if (type != i2 && type != i5) {
                    String nodeName = parser.getName();
                    if (nodeName.equals("action")) {
                        String value = parser.getAttributeValue(ANDROID_RESOURCES, "name");
                        if (value == null || value == "") {
                            break;
                        }
                        XmlUtils.skipCurrentTag(parser);
                        outInfo.addAction(value);
                    } else if (nodeName.equals("category")) {
                        String value2 = parser.getAttributeValue(ANDROID_RESOURCES, "name");
                        if (value2 == null || value2 == "") {
                            break;
                        }
                        XmlUtils.skipCurrentTag(parser);
                        outInfo.addCategory(value2);
                    } else if (nodeName.equals("data")) {
                        TypedArray sa2 = resources.obtainAttributes(parser, C3132R.styleable.AndroidManifestData);
                        String str = sa2.getNonConfigurationString(r5, r5);
                        if (str != null) {
                            try {
                                outInfo.addDataType(str);
                            } catch (IntentFilter.MalformedMimeTypeException e) {
                                outError[r5] = e.toString();
                                sa2.recycle();
                                return r5;
                            }
                        }
                        String str2 = sa2.getNonConfigurationString(1, r5);
                        if (str2 != null) {
                            outInfo.addDataScheme(str2);
                        }
                        String str3 = sa2.getNonConfigurationString(7, r5);
                        if (str3 != null) {
                            outInfo.addDataSchemeSpecificPart(str3, r5);
                        }
                        String str4 = sa2.getNonConfigurationString(8, r5);
                        if (str4 != null) {
                            outInfo.addDataSchemeSpecificPart(str4, 1);
                        }
                        String str5 = sa2.getNonConfigurationString(9, r5);
                        if (str5 != null) {
                            if (!allowGlobs) {
                                outError[r5] = "sspPattern not allowed here; ssp must be literal";
                                return r5;
                            }
                            i = 2;
                            outInfo.addDataSchemeSpecificPart(str5, 2);
                        } else {
                            i = 2;
                        }
                        String host = sa2.getNonConfigurationString(i, r5);
                        String port = sa2.getNonConfigurationString(3, r5);
                        if (host != null) {
                            outInfo.addDataAuthority(host, port);
                        }
                        String str6 = sa2.getNonConfigurationString(4, r5);
                        if (str6 != null) {
                            outInfo.addDataPath(str6, r5);
                        }
                        String str7 = sa2.getNonConfigurationString(5, r5);
                        if (str7 != null) {
                            outInfo.addDataPath(str7, 1);
                        }
                        String str8 = sa2.getNonConfigurationString(6, r5);
                        if (str8 != null) {
                            if (!allowGlobs) {
                                outError[r5] = "pathPattern not allowed here; path must be literal";
                                return r5;
                            }
                            outInfo.addDataPath(str8, 2);
                        }
                        String str9 = sa2.getNonConfigurationString(10, r5);
                        if (str9 != null) {
                            if (!allowGlobs) {
                                outError[r5] = "pathAdvancedPattern not allowed here; path must be literal";
                                return r5;
                            }
                            outInfo.addDataPath(str9, 3);
                        }
                        sa2.recycle();
                        XmlUtils.skipCurrentTag(parser);
                    } else {
                        Slog.m50w(TAG, "Unknown element under <intent-filter>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                        XmlUtils.skipCurrentTag(parser);
                    }
                }
                resources = res;
                r5 = 0;
                i2 = 3;
                i4 = 1;
                i5 = 4;
            }
        }
        outInfo.hasDefault = outInfo.hasCategory(Intent.CATEGORY_DEFAULT);
        return true;
    }

    /* renamed from: android.content.pm.PackageParser$SigningDetails */
    /* loaded from: classes.dex */
    public static final class SigningDetails implements Parcelable {
        private static final int PAST_CERT_EXISTS = 0;
        public final Signature[] pastSigningCertificates;
        public final ArraySet<PublicKey> publicKeys;
        @SignatureSchemeVersion
        public final int signatureSchemeVersion;
        @UnsupportedAppUsage
        public final Signature[] signatures;
        public static final SigningDetails UNKNOWN = new SigningDetails(null, 0, null, null);
        public static final Parcelable.Creator<SigningDetails> CREATOR = new Parcelable.Creator<SigningDetails>() { // from class: android.content.pm.PackageParser.SigningDetails.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SigningDetails createFromParcel(Parcel source) {
                if (source.readBoolean()) {
                    return SigningDetails.UNKNOWN;
                }
                return new SigningDetails(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SigningDetails[] newArray(int size) {
                return new SigningDetails[size];
            }
        };

        /* renamed from: android.content.pm.PackageParser$SigningDetails$CertCapabilities */
        /* loaded from: classes.dex */
        public @interface CertCapabilities {
            public static final int AUTH = 16;
            public static final int INSTALLED_DATA = 1;
            public static final int PERMISSION = 4;
            public static final int ROLLBACK = 8;
            public static final int SHARED_USER_ID = 2;
        }

        /* renamed from: android.content.pm.PackageParser$SigningDetails$SignatureSchemeVersion */
        /* loaded from: classes.dex */
        public @interface SignatureSchemeVersion {
            public static final int JAR = 1;
            public static final int SIGNING_BLOCK_V2 = 2;
            public static final int SIGNING_BLOCK_V3 = 3;
            public static final int UNKNOWN = 0;
        }

        @VisibleForTesting
        public SigningDetails(Signature[] signatures, @SignatureSchemeVersion int signatureSchemeVersion, ArraySet<PublicKey> keys, Signature[] pastSigningCertificates) {
            this.signatures = signatures;
            this.signatureSchemeVersion = signatureSchemeVersion;
            this.publicKeys = keys;
            this.pastSigningCertificates = pastSigningCertificates;
        }

        public SigningDetails(Signature[] signatures, @SignatureSchemeVersion int signatureSchemeVersion, Signature[] pastSigningCertificates) throws CertificateException {
            this(signatures, signatureSchemeVersion, PackageParser.toSigningKeys(signatures), pastSigningCertificates);
        }

        public SigningDetails(Signature[] signatures, @SignatureSchemeVersion int signatureSchemeVersion) throws CertificateException {
            this(signatures, signatureSchemeVersion, null);
        }

        public SigningDetails(SigningDetails orig) {
            if (orig != null) {
                if (orig.signatures != null) {
                    this.signatures = (Signature[]) orig.signatures.clone();
                } else {
                    this.signatures = null;
                }
                this.signatureSchemeVersion = orig.signatureSchemeVersion;
                this.publicKeys = new ArraySet<>(orig.publicKeys);
                if (orig.pastSigningCertificates != null) {
                    this.pastSigningCertificates = (Signature[]) orig.pastSigningCertificates.clone();
                    return;
                } else {
                    this.pastSigningCertificates = null;
                    return;
                }
            }
            this.signatures = null;
            this.signatureSchemeVersion = 0;
            this.publicKeys = null;
            this.pastSigningCertificates = null;
        }

        public boolean hasSignatures() {
            return this.signatures != null && this.signatures.length > 0;
        }

        public boolean hasPastSigningCertificates() {
            return this.pastSigningCertificates != null && this.pastSigningCertificates.length > 0;
        }

        public boolean hasAncestorOrSelf(SigningDetails oldDetails) {
            if (this == UNKNOWN || oldDetails == UNKNOWN) {
                return false;
            }
            if (oldDetails.signatures.length > 1) {
                return signaturesMatchExactly(oldDetails);
            }
            return hasCertificate(oldDetails.signatures[0]);
        }

        public boolean hasAncestor(SigningDetails oldDetails) {
            if (this != UNKNOWN && oldDetails != UNKNOWN && hasPastSigningCertificates() && oldDetails.signatures.length == 1) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (this.pastSigningCertificates[i].equals(oldDetails.signatures[i])) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean checkCapability(SigningDetails oldDetails, @CertCapabilities int flags) {
            if (this == UNKNOWN || oldDetails == UNKNOWN) {
                return false;
            }
            if (oldDetails.signatures.length > 1) {
                return signaturesMatchExactly(oldDetails);
            }
            return hasCertificate(oldDetails.signatures[0], flags);
        }

        public boolean checkCapabilityRecover(SigningDetails oldDetails, @CertCapabilities int flags) throws CertificateException {
            if (oldDetails == UNKNOWN || this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates() && oldDetails.signatures.length == 1) {
                for (int i = 0; i < this.pastSigningCertificates.length; i++) {
                    if (Signature.areEffectiveMatch(oldDetails.signatures[0], this.pastSigningCertificates[i]) && this.pastSigningCertificates[i].getFlags() == flags) {
                        return true;
                    }
                }
                return false;
            }
            return Signature.areEffectiveMatch(oldDetails.signatures, this.signatures);
        }

        public boolean hasCertificate(Signature signature) {
            return hasCertificateInternal(signature, 0);
        }

        public boolean hasCertificate(Signature signature, @CertCapabilities int flags) {
            return hasCertificateInternal(signature, flags);
        }

        public boolean hasCertificate(byte[] certificate) {
            Signature signature = new Signature(certificate);
            return hasCertificate(signature);
        }

        private boolean hasCertificateInternal(Signature signature, int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (this.pastSigningCertificates[i].equals(signature) && (flags == 0 || (this.pastSigningCertificates[i].getFlags() & flags) == flags)) {
                        return true;
                    }
                }
            }
            return this.signatures.length == 1 && this.signatures[0].equals(signature);
        }

        public boolean checkCapability(String sha256String, @CertCapabilities int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            byte[] sha256Bytes = ByteStringUtils.fromHexToByteArray(sha256String);
            if (hasSha256Certificate(sha256Bytes, flags)) {
                return true;
            }
            String[] mSignaturesSha256Digests = PackageUtils.computeSignaturesSha256Digests(this.signatures);
            String mSignaturesSha256Digest = PackageUtils.computeSignaturesSha256Digest(mSignaturesSha256Digests);
            return mSignaturesSha256Digest.equals(sha256String);
        }

        public boolean hasSha256Certificate(byte[] sha256Certificate) {
            return hasSha256CertificateInternal(sha256Certificate, 0);
        }

        public boolean hasSha256Certificate(byte[] sha256Certificate, @CertCapabilities int flags) {
            return hasSha256CertificateInternal(sha256Certificate, flags);
        }

        private boolean hasSha256CertificateInternal(byte[] sha256Certificate, int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    byte[] digest = PackageUtils.computeSha256DigestBytes(this.pastSigningCertificates[i].toByteArray());
                    if (Arrays.equals(sha256Certificate, digest) && (flags == 0 || (this.pastSigningCertificates[i].getFlags() & flags) == flags)) {
                        return true;
                    }
                }
            }
            if (this.signatures.length == 1) {
                byte[] digest2 = PackageUtils.computeSha256DigestBytes(this.signatures[0].toByteArray());
                return Arrays.equals(sha256Certificate, digest2);
            }
            return false;
        }

        public boolean signaturesMatchExactly(SigningDetails other) {
            return Signature.areExactMatch(this.signatures, other.signatures);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            boolean isUnknown = UNKNOWN == this;
            dest.writeBoolean(isUnknown);
            if (isUnknown) {
                return;
            }
            dest.writeTypedArray(this.signatures, flags);
            dest.writeInt(this.signatureSchemeVersion);
            dest.writeArraySet(this.publicKeys);
            dest.writeTypedArray(this.pastSigningCertificates, flags);
        }

        protected SigningDetails(Parcel in) {
            ClassLoader boot = Object.class.getClassLoader();
            this.signatures = (Signature[]) in.createTypedArray(Signature.CREATOR);
            this.signatureSchemeVersion = in.readInt();
            this.publicKeys = in.readArraySet(boot);
            this.pastSigningCertificates = (Signature[]) in.createTypedArray(Signature.CREATOR);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof SigningDetails) {
                SigningDetails that = (SigningDetails) o;
                if (this.signatureSchemeVersion == that.signatureSchemeVersion && Signature.areExactMatch(this.signatures, that.signatures)) {
                    if (this.publicKeys != null) {
                        if (!this.publicKeys.equals(that.publicKeys)) {
                            return false;
                        }
                    } else if (that.publicKeys != null) {
                        return false;
                    }
                    return Arrays.equals(this.pastSigningCertificates, that.pastSigningCertificates);
                }
                return false;
            }
            return false;
        }

        public int hashCode() {
            int result = Arrays.hashCode(this.signatures);
            return (((((result * 31) + this.signatureSchemeVersion) * 31) + (this.publicKeys != null ? this.publicKeys.hashCode() : 0)) * 31) + Arrays.hashCode(this.pastSigningCertificates);
        }

        /* renamed from: android.content.pm.PackageParser$SigningDetails$Builder */
        /* loaded from: classes.dex */
        public static class Builder {
            private Signature[] mPastSigningCertificates;
            private int mSignatureSchemeVersion = 0;
            private Signature[] mSignatures;

            @UnsupportedAppUsage
            public Builder setSignatures(Signature[] signatures) {
                this.mSignatures = signatures;
                return this;
            }

            @UnsupportedAppUsage
            public Builder setSignatureSchemeVersion(int signatureSchemeVersion) {
                this.mSignatureSchemeVersion = signatureSchemeVersion;
                return this;
            }

            @UnsupportedAppUsage
            public Builder setPastSigningCertificates(Signature[] pastSigningCertificates) {
                this.mPastSigningCertificates = pastSigningCertificates;
                return this;
            }

            private void checkInvariants() {
                if (this.mSignatures == null) {
                    throw new IllegalStateException("SigningDetails requires the current signing certificates.");
                }
            }

            @UnsupportedAppUsage
            public SigningDetails build() throws CertificateException {
                checkInvariants();
                return new SigningDetails(this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
            }
        }
    }

    /* renamed from: android.content.pm.PackageParser$Package */
    /* loaded from: classes.dex */
    public static final class Package implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Package>() { // from class: android.content.pm.PackageParser.Package.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Package createFromParcel(Parcel in) {
                return new Package(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Package[] newArray(int size) {
                return new Package[size];
            }
        };
        @UnsupportedAppUsage
        public final ArrayList<Activity> activities;
        @UnsupportedAppUsage
        public ApplicationInfo applicationInfo;
        public String baseCodePath;
        public boolean baseHardwareAccelerated;
        public int baseRevisionCode;
        public ArrayList<Package> childPackages;
        public String codePath;
        @UnsupportedAppUsage
        public ArrayList<ConfigurationInfo> configPreferences;
        public boolean coreApp;
        public String cpuAbiOverride;
        public ArrayList<FeatureGroupInfo> featureGroups;
        public final ArrayList<String> implicitPermissions;
        @UnsupportedAppUsage
        public int installLocation;
        @UnsupportedAppUsage
        public final ArrayList<Instrumentation> instrumentation;
        public boolean isStub;
        public ArrayList<String> libraryNames;
        public ArrayList<String> mAdoptPermissions;
        @UnsupportedAppUsage
        public Bundle mAppMetaData;
        public int mCompileSdkVersion;
        public String mCompileSdkVersionCodename;
        @UnsupportedAppUsage
        public Object mExtras;
        @UnsupportedAppUsage
        public ArrayMap<String, ArraySet<PublicKey>> mKeySetMapping;
        public long[] mLastPackageUsageTimeInMills;
        public ArrayList<String> mOriginalPackages;
        public String mOverlayCategory;
        public boolean mOverlayIsStatic;
        public int mOverlayPriority;
        public String mOverlayTarget;
        public String mOverlayTargetName;
        @UnsupportedAppUsage
        public int mPreferredOrder;
        public String mRealPackage;
        public String mRequiredAccountType;
        public boolean mRequiredForAllUsers;
        public String mRestrictedAccountType;
        @UnsupportedAppUsage
        public String mSharedUserId;
        @UnsupportedAppUsage
        public int mSharedUserLabel;
        @UnsupportedAppUsage
        public SigningDetails mSigningDetails;
        @UnsupportedAppUsage
        public ArraySet<String> mUpgradeKeySets;
        @UnsupportedAppUsage
        public int mVersionCode;
        public int mVersionCodeMajor;
        @UnsupportedAppUsage
        public String mVersionName;
        public String manifestPackageName;
        @UnsupportedAppUsage
        public String packageName;
        public Package parentPackage;
        @UnsupportedAppUsage
        public final ArrayList<PermissionGroup> permissionGroups;
        @UnsupportedAppUsage
        public final ArrayList<Permission> permissions;
        public ArrayList<ActivityIntentInfo> preferredActivityFilters;
        @UnsupportedAppUsage
        public ArrayList<String> protectedBroadcasts;
        @UnsupportedAppUsage
        public final ArrayList<Provider> providers;
        @UnsupportedAppUsage
        public final ArrayList<Activity> receivers;
        @UnsupportedAppUsage
        public ArrayList<FeatureInfo> reqFeatures;
        @UnsupportedAppUsage
        public final ArrayList<String> requestedPermissions;
        public byte[] restrictUpdateHash;
        @UnsupportedAppUsage
        public final ArrayList<Service> services;
        public String[] splitCodePaths;
        public int[] splitFlags;
        public String[] splitNames;
        public int[] splitPrivateFlags;
        public int[] splitRevisionCodes;
        public String staticSharedLibName;
        public long staticSharedLibVersion;
        public boolean use32bitAbi;
        @UnsupportedAppUsage
        public ArrayList<String> usesLibraries;
        @UnsupportedAppUsage
        public String[] usesLibraryFiles;
        public ArrayList<SharedLibraryInfo> usesLibraryInfos;
        @UnsupportedAppUsage
        public ArrayList<String> usesOptionalLibraries;
        public ArrayList<String> usesStaticLibraries;
        public String[][] usesStaticLibrariesCertDigests;
        public long[] usesStaticLibrariesVersions;
        public boolean visibleToInstantApps;
        public String volumeUuid;

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
        }

        @UnsupportedAppUsage
        public Package(String packageName) {
            this.applicationInfo = new ApplicationInfo();
            this.permissions = new ArrayList<>(0);
            this.permissionGroups = new ArrayList<>(0);
            this.activities = new ArrayList<>(0);
            this.receivers = new ArrayList<>(0);
            this.providers = new ArrayList<>(0);
            this.services = new ArrayList<>(0);
            this.instrumentation = new ArrayList<>(0);
            this.requestedPermissions = new ArrayList<>();
            this.implicitPermissions = new ArrayList<>();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            this.packageName = packageName;
            this.manifestPackageName = packageName;
            this.applicationInfo.packageName = packageName;
            this.applicationInfo.uid = -1;
        }

        public void setApplicationVolumeUuid(String volumeUuid) {
            UUID storageUuid = StorageManager.convert(volumeUuid);
            this.applicationInfo.volumeUuid = volumeUuid;
            this.applicationInfo.storageUuid = storageUuid;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.volumeUuid = volumeUuid;
                    this.childPackages.get(i).applicationInfo.storageUuid = storageUuid;
                }
            }
        }

        public void setApplicationInfoCodePath(String codePath) {
            this.applicationInfo.setCodePath(codePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setCodePath(codePath);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoResourcePath(String resourcePath) {
            this.applicationInfo.setResourcePath(resourcePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setResourcePath(resourcePath);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoBaseResourcePath(String resourcePath) {
            this.applicationInfo.setBaseResourcePath(resourcePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseResourcePath(resourcePath);
                }
            }
        }

        public void setApplicationInfoBaseCodePath(String baseCodePath) {
            this.applicationInfo.setBaseCodePath(baseCodePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseCodePath(baseCodePath);
                }
            }
        }

        public List<String> getChildPackageNames() {
            if (this.childPackages == null) {
                return null;
            }
            int childCount = this.childPackages.size();
            List<String> childPackageNames = new ArrayList<>(childCount);
            for (int i = 0; i < childCount; i++) {
                String childPackageName = this.childPackages.get(i).packageName;
                childPackageNames.add(childPackageName);
            }
            return childPackageNames;
        }

        public boolean hasChildPackage(String packageName) {
            int childCount = this.childPackages != null ? this.childPackages.size() : 0;
            for (int i = 0; i < childCount; i++) {
                if (this.childPackages.get(i).packageName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }

        public void setApplicationInfoSplitCodePaths(String[] splitCodePaths) {
            this.applicationInfo.setSplitCodePaths(splitCodePaths);
        }

        @Deprecated
        public void setApplicationInfoSplitResourcePaths(String[] resroucePaths) {
            this.applicationInfo.setSplitResourcePaths(resroucePaths);
        }

        public void setSplitCodePaths(String[] codePaths) {
            this.splitCodePaths = codePaths;
        }

        public void setCodePath(String codePath) {
            this.codePath = codePath;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).codePath = codePath;
                }
            }
        }

        public void setBaseCodePath(String baseCodePath) {
            this.baseCodePath = baseCodePath;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).baseCodePath = baseCodePath;
                }
            }
        }

        public void setSigningDetails(SigningDetails signingDetails) {
            this.mSigningDetails = signingDetails;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).mSigningDetails = signingDetails;
                }
            }
        }

        public void setVolumeUuid(String volumeUuid) {
            this.volumeUuid = volumeUuid;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).volumeUuid = volumeUuid;
                }
            }
        }

        public void setApplicationInfoFlags(int mask, int flags) {
            this.applicationInfo.flags = (this.applicationInfo.flags & (~mask)) | (mask & flags);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.flags = (this.applicationInfo.flags & (~mask)) | (mask & flags);
                }
            }
        }

        public void setUse32bitAbi(boolean use32bitAbi) {
            this.use32bitAbi = use32bitAbi;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).use32bitAbi = use32bitAbi;
                }
            }
        }

        public boolean isLibrary() {
            return (this.staticSharedLibName == null && ArrayUtils.isEmpty(this.libraryNames)) ? false : true;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList<>();
            paths.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }
            return paths;
        }

        public List<String> getAllCodePathsExcludingResourceOnly() {
            ArrayList<String> paths = new ArrayList<>();
            if ((this.applicationInfo.flags & 4) != 0) {
                paths.add(this.baseCodePath);
            }
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                for (int i = 0; i < this.splitCodePaths.length; i++) {
                    if ((this.splitFlags[i] & 4) != 0) {
                        paths.add(this.splitCodePaths[i]);
                    }
                }
            }
            return paths;
        }

        @UnsupportedAppUsage
        public void setPackageName(String newName) {
            this.packageName = newName;
            this.applicationInfo.packageName = newName;
            for (int i = this.permissions.size() - 1; i >= 0; i--) {
                this.permissions.get(i).setPackageName(newName);
            }
            for (int i2 = this.permissionGroups.size() - 1; i2 >= 0; i2--) {
                this.permissionGroups.get(i2).setPackageName(newName);
            }
            for (int i3 = this.activities.size() - 1; i3 >= 0; i3--) {
                this.activities.get(i3).setPackageName(newName);
            }
            for (int i4 = this.receivers.size() - 1; i4 >= 0; i4--) {
                this.receivers.get(i4).setPackageName(newName);
            }
            for (int i5 = this.providers.size() - 1; i5 >= 0; i5--) {
                this.providers.get(i5).setPackageName(newName);
            }
            for (int i6 = this.services.size() - 1; i6 >= 0; i6--) {
                this.services.get(i6).setPackageName(newName);
            }
            for (int i7 = this.instrumentation.size() - 1; i7 >= 0; i7--) {
                this.instrumentation.get(i7).setPackageName(newName);
            }
        }

        public boolean hasComponentClassName(String name) {
            for (int i = this.activities.size() - 1; i >= 0; i--) {
                if (name.equals(this.activities.get(i).className)) {
                    return true;
                }
            }
            for (int i2 = this.receivers.size() - 1; i2 >= 0; i2--) {
                if (name.equals(this.receivers.get(i2).className)) {
                    return true;
                }
            }
            for (int i3 = this.providers.size() - 1; i3 >= 0; i3--) {
                if (name.equals(this.providers.get(i3).className)) {
                    return true;
                }
            }
            for (int i4 = this.services.size() - 1; i4 >= 0; i4--) {
                if (name.equals(this.services.get(i4).className)) {
                    return true;
                }
            }
            for (int i5 = this.instrumentation.size() - 1; i5 >= 0; i5--) {
                if (name.equals(this.instrumentation.get(i5).className)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isExternal() {
            return this.applicationInfo.isExternal();
        }

        public boolean isForwardLocked() {
            return false;
        }

        public boolean isOem() {
            return this.applicationInfo.isOem();
        }

        public boolean isVendor() {
            return this.applicationInfo.isVendor();
        }

        public boolean isProduct() {
            return this.applicationInfo.isProduct();
        }

        public boolean isProductServices() {
            return this.applicationInfo.isProductServices();
        }

        public boolean isOdm() {
            return this.applicationInfo.isOdm();
        }

        public boolean isPrivileged() {
            return this.applicationInfo.isPrivilegedApp();
        }

        public boolean isSystem() {
            return this.applicationInfo.isSystemApp();
        }

        public boolean isUpdatedSystemApp() {
            return this.applicationInfo.isUpdatedSystemApp();
        }

        public boolean canHaveOatDir() {
            return !isSystem() || isUpdatedSystemApp();
        }

        public boolean isMatch(int flags) {
            if ((1048576 & flags) != 0) {
                return isSystem();
            }
            return true;
        }

        public long getLatestPackageUseTimeInMills() {
            long[] jArr;
            long latestUse = 0;
            for (long use : this.mLastPackageUsageTimeInMills) {
                latestUse = Math.max(latestUse, use);
            }
            return latestUse;
        }

        public long getLatestForegroundPackageUseTimeInMills() {
            int[] foregroundReasons = {0, 2};
            long latestUse = 0;
            for (int reason : foregroundReasons) {
                latestUse = Math.max(latestUse, this.mLastPackageUsageTimeInMills[reason]);
            }
            return latestUse;
        }

        public String toString() {
            return "Package{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.packageName + "}";
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Package(Parcel dest) {
            this.applicationInfo = new ApplicationInfo();
            this.permissions = new ArrayList<>(0);
            this.permissionGroups = new ArrayList<>(0);
            this.activities = new ArrayList<>(0);
            this.receivers = new ArrayList<>(0);
            this.providers = new ArrayList<>(0);
            this.services = new ArrayList<>(0);
            this.instrumentation = new ArrayList<>(0);
            this.requestedPermissions = new ArrayList<>();
            this.implicitPermissions = new ArrayList<>();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            ClassLoader boot = Object.class.getClassLoader();
            this.packageName = dest.readString().intern();
            this.manifestPackageName = dest.readString();
            this.splitNames = dest.readStringArray();
            this.volumeUuid = dest.readString();
            this.codePath = dest.readString();
            this.baseCodePath = dest.readString();
            this.splitCodePaths = dest.readStringArray();
            this.baseRevisionCode = dest.readInt();
            this.splitRevisionCodes = dest.createIntArray();
            this.splitFlags = dest.createIntArray();
            this.splitPrivateFlags = dest.createIntArray();
            this.baseHardwareAccelerated = dest.readInt() == 1;
            this.applicationInfo = (ApplicationInfo) dest.readParcelable(boot);
            if (this.applicationInfo.permission != null) {
                this.applicationInfo.permission = this.applicationInfo.permission.intern();
            }
            dest.readParcelableList(this.permissions, boot);
            fixupOwner(this.permissions);
            dest.readParcelableList(this.permissionGroups, boot);
            fixupOwner(this.permissionGroups);
            dest.readParcelableList(this.activities, boot);
            fixupOwner(this.activities);
            dest.readParcelableList(this.receivers, boot);
            fixupOwner(this.receivers);
            dest.readParcelableList(this.providers, boot);
            fixupOwner(this.providers);
            dest.readParcelableList(this.services, boot);
            fixupOwner(this.services);
            dest.readParcelableList(this.instrumentation, boot);
            fixupOwner(this.instrumentation);
            dest.readStringList(this.requestedPermissions);
            internStringArrayList(this.requestedPermissions);
            dest.readStringList(this.implicitPermissions);
            internStringArrayList(this.implicitPermissions);
            this.protectedBroadcasts = dest.createStringArrayList();
            internStringArrayList(this.protectedBroadcasts);
            this.parentPackage = (Package) dest.readParcelable(boot);
            this.childPackages = new ArrayList<>();
            dest.readParcelableList(this.childPackages, boot);
            if (this.childPackages.size() == 0) {
                this.childPackages = null;
            }
            this.staticSharedLibName = dest.readString();
            if (this.staticSharedLibName != null) {
                this.staticSharedLibName = this.staticSharedLibName.intern();
            }
            this.staticSharedLibVersion = dest.readLong();
            this.libraryNames = dest.createStringArrayList();
            internStringArrayList(this.libraryNames);
            this.usesLibraries = dest.createStringArrayList();
            internStringArrayList(this.usesLibraries);
            this.usesOptionalLibraries = dest.createStringArrayList();
            internStringArrayList(this.usesOptionalLibraries);
            this.usesLibraryFiles = dest.readStringArray();
            this.usesLibraryInfos = dest.createTypedArrayList(SharedLibraryInfo.CREATOR);
            int libCount = dest.readInt();
            if (libCount > 0) {
                this.usesStaticLibraries = new ArrayList<>(libCount);
                dest.readStringList(this.usesStaticLibraries);
                internStringArrayList(this.usesStaticLibraries);
                this.usesStaticLibrariesVersions = new long[libCount];
                dest.readLongArray(this.usesStaticLibrariesVersions);
                this.usesStaticLibrariesCertDigests = new String[libCount];
                for (int i = 0; i < libCount; i++) {
                    this.usesStaticLibrariesCertDigests[i] = dest.createStringArray();
                }
            }
            this.preferredActivityFilters = new ArrayList<>();
            dest.readParcelableList(this.preferredActivityFilters, boot);
            if (this.preferredActivityFilters.size() == 0) {
                this.preferredActivityFilters = null;
            }
            this.mOriginalPackages = dest.createStringArrayList();
            this.mRealPackage = dest.readString();
            this.mAdoptPermissions = dest.createStringArrayList();
            this.mAppMetaData = dest.readBundle();
            this.mVersionCode = dest.readInt();
            this.mVersionCodeMajor = dest.readInt();
            this.mVersionName = dest.readString();
            if (this.mVersionName != null) {
                this.mVersionName = this.mVersionName.intern();
            }
            this.mSharedUserId = dest.readString();
            if (this.mSharedUserId != null) {
                this.mSharedUserId = this.mSharedUserId.intern();
            }
            this.mSharedUserLabel = dest.readInt();
            this.mSigningDetails = (SigningDetails) dest.readParcelable(boot);
            this.mPreferredOrder = dest.readInt();
            this.configPreferences = new ArrayList<>();
            dest.readParcelableList(this.configPreferences, boot);
            if (this.configPreferences.size() == 0) {
                this.configPreferences = null;
            }
            this.reqFeatures = new ArrayList<>();
            dest.readParcelableList(this.reqFeatures, boot);
            if (this.reqFeatures.size() == 0) {
                this.reqFeatures = null;
            }
            this.featureGroups = new ArrayList<>();
            dest.readParcelableList(this.featureGroups, boot);
            if (this.featureGroups.size() == 0) {
                this.featureGroups = null;
            }
            this.installLocation = dest.readInt();
            this.coreApp = dest.readInt() == 1;
            this.mRequiredForAllUsers = dest.readInt() == 1;
            this.mRestrictedAccountType = dest.readString();
            this.mRequiredAccountType = dest.readString();
            this.mOverlayTarget = dest.readString();
            this.mOverlayTargetName = dest.readString();
            this.mOverlayCategory = dest.readString();
            this.mOverlayPriority = dest.readInt();
            this.mOverlayIsStatic = dest.readInt() == 1;
            this.mCompileSdkVersion = dest.readInt();
            this.mCompileSdkVersionCodename = dest.readString();
            this.mUpgradeKeySets = dest.readArraySet(boot);
            this.mKeySetMapping = readKeySetMapping(dest);
            this.cpuAbiOverride = dest.readString();
            this.use32bitAbi = dest.readInt() == 1;
            this.restrictUpdateHash = dest.createByteArray();
            this.visibleToInstantApps = dest.readInt() == 1;
        }

        private static void internStringArrayList(List<String> list) {
            if (list != null) {
                int N = list.size();
                for (int i = 0; i < N; i++) {
                    list.set(i, list.get(i).intern());
                }
            }
        }

        private void fixupOwner(List<? extends Component<?>> list) {
            if (list != null) {
                for (Component<?> c : list) {
                    if (c != null) {
                        c.owner = this;
                        if (c instanceof Activity) {
                            ((Activity) c).info.applicationInfo = this.applicationInfo;
                        } else if (c instanceof Service) {
                            ((Service) c).info.applicationInfo = this.applicationInfo;
                        } else if (c instanceof Provider) {
                            ((Provider) c).info.applicationInfo = this.applicationInfo;
                        }
                    }
                }
            }
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            String[][] strArr;
            dest.writeString(this.packageName);
            dest.writeString(this.manifestPackageName);
            dest.writeStringArray(this.splitNames);
            dest.writeString(this.volumeUuid);
            dest.writeString(this.codePath);
            dest.writeString(this.baseCodePath);
            dest.writeStringArray(this.splitCodePaths);
            dest.writeInt(this.baseRevisionCode);
            dest.writeIntArray(this.splitRevisionCodes);
            dest.writeIntArray(this.splitFlags);
            dest.writeIntArray(this.splitPrivateFlags);
            dest.writeInt(this.baseHardwareAccelerated ? 1 : 0);
            dest.writeParcelable(this.applicationInfo, flags);
            dest.writeParcelableList(this.permissions, flags);
            dest.writeParcelableList(this.permissionGroups, flags);
            dest.writeParcelableList(this.activities, flags);
            dest.writeParcelableList(this.receivers, flags);
            dest.writeParcelableList(this.providers, flags);
            dest.writeParcelableList(this.services, flags);
            dest.writeParcelableList(this.instrumentation, flags);
            dest.writeStringList(this.requestedPermissions);
            dest.writeStringList(this.implicitPermissions);
            dest.writeStringList(this.protectedBroadcasts);
            dest.writeParcelable(this.parentPackage, flags);
            dest.writeParcelableList(this.childPackages, flags);
            dest.writeString(this.staticSharedLibName);
            dest.writeLong(this.staticSharedLibVersion);
            dest.writeStringList(this.libraryNames);
            dest.writeStringList(this.usesLibraries);
            dest.writeStringList(this.usesOptionalLibraries);
            dest.writeStringArray(this.usesLibraryFiles);
            dest.writeTypedList(this.usesLibraryInfos);
            if (ArrayUtils.isEmpty(this.usesStaticLibraries)) {
                dest.writeInt(-1);
            } else {
                dest.writeInt(this.usesStaticLibraries.size());
                dest.writeStringList(this.usesStaticLibraries);
                dest.writeLongArray(this.usesStaticLibrariesVersions);
                for (String[] usesStaticLibrariesCertDigest : this.usesStaticLibrariesCertDigests) {
                    dest.writeStringArray(usesStaticLibrariesCertDigest);
                }
            }
            dest.writeParcelableList(this.preferredActivityFilters, flags);
            dest.writeStringList(this.mOriginalPackages);
            dest.writeString(this.mRealPackage);
            dest.writeStringList(this.mAdoptPermissions);
            dest.writeBundle(this.mAppMetaData);
            dest.writeInt(this.mVersionCode);
            dest.writeInt(this.mVersionCodeMajor);
            dest.writeString(this.mVersionName);
            dest.writeString(this.mSharedUserId);
            dest.writeInt(this.mSharedUserLabel);
            dest.writeParcelable(this.mSigningDetails, flags);
            dest.writeInt(this.mPreferredOrder);
            dest.writeParcelableList(this.configPreferences, flags);
            dest.writeParcelableList(this.reqFeatures, flags);
            dest.writeParcelableList(this.featureGroups, flags);
            dest.writeInt(this.installLocation);
            dest.writeInt(this.coreApp ? 1 : 0);
            dest.writeInt(this.mRequiredForAllUsers ? 1 : 0);
            dest.writeString(this.mRestrictedAccountType);
            dest.writeString(this.mRequiredAccountType);
            dest.writeString(this.mOverlayTarget);
            dest.writeString(this.mOverlayTargetName);
            dest.writeString(this.mOverlayCategory);
            dest.writeInt(this.mOverlayPriority);
            dest.writeInt(this.mOverlayIsStatic ? 1 : 0);
            dest.writeInt(this.mCompileSdkVersion);
            dest.writeString(this.mCompileSdkVersionCodename);
            dest.writeArraySet(this.mUpgradeKeySets);
            writeKeySetMapping(dest, this.mKeySetMapping);
            dest.writeString(this.cpuAbiOverride);
            dest.writeInt(this.use32bitAbi ? 1 : 0);
            dest.writeByteArray(this.restrictUpdateHash);
            dest.writeInt(this.visibleToInstantApps ? 1 : 0);
        }

        private static void writeKeySetMapping(Parcel dest, ArrayMap<String, ArraySet<PublicKey>> keySetMapping) {
            if (keySetMapping == null) {
                dest.writeInt(-1);
                return;
            }
            int N = keySetMapping.size();
            dest.writeInt(N);
            for (int i = 0; i < N; i++) {
                dest.writeString(keySetMapping.keyAt(i));
                ArraySet<PublicKey> keys = keySetMapping.valueAt(i);
                if (keys == null) {
                    dest.writeInt(-1);
                } else {
                    int M = keys.size();
                    dest.writeInt(M);
                    for (int j = 0; j < M; j++) {
                        dest.writeSerializable(keys.valueAt(j));
                    }
                }
            }
        }

        private static ArrayMap<String, ArraySet<PublicKey>> readKeySetMapping(Parcel in) {
            int N = in.readInt();
            if (N == -1) {
                return null;
            }
            ArrayMap<String, ArraySet<PublicKey>> keySetMapping = new ArrayMap<>();
            for (int i = 0; i < N; i++) {
                String key = in.readString();
                int M = in.readInt();
                if (M == -1) {
                    keySetMapping.put(key, null);
                } else {
                    ArraySet<PublicKey> keys = new ArraySet<>(M);
                    for (int j = 0; j < M; j++) {
                        PublicKey pk = (PublicKey) in.readSerializable();
                        keys.add(pk);
                    }
                    keySetMapping.put(key, keys);
                }
            }
            return keySetMapping;
        }
    }

    /* renamed from: android.content.pm.PackageParser$Component */
    /* loaded from: classes.dex */
    public static abstract class Component<II extends IntentInfo> {
        @UnsupportedAppUsage
        public final String className;
        ComponentName componentName;
        String componentShortName;
        @UnsupportedAppUsage
        public final ArrayList<II> intents;
        @UnsupportedAppUsage
        public Bundle metaData;
        public int order;
        @UnsupportedAppUsage
        public Package owner;

        public Component(Package owner, ArrayList<II> intents, String className) {
            this.owner = owner;
            this.intents = intents;
            this.className = className;
        }

        public Component(Package owner) {
            this.owner = owner;
            this.intents = null;
            this.className = null;
        }

        public Component(ParsePackageItemArgs args, PackageItemInfo outInfo) {
            this.owner = args.owner;
            this.intents = new ArrayList<>(0);
            if (PackageParser.parsePackageItemInfo(args.owner, outInfo, args.outError, args.tag, args.f29sa, true, args.nameRes, args.labelRes, args.iconRes, args.roundIconRes, args.logoRes, args.bannerRes)) {
                this.className = outInfo.name;
            } else {
                this.className = null;
            }
        }

        public Component(ParseComponentArgs args, ComponentInfo outInfo) {
            this((ParsePackageItemArgs) args, (PackageItemInfo) outInfo);
            if (args.outError[0] != null) {
                return;
            }
            if (args.processRes != 0) {
                CharSequence pname = this.owner.applicationInfo.targetSdkVersion >= 8 ? args.f29sa.getNonConfigurationString(args.processRes, 1024) : args.f29sa.getNonResourceString(args.processRes);
                outInfo.processName = PackageParser.buildProcessName(this.owner.applicationInfo.packageName, this.owner.applicationInfo.processName, pname, args.flags, args.sepProcesses, args.outError);
            }
            if (args.descriptionRes != 0) {
                outInfo.descriptionRes = args.f29sa.getResourceId(args.descriptionRes, 0);
            }
            outInfo.enabled = args.f29sa.getBoolean(args.enabledRes, true);
        }

        public Component(Component<II> clone) {
            this.owner = clone.owner;
            this.intents = clone.intents;
            this.className = clone.className;
            this.componentName = clone.componentName;
            this.componentShortName = clone.componentShortName;
        }

        @UnsupportedAppUsage
        public ComponentName getComponentName() {
            if (this.componentName != null) {
                return this.componentName;
            }
            if (this.className != null) {
                this.componentName = new ComponentName(this.owner.applicationInfo.packageName, this.className);
            }
            return this.componentName;
        }

        protected Component(Parcel in) {
            this.className = in.readString();
            this.metaData = in.readBundle();
            this.intents = createIntentsList(in);
            this.owner = null;
        }

        protected void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.className);
            dest.writeBundle(this.metaData);
            writeIntentsList(this.intents, dest, flags);
        }

        private static void writeIntentsList(ArrayList<? extends IntentInfo> list, Parcel out, int flags) {
            if (list == null) {
                out.writeInt(-1);
                return;
            }
            int N = list.size();
            out.writeInt(N);
            if (N > 0) {
                IntentInfo info = list.get(0);
                out.writeString(info.getClass().getName());
                for (int i = 0; i < N; i++) {
                    list.get(i).writeIntentInfoToParcel(out, flags);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static <T extends IntentInfo> ArrayList<T> createIntentsList(Parcel in) {
            int N = in.readInt();
            if (N == -1) {
                return null;
            }
            if (N == 0) {
                return new ArrayList<>(0);
            }
            String componentName = in.readString();
            try {
                Constructor<?> constructor = Class.forName(componentName).getConstructor(Parcel.class);
                ArrayList<T> intentsList = (ArrayList<T>) new ArrayList(N);
                for (int i = 0; i < N; i++) {
                    intentsList.add((IntentInfo) constructor.newInstance(in));
                }
                return intentsList;
            } catch (ReflectiveOperationException e) {
                throw new AssertionError("Unable to construct intent list for: " + componentName);
            }
        }

        public void appendComponentShortName(StringBuilder sb) {
            ComponentName.appendShortString(sb, this.owner.applicationInfo.packageName, this.className);
        }

        public void printComponentShortName(PrintWriter pw) {
            ComponentName.printShortString(pw, this.owner.applicationInfo.packageName, this.className);
        }

        public void setPackageName(String packageName) {
            this.componentName = null;
            this.componentShortName = null;
        }
    }

    /* renamed from: android.content.pm.PackageParser$Permission */
    /* loaded from: classes.dex */
    public static final class Permission extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Permission>() { // from class: android.content.pm.PackageParser.Permission.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Permission createFromParcel(Parcel in) {
                return new Permission(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Permission[] newArray(int size) {
                return new Permission[size];
            }
        };
        @UnsupportedAppUsage
        public PermissionGroup group;
        @UnsupportedAppUsage
        public final PermissionInfo info;
        @UnsupportedAppUsage
        public boolean tree;

        public Permission(Package owner, String backgroundPermission) {
            super(owner);
            this.info = new PermissionInfo(backgroundPermission);
        }

        @UnsupportedAppUsage
        public Permission(Package _owner, PermissionInfo _info) {
            super(_owner);
            this.info = _info;
        }

        @Override // android.content.p002pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            return "Permission{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.info.name + "}";
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.p002pm.PackageParser.Component, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags);
            dest.writeInt(this.tree ? 1 : 0);
            dest.writeParcelable(this.group, flags);
        }

        public boolean isAppOp() {
            return this.info.isAppOp();
        }

        private Permission(Parcel in) {
            super(in);
            ClassLoader boot = Object.class.getClassLoader();
            this.info = (PermissionInfo) in.readParcelable(boot);
            if (this.info.group != null) {
                this.info.group = this.info.group.intern();
            }
            this.tree = in.readInt() == 1;
            this.group = (PermissionGroup) in.readParcelable(boot);
        }
    }

    /* renamed from: android.content.pm.PackageParser$PermissionGroup */
    /* loaded from: classes.dex */
    public static final class PermissionGroup extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<PermissionGroup>() { // from class: android.content.pm.PackageParser.PermissionGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public PermissionGroup createFromParcel(Parcel in) {
                return new PermissionGroup(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public PermissionGroup[] newArray(int size) {
                return new PermissionGroup[size];
            }
        };
        @UnsupportedAppUsage
        public final PermissionGroupInfo info;

        public PermissionGroup(Package owner, int requestDetailResourceId, int backgroundRequestResourceId, int backgroundRequestDetailResourceId) {
            super(owner);
            this.info = new PermissionGroupInfo(requestDetailResourceId, backgroundRequestResourceId, backgroundRequestDetailResourceId);
        }

        public PermissionGroup(Package _owner, PermissionGroupInfo _info) {
            super(_owner);
            this.info = _info;
        }

        @Override // android.content.p002pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            return "PermissionGroup{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.info.name + "}";
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.p002pm.PackageParser.Component, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags);
        }

        private PermissionGroup(Parcel in) {
            super(in);
            this.info = (PermissionGroupInfo) in.readParcelable(Object.class.getClassLoader());
        }
    }

    private static boolean copyNeeded(int flags, Package p, PackageUserState state, Bundle metaData, int userId) {
        if (userId != 0) {
            return true;
        }
        if (state.enabled != 0) {
            boolean enabled = state.enabled == 1;
            if (p.applicationInfo.enabled != enabled) {
                return true;
            }
        }
        boolean suspended = (p.applicationInfo.flags & 1073741824) != 0;
        if (state.suspended != suspended || !state.installed || state.hidden || state.stopped || state.instantApp != p.applicationInfo.isInstantApp()) {
            return true;
        }
        if ((flags & 128) != 0 && (metaData != null || p.mAppMetaData != null)) {
            return true;
        }
        if ((flags & 1024) == 0 || p.usesLibraryFiles == null) {
            return (((flags & 1024) == 0 || p.usesLibraryInfos == null) && p.staticSharedLibName == null) ? false : true;
        }
        return true;
    }

    @UnsupportedAppUsage
    public static ApplicationInfo generateApplicationInfo(Package p, int flags, PackageUserState state) {
        return generateApplicationInfo(p, flags, state, UserHandle.getCallingUserId());
    }

    private static void updateApplicationInfo(ApplicationInfo ai, int flags, PackageUserState state) {
        if (!sCompatibilityModeEnabled) {
            ai.disableCompatibilityMode();
        }
        if (state.installed) {
            ai.flags |= 8388608;
        } else {
            ai.flags &= -8388609;
        }
        if (state.suspended) {
            ai.flags |= 1073741824;
        } else {
            ai.flags &= -1073741825;
        }
        if (state.instantApp) {
            ai.privateFlags |= 128;
        } else {
            ai.privateFlags &= -129;
        }
        if (state.virtualPreload) {
            ai.privateFlags |= 65536;
        } else {
            ai.privateFlags &= -65537;
        }
        if (state.hidden) {
            ai.privateFlags |= 1;
        } else {
            ai.privateFlags &= -2;
        }
        if (state.enabled == 1) {
            ai.enabled = true;
        } else if (state.enabled == 4) {
            ai.enabled = (32768 & flags) != 0;
        } else if (state.enabled == 2 || state.enabled == 3) {
            ai.enabled = false;
        }
        ai.enabledSetting = state.enabled;
        if (ai.category == -1) {
            ai.category = state.categoryHint;
        }
        if (ai.category == -1) {
            ai.category = FallbackCategoryProvider.getFallbackCategory(ai.packageName);
        }
        ai.seInfoUser = SELinuxUtil.assignSeinfoUser(state);
        ai.resourceDirs = state.overlayPaths;
        ai.icon = (!sUseRoundIcon || ai.roundIconRes == 0) ? ai.iconRes : ai.roundIconRes;
    }

    @UnsupportedAppUsage
    public static ApplicationInfo generateApplicationInfo(Package p, int flags, PackageUserState state, int userId) {
        if (p == null || !checkUseInstalledOrHidden(flags, state, p.applicationInfo) || !p.isMatch(flags)) {
            return null;
        }
        if (!copyNeeded(flags, p, state, null, userId) && ((32768 & flags) == 0 || state.enabled != 4)) {
            updateApplicationInfo(p.applicationInfo, flags, state);
            return p.applicationInfo;
        }
        ApplicationInfo ai = new ApplicationInfo(p.applicationInfo);
        ai.initForUser(userId);
        if ((flags & 128) != 0) {
            ai.metaData = p.mAppMetaData;
        }
        if ((flags & 1024) != 0) {
            ai.sharedLibraryFiles = p.usesLibraryFiles;
            ai.sharedLibraryInfos = p.usesLibraryInfos;
        }
        if (state.stopped) {
            ai.flags |= 2097152;
        } else {
            ai.flags &= -2097153;
        }
        updateApplicationInfo(ai, flags, state);
        return ai;
    }

    public static ApplicationInfo generateApplicationInfo(ApplicationInfo ai, int flags, PackageUserState state, int userId) {
        if (ai == null || !checkUseInstalledOrHidden(flags, state, ai)) {
            return null;
        }
        ApplicationInfo ai2 = new ApplicationInfo(ai);
        ai2.initForUser(userId);
        if (state.stopped) {
            ai2.flags |= 2097152;
        } else {
            ai2.flags &= -2097153;
        }
        updateApplicationInfo(ai2, flags, state);
        return ai2;
    }

    @UnsupportedAppUsage
    public static final PermissionInfo generatePermissionInfo(Permission p, int flags) {
        if (p == null) {
            return null;
        }
        if ((flags & 128) == 0) {
            return p.info;
        }
        PermissionInfo pi = new PermissionInfo(p.info);
        pi.metaData = p.metaData;
        return pi;
    }

    @UnsupportedAppUsage
    public static final PermissionGroupInfo generatePermissionGroupInfo(PermissionGroup pg, int flags) {
        if (pg == null) {
            return null;
        }
        if ((flags & 128) == 0) {
            return pg.info;
        }
        PermissionGroupInfo pgi = new PermissionGroupInfo(pg.info);
        pgi.metaData = pg.metaData;
        return pgi;
    }

    /* renamed from: android.content.pm.PackageParser$Activity */
    /* loaded from: classes.dex */
    public static final class Activity extends Component<ActivityIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Activity>() { // from class: android.content.pm.PackageParser.Activity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Activity createFromParcel(Parcel in) {
                return new Activity(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Activity[] newArray(int size) {
                return new Activity[size];
            }
        };
        @UnsupportedAppUsage
        public final ActivityInfo info;
        private boolean mHasMaxAspectRatio;
        private boolean mHasMinAspectRatio;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMaxAspectRatio() {
            return this.mHasMaxAspectRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMinAspectRatio() {
            return this.mHasMinAspectRatio;
        }

        Activity(Package owner, String className, ActivityInfo info) {
            super(owner, new ArrayList(0), className);
            this.info = info;
            this.info.applicationInfo = owner.applicationInfo;
        }

        public Activity(ParseComponentArgs args, ActivityInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
        }

        @Override // android.content.p002pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMaxAspectRatio(float maxAspectRatio) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (maxAspectRatio < 1.0f && maxAspectRatio != 0.0f) {
                return;
            }
            this.info.maxAspectRatio = maxAspectRatio;
            this.mHasMaxAspectRatio = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMinAspectRatio(float minAspectRatio) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (minAspectRatio < 1.0f && minAspectRatio != 0.0f) {
                return;
            }
            this.info.minAspectRatio = minAspectRatio;
            this.mHasMinAspectRatio = true;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Activity{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.p002pm.PackageParser.Component, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags | 2);
            dest.writeBoolean(this.mHasMaxAspectRatio);
            dest.writeBoolean(this.mHasMinAspectRatio);
        }

        private Activity(Parcel in) {
            super(in);
            this.info = (ActivityInfo) in.readParcelable(Object.class.getClassLoader());
            this.mHasMaxAspectRatio = in.readBoolean();
            this.mHasMinAspectRatio = in.readBoolean();
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ActivityIntentInfo aii = (ActivityIntentInfo) it.next();
                aii.activity = this;
                this.order = Math.max(aii.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                this.info.permission = this.info.permission.intern();
            }
        }
    }

    @UnsupportedAppUsage
    public static final ActivityInfo generateActivityInfo(Activity a, int flags, PackageUserState state, int userId) {
        if (a == null || !checkUseInstalledOrHidden(flags, state, a.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(flags, a.owner, state, a.metaData, userId)) {
            updateApplicationInfo(a.info.applicationInfo, flags, state);
            return a.info;
        }
        ActivityInfo ai = new ActivityInfo(a.info);
        ai.metaData = a.metaData;
        ai.applicationInfo = generateApplicationInfo(a.owner, flags, state, userId);
        return ai;
    }

    public static final ActivityInfo generateActivityInfo(ActivityInfo ai, int flags, PackageUserState state, int userId) {
        if (ai == null || !checkUseInstalledOrHidden(flags, state, ai.applicationInfo)) {
            return null;
        }
        ActivityInfo ai2 = new ActivityInfo(ai);
        ai2.applicationInfo = generateApplicationInfo(ai2.applicationInfo, flags, state, userId);
        return ai2;
    }

    /* renamed from: android.content.pm.PackageParser$Service */
    /* loaded from: classes.dex */
    public static final class Service extends Component<ServiceIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Service>() { // from class: android.content.pm.PackageParser.Service.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Service createFromParcel(Parcel in) {
                return new Service(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Service[] newArray(int size) {
                return new Service[size];
            }
        };
        @UnsupportedAppUsage
        public final ServiceInfo info;

        public Service(ParseComponentArgs args, ServiceInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
        }

        @Override // android.content.p002pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Service{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.p002pm.PackageParser.Component, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags | 2);
        }

        private Service(Parcel in) {
            super(in);
            this.info = (ServiceInfo) in.readParcelable(Object.class.getClassLoader());
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ServiceIntentInfo aii = (ServiceIntentInfo) it.next();
                aii.service = this;
                this.order = Math.max(aii.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                this.info.permission = this.info.permission.intern();
            }
        }
    }

    @UnsupportedAppUsage
    public static final ServiceInfo generateServiceInfo(Service s, int flags, PackageUserState state, int userId) {
        if (s == null || !checkUseInstalledOrHidden(flags, state, s.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(flags, s.owner, state, s.metaData, userId)) {
            updateApplicationInfo(s.info.applicationInfo, flags, state);
            return s.info;
        }
        ServiceInfo si = new ServiceInfo(s.info);
        si.metaData = s.metaData;
        si.applicationInfo = generateApplicationInfo(s.owner, flags, state, userId);
        return si;
    }

    /* renamed from: android.content.pm.PackageParser$Provider */
    /* loaded from: classes.dex */
    public static final class Provider extends Component<ProviderIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Provider>() { // from class: android.content.pm.PackageParser.Provider.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Provider createFromParcel(Parcel in) {
                return new Provider(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Provider[] newArray(int size) {
                return new Provider[size];
            }
        };
        @UnsupportedAppUsage
        public final ProviderInfo info;
        @UnsupportedAppUsage
        public boolean syncable;

        public Provider(ParseComponentArgs args, ProviderInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
            this.syncable = false;
        }

        @UnsupportedAppUsage
        public Provider(Provider existingProvider) {
            super(existingProvider);
            this.info = existingProvider.info;
            this.syncable = existingProvider.syncable;
        }

        @Override // android.content.p002pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Provider{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.p002pm.PackageParser.Component, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags | 2);
            dest.writeInt(this.syncable ? 1 : 0);
        }

        private Provider(Parcel in) {
            super(in);
            this.info = (ProviderInfo) in.readParcelable(Object.class.getClassLoader());
            this.syncable = in.readInt() == 1;
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ProviderIntentInfo aii = (ProviderIntentInfo) it.next();
                aii.provider = this;
            }
            if (this.info.readPermission != null) {
                this.info.readPermission = this.info.readPermission.intern();
            }
            if (this.info.writePermission != null) {
                this.info.writePermission = this.info.writePermission.intern();
            }
            if (this.info.authority != null) {
                this.info.authority = this.info.authority.intern();
            }
        }
    }

    @UnsupportedAppUsage
    public static final ProviderInfo generateProviderInfo(Provider p, int flags, PackageUserState state, int userId) {
        if (p == null || !checkUseInstalledOrHidden(flags, state, p.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(flags, p.owner, state, p.metaData, userId) && ((flags & 2048) != 0 || p.info.uriPermissionPatterns == null)) {
            updateApplicationInfo(p.info.applicationInfo, flags, state);
            return p.info;
        }
        ProviderInfo pi = new ProviderInfo(p.info);
        pi.metaData = p.metaData;
        if ((flags & 2048) == 0) {
            pi.uriPermissionPatterns = null;
        }
        pi.applicationInfo = generateApplicationInfo(p.owner, flags, state, userId);
        return pi;
    }

    /* renamed from: android.content.pm.PackageParser$Instrumentation */
    /* loaded from: classes.dex */
    public static final class Instrumentation extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Instrumentation>() { // from class: android.content.pm.PackageParser.Instrumentation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Instrumentation createFromParcel(Parcel in) {
                return new Instrumentation(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Instrumentation[] newArray(int size) {
                return new Instrumentation[size];
            }
        };
        @UnsupportedAppUsage
        public final InstrumentationInfo info;

        public Instrumentation(ParsePackageItemArgs args, InstrumentationInfo _info) {
            super(args, _info);
            this.info = _info;
        }

        @Override // android.content.p002pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Instrumentation{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.p002pm.PackageParser.Component, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags);
        }

        private Instrumentation(Parcel in) {
            super(in);
            this.info = (InstrumentationInfo) in.readParcelable(Object.class.getClassLoader());
            if (this.info.targetPackage != null) {
                this.info.targetPackage = this.info.targetPackage.intern();
            }
            if (this.info.targetProcesses != null) {
                this.info.targetProcesses = this.info.targetProcesses.intern();
            }
        }
    }

    @UnsupportedAppUsage
    public static final InstrumentationInfo generateInstrumentationInfo(Instrumentation i, int flags) {
        if (i == null) {
            return null;
        }
        if ((flags & 128) == 0) {
            return i.info;
        }
        InstrumentationInfo ii = new InstrumentationInfo(i.info);
        ii.metaData = i.metaData;
        return ii;
    }

    /* renamed from: android.content.pm.PackageParser$IntentInfo */
    /* loaded from: classes.dex */
    public static abstract class IntentInfo extends IntentFilter {
        @UnsupportedAppUsage
        public int banner;
        @UnsupportedAppUsage
        public boolean hasDefault;
        @UnsupportedAppUsage
        public int icon;
        @UnsupportedAppUsage
        public int labelRes;
        @UnsupportedAppUsage
        public int logo;
        @UnsupportedAppUsage
        public CharSequence nonLocalizedLabel;
        public int preferred;

        @UnsupportedAppUsage
        protected IntentInfo() {
        }

        protected IntentInfo(Parcel dest) {
            super(dest);
            this.hasDefault = dest.readInt() == 1;
            this.labelRes = dest.readInt();
            this.nonLocalizedLabel = dest.readCharSequence();
            this.icon = dest.readInt();
            this.logo = dest.readInt();
            this.banner = dest.readInt();
            this.preferred = dest.readInt();
        }

        public void writeIntentInfoToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.hasDefault ? 1 : 0);
            dest.writeInt(this.labelRes);
            dest.writeCharSequence(this.nonLocalizedLabel);
            dest.writeInt(this.icon);
            dest.writeInt(this.logo);
            dest.writeInt(this.banner);
            dest.writeInt(this.preferred);
        }
    }

    /* renamed from: android.content.pm.PackageParser$ActivityIntentInfo */
    /* loaded from: classes.dex */
    public static final class ActivityIntentInfo extends IntentInfo {
        @UnsupportedAppUsage
        public Activity activity;

        public ActivityIntentInfo(Activity _activity) {
            this.activity = _activity;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ActivityIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            this.activity.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ActivityIntentInfo(Parcel in) {
            super(in);
        }
    }

    /* renamed from: android.content.pm.PackageParser$ServiceIntentInfo */
    /* loaded from: classes.dex */
    public static final class ServiceIntentInfo extends IntentInfo {
        @UnsupportedAppUsage
        public Service service;

        public ServiceIntentInfo(Service _service) {
            this.service = _service;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ServiceIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            this.service.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ServiceIntentInfo(Parcel in) {
            super(in);
        }
    }

    /* renamed from: android.content.pm.PackageParser$ProviderIntentInfo */
    /* loaded from: classes.dex */
    public static final class ProviderIntentInfo extends IntentInfo {
        @UnsupportedAppUsage
        public Provider provider;

        public ProviderIntentInfo(Provider provider) {
            this.provider = provider;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ProviderIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            this.provider.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ProviderIntentInfo(Parcel in) {
            super(in);
        }
    }

    @UnsupportedAppUsage
    public static void setCompatibilityModeEnabled(boolean compatibilityModeEnabled) {
        sCompatibilityModeEnabled = compatibilityModeEnabled;
    }

    public static void readConfigUseRoundIcon(Resources r) {
        if (r != null) {
            sUseRoundIcon = r.getBoolean(C3132R.bool.config_useRoundIcon);
            return;
        }
        try {
            ApplicationInfo androidAppInfo = ActivityThread.getPackageManager().getApplicationInfo("android", 0, UserHandle.myUserId());
            Resources systemResources = Resources.getSystem();
            Resources overlayableRes = ResourcesManager.getInstance().getResources(null, null, null, androidAppInfo.resourceDirs, androidAppInfo.sharedLibraryFiles, 0, null, systemResources.getCompatibilityInfo(), systemResources.getClassLoader());
            sUseRoundIcon = overlayableRes.getBoolean(C3132R.bool.config_useRoundIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.content.pm.PackageParser$PackageParserException */
    /* loaded from: classes.dex */
    public static class PackageParserException extends Exception {
        public final int error;

        public PackageParserException(int error, String detailMessage) {
            super(detailMessage);
            this.error = error;
        }

        public PackageParserException(int error, String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
            this.error = error;
        }
    }

    public static PackageInfo generatePackageInfoFromApex(ApexInfo apexInfo, int flags) throws PackageParserException {
        PackageParser pp = new PackageParser();
        File apexFile = new File(apexInfo.packagePath);
        Package p = pp.parsePackage(apexFile, flags, false);
        PackageUserState state = new PackageUserState();
        PackageInfo pi = generatePackageInfo(p, EmptyArray.INT, flags, 0L, 0L, Collections.emptySet(), state);
        pi.applicationInfo.sourceDir = apexFile.getPath();
        pi.applicationInfo.publicSourceDir = apexFile.getPath();
        if (apexInfo.isFactory) {
            pi.applicationInfo.flags |= 1;
        } else {
            pi.applicationInfo.flags &= -2;
        }
        if (apexInfo.isActive) {
            pi.applicationInfo.flags |= 8388608;
        } else {
            pi.applicationInfo.flags &= -8388609;
        }
        pi.isApex = true;
        if ((134217728 & flags) != 0) {
            collectCertificates(p, apexFile, false);
            if (p.mSigningDetails.hasPastSigningCertificates()) {
                pi.signatures = new Signature[1];
                pi.signatures[0] = p.mSigningDetails.pastSigningCertificates[0];
            } else if (p.mSigningDetails.hasSignatures()) {
                int numberOfSigs = p.mSigningDetails.signatures.length;
                pi.signatures = new Signature[numberOfSigs];
                System.arraycopy(p.mSigningDetails.signatures, 0, pi.signatures, 0, numberOfSigs);
            }
            if (p.mSigningDetails != SigningDetails.UNKNOWN) {
                pi.signingInfo = new SigningInfo(p.mSigningDetails);
            } else {
                pi.signingInfo = null;
            }
        }
        return pi;
    }
}
