package android.net.wifi.hotspot2.pps;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class HomeSp implements Parcelable {
    public static final Parcelable.Creator<HomeSp> CREATOR = new Parcelable.Creator<HomeSp>() {
        public HomeSp createFromParcel(Parcel in) {
            HomeSp homeSp = new HomeSp();
            homeSp.setFqdn(in.readString());
            homeSp.setFriendlyName(in.readString());
            homeSp.setIconUrl(in.readString());
            homeSp.setHomeNetworkIds(readHomeNetworkIds(in));
            homeSp.setMatchAllOis(in.createLongArray());
            homeSp.setMatchAnyOis(in.createLongArray());
            homeSp.setOtherHomePartners(in.createStringArray());
            homeSp.setRoamingConsortiumOis(in.createLongArray());
            return homeSp;
        }

        public HomeSp[] newArray(int size) {
            return new HomeSp[size];
        }

        private Map<String, Long> readHomeNetworkIds(Parcel in) {
            int size = in.readInt();
            if (size == -1) {
                return null;
            }
            Map<String, Long> networkIds = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                String key = in.readString();
                Long value = null;
                long readValue = in.readLong();
                if (readValue != -1) {
                    value = Long.valueOf(readValue);
                }
                networkIds.put(key, value);
            }
            return networkIds;
        }
    };
    private static final int MAX_SSID_BYTES = 32;
    private static final int NULL_VALUE = -1;
    private static final String TAG = "HomeSp";
    private String mFqdn = null;
    private String mFriendlyName = null;
    private Map<String, Long> mHomeNetworkIds = null;
    private String mIconUrl = null;
    private long[] mMatchAllOis = null;
    private long[] mMatchAnyOis = null;
    private String[] mOtherHomePartners = null;
    private long[] mRoamingConsortiumOis = null;

    public void setFqdn(String fqdn) {
        this.mFqdn = fqdn;
    }

    public String getFqdn() {
        return this.mFqdn;
    }

    public void setFriendlyName(String friendlyName) {
        this.mFriendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return this.mFriendlyName;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setHomeNetworkIds(Map<String, Long> homeNetworkIds) {
        this.mHomeNetworkIds = homeNetworkIds;
    }

    public Map<String, Long> getHomeNetworkIds() {
        return this.mHomeNetworkIds;
    }

    public void setMatchAllOis(long[] matchAllOis) {
        this.mMatchAllOis = matchAllOis;
    }

    public long[] getMatchAllOis() {
        return this.mMatchAllOis;
    }

    public void setMatchAnyOis(long[] matchAnyOis) {
        this.mMatchAnyOis = matchAnyOis;
    }

    public long[] getMatchAnyOis() {
        return this.mMatchAnyOis;
    }

    public void setOtherHomePartners(String[] otherHomePartners) {
        this.mOtherHomePartners = otherHomePartners;
    }

    public String[] getOtherHomePartners() {
        return this.mOtherHomePartners;
    }

    public void setRoamingConsortiumOis(long[] roamingConsortiumOis) {
        this.mRoamingConsortiumOis = roamingConsortiumOis;
    }

    public long[] getRoamingConsortiumOis() {
        return this.mRoamingConsortiumOis;
    }

    public HomeSp() {
    }

    public HomeSp(HomeSp source) {
        if (source != null) {
            this.mFqdn = source.mFqdn;
            this.mFriendlyName = source.mFriendlyName;
            this.mIconUrl = source.mIconUrl;
            if (source.mHomeNetworkIds != null) {
                this.mHomeNetworkIds = Collections.unmodifiableMap(source.mHomeNetworkIds);
            }
            if (source.mMatchAllOis != null) {
                this.mMatchAllOis = Arrays.copyOf(source.mMatchAllOis, source.mMatchAllOis.length);
            }
            if (source.mMatchAnyOis != null) {
                this.mMatchAnyOis = Arrays.copyOf(source.mMatchAnyOis, source.mMatchAnyOis.length);
            }
            if (source.mOtherHomePartners != null) {
                this.mOtherHomePartners = (String[]) Arrays.copyOf(source.mOtherHomePartners, source.mOtherHomePartners.length);
            }
            if (source.mRoamingConsortiumOis != null) {
                this.mRoamingConsortiumOis = Arrays.copyOf(source.mRoamingConsortiumOis, source.mRoamingConsortiumOis.length);
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFqdn);
        dest.writeString(this.mFriendlyName);
        dest.writeString(this.mIconUrl);
        writeHomeNetworkIds(dest, this.mHomeNetworkIds);
        dest.writeLongArray(this.mMatchAllOis);
        dest.writeLongArray(this.mMatchAnyOis);
        dest.writeStringArray(this.mOtherHomePartners);
        dest.writeLongArray(this.mRoamingConsortiumOis);
    }

    public boolean equals(Object thatObject) {
        if (this == thatObject) {
            return true;
        }
        if (!(thatObject instanceof HomeSp)) {
            return false;
        }
        HomeSp that = (HomeSp) thatObject;
        if (!TextUtils.equals(this.mFqdn, that.mFqdn) || !TextUtils.equals(this.mFriendlyName, that.mFriendlyName) || !TextUtils.equals(this.mIconUrl, that.mIconUrl) || (this.mHomeNetworkIds != null ? !this.mHomeNetworkIds.equals(that.mHomeNetworkIds) : that.mHomeNetworkIds != null) || !Arrays.equals(this.mMatchAllOis, that.mMatchAllOis) || !Arrays.equals(this.mMatchAnyOis, that.mMatchAnyOis) || !Arrays.equals(this.mOtherHomePartners, that.mOtherHomePartners) || !Arrays.equals(this.mRoamingConsortiumOis, that.mRoamingConsortiumOis)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.mFqdn, this.mFriendlyName, this.mIconUrl, this.mHomeNetworkIds, this.mMatchAllOis, this.mMatchAnyOis, this.mOtherHomePartners, this.mRoamingConsortiumOis});
    }

    public String toString() {
        return "FQDN: " + this.mFqdn + "\n" + "FriendlyName: " + this.mFriendlyName + "\n" + "IconURL: " + this.mIconUrl + "\n" + "HomeNetworkIDs: " + this.mHomeNetworkIds + "\n" + "MatchAllOIs: " + this.mMatchAllOis + "\n" + "MatchAnyOIs: " + this.mMatchAnyOis + "\n" + "OtherHomePartners: " + this.mOtherHomePartners + "\n" + "RoamingConsortiumOIs: " + this.mRoamingConsortiumOis + "\n";
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean validate() {
        /*
            r5 = this;
            java.lang.String r0 = r5.mFqdn
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0011
            java.lang.String r0 = "HomeSp"
            java.lang.String r2 = "Missing FQDN"
            android.util.Log.d(r0, r2)
            return r1
        L_0x0011:
            java.lang.String r0 = r5.mFriendlyName
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0021
            java.lang.String r0 = "HomeSp"
            java.lang.String r2 = "Missing friendly name"
            android.util.Log.d(r0, r2)
            return r1
        L_0x0021:
            java.util.Map<java.lang.String, java.lang.Long> r0 = r5.mHomeNetworkIds
            if (r0 == 0) goto L_0x005c
            java.util.Map<java.lang.String, java.lang.Long> r0 = r5.mHomeNetworkIds
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x002f:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x005c
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            if (r3 == 0) goto L_0x0054
            java.lang.Object r3 = r2.getKey()
            java.lang.String r3 = (java.lang.String) r3
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r3 = r3.getBytes(r4)
            int r3 = r3.length
            r4 = 32
            if (r3 <= r4) goto L_0x0053
            goto L_0x0054
        L_0x0053:
            goto L_0x002f
        L_0x0054:
            java.lang.String r0 = "HomeSp"
            java.lang.String r3 = "Invalid SSID in HomeNetworkIDs"
            android.util.Log.d(r0, r3)
            return r1
        L_0x005c:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.hotspot2.pps.HomeSp.validate():boolean");
    }

    private static void writeHomeNetworkIds(Parcel dest, Map<String, Long> networkIds) {
        if (networkIds == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(networkIds.size());
        for (Map.Entry<String, Long> entry : networkIds.entrySet()) {
            dest.writeString(entry.getKey());
            if (entry.getValue() == null) {
                dest.writeLong(-1);
            } else {
                dest.writeLong(entry.getValue().longValue());
            }
        }
    }
}
