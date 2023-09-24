package android.support.p011v4.view.accessibility;

import android.graphics.Rect;
import android.p007os.Build;
import android.p007os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompatKitKat;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat */
/* loaded from: classes3.dex */
public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    static final AccessibilityNodeInfoBaseImpl IMPL;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private final AccessibilityNodeInfo mInfo;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int mParentVirtualDescendantId = -1;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat */
    /* loaded from: classes3.dex */
    public static class AccessibilityActionCompat {
        final Object mAction;
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, null);
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionShowOnScreen());
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollToPosition());
        public static final AccessibilityActionCompat ACTION_SCROLL_UP = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollUp());
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollLeft());
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollDown());
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollRight());
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionContextClick());
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionSetProgress());

        public AccessibilityActionCompat(int actionId, CharSequence label) {
            this(AccessibilityNodeInfoCompat.IMPL.newAccessibilityAction(actionId, label));
        }

        AccessibilityActionCompat(Object action) {
            this.mAction = action;
        }

        public int getId() {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionId(this.mAction);
        }

        public CharSequence getLabel() {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionLabel(this.mAction);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionInfoCompat */
    /* loaded from: classes3.dex */
    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo(rowCount, columnCount, hierarchical, selectionMode));
        }

        public static CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical) {
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo(rowCount, columnCount, hierarchical));
        }

        CollectionInfoCompat(Object info) {
            this.mInfo = info;
        }

        public int getColumnCount() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoColumnCount(this.mInfo);
        }

        public int getRowCount() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoRowCount(this.mInfo);
        }

        public boolean isHierarchical() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionInfoHierarchical(this.mInfo);
        }

        public int getSelectionMode() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoSelectionMode(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionItemInfoCompat */
    /* loaded from: classes3.dex */
    public static class CollectionItemInfoCompat {
        final Object mInfo;

        public static CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, heading, selected));
        }

        public static CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, heading));
        }

        CollectionItemInfoCompat(Object info) {
            this.mInfo = info;
        }

        public int getColumnIndex() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnIndex(this.mInfo);
        }

        public int getColumnSpan() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnSpan(this.mInfo);
        }

        public int getRowIndex() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowIndex(this.mInfo);
        }

        public int getRowSpan() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowSpan(this.mInfo);
        }

        public boolean isHeading() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemHeading(this.mInfo);
        }

        public boolean isSelected() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemSelected(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$RangeInfoCompat */
    /* loaded from: classes3.dex */
    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        public static RangeInfoCompat obtain(int type, float min, float max, float current) {
            return new RangeInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainRangeInfo(type, min, max, current));
        }

        RangeInfoCompat(Object info) {
            this.mInfo = info;
        }

        public float getCurrent() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getCurrent(this.mInfo);
        }

        public float getMax() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getMax(this.mInfo);
        }

        public float getMin() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getMin(this.mInfo);
        }

        public int getType() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getType(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoBaseImpl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoBaseImpl {
        AccessibilityNodeInfoBaseImpl() {
        }

        public Object newAccessibilityAction(int actionId, CharSequence label) {
            return null;
        }

        public AccessibilityNodeInfo obtain(View root, int virtualDescendantId) {
            return null;
        }

        public void addAction(AccessibilityNodeInfo info, Object action) {
        }

        public boolean removeAction(AccessibilityNodeInfo info, Object action) {
            return false;
        }

        public int getAccessibilityActionId(Object action) {
            return 0;
        }

        public CharSequence getAccessibilityActionLabel(Object action) {
            return null;
        }

        public void addChild(AccessibilityNodeInfo info, View child, int virtualDescendantId) {
        }

        public boolean removeChild(AccessibilityNodeInfo info, View child) {
            return false;
        }

        public boolean removeChild(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            return false;
        }

        public boolean isVisibleToUser(AccessibilityNodeInfo info) {
            return false;
        }

        public boolean isAccessibilityFocused(AccessibilityNodeInfo info) {
            return false;
        }

        public boolean performAction(AccessibilityNodeInfo info, int action, Bundle arguments) {
            return false;
        }

        public void setMovementGranularities(AccessibilityNodeInfo info, int granularities) {
        }

        public int getMovementGranularities(AccessibilityNodeInfo info) {
            return 0;
        }

        public void setVisibleToUser(AccessibilityNodeInfo info, boolean visibleToUser) {
        }

        public void setAccessibilityFocused(AccessibilityNodeInfo info, boolean focused) {
        }

        public void setSource(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
        }

        public Object findFocus(AccessibilityNodeInfo info, int focus) {
            return null;
        }

        public Object focusSearch(AccessibilityNodeInfo info, int direction) {
            return null;
        }

        public void setParent(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
        }

        public String getViewIdResourceName(AccessibilityNodeInfo info) {
            return null;
        }

        public void setViewIdResourceName(AccessibilityNodeInfo info, String viewId) {
        }

        public int getLiveRegion(AccessibilityNodeInfo info) {
            return 0;
        }

        public void setLiveRegion(AccessibilityNodeInfo info, int mode) {
        }

        public Object getCollectionInfo(AccessibilityNodeInfo info) {
            return null;
        }

        public void setCollectionInfo(AccessibilityNodeInfo info, Object collectionInfo) {
        }

        public Object getCollectionItemInfo(AccessibilityNodeInfo info) {
            return null;
        }

        public void setCollectionItemInfo(AccessibilityNodeInfo info, Object collectionItemInfo) {
        }

        public Object getRangeInfo(AccessibilityNodeInfo info) {
            return null;
        }

        public void setRangeInfo(AccessibilityNodeInfo info, Object rangeInfo) {
        }

        public List<Object> getActionList(AccessibilityNodeInfo info) {
            return null;
        }

        public Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            return null;
        }

        public Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical) {
            return null;
        }

        public int getCollectionInfoColumnCount(Object info) {
            return 0;
        }

        public int getCollectionInfoRowCount(Object info) {
            return 0;
        }

        public boolean isCollectionInfoHierarchical(Object info) {
            return false;
        }

        public Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            return null;
        }

        public Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            return null;
        }

        public int getCollectionItemColumnIndex(Object info) {
            return 0;
        }

        public int getCollectionItemColumnSpan(Object info) {
            return 0;
        }

        public int getCollectionItemRowIndex(Object info) {
            return 0;
        }

        public int getCollectionItemRowSpan(Object info) {
            return 0;
        }

        public boolean isCollectionItemHeading(Object info) {
            return false;
        }

        public boolean isCollectionItemSelected(Object info) {
            return false;
        }

        public Object obtainRangeInfo(int type, float min, float max, float current) {
            return null;
        }

        public Object getTraversalBefore(AccessibilityNodeInfo info) {
            return null;
        }

        public void setTraversalBefore(AccessibilityNodeInfo info, View view) {
        }

        public void setTraversalBefore(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
        }

        public Object getTraversalAfter(AccessibilityNodeInfo info) {
            return null;
        }

        public void setTraversalAfter(AccessibilityNodeInfo info, View view) {
        }

        public void setTraversalAfter(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
        }

        public void setContentInvalid(AccessibilityNodeInfo info, boolean contentInvalid) {
        }

        public boolean isContentInvalid(AccessibilityNodeInfo info) {
            return false;
        }

        public void setError(AccessibilityNodeInfo info, CharSequence error) {
        }

        public CharSequence getError(AccessibilityNodeInfo info) {
            return null;
        }

        public void setLabelFor(AccessibilityNodeInfo info, View labeled) {
        }

        public void setLabelFor(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
        }

        public Object getLabelFor(AccessibilityNodeInfo info) {
            return null;
        }

        public void setLabeledBy(AccessibilityNodeInfo info, View labeled) {
        }

        public void setLabeledBy(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
        }

        public Object getLabeledBy(AccessibilityNodeInfo info) {
            return null;
        }

        public boolean canOpenPopup(AccessibilityNodeInfo info) {
            return false;
        }

        public void setCanOpenPopup(AccessibilityNodeInfo info, boolean opensPopup) {
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityNodeInfo info, String viewId) {
            return Collections.emptyList();
        }

        public Bundle getExtras(AccessibilityNodeInfo info) {
            return new Bundle();
        }

        public int getInputType(AccessibilityNodeInfo info) {
            return 0;
        }

        public void setInputType(AccessibilityNodeInfo info, int inputType) {
        }

        public void setMaxTextLength(AccessibilityNodeInfo info, int max) {
        }

        public int getMaxTextLength(AccessibilityNodeInfo info) {
            return -1;
        }

        public void setTextSelection(AccessibilityNodeInfo info, int start, int end) {
        }

        public int getTextSelectionStart(AccessibilityNodeInfo info) {
            return -1;
        }

        public int getTextSelectionEnd(AccessibilityNodeInfo info) {
            return -1;
        }

        public Object getWindow(AccessibilityNodeInfo info) {
            return null;
        }

        public boolean isDismissable(AccessibilityNodeInfo info) {
            return false;
        }

        public void setDismissable(AccessibilityNodeInfo info, boolean dismissable) {
        }

        public boolean isEditable(AccessibilityNodeInfo info) {
            return false;
        }

        public void setEditable(AccessibilityNodeInfo info, boolean editable) {
        }

        public boolean isMultiLine(AccessibilityNodeInfo info) {
            return false;
        }

        public void setMultiLine(AccessibilityNodeInfo info, boolean multiLine) {
        }

        public boolean refresh(AccessibilityNodeInfo info) {
            return false;
        }

        public CharSequence getRoleDescription(AccessibilityNodeInfo info) {
            return null;
        }

        public void setRoleDescription(AccessibilityNodeInfo info, CharSequence roleDescription) {
        }

        public Object getActionScrollToPosition() {
            return null;
        }

        public Object getActionSetProgress() {
            return null;
        }

        public boolean isContextClickable(AccessibilityNodeInfo info) {
            return false;
        }

        public void setContextClickable(AccessibilityNodeInfo info, boolean contextClickable) {
        }

        public Object getActionShowOnScreen() {
            return null;
        }

        public Object getActionScrollUp() {
            return null;
        }

        public Object getActionScrollDown() {
            return null;
        }

        public Object getActionScrollLeft() {
            return null;
        }

        public Object getActionScrollRight() {
            return null;
        }

        public Object getActionContextClick() {
            return null;
        }

        public int getCollectionInfoSelectionMode(Object info) {
            return 0;
        }

        public int getDrawingOrder(AccessibilityNodeInfo info) {
            return 0;
        }

        public void setDrawingOrder(AccessibilityNodeInfo info, int drawingOrderInParent) {
        }

        public boolean isImportantForAccessibility(AccessibilityNodeInfo info) {
            return true;
        }

        public void setImportantForAccessibility(AccessibilityNodeInfo info, boolean importantForAccessibility) {
        }
    }

    @RequiresApi(16)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi16Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi16Impl extends AccessibilityNodeInfoBaseImpl {
        AccessibilityNodeInfoApi16Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public AccessibilityNodeInfo obtain(View root, int virtualDescendantId) {
            return AccessibilityNodeInfo.obtain(root, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object findFocus(AccessibilityNodeInfo info, int focus) {
            return info.findFocus(focus);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object focusSearch(AccessibilityNodeInfo info, int direction) {
            return info.focusSearch(direction);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void addChild(AccessibilityNodeInfo info, View child, int virtualDescendantId) {
            info.addChild(child, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setSource(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            info.setSource(root, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isVisibleToUser(AccessibilityNodeInfo info) {
            return info.isVisibleToUser();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setVisibleToUser(AccessibilityNodeInfo info, boolean visibleToUser) {
            info.setVisibleToUser(visibleToUser);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isAccessibilityFocused(AccessibilityNodeInfo info) {
            return info.isAccessibilityFocused();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setAccessibilityFocused(AccessibilityNodeInfo info, boolean focused) {
            info.setAccessibilityFocused(focused);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean performAction(AccessibilityNodeInfo info, int action, Bundle arguments) {
            return info.performAction(action, arguments);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setMovementGranularities(AccessibilityNodeInfo info, int granularities) {
            info.setMovementGranularities(granularities);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getMovementGranularities(AccessibilityNodeInfo info) {
            return info.getMovementGranularities();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setParent(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            info.setParent(root, virtualDescendantId);
        }
    }

    @RequiresApi(17)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi17Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi17Impl extends AccessibilityNodeInfoApi16Impl {
        AccessibilityNodeInfoApi17Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setLabelFor(AccessibilityNodeInfo info, View labeled) {
            info.setLabelFor(labeled);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setLabelFor(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            info.setLabelFor(root, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getLabelFor(AccessibilityNodeInfo info) {
            return info.getLabelFor();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setLabeledBy(AccessibilityNodeInfo info, View labeled) {
            info.setLabeledBy(labeled);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setLabeledBy(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            info.setLabeledBy(root, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getLabeledBy(AccessibilityNodeInfo info) {
            return info.getLabeledBy();
        }
    }

    @RequiresApi(18)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi18Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi18Impl extends AccessibilityNodeInfoApi17Impl {
        AccessibilityNodeInfoApi18Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public String getViewIdResourceName(AccessibilityNodeInfo info) {
            return info.getViewIdResourceName();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setViewIdResourceName(AccessibilityNodeInfo info, String viewId) {
            info.setViewIdResourceName(viewId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityNodeInfo info, String viewId) {
            return info.findAccessibilityNodeInfosByViewId(viewId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setTextSelection(AccessibilityNodeInfo info, int start, int end) {
            info.setTextSelection(start, end);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getTextSelectionStart(AccessibilityNodeInfo info) {
            return info.getTextSelectionStart();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getTextSelectionEnd(AccessibilityNodeInfo info) {
            return info.getTextSelectionEnd();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isEditable(AccessibilityNodeInfo info) {
            return info.isEditable();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setEditable(AccessibilityNodeInfo info, boolean editable) {
            info.setEditable(editable);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean refresh(AccessibilityNodeInfo info) {
            return info.refresh();
        }
    }

    @RequiresApi(19)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi19Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi19Impl extends AccessibilityNodeInfoApi18Impl {
        private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";

        AccessibilityNodeInfoApi19Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getLiveRegion(AccessibilityNodeInfo info) {
            return info.getLiveRegion();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setLiveRegion(AccessibilityNodeInfo info, int mode) {
            info.setLiveRegion(mode);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getCollectionInfo(AccessibilityNodeInfo info) {
            return info.getCollectionInfo();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setCollectionInfo(AccessibilityNodeInfo info, Object collectionInfo) {
            info.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) collectionInfo);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            return AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical) {
            return AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionInfoColumnCount(Object info) {
            return ((AccessibilityNodeInfo.CollectionInfo) info).getColumnCount();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionInfoRowCount(Object info) {
            return ((AccessibilityNodeInfo.CollectionInfo) info).getRowCount();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isCollectionInfoHierarchical(Object info) {
            return ((AccessibilityNodeInfo.CollectionInfo) info).isHierarchical();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getCollectionItemInfo(AccessibilityNodeInfo info) {
            return info.getCollectionItemInfo();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getRangeInfo(AccessibilityNodeInfo info) {
            return info.getRangeInfo();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setRangeInfo(AccessibilityNodeInfo info, Object rangeInfo) {
            info.setRangeInfo((AccessibilityNodeInfo.RangeInfo) rangeInfo);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionItemColumnIndex(Object info) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) info).getColumnIndex();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionItemColumnSpan(Object info) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) info).getColumnSpan();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionItemRowIndex(Object info) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) info).getRowIndex();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionItemRowSpan(Object info) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) info).getRowSpan();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isCollectionItemHeading(Object info) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) info).isHeading();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setCollectionItemInfo(AccessibilityNodeInfo info, Object collectionItemInfo) {
            info.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) collectionItemInfo);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainRangeInfo(int type, float min, float max, float current) {
            return AccessibilityNodeInfo.RangeInfo.obtain(type, min, max, current);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setContentInvalid(AccessibilityNodeInfo info, boolean contentInvalid) {
            info.setContentInvalid(contentInvalid);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isContentInvalid(AccessibilityNodeInfo info) {
            return info.isContentInvalid();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean canOpenPopup(AccessibilityNodeInfo info) {
            return info.canOpenPopup();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setCanOpenPopup(AccessibilityNodeInfo info, boolean opensPopup) {
            info.setCanOpenPopup(opensPopup);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Bundle getExtras(AccessibilityNodeInfo info) {
            return info.getExtras();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getInputType(AccessibilityNodeInfo info) {
            return info.getInputType();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setInputType(AccessibilityNodeInfo info, int inputType) {
            info.setInputType(inputType);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isDismissable(AccessibilityNodeInfo info) {
            return info.isDismissable();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setDismissable(AccessibilityNodeInfo info, boolean dismissable) {
            info.setDismissable(dismissable);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isMultiLine(AccessibilityNodeInfo info) {
            return info.isMultiLine();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setMultiLine(AccessibilityNodeInfo info, boolean multiLine) {
            info.setMultiLine(multiLine);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public CharSequence getRoleDescription(AccessibilityNodeInfo info) {
            Bundle extras = getExtras(info);
            return extras.getCharSequence(ROLE_DESCRIPTION_KEY);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setRoleDescription(AccessibilityNodeInfo info, CharSequence roleDescription) {
            Bundle extras = getExtras(info);
            extras.putCharSequence(ROLE_DESCRIPTION_KEY, roleDescription);
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi21Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi21Impl extends AccessibilityNodeInfoApi19Impl {
        AccessibilityNodeInfoApi21Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object newAccessibilityAction(int actionId, CharSequence label) {
            return new AccessibilityNodeInfo.AccessibilityAction(actionId, label);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public List<Object> getActionList(AccessibilityNodeInfo info) {
            Object result = info.getActionList();
            return (List) result;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoApi19Impl, android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            return AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical, selectionMode);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void addAction(AccessibilityNodeInfo info, Object action) {
            info.addAction((AccessibilityNodeInfo.AccessibilityAction) action);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean removeAction(AccessibilityNodeInfo info, Object action) {
            return info.removeAction((AccessibilityNodeInfo.AccessibilityAction) action);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getAccessibilityActionId(Object action) {
            return ((AccessibilityNodeInfo.AccessibilityAction) action).getId();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public CharSequence getAccessibilityActionLabel(Object action) {
            return ((AccessibilityNodeInfo.AccessibilityAction) action).getLabel();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoApi19Impl, android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading, selected);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isCollectionItemSelected(Object info) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) info).isSelected();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public CharSequence getError(AccessibilityNodeInfo info) {
            return info.getError();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setError(AccessibilityNodeInfo info, CharSequence error) {
            info.setError(error);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setMaxTextLength(AccessibilityNodeInfo info, int max) {
            info.setMaxTextLength(max);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getMaxTextLength(AccessibilityNodeInfo info) {
            return info.getMaxTextLength();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getWindow(AccessibilityNodeInfo info) {
            return info.getWindow();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean removeChild(AccessibilityNodeInfo info, View child) {
            return info.removeChild(child);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean removeChild(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            return info.removeChild(root, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getCollectionInfoSelectionMode(Object info) {
            return ((AccessibilityNodeInfo.CollectionInfo) info).getSelectionMode();
        }
    }

    @RequiresApi(22)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi22Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi22Impl extends AccessibilityNodeInfoApi21Impl {
        AccessibilityNodeInfoApi22Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getTraversalBefore(AccessibilityNodeInfo info) {
            return info.getTraversalBefore();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setTraversalBefore(AccessibilityNodeInfo info, View view) {
            info.setTraversalBefore(view);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setTraversalBefore(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            info.setTraversalBefore(root, virtualDescendantId);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getTraversalAfter(AccessibilityNodeInfo info) {
            return info.getTraversalAfter();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setTraversalAfter(AccessibilityNodeInfo info, View view) {
            info.setTraversalAfter(view);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setTraversalAfter(AccessibilityNodeInfo info, View root, int virtualDescendantId) {
            info.setTraversalAfter(root, virtualDescendantId);
        }
    }

    @RequiresApi(23)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi23Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi23Impl extends AccessibilityNodeInfoApi22Impl {
        AccessibilityNodeInfoApi23Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionScrollToPosition() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionShowOnScreen() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionScrollUp() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionScrollDown() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionScrollLeft() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionScrollRight() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionContextClick() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isContextClickable(AccessibilityNodeInfo info) {
            return info.isContextClickable();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setContextClickable(AccessibilityNodeInfo info, boolean contextClickable) {
            info.setContextClickable(contextClickable);
        }
    }

    @RequiresApi(24)
    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi24Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityNodeInfoApi24Impl extends AccessibilityNodeInfoApi23Impl {
        AccessibilityNodeInfoApi24Impl() {
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public Object getActionSetProgress() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public int getDrawingOrder(AccessibilityNodeInfo info) {
            return info.getDrawingOrder();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setDrawingOrder(AccessibilityNodeInfo info, int drawingOrderInParent) {
            info.setDrawingOrder(drawingOrderInParent);
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public boolean isImportantForAccessibility(AccessibilityNodeInfo info) {
            return info.isImportantForAccessibility();
        }

        @Override // android.support.p011v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoBaseImpl
        public void setImportantForAccessibility(AccessibilityNodeInfo info, boolean importantForAccessibility) {
            info.setImportantForAccessibility(importantForAccessibility);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new AccessibilityNodeInfoApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new AccessibilityNodeInfoApi23Impl();
        } else if (Build.VERSION.SDK_INT >= 22) {
            IMPL = new AccessibilityNodeInfoApi22Impl();
        } else if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityNodeInfoApi21Impl();
        } else if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeInfoApi19Impl();
        } else if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityNodeInfoApi18Impl();
        } else if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new AccessibilityNodeInfoApi17Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeInfoApi16Impl();
        } else {
            IMPL = new AccessibilityNodeInfoBaseImpl();
        }
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
        if (object != null) {
            return new AccessibilityNodeInfoCompat(object);
        }
        return null;
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object info) {
        this.mInfo = (AccessibilityNodeInfo) info;
    }

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo info) {
        this.mInfo = info;
    }

    public static AccessibilityNodeInfoCompat wrap(@NonNull AccessibilityNodeInfo info) {
        return new AccessibilityNodeInfoCompat(info);
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    @Deprecated
    public Object getInfo() {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View source) {
        return wrap(AccessibilityNodeInfo.obtain(source));
    }

    public static AccessibilityNodeInfoCompat obtain(View root, int virtualDescendantId) {
        return wrapNonNullInstance(IMPL.obtain(root, virtualDescendantId));
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat info) {
        return wrap(AccessibilityNodeInfo.obtain(info.mInfo));
    }

    public void setSource(View source) {
        this.mInfo.setSource(source);
    }

    public void setSource(View root, int virtualDescendantId) {
        IMPL.setSource(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat findFocus(int focus) {
        return wrapNonNullInstance(IMPL.findFocus(this.mInfo, focus));
    }

    public AccessibilityNodeInfoCompat focusSearch(int direction) {
        return wrapNonNullInstance(IMPL.focusSearch(this.mInfo, direction));
    }

    public int getWindowId() {
        return this.mInfo.getWindowId();
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public AccessibilityNodeInfoCompat getChild(int index) {
        return wrapNonNullInstance(this.mInfo.getChild(index));
    }

    public void addChild(View child) {
        this.mInfo.addChild(child);
    }

    public void addChild(View root, int virtualDescendantId) {
        IMPL.addChild(this.mInfo, root, virtualDescendantId);
    }

    public boolean removeChild(View child) {
        return IMPL.removeChild(this.mInfo, child);
    }

    public boolean removeChild(View root, int virtualDescendantId) {
        return IMPL.removeChild(this.mInfo, root, virtualDescendantId);
    }

    public int getActions() {
        return this.mInfo.getActions();
    }

    public void addAction(int action) {
        this.mInfo.addAction(action);
    }

    public void addAction(AccessibilityActionCompat action) {
        IMPL.addAction(this.mInfo, action.mAction);
    }

    public boolean removeAction(AccessibilityActionCompat action) {
        return IMPL.removeAction(this.mInfo, action.mAction);
    }

    public boolean performAction(int action) {
        return this.mInfo.performAction(action);
    }

    public boolean performAction(int action, Bundle arguments) {
        return IMPL.performAction(this.mInfo, action, arguments);
    }

    public void setMovementGranularities(int granularities) {
        IMPL.setMovementGranularities(this.mInfo, granularities);
    }

    public int getMovementGranularities() {
        return IMPL.getMovementGranularities(this.mInfo);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text) {
        List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
        List<AccessibilityNodeInfo> infos = this.mInfo.findAccessibilityNodeInfosByText(text);
        int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            AccessibilityNodeInfo info = infos.get(i);
            result.add(wrap(info));
        }
        return result;
    }

    public AccessibilityNodeInfoCompat getParent() {
        return wrapNonNullInstance(this.mInfo.getParent());
    }

    public void setParent(View parent) {
        this.mInfo.setParent(parent);
    }

    public void setParent(View root, int virtualDescendantId) {
        this.mParentVirtualDescendantId = virtualDescendantId;
        IMPL.setParent(this.mInfo, root, virtualDescendantId);
    }

    public void getBoundsInParent(Rect outBounds) {
        this.mInfo.getBoundsInParent(outBounds);
    }

    public void setBoundsInParent(Rect bounds) {
        this.mInfo.setBoundsInParent(bounds);
    }

    public void getBoundsInScreen(Rect outBounds) {
        this.mInfo.getBoundsInScreen(outBounds);
    }

    public void setBoundsInScreen(Rect bounds) {
        this.mInfo.setBoundsInScreen(bounds);
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public void setCheckable(boolean checkable) {
        this.mInfo.setCheckable(checkable);
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public void setChecked(boolean checked) {
        this.mInfo.setChecked(checked);
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public void setFocusable(boolean focusable) {
        this.mInfo.setFocusable(focusable);
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public void setFocused(boolean focused) {
        this.mInfo.setFocused(focused);
    }

    public boolean isVisibleToUser() {
        return IMPL.isVisibleToUser(this.mInfo);
    }

    public void setVisibleToUser(boolean visibleToUser) {
        IMPL.setVisibleToUser(this.mInfo, visibleToUser);
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public void setAccessibilityFocused(boolean focused) {
        IMPL.setAccessibilityFocused(this.mInfo, focused);
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public void setSelected(boolean selected) {
        this.mInfo.setSelected(selected);
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public void setClickable(boolean clickable) {
        this.mInfo.setClickable(clickable);
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public void setLongClickable(boolean longClickable) {
        this.mInfo.setLongClickable(longClickable);
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        this.mInfo.setEnabled(enabled);
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public void setPassword(boolean password) {
        this.mInfo.setPassword(password);
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public void setScrollable(boolean scrollable) {
        this.mInfo.setScrollable(scrollable);
    }

    public boolean isImportantForAccessibility() {
        return IMPL.isImportantForAccessibility(this.mInfo);
    }

    public void setImportantForAccessibility(boolean important) {
        IMPL.setImportantForAccessibility(this.mInfo, important);
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public void setPackageName(CharSequence packageName) {
        this.mInfo.setPackageName(packageName);
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public void setClassName(CharSequence className) {
        this.mInfo.setClassName(className);
    }

    public CharSequence getText() {
        return this.mInfo.getText();
    }

    public void setText(CharSequence text) {
        this.mInfo.setText(text);
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public void setContentDescription(CharSequence contentDescription) {
        this.mInfo.setContentDescription(contentDescription);
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public void setViewIdResourceName(String viewId) {
        IMPL.setViewIdResourceName(this.mInfo, viewId);
    }

    public String getViewIdResourceName() {
        return IMPL.getViewIdResourceName(this.mInfo);
    }

    public int getLiveRegion() {
        return IMPL.getLiveRegion(this.mInfo);
    }

    public void setLiveRegion(int mode) {
        IMPL.setLiveRegion(this.mInfo, mode);
    }

    public int getDrawingOrder() {
        return IMPL.getDrawingOrder(this.mInfo);
    }

    public void setDrawingOrder(int drawingOrderInParent) {
        IMPL.setDrawingOrder(this.mInfo, drawingOrderInParent);
    }

    public CollectionInfoCompat getCollectionInfo() {
        Object info = IMPL.getCollectionInfo(this.mInfo);
        if (info == null) {
            return null;
        }
        return new CollectionInfoCompat(info);
    }

    public void setCollectionInfo(Object collectionInfo) {
        IMPL.setCollectionInfo(this.mInfo, ((CollectionInfoCompat) collectionInfo).mInfo);
    }

    public void setCollectionItemInfo(Object collectionItemInfo) {
        IMPL.setCollectionItemInfo(this.mInfo, ((CollectionItemInfoCompat) collectionItemInfo).mInfo);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        Object info = IMPL.getCollectionItemInfo(this.mInfo);
        if (info == null) {
            return null;
        }
        return new CollectionItemInfoCompat(info);
    }

    public RangeInfoCompat getRangeInfo() {
        Object info = IMPL.getRangeInfo(this.mInfo);
        if (info == null) {
            return null;
        }
        return new RangeInfoCompat(info);
    }

    public void setRangeInfo(RangeInfoCompat rangeInfo) {
        IMPL.setRangeInfo(this.mInfo, rangeInfo.mInfo);
    }

    public List<AccessibilityActionCompat> getActionList() {
        List<Object> actions = IMPL.getActionList(this.mInfo);
        if (actions != null) {
            List<AccessibilityActionCompat> result = new ArrayList<>();
            int actionCount = actions.size();
            for (int i = 0; i < actionCount; i++) {
                Object action = actions.get(i);
                result.add(new AccessibilityActionCompat(action));
            }
            return result;
        }
        return Collections.emptyList();
    }

    public void setContentInvalid(boolean contentInvalid) {
        IMPL.setContentInvalid(this.mInfo, contentInvalid);
    }

    public boolean isContentInvalid() {
        return IMPL.isContentInvalid(this.mInfo);
    }

    public boolean isContextClickable() {
        return IMPL.isContextClickable(this.mInfo);
    }

    public void setContextClickable(boolean contextClickable) {
        IMPL.setContextClickable(this.mInfo, contextClickable);
    }

    public void setError(CharSequence error) {
        IMPL.setError(this.mInfo, error);
    }

    public CharSequence getError() {
        return IMPL.getError(this.mInfo);
    }

    public void setLabelFor(View labeled) {
        IMPL.setLabelFor(this.mInfo, labeled);
    }

    public void setLabelFor(View root, int virtualDescendantId) {
        IMPL.setLabelFor(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        return wrapNonNullInstance(IMPL.getLabelFor(this.mInfo));
    }

    public void setLabeledBy(View label) {
        IMPL.setLabeledBy(this.mInfo, label);
    }

    public void setLabeledBy(View root, int virtualDescendantId) {
        IMPL.setLabeledBy(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        return wrapNonNullInstance(IMPL.getLabeledBy(this.mInfo));
    }

    public boolean canOpenPopup() {
        return IMPL.canOpenPopup(this.mInfo);
    }

    public void setCanOpenPopup(boolean opensPopup) {
        IMPL.setCanOpenPopup(this.mInfo, opensPopup);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String viewId) {
        List<AccessibilityNodeInfo> nodes = IMPL.findAccessibilityNodeInfosByViewId(this.mInfo, viewId);
        if (nodes != null) {
            List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
            for (AccessibilityNodeInfo node : nodes) {
                result.add(wrap(node));
            }
            return result;
        }
        return Collections.emptyList();
    }

    public Bundle getExtras() {
        return IMPL.getExtras(this.mInfo);
    }

    public int getInputType() {
        return IMPL.getInputType(this.mInfo);
    }

    public void setInputType(int inputType) {
        IMPL.setInputType(this.mInfo, inputType);
    }

    public void setMaxTextLength(int max) {
        IMPL.setMaxTextLength(this.mInfo, max);
    }

    public int getMaxTextLength() {
        return IMPL.getMaxTextLength(this.mInfo);
    }

    public void setTextSelection(int start, int end) {
        IMPL.setTextSelection(this.mInfo, start, end);
    }

    public int getTextSelectionStart() {
        return IMPL.getTextSelectionStart(this.mInfo);
    }

    public int getTextSelectionEnd() {
        return IMPL.getTextSelectionEnd(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        return wrapNonNullInstance(IMPL.getTraversalBefore(this.mInfo));
    }

    public void setTraversalBefore(View view) {
        IMPL.setTraversalBefore(this.mInfo, view);
    }

    public void setTraversalBefore(View root, int virtualDescendantId) {
        IMPL.setTraversalBefore(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        return wrapNonNullInstance(IMPL.getTraversalAfter(this.mInfo));
    }

    public void setTraversalAfter(View view) {
        IMPL.setTraversalAfter(this.mInfo, view);
    }

    public void setTraversalAfter(View root, int virtualDescendantId) {
        IMPL.setTraversalAfter(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityWindowInfoCompat getWindow() {
        return AccessibilityWindowInfoCompat.wrapNonNullInstance(IMPL.getWindow(this.mInfo));
    }

    public boolean isDismissable() {
        return IMPL.isDismissable(this.mInfo);
    }

    public void setDismissable(boolean dismissable) {
        IMPL.setDismissable(this.mInfo, dismissable);
    }

    public boolean isEditable() {
        return IMPL.isEditable(this.mInfo);
    }

    public void setEditable(boolean editable) {
        IMPL.setEditable(this.mInfo, editable);
    }

    public boolean isMultiLine() {
        return IMPL.isMultiLine(this.mInfo);
    }

    public void setMultiLine(boolean multiLine) {
        IMPL.setMultiLine(this.mInfo, multiLine);
    }

    public boolean refresh() {
        return IMPL.refresh(this.mInfo);
    }

    @Nullable
    public CharSequence getRoleDescription() {
        return IMPL.getRoleDescription(this.mInfo);
    }

    public void setRoleDescription(@Nullable CharSequence roleDescription) {
        IMPL.setRoleDescription(this.mInfo, roleDescription);
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj;
        if (this.mInfo == null) {
            if (other.mInfo != null) {
                return false;
            }
        } else if (!this.mInfo.equals(other.mInfo)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        Rect bounds = new Rect();
        getBoundsInParent(bounds);
        builder.append("; boundsInParent: " + bounds);
        getBoundsInScreen(bounds);
        builder.append("; boundsInScreen: " + bounds);
        builder.append("; packageName: ");
        builder.append(getPackageName());
        builder.append("; className: ");
        builder.append(getClassName());
        builder.append("; text: ");
        builder.append(getText());
        builder.append("; contentDescription: ");
        builder.append(getContentDescription());
        builder.append("; viewId: ");
        builder.append(getViewIdResourceName());
        builder.append("; checkable: ");
        builder.append(isCheckable());
        builder.append("; checked: ");
        builder.append(isChecked());
        builder.append("; focusable: ");
        builder.append(isFocusable());
        builder.append("; focused: ");
        builder.append(isFocused());
        builder.append("; selected: ");
        builder.append(isSelected());
        builder.append("; clickable: ");
        builder.append(isClickable());
        builder.append("; longClickable: ");
        builder.append(isLongClickable());
        builder.append("; enabled: ");
        builder.append(isEnabled());
        builder.append("; password: ");
        builder.append(isPassword());
        builder.append("; scrollable: " + isScrollable());
        builder.append("; [");
        int actionBits = getActions();
        while (actionBits != 0) {
            int action = 1 << Integer.numberOfTrailingZeros(actionBits);
            actionBits &= ~action;
            builder.append(getActionSymbolicName(action));
            if (actionBits != 0) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private static String getActionSymbolicName(int action) {
        switch (action) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }
}