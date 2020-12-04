package com.orhanobut.logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiskLogStrategy implements LogStrategy {
    @NonNull
    private final Handler handler;

    public DiskLogStrategy(@NonNull Handler handler2) {
        this.handler = (Handler) Utils.checkNotNull(handler2);
    }

    public void log(int level, @Nullable String tag, @NonNull String message) {
        Utils.checkNotNull(message);
        this.handler.sendMessage(this.handler.obtainMessage(level, message));
    }

    static class WriteHandler extends Handler {
        @NonNull
        private final String folder;
        private final int maxFileSize;

        WriteHandler(@NonNull Looper looper, @NonNull String folder2, int maxFileSize2) {
            super((Looper) Utils.checkNotNull(looper));
            this.folder = (String) Utils.checkNotNull(folder2);
            this.maxFileSize = maxFileSize2;
        }

        public void handleMessage(@NonNull Message msg) {
            String content = (String) msg.obj;
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(getLogFile(this.folder, "logs"), true);
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
            fileWriter.append(content);
        }

        private File getLogFile(@NonNull String folderName, @NonNull String fileName) {
            Utils.checkNotNull(folderName);
            Utils.checkNotNull(fileName);
            File folder2 = new File(folderName);
            if (!folder2.exists()) {
                folder2.mkdirs();
            }
            int newFileCount = 0;
            File existingFile = null;
            File newFile = new File(folder2, String.format("%s_%s.csv", new Object[]{fileName, 0}));
            while (newFile.exists()) {
                existingFile = newFile;
                newFileCount++;
                newFile = new File(folder2, String.format("%s_%s.csv", new Object[]{fileName, Integer.valueOf(newFileCount)}));
            }
            if (existingFile == null || existingFile.length() >= ((long) this.maxFileSize)) {
                return newFile;
            }
            return existingFile;
        }
    }
}
