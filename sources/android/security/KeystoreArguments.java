package android.security;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes3.dex */
public class KeystoreArguments implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<KeystoreArguments> CREATOR = new Parcelable.Creator<KeystoreArguments>() { // from class: android.security.KeystoreArguments.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public KeystoreArguments createFromParcel(Parcel in) {
            return new KeystoreArguments(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public KeystoreArguments[] newArray(int size) {
            return new KeystoreArguments[size];
        }
    };
    public byte[][] args;

    public KeystoreArguments() {
        this.args = null;
    }

    @UnsupportedAppUsage
    public KeystoreArguments(byte[][] args) {
        this.args = args;
    }

    private KeystoreArguments(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        byte[][] bArr;
        if (this.args == null) {
            out.writeInt(0);
            return;
        }
        out.writeInt(this.args.length);
        for (byte[] arg : this.args) {
            out.writeByteArray(arg);
        }
    }

    private void readFromParcel(Parcel in) {
        int length = in.readInt();
        this.args = new byte[length];
        for (int i = 0; i < length; i++) {
            this.args[i] = in.createByteArray();
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
