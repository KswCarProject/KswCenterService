package android.graphics.fonts;

import android.graphics.FontListParser;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontCustomizationParser;
import android.graphics.fonts.FontFamily;
import android.text.FontConfig;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public final class SystemFonts {
    private static final String DEFAULT_FAMILY = "sans-serif";
    private static final String TAG = "SystemFonts";
    private static final FontConfig.Alias[] sAliases;
    private static final List<Font> sAvailableFonts;
    private static final Map<String, FontFamily[]> sSystemFallbackMap;

    private SystemFonts() {
    }

    public static Set<Font> getAvailableFonts() {
        HashSet<Font> set = new HashSet<>();
        set.addAll(sAvailableFonts);
        return set;
    }

    public static FontFamily[] getSystemFallback(String familyName) {
        FontFamily[] families = sSystemFallbackMap.get(familyName);
        return families == null ? sSystemFallbackMap.get(DEFAULT_FAMILY) : families;
    }

    public static Map<String, FontFamily[]> getRawSystemFallbackMap() {
        return sSystemFallbackMap;
    }

    public static FontConfig.Alias[] getAliases() {
        return sAliases;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r8 = r3;
        r3 = r2;
        r2 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        r3 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.nio.ByteBuffer mmap(java.lang.String r9) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0027 }
            r1.<init>(r9)     // Catch:{ IOException -> 0x0027 }
            java.nio.channels.FileChannel r2 = r1.getChannel()     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            long r6 = r2.size()     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            java.nio.channels.FileChannel$MapMode r3 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            r4 = 0
            java.nio.MappedByteBuffer r3 = r2.map(r3, r4, r6)     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            $closeResource(r0, r1)     // Catch:{ IOException -> 0x0027 }
            return r3
        L_0x001a:
            r2 = move-exception
            r3 = r0
            goto L_0x0023
        L_0x001d:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x001f }
        L_0x001f:
            r3 = move-exception
            r8 = r3
            r3 = r2
            r2 = r8
        L_0x0023:
            $closeResource(r3, r1)     // Catch:{ IOException -> 0x0027 }
            throw r2     // Catch:{ IOException -> 0x0027 }
        L_0x0027:
            r1 = move-exception
            java.lang.String r2 = "SystemFonts"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error mapping font file "
            r3.append(r4)
            r3.append(r9)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.fonts.SystemFonts.mmap(java.lang.String):java.nio.ByteBuffer");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    private static void pushFamilyToFallback(FontConfig.Family xmlFamily, ArrayMap<String, ArrayList<FontFamily>> fallbackMap, Map<String, ByteBuffer> cache, ArrayList<Font> availableFonts) {
        ArrayMap<String, ArrayList<FontFamily>> arrayMap = fallbackMap;
        String languageTags = xmlFamily.getLanguages();
        int variant = xmlFamily.getVariant();
        ArrayList<FontConfig.Font> defaultFonts = new ArrayList<>();
        ArrayMap arrayMap2 = new ArrayMap();
        for (FontConfig.Font font : xmlFamily.getFonts()) {
            String fallbackName = font.getFallbackFor();
            if (fallbackName == null) {
                defaultFonts.add(font);
            } else {
                ArrayList<FontConfig.Font> fallback = (ArrayList) arrayMap2.get(fallbackName);
                if (fallback == null) {
                    fallback = new ArrayList<>();
                    arrayMap2.put(fallbackName, fallback);
                }
                fallback.add(font);
            }
        }
        FontFamily defaultFamily = defaultFonts.isEmpty() ? null : createFontFamily(xmlFamily.getName(), defaultFonts, languageTags, variant, cache, availableFonts);
        for (int i = 0; i < fallbackMap.size(); i++) {
            ArrayList<FontConfig.Font> fallback2 = (ArrayList) arrayMap2.get(fallbackMap.keyAt(i));
            if (fallback2 != null) {
                FontFamily family = createFontFamily(xmlFamily.getName(), fallback2, languageTags, variant, cache, availableFonts);
                if (family != null) {
                    fallbackMap.valueAt(i).add(family);
                } else if (defaultFamily != null) {
                    fallbackMap.valueAt(i).add(defaultFamily);
                }
            } else if (defaultFamily != null) {
                fallbackMap.valueAt(i).add(defaultFamily);
            }
        }
    }

    private static FontFamily createFontFamily(String familyName, List<FontConfig.Font> fonts, String languageTags, int variant, Map<String, ByteBuffer> cache, ArrayList<Font> availableFonts) {
        if (fonts.size() == 0) {
            return null;
        }
        FontFamily.Builder b = null;
        int i = 0;
        while (i < fonts.size()) {
            FontConfig.Font fontConfig = fonts.get(i);
            String fullPath = fontConfig.getFontName();
            ByteBuffer buffer = cache.get(fullPath);
            if (buffer == null) {
                if (!cache.containsKey(fullPath)) {
                    buffer = mmap(fullPath);
                    cache.put(fullPath, buffer);
                    if (buffer == null) {
                    }
                }
                i++;
            }
            try {
                Font font = new Font.Builder(buffer, new File(fullPath), languageTags).setWeight(fontConfig.getWeight()).setSlant(fontConfig.isItalic() ? 1 : 0).setTtcIndex(fontConfig.getTtcIndex()).setFontVariationSettings(fontConfig.getAxes()).build();
                availableFonts.add(font);
                if (b == null) {
                    b = new FontFamily.Builder(font);
                } else {
                    b.addFont(font);
                }
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (b == null) {
            return null;
        }
        return b.build(languageTags, variant, false);
    }

    private static void appendNamedFamily(FontConfig.Family xmlFamily, HashMap<String, ByteBuffer> bufferCache, ArrayMap<String, ArrayList<FontFamily>> fallbackListMap, ArrayList<Font> availableFonts) {
        String familyName = xmlFamily.getName();
        FontFamily family = createFontFamily(familyName, Arrays.asList(xmlFamily.getFonts()), xmlFamily.getLanguages(), xmlFamily.getVariant(), bufferCache, availableFonts);
        if (family != null) {
            ArrayList<FontFamily> fallback = new ArrayList<>();
            fallback.add(family);
            fallbackListMap.put(familyName, fallback);
        }
    }

    @VisibleForTesting
    public static FontConfig.Alias[] buildSystemFallback(String xmlPath, String fontDir, FontCustomizationParser.Result oemCustomization, ArrayMap<String, FontFamily[]> fallbackMap, ArrayList<Font> availableFonts) {
        try {
            FontConfig fontConfig = FontListParser.parse(new FileInputStream(xmlPath), fontDir);
            HashMap<String, ByteBuffer> bufferCache = new HashMap<>();
            FontConfig.Family[] xmlFamilies = fontConfig.getFamilies();
            ArrayMap<String, ArrayList<FontFamily>> fallbackListMap = new ArrayMap<>();
            int i = 0;
            for (FontConfig.Family xmlFamily : xmlFamilies) {
                if (xmlFamily.getName() != null) {
                    appendNamedFamily(xmlFamily, bufferCache, fallbackListMap, availableFonts);
                }
            }
            for (int i2 = 0; i2 < oemCustomization.mAdditionalNamedFamilies.size(); i2++) {
                appendNamedFamily(oemCustomization.mAdditionalNamedFamilies.get(i2), bufferCache, fallbackListMap, availableFonts);
            }
            for (int i3 = 0; i3 < xmlFamilies.length; i3++) {
                FontConfig.Family xmlFamily2 = xmlFamilies[i3];
                if (i3 == 0 || xmlFamily2.getName() == null) {
                    pushFamilyToFallback(xmlFamily2, fallbackListMap, bufferCache, availableFonts);
                }
            }
            while (true) {
                int i4 = i;
                if (i4 < fallbackListMap.size()) {
                    List<FontFamily> familyList = fallbackListMap.valueAt(i4);
                    fallbackMap.put(fallbackListMap.keyAt(i4), (FontFamily[]) familyList.toArray(new FontFamily[familyList.size()]));
                    i = i4 + 1;
                } else {
                    ArrayList<FontConfig.Alias> list = new ArrayList<>();
                    list.addAll(Arrays.asList(fontConfig.getAliases()));
                    list.addAll(oemCustomization.mAdditionalAliases);
                    return (FontConfig.Alias[]) list.toArray(new FontConfig.Alias[list.size()]);
                }
            }
        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Failed initialize system fallbacks.", e);
            return (FontConfig.Alias[]) ArrayUtils.emptyArray(FontConfig.Alias.class);
        }
    }

    private static FontCustomizationParser.Result readFontCustomization(String customizeXml, String customFontsDir) {
        FileInputStream f;
        try {
            f = new FileInputStream(customizeXml);
            FontCustomizationParser.Result parse = FontCustomizationParser.parse(f, customFontsDir);
            $closeResource((Throwable) null, f);
            return parse;
        } catch (IOException e) {
            return new FontCustomizationParser.Result();
        } catch (XmlPullParserException e2) {
            Log.e(TAG, "Failed to parse font customization XML", e2);
            return new FontCustomizationParser.Result();
        } catch (Throwable th) {
            $closeResource(r1, f);
            throw th;
        }
    }

    static {
        ArrayMap<String, FontFamily[]> systemFallbackMap = new ArrayMap<>();
        ArrayList<Font> availableFonts = new ArrayList<>();
        sAliases = buildSystemFallback("/system/etc/fonts.xml", "/system/fonts/", readFontCustomization("/product/etc/fonts_customization.xml", "/product/fonts/"), systemFallbackMap, availableFonts);
        sSystemFallbackMap = Collections.unmodifiableMap(systemFallbackMap);
        sAvailableFonts = Collections.unmodifiableList(availableFonts);
    }
}
