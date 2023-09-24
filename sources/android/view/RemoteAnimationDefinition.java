package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.ArraySet;
import android.util.SparseArray;

/* loaded from: classes4.dex */
public class RemoteAnimationDefinition implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationDefinition> CREATOR = new Parcelable.Creator<RemoteAnimationDefinition>() { // from class: android.view.RemoteAnimationDefinition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RemoteAnimationDefinition createFromParcel(Parcel in) {
            return new RemoteAnimationDefinition(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RemoteAnimationDefinition[] newArray(int size) {
            return new RemoteAnimationDefinition[size];
        }
    };
    private final SparseArray<RemoteAnimationAdapterEntry> mTransitionAnimationMap;

    @UnsupportedAppUsage
    public RemoteAnimationDefinition() {
        this.mTransitionAnimationMap = new SparseArray<>();
    }

    @UnsupportedAppUsage
    public void addRemoteAnimation(int transition, @WindowConfiguration.ActivityType int activityTypeFilter, RemoteAnimationAdapter adapter) {
        this.mTransitionAnimationMap.put(transition, new RemoteAnimationAdapterEntry(adapter, activityTypeFilter));
    }

    @UnsupportedAppUsage
    public void addRemoteAnimation(int transition, RemoteAnimationAdapter adapter) {
        addRemoteAnimation(transition, 0, adapter);
    }

    public boolean hasTransition(int transition, ArraySet<Integer> activityTypes) {
        return getAdapter(transition, activityTypes) != null;
    }

    public RemoteAnimationAdapter getAdapter(int transition, ArraySet<Integer> activityTypes) {
        RemoteAnimationAdapterEntry entry = this.mTransitionAnimationMap.get(transition);
        if (entry == null) {
            return null;
        }
        if (entry.activityTypeFilter != 0 && !activityTypes.contains(Integer.valueOf(entry.activityTypeFilter))) {
            return null;
        }
        return entry.adapter;
    }

    public RemoteAnimationDefinition(Parcel in) {
        int size = in.readInt();
        this.mTransitionAnimationMap = new SparseArray<>(size);
        for (int i = 0; i < size; i++) {
            int transition = in.readInt();
            RemoteAnimationAdapterEntry entry = (RemoteAnimationAdapterEntry) in.readTypedObject(RemoteAnimationAdapterEntry.CREATOR);
            this.mTransitionAnimationMap.put(transition, entry);
        }
    }

    public void setCallingPid(int pid) {
        for (int i = this.mTransitionAnimationMap.size() - 1; i >= 0; i--) {
            this.mTransitionAnimationMap.valueAt(i).adapter.setCallingPid(pid);
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int size = this.mTransitionAnimationMap.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            dest.writeInt(this.mTransitionAnimationMap.keyAt(i));
            dest.writeTypedObject(this.mTransitionAnimationMap.valueAt(i), flags);
        }
    }

    /* loaded from: classes4.dex */
    private static class RemoteAnimationAdapterEntry implements Parcelable {
        private static final Parcelable.Creator<RemoteAnimationAdapterEntry> CREATOR = new Parcelable.Creator<RemoteAnimationAdapterEntry>() { // from class: android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public RemoteAnimationAdapterEntry createFromParcel(Parcel in) {
                return new RemoteAnimationAdapterEntry(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public RemoteAnimationAdapterEntry[] newArray(int size) {
                return new RemoteAnimationAdapterEntry[size];
            }
        };
        @WindowConfiguration.ActivityType
        final int activityTypeFilter;
        final RemoteAnimationAdapter adapter;

        RemoteAnimationAdapterEntry(RemoteAnimationAdapter adapter, int activityTypeFilter) {
            this.adapter = adapter;
            this.activityTypeFilter = activityTypeFilter;
        }

        private RemoteAnimationAdapterEntry(Parcel in) {
            this.adapter = (RemoteAnimationAdapter) in.readParcelable(RemoteAnimationAdapter.class.getClassLoader());
            this.activityTypeFilter = in.readInt();
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.adapter, flags);
            dest.writeInt(this.activityTypeFilter);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
