package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.widget.ProgressBar;

final /* synthetic */ class ForceMcuUpdate$$Lambda$0 implements Runnable {
    private final Context arg$1;
    private final ProgressBar arg$2;

    ForceMcuUpdate$$Lambda$0(Context context, ProgressBar progressBar) {
        this.arg$1 = context;
        this.arg$2 = progressBar;
    }

    public void run() {
        ForceMcuUpdate.lambda$fix$0$ForceMcuUpdate(this.arg$1, this.arg$2);
    }
}
