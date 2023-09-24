package android.support.p011v4.view.accessibility;

import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

@RequiresApi(19)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat */
/* loaded from: classes3.dex */
class AccessibilityNodeInfoCompatKitKat {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat$RangeInfo */
    /* loaded from: classes3.dex */
    static class RangeInfo {
        RangeInfo() {
        }

        static float getCurrent(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getCurrent();
        }

        static float getMax(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getMax();
        }

        static float getMin(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getMin();
        }

        static int getType(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() {
    }
}
