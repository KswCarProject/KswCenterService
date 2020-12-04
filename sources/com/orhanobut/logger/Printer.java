package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Printer {
    void addAdapter(@NonNull LogAdapter logAdapter);

    void clearLogAdapters();

    void d(@Nullable Object obj);

    void d(@NonNull String str, @Nullable Object... objArr);

    void e(@NonNull String str, @Nullable Object... objArr);

    void e(@Nullable Throwable th, @NonNull String str, @Nullable Object... objArr);

    void i(@NonNull String str, @Nullable Object... objArr);

    void json(@Nullable String str);

    void log(int i, @Nullable String str, @Nullable String str2, @Nullable Throwable th);

    Printer t(@Nullable String str);

    void v(@NonNull String str, @Nullable Object... objArr);

    void w(@NonNull String str, @Nullable Object... objArr);

    void wtf(@NonNull String str, @Nullable Object... objArr);

    void xml(@Nullable String str);
}
