package android.widget;

import android.content.Context;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import com.android.internal.view.menu.ListMenuItemView;
import com.android.internal.view.menu.MenuAdapter;
import com.android.internal.view.menu.MenuBuilder;

public class MenuPopupWindow extends ListPopupWindow implements MenuItemHoverListener {
    private MenuItemHoverListener mHoverListener;

    public MenuPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: package-private */
    public DropDownListView createDropDownListView(Context context, boolean hijackFocus) {
        MenuDropDownListView view = new MenuDropDownListView(context, hijackFocus);
        view.setHoverListener(this);
        return view;
    }

    public void setEnterTransition(Transition enterTransition) {
        this.mPopup.setEnterTransition(enterTransition);
    }

    public void setExitTransition(Transition exitTransition) {
        this.mPopup.setExitTransition(exitTransition);
    }

    public void setHoverListener(MenuItemHoverListener hoverListener) {
        this.mHoverListener = hoverListener;
    }

    public void setTouchModal(boolean touchModal) {
        this.mPopup.setTouchModal(touchModal);
    }

    public void onItemHoverEnter(MenuBuilder menu, MenuItem item) {
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverEnter(menu, item);
        }
    }

    public void onItemHoverExit(MenuBuilder menu, MenuItem item) {
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverExit(menu, item);
        }
    }

    public static class MenuDropDownListView extends DropDownListView {
        final int mAdvanceKey;
        private MenuItemHoverListener mHoverListener;
        private MenuItem mHoveredMenuItem;
        final int mRetreatKey;

        public MenuDropDownListView(Context context, boolean hijackFocus) {
            super(context, hijackFocus);
            if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
                return;
            }
            this.mAdvanceKey = 22;
            this.mRetreatKey = 21;
        }

        public void setHoverListener(MenuItemHoverListener hoverListener) {
            this.mHoverListener = hoverListener;
        }

        public void clearSelection() {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            ListMenuItemView selectedItem = (ListMenuItemView) getSelectedView();
            if (selectedItem != null && keyCode == this.mAdvanceKey) {
                if (selectedItem.isEnabled() && selectedItem.getItemData().hasSubMenu()) {
                    performItemClick(selectedItem, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            } else if (selectedItem == null || keyCode != this.mRetreatKey) {
                return super.onKeyDown(keyCode, event);
            } else {
                setSelectedPositionInt(-1);
                setNextSelectedPositionInt(-1);
                ((MenuAdapter) getAdapter()).getAdapterMenu().close(false);
                return true;
            }
        }

        /* JADX WARNING: type inference failed for: r3v3, types: [android.widget.ListAdapter] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onHoverEvent(android.view.MotionEvent r8) {
            /*
                r7 = this;
                android.widget.MenuItemHoverListener r0 = r7.mHoverListener
                if (r0 == 0) goto L_0x005f
                android.widget.ListAdapter r0 = r7.getAdapter()
                boolean r1 = r0 instanceof android.widget.HeaderViewListAdapter
                if (r1 == 0) goto L_0x001b
                r1 = r0
                android.widget.HeaderViewListAdapter r1 = (android.widget.HeaderViewListAdapter) r1
                int r2 = r1.getHeadersCount()
                android.widget.ListAdapter r3 = r1.getWrappedAdapter()
                r1 = r3
                com.android.internal.view.menu.MenuAdapter r1 = (com.android.internal.view.menu.MenuAdapter) r1
                goto L_0x001f
            L_0x001b:
                r2 = 0
                r1 = r0
                com.android.internal.view.menu.MenuAdapter r1 = (com.android.internal.view.menu.MenuAdapter) r1
            L_0x001f:
                r3 = 0
                int r4 = r8.getAction()
                r5 = 10
                if (r4 == r5) goto L_0x0047
                float r4 = r8.getX()
                int r4 = (int) r4
                float r5 = r8.getY()
                int r5 = (int) r5
                int r4 = r7.pointToPosition(r4, r5)
                r5 = -1
                if (r4 == r5) goto L_0x0047
                int r5 = r4 - r2
                if (r5 < 0) goto L_0x0047
                int r6 = r1.getCount()
                if (r5 >= r6) goto L_0x0047
                com.android.internal.view.menu.MenuItemImpl r3 = r1.getItem((int) r5)
            L_0x0047:
                android.view.MenuItem r4 = r7.mHoveredMenuItem
                if (r4 == r3) goto L_0x005f
                com.android.internal.view.menu.MenuBuilder r5 = r1.getAdapterMenu()
                if (r4 == 0) goto L_0x0056
                android.widget.MenuItemHoverListener r6 = r7.mHoverListener
                r6.onItemHoverExit(r5, r4)
            L_0x0056:
                r7.mHoveredMenuItem = r3
                if (r3 == 0) goto L_0x005f
                android.widget.MenuItemHoverListener r6 = r7.mHoverListener
                r6.onItemHoverEnter(r5, r3)
            L_0x005f:
                boolean r0 = super.onHoverEvent(r8)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.MenuPopupWindow.MenuDropDownListView.onHoverEvent(android.view.MotionEvent):boolean");
        }
    }
}
