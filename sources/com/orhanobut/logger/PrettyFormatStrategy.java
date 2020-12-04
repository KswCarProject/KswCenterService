package com.orhanobut.logger;

import android.net.wifi.WifiEnterpriseConfig;
import android.provider.SettingsStringUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.internal.content.NativeLibraryHelper;

public class PrettyFormatStrategy implements FormatStrategy {
    private static final String BOTTOM_BORDER = "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final int CHUNK_SIZE = 4000;
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final char HORIZONTAL_LINE = '│';
    private static final String MIDDLE_BORDER = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final char MIDDLE_CORNER = '├';
    private static final int MIN_STACK_OFFSET = 5;
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final char TOP_LEFT_CORNER = '┌';
    @NonNull
    private final LogStrategy logStrategy;
    private final int methodCount;
    private final int methodOffset;
    private final boolean showThreadInfo;
    @Nullable
    private final String tag;

    private PrettyFormatStrategy(@NonNull Builder builder) {
        Utils.checkNotNull(builder);
        this.methodCount = builder.methodCount;
        this.methodOffset = builder.methodOffset;
        this.showThreadInfo = builder.showThreadInfo;
        this.logStrategy = builder.logStrategy;
        this.tag = builder.tag;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
        Utils.checkNotNull(message);
        String tag2 = formatTag(onceOnlyTag);
        logTopBorder(priority, tag2);
        logHeaderContent(priority, tag2, this.methodCount);
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= 4000) {
            if (this.methodCount > 0) {
                logDivider(priority, tag2);
            }
            logContent(priority, tag2, message);
            logBottomBorder(priority, tag2);
            return;
        }
        if (this.methodCount > 0) {
            logDivider(priority, tag2);
        }
        for (int i = 0; i < length; i += 4000) {
            logContent(priority, tag2, new String(bytes, i, Math.min(length - i, 4000)));
        }
        logBottomBorder(priority, tag2);
    }

    private void logTopBorder(int logType, @Nullable String tag2) {
        logChunk(logType, tag2, TOP_BORDER);
    }

    private void logHeaderContent(int logType, @Nullable String tag2, int methodCount2) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (this.showThreadInfo) {
            logChunk(logType, tag2, "│ Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag2);
        }
        int stackOffset = getStackOffset(trace) + this.methodOffset;
        if (methodCount2 + stackOffset > trace.length) {
            methodCount2 = (trace.length - stackOffset) - 1;
        }
        String level = "";
        for (int i = methodCount2; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex < trace.length) {
                level = level + "   ";
                logChunk(logType, tag2, HORIZONTAL_LINE + ' ' + level + getSimpleClassName(trace[stackIndex].getClassName()) + "." + trace[stackIndex].getMethodName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + " (" + trace[stackIndex].getFileName() + SettingsStringUtil.DELIMITER + trace[stackIndex].getLineNumber() + ")");
            }
        }
    }

    private void logBottomBorder(int logType, @Nullable String tag2) {
        logChunk(logType, tag2, BOTTOM_BORDER);
    }

    private void logDivider(int logType, @Nullable String tag2) {
        logChunk(logType, tag2, MIDDLE_BORDER);
    }

    private void logContent(int logType, @Nullable String tag2, @NonNull String chunk) {
        Utils.checkNotNull(chunk);
        for (String line : chunk.split(System.getProperty("line.separator"))) {
            logChunk(logType, tag2, "│ " + line);
        }
    }

    private void logChunk(int priority, @Nullable String tag2, @NonNull String chunk) {
        Utils.checkNotNull(chunk);
        this.logStrategy.log(priority, tag2, chunk);
    }

    private String getSimpleClassName(@NonNull String name) {
        Utils.checkNotNull(name);
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private int getStackOffset(@NonNull StackTraceElement[] trace) {
        Utils.checkNotNull(trace);
        for (int i = 5; i < trace.length; i++) {
            String name = trace[i].getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return i - 1;
            }
        }
        return -1;
    }

    @Nullable
    private String formatTag(@Nullable String tag2) {
        if (Utils.isEmpty(tag2) || Utils.equals(this.tag, tag2)) {
            return this.tag;
        }
        return this.tag + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + tag2;
    }

    public static class Builder {
        @Nullable
        LogStrategy logStrategy;
        int methodCount;
        int methodOffset;
        boolean showThreadInfo;
        @Nullable
        String tag;

        private Builder() {
            this.methodCount = 2;
            this.methodOffset = 0;
            this.showThreadInfo = true;
            this.tag = "PRETTY_LOGGER";
        }

        @NonNull
        public Builder methodCount(int val) {
            this.methodCount = val;
            return this;
        }

        @NonNull
        public Builder methodOffset(int val) {
            this.methodOffset = val;
            return this;
        }

        @NonNull
        public Builder showThreadInfo(boolean val) {
            this.showThreadInfo = val;
            return this;
        }

        @NonNull
        public Builder logStrategy(@Nullable LogStrategy val) {
            this.logStrategy = val;
            return this;
        }

        @NonNull
        public Builder tag(@Nullable String tag2) {
            this.tag = tag2;
            return this;
        }

        @NonNull
        public PrettyFormatStrategy build() {
            if (this.logStrategy == null) {
                this.logStrategy = new LogcatLogStrategy();
            }
            return new PrettyFormatStrategy(this);
        }
    }
}
