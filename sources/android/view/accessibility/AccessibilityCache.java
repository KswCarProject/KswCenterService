package android.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityCache {
    public static final int CACHE_CRITICAL_EVENTS_MASK = 4307005;
    private static final boolean CHECK_INTEGRITY = Build.IS_ENG;
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "AccessibilityCache";
    private long mAccessibilityFocus = 2147483647L;
    private final AccessibilityNodeRefresher mAccessibilityNodeRefresher;
    private long mInputFocus = 2147483647L;
    private boolean mIsAllWindowsCached;
    private final Object mLock = new Object();
    private final SparseArray<LongSparseArray<AccessibilityNodeInfo>> mNodeCache = new SparseArray<>();
    private final SparseArray<AccessibilityWindowInfo> mTempWindowArray = new SparseArray<>();
    private final SparseArray<AccessibilityWindowInfo> mWindowCache = new SparseArray<>();

    public AccessibilityCache(AccessibilityNodeRefresher nodeRefresher) {
        this.mAccessibilityNodeRefresher = nodeRefresher;
    }

    public void setWindows(List<AccessibilityWindowInfo> windows) {
        synchronized (this.mLock) {
            clearWindowCache();
            if (windows != null) {
                int windowCount = windows.size();
                for (int i = 0; i < windowCount; i++) {
                    addWindow(windows.get(i));
                }
                this.mIsAllWindowsCached = true;
            }
        }
    }

    public void addWindow(AccessibilityWindowInfo window) {
        synchronized (this.mLock) {
            this.mWindowCache.put(window.getId(), new AccessibilityWindowInfo(window));
        }
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        synchronized (this.mLock) {
            switch (event.getEventType()) {
                case 1:
                case 4:
                case 16:
                case 8192:
                    refreshCachedNodeLocked(event.getWindowId(), event.getSourceNodeId());
                    break;
                case 8:
                    if (this.mInputFocus != 2147483647L) {
                        refreshCachedNodeLocked(event.getWindowId(), this.mInputFocus);
                    }
                    this.mInputFocus = event.getSourceNodeId();
                    refreshCachedNodeLocked(event.getWindowId(), this.mInputFocus);
                    break;
                case 32:
                case 4194304:
                    clear();
                    break;
                case 2048:
                    synchronized (this.mLock) {
                        int windowId = event.getWindowId();
                        long sourceId = event.getSourceNodeId();
                        if ((event.getContentChangeTypes() & 1) != 0) {
                            clearSubTreeLocked(windowId, sourceId);
                        } else {
                            refreshCachedNodeLocked(windowId, sourceId);
                        }
                    }
                    break;
                case 4096:
                    clearSubTreeLocked(event.getWindowId(), event.getSourceNodeId());
                    break;
                case 32768:
                    if (this.mAccessibilityFocus != 2147483647L) {
                        refreshCachedNodeLocked(event.getWindowId(), this.mAccessibilityFocus);
                    }
                    this.mAccessibilityFocus = event.getSourceNodeId();
                    refreshCachedNodeLocked(event.getWindowId(), this.mAccessibilityFocus);
                    break;
                case 65536:
                    if (this.mAccessibilityFocus == event.getSourceNodeId()) {
                        refreshCachedNodeLocked(event.getWindowId(), this.mAccessibilityFocus);
                        this.mAccessibilityFocus = 2147483647L;
                        break;
                    }
                    break;
            }
        }
        if (CHECK_INTEGRITY) {
            checkIntegrity();
        }
    }

    private void refreshCachedNodeLocked(int windowId, long sourceId) {
        AccessibilityNodeInfo cachedInfo;
        LongSparseArray<AccessibilityNodeInfo> nodes = this.mNodeCache.get(windowId);
        if (nodes != null && (cachedInfo = nodes.get(sourceId)) != null && !this.mAccessibilityNodeRefresher.refreshNode(cachedInfo, true)) {
            clearSubTreeLocked(windowId, sourceId);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.accessibility.AccessibilityNodeInfo getNode(int r5, long r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.util.SparseArray<android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo>> r1 = r4.mNodeCache     // Catch:{ all -> 0x0020 }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x0020 }
            android.util.LongSparseArray r1 = (android.util.LongSparseArray) r1     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x0010
            r2 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return r2
        L_0x0010:
            java.lang.Object r2 = r1.get(r6)     // Catch:{ all -> 0x0020 }
            android.view.accessibility.AccessibilityNodeInfo r2 = (android.view.accessibility.AccessibilityNodeInfo) r2     // Catch:{ all -> 0x0020 }
            if (r2 == 0) goto L_0x001e
            android.view.accessibility.AccessibilityNodeInfo r3 = new android.view.accessibility.AccessibilityNodeInfo     // Catch:{ all -> 0x0020 }
            r3.<init>(r2)     // Catch:{ all -> 0x0020 }
            r2 = r3
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return r2
        L_0x0020:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityCache.getNode(int, long):android.view.accessibility.AccessibilityNodeInfo");
    }

    public List<AccessibilityWindowInfo> getWindows() {
        synchronized (this.mLock) {
            if (!this.mIsAllWindowsCached) {
                return null;
            }
            int windowCount = this.mWindowCache.size();
            if (windowCount <= 0) {
                return null;
            }
            SparseArray<AccessibilityWindowInfo> sortedWindows = this.mTempWindowArray;
            sortedWindows.clear();
            for (int i = 0; i < windowCount; i++) {
                AccessibilityWindowInfo window = this.mWindowCache.valueAt(i);
                sortedWindows.put(window.getLayer(), window);
            }
            int sortedWindowCount = sortedWindows.size();
            List<AccessibilityWindowInfo> windows = new ArrayList<>(sortedWindowCount);
            for (int i2 = sortedWindowCount - 1; i2 >= 0; i2--) {
                windows.add(new AccessibilityWindowInfo(sortedWindows.valueAt(i2)));
                sortedWindows.removeAt(i2);
            }
            return windows;
        }
    }

    public AccessibilityWindowInfo getWindow(int windowId) {
        synchronized (this.mLock) {
            AccessibilityWindowInfo window = this.mWindowCache.get(windowId);
            if (window == null) {
                return null;
            }
            AccessibilityWindowInfo accessibilityWindowInfo = new AccessibilityWindowInfo(window);
            return accessibilityWindowInfo;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0095, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void add(android.view.accessibility.AccessibilityNodeInfo r13) {
        /*
            r12 = this;
            java.lang.Object r0 = r12.mLock
            monitor-enter(r0)
            int r1 = r13.getWindowId()     // Catch:{ all -> 0x0096 }
            android.util.SparseArray<android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo>> r2 = r12.mNodeCache     // Catch:{ all -> 0x0096 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0096 }
            android.util.LongSparseArray r2 = (android.util.LongSparseArray) r2     // Catch:{ all -> 0x0096 }
            if (r2 != 0) goto L_0x001c
            android.util.LongSparseArray r3 = new android.util.LongSparseArray     // Catch:{ all -> 0x0096 }
            r3.<init>()     // Catch:{ all -> 0x0096 }
            r2 = r3
            android.util.SparseArray<android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo>> r3 = r12.mNodeCache     // Catch:{ all -> 0x0096 }
            r3.put(r1, r2)     // Catch:{ all -> 0x0096 }
        L_0x001c:
            long r3 = r13.getSourceNodeId()     // Catch:{ all -> 0x0096 }
            java.lang.Object r5 = r2.get(r3)     // Catch:{ all -> 0x0096 }
            android.view.accessibility.AccessibilityNodeInfo r5 = (android.view.accessibility.AccessibilityNodeInfo) r5     // Catch:{ all -> 0x0096 }
            if (r5 == 0) goto L_0x005f
            android.util.LongArray r6 = r13.getChildNodeIds()     // Catch:{ all -> 0x0096 }
            int r7 = r5.getChildCount()     // Catch:{ all -> 0x0096 }
            r8 = 0
        L_0x0031:
            if (r8 >= r7) goto L_0x0050
            long r9 = r5.getChildId(r8)     // Catch:{ all -> 0x0096 }
            if (r6 == 0) goto L_0x003f
            int r11 = r6.indexOf(r9)     // Catch:{ all -> 0x0096 }
            if (r11 >= 0) goto L_0x0042
        L_0x003f:
            r12.clearSubTreeLocked(r1, r9)     // Catch:{ all -> 0x0096 }
        L_0x0042:
            java.lang.Object r11 = r2.get(r3)     // Catch:{ all -> 0x0096 }
            if (r11 != 0) goto L_0x004d
            r12.clearNodesForWindowLocked(r1)     // Catch:{ all -> 0x0096 }
            monitor-exit(r0)     // Catch:{ all -> 0x0096 }
            return
        L_0x004d:
            int r8 = r8 + 1
            goto L_0x0031
        L_0x0050:
            long r8 = r5.getParentNodeId()     // Catch:{ all -> 0x0096 }
            long r10 = r13.getParentNodeId()     // Catch:{ all -> 0x0096 }
            int r10 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r10 == 0) goto L_0x005f
            r12.clearSubTreeLocked(r1, r8)     // Catch:{ all -> 0x0096 }
        L_0x005f:
            android.view.accessibility.AccessibilityNodeInfo r6 = new android.view.accessibility.AccessibilityNodeInfo     // Catch:{ all -> 0x0096 }
            r6.<init>(r13)     // Catch:{ all -> 0x0096 }
            r2.put(r3, r6)     // Catch:{ all -> 0x0096 }
            boolean r7 = r6.isAccessibilityFocused()     // Catch:{ all -> 0x0096 }
            r8 = 2147483647(0x7fffffff, double:1.060997895E-314)
            if (r7 == 0) goto L_0x0084
            long r10 = r12.mAccessibilityFocus     // Catch:{ all -> 0x0096 }
            int r7 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r7 == 0) goto L_0x0081
            long r7 = r12.mAccessibilityFocus     // Catch:{ all -> 0x0096 }
            int r7 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r7 == 0) goto L_0x0081
            long r7 = r12.mAccessibilityFocus     // Catch:{ all -> 0x0096 }
            r12.refreshCachedNodeLocked(r1, r7)     // Catch:{ all -> 0x0096 }
        L_0x0081:
            r12.mAccessibilityFocus = r3     // Catch:{ all -> 0x0096 }
            goto L_0x008c
        L_0x0084:
            long r10 = r12.mAccessibilityFocus     // Catch:{ all -> 0x0096 }
            int r7 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r7 != 0) goto L_0x008c
            r12.mAccessibilityFocus = r8     // Catch:{ all -> 0x0096 }
        L_0x008c:
            boolean r7 = r6.isFocused()     // Catch:{ all -> 0x0096 }
            if (r7 == 0) goto L_0x0094
            r12.mInputFocus = r3     // Catch:{ all -> 0x0096 }
        L_0x0094:
            monitor-exit(r0)     // Catch:{ all -> 0x0096 }
            return
        L_0x0096:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0096 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityCache.add(android.view.accessibility.AccessibilityNodeInfo):void");
    }

    public void clear() {
        synchronized (this.mLock) {
            clearWindowCache();
            for (int i = this.mNodeCache.size() - 1; i >= 0; i--) {
                clearNodesForWindowLocked(this.mNodeCache.keyAt(i));
            }
            this.mAccessibilityFocus = 2147483647L;
            this.mInputFocus = 2147483647L;
        }
    }

    private void clearWindowCache() {
        this.mWindowCache.clear();
        this.mIsAllWindowsCached = false;
    }

    private void clearNodesForWindowLocked(int windowId) {
        if (this.mNodeCache.get(windowId) != null) {
            this.mNodeCache.remove(windowId);
        }
    }

    private void clearSubTreeLocked(int windowId, long rootNodeId) {
        LongSparseArray<AccessibilityNodeInfo> nodes = this.mNodeCache.get(windowId);
        if (nodes != null) {
            clearSubTreeRecursiveLocked(nodes, rootNodeId);
        }
    }

    private boolean clearSubTreeRecursiveLocked(LongSparseArray<AccessibilityNodeInfo> nodes, long rootNodeId) {
        AccessibilityNodeInfo current = nodes.get(rootNodeId);
        if (current == null) {
            clear();
            return true;
        }
        nodes.remove(rootNodeId);
        int childCount = current.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (clearSubTreeRecursiveLocked(nodes, current.getChildId(i))) {
                return true;
            }
        }
        return false;
    }

    public void checkIntegrity() {
        int nodesForWindowCount;
        AccessibilityWindowInfo activeWindow;
        int windowCount;
        AccessibilityWindowInfo focusedWindow;
        int nodesForWindowCount2;
        AccessibilityWindowInfo activeWindow2;
        int windowCount2;
        AccessibilityWindowInfo focusedWindow2;
        int childCount;
        AccessibilityNodeInfo accessFocus;
        int nodesForWindowCount3;
        boolean childOfItsParent;
        AccessibilityCache accessibilityCache = this;
        synchronized (accessibilityCache.mLock) {
            if (accessibilityCache.mWindowCache.size() > 0 || accessibilityCache.mNodeCache.size() != 0) {
                int windowCount3 = accessibilityCache.mWindowCache.size();
                AccessibilityWindowInfo activeWindow3 = null;
                AccessibilityWindowInfo focusedWindow3 = null;
                for (int i = 0; i < windowCount3; i++) {
                    AccessibilityWindowInfo window = accessibilityCache.mWindowCache.valueAt(i);
                    if (window.isActive()) {
                        if (activeWindow3 != null) {
                            Log.e(LOG_TAG, "Duplicate active window:" + window);
                        } else {
                            activeWindow3 = window;
                        }
                    }
                    if (window.isFocused()) {
                        if (focusedWindow3 != null) {
                            Log.e(LOG_TAG, "Duplicate focused window:" + window);
                        } else {
                            focusedWindow3 = window;
                        }
                    }
                }
                int nodesForWindowCount4 = accessibilityCache.mNodeCache.size();
                AccessibilityNodeInfo accessFocus2 = null;
                AccessibilityNodeInfo accessFocus3 = null;
                int i2 = 0;
                while (i2 < nodesForWindowCount4) {
                    LongSparseArray<AccessibilityNodeInfo> nodes = accessibilityCache.mNodeCache.valueAt(i2);
                    if (nodes.size() <= 0) {
                        focusedWindow = focusedWindow3;
                        windowCount = windowCount3;
                        activeWindow = activeWindow3;
                        nodesForWindowCount = nodesForWindowCount4;
                    } else {
                        ArraySet<AccessibilityNodeInfo> seen = new ArraySet<>();
                        int windowId = accessibilityCache.mNodeCache.keyAt(i2);
                        int nodeCount = nodes.size();
                        AccessibilityNodeInfo inputFocus = accessFocus2;
                        AccessibilityNodeInfo accessFocus4 = accessFocus3;
                        int j = 0;
                        while (j < nodeCount) {
                            AccessibilityNodeInfo node = nodes.valueAt(j);
                            if (!seen.add(node)) {
                                StringBuilder sb = new StringBuilder();
                                focusedWindow2 = focusedWindow3;
                                sb.append("Duplicate node: ");
                                sb.append(node);
                                sb.append(" in window:");
                                sb.append(windowId);
                                Log.e(LOG_TAG, sb.toString());
                                windowCount2 = windowCount3;
                                activeWindow2 = activeWindow3;
                                nodesForWindowCount2 = nodesForWindowCount4;
                            } else {
                                focusedWindow2 = focusedWindow3;
                                if (node.isAccessibilityFocused()) {
                                    if (accessFocus4 != null) {
                                        Log.e(LOG_TAG, "Duplicate accessibility focus:" + node + " in window:" + windowId);
                                    } else {
                                        accessFocus4 = node;
                                    }
                                }
                                if (node.isFocused()) {
                                    if (inputFocus != null) {
                                        Log.e(LOG_TAG, "Duplicate input focus: " + node + " in window:" + windowId);
                                    } else {
                                        inputFocus = node;
                                    }
                                }
                                windowCount2 = windowCount3;
                                AccessibilityNodeInfo nodeParent = nodes.get(node.getParentNodeId());
                                if (nodeParent != null) {
                                    boolean childOfItsParent2 = false;
                                    int childCount2 = nodeParent.getChildCount();
                                    int k = 0;
                                    while (true) {
                                        if (k >= childCount2) {
                                            childOfItsParent = childOfItsParent2;
                                            int i3 = childCount2;
                                            break;
                                        }
                                        boolean childOfItsParent3 = childOfItsParent2;
                                        int childCount3 = childCount2;
                                        if (nodes.get(nodeParent.getChildId(k)) == node) {
                                            childOfItsParent = true;
                                            break;
                                        }
                                        k++;
                                        childOfItsParent2 = childOfItsParent3;
                                        childCount2 = childCount3;
                                    }
                                    if (!childOfItsParent) {
                                        Log.e(LOG_TAG, "Invalid parent-child relation between parent: " + nodeParent + " and child: " + node);
                                    }
                                }
                                int childCount4 = node.getChildCount();
                                int k2 = 0;
                                while (k2 < childCount4) {
                                    AccessibilityWindowInfo activeWindow4 = activeWindow3;
                                    AccessibilityNodeInfo child = nodes.get(node.getChildId(k2));
                                    if (child != null) {
                                        nodesForWindowCount3 = nodesForWindowCount4;
                                        accessFocus = accessFocus4;
                                        if (nodes.get(child.getParentNodeId()) != node) {
                                            StringBuilder sb2 = new StringBuilder();
                                            childCount = childCount4;
                                            sb2.append("Invalid child-parent relation between child: ");
                                            sb2.append(node);
                                            sb2.append(" and parent: ");
                                            sb2.append(nodeParent);
                                            Log.e(LOG_TAG, sb2.toString());
                                        } else {
                                            childCount = childCount4;
                                        }
                                    } else {
                                        childCount = childCount4;
                                        nodesForWindowCount3 = nodesForWindowCount4;
                                        accessFocus = accessFocus4;
                                    }
                                    k2++;
                                    activeWindow3 = activeWindow4;
                                    nodesForWindowCount4 = nodesForWindowCount3;
                                    accessFocus4 = accessFocus;
                                    childCount4 = childCount;
                                }
                                activeWindow2 = activeWindow3;
                                nodesForWindowCount2 = nodesForWindowCount4;
                                AccessibilityNodeInfo accessibilityNodeInfo = accessFocus4;
                            }
                            j++;
                            focusedWindow3 = focusedWindow2;
                            windowCount3 = windowCount2;
                            activeWindow3 = activeWindow2;
                            nodesForWindowCount4 = nodesForWindowCount2;
                        }
                        focusedWindow = focusedWindow3;
                        windowCount = windowCount3;
                        activeWindow = activeWindow3;
                        nodesForWindowCount = nodesForWindowCount4;
                        accessFocus3 = accessFocus4;
                        accessFocus2 = inputFocus;
                    }
                    i2++;
                    focusedWindow3 = focusedWindow;
                    windowCount3 = windowCount;
                    activeWindow3 = activeWindow;
                    nodesForWindowCount4 = nodesForWindowCount;
                    accessibilityCache = this;
                }
            }
        }
    }

    public static class AccessibilityNodeRefresher {
        public boolean refreshNode(AccessibilityNodeInfo info, boolean bypassCache) {
            return info.refresh((Bundle) null, bypassCache);
        }
    }
}
