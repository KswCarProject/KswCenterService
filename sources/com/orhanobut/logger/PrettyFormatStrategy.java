package com.orhanobut.logger;

import android.net.wifi.WifiEnterpriseConfig;
import android.provider.SettingsStringUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes5.dex */
public class PrettyFormatStrategy implements FormatStrategy {
    private static final String BOTTOM_BORDER = "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500";
    private static final char BOTTOM_LEFT_CORNER = '\u2514';
    private static final int CHUNK_SIZE = 4000;
    private static final String DOUBLE_DIVIDER = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500";
    private static final char HORIZONTAL_LINE = '\u2502';
    private static final String MIDDLE_BORDER = "\u251c\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504";
    private static final char MIDDLE_CORNER = '\u251c';
    private static final int MIN_STACK_OFFSET = 5;
    private static final String SINGLE_DIVIDER = "\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504\u2504";
    private static final String TOP_BORDER = "\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500";
    private static final char TOP_LEFT_CORNER = '\u250c';
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

    @Override // com.orhanobut.logger.FormatStrategy
    public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
        Utils.checkNotNull(message);
        String tag = formatTag(onceOnlyTag);
        logTopBorder(priority, tag);
        logHeaderContent(priority, tag, this.methodCount);
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= 4000) {
            if (this.methodCount > 0) {
                logDivider(priority, tag);
            }
            logContent(priority, tag, message);
            logBottomBorder(priority, tag);
            return;
        }
        if (this.methodCount > 0) {
            logDivider(priority, tag);
        }
        for (int i = 0; i < length; i += 4000) {
            int count = Math.min(length - i, 4000);
            logContent(priority, tag, new String(bytes, i, count));
        }
        logBottomBorder(priority, tag);
    }

    private void logTopBorder(int logType, @Nullable String tag) {
        logChunk(logType, tag, TOP_BORDER);
    }

    private void logHeaderContent(int logType, @Nullable String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (this.showThreadInfo) {
            logChunk(logType, tag, "\u2502 Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag);
        }
        int stackOffset = getStackOffset(trace) + this.methodOffset;
        if (methodCount + stackOffset > trace.length) {
            methodCount = (trace.length - stackOffset) - 1;
        }
        String level = "";
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex < trace.length) {
                level = level + "   ";
                logChunk(logType, tag, HORIZONTAL_LINE + ' ' + level + getSimpleClassName(trace[stackIndex].getClassName()) + "." + trace[stackIndex].getMethodName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + " (" + trace[stackIndex].getFileName() + SettingsStringUtil.DELIMITER + trace[stackIndex].getLineNumber() + ")");
            }
        }
    }

    private void logBottomBorder(int logType, @Nullable String tag) {
        logChunk(logType, tag, BOTTOM_BORDER);
    }

    private void logDivider(int logType, @Nullable String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private void logContent(int logType, @Nullable String tag, @NonNull String chunk) {
        Utils.checkNotNull(chunk);
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, "\u2502 " + line);
        }
    }

    private void logChunk(int priority, @Nullable String tag, @NonNull String chunk) {
        Utils.checkNotNull(chunk);
        this.logStrategy.log(priority, tag, chunk);
    }

    private String getSimpleClassName(@NonNull String name) {
        Utils.checkNotNull(name);
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private int getStackOffset(@NonNull StackTraceElement[] trace) {
        Utils.checkNotNull(trace);
        for (int i = 5; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return i - 1;
            }
        }
        return -1;
    }

    @Nullable
    private String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + tag;
        }
        return this.tag;
    }

    /* loaded from: classes5.dex */
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
        public Builder tag(@Nullable String tag) {
            this.tag = tag;
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
