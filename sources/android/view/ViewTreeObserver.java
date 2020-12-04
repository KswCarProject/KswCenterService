package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class ViewTreeObserver {
    private static boolean sIllegalOnDrawModificationIsFatal;
    private boolean mAlive = true;
    private CopyOnWriteArray<Consumer<List<Rect>>> mGestureExclusionListeners;
    private boolean mInDispatchOnDraw;
    @UnsupportedAppUsage
    private CopyOnWriteArray<OnComputeInternalInsetsListener> mOnComputeInternalInsetsListeners;
    private ArrayList<OnDrawListener> mOnDrawListeners;
    private CopyOnWriteArrayList<OnEnterAnimationCompleteListener> mOnEnterAnimationCompleteListeners;
    private ArrayList<Runnable> mOnFrameCommitListeners;
    private CopyOnWriteArrayList<OnGlobalFocusChangeListener> mOnGlobalFocusListeners;
    @UnsupportedAppUsage
    private CopyOnWriteArray<OnGlobalLayoutListener> mOnGlobalLayoutListeners;
    private CopyOnWriteArray<OnPreDrawListener> mOnPreDrawListeners;
    @UnsupportedAppUsage
    private CopyOnWriteArray<OnScrollChangedListener> mOnScrollChangedListeners;
    @UnsupportedAppUsage
    private CopyOnWriteArrayList<OnTouchModeChangeListener> mOnTouchModeChangeListeners;
    private CopyOnWriteArrayList<OnWindowAttachListener> mOnWindowAttachListeners;
    private CopyOnWriteArrayList<OnWindowFocusChangeListener> mOnWindowFocusListeners;
    private CopyOnWriteArray<OnWindowShownListener> mOnWindowShownListeners;
    private boolean mWindowShown;

    public interface OnComputeInternalInsetsListener {
        void onComputeInternalInsets(InternalInsetsInfo internalInsetsInfo);
    }

    public interface OnDrawListener {
        void onDraw();
    }

    public interface OnEnterAnimationCompleteListener {
        void onEnterAnimationComplete();
    }

    public interface OnGlobalFocusChangeListener {
        void onGlobalFocusChanged(View view, View view2);
    }

    public interface OnGlobalLayoutListener {
        void onGlobalLayout();
    }

    public interface OnPreDrawListener {
        boolean onPreDraw();
    }

    public interface OnScrollChangedListener {
        void onScrollChanged();
    }

    public interface OnTouchModeChangeListener {
        void onTouchModeChanged(boolean z);
    }

    public interface OnWindowAttachListener {
        void onWindowAttached();

        void onWindowDetached();
    }

    public interface OnWindowFocusChangeListener {
        void onWindowFocusChanged(boolean z);
    }

    public interface OnWindowShownListener {
        void onWindowShown();
    }

    public static final class InternalInsetsInfo {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        @UnsupportedAppUsage
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        @UnsupportedAppUsage
        public final Rect contentInsets = new Rect();
        @UnsupportedAppUsage
        int mTouchableInsets;
        @UnsupportedAppUsage
        public final Region touchableRegion = new Region();
        @UnsupportedAppUsage
        public final Rect visibleInsets = new Rect();

        @UnsupportedAppUsage
        public void setTouchableInsets(int val) {
            this.mTouchableInsets = val;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.contentInsets.setEmpty();
            this.visibleInsets.setEmpty();
            this.touchableRegion.setEmpty();
            this.mTouchableInsets = 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isEmpty() {
            return this.contentInsets.isEmpty() && this.visibleInsets.isEmpty() && this.touchableRegion.isEmpty() && this.mTouchableInsets == 0;
        }

        public int hashCode() {
            return (((((this.contentInsets.hashCode() * 31) + this.visibleInsets.hashCode()) * 31) + this.touchableRegion.hashCode()) * 31) + this.mTouchableInsets;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InternalInsetsInfo other = (InternalInsetsInfo) o;
            if (this.mTouchableInsets != other.mTouchableInsets || !this.contentInsets.equals(other.contentInsets) || !this.visibleInsets.equals(other.visibleInsets) || !this.touchableRegion.equals(other.touchableRegion)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        @UnsupportedAppUsage
        public void set(InternalInsetsInfo other) {
            this.contentInsets.set(other.contentInsets);
            this.visibleInsets.set(other.visibleInsets);
            this.touchableRegion.set(other.touchableRegion);
            this.mTouchableInsets = other.mTouchableInsets;
        }
    }

    ViewTreeObserver(Context context) {
        boolean z = true;
        sIllegalOnDrawModificationIsFatal = context.getApplicationInfo().targetSdkVersion < 26 ? false : z;
    }

    /* access modifiers changed from: package-private */
    public void merge(ViewTreeObserver observer) {
        if (observer.mOnWindowAttachListeners != null) {
            if (this.mOnWindowAttachListeners != null) {
                this.mOnWindowAttachListeners.addAll(observer.mOnWindowAttachListeners);
            } else {
                this.mOnWindowAttachListeners = observer.mOnWindowAttachListeners;
            }
        }
        if (observer.mOnWindowFocusListeners != null) {
            if (this.mOnWindowFocusListeners != null) {
                this.mOnWindowFocusListeners.addAll(observer.mOnWindowFocusListeners);
            } else {
                this.mOnWindowFocusListeners = observer.mOnWindowFocusListeners;
            }
        }
        if (observer.mOnGlobalFocusListeners != null) {
            if (this.mOnGlobalFocusListeners != null) {
                this.mOnGlobalFocusListeners.addAll(observer.mOnGlobalFocusListeners);
            } else {
                this.mOnGlobalFocusListeners = observer.mOnGlobalFocusListeners;
            }
        }
        if (observer.mOnGlobalLayoutListeners != null) {
            if (this.mOnGlobalLayoutListeners != null) {
                this.mOnGlobalLayoutListeners.addAll(observer.mOnGlobalLayoutListeners);
            } else {
                this.mOnGlobalLayoutListeners = observer.mOnGlobalLayoutListeners;
            }
        }
        if (observer.mOnPreDrawListeners != null) {
            if (this.mOnPreDrawListeners != null) {
                this.mOnPreDrawListeners.addAll(observer.mOnPreDrawListeners);
            } else {
                this.mOnPreDrawListeners = observer.mOnPreDrawListeners;
            }
        }
        if (observer.mOnDrawListeners != null) {
            if (this.mOnDrawListeners != null) {
                this.mOnDrawListeners.addAll(observer.mOnDrawListeners);
            } else {
                this.mOnDrawListeners = observer.mOnDrawListeners;
            }
        }
        if (observer.mOnFrameCommitListeners != null) {
            if (this.mOnFrameCommitListeners != null) {
                this.mOnFrameCommitListeners.addAll(observer.captureFrameCommitCallbacks());
            } else {
                this.mOnFrameCommitListeners = observer.captureFrameCommitCallbacks();
            }
        }
        if (observer.mOnTouchModeChangeListeners != null) {
            if (this.mOnTouchModeChangeListeners != null) {
                this.mOnTouchModeChangeListeners.addAll(observer.mOnTouchModeChangeListeners);
            } else {
                this.mOnTouchModeChangeListeners = observer.mOnTouchModeChangeListeners;
            }
        }
        if (observer.mOnComputeInternalInsetsListeners != null) {
            if (this.mOnComputeInternalInsetsListeners != null) {
                this.mOnComputeInternalInsetsListeners.addAll(observer.mOnComputeInternalInsetsListeners);
            } else {
                this.mOnComputeInternalInsetsListeners = observer.mOnComputeInternalInsetsListeners;
            }
        }
        if (observer.mOnScrollChangedListeners != null) {
            if (this.mOnScrollChangedListeners != null) {
                this.mOnScrollChangedListeners.addAll(observer.mOnScrollChangedListeners);
            } else {
                this.mOnScrollChangedListeners = observer.mOnScrollChangedListeners;
            }
        }
        if (observer.mOnWindowShownListeners != null) {
            if (this.mOnWindowShownListeners != null) {
                this.mOnWindowShownListeners.addAll(observer.mOnWindowShownListeners);
            } else {
                this.mOnWindowShownListeners = observer.mOnWindowShownListeners;
            }
        }
        if (observer.mGestureExclusionListeners != null) {
            if (this.mGestureExclusionListeners != null) {
                this.mGestureExclusionListeners.addAll(observer.mGestureExclusionListeners);
            } else {
                this.mGestureExclusionListeners = observer.mGestureExclusionListeners;
            }
        }
        observer.kill();
    }

    public void addOnWindowAttachListener(OnWindowAttachListener listener) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            this.mOnWindowAttachListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowAttachListeners.add(listener);
    }

    public void removeOnWindowAttachListener(OnWindowAttachListener victim) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners != null) {
            this.mOnWindowAttachListeners.remove(victim);
        }
    }

    public void addOnWindowFocusChangeListener(OnWindowFocusChangeListener listener) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            this.mOnWindowFocusListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowFocusListeners.add(listener);
    }

    public void removeOnWindowFocusChangeListener(OnWindowFocusChangeListener victim) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners != null) {
            this.mOnWindowFocusListeners.remove(victim);
        }
    }

    public void addOnGlobalFocusChangeListener(OnGlobalFocusChangeListener listener) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            this.mOnGlobalFocusListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnGlobalFocusListeners.add(listener);
    }

    public void removeOnGlobalFocusChangeListener(OnGlobalFocusChangeListener victim) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners != null) {
            this.mOnGlobalFocusListeners.remove(victim);
        }
    }

    public void addOnGlobalLayoutListener(OnGlobalLayoutListener listener) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            this.mOnGlobalLayoutListeners = new CopyOnWriteArray<>();
        }
        this.mOnGlobalLayoutListeners.add(listener);
    }

    @Deprecated
    public void removeGlobalOnLayoutListener(OnGlobalLayoutListener victim) {
        removeOnGlobalLayoutListener(victim);
    }

    public void removeOnGlobalLayoutListener(OnGlobalLayoutListener victim) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners != null) {
            this.mOnGlobalLayoutListeners.remove(victim);
        }
    }

    public void addOnPreDrawListener(OnPreDrawListener listener) {
        checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            this.mOnPreDrawListeners = new CopyOnWriteArray<>();
        }
        this.mOnPreDrawListeners.add(listener);
    }

    public void removeOnPreDrawListener(OnPreDrawListener victim) {
        checkIsAlive();
        if (this.mOnPreDrawListeners != null) {
            this.mOnPreDrawListeners.remove(victim);
        }
    }

    public void addOnWindowShownListener(OnWindowShownListener listener) {
        checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            this.mOnWindowShownListeners = new CopyOnWriteArray<>();
        }
        this.mOnWindowShownListeners.add(listener);
        if (this.mWindowShown) {
            listener.onWindowShown();
        }
    }

    public void removeOnWindowShownListener(OnWindowShownListener victim) {
        checkIsAlive();
        if (this.mOnWindowShownListeners != null) {
            this.mOnWindowShownListeners.remove(victim);
        }
    }

    public void addOnDrawListener(OnDrawListener listener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            this.mOnDrawListeners = new ArrayList<>();
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException ex = new IllegalStateException("Cannot call addOnDrawListener inside of onDraw");
            if (!sIllegalOnDrawModificationIsFatal) {
                Log.e("ViewTreeObserver", ex.getMessage(), ex);
            } else {
                throw ex;
            }
        }
        this.mOnDrawListeners.add(listener);
    }

    public void removeOnDrawListener(OnDrawListener victim) {
        checkIsAlive();
        if (this.mOnDrawListeners != null) {
            if (this.mInDispatchOnDraw) {
                IllegalStateException ex = new IllegalStateException("Cannot call removeOnDrawListener inside of onDraw");
                if (!sIllegalOnDrawModificationIsFatal) {
                    Log.e("ViewTreeObserver", ex.getMessage(), ex);
                } else {
                    throw ex;
                }
            }
            this.mOnDrawListeners.remove(victim);
        }
    }

    public void registerFrameCommitCallback(Runnable callback) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            this.mOnFrameCommitListeners = new ArrayList<>();
        }
        this.mOnFrameCommitListeners.add(callback);
    }

    /* access modifiers changed from: package-private */
    public ArrayList<Runnable> captureFrameCommitCallbacks() {
        ArrayList<Runnable> ret = this.mOnFrameCommitListeners;
        this.mOnFrameCommitListeners = null;
        return ret;
    }

    public boolean unregisterFrameCommitCallback(Runnable callback) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            return false;
        }
        return this.mOnFrameCommitListeners.remove(callback);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener listener) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            this.mOnScrollChangedListeners = new CopyOnWriteArray<>();
        }
        this.mOnScrollChangedListeners.add(listener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener victim) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners != null) {
            this.mOnScrollChangedListeners.remove(victim);
        }
    }

    public void addOnTouchModeChangeListener(OnTouchModeChangeListener listener) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            this.mOnTouchModeChangeListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnTouchModeChangeListeners.add(listener);
    }

    public void removeOnTouchModeChangeListener(OnTouchModeChangeListener victim) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners != null) {
            this.mOnTouchModeChangeListeners.remove(victim);
        }
    }

    @UnsupportedAppUsage
    public void addOnComputeInternalInsetsListener(OnComputeInternalInsetsListener listener) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            this.mOnComputeInternalInsetsListeners = new CopyOnWriteArray<>();
        }
        this.mOnComputeInternalInsetsListeners.add(listener);
    }

    @UnsupportedAppUsage
    public void removeOnComputeInternalInsetsListener(OnComputeInternalInsetsListener victim) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners != null) {
            this.mOnComputeInternalInsetsListeners.remove(victim);
        }
    }

    public void addOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener listener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            this.mOnEnterAnimationCompleteListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnEnterAnimationCompleteListeners.add(listener);
    }

    public void removeOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener listener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners != null) {
            this.mOnEnterAnimationCompleteListeners.remove(listener);
        }
    }

    public void addOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> listener) {
        checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            this.mGestureExclusionListeners = new CopyOnWriteArray<>();
        }
        this.mGestureExclusionListeners.add(listener);
    }

    public void removeOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> listener) {
        checkIsAlive();
        if (this.mGestureExclusionListeners != null) {
            this.mGestureExclusionListeners.remove(listener);
        }
    }

    private void checkIsAlive() {
        if (!this.mAlive) {
            throw new IllegalStateException("This ViewTreeObserver is not alive, call getViewTreeObserver() again");
        }
    }

    public boolean isAlive() {
        return this.mAlive;
    }

    private void kill() {
        this.mAlive = false;
    }

    /* access modifiers changed from: package-private */
    public final void dispatchOnWindowAttachedChange(boolean attached) {
        CopyOnWriteArrayList<OnWindowAttachListener> listeners = this.mOnWindowAttachListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnWindowAttachListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnWindowAttachListener listener = it.next();
                if (attached) {
                    listener.onWindowAttached();
                } else {
                    listener.onWindowDetached();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void dispatchOnWindowFocusChange(boolean hasFocus) {
        CopyOnWriteArrayList<OnWindowFocusChangeListener> listeners = this.mOnWindowFocusListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnWindowFocusChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().onWindowFocusChanged(hasFocus);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final void dispatchOnGlobalFocusChange(View oldFocus, View newFocus) {
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> listeners = this.mOnGlobalFocusListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnGlobalFocusChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().onGlobalFocusChanged(oldFocus, newFocus);
            }
        }
    }

    public final void dispatchOnGlobalLayout() {
        CopyOnWriteArray<OnGlobalLayoutListener> listeners = this.mOnGlobalLayoutListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnGlobalLayoutListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onGlobalLayout();
                }
            } finally {
                listeners.end();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean hasOnPreDrawListeners() {
        return this.mOnPreDrawListeners != null && this.mOnPreDrawListeners.size() > 0;
    }

    public final boolean dispatchOnPreDraw() {
        boolean cancelDraw = false;
        CopyOnWriteArray<OnPreDrawListener> listeners = this.mOnPreDrawListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnPreDrawListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    cancelDraw |= !access.get(i).onPreDraw();
                }
            } finally {
                listeners.end();
            }
        }
        return cancelDraw;
    }

    public final void dispatchOnWindowShown() {
        this.mWindowShown = true;
        CopyOnWriteArray<OnWindowShownListener> listeners = this.mOnWindowShownListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnWindowShownListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onWindowShown();
                }
            } finally {
                listeners.end();
            }
        }
    }

    public final void dispatchOnDraw() {
        if (this.mOnDrawListeners != null) {
            this.mInDispatchOnDraw = true;
            ArrayList<OnDrawListener> listeners = this.mOnDrawListeners;
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                listeners.get(i).onDraw();
            }
            this.mInDispatchOnDraw = false;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final void dispatchOnTouchModeChanged(boolean inTouchMode) {
        CopyOnWriteArrayList<OnTouchModeChangeListener> listeners = this.mOnTouchModeChangeListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnTouchModeChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().onTouchModeChanged(inTouchMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final void dispatchOnScrollChanged() {
        CopyOnWriteArray<OnScrollChangedListener> listeners = this.mOnScrollChangedListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnScrollChangedListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onScrollChanged();
                }
            } finally {
                listeners.end();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final boolean hasComputeInternalInsetsListeners() {
        CopyOnWriteArray<OnComputeInternalInsetsListener> listeners = this.mOnComputeInternalInsetsListeners;
        return listeners != null && listeners.size() > 0;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final void dispatchOnComputeInternalInsets(InternalInsetsInfo inoutInfo) {
        CopyOnWriteArray<OnComputeInternalInsetsListener> listeners = this.mOnComputeInternalInsetsListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnComputeInternalInsetsListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onComputeInternalInsets(inoutInfo);
                }
            } finally {
                listeners.end();
            }
        }
    }

    public final void dispatchOnEnterAnimationComplete() {
        CopyOnWriteArrayList<OnEnterAnimationCompleteListener> listeners = this.mOnEnterAnimationCompleteListeners;
        if (listeners != null && !listeners.isEmpty()) {
            Iterator<OnEnterAnimationCompleteListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().onEnterAnimationComplete();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnSystemGestureExclusionRectsChanged(List<Rect> rects) {
        CopyOnWriteArray<Consumer<List<Rect>>> listeners = this.mGestureExclusionListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<Consumer<List<Rect>>> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).accept(rects);
                }
            } finally {
                listeners.end();
            }
        }
    }

    static class CopyOnWriteArray<T> {
        private final Access<T> mAccess = new Access<>();
        private ArrayList<T> mData = new ArrayList<>();
        private ArrayList<T> mDataCopy;
        private boolean mStart;

        static class Access<T> {
            /* access modifiers changed from: private */
            public ArrayList<T> mData;
            /* access modifiers changed from: private */
            public int mSize;

            Access() {
            }

            /* access modifiers changed from: package-private */
            public T get(int index) {
                return this.mData.get(index);
            }

            /* access modifiers changed from: package-private */
            public int size() {
                return this.mSize;
            }
        }

        CopyOnWriteArray() {
        }

        private ArrayList<T> getArray() {
            if (!this.mStart) {
                return this.mData;
            }
            if (this.mDataCopy == null) {
                this.mDataCopy = new ArrayList<>(this.mData);
            }
            return this.mDataCopy;
        }

        /* access modifiers changed from: package-private */
        public Access<T> start() {
            if (!this.mStart) {
                this.mStart = true;
                this.mDataCopy = null;
                ArrayList unused = this.mAccess.mData = this.mData;
                int unused2 = this.mAccess.mSize = this.mData.size();
                return this.mAccess;
            }
            throw new IllegalStateException("Iteration already started");
        }

        /* access modifiers changed from: package-private */
        public void end() {
            if (this.mStart) {
                this.mStart = false;
                if (this.mDataCopy != null) {
                    this.mData = this.mDataCopy;
                    this.mAccess.mData.clear();
                    int unused = this.mAccess.mSize = 0;
                }
                this.mDataCopy = null;
                return;
            }
            throw new IllegalStateException("Iteration not started");
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return getArray().size();
        }

        /* access modifiers changed from: package-private */
        public void add(T item) {
            getArray().add(item);
        }

        /* access modifiers changed from: package-private */
        public void addAll(CopyOnWriteArray<T> array) {
            getArray().addAll(array.mData);
        }

        /* access modifiers changed from: package-private */
        public void remove(T item) {
            getArray().remove(item);
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            getArray().clear();
        }
    }
}
