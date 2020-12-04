package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TimedRemoteCaller;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import com.android.internal.widget.IRemoteViewsFactory;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

public class RemoteViewsAdapter extends BaseAdapter implements Handler.Callback {
    private static final int CACHE_RESET_CONFIG_FLAGS = -1073737216;
    private static final int DEFAULT_CACHE_SIZE = 40;
    private static final int DEFAULT_LOADING_VIEW_HEIGHT = 50;
    static final int MSG_LOAD_NEXT_ITEM = 3;
    private static final int MSG_MAIN_HANDLER_COMMIT_METADATA = 1;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_CONNECTED = 3;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_DISCONNECTED = 4;
    private static final int MSG_MAIN_HANDLER_REMOTE_VIEWS_LOADED = 5;
    private static final int MSG_MAIN_HANDLER_SUPER_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_REQUEST_BIND = 1;
    static final int MSG_UNBIND_SERVICE = 4;
    private static final int REMOTE_VIEWS_CACHE_DURATION = 5000;
    private static final String TAG = "RemoteViewsAdapter";
    private static final int UNBIND_SERVICE_DELAY = 5000;
    private static Handler sCacheRemovalQueue;
    private static HandlerThread sCacheRemovalThread;
    private static final HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> sCachedRemoteViewsCaches = new HashMap<>();
    private static final HashMap<RemoteViewsCacheKey, Runnable> sRemoteViewsCacheRemoveRunnables = new HashMap<>();
    private final int mAppWidgetId;
    private final Executor mAsyncViewLoadExecutor;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public final FixedSizeRemoteViewsCache mCache;
    private final RemoteAdapterConnectionCallback mCallback;
    private final Context mContext;
    private boolean mDataReady = false;
    private final Intent mIntent;
    private ApplicationInfo mLastRemoteViewAppInfo;
    /* access modifiers changed from: private */
    public final Handler mMainHandler;
    private final boolean mOnLightBackground;
    /* access modifiers changed from: private */
    public RemoteViews.OnClickHandler mRemoteViewsOnClickHandler;
    private RemoteViewsFrameLayoutRefSet mRequestedViews;
    private final RemoteServiceHandler mServiceHandler;
    private int mVisibleWindowLowerBound;
    private int mVisibleWindowUpperBound;
    @UnsupportedAppUsage
    private final HandlerThread mWorkerThread;

    public interface RemoteAdapterConnectionCallback {
        void deferNotifyDataSetChanged();

        boolean onRemoteAdapterConnected();

        void onRemoteAdapterDisconnected();

        void setRemoteViewsAdapter(Intent intent, boolean z);
    }

    public static class AsyncRemoteAdapterAction implements Runnable {
        private final RemoteAdapterConnectionCallback mCallback;
        private final Intent mIntent;

        public AsyncRemoteAdapterAction(RemoteAdapterConnectionCallback callback, Intent intent) {
            this.mCallback = callback;
            this.mIntent = intent;
        }

        public void run() {
            this.mCallback.setRemoteViewsAdapter(this.mIntent, true);
        }
    }

    private static class RemoteServiceHandler extends Handler implements ServiceConnection {
        private final WeakReference<RemoteViewsAdapter> mAdapter;
        private boolean mBindRequested = false;
        private final Context mContext;
        private boolean mNotifyDataSetChangedPending = false;
        private IRemoteViewsFactory mRemoteViewsFactory;

        RemoteServiceHandler(Looper workerLooper, RemoteViewsAdapter adapter, Context context) {
            super(workerLooper);
            this.mAdapter = new WeakReference<>(adapter);
            this.mContext = context;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            this.mRemoteViewsFactory = IRemoteViewsFactory.Stub.asInterface(service);
            enqueueDeferredUnbindServiceMessage();
            RemoteViewsAdapter adapter = (RemoteViewsAdapter) this.mAdapter.get();
            if (adapter != null) {
                if (this.mNotifyDataSetChangedPending) {
                    this.mNotifyDataSetChangedPending = false;
                    Message msg = Message.obtain((Handler) this, 2);
                    handleMessage(msg);
                    msg.recycle();
                } else if (sendNotifyDataSetChange(false)) {
                    adapter.updateTemporaryMetaData(this.mRemoteViewsFactory);
                    adapter.mMainHandler.sendEmptyMessage(1);
                    adapter.mMainHandler.sendEmptyMessage(3);
                }
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            this.mRemoteViewsFactory = null;
            RemoteViewsAdapter adapter = (RemoteViewsAdapter) this.mAdapter.get();
            if (adapter != null) {
                adapter.mMainHandler.sendEmptyMessage(4);
            }
        }

        public void handleMessage(Message msg) {
            int newCount;
            int[] visibleWindow;
            RemoteViewsAdapter adapter = (RemoteViewsAdapter) this.mAdapter.get();
            switch (msg.what) {
                case 1:
                    if (adapter == null || this.mRemoteViewsFactory != null) {
                        enqueueDeferredUnbindServiceMessage();
                    }
                    if (!this.mBindRequested) {
                        try {
                            this.mBindRequested = AppWidgetManager.getInstance(this.mContext).bindRemoteViewsService(this.mContext, msg.arg1, (Intent) msg.obj, this.mContext.getServiceDispatcher(this, this, 33554433), 33554433);
                            return;
                        } catch (Exception e) {
                            Log.e(RemoteViewsAdapter.TAG, "Failed to bind remoteViewsService: " + e.getMessage());
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    enqueueDeferredUnbindServiceMessage();
                    if (adapter != null) {
                        if (this.mRemoteViewsFactory == null) {
                            this.mNotifyDataSetChangedPending = true;
                            adapter.requestBindService();
                            return;
                        } else if (sendNotifyDataSetChange(true)) {
                            synchronized (adapter.mCache) {
                                adapter.mCache.reset();
                            }
                            adapter.updateTemporaryMetaData(this.mRemoteViewsFactory);
                            synchronized (adapter.mCache.getTemporaryMetaData()) {
                                newCount = adapter.mCache.getTemporaryMetaData().count;
                                visibleWindow = adapter.getVisibleWindow(newCount);
                            }
                            for (int position : visibleWindow) {
                                if (position < newCount) {
                                    adapter.updateRemoteViews(this.mRemoteViewsFactory, position, false);
                                }
                            }
                            adapter.mMainHandler.sendEmptyMessage(1);
                            adapter.mMainHandler.sendEmptyMessage(2);
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case 3:
                    if (adapter != null && this.mRemoteViewsFactory != null) {
                        removeMessages(4);
                        int position2 = adapter.mCache.getNextIndexToLoad();
                        if (position2 > -1) {
                            adapter.updateRemoteViews(this.mRemoteViewsFactory, position2, true);
                            sendEmptyMessage(3);
                            return;
                        }
                        enqueueDeferredUnbindServiceMessage();
                        return;
                    }
                    return;
                case 4:
                    unbindNow();
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: protected */
        public void unbindNow() {
            if (this.mBindRequested) {
                this.mBindRequested = false;
                this.mContext.unbindService(this);
            }
            this.mRemoteViewsFactory = null;
        }

        private boolean sendNotifyDataSetChange(boolean always) {
            if (!always) {
                try {
                    if (this.mRemoteViewsFactory.isCreated()) {
                        return true;
                    }
                } catch (RemoteException | RuntimeException e) {
                    Log.e(RemoteViewsAdapter.TAG, "Error in updateNotifyDataSetChanged(): " + e.getMessage());
                    return false;
                }
            }
            this.mRemoteViewsFactory.onDataSetChanged();
            return true;
        }

        private void enqueueDeferredUnbindServiceMessage() {
            removeMessages(4);
            sendEmptyMessageDelayed(4, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        }
    }

    static class RemoteViewsFrameLayout extends AppWidgetHostView {
        public int cacheIndex = -1;
        private final FixedSizeRemoteViewsCache mCache;

        public RemoteViewsFrameLayout(Context context, FixedSizeRemoteViewsCache cache) {
            super(context);
            this.mCache = cache;
        }

        public void onRemoteViewsLoaded(RemoteViews view, RemoteViews.OnClickHandler handler, boolean forceApplyAsync) {
            setOnClickHandler(handler);
            applyRemoteViews(view, forceApplyAsync || (view != null && view.prefersAsyncApply()));
        }

        /* access modifiers changed from: protected */
        public View getDefaultView() {
            int viewHeight = this.mCache.getMetaData().getLoadingTemplate(getContext()).defaultHeight;
            TextView loadingTextView = (TextView) LayoutInflater.from(getContext()).inflate((int) R.layout.remote_views_adapter_default_loading_view, (ViewGroup) this, false);
            loadingTextView.setHeight(viewHeight);
            return loadingTextView;
        }

        /* access modifiers changed from: protected */
        public Context getRemoteContext() {
            return null;
        }

        /* access modifiers changed from: protected */
        public View getErrorView() {
            return getDefaultView();
        }
    }

    private class RemoteViewsFrameLayoutRefSet extends SparseArray<LinkedList<RemoteViewsFrameLayout>> {
        private RemoteViewsFrameLayoutRefSet() {
        }

        public void add(int position, RemoteViewsFrameLayout layout) {
            LinkedList<RemoteViewsFrameLayout> refs = (LinkedList) get(position);
            if (refs == null) {
                refs = new LinkedList<>();
                put(position, refs);
            }
            layout.cacheIndex = position;
            refs.add(layout);
        }

        public void notifyOnRemoteViewsLoaded(int position, RemoteViews view) {
            LinkedList<RemoteViewsFrameLayout> refs;
            if (view != null && (refs = (LinkedList) removeReturnOld(position)) != null) {
                Iterator it = refs.iterator();
                while (it.hasNext()) {
                    ((RemoteViewsFrameLayout) it.next()).onRemoteViewsLoaded(view, RemoteViewsAdapter.this.mRemoteViewsOnClickHandler, true);
                }
            }
        }

        public void removeView(RemoteViewsFrameLayout rvfl) {
            if (rvfl.cacheIndex >= 0) {
                LinkedList<RemoteViewsFrameLayout> refs = (LinkedList) get(rvfl.cacheIndex);
                if (refs != null) {
                    refs.remove(rvfl);
                }
                rvfl.cacheIndex = -1;
            }
        }
    }

    private static class RemoteViewsMetaData {
        int count;
        boolean hasStableIds;
        LoadingViewTemplate loadingTemplate;
        private final SparseIntArray mTypeIdIndexMap = new SparseIntArray();
        int viewTypeCount;

        public RemoteViewsMetaData() {
            reset();
        }

        public void set(RemoteViewsMetaData d) {
            synchronized (d) {
                this.count = d.count;
                this.viewTypeCount = d.viewTypeCount;
                this.hasStableIds = d.hasStableIds;
                this.loadingTemplate = d.loadingTemplate;
            }
        }

        public void reset() {
            this.count = 0;
            this.viewTypeCount = 1;
            this.hasStableIds = true;
            this.loadingTemplate = null;
            this.mTypeIdIndexMap.clear();
        }

        public int getMappedViewType(int typeId) {
            int mappedTypeId = this.mTypeIdIndexMap.get(typeId, -1);
            if (mappedTypeId != -1) {
                return mappedTypeId;
            }
            int mappedTypeId2 = this.mTypeIdIndexMap.size() + 1;
            this.mTypeIdIndexMap.put(typeId, mappedTypeId2);
            return mappedTypeId2;
        }

        public boolean isViewTypeInRange(int typeId) {
            return getMappedViewType(typeId) < this.viewTypeCount;
        }

        public synchronized LoadingViewTemplate getLoadingTemplate(Context context) {
            if (this.loadingTemplate == null) {
                this.loadingTemplate = new LoadingViewTemplate((RemoteViews) null, context);
            }
            return this.loadingTemplate;
        }
    }

    private static class RemoteViewsIndexMetaData {
        long itemId;
        int typeId;

        public RemoteViewsIndexMetaData(RemoteViews v, long itemId2) {
            set(v, itemId2);
        }

        public void set(RemoteViews v, long id) {
            this.itemId = id;
            if (v != null) {
                this.typeId = v.getLayoutId();
            } else {
                this.typeId = 0;
            }
        }
    }

    private static class FixedSizeRemoteViewsCache {
        private static final float sMaxCountSlackPercent = 0.75f;
        private static final int sMaxMemoryLimitInBytes = 2097152;
        /* access modifiers changed from: private */
        public final Configuration mConfiguration;
        private final SparseArray<RemoteViewsIndexMetaData> mIndexMetaData = new SparseArray<>();
        /* access modifiers changed from: private */
        public final SparseArray<RemoteViews> mIndexRemoteViews = new SparseArray<>();
        private final SparseBooleanArray mIndicesToLoad = new SparseBooleanArray();
        private int mLastRequestedIndex;
        private final int mMaxCount;
        private final int mMaxCountSlack;
        /* access modifiers changed from: private */
        public final RemoteViewsMetaData mMetaData = new RemoteViewsMetaData();
        private int mPreloadLowerBound;
        private int mPreloadUpperBound;
        private final RemoteViewsMetaData mTemporaryMetaData = new RemoteViewsMetaData();

        FixedSizeRemoteViewsCache(int maxCacheSize, Configuration configuration) {
            this.mMaxCount = maxCacheSize;
            this.mMaxCountSlack = Math.round(((float) (this.mMaxCount / 2)) * sMaxCountSlackPercent);
            this.mPreloadLowerBound = 0;
            this.mPreloadUpperBound = -1;
            this.mLastRequestedIndex = -1;
            this.mConfiguration = new Configuration(configuration);
        }

        public void insert(int position, RemoteViews v, long itemId, int[] visibleWindow) {
            int trimIndex;
            if (this.mIndexRemoteViews.size() >= this.mMaxCount) {
                this.mIndexRemoteViews.remove(getFarthestPositionFrom(position, visibleWindow));
            }
            int pruneFromPosition = this.mLastRequestedIndex > -1 ? this.mLastRequestedIndex : position;
            while (getRemoteViewsBitmapMemoryUsage() >= 2097152 && (trimIndex = getFarthestPositionFrom(pruneFromPosition, visibleWindow)) >= 0) {
                this.mIndexRemoteViews.remove(trimIndex);
            }
            RemoteViewsIndexMetaData metaData = this.mIndexMetaData.get(position);
            if (metaData != null) {
                metaData.set(v, itemId);
            } else {
                this.mIndexMetaData.put(position, new RemoteViewsIndexMetaData(v, itemId));
            }
            this.mIndexRemoteViews.put(position, v);
        }

        public RemoteViewsMetaData getMetaData() {
            return this.mMetaData;
        }

        public RemoteViewsMetaData getTemporaryMetaData() {
            return this.mTemporaryMetaData;
        }

        public RemoteViews getRemoteViewsAt(int position) {
            return this.mIndexRemoteViews.get(position);
        }

        public RemoteViewsIndexMetaData getMetaDataAt(int position) {
            return this.mIndexMetaData.get(position);
        }

        public void commitTemporaryMetaData() {
            synchronized (this.mTemporaryMetaData) {
                synchronized (this.mMetaData) {
                    this.mMetaData.set(this.mTemporaryMetaData);
                }
            }
        }

        private int getRemoteViewsBitmapMemoryUsage() {
            int mem = 0;
            for (int i = this.mIndexRemoteViews.size() - 1; i >= 0; i--) {
                RemoteViews v = this.mIndexRemoteViews.valueAt(i);
                if (v != null) {
                    mem += v.estimateMemoryUsage();
                }
            }
            return mem;
        }

        private int getFarthestPositionFrom(int pos, int[] visibleWindow) {
            int maxDist = 0;
            int maxDistIndex = -1;
            int maxDistNotVisible = 0;
            int maxDistIndexNotVisible = -1;
            for (int i = this.mIndexRemoteViews.size() - 1; i >= 0; i--) {
                int index = this.mIndexRemoteViews.keyAt(i);
                int dist = Math.abs(index - pos);
                if (dist > maxDistNotVisible && Arrays.binarySearch(visibleWindow, index) < 0) {
                    maxDistIndexNotVisible = index;
                    maxDistNotVisible = dist;
                }
                if (dist >= maxDist) {
                    maxDistIndex = index;
                    maxDist = dist;
                }
            }
            if (maxDistIndexNotVisible > -1) {
                return maxDistIndexNotVisible;
            }
            return maxDistIndex;
        }

        public void queueRequestedPositionToLoad(int position) {
            this.mLastRequestedIndex = position;
            synchronized (this.mIndicesToLoad) {
                this.mIndicesToLoad.put(position, true);
            }
        }

        public boolean queuePositionsToBePreloadedFromRequestedPosition(int position) {
            int count;
            if (this.mPreloadLowerBound <= position && position <= this.mPreloadUpperBound && Math.abs(position - ((this.mPreloadUpperBound + this.mPreloadLowerBound) / 2)) < this.mMaxCountSlack) {
                return false;
            }
            synchronized (this.mMetaData) {
                count = this.mMetaData.count;
            }
            synchronized (this.mIndicesToLoad) {
                for (int i = this.mIndicesToLoad.size() - 1; i >= 0; i--) {
                    if (!this.mIndicesToLoad.valueAt(i)) {
                        this.mIndicesToLoad.removeAt(i);
                    }
                }
                int halfMaxCount = this.mMaxCount / 2;
                this.mPreloadLowerBound = position - halfMaxCount;
                this.mPreloadUpperBound = position + halfMaxCount;
                int effectiveLowerBound = Math.max(0, this.mPreloadLowerBound);
                int effectiveUpperBound = Math.min(this.mPreloadUpperBound, count - 1);
                for (int i2 = effectiveLowerBound; i2 <= effectiveUpperBound; i2++) {
                    if (this.mIndexRemoteViews.indexOfKey(i2) < 0 && !this.mIndicesToLoad.get(i2)) {
                        this.mIndicesToLoad.put(i2, false);
                    }
                }
            }
            return true;
        }

        public int getNextIndexToLoad() {
            synchronized (this.mIndicesToLoad) {
                int index = this.mIndicesToLoad.indexOfValue(true);
                if (index < 0) {
                    index = this.mIndicesToLoad.indexOfValue(false);
                }
                if (index < 0) {
                    return -1;
                }
                int key = this.mIndicesToLoad.keyAt(index);
                this.mIndicesToLoad.removeAt(index);
                return key;
            }
        }

        public boolean containsRemoteViewAt(int position) {
            return this.mIndexRemoteViews.indexOfKey(position) >= 0;
        }

        public boolean containsMetaDataAt(int position) {
            return this.mIndexMetaData.indexOfKey(position) >= 0;
        }

        public void reset() {
            this.mPreloadLowerBound = 0;
            this.mPreloadUpperBound = -1;
            this.mLastRequestedIndex = -1;
            this.mIndexRemoteViews.clear();
            this.mIndexMetaData.clear();
            synchronized (this.mIndicesToLoad) {
                this.mIndicesToLoad.clear();
            }
        }
    }

    static class RemoteViewsCacheKey {
        final Intent.FilterComparison filter;
        final int widgetId;

        RemoteViewsCacheKey(Intent.FilterComparison filter2, int widgetId2) {
            this.filter = filter2;
            this.widgetId = widgetId2;
        }

        public boolean equals(Object o) {
            if (!(o instanceof RemoteViewsCacheKey)) {
                return false;
            }
            RemoteViewsCacheKey other = (RemoteViewsCacheKey) o;
            if (!other.filter.equals(this.filter) || other.widgetId != this.widgetId) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.filter == null ? 0 : this.filter.hashCode()) ^ (this.widgetId << 2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public RemoteViewsAdapter(android.content.Context r7, android.content.Intent r8, android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback r9, boolean r10) {
        /*
            r6 = this;
            r6.<init>()
            r0 = 0
            r6.mDataReady = r0
            r6.mContext = r7
            r6.mIntent = r8
            android.content.Intent r1 = r6.mIntent
            if (r1 == 0) goto L_0x00f6
            java.lang.String r1 = "remoteAdapterAppWidgetId"
            r2 = -1
            int r1 = r8.getIntExtra(r1, r2)
            r6.mAppWidgetId = r1
            android.widget.RemoteViewsAdapter$RemoteViewsFrameLayoutRefSet r1 = new android.widget.RemoteViewsAdapter$RemoteViewsFrameLayoutRefSet
            r2 = 0
            r1.<init>()
            r6.mRequestedViews = r1
            java.lang.String r1 = "remoteAdapterOnLightBackground"
            boolean r0 = r8.getBooleanExtra(r1, r0)
            r6.mOnLightBackground = r0
            java.lang.String r0 = "remoteAdapterAppWidgetId"
            r8.removeExtra(r0)
            java.lang.String r0 = "remoteAdapterOnLightBackground"
            r8.removeExtra(r0)
            android.os.HandlerThread r0 = new android.os.HandlerThread
            java.lang.String r1 = "RemoteViewsCache-loader"
            r0.<init>(r1)
            r6.mWorkerThread = r0
            android.os.HandlerThread r0 = r6.mWorkerThread
            r0.start()
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.myLooper()
            r0.<init>((android.os.Looper) r1, (android.os.Handler.Callback) r6)
            r6.mMainHandler = r0
            android.widget.RemoteViewsAdapter$RemoteServiceHandler r0 = new android.widget.RemoteViewsAdapter$RemoteServiceHandler
            android.os.HandlerThread r1 = r6.mWorkerThread
            android.os.Looper r1 = r1.getLooper()
            android.content.Context r3 = r7.getApplicationContext()
            r0.<init>(r1, r6, r3)
            r6.mServiceHandler = r0
            if (r10 == 0) goto L_0x0069
            android.widget.RemoteViewsAdapter$HandlerThreadExecutor r2 = new android.widget.RemoteViewsAdapter$HandlerThreadExecutor
            android.os.HandlerThread r0 = r6.mWorkerThread
            r2.<init>(r0)
        L_0x0069:
            r6.mAsyncViewLoadExecutor = r2
            r6.mCallback = r9
            android.os.HandlerThread r0 = sCacheRemovalThread
            if (r0 != 0) goto L_0x008c
            android.os.HandlerThread r0 = new android.os.HandlerThread
            java.lang.String r1 = "RemoteViewsAdapter-cachePruner"
            r0.<init>(r1)
            sCacheRemovalThread = r0
            android.os.HandlerThread r0 = sCacheRemovalThread
            r0.start()
            android.os.Handler r0 = new android.os.Handler
            android.os.HandlerThread r1 = sCacheRemovalThread
            android.os.Looper r1 = r1.getLooper()
            r0.<init>((android.os.Looper) r1)
            sCacheRemovalQueue = r0
        L_0x008c:
            android.widget.RemoteViewsAdapter$RemoteViewsCacheKey r0 = new android.widget.RemoteViewsAdapter$RemoteViewsCacheKey
            android.content.Intent$FilterComparison r1 = new android.content.Intent$FilterComparison
            android.content.Intent r2 = r6.mIntent
            r1.<init>(r2)
            int r2 = r6.mAppWidgetId
            r0.<init>(r1, r2)
            java.util.HashMap<android.widget.RemoteViewsAdapter$RemoteViewsCacheKey, android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache> r1 = sCachedRemoteViewsCaches
            monitor-enter(r1)
            java.util.HashMap<android.widget.RemoteViewsAdapter$RemoteViewsCacheKey, android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache> r2 = sCachedRemoteViewsCaches     // Catch:{ all -> 0x00f3 }
            java.lang.Object r2 = r2.get(r0)     // Catch:{ all -> 0x00f3 }
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r2 = (android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache) r2     // Catch:{ all -> 0x00f3 }
            android.content.res.Resources r3 = r7.getResources()     // Catch:{ all -> 0x00f3 }
            android.content.res.Configuration r3 = r3.getConfiguration()     // Catch:{ all -> 0x00f3 }
            if (r2 == 0) goto L_0x00e1
            android.content.res.Configuration r4 = r2.mConfiguration     // Catch:{ all -> 0x00f3 }
            int r4 = r4.diff(r3)     // Catch:{ all -> 0x00f3 }
            r5 = -1073737216(0xffffffffc0001200, float:-2.0010986)
            r4 = r4 & r5
            if (r4 == 0) goto L_0x00be
            goto L_0x00e1
        L_0x00be:
            java.util.HashMap<android.widget.RemoteViewsAdapter$RemoteViewsCacheKey, android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache> r4 = sCachedRemoteViewsCaches     // Catch:{ all -> 0x00f3 }
            java.lang.Object r4 = r4.get(r0)     // Catch:{ all -> 0x00f3 }
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r4 = (android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache) r4     // Catch:{ all -> 0x00f3 }
            r6.mCache = r4     // Catch:{ all -> 0x00f3 }
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r4 = r6.mCache     // Catch:{ all -> 0x00f3 }
            android.widget.RemoteViewsAdapter$RemoteViewsMetaData r4 = r4.mMetaData     // Catch:{ all -> 0x00f3 }
            monitor-enter(r4)     // Catch:{ all -> 0x00f3 }
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r5 = r6.mCache     // Catch:{ all -> 0x00de }
            android.widget.RemoteViewsAdapter$RemoteViewsMetaData r5 = r5.mMetaData     // Catch:{ all -> 0x00de }
            int r5 = r5.count     // Catch:{ all -> 0x00de }
            if (r5 <= 0) goto L_0x00dc
            r5 = 1
            r6.mDataReady = r5     // Catch:{ all -> 0x00de }
        L_0x00dc:
            monitor-exit(r4)     // Catch:{ all -> 0x00de }
            goto L_0x00ea
        L_0x00de:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00de }
            throw r5     // Catch:{ all -> 0x00f3 }
        L_0x00e1:
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r4 = new android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache     // Catch:{ all -> 0x00f3 }
            r5 = 40
            r4.<init>(r5, r3)     // Catch:{ all -> 0x00f3 }
            r6.mCache = r4     // Catch:{ all -> 0x00f3 }
        L_0x00ea:
            boolean r4 = r6.mDataReady     // Catch:{ all -> 0x00f3 }
            if (r4 != 0) goto L_0x00f1
            r6.requestBindService()     // Catch:{ all -> 0x00f3 }
        L_0x00f1:
            monitor-exit(r1)     // Catch:{ all -> 0x00f3 }
            return
        L_0x00f3:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f3 }
            throw r2
        L_0x00f6:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Non-null Intent must be specified."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.RemoteViewsAdapter.<init>(android.content.Context, android.content.Intent, android.widget.RemoteViewsAdapter$RemoteAdapterConnectionCallback, boolean):void");
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            this.mServiceHandler.unbindNow();
            this.mWorkerThread.quit();
        } finally {
            super.finalize();
        }
    }

    @UnsupportedAppUsage
    public boolean isDataReady() {
        return this.mDataReady;
    }

    @UnsupportedAppUsage
    public void setRemoteViewsOnClickHandler(RemoteViews.OnClickHandler handler) {
        this.mRemoteViewsOnClickHandler = handler;
    }

    @UnsupportedAppUsage
    public void saveRemoteViewsCache() {
        int metaDataCount;
        int numRemoteViewsCached;
        RemoteViewsCacheKey key = new RemoteViewsCacheKey(new Intent.FilterComparison(this.mIntent), this.mAppWidgetId);
        synchronized (sCachedRemoteViewsCaches) {
            if (sRemoteViewsCacheRemoveRunnables.containsKey(key)) {
                sCacheRemovalQueue.removeCallbacks(sRemoteViewsCacheRemoveRunnables.get(key));
                sRemoteViewsCacheRemoveRunnables.remove(key);
            }
            synchronized (this.mCache.mMetaData) {
                metaDataCount = this.mCache.mMetaData.count;
            }
            synchronized (this.mCache) {
                numRemoteViewsCached = this.mCache.mIndexRemoteViews.size();
            }
            if (metaDataCount > 0 && numRemoteViewsCached > 0) {
                sCachedRemoteViewsCaches.put(key, this.mCache);
            }
            Runnable r = new Runnable() {
                public final void run() {
                    RemoteViewsAdapter.lambda$saveRemoteViewsCache$0(RemoteViewsAdapter.RemoteViewsCacheKey.this);
                }
            };
            sRemoteViewsCacheRemoveRunnables.put(key, r);
            sCacheRemovalQueue.postDelayed(r, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        }
    }

    static /* synthetic */ void lambda$saveRemoteViewsCache$0(RemoteViewsCacheKey key) {
        synchronized (sCachedRemoteViewsCaches) {
            if (sCachedRemoteViewsCaches.containsKey(key)) {
                sCachedRemoteViewsCaches.remove(key);
            }
            if (sRemoteViewsCacheRemoveRunnables.containsKey(key)) {
                sRemoteViewsCacheRemoveRunnables.remove(key);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateTemporaryMetaData(IRemoteViewsFactory factory) {
        RemoteViews firstView;
        try {
            boolean hasStableIds = factory.hasStableIds();
            int viewTypeCount = factory.getViewTypeCount();
            int count = factory.getCount();
            LoadingViewTemplate loadingTemplate = new LoadingViewTemplate(factory.getLoadingView(), this.mContext);
            if (count > 0 && loadingTemplate.remoteViews == null && (firstView = factory.getViewAt(0)) != null) {
                loadingTemplate.loadFirstViewHeight(firstView, this.mContext, new HandlerThreadExecutor(this.mWorkerThread));
            }
            RemoteViewsMetaData tmpMetaData = this.mCache.getTemporaryMetaData();
            synchronized (tmpMetaData) {
                tmpMetaData.hasStableIds = hasStableIds;
                tmpMetaData.viewTypeCount = viewTypeCount + 1;
                tmpMetaData.count = count;
                tmpMetaData.loadingTemplate = loadingTemplate;
            }
        } catch (RemoteException | RuntimeException e) {
            Log.e(TAG, "Error in updateMetaData: " + e.getMessage());
            synchronized (this.mCache.getMetaData()) {
                this.mCache.getMetaData().reset();
                synchronized (this.mCache) {
                    this.mCache.reset();
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateRemoteViews(IRemoteViewsFactory factory, int position, boolean notifyWhenLoaded) {
        boolean viewTypeInRange;
        int cacheCount;
        try {
            RemoteViews remoteViews = factory.getViewAt(position);
            long itemId = factory.getItemId(position);
            if (remoteViews != null) {
                if (remoteViews.mApplication != null) {
                    if (this.mLastRemoteViewAppInfo == null || !remoteViews.hasSameAppInfo(this.mLastRemoteViewAppInfo)) {
                        this.mLastRemoteViewAppInfo = remoteViews.mApplication;
                    } else {
                        remoteViews.mApplication = this.mLastRemoteViewAppInfo;
                    }
                }
                int layoutId = remoteViews.getLayoutId();
                RemoteViewsMetaData metaData = this.mCache.getMetaData();
                synchronized (metaData) {
                    viewTypeInRange = metaData.isViewTypeInRange(layoutId);
                    cacheCount = this.mCache.mMetaData.count;
                }
                synchronized (this.mCache) {
                    if (viewTypeInRange) {
                        try {
                            this.mCache.insert(position, remoteViews, itemId, getVisibleWindow(cacheCount));
                            if (notifyWhenLoaded) {
                                Message.obtain(this.mMainHandler, 5, position, 0, remoteViews).sendToTarget();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else {
                        Log.e(TAG, "Error: widget's RemoteViewsFactory returns more view types than  indicated by getViewTypeCount() ");
                    }
                }
                return;
            }
            throw new RuntimeException("Null remoteViews");
        } catch (RemoteException | RuntimeException e) {
            Log.e(TAG, "Error in updateRemoteViews(" + position + "): " + e.getMessage());
        }
    }

    @UnsupportedAppUsage
    public Intent getRemoteViewsServiceIntent() {
        return this.mIntent;
    }

    public int getCount() {
        int i;
        RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            i = metaData.count;
        }
        return i;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        synchronized (this.mCache) {
            if (!this.mCache.containsMetaDataAt(position)) {
                return 0;
            }
            long j = this.mCache.getMetaDataAt(position).itemId;
            return j;
        }
    }

    public int getItemViewType(int position) {
        int mappedViewType;
        synchronized (this.mCache) {
            if (!this.mCache.containsMetaDataAt(position)) {
                return 0;
            }
            int typeId = this.mCache.getMetaDataAt(position).typeId;
            RemoteViewsMetaData metaData = this.mCache.getMetaData();
            synchronized (metaData) {
                mappedViewType = metaData.getMappedViewType(typeId);
            }
            return mappedViewType;
        }
    }

    @UnsupportedAppUsage
    public void setVisibleRangeHint(int lowerBound, int upperBound) {
        this.mVisibleWindowLowerBound = lowerBound;
        this.mVisibleWindowUpperBound = upperBound;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RemoteViewsFrameLayout layout;
        synchronized (this.mCache) {
            RemoteViews rv = this.mCache.getRemoteViewsAt(position);
            boolean isInCache = rv != null;
            boolean hasNewItems = false;
            if (convertView != null && (convertView instanceof RemoteViewsFrameLayout)) {
                this.mRequestedViews.removeView((RemoteViewsFrameLayout) convertView);
            }
            if (!isInCache) {
                requestBindService();
            } else {
                hasNewItems = this.mCache.queuePositionsToBePreloadedFromRequestedPosition(position);
            }
            if (convertView instanceof RemoteViewsFrameLayout) {
                layout = (RemoteViewsFrameLayout) convertView;
            } else {
                layout = new RemoteViewsFrameLayout(parent.getContext(), this.mCache);
                layout.setExecutor(this.mAsyncViewLoadExecutor);
                layout.setOnLightBackground(this.mOnLightBackground);
            }
            if (isInCache) {
                layout.onRemoteViewsLoaded(rv, this.mRemoteViewsOnClickHandler, false);
                if (hasNewItems) {
                    this.mServiceHandler.sendEmptyMessage(3);
                }
            } else {
                layout.onRemoteViewsLoaded(this.mCache.getMetaData().getLoadingTemplate(this.mContext).remoteViews, this.mRemoteViewsOnClickHandler, false);
                this.mRequestedViews.add(position, layout);
                this.mCache.queueRequestedPositionToLoad(position);
                this.mServiceHandler.sendEmptyMessage(3);
            }
        }
        return layout;
    }

    public int getViewTypeCount() {
        int i;
        RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            i = metaData.viewTypeCount;
        }
        return i;
    }

    public boolean hasStableIds() {
        boolean z;
        RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            z = metaData.hasStableIds;
        }
        return z;
    }

    public boolean isEmpty() {
        return getCount() <= 0;
    }

    /* access modifiers changed from: private */
    public int[] getVisibleWindow(int count) {
        int[] window;
        int lower = this.mVisibleWindowLowerBound;
        int upper = this.mVisibleWindowUpperBound;
        int j = 0;
        if ((lower == 0 && upper == 0) || lower < 0 || upper < 0) {
            return new int[0];
        }
        if (lower <= upper) {
            window = new int[((upper + 1) - lower)];
            int i = lower;
            while (i <= upper) {
                window[j] = i;
                i++;
                j++;
            }
        } else {
            int count2 = Math.max(count, lower);
            window = new int[((count2 - lower) + upper + 1)];
            int j2 = 0;
            while (j <= upper) {
                window[j2] = j;
                j++;
                j2++;
            }
            int i2 = lower;
            while (i2 < count2) {
                window[j2] = i2;
                i2++;
                j2++;
            }
        }
        return window;
    }

    public void notifyDataSetChanged() {
        this.mServiceHandler.removeMessages(4);
        this.mServiceHandler.sendEmptyMessage(2);
    }

    /* access modifiers changed from: package-private */
    public void superNotifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                this.mCache.commitTemporaryMetaData();
                return true;
            case 2:
                superNotifyDataSetChanged();
                return true;
            case 3:
                if (this.mCallback != null) {
                    this.mCallback.onRemoteAdapterConnected();
                }
                return true;
            case 4:
                if (this.mCallback != null) {
                    this.mCallback.onRemoteAdapterDisconnected();
                }
                return true;
            case 5:
                this.mRequestedViews.notifyOnRemoteViewsLoaded(msg.arg1, (RemoteViews) msg.obj);
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: private */
    public void requestBindService() {
        this.mServiceHandler.removeMessages(4);
        Message.obtain(this.mServiceHandler, 1, this.mAppWidgetId, 0, this.mIntent).sendToTarget();
    }

    private static class HandlerThreadExecutor implements Executor {
        private final HandlerThread mThread;

        HandlerThreadExecutor(HandlerThread thread) {
            this.mThread = thread;
        }

        public void execute(Runnable runnable) {
            if (Thread.currentThread().getId() == this.mThread.getId()) {
                runnable.run();
            } else {
                new Handler(this.mThread.getLooper()).post(runnable);
            }
        }
    }

    private static class LoadingViewTemplate {
        public int defaultHeight;
        public final RemoteViews remoteViews;

        LoadingViewTemplate(RemoteViews views, Context context) {
            this.remoteViews = views;
            this.defaultHeight = Math.round(50.0f * context.getResources().getDisplayMetrics().density);
        }

        public void loadFirstViewHeight(RemoteViews firstView, Context context, Executor executor) {
            firstView.applyAsync(context, new RemoteViewsFrameLayout(context, (FixedSizeRemoteViewsCache) null), executor, new RemoteViews.OnViewAppliedListener() {
                public void onViewApplied(View v) {
                    try {
                        v.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                        LoadingViewTemplate.this.defaultHeight = v.getMeasuredHeight();
                    } catch (Exception e) {
                        onError(e);
                    }
                }

                public void onError(Exception e) {
                    Log.w(RemoteViewsAdapter.TAG, "Error inflating first RemoteViews", e);
                }
            });
        }
    }
}
