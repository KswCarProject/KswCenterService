package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import java.lang.ref.WeakReference;

abstract class ThemedResourceCache<T> {
    private LongSparseArray<WeakReference<T>> mNullThemedEntries;
    @UnsupportedAppUsage
    private ArrayMap<Resources.ThemeKey, LongSparseArray<WeakReference<T>>> mThemedEntries;
    private LongSparseArray<WeakReference<T>> mUnthemedEntries;

    /* access modifiers changed from: protected */
    public abstract boolean shouldInvalidateEntry(T t, int i);

    ThemedResourceCache() {
    }

    public void put(long key, Resources.Theme theme, T entry) {
        put(key, theme, entry, true);
    }

    public void put(long key, Resources.Theme theme, T entry, boolean usesTheme) {
        LongSparseArray<WeakReference<T>> entries;
        if (entry != null) {
            synchronized (this) {
                if (!usesTheme) {
                    try {
                        entries = getUnthemedLocked(true);
                    } catch (Throwable th) {
                        throw th;
                    }
                } else {
                    entries = getThemedLocked(theme, true);
                }
                if (entries != null) {
                    entries.put(key, new WeakReference(entry));
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T get(long r5, android.content.res.Resources.Theme r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            android.util.LongSparseArray r1 = r4.getThemedLocked(r7, r0)     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x0016
            java.lang.Object r2 = r1.get(r5)     // Catch:{ all -> 0x002d }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x0016
            java.lang.Object r0 = r2.get()     // Catch:{ all -> 0x002d }
            monitor-exit(r4)     // Catch:{ all -> 0x002d }
            return r0
        L_0x0016:
            android.util.LongSparseArray r0 = r4.getUnthemedLocked(r0)     // Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x002a
            java.lang.Object r2 = r0.get(r5)     // Catch:{ all -> 0x002d }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x002a
            java.lang.Object r3 = r2.get()     // Catch:{ all -> 0x002d }
            monitor-exit(r4)     // Catch:{ all -> 0x002d }
            return r3
        L_0x002a:
            monitor-exit(r4)     // Catch:{ all -> 0x002d }
            r0 = 0
            return r0
        L_0x002d:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x002d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ThemedResourceCache.get(long, android.content.res.Resources$Theme):java.lang.Object");
    }

    @UnsupportedAppUsage
    public void onConfigurationChange(int configChanges) {
        prune(configChanges);
    }

    private LongSparseArray<WeakReference<T>> getThemedLocked(Resources.Theme t, boolean create) {
        if (t == null) {
            if (this.mNullThemedEntries == null && create) {
                this.mNullThemedEntries = new LongSparseArray<>(1);
            }
            return this.mNullThemedEntries;
        }
        if (this.mThemedEntries == null) {
            if (!create) {
                return null;
            }
            this.mThemedEntries = new ArrayMap<>(1);
        }
        Resources.ThemeKey key = t.getKey();
        LongSparseArray<WeakReference<T>> cache = this.mThemedEntries.get(key);
        if (cache != null || !create) {
            return cache;
        }
        LongSparseArray<WeakReference<T>> cache2 = new LongSparseArray<>(1);
        this.mThemedEntries.put(key.clone(), cache2);
        return cache2;
    }

    private LongSparseArray<WeakReference<T>> getUnthemedLocked(boolean create) {
        if (this.mUnthemedEntries == null && create) {
            this.mUnthemedEntries = new LongSparseArray<>(1);
        }
        return this.mUnthemedEntries;
    }

    private boolean prune(int configChanges) {
        boolean z;
        synchronized (this) {
            z = true;
            if (this.mThemedEntries != null) {
                for (int i = this.mThemedEntries.size() - 1; i >= 0; i--) {
                    if (pruneEntriesLocked(this.mThemedEntries.valueAt(i), configChanges)) {
                        this.mThemedEntries.removeAt(i);
                    }
                }
            }
            pruneEntriesLocked(this.mNullThemedEntries, configChanges);
            pruneEntriesLocked(this.mUnthemedEntries, configChanges);
            if (this.mThemedEntries != null || this.mNullThemedEntries != null || this.mUnthemedEntries != null) {
                z = false;
            }
        }
        return z;
    }

    private boolean pruneEntriesLocked(LongSparseArray<WeakReference<T>> entries, int configChanges) {
        if (entries == null) {
            return true;
        }
        for (int i = entries.size() - 1; i >= 0; i--) {
            WeakReference<T> ref = entries.valueAt(i);
            if (ref == null || pruneEntryLocked(ref.get(), configChanges)) {
                entries.removeAt(i);
            }
        }
        if (entries.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean pruneEntryLocked(T entry, int configChanges) {
        return entry == null || (configChanges != 0 && shouldInvalidateEntry(entry, configChanges));
    }
}
