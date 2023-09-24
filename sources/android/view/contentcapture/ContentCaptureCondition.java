package android.view.contentcapture;

import android.content.LocusId;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.DebugUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class ContentCaptureCondition implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureCondition> CREATOR = new Parcelable.Creator<ContentCaptureCondition>() { // from class: android.view.contentcapture.ContentCaptureCondition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ContentCaptureCondition createFromParcel(Parcel parcel) {
            return new ContentCaptureCondition((LocusId) parcel.readParcelable(null), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ContentCaptureCondition[] newArray(int size) {
            return new ContentCaptureCondition[size];
        }
    };
    public static final int FLAG_IS_REGEX = 2;
    private final int mFlags;
    private final LocusId mLocusId;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    @interface Flags {
    }

    public ContentCaptureCondition(LocusId locusId, int flags) {
        this.mLocusId = (LocusId) Preconditions.checkNotNull(locusId);
        this.mFlags = flags;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int hashCode() {
        int result = (1 * 31) + this.mFlags;
        return (result * 31) + (this.mLocusId == null ? 0 : this.mLocusId.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ContentCaptureCondition other = (ContentCaptureCondition) obj;
        if (this.mFlags != other.mFlags) {
            return false;
        }
        if (this.mLocusId == null) {
            if (other.mLocusId != null) {
                return false;
            }
        } else if (!this.mLocusId.equals(other.mLocusId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder string = new StringBuilder(this.mLocusId.toString());
        if (this.mFlags != 0) {
            string.append(" (");
            string.append(DebugUtils.flagsToString(ContentCaptureCondition.class, "FLAG_", this.mFlags));
            string.append(')');
        }
        return string.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.mLocusId, flags);
        parcel.writeInt(this.mFlags);
    }
}
