package com.orhanobut.logger;

import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes5.dex */
public class DiskLogStrategy implements LogStrategy {
    @NonNull
    private final Handler handler;

    public DiskLogStrategy(@NonNull Handler handler) {
        this.handler = (Handler) Utils.checkNotNull(handler);
    }

    @Override // com.orhanobut.logger.LogStrategy
    public void log(int level, @Nullable String tag, @NonNull String message) {
        Utils.checkNotNull(message);
        this.handler.sendMessage(this.handler.obtainMessage(level, message));
    }

    /* loaded from: classes5.dex */
    static class WriteHandler extends Handler {
        @NonNull
        private final String folder;
        private final int maxFileSize;

        WriteHandler(@NonNull Looper looper, @NonNull String folder, int maxFileSize) {
            super((Looper) Utils.checkNotNull(looper));
            this.folder = (String) Utils.checkNotNull(folder);
            this.maxFileSize = maxFileSize;
        }

        @Override // android.p007os.Handler
        public void handleMessage(@NonNull Message msg) {
            String content = (String) msg.obj;
            FileWriter fileWriter = null;
            File logFile = getLogFile(this.folder, "logs");
            try {
                fileWriter = new FileWriter(logFile, true);
                writeLog(fileWriter, content);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e2) {
                    }
                }
            }
        }

        private void writeLog(@NonNull FileWriter fileWriter, @NonNull String content) throws IOException {
            Utils.checkNotNull(fileWriter);
            Utils.checkNotNull(content);
            fileWriter.append((CharSequence) content);
        }

        private File getLogFile(@NonNull String folderName, @NonNull String fileName) {
            Utils.checkNotNull(folderName);
            Utils.checkNotNull(fileName);
            File folder = new File(folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            int newFileCount = 0;
            File existingFile = null;
            File newFile = new File(folder, String.format("%s_%s.csv", fileName, 0));
            while (newFile.exists()) {
                existingFile = newFile;
                newFileCount++;
                newFile = new File(folder, String.format("%s_%s.csv", fileName, Integer.valueOf(newFileCount)));
            }
            if (existingFile != null) {
                if (existingFile.length() >= this.maxFileSize) {
                    return newFile;
                }
                return existingFile;
            }
            return newFile;
        }
    }
}
