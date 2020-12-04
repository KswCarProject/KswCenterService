package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import com.android.internal.R;
import com.android.internal.view.menu.ShowableListMenu;

public class ListPopupWindow implements ShowableListMenu {
    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    private final ListSelectorHider mHideSelector;
    private boolean mIsAnimatedFromAnchor;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    @UnsupportedAppUsage
    PopupWindow mPopup;
    private int mPromptPosition;
    private View mPromptView;
    /* access modifiers changed from: private */
    public final ResizePopupRunnable mResizePopupRunnable;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    public ListPopupWindow(Context context) {
        this(context, (AttributeSet) null, 16843519, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 16843519, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mIsAnimatedFromAnchor = true;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.mListItemExpandMaximum = Integer.MAX_VALUE;
        this.mPromptPosition = 0;
        this.mResizePopupRunnable = new ResizePopupRunnable();
        this.mTouchInterceptor = new PopupTouchInterceptor();
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListPopupWindow, defStyleAttr, defStyleRes);
        this.mDropDownHorizontalOffset = a.getDimensionPixelOffset(0, 0);
        this.mDropDownVerticalOffset = a.getDimensionPixelOffset(1, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        a.recycle();
        this.mPopup = new PopupWindow(context, attrs, defStyleAttr, defStyleRes);
        this.mPopup.setInputMethodMode(1);
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = adapter;
        if (this.mAdapter != null) {
            adapter.registerDataSetObserver(this.mObserver);
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
        }
    }

    public void setPromptPosition(int position) {
        this.mPromptPosition = position;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public void setModal(boolean modal) {
        this.mModal = modal;
        this.mPopup.setFocusable(modal);
    }

    public boolean isModal() {
        return this.mModal;
    }

    @UnsupportedAppUsage
    public void setForceIgnoreOutsideTouch(boolean forceIgnoreOutsideTouch) {
        this.mForceIgnoreOutsideTouch = forceIgnoreOutsideTouch;
    }

    @UnsupportedAppUsage
    public void setDropDownAlwaysVisible(boolean dropDownAlwaysVisible) {
        this.mDropDownAlwaysVisible = dropDownAlwaysVisible;
    }

    @UnsupportedAppUsage
    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public void setSoftInputMode(int mode) {
        this.mPopup.setSoftInputMode(mode);
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public void setListSelector(Drawable selector) {
        this.mDropDownListHighlight = selector;
    }

    public Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public void setBackgroundDrawable(Drawable d) {
        this.mPopup.setBackgroundDrawable(d);
    }

    public void setAnimationStyle(int animationStyle) {
        this.mPopup.setAnimationStyle(animationStyle);
    }

    public int getAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public void setAnchorView(View anchor) {
        this.mDropDownAnchorView = anchor;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int offset) {
        this.mDropDownHorizontalOffset = offset;
    }

    public int getVerticalOffset() {
        if (!this.mDropDownVerticalOffsetSet) {
            return 0;
        }
        return this.mDropDownVerticalOffset;
    }

    public void setVerticalOffset(int offset) {
        this.mDropDownVerticalOffset = offset;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setEpicenterBounds(Rect bounds) {
        this.mEpicenterBounds = bounds != null ? new Rect(bounds) : null;
    }

    public Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public void setDropDownGravity(int gravity) {
        this.mDropDownGravity = gravity;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public void setWidth(int width) {
        this.mDropDownWidth = width;
    }

    public void setContentWidth(int width) {
        Drawable popupBackground = this.mPopup.getBackground();
        if (popupBackground != null) {
            popupBackground.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + width;
            return;
        }
        setWidth(width);
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public void setHeight(int height) {
        if (!(height >= 0 || -2 == height || -1 == height)) {
            if (this.mContext.getApplicationInfo().targetSdkVersion < 26) {
                Log.e(TAG, "Negative value " + height + " passed to ListPopupWindow#setHeight produces undefined results");
            } else {
                throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
            }
        }
        this.mDropDownHeight = height;
    }

    public void setWindowLayoutType(int layoutType) {
        this.mDropDownWindowLayoutType = layoutType;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
        this.mItemSelectedListener = selectedListener;
    }

    public void setPromptView(View prompt) {
        boolean showing = isShowing();
        if (showing) {
            removePromptView();
        }
        this.mPromptView = prompt;
        if (showing) {
            show();
        }
    }

    public void postShow() {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    public void show() {
        int widthSpec;
        int heightSpec;
        int widthSpec2;
        int heightSpec2;
        int height = buildDropDown();
        boolean noInputMethod = isInputMethodNotNeeded();
        this.mPopup.setAllowScrollingAnchorParent(!noInputMethod);
        this.mPopup.setWindowLayoutType(this.mDropDownWindowLayoutType);
        boolean z = true;
        if (!this.mPopup.isShowing()) {
            if (this.mDropDownWidth == -1) {
                widthSpec = -1;
            } else if (this.mDropDownWidth == -2) {
                widthSpec = getAnchorView().getWidth();
            } else {
                widthSpec = this.mDropDownWidth;
            }
            if (this.mDropDownHeight == -1) {
                heightSpec = -1;
            } else if (this.mDropDownHeight == -2) {
                heightSpec = height;
            } else {
                heightSpec = this.mDropDownHeight;
            }
            this.mPopup.setWidth(widthSpec);
            this.mPopup.setHeight(heightSpec);
            this.mPopup.setIsClippedToScreen(true);
            PopupWindow popupWindow = this.mPopup;
            if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
                z = false;
            }
            popupWindow.setOutsideTouchable(z);
            this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
            this.mPopup.setEpicenterBounds(this.mEpicenterBounds);
            if (this.mOverlapAnchorSet) {
                this.mPopup.setOverlapAnchor(this.mOverlapAnchor);
            }
            this.mPopup.showAsDropDown(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
            this.mDropDownList.setSelection(-1);
            this.mPopup.getContentView().restoreDefaultFocus();
            if (!this.mModal || this.mDropDownList.isInTouchMode()) {
                clearListSelection();
            }
            if (!this.mModal) {
                this.mHandler.post(this.mHideSelector);
            }
        } else if (getAnchorView().isAttachedToWindow()) {
            if (this.mDropDownWidth == -1) {
                widthSpec2 = -1;
            } else if (this.mDropDownWidth == -2) {
                widthSpec2 = getAnchorView().getWidth();
            } else {
                widthSpec2 = this.mDropDownWidth;
            }
            if (this.mDropDownHeight == -1) {
                heightSpec2 = noInputMethod ? height : -1;
                if (noInputMethod) {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                    this.mPopup.setHeight(0);
                } else {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                    this.mPopup.setHeight(-1);
                }
            } else {
                heightSpec2 = this.mDropDownHeight == -2 ? height : this.mDropDownHeight;
            }
            PopupWindow popupWindow2 = this.mPopup;
            if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
                z = false;
            }
            popupWindow2.setOutsideTouchable(z);
            this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, widthSpec2 < 0 ? -1 : widthSpec2, heightSpec2 < 0 ? -1 : heightSpec2);
            this.mPopup.getContentView().restoreDefaultFocus();
        }
    }

    public void dismiss() {
        this.mPopup.dismiss();
        removePromptView();
        this.mPopup.setContentView((View) null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mPopup.setOnDismissListener(listener);
    }

    private void removePromptView() {
        if (this.mPromptView != null) {
            ViewParent parent = this.mPromptView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mPromptView);
            }
        }
    }

    public void setInputMethodMode(int mode) {
        this.mPopup.setInputMethodMode(mode);
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    public void setSelection(int position) {
        DropDownListView list = this.mDropDownList;
        if (isShowing() && list != null) {
            list.setListSelectionHidden(false);
            list.setSelection(position);
            if (list.getChoiceMode() != 0) {
                list.setItemChecked(position, true);
            }
        }
    }

    public void clearListSelection() {
        DropDownListView list = this.mDropDownList;
        if (list != null) {
            list.setListSelectionHidden(true);
            list.hideSelector();
            list.requestLayout();
        }
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int position) {
        if (!isShowing()) {
            return false;
        }
        if (this.mItemClickListener == null) {
            return true;
        }
        DropDownListView list = this.mDropDownList;
        View child = list.getChildAt(position - list.getFirstVisiblePosition());
        DropDownListView dropDownListView = list;
        View view = child;
        int i = position;
        this.mItemClickListener.onItemClick(dropDownListView, view, i, list.getAdapter().getItemId(position));
        return true;
    }

    public Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedItem();
    }

    public int getSelectedItemPosition() {
        if (!isShowing()) {
            return -1;
        }
        return this.mDropDownList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        if (!isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.mDropDownList.getSelectedItemId();
    }

    public View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedView();
    }

    public ListView getListView() {
        return this.mDropDownList;
    }

    /* access modifiers changed from: package-private */
    public DropDownListView createDropDownListView(Context context, boolean hijackFocus) {
        return new DropDownListView(context, hijackFocus);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setListItemExpandMax(int max) {
        this.mListItemExpandMaximum = max;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int i;
        if (isShowing() && keyCode != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !KeyEvent.isConfirmKey(keyCode))) {
            int curIndex = this.mDropDownList.getSelectedItemPosition();
            boolean below = !this.mPopup.isAboveAnchor();
            ListAdapter adapter = this.mAdapter;
            int firstItem = Integer.MAX_VALUE;
            int lastItem = Integer.MIN_VALUE;
            if (adapter != null) {
                boolean allEnabled = adapter.areAllItemsEnabled();
                firstItem = allEnabled ? 0 : this.mDropDownList.lookForSelectablePosition(0, true);
                if (allEnabled) {
                    i = adapter.getCount() - 1;
                } else {
                    i = this.mDropDownList.lookForSelectablePosition(adapter.getCount() - 1, false);
                }
                lastItem = i;
            }
            if ((!below || keyCode != 19 || curIndex > firstItem) && (below || keyCode != 20 || curIndex < lastItem)) {
                this.mDropDownList.setListSelectionHidden(false);
                if (this.mDropDownList.onKeyDown(keyCode, event)) {
                    this.mPopup.setInputMethodMode(2);
                    this.mDropDownList.requestFocusFromTouch();
                    show();
                    if (!(keyCode == 23 || keyCode == 66)) {
                        switch (keyCode) {
                            case 19:
                            case 20:
                                break;
                        }
                    }
                    return true;
                } else if (!below || keyCode != 20) {
                    if (!below && keyCode == 19 && curIndex == firstItem) {
                        return true;
                    }
                    return false;
                } else if (curIndex == lastItem) {
                    return true;
                }
            } else {
                clearListSelection();
                this.mPopup.setInputMethodMode(1);
                show();
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!isShowing() || this.mDropDownList.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean consumed = this.mDropDownList.onKeyUp(keyCode, event);
        if (consumed && KeyEvent.isConfirmKey(keyCode)) {
            dismiss();
        }
        return consumed;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !isShowing()) {
            return false;
        }
        View anchorView = this.mDropDownAnchorView;
        if (event.getAction() == 0 && event.getRepeatCount() == 0) {
            KeyEvent.DispatcherState state = anchorView.getKeyDispatcherState();
            if (state != null) {
                state.startTracking(event, this);
            }
            return true;
        } else if (event.getAction() != 1) {
            return false;
        } else {
            KeyEvent.DispatcherState state2 = anchorView.getKeyDispatcherState();
            if (state2 != null) {
                state2.handleUpEvent(event);
            }
            if (!event.isTracking() || event.isCanceled()) {
                return false;
            }
            dismiss();
            return true;
        }
    }

    public View.OnTouchListener createDragToOpenListener(View src) {
        return new ForwardingListener(src) {
            public ShowableListMenu getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v21, resolved type: android.widget.DropDownListView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v22, resolved type: android.widget.DropDownListView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: android.widget.LinearLayout} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v23, resolved type: android.widget.DropDownListView} */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int buildDropDown() {
        /*
            r15 = this;
            r0 = 0
            android.widget.DropDownListView r1 = r15.mDropDownList
            r2 = -1
            r3 = 0
            r4 = 1
            if (r1 != 0) goto L_0x00c6
            android.content.Context r1 = r15.mContext
            android.widget.ListPopupWindow$2 r5 = new android.widget.ListPopupWindow$2
            r5.<init>()
            r15.mShowDropDownRunnable = r5
            boolean r5 = r15.mModal
            r5 = r5 ^ r4
            android.widget.DropDownListView r5 = r15.createDropDownListView(r1, r5)
            r15.mDropDownList = r5
            android.graphics.drawable.Drawable r5 = r15.mDropDownListHighlight
            if (r5 == 0) goto L_0x0025
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.graphics.drawable.Drawable r6 = r15.mDropDownListHighlight
            r5.setSelector((android.graphics.drawable.Drawable) r6)
        L_0x0025:
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.widget.ListAdapter r6 = r15.mAdapter
            r5.setAdapter((android.widget.ListAdapter) r6)
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.widget.AdapterView$OnItemClickListener r6 = r15.mItemClickListener
            r5.setOnItemClickListener(r6)
            android.widget.DropDownListView r5 = r15.mDropDownList
            r5.setFocusable((boolean) r4)
            android.widget.DropDownListView r5 = r15.mDropDownList
            r5.setFocusableInTouchMode(r4)
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.widget.ListPopupWindow$3 r6 = new android.widget.ListPopupWindow$3
            r6.<init>()
            r5.setOnItemSelectedListener(r6)
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.widget.ListPopupWindow$PopupScrollListener r6 = r15.mScrollListener
            r5.setOnScrollListener(r6)
            android.widget.AdapterView$OnItemSelectedListener r5 = r15.mItemSelectedListener
            if (r5 == 0) goto L_0x0059
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.widget.AdapterView$OnItemSelectedListener r6 = r15.mItemSelectedListener
            r5.setOnItemSelectedListener(r6)
        L_0x0059:
            android.widget.DropDownListView r5 = r15.mDropDownList
            android.view.View r6 = r15.mPromptView
            if (r6 == 0) goto L_0x00c0
            android.widget.LinearLayout r7 = new android.widget.LinearLayout
            r7.<init>(r1)
            r7.setOrientation(r4)
            android.widget.LinearLayout$LayoutParams r8 = new android.widget.LinearLayout$LayoutParams
            r9 = 1065353216(0x3f800000, float:1.0)
            r8.<init>(r2, r3, r9)
            int r9 = r15.mPromptPosition
            switch(r9) {
                case 0: goto L_0x0093;
                case 1: goto L_0x008c;
                default: goto L_0x0073;
            }
        L_0x0073:
            java.lang.String r9 = "ListPopupWindow"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Invalid hint position "
            r10.append(r11)
            int r11 = r15.mPromptPosition
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.e(r9, r10)
            goto L_0x009a
        L_0x008c:
            r7.addView((android.view.View) r5, (android.view.ViewGroup.LayoutParams) r8)
            r7.addView(r6)
            goto L_0x009a
        L_0x0093:
            r7.addView(r6)
            r7.addView((android.view.View) r5, (android.view.ViewGroup.LayoutParams) r8)
        L_0x009a:
            int r9 = r15.mDropDownWidth
            if (r9 < 0) goto L_0x00a3
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            int r10 = r15.mDropDownWidth
            goto L_0x00a5
        L_0x00a3:
            r9 = 0
            r10 = r3
        L_0x00a5:
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r9)
            r12 = 0
            r6.measure(r11, r3)
            android.view.ViewGroup$LayoutParams r13 = r6.getLayoutParams()
            r8 = r13
            android.widget.LinearLayout$LayoutParams r8 = (android.widget.LinearLayout.LayoutParams) r8
            int r13 = r6.getMeasuredHeight()
            int r14 = r8.topMargin
            int r13 = r13 + r14
            int r14 = r8.bottomMargin
            int r13 = r13 + r14
            r5 = r7
            r0 = r13
        L_0x00c0:
            android.widget.PopupWindow r7 = r15.mPopup
            r7.setContentView(r5)
            goto L_0x00dc
        L_0x00c6:
            android.view.View r1 = r15.mPromptView
            if (r1 == 0) goto L_0x00dc
            android.view.ViewGroup$LayoutParams r5 = r1.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r5 = (android.widget.LinearLayout.LayoutParams) r5
            int r6 = r1.getMeasuredHeight()
            int r7 = r5.topMargin
            int r6 = r6 + r7
            int r7 = r5.bottomMargin
            int r0 = r6 + r7
        L_0x00dc:
            android.widget.PopupWindow r1 = r15.mPopup
            android.graphics.drawable.Drawable r1 = r1.getBackground()
            if (r1 == 0) goto L_0x00fe
            android.graphics.Rect r5 = r15.mTempRect
            r1.getPadding(r5)
            android.graphics.Rect r5 = r15.mTempRect
            int r5 = r5.top
            android.graphics.Rect r6 = r15.mTempRect
            int r6 = r6.bottom
            int r5 = r5 + r6
            boolean r6 = r15.mDropDownVerticalOffsetSet
            if (r6 != 0) goto L_0x0104
            android.graphics.Rect r6 = r15.mTempRect
            int r6 = r6.top
            int r6 = -r6
            r15.mDropDownVerticalOffset = r6
            goto L_0x0104
        L_0x00fe:
            android.graphics.Rect r5 = r15.mTempRect
            r5.setEmpty()
            r5 = r3
        L_0x0104:
            android.widget.PopupWindow r6 = r15.mPopup
            int r6 = r6.getInputMethodMode()
            r7 = 2
            if (r6 != r7) goto L_0x010f
            r3 = r4
        L_0x010f:
            android.widget.PopupWindow r4 = r15.mPopup
            android.view.View r6 = r15.getAnchorView()
            int r7 = r15.mDropDownVerticalOffset
            int r4 = r4.getMaxAvailableHeight(r6, r7, r3)
            boolean r6 = r15.mDropDownAlwaysVisible
            if (r6 != 0) goto L_0x018c
            int r6 = r15.mDropDownHeight
            if (r6 != r2) goto L_0x0124
            goto L_0x018c
        L_0x0124:
            int r2 = r15.mDropDownWidth
            r6 = 1073741824(0x40000000, float:2.0)
            switch(r2) {
                case -2: goto L_0x014e;
                case -1: goto L_0x0133;
                default: goto L_0x012b;
            }
        L_0x012b:
            int r2 = r15.mDropDownWidth
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r6)
        L_0x0131:
            r7 = r2
            goto L_0x016b
        L_0x0133:
            android.content.Context r2 = r15.mContext
            android.content.res.Resources r2 = r2.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.graphics.Rect r7 = r15.mTempRect
            int r7 = r7.left
            android.graphics.Rect r8 = r15.mTempRect
            int r8 = r8.right
            int r7 = r7 + r8
            int r2 = r2 - r7
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r6)
            goto L_0x0131
        L_0x014e:
            android.content.Context r2 = r15.mContext
            android.content.res.Resources r2 = r2.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.graphics.Rect r6 = r15.mTempRect
            int r6 = r6.left
            android.graphics.Rect r7 = r15.mTempRect
            int r7 = r7.right
            int r6 = r6 + r7
            int r2 = r2 - r6
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r6)
            goto L_0x0131
        L_0x016b:
            android.widget.DropDownListView r6 = r15.mDropDownList
            r8 = 0
            r9 = -1
            int r10 = r4 - r0
            r11 = -1
            int r2 = r6.measureHeightOfChildren(r7, r8, r9, r10, r11)
            if (r2 <= 0) goto L_0x0189
            android.widget.DropDownListView r6 = r15.mDropDownList
            int r6 = r6.getPaddingTop()
            android.widget.DropDownListView r8 = r15.mDropDownList
            int r8 = r8.getPaddingBottom()
            int r6 = r6 + r8
            int r8 = r5 + r6
            int r0 = r0 + r8
        L_0x0189:
            int r6 = r2 + r0
            return r6
        L_0x018c:
            int r2 = r4 + r5
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListPopupWindow.buildDropDown():int");
    }

    public void setOverlapAnchor(boolean overlap) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = overlap;
    }

    private class PopupDataSetObserver extends DataSetObserver {
        private PopupDataSetObserver() {
        }

        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class ListSelectorHider implements Runnable {
        private ListSelectorHider() {
        }

        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class ResizePopupRunnable implements Runnable {
        private ResizePopupRunnable() {
        }

        public void run() {
            if (ListPopupWindow.this.mDropDownList != null && ListPopupWindow.this.mDropDownList.isAttachedToWindow() && ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount() && ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
                ListPopupWindow.this.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    private class PopupTouchInterceptor implements View.OnTouchListener {
        private PopupTouchInterceptor() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (action == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && x >= 0 && x < ListPopupWindow.this.mPopup.getWidth() && y >= 0 && y < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250);
                return false;
            } else if (action != 1) {
                return false;
            } else {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
                return false;
            }
        }
    }

    private class PopupScrollListener implements AbsListView.OnScrollListener {
        private PopupScrollListener() {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }
}
