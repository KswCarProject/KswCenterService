package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.HardwareRenderer;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceSession;
import android.view.SurfaceView;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;
import android.widget.Magnifier;
import com.android.internal.C3132R;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class Magnifier {
    private static final int NONEXISTENT_PREVIOUS_CONFIG_VALUE = -1;
    public static final int SOURCE_BOUND_MAX_IN_SURFACE = 0;
    public static final int SOURCE_BOUND_MAX_VISIBLE = 1;
    private static final String TAG = "Magnifier";
    private static final HandlerThread sPixelCopyHandlerThread = new HandlerThread("magnifier pixel copy result handler");
    private int mBottomContentBound;
    private Callback mCallback;
    private final Point mClampedCenterZoomCoords;
    private final boolean mClippingEnabled;
    private SurfaceInfo mContentCopySurface;
    private final int mDefaultHorizontalSourceToMagnifierOffset;
    private final int mDefaultVerticalSourceToMagnifierOffset;
    private final Object mDestroyLock;
    private boolean mDirtyState;
    private int mLeftContentBound;
    private final Object mLock;
    private final Drawable mOverlay;
    private SurfaceInfo mParentSurface;
    private final Rect mPixelCopyRequestRect;
    private final PointF mPrevShowSourceCoords;
    private final PointF mPrevShowWindowCoords;
    private final Point mPrevStartCoordsInSurface;
    private int mRightContentBound;
    private int mSourceHeight;
    private int mSourceWidth;
    private int mTopContentBound;
    private final View mView;
    private final int[] mViewCoordinatesInSurface;
    private InternalPopupWindow mWindow;
    private final Point mWindowCoords;
    private final float mWindowCornerRadius;
    private final float mWindowElevation;
    private final int mWindowHeight;
    private final int mWindowWidth;
    private float mZoom;

    /* loaded from: classes4.dex */
    public interface Callback {
        void onOperationComplete();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface SourceBound {
    }

    static {
        sPixelCopyHandlerThread.start();
    }

    @Deprecated
    public Magnifier(View view) {
        this(createBuilderWithOldMagnifierDefaults(view));
    }

    static Builder createBuilderWithOldMagnifierDefaults(View view) {
        Builder params = new Builder(view);
        Context context = view.getContext();
        TypedArray a = context.obtainStyledAttributes(null, C3132R.styleable.Magnifier, C3132R.attr.magnifierStyle, 0);
        params.mWidth = a.getDimensionPixelSize(5, 0);
        params.mHeight = a.getDimensionPixelSize(2, 0);
        params.mElevation = a.getDimension(1, 0.0f);
        params.mCornerRadius = getDeviceDefaultDialogCornerRadius(context);
        params.mZoom = a.getFloat(6, 0.0f);
        params.mHorizontalDefaultSourceToMagnifierOffset = a.getDimensionPixelSize(3, 0);
        params.mVerticalDefaultSourceToMagnifierOffset = a.getDimensionPixelSize(4, 0);
        params.mOverlay = new ColorDrawable(a.getColor(0, 0));
        a.recycle();
        params.mClippingEnabled = true;
        params.mLeftContentBound = 1;
        params.mTopContentBound = 0;
        params.mRightContentBound = 1;
        params.mBottomContentBound = 0;
        return params;
    }

    private static float getDeviceDefaultDialogCornerRadius(Context context) {
        Context deviceDefaultContext = new ContextThemeWrapper(context, 16974120);
        TypedArray ta = deviceDefaultContext.obtainStyledAttributes(new int[]{16844145});
        float dialogCornerRadius = ta.getDimension(0, 0.0f);
        ta.recycle();
        return dialogCornerRadius;
    }

    private Magnifier(Builder params) {
        this.mWindowCoords = new Point();
        this.mClampedCenterZoomCoords = new Point();
        this.mPrevStartCoordsInSurface = new Point(-1, -1);
        this.mPrevShowSourceCoords = new PointF(-1.0f, -1.0f);
        this.mPrevShowWindowCoords = new PointF(-1.0f, -1.0f);
        this.mPixelCopyRequestRect = new Rect();
        this.mLock = new Object();
        this.mDestroyLock = new Object();
        this.mView = params.mView;
        this.mWindowWidth = params.mWidth;
        this.mWindowHeight = params.mHeight;
        this.mZoom = params.mZoom;
        this.mSourceWidth = Math.round(this.mWindowWidth / this.mZoom);
        this.mSourceHeight = Math.round(this.mWindowHeight / this.mZoom);
        this.mWindowElevation = params.mElevation;
        this.mWindowCornerRadius = params.mCornerRadius;
        this.mOverlay = params.mOverlay;
        this.mDefaultHorizontalSourceToMagnifierOffset = params.mHorizontalDefaultSourceToMagnifierOffset;
        this.mDefaultVerticalSourceToMagnifierOffset = params.mVerticalDefaultSourceToMagnifierOffset;
        this.mClippingEnabled = params.mClippingEnabled;
        this.mLeftContentBound = params.mLeftContentBound;
        this.mTopContentBound = params.mTopContentBound;
        this.mRightContentBound = params.mRightContentBound;
        this.mBottomContentBound = params.mBottomContentBound;
        this.mViewCoordinatesInSurface = new int[2];
    }

    public void show(float sourceCenterX, float sourceCenterY) {
        show(sourceCenterX, sourceCenterY, this.mDefaultHorizontalSourceToMagnifierOffset + sourceCenterX, this.mDefaultVerticalSourceToMagnifierOffset + sourceCenterY);
    }

    public void show(float sourceCenterX, float sourceCenterY, float magnifierCenterX, float magnifierCenterY) {
        Drawable colorDrawable;
        obtainSurfaces();
        obtainContentCoordinates(sourceCenterX, sourceCenterY);
        obtainWindowCoordinates(magnifierCenterX, magnifierCenterY);
        int startX = this.mClampedCenterZoomCoords.f59x - (this.mSourceWidth / 2);
        int startY = this.mClampedCenterZoomCoords.f60y - (this.mSourceHeight / 2);
        if (sourceCenterX != this.mPrevShowSourceCoords.f61x || sourceCenterY != this.mPrevShowSourceCoords.f62y || this.mDirtyState) {
            if (this.mWindow == null) {
                synchronized (this.mLock) {
                    Context context = this.mView.getContext();
                    Display display = this.mView.getDisplay();
                    SurfaceControl surfaceControl = this.mParentSurface.mSurfaceControl;
                    int i = this.mWindowWidth;
                    int i2 = this.mWindowHeight;
                    float f = this.mWindowElevation;
                    float f2 = this.mWindowCornerRadius;
                    if (this.mOverlay != null) {
                        colorDrawable = this.mOverlay;
                    } else {
                        colorDrawable = new ColorDrawable(0);
                    }
                    this.mWindow = new InternalPopupWindow(context, display, surfaceControl, i, i2, f, f2, colorDrawable, Handler.getMain(), this.mLock, this.mCallback);
                }
            }
            performPixelCopy(startX, startY, true);
        } else if (magnifierCenterX != this.mPrevShowWindowCoords.f61x || magnifierCenterY != this.mPrevShowWindowCoords.f62y) {
            final Point windowCoords = getCurrentClampedWindowCoordinates();
            final InternalPopupWindow currentWindowInstance = this.mWindow;
            sPixelCopyHandlerThread.getThreadHandler().post(new Runnable() { // from class: android.widget.-$$Lambda$Magnifier$sEUKNU2_gseoDMBt_HOs-JGAfZ8
                @Override // java.lang.Runnable
                public final void run() {
                    Magnifier.lambda$show$0(Magnifier.this, currentWindowInstance, windowCoords);
                }
            });
        }
        this.mPrevShowSourceCoords.f61x = sourceCenterX;
        this.mPrevShowSourceCoords.f62y = sourceCenterY;
        this.mPrevShowWindowCoords.f61x = magnifierCenterX;
        this.mPrevShowWindowCoords.f62y = magnifierCenterY;
    }

    public static /* synthetic */ void lambda$show$0(Magnifier magnifier, InternalPopupWindow currentWindowInstance, Point windowCoords) {
        synchronized (magnifier.mLock) {
            if (magnifier.mWindow != currentWindowInstance) {
                return;
            }
            magnifier.mWindow.setContentPositionForNextDraw(windowCoords.f59x, windowCoords.f60y);
        }
    }

    public void dismiss() {
        if (this.mWindow != null) {
            synchronized (this.mLock) {
                this.mWindow.destroy();
                this.mWindow = null;
            }
            this.mPrevShowSourceCoords.f61x = -1.0f;
            this.mPrevShowSourceCoords.f62y = -1.0f;
            this.mPrevShowWindowCoords.f61x = -1.0f;
            this.mPrevShowWindowCoords.f62y = -1.0f;
            this.mPrevStartCoordsInSurface.f59x = -1;
            this.mPrevStartCoordsInSurface.f60y = -1;
        }
    }

    public void update() {
        if (this.mWindow != null) {
            obtainSurfaces();
            if (!this.mDirtyState) {
                performPixelCopy(this.mPrevStartCoordsInSurface.f59x, this.mPrevStartCoordsInSurface.f60y, false);
            } else {
                show(this.mPrevShowSourceCoords.f61x, this.mPrevShowSourceCoords.f62y, this.mPrevShowWindowCoords.f61x, this.mPrevShowWindowCoords.f62y);
            }
        }
    }

    public int getWidth() {
        return this.mWindowWidth;
    }

    public int getHeight() {
        return this.mWindowHeight;
    }

    public int getSourceWidth() {
        return this.mSourceWidth;
    }

    public int getSourceHeight() {
        return this.mSourceHeight;
    }

    public void setZoom(float zoom) {
        Preconditions.checkArgumentPositive(zoom, "Zoom should be positive");
        this.mZoom = zoom;
        this.mSourceWidth = Math.round(this.mWindowWidth / this.mZoom);
        this.mSourceHeight = Math.round(this.mWindowHeight / this.mZoom);
        this.mDirtyState = true;
    }

    public float getZoom() {
        return this.mZoom;
    }

    public float getElevation() {
        return this.mWindowElevation;
    }

    public float getCornerRadius() {
        return this.mWindowCornerRadius;
    }

    public int getDefaultHorizontalSourceToMagnifierOffset() {
        return this.mDefaultHorizontalSourceToMagnifierOffset;
    }

    public int getDefaultVerticalSourceToMagnifierOffset() {
        return this.mDefaultVerticalSourceToMagnifierOffset;
    }

    public Drawable getOverlay() {
        return this.mOverlay;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public Point getPosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point position = getCurrentClampedWindowCoordinates();
        position.offset(-this.mParentSurface.mInsets.left, -this.mParentSurface.mInsets.top);
        return new Point(position);
    }

    public Point getSourcePosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point position = new Point(this.mPixelCopyRequestRect.left, this.mPixelCopyRequestRect.top);
        position.offset(-this.mContentCopySurface.mInsets.left, -this.mContentCopySurface.mInsets.top);
        return new Point(position);
    }

    private void obtainSurfaces() {
        ViewRootImpl viewRootImpl;
        Surface mainWindowSurface;
        SurfaceInfo validMainWindowSurface = SurfaceInfo.NULL;
        if (this.mView.getViewRootImpl() != null && (mainWindowSurface = (viewRootImpl = this.mView.getViewRootImpl()).mSurface) != null && mainWindowSurface.isValid()) {
            Rect surfaceInsets = viewRootImpl.mWindowAttributes.surfaceInsets;
            int surfaceWidth = viewRootImpl.getWidth() + surfaceInsets.left + surfaceInsets.right;
            int surfaceHeight = viewRootImpl.getHeight() + surfaceInsets.top + surfaceInsets.bottom;
            validMainWindowSurface = new SurfaceInfo(viewRootImpl.getSurfaceControl(), mainWindowSurface, surfaceWidth, surfaceHeight, surfaceInsets, true);
        }
        SurfaceInfo validSurfaceViewSurface = SurfaceInfo.NULL;
        if (this.mView instanceof SurfaceView) {
            SurfaceControl sc = ((SurfaceView) this.mView).getSurfaceControl();
            SurfaceHolder surfaceHolder = ((SurfaceView) this.mView).getHolder();
            Surface surfaceViewSurface = surfaceHolder.getSurface();
            if (sc != null && sc.isValid()) {
                Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
                validSurfaceViewSurface = new SurfaceInfo(sc, surfaceViewSurface, surfaceFrame.right, surfaceFrame.bottom, new Rect(), false);
            }
        }
        this.mParentSurface = validMainWindowSurface != SurfaceInfo.NULL ? validMainWindowSurface : validSurfaceViewSurface;
        this.mContentCopySurface = this.mView instanceof SurfaceView ? validSurfaceViewSurface : validMainWindowSurface;
    }

    private void obtainContentCoordinates(float xPosInView, float yPosInView) {
        int zoomCenterX;
        int zoomCenterY;
        int prevViewXInSurface = this.mViewCoordinatesInSurface[0];
        int prevViewYInSurface = this.mViewCoordinatesInSurface[1];
        this.mView.getLocationInSurface(this.mViewCoordinatesInSurface);
        if (this.mViewCoordinatesInSurface[0] != prevViewXInSurface || this.mViewCoordinatesInSurface[1] != prevViewYInSurface) {
            this.mDirtyState = true;
        }
        if (this.mView instanceof SurfaceView) {
            zoomCenterX = Math.round(xPosInView);
            zoomCenterY = Math.round(yPosInView);
        } else {
            zoomCenterX = Math.round(xPosInView + this.mViewCoordinatesInSurface[0]);
            zoomCenterY = Math.round(yPosInView + this.mViewCoordinatesInSurface[1]);
        }
        Rect[] bounds = new Rect[2];
        Rect surfaceBounds = new Rect(0, 0, this.mContentCopySurface.mWidth, this.mContentCopySurface.mHeight);
        bounds[0] = surfaceBounds;
        Rect viewVisibleRegion = new Rect();
        this.mView.getGlobalVisibleRect(viewVisibleRegion);
        if (this.mView.getViewRootImpl() != null) {
            Rect surfaceInsets = this.mView.getViewRootImpl().mWindowAttributes.surfaceInsets;
            viewVisibleRegion.offset(surfaceInsets.left, surfaceInsets.top);
        }
        if (this.mView instanceof SurfaceView) {
            viewVisibleRegion.offset(-this.mViewCoordinatesInSurface[0], -this.mViewCoordinatesInSurface[1]);
        }
        bounds[1] = viewVisibleRegion;
        int resolvedLeft = Integer.MIN_VALUE;
        for (int i = this.mLeftContentBound; i >= 0; i--) {
            resolvedLeft = Math.max(resolvedLeft, bounds[i].left);
        }
        int resolvedTop = Integer.MIN_VALUE;
        for (int i2 = this.mTopContentBound; i2 >= 0; i2--) {
            resolvedTop = Math.max(resolvedTop, bounds[i2].top);
        }
        int resolvedRight = Integer.MAX_VALUE;
        for (int i3 = this.mRightContentBound; i3 >= 0; i3--) {
            resolvedRight = Math.min(resolvedRight, bounds[i3].right);
        }
        int resolvedBottom = Integer.MAX_VALUE;
        for (int i4 = this.mBottomContentBound; i4 >= 0; i4--) {
            resolvedBottom = Math.min(resolvedBottom, bounds[i4].bottom);
        }
        int resolvedLeft2 = Math.min(resolvedLeft, this.mContentCopySurface.mWidth - this.mSourceWidth);
        int resolvedTop2 = Math.min(resolvedTop, this.mContentCopySurface.mHeight - this.mSourceHeight);
        if (resolvedLeft2 < 0 || resolvedTop2 < 0) {
            Log.m70e(TAG, "Magnifier's content is copied from a surface smaller thanthe content requested size. The magnifier will be dismissed.");
        }
        int resolvedRight2 = Math.max(resolvedRight, this.mSourceWidth + resolvedLeft2);
        int resolvedRight3 = this.mSourceHeight;
        int resolvedBottom2 = Math.max(resolvedBottom, resolvedRight3 + resolvedTop2);
        Point point = this.mClampedCenterZoomCoords;
        int prevViewXInSurface2 = this.mSourceWidth;
        point.f59x = Math.max((this.mSourceWidth / 2) + resolvedLeft2, Math.min(zoomCenterX, resolvedRight2 - (prevViewXInSurface2 / 2)));
        this.mClampedCenterZoomCoords.f60y = Math.max((this.mSourceHeight / 2) + resolvedTop2, Math.min(zoomCenterY, resolvedBottom2 - (this.mSourceHeight / 2)));
    }

    private void obtainWindowCoordinates(float xWindowPos, float yWindowPos) {
        int windowCenterX;
        int windowCenterY;
        if (this.mView instanceof SurfaceView) {
            windowCenterX = Math.round(xWindowPos);
            windowCenterY = Math.round(yWindowPos);
        } else {
            windowCenterX = Math.round(this.mViewCoordinatesInSurface[0] + xWindowPos);
            windowCenterY = Math.round(this.mViewCoordinatesInSurface[1] + yWindowPos);
        }
        this.mWindowCoords.f59x = windowCenterX - (this.mWindowWidth / 2);
        this.mWindowCoords.f60y = windowCenterY - (this.mWindowHeight / 2);
        if (this.mParentSurface != this.mContentCopySurface) {
            this.mWindowCoords.f59x += this.mViewCoordinatesInSurface[0];
            this.mWindowCoords.f60y += this.mViewCoordinatesInSurface[1];
        }
    }

    private void performPixelCopy(int startXInSurface, int startYInSurface, final boolean updateWindowPosition) {
        if (this.mContentCopySurface.mSurface == null || !this.mContentCopySurface.mSurface.isValid()) {
            onPixelCopyFailed();
            return;
        }
        final Point windowCoords = getCurrentClampedWindowCoordinates();
        this.mPixelCopyRequestRect.set(startXInSurface, startYInSurface, this.mSourceWidth + startXInSurface, this.mSourceHeight + startYInSurface);
        final InternalPopupWindow currentWindowInstance = this.mWindow;
        final Bitmap bitmap = Bitmap.createBitmap(this.mSourceWidth, this.mSourceHeight, Bitmap.Config.ARGB_8888);
        PixelCopy.request(this.mContentCopySurface.mSurface, this.mPixelCopyRequestRect, bitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: android.widget.-$$Lambda$Magnifier$K0um0QSTAb4wXwua60CgJIIwGaI
            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
            public final void onPixelCopyFinished(int i) {
                Magnifier.lambda$performPixelCopy$1(Magnifier.this, currentWindowInstance, updateWindowPosition, windowCoords, bitmap, i);
            }
        }, sPixelCopyHandlerThread.getThreadHandler());
        this.mPrevStartCoordsInSurface.f59x = startXInSurface;
        this.mPrevStartCoordsInSurface.f60y = startYInSurface;
        this.mDirtyState = false;
    }

    public static /* synthetic */ void lambda$performPixelCopy$1(Magnifier magnifier, InternalPopupWindow currentWindowInstance, boolean updateWindowPosition, Point windowCoords, Bitmap bitmap, int result) {
        if (result != 0) {
            magnifier.onPixelCopyFailed();
            return;
        }
        synchronized (magnifier.mLock) {
            if (magnifier.mWindow != currentWindowInstance) {
                return;
            }
            if (updateWindowPosition) {
                magnifier.mWindow.setContentPositionForNextDraw(windowCoords.f59x, windowCoords.f60y);
            }
            magnifier.mWindow.updateContent(bitmap);
        }
    }

    private void onPixelCopyFailed() {
        Log.m70e(TAG, "Magnifier failed to copy content from the view Surface. It will be dismissed.");
        Handler.getMain().postAtFrontOfQueue(new Runnable() { // from class: android.widget.-$$Lambda$Magnifier$esRj9C7NyDvOX8eqqqLKuB6jpTw
            @Override // java.lang.Runnable
            public final void run() {
                Magnifier.lambda$onPixelCopyFailed$2(Magnifier.this);
            }
        });
    }

    public static /* synthetic */ void lambda$onPixelCopyFailed$2(Magnifier magnifier) {
        magnifier.dismiss();
        if (magnifier.mCallback != null) {
            magnifier.mCallback.onOperationComplete();
        }
    }

    private Point getCurrentClampedWindowCoordinates() {
        Rect windowBounds;
        if (!this.mClippingEnabled) {
            return new Point(this.mWindowCoords);
        }
        if (this.mParentSurface.mIsMainWindowSurface) {
            Insets systemInsets = this.mView.getRootWindowInsets().getSystemWindowInsets();
            windowBounds = new Rect(systemInsets.left + this.mParentSurface.mInsets.left, systemInsets.top + this.mParentSurface.mInsets.top, (this.mParentSurface.mWidth - systemInsets.right) - this.mParentSurface.mInsets.right, (this.mParentSurface.mHeight - systemInsets.bottom) - this.mParentSurface.mInsets.bottom);
        } else {
            windowBounds = new Rect(0, 0, this.mParentSurface.mWidth, this.mParentSurface.mHeight);
        }
        int windowCoordsX = Math.max(windowBounds.left, Math.min(windowBounds.right - this.mWindowWidth, this.mWindowCoords.f59x));
        int windowCoordsY = Math.max(windowBounds.top, Math.min(windowBounds.bottom - this.mWindowHeight, this.mWindowCoords.f60y));
        return new Point(windowCoordsX, windowCoordsY);
    }

    /* loaded from: classes4.dex */
    private static class SurfaceInfo {
        public static final SurfaceInfo NULL = new SurfaceInfo(null, null, 0, 0, null, false);
        private int mHeight;
        private Rect mInsets;
        private boolean mIsMainWindowSurface;
        private Surface mSurface;
        private SurfaceControl mSurfaceControl;
        private int mWidth;

        SurfaceInfo(SurfaceControl surfaceControl, Surface surface, int width, int height, Rect insets, boolean isMainWindowSurface) {
            this.mSurfaceControl = surfaceControl;
            this.mSurface = surface;
            this.mWidth = width;
            this.mHeight = height;
            this.mInsets = insets;
            this.mIsMainWindowSurface = isMainWindowSurface;
        }
    }

    /* loaded from: classes4.dex */
    private static class InternalPopupWindow {
        private static final int SURFACE_Z = 5;
        private Bitmap mBitmap;
        private final RenderNode mBitmapRenderNode;
        private Callback mCallback;
        private final int mContentHeight;
        private final int mContentWidth;
        private Bitmap mCurrentContent;
        private final Display mDisplay;
        private boolean mFrameDrawScheduled;
        private final Handler mHandler;
        private int mLastDrawContentPositionX;
        private int mLastDrawContentPositionY;
        private final Object mLock;
        private final Runnable mMagnifierUpdater;
        private final int mOffsetX;
        private final int mOffsetY;
        private final Drawable mOverlay;
        private final RenderNode mOverlayRenderNode;
        private boolean mPendingWindowPositionUpdate;
        private final ThreadedRenderer.SimpleRenderer mRenderer;
        private final SurfaceControl mSurfaceControl;
        private final int mSurfaceHeight;
        private final int mSurfaceWidth;
        private int mWindowPositionX;
        private int mWindowPositionY;
        private boolean mFirstDraw = true;
        private final SurfaceSession mSurfaceSession = new SurfaceSession();
        private final Surface mSurface = new Surface();

        InternalPopupWindow(Context context, Display display, SurfaceControl parentSurfaceControl, int width, int height, float elevation, float cornerRadius, Drawable overlay, Handler handler, Object lock, Callback callback) {
            this.mDisplay = display;
            this.mOverlay = overlay;
            this.mLock = lock;
            this.mCallback = callback;
            this.mContentWidth = width;
            this.mContentHeight = height;
            this.mOffsetX = (int) (elevation * 1.05f);
            this.mOffsetY = (int) (1.05f * elevation);
            this.mSurfaceWidth = this.mContentWidth + (this.mOffsetX * 2);
            this.mSurfaceHeight = this.mContentHeight + (this.mOffsetY * 2);
            this.mSurfaceControl = new SurfaceControl.Builder(this.mSurfaceSession).setFormat(-3).setBufferSize(this.mSurfaceWidth, this.mSurfaceHeight).setName("magnifier surface").setFlags(4).setParent(parentSurfaceControl).build();
            this.mSurface.copyFrom(this.mSurfaceControl);
            this.mRenderer = new ThreadedRenderer.SimpleRenderer(context, "magnifier renderer", this.mSurface);
            this.mBitmapRenderNode = createRenderNodeForBitmap("magnifier content", elevation, cornerRadius);
            this.mOverlayRenderNode = createRenderNodeForOverlay("magnifier overlay", cornerRadius);
            setupOverlay();
            RecordingCanvas canvas = this.mRenderer.getRootNode().beginRecording(width, height);
            try {
                canvas.insertReorderBarrier();
                canvas.drawRenderNode(this.mBitmapRenderNode);
                canvas.insertInorderBarrier();
                canvas.drawRenderNode(this.mOverlayRenderNode);
                canvas.insertInorderBarrier();
                this.mRenderer.getRootNode().endRecording();
                if (this.mCallback != null) {
                    this.mCurrentContent = Bitmap.createBitmap(this.mContentWidth, this.mContentHeight, Bitmap.Config.ARGB_8888);
                    updateCurrentContentForTesting();
                }
                this.mHandler = handler;
                this.mMagnifierUpdater = new Runnable() { // from class: android.widget.-$$Lambda$Magnifier$InternalPopupWindow$t9Cn2sIi2LBUhAVikvRPKKoAwIU
                    @Override // java.lang.Runnable
                    public final void run() {
                        Magnifier.InternalPopupWindow.this.doDraw();
                    }
                };
                this.mFrameDrawScheduled = false;
            } catch (Throwable th) {
                this.mRenderer.getRootNode().endRecording();
                throw th;
            }
        }

        private RenderNode createRenderNodeForBitmap(String name, float elevation, float cornerRadius) {
            RenderNode bitmapRenderNode = RenderNode.create(name, null);
            bitmapRenderNode.setLeftTopRightBottom(this.mOffsetX, this.mOffsetY, this.mOffsetX + this.mContentWidth, this.mOffsetY + this.mContentHeight);
            bitmapRenderNode.setElevation(elevation);
            Outline outline = new Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, cornerRadius);
            outline.setAlpha(1.0f);
            bitmapRenderNode.setOutline(outline);
            bitmapRenderNode.setClipToOutline(true);
            RecordingCanvas canvas = bitmapRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                canvas.drawColor(Color.GREEN);
                return bitmapRenderNode;
            } finally {
                bitmapRenderNode.endRecording();
            }
        }

        private RenderNode createRenderNodeForOverlay(String name, float cornerRadius) {
            RenderNode overlayRenderNode = RenderNode.create(name, null);
            overlayRenderNode.setLeftTopRightBottom(this.mOffsetX, this.mOffsetY, this.mOffsetX + this.mContentWidth, this.mOffsetY + this.mContentHeight);
            Outline outline = new Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, cornerRadius);
            outline.setAlpha(1.0f);
            overlayRenderNode.setOutline(outline);
            overlayRenderNode.setClipToOutline(true);
            return overlayRenderNode;
        }

        private void setupOverlay() {
            drawOverlay();
            this.mOverlay.setCallback(new Drawable.Callback() { // from class: android.widget.Magnifier.InternalPopupWindow.1
                @Override // android.graphics.drawable.Drawable.Callback
                public void invalidateDrawable(Drawable who) {
                    InternalPopupWindow.this.drawOverlay();
                    if (InternalPopupWindow.this.mCallback != null) {
                        InternalPopupWindow.this.updateCurrentContentForTesting();
                    }
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void scheduleDrawable(Drawable who, Runnable what, long when) {
                    Handler.getMain().postAtTime(what, who, when);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void unscheduleDrawable(Drawable who, Runnable what) {
                    Handler.getMain().removeCallbacks(what, who);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawOverlay() {
            RecordingCanvas canvas = this.mOverlayRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                this.mOverlay.setBounds(0, 0, this.mContentWidth, this.mContentHeight);
                this.mOverlay.draw(canvas);
            } finally {
                this.mOverlayRenderNode.endRecording();
            }
        }

        public void setContentPositionForNextDraw(int contentX, int contentY) {
            this.mWindowPositionX = contentX - this.mOffsetX;
            this.mWindowPositionY = contentY - this.mOffsetY;
            this.mPendingWindowPositionUpdate = true;
            requestUpdate();
        }

        public void updateContent(Bitmap bitmap) {
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
            }
            this.mBitmap = bitmap;
            requestUpdate();
        }

        private void requestUpdate() {
            if (this.mFrameDrawScheduled) {
                return;
            }
            Message request = Message.obtain(this.mHandler, this.mMagnifierUpdater);
            request.setAsynchronous(true);
            request.sendToTarget();
            this.mFrameDrawScheduled = true;
        }

        public void destroy() {
            this.mRenderer.destroy();
            this.mSurface.destroy();
            this.mSurfaceControl.remove();
            this.mSurfaceSession.kill();
            this.mHandler.removeCallbacks(this.mMagnifierUpdater);
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doDraw() {
            HardwareRenderer.FrameDrawingCallback callback;
            synchronized (this.mLock) {
                if (!this.mSurface.isValid()) {
                    return;
                }
                RecordingCanvas canvas = this.mBitmapRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
                Rect srcRect = new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                Rect dstRect = new Rect(0, 0, this.mContentWidth, this.mContentHeight);
                Paint paint = new Paint();
                paint.setFilterBitmap(true);
                canvas.drawBitmap(this.mBitmap, srcRect, dstRect, paint);
                this.mBitmapRenderNode.endRecording();
                if (!this.mPendingWindowPositionUpdate && !this.mFirstDraw) {
                    callback = null;
                    this.mLastDrawContentPositionX = this.mWindowPositionX + this.mOffsetX;
                    this.mLastDrawContentPositionY = this.mWindowPositionY + this.mOffsetY;
                    this.mFrameDrawScheduled = false;
                    this.mRenderer.draw(callback);
                    if (this.mCallback == null) {
                        updateCurrentContentForTesting();
                        this.mCallback.onOperationComplete();
                        return;
                    }
                    return;
                }
                final boolean firstDraw = this.mFirstDraw;
                this.mFirstDraw = false;
                final boolean updateWindowPosition = this.mPendingWindowPositionUpdate;
                this.mPendingWindowPositionUpdate = false;
                final int pendingX = this.mWindowPositionX;
                final int pendingY = this.mWindowPositionY;
                HardwareRenderer.FrameDrawingCallback callback2 = new HardwareRenderer.FrameDrawingCallback() { // from class: android.widget.-$$Lambda$Magnifier$InternalPopupWindow$qfjMrDJVvOQUv9_kKVdpLzbaJ-A
                    @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                    public final void onFrameDraw(long j) {
                        Magnifier.InternalPopupWindow.lambda$doDraw$0(Magnifier.InternalPopupWindow.this, updateWindowPosition, pendingX, pendingY, firstDraw, j);
                    }
                };
                this.mRenderer.setLightCenter(this.mDisplay, pendingX, pendingY);
                callback = callback2;
                this.mLastDrawContentPositionX = this.mWindowPositionX + this.mOffsetX;
                this.mLastDrawContentPositionY = this.mWindowPositionY + this.mOffsetY;
                this.mFrameDrawScheduled = false;
                this.mRenderer.draw(callback);
                if (this.mCallback == null) {
                }
            }
        }

        public static /* synthetic */ void lambda$doDraw$0(InternalPopupWindow internalPopupWindow, boolean updateWindowPosition, int pendingX, int pendingY, boolean firstDraw, long frame) {
            if (!internalPopupWindow.mSurface.isValid()) {
                return;
            }
            SurfaceControl.openTransaction();
            internalPopupWindow.mSurfaceControl.deferTransactionUntil(internalPopupWindow.mSurface, frame);
            if (updateWindowPosition) {
                internalPopupWindow.mSurfaceControl.setPosition(pendingX, pendingY);
            }
            if (firstDraw) {
                internalPopupWindow.mSurfaceControl.setLayer(5);
                internalPopupWindow.mSurfaceControl.show();
            }
            SurfaceControl.closeTransaction();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateCurrentContentForTesting() {
            Canvas canvas = new Canvas(this.mCurrentContent);
            Rect bounds = new Rect(0, 0, this.mContentWidth, this.mContentHeight);
            if (this.mBitmap != null && !this.mBitmap.isRecycled()) {
                Rect originalBounds = new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                canvas.drawBitmap(this.mBitmap, originalBounds, bounds, (Paint) null);
            }
            this.mOverlay.setBounds(bounds);
            this.mOverlay.draw(canvas);
        }
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private int mBottomContentBound;
        private boolean mClippingEnabled;
        private float mCornerRadius;
        private float mElevation;
        private int mHeight;
        private int mHorizontalDefaultSourceToMagnifierOffset;
        private int mLeftContentBound;
        private Drawable mOverlay;
        private int mRightContentBound;
        private int mTopContentBound;
        private int mVerticalDefaultSourceToMagnifierOffset;
        private View mView;
        private int mWidth;
        private float mZoom;

        public Builder(View view) {
            this.mView = (View) Preconditions.checkNotNull(view);
            applyDefaults();
        }

        private void applyDefaults() {
            Resources resources = this.mView.getContext().getResources();
            this.mWidth = resources.getDimensionPixelSize(C3132R.dimen.default_magnifier_width);
            this.mHeight = resources.getDimensionPixelSize(C3132R.dimen.default_magnifier_height);
            this.mElevation = resources.getDimension(C3132R.dimen.default_magnifier_elevation);
            this.mCornerRadius = resources.getDimension(C3132R.dimen.default_magnifier_corner_radius);
            this.mZoom = resources.getFloat(C3132R.dimen.default_magnifier_zoom);
            this.mHorizontalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(C3132R.dimen.default_magnifier_horizontal_offset);
            this.mVerticalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(C3132R.dimen.default_magnifier_vertical_offset);
            this.mOverlay = new ColorDrawable(resources.getColor(C3132R.color.default_magnifier_color_overlay, null));
            this.mClippingEnabled = true;
            this.mLeftContentBound = 1;
            this.mTopContentBound = 1;
            this.mRightContentBound = 1;
            this.mBottomContentBound = 1;
        }

        public Builder setSize(int width, int height) {
            Preconditions.checkArgumentPositive(width, "Width should be positive");
            Preconditions.checkArgumentPositive(height, "Height should be positive");
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        public Builder setInitialZoom(float zoom) {
            Preconditions.checkArgumentPositive(zoom, "Zoom should be positive");
            this.mZoom = zoom;
            return this;
        }

        public Builder setElevation(float elevation) {
            Preconditions.checkArgumentNonNegative(elevation, "Elevation should be non-negative");
            this.mElevation = elevation;
            return this;
        }

        public Builder setCornerRadius(float cornerRadius) {
            Preconditions.checkArgumentNonNegative(cornerRadius, "Corner radius should be non-negative");
            this.mCornerRadius = cornerRadius;
            return this;
        }

        public Builder setOverlay(Drawable overlay) {
            this.mOverlay = overlay;
            return this;
        }

        public Builder setDefaultSourceToMagnifierOffset(int horizontalOffset, int verticalOffset) {
            this.mHorizontalDefaultSourceToMagnifierOffset = horizontalOffset;
            this.mVerticalDefaultSourceToMagnifierOffset = verticalOffset;
            return this;
        }

        public Builder setClippingEnabled(boolean clip) {
            this.mClippingEnabled = clip;
            return this;
        }

        public Builder setSourceBounds(int left, int top, int right, int bottom) {
            this.mLeftContentBound = left;
            this.mTopContentBound = top;
            this.mRightContentBound = right;
            this.mBottomContentBound = bottom;
            return this;
        }

        public Magnifier build() {
            return new Magnifier(this);
        }
    }

    public void setOnOperationCompleteCallback(Callback callback) {
        this.mCallback = callback;
        if (this.mWindow != null) {
            this.mWindow.mCallback = callback;
        }
    }

    public Bitmap getContent() {
        Bitmap bitmap;
        if (this.mWindow != null) {
            synchronized (this.mWindow.mLock) {
                bitmap = this.mWindow.mCurrentContent;
            }
            return bitmap;
        }
        return null;
    }

    public Bitmap getOriginalContent() {
        Bitmap createBitmap;
        if (this.mWindow != null) {
            synchronized (this.mWindow.mLock) {
                createBitmap = Bitmap.createBitmap(this.mWindow.mBitmap);
            }
            return createBitmap;
        }
        return null;
    }

    public static PointF getMagnifierDefaultSize() {
        Resources resources = Resources.getSystem();
        float density = resources.getDisplayMetrics().density;
        PointF size = new PointF();
        size.f61x = resources.getDimension(C3132R.dimen.default_magnifier_width) / density;
        size.f62y = resources.getDimension(C3132R.dimen.default_magnifier_height) / density;
        return size;
    }
}
