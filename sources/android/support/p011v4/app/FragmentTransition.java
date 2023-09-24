package android.support.p011v4.app;

import android.graphics.Rect;
import android.p007os.Build;
import android.support.annotation.RequiresApi;
import android.support.p011v4.app.BackStackRecord;
import android.support.p011v4.util.ArrayMap;
import android.support.p011v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: android.support.v4.app.FragmentTransition */
/* loaded from: classes3.dex */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    FragmentTransition() {
    }

    static void startTransitions(FragmentManagerImpl fragmentManager, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered) {
        if (fragmentManager.mCurState >= 1 && Build.VERSION.SDK_INT >= 21) {
            SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
            for (int i = startIndex; i < endIndex; i++) {
                BackStackRecord record = records.get(i);
                boolean isPop = isRecordPop.get(i).booleanValue();
                if (isPop) {
                    calculatePopFragments(record, transitioningFragments, isReordered);
                } else {
                    calculateFragments(record, transitioningFragments, isReordered);
                }
            }
            int i2 = transitioningFragments.size();
            if (i2 != 0) {
                View nonExistentView = new View(fragmentManager.mHost.getContext());
                int numContainers = transitioningFragments.size();
                for (int i3 = 0; i3 < numContainers; i3++) {
                    int containerId = transitioningFragments.keyAt(i3);
                    ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, records, isRecordPop, startIndex, endIndex);
                    FragmentContainerTransition containerTransition = transitioningFragments.valueAt(i3);
                    if (isReordered) {
                        configureTransitionsReordered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    } else {
                        configureTransitionsOrdered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = records.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = isRecordPop.get(recordNum).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = sources.get(i);
                        String targetName = targets.get(i);
                        String previousTarget = nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    @RequiresApi(21)
    private static void configureTransitionsReordered(FragmentManagerImpl fragmentManager, int containerId, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> nameOverrides) {
        Object exitTransition;
        ViewGroup sceneRoot = null;
        if (fragmentManager.mContainer.onHasView()) {
            sceneRoot = (ViewGroup) fragmentManager.mContainer.onFindViewById(containerId);
        }
        ViewGroup sceneRoot2 = sceneRoot;
        if (sceneRoot2 == null) {
            return;
        }
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        boolean inIsPop = fragments.lastInIsPop;
        boolean outIsPop = fragments.firstOutIsPop;
        ArrayList<View> sharedElementsIn = new ArrayList<>();
        ArrayList<View> sharedElementsOut = new ArrayList<>();
        Object enterTransition = getEnterTransition(inFragment, inIsPop);
        Object exitTransition2 = getExitTransition(outFragment, outIsPop);
        Object sharedElementTransition = configureSharedElementsReordered(sceneRoot2, nonExistentView, nameOverrides, fragments, sharedElementsOut, sharedElementsIn, enterTransition, exitTransition2);
        if (enterTransition == null && sharedElementTransition == null) {
            exitTransition = exitTransition2;
            if (exitTransition == null) {
                return;
            }
        } else {
            exitTransition = exitTransition2;
        }
        ArrayList<View> exitingViews = configureEnteringExitingViews(exitTransition, outFragment, sharedElementsOut, nonExistentView);
        ArrayList<View> enteringViews = configureEnteringExitingViews(enterTransition, inFragment, sharedElementsIn, nonExistentView);
        setViewVisibility(enteringViews, 4);
        Object transition = mergeTransitions(enterTransition, exitTransition, sharedElementTransition, inFragment, inIsPop);
        if (transition != null) {
            replaceHide(exitTransition, outFragment, exitingViews);
            ArrayList<String> inNames = FragmentTransitionCompat21.prepareSetNameOverridesReordered(sharedElementsIn);
            FragmentTransitionCompat21.scheduleRemoveTargets(transition, enterTransition, enteringViews, exitTransition, exitingViews, sharedElementTransition, sharedElementsIn);
            FragmentTransitionCompat21.beginDelayedTransition(sceneRoot2, transition);
            FragmentTransitionCompat21.setNameOverridesReordered(sceneRoot2, sharedElementsOut, sharedElementsIn, inNames, nameOverrides);
            setViewVisibility(enteringViews, 0);
            FragmentTransitionCompat21.swapSharedElementTargets(sharedElementTransition, sharedElementsOut, sharedElementsIn);
        }
    }

    @RequiresApi(21)
    private static void replaceHide(Object exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            FragmentTransitionCompat21.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            ViewGroup container = exitingFragment.mContainer;
            OneShotPreDrawListener.add(container, new Runnable() { // from class: android.support.v4.app.FragmentTransition.1
                @Override // java.lang.Runnable
                public void run() {
                    FragmentTransition.setViewVisibility(exitingViews, 4);
                }
            });
        }
    }

    @RequiresApi(21)
    private static void configureTransitionsOrdered(FragmentManagerImpl fragmentManager, int containerId, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> nameOverrides) {
        Object exitTransition;
        ViewGroup sceneRoot = null;
        if (fragmentManager.mContainer.onHasView()) {
            sceneRoot = (ViewGroup) fragmentManager.mContainer.onFindViewById(containerId);
        }
        ViewGroup sceneRoot2 = sceneRoot;
        if (sceneRoot2 == null) {
            return;
        }
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        boolean inIsPop = fragments.lastInIsPop;
        boolean outIsPop = fragments.firstOutIsPop;
        Object enterTransition = getEnterTransition(inFragment, inIsPop);
        Object exitTransition2 = getExitTransition(outFragment, outIsPop);
        ArrayList<View> sharedElementsOut = new ArrayList<>();
        ArrayList<View> sharedElementsIn = new ArrayList<>();
        Object sharedElementTransition = configureSharedElementsOrdered(sceneRoot2, nonExistentView, nameOverrides, fragments, sharedElementsOut, sharedElementsIn, enterTransition, exitTransition2);
        if (enterTransition == null && sharedElementTransition == null) {
            exitTransition = exitTransition2;
            if (exitTransition == null) {
                return;
            }
        } else {
            exitTransition = exitTransition2;
        }
        ArrayList<View> exitingViews = configureEnteringExitingViews(exitTransition, outFragment, sharedElementsOut, nonExistentView);
        Object exitTransition3 = (exitingViews == null || exitingViews.isEmpty()) ? null : null;
        FragmentTransitionCompat21.addTarget(enterTransition, nonExistentView);
        Object transition = mergeTransitions(enterTransition, exitTransition3, sharedElementTransition, inFragment, fragments.lastInIsPop);
        if (transition != null) {
            ArrayList<View> enteringViews = new ArrayList<>();
            FragmentTransitionCompat21.scheduleRemoveTargets(transition, enterTransition, enteringViews, exitTransition3, exitingViews, sharedElementTransition, sharedElementsIn);
            scheduleTargetChange(sceneRoot2, inFragment, nonExistentView, sharedElementsIn, enterTransition, enteringViews, exitTransition3, exitingViews);
            FragmentTransitionCompat21.setNameOverridesOrdered(sceneRoot2, sharedElementsIn, nameOverrides);
            FragmentTransitionCompat21.beginDelayedTransition(sceneRoot2, transition);
            FragmentTransitionCompat21.scheduleNameReset(sceneRoot2, sharedElementsIn, nameOverrides);
        }
    }

    @RequiresApi(21)
    private static void scheduleTargetChange(ViewGroup sceneRoot, final Fragment inFragment, final View nonExistentView, final ArrayList<View> sharedElementsIn, final Object enterTransition, final ArrayList<View> enteringViews, final Object exitTransition, final ArrayList<View> exitingViews) {
        OneShotPreDrawListener.add(sceneRoot, new Runnable() { // from class: android.support.v4.app.FragmentTransition.2
            @Override // java.lang.Runnable
            public void run() {
                if (enterTransition != null) {
                    FragmentTransitionCompat21.removeTarget(enterTransition, nonExistentView);
                    ArrayList<View> views = FragmentTransition.configureEnteringExitingViews(enterTransition, inFragment, sharedElementsIn, nonExistentView);
                    enteringViews.addAll(views);
                }
                ArrayList<View> views2 = exitingViews;
                if (views2 != null) {
                    if (exitTransition != null) {
                        ArrayList<View> tempExiting = new ArrayList<>();
                        tempExiting.add(nonExistentView);
                        FragmentTransitionCompat21.replaceTargets(exitTransition, exitingViews, tempExiting);
                    }
                    exitingViews.clear();
                    exitingViews.add(nonExistentView);
                }
            }
        });
    }

    @RequiresApi(21)
    private static Object getSharedElementTransition(Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object sharedElementEnterTransition;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            sharedElementEnterTransition = outFragment.getSharedElementReturnTransition();
        } else {
            sharedElementEnterTransition = inFragment.getSharedElementEnterTransition();
        }
        Object transition = FragmentTransitionCompat21.cloneTransition(sharedElementEnterTransition);
        return FragmentTransitionCompat21.wrapTransitionInSet(transition);
    }

    @RequiresApi(21)
    private static Object getEnterTransition(Fragment inFragment, boolean isPop) {
        Object enterTransition;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            enterTransition = inFragment.getReenterTransition();
        } else {
            enterTransition = inFragment.getEnterTransition();
        }
        return FragmentTransitionCompat21.cloneTransition(enterTransition);
    }

    @RequiresApi(21)
    private static Object getExitTransition(Fragment outFragment, boolean isPop) {
        Object exitTransition;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            exitTransition = outFragment.getReturnTransition();
        } else {
            exitTransition = outFragment.getExitTransition();
        }
        return FragmentTransitionCompat21.cloneTransition(exitTransition);
    }

    @RequiresApi(21)
    private static Object configureSharedElementsReordered(ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        final View epicenterView;
        Rect epicenter;
        final Fragment inFragment = fragments.lastIn;
        final Fragment outFragment = fragments.firstOut;
        if (inFragment != null) {
            inFragment.getView().setVisibility(0);
        }
        if (inFragment == null || outFragment == null) {
            return null;
        }
        final boolean inIsPop = fragments.lastInIsPop;
        Object sharedElementTransition = nameOverrides.isEmpty() ? null : getSharedElementTransition(inFragment, outFragment, inIsPop);
        ArrayMap<String, View> outSharedElements = captureOutSharedElements(nameOverrides, sharedElementTransition, fragments);
        final ArrayMap<String, View> inSharedElements = captureInSharedElements(nameOverrides, sharedElementTransition, fragments);
        if (nameOverrides.isEmpty()) {
            sharedElementTransition = null;
            if (outSharedElements != null) {
                outSharedElements.clear();
            }
            if (inSharedElements != null) {
                inSharedElements.clear();
            }
        } else {
            addSharedElementsWithMatchingNames(sharedElementsOut, outSharedElements, nameOverrides.keySet());
            addSharedElementsWithMatchingNames(sharedElementsIn, inSharedElements, nameOverrides.values());
        }
        Object sharedElementTransition2 = sharedElementTransition;
        if (enterTransition == null && exitTransition == null && sharedElementTransition2 == null) {
            return null;
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
        if (sharedElementTransition2 != null) {
            sharedElementsIn.add(nonExistentView);
            FragmentTransitionCompat21.setSharedElementTargets(sharedElementTransition2, nonExistentView, sharedElementsOut);
            boolean outIsPop = fragments.firstOutIsPop;
            BackStackRecord outTransaction = fragments.firstOutTransaction;
            setOutEpicenter(sharedElementTransition2, exitTransition, outSharedElements, outIsPop, outTransaction);
            Rect epicenter2 = new Rect();
            View epicenterView2 = getInEpicenterView(inSharedElements, fragments, enterTransition, inIsPop);
            if (epicenterView2 != null) {
                FragmentTransitionCompat21.setEpicenter(enterTransition, epicenter2);
            }
            epicenter = epicenter2;
            epicenterView = epicenterView2;
        } else {
            epicenterView = null;
            epicenter = null;
        }
        final Rect rect = epicenter;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() { // from class: android.support.v4.app.FragmentTransition.3
            @Override // java.lang.Runnable
            public void run() {
                FragmentTransition.callSharedElementStartEnd(Fragment.this, outFragment, inIsPop, inSharedElements, false);
                if (epicenterView != null) {
                    FragmentTransitionCompat21.getBoundsOnScreen(epicenterView, rect);
                }
            }
        });
        return sharedElementTransition2;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                views.add(view);
            }
        }
    }

    @RequiresApi(21)
    private static Object configureSharedElementsOrdered(ViewGroup sceneRoot, final View nonExistentView, final ArrayMap<String, String> nameOverrides, final FragmentContainerTransition fragments, final ArrayList<View> sharedElementsOut, final ArrayList<View> sharedElementsIn, final Object enterTransition, Object exitTransition) {
        final Fragment inFragment = fragments.lastIn;
        final Fragment outFragment = fragments.firstOut;
        Rect inEpicenter = null;
        if (inFragment != null && outFragment != null) {
            final boolean inIsPop = fragments.lastInIsPop;
            Object sharedElementTransition = nameOverrides.isEmpty() ? null : getSharedElementTransition(inFragment, outFragment, inIsPop);
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(nameOverrides, sharedElementTransition, fragments);
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementsOut.addAll(outSharedElements.values());
            }
            final Object sharedElementTransition2 = sharedElementTransition;
            if (enterTransition == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                inEpicenter = new Rect();
                FragmentTransitionCompat21.setSharedElementTargets(sharedElementTransition2, nonExistentView, sharedElementsOut);
                boolean outIsPop = fragments.firstOutIsPop;
                BackStackRecord outTransaction = fragments.firstOutTransaction;
                setOutEpicenter(sharedElementTransition2, exitTransition, outSharedElements, outIsPop, outTransaction);
                if (enterTransition != null) {
                    FragmentTransitionCompat21.setEpicenter(enterTransition, inEpicenter);
                }
            }
            final Rect inEpicenter2 = inEpicenter;
            OneShotPreDrawListener.add(sceneRoot, new Runnable() { // from class: android.support.v4.app.FragmentTransition.4
                @Override // java.lang.Runnable
                public void run() {
                    ArrayMap<String, View> inSharedElements = FragmentTransition.captureInSharedElements(ArrayMap.this, sharedElementTransition2, fragments);
                    if (inSharedElements != null) {
                        sharedElementsIn.addAll(inSharedElements.values());
                        sharedElementsIn.add(nonExistentView);
                    }
                    FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
                    if (sharedElementTransition2 != null) {
                        FragmentTransitionCompat21.swapSharedElementTargets(sharedElementTransition2, sharedElementsOut, sharedElementsIn);
                        View inEpicenterView = FragmentTransition.getInEpicenterView(inSharedElements, fragments, enterTransition, inIsPop);
                        if (inEpicenterView != null) {
                            FragmentTransitionCompat21.getBoundsOnScreen(inEpicenterView, inEpicenter2);
                        }
                    }
                }
            });
            return sharedElementTransition2;
        }
        return null;
    }

    @RequiresApi(21)
    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        FragmentTransitionCompat21.findNamedViews(outSharedElements, outFragment.getView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        outSharedElements.retainAll(names);
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = outSharedElements.get(name);
                if (view == null) {
                    nameOverrides.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    String targetValue = nameOverrides.remove(name);
                    nameOverrides.put(ViewCompat.getTransitionName(view), targetValue);
                }
            }
        } else {
            nameOverrides.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    public static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        FragmentTransitionCompat21.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.remove(key2);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view)) && (key = findKeyForValue(nameOverrides, name)) != null) {
                    nameOverrides.put(key, ViewCompat.getTransitionName(view));
                }
            }
        } else {
            retainValues(nameOverrides, inSharedElements);
        }
        return inSharedElements;
    }

    private static String findKeyForValue(ArrayMap<String, String> map, String value) {
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return map.keyAt(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition != null && inSharedElements != null && inTransaction.mSharedElementSourceNames != null && !inTransaction.mSharedElementSourceNames.isEmpty()) {
            if (inIsPop) {
                targetName = inTransaction.mSharedElementSourceNames.get(0);
            } else {
                targetName = inTransaction.mSharedElementTargetNames.get(0);
            }
            return inSharedElements.get(targetName);
        }
        return null;
    }

    @RequiresApi(21)
    private static void setOutEpicenter(Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = outSharedElements.get(sourceName);
            FragmentTransitionCompat21.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                FragmentTransitionCompat21.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            String targetName = nameOverrides.valueAt(i);
            if (!namedViews.containsKey(targetName)) {
                nameOverrides.removeAt(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = sharedElements == null ? 0 : sharedElements.size();
            for (int i = 0; i < count; i++) {
                names.add(sharedElements.keyAt(i));
                views.add(sharedElements.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    public static ArrayList<View> configureEnteringExitingViews(Object transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                FragmentTransitionCompat21.captureTransitioningViews(viewList, root);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                FragmentTransitionCompat21.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views == null) {
            return;
        }
        for (int i = views.size() - 1; i >= 0; i--) {
            View view = views.get(i);
            view.setVisibility(visibility);
        }
    }

    @RequiresApi(21)
    private static Object mergeTransitions(Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean overlap = true;
        if (enterTransition != null && exitTransition != null && inFragment != null) {
            overlap = isPop ? inFragment.getAllowReturnTransitionOverlap() : inFragment.getAllowEnterTransitionOverlap();
        }
        if (overlap) {
            Object transition = FragmentTransitionCompat21.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
            return transition;
        }
        Object transition2 = FragmentTransitionCompat21.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
        return transition2;
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            BackStackRecord.C1937Op op = transaction.mOps.get(opNum);
            addToFirstInLastOut(transaction, op, transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (!transaction.mManager.mContainer.onHasView()) {
            return;
        }
        int numOps = transaction.mOps.size();
        for (int opNum = numOps - 1; opNum >= 0; opNum--) {
            BackStackRecord.C1937Op op = transaction.mOps.get(opNum);
            addToFirstInLastOut(transaction, op, transitioningFragments, true, isReordered);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x010b A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void addToFirstInLastOut(BackStackRecord transaction, BackStackRecord.C1937Op op, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isPop, boolean isReorderedTransaction) {
        int containerId;
        boolean setLastIn;
        boolean setFirstOut;
        FragmentContainerTransition containerTransition;
        Fragment fragment;
        FragmentContainerTransition containerTransition2;
        FragmentContainerTransition containerTransition3;
        FragmentManagerImpl manager;
        Fragment fragment2 = op.fragment;
        if (fragment2 == null || (containerId = fragment2.mContainerId) == 0) {
            return;
        }
        int command = isPop ? INVERSE_OPS[op.cmd] : op.cmd;
        boolean setLastIn2 = false;
        boolean wasRemoved = false;
        boolean setFirstOut2 = false;
        boolean wasAdded = false;
        boolean z = false;
        if (command != 1) {
            switch (command) {
                case 3:
                case 6:
                    if (isReorderedTransaction) {
                        if (!fragment2.mAdded && fragment2.mView != null && fragment2.mView.getVisibility() == 0 && fragment2.mPostponedAlpha >= 0.0f) {
                            z = true;
                        }
                        setFirstOut2 = z;
                    } else {
                        if (fragment2.mAdded && !fragment2.mHidden) {
                            z = true;
                        }
                        setFirstOut2 = z;
                    }
                    wasRemoved = true;
                    break;
                case 4:
                    if (isReorderedTransaction) {
                        if (fragment2.mHiddenChanged && fragment2.mAdded && fragment2.mHidden) {
                            z = true;
                        }
                        setFirstOut2 = z;
                    } else {
                        if (fragment2.mAdded && !fragment2.mHidden) {
                            z = true;
                        }
                        setFirstOut2 = z;
                    }
                    wasRemoved = true;
                    break;
                case 5:
                    if (isReorderedTransaction) {
                        if (fragment2.mHiddenChanged && !fragment2.mHidden && fragment2.mAdded) {
                            z = true;
                        }
                        setLastIn2 = z;
                    } else {
                        setLastIn2 = fragment2.mHidden;
                    }
                    wasAdded = true;
                    break;
            }
            setLastIn = setLastIn2;
            boolean wasRemoved2 = wasRemoved;
            setFirstOut = setFirstOut2;
            boolean wasAdded2 = wasAdded;
            FragmentContainerTransition containerTransition4 = transitioningFragments.get(containerId);
            if (setLastIn) {
                containerTransition4 = ensureContainer(containerTransition4, transitioningFragments, containerId);
                containerTransition4.lastIn = fragment2;
                containerTransition4.lastInIsPop = isPop;
                containerTransition4.lastInTransaction = transaction;
            }
            containerTransition = containerTransition4;
            if (!isReorderedTransaction && wasAdded2) {
                if (containerTransition != null && containerTransition.firstOut == fragment2) {
                    containerTransition.firstOut = null;
                }
                manager = transaction.mManager;
                if (fragment2.mState < 1 && manager.mCurState >= 1 && !transaction.mReorderingAllowed) {
                    manager.makeActive(fragment2);
                    containerTransition2 = containerTransition;
                    fragment = null;
                    manager.moveToState(fragment2, 1, 0, 0, false);
                    if (setFirstOut) {
                        containerTransition3 = containerTransition2;
                    } else {
                        containerTransition3 = containerTransition2;
                        if (containerTransition3 == null || containerTransition3.firstOut == null) {
                            FragmentContainerTransition containerTransition5 = ensureContainer(containerTransition3, transitioningFragments, containerId);
                            containerTransition5.firstOut = fragment2;
                            containerTransition5.firstOutIsPop = isPop;
                            containerTransition5.firstOutTransaction = transaction;
                            containerTransition3 = containerTransition5;
                        }
                    }
                    if (isReorderedTransaction && wasRemoved2 && containerTransition3 != null && containerTransition3.lastIn == fragment2) {
                        containerTransition3.lastIn = fragment;
                        return;
                    }
                    return;
                }
            }
            fragment = null;
            containerTransition2 = containerTransition;
            if (setFirstOut) {
            }
            if (isReorderedTransaction) {
                return;
            }
            return;
        }
        if (isReorderedTransaction) {
            setLastIn2 = fragment2.mIsNewlyAdded;
        } else {
            if (!fragment2.mAdded && !fragment2.mHidden) {
                z = true;
            }
            setLastIn2 = z;
        }
        wasAdded = true;
        setLastIn = setLastIn2;
        boolean wasRemoved22 = wasRemoved;
        setFirstOut = setFirstOut2;
        boolean wasAdded22 = wasAdded;
        FragmentContainerTransition containerTransition42 = transitioningFragments.get(containerId);
        if (setLastIn) {
        }
        containerTransition = containerTransition42;
        if (!isReorderedTransaction) {
            if (containerTransition != null) {
                containerTransition.firstOut = null;
            }
            manager = transaction.mManager;
            if (fragment2.mState < 1) {
                manager.makeActive(fragment2);
                containerTransition2 = containerTransition;
                fragment = null;
                manager.moveToState(fragment2, 1, 0, 0, false);
                if (setFirstOut) {
                }
                if (isReorderedTransaction) {
                }
            }
        }
        fragment = null;
        containerTransition2 = containerTransition;
        if (setFirstOut) {
        }
        if (isReorderedTransaction) {
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition == null) {
            FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
            transitioningFragments.put(containerId, containerTransition2);
            return containerTransition2;
        }
        return containerTransition;
    }

    /* renamed from: android.support.v4.app.FragmentTransition$FragmentContainerTransition */
    /* loaded from: classes3.dex */
    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }
}