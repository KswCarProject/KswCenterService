package android.hardware.radio;

import android.annotation.SystemApi;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SystemApi
public final class ProgramList implements AutoCloseable {
    private boolean mIsClosed = false;
    private boolean mIsComplete = false;
    private final List<ListCallback> mListCallbacks = new ArrayList();
    private final Object mLock = new Object();
    private OnCloseListener mOnCloseListener;
    private final List<OnCompleteListener> mOnCompleteListeners = new ArrayList();
    private final Map<ProgramSelector.Identifier, RadioManager.ProgramInfo> mPrograms = new HashMap();

    interface OnCloseListener {
        void onClose();
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    ProgramList() {
    }

    public static abstract class ListCallback {
        public void onItemChanged(ProgramSelector.Identifier id) {
        }

        public void onItemRemoved(ProgramSelector.Identifier id) {
        }
    }

    public void registerListCallback(final Executor executor, final ListCallback callback) {
        registerListCallback(new ListCallback() {
            public void onItemChanged(ProgramSelector.Identifier id) {
                executor.execute(new Runnable(id) {
                    private final /* synthetic */ ProgramSelector.Identifier f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        ProgramList.ListCallback.this.onItemChanged(this.f$1);
                    }
                });
            }

            public void onItemRemoved(ProgramSelector.Identifier id) {
                executor.execute(new Runnable(id) {
                    private final /* synthetic */ ProgramSelector.Identifier f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        ProgramList.ListCallback.this.onItemRemoved(this.f$1);
                    }
                });
            }
        });
    }

    public void registerListCallback(ListCallback callback) {
        synchronized (this.mLock) {
            if (!this.mIsClosed) {
                this.mListCallbacks.add((ListCallback) Objects.requireNonNull(callback));
            }
        }
    }

    public void unregisterListCallback(ListCallback callback) {
        synchronized (this.mLock) {
            if (!this.mIsClosed) {
                this.mListCallbacks.remove(Objects.requireNonNull(callback));
            }
        }
    }

    static /* synthetic */ void lambda$addOnCompleteListener$0(Executor executor, OnCompleteListener listener) {
        Objects.requireNonNull(listener);
        executor.execute(new Runnable() {
            public final void run() {
                ProgramList.OnCompleteListener.this.onComplete();
            }
        });
    }

    public void addOnCompleteListener(Executor executor, OnCompleteListener listener) {
        addOnCompleteListener(new OnCompleteListener(executor, listener) {
            private final /* synthetic */ Executor f$0;
            private final /* synthetic */ ProgramList.OnCompleteListener f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void onComplete() {
                ProgramList.lambda$addOnCompleteListener$0(this.f$0, this.f$1);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addOnCompleteListener(android.hardware.radio.ProgramList.OnCompleteListener r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.mIsClosed     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            return
        L_0x0009:
            java.util.List<android.hardware.radio.ProgramList$OnCompleteListener> r1 = r3.mOnCompleteListeners     // Catch:{ all -> 0x001d }
            java.lang.Object r2 = java.util.Objects.requireNonNull(r4)     // Catch:{ all -> 0x001d }
            android.hardware.radio.ProgramList$OnCompleteListener r2 = (android.hardware.radio.ProgramList.OnCompleteListener) r2     // Catch:{ all -> 0x001d }
            r1.add(r2)     // Catch:{ all -> 0x001d }
            boolean r1 = r3.mIsComplete     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x001b
            r4.onComplete()     // Catch:{ all -> 0x001d }
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            return
        L_0x001d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.ProgramList.addOnCompleteListener(android.hardware.radio.ProgramList$OnCompleteListener):void");
    }

    public void removeOnCompleteListener(OnCompleteListener listener) {
        synchronized (this.mLock) {
            if (!this.mIsClosed) {
                this.mOnCompleteListeners.remove(Objects.requireNonNull(listener));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnCloseListener(OnCloseListener listener) {
        synchronized (this.mLock) {
            if (this.mOnCloseListener == null) {
                this.mOnCloseListener = listener;
            } else {
                throw new IllegalStateException("Close callback is already set");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mLock
            monitor-enter(r0)
            boolean r1 = r2.mIsClosed     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            return
        L_0x0009:
            r1 = 1
            r2.mIsClosed = r1     // Catch:{ all -> 0x0029 }
            java.util.Map<android.hardware.radio.ProgramSelector$Identifier, android.hardware.radio.RadioManager$ProgramInfo> r1 = r2.mPrograms     // Catch:{ all -> 0x0029 }
            r1.clear()     // Catch:{ all -> 0x0029 }
            java.util.List<android.hardware.radio.ProgramList$ListCallback> r1 = r2.mListCallbacks     // Catch:{ all -> 0x0029 }
            r1.clear()     // Catch:{ all -> 0x0029 }
            java.util.List<android.hardware.radio.ProgramList$OnCompleteListener> r1 = r2.mOnCompleteListeners     // Catch:{ all -> 0x0029 }
            r1.clear()     // Catch:{ all -> 0x0029 }
            android.hardware.radio.ProgramList$OnCloseListener r1 = r2.mOnCloseListener     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x0027
            android.hardware.radio.ProgramList$OnCloseListener r1 = r2.mOnCloseListener     // Catch:{ all -> 0x0029 }
            r1.onClose()     // Catch:{ all -> 0x0029 }
            r1 = 0
            r2.mOnCloseListener = r1     // Catch:{ all -> 0x0029 }
        L_0x0027:
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            return
        L_0x0029:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.ProgramList.close():void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void apply(android.hardware.radio.ProgramList.Chunk r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.mIsClosed     // Catch:{ all -> 0x005b }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            return
        L_0x0009:
            r1 = 0
            r3.mIsComplete = r1     // Catch:{ all -> 0x005b }
            boolean r1 = r4.isPurge()     // Catch:{ all -> 0x005b }
            if (r1 == 0) goto L_0x0029
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x005b }
            java.util.Map<android.hardware.radio.ProgramSelector$Identifier, android.hardware.radio.RadioManager$ProgramInfo> r2 = r3.mPrograms     // Catch:{ all -> 0x005b }
            java.util.Set r2 = r2.keySet()     // Catch:{ all -> 0x005b }
            r1.<init>(r2)     // Catch:{ all -> 0x005b }
            java.util.stream.Stream r1 = r1.stream()     // Catch:{ all -> 0x005b }
            android.hardware.radio.-$$Lambda$ProgramList$F-JpTj3vYguKIUQbnLbTePTuqUE r2 = new android.hardware.radio.-$$Lambda$ProgramList$F-JpTj3vYguKIUQbnLbTePTuqUE     // Catch:{ all -> 0x005b }
            r2.<init>()     // Catch:{ all -> 0x005b }
            r1.forEach(r2)     // Catch:{ all -> 0x005b }
        L_0x0029:
            java.util.Set r1 = r4.getRemoved()     // Catch:{ all -> 0x005b }
            java.util.stream.Stream r1 = r1.stream()     // Catch:{ all -> 0x005b }
            android.hardware.radio.-$$Lambda$ProgramList$pKu0Zp5jwjix619hfB_Imj8Ke_g r2 = new android.hardware.radio.-$$Lambda$ProgramList$pKu0Zp5jwjix619hfB_Imj8Ke_g     // Catch:{ all -> 0x005b }
            r2.<init>()     // Catch:{ all -> 0x005b }
            r1.forEach(r2)     // Catch:{ all -> 0x005b }
            java.util.Set r1 = r4.getModified()     // Catch:{ all -> 0x005b }
            java.util.stream.Stream r1 = r1.stream()     // Catch:{ all -> 0x005b }
            android.hardware.radio.-$$Lambda$ProgramList$eY050tMTgAcGV9hiWR-UDxhkfhw r2 = new android.hardware.radio.-$$Lambda$ProgramList$eY050tMTgAcGV9hiWR-UDxhkfhw     // Catch:{ all -> 0x005b }
            r2.<init>()     // Catch:{ all -> 0x005b }
            r1.forEach(r2)     // Catch:{ all -> 0x005b }
            boolean r1 = r4.isComplete()     // Catch:{ all -> 0x005b }
            if (r1 == 0) goto L_0x0059
            r1 = 1
            r3.mIsComplete = r1     // Catch:{ all -> 0x005b }
            java.util.List<android.hardware.radio.ProgramList$OnCompleteListener> r1 = r3.mOnCompleteListeners     // Catch:{ all -> 0x005b }
            android.hardware.radio.-$$Lambda$ProgramList$GfCj9jJ5znxw2TV4c2uykq35dgI r2 = android.hardware.radio.$$Lambda$ProgramList$GfCj9jJ5znxw2TV4c2uykq35dgI.INSTANCE     // Catch:{ all -> 0x005b }
            r1.forEach(r2)     // Catch:{ all -> 0x005b }
        L_0x0059:
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            return
        L_0x005b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.ProgramList.apply(android.hardware.radio.ProgramList$Chunk):void");
    }

    /* access modifiers changed from: private */
    public void putLocked(RadioManager.ProgramInfo value) {
        this.mPrograms.put((ProgramSelector.Identifier) Objects.requireNonNull(value.getSelector().getPrimaryId()), value);
        this.mListCallbacks.forEach(new Consumer() {
            public final void accept(Object obj) {
                ((ProgramList.ListCallback) obj).onItemChanged(ProgramSelector.Identifier.this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void removeLocked(ProgramSelector.Identifier key) {
        RadioManager.ProgramInfo removed = this.mPrograms.remove(Objects.requireNonNull(key));
        if (removed != null) {
            this.mListCallbacks.forEach(new Consumer() {
                public final void accept(Object obj) {
                    ((ProgramList.ListCallback) obj).onItemRemoved(ProgramSelector.Identifier.this);
                }
            });
        }
    }

    public List<RadioManager.ProgramInfo> toList() {
        List<RadioManager.ProgramInfo> list;
        synchronized (this.mLock) {
            list = (List) this.mPrograms.values().stream().collect(Collectors.toList());
        }
        return list;
    }

    public RadioManager.ProgramInfo get(ProgramSelector.Identifier id) {
        RadioManager.ProgramInfo programInfo;
        synchronized (this.mLock) {
            programInfo = this.mPrograms.get(Objects.requireNonNull(id));
        }
        return programInfo;
    }

    public static final class Filter implements Parcelable {
        public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
            public Filter createFromParcel(Parcel in) {
                return new Filter(in);
            }

            public Filter[] newArray(int size) {
                return new Filter[size];
            }
        };
        private final boolean mExcludeModifications;
        private final Set<Integer> mIdentifierTypes;
        private final Set<ProgramSelector.Identifier> mIdentifiers;
        private final boolean mIncludeCategories;
        private final Map<String, String> mVendorFilter;

        public Filter(Set<Integer> identifierTypes, Set<ProgramSelector.Identifier> identifiers, boolean includeCategories, boolean excludeModifications) {
            this.mIdentifierTypes = (Set) Objects.requireNonNull(identifierTypes);
            this.mIdentifiers = (Set) Objects.requireNonNull(identifiers);
            this.mIncludeCategories = includeCategories;
            this.mExcludeModifications = excludeModifications;
            this.mVendorFilter = null;
        }

        public Filter() {
            this.mIdentifierTypes = Collections.emptySet();
            this.mIdentifiers = Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = null;
        }

        public Filter(Map<String, String> vendorFilter) {
            this.mIdentifierTypes = Collections.emptySet();
            this.mIdentifiers = Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = vendorFilter;
        }

        private Filter(Parcel in) {
            this.mIdentifierTypes = Utils.createIntSet(in);
            this.mIdentifiers = Utils.createSet(in, ProgramSelector.Identifier.CREATOR);
            boolean z = false;
            this.mIncludeCategories = in.readByte() != 0;
            this.mExcludeModifications = in.readByte() != 0 ? true : z;
            this.mVendorFilter = Utils.readStringMap(in);
        }

        public void writeToParcel(Parcel dest, int flags) {
            Utils.writeIntSet(dest, this.mIdentifierTypes);
            Utils.writeSet(dest, this.mIdentifiers);
            dest.writeByte(this.mIncludeCategories ? (byte) 1 : 0);
            dest.writeByte(this.mExcludeModifications ? (byte) 1 : 0);
            Utils.writeStringMap(dest, this.mVendorFilter);
        }

        public int describeContents() {
            return 0;
        }

        public Map<String, String> getVendorFilter() {
            return this.mVendorFilter;
        }

        public Set<Integer> getIdentifierTypes() {
            return this.mIdentifierTypes;
        }

        public Set<ProgramSelector.Identifier> getIdentifiers() {
            return this.mIdentifiers;
        }

        public boolean areCategoriesIncluded() {
            return this.mIncludeCategories;
        }

        public boolean areModificationsExcluded() {
            return this.mExcludeModifications;
        }
    }

    public static final class Chunk implements Parcelable {
        public static final Parcelable.Creator<Chunk> CREATOR = new Parcelable.Creator<Chunk>() {
            public Chunk createFromParcel(Parcel in) {
                return new Chunk(in);
            }

            public Chunk[] newArray(int size) {
                return new Chunk[size];
            }
        };
        private final boolean mComplete;
        private final Set<RadioManager.ProgramInfo> mModified;
        private final boolean mPurge;
        private final Set<ProgramSelector.Identifier> mRemoved;

        public Chunk(boolean purge, boolean complete, Set<RadioManager.ProgramInfo> modified, Set<ProgramSelector.Identifier> removed) {
            this.mPurge = purge;
            this.mComplete = complete;
            this.mModified = modified != null ? modified : Collections.emptySet();
            this.mRemoved = removed != null ? removed : Collections.emptySet();
        }

        private Chunk(Parcel in) {
            boolean z = false;
            this.mPurge = in.readByte() != 0;
            this.mComplete = in.readByte() != 0 ? true : z;
            this.mModified = Utils.createSet(in, RadioManager.ProgramInfo.CREATOR);
            this.mRemoved = Utils.createSet(in, ProgramSelector.Identifier.CREATOR);
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.mPurge ? (byte) 1 : 0);
            dest.writeByte(this.mComplete ? (byte) 1 : 0);
            Utils.writeSet(dest, this.mModified);
            Utils.writeSet(dest, this.mRemoved);
        }

        public int describeContents() {
            return 0;
        }

        public boolean isPurge() {
            return this.mPurge;
        }

        public boolean isComplete() {
            return this.mComplete;
        }

        public Set<RadioManager.ProgramInfo> getModified() {
            return this.mModified;
        }

        public Set<ProgramSelector.Identifier> getRemoved() {
            return this.mRemoved;
        }
    }
}
