package android.service.autofill;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class RegexValidator extends InternalValidator implements Validator, Parcelable {
    public static final Parcelable.Creator<RegexValidator> CREATOR = new Parcelable.Creator<RegexValidator>() { // from class: android.service.autofill.RegexValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RegexValidator createFromParcel(Parcel parcel) {
            return new RegexValidator((AutofillId) parcel.readParcelable(null), (Pattern) parcel.readSerializable());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RegexValidator[] newArray(int size) {
            return new RegexValidator[size];
        }
    };
    private static final String TAG = "RegexValidator";
    private final AutofillId mId;
    private final Pattern mRegex;

    public RegexValidator(AutofillId id, Pattern regex) {
        this.mId = (AutofillId) Preconditions.checkNotNull(id);
        this.mRegex = (Pattern) Preconditions.checkNotNull(regex);
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder finder) {
        String value = finder.findByAutofillId(this.mId);
        if (value == null) {
            Log.m64w(TAG, "No view for id " + this.mId);
            return false;
        }
        boolean valid = this.mRegex.matcher(value).matches();
        if (Helper.sDebug) {
            Log.m72d(TAG, "isValid(): " + valid);
        }
        return valid;
    }

    public String toString() {
        if (Helper.sDebug) {
            return "RegexValidator: [id=" + this.mId + ", regex=" + this.mRegex + "]";
        }
        return super.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.mId, flags);
        parcel.writeSerializable(this.mRegex);
    }
}
