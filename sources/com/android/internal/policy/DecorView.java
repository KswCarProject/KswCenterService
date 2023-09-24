package com.android.internal.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
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
import android.view.ViewRootImpl;
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
import com.android.internal.C3132R;
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

/* loaded from: classes4.dex */
public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks {
    private static final boolean DEBUG_MEASURE = false;
    private static final int DECOR_SHADOW_FOCUSED_HEIGHT_IN_DIP = 20;
    private static final int DECOR_SHADOW_UNFOCUSED_HEIGHT_IN_DIP = 5;
    private static final int SCRIM_LIGHT = -419430401;
    private static final boolean SWEEP_OPEN_MENU = false;
    private static final String TAG = "DecorView";
    private boolean mAllowUpdateElevation;
    private boolean mApplyFloatingHorizontalInsets;
    private boolean mApplyFloatingVerticalInsets;
    private float mAvailableWidth;
    private BackdropFrameRenderer mBackdropFrameRenderer;
    private final BackgroundFallback mBackgroundFallback;
    private Insets mBackgroundInsets;
    private final Rect mBackgroundPadding;
    private final int mBarEnterExitDuration;
    private Drawable mCaptionBackgroundDrawable;
    private boolean mChanging;
    ViewGroup mContentRoot;
    private DecorCaptionView mDecorCaptionView;
    int mDefaultOpacity;
    private int mDownY;
    private boolean mDrawLegacyNavigationBarBackground;
    private final Rect mDrawingBounds;
    private boolean mElevationAdjustedForStack;
    private ObjectAnimator mFadeAnim;
    private final int mFeatureId;
    private ActionMode mFloatingActionMode;
    private View mFloatingActionModeOriginatingView;
    private final Rect mFloatingInsets;
    private FloatingToolbar mFloatingToolbar;
    private ViewTreeObserver.OnPreDrawListener mFloatingToolbarPreDrawListener;
    final boolean mForceWindowDrawsBarBackgrounds;
    private final Rect mFrameOffsets;
    private final Rect mFramePadding;
    private boolean mHasCaption;
    private final Interpolator mHideInterpolator;
    private final Paint mHorizontalResizeShadowPaint;
    private boolean mIsInPictureInPictureMode;
    private Drawable.Callback mLastBackgroundDrawableCb;
    private Insets mLastBackgroundInsets;
    @UnsupportedAppUsage
    private int mLastBottomInset;
    private boolean mLastHasBottomStableInset;
    private boolean mLastHasLeftStableInset;
    private boolean mLastHasRightStableInset;
    private boolean mLastHasTopStableInset;
    @UnsupportedAppUsage
    private int mLastLeftInset;
    private Drawable mLastOriginalBackgroundDrawable;
    private ViewOutlineProvider mLastOutlineProvider;
    @UnsupportedAppUsage
    private int mLastRightInset;
    private boolean mLastShouldAlwaysConsumeSystemBars;
    private int mLastTopInset;
    private int mLastWindowFlags;
    private final Paint mLegacyNavigationBarBackgroundPaint;
    String mLogTag;
    private Drawable mMenuBackground;
    private final ColorViewState mNavigationColorViewState;
    private Drawable mOriginalBackgroundDrawable;
    private Rect mOutsets;
    ActionMode mPrimaryActionMode;
    private PopupWindow mPrimaryActionModePopup;
    private ActionBarContextView mPrimaryActionModeView;
    private int mResizeMode;
    private final int mResizeShadowSize;
    private Drawable mResizingBackgroundDrawable;
    private int mRootScrollY;
    private final int mSemiTransparentBarColor;
    private final Interpolator mShowInterpolator;
    private Runnable mShowPrimaryActionModePopup;
    private final ColorViewState mStatusColorViewState;
    private View mStatusGuard;
    private Rect mTempRect;
    private Drawable mUserCaptionBackgroundDrawable;
    private final Paint mVerticalResizeShadowPaint;
    private boolean mWatchingForMenu;
    @UnsupportedAppUsage
    private PhoneWindow mWindow;
    private boolean mWindowResizeCallbacksAdded;
    public static final ColorViewAttributes STATUS_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(4, 67108864, 48, 3, 5, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, 16908335, 1024);
    public static final ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(2, 134217728, 80, 5, 3, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, 16908336, 0);
    private static final ViewOutlineProvider PIP_OUTLINE_PROVIDER = new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };

    DecorView(Context context, int featureId, PhoneWindow window, WindowManager.LayoutParams params) {
        super(context);
        boolean z = false;
        this.mAllowUpdateElevation = false;
        this.mElevationAdjustedForStack = false;
        this.mDefaultOpacity = -1;
        this.mDrawingBounds = new Rect();
        this.mBackgroundPadding = new Rect();
        this.mFramePadding = new Rect();
        this.mFrameOffsets = new Rect();
        this.mHasCaption = false;
        this.mStatusColorViewState = new ColorViewState(STATUS_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mNavigationColorViewState = new ColorViewState(NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mBackgroundFallback = new BackgroundFallback();
        this.mLastTopInset = 0;
        this.mLastBottomInset = 0;
        this.mLastRightInset = 0;
        this.mLastLeftInset = 0;
        this.mLastHasTopStableInset = false;
        this.mLastHasBottomStableInset = false;
        this.mLastHasRightStableInset = false;
        this.mLastHasLeftStableInset = false;
        this.mLastWindowFlags = 0;
        this.mLastShouldAlwaysConsumeSystemBars = false;
        this.mRootScrollY = 0;
        this.mOutsets = new Rect();
        this.mWindowResizeCallbacksAdded = false;
        this.mLastBackgroundDrawableCb = null;
        this.mBackdropFrameRenderer = null;
        this.mLogTag = TAG;
        this.mFloatingInsets = new Rect();
        this.mApplyFloatingVerticalInsets = false;
        this.mApplyFloatingHorizontalInsets = false;
        this.mResizeMode = -1;
        this.mVerticalResizeShadowPaint = new Paint();
        this.mHorizontalResizeShadowPaint = new Paint();
        this.mLegacyNavigationBarBackgroundPaint = new Paint();
        this.mBackgroundInsets = Insets.NONE;
        this.mLastBackgroundInsets = Insets.NONE;
        this.mFeatureId = featureId;
        this.mShowInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mHideInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mBarEnterExitDuration = context.getResources().getInteger(C3132R.integer.dock_enter_exit_duration);
        if (context.getResources().getBoolean(C3132R.bool.config_forceWindowDrawsStatusBarBackground) && context.getApplicationInfo().targetSdkVersion >= 24) {
            z = true;
        }
        this.mForceWindowDrawsBarBackgrounds = z;
        this.mSemiTransparentBarColor = context.getResources().getColor(C3132R.color.system_bar_background_semi_transparent, null);
        updateAvailableWidth();
        setWindow(window);
        updateLogTag(params);
        this.mResizeShadowSize = context.getResources().getDimensionPixelSize(C3132R.dimen.resize_shadow_size);
        initResizingPaints();
        this.mLegacyNavigationBarBackgroundPaint.setColor(-16777216);
    }

    void setBackgroundFallback(Drawable fallbackDrawable) {
        this.mBackgroundFallback.setDrawable(fallbackDrawable);
        setWillNotDraw(getBackground() == null && !this.mBackgroundFallback.hasFallback());
    }

    public Drawable getBackgroundFallback() {
        return this.mBackgroundFallback.getDrawable();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean gatherTransparentRegion(Region region) {
        boolean statusOpaque = gatherTransparentRegion(this.mStatusColorViewState, region);
        boolean navOpaque = gatherTransparentRegion(this.mNavigationColorViewState, region);
        boolean decorOpaque = super.gatherTransparentRegion(region);
        return statusOpaque || navOpaque || decorOpaque;
    }

    boolean gatherTransparentRegion(ColorViewState colorViewState, Region region) {
        if (colorViewState.view != null && colorViewState.visible && isResizing()) {
            return colorViewState.view.gatherTransparentRegion(region);
        }
        return false;
    }

    @Override // android.view.View
    public void onDraw(Canvas c) {
        super.onDraw(c);
        this.mBackgroundFallback.draw(this, this.mContentRoot, c, this.mWindow.mContentParent, this.mStatusColorViewState.view, this.mNavigationColorViewState.view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int action = event.getAction();
        boolean isDown = action == 0;
        if (isDown && event.getRepeatCount() == 0) {
            if (this.mWindow.mPanelChordingKey > 0 && this.mWindow.mPanelChordingKey != keyCode) {
                boolean handled = dispatchKeyShortcutEvent(event);
                if (handled) {
                    return true;
                }
            }
            if (this.mWindow.mPreparedPanel != null && this.mWindow.mPreparedPanel.isOpen && this.mWindow.performPanelShortcut(this.mWindow.mPreparedPanel, keyCode, event, 0)) {
                return true;
            }
        }
        if (!this.mWindow.isDestroyed()) {
            Window.Callback cb = this.mWindow.getCallback();
            boolean handled2 = (cb == null || this.mFeatureId >= 0) ? super.dispatchKeyEvent(event) : cb.dispatchKeyEvent(event);
            if (handled2) {
                return true;
            }
        }
        return isDown ? this.mWindow.onKeyDown(this.mFeatureId, event.getKeyCode(), event) : this.mWindow.onKeyUp(this.mFeatureId, event.getKeyCode(), event);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyShortcutEvent(KeyEvent ev) {
        if (this.mWindow.mPreparedPanel != null) {
            boolean handled = this.mWindow.performPanelShortcut(this.mWindow.mPreparedPanel, ev.getKeyCode(), ev, 1);
            if (handled) {
                if (this.mWindow.mPreparedPanel != null) {
                    this.mWindow.mPreparedPanel.isHandled = true;
                }
                return true;
            }
        }
        Window.Callback cb = this.mWindow.getCallback();
        boolean handled2 = (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchKeyShortcutEvent(ev) : cb.dispatchKeyShortcutEvent(ev);
        if (handled2) {
            return true;
        }
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        if (st != null && this.mWindow.mPreparedPanel == null) {
            this.mWindow.preparePanel(st, ev);
            boolean handled3 = this.mWindow.performPanelShortcut(st, ev.getKeyCode(), ev, 1);
            st.isPrepared = false;
            if (handled3) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTouchEvent(ev) : cb.dispatchTouchEvent(ev);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTrackballEvent(ev) : cb.dispatchTrackballEvent(ev);
    }

    @Override // android.view.View
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
        if (super.dispatchKeyEvent(event)) {
            return true;
        }
        return getViewRootImpl() != null && getViewRootImpl().dispatchUnhandledKeyEvent(event);
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

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return onInterceptTouchEvent(event);
    }

    private boolean isOutOfInnerBounds(int x, int y) {
        return x < 0 || y < 0 || x > getWidth() || y > getHeight();
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (this.mHasCaption && isShowingCaption() && action == 0) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (isOutOfInnerBounds(x, y)) {
                return true;
            }
        }
        int x2 = this.mFeatureId;
        if (x2 >= 0 && action == 0) {
            int x3 = (int) event.getX();
            int y2 = (int) event.getY();
            if (isOutOfBounds(x3, y2)) {
                this.mWindow.closePanel(this.mFeatureId);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int eventType) {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return;
        }
        if ((this.mFeatureId == 0 || this.mFeatureId == 6 || this.mFeatureId == 2 || this.mFeatureId == 5) && getChildCount() == 1) {
            getChildAt(0).sendAccessibilityEvent(eventType);
        } else {
            super.sendAccessibilityEvent(eventType);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && !this.mWindow.isDestroyed() && cb.dispatchPopulateAccessibilityEvent(event)) {
            return true;
        }
        return super.dispatchPopulateAccessibilityEventInternal(event);
    }

    @Override // android.view.View
    protected boolean setFrame(int l, int t, int r, int b) {
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0145 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSpec2;
        int heightMeasureSpec2;
        int mode;
        int mode2;
        boolean measure;
        TypedValue tv;
        int min;
        TypedValue tvh;
        int h;
        int w;
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        boolean isPortrait = getResources().getConfiguration().orientation == 1;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        boolean fixedWidth = false;
        this.mApplyFloatingHorizontalInsets = false;
        if (widthMode == Integer.MIN_VALUE) {
            TypedValue tvw = isPortrait ? this.mWindow.mFixedWidthMinor : this.mWindow.mFixedWidthMajor;
            if (tvw != null && tvw.type != 0) {
                if (tvw.type != 5) {
                    if (tvw.type == 6) {
                        w = (int) tvw.getFraction(metrics.widthPixels, metrics.widthPixels);
                    } else {
                        w = 0;
                    }
                } else {
                    w = (int) tvw.getDimension(metrics);
                }
                int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
                if (w <= 0) {
                    int widthMeasureSpec3 = View.MeasureSpec.makeMeasureSpec((widthSize - this.mFloatingInsets.left) - this.mFloatingInsets.right, Integer.MIN_VALUE);
                    this.mApplyFloatingHorizontalInsets = true;
                    widthMeasureSpec2 = widthMeasureSpec3;
                } else {
                    widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min(w, widthSize), 1073741824);
                    fixedWidth = true;
                }
                this.mApplyFloatingVerticalInsets = false;
                if (heightMode == Integer.MIN_VALUE) {
                    if (isPortrait) {
                        tvh = this.mWindow.mFixedHeightMajor;
                    } else {
                        tvh = this.mWindow.mFixedHeightMinor;
                    }
                    if (tvh != null && tvh.type != 0) {
                        if (tvh.type != 5) {
                            if (tvh.type == 6) {
                                h = (int) tvh.getFraction(metrics.heightPixels, metrics.heightPixels);
                            } else {
                                h = 0;
                            }
                        } else {
                            h = (int) tvh.getDimension(metrics);
                        }
                        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
                        if (h > 0) {
                            heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min(h, heightSize), 1073741824);
                        } else if ((this.mWindow.getAttributes().flags & 256) == 0) {
                            int heightMeasureSpec3 = View.MeasureSpec.makeMeasureSpec((heightSize - this.mFloatingInsets.top) - this.mFloatingInsets.bottom, Integer.MIN_VALUE);
                            this.mApplyFloatingVerticalInsets = true;
                            heightMeasureSpec2 = heightMeasureSpec3;
                        }
                        getOutsets(this.mOutsets);
                        if ((this.mOutsets.top <= 0 || this.mOutsets.bottom > 0) && (mode = View.MeasureSpec.getMode(heightMeasureSpec2)) != 0) {
                            int height = View.MeasureSpec.getSize(heightMeasureSpec2);
                            heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.top + height + this.mOutsets.bottom, mode);
                        }
                        if ((this.mOutsets.left <= 0 || this.mOutsets.right > 0) && (mode2 = View.MeasureSpec.getMode(widthMeasureSpec2)) != 0) {
                            widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.left + View.MeasureSpec.getSize(widthMeasureSpec2) + this.mOutsets.right, mode2);
                        }
                        super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
                        int width = getMeasuredWidth();
                        measure = false;
                        int widthMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
                        if (!fixedWidth && widthMode == Integer.MIN_VALUE) {
                            tv = !isPortrait ? this.mWindow.mMinWidthMinor : this.mWindow.mMinWidthMajor;
                            if (tv.type != 0) {
                                if (tv.type != 5) {
                                    if (tv.type == 6) {
                                        min = (int) tv.getFraction(this.mAvailableWidth, this.mAvailableWidth);
                                    } else {
                                        min = 0;
                                    }
                                } else {
                                    min = (int) tv.getDimension(metrics);
                                }
                                if (width < min) {
                                    widthMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(min, 1073741824);
                                    measure = true;
                                }
                            }
                        }
                        if (measure) {
                            super.onMeasure(widthMeasureSpec4, heightMeasureSpec2);
                            return;
                        }
                        return;
                    }
                }
                heightMeasureSpec2 = heightMeasureSpec;
                getOutsets(this.mOutsets);
                if (this.mOutsets.top <= 0) {
                }
                int height2 = View.MeasureSpec.getSize(heightMeasureSpec2);
                heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.top + height2 + this.mOutsets.bottom, mode);
                if (this.mOutsets.left <= 0) {
                }
                widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.left + View.MeasureSpec.getSize(widthMeasureSpec2) + this.mOutsets.right, mode2);
                super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
                int width2 = getMeasuredWidth();
                measure = false;
                int widthMeasureSpec42 = View.MeasureSpec.makeMeasureSpec(width2, 1073741824);
                if (!fixedWidth) {
                    if (!isPortrait) {
                    }
                    if (tv.type != 0) {
                    }
                }
                if (measure) {
                }
            }
        }
        widthMeasureSpec2 = widthMeasureSpec;
        this.mApplyFloatingVerticalInsets = false;
        if (heightMode == Integer.MIN_VALUE) {
        }
        heightMeasureSpec2 = heightMeasureSpec;
        getOutsets(this.mOutsets);
        if (this.mOutsets.top <= 0) {
        }
        int height22 = View.MeasureSpec.getSize(heightMeasureSpec2);
        heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.top + height22 + this.mOutsets.bottom, mode);
        if (this.mOutsets.left <= 0) {
        }
        widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.left + View.MeasureSpec.getSize(widthMeasureSpec2) + this.mOutsets.right, mode2);
        super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
        int width22 = getMeasuredWidth();
        measure = false;
        int widthMeasureSpec422 = View.MeasureSpec.makeMeasureSpec(width22, 1073741824);
        if (!fixedWidth) {
        }
        if (measure) {
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
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

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mMenuBackground != null) {
            this.mMenuBackground.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View originalView) {
        return showContextMenuForChildInternal(originalView, Float.NaN, Float.NaN);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
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
        boolean isPopup = (Float.isNaN(x) || Float.isNaN(y)) ? false : true;
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
        return helper != null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return startActionModeForChild(originalView, callback, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View child, ActionMode.Callback callback, int type) {
        return startActionMode(child, callback, type);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    @Override // android.view.View
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
        if (mode != null) {
            if (mode.getType() == 0) {
                cleanupPrimaryActionMode();
                this.mPrimaryActionMode = mode;
            } else if (mode.getType() == 1) {
                if (this.mFloatingActionMode != null) {
                    this.mFloatingActionMode.finish();
                }
                this.mFloatingActionMode = mode;
            }
        } else {
            mode = createActionMode(type, wrappedCallback, originatingView);
            if (mode != null && wrappedCallback.onCreateActionMode(mode, mode.getMenu())) {
                setHandledActionMode(mode);
            } else {
                mode = null;
            }
        }
        if (mode != null && this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
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

    /* JADX INFO: Access modifiers changed from: private */
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

    void startChanging() {
        this.mChanging = true;
    }

    void finishChanging() {
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

    @Override // android.view.View
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

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int visible) {
        updateColorViews(null, true);
        updateDecorCaptionStatus(getResources().getConfiguration());
        if (this.mStatusGuard != null && this.mStatusGuard.getVisibility() == 0) {
            updateStatusGuardColor();
        }
    }

    @Override // android.view.View
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

    @Override // android.view.ViewGroup
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
        return isNavBarToRightEdge(bottomInset, rightInset) ? rightInset : isNavBarToLeftEdge(bottomInset, leftInset) ? leftInset : bottomInset;
    }

    public static void getNavigationBarRect(int canvasWidth, int canvasHeight, Rect stableInsets, Rect contentInsets, Rect outRect, float scale) {
        int bottomInset = (int) (getColorViewBottomInset(stableInsets.bottom, contentInsets.bottom) * scale);
        int leftInset = (int) (getColorViewLeftInset(stableInsets.left, contentInsets.left) * scale);
        int rightInset = (int) (getColorViewLeftInset(stableInsets.right, contentInsets.right) * scale);
        int size = getNavBarSize(bottomInset, rightInset, leftInset);
        if (isNavBarToRightEdge(bottomInset, rightInset)) {
            outRect.set(canvasWidth - size, 0, canvasWidth, canvasHeight);
        } else if (isNavBarToLeftEdge(bottomInset, leftInset)) {
            outRect.set(0, 0, size, canvasHeight);
        } else {
            outRect.set(0, canvasHeight - size, canvasWidth, canvasHeight);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:175:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:181:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    WindowInsets updateColorViews(WindowInsets insets, boolean animate) {
        int statusBarSideInset;
        int i;
        ViewRootImpl vri;
        WindowInsets insets2;
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        int sysUiVisibility = attrs.systemUiVisibility | getWindowSystemUiVisibility();
        boolean z = true;
        boolean isImeWindow = this.mWindow.getAttributes().type == 2011;
        if (!this.mWindow.mIsFloating || isImeWindow) {
            boolean disallowAnimate = (!isLaidOut()) | (((this.mLastWindowFlags ^ attrs.flags) & Integer.MIN_VALUE) != 0);
            this.mLastWindowFlags = attrs.flags;
            if (insets != null) {
                this.mLastTopInset = getColorViewTopInset(insets.getStableInsetTop(), insets.getSystemWindowInsetTop());
                this.mLastBottomInset = getColorViewBottomInset(insets.getStableInsetBottom(), insets.getSystemWindowInsetBottom());
                this.mLastRightInset = getColorViewRightInset(insets.getStableInsetRight(), insets.getSystemWindowInsetRight());
                this.mLastLeftInset = getColorViewRightInset(insets.getStableInsetLeft(), insets.getSystemWindowInsetLeft());
                boolean hasTopStableInset = insets.getStableInsetTop() != 0;
                boolean disallowAnimate2 = disallowAnimate | (hasTopStableInset != this.mLastHasTopStableInset);
                this.mLastHasTopStableInset = hasTopStableInset;
                boolean hasBottomStableInset = insets.getStableInsetBottom() != 0;
                boolean disallowAnimate3 = disallowAnimate2 | (hasBottomStableInset != this.mLastHasBottomStableInset);
                this.mLastHasBottomStableInset = hasBottomStableInset;
                boolean hasRightStableInset = insets.getStableInsetRight() != 0;
                boolean disallowAnimate4 = disallowAnimate3 | (hasRightStableInset != this.mLastHasRightStableInset);
                this.mLastHasRightStableInset = hasRightStableInset;
                boolean hasLeftStableInset = insets.getStableInsetLeft() != 0;
                disallowAnimate = disallowAnimate4 | (hasLeftStableInset != this.mLastHasLeftStableInset);
                this.mLastHasLeftStableInset = hasLeftStableInset;
                this.mLastShouldAlwaysConsumeSystemBars = insets.shouldAlwaysConsumeSystemBars();
            }
            boolean disallowAnimate5 = disallowAnimate;
            boolean navBarToRightEdge = isNavBarToRightEdge(this.mLastBottomInset, this.mLastRightInset);
            boolean navBarToLeftEdge = isNavBarToLeftEdge(this.mLastBottomInset, this.mLastLeftInset);
            int navBarSize = getNavBarSize(this.mLastBottomInset, this.mLastRightInset, this.mLastLeftInset);
            updateColorViewInt(this.mNavigationColorViewState, sysUiVisibility, calculateNavigationBarColor(), this.mWindow.mNavigationBarDividerColor, navBarSize, navBarToRightEdge || navBarToLeftEdge, navBarToLeftEdge, 0, animate && !disallowAnimate5, this.mForceWindowDrawsBarBackgrounds);
            boolean oldDrawLegacy = this.mDrawLegacyNavigationBarBackground;
            this.mDrawLegacyNavigationBarBackground = this.mNavigationColorViewState.visible && (this.mWindow.getAttributes().flags & Integer.MIN_VALUE) == 0;
            if (oldDrawLegacy != this.mDrawLegacyNavigationBarBackground && (vri = getViewRootImpl()) != null) {
                vri.requestInvalidateRootRenderNode();
            }
            boolean statusBarNeedsRightInset = navBarToRightEdge && this.mNavigationColorViewState.present;
            boolean statusBarNeedsLeftInset = navBarToLeftEdge && this.mNavigationColorViewState.present;
            if (statusBarNeedsRightInset) {
                i = this.mLastRightInset;
            } else if (statusBarNeedsLeftInset) {
                i = this.mLastLeftInset;
            } else {
                statusBarSideInset = 0;
                updateColorViewInt(this.mStatusColorViewState, sysUiVisibility, calculateStatusBarColor(), 0, this.mLastTopInset, false, statusBarNeedsLeftInset, statusBarSideInset, (animate || disallowAnimate5) ? false : true, this.mForceWindowDrawsBarBackgrounds);
            }
            statusBarSideInset = i;
            updateColorViewInt(this.mStatusColorViewState, sysUiVisibility, calculateStatusBarColor(), 0, this.mLastTopInset, false, statusBarNeedsLeftInset, statusBarSideInset, (animate || disallowAnimate5) ? false : true, this.mForceWindowDrawsBarBackgrounds);
        }
        boolean hideNavigation = (sysUiVisibility & 2) != 0;
        boolean forceConsumingNavBar = (this.mForceWindowDrawsBarBackgrounds && (attrs.flags & Integer.MIN_VALUE) == 0 && (sysUiVisibility & 512) == 0 && !hideNavigation) || (this.mLastShouldAlwaysConsumeSystemBars && hideNavigation);
        boolean consumingNavBar = !((attrs.flags & Integer.MIN_VALUE) == 0 || (sysUiVisibility & 512) != 0 || hideNavigation) || forceConsumingNavBar;
        boolean fullscreen = ((sysUiVisibility & 4) == 0 && (attrs.flags & 1024) == 0) ? false : true;
        if (((sysUiVisibility & 1024) != 0 || (attrs.flags & 256) != 0 || (attrs.flags & 65536) != 0 || !this.mForceWindowDrawsBarBackgrounds || this.mLastTopInset == 0) && (!this.mLastShouldAlwaysConsumeSystemBars || !fullscreen)) {
            z = false;
        }
        boolean consumingStatusBar = z;
        int consumedTop = consumingStatusBar ? this.mLastTopInset : 0;
        int consumedRight = consumingNavBar ? this.mLastRightInset : 0;
        int consumedBottom = consumingNavBar ? this.mLastBottomInset : 0;
        int consumedLeft = consumingNavBar ? this.mLastLeftInset : 0;
        if (this.mContentRoot != null && (this.mContentRoot.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mContentRoot.getLayoutParams();
            if (lp.topMargin != consumedTop || lp.rightMargin != consumedRight || lp.bottomMargin != consumedBottom || lp.leftMargin != consumedLeft) {
                lp.topMargin = consumedTop;
                lp.rightMargin = consumedRight;
                lp.bottomMargin = consumedBottom;
                lp.leftMargin = consumedLeft;
                this.mContentRoot.setLayoutParams(lp);
                if (insets == null) {
                    requestApplyInsets();
                }
            }
            if (insets != null) {
                insets2 = insets.inset(consumedLeft, consumedTop, consumedRight, consumedBottom);
                if (!forceConsumingNavBar) {
                    this.mBackgroundInsets = Insets.m132of(this.mLastLeftInset, 0, this.mLastRightInset, this.mLastBottomInset);
                } else {
                    this.mBackgroundInsets = Insets.NONE;
                }
                updateBackgroundDrawable();
                if (insets2 == null) {
                    return insets2.consumeStableInsets();
                }
                return insets2;
            }
        }
        insets2 = insets;
        if (!forceConsumingNavBar) {
        }
        updateBackgroundDrawable();
        if (insets2 == null) {
        }
    }

    private void updateBackgroundDrawable() {
        if (this.mBackgroundInsets == null) {
            this.mBackgroundInsets = Insets.NONE;
        }
        if (this.mBackgroundInsets.equals(this.mLastBackgroundInsets) && this.mLastOriginalBackgroundDrawable == this.mOriginalBackgroundDrawable) {
            return;
        }
        if (this.mOriginalBackgroundDrawable == null || this.mBackgroundInsets.equals(Insets.NONE)) {
            super.setBackgroundDrawable(this.mOriginalBackgroundDrawable);
        } else {
            super.setBackgroundDrawable(new InsetDrawable(this.mOriginalBackgroundDrawable, this.mBackgroundInsets.left, this.mBackgroundInsets.top, this.mBackgroundInsets.right, this.mBackgroundInsets.bottom) { // from class: com.android.internal.policy.DecorView.2
                @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
                public boolean getPadding(Rect padding) {
                    return getDrawable().getPadding(padding);
                }
            });
        }
        this.mLastBackgroundInsets = this.mBackgroundInsets;
        this.mLastOriginalBackgroundDrawable = this.mOriginalBackgroundDrawable;
    }

    @Override // android.view.View
    public Drawable getBackground() {
        return this.mOriginalBackgroundDrawable;
    }

    private int calculateStatusBarColor() {
        return calculateBarColor(this.mWindow.getAttributes().flags, 67108864, this.mSemiTransparentBarColor, this.mWindow.mStatusBarColor, getWindowSystemUiVisibility(), 8192, this.mWindow.mEnsureStatusBarContrastWhenTransparent);
    }

    private int calculateNavigationBarColor() {
        return calculateBarColor(this.mWindow.getAttributes().flags, 134217728, this.mSemiTransparentBarColor, this.mWindow.mNavigationBarColor, getWindowSystemUiVisibility(), 16, this.mWindow.mEnsureNavigationBarContrastWhenTransparent && getContext().getResources().getBoolean(C3132R.bool.config_navBarNeedsScrim));
    }

    public static int calculateBarColor(int flags, int translucentFlag, int semiTransparentBarColor, int barColor, int sysuiVis, int lightSysuiFlag, boolean scrimTransparent) {
        if ((flags & translucentFlag) != 0) {
            return semiTransparentBarColor;
        }
        if ((Integer.MIN_VALUE & flags) == 0) {
            return -16777216;
        }
        if (scrimTransparent && Color.alpha(barColor) == 0) {
            boolean light = (sysuiVis & lightSysuiFlag) != 0;
            return light ? SCRIM_LIGHT : semiTransparentBarColor;
        }
        return barColor;
    }

    private int getCurrentColor(ColorViewState state) {
        if (state.visible) {
            return state.color;
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00f3, code lost:
        if (r6.leftMargin != r14) goto L69;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateColorViewInt(final ColorViewState state, int sysUiVis, int color, int dividerColor, int size, boolean verticalBar, boolean seascape, int sideMargin, boolean animate, boolean force) {
        int resolvedGravity;
        boolean visibilityChanged;
        int rightMargin;
        int leftMargin;
        state.present = state.attributes.isPresent(sysUiVis, this.mWindow.getAttributes().flags, force);
        boolean show = state.attributes.isVisible(state.present, color, this.mWindow.getAttributes().flags, force);
        boolean showView = show && !isResizing() && size > 0;
        boolean visibilityChanged2 = false;
        View view = state.view;
        int resolvedHeight = verticalBar ? -1 : size;
        int resolvedWidth = verticalBar ? size : -1;
        if (verticalBar) {
            resolvedGravity = seascape ? state.attributes.seascapeGravity : state.attributes.horizontalGravity;
        } else {
            resolvedGravity = state.attributes.verticalGravity;
        }
        if (view == null) {
            if (showView) {
                View view2 = new View(this.mContext);
                view = view2;
                state.view = view2;
                setColor(view, color, dividerColor, verticalBar, seascape);
                view.setTransitionName(state.attributes.transitionName);
                view.setId(state.attributes.f2488id);
                visibilityChanged2 = true;
                view.setVisibility(4);
                state.targetVisibility = 0;
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(resolvedWidth, resolvedHeight, resolvedGravity);
                if (seascape) {
                    lp.leftMargin = sideMargin;
                } else {
                    lp.rightMargin = sideMargin;
                }
                addView(view, lp);
                updateColorViewTranslations();
            }
        } else {
            int vis = showView ? 0 : 4;
            boolean visibilityChanged3 = state.targetVisibility != vis;
            state.targetVisibility = vis;
            FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) view.getLayoutParams();
            int rightMargin2 = seascape ? 0 : sideMargin;
            int leftMargin2 = seascape ? sideMargin : 0;
            if (lp2.height == resolvedHeight && lp2.width == resolvedWidth && lp2.gravity == resolvedGravity) {
                rightMargin = rightMargin2;
                if (lp2.rightMargin == rightMargin) {
                    visibilityChanged = visibilityChanged3;
                    leftMargin = leftMargin2;
                } else {
                    visibilityChanged = visibilityChanged3;
                    leftMargin = leftMargin2;
                }
            } else {
                visibilityChanged = visibilityChanged3;
                rightMargin = rightMargin2;
                leftMargin = leftMargin2;
            }
            lp2.height = resolvedHeight;
            lp2.width = resolvedWidth;
            lp2.gravity = resolvedGravity;
            lp2.rightMargin = rightMargin;
            lp2.leftMargin = leftMargin;
            view.setLayoutParams(lp2);
            if (showView) {
                setColor(view, color, dividerColor, verticalBar, seascape);
            }
            visibilityChanged2 = visibilityChanged;
        }
        if (visibilityChanged2) {
            view.animate().cancel();
            if (!animate || isResizing()) {
                view.setAlpha(1.0f);
                view.setVisibility(showView ? 0 : 4);
            } else if (showView) {
                if (view.getVisibility() != 0) {
                    view.setVisibility(0);
                    view.setAlpha(0.0f);
                }
                view.animate().alpha(1.0f).setInterpolator(this.mShowInterpolator).setDuration(this.mBarEnterExitDuration);
            } else {
                view.animate().alpha(0.0f).setInterpolator(this.mHideInterpolator).setDuration(this.mBarEnterExitDuration).withEndAction(new Runnable() { // from class: com.android.internal.policy.DecorView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        state.view.setAlpha(1.0f);
                        state.view.setVisibility(4);
                    }
                });
            }
        }
        state.visible = show;
        state.color = color;
    }

    private static void setColor(View v, int color, int dividerColor, boolean verticalBar, boolean seascape) {
        if (dividerColor != 0) {
            Pair<Boolean, Boolean> dir = (Pair) v.getTag();
            if (dir == null || ((Boolean) dir.first).booleanValue() != verticalBar || ((Boolean) dir.second).booleanValue() != seascape) {
                int size = Math.round(TypedValue.applyDimension(1, 1.0f, v.getContext().getResources().getDisplayMetrics()));
                v.setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(dividerColor), new InsetDrawable((Drawable) new ColorDrawable(color), (!verticalBar || seascape) ? 0 : size, !verticalBar ? size : 0, (verticalBar && seascape) ? size : 0, 0)}));
                v.setTag(new Pair(Boolean.valueOf(verticalBar), Boolean.valueOf(seascape)));
                return;
            }
            LayerDrawable d = (LayerDrawable) v.getBackground();
            InsetDrawable inset = (InsetDrawable) d.getDrawable(1);
            ((ColorDrawable) inset.getDrawable()).setColor(color);
            ((ColorDrawable) d.getDrawable(0)).setColor(dividerColor);
            return;
        }
        v.setTag(null);
        v.setBackgroundColor(color);
    }

    private void updateColorViewTranslations() {
        int rootScrollY = this.mRootScrollY;
        if (this.mStatusColorViewState.view != null) {
            this.mStatusColorViewState.view.setTranslationY(rootScrollY > 0 ? rootScrollY : 0.0f);
        }
        if (this.mNavigationColorViewState.view != null) {
            this.mNavigationColorViewState.view.setTranslationY(rootScrollY < 0 ? rootScrollY : 0.0f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private WindowInsets updateStatusGuard(WindowInsets insets) {
        boolean showStatusGuard;
        int i;
        WindowInsets insets2 = insets;
        if (this.mPrimaryActionModeView != null && (this.mPrimaryActionModeView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) this.mPrimaryActionModeView.getLayoutParams();
            boolean mlpChanged = false;
            if (this.mPrimaryActionModeView.isShown()) {
                if (this.mTempRect == null) {
                    this.mTempRect = new Rect();
                }
                Rect rect = this.mTempRect;
                WindowInsets innerInsets = this.mWindow.mContentParent.computeSystemWindowInsets(insets2, rect);
                int newTopMargin = innerInsets.getSystemWindowInsetTop();
                int newLeftMargin = innerInsets.getSystemWindowInsetLeft();
                int newRightMargin = innerInsets.getSystemWindowInsetRight();
                WindowInsets rootInsets = getRootWindowInsets();
                int newGuardLeftMargin = rootInsets.getSystemWindowInsetLeft();
                int newGuardRightMargin = rootInsets.getSystemWindowInsetRight();
                if (mlp.topMargin != newTopMargin || mlp.leftMargin != newLeftMargin || mlp.rightMargin != newRightMargin) {
                    mlpChanged = true;
                    mlp.topMargin = newTopMargin;
                    mlp.leftMargin = newLeftMargin;
                    mlp.rightMargin = newRightMargin;
                }
                if (newTopMargin <= 0 || this.mStatusGuard != null) {
                    if (this.mStatusGuard != null) {
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.mStatusGuard.getLayoutParams();
                        if (lp.height != mlp.topMargin || lp.leftMargin != newGuardLeftMargin || lp.rightMargin != newGuardRightMargin) {
                            lp.height = mlp.topMargin;
                            lp.leftMargin = newGuardLeftMargin;
                            lp.rightMargin = newGuardRightMargin;
                            this.mStatusGuard.setLayoutParams(lp);
                        }
                    }
                } else {
                    this.mStatusGuard = new View(this.mContext);
                    this.mStatusGuard.setVisibility(8);
                    FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(-1, mlp.topMargin, 51);
                    lp2.leftMargin = newGuardLeftMargin;
                    lp2.rightMargin = newGuardRightMargin;
                    addView(this.mStatusGuard, indexOfChild(this.mStatusColorViewState.view), lp2);
                }
                boolean showStatusGuard2 = this.mStatusGuard != null;
                if (showStatusGuard2 && this.mStatusGuard.getVisibility() != 0) {
                    updateStatusGuardColor();
                }
                boolean nonOverlay = (this.mWindow.getLocalFeaturesPrivate() & 1024) == 0;
                if (nonOverlay && showStatusGuard2) {
                    insets2 = insets2.inset(0, insets.getSystemWindowInsetTop(), 0, 0);
                }
                showStatusGuard = showStatusGuard2;
            } else {
                showStatusGuard = false;
                if (mlp.topMargin != 0 || mlp.leftMargin != 0 || mlp.rightMargin != 0) {
                    mlpChanged = true;
                    i = 0;
                    mlp.topMargin = 0;
                    if (mlpChanged) {
                        this.mPrimaryActionModeView.setLayoutParams(mlp);
                    }
                }
            }
            i = 0;
            if (mlpChanged) {
            }
        } else {
            showStatusGuard = false;
            i = 0;
        }
        if (this.mStatusGuard != null) {
            View view = this.mStatusGuard;
            if (!showStatusGuard) {
                i = 8;
            }
            view.setVisibility(i);
        }
        return insets2;
    }

    private void updateStatusGuardColor() {
        int color;
        boolean lightStatusBar = (getWindowSystemUiVisibility() & 8192) != 0;
        View view = this.mStatusGuard;
        if (lightStatusBar) {
            color = this.mContext.getColor(C3132R.color.decor_view_status_guard_light);
        } else {
            color = this.mContext.getColor(C3132R.color.decor_view_status_guard);
        }
        view.setBackgroundColor(color);
    }

    public void updatePictureInPictureOutlineProvider(boolean isInPictureInPictureMode) {
        if (this.mIsInPictureInPictureMode == isInPictureInPictureMode) {
            return;
        }
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

    @Override // android.view.View
    public void setOutlineProvider(ViewOutlineProvider provider) {
        super.setOutlineProvider(provider);
        this.mLastOutlineProvider = provider;
    }

    private void drawableChanged() {
        if (this.mChanging) {
            return;
        }
        Rect framePadding = this.mFramePadding != null ? this.mFramePadding : new Rect();
        Rect backgroundPadding = this.mBackgroundPadding != null ? this.mBackgroundPadding : new Rect();
        setPadding(framePadding.left + backgroundPadding.left, framePadding.top + backgroundPadding.top, framePadding.right + backgroundPadding.right, framePadding.bottom + backgroundPadding.bottom);
        requestLayout();
        invalidate();
        int opacity = -1;
        WindowConfiguration winConfig = getResources().getConfiguration().windowConfiguration;
        if (winConfig.hasWindowShadow()) {
            opacity = -3;
        } else {
            Drawable bg = getBackground();
            Drawable fg = getForeground();
            if (bg != null) {
                if (fg == null) {
                    opacity = bg.getOpacity();
                } else if (framePadding.left <= 0 && framePadding.top <= 0 && framePadding.right <= 0 && framePadding.bottom <= 0) {
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
                } else {
                    opacity = -3;
                }
            }
        }
        this.mDefaultOpacity = opacity;
        if (this.mFeatureId < 0) {
            this.mWindow.setDefaultWindowFormat(opacity);
        }
    }

    @Override // android.view.View
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

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
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

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
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
        if (st != null && st.menu != null && this.mFeatureId < 0) {
            st.menu.close();
        }
        releaseThreadedRenderer();
        if (this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().removeWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = false;
        }
    }

    @Override // android.view.View
    public void onCloseSystemDialogs(String reason) {
        if (this.mFeatureId >= 0) {
            this.mWindow.closeAllPanels();
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public SurfaceHolder.Callback2 willYouTakeTheSurface() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeSurfaceCallback;
        }
        return null;
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public InputQueue.Callback willYouTakeTheInputQueue() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeInputQueueCallback;
        }
        return null;
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceType(int type) {
        this.mWindow.setType(type);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceFormat(int format) {
        this.mWindow.setFormat(format);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceKeepScreenOn(boolean keepOn) {
        if (keepOn) {
            this.mWindow.addFlags(128);
        } else {
            this.mWindow.clearFlags(128);
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
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
                this.mPrimaryActionModePopup = new PopupWindow(actionBarContext2, (AttributeSet) null, (int) C3132R.attr.actionModePopupWindowStyle);
                this.mPrimaryActionModePopup.setWindowLayoutType(2);
                this.mPrimaryActionModePopup.setContentView(this.mPrimaryActionModeView);
                this.mPrimaryActionModePopup.setWidth(-1);
                actionBarContext2.getTheme().resolveAttribute(16843499, outValue, true);
                int height = TypedValue.complexToDimensionPixelSize(outValue.data, actionBarContext2.getResources().getDisplayMetrics());
                this.mPrimaryActionModeView.setContentHeight(height);
                this.mPrimaryActionModePopup.setHeight(-2);
                this.mShowPrimaryActionModePopup = new Runnable() { // from class: com.android.internal.policy.DecorView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        DecorView.this.mPrimaryActionModePopup.showAtLocation(DecorView.this.mPrimaryActionModeView.getApplicationWindowToken(), 55, 0, 0);
                        DecorView.this.endOnGoingFadeAnimation();
                        if (!DecorView.this.shouldAnimatePrimaryActionModeView()) {
                            DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                            DecorView.this.mPrimaryActionModeView.setVisibility(0);
                            return;
                        }
                        DecorView.this.mFadeAnim = ObjectAnimator.ofFloat(DecorView.this.mPrimaryActionModeView, (Property<ActionBarContextView, Float>) View.ALPHA, 0.0f, 1.0f);
                        DecorView.this.mFadeAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.4.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationStart(Animator animation) {
                                DecorView.this.mPrimaryActionModeView.setVisibility(0);
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                                DecorView.this.mFadeAnim = null;
                            }
                        });
                        DecorView.this.mFadeAnim.start();
                    }
                };
            } else {
                ViewStub stub = (ViewStub) findViewById(C3132R.C3134id.action_mode_bar_stub);
                if (stub != null) {
                    this.mPrimaryActionModeView = (ActionBarContextView) stub.inflate();
                    this.mPrimaryActionModePopup = null;
                }
            }
        }
        if (this.mPrimaryActionModeView != null) {
            this.mPrimaryActionModeView.killMode();
            ActionMode mode = new StandaloneActionMode(this.mPrimaryActionModeView.getContext(), this.mPrimaryActionModeView, callback, this.mPrimaryActionModePopup == null);
            return mode;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
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
            this.mFadeAnim = ObjectAnimator.ofFloat(this.mPrimaryActionModeView, (Property<ActionBarContextView, Float>) View.ALPHA, 0.0f, 1.0f);
            this.mFadeAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                    DecorView.this.mFadeAnim = null;
                }
            });
            this.mFadeAnim.start();
        } else {
            this.mPrimaryActionModeView.setAlpha(1.0f);
            this.mPrimaryActionModeView.setVisibility(0);
        }
        this.mPrimaryActionModeView.sendAccessibilityEvent(32);
    }

    boolean shouldAnimatePrimaryActionModeView() {
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
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.policy.DecorView.6
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
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

    void enableCaption(boolean attachedAndVisible) {
        if (this.mHasCaption != attachedAndVisible) {
            this.mHasCaption = attachedAndVisible;
            if (getForeground() != null) {
                drawableChanged();
            }
        }
    }

    void setWindow(PhoneWindow phoneWindow) {
        this.mWindow = phoneWindow;
        Context context = getContext();
        if (context instanceof DecorContext) {
            DecorContext decorContext = (DecorContext) context;
            decorContext.setPhoneWindow(this.mWindow);
        }
    }

    @Override // android.view.View
    public Resources getResources() {
        return getContext().getResources();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateDecorCaptionStatus(newConfig);
        updateAvailableWidth();
        initializeElevation();
    }

    @Override // android.view.View
    public void onMovedToDisplay(int displayId, Configuration config) {
        super.onMovedToDisplay(displayId, config);
        getContext().updateDisplay(displayId);
    }

    private boolean isFillingScreen(Configuration config) {
        boolean isFullscreen = config.windowConfiguration.getWindowingMode() == 1;
        return isFullscreen && ((getWindowSystemUiVisibility() | getSystemUiVisibility()) & 4) != 0;
    }

    private void updateDecorCaptionStatus(Configuration config) {
        boolean displayWindowDecor = config.windowConfiguration.hasWindowDecorCaption() && !isFillingScreen(config);
        if (this.mDecorCaptionView != null || !displayWindowDecor) {
            if (this.mDecorCaptionView != null) {
                this.mDecorCaptionView.onConfigurationChanged(displayWindowDecor);
                enableCaption(displayWindowDecor);
                return;
            }
            return;
        }
        LayoutInflater inflater = this.mWindow.getLayoutInflater();
        this.mDecorCaptionView = createDecorCaptionView(inflater);
        if (this.mDecorCaptionView != null) {
            if (this.mDecorCaptionView.getParent() == null) {
                addView(this.mDecorCaptionView, 0, new ViewGroup.LayoutParams(-1, -1));
            }
            removeView(this.mContentRoot);
            this.mDecorCaptionView.addView(this.mContentRoot, new ViewGroup.MarginLayoutParams(-1, -1));
        }
    }

    void onResourcesLoaded(LayoutInflater inflater, int layoutResource) {
        if (this.mBackdropFrameRenderer != null) {
            loadBackgroundDrawablesIfNeeded();
            this.mBackdropFrameRenderer.onResourcesLoaded(this, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, getCurrentColor(this.mStatusColorViewState), getCurrentColor(this.mNavigationColorViewState));
        }
        this.mDecorCaptionView = createDecorCaptionView(inflater);
        View root = inflater.inflate(layoutResource, (ViewGroup) null);
        if (this.mDecorCaptionView != null) {
            if (this.mDecorCaptionView.getParent() == null) {
                addView(this.mDecorCaptionView, new ViewGroup.LayoutParams(-1, -1));
            }
            this.mDecorCaptionView.addView(root, new ViewGroup.MarginLayoutParams(-1, -1));
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
                Log.m64w(str, "Failed to find background drawable for PhoneWindow=" + this.mWindow);
            }
        }
        if (this.mCaptionBackgroundDrawable == null) {
            this.mCaptionBackgroundDrawable = getContext().getDrawable(C3132R.C3133drawable.decor_caption_title_focused);
        }
        if (this.mResizingBackgroundDrawable != null) {
            this.mLastBackgroundDrawableCb = this.mResizingBackgroundDrawable.getCallback();
            this.mResizingBackgroundDrawable.setCallback(null);
        }
    }

    private DecorCaptionView createDecorCaptionView(LayoutInflater inflater) {
        DecorCaptionView decorCaptionView = null;
        for (int i = getChildCount() - 1; i >= 0 && decorCaptionView == null; i--) {
            View view = getChildAt(i);
            if (view instanceof DecorCaptionView) {
                decorCaptionView = (DecorCaptionView) view;
                removeViewAt(i);
            }
        }
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        boolean isApplication = attrs.type == 1 || attrs.type == 2 || attrs.type == 4;
        WindowConfiguration winConfig = getResources().getConfiguration().windowConfiguration;
        if (!this.mWindow.isFloating() && isApplication && winConfig.hasWindowDecorCaption()) {
            if (decorCaptionView == null) {
                decorCaptionView = inflateDecorCaptionView(inflater);
            }
            decorCaptionView.setPhoneWindow(this.mWindow, true);
        } else {
            decorCaptionView = null;
        }
        enableCaption(decorCaptionView != null);
        return decorCaptionView;
    }

    private DecorCaptionView inflateDecorCaptionView(LayoutInflater inflater) {
        Context context = getContext();
        LayoutInflater inflater2 = LayoutInflater.from(context);
        DecorCaptionView view = (DecorCaptionView) inflater2.inflate(C3132R.layout.decor_caption, (ViewGroup) null);
        setDecorCaptionShade(context, view);
        return view;
    }

    private void setDecorCaptionShade(Context context, DecorCaptionView view) {
        int shade = this.mWindow.getDecorCaptionShade();
        switch (shade) {
            case 1:
                setLightDecorCaptionShade(view);
                return;
            case 2:
                setDarkDecorCaptionShade(view);
                return;
            default:
                TypedValue value = new TypedValue();
                context.getTheme().resolveAttribute(16843827, value, true);
                if (Color.luminance(value.data) < 0.5d) {
                    setLightDecorCaptionShade(view);
                    return;
                } else {
                    setDarkDecorCaptionShade(view);
                    return;
                }
        }
    }

    void updateDecorCaptionShade() {
        if (this.mDecorCaptionView != null) {
            setDecorCaptionShade(getContext(), this.mDecorCaptionView);
        }
    }

    private void setLightDecorCaptionShade(DecorCaptionView view) {
        view.findViewById(C3132R.C3134id.maximize_window).setBackgroundResource(C3132R.C3133drawable.decor_maximize_button_light);
        view.findViewById(C3132R.C3134id.close_window).setBackgroundResource(C3132R.C3133drawable.decor_close_button_light);
    }

    private void setDarkDecorCaptionShade(DecorCaptionView view) {
        view.findViewById(C3132R.C3134id.maximize_window).setBackgroundResource(C3132R.C3133drawable.decor_maximize_button_dark);
        view.findViewById(C3132R.C3134id.close_window).setBackgroundResource(C3132R.C3133drawable.decor_close_button_dark);
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

    void clearContentView() {
        if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.removeContentView();
            return;
        }
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View v = getChildAt(i);
            if (v != this.mStatusColorViewState.view && v != this.mNavigationColorViewState.view && v != this.mStatusGuard) {
                removeViewAt(i);
            }
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowSizeIsChanging(Rect newBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.setTargetRect(newBounds, fullscreen, systemInsets, stableInsets);
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeStart(Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets, int resizeMode) {
        if (this.mWindow.isDestroyed()) {
            releaseThreadedRenderer();
        } else if (this.mBackdropFrameRenderer != null) {
        } else {
            ThreadedRenderer renderer = getThreadedRenderer();
            if (renderer != null) {
                loadBackgroundDrawablesIfNeeded();
                this.mBackdropFrameRenderer = new BackdropFrameRenderer(this, renderer, initialBounds, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, getCurrentColor(this.mStatusColorViewState), getCurrentColor(this.mNavigationColorViewState), fullscreen, systemInsets, stableInsets);
                updateElevation();
                updateColorViews(null, false);
            }
            this.mResizeMode = resizeMode;
            getViewRootImpl().requestInvalidateRootRenderNode();
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeEnd() {
        releaseThreadedRenderer();
        updateColorViews(null, false);
        this.mResizeMode = -1;
        getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override // android.view.WindowCallbacks
    public boolean onContentDrawn(int offsetX, int offsetY, int sizeX, int sizeY) {
        if (this.mBackdropFrameRenderer == null) {
            return false;
        }
        return this.mBackdropFrameRenderer.onContentDrawn(offsetX, offsetY, sizeX, sizeY);
    }

    @Override // android.view.WindowCallbacks
    public void onRequestDraw(boolean reportNextDraw) {
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.onRequestDraw(reportNextDraw);
        } else if (reportNextDraw && isAttachedToWindow()) {
            getViewRootImpl().reportDrawFinish();
        }
    }

    @Override // android.view.WindowCallbacks
    public void onPostDraw(RecordingCanvas canvas) {
        drawResizingShadowIfNeeded(canvas);
        drawLegacyNavigationBarBackground(canvas);
    }

    private void initResizingPaints() {
        int startColor = this.mContext.getResources().getColor(C3132R.color.resize_shadow_start_color, null);
        int endColor = this.mContext.getResources().getColor(C3132R.color.resize_shadow_end_color, null);
        int middleColor = (startColor + endColor) / 2;
        this.mHorizontalResizeShadowPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, this.mResizeShadowSize, new int[]{startColor, middleColor, endColor}, new float[]{0.0f, 0.3f, 1.0f}, Shader.TileMode.CLAMP));
        this.mVerticalResizeShadowPaint.setShader(new LinearGradient(0.0f, 0.0f, this.mResizeShadowSize, 0.0f, new int[]{startColor, middleColor, endColor}, new float[]{0.0f, 0.3f, 1.0f}, Shader.TileMode.CLAMP));
    }

    private void drawResizingShadowIfNeeded(RecordingCanvas canvas) {
        if (this.mResizeMode != 1 || this.mWindow.mIsFloating || this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper()) {
            return;
        }
        canvas.save();
        canvas.translate(0.0f, getHeight() - this.mFrameOffsets.bottom);
        canvas.drawRect(0.0f, 0.0f, getWidth(), this.mResizeShadowSize, this.mHorizontalResizeShadowPaint);
        canvas.restore();
        canvas.save();
        canvas.translate(getWidth() - this.mFrameOffsets.right, 0.0f);
        canvas.drawRect(0.0f, 0.0f, this.mResizeShadowSize, getHeight(), this.mVerticalResizeShadowPaint);
        canvas.restore();
    }

    private void drawLegacyNavigationBarBackground(RecordingCanvas canvas) {
        View v;
        if (!this.mDrawLegacyNavigationBarBackground || (v = this.mNavigationColorViewState.view) == null) {
            return;
        }
        canvas.drawRect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom(), this.mLegacyNavigationBarBackgroundPaint);
    }

    private void releaseThreadedRenderer() {
        if (this.mResizingBackgroundDrawable != null && this.mLastBackgroundDrawableCb != null) {
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
        if (windowingMode == 5 && !isResizing()) {
            float elevation2 = hasWindowFocus() ? 20.0f : 5.0f;
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
        if ((wasAdjustedForStack || this.mElevationAdjustedForStack) && getElevation() != elevation) {
            if (!isResizing()) {
                this.mWindow.setElevation(elevation);
            } else {
                setElevation(elevation);
            }
        }
    }

    boolean isShowingCaption() {
        return this.mDecorCaptionView != null && this.mDecorCaptionView.isCaptionShowing();
    }

    int getCaptionHeight() {
        if (isShowingCaption()) {
            return this.mDecorCaptionView.getCaptionHeight();
        }
        return 0;
    }

    private float dipToPx(float dip) {
        return TypedValue.applyDimension(1, dip, getResources().getDisplayMetrics());
    }

    void setUserCaptionBackgroundDrawable(Drawable drawable) {
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

    void updateLogTag(WindowManager.LayoutParams params) {
        this.mLogTag = "DecorView[" + getTitleSuffix(params) + "]";
    }

    private void updateAvailableWidth() {
        Resources res = getResources();
        this.mAvailableWidth = TypedValue.applyDimension(1, res.getConfiguration().screenWidthDp, res.getDisplayMetrics());
    }

    @Override // android.view.View
    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int deviceId) {
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        Menu menu = st != null ? st.menu : null;
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onProvideKeyboardShortcuts(list, menu, deviceId);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchPointerCaptureChanged(boolean hasCapture) {
        super.dispatchPointerCaptureChanged(hasCapture);
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onPointerCaptureChanged(hasCapture);
        }
    }

    @Override // android.view.View
    public int getAccessibilityViewId() {
        return 2147483646;
    }

    @Override // android.view.View
    public String toString() {
        return "DecorView@" + Integer.toHexString(hashCode()) + "[" + getTitleSuffix(this.mWindow.getAttributes()) + "]";
    }

    /* loaded from: classes4.dex */
    private static class ColorViewState {
        final ColorViewAttributes attributes;
        int color;
        boolean visible;
        View view = null;
        int targetVisibility = 4;
        boolean present = false;

        ColorViewState(ColorViewAttributes attributes) {
            this.attributes = attributes;
        }
    }

    /* loaded from: classes4.dex */
    public static class ColorViewAttributes {
        final int hideWindowFlag;
        final int horizontalGravity;

        /* renamed from: id */
        final int f2488id;
        final int seascapeGravity;
        final int systemUiHideFlag;
        final String transitionName;
        final int translucentFlag;
        final int verticalGravity;

        private ColorViewAttributes(int systemUiHideFlag, int translucentFlag, int verticalGravity, int horizontalGravity, int seascapeGravity, String transitionName, int id, int hideWindowFlag) {
            this.f2488id = id;
            this.systemUiHideFlag = systemUiHideFlag;
            this.translucentFlag = translucentFlag;
            this.verticalGravity = verticalGravity;
            this.horizontalGravity = horizontalGravity;
            this.seascapeGravity = seascapeGravity;
            this.transitionName = transitionName;
            this.hideWindowFlag = hideWindowFlag;
        }

        public boolean isPresent(int sysUiVis, int windowFlags, boolean force) {
            return (this.systemUiHideFlag & sysUiVis) == 0 && (this.hideWindowFlag & windowFlags) == 0 && ((Integer.MIN_VALUE & windowFlags) != 0 || force);
        }

        public boolean isVisible(boolean present, int color, int windowFlags, boolean force) {
            return present && ((-16777216) & color) != 0 && ((this.translucentFlag & windowFlags) == 0 || force);
        }

        public boolean isVisible(int sysUiVis, int color, int windowFlags, boolean force) {
            boolean present = isPresent(sysUiVis, windowFlags, force);
            return isVisible(present, color, windowFlags, force);
        }
    }

    /* loaded from: classes4.dex */
    private class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback wrapped) {
            this.mWrapped = wrapped;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onCreateActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            DecorView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            boolean isPrimary;
            this.mWrapped.onDestroyActionMode(mode);
            boolean isMncApp = DecorView.this.mContext.getApplicationInfo().targetSdkVersion >= 23;
            if (isMncApp) {
                isPrimary = mode == DecorView.this.mPrimaryActionMode;
                isFloating = mode == DecorView.this.mFloatingActionMode;
                if (!isPrimary && mode.getType() == 0) {
                    Log.m70e(DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_PRIMARY; " + mode + " was not the current primary action mode! Expected " + DecorView.this.mPrimaryActionMode);
                }
                if (!isFloating && mode.getType() == 1) {
                    Log.m70e(DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_FLOATING; " + mode + " was not the current floating action mode! Expected " + DecorView.this.mFloatingActionMode);
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
                    DecorView.this.mFadeAnim = ObjectAnimator.ofFloat(DecorView.this.mPrimaryActionModeView, (Property<ActionBarContextView, Float>) View.ALPHA, 1.0f, 0.0f);
                    DecorView.this.mFadeAnim.addListener(new Animator.AnimatorListener() { // from class: com.android.internal.policy.DecorView.ActionModeCallback2Wrapper.1
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            if (lastActionModeView == DecorView.this.mPrimaryActionModeView) {
                                lastActionModeView.setVisibility(8);
                                if (DecorView.this.mPrimaryActionModePopup != null) {
                                    DecorView.this.mPrimaryActionModePopup.dismiss();
                                }
                                lastActionModeView.killMode();
                                DecorView.this.mFadeAnim = null;
                                DecorView.this.requestApplyInsets();
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    DecorView.this.mFadeAnim.start();
                }
                DecorView.this.mPrimaryActionMode = null;
            } else if (isFloating) {
                DecorView.this.cleanupFloatingActionModeViews();
                DecorView.this.mFloatingActionMode = null;
            }
            if (DecorView.this.mWindow.getCallback() != null && !DecorView.this.mWindow.isDestroyed()) {
                try {
                    DecorView.this.mWindow.getCallback().onActionModeFinished(mode);
                } catch (AbstractMethodError e) {
                }
            }
            DecorView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (this.mWrapped instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) this.mWrapped).onGetContentRect(mode, view, outRect);
            } else {
                super.onGetContentRect(mode, view, outRect);
            }
        }
    }
}
