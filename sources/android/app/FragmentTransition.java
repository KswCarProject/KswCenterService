package android.app;

import android.app.FragmentTransition;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionSet;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    public static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;
    }

    FragmentTransition() {
    }

    static void startTransitions(FragmentManagerImpl fragmentManager, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered) {
        if (fragmentManager.mCurState >= 1) {
            SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
            for (int i = startIndex; i < endIndex; i++) {
                BackStackRecord record = records.get(i);
                if (isRecordPop.get(i).booleanValue()) {
                    calculatePopFragments(record, transitioningFragments, isReordered);
                } else {
                    calculateFragments(record, transitioningFragments, isReordered);
                }
            }
            if (transitioningFragments.size() != 0) {
                View nonExistentView = new View(fragmentManager.mHost.getContext());
                int numContainers = transitioningFragments.size();
                for (int i2 = 0; i2 < numContainers; i2++) {
                    int containerId = transitioningFragments.keyAt(i2);
                    ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, records, isRecordPop, startIndex, endIndex);
                    FragmentContainerTransition containerTransition = transitioningFragments.valueAt(i2);
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

    /* JADX WARNING: type inference failed for: r2v5, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsReordered(android.app.FragmentManagerImpl r26, int r27, android.app.FragmentTransition.FragmentContainerTransition r28, android.view.View r29, android.util.ArrayMap<java.lang.String, java.lang.String> r30) {
        /*
            r0 = r26
            r9 = r28
            r10 = r29
            r1 = 0
            android.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001b
            android.app.FragmentContainer r2 = r0.mContainer
            r11 = r27
            android.view.View r2 = r2.onFindViewById(r11)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            goto L_0x001d
        L_0x001b:
            r11 = r27
        L_0x001d:
            r12 = r1
            if (r12 != 0) goto L_0x0021
            return
        L_0x0021:
            android.app.Fragment r13 = r9.lastIn
            android.app.Fragment r14 = r9.firstOut
            boolean r15 = r9.lastInIsPop
            boolean r8 = r9.firstOutIsPop
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r7 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6 = r1
            android.transition.Transition r5 = getEnterTransition(r13, r15)
            android.transition.Transition r4 = getExitTransition(r14, r8)
            r1 = r12
            r2 = r29
            r3 = r30
            r23 = r4
            r4 = r28
            r24 = r5
            r5 = r6
            r0 = r6
            r6 = r7
            r9 = r7
            r7 = r24
            r25 = r8
            r8 = r23
            android.transition.TransitionSet r1 = configureSharedElementsReordered(r1, r2, r3, r4, r5, r6, r7, r8)
            r2 = r24
            if (r2 != 0) goto L_0x0061
            if (r1 != 0) goto L_0x0061
            r3 = r23
            if (r3 != 0) goto L_0x0063
            return
        L_0x0061:
            r3 = r23
        L_0x0063:
            java.util.ArrayList r4 = configureEnteringExitingViews(r3, r14, r0, r10)
            java.util.ArrayList r5 = configureEnteringExitingViews(r2, r13, r9, r10)
            r6 = 4
            setViewVisibility(r5, r6)
            android.transition.Transition r6 = mergeTransitions(r2, r3, r1, r13, r15)
            if (r6 == 0) goto L_0x00a9
            replaceHide(r3, r14, r4)
            r7 = r30
            r6.setNameOverrides(r7)
            r16 = r6
            r17 = r2
            r18 = r5
            r19 = r3
            r20 = r4
            r21 = r1
            r22 = r9
            scheduleRemoveTargets(r16, r17, r18, r19, r20, r21, r22)
            android.transition.TransitionManager.beginDelayedTransition(r12, r6)
            r8 = 0
            setViewVisibility(r5, r8)
            if (r1 == 0) goto L_0x00ab
            java.util.List r8 = r1.getTargets()
            r8.clear()
            java.util.List r8 = r1.getTargets()
            r8.addAll(r9)
            replaceTargets(r1, r0, r9)
            goto L_0x00ab
        L_0x00a9:
            r7 = r30
        L_0x00ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.FragmentTransition.configureTransitionsReordered(android.app.FragmentManagerImpl, int, android.app.FragmentTransition$FragmentContainerTransition, android.view.View, android.util.ArrayMap):void");
    }

    /* JADX WARNING: type inference failed for: r2v5, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsOrdered(android.app.FragmentManagerImpl r29, int r30, android.app.FragmentTransition.FragmentContainerTransition r31, android.view.View r32, android.util.ArrayMap<java.lang.String, java.lang.String> r33) {
        /*
            r0 = r29
            r9 = r31
            r15 = r32
            r1 = 0
            android.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001b
            android.app.FragmentContainer r2 = r0.mContainer
            r14 = r30
            android.view.View r2 = r2.onFindViewById(r14)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            goto L_0x001d
        L_0x001b:
            r14 = r30
        L_0x001d:
            r13 = r1
            if (r13 != 0) goto L_0x0021
            return
        L_0x0021:
            android.app.Fragment r12 = r9.lastIn
            android.app.Fragment r11 = r9.firstOut
            boolean r10 = r9.lastInIsPop
            boolean r8 = r9.firstOutIsPop
            android.transition.Transition r7 = getEnterTransition(r12, r10)
            android.transition.Transition r5 = getExitTransition(r11, r8)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r4 = r1
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r1 = r13
            r2 = r32
            r3 = r33
            r25 = r4
            r4 = r31
            r26 = r5
            r5 = r25
            r27 = r7
            r28 = r8
            r8 = r26
            android.transition.TransitionSet r1 = configureSharedElementsOrdered(r1, r2, r3, r4, r5, r6, r7, r8)
            r2 = r27
            if (r2 != 0) goto L_0x005e
            if (r1 != 0) goto L_0x005e
            r3 = r26
            if (r3 != 0) goto L_0x0060
            return
        L_0x005e:
            r3 = r26
        L_0x0060:
            r4 = r25
            java.util.ArrayList r5 = configureEnteringExitingViews(r3, r11, r4, r15)
            if (r5 == 0) goto L_0x006e
            boolean r7 = r5.isEmpty()
            if (r7 == 0) goto L_0x006f
        L_0x006e:
            r3 = 0
        L_0x006f:
            if (r2 == 0) goto L_0x0074
            r2.addTarget((android.view.View) r15)
        L_0x0074:
            boolean r7 = r9.lastInIsPop
            android.transition.Transition r7 = mergeTransitions(r2, r3, r1, r12, r7)
            if (r7 == 0) goto L_0x00af
            r8 = r33
            r7.setNameOverrides(r8)
            java.util.ArrayList r20 = new java.util.ArrayList
            r20.<init>()
            r18 = r7
            r19 = r2
            r21 = r3
            r22 = r5
            r23 = r1
            r24 = r6
            scheduleRemoveTargets(r18, r19, r20, r21, r22, r23, r24)
            r18 = r10
            r10 = r13
            r19 = r11
            r11 = r12
            r21 = r12
            r12 = r32
            r0 = r13
            r13 = r6
            r14 = r2
            r15 = r20
            r16 = r3
            r17 = r5
            scheduleTargetChange(r10, r11, r12, r13, r14, r15, r16, r17)
            android.transition.TransitionManager.beginDelayedTransition(r0, r7)
            goto L_0x00b8
        L_0x00af:
            r8 = r33
            r18 = r10
            r19 = r11
            r21 = r12
            r0 = r13
        L_0x00b8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.FragmentTransition.configureTransitionsOrdered(android.app.FragmentManagerImpl, int, android.app.FragmentTransition$FragmentContainerTransition, android.view.View, android.util.ArrayMap):void");
    }

    private static void replaceHide(Transition exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            final View fragmentView = exitingFragment.getView();
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable(exitingViews) {
                private final /* synthetic */ ArrayList f$0;

                {
                    this.f$0 = r1;
                }

                public final void run() {
                    FragmentTransition.setViewVisibility(this.f$0, 4);
                }
            });
            exitTransition.addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    View.this.setVisibility(8);
                    FragmentTransition.setViewVisibility(exitingViews, 0);
                }
            });
        }
    }

    private static void scheduleTargetChange(ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList<View> sharedElementsIn, Transition enterTransition, ArrayList<View> enteringViews, Transition exitTransition, ArrayList<View> exitingViews) {
        ViewGroup viewGroup = sceneRoot;
        OneShotPreDrawListener.add(sceneRoot, new Runnable(nonExistentView, inFragment, sharedElementsIn, enteringViews, exitingViews, exitTransition) {
            private final /* synthetic */ View f$1;
            private final /* synthetic */ Fragment f$2;
            private final /* synthetic */ ArrayList f$3;
            private final /* synthetic */ ArrayList f$4;
            private final /* synthetic */ ArrayList f$5;
            private final /* synthetic */ Transition f$6;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
                this.f$5 = r6;
                this.f$6 = r7;
            }

            public final void run() {
                FragmentTransition.lambda$scheduleTargetChange$1(Transition.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
            }
        });
    }

    static /* synthetic */ void lambda$scheduleTargetChange$1(Transition enterTransition, View nonExistentView, Fragment inFragment, ArrayList sharedElementsIn, ArrayList enteringViews, ArrayList exitingViews, Transition exitTransition) {
        if (enterTransition != null) {
            enterTransition.removeTarget(nonExistentView);
            enteringViews.addAll(configureEnteringExitingViews(enterTransition, inFragment, sharedElementsIn, nonExistentView));
        }
        if (exitingViews != null) {
            if (exitTransition != null) {
                ArrayList<View> tempExiting = new ArrayList<>();
                tempExiting.add(nonExistentView);
                replaceTargets(exitTransition, exitingViews, tempExiting);
            }
            exitingViews.clear();
            exitingViews.add(nonExistentView);
        }
    }

    private static TransitionSet getSharedElementTransition(Fragment inFragment, Fragment outFragment, boolean isPop) {
        Transition transition;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            transition = outFragment.getSharedElementReturnTransition();
        } else {
            transition = inFragment.getSharedElementEnterTransition();
        }
        Transition transition2 = cloneTransition(transition);
        if (transition2 == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition2);
        return transitionSet;
    }

    private static Transition getEnterTransition(Fragment inFragment, boolean isPop) {
        Transition transition;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            transition = inFragment.getReenterTransition();
        } else {
            transition = inFragment.getEnterTransition();
        }
        return cloneTransition(transition);
    }

    private static Transition getExitTransition(Fragment outFragment, boolean isPop) {
        Transition transition;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            transition = outFragment.getReturnTransition();
        } else {
            transition = outFragment.getExitTransition();
        }
        return cloneTransition(transition);
    }

    private static Transition cloneTransition(Transition transition) {
        if (transition != null) {
            return transition.clone();
        }
        return transition;
    }

    private static TransitionSet configureSharedElementsReordered(ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Transition enterTransition, Transition exitTransition) {
        View epicenterView;
        Rect epicenter;
        View view = nonExistentView;
        ArrayMap<String, String> arrayMap = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Transition transition = enterTransition;
        Transition transition2 = exitTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment != null) {
            inFragment.getView().setVisibility(0);
        }
        if (inFragment == null || outFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            return null;
        }
        boolean inIsPop = fragmentContainerTransition.lastInIsPop;
        TransitionSet sharedElementTransition = nameOverrides.isEmpty() ? null : getSharedElementTransition(inFragment, outFragment, inIsPop);
        ArrayMap<String, View> outSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        ArrayMap<String, View> inSharedElements = captureInSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (nameOverrides.isEmpty()) {
            sharedElementTransition = null;
            if (outSharedElements != null) {
                outSharedElements.clear();
            }
            if (inSharedElements != null) {
                inSharedElements.clear();
            }
        } else {
            addSharedElementsWithMatchingNames(arrayList, outSharedElements, nameOverrides.keySet());
            addSharedElementsWithMatchingNames(arrayList2, inSharedElements, nameOverrides.values());
        }
        TransitionSet sharedElementTransition2 = sharedElementTransition;
        if (transition == null && transition2 == null && sharedElementTransition2 == null) {
            return null;
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
        if (sharedElementTransition2 != null) {
            arrayList2.add(view);
            setSharedElementTargets(sharedElementTransition2, view, arrayList);
            setOutEpicenter(sharedElementTransition2, transition2, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            final Rect epicenter2 = new Rect();
            View epicenterView2 = getInEpicenterView(inSharedElements, fragmentContainerTransition, transition, inIsPop);
            if (epicenterView2 != null) {
                transition.setEpicenterCallback(new Transition.EpicenterCallback() {
                    public Rect onGetEpicenter(Transition transition) {
                        return Rect.this;
                    }
                });
            }
            epicenter = epicenter2;
            epicenterView = epicenterView2;
        } else {
            epicenter = null;
            epicenterView = null;
        }
        $$Lambda$FragmentTransition$jurn0WXuKw3bRQ_2d5zCWdeZWuI r1 = r7;
        TransitionSet sharedElementTransition3 = sharedElementTransition2;
        ArrayMap<String, View> arrayMap2 = outSharedElements;
        boolean z = inIsPop;
        $$Lambda$FragmentTransition$jurn0WXuKw3bRQ_2d5zCWdeZWuI r7 = new Runnable(outFragment, inIsPop, inSharedElements, epicenterView, epicenter) {
            private final /* synthetic */ Fragment f$1;
            private final /* synthetic */ boolean f$2;
            private final /* synthetic */ ArrayMap f$3;
            private final /* synthetic */ View f$4;
            private final /* synthetic */ Rect f$5;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
                this.f$5 = r6;
            }

            public final void run() {
                FragmentTransition.lambda$configureSharedElementsReordered$2(Fragment.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
            }
        };
        OneShotPreDrawListener.add(sceneRoot, r1);
        return sharedElementTransition3;
    }

    static /* synthetic */ void lambda$configureSharedElementsReordered$2(Fragment inFragment, Fragment outFragment, boolean inIsPop, ArrayMap inSharedElements, View epicenterView, Rect epicenter) {
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
        if (epicenterView != null) {
            epicenterView.getBoundsOnScreen(epicenter);
        }
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = sharedElements.valueAt(i);
            if (view != null && nameOverridesSet.contains(view.getTransitionName())) {
                views.add(view);
            }
        }
    }

    private static TransitionSet configureSharedElementsOrdered(ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Transition enterTransition, Transition exitTransition) {
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        Transition transition = enterTransition;
        Transition transition2 = exitTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        final Rect inEpicenter = null;
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            Fragment fragment = outFragment;
            Fragment fragment2 = inFragment;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
            Fragment fragment3 = outFragment;
            Fragment fragment4 = inFragment;
        } else {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            TransitionSet sharedElementTransition = nameOverrides.isEmpty() ? null : getSharedElementTransition(inFragment, outFragment, inIsPop);
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(nameOverrides, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                arrayList.addAll(outSharedElements.values());
            }
            TransitionSet sharedElementTransition2 = sharedElementTransition;
            if (transition == null && transition2 == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                inEpicenter = new Rect();
                setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                setOutEpicenter(sharedElementTransition2, transition2, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                if (transition != null) {
                    transition.setEpicenterCallback(new Transition.EpicenterCallback() {
                        public Rect onGetEpicenter(Transition transition) {
                            if (Rect.this.isEmpty()) {
                                return null;
                            }
                            return Rect.this;
                        }
                    });
                }
            } else {
                View view = nonExistentView;
            }
            $$Lambda$FragmentTransition$Ip0LktADPhG_3ouNBXgzufWpFfY r12 = r0;
            TransitionSet sharedElementTransition3 = sharedElementTransition2;
            ArrayMap<String, View> arrayMap = outSharedElements;
            boolean z = inIsPop;
            Fragment fragment5 = outFragment;
            Fragment fragment6 = inFragment;
            $$Lambda$FragmentTransition$Ip0LktADPhG_3ouNBXgzufWpFfY r0 = new Runnable(sharedElementTransition2, fragments, sharedElementsIn, nonExistentView, inFragment, outFragment, inIsPop, sharedElementsOut, enterTransition, inEpicenter) {
                private final /* synthetic */ TransitionSet f$1;
                private final /* synthetic */ Rect f$10;
                private final /* synthetic */ FragmentTransition.FragmentContainerTransition f$2;
                private final /* synthetic */ ArrayList f$3;
                private final /* synthetic */ View f$4;
                private final /* synthetic */ Fragment f$5;
                private final /* synthetic */ Fragment f$6;
                private final /* synthetic */ boolean f$7;
                private final /* synthetic */ ArrayList f$8;
                private final /* synthetic */ Transition f$9;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                    this.f$4 = r5;
                    this.f$5 = r6;
                    this.f$6 = r7;
                    this.f$7 = r8;
                    this.f$8 = r9;
                    this.f$9 = r10;
                    this.f$10 = r11;
                }

                public final void run() {
                    FragmentTransition.lambda$configureSharedElementsOrdered$3(ArrayMap.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10);
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r12);
            return sharedElementTransition3;
        }
        return null;
    }

    static /* synthetic */ void lambda$configureSharedElementsOrdered$3(ArrayMap nameOverrides, TransitionSet finalSharedElementTransition, FragmentContainerTransition fragments, ArrayList sharedElementsIn, View nonExistentView, Fragment inFragment, Fragment outFragment, boolean inIsPop, ArrayList sharedElementsOut, Transition enterTransition, Rect inEpicenter) {
        ArrayMap<String, View> inSharedElements = captureInSharedElements(nameOverrides, finalSharedElementTransition, fragments);
        if (inSharedElements != null) {
            sharedElementsIn.addAll(inSharedElements.values());
            sharedElementsIn.add(nonExistentView);
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
        if (finalSharedElementTransition != null) {
            finalSharedElementTransition.getTargets().clear();
            finalSharedElementTransition.getTargets().addAll(sharedElementsIn);
            replaceTargets(finalSharedElementTransition, sharedElementsOut, sharedElementsIn);
            View inEpicenterView = getInEpicenterView(inSharedElements, fragments, enterTransition, inIsPop);
            if (inEpicenterView != null) {
                inEpicenterView.getBoundsOnScreen(inEpicenter);
            }
        }
    }

    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> nameOverrides, TransitionSet sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        outFragment.getView().findNamedViews(outSharedElements);
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
                } else if (!name.equals(view.getTransitionName())) {
                    nameOverrides.put(view.getTransitionName(), nameOverrides.remove(name));
                }
            }
        } else {
            nameOverrides.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    private static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> nameOverrides, TransitionSet sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        fragmentView.findNamedViews(inSharedElements);
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
        if (names == null || sharedElementCallback == null) {
            retainValues(nameOverrides, inSharedElements);
        } else {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.remove(key2);
                    }
                } else if (!name.equals(view.getTransitionName()) && (key = findKeyForValue(nameOverrides, name)) != null) {
                    nameOverrides.put(key, view.getTransitionName());
                }
            }
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

    private static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Transition enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inSharedElements == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (inIsPop) {
            targetName = inTransaction.mSharedElementSourceNames.get(0);
        } else {
            targetName = inTransaction.mSharedElementTargetNames.get(0);
        }
        return inSharedElements.get(targetName);
    }

    private static void setOutEpicenter(TransitionSet sharedElementTransition, Transition exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = outSharedElements.get(sourceName);
            setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void setEpicenter(Transition transition, View view) {
        if (view != null) {
            final Rect epicenter = new Rect();
            view.getBoundsOnScreen(epicenter);
            transition.setEpicenterCallback(new Transition.EpicenterCallback() {
                public Rect onGetEpicenter(Transition transition) {
                    return Rect.this;
                }
            });
        }
    }

    private static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey(nameOverrides.valueAt(i))) {
                nameOverrides.removeAt(i);
            }
        }
    }

    private static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
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
                sharedElementCallback.onSharedElementStart(names, views, (List<View>) null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, (List<View>) null);
            }
        }
    }

    private static void setSharedElementTargets(TransitionSet transition, View nonExistentView, ArrayList<View> sharedViews) {
        List<View> views = transition.getTargets();
        views.clear();
        int count = sharedViews.size();
        for (int i = 0; i < count; i++) {
            bfsAddViewChildren(views, sharedViews.get(i));
        }
        views.add(nonExistentView);
        sharedViews.add(nonExistentView);
        addTargets(transition, sharedViews);
    }

    private static void bfsAddViewChildren(List<View> views, View startView) {
        int startIndex = views.size();
        if (!containedBeforeIndex(views, startView, startIndex)) {
            views.add(startView);
            for (int index = startIndex; index < views.size(); index++) {
                View view = views.get(index);
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int childCount = viewGroup.getChildCount();
                    for (int childIndex = 0; childIndex < childCount; childIndex++) {
                        View child = viewGroup.getChildAt(childIndex);
                        if (!containedBeforeIndex(views, child, startIndex)) {
                            views.add(child);
                        }
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(List<View> views, View view, int maxIndex) {
        for (int i = 0; i < maxIndex; i++) {
            if (views.get(i) == view) {
                return true;
            }
        }
        return false;
    }

    private static void scheduleRemoveTargets(Transition overalTransition, Transition enterTransition, ArrayList<View> enteringViews, Transition exitTransition, ArrayList<View> exitingViews, TransitionSet sharedElementTransition, ArrayList<View> sharedElementsIn) {
        final Transition transition = enterTransition;
        final ArrayList<View> arrayList = enteringViews;
        final Transition transition2 = exitTransition;
        final ArrayList<View> arrayList2 = exitingViews;
        final TransitionSet transitionSet = sharedElementTransition;
        final ArrayList<View> arrayList3 = sharedElementsIn;
        overalTransition.addListener(new TransitionListenerAdapter() {
            public void onTransitionStart(Transition transition) {
                if (Transition.this != null) {
                    FragmentTransition.replaceTargets(Transition.this, arrayList, (ArrayList<View>) null);
                }
                if (transition2 != null) {
                    FragmentTransition.replaceTargets(transition2, arrayList2, (ArrayList<View>) null);
                }
                if (transitionSet != null) {
                    FragmentTransition.replaceTargets(transitionSet, arrayList3, (ArrayList<View>) null);
                }
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }
        });
    }

    public static void replaceTargets(Transition transition, ArrayList<View> oldTargets, ArrayList<View> newTargets) {
        List<View> targets;
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet set = (TransitionSet) transition;
            int numTransitions = set.getTransitionCount();
            while (i < numTransitions) {
                replaceTargets(set.getTransitionAt(i), oldTargets, newTargets);
                i++;
            }
        } else if (!hasSimpleTarget(transition) && (targets = transition.getTargets()) != null && targets.size() == oldTargets.size() && targets.containsAll(oldTargets)) {
            int targetCount = newTargets == null ? 0 : newTargets.size();
            while (i < targetCount) {
                transition.addTarget(newTargets.get(i));
                i++;
            }
            for (int i2 = oldTargets.size() - 1; i2 >= 0; i2--) {
                transition.removeTarget(oldTargets.get(i2));
            }
        }
    }

    public static void addTargets(Transition transition, ArrayList<View> views) {
        if (transition != null) {
            int i = 0;
            if (transition instanceof TransitionSet) {
                TransitionSet set = (TransitionSet) transition;
                int numTransitions = set.getTransitionCount();
                while (i < numTransitions) {
                    addTargets(set.getTransitionAt(i), views);
                    i++;
                }
            } else if (!hasSimpleTarget(transition) && isNullOrEmpty(transition.getTargets())) {
                int numViews = views.size();
                while (i < numViews) {
                    transition.addTarget(views.get(i));
                    i++;
                }
            }
        }
    }

    private static boolean hasSimpleTarget(Transition transition) {
        return !isNullOrEmpty(transition.getTargetIds()) || !isNullOrEmpty(transition.getTargetNames()) || !isNullOrEmpty(transition.getTargetTypes());
    }

    private static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    private static ArrayList<View> configureEnteringExitingViews(Transition transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                root.captureTransitioningViews(viewList);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    /* access modifiers changed from: private */
    public static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                views.get(i).setVisibility(visibility);
            }
        }
    }

    private static Transition mergeTransitions(Transition enterTransition, Transition exitTransition, Transition sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean z;
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            if (isPop) {
                z = inFragment.getAllowReturnTransitionOverlap();
            } else {
                z = inFragment.getAllowEnterTransitionOverlap();
            }
            overlap = z;
        }
        if (overlap) {
            TransitionSet transitionSet = new TransitionSet();
            if (enterTransition != null) {
                transitionSet.addTransition(enterTransition);
            }
            if (exitTransition != null) {
                transitionSet.addTransition(exitTransition);
            }
            if (sharedElementTransition == null) {
                return transitionSet;
            }
            transitionSet.addTransition(sharedElementTransition);
            return transitionSet;
        }
        Transition staggered = null;
        if (exitTransition != null && enterTransition != null) {
            staggered = new TransitionSet().addTransition(exitTransition).addTransition(enterTransition).setOrdering(1);
        } else if (exitTransition != null) {
            staggered = exitTransition;
        } else if (enterTransition != null) {
            staggered = enterTransition;
        }
        if (sharedElementTransition == null) {
            return staggered;
        }
        TransitionSet together = new TransitionSet();
        if (staggered != null) {
            together.addTransition(staggered);
        }
        together.addTransition(sharedElementTransition);
        return together;
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, transaction.mOps.get(opNum), transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (transaction.mManager.mContainer.onHasView()) {
            for (int opNum = transaction.mOps.size() - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, transaction.mOps.get(opNum), transitioningFragments, true, isReordered);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:88:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x011b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void addToFirstInLastOut(android.app.BackStackRecord r22, android.app.BackStackRecord.Op r23, android.util.SparseArray<android.app.FragmentTransition.FragmentContainerTransition> r24, boolean r25, boolean r26) {
        /*
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            android.app.Fragment r10 = r1.fragment
            if (r10 != 0) goto L_0x000d
            return
        L_0x000d:
            int r11 = r10.mContainerId
            if (r11 != 0) goto L_0x0012
            return
        L_0x0012:
            if (r3 == 0) goto L_0x001b
            int[] r4 = INVERSE_OPS
            int r5 = r1.cmd
            r4 = r4[r5]
            goto L_0x001d
        L_0x001b:
            int r4 = r1.cmd
        L_0x001d:
            r12 = r4
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 1
            if (r12 == r9) goto L_0x0093
            switch(r12) {
                case 3: goto L_0x0065;
                case 4: goto L_0x0046;
                case 5: goto L_0x0030;
                case 6: goto L_0x0065;
                case 7: goto L_0x0093;
                default: goto L_0x0029;
            }
        L_0x0029:
            r13 = r4
            r15 = r5
            r16 = r6
            r14 = r7
            goto L_0x00a5
        L_0x0030:
            if (r26 == 0) goto L_0x0042
            boolean r13 = r10.mHiddenChanged
            if (r13 == 0) goto L_0x0040
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x0040
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0040
            r8 = r9
        L_0x0040:
            r4 = r8
            goto L_0x0044
        L_0x0042:
            boolean r4 = r10.mHidden
        L_0x0044:
            r7 = 1
            goto L_0x0029
        L_0x0046:
            if (r26 == 0) goto L_0x0058
            boolean r13 = r10.mHiddenChanged
            if (r13 == 0) goto L_0x0056
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0056
            boolean r13 = r10.mHidden
            if (r13 == 0) goto L_0x0056
            r8 = r9
        L_0x0056:
            r6 = r8
            goto L_0x0063
        L_0x0058:
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0062
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x0062
            r8 = r9
        L_0x0062:
            r6 = r8
        L_0x0063:
            r5 = 1
            goto L_0x0029
        L_0x0065:
            if (r26 == 0) goto L_0x0086
            boolean r13 = r10.mAdded
            if (r13 != 0) goto L_0x0084
            android.view.View r13 = r10.mView
            if (r13 == 0) goto L_0x0084
            android.view.View r13 = r10.mView
            int r13 = r13.getVisibility()
            if (r13 != 0) goto L_0x0084
            android.view.View r13 = r10.mView
            float r13 = r13.getTransitionAlpha()
            r14 = 0
            int r13 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r13 <= 0) goto L_0x0084
            r8 = r9
        L_0x0084:
            r6 = r8
            goto L_0x0091
        L_0x0086:
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0090
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x0090
            r8 = r9
        L_0x0090:
            r6 = r8
        L_0x0091:
            r5 = 1
            goto L_0x0029
        L_0x0093:
            if (r26 == 0) goto L_0x0098
            boolean r4 = r10.mIsNewlyAdded
            goto L_0x00a3
        L_0x0098:
            boolean r13 = r10.mAdded
            if (r13 != 0) goto L_0x00a2
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x00a2
            r8 = r9
        L_0x00a2:
            r4 = r8
        L_0x00a3:
            r7 = 1
            goto L_0x0029
        L_0x00a5:
            java.lang.Object r4 = r2.get(r11)
            android.app.FragmentTransition$FragmentContainerTransition r4 = (android.app.FragmentTransition.FragmentContainerTransition) r4
            if (r13 == 0) goto L_0x00b8
            android.app.FragmentTransition$FragmentContainerTransition r4 = ensureContainer(r4, r2, r11)
            r4.lastIn = r10
            r4.lastInIsPop = r3
            r4.lastInTransaction = r0
        L_0x00b8:
            r8 = r4
            r7 = 0
            if (r26 != 0) goto L_0x0101
            if (r14 == 0) goto L_0x0101
            if (r8 == 0) goto L_0x00c6
            android.app.Fragment r4 = r8.firstOut
            if (r4 != r10) goto L_0x00c6
            r8.firstOut = r7
        L_0x00c6:
            android.app.FragmentManagerImpl r6 = r0.mManager
            int r4 = r10.mState
            if (r4 >= r9) goto L_0x0101
            int r4 = r6.mCurState
            if (r4 < r9) goto L_0x0101
            android.app.FragmentHostCallback<?> r4 = r6.mHost
            android.content.Context r4 = r4.getContext()
            android.content.pm.ApplicationInfo r4 = r4.getApplicationInfo()
            int r4 = r4.targetSdkVersion
            r5 = 24
            if (r4 < r5) goto L_0x0101
            boolean r4 = r0.mReorderingAllowed
            if (r4 != 0) goto L_0x0101
            r6.makeActive(r10)
            r9 = 1
            r17 = 0
            r18 = 0
            r19 = 0
            r4 = r6
            r5 = r10
            r20 = r6
            r6 = r9
            r9 = r7
            r7 = r17
            r21 = r8
            r8 = r18
            r1 = r9
            r9 = r19
            r4.moveToState(r5, r6, r7, r8, r9)
            goto L_0x0104
        L_0x0101:
            r1 = r7
            r21 = r8
        L_0x0104:
            if (r16 == 0) goto L_0x011b
            r4 = r21
            if (r4 == 0) goto L_0x010e
            android.app.Fragment r5 = r4.firstOut
            if (r5 != 0) goto L_0x011d
        L_0x010e:
            android.app.FragmentTransition$FragmentContainerTransition r8 = ensureContainer(r4, r2, r11)
            r8.firstOut = r10
            r8.firstOutIsPop = r3
            r8.firstOutTransaction = r0
            r4 = r8
            goto L_0x011d
        L_0x011b:
            r4 = r21
        L_0x011d:
            if (r26 != 0) goto L_0x0129
            if (r15 == 0) goto L_0x0129
            if (r4 == 0) goto L_0x0129
            android.app.Fragment r5 = r4.lastIn
            if (r5 != r10) goto L_0x0129
            r4.lastIn = r1
        L_0x0129:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.FragmentTransition.addToFirstInLastOut(android.app.BackStackRecord, android.app.BackStackRecord$Op, android.util.SparseArray, boolean, boolean):void");
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        transitioningFragments.put(containerId, containerTransition2);
        return containerTransition2;
    }
}
