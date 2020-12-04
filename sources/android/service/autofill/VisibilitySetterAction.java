package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

public final class VisibilitySetterAction extends InternalOnClickAction implements OnClickAction, Parcelable {
    public static final Parcelable.Creator<VisibilitySetterAction> CREATOR = new Parcelable.Creator<VisibilitySetterAction>() {
        public VisibilitySetterAction createFromParcel(Parcel parcel) {
            SparseIntArray visibilities = parcel.readSparseIntArray();
            Builder builder = null;
            for (int i = 0; i < visibilities.size(); i++) {
                int id = visibilities.keyAt(i);
                int visibility = visibilities.valueAt(i);
                if (builder == null) {
                    builder = new Builder(id, visibility);
                } else {
                    builder.setVisibility(id, visibility);
                }
            }
            if (builder == null) {
                return null;
            }
            return builder.build();
        }

        public VisibilitySetterAction[] newArray(int size) {
            return new VisibilitySetterAction[size];
        }
    };
    private static final String TAG = "VisibilitySetterAction";
    private final SparseIntArray mVisibilities;

    private VisibilitySetterAction(Builder builder) {
        this.mVisibilities = builder.mVisibilities;
    }

    public void onClick(ViewGroup rootView) {
        for (int i = 0; i < this.mVisibilities.size(); i++) {
            int id = this.mVisibilities.keyAt(i);
            View child = rootView.findViewById(id);
            if (child == null) {
                Slog.w(TAG, "Skipping view id " + id + " because it's not found on " + rootView);
            } else {
                int visibility = this.mVisibilities.valueAt(i);
                if (Helper.sVerbose) {
                    Slog.v(TAG, "Changing visibility of view " + child + " from " + child.getVisibility() + " to  " + visibility);
                }
                child.setVisibility(visibility);
            }
        }
    }

    public static final class Builder {
        private boolean mDestroyed;
        /* access modifiers changed from: private */
        public final SparseIntArray mVisibilities = new SparseIntArray();

        public Builder(int id, int visibility) {
            setVisibility(id, visibility);
        }

        public Builder setVisibility(int id, int visibility) {
            throwIfDestroyed();
            if (visibility == 0 || visibility == 4 || visibility == 8) {
                this.mVisibilities.put(id, visibility);
                return this;
            }
            throw new IllegalArgumentException("Invalid visibility: " + visibility);
        }

        public VisibilitySetterAction build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new VisibilitySetterAction(this);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "VisibilitySetterAction: [" + this.mVisibilities + "]";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeSparseIntArray(this.mVisibilities);
    }
}
