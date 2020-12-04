package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import android.util.StateSet;
import com.android.internal.R;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ColorStateList extends ComplexColor implements Parcelable {
    public static final Parcelable.Creator<ColorStateList> CREATOR = new Parcelable.Creator<ColorStateList>() {
        public ColorStateList[] newArray(int size) {
            return new ColorStateList[size];
        }

        public ColorStateList createFromParcel(Parcel source) {
            int N = source.readInt();
            int[][] stateSpecs = new int[N][];
            for (int i = 0; i < N; i++) {
                stateSpecs[i] = source.createIntArray();
            }
            return new ColorStateList(stateSpecs, source.createIntArray());
        }
    };
    private static final int DEFAULT_COLOR = -65536;
    private static final int[][] EMPTY = {new int[0]};
    private static final String TAG = "ColorStateList";
    private static final SparseArray<WeakReference<ColorStateList>> sCache = new SparseArray<>();
    /* access modifiers changed from: private */
    public int mChangingConfigurations;
    @UnsupportedAppUsage
    private int[] mColors;
    @UnsupportedAppUsage
    private int mDefaultColor;
    @UnsupportedAppUsage
    private ColorStateListFactory mFactory;
    private boolean mIsOpaque;
    @UnsupportedAppUsage
    private int[][] mStateSpecs;
    private int[][] mThemeAttrs;

    @UnsupportedAppUsage
    private ColorStateList() {
    }

    public ColorStateList(int[][] states, int[] colors) {
        this.mStateSpecs = states;
        this.mColors = colors;
        onColorsChanged();
    }

    public static ColorStateList valueOf(int color) {
        synchronized (sCache) {
            int index = sCache.indexOfKey(color);
            if (index >= 0) {
                ColorStateList cached = (ColorStateList) sCache.valueAt(index).get();
                if (cached != null) {
                    return cached;
                }
                sCache.removeAt(index);
            }
            for (int i = sCache.size() - 1; i >= 0; i--) {
                if (sCache.valueAt(i).get() == null) {
                    sCache.removeAt(i);
                }
            }
            ColorStateList csl = new ColorStateList(EMPTY, new int[]{color});
            sCache.put(color, new WeakReference(csl));
            return csl;
        }
    }

    private ColorStateList(ColorStateList orig) {
        if (orig != null) {
            this.mChangingConfigurations = orig.mChangingConfigurations;
            this.mStateSpecs = orig.mStateSpecs;
            this.mDefaultColor = orig.mDefaultColor;
            this.mIsOpaque = orig.mIsOpaque;
            this.mThemeAttrs = (int[][]) orig.mThemeAttrs.clone();
            this.mColors = (int[]) orig.mColors.clone();
        }
    }

    @Deprecated
    public static ColorStateList createFromXml(Resources r, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createFromXml(r, parser, (Resources.Theme) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0012  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.res.ColorStateList createFromXml(android.content.res.Resources r4, org.xmlpull.v1.XmlPullParser r5, android.content.res.Resources.Theme r6) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
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
            android.content.res.ColorStateList r1 = createFromXmlInner(r4, r5, r0, r6)
            return r1
        L_0x0017:
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r3 = "No start tag found"
            r1.<init>(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ColorStateList.createFromXml(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.content.res.Resources$Theme):android.content.res.ColorStateList");
    }

    static ColorStateList createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (name.equals("selector")) {
            ColorStateList colorStateList = new ColorStateList();
            colorStateList.inflate(r, parser, attrs, theme);
            return colorStateList;
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    public ColorStateList withAlpha(int alpha) {
        int[] colors = new int[this.mColors.length];
        int len = colors.length;
        for (int i = 0; i < len; i++) {
            colors[i] = (this.mColors[i] & 16777215) | (alpha << 24);
        }
        return new ColorStateList(this.mStateSpecs, colors);
    }

    /* JADX WARNING: type inference failed for: r14v3, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r14v4, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void inflate(android.content.res.Resources r26, org.xmlpull.v1.XmlPullParser r27, android.util.AttributeSet r28, android.content.res.Resources.Theme r29) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r25 = this;
            r0 = r25
            r1 = r28
            int r2 = r27.getDepth()
            r3 = 1
            int r2 = r2 + r3
            r4 = 0
            r5 = -65536(0xffffffffffff0000, float:NaN)
            r6 = 0
            java.lang.Class<int[]> r7 = int[].class
            r8 = 20
            java.lang.Object[] r7 = com.android.internal.util.ArrayUtils.newUnpaddedArray(r7, r8)
            int[][] r7 = (int[][]) r7
            int r8 = r7.length
            int[][] r8 = new int[r8][]
            int r9 = r7.length
            int[] r9 = new int[r9]
            r11 = r7
            r7 = r5
            r5 = r4
            r4 = 0
        L_0x0022:
            int r12 = r27.next()
            r13 = r12
            if (r12 == r3) goto L_0x00f3
            int r12 = r27.getDepth()
            r14 = r12
            if (r12 >= r2) goto L_0x003a
            r12 = 3
            if (r13 == r12) goto L_0x0034
            goto L_0x003a
        L_0x0034:
            r17 = r2
            r18 = r13
            goto L_0x00f7
        L_0x003a:
            r12 = 2
            if (r13 != r12) goto L_0x00ec
            if (r14 > r2) goto L_0x00ec
            java.lang.String r12 = r27.getName()
            java.lang.String r15 = "item"
            boolean r12 = r12.equals(r15)
            if (r12 != 0) goto L_0x0050
            r17 = r2
            goto L_0x00ee
        L_0x0050:
            int[] r12 = com.android.internal.R.styleable.ColorStateListItem
            r15 = r26
            r3 = r29
            android.content.res.TypedArray r12 = android.content.res.Resources.obtainAttributes(r15, r3, r1, r12)
            int[] r10 = r12.extractThemeAttrs()
            r17 = r2
            r2 = -65281(0xffffffffffff00ff, float:NaN)
            r3 = 0
            int r2 = r12.getColor(r3, r2)
            r3 = 1065353216(0x3f800000, float:1.0)
            r18 = r13
            r13 = 1
            float r3 = r12.getFloat(r13, r3)
            int r16 = r12.getChangingConfigurations()
            r5 = r5 | r16
            r12.recycle()
            r16 = 0
            int r13 = r28.getAttributeCount()
            r19 = r5
            int[] r5 = new int[r13]
            r20 = r12
            r12 = r16
            r16 = 0
        L_0x008a:
            r21 = r16
            r22 = r14
            r14 = r21
            if (r14 >= r13) goto L_0x00bf
            r23 = r13
            int r13 = r1.getAttributeNameResource(r14)
            r15 = 16843173(0x10101a5, float:2.3694738E-38)
            if (r13 == r15) goto L_0x00b5
            r15 = 16843551(0x101031f, float:2.3695797E-38)
            if (r13 == r15) goto L_0x00b5
            int r15 = r12 + 1
            r24 = r15
            r15 = 0
            boolean r16 = r1.getAttributeBooleanValue(r14, r15)
            if (r16 == 0) goto L_0x00af
            r15 = r13
            goto L_0x00b0
        L_0x00af:
            int r15 = -r13
        L_0x00b0:
            r5[r12] = r15
            r12 = r24
            goto L_0x00b6
        L_0x00b5:
        L_0x00b6:
            int r16 = r14 + 1
            r14 = r22
            r13 = r23
            r15 = r26
            goto L_0x008a
        L_0x00bf:
            r23 = r13
            int[] r5 = android.util.StateSet.trimStateSet(r5, r12)
            int r13 = r0.modulateColorAlpha(r2, r3)
            if (r4 == 0) goto L_0x00ce
            int r14 = r5.length
            if (r14 != 0) goto L_0x00cf
        L_0x00ce:
            r7 = r13
        L_0x00cf:
            if (r10 == 0) goto L_0x00d2
            r6 = 1
        L_0x00d2:
            int[] r9 = com.android.internal.util.GrowingArrayUtils.append((int[]) r9, (int) r4, (int) r13)
            java.lang.Object[] r14 = com.android.internal.util.GrowingArrayUtils.append((T[]) r8, (int) r4, r10)
            r8 = r14
            int[][] r8 = (int[][]) r8
            java.lang.Object[] r14 = com.android.internal.util.GrowingArrayUtils.append((T[]) r11, (int) r4, r5)
            r11 = r14
            int[][] r11 = (int[][]) r11
            int r4 = r4 + 1
            r2 = r17
            r5 = r19
            goto L_0x00f0
        L_0x00ec:
            r17 = r2
        L_0x00ee:
            r2 = r17
        L_0x00f0:
            r3 = 1
            goto L_0x0022
        L_0x00f3:
            r17 = r2
            r18 = r13
        L_0x00f7:
            r0.mChangingConfigurations = r5
            r0.mDefaultColor = r7
            if (r6 == 0) goto L_0x0108
            int[][] r2 = new int[r4][]
            r0.mThemeAttrs = r2
            int[][] r2 = r0.mThemeAttrs
            r3 = 0
            java.lang.System.arraycopy(r8, r3, r2, r3, r4)
            goto L_0x010b
        L_0x0108:
            r2 = 0
            r0.mThemeAttrs = r2
        L_0x010b:
            int[] r2 = new int[r4]
            r0.mColors = r2
            int[][] r2 = new int[r4][]
            r0.mStateSpecs = r2
            int[] r2 = r0.mColors
            r3 = 0
            java.lang.System.arraycopy(r9, r3, r2, r3, r4)
            int[][] r2 = r0.mStateSpecs
            java.lang.System.arraycopy(r11, r3, r2, r3, r4)
            r25.onColorsChanged()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ColorStateList.inflate(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    @UnsupportedAppUsage
    public boolean canApplyTheme() {
        return this.mThemeAttrs != null;
    }

    private void applyTheme(Resources.Theme t) {
        float defaultAlphaMod;
        if (this.mThemeAttrs != null) {
            int[][] themeAttrsList = this.mThemeAttrs;
            int N = themeAttrsList.length;
            boolean hasUnresolvedAttrs = false;
            for (int i = 0; i < N; i++) {
                if (themeAttrsList[i] != null) {
                    TypedArray a = t.resolveAttributes(themeAttrsList[i], R.styleable.ColorStateListItem);
                    if (themeAttrsList[i][0] != 0) {
                        defaultAlphaMod = ((float) Color.alpha(this.mColors[i])) / 255.0f;
                    } else {
                        defaultAlphaMod = 1.0f;
                    }
                    themeAttrsList[i] = a.extractThemeAttrs(themeAttrsList[i]);
                    if (themeAttrsList[i] != null) {
                        hasUnresolvedAttrs = true;
                    }
                    this.mColors[i] = modulateColorAlpha(a.getColor(0, this.mColors[i]), a.getFloat(1, defaultAlphaMod));
                    this.mChangingConfigurations |= a.getChangingConfigurations();
                    a.recycle();
                }
            }
            if (!hasUnresolvedAttrs) {
                this.mThemeAttrs = null;
            }
            onColorsChanged();
        }
    }

    @UnsupportedAppUsage
    public ColorStateList obtainForTheme(Resources.Theme t) {
        if (t == null || !canApplyTheme()) {
            return this;
        }
        ColorStateList clone = new ColorStateList(this);
        clone.applyTheme(t);
        return clone;
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    private int modulateColorAlpha(int baseColor, float alphaMod) {
        if (alphaMod == 1.0f) {
            return baseColor;
        }
        return (16777215 & baseColor) | (MathUtils.constrain((int) ((((float) Color.alpha(baseColor)) * alphaMod) + 0.5f), 0, 255) << 24);
    }

    public boolean isStateful() {
        return this.mStateSpecs.length >= 1 && this.mStateSpecs[0].length > 0;
    }

    public boolean hasFocusStateSpecified() {
        return StateSet.containsAttribute(this.mStateSpecs, 16842908);
    }

    public boolean isOpaque() {
        return this.mIsOpaque;
    }

    public int getColorForState(int[] stateSet, int defaultColor) {
        int setLength = this.mStateSpecs.length;
        for (int i = 0; i < setLength; i++) {
            if (StateSet.stateSetMatches(this.mStateSpecs[i], stateSet)) {
                return this.mColors[i];
            }
        }
        return defaultColor;
    }

    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    @UnsupportedAppUsage
    public int[][] getStates() {
        return this.mStateSpecs;
    }

    @UnsupportedAppUsage
    public int[] getColors() {
        return this.mColors;
    }

    public boolean hasState(int state) {
        for (int[] states : this.mStateSpecs) {
            int stateCount = states.length;
            for (int stateIndex = 0; stateIndex < stateCount; stateIndex++) {
                if (states[stateIndex] == state || states[stateIndex] == (~state)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        return "ColorStateList{mThemeAttrs=" + Arrays.deepToString(this.mThemeAttrs) + "mChangingConfigurations=" + this.mChangingConfigurations + "mStateSpecs=" + Arrays.deepToString(this.mStateSpecs) + "mColors=" + Arrays.toString(this.mColors) + "mDefaultColor=" + this.mDefaultColor + '}';
    }

    @UnsupportedAppUsage
    private void onColorsChanged() {
        int defaultColor = -65536;
        boolean isOpaque = true;
        int[][] states = this.mStateSpecs;
        int[] colors = this.mColors;
        int N = states.length;
        if (N > 0) {
            int i = 0;
            defaultColor = colors[0];
            int i2 = N - 1;
            while (true) {
                if (i2 <= 0) {
                    break;
                } else if (states[i2].length == 0) {
                    defaultColor = colors[i2];
                    break;
                } else {
                    i2--;
                }
            }
            while (true) {
                if (i >= N) {
                    break;
                } else if (Color.alpha(colors[i]) != 255) {
                    isOpaque = false;
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mDefaultColor = defaultColor;
        this.mIsOpaque = isOpaque;
    }

    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new ColorStateListFactory(this);
        }
        return this.mFactory;
    }

    private static class ColorStateListFactory extends ConstantState<ComplexColor> {
        private final ColorStateList mSrc;

        @UnsupportedAppUsage
        public ColorStateListFactory(ColorStateList src) {
            this.mSrc = src;
        }

        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        public ColorStateList newInstance() {
            return this.mSrc;
        }

        public ColorStateList newInstance(Resources res, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (canApplyTheme()) {
            Log.w(TAG, "Wrote partially-resolved ColorStateList to parcel!");
        }
        dest.writeInt(N);
        for (int[] writeIntArray : this.mStateSpecs) {
            dest.writeIntArray(writeIntArray);
        }
        dest.writeIntArray(this.mColors);
    }
}
