package android.text;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.Preconditions;
import java.util.Locale;

public interface InputFilter {
    CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4);

    public static class AllCaps implements InputFilter {
        private final Locale mLocale;

        public AllCaps() {
            this.mLocale = null;
        }

        public AllCaps(Locale locale) {
            Preconditions.checkNotNull(locale);
            this.mLocale = locale;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean copySpans;
            CharSequence upper;
            CharSequence wrapper = new CharSequenceWrapper(source, start, end);
            boolean lowerOrTitleFound = false;
            int length = end - start;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                int cp = Character.codePointAt(wrapper, i);
                if (Character.isLowerCase(cp) || Character.isTitleCase(cp)) {
                    lowerOrTitleFound = true;
                } else {
                    i += Character.charCount(cp);
                }
            }
            lowerOrTitleFound = true;
            if (lowerOrTitleFound && (upper = TextUtils.toUpperCase(this.mLocale, wrapper, copySpans)) != wrapper) {
                return (copySpans = source instanceof Spanned) ? new SpannableString(upper) : upper.toString();
            }
            return null;
        }

        private static class CharSequenceWrapper implements CharSequence, Spanned {
            private final int mEnd;
            private final int mLength;
            private final CharSequence mSource;
            private final int mStart;

            CharSequenceWrapper(CharSequence source, int start, int end) {
                this.mSource = source;
                this.mStart = start;
                this.mEnd = end;
                this.mLength = end - start;
            }

            public int length() {
                return this.mLength;
            }

            public char charAt(int index) {
                if (index >= 0 && index < this.mLength) {
                    return this.mSource.charAt(this.mStart + index);
                }
                throw new IndexOutOfBoundsException();
            }

            public CharSequence subSequence(int start, int end) {
                if (start >= 0 && end >= 0 && end <= this.mLength && start <= end) {
                    return new CharSequenceWrapper(this.mSource, this.mStart + start, this.mStart + end);
                }
                throw new IndexOutOfBoundsException();
            }

            public String toString() {
                return this.mSource.subSequence(this.mStart, this.mEnd).toString();
            }

            public <T> T[] getSpans(int start, int end, Class<T> type) {
                return ((Spanned) this.mSource).getSpans(this.mStart + start, this.mStart + end, type);
            }

            public int getSpanStart(Object tag) {
                return ((Spanned) this.mSource).getSpanStart(tag) - this.mStart;
            }

            public int getSpanEnd(Object tag) {
                return ((Spanned) this.mSource).getSpanEnd(tag) - this.mStart;
            }

            public int getSpanFlags(Object tag) {
                return ((Spanned) this.mSource).getSpanFlags(tag);
            }

            public int nextSpanTransition(int start, int limit, Class type) {
                return ((Spanned) this.mSource).nextSpanTransition(this.mStart + start, this.mStart + limit, type) - this.mStart;
            }
        }
    }

    public static class LengthFilter implements InputFilter {
        @UnsupportedAppUsage
        private final int mMax;

        public LengthFilter(int max) {
            this.mMax = max;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int keep = this.mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                return "";
            }
            if (keep >= end - start) {
                return null;
            }
            int keep2 = keep + start;
            if (!Character.isHighSurrogate(source.charAt(keep2 - 1)) || keep2 - 1 != start) {
                return source.subSequence(start, keep2);
            }
            return "";
        }

        public int getMax() {
            return this.mMax;
        }
    }
}
