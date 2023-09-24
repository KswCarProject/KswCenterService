package android.support.p011v4.p013os;

import android.p007os.Parcel;

@Deprecated
/* renamed from: android.support.v4.os.ParcelableCompatCreatorCallbacks */
/* loaded from: classes3.dex */
public interface ParcelableCompatCreatorCallbacks<T> {
    T createFromParcel(Parcel parcel, ClassLoader classLoader);

    T[] newArray(int i);
}
