package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class SelectionsRequest implements Parcelable {
    public static final Parcelable.Creator<SelectionsRequest> CREATOR = new Parcelable.Creator<SelectionsRequest>() {
        public SelectionsRequest createFromParcel(Parcel source) {
            return new SelectionsRequest(source.readInt(), (Point) source.readTypedObject(Point.CREATOR), source.readBundle());
        }

        public SelectionsRequest[] newArray(int size) {
            return new SelectionsRequest[size];
        }
    };
    private final Bundle mExtras;
    private final Point mInterestPoint;
    private final int mTaskId;

    private SelectionsRequest(int taskId, Point interestPoint, Bundle extras) {
        this.mTaskId = taskId;
        this.mInterestPoint = interestPoint;
        this.mExtras = extras;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public Point getInterestPoint() {
        return this.mInterestPoint;
    }

    public Bundle getExtras() {
        return this.mExtras == null ? new Bundle() : this.mExtras;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mTaskId);
        dest.writeTypedObject(this.mInterestPoint, flags);
        dest.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private Point mInterestPoint;
        private final int mTaskId;

        public Builder(int taskId) {
            this.mTaskId = taskId;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Builder setInterestPoint(Point interestPoint) {
            this.mInterestPoint = interestPoint;
            return this;
        }

        public SelectionsRequest build() {
            return new SelectionsRequest(this.mTaskId, this.mInterestPoint, this.mExtras);
        }
    }
}
