package com.wits.pms.mcu.custom.utils;

import android.content.DialogInterface;

/* renamed from: com.wits.pms.mcu.custom.utils.-$$Lambda$OTAUpdate$6FQxO82I90YiE1q7Sjy_xP3o7Pg  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$OTAUpdate$6FQxO82I90YiE1q7Sjy_xP3o7Pg implements DialogInterface.OnDismissListener {
    public static final /* synthetic */ $$Lambda$OTAUpdate$6FQxO82I90YiE1q7Sjy_xP3o7Pg INSTANCE = new $$Lambda$OTAUpdate$6FQxO82I90YiE1q7Sjy_xP3o7Pg();

    private /* synthetic */ $$Lambda$OTAUpdate$6FQxO82I90YiE1q7Sjy_xP3o7Pg() {
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        OTAUpdate.isUpdating = false;
    }
}
