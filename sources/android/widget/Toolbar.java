package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricPrompt;
import android.media.tv.TvContract;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.CollapsibleActionView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ActionMenuView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ToolbarWidgetWrapper;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    /* access modifiers changed from: private */
    public int mButtonGravity;
    /* access modifiers changed from: private */
    public ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private int mNavButtonStyle;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private ImageButton mNavButtonView;
    /* access modifiers changed from: private */
    public OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    @UnsupportedAppUsage
    private int mTitleMarginBottom;
    @UnsupportedAppUsage
    private int mTitleMarginEnd;
    @UnsupportedAppUsage
    private int mTitleMarginStart;
    @UnsupportedAppUsage
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    @UnsupportedAppUsage
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<Toolbar> {
        private int mCollapseContentDescriptionId;
        private int mCollapseIconId;
        private int mContentInsetEndId;
        private int mContentInsetEndWithActionsId;
        private int mContentInsetLeftId;
        private int mContentInsetRightId;
        private int mContentInsetStartId;
        private int mContentInsetStartWithNavigationId;
        private int mLogoDescriptionId;
        private int mLogoId;
        private int mNavigationContentDescriptionId;
        private int mNavigationIconId;
        private int mPopupThemeId;
        private boolean mPropertiesMapped = false;
        private int mSubtitleId;
        private int mTitleId;
        private int mTitleMarginBottomId;
        private int mTitleMarginEndId;
        private int mTitleMarginStartId;
        private int mTitleMarginTopId;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCollapseContentDescriptionId = propertyMapper.mapObject("collapseContentDescription", 16843984);
            this.mCollapseIconId = propertyMapper.mapObject("collapseIcon", 16844031);
            this.mContentInsetEndId = propertyMapper.mapInt("contentInsetEnd", 16843860);
            this.mContentInsetEndWithActionsId = propertyMapper.mapInt("contentInsetEndWithActions", 16844067);
            this.mContentInsetLeftId = propertyMapper.mapInt("contentInsetLeft", 16843861);
            this.mContentInsetRightId = propertyMapper.mapInt("contentInsetRight", 16843862);
            this.mContentInsetStartId = propertyMapper.mapInt("contentInsetStart", 16843859);
            this.mContentInsetStartWithNavigationId = propertyMapper.mapInt("contentInsetStartWithNavigation", 16844066);
            this.mLogoId = propertyMapper.mapObject(TvContract.Channels.Logo.CONTENT_DIRECTORY, 16843454);
            this.mLogoDescriptionId = propertyMapper.mapObject("logoDescription", 16844009);
            this.mNavigationContentDescriptionId = propertyMapper.mapObject("navigationContentDescription", 16843969);
            this.mNavigationIconId = propertyMapper.mapObject("navigationIcon", 16843968);
            this.mPopupThemeId = propertyMapper.mapInt("popupTheme", 16843945);
            this.mSubtitleId = propertyMapper.mapObject(BiometricPrompt.KEY_SUBTITLE, 16843473);
            this.mTitleId = propertyMapper.mapObject("title", 16843233);
            this.mTitleMarginBottomId = propertyMapper.mapInt("titleMarginBottom", 16844028);
            this.mTitleMarginEndId = propertyMapper.mapInt("titleMarginEnd", 16844026);
            this.mTitleMarginStartId = propertyMapper.mapInt("titleMarginStart", 16844025);
            this.mTitleMarginTopId = propertyMapper.mapInt("titleMarginTop", 16844027);
            this.mPropertiesMapped = true;
        }

        public void readProperties(Toolbar node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readObject(this.mCollapseContentDescriptionId, node.getCollapseContentDescription());
                propertyReader.readObject(this.mCollapseIconId, node.getCollapseIcon());
                propertyReader.readInt(this.mContentInsetEndId, node.getContentInsetEnd());
                propertyReader.readInt(this.mContentInsetEndWithActionsId, node.getContentInsetEndWithActions());
                propertyReader.readInt(this.mContentInsetLeftId, node.getContentInsetLeft());
                propertyReader.readInt(this.mContentInsetRightId, node.getContentInsetRight());
                propertyReader.readInt(this.mContentInsetStartId, node.getContentInsetStart());
                propertyReader.readInt(this.mContentInsetStartWithNavigationId, node.getContentInsetStartWithNavigation());
                propertyReader.readObject(this.mLogoId, node.getLogo());
                propertyReader.readObject(this.mLogoDescriptionId, node.getLogoDescription());
                propertyReader.readObject(this.mNavigationContentDescriptionId, node.getNavigationContentDescription());
                propertyReader.readObject(this.mNavigationIconId, node.getNavigationIcon());
                propertyReader.readInt(this.mPopupThemeId, node.getPopupTheme());
                propertyReader.readObject(this.mSubtitleId, node.getSubtitle());
                propertyReader.readObject(this.mTitleId, node.getTitle());
                propertyReader.readInt(this.mTitleMarginBottomId, node.getTitleMarginBottom());
                propertyReader.readInt(this.mTitleMarginEndId, node.getTitleMarginEnd());
                propertyReader.readInt(this.mTitleMarginStartId, node.getTitleMarginStart());
                propertyReader.readInt(this.mTitleMarginTopId, node.getTitleMarginTop());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public Toolbar(Context context) {
        this(context, (AttributeSet) null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 16843946);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int i;
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList<>();
        this.mHiddenViews = new ArrayList<>();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(item);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable() {
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.Toolbar, attrs, a, defStyleAttr, defStyleRes);
        this.mTitleTextAppearance = a.getResourceId(4, 0);
        this.mSubtitleTextAppearance = a.getResourceId(5, 0);
        this.mNavButtonStyle = a.getResourceId(27, 0);
        this.mGravity = a.getInteger(0, this.mGravity);
        this.mButtonGravity = a.getInteger(23, 48);
        int dimensionPixelOffset = a.getDimensionPixelOffset(17, 0);
        this.mTitleMarginBottom = dimensionPixelOffset;
        this.mTitleMarginTop = dimensionPixelOffset;
        this.mTitleMarginEnd = dimensionPixelOffset;
        this.mTitleMarginStart = dimensionPixelOffset;
        int marginStart = a.getDimensionPixelOffset(18, -1);
        if (marginStart >= 0) {
            this.mTitleMarginStart = marginStart;
        }
        int marginEnd = a.getDimensionPixelOffset(19, -1);
        if (marginEnd >= 0) {
            this.mTitleMarginEnd = marginEnd;
        }
        int marginTop = a.getDimensionPixelOffset(20, -1);
        if (marginTop >= 0) {
            this.mTitleMarginTop = marginTop;
        }
        int marginBottom = a.getDimensionPixelOffset(21, -1);
        if (marginBottom >= 0) {
            this.mTitleMarginBottom = marginBottom;
        }
        this.mMaxButtonHeight = a.getDimensionPixelSize(22, -1);
        int contentInsetStart = a.getDimensionPixelOffset(6, Integer.MIN_VALUE);
        int contentInsetEnd = a.getDimensionPixelOffset(7, Integer.MIN_VALUE);
        int contentInsetLeft = a.getDimensionPixelSize(8, 0);
        int contentInsetRight = a.getDimensionPixelSize(9, 0);
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
        if (!(contentInsetStart == Integer.MIN_VALUE && contentInsetEnd == Integer.MIN_VALUE)) {
            this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
        }
        this.mContentInsetStartWithNavigation = a.getDimensionPixelOffset(25, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = a.getDimensionPixelOffset(26, Integer.MIN_VALUE);
        this.mCollapseIcon = a.getDrawable(24);
        this.mCollapseDescription = a.getText(13);
        CharSequence title = a.getText(1);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        CharSequence subtitle = a.getText(3);
        if (!TextUtils.isEmpty(subtitle)) {
            setSubtitle(subtitle);
        }
        CharSequence charSequence = title;
        this.mPopupContext = this.mContext;
        int i2 = contentInsetLeft;
        setPopupTheme(a.getResourceId(10, 0));
        Drawable navIcon = a.getDrawable(11);
        if (navIcon != null) {
            setNavigationIcon(navIcon);
        }
        CharSequence navDesc = a.getText(12);
        if (!TextUtils.isEmpty(navDesc)) {
            setNavigationContentDescription(navDesc);
        }
        Drawable drawable = navIcon;
        Drawable logo = a.getDrawable(2);
        if (logo != null) {
            setLogo(logo);
        }
        Drawable drawable2 = logo;
        CharSequence logoDesc = a.getText(16);
        if (!TextUtils.isEmpty(logoDesc)) {
            setLogoDescription(logoDesc);
        }
        CharSequence charSequence2 = logoDesc;
        if (a.hasValue(14)) {
            CharSequence charSequence3 = navDesc;
            i = -1;
            setTitleTextColor(a.getColor(14, -1));
        } else {
            i = -1;
        }
        if (a.hasValue(15)) {
            setSubtitleTextColor(a.getColor(15, i));
        }
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof ViewGroup)) {
            ViewGroup vgParent = (ViewGroup) parent;
            if (vgParent.isKeyboardNavigationCluster()) {
                setKeyboardNavigationCluster(false);
                if (vgParent.getTouchscreenBlocksFocus()) {
                    setTouchscreenBlocksFocus(false);
                    return;
                }
                return;
            }
            parent = vgParent.getParent();
        }
    }

    public void setPopupTheme(int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = this.mContext;
            } else {
                this.mPopupContext = new ContextThemeWrapper(this.mContext, resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setTitleMargin(int start, int top, int end, int bottom) {
        this.mTitleMarginStart = start;
        this.mTitleMarginTop = top;
        this.mTitleMarginEnd = end;
        this.mTitleMarginBottom = bottom;
        requestLayout();
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public void setTitleMarginStart(int margin) {
        this.mTitleMarginStart = margin;
        requestLayout();
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public void setTitleMarginTop(int margin) {
        this.mTitleMarginTop = margin;
        requestLayout();
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public void setTitleMarginEnd(int margin) {
        this.mTitleMarginEnd = margin;
        requestLayout();
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public void setTitleMarginBottom(int margin) {
        this.mTitleMarginBottom = margin;
        requestLayout();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean z = true;
        if (layoutDirection != 1) {
            z = false;
        }
        rtlSpacingHelper.setDirection(z);
    }

    public void setLogo(int resId) {
        setLogo(getContext().getDrawable(resId));
    }

    public boolean canShowOverflowMenu() {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    public void setMenu(MenuBuilder menu, ActionMenuPresenter outerPresenter) {
        if (menu != null || this.mMenuView != null) {
            ensureMenuView();
            MenuBuilder oldMenu = this.mMenuView.peekMenu();
            if (oldMenu != menu) {
                if (oldMenu != null) {
                    oldMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
                    oldMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
                }
                if (this.mExpandedMenuPresenter == null) {
                    this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
                }
                outerPresenter.setExpandedActionViewsExclusive(true);
                if (menu != null) {
                    menu.addMenuPresenter(outerPresenter, this.mPopupContext);
                    menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
                } else {
                    outerPresenter.initForMenu(this.mPopupContext, (MenuBuilder) null);
                    this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, (MenuBuilder) null);
                    outerPresenter.updateMenuView(true);
                    this.mExpandedMenuPresenter.updateMenuView(true);
                }
                this.mMenuView.setPopupTheme(this.mPopupTheme);
                this.mMenuView.setPresenter(outerPresenter);
                this.mOuterActionMenuPresenter = outerPresenter;
            }
        }
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    public boolean isTitleTruncated() {
        Layout titleLayout;
        if (this.mTitleTextView == null || (titleLayout = this.mTitleTextView.getLayout()) == null) {
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

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable);
        }
    }

    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }

    public void setLogoDescription(int resId) {
        setLogoDescription(getContext().getText(resId));
    }

    public void setLogoDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(description);
        }
    }

    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new ImageView(getContext());
        }
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

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (this.mTitleTextView == null) {
                this.mTitleTextView = new TextView(getContext());
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(title);
        }
        this.mTitleText = title;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public void setSubtitle(int resId) {
        setSubtitle(getContext().getText(resId));
    }

    public void setSubtitle(CharSequence subtitle) {
        if (!TextUtils.isEmpty(subtitle)) {
            if (this.mSubtitleTextView == null) {
                this.mSubtitleTextView = new TextView(getContext());
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(subtitle);
        }
        this.mSubtitleText = subtitle;
    }

    public void setTitleTextAppearance(Context context, int resId) {
        this.mTitleTextAppearance = resId;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(resId);
        }
    }

    public void setSubtitleTextAppearance(Context context, int resId) {
        this.mSubtitleTextAppearance = resId;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(resId);
        }
    }

    public void setTitleTextColor(int color) {
        this.mTitleTextColor = color;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(color);
        }
    }

    public void setSubtitleTextColor(int color) {
        this.mSubtitleTextColor = color;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(color);
        }
    }

    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }

    public void setNavigationContentDescription(int resId) {
        setNavigationContentDescription(resId != 0 ? getContext().getText(resId) : null);
    }

    public void setNavigationContentDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(description);
        }
    }

    public void setNavigationIcon(int resId) {
        setNavigationIcon(getContext().getDrawable(resId));
    }

    public void setNavigationIcon(Drawable icon) {
        if (icon != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(icon);
        }
    }

    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(listener);
    }

    public View getNavigationView() {
        return this.mNavButtonView;
    }

    public CharSequence getCollapseContentDescription() {
        if (this.mCollapseButtonView != null) {
            return this.mCollapseButtonView.getContentDescription();
        }
        return null;
    }

    public void setCollapseContentDescription(int resId) {
        setCollapseContentDescription(resId != 0 ? getContext().getText(resId) : null);
    }

    public void setCollapseContentDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureCollapseButtonView();
        }
        if (this.mCollapseButtonView != null) {
            this.mCollapseButtonView.setContentDescription(description);
        }
    }

    public Drawable getCollapseIcon() {
        if (this.mCollapseButtonView != null) {
            return this.mCollapseButtonView.getDrawable();
        }
        return null;
    }

    public void setCollapseIcon(int resId) {
        setCollapseIcon(getContext().getDrawable(resId));
    }

    public void setCollapseIcon(Drawable icon) {
        if (icon != null) {
            ensureCollapseButtonView();
            this.mCollapseButtonView.setImageDrawable(icon);
        } else if (this.mCollapseButtonView != null) {
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
        }
    }

    public Menu getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public void setOverflowIcon(Drawable icon) {
        ensureMenu();
        this.mMenuView.setOverflowIcon(icon);
    }

    public Drawable getOverflowIcon() {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    private void ensureMenu() {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menu = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388613 | (this.mButtonGravity & 112);
            this.mMenuView.setLayoutParams(lp);
            addSystemView(this.mMenuView, false);
        }
    }

    private MenuInflater getMenuInflater() {
        return new MenuInflater(getContext());
    }

    public void inflateMenu(int resId) {
        getMenuInflater().inflate(resId, getMenu());
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    public void setContentInsetsRelative(int contentInsetStart, int contentInsetEnd) {
        ensureContentInsets();
        this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
    }

    public int getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart();
        }
        return 0;
    }

    public int getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd();
        }
        return 0;
    }

    public void setContentInsetsAbsolute(int contentInsetLeft, int contentInsetRight) {
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
    }

    public int getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft();
        }
        return 0;
    }

    public int getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        if (this.mContentInsetStartWithNavigation != Integer.MIN_VALUE) {
            return this.mContentInsetStartWithNavigation;
        }
        return getContentInsetStart();
    }

    public void setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        if (insetStartWithNavigation < 0) {
            insetStartWithNavigation = Integer.MIN_VALUE;
        }
        if (insetStartWithNavigation != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = insetStartWithNavigation;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getContentInsetEndWithActions() {
        if (this.mContentInsetEndWithActions != Integer.MIN_VALUE) {
            return this.mContentInsetEndWithActions;
        }
        return getContentInsetEnd();
    }

    public void setContentInsetEndWithActions(int insetEndWithActions) {
        if (insetEndWithActions < 0) {
            insetEndWithActions = Integer.MIN_VALUE;
        }
        if (insetEndWithActions != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = insetEndWithActions;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            return Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        boolean hasActions = false;
        if (this.mMenuView != null) {
            MenuBuilder mb = this.mMenuView.peekMenu();
            hasActions = mb != null && mb.hasVisibleItems();
        }
        if (hasActions) {
            return Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (isLayoutRtl()) {
            return getCurrentContentInsetEnd();
        }
        return getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (isLayoutRtl()) {
            return getCurrentContentInsetStart();
        }
        return getCurrentContentInsetEnd();
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new ImageButton(getContext(), (AttributeSet) null, 0, this.mNavButtonStyle);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.mButtonGravity & 112);
            this.mNavButtonView.setLayoutParams(lp);
        }
    }

    /* access modifiers changed from: private */
    public void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new ImageButton(getContext(), (AttributeSet) null, 0, this.mNavButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.mButtonGravity & 112);
            lp.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(lp);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    private void addSystemView(View v, boolean allowHide) {
        LayoutParams lp;
        ViewGroup.LayoutParams vlp = v.getLayoutParams();
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }
        lp.mViewType = 1;
        if (!allowHide || this.mExpandedActionView == null) {
            addView(v, (ViewGroup.LayoutParams) lp);
            return;
        }
        v.setLayoutParams(lp);
        this.mHiddenViews.add(v);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        if (!(this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null)) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        MenuItem item;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        Menu menu = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
        if (!(ss.expandedMenuItemId == 0 || this.mExpandedMenuPresenter == null || menu == null || (item = menu.findItem(ss.expandedMenuItemId)) == null)) {
            item.expandActionView();
        }
        if (ss.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    private void postShowOverflowMenu() {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean handled = super.onTouchEvent(ev);
            if (action == 0 && !handled) {
                this.mEatingTouch = true;
            }
        }
        if (action == 1 || action == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onSetLayoutParams(View child, ViewGroup.LayoutParams lp) {
        if (!checkLayoutParams(lp)) {
            child.setLayoutParams(generateLayoutParams(lp));
        }
    }

    private void measureChildConstrained(View child, int parentWidthSpec, int widthUsed, int parentHeightSpec, int heightUsed, int heightConstraint) {
        int size;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        int childWidthSpec = getChildMeasureSpec(parentWidthSpec, this.mPaddingLeft + this.mPaddingRight + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
        int childHeightSpec = getChildMeasureSpec(parentHeightSpec, this.mPaddingTop + this.mPaddingBottom + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        int childHeightMode = View.MeasureSpec.getMode(childHeightSpec);
        if (childHeightMode != 1073741824 && heightConstraint >= 0) {
            if (childHeightMode != 0) {
                size = Math.min(View.MeasureSpec.getSize(childHeightSpec), heightConstraint);
            } else {
                size = heightConstraint;
            }
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private int measureChildCollapseMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed, int[] collapsingMargins) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        int leftDiff = lp.leftMargin - collapsingMargins[0];
        int rightDiff = lp.rightMargin - collapsingMargins[1];
        int hMargins = Math.max(0, leftDiff) + Math.max(0, rightDiff);
        collapsingMargins[0] = Math.max(0, -leftDiff);
        collapsingMargins[1] = Math.max(0, -rightDiff);
        child.measure(getChildMeasureSpec(parentWidthMeasureSpec, this.mPaddingLeft + this.mPaddingRight + hMargins + widthUsed, lp.width), getChildMeasureSpec(parentHeightMeasureSpec, this.mPaddingTop + this.mPaddingBottom + lp.topMargin + lp.bottomMargin + heightUsed, lp.height));
        return child.getMeasuredWidth() + hMargins;
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (shouldLayout(child) && child.getMeasuredWidth() > 0 && child.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int marginStartIndex;
        int marginEndIndex;
        int marginStartIndex2;
        int childState;
        int height;
        int childState2;
        int childState3;
        int titleHeight;
        int i;
        int childCount;
        int height2 = 0;
        int childState4 = 0;
        int[] collapsingMargins = this.mTempMargins;
        if (isLayoutRtl()) {
            marginStartIndex = 1;
            marginEndIndex = 0;
        } else {
            marginStartIndex = 0;
            marginEndIndex = 1;
        }
        int i2 = marginStartIndex;
        int marginEndIndex2 = marginEndIndex;
        int navWidth = 0;
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, this.mMaxButtonHeight);
            navWidth = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
            height2 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
            childState4 = combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, this.mMaxButtonHeight);
            navWidth = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
            height2 = Math.max(height2, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView));
            childState4 = combineMeasuredStates(childState4, this.mCollapseButtonView.getMeasuredState());
        }
        int contentInsetStart = getCurrentContentInsetStart();
        int width = 0 + Math.max(contentInsetStart, navWidth);
        collapsingMargins[i2] = Math.max(0, contentInsetStart - navWidth);
        if (shouldLayout(this.mMenuView)) {
            char c = i2;
            marginStartIndex2 = 0;
            measureChildConstrained(this.mMenuView, widthMeasureSpec, width, heightMeasureSpec, 0, this.mMaxButtonHeight);
            int menuWidth = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            int height3 = Math.max(height2, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
            childState2 = combineMeasuredStates(childState4, this.mMenuView.getMeasuredState());
            childState = height3;
            height = menuWidth;
        } else {
            int marginStartIndex3 = i2;
            marginStartIndex2 = 0;
            childState2 = childState4;
            childState = height2;
            height = 0;
        }
        int contentInsetEnd = getCurrentContentInsetEnd();
        int width2 = width + Math.max(contentInsetEnd, height);
        collapsingMargins[marginEndIndex2] = Math.max(marginStartIndex2, contentInsetEnd - height);
        if (shouldLayout(this.mExpandedActionView)) {
            int i3 = contentInsetEnd;
            width2 += measureChildCollapseMargins(this.mExpandedActionView, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
            childState = Math.max(childState, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
            childState3 = combineMeasuredStates(childState2, this.mExpandedActionView.getMeasuredState());
        } else {
            childState3 = childState2;
        }
        if (shouldLayout(this.mLogoView)) {
            width2 += measureChildCollapseMargins(this.mLogoView, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
            childState = Math.max(childState, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            childState3 = combineMeasuredStates(childState3, this.mLogoView.getMeasuredState());
        }
        int childCount2 = getChildCount();
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 >= childCount2) {
                break;
            }
            View child = getChildAt(i5);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.mViewType != 0) {
                i = i5;
                childCount = childCount2;
            } else if (!shouldLayout(child)) {
                i = i5;
                childCount = childCount2;
            } else {
                LayoutParams layoutParams = lp;
                View child2 = child;
                i = i5;
                childCount = childCount2;
                width2 += measureChildCollapseMargins(child, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
                View child3 = child2;
                childState = Math.max(childState, child2.getMeasuredHeight() + getVerticalMargins(child3));
                childState3 = combineMeasuredStates(childState3, child3.getMeasuredState());
            }
            i4 = i + 1;
            childCount2 = childCount;
        }
        int titleWidth = 0;
        int titleHeight2 = 0;
        int titleVertMargins = this.mTitleMarginTop + this.mTitleMarginBottom;
        int titleHorizMargins = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            int measureChildCollapseMargins = measureChildCollapseMargins(this.mTitleTextView, widthMeasureSpec, width2 + titleHorizMargins, heightMeasureSpec, titleVertMargins, collapsingMargins);
            titleWidth = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            titleHeight2 = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            childState3 = combineMeasuredStates(childState3, this.mTitleTextView.getMeasuredState());
        }
        int childState5 = childState3;
        int titleWidth2 = titleWidth;
        if (shouldLayout(this.mSubtitleTextView)) {
            int i6 = height;
            titleWidth2 = Math.max(titleWidth2, measureChildCollapseMargins(this.mSubtitleTextView, widthMeasureSpec, width2 + titleHorizMargins, heightMeasureSpec, titleHeight2 + titleVertMargins, collapsingMargins));
            int titleHeight3 = titleHeight2 + this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            childState5 = combineMeasuredStates(childState5, this.mSubtitleTextView.getMeasuredState());
            titleHeight = titleHeight3;
        } else {
            int menuWidth2 = height;
            int menuWidth3 = childState5;
            titleHeight = titleHeight2;
        }
        setMeasuredDimension(resolveSizeAndState(Math.max(width2 + titleWidth2 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), widthMeasureSpec, -16777216 & childState5), shouldCollapse() ? 0 : resolveSizeAndState(Math.max(Math.max(childState, titleHeight) + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), heightMeasureSpec, childState5 << 16));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x015c, code lost:
        if (r0.mTitleTextView.getMeasuredWidth() <= 0) goto L_0x0161;
     */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01d9  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x025d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayout(boolean r41, int r42, int r43, int r44, int r45) {
        /*
            r40 = this;
            r0 = r40
            int r1 = r40.getLayoutDirection()
            r2 = 1
            r3 = 0
            if (r1 != r2) goto L_0x000c
            r1 = r2
            goto L_0x000d
        L_0x000c:
            r1 = r3
        L_0x000d:
            int r4 = r40.getWidth()
            int r5 = r40.getHeight()
            int r6 = r40.getPaddingLeft()
            int r7 = r40.getPaddingRight()
            int r8 = r40.getPaddingTop()
            int r9 = r40.getPaddingBottom()
            r10 = r6
            int r11 = r4 - r7
            int[] r12 = r0.mTempMargins
            r12[r2] = r3
            r12[r3] = r3
            int r13 = r40.getMinimumHeight()
            if (r13 < 0) goto L_0x003b
            int r2 = r45 - r43
            int r2 = java.lang.Math.min(r13, r2)
            goto L_0x003c
        L_0x003b:
            r2 = r3
        L_0x003c:
            android.widget.ImageButton r3 = r0.mNavButtonView
            boolean r3 = r0.shouldLayout(r3)
            if (r3 == 0) goto L_0x0053
            if (r1 == 0) goto L_0x004d
            android.widget.ImageButton r3 = r0.mNavButtonView
            int r11 = r0.layoutChildRight(r3, r11, r12, r2)
            goto L_0x0053
        L_0x004d:
            android.widget.ImageButton r3 = r0.mNavButtonView
            int r10 = r0.layoutChildLeft(r3, r10, r12, r2)
        L_0x0053:
            android.widget.ImageButton r3 = r0.mCollapseButtonView
            boolean r3 = r0.shouldLayout(r3)
            if (r3 == 0) goto L_0x006a
            if (r1 == 0) goto L_0x0064
            android.widget.ImageButton r3 = r0.mCollapseButtonView
            int r11 = r0.layoutChildRight(r3, r11, r12, r2)
            goto L_0x006a
        L_0x0064:
            android.widget.ImageButton r3 = r0.mCollapseButtonView
            int r10 = r0.layoutChildLeft(r3, r10, r12, r2)
        L_0x006a:
            android.widget.ActionMenuView r3 = r0.mMenuView
            boolean r3 = r0.shouldLayout(r3)
            if (r3 == 0) goto L_0x0081
            if (r1 == 0) goto L_0x007b
            android.widget.ActionMenuView r3 = r0.mMenuView
            int r10 = r0.layoutChildLeft(r3, r10, r12, r2)
            goto L_0x0081
        L_0x007b:
            android.widget.ActionMenuView r3 = r0.mMenuView
            int r11 = r0.layoutChildRight(r3, r11, r12, r2)
        L_0x0081:
            int r3 = r40.getCurrentContentInsetLeft()
            int r17 = r40.getCurrentContentInsetRight()
            r18 = r13
            int r13 = r3 - r10
            r14 = 0
            int r13 = java.lang.Math.max(r14, r13)
            r12[r14] = r13
            int r13 = r4 - r7
            int r13 = r13 - r11
            int r13 = r17 - r13
            int r13 = java.lang.Math.max(r14, r13)
            r14 = 1
            r12[r14] = r13
            int r10 = java.lang.Math.max(r10, r3)
            int r13 = r4 - r7
            int r13 = r13 - r17
            int r11 = java.lang.Math.min(r11, r13)
            android.view.View r13 = r0.mExpandedActionView
            boolean r13 = r0.shouldLayout(r13)
            if (r13 == 0) goto L_0x00c3
            if (r1 == 0) goto L_0x00bd
            android.view.View r13 = r0.mExpandedActionView
            int r11 = r0.layoutChildRight(r13, r11, r12, r2)
            goto L_0x00c3
        L_0x00bd:
            android.view.View r13 = r0.mExpandedActionView
            int r10 = r0.layoutChildLeft(r13, r10, r12, r2)
        L_0x00c3:
            android.widget.ImageView r13 = r0.mLogoView
            boolean r13 = r0.shouldLayout(r13)
            if (r13 == 0) goto L_0x00da
            if (r1 == 0) goto L_0x00d4
            android.widget.ImageView r13 = r0.mLogoView
            int r11 = r0.layoutChildRight(r13, r11, r12, r2)
            goto L_0x00da
        L_0x00d4:
            android.widget.ImageView r13 = r0.mLogoView
            int r10 = r0.layoutChildLeft(r13, r10, r12, r2)
        L_0x00da:
            android.widget.TextView r13 = r0.mTitleTextView
            boolean r13 = r0.shouldLayout(r13)
            android.widget.TextView r14 = r0.mSubtitleTextView
            boolean r14 = r0.shouldLayout(r14)
            r19 = 0
            if (r13 == 0) goto L_0x0105
            r20 = r3
            android.widget.TextView r3 = r0.mTitleTextView
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.Toolbar$LayoutParams r3 = (android.widget.Toolbar.LayoutParams) r3
            int r15 = r3.topMargin
            r21 = r7
            android.widget.TextView r7 = r0.mTitleTextView
            int r7 = r7.getMeasuredHeight()
            int r15 = r15 + r7
            int r7 = r3.bottomMargin
            int r15 = r15 + r7
            int r19 = r19 + r15
            goto L_0x0109
        L_0x0105:
            r20 = r3
            r21 = r7
        L_0x0109:
            if (r14 == 0) goto L_0x0121
            android.widget.TextView r3 = r0.mSubtitleTextView
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.Toolbar$LayoutParams r3 = (android.widget.Toolbar.LayoutParams) r3
            int r7 = r3.topMargin
            android.widget.TextView r15 = r0.mSubtitleTextView
            int r15 = r15.getMeasuredHeight()
            int r7 = r7 + r15
            int r15 = r3.bottomMargin
            int r7 = r7 + r15
            int r19 = r19 + r7
        L_0x0121:
            if (r13 != 0) goto L_0x0134
            if (r14 == 0) goto L_0x0126
            goto L_0x0134
        L_0x0126:
            r30 = r1
            r28 = r2
            r25 = r4
            r33 = r5
            r26 = r6
            r36 = r8
            goto L_0x02d8
        L_0x0134:
            if (r13 == 0) goto L_0x0139
            android.widget.TextView r3 = r0.mTitleTextView
            goto L_0x013b
        L_0x0139:
            android.widget.TextView r3 = r0.mSubtitleTextView
        L_0x013b:
            if (r14 == 0) goto L_0x0140
            android.widget.TextView r7 = r0.mSubtitleTextView
            goto L_0x0142
        L_0x0140:
            android.widget.TextView r7 = r0.mTitleTextView
        L_0x0142:
            android.view.ViewGroup$LayoutParams r15 = r3.getLayoutParams()
            android.widget.Toolbar$LayoutParams r15 = (android.widget.Toolbar.LayoutParams) r15
            android.view.ViewGroup$LayoutParams r22 = r7.getLayoutParams()
            r23 = r3
            r3 = r22
            android.widget.Toolbar$LayoutParams r3 = (android.widget.Toolbar.LayoutParams) r3
            if (r13 == 0) goto L_0x015f
            r24 = r7
            android.widget.TextView r7 = r0.mTitleTextView
            int r7 = r7.getMeasuredWidth()
            if (r7 > 0) goto L_0x016b
            goto L_0x0161
        L_0x015f:
            r24 = r7
        L_0x0161:
            if (r14 == 0) goto L_0x016d
            android.widget.TextView r7 = r0.mSubtitleTextView
            int r7 = r7.getMeasuredWidth()
            if (r7 <= 0) goto L_0x016d
        L_0x016b:
            r7 = 1
            goto L_0x016e
        L_0x016d:
            r7 = 0
        L_0x016e:
            r25 = r4
            int r4 = r0.mGravity
            r4 = r4 & 112(0x70, float:1.57E-43)
            r26 = r6
            r6 = 48
            if (r4 == r6) goto L_0x01c7
            r6 = 80
            if (r4 == r6) goto L_0x01b8
            int r4 = r5 - r8
            int r4 = r4 - r9
            int r6 = r4 - r19
            int r6 = r6 / 2
            r27 = r4
            int r4 = r15.topMargin
            r28 = r2
            int r2 = r0.mTitleMarginTop
            int r4 = r4 + r2
            if (r6 >= r4) goto L_0x0199
            int r2 = r15.topMargin
            int r4 = r0.mTitleMarginTop
            int r6 = r2 + r4
            r29 = r10
            goto L_0x01b5
        L_0x0199:
            int r2 = r5 - r9
            int r2 = r2 - r19
            int r2 = r2 - r6
            int r2 = r2 - r8
            int r4 = r15.bottomMargin
            r29 = r10
            int r10 = r0.mTitleMarginBottom
            int r4 = r4 + r10
            if (r2 >= r4) goto L_0x01b5
            int r4 = r3.bottomMargin
            int r10 = r0.mTitleMarginBottom
            int r4 = r4 + r10
            int r4 = r4 - r2
            int r4 = r6 - r4
            r10 = 0
            int r6 = java.lang.Math.max(r10, r4)
        L_0x01b5:
            int r2 = r8 + r6
            goto L_0x01d6
        L_0x01b8:
            r28 = r2
            r29 = r10
            int r2 = r5 - r9
            int r4 = r3.bottomMargin
            int r2 = r2 - r4
            int r4 = r0.mTitleMarginBottom
            int r2 = r2 - r4
            int r2 = r2 - r19
            goto L_0x01d6
        L_0x01c7:
            r28 = r2
            r29 = r10
            int r2 = r40.getPaddingTop()
            int r4 = r15.topMargin
            int r2 = r2 + r4
            int r4 = r0.mTitleMarginTop
            int r2 = r2 + r4
        L_0x01d6:
            if (r1 == 0) goto L_0x025d
            if (r7 == 0) goto L_0x01de
            int r4 = r0.mTitleMarginStart
            goto L_0x01df
        L_0x01de:
            r4 = 0
        L_0x01df:
            r6 = 1
            r10 = r12[r6]
            int r4 = r4 - r10
            r10 = 0
            int r16 = java.lang.Math.max(r10, r4)
            int r11 = r11 - r16
            r30 = r1
            int r1 = -r4
            int r1 = java.lang.Math.max(r10, r1)
            r12[r6] = r1
            r1 = r11
            r6 = r11
            if (r13 == 0) goto L_0x0222
            android.widget.TextView r10 = r0.mTitleTextView
            android.view.ViewGroup$LayoutParams r10 = r10.getLayoutParams()
            android.widget.Toolbar$LayoutParams r10 = (android.widget.Toolbar.LayoutParams) r10
            r31 = r3
            android.widget.TextView r3 = r0.mTitleTextView
            int r3 = r3.getMeasuredWidth()
            int r3 = r1 - r3
            r32 = r4
            android.widget.TextView r4 = r0.mTitleTextView
            int r4 = r4.getMeasuredHeight()
            int r4 = r4 + r2
            r33 = r5
            android.widget.TextView r5 = r0.mTitleTextView
            r5.layout(r3, r2, r1, r4)
            int r5 = r0.mTitleMarginEnd
            int r1 = r3 - r5
            int r5 = r10.bottomMargin
            int r2 = r4 + r5
            goto L_0x0228
        L_0x0222:
            r31 = r3
            r32 = r4
            r33 = r5
        L_0x0228:
            if (r14 == 0) goto L_0x0250
            android.widget.TextView r3 = r0.mSubtitleTextView
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.Toolbar$LayoutParams r3 = (android.widget.Toolbar.LayoutParams) r3
            int r4 = r3.topMargin
            int r2 = r2 + r4
            android.widget.TextView r4 = r0.mSubtitleTextView
            int r4 = r4.getMeasuredWidth()
            int r4 = r6 - r4
            android.widget.TextView r5 = r0.mSubtitleTextView
            int r5 = r5.getMeasuredHeight()
            int r5 = r5 + r2
            android.widget.TextView r10 = r0.mSubtitleTextView
            r10.layout(r4, r2, r6, r5)
            int r10 = r0.mTitleMarginEnd
            int r6 = r6 - r10
            int r10 = r3.bottomMargin
            int r2 = r5 + r10
        L_0x0250:
            if (r7 == 0) goto L_0x0257
            int r1 = java.lang.Math.min(r1, r6)
            r11 = r1
        L_0x0257:
            r36 = r8
            r10 = r29
            goto L_0x02d8
        L_0x025d:
            r30 = r1
            r31 = r3
            r33 = r5
            if (r7 == 0) goto L_0x0268
            int r3 = r0.mTitleMarginStart
            goto L_0x0269
        L_0x0268:
            r3 = 0
        L_0x0269:
            r1 = 0
            r4 = r12[r1]
            int r3 = r3 - r4
            int r4 = java.lang.Math.max(r1, r3)
            int r10 = r29 + r4
            int r4 = -r3
            int r4 = java.lang.Math.max(r1, r4)
            r12[r1] = r4
            r4 = r10
            r5 = r10
            if (r13 == 0) goto L_0x02a6
            android.widget.TextView r6 = r0.mTitleTextView
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            android.widget.Toolbar$LayoutParams r6 = (android.widget.Toolbar.LayoutParams) r6
            android.widget.TextView r1 = r0.mTitleTextView
            int r1 = r1.getMeasuredWidth()
            int r1 = r1 + r4
            r35 = r3
            android.widget.TextView r3 = r0.mTitleTextView
            int r3 = r3.getMeasuredHeight()
            int r3 = r3 + r2
            r36 = r8
            android.widget.TextView r8 = r0.mTitleTextView
            r8.layout(r4, r2, r1, r3)
            int r8 = r0.mTitleMarginEnd
            int r4 = r1 + r8
            int r8 = r6.bottomMargin
            int r2 = r3 + r8
            goto L_0x02aa
        L_0x02a6:
            r35 = r3
            r36 = r8
        L_0x02aa:
            if (r14 == 0) goto L_0x02d2
            android.widget.TextView r1 = r0.mSubtitleTextView
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.widget.Toolbar$LayoutParams r1 = (android.widget.Toolbar.LayoutParams) r1
            int r3 = r1.topMargin
            int r2 = r2 + r3
            android.widget.TextView r3 = r0.mSubtitleTextView
            int r3 = r3.getMeasuredWidth()
            int r3 = r3 + r5
            android.widget.TextView r6 = r0.mSubtitleTextView
            int r6 = r6.getMeasuredHeight()
            int r6 = r6 + r2
            android.widget.TextView r8 = r0.mSubtitleTextView
            r8.layout(r5, r2, r3, r6)
            int r8 = r0.mTitleMarginEnd
            int r5 = r3 + r8
            int r8 = r1.bottomMargin
            int r2 = r6 + r8
        L_0x02d2:
            if (r7 == 0) goto L_0x02d8
            int r10 = java.lang.Math.max(r4, r5)
        L_0x02d8:
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            r2 = 3
            r0.addCustomViewsWithGravity(r1, r2)
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            int r1 = r1.size()
            r2 = 0
        L_0x02e5:
            if (r2 >= r1) goto L_0x02f8
            java.util.ArrayList<android.view.View> r3 = r0.mTempViews
            java.lang.Object r3 = r3.get(r2)
            android.view.View r3 = (android.view.View) r3
            r4 = r28
            int r10 = r0.layoutChildLeft(r3, r10, r12, r4)
            int r2 = r2 + 1
            goto L_0x02e5
        L_0x02f8:
            r4 = r28
            java.util.ArrayList<android.view.View> r2 = r0.mTempViews
            r3 = 5
            r0.addCustomViewsWithGravity(r2, r3)
            java.util.ArrayList<android.view.View> r2 = r0.mTempViews
            int r2 = r2.size()
            r3 = 0
        L_0x0307:
            if (r3 >= r2) goto L_0x0318
            java.util.ArrayList<android.view.View> r5 = r0.mTempViews
            java.lang.Object r5 = r5.get(r3)
            android.view.View r5 = (android.view.View) r5
            int r11 = r0.layoutChildRight(r5, r11, r12, r4)
            int r3 = r3 + 1
            goto L_0x0307
        L_0x0318:
            java.util.ArrayList<android.view.View> r3 = r0.mTempViews
            r5 = 1
            r0.addCustomViewsWithGravity(r3, r5)
            java.util.ArrayList<android.view.View> r3 = r0.mTempViews
            int r3 = r0.getViewListMeasuredWidth(r3, r12)
            int r5 = r25 - r26
            int r5 = r5 - r21
            int r5 = r5 / 2
            int r6 = r26 + r5
            int r5 = r3 / 2
            int r7 = r6 - r5
            int r8 = r7 + r3
            if (r7 >= r10) goto L_0x0336
            r7 = r10
            goto L_0x033b
        L_0x0336:
            if (r8 <= r11) goto L_0x033b
            int r15 = r8 - r11
            int r7 = r7 - r15
        L_0x033b:
            java.util.ArrayList<android.view.View> r15 = r0.mTempViews
            int r15 = r15.size()
            r34 = 0
        L_0x0343:
            r37 = r34
            r38 = r1
            r1 = r37
            if (r1 >= r15) goto L_0x0360
            r39 = r2
            java.util.ArrayList<android.view.View> r2 = r0.mTempViews
            java.lang.Object r2 = r2.get(r1)
            android.view.View r2 = (android.view.View) r2
            int r7 = r0.layoutChildLeft(r2, r7, r12, r4)
            int r34 = r1 + 1
            r1 = r38
            r2 = r39
            goto L_0x0343
        L_0x0360:
            r39 = r2
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            r1.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    private int getViewListMeasuredWidth(List<View> views, int[] collapsingMargins) {
        int collapseLeft = collapsingMargins[0];
        int collapseRight = collapsingMargins[1];
        int count = views.size();
        int width = 0;
        int collapseRight2 = collapseRight;
        int collapseLeft2 = collapseLeft;
        for (int i = 0; i < count; i++) {
            View v = views.get(i);
            LayoutParams lp = (LayoutParams) v.getLayoutParams();
            int l = lp.leftMargin - collapseLeft2;
            int r = lp.rightMargin - collapseRight2;
            int leftMargin = Math.max(0, l);
            int rightMargin = Math.max(0, r);
            collapseLeft2 = Math.max(0, -l);
            collapseRight2 = Math.max(0, -r);
            width += v.getMeasuredWidth() + leftMargin + rightMargin;
        }
        return width;
    }

    private int layoutChildLeft(View child, int left, int[] collapsingMargins, int alignmentHeight) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int l = lp.leftMargin - collapsingMargins[0];
        int left2 = left + Math.max(0, l);
        collapsingMargins[0] = Math.max(0, -l);
        int top = getChildTop(child, alignmentHeight);
        int childWidth = child.getMeasuredWidth();
        child.layout(left2, top, left2 + childWidth, child.getMeasuredHeight() + top);
        return left2 + lp.rightMargin + childWidth;
    }

    private int layoutChildRight(View child, int right, int[] collapsingMargins, int alignmentHeight) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int r = lp.rightMargin - collapsingMargins[1];
        int right2 = right - Math.max(0, r);
        collapsingMargins[1] = Math.max(0, -r);
        int top = getChildTop(child, alignmentHeight);
        int childWidth = child.getMeasuredWidth();
        child.layout(right2 - childWidth, top, right2, child.getMeasuredHeight() + top);
        return right2 - (lp.leftMargin + childWidth);
    }

    private int getChildTop(View child, int alignmentHeight) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeight = child.getMeasuredHeight();
        int alignmentOffset = alignmentHeight > 0 ? (childHeight - alignmentHeight) / 2 : 0;
        int childVerticalGravity = getChildVerticalGravity(lp.gravity);
        if (childVerticalGravity == 48) {
            return getPaddingTop() - alignmentOffset;
        }
        if (childVerticalGravity == 80) {
            return (((getHeight() - getPaddingBottom()) - childHeight) - lp.bottomMargin) - alignmentOffset;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int spaceAbove = (((height - paddingTop) - paddingBottom) - childHeight) / 2;
        if (spaceAbove < lp.topMargin) {
            spaceAbove = lp.topMargin;
        } else {
            int spaceBelow = (((height - paddingBottom) - childHeight) - spaceAbove) - paddingTop;
            if (spaceBelow < lp.bottomMargin) {
                spaceAbove = Math.max(0, spaceAbove - (lp.bottomMargin - spaceBelow));
            }
        }
        return paddingTop + spaceAbove;
    }

    private int getChildVerticalGravity(int gravity) {
        int vgrav = gravity & 112;
        if (vgrav == 16 || vgrav == 48 || vgrav == 80) {
            return vgrav;
        }
        return this.mGravity & 112;
    }

    private void addCustomViewsWithGravity(List<View> views, int gravity) {
        boolean z = true;
        if (getLayoutDirection() != 1) {
            z = false;
        }
        boolean isRtl = z;
        int childCount = getChildCount();
        int absGrav = Gravity.getAbsoluteGravity(gravity, getLayoutDirection());
        views.clear();
        if (isRtl) {
            for (int i = childCount - 1; i >= 0; i--) {
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.mViewType == 0 && shouldLayout(child) && getChildHorizontalGravity(lp.gravity) == absGrav) {
                    views.add(child);
                }
            }
            return;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View child2 = getChildAt(i2);
            LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
            if (lp2.mViewType == 0 && shouldLayout(child2) && getChildHorizontalGravity(lp2.gravity) == absGrav) {
                views.add(child2);
            }
        }
    }

    private int getChildHorizontalGravity(int gravity) {
        int ld = getLayoutDirection();
        int hGrav = Gravity.getAbsoluteGravity(gravity, ld) & 7;
        if (hGrav == 1 || hGrav == 3 || hGrav == 5) {
            return hGrav;
        }
        if (ld == 1) {
            return 5;
        }
        return 3;
    }

    private boolean shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    private int getHorizontalMargins(View v) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        return mlp.getMarginStart() + mlp.getMarginEnd();
    }

    private int getVerticalMargins(View v) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        return mlp.topMargin + mlp.bottomMargin;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) p);
        }
        if (p instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams) p);
        }
        if (p instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) p);
        }
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && (p instanceof LayoutParams);
    }

    private static boolean isCustomView(View child) {
        return ((LayoutParams) child.getLayoutParams()).mViewType == 0;
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    /* access modifiers changed from: package-private */
    public void removeChildrenForExpandedActionView() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (!(((LayoutParams) child.getLayoutParams()).mViewType == 2 || child == this.mMenuView)) {
                removeViewAt(i);
                this.mHiddenViews.add(child);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; i--) {
            addView(this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }

    private boolean isChildOrHidden(View child) {
        return child.getParent() == this || this.mHiddenViews.contains(child);
    }

    public void setCollapsible(boolean collapsible) {
        this.mCollapsible = collapsible;
        requestLayout();
    }

    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(pcb, mcb);
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    /* access modifiers changed from: package-private */
    public ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    /* access modifiers changed from: package-private */
    public Context getPopupContext() {
        return this.mPopupContext;
    }

    public static class LayoutParams extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mViewType = 0;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.mViewType = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.mViewType = 0;
            this.gravity = gravity;
        }

        public LayoutParams(int gravity) {
            this(-2, -1, gravity);
        }

        public LayoutParams(LayoutParams source) {
            super((ActionBar.LayoutParams) source);
            this.mViewType = 0;
            this.mViewType = source.mViewType;
        }

        public LayoutParams(ActionBar.LayoutParams source) {
            super(source);
            this.mViewType = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super((ViewGroup.LayoutParams) source);
            this.mViewType = 0;
            copyMarginsFrom(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.mViewType = 0;
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        public int expandedMenuItemId;
        public boolean isOverflowOpen;

        public SavedState(Parcel source) {
            super(source);
            this.expandedMenuItemId = source.readInt();
            this.isOverflowOpen = source.readInt() != 0;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.expandedMenuItemId);
            out.writeInt(this.isOverflowOpen ? 1 : 0);
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
            Toolbar.this.ensureCollapseButtonView();
            if (Toolbar.this.mCollapseButtonView.getParent() != Toolbar.this) {
                Toolbar.this.addView(Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = item.getActionView();
            this.mCurrentExpandedItem = item;
            if (Toolbar.this.mExpandedActionView.getParent() != Toolbar.this) {
                LayoutParams lp = Toolbar.this.generateDefaultLayoutParams();
                lp.gravity = 8388611 | (Toolbar.this.mButtonGravity & 112);
                lp.mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams(lp);
                Toolbar.this.addView(Toolbar.this.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            item.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
            Toolbar.this.removeView(Toolbar.this.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
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
