package android.service.autofill;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Pair;
import android.util.SparseArray;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class CustomDescription implements Parcelable {
    public static final Parcelable.Creator<CustomDescription> CREATOR = new Parcelable.Creator<CustomDescription>() { // from class: android.service.autofill.CustomDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public CustomDescription createFromParcel(Parcel parcel) {
            RemoteViews parentPresentation = (RemoteViews) parcel.readParcelable(null);
            if (parentPresentation == null) {
                return null;
            }
            Builder builder = new Builder(parentPresentation);
            int[] transformationIds = parcel.createIntArray();
            if (transformationIds != null) {
                InternalTransformation[] values = (InternalTransformation[]) parcel.readParcelableArray(null, InternalTransformation.class);
                int size = transformationIds.length;
                for (int i = 0; i < size; i++) {
                    builder.addChild(transformationIds[i], values[i]);
                }
            }
            InternalValidator[] conditions = (InternalValidator[]) parcel.readParcelableArray(null, InternalValidator.class);
            if (conditions != null) {
                BatchUpdates[] updates = (BatchUpdates[]) parcel.readParcelableArray(null, BatchUpdates.class);
                int size2 = conditions.length;
                for (int i2 = 0; i2 < size2; i2++) {
                    builder.batchUpdate(conditions[i2], updates[i2]);
                }
            }
            int[] actionIds = parcel.createIntArray();
            if (actionIds != null) {
                InternalOnClickAction[] values2 = (InternalOnClickAction[]) parcel.readParcelableArray(null, InternalOnClickAction.class);
                int size3 = actionIds.length;
                for (int i3 = 0; i3 < size3; i3++) {
                    builder.addOnClickAction(actionIds[i3], values2[i3]);
                }
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public CustomDescription[] newArray(int size) {
            return new CustomDescription[size];
        }
    };
    private final SparseArray<InternalOnClickAction> mActions;
    private final RemoteViews mPresentation;
    private final ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
    private final ArrayList<Pair<InternalValidator, BatchUpdates>> mUpdates;

    private CustomDescription(Builder builder) {
        this.mPresentation = builder.mPresentation;
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
        this.mActions = builder.mActions;
    }

    public RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public ArrayList<Pair<Integer, InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public ArrayList<Pair<InternalValidator, BatchUpdates>> getUpdates() {
        return this.mUpdates;
    }

    public SparseArray<InternalOnClickAction> getActions() {
        return this.mActions;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private SparseArray<InternalOnClickAction> mActions;
        private boolean mDestroyed;
        private final RemoteViews mPresentation;
        private ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
        private ArrayList<Pair<InternalValidator, BatchUpdates>> mUpdates;

        public Builder(RemoteViews parentPresentation) {
            this.mPresentation = (RemoteViews) Preconditions.checkNotNull(parentPresentation);
        }

        public Builder addChild(int id, Transformation transformation) {
            throwIfDestroyed();
            Preconditions.checkArgument(transformation instanceof InternalTransformation, "not provided by Android System: " + transformation);
            if (this.mTransformations == null) {
                this.mTransformations = new ArrayList<>();
            }
            this.mTransformations.add(new Pair<>(Integer.valueOf(id), (InternalTransformation) transformation));
            return this;
        }

        public Builder batchUpdate(Validator condition, BatchUpdates updates) {
            throwIfDestroyed();
            Preconditions.checkArgument(condition instanceof InternalValidator, "not provided by Android System: " + condition);
            Preconditions.checkNotNull(updates);
            if (this.mUpdates == null) {
                this.mUpdates = new ArrayList<>();
            }
            this.mUpdates.add(new Pair<>((InternalValidator) condition, updates));
            return this;
        }

        public Builder addOnClickAction(int id, OnClickAction action) {
            throwIfDestroyed();
            Preconditions.checkArgument(action instanceof InternalOnClickAction, "not provided by Android System: " + action);
            if (this.mActions == null) {
                this.mActions = new SparseArray<>();
            }
            this.mActions.put(id, (InternalOnClickAction) action);
            return this;
        }

        public CustomDescription build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new CustomDescription(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("Already called #build()");
            }
        }
    }

    public String toString() {
        if (Helper.sDebug) {
            StringBuilder sb = new StringBuilder("CustomDescription: [presentation=");
            sb.append(this.mPresentation);
            sb.append(", transformations=");
            sb.append(this.mTransformations == null ? "N/A" : Integer.valueOf(this.mTransformations.size()));
            sb.append(", updates=");
            sb.append(this.mUpdates == null ? "N/A" : Integer.valueOf(this.mUpdates.size()));
            sb.append(", actions=");
            sb.append(this.mActions == null ? "N/A" : Integer.valueOf(this.mActions.size()));
            sb.append("]");
            return sb.toString();
        }
        return super.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPresentation, flags);
        if (this.mPresentation == null) {
            return;
        }
        if (this.mTransformations == null) {
            dest.writeIntArray(null);
        } else {
            int size = this.mTransformations.size();
            int[] ids = new int[size];
            InternalTransformation[] values = new InternalTransformation[size];
            for (int i = 0; i < size; i++) {
                Pair<Integer, InternalTransformation> pair = this.mTransformations.get(i);
                ids[i] = pair.first.intValue();
                values[i] = pair.second;
            }
            dest.writeIntArray(ids);
            dest.writeParcelableArray(values, flags);
        }
        if (this.mUpdates == null) {
            dest.writeParcelableArray(null, flags);
        } else {
            int size2 = this.mUpdates.size();
            InternalValidator[] conditions = new InternalValidator[size2];
            BatchUpdates[] updates = new BatchUpdates[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                Pair<InternalValidator, BatchUpdates> pair2 = this.mUpdates.get(i2);
                conditions[i2] = pair2.first;
                updates[i2] = pair2.second;
            }
            dest.writeParcelableArray(conditions, flags);
            dest.writeParcelableArray(updates, flags);
        }
        if (this.mActions == null) {
            dest.writeIntArray(null);
            return;
        }
        int size3 = this.mActions.size();
        int[] ids2 = new int[size3];
        InternalOnClickAction[] values2 = new InternalOnClickAction[size3];
        for (int i3 = 0; i3 < size3; i3++) {
            ids2[i3] = this.mActions.keyAt(i3);
            values2[i3] = this.mActions.valueAt(i3);
        }
        dest.writeIntArray(ids2);
        dest.writeParcelableArray(values2, flags);
    }
}
