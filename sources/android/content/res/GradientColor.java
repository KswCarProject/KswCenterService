package android.content.res;

import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.net.wifi.WifiEnterpriseConfig;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class GradientColor extends ComplexColor {
    private static final boolean DBG_GRADIENT = false;
    private static final String TAG = "GradientColor";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private int mCenterColor = 0;
    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;
    /* access modifiers changed from: private */
    public int mChangingConfigurations;
    private int mDefaultColor;
    private int mEndColor = 0;
    private float mEndX = 0.0f;
    private float mEndY = 0.0f;
    private GradientColorFactory mFactory;
    private float mGradientRadius = 0.0f;
    private int mGradientType = 0;
    private boolean mHasCenterColor = false;
    private int[] mItemColors;
    private float[] mItemOffsets;
    private int[][] mItemsThemeAttrs;
    private Shader mShader = null;
    private int mStartColor = 0;
    private float mStartX = 0.0f;
    private float mStartY = 0.0f;
    private int[] mThemeAttrs;
    private int mTileMode = 0;

    @Retention(RetentionPolicy.SOURCE)
    private @interface GradientTileMode {
    }

    private GradientColor() {
    }

    private GradientColor(GradientColor copy) {
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

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0012  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.res.GradientColor createFromXml(android.content.res.Resources r4, android.content.res.XmlResourceParser r5, android.content.res.Resources.Theme r6) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r5)
        L_0x0004:
            int r1 = r5.next()
            r2 = r1
            r3 = 2
            if (r1 == r3) goto L_0x0010
            r1 = 1
            if (r2 == r1) goto L_0x0010
            goto L_0x0004
        L_0x0010:
            if (r2 != r3) goto L_0x0017
            android.content.res.GradientColor r1 = createFromXmlInner(r4, r5, r0, r6)
            return r1
        L_0x0017:
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r3 = "No start tag found"
            r1.<init>(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.GradientColor.createFromXml(android.content.res.Resources, android.content.res.XmlResourceParser, android.content.res.Resources$Theme):android.content.res.GradientColor");
    }

    static GradientColor createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (name.equals("gradient")) {
            GradientColor gradientColor = new GradientColor();
            gradientColor.inflate(r, parser, attrs, theme);
            return gradientColor;
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid gradient color tag " + name);
    }

    private void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a = Resources.obtainAttributes(r, theme, attrs, R.styleable.GradientColor);
        updateRootElementState(a);
        this.mChangingConfigurations |= a.getChangingConfigurations();
        a.recycle();
        validateXmlContent();
        inflateChildElements(r, parser, attrs, theme);
        onColorsChange();
    }

    /* JADX WARNING: type inference failed for: r10v4, types: [java.lang.Object[]] */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00c1, code lost:
        throw new org.xmlpull.v1.XmlPullParserException(r22.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void inflateChildElements(android.content.res.Resources r21, org.xmlpull.v1.XmlPullParser r22, android.util.AttributeSet r23, android.content.res.Resources.Theme r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r20 = this;
            r0 = r20
            int r1 = r22.getDepth()
            r2 = 1
            int r1 = r1 + r2
            r3 = 20
            float[] r3 = new float[r3]
            int r4 = r3.length
            int[] r4 = new int[r4]
            int r5 = r3.length
            int[][] r5 = new int[r5][]
            r6 = 0
            r7 = 0
            r8 = r3
            r3 = r7
        L_0x0016:
            int r9 = r22.next()
            r10 = r9
            if (r9 == r2) goto L_0x00d0
            int r9 = r22.getDepth()
            r11 = r9
            if (r9 >= r1) goto L_0x0034
            r9 = 3
            if (r10 == r9) goto L_0x0028
            goto L_0x0034
        L_0x0028:
            r12 = r21
            r13 = r23
            r14 = r24
            r18 = r1
            r19 = r10
            goto L_0x00da
        L_0x0034:
            r9 = 2
            if (r10 == r9) goto L_0x0042
        L_0x0038:
            r12 = r21
            r13 = r23
            r14 = r24
            r18 = r1
            goto L_0x00ca
        L_0x0042:
            if (r11 > r1) goto L_0x00c2
            java.lang.String r9 = r22.getName()
            java.lang.String r12 = "item"
            boolean r9 = r9.equals(r12)
            if (r9 != 0) goto L_0x0051
            goto L_0x0038
        L_0x0051:
            int[] r9 = com.android.internal.R.styleable.GradientColorItem
            r12 = r21
            r13 = r23
            r14 = r24
            android.content.res.TypedArray r9 = android.content.res.Resources.obtainAttributes(r12, r14, r13, r9)
            boolean r15 = r9.hasValue(r7)
            boolean r16 = r9.hasValue(r2)
            if (r15 == 0) goto L_0x00a3
            if (r16 == 0) goto L_0x00a3
            int[] r2 = r9.extractThemeAttrs()
            r18 = r1
            int r1 = r9.getColor(r7, r7)
            r7 = 0
            r19 = r10
            r10 = 1
            float r7 = r9.getFloat(r10, r7)
            int r10 = r0.mChangingConfigurations
            int r17 = r9.getChangingConfigurations()
            r10 = r10 | r17
            r0.mChangingConfigurations = r10
            r9.recycle()
            if (r2 == 0) goto L_0x008b
            r3 = 1
        L_0x008b:
            int[] r4 = com.android.internal.util.GrowingArrayUtils.append((int[]) r4, (int) r6, (int) r1)
            float[] r8 = com.android.internal.util.GrowingArrayUtils.append((float[]) r8, (int) r6, (float) r7)
            java.lang.Object[] r10 = com.android.internal.util.GrowingArrayUtils.append((T[]) r5, (int) r6, r2)
            r5 = r10
            int[][] r5 = (int[][]) r5
            int r6 = r6 + 1
            r1 = r18
            r2 = 1
            r7 = 0
            goto L_0x0016
        L_0x00a3:
            r18 = r1
            r19 = r10
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r7 = r22.getPositionDescription()
            r2.append(r7)
            java.lang.String r7 = ": <item> tag requires a 'color' attribute and a 'offset' attribute!"
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00c2:
            r12 = r21
            r13 = r23
            r14 = r24
            r18 = r1
        L_0x00ca:
            r1 = r18
            r2 = 1
            r7 = 0
            goto L_0x0016
        L_0x00d0:
            r12 = r21
            r13 = r23
            r14 = r24
            r18 = r1
            r19 = r10
        L_0x00da:
            if (r6 <= 0) goto L_0x00ff
            if (r3 == 0) goto L_0x00e9
            int[][] r1 = new int[r6][]
            r0.mItemsThemeAttrs = r1
            int[][] r1 = r0.mItemsThemeAttrs
            r2 = 0
            java.lang.System.arraycopy(r5, r2, r1, r2, r6)
            goto L_0x00ec
        L_0x00e9:
            r1 = 0
            r0.mItemsThemeAttrs = r1
        L_0x00ec:
            int[] r1 = new int[r6]
            r0.mItemColors = r1
            float[] r1 = new float[r6]
            r0.mItemOffsets = r1
            int[] r1 = r0.mItemColors
            r2 = 0
            java.lang.System.arraycopy(r4, r2, r1, r2, r6)
            float[] r1 = r0.mItemOffsets
            java.lang.System.arraycopy(r8, r2, r1, r2, r6)
        L_0x00ff:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.GradientColor.inflateChildElements(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    private void applyItemsAttrsTheme(Resources.Theme t) {
        if (this.mItemsThemeAttrs != null) {
            int[][] themeAttrsList = this.mItemsThemeAttrs;
            int N = themeAttrsList.length;
            boolean hasUnresolvedAttrs = false;
            for (int i = 0; i < N; i++) {
                if (themeAttrsList[i] != null) {
                    TypedArray a = t.resolveAttributes(themeAttrsList[i], R.styleable.GradientColorItem);
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
            Log.w(TAG, "<gradient> tag requires 2 color values specified!" + tempColors.length + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + tempColors);
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

    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new GradientColorFactory(this);
        }
        return this.mFactory;
    }

    private static class GradientColorFactory extends ConstantState<ComplexColor> {
        private final GradientColor mSrc;

        public GradientColorFactory(GradientColor src) {
            this.mSrc = src;
        }

        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        public GradientColor newInstance() {
            return this.mSrc;
        }

        public GradientColor newInstance(Resources res, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    public GradientColor obtainForTheme(Resources.Theme t) {
        if (t == null || !canApplyTheme()) {
            return this;
        }
        GradientColor clone = new GradientColor(this);
        clone.applyTheme(t);
        return clone;
    }

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
        TypedArray a = t.resolveAttributes(this.mThemeAttrs, R.styleable.GradientColor);
        this.mThemeAttrs = a.extractThemeAttrs(this.mThemeAttrs);
        updateRootElementState(a);
        this.mChangingConfigurations |= a.getChangingConfigurations();
        a.recycle();
    }

    public boolean canApplyTheme() {
        return (this.mThemeAttrs == null && this.mItemsThemeAttrs == null) ? false : true;
    }
}
