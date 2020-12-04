package com.android.internal.widget;

import android.view.View;

public class ViewClippingUtil {
    private static final int CLIP_CHILDREN_TAG = 16908813;
    private static final int CLIP_CLIPPING_SET = 16908812;
    private static final int CLIP_TO_PADDING = 16908815;

    /* JADX WARNING: type inference failed for: r1v2, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setClippingDeactivated(android.view.View r7, boolean r8, com.android.internal.widget.ViewClippingUtil.ClippingParameters r9) {
        /*
            if (r8 != 0) goto L_0x0009
            boolean r0 = r9.isClippingEnablingAllowed(r7)
            if (r0 != 0) goto L_0x0009
            return
        L_0x0009:
            android.view.ViewParent r0 = r7.getParent()
            boolean r0 = r0 instanceof android.view.ViewGroup
            if (r0 != 0) goto L_0x0012
            return
        L_0x0012:
            android.view.ViewParent r0 = r7.getParent()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
        L_0x0018:
            if (r8 != 0) goto L_0x0021
            boolean r1 = r9.isClippingEnablingAllowed(r7)
            if (r1 != 0) goto L_0x0021
            return
        L_0x0021:
            r1 = 16908812(0x102020c, float:2.3878698E-38)
            java.lang.Object r2 = r0.getTag(r1)
            android.util.ArraySet r2 = (android.util.ArraySet) r2
            if (r2 != 0) goto L_0x0035
            android.util.ArraySet r3 = new android.util.ArraySet
            r3.<init>()
            r2 = r3
            r0.setTagInternal(r1, r2)
        L_0x0035:
            r3 = 16908813(0x102020d, float:2.38787E-38)
            java.lang.Object r4 = r0.getTag(r3)
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            if (r4 != 0) goto L_0x004b
            boolean r5 = r0.getClipChildren()
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r5)
            r0.setTagInternal(r3, r4)
        L_0x004b:
            r3 = 16908815(0x102020f, float:2.3878706E-38)
            java.lang.Object r5 = r0.getTag(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            if (r5 != 0) goto L_0x0061
            boolean r6 = r0.getClipToPadding()
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
            r0.setTagInternal(r3, r5)
        L_0x0061:
            if (r8 != 0) goto L_0x0083
            r2.remove(r7)
            boolean r3 = r2.isEmpty()
            if (r3 == 0) goto L_0x0090
            boolean r3 = r4.booleanValue()
            r0.setClipChildren(r3)
            boolean r3 = r5.booleanValue()
            r0.setClipToPadding(r3)
            r3 = 0
            r0.setTagInternal(r1, r3)
            r1 = 1
            r9.onClippingStateChanged(r0, r1)
            goto L_0x0090
        L_0x0083:
            r2.add(r7)
            r1 = 0
            r0.setClipChildren(r1)
            r0.setClipToPadding(r1)
            r9.onClippingStateChanged(r0, r1)
        L_0x0090:
            boolean r1 = r9.shouldFinish(r0)
            if (r1 == 0) goto L_0x0097
            return
        L_0x0097:
            android.view.ViewParent r1 = r0.getParent()
            boolean r3 = r1 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x00a4
            r0 = r1
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            goto L_0x0018
        L_0x00a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ViewClippingUtil.setClippingDeactivated(android.view.View, boolean, com.android.internal.widget.ViewClippingUtil$ClippingParameters):void");
    }

    public interface ClippingParameters {
        boolean shouldFinish(View view);

        boolean isClippingEnablingAllowed(View view) {
            return !MessagingPropertyAnimator.isAnimatingTranslation(view);
        }

        void onClippingStateChanged(View view, boolean isClipping) {
        }
    }
}
