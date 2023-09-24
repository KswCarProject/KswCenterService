package android.p007os;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcelable;
import android.util.MathUtils;

/* renamed from: android.os.ParcelableParcel */
/* loaded from: classes3.dex */
public class ParcelableParcel implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.ClassLoaderCreator<ParcelableParcel> CREATOR = new Parcelable.ClassLoaderCreator<ParcelableParcel>() { // from class: android.os.ParcelableParcel.1
        @Override // android.p007os.Parcelable.Creator
        public ParcelableParcel createFromParcel(Parcel in) {
            return new ParcelableParcel(in, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.ClassLoaderCreator
        public ParcelableParcel createFromParcel(Parcel in, ClassLoader loader) {
            return new ParcelableParcel(in, loader);
        }

        @Override // android.p007os.Parcelable.Creator
        public ParcelableParcel[] newArray(int size) {
            return new ParcelableParcel[size];
        }
    };
    final ClassLoader mClassLoader;
    final Parcel mParcel = Parcel.obtain();

    @UnsupportedAppUsage
    public ParcelableParcel(ClassLoader loader) {
        this.mClassLoader = loader;
    }

    public ParcelableParcel(Parcel src, ClassLoader loader) {
        this.mClassLoader = loader;
        int size = src.readInt();
        if (size < 0) {
            throw new IllegalArgumentException("Negative size read from parcel");
        }
        int pos = src.dataPosition();
        src.setDataPosition(MathUtils.addOrThrow(pos, size));
        this.mParcel.appendFrom(src, pos, size);
    }

    @UnsupportedAppUsage
    public Parcel getParcel() {
        this.mParcel.setDataPosition(0);
        return this.mParcel;
    }

    @UnsupportedAppUsage
    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mParcel.dataSize());
        dest.appendFrom(this.mParcel, 0, this.mParcel.dataSize());
    }
}
