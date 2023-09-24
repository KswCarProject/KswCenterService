package android.support.p011v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.p007os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p011v4.content.ContextCompat;
import android.support.p011v4.view.ViewCompat;
import android.support.p011v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

@Deprecated
/* renamed from: android.support.v4.app.ActionBarDrawerToggle */
/* loaded from: classes3.dex */
public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final String TAG = "ActionBarDrawerToggle";
    private static final int[] THEME_ATTRS = {16843531};
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private SetIndicatorInfo mSetIndicatorInfo;
    private SlideDrawable mSlider;

    @Deprecated
    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$Delegate */
    /* loaded from: classes3.dex */
    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    @Deprecated
    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$DelegateProvider */
    /* loaded from: classes3.dex */
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int drawerImageRes, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this(activity, drawerLayout, !assumeMaterial(activity), drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    private static boolean assumeMaterial(Context context) {
        return context.getApplicationInfo().targetSdkVersion >= 21 && Build.VERSION.SDK_INT >= 21;
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, boolean animate, @DrawableRes int drawerImageRes, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this.mDrawerIndicatorEnabled = true;
        this.mActivity = activity;
        if (activity instanceof DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.mActivityImpl = null;
        }
        this.mDrawerLayout = drawerLayout;
        this.mDrawerImageResource = drawerImageRes;
        this.mOpenDrawerContentDescRes = openDrawerContentDescRes;
        this.mCloseDrawerContentDescRes = closeDrawerContentDescRes;
        this.mHomeAsUpIndicator = getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable(activity, drawerImageRes);
        this.mSlider = new SlideDrawable(this.mDrawerImage);
        this.mSlider.setOffset(animate ? TOGGLE_DRAWABLE_OFFSET : 0.0f);
    }

    public void syncState() {
        if (this.mDrawerLayout.isDrawerOpen(8388611)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
        }
    }

    public void setHomeAsUpIndicator(Drawable indicator) {
        if (indicator == null) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = indicator;
            this.mHasCustomUpIndicator = true;
        }
        if (!this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
    }

    public void setHomeAsUpIndicator(int resId) {
        Drawable indicator = null;
        if (resId != 0) {
            indicator = ContextCompat.getDrawable(this.mActivity, resId);
        }
        setHomeAsUpIndicator(indicator);
    }

    public void setDrawerIndicatorEnabled(boolean enable) {
        if (enable != this.mDrawerIndicatorEnabled) {
            if (enable) {
                setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
            } else {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            }
            this.mDrawerIndicatorEnabled = enable;
        }
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable(this.mActivity, this.mDrawerImageResource);
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == 16908332 && this.mDrawerIndicatorEnabled) {
            if (this.mDrawerLayout.isDrawerVisible(8388611)) {
                this.mDrawerLayout.closeDrawer(8388611);
                return true;
            }
            this.mDrawerLayout.openDrawer(8388611);
            return true;
        }
        return false;
    }

    @Override // android.support.p011v4.widget.DrawerLayout.DrawerListener
    public void onDrawerSlide(View drawerView, float slideOffset) {
        float glyphOffset;
        float glyphOffset2 = this.mSlider.getPosition();
        if (slideOffset > 0.5f) {
            glyphOffset = Math.max(glyphOffset2, Math.max(0.0f, slideOffset - 0.5f) * 2.0f);
        } else {
            glyphOffset = Math.min(glyphOffset2, 2.0f * slideOffset);
        }
        this.mSlider.setPosition(glyphOffset);
    }

    @Override // android.support.p011v4.widget.DrawerLayout.DrawerListener
    public void onDrawerOpened(View drawerView) {
        this.mSlider.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    @Override // android.support.p011v4.widget.DrawerLayout.DrawerListener
    public void onDrawerClosed(View drawerView) {
        this.mSlider.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    @Override // android.support.p011v4.widget.DrawerLayout.DrawerListener
    public void onDrawerStateChanged(int newState) {
    }

    private Drawable getThemeUpIndicator() {
        Context context;
        if (this.mActivityImpl != null) {
            return this.mActivityImpl.getThemeUpIndicator();
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                context = actionBar.getThemedContext();
            } else {
                context = this.mActivity;
            }
            TypedArray a = context.obtainStyledAttributes(null, THEME_ATTRS, 16843470, 0);
            Drawable result = a.getDrawable(0);
            a.recycle();
            return result;
        }
        TypedArray a2 = this.mActivity.obtainStyledAttributes(THEME_ATTRS);
        Drawable result2 = a2.getDrawable(0);
        a2.recycle();
        return result2;
    }

    private void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarUpIndicator(upDrawable, contentDescRes);
        } else if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(upDrawable);
                actionBar.setHomeActionContentDescription(contentDescRes);
            }
        } else {
            if (this.mSetIndicatorInfo == null) {
                this.mSetIndicatorInfo = new SetIndicatorInfo(this.mActivity);
            }
            if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
                try {
                    ActionBar actionBar2 = this.mActivity.getActionBar();
                    this.mSetIndicatorInfo.mSetHomeAsUpIndicator.invoke(actionBar2, upDrawable);
                    this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(actionBar2, Integer.valueOf(contentDescRes));
                } catch (Exception e) {
                    Log.m63w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e);
                }
            } else if (this.mSetIndicatorInfo.mUpIndicatorView != null) {
                this.mSetIndicatorInfo.mUpIndicatorView.setImageDrawable(upDrawable);
            } else {
                Log.m64w(TAG, "Couldn't set home-as-up indicator");
            }
        }
    }

    private void setActionBarDescription(int contentDescRes) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarDescription(contentDescRes);
        } else if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(contentDescRes);
            }
        } else {
            if (this.mSetIndicatorInfo == null) {
                this.mSetIndicatorInfo = new SetIndicatorInfo(this.mActivity);
            }
            if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
                try {
                    ActionBar actionBar2 = this.mActivity.getActionBar();
                    this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(actionBar2, Integer.valueOf(contentDescRes));
                    actionBar2.setSubtitle(actionBar2.getSubtitle());
                } catch (Exception e) {
                    Log.m63w(TAG, "Couldn't set content description via JB-MR2 API", e);
                }
            }
        }
    }

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$SetIndicatorInfo */
    /* loaded from: classes3.dex */
    private static class SetIndicatorInfo {
        Method mSetHomeActionContentDescription;
        Method mSetHomeAsUpIndicator;
        ImageView mUpIndicatorView;

        SetIndicatorInfo(Activity activity) {
            try {
                this.mSetHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.mSetHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
            } catch (NoSuchMethodException e) {
                View home = activity.findViewById(16908332);
                if (home == null) {
                    return;
                }
                ViewGroup parent = (ViewGroup) home.getParent();
                int childCount = parent.getChildCount();
                if (childCount != 2) {
                    return;
                }
                View first = parent.getChildAt(0);
                View second = parent.getChildAt(1);
                View up = first.getId() == 16908332 ? second : first;
                if (up instanceof ImageView) {
                    this.mUpIndicatorView = (ImageView) up;
                }
            }
        }
    }

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$SlideDrawable */
    /* loaded from: classes3.dex */
    private class SlideDrawable extends InsetDrawable implements Drawable.Callback {
        private final boolean mHasMirroring;
        private float mOffset;
        private float mPosition;
        private final Rect mTmpRect;

        SlideDrawable(Drawable wrapped) {
            super(wrapped, 0);
            this.mHasMirroring = Build.VERSION.SDK_INT > 18;
            this.mTmpRect = new Rect();
        }

        public void setPosition(float position) {
            this.mPosition = position;
            invalidateSelf();
        }

        public float getPosition() {
            return this.mPosition;
        }

        public void setOffset(float offset) {
            this.mOffset = offset;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(@NonNull Canvas canvas) {
            copyBounds(this.mTmpRect);
            canvas.save();
            boolean isLayoutRTL = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.mActivity.getWindow().getDecorView()) == 1;
            int flipRtl = isLayoutRTL ? -1 : 1;
            int width = this.mTmpRect.width();
            canvas.translate((-this.mOffset) * width * this.mPosition * flipRtl, 0.0f);
            if (isLayoutRTL && !this.mHasMirroring) {
                canvas.translate(width, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            super.draw(canvas);
            canvas.restore();
        }
    }
}