package android.graphics;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.telephony.SmsManager;
import android.util.Size;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class Point implements Parcelable {
    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() { // from class: android.graphics.Point.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public Point createFromParcel(Parcel in) {
            Point r = new Point();
            r.readFromParcel(in);
            return r;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    /* renamed from: x */
    public int f59x;

    /* renamed from: y */
    public int f60y;

    public Point() {
    }

    public Point(int x, int y) {
        this.f59x = x;
        this.f60y = y;
    }

    public Point(Point src) {
        this.f59x = src.f59x;
        this.f60y = src.f60y;
    }

    public void set(int x, int y) {
        this.f59x = x;
        this.f60y = y;
    }

    public final void negate() {
        this.f59x = -this.f59x;
        this.f60y = -this.f60y;
    }

    public final void offset(int dx, int dy) {
        this.f59x += dx;
        this.f60y += dy;
    }

    public final boolean equals(int x, int y) {
        return this.f59x == x && this.f60y == y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        if (this.f59x == point.f59x && this.f60y == point.f60y) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.f59x;
        return (result * 31) + this.f60y;
    }

    public String toString() {
        return "Point(" + this.f59x + ", " + this.f60y + ")";
    }

    public void printShortString(PrintWriter pw) {
        pw.print("[");
        pw.print(this.f59x);
        pw.print(SmsManager.REGEX_PREFIX_DELIMITER);
        pw.print(this.f60y);
        pw.print("]");
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.f59x);
        out.writeInt(this.f60y);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long fieldId) {
        long token = protoOutputStream.start(fieldId);
        protoOutputStream.write(1120986464257L, this.f59x);
        protoOutputStream.write(1120986464258L, this.f60y);
        protoOutputStream.end(token);
    }

    public void readFromParcel(Parcel in) {
        this.f59x = in.readInt();
        this.f60y = in.readInt();
    }

    public static Point convert(Size size) {
        return new Point(size.getWidth(), size.getHeight());
    }

    public static Size convert(Point point) {
        return new Size(point.f59x, point.f60y);
    }
}
