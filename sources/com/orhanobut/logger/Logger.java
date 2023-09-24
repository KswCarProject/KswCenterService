package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes5.dex */
public final class Logger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    @NonNull
    private static Printer printer = new LoggerPrinter();

    private Logger() {
    }

    public static void printer(@NonNull Printer printer2) {
        printer = (Printer) Utils.checkNotNull(printer2);
    }

    public static void addLogAdapter(@NonNull LogAdapter adapter) {
        printer.addAdapter((LogAdapter) Utils.checkNotNull(adapter));
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    /* renamed from: t */
    public static Printer m14t(@Nullable String tag) {
        return printer.mo6t(tag);
    }

    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

    /* renamed from: d */
    public static void m18d(@NonNull String message, @Nullable Object... args) {
        printer.mo10d(message, args);
    }

    /* renamed from: d */
    public static void m19d(@Nullable Object object) {
        printer.mo11d(object);
    }

    /* renamed from: e */
    public static void m17e(@NonNull String message, @Nullable Object... args) {
        printer.mo8e(null, message, args);
    }

    /* renamed from: e */
    public static void m16e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.mo8e(throwable, message, args);
    }

    /* renamed from: i */
    public static void m15i(@NonNull String message, @Nullable Object... args) {
        printer.mo7i(message, args);
    }

    /* renamed from: v */
    public static void m13v(@NonNull String message, @Nullable Object... args) {
        printer.mo5v(message, args);
    }

    /* renamed from: w */
    public static void m12w(@NonNull String message, @Nullable Object... args) {
        printer.mo4w(message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        printer.wtf(message, args);
    }

    public static void json(@Nullable String json) {
        printer.json(json);
    }

    public static void xml(@Nullable String xml) {
        printer.xml(xml);
    }
}
