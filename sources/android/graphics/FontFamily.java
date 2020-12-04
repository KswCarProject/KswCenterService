package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.graphics.fonts.FontVariationAxis;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import libcore.util.NativeAllocationRegistry;

@Deprecated
public class FontFamily {
    private static String TAG = "FontFamily";
    private static final NativeAllocationRegistry sBuilderRegistry = NativeAllocationRegistry.createMalloced(FontFamily.class.getClassLoader(), nGetBuilderReleaseFunc());
    private static final NativeAllocationRegistry sFamilyRegistry = NativeAllocationRegistry.createMalloced(FontFamily.class.getClassLoader(), nGetFamilyReleaseFunc());
    private long mBuilderPtr;
    private Runnable mNativeBuilderCleaner;
    @UnsupportedAppUsage(trackingBug = 123768928)
    public long mNativePtr;

    private static native void nAddAxisValue(long j, int i, float f);

    private static native boolean nAddFont(long j, ByteBuffer byteBuffer, int i, int i2, int i3);

    private static native boolean nAddFontFromAssetManager(long j, AssetManager assetManager, String str, int i, boolean z, int i2, int i3, int i4);

    private static native boolean nAddFontWeightStyle(long j, ByteBuffer byteBuffer, int i, int i2, int i3);

    private static native long nCreateFamily(long j);

    private static native long nGetBuilderReleaseFunc();

    private static native long nGetFamilyReleaseFunc();

    private static native long nInitBuilder(String str, int i);

    @UnsupportedAppUsage(trackingBug = 123768928)
    public FontFamily() {
        this.mBuilderPtr = nInitBuilder((String) null, 0);
        this.mNativeBuilderCleaner = sBuilderRegistry.registerNativeAllocation(this, this.mBuilderPtr);
    }

    @UnsupportedAppUsage(trackingBug = 123768928)
    public FontFamily(String[] langs, int variant) {
        String langsString;
        if (langs == null || langs.length == 0) {
            langsString = null;
        } else if (langs.length == 1) {
            langsString = langs[0];
        } else {
            langsString = TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Object[]) langs);
        }
        this.mBuilderPtr = nInitBuilder(langsString, variant);
        this.mNativeBuilderCleaner = sBuilderRegistry.registerNativeAllocation(this, this.mBuilderPtr);
    }

    @UnsupportedAppUsage(trackingBug = 123768928)
    public boolean freeze() {
        if (this.mBuilderPtr != 0) {
            this.mNativePtr = nCreateFamily(this.mBuilderPtr);
            this.mNativeBuilderCleaner.run();
            this.mBuilderPtr = 0;
            if (this.mNativePtr != 0) {
                sFamilyRegistry.registerNativeAllocation(this, this.mNativePtr);
            }
            return this.mNativePtr != 0;
        }
        throw new IllegalStateException("This FontFamily is already frozen");
    }

    @UnsupportedAppUsage(trackingBug = 123768928)
    public void abortCreation() {
        if (this.mBuilderPtr != 0) {
            this.mNativeBuilderCleaner.run();
            this.mBuilderPtr = 0;
            return;
        }
        throw new IllegalStateException("This FontFamily is already frozen or abandoned");
    }

    @UnsupportedAppUsage(trackingBug = 123768928)
    public boolean addFont(String path, int ttcIndex, FontVariationAxis[] axes, int weight, int italic) {
        FileInputStream file;
        Throwable th;
        Throwable th2;
        Throwable th3;
        String str = path;
        FontVariationAxis[] fontVariationAxisArr = axes;
        if (this.mBuilderPtr != 0) {
            try {
                file = new FileInputStream(str);
                try {
                    FileChannel fileChannel = file.getChannel();
                    ByteBuffer fontBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
                    if (fontVariationAxisArr != null) {
                        for (FontVariationAxis axis : fontVariationAxisArr) {
                            nAddAxisValue(this.mBuilderPtr, axis.getOpenTypeTagValue(), axis.getStyleValue());
                        }
                    }
                    boolean nAddFont = nAddFont(this.mBuilderPtr, fontBuffer, ttcIndex, weight, italic);
                    file.close();
                    return nAddFont;
                } catch (Throwable th4) {
                    th = th3;
                    th2 = th4;
                }
            } catch (IOException e) {
                Log.e(TAG, "Error mapping font file " + str);
                return false;
            }
        } else {
            throw new IllegalStateException("Unable to call addFont after freezing.");
        }
        throw th2;
        if (th != null) {
            try {
                file.close();
            } catch (Throwable th5) {
                th.addSuppressed(th5);
            }
        } else {
            file.close();
        }
        throw th2;
    }

    @UnsupportedAppUsage(trackingBug = 123768928)
    public boolean addFontFromBuffer(ByteBuffer font, int ttcIndex, FontVariationAxis[] axes, int weight, int italic) {
        if (this.mBuilderPtr != 0) {
            if (axes != null) {
                for (FontVariationAxis axis : axes) {
                    nAddAxisValue(this.mBuilderPtr, axis.getOpenTypeTagValue(), axis.getStyleValue());
                }
            }
            return nAddFontWeightStyle(this.mBuilderPtr, font, ttcIndex, weight, italic);
        }
        throw new IllegalStateException("Unable to call addFontWeightStyle after freezing.");
    }

    @UnsupportedAppUsage(trackingBug = 123768928)
    public boolean addFontFromAssetManager(AssetManager mgr, String path, int cookie, boolean isAsset, int ttcIndex, int weight, int isItalic, FontVariationAxis[] axes) {
        FontVariationAxis[] fontVariationAxisArr = axes;
        if (this.mBuilderPtr != 0) {
            if (fontVariationAxisArr != null) {
                for (FontVariationAxis axis : fontVariationAxisArr) {
                    nAddAxisValue(this.mBuilderPtr, axis.getOpenTypeTagValue(), axis.getStyleValue());
                }
            }
            return nAddFontFromAssetManager(this.mBuilderPtr, mgr, path, cookie, isAsset, ttcIndex, weight, isItalic);
        }
        throw new IllegalStateException("Unable to call addFontFromAsset after freezing.");
    }

    private static boolean nAddFont(long builderPtr, ByteBuffer font, int ttcIndex) {
        return nAddFont(builderPtr, font, ttcIndex, -1, -1);
    }
}
