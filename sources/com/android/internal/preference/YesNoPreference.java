package com.android.internal.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;

public class YesNoPreference extends DialogPreference {
    private boolean mWasPositiveResult;

    public YesNoPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public YesNoPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public YesNoPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 16842896);
    }

    public YesNoPreference(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (callChangeListener(Boolean.valueOf(positiveResult))) {
            setValue(positiveResult);
        }
    }

    public void setValue(boolean value) {
        this.mWasPositiveResult = value;
        persistBoolean(value);
        notifyDependencyChange(!value);
    }

    public boolean getValue() {
        return this.mWasPositiveResult;
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray a, int index) {
        return Boolean.valueOf(a.getBoolean(index, false));
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        boolean z;
        if (restorePersistedValue) {
            z = getPersistedBoolean(this.mWasPositiveResult);
        } else {
            z = ((Boolean) defaultValue).booleanValue();
        }
        setValue(z);
    }

    public boolean shouldDisableDependents() {
        return !this.mWasPositiveResult || super.shouldDisableDependents();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }
        SavedState myState = new SavedState(superState);
        myState.wasPositiveResult = getValue();
        return myState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setValue(myState.wasPositiveResult);
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean wasPositiveResult;

        public SavedState(Parcel source) {
            super(source);
            this.wasPositiveResult = source.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.wasPositiveResult ? 1 : 0);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }
    }
}
