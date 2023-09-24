package android.view;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Pools;

/* loaded from: classes4.dex */
public class MagnificationSpec implements Parcelable {
    private static final int MAX_POOL_SIZE = 20;
    public float offsetX;
    public float offsetY;
    public float scale = 1.0f;
    private static final Pools.SynchronizedPool<MagnificationSpec> sPool = new Pools.SynchronizedPool<>(20);
    public static final Parcelable.Creator<MagnificationSpec> CREATOR = new Parcelable.Creator<MagnificationSpec>() { // from class: android.view.MagnificationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public MagnificationSpec[] newArray(int size) {
            return new MagnificationSpec[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public MagnificationSpec createFromParcel(Parcel parcel) {
            MagnificationSpec spec = MagnificationSpec.obtain();
            spec.initFromParcel(parcel);
            return spec;
        }
    };

    private MagnificationSpec() {
    }

    public void initialize(float scale, float offsetX, float offsetY) {
        if (scale < 1.0f) {
            throw new IllegalArgumentException("Scale must be greater than or equal to one!");
        }
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public boolean isNop() {
        return this.scale == 1.0f && this.offsetX == 0.0f && this.offsetY == 0.0f;
    }

    public static MagnificationSpec obtain(MagnificationSpec other) {
        MagnificationSpec info = obtain();
        info.scale = other.scale;
        info.offsetX = other.offsetX;
        info.offsetY = other.offsetY;
        return info;
    }

    public static MagnificationSpec obtain() {
        MagnificationSpec spec = sPool.acquire();
        return spec != null ? spec : new MagnificationSpec();
    }

    public void recycle() {
        clear();
        sPool.release(this);
    }

    public void clear() {
        this.scale = 1.0f;
        this.offsetX = 0.0f;
        this.offsetY = 0.0f;
    }

    public void setTo(MagnificationSpec other) {
        this.scale = other.scale;
        this.offsetX = other.offsetX;
        this.offsetY = other.offsetY;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.offsetX);
        parcel.writeFloat(this.offsetY);
        recycle();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MagnificationSpec s = (MagnificationSpec) other;
        if (this.scale == s.scale && this.offsetX == s.offsetX && this.offsetY == s.offsetY) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.scale != 0.0f ? Float.floatToIntBits(this.scale) : 0;
        return (((result * 31) + (this.offsetX != 0.0f ? Float.floatToIntBits(this.offsetX) : 0)) * 31) + (this.offsetY != 0.0f ? Float.floatToIntBits(this.offsetY) : 0);
    }

    public String toString() {
        return "<scale:" + Float.toString(this.scale) + ",offsetX:" + Float.toString(this.offsetX) + ",offsetY:" + Float.toString(this.offsetY) + ">";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(Parcel parcel) {
        this.scale = parcel.readFloat();
        this.offsetX = parcel.readFloat();
        this.offsetY = parcel.readFloat();
    }
}
