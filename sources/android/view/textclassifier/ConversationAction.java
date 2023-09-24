package android.view.textclassifier;

import android.app.RemoteAction;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class ConversationAction implements Parcelable {
    public static final Parcelable.Creator<ConversationAction> CREATOR = new Parcelable.Creator<ConversationAction>() { // from class: android.view.textclassifier.ConversationAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ConversationAction createFromParcel(Parcel in) {
            return new ConversationAction(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ConversationAction[] newArray(int size) {
            return new ConversationAction[size];
        }
    };
    public static final String TYPE_ADD_CONTACT = "add_contact";
    public static final String TYPE_CALL_PHONE = "call_phone";
    public static final String TYPE_COPY = "copy";
    public static final String TYPE_CREATE_REMINDER = "create_reminder";
    public static final String TYPE_OPEN_URL = "open_url";
    public static final String TYPE_SEND_EMAIL = "send_email";
    public static final String TYPE_SEND_SMS = "send_sms";
    public static final String TYPE_SHARE_LOCATION = "share_location";
    public static final String TYPE_TEXT_REPLY = "text_reply";
    public static final String TYPE_TRACK_FLIGHT = "track_flight";
    public static final String TYPE_VIEW_CALENDAR = "view_calendar";
    public static final String TYPE_VIEW_MAP = "view_map";
    private final RemoteAction mAction;
    private final Bundle mExtras;
    private final float mScore;
    private final CharSequence mTextReply;
    private final String mType;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface ActionType {
    }

    private ConversationAction(String type, RemoteAction action, CharSequence textReply, float score, Bundle extras) {
        this.mType = (String) Preconditions.checkNotNull(type);
        this.mAction = action;
        this.mTextReply = textReply;
        this.mScore = score;
        this.mExtras = (Bundle) Preconditions.checkNotNull(extras);
    }

    private ConversationAction(Parcel in) {
        this.mType = in.readString();
        this.mAction = (RemoteAction) in.readParcelable(null);
        this.mTextReply = in.readCharSequence();
        this.mScore = in.readFloat();
        this.mExtras = in.readBundle();
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.mType);
        parcel.writeParcelable(this.mAction, flags);
        parcel.writeCharSequence(this.mTextReply);
        parcel.writeFloat(this.mScore);
        parcel.writeBundle(this.mExtras);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getType() {
        return this.mType;
    }

    public RemoteAction getAction() {
        return this.mAction;
    }

    public float getConfidenceScore() {
        return this.mScore;
    }

    public CharSequence getTextReply() {
        return this.mTextReply;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private RemoteAction mAction;
        private Bundle mExtras;
        private float mScore;
        private CharSequence mTextReply;
        private String mType;

        public Builder(String actionType) {
            this.mType = (String) Preconditions.checkNotNull(actionType);
        }

        public Builder setAction(RemoteAction action) {
            this.mAction = action;
            return this;
        }

        public Builder setTextReply(CharSequence textReply) {
            this.mTextReply = textReply;
            return this;
        }

        public Builder setConfidenceScore(float score) {
            this.mScore = score;
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public ConversationAction build() {
            return new ConversationAction(this.mType, this.mAction, this.mTextReply, this.mScore, this.mExtras == null ? Bundle.EMPTY : this.mExtras);
        }
    }
}
