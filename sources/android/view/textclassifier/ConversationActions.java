package android.view.textclassifier;

import android.app.Person;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.SpannedString;
import android.view.textclassifier.TextClassifier;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class ConversationActions implements Parcelable {
    public static final Parcelable.Creator<ConversationActions> CREATOR = new Parcelable.Creator<ConversationActions>() { // from class: android.view.textclassifier.ConversationActions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ConversationActions createFromParcel(Parcel in) {
            return new ConversationActions(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ConversationActions[] newArray(int size) {
            return new ConversationActions[size];
        }
    };
    private final List<ConversationAction> mConversationActions;
    private final String mId;

    public ConversationActions(List<ConversationAction> conversationActions, String id) {
        this.mConversationActions = Collections.unmodifiableList((List) Preconditions.checkNotNull(conversationActions));
        this.mId = id;
    }

    private ConversationActions(Parcel in) {
        this.mConversationActions = Collections.unmodifiableList(in.createTypedArrayList(ConversationAction.CREATOR));
        this.mId = in.readString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(this.mConversationActions);
        parcel.writeString(this.mId);
    }

    public List<ConversationAction> getConversationActions() {
        return this.mConversationActions;
    }

    public String getId() {
        return this.mId;
    }

    /* loaded from: classes4.dex */
    public static final class Message implements Parcelable {
        private final Person mAuthor;
        private final Bundle mExtras;
        private final ZonedDateTime mReferenceTime;
        private final CharSequence mText;
        public static final Person PERSON_USER_SELF = new Person.Builder().setKey("text-classifier-conversation-actions-user-self").build();
        public static final Person PERSON_USER_OTHERS = new Person.Builder().setKey("text-classifier-conversation-actions-user-others").build();
        public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() { // from class: android.view.textclassifier.ConversationActions.Message.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Message createFromParcel(Parcel in) {
                return new Message(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Message[] newArray(int size) {
                return new Message[size];
            }
        };

        private Message(Person author, ZonedDateTime referenceTime, CharSequence text, Bundle bundle) {
            this.mAuthor = author;
            this.mReferenceTime = referenceTime;
            this.mText = text;
            this.mExtras = (Bundle) Preconditions.checkNotNull(bundle);
        }

        private Message(Parcel in) {
            this.mAuthor = (Person) in.readParcelable(null);
            this.mReferenceTime = in.readInt() != 0 ? ZonedDateTime.parse(in.readString(), DateTimeFormatter.ISO_ZONED_DATE_TIME) : null;
            this.mText = in.readCharSequence();
            this.mExtras = in.readBundle();
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeParcelable(this.mAuthor, flags);
            parcel.writeInt(this.mReferenceTime != null ? 1 : 0);
            if (this.mReferenceTime != null) {
                parcel.writeString(this.mReferenceTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            }
            parcel.writeCharSequence(this.mText);
            parcel.writeBundle(this.mExtras);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Person getAuthor() {
            return this.mAuthor;
        }

        public ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        /* loaded from: classes4.dex */
        public static final class Builder {
            private Person mAuthor;
            private Bundle mExtras;
            private ZonedDateTime mReferenceTime;
            private CharSequence mText;

            public Builder(Person author) {
                this.mAuthor = (Person) Preconditions.checkNotNull(author);
            }

            public Builder setText(CharSequence text) {
                this.mText = text;
                return this;
            }

            public Builder setReferenceTime(ZonedDateTime referenceTime) {
                this.mReferenceTime = referenceTime;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Message build() {
                return new Message(this.mAuthor, this.mReferenceTime, this.mText == null ? null : new SpannedString(this.mText), this.mExtras == null ? Bundle.EMPTY : this.mExtras);
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.ConversationActions.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Request createFromParcel(Parcel in) {
                return Request.readFromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Request[] newArray(int size) {
                return new Request[size];
            }
        };
        public static final String HINT_FOR_IN_APP = "in_app";
        public static final String HINT_FOR_NOTIFICATION = "notification";
        private String mCallingPackageName;
        private final List<Message> mConversation;
        private Bundle mExtras;
        private final List<String> mHints;
        private final int mMaxSuggestions;
        private final TextClassifier.EntityConfig mTypeConfig;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes4.dex */
        public @interface Hint {
        }

        private Request(List<Message> conversation, TextClassifier.EntityConfig typeConfig, int maxSuggestions, List<String> hints, Bundle extras) {
            this.mConversation = (List) Preconditions.checkNotNull(conversation);
            this.mTypeConfig = (TextClassifier.EntityConfig) Preconditions.checkNotNull(typeConfig);
            this.mMaxSuggestions = maxSuggestions;
            this.mHints = hints;
            this.mExtras = extras;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel in) {
            ArrayList arrayList = new ArrayList();
            in.readParcelableList(arrayList, null);
            TextClassifier.EntityConfig typeConfig = (TextClassifier.EntityConfig) in.readParcelable(null);
            int maxSuggestions = in.readInt();
            List<String> hints = new ArrayList<>();
            in.readStringList(hints);
            String callingPackageName = in.readString();
            Bundle extras = in.readBundle();
            Request request = new Request(arrayList, typeConfig, maxSuggestions, hints, extras);
            request.setCallingPackageName(callingPackageName);
            return request;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeParcelableList(this.mConversation, flags);
            parcel.writeParcelable(this.mTypeConfig, flags);
            parcel.writeInt(this.mMaxSuggestions);
            parcel.writeStringList(this.mHints);
            parcel.writeString(this.mCallingPackageName);
            parcel.writeBundle(this.mExtras);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public TextClassifier.EntityConfig getTypeConfig() {
            return this.mTypeConfig;
        }

        public List<Message> getConversation() {
            return this.mConversation;
        }

        public int getMaxSuggestions() {
            return this.mMaxSuggestions;
        }

        public List<String> getHints() {
            return this.mHints;
        }

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String callingPackageName) {
            this.mCallingPackageName = callingPackageName;
        }

        public String getCallingPackageName() {
            return this.mCallingPackageName;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        /* loaded from: classes4.dex */
        public static final class Builder {
            private List<Message> mConversation;
            private Bundle mExtras;
            private List<String> mHints;
            private int mMaxSuggestions = -1;
            private TextClassifier.EntityConfig mTypeConfig;

            public Builder(List<Message> conversation) {
                this.mConversation = (List) Preconditions.checkNotNull(conversation);
            }

            public Builder setHints(List<String> hints) {
                this.mHints = hints;
                return this;
            }

            public Builder setTypeConfig(TextClassifier.EntityConfig typeConfig) {
                this.mTypeConfig = typeConfig;
                return this;
            }

            public Builder setMaxSuggestions(int maxSuggestions) {
                this.mMaxSuggestions = Preconditions.checkArgumentNonnegative(maxSuggestions);
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Request build() {
                List unmodifiableList;
                List unmodifiableList2 = Collections.unmodifiableList(this.mConversation);
                TextClassifier.EntityConfig build = this.mTypeConfig == null ? new TextClassifier.EntityConfig.Builder().build() : this.mTypeConfig;
                int i = this.mMaxSuggestions;
                if (this.mHints == null) {
                    unmodifiableList = Collections.emptyList();
                } else {
                    unmodifiableList = Collections.unmodifiableList(this.mHints);
                }
                return new Request(unmodifiableList2, build, i, unmodifiableList, this.mExtras == null ? Bundle.EMPTY : this.mExtras);
            }
        }
    }
}
