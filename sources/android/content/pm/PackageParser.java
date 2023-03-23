package android.content.pm;

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
import android.content.pm.ActivityInfo;
import android.content.pm.PackageParserCacheHelper;
import android.content.pm.split.DefaultSplitAssetLoader;
import android.content.pm.split.SplitAssetDependencyLoader;
import android.content.pm.split.SplitAssetLoader;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.IncidentManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.SettingsStringUtil;
import android.security.keystore.KeyProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
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
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.DumpHeapActivity;
import com.android.internal.os.ClassLoaderFactory;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class PackageParser {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    public static final String APK_FILE_EXTENSION = ".apk";
    private static final Set<String> CHILD_PACKAGE_TAGS = new ArraySet();
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
    private static final boolean MULTI_PACKAGE_APK_ENABLED = (Build.IS_DEBUGGABLE && SystemProperties.getBoolean(PROPERTY_CHILD_PACKAGES_ENABLED, false));
    @UnsupportedAppUsage
    public static final NewPermissionInfo[] NEW_PERMISSIONS = {new NewPermissionInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE, 4, 0), new NewPermissionInfo(Manifest.permission.READ_PHONE_STATE, 4, 0)};
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
    private static final Set<String> SAFE_BROADCASTS = new ArraySet();
    private static final String[] SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;
    private static final int SDK_VERSION = Build.VERSION.SDK_INT;
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
    public static final AtomicInteger sCachedPackageReadCount = new AtomicInteger();
    private static boolean sCompatibilityModeEnabled = true;
    private static final Comparator<String> sSplitNameComparator = new SplitNameComparator();
    private static boolean sUseRoundIcon = false;
    @Deprecated
    private String mArchiveSourcePath;
    private File mCacheDir;
    @UnsupportedAppUsage
    private Callback mCallback;
    private DisplayMetrics mMetrics = new DisplayMetrics();
    private boolean mOnlyCoreApps;
    private int mParseError = 1;
    private ParsePackageItemArgs mParseInstrumentationArgs;
    private String[] mSeparateProcesses;

    public interface Callback {
        String[] getOverlayApks(String str);

        String[] getOverlayPaths(String str, String str2);

        boolean hasFeature(String str);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParseFlags {
    }

    static {
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
        SAFE_BROADCASTS.add(Intent.ACTION_BOOT_COMPLETED);
    }

    public static class NewPermissionInfo {
        public final int fileVersion;
        @UnsupportedAppUsage
        public final String name;
        @UnsupportedAppUsage
        public final int sdkVersion;

        public NewPermissionInfo(String name2, int sdkVersion2, int fileVersion2) {
            this.name = name2;
            this.sdkVersion = sdkVersion2;
            this.fileVersion = fileVersion2;
        }
    }

    static class ParsePackageItemArgs {
        final int bannerRes;
        final int iconRes;
        final int labelRes;
        final int logoRes;
        final int nameRes;
        final String[] outError;
        final Package owner;
        final int roundIconRes;
        TypedArray sa;
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

        public PackageLite(String codePath2, ApkLite baseApk, String[] splitNames2, boolean[] isFeatureSplits2, String[] usesSplitNames2, String[] configForSplit2, String[] splitCodePaths2, int[] splitRevisionCodes2) {
            this.packageName = baseApk.packageName;
            this.versionCode = baseApk.versionCode;
            this.versionCodeMajor = baseApk.versionCodeMajor;
            this.installLocation = baseApk.installLocation;
            this.verifiers = baseApk.verifiers;
            this.splitNames = splitNames2;
            this.isFeatureSplits = isFeatureSplits2;
            this.usesSplitNames = usesSplitNames2;
            this.configForSplit = configForSplit2;
            this.codePath = codePath2;
            this.baseCodePath = baseApk.codePath;
            this.splitCodePaths = splitCodePaths2;
            this.baseRevisionCode = baseApk.revisionCode;
            this.splitRevisionCodes = splitRevisionCodes2;
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
            if (!ArrayUtils.isEmpty((T[]) this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }
            return paths;
        }
    }

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

        public ApkLite(String codePath2, String packageName2, String splitName2, boolean isFeatureSplit2, String configForSplit2, String usesSplitName2, boolean isSplitRequired2, int versionCode2, int versionCodeMajor2, int revisionCode2, int installLocation2, List<VerifierInfo> verifiers2, SigningDetails signingDetails2, boolean coreApp2, boolean debuggable2, boolean multiArch2, boolean use32bitAbi2, boolean useEmbeddedDex2, boolean extractNativeLibs2, boolean isolatedSplits2, int minSdkVersion2, int targetSdkVersion2) {
            this.codePath = codePath2;
            this.packageName = packageName2;
            this.splitName = splitName2;
            this.isFeatureSplit = isFeatureSplit2;
            this.configForSplit = configForSplit2;
            this.usesSplitName = usesSplitName2;
            this.versionCode = versionCode2;
            this.versionCodeMajor = versionCodeMajor2;
            this.revisionCode = revisionCode2;
            this.installLocation = installLocation2;
            this.signingDetails = signingDetails2;
            this.verifiers = (VerifierInfo[]) verifiers2.toArray(new VerifierInfo[verifiers2.size()]);
            this.coreApp = coreApp2;
            this.debuggable = debuggable2;
            this.multiArch = multiArch2;
            this.use32bitAbi = use32bitAbi2;
            this.useEmbeddedDex = useEmbeddedDex2;
            this.extractNativeLibs = extractNativeLibs2;
            this.isolatedSplits = isolatedSplits2;
            this.isSplitRequired = isSplitRequired2;
            this.minSdkVersion = minSdkVersion2;
            this.targetSdkVersion = targetSdkVersion2;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

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

    public static final class CallbackImpl implements Callback {
        private final PackageManager mPm;

        public CallbackImpl(PackageManager pm) {
            this.mPm = pm;
        }

        public boolean hasFeature(String feature) {
            return this.mPm.hasSystemFeature(feature);
        }

        public String[] getOverlayPaths(String targetPackageName, String targetPath) {
            return null;
        }

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
        if ((flags & 536870912) == 0 && !state.installed && appInfo != null && appInfo.hiddenUntilInstalled) {
            return false;
        }
        if (!state.isAvailable(flags)) {
            if (appInfo == null || !appInfo.isSystemApp()) {
                return false;
            }
            if ((4202496 & flags) == 0 && (536870912 & flags) == 0) {
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean isAvailable(PackageUserState state) {
        return checkUseInstalledOrHidden(0, state, (ApplicationInfo) null);
    }

    @UnsupportedAppUsage
    public static PackageInfo generatePackageInfo(Package p, int[] gids, int flags, long firstInstallTime, long lastUpdateTime, Set<String> grantedPermissions, PackageUserState state, int userId) {
        int N;
        int N2;
        int N3;
        int N4;
        int N5;
        Package packageR = p;
        int i = flags;
        Set<String> set = grantedPermissions;
        PackageUserState packageUserState = state;
        int i2 = userId;
        if (!checkUseInstalledOrHidden(i, packageUserState, packageR.applicationInfo) || !packageR.isMatch(i)) {
            long j = firstInstallTime;
            long j2 = lastUpdateTime;
            return null;
        }
        PackageInfo pi = new PackageInfo();
        pi.packageName = packageR.packageName;
        pi.splitNames = packageR.splitNames;
        pi.versionCode = packageR.mVersionCode;
        pi.versionCodeMajor = packageR.mVersionCodeMajor;
        pi.baseRevisionCode = packageR.baseRevisionCode;
        pi.splitRevisionCodes = packageR.splitRevisionCodes;
        pi.versionName = packageR.mVersionName;
        pi.sharedUserId = packageR.mSharedUserId;
        pi.sharedUserLabel = packageR.mSharedUserLabel;
        pi.applicationInfo = generateApplicationInfo(packageR, i, packageUserState, i2);
        pi.installLocation = packageR.installLocation;
        pi.isStub = packageR.isStub;
        pi.coreApp = packageR.coreApp;
        if (!((pi.applicationInfo.flags & 1) == 0 && (pi.applicationInfo.flags & 128) == 0)) {
            pi.requiredForAllUsers = packageR.mRequiredForAllUsers;
        }
        pi.restrictedAccountType = packageR.mRestrictedAccountType;
        pi.requiredAccountType = packageR.mRequiredAccountType;
        pi.overlayTarget = packageR.mOverlayTarget;
        pi.targetOverlayableName = packageR.mOverlayTargetName;
        pi.overlayCategory = packageR.mOverlayCategory;
        pi.overlayPriority = packageR.mOverlayPriority;
        pi.mOverlayIsStatic = packageR.mOverlayIsStatic;
        pi.compileSdkVersion = packageR.mCompileSdkVersion;
        pi.compileSdkVersionCodename = packageR.mCompileSdkVersionCodename;
        pi.firstInstallTime = firstInstallTime;
        pi.lastUpdateTime = lastUpdateTime;
        if ((i & 256) != 0) {
            pi.gids = gids;
        } else {
            int[] iArr = gids;
        }
        if ((i & 16384) != 0) {
            int N6 = packageR.configPreferences != null ? packageR.configPreferences.size() : 0;
            if (N6 > 0) {
                pi.configPreferences = new ConfigurationInfo[N6];
                packageR.configPreferences.toArray(pi.configPreferences);
            }
            int N7 = packageR.reqFeatures != null ? packageR.reqFeatures.size() : 0;
            if (N7 > 0) {
                pi.reqFeatures = new FeatureInfo[N7];
                packageR.reqFeatures.toArray(pi.reqFeatures);
            }
            int N8 = packageR.featureGroups != null ? packageR.featureGroups.size() : 0;
            if (N8 > 0) {
                pi.featureGroups = new FeatureGroupInfo[N8];
                packageR.featureGroups.toArray(pi.featureGroups);
            }
        }
        if ((i & 1) != 0 && (N5 = packageR.activities.size()) > 0) {
            ActivityInfo[] res = new ActivityInfo[N5];
            int num = 0;
            int i3 = 0;
            while (i3 < N5) {
                Activity a = packageR.activities.get(i3);
                int N9 = N5;
                if (packageUserState.isMatch(a.info, i) && !PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(a.className)) {
                    res[num] = generateActivityInfo(a, i, packageUserState, i2);
                    num++;
                }
                i3++;
                N5 = N9;
                int[] iArr2 = gids;
            }
            pi.activities = (ActivityInfo[]) ArrayUtils.trimToSize(res, num);
        }
        if ((i & 2) != 0 && (N4 = packageR.receivers.size()) > 0) {
            ActivityInfo[] res2 = new ActivityInfo[N4];
            int num2 = 0;
            for (int i4 = 0; i4 < N4; i4++) {
                Activity a2 = packageR.receivers.get(i4);
                if (packageUserState.isMatch(a2.info, i)) {
                    res2[num2] = generateActivityInfo(a2, i, packageUserState, i2);
                    num2++;
                }
            }
            pi.receivers = (ActivityInfo[]) ArrayUtils.trimToSize(res2, num2);
        }
        if ((i & 4) != 0 && (N3 = packageR.services.size()) > 0) {
            ServiceInfo[] res3 = new ServiceInfo[N3];
            int num3 = 0;
            for (int i5 = 0; i5 < N3; i5++) {
                Service s = packageR.services.get(i5);
                if (packageUserState.isMatch(s.info, i)) {
                    res3[num3] = generateServiceInfo(s, i, packageUserState, i2);
                    num3++;
                }
            }
            pi.services = (ServiceInfo[]) ArrayUtils.trimToSize(res3, num3);
        }
        if ((i & 8) != 0 && (N2 = packageR.providers.size()) > 0) {
            ProviderInfo[] res4 = new ProviderInfo[N2];
            int num4 = 0;
            for (int i6 = 0; i6 < N2; i6++) {
                Provider pr = packageR.providers.get(i6);
                if (packageUserState.isMatch(pr.info, i)) {
                    res4[num4] = generateProviderInfo(pr, i, packageUserState, i2);
                    num4++;
                }
            }
            pi.providers = (ProviderInfo[]) ArrayUtils.trimToSize(res4, num4);
        }
        if ((i & 16) != 0 && (N = packageR.instrumentation.size()) > 0) {
            pi.instrumentation = new InstrumentationInfo[N];
            for (int i7 = 0; i7 < N; i7++) {
                pi.instrumentation[i7] = generateInstrumentationInfo(packageR.instrumentation.get(i7), i);
            }
        }
        if ((i & 4096) != 0) {
            int N10 = packageR.permissions.size();
            if (N10 > 0) {
                pi.permissions = new PermissionInfo[N10];
                for (int i8 = 0; i8 < N10; i8++) {
                    pi.permissions[i8] = generatePermissionInfo(packageR.permissions.get(i8), i);
                }
            }
            int N11 = packageR.requestedPermissions.size();
            if (N11 > 0) {
                pi.requestedPermissions = new String[N11];
                pi.requestedPermissionsFlags = new int[N11];
                for (int i9 = 0; i9 < N11; i9++) {
                    String perm = packageR.requestedPermissions.get(i9);
                    pi.requestedPermissions[i9] = perm;
                    int[] iArr3 = pi.requestedPermissionsFlags;
                    iArr3[i9] = iArr3[i9] | 1;
                    if (set != null && set.contains(perm)) {
                        int[] iArr4 = pi.requestedPermissionsFlags;
                        iArr4[i9] = iArr4[i9] | 2;
                    }
                }
            }
        }
        if ((i & 64) != 0) {
            if (packageR.mSigningDetails.hasPastSigningCertificates()) {
                pi.signatures = new Signature[1];
                pi.signatures[0] = packageR.mSigningDetails.pastSigningCertificates[0];
            } else if (packageR.mSigningDetails.hasSignatures()) {
                int numberOfSigs = packageR.mSigningDetails.signatures.length;
                pi.signatures = new Signature[numberOfSigs];
                System.arraycopy(packageR.mSigningDetails.signatures, 0, pi.signatures, 0, numberOfSigs);
            }
        }
        if ((134217728 & i) != 0) {
            if (packageR.mSigningDetails != SigningDetails.UNKNOWN) {
                pi.signingInfo = new SigningInfo(packageR.mSigningDetails);
            } else {
                pi.signingInfo = null;
            }
        }
        return pi;
    }

    private static class SplitNameComparator implements Comparator<String> {
        private SplitNameComparator() {
        }

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
        Trace.traceBegin(262144, "parseApkLite");
        ApkLite baseApk = parseApkLite(packageFile, flags);
        String packagePath = packageFile.getAbsolutePath();
        Trace.traceEnd(262144);
        return new PackageLite(packagePath, baseApk, (String[]) null, (boolean[]) null, (String[]) null, (String[]) null, (String[]) null, (int[]) null);
    }

    /* JADX WARNING: type inference failed for: r14v2, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.content.pm.PackageParser.PackageLite parseClusterPackageLite(java.io.File r25, int r26) throws android.content.pm.PackageParser.PackageParserException {
        /*
            java.io.File[] r0 = r25.listFiles()
            boolean r1 = com.android.internal.util.ArrayUtils.isEmpty((T[]) r0)
            if (r1 != 0) goto L_0x0156
            r1 = 0
            r2 = 0
            java.lang.String r3 = "parseApkLite"
            r4 = 262144(0x40000, double:1.295163E-318)
            android.os.Trace.traceBegin(r4, r3)
            android.util.ArrayMap r3 = new android.util.ArrayMap
            r3.<init>()
            int r6 = r0.length
            r7 = 0
            r8 = r2
            r2 = r1
            r1 = r7
        L_0x001f:
            r9 = -101(0xffffffffffffff9b, float:NaN)
            if (r1 >= r6) goto L_0x00c6
            r10 = r0[r1]
            boolean r11 = isApkFile(r10)
            if (r11 == 0) goto L_0x00c0
            r11 = r26
            android.content.pm.PackageParser$ApkLite r12 = parseApkLite(r10, r11)
            if (r2 != 0) goto L_0x0038
            java.lang.String r2 = r12.packageName
            int r8 = r12.versionCode
            goto L_0x0044
        L_0x0038:
            java.lang.String r13 = r12.packageName
            boolean r13 = r2.equals(r13)
            if (r13 == 0) goto L_0x0097
            int r13 = r12.versionCode
            if (r8 != r13) goto L_0x006e
        L_0x0044:
            java.lang.String r13 = r12.splitName
            java.lang.Object r13 = r3.put(r13, r12)
            if (r13 != 0) goto L_0x004d
            goto L_0x00c2
        L_0x004d:
            android.content.pm.PackageParser$PackageParserException r1 = new android.content.pm.PackageParser$PackageParserException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Split name "
            r4.append(r5)
            java.lang.String r5 = r12.splitName
            r4.append(r5)
            java.lang.String r5 = " defined more than once; most recent was "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r4 = r4.toString()
            r1.<init>(r9, r4)
            throw r1
        L_0x006e:
            android.content.pm.PackageParser$PackageParserException r1 = new android.content.pm.PackageParser$PackageParserException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Inconsistent version "
            r4.append(r5)
            int r5 = r12.versionCode
            r4.append(r5)
            java.lang.String r5 = " in "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r5 = "; expected "
            r4.append(r5)
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            r1.<init>(r9, r4)
            throw r1
        L_0x0097:
            android.content.pm.PackageParser$PackageParserException r1 = new android.content.pm.PackageParser$PackageParserException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Inconsistent package "
            r4.append(r5)
            java.lang.String r5 = r12.packageName
            r4.append(r5)
            java.lang.String r5 = " in "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r5 = "; expected "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            r1.<init>(r9, r4)
            throw r1
        L_0x00c0:
            r11 = r26
        L_0x00c2:
            int r1 = r1 + 1
            goto L_0x001f
        L_0x00c6:
            r11 = r26
            android.os.Trace.traceEnd(r4)
            r1 = 0
            java.lang.Object r1 = r3.remove(r1)
            android.content.pm.PackageParser$ApkLite r1 = (android.content.pm.PackageParser.ApkLite) r1
            if (r1 == 0) goto L_0x013d
            int r4 = r3.size()
            r5 = 0
            r6 = 0
            r9 = 0
            r10 = 0
            r12 = 0
            r13 = 0
            r21 = 0
            if (r4 <= 0) goto L_0x0120
            java.lang.String[] r5 = new java.lang.String[r4]
            boolean[] r6 = new boolean[r4]
            java.lang.String[] r9 = new java.lang.String[r4]
            java.lang.String[] r10 = new java.lang.String[r4]
            java.lang.String[] r12 = new java.lang.String[r4]
            int[] r13 = new int[r4]
            java.util.Set r14 = r3.keySet()
            java.lang.Object[] r14 = r14.toArray(r5)
            r5 = r14
            java.lang.String[] r5 = (java.lang.String[]) r5
            java.util.Comparator<java.lang.String> r14 = sSplitNameComparator
            java.util.Arrays.sort(r5, r14)
        L_0x00ff:
            if (r7 >= r4) goto L_0x0120
            r14 = r5[r7]
            java.lang.Object r14 = r3.get(r14)
            android.content.pm.PackageParser$ApkLite r14 = (android.content.pm.PackageParser.ApkLite) r14
            java.lang.String r15 = r14.usesSplitName
            r9[r7] = r15
            boolean r15 = r14.isFeatureSplit
            r6[r7] = r15
            java.lang.String r15 = r14.configForSplit
            r10[r7] = r15
            java.lang.String r15 = r14.codePath
            r12[r7] = r15
            int r15 = r14.revisionCode
            r13[r7] = r15
            int r7 = r7 + 1
            goto L_0x00ff
        L_0x0120:
            r7 = r12
            r22 = r13
            java.lang.String r23 = r25.getAbsolutePath()
            android.content.pm.PackageParser$PackageLite r24 = new android.content.pm.PackageParser$PackageLite
            r12 = r24
            r13 = r23
            r14 = r1
            r15 = r5
            r16 = r6
            r17 = r9
            r18 = r10
            r19 = r7
            r20 = r22
            r12.<init>(r13, r14, r15, r16, r17, r18, r19, r20)
            return r24
        L_0x013d:
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Missing base APK in "
            r5.append(r6)
            r6 = r25
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r9, r5)
            throw r4
        L_0x0156:
            r6 = r25
            r11 = r26
            android.content.pm.PackageParser$PackageParserException r1 = new android.content.pm.PackageParser$PackageParserException
            r2 = -100
            java.lang.String r3 = "No packages found in split"
            r1.<init>(r2, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseClusterPackageLite(java.io.File, int):android.content.pm.PackageParser$PackageLite");
    }

    @UnsupportedAppUsage
    public Package parsePackage(File packageFile, int flags, boolean useCaches) throws PackageParserException {
        Package parsed;
        Package parsed2 = useCaches ? getCachedResult(packageFile, flags) : null;
        if (parsed2 != null) {
            return parsed2;
        }
        long j = 0;
        long parseTime = LOG_PARSE_TIMINGS ? SystemClock.uptimeMillis() : 0;
        if (packageFile.isDirectory()) {
            parsed = parseClusterPackage(packageFile, flags);
        } else {
            parsed = parseMonolithicPackage(packageFile, flags);
        }
        if (LOG_PARSE_TIMINGS) {
            j = SystemClock.uptimeMillis();
        }
        long cacheTime = j;
        cacheResult(packageFile, flags, parsed);
        if (LOG_PARSE_TIMINGS) {
            long parseTime2 = cacheTime - parseTime;
            long cacheTime2 = SystemClock.uptimeMillis() - cacheTime;
            if (parseTime2 + cacheTime2 > 100) {
                Slog.i(TAG, "Parse times for '" + packageFile + "': parse=" + parseTime2 + "ms, update_cache=" + cacheTime2 + " ms");
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

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public Package fromCacheEntry(byte[] bytes) {
        return fromCacheEntryStatic(bytes);
    }

    @VisibleForTesting
    public static Package fromCacheEntryStatic(byte[] bytes) {
        Parcel p = Parcel.obtain();
        p.unmarshall(bytes, 0, bytes.length);
        p.setDataPosition(0);
        new PackageParserCacheHelper.ReadHelper(p).startAndInstall();
        Package pkg = new Package(p);
        p.recycle();
        sCachedPackageReadCount.incrementAndGet();
        return pkg;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public byte[] toCacheEntry(Package pkg) {
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
            if (Os.stat(packageFile.getAbsolutePath()).st_mtime < Os.stat(cacheFile.getAbsolutePath()).st_mtime) {
                return true;
            }
            return false;
        } catch (ErrnoException ee) {
            if (ee.errno != OsConstants.ENOENT) {
                Slog.w("Error while stating package cache : ", (Throwable) ee);
            }
            return false;
        }
    }

    private Package getCachedResult(File packageFile, int flags) {
        String[] overlayApks;
        if (this.mCacheDir == null) {
            return null;
        }
        File cacheFile = new File(this.mCacheDir, getCacheKey(packageFile, flags));
        Slog.e(TAG, "wits getCachedResult packageFile=" + packageFile + "ms, cacheFile=" + cacheFile.getAbsolutePath());
        try {
            if (!isCacheUpToDate(packageFile, cacheFile)) {
                return null;
            }
            Package p = fromCacheEntry(IoUtils.readFileAsByteArray(cacheFile.getAbsolutePath()));
            if (!(this.mCallback == null || (overlayApks = this.mCallback.getOverlayApks(p.packageName)) == null || overlayApks.length <= 0)) {
                for (String overlayApk : overlayApks) {
                    if (!isCacheUpToDate(new File(overlayApk), cacheFile)) {
                        return null;
                    }
                }
            }
            return p;
        } catch (Throwable e) {
            Slog.w(TAG, "Error reading package cache: ", e);
            cacheFile.delete();
            return null;
        }
    }

    private void cacheResult(File packageFile, int flags, Package parsed) {
        FileOutputStream fos;
        if (this.mCacheDir != null) {
            try {
                File cacheFile = new File(this.mCacheDir, getCacheKey(packageFile, flags));
                if (cacheFile.exists() && !cacheFile.delete()) {
                    Slog.e(TAG, "Unable to delete cache file: " + cacheFile);
                }
                byte[] cacheEntry = toCacheEntry(parsed);
                if (cacheEntry != null) {
                    try {
                        fos = new FileOutputStream(cacheFile);
                        fos.write(cacheEntry);
                        fos.close();
                        return;
                    } catch (IOException ioe) {
                        Slog.w(TAG, "Error writing cache entry.", ioe);
                        cacheFile.delete();
                        return;
                    } catch (Throwable th) {
                        r4.addSuppressed(th);
                    }
                } else {
                    return;
                }
            } catch (Throwable e) {
                Slog.w(TAG, "Error saving package cache.", e);
                return;
            }
        } else {
            return;
        }
        throw th;
    }

    private Package parseClusterPackage(File packageDir, int flags) throws PackageParserException {
        SplitAssetLoader assetLoader;
        PackageLite lite = parseClusterPackageLite(packageDir, 0);
        if (!this.mOnlyCoreApps || lite.coreApp) {
            SparseArray<int[]> splitDependencies = null;
            if (!lite.isolatedSplits || ArrayUtils.isEmpty((T[]) lite.splitNames)) {
                assetLoader = new DefaultSplitAssetLoader(lite, flags);
            } else {
                try {
                    splitDependencies = SplitAssetDependencyLoader.createDependenciesFromPackage(lite);
                    assetLoader = new SplitAssetDependencyLoader(lite, splitDependencies, flags);
                } catch (SplitDependencyLoader.IllegalDependencyException e) {
                    throw new PackageParserException(-101, e.getMessage());
                }
            }
            try {
                AssetManager assets = assetLoader.getBaseAssetManager();
                File baseApk = new File(lite.baseCodePath);
                Package pkg = parseBaseApk(baseApk, assets, flags);
                if (pkg != null) {
                    if (!ArrayUtils.isEmpty((T[]) lite.splitNames)) {
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
                            parseSplitApk(pkg, i, assetLoader.getSplitAssetManager(i), flags);
                        }
                    }
                    pkg.setCodePath(packageDir.getCanonicalPath());
                    pkg.setUse32bitAbi(lite.use32bitAbi);
                    IoUtils.closeQuietly(assetLoader);
                    return pkg;
                }
                throw new PackageParserException(-100, "Failed to parse base APK: " + baseApk);
            } catch (IOException e2) {
                throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to get path: " + lite.baseCodePath, e2);
            } catch (Throwable th) {
                IoUtils.closeQuietly(assetLoader);
                throw th;
            }
        } else {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Not a coreApp: " + packageDir);
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public Package parseMonolithicPackage(File apkFile, int flags) throws PackageParserException {
        PackageLite lite = parseMonolithicPackageLite(apkFile, flags);
        if (!this.mOnlyCoreApps || lite.coreApp) {
            SplitAssetLoader assetLoader = new DefaultSplitAssetLoader(lite, flags);
            try {
                Package pkg = parseBaseApk(apkFile, assetLoader.getBaseAssetManager(), flags);
                pkg.setCodePath(apkFile.getCanonicalPath());
                pkg.setUse32bitAbi(lite.use32bitAbi);
                IoUtils.closeQuietly(assetLoader);
                return pkg;
            } catch (IOException e) {
                throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to get path: " + apkFile, e);
            } catch (Throwable th) {
                IoUtils.closeQuietly(assetLoader);
                throw th;
            }
        } else {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Not a coreApp: " + apkFile);
        }
    }

    private Package parseBaseApk(File apkFile, AssetManager assets, int flags) throws PackageParserException {
        XmlResourceParser parser;
        String apkPath = apkFile.getAbsolutePath();
        String volumeUuid = null;
        if (apkPath.startsWith(MNT_EXPAND)) {
            volumeUuid = apkPath.substring(MNT_EXPAND.length(), apkPath.indexOf(47, MNT_EXPAND.length()));
        }
        String volumeUuid2 = volumeUuid;
        this.mParseError = 1;
        this.mArchiveSourcePath = apkFile.getAbsolutePath();
        XmlResourceParser parser2 = null;
        try {
            int cookie = assets.findCookieForPath(apkPath);
            if (cookie != 0) {
                parser = assets.openXmlResourceParser(cookie, ANDROID_MANIFEST_FILENAME);
                try {
                    String[] outError = new String[1];
                    Package pkg = parseBaseApk(apkPath, new Resources(assets, this.mMetrics, (Configuration) null), parser, flags, outError);
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
                    e = e;
                    XmlResourceParser xmlResourceParser = parser;
                    throw e;
                } catch (Exception e2) {
                    e = e2;
                    parser2 = parser;
                    throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
                } catch (Throwable th) {
                    e = th;
                    IoUtils.closeQuietly(parser);
                    throw e;
                }
            } else {
                throw new PackageParserException(-101, "Failed adding asset path: " + apkPath);
            }
        } catch (PackageParserException e3) {
            e = e3;
            throw e;
        } catch (Exception e4) {
            e = e4;
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
        } catch (Throwable th2) {
            e = th2;
            parser = parser2;
            IoUtils.closeQuietly(parser);
            throw e;
        }
    }

    private void parseSplitApk(Package pkg, int splitIndex, AssetManager assets, int flags) throws PackageParserException {
        AssetManager assetManager = assets;
        String apkPath = pkg.splitCodePaths[splitIndex];
        this.mParseError = 1;
        this.mArchiveSourcePath = apkPath;
        XmlResourceParser parser = null;
        try {
            int cookie = assetManager.findCookieForPath(apkPath);
            if (cookie != 0) {
                XmlResourceParser parser2 = assetManager.openXmlResourceParser(cookie, ANDROID_MANIFEST_FILENAME);
                try {
                    String[] outError = new String[1];
                    Package pkg2 = parseSplitApk(pkg, new Resources(assetManager, this.mMetrics, (Configuration) null), parser2, flags, splitIndex, outError);
                    if (pkg2 != null) {
                        IoUtils.closeQuietly(parser2);
                        return;
                    }
                    try {
                        throw new PackageParserException(this.mParseError, apkPath + " (at " + parser2.getPositionDescription() + "): " + outError[0]);
                    } catch (PackageParserException e) {
                        e = e;
                        Package packageR = pkg2;
                        throw e;
                    } catch (Exception e2) {
                        e = e2;
                        Package packageR2 = pkg2;
                        parser = parser2;
                        throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
                    } catch (Throwable th) {
                        e = th;
                        Package packageR3 = pkg2;
                        parser = parser2;
                        IoUtils.closeQuietly(parser);
                        throw e;
                    }
                } catch (PackageParserException e3) {
                    e = e3;
                    throw e;
                } catch (Exception e4) {
                    e = e4;
                    parser = parser2;
                    throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
                } catch (Throwable th2) {
                    e = th2;
                    parser = parser2;
                    IoUtils.closeQuietly(parser);
                    throw e;
                }
            } else {
                throw new PackageParserException(-101, "Failed adding asset path: " + apkPath);
            }
        } catch (PackageParserException e5) {
            e = e5;
            throw e;
        } catch (Exception e6) {
            e = e6;
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to read manifest from " + apkPath, e);
        } catch (Throwable th3) {
            e = th3;
            IoUtils.closeQuietly(parser);
            throw e;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x007b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.pm.PackageParser.Package parseSplitApk(android.content.pm.PackageParser.Package r10, android.content.res.Resources r11, android.content.res.XmlResourceParser r12, int r13, int r14, java.lang.String[] r15) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.content.pm.PackageParser.PackageParserException {
        /*
            r9 = this;
            r0 = r12
            parsePackageSplitNames(r12, r0)
            r1 = 0
            r9.mParseInstrumentationArgs = r1
            r2 = 0
            int r3 = r12.getDepth()
        L_0x000c:
            int r4 = r12.next()
            r5 = r4
            r6 = 1
            if (r4 == r6) goto L_0x0079
            r4 = 3
            if (r5 != r4) goto L_0x001d
            int r6 = r12.getDepth()
            if (r6 <= r3) goto L_0x0079
        L_0x001d:
            if (r5 == r4) goto L_0x000c
            r4 = 4
            if (r5 != r4) goto L_0x0023
            goto L_0x000c
        L_0x0023:
            java.lang.String r4 = r12.getName()
            java.lang.String r6 = "application"
            boolean r6 = r4.equals(r6)
            if (r6 == 0) goto L_0x0045
            if (r2 == 0) goto L_0x003c
            java.lang.String r6 = "PackageParser"
            java.lang.String r7 = "<manifest> has more than one <application>"
            android.util.Slog.w((java.lang.String) r6, (java.lang.String) r7)
            com.android.internal.util.XmlUtils.skipCurrentTag(r12)
            goto L_0x000c
        L_0x003c:
            r2 = 1
            boolean r6 = r9.parseSplitApplication(r10, r11, r12, r13, r14, r15)
            if (r6 != 0) goto L_0x0044
            return r1
        L_0x0044:
            goto L_0x000c
        L_0x0045:
            java.lang.String r6 = "PackageParser"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Unknown element under <manifest>: "
            r7.append(r8)
            java.lang.String r8 = r12.getName()
            r7.append(r8)
            java.lang.String r8 = " at "
            r7.append(r8)
            java.lang.String r8 = r9.mArchiveSourcePath
            r7.append(r8)
            java.lang.String r8 = " "
            r7.append(r8)
            java.lang.String r8 = r12.getPositionDescription()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Slog.w((java.lang.String) r6, (java.lang.String) r7)
            com.android.internal.util.XmlUtils.skipCurrentTag(r12)
            goto L_0x000c
        L_0x0079:
            if (r2 != 0) goto L_0x0084
            r1 = 0
            java.lang.String r4 = "<manifest> does not contain an <application>"
            r15[r1] = r4
            r1 = -109(0xffffffffffffff93, float:NaN)
            r9.mParseError = r1
        L_0x0084:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseSplitApk(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, int, java.lang.String[]):android.content.pm.PackageParser$Package");
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] signatures) throws CertificateException {
        ArraySet<PublicKey> keys = new ArraySet<>(signatures.length);
        for (Signature publicKey : signatures) {
            keys.add(publicKey.getPublicKey());
        }
        return keys;
    }

    @UnsupportedAppUsage
    public static void collectCertificates(Package pkg, boolean skipVerify) throws PackageParserException {
        collectCertificatesInternal(pkg, skipVerify);
        int childCount = pkg.childPackages != null ? pkg.childPackages.size() : 0;
        for (int i = 0; i < childCount; i++) {
            pkg.childPackages.get(i).mSigningDetails = pkg.mSigningDetails;
        }
    }

    private static void collectCertificatesInternal(Package pkg, boolean skipVerify) throws PackageParserException {
        pkg.mSigningDetails = SigningDetails.UNKNOWN;
        Trace.traceBegin(262144, "collectCertificates");
        try {
            collectCertificates(pkg, new File(pkg.baseCodePath), skipVerify);
            if (!ArrayUtils.isEmpty((T[]) pkg.splitCodePaths)) {
                for (String file : pkg.splitCodePaths) {
                    collectCertificates(pkg, new File(file), skipVerify);
                }
            }
        } finally {
            Trace.traceEnd(262144);
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
        assetManager.setConfiguration(0, 0, (String) null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        return assetManager;
    }

    public static ApkLite parseApkLite(File apkFile, int flags) throws PackageParserException {
        return parseApkLiteInner(apkFile, (FileDescriptor) null, (String) null, flags);
    }

    public static ApkLite parseApkLite(FileDescriptor fd, String debugPathName, int flags) throws PackageParserException {
        return parseApkLiteInner((File) null, fd, debugPathName, flags);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        android.util.Slog.w(TAG, "Failed to parse " + r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ac, code lost:
        throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed to parse " + r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0016 A[Catch:{ all -> 0x004c, IOException -> 0x0018, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, all -> 0x0013, Throwable -> 0x00b6 }, ExcHandler: RuntimeException | XmlPullParserException (r2v6 'e' java.lang.Exception A[CUSTOM_DECLARE, Catch:{  }]), PHI: r1 r3 
      PHI: (r1v2 'parser' android.content.res.XmlResourceParser) = (r1v0 'parser' android.content.res.XmlResourceParser), (r1v1 'parser' android.content.res.XmlResourceParser), (r1v1 'parser' android.content.res.XmlResourceParser), (r1v0 'parser' android.content.res.XmlResourceParser) binds: [B:15:0x0022, B:22:0x0041, B:24:0x0044, B:37:0x0064] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r3v2 'apkAssets' android.content.res.ApkAssets) = (r3v1 'apkAssets' android.content.res.ApkAssets), (r3v1 'apkAssets' android.content.res.ApkAssets), (r3v1 'apkAssets' android.content.res.ApkAssets), (r3v0 'apkAssets' android.content.res.ApkAssets) binds: [B:15:0x0022, B:22:0x0041, B:24:0x0044, B:37:0x0064] A[DONT_GENERATE, DONT_INLINE], Splitter:B:15:0x0022] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.pm.PackageParser.ApkLite parseApkLiteInner(java.io.File r8, java.io.FileDescriptor r9, java.lang.String r10, int r11) throws android.content.pm.PackageParser.PackageParserException {
        /*
            if (r9 == 0) goto L_0x0004
            r0 = r10
            goto L_0x0008
        L_0x0004:
            java.lang.String r0 = r8.getAbsolutePath()
        L_0x0008:
            r1 = 0
            r2 = 0
            r3 = r2
            r4 = 0
            if (r9 == 0) goto L_0x001a
            android.content.res.ApkAssets r5 = android.content.res.ApkAssets.loadFromFd(r9, r10, r4, r4)     // Catch:{ IOException -> 0x0018 }
            goto L_0x001e
        L_0x0013:
            r2 = move-exception
            goto L_0x00ad
        L_0x0016:
            r2 = move-exception
            goto L_0x007d
        L_0x0018:
            r2 = move-exception
            goto L_0x0063
        L_0x001a:
            android.content.res.ApkAssets r5 = android.content.res.ApkAssets.loadFromPath(r0)     // Catch:{ IOException -> 0x0018 }
        L_0x001e:
            r3 = r5
            java.lang.String r5 = "AndroidManifest.xml"
            android.content.res.XmlResourceParser r5 = r3.openXml(r5)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r1 = r5
            r5 = r11 & 32
            if (r5 == 0) goto L_0x0051
            android.content.pm.PackageParser$Package r5 = new android.content.pm.PackageParser$Package     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r5.<init>((java.lang.String) r2)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r2 = r5
            r5 = r11 & 16
            if (r5 == 0) goto L_0x0039
            r4 = 1
        L_0x0039:
            java.lang.String r5 = "collectCertificates"
            r6 = 262144(0x40000, double:1.295163E-318)
            android.os.Trace.traceBegin(r6, r5)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            collectCertificates(r2, r8, r4)     // Catch:{ all -> 0x004c }
            android.os.Trace.traceEnd(r6)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            android.content.pm.PackageParser$SigningDetails r5 = r2.mSigningDetails     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r2 = r5
            goto L_0x0053
        L_0x004c:
            r5 = move-exception
            android.os.Trace.traceEnd(r6)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            throw r5     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
        L_0x0051:
            android.content.pm.PackageParser$SigningDetails r2 = android.content.pm.PackageParser.SigningDetails.UNKNOWN     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
        L_0x0053:
            r4 = r1
            android.content.pm.PackageParser$ApkLite r5 = parseApkLite(r0, r1, r4, r2)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            libcore.io.IoUtils.closeQuietly(r1)
            if (r3 == 0) goto L_0x0062
            r3.close()     // Catch:{ Throwable -> 0x0061 }
            goto L_0x0062
        L_0x0061:
            r6 = move-exception
        L_0x0062:
            return r5
        L_0x0063:
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r5 = -100
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r6.<init>()     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            java.lang.String r7 = "Failed to parse "
            r6.append(r7)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r6.append(r0)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            java.lang.String r6 = r6.toString()     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            r4.<init>(r5, r6)     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
            throw r4     // Catch:{ RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016, RuntimeException | XmlPullParserException -> 0x0016 }
        L_0x007d:
            java.lang.String r4 = "PackageParser"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0013 }
            r5.<init>()     // Catch:{ all -> 0x0013 }
            java.lang.String r6 = "Failed to parse "
            r5.append(r6)     // Catch:{ all -> 0x0013 }
            r5.append(r0)     // Catch:{ all -> 0x0013 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0013 }
            android.util.Slog.w(r4, r5, r2)     // Catch:{ all -> 0x0013 }
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ all -> 0x0013 }
            r5 = -102(0xffffffffffffff9a, float:NaN)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0013 }
            r6.<init>()     // Catch:{ all -> 0x0013 }
            java.lang.String r7 = "Failed to parse "
            r6.append(r7)     // Catch:{ all -> 0x0013 }
            r6.append(r0)     // Catch:{ all -> 0x0013 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0013 }
            r4.<init>(r5, r6, r2)     // Catch:{ all -> 0x0013 }
            throw r4     // Catch:{ all -> 0x0013 }
        L_0x00ad:
            libcore.io.IoUtils.closeQuietly(r1)
            if (r3 == 0) goto L_0x00b7
            r3.close()     // Catch:{ Throwable -> 0x00b6 }
            goto L_0x00b7
        L_0x00b6:
            r4 = move-exception
        L_0x00b7:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseApkLiteInner(java.io.File, java.io.FileDescriptor, java.lang.String, int):android.content.pm.PackageParser$ApkLite");
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
                if (c == '.') {
                    hasSep = true;
                    front = true;
                } else {
                    return "bad character '" + c + "'";
                }
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
        String str;
        String error;
        do {
            int next = parser.next();
            type = next;
            if (next == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No start tag found");
        } else if (parser.getName().equals(TAG_MANIFEST)) {
            String packageName = attrs.getAttributeValue((String) null, "package");
            if ("android".equals(packageName) || (error = validateName(packageName, true, true)) == null) {
                String splitName = attrs.getAttributeValue((String) null, "split");
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
                String error3 = packageName.intern();
                if (splitName != null) {
                    str = splitName.intern();
                } else {
                    str = splitName;
                }
                return Pair.create(error3, str);
            }
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest package: " + error);
        } else {
            throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No <manifest> tag");
        }
    }

    private static ApkLite parseApkLite(String codePath, XmlPullParser parser, AttributeSet attrs, SigningDetails signingDetails) throws IOException, XmlPullParserException, PackageParserException {
        int minSdkVersion;
        int searchDepth;
        AttributeSet attributeSet = attrs;
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
        boolean coreApp = false;
        boolean isolatedSplits = false;
        int versionCodeMajor = 0;
        int revisionCode = 0;
        int installLocation = -1;
        int versionCode = 0;
        int i = 0;
        while (i < attrs.getAttributeCount()) {
            String attr = attributeSet.getAttributeName(i);
            int targetSdkVersion2 = targetSdkVersion;
            if (attr.equals("installLocation")) {
                installLocation = attributeSet.getAttributeIntValue(i, -1);
            } else if (attr.equals("versionCode")) {
                versionCode = attributeSet.getAttributeIntValue(i, 0);
            } else if (attr.equals("versionCodeMajor")) {
                versionCodeMajor = attributeSet.getAttributeIntValue(i, 0);
            } else if (attr.equals("revisionCode")) {
                revisionCode = attributeSet.getAttributeIntValue(i, 0);
            } else if (attr.equals("coreApp")) {
                coreApp = attributeSet.getAttributeBooleanValue(i, false);
            } else if (attr.equals("isolatedSplits")) {
                isolatedSplits = attributeSet.getAttributeBooleanValue(i, false);
            } else if (attr.equals("configForSplit")) {
                configForSplit = attributeSet.getAttributeValue(i);
            } else if (attr.equals("isFeatureSplit")) {
                isFeatureSplit = attributeSet.getAttributeBooleanValue(i, false);
            } else if (attr.equals("isSplitRequired")) {
                isSplitRequired = attributeSet.getAttributeBooleanValue(i, false);
            }
            i++;
            targetSdkVersion = targetSdkVersion2;
        }
        int targetSdkVersion3 = targetSdkVersion;
        int type = 1;
        int searchDepth2 = parser.getDepth() + 1;
        List<VerifierInfo> verifiers = new ArrayList<>();
        while (true) {
            minSdkVersion = minSdkVersion2;
            int minSdkVersion3 = parser.next();
            int type2 = minSdkVersion3;
            if (minSdkVersion3 == type) {
                int i2 = type2;
                break;
            }
            int type3 = type2;
            if (type3 == 3 && parser.getDepth() < searchDepth2) {
                int i3 = searchDepth2;
                int i4 = type3;
                break;
            }
            if (type3 == 3) {
                searchDepth = searchDepth2;
            } else if (type3 != 4 && parser.getDepth() == searchDepth2) {
                searchDepth = searchDepth2;
                if (TAG_PACKAGE_VERIFIER.equals(parser.getName())) {
                    VerifierInfo verifier = parseVerifier(attrs);
                    if (verifier != null) {
                        verifiers.add(verifier);
                    }
                } else if (TAG_APPLICATION.equals(parser.getName())) {
                    int i5 = 0;
                    while (i5 < attrs.getAttributeCount()) {
                        String attr2 = attributeSet.getAttributeName(i5);
                        int type4 = type3;
                        if ("debuggable".equals(attr2)) {
                            debuggable = attributeSet.getAttributeBooleanValue(i5, false);
                        }
                        if ("multiArch".equals(attr2)) {
                            multiArch = attributeSet.getAttributeBooleanValue(i5, false);
                        }
                        if ("use32bitAbi".equals(attr2)) {
                            use32bitAbi = attributeSet.getAttributeBooleanValue(i5, false);
                        }
                        if ("extractNativeLibs".equals(attr2)) {
                            extractNativeLibs = attributeSet.getAttributeBooleanValue(i5, true);
                        }
                        if ("useEmbeddedDex".equals(attr2)) {
                            useEmbeddedDex = attributeSet.getAttributeBooleanValue(i5, false);
                        }
                        i5++;
                        type3 = type4;
                    }
                    minSdkVersion2 = minSdkVersion;
                    searchDepth2 = searchDepth;
                    type = 1;
                } else {
                    if (TAG_USES_SPLIT.equals(parser.getName())) {
                        if (usesSplitName != null) {
                            Slog.w(TAG, "Only one <uses-split> permitted. Ignoring others.");
                        } else {
                            usesSplitName = attributeSet.getAttributeValue(ANDROID_RESOURCES, "name");
                            if (usesSplitName != null) {
                                minSdkVersion2 = minSdkVersion;
                                searchDepth2 = searchDepth;
                                type = 1;
                            } else {
                                throw new PackageParserException(PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "<uses-split> tag requires 'android:name' attribute");
                            }
                        }
                    } else if (TAG_USES_SDK.equals(parser.getName())) {
                        minSdkVersion2 = minSdkVersion;
                        int i6 = 0;
                        while (i6 < attrs.getAttributeCount()) {
                            String attr3 = attributeSet.getAttributeName(i6);
                            int minSdkVersion4 = minSdkVersion2;
                            if ("targetSdkVersion".equals(attr3)) {
                                targetSdkVersion3 = attributeSet.getAttributeIntValue(i6, 0);
                            }
                            int minSdkVersion5 = "minSdkVersion".equals(attr3) ? attributeSet.getAttributeIntValue(i6, 1) : minSdkVersion4;
                            i6++;
                            minSdkVersion2 = minSdkVersion5;
                        }
                        int i7 = minSdkVersion2;
                        searchDepth2 = searchDepth;
                        type = 1;
                    }
                }
            } else {
                searchDepth = searchDepth2;
            }
            type = 1;
            minSdkVersion2 = minSdkVersion;
            searchDepth2 = searchDepth;
        }
        return new ApkLite(codePath, (String) packageSplit.first, (String) packageSplit.second, isFeatureSplit, configForSplit, usesSplitName, isSplitRequired, versionCode, versionCodeMajor, revisionCode, installLocation, verifiers, signingDetails, coreApp, debuggable, multiArch, use32bitAbi, useEmbeddedDex, extractNativeLibs, isolatedSplits, minSdkVersion, targetSdkVersion3);
    }

    private boolean parseBaseApkChild(Package parentPkg, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        Package packageR = parentPkg;
        String childPackageName = parser.getAttributeValue((String) null, "package");
        if (validateName(childPackageName, true, false) != null) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
            return false;
        } else if (childPackageName.equals(packageR.packageName)) {
            String message = "Child package name cannot be equal to parent package name: " + packageR.packageName;
            Slog.w(TAG, message);
            outError[0] = message;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        } else if (parentPkg.hasChildPackage(childPackageName)) {
            String message2 = "Duplicate child package:" + childPackageName;
            Slog.w(TAG, message2);
            outError[0] = message2;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        } else {
            Package childPkg = new Package(childPackageName);
            childPkg.mVersionCode = packageR.mVersionCode;
            childPkg.baseRevisionCode = packageR.baseRevisionCode;
            childPkg.mVersionName = packageR.mVersionName;
            childPkg.applicationInfo.targetSdkVersion = packageR.applicationInfo.targetSdkVersion;
            childPkg.applicationInfo.minSdkVersion = packageR.applicationInfo.minSdkVersion;
            Package childPkg2 = parseBaseApkCommon(childPkg, CHILD_PACKAGE_TAGS, res, parser, flags, outError);
            if (childPkg2 == null) {
                return false;
            }
            if (packageR.childPackages == null) {
                packageR.childPackages = new ArrayList<>();
            }
            packageR.childPackages.add(childPkg2);
            childPkg2.parentPackage = packageR;
            return true;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private Package parseBaseApk(String apkPath, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        XmlResourceParser xmlResourceParser = parser;
        try {
            Pair<String, String> packageSplit = parsePackageSplitNames(xmlResourceParser, xmlResourceParser);
            String pkgName = (String) packageSplit.first;
            String splitName = (String) packageSplit.second;
            if (!TextUtils.isEmpty(splitName)) {
                outError[0] = "Expected base APK, but found split " + splitName;
                this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
                return null;
            }
            if (this.mCallback != null) {
                String[] overlayPaths = this.mCallback.getOverlayPaths(pkgName, apkPath);
                if (overlayPaths != null && overlayPaths.length > 0) {
                    for (String overlayPath : overlayPaths) {
                        res.getAssets().addOverlayPath(overlayPath);
                    }
                }
            } else {
                String str = apkPath;
            }
            Package pkg = new Package(pkgName);
            TypedArray sa = res.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifest);
            pkg.mVersionCode = sa.getInteger(1, 0);
            pkg.mVersionCodeMajor = sa.getInteger(11, 0);
            pkg.applicationInfo.setVersionCode(pkg.getLongVersionCode());
            pkg.baseRevisionCode = sa.getInteger(5, 0);
            pkg.mVersionName = sa.getNonConfigurationString(2, 0);
            if (pkg.mVersionName != null) {
                pkg.mVersionName = pkg.mVersionName.intern();
            }
            pkg.coreApp = xmlResourceParser.getAttributeBooleanValue((String) null, "coreApp", false);
            pkg.mCompileSdkVersion = sa.getInteger(9, 0);
            pkg.applicationInfo.compileSdkVersion = pkg.mCompileSdkVersion;
            pkg.mCompileSdkVersionCodename = sa.getNonConfigurationString(10, 0);
            if (pkg.mCompileSdkVersionCodename != null) {
                pkg.mCompileSdkVersionCodename = pkg.mCompileSdkVersionCodename.intern();
            }
            pkg.applicationInfo.compileSdkVersionCodename = pkg.mCompileSdkVersionCodename;
            sa.recycle();
            return parseBaseApkCommon(pkg, (Set<String>) null, res, parser, flags, outError);
        } catch (PackageParserException e) {
            String str2 = apkPath;
            Resources resources = res;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:262:0x0773, code lost:
        if (r18 != false) goto L_0x0787;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:0x077b, code lost:
        if (r7.instrumentation.size() != 0) goto L_0x0787;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x077d, code lost:
        r2 = 0;
        r12[0] = "<manifest> does not contain an <application> or <instrumentation>";
        r6.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x0787, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:0x0788, code lost:
        r0 = NEW_PERMISSIONS.length;
        r3 = null;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:0x078e, code lost:
        if (r1 >= r0) goto L_0x07d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x0790, code lost:
        r4 = NEW_PERMISSIONS[r1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:270:0x079a, code lost:
        if (r7.applicationInfo.targetSdkVersion < r4.sdkVersion) goto L_0x079d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:272:0x07a5, code lost:
        if (r7.requestedPermissions.contains(r4.name) != false) goto L_0x07d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:0x07a7, code lost:
        if (r3 != null) goto L_0x07bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:274:0x07a9, code lost:
        r3 = new java.lang.StringBuilder(128);
        r3.append(r7.packageName);
        r3.append(": compat added ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:0x07bc, code lost:
        r3.append(' ');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:276:0x07c1, code lost:
        r3.append(r4.name);
        r7.requestedPermissions.add(r4.name);
        r7.implicitPermissions.add(r4.name);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:277:0x07d4, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:278:0x07d7, code lost:
        if (r3 == null) goto L_0x07e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:279:0x07d9, code lost:
        android.util.Slog.i(TAG, r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:280:0x07e2, code lost:
        r1 = android.permission.PermissionManager.SPLIT_PERMISSIONS.size();
        r4 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x07e9, code lost:
        if (r4 >= r1) goto L_0x0840;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:282:0x07eb, code lost:
        r5 = android.permission.PermissionManager.SPLIT_PERMISSIONS.get(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x07fb, code lost:
        if (r7.applicationInfo.targetSdkVersion >= r5.getTargetSdk()) goto L_0x083a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:285:0x0807, code lost:
        if (r7.requestedPermissions.contains(r5.getSplitPermission()) != false) goto L_0x080d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:286:0x080d, code lost:
        r11 = r5.getNewPermissions();
        r13 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:288:0x0816, code lost:
        if (r13 >= r11.size()) goto L_0x083a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x0818, code lost:
        r2 = r11.get(r13);
        r43 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:290:0x0826, code lost:
        if (r7.requestedPermissions.contains(r2) != false) goto L_0x0832;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:291:0x0828, code lost:
        r7.requestedPermissions.add(r2);
        r7.implicitPermissions.add(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x0832, code lost:
        r13 = r13 + 1;
        r0 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x083a, code lost:
        r4 = r4 + 1;
        r0 = r0;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:294:0x0840, code lost:
        r43 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:295:0x0842, code lost:
        if (r15 < 0) goto L_0x084d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:296:0x0844, code lost:
        if (r15 <= 0) goto L_0x0855;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x084b, code lost:
        if (r7.applicationInfo.targetSdkVersion < 4) goto L_0x0855;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:299:0x084d, code lost:
        r7.applicationInfo.flags |= 512;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:300:0x0855, code lost:
        if (r14 == 0) goto L_0x085f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:301:0x0857, code lost:
        r7.applicationInfo.flags |= 1024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:302:0x085f, code lost:
        if (r8 < 0) goto L_0x086a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:303:0x0861, code lost:
        if (r8 <= 0) goto L_0x0872;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x0868, code lost:
        if (r7.applicationInfo.targetSdkVersion < 4) goto L_0x0872;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:306:0x086a, code lost:
        r7.applicationInfo.flags |= 2048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:307:0x0872, code lost:
        if (r16 < 0) goto L_0x087e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:308:0x0874, code lost:
        if (r16 <= 0) goto L_0x0887;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:310:0x087c, code lost:
        if (r7.applicationInfo.targetSdkVersion < 9) goto L_0x0887;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:311:0x087e, code lost:
        r7.applicationInfo.flags |= 524288;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:312:0x0887, code lost:
        if (r21 < 0) goto L_0x0892;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:313:0x0889, code lost:
        if (r21 <= 0) goto L_0x089a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:315:0x0890, code lost:
        if (r7.applicationInfo.targetSdkVersion < 4) goto L_0x089a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:316:0x0892, code lost:
        r7.applicationInfo.flags |= 4096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:317:0x089a, code lost:
        if (r40 < 0) goto L_0x08a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:318:0x089c, code lost:
        if (r40 <= 0) goto L_0x08ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:320:0x08a3, code lost:
        if (r7.applicationInfo.targetSdkVersion < 4) goto L_0x08ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:321:0x08a5, code lost:
        r7.applicationInfo.flags |= 8192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:323:0x08b3, code lost:
        if (r7.applicationInfo.usesCompatibilityMode() == false) goto L_0x08b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:324:0x08b5, code lost:
        adjustPackageToBeUnresizeableAndUnpipable(r47);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:325:0x08b8, code lost:
        return r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.pm.PackageParser.Package parseBaseApkCommon(android.content.pm.PackageParser.Package r47, java.util.Set<java.lang.String> r48, android.content.res.Resources r49, android.content.res.XmlResourceParser r50, int r51, java.lang.String[] r52) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r46 = this;
            r6 = r46
            r7 = r47
            r8 = r48
            r9 = r49
            r10 = r50
            r12 = r52
            r13 = 0
            r6.mParseInstrumentationArgs = r13
            r0 = 0
            int[] r1 = com.android.internal.R.styleable.AndroidManifest
            android.content.res.TypedArray r1 = r9.obtainAttributes(r10, r1)
            r14 = 0
            java.lang.String r15 = r1.getNonConfigurationString(r14, r14)
            r5 = 3
            r4 = 1
            if (r15 == 0) goto L_0x0061
            int r2 = r15.length()
            if (r2 <= 0) goto L_0x0061
            java.lang.String r2 = validateName(r15, r4, r4)
            if (r2 == 0) goto L_0x0055
            java.lang.String r3 = "android"
            java.lang.String r4 = r7.packageName
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0055
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "<manifest> specifies bad sharedUserId name \""
            r3.append(r4)
            r3.append(r15)
            java.lang.String r4 = "\": "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            r12[r14] = r3
            r3 = -107(0xffffffffffffff95, float:NaN)
            r6.mParseError = r3
            return r13
        L_0x0055:
            java.lang.String r3 = r15.intern()
            r7.mSharedUserId = r3
            int r3 = r1.getResourceId(r5, r14)
            r7.mSharedUserLabel = r3
        L_0x0061:
            r2 = -1
            r4 = 4
            int r2 = r1.getInteger(r4, r2)
            r7.installLocation = r2
            android.content.pm.ApplicationInfo r2 = r7.applicationInfo
            int r3 = r7.installLocation
            r2.installLocation = r3
            r3 = 7
            r2 = 1
            int r13 = r1.getInteger(r3, r2)
            android.content.pm.ApplicationInfo r2 = r7.applicationInfo
            r2.targetSandboxVersion = r13
            r2 = r51 & 8
            if (r2 == 0) goto L_0x0087
            android.content.pm.ApplicationInfo r2 = r7.applicationInfo
            int r3 = r2.flags
            r18 = 262144(0x40000, float:3.67342E-40)
            r3 = r3 | r18
            r2.flags = r3
        L_0x0087:
            r3 = 6
            boolean r2 = r1.getBoolean(r3, r14)
            if (r2 == 0) goto L_0x0099
            android.content.pm.ApplicationInfo r2 = r7.applicationInfo
            int r3 = r2.privateFlags
            r18 = 32768(0x8000, float:4.5918E-41)
            r3 = r3 | r18
            r2.privateFlags = r3
        L_0x0099:
            r2 = 1
            r3 = 1
            r18 = 1
            r20 = 1
            r21 = 1
            r22 = 1
            int r23 = r50.getDepth()
            r24 = r21
            r25 = r22
            r44 = r18
            r18 = r0
            r0 = r20
            r20 = r1
            r1 = r44
            r45 = r3
            r3 = r2
            r2 = r45
        L_0x00ba:
            r26 = r23
            int r14 = r50.next()
            r27 = r14
            r4 = 1
            if (r14 == r4) goto L_0x0762
            r14 = r27
            if (r14 != r5) goto L_0x00e5
            int r4 = r50.getDepth()
            r5 = r26
            if (r4 <= r5) goto L_0x00d2
            goto L_0x00e7
        L_0x00d2:
            r16 = r0
            r8 = r1
            r17 = r5
            r28 = r13
            r31 = r14
            r32 = r15
            r21 = r24
            r40 = r25
            r14 = r2
            r15 = r3
            goto L_0x0773
        L_0x00e5:
            r5 = r26
        L_0x00e7:
            r4 = 3
            if (r14 == r4) goto L_0x0737
            r4 = 4
            if (r14 != r4) goto L_0x0104
            r16 = r0
            r8 = r1
            r14 = r2
            r17 = r5
            r28 = r13
            r32 = r15
            r21 = r24
            r40 = r25
        L_0x00fc:
            r0 = 0
            r19 = 3
            r22 = 7
            r15 = r3
            goto L_0x074b
        L_0x0104:
            r28 = r13
            java.lang.String r13 = r50.getName()
            if (r8 == 0) goto L_0x0154
            boolean r16 = r8.contains(r13)
            if (r16 != 0) goto L_0x0154
            java.lang.String r4 = "PackageParser"
            r29 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r30 = r1
            java.lang.String r1 = "Skipping unsupported element under <manifest>: "
            r0.append(r1)
            r0.append(r13)
            java.lang.String r1 = " at "
            r0.append(r1)
            java.lang.String r1 = r6.mArchiveSourcePath
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.lang.String r1 = r50.getPositionDescription()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r0)
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
        L_0x0146:
            r14 = r2
            r17 = r5
            r32 = r15
            r21 = r24
            r40 = r25
            r16 = r29
            r8 = r30
            goto L_0x00fc
        L_0x0154:
            r29 = r0
            r30 = r1
            java.lang.String r0 = "application"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x01a1
            if (r18 == 0) goto L_0x016d
            java.lang.String r0 = "PackageParser"
            java.lang.String r1 = "<manifest> has more than one <application>"
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r1)
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x0146
        L_0x016d:
            r16 = 1
            r4 = r29
            r0 = r46
            r8 = r30
            r1 = r47
            r31 = r14
            r14 = r2
            r2 = r49
            r32 = r15
            r11 = 6
            r15 = r3
            r3 = r50
            r33 = r4
            r11 = 1
            r17 = 3
            r4 = r51
            r17 = r5
            r5 = r52
            boolean r0 = r0.parseBaseApplication(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x0195
            r0 = 0
            return r0
        L_0x0195:
            r1 = r8
            r2 = r14
            r3 = r15
            r18 = r16
        L_0x019a:
            r0 = 0
            r19 = 3
        L_0x019d:
            r22 = 7
            goto L_0x072c
        L_0x01a1:
            r17 = r5
            r31 = r14
            r32 = r15
            r33 = r29
            r8 = r30
            r11 = 1
            r14 = r2
            r15 = r3
            java.lang.String r0 = "overlay"
            boolean r0 = r13.equals(r0)
            r1 = 5
            r2 = 2
            if (r0 == 0) goto L_0x025d
            int[] r0 = com.android.internal.R.styleable.AndroidManifestResourceOverlay
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            java.lang.String r3 = r0.getString(r11)
            r7.mOverlayTarget = r3
            r5 = 3
            java.lang.String r3 = r0.getString(r5)
            r7.mOverlayTargetName = r3
            java.lang.String r2 = r0.getString(r2)
            r7.mOverlayCategory = r2
            r2 = 0
            int r3 = r0.getInt(r2, r2)
            r7.mOverlayPriority = r3
            r4 = 4
            boolean r3 = r0.getBoolean(r4, r2)
            r7.mOverlayIsStatic = r3
            java.lang.String r1 = r0.getString(r1)
            r2 = 6
            java.lang.String r3 = r0.getString(r2)
            r0.recycle()
            java.lang.String r2 = r7.mOverlayTarget
            r4 = -108(0xffffffffffffff94, float:NaN)
            if (r2 != 0) goto L_0x01fb
            java.lang.String r2 = "<overlay> does not specify a target package"
            r5 = 0
            r12[r5] = r2
            r6.mParseError = r4
            r2 = 0
            return r2
        L_0x01fb:
            int r2 = r7.mOverlayPriority
            if (r2 < 0) goto L_0x0254
            int r2 = r7.mOverlayPriority
            r5 = 9999(0x270f, float:1.4012E-41)
            if (r2 <= r5) goto L_0x0206
            goto L_0x0254
        L_0x0206:
            boolean r2 = r6.checkOverlayRequiredSystemProperty(r1, r3)
            if (r2 != 0) goto L_0x0240
            java.lang.String r2 = "PackageParser"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Skipping target and overlay pair "
            r4.append(r5)
            java.lang.String r5 = r7.mOverlayTarget
            r4.append(r5)
            java.lang.String r5 = " and "
            r4.append(r5)
            java.lang.String r5 = r7.baseCodePath
            r4.append(r5)
            java.lang.String r5 = ": overlay ignored due to required system property: "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = " with value: "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Slog.i(r2, r4)
            r2 = 0
            return r2
        L_0x0240:
            android.content.pm.ApplicationInfo r2 = r7.applicationInfo
            int r4 = r2.privateFlags
            r5 = 268435456(0x10000000, float:2.5243549E-29)
            r4 = r4 | r5
            r2.privateFlags = r4
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            r20 = r0
        L_0x024f:
            r1 = r8
            r2 = r14
            r3 = r15
            goto L_0x019a
        L_0x0254:
            java.lang.String r2 = "<overlay> priority must be between 0 and 9999"
            r5 = 0
            r12[r5] = r2
            r6.mParseError = r4
            r4 = 0
            return r4
        L_0x025d:
            r4 = 0
            java.lang.String r0 = "key-sets"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x027a
            boolean r0 = r6.parseKeySets(r7, r9, r10, r12)
            if (r0 != 0) goto L_0x026d
            return r4
        L_0x026d:
            r0 = r4
        L_0x026e:
            r21 = r24
            r40 = r25
            r16 = r33
        L_0x0274:
            r19 = 3
        L_0x0276:
            r22 = 7
            goto L_0x0723
        L_0x027a:
            java.lang.String r0 = "permission-group"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x02a0
            r0 = r46
            r1 = r47
            r2 = r51
            r3 = r49
            r5 = 4
            r4 = r50
            r5 = r52
            boolean r0 = r0.parsePermissionGroup(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x0298
            r0 = 0
            return r0
        L_0x0298:
            r21 = r24
            r40 = r25
            r16 = r33
            r0 = 0
            goto L_0x0274
        L_0x02a0:
            r0 = 0
            java.lang.String r3 = "permission"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x02b1
            boolean r1 = r6.parsePermission(r7, r9, r10, r12)
            if (r1 != 0) goto L_0x026e
            return r0
        L_0x02b1:
            java.lang.String r3 = "permission-tree"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x02c1
            boolean r1 = r6.parsePermissionTree(r7, r9, r10, r12)
            if (r1 != 0) goto L_0x026e
            return r0
        L_0x02c1:
            java.lang.String r3 = "uses-permission"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x02d1
            boolean r1 = r6.parseUsesPermission(r7, r9, r10)
            if (r1 != 0) goto L_0x026e
            return r0
        L_0x02d1:
            java.lang.String r0 = "uses-permission-sdk-m"
            boolean r0 = r13.equals(r0)
            if (r0 != 0) goto L_0x0710
            java.lang.String r0 = "uses-permission-sdk-23"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x02ef
            r21 = r24
            r40 = r25
            r16 = r33
            r19 = 3
            r22 = 7
            goto L_0x071a
        L_0x02ef:
            java.lang.String r0 = "uses-configuration"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0347
            android.content.pm.ConfigurationInfo r0 = new android.content.pm.ConfigurationInfo
            r0.<init>()
            int[] r1 = com.android.internal.R.styleable.AndroidManifestUsesConfiguration
            android.content.res.TypedArray r1 = r9.obtainAttributes(r10, r1)
            r3 = 0
            int r4 = r1.getInt(r3, r3)
            r0.reqTouchScreen = r4
            int r4 = r1.getInt(r11, r3)
            r0.reqKeyboardType = r4
            boolean r4 = r1.getBoolean(r2, r3)
            if (r4 == 0) goto L_0x031b
            int r4 = r0.reqInputFeatures
            r4 = r4 | r11
            r0.reqInputFeatures = r4
        L_0x031b:
            r5 = 3
            int r4 = r1.getInt(r5, r3)
            r0.reqNavigation = r4
            r4 = 4
            boolean r16 = r1.getBoolean(r4, r3)
            if (r16 == 0) goto L_0x032e
            int r3 = r0.reqInputFeatures
            r2 = r2 | r3
            r0.reqInputFeatures = r2
        L_0x032e:
            r1.recycle()
            java.util.ArrayList<android.content.pm.ConfigurationInfo> r2 = r7.configPreferences
            java.util.ArrayList r2 = com.android.internal.util.ArrayUtils.add(r2, r0)
            r7.configPreferences = r2
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            r20 = r1
            r19 = r5
            r1 = r8
            r2 = r14
            r3 = r15
            r0 = 0
            goto L_0x019d
        L_0x0347:
            r4 = 4
            r5 = 3
            java.lang.String r0 = "uses-feature"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0382
            android.content.pm.FeatureInfo r0 = r6.parseUsesFeature(r9, r10)
            java.util.ArrayList<android.content.pm.FeatureInfo> r1 = r7.reqFeatures
            java.util.ArrayList r1 = com.android.internal.util.ArrayUtils.add(r1, r0)
            r7.reqFeatures = r1
            java.lang.String r1 = r0.name
            if (r1 != 0) goto L_0x0373
            android.content.pm.ConfigurationInfo r1 = new android.content.pm.ConfigurationInfo
            r1.<init>()
            int r2 = r0.reqGlEsVersion
            r1.reqGlEsVersion = r2
            java.util.ArrayList<android.content.pm.ConfigurationInfo> r2 = r7.configPreferences
            java.util.ArrayList r2 = com.android.internal.util.ArrayUtils.add(r2, r1)
            r7.configPreferences = r2
        L_0x0373:
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            r19 = r5
            r21 = r24
            r40 = r25
            r16 = r33
            r0 = 0
            goto L_0x0276
        L_0x0382:
            java.lang.String r0 = "feature-group"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0434
            android.content.pm.FeatureGroupInfo r0 = new android.content.pm.FeatureGroupInfo
            r0.<init>()
            r1 = 0
            int r2 = r50.getDepth()
        L_0x0394:
            int r3 = r50.next()
            r34 = r3
            if (r3 == r11) goto L_0x040f
            r3 = r34
            if (r3 != r5) goto L_0x03aa
            int r11 = r50.getDepth()
            if (r11 <= r2) goto L_0x03a7
            goto L_0x03aa
        L_0x03a7:
            r35 = r2
            goto L_0x0413
        L_0x03aa:
            if (r3 == r5) goto L_0x0405
            if (r3 != r4) goto L_0x03b2
            r35 = r2
            goto L_0x0407
        L_0x03b2:
            java.lang.String r11 = r50.getName()
            java.lang.String r4 = "uses-feature"
            boolean r4 = r11.equals(r4)
            if (r4 == 0) goto L_0x03d3
            android.content.pm.FeatureInfo r4 = r6.parseUsesFeature(r9, r10)
            int r5 = r4.flags
            r16 = 1
            r5 = r5 | 1
            r4.flags = r5
            java.util.ArrayList r1 = com.android.internal.util.ArrayUtils.add(r1, r4)
            r35 = r2
            goto L_0x0401
        L_0x03d3:
            java.lang.String r4 = "PackageParser"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r35 = r2
            java.lang.String r2 = "Unknown element under <feature-group>: "
            r5.append(r2)
            r5.append(r11)
            java.lang.String r2 = " at "
            r5.append(r2)
            java.lang.String r2 = r6.mArchiveSourcePath
            r5.append(r2)
            java.lang.String r2 = " "
            r5.append(r2)
            java.lang.String r2 = r50.getPositionDescription()
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r2)
        L_0x0401:
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x0407
        L_0x0405:
            r35 = r2
        L_0x0407:
            r31 = r3
            r2 = r35
            r4 = 4
            r5 = 3
            r11 = 1
            goto L_0x0394
        L_0x040f:
            r35 = r2
            r3 = r34
        L_0x0413:
            if (r1 == 0) goto L_0x0427
            int r2 = r1.size()
            android.content.pm.FeatureInfo[] r2 = new android.content.pm.FeatureInfo[r2]
            r0.features = r2
            android.content.pm.FeatureInfo[] r2 = r0.features
            java.lang.Object[] r2 = r1.toArray(r2)
            android.content.pm.FeatureInfo[] r2 = (android.content.pm.FeatureInfo[]) r2
            r0.features = r2
        L_0x0427:
            java.util.ArrayList<android.content.pm.FeatureGroupInfo> r2 = r7.featureGroups
            java.util.ArrayList r2 = com.android.internal.util.ArrayUtils.add(r2, r0)
            r7.featureGroups = r2
            r31 = r3
            goto L_0x024f
        L_0x0434:
            java.lang.String r0 = "uses-sdk"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x04c8
            int r0 = SDK_VERSION
            if (r0 <= 0) goto L_0x04c3
            int[] r0 = com.android.internal.R.styleable.AndroidManifestUsesSdk
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            r1 = 1
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            android.util.TypedValue r11 = r0.peekValue(r5)
            if (r11 == 0) goto L_0x0469
            int r5 = r11.type
            r36 = r1
            r1 = 3
            if (r5 != r1) goto L_0x0466
            java.lang.CharSequence r1 = r11.string
            if (r1 == 0) goto L_0x0466
            java.lang.CharSequence r1 = r11.string
            java.lang.String r2 = r1.toString()
            r1 = r36
            goto L_0x046b
        L_0x0466:
            int r1 = r11.data
            goto L_0x046b
        L_0x0469:
            r36 = r1
        L_0x046b:
            r5 = 1
            android.util.TypedValue r11 = r0.peekValue(r5)
            if (r11 == 0) goto L_0x048c
            int r5 = r11.type
            r37 = r3
            r3 = 3
            if (r5 != r3) goto L_0x0489
            java.lang.CharSequence r3 = r11.string
            if (r3 == 0) goto L_0x0489
            java.lang.CharSequence r3 = r11.string
            java.lang.String r4 = r3.toString()
            if (r2 != 0) goto L_0x0486
            r2 = r4
        L_0x0486:
            r3 = r37
            goto L_0x0490
        L_0x0489:
            int r3 = r11.data
            goto L_0x0490
        L_0x048c:
            r37 = r3
            r3 = r1
            r4 = r2
        L_0x0490:
            r0.recycle()
            int r5 = SDK_VERSION
            r38 = r0
            java.lang.String[] r0 = SDK_CODENAMES
            int r0 = computeMinSdkVersion(r1, r2, r5, r0, r12)
            r5 = -12
            if (r0 >= 0) goto L_0x04a6
            r6.mParseError = r5
            r16 = 0
            return r16
        L_0x04a6:
            r16 = 0
            java.lang.String[] r5 = SDK_CODENAMES
            int r5 = computeTargetSdkVersion(r3, r4, r5, r12)
            if (r5 >= 0) goto L_0x04b7
            r39 = r1
            r1 = -12
            r6.mParseError = r1
            return r16
        L_0x04b7:
            r39 = r1
            android.content.pm.ApplicationInfo r1 = r7.applicationInfo
            r1.minSdkVersion = r0
            android.content.pm.ApplicationInfo r1 = r7.applicationInfo
            r1.targetSdkVersion = r5
            r20 = r38
        L_0x04c3:
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x024f
        L_0x04c8:
            java.lang.String r0 = "supports-screens"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x052c
            int[] r0 = com.android.internal.R.styleable.AndroidManifestSupportsScreens
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            android.content.pm.ApplicationInfo r3 = r7.applicationInfo
            r4 = 0
            r11 = 6
            int r5 = r0.getInteger(r11, r4)
            r3.requiresSmallestWidthDp = r5
            android.content.pm.ApplicationInfo r3 = r7.applicationInfo
            r5 = 7
            int r11 = r0.getInteger(r5, r4)
            r3.compatibleWidthLimitDp = r11
            android.content.pm.ApplicationInfo r3 = r7.applicationInfo
            r11 = 8
            int r11 = r0.getInteger(r11, r4)
            r3.largestWidthLimitDp = r11
            r11 = 1
            int r3 = r0.getInteger(r11, r15)
            int r2 = r0.getInteger(r2, r14)
            r4 = 3
            int r8 = r0.getInteger(r4, r8)
            r14 = r33
            int r1 = r0.getInteger(r1, r14)
            r14 = r24
            r15 = 4
            int r14 = r0.getInteger(r15, r14)
            r11 = r25
            r4 = 0
            int r11 = r0.getInteger(r4, r11)
            r0.recycle()
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            r20 = r0
            r33 = r1
            r22 = r5
            r1 = r8
            r25 = r11
            r24 = r14
        L_0x0527:
            r0 = 0
            r19 = 3
            goto L_0x072c
        L_0x052c:
            r21 = r24
            r11 = r25
            r16 = r33
            r4 = 4
            r5 = 7
            java.lang.String r0 = "protected-broadcast"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x057b
            int[] r0 = com.android.internal.R.styleable.AndroidManifestProtectedBroadcast
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            r1 = 0
            java.lang.String r2 = r0.getNonResourceString(r1)
            r0.recycle()
            if (r2 == 0) goto L_0x0569
            java.util.ArrayList<java.lang.String> r1 = r7.protectedBroadcasts
            if (r1 != 0) goto L_0x0558
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r7.protectedBroadcasts = r1
        L_0x0558:
            java.util.ArrayList<java.lang.String> r1 = r7.protectedBroadcasts
            boolean r1 = r1.contains(r2)
            if (r1 != 0) goto L_0x0569
            java.util.ArrayList<java.lang.String> r1 = r7.protectedBroadcasts
            java.lang.String r3 = r2.intern()
            r1.add(r3)
        L_0x0569:
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
        L_0x056d:
            r20 = r0
            r22 = r5
            r1 = r8
            r25 = r11
            r2 = r14
            r3 = r15
            r33 = r16
            r24 = r21
            goto L_0x0527
        L_0x057b:
            java.lang.String r0 = "instrumentation"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0594
            android.content.pm.PackageParser$Instrumentation r0 = r6.parseInstrumentation(r7, r9, r10, r12)
            if (r0 != 0) goto L_0x058b
            r0 = 0
            return r0
        L_0x058b:
            r22 = r5
            r40 = r11
            r0 = 0
            r19 = 3
            goto L_0x0723
        L_0x0594:
            java.lang.String r0 = "original-package"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x05cb
            int[] r0 = com.android.internal.R.styleable.AndroidManifestOriginalPackage
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            r1 = 0
            java.lang.String r2 = r0.getNonConfigurationString(r1, r1)
            java.lang.String r1 = r7.packageName
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x05c4
            java.util.ArrayList<java.lang.String> r1 = r7.mOriginalPackages
            if (r1 != 0) goto L_0x05bf
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r7.mOriginalPackages = r1
            java.lang.String r1 = r7.packageName
            r7.mRealPackage = r1
        L_0x05bf:
            java.util.ArrayList<java.lang.String> r1 = r7.mOriginalPackages
            r1.add(r2)
        L_0x05c4:
            r0.recycle()
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x056d
        L_0x05cb:
            java.lang.String r0 = "adopt-permissions"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x05f8
            int[] r0 = com.android.internal.R.styleable.AndroidManifestOriginalPackage
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            r1 = 0
            java.lang.String r2 = r0.getNonConfigurationString(r1, r1)
            r0.recycle()
            if (r2 == 0) goto L_0x05f3
            java.util.ArrayList<java.lang.String> r1 = r7.mAdoptPermissions
            if (r1 != 0) goto L_0x05ee
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r7.mAdoptPermissions = r1
        L_0x05ee:
            java.util.ArrayList<java.lang.String> r1 = r7.mAdoptPermissions
            r1.add(r2)
        L_0x05f3:
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x056d
        L_0x05f8:
            java.lang.String r0 = "uses-gl-texture"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x060e
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
        L_0x0605:
            r22 = r5
            r40 = r11
            r0 = 0
            r19 = 3
            goto L_0x074b
        L_0x060e:
            java.lang.String r0 = "compatible-screens"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x061a
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x0605
        L_0x061a:
            java.lang.String r0 = "supports-input"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0627
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x0605
        L_0x0627:
            java.lang.String r0 = "eat-comment"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0633
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x0605
        L_0x0633:
            java.lang.String r0 = "package"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x065f
            boolean r0 = MULTI_PACKAGE_APK_ENABLED
            if (r0 != 0) goto L_0x0644
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            goto L_0x0605
        L_0x0644:
            r0 = r46
            r1 = r47
            r2 = r49
            r3 = r50
            r40 = r11
            r19 = 3
            r11 = r4
            r4 = r51
            r22 = r5
            r5 = r52
            boolean r0 = r0.parseBaseApkChild(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x0722
            r0 = 0
            return r0
        L_0x065f:
            r22 = r5
            r40 = r11
            r19 = 3
            r11 = r4
            java.lang.String r0 = "restrict-update"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x06da
            r1 = 6
            r2 = r51 & 16
            if (r2 == 0) goto L_0x06c8
            int[] r2 = com.android.internal.R.styleable.AndroidManifestRestrictUpdate
            android.content.res.TypedArray r2 = r9.obtainAttributes(r10, r2)
            r3 = 0
            java.lang.String r4 = r2.getNonConfigurationString(r3, r3)
            r2.recycle()
            r3 = 0
            r7.restrictUpdateHash = r3
            if (r4 == 0) goto L_0x06c5
            int r3 = r4.length()
            int r5 = r3 / 2
            byte[] r5 = new byte[r5]
            r20 = 0
        L_0x0691:
            r41 = r20
            r1 = r41
            if (r1 >= r3) goto L_0x06c0
            int r41 = r1 / 2
            char r11 = r4.charAt(r1)
            r0 = 16
            int r11 = java.lang.Character.digit(r11, r0)
            r20 = 4
            int r11 = r11 << 4
            int r0 = r1 + 1
            char r0 = r4.charAt(r0)
            r42 = r2
            r2 = 16
            int r0 = java.lang.Character.digit(r0, r2)
            int r11 = r11 + r0
            byte r0 = (byte) r11
            r5[r41] = r0
            int r20 = r1 + 2
            r2 = r42
            r1 = 6
            r11 = 4
            goto L_0x0691
        L_0x06c0:
            r42 = r2
            r7.restrictUpdateHash = r5
            goto L_0x06ca
        L_0x06c5:
            r42 = r2
            goto L_0x06ca
        L_0x06c8:
            r42 = r20
        L_0x06ca:
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            r1 = r8
            r2 = r14
            r3 = r15
            r33 = r16
            r24 = r21
            r25 = r40
            r20 = r42
            r0 = 0
            goto L_0x072c
        L_0x06da:
            java.lang.String r0 = "PackageParser"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unknown element under <manifest>: "
            r1.append(r2)
            java.lang.String r2 = r50.getName()
            r1.append(r2)
            java.lang.String r2 = " at "
            r1.append(r2)
            java.lang.String r2 = r6.mArchiveSourcePath
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = r50.getPositionDescription()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r1)
            com.android.internal.util.XmlUtils.skipCurrentTag(r50)
            r0 = 0
            goto L_0x074b
        L_0x0710:
            r21 = r24
            r40 = r25
            r16 = r33
            r19 = 3
            r22 = 7
        L_0x071a:
            boolean r0 = r6.parseUsesPermission(r7, r9, r10)
            if (r0 != 0) goto L_0x0722
            r0 = 0
            return r0
        L_0x0722:
            r0 = 0
        L_0x0723:
            r1 = r8
            r2 = r14
            r3 = r15
            r33 = r16
            r24 = r21
            r25 = r40
        L_0x072c:
            r23 = r17
            r5 = r19
            r13 = r28
            r15 = r32
            r0 = r33
            goto L_0x075c
        L_0x0737:
            r16 = r0
            r8 = r1
            r14 = r2
            r19 = r4
            r17 = r5
            r28 = r13
            r32 = r15
            r21 = r24
            r40 = r25
            r0 = 0
            r22 = 7
            r15 = r3
        L_0x074b:
            r1 = r8
            r2 = r14
            r3 = r15
            r0 = r16
            r23 = r17
            r5 = r19
            r24 = r21
            r13 = r28
            r15 = r32
            r25 = r40
        L_0x075c:
            r4 = 4
            r8 = r48
            r14 = 0
            goto L_0x00ba
        L_0x0762:
            r16 = r0
            r8 = r1
            r14 = r2
            r28 = r13
            r32 = r15
            r21 = r24
            r40 = r25
            r17 = r26
            r31 = r27
            r15 = r3
        L_0x0773:
            if (r18 != 0) goto L_0x0787
            java.util.ArrayList<android.content.pm.PackageParser$Instrumentation> r0 = r7.instrumentation
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0787
            java.lang.String r0 = "<manifest> does not contain an <application> or <instrumentation>"
            r2 = 0
            r12[r2] = r0
            r0 = -109(0xffffffffffffff93, float:NaN)
            r6.mParseError = r0
            goto L_0x0788
        L_0x0787:
            r2 = 0
        L_0x0788:
            android.content.pm.PackageParser$NewPermissionInfo[] r0 = NEW_PERMISSIONS
            int r0 = r0.length
            r1 = 0
            r3 = r1
            r1 = r2
        L_0x078e:
            if (r1 >= r0) goto L_0x07d7
            android.content.pm.PackageParser$NewPermissionInfo[] r4 = NEW_PERMISSIONS
            r4 = r4[r1]
            android.content.pm.ApplicationInfo r5 = r7.applicationInfo
            int r5 = r5.targetSdkVersion
            int r11 = r4.sdkVersion
            if (r5 < r11) goto L_0x079d
            goto L_0x07d7
        L_0x079d:
            java.util.ArrayList<java.lang.String> r5 = r7.requestedPermissions
            java.lang.String r11 = r4.name
            boolean r5 = r5.contains(r11)
            if (r5 != 0) goto L_0x07d4
            if (r3 != 0) goto L_0x07bc
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r11 = 128(0x80, float:1.794E-43)
            r5.<init>(r11)
            r3 = r5
            java.lang.String r5 = r7.packageName
            r3.append(r5)
            java.lang.String r5 = ": compat added "
            r3.append(r5)
            goto L_0x07c1
        L_0x07bc:
            r5 = 32
            r3.append(r5)
        L_0x07c1:
            java.lang.String r5 = r4.name
            r3.append(r5)
            java.util.ArrayList<java.lang.String> r5 = r7.requestedPermissions
            java.lang.String r11 = r4.name
            r5.add(r11)
            java.util.ArrayList<java.lang.String> r5 = r7.implicitPermissions
            java.lang.String r11 = r4.name
            r5.add(r11)
        L_0x07d4:
            int r1 = r1 + 1
            goto L_0x078e
        L_0x07d7:
            if (r3 == 0) goto L_0x07e2
            java.lang.String r1 = "PackageParser"
            java.lang.String r4 = r3.toString()
            android.util.Slog.i(r1, r4)
        L_0x07e2:
            java.util.ArrayList<android.permission.PermissionManager$SplitPermissionInfo> r1 = android.permission.PermissionManager.SPLIT_PERMISSIONS
            int r1 = r1.size()
            r4 = r2
        L_0x07e9:
            if (r4 >= r1) goto L_0x0840
            java.util.ArrayList<android.permission.PermissionManager$SplitPermissionInfo> r5 = android.permission.PermissionManager.SPLIT_PERMISSIONS
            java.lang.Object r5 = r5.get(r4)
            android.permission.PermissionManager$SplitPermissionInfo r5 = (android.permission.PermissionManager.SplitPermissionInfo) r5
            android.content.pm.ApplicationInfo r11 = r7.applicationInfo
            int r11 = r11.targetSdkVersion
            int r13 = r5.getTargetSdk()
            if (r11 >= r13) goto L_0x0838
            java.util.ArrayList<java.lang.String> r11 = r7.requestedPermissions
            java.lang.String r13 = r5.getSplitPermission()
            boolean r11 = r11.contains(r13)
            if (r11 != 0) goto L_0x080d
            r43 = r0
            goto L_0x083a
        L_0x080d:
            java.util.List r11 = r5.getNewPermissions()
            r13 = r2
        L_0x0812:
            int r2 = r11.size()
            if (r13 >= r2) goto L_0x0838
            java.lang.Object r2 = r11.get(r13)
            java.lang.String r2 = (java.lang.String) r2
            r43 = r0
            java.util.ArrayList<java.lang.String> r0 = r7.requestedPermissions
            boolean r0 = r0.contains(r2)
            if (r0 != 0) goto L_0x0832
            java.util.ArrayList<java.lang.String> r0 = r7.requestedPermissions
            r0.add(r2)
            java.util.ArrayList<java.lang.String> r0 = r7.implicitPermissions
            r0.add(r2)
        L_0x0832:
            int r13 = r13 + 1
            r0 = r43
            r2 = 0
            goto L_0x0812
        L_0x0838:
            r43 = r0
        L_0x083a:
            int r4 = r4 + 1
            r0 = r43
            r2 = 0
            goto L_0x07e9
        L_0x0840:
            r43 = r0
            if (r15 < 0) goto L_0x084d
            if (r15 <= 0) goto L_0x0855
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r0 = r0.targetSdkVersion
            r2 = 4
            if (r0 < r2) goto L_0x0855
        L_0x084d:
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r2 = r0.flags
            r2 = r2 | 512(0x200, float:7.175E-43)
            r0.flags = r2
        L_0x0855:
            if (r14 == 0) goto L_0x085f
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r2 = r0.flags
            r2 = r2 | 1024(0x400, float:1.435E-42)
            r0.flags = r2
        L_0x085f:
            if (r8 < 0) goto L_0x086a
            if (r8 <= 0) goto L_0x0872
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r0 = r0.targetSdkVersion
            r2 = 4
            if (r0 < r2) goto L_0x0872
        L_0x086a:
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r2 = r0.flags
            r2 = r2 | 2048(0x800, float:2.87E-42)
            r0.flags = r2
        L_0x0872:
            if (r16 < 0) goto L_0x087e
            if (r16 <= 0) goto L_0x0887
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r0 = r0.targetSdkVersion
            r2 = 9
            if (r0 < r2) goto L_0x0887
        L_0x087e:
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r2 = r0.flags
            r4 = 524288(0x80000, float:7.34684E-40)
            r2 = r2 | r4
            r0.flags = r2
        L_0x0887:
            if (r21 < 0) goto L_0x0892
            if (r21 <= 0) goto L_0x089a
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r0 = r0.targetSdkVersion
            r2 = 4
            if (r0 < r2) goto L_0x089a
        L_0x0892:
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r2 = r0.flags
            r2 = r2 | 4096(0x1000, float:5.74E-42)
            r0.flags = r2
        L_0x089a:
            if (r40 < 0) goto L_0x08a5
            if (r40 <= 0) goto L_0x08ad
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r0 = r0.targetSdkVersion
            r2 = 4
            if (r0 < r2) goto L_0x08ad
        L_0x08a5:
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            int r2 = r0.flags
            r2 = r2 | 8192(0x2000, float:1.14794E-41)
            r0.flags = r2
        L_0x08ad:
            android.content.pm.ApplicationInfo r0 = r7.applicationInfo
            boolean r0 = r0.usesCompatibilityMode()
            if (r0 == 0) goto L_0x08b8
            r46.adjustPackageToBeUnresizeableAndUnpipable(r47)
        L_0x08b8:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseBaseApkCommon(android.content.pm.PackageParser$Package, java.util.Set, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[]):android.content.pm.PackageParser$Package");
    }

    private boolean checkOverlayRequiredSystemProperty(String propName, String propValue) {
        if (!TextUtils.isEmpty(propName) && !TextUtils.isEmpty(propValue)) {
            String currValue = SystemProperties.get(propName);
            if (currValue == null || !currValue.equals(propValue)) {
                return false;
            }
            return true;
        } else if (TextUtils.isEmpty(propName) && TextUtils.isEmpty(propValue)) {
            return true;
        } else {
            Slog.w(TAG, "Disabling overlay - incomplete property :'" + propName + "=" + propValue + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
            return false;
        }
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
        return ArrayUtils.contains((T[]) codeNames, targetCodeName);
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
        TypedArray sa = res.obtainAttributes(attrs, R.styleable.AndroidManifestUsesFeature);
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
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesStaticLibrary);
        String lname = sa.getNonResourceString(0);
        int version = sa.getInt(1, -1);
        String certSha256Digest = sa.getNonResourceString(2);
        sa.recycle();
        if (lname == null || version < 0 || certSha256Digest == null) {
            outError[0] = "Bad uses-static-library declaration name: " + lname + " version: " + version + " certDigest" + certSha256Digest;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            XmlUtils.skipCurrentTag(parser);
            return false;
        } else if (pkg.usesStaticLibraries == null || !pkg.usesStaticLibraries.contains(lname)) {
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
            String[] certSha256Digests = new String[(additionalCertSha256Digests.length + 1)];
            certSha256Digests[0] = certSha256Digest2;
            System.arraycopy(additionalCertSha256Digests, 0, certSha256Digests, 1, additionalCertSha256Digests.length);
            pkg.usesStaticLibraries = ArrayUtils.add(pkg.usesStaticLibraries, lname2);
            pkg.usesStaticLibrariesVersions = ArrayUtils.appendLong(pkg.usesStaticLibrariesVersions, (long) version, true);
            pkg.usesStaticLibrariesCertDigests = (String[][]) ArrayUtils.appendElement(String[].class, pkg.usesStaticLibrariesCertDigests, certSha256Digests, true);
            return true;
        } else {
            outError[0] = "Depending on multiple versions of static library " + lname;
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            XmlUtils.skipCurrentTag(parser);
            return false;
        }
    }

    /* JADX WARNING: type inference failed for: r6v2, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] parseAdditionalCertificates(android.content.res.Resources r10, android.content.res.XmlResourceParser r11, java.lang.String[] r12) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r9 = this;
            java.lang.String[] r0 = libcore.util.EmptyArray.STRING
            int r1 = r11.getDepth()
        L_0x0006:
            int r2 = r11.next()
            r3 = r2
            r4 = 1
            if (r2 == r4) goto L_0x0076
            r2 = 3
            if (r3 != r2) goto L_0x0017
            int r4 = r11.getDepth()
            if (r4 <= r1) goto L_0x0076
        L_0x0017:
            if (r3 == r2) goto L_0x0006
            r2 = 4
            if (r3 != r2) goto L_0x001d
            goto L_0x0006
        L_0x001d:
            java.lang.String r2 = r11.getName()
            java.lang.String r4 = "additional-certificate"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0072
            int[] r4 = com.android.internal.R.styleable.AndroidManifestAdditionalCertificate
            android.content.res.TypedArray r4 = r10.obtainAttributes(r11, r4)
            r5 = 0
            java.lang.String r6 = r4.getNonResourceString(r5)
            r4.recycle()
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 == 0) goto L_0x005c
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Bad additional-certificate declaration with empty certDigest:"
            r7.append(r8)
            r7.append(r6)
            java.lang.String r7 = r7.toString()
            r12[r5] = r7
            r5 = -108(0xffffffffffffff94, float:NaN)
            r9.mParseError = r5
            com.android.internal.util.XmlUtils.skipCurrentTag(r11)
            r4.recycle()
            r5 = 0
            return r5
        L_0x005c:
            java.lang.String r5 = ":"
            java.lang.String r7 = ""
            java.lang.String r5 = r6.replace(r5, r7)
            java.lang.String r5 = r5.toLowerCase()
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            java.lang.Object[] r6 = com.android.internal.util.ArrayUtils.appendElement(r6, r0, r5)
            r0 = r6
            java.lang.String[] r0 = (java.lang.String[]) r0
            goto L_0x0075
        L_0x0072:
            com.android.internal.util.XmlUtils.skipCurrentTag(r11)
        L_0x0075:
            goto L_0x0006
        L_0x0076:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseAdditionalCertificates(android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):java.lang.String[]");
    }

    private boolean parseUsesPermission(Package pkg, Resources res, XmlResourceParser parser) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesPermission);
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
        if (pkg.requestedPermissions.indexOf(name) == -1) {
            pkg.requestedPermissions.add(name.intern());
        } else {
            Slog.w(TAG, "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + name + " in package: " + pkg.packageName + " at: " + parser.getPositionDescription());
        }
        return true;
    }

    private static String buildClassName(String pkg, CharSequence clsSeq, String[] outError) {
        if (clsSeq == null || clsSeq.length() <= 0) {
            outError[0] = "Empty class name in package " + pkg;
            return null;
        }
        String cls = clsSeq.toString();
        if (cls.charAt(0) == '.') {
            return pkg + cls;
        } else if (cls.indexOf(46) >= 0) {
            return cls;
        } else {
            return pkg + '.' + cls;
        }
    }

    private static String buildCompoundName(String pkg, CharSequence procSeq, String type, String[] outError) {
        String proc = procSeq.toString();
        char c = proc.charAt(0);
        if (pkg == null || c != ':') {
            String nameError = validateName(proc, true, false);
            if (nameError == null || "system".equals(proc)) {
                return proc;
            }
            outError[0] = "Invalid " + type + " name " + proc + " in package " + pkg + PluralRules.KEYWORD_RULE_SEPARATOR + nameError;
            return null;
        } else if (proc.length() < 2) {
            outError[0] = "Bad " + type + " name " + proc + " in package " + pkg + ": must be at least two characters";
            return null;
        } else {
            String nameError2 = validateName(proc.substring(1), false, false);
            if (nameError2 != null) {
                outError[0] = "Invalid " + type + " name " + proc + " in package " + pkg + PluralRules.KEYWORD_RULE_SEPARATOR + nameError2;
                return null;
            }
            return pkg + proc;
        }
    }

    /* access modifiers changed from: private */
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

    private boolean parseKeySets(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        Set<String> publicKeyNames;
        int currentKeySetDepth;
        int outerDepth;
        Package packageR = owner;
        Resources resources = res;
        XmlResourceParser xmlResourceParser = parser;
        int outerDepth2 = parser.getDepth();
        int currentKeySetDepth2 = -1;
        String currentKeySet = null;
        ArrayMap<String, PublicKey> publicKeys = new ArrayMap<>();
        ArraySet<String> upgradeKeySets = new ArraySet<>();
        ArrayMap<String, ArraySet<String>> definedKeySets = new ArrayMap<>();
        ArraySet<String> improperKeySets = new ArraySet<>();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next != 1) {
                if (type == 3 && parser.getDepth() <= outerDepth2) {
                    int i = outerDepth2;
                    int i2 = currentKeySetDepth2;
                    int i3 = type;
                    break;
                }
                if (type != 3) {
                    String tagName = parser.getName();
                    if (!tagName.equals("key-set")) {
                        outerDepth = outerDepth2;
                        if (!tagName.equals("public-key")) {
                            currentKeySetDepth = currentKeySetDepth2;
                            int i4 = type;
                            if (tagName.equals("upgrade-key-set")) {
                                TypedArray sa = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUpgradeKeySet);
                                upgradeKeySets.add(sa.getNonResourceString(0));
                                sa.recycle();
                                XmlUtils.skipCurrentTag(parser);
                            } else {
                                Slog.w(TAG, "Unknown element under <key-sets>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                XmlUtils.skipCurrentTag(parser);
                            }
                        } else if (currentKeySet == null) {
                            outError[0] = "Improperly nested 'key-set' tag at " + parser.getPositionDescription();
                            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return false;
                        } else {
                            TypedArray sa2 = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPublicKey);
                            String publicKeyName = sa2.getNonResourceString(0);
                            String encodedKey = sa2.getNonResourceString(1);
                            if (encodedKey == null && publicKeys.get(publicKeyName) == null) {
                                int i5 = currentKeySetDepth2;
                                StringBuilder sb = new StringBuilder();
                                int i6 = type;
                                sb.append("'public-key' ");
                                sb.append(publicKeyName);
                                sb.append(" must define a public-key value on first use at ");
                                sb.append(parser.getPositionDescription());
                                outError[0] = sb.toString();
                                this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                sa2.recycle();
                                return false;
                            }
                            currentKeySetDepth = currentKeySetDepth2;
                            int i7 = type;
                            if (encodedKey != null) {
                                PublicKey currentKey = parsePublicKey(encodedKey);
                                if (currentKey == null) {
                                    String str = encodedKey;
                                    Slog.w(TAG, "No recognized valid key in 'public-key' tag at " + parser.getPositionDescription() + " key-set " + currentKeySet + " will not be added to the package's defined key-sets.");
                                    sa2.recycle();
                                    improperKeySets.add(currentKeySet);
                                    XmlUtils.skipCurrentTag(parser);
                                } else {
                                    if (publicKeys.get(publicKeyName) == null || publicKeys.get(publicKeyName).equals(currentKey)) {
                                        publicKeys.put(publicKeyName, currentKey);
                                    } else {
                                        outError[0] = "Value of 'public-key' " + publicKeyName + " conflicts with previously defined value at " + parser.getPositionDescription();
                                        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                        sa2.recycle();
                                        return false;
                                    }
                                }
                            }
                            definedKeySets.get(currentKeySet).add(publicKeyName);
                            sa2.recycle();
                            XmlUtils.skipCurrentTag(parser);
                        }
                        currentKeySetDepth2 = currentKeySetDepth;
                    } else if (currentKeySet != null) {
                        outError[0] = "Improperly nested 'key-set' tag at " + parser.getPositionDescription();
                        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        return false;
                    } else {
                        TypedArray sa3 = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestKeySet);
                        String keysetName = sa3.getNonResourceString(0);
                        outerDepth = outerDepth2;
                        definedKeySets.put(keysetName, new ArraySet());
                        currentKeySetDepth2 = parser.getDepth();
                        sa3.recycle();
                        currentKeySet = keysetName;
                        int i8 = type;
                    }
                    outerDepth2 = outerDepth;
                    Package packageR2 = owner;
                } else if (parser.getDepth() == currentKeySetDepth2) {
                    currentKeySet = null;
                    currentKeySetDepth2 = -1;
                } else {
                    outerDepth = outerDepth2;
                    currentKeySetDepth = currentKeySetDepth2;
                }
                outerDepth2 = outerDepth;
                currentKeySetDepth2 = currentKeySetDepth;
                Package packageR3 = owner;
            } else {
                int i9 = currentKeySetDepth2;
                int i10 = type;
                break;
            }
        }
        Set<String> publicKeyNames2 = publicKeys.keySet();
        if (publicKeyNames2.removeAll(definedKeySets.keySet())) {
            outError[0] = "Package" + owner.packageName + " AndroidManifext.xml 'key-set' and 'public-key' names must be distinct.";
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        Package packageR4 = owner;
        packageR4.mKeySetMapping = new ArrayMap<>();
        for (Map.Entry<String, ArraySet<String>> e : definedKeySets.entrySet()) {
            String keySetName = e.getKey();
            if (e.getValue().size() == 0) {
                StringBuilder sb2 = new StringBuilder();
                publicKeyNames = publicKeyNames2;
                sb2.append("Package");
                sb2.append(packageR4.packageName);
                sb2.append(" AndroidManifext.xml 'key-set' ");
                sb2.append(keySetName);
                sb2.append(" has no valid associated 'public-key'. Not including in package's defined key-sets.");
                Slog.w(TAG, sb2.toString());
            } else {
                publicKeyNames = publicKeyNames2;
                if (improperKeySets.contains(keySetName)) {
                    Slog.w(TAG, "Package" + packageR4.packageName + " AndroidManifext.xml 'key-set' " + keySetName + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
                } else {
                    packageR4.mKeySetMapping.put(keySetName, new ArraySet());
                    for (Iterator it = e.getValue().iterator(); it.hasNext(); it = it) {
                        packageR4.mKeySetMapping.get(keySetName).add(publicKeys.get((String) it.next()));
                    }
                }
            }
            publicKeyNames2 = publicKeyNames;
        }
        if (packageR4.mKeySetMapping.keySet().containsAll(upgradeKeySets)) {
            packageR4.mUpgradeKeySets = upgradeKeySets;
            return true;
        }
        outError[0] = "Package" + packageR4.packageName + " AndroidManifext.xml does not define all 'upgrade-key-set's .";
        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        return false;
    }

    private boolean parsePermissionGroup(Package owner, int flags, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        Package packageR = owner;
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestPermissionGroup);
        int requestDetailResourceId = sa.getResourceId(12, 0);
        int backgroundRequestResourceId = sa.getResourceId(9, 0);
        int backgroundRequestDetailResourceId = sa.getResourceId(10, 0);
        PermissionGroup perm = new PermissionGroup(packageR, requestDetailResourceId, backgroundRequestResourceId, backgroundRequestDetailResourceId);
        PermissionGroup perm2 = perm;
        int i = backgroundRequestDetailResourceId;
        int i2 = backgroundRequestResourceId;
        if (!parsePackageItemInfo(owner, perm.info, outError, "<permission-group>", sa, true, 2, 0, 1, 8, 5, 7)) {
            sa.recycle();
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        PermissionGroup perm3 = perm2;
        perm3.info.descriptionRes = sa.getResourceId(4, 0);
        perm3.info.requestRes = sa.getResourceId(11, 0);
        perm3.info.flags = sa.getInt(6, 0);
        perm3.info.priority = sa.getInt(3, 0);
        sa.recycle();
        int i3 = requestDetailResourceId;
        TypedArray typedArray = sa;
        Package packageR2 = packageR;
        if (!parseAllMetaData(res, parser, "<permission-group>", perm3, outError)) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        packageR2.permissionGroups.add(perm3);
        return true;
    }

    private boolean parsePermission(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        Package packageR = owner;
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestPermission);
        String backgroundPermission = null;
        if (sa.hasValue(10)) {
            if ("android".equals(packageR.packageName)) {
                backgroundPermission = sa.getNonResourceString(10);
            } else {
                Slog.w(TAG, packageR.packageName + " defines a background permission. Only the 'android' package can do that.");
            }
        }
        String backgroundPermission2 = backgroundPermission;
        Permission perm = new Permission(packageR, backgroundPermission2);
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
        } else if (!((perm.info.flags & 4) == 0 || (perm.info.flags & 8) == 0)) {
            throw new IllegalStateException("Permission cannot be both soft and hard restricted: " + perm.info.name);
        }
        sa.recycle();
        if (perm.info.protectionLevel == -1) {
            outError[0] = "<permission> does not specify protectionLevel";
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        perm.info.protectionLevel = PermissionInfo.fixProtectionLevel(perm.info.protectionLevel);
        if (perm.info.getProtectionFlags() == 0 || (perm.info.protectionLevel & 4096) != 0 || (perm.info.protectionLevel & 8192) != 0 || (perm.info.protectionLevel & 15) == 2) {
            Permission perm2 = perm;
            String str = backgroundPermission2;
            TypedArray typedArray = sa;
            Package packageR2 = packageR;
            if (!parseAllMetaData(res, parser, "<permission>", perm2, outError)) {
                this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                return false;
            }
            packageR2.permissions.add(perm2);
            return true;
        }
        outError[0] = "<permission>  protectionLevel specifies a non-instant flag but is not based on signature type";
        this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        return false;
    }

    private boolean parsePermissionTree(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        Package packageR = owner;
        Permission perm = new Permission(packageR, (String) null);
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestPermissionTree);
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
        Permission perm2 = perm;
        Package packageR2 = packageR;
        if (!parseAllMetaData(res, parser, "<permission-tree>", perm, outError)) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        packageR2.permissions.add(perm2);
        return true;
    }

    private Instrumentation parseInstrumentation(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestInstrumentation);
        if (this.mParseInstrumentationArgs == null) {
            this.mParseInstrumentationArgs = new ParsePackageItemArgs(owner, outError, 2, 0, 1, 8, 6, 7);
            this.mParseInstrumentationArgs.tag = "<instrumentation>";
        }
        this.mParseInstrumentationArgs.sa = sa;
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
        }
        TypedArray typedArray = sa;
        if (!parseAllMetaData(res, parser, "<instrumentation>", a, outError)) {
            this.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        }
        owner.instrumentation.add(a);
        return a;
    }

    /* JADX WARNING: type inference failed for: r1v63, types: [boolean] */
    /* JADX WARNING: type inference failed for: r1v97 */
    /* JADX WARNING: type inference failed for: r1v100 */
    /* JADX WARNING: Code restructure failed: missing block: B:320:0x0786, code lost:
        if (android.text.TextUtils.isEmpty(r14.staticSharedLibName) == false) goto L_0x0796;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:321:0x0788, code lost:
        r14.activities.add(r0.generateAppDetailsHiddenActivity(r14, r39, r9, r14.baseHardwareAccelerated));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:322:0x0796, code lost:
        r2 = r39;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:323:0x0798, code lost:
        if (r23 == false) goto L_0x07a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:324:0x079a, code lost:
        java.util.Collections.sort(r14.activities, android.content.pm.$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4.INSTANCE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:325:0x07a1, code lost:
        if (r24 == false) goto L_0x07aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:326:0x07a3, code lost:
        java.util.Collections.sort(r14.receivers, android.content.pm.$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw.INSTANCE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:327:0x07aa, code lost:
        if (r26 == false) goto L_0x07b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:328:0x07ac, code lost:
        java.util.Collections.sort(r14.services, android.content.pm.$$Lambda$PackageParser$M9fHqS_eEp1oYkuKJhRHOGUxf8.INSTANCE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:329:0x07b3, code lost:
        setMaxAspectRatio(r36);
        setMinAspectRatio(r36);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:330:0x07bd, code lost:
        if (hasDomainURLs(r36) == false) goto L_0x07c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:331:0x07bf, code lost:
        r14.applicationInfo.privateFlags |= 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:332:0x07c8, code lost:
        r14.applicationInfo.privateFlags &= -17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:354:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:355:?, code lost:
        return true;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean parseBaseApplication(android.content.pm.PackageParser.Package r36, android.content.res.Resources r37, android.content.res.XmlResourceParser r38, int r39, java.lang.String[] r40) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r35 = this;
            r15 = r35
            r14 = r36
            r13 = r37
            r12 = r38
            r11 = r40
            android.content.pm.ApplicationInfo r10 = r14.applicationInfo
            android.content.pm.ApplicationInfo r0 = r14.applicationInfo
            java.lang.String r9 = r0.packageName
            int[] r0 = com.android.internal.R.styleable.AndroidManifestApplication
            android.content.res.TypedArray r8 = r13.obtainAttributes(r12, r0)
            r7 = 2
            r6 = 0
            int r0 = r8.getResourceId(r7, r6)
            r10.iconRes = r0
            r0 = 42
            int r0 = r8.getResourceId(r0, r6)
            r10.roundIconRes = r0
            java.lang.String r3 = "<application>"
            r5 = 0
            r16 = 3
            r17 = 1
            r18 = 2
            r19 = 42
            r20 = 22
            r21 = 30
            r0 = r36
            r1 = r10
            r2 = r40
            r4 = r8
            r6 = r16
            r7 = r17
            r24 = r8
            r8 = r18
            r25 = r9
            r9 = r19
            r26 = r10
            r10 = r20
            r13 = r11
            r11 = r21
            boolean r0 = parsePackageItemInfo(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r11 = -108(0xffffffffffffff94, float:NaN)
            if (r0 != 0) goto L_0x005d
            r24.recycle()
            r15.mParseError = r11
            r10 = 0
            return r10
        L_0x005d:
            r10 = 0
            r9 = r26
            java.lang.String r0 = r9.name
            if (r0 == 0) goto L_0x0068
            java.lang.String r0 = r9.name
            r9.className = r0
        L_0x0068:
            r8 = 4
            r0 = 1024(0x400, float:1.435E-42)
            r7 = r24
            java.lang.String r6 = r7.getNonConfigurationString(r8, r0)
            if (r6 == 0) goto L_0x007c
            r5 = r25
            java.lang.String r1 = buildClassName(r5, r6, r13)
            r9.manageSpaceActivityName = r1
            goto L_0x007e
        L_0x007c:
            r5 = r25
        L_0x007e:
            r1 = 17
            r4 = 1
            boolean r16 = r7.getBoolean(r1, r4)
            r1 = 67108864(0x4000000, float:1.5046328E-36)
            r3 = 16
            r11 = 32
            if (r16 == 0) goto L_0x00f2
            int r2 = r9.flags
            r17 = 32768(0x8000, float:4.5918E-41)
            r2 = r2 | r17
            r9.flags = r2
            java.lang.String r2 = r7.getNonConfigurationString(r3, r0)
            if (r2 == 0) goto L_0x00db
            java.lang.String r3 = buildClassName(r5, r2, r13)
            r9.backupAgentName = r3
            r3 = 18
            boolean r3 = r7.getBoolean(r3, r4)
            if (r3 == 0) goto L_0x00b2
            int r3 = r9.flags
            r17 = 65536(0x10000, float:9.18355E-41)
            r3 = r3 | r17
            r9.flags = r3
        L_0x00b2:
            r3 = 21
            boolean r3 = r7.getBoolean(r3, r10)
            if (r3 == 0) goto L_0x00c2
            int r3 = r9.flags
            r17 = 131072(0x20000, float:1.83671E-40)
            r3 = r3 | r17
            r9.flags = r3
        L_0x00c2:
            boolean r3 = r7.getBoolean(r11, r10)
            if (r3 == 0) goto L_0x00cd
            int r3 = r9.flags
            r3 = r3 | r1
            r9.flags = r3
        L_0x00cd:
            r3 = 40
            boolean r3 = r7.getBoolean(r3, r10)
            if (r3 == 0) goto L_0x00db
            int r3 = r9.privateFlags
            r3 = r3 | 8192(0x2000, float:1.14794E-41)
            r9.privateFlags = r3
        L_0x00db:
            r3 = 35
            android.util.TypedValue r3 = r7.peekValue(r3)
            if (r3 == 0) goto L_0x00f2
            int r1 = r3.resourceId
            r9.fullBackupContent = r1
            if (r1 != 0) goto L_0x00f2
            int r1 = r3.data
            if (r1 != 0) goto L_0x00ef
            r1 = -1
            goto L_0x00f0
        L_0x00ef:
            r1 = r10
        L_0x00f0:
            r9.fullBackupContent = r1
        L_0x00f2:
            int r1 = r7.getResourceId(r10, r10)
            r9.theme = r1
            r1 = 13
            int r1 = r7.getResourceId(r1, r10)
            r9.descriptionRes = r1
            r1 = 8
            boolean r2 = r7.getBoolean(r1, r10)
            if (r2 == 0) goto L_0x011d
            r2 = 45
            java.lang.String r2 = r7.getNonResourceString(r2)
            if (r2 == 0) goto L_0x0118
            android.content.pm.PackageParser$Callback r3 = r15.mCallback
            boolean r3 = r3.hasFeature(r2)
            if (r3 == 0) goto L_0x011d
        L_0x0118:
            int r3 = r9.flags
            r3 = r3 | r1
            r9.flags = r3
        L_0x011d:
            r2 = 27
            boolean r2 = r7.getBoolean(r2, r10)
            if (r2 == 0) goto L_0x0127
            r14.mRequiredForAllUsers = r4
        L_0x0127:
            r2 = 28
            java.lang.String r3 = r7.getString(r2)
            if (r3 == 0) goto L_0x0137
            int r17 = r3.length()
            if (r17 <= 0) goto L_0x0137
            r14.mRestrictedAccountType = r3
        L_0x0137:
            r1 = 29
            java.lang.String r0 = r7.getString(r1)
            if (r0 == 0) goto L_0x0147
            int r17 = r0.length()
            if (r17 <= 0) goto L_0x0147
            r14.mRequiredAccountType = r0
        L_0x0147:
            r1 = 10
            boolean r1 = r7.getBoolean(r1, r10)
            r17 = 8388608(0x800000, float:1.17549435E-38)
            if (r1 == 0) goto L_0x015e
            int r1 = r9.flags
            r2 = 2
            r1 = r1 | r2
            r9.flags = r1
            int r1 = r9.privateFlags
            r1 = r1 | r17
            r9.privateFlags = r1
            goto L_0x015f
        L_0x015e:
            r2 = 2
        L_0x015f:
            r1 = 20
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x016d
            int r1 = r9.flags
            r1 = r1 | 16384(0x4000, float:2.2959E-41)
            r9.flags = r1
        L_0x016d:
            r1 = 23
            android.content.pm.ApplicationInfo r2 = r14.applicationInfo
            int r2 = r2.targetSdkVersion
            r11 = 14
            if (r2 < r11) goto L_0x0179
            r2 = r4
            goto L_0x017a
        L_0x0179:
            r2 = r10
        L_0x017a:
            boolean r1 = r7.getBoolean(r1, r2)
            r14.baseHardwareAccelerated = r1
            boolean r1 = r14.baseHardwareAccelerated
            if (r1 == 0) goto L_0x018b
            int r1 = r9.flags
            r2 = 536870912(0x20000000, float:1.0842022E-19)
            r1 = r1 | r2
            r9.flags = r1
        L_0x018b:
            r1 = 7
            boolean r1 = r7.getBoolean(r1, r4)
            if (r1 == 0) goto L_0x0197
            int r1 = r9.flags
            r1 = r1 | r8
            r9.flags = r1
        L_0x0197:
            r1 = 14
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x01a6
            int r1 = r9.flags
            r2 = 32
            r1 = r1 | r2
            r9.flags = r1
        L_0x01a6:
            r1 = 5
            boolean r1 = r7.getBoolean(r1, r4)
            if (r1 == 0) goto L_0x01b3
            int r1 = r9.flags
            r1 = r1 | 64
            r9.flags = r1
        L_0x01b3:
            android.content.pm.PackageParser$Package r1 = r14.parentPackage
            if (r1 != 0) goto L_0x01c5
            r1 = 15
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x01c5
            int r1 = r9.flags
            r1 = r1 | 256(0x100, float:3.59E-43)
            r9.flags = r1
        L_0x01c5:
            r1 = 24
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x01d4
            int r1 = r9.flags
            r2 = 1048576(0x100000, float:1.469368E-39)
            r1 = r1 | r2
            r9.flags = r1
        L_0x01d4:
            r1 = 36
            android.content.pm.ApplicationInfo r2 = r14.applicationInfo
            int r2 = r2.targetSdkVersion
            r11 = 28
            if (r2 >= r11) goto L_0x01e0
            r2 = r4
            goto L_0x01e1
        L_0x01e0:
            r2 = r10
        L_0x01e1:
            boolean r1 = r7.getBoolean(r1, r2)
            if (r1 == 0) goto L_0x01ee
            int r1 = r9.flags
            r2 = 134217728(0x8000000, float:3.85186E-34)
            r1 = r1 | r2
            r9.flags = r1
        L_0x01ee:
            r1 = 26
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x01fd
            int r1 = r9.flags
            r2 = 4194304(0x400000, float:5.877472E-39)
            r1 = r1 | r2
            r9.flags = r1
        L_0x01fd:
            r1 = 33
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x020c
            int r1 = r9.flags
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 | r2
            r9.flags = r1
        L_0x020c:
            r1 = 34
            boolean r1 = r7.getBoolean(r1, r4)
            if (r1 == 0) goto L_0x021b
            int r1 = r9.flags
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            r1 = r1 | r2
            r9.flags = r1
        L_0x021b:
            r1 = 53
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x022a
            int r1 = r9.privateFlags
            r2 = 33554432(0x2000000, float:9.403955E-38)
            r1 = r1 | r2
            r9.privateFlags = r1
        L_0x022a:
            r1 = 38
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x0239
            int r1 = r9.privateFlags
            r2 = 32
            r1 = r1 | r2
            r9.privateFlags = r1
        L_0x0239:
            r1 = 39
            boolean r1 = r7.getBoolean(r1, r10)
            if (r1 == 0) goto L_0x0247
            int r1 = r9.privateFlags
            r1 = r1 | 64
            r9.privateFlags = r1
        L_0x0247:
            r1 = 37
            boolean r1 = r7.hasValueOrEmpty(r1)
            if (r1 == 0) goto L_0x0266
            r1 = 37
            boolean r1 = r7.getBoolean(r1, r4)
            if (r1 == 0) goto L_0x025f
            int r1 = r9.privateFlags
            r2 = 1024(0x400, float:1.435E-42)
            r1 = r1 | r2
            r9.privateFlags = r1
            goto L_0x0274
        L_0x025f:
            int r1 = r9.privateFlags
            r1 = r1 | 2048(0x800, float:2.87E-42)
            r9.privateFlags = r1
            goto L_0x0274
        L_0x0266:
            android.content.pm.ApplicationInfo r1 = r14.applicationInfo
            int r1 = r1.targetSdkVersion
            r2 = 24
            if (r1 < r2) goto L_0x0274
            int r1 = r9.privateFlags
            r1 = r1 | 4096(0x1000, float:5.74E-42)
            r9.privateFlags = r1
        L_0x0274:
            r1 = 54
            boolean r1 = r7.getBoolean(r1, r4)
            if (r1 == 0) goto L_0x0283
            int r1 = r9.privateFlags
            r2 = 67108864(0x4000000, float:1.5046328E-36)
            r1 = r1 | r2
            r9.privateFlags = r1
        L_0x0283:
            r1 = 55
            android.content.pm.ApplicationInfo r2 = r14.applicationInfo
            int r2 = r2.targetSdkVersion
            r11 = 29
            if (r2 < r11) goto L_0x028f
            r2 = r4
            goto L_0x0290
        L_0x028f:
            r2 = r10
        L_0x0290:
            boolean r1 = r7.getBoolean(r1, r2)
            if (r1 == 0) goto L_0x029d
            int r1 = r9.privateFlags
            r2 = 134217728(0x8000000, float:3.85186E-34)
            r1 = r1 | r2
            r9.privateFlags = r1
        L_0x029d:
            r1 = 56
            android.content.pm.ApplicationInfo r2 = r14.applicationInfo
            int r2 = r2.targetSdkVersion
            r11 = 29
            if (r2 >= r11) goto L_0x02a9
            r2 = r4
            goto L_0x02aa
        L_0x02a9:
            r2 = r10
        L_0x02aa:
            boolean r1 = r7.getBoolean(r1, r2)
            if (r1 == 0) goto L_0x02b7
            int r1 = r9.privateFlags
            r2 = 536870912(0x20000000, float:1.0842022E-19)
            r1 = r1 | r2
            r9.privateFlags = r1
        L_0x02b7:
            r1 = 44
            r2 = 0
            float r1 = r7.getFloat(r1, r2)
            r9.maxAspectRatio = r1
            r1 = 51
            float r1 = r7.getFloat(r1, r2)
            r9.minAspectRatio = r1
            r1 = 41
            int r1 = r7.getResourceId(r1, r10)
            r9.networkSecurityConfigRes = r1
            r1 = 43
            r2 = -1
            int r1 = r7.getInt(r1, r2)
            r9.category = r1
            r1 = 6
            java.lang.String r1 = r7.getNonConfigurationString(r1, r10)
            if (r1 == 0) goto L_0x02eb
            int r11 = r1.length()
            if (r11 <= 0) goto L_0x02eb
            java.lang.String r11 = r1.intern()
            goto L_0x02ec
        L_0x02eb:
            r11 = 0
        L_0x02ec:
            r9.permission = r11
            android.content.pm.ApplicationInfo r11 = r14.applicationInfo
            int r11 = r11.targetSdkVersion
            r2 = 8
            if (r11 < r2) goto L_0x0300
            r2 = 12
            r11 = 1024(0x400, float:1.435E-42)
            java.lang.String r1 = r7.getNonConfigurationString(r2, r11)
        L_0x02fe:
            r11 = r1
            goto L_0x0307
        L_0x0300:
            r2 = 12
            java.lang.String r1 = r7.getNonResourceString(r2)
            goto L_0x02fe
        L_0x0307:
            java.lang.String r1 = r9.packageName
            java.lang.String r2 = r9.packageName
            java.lang.String r1 = buildTaskAffinityName(r1, r2, r11, r13)
            r9.taskAffinity = r1
            r1 = 48
            java.lang.String r1 = r7.getNonResourceString(r1)
            if (r1 == 0) goto L_0x0321
            java.lang.String r2 = r9.packageName
            java.lang.String r2 = buildClassName(r2, r1, r13)
            r9.appComponentFactory = r2
        L_0x0321:
            r2 = 49
            boolean r2 = r7.getBoolean(r2, r10)
            if (r2 == 0) goto L_0x0331
            int r2 = r9.privateFlags
            r18 = 4194304(0x400000, float:5.877472E-39)
            r2 = r2 | r18
            r9.privateFlags = r2
        L_0x0331:
            r2 = 50
            boolean r2 = r7.getBoolean(r2, r10)
            if (r2 == 0) goto L_0x0341
            int r2 = r9.privateFlags
            r18 = 16777216(0x1000000, float:2.3509887E-38)
            r2 = r2 | r18
            r9.privateFlags = r2
        L_0x0341:
            r2 = r13[r10]
            if (r2 != 0) goto L_0x03ba
            android.content.pm.ApplicationInfo r2 = r14.applicationInfo
            int r2 = r2.targetSdkVersion
            r4 = 8
            if (r2 < r4) goto L_0x0356
            r2 = 11
            r4 = 1024(0x400, float:1.435E-42)
            java.lang.String r2 = r7.getNonConfigurationString(r2, r4)
            goto L_0x035c
        L_0x0356:
            r2 = 11
            java.lang.String r2 = r7.getNonResourceString(r2)
        L_0x035c:
            r4 = 2
            java.lang.String r4 = r9.packageName
            r18 = 0
            java.lang.String[] r8 = r15.mSeparateProcesses
            r19 = r0
            r0 = r4
            r20 = r1
            r1 = r18
            r18 = r3
            r21 = 16
            r3 = r39
            r10 = 1
            r4 = r8
            r8 = r5
            r5 = r40
            java.lang.String r0 = buildProcessName(r0, r1, r2, r3, r4, r5)
            r9.processName = r0
            r0 = 9
            boolean r0 = r7.getBoolean(r0, r10)
            r9.enabled = r0
            r0 = 31
            r1 = 0
            boolean r0 = r7.getBoolean(r0, r1)
            if (r0 == 0) goto L_0x0393
            int r0 = r9.flags
            r3 = 33554432(0x2000000, float:9.403955E-38)
            r0 = r0 | r3
            r9.flags = r0
        L_0x0393:
            r0 = 47
            boolean r0 = r7.getBoolean(r0, r1)
            if (r0 == 0) goto L_0x03b7
            int r0 = r9.privateFlags
            r5 = 2
            r0 = r0 | r5
            r9.privateFlags = r0
            java.lang.String r0 = r9.processName
            if (r0 == 0) goto L_0x03b5
            java.lang.String r0 = r9.processName
            java.lang.String r1 = r9.packageName
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03b5
            java.lang.String r0 = "cantSaveState applications can not use custom processes"
            r1 = 0
            r13[r1] = r0
            goto L_0x03c6
        L_0x03b5:
            r1 = 0
            goto L_0x03c6
        L_0x03b7:
            r1 = 0
            r5 = 2
            goto L_0x03c6
        L_0x03ba:
            r19 = r0
            r20 = r1
            r18 = r3
            r8 = r5
            r1 = r10
            r5 = 2
            r21 = 16
            r10 = r4
        L_0x03c6:
            r0 = 25
            int r0 = r7.getInt(r0, r1)
            r9.uiOptions = r0
            r0 = 46
            java.lang.String r0 = r7.getString(r0)
            r9.classLoaderName = r0
            java.lang.String r0 = r9.classLoaderName
            if (r0 == 0) goto L_0x03f9
            java.lang.String r0 = r9.classLoaderName
            boolean r0 = com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(r0)
            if (r0 != 0) goto L_0x03f9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Invalid class loader name: "
            r0.append(r1)
            java.lang.String r1 = r9.classLoaderName
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = 0
            r13[r1] = r0
            goto L_0x03fa
        L_0x03f9:
            r1 = 0
        L_0x03fa:
            r0 = 52
            java.lang.String r0 = r7.getString(r0)
            r9.zygotePreloadName = r0
            r7.recycle()
            r0 = r13[r1]
            if (r0 == 0) goto L_0x040e
            r0 = -108(0xffffffffffffff94, float:NaN)
            r15.mParseError = r0
            return r1
        L_0x040e:
            int r4 = r38.getDepth()
            android.content.pm.PackageParser$CachedComponentArgs r0 = new android.content.pm.PackageParser$CachedComponentArgs
            r1 = 0
            r0.<init>()
            r22 = r6
            r6 = r0
            r0 = 0
            r1 = 0
            r23 = r0
            r24 = r1
            r25 = r7
            r0 = 0
        L_0x0424:
            r26 = r0
            int r0 = r38.next()
            r7 = r0
            if (r0 == r10) goto L_0x0773
            r0 = 3
            if (r7 != r0) goto L_0x0446
            int r0 = r38.getDepth()
            if (r0 <= r4) goto L_0x0437
            goto L_0x0446
        L_0x0437:
            r31 = r4
            r32 = r7
            r5 = r9
            r27 = r11
            r9 = r13
            r0 = r15
            r7 = r37
            r11 = r8
            r8 = r12
            goto L_0x0780
        L_0x0446:
            r0 = 3
            if (r7 == r0) goto L_0x0754
            r3 = 4
            if (r7 != r3) goto L_0x045f
            r28 = r3
            r31 = r4
            r5 = r9
            r27 = r11
            r9 = r13
            r0 = r15
            r1 = -108(0xffffffffffffff94, float:NaN)
            r4 = 0
            r7 = r37
            r11 = r8
            r8 = r12
            goto L_0x0764
        L_0x045f:
            java.lang.String r2 = r38.getName()
            java.lang.String r0 = "activity"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x04ba
            r28 = 0
            boolean r1 = r14.baseHardwareAccelerated
            r0 = r35
            r29 = r1
            r1 = r36
            r33 = r2
            r2 = r37
            r30 = r3
            r3 = r38
            r31 = r4
            r4 = r39
            r5 = r40
            r32 = r7
            r7 = r28
            r34 = r8
            r28 = r30
            r8 = r29
            android.content.pm.PackageParser$Activity r0 = r0.parseActivity(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r0 != 0) goto L_0x0499
            r1 = -108(0xffffffffffffff94, float:NaN)
            r15.mParseError = r1
            r2 = 0
            return r2
        L_0x0499:
            r1 = -108(0xffffffffffffff94, float:NaN)
            r2 = 0
            int r3 = r0.order
            if (r3 == 0) goto L_0x04a2
            r3 = r10
            goto L_0x04a3
        L_0x04a2:
            r3 = r2
        L_0x04a3:
            r3 = r23 | r3
            java.util.ArrayList<android.content.pm.PackageParser$Activity> r4 = r14.activities
            r4.add(r0)
            r4 = r2
            r23 = r3
            r5 = r9
            r27 = r11
            r8 = r12
            r9 = r13
            r0 = r15
            r11 = r34
            r7 = r37
            goto L_0x0722
        L_0x04ba:
            r33 = r2
            r28 = r3
            r31 = r4
            r32 = r7
            r34 = r8
            r1 = -108(0xffffffffffffff94, float:NaN)
            r2 = 0
            java.lang.String r0 = "receiver"
            r3 = r33
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0511
            r0 = 1
            r4 = 0
            r7 = r35
            r8 = r36
            r5 = r9
            r9 = r37
            r1 = r10
            r10 = r38
            r27 = r11
            r1 = -108(0xffffffffffffff94, float:NaN)
            r11 = r39
            r12 = r40
            r13 = r6
            r14 = r0
            r0 = r15
            r15 = r4
            android.content.pm.PackageParser$Activity r4 = r7.parseActivity(r8, r9, r10, r11, r12, r13, r14, r15)
            if (r4 != 0) goto L_0x04f3
            r0.mParseError = r1
            return r2
        L_0x04f3:
            int r7 = r4.order
            if (r7 == 0) goto L_0x04f9
            r7 = 1
            goto L_0x04fa
        L_0x04f9:
            r7 = r2
        L_0x04fa:
            r7 = r24 | r7
            r14 = r36
            java.util.ArrayList<android.content.pm.PackageParser$Activity> r8 = r14.receivers
            r8.add(r4)
            r4 = r2
            r24 = r7
        L_0x0507:
            r11 = r34
            r7 = r37
            r8 = r38
            r9 = r40
            goto L_0x0722
        L_0x0511:
            r5 = r9
            r27 = r11
            r0 = r15
            java.lang.String r4 = "service"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0547
            r7 = r35
            r8 = r36
            r9 = r37
            r10 = r38
            r11 = r39
            r12 = r40
            r13 = r6
            android.content.pm.PackageParser$Service r4 = r7.parseService(r8, r9, r10, r11, r12, r13)
            if (r4 != 0) goto L_0x0534
            r0.mParseError = r1
            return r2
        L_0x0534:
            int r7 = r4.order
            if (r7 == 0) goto L_0x053a
            r7 = 1
            goto L_0x053b
        L_0x053a:
            r7 = r2
        L_0x053b:
            r7 = r26 | r7
            java.util.ArrayList<android.content.pm.PackageParser$Service> r8 = r14.services
            r8.add(r4)
            r4 = r2
            r26 = r7
            goto L_0x0507
        L_0x0547:
            java.lang.String r4 = "provider"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0576
            r7 = r35
            r8 = r36
            r9 = r37
            r10 = r38
            r11 = r39
            r12 = r40
            r13 = r6
            android.content.pm.PackageParser$Provider r4 = r7.parseProvider(r8, r9, r10, r11, r12, r13)
            if (r4 != 0) goto L_0x0566
            r0.mParseError = r1
            return r2
        L_0x0566:
            java.util.ArrayList<android.content.pm.PackageParser$Provider> r7 = r14.providers
            r7.add(r4)
            r11 = r34
            r7 = r37
            r8 = r38
            r9 = r40
            goto L_0x06ff
        L_0x0576:
            java.lang.String r4 = "activity-alias"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x05a8
            r7 = r35
            r8 = r36
            r9 = r37
            r10 = r38
            r11 = r39
            r12 = r40
            r13 = r6
            android.content.pm.PackageParser$Activity r4 = r7.parseActivityAlias(r8, r9, r10, r11, r12, r13)
            if (r4 != 0) goto L_0x0594
            r0.mParseError = r1
            return r2
        L_0x0594:
            int r7 = r4.order
            if (r7 == 0) goto L_0x059a
            r7 = 1
            goto L_0x059b
        L_0x059a:
            r7 = r2
        L_0x059b:
            r7 = r23 | r7
            java.util.ArrayList<android.content.pm.PackageParser$Activity> r8 = r14.activities
            r8.add(r4)
            r4 = r2
            r23 = r7
            goto L_0x0507
        L_0x05a8:
            java.lang.String r4 = r38.getName()
            java.lang.String r7 = "meta-data"
            boolean r4 = r4.equals(r7)
            if (r4 == 0) goto L_0x05cb
            android.os.Bundle r4 = r14.mAppMetaData
            r7 = r37
            r8 = r38
            r9 = r40
            android.os.Bundle r4 = r0.parseMetaData(r7, r8, r4, r9)
            r14.mAppMetaData = r4
            if (r4 != 0) goto L_0x05c7
            r0.mParseError = r1
            return r2
        L_0x05c7:
            r11 = r34
            goto L_0x06ff
        L_0x05cb:
            r7 = r37
            r8 = r38
            r9 = r40
            java.lang.String r4 = "static-library"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0673
            int[] r4 = com.android.internal.R.styleable.AndroidManifestStaticLibrary
            android.content.res.TypedArray r4 = r7.obtainAttributes(r8, r4)
            java.lang.String r10 = r4.getNonResourceString(r2)
            r11 = 1
            r12 = -1
            int r13 = r4.getInt(r11, r12)
            r11 = 2
            int r15 = r4.getInt(r11, r2)
            r4.recycle()
            if (r10 == 0) goto L_0x064d
            if (r13 >= 0) goto L_0x05f9
            r11 = r34
            goto L_0x064f
        L_0x05f9:
            java.lang.String r11 = r14.mSharedUserId
            if (r11 == 0) goto L_0x060a
            java.lang.String r1 = "sharedUserId not allowed in static shared library"
            r9[r2] = r1
            r1 = -107(0xffffffffffffff95, float:NaN)
            r0.mParseError = r1
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            return r2
        L_0x060a:
            java.lang.String r11 = r14.staticSharedLibName
            if (r11 == 0) goto L_0x0629
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Multiple static-shared libs for package "
            r11.append(r12)
            r12 = r34
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r9[r2] = r11
            r0.mParseError = r1
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            return r2
        L_0x0629:
            r11 = r34
            java.lang.String r12 = r10.intern()
            r14.staticSharedLibName = r12
            if (r13 < 0) goto L_0x063b
            long r1 = android.content.pm.PackageInfo.composeLongVersionCode(r15, r13)
            r14.staticSharedLibVersion = r1
            goto L_0x063e
        L_0x063b:
            long r1 = (long) r13
            r14.staticSharedLibVersion = r1
        L_0x063e:
            int r1 = r5.privateFlags
            r1 = r1 | 16384(0x4000, float:2.2959E-41)
            r5.privateFlags = r1
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            r25 = r4
            r1 = -108(0xffffffffffffff94, float:NaN)
            goto L_0x06a6
        L_0x064d:
            r11 = r34
        L_0x064f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Bad static-library declaration name: "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r2 = " version: "
            r1.append(r2)
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            r2 = 0
            r9[r2] = r1
            r1 = -108(0xffffffffffffff94, float:NaN)
            r0.mParseError = r1
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            return r2
        L_0x0673:
            r11 = r34
            java.lang.String r4 = "library"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x06a9
            int[] r4 = com.android.internal.R.styleable.AndroidManifestLibrary
            android.content.res.TypedArray r4 = r7.obtainAttributes(r8, r4)
            java.lang.String r10 = r4.getNonResourceString(r2)
            r4.recycle()
            if (r10 == 0) goto L_0x06a0
            java.lang.String r10 = r10.intern()
            java.util.ArrayList<java.lang.String> r2 = r14.libraryNames
            boolean r2 = com.android.internal.util.ArrayUtils.contains(r2, r10)
            if (r2 != 0) goto L_0x06a0
            java.util.ArrayList<java.lang.String> r2 = r14.libraryNames
            java.util.ArrayList r2 = com.android.internal.util.ArrayUtils.add(r2, r10)
            r14.libraryNames = r2
        L_0x06a0:
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
        L_0x06a4:
            r25 = r4
        L_0x06a6:
            r4 = 0
            goto L_0x0722
        L_0x06a9:
            java.lang.String r2 = "uses-static-library"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x06ba
            boolean r2 = r0.parseUsesStaticLibrary(r14, r7, r8, r9)
            if (r2 != 0) goto L_0x06ff
            r2 = 0
            return r2
        L_0x06ba:
            r2 = 0
            java.lang.String r4 = "uses-library"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x06f3
            int[] r4 = com.android.internal.R.styleable.AndroidManifestUsesLibrary
            android.content.res.TypedArray r4 = r7.obtainAttributes(r8, r4)
            java.lang.String r10 = r4.getNonResourceString(r2)
            r2 = 1
            boolean r12 = r4.getBoolean(r2, r2)
            r4.recycle()
            if (r10 == 0) goto L_0x06ef
            java.lang.String r10 = r10.intern()
            if (r12 == 0) goto L_0x06e7
            java.util.ArrayList<java.lang.String> r2 = r14.usesLibraries
            java.util.ArrayList r2 = com.android.internal.util.ArrayUtils.add(r2, r10)
            r14.usesLibraries = r2
            goto L_0x06ef
        L_0x06e7:
            java.util.ArrayList<java.lang.String> r2 = r14.usesOptionalLibraries
            java.util.ArrayList r2 = com.android.internal.util.ArrayUtils.add(r2, r10)
            r14.usesOptionalLibraries = r2
        L_0x06ef:
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            goto L_0x06a4
        L_0x06f3:
            java.lang.String r2 = "uses-package"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0701
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
        L_0x06ff:
            r4 = 0
            goto L_0x0722
        L_0x0701:
            java.lang.String r2 = "profileable"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0723
            int[] r2 = com.android.internal.R.styleable.AndroidManifestProfileable
            android.content.res.TypedArray r2 = r7.obtainAttributes(r8, r2)
            r4 = 0
            boolean r10 = r2.getBoolean(r4, r4)
            if (r10 == 0) goto L_0x071d
            int r10 = r5.privateFlags
            r10 = r10 | r17
            r5.privateFlags = r10
        L_0x071d:
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            r25 = r2
        L_0x0722:
            goto L_0x0764
        L_0x0723:
            r4 = 0
            java.lang.String r2 = "PackageParser"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r12 = "Unknown element under <application>: "
            r10.append(r12)
            r10.append(r3)
            java.lang.String r12 = " at "
            r10.append(r12)
            java.lang.String r12 = r0.mArchiveSourcePath
            r10.append(r12)
            java.lang.String r12 = " "
            r10.append(r12)
            java.lang.String r12 = r38.getPositionDescription()
            r10.append(r12)
            java.lang.String r10 = r10.toString()
            android.util.Slog.w((java.lang.String) r2, (java.lang.String) r10)
            com.android.internal.util.XmlUtils.skipCurrentTag(r38)
            goto L_0x0764
        L_0x0754:
            r31 = r4
            r5 = r9
            r27 = r11
            r9 = r13
            r0 = r15
            r1 = -108(0xffffffffffffff94, float:NaN)
            r4 = 0
            r7 = r37
            r28 = 4
            r11 = r8
            r8 = r12
        L_0x0764:
            r15 = r0
            r12 = r8
            r13 = r9
            r8 = r11
            r0 = r26
            r11 = r27
            r4 = r31
            r10 = 1
            r9 = r5
            r5 = 2
            goto L_0x0424
        L_0x0773:
            r31 = r4
            r32 = r7
            r5 = r9
            r27 = r11
            r9 = r13
            r0 = r15
            r7 = r37
            r11 = r8
            r8 = r12
        L_0x0780:
            java.lang.String r1 = r14.staticSharedLibName
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x0796
            boolean r1 = r14.baseHardwareAccelerated
            r2 = r39
            android.content.pm.PackageParser$Activity r1 = r0.generateAppDetailsHiddenActivity(r14, r2, r9, r1)
            java.util.ArrayList<android.content.pm.PackageParser$Activity> r3 = r14.activities
            r3.add(r1)
            goto L_0x0798
        L_0x0796:
            r2 = r39
        L_0x0798:
            if (r23 == 0) goto L_0x07a1
            java.util.ArrayList<android.content.pm.PackageParser$Activity> r1 = r14.activities
            android.content.pm.-$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4 r3 = android.content.pm.$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4.INSTANCE
            java.util.Collections.sort(r1, r3)
        L_0x07a1:
            if (r24 == 0) goto L_0x07aa
            java.util.ArrayList<android.content.pm.PackageParser$Activity> r1 = r14.receivers
            android.content.pm.-$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw r3 = android.content.pm.$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw.INSTANCE
            java.util.Collections.sort(r1, r3)
        L_0x07aa:
            if (r26 == 0) goto L_0x07b3
            java.util.ArrayList<android.content.pm.PackageParser$Service> r1 = r14.services
            android.content.pm.-$$Lambda$PackageParser$M-9fHqS_eEp1oYkuKJhRHOGUxf8 r3 = android.content.pm.$$Lambda$PackageParser$M9fHqS_eEp1oYkuKJhRHOGUxf8.INSTANCE
            java.util.Collections.sort(r1, r3)
        L_0x07b3:
            r35.setMaxAspectRatio(r36)
            r35.setMinAspectRatio(r36)
            boolean r1 = hasDomainURLs(r36)
            if (r1 == 0) goto L_0x07c8
            android.content.pm.ApplicationInfo r1 = r14.applicationInfo
            int r3 = r1.privateFlags
            r3 = r3 | 16
            r1.privateFlags = r3
            goto L_0x07d0
        L_0x07c8:
            android.content.pm.ApplicationInfo r1 = r14.applicationInfo
            int r3 = r1.privateFlags
            r3 = r3 & -17
            r1.privateFlags = r3
        L_0x07d0:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseBaseApplication(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[]):boolean");
    }

    private static boolean hasDomainURLs(Package pkg) {
        if (pkg == null || pkg.activities == null) {
            return false;
        }
        ArrayList<Activity> activities = pkg.activities;
        int countActivities = activities.size();
        for (int n = 0; n < countActivities; n++) {
            ArrayList<ActivityIntentInfo> filters = activities.get(n).intents;
            if (filters != null) {
                int countFilters = filters.size();
                for (int m = 0; m < countFilters; m++) {
                    ActivityIntentInfo aii = filters.get(m);
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
        boolean z;
        int i;
        String[] strArr;
        Resources resources;
        XmlResourceParser xmlResourceParser;
        Package packageR;
        PackageParser packageParser;
        ComponentInfo parsedComponent;
        PackageParser packageParser2 = this;
        Package packageR2 = owner;
        Resources resources2 = res;
        XmlResourceParser xmlResourceParser2 = parser;
        String[] strArr2 = outError;
        TypedArray sa = resources2.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestApplication);
        int i2 = 1;
        int i3 = 4;
        if (sa.getBoolean(7, true)) {
            int[] iArr = packageR2.splitFlags;
            iArr[splitIndex] = iArr[splitIndex] | 4;
        }
        String classLoaderName2 = sa.getString(46);
        int i4 = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        boolean z2 = false;
        if (classLoaderName2 == null || ClassLoaderFactory.isValidClassLoaderName(classLoaderName2)) {
            packageR2.applicationInfo.splitClassLoaderNames[splitIndex] = classLoaderName2;
            int innerDepth2 = parser.getDepth();
            TypedArray typedArray = sa;
            while (true) {
                int innerDepth3 = innerDepth2;
                int next = parser.next();
                int type = next;
                if (next == i2) {
                    int i5 = type;
                    int i6 = innerDepth3;
                    String str = classLoaderName2;
                    String[] strArr3 = strArr2;
                    XmlResourceParser xmlResourceParser3 = xmlResourceParser2;
                    Resources resources3 = resources2;
                    Package packageR3 = packageR2;
                    PackageParser packageParser3 = packageParser2;
                    return true;
                } else if (type != 3 || parser.getDepth() > innerDepth3) {
                    if (type == 3) {
                        innerDepth = innerDepth3;
                        classLoaderName = classLoaderName2;
                        xmlResourceParser = xmlResourceParser2;
                        packageR = packageR2;
                        packageParser = packageParser2;
                        z = z2;
                        i = i4;
                        strArr = strArr2;
                        resources = resources2;
                    } else if (type == i3) {
                        innerDepth = innerDepth3;
                        classLoaderName = classLoaderName2;
                        xmlResourceParser = xmlResourceParser2;
                        packageR = packageR2;
                        packageParser = packageParser2;
                        z = z2;
                        i = i4;
                        strArr = strArr2;
                        resources = resources2;
                    } else {
                        CachedComponentArgs cachedArgs = new CachedComponentArgs();
                        String tagName = parser.getName();
                        if (tagName.equals(Context.ACTIVITY_SERVICE)) {
                            String tagName2 = tagName;
                            int i7 = type;
                            innerDepth = innerDepth3;
                            boolean z3 = z2;
                            int i8 = i4;
                            classLoaderName = classLoaderName2;
                            Activity a = parseActivity(owner, res, parser, flags, outError, cachedArgs, false, packageR2.baseHardwareAccelerated);
                            if (a == null) {
                                packageParser2.mParseError = i8;
                                return false;
                            }
                            packageR2.activities.add(a);
                            parsedComponent = a.info;
                            strArr = strArr2;
                            xmlResourceParser = xmlResourceParser2;
                            packageParser = packageParser2;
                            String str2 = tagName2;
                            resources = res;
                            z = false;
                            packageR = packageR2;
                            i = i8;
                        } else {
                            int i9 = type;
                            innerDepth = innerDepth3;
                            boolean z4 = z2;
                            int i10 = i4;
                            classLoaderName = classLoaderName2;
                            if (tagName.equals(IncidentManager.URI_PARAM_RECEIVER_CLASS)) {
                                int i11 = i3;
                                int i12 = i2;
                                String[] strArr4 = strArr2;
                                xmlResourceParser = xmlResourceParser2;
                                resources = res;
                                packageR = packageR2;
                                packageParser = packageParser2;
                                Activity a2 = parseActivity(owner, res, parser, flags, outError, cachedArgs, true, false);
                                if (a2 == null) {
                                    packageParser.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                    return false;
                                }
                                i = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                                z = false;
                                packageR.receivers.add(a2);
                                parsedComponent = a2.info;
                            } else {
                                xmlResourceParser = xmlResourceParser2;
                                packageParser = packageParser2;
                                resources = res;
                                z = z4;
                                packageR = packageR2;
                                i = i10;
                                if (tagName.equals("service")) {
                                    Service s = parseService(owner, res, parser, flags, outError, cachedArgs);
                                    if (s == null) {
                                        packageParser.mParseError = i;
                                        return z;
                                    }
                                    packageR.services.add(s);
                                    parsedComponent = s.info;
                                } else if (tagName.equals("provider")) {
                                    Provider p = parseProvider(owner, res, parser, flags, outError, cachedArgs);
                                    if (p == null) {
                                        packageParser.mParseError = i;
                                        return z;
                                    }
                                    packageR.providers.add(p);
                                    parsedComponent = p.info;
                                } else if (tagName.equals("activity-alias")) {
                                    Activity a3 = parseActivityAlias(owner, res, parser, flags, outError, cachedArgs);
                                    if (a3 == null) {
                                        packageParser.mParseError = i;
                                        return z;
                                    }
                                    packageR.activities.add(a3);
                                    parsedComponent = a3.info;
                                } else {
                                    if (parser.getName().equals("meta-data")) {
                                        strArr = outError;
                                        Bundle parseMetaData = packageParser.parseMetaData(resources, xmlResourceParser, packageR.mAppMetaData, strArr);
                                        packageR.mAppMetaData = parseMetaData;
                                        if (parseMetaData == null) {
                                            packageParser.mParseError = i;
                                            return z;
                                        }
                                    } else {
                                        strArr = outError;
                                        if (tagName.equals("uses-static-library")) {
                                            if (!packageParser.parseUsesStaticLibrary(packageR, resources, xmlResourceParser, strArr)) {
                                                return z;
                                            }
                                        } else if (tagName.equals("uses-library")) {
                                            TypedArray sa2 = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesLibrary);
                                            String lname = sa2.getNonResourceString(z ? 1 : 0);
                                            boolean req = sa2.getBoolean(1, true);
                                            sa2.recycle();
                                            if (lname != null) {
                                                String lname2 = lname.intern();
                                                if (req) {
                                                    packageR.usesLibraries = ArrayUtils.add(packageR.usesLibraries, lname2);
                                                    packageR.usesOptionalLibraries = ArrayUtils.remove(packageR.usesOptionalLibraries, lname2);
                                                } else if (!ArrayUtils.contains(packageR.usesLibraries, lname2)) {
                                                    packageR.usesOptionalLibraries = ArrayUtils.add(packageR.usesOptionalLibraries, lname2);
                                                }
                                            }
                                            XmlUtils.skipCurrentTag(parser);
                                            TypedArray typedArray2 = sa2;
                                        } else if (tagName.equals("uses-package")) {
                                            XmlUtils.skipCurrentTag(parser);
                                        } else {
                                            Slog.w(TAG, "Unknown element under <application>: " + tagName + " at " + packageParser.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(parser);
                                        }
                                    }
                                    parsedComponent = null;
                                }
                            }
                            strArr = outError;
                        }
                        if (parsedComponent != null && parsedComponent.splitName == null) {
                            parsedComponent.splitName = packageR.splitNames[splitIndex];
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
                    packageR2 = packageR;
                    innerDepth2 = innerDepth;
                } else {
                    int i13 = type;
                    int i14 = innerDepth3;
                    String str3 = classLoaderName2;
                    String[] strArr5 = strArr2;
                    XmlResourceParser xmlResourceParser4 = xmlResourceParser2;
                    Resources resources4 = resources2;
                    Package packageR4 = packageR2;
                    PackageParser packageParser4 = packageParser2;
                    return true;
                }
            }
        } else {
            strArr2[0] = "Invalid class loader name: " + classLoaderName2;
            packageParser2.mParseError = PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean parsePackageItemInfo(Package owner, PackageItemInfo outInfo, String[] outError, String tag, TypedArray sa, boolean nameRequired, int nameRes, int labelRes, int iconRes, int roundIconRes, int logoRes, int bannerRes) {
        int roundIconVal;
        Package packageR = owner;
        PackageItemInfo packageItemInfo = outInfo;
        String[] strArr = outError;
        String str = tag;
        TypedArray typedArray = sa;
        if (typedArray == null) {
            strArr[0] = str + " does not contain any attributes";
            return false;
        }
        String name = typedArray.getNonConfigurationString(nameRes, 0);
        if (name != null) {
            String outInfoName = buildClassName(packageR.applicationInfo.packageName, name, strArr);
            if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(outInfoName)) {
                strArr[0] = str + " invalid android:name";
                return false;
            }
            packageItemInfo.name = outInfoName;
            if (outInfoName == null) {
                return false;
            }
        } else if (nameRequired) {
            strArr[0] = str + " does not specify android:name";
            return false;
        }
        if (sUseRoundIcon) {
            roundIconVal = typedArray.getResourceId(roundIconRes, 0);
        } else {
            int i = roundIconRes;
            roundIconVal = 0;
        }
        if (roundIconVal != 0) {
            packageItemInfo.icon = roundIconVal;
            packageItemInfo.nonLocalizedLabel = null;
            int i2 = iconRes;
        } else {
            int iconVal = typedArray.getResourceId(iconRes, 0);
            if (iconVal != 0) {
                packageItemInfo.icon = iconVal;
                packageItemInfo.nonLocalizedLabel = null;
            }
        }
        int logoVal = typedArray.getResourceId(logoRes, 0);
        if (logoVal != 0) {
            packageItemInfo.logo = logoVal;
        }
        int bannerVal = typedArray.getResourceId(bannerRes, 0);
        if (bannerVal != 0) {
            packageItemInfo.banner = bannerVal;
        }
        TypedValue v = typedArray.peekValue(labelRes);
        if (v != null) {
            int i3 = v.resourceId;
            packageItemInfo.labelRes = i3;
            if (i3 == 0) {
                packageItemInfo.nonLocalizedLabel = v.coerceToString();
            }
        }
        packageItemInfo.packageName = packageR.packageName;
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

    private Activity parseActivity(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs, boolean receiver, boolean hardwareAccelerated) throws XmlPullParserException, IOException {
        String str;
        char c;
        TypedArray sa;
        int outerDepth;
        String str2;
        Package packageR;
        String[] strArr;
        char c2;
        XmlResourceParser xmlResourceParser;
        Resources resources;
        Package packageR2 = owner;
        Resources resources2 = res;
        XmlResourceParser xmlResourceParser2 = parser;
        String[] strArr2 = outError;
        CachedComponentArgs cachedComponentArgs = cachedArgs;
        TypedArray sa2 = resources2.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestActivity);
        if (cachedComponentArgs.mActivityArgs == null) {
            cachedComponentArgs.mActivityArgs = new ParseComponentArgs(owner, outError, 3, 1, 2, 44, 23, 30, this.mSeparateProcesses, 7, 17, 5);
        }
        cachedComponentArgs.mActivityArgs.tag = receiver ? "<receiver>" : "<activity>";
        cachedComponentArgs.mActivityArgs.sa = sa2;
        cachedComponentArgs.mActivityArgs.flags = flags;
        Activity a = new Activity(cachedComponentArgs.mActivityArgs, new ActivityInfo());
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
                Log.e(TAG, "Activity " + a.info.name + " specified invalid parentActivityName " + parentName);
                i = 0;
                strArr2[0] = null;
            }
        }
        String str3 = sa2.getNonConfigurationString(4, i);
        if (str3 == null) {
            a.info.permission = packageR2.applicationInfo.permission;
        } else {
            a.info.permission = str3.length() > 0 ? str3.toString().intern() : null;
        }
        String str4 = sa2.getNonConfigurationString(8, 1024);
        a.info.taskAffinity = buildTaskAffinityName(packageR2.applicationInfo.packageName, packageR2.applicationInfo.taskAffinity, str4, strArr2);
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
        if (sa2.getBoolean(19, (packageR2.applicationInfo.flags & 32) != 0)) {
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
        if (!receiver) {
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
            setActivityResizeMode(a.info, sa2, packageR2);
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
            ActivityInfo activityInfo = a.info;
            ActivityInfo activityInfo2 = a.info;
            boolean z = sa2.getBoolean(42, false);
            activityInfo2.directBootAware = z;
            activityInfo.encryptionAware = z;
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
        } else {
            boolean z2 = hardwareAccelerated;
            str = str4;
            a.info.launchMode = 0;
            a.info.configChanges = 0;
            if (sa2.getBoolean(28, false)) {
                a.info.flags |= 1073741824;
            }
            ActivityInfo activityInfo3 = a.info;
            ActivityInfo activityInfo4 = a.info;
            boolean z3 = sa2.getBoolean(42, false);
            activityInfo4.directBootAware = z3;
            activityInfo3.encryptionAware = z3;
        }
        if (a.info.directBootAware) {
            packageR2.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa2.getBoolean(45, false);
        if (visibleToEphemeral) {
            a.info.flags |= 1048576;
            packageR2.visibleToInstantApps = true;
        }
        sa2.recycle();
        if (!receiver || (packageR2.applicationInfo.privateFlags & 2) == 0 || a.info.processName != packageR2.packageName) {
            c = 0;
        } else {
            c = 0;
            strArr2[0] = "Heavy-weight applications can not have receivers in main process";
        }
        if (strArr2[c] != null) {
            return null;
        }
        int outerDepth2 = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1) {
                TypedArray typedArray = sa2;
                String[] strArr3 = strArr2;
                XmlResourceParser xmlResourceParser3 = xmlResourceParser2;
                Resources resources3 = resources2;
                Package packageR3 = packageR2;
                String str5 = str;
                int i2 = type;
                break;
            }
            int type2 = type;
            if (type2 == 3 && parser.getDepth() <= outerDepth2) {
                int i3 = outerDepth2;
                TypedArray typedArray2 = sa2;
                String[] strArr4 = strArr2;
                XmlResourceParser xmlResourceParser4 = xmlResourceParser2;
                Resources resources4 = resources2;
                int i4 = type2;
                String str6 = str;
                Package packageR4 = packageR2;
                break;
            }
            if (type2 != 3) {
                if (type2 == 4) {
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    resources = resources2;
                    packageR = packageR2;
                    str2 = str;
                } else {
                    int outerDepth3 = outerDepth2;
                    if (parser.getName().equals("intent-filter")) {
                        ActivityIntentInfo intent = new ActivityIntentInfo(a);
                        str2 = str;
                        outerDepth = outerDepth3;
                        sa = sa2;
                        strArr = strArr2;
                        int i5 = type2;
                        packageR = packageR2;
                        if (!parseIntent(res, parser, true, true, intent, outError)) {
                            return null;
                        }
                        if (intent.countActions() == 0) {
                            Slog.w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                        } else {
                            a.order = Math.max(intent.getOrder(), a.order);
                            a.intents.add(intent);
                        }
                        intent.setVisibilityToInstantApp(visibleToEphemeral ? 1 : (receiver || !isImplicitlyExposedIntent(intent)) ? 0 : 2);
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
                        int i6 = type2;
                        str2 = str;
                        outerDepth = outerDepth3;
                        packageR = packageR2;
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
                                    Slog.w(TAG, "Problem in package " + this.mArchiveSourcePath + SettingsStringUtil.DELIMITER);
                                    if (receiver) {
                                        Slog.w(TAG, "Unknown element under <receiver>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                    } else {
                                        Slog.w(TAG, "Unknown element under <activity>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
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
                                Slog.w(TAG, "No actions in preferred at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                            } else {
                                if (packageR.preferredActivityFilters == null) {
                                    packageR.preferredActivityFilters = new ArrayList<>();
                                }
                                packageR.preferredActivityFilters.add(intent2);
                            }
                            intent2.setVisibilityToInstantApp(visibleToEphemeral ? 1 : (receiver || !isImplicitlyExposedIntent(intent2)) ? 0 : 2);
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
                packageR = packageR2;
                str2 = str;
                c2 = 0;
            }
            resources2 = resources;
            xmlResourceParser2 = xmlResourceParser;
            packageR2 = packageR;
            str = str2;
            outerDepth2 = outerDepth;
            sa2 = sa;
            CachedComponentArgs cachedComponentArgs2 = cachedArgs;
            char c3 = c2;
            strArr2 = strArr;
            int i7 = flags;
        }
        if (!setExported) {
            a.info.exported = a.intents.size() > 0;
        }
        return a;
    }

    private void setActivityResizeMode(ActivityInfo aInfo, TypedArray sa, Package owner) {
        boolean appResizeable = true;
        boolean appExplicitDefault = (owner.applicationInfo.privateFlags & 3072) != 0;
        if (sa.hasValue(40) || appExplicitDefault) {
            if ((owner.applicationInfo.privateFlags & 1024) == 0) {
                appResizeable = false;
            }
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
        Log.d("debugWindow", "PackageParser  pkgName = " + pkgName);
        Iterator<Activity> it = owner.activities.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (!activity.hasMaxAspectRatio()) {
                if (activity.metaData != null) {
                    activityAspectRatio = activity.metaData.getFloat(METADATA_MAX_ASPECT_RATIO, maxAspectRatio);
                } else {
                    activityAspectRatio = maxAspectRatio;
                }
                Log.d("debugWindow", "PackageParser  setMaxAspectRatio(owner)  activityAspectRatio = " + activityAspectRatio);
                if (pkgName.contains("com.android") || pkgName.contains("com.google") || pkgName.contains("com.wits") || pkgName.contains("com.qcom") || activityAspectRatio >= 3.0f) {
                    activity.setMaxAspectRatio(activityAspectRatio);
                } else {
                    Log.d("debugWindow", "PackageParser  setMaxAspectRatio(owner)  force setMaxAspectRatio(DEFAULT_PRE_O_MAX_ASPECT_RATIO)");
                    activity.setMaxAspectRatio(3.0f);
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
                if (this.mCallback == null || !this.mCallback.hasFeature(PackageManager.FEATURE_WATCH)) {
                    f = DEFAULT_PRE_Q_MIN_ASPECT_RATIO;
                } else {
                    f = 1.0f;
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
        TypedArray sw = res.obtainAttributes(attrs, R.styleable.AndroidManifestLayout);
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
        int i = heightType;
        a.info.windowLayout = new ActivityInfo.WindowLayout(width, widthFraction, height, heightFraction, gravity, minWidth, minHeight);
    }

    private Activity parseActivityAlias(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        String targetActivity;
        boolean z;
        boolean z2;
        TypedArray sa;
        int outerDepth;
        String targetActivity2;
        String[] strArr;
        char c;
        Resources resources;
        ActivityIntentInfo intent;
        int visibility;
        Package packageR = owner;
        Resources resources2 = res;
        String[] strArr2 = outError;
        CachedComponentArgs cachedComponentArgs = cachedArgs;
        TypedArray sa2 = resources2.obtainAttributes(parser, R.styleable.AndroidManifestActivityAlias);
        String targetActivity3 = sa2.getNonConfigurationString(7, 1024);
        if (targetActivity3 == null) {
            strArr2[0] = "<activity-alias> does not specify android:targetActivity";
            sa2.recycle();
            return null;
        }
        String targetActivity4 = buildClassName(packageR.applicationInfo.packageName, targetActivity3, strArr2);
        if (targetActivity4 == null) {
            sa2.recycle();
            return null;
        }
        if (cachedComponentArgs.mActivityAliasArgs == null) {
            ParseComponentArgs parseComponentArgs = r8;
            targetActivity = targetActivity4;
            ParseComponentArgs parseComponentArgs2 = new ParseComponentArgs(owner, outError, 2, 0, 1, 11, 8, 10, this.mSeparateProcesses, 0, 6, 4);
            cachedComponentArgs.mActivityAliasArgs = parseComponentArgs;
            cachedComponentArgs.mActivityAliasArgs.tag = "<activity-alias>";
        } else {
            targetActivity = targetActivity4;
        }
        cachedComponentArgs.mActivityAliasArgs.sa = sa2;
        cachedComponentArgs.mActivityAliasArgs.flags = flags;
        Activity target = null;
        int NA = packageR.activities.size();
        int i = 0;
        while (true) {
            if (i >= NA) {
                break;
            }
            Activity t = packageR.activities.get(i);
            if (targetActivity.equals(t.info.name)) {
                target = t;
                break;
            }
            i++;
        }
        Activity target2 = target;
        if (target2 == null) {
            strArr2[0] = "<activity-alias> target activity " + targetActivity + " not found in manifest";
            sa2.recycle();
            return null;
        }
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
        Activity a = new Activity(cachedComponentArgs.mActivityAliasArgs, info);
        if (strArr2[0] != null) {
            sa2.recycle();
            return null;
        }
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
                String str2 = parentClassName;
                Log.e(TAG, "Activity alias " + a.info.name + " specified invalid parentActivityName " + parentName);
                strArr2[0] = null;
            }
        }
        boolean z4 = true;
        boolean visibleToEphemeral = (a.info.flags & 1048576) != 0;
        sa2.recycle();
        if (strArr2[0] != null) {
            return null;
        }
        int outerDepth2 = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == z4) {
                TypedArray typedArray = sa2;
                z = z4;
                String[] strArr3 = strArr2;
                String str3 = targetActivity;
                Resources resources3 = resources2;
                int i2 = type;
                XmlResourceParser xmlResourceParser = parser;
                break;
            }
            String targetActivity5 = targetActivity;
            int type2 = type;
            if (type2 == 3 && parser.getDepth() <= outerDepth2) {
                int i3 = outerDepth2;
                TypedArray typedArray2 = sa2;
                String[] strArr4 = strArr2;
                Resources resources4 = resources2;
                String str4 = targetActivity5;
                XmlResourceParser xmlResourceParser2 = parser;
                z = true;
                break;
            }
            if (type2 == 3) {
                outerDepth = outerDepth2;
                sa = sa2;
                strArr = strArr2;
                resources = resources2;
                targetActivity2 = targetActivity5;
                XmlResourceParser xmlResourceParser3 = parser;
                c = 0;
                z2 = true;
            } else if (type2 == 4) {
                outerDepth = outerDepth2;
                sa = sa2;
                strArr = strArr2;
                resources = resources2;
                targetActivity2 = targetActivity5;
                XmlResourceParser xmlResourceParser4 = parser;
                c = 0;
                z2 = true;
            } else if (parser.getName().equals("intent-filter")) {
                outerDepth = outerDepth2;
                sa = sa2;
                z2 = true;
                strArr = strArr2;
                ActivityIntentInfo intent2 = new ActivityIntentInfo(a);
                targetActivity2 = targetActivity5;
                if (!parseIntent(res, parser, true, true, intent2, outError)) {
                    return null;
                }
                if (intent2.countActions() == 0) {
                    Slog.w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                    intent = intent2;
                } else {
                    a.order = Math.max(intent2.getOrder(), a.order);
                    intent = intent2;
                    a.intents.add(intent);
                }
                if (visibleToEphemeral) {
                    visibility = 1;
                } else {
                    visibility = isImplicitlyExposedIntent(intent) ? 2 : 0;
                }
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
                XmlResourceParser xmlResourceParser5 = parser;
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
                    XmlResourceParser xmlResourceParser6 = parser;
                    Slog.w(TAG, "Unknown element under <activity-alias>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
            Package packageR2 = owner;
            resources2 = resources;
            char c2 = c;
            strArr2 = strArr;
            targetActivity = targetActivity2;
            outerDepth2 = outerDepth;
            sa2 = sa;
            z4 = z2;
            int i4 = flags;
        }
        if (!setExported) {
            a.info.exported = a.intents.size() > 0 ? z : false;
        }
        return a;
    }

    private Provider parseProvider(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        TypedArray sa;
        Package packageR = owner;
        CachedComponentArgs cachedComponentArgs = cachedArgs;
        TypedArray sa2 = res.obtainAttributes(parser, R.styleable.AndroidManifestProvider);
        if (cachedComponentArgs.mProviderArgs == null) {
            ParseComponentArgs parseComponentArgs = r0;
            sa = sa2;
            ParseComponentArgs parseComponentArgs2 = new ParseComponentArgs(owner, outError, 2, 0, 1, 19, 15, 17, this.mSeparateProcesses, 8, 14, 6);
            cachedComponentArgs.mProviderArgs = parseComponentArgs;
            cachedComponentArgs.mProviderArgs.tag = "<provider>";
        } else {
            sa = sa2;
        }
        TypedArray sa3 = sa;
        cachedComponentArgs.mProviderArgs.sa = sa3;
        cachedComponentArgs.mProviderArgs.flags = flags;
        Provider p = new Provider(cachedComponentArgs.mProviderArgs, new ProviderInfo());
        if (outError[0] != null) {
            sa3.recycle();
            return null;
        }
        boolean providerExportedDefault = false;
        if (packageR.applicationInfo.targetSdkVersion < 17) {
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
            p.info.readPermission = packageR.applicationInfo.permission;
        } else {
            p.info.readPermission = str.length() > 0 ? str.toString().intern() : null;
        }
        String str2 = sa3.getNonConfigurationString(5, 0);
        if (str2 == null) {
            str2 = permission;
        }
        String str3 = str2;
        if (str3 == null) {
            p.info.writePermission = packageR.applicationInfo.permission;
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
            packageR.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa3.getBoolean(20, false);
        if (visibleToEphemeral) {
            p.info.flags |= 1048576;
            packageR.visibleToInstantApps = true;
        }
        sa3.recycle();
        if ((packageR.applicationInfo.privateFlags & 2) != 0 && p.info.processName == packageR.packageName) {
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
            if (!parseProviderTags(res, parser, visibleToEphemeral, p, outError)) {
                return null;
            }
            return p;
        }
    }

    private boolean parseProviderTags(Resources res, XmlResourceParser parser, boolean visibleToEphemeral, Provider outInfo, String[] outError) throws XmlPullParserException, IOException {
        Resources resources = res;
        XmlResourceParser xmlResourceParser = parser;
        Provider provider = outInfo;
        int outerDepth = parser.getDepth();
        while (true) {
            int outerDepth2 = outerDepth;
            int outerDepth3 = parser.next();
            int type = outerDepth3;
            if (outerDepth3 == 1 || (type == 3 && parser.getDepth() <= outerDepth2)) {
                String[] strArr = outError;
            } else {
                if (!(type == 3 || type == 4)) {
                    if (parser.getName().equals("intent-filter")) {
                        ProviderIntentInfo intent = new ProviderIntentInfo(provider);
                        if (!parseIntent(res, parser, true, false, intent, outError)) {
                            return false;
                        }
                        if (visibleToEphemeral) {
                            intent.setVisibilityToInstantApp(1);
                            provider.info.flags |= 1048576;
                        }
                        provider.order = Math.max(intent.getOrder(), provider.order);
                        provider.intents.add(intent);
                    } else {
                        if (parser.getName().equals("meta-data")) {
                            Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, provider.metaData, outError);
                            provider.metaData = parseMetaData;
                            if (parseMetaData == null) {
                                return false;
                            }
                        } else {
                            String[] strArr2 = outError;
                            if (parser.getName().equals("grant-uri-permission")) {
                                TypedArray sa = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestGrantUriPermission);
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
                                    if (provider.info.uriPermissionPatterns == null) {
                                        provider.info.uriPermissionPatterns = new PatternMatcher[1];
                                        provider.info.uriPermissionPatterns[0] = pa;
                                    } else {
                                        int N = provider.info.uriPermissionPatterns.length;
                                        PatternMatcher[] newp = new PatternMatcher[(N + 1)];
                                        System.arraycopy(provider.info.uriPermissionPatterns, 0, newp, 0, N);
                                        newp[N] = pa;
                                        provider.info.uriPermissionPatterns = newp;
                                    }
                                    provider.info.grantUriPermissions = true;
                                    XmlUtils.skipCurrentTag(parser);
                                } else {
                                    Slog.w(TAG, "Unknown element under <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                    XmlUtils.skipCurrentTag(parser);
                                }
                            } else if (parser.getName().equals("path-permission")) {
                                TypedArray sa2 = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPathPermission);
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
                                    Slog.w(TAG, "No readPermission or writePermssion for <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
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
                                        if (provider.info.pathPermissions == null) {
                                            provider.info.pathPermissions = new PathPermission[1];
                                            provider.info.pathPermissions[0] = pa2;
                                            String str4 = path4;
                                        } else {
                                            int N2 = provider.info.pathPermissions.length;
                                            PathPermission[] newp2 = new PathPermission[(N2 + 1)];
                                            String str5 = path4;
                                            System.arraycopy(provider.info.pathPermissions, 0, newp2, 0, N2);
                                            newp2[N2] = pa2;
                                            provider.info.pathPermissions = newp2;
                                        }
                                        XmlUtils.skipCurrentTag(parser);
                                    } else {
                                        String str6 = path4;
                                        Slog.w(TAG, "No path, pathPrefix, or pathPattern for <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                        XmlUtils.skipCurrentTag(parser);
                                    }
                                }
                            } else {
                                Slog.w(TAG, "Unknown element under <provider>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                                XmlUtils.skipCurrentTag(parser);
                            }
                        }
                        outerDepth = outerDepth2;
                    }
                }
                String[] strArr3 = outError;
                outerDepth = outerDepth2;
            }
        }
        String[] strArr4 = outError;
        return true;
    }

    private Service parseService(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        boolean z;
        TypedArray sa;
        int outerDepth;
        XmlResourceParser xmlResourceParser;
        String[] strArr;
        char c;
        Resources resources;
        boolean z2;
        Package packageR = owner;
        Resources resources2 = res;
        XmlResourceParser xmlResourceParser2 = parser;
        String[] strArr2 = outError;
        CachedComponentArgs cachedComponentArgs = cachedArgs;
        TypedArray sa2 = resources2.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestService);
        if (cachedComponentArgs.mServiceArgs == null) {
            cachedComponentArgs.mServiceArgs = new ParseComponentArgs(owner, outError, 2, 0, 1, 15, 8, 12, this.mSeparateProcesses, 6, 7, 4);
            cachedComponentArgs.mServiceArgs.tag = "<service>";
        }
        cachedComponentArgs.mServiceArgs.sa = sa2;
        cachedComponentArgs.mServiceArgs.flags = flags;
        Service s = new Service(cachedComponentArgs.mServiceArgs, new ServiceInfo());
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
            s.info.permission = packageR.applicationInfo.permission;
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
            packageR.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa2.getBoolean(16, false);
        if (visibleToEphemeral) {
            s.info.flags |= 1048576;
            packageR.visibleToInstantApps = true;
        }
        sa2.recycle();
        if ((packageR.applicationInfo.privateFlags & 2) == 0 || s.info.processName != packageR.packageName) {
            int outerDepth2 = parser.getDepth();
            while (true) {
                int next = parser.next();
                int type = next;
                if (next == z3) {
                    TypedArray typedArray = sa2;
                    String[] strArr3 = strArr2;
                    Resources resources3 = resources2;
                    z = z3;
                    int i = type;
                    XmlResourceParser xmlResourceParser3 = xmlResourceParser2;
                    break;
                }
                int type2 = type;
                if (type2 == 3 && parser.getDepth() <= outerDepth2) {
                    int i2 = outerDepth2;
                    TypedArray typedArray2 = sa2;
                    String[] strArr4 = strArr2;
                    XmlResourceParser xmlResourceParser4 = xmlResourceParser2;
                    Resources resources4 = resources2;
                    z = true;
                    break;
                }
                if (type2 == 3) {
                    outerDepth = outerDepth2;
                    sa = sa2;
                    strArr = strArr2;
                    xmlResourceParser = xmlResourceParser2;
                    resources = resources2;
                    z2 = true;
                    c = 0;
                } else if (type2 == 4) {
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
                        Slog.w(TAG, "Unknown element under <service>: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                        XmlUtils.skipCurrentTag(parser);
                    }
                }
                Package packageR2 = owner;
                resources2 = resources;
                char c2 = c;
                strArr2 = strArr;
                xmlResourceParser2 = xmlResourceParser;
                sa2 = sa;
                int i3 = flags;
                CachedComponentArgs cachedComponentArgs2 = cachedArgs;
                z3 = z2;
                outerDepth2 = outerDepth;
            }
            if (!setExported) {
                ServiceInfo serviceInfo3 = s.info;
                if (s.intents.size() <= 0) {
                    z = false;
                }
                serviceInfo3.exported = z;
            }
            return s;
        }
        strArr2[0] = "Heavy-weight applications can not have services in main process";
        return null;
    }

    private boolean isImplicitlyExposedIntent(IntentInfo intent) {
        return intent.hasCategory(Intent.CATEGORY_BROWSABLE) || intent.hasAction(Intent.ACTION_SEND) || intent.hasAction(Intent.ACTION_SENDTO) || intent.hasAction(Intent.ACTION_SEND_MULTIPLE);
    }

    private boolean parseAllMetaData(Resources res, XmlResourceParser parser, String tag, Component<?> outInfo, String[] outError) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                return true;
            }
            if (!(type == 3 || type == 4)) {
                if (parser.getName().equals("meta-data")) {
                    Bundle parseMetaData = parseMetaData(res, parser, outInfo.metaData, outError);
                    outInfo.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return false;
                    }
                } else {
                    Slog.w(TAG, "Unknown element under " + tag + PluralRules.KEYWORD_RULE_SEPARATOR + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
        return true;
    }

    private Bundle parseMetaData(Resources res, XmlResourceParser parser, Bundle data, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestMetaData);
        if (data == null) {
            data = new Bundle();
        }
        boolean z = false;
        String name = sa.getNonConfigurationString(0, 0);
        String str = null;
        if (name == null) {
            outError[0] = "<meta-data> requires an android:name attribute";
            sa.recycle();
            return null;
        }
        String name2 = name.intern();
        TypedValue v = sa.peekValue(2);
        if (v == null || v.resourceId == 0) {
            TypedValue v2 = sa.peekValue(1);
            if (v2 == null) {
                outError[0] = "<meta-data> requires an android:value or android:resource attribute";
                data = null;
            } else if (v2.type == 3) {
                CharSequence cs = v2.coerceToString();
                if (cs != null) {
                    str = cs.toString();
                }
                data.putString(name2, str);
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
                Slog.w(TAG, "<meta-data> only supports string, integer, float, color, boolean, and resource reference types: " + parser.getName() + " at " + this.mArchiveSourcePath + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + parser.getPositionDescription());
            }
        } else {
            data.putInt(name2, v.resourceId);
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
            Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        PublicKey publicKey = parsePublicKey(encodedPublicKey);
        if (publicKey != null) {
            return new VerifierInfo(packageName, publicKey);
        }
        Slog.i(TAG, "Unable to parse verifier public key for " + packageName);
        return null;
    }

    public static final PublicKey parsePublicKey(String encodedPublicKey) {
        if (encodedPublicKey == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(encodedPublicKey, 0));
            try {
                return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA).generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(keySpec);
                } catch (NoSuchAlgorithmException e2) {
                    Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        return KeyFactory.getInstance("DSA").generatePublic(keySpec);
                    } catch (NoSuchAlgorithmException e3) {
                        Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (InvalidKeySpecException e4) {
                        return null;
                    }
                } catch (InvalidKeySpecException e5) {
                    return KeyFactory.getInstance("DSA").generatePublic(keySpec);
                }
            } catch (InvalidKeySpecException e6) {
                return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(keySpec);
            }
        } catch (IllegalArgumentException e7) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [int, boolean] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00aa, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00cd, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean parseIntent(android.content.res.Resources r19, android.content.res.XmlResourceParser r20, boolean r21, boolean r22, android.content.pm.PackageParser.IntentInfo r23, java.lang.String[] r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r18 = this;
            r1 = r19
            r2 = r20
            r3 = r23
            int[] r0 = com.android.internal.R.styleable.AndroidManifestIntentFilter
            android.content.res.TypedArray r0 = r1.obtainAttributes(r2, r0)
            r4 = 2
            r5 = 0
            int r6 = r0.getInt(r4, r5)
            r3.setPriority(r6)
            r7 = 3
            int r8 = r0.getInt(r7, r5)
            r3.setOrder(r8)
            android.util.TypedValue r9 = r0.peekValue(r5)
            if (r9 == 0) goto L_0x002f
            int r10 = r9.resourceId
            r3.labelRes = r10
            if (r10 != 0) goto L_0x002f
            java.lang.CharSequence r10 = r9.coerceToString()
            r3.nonLocalizedLabel = r10
        L_0x002f:
            boolean r10 = sUseRoundIcon
            r11 = 7
            if (r10 == 0) goto L_0x0039
            int r10 = r0.getResourceId(r11, r5)
            goto L_0x003a
        L_0x0039:
            r10 = r5
        L_0x003a:
            r12 = 1
            if (r10 == 0) goto L_0x0040
            r3.icon = r10
            goto L_0x0046
        L_0x0040:
            int r13 = r0.getResourceId(r12, r5)
            r3.icon = r13
        L_0x0046:
            r13 = 4
            int r4 = r0.getResourceId(r13, r5)
            r3.logo = r4
            r4 = 5
            int r11 = r0.getResourceId(r4, r5)
            r3.banner = r11
            r11 = 6
            if (r22 == 0) goto L_0x005e
            boolean r4 = r0.getBoolean(r11, r5)
            r3.setAutoVerify(r4)
        L_0x005e:
            r0.recycle()
            int r4 = r20.getDepth()
        L_0x0065:
            int r11 = r20.next()
            r15 = r11
            if (r11 == r12) goto L_0x01c9
            r11 = r15
            if (r11 != r7) goto L_0x007a
            int r12 = r20.getDepth()
            if (r12 <= r4) goto L_0x0076
            goto L_0x007a
        L_0x0076:
            r5 = r18
            goto L_0x01cc
        L_0x007a:
            if (r11 == r7) goto L_0x01be
            if (r11 != r13) goto L_0x0080
            goto L_0x01be
        L_0x0080:
            java.lang.String r12 = r20.getName()
            java.lang.String r13 = "action"
            boolean r13 = r12.equals(r13)
            if (r13 == 0) goto L_0x00ab
            java.lang.String r13 = "http://schemas.android.com/apk/res/android"
            java.lang.String r7 = "name"
            java.lang.String r7 = r2.getAttributeValue(r13, r7)
            if (r7 == 0) goto L_0x00a6
            java.lang.String r13 = ""
            if (r7 != r13) goto L_0x009b
            goto L_0x00a6
        L_0x009b:
            com.android.internal.util.XmlUtils.skipCurrentTag(r20)
            r3.addAction(r7)
        L_0x00a2:
            r5 = r18
            goto L_0x01bd
        L_0x00a6:
            java.lang.String r13 = "No value supplied for <android:name>"
            r24[r5] = r13
            return r5
        L_0x00ab:
            java.lang.String r7 = "category"
            boolean r7 = r12.equals(r7)
            if (r7 == 0) goto L_0x00ce
            java.lang.String r7 = "http://schemas.android.com/apk/res/android"
            java.lang.String r13 = "name"
            java.lang.String r7 = r2.getAttributeValue(r7, r13)
            if (r7 == 0) goto L_0x00c9
            java.lang.String r13 = ""
            if (r7 != r13) goto L_0x00c2
            goto L_0x00c9
        L_0x00c2:
            com.android.internal.util.XmlUtils.skipCurrentTag(r20)
            r3.addCategory(r7)
            goto L_0x00a2
        L_0x00c9:
            java.lang.String r13 = "No value supplied for <android:name>"
            r24[r5] = r13
            return r5
        L_0x00ce:
            java.lang.String r7 = "data"
            boolean r7 = r12.equals(r7)
            if (r7 == 0) goto L_0x0186
            int[] r7 = com.android.internal.R.styleable.AndroidManifestData
            android.content.res.TypedArray r7 = r1.obtainAttributes(r2, r7)
            java.lang.String r13 = r7.getNonConfigurationString(r5, r5)
            if (r13 == 0) goto L_0x00f3
            r3.addDataType(r13)     // Catch:{ MalformedMimeTypeException -> 0x00e6 }
            goto L_0x00f3
        L_0x00e6:
            r0 = move-exception
            r14 = r0
            r0 = r14
            java.lang.String r14 = r0.toString()
            r24[r5] = r14
            r7.recycle()
            return r5
        L_0x00f3:
            r0 = 1
            java.lang.String r13 = r7.getNonConfigurationString(r0, r5)
            if (r13 == 0) goto L_0x00fd
            r3.addDataScheme(r13)
        L_0x00fd:
            r0 = 7
            java.lang.String r13 = r7.getNonConfigurationString(r0, r5)
            if (r13 == 0) goto L_0x0107
            r3.addDataSchemeSpecificPart(r13, r5)
        L_0x0107:
            r0 = 8
            java.lang.String r0 = r7.getNonConfigurationString(r0, r5)
            if (r0 == 0) goto L_0x0113
            r13 = 1
            r3.addDataSchemeSpecificPart(r0, r13)
        L_0x0113:
            r13 = 9
            java.lang.String r0 = r7.getNonConfigurationString(r13, r5)
            if (r0 == 0) goto L_0x0128
            if (r21 != 0) goto L_0x0123
            java.lang.String r13 = "sspPattern not allowed here; ssp must be literal"
            r24[r5] = r13
            return r5
        L_0x0123:
            r13 = 2
            r3.addDataSchemeSpecificPart(r0, r13)
            goto L_0x0129
        L_0x0128:
            r13 = 2
        L_0x0129:
            r16 = r0
            java.lang.String r0 = r7.getNonConfigurationString(r13, r5)
            r13 = 3
            java.lang.String r1 = r7.getNonConfigurationString(r13, r5)
            if (r0 == 0) goto L_0x0139
            r3.addDataAuthority(r0, r1)
        L_0x0139:
            r17 = r0
            r13 = 4
            java.lang.String r0 = r7.getNonConfigurationString(r13, r5)
            if (r0 == 0) goto L_0x0145
            r3.addDataPath(r0, r5)
        L_0x0145:
            r13 = 5
            java.lang.String r0 = r7.getNonConfigurationString(r13, r5)
            if (r0 == 0) goto L_0x0150
            r13 = 1
            r3.addDataPath(r0, r13)
        L_0x0150:
            r13 = 6
            java.lang.String r0 = r7.getNonConfigurationString(r13, r5)
            if (r0 == 0) goto L_0x0164
            if (r21 != 0) goto L_0x015f
            java.lang.String r13 = "pathPattern not allowed here; path must be literal"
            r24[r5] = r13
            return r5
        L_0x015f:
            r13 = 2
            r3.addDataPath(r0, r13)
            goto L_0x0165
        L_0x0164:
            r13 = 2
        L_0x0165:
            r13 = 10
            java.lang.String r0 = r7.getNonConfigurationString(r13, r5)
            if (r0 == 0) goto L_0x017a
            if (r21 != 0) goto L_0x0175
            java.lang.String r13 = "pathAdvancedPattern not allowed here; path must be literal"
            r24[r5] = r13
            return r5
        L_0x0175:
            r13 = 3
            r3.addDataPath(r0, r13)
            goto L_0x017b
        L_0x017a:
            r13 = 3
        L_0x017b:
            r7.recycle()
            com.android.internal.util.XmlUtils.skipCurrentTag(r20)
            r5 = r18
            r0 = r7
            goto L_0x01bd
        L_0x0186:
            r1 = 7
            r13 = 3
            java.lang.String r7 = "PackageParser"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "Unknown element under <intent-filter>: "
            r1.append(r5)
            java.lang.String r5 = r20.getName()
            r1.append(r5)
            java.lang.String r5 = " at "
            r1.append(r5)
            r5 = r18
            java.lang.String r13 = r5.mArchiveSourcePath
            r1.append(r13)
            java.lang.String r13 = " "
            r1.append(r13)
            java.lang.String r13 = r20.getPositionDescription()
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            android.util.Slog.w((java.lang.String) r7, (java.lang.String) r1)
            com.android.internal.util.XmlUtils.skipCurrentTag(r20)
        L_0x01bd:
            goto L_0x01c0
        L_0x01be:
            r5 = r18
        L_0x01c0:
            r1 = r19
            r5 = 0
            r7 = 3
            r11 = 6
            r12 = 1
            r13 = 4
            goto L_0x0065
        L_0x01c9:
            r5 = r18
            r11 = r15
        L_0x01cc:
            java.lang.String r1 = "android.intent.category.DEFAULT"
            boolean r1 = r3.hasCategory(r1)
            r3.hasDefault = r1
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseIntent(android.content.res.Resources, android.content.res.XmlResourceParser, boolean, boolean, android.content.pm.PackageParser$IntentInfo, java.lang.String[]):boolean");
    }

    public static final class SigningDetails implements Parcelable {
        public static final Parcelable.Creator<SigningDetails> CREATOR = new Parcelable.Creator<SigningDetails>() {
            public SigningDetails createFromParcel(Parcel source) {
                if (source.readBoolean()) {
                    return SigningDetails.UNKNOWN;
                }
                return new SigningDetails(source);
            }

            public SigningDetails[] newArray(int size) {
                return new SigningDetails[size];
            }
        };
        private static final int PAST_CERT_EXISTS = 0;
        public static final SigningDetails UNKNOWN = new SigningDetails((Signature[]) null, 0, (ArraySet<PublicKey>) null, (Signature[]) null);
        public final Signature[] pastSigningCertificates;
        public final ArraySet<PublicKey> publicKeys;
        @SignatureSchemeVersion
        public final int signatureSchemeVersion;
        @UnsupportedAppUsage
        public final Signature[] signatures;

        public @interface CertCapabilities {
            public static final int AUTH = 16;
            public static final int INSTALLED_DATA = 1;
            public static final int PERMISSION = 4;
            public static final int ROLLBACK = 8;
            public static final int SHARED_USER_ID = 2;
        }

        public @interface SignatureSchemeVersion {
            public static final int JAR = 1;
            public static final int SIGNING_BLOCK_V2 = 2;
            public static final int SIGNING_BLOCK_V3 = 3;
            public static final int UNKNOWN = 0;
        }

        @VisibleForTesting
        public SigningDetails(Signature[] signatures2, @SignatureSchemeVersion int signatureSchemeVersion2, ArraySet<PublicKey> keys, Signature[] pastSigningCertificates2) {
            this.signatures = signatures2;
            this.signatureSchemeVersion = signatureSchemeVersion2;
            this.publicKeys = keys;
            this.pastSigningCertificates = pastSigningCertificates2;
        }

        public SigningDetails(Signature[] signatures2, @SignatureSchemeVersion int signatureSchemeVersion2, Signature[] pastSigningCertificates2) throws CertificateException {
            this(signatures2, signatureSchemeVersion2, PackageParser.toSigningKeys(signatures2), pastSigningCertificates2);
        }

        public SigningDetails(Signature[] signatures2, @SignatureSchemeVersion int signatureSchemeVersion2) throws CertificateException {
            this(signatures2, signatureSchemeVersion2, (Signature[]) null);
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
                } else {
                    this.pastSigningCertificates = null;
                }
            } else {
                this.signatures = null;
                this.signatureSchemeVersion = 0;
                this.publicKeys = null;
                this.pastSigningCertificates = null;
            }
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
            if (!hasPastSigningCertificates() || oldDetails.signatures.length != 1) {
                return Signature.areEffectiveMatch(oldDetails.signatures, this.signatures);
            }
            for (int i = 0; i < this.pastSigningCertificates.length; i++) {
                if (Signature.areEffectiveMatch(oldDetails.signatures[0], this.pastSigningCertificates[i]) && this.pastSigningCertificates[i].getFlags() == flags) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasCertificate(Signature signature) {
            return hasCertificateInternal(signature, 0);
        }

        public boolean hasCertificate(Signature signature, @CertCapabilities int flags) {
            return hasCertificateInternal(signature, flags);
        }

        public boolean hasCertificate(byte[] certificate) {
            return hasCertificate(new Signature(certificate));
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
            if (this.signatures.length != 1 || !this.signatures[0].equals(signature)) {
                return false;
            }
            return true;
        }

        public boolean checkCapability(String sha256String, @CertCapabilities int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasSha256Certificate(ByteStringUtils.fromHexToByteArray(sha256String), flags)) {
                return true;
            }
            return PackageUtils.computeSignaturesSha256Digest(PackageUtils.computeSignaturesSha256Digests(this.signatures)).equals(sha256String);
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
                    if (Arrays.equals(sha256Certificate, PackageUtils.computeSha256DigestBytes(this.pastSigningCertificates[i].toByteArray())) && (flags == 0 || (this.pastSigningCertificates[i].getFlags() & flags) == flags)) {
                        return true;
                    }
                }
            }
            if (this.signatures.length == 1) {
                return Arrays.equals(sha256Certificate, PackageUtils.computeSha256DigestBytes(this.signatures[0].toByteArray()));
            }
            return false;
        }

        public boolean signaturesMatchExactly(SigningDetails other) {
            return Signature.areExactMatch(this.signatures, other.signatures);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            boolean isUnknown = UNKNOWN == this;
            dest.writeBoolean(isUnknown);
            if (!isUnknown) {
                dest.writeTypedArray(this.signatures, flags);
                dest.writeInt(this.signatureSchemeVersion);
                dest.writeArraySet(this.publicKeys);
                dest.writeTypedArray(this.pastSigningCertificates, flags);
            }
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
            if (!(o instanceof SigningDetails)) {
                return false;
            }
            SigningDetails that = (SigningDetails) o;
            if (this.signatureSchemeVersion != that.signatureSchemeVersion || !Signature.areExactMatch(this.signatures, that.signatures)) {
                return false;
            }
            if (this.publicKeys != null) {
                if (!this.publicKeys.equals(that.publicKeys)) {
                    return false;
                }
            } else if (that.publicKeys != null) {
                return false;
            }
            if (!Arrays.equals(this.pastSigningCertificates, that.pastSigningCertificates)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((((Arrays.hashCode(this.signatures) * 31) + this.signatureSchemeVersion) * 31) + (this.publicKeys != null ? this.publicKeys.hashCode() : 0)) * 31) + Arrays.hashCode(this.pastSigningCertificates);
        }

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

    public static final class Package implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Package>() {
            public Package createFromParcel(Parcel in) {
                return new Package(in);
            }

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
        public Package(String packageName2) {
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
            this.staticSharedLibVersion = 0;
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
            this.packageName = packageName2;
            this.manifestPackageName = packageName2;
            this.applicationInfo.packageName = packageName2;
            this.applicationInfo.uid = -1;
        }

        public void setApplicationVolumeUuid(String volumeUuid2) {
            UUID storageUuid = StorageManager.convert(volumeUuid2);
            this.applicationInfo.volumeUuid = volumeUuid2;
            this.applicationInfo.storageUuid = storageUuid;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.volumeUuid = volumeUuid2;
                    this.childPackages.get(i).applicationInfo.storageUuid = storageUuid;
                }
            }
        }

        public void setApplicationInfoCodePath(String codePath2) {
            this.applicationInfo.setCodePath(codePath2);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setCodePath(codePath2);
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

        public void setApplicationInfoBaseCodePath(String baseCodePath2) {
            this.applicationInfo.setBaseCodePath(baseCodePath2);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseCodePath(baseCodePath2);
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
                childPackageNames.add(this.childPackages.get(i).packageName);
            }
            return childPackageNames;
        }

        public boolean hasChildPackage(String packageName2) {
            int childCount = this.childPackages != null ? this.childPackages.size() : 0;
            for (int i = 0; i < childCount; i++) {
                if (this.childPackages.get(i).packageName.equals(packageName2)) {
                    return true;
                }
            }
            return false;
        }

        public void setApplicationInfoSplitCodePaths(String[] splitCodePaths2) {
            this.applicationInfo.setSplitCodePaths(splitCodePaths2);
        }

        @Deprecated
        public void setApplicationInfoSplitResourcePaths(String[] resroucePaths) {
            this.applicationInfo.setSplitResourcePaths(resroucePaths);
        }

        public void setSplitCodePaths(String[] codePaths) {
            this.splitCodePaths = codePaths;
        }

        public void setCodePath(String codePath2) {
            this.codePath = codePath2;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).codePath = codePath2;
                }
            }
        }

        public void setBaseCodePath(String baseCodePath2) {
            this.baseCodePath = baseCodePath2;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).baseCodePath = baseCodePath2;
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

        public void setVolumeUuid(String volumeUuid2) {
            this.volumeUuid = volumeUuid2;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).volumeUuid = volumeUuid2;
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

        public void setUse32bitAbi(boolean use32bitAbi2) {
            this.use32bitAbi = use32bitAbi2;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).use32bitAbi = use32bitAbi2;
                }
            }
        }

        public boolean isLibrary() {
            return this.staticSharedLibName != null || !ArrayUtils.isEmpty((Collection<?>) this.libraryNames);
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList<>();
            paths.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty((T[]) this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }
            return paths;
        }

        public List<String> getAllCodePathsExcludingResourceOnly() {
            ArrayList<String> paths = new ArrayList<>();
            if ((this.applicationInfo.flags & 4) != 0) {
                paths.add(this.baseCodePath);
            }
            if (!ArrayUtils.isEmpty((T[]) this.splitCodePaths)) {
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
            long latestUse = 0;
            for (long use : this.mLastPackageUsageTimeInMills) {
                latestUse = Math.max(latestUse, use);
            }
            return latestUse;
        }

        public long getLatestForegroundPackageUseTimeInMills() {
            long latestUse = 0;
            for (int reason : new int[]{0, 2}) {
                latestUse = Math.max(latestUse, this.mLastPackageUsageTimeInMills[reason]);
            }
            return latestUse;
        }

        public String toString() {
            return "Package{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.packageName + "}";
        }

        public int describeContents() {
            return 0;
        }

        public Package(Parcel dest) {
            this.applicationInfo = new ApplicationInfo();
            boolean z = false;
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
            this.staticSharedLibVersion = 0;
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
                this.usesStaticLibrariesCertDigests = new String[libCount][];
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
            this.visibleToInstantApps = dest.readInt() == 1 ? true : z;
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

        public void writeToParcel(Parcel dest, int flags) {
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
            if (ArrayUtils.isEmpty((Collection<?>) this.usesStaticLibraries)) {
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
                        keys.add((PublicKey) in.readSerializable());
                    }
                    keySetMapping.put(key, keys);
                }
            }
            return keySetMapping;
        }
    }

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

        public Component(Package owner2, ArrayList<II> intents2, String className2) {
            this.owner = owner2;
            this.intents = intents2;
            this.className = className2;
        }

        public Component(Package owner2) {
            this.owner = owner2;
            this.intents = null;
            this.className = null;
        }

        public Component(ParsePackageItemArgs args, PackageItemInfo outInfo) {
            ParsePackageItemArgs parsePackageItemArgs = args;
            this.owner = parsePackageItemArgs.owner;
            this.intents = new ArrayList<>(0);
            if (PackageParser.parsePackageItemInfo(parsePackageItemArgs.owner, outInfo, parsePackageItemArgs.outError, parsePackageItemArgs.tag, parsePackageItemArgs.sa, true, parsePackageItemArgs.nameRes, parsePackageItemArgs.labelRes, parsePackageItemArgs.iconRes, parsePackageItemArgs.roundIconRes, parsePackageItemArgs.logoRes, parsePackageItemArgs.bannerRes)) {
                this.className = outInfo.name;
                return;
            }
            PackageItemInfo packageItemInfo = outInfo;
            this.className = null;
        }

        public Component(ParseComponentArgs args, ComponentInfo outInfo) {
            this((ParsePackageItemArgs) args, (PackageItemInfo) outInfo);
            CharSequence pname;
            if (args.outError[0] == null) {
                if (args.processRes != 0) {
                    if (this.owner.applicationInfo.targetSdkVersion >= 8) {
                        pname = args.sa.getNonConfigurationString(args.processRes, 1024);
                    } else {
                        pname = args.sa.getNonResourceString(args.processRes);
                    }
                    outInfo.processName = PackageParser.buildProcessName(this.owner.applicationInfo.packageName, this.owner.applicationInfo.processName, pname, args.flags, args.sepProcesses, args.outError);
                }
                if (args.descriptionRes != 0) {
                    outInfo.descriptionRes = args.sa.getResourceId(args.descriptionRes, 0);
                }
                outInfo.enabled = args.sa.getBoolean(args.enabledRes, true);
            }
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

        /* access modifiers changed from: protected */
        public void writeToParcel(Parcel dest, int flags) {
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
                out.writeString(((IntentInfo) list.get(0)).getClass().getName());
                for (int i = 0; i < N; i++) {
                    ((IntentInfo) list.get(i)).writeIntentInfoToParcel(out, flags);
                }
            }
        }

        private static <T extends IntentInfo> ArrayList<T> createIntentsList(Parcel in) {
            int N = in.readInt();
            if (N == -1) {
                return null;
            }
            if (N == 0) {
                return new ArrayList<>(0);
            }
            String componentName2 = in.readString();
            try {
                Constructor<?> constructor = Class.forName(componentName2).getConstructor(new Class[]{Parcel.class});
                ArrayList<T> intentsList = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    intentsList.add((IntentInfo) constructor.newInstance(new Object[]{in}));
                }
                return intentsList;
            } catch (ReflectiveOperationException e) {
                throw new AssertionError("Unable to construct intent list for: " + componentName2);
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

    public static final class Permission extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Permission>() {
            public Permission createFromParcel(Parcel in) {
                return new Permission(in);
            }

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

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            return "Permission{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.info.name + "}";
        }

        public int describeContents() {
            return 0;
        }

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
            this.tree = in.readInt() != 1 ? false : true;
            this.group = (PermissionGroup) in.readParcelable(boot);
        }
    }

    public static final class PermissionGroup extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<PermissionGroup>() {
            public PermissionGroup createFromParcel(Parcel in) {
                return new PermissionGroup(in);
            }

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

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            return "PermissionGroup{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.info.name + "}";
        }

        public int describeContents() {
            return 0;
        }

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
            if (p.applicationInfo.enabled != (state.enabled == 1)) {
                return true;
            }
        }
        if (state.suspended != ((p.applicationInfo.flags & 1073741824) != 0) || !state.installed || state.hidden || state.stopped || state.instantApp != p.applicationInfo.isInstantApp()) {
            return true;
        }
        if ((flags & 128) != 0 && (metaData != null || p.mAppMetaData != null)) {
            return true;
        }
        if ((flags & 1024) != 0 && p.usesLibraryFiles != null) {
            return true;
        }
        if (((flags & 1024) == 0 || p.usesLibraryInfos == null) && p.staticSharedLibName == null) {
            return false;
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
        boolean z = true;
        if (state.hidden) {
            ai.privateFlags |= 1;
        } else {
            ai.privateFlags &= -2;
        }
        if (state.enabled == 1) {
            ai.enabled = true;
        } else if (state.enabled == 4) {
            if ((32768 & flags) == 0) {
                z = false;
            }
            ai.enabled = z;
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
        if (copyNeeded(flags, p, state, (Bundle) null, userId) || ((32768 & flags) != 0 && state.enabled == 4)) {
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
        updateApplicationInfo(p.applicationInfo, flags, state);
        return p.applicationInfo;
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

    public static final class Activity extends Component<ActivityIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Activity>() {
            public Activity createFromParcel(Parcel in) {
                return new Activity(in);
            }

            public Activity[] newArray(int size) {
                return new Activity[size];
            }
        };
        @UnsupportedAppUsage
        public final ActivityInfo info;
        private boolean mHasMaxAspectRatio;
        private boolean mHasMinAspectRatio;

        /* access modifiers changed from: private */
        public boolean hasMaxAspectRatio() {
            return this.mHasMaxAspectRatio;
        }

        /* access modifiers changed from: private */
        public boolean hasMinAspectRatio() {
            return this.mHasMinAspectRatio;
        }

        Activity(Package owner, String className, ActivityInfo info2) {
            super(owner, new ArrayList(0), className);
            this.info = info2;
            this.info.applicationInfo = owner.applicationInfo;
        }

        public Activity(ParseComponentArgs args, ActivityInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        /* access modifiers changed from: private */
        public void setMaxAspectRatio(float maxAspectRatio) {
            if (this.info.resizeMode != 2 && this.info.resizeMode != 1) {
                if (maxAspectRatio >= 1.0f || maxAspectRatio == 0.0f) {
                    this.info.maxAspectRatio = maxAspectRatio;
                    this.mHasMaxAspectRatio = true;
                }
            }
        }

        /* access modifiers changed from: private */
        public void setMinAspectRatio(float minAspectRatio) {
            if (this.info.resizeMode != 2 && this.info.resizeMode != 1) {
                if (minAspectRatio >= 1.0f || minAspectRatio == 0.0f) {
                    this.info.minAspectRatio = minAspectRatio;
                    this.mHasMinAspectRatio = true;
                }
            }
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

        public int describeContents() {
            return 0;
        }

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

    public static final class Service extends Component<ServiceIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Service>() {
            public Service createFromParcel(Parcel in) {
                return new Service(in);
            }

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

        public int describeContents() {
            return 0;
        }

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

    public static final class Provider extends Component<ProviderIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Provider>() {
            public Provider createFromParcel(Parcel in) {
                return new Provider(in);
            }

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

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags | 2);
            dest.writeInt(this.syncable ? 1 : 0);
        }

        private Provider(Parcel in) {
            super(in);
            this.info = (ProviderInfo) in.readParcelable(Object.class.getClassLoader());
            this.syncable = in.readInt() != 1 ? false : true;
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ((ProviderIntentInfo) it.next()).provider = this;
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
        if (copyNeeded(flags, p.owner, state, p.metaData, userId) || ((flags & 2048) == 0 && p.info.uriPermissionPatterns != null)) {
            ProviderInfo pi = new ProviderInfo(p.info);
            pi.metaData = p.metaData;
            if ((flags & 2048) == 0) {
                pi.uriPermissionPatterns = null;
            }
            pi.applicationInfo = generateApplicationInfo(p.owner, flags, state, userId);
            return pi;
        }
        updateApplicationInfo(p.info.applicationInfo, flags, state);
        return p.info;
    }

    public static final class Instrumentation extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Instrumentation>() {
            public Instrumentation createFromParcel(Parcel in) {
                return new Instrumentation(in);
            }

            public Instrumentation[] newArray(int size) {
                return new Instrumentation[size];
            }
        };
        @UnsupportedAppUsage
        public final InstrumentationInfo info;

        public Instrumentation(ParsePackageItemArgs args, InstrumentationInfo _info) {
            super(args, (PackageItemInfo) _info);
            this.info = _info;
        }

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

        public int describeContents() {
            return 0;
        }

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
            this.hasDefault = dest.readInt() != 1 ? false : true;
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

    public static final class ProviderIntentInfo extends IntentInfo {
        @UnsupportedAppUsage
        public Provider provider;

        public ProviderIntentInfo(Provider provider2) {
            this.provider = provider2;
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
            sUseRoundIcon = r.getBoolean(R.bool.config_useRoundIcon);
            return;
        }
        try {
            ApplicationInfo androidAppInfo = ActivityThread.getPackageManager().getApplicationInfo("android", 0, UserHandle.myUserId());
            Resources systemResources = Resources.getSystem();
            sUseRoundIcon = ResourcesManager.getInstance().getResources((IBinder) null, (String) null, (String[]) null, androidAppInfo.resourceDirs, androidAppInfo.sharedLibraryFiles, 0, (Configuration) null, systemResources.getCompatibilityInfo(), systemResources.getClassLoader()).getBoolean(R.bool.config_useRoundIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class PackageParserException extends Exception {
        public final int error;

        public PackageParserException(int error2, String detailMessage) {
            super(detailMessage);
            this.error = error2;
        }

        public PackageParserException(int error2, String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
            this.error = error2;
        }
    }

    public static PackageInfo generatePackageInfoFromApex(ApexInfo apexInfo, int flags) throws PackageParserException {
        PackageParser pp = new PackageParser();
        File apexFile = new File(apexInfo.packagePath);
        Package p = pp.parsePackage(apexFile, flags, false);
        PackageInfo pi = generatePackageInfo(p, EmptyArray.INT, flags, 0, 0, Collections.emptySet(), new PackageUserState());
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
