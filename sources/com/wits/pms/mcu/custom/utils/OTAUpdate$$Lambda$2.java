package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ProgressBar;

final /* synthetic */ class OTAUpdate$$Lambda$2 implements DialogInterface.OnClickListener {
    private final Context arg$1;
    private final ProgressBar arg$2;

    OTAUpdate$$Lambda$2(Context context, ProgressBar progressBar) {
        this.arg$1 = context;
        this.arg$2 = progressBar;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        OTAUpdate.lambda$showUpdateDialog$2$OTAUpdate(this.arg$1, this.arg$2, dialogInterface, i);
    }
}
