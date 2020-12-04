package android.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityTransitionCoordinator;
import android.app.SharedElementCallback;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;

class EnterTransitionCoordinator extends ActivityTransitionCoordinator {
    private static final int MIN_ANIMATION_FRAMES = 2;
    private static final String TAG = "EnterTransitionCoordinator";
    private Activity mActivity;
    private boolean mAreViewsReady;
    private ObjectAnimator mBackgroundAnimator;
    /* access modifiers changed from: private */
    public Transition mEnterViewsTransition;
    private boolean mHasStopped;
    private boolean mIsCanceled;
    private final boolean mIsCrossTask;
    private boolean mIsExitTransitionComplete;
    /* access modifiers changed from: private */
    public boolean mIsReadyForTransition;
    private boolean mIsViewsTransitionStarted;
    private ArrayList<String> mPendingExitNames;
    private Drawable mReplacedBackground;
    private boolean mSharedElementTransitionStarted;
    private Bundle mSharedElementsBundle;
    private OneShotPreDrawListener mViewsReadyListener;
    private boolean mWasOpaque;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public EnterTransitionCoordinator(Activity activity, ResultReceiver resultReceiver, ArrayList<String> sharedElementNames, boolean isReturning, boolean isCrossTask) {
        super(activity.getWindow(), sharedElementNames, getListener(activity, isReturning && !isCrossTask), isReturning);
        this.mActivity = activity;
        this.mIsCrossTask = isCrossTask;
        setResultReceiver(resultReceiver);
        prepareEnter();
        Bundle resultReceiverBundle = new Bundle();
        resultReceiverBundle.putParcelable("android:remoteReceiver", this);
        this.mResultReceiver.send(100, resultReceiverBundle);
        final View decorView = getDecor();
        if (decorView != null) {
            final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (!EnterTransitionCoordinator.this.mIsReadyForTransition) {
                        return false;
                    }
                    if (viewTreeObserver.isAlive()) {
                        viewTreeObserver.removeOnPreDrawListener(this);
                        return false;
                    }
                    decorView.getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isCrossTask() {
        return this.mIsCrossTask;
    }

    public void viewInstancesReady(ArrayList<String> accepted, ArrayList<String> localNames, ArrayList<View> localViews) {
        boolean remap = false;
        int i = 0;
        while (true) {
            if (i >= localViews.size()) {
                break;
            }
            View view = localViews.get(i);
            if (!TextUtils.equals(view.getTransitionName(), localNames.get(i)) || !view.isAttachedToWindow()) {
                remap = true;
            } else {
                i++;
            }
        }
        remap = true;
        if (remap) {
            triggerViewsReady(mapNamedElements(accepted, localNames));
        } else {
            triggerViewsReady(mapSharedElements(accepted, localViews));
        }
    }

    public void namedViewsReady(ArrayList<String> accepted, ArrayList<String> localNames) {
        triggerViewsReady(mapNamedElements(accepted, localNames));
    }

    public Transition getEnterViewsTransition() {
        return this.mEnterViewsTransition;
    }

    /* access modifiers changed from: protected */
    public void viewsReady(ArrayMap<String, View> sharedElements) {
        super.viewsReady(sharedElements);
        this.mIsReadyForTransition = true;
        hideViews(this.mSharedElements);
        Transition viewsTransition = getViewsTransition();
        if (!(viewsTransition == null || this.mTransitioningViews == null)) {
            removeExcludedViews(viewsTransition, this.mTransitioningViews);
            stripOffscreenViews();
            hideViews(this.mTransitioningViews);
        }
        if (this.mIsReturning) {
            sendSharedElementDestination();
        } else {
            moveSharedElementsToOverlay();
        }
        if (this.mSharedElementsBundle != null) {
            onTakeSharedElements();
        }
    }

    private void triggerViewsReady(ArrayMap<String, View> sharedElements) {
        if (!this.mAreViewsReady) {
            this.mAreViewsReady = true;
            ViewGroup decor = getDecor();
            if (decor == null || (decor.isAttachedToWindow() && (sharedElements.isEmpty() || !sharedElements.valueAt(0).isLayoutRequested()))) {
                viewsReady(sharedElements);
                return;
            }
            this.mViewsReadyListener = OneShotPreDrawListener.add(decor, new Runnable(sharedElements) {
                private final /* synthetic */ ArrayMap f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    EnterTransitionCoordinator.lambda$triggerViewsReady$0(EnterTransitionCoordinator.this, this.f$1);
                }
            });
            decor.invalidate();
        }
    }

    public static /* synthetic */ void lambda$triggerViewsReady$0(EnterTransitionCoordinator enterTransitionCoordinator, ArrayMap sharedElements) {
        enterTransitionCoordinator.mViewsReadyListener = null;
        enterTransitionCoordinator.viewsReady(sharedElements);
    }

    private ArrayMap<String, View> mapNamedElements(ArrayList<String> accepted, ArrayList<String> localNames) {
        View view;
        ArrayMap<String, View> sharedElements = new ArrayMap<>();
        ViewGroup decorView = getDecor();
        if (decorView != null) {
            decorView.findNamedViews(sharedElements);
        }
        if (accepted != null) {
            for (int i = 0; i < localNames.size(); i++) {
                String localName = localNames.get(i);
                String acceptedName = accepted.get(i);
                if (!(localName == null || localName.equals(acceptedName) || (view = sharedElements.get(localName)) == null)) {
                    sharedElements.put(acceptedName, view);
                }
            }
        }
        return sharedElements;
    }

    private void sendSharedElementDestination() {
        boolean allReady;
        View decorView = getDecor();
        if (allowOverlappingTransitions() && getEnterViewsTransition() != null) {
            allReady = false;
        } else if (decorView == null) {
            allReady = true;
        } else {
            allReady = !decorView.isLayoutRequested();
            if (allReady) {
                int i = 0;
                while (true) {
                    if (i >= this.mSharedElements.size()) {
                        break;
                    } else if (((View) this.mSharedElements.get(i)).isLayoutRequested()) {
                        allReady = false;
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        if (allReady) {
            Bundle state = captureSharedElementState();
            moveSharedElementsToOverlay();
            this.mResultReceiver.send(107, state);
        } else if (decorView != null) {
            OneShotPreDrawListener.add(decorView, new Runnable() {
                public final void run() {
                    EnterTransitionCoordinator.lambda$sendSharedElementDestination$1(EnterTransitionCoordinator.this);
                }
            });
        }
        if (allowOverlappingTransitions()) {
            startEnterTransitionOnly();
        }
    }

    public static /* synthetic */ void lambda$sendSharedElementDestination$1(EnterTransitionCoordinator enterTransitionCoordinator) {
        if (enterTransitionCoordinator.mResultReceiver != null) {
            Bundle state = enterTransitionCoordinator.captureSharedElementState();
            enterTransitionCoordinator.moveSharedElementsToOverlay();
            enterTransitionCoordinator.mResultReceiver.send(107, state);
        }
    }

    private static SharedElementCallback getListener(Activity activity, boolean isReturning) {
        return isReturning ? activity.mExitTransitionListener : activity.mEnterTransitionListener;
    }

    /* access modifiers changed from: protected */
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case 103:
                if (!this.mIsCanceled) {
                    this.mSharedElementsBundle = resultData;
                    onTakeSharedElements();
                    return;
                }
                return;
            case 104:
                if (!this.mIsCanceled) {
                    this.mIsExitTransitionComplete = true;
                    if (this.mSharedElementTransitionStarted) {
                        onRemoteExitTransitionComplete();
                        return;
                    }
                    return;
                }
                return;
            case 106:
                cancel();
                return;
            case 108:
                if (!this.mIsCanceled) {
                    this.mPendingExitNames = this.mAllSharedElementNames;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean isWaitingForRemoteExit() {
        return this.mIsReturning && this.mResultReceiver != null;
    }

    public ArrayList<String> getPendingExitSharedElementNames() {
        return this.mPendingExitNames;
    }

    public void forceViewsToAppear() {
        if (this.mIsReturning) {
            if (!this.mIsReadyForTransition) {
                this.mIsReadyForTransition = true;
                if (!(getDecor() == null || this.mViewsReadyListener == null)) {
                    this.mViewsReadyListener.removeListener();
                    this.mViewsReadyListener = null;
                }
                showViews(this.mTransitioningViews, true);
                setTransitioningViewsVisiblity(0, true);
                this.mSharedElements.clear();
                this.mAllSharedElementNames.clear();
                this.mTransitioningViews.clear();
                this.mIsReadyForTransition = true;
                viewsTransitionComplete();
                sharedElementTransitionComplete();
            } else {
                if (!this.mSharedElementTransitionStarted) {
                    moveSharedElementsFromOverlay();
                    this.mSharedElementTransitionStarted = true;
                    showViews(this.mSharedElements, true);
                    this.mSharedElements.clear();
                    sharedElementTransitionComplete();
                }
                if (!this.mIsViewsTransitionStarted) {
                    this.mIsViewsTransitionStarted = true;
                    showViews(this.mTransitioningViews, true);
                    setTransitioningViewsVisiblity(0, true);
                    this.mTransitioningViews.clear();
                    viewsTransitionComplete();
                }
                cancelPendingTransitions();
            }
            this.mAreViewsReady = true;
            if (this.mResultReceiver != null) {
                this.mResultReceiver.send(106, (Bundle) null);
                this.mResultReceiver = null;
            }
        }
    }

    private void cancel() {
        if (!this.mIsCanceled) {
            this.mIsCanceled = true;
            if (getViewsTransition() == null || this.mIsViewsTransitionStarted) {
                showViews(this.mSharedElements, true);
            } else if (this.mTransitioningViews != null) {
                this.mTransitioningViews.addAll(this.mSharedElements);
            }
            moveSharedElementsFromOverlay();
            this.mSharedElementNames.clear();
            this.mSharedElements.clear();
            this.mAllSharedElementNames.clear();
            startSharedElementTransition((Bundle) null);
            onRemoteExitTransitionComplete();
        }
    }

    public boolean isReturning() {
        return this.mIsReturning;
    }

    /* access modifiers changed from: protected */
    public void prepareEnter() {
        Drawable background;
        ViewGroup decorView = getDecor();
        if (this.mActivity != null && decorView != null) {
            if (!isCrossTask()) {
                this.mActivity.overridePendingTransition(0, 0);
            }
            if (!this.mIsReturning) {
                this.mWasOpaque = this.mActivity.convertToTranslucent((Activity.TranslucentConversionListener) null, (ActivityOptions) null);
                Drawable background2 = decorView.getBackground();
                if (background2 == null) {
                    background = new ColorDrawable(0);
                    this.mReplacedBackground = background;
                } else {
                    getWindow().setBackgroundDrawable((Drawable) null);
                    background = background2.mutate();
                    background.setAlpha(0);
                }
                getWindow().setBackgroundDrawable(background);
                return;
            }
            this.mActivity = null;
        }
    }

    /* access modifiers changed from: protected */
    public Transition getViewsTransition() {
        Window window = getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getReenterTransition();
        }
        return window.getEnterTransition();
    }

    /* access modifiers changed from: protected */
    public Transition getSharedElementTransition() {
        Window window = getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getSharedElementReenterTransition();
        }
        return window.getSharedElementEnterTransition();
    }

    /* access modifiers changed from: private */
    public void startSharedElementTransition(Bundle sharedElementState) {
        ViewGroup decorView = getDecor();
        if (decorView != null) {
            ArrayList<String> rejectedNames = new ArrayList<>(this.mAllSharedElementNames);
            rejectedNames.removeAll(this.mSharedElementNames);
            ArrayList<View> rejectedSnapshots = createSnapshots(sharedElementState, rejectedNames);
            if (this.mListener != null) {
                this.mListener.onRejectSharedElements(rejectedSnapshots);
            }
            removeNullViews(rejectedSnapshots);
            startRejectedAnimations(rejectedSnapshots);
            ArrayList<View> sharedElementSnapshots = createSnapshots(sharedElementState, this.mSharedElementNames);
            boolean startEnterTransition = true;
            showViews(this.mSharedElements, true);
            scheduleSetSharedElementEnd(sharedElementSnapshots);
            ArrayList<ActivityTransitionCoordinator.SharedElementOriginalState> originalImageViewState = setSharedElementState(sharedElementState, sharedElementSnapshots);
            requestLayoutForSharedElements();
            if (!allowOverlappingTransitions() || this.mIsReturning) {
                startEnterTransition = false;
            }
            setGhostVisibility(4);
            scheduleGhostVisibilityChange(4);
            pauseInput();
            Transition transition = beginTransition(decorView, startEnterTransition, true);
            scheduleGhostVisibilityChange(0);
            setGhostVisibility(0);
            if (startEnterTransition) {
                startEnterTransition(transition);
            }
            setOriginalSharedElementState(this.mSharedElements, originalImageViewState);
            if (this.mResultReceiver != null) {
                decorView.postOnAnimation(new Runnable() {
                    int mAnimations;

                    public void run() {
                        int i = this.mAnimations;
                        this.mAnimations = i + 1;
                        if (i < 2) {
                            View decorView = EnterTransitionCoordinator.this.getDecor();
                            if (decorView != null) {
                                decorView.postOnAnimation(this);
                            }
                        } else if (EnterTransitionCoordinator.this.mResultReceiver != null) {
                            EnterTransitionCoordinator.this.mResultReceiver.send(101, (Bundle) null);
                            EnterTransitionCoordinator.this.mResultReceiver = null;
                        }
                    }
                });
            }
        }
    }

    private static void removeNullViews(ArrayList<View> views) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                if (views.get(i) == null) {
                    views.remove(i);
                }
            }
        }
    }

    private void onTakeSharedElements() {
        if (this.mIsReadyForTransition && this.mSharedElementsBundle != null) {
            final Bundle sharedElementState = this.mSharedElementsBundle;
            this.mSharedElementsBundle = null;
            SharedElementCallback.OnSharedElementsReadyListener listener = new SharedElementCallback.OnSharedElementsReadyListener() {
                public void onSharedElementsReady() {
                    View decorView = EnterTransitionCoordinator.this.getDecor();
                    if (decorView != null) {
                        OneShotPreDrawListener.add(decorView, false, new Runnable(sharedElementState) {
                            private final /* synthetic */ Bundle f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                EnterTransitionCoordinator.this.startTransition(new Runnable(this.f$1) {
                                    private final /* synthetic */ Bundle f$1;

                                    {
                                        this.f$1 = r2;
                                    }

                                    public final void run() {
                                        EnterTransitionCoordinator.this.startSharedElementTransition(this.f$1);
                                    }
                                });
                            }
                        });
                        decorView.invalidate();
                    }
                }
            };
            if (this.mListener == null) {
                listener.onSharedElementsReady();
            } else {
                this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, listener);
            }
        }
    }

    private void requestLayoutForSharedElements() {
        int numSharedElements = this.mSharedElements.size();
        for (int i = 0; i < numSharedElements; i++) {
            ((View) this.mSharedElements.get(i)).requestLayout();
        }
    }

    /* access modifiers changed from: private */
    public Transition beginTransition(ViewGroup decorView, boolean startEnterTransition, boolean startSharedElementTransition) {
        Transition sharedElementTransition = null;
        if (startSharedElementTransition) {
            if (!this.mSharedElementNames.isEmpty()) {
                sharedElementTransition = configureTransition(getSharedElementTransition(), false);
            }
            if (sharedElementTransition == null) {
                sharedElementTransitionStarted();
                sharedElementTransitionComplete();
            } else {
                sharedElementTransition.addListener(new TransitionListenerAdapter() {
                    public void onTransitionStart(Transition transition) {
                        EnterTransitionCoordinator.this.sharedElementTransitionStarted();
                    }

                    public void onTransitionEnd(Transition transition) {
                        transition.removeListener(this);
                        EnterTransitionCoordinator.this.sharedElementTransitionComplete();
                    }
                });
            }
        }
        Transition viewsTransition = null;
        if (startEnterTransition) {
            this.mIsViewsTransitionStarted = true;
            if (this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
                viewsTransition = configureTransition(getViewsTransition(), true);
            }
            if (viewsTransition == null) {
                viewsTransitionComplete();
            } else {
                final ArrayList<View> transitioningViews = this.mTransitioningViews;
                viewsTransition.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener() {
                    public void onTransitionStart(Transition transition) {
                        Transition unused = EnterTransitionCoordinator.this.mEnterViewsTransition = transition;
                        if (transitioningViews != null) {
                            EnterTransitionCoordinator.this.showViews(transitioningViews, false);
                        }
                        super.onTransitionStart(transition);
                    }

                    public void onTransitionEnd(Transition transition) {
                        Transition unused = EnterTransitionCoordinator.this.mEnterViewsTransition = null;
                        transition.removeListener(this);
                        EnterTransitionCoordinator.this.viewsTransitionComplete();
                        super.onTransitionEnd(transition);
                    }
                });
            }
        }
        Transition transition = mergeTransitions(sharedElementTransition, viewsTransition);
        if (transition != null) {
            transition.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener());
            if (startEnterTransition) {
                setTransitioningViewsVisiblity(4, false);
            }
            TransitionManager.beginDelayedTransition(decorView, transition);
            if (startEnterTransition) {
                setTransitioningViewsVisiblity(0, false);
            }
            decorView.invalidate();
        } else {
            transitionStarted();
        }
        return transition;
    }

    /* access modifiers changed from: protected */
    public void onTransitionsComplete() {
        moveSharedElementsFromOverlay();
        ViewGroup decorView = getDecor();
        if (decorView != null) {
            decorView.sendAccessibilityEvent(2048);
            Window window = getWindow();
            if (window != null && this.mReplacedBackground == decorView.getBackground()) {
                window.setBackgroundDrawable((Drawable) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void sharedElementTransitionStarted() {
        this.mSharedElementTransitionStarted = true;
        if (this.mIsExitTransitionComplete) {
            send(104, (Bundle) null);
        }
    }

    /* access modifiers changed from: private */
    public void startEnterTransition(Transition transition) {
        ViewGroup decorView = getDecor();
        if (this.mIsReturning || decorView == null) {
            backgroundAnimatorComplete();
            return;
        }
        Drawable background = decorView.getBackground();
        if (background != null) {
            Drawable background2 = background.mutate();
            getWindow().setBackgroundDrawable(background2);
            this.mBackgroundAnimator = ObjectAnimator.ofInt((Object) background2, "alpha", 255);
            this.mBackgroundAnimator.setDuration(getFadeDuration());
            this.mBackgroundAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    EnterTransitionCoordinator.this.makeOpaque();
                    EnterTransitionCoordinator.this.backgroundAnimatorComplete();
                }
            });
            this.mBackgroundAnimator.start();
        } else if (transition != null) {
            transition.addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    EnterTransitionCoordinator.this.makeOpaque();
                }
            });
            backgroundAnimatorComplete();
        } else {
            makeOpaque();
            backgroundAnimatorComplete();
        }
    }

    public void stop() {
        ViewGroup decorView;
        Drawable drawable;
        if (this.mBackgroundAnimator != null) {
            this.mBackgroundAnimator.end();
            this.mBackgroundAnimator = null;
        } else if (!(!this.mWasOpaque || (decorView = getDecor()) == null || (drawable = decorView.getBackground()) == null)) {
            drawable.setAlpha(1);
        }
        makeOpaque();
        this.mIsCanceled = true;
        this.mResultReceiver = null;
        this.mActivity = null;
        moveSharedElementsFromOverlay();
        if (this.mTransitioningViews != null) {
            showViews(this.mTransitioningViews, true);
            setTransitioningViewsVisiblity(0, true);
        }
        showViews(this.mSharedElements, true);
        clearState();
    }

    public boolean cancelEnter() {
        setGhostVisibility(4);
        this.mHasStopped = true;
        this.mIsCanceled = true;
        clearState();
        return super.cancelPendingTransitions();
    }

    /* access modifiers changed from: protected */
    public void clearState() {
        this.mSharedElementsBundle = null;
        this.mEnterViewsTransition = null;
        this.mResultReceiver = null;
        if (this.mBackgroundAnimator != null) {
            this.mBackgroundAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        super.clearState();
    }

    /* access modifiers changed from: private */
    public void makeOpaque() {
        if (!this.mHasStopped && this.mActivity != null) {
            if (this.mWasOpaque) {
                this.mActivity.convertFromTranslucent();
            }
            this.mActivity = null;
        }
    }

    private boolean allowOverlappingTransitions() {
        if (this.mIsReturning) {
            return getWindow().getAllowReturnTransitionOverlap();
        }
        return getWindow().getAllowEnterTransitionOverlap();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0009, code lost:
        r0 = getDecor();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void startRejectedAnimations(final java.util.ArrayList<android.view.View> r9) {
        /*
            r8 = this;
            if (r9 == 0) goto L_0x003f
            boolean r0 = r9.isEmpty()
            if (r0 == 0) goto L_0x0009
            goto L_0x003f
        L_0x0009:
            android.view.ViewGroup r0 = r8.getDecor()
            if (r0 == 0) goto L_0x003e
            android.view.ViewGroupOverlay r1 = r0.getOverlay()
            r2 = 0
            int r3 = r9.size()
            r4 = 0
        L_0x0019:
            if (r4 >= r3) goto L_0x0036
            java.lang.Object r5 = r9.get(r4)
            android.view.View r5 = (android.view.View) r5
            r1.add(r5)
            android.util.Property<android.view.View, java.lang.Float> r6 = android.view.View.ALPHA
            r7 = 2
            float[] r7 = new float[r7]
            r7 = {1065353216, 0} // fill-array
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r5, r6, (float[]) r7)
            r2.start()
            int r4 = r4 + 1
            goto L_0x0019
        L_0x0036:
            android.app.EnterTransitionCoordinator$8 r4 = new android.app.EnterTransitionCoordinator$8
            r4.<init>(r0, r9)
            r2.addListener(r4)
        L_0x003e:
            return
        L_0x003f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.EnterTransitionCoordinator.startRejectedAnimations(java.util.ArrayList):void");
    }

    /* access modifiers changed from: protected */
    public void onRemoteExitTransitionComplete() {
        if (!allowOverlappingTransitions()) {
            startEnterTransitionOnly();
        }
    }

    private void startEnterTransitionOnly() {
        startTransition(new Runnable() {
            public void run() {
                ViewGroup decorView = EnterTransitionCoordinator.this.getDecor();
                if (decorView != null) {
                    EnterTransitionCoordinator.this.startEnterTransition(EnterTransitionCoordinator.this.beginTransition(decorView, true, false));
                }
            }
        });
    }
}
