package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.android.internal.R;
import java.lang.ref.WeakReference;

public class AlertController {
    public static final int MICRO = 1;
    /* access modifiers changed from: private */
    public ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Message m;
            if (v == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null) {
                m = Message.obtain(AlertController.this.mButtonPositiveMessage);
            } else if (v == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null) {
                m = Message.obtain(AlertController.this.mButtonNegativeMessage);
            } else if (v != AlertController.this.mButtonNeutral || AlertController.this.mButtonNeutralMessage == null) {
                m = null;
            } else {
                m = Message.obtain(AlertController.this.mButtonNeutralMessage);
            }
            if (m != null) {
                m.sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialogInterface).sendToTarget();
        }
    };
    /* access modifiers changed from: private */
    public Button mButtonNegative;
    /* access modifiers changed from: private */
    public Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    /* access modifiers changed from: private */
    public Button mButtonNeutral;
    /* access modifiers changed from: private */
    public Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    /* access modifiers changed from: private */
    public Button mButtonPositive;
    /* access modifiers changed from: private */
    public Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    /* access modifiers changed from: private */
    public int mCheckedItem = -1;
    private final Context mContext;
    @UnsupportedAppUsage
    private View mCustomTitleView;
    /* access modifiers changed from: private */
    public final DialogInterface mDialogInterface;
    @UnsupportedAppUsage
    private boolean mForceInverseBackground;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    /* access modifiers changed from: private */
    public int mListItemLayout;
    /* access modifiers changed from: private */
    public int mListLayout;
    protected ListView mListView;
    protected CharSequence mMessage;
    private Integer mMessageHyphenationFrequency;
    private MovementMethod mMessageMovementMethod;
    protected TextView mMessageView;
    /* access modifiers changed from: private */
    public int mMultiChoiceItemLayout;
    protected ScrollView mScrollView;
    private boolean mShowTitle;
    /* access modifiers changed from: private */
    public int mSingleChoiceItemLayout;
    @UnsupportedAppUsage
    private CharSequence mTitle;
    private TextView mTitleView;
    @UnsupportedAppUsage
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    protected final Window mWindow;

    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            this.mDialog = new WeakReference<>(dialog);
        }

        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                switch (i) {
                    case -3:
                    case -2:
                    case -1:
                        ((DialogInterface.OnClickListener) msg.obj).onClick((DialogInterface) this.mDialog.get(), msg.what);
                        return;
                    default:
                        return;
                }
            } else {
                ((DialogInterface) msg.obj).dismiss();
            }
        }
    }

    private static boolean shouldCenterSingleButton(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogCenterButtons, outValue, true);
        if (outValue.data != 0) {
            return true;
        }
        return false;
    }

    public static final AlertController create(Context context, DialogInterface di, Window window) {
        TypedArray a = context.obtainStyledAttributes((AttributeSet) null, R.styleable.AlertDialog, 16842845, 16974371);
        int controllerType = a.getInt(12, 0);
        a.recycle();
        if (controllerType != 1) {
            return new AlertController(context, di, window);
        }
        return new MicroAlertController(context, di, window);
    }

    @UnsupportedAppUsage
    protected AlertController(Context context, DialogInterface di, Window window) {
        this.mContext = context;
        this.mDialogInterface = di;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(di);
        TypedArray a = context.obtainStyledAttributes((AttributeSet) null, R.styleable.AlertDialog, 16842845, 0);
        this.mAlertDialogLayout = a.getResourceId(10, R.layout.alert_dialog);
        this.mButtonPanelSideLayout = a.getResourceId(11, 0);
        this.mListLayout = a.getResourceId(15, R.layout.select_dialog);
        this.mMultiChoiceItemLayout = a.getResourceId(16, 17367059);
        this.mSingleChoiceItemLayout = a.getResourceId(21, 17367058);
        this.mListItemLayout = a.getResourceId(14, 17367057);
        this.mShowTitle = a.getBoolean(20, true);
        a.recycle();
        window.requestFeature(1);
    }

    static boolean canTextInput(View v) {
        if (v.onCheckIsTextEditor()) {
            return true;
        }
        if (!(v instanceof ViewGroup)) {
            return false;
        }
        ViewGroup vg = (ViewGroup) v;
        int i = vg.getChildCount();
        while (i > 0) {
            i--;
            if (canTextInput(vg.getChildAt(i))) {
                return true;
            }
        }
        return false;
    }

    public void installContent(AlertParams params) {
        params.apply(this);
        installContent();
    }

    @UnsupportedAppUsage
    public void installContent() {
        this.mWindow.setContentView(selectContentView());
        setupView();
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    @UnsupportedAppUsage
    public void setTitle(CharSequence title) {
        this.mTitle = title;
        if (this.mTitleView != null) {
            this.mTitleView.setText(title);
        }
    }

    @UnsupportedAppUsage
    public void setCustomTitle(View customTitleView) {
        this.mCustomTitleView = customTitleView;
    }

    @UnsupportedAppUsage
    public void setMessage(CharSequence message) {
        this.mMessage = message;
        if (this.mMessageView != null) {
            this.mMessageView.setText(message);
        }
    }

    public void setMessageMovementMethod(MovementMethod movementMethod) {
        this.mMessageMovementMethod = movementMethod;
        if (this.mMessageView != null) {
            this.mMessageView.setMovementMethod(movementMethod);
        }
    }

    public void setMessageHyphenationFrequency(int hyphenationFrequency) {
        this.mMessageHyphenationFrequency = Integer.valueOf(hyphenationFrequency);
        if (this.mMessageView != null) {
            this.mMessageView.setHyphenationFrequency(hyphenationFrequency);
        }
    }

    public void setView(int layoutResId) {
        this.mView = null;
        this.mViewLayoutResId = layoutResId;
        this.mViewSpacingSpecified = false;
    }

    @UnsupportedAppUsage
    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = viewSpacingLeft;
        this.mViewSpacingTop = viewSpacingTop;
        this.mViewSpacingRight = viewSpacingRight;
        this.mViewSpacingBottom = viewSpacingBottom;
    }

    public void setButtonPanelLayoutHint(int layoutHint) {
        this.mButtonPanelLayoutHint = layoutHint;
    }

    @UnsupportedAppUsage
    public void setButton(int whichButton, CharSequence text, DialogInterface.OnClickListener listener, Message msg) {
        if (msg == null && listener != null) {
            msg = this.mHandler.obtainMessage(whichButton, listener);
        }
        switch (whichButton) {
            case -3:
                this.mButtonNeutralText = text;
                this.mButtonNeutralMessage = msg;
                return;
            case -2:
                this.mButtonNegativeText = text;
                this.mButtonNegativeMessage = msg;
                return;
            case -1:
                this.mButtonPositiveText = text;
                this.mButtonPositiveMessage = msg;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    @UnsupportedAppUsage
    public void setIcon(int resId) {
        this.mIcon = null;
        this.mIconId = resId;
        if (this.mIconView == null) {
            return;
        }
        if (resId != 0) {
            this.mIconView.setVisibility(0);
            this.mIconView.setImageResource(this.mIconId);
            return;
        }
        this.mIconView.setVisibility(8);
    }

    @UnsupportedAppUsage
    public void setIcon(Drawable icon) {
        this.mIcon = icon;
        this.mIconId = 0;
        if (this.mIconView == null) {
            return;
        }
        if (icon != null) {
            this.mIconView.setVisibility(0);
            this.mIconView.setImageDrawable(icon);
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public int getIconAttributeResId(int attrId) {
        TypedValue out = new TypedValue();
        this.mContext.getTheme().resolveAttribute(attrId, out, true);
        return out.resourceId;
    }

    public void setInverseBackgroundForced(boolean forceInverseBackground) {
        this.mForceInverseBackground = forceInverseBackground;
    }

    @UnsupportedAppUsage
    public ListView getListView() {
        return this.mListView;
    }

    @UnsupportedAppUsage
    public Button getButton(int whichButton) {
        switch (whichButton) {
            case -3:
                return this.mButtonNeutral;
            case -2:
                return this.mButtonNegative;
            case -1:
                return this.mButtonPositive;
            default:
                return null;
        }
    }

    @UnsupportedAppUsage
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(event);
    }

    @UnsupportedAppUsage
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(event);
    }

    private ViewGroup resolvePanel(View customPanel, View defaultPanel) {
        if (customPanel == null) {
            if (defaultPanel instanceof ViewStub) {
                defaultPanel = ((ViewStub) defaultPanel).inflate();
            }
            return (ViewGroup) defaultPanel;
        }
        if (defaultPanel != null) {
            ViewParent parent = defaultPanel.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(defaultPanel);
            }
        }
        if (customPanel instanceof ViewStub) {
            customPanel = ((ViewStub) customPanel).inflate();
        }
        return (ViewGroup) customPanel;
    }

    private void setupView() {
        boolean z;
        boolean hasButtonPanel;
        View spacer;
        View spacer2;
        View parentPanel = this.mWindow.findViewById(R.id.parentPanel);
        View defaultTopPanel = parentPanel.findViewById(R.id.topPanel);
        View defaultContentPanel = parentPanel.findViewById(R.id.contentPanel);
        View defaultButtonPanel = parentPanel.findViewById(R.id.buttonPanel);
        ViewGroup customPanel = (ViewGroup) parentPanel.findViewById(R.id.customPanel);
        setupCustomContent(customPanel);
        View customTopPanel = customPanel.findViewById(R.id.topPanel);
        View customContentPanel = customPanel.findViewById(R.id.contentPanel);
        View customButtonPanel = customPanel.findViewById(R.id.buttonPanel);
        ViewGroup topPanel = resolvePanel(customTopPanel, defaultTopPanel);
        ViewGroup contentPanel = resolvePanel(customContentPanel, defaultContentPanel);
        ViewGroup buttonPanel = resolvePanel(customButtonPanel, defaultButtonPanel);
        setupContent(contentPanel);
        setupButtons(buttonPanel);
        setupTitle(topPanel);
        boolean hasCustomPanel = (customPanel == null || customPanel.getVisibility() == 8) ? false : true;
        boolean hasTopPanel = (topPanel == null || topPanel.getVisibility() == 8) ? false : true;
        boolean hasButtonPanel2 = (buttonPanel == null || buttonPanel.getVisibility() == 8) ? false : true;
        if (!hasButtonPanel2) {
            if (!(contentPanel == null || (spacer2 = contentPanel.findViewById(R.id.textSpacerNoButtons)) == null)) {
                spacer2.setVisibility(0);
            }
            z = true;
            this.mWindow.setCloseOnTouchOutsideIfNotSet(true);
        } else {
            z = true;
        }
        if (hasTopPanel) {
            if (this.mScrollView != null) {
                this.mScrollView.setClipToPadding(z);
            }
            View divider = null;
            if (this.mMessage == null && this.mListView == null && !hasCustomPanel) {
                divider = topPanel.findViewById(R.id.titleDividerTop);
            } else {
                if (!hasCustomPanel) {
                    divider = topPanel.findViewById(R.id.titleDividerNoCustom);
                }
                if (divider == null) {
                    divider = topPanel.findViewById(R.id.titleDivider);
                }
            }
            if (divider != null) {
                divider.setVisibility(0);
            }
        } else if (!(contentPanel == null || (spacer = contentPanel.findViewById(R.id.textSpacerNoTitle)) == null)) {
            spacer.setVisibility(0);
        }
        if (this.mListView instanceof RecycleListView) {
            ((RecycleListView) this.mListView).setHasDecor(hasTopPanel, hasButtonPanel2);
        }
        if (!hasCustomPanel) {
            View content = this.mListView != null ? this.mListView : this.mScrollView;
            if (content != null) {
                hasButtonPanel = hasButtonPanel2;
                content.setScrollIndicators((hasTopPanel ? 1 : 0) | (hasButtonPanel2 ? 2 : 0), 3);
                TypedArray a = this.mContext.obtainStyledAttributes((AttributeSet) null, R.styleable.AlertDialog, 16842845, 0);
                ViewGroup viewGroup = contentPanel;
                ViewGroup viewGroup2 = topPanel;
                View view = customButtonPanel;
                View view2 = customContentPanel;
                setBackground(a, topPanel, contentPanel, customPanel, buttonPanel, hasTopPanel, hasCustomPanel, hasButtonPanel);
                a.recycle();
            }
        }
        hasButtonPanel = hasButtonPanel2;
        TypedArray a2 = this.mContext.obtainStyledAttributes((AttributeSet) null, R.styleable.AlertDialog, 16842845, 0);
        ViewGroup viewGroup3 = contentPanel;
        ViewGroup viewGroup22 = topPanel;
        View view3 = customButtonPanel;
        View view22 = customContentPanel;
        setBackground(a2, topPanel, contentPanel, customPanel, buttonPanel, hasTopPanel, hasCustomPanel, hasButtonPanel);
        a2.recycle();
    }

    private void setupCustomContent(ViewGroup customPanel) {
        View customView;
        boolean hasCustomView = false;
        if (this.mView != null) {
            customView = this.mView;
        } else if (this.mViewLayoutResId != 0) {
            customView = LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, customPanel, false);
        } else {
            customView = null;
        }
        if (customView != null) {
            hasCustomView = true;
        }
        if (!hasCustomView || !canTextInput(customView)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (hasCustomView) {
            FrameLayout custom = (FrameLayout) this.mWindow.findViewById(16908331);
            custom.addView(customView, new ViewGroup.LayoutParams(-1, -1));
            if (this.mViewSpacingSpecified) {
                custom.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
            if (this.mListView != null) {
                ((LinearLayout.LayoutParams) customPanel.getLayoutParams()).weight = 0.0f;
                return;
            }
            return;
        }
        customPanel.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void setupTitle(ViewGroup topPanel) {
        if (this.mCustomTitleView == null || !this.mShowTitle) {
            this.mIconView = (ImageView) this.mWindow.findViewById(16908294);
            if (!(!TextUtils.isEmpty(this.mTitle)) || !this.mShowTitle) {
                this.mWindow.findViewById(R.id.title_template).setVisibility(8);
                this.mIconView.setVisibility(8);
                topPanel.setVisibility(8);
                return;
            }
            this.mTitleView = (TextView) this.mWindow.findViewById(R.id.alertTitle);
            this.mTitleView.setText(this.mTitle);
            if (this.mIconId != 0) {
                this.mIconView.setImageResource(this.mIconId);
            } else if (this.mIcon != null) {
                this.mIconView.setImageDrawable(this.mIcon);
            } else {
                this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                this.mIconView.setVisibility(8);
            }
        } else {
            topPanel.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void setupContent(ViewGroup contentPanel) {
        this.mScrollView = (ScrollView) contentPanel.findViewById(R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mMessageView = (TextView) contentPanel.findViewById(16908299);
        if (this.mMessageView != null) {
            if (this.mMessage != null) {
                this.mMessageView.setText(this.mMessage);
                if (this.mMessageMovementMethod != null) {
                    this.mMessageView.setMovementMethod(this.mMessageMovementMethod);
                }
                if (this.mMessageHyphenationFrequency != null) {
                    this.mMessageView.setHyphenationFrequency(this.mMessageHyphenationFrequency.intValue());
                    return;
                }
                return;
            }
            this.mMessageView.setVisibility(8);
            this.mScrollView.removeView(this.mMessageView);
            if (this.mListView != null) {
                ViewGroup scrollParent = (ViewGroup) this.mScrollView.getParent();
                int childIndex = scrollParent.indexOfChild(this.mScrollView);
                scrollParent.removeViewAt(childIndex);
                scrollParent.addView((View) this.mListView, childIndex, new ViewGroup.LayoutParams(-1, -1));
                return;
            }
            contentPanel.setVisibility(8);
        }
    }

    private static void manageScrollIndicators(View v, View upIndicator, View downIndicator) {
        int i = 4;
        if (upIndicator != null) {
            upIndicator.setVisibility(v.canScrollVertically(-1) ? 0 : 4);
        }
        if (downIndicator != null) {
            if (v.canScrollVertically(1)) {
                i = 0;
            }
            downIndicator.setVisibility(i);
        }
    }

    /* access modifiers changed from: protected */
    public void setupButtons(ViewGroup buttonPanel) {
        int whichButtons = 0;
        this.mButtonPositive = (Button) buttonPanel.findViewById(16908313);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        boolean hasButtons = false;
        if (TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            whichButtons = 0 | 1;
        }
        this.mButtonNegative = (Button) buttonPanel.findViewById(16908314);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            whichButtons |= 2;
        }
        this.mButtonNeutral = (Button) buttonPanel.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            whichButtons |= 4;
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (whichButtons == 1) {
                centerButton(this.mButtonPositive);
            } else if (whichButtons == 2) {
                centerButton(this.mButtonNegative);
            } else if (whichButtons == 4) {
                centerButton(this.mButtonNeutral);
            }
        }
        if (whichButtons != 0) {
            hasButtons = true;
        }
        if (!hasButtons) {
            buttonPanel.setVisibility(8);
        }
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        params.gravity = 1;
        params.weight = 0.5f;
        button.setLayoutParams(params);
        View leftSpacer = this.mWindow.findViewById(R.id.leftSpacer);
        if (leftSpacer != null) {
            leftSpacer.setVisibility(0);
        }
        View rightSpacer = this.mWindow.findViewById(R.id.rightSpacer);
        if (rightSpacer != null) {
            rightSpacer.setVisibility(0);
        }
    }

    private void setBackground(TypedArray a, View topPanel, View contentPanel, View customPanel, View buttonPanel, boolean hasTitle, boolean hasCustomView, boolean hasButtons) {
        int centerBright;
        int centerBright2;
        TypedArray typedArray = a;
        int fullDark = 0;
        int topDark = 0;
        int centerDark = 0;
        int bottomDark = 0;
        int fullBright = 0;
        int topBright = 0;
        int centerBright3 = 0;
        int bottomBright = 0;
        int bottomMedium = 0;
        if (typedArray.getBoolean(17, true)) {
            fullDark = R.drawable.popup_full_dark;
            topDark = R.drawable.popup_top_dark;
            centerDark = R.drawable.popup_center_dark;
            bottomDark = R.drawable.popup_bottom_dark;
            fullBright = R.drawable.popup_full_bright;
            topBright = R.drawable.popup_top_bright;
            centerBright3 = R.drawable.popup_center_bright;
            bottomBright = R.drawable.popup_bottom_bright;
            bottomMedium = R.drawable.popup_bottom_medium;
        }
        int topBright2 = typedArray.getResourceId(5, topBright);
        int topDark2 = typedArray.getResourceId(1, topDark);
        int centerBright4 = typedArray.getResourceId(6, centerBright3);
        int centerDark2 = typedArray.getResourceId(2, centerDark);
        View[] views = new View[4];
        boolean[] light = new boolean[4];
        boolean lastLight = false;
        int pos = 0;
        if (hasTitle) {
            views[0] = topPanel;
            light[0] = false;
            pos = 0 + 1;
        }
        int topDark3 = topDark2;
        views[pos] = contentPanel.getVisibility() == 8 ? null : contentPanel;
        light[pos] = this.mListView != null;
        int pos2 = pos + 1;
        if (hasCustomView) {
            views[pos2] = customPanel;
            light[pos2] = this.mForceInverseBackground;
            pos2++;
        }
        if (hasButtons) {
            views[pos2] = buttonPanel;
            light[pos2] = true;
        }
        boolean setView = false;
        int centerDark3 = centerDark2;
        View lastView = null;
        int pos3 = 0;
        while (true) {
            int topBright3 = topBright2;
            if (pos3 >= views.length) {
                break;
            }
            View v = views[pos3];
            if (v == null) {
                centerBright = centerBright4;
            } else {
                if (lastView != null) {
                    if (!setView) {
                        if (lastLight) {
                            centerBright = centerBright4;
                            centerBright2 = topBright3;
                        } else {
                            centerBright = centerBright4;
                            centerBright2 = topDark3;
                        }
                        lastView.setBackgroundResource(centerBright2);
                    } else {
                        centerBright = centerBright4;
                        lastView.setBackgroundResource(lastLight ? centerBright : centerDark3);
                    }
                    setView = true;
                } else {
                    centerBright = centerBright4;
                }
                lastView = v;
                lastLight = light[pos3];
            }
            pos3++;
            topBright2 = topBright3;
            centerBright4 = centerBright;
        }
        if (lastView != null) {
            if (setView) {
                lastView.setBackgroundResource(lastLight ? hasButtons ? typedArray.getResourceId(8, bottomMedium) : typedArray.getResourceId(7, bottomBright) : typedArray.getResourceId(3, bottomDark));
            } else {
                lastView.setBackgroundResource(lastLight ? typedArray.getResourceId(4, fullBright) : typedArray.getResourceId(0, fullDark));
            }
        }
        ListView listView = this.mListView;
        if (listView != null && this.mAdapter != null) {
            listView.setAdapter(this.mAdapter);
            int checkedItem = this.mCheckedItem;
            if (checkedItem > -1) {
                listView.setItemChecked(checkedItem, true);
                listView.setSelectionFromTop(checkedItem, typedArray.getDimensionPixelSize(19, 0));
            }
        }
    }

    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;
        boolean mRecycleOnMeasure;

        @UnsupportedAppUsage
        public RecycleListView(Context context) {
            this(context, (AttributeSet) null);
        }

        @UnsupportedAppUsage
        public RecycleListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mRecycleOnMeasure = true;
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = ta.getDimensionPixelOffset(0, -1);
            this.mPaddingTopNoTitle = ta.getDimensionPixelOffset(1, -1);
        }

        public void setHasDecor(boolean hasTitle, boolean hasButtons) {
            if (!hasButtons || !hasTitle) {
                setPadding(getPaddingLeft(), hasTitle ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), hasButtons ? getPaddingBottom() : this.mPaddingBottomNoButtons);
            }
        }

        /* access modifiers changed from: protected */
        public boolean recycleOnMeasure() {
            return this.mRecycleOnMeasure;
        }
    }

    public static class AlertParams {
        @UnsupportedAppUsage
        public ListAdapter mAdapter;
        @UnsupportedAppUsage
        public boolean mCancelable;
        @UnsupportedAppUsage
        public int mCheckedItem = -1;
        @UnsupportedAppUsage
        public boolean[] mCheckedItems;
        @UnsupportedAppUsage
        public final Context mContext;
        @UnsupportedAppUsage
        public Cursor mCursor;
        @UnsupportedAppUsage
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        @UnsupportedAppUsage
        public Drawable mIcon;
        public int mIconAttrId = 0;
        @UnsupportedAppUsage
        public int mIconId = 0;
        @UnsupportedAppUsage
        public final LayoutInflater mInflater;
        @UnsupportedAppUsage
        public String mIsCheckedColumn;
        @UnsupportedAppUsage
        public boolean mIsMultiChoice;
        @UnsupportedAppUsage
        public boolean mIsSingleChoice;
        @UnsupportedAppUsage
        public CharSequence[] mItems;
        @UnsupportedAppUsage
        public String mLabelColumn;
        @UnsupportedAppUsage
        public CharSequence mMessage;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mNegativeButtonListener;
        @UnsupportedAppUsage
        public CharSequence mNegativeButtonText;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mNeutralButtonListener;
        @UnsupportedAppUsage
        public CharSequence mNeutralButtonText;
        @UnsupportedAppUsage
        public DialogInterface.OnCancelListener mOnCancelListener;
        @UnsupportedAppUsage
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mOnClickListener;
        @UnsupportedAppUsage
        public DialogInterface.OnDismissListener mOnDismissListener;
        @UnsupportedAppUsage
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        @UnsupportedAppUsage
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mPositiveButtonListener;
        @UnsupportedAppUsage
        public CharSequence mPositiveButtonText;
        public boolean mRecycleOnMeasure = true;
        @UnsupportedAppUsage
        public CharSequence mTitle;
        @UnsupportedAppUsage
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        @UnsupportedAppUsage
        public AlertParams(Context context) {
            this.mContext = context;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @UnsupportedAppUsage
        public void apply(AlertController dialog) {
            if (this.mCustomTitleView != null) {
                dialog.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    dialog.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    dialog.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    dialog.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    dialog.setIcon(dialog.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                dialog.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                dialog.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, (Message) null);
            }
            if (this.mNegativeButtonText != null) {
                dialog.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, (Message) null);
            }
            if (this.mNeutralButtonText != null) {
                dialog.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, (Message) null);
            }
            if (this.mForceInverseBackground) {
                dialog.setInverseBackgroundForced(true);
            }
            if (!(this.mItems == null && this.mCursor == null && this.mAdapter == null)) {
                createListView(dialog);
            }
            if (this.mView != null) {
                if (this.mViewSpacingSpecified) {
                    dialog.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                    return;
                }
                dialog.setView(this.mView);
            } else if (this.mViewLayoutResId != 0) {
                dialog.setView(this.mViewLayoutResId);
            }
        }

        private void createListView(final AlertController dialog) {
            ListAdapter adapter;
            int layout;
            ListAdapter r1;
            final RecycleListView listView = (RecycleListView) this.mInflater.inflate(dialog.mListLayout, (ViewGroup) null);
            if (this.mIsMultiChoice) {
                if (this.mCursor == null) {
                    final RecycleListView recycleListView = listView;
                    r1 = new ArrayAdapter<CharSequence>(this.mContext, dialog.mMultiChoiceItemLayout, 16908308, this.mItems) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[position]) {
                                recycleListView.setItemChecked(position, true);
                            }
                            return view;
                        }
                    };
                } else {
                    final RecycleListView recycleListView2 = listView;
                    final AlertController alertController = dialog;
                    r1 = new CursorAdapter(this.mContext, this.mCursor, false) {
                        private final int mIsCheckedIndex;
                        private final int mLabelIndex;

                        {
                            Cursor cursor = getCursor();
                            this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                            this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                        }

                        public void bindView(View view, Context context, Cursor cursor) {
                            ((CheckedTextView) view.findViewById(16908308)).setText((CharSequence) cursor.getString(this.mLabelIndex));
                            RecycleListView recycleListView = recycleListView2;
                            int position = cursor.getPosition();
                            boolean z = true;
                            if (cursor.getInt(this.mIsCheckedIndex) != 1) {
                                z = false;
                            }
                            recycleListView.setItemChecked(position, z);
                        }

                        public View newView(Context context, Cursor cursor, ViewGroup parent) {
                            return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, parent, false);
                        }
                    };
                }
                adapter = r1;
            } else {
                if (this.mIsSingleChoice) {
                    layout = dialog.mSingleChoiceItemLayout;
                } else {
                    layout = dialog.mListItemLayout;
                }
                if (this.mCursor != null) {
                    adapter = new SimpleCursorAdapter(this.mContext, layout, this.mCursor, new String[]{this.mLabelColumn}, new int[]{16908308});
                } else {
                    adapter = this.mAdapter != null ? this.mAdapter : new CheckedItemAdapter(this.mContext, layout, 16908308, this.mItems);
                }
            }
            ListAdapter adapter2 = adapter;
            if (this.mOnPrepareListViewListener != null) {
                this.mOnPrepareListViewListener.onPrepareListView(listView);
            }
            ListAdapter unused = dialog.mAdapter = adapter2;
            int unused2 = dialog.mCheckedItem = this.mCheckedItem;
            if (this.mOnClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                        AlertParams.this.mOnClickListener.onClick(dialog.mDialogInterface, position);
                        if (!AlertParams.this.mIsSingleChoice) {
                            dialog.mDialogInterface.dismiss();
                        }
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[position] = listView.isItemChecked(position);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(dialog.mDialogInterface, position, listView.isItemChecked(position));
                    }
                });
            }
            if (this.mOnItemSelectedListener != null) {
                listView.setOnItemSelectedListener(this.mOnItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                listView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                listView.setChoiceMode(2);
            }
            listView.mRecycleOnMeasure = this.mRecycleOnMeasure;
            dialog.mListView = listView;
        }
    }

    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int resource, int textViewResourceId, CharSequence[] objects) {
            super(context, resource, textViewResourceId, (T[]) objects);
        }

        public boolean hasStableIds() {
            return true;
        }

        public long getItemId(int position) {
            return (long) position;
        }
    }
}
