package android.view;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseSetArray;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsetsAnimationListener;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.function.Supplier;

@VisibleForTesting
public class InsetsAnimationControlImpl implements WindowInsetsAnimationController {
    private final WindowInsetsAnimationListener.InsetsAnimation mAnimation;
    private boolean mCancelled;
    private final SparseArray<InsetsSourceConsumer> mConsumers;
    private final InsetsController mController;
    private Insets mCurrentInsets;
    private boolean mFinished;
    private int mFinishedShownTypes;
    private final Rect mFrame;
    private final Insets mHiddenInsets;
    private final InsetsState mInitialInsetsState;
    private final WindowInsetsAnimationControlListener mListener;
    private Insets mPendingInsets;
    private final Insets mShownInsets;
    private final SparseSetArray<InsetsSourceConsumer> mSideSourceMap = new SparseSetArray<>();
    private final Rect mTmpFrame = new Rect();
    private final Matrix mTmpMatrix = new Matrix();
    private final Supplier<SyncRtSurfaceTransactionApplier> mTransactionApplierSupplier;
    private final SparseIntArray mTypeSideMap = new SparseIntArray();
    private final int mTypes;

    @VisibleForTesting
    public InsetsAnimationControlImpl(SparseArray<InsetsSourceConsumer> consumers, Rect frame, InsetsState state, WindowInsetsAnimationControlListener listener, int types, Supplier<SyncRtSurfaceTransactionApplier> transactionApplierSupplier, InsetsController controller) {
        this.mConsumers = consumers;
        this.mListener = listener;
        this.mTypes = types;
        this.mTransactionApplierSupplier = transactionApplierSupplier;
        this.mController = controller;
        this.mInitialInsetsState = new InsetsState(state, true);
        this.mCurrentInsets = getInsetsFromState(this.mInitialInsetsState, frame, (SparseIntArray) null);
        Rect rect = frame;
        SparseArray<InsetsSourceConsumer> sparseArray = consumers;
        this.mHiddenInsets = calculateInsets(this.mInitialInsetsState, rect, sparseArray, false, (SparseIntArray) null);
        this.mShownInsets = calculateInsets(this.mInitialInsetsState, rect, sparseArray, true, this.mTypeSideMap);
        this.mFrame = new Rect(frame);
        buildTypeSourcesMap(this.mTypeSideMap, this.mSideSourceMap, this.mConsumers);
        listener.onReady(this, types);
        this.mAnimation = new WindowInsetsAnimationListener.InsetsAnimation(this.mTypes, this.mHiddenInsets, this.mShownInsets);
        this.mController.dispatchAnimationStarted(this.mAnimation);
    }

    public Insets getHiddenStateInsets() {
        return this.mHiddenInsets;
    }

    public Insets getShownStateInsets() {
        return this.mShownInsets;
    }

    public Insets getCurrentInsets() {
        return this.mCurrentInsets;
    }

    public int getTypes() {
        return this.mTypes;
    }

    public void changeInsets(Insets insets) {
        if (this.mFinished) {
            throw new IllegalStateException("Can't change insets on an animation that is finished.");
        } else if (!this.mCancelled) {
            this.mPendingInsets = sanitize(insets);
            this.mController.scheduleApplyChangeInsets();
        } else {
            throw new IllegalStateException("Can't change insets on an animation that is cancelled.");
        }
    }

    @VisibleForTesting
    public boolean applyChangeInsets(InsetsState state) {
        if (this.mCancelled) {
            return false;
        }
        Insets offset = Insets.subtract(this.mShownInsets, this.mPendingInsets);
        ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> params = new ArrayList<>();
        if (offset.left != 0) {
            updateLeashesForSide(0, offset.left, this.mPendingInsets.left, params, state);
        }
        if (offset.top != 0) {
            updateLeashesForSide(1, offset.top, this.mPendingInsets.top, params, state);
        }
        if (offset.right != 0) {
            updateLeashesForSide(2, offset.right, this.mPendingInsets.right, params, state);
        }
        if (offset.bottom != 0) {
            updateLeashesForSide(3, offset.bottom, this.mPendingInsets.bottom, params, state);
        }
        this.mTransactionApplierSupplier.get().scheduleApply((SyncRtSurfaceTransactionApplier.SurfaceParams[]) params.toArray(new SyncRtSurfaceTransactionApplier.SurfaceParams[params.size()]));
        this.mCurrentInsets = this.mPendingInsets;
        if (this.mFinished) {
            this.mController.notifyFinished(this, this.mFinishedShownTypes);
        }
        return this.mFinished;
    }

    public void finish(int shownTypes) {
        if (!this.mCancelled) {
            InsetsState state = new InsetsState(this.mController.getState());
            for (int i = this.mConsumers.size() - 1; i >= 0; i--) {
                InsetsSourceConsumer consumer = this.mConsumers.valueAt(i);
                state.getSource(consumer.getType()).setVisible((InsetsState.toPublicType(consumer.getType()) & shownTypes) != 0);
            }
            changeInsets(getInsetsFromState(state, this.mFrame, (SparseIntArray) null));
            this.mFinished = true;
            this.mFinishedShownTypes = shownTypes;
        }
    }

    @VisibleForTesting
    public void onCancelled() {
        if (!this.mFinished) {
            this.mCancelled = true;
            this.mListener.onCancelled();
        }
    }

    /* access modifiers changed from: package-private */
    public WindowInsetsAnimationListener.InsetsAnimation getAnimation() {
        return this.mAnimation;
    }

    private Insets calculateInsets(InsetsState state, Rect frame, SparseArray<InsetsSourceConsumer> consumers, boolean shown, SparseIntArray typeSideMap) {
        for (int i = consumers.size() - 1; i >= 0; i--) {
            state.getSource(consumers.valueAt(i).getType()).setVisible(shown);
        }
        return getInsetsFromState(state, frame, typeSideMap);
    }

    private Insets getInsetsFromState(InsetsState state, Rect frame, SparseIntArray typeSideMap) {
        return state.calculateInsets(frame, false, false, (DisplayCutout) null, (Rect) null, (Rect) null, 16, typeSideMap).getInsets(this.mTypes);
    }

    private Insets sanitize(Insets insets) {
        return Insets.max(Insets.min(insets, this.mShownInsets), this.mHiddenInsets);
    }

    private void updateLeashesForSide(int side, int offset, int inset, ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> surfaceParams, InsetsState state) {
        int i = side;
        ArraySet<InsetsSourceConsumer> items = this.mSideSourceMap.get(i);
        boolean z = true;
        int i2 = items.size() - 1;
        while (i2 >= 0) {
            InsetsSourceConsumer consumer = items.valueAt(i2);
            InsetsSource source = this.mInitialInsetsState.getSource(consumer.getType());
            InsetsSourceControl control = consumer.getControl();
            SurfaceControl leash = consumer.getControl().getLeash();
            this.mTmpMatrix.setTranslate((float) control.getSurfacePosition().x, (float) control.getSurfacePosition().y);
            this.mTmpFrame.set(source.getFrame());
            addTranslationToMatrix(i, offset, this.mTmpMatrix, this.mTmpFrame);
            state.getSource(source.getType()).setFrame(this.mTmpFrame);
            Matrix matrix = this.mTmpMatrix;
            boolean z2 = inset != 0 ? z : false;
            SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams2 = r9;
            SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams3 = new SyncRtSurfaceTransactionApplier.SurfaceParams(leash, 1.0f, matrix, (Rect) null, 0, 0.0f, z2);
            surfaceParams.add(surfaceParams2);
            i2--;
            z = true;
        }
        ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList = surfaceParams;
    }

    private void addTranslationToMatrix(int side, int inset, Matrix m, Rect frame) {
        switch (side) {
            case 0:
                m.postTranslate((float) (-inset), 0.0f);
                frame.offset(-inset, 0);
                return;
            case 1:
                m.postTranslate(0.0f, (float) (-inset));
                frame.offset(0, -inset);
                return;
            case 2:
                m.postTranslate((float) inset, 0.0f);
                frame.offset(inset, 0);
                return;
            case 3:
                m.postTranslate(0.0f, (float) inset);
                frame.offset(0, inset);
                return;
            default:
                return;
        }
    }

    private static void buildTypeSourcesMap(SparseIntArray typeSideMap, SparseSetArray<InsetsSourceConsumer> sideSourcesMap, SparseArray<InsetsSourceConsumer> consumers) {
        for (int i = typeSideMap.size() - 1; i >= 0; i--) {
            sideSourcesMap.add(typeSideMap.valueAt(i), consumers.get(typeSideMap.keyAt(i)));
        }
    }
}
