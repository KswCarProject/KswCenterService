package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    public static Printer t(@Nullable String tag) {
        return printer.t(tag);
    }

    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        printer.d(message, args);
    }

    public static void d(@Nullable Object object) {
        printer.d(object);
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        printer.e((Throwable) null, message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        printer.i(message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        printer.v(message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        printer.w(message, args);
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
