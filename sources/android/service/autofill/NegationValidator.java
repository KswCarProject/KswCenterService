package android.service.autofill;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
final class NegationValidator extends InternalValidator {
    public static final Parcelable.Creator<NegationValidator> CREATOR = new Parcelable.Creator<NegationValidator>() { // from class: android.service.autofill.NegationValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public NegationValidator createFromParcel(Parcel parcel) {
            return new NegationValidator((InternalValidator) parcel.readParcelable(null));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public NegationValidator[] newArray(int size) {
            return new NegationValidator[size];
        }
    };
    private final InternalValidator mValidator;

    NegationValidator(InternalValidator validator) {
        this.mValidator = (InternalValidator) Preconditions.checkNotNull(validator);
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder finder) {
        return !this.mValidator.isValid(finder);
    }

    public String toString() {
        if (Helper.sDebug) {
            return "NegationValidator: [validator=" + this.mValidator + "]";
        }
        return super.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mValidator, flags);
    }
}
