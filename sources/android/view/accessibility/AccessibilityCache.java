package android.view.accessibility;

import android.p007os.Build;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.LongSparseArray;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class AccessibilityCache {
    public static final int CACHE_CRITICAL_EVENTS_MASK = 4307005;
    private static final boolean CHECK_INTEGRITY = Build.IS_ENG;
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "AccessibilityCache";
    private final AccessibilityNodeRefresher mAccessibilityNodeRefresher;
    private boolean mIsAllWindowsCached;
    private final Object mLock = new Object();
    private long mAccessibilityFocus = 2147483647L;
    private long mInputFocus = 2147483647L;
    private final SparseArray<AccessibilityWindowInfo> mWindowCache = new SparseArray<>();
    private final SparseArray<LongSparseArray<AccessibilityNodeInfo>> mNodeCache = new SparseArray<>();
    private final SparseArray<AccessibilityWindowInfo> mTempWindowArray = new SparseArray<>();

    public AccessibilityCache(AccessibilityNodeRefresher nodeRefresher) {
        this.mAccessibilityNodeRefresher = nodeRefresher;
    }

    public void setWindows(List<AccessibilityWindowInfo> windows) {
        synchronized (this.mLock) {
            clearWindowCache();
            if (windows == null) {
                return;
            }
            int windowCount = windows.size();
            for (int i = 0; i < windowCount; i++) {
                AccessibilityWindowInfo window = windows.get(i);
                addWindow(window);
            }
            this.mIsAllWindowsCached = true;
        }
    }

    public void addWindow(AccessibilityWindowInfo window) {
        synchronized (this.mLock) {
            int windowId = window.getId();
            this.mWindowCache.put(windowId, new AccessibilityWindowInfo(window));
        }
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        synchronized (this.mLock) {
            int eventType = event.getEventType();
            switch (eventType) {
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
        if (nodes == null || (cachedInfo = nodes.get(sourceId)) == null || this.mAccessibilityNodeRefresher.refreshNode(cachedInfo, true)) {
            return;
        }
        clearSubTreeLocked(windowId, sourceId);
    }

    public AccessibilityNodeInfo getNode(int windowId, long accessibilityNodeId) {
        synchronized (this.mLock) {
            LongSparseArray<AccessibilityNodeInfo> nodes = this.mNodeCache.get(windowId);
            if (nodes == null) {
                return null;
            }
            AccessibilityNodeInfo info = nodes.get(accessibilityNodeId);
            if (info != null) {
                info = new AccessibilityNodeInfo(info);
            }
            return info;
        }
    }

    public List<AccessibilityWindowInfo> getWindows() {
        synchronized (this.mLock) {
            if (this.mIsAllWindowsCached) {
                int windowCount = this.mWindowCache.size();
                if (windowCount > 0) {
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
                return null;
            }
            return null;
        }
    }

    public AccessibilityWindowInfo getWindow(int windowId) {
        synchronized (this.mLock) {
            AccessibilityWindowInfo window = this.mWindowCache.get(windowId);
            if (window != null) {
                return new AccessibilityWindowInfo(window);
            }
            return null;
        }
    }

    public void add(AccessibilityNodeInfo info) {
        synchronized (this.mLock) {
            int windowId = info.getWindowId();
            LongSparseArray<AccessibilityNodeInfo> nodes = this.mNodeCache.get(windowId);
            if (nodes == null) {
                nodes = new LongSparseArray<>();
                this.mNodeCache.put(windowId, nodes);
            }
            long sourceId = info.getSourceNodeId();
            AccessibilityNodeInfo oldInfo = nodes.get(sourceId);
            if (oldInfo != null) {
                LongArray newChildrenIds = info.getChildNodeIds();
                int oldChildCount = oldInfo.getChildCount();
                for (int i = 0; i < oldChildCount; i++) {
                    long oldChildId = oldInfo.getChildId(i);
                    if (newChildrenIds == null || newChildrenIds.indexOf(oldChildId) < 0) {
                        clearSubTreeLocked(windowId, oldChildId);
                    }
                    if (nodes.get(sourceId) == null) {
                        clearNodesForWindowLocked(windowId);
                        return;
                    }
                }
                long oldParentId = oldInfo.getParentNodeId();
                if (info.getParentNodeId() != oldParentId) {
                    clearSubTreeLocked(windowId, oldParentId);
                }
            }
            AccessibilityNodeInfo clone = new AccessibilityNodeInfo(info);
            nodes.put(sourceId, clone);
            if (clone.isAccessibilityFocused()) {
                if (this.mAccessibilityFocus != 2147483647L && this.mAccessibilityFocus != sourceId) {
                    refreshCachedNodeLocked(windowId, this.mAccessibilityFocus);
                }
                this.mAccessibilityFocus = sourceId;
            } else if (this.mAccessibilityFocus == sourceId) {
                this.mAccessibilityFocus = 2147483647L;
            }
            if (clone.isFocused()) {
                this.mInputFocus = sourceId;
            }
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            clearWindowCache();
            int nodesForWindowCount = this.mNodeCache.size();
            for (int i = nodesForWindowCount - 1; i >= 0; i--) {
                int windowId = this.mNodeCache.keyAt(i);
                clearNodesForWindowLocked(windowId);
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
        LongSparseArray<AccessibilityNodeInfo> nodes = this.mNodeCache.get(windowId);
        if (nodes == null) {
            return;
        }
        this.mNodeCache.remove(windowId);
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
            long childNodeId = current.getChildId(i);
            if (clearSubTreeRecursiveLocked(nodes, childNodeId)) {
                return true;
            }
        }
        return false;
    }

    public void checkIntegrity() {
        AccessibilityWindowInfo focusedWindow;
        int windowCount;
        AccessibilityWindowInfo activeWindow;
        int nodesForWindowCount;
        AccessibilityWindowInfo focusedWindow2;
        int windowCount2;
        AccessibilityWindowInfo activeWindow2;
        int nodesForWindowCount2;
        int childCount;
        int nodesForWindowCount3;
        AccessibilityNodeInfo accessFocus;
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
                            Log.m70e(LOG_TAG, "Duplicate active window:" + window);
                        } else {
                            activeWindow3 = window;
                        }
                    }
                    if (window.isFocused()) {
                        if (focusedWindow3 != null) {
                            Log.m70e(LOG_TAG, "Duplicate focused window:" + window);
                        } else {
                            focusedWindow3 = window;
                        }
                    }
                }
                int nodesForWindowCount4 = accessibilityCache.mNodeCache.size();
                AccessibilityNodeInfo inputFocus = null;
                AccessibilityNodeInfo inputFocus2 = null;
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
                        AccessibilityNodeInfo inputFocus3 = inputFocus;
                        AccessibilityNodeInfo inputFocus4 = inputFocus2;
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
                                Log.m70e(LOG_TAG, sb.toString());
                                windowCount2 = windowCount3;
                                activeWindow2 = activeWindow3;
                                nodesForWindowCount2 = nodesForWindowCount4;
                            } else {
                                focusedWindow2 = focusedWindow3;
                                if (node.isAccessibilityFocused()) {
                                    if (inputFocus4 != null) {
                                        Log.m70e(LOG_TAG, "Duplicate accessibility focus:" + node + " in window:" + windowId);
                                    } else {
                                        inputFocus4 = node;
                                    }
                                }
                                if (node.isFocused()) {
                                    if (inputFocus3 != null) {
                                        Log.m70e(LOG_TAG, "Duplicate input focus: " + node + " in window:" + windowId);
                                    } else {
                                        inputFocus3 = node;
                                    }
                                }
                                windowCount2 = windowCount3;
                                AccessibilityNodeInfo nodeParent = nodes.get(node.getParentNodeId());
                                if (nodeParent != null) {
                                    boolean childOfItsParent2 = false;
                                    int childCount2 = nodeParent.getChildCount();
                                    int k = 0;
                                    while (true) {
                                        if (k < childCount2) {
                                            boolean childOfItsParent3 = childOfItsParent2;
                                            int childCount3 = childCount2;
                                            if (nodes.get(nodeParent.getChildId(k)) != node) {
                                                k++;
                                                childOfItsParent2 = childOfItsParent3;
                                                childCount2 = childCount3;
                                            } else {
                                                childOfItsParent = true;
                                                break;
                                            }
                                        } else {
                                            childOfItsParent = childOfItsParent2;
                                            break;
                                        }
                                    }
                                    if (!childOfItsParent) {
                                        Log.m70e(LOG_TAG, "Invalid parent-child relation between parent: " + nodeParent + " and child: " + node);
                                    }
                                }
                                int childCount4 = node.getChildCount();
                                int k2 = 0;
                                while (k2 < childCount4) {
                                    AccessibilityWindowInfo activeWindow4 = activeWindow3;
                                    AccessibilityNodeInfo child = nodes.get(node.getChildId(k2));
                                    if (child != null) {
                                        nodesForWindowCount3 = nodesForWindowCount4;
                                        accessFocus = inputFocus4;
                                        AccessibilityNodeInfo parent = nodes.get(child.getParentNodeId());
                                        if (parent == node) {
                                            childCount = childCount4;
                                        } else {
                                            StringBuilder sb2 = new StringBuilder();
                                            childCount = childCount4;
                                            sb2.append("Invalid child-parent relation between child: ");
                                            sb2.append(node);
                                            sb2.append(" and parent: ");
                                            sb2.append(nodeParent);
                                            Log.m70e(LOG_TAG, sb2.toString());
                                        }
                                    } else {
                                        childCount = childCount4;
                                        nodesForWindowCount3 = nodesForWindowCount4;
                                        accessFocus = inputFocus4;
                                    }
                                    k2++;
                                    activeWindow3 = activeWindow4;
                                    nodesForWindowCount4 = nodesForWindowCount3;
                                    inputFocus4 = accessFocus;
                                    childCount4 = childCount;
                                }
                                activeWindow2 = activeWindow3;
                                nodesForWindowCount2 = nodesForWindowCount4;
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
                        inputFocus2 = inputFocus4;
                        inputFocus = inputFocus3;
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

    /* loaded from: classes4.dex */
    public static class AccessibilityNodeRefresher {
        public boolean refreshNode(AccessibilityNodeInfo info, boolean bypassCache) {
            return info.refresh(null, bypassCache);
        }
    }
}
