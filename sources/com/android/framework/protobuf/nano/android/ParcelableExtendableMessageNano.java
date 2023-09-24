package com.android.framework.protobuf.nano.android;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.framework.protobuf.nano.ExtendableMessageNano;

/* loaded from: classes4.dex */
public abstract class ParcelableExtendableMessageNano<M extends ExtendableMessageNano<M>> extends ExtendableMessageNano<M> implements Parcelable {
    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ParcelableMessageNanoCreator.writeToParcel(getClass(), this, out);
    }
}
