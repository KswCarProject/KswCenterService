package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.IBinder;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.android.internal.R;
import java.lang.ref.WeakReference;
import java.util.List;

public class PopupWindow {
    /* access modifiers changed from: private */
    public static final int[] ABOVE_ANCHOR_STATE_SET = {16842922};
    private static final int ANIMATION_STYLE_DEFAULT = -1;
    private static final int DEFAULT_ANCHORED_GRAVITY = 8388659;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public boolean mAboveAnchor;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private Drawable mAboveAnchorBackgroundDrawable;
    private boolean mAllowScrollingAnchorParent;
    @UnsupportedAppUsage
    private WeakReference<View> mAnchor;
    private WeakReference<View> mAnchorRoot;
    private int mAnchorXoff;
    private int mAnchorYoff;
    private int mAnchoredGravity;
    @UnsupportedAppUsage
    private int mAnimationStyle;
    private boolean mAttachedInDecor;
    private boolean mAttachedInDecorSet;
    private Drawable mBackground;
    @UnsupportedAppUsage
    private View mBackgroundView;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private Drawable mBelowAnchorBackgroundDrawable;
    private boolean mClipToScreen;
    private boolean mClippingEnabled;
    @UnsupportedAppUsage
    private View mContentView;
    @UnsupportedAppUsage
    private Context mContext;
    @UnsupportedAppUsage
    private PopupDecorView mDecorView;
    private float mElevation;
    private Transition mEnterTransition;
    private Rect mEpicenterBounds;
    private Transition mExitTransition;
    private boolean mFocusable;
    private int mGravity;
    private int mHeight;
    @UnsupportedAppUsage
    private int mHeightMode;
    private boolean mIgnoreCheekPress;
    private int mInputMethodMode;
    /* access modifiers changed from: private */
    public boolean mIsAnchorRootAttached;
    @UnsupportedAppUsage
    private boolean mIsDropdown;
    @UnsupportedAppUsage
    private boolean mIsShowing;
    private boolean mIsTransitioningToDismiss;
    @UnsupportedAppUsage
    private int mLastHeight;
    @UnsupportedAppUsage
    private int mLastWidth;
    @UnsupportedAppUsage
    private boolean mLayoutInScreen;
    private boolean mLayoutInsetDecor;
    @UnsupportedAppUsage
    private boolean mNotTouchModal;
    private final View.OnAttachStateChangeListener mOnAnchorDetachedListener;
    private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener;
    @UnsupportedAppUsage
    private OnDismissListener mOnDismissListener;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private final ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private boolean mOutsideTouchable;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private boolean mOverlapAnchor;
    /* access modifiers changed from: private */
    public WeakReference<View> mParentRootView;
    private boolean mPopupViewInitialLayoutDirectionInherited;
    private int mSoftInputMode;
    private int mSplitTouchEnabled;
    private final Rect mTempRect;
    private final int[] mTmpAppLocation;
    private final int[] mTmpDrawingLocation;
    private final int[] mTmpScreenLocation;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public View.OnTouchListener mTouchInterceptor;
    private boolean mTouchable;
    private int mWidth;
    @UnsupportedAppUsage
    private int mWidthMode;
    @UnsupportedAppUsage
    private int mWindowLayoutType;
    @UnsupportedAppUsage
    private WindowManager mWindowManager;

    public interface OnDismissListener {
        void onDismiss();
    }

    public PopupWindow(Context context) {
        this(context, (AttributeSet) null);
    }

    public PopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 16842870);
    }

    public PopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Transition exitTransition;
        this.mTmpDrawingLocation = new int[2];
        this.mTmpScreenLocation = new int[2];
        this.mTmpAppLocation = new int[2];
        this.mTempRect = new Rect();
        this.mInputMethodMode = 0;
        this.mSoftInputMode = 1;
        this.mTouchable = true;
        this.mOutsideTouchable = false;
        this.mClippingEnabled = true;
        this.mSplitTouchEnabled = -1;
        this.mAllowScrollingAnchorParent = true;
        this.mLayoutInsetDecor = false;
        this.mAttachedInDecor = true;
        this.mAttachedInDecorSet = false;
        this.mWidth = -2;
        this.mHeight = -2;
        this.mWindowLayoutType = 1000;
        this.mIgnoreCheekPress = false;
        this.mAnimationStyle = -1;
        this.mGravity = 0;
        this.mOnAnchorDetachedListener = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
                PopupWindow.this.alignToAnchor();
            }

            public void onViewDetachedFromWindow(View v) {
            }
        };
        this.mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
            }

            public void onViewDetachedFromWindow(View v) {
                boolean unused = PopupWindow.this.mIsAnchorRootAttached = false;
            }
        };
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            public final void onScrollChanged() {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PopupWindow, defStyleAttr, defStyleRes);
        Drawable bg = a.getDrawable(0);
        this.mElevation = a.getDimension(3, 0.0f);
        this.mOverlapAnchor = a.getBoolean(2, false);
        if (a.hasValueOrEmpty(1)) {
            int animStyle = a.getResourceId(1, 0);
            if (animStyle == 16974594) {
                this.mAnimationStyle = -1;
            } else {
                this.mAnimationStyle = animStyle;
            }
        } else {
            this.mAnimationStyle = -1;
        }
        Transition enterTransition = getTransition(a.getResourceId(4, 0));
        if (a.hasValueOrEmpty(5)) {
            exitTransition = getTransition(a.getResourceId(5, 0));
        } else {
            exitTransition = enterTransition == null ? null : enterTransition.clone();
        }
        a.recycle();
        setEnterTransition(enterTransition);
        setExitTransition(exitTransition);
        setBackgroundDrawable(bg);
    }

    public PopupWindow() {
        this((View) null, 0, 0);
    }

    public PopupWindow(View contentView) {
        this(contentView, 0, 0);
    }

    public PopupWindow(int width, int height) {
        this((View) null, width, height);
    }

    public PopupWindow(View contentView, int width, int height) {
        this(contentView, width, height, false);
    }

    public PopupWindow(View contentView, int width, int height, boolean focusable) {
        this.mTmpDrawingLocation = new int[2];
        this.mTmpScreenLocation = new int[2];
        this.mTmpAppLocation = new int[2];
        this.mTempRect = new Rect();
        this.mInputMethodMode = 0;
        this.mSoftInputMode = 1;
        this.mTouchable = true;
        this.mOutsideTouchable = false;
        this.mClippingEnabled = true;
        this.mSplitTouchEnabled = -1;
        this.mAllowScrollingAnchorParent = true;
        this.mLayoutInsetDecor = false;
        this.mAttachedInDecor = true;
        this.mAttachedInDecorSet = false;
        this.mWidth = -2;
        this.mHeight = -2;
        this.mWindowLayoutType = 1000;
        this.mIgnoreCheekPress = false;
        this.mAnimationStyle = -1;
        this.mGravity = 0;
        this.mOnAnchorDetachedListener = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
                PopupWindow.this.alignToAnchor();
            }

            public void onViewDetachedFromWindow(View v) {
            }
        };
        this.mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
            }

            public void onViewDetachedFromWindow(View v) {
                boolean unused = PopupWindow.this.mIsAnchorRootAttached = false;
            }
        };
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            public final void onScrollChanged() {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                PopupWindow.this.alignToAnchor();
            }
        };
        if (contentView != null) {
            this.mContext = contentView.getContext();
            this.mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        setContentView(contentView);
        setWidth(width);
        setHeight(height);
        setFocusable(focusable);
    }

    public void setEnterTransition(Transition enterTransition) {
        this.mEnterTransition = enterTransition;
    }

    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    public void setExitTransition(Transition exitTransition) {
        this.mExitTransition = exitTransition;
    }

    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    public Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public void setEpicenterBounds(Rect bounds) {
        this.mEpicenterBounds = bounds != null ? new Rect(bounds) : null;
    }

    private Transition getTransition(int resId) {
        Transition transition;
        if (resId == 0 || resId == 17760256 || (transition = TransitionInflater.from(this.mContext).inflateTransition(resId)) == null) {
            return null;
        }
        if (!((transition instanceof TransitionSet) && ((TransitionSet) transition).getTransitionCount() == 0)) {
            return transition;
        }
        return null;
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public void setBackgroundDrawable(Drawable background) {
        this.mBackground = background;
        if (this.mBackground instanceof StateListDrawable) {
            StateListDrawable stateList = (StateListDrawable) this.mBackground;
            int aboveAnchorStateIndex = stateList.findStateDrawableIndex(ABOVE_ANCHOR_STATE_SET);
            int count = stateList.getStateCount();
            int belowAnchorStateIndex = -1;
            int i = 0;
            while (true) {
                if (i >= count) {
                    break;
                } else if (i != aboveAnchorStateIndex) {
                    belowAnchorStateIndex = i;
                    break;
                } else {
                    i++;
                }
            }
            if (aboveAnchorStateIndex == -1 || belowAnchorStateIndex == -1) {
                this.mBelowAnchorBackgroundDrawable = null;
                this.mAboveAnchorBackgroundDrawable = null;
                return;
            }
            this.mAboveAnchorBackgroundDrawable = stateList.getStateDrawable(aboveAnchorStateIndex);
            this.mBelowAnchorBackgroundDrawable = stateList.getStateDrawable(belowAnchorStateIndex);
        }
    }

    public float getElevation() {
        return this.mElevation;
    }

    public void setElevation(float elevation) {
        this.mElevation = elevation;
    }

    public int getAnimationStyle() {
        return this.mAnimationStyle;
    }

    public void setIgnoreCheekPress() {
        this.mIgnoreCheekPress = true;
    }

    public void setAnimationStyle(int animationStyle) {
        this.mAnimationStyle = animationStyle;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setContentView(View contentView) {
        if (!isShowing()) {
            this.mContentView = contentView;
            if (this.mContext == null && this.mContentView != null) {
                this.mContext = this.mContentView.getContext();
            }
            if (this.mWindowManager == null && this.mContentView != null) {
                this.mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            }
            if (this.mContext != null && !this.mAttachedInDecorSet) {
                setAttachedInDecor(this.mContext.getApplicationInfo().targetSdkVersion >= 22);
            }
        }
    }

    public void setTouchInterceptor(View.OnTouchListener l) {
        this.mTouchInterceptor = l;
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public void setFocusable(boolean focusable) {
        this.mFocusable = focusable;
    }

    public int getInputMethodMode() {
        return this.mInputMethodMode;
    }

    public void setInputMethodMode(int mode) {
        this.mInputMethodMode = mode;
    }

    public void setSoftInputMode(int mode) {
        this.mSoftInputMode = mode;
    }

    public int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    public boolean isTouchable() {
        return this.mTouchable;
    }

    public void setTouchable(boolean touchable) {
        this.mTouchable = touchable;
    }

    public boolean isOutsideTouchable() {
        return this.mOutsideTouchable;
    }

    public void setOutsideTouchable(boolean touchable) {
        this.mOutsideTouchable = touchable;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public void setClippingEnabled(boolean enabled) {
        this.mClippingEnabled = enabled;
    }

    @Deprecated
    public boolean isClipToScreenEnabled() {
        return this.mClipToScreen;
    }

    @Deprecated
    public void setClipToScreenEnabled(boolean enabled) {
        this.mClipToScreen = enabled;
    }

    public boolean isClippedToScreen() {
        return this.mClipToScreen;
    }

    public void setIsClippedToScreen(boolean enabled) {
        this.mClipToScreen = enabled;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setAllowScrollingAnchorParent(boolean enabled) {
        this.mAllowScrollingAnchorParent = enabled;
    }

    /* access modifiers changed from: protected */
    public final boolean getAllowScrollingAnchorParent() {
        return this.mAllowScrollingAnchorParent;
    }

    public boolean isSplitTouchEnabled() {
        if (this.mSplitTouchEnabled >= 0 || this.mContext == null) {
            if (this.mSplitTouchEnabled == 1) {
                return true;
            }
            return false;
        } else if (this.mContext.getApplicationInfo().targetSdkVersion >= 11) {
            return true;
        } else {
            return false;
        }
    }

    public void setSplitTouchEnabled(boolean enabled) {
        this.mSplitTouchEnabled = enabled;
    }

    @Deprecated
    public boolean isLayoutInScreenEnabled() {
        return this.mLayoutInScreen;
    }

    @Deprecated
    public void setLayoutInScreenEnabled(boolean enabled) {
        this.mLayoutInScreen = enabled;
    }

    public boolean isLaidOutInScreen() {
        return this.mLayoutInScreen;
    }

    public void setIsLaidOutInScreen(boolean enabled) {
        this.mLayoutInScreen = enabled;
    }

    public boolean isAttachedInDecor() {
        return this.mAttachedInDecor;
    }

    public void setAttachedInDecor(boolean enabled) {
        this.mAttachedInDecor = enabled;
        this.mAttachedInDecorSet = true;
    }

    @UnsupportedAppUsage
    public void setLayoutInsetDecor(boolean enabled) {
        this.mLayoutInsetDecor = enabled;
    }

    /* access modifiers changed from: protected */
    public final boolean isLayoutInsetDecor() {
        return this.mLayoutInsetDecor;
    }

    public void setWindowLayoutType(int layoutType) {
        this.mWindowLayoutType = layoutType;
    }

    public int getWindowLayoutType() {
        return this.mWindowLayoutType;
    }

    public boolean isTouchModal() {
        return !this.mNotTouchModal;
    }

    public void setTouchModal(boolean touchModal) {
        this.mNotTouchModal = !touchModal;
    }

    @Deprecated
    public void setWindowLayoutMode(int widthSpec, int heightSpec) {
        this.mWidthMode = widthSpec;
        this.mHeightMode = heightSpec;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setOverlapAnchor(boolean overlapAnchor) {
        this.mOverlapAnchor = overlapAnchor;
    }

    public boolean getOverlapAnchor() {
        return this.mOverlapAnchor;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    /* access modifiers changed from: protected */
    public final void setShowing(boolean isShowing) {
        this.mIsShowing = isShowing;
    }

    /* access modifiers changed from: protected */
    public final void setDropDown(boolean isDropDown) {
        this.mIsDropdown = isDropDown;
    }

    /* access modifiers changed from: protected */
    public final void setTransitioningToDismiss(boolean transitioningToDismiss) {
        this.mIsTransitioningToDismiss = transitioningToDismiss;
    }

    /* access modifiers changed from: protected */
    public final boolean isTransitioningToDismiss() {
        return this.mIsTransitioningToDismiss;
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        this.mParentRootView = new WeakReference<>(parent.getRootView());
        showAtLocation(parent.getWindowToken(), gravity, x, y);
    }

    @UnsupportedAppUsage
    public void showAtLocation(IBinder token, int gravity, int x, int y) {
        if (!isShowing() && this.mContentView != null) {
            TransitionManager.endTransitions(this.mDecorView);
            detachFromAnchor();
            this.mIsShowing = true;
            this.mIsDropdown = false;
            this.mGravity = gravity;
            WindowManager.LayoutParams p = createPopupLayoutParams(token);
            preparePopup(p);
            p.x = x;
            p.y = y;
            invokePopup(p);
        }
    }

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        showAsDropDown(anchor, xoff, yoff, DEFAULT_ANCHORED_GRAVITY);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (!isShowing() && hasContentView()) {
            TransitionManager.endTransitions(this.mDecorView);
            attachToAnchor(anchor, xoff, yoff, gravity);
            this.mIsShowing = true;
            this.mIsDropdown = true;
            WindowManager.LayoutParams p = createPopupLayoutParams(anchor.getApplicationWindowToken());
            preparePopup(p);
            updateAboveAnchor(findDropDownPosition(anchor, p, xoff, yoff, p.width, p.height, gravity, this.mAllowScrollingAnchorParent));
            p.accessibilityIdOfAnchor = anchor != null ? (long) anchor.getAccessibilityViewId() : -1;
            invokePopup(p);
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public final void updateAboveAnchor(boolean aboveAnchor) {
        if (aboveAnchor != this.mAboveAnchor) {
            this.mAboveAnchor = aboveAnchor;
            if (this.mBackground != null && this.mBackgroundView != null) {
                if (this.mAboveAnchorBackgroundDrawable == null) {
                    this.mBackgroundView.refreshDrawableState();
                } else if (this.mAboveAnchor) {
                    this.mBackgroundView.setBackground(this.mAboveAnchorBackgroundDrawable);
                } else {
                    this.mBackgroundView.setBackground(this.mBelowAnchorBackgroundDrawable);
                }
            }
        }
    }

    public boolean isAboveAnchor() {
        return this.mAboveAnchor;
    }

    @UnsupportedAppUsage
    private void preparePopup(WindowManager.LayoutParams p) {
        if (this.mContentView == null || this.mContext == null || this.mWindowManager == null) {
            throw new IllegalStateException("You must specify a valid content view by calling setContentView() before attempting to show the popup.");
        }
        if (p.accessibilityTitle == null) {
            p.accessibilityTitle = this.mContext.getString(R.string.popup_window_default_title);
        }
        if (this.mDecorView != null) {
            this.mDecorView.cancelTransitions();
        }
        if (this.mBackground != null) {
            this.mBackgroundView = createBackgroundView(this.mContentView);
            this.mBackgroundView.setBackground(this.mBackground);
        } else {
            this.mBackgroundView = this.mContentView;
        }
        this.mDecorView = createDecorView(this.mBackgroundView);
        boolean z = true;
        this.mDecorView.setIsRootNamespace(true);
        this.mBackgroundView.setElevation(this.mElevation);
        p.setSurfaceInsets(this.mBackgroundView, true, true);
        if (this.mContentView.getRawLayoutDirection() != 2) {
            z = false;
        }
        this.mPopupViewInitialLayoutDirectionInherited = z;
    }

    private PopupBackgroundView createBackgroundView(View contentView) {
        int height;
        ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams == null || layoutParams.height != -2) {
            height = -1;
        } else {
            height = -2;
        }
        PopupBackgroundView backgroundView = new PopupBackgroundView(this.mContext);
        backgroundView.addView(contentView, (ViewGroup.LayoutParams) new FrameLayout.LayoutParams(-1, height));
        return backgroundView;
    }

    private PopupDecorView createDecorView(View contentView) {
        int height;
        ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams == null || layoutParams.height != -2) {
            height = -1;
        } else {
            height = -2;
        }
        PopupDecorView decorView = new PopupDecorView(this.mContext);
        decorView.addView(contentView, -1, height);
        decorView.setClipChildren(false);
        decorView.setClipToPadding(false);
        return decorView;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private void invokePopup(WindowManager.LayoutParams p) {
        if (this.mContext != null) {
            p.packageName = this.mContext.getPackageName();
        }
        PopupDecorView decorView = this.mDecorView;
        decorView.setFitsSystemWindows(this.mLayoutInsetDecor);
        setLayoutDirectionFromAnchor();
        this.mWindowManager.addView(decorView, p);
        if (this.mEnterTransition != null) {
            decorView.requestEnterTransition(this.mEnterTransition);
        }
    }

    private void setLayoutDirectionFromAnchor() {
        View anchor;
        if (this.mAnchor != null && (anchor = (View) this.mAnchor.get()) != null && this.mPopupViewInitialLayoutDirectionInherited) {
            this.mDecorView.setLayoutDirection(anchor.getLayoutDirection());
        }
    }

    private int computeGravity() {
        int gravity = this.mGravity == 0 ? DEFAULT_ANCHORED_GRAVITY : this.mGravity;
        if (!this.mIsDropdown) {
            return gravity;
        }
        if (this.mClipToScreen || this.mClippingEnabled) {
            return gravity | 268435456;
        }
        return gravity;
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public final WindowManager.LayoutParams createPopupLayoutParams(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = computeGravity();
        p.flags = computeFlags(p.flags);
        p.type = this.mWindowLayoutType;
        p.token = token;
        p.softInputMode = this.mSoftInputMode;
        p.windowAnimations = computeAnimationResource();
        if (this.mBackground != null) {
            p.format = this.mBackground.getOpacity();
        } else {
            p.format = -3;
        }
        if (this.mHeightMode < 0) {
            int i = this.mHeightMode;
            this.mLastHeight = i;
            p.height = i;
        } else {
            int i2 = this.mHeight;
            this.mLastHeight = i2;
            p.height = i2;
        }
        if (this.mWidthMode < 0) {
            int i3 = this.mWidthMode;
            this.mLastWidth = i3;
            p.width = i3;
        } else {
            int i4 = this.mWidth;
            this.mLastWidth = i4;
            p.width = i4;
        }
        p.privateFlags = 98304;
        p.setTitle("PopupWindow:" + Integer.toHexString(hashCode()));
        return p;
    }

    private int computeFlags(int curFlags) {
        int curFlags2 = curFlags & -8815129;
        if (this.mIgnoreCheekPress) {
            curFlags2 |= 32768;
        }
        if (!this.mFocusable) {
            curFlags2 |= 8;
            if (this.mInputMethodMode == 1) {
                curFlags2 |= 131072;
            }
        } else if (this.mInputMethodMode == 2) {
            curFlags2 |= 131072;
        }
        if (!this.mTouchable) {
            curFlags2 |= 16;
        }
        if (this.mOutsideTouchable) {
            curFlags2 |= 262144;
        }
        if (!this.mClippingEnabled || this.mClipToScreen) {
            curFlags2 |= 512;
        }
        if (isSplitTouchEnabled()) {
            curFlags2 |= 8388608;
        }
        if (this.mLayoutInScreen) {
            curFlags2 |= 256;
        }
        if (this.mLayoutInsetDecor) {
            curFlags2 |= 65536;
        }
        if (this.mNotTouchModal) {
            curFlags2 |= 32;
        }
        if (this.mAttachedInDecor) {
            return curFlags2 | 1073741824;
        }
        return curFlags2;
    }

    @UnsupportedAppUsage
    private int computeAnimationResource() {
        if (this.mAnimationStyle != -1) {
            return this.mAnimationStyle;
        }
        if (!this.mIsDropdown) {
            return 0;
        }
        if (this.mAboveAnchor) {
            return R.style.Animation_DropDownUp;
        }
        return R.style.Animation_DropDownDown;
    }

    /* JADX WARNING: type inference failed for: r23v1, types: [boolean] */
    /* JADX WARNING: type inference failed for: r23v7 */
    /* JADX WARNING: type inference failed for: r23v8 */
    /* access modifiers changed from: protected */
    public boolean findDropDownPosition(View anchor, WindowManager.LayoutParams outParams, int xOffset, int yOffset, int width, int height, int gravity, boolean allowScroll) {
        int yOffset2;
        int height2;
        ? r23;
        WindowManager.LayoutParams layoutParams;
        char c;
        WindowManager.LayoutParams layoutParams2;
        int hgrav;
        View view = anchor;
        WindowManager.LayoutParams layoutParams3 = outParams;
        int anchorHeight = anchor.getHeight();
        int anchorWidth = anchor.getWidth();
        if (this.mOverlapAnchor) {
            yOffset2 = yOffset - anchorHeight;
        } else {
            yOffset2 = yOffset;
        }
        int[] appScreenLocation = this.mTmpAppLocation;
        View appRootView = getAppRootView(anchor);
        appRootView.getLocationOnScreen(appScreenLocation);
        int[] screenLocation = this.mTmpScreenLocation;
        view.getLocationOnScreen(screenLocation);
        int[] drawingLocation = this.mTmpDrawingLocation;
        drawingLocation[0] = screenLocation[0] - appScreenLocation[0];
        drawingLocation[1] = screenLocation[1] - appScreenLocation[1];
        layoutParams3.x = drawingLocation[0] + xOffset;
        layoutParams3.y = drawingLocation[1] + anchorHeight + yOffset2;
        Rect displayFrame = new Rect();
        appRootView.getWindowVisibleDisplayFrame(displayFrame);
        int i = width;
        if (i == -1) {
            i = displayFrame.right - displayFrame.left;
        }
        int width2 = i;
        int i2 = height;
        if (i2 == -1) {
            height2 = displayFrame.bottom - displayFrame.top;
        } else {
            height2 = i2;
        }
        layoutParams3.gravity = computeGravity();
        layoutParams3.width = width2;
        layoutParams3.height = height2;
        int hgrav2 = Gravity.getAbsoluteGravity(gravity, anchor.getLayoutDirection()) & 7;
        int[] appScreenLocation2 = appScreenLocation;
        if (hgrav2 == 5) {
            layoutParams3.x -= width2 - anchorWidth;
        }
        int i3 = drawingLocation[1];
        int hgrav3 = hgrav2;
        WindowManager.LayoutParams layoutParams4 = outParams;
        int height3 = height2;
        int width3 = width2;
        int hgrav4 = hgrav3;
        Rect displayFrame2 = displayFrame;
        int[] drawingLocation2 = drawingLocation;
        int i4 = screenLocation[1];
        View view2 = appRootView;
        int[] screenLocation2 = screenLocation;
        boolean fitsVertical = tryFitVertical(layoutParams4, yOffset2, height3, anchorHeight, i3, i4, displayFrame.top, displayFrame.bottom, false);
        boolean fitsHorizontal = tryFitHorizontal(layoutParams4, xOffset, width3, anchorWidth, drawingLocation2[0], screenLocation2[0], displayFrame2.left, displayFrame2.right, false);
        if (!fitsVertical || !fitsHorizontal) {
            int scrollX = anchor.getScrollX();
            int scrollY = anchor.getScrollY();
            Rect r = new Rect(scrollX, scrollY, scrollX + width3 + xOffset, scrollY + height3 + anchorHeight + yOffset2);
            if (!allowScroll) {
                hgrav = hgrav4;
                layoutParams2 = outParams;
                c = 1;
            } else if (view.requestRectangleOnScreen(r, true)) {
                view.getLocationOnScreen(screenLocation2);
                drawingLocation2[0] = screenLocation2[0] - appScreenLocation2[0];
                drawingLocation2[1] = screenLocation2[1] - appScreenLocation2[1];
                char c2 = 1;
                layoutParams2 = outParams;
                layoutParams2.x = drawingLocation2[0] + xOffset;
                layoutParams2.y = drawingLocation2[1] + anchorHeight + yOffset2;
                hgrav = hgrav4;
                c = c2;
                if (hgrav == 5) {
                    layoutParams2.x -= width3 - anchorWidth;
                    c = c2;
                }
            } else {
                c = 1;
                hgrav = hgrav4;
                layoutParams2 = outParams;
            }
            int i5 = drawingLocation2[c];
            int i6 = screenLocation2[c];
            int i7 = displayFrame2.top;
            int i8 = hgrav;
            WindowManager.LayoutParams layoutParams5 = outParams;
            layoutParams = layoutParams2;
            int i9 = i6;
            Rect rect = r;
            int i10 = scrollY;
            int i11 = scrollX;
            tryFitVertical(layoutParams5, yOffset2, height3, anchorHeight, i5, i9, i7, displayFrame2.bottom, this.mClipToScreen);
            tryFitHorizontal(layoutParams5, xOffset, width3, anchorWidth, drawingLocation2[0], screenLocation2[0], displayFrame2.left, displayFrame2.right, this.mClipToScreen);
            r23 = c;
        } else {
            int i12 = hgrav4;
            layoutParams = outParams;
            r23 = 1;
        }
        if (layoutParams.y < drawingLocation2[r23]) {
            return r23;
        }
        return false;
    }

    private boolean tryFitVertical(WindowManager.LayoutParams outParams, int yOffset, int height, int anchorHeight, int drawingLocationY, int screenLocationY, int displayFrameTop, int displayFrameBottom, boolean allowResize) {
        int yOffset2;
        WindowManager.LayoutParams layoutParams = outParams;
        int i = height;
        int anchorTopInScreen = layoutParams.y + (screenLocationY - drawingLocationY);
        int spaceBelow = displayFrameBottom - anchorTopInScreen;
        if (anchorTopInScreen >= 0 && i <= spaceBelow) {
            return true;
        }
        int spaceAbove = (anchorTopInScreen - anchorHeight) - displayFrameTop;
        if (i <= spaceAbove) {
            if (this.mOverlapAnchor) {
                yOffset2 = yOffset + anchorHeight;
            } else {
                yOffset2 = yOffset;
            }
            layoutParams.y = (drawingLocationY - i) + yOffset2;
            return true;
        }
        int i2 = spaceAbove;
        int i3 = spaceBelow;
        if (positionInDisplayVertical(outParams, height, drawingLocationY, screenLocationY, displayFrameTop, displayFrameBottom, allowResize)) {
            return true;
        }
        return false;
    }

    private boolean positionInDisplayVertical(WindowManager.LayoutParams outParams, int height, int drawingLocationY, int screenLocationY, int displayFrameTop, int displayFrameBottom, boolean canResize) {
        boolean fitsInDisplay = true;
        int winOffsetY = screenLocationY - drawingLocationY;
        outParams.y += winOffsetY;
        outParams.height = height;
        int bottom = outParams.y + height;
        if (bottom > displayFrameBottom) {
            outParams.y -= bottom - displayFrameBottom;
        }
        if (outParams.y < displayFrameTop) {
            outParams.y = displayFrameTop;
            int displayFrameHeight = displayFrameBottom - displayFrameTop;
            if (!canResize || height <= displayFrameHeight) {
                fitsInDisplay = false;
            } else {
                outParams.height = displayFrameHeight;
            }
        }
        outParams.y -= winOffsetY;
        return fitsInDisplay;
    }

    private boolean tryFitHorizontal(WindowManager.LayoutParams outParams, int xOffset, int width, int anchorWidth, int drawingLocationX, int screenLocationX, int displayFrameLeft, int displayFrameRight, boolean allowResize) {
        int anchorLeftInScreen = outParams.x + (screenLocationX - drawingLocationX);
        int spaceRight = displayFrameRight - anchorLeftInScreen;
        if (anchorLeftInScreen < 0) {
            int i = width;
        } else if (width <= spaceRight) {
            return true;
        }
        if (positionInDisplayHorizontal(outParams, width, drawingLocationX, screenLocationX, displayFrameLeft, displayFrameRight, allowResize)) {
            return true;
        }
        return false;
    }

    private boolean positionInDisplayHorizontal(WindowManager.LayoutParams outParams, int width, int drawingLocationX, int screenLocationX, int displayFrameLeft, int displayFrameRight, boolean canResize) {
        boolean fitsInDisplay = true;
        int winOffsetX = screenLocationX - drawingLocationX;
        outParams.x += winOffsetX;
        int right = outParams.x + width;
        if (right > displayFrameRight) {
            outParams.x -= right - displayFrameRight;
        }
        if (outParams.x < displayFrameLeft) {
            outParams.x = displayFrameLeft;
            int displayFrameWidth = displayFrameRight - displayFrameLeft;
            if (!canResize || width <= displayFrameWidth) {
                fitsInDisplay = false;
            } else {
                outParams.width = displayFrameWidth;
            }
        }
        outParams.x -= winOffsetX;
        return fitsInDisplay;
    }

    public int getMaxAvailableHeight(View anchor) {
        return getMaxAvailableHeight(anchor, 0);
    }

    public int getMaxAvailableHeight(View anchor, int yOffset) {
        return getMaxAvailableHeight(anchor, yOffset, false);
    }

    public int getMaxAvailableHeight(View anchor, int yOffset, boolean ignoreBottomDecorations) {
        Rect displayFrame;
        int distanceToBottom;
        Rect visibleDisplayFrame = new Rect();
        getAppRootView(anchor).getWindowVisibleDisplayFrame(visibleDisplayFrame);
        if (ignoreBottomDecorations) {
            displayFrame = new Rect();
            anchor.getWindowDisplayFrame(displayFrame);
            displayFrame.top = visibleDisplayFrame.top;
            displayFrame.right = visibleDisplayFrame.right;
            displayFrame.left = visibleDisplayFrame.left;
        } else {
            displayFrame = visibleDisplayFrame;
        }
        int[] anchorPos = this.mTmpDrawingLocation;
        anchor.getLocationOnScreen(anchorPos);
        int bottomEdge = displayFrame.bottom;
        if (this.mOverlapAnchor) {
            distanceToBottom = (bottomEdge - anchorPos[1]) - yOffset;
        } else {
            distanceToBottom = (bottomEdge - (anchorPos[1] + anchor.getHeight())) - yOffset;
        }
        int returnedHeight = Math.max(distanceToBottom, (anchorPos[1] - displayFrame.top) + yOffset);
        if (this.mBackground == null) {
            return returnedHeight;
        }
        this.mBackground.getPadding(this.mTempRect);
        return returnedHeight - (this.mTempRect.top + this.mTempRect.bottom);
    }

    public void dismiss() {
        final ViewGroup contentHolder;
        if (isShowing() && !isTransitioningToDismiss()) {
            final PopupDecorView decorView = this.mDecorView;
            final View contentView = this.mContentView;
            ViewParent contentParent = contentView.getParent();
            View anchorRoot = null;
            if (contentParent instanceof ViewGroup) {
                contentHolder = (ViewGroup) contentParent;
            } else {
                contentHolder = null;
            }
            decorView.cancelTransitions();
            this.mIsShowing = false;
            this.mIsTransitioningToDismiss = true;
            Transition exitTransition = this.mExitTransition;
            if (exitTransition == null || !decorView.isLaidOut() || (!this.mIsAnchorRootAttached && this.mAnchorRoot != null)) {
                dismissImmediate(decorView, contentHolder, contentView);
            } else {
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) decorView.getLayoutParams();
                p.flags |= 16;
                p.flags |= 8;
                p.flags &= -131073;
                this.mWindowManager.updateViewLayout(decorView, p);
                if (this.mAnchorRoot != null) {
                    anchorRoot = (View) this.mAnchorRoot.get();
                }
                decorView.startExitTransition(exitTransition, anchorRoot, getTransitionEpicenter(), new TransitionListenerAdapter() {
                    public void onTransitionEnd(Transition transition) {
                        PopupWindow.this.dismissImmediate(decorView, contentHolder, contentView);
                    }
                });
            }
            detachFromAnchor();
            if (this.mOnDismissListener != null) {
                this.mOnDismissListener.onDismiss();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Rect getTransitionEpicenter() {
        View anchor = this.mAnchor != null ? (View) this.mAnchor.get() : null;
        View decor = this.mDecorView;
        if (anchor == null || decor == null) {
            return null;
        }
        int[] anchorLocation = anchor.getLocationOnScreen();
        int[] popupLocation = this.mDecorView.getLocationOnScreen();
        Rect bounds = new Rect(0, 0, anchor.getWidth(), anchor.getHeight());
        bounds.offset(anchorLocation[0] - popupLocation[0], anchorLocation[1] - popupLocation[1]);
        if (this.mEpicenterBounds != null) {
            int offsetX = bounds.left;
            int offsetY = bounds.top;
            bounds.set(this.mEpicenterBounds);
            bounds.offset(offsetX, offsetY);
        }
        return bounds;
    }

    /* access modifiers changed from: private */
    public void dismissImmediate(View decorView, ViewGroup contentHolder, View contentView) {
        if (decorView.getParent() != null) {
            this.mWindowManager.removeViewImmediate(decorView);
        }
        if (contentHolder != null) {
            contentHolder.removeView(contentView);
        }
        this.mDecorView = null;
        this.mBackgroundView = null;
        this.mIsTransitioningToDismiss = false;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    /* access modifiers changed from: protected */
    public final OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public void update() {
        if (isShowing() && hasContentView()) {
            WindowManager.LayoutParams p = getDecorViewLayoutParams();
            boolean update = false;
            int newAnim = computeAnimationResource();
            if (newAnim != p.windowAnimations) {
                p.windowAnimations = newAnim;
                update = true;
            }
            int newFlags = computeFlags(p.flags);
            if (newFlags != p.flags) {
                p.flags = newFlags;
                update = true;
            }
            int newGravity = computeGravity();
            if (newGravity != p.gravity) {
                p.gravity = newGravity;
                update = true;
            }
            if (update) {
                update(this.mAnchor != null ? (View) this.mAnchor.get() : null, p);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void update(View anchor, WindowManager.LayoutParams params) {
        setLayoutDirectionFromAnchor();
        this.mWindowManager.updateViewLayout(this.mDecorView, params);
    }

    public void update(int width, int height) {
        WindowManager.LayoutParams p = getDecorViewLayoutParams();
        update(p.x, p.y, width, height, false);
    }

    public void update(int x, int y, int width, int height) {
        update(x, y, width, height, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: android.view.View} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(int r17, int r18, int r19, int r20, boolean r21) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            if (r3 < 0) goto L_0x0011
            r0.mLastWidth = r3
            r0.setWidth(r3)
        L_0x0011:
            if (r4 < 0) goto L_0x0018
            r0.mLastHeight = r4
            r0.setHeight(r4)
        L_0x0018:
            boolean r5 = r16.isShowing()
            if (r5 == 0) goto L_0x00b2
            boolean r5 = r16.hasContentView()
            if (r5 != 0) goto L_0x0026
            goto L_0x00b2
        L_0x0026:
            android.view.WindowManager$LayoutParams r5 = r16.getDecorViewLayoutParams()
            r6 = r21
            int r7 = r0.mWidthMode
            if (r7 >= 0) goto L_0x0033
            int r7 = r0.mWidthMode
            goto L_0x0035
        L_0x0033:
            int r7 = r0.mLastWidth
        L_0x0035:
            r8 = -1
            if (r3 == r8) goto L_0x0041
            int r9 = r5.width
            if (r9 == r7) goto L_0x0041
            r0.mLastWidth = r7
            r5.width = r7
            r6 = 1
        L_0x0041:
            int r9 = r0.mHeightMode
            if (r9 >= 0) goto L_0x0048
            int r9 = r0.mHeightMode
            goto L_0x004a
        L_0x0048:
            int r9 = r0.mLastHeight
        L_0x004a:
            if (r4 == r8) goto L_0x0055
            int r8 = r5.height
            if (r8 == r9) goto L_0x0055
            r0.mLastHeight = r9
            r5.height = r9
            r6 = 1
        L_0x0055:
            int r8 = r5.x
            if (r8 == r1) goto L_0x005c
            r5.x = r1
            r6 = 1
        L_0x005c:
            int r8 = r5.y
            if (r8 == r2) goto L_0x0063
            r5.y = r2
            r6 = 1
        L_0x0063:
            int r8 = r16.computeAnimationResource()
            int r10 = r5.windowAnimations
            if (r8 == r10) goto L_0x006e
            r5.windowAnimations = r8
            r6 = 1
        L_0x006e:
            int r10 = r5.flags
            int r10 = r0.computeFlags(r10)
            int r11 = r5.flags
            if (r10 == r11) goto L_0x007b
            r5.flags = r10
            r6 = 1
        L_0x007b:
            int r11 = r16.computeGravity()
            int r12 = r5.gravity
            if (r11 == r12) goto L_0x0086
            r5.gravity = r11
            r6 = 1
        L_0x0086:
            r12 = 0
            r13 = -1
            java.lang.ref.WeakReference<android.view.View> r14 = r0.mAnchor
            if (r14 == 0) goto L_0x00a1
            java.lang.ref.WeakReference<android.view.View> r14 = r0.mAnchor
            java.lang.Object r14 = r14.get()
            if (r14 == 0) goto L_0x00a1
            java.lang.ref.WeakReference<android.view.View> r14 = r0.mAnchor
            java.lang.Object r14 = r14.get()
            r12 = r14
            android.view.View r12 = (android.view.View) r12
            int r13 = r12.getAccessibilityViewId()
        L_0x00a1:
            long r14 = (long) r13
            long r1 = r5.accessibilityIdOfAnchor
            int r1 = (r14 > r1 ? 1 : (r14 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x00ac
            long r1 = (long) r13
            r5.accessibilityIdOfAnchor = r1
            r6 = 1
        L_0x00ac:
            if (r6 == 0) goto L_0x00b1
            r0.update((android.view.View) r12, (android.view.WindowManager.LayoutParams) r5)
        L_0x00b1:
            return
        L_0x00b2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.PopupWindow.update(int, int, int, int, boolean):void");
    }

    /* access modifiers changed from: protected */
    public boolean hasContentView() {
        return this.mContentView != null;
    }

    /* access modifiers changed from: protected */
    public boolean hasDecorView() {
        return this.mDecorView != null;
    }

    /* access modifiers changed from: protected */
    public WindowManager.LayoutParams getDecorViewLayoutParams() {
        return (WindowManager.LayoutParams) this.mDecorView.getLayoutParams();
    }

    public void update(View anchor, int width, int height) {
        update(anchor, false, 0, 0, width, height);
    }

    public void update(View anchor, int xoff, int yoff, int width, int height) {
        update(anchor, true, xoff, yoff, width, height);
    }

    private void update(View anchor, boolean updateLocation, int xoff, int yoff, int width, int height) {
        int width2;
        int height2;
        View view = anchor;
        int i = xoff;
        int i2 = yoff;
        if (isShowing() && hasContentView()) {
            WeakReference<View> oldAnchor = this.mAnchor;
            int gravity = this.mAnchoredGravity;
            boolean needsUpdate = updateLocation && !(this.mAnchorXoff == i && this.mAnchorYoff == i2);
            if (oldAnchor == null || oldAnchor.get() != view || (needsUpdate && !this.mIsDropdown)) {
                attachToAnchor(view, i, i2, gravity);
            } else if (needsUpdate) {
                this.mAnchorXoff = i;
                this.mAnchorYoff = i2;
            }
            WindowManager.LayoutParams p = getDecorViewLayoutParams();
            int oldGravity = p.gravity;
            int oldWidth = p.width;
            int oldHeight = p.height;
            int oldX = p.x;
            int oldY = p.y;
            if (width < 0) {
                width2 = this.mWidth;
            } else {
                width2 = width;
            }
            if (height < 0) {
                height2 = this.mHeight;
            } else {
                height2 = height;
            }
            int oldY2 = oldY;
            int oldX2 = oldX;
            int oldHeight2 = oldHeight;
            int oldWidth2 = oldWidth;
            WeakReference<View> weakReference = oldAnchor;
            int oldGravity2 = oldGravity;
            int oldGravity3 = gravity;
            int i3 = gravity;
            WindowManager.LayoutParams p2 = p;
            updateAboveAnchor(findDropDownPosition(anchor, p, this.mAnchorXoff, this.mAnchorYoff, width2, height2, oldGravity3, this.mAllowScrollingAnchorParent));
            update(p2.x, p2.y, width2 < 0 ? width2 : p2.width, height2 < 0 ? height2 : p2.height, (oldGravity2 == p2.gravity && oldX2 == p2.x && oldY2 == p2.y && oldWidth2 == p2.width && oldHeight2 == p2.height) ? false : true);
        }
    }

    /* access modifiers changed from: protected */
    public void detachFromAnchor() {
        View anchor = getAnchor();
        if (anchor != null) {
            anchor.getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
            anchor.removeOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        }
        View anchorRoot = this.mAnchorRoot != null ? (View) this.mAnchorRoot.get() : null;
        if (anchorRoot != null) {
            anchorRoot.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            anchorRoot.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mAnchor = null;
        this.mAnchorRoot = null;
        this.mIsAnchorRootAttached = false;
    }

    /* access modifiers changed from: protected */
    public void attachToAnchor(View anchor, int xoff, int yoff, int gravity) {
        detachFromAnchor();
        ViewTreeObserver vto = anchor.getViewTreeObserver();
        if (vto != null) {
            vto.addOnScrollChangedListener(this.mOnScrollChangedListener);
        }
        anchor.addOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        View anchorRoot = anchor.getRootView();
        anchorRoot.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
        anchorRoot.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mAnchor = new WeakReference<>(anchor);
        this.mAnchorRoot = new WeakReference<>(anchorRoot);
        this.mIsAnchorRootAttached = anchorRoot.isAttachedToWindow();
        this.mParentRootView = this.mAnchorRoot;
        this.mAnchorXoff = xoff;
        this.mAnchorYoff = yoff;
        this.mAnchoredGravity = gravity;
    }

    /* access modifiers changed from: protected */
    public View getAnchor() {
        if (this.mAnchor != null) {
            return (View) this.mAnchor.get();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void alignToAnchor() {
        View anchor = this.mAnchor != null ? (View) this.mAnchor.get() : null;
        if (anchor != null && anchor.isAttachedToWindow() && hasDecorView()) {
            WindowManager.LayoutParams p = getDecorViewLayoutParams();
            updateAboveAnchor(findDropDownPosition(anchor, p, this.mAnchorXoff, this.mAnchorYoff, p.width, p.height, this.mAnchoredGravity, false));
            update(p.x, p.y, -1, -1, true);
        }
    }

    private View getAppRootView(View anchor) {
        View appWindowView = WindowManagerGlobal.getInstance().getWindowView(anchor.getApplicationWindowToken());
        if (appWindowView != null) {
            return appWindowView;
        }
        return anchor.getRootView();
    }

    private class PopupDecorView extends FrameLayout {
        /* access modifiers changed from: private */
        public Runnable mCleanupAfterExit;
        private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
            }

            public void onViewDetachedFromWindow(View v) {
                v.removeOnAttachStateChangeListener(this);
                if (PopupDecorView.this.isAttachedToWindow()) {
                    TransitionManager.endTransitions(PopupDecorView.this);
                }
            }
        };

        public PopupDecorView(Context context) {
            super(context);
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            KeyEvent.DispatcherState state;
            if (event.getKeyCode() != 4) {
                return super.dispatchKeyEvent(event);
            }
            if (getKeyDispatcherState() == null) {
                return super.dispatchKeyEvent(event);
            }
            if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                KeyEvent.DispatcherState state2 = getKeyDispatcherState();
                if (state2 != null) {
                    state2.startTracking(event, this);
                }
                return true;
            } else if (event.getAction() != 1 || (state = getKeyDispatcherState()) == null || !state.isTracking(event) || event.isCanceled()) {
                return super.dispatchKeyEvent(event);
            } else {
                PopupWindow.this.dismiss();
                return true;
            }
        }

        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (PopupWindow.this.mTouchInterceptor == null || !PopupWindow.this.mTouchInterceptor.onTouch(this, ev)) {
                return super.dispatchTouchEvent(ev);
            }
            return true;
        }

        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (event.getAction() == 0 && (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())) {
                PopupWindow.this.dismiss();
                return true;
            } else if (event.getAction() != 4) {
                return super.onTouchEvent(event);
            } else {
                PopupWindow.this.dismiss();
                return true;
            }
        }

        public void requestEnterTransition(Transition transition) {
            ViewTreeObserver observer = getViewTreeObserver();
            if (observer != null && transition != null) {
                final Transition enterTransition = transition.clone();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        ViewTreeObserver observer = PopupDecorView.this.getViewTreeObserver();
                        if (observer != null) {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                        final Rect epicenter = PopupWindow.this.getTransitionEpicenter();
                        enterTransition.setEpicenterCallback(new Transition.EpicenterCallback() {
                            public Rect onGetEpicenter(Transition transition) {
                                return epicenter;
                            }
                        });
                        PopupDecorView.this.startEnterTransition(enterTransition);
                    }
                });
            }
        }

        /* access modifiers changed from: private */
        public void startEnterTransition(Transition enterTransition) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                enterTransition.addTarget(child);
                child.setTransitionVisibility(4);
            }
            TransitionManager.beginDelayedTransition(this, enterTransition);
            for (int i2 = 0; i2 < count; i2++) {
                getChildAt(i2).setTransitionVisibility(0);
            }
        }

        public void startExitTransition(Transition transition, View anchorRoot, final Rect epicenter, Transition.TransitionListener listener) {
            if (transition != null) {
                if (anchorRoot != null) {
                    anchorRoot.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
                }
                this.mCleanupAfterExit = new Runnable(listener, transition, anchorRoot) {
                    private final /* synthetic */ Transition.TransitionListener f$1;
                    private final /* synthetic */ Transition f$2;
                    private final /* synthetic */ View f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void run() {
                        PopupWindow.PopupDecorView.lambda$startExitTransition$0(PopupWindow.PopupDecorView.this, this.f$1, this.f$2, this.f$3);
                    }
                };
                Transition exitTransition = transition.clone();
                exitTransition.addListener(new TransitionListenerAdapter() {
                    public void onTransitionEnd(Transition t) {
                        t.removeListener(this);
                        if (PopupDecorView.this.mCleanupAfterExit != null) {
                            PopupDecorView.this.mCleanupAfterExit.run();
                        }
                    }
                });
                exitTransition.setEpicenterCallback(new Transition.EpicenterCallback() {
                    public Rect onGetEpicenter(Transition transition) {
                        return epicenter;
                    }
                });
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    exitTransition.addTarget(getChildAt(i));
                }
                TransitionManager.beginDelayedTransition(this, exitTransition);
                for (int i2 = 0; i2 < count; i2++) {
                    getChildAt(i2).setVisibility(4);
                }
            }
        }

        public static /* synthetic */ void lambda$startExitTransition$0(PopupDecorView popupDecorView, Transition.TransitionListener listener, Transition transition, View anchorRoot) {
            listener.onTransitionEnd(transition);
            if (anchorRoot != null) {
                anchorRoot.removeOnAttachStateChangeListener(popupDecorView.mOnAnchorRootDetachedListener);
            }
            popupDecorView.mCleanupAfterExit = null;
        }

        public void cancelTransitions() {
            TransitionManager.endTransitions(this);
            if (this.mCleanupAfterExit != null) {
                this.mCleanupAfterExit.run();
            }
        }

        public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int deviceId) {
            View parentRoot;
            if (PopupWindow.this.mParentRootView != null && (parentRoot = (View) PopupWindow.this.mParentRootView.get()) != null) {
                parentRoot.requestKeyboardShortcuts(list, deviceId);
            }
        }
    }

    private class PopupBackgroundView extends FrameLayout {
        public PopupBackgroundView(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public int[] onCreateDrawableState(int extraSpace) {
            if (!PopupWindow.this.mAboveAnchor) {
                return super.onCreateDrawableState(extraSpace);
            }
            int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            View.mergeDrawableStates(drawableState, PopupWindow.ABOVE_ANCHOR_STATE_SET);
            return drawableState;
        }
    }
}
