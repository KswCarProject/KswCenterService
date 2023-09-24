package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;

/* loaded from: classes3.dex */
class KeymasterBooleanArgument extends KeymasterArgument {
    public final boolean value;

    public KeymasterBooleanArgument(int tag) {
        super(tag);
        this.value = true;
        if (KeymasterDefs.getTagType(tag) != 1879048192) {
            throw new IllegalArgumentException("Bad bool tag " + tag);
        }
    }

    @UnsupportedAppUsage
    public KeymasterBooleanArgument(int tag, Parcel in) {
        super(tag);
        this.value = true;
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel out) {
    }
}
