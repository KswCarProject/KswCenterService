package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class ExportResult implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<ExportResult> CREATOR = new Parcelable.Creator<ExportResult>() {
        public ExportResult createFromParcel(Parcel in) {
            return new ExportResult(in);
        }

        public ExportResult[] newArray(int length) {
            return new ExportResult[length];
        }
    };
    public final byte[] exportData;
    public final int resultCode;

    public ExportResult(int resultCode2) {
        this.resultCode = resultCode2;
        this.exportData = new byte[0];
    }

    protected ExportResult(Parcel in) {
        this.resultCode = in.readInt();
        this.exportData = in.createByteArray();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.resultCode);
        out.writeByteArray(this.exportData);
    }
}
