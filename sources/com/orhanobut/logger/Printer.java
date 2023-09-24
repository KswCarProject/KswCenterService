package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes5.dex */
public interface Printer {
    void addAdapter(@NonNull LogAdapter logAdapter);

    void clearLogAdapters();

    /* renamed from: d */
    void mo11d(@Nullable Object obj);

    /* renamed from: d */
    void mo10d(@NonNull String str, @Nullable Object... objArr);

    /* renamed from: e */
    void mo9e(@NonNull String str, @Nullable Object... objArr);

    /* renamed from: e */
    void mo8e(@Nullable Throwable th, @NonNull String str, @Nullable Object... objArr);

    /* renamed from: i */
    void mo7i(@NonNull String str, @Nullable Object... objArr);

    void json(@Nullable String str);

    void log(int i, @Nullable String str, @Nullable String str2, @Nullable Throwable th);

    /* renamed from: t */
    Printer mo6t(@Nullable String str);

    /* renamed from: v */
    void mo5v(@NonNull String str, @Nullable Object... objArr);

    /* renamed from: w */
    void mo4w(@NonNull String str, @Nullable Object... objArr);

    void wtf(@NonNull String str, @Nullable Object... objArr);

    void xml(@Nullable String str);
}
