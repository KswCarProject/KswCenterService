package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricPrompt;
import android.media.p006tv.TvContract;
import android.p007os.Parcel;
import android.p007os.Parcelable;
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
import com.android.internal.C3132R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ToolbarWidgetWrapper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private int mButtonGravity;
    private ImageButton mCollapseButtonView;
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
    private OnMenuItemClickListener mOnMenuItemClickListener;
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

    /* loaded from: classes4.dex */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    /* loaded from: classes4.dex */
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

        @Override // android.view.inspector.InspectionCompanion
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

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(Toolbar node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
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
        }
    }

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 16843946);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int i;
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList<>();
        this.mHiddenViews = new ArrayList<>();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() { // from class: android.widget.Toolbar.1
            @Override // android.widget.ActionMenuView.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem item) {
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(item);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable() { // from class: android.widget.Toolbar.2
            @Override // java.lang.Runnable
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.Toolbar, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, C3132R.styleable.Toolbar, attrs, a, defStyleAttr, defStyleRes);
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
        if (contentInsetStart != Integer.MIN_VALUE || contentInsetEnd != Integer.MIN_VALUE) {
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
        this.mPopupContext = this.mContext;
        setPopupTheme(a.getResourceId(10, 0));
        Drawable navIcon = a.getDrawable(11);
        if (navIcon != null) {
            setNavigationIcon(navIcon);
        }
        CharSequence navDesc = a.getText(12);
        if (!TextUtils.isEmpty(navDesc)) {
            setNavigationContentDescription(navDesc);
        }
        Drawable logo = a.getDrawable(2);
        if (logo != null) {
            setLogo(logo);
        }
        CharSequence logoDesc = a.getText(16);
        if (!TextUtils.isEmpty(logoDesc)) {
            setLogoDescription(logoDesc);
        }
        if (a.hasValue(14)) {
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

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
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

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        ensureContentInsets();
        this.mContentInsets.setDirection(layoutDirection == 1);
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
        if (menu == null && this.mMenuView == null) {
            return;
        }
        ensureMenuView();
        MenuBuilder oldMenu = this.mMenuView.peekMenu();
        if (oldMenu == menu) {
            return;
        }
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
            outerPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            outerPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(outerPresenter);
        this.mOuterActionMenuPresenter = outerPresenter;
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
        MenuItemImpl item = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
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
                Context context = getContext();
                this.mTitleTextView = new TextView(context);
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
                Context context = getContext();
                this.mSubtitleTextView = new TextView(context);
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
            this.mNavButtonView = new ImageButton(getContext(), null, 0, this.mNavButtonStyle);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.mButtonGravity & 112);
            this.mNavButtonView.setLayoutParams(lp);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new ImageButton(getContext(), null, 0, this.mNavButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.mButtonGravity & 112);
            lp.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(lp);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener() { // from class: android.widget.Toolbar.3
                @Override // android.view.View.OnClickListener
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
        if (allowHide && this.mExpandedActionView != null) {
            v.setLayoutParams(lp);
            this.mHiddenViews.add(v);
            return;
        }
        addView(v, lp);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable state) {
        MenuItem item;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        Menu menu = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
        if (ss.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && menu != null && (item = menu.findItem(ss.expandedMenuItemId)) != null) {
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

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    @Override // android.view.View
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

    @Override // android.view.ViewGroup
    protected void onSetLayoutParams(View child, ViewGroup.LayoutParams lp) {
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
        int leftMargin = Math.max(0, leftDiff);
        int rightMargin = Math.max(0, rightDiff);
        int hMargins = leftMargin + rightMargin;
        collapsingMargins[0] = Math.max(0, -leftDiff);
        collapsingMargins[1] = Math.max(0, -rightDiff);
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, this.mPaddingLeft + this.mPaddingRight + hMargins + widthUsed, lp.width);
        int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, this.mPaddingTop + this.mPaddingBottom + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        return child.getMeasuredWidth() + hMargins;
    }

    private boolean shouldCollapse() {
        if (this.mCollapsible) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (shouldLayout(child) && child.getMeasuredWidth() > 0 && child.getMeasuredHeight() > 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int marginStartIndex;
        int marginEndIndex;
        int marginStartIndex2;
        int childState;
        int childState2;
        int height;
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
        int marginStartIndex3 = marginStartIndex;
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
        collapsingMargins[marginStartIndex3] = Math.max(0, contentInsetStart - navWidth);
        if (shouldLayout(this.mMenuView)) {
            marginStartIndex2 = 0;
            measureChildConstrained(this.mMenuView, widthMeasureSpec, width, heightMeasureSpec, 0, this.mMaxButtonHeight);
            int menuWidth = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            int height3 = Math.max(height2, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
            childState = combineMeasuredStates(childState4, this.mMenuView.getMeasuredState());
            childState2 = height3;
            height = menuWidth;
        } else {
            marginStartIndex2 = 0;
            childState = childState4;
            childState2 = height2;
            height = 0;
        }
        int contentInsetEnd = getCurrentContentInsetEnd();
        int width2 = width + Math.max(contentInsetEnd, height);
        collapsingMargins[marginEndIndex2] = Math.max(marginStartIndex2, contentInsetEnd - height);
        if (shouldLayout(this.mExpandedActionView)) {
            width2 += measureChildCollapseMargins(this.mExpandedActionView, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
            childState2 = Math.max(childState2, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
            childState3 = combineMeasuredStates(childState, this.mExpandedActionView.getMeasuredState());
        } else {
            childState3 = childState;
        }
        if (shouldLayout(this.mLogoView)) {
            width2 += measureChildCollapseMargins(this.mLogoView, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
            childState2 = Math.max(childState2, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            childState3 = combineMeasuredStates(childState3, this.mLogoView.getMeasuredState());
        }
        int childCount2 = getChildCount();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= childCount2) {
                break;
            }
            View child = getChildAt(i3);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.mViewType != 0) {
                i = i3;
                childCount = childCount2;
            } else if (!shouldLayout(child)) {
                i = i3;
                childCount = childCount2;
            } else {
                i = i3;
                childCount = childCount2;
                width2 += measureChildCollapseMargins(child, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
                int height4 = Math.max(childState2, child.getMeasuredHeight() + getVerticalMargins(child));
                childState2 = height4;
                childState3 = combineMeasuredStates(childState3, child.getMeasuredState());
            }
            i2 = i + 1;
            childCount2 = childCount;
        }
        int titleWidth = 0;
        int titleHeight2 = 0;
        int titleVertMargins = this.mTitleMarginTop + this.mTitleMarginBottom;
        int titleHorizMargins = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            measureChildCollapseMargins(this.mTitleTextView, widthMeasureSpec, width2 + titleHorizMargins, heightMeasureSpec, titleVertMargins, collapsingMargins);
            titleWidth = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            titleHeight2 = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            childState3 = combineMeasuredStates(childState3, this.mTitleTextView.getMeasuredState());
        }
        int childState5 = childState3;
        int titleWidth2 = titleWidth;
        if (shouldLayout(this.mSubtitleTextView)) {
            titleWidth2 = Math.max(titleWidth2, measureChildCollapseMargins(this.mSubtitleTextView, widthMeasureSpec, width2 + titleHorizMargins, heightMeasureSpec, titleHeight2 + titleVertMargins, collapsingMargins));
            int titleHeight3 = titleHeight2 + this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            childState5 = combineMeasuredStates(childState5, this.mSubtitleTextView.getMeasuredState());
            titleHeight = titleHeight3;
        } else {
            titleHeight = titleHeight2;
        }
        int height5 = Math.max(childState2, titleHeight);
        int width3 = width2 + titleWidth2 + getPaddingLeft() + getPaddingRight();
        int height6 = height5 + getPaddingTop() + getPaddingBottom();
        int measuredWidth = resolveSizeAndState(Math.max(width3, getSuggestedMinimumWidth()), widthMeasureSpec, (-16777216) & childState5);
        int measuredHeight = resolveSizeAndState(Math.max(height6, getSuggestedMinimumHeight()), heightMeasureSpec, childState5 << 16);
        setMeasuredDimension(measuredWidth, shouldCollapse() ? 0 : measuredHeight);
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x015c, code lost:
        if (r7.getMeasuredWidth() <= 0) goto L108;
     */
    /* JADX WARN: Removed duplicated region for block: B:64:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x025d  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingRight;
        boolean titleHasWidth;
        int width;
        int i;
        int paddingLeft;
        int alignmentHeight;
        int left;
        int spaceBelow;
        boolean isRtl = getLayoutDirection() == 1;
        int width2 = getWidth();
        int height = getHeight();
        int paddingLeft2 = getPaddingLeft();
        int paddingRight2 = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int left2 = paddingLeft2;
        int right = width2 - paddingRight2;
        int[] collapsingMargins = this.mTempMargins;
        collapsingMargins[1] = 0;
        collapsingMargins[0] = 0;
        int minHeight = getMinimumHeight();
        int alignmentHeight2 = minHeight >= 0 ? Math.min(minHeight, b - t) : 0;
        if (shouldLayout(this.mNavButtonView)) {
            if (isRtl) {
                right = layoutChildRight(this.mNavButtonView, right, collapsingMargins, alignmentHeight2);
            } else {
                left2 = layoutChildLeft(this.mNavButtonView, left2, collapsingMargins, alignmentHeight2);
            }
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            if (isRtl) {
                right = layoutChildRight(this.mCollapseButtonView, right, collapsingMargins, alignmentHeight2);
            } else {
                left2 = layoutChildLeft(this.mCollapseButtonView, left2, collapsingMargins, alignmentHeight2);
            }
        }
        if (shouldLayout(this.mMenuView)) {
            if (isRtl) {
                left2 = layoutChildLeft(this.mMenuView, left2, collapsingMargins, alignmentHeight2);
            } else {
                right = layoutChildRight(this.mMenuView, right, collapsingMargins, alignmentHeight2);
            }
        }
        int contentInsetLeft = getCurrentContentInsetLeft();
        int contentInsetRight = getCurrentContentInsetRight();
        collapsingMargins[0] = Math.max(0, contentInsetLeft - left2);
        collapsingMargins[1] = Math.max(0, contentInsetRight - ((width2 - paddingRight2) - right));
        int left3 = Math.max(left2, contentInsetLeft);
        int right2 = Math.min(right, (width2 - paddingRight2) - contentInsetRight);
        if (shouldLayout(this.mExpandedActionView)) {
            if (isRtl) {
                right2 = layoutChildRight(this.mExpandedActionView, right2, collapsingMargins, alignmentHeight2);
            } else {
                left3 = layoutChildLeft(this.mExpandedActionView, left3, collapsingMargins, alignmentHeight2);
            }
        }
        if (shouldLayout(this.mLogoView)) {
            if (isRtl) {
                right2 = layoutChildRight(this.mLogoView, right2, collapsingMargins, alignmentHeight2);
            } else {
                left3 = layoutChildLeft(this.mLogoView, left3, collapsingMargins, alignmentHeight2);
            }
        }
        boolean layoutTitle = shouldLayout(this.mTitleTextView);
        boolean layoutSubtitle = shouldLayout(this.mSubtitleTextView);
        int titleHeight = 0;
        if (layoutTitle) {
            LayoutParams lp = (LayoutParams) this.mTitleTextView.getLayoutParams();
            paddingRight = paddingRight2;
            titleHeight = 0 + lp.topMargin + this.mTitleTextView.getMeasuredHeight() + lp.bottomMargin;
        } else {
            paddingRight = paddingRight2;
        }
        if (layoutSubtitle) {
            LayoutParams lp2 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
            titleHeight += lp2.topMargin + this.mSubtitleTextView.getMeasuredHeight() + lp2.bottomMargin;
        }
        if (layoutTitle || layoutSubtitle) {
            TextView textView = layoutTitle ? this.mTitleTextView : this.mSubtitleTextView;
            View bottomChild = layoutSubtitle ? this.mSubtitleTextView : this.mTitleTextView;
            LayoutParams toplp = (LayoutParams) textView.getLayoutParams();
            LayoutParams bottomlp = (LayoutParams) bottomChild.getLayoutParams();
            if (layoutTitle) {
                View bottomChild2 = this.mTitleTextView;
            }
            if (!layoutSubtitle || this.mSubtitleTextView.getMeasuredWidth() <= 0) {
                titleHasWidth = false;
                width = width2;
                i = this.mGravity & 112;
                paddingLeft = paddingLeft2;
                if (i != 48) {
                    alignmentHeight = alignmentHeight2;
                    left = left3;
                    spaceBelow = getPaddingTop() + toplp.topMargin + this.mTitleMarginTop;
                } else if (i != 80) {
                    int space = (height - paddingTop) - paddingBottom;
                    int spaceAbove = (space - titleHeight) / 2;
                    int space2 = toplp.topMargin;
                    alignmentHeight = alignmentHeight2;
                    if (spaceAbove < space2 + this.mTitleMarginTop) {
                        spaceAbove = toplp.topMargin + this.mTitleMarginTop;
                        left = left3;
                    } else {
                        int spaceBelow2 = (((height - paddingBottom) - titleHeight) - spaceAbove) - paddingTop;
                        left = left3;
                        if (spaceBelow2 < toplp.bottomMargin + this.mTitleMarginBottom) {
                            spaceAbove = Math.max(0, spaceAbove - ((bottomlp.bottomMargin + this.mTitleMarginBottom) - spaceBelow2));
                        }
                    }
                    spaceBelow = paddingTop + spaceAbove;
                } else {
                    alignmentHeight = alignmentHeight2;
                    left = left3;
                    spaceBelow = (((height - paddingBottom) - bottomlp.bottomMargin) - this.mTitleMarginBottom) - titleHeight;
                }
                if (isRtl) {
                    int ld = (titleHasWidth ? this.mTitleMarginStart : 0) - collapsingMargins[0];
                    left3 = left + Math.max(0, ld);
                    collapsingMargins[0] = Math.max(0, -ld);
                    int titleLeft = left3;
                    int subtitleLeft = left3;
                    if (layoutTitle) {
                        int titleRight = this.mTitleTextView.getMeasuredWidth() + titleLeft;
                        int titleBottom = this.mTitleTextView.getMeasuredHeight() + spaceBelow;
                        this.mTitleTextView.layout(titleLeft, spaceBelow, titleRight, titleBottom);
                        titleLeft = titleRight + this.mTitleMarginEnd;
                        spaceBelow = titleBottom + ((LayoutParams) this.mTitleTextView.getLayoutParams()).bottomMargin;
                    }
                    if (layoutSubtitle) {
                        LayoutParams lp3 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                        int titleTop = spaceBelow + lp3.topMargin;
                        int subtitleRight = this.mSubtitleTextView.getMeasuredWidth() + subtitleLeft;
                        int subtitleBottom = this.mSubtitleTextView.getMeasuredHeight() + titleTop;
                        this.mSubtitleTextView.layout(subtitleLeft, titleTop, subtitleRight, subtitleBottom);
                        subtitleLeft = subtitleRight + this.mTitleMarginEnd;
                        int titleTop2 = subtitleBottom + lp3.bottomMargin;
                    }
                    if (titleHasWidth) {
                        left3 = Math.max(titleLeft, subtitleLeft);
                    }
                } else {
                    int rd = (titleHasWidth ? this.mTitleMarginStart : 0) - collapsingMargins[1];
                    right2 -= Math.max(0, rd);
                    collapsingMargins[1] = Math.max(0, -rd);
                    int titleRight2 = right2;
                    int subtitleRight2 = right2;
                    if (layoutTitle) {
                        int titleLeft2 = titleRight2 - this.mTitleTextView.getMeasuredWidth();
                        int titleBottom2 = this.mTitleTextView.getMeasuredHeight() + spaceBelow;
                        this.mTitleTextView.layout(titleLeft2, spaceBelow, titleRight2, titleBottom2);
                        titleRight2 = titleLeft2 - this.mTitleMarginEnd;
                        spaceBelow = titleBottom2 + ((LayoutParams) this.mTitleTextView.getLayoutParams()).bottomMargin;
                    }
                    if (layoutSubtitle) {
                        LayoutParams lp4 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                        int titleTop3 = spaceBelow + lp4.topMargin;
                        int subtitleBottom2 = this.mSubtitleTextView.getMeasuredHeight() + titleTop3;
                        this.mSubtitleTextView.layout(subtitleRight2 - this.mSubtitleTextView.getMeasuredWidth(), titleTop3, subtitleRight2, subtitleBottom2);
                        subtitleRight2 -= this.mTitleMarginEnd;
                        int titleTop4 = subtitleBottom2 + lp4.bottomMargin;
                    }
                    if (titleHasWidth) {
                        right2 = Math.min(titleRight2, subtitleRight2);
                    }
                    left3 = left;
                }
            }
            titleHasWidth = true;
            width = width2;
            i = this.mGravity & 112;
            paddingLeft = paddingLeft2;
            if (i != 48) {
            }
            if (isRtl) {
            }
        } else {
            alignmentHeight = alignmentHeight2;
            width = width2;
            paddingLeft = paddingLeft2;
        }
        addCustomViewsWithGravity(this.mTempViews, 3);
        int i2 = this.mTempViews.size();
        for (int i3 = 0; i3 < i2; i3++) {
            left3 = layoutChildLeft(this.mTempViews.get(i3), left3, collapsingMargins, alignmentHeight);
        }
        int alignmentHeight3 = alignmentHeight;
        addCustomViewsWithGravity(this.mTempViews, 5);
        int rightViewsCount = this.mTempViews.size();
        for (int i4 = 0; i4 < rightViewsCount; i4++) {
            right2 = layoutChildRight(this.mTempViews.get(i4), right2, collapsingMargins, alignmentHeight3);
        }
        addCustomViewsWithGravity(this.mTempViews, 1);
        int centerViewsWidth = getViewListMeasuredWidth(this.mTempViews, collapsingMargins);
        int parentCenter = paddingLeft + (((width - paddingLeft) - paddingRight) / 2);
        int halfCenterViewsWidth = centerViewsWidth / 2;
        int centerLeft = parentCenter - halfCenterViewsWidth;
        int centerRight = centerLeft + centerViewsWidth;
        if (centerLeft < left3) {
            centerLeft = left3;
        } else if (centerRight > right2) {
            centerLeft -= centerRight - right2;
        }
        int centerViewsCount = this.mTempViews.size();
        int i5 = 0;
        while (true) {
            int i6 = i5;
            int leftViewsCount = i2;
            if (i6 < centerViewsCount) {
                centerLeft = layoutChildLeft(this.mTempViews.get(i6), centerLeft, collapsingMargins, alignmentHeight3);
                i5 = i6 + 1;
                i2 = leftViewsCount;
                rightViewsCount = rightViewsCount;
            } else {
                this.mTempViews.clear();
                return;
            }
        }
    }

    private int getViewListMeasuredWidth(List<View> views, int[] collapsingMargins) {
        int collapseLeft = collapsingMargins[0];
        int collapseRight = collapsingMargins[1];
        int count = views.size();
        int width = 0;
        int collapseRight2 = collapseRight;
        int collapseLeft2 = collapseLeft;
        for (int collapseLeft3 = 0; collapseLeft3 < count; collapseLeft3++) {
            View v = views.get(collapseLeft3);
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
        if (childVerticalGravity != 48) {
            if (childVerticalGravity == 80) {
                return (((getHeight() - getPaddingBottom()) - childHeight) - lp.bottomMargin) - alignmentOffset;
            }
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int height = getHeight();
            int space = (height - paddingTop) - paddingBottom;
            int spaceAbove = (space - childHeight) / 2;
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
        return getPaddingTop() - alignmentOffset;
    }

    private int getChildVerticalGravity(int gravity) {
        int vgrav = gravity & 112;
        if (vgrav == 16 || vgrav == 48 || vgrav == 80) {
            return vgrav;
        }
        return this.mGravity & 112;
    }

    private void addCustomViewsWithGravity(List<View> views, int gravity) {
        boolean isRtl = getLayoutDirection() == 1;
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
        int absGrav = Gravity.getAbsoluteGravity(gravity, ld);
        int hGrav = absGrav & 7;
        if (hGrav == 1 || hGrav == 3 || hGrav == 5) {
            return hGrav;
        }
        return ld == 1 ? 5 : 3;
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

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
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

    void removeChildrenForExpandedActionView() {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.mViewType != 2 && child != this.mMenuView) {
                removeViewAt(i);
                this.mHiddenViews.add(child);
            }
        }
    }

    void addChildrenForExpandedActionView() {
        int count = this.mHiddenViews.size();
        for (int i = count - 1; i >= 0; i--) {
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

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    /* loaded from: classes4.dex */
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
            super(source);
            this.mViewType = 0;
            copyMarginsFrom(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.mViewType = 0;
        }
    }

    /* loaded from: classes4.dex */
    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.Toolbar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
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

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.p007os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.expandedMenuItemId);
            out.writeInt(this.isOverflowOpen ? 1 : 0);
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

        @Override // com.android.internal.view.menu.MenuPresenter
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
