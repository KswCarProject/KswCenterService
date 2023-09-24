package android.animation;

import android.graphics.PointF;

/* loaded from: classes.dex */
public class PointFEvaluator implements TypeEvaluator<PointF> {
    private PointF mPoint;

    public PointFEvaluator() {
    }

    public PointFEvaluator(PointF reuse) {
        this.mPoint = reuse;
    }

    @Override // android.animation.TypeEvaluator
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = startValue.f61x + ((endValue.f61x - startValue.f61x) * fraction);
        float y = startValue.f62y + ((endValue.f62y - startValue.f62y) * fraction);
        if (this.mPoint != null) {
            this.mPoint.set(x, y);
            return this.mPoint;
        }
        return new PointF(x, y);
    }
}
