package android.view.textclassifier;

import android.icu.util.ULocale;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.ArrayMap;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public final class TextLanguage implements Parcelable {
    public static final Parcelable.Creator<TextLanguage> CREATOR = new Parcelable.Creator<TextLanguage>() { // from class: android.view.textclassifier.TextLanguage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public TextLanguage createFromParcel(Parcel in) {
            return TextLanguage.readFromParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public TextLanguage[] newArray(int size) {
            return new TextLanguage[size];
        }
    };
    static final TextLanguage EMPTY = new Builder().build();
    private final Bundle mBundle;
    private final EntityConfidence mEntityConfidence;
    private final String mId;

    private TextLanguage(String id, EntityConfidence entityConfidence, Bundle bundle) {
        this.mId = id;
        this.mEntityConfidence = entityConfidence;
        this.mBundle = bundle;
    }

    public String getId() {
        return this.mId;
    }

    public int getLocaleHypothesisCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public ULocale getLocale(int index) {
        return ULocale.forLanguageTag(this.mEntityConfidence.getEntities().get(index));
    }

    public float getConfidenceScore(ULocale locale) {
        return this.mEntityConfidence.getConfidenceScore(locale.toLanguageTag());
    }

    public Bundle getExtras() {
        return this.mBundle;
    }

    public String toString() {
        return String.format(Locale.US, "TextLanguage {id=%s, locales=%s, bundle=%s}", this.mId, this.mEntityConfidence, this.mBundle);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        this.mEntityConfidence.writeToParcel(dest, flags);
        dest.writeBundle(this.mBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TextLanguage readFromParcel(Parcel in) {
        return new TextLanguage(in.readString(), EntityConfidence.CREATOR.createFromParcel(in), in.readBundle());
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private Bundle mBundle;
        private final Map<String, Float> mEntityConfidenceMap = new ArrayMap();
        private String mId;

        public Builder putLocale(ULocale locale, float confidenceScore) {
            Preconditions.checkNotNull(locale);
            this.mEntityConfidenceMap.put(locale.toLanguageTag(), Float.valueOf(confidenceScore));
            return this;
        }

        public Builder setId(String id) {
            this.mId = id;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mBundle = (Bundle) Preconditions.checkNotNull(bundle);
            return this;
        }

        public TextLanguage build() {
            this.mBundle = this.mBundle == null ? Bundle.EMPTY : this.mBundle;
            return new TextLanguage(this.mId, new EntityConfidence(this.mEntityConfidenceMap), this.mBundle);
        }
    }

    /* loaded from: classes4.dex */
    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.TextLanguage.Request.1
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
        private String mCallingPackageName;
        private final Bundle mExtra;
        private final CharSequence mText;

        private Request(CharSequence text, Bundle bundle) {
            this.mText = text;
            this.mExtra = bundle;
        }

        public CharSequence getText() {
            return this.mText;
        }

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String callingPackageName) {
            this.mCallingPackageName = callingPackageName;
        }

        public String getCallingPackageName() {
            return this.mCallingPackageName;
        }

        public Bundle getExtras() {
            return this.mExtra;
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeCharSequence(this.mText);
            dest.writeString(this.mCallingPackageName);
            dest.writeBundle(this.mExtra);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel in) {
            CharSequence text = in.readCharSequence();
            String callingPackageName = in.readString();
            Bundle extra = in.readBundle();
            Request request = new Request(text, extra);
            request.setCallingPackageName(callingPackageName);
            return request;
        }

        /* loaded from: classes4.dex */
        public static final class Builder {
            private Bundle mBundle;
            private final CharSequence mText;

            public Builder(CharSequence text) {
                this.mText = (CharSequence) Preconditions.checkNotNull(text);
            }

            public Builder setExtras(Bundle bundle) {
                this.mBundle = (Bundle) Preconditions.checkNotNull(bundle);
                return this;
            }

            public Request build() {
                return new Request(this.mText.toString(), this.mBundle == null ? Bundle.EMPTY : this.mBundle);
            }
        }
    }
}
