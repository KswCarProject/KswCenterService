package android.content.res;

import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.net.wifi.WifiEnterpriseConfig;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.ims.ImsConfig;
import com.android.internal.C3132R;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class GradientColor extends ComplexColor {
    private static final boolean DBG_GRADIENT = false;
    private static final String TAG = "GradientColor";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private int mCenterColor;
    private float mCenterX;
    private float mCenterY;
    private int mChangingConfigurations;
    private int mDefaultColor;
    private int mEndColor;
    private float mEndX;
    private float mEndY;
    private GradientColorFactory mFactory;
    private float mGradientRadius;
    private int mGradientType;
    private boolean mHasCenterColor;
    private int[] mItemColors;
    private float[] mItemOffsets;
    private int[][] mItemsThemeAttrs;
    private Shader mShader;
    private int mStartColor;
    private float mStartX;
    private float mStartY;
    private int[] mThemeAttrs;
    private int mTileMode;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    private @interface GradientTileMode {
    }

    private GradientColor() {
        this.mShader = null;
        this.mGradientType = 0;
        this.mCenterX = 0.0f;
        this.mCenterY = 0.0f;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        this.mStartColor = 0;
        this.mCenterColor = 0;
        this.mEndColor = 0;
        this.mHasCenterColor = false;
        this.mTileMode = 0;
        this.mGradientRadius = 0.0f;
    }

    private GradientColor(GradientColor copy) {
        this.mShader = null;
        this.mGradientType = 0;
        this.mCenterX = 0.0f;
        this.mCenterY = 0.0f;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        this.mStartColor = 0;
        this.mCenterColor = 0;
        this.mEndColor = 0;
        this.mHasCenterColor = false;
        this.mTileMode = 0;
        this.mGradientRadius = 0.0f;
        if (copy != null) {
            this.mChangingConfigurations = copy.mChangingConfigurations;
            this.mDefaultColor = copy.mDefaultColor;
            this.mShader = copy.mShader;
            this.mGradientType = copy.mGradientType;
            this.mCenterX = copy.mCenterX;
            this.mCenterY = copy.mCenterY;
            this.mStartX = copy.mStartX;
            this.mStartY = copy.mStartY;
            this.mEndX = copy.mEndX;
            this.mEndY = copy.mEndY;
            this.mStartColor = copy.mStartColor;
            this.mCenterColor = copy.mCenterColor;
            this.mEndColor = copy.mEndColor;
            this.mHasCenterColor = copy.mHasCenterColor;
            this.mGradientRadius = copy.mGradientRadius;
            this.mTileMode = copy.mTileMode;
            if (copy.mItemColors != null) {
                this.mItemColors = (int[]) copy.mItemColors.clone();
            }
            if (copy.mItemOffsets != null) {
                this.mItemOffsets = (float[]) copy.mItemOffsets.clone();
            }
            if (copy.mThemeAttrs != null) {
                this.mThemeAttrs = (int[]) copy.mThemeAttrs.clone();
            }
            if (copy.mItemsThemeAttrs != null) {
                this.mItemsThemeAttrs = (int[][]) copy.mItemsThemeAttrs.clone();
            }
        }
    }

    private static Shader.TileMode parseTileMode(int tileMode) {
        switch (tileMode) {
            case 0:
                return Shader.TileMode.CLAMP;
            case 1:
                return Shader.TileMode.REPEAT;
            case 2:
                return Shader.TileMode.MIRROR;
            default:
                return Shader.TileMode.CLAMP;
        }
    }

    private void updateRootElementState(TypedArray a) {
        this.mThemeAttrs = a.extractThemeAttrs();
        this.mStartX = a.getFloat(8, this.mStartX);
        this.mStartY = a.getFloat(9, this.mStartY);
        this.mEndX = a.getFloat(10, this.mEndX);
        this.mEndY = a.getFloat(11, this.mEndY);
        this.mCenterX = a.getFloat(3, this.mCenterX);
        this.mCenterY = a.getFloat(4, this.mCenterY);
        this.mGradientType = a.getInt(2, this.mGradientType);
        this.mStartColor = a.getColor(0, this.mStartColor);
        this.mHasCenterColor |= a.hasValue(7);
        this.mCenterColor = a.getColor(7, this.mCenterColor);
        this.mEndColor = a.getColor(1, this.mEndColor);
        this.mTileMode = a.getInt(6, this.mTileMode);
        this.mGradientRadius = a.getFloat(5, this.mGradientRadius);
    }

    private void validateXmlContent() throws XmlPullParserException {
        if (this.mGradientRadius <= 0.0f && this.mGradientType == 1) {
            throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }
    }

    public Shader getShader() {
        return this.mShader;
    }

    public static GradientColor createFromXml(Resources r, XmlResourceParser parser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int type;
        AttributeSet attrs = Xml.asAttributeSet(parser);
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(r, parser, attrs, theme);
    }

    static GradientColor createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (!name.equals("gradient")) {
            throw new XmlPullParserException(parser.getPositionDescription() + ": invalid gradient color tag " + name);
        }
        GradientColor gradientColor = new GradientColor();
        gradientColor.inflate(r, parser, attrs, theme);
        return gradientColor;
    }

    private void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a = Resources.obtainAttributes(r, theme, attrs, C3132R.styleable.GradientColor);
        updateRootElementState(a);
        this.mChangingConfigurations |= a.getChangingConfigurations();
        a.recycle();
        validateXmlContent();
        inflateChildElements(r, parser, attrs, theme);
        onColorsChange();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00da, code lost:
        if (r6 <= 0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00dc, code lost:
        if (r3 == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00de, code lost:
        r20.mItemsThemeAttrs = new int[r6];
        java.lang.System.arraycopy(r5, 0, r20.mItemsThemeAttrs, 0, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00e9, code lost:
        r20.mItemsThemeAttrs = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ec, code lost:
        r20.mItemColors = new int[r6];
        r20.mItemOffsets = new float[r6];
        java.lang.System.arraycopy(r4, 0, r20.mItemColors, 0, r6);
        java.lang.System.arraycopy(r8, 0, r20.mItemOffsets, 0, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ff, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void inflateChildElements(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int i = 1;
        int innerDepth = parser.getDepth() + 1;
        float[] offsetList = new float[20];
        int[] colorList = new int[offsetList.length];
        int[][] themeAttrsList = new int[offsetList.length];
        int listSize = 0;
        int i2 = 0;
        float[] offsetList2 = offsetList;
        boolean hasUnresolvedAttrs = false;
        while (true) {
            int type = parser.next();
            if (type != i && ((depth = parser.getDepth()) >= innerDepth || type != 3)) {
                if (type == 2 && depth <= innerDepth && parser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                    TypedArray a = Resources.obtainAttributes(r, theme, attrs, C3132R.styleable.GradientColorItem);
                    boolean hasColor = a.hasValue(i2);
                    boolean hasOffset = a.hasValue(i);
                    if (!hasColor || !hasOffset) {
                        break;
                    }
                    int[] themeAttrs = a.extractThemeAttrs();
                    int innerDepth2 = innerDepth;
                    int color = a.getColor(i2, i2);
                    float offset = a.getFloat(1, 0.0f);
                    this.mChangingConfigurations |= a.getChangingConfigurations();
                    a.recycle();
                    if (themeAttrs != null) {
                        hasUnresolvedAttrs = true;
                    }
                    colorList = GrowingArrayUtils.append(colorList, listSize, color);
                    offsetList2 = GrowingArrayUtils.append(offsetList2, listSize, offset);
                    themeAttrsList = (int[][]) GrowingArrayUtils.append(themeAttrsList, listSize, themeAttrs);
                    listSize++;
                    innerDepth = innerDepth2;
                    i = 1;
                    i2 = 0;
                } else {
                    innerDepth = innerDepth;
                    i = 1;
                    i2 = 0;
                }
            }
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
    }

    private void applyItemsAttrsTheme(Resources.Theme t) {
        if (this.mItemsThemeAttrs == null) {
            return;
        }
        int[][] themeAttrsList = this.mItemsThemeAttrs;
        int N = themeAttrsList.length;
        boolean hasUnresolvedAttrs = false;
        for (int i = 0; i < N; i++) {
            if (themeAttrsList[i] != null) {
                TypedArray a = t.resolveAttributes(themeAttrsList[i], C3132R.styleable.GradientColorItem);
                themeAttrsList[i] = a.extractThemeAttrs(themeAttrsList[i]);
                if (themeAttrsList[i] != null) {
                    hasUnresolvedAttrs = true;
                }
                this.mItemColors[i] = a.getColor(0, this.mItemColors[i]);
                this.mItemOffsets[i] = a.getFloat(1, this.mItemOffsets[i]);
                this.mChangingConfigurations |= a.getChangingConfigurations();
                a.recycle();
            }
        }
        if (!hasUnresolvedAttrs) {
            this.mItemsThemeAttrs = null;
        }
    }

    private void onColorsChange() {
        int[] tempColors;
        float[] tempOffsets = null;
        if (this.mItemColors != null) {
            int length = this.mItemColors.length;
            tempColors = new int[length];
            tempOffsets = new float[length];
            for (int i = 0; i < length; i++) {
                tempColors[i] = this.mItemColors[i];
                tempOffsets[i] = this.mItemOffsets[i];
            }
        } else if (this.mHasCenterColor) {
            tempColors = new int[]{this.mStartColor, this.mCenterColor, this.mEndColor};
            tempOffsets = new float[]{0.0f, 0.5f, 1.0f};
        } else {
            tempColors = new int[]{this.mStartColor, this.mEndColor};
        }
        if (tempColors.length < 2) {
            Log.m64w(TAG, "<gradient> tag requires 2 color values specified!" + tempColors.length + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + tempColors);
        }
        if (this.mGradientType == 0) {
            this.mShader = new LinearGradient(this.mStartX, this.mStartY, this.mEndX, this.mEndY, tempColors, tempOffsets, parseTileMode(this.mTileMode));
        } else if (this.mGradientType == 1) {
            this.mShader = new RadialGradient(this.mCenterX, this.mCenterY, this.mGradientRadius, tempColors, tempOffsets, parseTileMode(this.mTileMode));
        } else {
            this.mShader = new SweepGradient(this.mCenterX, this.mCenterY, tempColors, tempOffsets);
        }
        this.mDefaultColor = tempColors[0];
    }

    @Override // android.content.res.ComplexColor
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    @Override // android.content.res.ComplexColor
    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new GradientColorFactory(this);
        }
        return this.mFactory;
    }

    /* loaded from: classes.dex */
    private static class GradientColorFactory extends ConstantState<ComplexColor> {
        private final GradientColor mSrc;

        public GradientColorFactory(GradientColor src) {
            this.mSrc = src;
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public ComplexColor newInstance2() {
            return this.mSrc;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public ComplexColor newInstance2(Resources res, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    @Override // android.content.res.ComplexColor
    public GradientColor obtainForTheme(Resources.Theme t) {
        if (t == null || !canApplyTheme()) {
            return this;
        }
        GradientColor clone = new GradientColor(this);
        clone.applyTheme(t);
        return clone;
    }

    @Override // android.content.res.ComplexColor
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    private void applyTheme(Resources.Theme t) {
        if (this.mThemeAttrs != null) {
            applyRootAttrsTheme(t);
        }
        if (this.mItemsThemeAttrs != null) {
            applyItemsAttrsTheme(t);
        }
        onColorsChange();
    }

    private void applyRootAttrsTheme(Resources.Theme t) {
        TypedArray a = t.resolveAttributes(this.mThemeAttrs, C3132R.styleable.GradientColor);
        this.mThemeAttrs = a.extractThemeAttrs(this.mThemeAttrs);
        updateRootElementState(a);
        this.mChangingConfigurations |= a.getChangingConfigurations();
        a.recycle();
    }

    @Override // android.content.res.ComplexColor
    public boolean canApplyTheme() {
        return (this.mThemeAttrs == null && this.mItemsThemeAttrs == null) ? false : true;
    }
}
