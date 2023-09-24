package android.service.autofill;

import android.icu.text.DateFormat;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import com.ibm.icu.text.PluralRules;
import java.util.Date;

/* loaded from: classes3.dex */
public final class DateValueSanitizer extends InternalSanitizer implements Sanitizer, Parcelable {
    public static final Parcelable.Creator<DateValueSanitizer> CREATOR = new Parcelable.Creator<DateValueSanitizer>() { // from class: android.service.autofill.DateValueSanitizer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public DateValueSanitizer createFromParcel(Parcel parcel) {
            return new DateValueSanitizer((DateFormat) parcel.readSerializable());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public DateValueSanitizer[] newArray(int size) {
            return new DateValueSanitizer[size];
        }
    };
    private static final String TAG = "DateValueSanitizer";
    private final DateFormat mDateFormat;

    public DateValueSanitizer(DateFormat dateFormat) {
        this.mDateFormat = (DateFormat) Preconditions.checkNotNull(dateFormat);
    }

    @Override // android.service.autofill.InternalSanitizer
    public AutofillValue sanitize(AutofillValue value) {
        if (value == null) {
            Log.m64w(TAG, "sanitize() called with null value");
            return null;
        } else if (!value.isDate()) {
            if (Helper.sDebug) {
                Log.m72d(TAG, value + " is not a date");
            }
            return null;
        } else {
            try {
                Date date = new Date(value.getDateValue());
                String converted = this.mDateFormat.format(date);
                if (Helper.sDebug) {
                    Log.m72d(TAG, "Transformed " + date + " to " + converted);
                }
                Date sanitized = this.mDateFormat.parse(converted);
                if (Helper.sDebug) {
                    Log.m72d(TAG, "Sanitized to " + sanitized);
                }
                return AutofillValue.forDate(sanitized.getTime());
            } catch (Exception e) {
                Log.m64w(TAG, "Could not apply " + this.mDateFormat + " to " + value + PluralRules.KEYWORD_RULE_SEPARATOR + e);
                return null;
            }
        }
    }

    public String toString() {
        if (Helper.sDebug) {
            return "DateValueSanitizer: [dateFormat=" + this.mDateFormat + "]";
        }
        return super.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeSerializable(this.mDateFormat);
    }
}
