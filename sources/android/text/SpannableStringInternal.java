package android.text;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.WifiEnterpriseConfig;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import libcore.util.EmptyArray;

abstract class SpannableStringInternal {
    @UnsupportedAppUsage
    private static final int COLUMNS = 3;
    @UnsupportedAppUsage
    static final Object[] EMPTY = new Object[0];
    @UnsupportedAppUsage
    private static final int END = 1;
    @UnsupportedAppUsage
    private static final int FLAGS = 2;
    @UnsupportedAppUsage
    private static final int START = 0;
    @UnsupportedAppUsage
    private int mSpanCount;
    @UnsupportedAppUsage
    private int[] mSpanData;
    @UnsupportedAppUsage
    private Object[] mSpans;
    @UnsupportedAppUsage
    private String mText;

    SpannableStringInternal(CharSequence source, int start, int end, boolean ignoreNoCopySpan) {
        if (start == 0 && end == source.length()) {
            this.mText = source.toString();
        } else {
            this.mText = source.toString().substring(start, end);
        }
        this.mSpans = EmptyArray.OBJECT;
        this.mSpanData = EmptyArray.INT;
        if (!(source instanceof Spanned)) {
            return;
        }
        if (source instanceof SpannableStringInternal) {
            copySpans((SpannableStringInternal) source, start, end, ignoreNoCopySpan);
        } else {
            copySpans((Spanned) source, start, end, ignoreNoCopySpan);
        }
    }

    @UnsupportedAppUsage
    SpannableStringInternal(CharSequence source, int start, int end) {
        this(source, start, end, false);
    }

    private void copySpans(Spanned src, int start, int end, boolean ignoreNoCopySpan) {
        Object[] spans = src.getSpans(start, end, Object.class);
        for (int i = 0; i < spans.length; i++) {
            if (!ignoreNoCopySpan || !(spans[i] instanceof NoCopySpan)) {
                int st = src.getSpanStart(spans[i]);
                int en = src.getSpanEnd(spans[i]);
                int fl = src.getSpanFlags(spans[i]);
                if (st < start) {
                    st = start;
                }
                if (en > end) {
                    en = end;
                }
                setSpan(spans[i], st - start, en - start, fl, false);
            }
        }
    }

    private void copySpans(SpannableStringInternal src, int start, int end, boolean ignoreNoCopySpan) {
        SpannableStringInternal spannableStringInternal = src;
        int i = start;
        int i2 = end;
        int[] srcData = spannableStringInternal.mSpanData;
        Object[] srcSpans = spannableStringInternal.mSpans;
        int limit = spannableStringInternal.mSpanCount;
        boolean hasNoCopySpan = false;
        int count = 0;
        for (int i3 = 0; i3 < limit; i3++) {
            if (!isOutOfCopyRange(i, i2, srcData[(i3 * 3) + 0], srcData[(i3 * 3) + 1])) {
                if (srcSpans[i3] instanceof NoCopySpan) {
                    hasNoCopySpan = true;
                    if (ignoreNoCopySpan) {
                    }
                }
                count++;
            }
        }
        if (count != 0) {
            if (!hasNoCopySpan && i == 0 && i2 == src.length()) {
                this.mSpans = ArrayUtils.newUnpaddedObjectArray(spannableStringInternal.mSpans.length);
                this.mSpanData = new int[spannableStringInternal.mSpanData.length];
                this.mSpanCount = spannableStringInternal.mSpanCount;
                System.arraycopy(spannableStringInternal.mSpans, 0, this.mSpans, 0, spannableStringInternal.mSpans.length);
                System.arraycopy(spannableStringInternal.mSpanData, 0, this.mSpanData, 0, this.mSpanData.length);
                return;
            }
            this.mSpanCount = count;
            this.mSpans = ArrayUtils.newUnpaddedObjectArray(this.mSpanCount);
            this.mSpanData = new int[(this.mSpans.length * 3)];
            int j = 0;
            for (int i4 = 0; i4 < limit; i4++) {
                int spanStart = srcData[(i4 * 3) + 0];
                int spanEnd = srcData[(i4 * 3) + 1];
                if (!isOutOfCopyRange(i, i2, spanStart, spanEnd) && (!ignoreNoCopySpan || !(srcSpans[i4] instanceof NoCopySpan))) {
                    if (spanStart < i) {
                        spanStart = start;
                    }
                    if (spanEnd > i2) {
                        spanEnd = end;
                    }
                    this.mSpans[j] = srcSpans[i4];
                    this.mSpanData[(j * 3) + 0] = spanStart - i;
                    this.mSpanData[(j * 3) + 1] = spanEnd - i;
                    this.mSpanData[(j * 3) + 2] = srcData[(i4 * 3) + 2];
                    j++;
                }
            }
        }
    }

    @UnsupportedAppUsage
    private final boolean isOutOfCopyRange(int start, int end, int spanStart, int spanEnd) {
        if (spanStart > end || spanEnd < start) {
            return true;
        }
        if (spanStart == spanEnd || start == end) {
            return false;
        }
        if (spanStart == end || spanEnd == start) {
            return true;
        }
        return false;
    }

    public final int length() {
        return this.mText.length();
    }

    public final char charAt(int i) {
        return this.mText.charAt(i);
    }

    public final String toString() {
        return this.mText;
    }

    public final void getChars(int start, int end, char[] dest, int off) {
        this.mText.getChars(start, end, dest, off);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setSpan(Object what, int start, int end, int flags) {
        setSpan(what, start, end, flags, true);
    }

    @UnsupportedAppUsage
    private boolean isIndexFollowsNextLine(int index) {
        return (index == 0 || index == length() || charAt(index + -1) == 10) ? false : true;
    }

    @UnsupportedAppUsage
    private void setSpan(Object what, int start, int end, int flags, boolean enforceParagraph) {
        Object obj = what;
        int i = start;
        int i2 = end;
        int nstart = start;
        int nend = end;
        checkRange("setSpan", i, i2);
        if ((flags & 51) == 51) {
            if (isIndexFollowsNextLine(i)) {
                if (enforceParagraph) {
                    throw new RuntimeException("PARAGRAPH span must start at paragraph boundary (" + i + " follows " + charAt(i - 1) + ")");
                }
                return;
            } else if (isIndexFollowsNextLine(i2)) {
                if (enforceParagraph) {
                    throw new RuntimeException("PARAGRAPH span must end at paragraph boundary (" + i2 + " follows " + charAt(i2 - 1) + ")");
                }
                return;
            }
        }
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= count) {
                if (this.mSpanCount + 1 >= this.mSpans.length) {
                    Object[] newtags = ArrayUtils.newUnpaddedObjectArray(GrowingArrayUtils.growSize(this.mSpanCount));
                    int[] newdata = new int[(newtags.length * 3)];
                    System.arraycopy(this.mSpans, 0, newtags, 0, this.mSpanCount);
                    System.arraycopy(this.mSpanData, 0, newdata, 0, this.mSpanCount * 3);
                    this.mSpans = newtags;
                    this.mSpanData = newdata;
                }
                this.mSpans[this.mSpanCount] = obj;
                this.mSpanData[(this.mSpanCount * 3) + 0] = i;
                this.mSpanData[(this.mSpanCount * 3) + 1] = i2;
                this.mSpanData[(this.mSpanCount * 3) + 2] = flags;
                this.mSpanCount++;
                if (this instanceof Spannable) {
                    sendSpanAdded(obj, nstart, nend);
                    return;
                }
                return;
            } else if (spans[i4] == obj) {
                int ostart = data[(i4 * 3) + 0];
                int oend = data[(i4 * 3) + 1];
                data[(i4 * 3) + 0] = i;
                data[(i4 * 3) + 1] = i2;
                data[(i4 * 3) + 2] = flags;
                int i5 = i4;
                int[] iArr = data;
                sendSpanChanged(what, ostart, oend, nstart, nend);
                return;
            } else {
                int[] iArr2 = data;
                i3 = i4 + 1;
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void removeSpan(Object what) {
        removeSpan(what, 0);
    }

    public void removeSpan(Object what, int flags) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                int ostart = data[(i * 3) + 0];
                int oend = data[(i * 3) + 1];
                int c = count - (i + 1);
                System.arraycopy(spans, i + 1, spans, i, c);
                System.arraycopy(data, (i + 1) * 3, data, i * 3, c * 3);
                this.mSpanCount--;
                if ((flags & 512) == 0) {
                    sendSpanRemoved(what, ostart, oend);
                    return;
                }
                return;
            }
        }
    }

    @UnsupportedAppUsage
    public int getSpanStart(Object what) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                return data[(i * 3) + 0];
            }
        }
        return -1;
    }

    @UnsupportedAppUsage
    public int getSpanEnd(Object what) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                return data[(i * 3) + 1];
            }
        }
        return -1;
    }

    @UnsupportedAppUsage
    public int getSpanFlags(Object what) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                return data[(i * 3) + 2];
            }
        }
        return 0;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: T[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T[] getSpans(int r19, int r20, java.lang.Class<T> r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = 0
            int r5 = r0.mSpanCount
            java.lang.Object[] r6 = r0.mSpans
            int[] r7 = r0.mSpanData
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = r9
            r9 = r8
            r8 = r4
            r4 = r10
        L_0x0016:
            r12 = 1
            if (r4 >= r5) goto L_0x0098
            int r13 = r4 * 3
            int r13 = r13 + r10
            r13 = r7[r13]
            int r14 = r4 * 3
            int r14 = r14 + r12
            r14 = r7[r14]
            if (r13 <= r2) goto L_0x0027
            goto L_0x0091
        L_0x0027:
            if (r14 >= r1) goto L_0x002b
            goto L_0x0091
        L_0x002b:
            if (r13 == r14) goto L_0x0035
            if (r1 == r2) goto L_0x0035
            if (r13 != r2) goto L_0x0032
            goto L_0x0091
        L_0x0032:
            if (r14 != r1) goto L_0x0035
            goto L_0x0091
        L_0x0035:
            if (r3 == 0) goto L_0x0044
            java.lang.Class<java.lang.Object> r15 = java.lang.Object.class
            if (r3 == r15) goto L_0x0044
            r15 = r6[r4]
            boolean r15 = r3.isInstance(r15)
            if (r15 != 0) goto L_0x0044
            goto L_0x0091
        L_0x0044:
            if (r8 != 0) goto L_0x004b
            r11 = r6[r4]
            int r8 = r8 + 1
            goto L_0x0091
        L_0x004b:
            if (r8 != r12) goto L_0x0059
            int r15 = r5 - r4
            int r15 = r15 + r12
            java.lang.Object r12 = java.lang.reflect.Array.newInstance(r3, r15)
            r9 = r12
            java.lang.Object[] r9 = (java.lang.Object[]) r9
            r9[r10] = r11
        L_0x0059:
            int r12 = r4 * 3
            int r12 = r12 + 2
            r12 = r7[r12]
            r15 = 16711680(0xff0000, float:2.3418052E-38)
            r12 = r12 & r15
            if (r12 == 0) goto L_0x008a
            r16 = r10
        L_0x0066:
            r17 = r16
            r10 = r17
            if (r10 >= r8) goto L_0x007c
            r1 = r9[r10]
            int r1 = r0.getSpanFlags(r1)
            r1 = r1 & r15
            if (r12 <= r1) goto L_0x0076
            goto L_0x007c
        L_0x0076:
            int r16 = r10 + 1
            r1 = r19
            r10 = 0
            goto L_0x0066
        L_0x007c:
            int r1 = r10 + 1
            int r15 = r8 - r10
            java.lang.System.arraycopy(r9, r10, r9, r1, r15)
            r1 = r6[r4]
            r9[r10] = r1
            int r8 = r8 + 1
            goto L_0x0091
        L_0x008a:
            int r1 = r8 + 1
            r10 = r6[r4]
            r9[r8] = r10
            r8 = r1
        L_0x0091:
            int r4 = r4 + 1
            r1 = r19
            r10 = 0
            goto L_0x0016
        L_0x0098:
            if (r8 != 0) goto L_0x009f
            java.lang.Object[] r1 = com.android.internal.util.ArrayUtils.emptyArray(r21)
            return r1
        L_0x009f:
            if (r8 != r12) goto L_0x00ab
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r3, r12)
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            r4 = 0
            r1[r4] = r11
            return r1
        L_0x00ab:
            int r1 = r9.length
            if (r8 != r1) goto L_0x00af
            return r9
        L_0x00af:
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r3, r8)
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            r4 = 0
            java.lang.System.arraycopy(r9, r4, r1, r4, r8)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringInternal.getSpans(int, int, java.lang.Class):java.lang.Object[]");
    }

    @UnsupportedAppUsage
    public int nextSpanTransition(int start, int limit, Class kind) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        if (kind == null) {
            kind = Object.class;
        }
        int limit2 = limit;
        for (int i = 0; i < count; i++) {
            int st = data[(i * 3) + 0];
            int en = data[(i * 3) + 1];
            if (st > start && st < limit2 && kind.isInstance(spans[i])) {
                limit2 = st;
            }
            if (en > start && en < limit2 && kind.isInstance(spans[i])) {
                limit2 = en;
            }
        }
        return limit2;
    }

    @UnsupportedAppUsage
    private void sendSpanAdded(Object what, int start, int end) {
        for (SpanWatcher onSpanAdded : (SpanWatcher[]) getSpans(start, end, SpanWatcher.class)) {
            onSpanAdded.onSpanAdded((Spannable) this, what, start, end);
        }
    }

    @UnsupportedAppUsage
    private void sendSpanRemoved(Object what, int start, int end) {
        for (SpanWatcher onSpanRemoved : (SpanWatcher[]) getSpans(start, end, SpanWatcher.class)) {
            onSpanRemoved.onSpanRemoved((Spannable) this, what, start, end);
        }
    }

    @UnsupportedAppUsage
    private void sendSpanChanged(Object what, int s, int e, int st, int en) {
        for (SpanWatcher onSpanChanged : (SpanWatcher[]) getSpans(Math.min(s, st), Math.max(e, en), SpanWatcher.class)) {
            onSpanChanged.onSpanChanged((Spannable) this, what, s, e, st, en);
        }
    }

    @UnsupportedAppUsage
    private static String region(int start, int end) {
        return "(" + start + " ... " + end + ")";
    }

    @UnsupportedAppUsage
    private void checkRange(String operation, int start, int end) {
        if (end >= start) {
            int len = length();
            if (start > len || end > len) {
                throw new IndexOutOfBoundsException(operation + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + region(start, end) + " ends beyond length " + len);
            } else if (start < 0 || end < 0) {
                throw new IndexOutOfBoundsException(operation + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + region(start, end) + " starts before 0");
            }
        } else {
            throw new IndexOutOfBoundsException(operation + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + region(start, end) + " has end before start");
        }
    }

    public boolean equals(Object o) {
        if ((o instanceof Spanned) && toString().equals(o.toString())) {
            Spanned other = (Spanned) o;
            Object[] otherSpans = other.getSpans(0, other.length(), Object.class);
            Object[] thisSpans = getSpans(0, length(), Object.class);
            if (this.mSpanCount == otherSpans.length) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    Object thisSpan = thisSpans[i];
                    Object otherSpan = otherSpans[i];
                    if (thisSpan == this) {
                        if (other != otherSpan || getSpanStart(thisSpan) != other.getSpanStart(otherSpan) || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan) || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                            return false;
                        }
                    } else if (!thisSpan.equals(otherSpan) || getSpanStart(thisSpan) != other.getSpanStart(otherSpan) || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan) || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = (toString().hashCode() * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            Object span = this.mSpans[i];
            if (span != this) {
                hash = (hash * 31) + span.hashCode();
            }
            hash = (((((hash * 31) + getSpanStart(span)) * 31) + getSpanEnd(span)) * 31) + getSpanFlags(span);
        }
        return hash;
    }

    @UnsupportedAppUsage
    private void copySpans(Spanned src, int start, int end) {
        copySpans(src, start, end, false);
    }

    @UnsupportedAppUsage
    private void copySpans(SpannableStringInternal src, int start, int end) {
        copySpans(src, start, end, false);
    }
}
