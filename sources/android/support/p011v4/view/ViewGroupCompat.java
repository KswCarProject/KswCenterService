package android.support.p011v4.view;

import android.p007os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v4.view.ViewGroupCompat */
/* loaded from: classes3.dex */
public final class ViewGroupCompat {
    static final ViewGroupCompatBaseImpl IMPL;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatBaseImpl */
    /* loaded from: classes3.dex */
    static class ViewGroupCompatBaseImpl {
        ViewGroupCompatBaseImpl() {
        }

        public int getLayoutMode(ViewGroup group) {
            return 0;
        }

        public void setLayoutMode(ViewGroup group, int mode) {
        }

        public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
        }

        public boolean isTransitionGroup(ViewGroup group) {
            return false;
        }

        public int getNestedScrollAxes(ViewGroup group) {
            if (group instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) group).getNestedScrollAxes();
            }
            return 0;
        }
    }

    @RequiresApi(18)
    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatApi18Impl */
    /* loaded from: classes3.dex */
    static class ViewGroupCompatApi18Impl extends ViewGroupCompatBaseImpl {
        ViewGroupCompatApi18Impl() {
        }

        @Override // android.support.p011v4.view.ViewGroupCompat.ViewGroupCompatBaseImpl
        public int getLayoutMode(ViewGroup group) {
            return group.getLayoutMode();
        }

        @Override // android.support.p011v4.view.ViewGroupCompat.ViewGroupCompatBaseImpl
        public void setLayoutMode(ViewGroup group, int mode) {
            group.setLayoutMode(mode);
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatApi21Impl */
    /* loaded from: classes3.dex */
    static class ViewGroupCompatApi21Impl extends ViewGroupCompatApi18Impl {
        ViewGroupCompatApi21Impl() {
        }

        @Override // android.support.p011v4.view.ViewGroupCompat.ViewGroupCompatBaseImpl
        public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
            group.setTransitionGroup(isTransitionGroup);
        }

        @Override // android.support.p011v4.view.ViewGroupCompat.ViewGroupCompatBaseImpl
        public boolean isTransitionGroup(ViewGroup group) {
            return group.isTransitionGroup();
        }

        @Override // android.support.p011v4.view.ViewGroupCompat.ViewGroupCompatBaseImpl
        public int getNestedScrollAxes(ViewGroup group) {
            return group.getNestedScrollAxes();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new ViewGroupCompatApi21Impl();
        } else if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new ViewGroupCompatApi18Impl();
        } else {
            IMPL = new ViewGroupCompatBaseImpl();
        }
    }

    private ViewGroupCompat() {
    }

    @Deprecated
    public static boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
        return group.onRequestSendAccessibilityEvent(child, event);
    }

    @Deprecated
    public static void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
        group.setMotionEventSplittingEnabled(split);
    }

    public static int getLayoutMode(ViewGroup group) {
        return IMPL.getLayoutMode(group);
    }

    public static void setLayoutMode(ViewGroup group, int mode) {
        IMPL.setLayoutMode(group, mode);
    }

    public static void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
        IMPL.setTransitionGroup(group, isTransitionGroup);
    }

    public static boolean isTransitionGroup(ViewGroup group) {
        return IMPL.isTransitionGroup(group);
    }

    public static int getNestedScrollAxes(@NonNull ViewGroup group) {
        return IMPL.getNestedScrollAxes(group);
    }
}
