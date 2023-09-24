package android.util;

import com.ibm.icu.text.PluralRules;

/* loaded from: classes4.dex */
public abstract class Spline {
    public abstract float interpolate(float f);

    public static Spline createSpline(float[] x, float[] y) {
        if (!isStrictlyIncreasing(x)) {
            throw new IllegalArgumentException("The control points must all have strictly increasing X values.");
        }
        if (isMonotonic(y)) {
            return createMonotoneCubicSpline(x, y);
        }
        return createLinearSpline(x, y);
    }

    public static Spline createMonotoneCubicSpline(float[] x, float[] y) {
        return new MonotoneCubicSpline(x, y);
    }

    public static Spline createLinearSpline(float[] x, float[] y) {
        return new LinearSpline(x, y);
    }

    private static boolean isStrictlyIncreasing(float[] x) {
        if (x == null || x.length < 2) {
            throw new IllegalArgumentException("There must be at least two control points.");
        }
        float prev = x[0];
        float prev2 = prev;
        for (int i = 1; i < x.length; i++) {
            float curr = x[i];
            if (curr <= prev2) {
                return false;
            }
            prev2 = curr;
        }
        return true;
    }

    private static boolean isMonotonic(float[] x) {
        if (x == null || x.length < 2) {
            throw new IllegalArgumentException("There must be at least two control points.");
        }
        float prev = x[0];
        float prev2 = prev;
        for (int i = 1; i < x.length; i++) {
            float curr = x[i];
            if (curr < prev2) {
                return false;
            }
            prev2 = curr;
        }
        return true;
    }

    /* loaded from: classes4.dex */
    public static class MonotoneCubicSpline extends Spline {

        /* renamed from: mM */
        private float[] f369mM;

        /* renamed from: mX */
        private float[] f370mX;

        /* renamed from: mY */
        private float[] f371mY;

        public MonotoneCubicSpline(float[] x, float[] y) {
            if (x == null || y == null || x.length != y.length || x.length < 2) {
                throw new IllegalArgumentException("There must be at least two control points and the arrays must be of equal length.");
            }
            int n = x.length;
            float[] d = new float[n - 1];
            float[] m = new float[n];
            for (int i = 0; i < n - 1; i++) {
                float h = x[i + 1] - x[i];
                if (h <= 0.0f) {
                    throw new IllegalArgumentException("The control points must all have strictly increasing X values.");
                }
                d[i] = (y[i + 1] - y[i]) / h;
            }
            m[0] = d[0];
            for (int i2 = 1; i2 < n - 1; i2++) {
                m[i2] = (d[i2 - 1] + d[i2]) * 0.5f;
            }
            int i3 = n - 1;
            m[i3] = d[n - 2];
            for (int i4 = 0; i4 < n - 1; i4++) {
                if (d[i4] == 0.0f) {
                    m[i4] = 0.0f;
                    m[i4 + 1] = 0.0f;
                } else {
                    float a = m[i4] / d[i4];
                    float b = m[i4 + 1] / d[i4];
                    if (a < 0.0f || b < 0.0f) {
                        throw new IllegalArgumentException("The control points must have monotonic Y values.");
                    }
                    float h2 = (float) Math.hypot(a, b);
                    if (h2 > 3.0f) {
                        float t = 3.0f / h2;
                        m[i4] = m[i4] * t;
                        int i5 = i4 + 1;
                        m[i5] = m[i5] * t;
                    }
                }
            }
            this.f370mX = x;
            this.f371mY = y;
            this.f369mM = m;
        }

        @Override // android.util.Spline
        public float interpolate(float x) {
            int n = this.f370mX.length;
            if (Float.isNaN(x)) {
                return x;
            }
            int i = 0;
            if (x <= this.f370mX[0]) {
                return this.f371mY[0];
            }
            if (x >= this.f370mX[n - 1]) {
                return this.f371mY[n - 1];
            }
            do {
                int i2 = i;
                if (x >= this.f370mX[i2 + 1]) {
                    i = i2 + 1;
                } else {
                    float h = this.f370mX[i2 + 1] - this.f370mX[i2];
                    float t = (x - this.f370mX[i2]) / h;
                    return (((this.f371mY[i2] * ((t * 2.0f) + 1.0f)) + (this.f369mM[i2] * h * t)) * (1.0f - t) * (1.0f - t)) + (((this.f371mY[i2 + 1] * (3.0f - (2.0f * t))) + (this.f369mM[i2 + 1] * h * (t - 1.0f))) * t * t);
                }
            } while (x != this.f370mX[i]);
            return this.f371mY[i];
        }

        public String toString() {
            StringBuilder str = new StringBuilder();
            int n = this.f370mX.length;
            str.append("MonotoneCubicSpline{[");
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    str.append(", ");
                }
                str.append("(");
                str.append(this.f370mX[i]);
                str.append(", ");
                str.append(this.f371mY[i]);
                str.append(PluralRules.KEYWORD_RULE_SEPARATOR);
                str.append(this.f369mM[i]);
                str.append(")");
            }
            str.append("]}");
            return str.toString();
        }
    }

    /* loaded from: classes4.dex */
    public static class LinearSpline extends Spline {

        /* renamed from: mM */
        private final float[] f366mM;

        /* renamed from: mX */
        private final float[] f367mX;

        /* renamed from: mY */
        private final float[] f368mY;

        public LinearSpline(float[] x, float[] y) {
            if (x == null || y == null || x.length != y.length || x.length < 2) {
                throw new IllegalArgumentException("There must be at least two control points and the arrays must be of equal length.");
            }
            int N = x.length;
            this.f366mM = new float[N - 1];
            for (int i = 0; i < N - 1; i++) {
                this.f366mM[i] = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);
            }
            this.f367mX = x;
            this.f368mY = y;
        }

        @Override // android.util.Spline
        public float interpolate(float x) {
            int n = this.f367mX.length;
            if (Float.isNaN(x)) {
                return x;
            }
            int i = 0;
            if (x <= this.f367mX[0]) {
                return this.f368mY[0];
            }
            if (x >= this.f367mX[n - 1]) {
                return this.f368mY[n - 1];
            }
            do {
                int i2 = i;
                if (x >= this.f367mX[i2 + 1]) {
                    i = i2 + 1;
                } else {
                    return this.f368mY[i2] + (this.f366mM[i2] * (x - this.f367mX[i2]));
                }
            } while (x != this.f367mX[i]);
            return this.f368mY[i];
        }

        public String toString() {
            StringBuilder str = new StringBuilder();
            int n = this.f367mX.length;
            str.append("LinearSpline{[");
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    str.append(", ");
                }
                str.append("(");
                str.append(this.f367mX[i]);
                str.append(", ");
                str.append(this.f368mY[i]);
                if (i < n - 1) {
                    str.append(PluralRules.KEYWORD_RULE_SEPARATOR);
                    str.append(this.f366mM[i]);
                }
                str.append(")");
            }
            str.append("]}");
            return str.toString();
        }
    }
}
