package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class ResultInfo implements Parcelable {
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static final Parcelable.Creator<ResultInfo> CREATOR = new Parcelable.Creator<ResultInfo>() {
        public ResultInfo createFromParcel(Parcel in) {
            return new ResultInfo(in);
        }

        public ResultInfo[] newArray(int size) {
            return new ResultInfo[size];
        }
    };
    @UnsupportedAppUsage
    public final Intent mData;
    @UnsupportedAppUsage
    public final int mRequestCode;
    public final int mResultCode;
    @UnsupportedAppUsage
    public final String mResultWho;

    @UnsupportedAppUsage
    public ResultInfo(String resultWho, int requestCode, int resultCode, Intent data) {
        this.mResultWho = resultWho;
        this.mRequestCode = requestCode;
        this.mResultCode = resultCode;
        this.mData = data;
    }

    public String toString() {
        return "ResultInfo{who=" + this.mResultWho + ", request=" + this.mRequestCode + ", result=" + this.mResultCode + ", data=" + this.mData + "}";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mResultWho);
        out.writeInt(this.mRequestCode);
        out.writeInt(this.mResultCode);
        if (this.mData != null) {
            out.writeInt(1);
            this.mData.writeToParcel(out, 0);
            return;
        }
        out.writeInt(0);
    }

    public ResultInfo(Parcel in) {
        this.mResultWho = in.readString();
        this.mRequestCode = in.readInt();
        this.mResultCode = in.readInt();
        if (in.readInt() != 0) {
            this.mData = Intent.CREATOR.createFromParcel(in);
        } else {
            this.mData = null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ResultInfo)) {
            return false;
        }
        ResultInfo other = (ResultInfo) obj;
        if (!(this.mData == null ? other.mData == null : this.mData.filterEquals(other.mData)) || !Objects.equals(this.mResultWho, other.mResultWho) || this.mResultCode != other.mResultCode || this.mRequestCode != other.mRequestCode) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = (((((17 * 31) + this.mRequestCode) * 31) + this.mResultCode) * 31) + Objects.hashCode(this.mResultWho);
        if (this.mData != null) {
            return (result * 31) + this.mData.filterHashCode();
        }
        return result;
    }
}
