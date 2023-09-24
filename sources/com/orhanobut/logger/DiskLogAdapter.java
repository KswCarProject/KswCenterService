package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes5.dex */
public class DiskLogAdapter implements LogAdapter {
    @NonNull
    private final FormatStrategy formatStrategy;

    public DiskLogAdapter() {
        this.formatStrategy = CsvFormatStrategy.newBuilder().build();
    }

    public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = (FormatStrategy) Utils.checkNotNull(formatStrategy);
    }

    @Override // com.orhanobut.logger.LogAdapter
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override // com.orhanobut.logger.LogAdapter
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        this.formatStrategy.log(priority, tag, message);
    }
}
