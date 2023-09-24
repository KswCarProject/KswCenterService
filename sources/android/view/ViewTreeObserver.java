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

/* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    public interface OnComputeInternalInsetsListener {
        void onComputeInternalInsets(InternalInsetsInfo internalInsetsInfo);
    }

    /* loaded from: classes4.dex */
    public interface OnDrawListener {
        void onDraw();
    }

    /* loaded from: classes4.dex */
    public interface OnEnterAnimationCompleteListener {
        void onEnterAnimationComplete();
    }

    /* loaded from: classes4.dex */
    public interface OnGlobalFocusChangeListener {
        void onGlobalFocusChanged(View view, View view2);
    }

    /* loaded from: classes4.dex */
    public interface OnGlobalLayoutListener {
        void onGlobalLayout();
    }

    /* loaded from: classes4.dex */
    public interface OnPreDrawListener {
        boolean onPreDraw();
    }

    /* loaded from: classes4.dex */
    public interface OnScrollChangedListener {
        void onScrollChanged();
    }

    /* loaded from: classes4.dex */
    public interface OnTouchModeChangeListener {
        void onTouchModeChanged(boolean z);
    }

    /* loaded from: classes4.dex */
    public interface OnWindowAttachListener {
        void onWindowAttached();

        void onWindowDetached();
    }

    /* loaded from: classes4.dex */
    public interface OnWindowFocusChangeListener {
        void onWindowFocusChanged(boolean z);
    }

    /* loaded from: classes4.dex */
    public interface OnWindowShownListener {
        void onWindowShown();
    }

    /* loaded from: classes4.dex */
    public static final class InternalInsetsInfo {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        @UnsupportedAppUsage
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        @UnsupportedAppUsage
        int mTouchableInsets;
        @UnsupportedAppUsage
        public final Rect contentInsets = new Rect();
        @UnsupportedAppUsage
        public final Rect visibleInsets = new Rect();
        @UnsupportedAppUsage
        public final Region touchableRegion = new Region();

        @UnsupportedAppUsage
        public void setTouchableInsets(int val) {
            this.mTouchableInsets = val;
        }

        void reset() {
            this.contentInsets.setEmpty();
            this.visibleInsets.setEmpty();
            this.touchableRegion.setEmpty();
            this.mTouchableInsets = 0;
        }

        boolean isEmpty() {
            return this.contentInsets.isEmpty() && this.visibleInsets.isEmpty() && this.touchableRegion.isEmpty() && this.mTouchableInsets == 0;
        }

        public int hashCode() {
            int result = this.contentInsets.hashCode();
            return (((((result * 31) + this.visibleInsets.hashCode()) * 31) + this.touchableRegion.hashCode()) * 31) + this.mTouchableInsets;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InternalInsetsInfo other = (InternalInsetsInfo) o;
            if (this.mTouchableInsets == other.mTouchableInsets && this.contentInsets.equals(other.contentInsets) && this.visibleInsets.equals(other.visibleInsets) && this.touchableRegion.equals(other.touchableRegion)) {
                return true;
            }
            return false;
        }

        @UnsupportedAppUsage
        void set(InternalInsetsInfo other) {
            this.contentInsets.set(other.contentInsets);
            this.visibleInsets.set(other.visibleInsets);
            this.touchableRegion.set(other.touchableRegion);
            this.mTouchableInsets = other.mTouchableInsets;
        }
    }

    ViewTreeObserver(Context context) {
        sIllegalOnDrawModificationIsFatal = context.getApplicationInfo().targetSdkVersion >= 26;
    }

    void merge(ViewTreeObserver observer) {
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
        if (this.mOnWindowAttachListeners == null) {
            return;
        }
        this.mOnWindowAttachListeners.remove(victim);
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
        if (this.mOnWindowFocusListeners == null) {
            return;
        }
        this.mOnWindowFocusListeners.remove(victim);
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
        if (this.mOnGlobalFocusListeners == null) {
            return;
        }
        this.mOnGlobalFocusListeners.remove(victim);
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
        if (this.mOnGlobalLayoutListeners == null) {
            return;
        }
        this.mOnGlobalLayoutListeners.remove(victim);
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
        if (this.mOnPreDrawListeners == null) {
            return;
        }
        this.mOnPreDrawListeners.remove(victim);
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
        if (this.mOnWindowShownListeners == null) {
            return;
        }
        this.mOnWindowShownListeners.remove(victim);
    }

    public void addOnDrawListener(OnDrawListener listener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            this.mOnDrawListeners = new ArrayList<>();
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException ex = new IllegalStateException("Cannot call addOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw ex;
            }
            Log.m69e("ViewTreeObserver", ex.getMessage(), ex);
        }
        this.mOnDrawListeners.add(listener);
    }

    public void removeOnDrawListener(OnDrawListener victim) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            return;
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException ex = new IllegalStateException("Cannot call removeOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw ex;
            }
            Log.m69e("ViewTreeObserver", ex.getMessage(), ex);
        }
        this.mOnDrawListeners.remove(victim);
    }

    public void registerFrameCommitCallback(Runnable callback) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            this.mOnFrameCommitListeners = new ArrayList<>();
        }
        this.mOnFrameCommitListeners.add(callback);
    }

    ArrayList<Runnable> captureFrameCommitCallbacks() {
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
        if (this.mOnScrollChangedListeners == null) {
            return;
        }
        this.mOnScrollChangedListeners.remove(victim);
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
        if (this.mOnTouchModeChangeListeners == null) {
            return;
        }
        this.mOnTouchModeChangeListeners.remove(victim);
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
        if (this.mOnComputeInternalInsetsListeners == null) {
            return;
        }
        this.mOnComputeInternalInsetsListeners.remove(victim);
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
        if (this.mOnEnterAnimationCompleteListeners == null) {
            return;
        }
        this.mOnEnterAnimationCompleteListeners.remove(listener);
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
        if (this.mGestureExclusionListeners == null) {
            return;
        }
        this.mGestureExclusionListeners.remove(listener);
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

    final void dispatchOnWindowAttachedChange(boolean attached) {
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

    final void dispatchOnWindowFocusChange(boolean hasFocus) {
        CopyOnWriteArrayList<OnWindowFocusChangeListener> listeners = this.mOnWindowFocusListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnWindowFocusChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnWindowFocusChangeListener listener = it.next();
                listener.onWindowFocusChanged(hasFocus);
            }
        }
    }

    @UnsupportedAppUsage
    final void dispatchOnGlobalFocusChange(View oldFocus, View newFocus) {
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> listeners = this.mOnGlobalFocusListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnGlobalFocusChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnGlobalFocusChangeListener listener = it.next();
                listener.onGlobalFocusChanged(oldFocus, newFocus);
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

    final boolean hasOnPreDrawListeners() {
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

    @UnsupportedAppUsage
    final void dispatchOnTouchModeChanged(boolean inTouchMode) {
        CopyOnWriteArrayList<OnTouchModeChangeListener> listeners = this.mOnTouchModeChangeListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnTouchModeChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnTouchModeChangeListener listener = it.next();
                listener.onTouchModeChanged(inTouchMode);
            }
        }
    }

    @UnsupportedAppUsage
    final void dispatchOnScrollChanged() {
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

    @UnsupportedAppUsage
    final boolean hasComputeInternalInsetsListeners() {
        CopyOnWriteArray<OnComputeInternalInsetsListener> listeners = this.mOnComputeInternalInsetsListeners;
        return listeners != null && listeners.size() > 0;
    }

    @UnsupportedAppUsage
    final void dispatchOnComputeInternalInsets(InternalInsetsInfo inoutInfo) {
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
                OnEnterAnimationCompleteListener listener = it.next();
                listener.onEnterAnimationComplete();
            }
        }
    }

    void dispatchOnSystemGestureExclusionRectsChanged(List<Rect> rects) {
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

    /* loaded from: classes4.dex */
    static class CopyOnWriteArray<T> {
        private ArrayList<T> mDataCopy;
        private boolean mStart;
        private ArrayList<T> mData = new ArrayList<>();
        private final Access<T> mAccess = new Access<>();

        /* loaded from: classes4.dex */
        static class Access<T> {
            private ArrayList<T> mData;
            private int mSize;

            Access() {
            }

            T get(int index) {
                return this.mData.get(index);
            }

            int size() {
                return this.mSize;
            }
        }

        CopyOnWriteArray() {
        }

        private ArrayList<T> getArray() {
            if (this.mStart) {
                if (this.mDataCopy == null) {
                    this.mDataCopy = new ArrayList<>(this.mData);
                }
                return this.mDataCopy;
            }
            return this.mData;
        }

        Access<T> start() {
            if (this.mStart) {
                throw new IllegalStateException("Iteration already started");
            }
            this.mStart = true;
            this.mDataCopy = null;
            ((Access) this.mAccess).mData = this.mData;
            ((Access) this.mAccess).mSize = this.mData.size();
            return this.mAccess;
        }

        void end() {
            if (!this.mStart) {
                throw new IllegalStateException("Iteration not started");
            }
            this.mStart = false;
            if (this.mDataCopy != null) {
                this.mData = this.mDataCopy;
                ((Access) this.mAccess).mData.clear();
                ((Access) this.mAccess).mSize = 0;
            }
            this.mDataCopy = null;
        }

        int size() {
            return getArray().size();
        }

        void add(T item) {
            getArray().add(item);
        }

        void addAll(CopyOnWriteArray<T> array) {
            getArray().addAll(array.mData);
        }

        void remove(T item) {
            getArray().remove(item);
        }

        void clear() {
            getArray().clear();
        }
    }
}
