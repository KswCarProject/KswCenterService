package android.content.p002pm;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.UserHandle;
import android.p007os.UserManager;
import android.provider.SettingsStringUtil;

/* renamed from: android.content.pm.UserInfo */
/* loaded from: classes.dex */
public class UserInfo implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() { // from class: android.content.pm.UserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
    public static final int FLAG_ADMIN = 2;
    public static final int FLAG_DEMO = 512;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_EPHEMERAL = 256;
    public static final int FLAG_GUEST = 4;
    public static final int FLAG_INITIALIZED = 16;
    public static final int FLAG_MANAGED_PROFILE = 32;
    public static final int FLAG_MASK_USER_TYPE = 65535;
    @UnsupportedAppUsage
    public static final int FLAG_PRIMARY = 1;
    public static final int FLAG_QUIET_MODE = 128;
    public static final int FLAG_RESTRICTED = 8;
    public static final int NO_PROFILE_GROUP_ID = -10000;
    @UnsupportedAppUsage
    public long creationTime;
    @UnsupportedAppUsage
    public int flags;
    @UnsupportedAppUsage
    public boolean guestToRemove;
    @UnsupportedAppUsage
    public String iconPath;
    @UnsupportedAppUsage

    /* renamed from: id */
    public int f30id;
    public String lastLoggedInFingerprint;
    @UnsupportedAppUsage
    public long lastLoggedInTime;
    @UnsupportedAppUsage
    public String name;
    @UnsupportedAppUsage
    public boolean partial;
    public int profileBadge;
    @UnsupportedAppUsage
    public int profileGroupId;
    public int restrictedProfileParentId;
    @UnsupportedAppUsage
    public int serialNumber;

    @UnsupportedAppUsage
    public UserInfo(int id, String name, int flags) {
        this(id, name, null, flags);
    }

    @UnsupportedAppUsage
    public UserInfo(int id, String name, String iconPath, int flags) {
        this.f30id = id;
        this.name = name;
        this.flags = flags;
        this.iconPath = iconPath;
        this.profileGroupId = -10000;
        this.restrictedProfileParentId = -10000;
    }

    @UnsupportedAppUsage
    public boolean isPrimary() {
        return (this.flags & 1) == 1;
    }

    @UnsupportedAppUsage
    public boolean isAdmin() {
        return (this.flags & 2) == 2;
    }

    @UnsupportedAppUsage
    public boolean isGuest() {
        return (this.flags & 4) == 4;
    }

    @UnsupportedAppUsage
    public boolean isRestricted() {
        return (this.flags & 8) == 8;
    }

    @UnsupportedAppUsage
    public boolean isManagedProfile() {
        return (this.flags & 32) == 32;
    }

    @UnsupportedAppUsage
    public boolean isEnabled() {
        return (this.flags & 64) != 64;
    }

    public boolean isQuietModeEnabled() {
        return (this.flags & 128) == 128;
    }

    public boolean isEphemeral() {
        return (this.flags & 256) == 256;
    }

    public boolean isInitialized() {
        return (this.flags & 16) == 16;
    }

    public boolean isDemo() {
        return (this.flags & 512) == 512;
    }

    public boolean isSystemOnly() {
        return isSystemOnly(this.f30id);
    }

    public static boolean isSystemOnly(int userId) {
        return userId == 0 && UserManager.isSplitSystemUser();
    }

    public boolean supportsSwitchTo() {
        if (isEphemeral() && !isEnabled()) {
            return false;
        }
        return !isManagedProfile();
    }

    public boolean supportsSwitchToByUser() {
        boolean hideSystemUser = UserManager.isSplitSystemUser();
        return !(hideSystemUser && this.f30id == 0) && supportsSwitchTo();
    }

    public boolean canHaveProfile() {
        if (isManagedProfile() || isGuest() || isRestricted()) {
            return false;
        }
        return UserManager.isSplitSystemUser() ? this.f30id != 0 : this.f30id == 0;
    }

    public UserInfo() {
    }

    public UserInfo(UserInfo orig) {
        this.name = orig.name;
        this.iconPath = orig.iconPath;
        this.f30id = orig.f30id;
        this.flags = orig.flags;
        this.serialNumber = orig.serialNumber;
        this.creationTime = orig.creationTime;
        this.lastLoggedInTime = orig.lastLoggedInTime;
        this.lastLoggedInFingerprint = orig.lastLoggedInFingerprint;
        this.partial = orig.partial;
        this.profileGroupId = orig.profileGroupId;
        this.restrictedProfileParentId = orig.restrictedProfileParentId;
        this.guestToRemove = orig.guestToRemove;
        this.profileBadge = orig.profileBadge;
    }

    @UnsupportedAppUsage
    public UserHandle getUserHandle() {
        return new UserHandle(this.f30id);
    }

    public String toString() {
        return "UserInfo{" + this.f30id + SettingsStringUtil.DELIMITER + this.name + SettingsStringUtil.DELIMITER + Integer.toHexString(this.flags) + "}";
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeInt(this.f30id);
        dest.writeString(this.name);
        dest.writeString(this.iconPath);
        dest.writeInt(this.flags);
        dest.writeInt(this.serialNumber);
        dest.writeLong(this.creationTime);
        dest.writeLong(this.lastLoggedInTime);
        dest.writeString(this.lastLoggedInFingerprint);
        dest.writeInt(this.partial ? 1 : 0);
        dest.writeInt(this.profileGroupId);
        dest.writeInt(this.guestToRemove ? 1 : 0);
        dest.writeInt(this.restrictedProfileParentId);
        dest.writeInt(this.profileBadge);
    }

    private UserInfo(Parcel source) {
        this.f30id = source.readInt();
        this.name = source.readString();
        this.iconPath = source.readString();
        this.flags = source.readInt();
        this.serialNumber = source.readInt();
        this.creationTime = source.readLong();
        this.lastLoggedInTime = source.readLong();
        this.lastLoggedInFingerprint = source.readString();
        this.partial = source.readInt() != 0;
        this.profileGroupId = source.readInt();
        this.guestToRemove = source.readInt() != 0;
        this.restrictedProfileParentId = source.readInt();
        this.profileBadge = source.readInt();
    }
}
