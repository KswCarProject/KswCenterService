package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.graphics.Color;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import android.util.StateSet;
import android.util.Xml;
import com.android.ims.ImsConfig;
import com.android.internal.C3132R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ColorStateList extends ComplexColor implements Parcelable {
    private static final int DEFAULT_COLOR = -65536;
    private static final String TAG = "ColorStateList";
    private int mChangingConfigurations;
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
    private static final int[][] EMPTY = {new int[0]};
    private static final SparseArray<WeakReference<ColorStateList>> sCache = new SparseArray<>();
    public static final Parcelable.Creator<ColorStateList> CREATOR = new Parcelable.Creator<ColorStateList>() { // from class: android.content.res.ColorStateList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ColorStateList[] newArray(int size) {
            return new ColorStateList[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ColorStateList createFromParcel(Parcel source) {
            int N = source.readInt();
            int[][] stateSpecs = new int[N];
            for (int i = 0; i < N; i++) {
                stateSpecs[i] = source.createIntArray();
            }
            int[] colors = source.createIntArray();
            return new ColorStateList(stateSpecs, colors);
        }
    };

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
                ColorStateList cached = sCache.valueAt(index).get();
                if (cached != null) {
                    return cached;
                }
                sCache.removeAt(index);
            }
            int N = sCache.size();
            for (int i = N - 1; i >= 0; i--) {
                if (sCache.valueAt(i).get() == null) {
                    sCache.removeAt(i);
                }
            }
            ColorStateList csl = new ColorStateList(EMPTY, new int[]{color});
            sCache.put(color, new WeakReference<>(csl));
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
        return createFromXml(r, parser, null);
    }

    public static ColorStateList createFromXml(Resources r, XmlPullParser parser, Resources.Theme theme) throws XmlPullParserException, IOException {
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

    static ColorStateList createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        ColorStateList colorStateList = new ColorStateList();
        colorStateList.inflate(r, parser, attrs, theme);
        return colorStateList;
    }

    public ColorStateList withAlpha(int alpha) {
        int[] colors = new int[this.mColors.length];
        int len = colors.length;
        for (int i = 0; i < len; i++) {
            colors[i] = (this.mColors[i] & 16777215) | (alpha << 24);
        }
        return new ColorStateList(this.mStateSpecs, colors);
    }

    private void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int innerDepth;
        int i = 1;
        int innerDepth2 = parser.getDepth() + 1;
        boolean hasUnresolvedAttrs = false;
        int[][] stateSpecList = (int[][]) ArrayUtils.newUnpaddedArray(int[].class, 20);
        int[][] themeAttrsList = new int[stateSpecList.length];
        int[] colorList = new int[stateSpecList.length];
        int[][] stateSpecList2 = stateSpecList;
        int defaultColor = -65536;
        int changingConfigurations = 0;
        int listSize = 0;
        while (true) {
            int type = parser.next();
            if (type == i) {
                break;
            }
            int depth = parser.getDepth();
            int i2 = depth;
            if (depth < innerDepth2 && type == 3) {
                break;
            }
            if (type != 2 || i2 > innerDepth2) {
                innerDepth = innerDepth2;
            } else if (!parser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                innerDepth = innerDepth2;
            } else {
                TypedArray a = Resources.obtainAttributes(r, theme, attrs, C3132R.styleable.ColorStateListItem);
                int[] themeAttrs = a.extractThemeAttrs();
                int innerDepth3 = innerDepth2;
                int baseColor = a.getColor(0, Color.MAGENTA);
                float alphaMod = a.getFloat(1, 1.0f);
                int changingConfigurations2 = changingConfigurations | a.getChangingConfigurations();
                a.recycle();
                int numAttrs = attrs.getAttributeCount();
                int[] stateSpec = new int[numAttrs];
                int j = 0;
                int j2 = 0;
                while (true) {
                    int i3 = j2;
                    int depth2 = i2;
                    if (i3 >= numAttrs) {
                        break;
                    }
                    int numAttrs2 = numAttrs;
                    int stateResId = attrs.getAttributeNameResource(i3);
                    if (stateResId != 16843173 && stateResId != 16843551) {
                        int j3 = j + 1;
                        stateSpec[j] = attrs.getAttributeBooleanValue(i3, false) ? stateResId : -stateResId;
                        j = j3;
                    }
                    j2 = i3 + 1;
                    i2 = depth2;
                    numAttrs = numAttrs2;
                }
                int[] stateSpec2 = StateSet.trimStateSet(stateSpec, j);
                int color = modulateColorAlpha(baseColor, alphaMod);
                if (listSize == 0 || stateSpec2.length == 0) {
                    defaultColor = color;
                }
                if (themeAttrs != null) {
                    hasUnresolvedAttrs = true;
                }
                colorList = GrowingArrayUtils.append(colorList, listSize, color);
                themeAttrsList = (int[][]) GrowingArrayUtils.append(themeAttrsList, listSize, themeAttrs);
                stateSpecList2 = (int[][]) GrowingArrayUtils.append(stateSpecList2, listSize, stateSpec2);
                listSize++;
                innerDepth2 = innerDepth3;
                changingConfigurations = changingConfigurations2;
                i = 1;
            }
            innerDepth2 = innerDepth;
            i = 1;
        }
        this.mChangingConfigurations = changingConfigurations;
        this.mDefaultColor = defaultColor;
        if (hasUnresolvedAttrs) {
            this.mThemeAttrs = new int[listSize];
            System.arraycopy(themeAttrsList, 0, this.mThemeAttrs, 0, listSize);
        } else {
            this.mThemeAttrs = null;
        }
        this.mColors = new int[listSize];
        this.mStateSpecs = new int[listSize];
        System.arraycopy(colorList, 0, this.mColors, 0, listSize);
        System.arraycopy(stateSpecList2, 0, this.mStateSpecs, 0, listSize);
        onColorsChanged();
    }

    @Override // android.content.res.ComplexColor
    @UnsupportedAppUsage
    public boolean canApplyTheme() {
        return this.mThemeAttrs != null;
    }

    private void applyTheme(Resources.Theme t) {
        float defaultAlphaMod;
        if (this.mThemeAttrs == null) {
            return;
        }
        int[][] themeAttrsList = this.mThemeAttrs;
        int N = themeAttrsList.length;
        boolean hasUnresolvedAttrs = false;
        for (int i = 0; i < N; i++) {
            if (themeAttrsList[i] != null) {
                TypedArray a = t.resolveAttributes(themeAttrsList[i], C3132R.styleable.ColorStateListItem);
                if (themeAttrsList[i][0] != 0) {
                    defaultAlphaMod = Color.alpha(this.mColors[i]) / 255.0f;
                } else {
                    defaultAlphaMod = 1.0f;
                }
                themeAttrsList[i] = a.extractThemeAttrs(themeAttrsList[i]);
                if (themeAttrsList[i] != null) {
                    hasUnresolvedAttrs = true;
                }
                int baseColor = a.getColor(0, this.mColors[i]);
                float alphaMod = a.getFloat(1, defaultAlphaMod);
                this.mColors[i] = modulateColorAlpha(baseColor, alphaMod);
                this.mChangingConfigurations |= a.getChangingConfigurations();
                a.recycle();
            }
        }
        if (!hasUnresolvedAttrs) {
            this.mThemeAttrs = null;
        }
        onColorsChanged();
    }

    @Override // android.content.res.ComplexColor
    @UnsupportedAppUsage
    public ColorStateList obtainForTheme(Resources.Theme t) {
        if (t == null || !canApplyTheme()) {
            return this;
        }
        ColorStateList clone = new ColorStateList(this);
        clone.applyTheme(t);
        return clone;
    }

    @Override // android.content.res.ComplexColor
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    private int modulateColorAlpha(int baseColor, float alphaMod) {
        if (alphaMod == 1.0f) {
            return baseColor;
        }
        int baseAlpha = Color.alpha(baseColor);
        int alpha = MathUtils.constrain((int) ((baseAlpha * alphaMod) + 0.5f), 0, 255);
        return (16777215 & baseColor) | (alpha << 24);
    }

    @Override // android.content.res.ComplexColor
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
            int[] stateSpec = this.mStateSpecs[i];
            if (StateSet.stateSetMatches(stateSpec, stateSet)) {
                return this.mColors[i];
            }
        }
        return defaultColor;
    }

    @Override // android.content.res.ComplexColor
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
        int[][] stateSpecs = this.mStateSpecs;
        for (int[] states : stateSpecs) {
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
                } else if (states[i2].length != 0) {
                    i2--;
                } else {
                    defaultColor = colors[i2];
                    break;
                }
            }
            while (true) {
                if (i >= N) {
                    break;
                } else if (Color.alpha(colors[i]) == 255) {
                    i++;
                } else {
                    isOpaque = false;
                    break;
                }
            }
        }
        this.mDefaultColor = defaultColor;
        this.mIsOpaque = isOpaque;
    }

    @Override // android.content.res.ComplexColor
    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new ColorStateListFactory(this);
        }
        return this.mFactory;
    }

    /* loaded from: classes.dex */
    private static class ColorStateListFactory extends ConstantState<ComplexColor> {
        private final ColorStateList mSrc;

        @UnsupportedAppUsage
        public ColorStateListFactory(ColorStateList src) {
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

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (canApplyTheme()) {
            Log.m64w(TAG, "Wrote partially-resolved ColorStateList to parcel!");
        }
        int N = this.mStateSpecs.length;
        dest.writeInt(N);
        for (int i = 0; i < N; i++) {
            dest.writeIntArray(this.mStateSpecs[i]);
        }
        dest.writeIntArray(this.mColors);
    }
}
