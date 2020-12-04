package android.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.internal.R;

public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = ((float) Math.tan(Math.toRadians(35.0d)));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
    private float mMaximumTangent = DEFAULT_MAX_TANGENT;
    private float mMinimumHorizontalAngle = 0.0f;
    private float mMinimumHorizontalTangent = 0.0f;
    private float mMinimumVerticalAngle = 0.0f;
    private float mMinimumVerticalTangent = 0.0f;

    public ArcMotion() {
    }

    public ArcMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcMotion);
        setMinimumVerticalAngle(a.getFloat(1, 0.0f));
        setMinimumHorizontalAngle(a.getFloat(0, 0.0f));
        setMaximumAngle(a.getFloat(2, DEFAULT_MAX_ANGLE_DEGREES));
        a.recycle();
    }

    public void setMinimumHorizontalAngle(float angleInDegrees) {
        this.mMinimumHorizontalAngle = angleInDegrees;
        this.mMinimumHorizontalTangent = toTangent(angleInDegrees);
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public void setMinimumVerticalAngle(float angleInDegrees) {
        this.mMinimumVerticalAngle = angleInDegrees;
        this.mMinimumVerticalTangent = toTangent(angleInDegrees);
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    public void setMaximumAngle(float angleInDegrees) {
        this.mMaximumAngle = angleInDegrees;
        this.mMaximumTangent = toTangent(angleInDegrees);
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    private static float toTangent(float arcInDegrees) {
        if (arcInDegrees >= 0.0f && arcInDegrees <= 90.0f) {
            return (float) Math.tan(Math.toRadians((double) (arcInDegrees / 2.0f)));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Path getPath(float r31, float r32, float r33, float r34) {
        /*
            r30 = this;
            r0 = r30
            r1 = r31
            r2 = r32
            android.graphics.Path r3 = new android.graphics.Path
            r3.<init>()
            r10 = r3
            r10.moveTo(r1, r2)
            float r11 = r33 - r1
            float r12 = r34 - r2
            float r3 = r11 * r11
            float r4 = r12 * r12
            float r13 = r3 + r4
            float r3 = r1 + r33
            r4 = 1073741824(0x40000000, float:2.0)
            float r14 = r3 / r4
            float r3 = r2 + r34
            float r15 = r3 / r4
            r3 = 1048576000(0x3e800000, float:0.25)
            float r16 = r13 * r3
            r3 = 0
            int r5 = (r2 > r34 ? 1 : (r2 == r34 ? 0 : -1))
            if (r5 <= 0) goto L_0x002e
            r5 = 1
            goto L_0x002f
        L_0x002e:
            r5 = 0
        L_0x002f:
            r17 = r5
            r5 = 0
            int r6 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            r7 = 1056964608(0x3f000000, float:0.5)
            if (r6 != 0) goto L_0x0045
            r6 = r14
            float r8 = java.lang.Math.abs(r11)
            float r8 = r8 * r7
            float r7 = r0.mMinimumHorizontalTangent
            float r8 = r8 * r7
            float r8 = r8 + r15
        L_0x0042:
            r19 = r3
            goto L_0x0099
        L_0x0045:
            int r6 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r6 != 0) goto L_0x0054
            float r6 = java.lang.Math.abs(r12)
            float r6 = r6 * r7
            float r7 = r0.mMinimumVerticalTangent
            float r6 = r6 * r7
            float r6 = r6 + r14
            r8 = r15
            goto L_0x0042
        L_0x0054:
            float r6 = java.lang.Math.abs(r11)
            float r7 = java.lang.Math.abs(r12)
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 >= 0) goto L_0x0080
            float r6 = r12 * r4
            float r6 = r13 / r6
            float r6 = java.lang.Math.abs(r6)
            if (r17 == 0) goto L_0x006f
            float r7 = r34 + r6
            r8 = r33
            goto L_0x0072
        L_0x006f:
            float r7 = r2 + r6
            r8 = r1
        L_0x0072:
            float r9 = r0.mMinimumVerticalTangent
            float r9 = r9 * r16
            float r5 = r0.mMinimumVerticalTangent
            float r3 = r9 * r5
            r19 = r3
            r6 = r8
            r8 = r7
            goto L_0x0099
        L_0x0080:
            float r5 = r11 * r4
            float r5 = r13 / r5
            if (r17 == 0) goto L_0x008b
            float r6 = r1 + r5
            r7 = r32
            goto L_0x008f
        L_0x008b:
            float r6 = r33 - r5
            r7 = r34
        L_0x008f:
            r8 = r7
            float r7 = r0.mMinimumHorizontalTangent
            float r7 = r7 * r16
            float r9 = r0.mMinimumHorizontalTangent
            float r3 = r7 * r9
            goto L_0x0042
        L_0x0099:
            float r20 = r14 - r6
            float r21 = r15 - r8
            float r3 = r20 * r20
            float r5 = r21 * r21
            float r22 = r3 + r5
            float r3 = r0.mMaximumTangent
            float r3 = r3 * r16
            float r5 = r0.mMaximumTangent
            float r23 = r3 * r5
            r3 = 0
            r5 = 0
            int r7 = (r22 > r5 ? 1 : (r22 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x00b8
            int r5 = (r22 > r19 ? 1 : (r22 == r19 ? 0 : -1))
            if (r5 >= 0) goto L_0x00b8
            r3 = r19
            goto L_0x00be
        L_0x00b8:
            int r5 = (r22 > r23 ? 1 : (r22 == r23 ? 0 : -1))
            if (r5 <= 0) goto L_0x00be
            r3 = r23
        L_0x00be:
            r24 = r3
            r3 = 0
            int r3 = (r24 > r3 ? 1 : (r24 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x00d7
            float r3 = r24 / r22
            double r4 = (double) r3
            double r4 = java.lang.Math.sqrt(r4)
            float r4 = (float) r4
            float r5 = r6 - r14
            float r5 = r5 * r4
            float r6 = r14 + r5
            float r5 = r8 - r15
            float r5 = r5 * r4
            float r8 = r15 + r5
        L_0x00d7:
            r18 = r6
            r26 = r8
            float r3 = r1 + r18
            r4 = 1073741824(0x40000000, float:2.0)
            float r25 = r3 / r4
            float r3 = r2 + r26
            float r27 = r3 / r4
            float r3 = r18 + r33
            float r28 = r3 / r4
            float r3 = r26 + r34
            float r29 = r3 / r4
            r3 = r10
            r4 = r25
            r5 = r27
            r6 = r28
            r7 = r29
            r8 = r33
            r9 = r34
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.ArcMotion.getPath(float, float, float, float):android.graphics.Path");
    }
}
