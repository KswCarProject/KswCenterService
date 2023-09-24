package com.orhanobut.logger;

import android.p007os.Environment;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.internal.content.NativeLibraryHelper;
import com.orhanobut.logger.DiskLogStrategy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes5.dex */
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

    @Override // com.orhanobut.logger.FormatStrategy
    public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
        Utils.checkNotNull(message);
        String tag = formatTag(onceOnlyTag);
        this.date.setTime(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        builder.append(Long.toString(this.date.getTime()));
        builder.append(",");
        builder.append(this.dateFormat.format(this.date));
        builder.append(",");
        builder.append(Utils.logLevel(priority));
        builder.append(",");
        builder.append(tag);
        if (message.contains(NEW_LINE)) {
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }
        builder.append(",");
        builder.append(message);
        builder.append(NEW_LINE);
        this.logStrategy.log(priority, tag, builder.toString());
    }

    @Nullable
    private String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + tag;
        }
        return this.tag;
    }

    /* loaded from: classes5.dex */
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
        public Builder tag(@Nullable String tag) {
            this.tag = tag;
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
                String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String folder = diskPath + File.separatorChar + "logger";
                HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
                ht.start();
                Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES);
                this.logStrategy = new DiskLogStrategy(handler);
            }
            return new CsvFormatStrategy(this);
        }
    }
}
