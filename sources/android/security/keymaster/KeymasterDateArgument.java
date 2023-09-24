package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import java.util.Date;

/* loaded from: classes3.dex */
class KeymasterDateArgument extends KeymasterArgument {
    public final Date date;

    public KeymasterDateArgument(int tag, Date date) {
        super(tag);
        if (KeymasterDefs.getTagType(tag) != 1610612736) {
            throw new IllegalArgumentException("Bad date tag " + tag);
        }
        this.date = date;
    }

    @UnsupportedAppUsage
    public KeymasterDateArgument(int tag, Parcel in) {
        super(tag);
        this.date = new Date(in.readLong());
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel out) {
        out.writeLong(this.date.getTime());
    }
}
