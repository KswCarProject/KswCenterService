package com.android.internal.policy;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.health.ServiceHealthStats;
import android.provider.Settings;
import android.text.TextUtils;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputQueue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.IconMenuPresenter;
import com.android.internal.view.menu.ListMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.widget.DecorContentParent;
import com.android.internal.widget.SwipeDismissLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PhoneWindow extends Window implements MenuBuilder.Callback {
    private static final String ACTION_BAR_TAG = "android:ActionBar";
    private static final int CUSTOM_TITLE_COMPATIBLE_FEATURES = 13505;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKGROUND_FADE_DURATION_MS = 300;
    static final int FLAG_RESOURCE_SET_ICON = 1;
    static final int FLAG_RESOURCE_SET_ICON_FALLBACK = 4;
    static final int FLAG_RESOURCE_SET_LOGO = 2;
    private static final String FOCUSED_ID_TAG = "android:focusedViewId";
    private static final String PANELS_TAG = "android:Panels";
    private static final String TAG = "PhoneWindow";
    private static final Transition USE_DEFAULT_TRANSITION = new TransitionSet();
    private static final String VIEWS_TAG = "android:views";
    static final RotationWatcher sRotationWatcher = new RotationWatcher();
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    private ViewRootImpl.ActivityConfigCallback mActivityConfigCallback;
    private Boolean mAllowEnterTransitionOverlap;
    private Boolean mAllowReturnTransitionOverlap;
    private boolean mAlwaysReadCloseOnTouchAttr;
    private AudioManager mAudioManager;
    Drawable mBackgroundDrawable;
    private long mBackgroundFadeDurationMillis;
    Drawable mBackgroundFallbackDrawable;
    private ProgressBar mCircularProgressBar;
    private boolean mClipToOutline;
    private boolean mClosingActionMenu;
    ViewGroup mContentParent;
    private boolean mContentParentExplicitlySet;
    private Scene mContentScene;
    ContextMenuBuilder mContextMenu;
    final PhoneWindowMenuCallback mContextMenuCallback;
    MenuHelper mContextMenuHelper;
    private DecorView mDecor;
    private int mDecorCaptionShade;
    DecorContentParent mDecorContentParent;
    private DrawableFeatureState[] mDrawables;
    private float mElevation;
    boolean mEnsureNavigationBarContrastWhenTransparent;
    boolean mEnsureStatusBarContrastWhenTransparent;
    private Transition mEnterTransition;
    private Transition mExitTransition;
    TypedValue mFixedHeightMajor;
    TypedValue mFixedHeightMinor;
    TypedValue mFixedWidthMajor;
    TypedValue mFixedWidthMinor;
    private boolean mForceDecorInstall;
    private boolean mForcedNavigationBarColor;
    private boolean mForcedStatusBarColor;
    private int mFrameResource;
    private ProgressBar mHorizontalProgressBar;
    int mIconRes;
    /* access modifiers changed from: private */
    public int mInvalidatePanelMenuFeatures;
    /* access modifiers changed from: private */
    public boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable;
    boolean mIsFloating;
    private boolean mIsStartingWindow;
    private boolean mIsTranslucent;
    private KeyguardManager mKeyguardManager;
    private LayoutInflater mLayoutInflater;
    private ImageView mLeftIconView;
    private boolean mLoadElevation;
    int mLogoRes;
    private MediaController mMediaController;
    private MediaSessionManager mMediaSessionManager;
    final TypedValue mMinWidthMajor;
    final TypedValue mMinWidthMinor;
    int mNavigationBarColor;
    int mNavigationBarDividerColor;
    int mPanelChordingKey;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    PanelFeatureState mPreparedPanel;
    private Transition mReenterTransition;
    int mResourcesSetFlags;
    private Transition mReturnTransition;
    private ImageView mRightIconView;
    private Transition mSharedElementEnterTransition;
    private Transition mSharedElementExitTransition;
    private Transition mSharedElementReenterTransition;
    private Transition mSharedElementReturnTransition;
    private Boolean mSharedElementsUseOverlay;
    int mStatusBarColor;
    private boolean mSupportsPictureInPicture;
    InputQueue.Callback mTakeInputQueueCallback;
    SurfaceHolder.Callback2 mTakeSurfaceCallback;
    private int mTextColor;
    private int mTheme;
    @UnsupportedAppUsage
    private CharSequence mTitle;
    private int mTitleColor;
    private TextView mTitleView;
    private TransitionManager mTransitionManager;
    private int mUiOptions;
    private boolean mUseDecorContext;
    private int mVolumeControlStreamType;

    static class WindowManagerHolder {
        static final IWindowManager sWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));

        WindowManagerHolder() {
        }
    }

    @UnsupportedAppUsage
    public PhoneWindow(Context context) {
        super(context);
        this.mContextMenuCallback = new PhoneWindowMenuCallback(this);
        this.mMinWidthMajor = new TypedValue();
        this.mMinWidthMinor = new TypedValue();
        this.mForceDecorInstall = false;
        this.mContentParentExplicitlySet = false;
        this.mBackgroundDrawable = null;
        this.mBackgroundFallbackDrawable = null;
        this.mLoadElevation = true;
        this.mFrameResource = 0;
        this.mTextColor = 0;
        this.mStatusBarColor = 0;
        this.mNavigationBarColor = 0;
        this.mNavigationBarDividerColor = 0;
        this.mForcedStatusBarColor = false;
        this.mForcedNavigationBarColor = false;
        this.mTitle = null;
        this.mTitleColor = 0;
        this.mAlwaysReadCloseOnTouchAttr = false;
        this.mVolumeControlStreamType = Integer.MIN_VALUE;
        this.mUiOptions = 0;
        this.mInvalidatePanelMenuRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i <= 13; i++) {
                    if ((PhoneWindow.this.mInvalidatePanelMenuFeatures & (1 << i)) != 0) {
                        PhoneWindow.this.doInvalidatePanelMenu(i);
                    }
                }
                boolean unused = PhoneWindow.this.mInvalidatePanelMenuPosted = false;
                int unused2 = PhoneWindow.this.mInvalidatePanelMenuFeatures = 0;
            }
        };
        this.mEnterTransition = null;
        this.mReturnTransition = USE_DEFAULT_TRANSITION;
        this.mExitTransition = null;
        this.mReenterTransition = USE_DEFAULT_TRANSITION;
        this.mSharedElementEnterTransition = null;
        this.mSharedElementReturnTransition = USE_DEFAULT_TRANSITION;
        this.mSharedElementExitTransition = null;
        this.mSharedElementReenterTransition = USE_DEFAULT_TRANSITION;
        this.mBackgroundFadeDurationMillis = -1;
        this.mTheme = -1;
        this.mDecorCaptionShade = 0;
        this.mUseDecorContext = false;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public PhoneWindow(Context context, Window preservedWindow, ViewRootImpl.ActivityConfigCallback activityConfigCallback) {
        this(context);
        boolean z = true;
        this.mUseDecorContext = true;
        if (preservedWindow != null) {
            this.mDecor = (DecorView) preservedWindow.getDecorView();
            this.mElevation = preservedWindow.getElevation();
            this.mLoadElevation = false;
            this.mForceDecorInstall = true;
            getAttributes().token = preservedWindow.getAttributes().token;
        }
        if (!(Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES, 0) != 0) && !context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            z = false;
        }
        this.mSupportsPictureInPicture = z;
        this.mActivityConfigCallback = activityConfigCallback;
    }

    public final void setContainer(Window container) {
        super.setContainer(container);
    }

    public boolean requestFeature(int featureId) {
        if (!this.mContentParentExplicitlySet) {
            int features = getFeatures();
            int newFeatures = (1 << featureId) | features;
            if ((newFeatures & 128) != 0 && (newFeatures & -13506) != 0) {
                throw new AndroidRuntimeException("You cannot combine custom titles with other title features");
            } else if ((features & 2) != 0 && featureId == 8) {
                return false;
            } else {
                if ((features & 256) != 0 && featureId == 1) {
                    removeFeature(8);
                }
                if ((features & 256) != 0 && featureId == 11) {
                    throw new AndroidRuntimeException("You cannot combine swipe dismissal and the action bar.");
                } else if ((features & 2048) != 0 && featureId == 8) {
                    throw new AndroidRuntimeException("You cannot combine swipe dismissal and the action bar.");
                } else if (featureId != 5 || !getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
                    return super.requestFeature(featureId);
                } else {
                    throw new AndroidRuntimeException("You cannot use indeterminate progress on a watch.");
                }
            }
        } else {
            throw new AndroidRuntimeException("requestFeature() must be called before adding content");
        }
    }

    public void setUiOptions(int uiOptions) {
        this.mUiOptions = uiOptions;
    }

    public void setUiOptions(int uiOptions, int mask) {
        this.mUiOptions = (this.mUiOptions & (~mask)) | (uiOptions & mask);
    }

    public TransitionManager getTransitionManager() {
        return this.mTransitionManager;
    }

    public void setTransitionManager(TransitionManager tm) {
        this.mTransitionManager = tm;
    }

    public Scene getContentScene() {
        return this.mContentScene;
    }

    public void setContentView(int layoutResID) {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            transitionTo(Scene.getSceneForLayout(this.mContentParent, layoutResID, getContext()));
        } else {
            this.mLayoutInflater.inflate(layoutResID, this.mContentParent);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
        this.mContentParentExplicitlySet = true;
    }

    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            view.setLayoutParams(params);
            transitionTo(new Scene(this.mContentParent, view));
        } else {
            this.mContentParent.addView(view, params);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
        this.mContentParentExplicitlySet = true;
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (this.mContentParent == null) {
            installDecor();
        }
        if (hasFeature(12)) {
            Log.v(TAG, "addContentView does not support content transitions");
        }
        this.mContentParent.addView(view, params);
        this.mContentParent.requestApplyInsets();
        Window.Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
    }

    public void clearContentView() {
        if (this.mDecor != null) {
            this.mDecor.clearContentView();
        }
    }

    private void transitionTo(Scene scene) {
        if (this.mContentScene == null) {
            scene.enter();
        } else {
            this.mTransitionManager.transitionTo(scene);
        }
        this.mContentScene = scene;
    }

    public View getCurrentFocus() {
        if (this.mDecor != null) {
            return this.mDecor.findFocus();
        }
        return null;
    }

    public void takeSurface(SurfaceHolder.Callback2 callback) {
        this.mTakeSurfaceCallback = callback;
    }

    public void takeInputQueue(InputQueue.Callback callback) {
        this.mTakeInputQueueCallback = callback;
    }

    public boolean isFloating() {
        return this.mIsFloating;
    }

    public boolean isTranslucent() {
        return this.mIsTranslucent;
    }

    /* access modifiers changed from: package-private */
    public boolean isShowingWallpaper() {
        return (getAttributes().flags & 1048576) != 0;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    public void setTitle(CharSequence title) {
        setTitle(title, true);
    }

    public void setTitle(CharSequence title, boolean updateAccessibilityTitle) {
        ViewRootImpl vr;
        if (this.mTitleView != null) {
            this.mTitleView.setText(title);
        } else if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(title);
        }
        this.mTitle = title;
        if (updateAccessibilityTitle) {
            WindowManager.LayoutParams params = getAttributes();
            if (!TextUtils.equals(title, params.accessibilityTitle)) {
                params.accessibilityTitle = TextUtils.stringOrSpannedString(title);
                if (!(this.mDecor == null || (vr = this.mDecor.getViewRootImpl()) == null)) {
                    vr.onWindowTitleChanged();
                }
                dispatchWindowAttributesChanged(getAttributes());
            }
        }
    }

    @Deprecated
    public void setTitleColor(int textColor) {
        if (this.mTitleView != null) {
            this.mTitleView.setTextColor(textColor);
        }
        this.mTitleColor = textColor;
    }

    public final boolean preparePanel(PanelFeatureState st, KeyEvent event) {
        if (isDestroyed()) {
            return false;
        }
        if (st.isPrepared) {
            return true;
        }
        if (!(this.mPreparedPanel == null || this.mPreparedPanel == st)) {
            closePanel(this.mPreparedPanel, false);
        }
        Window.Callback cb = getCallback();
        if (cb != null) {
            st.createdPanelView = cb.onCreatePanelView(st.featureId);
        }
        boolean isActionBarMenu = st.featureId == 0 || st.featureId == 8;
        if (isActionBarMenu && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if (st.createdPanelView == null) {
            if (st.menu == null || st.refreshMenuContent) {
                if (st.menu == null && (!initializePanelMenu(st) || st.menu == null)) {
                    return false;
                }
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(st.menu, this.mActionMenuPresenterCallback);
                }
                st.menu.stopDispatchingItemsChanged();
                if (cb == null || !cb.onCreatePanelMenu(st.featureId, st.menu)) {
                    st.setMenu((MenuBuilder) null);
                    if (isActionBarMenu && this.mDecorContentParent != null) {
                        this.mDecorContentParent.setMenu((Menu) null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                st.refreshMenuContent = false;
            }
            st.menu.stopDispatchingItemsChanged();
            if (st.frozenActionViewState != null) {
                st.menu.restoreActionViewStates(st.frozenActionViewState);
                st.frozenActionViewState = null;
            }
            if (!cb.onPreparePanel(st.featureId, st.createdPanelView, st.menu)) {
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu((Menu) null, this.mActionMenuPresenterCallback);
                }
                st.menu.startDispatchingItemsChanged();
                return false;
            }
            st.qwertyMode = KeyCharacterMap.load(event != null ? event.getDeviceId() : -1).getKeyboardType() != 1;
            st.menu.setQwertyMode(st.qwertyMode);
            st.menu.startDispatchingItemsChanged();
        }
        st.isPrepared = true;
        st.isHandled = false;
        this.mPreparedPanel = st;
        return true;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        PanelFeatureState st;
        if (this.mDecorContentParent == null && (st = getPanelState(0, false)) != null && st.menu != null) {
            if (st.isOpen) {
                Bundle state = new Bundle();
                if (st.iconMenuPresenter != null) {
                    st.iconMenuPresenter.saveHierarchyState(state);
                }
                if (st.listMenuPresenter != null) {
                    st.listMenuPresenter.saveHierarchyState(state);
                }
                clearMenuViews(st);
                reopenMenu(false);
                if (st.iconMenuPresenter != null) {
                    st.iconMenuPresenter.restoreHierarchyState(state);
                }
                if (st.listMenuPresenter != null) {
                    st.listMenuPresenter.restoreHierarchyState(state);
                    return;
                }
                return;
            }
            clearMenuViews(st);
        }
    }

    public void onMultiWindowModeChanged() {
        if (this.mDecor != null) {
            this.mDecor.onConfigurationChanged(getContext().getResources().getConfiguration());
        }
    }

    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        if (this.mDecor != null) {
            this.mDecor.updatePictureInPictureOutlineProvider(isInPictureInPictureMode);
        }
    }

    public void reportActivityRelaunched() {
        if (this.mDecor != null && this.mDecor.getViewRootImpl() != null) {
            this.mDecor.getViewRootImpl().reportActivityRelaunched();
        }
    }

    private static void clearMenuViews(PanelFeatureState st) {
        st.createdPanelView = null;
        st.refreshDecorView = true;
        st.clearMenuPresenters();
    }

    public final void openPanel(int featureId, KeyEvent event) {
        if (featureId != 0 || this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            openPanel(getPanelState(featureId, true), event);
        } else {
            this.mDecorContentParent.showOverflowMenu();
        }
    }

    private void openPanel(PanelFeatureState st, KeyEvent event) {
        int backgroundResId;
        ViewGroup.LayoutParams lp;
        PanelFeatureState panelFeatureState = st;
        if (!panelFeatureState.isOpen && !isDestroyed()) {
            if (panelFeatureState.featureId == 0) {
                Context context = getContext();
                boolean isXLarge = (context.getResources().getConfiguration().screenLayout & 15) == 4;
                boolean isHoneycombApp = context.getApplicationInfo().targetSdkVersion >= 11;
                if (isXLarge && isHoneycombApp) {
                    return;
                }
            }
            Window.Callback cb = getCallback();
            if (cb == null || cb.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
                WindowManager wm = getWindowManager();
                if (wm != null && preparePanel(st, event)) {
                    int width = -2;
                    if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) {
                        if (panelFeatureState.decorView == null) {
                            if (!initializePanelDecor(st) || panelFeatureState.decorView == null) {
                                return;
                            }
                        } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                            panelFeatureState.decorView.removeAllViews();
                        }
                        if (initializePanelContent(st) && st.hasPanelItems()) {
                            ViewGroup.LayoutParams lp2 = panelFeatureState.shownPanelView.getLayoutParams();
                            if (lp2 == null) {
                                lp2 = new ViewGroup.LayoutParams(-2, -2);
                            }
                            if (lp2.width == -1) {
                                backgroundResId = panelFeatureState.fullBackground;
                                width = -1;
                            } else {
                                backgroundResId = panelFeatureState.background;
                            }
                            panelFeatureState.decorView.setWindowBackground(getContext().getDrawable(backgroundResId));
                            ViewParent shownPanelParent = panelFeatureState.shownPanelView.getParent();
                            if (shownPanelParent != null && (shownPanelParent instanceof ViewGroup)) {
                                ((ViewGroup) shownPanelParent).removeView(panelFeatureState.shownPanelView);
                            }
                            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, lp2);
                            if (!panelFeatureState.shownPanelView.hasFocus()) {
                                panelFeatureState.shownPanelView.requestFocus();
                            }
                        } else {
                            return;
                        }
                    } else if (!st.isInListMode()) {
                        width = -1;
                    } else if (!(panelFeatureState.createdPanelView == null || (lp = panelFeatureState.createdPanelView.getLayoutParams()) == null || lp.width != -1)) {
                        width = -1;
                    }
                    panelFeatureState.isHandled = false;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, -2, panelFeatureState.x, panelFeatureState.y, 1003, 8519680, panelFeatureState.decorView.mDefaultOpacity);
                    if (panelFeatureState.isCompact) {
                        layoutParams.gravity = getOptionsPanelGravity();
                        sRotationWatcher.addWindow(this);
                    } else {
                        layoutParams.gravity = panelFeatureState.gravity;
                    }
                    layoutParams.windowAnimations = panelFeatureState.windowAnimations;
                    wm.addView(panelFeatureState.decorView, layoutParams);
                    panelFeatureState.isOpen = true;
                    return;
                }
                return;
            }
            closePanel(panelFeatureState, true);
        }
    }

    public final void closePanel(int featureId) {
        if (featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.hideOverflowMenu();
        } else if (featureId == 6) {
            closeContextMenu();
        } else {
            closePanel(getPanelState(featureId, true), true);
        }
    }

    public final void closePanel(PanelFeatureState st, boolean doCallback) {
        if (!doCallback || st.featureId != 0 || this.mDecorContentParent == null || !this.mDecorContentParent.isOverflowMenuShowing()) {
            ViewManager wm = getWindowManager();
            if (wm != null && st.isOpen) {
                if (st.decorView != null) {
                    wm.removeView(st.decorView);
                    if (st.isCompact) {
                        sRotationWatcher.removeWindow(this);
                    }
                }
                if (doCallback) {
                    callOnPanelClosed(st.featureId, st, (Menu) null);
                }
            }
            st.isPrepared = false;
            st.isHandled = false;
            st.isOpen = false;
            st.shownPanelView = null;
            if (st.isInExpandedMode) {
                st.refreshDecorView = true;
                st.isInExpandedMode = false;
            }
            if (this.mPreparedPanel == st) {
                this.mPreparedPanel = null;
                this.mPanelChordingKey = 0;
                return;
            }
            return;
        }
        checkCloseActionMenu(st.menu);
    }

    /* access modifiers changed from: package-private */
    public void checkCloseActionMenu(Menu menu) {
        if (!this.mClosingActionMenu) {
            this.mClosingActionMenu = true;
            this.mDecorContentParent.dismissPopups();
            Window.Callback cb = getCallback();
            if (cb != null && !isDestroyed()) {
                cb.onPanelClosed(8, menu);
            }
            this.mClosingActionMenu = false;
        }
    }

    public final void togglePanel(int featureId, KeyEvent event) {
        PanelFeatureState st = getPanelState(featureId, true);
        if (st.isOpen) {
            closePanel(st, true);
        } else {
            openPanel(st, event);
        }
    }

    public void invalidatePanelMenu(int featureId) {
        this.mInvalidatePanelMenuFeatures |= 1 << featureId;
        if (!this.mInvalidatePanelMenuPosted && this.mDecor != null) {
            this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void doPendingInvalidatePanelMenu() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
    }

    /* access modifiers changed from: package-private */
    public void doInvalidatePanelMenu(int featureId) {
        PanelFeatureState st;
        PanelFeatureState st2 = getPanelState(featureId, false);
        if (st2 != null) {
            if (st2.menu != null) {
                Bundle savedActionViewStates = new Bundle();
                st2.menu.saveActionViewStates(savedActionViewStates);
                if (savedActionViewStates.size() > 0) {
                    st2.frozenActionViewState = savedActionViewStates;
                }
                st2.menu.stopDispatchingItemsChanged();
                st2.menu.clear();
            }
            st2.refreshMenuContent = true;
            st2.refreshDecorView = true;
            if ((featureId == 8 || featureId == 0) && this.mDecorContentParent != null && (st = getPanelState(0, false)) != null) {
                st.isPrepared = false;
                preparePanel(st, (KeyEvent) null);
            }
        }
    }

    public final boolean onKeyDownPanel(int featureId, KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (event.getRepeatCount() == 0) {
            this.mPanelChordingKey = keyCode;
            PanelFeatureState st = getPanelState(featureId, false);
            if (st != null && !st.isOpen) {
                return preparePanel(st, event);
            }
        }
        return false;
    }

    public final void onKeyUpPanel(int featureId, KeyEvent event) {
        if (this.mPanelChordingKey != 0) {
            this.mPanelChordingKey = 0;
            PanelFeatureState st = getPanelState(featureId, false);
            if (event.isCanceled()) {
                return;
            }
            if ((this.mDecor == null || this.mDecor.mPrimaryActionMode == null) && st != null) {
                boolean playSoundEffect = false;
                if (featureId != 0 || this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
                    if (st.isOpen || st.isHandled) {
                        playSoundEffect = st.isOpen;
                        closePanel(st, true);
                    } else if (st.isPrepared) {
                        boolean show = true;
                        if (st.refreshMenuContent) {
                            st.isPrepared = false;
                            show = preparePanel(st, event);
                        }
                        if (show) {
                            EventLog.writeEvent((int) ServiceHealthStats.MEASUREMENT_START_SERVICE_COUNT, 0);
                            openPanel(st, event);
                            playSoundEffect = true;
                        }
                    }
                } else if (this.mDecorContentParent.isOverflowMenuShowing()) {
                    playSoundEffect = this.mDecorContentParent.hideOverflowMenu();
                } else if (!isDestroyed() && preparePanel(st, event)) {
                    playSoundEffect = this.mDecorContentParent.showOverflowMenu();
                }
                if (playSoundEffect) {
                    AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
                    if (audioManager != null) {
                        audioManager.playSoundEffect(0);
                    } else {
                        Log.w(TAG, "Couldn't get audio manager");
                    }
                }
            }
        }
    }

    public final void closeAllPanels() {
        if (getWindowManager() != null) {
            PanelFeatureState[] panels = this.mPanels;
            int N = panels != null ? panels.length : 0;
            for (int i = 0; i < N; i++) {
                PanelFeatureState panel = panels[i];
                if (panel != null) {
                    closePanel(panel, true);
                }
            }
            closeContextMenu();
        }
    }

    private synchronized void closeContextMenu() {
        if (this.mContextMenu != null) {
            this.mContextMenu.close();
            dismissContextMenu();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void dismissContextMenu() {
        this.mContextMenu = null;
        if (this.mContextMenuHelper != null) {
            this.mContextMenuHelper.dismiss();
            this.mContextMenuHelper = null;
        }
    }

    public boolean performPanelShortcut(int featureId, int keyCode, KeyEvent event, int flags) {
        return performPanelShortcut(getPanelState(featureId, false), keyCode, event, flags);
    }

    /* access modifiers changed from: package-private */
    public boolean performPanelShortcut(PanelFeatureState st, int keyCode, KeyEvent event, int flags) {
        if (event.isSystem() || st == null) {
            return false;
        }
        boolean handled = false;
        if ((st.isPrepared || preparePanel(st, event)) && st.menu != null) {
            handled = st.menu.performShortcut(keyCode, event, flags);
        }
        if (handled) {
            st.isHandled = true;
            if ((flags & 1) == 0 && this.mDecorContentParent == null) {
                closePanel(st, true);
            }
        }
        return handled;
    }

    public boolean performPanelIdentifierAction(int featureId, int id, int flags) {
        PanelFeatureState st = getPanelState(featureId, true);
        if (!preparePanel(st, new KeyEvent(0, 82)) || st.menu == null) {
            return false;
        }
        boolean res = st.menu.performIdentifierAction(id, flags);
        if (this.mDecorContentParent == null) {
            closePanel(st, true);
        }
        return res;
    }

    public PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panels = this.mPanels;
        int N = panels != null ? panels.length : 0;
        for (int i = 0; i < N; i++) {
            PanelFeatureState panel = panels[i];
            if (panel != null && panel.menu == menu) {
                return panel;
            }
        }
        return null;
    }

    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
        PanelFeatureState panel;
        Window.Callback cb = getCallback();
        if (cb == null || isDestroyed() || (panel = findMenuPanel(menu.getRootMenu())) == null) {
            return false;
        }
        return cb.onMenuItemSelected(panel.featureId, item);
    }

    public void onMenuModeChange(MenuBuilder menu) {
        reopenMenu(true);
    }

    private void reopenMenu(boolean toggleMenuMode) {
        if (this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(getContext()).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState st = getPanelState(0, false);
            if (st != null) {
                boolean newExpandedMode = toggleMenuMode ? !st.isInExpandedMode : st.isInExpandedMode;
                st.refreshDecorView = true;
                closePanel(st, false);
                st.isInExpandedMode = newExpandedMode;
                openPanel(st, (KeyEvent) null);
                return;
            }
            return;
        }
        Window.Callback cb = getCallback();
        if (this.mDecorContentParent.isOverflowMenuShowing() && toggleMenuMode) {
            this.mDecorContentParent.hideOverflowMenu();
            PanelFeatureState st2 = getPanelState(0, false);
            if (st2 != null && cb != null && !isDestroyed()) {
                cb.onPanelClosed(8, st2.menu);
            }
        } else if (cb != null && !isDestroyed()) {
            if (this.mInvalidatePanelMenuPosted && (1 & this.mInvalidatePanelMenuFeatures) != 0) {
                this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
                this.mInvalidatePanelMenuRunnable.run();
            }
            PanelFeatureState st3 = getPanelState(0, false);
            if (st3 != null && st3.menu != null && !st3.refreshMenuContent && cb.onPreparePanel(0, st3.createdPanelView, st3.menu)) {
                cb.onMenuOpened(8, st3.menu);
                this.mDecorContentParent.showOverflowMenu();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean initializePanelMenu(PanelFeatureState st) {
        Context context = getContext();
        if ((st.featureId == 0 || st.featureId == 8) && this.mDecorContentParent != null) {
            TypedValue outValue = new TypedValue();
            Resources.Theme baseTheme = context.getTheme();
            baseTheme.resolveAttribute(16843825, outValue, true);
            Resources.Theme widgetTheme = null;
            if (outValue.resourceId != 0) {
                widgetTheme = context.getResources().newTheme();
                widgetTheme.setTo(baseTheme);
                widgetTheme.applyStyle(outValue.resourceId, true);
                widgetTheme.resolveAttribute(16843671, outValue, true);
            } else {
                baseTheme.resolveAttribute(16843671, outValue, true);
            }
            if (outValue.resourceId != 0) {
                if (widgetTheme == null) {
                    widgetTheme = context.getResources().newTheme();
                    widgetTheme.setTo(baseTheme);
                }
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            if (widgetTheme != null) {
                context = new ContextThemeWrapper(context, 0);
                context.getTheme().setTo(widgetTheme);
            }
        }
        MenuBuilder menu = new MenuBuilder(context);
        menu.setCallback(this);
        st.setMenu(menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean initializePanelDecor(PanelFeatureState st) {
        st.decorView = generateDecor(st.featureId);
        st.gravity = 81;
        st.setStyle(getContext());
        TypedArray a = getContext().obtainStyledAttributes((AttributeSet) null, R.styleable.Window, 0, st.listPresenterTheme);
        float elevation = a.getDimension(38, 0.0f);
        if (elevation != 0.0f) {
            st.decorView.setElevation(elevation);
        }
        a.recycle();
        return true;
    }

    private int getOptionsPanelGravity() {
        try {
            return WindowManagerHolder.sWindowManager.getPreferredOptionsPanelGravity(getContext().getDisplayId());
        } catch (RemoteException ex) {
            Log.e(TAG, "Couldn't getOptionsPanelGravity; using default", ex);
            return 81;
        }
    }

    /* access modifiers changed from: package-private */
    public void onOptionsPanelRotationChanged() {
        PanelFeatureState st = getPanelState(0, false);
        if (st != null) {
            WindowManager.LayoutParams lp = st.decorView != null ? (WindowManager.LayoutParams) st.decorView.getLayoutParams() : null;
            if (lp != null) {
                lp.gravity = getOptionsPanelGravity();
                ViewManager wm = getWindowManager();
                if (wm != null) {
                    wm.updateViewLayout(st.decorView, lp);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean initializePanelContent(PanelFeatureState st) {
        MenuView menuView;
        if (st.createdPanelView != null) {
            st.shownPanelView = st.createdPanelView;
            return true;
        } else if (st.menu == null) {
            return false;
        } else {
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            if (st.isInListMode()) {
                menuView = st.getListMenuView(getContext(), this.mPanelMenuPresenterCallback);
            } else {
                menuView = st.getIconMenuView(getContext(), this.mPanelMenuPresenterCallback);
            }
            st.shownPanelView = (View) menuView;
            if (st.shownPanelView == null) {
                return false;
            }
            int defaultAnimations = menuView.getWindowAnimations();
            if (defaultAnimations != 0) {
                st.windowAnimations = defaultAnimations;
            }
            return true;
        }
    }

    public boolean performContextMenuIdentifierAction(int id, int flags) {
        if (this.mContextMenu != null) {
            return this.mContextMenu.performIdentifierAction(id, flags);
        }
        return false;
    }

    public final void setElevation(float elevation) {
        this.mElevation = elevation;
        WindowManager.LayoutParams attrs = getAttributes();
        if (this.mDecor != null) {
            this.mDecor.setElevation(elevation);
            attrs.setSurfaceInsets(this.mDecor, true, false);
        }
        dispatchWindowAttributesChanged(attrs);
    }

    public float getElevation() {
        return this.mElevation;
    }

    public final void setClipToOutline(boolean clipToOutline) {
        this.mClipToOutline = clipToOutline;
        if (this.mDecor != null) {
            this.mDecor.setClipToOutline(clipToOutline);
        }
    }

    public final void setBackgroundDrawable(Drawable drawable) {
        Drawable drawable2;
        if (drawable != this.mBackgroundDrawable) {
            this.mBackgroundDrawable = drawable;
            if (this.mDecor != null) {
                this.mDecor.setWindowBackground(drawable);
                if (this.mBackgroundFallbackDrawable != null) {
                    DecorView decorView = this.mDecor;
                    if (drawable != null) {
                        drawable2 = null;
                    } else {
                        drawable2 = this.mBackgroundFallbackDrawable;
                    }
                    decorView.setBackgroundFallback(drawable2);
                }
            }
        }
    }

    public final void setFeatureDrawableResource(int featureId, int resId) {
        if (resId != 0) {
            DrawableFeatureState st = getDrawableState(featureId, true);
            if (st.resid != resId) {
                st.resid = resId;
                st.uri = null;
                st.local = getContext().getDrawable(resId);
                updateDrawable(featureId, st, false);
                return;
            }
            return;
        }
        setFeatureDrawable(featureId, (Drawable) null);
    }

    public final void setFeatureDrawableUri(int featureId, Uri uri) {
        if (uri != null) {
            DrawableFeatureState st = getDrawableState(featureId, true);
            if (st.uri == null || !st.uri.equals(uri)) {
                st.resid = 0;
                st.uri = uri;
                st.local = loadImageURI(uri);
                updateDrawable(featureId, st, false);
                return;
            }
            return;
        }
        setFeatureDrawable(featureId, (Drawable) null);
    }

    public final void setFeatureDrawable(int featureId, Drawable drawable) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        st.resid = 0;
        st.uri = null;
        if (st.local != drawable) {
            st.local = drawable;
            updateDrawable(featureId, st, false);
        }
    }

    public void setFeatureDrawableAlpha(int featureId, int alpha) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        if (st.alpha != alpha) {
            st.alpha = alpha;
            updateDrawable(featureId, st, false);
        }
    }

    /* access modifiers changed from: protected */
    public final void setFeatureDefaultDrawable(int featureId, Drawable drawable) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        if (st.def != drawable) {
            st.def = drawable;
            updateDrawable(featureId, st, false);
        }
    }

    public final void setFeatureInt(int featureId, int value) {
        updateInt(featureId, value, false);
    }

    /* access modifiers changed from: protected */
    public final void updateDrawable(int featureId, boolean fromActive) {
        DrawableFeatureState st = getDrawableState(featureId, false);
        if (st != null) {
            updateDrawable(featureId, st, fromActive);
        }
    }

    /* access modifiers changed from: protected */
    public void onDrawableChanged(int featureId, Drawable drawable, int alpha) {
        ImageView view;
        if (featureId == 3) {
            view = getLeftIconView();
        } else if (featureId == 4) {
            view = getRightIconView();
        } else {
            return;
        }
        if (drawable != null) {
            drawable.setAlpha(alpha);
            view.setImageDrawable(drawable);
            view.setVisibility(0);
            return;
        }
        view.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void onIntChanged(int featureId, int value) {
        FrameLayout titleContainer;
        if (featureId == 2 || featureId == 5) {
            updateProgressBars(value);
        } else if (featureId == 7 && (titleContainer = (FrameLayout) findViewById(R.id.title_container)) != null) {
            this.mLayoutInflater.inflate(value, (ViewGroup) titleContainer);
        }
    }

    private void updateProgressBars(int value) {
        ProgressBar circularProgressBar = getCircularProgressBar(true);
        ProgressBar horizontalProgressBar = getHorizontalProgressBar(true);
        int features = getLocalFeatures();
        if (value == -1) {
            if ((features & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility((horizontalProgressBar.isIndeterminate() || horizontalProgressBar.getProgress() < 10000) ? 0 : 4);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((features & 32) == 0) {
                return;
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(0);
            } else {
                Log.e(TAG, "Circular progress bar not located in current window decor");
            }
        } else if (value == -2) {
            if ((features & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility(8);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((features & 32) == 0) {
                return;
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(8);
            } else {
                Log.e(TAG, "Circular progress bar not located in current window decor");
            }
        } else if (value == -3) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(true);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
        } else if (value == -4) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(false);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
        } else if (value >= 0 && value <= 10000) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setProgress(value + 0);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            if (value < 10000) {
                showProgressBars(horizontalProgressBar, circularProgressBar);
            } else {
                hideProgressBars(horizontalProgressBar, circularProgressBar);
            }
        } else if (20000 <= value && value <= 30000) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setSecondaryProgress(value - 20000);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            showProgressBars(horizontalProgressBar, circularProgressBar);
        }
    }

    private void showProgressBars(ProgressBar horizontalProgressBar, ProgressBar spinnyProgressBar) {
        int features = getLocalFeatures();
        if (!((features & 32) == 0 || spinnyProgressBar == null || spinnyProgressBar.getVisibility() != 4)) {
            spinnyProgressBar.setVisibility(0);
        }
        if ((features & 4) != 0 && horizontalProgressBar != null && horizontalProgressBar.getProgress() < 10000) {
            horizontalProgressBar.setVisibility(0);
        }
    }

    private void hideProgressBars(ProgressBar horizontalProgressBar, ProgressBar spinnyProgressBar) {
        int features = getLocalFeatures();
        Animation anim = AnimationUtils.loadAnimation(getContext(), 17432577);
        anim.setDuration(1000);
        if (!((features & 32) == 0 || spinnyProgressBar == null || spinnyProgressBar.getVisibility() != 0)) {
            spinnyProgressBar.startAnimation(anim);
            spinnyProgressBar.setVisibility(4);
        }
        if ((features & 4) != 0 && horizontalProgressBar != null && horizontalProgressBar.getVisibility() == 0) {
            horizontalProgressBar.startAnimation(anim);
            horizontalProgressBar.setVisibility(4);
        }
    }

    public void setIcon(int resId) {
        this.mIconRes = resId;
        this.mResourcesSetFlags |= 1;
        this.mResourcesSetFlags &= -5;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setIcon(resId);
        }
    }

    public void setDefaultIcon(int resId) {
        if ((this.mResourcesSetFlags & 1) == 0) {
            this.mIconRes = resId;
            if (this.mDecorContentParent == null) {
                return;
            }
            if (this.mDecorContentParent.hasIcon() && (this.mResourcesSetFlags & 4) == 0) {
                return;
            }
            if (resId != 0) {
                this.mDecorContentParent.setIcon(resId);
                this.mResourcesSetFlags &= -5;
                return;
            }
            this.mDecorContentParent.setIcon(getContext().getPackageManager().getDefaultActivityIcon());
            this.mResourcesSetFlags |= 4;
        }
    }

    public void setLogo(int resId) {
        this.mLogoRes = resId;
        this.mResourcesSetFlags |= 2;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setLogo(resId);
        }
    }

    public void setDefaultLogo(int resId) {
        if ((this.mResourcesSetFlags & 2) == 0) {
            this.mLogoRes = resId;
            if (this.mDecorContentParent != null && !this.mDecorContentParent.hasLogo()) {
                this.mDecorContentParent.setLogo(resId);
            }
        }
    }

    public void setLocalFocus(boolean hasFocus, boolean inTouchMode) {
        getViewRootImpl().windowFocusChanged(hasFocus, inTouchMode);
    }

    public void injectInputEvent(InputEvent event) {
        getViewRootImpl().dispatchInputEvent(event);
    }

    private ViewRootImpl getViewRootImpl() {
        ViewRootImpl viewRootImpl;
        if (this.mDecor != null && (viewRootImpl = this.mDecor.getViewRootImpl()) != null) {
            return viewRootImpl;
        }
        throw new IllegalStateException("view not added");
    }

    public void takeKeyEvents(boolean get) {
        this.mDecor.setFocusable(get);
    }

    public boolean superDispatchKeyEvent(KeyEvent event) {
        return this.mDecor.superDispatchKeyEvent(event);
    }

    public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return this.mDecor.superDispatchKeyShortcutEvent(event);
    }

    public boolean superDispatchTouchEvent(MotionEvent event) {
        return this.mDecor.superDispatchTouchEvent(event);
    }

    public boolean superDispatchTrackballEvent(MotionEvent event) {
        return this.mDecor.superDispatchTrackballEvent(event);
    }

    public boolean superDispatchGenericMotionEvent(MotionEvent event) {
        return this.mDecor.superDispatchGenericMotionEvent(event);
    }

    /* access modifiers changed from: protected */
    public boolean onKeyDown(int featureId, int keyCode, KeyEvent event) {
        KeyEvent.DispatcherState dispatcher = this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null;
        int i = 0;
        if (keyCode != 4) {
            if (keyCode != 79) {
                if (keyCode == 82) {
                    if (featureId >= 0) {
                        i = featureId;
                    }
                    onKeyDownPanel(i, event);
                    return true;
                } else if (keyCode != 130) {
                    if (keyCode != 164) {
                        switch (keyCode) {
                            case 24:
                            case 25:
                                break;
                            default:
                                switch (keyCode) {
                                    case 85:
                                    case 86:
                                    case 87:
                                    case 88:
                                    case 89:
                                    case 90:
                                    case 91:
                                        break;
                                    default:
                                        switch (keyCode) {
                                            case 126:
                                            case 127:
                                                break;
                                        }
                                }
                        }
                    }
                    if (this.mMediaController != null) {
                        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(this.mMediaController.getSessionToken(), event);
                    } else {
                        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(event, this.mVolumeControlStreamType);
                    }
                    return true;
                }
            }
            return this.mMediaController != null && getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), event);
        } else if (event.getRepeatCount() <= 0 && featureId >= 0) {
            if (dispatcher != null) {
                dispatcher.startTracking(event, this);
            }
            return true;
        }
        return false;
    }

    private KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
        }
        return this.mKeyguardManager;
    }

    /* access modifiers changed from: package-private */
    public AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        }
        return this.mAudioManager;
    }

    private MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager) getContext().getSystemService(Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    /* access modifiers changed from: protected */
    public boolean onKeyUp(int featureId, int keyCode, KeyEvent event) {
        PanelFeatureState st;
        KeyEvent.DispatcherState dispatcher = this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null;
        if (dispatcher != null) {
            dispatcher.handleUpEvent(event);
        }
        int i = 0;
        if (keyCode != 4) {
            if (keyCode != 79) {
                if (keyCode == 82) {
                    if (featureId >= 0) {
                        i = featureId;
                    }
                    onKeyUpPanel(i, event);
                    return true;
                } else if (keyCode != 130) {
                    if (keyCode == 164) {
                        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(event, Integer.MIN_VALUE);
                        return true;
                    } else if (keyCode != 171) {
                        switch (keyCode) {
                            case 24:
                            case 25:
                                if (this.mMediaController != null) {
                                    getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(this.mMediaController.getSessionToken(), event);
                                } else {
                                    getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(event, this.mVolumeControlStreamType);
                                }
                                return true;
                            default:
                                switch (keyCode) {
                                    case 84:
                                        if (!isNotInstantAppAndKeyguardRestricted() && (getContext().getResources().getConfiguration().uiMode & 15) != 6) {
                                            if (event.isTracking() && !event.isCanceled()) {
                                                launchDefaultSearch(event);
                                            }
                                            return true;
                                        }
                                    case 85:
                                    case 86:
                                    case 87:
                                    case 88:
                                    case 89:
                                    case 90:
                                    case 91:
                                        break;
                                    default:
                                        switch (keyCode) {
                                            case 126:
                                            case 127:
                                                break;
                                        }
                                }
                                break;
                        }
                    } else {
                        if (this.mSupportsPictureInPicture && !event.isCanceled()) {
                            getWindowControllerCallback().enterPictureInPictureModeIfPossible();
                        }
                        return true;
                    }
                }
            }
            return this.mMediaController != null && getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), event);
        } else if (featureId >= 0 && event.isTracking() && !event.isCanceled()) {
            if (featureId != 0 || (st = getPanelState(featureId, false)) == null || !st.isInExpandedMode) {
                closePanel(featureId);
                return true;
            }
            reopenMenu(true);
            return true;
        }
        return false;
    }

    private boolean isNotInstantAppAndKeyguardRestricted() {
        return !getContext().getPackageManager().isInstantApp() && getKeyguardManager().inKeyguardRestrictedInputMode();
    }

    /* access modifiers changed from: protected */
    public void onActive() {
    }

    public final View getDecorView() {
        if (this.mDecor == null || this.mForceDecorInstall) {
            installDecor();
        }
        return this.mDecor;
    }

    public final View peekDecorView() {
        return this.mDecor;
    }

    /* access modifiers changed from: package-private */
    public void onViewRootImplSet(ViewRootImpl viewRoot) {
        viewRoot.setActivityConfigCallback(this.mActivityConfigCallback);
    }

    public Bundle saveHierarchyState() {
        Bundle outState = new Bundle();
        if (this.mContentParent == null) {
            return outState;
        }
        SparseArray<Parcelable> states = new SparseArray<>();
        this.mContentParent.saveHierarchyState(states);
        outState.putSparseParcelableArray(VIEWS_TAG, states);
        View focusedView = this.mContentParent.findFocus();
        if (!(focusedView == null || focusedView.getId() == -1)) {
            outState.putInt(FOCUSED_ID_TAG, focusedView.getId());
        }
        SparseArray<Parcelable> panelStates = new SparseArray<>();
        savePanelState(panelStates);
        if (panelStates.size() > 0) {
            outState.putSparseParcelableArray(PANELS_TAG, panelStates);
        }
        if (this.mDecorContentParent != null) {
            SparseArray<Parcelable> actionBarStates = new SparseArray<>();
            this.mDecorContentParent.saveToolbarHierarchyState(actionBarStates);
            outState.putSparseParcelableArray(ACTION_BAR_TAG, actionBarStates);
        }
        return outState;
    }

    public void restoreHierarchyState(Bundle savedInstanceState) {
        if (this.mContentParent != null) {
            SparseArray<Parcelable> savedStates = savedInstanceState.getSparseParcelableArray(VIEWS_TAG);
            if (savedStates != null) {
                this.mContentParent.restoreHierarchyState(savedStates);
            }
            int focusedViewId = savedInstanceState.getInt(FOCUSED_ID_TAG, -1);
            if (focusedViewId != -1) {
                View needsFocus = this.mContentParent.findViewById(focusedViewId);
                if (needsFocus != null) {
                    needsFocus.requestFocus();
                } else {
                    Log.w(TAG, "Previously focused view reported id " + focusedViewId + " during save, but can't be found during restore.");
                }
            }
            SparseArray<Parcelable> panelStates = savedInstanceState.getSparseParcelableArray(PANELS_TAG);
            if (panelStates != null) {
                restorePanelState(panelStates);
            }
            if (this.mDecorContentParent != null) {
                SparseArray<Parcelable> actionBarStates = savedInstanceState.getSparseParcelableArray(ACTION_BAR_TAG);
                if (actionBarStates != null) {
                    doPendingInvalidatePanelMenu();
                    this.mDecorContentParent.restoreToolbarHierarchyState(actionBarStates);
                    return;
                }
                Log.w(TAG, "Missing saved instance states for action bar views! State will not be restored.");
            }
        }
    }

    private void savePanelState(SparseArray<Parcelable> icicles) {
        PanelFeatureState[] panels = this.mPanels;
        if (panels != null) {
            for (int curFeatureId = panels.length - 1; curFeatureId >= 0; curFeatureId--) {
                if (panels[curFeatureId] != null) {
                    icicles.put(curFeatureId, panels[curFeatureId].onSaveInstanceState());
                }
            }
        }
    }

    private void restorePanelState(SparseArray<Parcelable> icicles) {
        for (int i = icicles.size() - 1; i >= 0; i--) {
            int curFeatureId = icicles.keyAt(i);
            PanelFeatureState st = getPanelState(curFeatureId, false);
            if (st != null) {
                st.onRestoreInstanceState(icicles.get(curFeatureId));
                invalidatePanelMenu(curFeatureId);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void openPanelsAfterRestore() {
        PanelFeatureState[] panels = this.mPanels;
        if (panels != null) {
            for (int i = panels.length - 1; i >= 0; i--) {
                PanelFeatureState st = panels[i];
                if (st != null) {
                    st.applyFrozenState();
                    if (!st.isOpen && st.wasLastOpen) {
                        st.isInExpandedMode = st.wasLastExpanded;
                        openPanel(st, (KeyEvent) null);
                    }
                }
            }
        }
    }

    private class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        private PanelMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            Menu parentMenu = menu.getRootMenu();
            boolean isSubMenu = parentMenu != menu;
            PanelFeatureState panel = PhoneWindow.this.findMenuPanel(isSubMenu ? parentMenu : menu);
            if (panel == null) {
                return;
            }
            if (isSubMenu) {
                PhoneWindow.this.callOnPanelClosed(panel.featureId, panel, parentMenu);
                PhoneWindow.this.closePanel(panel, true);
                return;
            }
            PhoneWindow.this.closePanel(panel, allMenusAreClosing);
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            Window.Callback cb;
            if (subMenu != null || !PhoneWindow.this.hasFeature(8) || (cb = PhoneWindow.this.getCallback()) == null || PhoneWindow.this.isDestroyed()) {
                return true;
            }
            cb.onMenuOpened(8, subMenu);
            return true;
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            Window.Callback cb = PhoneWindow.this.getCallback();
            if (cb == null) {
                return false;
            }
            cb.onMenuOpened(8, subMenu);
            return true;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            PhoneWindow.this.checkCloseActionMenu(menu);
        }
    }

    /* access modifiers changed from: protected */
    public DecorView generateDecor(int featureId) {
        Context context;
        if (this.mUseDecorContext) {
            Context applicationContext = getContext().getApplicationContext();
            if (applicationContext == null) {
                context = getContext();
            } else {
                context = new DecorContext(applicationContext, getContext());
                if (this.mTheme != -1) {
                    context.setTheme(this.mTheme);
                }
            }
        } else {
            context = getContext();
        }
        return new DecorView(context, featureId, this, getAttributes());
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x02eb  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x02f9  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x034b  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x0356  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x040c  */
    /* JADX WARNING: Removed duplicated region for block: B:232:0x0473  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.ViewGroup generateLayout(com.android.internal.policy.DecorView r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            android.content.res.TypedArray r2 = r19.getWindowStyle()
            r3 = 0
            r4 = 4
            boolean r4 = r2.getBoolean(r4, r3)
            r0.mIsFloating = r4
            int r4 = r19.getForcedWindowFlags()
            int r4 = ~r4
            r5 = 65792(0x10100, float:9.2194E-41)
            r4 = r4 & r5
            boolean r6 = r0.mIsFloating
            if (r6 == 0) goto L_0x0026
            r5 = -2
            r0.setLayout(r5, r5)
            r0.setFlags(r3, r4)
            goto L_0x0029
        L_0x0026:
            r0.setFlags(r5, r4)
        L_0x0029:
            r5 = 3
            boolean r5 = r2.getBoolean(r5, r3)
            r6 = 8
            r7 = 1
            if (r5 == 0) goto L_0x0037
            r0.requestFeature(r7)
            goto L_0x0042
        L_0x0037:
            r5 = 15
            boolean r5 = r2.getBoolean(r5, r3)
            if (r5 == 0) goto L_0x0042
            r0.requestFeature(r6)
        L_0x0042:
            r5 = 17
            boolean r5 = r2.getBoolean(r5, r3)
            r8 = 9
            if (r5 == 0) goto L_0x004f
            r0.requestFeature(r8)
        L_0x004f:
            r5 = 16
            boolean r9 = r2.getBoolean(r5, r3)
            if (r9 == 0) goto L_0x005c
            r9 = 10
            r0.requestFeature(r9)
        L_0x005c:
            r9 = 25
            boolean r9 = r2.getBoolean(r9, r3)
            r10 = 11
            if (r9 == 0) goto L_0x0069
            r0.requestFeature(r10)
        L_0x0069:
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x007a
            int r8 = r19.getForcedWindowFlags()
            int r8 = ~r8
            r9 = 1024(0x400, float:1.435E-42)
            r8 = r8 & r9
            r0.setFlags(r9, r8)
        L_0x007a:
            r8 = 23
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x008e
            int r8 = r19.getForcedWindowFlags()
            int r8 = ~r8
            r9 = 67108864(0x4000000, float:1.5046328E-36)
            r8 = r8 & r9
            r0.setFlags(r9, r8)
        L_0x008e:
            r8 = 24
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x00a2
            int r8 = r19.getForcedWindowFlags()
            int r8 = ~r8
            r9 = 134217728(0x8000000, float:3.85186E-34)
            r8 = r8 & r9
            r0.setFlags(r9, r8)
        L_0x00a2:
            r8 = 22
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x00b5
            int r8 = r19.getForcedWindowFlags()
            int r8 = ~r8
            r9 = 33554432(0x2000000, float:9.403955E-38)
            r8 = r8 & r9
            r0.setFlags(r9, r8)
        L_0x00b5:
            r8 = 14
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x00ca
            r8 = 1048576(0x100000, float:1.469368E-39)
            r9 = 1048576(0x100000, float:1.469368E-39)
            int r11 = r19.getForcedWindowFlags()
            int r11 = ~r11
            r9 = r9 & r11
            r0.setFlags(r8, r9)
        L_0x00ca:
            r8 = 18
            android.content.Context r9 = r19.getContext()
            android.content.pm.ApplicationInfo r9 = r9.getApplicationInfo()
            int r9 = r9.targetSdkVersion
            if (r9 < r10) goto L_0x00da
            r9 = r7
            goto L_0x00dc
        L_0x00da:
            r9 = r3
        L_0x00dc:
            boolean r8 = r2.getBoolean(r8, r9)
            if (r8 == 0) goto L_0x00ef
            r8 = 8388608(0x800000, float:1.17549435E-38)
            r9 = 8388608(0x800000, float:1.17549435E-38)
            int r11 = r19.getForcedWindowFlags()
            int r11 = ~r11
            r9 = r9 & r11
            r0.setFlags(r8, r9)
        L_0x00ef:
            r8 = 19
            android.util.TypedValue r9 = r0.mMinWidthMajor
            r2.getValue(r8, r9)
            r8 = 20
            android.util.TypedValue r9 = r0.mMinWidthMinor
            r2.getValue(r8, r9)
            r8 = 57
            boolean r8 = r2.hasValue(r8)
            if (r8 == 0) goto L_0x0117
            android.util.TypedValue r8 = r0.mFixedWidthMajor
            if (r8 != 0) goto L_0x0110
            android.util.TypedValue r8 = new android.util.TypedValue
            r8.<init>()
            r0.mFixedWidthMajor = r8
        L_0x0110:
            r8 = 57
            android.util.TypedValue r9 = r0.mFixedWidthMajor
            r2.getValue(r8, r9)
        L_0x0117:
            r8 = 58
            boolean r8 = r2.hasValue(r8)
            if (r8 == 0) goto L_0x0131
            android.util.TypedValue r8 = r0.mFixedWidthMinor
            if (r8 != 0) goto L_0x012a
            android.util.TypedValue r8 = new android.util.TypedValue
            r8.<init>()
            r0.mFixedWidthMinor = r8
        L_0x012a:
            r8 = 58
            android.util.TypedValue r9 = r0.mFixedWidthMinor
            r2.getValue(r8, r9)
        L_0x0131:
            r8 = 55
            boolean r8 = r2.hasValue(r8)
            if (r8 == 0) goto L_0x014b
            android.util.TypedValue r8 = r0.mFixedHeightMajor
            if (r8 != 0) goto L_0x0144
            android.util.TypedValue r8 = new android.util.TypedValue
            r8.<init>()
            r0.mFixedHeightMajor = r8
        L_0x0144:
            r8 = 55
            android.util.TypedValue r9 = r0.mFixedHeightMajor
            r2.getValue(r8, r9)
        L_0x014b:
            r8 = 56
            boolean r8 = r2.hasValue(r8)
            if (r8 == 0) goto L_0x0165
            android.util.TypedValue r8 = r0.mFixedHeightMinor
            if (r8 != 0) goto L_0x015e
            android.util.TypedValue r8 = new android.util.TypedValue
            r8.<init>()
            r0.mFixedHeightMinor = r8
        L_0x015e:
            r8 = 56
            android.util.TypedValue r9 = r0.mFixedHeightMinor
            r2.getValue(r8, r9)
        L_0x0165:
            r8 = 26
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x0172
            r8 = 12
            r0.requestFeature(r8)
        L_0x0172:
            r8 = 45
            boolean r8 = r2.getBoolean(r8, r3)
            if (r8 == 0) goto L_0x017f
            r8 = 13
            r0.requestFeature(r8)
        L_0x017f:
            r8 = 5
            boolean r8 = r2.getBoolean(r8, r3)
            r0.mIsTranslucent = r8
            android.content.Context r8 = r19.getContext()
            android.content.pm.ApplicationInfo r9 = r8.getApplicationInfo()
            int r9 = r9.targetSdkVersion
            if (r9 >= r10) goto L_0x0194
            r11 = r7
            goto L_0x0195
        L_0x0194:
            r11 = r3
        L_0x0195:
            r12 = 14
            if (r9 >= r12) goto L_0x019b
            r12 = r7
            goto L_0x019c
        L_0x019b:
            r12 = r3
        L_0x019c:
            r13 = 21
            if (r9 >= r13) goto L_0x01a2
            r13 = r7
            goto L_0x01a3
        L_0x01a2:
            r13 = r3
        L_0x01a3:
            r14 = 29
            if (r9 >= r14) goto L_0x01a9
            r14 = r7
            goto L_0x01aa
        L_0x01a9:
            r14 = r3
        L_0x01aa:
            android.content.res.Resources r15 = r8.getResources()
            r10 = 17891625(0x1110129, float:2.6633126E-38)
            boolean r10 = r15.getBoolean(r10)
            boolean r15 = r0.hasFeature(r6)
            if (r15 == 0) goto L_0x01c4
            boolean r15 = r0.hasFeature(r7)
            if (r15 == 0) goto L_0x01c2
            goto L_0x01c4
        L_0x01c2:
            r15 = r3
            goto L_0x01c5
        L_0x01c4:
            r15 = r7
        L_0x01c5:
            r6 = 2
            if (r11 != 0) goto L_0x01d3
            if (r12 == 0) goto L_0x01cf
            if (r10 == 0) goto L_0x01cf
            if (r15 == 0) goto L_0x01cf
            goto L_0x01d3
        L_0x01cf:
            r0.setNeedsMenuKey(r6)
            goto L_0x01d6
        L_0x01d3:
            r0.setNeedsMenuKey(r7)
        L_0x01d6:
            boolean r6 = r0.mForcedStatusBarColor
            if (r6 != 0) goto L_0x01e4
            r6 = 35
            r5 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            int r5 = r2.getColor(r6, r5)
            r0.mStatusBarColor = r5
        L_0x01e4:
            boolean r5 = r0.mForcedNavigationBarColor
            if (r5 != 0) goto L_0x01fa
            r5 = 36
            r6 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            int r5 = r2.getColor(r5, r6)
            r0.mNavigationBarColor = r5
            r5 = 50
            int r5 = r2.getColor(r5, r3)
            r0.mNavigationBarDividerColor = r5
        L_0x01fa:
            if (r14 != 0) goto L_0x020c
            r5 = 52
            boolean r5 = r2.getBoolean(r5, r3)
            r0.mEnsureStatusBarContrastWhenTransparent = r5
            r5 = 53
            boolean r5 = r2.getBoolean(r5, r7)
            r0.mEnsureNavigationBarContrastWhenTransparent = r5
        L_0x020c:
            android.view.WindowManager$LayoutParams r5 = r19.getAttributes()
            boolean r6 = r0.mIsFloating
            if (r6 != 0) goto L_0x0239
            if (r13 != 0) goto L_0x022c
            r6 = 34
            boolean r6 = r2.getBoolean(r6, r3)
            if (r6 == 0) goto L_0x022c
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r16 = -2147483648(0xffffffff80000000, float:-0.0)
            int r7 = r19.getForcedWindowFlags()
            int r7 = ~r7
            r7 = r7 & r16
            r0.setFlags(r6, r7)
        L_0x022c:
            com.android.internal.policy.DecorView r6 = r0.mDecor
            boolean r6 = r6.mForceWindowDrawsBarBackgrounds
            if (r6 == 0) goto L_0x0239
            int r6 = r5.privateFlags
            r7 = 131072(0x20000, float:1.83671E-40)
            r6 = r6 | r7
            r5.privateFlags = r6
        L_0x0239:
            r6 = 46
            boolean r6 = r2.getBoolean(r6, r3)
            if (r6 == 0) goto L_0x024b
            int r6 = r20.getSystemUiVisibility()
            r6 = r6 | 8192(0x2000, float:1.14794E-41)
            r1.setSystemUiVisibility(r6)
        L_0x024b:
            r6 = 49
            boolean r6 = r2.getBoolean(r6, r3)
            if (r6 == 0) goto L_0x025e
            int r6 = r20.getSystemUiVisibility()
            r7 = 16
            r6 = r6 | r7
            r1.setSystemUiVisibility(r6)
        L_0x025e:
            r6 = 51
            boolean r7 = r2.hasValue(r6)
            if (r7 == 0) goto L_0x0290
            r7 = -1
            int r7 = r2.getInt(r6, r7)
            if (r7 < 0) goto L_0x0273
            r3 = 2
            if (r7 > r3) goto L_0x0273
            r5.layoutInDisplayCutoutMode = r7
            goto L_0x0290
        L_0x0273:
            java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r1 = "Unknown windowLayoutInDisplayCutoutMode: "
            r6.append(r1)
            r1 = 51
            java.lang.String r1 = r2.getString(r1)
            r6.append(r1)
            java.lang.String r1 = r6.toString()
            r3.<init>(r1)
            throw r3
        L_0x0290:
            boolean r1 = r0.mAlwaysReadCloseOnTouchAttr
            if (r1 != 0) goto L_0x02a2
            android.content.Context r1 = r19.getContext()
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo()
            int r1 = r1.targetSdkVersion
            r3 = 11
            if (r1 < r3) goto L_0x02af
        L_0x02a2:
            r1 = 21
            r3 = 0
            boolean r1 = r2.getBoolean(r1, r3)
            if (r1 == 0) goto L_0x02af
            r1 = 1
            r0.setCloseOnTouchOutsideIfNotSet(r1)
        L_0x02af:
            boolean r1 = r19.hasSoftInputMode()
            if (r1 != 0) goto L_0x02bf
            r1 = 13
            int r3 = r5.softInputMode
            int r1 = r2.getInt(r1, r3)
            r5.softInputMode = r1
        L_0x02bf:
            boolean r1 = r0.mIsFloating
            r3 = 11
            boolean r1 = r2.getBoolean(r3, r1)
            if (r1 == 0) goto L_0x02e6
            int r1 = r19.getForcedWindowFlags()
            r3 = 2
            r1 = r1 & r3
            if (r1 != 0) goto L_0x02d6
            int r1 = r5.flags
            r1 = r1 | r3
            r5.flags = r1
        L_0x02d6:
            boolean r1 = r19.haveDimAmount()
            if (r1 != 0) goto L_0x02e6
            r1 = 1056964608(0x3f000000, float:0.5)
            r3 = 0
            float r1 = r2.getFloat(r3, r1)
            r5.dimAmount = r1
            goto L_0x02e7
        L_0x02e6:
            r3 = 0
        L_0x02e7:
            int r1 = r5.windowAnimations
            if (r1 != 0) goto L_0x02f3
            r1 = 8
            int r6 = r2.getResourceId(r1, r3)
            r5.windowAnimations = r6
        L_0x02f3:
            android.view.Window r1 = r19.getContainer()
            if (r1 != 0) goto L_0x0343
            android.graphics.drawable.Drawable r1 = r0.mBackgroundDrawable
            if (r1 != 0) goto L_0x0316
            int r1 = r0.mFrameResource
            if (r1 != 0) goto L_0x0309
            r1 = 0
            r3 = 2
            int r3 = r2.getResourceId(r3, r1)
            r0.mFrameResource = r3
        L_0x0309:
            r1 = 1
            boolean r3 = r2.hasValue(r1)
            if (r3 == 0) goto L_0x0316
            android.graphics.drawable.Drawable r3 = r2.getDrawable(r1)
            r0.mBackgroundDrawable = r3
        L_0x0316:
            r1 = 47
            boolean r1 = r2.hasValue(r1)
            if (r1 == 0) goto L_0x0326
            r1 = 47
            android.graphics.drawable.Drawable r1 = r2.getDrawable(r1)
            r0.mBackgroundFallbackDrawable = r1
        L_0x0326:
            boolean r1 = r0.mLoadElevation
            if (r1 == 0) goto L_0x0333
            r1 = 38
            r3 = 0
            float r1 = r2.getDimension(r1, r3)
            r0.mElevation = r1
        L_0x0333:
            r1 = 39
            r3 = 0
            boolean r1 = r2.getBoolean(r1, r3)
            r0.mClipToOutline = r1
            r1 = 7
            int r1 = r2.getColor(r1, r3)
            r0.mTextColor = r1
        L_0x0343:
            int r1 = r19.getLocalFeatures()
            r3 = r1 & 2048(0x800, float:2.87E-42)
            if (r3 == 0) goto L_0x0356
            r3 = 17367273(0x10900e9, float:2.516358E-38)
            r6 = 1
            r0.setCloseOnSwipeEnabled(r6)
            r17 = r4
            goto L_0x03f5
        L_0x0356:
            r3 = r1 & 24
            if (r3 == 0) goto L_0x0383
            boolean r3 = r0.mIsFloating
            if (r3 == 0) goto L_0x0377
            android.util.TypedValue r3 = new android.util.TypedValue
            r3.<init>()
            android.content.Context r6 = r19.getContext()
            android.content.res.Resources$Theme r6 = r6.getTheme()
            r7 = 17956910(0x112002e, float:2.6816094E-38)
            r17 = r4
            r4 = 1
            r6.resolveAttribute(r7, r3, r4)
            int r3 = r3.resourceId
            goto L_0x037c
        L_0x0377:
            r17 = r4
            r3 = 17367275(0x10900eb, float:2.5163585E-38)
        L_0x037c:
            r4 = 8
            r0.removeFeature(r4)
            goto L_0x03f5
        L_0x0383:
            r17 = r4
            r3 = r1 & 36
            if (r3 == 0) goto L_0x0391
            r3 = r1 & 256(0x100, float:3.59E-43)
            if (r3 != 0) goto L_0x0391
            r3 = 17367270(0x10900e6, float:2.516357E-38)
            goto L_0x03f5
        L_0x0391:
            r3 = r1 & 128(0x80, float:1.794E-43)
            if (r3 == 0) goto L_0x03b9
            boolean r3 = r0.mIsFloating
            if (r3 == 0) goto L_0x03b0
            android.util.TypedValue r3 = new android.util.TypedValue
            r3.<init>()
            android.content.Context r4 = r19.getContext()
            android.content.res.Resources$Theme r4 = r4.getTheme()
            r6 = 17956907(0x112002b, float:2.6816085E-38)
            r7 = 1
            r4.resolveAttribute(r6, r3, r7)
            int r3 = r3.resourceId
            goto L_0x03b3
        L_0x03b0:
            r3 = 17367269(0x10900e5, float:2.5163568E-38)
        L_0x03b3:
            r4 = 8
            r0.removeFeature(r4)
            goto L_0x03f5
        L_0x03b9:
            r3 = r1 & 2
            if (r3 != 0) goto L_0x03ea
            boolean r3 = r0.mIsFloating
            if (r3 == 0) goto L_0x03d8
            android.util.TypedValue r3 = new android.util.TypedValue
            r3.<init>()
            android.content.Context r4 = r19.getContext()
            android.content.res.Resources$Theme r4 = r4.getTheme()
            r6 = 17956909(0x112002d, float:2.681609E-38)
            r7 = 1
            r4.resolveAttribute(r6, r3, r7)
            int r3 = r3.resourceId
            goto L_0x03f5
        L_0x03d8:
            r3 = r1 & 256(0x100, float:3.59E-43)
            if (r3 == 0) goto L_0x03e6
            r3 = 54
            r4 = 17367268(0x10900e4, float:2.5163565E-38)
            int r3 = r2.getResourceId(r3, r4)
            goto L_0x03f5
        L_0x03e6:
            r3 = 17367274(0x10900ea, float:2.5163582E-38)
            goto L_0x03f5
        L_0x03ea:
            r3 = r1 & 1024(0x400, float:1.435E-42)
            if (r3 == 0) goto L_0x03f2
            r3 = 17367272(0x10900e8, float:2.5163576E-38)
            goto L_0x03f5
        L_0x03f2:
            r3 = 17367271(0x10900e7, float:2.5163573E-38)
        L_0x03f5:
            com.android.internal.policy.DecorView r4 = r0.mDecor
            r4.startChanging()
            com.android.internal.policy.DecorView r4 = r0.mDecor
            android.view.LayoutInflater r6 = r0.mLayoutInflater
            r4.onResourcesLoaded(r6, r3)
            r4 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r4 = r0.findViewById(r4)
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            if (r4 == 0) goto L_0x0473
            r6 = r1 & 32
            if (r6 == 0) goto L_0x041b
            r6 = 0
            android.widget.ProgressBar r6 = r0.getCircularProgressBar(r6)
            if (r6 == 0) goto L_0x041b
            r7 = 1
            r6.setIndeterminate(r7)
        L_0x041b:
            r6 = r1 & 2048(0x800, float:2.87E-42)
            if (r6 == 0) goto L_0x0422
            r0.registerSwipeCallbacks(r4)
        L_0x0422:
            android.view.Window r6 = r19.getContainer()
            if (r6 != 0) goto L_0x046b
            com.android.internal.policy.DecorView r6 = r0.mDecor
            android.graphics.drawable.Drawable r7 = r0.mBackgroundDrawable
            r6.setWindowBackground(r7)
            int r6 = r0.mFrameResource
            if (r6 == 0) goto L_0x043e
            android.content.Context r6 = r19.getContext()
            int r7 = r0.mFrameResource
            android.graphics.drawable.Drawable r6 = r6.getDrawable(r7)
            goto L_0x043f
        L_0x043e:
            r6 = 0
        L_0x043f:
            com.android.internal.policy.DecorView r7 = r0.mDecor
            r7.setWindowFrame(r6)
            com.android.internal.policy.DecorView r7 = r0.mDecor
            r18 = r1
            float r1 = r0.mElevation
            r7.setElevation(r1)
            com.android.internal.policy.DecorView r1 = r0.mDecor
            boolean r7 = r0.mClipToOutline
            r1.setClipToOutline(r7)
            java.lang.CharSequence r1 = r0.mTitle
            if (r1 == 0) goto L_0x045d
            java.lang.CharSequence r1 = r0.mTitle
            r0.setTitle(r1)
        L_0x045d:
            int r1 = r0.mTitleColor
            if (r1 != 0) goto L_0x0465
            int r1 = r0.mTextColor
            r0.mTitleColor = r1
        L_0x0465:
            int r1 = r0.mTitleColor
            r0.setTitleColor(r1)
            goto L_0x046d
        L_0x046b:
            r18 = r1
        L_0x046d:
            com.android.internal.policy.DecorView r1 = r0.mDecor
            r1.finishChanging()
            return r4
        L_0x0473:
            r18 = r1
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r6 = "Window couldn't find content container view"
            r1.<init>(r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.PhoneWindow.generateLayout(com.android.internal.policy.DecorView):android.view.ViewGroup");
    }

    public void alwaysReadCloseOnTouchAttr() {
        this.mAlwaysReadCloseOnTouchAttr = true;
    }

    private void installDecor() {
        this.mForceDecorInstall = false;
        if (this.mDecor == null) {
            this.mDecor = generateDecor(-1);
            this.mDecor.setDescendantFocusability(262144);
            this.mDecor.setIsRootNamespace(true);
            if (!this.mInvalidatePanelMenuPosted && this.mInvalidatePanelMenuFeatures != 0) {
                this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            }
        } else {
            this.mDecor.setWindow(this);
        }
        if (this.mContentParent == null) {
            this.mContentParent = generateLayout(this.mDecor);
            this.mDecor.makeOptionalFitsSystemWindows();
            DecorContentParent decorContentParent = (DecorContentParent) this.mDecor.findViewById(R.id.decor_content_parent);
            if (decorContentParent != null) {
                this.mDecorContentParent = decorContentParent;
                this.mDecorContentParent.setWindowCallback(getCallback());
                if (this.mDecorContentParent.getTitle() == null) {
                    this.mDecorContentParent.setWindowTitle(this.mTitle);
                }
                int localFeatures = getLocalFeatures();
                for (int i = 0; i < 13; i++) {
                    if (((1 << i) & localFeatures) != 0) {
                        this.mDecorContentParent.initFeature(i);
                    }
                }
                this.mDecorContentParent.setUiOptions(this.mUiOptions);
                if ((this.mResourcesSetFlags & 1) != 0 || (this.mIconRes != 0 && !this.mDecorContentParent.hasIcon())) {
                    this.mDecorContentParent.setIcon(this.mIconRes);
                } else if ((this.mResourcesSetFlags & 1) == 0 && this.mIconRes == 0 && !this.mDecorContentParent.hasIcon()) {
                    this.mDecorContentParent.setIcon(getContext().getPackageManager().getDefaultActivityIcon());
                    this.mResourcesSetFlags |= 4;
                }
                if ((this.mResourcesSetFlags & 2) != 0 || (this.mLogoRes != 0 && !this.mDecorContentParent.hasLogo())) {
                    this.mDecorContentParent.setLogo(this.mLogoRes);
                }
                PanelFeatureState st = getPanelState(0, false);
                if (!isDestroyed() && ((st == null || st.menu == null) && !this.mIsStartingWindow)) {
                    invalidatePanelMenu(8);
                }
            } else {
                this.mTitleView = (TextView) findViewById(16908310);
                if (this.mTitleView != null) {
                    if ((getLocalFeatures() & 2) != 0) {
                        View titleContainer = findViewById(R.id.title_container);
                        if (titleContainer != null) {
                            titleContainer.setVisibility(8);
                        } else {
                            this.mTitleView.setVisibility(8);
                        }
                        this.mContentParent.setForeground((Drawable) null);
                    } else {
                        this.mTitleView.setText(this.mTitle);
                    }
                }
            }
            if (this.mDecor.getBackground() == null && this.mBackgroundFallbackDrawable != null) {
                this.mDecor.setBackgroundFallback(this.mBackgroundFallbackDrawable);
            }
            if (hasFeature(13)) {
                if (this.mTransitionManager == null) {
                    int transitionRes = getWindowStyle().getResourceId(27, 0);
                    if (transitionRes != 0) {
                        this.mTransitionManager = TransitionInflater.from(getContext()).inflateTransitionManager(transitionRes, this.mContentParent);
                    } else {
                        this.mTransitionManager = new TransitionManager();
                    }
                }
                this.mEnterTransition = getTransition(this.mEnterTransition, (Transition) null, 28);
                this.mReturnTransition = getTransition(this.mReturnTransition, USE_DEFAULT_TRANSITION, 40);
                this.mExitTransition = getTransition(this.mExitTransition, (Transition) null, 29);
                this.mReenterTransition = getTransition(this.mReenterTransition, USE_DEFAULT_TRANSITION, 41);
                this.mSharedElementEnterTransition = getTransition(this.mSharedElementEnterTransition, (Transition) null, 30);
                this.mSharedElementReturnTransition = getTransition(this.mSharedElementReturnTransition, USE_DEFAULT_TRANSITION, 42);
                this.mSharedElementExitTransition = getTransition(this.mSharedElementExitTransition, (Transition) null, 31);
                this.mSharedElementReenterTransition = getTransition(this.mSharedElementReenterTransition, USE_DEFAULT_TRANSITION, 43);
                if (this.mAllowEnterTransitionOverlap == null) {
                    this.mAllowEnterTransitionOverlap = Boolean.valueOf(getWindowStyle().getBoolean(33, true));
                }
                if (this.mAllowReturnTransitionOverlap == null) {
                    this.mAllowReturnTransitionOverlap = Boolean.valueOf(getWindowStyle().getBoolean(32, true));
                }
                if (this.mBackgroundFadeDurationMillis < 0) {
                    this.mBackgroundFadeDurationMillis = (long) getWindowStyle().getInteger(37, 300);
                }
                if (this.mSharedElementsUseOverlay == null) {
                    this.mSharedElementsUseOverlay = Boolean.valueOf(getWindowStyle().getBoolean(44, true));
                }
            }
        }
    }

    private Transition getTransition(Transition currentValue, Transition defaultValue, int id) {
        if (currentValue != defaultValue) {
            return currentValue;
        }
        int transitionId = getWindowStyle().getResourceId(id, -1);
        Transition transition = defaultValue;
        if (transitionId == -1 || transitionId == 17760256) {
            return transition;
        }
        Transition transition2 = TransitionInflater.from(getContext()).inflateTransition(transitionId);
        if (!(transition2 instanceof TransitionSet) || ((TransitionSet) transition2).getTransitionCount() != 0) {
            return transition2;
        }
        return null;
    }

    private Drawable loadImageURI(Uri uri) {
        try {
            return Drawable.createFromStream(getContext().getContentResolver().openInputStream(uri), (String) null);
        } catch (Exception e) {
            Log.w(TAG, "Unable to open content: " + uri);
            return null;
        }
    }

    private DrawableFeatureState getDrawableState(int featureId, boolean required) {
        if ((getFeatures() & (1 << featureId)) != 0) {
            DrawableFeatureState[] drawableFeatureStateArr = this.mDrawables;
            DrawableFeatureState[] ar = drawableFeatureStateArr;
            if (drawableFeatureStateArr == null || ar.length <= featureId) {
                DrawableFeatureState[] nar = new DrawableFeatureState[(featureId + 1)];
                if (ar != null) {
                    System.arraycopy(ar, 0, nar, 0, ar.length);
                }
                ar = nar;
                this.mDrawables = nar;
            }
            DrawableFeatureState st = ar[featureId];
            if (st != null) {
                return st;
            }
            DrawableFeatureState drawableFeatureState = new DrawableFeatureState(featureId);
            DrawableFeatureState st2 = drawableFeatureState;
            ar[featureId] = drawableFeatureState;
            return st2;
        } else if (!required) {
            return null;
        } else {
            throw new RuntimeException("The feature has not been requested");
        }
    }

    /* access modifiers changed from: package-private */
    public PanelFeatureState getPanelState(int featureId, boolean required) {
        return getPanelState(featureId, required, (PanelFeatureState) null);
    }

    private PanelFeatureState getPanelState(int featureId, boolean required, PanelFeatureState convertPanelState) {
        if ((getFeatures() & (1 << featureId)) != 0) {
            PanelFeatureState[] panelFeatureStateArr = this.mPanels;
            PanelFeatureState[] ar = panelFeatureStateArr;
            if (panelFeatureStateArr == null || ar.length <= featureId) {
                PanelFeatureState[] nar = new PanelFeatureState[(featureId + 1)];
                if (ar != null) {
                    System.arraycopy(ar, 0, nar, 0, ar.length);
                }
                ar = nar;
                this.mPanels = nar;
            }
            PanelFeatureState st = ar[featureId];
            if (st != null) {
                return st;
            }
            PanelFeatureState panelFeatureState = convertPanelState != null ? convertPanelState : new PanelFeatureState(featureId);
            PanelFeatureState st2 = panelFeatureState;
            ar[featureId] = panelFeatureState;
            return st2;
        } else if (!required) {
            return null;
        } else {
            throw new RuntimeException("The feature has not been requested");
        }
    }

    public final void setChildDrawable(int featureId, Drawable drawable) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        st.child = drawable;
        updateDrawable(featureId, st, false);
    }

    public final void setChildInt(int featureId, int value) {
        updateInt(featureId, value, false);
    }

    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        PanelFeatureState st = getPanelState(0, false);
        if (st == null || st.menu == null || !st.menu.isShortcutKey(keyCode, event)) {
            return false;
        }
        return true;
    }

    private void updateDrawable(int featureId, DrawableFeatureState st, boolean fromResume) {
        if (this.mContentParent != null) {
            int featureMask = 1 << featureId;
            if ((getFeatures() & featureMask) != 0 || fromResume) {
                Drawable drawable = null;
                if (st != null) {
                    drawable = st.child;
                    if (drawable == null) {
                        drawable = st.local;
                    }
                    if (drawable == null) {
                        drawable = st.def;
                    }
                }
                if ((getLocalFeatures() & featureMask) == 0) {
                    if (getContainer() == null) {
                        return;
                    }
                    if (isActive() || fromResume) {
                        getContainer().setChildDrawable(featureId, drawable);
                    }
                } else if (st == null) {
                } else {
                    if (st.cur != drawable || st.curAlpha != st.alpha) {
                        st.cur = drawable;
                        st.curAlpha = st.alpha;
                        onDrawableChanged(featureId, drawable, st.alpha);
                    }
                }
            }
        }
    }

    private void updateInt(int featureId, int value, boolean fromResume) {
        if (this.mContentParent != null) {
            int featureMask = 1 << featureId;
            if ((getFeatures() & featureMask) == 0 && !fromResume) {
                return;
            }
            if ((getLocalFeatures() & featureMask) != 0) {
                onIntChanged(featureId, value);
            } else if (getContainer() != null) {
                getContainer().setChildInt(featureId, value);
            }
        }
    }

    private ImageView getLeftIconView() {
        if (this.mLeftIconView != null) {
            return this.mLeftIconView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        ImageView imageView = (ImageView) findViewById(R.id.left_icon);
        this.mLeftIconView = imageView;
        return imageView;
    }

    /* access modifiers changed from: protected */
    public void dispatchWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        super.dispatchWindowAttributesChanged(attrs);
        if (this.mDecor != null) {
            this.mDecor.updateColorViews((WindowInsets) null, true);
        }
    }

    private ProgressBar getCircularProgressBar(boolean shouldInstallDecor) {
        if (this.mCircularProgressBar != null) {
            return this.mCircularProgressBar;
        }
        if (this.mContentParent == null && shouldInstallDecor) {
            installDecor();
        }
        this.mCircularProgressBar = (ProgressBar) findViewById(R.id.progress_circular);
        if (this.mCircularProgressBar != null) {
            this.mCircularProgressBar.setVisibility(4);
        }
        return this.mCircularProgressBar;
    }

    private ProgressBar getHorizontalProgressBar(boolean shouldInstallDecor) {
        if (this.mHorizontalProgressBar != null) {
            return this.mHorizontalProgressBar;
        }
        if (this.mContentParent == null && shouldInstallDecor) {
            installDecor();
        }
        this.mHorizontalProgressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        if (this.mHorizontalProgressBar != null) {
            this.mHorizontalProgressBar.setVisibility(4);
        }
        return this.mHorizontalProgressBar;
    }

    private ImageView getRightIconView() {
        if (this.mRightIconView != null) {
            return this.mRightIconView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        ImageView imageView = (ImageView) findViewById(R.id.right_icon);
        this.mRightIconView = imageView;
        return imageView;
    }

    private void registerSwipeCallbacks(ViewGroup contentParent) {
        if (!(contentParent instanceof SwipeDismissLayout)) {
            Log.w(TAG, "contentParent is not a SwipeDismissLayout: " + contentParent);
            return;
        }
        SwipeDismissLayout swipeDismiss = (SwipeDismissLayout) contentParent;
        swipeDismiss.setOnDismissedListener(new SwipeDismissLayout.OnDismissedListener() {
            public void onDismissed(SwipeDismissLayout layout) {
                PhoneWindow.this.dispatchOnWindowSwipeDismissed();
                PhoneWindow.this.dispatchOnWindowDismissed(false, true);
            }
        });
        swipeDismiss.setOnSwipeProgressChangedListener(new SwipeDismissLayout.OnSwipeProgressChangedListener() {
            public void onSwipeProgressChanged(SwipeDismissLayout layout, float alpha, float translate) {
                int flags;
                WindowManager.LayoutParams newParams = PhoneWindow.this.getAttributes();
                newParams.x = (int) translate;
                newParams.alpha = alpha;
                PhoneWindow.this.setAttributes(newParams);
                if (newParams.x == 0) {
                    flags = 1024;
                } else {
                    flags = 512;
                }
                PhoneWindow.this.setFlags(flags, 1536);
            }

            public void onSwipeCancelled(SwipeDismissLayout layout) {
                WindowManager.LayoutParams newParams = PhoneWindow.this.getAttributes();
                if (newParams.x != 0 || newParams.alpha != 1.0f) {
                    newParams.x = 0;
                    newParams.alpha = 1.0f;
                    PhoneWindow.this.setAttributes(newParams);
                    PhoneWindow.this.setFlags(1024, 1536);
                }
            }
        });
    }

    public void setCloseOnSwipeEnabled(boolean closeOnSwipeEnabled) {
        if (hasFeature(11) && (this.mContentParent instanceof SwipeDismissLayout)) {
            ((SwipeDismissLayout) this.mContentParent).setDismissable(closeOnSwipeEnabled);
        }
        super.setCloseOnSwipeEnabled(closeOnSwipeEnabled);
    }

    /* access modifiers changed from: private */
    public void callOnPanelClosed(int featureId, PanelFeatureState panel, Menu menu) {
        Window.Callback cb = getCallback();
        if (cb != null) {
            if (menu == null) {
                if (panel == null && featureId >= 0 && featureId < this.mPanels.length) {
                    panel = this.mPanels[featureId];
                }
                if (panel != null) {
                    menu = panel.menu;
                }
            }
            if ((panel == null || panel.isOpen) && !isDestroyed()) {
                cb.onPanelClosed(featureId, menu);
            }
        }
    }

    private boolean isTvUserSetupComplete() {
        boolean z = true;
        boolean isTvSetupComplete = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0) != 0;
        if (Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.TV_USER_SETUP_COMPLETE, 0) == 0) {
            z = false;
        }
        return isTvSetupComplete & z;
    }

    private boolean launchDefaultSearch(KeyEvent event) {
        boolean result;
        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) && !isTvUserSetupComplete()) {
            return false;
        }
        Window.Callback cb = getCallback();
        if (cb == null || isDestroyed()) {
            result = false;
        } else {
            sendCloseSystemWindows("search");
            int deviceId = event.getDeviceId();
            SearchEvent searchEvent = null;
            if (deviceId != 0) {
                searchEvent = new SearchEvent(InputDevice.getDevice(deviceId));
            }
            try {
                result = cb.onSearchRequested(searchEvent);
            } catch (AbstractMethodError e) {
                Log.e(TAG, "WindowCallback " + cb.getClass().getName() + " does not implement method onSearchRequested(SearchEvent); fa", e);
                result = cb.onSearchRequested();
            }
        }
        boolean result2 = result;
        if (result2 || (getContext().getResources().getConfiguration().uiMode & 15) != 4) {
            return result2;
        }
        Bundle args = new Bundle();
        args.putInt(Intent.EXTRA_ASSIST_INPUT_DEVICE_ID, event.getDeviceId());
        return ((SearchManager) getContext().getSystemService("search")).launchLegacyAssist((String) null, getContext().getUserId(), args);
    }

    public void setVolumeControlStream(int streamType) {
        this.mVolumeControlStreamType = streamType;
    }

    public int getVolumeControlStream() {
        return this.mVolumeControlStreamType;
    }

    public void setMediaController(MediaController controller) {
        this.mMediaController = controller;
    }

    public MediaController getMediaController() {
        return this.mMediaController;
    }

    public void setEnterTransition(Transition enterTransition) {
        this.mEnterTransition = enterTransition;
    }

    public void setReturnTransition(Transition transition) {
        this.mReturnTransition = transition;
    }

    public void setExitTransition(Transition exitTransition) {
        this.mExitTransition = exitTransition;
    }

    public void setReenterTransition(Transition transition) {
        this.mReenterTransition = transition;
    }

    public void setSharedElementEnterTransition(Transition sharedElementEnterTransition) {
        this.mSharedElementEnterTransition = sharedElementEnterTransition;
    }

    public void setSharedElementReturnTransition(Transition transition) {
        this.mSharedElementReturnTransition = transition;
    }

    public void setSharedElementExitTransition(Transition sharedElementExitTransition) {
        this.mSharedElementExitTransition = sharedElementExitTransition;
    }

    public void setSharedElementReenterTransition(Transition transition) {
        this.mSharedElementReenterTransition = transition;
    }

    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    public Transition getReturnTransition() {
        if (this.mReturnTransition == USE_DEFAULT_TRANSITION) {
            return getEnterTransition();
        }
        return this.mReturnTransition;
    }

    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    public Transition getReenterTransition() {
        if (this.mReenterTransition == USE_DEFAULT_TRANSITION) {
            return getExitTransition();
        }
        return this.mReenterTransition;
    }

    public Transition getSharedElementEnterTransition() {
        return this.mSharedElementEnterTransition;
    }

    public Transition getSharedElementReturnTransition() {
        return this.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : this.mSharedElementReturnTransition;
    }

    public Transition getSharedElementExitTransition() {
        return this.mSharedElementExitTransition;
    }

    public Transition getSharedElementReenterTransition() {
        return this.mSharedElementReenterTransition == USE_DEFAULT_TRANSITION ? getSharedElementExitTransition() : this.mSharedElementReenterTransition;
    }

    public void setAllowEnterTransitionOverlap(boolean allow) {
        this.mAllowEnterTransitionOverlap = Boolean.valueOf(allow);
    }

    public boolean getAllowEnterTransitionOverlap() {
        if (this.mAllowEnterTransitionOverlap == null) {
            return true;
        }
        return this.mAllowEnterTransitionOverlap.booleanValue();
    }

    public void setAllowReturnTransitionOverlap(boolean allowExitTransitionOverlap) {
        this.mAllowReturnTransitionOverlap = Boolean.valueOf(allowExitTransitionOverlap);
    }

    public boolean getAllowReturnTransitionOverlap() {
        if (this.mAllowReturnTransitionOverlap == null) {
            return true;
        }
        return this.mAllowReturnTransitionOverlap.booleanValue();
    }

    public long getTransitionBackgroundFadeDuration() {
        if (this.mBackgroundFadeDurationMillis < 0) {
            return 300;
        }
        return this.mBackgroundFadeDurationMillis;
    }

    public void setTransitionBackgroundFadeDuration(long fadeDurationMillis) {
        if (fadeDurationMillis >= 0) {
            this.mBackgroundFadeDurationMillis = fadeDurationMillis;
            return;
        }
        throw new IllegalArgumentException("negative durations are not allowed");
    }

    public void setSharedElementsUseOverlay(boolean sharedElementsUseOverlay) {
        this.mSharedElementsUseOverlay = Boolean.valueOf(sharedElementsUseOverlay);
    }

    public boolean getSharedElementsUseOverlay() {
        if (this.mSharedElementsUseOverlay == null) {
            return true;
        }
        return this.mSharedElementsUseOverlay.booleanValue();
    }

    private static final class DrawableFeatureState {
        int alpha = 255;
        Drawable child;
        Drawable cur;
        int curAlpha = 255;
        Drawable def;
        final int featureId;
        Drawable local;
        int resid;
        Uri uri;

        DrawableFeatureState(int _featureId) {
            this.featureId = _featureId;
        }
    }

    static final class PanelFeatureState {
        int background;
        View createdPanelView;
        DecorView decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int fullBackground;
        int gravity;
        IconMenuPresenter iconMenuPresenter;
        boolean isCompact;
        boolean isHandled;
        boolean isInExpandedMode;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        int listPresenterTheme;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastExpanded;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int featureId2) {
            this.featureId = featureId2;
        }

        public boolean isInListMode() {
            return this.isInExpandedMode || this.isCompact;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            if (this.isCompact || this.isInExpandedMode) {
                if (this.listMenuPresenter.getAdapter().getCount() > 0) {
                    return true;
                }
                return false;
            } else if (((ViewGroup) this.shownPanelView).getChildCount() > 0) {
                return true;
            } else {
                return false;
            }
        }

        public void clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.iconMenuPresenter = null;
            this.listMenuPresenter = null;
        }

        /* access modifiers changed from: package-private */
        public void setStyle(Context context) {
            TypedArray a = context.obtainStyledAttributes(R.styleable.Theme);
            this.background = a.getResourceId(46, 0);
            this.fullBackground = a.getResourceId(47, 0);
            this.windowAnimations = a.getResourceId(93, 0);
            this.isCompact = a.getBoolean(314, false);
            this.listPresenterTheme = a.getResourceId(315, R.style.Theme_ExpandedMenu);
            a.recycle();
        }

        /* access modifiers changed from: package-private */
        public void setMenu(MenuBuilder menu2) {
            if (menu2 != this.menu) {
                if (this.menu != null) {
                    this.menu.removeMenuPresenter(this.iconMenuPresenter);
                    this.menu.removeMenuPresenter(this.listMenuPresenter);
                }
                this.menu = menu2;
                if (menu2 != null) {
                    if (this.iconMenuPresenter != null) {
                        menu2.addMenuPresenter(this.iconMenuPresenter);
                    }
                    if (this.listMenuPresenter != null) {
                        menu2.addMenuPresenter(this.listMenuPresenter);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public MenuView getListMenuView(Context context, MenuPresenter.Callback cb) {
            if (this.menu == null) {
                return null;
            }
            if (!this.isCompact) {
                getIconMenuView(context, cb);
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter((int) R.layout.list_menu_item_layout, this.listPresenterTheme);
                this.listMenuPresenter.setCallback(cb);
                this.listMenuPresenter.setId(R.id.list_menu_presenter);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            if (this.iconMenuPresenter != null) {
                this.listMenuPresenter.setItemIndexOffset(this.iconMenuPresenter.getNumActualItemsShown());
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        /* access modifiers changed from: package-private */
        public MenuView getIconMenuView(Context context, MenuPresenter.Callback cb) {
            if (this.menu == null) {
                return null;
            }
            if (this.iconMenuPresenter == null) {
                this.iconMenuPresenter = new IconMenuPresenter(context);
                this.iconMenuPresenter.setCallback(cb);
                this.iconMenuPresenter.setId(R.id.icon_menu_presenter);
                this.menu.addMenuPresenter(this.iconMenuPresenter);
            }
            return this.iconMenuPresenter.getMenuView(this.decorView);
        }

        /* access modifiers changed from: package-private */
        public Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            savedState.isInExpandedMode = this.isInExpandedMode;
            if (this.menu != null) {
                savedState.menuState = new Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        /* access modifiers changed from: package-private */
        public void onRestoreInstanceState(Parcelable state) {
            SavedState savedState = (SavedState) state;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.wasLastExpanded = savedState.isInExpandedMode;
            this.frozenMenuState = savedState.menuState;
            this.createdPanelView = null;
            this.shownPanelView = null;
            this.decorView = null;
        }

        /* access modifiers changed from: package-private */
        public void applyFrozenState() {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }

        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
                public SavedState createFromParcel(Parcel in) {
                    return SavedState.readFromParcel(in);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
            int featureId;
            boolean isInExpandedMode;
            boolean isOpen;
            Bundle menuState;

            private SavedState() {
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.featureId);
                dest.writeInt(this.isOpen ? 1 : 0);
                dest.writeInt(this.isInExpandedMode ? 1 : 0);
                if (this.isOpen) {
                    dest.writeBundle(this.menuState);
                }
            }

            /* access modifiers changed from: private */
            public static SavedState readFromParcel(Parcel source) {
                SavedState savedState = new SavedState();
                savedState.featureId = source.readInt();
                boolean z = false;
                savedState.isOpen = source.readInt() == 1;
                if (source.readInt() == 1) {
                    z = true;
                }
                savedState.isInExpandedMode = z;
                if (savedState.isOpen) {
                    savedState.menuState = source.readBundle();
                }
                return savedState;
            }
        }
    }

    static class RotationWatcher extends IRotationWatcher.Stub {
        private Handler mHandler;
        private boolean mIsWatching;
        private final Runnable mRotationChanged = new Runnable() {
            public void run() {
                RotationWatcher.this.dispatchRotationChanged();
            }
        };
        private final ArrayList<WeakReference<PhoneWindow>> mWindows = new ArrayList<>();

        RotationWatcher() {
        }

        public void onRotationChanged(int rotation) throws RemoteException {
            this.mHandler.post(this.mRotationChanged);
        }

        public void addWindow(PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                if (!this.mIsWatching) {
                    try {
                        WindowManagerHolder.sWindowManager.watchRotation(this, phoneWindow.getContext().getDisplayId());
                        this.mHandler = new Handler();
                        this.mIsWatching = true;
                    } catch (RemoteException ex) {
                        Log.e(PhoneWindow.TAG, "Couldn't start watching for device rotation", ex);
                    }
                }
                this.mWindows.add(new WeakReference(phoneWindow));
            }
        }

        public void removeWindow(PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    PhoneWindow win = (PhoneWindow) this.mWindows.get(i).get();
                    if (win != null) {
                        if (win != phoneWindow) {
                            i++;
                        }
                    }
                    this.mWindows.remove(i);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void dispatchRotationChanged() {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    PhoneWindow win = (PhoneWindow) this.mWindows.get(i).get();
                    if (win != null) {
                        win.onOptionsPanelRotationChanged();
                        i++;
                    } else {
                        this.mWindows.remove(i);
                    }
                }
            }
        }
    }

    public static final class PhoneWindowMenuCallback implements MenuBuilder.Callback, MenuPresenter.Callback {
        private static final int FEATURE_ID = 6;
        private boolean mShowDialogForSubmenu;
        private MenuDialogHelper mSubMenuHelper;
        private final PhoneWindow mWindow;

        public PhoneWindowMenuCallback(PhoneWindow window) {
            this.mWindow = window;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            if (menu.getRootMenu() != menu) {
                onCloseSubMenu(menu);
            }
            if (allMenusAreClosing) {
                Window.Callback callback = this.mWindow.getCallback();
                if (callback != null && !this.mWindow.isDestroyed()) {
                    callback.onPanelClosed(6, menu);
                }
                if (menu == this.mWindow.mContextMenu) {
                    this.mWindow.dismissContextMenu();
                }
                if (this.mSubMenuHelper != null) {
                    this.mSubMenuHelper.dismiss();
                    this.mSubMenuHelper = null;
                }
            }
        }

        private void onCloseSubMenu(MenuBuilder menu) {
            Window.Callback callback = this.mWindow.getCallback();
            if (callback != null && !this.mWindow.isDestroyed()) {
                callback.onPanelClosed(6, menu.getRootMenu());
            }
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            Window.Callback callback = this.mWindow.getCallback();
            return callback != null && !this.mWindow.isDestroyed() && callback.onMenuItemSelected(6, item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            if (subMenu == null) {
                return false;
            }
            subMenu.setCallback(this);
            if (!this.mShowDialogForSubmenu) {
                return false;
            }
            this.mSubMenuHelper = new MenuDialogHelper(subMenu);
            this.mSubMenuHelper.show((IBinder) null);
            return true;
        }

        public void setShowDialogForSubmenu(boolean enabled) {
            this.mShowDialogForSubmenu = enabled;
        }
    }

    /* access modifiers changed from: package-private */
    public int getLocalFeaturesPrivate() {
        return super.getLocalFeatures();
    }

    /* access modifiers changed from: protected */
    public void setDefaultWindowFormat(int format) {
        super.setDefaultWindowFormat(format);
    }

    /* access modifiers changed from: package-private */
    public void sendCloseSystemWindows() {
        sendCloseSystemWindows(getContext(), (String) null);
    }

    /* access modifiers changed from: package-private */
    public void sendCloseSystemWindows(String reason) {
        sendCloseSystemWindows(getContext(), reason);
    }

    public static void sendCloseSystemWindows(Context context, String reason) {
        if (ActivityManager.isSystemReady()) {
            try {
                ActivityManager.getService().closeSystemDialogs(reason);
            } catch (RemoteException e) {
            }
        }
    }

    public int getStatusBarColor() {
        return this.mStatusBarColor;
    }

    public void setStatusBarColor(int color) {
        this.mStatusBarColor = color;
        this.mForcedStatusBarColor = true;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews((WindowInsets) null, false);
        }
    }

    public int getNavigationBarColor() {
        return this.mNavigationBarColor;
    }

    public void setNavigationBarColor(int color) {
        this.mNavigationBarColor = color;
        this.mForcedNavigationBarColor = true;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews((WindowInsets) null, false);
        }
    }

    public void setNavigationBarDividerColor(int navigationBarDividerColor) {
        this.mNavigationBarDividerColor = navigationBarDividerColor;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews((WindowInsets) null, false);
        }
    }

    public int getNavigationBarDividerColor() {
        return this.mNavigationBarDividerColor;
    }

    public void setStatusBarContrastEnforced(boolean ensureContrast) {
        this.mEnsureStatusBarContrastWhenTransparent = ensureContrast;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews((WindowInsets) null, false);
        }
    }

    public boolean isStatusBarContrastEnforced() {
        return this.mEnsureStatusBarContrastWhenTransparent;
    }

    public void setNavigationBarContrastEnforced(boolean enforceContrast) {
        this.mEnsureNavigationBarContrastWhenTransparent = enforceContrast;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews((WindowInsets) null, false);
        }
    }

    public boolean isNavigationBarContrastEnforced() {
        return this.mEnsureNavigationBarContrastWhenTransparent;
    }

    public void setIsStartingWindow(boolean isStartingWindow) {
        this.mIsStartingWindow = isStartingWindow;
    }

    public void setTheme(int resid) {
        this.mTheme = resid;
        if (this.mDecor != null) {
            Context context = this.mDecor.getContext();
            if (context instanceof DecorContext) {
                context.setTheme(resid);
            }
        }
    }

    public void setResizingCaptionDrawable(Drawable drawable) {
        this.mDecor.setUserCaptionBackgroundDrawable(drawable);
    }

    public void setDecorCaptionShade(int decorCaptionShade) {
        this.mDecorCaptionShade = decorCaptionShade;
        if (this.mDecor != null) {
            this.mDecor.updateDecorCaptionShade();
        }
    }

    /* access modifiers changed from: package-private */
    public int getDecorCaptionShade() {
        return this.mDecorCaptionShade;
    }

    public void setAttributes(WindowManager.LayoutParams params) {
        super.setAttributes(params);
        if (this.mDecor != null) {
            this.mDecor.updateLogTag(params);
        }
    }

    public WindowInsetsController getInsetsController() {
        return this.mDecor.getWindowInsetsController();
    }

    public void setSystemGestureExclusionRects(List<Rect> rects) {
        getViewRootImpl().setRootSystemGestureExclusionRects(rects);
    }

    public List<Rect> getSystemGestureExclusionRects() {
        return getViewRootImpl().getRootSystemGestureExclusionRects();
    }
}
