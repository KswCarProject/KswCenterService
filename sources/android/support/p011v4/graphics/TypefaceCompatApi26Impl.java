package android.support.p011v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.p007os.CancellationSignal;
import android.p007os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p011v4.content.res.FontResourcesParserCompat;
import android.support.p011v4.provider.FontsContractCompat;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.graphics.TypefaceCompatApi26Impl */
/* loaded from: classes3.dex */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Constructor fontFamilyCtor;
        Constructor constructor;
        Constructor constructor2;
        Class fontFamilyClass;
        Constructor fontFamilyCtor2;
        Method addFontMethod;
        Method createFromFamiliesWithDefaultMethod;
        Method freezeMethod;
        Method abortCreationMethod;
        Method createFromFamiliesWithDefaultMethod2;
        Method addFromBufferMethod;
        Method abortCreationMethod2;
        Method createFromFamiliesWithDefaultMethod3 = null;
        try {
            fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e = e;
            fontFamilyCtor = null;
        }
        try {
            fontFamilyCtor2 = fontFamilyClass.getConstructor(new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            e = e2;
            fontFamilyCtor = null;
            constructor = fontFamilyCtor;
            constructor2 = constructor;
            Log.m69e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            fontFamilyClass = null;
            fontFamilyCtor2 = null;
            addFontMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod2 = null;
            sFontFamilyCtor = fontFamilyCtor2;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = createFromFamiliesWithDefaultMethod;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
        }
        try {
            addFontMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
            try {
                addFromBufferMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
                try {
                    freezeMethod = fontFamilyClass.getMethod(FREEZE_METHOD, new Class[0]);
                } catch (ClassNotFoundException | NoSuchMethodException e3) {
                    e = e3;
                    abortCreationMethod2 = null;
                }
            } catch (ClassNotFoundException | NoSuchMethodException e4) {
                e = e4;
                constructor2 = null;
                Log.m69e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor2 = null;
                addFontMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod2 = null;
                sFontFamilyCtor = fontFamilyCtor2;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = createFromFamiliesWithDefaultMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
            }
            try {
                abortCreationMethod = fontFamilyClass.getMethod(ABORT_CREATION_METHOD, new Class[0]);
            } catch (ClassNotFoundException | NoSuchMethodException e5) {
                e = e5;
                abortCreationMethod2 = null;
                Log.m69e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor2 = null;
                addFontMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod2 = null;
                sFontFamilyCtor = fontFamilyCtor2;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = createFromFamiliesWithDefaultMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
            }
            try {
                Object familyArray = Array.newInstance(fontFamilyClass, 1);
                createFromFamiliesWithDefaultMethod3 = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, familyArray.getClass(), Integer.TYPE, Integer.TYPE);
                createFromFamiliesWithDefaultMethod3.setAccessible(true);
                createFromFamiliesWithDefaultMethod2 = createFromFamiliesWithDefaultMethod3;
                createFromFamiliesWithDefaultMethod = addFromBufferMethod;
            } catch (ClassNotFoundException | NoSuchMethodException e6) {
                e = e6;
                Log.m69e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor2 = null;
                addFontMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod2 = null;
                sFontFamilyCtor = fontFamilyCtor2;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = createFromFamiliesWithDefaultMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
            }
        } catch (ClassNotFoundException | NoSuchMethodException e7) {
            e = e7;
            constructor = null;
            constructor2 = constructor;
            Log.m69e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            fontFamilyClass = null;
            fontFamilyCtor2 = null;
            addFontMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod2 = null;
            sFontFamilyCtor = fontFamilyCtor2;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = createFromFamiliesWithDefaultMethod;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
        }
        sFontFamilyCtor = fontFamilyCtor2;
        sFontFamily = fontFamilyClass;
        sAddFontFromAssetManager = addFontMethod;
        sAddFontFromBuffer = createFromFamiliesWithDefaultMethod;
        sFreeze = freezeMethod;
        sAbortCreation = abortCreationMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.m64w(TAG, "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style) {
        try {
            Boolean result = (Boolean) sAddFontFromAssetManager.invoke(family, context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), null);
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            Boolean result = (Boolean) sAddFontFromBuffer.invoke(family, buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style));
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(sFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, familyArray, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean freeze(Object family) {
        try {
            Boolean result = (Boolean) sFreeze.invoke(family, new Object[0]);
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean abortCreation(Object family) {
        try {
            Boolean result = (Boolean) sAbortCreation.invoke(family, new Object[0]);
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.support.p011v4.graphics.TypefaceCompatBaseImpl, android.support.p011v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        FontResourcesParserCompat.FontFileResourceEntry[] entries;
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFile : entry.getEntries()) {
            if (!addFontFromAssetManager(context, fontFamily, fontFile.getFileName(), 0, fontFile.getWeight(), fontFile.isItalic() ? 1 : 0)) {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (freeze(fontFamily)) {
            return createFromFamiliesWithDefault(fontFamily);
        }
        return null;
    }

    @Override // android.support.p011v4.graphics.TypefaceCompatApi21Impl, android.support.p011v4.graphics.TypefaceCompatBaseImpl, android.support.p011v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fonts, int style) {
        if (fonts.length < 1) {
            return null;
        }
        if (isFontFamilyPrivateAPIAvailable()) {
            Map<Uri, ByteBuffer> uriBuffer = FontsContractCompat.prepareFontData(context, fonts, cancellationSignal);
            Object fontFamily = newFamily();
            boolean atLeastOneFont = false;
            for (FontsContractCompat.FontInfo font : fonts) {
                ByteBuffer fontBuffer = uriBuffer.get(font.getUri());
                if (fontBuffer != null) {
                    boolean success = addFontFromBuffer(fontFamily, fontBuffer, font.getTtcIndex(), font.getWeight(), font.isItalic() ? 1 : 0);
                    if (!success) {
                        abortCreation(fontFamily);
                        return null;
                    }
                    atLeastOneFont = true;
                }
            }
            if (!atLeastOneFont) {
                abortCreation(fontFamily);
                return null;
            } else if (freeze(fontFamily)) {
                return createFromFamiliesWithDefault(fontFamily);
            } else {
                return null;
            }
        }
        FontsContractCompat.FontInfo bestFont = findBestInfo(fonts, style);
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            Typeface build = new Typeface.Builder(pfd.getFileDescriptor()).setWeight(bestFont.getWeight()).setItalic(bestFont.isItalic()).build();
            if (pfd != null) {
                pfd.close();
            }
            return build;
        } catch (IOException e) {
            return null;
        }
    }

    @Override // android.support.p011v4.graphics.TypefaceCompatBaseImpl, android.support.p011v4.graphics.TypefaceCompat.TypefaceCompatImpl
    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1)) {
            abortCreation(fontFamily);
            return null;
        } else if (freeze(fontFamily)) {
            return createFromFamiliesWithDefault(fontFamily);
        } else {
            return null;
        }
    }
}
