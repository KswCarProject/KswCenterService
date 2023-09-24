package android.graphics;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes.dex */
public class PointF implements Parcelable {
    public static final Parcelable.Creator<PointF> CREATOR = new Parcelable.Creator<PointF>() { // from class: android.graphics.PointF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PointF createFromParcel(Parcel in) {
            PointF r = new PointF();
            r.readFromParcel(in);
            return r;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PointF[] newArray(int size) {
            return new PointF[size];
        }
    };

    /* renamed from: x */
    public float f61x;

    /* renamed from: y */
    public float f62y;

    public PointF() {
    }

    public PointF(float x, float y) {
        this.f61x = x;
        this.f62y = y;
    }

    public PointF(Point p) {
        this.f61x = p.f59x;
        this.f62y = p.f60y;
    }

    public final void set(float x, float y) {
        this.f61x = x;
        this.f62y = y;
    }

    public final void set(PointF p) {
        this.f61x = p.f61x;
        this.f62y = p.f62y;
    }

    public final void negate() {
        this.f61x = -this.f61x;
        this.f62y = -this.f62y;
    }

    public final void offset(float dx, float dy) {
        this.f61x += dx;
        this.f62y += dy;
    }

    public final boolean equals(float x, float y) {
        return this.f61x == x && this.f62y == y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointF pointF = (PointF) o;
        if (Float.compare(pointF.f61x, this.f61x) == 0 && Float.compare(pointF.f62y, this.f62y) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.f61x != 0.0f ? Float.floatToIntBits(this.f61x) : 0;
        return (result * 31) + (this.f62y != 0.0f ? Float.floatToIntBits(this.f62y) : 0);
    }

    public String toString() {
        return "PointF(" + this.f61x + ", " + this.f62y + ")";
    }

    public final float length() {
        return length(this.f61x, this.f62y);
    }

    public static float length(float x, float y) {
        return (float) Math.hypot(x, y);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(this.f61x);
        out.writeFloat(this.f62y);
    }

    public void readFromParcel(Parcel in) {
        this.f61x = in.readFloat();
        this.f62y = in.readFloat();
    }
}
