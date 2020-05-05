package com.wits.pms.mcu.custom.utils;

import android.content.DialogInterface;

final /* synthetic */ class OTAUpdate$$Lambda$0 implements DialogInterface.OnDismissListener {
    static final DialogInterface.OnDismissListener $instance = new OTAUpdate$$Lambda$0();

    private OTAUpdate$$Lambda$0() {
    }

    public void onDismiss(DialogInterface dialogInterface) {
        OTAUpdate.isUpdating = false;
    }
}
