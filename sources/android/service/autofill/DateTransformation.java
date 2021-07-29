package android.service.autofill;

import android.icu.text.DateFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import com.ibm.icu.text.PluralRules;
import java.util.Date;

public final class DateTransformation extends InternalTransformation implements Transformation, Parcelable {
    public static final Parcelable.Creator<DateTransformation> CREATOR = new Parcelable.Creator<DateTransformation>() {
        public DateTransformation createFromParcel(Parcel parcel) {
            return new DateTransformation((AutofillId) parcel.readParcelable((ClassLoader) null), (DateFormat) parcel.readSerializable());
        }

        public DateTransformation[] newArray(int size) {
            return new DateTransformation[size];
        }
    };
    private static final String TAG = "DateTransformation";
    private final DateFormat mDateFormat;
    private final AutofillId mFieldId;

    public DateTransformation(AutofillId id, DateFormat dateFormat) {
        this.mFieldId = (AutofillId) Preconditions.checkNotNull(id);
        this.mDateFormat = (DateFormat) Preconditions.checkNotNull(dateFormat);
    }

    public void apply(ValueFinder finder, RemoteViews parentTemplate, int childViewId) throws Exception {
        AutofillValue value = finder.findRawValueByAutofillId(this.mFieldId);
        if (value == null) {
            Log.w(TAG, "No value for id " + this.mFieldId);
        } else if (!value.isDate()) {
            Log.w(TAG, "Value for " + this.mFieldId + " is not date: " + value);
        } else {
            try {
                Date date = new Date(value.getDateValue());
                String transformed = this.mDateFormat.format(date);
                if (Helper.sDebug) {
                    Log.d(TAG, "Transformed " + date + " to " + transformed);
                }
                parentTemplate.setCharSequence(childViewId, "setText", transformed);
            } catch (Exception e) {
                Log.w(TAG, "Could not apply " + this.mDateFormat + " to " + value + PluralRules.KEYWORD_RULE_SEPARATOR + e);
            }
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "DateTransformation: [id=" + this.mFieldId + ", format=" + this.mDateFormat + "]";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.mFieldId, flags);
        parcel.writeSerializable(this.mDateFormat);
    }
}
