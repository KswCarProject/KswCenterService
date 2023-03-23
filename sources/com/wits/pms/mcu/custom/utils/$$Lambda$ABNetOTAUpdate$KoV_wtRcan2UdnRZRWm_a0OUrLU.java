package com.wits.pms.mcu.custom.utils;

import android.content.DialogInterface;

/* renamed from: com.wits.pms.mcu.custom.utils.-$$Lambda$ABNetOTAUpdate$KoV_wtRcan2UdnRZRWm_a0OUrLU  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ABNetOTAUpdate$KoV_wtRcan2UdnRZRWm_a0OUrLU implements DialogInterface.OnDismissListener {
    public static final /* synthetic */ $$Lambda$ABNetOTAUpdate$KoV_wtRcan2UdnRZRWm_a0OUrLU INSTANCE = new $$Lambda$ABNetOTAUpdate$KoV_wtRcan2UdnRZRWm_a0OUrLU();

    private /* synthetic */ $$Lambda$ABNetOTAUpdate$KoV_wtRcan2UdnRZRWm_a0OUrLU() {
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        ABNetOTAUpdate.isUpdating = false;
    }
}
