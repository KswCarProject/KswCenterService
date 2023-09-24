package com.android.internal.p016os;

import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.Parcelable;
import com.android.internal.util.Preconditions;

/* renamed from: com.android.internal.os.AppFuseMount */
/* loaded from: classes4.dex */
public class AppFuseMount implements Parcelable {
    public static final Parcelable.Creator<AppFuseMount> CREATOR = new Parcelable.Creator<AppFuseMount>() { // from class: com.android.internal.os.AppFuseMount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public AppFuseMount createFromParcel(Parcel in) {
            return new AppFuseMount(in.readInt(), (ParcelFileDescriptor) in.readParcelable(null));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public AppFuseMount[] newArray(int size) {
            return new AppFuseMount[size];
        }
    };

    /* renamed from: fd */
    public final ParcelFileDescriptor f2485fd;
    public final int mountPointId;

    public AppFuseMount(int mountPointId, ParcelFileDescriptor fd) {
        Preconditions.checkNotNull(fd);
        this.mountPointId = mountPointId;
        this.f2485fd = fd;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mountPointId);
        dest.writeParcelable(this.f2485fd, flags);
    }
}
