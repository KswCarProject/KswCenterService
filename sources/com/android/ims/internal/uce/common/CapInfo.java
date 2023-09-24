package com.android.ims.internal.uce.common;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes4.dex */
public class CapInfo implements Parcelable {
    public static final Parcelable.Creator<CapInfo> CREATOR = new Parcelable.Creator<CapInfo>() { // from class: com.android.ims.internal.uce.common.CapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public CapInfo createFromParcel(Parcel source) {
            return new CapInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public CapInfo[] newArray(int size) {
            return new CapInfo[size];
        }
    };
    private boolean mCallComposerSupported;
    private long mCapTimestamp;
    private boolean mCdViaPresenceSupported;
    private boolean mChatbotRoleSupported;
    private boolean mChatbotSupported;
    private String[] mExts;
    private boolean mFtHttpSupported;
    private boolean mFtSnFSupported;
    private boolean mFtSupported;
    private boolean mFtThumbSupported;
    private boolean mFullSnFGroupChatSupported;
    private boolean mGeoPullFtSupported;
    private boolean mGeoPullSupported;
    private boolean mGeoPushSupported;
    private boolean mGeoSmsSupported;
    private boolean mImSupported;
    private boolean mIpVideoSupported;
    private boolean mIpVoiceSupported;
    private boolean mIsSupported;
    private boolean mPostCallSupported;
    private boolean mRcsIpVideoCallSupported;
    private boolean mRcsIpVideoOnlyCallSupported;
    private boolean mRcsIpVoiceCallSupported;
    private boolean mSharedMapSupported;
    private boolean mSharedSketchSupported;
    private boolean mSmSupported;
    private boolean mSpSupported;
    private boolean mVsDuringCSSupported;
    private boolean mVsSupported;

    @UnsupportedAppUsage
    public CapInfo() {
        this.mImSupported = false;
        this.mFtSupported = false;
        this.mFtThumbSupported = false;
        this.mFtSnFSupported = false;
        this.mFtHttpSupported = false;
        this.mIsSupported = false;
        this.mVsDuringCSSupported = false;
        this.mVsSupported = false;
        this.mSpSupported = false;
        this.mCdViaPresenceSupported = false;
        this.mIpVoiceSupported = false;
        this.mIpVideoSupported = false;
        this.mGeoPullFtSupported = false;
        this.mGeoPullSupported = false;
        this.mGeoPushSupported = false;
        this.mSmSupported = false;
        this.mFullSnFGroupChatSupported = false;
        this.mRcsIpVoiceCallSupported = false;
        this.mRcsIpVideoCallSupported = false;
        this.mRcsIpVideoOnlyCallSupported = false;
        this.mGeoSmsSupported = false;
        this.mCallComposerSupported = false;
        this.mPostCallSupported = false;
        this.mSharedMapSupported = false;
        this.mSharedSketchSupported = false;
        this.mChatbotSupported = false;
        this.mChatbotRoleSupported = false;
        this.mExts = new String[10];
        this.mCapTimestamp = 0L;
    }

    @UnsupportedAppUsage
    public boolean isImSupported() {
        return this.mImSupported;
    }

    @UnsupportedAppUsage
    public void setImSupported(boolean imSupported) {
        this.mImSupported = imSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtThumbSupported() {
        return this.mFtThumbSupported;
    }

    @UnsupportedAppUsage
    public void setFtThumbSupported(boolean ftThumbSupported) {
        this.mFtThumbSupported = ftThumbSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtSnFSupported() {
        return this.mFtSnFSupported;
    }

    @UnsupportedAppUsage
    public void setFtSnFSupported(boolean ftSnFSupported) {
        this.mFtSnFSupported = ftSnFSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtHttpSupported() {
        return this.mFtHttpSupported;
    }

    @UnsupportedAppUsage
    public void setFtHttpSupported(boolean ftHttpSupported) {
        this.mFtHttpSupported = ftHttpSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtSupported() {
        return this.mFtSupported;
    }

    @UnsupportedAppUsage
    public void setFtSupported(boolean ftSupported) {
        this.mFtSupported = ftSupported;
    }

    @UnsupportedAppUsage
    public boolean isIsSupported() {
        return this.mIsSupported;
    }

    @UnsupportedAppUsage
    public void setIsSupported(boolean isSupported) {
        this.mIsSupported = isSupported;
    }

    @UnsupportedAppUsage
    public boolean isVsDuringCSSupported() {
        return this.mVsDuringCSSupported;
    }

    @UnsupportedAppUsage
    public void setVsDuringCSSupported(boolean vsDuringCSSupported) {
        this.mVsDuringCSSupported = vsDuringCSSupported;
    }

    @UnsupportedAppUsage
    public boolean isVsSupported() {
        return this.mVsSupported;
    }

    @UnsupportedAppUsage
    public void setVsSupported(boolean vsSupported) {
        this.mVsSupported = vsSupported;
    }

    @UnsupportedAppUsage
    public boolean isSpSupported() {
        return this.mSpSupported;
    }

    @UnsupportedAppUsage
    public void setSpSupported(boolean spSupported) {
        this.mSpSupported = spSupported;
    }

    @UnsupportedAppUsage
    public boolean isCdViaPresenceSupported() {
        return this.mCdViaPresenceSupported;
    }

    @UnsupportedAppUsage
    public void setCdViaPresenceSupported(boolean cdViaPresenceSupported) {
        this.mCdViaPresenceSupported = cdViaPresenceSupported;
    }

    @UnsupportedAppUsage
    public boolean isIpVoiceSupported() {
        return this.mIpVoiceSupported;
    }

    @UnsupportedAppUsage
    public void setIpVoiceSupported(boolean ipVoiceSupported) {
        this.mIpVoiceSupported = ipVoiceSupported;
    }

    @UnsupportedAppUsage
    public boolean isIpVideoSupported() {
        return this.mIpVideoSupported;
    }

    @UnsupportedAppUsage
    public void setIpVideoSupported(boolean ipVideoSupported) {
        this.mIpVideoSupported = ipVideoSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoPullFtSupported() {
        return this.mGeoPullFtSupported;
    }

    @UnsupportedAppUsage
    public void setGeoPullFtSupported(boolean geoPullFtSupported) {
        this.mGeoPullFtSupported = geoPullFtSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoPullSupported() {
        return this.mGeoPullSupported;
    }

    @UnsupportedAppUsage
    public void setGeoPullSupported(boolean geoPullSupported) {
        this.mGeoPullSupported = geoPullSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoPushSupported() {
        return this.mGeoPushSupported;
    }

    @UnsupportedAppUsage
    public void setGeoPushSupported(boolean geoPushSupported) {
        this.mGeoPushSupported = geoPushSupported;
    }

    @UnsupportedAppUsage
    public boolean isSmSupported() {
        return this.mSmSupported;
    }

    @UnsupportedAppUsage
    public void setSmSupported(boolean smSupported) {
        this.mSmSupported = smSupported;
    }

    @UnsupportedAppUsage
    public boolean isFullSnFGroupChatSupported() {
        return this.mFullSnFGroupChatSupported;
    }

    @UnsupportedAppUsage
    public boolean isRcsIpVoiceCallSupported() {
        return this.mRcsIpVoiceCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isRcsIpVideoCallSupported() {
        return this.mRcsIpVideoCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isRcsIpVideoOnlyCallSupported() {
        return this.mRcsIpVideoOnlyCallSupported;
    }

    @UnsupportedAppUsage
    public void setFullSnFGroupChatSupported(boolean fullSnFGroupChatSupported) {
        this.mFullSnFGroupChatSupported = fullSnFGroupChatSupported;
    }

    @UnsupportedAppUsage
    public void setRcsIpVoiceCallSupported(boolean rcsIpVoiceCallSupported) {
        this.mRcsIpVoiceCallSupported = rcsIpVoiceCallSupported;
    }

    @UnsupportedAppUsage
    public void setRcsIpVideoCallSupported(boolean rcsIpVideoCallSupported) {
        this.mRcsIpVideoCallSupported = rcsIpVideoCallSupported;
    }

    @UnsupportedAppUsage
    public void setRcsIpVideoOnlyCallSupported(boolean rcsIpVideoOnlyCallSupported) {
        this.mRcsIpVideoOnlyCallSupported = rcsIpVideoOnlyCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoSmsSupported() {
        return this.mGeoSmsSupported;
    }

    @UnsupportedAppUsage
    public void setGeoSmsSupported(boolean geoSmsSupported) {
        this.mGeoSmsSupported = geoSmsSupported;
    }

    @UnsupportedAppUsage
    public boolean isCallComposerSupported() {
        return this.mCallComposerSupported;
    }

    @UnsupportedAppUsage
    public void setCallComposerSupported(boolean callComposerSupported) {
        this.mCallComposerSupported = callComposerSupported;
    }

    @UnsupportedAppUsage
    public boolean isPostCallSupported() {
        return this.mPostCallSupported;
    }

    @UnsupportedAppUsage
    public void setPostCallSupported(boolean postCallSupported) {
        this.mPostCallSupported = postCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isSharedMapSupported() {
        return this.mSharedMapSupported;
    }

    @UnsupportedAppUsage
    public void setSharedMapSupported(boolean sharedMapSupported) {
        this.mSharedMapSupported = sharedMapSupported;
    }

    @UnsupportedAppUsage
    public boolean isSharedSketchSupported() {
        return this.mSharedSketchSupported;
    }

    @UnsupportedAppUsage
    public void setSharedSketchSupported(boolean sharedSketchSupported) {
        this.mSharedSketchSupported = sharedSketchSupported;
    }

    @UnsupportedAppUsage
    public boolean isChatbotSupported() {
        return this.mChatbotSupported;
    }

    @UnsupportedAppUsage
    public void setChatbotSupported(boolean chatbotSupported) {
        this.mChatbotSupported = chatbotSupported;
    }

    @UnsupportedAppUsage
    public boolean isChatbotRoleSupported() {
        return this.mChatbotRoleSupported;
    }

    @UnsupportedAppUsage
    public void setChatbotRoleSupported(boolean chatbotRoleSupported) {
        this.mChatbotRoleSupported = chatbotRoleSupported;
    }

    public String[] getExts() {
        return this.mExts;
    }

    @UnsupportedAppUsage
    public void setExts(String[] exts) {
        this.mExts = exts;
    }

    @UnsupportedAppUsage
    public long getCapTimestamp() {
        return this.mCapTimestamp;
    }

    @UnsupportedAppUsage
    public void setCapTimestamp(long capTimestamp) {
        this.mCapTimestamp = capTimestamp;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mImSupported ? 1 : 0);
        dest.writeInt(this.mFtSupported ? 1 : 0);
        dest.writeInt(this.mFtThumbSupported ? 1 : 0);
        dest.writeInt(this.mFtSnFSupported ? 1 : 0);
        dest.writeInt(this.mFtHttpSupported ? 1 : 0);
        dest.writeInt(this.mIsSupported ? 1 : 0);
        dest.writeInt(this.mVsDuringCSSupported ? 1 : 0);
        dest.writeInt(this.mVsSupported ? 1 : 0);
        dest.writeInt(this.mSpSupported ? 1 : 0);
        dest.writeInt(this.mCdViaPresenceSupported ? 1 : 0);
        dest.writeInt(this.mIpVoiceSupported ? 1 : 0);
        dest.writeInt(this.mIpVideoSupported ? 1 : 0);
        dest.writeInt(this.mGeoPullFtSupported ? 1 : 0);
        dest.writeInt(this.mGeoPullSupported ? 1 : 0);
        dest.writeInt(this.mGeoPushSupported ? 1 : 0);
        dest.writeInt(this.mSmSupported ? 1 : 0);
        dest.writeInt(this.mFullSnFGroupChatSupported ? 1 : 0);
        dest.writeInt(this.mGeoSmsSupported ? 1 : 0);
        dest.writeInt(this.mCallComposerSupported ? 1 : 0);
        dest.writeInt(this.mPostCallSupported ? 1 : 0);
        dest.writeInt(this.mSharedMapSupported ? 1 : 0);
        dest.writeInt(this.mSharedSketchSupported ? 1 : 0);
        dest.writeInt(this.mChatbotSupported ? 1 : 0);
        dest.writeInt(this.mChatbotRoleSupported ? 1 : 0);
        dest.writeInt(this.mRcsIpVoiceCallSupported ? 1 : 0);
        dest.writeInt(this.mRcsIpVideoCallSupported ? 1 : 0);
        dest.writeInt(this.mRcsIpVideoOnlyCallSupported ? 1 : 0);
        dest.writeStringArray(this.mExts);
        dest.writeLong(this.mCapTimestamp);
    }

    private CapInfo(Parcel source) {
        this.mImSupported = false;
        this.mFtSupported = false;
        this.mFtThumbSupported = false;
        this.mFtSnFSupported = false;
        this.mFtHttpSupported = false;
        this.mIsSupported = false;
        this.mVsDuringCSSupported = false;
        this.mVsSupported = false;
        this.mSpSupported = false;
        this.mCdViaPresenceSupported = false;
        this.mIpVoiceSupported = false;
        this.mIpVideoSupported = false;
        this.mGeoPullFtSupported = false;
        this.mGeoPullSupported = false;
        this.mGeoPushSupported = false;
        this.mSmSupported = false;
        this.mFullSnFGroupChatSupported = false;
        this.mRcsIpVoiceCallSupported = false;
        this.mRcsIpVideoCallSupported = false;
        this.mRcsIpVideoOnlyCallSupported = false;
        this.mGeoSmsSupported = false;
        this.mCallComposerSupported = false;
        this.mPostCallSupported = false;
        this.mSharedMapSupported = false;
        this.mSharedSketchSupported = false;
        this.mChatbotSupported = false;
        this.mChatbotRoleSupported = false;
        this.mExts = new String[10];
        this.mCapTimestamp = 0L;
        readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
        this.mImSupported = source.readInt() != 0;
        this.mFtSupported = source.readInt() != 0;
        this.mFtThumbSupported = source.readInt() != 0;
        this.mFtSnFSupported = source.readInt() != 0;
        this.mFtHttpSupported = source.readInt() != 0;
        this.mIsSupported = source.readInt() != 0;
        this.mVsDuringCSSupported = source.readInt() != 0;
        this.mVsSupported = source.readInt() != 0;
        this.mSpSupported = source.readInt() != 0;
        this.mCdViaPresenceSupported = source.readInt() != 0;
        this.mIpVoiceSupported = source.readInt() != 0;
        this.mIpVideoSupported = source.readInt() != 0;
        this.mGeoPullFtSupported = source.readInt() != 0;
        this.mGeoPullSupported = source.readInt() != 0;
        this.mGeoPushSupported = source.readInt() != 0;
        this.mSmSupported = source.readInt() != 0;
        this.mFullSnFGroupChatSupported = source.readInt() != 0;
        this.mGeoSmsSupported = source.readInt() != 0;
        this.mCallComposerSupported = source.readInt() != 0;
        this.mPostCallSupported = source.readInt() != 0;
        this.mSharedMapSupported = source.readInt() != 0;
        this.mSharedSketchSupported = source.readInt() != 0;
        this.mChatbotSupported = source.readInt() != 0;
        this.mChatbotRoleSupported = source.readInt() != 0;
        this.mRcsIpVoiceCallSupported = source.readInt() != 0;
        this.mRcsIpVideoCallSupported = source.readInt() != 0;
        this.mRcsIpVideoOnlyCallSupported = source.readInt() != 0;
        this.mExts = source.createStringArray();
        this.mCapTimestamp = source.readLong();
    }
}
