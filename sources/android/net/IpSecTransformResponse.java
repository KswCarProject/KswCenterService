package android.net;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes3.dex */
public final class IpSecTransformResponse implements Parcelable {
    public static final Parcelable.Creator<IpSecTransformResponse> CREATOR = new Parcelable.Creator<IpSecTransformResponse>() { // from class: android.net.IpSecTransformResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public IpSecTransformResponse createFromParcel(Parcel in) {
            return new IpSecTransformResponse(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public IpSecTransformResponse[] newArray(int size) {
            return new IpSecTransformResponse[size];
        }
    };
    private static final String TAG = "IpSecTransformResponse";
    public final int resourceId;
    public final int status;

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.status);
        out.writeInt(this.resourceId);
    }

    public IpSecTransformResponse(int inStatus) {
        if (inStatus == 0) {
            throw new IllegalArgumentException("Valid status implies other args must be provided");
        }
        this.status = inStatus;
        this.resourceId = -1;
    }

    public IpSecTransformResponse(int inStatus, int inResourceId) {
        this.status = inStatus;
        this.resourceId = inResourceId;
    }

    private IpSecTransformResponse(Parcel in) {
        this.status = in.readInt();
        this.resourceId = in.readInt();
    }
}
