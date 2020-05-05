package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.widget.Toast;

final /* synthetic */ class ForceMcuUpdate$1$$Lambda$0 implements Runnable {
    private final Context arg$1;
    private final int arg$2;

    ForceMcuUpdate$1$$Lambda$0(Context context, int i) {
        this.arg$1 = context;
        this.arg$2 = i;
    }

    public void run() {
        Toast.makeText(this.arg$1, "Mcu Error Code:" + this.arg$2, 0).show();
    }
}
