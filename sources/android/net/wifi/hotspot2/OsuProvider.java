package android.net.wifi.hotspot2;

import android.annotation.SystemApi;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.net.wifi.WifiSsid;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class OsuProvider implements Parcelable {
    public static final Parcelable.Creator<OsuProvider> CREATOR = new Parcelable.Creator<OsuProvider>() { // from class: android.net.wifi.hotspot2.OsuProvider.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OsuProvider createFromParcel(Parcel in) {
            WifiSsid osuSsid = (WifiSsid) in.readParcelable(null);
            String serviceDescription = in.readString();
            Uri serverUri = (Uri) in.readParcelable(null);
            String nai = in.readString();
            List<Integer> methodList = new ArrayList<>();
            in.readList(methodList, null);
            Icon icon = (Icon) in.readParcelable(null);
            Bundle bundle = in.readBundle();
            Map<String, String> friendlyNamesMap = (HashMap) bundle.getSerializable("friendlyNameMap");
            return new OsuProvider(osuSsid, friendlyNamesMap, serviceDescription, serverUri, nai, methodList, icon);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OsuProvider[] newArray(int size) {
            return new OsuProvider[size];
        }
    };
    public static final int METHOD_OMA_DM = 0;
    public static final int METHOD_SOAP_XML_SPP = 1;
    private final Map<String, String> mFriendlyNames;
    private final Icon mIcon;
    private final List<Integer> mMethodList;
    private final String mNetworkAccessIdentifier;
    private WifiSsid mOsuSsid;
    private final Uri mServerUri;
    private final String mServiceDescription;

    public OsuProvider(WifiSsid osuSsid, Map<String, String> friendlyNames, String serviceDescription, Uri serverUri, String nai, List<Integer> methodList, Icon icon) {
        this.mOsuSsid = osuSsid;
        this.mFriendlyNames = friendlyNames;
        this.mServiceDescription = serviceDescription;
        this.mServerUri = serverUri;
        this.mNetworkAccessIdentifier = nai;
        if (methodList == null) {
            this.mMethodList = new ArrayList();
        } else {
            this.mMethodList = new ArrayList(methodList);
        }
        this.mIcon = icon;
    }

    public OsuProvider(OsuProvider source) {
        if (source == null) {
            this.mOsuSsid = null;
            this.mFriendlyNames = null;
            this.mServiceDescription = null;
            this.mServerUri = null;
            this.mNetworkAccessIdentifier = null;
            this.mMethodList = new ArrayList();
            this.mIcon = null;
            return;
        }
        this.mOsuSsid = source.mOsuSsid;
        this.mFriendlyNames = source.mFriendlyNames;
        this.mServiceDescription = source.mServiceDescription;
        this.mServerUri = source.mServerUri;
        this.mNetworkAccessIdentifier = source.mNetworkAccessIdentifier;
        if (source.mMethodList == null) {
            this.mMethodList = new ArrayList();
        } else {
            this.mMethodList = new ArrayList(source.mMethodList);
        }
        this.mIcon = source.mIcon;
    }

    public WifiSsid getOsuSsid() {
        return this.mOsuSsid;
    }

    public void setOsuSsid(WifiSsid osuSsid) {
        this.mOsuSsid = osuSsid;
    }

    public String getFriendlyName() {
        if (this.mFriendlyNames == null || this.mFriendlyNames.isEmpty()) {
            return null;
        }
        String lang = Locale.getDefault().getLanguage();
        String friendlyName = this.mFriendlyNames.get(lang);
        if (friendlyName != null) {
            return friendlyName;
        }
        String friendlyName2 = this.mFriendlyNames.get("en");
        if (friendlyName2 != null) {
            return friendlyName2;
        }
        return this.mFriendlyNames.get(this.mFriendlyNames.keySet().stream().findFirst().get());
    }

    public Map<String, String> getFriendlyNameList() {
        return this.mFriendlyNames;
    }

    public String getServiceDescription() {
        return this.mServiceDescription;
    }

    public Uri getServerUri() {
        return this.mServerUri;
    }

    public String getNetworkAccessIdentifier() {
        return this.mNetworkAccessIdentifier;
    }

    public List<Integer> getMethodList() {
        return this.mMethodList;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mOsuSsid, flags);
        dest.writeString(this.mServiceDescription);
        dest.writeParcelable(this.mServerUri, flags);
        dest.writeString(this.mNetworkAccessIdentifier);
        dest.writeList(this.mMethodList);
        dest.writeParcelable(this.mIcon, flags);
        Bundle bundle = new Bundle();
        bundle.putSerializable("friendlyNameMap", (HashMap) this.mFriendlyNames);
        dest.writeBundle(bundle);
    }

    public boolean equals(Object thatObject) {
        if (this == thatObject) {
            return true;
        }
        if (thatObject instanceof OsuProvider) {
            OsuProvider that = (OsuProvider) thatObject;
            if (this.mOsuSsid != null ? this.mOsuSsid.equals(that.mOsuSsid) : that.mOsuSsid == null) {
                if (this.mFriendlyNames == null) {
                    if (that.mFriendlyNames == null) {
                        return true;
                    }
                    return false;
                }
            }
            if (this.mFriendlyNames.equals(that.mFriendlyNames) && TextUtils.equals(this.mServiceDescription, that.mServiceDescription) && (this.mServerUri != null ? this.mServerUri.equals(that.mServerUri) : that.mServerUri == null) && TextUtils.equals(this.mNetworkAccessIdentifier, that.mNetworkAccessIdentifier) && (this.mMethodList != null ? this.mMethodList.equals(that.mMethodList) : that.mMethodList == null)) {
                if (this.mIcon == null) {
                    if (that.mIcon == null) {
                        return true;
                    }
                } else if (this.mIcon.sameAs(that.mIcon)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mOsuSsid, this.mServiceDescription, this.mFriendlyNames, this.mServerUri, this.mNetworkAccessIdentifier, this.mMethodList);
    }

    public String toString() {
        return "OsuProvider{mOsuSsid=" + this.mOsuSsid + " mFriendlyNames=" + this.mFriendlyNames + " mServiceDescription=" + this.mServiceDescription + " mServerUri=" + this.mServerUri + " mNetworkAccessIdentifier=" + this.mNetworkAccessIdentifier + " mMethodList=" + this.mMethodList + " mIcon=" + this.mIcon;
    }
}
