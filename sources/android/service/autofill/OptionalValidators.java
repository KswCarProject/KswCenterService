package android.service.autofill;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
final class OptionalValidators extends InternalValidator {
    public static final Parcelable.Creator<OptionalValidators> CREATOR = new Parcelable.Creator<OptionalValidators>() { // from class: android.service.autofill.OptionalValidators.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OptionalValidators createFromParcel(Parcel parcel) {
            return new OptionalValidators((InternalValidator[]) parcel.readParcelableArray(null, InternalValidator.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OptionalValidators[] newArray(int size) {
            return new OptionalValidators[size];
        }
    };
    private static final String TAG = "OptionalValidators";
    private final InternalValidator[] mValidators;

    OptionalValidators(InternalValidator[] validators) {
        this.mValidators = (InternalValidator[]) Preconditions.checkArrayElementsNotNull(validators, "validators");
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder finder) {
        InternalValidator[] internalValidatorArr;
        for (InternalValidator validator : this.mValidators) {
            boolean valid = validator.isValid(finder);
            if (Helper.sDebug) {
                Log.m72d(TAG, "isValid(" + validator + "): " + valid);
            }
            if (valid) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (Helper.sDebug) {
            return "OptionalValidators: [validators=" + this.mValidators + "]";
        }
        return super.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.mValidators, flags);
    }
}
