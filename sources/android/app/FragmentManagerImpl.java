package android.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.p007os.Bundle;
import android.p007os.Looper;
import android.p007os.Parcelable;
import android.provider.SettingsStringUtil;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.Log;
import android.util.LogWriter;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.C3132R;
import com.android.internal.util.FastPrintWriter;
import com.ibm.icu.text.PluralRules;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: FragmentManager.java */
/* loaded from: classes.dex */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 {
    static boolean DEBUG = false;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    @UnsupportedAppUsage
    SparseArray<Fragment> mActive;
    boolean mAllowOldReentrantBehavior;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    boolean mDestroyed;
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback<?> mHost;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    FragmentManagerNonConfig mSavedNonConfig;
    @UnsupportedAppUsage
    boolean mStateSaved;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;
    int mNextFragmentIndex = 0;
    @UnsupportedAppUsage
    final ArrayList<Fragment> mAdded = new ArrayList<>();
    final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList<>();
    int mCurState = 0;
    Bundle mStateBundle = null;
    SparseArray<Parcelable> mStateArray = null;
    Runnable mExecCommit = new Runnable() { // from class: android.app.FragmentManagerImpl.1
        @Override // java.lang.Runnable
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };

    /* compiled from: FragmentManager.java */
    /* loaded from: classes.dex */
    interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    FragmentManagerImpl() {
    }

    /* compiled from: FragmentManager.java */
    /* loaded from: classes.dex */
    static class AnimateOnHWLayerIfNeededListener implements Animator.AnimatorListener {
        private boolean mShouldRunOnHWLayer = false;
        private View mView;

        public AnimateOnHWLayerIfNeededListener(View v) {
            if (v == null) {
                return;
            }
            this.mView = v;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            this.mShouldRunOnHWLayer = FragmentManagerImpl.shouldRunOnHWLayer(this.mView, animation);
            if (this.mShouldRunOnHWLayer) {
                this.mView.setLayerType(2, null);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            if (this.mShouldRunOnHWLayer) {
                this.mView.setLayerType(0, null);
            }
            this.mView = null;
            animation.removeListener(this);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animation) {
        }
    }

    private void throwException(RuntimeException ex) {
        Log.m70e(TAG, ex.getMessage());
        LogWriter logw = new LogWriter(6, TAG);
        PrintWriter pw = new FastPrintWriter((Writer) logw, false, 1024);
        if (this.mHost != null) {
            Log.m70e(TAG, "Activity state:");
            try {
                this.mHost.onDump("  ", null, pw, new String[0]);
            } catch (Exception e) {
                pw.flush();
                Log.m69e(TAG, "Failed dumping state", e);
            }
        } else {
            Log.m70e(TAG, "Fragment manager state:");
            try {
                dump("  ", null, pw, new String[0]);
            } catch (Exception e2) {
                pw.flush();
                Log.m69e(TAG, "Failed dumping state", e2);
            }
        }
        pw.flush();
        throw ex;
    }

    static boolean modifiesAlpha(Animator anim) {
        if (anim == null) {
            return false;
        }
        if (anim instanceof ValueAnimator) {
            ValueAnimator valueAnim = (ValueAnimator) anim;
            PropertyValuesHolder[] values = valueAnim.getValues();
            for (PropertyValuesHolder propertyValuesHolder : values) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
        } else if (anim instanceof AnimatorSet) {
            List<Animator> animList = ((AnimatorSet) anim).getChildAnimations();
            for (int i = 0; i < animList.size(); i++) {
                if (modifiesAlpha(animList.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(View v, Animator anim) {
        return v != null && anim != null && v.getLayerType() == 0 && v.hasOverlappingRendering() && modifiesAlpha(anim);
    }

    private void setHWLayerAnimListenerIfAlpha(View v, Animator anim) {
        if (v != null && anim != null && shouldRunOnHWLayer(v, anim)) {
            anim.addListener(new AnimateOnHWLayerIfNeededListener(v));
        }
    }

    @Override // android.app.FragmentManager
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    @Override // android.app.FragmentManager
    public boolean executePendingTransactions() {
        boolean updates = execPendingActions();
        forcePostponedTransactions();
        return updates;
    }

    @Override // android.app.FragmentManager
    public void popBackStack() {
        enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    @Override // android.app.FragmentManager
    public boolean popBackStackImmediate() {
        checkStateLoss();
        return popBackStackImmediate(null, -1, 0);
    }

    @Override // android.app.FragmentManager
    public void popBackStack(String name, int flags) {
        enqueueAction(new PopBackStackState(name, -1, flags), false);
    }

    @Override // android.app.FragmentManager
    public boolean popBackStackImmediate(String name, int flags) {
        checkStateLoss();
        return popBackStackImmediate(name, -1, flags);
    }

    @Override // android.app.FragmentManager
    public void popBackStack(int id, int flags) {
        if (id < 0) {
            throw new IllegalArgumentException("Bad id: " + id);
        }
        enqueueAction(new PopBackStackState(null, id, flags), false);
    }

    @Override // android.app.FragmentManager
    public boolean popBackStackImmediate(int id, int flags) {
        checkStateLoss();
        if (id < 0) {
            throw new IllegalArgumentException("Bad id: " + id);
        }
        return popBackStackImmediate(null, id, flags);
    }

    private boolean popBackStackImmediate(String name, int id, int flags) {
        FragmentManager childManager;
        execPendingActions();
        ensureExecReady(true);
        if (this.mPrimaryNav != null && id < 0 && name == null && (childManager = this.mPrimaryNav.mChildFragmentManager) != null && childManager.popBackStackImmediate()) {
            return true;
        }
        boolean executePop = popBackStackState(this.mTmpRecords, this.mTmpIsPop, name, id, flags);
        if (executePop) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
        return executePop;
    }

    @Override // android.app.FragmentManager
    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    @Override // android.app.FragmentManager
    public FragmentManager.BackStackEntry getBackStackEntryAt(int index) {
        return this.mBackStack.get(index);
    }

    @Override // android.app.FragmentManager
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(listener);
    }

    @Override // android.app.FragmentManager
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(listener);
        }
    }

    @Override // android.app.FragmentManager
    public void putFragment(Bundle bundle, String key, Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(key, fragment.mIndex);
    }

    @Override // android.app.FragmentManager
    public Fragment getFragment(Bundle bundle, String key) {
        int index = bundle.getInt(key, -1);
        if (index == -1) {
            return null;
        }
        Fragment f = this.mActive.get(index);
        if (f == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + key + ": index " + index));
        }
        return f;
    }

    @Override // android.app.FragmentManager
    public List<Fragment> getFragments() {
        List<Fragment> list;
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        synchronized (this.mAdded) {
            list = (List) this.mAdded.clone();
        }
        return list;
    }

    @Override // android.app.FragmentManager
    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        Bundle result;
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        if (fragment.mState <= 0 || (result = saveFragmentBasicState(fragment)) == null) {
            return null;
        }
        return new Fragment.SavedState(result);
    }

    @Override // android.app.FragmentManager
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, sb);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    @Override // android.app.FragmentManager
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        int N;
        int N2;
        int N3;
        int N4;
        int N5;
        String innerPrefix = prefix + "    ";
        if (this.mActive != null && (N5 = this.mActive.size()) > 0) {
            writer.print(prefix);
            writer.print("Active Fragments in ");
            writer.print(Integer.toHexString(System.identityHashCode(this)));
            writer.println(SettingsStringUtil.DELIMITER);
            for (int i = 0; i < N5; i++) {
                Fragment f = this.mActive.valueAt(i);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i);
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(f);
                if (f != null) {
                    f.dump(innerPrefix, fd, writer, args);
                }
            }
        }
        int N6 = this.mAdded.size();
        if (N6 > 0) {
            writer.print(prefix);
            writer.println("Added Fragments:");
            for (int i2 = 0; i2 < N6; i2++) {
                Fragment f2 = this.mAdded.get(i2);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i2);
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(f2.toString());
            }
        }
        if (this.mCreatedMenus != null && (N4 = this.mCreatedMenus.size()) > 0) {
            writer.print(prefix);
            writer.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < N4; i3++) {
                Fragment f3 = this.mCreatedMenus.get(i3);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i3);
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(f3.toString());
            }
        }
        if (this.mBackStack != null && (N3 = this.mBackStack.size()) > 0) {
            writer.print(prefix);
            writer.println("Back Stack:");
            for (int i4 = 0; i4 < N3; i4++) {
                BackStackRecord bs = this.mBackStack.get(i4);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i4);
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(bs.toString());
                bs.dump(innerPrefix, fd, writer, args);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (N2 = this.mBackStackIndices.size()) > 0) {
                writer.print(prefix);
                writer.println("Back Stack Indices:");
                for (int i5 = 0; i5 < N2; i5++) {
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i5);
                    writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                    writer.println(this.mBackStackIndices.get(i5));
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                writer.print(prefix);
                writer.print("mAvailBackStackIndices: ");
                writer.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null && (N = this.mPendingActions.size()) > 0) {
            writer.print(prefix);
            writer.println("Pending Actions:");
            for (int i6 = 0; i6 < N; i6++) {
                OpGenerator r = this.mPendingActions.get(i6);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i6);
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(r);
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mHost=");
        writer.println(this.mHost);
        writer.print(prefix);
        writer.print("  mContainer=");
        writer.println(this.mContainer);
        if (this.mParent != null) {
            writer.print(prefix);
            writer.print("  mParent=");
            writer.println(this.mParent);
        }
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            writer.print(prefix);
            writer.print("  mNoTransactionsBecause=");
            writer.println(this.mNoTransactionsBecause);
        }
    }

    @UnsupportedAppUsage
    Animator loadAnimator(Fragment fragment, int transit, boolean enter, int transitionStyle) {
        int styleIndex;
        Animator anim;
        Animator animObj = fragment.onCreateAnimator(transit, enter, fragment.getNextAnim());
        if (animObj != null) {
            return animObj;
        }
        if (fragment.getNextAnim() != 0 && (anim = AnimatorInflater.loadAnimator(this.mHost.getContext(), fragment.getNextAnim())) != null) {
            return anim;
        }
        if (transit == 0 || (styleIndex = transitToStyleIndex(transit, enter)) < 0) {
            return null;
        }
        if (transitionStyle == 0 && this.mHost.onHasWindowAnimations()) {
            transitionStyle = this.mHost.onGetWindowAnimations();
        }
        if (transitionStyle == 0) {
            return null;
        }
        TypedArray attrs = this.mHost.getContext().obtainStyledAttributes(transitionStyle, C3132R.styleable.FragmentAnimation);
        int anim2 = attrs.getResourceId(styleIndex, 0);
        attrs.recycle();
        if (anim2 == 0) {
            return null;
        }
        return AnimatorInflater.loadAnimator(this.mHost.getContext(), anim2);
    }

    public void performPendingDeferredStart(Fragment f) {
        if (f.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
                return;
            }
            f.mDeferStart = false;
            moveToState(f, this.mCurState, 0, 0, false);
        }
    }

    boolean isStateAtLeast(int state) {
        return this.mCurState >= state;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:215:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void moveToState(final Fragment f, int newState, int transit, int transitionStyle, boolean keepActive) {
        int newState2;
        String resName;
        FragmentManagerImpl fragmentManagerImpl;
        boolean z = DEBUG;
        boolean z2 = true;
        if (!f.mAdded || f.mDetached) {
            newState2 = newState;
            if (newState2 > 1) {
                newState2 = 1;
            }
        } else {
            newState2 = newState;
        }
        if (f.mRemoving && newState2 > f.mState) {
            if (f.mState == 0 && f.isInBackStack()) {
                newState2 = 1;
            } else {
                newState2 = f.mState;
            }
        }
        if (f.mDeferStart && f.mState < 4 && newState2 > 3) {
            newState2 = 3;
        }
        if (f.mState <= newState2) {
            if (f.mFromLayout && !f.mInLayout) {
                return;
            }
            if (f.getAnimatingAway() != null) {
                f.setAnimatingAway(null);
                moveToState(f, f.getStateAfterAnimating(), 0, 0, true);
            }
            switch (f.mState) {
                case 0:
                    if (newState2 > 0) {
                        if (DEBUG) {
                            Log.m66v(TAG, "moveto CREATED: " + f);
                        }
                        if (f.mSavedFragmentState != null) {
                            f.mSavedViewState = f.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            f.mTarget = getFragment(f.mSavedFragmentState, TARGET_STATE_TAG);
                            if (f.mTarget != null) {
                                f.mTargetRequestCode = f.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
                            }
                            f.mUserVisibleHint = f.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
                            if (!f.mUserVisibleHint) {
                                f.mDeferStart = true;
                                if (newState2 > 3) {
                                    newState2 = 3;
                                }
                            }
                        }
                        f.mHost = this.mHost;
                        f.mParentFragment = this.mParent;
                        if (this.mParent == null) {
                            fragmentManagerImpl = this.mHost.getFragmentManagerImpl();
                        } else {
                            fragmentManagerImpl = this.mParent.mChildFragmentManager;
                        }
                        f.mFragmentManager = fragmentManagerImpl;
                        if (f.mTarget != null) {
                            if (this.mActive.get(f.mTarget.mIndex) != f.mTarget) {
                                throw new IllegalStateException("Fragment " + f + " declared target fragment " + f.mTarget + " that does not belong to this FragmentManager!");
                            } else if (f.mTarget.mState < 1) {
                                moveToState(f.mTarget, 1, 0, 0, true);
                            }
                        }
                        dispatchOnFragmentPreAttached(f, this.mHost.getContext(), false);
                        f.mCalled = false;
                        f.onAttach(this.mHost.getContext());
                        if (!f.mCalled) {
                            throw new SuperNotCalledException("Fragment " + f + " did not call through to super.onAttach()");
                        }
                        if (f.mParentFragment == null) {
                            this.mHost.onAttachFragment(f);
                        } else {
                            f.mParentFragment.onAttachFragment(f);
                        }
                        dispatchOnFragmentAttached(f, this.mHost.getContext(), false);
                        if (!f.mIsCreated) {
                            dispatchOnFragmentPreCreated(f, f.mSavedFragmentState, false);
                            f.performCreate(f.mSavedFragmentState);
                            dispatchOnFragmentCreated(f, f.mSavedFragmentState, false);
                        } else {
                            f.restoreChildFragmentState(f.mSavedFragmentState, true);
                            f.mState = 1;
                        }
                        f.mRetaining = false;
                    }
                case 1:
                    int newState3 = newState2;
                    ensureInflatedFragmentView(f);
                    if (newState3 > 1) {
                        if (DEBUG) {
                            Log.m66v(TAG, "moveto ACTIVITY_CREATED: " + f);
                        }
                        if (!f.mFromLayout) {
                            ViewGroup container = null;
                            if (f.mContainerId != 0) {
                                if (f.mContainerId == -1) {
                                    throwException(new IllegalArgumentException("Cannot create fragment " + f + " for a container view with no id"));
                                }
                                ViewGroup container2 = (ViewGroup) this.mContainer.onFindViewById(f.mContainerId);
                                if (container2 == null && !f.mRestored) {
                                    try {
                                        resName = f.getResources().getResourceName(f.mContainerId);
                                    } catch (Resources.NotFoundException e) {
                                        resName = "unknown";
                                    }
                                    throwException(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(f.mContainerId) + " (" + resName + ") for fragment " + f));
                                }
                                container = container2;
                            }
                            f.mContainer = container;
                            f.mView = f.performCreateView(f.performGetLayoutInflater(f.mSavedFragmentState), container, f.mSavedFragmentState);
                            if (f.mView != null) {
                                f.mView.setSaveFromParentEnabled(false);
                                if (container != null) {
                                    container.addView(f.mView);
                                }
                                if (f.mHidden) {
                                    f.mView.setVisibility(8);
                                }
                                f.onViewCreated(f.mView, f.mSavedFragmentState);
                                dispatchOnFragmentViewCreated(f, f.mView, f.mSavedFragmentState, false);
                                if (f.mView.getVisibility() != 0 || f.mContainer == null) {
                                    z2 = false;
                                }
                                f.mIsNewlyAdded = z2;
                            }
                        }
                        f.performActivityCreated(f.mSavedFragmentState);
                        dispatchOnFragmentActivityCreated(f, f.mSavedFragmentState, false);
                        if (f.mView != null) {
                            f.restoreViewState(f.mSavedFragmentState);
                        }
                        f.mSavedFragmentState = null;
                    }
                    newState2 = newState3;
                    break;
                case 2:
                    if (newState2 > 2) {
                        f.mState = 3;
                    }
                case 3:
                    if (newState2 > 3) {
                        if (DEBUG) {
                            Log.m66v(TAG, "moveto STARTED: " + f);
                        }
                        f.performStart();
                        dispatchOnFragmentStarted(f, false);
                    }
                case 4:
                    if (newState2 > 4) {
                        if (DEBUG) {
                            Log.m66v(TAG, "moveto RESUMED: " + f);
                        }
                        f.performResume();
                        dispatchOnFragmentResumed(f, false);
                        f.mSavedFragmentState = null;
                        f.mSavedViewState = null;
                        break;
                    }
                    break;
            }
        } else if (f.mState > newState2) {
            switch (f.mState) {
                case 1:
                    if (newState2 < 1) {
                        if (this.mDestroyed && f.getAnimatingAway() != null) {
                            Animator anim = f.getAnimatingAway();
                            f.setAnimatingAway(null);
                            anim.cancel();
                        }
                        Animator anim2 = f.getAnimatingAway();
                        if (anim2 != null) {
                            f.setStateAfterAnimating(newState2);
                            newState2 = 1;
                            break;
                        } else {
                            if (DEBUG) {
                                Log.m66v(TAG, "movefrom CREATED: " + f);
                            }
                            if (!f.mRetaining) {
                                f.performDestroy();
                                dispatchOnFragmentDestroyed(f, false);
                            } else {
                                f.mState = 0;
                            }
                            f.performDetach();
                            dispatchOnFragmentDetached(f, false);
                            if (!keepActive) {
                                if (!f.mRetaining) {
                                    makeInactive(f);
                                    break;
                                } else {
                                    f.mHost = null;
                                    f.mParentFragment = null;
                                    f.mFragmentManager = null;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 2:
                case 3:
                    if (newState2 < 2) {
                        if (DEBUG) {
                            Log.m66v(TAG, "movefrom ACTIVITY_CREATED: " + f);
                        }
                        if (f.mView != null && this.mHost.onShouldSaveFragmentState(f) && f.mSavedViewState == null) {
                            saveFragmentViewState(f);
                        }
                        f.performDestroyView();
                        dispatchOnFragmentViewDestroyed(f, false);
                        if (f.mView != null && f.mContainer != null) {
                            if (getTargetSdk() >= 26) {
                                f.mView.clearAnimation();
                                f.mContainer.endViewTransition(f.mView);
                            }
                            Animator anim3 = null;
                            if (this.mCurState > 0 && !this.mDestroyed && f.mView.getVisibility() == 0 && f.mView.getTransitionAlpha() > 0.0f) {
                                anim3 = loadAnimator(f, transit, false, transitionStyle);
                            }
                            Animator anim4 = anim3;
                            f.mView.setTransitionAlpha(1.0f);
                            if (anim4 != null) {
                                final ViewGroup container3 = f.mContainer;
                                final View view = f.mView;
                                container3.startViewTransition(view);
                                f.setAnimatingAway(anim4);
                                f.setStateAfterAnimating(newState2);
                                anim4.addListener(new AnimatorListenerAdapter() { // from class: android.app.FragmentManagerImpl.2
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator anim5) {
                                        container3.endViewTransition(view);
                                        Animator animator = f.getAnimatingAway();
                                        f.setAnimatingAway(null);
                                        if (container3.indexOfChild(view) == -1 && animator != null) {
                                            FragmentManagerImpl.this.moveToState(f, f.getStateAfterAnimating(), 0, 0, false);
                                        }
                                    }
                                });
                                anim4.setTarget(f.mView);
                                setHWLayerAnimListenerIfAlpha(f.mView, anim4);
                                anim4.start();
                            }
                            f.mContainer.removeView(f.mView);
                        }
                        f.mContainer = null;
                        f.mView = null;
                        f.mInLayout = false;
                        if (newState2 < 1) {
                        }
                    }
                    if (newState2 < 1) {
                    }
                    break;
                case 4:
                    if (newState2 < 4) {
                        if (DEBUG) {
                            Log.m66v(TAG, "movefrom STARTED: " + f);
                        }
                        f.performStop();
                        dispatchOnFragmentStopped(f, false);
                    }
                    if (newState2 < 2) {
                    }
                    if (newState2 < 1) {
                    }
                    break;
                case 5:
                    if (newState2 < 5) {
                        if (DEBUG) {
                            Log.m66v(TAG, "movefrom RESUMED: " + f);
                        }
                        f.performPause();
                        dispatchOnFragmentPaused(f, false);
                    }
                    if (newState2 < 4) {
                    }
                    if (newState2 < 2) {
                    }
                    if (newState2 < 1) {
                    }
                    break;
            }
            if (f.mState == newState2) {
                Log.m64w(TAG, "moveToState: Fragment state for " + f + " not updated inline; expected state " + newState2 + " found " + f.mState);
                f.mState = newState2;
                return;
            }
            return;
        }
        if (f.mState == newState2) {
        }
    }

    void moveToState(Fragment f) {
        moveToState(f, this.mCurState, 0, 0, false);
    }

    void ensureInflatedFragmentView(Fragment f) {
        if (f.mFromLayout && !f.mPerformedCreateView) {
            f.mView = f.performCreateView(f.performGetLayoutInflater(f.mSavedFragmentState), null, f.mSavedFragmentState);
            if (f.mView != null) {
                f.mView.setSaveFromParentEnabled(false);
                if (f.mHidden) {
                    f.mView.setVisibility(8);
                }
                f.onViewCreated(f.mView, f.mSavedFragmentState);
                dispatchOnFragmentViewCreated(f, f.mView, f.mSavedFragmentState, false);
            }
        }
    }

    void completeShowHideFragment(Fragment fragment) {
        int visibility;
        if (fragment.mView != null) {
            Animator anim = loadAnimator(fragment, fragment.getNextTransition(), !fragment.mHidden, fragment.getNextTransitionStyle());
            if (anim != null) {
                anim.setTarget(fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        final ViewGroup container = fragment.mContainer;
                        final View animatingView = fragment.mView;
                        if (container != null) {
                            container.startViewTransition(animatingView);
                        }
                        anim.addListener(new AnimatorListenerAdapter() { // from class: android.app.FragmentManagerImpl.3
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                if (container != null) {
                                    container.endViewTransition(animatingView);
                                }
                                animation.removeListener(this);
                                animatingView.setVisibility(8);
                            }
                        });
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                setHWLayerAnimListenerIfAlpha(fragment.mView, anim);
                anim.start();
            } else {
                if (fragment.mHidden && !fragment.isHideReplaced()) {
                    visibility = 8;
                } else {
                    visibility = 0;
                }
                fragment.mView.setVisibility(visibility);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    void moveFragmentToExpectedState(Fragment f) {
        if (f == null) {
            return;
        }
        int nextState = this.mCurState;
        if (f.mRemoving) {
            if (f.isInBackStack()) {
                nextState = Math.min(nextState, 1);
            } else {
                nextState = Math.min(nextState, 0);
            }
        }
        moveToState(f, nextState, f.getNextTransition(), f.getNextTransitionStyle(), false);
        if (f.mView != null) {
            Fragment underFragment = findFragmentUnder(f);
            if (underFragment != null) {
                View underView = underFragment.mView;
                ViewGroup container = f.mContainer;
                int underIndex = container.indexOfChild(underView);
                int viewIndex = container.indexOfChild(f.mView);
                if (viewIndex < underIndex) {
                    container.removeViewAt(viewIndex);
                    container.addView(f.mView, underIndex);
                }
            }
            if (f.mIsNewlyAdded && f.mContainer != null) {
                f.mView.setTransitionAlpha(1.0f);
                f.mIsNewlyAdded = false;
                Animator anim = loadAnimator(f, f.getNextTransition(), true, f.getNextTransitionStyle());
                if (anim != null) {
                    anim.setTarget(f.mView);
                    setHWLayerAnimListenerIfAlpha(f.mView, anim);
                    anim.start();
                }
            }
        }
        if (f.mHiddenChanged) {
            completeShowHideFragment(f);
        }
    }

    void moveToState(int newState, boolean always) {
        if (this.mHost == null && newState != 0) {
            throw new IllegalStateException("No activity");
        }
        if (!always && this.mCurState == newState) {
            return;
        }
        this.mCurState = newState;
        if (this.mActive != null) {
            int numAdded = this.mAdded.size();
            boolean loadersRunning = false;
            for (int i = 0; i < numAdded; i++) {
                Fragment f = this.mAdded.get(i);
                moveFragmentToExpectedState(f);
                if (f.mLoaderManager != null) {
                    loadersRunning |= f.mLoaderManager.hasRunningLoaders();
                }
            }
            int numActive = this.mActive.size();
            boolean loadersRunning2 = loadersRunning;
            for (int i2 = 0; i2 < numActive; i2++) {
                Fragment f2 = this.mActive.valueAt(i2);
                if (f2 != null && ((f2.mRemoving || f2.mDetached) && !f2.mIsNewlyAdded)) {
                    moveFragmentToExpectedState(f2);
                    if (f2.mLoaderManager != null) {
                        loadersRunning2 |= f2.mLoaderManager.hasRunningLoaders();
                    }
                }
            }
            if (!loadersRunning2) {
                startPendingDeferredFragments();
            }
            if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                this.mHost.onInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    void startPendingDeferredFragments() {
        if (this.mActive == null) {
            return;
        }
        for (int i = 0; i < this.mActive.size(); i++) {
            Fragment f = this.mActive.valueAt(i);
            if (f != null) {
                performPendingDeferredStart(f);
            }
        }
    }

    void makeActive(Fragment f) {
        if (f.mIndex >= 0) {
            return;
        }
        int i = this.mNextFragmentIndex;
        this.mNextFragmentIndex = i + 1;
        f.setIndex(i, this.mParent);
        if (this.mActive == null) {
            this.mActive = new SparseArray<>();
        }
        this.mActive.put(f.mIndex, f);
        if (DEBUG) {
            Log.m66v(TAG, "Allocated fragment index " + f);
        }
    }

    void makeInactive(Fragment f) {
        if (f.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            Log.m66v(TAG, "Freeing fragment index " + f);
        }
        this.mActive.put(f.mIndex, null);
        this.mHost.inactivateFragment(f.mWho);
        f.initState();
    }

    public void addFragment(Fragment fragment, boolean moveToStateNow) {
        if (DEBUG) {
            Log.m66v(TAG, "add: " + fragment);
        }
        makeActive(fragment);
        if (!fragment.mDetached) {
            if (this.mAdded.contains(fragment)) {
                throw new IllegalStateException("Fragment already added: " + fragment);
            }
            synchronized (this.mAdded) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (moveToStateNow) {
                moveToState(fragment);
            }
        }
    }

    public void removeFragment(Fragment fragment) {
        if (DEBUG) {
            Log.m66v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean inactive = !fragment.isInBackStack();
        if (!fragment.mDetached || inactive) {
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
        }
    }

    public void hideFragment(Fragment fragment) {
        if (DEBUG) {
            Log.m66v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        }
    }

    public void showFragment(Fragment fragment) {
        if (DEBUG) {
            Log.m66v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    public void detachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.m66v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (DEBUG) {
                    Log.m66v(TAG, "remove from detach: " + fragment);
                }
                synchronized (this.mAdded) {
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
            }
        }
    }

    public void attachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.m66v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    Log.m66v(TAG, "add from attach: " + fragment);
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(fragment);
                }
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    @Override // android.app.FragmentManager
    public Fragment findFragmentById(int id) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = this.mAdded.get(i);
            if (f != null && f.mFragmentId == id) {
                return f;
            }
        }
        if (this.mActive != null) {
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment f2 = this.mActive.valueAt(i2);
                if (f2 != null && f2.mFragmentId == id) {
                    return f2;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.app.FragmentManager
    public Fragment findFragmentByTag(String tag) {
        if (tag != null) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment f = this.mAdded.get(i);
                if (f != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
        }
        if (this.mActive != null && tag != null) {
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment f2 = this.mActive.valueAt(i2);
                if (f2 != null && tag.equals(f2.mTag)) {
                    return f2;
                }
            }
            return null;
        }
        return null;
    }

    public Fragment findFragmentByWho(String who) {
        Fragment f;
        if (this.mActive != null && who != null) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                Fragment f2 = this.mActive.valueAt(i);
                if (f2 != null && (f = f2.findFragmentByWho(who)) != null) {
                    return f;
                }
            }
            return null;
        }
        return null;
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    @Override // android.app.FragmentManager
    public boolean isStateSaved() {
        return this.mStateSaved;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0027, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void enqueueAction(OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss) {
            checkStateLoss();
        }
        synchronized (this) {
            if (!this.mDestroyed && this.mHost != null) {
                if (this.mPendingActions == null) {
                    this.mPendingActions = new ArrayList<>();
                }
                this.mPendingActions.add(action);
                scheduleCommit();
                return;
            }
            throw new IllegalStateException("Activity has been destroyed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleCommit() {
        synchronized (this) {
            boolean pendingReady = false;
            boolean postponeReady = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true;
            if (this.mPendingActions != null && this.mPendingActions.size() == 1) {
                pendingReady = true;
            }
            if (postponeReady || pendingReady) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(BackStackRecord bse) {
        synchronized (this) {
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                int index = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1).intValue();
                if (DEBUG) {
                    Log.m66v(TAG, "Adding back stack index " + index + " with " + bse);
                }
                this.mBackStackIndices.set(index, bse);
                return index;
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int index2 = this.mBackStackIndices.size();
            if (DEBUG) {
                Log.m66v(TAG, "Setting back stack index " + index2 + " to " + bse);
            }
            this.mBackStackIndices.add(bse);
            return index2;
        }
    }

    public void setBackStackIndex(int index, BackStackRecord bse) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int N = this.mBackStackIndices.size();
            if (index < N) {
                if (DEBUG) {
                    Log.m66v(TAG, "Setting back stack index " + index + " to " + bse);
                }
                this.mBackStackIndices.set(index, bse);
            } else {
                while (N < index) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.m66v(TAG, "Adding available back stack index " + N);
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(N));
                    N++;
                }
                if (DEBUG) {
                    Log.m66v(TAG, "Adding back stack index " + index + " with " + bse);
                }
                this.mBackStackIndices.add(bse);
            }
        }
    }

    public void freeBackStackIndex(int index) {
        synchronized (this) {
            this.mBackStackIndices.set(index, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList<>();
            }
            if (DEBUG) {
                Log.m66v(TAG, "Freeing back stack index " + index);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(index));
        }
    }

    private void ensureExecReady(boolean allowStateLoss) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!allowStateLoss) {
            checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<>();
            this.mTmpIsPop = new ArrayList<>();
        }
        this.mExecutingActions = true;
        try {
            executePostponedTransaction(null, null);
        } finally {
            this.mExecutingActions = false;
        }
    }

    public void execSingleAction(OpGenerator action, boolean allowStateLoss) {
        if (allowStateLoss && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(allowStateLoss);
        if (action.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    public boolean execPendingActions() {
        ensureExecReady(true);
        boolean didSomething = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                didSomething = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        doPendingDeferredStart();
        burpActive();
        return didSomething;
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
        int index;
        int index2;
        int numPostponed = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size();
        int numPostponed2 = numPostponed;
        int numPostponed3 = 0;
        while (numPostponed3 < numPostponed2) {
            StartEnterTransitionListener listener = this.mPostponedTransactions.get(numPostponed3);
            if (records != null && !listener.mIsBack && (index2 = records.indexOf(listener.mRecord)) != -1 && isRecordPop.get(index2).booleanValue()) {
                listener.cancelTransaction();
            } else if (listener.isReady() || (records != null && listener.mRecord.interactsWith(records, 0, records.size()))) {
                this.mPostponedTransactions.remove(numPostponed3);
                numPostponed3--;
                numPostponed2--;
                if (records != null && !listener.mIsBack && (index = records.indexOf(listener.mRecord)) != -1 && isRecordPop.get(index).booleanValue()) {
                    listener.cancelTransaction();
                } else {
                    listener.completeTransaction();
                }
            }
            numPostponed3++;
        }
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
        if (records == null || records.isEmpty()) {
            return;
        }
        if (isRecordPop == null || records.size() != isRecordPop.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        executePostponedTransaction(records, isRecordPop);
        int numRecords = records.size();
        int startIndex = 0;
        int recordNum = 0;
        while (recordNum < numRecords) {
            boolean canReorder = records.get(recordNum).mReorderingAllowed;
            if (!canReorder) {
                if (startIndex != recordNum) {
                    executeOpsTogether(records, isRecordPop, startIndex, recordNum);
                }
                int reorderingEnd = recordNum + 1;
                if (isRecordPop.get(recordNum).booleanValue()) {
                    while (reorderingEnd < numRecords && isRecordPop.get(reorderingEnd).booleanValue() && !records.get(reorderingEnd).mReorderingAllowed) {
                        reorderingEnd++;
                    }
                }
                executeOpsTogether(records, isRecordPop, recordNum, reorderingEnd);
                startIndex = reorderingEnd;
                recordNum = reorderingEnd - 1;
            }
            recordNum++;
        }
        if (startIndex != numRecords) {
            executeOpsTogether(records, isRecordPop, startIndex, numRecords);
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        boolean allowReordering = records.get(startIndex).mReorderingAllowed;
        if (this.mTmpAddedFragments == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            this.mTmpAddedFragments.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        Fragment oldPrimaryNav = getPrimaryNavigationFragment();
        boolean addToBackStack = false;
        Fragment oldPrimaryNav2 = oldPrimaryNav;
        int recordNum = startIndex;
        while (true) {
            boolean z = true;
            if (recordNum >= endIndex) {
                break;
            }
            BackStackRecord record = records.get(recordNum);
            boolean isPop = isRecordPop.get(recordNum).booleanValue();
            if (!isPop) {
                oldPrimaryNav2 = record.expandOps(this.mTmpAddedFragments, oldPrimaryNav2);
            } else {
                record.trackAddedFragmentsInPop(this.mTmpAddedFragments);
            }
            if (!addToBackStack && !record.mAddToBackStack) {
                z = false;
            }
            addToBackStack = z;
            recordNum++;
        }
        this.mTmpAddedFragments.clear();
        if (!allowReordering) {
            FragmentTransition.startTransitions(this, records, isRecordPop, startIndex, endIndex, false);
        }
        executeOps(records, isRecordPop, startIndex, endIndex);
        int postponeIndex = endIndex;
        if (allowReordering) {
            ArraySet<Fragment> addedFragments = new ArraySet<>();
            addAddedFragments(addedFragments);
            int postponeIndex2 = postponePostponableTransactions(records, isRecordPop, startIndex, endIndex, addedFragments);
            makeRemovedFragmentsInvisible(addedFragments);
            postponeIndex = postponeIndex2;
        }
        if (postponeIndex != startIndex && allowReordering) {
            FragmentTransition.startTransitions(this, records, isRecordPop, startIndex, postponeIndex, true);
            moveToState(this.mCurState, true);
        }
        for (int recordNum2 = startIndex; recordNum2 < endIndex; recordNum2++) {
            BackStackRecord record2 = records.get(recordNum2);
            boolean isPop2 = isRecordPop.get(recordNum2).booleanValue();
            if (isPop2 && record2.mIndex >= 0) {
                freeBackStackIndex(record2.mIndex);
                record2.mIndex = -1;
            }
            record2.runOnCommitRunnables();
        }
        if (addToBackStack) {
            reportBackStackChanged();
        }
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> fragments) {
        int numAdded = fragments.size();
        for (int i = 0; i < numAdded; i++) {
            Fragment fragment = fragments.valueAt(i);
            if (!fragment.mAdded) {
                View view = fragment.getView();
                view.setTransitionAlpha(0.0f);
            }
        }
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, ArraySet<Fragment> added) {
        int postponeIndex = endIndex;
        for (int i = endIndex - 1; i >= startIndex; i--) {
            BackStackRecord record = records.get(i);
            boolean isPop = isRecordPop.get(i).booleanValue();
            boolean isPostponed = record.isPostponed() && !record.interactsWith(records, i + 1, endIndex);
            if (isPostponed) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList<>();
                }
                StartEnterTransitionListener listener = new StartEnterTransitionListener(record, isPop);
                this.mPostponedTransactions.add(listener);
                record.setOnStartPostponedListener(listener);
                if (isPop) {
                    record.executeOps();
                } else {
                    record.executePopOps(false);
                }
                postponeIndex--;
                if (i != postponeIndex) {
                    records.remove(i);
                    records.add(postponeIndex, record);
                }
                addAddedFragments(added);
            }
        }
        return postponeIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeExecute(BackStackRecord record, boolean isPop, boolean runTransitions, boolean moveToState) {
        if (isPop) {
            record.executePopOps(moveToState);
        } else {
            record.executeOps();
        }
        ArrayList<BackStackRecord> records = new ArrayList<>(1);
        ArrayList<Boolean> isRecordPop = new ArrayList<>(1);
        records.add(record);
        isRecordPop.add(Boolean.valueOf(isPop));
        if (runTransitions) {
            FragmentTransition.startTransitions(this, records, isRecordPop, 0, 1, true);
        }
        if (moveToState) {
            moveToState(this.mCurState, true);
        }
        if (this.mActive != null) {
            int numActive = this.mActive.size();
            for (int i = 0; i < numActive; i++) {
                Fragment fragment = this.mActive.valueAt(i);
                if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && record.interactsWith(fragment.mContainerId)) {
                    fragment.mIsNewlyAdded = false;
                }
            }
        }
    }

    private Fragment findFragmentUnder(Fragment f) {
        ViewGroup container = f.mContainer;
        View view = f.mView;
        if (container == null || view == null) {
            return null;
        }
        int fragmentIndex = this.mAdded.indexOf(f);
        for (int i = fragmentIndex - 1; i >= 0; i--) {
            Fragment underFragment = this.mAdded.get(i);
            if (underFragment.mContainer == container && underFragment.mView != null) {
                return underFragment;
            }
        }
        return null;
    }

    private static void executeOps(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        int i = startIndex;
        while (i < endIndex) {
            BackStackRecord record = records.get(i);
            boolean isPop = isRecordPop.get(i).booleanValue();
            if (isPop) {
                record.bumpBackStackNesting(-1);
                boolean moveToState = i == endIndex + (-1);
                record.executePopOps(moveToState);
            } else {
                record.bumpBackStackNesting(1);
                record.executeOps();
            }
            i++;
        }
    }

    private void addAddedFragments(ArraySet<Fragment> added) {
        if (this.mCurState < 1) {
            return;
        }
        int state = Math.min(this.mCurState, 4);
        int numAdded = this.mAdded.size();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < numAdded) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment.mState < state) {
                    moveToState(fragment, state, fragment.getNextAnim(), fragment.getNextTransition(), false);
                    if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                        added.add(fragment);
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        int numFragments = this.mActive == null ? 0 : this.mActive.size();
        for (int i = 0; i < numFragments; i++) {
            Fragment fragment = this.mActive.valueAt(i);
            if (fragment != null && fragment.getAnimatingAway() != null) {
                fragment.getAnimatingAway().end();
            }
        }
    }

    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> records, ArrayList<Boolean> isPop) {
        boolean didSomething = false;
        synchronized (this) {
            if (this.mPendingActions != null && this.mPendingActions.size() != 0) {
                int numActions = this.mPendingActions.size();
                for (int i = 0; i < numActions; i++) {
                    didSomething |= this.mPendingActions.get(i).generateOps(records, isPop);
                }
                this.mPendingActions.clear();
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                return didSomething;
            }
            return false;
        }
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean loadersRunning = false;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = this.mActive.valueAt(i);
                if (f != null && f.mLoaderManager != null) {
                    loadersRunning |= f.mLoaderManager.hasRunningLoaders();
                }
            }
            if (!loadersRunning) {
                this.mHavePendingDeferredStart = false;
                startPendingDeferredFragments();
            }
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    void addBackStackState(BackStackRecord state) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(state);
    }

    boolean popBackStackState(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, String name, int id, int flags) {
        if (this.mBackStack == null) {
            return false;
        }
        if (name == null && id < 0 && (flags & 1) == 0) {
            int last = this.mBackStack.size() - 1;
            if (last < 0) {
                return false;
            }
            records.add(this.mBackStack.remove(last));
            isRecordPop.add(true);
        } else {
            int index = -1;
            if (name != null || id >= 0) {
                index = this.mBackStack.size() - 1;
                while (index >= 0) {
                    BackStackRecord bss = this.mBackStack.get(index);
                    if ((name != null && name.equals(bss.getName())) || (id >= 0 && id == bss.mIndex)) {
                        break;
                    }
                    index--;
                }
                if (index < 0) {
                    return false;
                }
                if ((flags & 1) != 0) {
                    index--;
                    while (index >= 0) {
                        BackStackRecord bss2 = this.mBackStack.get(index);
                        if ((name == null || !name.equals(bss2.getName())) && (id < 0 || id != bss2.mIndex)) {
                            break;
                        }
                        index--;
                    }
                }
            }
            if (index == this.mBackStack.size() - 1) {
                return false;
            }
            for (int i = this.mBackStack.size() - 1; i > index; i--) {
                records.add(this.mBackStack.remove(i));
                isRecordPop.add(true);
            }
        }
        return true;
    }

    FragmentManagerNonConfig retainNonConfig() {
        setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    private static void setRetaining(FragmentManagerNonConfig nonConfig) {
        if (nonConfig == null) {
            return;
        }
        List<Fragment> fragments = nonConfig.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.mRetaining = true;
            }
        }
        List<FragmentManagerNonConfig> children = nonConfig.getChildNonConfigs();
        if (children != null) {
            for (FragmentManagerNonConfig child : children) {
                setRetaining(child);
            }
        }
    }

    void saveNonConfig() {
        FragmentManagerNonConfig child;
        ArrayList<Fragment> fragments = null;
        ArrayList<FragmentManagerNonConfig> fragments2 = null;
        if (this.mActive != null) {
            ArrayList<FragmentManagerNonConfig> childFragments = null;
            ArrayList<Fragment> fragments3 = null;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = this.mActive.valueAt(i);
                if (f != null) {
                    if (f.mRetainInstance) {
                        if (fragments3 == null) {
                            fragments3 = new ArrayList<>();
                        }
                        fragments3.add(f);
                        f.mTargetIndex = f.mTarget != null ? f.mTarget.mIndex : -1;
                        if (DEBUG) {
                            Log.m66v(TAG, "retainNonConfig: keeping retained " + f);
                        }
                    }
                    if (f.mChildFragmentManager != null) {
                        f.mChildFragmentManager.saveNonConfig();
                        child = f.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        child = f.mChildNonConfig;
                    }
                    if (childFragments == null && child != null) {
                        childFragments = new ArrayList<>(this.mActive.size());
                        for (int j = 0; j < i; j++) {
                            childFragments.add(null);
                        }
                    }
                    if (childFragments != null) {
                        childFragments.add(child);
                    }
                }
            }
            fragments = fragments3;
            fragments2 = childFragments;
        }
        if (fragments == null && fragments2 == null) {
            this.mSavedNonConfig = null;
        } else {
            this.mSavedNonConfig = new FragmentManagerNonConfig(fragments, fragments2);
        }
    }

    void saveFragmentViewState(Fragment f) {
        if (f.mView == null) {
            return;
        }
        if (this.mStateArray == null) {
            this.mStateArray = new SparseArray<>();
        } else {
            this.mStateArray.clear();
        }
        f.mView.saveHierarchyState(this.mStateArray);
        if (this.mStateArray.size() > 0) {
            f.mSavedViewState = this.mStateArray;
            this.mStateArray = null;
        }
    }

    Bundle saveFragmentBasicState(Fragment f) {
        Bundle result = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        f.performSaveInstanceState(this.mStateBundle);
        dispatchOnFragmentSaveInstanceState(f, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            result = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (f.mView != null) {
            saveFragmentViewState(f);
        }
        if (f.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, f.mSavedViewState);
        }
        if (!f.mUserVisibleHint) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBoolean(USER_VISIBLE_HINT_TAG, f.mUserVisibleHint);
        }
        return result;
    }

    Parcelable saveAllState() {
        int N;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions();
        this.mStateSaved = true;
        this.mSavedNonConfig = null;
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null;
        }
        int N2 = this.mActive.size();
        FragmentState[] active = new FragmentState[N2];
        boolean haveFragments = false;
        for (int i = 0; i < N2; i++) {
            Fragment f = this.mActive.valueAt(i);
            if (f != null) {
                if (f.mIndex < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + f + " has cleared index: " + f.mIndex));
                }
                haveFragments = true;
                FragmentState fs = new FragmentState(f);
                active[i] = fs;
                if (f.mState > 0 && fs.mSavedFragmentState == null) {
                    fs.mSavedFragmentState = saveFragmentBasicState(f);
                    if (f.mTarget != null) {
                        if (f.mTarget.mIndex < 0) {
                            throwException(new IllegalStateException("Failure saving state: " + f + " has target not in fragment manager: " + f.mTarget));
                        }
                        if (fs.mSavedFragmentState == null) {
                            fs.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fs.mSavedFragmentState, TARGET_STATE_TAG, f.mTarget);
                        if (f.mTargetRequestCode != 0) {
                            fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, f.mTargetRequestCode);
                        }
                    }
                } else {
                    fs.mSavedFragmentState = f.mSavedFragmentState;
                }
                if (DEBUG) {
                    Log.m66v(TAG, "Saved state of " + f + PluralRules.KEYWORD_RULE_SEPARATOR + fs.mSavedFragmentState);
                }
            }
        }
        if (!haveFragments) {
            if (DEBUG) {
                Log.m66v(TAG, "saveAllState: no fragments!");
            }
            return null;
        }
        int[] added = null;
        BackStackState[] backStack = null;
        int N3 = this.mAdded.size();
        if (N3 > 0) {
            added = new int[N3];
            for (int i2 = 0; i2 < N3; i2++) {
                added[i2] = this.mAdded.get(i2).mIndex;
                if (added[i2] < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + added[i2]));
                }
                if (DEBUG) {
                    Log.m66v(TAG, "saveAllState: adding fragment #" + i2 + PluralRules.KEYWORD_RULE_SEPARATOR + this.mAdded.get(i2));
                }
            }
        }
        if (this.mBackStack != null && (N = this.mBackStack.size()) > 0) {
            backStack = new BackStackState[N];
            for (int i3 = 0; i3 < N; i3++) {
                backStack[i3] = new BackStackState(this, this.mBackStack.get(i3));
                if (DEBUG) {
                    Log.m66v(TAG, "saveAllState: adding back stack #" + i3 + PluralRules.KEYWORD_RULE_SEPARATOR + this.mBackStack.get(i3));
                }
            }
        }
        FragmentManagerState fms = new FragmentManagerState();
        fms.mActive = active;
        fms.mAdded = added;
        fms.mBackStack = backStack;
        fms.mNextFragmentIndex = this.mNextFragmentIndex;
        if (this.mPrimaryNav != null) {
            fms.mPrimaryNavActiveIndex = this.mPrimaryNav.mIndex;
        }
        saveNonConfig();
        return fms;
    }

    void restoreAllState(Parcelable state, FragmentManagerNonConfig nonConfig) {
        if (state == null) {
            return;
        }
        FragmentManagerState fms = (FragmentManagerState) state;
        if (fms.mActive == null) {
            return;
        }
        List<FragmentManagerNonConfig> childNonConfigs = null;
        if (nonConfig != null) {
            List<Fragment> nonConfigFragments = nonConfig.getFragments();
            childNonConfigs = nonConfig.getChildNonConfigs();
            int count = nonConfigFragments != null ? nonConfigFragments.size() : 0;
            for (int i = 0; i < count; i++) {
                Fragment f = nonConfigFragments.get(i);
                if (DEBUG) {
                    Log.m66v(TAG, "restoreAllState: re-attaching retained " + f);
                }
                int index = 0;
                while (index < fms.mActive.length && fms.mActive[index].mIndex != f.mIndex) {
                    index++;
                }
                if (index == fms.mActive.length) {
                    throwException(new IllegalStateException("Could not find active fragment with index " + f.mIndex));
                }
                FragmentState fs = fms.mActive[index];
                fs.mInstance = f;
                f.mSavedViewState = null;
                f.mBackStackNesting = 0;
                f.mInLayout = false;
                f.mAdded = false;
                f.mTarget = null;
                if (fs.mSavedFragmentState != null) {
                    fs.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                    f.mSavedViewState = fs.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                    f.mSavedFragmentState = fs.mSavedFragmentState;
                }
            }
        }
        this.mActive = new SparseArray<>(fms.mActive.length);
        for (int i2 = 0; i2 < fms.mActive.length; i2++) {
            FragmentState fs2 = fms.mActive[i2];
            if (fs2 != null) {
                FragmentManagerNonConfig childNonConfig = null;
                if (childNonConfigs != null && i2 < childNonConfigs.size()) {
                    childNonConfig = childNonConfigs.get(i2);
                }
                Fragment f2 = fs2.instantiate(this.mHost, this.mContainer, this.mParent, childNonConfig);
                if (DEBUG) {
                    Log.m66v(TAG, "restoreAllState: active #" + i2 + PluralRules.KEYWORD_RULE_SEPARATOR + f2);
                }
                this.mActive.put(f2.mIndex, f2);
                fs2.mInstance = null;
            }
        }
        if (nonConfig != null) {
            List<Fragment> nonConfigFragments2 = nonConfig.getFragments();
            int count2 = nonConfigFragments2 != null ? nonConfigFragments2.size() : 0;
            for (int i3 = 0; i3 < count2; i3++) {
                Fragment f3 = nonConfigFragments2.get(i3);
                if (f3.mTargetIndex >= 0) {
                    f3.mTarget = this.mActive.get(f3.mTargetIndex);
                    if (f3.mTarget == null) {
                        Log.m64w(TAG, "Re-attaching retained fragment " + f3 + " target no longer exists: " + f3.mTargetIndex);
                        f3.mTarget = null;
                    }
                }
            }
        }
        this.mAdded.clear();
        if (fms.mAdded != null) {
            for (int i4 = 0; i4 < fms.mAdded.length; i4++) {
                Fragment f4 = this.mActive.get(fms.mAdded[i4]);
                if (f4 == null) {
                    throwException(new IllegalStateException("No instantiated fragment for index #" + fms.mAdded[i4]));
                }
                f4.mAdded = true;
                if (DEBUG) {
                    Log.m66v(TAG, "restoreAllState: added #" + i4 + PluralRules.KEYWORD_RULE_SEPARATOR + f4);
                }
                if (this.mAdded.contains(f4)) {
                    throw new IllegalStateException("Already added!");
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(f4);
                }
            }
        }
        if (fms.mBackStack != null) {
            this.mBackStack = new ArrayList<>(fms.mBackStack.length);
            for (int i5 = 0; i5 < fms.mBackStack.length; i5++) {
                BackStackRecord bse = fms.mBackStack[i5].instantiate(this);
                if (DEBUG) {
                    Log.m66v(TAG, "restoreAllState: back stack #" + i5 + " (index " + bse.mIndex + "): " + bse);
                    LogWriter logw = new LogWriter(2, TAG);
                    PrintWriter pw = new FastPrintWriter((Writer) logw, false, 1024);
                    bse.dump("  ", pw, false);
                    pw.flush();
                }
                this.mBackStack.add(bse);
                if (bse.mIndex >= 0) {
                    setBackStackIndex(bse.mIndex, bse);
                }
            }
        } else {
            this.mBackStack = null;
        }
        if (fms.mPrimaryNavActiveIndex >= 0) {
            this.mPrimaryNav = this.mActive.get(fms.mPrimaryNavActiveIndex);
        }
        this.mNextFragmentIndex = fms.mNextFragmentIndex;
    }

    private void burpActive() {
        if (this.mActive != null) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                if (this.mActive.valueAt(i) == null) {
                    this.mActive.delete(this.mActive.keyAt(i));
                }
            }
        }
    }

    public void attachController(FragmentHostCallback<?> host, FragmentContainer container, Fragment parent) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = host;
        this.mContainer = container;
        this.mParent = parent;
        this.mAllowOldReentrantBehavior = getTargetSdk() <= 25;
    }

    int getTargetSdk() {
        Context context;
        ApplicationInfo info;
        if (this.mHost != null && (context = this.mHost.getContext()) != null && (info = context.getApplicationInfo()) != null) {
            return info.targetSdkVersion;
        }
        return 0;
    }

    @UnsupportedAppUsage
    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        int addedCount = this.mAdded.size();
        for (int i = 0; i < addedCount; i++) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment != null) {
                fragment.noteStateNotSaved();
            }
        }
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        dispatchMoveToState(1);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        dispatchMoveToState(2);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        dispatchMoveToState(4);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        dispatchMoveToState(5);
    }

    public void dispatchPause() {
        dispatchMoveToState(4);
    }

    public void dispatchStop() {
        dispatchMoveToState(3);
    }

    public void dispatchDestroyView() {
        dispatchMoveToState(1);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        dispatchMoveToState(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    private void dispatchMoveToState(int state) {
        if (this.mAllowOldReentrantBehavior) {
            moveToState(state, false);
        } else {
            try {
                this.mExecutingActions = true;
                moveToState(state, false);
            } finally {
                this.mExecutingActions = false;
            }
        }
        execPendingActions();
    }

    @Deprecated
    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performMultiWindowModeChanged(isInMultiWindowMode);
            }
        }
    }

    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performMultiWindowModeChanged(isInMultiWindowMode, newConfig);
            }
        }
    }

    @Deprecated
    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performPictureInPictureModeChanged(isInPictureInPictureMode);
            }
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
            }
        }
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performConfigurationChanged(newConfig);
            }
        }
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performLowMemory();
            }
        }
    }

    public void dispatchTrimMemory(int level) {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performTrimMemory(level);
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int i = 0;
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList<Fragment> newMenus = null;
        boolean show = false;
        for (int i2 = 0; i2 < this.mAdded.size(); i2++) {
            Fragment f = this.mAdded.get(i2);
            if (f != null && f.performCreateOptionsMenu(menu, inflater)) {
                show = true;
                if (newMenus == null) {
                    newMenus = new ArrayList<>();
                }
                newMenus.add(f);
            }
        }
        if (this.mCreatedMenus != null) {
            while (true) {
                int i3 = i;
                if (i3 >= this.mCreatedMenus.size()) {
                    break;
                }
                Fragment f2 = this.mCreatedMenus.get(i3);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
                i = i3 + 1;
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean show = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null && f.performPrepareOptionsMenu(menu)) {
                show = true;
            }
        }
        return show;
    }

    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null && f.performOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null && f.performContextItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = this.mAdded.get(i);
            if (f != null) {
                f.performOptionsMenuClosed(menu);
            }
        }
    }

    public void setPrimaryNavigationFragment(Fragment f) {
        if (f != null && (this.mActive.get(f.mIndex) != f || (f.mHost != null && f.getFragmentManager() != this))) {
            throw new IllegalArgumentException("Fragment " + f + " is not an active fragment of FragmentManager " + this);
        }
        this.mPrimaryNav = f;
    }

    @Override // android.app.FragmentManager
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    @Override // android.app.FragmentManager
    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks cb, boolean recursive) {
        this.mLifecycleCallbacks.add(new Pair<>(cb, Boolean.valueOf(recursive)));
    }

    @Override // android.app.FragmentManager
    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks cb) {
        synchronized (this.mLifecycleCallbacks) {
            int i = 0;
            int N = this.mLifecycleCallbacks.size();
            while (true) {
                if (i >= N) {
                    break;
                } else if (this.mLifecycleCallbacks.get(i).first != cb) {
                    i++;
                } else {
                    this.mLifecycleCallbacks.remove(i);
                    break;
                }
            }
        }
    }

    void dispatchOnFragmentPreAttached(Fragment f, Context context, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPreAttached(f, context, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentPreAttached(this, f, context);
            }
        }
    }

    void dispatchOnFragmentAttached(Fragment f, Context context, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentAttached(f, context, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentAttached(this, f, context);
            }
        }
    }

    void dispatchOnFragmentPreCreated(Fragment f, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPreCreated(f, savedInstanceState, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentPreCreated(this, f, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentCreated(Fragment f, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentCreated(f, savedInstanceState, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentCreated(this, f, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentActivityCreated(Fragment f, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentActivityCreated(f, savedInstanceState, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentActivityCreated(this, f, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentViewCreated(Fragment f, View v, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentViewCreated(f, v, savedInstanceState, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentViewCreated(this, f, v, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentStarted(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentStarted(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentStarted(this, f);
            }
        }
    }

    void dispatchOnFragmentResumed(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentResumed(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentResumed(this, f);
            }
        }
    }

    void dispatchOnFragmentPaused(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPaused(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentPaused(this, f);
            }
        }
    }

    void dispatchOnFragmentStopped(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentStopped(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentStopped(this, f);
            }
        }
    }

    void dispatchOnFragmentSaveInstanceState(Fragment f, Bundle outState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentSaveInstanceState(f, outState, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentSaveInstanceState(this, f, outState);
            }
        }
    }

    void dispatchOnFragmentViewDestroyed(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentViewDestroyed(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentViewDestroyed(this, f);
            }
        }
    }

    void dispatchOnFragmentDestroyed(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentDestroyed(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentDestroyed(this, f);
            }
        }
    }

    void dispatchOnFragmentDetached(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentDetached(f, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> p = it.next();
            if (!onlyRecursive || p.second.booleanValue()) {
                p.first.onFragmentDetached(this, f);
            }
        }
    }

    @Override // android.app.FragmentManager
    public void invalidateOptionsMenu() {
        if (this.mHost != null && this.mCurState == 5) {
            this.mHost.onInvalidateOptionsMenu();
        } else {
            this.mNeedMenuInvalidate = true;
        }
    }

    public static int reverseTransit(int transit) {
        if (transit != 4097) {
            if (transit != 4099) {
                if (transit != 8194) {
                    return 0;
                }
                return 4097;
            }
            return 4099;
        }
        return 8194;
    }

    public static int transitToStyleIndex(int transit, boolean enter) {
        int i;
        int i2;
        int i3;
        if (transit == 4097) {
            if (enter) {
                i = 0;
            } else {
                i = 1;
            }
            int animAttr = i;
            return animAttr;
        } else if (transit == 4099) {
            if (enter) {
                i2 = 4;
            } else {
                i2 = 5;
            }
            int animAttr2 = i2;
            return animAttr2;
        } else if (transit != 8194) {
            return -1;
        } else {
            if (enter) {
                i3 = 2;
            } else {
                i3 = 3;
            }
            int animAttr3 = i3;
            return animAttr3;
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Fragment fragment;
        Fragment fragment2;
        if ("fragment".equals(name)) {
            String fname = attrs.getAttributeValue(null, "class");
            TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.Fragment);
            if (fname == null) {
                fname = a.getString(0);
            }
            String fname2 = fname;
            int id = a.getResourceId(1, -1);
            String tag = a.getString(2);
            a.recycle();
            int containerId = parent != null ? parent.getId() : 0;
            if (containerId == -1 && id == -1 && tag == null) {
                throw new IllegalArgumentException(attrs.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname2);
            }
            Fragment fragment3 = id != -1 ? findFragmentById(id) : null;
            if (fragment3 == null && tag != null) {
                fragment3 = findFragmentByTag(tag);
            }
            if (fragment3 == null && containerId != -1) {
                fragment3 = findFragmentById(containerId);
            }
            if (DEBUG) {
                Log.m66v(TAG, "onCreateView: id=0x" + Integer.toHexString(id) + " fname=" + fname2 + " existing=" + fragment3);
            }
            if (fragment3 == null) {
                Fragment fragment4 = this.mContainer.instantiate(context, fname2, null);
                fragment4.mFromLayout = true;
                fragment4.mFragmentId = id != 0 ? id : containerId;
                fragment4.mContainerId = containerId;
                fragment4.mTag = tag;
                fragment4.mInLayout = true;
                fragment4.mFragmentManager = this;
                fragment4.mHost = this.mHost;
                fragment4.onInflate(this.mHost.getContext(), attrs, fragment4.mSavedFragmentState);
                addFragment(fragment4, true);
                fragment = fragment4;
            } else if (fragment3.mInLayout) {
                throw new IllegalArgumentException(attrs.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(id) + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId) + " with another fragment for " + fname2);
            } else {
                fragment3.mInLayout = true;
                fragment3.mHost = this.mHost;
                if (!fragment3.mRetaining) {
                    fragment3.onInflate(this.mHost.getContext(), attrs, fragment3.mSavedFragmentState);
                }
                fragment = fragment3;
            }
            if (this.mCurState < 1 && fragment.mFromLayout) {
                fragment2 = fragment;
                moveToState(fragment, 1, 0, 0, false);
            } else {
                fragment2 = fragment;
                moveToState(fragment2);
            }
            if (fragment2.mView == null) {
                throw new IllegalStateException("Fragment " + fname2 + " did not create a view.");
            }
            if (id != 0) {
                fragment2.mView.setId(id);
            }
            if (fragment2.mView.getTag() == null) {
                fragment2.mView.setTag(tag);
            }
            return fragment2.mView;
        }
        return null;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    /* compiled from: FragmentManager.java */
    /* loaded from: classes.dex */
    private class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        public PopBackStackState(String name, int id, int flags) {
            this.mName = name;
            this.mId = id;
            this.mFlags = flags;
        }

        @Override // android.app.FragmentManagerImpl.OpGenerator
        public boolean generateOps(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
            FragmentManager childManager;
            if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && (childManager = FragmentManagerImpl.this.mPrimaryNav.mChildFragmentManager) != null && childManager.popBackStackImmediate()) {
                return false;
            }
            return FragmentManagerImpl.this.popBackStackState(records, isRecordPop, this.mName, this.mId, this.mFlags);
        }
    }

    /* compiled from: FragmentManager.java */
    /* loaded from: classes.dex */
    static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final BackStackRecord mRecord;

        public StartEnterTransitionListener(BackStackRecord record, boolean isBack) {
            this.mIsBack = isBack;
            this.mRecord = record;
        }

        @Override // android.app.Fragment.OnStartEnterTransitionListener
        public void onStartEnterTransition() {
            this.mNumPostponed--;
            if (this.mNumPostponed == 0) {
                this.mRecord.mManager.scheduleCommit();
            }
        }

        @Override // android.app.Fragment.OnStartEnterTransitionListener
        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        public void completeTransaction() {
            boolean canceled = this.mNumPostponed > 0;
            FragmentManagerImpl manager = this.mRecord.mManager;
            int numAdded = manager.mAdded.size();
            for (int i = 0; i < numAdded; i++) {
                Fragment fragment = manager.mAdded.get(i);
                fragment.setOnStartEnterTransitionListener(null);
                if (canceled && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, canceled ? false : true, true);
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }
    }
}
