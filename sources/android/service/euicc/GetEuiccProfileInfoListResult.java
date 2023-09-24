package android.service.euicc;

import android.annotation.SystemApi;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import java.util.Arrays;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class GetEuiccProfileInfoListResult implements Parcelable {
    public static final Parcelable.Creator<GetEuiccProfileInfoListResult> CREATOR = new Parcelable.Creator<GetEuiccProfileInfoListResult>() { // from class: android.service.euicc.GetEuiccProfileInfoListResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public GetEuiccProfileInfoListResult createFromParcel(Parcel in) {
            return new GetEuiccProfileInfoListResult(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public GetEuiccProfileInfoListResult[] newArray(int size) {
            return new GetEuiccProfileInfoListResult[size];
        }
    };
    private final boolean mIsRemovable;
    private final EuiccProfileInfo[] mProfiles;
    @Deprecated
    public final int result;

    public int getResult() {
        return this.result;
    }

    public List<EuiccProfileInfo> getProfiles() {
        if (this.mProfiles == null) {
            return null;
        }
        return Arrays.asList(this.mProfiles);
    }

    public boolean getIsRemovable() {
        return this.mIsRemovable;
    }

    public GetEuiccProfileInfoListResult(int result, EuiccProfileInfo[] profiles, boolean isRemovable) {
        this.result = result;
        this.mIsRemovable = isRemovable;
        if (this.result == 0) {
            this.mProfiles = profiles;
        } else if (profiles != null && profiles.length > 0) {
            throw new IllegalArgumentException("Error result with non-empty profiles: " + result);
        } else {
            this.mProfiles = null;
        }
    }

    private GetEuiccProfileInfoListResult(Parcel in) {
        this.result = in.readInt();
        this.mProfiles = (EuiccProfileInfo[]) in.createTypedArray(EuiccProfileInfo.CREATOR);
        this.mIsRemovable = in.readBoolean();
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeTypedArray(this.mProfiles, flags);
        dest.writeBoolean(this.mIsRemovable);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
