package com.wits.pms.mcu.custom.utils;

import android.content.DialogInterface;

/* renamed from: com.wits.pms.mcu.custom.utils.-$$Lambda$ABOTAUpdate$2cXJ-dYPUxzrQMtgEennVqfBXrE  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ABOTAUpdate$2cXJdYPUxzrQMtgEennVqfBXrE implements DialogInterface.OnDismissListener {
    public static final /* synthetic */ $$Lambda$ABOTAUpdate$2cXJdYPUxzrQMtgEennVqfBXrE INSTANCE = new $$Lambda$ABOTAUpdate$2cXJdYPUxzrQMtgEennVqfBXrE();

    private /* synthetic */ $$Lambda$ABOTAUpdate$2cXJdYPUxzrQMtgEennVqfBXrE() {
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        ABOTAUpdate.isUpdating = false;
    }
}
