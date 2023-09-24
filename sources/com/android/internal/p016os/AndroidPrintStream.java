package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.p007os.DropBoxManager;
import android.util.Log;

/* renamed from: com.android.internal.os.AndroidPrintStream */
/* loaded from: classes4.dex */
class AndroidPrintStream extends LoggingPrintStream {
    private final int priority;
    private final String tag;

    @UnsupportedAppUsage
    public AndroidPrintStream(int priority, String tag) {
        if (tag == null) {
            throw new NullPointerException(DropBoxManager.EXTRA_TAG);
        }
        this.priority = priority;
        this.tag = tag;
    }

    @Override // com.android.internal.p016os.LoggingPrintStream
    protected void log(String line) {
        Log.println(this.priority, this.tag, line);
    }
}
