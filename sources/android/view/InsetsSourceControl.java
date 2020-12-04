package android.view;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

public class InsetsSourceControl implements Parcelable {
    public static final Parcelable.Creator<InsetsSourceControl> CREATOR = new Parcelable.Creator<InsetsSourceControl>() {
        public InsetsSourceControl createFromParcel(Parcel in) {
            return new InsetsSourceControl(in);
        }

        public InsetsSourceControl[] newArray(int size) {
            return new InsetsSourceControl[size];
        }
    };
    private final SurfaceControl mLeash;
    private final Point mSurfacePosition;
    private final int mType;

    public InsetsSourceControl(int type, SurfaceControl leash, Point surfacePosition) {
        this.mType = type;
        this.mLeash = leash;
        this.mSurfacePosition = surfacePosition;
    }

    public int getType() {
        return this.mType;
    }

    public SurfaceControl getLeash() {
        return this.mLeash;
    }

    public InsetsSourceControl(Parcel in) {
        this.mType = in.readInt();
        this.mLeash = (SurfaceControl) in.readParcelable((ClassLoader) null);
        this.mSurfacePosition = (Point) in.readParcelable((ClassLoader) null);
    }

    public boolean setSurfacePosition(int left, int top) {
        if (this.mSurfacePosition.equals(left, top)) {
            return false;
        }
        this.mSurfacePosition.set(left, top);
        return true;
    }

    public Point getSurfacePosition() {
        return this.mSurfacePosition;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeParcelable(this.mLeash, 0);
        dest.writeParcelable(this.mSurfacePosition, 0);
    }
}
