package android.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float) Math.tan(Math.toRadians(35.0d));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle;
    private float mMaximumTangent;
    private float mMinimumHorizontalAngle;
    private float mMinimumHorizontalTangent;
    private float mMinimumVerticalAngle;
    private float mMinimumVerticalTangent;

    public ArcMotion() {
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
    }

    public ArcMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.ArcMotion);
        float minimumVerticalAngle = a.getFloat(1, 0.0f);
        setMinimumVerticalAngle(minimumVerticalAngle);
        float minimumHorizontalAngle = a.getFloat(0, 0.0f);
        setMinimumHorizontalAngle(minimumHorizontalAngle);
        float maximumAngle = a.getFloat(2, DEFAULT_MAX_ANGLE_DEGREES);
        setMaximumAngle(maximumAngle);
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
        if (arcInDegrees < 0.0f || arcInDegrees > 90.0f) {
            throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float) Math.tan(Math.toRadians(arcInDegrees / 2.0f));
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c5  */
    @Override // android.transition.PathMotion
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Path getPath(float startX, float startY, float endX, float endY) {
        float ex;
        float ey;
        float ey2;
        float ey3;
        float ex2;
        float minimumArcDist2;
        float arcDist2;
        float maximumArcDist2;
        float newArcDistance2;
        Path path = new Path();
        path.moveTo(startX, startY);
        float deltaX = endX - startX;
        float deltaY = endY - startY;
        float h2 = (deltaX * deltaX) + (deltaY * deltaY);
        float dx = (startX + endX) / 2.0f;
        float dy = (startY + endY) / 2.0f;
        float midDist2 = h2 * 0.25f;
        float minimumArcDist22 = 0.0f;
        boolean isMovingUpwards = startY > endY;
        if (deltaY == 0.0f) {
            ex = dx;
            ey2 = (Math.abs(deltaX) * 0.5f * this.mMinimumHorizontalTangent) + dy;
        } else if (deltaX == 0.0f) {
            ex = (Math.abs(deltaY) * 0.5f * this.mMinimumVerticalTangent) + dx;
            ey2 = dy;
        } else {
            float ex3 = Math.abs(deltaX);
            if (ex3 < Math.abs(deltaY)) {
                float eDistY = Math.abs(h2 / (deltaY * 2.0f));
                if (isMovingUpwards) {
                    ey3 = endY + eDistY;
                    ex2 = endX;
                } else {
                    ey3 = startY + eDistY;
                    ex2 = startX;
                }
                minimumArcDist2 = this.mMinimumVerticalTangent * midDist2 * this.mMinimumVerticalTangent;
                ex = ex2;
                ey2 = ey3;
                float arcDistX = dx - ex;
                float arcDistY = dy - ey2;
                arcDist2 = (arcDistX * arcDistX) + (arcDistY * arcDistY);
                maximumArcDist2 = this.mMaximumTangent * midDist2 * this.mMaximumTangent;
                float newArcDistance22 = 0.0f;
                if (arcDist2 == 0.0f && arcDist2 < minimumArcDist2) {
                    newArcDistance22 = minimumArcDist2;
                } else if (arcDist2 > maximumArcDist2) {
                    newArcDistance22 = maximumArcDist2;
                }
                newArcDistance2 = newArcDistance22;
                if (newArcDistance2 != 0.0f) {
                    float ratio2 = newArcDistance2 / arcDist2;
                    float ratio = (float) Math.sqrt(ratio2);
                    ex = dx + ((ex - dx) * ratio);
                    ey2 = dy + ((ey2 - dy) * ratio);
                }
                float ex4 = ex;
                float ey4 = ey2;
                float control1X = (startX + ex4) / 2.0f;
                float control1Y = (startY + ey4) / 2.0f;
                float control2X = (ex4 + endX) / 2.0f;
                float control2Y = (ey4 + endY) / 2.0f;
                path.cubicTo(control1X, control1Y, control2X, control2Y, endX, endY);
                return path;
            }
            float eDistX = h2 / (deltaX * 2.0f);
            if (isMovingUpwards) {
                ex = startX + eDistX;
                ey = startY;
            } else {
                ex = endX - eDistX;
                ey = endY;
            }
            ey2 = ey;
            float ey5 = this.mMinimumHorizontalTangent;
            minimumArcDist22 = ey5 * midDist2 * this.mMinimumHorizontalTangent;
        }
        minimumArcDist2 = minimumArcDist22;
        float arcDistX2 = dx - ex;
        float arcDistY2 = dy - ey2;
        arcDist2 = (arcDistX2 * arcDistX2) + (arcDistY2 * arcDistY2);
        maximumArcDist2 = this.mMaximumTangent * midDist2 * this.mMaximumTangent;
        float newArcDistance222 = 0.0f;
        if (arcDist2 == 0.0f) {
        }
        if (arcDist2 > maximumArcDist2) {
        }
        newArcDistance2 = newArcDistance222;
        if (newArcDistance2 != 0.0f) {
        }
        float ex42 = ex;
        float ey42 = ey2;
        float control1X2 = (startX + ex42) / 2.0f;
        float control1Y2 = (startY + ey42) / 2.0f;
        float control2X2 = (ex42 + endX) / 2.0f;
        float control2Y2 = (ey42 + endY) / 2.0f;
        path.cubicTo(control1X2, control1Y2, control2X2, control2Y2, endX, endY);
        return path;
    }
}
