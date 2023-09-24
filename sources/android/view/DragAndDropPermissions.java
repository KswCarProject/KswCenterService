package android.view;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import com.android.internal.view.IDragAndDropPermissions;

/* loaded from: classes4.dex */
public final class DragAndDropPermissions implements Parcelable {
    public static final Parcelable.Creator<DragAndDropPermissions> CREATOR = new Parcelable.Creator<DragAndDropPermissions>() { // from class: android.view.DragAndDropPermissions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public DragAndDropPermissions createFromParcel(Parcel source) {
            return new DragAndDropPermissions(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public DragAndDropPermissions[] newArray(int size) {
            return new DragAndDropPermissions[size];
        }
    };
    private final IDragAndDropPermissions mDragAndDropPermissions;
    private IBinder mTransientToken;

    public static DragAndDropPermissions obtain(DragEvent dragEvent) {
        if (dragEvent.getDragAndDropPermissions() == null) {
            return null;
        }
        return new DragAndDropPermissions(dragEvent.getDragAndDropPermissions());
    }

    private DragAndDropPermissions(IDragAndDropPermissions dragAndDropPermissions) {
        this.mDragAndDropPermissions = dragAndDropPermissions;
    }

    public boolean take(IBinder activityToken) {
        try {
            this.mDragAndDropPermissions.take(activityToken);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean takeTransient() {
        try {
            this.mTransientToken = new Binder();
            this.mDragAndDropPermissions.takeTransient(this.mTransientToken);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public void release() {
        try {
            this.mDragAndDropPermissions.release();
            this.mTransientToken = null;
        } catch (RemoteException e) {
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeStrongInterface(this.mDragAndDropPermissions);
        destination.writeStrongBinder(this.mTransientToken);
    }

    private DragAndDropPermissions(Parcel in) {
        this.mDragAndDropPermissions = IDragAndDropPermissions.Stub.asInterface(in.readStrongBinder());
        this.mTransientToken = in.readStrongBinder();
    }
}
