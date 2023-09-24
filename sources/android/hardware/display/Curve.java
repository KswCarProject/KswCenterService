package android.hardware.display;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes.dex */
public final class Curve implements Parcelable {
    public static final Parcelable.Creator<Curve> CREATOR = new Parcelable.Creator<Curve>() { // from class: android.hardware.display.Curve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public Curve createFromParcel(Parcel in) {
            float[] x = in.createFloatArray();
            float[] y = in.createFloatArray();
            return new Curve(x, y);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public Curve[] newArray(int size) {
            return new Curve[size];
        }
    };

    /* renamed from: mX */
    private final float[] f80mX;

    /* renamed from: mY */
    private final float[] f81mY;

    public Curve(float[] x, float[] y) {
        this.f80mX = x;
        this.f81mY = y;
    }

    public float[] getX() {
        return this.f80mX;
    }

    public float[] getY() {
        return this.f81mY;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(this.f80mX);
        out.writeFloatArray(this.f81mY);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int size = this.f80mX.length;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append("(");
            sb.append(this.f80mX[i]);
            sb.append(", ");
            sb.append(this.f81mY[i]);
            sb.append(")");
        }
        sb.append("]");
        return sb.toString();
    }
}
