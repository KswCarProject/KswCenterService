package android.view.textclassifier;

import android.os.LocaleList;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.ArrayMap;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TextClassifier {
    public static final String DEFAULT_LOG_TAG = "androidtc";
    public static final String EXTRA_FROM_TEXT_CLASSIFIER = "android.view.textclassifier.extra.FROM_TEXT_CLASSIFIER";
    public static final String HINT_TEXT_IS_EDITABLE = "android.text_is_editable";
    public static final String HINT_TEXT_IS_NOT_EDITABLE = "android.text_is_not_editable";
    public static final int LOCAL = 0;
    public static final TextClassifier NO_OP = new TextClassifier() {
        public String toString() {
            return "TextClassifier.NO_OP";
        }
    };
    public static final int SYSTEM = 1;
    public static final String TYPE_ADDRESS = "address";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATE_TIME = "datetime";
    public static final String TYPE_DICTIONARY = "dictionary";
    public static final String TYPE_EMAIL = "email";
    public static final String TYPE_FLIGHT_NUMBER = "flight";
    public static final String TYPE_OTHER = "other";
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_UNKNOWN = "";
    public static final String TYPE_URL = "url";
    public static final String WIDGET_TYPE_CUSTOM_EDITTEXT = "customedit";
    public static final String WIDGET_TYPE_CUSTOM_TEXTVIEW = "customview";
    public static final String WIDGET_TYPE_CUSTOM_UNSELECTABLE_TEXTVIEW = "nosel-customview";
    public static final String WIDGET_TYPE_EDITTEXT = "edittext";
    public static final String WIDGET_TYPE_EDIT_WEBVIEW = "edit-webview";
    public static final String WIDGET_TYPE_NOTIFICATION = "notification";
    public static final String WIDGET_TYPE_TEXTVIEW = "textview";
    public static final String WIDGET_TYPE_UNKNOWN = "unknown";
    public static final String WIDGET_TYPE_UNSELECTABLE_TEXTVIEW = "nosel-textview";
    public static final String WIDGET_TYPE_WEBVIEW = "webview";

    @Retention(RetentionPolicy.SOURCE)
    public @interface EntityType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Hints {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextClassifierType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WidgetType {
    }

    TextSelection suggestSelection(TextSelection.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return new TextSelection.Builder(request.getStartIndex(), request.getEndIndex()).build();
    }

    TextSelection suggestSelection(CharSequence text, int selectionStartIndex, int selectionEndIndex, LocaleList defaultLocales) {
        return suggestSelection(new TextSelection.Request.Builder(text, selectionStartIndex, selectionEndIndex).setDefaultLocales(defaultLocales).build());
    }

    TextClassification classifyText(TextClassification.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return TextClassification.EMPTY;
    }

    TextClassification classifyText(CharSequence text, int startIndex, int endIndex, LocaleList defaultLocales) {
        return classifyText(new TextClassification.Request.Builder(text, startIndex, endIndex).setDefaultLocales(defaultLocales).build());
    }

    TextLinks generateLinks(TextLinks.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return new TextLinks.Builder(request.getText().toString()).build();
    }

    int getMaxGenerateLinksTextLength() {
        return Integer.MAX_VALUE;
    }

    TextLanguage detectLanguage(TextLanguage.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return TextLanguage.EMPTY;
    }

    ConversationActions suggestConversationActions(ConversationActions.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return new ConversationActions((List<ConversationAction>) Collections.emptyList(), (String) null);
    }

    void onSelectionEvent(SelectionEvent event) {
    }

    void onTextClassifierEvent(TextClassifierEvent event) {
    }

    void destroy() {
    }

    boolean isDestroyed() {
        return false;
    }

    void dump(IndentingPrintWriter printWriter) {
    }

    public static final class EntityConfig implements Parcelable {
        public static final Parcelable.Creator<EntityConfig> CREATOR = new Parcelable.Creator<EntityConfig>() {
            public EntityConfig createFromParcel(Parcel in) {
                return new EntityConfig(in);
            }

            public EntityConfig[] newArray(int size) {
                return new EntityConfig[size];
            }
        };
        private final List<String> mExcludedTypes;
        private final List<String> mHints;
        private final boolean mIncludeTypesFromTextClassifier;
        private final List<String> mIncludedTypes;

        private EntityConfig(List<String> includedEntityTypes, List<String> excludedEntityTypes, List<String> hints, boolean includeTypesFromTextClassifier) {
            this.mIncludedTypes = (List) Preconditions.checkNotNull(includedEntityTypes);
            this.mExcludedTypes = (List) Preconditions.checkNotNull(excludedEntityTypes);
            this.mHints = (List) Preconditions.checkNotNull(hints);
            this.mIncludeTypesFromTextClassifier = includeTypesFromTextClassifier;
        }

        private EntityConfig(Parcel in) {
            this.mIncludedTypes = new ArrayList();
            in.readStringList(this.mIncludedTypes);
            this.mExcludedTypes = new ArrayList();
            in.readStringList(this.mExcludedTypes);
            List<String> tmpHints = new ArrayList<>();
            in.readStringList(tmpHints);
            this.mHints = Collections.unmodifiableList(tmpHints);
            this.mIncludeTypesFromTextClassifier = in.readByte() != 0;
        }

        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeStringList(this.mIncludedTypes);
            parcel.writeStringList(this.mExcludedTypes);
            parcel.writeStringList(this.mHints);
            parcel.writeByte(this.mIncludeTypesFromTextClassifier ? (byte) 1 : 0);
        }

        @Deprecated
        public static EntityConfig createWithHints(Collection<String> hints) {
            return new Builder().includeTypesFromTextClassifier(true).setHints(hints).build();
        }

        @Deprecated
        public static EntityConfig create(Collection<String> hints, Collection<String> includedEntityTypes, Collection<String> excludedEntityTypes) {
            return new Builder().setIncludedTypes(includedEntityTypes).setExcludedTypes(excludedEntityTypes).setHints(hints).includeTypesFromTextClassifier(true).build();
        }

        @Deprecated
        public static EntityConfig createWithExplicitEntityList(Collection<String> entityTypes) {
            return new Builder().setIncludedTypes(entityTypes).includeTypesFromTextClassifier(false).build();
        }

        public Collection<String> resolveEntityListModifications(Collection<String> entityTypes) {
            Set<String> finalSet = new HashSet<>();
            if (this.mIncludeTypesFromTextClassifier) {
                finalSet.addAll(entityTypes);
            }
            finalSet.addAll(this.mIncludedTypes);
            finalSet.removeAll(this.mExcludedTypes);
            return finalSet;
        }

        public Collection<String> getHints() {
            return this.mHints;
        }

        public boolean shouldIncludeTypesFromTextClassifier() {
            return this.mIncludeTypesFromTextClassifier;
        }

        public int describeContents() {
            return 0;
        }

        public static final class Builder {
            private Collection<String> mExcludedTypes;
            private Collection<String> mHints;
            private boolean mIncludeTypesFromTextClassifier = true;
            private Collection<String> mIncludedTypes;

            public Builder setIncludedTypes(Collection<String> includedTypes) {
                this.mIncludedTypes = includedTypes;
                return this;
            }

            public Builder setExcludedTypes(Collection<String> excludedTypes) {
                this.mExcludedTypes = excludedTypes;
                return this;
            }

            public Builder includeTypesFromTextClassifier(boolean includeTypesFromTextClassifier) {
                this.mIncludeTypesFromTextClassifier = includeTypesFromTextClassifier;
                return this;
            }

            public Builder setHints(Collection<String> hints) {
                this.mHints = hints;
                return this;
            }

            public EntityConfig build() {
                List arrayList;
                List arrayList2;
                List unmodifiableList;
                if (this.mIncludedTypes == null) {
                    arrayList = Collections.emptyList();
                } else {
                    arrayList = new ArrayList(this.mIncludedTypes);
                }
                List list = arrayList;
                if (this.mExcludedTypes == null) {
                    arrayList2 = Collections.emptyList();
                } else {
                    arrayList2 = new ArrayList(this.mExcludedTypes);
                }
                List list2 = arrayList2;
                if (this.mHints == null) {
                    unmodifiableList = Collections.emptyList();
                } else {
                    unmodifiableList = Collections.unmodifiableList(new ArrayList(this.mHints));
                }
                return new EntityConfig(list, list2, unmodifiableList, this.mIncludeTypesFromTextClassifier);
            }
        }
    }

    public static final class Utils {
        @GuardedBy({"WORD_ITERATOR"})
        private static final BreakIterator WORD_ITERATOR = BreakIterator.getWordInstance();

        static void checkArgument(CharSequence text, int startIndex, int endIndex) {
            boolean z = false;
            Preconditions.checkArgument(text != null);
            Preconditions.checkArgument(startIndex >= 0);
            Preconditions.checkArgument(endIndex <= text.length());
            if (endIndex > startIndex) {
                z = true;
            }
            Preconditions.checkArgument(z);
        }

        static void checkTextLength(CharSequence text, int maxLength) {
            Preconditions.checkArgumentInRange(text.length(), 0, maxLength, "text.length()");
        }

        public static String getSubString(String text, int start, int end, int minimumLength) {
            String substring;
            boolean z = true;
            Preconditions.checkArgument(start >= 0);
            Preconditions.checkArgument(end <= text.length());
            if (start > end) {
                z = false;
            }
            Preconditions.checkArgument(z);
            if (text.length() < minimumLength) {
                return text;
            }
            int length = end - start;
            if (length >= minimumLength) {
                return text.substring(start, end);
            }
            int iterStart = Math.max(0, Math.min(start - ((minimumLength - length) / 2), text.length() - minimumLength));
            int iterEnd = Math.min(text.length(), iterStart + minimumLength);
            synchronized (WORD_ITERATOR) {
                WORD_ITERATOR.setText(text);
                int iterStart2 = WORD_ITERATOR.isBoundary(iterStart) ? iterStart : Math.max(0, WORD_ITERATOR.preceding(iterStart));
                int iterEnd2 = WORD_ITERATOR.isBoundary(iterEnd) ? iterEnd : Math.max(iterEnd, WORD_ITERATOR.following(iterEnd));
                WORD_ITERATOR.setText("");
                substring = text.substring(iterStart2, iterEnd2);
            }
            return substring;
        }

        public static TextLinks generateLegacyLinks(TextLinks.Request request) {
            String string = request.getText().toString();
            TextLinks.Builder links = new TextLinks.Builder(string);
            Collection<String> entities = request.getEntityConfig().resolveEntityListModifications(Collections.emptyList());
            if (entities.contains("url")) {
                addLinks(links, string, "url");
            }
            if (entities.contains("phone")) {
                addLinks(links, string, "phone");
            }
            if (entities.contains("email")) {
                addLinks(links, string, "email");
            }
            return links.build();
        }

        private static void addLinks(TextLinks.Builder links, String string, String entityType) {
            Spannable spannable = new SpannableString(string);
            if (Linkify.addLinks(spannable, linkMask(entityType))) {
                for (URLSpan urlSpan : (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class)) {
                    links.addLink(spannable.getSpanStart(urlSpan), spannable.getSpanEnd(urlSpan), entityScores(entityType), urlSpan);
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x003b A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x003c A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x003d A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x003f A[RETURN] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int linkMask(java.lang.String r5) {
            /*
                int r0 = r5.hashCode()
                r1 = 116079(0x1c56f, float:1.62661E-40)
                r2 = 2
                r3 = 1
                r4 = 0
                if (r0 == r1) goto L_0x002c
                r1 = 96619420(0x5c24b9c, float:1.8271447E-35)
                if (r0 == r1) goto L_0x0022
                r1 = 106642798(0x65b3d6e, float:4.1234453E-35)
                if (r0 == r1) goto L_0x0017
                goto L_0x0037
            L_0x0017:
                java.lang.String r0 = "phone"
                boolean r0 = r5.equals(r0)
                if (r0 == 0) goto L_0x0037
                r0 = r3
                goto L_0x0038
            L_0x0022:
                java.lang.String r0 = "email"
                boolean r0 = r5.equals(r0)
                if (r0 == 0) goto L_0x0037
                r0 = r2
                goto L_0x0038
            L_0x002c:
                java.lang.String r0 = "url"
                boolean r0 = r5.equals(r0)
                if (r0 == 0) goto L_0x0037
                r0 = r4
                goto L_0x0038
            L_0x0037:
                r0 = -1
            L_0x0038:
                switch(r0) {
                    case 0: goto L_0x003f;
                    case 1: goto L_0x003d;
                    case 2: goto L_0x003c;
                    default: goto L_0x003b;
                }
            L_0x003b:
                return r4
            L_0x003c:
                return r2
            L_0x003d:
                r0 = 4
                return r0
            L_0x003f:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.textclassifier.TextClassifier.Utils.linkMask(java.lang.String):int");
        }

        private static Map<String, Float> entityScores(String entityType) {
            Map<String, Float> scores = new ArrayMap<>();
            scores.put(entityType, Float.valueOf(1.0f));
            return scores;
        }

        static void checkMainThread() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Log.w(TextClassifier.DEFAULT_LOG_TAG, "TextClassifier called on main thread");
            }
        }
    }
}
