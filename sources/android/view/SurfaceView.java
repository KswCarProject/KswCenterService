package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import com.android.internal.view.SurfaceCallbackHelper;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class SurfaceView extends View implements ViewRootImpl.WindowStoppedCallback {
    private static final boolean DEBUG = false;
    private static final String TAG = "SurfaceView";
    private boolean mAttachedToWindow;
    SurfaceControl mBackgroundControl;
    @UnsupportedAppUsage
    final ArrayList<SurfaceHolder.Callback> mCallbacks;
    final Configuration mConfiguration;
    float mCornerRadius;
    SurfaceControl mDeferredDestroySurfaceControl;
    boolean mDrawFinished;
    @UnsupportedAppUsage
    private final ViewTreeObserver.OnPreDrawListener mDrawListener;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    boolean mDrawingStopped;
    @UnsupportedAppUsage
    int mFormat;
    private boolean mGlobalListenersAdded;
    @UnsupportedAppUsage
    boolean mHaveFrame;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    boolean mIsCreating;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    long mLastLockTime;
    int mLastSurfaceHeight;
    int mLastSurfaceWidth;
    boolean mLastWindowVisibility;
    final int[] mLocation;
    private int mPendingReportDraws;
    private RenderNode.PositionUpdateListener mPositionListener;
    private Rect mRTLastReportedPosition;
    @UnsupportedAppUsage
    int mRequestedFormat;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    int mRequestedHeight;
    boolean mRequestedVisible;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    int mRequestedWidth;
    Paint mRoundedViewportPaint;
    private volatile boolean mRtHandlingPositionUpdates;
    private SurfaceControl.Transaction mRtTransaction;
    final Rect mScreenRect;
    private final ViewTreeObserver.OnScrollChangedListener mScrollChangedListener;
    int mSubLayer;
    @UnsupportedAppUsage
    final Surface mSurface;
    SurfaceControl mSurfaceControl;
    boolean mSurfaceCreated;
    private int mSurfaceFlags;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    final Rect mSurfaceFrame;
    int mSurfaceHeight;
    @UnsupportedAppUsage
    private final SurfaceHolder mSurfaceHolder;
    @UnsupportedAppUsage
    final ReentrantLock mSurfaceLock;
    SurfaceSession mSurfaceSession;
    int mSurfaceWidth;
    final Rect mTmpRect;
    private CompatibilityInfo.Translator mTranslator;
    boolean mViewVisibility;
    boolean mVisible;
    int mWindowSpaceLeft;
    int mWindowSpaceTop;
    boolean mWindowStopped;
    boolean mWindowVisibility;

    public SurfaceView(Context context) {
        this(context, null);
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCallbacks = new ArrayList<>();
        this.mLocation = new int[2];
        this.mSurfaceLock = new ReentrantLock();
        this.mSurface = new Surface();
        this.mDrawingStopped = true;
        this.mDrawFinished = false;
        this.mScreenRect = new Rect();
        this.mTmpRect = new Rect();
        this.mConfiguration = new Configuration();
        this.mSubLayer = -2;
        this.mIsCreating = false;
        this.mRtHandlingPositionUpdates = false;
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: android.view.SurfaceView.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                SurfaceView.this.updateSurface();
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.view.SurfaceView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SurfaceView.this.mHaveFrame = SurfaceView.this.getWidth() > 0 && SurfaceView.this.getHeight() > 0;
                SurfaceView.this.updateSurface();
                return true;
            }
        };
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mLastWindowVisibility = false;
        this.mViewVisibility = false;
        this.mWindowStopped = false;
        this.mRequestedWidth = -1;
        this.mRequestedHeight = -1;
        this.mRequestedFormat = 4;
        this.mHaveFrame = false;
        this.mSurfaceCreated = false;
        this.mLastLockTime = 0L;
        this.mVisible = false;
        this.mWindowSpaceLeft = -1;
        this.mWindowSpaceTop = -1;
        this.mSurfaceWidth = -1;
        this.mSurfaceHeight = -1;
        this.mFormat = -1;
        this.mSurfaceFrame = new Rect();
        this.mLastSurfaceWidth = -1;
        this.mLastSurfaceHeight = -1;
        this.mSurfaceFlags = 4;
        this.mRtTransaction = new SurfaceControl.Transaction();
        this.mRTLastReportedPosition = new Rect();
        this.mPositionListener = new RenderNode.PositionUpdateListener() { // from class: android.view.SurfaceView.3
            @Override // android.graphics.RenderNode.PositionUpdateListener
            public void positionChanged(long frameNumber, int left, int top, int right, int bottom) {
                if (SurfaceView.this.mSurfaceControl != null) {
                    SurfaceView.this.mRtHandlingPositionUpdates = true;
                    if (SurfaceView.this.mRTLastReportedPosition.left != left || SurfaceView.this.mRTLastReportedPosition.top != top || SurfaceView.this.mRTLastReportedPosition.right != right || SurfaceView.this.mRTLastReportedPosition.bottom != bottom) {
                        try {
                            SurfaceView.this.mRTLastReportedPosition.set(left, top, right, bottom);
                            SurfaceView.this.setParentSpaceRectangle(SurfaceView.this.mRTLastReportedPosition, frameNumber);
                        } catch (Exception ex) {
                            Log.m69e(SurfaceView.TAG, "Exception from repositionChild", ex);
                        }
                    }
                }
            }

            @Override // android.graphics.RenderNode.PositionUpdateListener
            public void positionLost(long frameNumber) {
                SurfaceView.this.mRTLastReportedPosition.setEmpty();
                if (SurfaceView.this.mSurfaceControl == null) {
                    return;
                }
                if (frameNumber > 0) {
                    ViewRootImpl viewRoot = SurfaceView.this.getViewRootImpl();
                    SurfaceView.this.mRtTransaction.deferTransactionUntilSurface(SurfaceView.this.mSurfaceControl, viewRoot.mSurface, frameNumber);
                }
                SurfaceView.this.mRtTransaction.hide(SurfaceView.this.mSurfaceControl);
                SurfaceView.this.mRtTransaction.apply();
            }
        };
        this.mSurfaceHolder = new SurfaceHolderC27114();
        this.mRenderNode.addPositionUpdateListener(this.mPositionListener);
        setWillNotDraw(true);
    }

    public SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    private void updateRequestedVisibility() {
        this.mRequestedVisible = this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
    }

    @Override // android.view.ViewRootImpl.WindowStoppedCallback
    public void windowStopped(boolean stopped) {
        this.mWindowStopped = stopped;
        updateRequestedVisibility();
        updateSurface();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewRootImpl().addWindowStoppedCallback(this);
        this.mWindowStopped = false;
        this.mViewVisibility = getVisibility() == 0;
        updateRequestedVisibility();
        this.mAttachedToWindow = true;
        this.mParent.requestTransparentRegion(this);
        if (!this.mGlobalListenersAdded) {
            ViewTreeObserver observer = getViewTreeObserver();
            observer.addOnScrollChangedListener(this.mScrollChangedListener);
            observer.addOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = true;
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mWindowVisibility = visibility == 0;
        updateRequestedVisibility();
        updateSurface();
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        boolean newRequestedVisible = false;
        this.mViewVisibility = visibility == 0;
        if (this.mWindowVisibility && this.mViewVisibility && !this.mWindowStopped) {
            newRequestedVisible = true;
        }
        if (newRequestedVisible != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = newRequestedVisible;
        updateSurface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performDrawFinished() {
        if (this.mPendingReportDraws > 0) {
            this.mDrawFinished = true;
            if (this.mAttachedToWindow) {
                notifyDrawFinished();
                invalidate();
                return;
            }
            return;
        }
        Log.m70e(TAG, System.identityHashCode(this) + "finished drawing but no pending report draw (extra call to draw completion runnable?)");
    }

    void notifyDrawFinished() {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.pendingDrawFinished();
        }
        this.mPendingReportDraws--;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.removeWindowStoppedCallback(this);
        }
        this.mAttachedToWindow = false;
        if (this.mGlobalListenersAdded) {
            ViewTreeObserver observer = getViewTreeObserver();
            observer.removeOnScrollChangedListener(this.mScrollChangedListener);
            observer.removeOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = false;
        }
        while (this.mPendingReportDraws > 0) {
            notifyDrawFinished();
        }
        this.mRequestedVisible = false;
        updateSurface();
        if (this.mSurfaceControl != null) {
            this.mSurfaceControl.remove();
        }
        this.mSurfaceControl = null;
        this.mHaveFrame = false;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        if (this.mRequestedWidth >= 0) {
            width = resolveSizeAndState(this.mRequestedWidth, widthMeasureSpec, 0);
        } else {
            width = getDefaultSize(0, widthMeasureSpec);
        }
        if (this.mRequestedHeight >= 0) {
            height = resolveSizeAndState(this.mRequestedHeight, heightMeasureSpec, 0);
        } else {
            height = getDefaultSize(0, heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    @Override // android.view.View
    @UnsupportedAppUsage
    protected boolean setFrame(int left, int top, int right, int bottom) {
        boolean result = super.setFrame(left, top, right, bottom);
        updateSurface();
        return result;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(Region region) {
        if (isAboveParent() || !this.mDrawFinished) {
            return super.gatherTransparentRegion(region);
        }
        boolean opaque = true;
        if ((this.mPrivateFlags & 128) == 0) {
            opaque = super.gatherTransparentRegion(region);
        } else if (region != null) {
            int w = getWidth();
            int h = getHeight();
            if (w > 0 && h > 0) {
                getLocationInWindow(this.mLocation);
                int l = this.mLocation[0];
                int t = this.mLocation[1];
                region.m126op(l, t, l + w, t + h, Region.EnumC0685Op.UNION);
            }
        }
        if (PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            return false;
        }
        return opaque;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 0) {
            clearSurfaceViewPort(canvas);
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 128) {
            clearSurfaceViewPort(canvas);
        }
        super.dispatchDraw(canvas);
    }

    private void clearSurfaceViewPort(Canvas canvas) {
        if (this.mCornerRadius > 0.0f) {
            canvas.getClipBounds(this.mTmpRect);
            canvas.drawRoundRect(this.mTmpRect.left, this.mTmpRect.top, this.mTmpRect.right, this.mTmpRect.bottom, this.mCornerRadius, this.mCornerRadius, this.mRoundedViewportPaint);
            return;
        }
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    public void setCornerRadius(float cornerRadius) {
        this.mCornerRadius = cornerRadius;
        if (this.mCornerRadius > 0.0f && this.mRoundedViewportPaint == null) {
            this.mRoundedViewportPaint = new Paint(1);
            this.mRoundedViewportPaint.setBlendMode(BlendMode.CLEAR);
            this.mRoundedViewportPaint.setColor(0);
        }
        invalidate();
    }

    public void setZOrderMediaOverlay(boolean isMediaOverlay) {
        this.mSubLayer = isMediaOverlay ? -1 : -2;
    }

    public void setZOrderOnTop(boolean onTop) {
        if (onTop) {
            this.mSubLayer = 1;
        } else {
            this.mSubLayer = -2;
        }
    }

    public void setSecure(boolean isSecure) {
        if (isSecure) {
            this.mSurfaceFlags |= 128;
        } else {
            this.mSurfaceFlags &= -129;
        }
    }

    private void updateOpaqueFlag() {
        if (!PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            this.mSurfaceFlags |= 1024;
        } else {
            this.mSurfaceFlags &= -1025;
        }
    }

    private Rect getParentSurfaceInsets() {
        ViewRootImpl root = getViewRootImpl();
        if (root == null) {
            return null;
        }
        return root.mWindowAttributes.surfaceInsets;
    }

    private void updateBackgroundVisibilityInTransaction(SurfaceControl viewRoot) {
        if (this.mBackgroundControl == null) {
            return;
        }
        if (this.mSubLayer < 0 && (this.mSurfaceFlags & 1024) != 0) {
            this.mBackgroundControl.show();
            this.mBackgroundControl.setRelativeLayer(viewRoot, Integer.MIN_VALUE);
            return;
        }
        this.mBackgroundControl.hide();
    }

    private void releaseSurfaces() {
        if (this.mSurfaceControl != null) {
            this.mSurfaceControl.remove();
            this.mSurfaceControl = null;
        }
        if (this.mBackgroundControl != null) {
            this.mBackgroundControl.remove();
            this.mBackgroundControl = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:213:0x03f9, code lost:
        r0 = getSurfaceCallbacks();
     */
    /* JADX WARN: Removed duplicated region for block: B:162:0x033e  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x038f A[Catch: all -> 0x0397, TryCatch #8 {all -> 0x0397, blocks: (B:182:0x038f, B:186:0x039e, B:188:0x03ac, B:197:0x03c6, B:199:0x03cc, B:200:0x03d1, B:202:0x03d5, B:212:0x03f5, B:173:0x0366, B:174:0x0370, B:176:0x037c), top: B:279:0x0366 }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x039e A[Catch: all -> 0x0397, TryCatch #8 {all -> 0x0397, blocks: (B:182:0x038f, B:186:0x039e, B:188:0x03ac, B:197:0x03c6, B:199:0x03cc, B:200:0x03d1, B:202:0x03d5, B:212:0x03f5, B:173:0x0366, B:174:0x0370, B:176:0x037c), top: B:279:0x0366 }] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0448 A[Catch: Exception -> 0x0471, TryCatch #6 {Exception -> 0x0471, blocks: (B:231:0x0442, B:233:0x0448, B:235:0x044c, B:239:0x045d, B:241:0x0464, B:243:0x0468, B:244:0x0470), top: B:276:0x0324 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0464 A[Catch: Exception -> 0x0471, TryCatch #6 {Exception -> 0x0471, blocks: (B:231:0x0442, B:233:0x0448, B:235:0x044c, B:239:0x045d, B:241:0x0464, B:243:0x0468, B:244:0x0470), top: B:276:0x0324 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0326 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:287:0x03b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void updateSurface() {
        ViewRootImpl viewRoot;
        int i;
        int i2;
        boolean z;
        int i3;
        if (!this.mHaveFrame || (viewRoot = getViewRootImpl()) == null || viewRoot.mSurface == null || !viewRoot.mSurface.isValid()) {
            return;
        }
        this.mTranslator = viewRoot.mTranslator;
        if (this.mTranslator != null) {
            this.mSurface.setCompatibilityTranslator(this.mTranslator);
        }
        int myWidth = this.mRequestedWidth;
        if (myWidth <= 0) {
            myWidth = getWidth();
        }
        int myWidth2 = myWidth;
        int myHeight = this.mRequestedHeight;
        if (myHeight <= 0) {
            myHeight = getHeight();
        }
        int myHeight2 = myHeight;
        int i4 = 0;
        boolean formatChanged = this.mFormat != this.mRequestedFormat;
        boolean visibleChanged = this.mVisible != this.mRequestedVisible;
        boolean creating = (this.mSurfaceControl == null || formatChanged || visibleChanged) && this.mRequestedVisible;
        boolean sizeChanged = (this.mSurfaceWidth == myWidth2 && this.mSurfaceHeight == myHeight2) ? false : true;
        boolean windowVisibleChanged = this.mWindowVisibility != this.mLastWindowVisibility;
        if (creating || formatChanged || sizeChanged || visibleChanged) {
            i = 0;
        } else if (!windowVisibleChanged) {
            getLocationInSurface(this.mLocation);
            boolean positionChanged = (this.mWindowSpaceLeft == this.mLocation[0] && this.mWindowSpaceTop == this.mLocation[1]) ? false : true;
            boolean layoutSizeChanged = (getWidth() == this.mScreenRect.width() && getHeight() == this.mScreenRect.height()) ? false : true;
            if (positionChanged || layoutSizeChanged) {
                this.mWindowSpaceLeft = this.mLocation[0];
                this.mWindowSpaceTop = this.mLocation[1];
                this.mLocation[0] = getWidth();
                this.mLocation[1] = getHeight();
                this.mScreenRect.set(this.mWindowSpaceLeft, this.mWindowSpaceTop, this.mWindowSpaceLeft + this.mLocation[0], this.mWindowSpaceTop + this.mLocation[1]);
                if (this.mTranslator != null) {
                    this.mTranslator.translateRectInAppWindowToScreen(this.mScreenRect);
                }
                if (this.mSurfaceControl == null) {
                    return;
                }
                if (!isHardwareAccelerated() || !this.mRtHandlingPositionUpdates) {
                    try {
                        setParentSpaceRectangle(this.mScreenRect, -1L);
                    } catch (Exception ex) {
                        Log.m69e(TAG, "Exception configuring surface", ex);
                    }
                }
            }
            return;
        } else {
            i = 0;
        }
        getLocationInWindow(this.mLocation);
        try {
            boolean visible = this.mRequestedVisible;
            this.mVisible = visible;
            this.mWindowSpaceLeft = this.mLocation[0];
            this.mWindowSpaceTop = this.mLocation[1];
            this.mSurfaceWidth = myWidth2;
            this.mSurfaceHeight = myHeight2;
            this.mFormat = this.mRequestedFormat;
            this.mLastWindowVisibility = this.mWindowVisibility;
            this.mScreenRect.left = this.mWindowSpaceLeft;
            this.mScreenRect.top = this.mWindowSpaceTop;
            this.mScreenRect.right = this.mWindowSpaceLeft + getWidth();
            this.mScreenRect.bottom = this.mWindowSpaceTop + getHeight();
            if (this.mTranslator != null) {
                try {
                    this.mTranslator.translateRectInAppWindowToScreen(this.mScreenRect);
                } catch (Exception e) {
                    ex = e;
                    i2 = i;
                    Log.m69e(TAG, "Exception configuring surface", ex);
                    return;
                }
            }
            Rect surfaceInsets = getParentSurfaceInsets();
            this.mScreenRect.offset(surfaceInsets.left, surfaceInsets.top);
            if (creating) {
                viewRoot.createBoundsSurface(this.mSubLayer);
                this.mSurfaceSession = new SurfaceSession();
                this.mDeferredDestroySurfaceControl = this.mSurfaceControl;
                updateOpaqueFlag();
                String name = "SurfaceView - " + viewRoot.getTitle().toString();
                this.mSurfaceControl = new SurfaceControl.Builder(this.mSurfaceSession).setName(name).setOpaque((this.mSurfaceFlags & 1024) != 0).setBufferSize(this.mSurfaceWidth, this.mSurfaceHeight).setFormat(this.mFormat).setParent(viewRoot.getSurfaceControl()).setFlags(this.mSurfaceFlags).build();
                this.mBackgroundControl = new SurfaceControl.Builder(this.mSurfaceSession).setName("Background for -" + name).setOpaque(true).setColorLayer().setParent(this.mSurfaceControl).build();
            } else if (this.mSurfaceControl == null) {
                return;
            }
            this.mSurfaceLock.lock();
            try {
                this.mDrawingStopped = !visible;
                SurfaceControl.openTransaction();
                try {
                    this.mSurfaceControl.setLayer(this.mSubLayer);
                    if (this.mViewVisibility) {
                        try {
                            this.mSurfaceControl.show();
                        } catch (Throwable th) {
                            th = th;
                            try {
                                SurfaceControl.closeTransaction();
                                throw th;
                            } catch (Throwable th2) {
                                th = th2;
                                try {
                                    this.mSurfaceLock.unlock();
                                    throw th;
                                } catch (Exception e2) {
                                    ex = e2;
                                    i2 = i;
                                    Log.m69e(TAG, "Exception configuring surface", ex);
                                    return;
                                }
                            }
                        }
                    } else {
                        this.mSurfaceControl.hide();
                    }
                    updateBackgroundVisibilityInTransaction(viewRoot.getSurfaceControl());
                    if (sizeChanged || creating || !this.mRtHandlingPositionUpdates) {
                        this.mSurfaceControl.setPosition(this.mScreenRect.left, this.mScreenRect.top);
                        this.mSurfaceControl.setMatrix(this.mScreenRect.width() / this.mSurfaceWidth, 0.0f, 0.0f, this.mScreenRect.height() / this.mSurfaceHeight);
                        this.mSurfaceControl.setWindowCrop(this.mSurfaceWidth, this.mSurfaceHeight);
                    }
                    this.mSurfaceControl.setCornerRadius(this.mCornerRadius);
                    if (sizeChanged && !creating) {
                        this.mSurfaceControl.setBufferSize(this.mSurfaceWidth, this.mSurfaceHeight);
                    }
                    SurfaceControl.closeTransaction();
                    if (sizeChanged || creating) {
                        i = 1;
                    }
                    this.mSurfaceFrame.left = 0;
                    this.mSurfaceFrame.top = 0;
                    if (this.mTranslator == null) {
                        try {
                            this.mSurfaceFrame.right = this.mSurfaceWidth;
                            this.mSurfaceFrame.bottom = this.mSurfaceHeight;
                        } catch (Throwable th3) {
                            th = th3;
                            this.mSurfaceLock.unlock();
                            throw th;
                        }
                    } else {
                        float appInvertedScale = this.mTranslator.applicationInvertedScale;
                        this.mSurfaceFrame.right = (int) ((this.mSurfaceWidth * appInvertedScale) + 0.5f);
                        this.mSurfaceFrame.bottom = (int) ((this.mSurfaceHeight * appInvertedScale) + 0.5f);
                    }
                    int surfaceWidth = this.mSurfaceFrame.right;
                    int surfaceHeight = this.mSurfaceFrame.bottom;
                    try {
                        try {
                            if (this.mLastSurfaceWidth == surfaceWidth) {
                                if (this.mLastSurfaceHeight == surfaceHeight) {
                                    z = false;
                                    boolean realSizeChanged = z;
                                    this.mLastSurfaceWidth = surfaceWidth;
                                    this.mLastSurfaceHeight = surfaceHeight;
                                    this.mSurfaceLock.unlock();
                                    if (visible) {
                                        try {
                                            if (!this.mDrawFinished) {
                                                i3 = 1;
                                                int i5 = i | i3;
                                                SurfaceHolder.Callback[] callbacks = null;
                                                if (this.mSurfaceCreated) {
                                                    if (creating || (!visible && visibleChanged)) {
                                                        try {
                                                            this.mSurfaceCreated = false;
                                                            if (this.mSurface.isValid()) {
                                                                SurfaceHolder.Callback[] callbacks2 = getSurfaceCallbacks();
                                                                int length = callbacks2.length;
                                                                while (i4 < length) {
                                                                    SurfaceHolder.Callback c = callbacks2[i4];
                                                                    SurfaceHolder.Callback[] callbacks3 = callbacks2;
                                                                    Rect surfaceInsets2 = surfaceInsets;
                                                                    try {
                                                                        c.surfaceDestroyed(this.mSurfaceHolder);
                                                                        i4++;
                                                                        callbacks2 = callbacks3;
                                                                        surfaceInsets = surfaceInsets2;
                                                                    } catch (Throwable th4) {
                                                                        th = th4;
                                                                        this.mIsCreating = false;
                                                                        if (this.mSurfaceControl != null) {
                                                                        }
                                                                        throw th;
                                                                    }
                                                                }
                                                                SurfaceHolder.Callback[] callbacks4 = callbacks2;
                                                                if (this.mSurface.isValid()) {
                                                                    this.mSurface.forceScopedDisconnect();
                                                                }
                                                                callbacks = callbacks4;
                                                            }
                                                        } catch (Throwable th5) {
                                                            th = th5;
                                                            this.mIsCreating = false;
                                                            if (this.mSurfaceControl != null) {
                                                                this.mSurface.release();
                                                                releaseSurfaces();
                                                            }
                                                            throw th;
                                                        }
                                                    }
                                                    if (creating) {
                                                        this.mSurface.copyFrom(this.mSurfaceControl);
                                                    }
                                                    if (sizeChanged && getContext().getApplicationInfo().targetSdkVersion < 26) {
                                                        this.mSurface.createFrom(this.mSurfaceControl);
                                                    }
                                                    if (visible) {
                                                        try {
                                                            if (this.mSurface.isValid()) {
                                                                if (!this.mSurfaceCreated && (creating || visibleChanged)) {
                                                                    this.mSurfaceCreated = true;
                                                                    this.mIsCreating = true;
                                                                    if (callbacks == null) {
                                                                        callbacks = getSurfaceCallbacks();
                                                                    }
                                                                    int length2 = callbacks.length;
                                                                    int i6 = 0;
                                                                    while (i6 < length2) {
                                                                        SurfaceHolder.Callback c2 = callbacks[i6];
                                                                        c2.surfaceCreated(this.mSurfaceHolder);
                                                                        i6++;
                                                                        callbacks = callbacks;
                                                                    }
                                                                }
                                                                if (!creating && !formatChanged && !sizeChanged && !visibleChanged && !realSizeChanged) {
                                                                    if (i5 != 0) {
                                                                        if (callbacks == null) {
                                                                            callbacks = getSurfaceCallbacks();
                                                                        }
                                                                        this.mPendingReportDraws++;
                                                                        viewRoot.drawPending();
                                                                        SurfaceCallbackHelper sch = new SurfaceCallbackHelper(new Runnable() { // from class: android.view.-$$Lambda$SurfaceView$SyyzxOgxKwZMRgiiTGcRYbOU5JY
                                                                            @Override // java.lang.Runnable
                                                                            public final void run() {
                                                                                SurfaceView.this.onDrawFinished();
                                                                            }
                                                                        });
                                                                        sch.dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, callbacks);
                                                                    }
                                                                    this.mIsCreating = false;
                                                                    if (this.mSurfaceControl != null && !this.mSurfaceCreated) {
                                                                        this.mSurface.release();
                                                                        releaseSurfaces();
                                                                    }
                                                                    return;
                                                                }
                                                                int length3 = callbacks.length;
                                                                int i7 = 0;
                                                                while (i7 < length3) {
                                                                    SurfaceHolder.Callback c3 = callbacks[i7];
                                                                    SurfaceHolder.Callback[] callbacks5 = callbacks;
                                                                    boolean formatChanged2 = formatChanged;
                                                                    try {
                                                                        c3.surfaceChanged(this.mSurfaceHolder, this.mFormat, myWidth2, myHeight2);
                                                                        i7++;
                                                                        callbacks = callbacks5;
                                                                        formatChanged = formatChanged2;
                                                                    } catch (Throwable th6) {
                                                                        th = th6;
                                                                        this.mIsCreating = false;
                                                                        if (this.mSurfaceControl != null && !this.mSurfaceCreated) {
                                                                            this.mSurface.release();
                                                                            releaseSurfaces();
                                                                        }
                                                                        throw th;
                                                                    }
                                                                }
                                                                if (i5 != 0) {
                                                                }
                                                                this.mIsCreating = false;
                                                                if (this.mSurfaceControl != null) {
                                                                    this.mSurface.release();
                                                                    releaseSurfaces();
                                                                }
                                                                return;
                                                            }
                                                        } catch (Throwable th7) {
                                                            th = th7;
                                                        }
                                                    }
                                                    this.mIsCreating = false;
                                                    if (this.mSurfaceControl != null) {
                                                    }
                                                    return;
                                                }
                                                if (creating) {
                                                }
                                                if (sizeChanged) {
                                                    this.mSurface.createFrom(this.mSurfaceControl);
                                                }
                                                if (visible) {
                                                }
                                                this.mIsCreating = false;
                                                if (this.mSurfaceControl != null) {
                                                }
                                                return;
                                            }
                                        } catch (Throwable th8) {
                                            th = th8;
                                            this.mIsCreating = false;
                                            if (this.mSurfaceControl != null) {
                                            }
                                            throw th;
                                        }
                                    }
                                    i3 = 0;
                                    int i52 = i | i3;
                                    SurfaceHolder.Callback[] callbacks6 = null;
                                    if (this.mSurfaceCreated) {
                                    }
                                    if (creating) {
                                    }
                                    if (sizeChanged) {
                                    }
                                    if (visible) {
                                    }
                                    this.mIsCreating = false;
                                    if (this.mSurfaceControl != null) {
                                    }
                                    return;
                                }
                            }
                            if (this.mSurfaceCreated) {
                            }
                            if (creating) {
                            }
                            if (sizeChanged) {
                            }
                            if (visible) {
                            }
                            this.mIsCreating = false;
                            if (this.mSurfaceControl != null) {
                            }
                            return;
                        } catch (Throwable th9) {
                            th = th9;
                        }
                        if (visible) {
                        }
                        i3 = 0;
                        int i522 = i | i3;
                        SurfaceHolder.Callback[] callbacks62 = null;
                    } catch (Exception e3) {
                        ex = e3;
                        i2 = surfaceHeight;
                        Log.m69e(TAG, "Exception configuring surface", ex);
                        return;
                    }
                    z = true;
                    boolean realSizeChanged2 = z;
                    this.mLastSurfaceWidth = surfaceWidth;
                    this.mLastSurfaceHeight = surfaceHeight;
                    this.mSurfaceLock.unlock();
                } catch (Throwable th10) {
                    th = th10;
                }
            } catch (Throwable th11) {
                th = th11;
            }
        } catch (Exception e4) {
            ex = e4;
            i2 = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDrawFinished() {
        if (this.mDeferredDestroySurfaceControl != null) {
            this.mDeferredDestroySurfaceControl.remove();
            this.mDeferredDestroySurfaceControl = null;
        }
        runOnUiThread(new Runnable() { // from class: android.view.-$$Lambda$SurfaceView$Cs7TGTdA1lXf9qW8VOJAfEsMjdk
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceView.this.performDrawFinished();
            }
        });
    }

    protected void applyChildSurfaceTransaction_renderWorker(SurfaceControl.Transaction t, Surface viewRootSurface, long nextViewRootFrameNumber) {
    }

    private void applySurfaceTransforms(SurfaceControl surface, Rect position, long frameNumber) {
        if (frameNumber > 0) {
            ViewRootImpl viewRoot = getViewRootImpl();
            this.mRtTransaction.deferTransactionUntilSurface(surface, viewRoot.mSurface, frameNumber);
        }
        this.mRtTransaction.setPosition(surface, position.left, position.top);
        this.mRtTransaction.setMatrix(surface, position.width() / this.mSurfaceWidth, 0.0f, 0.0f, position.height() / this.mSurfaceHeight);
        if (this.mViewVisibility) {
            this.mRtTransaction.show(surface);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setParentSpaceRectangle(Rect position, long frameNumber) {
        ViewRootImpl viewRoot = getViewRootImpl();
        applySurfaceTransforms(this.mSurfaceControl, position, frameNumber);
        applyChildSurfaceTransaction_renderWorker(this.mRtTransaction, viewRoot.mSurface, frameNumber);
        this.mRtTransaction.apply();
    }

    private SurfaceHolder.Callback[] getSurfaceCallbacks() {
        SurfaceHolder.Callback[] callbacks;
        synchronized (this.mCallbacks) {
            callbacks = new SurfaceHolder.Callback[this.mCallbacks.size()];
            this.mCallbacks.toArray(callbacks);
        }
        return callbacks;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnUiThread(Runnable runnable) {
        Handler handler = getHandler();
        if (handler != null && handler.getLooper() != Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @UnsupportedAppUsage
    public boolean isFixedSize() {
        return (this.mRequestedWidth == -1 && this.mRequestedHeight == -1) ? false : true;
    }

    private boolean isAboveParent() {
        return this.mSubLayer >= 0;
    }

    public void setResizeBackgroundColor(int bgColor) {
        if (this.mBackgroundControl == null) {
            return;
        }
        float[] colorComponents = {Color.red(bgColor) / 255.0f, Color.green(bgColor) / 255.0f, Color.blue(bgColor) / 255.0f};
        SurfaceControl.openTransaction();
        try {
            this.mBackgroundControl.setColor(colorComponents);
        } finally {
            SurfaceControl.closeTransaction();
        }
    }

    /* renamed from: android.view.SurfaceView$4 */
    /* loaded from: classes4.dex */
    class SurfaceHolderC27114 implements SurfaceHolder {
        private static final String LOG_TAG = "SurfaceHolder";

        SurfaceHolderC27114() {
        }

        @Override // android.view.SurfaceHolder
        public boolean isCreating() {
            return SurfaceView.this.mIsCreating;
        }

        @Override // android.view.SurfaceHolder
        public void addCallback(SurfaceHolder.Callback callback) {
            synchronized (SurfaceView.this.mCallbacks) {
                if (!SurfaceView.this.mCallbacks.contains(callback)) {
                    SurfaceView.this.mCallbacks.add(callback);
                }
            }
        }

        @Override // android.view.SurfaceHolder
        public void removeCallback(SurfaceHolder.Callback callback) {
            synchronized (SurfaceView.this.mCallbacks) {
                SurfaceView.this.mCallbacks.remove(callback);
            }
        }

        @Override // android.view.SurfaceHolder
        public void setFixedSize(int width, int height) {
            if (SurfaceView.this.mRequestedWidth != width || SurfaceView.this.mRequestedHeight != height) {
                SurfaceView.this.mRequestedWidth = width;
                SurfaceView.this.mRequestedHeight = height;
                SurfaceView.this.requestLayout();
            }
        }

        @Override // android.view.SurfaceHolder
        public void setSizeFromLayout() {
            if (SurfaceView.this.mRequestedWidth != -1 || SurfaceView.this.mRequestedHeight != -1) {
                SurfaceView surfaceView = SurfaceView.this;
                SurfaceView.this.mRequestedHeight = -1;
                surfaceView.mRequestedWidth = -1;
                SurfaceView.this.requestLayout();
            }
        }

        @Override // android.view.SurfaceHolder
        public void setFormat(int format) {
            if (format == -1) {
                format = 4;
            }
            SurfaceView.this.mRequestedFormat = format;
            if (SurfaceView.this.mSurfaceControl != null) {
                SurfaceView.this.updateSurface();
            }
        }

        @Override // android.view.SurfaceHolder
        @Deprecated
        public void setType(int type) {
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(final boolean screenOn) {
            SurfaceView.this.runOnUiThread(new Runnable() { // from class: android.view.-$$Lambda$SurfaceView$4$wAwzCgpoBmqWbw6GlT0xJXSxjm4
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceView.this.setKeepScreenOn(screenOn);
                }
            });
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockCanvas() {
            return internalLockCanvas(null, false);
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockCanvas(Rect inOutDirty) {
            return internalLockCanvas(inOutDirty, false);
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockHardwareCanvas() {
            return internalLockCanvas(null, true);
        }

        private Canvas internalLockCanvas(Rect dirty, boolean hardware) {
            SurfaceView.this.mSurfaceLock.lock();
            Canvas c = null;
            if (!SurfaceView.this.mDrawingStopped && SurfaceView.this.mSurfaceControl != null) {
                try {
                    if (hardware) {
                        c = SurfaceView.this.mSurface.lockHardwareCanvas();
                    } else {
                        c = SurfaceView.this.mSurface.lockCanvas(dirty);
                    }
                } catch (Exception e) {
                    Log.m69e(LOG_TAG, "Exception locking surface", e);
                }
            }
            if (c != null) {
                SurfaceView.this.mLastLockTime = SystemClock.uptimeMillis();
                return c;
            }
            long now = SystemClock.uptimeMillis();
            long nextTime = SurfaceView.this.mLastLockTime + 100;
            if (nextTime > now) {
                try {
                    Thread.sleep(nextTime - now);
                } catch (InterruptedException e2) {
                }
                now = SystemClock.uptimeMillis();
            }
            SurfaceView.this.mLastLockTime = now;
            SurfaceView.this.mSurfaceLock.unlock();
            return null;
        }

        @Override // android.view.SurfaceHolder
        public void unlockCanvasAndPost(Canvas canvas) {
            SurfaceView.this.mSurface.unlockCanvasAndPost(canvas);
            SurfaceView.this.mSurfaceLock.unlock();
        }

        @Override // android.view.SurfaceHolder
        public Surface getSurface() {
            return SurfaceView.this.mSurface;
        }

        @Override // android.view.SurfaceHolder
        public Rect getSurfaceFrame() {
            return SurfaceView.this.mSurfaceFrame;
        }
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }
}
