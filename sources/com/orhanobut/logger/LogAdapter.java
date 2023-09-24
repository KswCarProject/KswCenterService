package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes5.dex */
public interface LogAdapter {
    boolean isLoggable(int i, @Nullable String str);

    void log(int i, @Nullable String str, @NonNull String str2);
}
