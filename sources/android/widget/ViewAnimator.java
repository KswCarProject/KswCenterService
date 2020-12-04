package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;

public class ViewAnimator extends FrameLayout {
    boolean mAnimateFirstTime = true;
    @UnsupportedAppUsage
    boolean mFirstTime = true;
    Animation mInAnimation;
    Animation mOutAnimation;
    @UnsupportedAppUsage
    int mWhichChild = 0;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ViewAnimator> {
        private int mAnimateFirstViewId;
        private int mInAnimationId;
        private int mOutAnimationId;
        private boolean mPropertiesMapped = false;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAnimateFirstViewId = propertyMapper.mapBoolean("animateFirstView", 16843477);
            this.mInAnimationId = propertyMapper.mapObject("inAnimation", 16843127);
            this.mOutAnimationId = propertyMapper.mapObject("outAnimation", 16843128);
            this.mPropertiesMapped = true;
        }

        public void readProperties(ViewAnimator node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readBoolean(this.mAnimateFirstViewId, node.getAnimateFirstView());
                propertyReader.readObject(this.mInAnimationId, node.getInAnimation());
                propertyReader.readObject(this.mOutAnimationId, node.getOutAnimation());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public ViewAnimator(Context context) {
        super(context);
        initViewAnimator(context, (AttributeSet) null);
    }

    public ViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewAnimator);
        int resource = a.getResourceId(0, 0);
        if (resource > 0) {
            setInAnimation(context, resource);
        }
        int resource2 = a.getResourceId(1, 0);
        if (resource2 > 0) {
            setOutAnimation(context, resource2);
        }
        setAnimateFirstView(a.getBoolean(2, true));
        a.recycle();
        initViewAnimator(context, attrs);
    }

    private void initViewAnimator(Context context, AttributeSet attrs) {
        if (attrs == null) {
            this.mMeasureAllChildren = true;
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FrameLayout);
        setMeasureAllChildren(a.getBoolean(0, true));
        a.recycle();
    }

    @RemotableViewMethod
    public void setDisplayedChild(int whichChild) {
        this.mWhichChild = whichChild;
        boolean z = true;
        if (whichChild >= getChildCount()) {
            this.mWhichChild = 0;
        } else if (whichChild < 0) {
            this.mWhichChild = getChildCount() - 1;
        }
        if (getFocusedChild() == null) {
            z = false;
        }
        boolean hasFocus = z;
        showOnly(this.mWhichChild);
        if (hasFocus) {
            requestFocus(2);
        }
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    @RemotableViewMethod
    public void showNext() {
        setDisplayedChild(this.mWhichChild + 1);
    }

    @RemotableViewMethod
    public void showPrevious() {
        setDisplayedChild(this.mWhichChild - 1);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void showOnly(int childIndex, boolean animate) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (i == childIndex) {
                if (animate && this.mInAnimation != null) {
                    child.startAnimation(this.mInAnimation);
                }
                child.setVisibility(0);
                this.mFirstTime = false;
            } else {
                if (animate && this.mOutAnimation != null && child.getVisibility() == 0) {
                    child.startAnimation(this.mOutAnimation);
                } else if (child.getAnimation() == this.mInAnimation) {
                    child.clearAnimation();
                }
                child.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void showOnly(int childIndex) {
        showOnly(childIndex, !this.mFirstTime || this.mAnimateFirstTime);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() == 1) {
            child.setVisibility(0);
        } else {
            child.setVisibility(8);
        }
        if (index >= 0 && this.mWhichChild >= index) {
            setDisplayedChild(this.mWhichChild + 1);
        }
    }

    public void removeAllViews() {
        super.removeAllViews();
        this.mWhichChild = 0;
        this.mFirstTime = true;
    }

    public void removeView(View view) {
        int index = indexOfChild(view);
        if (index >= 0) {
            removeViewAt(index);
        }
    }

    public void removeViewAt(int index) {
        super.removeViewAt(index);
        int childCount = getChildCount();
        if (childCount == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
        } else if (this.mWhichChild >= childCount) {
            setDisplayedChild(childCount - 1);
        } else if (this.mWhichChild == index) {
            setDisplayedChild(this.mWhichChild);
        }
    }

    public void removeViewInLayout(View view) {
        removeView(view);
    }

    public void removeViews(int start, int count) {
        super.removeViews(start, count);
        if (getChildCount() == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
        } else if (this.mWhichChild >= start && this.mWhichChild < start + count) {
            setDisplayedChild(this.mWhichChild);
        }
    }

    public void removeViewsInLayout(int start, int count) {
        removeViews(start, count);
    }

    public View getCurrentView() {
        return getChildAt(this.mWhichChild);
    }

    public Animation getInAnimation() {
        return this.mInAnimation;
    }

    public void setInAnimation(Animation inAnimation) {
        this.mInAnimation = inAnimation;
    }

    public Animation getOutAnimation() {
        return this.mOutAnimation;
    }

    public void setOutAnimation(Animation outAnimation) {
        this.mOutAnimation = outAnimation;
    }

    public void setInAnimation(Context context, int resourceID) {
        setInAnimation(AnimationUtils.loadAnimation(context, resourceID));
    }

    public void setOutAnimation(Context context, int resourceID) {
        setOutAnimation(AnimationUtils.loadAnimation(context, resourceID));
    }

    public boolean getAnimateFirstView() {
        return this.mAnimateFirstTime;
    }

    public void setAnimateFirstView(boolean animate) {
        this.mAnimateFirstTime = animate;
    }

    public int getBaseline() {
        return getCurrentView() != null ? getCurrentView().getBaseline() : super.getBaseline();
    }

    public CharSequence getAccessibilityClassName() {
        return ViewAnimator.class.getName();
    }
}
