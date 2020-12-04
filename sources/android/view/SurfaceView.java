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
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

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
    /* access modifiers changed from: private */
    public Rect mRTLastReportedPosition;
    @UnsupportedAppUsage
    int mRequestedFormat;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    int mRequestedHeight;
    boolean mRequestedVisible;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    int mRequestedWidth;
    Paint mRoundedViewportPaint;
    /* access modifiers changed from: private */
    public volatile boolean mRtHandlingPositionUpdates;
    /* access modifiers changed from: private */
    public SurfaceControl.Transaction mRtTransaction;
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
        this(context, (AttributeSet) null);
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
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            public void onScrollChanged() {
                SurfaceView.this.updateSurface();
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener() {
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
        this.mLastLockTime = 0;
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
        this.mPositionListener = new RenderNode.PositionUpdateListener() {
            public void positionChanged(long frameNumber, int left, int top, int right, int bottom) {
                if (SurfaceView.this.mSurfaceControl != null) {
                    boolean unused = SurfaceView.this.mRtHandlingPositionUpdates = true;
                    if (SurfaceView.this.mRTLastReportedPosition.left != left || SurfaceView.this.mRTLastReportedPosition.top != top || SurfaceView.this.mRTLastReportedPosition.right != right || SurfaceView.this.mRTLastReportedPosition.bottom != bottom) {
                        try {
                            SurfaceView.this.mRTLastReportedPosition.set(left, top, right, bottom);
                            SurfaceView.this.setParentSpaceRectangle(SurfaceView.this.mRTLastReportedPosition, frameNumber);
                        } catch (Exception ex) {
                            Log.e(SurfaceView.TAG, "Exception from repositionChild", ex);
                        }
                    }
                }
            }

            public void positionLost(long frameNumber) {
                SurfaceView.this.mRTLastReportedPosition.setEmpty();
                if (SurfaceView.this.mSurfaceControl != null) {
                    if (frameNumber > 0) {
                        SurfaceView.this.mRtTransaction.deferTransactionUntilSurface(SurfaceView.this.mSurfaceControl, SurfaceView.this.getViewRootImpl().mSurface, frameNumber);
                    }
                    SurfaceView.this.mRtTransaction.hide(SurfaceView.this.mSurfaceControl);
                    SurfaceView.this.mRtTransaction.apply();
                }
            }
        };
        this.mSurfaceHolder = new SurfaceHolder() {
            private static final String LOG_TAG = "SurfaceHolder";

            public boolean isCreating() {
                return SurfaceView.this.mIsCreating;
            }

            public void addCallback(SurfaceHolder.Callback callback) {
                synchronized (SurfaceView.this.mCallbacks) {
                    if (!SurfaceView.this.mCallbacks.contains(callback)) {
                        SurfaceView.this.mCallbacks.add(callback);
                    }
                }
            }

            public void removeCallback(SurfaceHolder.Callback callback) {
                synchronized (SurfaceView.this.mCallbacks) {
                    SurfaceView.this.mCallbacks.remove(callback);
                }
            }

            public void setFixedSize(int width, int height) {
                if (SurfaceView.this.mRequestedWidth != width || SurfaceView.this.mRequestedHeight != height) {
                    SurfaceView.this.mRequestedWidth = width;
                    SurfaceView.this.mRequestedHeight = height;
                    SurfaceView.this.requestLayout();
                }
            }

            public void setSizeFromLayout() {
                if (SurfaceView.this.mRequestedWidth != -1 || SurfaceView.this.mRequestedHeight != -1) {
                    SurfaceView surfaceView = SurfaceView.this;
                    SurfaceView.this.mRequestedHeight = -1;
                    surfaceView.mRequestedWidth = -1;
                    SurfaceView.this.requestLayout();
                }
            }

            public void setFormat(int format) {
                if (format == -1) {
                    format = 4;
                }
                SurfaceView.this.mRequestedFormat = format;
                if (SurfaceView.this.mSurfaceControl != null) {
                    SurfaceView.this.updateSurface();
                }
            }

            @Deprecated
            public void setType(int type) {
            }

            public void setKeepScreenOn(boolean screenOn) {
                SurfaceView.this.runOnUiThread(new Runnable(screenOn) {
                    private final /* synthetic */ boolean f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        SurfaceView.this.setKeepScreenOn(this.f$1);
                    }
                });
            }

            public Canvas lockCanvas() {
                return internalLockCanvas((Rect) null, false);
            }

            public Canvas lockCanvas(Rect inOutDirty) {
                return internalLockCanvas(inOutDirty, false);
            }

            public Canvas lockHardwareCanvas() {
                return internalLockCanvas((Rect) null, true);
            }

            private Canvas internalLockCanvas(Rect dirty, boolean hardware) {
                SurfaceView.this.mSurfaceLock.lock();
                Canvas c = null;
                if (!SurfaceView.this.mDrawingStopped && SurfaceView.this.mSurfaceControl != null) {
                    if (hardware) {
                        try {
                            c = SurfaceView.this.mSurface.lockHardwareCanvas();
                        } catch (Exception e) {
                            Log.e(LOG_TAG, "Exception locking surface", e);
                        }
                    } else {
                        c = SurfaceView.this.mSurface.lockCanvas(dirty);
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

            public void unlockCanvasAndPost(Canvas canvas) {
                SurfaceView.this.mSurface.unlockCanvasAndPost(canvas);
                SurfaceView.this.mSurfaceLock.unlock();
            }

            public Surface getSurface() {
                return SurfaceView.this.mSurface;
            }

            public Rect getSurfaceFrame() {
                return SurfaceView.this.mSurfaceFrame;
            }
        };
        this.mRenderNode.addPositionUpdateListener(this.mPositionListener);
        setWillNotDraw(true);
    }

    public SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    private void updateRequestedVisibility() {
        this.mRequestedVisible = this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
    }

    public void windowStopped(boolean stopped) {
        this.mWindowStopped = stopped;
        updateRequestedVisibility();
        updateSurface();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewRootImpl().addWindowStoppedCallback(this);
        boolean z = false;
        this.mWindowStopped = false;
        if (getVisibility() == 0) {
            z = true;
        }
        this.mViewVisibility = z;
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

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mWindowVisibility = visibility == 0;
        updateRequestedVisibility();
        updateSurface();
    }

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

    /* access modifiers changed from: private */
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
        Log.e(TAG, System.identityHashCode(this) + "finished drawing but no pending report draw (extra call to draw completion runnable?)");
    }

    /* access modifiers changed from: package-private */
    public void notifyDrawFinished() {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.pendingDrawFinished();
        }
        this.mPendingReportDraws--;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
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

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public boolean setFrame(int left, int top, int right, int bottom) {
        boolean result = super.setFrame(left, top, right, bottom);
        updateSurface();
        return result;
    }

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
                region.op(l, t, l + w, t + h, Region.Op.UNION);
            }
        }
        if (PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            return false;
        }
        return opaque;
    }

    public void draw(Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 0) {
            clearSurfaceViewPort(canvas);
        }
        super.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 128) {
            clearSurfaceViewPort(canvas);
        }
        super.dispatchDraw(canvas);
    }

    private void clearSurfaceViewPort(Canvas canvas) {
        if (this.mCornerRadius > 0.0f) {
            canvas.getClipBounds(this.mTmpRect);
            canvas.drawRoundRect((float) this.mTmpRect.left, (float) this.mTmpRect.top, (float) this.mTmpRect.right, (float) this.mTmpRect.bottom, this.mCornerRadius, this.mCornerRadius, this.mRoundedViewportPaint);
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
        if (this.mBackgroundControl != null) {
            if (this.mSubLayer >= 0 || (this.mSurfaceFlags & 1024) == 0) {
                this.mBackgroundControl.hide();
                return;
            }
            this.mBackgroundControl.show();
            this.mBackgroundControl.setRelativeLayer(viewRoot, Integer.MIN_VALUE);
        }
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

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0276, code lost:
        if (r1.mRtHandlingPositionUpdates == false) goto L_0x0278;
     */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0326 A[SYNTHETIC, Splitter:B:159:0x0326] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x033e  */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x038f A[Catch:{ all -> 0x0384, all -> 0x0397 }] */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x03b5 A[SYNTHETIC, Splitter:B:199:0x03b5] */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x0418 A[Catch:{ all -> 0x0420 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateSurface() {
        /*
            r25 = this;
            r1 = r25
            boolean r0 = r1.mHaveFrame
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            android.view.ViewRootImpl r2 = r25.getViewRootImpl()
            if (r2 == 0) goto L_0x049d
            android.view.Surface r0 = r2.mSurface
            if (r0 == 0) goto L_0x049d
            android.view.Surface r0 = r2.mSurface
            boolean r0 = r0.isValid()
            if (r0 != 0) goto L_0x001b
            goto L_0x049d
        L_0x001b:
            android.content.res.CompatibilityInfo$Translator r0 = r2.mTranslator
            r1.mTranslator = r0
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator
            if (r0 == 0) goto L_0x002a
            android.view.Surface r0 = r1.mSurface
            android.content.res.CompatibilityInfo$Translator r3 = r1.mTranslator
            r0.setCompatibilityTranslator(r3)
        L_0x002a:
            int r0 = r1.mRequestedWidth
            if (r0 > 0) goto L_0x0032
            int r0 = r25.getWidth()
        L_0x0032:
            r3 = r0
            int r0 = r1.mRequestedHeight
            if (r0 > 0) goto L_0x003b
            int r0 = r25.getHeight()
        L_0x003b:
            r4 = r0
            int r0 = r1.mFormat
            int r5 = r1.mRequestedFormat
            r6 = 1
            r7 = 0
            if (r0 == r5) goto L_0x0046
            r0 = r6
            goto L_0x0047
        L_0x0046:
            r0 = r7
        L_0x0047:
            r5 = r0
            boolean r0 = r1.mVisible
            boolean r8 = r1.mRequestedVisible
            if (r0 == r8) goto L_0x0050
            r0 = r6
            goto L_0x0051
        L_0x0050:
            r0 = r7
        L_0x0051:
            r8 = r0
            android.view.SurfaceControl r0 = r1.mSurfaceControl
            if (r0 == 0) goto L_0x005a
            if (r5 != 0) goto L_0x005a
            if (r8 == 0) goto L_0x0060
        L_0x005a:
            boolean r0 = r1.mRequestedVisible
            if (r0 == 0) goto L_0x0060
            r0 = r6
            goto L_0x0061
        L_0x0060:
            r0 = r7
        L_0x0061:
            r9 = r0
            int r0 = r1.mSurfaceWidth
            if (r0 != r3) goto L_0x006d
            int r0 = r1.mSurfaceHeight
            if (r0 == r4) goto L_0x006b
            goto L_0x006d
        L_0x006b:
            r0 = r7
            goto L_0x006e
        L_0x006d:
            r0 = r6
        L_0x006e:
            r10 = r0
            boolean r0 = r1.mWindowVisibility
            boolean r11 = r1.mLastWindowVisibility
            if (r0 == r11) goto L_0x0077
            r0 = r6
            goto L_0x0078
        L_0x0077:
            r0 = r7
        L_0x0078:
            r11 = r0
            r12 = 0
            if (r9 != 0) goto L_0x0132
            if (r5 != 0) goto L_0x0132
            if (r10 != 0) goto L_0x0132
            if (r8 != 0) goto L_0x0132
            if (r11 == 0) goto L_0x008a
            r17 = r11
            r18 = r12
            goto L_0x0136
        L_0x008a:
            int[] r0 = r1.mLocation
            r1.getLocationInSurface(r0)
            int r0 = r1.mWindowSpaceLeft
            int[] r13 = r1.mLocation
            r13 = r13[r7]
            if (r0 != r13) goto L_0x00a2
            int r0 = r1.mWindowSpaceTop
            int[] r13 = r1.mLocation
            r13 = r13[r6]
            if (r0 == r13) goto L_0x00a0
            goto L_0x00a2
        L_0x00a0:
            r0 = r7
            goto L_0x00a3
        L_0x00a2:
            r0 = r6
        L_0x00a3:
            r13 = r0
            int r0 = r25.getWidth()
            android.graphics.Rect r14 = r1.mScreenRect
            int r14 = r14.width()
            if (r0 != r14) goto L_0x00bf
            int r0 = r25.getHeight()
            android.graphics.Rect r14 = r1.mScreenRect
            int r14 = r14.height()
            if (r0 == r14) goto L_0x00bd
            goto L_0x00bf
        L_0x00bd:
            r0 = r7
            goto L_0x00c0
        L_0x00bf:
            r0 = r6
        L_0x00c0:
            r14 = r0
            if (r13 != 0) goto L_0x00cb
            if (r14 == 0) goto L_0x00c6
            goto L_0x00cb
        L_0x00c6:
            r17 = r11
            r18 = r12
            goto L_0x012e
        L_0x00cb:
            int[] r0 = r1.mLocation
            r0 = r0[r7]
            r1.mWindowSpaceLeft = r0
            int[] r0 = r1.mLocation
            r0 = r0[r6]
            r1.mWindowSpaceTop = r0
            int[] r0 = r1.mLocation
            int r15 = r25.getWidth()
            r0[r7] = r15
            int[] r0 = r1.mLocation
            int r15 = r25.getHeight()
            r0[r6] = r15
            android.graphics.Rect r0 = r1.mScreenRect
            int r15 = r1.mWindowSpaceLeft
            int r6 = r1.mWindowSpaceTop
            r17 = r11
            int r11 = r1.mWindowSpaceLeft
            r18 = r12
            int[] r12 = r1.mLocation
            r7 = r12[r7]
            int r11 = r11 + r7
            int r7 = r1.mWindowSpaceTop
            int[] r12 = r1.mLocation
            r16 = 1
            r12 = r12[r16]
            int r7 = r7 + r12
            r0.set(r15, r6, r11, r7)
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator
            if (r0 == 0) goto L_0x010f
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator
            android.graphics.Rect r6 = r1.mScreenRect
            r0.translateRectInAppWindowToScreen(r6)
        L_0x010f:
            android.view.SurfaceControl r0 = r1.mSurfaceControl
            if (r0 != 0) goto L_0x0114
            return
        L_0x0114:
            boolean r0 = r25.isHardwareAccelerated()
            if (r0 == 0) goto L_0x011e
            boolean r0 = r1.mRtHandlingPositionUpdates
            if (r0 != 0) goto L_0x012e
        L_0x011e:
            android.graphics.Rect r0 = r1.mScreenRect     // Catch:{ Exception -> 0x0126 }
            r6 = -1
            r1.setParentSpaceRectangle(r0, r6)     // Catch:{ Exception -> 0x0126 }
            goto L_0x012e
        L_0x0126:
            r0 = move-exception
            java.lang.String r6 = "SurfaceView"
            java.lang.String r7 = "Exception configuring surface"
            android.util.Log.e(r6, r7, r0)
        L_0x012e:
            r24 = r5
            goto L_0x049c
        L_0x0132:
            r17 = r11
            r18 = r12
        L_0x0136:
            int[] r0 = r1.mLocation
            r1.getLocationInWindow(r0)
            boolean r0 = r1.mRequestedVisible     // Catch:{ Exception -> 0x048d }
            r1.mVisible = r0     // Catch:{ Exception -> 0x048d }
            r6 = r0
            int[] r0 = r1.mLocation     // Catch:{ Exception -> 0x048d }
            r0 = r0[r7]     // Catch:{ Exception -> 0x048d }
            r1.mWindowSpaceLeft = r0     // Catch:{ Exception -> 0x048d }
            int[] r0 = r1.mLocation     // Catch:{ Exception -> 0x048d }
            r11 = 1
            r0 = r0[r11]     // Catch:{ Exception -> 0x048d }
            r1.mWindowSpaceTop = r0     // Catch:{ Exception -> 0x048d }
            r1.mSurfaceWidth = r3     // Catch:{ Exception -> 0x048d }
            r1.mSurfaceHeight = r4     // Catch:{ Exception -> 0x048d }
            int r0 = r1.mRequestedFormat     // Catch:{ Exception -> 0x048d }
            r1.mFormat = r0     // Catch:{ Exception -> 0x048d }
            boolean r0 = r1.mWindowVisibility     // Catch:{ Exception -> 0x048d }
            r1.mLastWindowVisibility = r0     // Catch:{ Exception -> 0x048d }
            android.graphics.Rect r0 = r1.mScreenRect     // Catch:{ Exception -> 0x048d }
            int r11 = r1.mWindowSpaceLeft     // Catch:{ Exception -> 0x048d }
            r0.left = r11     // Catch:{ Exception -> 0x048d }
            android.graphics.Rect r0 = r1.mScreenRect     // Catch:{ Exception -> 0x048d }
            int r11 = r1.mWindowSpaceTop     // Catch:{ Exception -> 0x048d }
            r0.top = r11     // Catch:{ Exception -> 0x048d }
            android.graphics.Rect r0 = r1.mScreenRect     // Catch:{ Exception -> 0x048d }
            int r11 = r1.mWindowSpaceLeft     // Catch:{ Exception -> 0x048d }
            int r12 = r25.getWidth()     // Catch:{ Exception -> 0x048d }
            int r11 = r11 + r12
            r0.right = r11     // Catch:{ Exception -> 0x048d }
            android.graphics.Rect r0 = r1.mScreenRect     // Catch:{ Exception -> 0x048d }
            int r11 = r1.mWindowSpaceTop     // Catch:{ Exception -> 0x048d }
            int r12 = r25.getHeight()     // Catch:{ Exception -> 0x048d }
            int r11 = r11 + r12
            r0.bottom = r11     // Catch:{ Exception -> 0x048d }
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator     // Catch:{ Exception -> 0x048d }
            if (r0 == 0) goto L_0x018e
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator     // Catch:{ Exception -> 0x0187 }
            android.graphics.Rect r11 = r1.mScreenRect     // Catch:{ Exception -> 0x0187 }
            r0.translateRectInAppWindowToScreen(r11)     // Catch:{ Exception -> 0x0187 }
            goto L_0x018e
        L_0x0187:
            r0 = move-exception
            r24 = r5
        L_0x018a:
            r12 = r18
            goto L_0x0492
        L_0x018e:
            android.graphics.Rect r0 = r25.getParentSurfaceInsets()     // Catch:{ Exception -> 0x048d }
            r11 = r0
            android.graphics.Rect r0 = r1.mScreenRect     // Catch:{ Exception -> 0x048d }
            int r12 = r11.left     // Catch:{ Exception -> 0x048d }
            int r13 = r11.top     // Catch:{ Exception -> 0x048d }
            r0.offset(r12, r13)     // Catch:{ Exception -> 0x048d }
            if (r9 == 0) goto L_0x0237
            int r0 = r1.mSubLayer     // Catch:{ Exception -> 0x0187 }
            r2.createBoundsSurface(r0)     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceSession r0 = new android.view.SurfaceSession     // Catch:{ Exception -> 0x0187 }
            r0.<init>()     // Catch:{ Exception -> 0x0187 }
            r1.mSurfaceSession = r0     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ Exception -> 0x0187 }
            r1.mDeferredDestroySurfaceControl = r0     // Catch:{ Exception -> 0x0187 }
            r25.updateOpaqueFlag()     // Catch:{ Exception -> 0x0187 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0187 }
            r0.<init>()     // Catch:{ Exception -> 0x0187 }
            java.lang.String r12 = "SurfaceView - "
            r0.append(r12)     // Catch:{ Exception -> 0x0187 }
            java.lang.CharSequence r12 = r2.getTitle()     // Catch:{ Exception -> 0x0187 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0187 }
            r0.append(r12)     // Catch:{ Exception -> 0x0187 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = new android.view.SurfaceControl$Builder     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceSession r13 = r1.mSurfaceSession     // Catch:{ Exception -> 0x0187 }
            r12.<init>(r13)     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setName(r0)     // Catch:{ Exception -> 0x0187 }
            int r13 = r1.mSurfaceFlags     // Catch:{ Exception -> 0x0187 }
            r13 = r13 & 1024(0x400, float:1.435E-42)
            if (r13 == 0) goto L_0x01dd
            r13 = 1
            goto L_0x01df
        L_0x01dd:
            r13 = r7
        L_0x01df:
            android.view.SurfaceControl$Builder r12 = r12.setOpaque(r13)     // Catch:{ Exception -> 0x0187 }
            int r13 = r1.mSurfaceWidth     // Catch:{ Exception -> 0x0187 }
            int r14 = r1.mSurfaceHeight     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setBufferSize(r13, r14)     // Catch:{ Exception -> 0x0187 }
            int r13 = r1.mFormat     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setFormat(r13)     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl r13 = r2.getSurfaceControl()     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setParent(r13)     // Catch:{ Exception -> 0x0187 }
            int r13 = r1.mSurfaceFlags     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setFlags(r13)     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl r12 = r12.build()     // Catch:{ Exception -> 0x0187 }
            r1.mSurfaceControl = r12     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = new android.view.SurfaceControl$Builder     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceSession r13 = r1.mSurfaceSession     // Catch:{ Exception -> 0x0187 }
            r12.<init>(r13)     // Catch:{ Exception -> 0x0187 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0187 }
            r13.<init>()     // Catch:{ Exception -> 0x0187 }
            java.lang.String r14 = "Background for -"
            r13.append(r14)     // Catch:{ Exception -> 0x0187 }
            r13.append(r0)     // Catch:{ Exception -> 0x0187 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setName(r13)     // Catch:{ Exception -> 0x0187 }
            r13 = 1
            android.view.SurfaceControl$Builder r12 = r12.setOpaque(r13)     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setColorLayer()     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl r13 = r1.mSurfaceControl     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl$Builder r12 = r12.setParent(r13)     // Catch:{ Exception -> 0x0187 }
            android.view.SurfaceControl r12 = r12.build()     // Catch:{ Exception -> 0x0187 }
            r1.mBackgroundControl = r12     // Catch:{ Exception -> 0x0187 }
            goto L_0x023c
        L_0x0237:
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ Exception -> 0x048d }
            if (r0 != 0) goto L_0x023c
            return
        L_0x023c:
            r12 = 0
            java.util.concurrent.locks.ReentrantLock r0 = r1.mSurfaceLock     // Catch:{ Exception -> 0x048d }
            r0.lock()     // Catch:{ Exception -> 0x048d }
            if (r6 != 0) goto L_0x0246
            r0 = 1
            goto L_0x0247
        L_0x0246:
            r0 = r7
        L_0x0247:
            r1.mDrawingStopped = r0     // Catch:{ all -> 0x047f }
            android.view.SurfaceControl.openTransaction()     // Catch:{ all -> 0x047f }
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x0474 }
            int r13 = r1.mSubLayer     // Catch:{ all -> 0x0474 }
            r0.setLayer(r13)     // Catch:{ all -> 0x0474 }
            boolean r0 = r1.mViewVisibility     // Catch:{ all -> 0x0474 }
            if (r0 == 0) goto L_0x0264
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x025d }
            r0.show()     // Catch:{ all -> 0x025d }
            goto L_0x0269
        L_0x025d:
            r0 = move-exception
            r24 = r5
            r21 = r11
            goto L_0x0479
        L_0x0264:
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x0474 }
            r0.hide()     // Catch:{ all -> 0x0474 }
        L_0x0269:
            android.view.SurfaceControl r0 = r2.getSurfaceControl()     // Catch:{ all -> 0x0474 }
            r1.updateBackgroundVisibilityInTransaction(r0)     // Catch:{ all -> 0x0474 }
            if (r10 != 0) goto L_0x0278
            if (r9 != 0) goto L_0x0278
            boolean r0 = r1.mRtHandlingPositionUpdates     // Catch:{ all -> 0x025d }
            if (r0 != 0) goto L_0x02ac
        L_0x0278:
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x0474 }
            android.graphics.Rect r13 = r1.mScreenRect     // Catch:{ all -> 0x0474 }
            int r13 = r13.left     // Catch:{ all -> 0x0474 }
            float r13 = (float) r13     // Catch:{ all -> 0x0474 }
            android.graphics.Rect r14 = r1.mScreenRect     // Catch:{ all -> 0x0474 }
            int r14 = r14.top     // Catch:{ all -> 0x0474 }
            float r14 = (float) r14     // Catch:{ all -> 0x0474 }
            r0.setPosition(r13, r14)     // Catch:{ all -> 0x0474 }
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x0474 }
            android.graphics.Rect r13 = r1.mScreenRect     // Catch:{ all -> 0x0474 }
            int r13 = r13.width()     // Catch:{ all -> 0x0474 }
            float r13 = (float) r13     // Catch:{ all -> 0x0474 }
            int r14 = r1.mSurfaceWidth     // Catch:{ all -> 0x0474 }
            float r14 = (float) r14     // Catch:{ all -> 0x0474 }
            float r13 = r13 / r14
            android.graphics.Rect r14 = r1.mScreenRect     // Catch:{ all -> 0x0474 }
            int r14 = r14.height()     // Catch:{ all -> 0x0474 }
            float r14 = (float) r14     // Catch:{ all -> 0x0474 }
            int r15 = r1.mSurfaceHeight     // Catch:{ all -> 0x0474 }
            float r15 = (float) r15     // Catch:{ all -> 0x0474 }
            float r14 = r14 / r15
            r15 = 0
            r0.setMatrix(r13, r15, r15, r14)     // Catch:{ all -> 0x0474 }
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x0474 }
            int r13 = r1.mSurfaceWidth     // Catch:{ all -> 0x0474 }
            int r14 = r1.mSurfaceHeight     // Catch:{ all -> 0x0474 }
            r0.setWindowCrop(r13, r14)     // Catch:{ all -> 0x0474 }
        L_0x02ac:
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x0474 }
            float r13 = r1.mCornerRadius     // Catch:{ all -> 0x0474 }
            r0.setCornerRadius(r13)     // Catch:{ all -> 0x0474 }
            if (r10 == 0) goto L_0x02c0
            if (r9 != 0) goto L_0x02c0
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ all -> 0x025d }
            int r13 = r1.mSurfaceWidth     // Catch:{ all -> 0x025d }
            int r14 = r1.mSurfaceHeight     // Catch:{ all -> 0x025d }
            r0.setBufferSize(r13, r14)     // Catch:{ all -> 0x025d }
        L_0x02c0:
            android.view.SurfaceControl.closeTransaction()     // Catch:{ all -> 0x047f }
            if (r10 != 0) goto L_0x02c8
            if (r9 == 0) goto L_0x02cb
        L_0x02c8:
            r0 = 1
            r18 = r0
        L_0x02cb:
            android.graphics.Rect r0 = r1.mSurfaceFrame     // Catch:{ all -> 0x047f }
            r0.left = r7     // Catch:{ all -> 0x047f }
            android.graphics.Rect r0 = r1.mSurfaceFrame     // Catch:{ all -> 0x047f }
            r0.top = r7     // Catch:{ all -> 0x047f }
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator     // Catch:{ all -> 0x047f }
            if (r0 != 0) goto L_0x02eb
            android.graphics.Rect r0 = r1.mSurfaceFrame     // Catch:{ all -> 0x02e4 }
            int r13 = r1.mSurfaceWidth     // Catch:{ all -> 0x02e4 }
            r0.right = r13     // Catch:{ all -> 0x02e4 }
            android.graphics.Rect r0 = r1.mSurfaceFrame     // Catch:{ all -> 0x02e4 }
            int r13 = r1.mSurfaceHeight     // Catch:{ all -> 0x02e4 }
            r0.bottom = r13     // Catch:{ all -> 0x02e4 }
            goto L_0x0305
        L_0x02e4:
            r0 = move-exception
            r24 = r5
            r21 = r11
            goto L_0x0484
        L_0x02eb:
            android.content.res.CompatibilityInfo$Translator r0 = r1.mTranslator     // Catch:{ all -> 0x047f }
            float r0 = r0.applicationInvertedScale     // Catch:{ all -> 0x047f }
            android.graphics.Rect r13 = r1.mSurfaceFrame     // Catch:{ all -> 0x047f }
            int r14 = r1.mSurfaceWidth     // Catch:{ all -> 0x047f }
            float r14 = (float) r14     // Catch:{ all -> 0x047f }
            float r14 = r14 * r0
            r15 = 1056964608(0x3f000000, float:0.5)
            float r14 = r14 + r15
            int r14 = (int) r14     // Catch:{ all -> 0x047f }
            r13.right = r14     // Catch:{ all -> 0x047f }
            android.graphics.Rect r13 = r1.mSurfaceFrame     // Catch:{ all -> 0x047f }
            int r14 = r1.mSurfaceHeight     // Catch:{ all -> 0x047f }
            float r14 = (float) r14     // Catch:{ all -> 0x047f }
            float r14 = r14 * r0
            float r14 = r14 + r15
            int r14 = (int) r14     // Catch:{ all -> 0x047f }
            r13.bottom = r14     // Catch:{ all -> 0x047f }
        L_0x0305:
            android.graphics.Rect r0 = r1.mSurfaceFrame     // Catch:{ all -> 0x047f }
            int r0 = r0.right     // Catch:{ all -> 0x047f }
            android.graphics.Rect r13 = r1.mSurfaceFrame     // Catch:{ all -> 0x047f }
            int r13 = r13.bottom     // Catch:{ all -> 0x047f }
            int r14 = r1.mLastSurfaceWidth     // Catch:{ all -> 0x047f }
            if (r14 != r0) goto L_0x0318
            int r14 = r1.mLastSurfaceHeight     // Catch:{ all -> 0x02e4 }
            if (r14 == r13) goto L_0x0316
            goto L_0x0318
        L_0x0316:
            r14 = r7
            goto L_0x0319
        L_0x0318:
            r14 = 1
        L_0x0319:
            r12 = r14
            r1.mLastSurfaceWidth = r0     // Catch:{ all -> 0x047f }
            r1.mLastSurfaceHeight = r13     // Catch:{ all -> 0x047f }
            java.util.concurrent.locks.ReentrantLock r0 = r1.mSurfaceLock     // Catch:{ Exception -> 0x048d }
            r0.unlock()     // Catch:{ Exception -> 0x048d }
            if (r6 == 0) goto L_0x0335
            boolean r0 = r1.mDrawFinished     // Catch:{ all -> 0x032c }
            if (r0 != 0) goto L_0x0335
            r0 = 1
            goto L_0x0336
        L_0x032c:
            r0 = move-exception
            r24 = r5
            r21 = r11
            r13 = r18
            goto L_0x045d
        L_0x0335:
            r0 = r7
        L_0x0336:
            r13 = r18 | r0
            r0 = 0
            r14 = r9
            boolean r15 = r1.mSurfaceCreated     // Catch:{ all -> 0x0458 }
            if (r15 == 0) goto L_0x038b
            if (r14 != 0) goto L_0x0348
            if (r6 != 0) goto L_0x0345
            if (r8 == 0) goto L_0x0345
            goto L_0x0348
        L_0x0345:
            r21 = r11
            goto L_0x038d
        L_0x0348:
            r1.mSurfaceCreated = r7     // Catch:{ all -> 0x0384 }
            android.view.Surface r15 = r1.mSurface     // Catch:{ all -> 0x0384 }
            boolean r15 = r15.isValid()     // Catch:{ all -> 0x0384 }
            if (r15 == 0) goto L_0x038b
            android.view.SurfaceHolder$Callback[] r15 = r25.getSurfaceCallbacks()     // Catch:{ all -> 0x0384 }
            r0 = r15
            int r15 = r0.length     // Catch:{ all -> 0x0384 }
        L_0x0358:
            if (r7 >= r15) goto L_0x0370
            r18 = r0[r7]     // Catch:{ all -> 0x0384 }
            r19 = r18
            r20 = r0
            android.view.SurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x0384 }
            r21 = r11
            r11 = r19
            r11.surfaceDestroyed(r0)     // Catch:{ all -> 0x0397 }
            int r7 = r7 + 1
            r0 = r20
            r11 = r21
            goto L_0x0358
        L_0x0370:
            r20 = r0
            r21 = r11
            android.view.Surface r0 = r1.mSurface     // Catch:{ all -> 0x0397 }
            boolean r0 = r0.isValid()     // Catch:{ all -> 0x0397 }
            if (r0 == 0) goto L_0x0381
            android.view.Surface r0 = r1.mSurface     // Catch:{ all -> 0x0397 }
            r0.forceScopedDisconnect()     // Catch:{ all -> 0x0397 }
        L_0x0381:
            r0 = r20
            goto L_0x038d
        L_0x0384:
            r0 = move-exception
            r21 = r11
            r24 = r5
            goto L_0x045d
        L_0x038b:
            r21 = r11
        L_0x038d:
            if (r9 == 0) goto L_0x039c
            android.view.Surface r7 = r1.mSurface     // Catch:{ all -> 0x0397 }
            android.view.SurfaceControl r11 = r1.mSurfaceControl     // Catch:{ all -> 0x0397 }
            r7.copyFrom(r11)     // Catch:{ all -> 0x0397 }
            goto L_0x039c
        L_0x0397:
            r0 = move-exception
            r24 = r5
            goto L_0x045d
        L_0x039c:
            if (r10 == 0) goto L_0x03b3
            android.content.Context r7 = r25.getContext()     // Catch:{ all -> 0x0397 }
            android.content.pm.ApplicationInfo r7 = r7.getApplicationInfo()     // Catch:{ all -> 0x0397 }
            int r7 = r7.targetSdkVersion     // Catch:{ all -> 0x0397 }
            r11 = 26
            if (r7 >= r11) goto L_0x03b3
            android.view.Surface r7 = r1.mSurface     // Catch:{ all -> 0x0397 }
            android.view.SurfaceControl r11 = r1.mSurfaceControl     // Catch:{ all -> 0x0397 }
            r7.createFrom(r11)     // Catch:{ all -> 0x0397 }
        L_0x03b3:
            if (r6 == 0) goto L_0x043f
            android.view.Surface r7 = r1.mSurface     // Catch:{ all -> 0x043b }
            boolean r7 = r7.isValid()     // Catch:{ all -> 0x043b }
            if (r7 == 0) goto L_0x043f
            boolean r7 = r1.mSurfaceCreated     // Catch:{ all -> 0x043b }
            if (r7 != 0) goto L_0x03e5
            if (r14 != 0) goto L_0x03c5
            if (r8 == 0) goto L_0x03e5
        L_0x03c5:
            r7 = 1
            r1.mSurfaceCreated = r7     // Catch:{ all -> 0x0397 }
            r1.mIsCreating = r7     // Catch:{ all -> 0x0397 }
            if (r0 != 0) goto L_0x03d1
            android.view.SurfaceHolder$Callback[] r7 = r25.getSurfaceCallbacks()     // Catch:{ all -> 0x0397 }
            r0 = r7
        L_0x03d1:
            int r7 = r0.length     // Catch:{ all -> 0x0397 }
            r11 = 0
        L_0x03d3:
            if (r11 >= r7) goto L_0x03e3
            r15 = r0[r11]     // Catch:{ all -> 0x0397 }
            r22 = r0
            android.view.SurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x0397 }
            r15.surfaceCreated(r0)     // Catch:{ all -> 0x0397 }
            int r11 = r11 + 1
            r0 = r22
            goto L_0x03d3
        L_0x03e3:
            r22 = r0
        L_0x03e5:
            if (r9 != 0) goto L_0x03f3
            if (r5 != 0) goto L_0x03f3
            if (r10 != 0) goto L_0x03f3
            if (r8 != 0) goto L_0x03f3
            if (r12 == 0) goto L_0x03f0
            goto L_0x03f3
        L_0x03f0:
            r24 = r5
            goto L_0x0416
        L_0x03f3:
            if (r0 != 0) goto L_0x03fa
            android.view.SurfaceHolder$Callback[] r7 = r25.getSurfaceCallbacks()     // Catch:{ all -> 0x0397 }
            r0 = r7
        L_0x03fa:
            int r7 = r0.length     // Catch:{ all -> 0x043b }
            r11 = 0
        L_0x03fc:
            if (r11 >= r7) goto L_0x0412
            r15 = r0[r11]     // Catch:{ all -> 0x043b }
            r23 = r0
            android.view.SurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x043b }
            r24 = r5
            int r5 = r1.mFormat     // Catch:{ all -> 0x0420 }
            r15.surfaceChanged(r0, r5, r3, r4)     // Catch:{ all -> 0x0420 }
            int r11 = r11 + 1
            r0 = r23
            r5 = r24
            goto L_0x03fc
        L_0x0412:
            r23 = r0
            r24 = r5
        L_0x0416:
            if (r13 == 0) goto L_0x0441
            if (r0 != 0) goto L_0x0422
            android.view.SurfaceHolder$Callback[] r5 = r25.getSurfaceCallbacks()     // Catch:{ all -> 0x0420 }
            r0 = r5
            goto L_0x0422
        L_0x0420:
            r0 = move-exception
            goto L_0x045d
        L_0x0422:
            int r5 = r1.mPendingReportDraws     // Catch:{ all -> 0x0420 }
            r7 = 1
            int r5 = r5 + r7
            r1.mPendingReportDraws = r5     // Catch:{ all -> 0x0420 }
            r2.drawPending()     // Catch:{ all -> 0x0420 }
            com.android.internal.view.SurfaceCallbackHelper r5 = new com.android.internal.view.SurfaceCallbackHelper     // Catch:{ all -> 0x0420 }
            android.view.-$$Lambda$SurfaceView$SyyzxOgxKwZMRgiiTGcRYbOU5JY r7 = new android.view.-$$Lambda$SurfaceView$SyyzxOgxKwZMRgiiTGcRYbOU5JY     // Catch:{ all -> 0x0420 }
            r7.<init>()     // Catch:{ all -> 0x0420 }
            r5.<init>(r7)     // Catch:{ all -> 0x0420 }
            android.view.SurfaceHolder r7 = r1.mSurfaceHolder     // Catch:{ all -> 0x0420 }
            r5.dispatchSurfaceRedrawNeededAsync(r7, r0)     // Catch:{ all -> 0x0420 }
            goto L_0x0441
        L_0x043b:
            r0 = move-exception
            r24 = r5
            goto L_0x045d
        L_0x043f:
            r24 = r5
        L_0x0441:
            r5 = 0
            r1.mIsCreating = r5     // Catch:{ Exception -> 0x0471 }
            android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ Exception -> 0x0471 }
            if (r0 == 0) goto L_0x0454
            boolean r0 = r1.mSurfaceCreated     // Catch:{ Exception -> 0x0471 }
            if (r0 != 0) goto L_0x0454
            android.view.Surface r0 = r1.mSurface     // Catch:{ Exception -> 0x0471 }
            r0.release()     // Catch:{ Exception -> 0x0471 }
            r25.releaseSurfaces()     // Catch:{ Exception -> 0x0471 }
        L_0x0454:
            r18 = r13
            goto L_0x049c
        L_0x0458:
            r0 = move-exception
            r24 = r5
            r21 = r11
        L_0x045d:
            r5 = 0
            r1.mIsCreating = r5     // Catch:{ Exception -> 0x0471 }
            android.view.SurfaceControl r5 = r1.mSurfaceControl     // Catch:{ Exception -> 0x0471 }
            if (r5 == 0) goto L_0x0470
            boolean r5 = r1.mSurfaceCreated     // Catch:{ Exception -> 0x0471 }
            if (r5 != 0) goto L_0x0470
            android.view.Surface r5 = r1.mSurface     // Catch:{ Exception -> 0x0471 }
            r5.release()     // Catch:{ Exception -> 0x0471 }
            r25.releaseSurfaces()     // Catch:{ Exception -> 0x0471 }
        L_0x0470:
            throw r0     // Catch:{ Exception -> 0x0471 }
        L_0x0471:
            r0 = move-exception
            r12 = r13
            goto L_0x0492
        L_0x0474:
            r0 = move-exception
            r24 = r5
            r21 = r11
        L_0x0479:
            android.view.SurfaceControl.closeTransaction()     // Catch:{ all -> 0x047d }
            throw r0     // Catch:{ all -> 0x047d }
        L_0x047d:
            r0 = move-exception
            goto L_0x0484
        L_0x047f:
            r0 = move-exception
            r24 = r5
            r21 = r11
        L_0x0484:
            java.util.concurrent.locks.ReentrantLock r5 = r1.mSurfaceLock     // Catch:{ Exception -> 0x048a }
            r5.unlock()     // Catch:{ Exception -> 0x048a }
            throw r0     // Catch:{ Exception -> 0x048a }
        L_0x048a:
            r0 = move-exception
            goto L_0x018a
        L_0x048d:
            r0 = move-exception
            r24 = r5
            r12 = r18
        L_0x0492:
            java.lang.String r5 = "SurfaceView"
            java.lang.String r6 = "Exception configuring surface"
            android.util.Log.e(r5, r6, r0)
            r18 = r12
        L_0x049c:
            return
        L_0x049d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.SurfaceView.updateSurface():void");
    }

    /* access modifiers changed from: private */
    public void onDrawFinished() {
        if (this.mDeferredDestroySurfaceControl != null) {
            this.mDeferredDestroySurfaceControl.remove();
            this.mDeferredDestroySurfaceControl = null;
        }
        runOnUiThread(new Runnable() {
            public final void run() {
                SurfaceView.this.performDrawFinished();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void applyChildSurfaceTransaction_renderWorker(SurfaceControl.Transaction t, Surface viewRootSurface, long nextViewRootFrameNumber) {
    }

    private void applySurfaceTransforms(SurfaceControl surface, Rect position, long frameNumber) {
        if (frameNumber > 0) {
            this.mRtTransaction.deferTransactionUntilSurface(surface, getViewRootImpl().mSurface, frameNumber);
        }
        this.mRtTransaction.setPosition(surface, (float) position.left, (float) position.top);
        this.mRtTransaction.setMatrix(surface, ((float) position.width()) / ((float) this.mSurfaceWidth), 0.0f, 0.0f, ((float) position.height()) / ((float) this.mSurfaceHeight));
        if (this.mViewVisibility) {
            this.mRtTransaction.show(surface);
        }
    }

    /* access modifiers changed from: private */
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

    /* access modifiers changed from: private */
    public void runOnUiThread(Runnable runnable) {
        Handler handler = getHandler();
        if (handler == null || handler.getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
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
        if (this.mBackgroundControl != null) {
            float[] colorComponents = {((float) Color.red(bgColor)) / 255.0f, ((float) Color.green(bgColor)) / 255.0f, ((float) Color.blue(bgColor)) / 255.0f};
            SurfaceControl.openTransaction();
            try {
                this.mBackgroundControl.setColor(colorComponents);
            } finally {
                SurfaceControl.closeTransaction();
            }
        }
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }
}
