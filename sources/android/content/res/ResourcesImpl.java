package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.FontResourcesParser;
import android.content.res.Resources;
import android.content.res.XmlBlock;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.icu.text.PluralRules;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Build;
import android.os.LocaleList;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.DisplayAdjustments;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

public class ResourcesImpl {
    private static final boolean DEBUG_CONFIG = false;
    private static final boolean DEBUG_LOAD = false;
    private static final int ID_OTHER = 16777220;
    static final String TAG = "Resources";
    static final String TAG_PRELOAD = "Resources.preload";
    public static final boolean TRACE_FOR_DETAILED_PRELOAD = SystemProperties.getBoolean("debug.trace_resource_preload", false);
    @UnsupportedAppUsage
    private static final boolean TRACE_FOR_MISS_PRELOAD = false;
    @UnsupportedAppUsage
    private static final boolean TRACE_FOR_PRELOAD = false;
    private static final int XML_BLOCK_CACHE_SIZE = 4;
    private static int sPreloadTracingNumLoadedDrawables;
    private static boolean sPreloaded;
    @UnsupportedAppUsage
    private static final LongSparseArray<Drawable.ConstantState> sPreloadedColorDrawables = new LongSparseArray<>();
    @UnsupportedAppUsage
    private static final LongSparseArray<ConstantState<ComplexColor>> sPreloadedComplexColors = new LongSparseArray<>();
    @UnsupportedAppUsage
    private static final LongSparseArray<Drawable.ConstantState>[] sPreloadedDrawables = new LongSparseArray[2];
    private static final Object sSync = new Object();
    @UnsupportedAppUsage
    private final Object mAccessLock = new Object();
    @UnsupportedAppUsage
    private final ConfigurationBoundResourceCache<Animator> mAnimatorCache = new ConfigurationBoundResourceCache<>();
    @UnsupportedAppUsage
    final AssetManager mAssets;
    private final int[] mCachedXmlBlockCookies = new int[4];
    private final String[] mCachedXmlBlockFiles = new String[4];
    private final XmlBlock[] mCachedXmlBlocks = new XmlBlock[4];
    @UnsupportedAppUsage
    private final DrawableCache mColorDrawableCache = new DrawableCache();
    private final ConfigurationBoundResourceCache<ComplexColor> mComplexColorCache = new ConfigurationBoundResourceCache<>();
    @UnsupportedAppUsage
    private final Configuration mConfiguration = new Configuration();
    private final DisplayAdjustments mDisplayAdjustments;
    @UnsupportedAppUsage
    private final DrawableCache mDrawableCache = new DrawableCache();
    private int mLastCachedXmlBlockIndex = -1;
    private final ThreadLocal<LookupStack> mLookupStack = ThreadLocal.withInitial($$Lambda$ResourcesImpl$h3PTRX185BeQl8SVC2_w9arp5Og.INSTANCE);
    private final DisplayMetrics mMetrics = new DisplayMetrics();
    private PluralRules mPluralRule;
    private long mPreloadTracingPreloadStartTime;
    private long mPreloadTracingStartBitmapCount;
    private long mPreloadTracingStartBitmapSize;
    @UnsupportedAppUsage
    private boolean mPreloading;
    @UnsupportedAppUsage
    private final ConfigurationBoundResourceCache<StateListAnimator> mStateListAnimatorCache = new ConfigurationBoundResourceCache<>();
    private final Configuration mTmpConfig = new Configuration();

    static {
        sPreloadedDrawables[0] = new LongSparseArray<>();
        sPreloadedDrawables[1] = new LongSparseArray<>();
    }

    static /* synthetic */ LookupStack lambda$new$0() {
        return new LookupStack();
    }

    @UnsupportedAppUsage
    public ResourcesImpl(AssetManager assets, DisplayMetrics metrics, Configuration config, DisplayAdjustments displayAdjustments) {
        this.mAssets = assets;
        this.mMetrics.setToDefaults();
        this.mDisplayAdjustments = displayAdjustments;
        this.mConfiguration.setToDefaults();
        updateConfiguration(config, metrics, displayAdjustments.getCompatibilityInfo());
    }

    public DisplayAdjustments getDisplayAdjustments() {
        return this.mDisplayAdjustments;
    }

    @UnsupportedAppUsage
    public AssetManager getAssets() {
        return this.mAssets;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public DisplayMetrics getDisplayMetrics() {
        return this.mMetrics;
    }

    /* access modifiers changed from: package-private */
    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    /* access modifiers changed from: package-private */
    public Configuration[] getSizeConfigurations() {
        return this.mAssets.getSizeConfigurations();
    }

    /* access modifiers changed from: package-private */
    public CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    private PluralRules getPluralRule() {
        PluralRules pluralRules;
        synchronized (sSync) {
            if (this.mPluralRule == null) {
                this.mPluralRule = PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
            }
            pluralRules = this.mPluralRule;
        }
        return pluralRules;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void getValue(int id, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        if (!this.mAssets.getResourceValue(id, 0, outValue, resolveRefs)) {
            throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(id));
        }
    }

    /* access modifiers changed from: package-private */
    public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        if (!this.mAssets.getResourceValue(id, density, outValue, resolveRefs)) {
            throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(id));
        }
    }

    /* access modifiers changed from: package-private */
    public void getValue(String name, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        int id = getIdentifier(name, "string", (String) null);
        if (id != 0) {
            getValue(id, outValue, resolveRefs);
            return;
        }
        throw new Resources.NotFoundException("String resource name " + name);
    }

    /* access modifiers changed from: package-private */
    public int getIdentifier(String name, String defType, String defPackage) {
        if (name != null) {
            try {
                return Integer.parseInt(name);
            } catch (Exception e) {
                return this.mAssets.getResourceIdentifier(name, defType, defPackage);
            }
        } else {
            throw new NullPointerException("name is null");
        }
    }

    /* access modifiers changed from: package-private */
    public String getResourceName(int resid) throws Resources.NotFoundException {
        String str = this.mAssets.getResourceName(resid);
        if (str != null) {
            return str;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(resid));
    }

    /* access modifiers changed from: package-private */
    public String getResourcePackageName(int resid) throws Resources.NotFoundException {
        String str = this.mAssets.getResourcePackageName(resid);
        if (str != null) {
            return str;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(resid));
    }

    /* access modifiers changed from: package-private */
    public String getResourceTypeName(int resid) throws Resources.NotFoundException {
        String str = this.mAssets.getResourceTypeName(resid);
        if (str != null) {
            return str;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(resid));
    }

    /* access modifiers changed from: package-private */
    public String getResourceEntryName(int resid) throws Resources.NotFoundException {
        String str = this.mAssets.getResourceEntryName(resid);
        if (str != null) {
            return str;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(resid));
    }

    /* access modifiers changed from: package-private */
    public String getLastResourceResolution() throws Resources.NotFoundException {
        String str = this.mAssets.getLastResourceResolution();
        if (str != null) {
            return str;
        }
        throw new Resources.NotFoundException("Associated AssetManager hasn't resolved a resource");
    }

    /* access modifiers changed from: package-private */
    public CharSequence getQuantityText(int id, int quantity) throws Resources.NotFoundException {
        PluralRules rule = getPluralRule();
        CharSequence res = this.mAssets.getResourceBagText(id, attrForQuantityCode(rule.select((double) quantity)));
        if (res != null) {
            return res;
        }
        CharSequence res2 = this.mAssets.getResourceBagText(id, ID_OTHER);
        if (res2 != null) {
            return res2;
        }
        throw new Resources.NotFoundException("Plural resource ID #0x" + Integer.toHexString(id) + " quantity=" + quantity + " item=" + rule.select((double) quantity));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int attrForQuantityCode(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case 101272: goto L_0x0033;
                case 110182: goto L_0x0028;
                case 115276: goto L_0x001d;
                case 3343967: goto L_0x0013;
                case 3735208: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x003d
        L_0x0008:
            java.lang.String r0 = "zero"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 0
            goto L_0x003e
        L_0x0013:
            java.lang.String r0 = "many"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 4
            goto L_0x003e
        L_0x001d:
            java.lang.String r0 = "two"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 2
            goto L_0x003e
        L_0x0028:
            java.lang.String r0 = "one"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 1
            goto L_0x003e
        L_0x0033:
            java.lang.String r0 = "few"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 3
            goto L_0x003e
        L_0x003d:
            r0 = -1
        L_0x003e:
            switch(r0) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0051;
                case 2: goto L_0x004d;
                case 3: goto L_0x0049;
                case 4: goto L_0x0045;
                default: goto L_0x0041;
            }
        L_0x0041:
            r0 = 16777220(0x1000004, float:2.3509898E-38)
            return r0
        L_0x0045:
            r0 = 16777225(0x1000009, float:2.3509912E-38)
            return r0
        L_0x0049:
            r0 = 16777224(0x1000008, float:2.350991E-38)
            return r0
        L_0x004d:
            r0 = 16777223(0x1000007, float:2.3509907E-38)
            return r0
        L_0x0051:
            r0 = 16777222(0x1000006, float:2.3509904E-38)
            return r0
        L_0x0055:
            r0 = 16777221(0x1000005, float:2.35099E-38)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ResourcesImpl.attrForQuantityCode(java.lang.String):int");
    }

    /* access modifiers changed from: package-private */
    public AssetFileDescriptor openRawResourceFd(int id, TypedValue tempValue) throws Resources.NotFoundException {
        getValue(id, tempValue, true);
        try {
            return this.mAssets.openNonAssetFd(tempValue.assetCookie, tempValue.string.toString());
        } catch (Exception e) {
            throw new Resources.NotFoundException("File " + tempValue.string.toString() + " from drawable resource ID #0x" + Integer.toHexString(id), e);
        }
    }

    /* access modifiers changed from: package-private */
    public InputStream openRawResource(int id, TypedValue value) throws Resources.NotFoundException {
        getValue(id, value, true);
        try {
            return this.mAssets.openNonAsset(value.assetCookie, value.string.toString(), 2);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("File ");
            sb.append(value.string == null ? "(null)" : value.string.toString());
            sb.append(" from drawable resource ID #0x");
            sb.append(Integer.toHexString(id));
            Resources.NotFoundException rnf = new Resources.NotFoundException(sb.toString());
            rnf.initCause(e);
            throw rnf;
        }
    }

    /* access modifiers changed from: package-private */
    public ConfigurationBoundResourceCache<Animator> getAnimatorCache() {
        return this.mAnimatorCache;
    }

    /* access modifiers changed from: package-private */
    public ConfigurationBoundResourceCache<StateListAnimator> getStateListAnimatorCache() {
        return this.mStateListAnimatorCache;
    }

    public void updateConfiguration(Configuration config, DisplayMetrics metrics, CompatibilityInfo compat) {
        int width;
        int i;
        int i2;
        Locale bestLocale;
        DisplayMetrics displayMetrics = metrics;
        CompatibilityInfo compatibilityInfo = compat;
        Trace.traceBegin(8192, "ResourcesImpl#updateConfiguration");
        try {
            synchronized (this.mAccessLock) {
                if (compatibilityInfo != null) {
                    this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
                }
                if (displayMetrics != null) {
                    this.mMetrics.setTo(displayMetrics);
                }
                this.mDisplayAdjustments.getCompatibilityInfo().applyToDisplayMetrics(this.mMetrics);
                int configChanges = calcConfigChanges(config);
                LocaleList locales = this.mConfiguration.getLocales();
                if (locales.isEmpty()) {
                    locales = LocaleList.getDefault();
                    this.mConfiguration.setLocales(locales);
                }
                if ((configChanges & 4) != 0 && locales.size() > 1) {
                    String[] availableLocales = this.mAssets.getNonSystemLocales();
                    if (LocaleList.isPseudoLocalesOnly(availableLocales)) {
                        availableLocales = this.mAssets.getLocales();
                        if (LocaleList.isPseudoLocalesOnly(availableLocales)) {
                            availableLocales = null;
                        }
                    }
                    if (!(availableLocales == null || (bestLocale = locales.getFirstMatchWithEnglishSupported(availableLocales)) == null || bestLocale == locales.get(0))) {
                        this.mConfiguration.setLocales(new LocaleList(bestLocale, locales));
                    }
                }
                if (this.mConfiguration.densityDpi != 0) {
                    this.mMetrics.densityDpi = this.mConfiguration.densityDpi;
                    this.mMetrics.density = ((float) this.mConfiguration.densityDpi) * 0.00625f;
                }
                this.mMetrics.scaledDensity = this.mMetrics.density * (this.mConfiguration.fontScale != 0.0f ? this.mConfiguration.fontScale : 1.0f);
                if (this.mMetrics.widthPixels >= this.mMetrics.heightPixels) {
                    width = this.mMetrics.widthPixels;
                    i = this.mMetrics.heightPixels;
                } else {
                    width = this.mMetrics.heightPixels;
                    i = this.mMetrics.widthPixels;
                }
                int height = i;
                if (this.mConfiguration.keyboardHidden == 1 && this.mConfiguration.hardKeyboardHidden == 2) {
                    i2 = 3;
                } else {
                    i2 = this.mConfiguration.keyboardHidden;
                }
                int keyboardHidden = i2;
                LocaleList localeList = locales;
                this.mAssets.setConfiguration(this.mConfiguration.mcc, this.mConfiguration.mnc, adjustLanguageTag(this.mConfiguration.getLocales().get(0).toLanguageTag()), this.mConfiguration.orientation, this.mConfiguration.touchscreen, this.mConfiguration.densityDpi, this.mConfiguration.keyboard, keyboardHidden, this.mConfiguration.navigation, width, height, this.mConfiguration.smallestScreenWidthDp, this.mConfiguration.screenWidthDp, this.mConfiguration.screenHeightDp, this.mConfiguration.screenLayout, this.mConfiguration.uiMode, this.mConfiguration.colorMode, Build.VERSION.RESOURCES_SDK_INT);
                int configChanges2 = configChanges;
                this.mDrawableCache.onConfigurationChange(configChanges2);
                this.mColorDrawableCache.onConfigurationChange(configChanges2);
                this.mComplexColorCache.onConfigurationChange(configChanges2);
                this.mAnimatorCache.onConfigurationChange(configChanges2);
                this.mStateListAnimatorCache.onConfigurationChange(configChanges2);
                flushLayoutCache();
            }
            synchronized (sSync) {
                if (this.mPluralRule != null) {
                    this.mPluralRule = PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
                }
            }
            Trace.traceEnd(8192);
        } catch (Throwable th) {
            Trace.traceEnd(8192);
            throw th;
        }
    }

    public int calcConfigChanges(Configuration config) {
        if (config == null) {
            return -1;
        }
        this.mTmpConfig.setTo(config);
        int density = config.densityDpi;
        if (density == 0) {
            density = this.mMetrics.noncompatDensityDpi;
        }
        this.mDisplayAdjustments.getCompatibilityInfo().applyToConfiguration(density, this.mTmpConfig);
        if (this.mTmpConfig.getLocales().isEmpty()) {
            this.mTmpConfig.setLocales(LocaleList.getDefault());
        }
        return this.mConfiguration.updateFrom(this.mTmpConfig);
    }

    private static String adjustLanguageTag(String languageTag) {
        String remainder;
        String language;
        int separator = languageTag.indexOf(45);
        if (separator == -1) {
            language = languageTag;
            remainder = "";
        } else {
            language = languageTag.substring(0, separator);
            remainder = languageTag.substring(separator);
        }
        return Locale.adjustLanguageCode(language) + remainder;
    }

    public void flushLayoutCache() {
        synchronized (this.mCachedXmlBlocks) {
            Arrays.fill(this.mCachedXmlBlockCookies, 0);
            Arrays.fill(this.mCachedXmlBlockFiles, (Object) null);
            XmlBlock[] cachedXmlBlocks = this.mCachedXmlBlocks;
            for (int i = 0; i < 4; i++) {
                XmlBlock oldBlock = cachedXmlBlocks[i];
                if (oldBlock != null) {
                    oldBlock.close();
                }
            }
            Arrays.fill(cachedXmlBlocks, (Object) null);
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable loadDrawable(Resources wrapper, TypedValue value, int id, int density, Resources.Theme theme) throws Resources.NotFoundException {
        int i;
        String name;
        long key;
        DrawableCache caches;
        boolean isColorDrawable;
        Drawable.ConstantState cs;
        Drawable dr;
        Drawable.ConstantState state;
        String name2;
        Drawable cachedDrawable;
        Resources resources = wrapper;
        TypedValue typedValue = value;
        int i2 = id;
        int i3 = density;
        Resources.Theme theme2 = theme;
        boolean useCache = i3 == 0 || typedValue.density == this.mMetrics.densityDpi;
        if (i3 > 0 && typedValue.density > 0 && typedValue.density != 65535) {
            if (typedValue.density == i3) {
                typedValue.density = this.mMetrics.densityDpi;
            } else {
                typedValue.density = (typedValue.density * this.mMetrics.densityDpi) / i3;
            }
        }
        try {
            if (typedValue.type < 28 || typedValue.type > 31) {
                isColorDrawable = false;
                caches = this.mDrawableCache;
                key = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
            } else {
                isColorDrawable = true;
                caches = this.mColorDrawableCache;
                key = (long) typedValue.data;
            }
            boolean isColorDrawable2 = isColorDrawable;
            DrawableCache caches2 = caches;
            long key2 = key;
            if (this.mPreloading || !useCache || (cachedDrawable = caches2.getInstance(key2, resources, theme2)) == null) {
                if (isColorDrawable2) {
                    cs = sPreloadedColorDrawables.get(key2);
                } else {
                    cs = sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].get(key2);
                }
                Drawable.ConstantState cs2 = cs;
                boolean needsNewDrawableAfterCache = false;
                if (cs2 != null) {
                    if (TRACE_FOR_DETAILED_PRELOAD && (i2 >>> 24) == 1 && Process.myUid() != 0 && (name2 = getResourceName(i2)) != null) {
                        Log.d(TAG_PRELOAD, "Hit preloaded FW drawable #" + Integer.toHexString(id) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + name2);
                    }
                    dr = cs2.newDrawable(resources);
                } else if (isColorDrawable2) {
                    dr = new ColorDrawable(typedValue.data);
                } else {
                    dr = loadDrawableForCookie(wrapper, value, id, density);
                }
                if (dr instanceof DrawableContainer) {
                    needsNewDrawableAfterCache = true;
                }
                boolean needsNewDrawableAfterCache2 = needsNewDrawableAfterCache;
                boolean canApplyTheme = dr != null && dr.canApplyTheme();
                if (canApplyTheme && theme2 != null) {
                    dr = dr.mutate();
                    dr.applyTheme(theme2);
                    dr.clearMutated();
                }
                if (dr != null) {
                    dr.setChangingConfigurations(typedValue.changingConfigurations);
                    if (useCache) {
                        DrawableCache drawableCache = caches2;
                        Drawable.ConstantState constantState = cs2;
                        i = 0;
                        try {
                            cacheDrawable(value, isColorDrawable2, caches2, theme, canApplyTheme, key2, dr);
                            if (!needsNewDrawableAfterCache2 || (state = dr.getConstantState()) == null) {
                                return dr;
                            }
                            return state.newDrawable(resources);
                        } catch (Exception e) {
                            e = e;
                            Exception e2 = e;
                            try {
                                name = getResourceName(i2);
                            } catch (Resources.NotFoundException e3) {
                                Resources.NotFoundException notFoundException = e3;
                                name = "(missing name)";
                            }
                            Resources.NotFoundException nfe = new Resources.NotFoundException("Drawable " + name + " with resource ID #0x" + Integer.toHexString(id), e2);
                            nfe.setStackTrace(new StackTraceElement[i]);
                            throw nfe;
                        }
                    }
                }
                long j = key2;
                DrawableCache drawableCache2 = caches2;
                Drawable.ConstantState constantState2 = cs2;
                return dr;
            }
            cachedDrawable.setChangingConfigurations(typedValue.changingConfigurations);
            return cachedDrawable;
        } catch (Exception e4) {
            e = e4;
            i = 0;
            Exception e22 = e;
            name = getResourceName(i2);
            Resources.NotFoundException nfe2 = new Resources.NotFoundException("Drawable " + name + " with resource ID #0x" + Integer.toHexString(id), e22);
            nfe2.setStackTrace(new StackTraceElement[i]);
            throw nfe2;
        }
    }

    private void cacheDrawable(TypedValue value, boolean isColorDrawable, DrawableCache caches, Resources.Theme theme, boolean usesTheme, long key, Drawable dr) {
        TypedValue typedValue = value;
        long j = key;
        Drawable.ConstantState cs = dr.getConstantState();
        if (cs != null) {
            if (this.mPreloading) {
                int changingConfigs = cs.getChangingConfigurations();
                if (isColorDrawable) {
                    if (verifyPreloadConfig(changingConfigs, 0, typedValue.resourceId, "drawable")) {
                        sPreloadedColorDrawables.put(j, cs);
                    }
                } else if (!verifyPreloadConfig(changingConfigs, 8192, typedValue.resourceId, "drawable")) {
                } else {
                    if ((changingConfigs & 8192) == 0) {
                        sPreloadedDrawables[0].put(j, cs);
                        sPreloadedDrawables[1].put(j, cs);
                        return;
                    }
                    sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].put(j, cs);
                }
            } else {
                synchronized (this.mAccessLock) {
                    caches.put(key, theme, cs, usesTheme);
                }
            }
        }
    }

    private boolean verifyPreloadConfig(int changingConfigurations, int allowVarying, int resourceId, String name) {
        String resName;
        if ((-1073745921 & changingConfigurations & (~allowVarying)) == 0) {
            return true;
        }
        try {
            resName = getResourceName(resourceId);
        } catch (Resources.NotFoundException e) {
            resName = "?";
        }
        Log.w(TAG, "Preloaded " + name + " resource #0x" + Integer.toHexString(resourceId) + " (" + resName + ") that varies with configuration!!");
        return false;
    }

    private Drawable decodeImageDrawable(AssetManager.AssetInputStream ais, Resources wrapper, TypedValue value) {
        try {
            return ImageDecoder.decodeDrawable(new ImageDecoder.AssetInputStreamSource(ais, wrapper, value), $$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90.INSTANCE);
        } catch (IOException e) {
            return null;
        }
    }

    private Drawable loadDrawableForCookie(Resources wrapper, TypedValue value, int id, int density) {
        long j;
        LookupStack stack;
        Drawable dr;
        String name;
        String str;
        TypedValue typedValue = value;
        int i = id;
        if (typedValue.string != null) {
            String file = typedValue.string.toString();
            long startTime = 0;
            int startBitmapCount = 0;
            long startBitmapSize = 0;
            int startDrawableCount = 0;
            if (TRACE_FOR_DETAILED_PRELOAD) {
                startTime = System.nanoTime();
                startBitmapCount = Bitmap.sPreloadTracingNumInstantiatedBitmaps;
                startBitmapSize = Bitmap.sPreloadTracingTotalBitmapsSize;
                startDrawableCount = sPreloadTracingNumLoadedDrawables;
            }
            long startTime2 = startTime;
            int startBitmapCount2 = startBitmapCount;
            long startBitmapSize2 = startBitmapSize;
            int startDrawableCount2 = startDrawableCount;
            Trace.traceBegin(8192, file);
            LookupStack stack2 = this.mLookupStack.get();
            try {
                if (!stack2.contains(i)) {
                    stack2.push(i);
                    try {
                        if (file.endsWith(".xml")) {
                            try {
                                if (file.startsWith("res/color/")) {
                                    stack = stack2;
                                    j = 8192;
                                    try {
                                        dr = loadColorOrXmlDrawable(wrapper, value, id, density, file);
                                    } catch (Throwable th) {
                                        th = th;
                                        int i2 = startBitmapCount2;
                                        int i3 = startDrawableCount2;
                                        try {
                                            stack.pop();
                                            throw th;
                                        } catch (Exception | StackOverflowError e) {
                                            e = e;
                                            Trace.traceEnd(j);
                                            Resources.NotFoundException rnf = new Resources.NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
                                            rnf.initCause(e);
                                            throw rnf;
                                        }
                                    }
                                } else {
                                    stack = stack2;
                                    j = 8192;
                                    dr = loadXmlDrawable(wrapper, value, id, density, file);
                                }
                                Resources resources = wrapper;
                            } catch (Throwable th2) {
                                th = th2;
                                stack = stack2;
                                j = 8192;
                                int i4 = startBitmapCount2;
                                int i5 = startDrawableCount2;
                                stack.pop();
                                throw th;
                            }
                        } else {
                            stack = stack2;
                            j = 8192;
                            try {
                                dr = decodeImageDrawable((AssetManager.AssetInputStream) this.mAssets.openNonAsset(typedValue.assetCookie, file, 2), wrapper, typedValue);
                            } catch (Throwable th3) {
                                th = th3;
                                int i6 = startBitmapCount2;
                                int i7 = startDrawableCount2;
                                stack.pop();
                                throw th;
                            }
                        }
                        try {
                            stack.pop();
                            Trace.traceEnd(j);
                            if (!TRACE_FOR_DETAILED_PRELOAD || (i >>> 24) != 1 || (name = getResourceName(i)) == null) {
                                int i8 = startDrawableCount2;
                            } else {
                                int loadedBitmapCount = Bitmap.sPreloadTracingNumInstantiatedBitmaps - startBitmapCount2;
                                long time = System.nanoTime() - startTime2;
                                long loadedBitmapSize = Bitmap.sPreloadTracingTotalBitmapsSize - startBitmapSize2;
                                int loadedDrawables = sPreloadTracingNumLoadedDrawables - startDrawableCount2;
                                sPreloadTracingNumLoadedDrawables++;
                                boolean isRoot = Process.myUid() == 0;
                                int i9 = startBitmapCount2;
                                StringBuilder sb = new StringBuilder();
                                if (isRoot) {
                                    str = "Preloaded FW drawable #";
                                } else {
                                    str = "Loaded non-preloaded FW drawable #";
                                }
                                sb.append(str);
                                sb.append(Integer.toHexString(id));
                                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                                sb.append(name);
                                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                                sb.append(file);
                                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                                sb.append(dr.getClass().getCanonicalName());
                                sb.append(" #nested_drawables= ");
                                sb.append(loadedDrawables);
                                sb.append(" #bitmaps= ");
                                sb.append(loadedBitmapCount);
                                sb.append(" total_bitmap_size= ");
                                sb.append(loadedBitmapSize);
                                sb.append(" in[us] ");
                                long j2 = loadedBitmapSize;
                                sb.append(time / 1000);
                                Log.d(TAG_PRELOAD, sb.toString());
                            }
                            return dr;
                        } catch (Exception | StackOverflowError e2) {
                            e = e2;
                            int i10 = startBitmapCount2;
                            int i11 = startDrawableCount2;
                            Trace.traceEnd(j);
                            Resources.NotFoundException rnf2 = new Resources.NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
                            rnf2.initCause(e);
                            throw rnf2;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        stack = stack2;
                        j = 8192;
                        int i12 = startBitmapCount2;
                        int i13 = startDrawableCount2;
                        stack.pop();
                        throw th;
                    }
                } else {
                    int i14 = startBitmapCount2;
                    int i15 = startDrawableCount2;
                    throw new Exception("Recursive reference in drawable");
                }
            } catch (Exception | StackOverflowError e3) {
                e = e3;
                LookupStack lookupStack = stack2;
                j = 8192;
                int i16 = startBitmapCount2;
                int i17 = startDrawableCount2;
                Trace.traceEnd(j);
                Resources.NotFoundException rnf22 = new Resources.NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
                rnf22.initCause(e);
                throw rnf22;
            }
        } else {
            throw new Resources.NotFoundException("Resource \"" + getResourceName(i) + "\" (" + Integer.toHexString(id) + ") is not a Drawable (color or path): " + typedValue);
        }
    }

    private Drawable loadColorOrXmlDrawable(Resources wrapper, TypedValue value, int id, int density, String file) {
        try {
            return new ColorStateListDrawable(loadColorStateList(wrapper, value, id, (Resources.Theme) null));
        } catch (Resources.NotFoundException originalException) {
            try {
                return loadXmlDrawable(wrapper, value, id, density, file);
            } catch (Exception e) {
                throw originalException;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        if (r0 != null) goto L_0x001a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        if (r1 != null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0025, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0014, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable loadXmlDrawable(android.content.res.Resources r5, android.util.TypedValue r6, int r7, int r8, java.lang.String r9) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            r4 = this;
            int r0 = r6.assetCookie
            java.lang.String r1 = "drawable"
            android.content.res.XmlResourceParser r0 = r4.loadXmlResourceParser(r9, r7, r0, r1)
            r1 = 0
            android.graphics.drawable.Drawable r2 = android.graphics.drawable.Drawable.createFromXmlForDensity(r5, r0, r8, r1)     // Catch:{ Throwable -> 0x0016 }
            if (r0 == 0) goto L_0x0013
            r0.close()
        L_0x0013:
            return r2
        L_0x0014:
            r2 = move-exception
            goto L_0x0018
        L_0x0016:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0014 }
        L_0x0018:
            if (r0 == 0) goto L_0x0028
            if (r1 == 0) goto L_0x0025
            r0.close()     // Catch:{ Throwable -> 0x0020 }
            goto L_0x0028
        L_0x0020:
            r3 = move-exception
            r1.addSuppressed(r3)
            goto L_0x0028
        L_0x0025:
            r0.close()
        L_0x0028:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ResourcesImpl.loadXmlDrawable(android.content.res.Resources, android.util.TypedValue, int, int, java.lang.String):android.graphics.drawable.Drawable");
    }

    public Typeface loadFont(Resources wrapper, TypedValue value, int id) {
        if (value.string != null) {
            String file = value.string.toString();
            if (!file.startsWith("res/")) {
                return null;
            }
            Typeface cached = Typeface.findFromCache(this.mAssets, file);
            if (cached != null) {
                return cached;
            }
            Trace.traceBegin(8192, file);
            try {
                if (file.endsWith("xml")) {
                    FontResourcesParser.FamilyResourceEntry familyEntry = FontResourcesParser.parse(loadXmlResourceParser(file, id, value.assetCookie, "font"), wrapper);
                    if (familyEntry == null) {
                        Trace.traceEnd(8192);
                        return null;
                    }
                    Typeface createFromResources = Typeface.createFromResources(familyEntry, this.mAssets, file);
                    Trace.traceEnd(8192);
                    return createFromResources;
                }
                Typeface build = new Typeface.Builder(this.mAssets, file, false, value.assetCookie).build();
                Trace.traceEnd(8192);
                return build;
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Failed to parse xml resource " + file, e);
            } catch (IOException e2) {
                Log.e(TAG, "Failed to read xml resource " + file, e2);
            } catch (Throwable th) {
                Trace.traceEnd(8192);
                throw th;
            }
        } else {
            throw new Resources.NotFoundException("Resource \"" + getResourceName(id) + "\" (" + Integer.toHexString(id) + ") is not a Font: " + value);
        }
        Trace.traceEnd(8192);
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.content.res.ComplexColor} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.res.ComplexColor loadComplexColorFromName(android.content.res.Resources r10, android.content.res.Resources.Theme r11, android.util.TypedValue r12, int r13) {
        /*
            r9 = this;
            int r0 = r12.assetCookie
            long r0 = (long) r0
            r2 = 32
            long r0 = r0 << r2
            int r2 = r12.data
            long r2 = (long) r2
            long r0 = r0 | r2
            android.content.res.ConfigurationBoundResourceCache<android.content.res.ComplexColor> r2 = r9.mComplexColorCache
            java.lang.Object r3 = r2.getInstance(r0, r10, r11)
            android.content.res.ComplexColor r3 = (android.content.res.ComplexColor) r3
            if (r3 == 0) goto L_0x0015
            return r3
        L_0x0015:
            android.util.LongSparseArray<android.content.res.ConstantState<android.content.res.ComplexColor>> r4 = sPreloadedComplexColors
            java.lang.Object r4 = r4.get(r0)
            android.content.res.ConstantState r4 = (android.content.res.ConstantState) r4
            if (r4 == 0) goto L_0x0026
            java.lang.Object r5 = r4.newInstance(r10, r11)
            r3 = r5
            android.content.res.ComplexColor r3 = (android.content.res.ComplexColor) r3
        L_0x0026:
            if (r3 != 0) goto L_0x002c
            android.content.res.ComplexColor r3 = r9.loadComplexColorForCookie(r10, r12, r13, r11)
        L_0x002c:
            if (r3 == 0) goto L_0x0057
            int r5 = r12.changingConfigurations
            r3.setBaseChangingConfigurations(r5)
            boolean r5 = r9.mPreloading
            if (r5 == 0) goto L_0x0050
            int r5 = r3.getChangingConfigurations()
            r6 = 0
            int r7 = r12.resourceId
            java.lang.String r8 = "color"
            boolean r5 = r9.verifyPreloadConfig(r5, r6, r7, r8)
            if (r5 == 0) goto L_0x0057
            android.util.LongSparseArray<android.content.res.ConstantState<android.content.res.ComplexColor>> r5 = sPreloadedComplexColors
            android.content.res.ConstantState r6 = r3.getConstantState()
            r5.put(r0, r6)
            goto L_0x0057
        L_0x0050:
            android.content.res.ConstantState r5 = r3.getConstantState()
            r2.put(r0, r11, r5)
        L_0x0057:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ResourcesImpl.loadComplexColorFromName(android.content.res.Resources, android.content.res.Resources$Theme, android.util.TypedValue, int):android.content.res.ComplexColor");
    }

    /* access modifiers changed from: package-private */
    public ComplexColor loadComplexColor(Resources wrapper, TypedValue value, int id, Resources.Theme theme) {
        long key = (((long) value.assetCookie) << 32) | ((long) value.data);
        if (value.type >= 28 && value.type <= 31) {
            return getColorStateListFromInt(value, key);
        }
        String file = value.string.toString();
        if (file.endsWith(".xml")) {
            try {
                return loadComplexColorFromName(wrapper, theme, value, id);
            } catch (Exception e) {
                Resources.NotFoundException rnf = new Resources.NotFoundException("File " + file + " from complex color resource ID #0x" + Integer.toHexString(id));
                rnf.initCause(e);
                throw rnf;
            }
        } else {
            throw new Resources.NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id) + ": .xml extension required");
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList loadColorStateList(Resources wrapper, TypedValue value, int id, Resources.Theme theme) throws Resources.NotFoundException {
        long key = (((long) value.assetCookie) << 32) | ((long) value.data);
        if (value.type >= 28 && value.type <= 31) {
            return getColorStateListFromInt(value, key);
        }
        ComplexColor complexColor = loadComplexColorFromName(wrapper, theme, value, id);
        if (complexColor != null && (complexColor instanceof ColorStateList)) {
            return (ColorStateList) complexColor;
        }
        throw new Resources.NotFoundException("Can't find ColorStateList from drawable resource ID #0x" + Integer.toHexString(id));
    }

    private ColorStateList getColorStateListFromInt(TypedValue value, long key) {
        ConstantState<ComplexColor> factory = sPreloadedComplexColors.get(key);
        if (factory != null) {
            return (ColorStateList) factory.newInstance();
        }
        ColorStateList csl = ColorStateList.valueOf(value.data);
        if (this.mPreloading && verifyPreloadConfig(value.changingConfigurations, 0, value.resourceId, "color")) {
            sPreloadedComplexColors.put(key, csl.getConstantState());
        }
        return csl;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032 A[Catch:{ Exception -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005a A[SYNTHETIC, Splitter:B:21:0x005a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.res.ComplexColor loadComplexColorForCookie(android.content.res.Resources r10, android.util.TypedValue r11, int r12, android.content.res.Resources.Theme r13) {
        /*
            r9 = this;
            java.lang.CharSequence r0 = r11.string
            if (r0 == 0) goto L_0x00b7
            java.lang.CharSequence r0 = r11.string
            java.lang.String r0 = r0.toString()
            r1 = 0
            r2 = 8192(0x2000, double:4.0474E-320)
            android.os.Trace.traceBegin(r2, r0)
            java.lang.String r4 = ".xml"
            boolean r4 = r0.endsWith(r4)
            if (r4 == 0) goto L_0x008c
            int r4 = r11.assetCookie     // Catch:{ Exception -> 0x0062 }
            java.lang.String r5 = "ComplexColor"
            android.content.res.XmlResourceParser r4 = r9.loadXmlResourceParser(r0, r12, r4, r5)     // Catch:{ Exception -> 0x0062 }
            android.util.AttributeSet r5 = android.util.Xml.asAttributeSet(r4)     // Catch:{ Exception -> 0x0062 }
        L_0x0024:
            int r6 = r4.next()     // Catch:{ Exception -> 0x0062 }
            r7 = r6
            r8 = 2
            if (r6 == r8) goto L_0x0030
            r6 = 1
            if (r7 == r6) goto L_0x0030
            goto L_0x0024
        L_0x0030:
            if (r7 != r8) goto L_0x005a
            java.lang.String r6 = r4.getName()     // Catch:{ Exception -> 0x0062 }
            java.lang.String r8 = "gradient"
            boolean r8 = r6.equals(r8)     // Catch:{ Exception -> 0x0062 }
            if (r8 == 0) goto L_0x0044
            android.content.res.GradientColor r8 = android.content.res.GradientColor.createFromXmlInner(r10, r4, r5, r13)     // Catch:{ Exception -> 0x0062 }
            r1 = r8
            goto L_0x0052
        L_0x0044:
            java.lang.String r8 = "selector"
            boolean r8 = r6.equals(r8)     // Catch:{ Exception -> 0x0062 }
            if (r8 == 0) goto L_0x0052
            android.content.res.ColorStateList r8 = android.content.res.ColorStateList.createFromXmlInner(r10, r4, r5, r13)     // Catch:{ Exception -> 0x0062 }
            r1 = r8
        L_0x0052:
            r4.close()     // Catch:{ Exception -> 0x0062 }
            android.os.Trace.traceEnd(r2)
            return r1
        L_0x005a:
            org.xmlpull.v1.XmlPullParserException r6 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x0062 }
            java.lang.String r8 = "No start tag found"
            r6.<init>(r8)     // Catch:{ Exception -> 0x0062 }
            throw r6     // Catch:{ Exception -> 0x0062 }
        L_0x0062:
            r4 = move-exception
            android.os.Trace.traceEnd(r2)
            android.content.res.Resources$NotFoundException r2 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "File "
            r3.append(r5)
            r3.append(r0)
            java.lang.String r5 = " from ComplexColor resource ID #0x"
            r3.append(r5)
            java.lang.String r5 = java.lang.Integer.toHexString(r12)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            r2.initCause(r4)
            throw r2
        L_0x008c:
            android.os.Trace.traceEnd(r2)
            android.content.res.Resources$NotFoundException r2 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "File "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r4 = " from drawable resource ID #0x"
            r3.append(r4)
            java.lang.String r4 = java.lang.Integer.toHexString(r12)
            r3.append(r4)
            java.lang.String r4 = ": .xml extension required"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x00b7:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Can't convert to ComplexColor: type=0x"
            r1.append(r2)
            int r2 = r11.type
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ResourcesImpl.loadComplexColorForCookie(android.content.res.Resources, android.util.TypedValue, int, android.content.res.Resources$Theme):android.content.res.ComplexColor");
    }

    /* access modifiers changed from: package-private */
    public XmlResourceParser loadXmlResourceParser(String file, int id, int assetCookie, String type) throws Resources.NotFoundException {
        if (id != 0) {
            try {
                synchronized (this.mCachedXmlBlocks) {
                    int[] cachedXmlBlockCookies = this.mCachedXmlBlockCookies;
                    String[] cachedXmlBlockFiles = this.mCachedXmlBlockFiles;
                    XmlBlock[] cachedXmlBlocks = this.mCachedXmlBlocks;
                    int num = cachedXmlBlockFiles.length;
                    int i = 0;
                    while (i < num) {
                        if (cachedXmlBlockCookies[i] != assetCookie || cachedXmlBlockFiles[i] == null || !cachedXmlBlockFiles[i].equals(file)) {
                            i++;
                        } else {
                            XmlResourceParser newParser = cachedXmlBlocks[i].newParser(id);
                            return newParser;
                        }
                    }
                    XmlBlock block = this.mAssets.openXmlBlockAsset(assetCookie, file);
                    if (block != null) {
                        int pos = (this.mLastCachedXmlBlockIndex + 1) % num;
                        this.mLastCachedXmlBlockIndex = pos;
                        XmlBlock oldBlock = cachedXmlBlocks[pos];
                        if (oldBlock != null) {
                            oldBlock.close();
                        }
                        cachedXmlBlockCookies[pos] = assetCookie;
                        cachedXmlBlockFiles[pos] = file;
                        cachedXmlBlocks[pos] = block;
                        XmlResourceParser newParser2 = block.newParser(id);
                        return newParser2;
                    }
                }
            } catch (Exception e) {
                Resources.NotFoundException rnf = new Resources.NotFoundException("File " + file + " from xml type " + type + " resource ID #0x" + Integer.toHexString(id));
                rnf.initCause(e);
                throw rnf;
            }
        }
        throw new Resources.NotFoundException("File " + file + " from xml type " + type + " resource ID #0x" + Integer.toHexString(id));
    }

    public final void startPreloading() {
        synchronized (sSync) {
            if (!sPreloaded) {
                sPreloaded = true;
                this.mPreloading = true;
                this.mConfiguration.densityDpi = DisplayMetrics.DENSITY_DEVICE;
                updateConfiguration((Configuration) null, (DisplayMetrics) null, (CompatibilityInfo) null);
                if (TRACE_FOR_DETAILED_PRELOAD) {
                    this.mPreloadTracingPreloadStartTime = SystemClock.uptimeMillis();
                    this.mPreloadTracingStartBitmapSize = Bitmap.sPreloadTracingTotalBitmapsSize;
                    this.mPreloadTracingStartBitmapCount = (long) Bitmap.sPreloadTracingNumInstantiatedBitmaps;
                    Log.d(TAG_PRELOAD, "Preload starting");
                }
            } else {
                throw new IllegalStateException("Resources already preloaded");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void finishPreloading() {
        if (this.mPreloading) {
            if (TRACE_FOR_DETAILED_PRELOAD) {
                long time = SystemClock.uptimeMillis() - this.mPreloadTracingPreloadStartTime;
                long size = Bitmap.sPreloadTracingTotalBitmapsSize - this.mPreloadTracingStartBitmapSize;
                Log.d(TAG_PRELOAD, "Preload finished, " + (((long) Bitmap.sPreloadTracingNumInstantiatedBitmaps) - this.mPreloadTracingStartBitmapCount) + " bitmaps of " + size + " bytes in " + time + " ms");
            }
            this.mPreloading = false;
            flushLayoutCache();
        }
    }

    static int getAttributeSetSourceResId(AttributeSet set) {
        if (set == null || !(set instanceof XmlBlock.Parser)) {
            return 0;
        }
        return ((XmlBlock.Parser) set).getSourceResId();
    }

    /* access modifiers changed from: package-private */
    public LongSparseArray<Drawable.ConstantState> getPreloadedDrawables() {
        return sPreloadedDrawables[0];
    }

    /* access modifiers changed from: package-private */
    public ThemeImpl newThemeImpl() {
        return new ThemeImpl();
    }

    /* access modifiers changed from: package-private */
    public ThemeImpl newThemeImpl(Resources.ThemeKey key) {
        ThemeImpl impl = new ThemeImpl();
        impl.mKey.setTo(key);
        impl.rebase();
        return impl;
    }

    public class ThemeImpl {
        private final AssetManager mAssets;
        /* access modifiers changed from: private */
        public final Resources.ThemeKey mKey = new Resources.ThemeKey();
        private final long mTheme;
        private int mThemeResId = 0;

        ThemeImpl() {
            this.mAssets = ResourcesImpl.this.mAssets;
            this.mTheme = this.mAssets.createTheme();
        }

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            super.finalize();
            this.mAssets.releaseTheme(this.mTheme);
        }

        /* access modifiers changed from: package-private */
        public Resources.ThemeKey getKey() {
            return this.mKey;
        }

        /* access modifiers changed from: package-private */
        public long getNativeTheme() {
            return this.mTheme;
        }

        /* access modifiers changed from: package-private */
        public int getAppliedStyleResId() {
            return this.mThemeResId;
        }

        /* access modifiers changed from: package-private */
        public void applyStyle(int resId, boolean force) {
            synchronized (this.mKey) {
                this.mAssets.applyStyleToTheme(this.mTheme, resId, force);
                this.mThemeResId = resId;
                this.mKey.append(resId, force);
            }
        }

        /* access modifiers changed from: package-private */
        public void setTo(ThemeImpl other) {
            synchronized (this.mKey) {
                synchronized (other.mKey) {
                    this.mAssets.setThemeTo(this.mTheme, other.mAssets, other.mTheme);
                    this.mThemeResId = other.mThemeResId;
                    this.mKey.setTo(other.getKey());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public TypedArray obtainStyledAttributes(Resources.Theme wrapper, AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) {
            synchronized (this.mKey) {
                try {
                    int len = attrs.length;
                    TypedArray array = TypedArray.obtain(wrapper.getResources(), len);
                    XmlBlock.Parser parser = (XmlBlock.Parser) set;
                    int i = len;
                    XmlBlock.Parser parser2 = parser;
                    this.mAssets.applyStyle(this.mTheme, defStyleAttr, defStyleRes, parser, attrs, array.mDataAddress, array.mIndicesAddress);
                    array.mTheme = wrapper;
                    array.mXml = parser2;
                    return array;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public TypedArray resolveAttributes(Resources.Theme wrapper, int[] values, int[] attrs) {
            TypedArray array;
            synchronized (this.mKey) {
                int len = attrs.length;
                if (values == null || len != values.length) {
                    throw new IllegalArgumentException("Base attribute values must the same length as attrs");
                }
                array = TypedArray.obtain(wrapper.getResources(), len);
                this.mAssets.resolveAttrs(this.mTheme, 0, 0, values, attrs, array.mData, array.mIndices);
                array.mTheme = wrapper;
                array.mXml = null;
            }
            return array;
        }

        /* access modifiers changed from: package-private */
        public boolean resolveAttribute(int resid, TypedValue outValue, boolean resolveRefs) {
            boolean themeValue;
            synchronized (this.mKey) {
                themeValue = this.mAssets.getThemeValue(this.mTheme, resid, outValue, resolveRefs);
            }
            return themeValue;
        }

        /* access modifiers changed from: package-private */
        public int[] getAllAttributes() {
            return this.mAssets.getStyleAttributes(getAppliedStyleResId());
        }

        /* access modifiers changed from: package-private */
        public int getChangingConfigurations() {
            int activityInfoConfigNativeToJava;
            synchronized (this.mKey) {
                activityInfoConfigNativeToJava = ActivityInfo.activityInfoConfigNativeToJava(AssetManager.nativeThemeGetChangingConfigurations(this.mTheme));
            }
            return activityInfoConfigNativeToJava;
        }

        public void dump(int priority, String tag, String prefix) {
            synchronized (this.mKey) {
                this.mAssets.dumpTheme(this.mTheme, priority, tag, prefix);
            }
        }

        /* access modifiers changed from: package-private */
        public String[] getTheme() {
            String[] themes;
            synchronized (this.mKey) {
                int N = this.mKey.mCount;
                themes = new String[(N * 2)];
                int i = 0;
                int j = N - 1;
                while (i < themes.length) {
                    int resId = this.mKey.mResId[j];
                    boolean forced = this.mKey.mForce[j];
                    try {
                        themes[i] = ResourcesImpl.this.getResourceName(resId);
                    } catch (Resources.NotFoundException e) {
                        themes[i] = Integer.toHexString(i);
                    }
                    themes[i + 1] = forced ? "forced" : "not forced";
                    i += 2;
                    j--;
                }
            }
            return themes;
        }

        /* access modifiers changed from: package-private */
        public void rebase() {
            synchronized (this.mKey) {
                AssetManager.nativeThemeClear(this.mTheme);
                for (int i = 0; i < this.mKey.mCount; i++) {
                    this.mAssets.applyStyleToTheme(this.mTheme, this.mKey.mResId[i], this.mKey.mForce[i]);
                }
            }
        }

        public int[] getAttributeResolutionStack(int defStyleAttr, int defStyleRes, int explicitStyleRes) {
            int[] attributeResolutionStack;
            synchronized (this.mKey) {
                attributeResolutionStack = this.mAssets.getAttributeResolutionStack(this.mTheme, defStyleAttr, defStyleRes, explicitStyleRes);
            }
            return attributeResolutionStack;
        }
    }

    private static class LookupStack {
        private int[] mIds;
        private int mSize;

        private LookupStack() {
            this.mIds = new int[4];
            this.mSize = 0;
        }

        public void push(int id) {
            this.mIds = GrowingArrayUtils.append(this.mIds, this.mSize, id);
            this.mSize++;
        }

        public boolean contains(int id) {
            for (int i = 0; i < this.mSize; i++) {
                if (this.mIds[i] == id) {
                    return true;
                }
            }
            return false;
        }

        public void pop() {
            this.mSize--;
        }
    }
}
