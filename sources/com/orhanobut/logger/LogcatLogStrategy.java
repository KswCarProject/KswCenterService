package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class LogcatLogStrategy implements LogStrategy {
    static final String DEFAULT_TAG = "NO_TAG";

    public void log(int priority, @Nullable String tag, @NonNull String message) {
        Utils.checkNotNull(message);
        if (tag == null) {
            tag = DEFAULT_TAG;
        }
        Log.println(priority, tag, message);
    }
}
