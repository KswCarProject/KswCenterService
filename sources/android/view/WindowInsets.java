package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Insets;
import android.graphics.Rect;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

public final class WindowInsets {
    @UnsupportedAppUsage
    public static final WindowInsets CONSUMED = new WindowInsets((Rect) null, (Rect) null, false, false, (DisplayCutout) null);
    /* access modifiers changed from: private */
    public final boolean mAlwaysConsumeSystemBars;
    private final DisplayCutout mDisplayCutout;
    private final boolean mDisplayCutoutConsumed;
    /* access modifiers changed from: private */
    public final boolean mIsRound;
    /* access modifiers changed from: private */
    public final boolean mStableInsetsConsumed;
    /* access modifiers changed from: private */
    public final boolean mSystemWindowInsetsConsumed;
    private Rect mTempRect;
    /* access modifiers changed from: private */
    public final Insets[] mTypeInsetsMap;
    /* access modifiers changed from: private */
    public final Insets[] mTypeMaxInsetsMap;
    /* access modifiers changed from: private */
    public final boolean[] mTypeVisibilityMap;

    public WindowInsets(Rect systemWindowInsetsRect, Rect stableInsetsRect, boolean isRound, boolean alwaysConsumeSystemBars, DisplayCutout displayCutout) {
        this(createCompatTypeMap(systemWindowInsetsRect), createCompatTypeMap(stableInsetsRect), createCompatVisibilityMap(createCompatTypeMap(systemWindowInsetsRect)), isRound, alwaysConsumeSystemBars, displayCutout);
    }

    public WindowInsets(Insets[] typeInsetsMap, Insets[] typeMaxInsetsMap, boolean[] typeVisibilityMap, boolean isRound, boolean alwaysConsumeSystemBars, DisplayCutout displayCutout) {
        Insets[] insetsArr;
        Insets[] insetsArr2;
        boolean z = false;
        this.mSystemWindowInsetsConsumed = typeInsetsMap == null;
        if (this.mSystemWindowInsetsConsumed) {
            insetsArr = new Insets[7];
        } else {
            insetsArr = (Insets[]) typeInsetsMap.clone();
        }
        this.mTypeInsetsMap = insetsArr;
        this.mStableInsetsConsumed = typeMaxInsetsMap == null;
        if (this.mStableInsetsConsumed) {
            insetsArr2 = new Insets[7];
        } else {
            insetsArr2 = (Insets[]) typeMaxInsetsMap.clone();
        }
        this.mTypeMaxInsetsMap = insetsArr2;
        this.mTypeVisibilityMap = typeVisibilityMap;
        this.mIsRound = isRound;
        this.mAlwaysConsumeSystemBars = alwaysConsumeSystemBars;
        this.mDisplayCutoutConsumed = displayCutout == null ? true : z;
        this.mDisplayCutout = (this.mDisplayCutoutConsumed || displayCutout.isEmpty()) ? null : displayCutout;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public WindowInsets(android.view.WindowInsets r10) {
        /*
            r9 = this;
            boolean r0 = r10.mSystemWindowInsetsConsumed
            r1 = 0
            if (r0 == 0) goto L_0x0007
            r3 = r1
            goto L_0x000a
        L_0x0007:
            android.graphics.Insets[] r0 = r10.mTypeInsetsMap
            r3 = r0
        L_0x000a:
            boolean r0 = r10.mStableInsetsConsumed
            if (r0 == 0) goto L_0x0010
        L_0x000e:
            r4 = r1
            goto L_0x0013
        L_0x0010:
            android.graphics.Insets[] r1 = r10.mTypeMaxInsetsMap
            goto L_0x000e
        L_0x0013:
            boolean[] r5 = r10.mTypeVisibilityMap
            boolean r6 = r10.mIsRound
            boolean r7 = r10.mAlwaysConsumeSystemBars
            android.view.DisplayCutout r8 = displayCutoutCopyConstructorArgument(r10)
            r2 = r9
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.WindowInsets.<init>(android.view.WindowInsets):void");
    }

    /* access modifiers changed from: private */
    public static DisplayCutout displayCutoutCopyConstructorArgument(WindowInsets w) {
        if (w.mDisplayCutoutConsumed) {
            return null;
        }
        if (w.mDisplayCutout == null) {
            return DisplayCutout.NO_CUTOUT;
        }
        return w.mDisplayCutout;
    }

    private static Insets getInsets(Insets[] typeInsetsMap, int typeMask) {
        Insets insets;
        Insets result = null;
        for (int i = 1; i <= 64; i <<= 1) {
            if (!((typeMask & i) == 0 || (insets = typeInsetsMap[Type.indexOf(i)]) == null)) {
                if (result == null) {
                    result = insets;
                } else {
                    result = Insets.max(result, insets);
                }
            }
        }
        return result == null ? Insets.NONE : result;
    }

    /* access modifiers changed from: private */
    public static void setInsets(Insets[] typeInsetsMap, int typeMask, Insets insets) {
        for (int i = 1; i <= 64; i <<= 1) {
            if ((typeMask & i) != 0) {
                typeInsetsMap[Type.indexOf(i)] = insets;
            }
        }
    }

    @UnsupportedAppUsage
    public WindowInsets(Rect systemWindowInsets) {
        this(createCompatTypeMap(systemWindowInsets), (Insets[]) null, new boolean[7], false, false, (DisplayCutout) null);
    }

    private static Insets[] createCompatTypeMap(Rect insets) {
        if (insets == null) {
            return null;
        }
        Insets[] typeInsetMap = new Insets[7];
        assignCompatInsets(typeInsetMap, insets);
        return typeInsetMap;
    }

    static void assignCompatInsets(Insets[] typeInsetMap, Rect insets) {
        typeInsetMap[Type.indexOf(1)] = Insets.of(0, insets.top, 0, 0);
        typeInsetMap[Type.indexOf(4)] = Insets.of(insets.left, 0, insets.right, insets.bottom);
    }

    private static boolean[] createCompatVisibilityMap(Insets[] typeInsetMap) {
        boolean[] typeVisibilityMap = new boolean[7];
        if (typeInsetMap == null) {
            return typeVisibilityMap;
        }
        for (int i = 1; i <= 64; i <<= 1) {
            int index = Type.indexOf(i);
            if (!Insets.NONE.equals(typeInsetMap[index])) {
                typeVisibilityMap[index] = true;
            }
        }
        return typeVisibilityMap;
    }

    @Deprecated
    public Rect getSystemWindowInsetsAsRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Insets insets = getSystemWindowInsets();
        this.mTempRect.set(insets.left, insets.top, insets.right, insets.bottom);
        return this.mTempRect;
    }

    public Insets getSystemWindowInsets() {
        return getInsets(this.mTypeInsetsMap, Type.compatSystemInsets());
    }

    public Insets getInsets(int typeMask) {
        return getInsets(this.mTypeInsetsMap, typeMask);
    }

    public Insets getMaxInsets(int typeMask) throws IllegalArgumentException {
        if ((typeMask & 2) == 0) {
            return getInsets(this.mTypeMaxInsetsMap, typeMask);
        }
        throw new IllegalArgumentException("Unable to query the maximum insets for IME");
    }

    public boolean isVisible(int typeMask) {
        for (int i = 1; i <= 64; i <<= 1) {
            if ((typeMask & i) != 0 && !this.mTypeVisibilityMap[Type.indexOf(i)]) {
                return false;
            }
        }
        return true;
    }

    public int getSystemWindowInsetLeft() {
        return getSystemWindowInsets().left;
    }

    public int getSystemWindowInsetTop() {
        return getSystemWindowInsets().top;
    }

    public int getSystemWindowInsetRight() {
        return getSystemWindowInsets().right;
    }

    public int getSystemWindowInsetBottom() {
        return getSystemWindowInsets().bottom;
    }

    public boolean hasSystemWindowInsets() {
        return !getSystemWindowInsets().equals(Insets.NONE);
    }

    public boolean hasInsets() {
        return !getInsets(this.mTypeInsetsMap, Type.all()).equals(Insets.NONE) || !getInsets(this.mTypeMaxInsetsMap, Type.all()).equals(Insets.NONE) || this.mDisplayCutout != null;
    }

    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout;
    }

    public WindowInsets consumeDisplayCutout() {
        Insets[] insetsArr;
        if (this.mSystemWindowInsetsConsumed) {
            insetsArr = null;
        } else {
            insetsArr = this.mTypeInsetsMap;
        }
        return new WindowInsets(insetsArr, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, (DisplayCutout) null);
    }

    public boolean isConsumed() {
        return this.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed && this.mDisplayCutoutConsumed;
    }

    public boolean isRound() {
        return this.mIsRound;
    }

    public WindowInsets consumeSystemWindowInsets() {
        return new WindowInsets((Insets[]) null, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, displayCutoutCopyConstructorArgument(this));
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        if (this.mSystemWindowInsetsConsumed) {
            return this;
        }
        return new Builder(this).setSystemWindowInsets(Insets.of(left, top, right, bottom)).build();
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(Rect systemWindowInsets) {
        return replaceSystemWindowInsets(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
    }

    public Insets getStableInsets() {
        return getInsets(this.mTypeMaxInsetsMap, Type.compatSystemInsets());
    }

    public int getStableInsetTop() {
        return getStableInsets().top;
    }

    public int getStableInsetLeft() {
        return getStableInsets().left;
    }

    public int getStableInsetRight() {
        return getStableInsets().right;
    }

    public int getStableInsetBottom() {
        return getStableInsets().bottom;
    }

    public boolean hasStableInsets() {
        return !getStableInsets().equals(Insets.NONE);
    }

    public Insets getSystemGestureInsets() {
        return getInsets(this.mTypeInsetsMap, 8);
    }

    public Insets getMandatorySystemGestureInsets() {
        return getInsets(this.mTypeInsetsMap, 16);
    }

    public Insets getTappableElementInsets() {
        return getInsets(this.mTypeInsetsMap, 32);
    }

    public WindowInsets consumeStableInsets() {
        return new WindowInsets(this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap, (Insets[]) null, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, displayCutoutCopyConstructorArgument(this));
    }

    public boolean shouldAlwaysConsumeSystemBars() {
        return this.mAlwaysConsumeSystemBars;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("WindowInsets{systemWindowInsets=");
        sb.append(getSystemWindowInsets());
        sb.append(" stableInsets=");
        sb.append(getStableInsets());
        sb.append(" sysGestureInsets=");
        sb.append(getSystemGestureInsets());
        if (this.mDisplayCutout != null) {
            str = " cutout=" + this.mDisplayCutout;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(isRound() ? " round" : "");
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public WindowInsets inset(Rect r) {
        return inset(r.left, r.top, r.right, r.bottom);
    }

    public WindowInsets inset(Insets insets) {
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public WindowInsets inset(int left, int top, int right, int bottom) {
        Insets[] insetsArr;
        DisplayCutout displayCutout;
        DisplayCutout inset;
        Preconditions.checkArgumentNonnegative(left);
        Preconditions.checkArgumentNonnegative(top);
        Preconditions.checkArgumentNonnegative(right);
        Preconditions.checkArgumentNonnegative(bottom);
        if (this.mSystemWindowInsetsConsumed) {
            insetsArr = null;
        } else {
            insetsArr = insetInsets(this.mTypeInsetsMap, left, top, right, bottom);
        }
        Insets[] insetInsets = this.mStableInsetsConsumed ? null : insetInsets(this.mTypeMaxInsetsMap, left, top, right, bottom);
        boolean[] zArr = this.mTypeVisibilityMap;
        boolean z = this.mIsRound;
        boolean z2 = this.mAlwaysConsumeSystemBars;
        if (this.mDisplayCutoutConsumed) {
            displayCutout = null;
        } else {
            if (this.mDisplayCutout == null) {
                inset = DisplayCutout.NO_CUTOUT;
            } else {
                inset = this.mDisplayCutout.inset(left, top, right, bottom);
            }
            displayCutout = inset;
        }
        return new WindowInsets(insetsArr, insetInsets, zArr, z, z2, displayCutout);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof WindowInsets)) {
            return false;
        }
        WindowInsets that = (WindowInsets) o;
        if (this.mIsRound == that.mIsRound && this.mAlwaysConsumeSystemBars == that.mAlwaysConsumeSystemBars && this.mSystemWindowInsetsConsumed == that.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed == that.mStableInsetsConsumed && this.mDisplayCutoutConsumed == that.mDisplayCutoutConsumed && Arrays.equals(this.mTypeInsetsMap, that.mTypeInsetsMap) && Arrays.equals(this.mTypeMaxInsetsMap, that.mTypeMaxInsetsMap) && Arrays.equals(this.mTypeVisibilityMap, that.mTypeVisibilityMap) && Objects.equals(this.mDisplayCutout, that.mDisplayCutout)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(Arrays.hashCode(this.mTypeInsetsMap)), Integer.valueOf(Arrays.hashCode(this.mTypeMaxInsetsMap)), Integer.valueOf(Arrays.hashCode(this.mTypeVisibilityMap)), Boolean.valueOf(this.mIsRound), this.mDisplayCutout, Boolean.valueOf(this.mAlwaysConsumeSystemBars), Boolean.valueOf(this.mSystemWindowInsetsConsumed), Boolean.valueOf(this.mStableInsetsConsumed), Boolean.valueOf(this.mDisplayCutoutConsumed)});
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: android.graphics.Insets[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Insets[] insetInsets(android.graphics.Insets[] r5, int r6, int r7, int r8, int r9) {
        /*
            r0 = 0
            r1 = 0
        L_0x0002:
            r2 = 7
            if (r1 >= r2) goto L_0x001f
            r2 = r5[r1]
            if (r2 != 0) goto L_0x000a
            goto L_0x001c
        L_0x000a:
            android.graphics.Insets r3 = insetInsets((android.graphics.Insets) r2, (int) r6, (int) r7, (int) r8, (int) r9)
            if (r3 == r2) goto L_0x001c
            if (r0 != 0) goto L_0x001a
            java.lang.Object r4 = r5.clone()
            r5 = r4
            android.graphics.Insets[] r5 = (android.graphics.Insets[]) r5
            r0 = 1
        L_0x001a:
            r5[r1] = r3
        L_0x001c:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x001f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.WindowInsets.insetInsets(android.graphics.Insets[], int, int, int, int):android.graphics.Insets[]");
    }

    private static Insets insetInsets(Insets insets, int left, int top, int right, int bottom) {
        int newLeft = Math.max(0, insets.left - left);
        int newTop = Math.max(0, insets.top - top);
        int newRight = Math.max(0, insets.right - right);
        int newBottom = Math.max(0, insets.bottom - bottom);
        if (newLeft == left && newTop == top && newRight == right && newBottom == bottom) {
            return insets;
        }
        return Insets.of(newLeft, newTop, newRight, newBottom);
    }

    /* access modifiers changed from: package-private */
    public boolean isSystemWindowInsetsConsumed() {
        return this.mSystemWindowInsetsConsumed;
    }

    public static final class Builder {
        private boolean mAlwaysConsumeSystemBars;
        private DisplayCutout mDisplayCutout;
        private boolean mIsRound;
        private boolean mStableInsetsConsumed;
        private boolean mSystemInsetsConsumed;
        private final Insets[] mTypeInsetsMap;
        private final Insets[] mTypeMaxInsetsMap;
        private final boolean[] mTypeVisibilityMap;

        public Builder() {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mTypeInsetsMap = new Insets[7];
            this.mTypeMaxInsetsMap = new Insets[7];
            this.mTypeVisibilityMap = new boolean[7];
        }

        public Builder(WindowInsets insets) {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mTypeInsetsMap = (Insets[]) insets.mTypeInsetsMap.clone();
            this.mTypeMaxInsetsMap = (Insets[]) insets.mTypeMaxInsetsMap.clone();
            this.mTypeVisibilityMap = (boolean[]) insets.mTypeVisibilityMap.clone();
            this.mSystemInsetsConsumed = insets.mSystemWindowInsetsConsumed;
            this.mStableInsetsConsumed = insets.mStableInsetsConsumed;
            this.mDisplayCutout = WindowInsets.displayCutoutCopyConstructorArgument(insets);
            this.mIsRound = insets.mIsRound;
            this.mAlwaysConsumeSystemBars = insets.mAlwaysConsumeSystemBars;
        }

        public Builder setSystemWindowInsets(Insets systemWindowInsets) {
            Preconditions.checkNotNull(systemWindowInsets);
            WindowInsets.assignCompatInsets(this.mTypeInsetsMap, systemWindowInsets.toRect());
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setSystemGestureInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 8, insets);
            return this;
        }

        public Builder setMandatorySystemGestureInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 16, insets);
            return this;
        }

        public Builder setTappableElementInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 32, insets);
            return this;
        }

        public Builder setInsets(int typeMask, Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.setInsets(this.mTypeInsetsMap, typeMask, insets);
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setMaxInsets(int typeMask, Insets insets) throws IllegalArgumentException {
            if (typeMask != 2) {
                Preconditions.checkNotNull(insets);
                WindowInsets.setInsets(this.mTypeMaxInsetsMap, typeMask, insets);
                this.mStableInsetsConsumed = false;
                return this;
            }
            throw new IllegalArgumentException("Maximum inset not available for IME");
        }

        public Builder setVisible(int typeMask, boolean visible) {
            for (int i = 1; i <= 64; i <<= 1) {
                if ((typeMask & i) != 0) {
                    this.mTypeVisibilityMap[Type.indexOf(i)] = visible;
                }
            }
            return this;
        }

        public Builder setStableInsets(Insets stableInsets) {
            Preconditions.checkNotNull(stableInsets);
            WindowInsets.assignCompatInsets(this.mTypeMaxInsetsMap, stableInsets.toRect());
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setDisplayCutout(DisplayCutout displayCutout) {
            this.mDisplayCutout = displayCutout != null ? displayCutout : DisplayCutout.NO_CUTOUT;
            return this;
        }

        public Builder setRound(boolean round) {
            this.mIsRound = round;
            return this;
        }

        public Builder setAlwaysConsumeSystemBars(boolean alwaysConsumeSystemBars) {
            this.mAlwaysConsumeSystemBars = alwaysConsumeSystemBars;
            return this;
        }

        public WindowInsets build() {
            Insets[] insetsArr;
            if (this.mSystemInsetsConsumed) {
                insetsArr = null;
            } else {
                insetsArr = this.mTypeInsetsMap;
            }
            return new WindowInsets(insetsArr, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, this.mDisplayCutout);
        }
    }

    public static final class Type {
        static final int FIRST = 1;
        static final int IME = 2;
        static final int LAST = 64;
        static final int MANDATORY_SYSTEM_GESTURES = 16;
        static final int SIDE_BARS = 4;
        static final int SIZE = 7;
        static final int SYSTEM_GESTURES = 8;
        static final int TAPPABLE_ELEMENT = 32;
        static final int TOP_BAR = 1;
        static final int WINDOW_DECOR = 64;

        @Retention(RetentionPolicy.SOURCE)
        public @interface InsetType {
        }

        static int indexOf(int type) {
            if (type == 4) {
                return 2;
            }
            if (type == 8) {
                return 3;
            }
            if (type == 16) {
                return 4;
            }
            if (type == 32) {
                return 5;
            }
            if (type == 64) {
                return 6;
            }
            switch (type) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                default:
                    throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + type);
            }
        }

        private Type() {
        }

        public static int topBar() {
            return 1;
        }

        public static int ime() {
            return 2;
        }

        public static int sideBars() {
            return 4;
        }

        public static int windowDecor() {
            return 64;
        }

        public static int systemGestures() {
            return 8;
        }

        public static int mandatorySystemGestures() {
            return 16;
        }

        public static int tappableElement() {
            return 32;
        }

        public static int systemBars() {
            return 5;
        }

        static int compatSystemInsets() {
            return 7;
        }

        public static int all() {
            return -1;
        }
    }
}
