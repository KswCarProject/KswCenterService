package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.RestrictTo;
import android.support.p011v4.content.res.TypedArrayUtils;
import android.support.p011v4.graphics.PathParser;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class PathInterpolatorCompat implements Interpolator {
    public static final double EPSILON = 1.0E-5d;
    public static final int MAX_NUM_POINTS = 3000;
    private static final float PRECISION = 0.002f;

    /* renamed from: mX */
    private float[] f259mX;

    /* renamed from: mY */
    private float[] f260mY;

    public PathInterpolatorCompat(Context context, AttributeSet attrs, XmlPullParser parser) {
        this(context.getResources(), context.getTheme(), attrs, parser);
    }

    public PathInterpolatorCompat(Resources res, Resources.Theme theme, AttributeSet attrs, XmlPullParser parser) {
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PATH_INTERPOLATOR);
        parseInterpolatorFromTypeArray(a, parser);
        a.recycle();
    }

    private void parseInterpolatorFromTypeArray(TypedArray a, XmlPullParser parser) {
        if (TypedArrayUtils.hasAttribute(parser, "pathData")) {
            String pathData = TypedArrayUtils.getNamedString(a, parser, "pathData", 4);
            Path path = PathParser.createPathFromPathData(pathData);
            if (path == null) {
                throw new InflateException("The path is null, which is created from " + pathData);
            }
            initPath(path);
        } else if (!TypedArrayUtils.hasAttribute(parser, "controlX1")) {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        } else {
            if (!TypedArrayUtils.hasAttribute(parser, "controlY1")) {
                throw new InflateException("pathInterpolator requires the controlY1 attribute");
            }
            float x1 = TypedArrayUtils.getNamedFloat(a, parser, "controlX1", 0, 0.0f);
            float y1 = TypedArrayUtils.getNamedFloat(a, parser, "controlY1", 1, 0.0f);
            boolean hasX2 = TypedArrayUtils.hasAttribute(parser, "controlX2");
            boolean hasY2 = TypedArrayUtils.hasAttribute(parser, "controlY2");
            if (hasX2 != hasY2) {
                throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
            }
            if (!hasX2) {
                initQuad(x1, y1);
                return;
            }
            float x2 = TypedArrayUtils.getNamedFloat(a, parser, "controlX2", 2, 0.0f);
            float y2 = TypedArrayUtils.getNamedFloat(a, parser, "controlY2", 3, 0.0f);
            initCubic(x1, y1, x2, y2);
        }
    }

    private void initQuad(float controlX, float controlY) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(controlX, controlY, 1.0f, 1.0f);
        initPath(path);
    }

    private void initCubic(float x1, float y1, float x2, float y2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(x1, y1, x2, y2, 1.0f, 1.0f);
        initPath(path);
    }

    private void initPath(Path path) {
        int i = 0;
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float pathLength = pathMeasure.getLength();
        int numPoints = Math.min((int) MAX_NUM_POINTS, ((int) (pathLength / PRECISION)) + 1);
        if (numPoints <= 0) {
            throw new IllegalArgumentException("The Path has a invalid length " + pathLength);
        }
        this.f259mX = new float[numPoints];
        this.f260mY = new float[numPoints];
        float[] position = new float[2];
        for (int i2 = 0; i2 < numPoints; i2++) {
            float distance = (i2 * pathLength) / (numPoints - 1);
            pathMeasure.getPosTan(distance, position, null);
            this.f259mX[i2] = position[0];
            this.f260mY[i2] = position[1];
        }
        if (Math.abs(this.f259mX[0]) > 1.0E-5d || Math.abs(this.f260mY[0]) > 1.0E-5d || Math.abs(this.f259mX[numPoints - 1] - 1.0f) > 1.0E-5d || Math.abs(this.f260mY[numPoints - 1] - 1.0f) > 1.0E-5d) {
            throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1) start: " + this.f259mX[0] + SmsManager.REGEX_PREFIX_DELIMITER + this.f260mY[0] + " end:" + this.f259mX[numPoints - 1] + SmsManager.REGEX_PREFIX_DELIMITER + this.f260mY[numPoints - 1]);
        }
        float prevX = 0.0f;
        int componentIndex = 0;
        while (i < numPoints) {
            int componentIndex2 = componentIndex + 1;
            float x = this.f259mX[componentIndex];
            if (x < prevX) {
                throw new IllegalArgumentException("The Path cannot loop back on itself, x :" + x);
            }
            this.f259mX[i] = x;
            prevX = x;
            i++;
            componentIndex = componentIndex2;
        }
        if (pathMeasure.nextContour()) {
            throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
        }
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        if (t <= 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        int startIndex = 0;
        int endIndex = this.f259mX.length - 1;
        while (endIndex - startIndex > 1) {
            int midIndex = (startIndex + endIndex) / 2;
            if (t < this.f259mX[midIndex]) {
                endIndex = midIndex;
            } else {
                startIndex = midIndex;
            }
        }
        float xRange = this.f259mX[endIndex] - this.f259mX[startIndex];
        if (xRange == 0.0f) {
            return this.f260mY[startIndex];
        }
        float tInRange = t - this.f259mX[startIndex];
        float fraction = tInRange / xRange;
        float startY = this.f260mY[startIndex];
        float endY = this.f260mY[endIndex];
        return ((endY - startY) * fraction) + startY;
    }
}
