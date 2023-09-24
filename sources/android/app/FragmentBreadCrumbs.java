package android.app;

import android.animation.LayoutTransition;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.C3132R;

@Deprecated
/* loaded from: classes.dex */
public class FragmentBreadCrumbs extends ViewGroup implements FragmentManager.OnBackStackChangedListener {
    private static final int DEFAULT_GRAVITY = 8388627;
    Activity mActivity;
    LinearLayout mContainer;
    private int mGravity;
    LayoutInflater mInflater;
    private int mLayoutResId;
    int mMaxVisible;
    private OnBreadCrumbClickListener mOnBreadCrumbClickListener;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mParentClickListener;
    BackStackRecord mParentEntry;
    private int mTextColor;
    BackStackRecord mTopEntry;

    @Deprecated
    /* loaded from: classes.dex */
    public interface OnBreadCrumbClickListener {
        boolean onBreadCrumbClick(FragmentManager.BackStackEntry backStackEntry, int i);
    }

    public FragmentBreadCrumbs(Context context) {
        this(context, null);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attrs) {
        this(context, attrs, C3132R.attr.fragmentBreadCrumbsStyle);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mMaxVisible = -1;
        this.mOnClickListener = new View.OnClickListener() { // from class: android.app.FragmentBreadCrumbs.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (v.getTag() instanceof FragmentManager.BackStackEntry) {
                    FragmentManager.BackStackEntry bse = (FragmentManager.BackStackEntry) v.getTag();
                    if (bse == FragmentBreadCrumbs.this.mParentEntry) {
                        if (FragmentBreadCrumbs.this.mParentClickListener != null) {
                            FragmentBreadCrumbs.this.mParentClickListener.onClick(v);
                            return;
                        }
                        return;
                    }
                    if (FragmentBreadCrumbs.this.mOnBreadCrumbClickListener != null) {
                        if (FragmentBreadCrumbs.this.mOnBreadCrumbClickListener.onBreadCrumbClick(bse == FragmentBreadCrumbs.this.mTopEntry ? null : bse, 0)) {
                            return;
                        }
                    }
                    if (bse == FragmentBreadCrumbs.this.mTopEntry) {
                        FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack();
                    } else {
                        FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack(bse.getId(), 0);
                    }
                }
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.FragmentBreadCrumbs, defStyleAttr, defStyleRes);
        this.mGravity = a.getInt(0, DEFAULT_GRAVITY);
        this.mLayoutResId = a.getResourceId(2, C3132R.layout.fragment_bread_crumb_item);
        this.mTextColor = a.getColor(1, 0);
        a.recycle();
    }

    public void setActivity(Activity a) {
        this.mActivity = a;
        this.mInflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContainer = (LinearLayout) this.mInflater.inflate(C3132R.layout.fragment_bread_crumbs, (ViewGroup) this, false);
        addView(this.mContainer);
        a.getFragmentManager().addOnBackStackChangedListener(this);
        updateCrumbs();
        setLayoutTransition(new LayoutTransition());
    }

    public void setMaxVisible(int visibleCrumbs) {
        if (visibleCrumbs < 1) {
            throw new IllegalArgumentException("visibleCrumbs must be greater than zero");
        }
        this.mMaxVisible = visibleCrumbs;
    }

    public void setParentTitle(CharSequence title, CharSequence shortTitle, View.OnClickListener listener) {
        this.mParentEntry = createBackStackEntry(title, shortTitle);
        this.mParentClickListener = listener;
        updateCrumbs();
    }

    public void setOnBreadCrumbClickListener(OnBreadCrumbClickListener listener) {
        this.mOnBreadCrumbClickListener = listener;
    }

    private BackStackRecord createBackStackEntry(CharSequence title, CharSequence shortTitle) {
        if (title == null) {
            return null;
        }
        BackStackRecord entry = new BackStackRecord((FragmentManagerImpl) this.mActivity.getFragmentManager());
        entry.setBreadCrumbTitle(title);
        entry.setBreadCrumbShortTitle(shortTitle);
        return entry;
    }

    public void setTitle(CharSequence title, CharSequence shortTitle) {
        this.mTopEntry = createBackStackEntry(title, shortTitle);
        updateCrumbs();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft;
        int childRight;
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        View child = getChildAt(0);
        int childTop = this.mPaddingTop;
        int childBottom = (this.mPaddingTop + child.getMeasuredHeight()) - this.mPaddingBottom;
        int layoutDirection = getLayoutDirection();
        int horizontalGravity = this.mGravity & 8388615;
        int absoluteGravity = Gravity.getAbsoluteGravity(horizontalGravity, layoutDirection);
        if (absoluteGravity == 1) {
            childLeft = this.mPaddingLeft + (((this.mRight - this.mLeft) - child.getMeasuredWidth()) / 2);
            childRight = child.getMeasuredWidth() + childLeft;
        } else if (absoluteGravity == 5) {
            childRight = (this.mRight - this.mLeft) - this.mPaddingRight;
            childLeft = childRight - child.getMeasuredWidth();
        } else {
            childLeft = this.mPaddingLeft;
            childRight = child.getMeasuredWidth() + childLeft;
        }
        if (childLeft < this.mPaddingLeft) {
            childLeft = this.mPaddingLeft;
        }
        if (childRight > (this.mRight - this.mLeft) - this.mPaddingRight) {
            childRight = (this.mRight - this.mLeft) - this.mPaddingRight;
        }
        child.layout(childLeft, childTop, childRight, childBottom);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        int measuredChildState = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                measuredChildState = combineMeasuredStates(measuredChildState, child.getMeasuredState());
            }
        }
        int i2 = this.mPaddingLeft;
        setMeasuredDimension(resolveSizeAndState(Math.max(maxWidth + i2 + this.mPaddingRight, getSuggestedMinimumWidth()), widthMeasureSpec, measuredChildState), resolveSizeAndState(Math.max(maxHeight + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), heightMeasureSpec, measuredChildState << 16));
    }

    @Override // android.app.FragmentManager.OnBackStackChangedListener
    public void onBackStackChanged() {
        updateCrumbs();
    }

    private int getPreEntryCount() {
        return (this.mTopEntry != null ? 1 : 0) + (this.mParentEntry != null ? 1 : 0);
    }

    private FragmentManager.BackStackEntry getPreEntry(int index) {
        if (this.mParentEntry != null) {
            return index == 0 ? this.mParentEntry : this.mTopEntry;
        }
        return this.mTopEntry;
    }

    void updateCrumbs() {
        int i;
        FragmentManager.BackStackEntry bse;
        FragmentManager fm = this.mActivity.getFragmentManager();
        int numEntries = fm.getBackStackEntryCount();
        int numPreEntries = getPreEntryCount();
        int numViews = this.mContainer.getChildCount();
        int numViews2 = numViews;
        for (int numViews3 = 0; numViews3 < numEntries + numPreEntries; numViews3++) {
            if (numViews3 < numPreEntries) {
                bse = getPreEntry(numViews3);
            } else {
                bse = fm.getBackStackEntryAt(numViews3 - numPreEntries);
            }
            if (numViews3 < numViews2) {
                View v = this.mContainer.getChildAt(numViews3);
                Object tag = v.getTag();
                if (tag != bse) {
                    for (int j = numViews3; j < numViews2; j++) {
                        this.mContainer.removeViewAt(numViews3);
                    }
                    numViews2 = numViews3;
                }
            }
            if (numViews3 >= numViews2) {
                View item = this.mInflater.inflate(this.mLayoutResId, (ViewGroup) this, false);
                TextView text = (TextView) item.findViewById(16908310);
                text.setText(bse.getBreadCrumbTitle());
                text.setTag(bse);
                text.setTextColor(this.mTextColor);
                if (numViews3 == 0) {
                    item.findViewById(C3132R.C3134id.left_icon).setVisibility(8);
                }
                this.mContainer.addView(item);
                text.setOnClickListener(this.mOnClickListener);
            }
        }
        int i2 = numEntries + numPreEntries;
        int numViews4 = this.mContainer.getChildCount();
        while (numViews4 > i2) {
            this.mContainer.removeViewAt(numViews4 - 1);
            numViews4--;
        }
        int i3 = 0;
        while (i3 < numViews4) {
            View child = this.mContainer.getChildAt(i3);
            child.findViewById(16908310).setEnabled(i3 < numViews4 + (-1));
            if (this.mMaxVisible > 0) {
                child.setVisibility(i3 < numViews4 - this.mMaxVisible ? 8 : 0);
                View leftIcon = child.findViewById(C3132R.C3134id.left_icon);
                if (i3 > numViews4 - this.mMaxVisible && i3 != 0) {
                    i = 0;
                } else {
                    i = 8;
                }
                leftIcon.setVisibility(i);
            }
            i3++;
        }
    }
}
