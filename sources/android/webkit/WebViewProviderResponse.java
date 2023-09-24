package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.content.p002pm.PackageInfo;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes4.dex */
public final class WebViewProviderResponse implements Parcelable {
    public static final Parcelable.Creator<WebViewProviderResponse> CREATOR = new Parcelable.Creator<WebViewProviderResponse>() { // from class: android.webkit.WebViewProviderResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public WebViewProviderResponse createFromParcel(Parcel in) {
            return new WebViewProviderResponse(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public WebViewProviderResponse[] newArray(int size) {
            return new WebViewProviderResponse[size];
        }
    };
    @UnsupportedAppUsage
    public final PackageInfo packageInfo;
    public final int status;

    public WebViewProviderResponse(PackageInfo packageInfo, int status) {
        this.packageInfo = packageInfo;
        this.status = status;
    }

    private WebViewProviderResponse(Parcel in) {
        this.packageInfo = (PackageInfo) in.readTypedObject(PackageInfo.CREATOR);
        this.status = in.readInt();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedObject(this.packageInfo, flags);
        out.writeInt(this.status);
    }
}
