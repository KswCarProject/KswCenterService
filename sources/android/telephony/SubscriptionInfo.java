package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SubscriptionInfo implements Parcelable {
    public static final Parcelable.Creator<SubscriptionInfo> CREATOR = new Parcelable.Creator<SubscriptionInfo>() {
        public SubscriptionInfo createFromParcel(Parcel source) {
            Parcel parcel = source;
            Bitmap bitmap = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
            String[] ehplmns = source.readStringArray();
            String[] hplmns = source.readStringArray();
            String[] hplmns2 = ehplmns;
            SubscriptionInfo info = new SubscriptionInfo(source.readInt(), source.readString(), source.readInt(), source.readCharSequence(), source.readCharSequence(), source.readInt(), source.readInt(), source.readString(), source.readInt(), bitmap, source.readString(), source.readString(), source.readString(), source.readBoolean(), (UiccAccessRule[]) parcel.createTypedArray(UiccAccessRule.CREATOR), source.readString(), source.readInt(), source.readBoolean(), source.readString(), source.readBoolean(), source.readInt(), source.readInt(), source.readInt(), source.readString());
            info.setAssociatedPlmns(hplmns2, hplmns);
            return info;
        }

        public SubscriptionInfo[] newArray(int size) {
            return new SubscriptionInfo[size];
        }
    };
    private static final int TEXT_SIZE = 16;
    private UiccAccessRule[] mAccessRules;
    private int mCardId;
    private String mCardString;
    private int mCarrierId;
    private CharSequence mCarrierName;
    private String mCountryIso;
    private int mDataRoaming;
    private CharSequence mDisplayName;
    private String[] mEhplmns;
    private String mGroupOwner;
    private ParcelUuid mGroupUUID;
    private String[] mHplmns;
    private String mIccId;
    private Bitmap mIconBitmap;
    private int mIconTint;
    private int mId;
    private boolean mIsEmbedded;
    private boolean mIsGroupDisabled;
    private boolean mIsOpportunistic;
    private String mMcc;
    private String mMnc;
    private int mNameSource;
    private String mNumber;
    private int mProfileClass;
    private int mSimSlotIndex;
    private int mSubscriptionType;

    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] accessRules, String cardString) {
        this(id, iccId, simSlotIndex, displayName, carrierName, nameSource, iconTint, number, roaming, icon, mcc, mnc, countryIso, isEmbedded, accessRules, cardString, -1, false, (String) null, false, -1, -1, 0, (String) null);
    }

    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] accessRules, String cardString, boolean isOpportunistic, String groupUUID, int carrierId, int profileClass) {
        this(id, iccId, simSlotIndex, displayName, carrierName, nameSource, iconTint, number, roaming, icon, mcc, mnc, countryIso, isEmbedded, accessRules, cardString, -1, isOpportunistic, groupUUID, false, carrierId, profileClass, 0, (String) null);
    }

    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] accessRules, String cardString, int cardId, boolean isOpportunistic, String groupUUID, boolean isGroupDisabled, int carrierId, int profileClass, int subType, String groupOwner) {
        this.mIsGroupDisabled = false;
        this.mId = id;
        this.mIccId = iccId;
        this.mSimSlotIndex = simSlotIndex;
        this.mDisplayName = displayName;
        this.mCarrierName = carrierName;
        this.mNameSource = nameSource;
        this.mIconTint = iconTint;
        this.mNumber = number;
        this.mDataRoaming = roaming;
        this.mIconBitmap = icon;
        this.mMcc = mcc;
        this.mMnc = mnc;
        this.mCountryIso = countryIso;
        this.mIsEmbedded = isEmbedded;
        this.mAccessRules = accessRules;
        this.mCardString = cardString;
        this.mCardId = cardId;
        this.mIsOpportunistic = isOpportunistic;
        this.mGroupUUID = groupUUID == null ? null : ParcelUuid.fromString(groupUUID);
        this.mIsGroupDisabled = isGroupDisabled;
        this.mCarrierId = carrierId;
        this.mProfileClass = profileClass;
        this.mSubscriptionType = subType;
        this.mGroupOwner = groupOwner;
    }

    public int getSubscriptionId() {
        return this.mId;
    }

    public String getIccId() {
        return this.mIccId;
    }

    public int getSimSlotIndex() {
        return this.mSimSlotIndex;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    @UnsupportedAppUsage
    public void setDisplayName(CharSequence name) {
        this.mDisplayName = name;
    }

    public CharSequence getCarrierName() {
        return this.mCarrierName;
    }

    public void setCarrierName(CharSequence name) {
        this.mCarrierName = name;
    }

    @UnsupportedAppUsage
    public int getNameSource() {
        return this.mNameSource;
    }

    public void setAssociatedPlmns(String[] ehplmns, String[] hplmns) {
        this.mEhplmns = ehplmns;
        this.mHplmns = hplmns;
    }

    public Bitmap createIconBitmap(Context context) {
        int width = this.mIconBitmap.getWidth();
        int height = this.mIconBitmap.getHeight();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Bitmap workingBitmap = Bitmap.createBitmap(metrics, width, height, this.mIconBitmap.getConfig());
        Canvas canvas = new Canvas(workingBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(this.mIconTint, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(this.mIconBitmap, 0.0f, 0.0f, paint);
        paint.setColorFilter((ColorFilter) null);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create("sans-serif", 0));
        paint.setColor(-1);
        paint.setTextSize(metrics.density * 16.0f);
        String index = String.format("%d", new Object[]{Integer.valueOf(this.mSimSlotIndex + 1)});
        Rect textBound = new Rect();
        paint.getTextBounds(index, 0, 1, textBound);
        canvas.drawText(index, (((float) width) / 2.0f) - ((float) textBound.centerX()), (((float) height) / 2.0f) - ((float) textBound.centerY()), paint);
        return workingBitmap;
    }

    public int getIconTint() {
        return this.mIconTint;
    }

    @UnsupportedAppUsage
    public void setIconTint(int iconTint) {
        this.mIconTint = iconTint;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public int getDataRoaming() {
        return this.mDataRoaming;
    }

    @Deprecated
    public int getMcc() {
        try {
            if (this.mMcc == null) {
                return 0;
            }
            return Integer.valueOf(this.mMcc).intValue();
        } catch (NumberFormatException e) {
            Log.w(SubscriptionInfo.class.getSimpleName(), "MCC string is not a number");
            return 0;
        }
    }

    @Deprecated
    public int getMnc() {
        try {
            if (this.mMnc == null) {
                return 0;
            }
            return Integer.valueOf(this.mMnc).intValue();
        } catch (NumberFormatException e) {
            Log.w(SubscriptionInfo.class.getSimpleName(), "MNC string is not a number");
            return 0;
        }
    }

    public String getMccString() {
        return this.mMcc;
    }

    public String getMncString() {
        return this.mMnc;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public boolean isOpportunistic() {
        return this.mIsOpportunistic;
    }

    public ParcelUuid getGroupUuid() {
        return this.mGroupUUID;
    }

    public List<String> getEhplmns() {
        return this.mEhplmns == null ? Collections.emptyList() : Arrays.asList(this.mEhplmns);
    }

    public List<String> getHplmns() {
        return this.mHplmns == null ? Collections.emptyList() : Arrays.asList(this.mHplmns);
    }

    public String getGroupOwner() {
        return this.mGroupOwner;
    }

    @SystemApi
    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int getSubscriptionType() {
        return this.mSubscriptionType;
    }

    @Deprecated
    public boolean canManageSubscription(Context context) {
        return canManageSubscription(context, context.getPackageName());
    }

    @Deprecated
    public boolean canManageSubscription(Context context, String packageName) {
        if (!isEmbedded()) {
            throw new UnsupportedOperationException("Not an embedded subscription");
        } else if (this.mAccessRules == null) {
            return false;
        } else {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 64);
                for (UiccAccessRule rule : this.mAccessRules) {
                    if (rule.getCarrierPrivilegeStatus(packageInfo) == 1) {
                        return true;
                    }
                }
                return false;
            } catch (PackageManager.NameNotFoundException e) {
                throw new IllegalArgumentException("Unknown package: " + packageName, e);
            }
        }
    }

    @SystemApi
    public List<UiccAccessRule> getAccessRules() {
        if (!isEmbedded()) {
            throw new UnsupportedOperationException("Not an embedded subscription");
        } else if (this.mAccessRules == null) {
            return null;
        } else {
            return Arrays.asList(this.mAccessRules);
        }
    }

    public String getCardString() {
        return this.mCardString;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public void setGroupDisabled(boolean isGroupDisabled) {
        this.mIsGroupDisabled = isGroupDisabled;
    }

    public boolean isGroupDisabled() {
        return this.mIsGroupDisabled;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mIccId);
        dest.writeInt(this.mSimSlotIndex);
        dest.writeCharSequence(this.mDisplayName);
        dest.writeCharSequence(this.mCarrierName);
        dest.writeInt(this.mNameSource);
        dest.writeInt(this.mIconTint);
        dest.writeString(this.mNumber);
        dest.writeInt(this.mDataRoaming);
        dest.writeString(this.mMcc);
        dest.writeString(this.mMnc);
        dest.writeString(this.mCountryIso);
        dest.writeParcelable(this.mIconBitmap, flags);
        dest.writeBoolean(this.mIsEmbedded);
        dest.writeTypedArray(this.mAccessRules, flags);
        dest.writeString(this.mCardString);
        dest.writeInt(this.mCardId);
        dest.writeBoolean(this.mIsOpportunistic);
        dest.writeString(this.mGroupUUID == null ? null : this.mGroupUUID.toString());
        dest.writeBoolean(this.mIsGroupDisabled);
        dest.writeInt(this.mCarrierId);
        dest.writeInt(this.mProfileClass);
        dest.writeInt(this.mSubscriptionType);
        dest.writeStringArray(this.mEhplmns);
        dest.writeStringArray(this.mHplmns);
        dest.writeString(this.mGroupOwner);
    }

    public int describeContents() {
        return 0;
    }

    public static String givePrintableIccid(String iccId) {
        if (iccId == null) {
            return null;
        }
        if (iccId.length() <= 9 || Build.IS_DEBUGGABLE) {
            return iccId;
        }
        return iccId.substring(0, 9) + Rlog.pii(false, (Object) iccId.substring(9));
    }

    public String toString() {
        String iccIdToPrint = givePrintableIccid(this.mIccId);
        String cardStringToPrint = givePrintableIccid(this.mCardString);
        return "{id=" + this.mId + ", iccId=" + iccIdToPrint + " simSlotIndex=" + this.mSimSlotIndex + " carrierId=" + this.mCarrierId + " displayName=" + this.mDisplayName + " carrierName=" + this.mCarrierName + " nameSource=" + this.mNameSource + " iconTint=" + this.mIconTint + " mNumber=" + Rlog.pii(Build.IS_DEBUGGABLE, (Object) this.mNumber) + " dataRoaming=" + this.mDataRoaming + " iconBitmap=" + this.mIconBitmap + " mcc " + this.mMcc + " mnc " + this.mMnc + "mCountryIso=" + this.mCountryIso + " isEmbedded " + this.mIsEmbedded + " accessRules " + Arrays.toString(this.mAccessRules) + " cardString=" + cardStringToPrint + " cardId=" + this.mCardId + " isOpportunistic " + this.mIsOpportunistic + " mGroupUUID=" + this.mGroupUUID + " mIsGroupDisabled=" + this.mIsGroupDisabled + " profileClass=" + this.mProfileClass + " ehplmns = " + Arrays.toString(this.mEhplmns) + " hplmns = " + Arrays.toString(this.mHplmns) + " subscriptionType=" + this.mSubscriptionType + " mGroupOwner=" + this.mGroupOwner + "}";
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.mId), Integer.valueOf(this.mSimSlotIndex), Integer.valueOf(this.mNameSource), Integer.valueOf(this.mIconTint), Integer.valueOf(this.mDataRoaming), Boolean.valueOf(this.mIsEmbedded), Boolean.valueOf(this.mIsOpportunistic), this.mGroupUUID, this.mIccId, this.mNumber, this.mMcc, this.mMnc, this.mCountryIso, this.mCardString, Integer.valueOf(this.mCardId), this.mDisplayName, this.mCarrierName, this.mAccessRules, Boolean.valueOf(this.mIsGroupDisabled), Integer.valueOf(this.mCarrierId), Integer.valueOf(this.mProfileClass), this.mGroupOwner});
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        try {
            SubscriptionInfo toCompare = (SubscriptionInfo) obj;
            if (this.mId != toCompare.mId || this.mSimSlotIndex != toCompare.mSimSlotIndex || this.mNameSource != toCompare.mNameSource || this.mIconTint != toCompare.mIconTint || this.mDataRoaming != toCompare.mDataRoaming || this.mIsEmbedded != toCompare.mIsEmbedded || this.mIsOpportunistic != toCompare.mIsOpportunistic || this.mIsGroupDisabled != toCompare.mIsGroupDisabled || this.mCarrierId != toCompare.mCarrierId || !Objects.equals(this.mGroupUUID, toCompare.mGroupUUID) || !Objects.equals(this.mIccId, toCompare.mIccId) || !Objects.equals(this.mNumber, toCompare.mNumber) || !Objects.equals(this.mMcc, toCompare.mMcc) || !Objects.equals(this.mMnc, toCompare.mMnc) || !Objects.equals(this.mCountryIso, toCompare.mCountryIso) || !Objects.equals(this.mCardString, toCompare.mCardString) || !Objects.equals(Integer.valueOf(this.mCardId), Integer.valueOf(toCompare.mCardId)) || !Objects.equals(this.mGroupOwner, toCompare.mGroupOwner) || !TextUtils.equals(this.mDisplayName, toCompare.mDisplayName) || !TextUtils.equals(this.mCarrierName, toCompare.mCarrierName) || !Arrays.equals(this.mAccessRules, toCompare.mAccessRules) || this.mProfileClass != toCompare.mProfileClass || !Arrays.equals(this.mEhplmns, toCompare.mEhplmns) || !Arrays.equals(this.mHplmns, toCompare.mHplmns)) {
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
