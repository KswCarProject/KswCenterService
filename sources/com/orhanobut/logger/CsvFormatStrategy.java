package com.orhanobut.logger;

import android.os.Environment;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.internal.content.NativeLibraryHelper;
import com.orhanobut.logger.DiskLogStrategy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CsvFormatStrategy implements FormatStrategy {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NEW_LINE_REPLACEMENT = " <br> ";
    private static final String SEPARATOR = ",";
    @NonNull
    private final Date date;
    @NonNull
    private final SimpleDateFormat dateFormat;
    @NonNull
    private final LogStrategy logStrategy;
    @Nullable
    private final String tag;

    private CsvFormatStrategy(@NonNull Builder builder) {
        Utils.checkNotNull(builder);
        this.date = builder.date;
        this.dateFormat = builder.dateFormat;
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
        this.date.setTime(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        builder.append(Long.toString(this.date.getTime()));
        builder.append(",");
        builder.append(this.dateFormat.format(this.date));
        builder.append(",");
        builder.append(Utils.logLevel(priority));
        builder.append(",");
        builder.append(tag2);
        if (message.contains(NEW_LINE)) {
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }
        builder.append(",");
        builder.append(message);
        builder.append(NEW_LINE);
        this.logStrategy.log(priority, tag2, builder.toString());
    }

    @Nullable
    private String formatTag(@Nullable String tag2) {
        if (Utils.isEmpty(tag2) || Utils.equals(this.tag, tag2)) {
            return this.tag;
        }
        return this.tag + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + tag2;
    }

    public static final class Builder {
        private static final int MAX_BYTES = 512000;
        Date date;
        SimpleDateFormat dateFormat;
        LogStrategy logStrategy;
        String tag;

        private Builder() {
            this.tag = "PRETTY_LOGGER";
        }

        @NonNull
        public Builder date(@Nullable Date val) {
            this.date = val;
            return this;
        }

        @NonNull
        public Builder dateFormat(@Nullable SimpleDateFormat val) {
            this.dateFormat = val;
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
        public CsvFormatStrategy build() {
            if (this.date == null) {
                this.date = new Date();
            }
            if (this.dateFormat == null) {
                this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK);
            }
            if (this.logStrategy == null) {
                String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "logger";
                HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
                ht.start();
                this.logStrategy = new DiskLogStrategy(new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES));
            }
            return new CsvFormatStrategy(this);
        }
    }
}
