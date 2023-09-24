package android.support.p011v4.content;

import android.p007os.AsyncTask;
import java.util.concurrent.Executor;

@Deprecated
/* renamed from: android.support.v4.content.ParallelExecutorCompat */
/* loaded from: classes3.dex */
public final class ParallelExecutorCompat {
    @Deprecated
    public static Executor getParallelExecutor() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

    private ParallelExecutorCompat() {
    }
}
