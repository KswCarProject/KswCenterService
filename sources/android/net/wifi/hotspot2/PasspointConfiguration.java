package android.net.wifi.hotspot2;

import android.net.wifi.hotspot2.pps.Credential;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.net.wifi.hotspot2.pps.Policy;
import android.net.wifi.hotspot2.pps.UpdateParameter;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PasspointConfiguration implements Parcelable {
    private static final int CERTIFICATE_SHA256_BYTES = 32;
    public static final Parcelable.Creator<PasspointConfiguration> CREATOR = new Parcelable.Creator<PasspointConfiguration>() { // from class: android.net.wifi.hotspot2.PasspointConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PasspointConfiguration createFromParcel(Parcel in) {
            PasspointConfiguration config = new PasspointConfiguration();
            config.setHomeSp((HomeSp) in.readParcelable(null));
            config.setCredential((Credential) in.readParcelable(null));
            config.setPolicy((Policy) in.readParcelable(null));
            config.setSubscriptionUpdate((UpdateParameter) in.readParcelable(null));
            config.setTrustRootCertList(readTrustRootCerts(in));
            config.setUpdateIdentifier(in.readInt());
            config.setCredentialPriority(in.readInt());
            config.setSubscriptionCreationTimeInMillis(in.readLong());
            config.setSubscriptionExpirationTimeInMillis(in.readLong());
            config.setSubscriptionType(in.readString());
            config.setUsageLimitUsageTimePeriodInMinutes(in.readLong());
            config.setUsageLimitStartTimeInMillis(in.readLong());
            config.setUsageLimitDataLimit(in.readLong());
            config.setUsageLimitTimeLimitInMinutes(in.readLong());
            Bundle bundle = in.readBundle();
            Map<String, String> friendlyNamesMap = (HashMap) bundle.getSerializable("serviceFriendlyNames");
            config.setServiceFriendlyNames(friendlyNamesMap);
            return config;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PasspointConfiguration[] newArray(int size) {
            return new PasspointConfiguration[size];
        }

        private Map<String, byte[]> readTrustRootCerts(Parcel in) {
            int size = in.readInt();
            if (size == -1) {
                return null;
            }
            Map<String, byte[]> trustRootCerts = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                String key = in.readString();
                byte[] value = in.createByteArray();
                trustRootCerts.put(key, value);
            }
            return trustRootCerts;
        }
    };
    private static final int MAX_URL_BYTES = 1023;
    private static final int NULL_VALUE = -1;
    private static final String TAG = "PasspointConfiguration";
    private Credential mCredential;
    private int mCredentialPriority;
    private HomeSp mHomeSp;
    private Policy mPolicy;
    private Map<String, String> mServiceFriendlyNames;
    private long mSubscriptionCreationTimeInMillis;
    private long mSubscriptionExpirationTimeInMillis;
    private String mSubscriptionType;
    private UpdateParameter mSubscriptionUpdate;
    private Map<String, byte[]> mTrustRootCertList;
    private int mUpdateIdentifier;
    private long mUsageLimitDataLimit;
    private long mUsageLimitStartTimeInMillis;
    private long mUsageLimitTimeLimitInMinutes;
    private long mUsageLimitUsageTimePeriodInMinutes;

    public void setHomeSp(HomeSp homeSp) {
        this.mHomeSp = homeSp;
    }

    public HomeSp getHomeSp() {
        return this.mHomeSp;
    }

    public void setCredential(Credential credential) {
        this.mCredential = credential;
    }

    public Credential getCredential() {
        return this.mCredential;
    }

    public void setPolicy(Policy policy) {
        this.mPolicy = policy;
    }

    public Policy getPolicy() {
        return this.mPolicy;
    }

    public void setSubscriptionUpdate(UpdateParameter subscriptionUpdate) {
        this.mSubscriptionUpdate = subscriptionUpdate;
    }

    public UpdateParameter getSubscriptionUpdate() {
        return this.mSubscriptionUpdate;
    }

    public void setTrustRootCertList(Map<String, byte[]> trustRootCertList) {
        this.mTrustRootCertList = trustRootCertList;
    }

    public Map<String, byte[]> getTrustRootCertList() {
        return this.mTrustRootCertList;
    }

    public void setUpdateIdentifier(int updateIdentifier) {
        this.mUpdateIdentifier = updateIdentifier;
    }

    public int getUpdateIdentifier() {
        return this.mUpdateIdentifier;
    }

    public void setCredentialPriority(int credentialPriority) {
        this.mCredentialPriority = credentialPriority;
    }

    public int getCredentialPriority() {
        return this.mCredentialPriority;
    }

    public void setSubscriptionCreationTimeInMillis(long subscriptionCreationTimeInMillis) {
        this.mSubscriptionCreationTimeInMillis = subscriptionCreationTimeInMillis;
    }

    public long getSubscriptionCreationTimeInMillis() {
        return this.mSubscriptionCreationTimeInMillis;
    }

    public void setSubscriptionExpirationTimeInMillis(long subscriptionExpirationTimeInMillis) {
        this.mSubscriptionExpirationTimeInMillis = subscriptionExpirationTimeInMillis;
    }

    public long getSubscriptionExpirationTimeInMillis() {
        return this.mSubscriptionExpirationTimeInMillis;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.mSubscriptionType = subscriptionType;
    }

    public String getSubscriptionType() {
        return this.mSubscriptionType;
    }

    public void setUsageLimitUsageTimePeriodInMinutes(long usageLimitUsageTimePeriodInMinutes) {
        this.mUsageLimitUsageTimePeriodInMinutes = usageLimitUsageTimePeriodInMinutes;
    }

    public long getUsageLimitUsageTimePeriodInMinutes() {
        return this.mUsageLimitUsageTimePeriodInMinutes;
    }

    public void setUsageLimitStartTimeInMillis(long usageLimitStartTimeInMillis) {
        this.mUsageLimitStartTimeInMillis = usageLimitStartTimeInMillis;
    }

    public long getUsageLimitStartTimeInMillis() {
        return this.mUsageLimitStartTimeInMillis;
    }

    public void setUsageLimitDataLimit(long usageLimitDataLimit) {
        this.mUsageLimitDataLimit = usageLimitDataLimit;
    }

    public long getUsageLimitDataLimit() {
        return this.mUsageLimitDataLimit;
    }

    public void setUsageLimitTimeLimitInMinutes(long usageLimitTimeLimitInMinutes) {
        this.mUsageLimitTimeLimitInMinutes = usageLimitTimeLimitInMinutes;
    }

    public long getUsageLimitTimeLimitInMinutes() {
        return this.mUsageLimitTimeLimitInMinutes;
    }

    public void setServiceFriendlyNames(Map<String, String> serviceFriendlyNames) {
        this.mServiceFriendlyNames = serviceFriendlyNames;
    }

    public Map<String, String> getServiceFriendlyNames() {
        return this.mServiceFriendlyNames;
    }

    public String getServiceFriendlyName() {
        if (this.mServiceFriendlyNames == null || this.mServiceFriendlyNames.isEmpty()) {
            return null;
        }
        String lang = Locale.getDefault().getLanguage();
        String friendlyName = this.mServiceFriendlyNames.get(lang);
        if (friendlyName != null) {
            return friendlyName;
        }
        String friendlyName2 = this.mServiceFriendlyNames.get("en");
        if (friendlyName2 != null) {
            return friendlyName2;
        }
        return this.mServiceFriendlyNames.get(this.mServiceFriendlyNames.keySet().stream().findFirst().get());
    }

    public PasspointConfiguration() {
        this.mHomeSp = null;
        this.mCredential = null;
        this.mPolicy = null;
        this.mSubscriptionUpdate = null;
        this.mTrustRootCertList = null;
        this.mUpdateIdentifier = Integer.MIN_VALUE;
        this.mCredentialPriority = Integer.MIN_VALUE;
        this.mSubscriptionCreationTimeInMillis = Long.MIN_VALUE;
        this.mSubscriptionExpirationTimeInMillis = Long.MIN_VALUE;
        this.mSubscriptionType = null;
        this.mUsageLimitUsageTimePeriodInMinutes = Long.MIN_VALUE;
        this.mUsageLimitStartTimeInMillis = Long.MIN_VALUE;
        this.mUsageLimitDataLimit = Long.MIN_VALUE;
        this.mUsageLimitTimeLimitInMinutes = Long.MIN_VALUE;
        this.mServiceFriendlyNames = null;
    }

    public PasspointConfiguration(PasspointConfiguration source) {
        this.mHomeSp = null;
        this.mCredential = null;
        this.mPolicy = null;
        this.mSubscriptionUpdate = null;
        this.mTrustRootCertList = null;
        this.mUpdateIdentifier = Integer.MIN_VALUE;
        this.mCredentialPriority = Integer.MIN_VALUE;
        this.mSubscriptionCreationTimeInMillis = Long.MIN_VALUE;
        this.mSubscriptionExpirationTimeInMillis = Long.MIN_VALUE;
        this.mSubscriptionType = null;
        this.mUsageLimitUsageTimePeriodInMinutes = Long.MIN_VALUE;
        this.mUsageLimitStartTimeInMillis = Long.MIN_VALUE;
        this.mUsageLimitDataLimit = Long.MIN_VALUE;
        this.mUsageLimitTimeLimitInMinutes = Long.MIN_VALUE;
        this.mServiceFriendlyNames = null;
        if (source == null) {
            return;
        }
        if (source.mHomeSp != null) {
            this.mHomeSp = new HomeSp(source.mHomeSp);
        }
        if (source.mCredential != null) {
            this.mCredential = new Credential(source.mCredential);
        }
        if (source.mPolicy != null) {
            this.mPolicy = new Policy(source.mPolicy);
        }
        if (source.mTrustRootCertList != null) {
            this.mTrustRootCertList = Collections.unmodifiableMap(source.mTrustRootCertList);
        }
        if (source.mSubscriptionUpdate != null) {
            this.mSubscriptionUpdate = new UpdateParameter(source.mSubscriptionUpdate);
        }
        this.mUpdateIdentifier = source.mUpdateIdentifier;
        this.mCredentialPriority = source.mCredentialPriority;
        this.mSubscriptionCreationTimeInMillis = source.mSubscriptionCreationTimeInMillis;
        this.mSubscriptionExpirationTimeInMillis = source.mSubscriptionExpirationTimeInMillis;
        this.mSubscriptionType = source.mSubscriptionType;
        this.mUsageLimitDataLimit = source.mUsageLimitDataLimit;
        this.mUsageLimitStartTimeInMillis = source.mUsageLimitStartTimeInMillis;
        this.mUsageLimitTimeLimitInMinutes = source.mUsageLimitTimeLimitInMinutes;
        this.mUsageLimitUsageTimePeriodInMinutes = source.mUsageLimitUsageTimePeriodInMinutes;
        this.mServiceFriendlyNames = source.mServiceFriendlyNames;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mHomeSp, flags);
        dest.writeParcelable(this.mCredential, flags);
        dest.writeParcelable(this.mPolicy, flags);
        dest.writeParcelable(this.mSubscriptionUpdate, flags);
        writeTrustRootCerts(dest, this.mTrustRootCertList);
        dest.writeInt(this.mUpdateIdentifier);
        dest.writeInt(this.mCredentialPriority);
        dest.writeLong(this.mSubscriptionCreationTimeInMillis);
        dest.writeLong(this.mSubscriptionExpirationTimeInMillis);
        dest.writeString(this.mSubscriptionType);
        dest.writeLong(this.mUsageLimitUsageTimePeriodInMinutes);
        dest.writeLong(this.mUsageLimitStartTimeInMillis);
        dest.writeLong(this.mUsageLimitDataLimit);
        dest.writeLong(this.mUsageLimitTimeLimitInMinutes);
        Bundle bundle = new Bundle();
        bundle.putSerializable("serviceFriendlyNames", (HashMap) this.mServiceFriendlyNames);
        dest.writeBundle(bundle);
    }

    public boolean equals(Object thatObject) {
        if (this == thatObject) {
            return true;
        }
        if (thatObject instanceof PasspointConfiguration) {
            PasspointConfiguration that = (PasspointConfiguration) thatObject;
            if (this.mHomeSp != null ? this.mHomeSp.equals(that.mHomeSp) : that.mHomeSp == null) {
                if (this.mCredential != null ? this.mCredential.equals(that.mCredential) : that.mCredential == null) {
                    if (this.mPolicy != null ? this.mPolicy.equals(that.mPolicy) : that.mPolicy == null) {
                        if (this.mSubscriptionUpdate != null ? this.mSubscriptionUpdate.equals(that.mSubscriptionUpdate) : that.mSubscriptionUpdate == null) {
                            if (isTrustRootCertListEquals(this.mTrustRootCertList, that.mTrustRootCertList) && this.mUpdateIdentifier == that.mUpdateIdentifier && this.mCredentialPriority == that.mCredentialPriority && this.mSubscriptionCreationTimeInMillis == that.mSubscriptionCreationTimeInMillis && this.mSubscriptionExpirationTimeInMillis == that.mSubscriptionExpirationTimeInMillis && TextUtils.equals(this.mSubscriptionType, that.mSubscriptionType) && this.mUsageLimitUsageTimePeriodInMinutes == that.mUsageLimitUsageTimePeriodInMinutes && this.mUsageLimitStartTimeInMillis == that.mUsageLimitStartTimeInMillis && this.mUsageLimitDataLimit == that.mUsageLimitDataLimit && this.mUsageLimitTimeLimitInMinutes == that.mUsageLimitTimeLimitInMinutes) {
                                if (this.mServiceFriendlyNames == null) {
                                    if (that.mServiceFriendlyNames == null) {
                                        return true;
                                    }
                                } else if (this.mServiceFriendlyNames.equals(that.mServiceFriendlyNames)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mHomeSp, this.mCredential, this.mPolicy, this.mSubscriptionUpdate, this.mTrustRootCertList, Integer.valueOf(this.mUpdateIdentifier), Integer.valueOf(this.mCredentialPriority), Long.valueOf(this.mSubscriptionCreationTimeInMillis), Long.valueOf(this.mSubscriptionExpirationTimeInMillis), Long.valueOf(this.mUsageLimitUsageTimePeriodInMinutes), Long.valueOf(this.mUsageLimitStartTimeInMillis), Long.valueOf(this.mUsageLimitDataLimit), Long.valueOf(this.mUsageLimitTimeLimitInMinutes), this.mServiceFriendlyNames);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UpdateIdentifier: ");
        builder.append(this.mUpdateIdentifier);
        builder.append("\n");
        builder.append("CredentialPriority: ");
        builder.append(this.mCredentialPriority);
        builder.append("\n");
        builder.append("SubscriptionCreationTime: ");
        builder.append(this.mSubscriptionCreationTimeInMillis != Long.MIN_VALUE ? new Date(this.mSubscriptionCreationTimeInMillis) : "Not specified");
        builder.append("\n");
        builder.append("SubscriptionExpirationTime: ");
        builder.append(this.mSubscriptionExpirationTimeInMillis != Long.MIN_VALUE ? new Date(this.mSubscriptionExpirationTimeInMillis) : "Not specified");
        builder.append("\n");
        builder.append("UsageLimitStartTime: ");
        builder.append(this.mUsageLimitStartTimeInMillis != Long.MIN_VALUE ? new Date(this.mUsageLimitStartTimeInMillis) : "Not specified");
        builder.append("\n");
        builder.append("UsageTimePeriod: ");
        builder.append(this.mUsageLimitUsageTimePeriodInMinutes);
        builder.append("\n");
        builder.append("UsageLimitDataLimit: ");
        builder.append(this.mUsageLimitDataLimit);
        builder.append("\n");
        builder.append("UsageLimitTimeLimit: ");
        builder.append(this.mUsageLimitTimeLimitInMinutes);
        builder.append("\n");
        if (this.mHomeSp != null) {
            builder.append("HomeSP Begin ---\n");
            builder.append(this.mHomeSp);
            builder.append("HomeSP End ---\n");
        }
        if (this.mCredential != null) {
            builder.append("Credential Begin ---\n");
            builder.append(this.mCredential);
            builder.append("Credential End ---\n");
        }
        if (this.mPolicy != null) {
            builder.append("Policy Begin ---\n");
            builder.append(this.mPolicy);
            builder.append("Policy End ---\n");
        }
        if (this.mSubscriptionUpdate != null) {
            builder.append("SubscriptionUpdate Begin ---\n");
            builder.append(this.mSubscriptionUpdate);
            builder.append("SubscriptionUpdate End ---\n");
        }
        if (this.mTrustRootCertList != null) {
            builder.append("TrustRootCertServers: ");
            builder.append(this.mTrustRootCertList.keySet());
            builder.append("\n");
        }
        if (this.mServiceFriendlyNames != null) {
            builder.append("ServiceFriendlyNames: ");
            builder.append(this.mServiceFriendlyNames);
        }
        return builder.toString();
    }

    public boolean validate() {
        if (this.mSubscriptionUpdate != null && !this.mSubscriptionUpdate.validate()) {
            return false;
        }
        return validateForCommonR1andR2(true);
    }

    public boolean validateForR2() {
        if (this.mUpdateIdentifier == Integer.MIN_VALUE || this.mSubscriptionUpdate == null || !this.mSubscriptionUpdate.validate()) {
            return false;
        }
        return validateForCommonR1andR2(false);
    }

    private boolean validateForCommonR1andR2(boolean isR1) {
        if (this.mHomeSp == null || !this.mHomeSp.validate() || this.mCredential == null || !this.mCredential.validate(isR1)) {
            return false;
        }
        if (this.mPolicy == null || this.mPolicy.validate()) {
            if (this.mTrustRootCertList != null) {
                for (Map.Entry<String, byte[]> entry : this.mTrustRootCertList.entrySet()) {
                    String url = entry.getKey();
                    byte[] certFingerprint = entry.getValue();
                    if (TextUtils.isEmpty(url)) {
                        Log.m72d(TAG, "Empty URL");
                        return false;
                    } else if (url.getBytes(StandardCharsets.UTF_8).length > 1023) {
                        Log.m72d(TAG, "URL bytes exceeded the max: " + url.getBytes(StandardCharsets.UTF_8).length);
                        return false;
                    } else if (certFingerprint == null) {
                        Log.m72d(TAG, "Fingerprint not specified");
                        return false;
                    } else if (certFingerprint.length != 32) {
                        Log.m72d(TAG, "Incorrect size of trust root certificate SHA-256 fingerprint: " + certFingerprint.length);
                        return false;
                    }
                }
                return true;
            }
            return true;
        }
        return false;
    }

    private static void writeTrustRootCerts(Parcel dest, Map<String, byte[]> trustRootCerts) {
        if (trustRootCerts == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(trustRootCerts.size());
        for (Map.Entry<String, byte[]> entry : trustRootCerts.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeByteArray(entry.getValue());
        }
    }

    private static boolean isTrustRootCertListEquals(Map<String, byte[]> list1, Map<String, byte[]> list2) {
        if (list1 == null || list2 == null) {
            return list1 == list2;
        } else if (list1.size() != list2.size()) {
            return false;
        } else {
            for (Map.Entry<String, byte[]> entry : list1.entrySet()) {
                if (!Arrays.equals(entry.getValue(), list2.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
    }
}
