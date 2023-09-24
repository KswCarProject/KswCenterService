package android.content.p001om;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* renamed from: android.content.om.OverlayInfo */
/* loaded from: classes.dex */
public final class OverlayInfo implements Parcelable {
    public static final String CATEGORY_THEME = "android.theme";
    public static final Parcelable.Creator<OverlayInfo> CREATOR = new Parcelable.Creator<OverlayInfo>() { // from class: android.content.om.OverlayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OverlayInfo createFromParcel(Parcel source) {
            return new OverlayInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OverlayInfo[] newArray(int size) {
            return new OverlayInfo[size];
        }
    };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 3;
    public static final int STATE_ENABLED_STATIC = 6;
    public static final int STATE_MISSING_TARGET = 0;
    public static final int STATE_NO_IDMAP = 1;
    public static final int STATE_OVERLAY_IS_BEING_REPLACED = 5;
    @Deprecated
    public static final int STATE_TARGET_IS_BEING_REPLACED = 4;
    public static final int STATE_UNKNOWN = -1;
    public final String baseCodePath;
    public final String category;
    public final boolean isStatic;
    public final String packageName;
    public final int priority;
    @UnsupportedAppUsage
    public final int state;
    public final String targetOverlayableName;
    public final String targetPackageName;
    public final int userId;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.content.om.OverlayInfo$State */
    /* loaded from: classes.dex */
    public @interface State {
    }

    public OverlayInfo(OverlayInfo source, int state) {
        this(source.packageName, source.targetPackageName, source.targetOverlayableName, source.category, source.baseCodePath, state, source.userId, source.priority, source.isStatic);
    }

    public OverlayInfo(String packageName, String targetPackageName, String targetOverlayableName, String category, String baseCodePath, int state, int userId, int priority, boolean isStatic) {
        this.packageName = packageName;
        this.targetPackageName = targetPackageName;
        this.targetOverlayableName = targetOverlayableName;
        this.category = category;
        this.baseCodePath = baseCodePath;
        this.state = state;
        this.userId = userId;
        this.priority = priority;
        this.isStatic = isStatic;
        ensureValidState();
    }

    public OverlayInfo(Parcel source) {
        this.packageName = source.readString();
        this.targetPackageName = source.readString();
        this.targetOverlayableName = source.readString();
        this.category = source.readString();
        this.baseCodePath = source.readString();
        this.state = source.readInt();
        this.userId = source.readInt();
        this.priority = source.readInt();
        this.isStatic = source.readBoolean();
        ensureValidState();
    }

    @SystemApi
    public String getPackageName() {
        return this.packageName;
    }

    @SystemApi
    public String getTargetPackageName() {
        return this.targetPackageName;
    }

    @SystemApi
    public String getCategory() {
        return this.category;
    }

    @SystemApi
    public int getUserId() {
        return this.userId;
    }

    @SystemApi
    public String getTargetOverlayableName() {
        return this.targetOverlayableName;
    }

    private void ensureValidState() {
        if (this.packageName == null) {
            throw new IllegalArgumentException("packageName must not be null");
        }
        if (this.targetPackageName == null) {
            throw new IllegalArgumentException("targetPackageName must not be null");
        }
        if (this.baseCodePath == null) {
            throw new IllegalArgumentException("baseCodePath must not be null");
        }
        switch (this.state) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return;
            default:
                throw new IllegalArgumentException("State " + this.state + " is not a valid state");
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.targetPackageName);
        dest.writeString(this.targetOverlayableName);
        dest.writeString(this.category);
        dest.writeString(this.baseCodePath);
        dest.writeInt(this.state);
        dest.writeInt(this.userId);
        dest.writeInt(this.priority);
        dest.writeBoolean(this.isStatic);
    }

    @SystemApi
    public boolean isEnabled() {
        int i = this.state;
        if (i == 3 || i == 6) {
            return true;
        }
        return false;
    }

    public static String stateToString(int state) {
        switch (state) {
            case -1:
                return "STATE_UNKNOWN";
            case 0:
                return "STATE_MISSING_TARGET";
            case 1:
                return "STATE_NO_IDMAP";
            case 2:
                return "STATE_DISABLED";
            case 3:
                return "STATE_ENABLED";
            case 4:
                return "STATE_TARGET_IS_BEING_REPLACED";
            case 5:
                return "STATE_OVERLAY_IS_BEING_REPLACED";
            case 6:
                return "STATE_ENABLED_STATIC";
            default:
                return "<unknown state>";
        }
    }

    public int hashCode() {
        int result = (1 * 31) + this.userId;
        return (((((((((((result * 31) + this.state) * 31) + (this.packageName == null ? 0 : this.packageName.hashCode())) * 31) + (this.targetPackageName == null ? 0 : this.targetPackageName.hashCode())) * 31) + (this.targetOverlayableName == null ? 0 : this.targetOverlayableName.hashCode())) * 31) + (this.category == null ? 0 : this.category.hashCode())) * 31) + (this.baseCodePath != null ? this.baseCodePath.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OverlayInfo other = (OverlayInfo) obj;
        if (this.userId == other.userId && this.state == other.state && this.packageName.equals(other.packageName) && this.targetPackageName.equals(other.targetPackageName) && Objects.equals(this.targetOverlayableName, other.targetOverlayableName) && Objects.equals(this.category, other.category) && this.baseCodePath.equals(other.baseCodePath)) {
            return true;
        }
        return false;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("OverlayInfo { overlay=");
        sb.append(this.packageName);
        sb.append(", targetPackage=");
        sb.append(this.targetPackageName);
        if (this.targetOverlayableName == null) {
            str = "";
        } else {
            str = ", targetOverlayable=" + this.targetOverlayableName;
        }
        sb.append(str);
        sb.append(", state=");
        sb.append(this.state);
        sb.append(" (");
        sb.append(stateToString(this.state));
        sb.append("), userId=");
        sb.append(this.userId);
        sb.append(" }");
        return sb.toString();
    }
}
