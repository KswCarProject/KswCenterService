package com.android.internal.widget;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.CollapsibleActionView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.internal.C3132R;
import com.android.internal.view.menu.ActionMenuItem;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;

/* loaded from: classes4.dex */
public class ActionBarView extends AbsActionBarView implements DecorToolbar {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 63;
    private static final String TAG = "ActionBarView";
    private ActionBarContextView mContextView;
    private View mCustomNavView;
    private int mDefaultUpDescription;
    private int mDisplayOptions;
    View mExpandedActionView;
    private final View.OnClickListener mExpandedActionViewUpListener;
    private HomeView mExpandedHomeLayout;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private CharSequence mHomeDescription;
    private int mHomeDescriptionRes;
    private HomeView mHomeLayout;
    private Drawable mIcon;
    private boolean mIncludeTabs;
    private final int mIndeterminateProgressStyle;
    private ProgressBar mIndeterminateProgressView;
    private boolean mIsCollapsible;
    private int mItemPadding;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    private ActionMenuItem mLogoNavItem;
    private boolean mMenuPrepared;
    private AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    private int mNavigationMode;
    private MenuBuilder mOptionsMenu;
    private int mProgressBarPadding;
    private final int mProgressStyle;
    private ProgressBar mProgressView;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private CharSequence mSubtitle;
    private final int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private ScrollingTabContainerView mTabScrollView;
    private Runnable mTabSelector;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private final int mTitleStyleRes;
    private TextView mTitleView;
    private final View.OnClickListener mUpClickListener;
    private ViewGroup mUpGoerFive;
    private boolean mUserTitle;
    private boolean mWasHomeEnabled;
    Window.Callback mWindowCallback;

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDisplayOptions = -1;
        this.mDefaultUpDescription = C3132R.string.action_bar_up_description;
        this.mExpandedActionViewUpListener = new View.OnClickListener() { // from class: com.android.internal.widget.ActionBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MenuItemImpl item = ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
                if (item != null) {
                    item.collapseActionView();
                }
            }
        };
        this.mUpClickListener = new View.OnClickListener() { // from class: com.android.internal.widget.ActionBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActionBarView.this.mMenuPrepared) {
                    ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.this.mLogoNavItem);
                }
            }
        };
        setBackgroundResource(0);
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.ActionBar, 16843470, 0);
        this.mNavigationMode = a.getInt(7, 0);
        this.mTitle = a.getText(5);
        this.mSubtitle = a.getText(9);
        this.mLogo = a.getDrawable(6);
        this.mIcon = a.getDrawable(0);
        LayoutInflater inflater = LayoutInflater.from(context);
        int homeResId = a.getResourceId(16, C3132R.layout.action_bar_home);
        this.mUpGoerFive = (ViewGroup) inflater.inflate(C3132R.layout.action_bar_up_container, (ViewGroup) this, false);
        this.mHomeLayout = (HomeView) inflater.inflate(homeResId, this.mUpGoerFive, false);
        this.mExpandedHomeLayout = (HomeView) inflater.inflate(homeResId, this.mUpGoerFive, false);
        this.mExpandedHomeLayout.setShowUp(true);
        this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
        this.mExpandedHomeLayout.setContentDescription(getResources().getText(this.mDefaultUpDescription));
        Drawable upBackground = this.mUpGoerFive.getBackground();
        if (upBackground != null) {
            this.mExpandedHomeLayout.setBackground(upBackground.getConstantState().newDrawable());
        }
        this.mExpandedHomeLayout.setEnabled(true);
        this.mExpandedHomeLayout.setFocusable(true);
        this.mTitleStyleRes = a.getResourceId(11, 0);
        this.mSubtitleStyleRes = a.getResourceId(12, 0);
        this.mProgressStyle = a.getResourceId(1, 0);
        this.mIndeterminateProgressStyle = a.getResourceId(14, 0);
        this.mProgressBarPadding = a.getDimensionPixelOffset(15, 0);
        this.mItemPadding = a.getDimensionPixelOffset(17, 0);
        setDisplayOptions(a.getInt(8, 0));
        int customNavId = a.getResourceId(10, 0);
        if (customNavId != 0) {
            this.mCustomNavView = inflater.inflate(customNavId, (ViewGroup) this, false);
            this.mNavigationMode = 0;
            setDisplayOptions(16 | this.mDisplayOptions);
        }
        this.mContentHeight = a.getLayoutDimension(4, 0);
        a.recycle();
        this.mLogoNavItem = new ActionMenuItem(context, 0, 16908332, 0, 0, this.mTitle);
        this.mUpGoerFive.setOnClickListener(this.mUpClickListener);
        this.mUpGoerFive.setClickable(true);
        this.mUpGoerFive.setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    @Override // com.android.internal.widget.AbsActionBarView, android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mTitleView = null;
        this.mSubtitleView = null;
        if (this.mTitleLayout != null && this.mTitleLayout.getParent() == this.mUpGoerFive) {
            this.mUpGoerFive.removeView(this.mTitleLayout);
        }
        this.mTitleLayout = null;
        if ((this.mDisplayOptions & 8) != 0) {
            initTitle();
        }
        if (this.mHomeDescriptionRes != 0) {
            setNavigationContentDescription(this.mHomeDescriptionRes);
        }
        if (this.mTabScrollView != null && this.mIncludeTabs) {
            ViewGroup.LayoutParams lp = this.mTabScrollView.getLayoutParams();
            if (lp != null) {
                lp.width = -2;
                lp.height = -1;
            }
            this.mTabScrollView.setAllowCollapse(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowCallback(Window.Callback cb) {
        this.mWindowCallback = cb;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mTabSelector);
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initProgress() {
        this.mProgressView = new ProgressBar(this.mContext, null, 0, this.mProgressStyle);
        this.mProgressView.setId(C3132R.C3134id.progress_horizontal);
        this.mProgressView.setMax(10000);
        this.mProgressView.setVisibility(8);
        addView(this.mProgressView);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initIndeterminateProgress() {
        this.mIndeterminateProgressView = new ProgressBar(this.mContext, null, 0, this.mIndeterminateProgressStyle);
        this.mIndeterminateProgressView.setId(C3132R.C3134id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        addView(this.mIndeterminateProgressView);
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public void setSplitToolbar(boolean splitActionBar) {
        if (this.mSplitActionBar != splitActionBar) {
            if (this.mMenuView != null) {
                ViewGroup oldParent = (ViewGroup) this.mMenuView.getParent();
                if (oldParent != null) {
                    oldParent.removeView(this.mMenuView);
                }
                if (splitActionBar) {
                    if (this.mSplitView != null) {
                        this.mSplitView.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            if (this.mSplitView != null) {
                this.mSplitView.setVisibility(splitActionBar ? 0 : 8);
            }
            if (this.mActionMenuPresenter != null) {
                if (!splitActionBar) {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(C3132R.bool.action_bar_expanded_action_views_exclusive));
                } else {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                }
            }
            super.setSplitToolbar(splitActionBar);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isSplit() {
        return this.mSplitActionBar;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canSplit() {
        return true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasEmbeddedTabs() {
        return this.mIncludeTabs;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setEmbeddedTabView(ScrollingTabContainerView tabs) {
        if (this.mTabScrollView != null) {
            removeView(this.mTabScrollView);
        }
        this.mTabScrollView = tabs;
        this.mIncludeTabs = tabs != null;
        if (this.mIncludeTabs && this.mNavigationMode == 2) {
            addView(this.mTabScrollView);
            ViewGroup.LayoutParams lp = this.mTabScrollView.getLayoutParams();
            lp.width = -2;
            lp.height = -1;
            tabs.setAllowCollapse(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenu(Menu menu, MenuPresenter.Callback cb) {
        ActionMenuView menuView;
        ViewGroup oldParent;
        if (menu == this.mOptionsMenu) {
            return;
        }
        if (this.mOptionsMenu != null) {
            this.mOptionsMenu.removeMenuPresenter(this.mActionMenuPresenter);
            this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        MenuBuilder builder = (MenuBuilder) menu;
        this.mOptionsMenu = builder;
        if (this.mMenuView != null && (oldParent = (ViewGroup) this.mMenuView.getParent()) != null) {
            oldParent.removeView(this.mMenuView);
        }
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
            this.mActionMenuPresenter.setCallback(cb);
            this.mActionMenuPresenter.setId(C3132R.C3134id.action_menu_presenter);
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(C3132R.bool.action_bar_expanded_action_views_exclusive));
            configPresenters(builder);
            menuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            ViewGroup oldParent2 = (ViewGroup) menuView.getParent();
            if (oldParent2 != null && oldParent2 != this) {
                oldParent2.removeView(menuView);
            }
            addView(menuView, layoutParams);
        } else {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
            this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
            this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
            layoutParams.width = -1;
            layoutParams.height = -2;
            configPresenters(builder);
            menuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            if (this.mSplitView != null) {
                ViewGroup oldParent3 = (ViewGroup) menuView.getParent();
                if (oldParent3 != null && oldParent3 != this.mSplitView) {
                    oldParent3.removeView(menuView);
                }
                menuView.setVisibility(getAnimatedVisibility());
                this.mSplitView.addView(menuView, layoutParams);
            } else {
                menuView.setLayoutParams(layoutParams);
            }
        }
        this.mMenuView = menuView;
    }

    private void configPresenters(MenuBuilder builder) {
        if (builder != null) {
            builder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            builder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
            return;
        }
        this.mActionMenuPresenter.initForMenu(this.mPopupContext, null);
        this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
        this.mActionMenuPresenter.updateMenuView(true);
        this.mExpandedMenuPresenter.updateMenuView(true);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void collapseActionView() {
        MenuItemImpl item = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if (item != null) {
            item.collapseActionView();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCustomView(View view) {
        boolean showCustom = (this.mDisplayOptions & 16) != 0;
        if (this.mCustomNavView != null && showCustom) {
            removeView(this.mCustomNavView);
        }
        this.mCustomNavView = view;
        if (this.mCustomNavView != null && showCustom) {
            addView(this.mCustomNavView);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setTitle(CharSequence title) {
        this.mUserTitle = true;
        setTitleImpl(title);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowTitle(CharSequence title) {
        if (!this.mUserTitle) {
            setTitleImpl(title);
        }
    }

    private void setTitleImpl(CharSequence title) {
        this.mTitle = title;
        if (this.mTitleView != null) {
            this.mTitleView.setText(title);
            boolean visible = (this.mExpandedActionView != null || (this.mDisplayOptions & 8) == 0 || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) ? false : true;
            this.mTitleLayout.setVisibility(visible ? 0 : 8);
        }
        if (this.mLogoNavItem != null) {
            this.mLogoNavItem.setTitle(title);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSubtitle(CharSequence subtitle) {
        this.mSubtitle = subtitle;
        if (this.mSubtitleView != null) {
            this.mSubtitleView.setText(subtitle);
            this.mSubtitleView.setVisibility(subtitle != null ? 0 : 8);
            boolean visible = (this.mExpandedActionView != null || (this.mDisplayOptions & 8) == 0 || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) ? false : true;
            this.mTitleLayout.setVisibility(visible ? 0 : 8);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setHomeButtonEnabled(boolean enable) {
        setHomeButtonEnabled(enable, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHomeButtonEnabled(boolean enable, boolean recordState) {
        if (recordState) {
            this.mWasHomeEnabled = enable;
        }
        if (this.mExpandedActionView != null) {
            return;
        }
        this.mUpGoerFive.setEnabled(enable);
        this.mUpGoerFive.setFocusable(enable);
        updateHomeAccessibility(enable);
    }

    private void updateHomeAccessibility(boolean homeEnabled) {
        if (!homeEnabled) {
            this.mUpGoerFive.setContentDescription(null);
            this.mUpGoerFive.setImportantForAccessibility(2);
            return;
        }
        this.mUpGoerFive.setImportantForAccessibility(0);
        this.mUpGoerFive.setContentDescription(buildHomeContentDescription());
    }

    private CharSequence buildHomeContentDescription() {
        CharSequence homeDesc;
        if (this.mHomeDescription != null) {
            homeDesc = this.mHomeDescription;
        } else if ((this.mDisplayOptions & 4) != 0) {
            homeDesc = this.mContext.getResources().getText(this.mDefaultUpDescription);
        } else {
            homeDesc = this.mContext.getResources().getText(C3132R.string.action_bar_home_description);
        }
        CharSequence title = getTitle();
        CharSequence subtitle = getSubtitle();
        if (!TextUtils.isEmpty(title)) {
            if (!TextUtils.isEmpty(subtitle)) {
                String result = getResources().getString(C3132R.string.action_bar_home_subtitle_description_format, title, subtitle, homeDesc);
                return result;
            }
            String result2 = getResources().getString(C3132R.string.action_bar_home_description_format, title, homeDesc);
            return result2;
        }
        return homeDesc;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDisplayOptions(int options) {
        int flagsChanged = this.mDisplayOptions != -1 ? options ^ this.mDisplayOptions : -1;
        this.mDisplayOptions = options;
        if ((flagsChanged & 63) != 0) {
            if ((flagsChanged & 4) != 0) {
                boolean setUp = (options & 4) != 0;
                this.mHomeLayout.setShowUp(setUp);
                if (setUp) {
                    setHomeButtonEnabled(true);
                }
            }
            if ((flagsChanged & 1) != 0) {
                boolean logoVis = (this.mLogo == null || (options & 1) == 0) ? false : true;
                this.mHomeLayout.setIcon(logoVis ? this.mLogo : this.mIcon);
            }
            if ((flagsChanged & 8) != 0) {
                if ((options & 8) != 0) {
                    initTitle();
                } else {
                    this.mUpGoerFive.removeView(this.mTitleLayout);
                }
            }
            boolean showHome = (options & 2) != 0;
            boolean homeAsUp = (this.mDisplayOptions & 4) != 0;
            boolean titleUp = !showHome && homeAsUp;
            this.mHomeLayout.setShowIcon(showHome);
            int homeVis = ((showHome || titleUp) && this.mExpandedActionView == null) ? 0 : 8;
            this.mHomeLayout.setVisibility(homeVis);
            if ((flagsChanged & 16) != 0 && this.mCustomNavView != null) {
                if ((options & 16) != 0) {
                    addView(this.mCustomNavView);
                } else {
                    removeView(this.mCustomNavView);
                }
            }
            if (this.mTitleLayout != null && (flagsChanged & 32) != 0) {
                if ((options & 32) != 0) {
                    this.mTitleView.setSingleLine(false);
                    this.mTitleView.setMaxLines(2);
                } else {
                    this.mTitleView.setMaxLines(1);
                    this.mTitleView.setSingleLine(true);
                }
            }
            requestLayout();
        } else {
            invalidate();
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(Drawable icon) {
        this.mIcon = icon;
        if (icon != null && ((this.mDisplayOptions & 1) == 0 || this.mLogo == null)) {
            this.mHomeLayout.setIcon(icon);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(int resId) {
        setIcon(resId != 0 ? this.mContext.getDrawable(resId) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasIcon() {
        return this.mIcon != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(Drawable logo) {
        this.mLogo = logo;
        if (logo != null && (this.mDisplayOptions & 1) != 0) {
            this.mHomeLayout.setIcon(logo);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(int resId) {
        setLogo(resId != 0 ? this.mContext.getDrawable(resId) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasLogo() {
        return this.mLogo != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationMode(int mode) {
        int oldMode = this.mNavigationMode;
        if (mode != oldMode) {
            switch (oldMode) {
                case 1:
                    if (this.mListNavLayout != null) {
                        removeView(this.mListNavLayout);
                        break;
                    }
                    break;
                case 2:
                    if (this.mTabScrollView != null && this.mIncludeTabs) {
                        removeView(this.mTabScrollView);
                        break;
                    }
                    break;
            }
            switch (mode) {
                case 1:
                    if (this.mSpinner == null) {
                        this.mSpinner = new Spinner(this.mContext, null, 16843479);
                        this.mSpinner.setId(C3132R.C3134id.action_bar_spinner);
                        this.mListNavLayout = new LinearLayout(this.mContext, null, 16843508);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -1);
                        params.gravity = 17;
                        this.mListNavLayout.addView(this.mSpinner, params);
                    }
                    if (this.mSpinner.getAdapter() != this.mSpinnerAdapter) {
                        this.mSpinner.setAdapter(this.mSpinnerAdapter);
                    }
                    this.mSpinner.setOnItemSelectedListener(this.mNavItemSelectedListener);
                    addView(this.mListNavLayout);
                    break;
                case 2:
                    if (this.mTabScrollView != null && this.mIncludeTabs) {
                        addView(this.mTabScrollView);
                        break;
                    }
                    break;
            }
            this.mNavigationMode = mode;
            requestLayout();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownParams(SpinnerAdapter adapter, AdapterView.OnItemSelectedListener l) {
        this.mSpinnerAdapter = adapter;
        this.mNavItemSelectedListener = l;
        if (this.mSpinner != null) {
            this.mSpinner.setAdapter(adapter);
            this.mSpinner.setOnItemSelectedListener(l);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownItemCount() {
        if (this.mSpinnerAdapter != null) {
            return this.mSpinnerAdapter.getCount();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownSelectedPosition(int position) {
        this.mSpinner.setSelection(position);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public View getCustomView() {
        return this.mCustomNavView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public ViewGroup getViewGroup() {
        return this;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams((int) DEFAULT_CUSTOM_GRAVITY);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        ViewParent parent;
        super.onFinishInflate();
        this.mUpGoerFive.addView(this.mHomeLayout, 0);
        addView(this.mUpGoerFive);
        if (this.mCustomNavView != null && (this.mDisplayOptions & 16) != 0 && (parent = this.mCustomNavView.getParent()) != this) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mCustomNavView);
            }
            addView(this.mCustomNavView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            this.mTitleLayout = (LinearLayout) inflater.inflate(C3132R.layout.action_bar_title_item, (ViewGroup) this, false);
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(C3132R.C3134id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C3132R.C3134id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(this.mTitleStyleRes);
            }
            if (this.mTitle != null) {
                this.mTitleView.setText(this.mTitle);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(this.mSubtitleStyleRes);
            }
            if (this.mSubtitle != null) {
                this.mSubtitleView.setText(this.mSubtitle);
                this.mSubtitleView.setVisibility(0);
            }
        }
        this.mUpGoerFive.addView(this.mTitleLayout);
        if (this.mExpandedActionView != null || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) {
            this.mTitleLayout.setVisibility(8);
        } else {
            this.mTitleLayout.setVisibility(0);
        }
    }

    public void setContextView(ActionBarContextView view) {
        this.mContextView = view;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCollapsible(boolean collapsible) {
        this.mIsCollapsible = collapsible;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isTitleTruncated() {
        Layout titleLayout;
        if (this.mTitleView == null || (titleLayout = this.mTitleView.getLayout()) == null) {
            return false;
        }
        int lineCount = titleLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (titleLayout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0208  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int availableWidth;
        View customView;
        int horizontalMargin;
        int customNavHeightMode;
        int childCount = getChildCount();
        if (this.mIsCollapsible) {
            int visibleChildren = 0;
            for (int visibleChildren2 = 0; visibleChildren2 < childCount; visibleChildren2++) {
                View child = getChildAt(visibleChildren2);
                if (child.getVisibility() != 8 && ((child != this.mMenuView || this.mMenuView.getChildCount() != 0) && child != this.mUpGoerFive)) {
                    visibleChildren++;
                }
            }
            int upChildCount = this.mUpGoerFive.getChildCount();
            int visibleChildren3 = visibleChildren;
            for (int visibleChildren4 = 0; visibleChildren4 < upChildCount; visibleChildren4++) {
                if (this.mUpGoerFive.getChildAt(visibleChildren4).getVisibility() != 8) {
                    visibleChildren3++;
                }
            }
            if (visibleChildren3 == 0) {
                setMeasuredDimension(0, 0);
                return;
            }
        }
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        }
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != Integer.MIN_VALUE) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
        int contentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = this.mContentHeight >= 0 ? this.mContentHeight : View.MeasureSpec.getSize(heightMeasureSpec);
        int verticalPadding = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int height = maxHeight - verticalPadding;
        int childSpecHeight = View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
        int exactHeightSpec = View.MeasureSpec.makeMeasureSpec(height, 1073741824);
        int availableWidth2 = (contentWidth - paddingLeft) - paddingRight;
        int leftOfCenter = availableWidth2 / 2;
        int rightOfCenter = leftOfCenter;
        boolean showTitle = (this.mTitleLayout == null || this.mTitleLayout.getVisibility() == 8 || (this.mDisplayOptions & 8) == 0) ? false : true;
        HomeView homeLayout = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        ViewGroup.LayoutParams homeLp = homeLayout.getLayoutParams();
        int widthMode2 = homeLp.width;
        int homeWidthSpec = widthMode2 < 0 ? View.MeasureSpec.makeMeasureSpec(availableWidth2, Integer.MIN_VALUE) : View.MeasureSpec.makeMeasureSpec(homeLp.width, 1073741824);
        homeLayout.measure(homeWidthSpec, exactHeightSpec);
        int homeWidthSpec2 = homeLayout.getVisibility();
        int homeWidth = 0;
        if ((homeWidthSpec2 == 8 || homeLayout.getParent() != this.mUpGoerFive) && !showTitle) {
            availableWidth = availableWidth2;
        } else {
            int homeWidth2 = homeLayout.getMeasuredWidth();
            int homeOffsetWidth = homeLayout.getStartOffset() + homeWidth2;
            int homeWidth3 = availableWidth2 - homeOffsetWidth;
            availableWidth = Math.max(0, homeWidth3);
            int heightMode2 = availableWidth - homeOffsetWidth;
            leftOfCenter = Math.max(0, heightMode2);
            homeWidth = homeWidth2;
        }
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            availableWidth = measureChildView(this.mMenuView, availableWidth, exactHeightSpec, 0);
            rightOfCenter = Math.max(0, rightOfCenter - this.mMenuView.getMeasuredWidth());
        }
        if (this.mIndeterminateProgressView != null && this.mIndeterminateProgressView.getVisibility() != 8) {
            availableWidth = measureChildView(this.mIndeterminateProgressView, availableWidth, childSpecHeight, 0);
            rightOfCenter = Math.max(0, rightOfCenter - this.mIndeterminateProgressView.getMeasuredWidth());
        }
        int rightOfCenter2 = rightOfCenter;
        if (this.mExpandedActionView == null) {
            switch (this.mNavigationMode) {
                case 1:
                    if (this.mListNavLayout != null) {
                        int itemPaddingSize = showTitle ? this.mItemPadding * 2 : this.mItemPadding;
                        int availableWidth3 = Math.max(0, availableWidth - itemPaddingSize);
                        int leftOfCenter2 = Math.max(0, leftOfCenter - itemPaddingSize);
                        this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(availableWidth3, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
                        int listNavWidth = this.mListNavLayout.getMeasuredWidth();
                        availableWidth = Math.max(0, availableWidth3 - listNavWidth);
                        leftOfCenter = Math.max(0, leftOfCenter2 - listNavWidth);
                        break;
                    }
                    break;
                case 2:
                    if (this.mTabScrollView != null) {
                        int itemPaddingSize2 = showTitle ? this.mItemPadding * 2 : this.mItemPadding;
                        int availableWidth4 = Math.max(0, availableWidth - itemPaddingSize2);
                        int leftOfCenter3 = Math.max(0, leftOfCenter - itemPaddingSize2);
                        ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
                        int paddingLeft2 = View.MeasureSpec.makeMeasureSpec(availableWidth4, Integer.MIN_VALUE);
                        int paddingRight2 = View.MeasureSpec.makeMeasureSpec(height, 1073741824);
                        scrollingTabContainerView.measure(paddingLeft2, paddingRight2);
                        int tabWidth = this.mTabScrollView.getMeasuredWidth();
                        availableWidth = Math.max(0, availableWidth4 - tabWidth);
                        leftOfCenter = Math.max(0, leftOfCenter3 - tabWidth);
                        break;
                    }
            }
            int leftOfCenter4 = leftOfCenter;
            customView = null;
            if (this.mExpandedActionView == null) {
                customView = this.mExpandedActionView;
            } else if ((this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
                customView = this.mCustomNavView;
            }
            if (customView == null) {
                ViewGroup.LayoutParams lp = generateLayoutParams(customView.getLayoutParams());
                ActionBar.LayoutParams ablp = lp instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) lp : null;
                int verticalMargin = 0;
                if (ablp == null) {
                    horizontalMargin = 0;
                } else {
                    int i = ablp.leftMargin;
                    int horizontalMargin2 = ablp.rightMargin;
                    int horizontalMargin3 = horizontalMargin2 + i;
                    int i2 = ablp.topMargin;
                    int horizontalMargin4 = ablp.bottomMargin;
                    verticalMargin = i2 + horizontalMargin4;
                    horizontalMargin = horizontalMargin3;
                }
                if (this.mContentHeight <= 0) {
                    customNavHeightMode = Integer.MIN_VALUE;
                } else {
                    customNavHeightMode = lp.height != -2 ? 1073741824 : Integer.MIN_VALUE;
                }
                int customNavHeight = Math.max(0, (lp.height >= 0 ? Math.min(lp.height, height) : height) - verticalMargin);
                int customNavWidthMode = lp.width != -2 ? 1073741824 : Integer.MIN_VALUE;
                int customNavWidth = Math.max(0, (lp.width >= 0 ? Math.min(lp.width, availableWidth) : availableWidth) - horizontalMargin);
                int hgrav = (ablp != null ? ablp.gravity : DEFAULT_CUSTOM_GRAVITY) & 7;
                if (hgrav == 1 && lp.width == -1) {
                    customNavWidth = Math.min(leftOfCenter4, rightOfCenter2) * 2;
                }
                customView.measure(View.MeasureSpec.makeMeasureSpec(customNavWidth, customNavWidthMode), View.MeasureSpec.makeMeasureSpec(customNavHeight, customNavHeightMode));
                availableWidth -= horizontalMargin + customView.getMeasuredWidth();
            }
            int paddedViewHeight = 0;
            measureChildView(this.mUpGoerFive, availableWidth + homeWidth, View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824), 0);
            if (this.mTitleLayout != null) {
                Math.max(0, leftOfCenter4 - this.mTitleLayout.getMeasuredWidth());
            }
            if (this.mContentHeight > 0) {
                int measuredHeight = 0;
                while (true) {
                    int i3 = paddedViewHeight;
                    if (i3 < childCount) {
                        View v = getChildAt(i3);
                        int paddedViewHeight2 = v.getMeasuredHeight() + verticalPadding;
                        if (paddedViewHeight2 > measuredHeight) {
                            measuredHeight = paddedViewHeight2;
                        }
                        paddedViewHeight = i3 + 1;
                    } else {
                        setMeasuredDimension(contentWidth, measuredHeight);
                    }
                }
            } else {
                setMeasuredDimension(contentWidth, maxHeight);
            }
            if (this.mContextView != null) {
                this.mContextView.setContentHeight(getMeasuredHeight());
            }
            if (this.mProgressView == null && this.mProgressView.getVisibility() != 8) {
                this.mProgressView.measure(View.MeasureSpec.makeMeasureSpec(contentWidth - (this.mProgressBarPadding * 2), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Integer.MIN_VALUE));
                return;
            }
        }
        int leftOfCenter42 = leftOfCenter;
        customView = null;
        if (this.mExpandedActionView == null) {
        }
        if (customView == null) {
        }
        int paddedViewHeight3 = 0;
        measureChildView(this.mUpGoerFive, availableWidth + homeWidth, View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824), 0);
        if (this.mTitleLayout != null) {
        }
        if (this.mContentHeight > 0) {
        }
        if (this.mContextView != null) {
        }
        if (this.mProgressView == null) {
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x;
        int topMargin;
        int x2;
        int hgravity;
        int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
        if (contentHeight <= 0) {
            return;
        }
        boolean isLayoutRtl = isLayoutRtl();
        int direction = isLayoutRtl ? 1 : -1;
        int menuStart = isLayoutRtl ? getPaddingLeft() : (r - l) - getPaddingRight();
        int x3 = isLayoutRtl ? (r - l) - getPaddingRight() : getPaddingLeft();
        int y = getPaddingTop();
        HomeView homeLayout = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        boolean showTitle = (this.mTitleLayout == null || this.mTitleLayout.getVisibility() == 8 || (this.mDisplayOptions & 8) == 0) ? false : true;
        int startOffset = 0;
        if (homeLayout.getParent() == this.mUpGoerFive) {
            if (homeLayout.getVisibility() != 8) {
                startOffset = homeLayout.getStartOffset();
            } else if (showTitle) {
                startOffset = homeLayout.getUpWidth();
            }
        }
        int startOffset2 = startOffset;
        int x4 = next(x3 + positionChild(this.mUpGoerFive, next(x3, startOffset2, isLayoutRtl), y, contentHeight, isLayoutRtl), startOffset2, isLayoutRtl);
        if (this.mExpandedActionView == null) {
            switch (this.mNavigationMode) {
                case 1:
                    if (this.mListNavLayout != null) {
                        if (showTitle) {
                            x4 = next(x4, this.mItemPadding, isLayoutRtl);
                        }
                        int x5 = x4;
                        x4 = next(x5 + positionChild(this.mListNavLayout, x5, y, contentHeight, isLayoutRtl), this.mItemPadding, isLayoutRtl);
                        break;
                    }
                    break;
                case 2:
                    if (this.mTabScrollView != null) {
                        if (showTitle) {
                            x4 = next(x4, this.mItemPadding, isLayoutRtl);
                        }
                        int x6 = x4;
                        x4 = next(x6 + positionChild(this.mTabScrollView, x6, y, contentHeight, isLayoutRtl), this.mItemPadding, isLayoutRtl);
                        break;
                    }
                    break;
            }
        }
        int x7 = x4;
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            x = x7;
            positionChild(this.mMenuView, menuStart, y, contentHeight, !isLayoutRtl);
            menuStart += this.mMenuView.getMeasuredWidth() * direction;
        } else {
            x = x7;
        }
        if (this.mIndeterminateProgressView != null && this.mIndeterminateProgressView.getVisibility() != 8) {
            positionChild(this.mIndeterminateProgressView, menuStart, y, contentHeight, !isLayoutRtl);
            menuStart += this.mIndeterminateProgressView.getMeasuredWidth() * direction;
        }
        View customView = null;
        if (this.mExpandedActionView != null) {
            customView = this.mExpandedActionView;
        } else if ((this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
            customView = this.mCustomNavView;
        }
        if (customView != null) {
            int layoutDirection = getLayoutDirection();
            ViewGroup.LayoutParams lp = customView.getLayoutParams();
            ActionBar.LayoutParams ablp = lp instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) lp : null;
            int gravity = ablp != null ? ablp.gravity : DEFAULT_CUSTOM_GRAVITY;
            int navWidth = customView.getMeasuredWidth();
            int topMargin2 = 0;
            if (ablp == null) {
                topMargin = x;
                x2 = 0;
            } else {
                int x8 = next(x, ablp.getMarginStart(), isLayoutRtl);
                menuStart += ablp.getMarginEnd() * direction;
                int topMargin3 = ablp.topMargin;
                x2 = ablp.bottomMargin;
                topMargin2 = topMargin3;
                topMargin = x8;
            }
            int hgravity2 = gravity & 8388615;
            if (hgravity2 == 1) {
                int hgravity3 = hgravity2;
                int centeredLeft = ((this.mRight - this.mLeft) - navWidth) / 2;
                if (isLayoutRtl) {
                    int centeredStart = centeredLeft + navWidth;
                    if (centeredStart <= topMargin) {
                        if (centeredLeft < menuStart) {
                            hgravity3 = 3;
                        }
                    } else {
                        hgravity3 = 5;
                    }
                } else {
                    int centeredEnd = centeredLeft + navWidth;
                    if (centeredLeft >= topMargin) {
                        if (centeredEnd > menuStart) {
                            hgravity = 5;
                        }
                    } else {
                        hgravity = 3;
                    }
                }
                hgravity = hgravity3;
            } else {
                hgravity = gravity == 0 ? 8388611 : hgravity2;
            }
            int xpos = 0;
            int absoluteGravity = Gravity.getAbsoluteGravity(hgravity, layoutDirection);
            if (absoluteGravity == 1) {
                xpos = ((this.mRight - this.mLeft) - navWidth) / 2;
            } else if (absoluteGravity == 3) {
                xpos = isLayoutRtl ? menuStart : topMargin;
            } else if (absoluteGravity == 5) {
                xpos = isLayoutRtl ? topMargin - navWidth : menuStart - navWidth;
            }
            int vgravity = gravity & 112;
            if (gravity == 0) {
                vgravity = 16;
            }
            int ypos = 0;
            if (vgravity == 16) {
                int paddedTop = getPaddingTop();
                int vgravity2 = this.mBottom;
                int bottomMargin = this.mTop;
                int paddedBottom = (vgravity2 - bottomMargin) - getPaddingBottom();
                ypos = ((paddedBottom - paddedTop) - customView.getMeasuredHeight()) / 2;
            } else if (vgravity == 48) {
                ypos = getPaddingTop() + topMargin2;
            } else if (vgravity == 80) {
                ypos = ((getHeight() - getPaddingBottom()) - customView.getMeasuredHeight()) - x2;
            }
            int customWidth = customView.getMeasuredWidth();
            customView.layout(xpos, ypos, xpos + customWidth, customView.getMeasuredHeight() + ypos);
            next(topMargin, customWidth, isLayoutRtl);
        }
        if (this.mProgressView != null) {
            this.mProgressView.bringToFront();
            int halfProgressHeight = this.mProgressView.getMeasuredHeight() / 2;
            this.mProgressView.layout(this.mProgressBarPadding, -halfProgressHeight, this.mProgressBarPadding + this.mProgressView.getMeasuredWidth(), halfProgressHeight);
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ActionBar.LayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp == null) {
            return generateDefaultLayoutParams();
        }
        return lp;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable p) {
        MenuItem item;
        SavedState state = (SavedState) p;
        super.onRestoreInstanceState(state.getSuperState());
        if (state.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && this.mOptionsMenu != null && (item = this.mOptionsMenu.findItem(state.expandedMenuItemId)) != null) {
            item.expandActionView();
        }
        if (state.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(Drawable indicator) {
        this.mHomeLayout.setUpIndicator(indicator);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationIcon(Drawable icon) {
        this.mHomeLayout.setDefaultUpIndicator(icon);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(int resId) {
        this.mHomeLayout.setUpIndicator(resId);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(CharSequence description) {
        this.mHomeDescription = description;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(int resId) {
        this.mHomeDescriptionRes = resId;
        this.mHomeDescription = resId != 0 ? getResources().getText(resId) : null;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationContentDescription(int defaultNavigationContentDescription) {
        if (this.mDefaultUpDescription == defaultNavigationContentDescription) {
            return;
        }
        this.mDefaultUpDescription = defaultNavigationContentDescription;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuCallbacks(MenuPresenter.Callback presenterCallback, MenuBuilder.Callback menuBuilderCallback) {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.setCallback(presenterCallback);
        }
        if (this.mOptionsMenu != null) {
            this.mOptionsMenu.setCallback(menuBuilderCallback);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public Menu getMenu() {
        return this.mOptionsMenu;
    }

    /* loaded from: classes4.dex */
    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.internal.widget.ActionBarView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int expandedMenuItemId;
        boolean isOverflowOpen;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.expandedMenuItemId = in.readInt();
            this.isOverflowOpen = in.readInt() != 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.p007os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.expandedMenuItemId);
            out.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

    /* loaded from: classes4.dex */
    private static class HomeView extends FrameLayout {
        private static final long DEFAULT_TRANSITION_DURATION = 150;
        private Drawable mDefaultUpIndicator;
        private ImageView mIconView;
        private int mStartOffset;
        private Drawable mUpIndicator;
        private int mUpIndicatorRes;
        private ImageView mUpView;
        private int mUpWidth;

        public HomeView(Context context) {
            this(context, null);
        }

        public HomeView(Context context, AttributeSet attrs) {
            super(context, attrs);
            LayoutTransition t = getLayoutTransition();
            if (t != null) {
                t.setDuration(DEFAULT_TRANSITION_DURATION);
            }
        }

        public void setShowUp(boolean isUp) {
            this.mUpView.setVisibility(isUp ? 0 : 8);
        }

        public void setShowIcon(boolean showIcon) {
            this.mIconView.setVisibility(showIcon ? 0 : 8);
        }

        public void setIcon(Drawable icon) {
            this.mIconView.setImageDrawable(icon);
        }

        public void setUpIndicator(Drawable d) {
            this.mUpIndicator = d;
            this.mUpIndicatorRes = 0;
            updateUpIndicator();
        }

        public void setDefaultUpIndicator(Drawable d) {
            this.mDefaultUpIndicator = d;
            updateUpIndicator();
        }

        public void setUpIndicator(int resId) {
            this.mUpIndicatorRes = resId;
            this.mUpIndicator = null;
            updateUpIndicator();
        }

        private void updateUpIndicator() {
            if (this.mUpIndicator != null) {
                this.mUpView.setImageDrawable(this.mUpIndicator);
            } else if (this.mUpIndicatorRes != 0) {
                this.mUpView.setImageDrawable(getContext().getDrawable(this.mUpIndicatorRes));
            } else {
                this.mUpView.setImageDrawable(this.mDefaultUpIndicator);
            }
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            if (this.mUpIndicatorRes != 0) {
                updateUpIndicator();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
            onPopulateAccessibilityEvent(event);
            return true;
        }

        @Override // android.view.View
        public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
            super.onPopulateAccessibilityEventInternal(event);
            CharSequence cdesc = getContentDescription();
            if (!TextUtils.isEmpty(cdesc)) {
                event.getText().add(cdesc);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchHoverEvent(MotionEvent event) {
            return onHoverEvent(event);
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            this.mUpView = (ImageView) findViewById(C3132R.C3134id.f2474up);
            this.mIconView = (ImageView) findViewById(16908332);
            this.mDefaultUpIndicator = this.mUpView.getDrawable();
        }

        public int getStartOffset() {
            if (this.mUpView.getVisibility() == 8) {
                return this.mStartOffset;
            }
            return 0;
        }

        public int getUpWidth() {
            return this.mUpWidth;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            measureChildWithMargins(this.mUpView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            FrameLayout.LayoutParams upLp = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
            int upMargins = upLp.leftMargin + upLp.rightMargin;
            this.mUpWidth = this.mUpView.getMeasuredWidth();
            this.mStartOffset = this.mUpWidth + upMargins;
            int width = this.mUpView.getVisibility() == 8 ? 0 : this.mStartOffset;
            int height = upLp.topMargin + this.mUpView.getMeasuredHeight() + upLp.bottomMargin;
            if (this.mIconView.getVisibility() != 8) {
                measureChildWithMargins(this.mIconView, widthMeasureSpec, width, heightMeasureSpec, 0);
                FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
                width += iconLp.leftMargin + this.mIconView.getMeasuredWidth() + iconLp.rightMargin;
                height = Math.max(height, iconLp.topMargin + this.mIconView.getMeasuredHeight() + iconLp.bottomMargin);
            } else if (upMargins < 0) {
                width -= upMargins;
            }
            int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (widthMode != Integer.MIN_VALUE) {
                if (widthMode == 1073741824) {
                    width = widthSize;
                }
            } else {
                width = Math.min(width, widthSize);
            }
            if (heightMode != Integer.MIN_VALUE) {
                if (heightMode == 1073741824) {
                    height = heightSize;
                }
            } else {
                height = Math.min(height, heightSize);
            }
            setMeasuredDimension(width, height);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int l2;
            int r2;
            int iconLeft;
            int iconRight;
            int upRight;
            int upLeft;
            int vCenter = (b - t) / 2;
            boolean isLayoutRtl = isLayoutRtl();
            int width = getWidth();
            int upOffset = 0;
            if (this.mUpView.getVisibility() != 8) {
                FrameLayout.LayoutParams upLp = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
                int upHeight = this.mUpView.getMeasuredHeight();
                int upWidth = this.mUpView.getMeasuredWidth();
                upOffset = upLp.leftMargin + upWidth + upLp.rightMargin;
                int upTop = vCenter - (upHeight / 2);
                int upBottom = upTop + upHeight;
                if (isLayoutRtl) {
                    upRight = width;
                    upLeft = upRight - upWidth;
                    r2 = r - upOffset;
                    l2 = l;
                } else {
                    upRight = upWidth;
                    upLeft = 0;
                    l2 = l + upOffset;
                    r2 = r;
                }
                this.mUpView.layout(upLeft, upTop, upRight, upBottom);
            } else {
                l2 = l;
                r2 = r;
            }
            FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int iconHeight = this.mIconView.getMeasuredHeight();
            int iconWidth = this.mIconView.getMeasuredWidth();
            int hCenter = (r2 - l2) / 2;
            int iconTop = Math.max(iconLp.topMargin, vCenter - (iconHeight / 2));
            int iconBottom = iconTop + iconHeight;
            int marginStart = iconLp.getMarginStart();
            int delta = Math.max(marginStart, hCenter - (iconWidth / 2));
            if (isLayoutRtl) {
                iconRight = (width - upOffset) - delta;
                iconLeft = iconRight - iconWidth;
            } else {
                iconLeft = upOffset + delta;
                iconRight = iconLeft + iconWidth;
            }
            int iconLeft2 = iconLeft;
            this.mIconView.layout(iconLeft2, iconTop, iconRight, iconBottom);
        }
    }

    /* loaded from: classes4.dex */
    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menu) {
            if (this.mMenu != null && this.mCurrentExpandedItem != null) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menu;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup root) {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean cleared) {
            if (this.mCurrentExpandedItem != null) {
                boolean found = false;
                if (this.mMenu != null) {
                    int count = this.mMenu.size();
                    int i = 0;
                    while (true) {
                        if (i >= count) {
                            break;
                        }
                        MenuItem item = this.mMenu.getItem(i);
                        if (item != this.mCurrentExpandedItem) {
                            i++;
                        } else {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback cb) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
            ActionBarView.this.mExpandedActionView = item.getActionView();
            ActionBarView.this.mExpandedHomeLayout.setIcon(ActionBarView.this.mIcon.getConstantState().newDrawable(ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = item;
            if (ActionBarView.this.mExpandedActionView.getParent() != ActionBarView.this) {
                ActionBarView.this.addView(ActionBarView.this.mExpandedActionView);
            }
            if (ActionBarView.this.mExpandedHomeLayout.getParent() != ActionBarView.this.mUpGoerFive) {
                ActionBarView.this.mUpGoerFive.addView(ActionBarView.this.mExpandedHomeLayout);
            }
            ActionBarView.this.mHomeLayout.setVisibility(8);
            if (ActionBarView.this.mTitleLayout != null) {
                ActionBarView.this.mTitleLayout.setVisibility(8);
            }
            if (ActionBarView.this.mTabScrollView != null) {
                ActionBarView.this.mTabScrollView.setVisibility(8);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            ActionBarView.this.setHomeButtonEnabled(false, false);
            ActionBarView.this.requestLayout();
            item.setActionViewExpanded(true);
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewCollapsed();
            }
            ActionBarView.this.removeView(ActionBarView.this.mExpandedActionView);
            ActionBarView.this.mUpGoerFive.removeView(ActionBarView.this.mExpandedHomeLayout);
            ActionBarView.this.mExpandedActionView = null;
            if ((ActionBarView.this.mDisplayOptions & 2) != 0) {
                ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (ActionBarView.this.mTitleLayout == null) {
                    ActionBarView.this.initTitle();
                } else {
                    ActionBarView.this.mTitleLayout.setVisibility(0);
                }
            }
            if (ActionBarView.this.mTabScrollView != null) {
                ActionBarView.this.mTabScrollView.setVisibility(0);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            ActionBarView.this.setHomeButtonEnabled(ActionBarView.this.mWasHomeEnabled);
            ActionBarView.this.requestLayout();
            item.setActionViewExpanded(false);
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable state) {
        }
    }
}
