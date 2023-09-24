package android.content;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes.dex */
public abstract class UndoOperation<DATA> implements Parcelable {
    UndoOwner mOwner;

    public abstract void commit();

    public abstract void redo();

    public abstract void undo();

    @UnsupportedAppUsage
    public UndoOperation(UndoOwner owner) {
        this.mOwner = owner;
    }

    @UnsupportedAppUsage
    protected UndoOperation(Parcel src, ClassLoader loader) {
    }

    public UndoOwner getOwner() {
        return this.mOwner;
    }

    public DATA getOwnerData() {
        return (DATA) this.mOwner.getData();
    }

    public boolean matchOwner(UndoOwner owner) {
        return owner == getOwner();
    }

    public boolean hasData() {
        return true;
    }

    public boolean allowMerge() {
        return true;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
