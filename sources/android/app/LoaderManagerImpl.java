package android.app;

import android.app.LoaderManager;
import android.content.Loader;
import android.p007os.Bundle;
import android.provider.SettingsStringUtil;
import android.util.DebugUtils;
import android.util.Log;
import android.util.SparseArray;
import com.ibm.icu.text.PluralRules;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* compiled from: LoaderManager.java */
/* loaded from: classes.dex */
class LoaderManagerImpl extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    boolean mCreatingLoader;
    private FragmentHostCallback mHost;
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final String mWho;
    final SparseArray<LoaderInfo> mLoaders = new SparseArray<>(0);
    final SparseArray<LoaderInfo> mInactiveLoaders = new SparseArray<>(0);

    /* compiled from: LoaderManager.java */
    /* loaded from: classes.dex */
    final class LoaderInfo implements Loader.OnLoadCompleteListener<Object>, Loader.OnLoadCanceledListener<Object> {
        final Bundle mArgs;
        LoaderManager.LoaderCallbacks<Object> mCallbacks;
        Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        Loader<Object> mLoader;
        LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;

        public LoaderInfo(int id, Bundle args, LoaderManager.LoaderCallbacks<Object> callbacks) {
            this.mId = id;
            this.mArgs = args;
            this.mCallbacks = callbacks;
        }

        void start() {
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
            } else if (this.mStarted) {
            } else {
                this.mStarted = true;
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Starting: " + this);
                }
                if (this.mLoader == null && this.mCallbacks != null) {
                    this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
                }
                if (this.mLoader != null) {
                    if (this.mLoader.getClass().isMemberClass() && !Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                        throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
                    }
                    if (!this.mListenerRegistered) {
                        this.mLoader.registerListener(this.mId, this);
                        this.mLoader.registerOnLoadCanceledListener(this);
                        this.mListenerRegistered = true;
                    }
                    this.mLoader.startLoading();
                }
            }
        }

        void retain() {
            if (LoaderManagerImpl.DEBUG) {
                Log.m66v(LoaderManagerImpl.TAG, "  Retaining: " + this);
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        void finishRetain() {
            if (this.mRetaining) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Finished Retaining: " + this);
                }
                this.mRetaining = false;
                if (this.mStarted != this.mRetainingStarted && !this.mStarted) {
                    stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        void reportStart() {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData && !this.mRetaining) {
                    callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        void stop() {
            if (LoaderManagerImpl.DEBUG) {
                Log.m66v(LoaderManagerImpl.TAG, "  Stopping: " + this);
            }
            this.mStarted = false;
            if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                this.mLoader.unregisterListener(this);
                this.mLoader.unregisterOnLoadCanceledListener(this);
                this.mLoader.stopLoading();
            }
        }

        boolean cancel() {
            if (LoaderManagerImpl.DEBUG) {
                Log.m66v(LoaderManagerImpl.TAG, "  Canceling: " + this);
            }
            if (this.mStarted && this.mLoader != null && this.mListenerRegistered) {
                boolean cancelLoadResult = this.mLoader.cancelLoad();
                if (!cancelLoadResult) {
                    onLoadCanceled(this.mLoader);
                }
                return cancelLoadResult;
            }
            return false;
        }

        void destroy() {
            if (LoaderManagerImpl.DEBUG) {
                Log.m66v(LoaderManagerImpl.TAG, "  Destroying: " + this);
            }
            this.mDestroyed = true;
            boolean needReset = this.mDeliveredData;
            this.mDeliveredData = false;
            if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && needReset) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Reseting: " + this);
                }
                String lastBecause = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    lastBecause = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
                }
                try {
                    this.mCallbacks.onLoaderReset(this.mLoader);
                } finally {
                    if (LoaderManagerImpl.this.mHost != null) {
                        LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = lastBecause;
                    }
                }
            }
            this.mCallbacks = null;
            this.mData = null;
            this.mHaveData = false;
            if (this.mLoader != null) {
                if (this.mListenerRegistered) {
                    this.mListenerRegistered = false;
                    this.mLoader.unregisterListener(this);
                    this.mLoader.unregisterOnLoadCanceledListener(this);
                }
                this.mLoader.reset();
            }
            if (this.mPendingLoader != null) {
                this.mPendingLoader.destroy();
            }
        }

        @Override // android.content.Loader.OnLoadCanceledListener
        public void onLoadCanceled(Loader<Object> loader) {
            if (LoaderManagerImpl.DEBUG) {
                Log.m66v(LoaderManagerImpl.TAG, "onLoadCanceled: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- destroyed");
                }
            } else if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- not active");
                }
            } else {
                LoaderInfo pending = this.mPendingLoader;
                if (pending != null) {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.m66v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + pending);
                    }
                    this.mPendingLoader = null;
                    LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                    destroy();
                    LoaderManagerImpl.this.installLoader(pending);
                }
            }
        }

        @Override // android.content.Loader.OnLoadCompleteListener
        public void onLoadComplete(Loader<Object> loader, Object data) {
            if (LoaderManagerImpl.DEBUG) {
                Log.m66v(LoaderManagerImpl.TAG, "onLoadComplete: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
                }
            } else if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.m66v(LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
                }
            } else {
                LoaderInfo pending = this.mPendingLoader;
                if (pending != null) {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.m66v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + pending);
                    }
                    this.mPendingLoader = null;
                    LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                    destroy();
                    LoaderManagerImpl.this.installLoader(pending);
                    return;
                }
                if (this.mData != data || !this.mHaveData) {
                    this.mData = data;
                    this.mHaveData = true;
                    if (this.mStarted) {
                        callOnLoadFinished(loader, data);
                    }
                }
                LoaderInfo info = LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
                if (info != null && info != this) {
                    info.mDeliveredData = false;
                    info.destroy();
                    LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
                }
                if (LoaderManagerImpl.this.mHost != null && !LoaderManagerImpl.this.hasRunningLoaders()) {
                    LoaderManagerImpl.this.mHost.mFragmentManager.startPendingDeferredFragments();
                }
            }
        }

        void callOnLoadFinished(Loader<Object> loader, Object data) {
            if (this.mCallbacks != null) {
                String lastBecause = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    lastBecause = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
                }
                try {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.m66v(LoaderManagerImpl.TAG, "  onLoadFinished in " + loader + PluralRules.KEYWORD_RULE_SEPARATOR + loader.dataToString(data));
                    }
                    this.mCallbacks.onLoadFinished(loader, data);
                    this.mDeliveredData = true;
                } finally {
                    if (LoaderManagerImpl.this.mHost != null) {
                        LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = lastBecause;
                    }
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.mId);
            sb.append(" : ");
            DebugUtils.buildShortClassTag(this.mLoader, sb);
            sb.append("}}");
            return sb.toString();
        }

        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            writer.print(prefix);
            writer.print("mId=");
            writer.print(this.mId);
            writer.print(" mArgs=");
            writer.println(this.mArgs);
            writer.print(prefix);
            writer.print("mCallbacks=");
            writer.println(this.mCallbacks);
            writer.print(prefix);
            writer.print("mLoader=");
            writer.println(this.mLoader);
            if (this.mLoader != null) {
                Loader<Object> loader = this.mLoader;
                loader.dump(prefix + "  ", fd, writer, args);
            }
            if (this.mHaveData || this.mDeliveredData) {
                writer.print(prefix);
                writer.print("mHaveData=");
                writer.print(this.mHaveData);
                writer.print("  mDeliveredData=");
                writer.println(this.mDeliveredData);
                writer.print(prefix);
                writer.print("mData=");
                writer.println(this.mData);
            }
            writer.print(prefix);
            writer.print("mStarted=");
            writer.print(this.mStarted);
            writer.print(" mReportNextStart=");
            writer.print(this.mReportNextStart);
            writer.print(" mDestroyed=");
            writer.println(this.mDestroyed);
            writer.print(prefix);
            writer.print("mRetaining=");
            writer.print(this.mRetaining);
            writer.print(" mRetainingStarted=");
            writer.print(this.mRetainingStarted);
            writer.print(" mListenerRegistered=");
            writer.println(this.mListenerRegistered);
            if (this.mPendingLoader != null) {
                writer.print(prefix);
                writer.println("Pending Loader ");
                writer.print(this.mPendingLoader);
                writer.println(SettingsStringUtil.DELIMITER);
                LoaderInfo loaderInfo = this.mPendingLoader;
                loaderInfo.dump(prefix + "  ", fd, writer, args);
            }
        }
    }

    LoaderManagerImpl(String who, FragmentHostCallback host, boolean started) {
        this.mWho = who;
        this.mHost = host;
        this.mStarted = started;
    }

    void updateHostController(FragmentHostCallback host) {
        this.mHost = host;
    }

    @Override // android.app.LoaderManager
    public FragmentHostCallback getFragmentHostCallback() {
        return this.mHost;
    }

    private LoaderInfo createLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<Object> callback) {
        LoaderInfo info = new LoaderInfo(id, args, callback);
        Loader<Object> loader = callback.onCreateLoader(id, args);
        info.mLoader = loader;
        return info;
    }

    private LoaderInfo createAndInstallLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<Object> callback) {
        try {
            this.mCreatingLoader = true;
            LoaderInfo info = createLoader(id, args, callback);
            installLoader(info);
            return info;
        } finally {
            this.mCreatingLoader = false;
        }
    }

    void installLoader(LoaderInfo info) {
        this.mLoaders.put(info.mId, info);
        if (this.mStarted) {
            info.start();
        }
    }

    @Override // android.app.LoaderManager
    public <D> Loader<D> initLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo info = this.mLoaders.get(id);
        if (DEBUG) {
            Log.m66v(TAG, "initLoader in " + this + ": args=" + args);
        }
        if (info == null) {
            info = createAndInstallLoader(id, args, callback);
            if (DEBUG) {
                Log.m66v(TAG, "  Created new loader " + info);
            }
        } else {
            if (DEBUG) {
                Log.m66v(TAG, "  Re-using existing loader " + info);
            }
            info.mCallbacks = callback;
        }
        if (info.mHaveData && this.mStarted) {
            info.callOnLoadFinished(info.mLoader, info.mData);
        }
        return (Loader<D>) info.mLoader;
    }

    @Override // android.app.LoaderManager
    public <D> Loader<D> restartLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo info = this.mLoaders.get(id);
        if (DEBUG) {
            Log.m66v(TAG, "restartLoader in " + this + ": args=" + args);
        }
        if (info != null) {
            LoaderInfo inactive = this.mInactiveLoaders.get(id);
            if (inactive != null) {
                if (info.mHaveData) {
                    if (DEBUG) {
                        Log.m66v(TAG, "  Removing last inactive loader: " + info);
                    }
                    inactive.mDeliveredData = false;
                    inactive.destroy();
                    info.mLoader.abandon();
                    this.mInactiveLoaders.put(id, info);
                } else if (!info.cancel()) {
                    if (DEBUG) {
                        Log.m66v(TAG, "  Current loader is stopped; replacing");
                    }
                    this.mLoaders.put(id, null);
                    info.destroy();
                } else {
                    if (DEBUG) {
                        Log.m66v(TAG, "  Current loader is running; configuring pending loader");
                    }
                    if (info.mPendingLoader != null) {
                        if (DEBUG) {
                            Log.m66v(TAG, "  Removing pending loader: " + info.mPendingLoader);
                        }
                        info.mPendingLoader.destroy();
                        info.mPendingLoader = null;
                    }
                    if (DEBUG) {
                        Log.m66v(TAG, "  Enqueuing as new pending loader");
                    }
                    info.mPendingLoader = createLoader(id, args, callback);
                    return (Loader<D>) info.mPendingLoader.mLoader;
                }
            } else {
                if (DEBUG) {
                    Log.m66v(TAG, "  Making last loader inactive: " + info);
                }
                info.mLoader.abandon();
                this.mInactiveLoaders.put(id, info);
            }
        }
        return (Loader<D>) createAndInstallLoader(id, args, callback).mLoader;
    }

    @Override // android.app.LoaderManager
    public void destroyLoader(int id) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (DEBUG) {
            Log.m66v(TAG, "destroyLoader in " + this + " of " + id);
        }
        int idx = this.mLoaders.indexOfKey(id);
        if (idx >= 0) {
            LoaderInfo info = this.mLoaders.valueAt(idx);
            this.mLoaders.removeAt(idx);
            info.destroy();
        }
        int idx2 = this.mInactiveLoaders.indexOfKey(id);
        if (idx2 >= 0) {
            LoaderInfo info2 = this.mInactiveLoaders.valueAt(idx2);
            this.mInactiveLoaders.removeAt(idx2);
            info2.destroy();
        }
        if (this.mHost != null && !hasRunningLoaders()) {
            this.mHost.mFragmentManager.startPendingDeferredFragments();
        }
    }

    @Override // android.app.LoaderManager
    public <D> Loader<D> getLoader(int id) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = this.mLoaders.get(id);
        if (loaderInfo != null) {
            if (loaderInfo.mPendingLoader != null) {
                return (Loader<D>) loaderInfo.mPendingLoader.mLoader;
            }
            return (Loader<D>) loaderInfo.mLoader;
        }
        return null;
    }

    void doStart() {
        if (DEBUG) {
            Log.m66v(TAG, "Starting in " + this);
        }
        if (this.mStarted) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.m63w(TAG, "Called doStart when already started: " + this, e);
            return;
        }
        this.mStarted = true;
        int i = this.mLoaders.size() - 1;
        while (true) {
            int i2 = i;
            if (i2 >= 0) {
                this.mLoaders.valueAt(i2).start();
                i = i2 - 1;
            } else {
                return;
            }
        }
    }

    void doStop() {
        if (DEBUG) {
            Log.m66v(TAG, "Stopping in " + this);
        }
        if (!this.mStarted) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.m63w(TAG, "Called doStop when not started: " + this, e);
            return;
        }
        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
            this.mLoaders.valueAt(i).stop();
        }
        this.mStarted = false;
    }

    void doRetain() {
        if (DEBUG) {
            Log.m66v(TAG, "Retaining in " + this);
        }
        if (!this.mStarted) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.m63w(TAG, "Called doRetain when not started: " + this, e);
            return;
        }
        this.mRetaining = true;
        this.mStarted = false;
        int i = this.mLoaders.size() - 1;
        while (true) {
            int i2 = i;
            if (i2 >= 0) {
                this.mLoaders.valueAt(i2).retain();
                i = i2 - 1;
            } else {
                return;
            }
        }
    }

    void finishRetain() {
        if (this.mRetaining) {
            if (DEBUG) {
                Log.m66v(TAG, "Finished Retaining in " + this);
            }
            this.mRetaining = false;
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                this.mLoaders.valueAt(i).finishRetain();
            }
        }
    }

    void doReportNextStart() {
        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
            this.mLoaders.valueAt(i).mReportNextStart = true;
        }
    }

    void doReportStart() {
        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
            this.mLoaders.valueAt(i).reportStart();
        }
    }

    void doDestroy() {
        if (!this.mRetaining) {
            if (DEBUG) {
                Log.m66v(TAG, "Destroying Active in " + this);
            }
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                this.mLoaders.valueAt(i).destroy();
            }
            this.mLoaders.clear();
        }
        if (DEBUG) {
            Log.m66v(TAG, "Destroying Inactive in " + this);
        }
        for (int i2 = this.mInactiveLoaders.size() - 1; i2 >= 0; i2--) {
            this.mInactiveLoaders.valueAt(i2).destroy();
        }
        this.mInactiveLoaders.clear();
        this.mHost = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        DebugUtils.buildShortClassTag(this.mHost, sb);
        sb.append("}}");
        return sb.toString();
    }

    @Override // android.app.LoaderManager
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        if (this.mLoaders.size() > 0) {
            writer.print(prefix);
            writer.println("Active Loaders:");
            String innerPrefix = prefix + "    ";
            for (int i = 0; i < this.mLoaders.size(); i++) {
                LoaderInfo li = this.mLoaders.valueAt(i);
                writer.print(prefix);
                writer.print("  #");
                writer.print(this.mLoaders.keyAt(i));
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(li.toString());
                li.dump(innerPrefix, fd, writer, args);
            }
        }
        if (this.mInactiveLoaders.size() > 0) {
            writer.print(prefix);
            writer.println("Inactive Loaders:");
            String innerPrefix2 = prefix + "    ";
            for (int i2 = 0; i2 < this.mInactiveLoaders.size(); i2++) {
                LoaderInfo li2 = this.mInactiveLoaders.valueAt(i2);
                writer.print(prefix);
                writer.print("  #");
                writer.print(this.mInactiveLoaders.keyAt(i2));
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(li2.toString());
                li2.dump(innerPrefix2, fd, writer, args);
            }
        }
    }

    public boolean hasRunningLoaders() {
        int count = this.mLoaders.size();
        boolean loadersRunning = false;
        for (int i = 0; i < count; i++) {
            LoaderInfo li = this.mLoaders.valueAt(i);
            loadersRunning |= li.mStarted && !li.mDeliveredData;
        }
        return loadersRunning;
    }
}
