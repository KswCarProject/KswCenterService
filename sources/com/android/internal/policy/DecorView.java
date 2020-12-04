package com.android.internal.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowCallbacks;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.android.internal.R;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.StandaloneActionMode;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.BackgroundFallback;
import com.android.internal.widget.DecorCaptionView;
import com.android.internal.widget.FloatingToolbar;
import java.util.List;

public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks {
    private static final boolean DEBUG_MEASURE = false;
    private static final int DECOR_SHADOW_FOCUSED_HEIGHT_IN_DIP = 20;
    private static final int DECOR_SHADOW_UNFOCUSED_HEIGHT_IN_DIP = 5;
    public static final ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(2, 134217728, 80, 5, 3, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, 16908336, 0);
    private static final ViewOutlineProvider PIP_OUTLINE_PROVIDER = new ViewOutlineProvider() {
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };
    private static final int SCRIM_LIGHT = -419430401;
    public static final ColorViewAttributes STATUS_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(4, 67108864, 48, 3, 5, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, 16908335, 1024);
    private static final boolean SWEEP_OPEN_MENU = false;
    private static final String TAG = "DecorView";
    private boolean mAllowUpdateElevation = false;
    private boolean mApplyFloatingHorizontalInsets = false;
    private boolean mApplyFloatingVerticalInsets = false;
    private float mAvailableWidth;
    private BackdropFrameRenderer mBackdropFrameRenderer = null;
    private final BackgroundFallback mBackgroundFallback = new BackgroundFallback();
    private Insets mBackgroundInsets = Insets.NONE;
    private final Rect mBackgroundPadding = new Rect();
    private final int mBarEnterExitDuration;
    private Drawable mCaptionBackgroundDrawable;
    private boolean mChanging;
    ViewGroup mContentRoot;
    private DecorCaptionView mDecorCaptionView;
    int mDefaultOpacity = -1;
    private int mDownY;
    private boolean mDrawLegacyNavigationBarBackground;
    private final Rect mDrawingBounds = new Rect();
    private boolean mElevationAdjustedForStack = false;
    /* access modifiers changed from: private */
    public ObjectAnimator mFadeAnim;
    private final int mFeatureId;
    /* access modifiers changed from: private */
    public ActionMode mFloatingActionMode;
    private View mFloatingActionModeOriginatingView;
    private final Rect mFloatingInsets = new Rect();
    private FloatingToolbar mFloatingToolbar;
    private ViewTreeObserver.OnPreDrawListener mFloatingToolbarPreDrawListener;
    final boolean mForceWindowDrawsBarBackgrounds;
    private final Rect mFrameOffsets = new Rect();
    private final Rect mFramePadding = new Rect();
    private boolean mHasCaption = false;
    private final Interpolator mHideInterpolator;
    private final Paint mHorizontalResizeShadowPaint = new Paint();
    private boolean mIsInPictureInPictureMode;
    private Drawable.Callback mLastBackgroundDrawableCb = null;
    private Insets mLastBackgroundInsets = Insets.NONE;
    @UnsupportedAppUsage
    private int mLastBottomInset = 0;
    private boolean mLastHasBottomStableInset = false;
    private boolean mLastHasLeftStableInset = false;
    private boolean mLastHasRightStableInset = false;
    private boolean mLastHasTopStableInset = false;
    @UnsupportedAppUsage
    private int mLastLeftInset = 0;
    private Drawable mLastOriginalBackgroundDrawable;
    private ViewOutlineProvider mLastOutlineProvider;
    @UnsupportedAppUsage
    private int mLastRightInset = 0;
    private boolean mLastShouldAlwaysConsumeSystemBars = false;
    private int mLastTopInset = 0;
    private int mLastWindowFlags = 0;
    private final Paint mLegacyNavigationBarBackgroundPaint = new Paint();
    String mLogTag = TAG;
    private Drawable mMenuBackground;
    private final ColorViewState mNavigationColorViewState = new ColorViewState(NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES);
    private Drawable mOriginalBackgroundDrawable;
    private Rect mOutsets = new Rect();
    ActionMode mPrimaryActionMode;
    /* access modifiers changed from: private */
    public PopupWindow mPrimaryActionModePopup;
    /* access modifiers changed from: private */
    public ActionBarContextView mPrimaryActionModeView;
    private int mResizeMode = -1;
    private final int mResizeShadowSize;
    private Drawable mResizingBackgroundDrawable;
    private int mRootScrollY = 0;
    private final int mSemiTransparentBarColor;
    private final Interpolator mShowInterpolator;
    /* access modifiers changed from: private */
    public Runnable mShowPrimaryActionModePopup;
    private final ColorViewState mStatusColorViewState = new ColorViewState(STATUS_BAR_COLOR_VIEW_ATTRIBUTES);
    private View mStatusGuard;
    private Rect mTempRect;
    private Drawable mUserCaptionBackgroundDrawable;
    private final Paint mVerticalResizeShadowPaint = new Paint();
    private boolean mWatchingForMenu;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public PhoneWindow mWindow;
    private boolean mWindowResizeCallbacksAdded = false;

    DecorView(Context context, int featureId, PhoneWindow window, WindowManager.LayoutParams params) {
        super(context);
        boolean z = false;
        this.mFeatureId = featureId;
        this.mShowInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mHideInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mBarEnterExitDuration = context.getResources().getInteger(R.integer.dock_enter_exit_duration);
        if (context.getResources().getBoolean(R.bool.config_forceWindowDrawsStatusBarBackground) && context.getApplicationInfo().targetSdkVersion >= 24) {
            z = true;
        }
        this.mForceWindowDrawsBarBackgrounds = z;
        this.mSemiTransparentBarColor = context.getResources().getColor(R.color.system_bar_background_semi_transparent, (Resources.Theme) null);
        updateAvailableWidth();
        setWindow(window);
        updateLogTag(params);
        this.mResizeShadowSize = context.getResources().getDimensionPixelSize(R.dimen.resize_shadow_size);
        initResizingPaints();
        this.mLegacyNavigationBarBackgroundPaint.setColor(-16777216);
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundFallback(Drawable fallbackDrawable) {
        this.mBackgroundFallback.setDrawable(fallbackDrawable);
        setWillNotDraw(getBackground() == null && !this.mBackgroundFallback.hasFallback());
    }

    public Drawable getBackgroundFallback() {
        return this.mBackgroundFallback.getDrawable();
    }

    public boolean gatherTransparentRegion(Region region) {
        return gatherTransparentRegion(this.mStatusColorViewState, region) || gatherTransparentRegion(this.mNavigationColorViewState, region) || super.gatherTransparentRegion(region);
    }

    /* access modifiers changed from: package-private */
    public boolean gatherTransparentRegion(ColorViewState colorViewState, Region region) {
        if (colorViewState.view == null || !colorViewState.visible || !isResizing()) {
            return false;
        }
        return colorViewState.view.gatherTransparentRegion(region);
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
        this.mBackgroundFallback.draw(this, this.mContentRoot, c, this.mWindow.mContentParent, this.mStatusColorViewState.view, this.mNavigationColorViewState.view);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled;
        int keyCode = event.getKeyCode();
        boolean isDown = event.getAction() == 0;
        if (isDown && event.getRepeatCount() == 0) {
            if (this.mWindow.mPanelChordingKey > 0 && this.mWindow.mPanelChordingKey != keyCode && dispatchKeyShortcutEvent(event)) {
                return true;
            }
            if (this.mWindow.mPreparedPanel != null && this.mWindow.mPreparedPanel.isOpen && this.mWindow.performPanelShortcut(this.mWindow.mPreparedPanel, keyCode, event, 0)) {
                return true;
            }
        }
        if (!this.mWindow.isDestroyed()) {
            Window.Callback cb = this.mWindow.getCallback();
            if (cb == null || this.mFeatureId >= 0) {
                handled = super.dispatchKeyEvent(event);
            } else {
                handled = cb.dispatchKeyEvent(event);
            }
            if (handled) {
                return true;
            }
        }
        if (isDown) {
            return this.mWindow.onKeyDown(this.mFeatureId, event.getKeyCode(), event);
        }
        return this.mWindow.onKeyUp(this.mFeatureId, event.getKeyCode(), event);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent ev) {
        if (this.mWindow.mPreparedPanel == null || !this.mWindow.performPanelShortcut(this.mWindow.mPreparedPanel, ev.getKeyCode(), ev, 1)) {
            Window.Callback cb = this.mWindow.getCallback();
            if ((cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchKeyShortcutEvent(ev) : cb.dispatchKeyShortcutEvent(ev)) {
                return true;
            }
            PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
            if (st != null && this.mWindow.mPreparedPanel == null) {
                this.mWindow.preparePanel(st, ev);
                boolean handled = this.mWindow.performPanelShortcut(st, ev.getKeyCode(), ev, 1);
                st.isPrepared = false;
                if (handled) {
                    return true;
                }
            }
            return false;
        }
        if (this.mWindow.mPreparedPanel != null) {
            this.mWindow.mPreparedPanel.isHandled = true;
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTouchEvent(ev) : cb.dispatchTouchEvent(ev);
    }

    public boolean dispatchTrackballEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTrackballEvent(ev) : cb.dispatchTrackballEvent(ev);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchGenericMotionEvent(ev) : cb.dispatchGenericMotionEvent(ev);
    }

    public boolean superDispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 4) {
            int action = event.getAction();
            if (this.mPrimaryActionMode != null) {
                if (action == 1) {
                    this.mPrimaryActionMode.finish();
                }
                return true;
            }
        }
        if (super.dispatchKeyEvent(event) != 0) {
            return true;
        }
        if (getViewRootImpl() == null || !getViewRootImpl().dispatchUnhandledKeyEvent(event)) {
            return false;
        }
        return true;
    }

    public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return super.dispatchKeyShortcutEvent(event);
    }

    public boolean superDispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public boolean superDispatchTrackballEvent(MotionEvent event) {
        return super.dispatchTrackballEvent(event);
    }

    public boolean superDispatchGenericMotionEvent(MotionEvent event) {
        return super.dispatchGenericMotionEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return onInterceptTouchEvent(event);
    }

    private boolean isOutOfInnerBounds(int x, int y) {
        return x < 0 || y < 0 || x > getWidth() || y > getHeight();
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (this.mHasCaption && isShowingCaption() && action == 0 && isOutOfInnerBounds((int) event.getX(), (int) event.getY())) {
            return true;
        }
        if (this.mFeatureId < 0 || action != 0 || !isOutOfBounds((int) event.getX(), (int) event.getY())) {
            return false;
        }
        this.mWindow.closePanel(this.mFeatureId);
        return true;
    }

    public void sendAccessibilityEvent(int eventType) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if ((this.mFeatureId == 0 || this.mFeatureId == 6 || this.mFeatureId == 2 || this.mFeatureId == 5) && getChildCount() == 1) {
                getChildAt(0).sendAccessibilityEvent(eventType);
            } else {
                super.sendAccessibilityEvent(eventType);
            }
        }
    }

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        Window.Callback cb = this.mWindow.getCallback();
        if (cb == null || this.mWindow.isDestroyed() || !cb.dispatchPopulateAccessibilityEvent(event)) {
            return super.dispatchPopulateAccessibilityEventInternal(event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            Rect drawingBounds = this.mDrawingBounds;
            getDrawingRect(drawingBounds);
            Drawable fg = getForeground();
            if (fg != null) {
                Rect frameOffsets = this.mFrameOffsets;
                drawingBounds.left += frameOffsets.left;
                drawingBounds.top += frameOffsets.top;
                drawingBounds.right -= frameOffsets.right;
                drawingBounds.bottom -= frameOffsets.bottom;
                fg.setBounds(drawingBounds);
                Rect framePadding = this.mFramePadding;
                drawingBounds.left += framePadding.left - frameOffsets.left;
                drawingBounds.top += framePadding.top - frameOffsets.top;
                drawingBounds.right -= framePadding.right - frameOffsets.right;
                drawingBounds.bottom -= framePadding.bottom - frameOffsets.bottom;
            }
            Drawable bg = super.getBackground();
            if (bg != null) {
                bg.setBounds(drawingBounds);
            }
        }
        return changed;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0149  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0156  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r17, int r18) {
        /*
            r16 = this;
            r0 = r16
            android.content.Context r1 = r16.getContext()
            android.content.res.Resources r1 = r1.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            android.content.res.Resources r2 = r16.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
            int r2 = r2.orientation
            r3 = 1
            r4 = 0
            if (r2 != r3) goto L_0x001f
            r2 = r3
            goto L_0x0020
        L_0x001f:
            r2 = r4
        L_0x0020:
            int r5 = android.view.View.MeasureSpec.getMode(r17)
            int r6 = android.view.View.MeasureSpec.getMode(r18)
            r7 = 0
            r0.mApplyFloatingHorizontalInsets = r4
            r8 = 6
            r9 = 5
            r10 = 1073741824(0x40000000, float:2.0)
            r11 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r5 != r11) goto L_0x0083
            if (r2 == 0) goto L_0x003a
            com.android.internal.policy.PhoneWindow r12 = r0.mWindow
            android.util.TypedValue r12 = r12.mFixedWidthMinor
            goto L_0x003e
        L_0x003a:
            com.android.internal.policy.PhoneWindow r12 = r0.mWindow
            android.util.TypedValue r12 = r12.mFixedWidthMajor
        L_0x003e:
            if (r12 == 0) goto L_0x0083
            int r13 = r12.type
            if (r13 == 0) goto L_0x0083
            int r13 = r12.type
            if (r13 != r9) goto L_0x004e
            float r13 = r12.getDimension(r1)
            int r13 = (int) r13
            goto L_0x005f
        L_0x004e:
            int r13 = r12.type
            if (r13 != r8) goto L_0x005e
            int r13 = r1.widthPixels
            float r13 = (float) r13
            int r14 = r1.widthPixels
            float r14 = (float) r14
            float r13 = r12.getFraction(r13, r14)
            int r13 = (int) r13
            goto L_0x005f
        L_0x005e:
            r13 = r4
        L_0x005f:
            int r14 = android.view.View.MeasureSpec.getSize(r17)
            if (r13 <= 0) goto L_0x0070
            int r15 = java.lang.Math.min(r13, r14)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r10)
            r7 = 1
            goto L_0x0085
        L_0x0070:
            android.graphics.Rect r15 = r0.mFloatingInsets
            int r15 = r15.left
            int r15 = r14 - r15
            android.graphics.Rect r10 = r0.mFloatingInsets
            int r10 = r10.right
            int r15 = r15 - r10
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r11)
            r0.mApplyFloatingHorizontalInsets = r3
            r15 = r10
            goto L_0x0085
        L_0x0083:
            r15 = r17
        L_0x0085:
            r0.mApplyFloatingVerticalInsets = r4
            if (r6 != r11) goto L_0x00e6
            if (r2 == 0) goto L_0x0090
            com.android.internal.policy.PhoneWindow r10 = r0.mWindow
            android.util.TypedValue r10 = r10.mFixedHeightMajor
            goto L_0x0094
        L_0x0090:
            com.android.internal.policy.PhoneWindow r10 = r0.mWindow
            android.util.TypedValue r10 = r10.mFixedHeightMinor
        L_0x0094:
            if (r10 == 0) goto L_0x00e6
            int r12 = r10.type
            if (r12 == 0) goto L_0x00e6
            int r12 = r10.type
            if (r12 != r9) goto L_0x00a4
            float r12 = r10.getDimension(r1)
            int r12 = (int) r12
            goto L_0x00b5
        L_0x00a4:
            int r12 = r10.type
            if (r12 != r8) goto L_0x00b4
            int r12 = r1.heightPixels
            float r12 = (float) r12
            int r13 = r1.heightPixels
            float r13 = (float) r13
            float r12 = r10.getFraction(r12, r13)
            int r12 = (int) r12
            goto L_0x00b5
        L_0x00b4:
            r12 = r4
        L_0x00b5:
            int r13 = android.view.View.MeasureSpec.getSize(r18)
            if (r12 <= 0) goto L_0x00c7
            int r3 = java.lang.Math.min(r12, r13)
            r14 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r14)
            goto L_0x00e8
        L_0x00c7:
            com.android.internal.policy.PhoneWindow r14 = r0.mWindow
            android.view.WindowManager$LayoutParams r14 = r14.getAttributes()
            int r14 = r14.flags
            r14 = r14 & 256(0x100, float:3.59E-43)
            if (r14 != 0) goto L_0x00e6
            android.graphics.Rect r14 = r0.mFloatingInsets
            int r14 = r14.top
            int r14 = r13 - r14
            android.graphics.Rect r4 = r0.mFloatingInsets
            int r4 = r4.bottom
            int r14 = r14 - r4
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r11)
            r0.mApplyFloatingVerticalInsets = r3
            r3 = r4
            goto L_0x00e8
        L_0x00e6:
            r3 = r18
        L_0x00e8:
            android.graphics.Rect r4 = r0.mOutsets
            r0.getOutsets(r4)
            android.graphics.Rect r4 = r0.mOutsets
            int r4 = r4.top
            if (r4 > 0) goto L_0x00f9
            android.graphics.Rect r4 = r0.mOutsets
            int r4 = r4.bottom
            if (r4 <= 0) goto L_0x0111
        L_0x00f9:
            int r4 = android.view.View.MeasureSpec.getMode(r3)
            if (r4 == 0) goto L_0x0111
            int r10 = android.view.View.MeasureSpec.getSize(r3)
            android.graphics.Rect r12 = r0.mOutsets
            int r12 = r12.top
            int r12 = r12 + r10
            android.graphics.Rect r13 = r0.mOutsets
            int r13 = r13.bottom
            int r12 = r12 + r13
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r4)
        L_0x0111:
            android.graphics.Rect r4 = r0.mOutsets
            int r4 = r4.left
            if (r4 > 0) goto L_0x011d
            android.graphics.Rect r4 = r0.mOutsets
            int r4 = r4.right
            if (r4 <= 0) goto L_0x0135
        L_0x011d:
            int r4 = android.view.View.MeasureSpec.getMode(r15)
            if (r4 == 0) goto L_0x0135
            int r10 = android.view.View.MeasureSpec.getSize(r15)
            android.graphics.Rect r12 = r0.mOutsets
            int r12 = r12.left
            int r12 = r12 + r10
            android.graphics.Rect r13 = r0.mOutsets
            int r13 = r13.right
            int r12 = r12 + r13
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r4)
        L_0x0135:
            super.onMeasure(r15, r3)
            int r4 = r16.getMeasuredWidth()
            r10 = 0
            r12 = 1073741824(0x40000000, float:2.0)
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r12)
            if (r7 != 0) goto L_0x0178
            if (r5 != r11) goto L_0x0178
            if (r2 == 0) goto L_0x014e
            com.android.internal.policy.PhoneWindow r11 = r0.mWindow
            android.util.TypedValue r11 = r11.mMinWidthMinor
            goto L_0x0152
        L_0x014e:
            com.android.internal.policy.PhoneWindow r11 = r0.mWindow
            android.util.TypedValue r11 = r11.mMinWidthMajor
        L_0x0152:
            int r12 = r11.type
            if (r12 == 0) goto L_0x0178
            int r12 = r11.type
            if (r12 != r9) goto L_0x0160
            float r8 = r11.getDimension(r1)
            int r8 = (int) r8
            goto L_0x016f
        L_0x0160:
            int r9 = r11.type
            if (r9 != r8) goto L_0x016e
            float r8 = r0.mAvailableWidth
            float r9 = r0.mAvailableWidth
            float r8 = r11.getFraction(r8, r9)
            int r8 = (int) r8
            goto L_0x016f
        L_0x016e:
            r8 = 0
        L_0x016f:
            if (r4 >= r8) goto L_0x0178
            r9 = 1073741824(0x40000000, float:2.0)
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r9)
            r10 = 1
        L_0x0178:
            if (r10 == 0) goto L_0x017d
            super.onMeasure(r13, r3)
        L_0x017d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.DecorView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        getOutsets(this.mOutsets);
        if (this.mOutsets.left > 0) {
            offsetLeftAndRight(-this.mOutsets.left);
        }
        if (this.mOutsets.top > 0) {
            offsetTopAndBottom(-this.mOutsets.top);
        }
        if (this.mApplyFloatingVerticalInsets) {
            offsetTopAndBottom(this.mFloatingInsets.top);
        }
        if (this.mApplyFloatingHorizontalInsets) {
            offsetLeftAndRight(this.mFloatingInsets.left);
        }
        updateElevation();
        this.mAllowUpdateElevation = true;
        if (changed && this.mResizeMode == 1) {
            getViewRootImpl().requestInvalidateRootRenderNode();
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mMenuBackground != null) {
            this.mMenuBackground.draw(canvas);
        }
    }

    public boolean showContextMenuForChild(View originalView) {
        return showContextMenuForChildInternal(originalView, Float.NaN, Float.NaN);
    }

    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return showContextMenuForChildInternal(originalView, x, y);
    }

    private boolean showContextMenuForChildInternal(View originalView, float x, float y) {
        MenuHelper helper;
        if (this.mWindow.mContextMenuHelper != null) {
            this.mWindow.mContextMenuHelper.dismiss();
            this.mWindow.mContextMenuHelper = null;
        }
        PhoneWindow.PhoneWindowMenuCallback callback = this.mWindow.mContextMenuCallback;
        if (this.mWindow.mContextMenu == null) {
            this.mWindow.mContextMenu = new ContextMenuBuilder(getContext());
            this.mWindow.mContextMenu.setCallback(callback);
        } else {
            this.mWindow.mContextMenu.clearAll();
        }
        boolean isPopup = !Float.isNaN(x) && !Float.isNaN(y);
        if (isPopup) {
            helper = this.mWindow.mContextMenu.showPopup(getContext(), originalView, x, y);
        } else {
            helper = this.mWindow.mContextMenu.showDialog(originalView, originalView.getWindowToken());
        }
        if (helper != null) {
            callback.setShowDialogForSubmenu(!isPopup);
            helper.setPresenterCallback(callback);
        }
        this.mWindow.mContextMenuHelper = helper;
        if (helper != null) {
            return true;
        }
        return false;
    }

    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return startActionModeForChild(originalView, callback, 0);
    }

    public ActionMode startActionModeForChild(View child, ActionMode.Callback callback, int type) {
        return startActionMode(child, callback, type);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        return startActionMode(this, callback, type);
    }

    private ActionMode startActionMode(View originatingView, ActionMode.Callback callback, int type) {
        ActionMode.Callback2 wrappedCallback = new ActionModeCallback2Wrapper(callback);
        ActionMode mode = null;
        if (this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
            try {
                mode = this.mWindow.getCallback().onWindowStartingActionMode(wrappedCallback, type);
            } catch (AbstractMethodError e) {
                if (type == 0) {
                    try {
                        mode = this.mWindow.getCallback().onWindowStartingActionMode(wrappedCallback);
                    } catch (AbstractMethodError e2) {
                    }
                }
            }
        }
        if (mode == null) {
            mode = createActionMode(type, wrappedCallback, originatingView);
            if (mode == null || !wrappedCallback.onCreateActionMode(mode, mode.getMenu())) {
                mode = null;
            } else {
                setHandledActionMode(mode);
            }
        } else if (mode.getType() == 0) {
            cleanupPrimaryActionMode();
            this.mPrimaryActionMode = mode;
        } else if (mode.getType() == 1) {
            if (this.mFloatingActionMode != null) {
                this.mFloatingActionMode.finish();
            }
            this.mFloatingActionMode = mode;
        }
        if (!(mode == null || this.mWindow.getCallback() == null || this.mWindow.isDestroyed())) {
            try {
                this.mWindow.getCallback().onActionModeStarted(mode);
            } catch (AbstractMethodError e3) {
            }
        }
        return mode;
    }

    private void cleanupPrimaryActionMode() {
        if (this.mPrimaryActionMode != null) {
            this.mPrimaryActionMode.finish();
            this.mPrimaryActionMode = null;
        }
        if (this.mPrimaryActionModeView != null) {
            this.mPrimaryActionModeView.killMode();
        }
    }

    /* access modifiers changed from: private */
    public void cleanupFloatingActionModeViews() {
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        if (this.mFloatingActionModeOriginatingView != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                this.mFloatingActionModeOriginatingView.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void startChanging() {
        this.mChanging = true;
    }

    /* access modifiers changed from: package-private */
    public void finishChanging() {
        this.mChanging = false;
        drawableChanged();
    }

    public void setWindowBackground(Drawable drawable) {
        if (this.mOriginalBackgroundDrawable != drawable) {
            this.mOriginalBackgroundDrawable = drawable;
            updateBackgroundDrawable();
            boolean z = false;
            if (drawable != null) {
                if (this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper()) {
                    z = true;
                }
                this.mResizingBackgroundDrawable = enforceNonTranslucentBackground(drawable, z);
            } else {
                Drawable drawable2 = this.mWindow.mBackgroundDrawable;
                Drawable drawable3 = this.mWindow.mBackgroundFallbackDrawable;
                if (this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper()) {
                    z = true;
                }
                this.mResizingBackgroundDrawable = getResizingBackgroundDrawable(drawable2, drawable3, z);
            }
            if (this.mResizingBackgroundDrawable != null) {
                this.mResizingBackgroundDrawable.getPadding(this.mBackgroundPadding);
            } else {
                this.mBackgroundPadding.setEmpty();
            }
            drawableChanged();
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        if (this.mOriginalBackgroundDrawable != background) {
            this.mOriginalBackgroundDrawable = background;
            updateBackgroundDrawable();
            if (!View.sBrokenWindowBackground) {
                drawableChanged();
            }
        }
    }

    public void setWindowFrame(Drawable drawable) {
        if (getForeground() != drawable) {
            setForeground(drawable);
            if (drawable != null) {
                drawable.getPadding(this.mFramePadding);
            } else {
                this.mFramePadding.setEmpty();
            }
            drawableChanged();
        }
    }

    public void onWindowSystemUiVisibilityChanged(int visible) {
        updateColorViews((WindowInsets) null, true);
        updateDecorCaptionStatus(getResources().getConfiguration());
        if (this.mStatusGuard != null && this.mStatusGuard.getVisibility() == 0) {
            updateStatusGuardColor();
        }
    }

    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        this.mFloatingInsets.setEmpty();
        if ((attrs.flags & 256) == 0) {
            if (attrs.height == -2) {
                this.mFloatingInsets.top = insets.getSystemWindowInsetTop();
                this.mFloatingInsets.bottom = insets.getSystemWindowInsetBottom();
                insets = insets.inset(0, insets.getSystemWindowInsetTop(), 0, insets.getSystemWindowInsetBottom());
            }
            if (this.mWindow.getAttributes().width == -2) {
                this.mFloatingInsets.left = insets.getSystemWindowInsetTop();
                this.mFloatingInsets.right = insets.getSystemWindowInsetBottom();
                insets = insets.inset(insets.getSystemWindowInsetLeft(), 0, insets.getSystemWindowInsetRight(), 0);
            }
        }
        this.mFrameOffsets.set(insets.getSystemWindowInsetsAsRect());
        WindowInsets insets2 = updateStatusGuard(updateColorViews(insets, true));
        if (getForeground() != null) {
            drawableChanged();
        }
        return insets2;
    }

    public boolean isTransitionGroup() {
        return false;
    }

    public static int getColorViewTopInset(int stableTop, int systemTop) {
        return Math.min(stableTop, systemTop);
    }

    public static int getColorViewBottomInset(int stableBottom, int systemBottom) {
        return Math.min(stableBottom, systemBottom);
    }

    public static int getColorViewRightInset(int stableRight, int systemRight) {
        return Math.min(stableRight, systemRight);
    }

    public static int getColorViewLeftInset(int stableLeft, int systemLeft) {
        return Math.min(stableLeft, systemLeft);
    }

    public static boolean isNavBarToRightEdge(int bottomInset, int rightInset) {
        return bottomInset == 0 && rightInset > 0;
    }

    public static boolean isNavBarToLeftEdge(int bottomInset, int leftInset) {
        return bottomInset == 0 && leftInset > 0;
    }

    public static int getNavBarSize(int bottomInset, int rightInset, int leftInset) {
        if (isNavBarToRightEdge(bottomInset, rightInset)) {
            return rightInset;
        }
        return isNavBarToLeftEdge(bottomInset, leftInset) ? leftInset : bottomInset;
    }

    public static void getNavigationBarRect(int canvasWidth, int canvasHeight, Rect stableInsets, Rect contentInsets, Rect outRect, float scale) {
        int bottomInset = (int) (((float) getColorViewBottomInset(stableInsets.bottom, contentInsets.bottom)) * scale);
        int leftInset = (int) (((float) getColorViewLeftInset(stableInsets.left, contentInsets.left)) * scale);
        int rightInset = (int) (((float) getColorViewLeftInset(stableInsets.right, contentInsets.right)) * scale);
        int size = getNavBarSize(bottomInset, rightInset, leftInset);
        if (isNavBarToRightEdge(bottomInset, rightInset)) {
            outRect.set(canvasWidth - size, 0, canvasWidth, canvasHeight);
        } else if (isNavBarToLeftEdge(bottomInset, leftInset)) {
            outRect.set(0, 0, size, canvasHeight);
        } else {
            outRect.set(0, canvasHeight - size, canvasWidth, canvasHeight);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x0243  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x0255  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x0262  */
    /* JADX WARNING: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.WindowInsets updateColorViews(android.view.WindowInsets r29, boolean r30) {
        /*
            r28 = this;
            r11 = r28
            r12 = r29
            com.android.internal.policy.PhoneWindow r0 = r11.mWindow
            android.view.WindowManager$LayoutParams r13 = r0.getAttributes()
            int r0 = r13.systemUiVisibility
            int r1 = r28.getWindowSystemUiVisibility()
            r14 = r0 | r1
            com.android.internal.policy.PhoneWindow r0 = r11.mWindow
            android.view.WindowManager$LayoutParams r0 = r0.getAttributes()
            int r0 = r0.type
            r15 = 1
            r10 = 0
            r1 = 2011(0x7db, float:2.818E-42)
            if (r0 != r1) goto L_0x0022
            r0 = r15
            goto L_0x0023
        L_0x0022:
            r0 = r10
        L_0x0023:
            r16 = r0
            com.android.internal.policy.PhoneWindow r0 = r11.mWindow
            boolean r0 = r0.mIsFloating
            r17 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 == 0) goto L_0x002f
            if (r16 == 0) goto L_0x0184
        L_0x002f:
            boolean r0 = r28.isLaidOut()
            r0 = r0 ^ r15
            int r1 = r11.mLastWindowFlags
            int r2 = r13.flags
            r1 = r1 ^ r2
            r1 = r1 & r17
            if (r1 == 0) goto L_0x003f
            r1 = r15
            goto L_0x0040
        L_0x003f:
            r1 = r10
        L_0x0040:
            r0 = r0 | r1
            int r1 = r13.flags
            r11.mLastWindowFlags = r1
            if (r12 == 0) goto L_0x00d1
            int r1 = r29.getStableInsetTop()
            int r2 = r29.getSystemWindowInsetTop()
            int r1 = getColorViewTopInset(r1, r2)
            r11.mLastTopInset = r1
            int r1 = r29.getStableInsetBottom()
            int r2 = r29.getSystemWindowInsetBottom()
            int r1 = getColorViewBottomInset(r1, r2)
            r11.mLastBottomInset = r1
            int r1 = r29.getStableInsetRight()
            int r2 = r29.getSystemWindowInsetRight()
            int r1 = getColorViewRightInset(r1, r2)
            r11.mLastRightInset = r1
            int r1 = r29.getStableInsetLeft()
            int r2 = r29.getSystemWindowInsetLeft()
            int r1 = getColorViewRightInset(r1, r2)
            r11.mLastLeftInset = r1
            int r1 = r29.getStableInsetTop()
            if (r1 == 0) goto L_0x0087
            r1 = r15
            goto L_0x0088
        L_0x0087:
            r1 = r10
        L_0x0088:
            boolean r2 = r11.mLastHasTopStableInset
            if (r1 == r2) goto L_0x008e
            r2 = r15
            goto L_0x008f
        L_0x008e:
            r2 = r10
        L_0x008f:
            r0 = r0 | r2
            r11.mLastHasTopStableInset = r1
            int r2 = r29.getStableInsetBottom()
            if (r2 == 0) goto L_0x009a
            r2 = r15
            goto L_0x009b
        L_0x009a:
            r2 = r10
        L_0x009b:
            boolean r3 = r11.mLastHasBottomStableInset
            if (r2 == r3) goto L_0x00a1
            r3 = r15
            goto L_0x00a2
        L_0x00a1:
            r3 = r10
        L_0x00a2:
            r0 = r0 | r3
            r11.mLastHasBottomStableInset = r2
            int r3 = r29.getStableInsetRight()
            if (r3 == 0) goto L_0x00ad
            r3 = r15
            goto L_0x00ae
        L_0x00ad:
            r3 = r10
        L_0x00ae:
            boolean r4 = r11.mLastHasRightStableInset
            if (r3 == r4) goto L_0x00b4
            r4 = r15
            goto L_0x00b5
        L_0x00b4:
            r4 = r10
        L_0x00b5:
            r0 = r0 | r4
            r11.mLastHasRightStableInset = r3
            int r4 = r29.getStableInsetLeft()
            if (r4 == 0) goto L_0x00c0
            r4 = r15
            goto L_0x00c1
        L_0x00c0:
            r4 = r10
        L_0x00c1:
            boolean r5 = r11.mLastHasLeftStableInset
            if (r4 == r5) goto L_0x00c7
            r5 = r15
            goto L_0x00c8
        L_0x00c7:
            r5 = r10
        L_0x00c8:
            r0 = r0 | r5
            r11.mLastHasLeftStableInset = r4
            boolean r5 = r29.shouldAlwaysConsumeSystemBars()
            r11.mLastShouldAlwaysConsumeSystemBars = r5
        L_0x00d1:
            r18 = r0
            int r0 = r11.mLastBottomInset
            int r1 = r11.mLastRightInset
            boolean r19 = isNavBarToRightEdge(r0, r1)
            int r0 = r11.mLastBottomInset
            int r1 = r11.mLastLeftInset
            boolean r20 = isNavBarToLeftEdge(r0, r1)
            int r0 = r11.mLastBottomInset
            int r1 = r11.mLastRightInset
            int r2 = r11.mLastLeftInset
            int r21 = getNavBarSize(r0, r1, r2)
            com.android.internal.policy.DecorView$ColorViewState r1 = r11.mNavigationColorViewState
            int r3 = r28.calculateNavigationBarColor()
            com.android.internal.policy.PhoneWindow r0 = r11.mWindow
            int r4 = r0.mNavigationBarDividerColor
            if (r19 != 0) goto L_0x00fe
            if (r20 == 0) goto L_0x00fc
            goto L_0x00fe
        L_0x00fc:
            r6 = r10
            goto L_0x00ff
        L_0x00fe:
            r6 = r15
        L_0x00ff:
            r8 = 0
            if (r30 == 0) goto L_0x0106
            if (r18 != 0) goto L_0x0106
            r9 = r15
            goto L_0x0107
        L_0x0106:
            r9 = r10
        L_0x0107:
            boolean r7 = r11.mForceWindowDrawsBarBackgrounds
            r0 = r28
            r2 = r14
            r5 = r21
            r22 = r7
            r7 = r20
            r10 = r22
            r0.updateColorViewInt(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            boolean r10 = r11.mDrawLegacyNavigationBarBackground
            com.android.internal.policy.DecorView$ColorViewState r0 = r11.mNavigationColorViewState
            boolean r0 = r0.visible
            if (r0 == 0) goto L_0x012d
            com.android.internal.policy.PhoneWindow r0 = r11.mWindow
            android.view.WindowManager$LayoutParams r0 = r0.getAttributes()
            int r0 = r0.flags
            r0 = r0 & r17
            if (r0 != 0) goto L_0x012d
            r0 = r15
            goto L_0x012e
        L_0x012d:
            r0 = 0
        L_0x012e:
            r11.mDrawLegacyNavigationBarBackground = r0
            boolean r0 = r11.mDrawLegacyNavigationBarBackground
            if (r10 == r0) goto L_0x013d
            android.view.ViewRootImpl r0 = r28.getViewRootImpl()
            if (r0 == 0) goto L_0x013d
            r0.requestInvalidateRootRenderNode()
        L_0x013d:
            if (r19 == 0) goto L_0x0147
            com.android.internal.policy.DecorView$ColorViewState r0 = r11.mNavigationColorViewState
            boolean r0 = r0.present
            if (r0 == 0) goto L_0x0147
            r0 = r15
            goto L_0x0148
        L_0x0147:
            r0 = 0
        L_0x0148:
            r22 = r0
            if (r20 == 0) goto L_0x0154
            com.android.internal.policy.DecorView$ColorViewState r0 = r11.mNavigationColorViewState
            boolean r0 = r0.present
            if (r0 == 0) goto L_0x0154
            r0 = r15
            goto L_0x0155
        L_0x0154:
            r0 = 0
        L_0x0155:
            r23 = r0
            if (r22 == 0) goto L_0x015d
            int r0 = r11.mLastRightInset
        L_0x015b:
            r8 = r0
            goto L_0x0163
        L_0x015d:
            if (r23 == 0) goto L_0x0162
            int r0 = r11.mLastLeftInset
            goto L_0x015b
        L_0x0162:
            r8 = 0
        L_0x0163:
            com.android.internal.policy.DecorView$ColorViewState r1 = r11.mStatusColorViewState
            int r3 = r28.calculateStatusBarColor()
            r4 = 0
            int r5 = r11.mLastTopInset
            r6 = 0
            if (r30 == 0) goto L_0x0173
            if (r18 != 0) goto L_0x0173
            r9 = r15
            goto L_0x0174
        L_0x0173:
            r9 = 0
        L_0x0174:
            boolean r7 = r11.mForceWindowDrawsBarBackgrounds
            r0 = r28
            r2 = r14
            r24 = r7
            r7 = r23
            r25 = r10
            r10 = r24
            r0.updateColorViewInt(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
        L_0x0184:
            r0 = r14 & 2
            if (r0 == 0) goto L_0x018a
            r0 = r15
            goto L_0x018b
        L_0x018a:
            r0 = 0
        L_0x018b:
            boolean r1 = r11.mForceWindowDrawsBarBackgrounds
            if (r1 == 0) goto L_0x019b
            int r1 = r13.flags
            r1 = r1 & r17
            if (r1 != 0) goto L_0x019b
            r1 = r14 & 512(0x200, float:7.175E-43)
            if (r1 != 0) goto L_0x019b
            if (r0 == 0) goto L_0x01a1
        L_0x019b:
            boolean r1 = r11.mLastShouldAlwaysConsumeSystemBars
            if (r1 == 0) goto L_0x01a3
            if (r0 == 0) goto L_0x01a3
        L_0x01a1:
            r1 = r15
            goto L_0x01a4
        L_0x01a3:
            r1 = 0
        L_0x01a4:
            int r2 = r13.flags
            r2 = r2 & r17
            if (r2 == 0) goto L_0x01b0
            r2 = r14 & 512(0x200, float:7.175E-43)
            if (r2 != 0) goto L_0x01b0
            if (r0 == 0) goto L_0x01b2
        L_0x01b0:
            if (r1 == 0) goto L_0x01b4
        L_0x01b2:
            r2 = r15
            goto L_0x01b5
        L_0x01b4:
            r2 = 0
        L_0x01b5:
            r3 = r14 & 4
            if (r3 != 0) goto L_0x01c2
            int r3 = r13.flags
            r3 = r3 & 1024(0x400, float:1.435E-42)
            if (r3 == 0) goto L_0x01c0
            goto L_0x01c2
        L_0x01c0:
            r3 = 0
            goto L_0x01c3
        L_0x01c2:
            r3 = r15
        L_0x01c3:
            r4 = r14 & 1024(0x400, float:1.435E-42)
            if (r4 != 0) goto L_0x01dc
            int r4 = r13.flags
            r4 = r4 & 256(0x100, float:3.59E-43)
            if (r4 != 0) goto L_0x01dc
            int r4 = r13.flags
            r5 = 65536(0x10000, float:9.18355E-41)
            r4 = r4 & r5
            if (r4 != 0) goto L_0x01dc
            boolean r4 = r11.mForceWindowDrawsBarBackgrounds
            if (r4 == 0) goto L_0x01dc
            int r4 = r11.mLastTopInset
            if (r4 != 0) goto L_0x01e2
        L_0x01dc:
            boolean r4 = r11.mLastShouldAlwaysConsumeSystemBars
            if (r4 == 0) goto L_0x01e3
            if (r3 == 0) goto L_0x01e3
        L_0x01e2:
            goto L_0x01e4
        L_0x01e3:
            r15 = 0
        L_0x01e4:
            r4 = r15
            if (r4 == 0) goto L_0x01ea
            int r10 = r11.mLastTopInset
            goto L_0x01eb
        L_0x01ea:
            r10 = 0
        L_0x01eb:
            r5 = r10
            if (r2 == 0) goto L_0x01f1
            int r10 = r11.mLastRightInset
            goto L_0x01f2
        L_0x01f1:
            r10 = 0
        L_0x01f2:
            r6 = r10
            if (r2 == 0) goto L_0x01f8
            int r10 = r11.mLastBottomInset
            goto L_0x01f9
        L_0x01f8:
            r10 = 0
        L_0x01f9:
            r7 = r10
            if (r2 == 0) goto L_0x01ff
            int r10 = r11.mLastLeftInset
            goto L_0x0200
        L_0x01ff:
            r10 = 0
        L_0x0200:
            r8 = r10
            android.view.ViewGroup r9 = r11.mContentRoot
            if (r9 == 0) goto L_0x0240
            android.view.ViewGroup r9 = r11.mContentRoot
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            boolean r9 = r9 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r9 == 0) goto L_0x0240
            android.view.ViewGroup r9 = r11.mContentRoot
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r9 = (android.view.ViewGroup.MarginLayoutParams) r9
            int r10 = r9.topMargin
            if (r10 != r5) goto L_0x0227
            int r10 = r9.rightMargin
            if (r10 != r6) goto L_0x0227
            int r10 = r9.bottomMargin
            if (r10 != r7) goto L_0x0227
            int r10 = r9.leftMargin
            if (r10 == r8) goto L_0x0239
        L_0x0227:
            r9.topMargin = r5
            r9.rightMargin = r6
            r9.bottomMargin = r7
            r9.leftMargin = r8
            android.view.ViewGroup r10 = r11.mContentRoot
            r10.setLayoutParams(r9)
            if (r12 != 0) goto L_0x0239
            r28.requestApplyInsets()
        L_0x0239:
            if (r12 == 0) goto L_0x0240
            android.view.WindowInsets r9 = r12.inset(r8, r5, r6, r7)
            goto L_0x0241
        L_0x0240:
            r9 = r12
        L_0x0241:
            if (r1 == 0) goto L_0x0255
            int r10 = r11.mLastLeftInset
            int r12 = r11.mLastRightInset
            r26 = r0
            int r0 = r11.mLastBottomInset
            r27 = r1
            r1 = 0
            android.graphics.Insets r0 = android.graphics.Insets.of(r10, r1, r12, r0)
            r11.mBackgroundInsets = r0
            goto L_0x025d
        L_0x0255:
            r26 = r0
            r27 = r1
            android.graphics.Insets r0 = android.graphics.Insets.NONE
            r11.mBackgroundInsets = r0
        L_0x025d:
            r28.updateBackgroundDrawable()
            if (r9 == 0) goto L_0x0266
            android.view.WindowInsets r9 = r9.consumeStableInsets()
        L_0x0266:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.DecorView.updateColorViews(android.view.WindowInsets, boolean):android.view.WindowInsets");
    }

    private void updateBackgroundDrawable() {
        if (this.mBackgroundInsets == null) {
            this.mBackgroundInsets = Insets.NONE;
        }
        if (!this.mBackgroundInsets.equals(this.mLastBackgroundInsets) || this.mLastOriginalBackgroundDrawable != this.mOriginalBackgroundDrawable) {
            if (this.mOriginalBackgroundDrawable == null || this.mBackgroundInsets.equals(Insets.NONE)) {
                super.setBackgroundDrawable(this.mOriginalBackgroundDrawable);
            } else {
                super.setBackgroundDrawable(new InsetDrawable(this.mOriginalBackgroundDrawable, this.mBackgroundInsets.left, this.mBackgroundInsets.top, this.mBackgroundInsets.right, this.mBackgroundInsets.bottom) {
                    public boolean getPadding(Rect padding) {
                        return getDrawable().getPadding(padding);
                    }
                });
            }
            this.mLastBackgroundInsets = this.mBackgroundInsets;
            this.mLastOriginalBackgroundDrawable = this.mOriginalBackgroundDrawable;
        }
    }

    public Drawable getBackground() {
        return this.mOriginalBackgroundDrawable;
    }

    private int calculateStatusBarColor() {
        return calculateBarColor(this.mWindow.getAttributes().flags, 67108864, this.mSemiTransparentBarColor, this.mWindow.mStatusBarColor, getWindowSystemUiVisibility(), 8192, this.mWindow.mEnsureStatusBarContrastWhenTransparent);
    }

    private int calculateNavigationBarColor() {
        return calculateBarColor(this.mWindow.getAttributes().flags, 134217728, this.mSemiTransparentBarColor, this.mWindow.mNavigationBarColor, getWindowSystemUiVisibility(), 16, this.mWindow.mEnsureNavigationBarContrastWhenTransparent && getContext().getResources().getBoolean(R.bool.config_navBarNeedsScrim));
    }

    public static int calculateBarColor(int flags, int translucentFlag, int semiTransparentBarColor, int barColor, int sysuiVis, int lightSysuiFlag, boolean scrimTransparent) {
        if ((flags & translucentFlag) != 0) {
            return semiTransparentBarColor;
        }
        if ((Integer.MIN_VALUE & flags) == 0) {
            return -16777216;
        }
        if (!scrimTransparent || Color.alpha(barColor) != 0) {
            return barColor;
        }
        return (sysuiVis & lightSysuiFlag) != 0 ? SCRIM_LIGHT : semiTransparentBarColor;
    }

    private int getCurrentColor(ColorViewState state) {
        if (state.visible) {
            return state.color;
        }
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f3, code lost:
        if (r6.leftMargin != r14) goto L_0x0101;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateColorViewInt(com.android.internal.policy.DecorView.ColorViewState r26, int r27, int r28, int r29, int r30, boolean r31, boolean r32, int r33, boolean r34, boolean r35) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r28
            r3 = r29
            r4 = r31
            r5 = r32
            r6 = r33
            r7 = r35
            com.android.internal.policy.DecorView$ColorViewAttributes r8 = r1.attributes
            com.android.internal.policy.PhoneWindow r9 = r0.mWindow
            android.view.WindowManager$LayoutParams r9 = r9.getAttributes()
            int r9 = r9.flags
            r10 = r27
            boolean r8 = r8.isPresent(r10, r9, r7)
            r1.present = r8
            com.android.internal.policy.DecorView$ColorViewAttributes r8 = r1.attributes
            boolean r9 = r1.present
            com.android.internal.policy.PhoneWindow r11 = r0.mWindow
            android.view.WindowManager$LayoutParams r11 = r11.getAttributes()
            int r11 = r11.flags
            boolean r8 = r8.isVisible((boolean) r9, (int) r2, (int) r11, (boolean) r7)
            if (r8 == 0) goto L_0x003e
            boolean r12 = r25.isResizing()
            if (r12 != 0) goto L_0x003e
            if (r30 <= 0) goto L_0x003e
            r13 = 1
            goto L_0x003f
        L_0x003e:
            r13 = 0
        L_0x003f:
            r14 = 0
            android.view.View r15 = r1.view
            r16 = -1
            if (r4 == 0) goto L_0x0049
            r17 = r16
            goto L_0x004b
        L_0x0049:
            r17 = r30
        L_0x004b:
            r18 = r17
            if (r4 == 0) goto L_0x0052
            r16 = r30
        L_0x0052:
            r19 = r16
            if (r4 == 0) goto L_0x0062
            if (r5 == 0) goto L_0x005d
            com.android.internal.policy.DecorView$ColorViewAttributes r9 = r1.attributes
            int r9 = r9.seascapeGravity
            goto L_0x0066
        L_0x005d:
            com.android.internal.policy.DecorView$ColorViewAttributes r9 = r1.attributes
            int r9 = r9.horizontalGravity
            goto L_0x0066
        L_0x0062:
            com.android.internal.policy.DecorView$ColorViewAttributes r9 = r1.attributes
            int r9 = r9.verticalGravity
        L_0x0066:
            if (r15 != 0) goto L_0x00ab
            if (r13 == 0) goto L_0x00a5
            android.view.View r11 = new android.view.View
            android.content.Context r7 = r0.mContext
            r11.<init>(r7)
            r15 = r11
            r1.view = r11
            setColor(r15, r2, r3, r4, r5)
            com.android.internal.policy.DecorView$ColorViewAttributes r7 = r1.attributes
            java.lang.String r7 = r7.transitionName
            r15.setTransitionName(r7)
            com.android.internal.policy.DecorView$ColorViewAttributes r7 = r1.attributes
            int r7 = r7.id
            r15.setId(r7)
            r14 = 1
            r7 = 4
            r15.setVisibility(r7)
            r11 = 0
            r1.targetVisibility = r11
            android.widget.FrameLayout$LayoutParams r11 = new android.widget.FrameLayout$LayoutParams
            r7 = r18
            r10 = r19
            r11.<init>(r10, r7, r9)
            if (r5 == 0) goto L_0x009b
            r11.leftMargin = r6
            goto L_0x009d
        L_0x009b:
            r11.rightMargin = r6
        L_0x009d:
            r0.addView((android.view.View) r15, (android.view.ViewGroup.LayoutParams) r11)
            r25.updateColorViewTranslations()
            goto L_0x0115
        L_0x00a5:
            r7 = r18
            r10 = r19
            goto L_0x0115
        L_0x00ab:
            r7 = r18
            r10 = r19
            if (r13 == 0) goto L_0x00b3
            r11 = 0
            goto L_0x00b4
        L_0x00b3:
            r11 = 4
        L_0x00b4:
            int r6 = r1.targetVisibility
            if (r6 == r11) goto L_0x00bb
            r20 = 1
            goto L_0x00bd
        L_0x00bb:
            r20 = 0
        L_0x00bd:
            r14 = r20
            r1.targetVisibility = r11
            android.view.ViewGroup$LayoutParams r6 = r15.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r6 = (android.widget.FrameLayout.LayoutParams) r6
            if (r5 == 0) goto L_0x00cc
            r16 = 0
            goto L_0x00ce
        L_0x00cc:
            r16 = r33
        L_0x00ce:
            r21 = r16
            if (r5 == 0) goto L_0x00d5
            r16 = r33
            goto L_0x00d7
        L_0x00d5:
            r16 = 0
        L_0x00d7:
            r22 = r16
            r23 = r11
            int r11 = r6.height
            if (r11 != r7) goto L_0x00fb
            int r11 = r6.width
            if (r11 != r10) goto L_0x00fb
            int r11 = r6.gravity
            if (r11 != r9) goto L_0x00fb
            int r11 = r6.rightMargin
            r12 = r21
            if (r11 != r12) goto L_0x00f6
            int r11 = r6.leftMargin
            r24 = r14
            r14 = r22
            if (r11 == r14) goto L_0x010e
            goto L_0x0101
        L_0x00f6:
            r24 = r14
            r14 = r22
            goto L_0x0101
        L_0x00fb:
            r24 = r14
            r12 = r21
            r14 = r22
        L_0x0101:
            r6.height = r7
            r6.width = r10
            r6.gravity = r9
            r6.rightMargin = r12
            r6.leftMargin = r14
            r15.setLayoutParams(r6)
        L_0x010e:
            if (r13 == 0) goto L_0x0113
            setColor(r15, r2, r3, r4, r5)
        L_0x0113:
            r14 = r24
        L_0x0115:
            if (r14 == 0) goto L_0x0178
            android.view.ViewPropertyAnimator r6 = r15.animate()
            r6.cancel()
            if (r34 == 0) goto L_0x016b
            boolean r12 = r25.isResizing()
            if (r12 != 0) goto L_0x016b
            r12 = 0
            if (r13 == 0) goto L_0x014d
            int r16 = r15.getVisibility()
            if (r16 == 0) goto L_0x0136
            r6 = 0
            r15.setVisibility(r6)
            r15.setAlpha(r12)
        L_0x0136:
            android.view.ViewPropertyAnimator r6 = r15.animate()
            r12 = 1065353216(0x3f800000, float:1.0)
            android.view.ViewPropertyAnimator r6 = r6.alpha(r12)
            android.view.animation.Interpolator r12 = r0.mShowInterpolator
            android.view.ViewPropertyAnimator r6 = r6.setInterpolator(r12)
            int r12 = r0.mBarEnterExitDuration
            long r3 = (long) r12
            r6.setDuration(r3)
            goto L_0x0178
        L_0x014d:
            android.view.ViewPropertyAnimator r3 = r15.animate()
            android.view.ViewPropertyAnimator r3 = r3.alpha(r12)
            android.view.animation.Interpolator r4 = r0.mHideInterpolator
            android.view.ViewPropertyAnimator r3 = r3.setInterpolator(r4)
            int r4 = r0.mBarEnterExitDuration
            long r4 = (long) r4
            android.view.ViewPropertyAnimator r3 = r3.setDuration(r4)
            com.android.internal.policy.DecorView$3 r4 = new com.android.internal.policy.DecorView$3
            r4.<init>(r1)
            r3.withEndAction(r4)
            goto L_0x0178
        L_0x016b:
            r6 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            r15.setAlpha(r3)
            if (r13 == 0) goto L_0x0174
            goto L_0x0175
        L_0x0174:
            r6 = 4
        L_0x0175:
            r15.setVisibility(r6)
        L_0x0178:
            r1.visible = r8
            r1.color = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.DecorView.updateColorViewInt(com.android.internal.policy.DecorView$ColorViewState, int, int, int, int, boolean, boolean, int, boolean, boolean):void");
    }

    private static void setColor(View v, int color, int dividerColor, boolean verticalBar, boolean seascape) {
        if (dividerColor != 0) {
            Pair<Boolean, Boolean> dir = (Pair) v.getTag();
            if (dir != null && ((Boolean) dir.first).booleanValue() == verticalBar && ((Boolean) dir.second).booleanValue() == seascape) {
                LayerDrawable d = (LayerDrawable) v.getBackground();
                ((ColorDrawable) ((InsetDrawable) d.getDrawable(1)).getDrawable()).setColor(color);
                ((ColorDrawable) d.getDrawable(0)).setColor(dividerColor);
                return;
            }
            int size = Math.round(TypedValue.applyDimension(1, 1.0f, v.getContext().getResources().getDisplayMetrics()));
            v.setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(dividerColor), new InsetDrawable((Drawable) new ColorDrawable(color), (!verticalBar || seascape) ? 0 : size, !verticalBar ? size : 0, (!verticalBar || !seascape) ? 0 : size, 0)}));
            v.setTag(new Pair(Boolean.valueOf(verticalBar), Boolean.valueOf(seascape)));
            return;
        }
        v.setTag((Object) null);
        v.setBackgroundColor(color);
    }

    private void updateColorViewTranslations() {
        int rootScrollY = this.mRootScrollY;
        float f = 0.0f;
        if (this.mStatusColorViewState.view != null) {
            this.mStatusColorViewState.view.setTranslationY(rootScrollY > 0 ? (float) rootScrollY : 0.0f);
        }
        if (this.mNavigationColorViewState.view != null) {
            View view = this.mNavigationColorViewState.view;
            if (rootScrollY < 0) {
                f = (float) rootScrollY;
            }
            view.setTranslationY(f);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x010c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.WindowInsets updateStatusGuard(android.view.WindowInsets r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 0
            com.android.internal.widget.ActionBarContextView r3 = r0.mPrimaryActionModeView
            r4 = 8
            if (r3 == 0) goto L_0x0112
            com.android.internal.widget.ActionBarContextView r3 = r0.mPrimaryActionModeView
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            boolean r3 = r3 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r3 == 0) goto L_0x0112
            com.android.internal.widget.ActionBarContextView r3 = r0.mPrimaryActionModeView
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r3 = (android.view.ViewGroup.MarginLayoutParams) r3
            r6 = 0
            com.android.internal.widget.ActionBarContextView r7 = r0.mPrimaryActionModeView
            boolean r7 = r7.isShown()
            if (r7 == 0) goto L_0x00f5
            android.graphics.Rect r7 = r0.mTempRect
            if (r7 != 0) goto L_0x0031
            android.graphics.Rect r7 = new android.graphics.Rect
            r7.<init>()
            r0.mTempRect = r7
        L_0x0031:
            android.graphics.Rect r7 = r0.mTempRect
            com.android.internal.policy.PhoneWindow r8 = r0.mWindow
            android.view.ViewGroup r8 = r8.mContentParent
            android.view.WindowInsets r8 = r8.computeSystemWindowInsets(r1, r7)
            int r9 = r8.getSystemWindowInsetTop()
            int r10 = r8.getSystemWindowInsetLeft()
            int r11 = r8.getSystemWindowInsetRight()
            android.view.WindowInsets r12 = r17.getRootWindowInsets()
            int r13 = r12.getSystemWindowInsetLeft()
            int r14 = r12.getSystemWindowInsetRight()
            int r15 = r3.topMargin
            if (r15 != r9) goto L_0x005f
            int r15 = r3.leftMargin
            if (r15 != r10) goto L_0x005f
            int r15 = r3.rightMargin
            if (r15 == r11) goto L_0x0066
        L_0x005f:
            r6 = 1
            r3.topMargin = r9
            r3.leftMargin = r10
            r3.rightMargin = r11
        L_0x0066:
            if (r9 <= 0) goto L_0x0099
            android.view.View r15 = r0.mStatusGuard
            if (r15 != 0) goto L_0x0099
            android.view.View r15 = new android.view.View
            android.content.Context r5 = r0.mContext
            r15.<init>(r5)
            r0.mStatusGuard = r15
            android.view.View r5 = r0.mStatusGuard
            r5.setVisibility(r4)
            android.widget.FrameLayout$LayoutParams r5 = new android.widget.FrameLayout$LayoutParams
            r15 = -1
            int r4 = r3.topMargin
            r16 = r2
            r2 = 51
            r5.<init>(r15, r4, r2)
            r2 = r5
            r2.leftMargin = r13
            r2.rightMargin = r14
            android.view.View r4 = r0.mStatusGuard
            com.android.internal.policy.DecorView$ColorViewState r5 = r0.mStatusColorViewState
            android.view.View r5 = r5.view
            int r5 = r0.indexOfChild(r5)
            r0.addView((android.view.View) r4, (int) r5, (android.view.ViewGroup.LayoutParams) r2)
            goto L_0x00c2
        L_0x0099:
            r16 = r2
            android.view.View r2 = r0.mStatusGuard
            if (r2 == 0) goto L_0x00c2
            android.view.View r2 = r0.mStatusGuard
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r2 = (android.widget.FrameLayout.LayoutParams) r2
            int r4 = r2.height
            int r5 = r3.topMargin
            if (r4 != r5) goto L_0x00b5
            int r4 = r2.leftMargin
            if (r4 != r13) goto L_0x00b5
            int r4 = r2.rightMargin
            if (r4 == r14) goto L_0x00c2
        L_0x00b5:
            int r4 = r3.topMargin
            r2.height = r4
            r2.leftMargin = r13
            r2.rightMargin = r14
            android.view.View r4 = r0.mStatusGuard
            r4.setLayoutParams(r2)
        L_0x00c2:
            android.view.View r2 = r0.mStatusGuard
            r5 = 1
            if (r2 == 0) goto L_0x00c9
            r2 = r5
            goto L_0x00ca
        L_0x00c9:
            r2 = 0
        L_0x00ca:
            if (r2 == 0) goto L_0x00d7
            android.view.View r4 = r0.mStatusGuard
            int r4 = r4.getVisibility()
            if (r4 == 0) goto L_0x00d7
            r17.updateStatusGuardColor()
        L_0x00d7:
            com.android.internal.policy.PhoneWindow r4 = r0.mWindow
            int r4 = r4.getLocalFeaturesPrivate()
            r4 = r4 & 1024(0x400, float:1.435E-42)
            if (r4 != 0) goto L_0x00e2
            goto L_0x00e3
        L_0x00e2:
            r5 = 0
        L_0x00e3:
            r4 = r5
            if (r4 == 0) goto L_0x00f1
            if (r2 == 0) goto L_0x00f1
            int r5 = r18.getSystemWindowInsetTop()
            r15 = 0
            android.view.WindowInsets r1 = r1.inset(r15, r5, r15, r15)
        L_0x00f1:
            r16 = r2
            goto L_0x0104
        L_0x00f5:
            r16 = r2
            int r2 = r3.topMargin
            if (r2 != 0) goto L_0x0106
            int r2 = r3.leftMargin
            if (r2 != 0) goto L_0x0106
            int r2 = r3.rightMargin
            if (r2 == 0) goto L_0x0104
            goto L_0x0106
        L_0x0104:
            r2 = 0
            goto L_0x010a
        L_0x0106:
            r6 = 1
            r2 = 0
            r3.topMargin = r2
        L_0x010a:
            if (r6 == 0) goto L_0x0115
            com.android.internal.widget.ActionBarContextView r4 = r0.mPrimaryActionModeView
            r4.setLayoutParams(r3)
            goto L_0x0115
        L_0x0112:
            r16 = r2
            r2 = 0
        L_0x0115:
            android.view.View r3 = r0.mStatusGuard
            if (r3 == 0) goto L_0x0123
            android.view.View r3 = r0.mStatusGuard
            if (r16 == 0) goto L_0x011e
            goto L_0x0120
        L_0x011e:
            r2 = 8
        L_0x0120:
            r3.setVisibility(r2)
        L_0x0123:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.DecorView.updateStatusGuard(android.view.WindowInsets):android.view.WindowInsets");
    }

    private void updateStatusGuardColor() {
        int i;
        boolean lightStatusBar = (getWindowSystemUiVisibility() & 8192) != 0;
        View view = this.mStatusGuard;
        if (lightStatusBar) {
            i = this.mContext.getColor(R.color.decor_view_status_guard_light);
        } else {
            i = this.mContext.getColor(R.color.decor_view_status_guard);
        }
        view.setBackgroundColor(i);
    }

    public void updatePictureInPictureOutlineProvider(boolean isInPictureInPictureMode) {
        if (this.mIsInPictureInPictureMode != isInPictureInPictureMode) {
            if (isInPictureInPictureMode) {
                Window.WindowControllerCallback callback = this.mWindow.getWindowControllerCallback();
                if (callback != null && callback.isTaskRoot()) {
                    super.setOutlineProvider(PIP_OUTLINE_PROVIDER);
                }
            } else if (getOutlineProvider() != this.mLastOutlineProvider) {
                setOutlineProvider(this.mLastOutlineProvider);
            }
            this.mIsInPictureInPictureMode = isInPictureInPictureMode;
        }
    }

    public void setOutlineProvider(ViewOutlineProvider provider) {
        super.setOutlineProvider(provider);
        this.mLastOutlineProvider = provider;
    }

    private void drawableChanged() {
        if (!this.mChanging) {
            Rect framePadding = this.mFramePadding != null ? this.mFramePadding : new Rect();
            Rect backgroundPadding = this.mBackgroundPadding != null ? this.mBackgroundPadding : new Rect();
            setPadding(framePadding.left + backgroundPadding.left, framePadding.top + backgroundPadding.top, framePadding.right + backgroundPadding.right, framePadding.bottom + backgroundPadding.bottom);
            requestLayout();
            invalidate();
            int opacity = -1;
            if (getResources().getConfiguration().windowConfiguration.hasWindowShadow()) {
                opacity = -3;
            } else {
                Drawable bg = getBackground();
                Drawable fg = getForeground();
                if (bg != null) {
                    if (fg == null) {
                        opacity = bg.getOpacity();
                    } else if (framePadding.left > 0 || framePadding.top > 0 || framePadding.right > 0 || framePadding.bottom > 0) {
                        opacity = -3;
                    } else {
                        int fop = fg.getOpacity();
                        int bop = bg.getOpacity();
                        if (fop == -1 || bop == -1) {
                            opacity = -1;
                        } else if (fop == 0) {
                            opacity = bop;
                        } else if (bop == 0) {
                            opacity = fop;
                        } else {
                            opacity = Drawable.resolveOpacity(fop, bop);
                        }
                    }
                }
            }
            this.mDefaultOpacity = opacity;
            if (this.mFeatureId < 0) {
                this.mWindow.setDefaultWindowFormat(opacity);
            }
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (this.mWindow.hasFeature(0) && !hasWindowFocus && this.mWindow.mPanelChordingKey != 0) {
            this.mWindow.closePanel(0);
        }
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            cb.onWindowFocusChanged(hasWindowFocus);
        }
        if (this.mPrimaryActionMode != null) {
            this.mPrimaryActionMode.onWindowFocusChanged(hasWindowFocus);
        }
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.onWindowFocusChanged(hasWindowFocus);
        }
        updateElevation();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            cb.onAttachedToWindow();
        }
        if (this.mFeatureId == -1) {
            this.mWindow.openPanelsAfterRestore();
        }
        if (!this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().addWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = true;
        } else if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.onConfigurationChange();
        }
        this.mWindow.onViewRootImplSet(getViewRootImpl());
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && this.mFeatureId < 0) {
            cb.onDetachedFromWindow();
        }
        if (this.mWindow.mDecorContentParent != null) {
            this.mWindow.mDecorContentParent.dismissPopups();
        }
        if (this.mPrimaryActionModePopup != null) {
            removeCallbacks(this.mShowPrimaryActionModePopup);
            if (this.mPrimaryActionModePopup.isShowing()) {
                this.mPrimaryActionModePopup.dismiss();
            }
            this.mPrimaryActionModePopup = null;
        }
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        if (!(st == null || st.menu == null || this.mFeatureId >= 0)) {
            st.menu.close();
        }
        releaseThreadedRenderer();
        if (this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().removeWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = false;
        }
    }

    public void onCloseSystemDialogs(String reason) {
        if (this.mFeatureId >= 0) {
            this.mWindow.closeAllPanels();
        }
    }

    public SurfaceHolder.Callback2 willYouTakeTheSurface() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeSurfaceCallback;
        }
        return null;
    }

    public InputQueue.Callback willYouTakeTheInputQueue() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeInputQueueCallback;
        }
        return null;
    }

    public void setSurfaceType(int type) {
        this.mWindow.setType(type);
    }

    public void setSurfaceFormat(int format) {
        this.mWindow.setFormat(format);
    }

    public void setSurfaceKeepScreenOn(boolean keepOn) {
        if (keepOn) {
            this.mWindow.addFlags(128);
        } else {
            this.mWindow.clearFlags(128);
        }
    }

    public void onRootViewScrollYChanged(int rootScrollY) {
        this.mRootScrollY = rootScrollY;
        if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.onRootViewScrollYChanged(rootScrollY);
        }
        updateColorViewTranslations();
    }

    private ActionMode createActionMode(int type, ActionMode.Callback2 callback, View originatingView) {
        if (type != 1) {
            return createStandaloneActionMode(callback);
        }
        return createFloatingActionMode(originatingView, callback);
    }

    private void setHandledActionMode(ActionMode mode) {
        if (mode.getType() == 0) {
            setHandledPrimaryActionMode(mode);
        } else if (mode.getType() == 1) {
            setHandledFloatingActionMode(mode);
        }
    }

    private ActionMode createStandaloneActionMode(ActionMode.Callback callback) {
        Context actionBarContext;
        endOnGoingFadeAnimation();
        cleanupPrimaryActionMode();
        boolean z = false;
        if (this.mPrimaryActionModeView == null || !this.mPrimaryActionModeView.isAttachedToWindow()) {
            if (this.mWindow.isFloating()) {
                TypedValue outValue = new TypedValue();
                Resources.Theme baseTheme = this.mContext.getTheme();
                baseTheme.resolveAttribute(16843825, outValue, true);
                if (outValue.resourceId != 0) {
                    Resources.Theme actionBarTheme = this.mContext.getResources().newTheme();
                    actionBarTheme.setTo(baseTheme);
                    actionBarTheme.applyStyle(outValue.resourceId, true);
                    actionBarContext = new ContextThemeWrapper(this.mContext, 0);
                    actionBarContext.getTheme().setTo(actionBarTheme);
                } else {
                    actionBarContext = this.mContext;
                }
                Context actionBarContext2 = actionBarContext;
                this.mPrimaryActionModeView = new ActionBarContextView(actionBarContext2);
                this.mPrimaryActionModePopup = new PopupWindow(actionBarContext2, (AttributeSet) null, (int) R.attr.actionModePopupWindowStyle);
                this.mPrimaryActionModePopup.setWindowLayoutType(2);
                this.mPrimaryActionModePopup.setContentView(this.mPrimaryActionModeView);
                this.mPrimaryActionModePopup.setWidth(-1);
                actionBarContext2.getTheme().resolveAttribute(16843499, outValue, true);
                this.mPrimaryActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(outValue.data, actionBarContext2.getResources().getDisplayMetrics()));
                this.mPrimaryActionModePopup.setHeight(-2);
                this.mShowPrimaryActionModePopup = new Runnable() {
                    public void run() {
                        DecorView.this.mPrimaryActionModePopup.showAtLocation(DecorView.this.mPrimaryActionModeView.getApplicationWindowToken(), 55, 0, 0);
                        DecorView.this.endOnGoingFadeAnimation();
                        if (DecorView.this.shouldAnimatePrimaryActionModeView()) {
                            ObjectAnimator unused = DecorView.this.mFadeAnim = ObjectAnimator.ofFloat(DecorView.this.mPrimaryActionModeView, View.ALPHA, 0.0f, 1.0f);
                            DecorView.this.mFadeAnim.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationStart(Animator animation) {
                                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                                }

                                public void onAnimationEnd(Animator animation) {
                                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                                    ObjectAnimator unused = DecorView.this.mFadeAnim = null;
                                }
                            });
                            DecorView.this.mFadeAnim.start();
                            return;
                        }
                        DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                        DecorView.this.mPrimaryActionModeView.setVisibility(0);
                    }
                };
            } else {
                ViewStub stub = (ViewStub) findViewById(R.id.action_mode_bar_stub);
                if (stub != null) {
                    this.mPrimaryActionModeView = (ActionBarContextView) stub.inflate();
                    this.mPrimaryActionModePopup = null;
                }
            }
        }
        if (this.mPrimaryActionModeView == null) {
            return null;
        }
        this.mPrimaryActionModeView.killMode();
        Context context = this.mPrimaryActionModeView.getContext();
        ActionBarContextView actionBarContextView = this.mPrimaryActionModeView;
        if (this.mPrimaryActionModePopup == null) {
            z = true;
        }
        return new StandaloneActionMode(context, actionBarContextView, callback, z);
    }

    /* access modifiers changed from: private */
    public void endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.end();
        }
    }

    private void setHandledPrimaryActionMode(ActionMode mode) {
        endOnGoingFadeAnimation();
        this.mPrimaryActionMode = mode;
        this.mPrimaryActionMode.invalidate();
        this.mPrimaryActionModeView.initForMode(this.mPrimaryActionMode);
        if (this.mPrimaryActionModePopup != null) {
            post(this.mShowPrimaryActionModePopup);
        } else if (shouldAnimatePrimaryActionModeView()) {
            this.mFadeAnim = ObjectAnimator.ofFloat(this.mPrimaryActionModeView, View.ALPHA, 0.0f, 1.0f);
            this.mFadeAnim.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animation) {
                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                }

                public void onAnimationEnd(Animator animation) {
                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                    ObjectAnimator unused = DecorView.this.mFadeAnim = null;
                }
            });
            this.mFadeAnim.start();
        } else {
            this.mPrimaryActionModeView.setAlpha(1.0f);
            this.mPrimaryActionModeView.setVisibility(0);
        }
        this.mPrimaryActionModeView.sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldAnimatePrimaryActionModeView() {
        return isLaidOut();
    }

    private ActionMode createFloatingActionMode(View originatingView, ActionMode.Callback2 callback) {
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mWindow);
        final FloatingActionMode mode = new FloatingActionMode(this.mContext, callback, originatingView, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = originatingView;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                mode.updateViewLocationInWindow();
                return true;
            }
        };
        return mode;
    }

    private void setHandledFloatingActionMode(ActionMode mode) {
        this.mFloatingActionMode = mode;
        this.mFloatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
    }

    /* access modifiers changed from: package-private */
    public void enableCaption(boolean attachedAndVisible) {
        if (this.mHasCaption != attachedAndVisible) {
            this.mHasCaption = attachedAndVisible;
            if (getForeground() != null) {
                drawableChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setWindow(PhoneWindow phoneWindow) {
        this.mWindow = phoneWindow;
        Context context = getContext();
        if (context instanceof DecorContext) {
            ((DecorContext) context).setPhoneWindow(this.mWindow);
        }
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateDecorCaptionStatus(newConfig);
        updateAvailableWidth();
        initializeElevation();
    }

    public void onMovedToDisplay(int displayId, Configuration config) {
        super.onMovedToDisplay(displayId, config);
        getContext().updateDisplay(displayId);
    }

    private boolean isFillingScreen(Configuration config) {
        if (!(config.windowConfiguration.getWindowingMode() == 1) || ((getWindowSystemUiVisibility() | getSystemUiVisibility()) & 4) == 0) {
            return false;
        }
        return true;
    }

    private void updateDecorCaptionStatus(Configuration config) {
        boolean displayWindowDecor = config.windowConfiguration.hasWindowDecorCaption() && !isFillingScreen(config);
        if (this.mDecorCaptionView == null && displayWindowDecor) {
            this.mDecorCaptionView = createDecorCaptionView(this.mWindow.getLayoutInflater());
            if (this.mDecorCaptionView != null) {
                if (this.mDecorCaptionView.getParent() == null) {
                    addView((View) this.mDecorCaptionView, 0, new ViewGroup.LayoutParams(-1, -1));
                }
                removeView(this.mContentRoot);
                this.mDecorCaptionView.addView((View) this.mContentRoot, (ViewGroup.LayoutParams) new ViewGroup.MarginLayoutParams(-1, -1));
            }
        } else if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.onConfigurationChanged(displayWindowDecor);
            enableCaption(displayWindowDecor);
        }
    }

    /* access modifiers changed from: package-private */
    public void onResourcesLoaded(LayoutInflater inflater, int layoutResource) {
        if (this.mBackdropFrameRenderer != null) {
            loadBackgroundDrawablesIfNeeded();
            this.mBackdropFrameRenderer.onResourcesLoaded(this, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, getCurrentColor(this.mStatusColorViewState), getCurrentColor(this.mNavigationColorViewState));
        }
        this.mDecorCaptionView = createDecorCaptionView(inflater);
        View root = inflater.inflate(layoutResource, (ViewGroup) null);
        if (this.mDecorCaptionView != null) {
            if (this.mDecorCaptionView.getParent() == null) {
                addView((View) this.mDecorCaptionView, new ViewGroup.LayoutParams(-1, -1));
            }
            this.mDecorCaptionView.addView(root, (ViewGroup.LayoutParams) new ViewGroup.MarginLayoutParams(-1, -1));
        } else {
            addView(root, 0, new ViewGroup.LayoutParams(-1, -1));
        }
        this.mContentRoot = (ViewGroup) root;
        initializeElevation();
    }

    private void loadBackgroundDrawablesIfNeeded() {
        if (this.mResizingBackgroundDrawable == null) {
            this.mResizingBackgroundDrawable = getResizingBackgroundDrawable(this.mWindow.mBackgroundDrawable, this.mWindow.mBackgroundFallbackDrawable, this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
            if (this.mResizingBackgroundDrawable == null) {
                String str = this.mLogTag;
                Log.w(str, "Failed to find background drawable for PhoneWindow=" + this.mWindow);
            }
        }
        if (this.mCaptionBackgroundDrawable == null) {
            this.mCaptionBackgroundDrawable = getContext().getDrawable(R.drawable.decor_caption_title_focused);
        }
        if (this.mResizingBackgroundDrawable != null) {
            this.mLastBackgroundDrawableCb = this.mResizingBackgroundDrawable.getCallback();
            this.mResizingBackgroundDrawable.setCallback((Drawable.Callback) null);
        }
    }

    /* JADX WARNING: type inference failed for: r3v6, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.android.internal.widget.DecorCaptionView createDecorCaptionView(android.view.LayoutInflater r8) {
        /*
            r7 = this;
            r0 = 0
            int r1 = r7.getChildCount()
            r2 = 1
            int r1 = r1 - r2
        L_0x0007:
            if (r1 < 0) goto L_0x001c
            if (r0 != 0) goto L_0x001c
            android.view.View r3 = r7.getChildAt(r1)
            boolean r4 = r3 instanceof com.android.internal.widget.DecorCaptionView
            if (r4 == 0) goto L_0x0019
            r0 = r3
            com.android.internal.widget.DecorCaptionView r0 = (com.android.internal.widget.DecorCaptionView) r0
            r7.removeViewAt(r1)
        L_0x0019:
            int r1 = r1 + -1
            goto L_0x0007
        L_0x001c:
            com.android.internal.policy.PhoneWindow r1 = r7.mWindow
            android.view.WindowManager$LayoutParams r1 = r1.getAttributes()
            int r3 = r1.type
            r4 = 0
            if (r3 == r2) goto L_0x0034
            int r3 = r1.type
            r5 = 2
            if (r3 == r5) goto L_0x0034
            int r3 = r1.type
            r5 = 4
            if (r3 != r5) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            r3 = r4
            goto L_0x0035
        L_0x0034:
            r3 = r2
        L_0x0035:
            android.content.res.Resources r5 = r7.getResources()
            android.content.res.Configuration r5 = r5.getConfiguration()
            android.app.WindowConfiguration r5 = r5.windowConfiguration
            com.android.internal.policy.PhoneWindow r6 = r7.mWindow
            boolean r6 = r6.isFloating()
            if (r6 != 0) goto L_0x005b
            if (r3 == 0) goto L_0x005b
            boolean r6 = r5.hasWindowDecorCaption()
            if (r6 == 0) goto L_0x005b
            if (r0 != 0) goto L_0x0055
            com.android.internal.widget.DecorCaptionView r0 = r7.inflateDecorCaptionView(r8)
        L_0x0055:
            com.android.internal.policy.PhoneWindow r6 = r7.mWindow
            r0.setPhoneWindow(r6, r2)
            goto L_0x005c
        L_0x005b:
            r0 = 0
        L_0x005c:
            if (r0 == 0) goto L_0x005f
            goto L_0x0060
        L_0x005f:
            r2 = r4
        L_0x0060:
            r7.enableCaption(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.DecorView.createDecorCaptionView(android.view.LayoutInflater):com.android.internal.widget.DecorCaptionView");
    }

    private DecorCaptionView inflateDecorCaptionView(LayoutInflater inflater) {
        Context context = getContext();
        DecorCaptionView view = (DecorCaptionView) LayoutInflater.from(context).inflate((int) R.layout.decor_caption, (ViewGroup) null);
        setDecorCaptionShade(context, view);
        return view;
    }

    private void setDecorCaptionShade(Context context, DecorCaptionView view) {
        switch (this.mWindow.getDecorCaptionShade()) {
            case 1:
                setLightDecorCaptionShade(view);
                return;
            case 2:
                setDarkDecorCaptionShade(view);
                return;
            default:
                TypedValue value = new TypedValue();
                context.getTheme().resolveAttribute(16843827, value, true);
                if (((double) Color.luminance(value.data)) < 0.5d) {
                    setLightDecorCaptionShade(view);
                    return;
                } else {
                    setDarkDecorCaptionShade(view);
                    return;
                }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateDecorCaptionShade() {
        if (this.mDecorCaptionView != null) {
            setDecorCaptionShade(getContext(), this.mDecorCaptionView);
        }
    }

    private void setLightDecorCaptionShade(DecorCaptionView view) {
        view.findViewById(R.id.maximize_window).setBackgroundResource(R.drawable.decor_maximize_button_light);
        view.findViewById(R.id.close_window).setBackgroundResource(R.drawable.decor_close_button_light);
    }

    private void setDarkDecorCaptionShade(DecorCaptionView view) {
        view.findViewById(R.id.maximize_window).setBackgroundResource(R.drawable.decor_maximize_button_dark);
        view.findViewById(R.id.close_window).setBackgroundResource(R.drawable.decor_close_button_dark);
    }

    public static Drawable getResizingBackgroundDrawable(Drawable backgroundDrawable, Drawable fallbackDrawable, boolean windowTranslucent) {
        if (backgroundDrawable != null) {
            return enforceNonTranslucentBackground(backgroundDrawable, windowTranslucent);
        }
        if (fallbackDrawable != null) {
            return enforceNonTranslucentBackground(fallbackDrawable, windowTranslucent);
        }
        return new ColorDrawable(-16777216);
    }

    private static Drawable enforceNonTranslucentBackground(Drawable drawable, boolean windowTranslucent) {
        if (!windowTranslucent && (drawable instanceof ColorDrawable)) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            int color = colorDrawable.getColor();
            if (Color.alpha(color) != 255) {
                ColorDrawable copy = (ColorDrawable) colorDrawable.getConstantState().newDrawable().mutate();
                copy.setColor(Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)));
                return copy;
            }
        }
        return drawable;
    }

    /* access modifiers changed from: package-private */
    public void clearContentView() {
        if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.removeContentView();
            return;
        }
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View v = getChildAt(i);
            if (!(v == this.mStatusColorViewState.view || v == this.mNavigationColorViewState.view || v == this.mStatusGuard)) {
                removeViewAt(i);
            }
        }
    }

    public void onWindowSizeIsChanging(Rect newBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.setTargetRect(newBounds, fullscreen, systemInsets, stableInsets);
        }
    }

    public void onWindowDragResizeStart(Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets, int resizeMode) {
        if (this.mWindow.isDestroyed()) {
            releaseThreadedRenderer();
        } else if (this.mBackdropFrameRenderer == null) {
            ThreadedRenderer renderer = getThreadedRenderer();
            if (renderer != null) {
                loadBackgroundDrawablesIfNeeded();
                this.mBackdropFrameRenderer = new BackdropFrameRenderer(this, renderer, initialBounds, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, getCurrentColor(this.mStatusColorViewState), getCurrentColor(this.mNavigationColorViewState), fullscreen, systemInsets, stableInsets);
                updateElevation();
                updateColorViews((WindowInsets) null, false);
            }
            this.mResizeMode = resizeMode;
            getViewRootImpl().requestInvalidateRootRenderNode();
        }
    }

    public void onWindowDragResizeEnd() {
        releaseThreadedRenderer();
        updateColorViews((WindowInsets) null, false);
        this.mResizeMode = -1;
        getViewRootImpl().requestInvalidateRootRenderNode();
    }

    public boolean onContentDrawn(int offsetX, int offsetY, int sizeX, int sizeY) {
        if (this.mBackdropFrameRenderer == null) {
            return false;
        }
        return this.mBackdropFrameRenderer.onContentDrawn(offsetX, offsetY, sizeX, sizeY);
    }

    public void onRequestDraw(boolean reportNextDraw) {
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.onRequestDraw(reportNextDraw);
        } else if (reportNextDraw && isAttachedToWindow()) {
            getViewRootImpl().reportDrawFinish();
        }
    }

    public void onPostDraw(RecordingCanvas canvas) {
        drawResizingShadowIfNeeded(canvas);
        drawLegacyNavigationBarBackground(canvas);
    }

    private void initResizingPaints() {
        int startColor = this.mContext.getResources().getColor(R.color.resize_shadow_start_color, (Resources.Theme) null);
        int endColor = this.mContext.getResources().getColor(R.color.resize_shadow_end_color, (Resources.Theme) null);
        int middleColor = (startColor + endColor) / 2;
        this.mHorizontalResizeShadowPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) this.mResizeShadowSize, new int[]{startColor, middleColor, endColor}, new float[]{0.0f, 0.3f, 1.0f}, Shader.TileMode.CLAMP));
        this.mVerticalResizeShadowPaint.setShader(new LinearGradient(0.0f, 0.0f, (float) this.mResizeShadowSize, 0.0f, new int[]{startColor, middleColor, endColor}, new float[]{0.0f, 0.3f, 1.0f}, Shader.TileMode.CLAMP));
    }

    private void drawResizingShadowIfNeeded(RecordingCanvas canvas) {
        if (this.mResizeMode == 1 && !this.mWindow.mIsFloating && !this.mWindow.isTranslucent() && !this.mWindow.isShowingWallpaper()) {
            canvas.save();
            canvas.translate(0.0f, (float) (getHeight() - this.mFrameOffsets.bottom));
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) this.mResizeShadowSize, this.mHorizontalResizeShadowPaint);
            canvas.restore();
            canvas.save();
            canvas.translate((float) (getWidth() - this.mFrameOffsets.right), 0.0f);
            canvas.drawRect(0.0f, 0.0f, (float) this.mResizeShadowSize, (float) getHeight(), this.mVerticalResizeShadowPaint);
            canvas.restore();
        }
    }

    private void drawLegacyNavigationBarBackground(RecordingCanvas canvas) {
        View v;
        if (this.mDrawLegacyNavigationBarBackground && (v = this.mNavigationColorViewState.view) != null) {
            canvas.drawRect((float) v.getLeft(), (float) v.getTop(), (float) v.getRight(), (float) v.getBottom(), this.mLegacyNavigationBarBackgroundPaint);
        }
    }

    private void releaseThreadedRenderer() {
        if (!(this.mResizingBackgroundDrawable == null || this.mLastBackgroundDrawableCb == null)) {
            this.mResizingBackgroundDrawable.setCallback(this.mLastBackgroundDrawableCb);
            this.mLastBackgroundDrawableCb = null;
        }
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.releaseRenderer();
            this.mBackdropFrameRenderer = null;
            updateElevation();
        }
    }

    private boolean isResizing() {
        return this.mBackdropFrameRenderer != null;
    }

    private void initializeElevation() {
        this.mAllowUpdateElevation = false;
        updateElevation();
    }

    private void updateElevation() {
        float elevation = 0.0f;
        boolean wasAdjustedForStack = this.mElevationAdjustedForStack;
        int windowingMode = getResources().getConfiguration().windowConfiguration.getWindowingMode();
        float f = 5.0f;
        if (windowingMode == 5 && !isResizing()) {
            if (hasWindowFocus()) {
                f = 20.0f;
            }
            float elevation2 = f;
            if (!this.mAllowUpdateElevation) {
                elevation2 = 20.0f;
            }
            elevation = dipToPx(elevation2);
            this.mElevationAdjustedForStack = true;
        } else if (windowingMode == 2) {
            elevation = dipToPx(5.0f);
            this.mElevationAdjustedForStack = true;
        } else {
            this.mElevationAdjustedForStack = false;
        }
        if ((!wasAdjustedForStack && !this.mElevationAdjustedForStack) || getElevation() == elevation) {
            return;
        }
        if (!isResizing()) {
            this.mWindow.setElevation(elevation);
        } else {
            setElevation(elevation);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isShowingCaption() {
        return this.mDecorCaptionView != null && this.mDecorCaptionView.isCaptionShowing();
    }

    /* access modifiers changed from: package-private */
    public int getCaptionHeight() {
        if (isShowingCaption()) {
            return this.mDecorCaptionView.getCaptionHeight();
        }
        return 0;
    }

    private float dipToPx(float dip) {
        return TypedValue.applyDimension(1, dip, getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: package-private */
    public void setUserCaptionBackgroundDrawable(Drawable drawable) {
        this.mUserCaptionBackgroundDrawable = drawable;
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.setUserCaptionBackgroundDrawable(drawable);
        }
    }

    private static String getTitleSuffix(WindowManager.LayoutParams params) {
        if (params == null) {
            return "";
        }
        String[] split = params.getTitle().toString().split("\\.");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        return "";
    }

    /* access modifiers changed from: package-private */
    public void updateLogTag(WindowManager.LayoutParams params) {
        this.mLogTag = "DecorView[" + getTitleSuffix(params) + "]";
    }

    private void updateAvailableWidth() {
        Resources res = getResources();
        this.mAvailableWidth = TypedValue.applyDimension(1, (float) res.getConfiguration().screenWidthDp, res.getDisplayMetrics());
    }

    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int deviceId) {
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        Menu menu = st != null ? st.menu : null;
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onProvideKeyboardShortcuts(list, menu, deviceId);
        }
    }

    public void dispatchPointerCaptureChanged(boolean hasCapture) {
        super.dispatchPointerCaptureChanged(hasCapture);
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onPointerCaptureChanged(hasCapture);
        }
    }

    public int getAccessibilityViewId() {
        return 2147483646;
    }

    public String toString() {
        return "DecorView@" + Integer.toHexString(hashCode()) + "[" + getTitleSuffix(this.mWindow.getAttributes()) + "]";
    }

    private static class ColorViewState {
        final ColorViewAttributes attributes;
        int color;
        boolean present = false;
        int targetVisibility = 4;
        View view = null;
        boolean visible;

        ColorViewState(ColorViewAttributes attributes2) {
            this.attributes = attributes2;
        }
    }

    public static class ColorViewAttributes {
        final int hideWindowFlag;
        final int horizontalGravity;
        final int id;
        final int seascapeGravity;
        final int systemUiHideFlag;
        final String transitionName;
        final int translucentFlag;
        final int verticalGravity;

        private ColorViewAttributes(int systemUiHideFlag2, int translucentFlag2, int verticalGravity2, int horizontalGravity2, int seascapeGravity2, String transitionName2, int id2, int hideWindowFlag2) {
            this.id = id2;
            this.systemUiHideFlag = systemUiHideFlag2;
            this.translucentFlag = translucentFlag2;
            this.verticalGravity = verticalGravity2;
            this.horizontalGravity = horizontalGravity2;
            this.seascapeGravity = seascapeGravity2;
            this.transitionName = transitionName2;
            this.hideWindowFlag = hideWindowFlag2;
        }

        public boolean isPresent(int sysUiVis, int windowFlags, boolean force) {
            return (this.systemUiHideFlag & sysUiVis) == 0 && (this.hideWindowFlag & windowFlags) == 0 && ((Integer.MIN_VALUE & windowFlags) != 0 || force);
        }

        public boolean isVisible(boolean present, int color, int windowFlags, boolean force) {
            return present && (-16777216 & color) != 0 && ((this.translucentFlag & windowFlags) == 0 || force);
        }

        public boolean isVisible(int sysUiVis, int color, int windowFlags, boolean force) {
            return isVisible(isPresent(sysUiVis, windowFlags, force), color, windowFlags, force);
        }
    }

    private class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback wrapped) {
            this.mWrapped = wrapped;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onCreateActionMode(mode, menu);
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            DecorView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        public void onDestroyActionMode(ActionMode mode) {
            boolean isPrimary;
            this.mWrapped.onDestroyActionMode(mode);
            boolean isFloating = false;
            if (DecorView.this.mContext.getApplicationInfo().targetSdkVersion >= 23) {
                isPrimary = mode == DecorView.this.mPrimaryActionMode;
                if (mode == DecorView.this.mFloatingActionMode) {
                    isFloating = true;
                }
                if (!isPrimary && mode.getType() == 0) {
                    Log.e(DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_PRIMARY; " + mode + " was not the current primary action mode! Expected " + DecorView.this.mPrimaryActionMode);
                }
                if (!isFloating && mode.getType() == 1) {
                    Log.e(DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_FLOATING; " + mode + " was not the current floating action mode! Expected " + DecorView.this.mFloatingActionMode);
                }
            } else {
                isPrimary = mode.getType() == 0;
                if (mode.getType() == 1) {
                    isFloating = true;
                }
            }
            if (isPrimary) {
                if (DecorView.this.mPrimaryActionModePopup != null) {
                    DecorView.this.removeCallbacks(DecorView.this.mShowPrimaryActionModePopup);
                }
                if (DecorView.this.mPrimaryActionModeView != null) {
                    DecorView.this.endOnGoingFadeAnimation();
                    final ActionBarContextView lastActionModeView = DecorView.this.mPrimaryActionModeView;
                    ObjectAnimator unused = DecorView.this.mFadeAnim = ObjectAnimator.ofFloat(DecorView.this.mPrimaryActionModeView, View.ALPHA, 1.0f, 0.0f);
                    DecorView.this.mFadeAnim.addListener(new Animator.AnimatorListener() {
                        public void onAnimationStart(Animator animation) {
                        }

                        public void onAnimationEnd(Animator animation) {
                            if (lastActionModeView == DecorView.this.mPrimaryActionModeView) {
                                lastActionModeView.setVisibility(8);
                                if (DecorView.this.mPrimaryActionModePopup != null) {
                                    DecorView.this.mPrimaryActionModePopup.dismiss();
                                }
                                lastActionModeView.killMode();
                                ObjectAnimator unused = DecorView.this.mFadeAnim = null;
                                DecorView.this.requestApplyInsets();
                            }
                        }

                        public void onAnimationCancel(Animator animation) {
                        }

                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    DecorView.this.mFadeAnim.start();
                }
                DecorView.this.mPrimaryActionMode = null;
            } else if (isFloating) {
                DecorView.this.cleanupFloatingActionModeViews();
                ActionMode unused2 = DecorView.this.mFloatingActionMode = null;
            }
            if (DecorView.this.mWindow.getCallback() != null && !DecorView.this.mWindow.isDestroyed()) {
                try {
                    DecorView.this.mWindow.getCallback().onActionModeFinished(mode);
                } catch (AbstractMethodError e) {
                }
            }
            DecorView.this.requestFitSystemWindows();
        }

        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (this.mWrapped instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) this.mWrapped).onGetContentRect(mode, view, outRect);
            } else {
                super.onGetContentRect(mode, view, outRect);
            }
        }
    }
}
