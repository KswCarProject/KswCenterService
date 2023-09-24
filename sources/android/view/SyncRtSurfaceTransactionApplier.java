package android.view;

import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class SyncRtSurfaceTransactionApplier {
    private final Surface mTargetSurface;
    private final ViewRootImpl mTargetViewRootImpl;
    private final float[] mTmpFloat9 = new float[9];

    public SyncRtSurfaceTransactionApplier(View targetView) {
        this.mTargetViewRootImpl = targetView != null ? targetView.getViewRootImpl() : null;
        this.mTargetSurface = this.mTargetViewRootImpl != null ? this.mTargetViewRootImpl.mSurface : null;
    }

    public void scheduleApply(final SurfaceParams... params) {
        if (this.mTargetViewRootImpl == null) {
            return;
        }
        this.mTargetViewRootImpl.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: android.view.-$$Lambda$SyncRtSurfaceTransactionApplier$ttntIVYYZl7t890CcQHVoB3U1nQ
            @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
            public final void onFrameDraw(long j) {
                SyncRtSurfaceTransactionApplier.lambda$scheduleApply$0(SyncRtSurfaceTransactionApplier.this, params, j);
            }
        });
        this.mTargetViewRootImpl.getView().invalidate();
    }

    public static /* synthetic */ void lambda$scheduleApply$0(SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, SurfaceParams[] params, long frame) {
        if (syncRtSurfaceTransactionApplier.mTargetSurface == null || !syncRtSurfaceTransactionApplier.mTargetSurface.isValid()) {
            return;
        }
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        for (int i = params.length - 1; i >= 0; i--) {
            SurfaceParams surfaceParams = params[i];
            SurfaceControl surface = surfaceParams.surface;
            t.deferTransactionUntilSurface(surface, syncRtSurfaceTransactionApplier.mTargetSurface, frame);
            applyParams(t, surfaceParams, syncRtSurfaceTransactionApplier.mTmpFloat9);
        }
        t.setEarlyWakeup();
        t.apply();
    }

    public static void applyParams(SurfaceControl.Transaction t, SurfaceParams params, float[] tmpFloat9) {
        t.setMatrix(params.surface, params.matrix, tmpFloat9);
        t.setWindowCrop(params.surface, params.windowCrop);
        t.setAlpha(params.surface, params.alpha);
        t.setLayer(params.surface, params.layer);
        t.setCornerRadius(params.surface, params.cornerRadius);
        if (params.visible) {
            t.show(params.surface);
        } else {
            t.hide(params.surface);
        }
    }

    public static void create(final View targetView, final Consumer<SyncRtSurfaceTransactionApplier> callback) {
        if (targetView == null) {
            callback.accept(null);
        } else if (targetView.getViewRootImpl() != null) {
            callback.accept(new SyncRtSurfaceTransactionApplier(targetView));
        } else {
            targetView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: android.view.SyncRtSurfaceTransactionApplier.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View v) {
                    View.this.removeOnAttachStateChangeListener(this);
                    callback.accept(new SyncRtSurfaceTransactionApplier(View.this));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View v) {
                }
            });
        }
    }

    /* loaded from: classes4.dex */
    public static class SurfaceParams {
        @VisibleForTesting
        public final float alpha;
        @VisibleForTesting
        final float cornerRadius;
        @VisibleForTesting
        public final int layer;
        @VisibleForTesting
        public final Matrix matrix;
        @VisibleForTesting
        public final SurfaceControl surface;
        public final boolean visible;
        @VisibleForTesting
        public final Rect windowCrop;

        public SurfaceParams(SurfaceControl surface, float alpha, Matrix matrix, Rect windowCrop, int layer, float cornerRadius, boolean visible) {
            this.surface = surface;
            this.alpha = alpha;
            this.matrix = new Matrix(matrix);
            this.windowCrop = new Rect(windowCrop);
            this.layer = layer;
            this.cornerRadius = cornerRadius;
            this.visible = visible;
        }
    }
}
