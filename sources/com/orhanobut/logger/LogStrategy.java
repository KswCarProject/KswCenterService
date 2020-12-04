package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface LogStrategy {
    void log(int i, @Nullable String str, @NonNull String str2);
}
