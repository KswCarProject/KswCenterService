package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;

/* loaded from: classes3.dex */
class KeymasterBlobArgument extends KeymasterArgument {
    @UnsupportedAppUsage
    public final byte[] blob;

    @UnsupportedAppUsage
    public KeymasterBlobArgument(int tag, byte[] blob) {
        super(tag);
        int tagType = KeymasterDefs.getTagType(tag);
        if (tagType != Integer.MIN_VALUE && tagType != -1879048192) {
            throw new IllegalArgumentException("Bad blob tag " + tag);
        }
        this.blob = blob;
    }

    @UnsupportedAppUsage
    public KeymasterBlobArgument(int tag, Parcel in) {
        super(tag);
        this.blob = in.createByteArray();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel out) {
        out.writeByteArray(this.blob);
    }
}
