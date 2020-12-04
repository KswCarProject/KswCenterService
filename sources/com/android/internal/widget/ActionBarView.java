package com.android.internal.widget;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
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
import com.android.internal.R;
import com.android.internal.view.menu.ActionMenuItem;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;

public class ActionBarView extends AbsActionBarView implements DecorToolbar {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 63;
    private static final String TAG = "ActionBarView";
    private ActionBarContextView mContextView;
    /* access modifiers changed from: private */
    public View mCustomNavView;
    private int mDefaultUpDescription = R.string.action_bar_up_description;
    /* access modifiers changed from: private */
    public int mDisplayOptions = -1;
    View mExpandedActionView;
    private final View.OnClickListener mExpandedActionViewUpListener = new View.OnClickListener() {
        public void onClick(View v) {
            MenuItemImpl item = ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
            if (item != null) {
                item.collapseActionView();
            }
        }
    };
    /* access modifiers changed from: private */
    public HomeView mExpandedHomeLayout;
    /* access modifiers changed from: private */
    public ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private CharSequence mHomeDescription;
    private int mHomeDescriptionRes;
    /* access modifiers changed from: private */
    public HomeView mHomeLayout;
    /* access modifiers changed from: private */
    public Drawable mIcon;
    private boolean mIncludeTabs;
    private final int mIndeterminateProgressStyle;
    private ProgressBar mIndeterminateProgressView;
    private boolean mIsCollapsible;
    private int mItemPadding;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    /* access modifiers changed from: private */
    public ActionMenuItem mLogoNavItem;
    /* access modifiers changed from: private */
    public boolean mMenuPrepared;
    private AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    private int mNavigationMode;
    private MenuBuilder mOptionsMenu;
    private int mProgressBarPadding;
    private final int mProgressStyle;
    private ProgressBar mProgressView;
    /* access modifiers changed from: private */
    public Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private CharSequence mSubtitle;
    private final int mSubtitleStyleRes;
    private TextView mSubtitleView;
    /* access modifiers changed from: private */
    public ScrollingTabContainerView mTabScrollView;
    private Runnable mTabSelector;
    private CharSequence mTitle;
    /* access modifiers changed from: private */
    public LinearLayout mTitleLayout;
    private final int mTitleStyleRes;
    private TextView mTitleView;
    private final View.OnClickListener mUpClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (ActionBarView.this.mMenuPrepared) {
                ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.this.mLogoNavItem);
            }
        }
    };
    /* access modifiers changed from: private */
    public ViewGroup mUpGoerFive;
    private boolean mUserTitle;
    /* access modifiers changed from: private */
    public boolean mWasHomeEnabled;
    Window.Callback mWindowCallback;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar, 16843470, 0);
        this.mNavigationMode = a.getInt(7, 0);
        this.mTitle = a.getText(5);
        this.mSubtitle = a.getText(9);
        this.mLogo = a.getDrawable(6);
        this.mIcon = a.getDrawable(0);
        LayoutInflater inflater = LayoutInflater.from(context);
        int homeResId = a.getResourceId(16, R.layout.action_bar_home);
        this.mUpGoerFive = (ViewGroup) inflater.inflate((int) R.layout.action_bar_up_container, (ViewGroup) this, false);
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
        int i = customNavId;
        this.mLogoNavItem = new ActionMenuItem(context, 0, 16908332, 0, 0, this.mTitle);
        this.mUpGoerFive.setOnClickListener(this.mUpClickListener);
        this.mUpGoerFive.setClickable(true);
        this.mUpGoerFive.setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
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

    public void setWindowCallback(Window.Callback cb) {
        this.mWindowCallback = cb;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mTabSelector);
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void initProgress() {
        this.mProgressView = new ProgressBar(this.mContext, (AttributeSet) null, 0, this.mProgressStyle);
        this.mProgressView.setId(R.id.progress_horizontal);
        this.mProgressView.setMax(10000);
        this.mProgressView.setVisibility(8);
        addView(this.mProgressView);
    }

    public void initIndeterminateProgress() {
        this.mIndeterminateProgressView = new ProgressBar(this.mContext, (AttributeSet) null, 0, this.mIndeterminateProgressStyle);
        this.mIndeterminateProgressView.setId(R.id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        addView(this.mIndeterminateProgressView);
    }

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
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.action_bar_expanded_action_views_exclusive));
                } else {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                }
            }
            super.setSplitToolbar(splitActionBar);
        }
    }

    public boolean isSplit() {
        return this.mSplitActionBar;
    }

    public boolean canSplit() {
        return true;
    }

    public boolean hasEmbeddedTabs() {
        return this.mIncludeTabs;
    }

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

    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    public void setMenu(Menu menu, MenuPresenter.Callback cb) {
        ActionMenuView menuView;
        ViewGroup oldParent;
        if (menu != this.mOptionsMenu) {
            if (this.mOptionsMenu != null) {
                this.mOptionsMenu.removeMenuPresenter(this.mActionMenuPresenter);
                this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
            }
            MenuBuilder builder = (MenuBuilder) menu;
            this.mOptionsMenu = builder;
            if (!(this.mMenuView == null || (oldParent = (ViewGroup) this.mMenuView.getParent()) == null)) {
                oldParent.removeView(this.mMenuView);
            }
            if (this.mActionMenuPresenter == null) {
                this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
                this.mActionMenuPresenter.setCallback(cb);
                this.mActionMenuPresenter.setId(R.id.action_menu_presenter);
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
            if (!this.mSplitActionBar) {
                this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.action_bar_expanded_action_views_exclusive));
                configPresenters(builder);
                menuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                ViewGroup oldParent2 = (ViewGroup) menuView.getParent();
                if (!(oldParent2 == null || oldParent2 == this)) {
                    oldParent2.removeView(menuView);
                }
                addView((View) menuView, layoutParams);
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
                    if (!(oldParent3 == null || oldParent3 == this.mSplitView)) {
                        oldParent3.removeView(menuView);
                    }
                    menuView.setVisibility(getAnimatedVisibility());
                    this.mSplitView.addView((View) menuView, layoutParams);
                } else {
                    menuView.setLayoutParams(layoutParams);
                }
            }
            this.mMenuView = menuView;
        }
    }

    private void configPresenters(MenuBuilder builder) {
        if (builder != null) {
            builder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            builder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
            return;
        }
        this.mActionMenuPresenter.initForMenu(this.mPopupContext, (MenuBuilder) null);
        this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, (MenuBuilder) null);
        this.mActionMenuPresenter.updateMenuView(true);
        this.mExpandedMenuPresenter.updateMenuView(true);
    }

    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void collapseActionView() {
        MenuItemImpl item;
        if (this.mExpandedMenuPresenter == null) {
            item = null;
        } else {
            item = this.mExpandedMenuPresenter.mCurrentExpandedItem;
        }
        if (item != null) {
            item.collapseActionView();
        }
    }

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

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setTitle(CharSequence title) {
        this.mUserTitle = true;
        setTitleImpl(title);
    }

    public void setWindowTitle(CharSequence title) {
        if (!this.mUserTitle) {
            setTitleImpl(title);
        }
    }

    private void setTitleImpl(CharSequence title) {
        this.mTitle = title;
        if (this.mTitleView != null) {
            this.mTitleView.setText(title);
            int i = 0;
            boolean visible = this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle));
            LinearLayout linearLayout = this.mTitleLayout;
            if (!visible) {
                i = 8;
            }
            linearLayout.setVisibility(i);
        }
        if (this.mLogoNavItem != null) {
            this.mLogoNavItem.setTitle(title);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mSubtitle = subtitle;
        if (this.mSubtitleView != null) {
            this.mSubtitleView.setText(subtitle);
            int i = 8;
            this.mSubtitleView.setVisibility(subtitle != null ? 0 : 8);
            boolean visible = this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle));
            LinearLayout linearLayout = this.mTitleLayout;
            if (visible) {
                i = 0;
            }
            linearLayout.setVisibility(i);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    public void setHomeButtonEnabled(boolean enable) {
        setHomeButtonEnabled(enable, true);
    }

    /* access modifiers changed from: private */
    public void setHomeButtonEnabled(boolean enable, boolean recordState) {
        if (recordState) {
            this.mWasHomeEnabled = enable;
        }
        if (this.mExpandedActionView == null) {
            this.mUpGoerFive.setEnabled(enable);
            this.mUpGoerFive.setFocusable(enable);
            updateHomeAccessibility(enable);
        }
    }

    private void updateHomeAccessibility(boolean homeEnabled) {
        if (!homeEnabled) {
            this.mUpGoerFive.setContentDescription((CharSequence) null);
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
            homeDesc = this.mContext.getResources().getText(R.string.action_bar_home_description);
        }
        CharSequence title = getTitle();
        CharSequence subtitle = getSubtitle();
        if (TextUtils.isEmpty(title)) {
            return homeDesc;
        }
        if (!TextUtils.isEmpty(subtitle)) {
            return getResources().getString(R.string.action_bar_home_subtitle_description_format, title, subtitle, homeDesc);
        }
        return getResources().getString(R.string.action_bar_home_description_format, title, homeDesc);
    }

    public void setDisplayOptions(int options) {
        int i = -1;
        if (this.mDisplayOptions != -1) {
            i = options ^ this.mDisplayOptions;
        }
        int flagsChanged = i;
        this.mDisplayOptions = options;
        if ((flagsChanged & 63) != 0) {
            if ((flagsChanged & 4) != 0) {
                boolean setUp = (options & 4) != 0;
                this.mHomeLayout.setShowUp(setUp);
                if (setUp) {
                    setHomeButtonEnabled(true);
                }
            }
            if (flagsChanged != false && true) {
                this.mHomeLayout.setIcon(this.mLogo != null && (options & 1) != 0 ? this.mLogo : this.mIcon);
            }
            if (flagsChanged != false && true) {
                if ((options & 8) != 0) {
                    initTitle();
                } else {
                    this.mUpGoerFive.removeView(this.mTitleLayout);
                }
            }
            boolean showHome = (options & 2) != 0;
            boolean titleUp = !showHome && ((this.mDisplayOptions & 4) != 0);
            this.mHomeLayout.setShowIcon(showHome);
            this.mHomeLayout.setVisibility(((showHome || titleUp) && this.mExpandedActionView == null) ? 0 : 8);
            if (!((flagsChanged & 16) == 0 || this.mCustomNavView == null)) {
                if ((options & 16) != 0) {
                    addView(this.mCustomNavView);
                } else {
                    removeView(this.mCustomNavView);
                }
            }
            if (!(this.mTitleLayout == null || (flagsChanged & 32) == 0)) {
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

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
        if (icon != null && ((this.mDisplayOptions & 1) == 0 || this.mLogo == null)) {
            this.mHomeLayout.setIcon(icon);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    public void setIcon(int resId) {
        setIcon(resId != 0 ? this.mContext.getDrawable(resId) : null);
    }

    public boolean hasIcon() {
        return this.mIcon != null;
    }

    public void setLogo(Drawable logo) {
        this.mLogo = logo;
        if (logo != null && (this.mDisplayOptions & 1) != 0) {
            this.mHomeLayout.setIcon(logo);
        }
    }

    public void setLogo(int resId) {
        setLogo(resId != 0 ? this.mContext.getDrawable(resId) : null);
    }

    public boolean hasLogo() {
        return this.mLogo != null;
    }

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
            }
            switch (mode) {
                case 1:
                    if (this.mSpinner == null) {
                        this.mSpinner = new Spinner(this.mContext, (AttributeSet) null, 16843479);
                        this.mSpinner.setId(R.id.action_bar_spinner);
                        this.mListNavLayout = new LinearLayout(this.mContext, (AttributeSet) null, 16843508);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -1);
                        params.gravity = 17;
                        this.mListNavLayout.addView((View) this.mSpinner, (ViewGroup.LayoutParams) params);
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
            }
            this.mNavigationMode = mode;
            requestLayout();
        }
    }

    public void setDropdownParams(SpinnerAdapter adapter, AdapterView.OnItemSelectedListener l) {
        this.mSpinnerAdapter = adapter;
        this.mNavItemSelectedListener = l;
        if (this.mSpinner != null) {
            this.mSpinner.setAdapter(adapter);
            this.mSpinner.setOnItemSelectedListener(l);
        }
    }

    public int getDropdownItemCount() {
        if (this.mSpinnerAdapter != null) {
            return this.mSpinnerAdapter.getCount();
        }
        return 0;
    }

    public void setDropdownSelectedPosition(int position) {
        this.mSpinner.setSelection(position);
    }

    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    public View getCustomView() {
        return this.mCustomNavView;
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    public ViewGroup getViewGroup() {
        return this;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams((int) DEFAULT_CUSTOM_GRAVITY);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        ViewParent parent;
        super.onFinishInflate();
        this.mUpGoerFive.addView((View) this.mHomeLayout, 0);
        addView(this.mUpGoerFive);
        if (this.mCustomNavView != null && (this.mDisplayOptions & 16) != 0 && (parent = this.mCustomNavView.getParent()) != this) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mCustomNavView);
            }
            addView(this.mCustomNavView);
        }
    }

    /* access modifiers changed from: private */
    public void initTitle() {
        if (this.mTitleLayout == null) {
            this.mTitleLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate((int) R.layout.action_bar_title_item, (ViewGroup) this, false);
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_subtitle);
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

    public void setCollapsible(boolean collapsible) {
        this.mIsCollapsible = collapsible;
    }

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

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r40, int r41) {
        /*
            r39 = this;
            r0 = r39
            int r1 = r39.getChildCount()
            boolean r2 = r0.mIsCollapsible
            r3 = 8
            r4 = 0
            if (r2 == 0) goto L_0x0052
            r2 = 0
            r5 = r2
            r2 = r4
        L_0x0010:
            if (r2 >= r1) goto L_0x0031
            android.view.View r6 = r0.getChildAt(r2)
            int r7 = r6.getVisibility()
            if (r7 == r3) goto L_0x002e
            android.widget.ActionMenuView r7 = r0.mMenuView
            if (r6 != r7) goto L_0x0028
            android.widget.ActionMenuView r7 = r0.mMenuView
            int r7 = r7.getChildCount()
            if (r7 == 0) goto L_0x002e
        L_0x0028:
            android.view.ViewGroup r7 = r0.mUpGoerFive
            if (r6 == r7) goto L_0x002e
            int r5 = r5 + 1
        L_0x002e:
            int r2 = r2 + 1
            goto L_0x0010
        L_0x0031:
            android.view.ViewGroup r2 = r0.mUpGoerFive
            int r2 = r2.getChildCount()
            r6 = r5
            r5 = r4
        L_0x0039:
            if (r5 >= r2) goto L_0x004c
            android.view.ViewGroup r7 = r0.mUpGoerFive
            android.view.View r7 = r7.getChildAt(r5)
            int r8 = r7.getVisibility()
            if (r8 == r3) goto L_0x0049
            int r6 = r6 + 1
        L_0x0049:
            int r5 = r5 + 1
            goto L_0x0039
        L_0x004c:
            if (r6 != 0) goto L_0x0052
            r0.setMeasuredDimension(r4, r4)
            return
        L_0x0052:
            int r2 = android.view.View.MeasureSpec.getMode(r40)
            r5 = 1073741824(0x40000000, float:2.0)
            if (r2 != r5) goto L_0x0353
            int r6 = android.view.View.MeasureSpec.getMode(r41)
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r6 != r7) goto L_0x0330
            int r8 = android.view.View.MeasureSpec.getSize(r40)
            int r9 = r0.mContentHeight
            if (r9 < 0) goto L_0x006d
            int r9 = r0.mContentHeight
            goto L_0x0071
        L_0x006d:
            int r9 = android.view.View.MeasureSpec.getSize(r41)
        L_0x0071:
            int r10 = r39.getPaddingTop()
            int r11 = r39.getPaddingBottom()
            int r10 = r10 + r11
            int r11 = r39.getPaddingLeft()
            int r12 = r39.getPaddingRight()
            int r13 = r9 - r10
            int r14 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r7)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r5)
            int r16 = r8 - r11
            int r4 = r16 - r12
            int r16 = r4 / 2
            r17 = r16
            android.widget.LinearLayout r5 = r0.mTitleLayout
            if (r5 == 0) goto L_0x00a7
            android.widget.LinearLayout r5 = r0.mTitleLayout
            int r5 = r5.getVisibility()
            if (r5 == r3) goto L_0x00a7
            int r5 = r0.mDisplayOptions
            r5 = r5 & r3
            if (r5 == 0) goto L_0x00a7
            r5 = 1
            goto L_0x00a8
        L_0x00a7:
            r5 = 0
        L_0x00a8:
            android.view.View r7 = r0.mExpandedActionView
            if (r7 == 0) goto L_0x00af
            com.android.internal.widget.ActionBarView$HomeView r7 = r0.mExpandedHomeLayout
            goto L_0x00b1
        L_0x00af:
            com.android.internal.widget.ActionBarView$HomeView r7 = r0.mHomeLayout
        L_0x00b1:
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            r18 = r2
            int r2 = r3.width
            if (r2 >= 0) goto L_0x00c4
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            int r19 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r2)
            r20 = r3
            goto L_0x00ce
        L_0x00c4:
            int r2 = r3.width
            r20 = r3
            r3 = 1073741824(0x40000000, float:2.0)
            int r19 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r3)
        L_0x00ce:
            r2 = r19
            r7.measure(r2, r15)
            r3 = 0
            r21 = r2
            int r2 = r7.getVisibility()
            r22 = r3
            r3 = 8
            if (r2 == r3) goto L_0x00e8
            android.view.ViewParent r2 = r7.getParent()
            android.view.ViewGroup r3 = r0.mUpGoerFive
            if (r2 == r3) goto L_0x00ea
        L_0x00e8:
            if (r5 == 0) goto L_0x0109
        L_0x00ea:
            int r3 = r7.getMeasuredWidth()
            int r2 = r7.getStartOffset()
            int r2 = r2 + r3
            r23 = r3
            int r3 = r4 - r2
            r24 = r4
            r4 = 0
            int r3 = java.lang.Math.max(r4, r3)
            r25 = r6
            int r6 = r3 - r2
            int r16 = java.lang.Math.max(r4, r6)
            r22 = r23
            goto L_0x010f
        L_0x0109:
            r24 = r4
            r25 = r6
            r3 = r24
        L_0x010f:
            android.widget.ActionMenuView r2 = r0.mMenuView
            if (r2 == 0) goto L_0x012e
            android.widget.ActionMenuView r2 = r0.mMenuView
            android.view.ViewParent r2 = r2.getParent()
            if (r2 != r0) goto L_0x012e
            android.widget.ActionMenuView r2 = r0.mMenuView
            r4 = 0
            int r3 = r0.measureChildView(r2, r3, r15, r4)
            android.widget.ActionMenuView r2 = r0.mMenuView
            int r2 = r2.getMeasuredWidth()
            int r2 = r17 - r2
            int r17 = java.lang.Math.max(r4, r2)
        L_0x012e:
            android.widget.ProgressBar r2 = r0.mIndeterminateProgressView
            if (r2 == 0) goto L_0x014f
            android.widget.ProgressBar r2 = r0.mIndeterminateProgressView
            int r2 = r2.getVisibility()
            r4 = 8
            if (r2 == r4) goto L_0x014f
            android.widget.ProgressBar r2 = r0.mIndeterminateProgressView
            r4 = 0
            int r3 = r0.measureChildView(r2, r3, r14, r4)
            android.widget.ProgressBar r2 = r0.mIndeterminateProgressView
            int r2 = r2.getMeasuredWidth()
            int r2 = r17 - r2
            int r17 = java.lang.Math.max(r4, r2)
        L_0x014f:
            r2 = r17
            android.view.View r4 = r0.mExpandedActionView
            if (r4 != 0) goto L_0x01ea
            int r4 = r0.mNavigationMode
            switch(r4) {
                case 1: goto L_0x01a3;
                case 2: goto L_0x015c;
                default: goto L_0x015a;
            }
        L_0x015a:
            goto L_0x01ea
        L_0x015c:
            com.android.internal.widget.ScrollingTabContainerView r4 = r0.mTabScrollView
            if (r4 == 0) goto L_0x01ea
            if (r5 == 0) goto L_0x0167
            int r4 = r0.mItemPadding
            int r4 = r4 * 2
            goto L_0x0169
        L_0x0167:
            int r4 = r0.mItemPadding
        L_0x0169:
            int r6 = r3 - r4
            r26 = r7
            r7 = 0
            int r3 = java.lang.Math.max(r7, r6)
            int r6 = r16 - r4
            int r6 = java.lang.Math.max(r7, r6)
            com.android.internal.widget.ScrollingTabContainerView r7 = r0.mTabScrollView
            r27 = r4
            r28 = r11
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r4)
            r29 = r12
            r4 = 1073741824(0x40000000, float:2.0)
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r4)
            r7.measure(r11, r12)
            com.android.internal.widget.ScrollingTabContainerView r4 = r0.mTabScrollView
            int r4 = r4.getMeasuredWidth()
            int r7 = r3 - r4
            r11 = 0
            int r3 = java.lang.Math.max(r11, r7)
            int r7 = r6 - r4
            int r16 = java.lang.Math.max(r11, r7)
            goto L_0x01f0
        L_0x01a3:
            r26 = r7
            r28 = r11
            r29 = r12
            android.widget.LinearLayout r4 = r0.mListNavLayout
            if (r4 == 0) goto L_0x01f0
            if (r5 == 0) goto L_0x01b4
            int r4 = r0.mItemPadding
            int r4 = r4 * 2
            goto L_0x01b6
        L_0x01b4:
            int r4 = r0.mItemPadding
        L_0x01b6:
            int r6 = r3 - r4
            r7 = 0
            int r3 = java.lang.Math.max(r7, r6)
            int r6 = r16 - r4
            int r6 = java.lang.Math.max(r7, r6)
            android.widget.LinearLayout r7 = r0.mListNavLayout
            r11 = -2147483648(0xffffffff80000000, float:-0.0)
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r11)
            r30 = r4
            r11 = 1073741824(0x40000000, float:2.0)
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r11)
            r7.measure(r12, r4)
            android.widget.LinearLayout r4 = r0.mListNavLayout
            int r4 = r4.getMeasuredWidth()
            int r7 = r3 - r4
            r11 = 0
            int r3 = java.lang.Math.max(r11, r7)
            int r7 = r6 - r4
            int r16 = java.lang.Math.max(r11, r7)
            goto L_0x01f0
        L_0x01ea:
            r26 = r7
            r28 = r11
            r29 = r12
        L_0x01f0:
            r4 = r16
            r6 = 0
            android.view.View r7 = r0.mExpandedActionView
            if (r7 == 0) goto L_0x01fa
            android.view.View r6 = r0.mExpandedActionView
            goto L_0x0206
        L_0x01fa:
            int r7 = r0.mDisplayOptions
            r7 = r7 & 16
            if (r7 == 0) goto L_0x0206
            android.view.View r7 = r0.mCustomNavView
            if (r7 == 0) goto L_0x0206
            android.view.View r6 = r0.mCustomNavView
        L_0x0206:
            if (r6 == 0) goto L_0x02b1
            android.view.ViewGroup$LayoutParams r7 = r6.getLayoutParams()
            android.view.ViewGroup$LayoutParams r7 = r0.generateLayoutParams((android.view.ViewGroup.LayoutParams) r7)
            boolean r11 = r7 instanceof android.app.ActionBar.LayoutParams
            if (r11 == 0) goto L_0x0218
            r11 = r7
            android.app.ActionBar$LayoutParams r11 = (android.app.ActionBar.LayoutParams) r11
            goto L_0x0219
        L_0x0218:
            r11 = 0
        L_0x0219:
            r12 = 0
            r16 = 0
            if (r11 == 0) goto L_0x0232
            r31 = r5
            int r5 = r11.leftMargin
            r32 = r12
            int r12 = r11.rightMargin
            int r12 = r12 + r5
            int r5 = r11.topMargin
            r33 = r12
            int r12 = r11.bottomMargin
            int r16 = r5 + r12
            r32 = r33
            goto L_0x0236
        L_0x0232:
            r31 = r5
            r32 = r12
        L_0x0236:
            int r5 = r0.mContentHeight
            r12 = -2
            if (r5 > 0) goto L_0x023e
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0247
        L_0x023e:
            int r5 = r7.height
            if (r5 == r12) goto L_0x0245
            r5 = 1073741824(0x40000000, float:2.0)
            goto L_0x0247
        L_0x0245:
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0247:
            int r12 = r7.height
            if (r12 < 0) goto L_0x0253
            int r12 = r7.height
            int r12 = java.lang.Math.min(r12, r13)
            goto L_0x0254
        L_0x0253:
            r12 = r13
        L_0x0254:
            int r12 = r12 - r16
            r34 = r13
            r13 = 0
            int r12 = java.lang.Math.max(r13, r12)
            int r13 = r7.width
            r35 = r14
            r14 = -2
            if (r13 == r14) goto L_0x0267
            r13 = 1073741824(0x40000000, float:2.0)
            goto L_0x0269
        L_0x0267:
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0269:
            int r14 = r7.width
            if (r14 < 0) goto L_0x0275
            int r14 = r7.width
            int r14 = java.lang.Math.min(r14, r3)
            goto L_0x0276
        L_0x0275:
            r14 = r3
        L_0x0276:
            int r14 = r14 - r32
            r36 = r15
            r15 = 0
            int r14 = java.lang.Math.max(r15, r14)
            if (r11 == 0) goto L_0x0284
            int r15 = r11.gravity
            goto L_0x0287
        L_0x0284:
            r15 = 8388627(0x800013, float:1.175497E-38)
        L_0x0287:
            r15 = r15 & 7
            r37 = r11
            r11 = 1
            if (r15 != r11) goto L_0x029c
            int r11 = r7.width
            r38 = r7
            r7 = -1
            if (r11 != r7) goto L_0x029e
            int r7 = java.lang.Math.min(r4, r2)
            int r14 = r7 * 2
            goto L_0x029e
        L_0x029c:
            r38 = r7
        L_0x029e:
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r13)
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r5)
            r6.measure(r7, r11)
            int r7 = r6.getMeasuredWidth()
            int r7 = r32 + r7
            int r3 = r3 - r7
            goto L_0x02b9
        L_0x02b1:
            r31 = r5
            r34 = r13
            r35 = r14
            r36 = r15
        L_0x02b9:
            android.view.ViewGroup r5 = r0.mUpGoerFive
            int r7 = r3 + r22
            int r11 = r0.mContentHeight
            r12 = 1073741824(0x40000000, float:2.0)
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r12)
            r12 = 0
            int r3 = r0.measureChildView(r5, r7, r11, r12)
            android.widget.LinearLayout r5 = r0.mTitleLayout
            if (r5 == 0) goto L_0x02da
            android.widget.LinearLayout r5 = r0.mTitleLayout
            int r5 = r5.getMeasuredWidth()
            int r5 = r4 - r5
            int r4 = java.lang.Math.max(r12, r5)
        L_0x02da:
            int r5 = r0.mContentHeight
            if (r5 > 0) goto L_0x02f6
            r5 = 0
        L_0x02e0:
            r7 = r12
            if (r7 >= r1) goto L_0x02f2
            android.view.View r11 = r0.getChildAt(r7)
            int r12 = r11.getMeasuredHeight()
            int r12 = r12 + r10
            if (r12 <= r5) goto L_0x02ef
            r5 = r12
        L_0x02ef:
            int r12 = r7 + 1
            goto L_0x02e0
        L_0x02f2:
            r0.setMeasuredDimension(r8, r5)
            goto L_0x02f9
        L_0x02f6:
            r0.setMeasuredDimension(r8, r9)
        L_0x02f9:
            com.android.internal.widget.ActionBarContextView r5 = r0.mContextView
            if (r5 == 0) goto L_0x0306
            com.android.internal.widget.ActionBarContextView r5 = r0.mContextView
            int r7 = r39.getMeasuredHeight()
            r5.setContentHeight(r7)
        L_0x0306:
            android.widget.ProgressBar r5 = r0.mProgressView
            if (r5 == 0) goto L_0x032f
            android.widget.ProgressBar r5 = r0.mProgressView
            int r5 = r5.getVisibility()
            r7 = 8
            if (r5 == r7) goto L_0x032f
            android.widget.ProgressBar r5 = r0.mProgressView
            int r7 = r0.mProgressBarPadding
            int r7 = r7 * 2
            int r7 = r8 - r7
            r11 = 1073741824(0x40000000, float:2.0)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r11)
            int r11 = r39.getMeasuredHeight()
            r12 = -2147483648(0xffffffff80000000, float:-0.0)
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r12)
            r5.measure(r7, r11)
        L_0x032f:
            return
        L_0x0330:
            r18 = r2
            r25 = r6
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r39.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = " can only be used with android:layout_height=\"wrap_content\""
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0353:
            r18 = r2
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r39.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = " can only be used with android:layout_width=\"match_parent\" (or fill_parent)"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ActionBarView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int x;
        int topMargin;
        int x2;
        int centeredStart;
        int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
        if (contentHeight > 0) {
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
            if (this.mMenuView == null || this.mMenuView.getParent() != this) {
                x = x7;
            } else {
                x = x7;
                positionChild(this.mMenuView, menuStart, y, contentHeight, !isLayoutRtl);
                menuStart += this.mMenuView.getMeasuredWidth() * direction;
            }
            if (!(this.mIndeterminateProgressView == null || this.mIndeterminateProgressView.getVisibility() == 8)) {
                positionChild(this.mIndeterminateProgressView, menuStart, y, contentHeight, !isLayoutRtl);
                menuStart += this.mIndeterminateProgressView.getMeasuredWidth() * direction;
            }
            View customView = null;
            if (this.mExpandedActionView != null) {
                customView = this.mExpandedActionView;
            } else if (!((this.mDisplayOptions & 16) == 0 || this.mCustomNavView == null)) {
                customView = this.mCustomNavView;
            }
            if (customView != null) {
                int layoutDirection = getLayoutDirection();
                ViewGroup.LayoutParams lp = customView.getLayoutParams();
                ActionBar.LayoutParams ablp = lp instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) lp : null;
                int gravity = ablp != null ? ablp.gravity : DEFAULT_CUSTOM_GRAVITY;
                int navWidth = customView.getMeasuredWidth();
                int topMargin2 = 0;
                if (ablp != null) {
                    ViewGroup.LayoutParams layoutParams = lp;
                    int x8 = next(x, ablp.getMarginStart(), isLayoutRtl);
                    menuStart += ablp.getMarginEnd() * direction;
                    int topMargin3 = ablp.topMargin;
                    int x9 = x8;
                    x2 = ablp.bottomMargin;
                    topMargin2 = topMargin3;
                    topMargin = x9;
                } else {
                    topMargin = x;
                    x2 = 0;
                }
                ActionBar.LayoutParams layoutParams2 = ablp;
                int hgravity = gravity & 8388615;
                if (hgravity == 1) {
                    int hgravity2 = hgravity;
                    int centeredLeft = ((this.mRight - this.mLeft) - navWidth) / 2;
                    if (isLayoutRtl) {
                        int centeredStart2 = centeredLeft + navWidth;
                        int centeredEnd = centeredLeft;
                        if (centeredStart2 > topMargin) {
                            hgravity2 = 5;
                        } else {
                            int i = centeredStart2;
                            if (centeredEnd < menuStart) {
                                hgravity2 = 3;
                            }
                        }
                    } else {
                        int centeredEnd2 = centeredLeft + navWidth;
                        if (centeredLeft < topMargin) {
                            centeredStart = 3;
                        } else if (centeredEnd2 > menuStart) {
                            centeredStart = 5;
                        }
                    }
                    centeredStart = hgravity2;
                } else {
                    centeredStart = gravity == 0 ? 8388611 : hgravity;
                }
                int xpos = 0;
                int absoluteGravity = Gravity.getAbsoluteGravity(centeredStart, layoutDirection);
                int i2 = layoutDirection;
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
                int i3 = centeredStart;
                if (vgravity != 16) {
                    if (vgravity == 48) {
                        ypos = getPaddingTop() + topMargin2;
                    } else if (vgravity == 80) {
                        ypos = ((getHeight() - getPaddingBottom()) - customView.getMeasuredHeight()) - x2;
                    }
                    int bottomMargin = x2;
                } else {
                    int i4 = vgravity;
                    int i5 = x2;
                    ypos = ((((this.mBottom - this.mTop) - getPaddingBottom()) - getPaddingTop()) - customView.getMeasuredHeight()) / 2;
                }
                int paddedBottom = customView.getMeasuredWidth();
                customView.layout(xpos, ypos, xpos + paddedBottom, customView.getMeasuredHeight() + ypos);
                int x10 = next(topMargin, paddedBottom, isLayoutRtl);
            }
            if (this.mProgressView != null) {
                this.mProgressView.bringToFront();
                int halfProgressHeight = this.mProgressView.getMeasuredHeight() / 2;
                this.mProgressView.layout(this.mProgressBarPadding, -halfProgressHeight, this.mProgressBarPadding + this.mProgressView.getMeasuredWidth(), halfProgressHeight);
            }
        }
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ActionBar.LayoutParams(getContext(), attrs);
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp == null) {
            return generateDefaultLayoutParams();
        }
        return lp;
    }

    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        if (!(this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null)) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    public void onRestoreInstanceState(Parcelable p) {
        MenuItem item;
        SavedState state = (SavedState) p;
        super.onRestoreInstanceState(state.getSuperState());
        if (!(state.expandedMenuItemId == 0 || this.mExpandedMenuPresenter == null || this.mOptionsMenu == null || (item = this.mOptionsMenu.findItem(state.expandedMenuItemId)) == null)) {
            item.expandActionView();
        }
        if (state.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    public void setNavigationIcon(Drawable indicator) {
        this.mHomeLayout.setUpIndicator(indicator);
    }

    public void setDefaultNavigationIcon(Drawable icon) {
        this.mHomeLayout.setDefaultUpIndicator(icon);
    }

    public void setNavigationIcon(int resId) {
        this.mHomeLayout.setUpIndicator(resId);
    }

    public void setNavigationContentDescription(CharSequence description) {
        this.mHomeDescription = description;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    public void setNavigationContentDescription(int resId) {
        this.mHomeDescriptionRes = resId;
        this.mHomeDescription = resId != 0 ? getResources().getText(resId) : null;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    public void setDefaultNavigationContentDescription(int defaultNavigationContentDescription) {
        if (this.mDefaultUpDescription != defaultNavigationContentDescription) {
            this.mDefaultUpDescription = defaultNavigationContentDescription;
            updateHomeAccessibility(this.mUpGoerFive.isEnabled());
        }
    }

    public void setMenuCallbacks(MenuPresenter.Callback presenterCallback, MenuBuilder.Callback menuBuilderCallback) {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.setCallback(presenterCallback);
        }
        if (this.mOptionsMenu != null) {
            this.mOptionsMenu.setCallback(menuBuilderCallback);
        }
    }

    public Menu getMenu() {
        return this.mOptionsMenu;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

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

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.expandedMenuItemId);
            out.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

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
            this(context, (AttributeSet) null);
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

        /* access modifiers changed from: protected */
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            if (this.mUpIndicatorRes != 0) {
                updateUpIndicator();
            }
        }

        public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
            onPopulateAccessibilityEvent(event);
            return true;
        }

        public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
            super.onPopulateAccessibilityEventInternal(event);
            CharSequence cdesc = getContentDescription();
            if (!TextUtils.isEmpty(cdesc)) {
                event.getText().add(cdesc);
            }
        }

        public boolean dispatchHoverEvent(MotionEvent event) {
            return onHoverEvent(event);
        }

        /* access modifiers changed from: protected */
        public void onFinishInflate() {
            this.mUpView = (ImageView) findViewById(R.id.up);
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

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
            if (widthMode == Integer.MIN_VALUE) {
                width = Math.min(width, widthSize);
            } else if (widthMode == 1073741824) {
                width = widthSize;
            }
            if (heightMode == Integer.MIN_VALUE) {
                height = Math.min(height, heightSize);
            } else if (heightMode == 1073741824) {
                height = heightSize;
            }
            setMeasuredDimension(width, height);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int l, int t, int r, int b) {
            int l2;
            int r2;
            int iconLeft;
            int iconRight;
            int upLeft;
            int upRight;
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
            int iconTop = Math.max(iconLp.topMargin, vCenter - (iconHeight / 2));
            int iconBottom = iconTop + iconHeight;
            int delta = Math.max(iconLp.getMarginStart(), ((r2 - l2) / 2) - (iconWidth / 2));
            if (isLayoutRtl) {
                iconRight = (width - upOffset) - delta;
                iconLeft = iconRight - iconWidth;
            } else {
                iconLeft = upOffset + delta;
                iconRight = iconLeft + iconWidth;
            }
            this.mIconView.layout(iconLeft, iconTop, iconRight, iconBottom);
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        public void initForMenu(Context context, MenuBuilder menu) {
            if (!(this.mMenu == null || this.mCurrentExpandedItem == null)) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menu;
        }

        public MenuView getMenuView(ViewGroup root) {
            return null;
        }

        public void updateMenuView(boolean cleared) {
            if (this.mCurrentExpandedItem != null) {
                boolean found = false;
                if (this.mMenu != null) {
                    int count = this.mMenu.size();
                    int i = 0;
                    while (true) {
                        if (i >= count) {
                            break;
                        } else if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            found = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (!found) {
                    collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }

        public void setCallback(MenuPresenter.Callback cb) {
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
            return false;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean flagActionItems() {
            return false;
        }

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
            ActionBarView.this.mExpandedHomeLayout.setIcon((Drawable) null);
            this.mCurrentExpandedItem = null;
            ActionBarView.this.setHomeButtonEnabled(ActionBarView.this.mWasHomeEnabled);
            ActionBarView.this.requestLayout();
            item.setActionViewExpanded(false);
            return true;
        }

        public int getId() {
            return 0;
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onRestoreInstanceState(Parcelable state) {
        }
    }
}
