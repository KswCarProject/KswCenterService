package android.text;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes4.dex */
public interface ParcelableSpan extends Parcelable {
    int getSpanTypeId();

    int getSpanTypeIdInternal();

    void writeToParcelInternal(Parcel parcel, int i);
}
