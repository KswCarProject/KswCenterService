package android.support.p011v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.p007os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EdgeEffect;

/* renamed from: android.support.v4.widget.EdgeEffectCompat */
/* loaded from: classes3.dex */
public final class EdgeEffectCompat {
    private static final EdgeEffectBaseImpl IMPL;
    private EdgeEffect mEdgeEffect;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new EdgeEffectApi21Impl();
        } else {
            IMPL = new EdgeEffectBaseImpl();
        }
    }

    /* renamed from: android.support.v4.widget.EdgeEffectCompat$EdgeEffectBaseImpl */
    /* loaded from: classes3.dex */
    static class EdgeEffectBaseImpl {
        EdgeEffectBaseImpl() {
        }

        public void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
            edgeEffect.onPull(deltaDistance);
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.widget.EdgeEffectCompat$EdgeEffectApi21Impl */
    /* loaded from: classes3.dex */
    static class EdgeEffectApi21Impl extends EdgeEffectBaseImpl {
        EdgeEffectApi21Impl() {
        }

        @Override // android.support.p011v4.widget.EdgeEffectCompat.EdgeEffectBaseImpl
        public void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
            edgeEffect.onPull(deltaDistance, displacement);
        }
    }

    @Deprecated
    public EdgeEffectCompat(Context context) {
        this.mEdgeEffect = new EdgeEffect(context);
    }

    @Deprecated
    public void setSize(int width, int height) {
        this.mEdgeEffect.setSize(width, height);
    }

    @Deprecated
    public boolean isFinished() {
        return this.mEdgeEffect.isFinished();
    }

    @Deprecated
    public void finish() {
        this.mEdgeEffect.finish();
    }

    @Deprecated
    public boolean onPull(float deltaDistance) {
        this.mEdgeEffect.onPull(deltaDistance);
        return true;
    }

    @Deprecated
    public boolean onPull(float deltaDistance, float displacement) {
        IMPL.onPull(this.mEdgeEffect, deltaDistance, displacement);
        return true;
    }

    public static void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
        IMPL.onPull(edgeEffect, deltaDistance, displacement);
    }

    @Deprecated
    public boolean onRelease() {
        this.mEdgeEffect.onRelease();
        return this.mEdgeEffect.isFinished();
    }

    @Deprecated
    public boolean onAbsorb(int velocity) {
        this.mEdgeEffect.onAbsorb(velocity);
        return true;
    }

    @Deprecated
    public boolean draw(Canvas canvas) {
        return this.mEdgeEffect.draw(canvas);
    }
}
