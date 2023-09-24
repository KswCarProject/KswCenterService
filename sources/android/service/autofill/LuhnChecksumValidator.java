package android.service.autofill;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.provider.Downloads;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class LuhnChecksumValidator extends InternalValidator implements Validator, Parcelable {
    public static final Parcelable.Creator<LuhnChecksumValidator> CREATOR = new Parcelable.Creator<LuhnChecksumValidator>() { // from class: android.service.autofill.LuhnChecksumValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public LuhnChecksumValidator createFromParcel(Parcel parcel) {
            return new LuhnChecksumValidator((AutofillId[]) parcel.readParcelableArray(null, AutofillId.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public LuhnChecksumValidator[] newArray(int size) {
            return new LuhnChecksumValidator[size];
        }
    };
    private static final String TAG = "LuhnChecksumValidator";
    private final AutofillId[] mIds;

    public LuhnChecksumValidator(AutofillId... ids) {
        this.mIds = (AutofillId[]) Preconditions.checkArrayElementsNotNull(ids, Downloads.EXTRA_IDS);
    }

    private static boolean isLuhnChecksumValid(String number) {
        int addend;
        int sum = 0;
        boolean isDoubled = false;
        int i = number.length() - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            int digit = number.charAt(i) - '0';
            if (digit >= 0 && digit <= 9) {
                if (isDoubled) {
                    int addend2 = digit * 2;
                    if (addend2 > 9) {
                        addend = addend2 - 9;
                    } else {
                        addend = addend2;
                    }
                } else {
                    addend = digit;
                }
                sum += addend;
                isDoubled = isDoubled ? false : true;
            }
            i--;
        }
        int i2 = sum % 10;
        return i2 == 0;
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder finder) {
        AutofillId[] autofillIdArr;
        if (this.mIds == null || this.mIds.length == 0) {
            return false;
        }
        StringBuilder builder = new StringBuilder();
        for (AutofillId id : this.mIds) {
            String partialNumber = finder.findByAutofillId(id);
            if (partialNumber == null) {
                if (Helper.sDebug) {
                    Log.m72d(TAG, "No partial number for id " + id);
                }
                return false;
            }
            builder.append(partialNumber);
        }
        String number = builder.toString();
        boolean valid = isLuhnChecksumValid(number);
        if (Helper.sDebug) {
            Log.m72d(TAG, "isValid(" + number.length() + " chars): " + valid);
        }
        return valid;
    }

    public String toString() {
        if (Helper.sDebug) {
            return "LuhnChecksumValidator: [ids=" + Arrays.toString(this.mIds) + "]";
        }
        return super.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelableArray(this.mIds, flags);
    }
}
