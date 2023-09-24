package android.net;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class StringNetworkSpecifier extends NetworkSpecifier implements Parcelable {
    public static final Parcelable.Creator<StringNetworkSpecifier> CREATOR = new Parcelable.Creator<StringNetworkSpecifier>() { // from class: android.net.StringNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public StringNetworkSpecifier createFromParcel(Parcel in) {
            return new StringNetworkSpecifier(in.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public StringNetworkSpecifier[] newArray(int size) {
            return new StringNetworkSpecifier[size];
        }
    };
    @UnsupportedAppUsage
    public final String specifier;

    public StringNetworkSpecifier(String specifier) {
        Preconditions.checkStringNotEmpty(specifier);
        this.specifier = specifier;
    }

    @Override // android.net.NetworkSpecifier
    public boolean satisfiedBy(NetworkSpecifier other) {
        return equals(other);
    }

    public boolean equals(Object o) {
        if (o instanceof StringNetworkSpecifier) {
            return TextUtils.equals(this.specifier, ((StringNetworkSpecifier) o).specifier);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.specifier);
    }

    public String toString() {
        return this.specifier;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.specifier);
    }
}
