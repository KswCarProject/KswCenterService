package com.wits.pms.mcu.custom.utils;

import android.content.DialogInterface;

/* renamed from: com.wits.pms.mcu.custom.utils.-$$Lambda$NetOTAUpdate$HyYqoMWJQx4CfGjFkRF69dicwA4  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$NetOTAUpdate$HyYqoMWJQx4CfGjFkRF69dicwA4 implements DialogInterface.OnDismissListener {
    public static final /* synthetic */ $$Lambda$NetOTAUpdate$HyYqoMWJQx4CfGjFkRF69dicwA4 INSTANCE = new $$Lambda$NetOTAUpdate$HyYqoMWJQx4CfGjFkRF69dicwA4();

    private /* synthetic */ $$Lambda$NetOTAUpdate$HyYqoMWJQx4CfGjFkRF69dicwA4() {
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        NetOTAUpdate.isUpdating = false;
    }
}
