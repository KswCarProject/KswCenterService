package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AndroidLogAdapter implements LogAdapter {
    @NonNull
    private final FormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy2) {
        this.formatStrategy = (FormatStrategy) Utils.checkNotNull(formatStrategy2);
    }

    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    public void log(int priority, @Nullable String tag, @NonNull String message) {
        this.formatStrategy.log(priority, tag, message);
    }
}
