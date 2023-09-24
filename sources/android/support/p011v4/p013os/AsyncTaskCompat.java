package android.support.p011v4.p013os;

import android.p007os.AsyncTask;

@Deprecated
/* renamed from: android.support.v4.os.AsyncTaskCompat */
/* loaded from: classes3.dex */
public final class AsyncTaskCompat {
    @Deprecated
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(AsyncTask<Params, Progress, Result> task, Params... params) {
        if (task == null) {
            throw new IllegalArgumentException("task can not be null");
        }
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        return task;
    }

    private AsyncTaskCompat() {
    }
}
