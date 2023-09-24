package com.wits.pms.mcu.custom.utils;

/* loaded from: classes2.dex */
public class BrightnessUtils {

    /* renamed from: A */
    private static final float f2572A = 0.17883277f;

    /* renamed from: B */
    private static final float f2573B = 0.28466892f;

    /* renamed from: C */
    private static final float f2574C = 0.5599107f;
    public static final int GAMMA_SPACE_MAX = 1023;

    /* renamed from: R */
    private static final float f2575R = 0.5f;

    public static final int convertGammaToLinear(int val, int min, int max) {
        float ret;
        float normalizedVal = MathUtils.norm(0.0f, 1023.0f, val);
        if (normalizedVal <= 0.5f) {
            ret = MathUtils.m3sq(normalizedVal / 0.5f);
        } else {
            ret = MathUtils.exp((normalizedVal - f2574C) / f2572A) + f2573B;
        }
        return Math.round(MathUtils.lerp(min, max, ret / 12.0f));
    }

    public static final int convertLinearToGamma(int val, int min, int max) {
        float ret;
        float normalizedVal = MathUtils.norm(min, max, val) * 12.0f;
        if (normalizedVal <= 1.0f) {
            ret = MathUtils.sqrt(normalizedVal) * 0.5f;
        } else {
            ret = f2574C + (MathUtils.log(normalizedVal - f2573B) * f2572A);
        }
        return Math.round(MathUtils.lerp(0.0f, 1023.0f, ret));
    }

    public static double getPercentage(double value, int min, int max) {
        if (value > max) {
            return 1.0d;
        }
        if (value < min) {
            return 0.0d;
        }
        return (value - min) / (max - min);
    }
}
