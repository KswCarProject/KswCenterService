package android.support.p014v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.p011v4.view.ViewCompat;
import android.support.p014v7.appcompat.C2132R;
import android.support.p014v7.view.menu.MenuView;
import android.support.p014v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.ListMenuItemView */
/* loaded from: classes3.dex */
public class ListMenuItemView extends LinearLayout implements MenuView.ItemView {
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private CheckBox mCheckBox;
    private boolean mForceShowIcon;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private MenuItemImpl mItemData;
    private int mMenuType;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private Drawable mSubMenuArrow;
    private ImageView mSubMenuArrowView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    public ListMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, C2132R.attr.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, C2132R.styleable.MenuView, defStyleAttr, 0);
        this.mBackground = a.getDrawable(C2132R.styleable.MenuView_android_itemBackground);
        this.mTextAppearance = a.getResourceId(C2132R.styleable.MenuView_android_itemTextAppearance, -1);
        this.mPreserveIconSpacing = a.getBoolean(C2132R.styleable.MenuView_preserveIconSpacing, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = a.getDrawable(C2132R.styleable.MenuView_subMenuArrow);
        a.recycle();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewCompat.setBackground(this, this.mBackground);
        this.mTitleView = (TextView) findViewById(C2132R.C2134id.title);
        if (this.mTextAppearance != -1) {
            this.mTitleView.setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
        }
        this.mShortcutView = (TextView) findViewById(C2132R.C2134id.shortcut);
        this.mSubMenuArrowView = (ImageView) findViewById(C2132R.C2134id.submenuarrow);
        if (this.mSubMenuArrowView != null) {
            this.mSubMenuArrowView.setImageDrawable(this.mSubMenuArrow);
        }
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        this.mMenuType = menuType;
        setVisibility(itemData.isVisible() ? 0 : 8);
        setTitle(itemData.getTitleForItemView(this));
        setCheckable(itemData.isCheckable());
        setShortcut(itemData.shouldShowShortcut(), itemData.getShortcut());
        setIcon(itemData.getIcon());
        setEnabled(itemData.isEnabled());
        setSubMenuArrowVisible(itemData.hasSubMenu());
        setContentDescription(itemData.getContentDescription());
    }

    public void setForceShowIcon(boolean forceShow) {
        this.mForceShowIcon = forceShow;
        this.mPreserveIconSpacing = forceShow;
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public void setTitle(CharSequence title) {
        if (title != null) {
            this.mTitleView.setText(title);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public void setCheckable(boolean checkable) {
        CompoundButton compoundButton;
        CompoundButton otherCompoundButton;
        if (!checkable && this.mRadioButton == null && this.mCheckBox == null) {
            return;
        }
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
            otherCompoundButton = this.mCheckBox;
        } else {
            CompoundButton compoundButton2 = this.mCheckBox;
            if (compoundButton2 == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
            otherCompoundButton = this.mRadioButton;
        }
        if (checkable) {
            compoundButton.setChecked(this.mItemData.isChecked());
            int newVisibility = checkable ? 0 : 8;
            if (compoundButton.getVisibility() != newVisibility) {
                compoundButton.setVisibility(newVisibility);
            }
            if (otherCompoundButton != null && otherCompoundButton.getVisibility() != 8) {
                otherCompoundButton.setVisibility(8);
                return;
            }
            return;
        }
        if (this.mCheckBox != null) {
            this.mCheckBox.setVisibility(8);
        }
        if (this.mRadioButton != null) {
            this.mRadioButton.setVisibility(8);
        }
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public void setChecked(boolean checked) {
        CompoundButton compoundButton;
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
        }
        compoundButton.setChecked(checked);
    }

    private void setSubMenuArrowVisible(boolean hasSubmenu) {
        if (this.mSubMenuArrowView != null) {
            this.mSubMenuArrowView.setVisibility(hasSubmenu ? 0 : 8);
        }
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public void setShortcut(boolean showShortcut, char shortcutKey) {
        int newVisibility = (showShortcut && this.mItemData.shouldShowShortcut()) ? 0 : 8;
        if (newVisibility == 0) {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != newVisibility) {
            this.mShortcutView.setVisibility(newVisibility);
        }
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public void setIcon(Drawable icon) {
        boolean showIcon = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (!showIcon && !this.mPreserveIconSpacing) {
            return;
        }
        if (this.mIconView == null && icon == null && !this.mPreserveIconSpacing) {
            return;
        }
        if (this.mIconView == null) {
            insertIconView();
        }
        if (icon != null || this.mPreserveIconSpacing) {
            this.mIconView.setImageDrawable(showIcon ? icon : null);
            if (this.mIconView.getVisibility() != 0) {
                this.mIconView.setVisibility(0);
                return;
            }
            return;
        }
        this.mIconView.setVisibility(8);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams lp = getLayoutParams();
            LinearLayout.LayoutParams iconLp = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if (lp.height > 0 && iconLp.width <= 0) {
                iconLp.width = lp.height;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void insertIconView() {
        LayoutInflater inflater = getInflater();
        this.mIconView = (ImageView) inflater.inflate(C2132R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
        addView(this.mIconView, 0);
    }

    private void insertRadioButton() {
        LayoutInflater inflater = getInflater();
        this.mRadioButton = (RadioButton) inflater.inflate(C2132R.layout.abc_list_menu_item_radio, (ViewGroup) this, false);
        addView(this.mRadioButton);
    }

    private void insertCheckBox() {
        LayoutInflater inflater = getInflater();
        this.mCheckBox = (CheckBox) inflater.inflate(C2132R.layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
        addView(this.mCheckBox);
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // android.support.p014v7.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return this.mForceShowIcon;
    }

    private LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        return this.mInflater;
    }
}
