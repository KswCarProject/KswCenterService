package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import com.android.internal.view.menu.MenuPresenter;

public class MenuPopupHelper implements MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private View mAnchorView;
    private final Context mContext;
    private int mDropDownGravity;
    @UnsupportedAppUsage
    private boolean mForceShowIcon;
    private final PopupWindow.OnDismissListener mInternalOnDismissListener;
    private final MenuBuilder mMenu;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private MenuPopup mPopup;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;

    @UnsupportedAppUsage
    public MenuPopupHelper(Context context, MenuBuilder menu) {
        this(context, menu, (View) null, false, 16843520, 0);
    }

    @UnsupportedAppUsage
    public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView) {
        this(context, menu, anchorView, false, 16843520, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly, int popupStyleAttr) {
        this(context, menu, anchorView, overflowOnly, popupStyleAttr, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly, int popupStyleAttr, int popupStyleRes) {
        this.mDropDownGravity = 8388611;
        this.mInternalOnDismissListener = new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                MenuPopupHelper.this.onDismiss();
            }
        };
        this.mContext = context;
        this.mMenu = menu;
        this.mAnchorView = anchorView;
        this.mOverflowOnly = overflowOnly;
        this.mPopupStyleAttr = popupStyleAttr;
        this.mPopupStyleRes = popupStyleRes;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    @UnsupportedAppUsage
    public void setAnchorView(View anchor) {
        this.mAnchorView = anchor;
    }

    @UnsupportedAppUsage
    public void setForceShowIcon(boolean forceShowIcon) {
        this.mForceShowIcon = forceShowIcon;
        if (this.mPopup != null) {
            this.mPopup.setForceShowIcon(forceShowIcon);
        }
    }

    @UnsupportedAppUsage
    public void setGravity(int gravity) {
        this.mDropDownGravity = gravity;
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    @UnsupportedAppUsage
    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int x, int y) {
        if (!tryShow(x, y)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    @UnsupportedAppUsage
    public MenuPopup getPopup() {
        if (this.mPopup == null) {
            this.mPopup = createPopup();
        }
        return this.mPopup;
    }

    @UnsupportedAppUsage
    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int x, int y) {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(x, y, true, true);
        return true;
    }

    /* JADX WARNING: type inference failed for: r6v2, types: [com.android.internal.view.menu.CascadingMenuPopup] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.android.internal.view.menu.MenuPopup createPopup() {
        /*
            r14 = this;
            android.content.Context r0 = r14.mContext
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService((java.lang.String) r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.view.Display r1 = r0.getDefaultDisplay()
            android.graphics.Point r2 = new android.graphics.Point
            r2.<init>()
            r1.getRealSize(r2)
            int r3 = r2.x
            int r4 = r2.y
            int r3 = java.lang.Math.min(r3, r4)
            android.content.Context r4 = r14.mContext
            android.content.res.Resources r4 = r4.getResources()
            r5 = 17105031(0x1050087, float:2.442862E-38)
            int r4 = r4.getDimensionPixelSize(r5)
            if (r3 < r4) goto L_0x0030
            r5 = 1
            goto L_0x0031
        L_0x0030:
            r5 = 0
        L_0x0031:
            if (r5 == 0) goto L_0x0044
            com.android.internal.view.menu.CascadingMenuPopup r12 = new com.android.internal.view.menu.CascadingMenuPopup
            android.content.Context r7 = r14.mContext
            android.view.View r8 = r14.mAnchorView
            int r9 = r14.mPopupStyleAttr
            int r10 = r14.mPopupStyleRes
            boolean r11 = r14.mOverflowOnly
            r6 = r12
            r6.<init>(r7, r8, r9, r10, r11)
            goto L_0x0056
        L_0x0044:
            com.android.internal.view.menu.StandardMenuPopup r6 = new com.android.internal.view.menu.StandardMenuPopup
            android.content.Context r8 = r14.mContext
            com.android.internal.view.menu.MenuBuilder r9 = r14.mMenu
            android.view.View r10 = r14.mAnchorView
            int r11 = r14.mPopupStyleAttr
            int r12 = r14.mPopupStyleRes
            boolean r13 = r14.mOverflowOnly
            r7 = r6
            r7.<init>(r8, r9, r10, r11, r12, r13)
        L_0x0056:
            com.android.internal.view.menu.MenuBuilder r7 = r14.mMenu
            r6.addMenu(r7)
            android.widget.PopupWindow$OnDismissListener r7 = r14.mInternalOnDismissListener
            r6.setOnDismissListener(r7)
            android.view.View r7 = r14.mAnchorView
            r6.setAnchorView(r7)
            com.android.internal.view.menu.MenuPresenter$Callback r7 = r14.mPresenterCallback
            r6.setCallback(r7)
            boolean r7 = r14.mForceShowIcon
            r6.setForceShowIcon(r7)
            int r7 = r14.mDropDownGravity
            r6.setGravity(r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.menu.MenuPopupHelper.createPopup():com.android.internal.view.menu.MenuPopup");
    }

    private void showPopup(int xOffset, int yOffset, boolean useOffsets, boolean showTitle) {
        MenuPopup popup = getPopup();
        popup.setShowTitle(showTitle);
        if (useOffsets) {
            if ((Gravity.getAbsoluteGravity(this.mDropDownGravity, this.mAnchorView.getLayoutDirection()) & 7) == 5) {
                xOffset -= this.mAnchorView.getWidth();
            }
            popup.setHorizontalOffset(xOffset);
            popup.setVerticalOffset(yOffset);
            int halfSize = (int) ((48.0f * this.mContext.getResources().getDisplayMetrics().density) / 2.0f);
            popup.setEpicenterBounds(new Rect(xOffset - halfSize, yOffset - halfSize, xOffset + halfSize, yOffset + halfSize));
        }
        popup.show();
    }

    @UnsupportedAppUsage
    public void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDismiss() {
        this.mPopup = null;
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss();
        }
    }

    public boolean isShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    public void setPresenterCallback(MenuPresenter.Callback cb) {
        this.mPresenterCallback = cb;
        if (this.mPopup != null) {
            this.mPopup.setCallback(cb);
        }
    }
}
