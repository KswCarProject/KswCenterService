package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Annotation;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineHeightSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;

final class StringBlock {
    private static final String TAG = "AssetManager";
    private static final boolean localLOGV = false;
    private final long mNative;
    @GuardedBy({"this"})
    private boolean mOpen = true;
    private final boolean mOwnsNative;
    private SparseArray<CharSequence> mSparseStrings;
    private CharSequence[] mStrings;
    StyleIDs mStyleIDs = null;
    private final boolean mUseSparse;

    private static native long nativeCreate(byte[] bArr, int i, int i2);

    private static native void nativeDestroy(long j);

    private static native int nativeGetSize(long j);

    private static native String nativeGetString(long j, int i);

    private static native int[] nativeGetStyle(long j, int i);

    public StringBlock(byte[] data, boolean useSparse) {
        this.mNative = nativeCreate(data, 0, data.length);
        this.mUseSparse = useSparse;
        this.mOwnsNative = true;
    }

    public StringBlock(byte[] data, int offset, int size, boolean useSparse) {
        this.mNative = nativeCreate(data, offset, size);
        this.mUseSparse = useSparse;
        this.mOwnsNative = true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0175, code lost:
        return r1;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.CharSequence get(int r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.CharSequence[] r0 = r7.mStrings     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x000e
            java.lang.CharSequence[] r0 = r7.mStrings     // Catch:{ all -> 0x0176 }
            r0 = r0[r8]     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x000d
            monitor-exit(r7)     // Catch:{ all -> 0x0176 }
            return r0
        L_0x000d:
            goto L_0x0039
        L_0x000e:
            android.util.SparseArray<java.lang.CharSequence> r0 = r7.mSparseStrings     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x001f
            android.util.SparseArray<java.lang.CharSequence> r0 = r7.mSparseStrings     // Catch:{ all -> 0x0176 }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x001e
            monitor-exit(r7)     // Catch:{ all -> 0x0176 }
            return r0
        L_0x001e:
            goto L_0x0039
        L_0x001f:
            long r0 = r7.mNative     // Catch:{ all -> 0x0176 }
            int r0 = nativeGetSize(r0)     // Catch:{ all -> 0x0176 }
            boolean r1 = r7.mUseSparse     // Catch:{ all -> 0x0176 }
            if (r1 == 0) goto L_0x0035
            r1 = 250(0xfa, float:3.5E-43)
            if (r0 <= r1) goto L_0x0035
            android.util.SparseArray r1 = new android.util.SparseArray     // Catch:{ all -> 0x0176 }
            r1.<init>()     // Catch:{ all -> 0x0176 }
            r7.mSparseStrings = r1     // Catch:{ all -> 0x0176 }
            goto L_0x0039
        L_0x0035:
            java.lang.CharSequence[] r1 = new java.lang.CharSequence[r0]     // Catch:{ all -> 0x0176 }
            r7.mStrings = r1     // Catch:{ all -> 0x0176 }
        L_0x0039:
            long r0 = r7.mNative     // Catch:{ all -> 0x0176 }
            java.lang.String r0 = nativeGetString(r0, r8)     // Catch:{ all -> 0x0176 }
            r1 = r0
            long r2 = r7.mNative     // Catch:{ all -> 0x0176 }
            int[] r2 = nativeGetStyle(r2, r8)     // Catch:{ all -> 0x0176 }
            if (r2 == 0) goto L_0x0166
            android.content.res.StringBlock$StyleIDs r3 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            if (r3 != 0) goto L_0x0053
            android.content.res.StringBlock$StyleIDs r3 = new android.content.res.StringBlock$StyleIDs     // Catch:{ all -> 0x0176 }
            r3.<init>()     // Catch:{ all -> 0x0176 }
            r7.mStyleIDs = r3     // Catch:{ all -> 0x0176 }
        L_0x0053:
            r3 = 0
        L_0x0054:
            int r4 = r2.length     // Catch:{ all -> 0x0176 }
            if (r3 >= r4) goto L_0x015f
            r4 = r2[r3]     // Catch:{ all -> 0x0176 }
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.boldId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.italicId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.underlineId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.ttId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.bigId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.smallId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.subId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.supId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.strikeId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.listItemId     // Catch:{ all -> 0x0176 }
            if (r4 == r5) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r5 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int r5 = r5.marqueeId     // Catch:{ all -> 0x0176 }
            if (r4 != r5) goto L_0x00b3
            goto L_0x015b
        L_0x00b3:
            long r5 = r7.mNative     // Catch:{ all -> 0x0176 }
            java.lang.String r5 = nativeGetString(r5, r4)     // Catch:{ all -> 0x0176 }
            java.lang.String r6 = "b"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x00c8
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.boldId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x00c8:
            java.lang.String r6 = "i"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x00d7
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.italicId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x00d7:
            java.lang.String r6 = "u"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x00e7
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.underlineId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x00e7:
            java.lang.String r6 = "tt"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x00f6
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.ttId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x00f6:
            java.lang.String r6 = "big"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x0104
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.bigId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x0104:
            java.lang.String r6 = "small"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x0113
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.smallId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x0113:
            java.lang.String r6 = "sup"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x0122
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.supId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x0122:
            java.lang.String r6 = "sub"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x0131
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.subId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x0131:
            java.lang.String r6 = "strike"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x0140
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.strikeId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x0140:
            java.lang.String r6 = "li"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x014e
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.listItemId = r4     // Catch:{ all -> 0x0176 }
            goto L_0x015b
        L_0x014e:
            java.lang.String r6 = "marquee"
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x0176 }
            if (r6 == 0) goto L_0x015b
            android.content.res.StringBlock$StyleIDs r6 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            int unused = r6.marqueeId = r4     // Catch:{ all -> 0x0176 }
        L_0x015b:
            int r3 = r3 + 3
            goto L_0x0054
        L_0x015f:
            android.content.res.StringBlock$StyleIDs r3 = r7.mStyleIDs     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r3 = r7.applyStyles(r0, r2, r3)     // Catch:{ all -> 0x0176 }
            r1 = r3
        L_0x0166:
            java.lang.CharSequence[] r3 = r7.mStrings     // Catch:{ all -> 0x0176 }
            if (r3 == 0) goto L_0x016f
            java.lang.CharSequence[] r3 = r7.mStrings     // Catch:{ all -> 0x0176 }
            r3[r8] = r1     // Catch:{ all -> 0x0176 }
            goto L_0x0174
        L_0x016f:
            android.util.SparseArray<java.lang.CharSequence> r3 = r7.mSparseStrings     // Catch:{ all -> 0x0176 }
            r3.put(r8, r1)     // Catch:{ all -> 0x0176 }
        L_0x0174:
            monitor-exit(r7)     // Catch:{ all -> 0x0176 }
            return r1
        L_0x0176:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0176 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.StringBlock.get(int):java.lang.CharSequence");
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            close();
        }
    }

    public void close() throws Throwable {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                if (this.mOwnsNative) {
                    nativeDestroy(this.mNative);
                }
            }
        }
    }

    static final class StyleIDs {
        /* access modifiers changed from: private */
        public int bigId = -1;
        /* access modifiers changed from: private */
        public int boldId = -1;
        /* access modifiers changed from: private */
        public int italicId = -1;
        /* access modifiers changed from: private */
        public int listItemId = -1;
        /* access modifiers changed from: private */
        public int marqueeId = -1;
        /* access modifiers changed from: private */
        public int smallId = -1;
        /* access modifiers changed from: private */
        public int strikeId = -1;
        /* access modifiers changed from: private */
        public int subId = -1;
        /* access modifiers changed from: private */
        public int supId = -1;
        /* access modifiers changed from: private */
        public int ttId = -1;
        /* access modifiers changed from: private */
        public int underlineId = -1;

        StyleIDs() {
        }
    }

    private CharSequence applyStyles(String str, int[] style, StyleIDs ids) {
        String str2 = str;
        int[] iArr = style;
        if (iArr.length == 0) {
            return str2;
        }
        SpannableString buffer = new SpannableString(str2);
        boolean z = false;
        int i = 0;
        while (i < iArr.length) {
            int type = iArr[i];
            if (type == ids.boldId) {
                buffer.setSpan(new StyleSpan(1), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.italicId) {
                buffer.setSpan(new StyleSpan(2), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.underlineId) {
                buffer.setSpan(new UnderlineSpan(), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.ttId) {
                buffer.setSpan(new TypefaceSpan("monospace"), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.bigId) {
                buffer.setSpan(new RelativeSizeSpan(1.25f), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.smallId) {
                buffer.setSpan(new RelativeSizeSpan(0.8f), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.subId) {
                buffer.setSpan(new SubscriptSpan(), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.supId) {
                buffer.setSpan(new SuperscriptSpan(), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.strikeId) {
                buffer.setSpan(new StrikethroughSpan(), iArr[i + 1], iArr[i + 2] + 1, 33);
            } else if (type == ids.listItemId) {
                addParagraphSpan(buffer, new BulletSpan(10), iArr[i + 1], iArr[i + 2] + 1);
            } else if (type == ids.marqueeId) {
                buffer.setSpan(TextUtils.TruncateAt.MARQUEE, iArr[i + 1], iArr[i + 2] + 1, 18);
            } else {
                String tag = nativeGetString(this.mNative, type);
                if (tag.startsWith("font;")) {
                    String sub = subtag(tag, ";height=");
                    if (sub != null) {
                        addParagraphSpan(buffer, new Height(Integer.parseInt(sub)), iArr[i + 1], iArr[i + 2] + 1);
                    }
                    String sub2 = subtag(tag, ";size=");
                    if (sub2 != null) {
                        buffer.setSpan(new AbsoluteSizeSpan(Integer.parseInt(sub2), true), iArr[i + 1], iArr[i + 2] + 1, 33);
                    }
                    String sub3 = subtag(tag, ";fgcolor=");
                    if (sub3 != null) {
                        buffer.setSpan(getColor(sub3, true), iArr[i + 1], iArr[i + 2] + 1, 33);
                    }
                    String sub4 = subtag(tag, ";color=");
                    if (sub4 != null) {
                        buffer.setSpan(getColor(sub4, true), iArr[i + 1], iArr[i + 2] + 1, 33);
                    }
                    String sub5 = subtag(tag, ";bgcolor=");
                    if (sub5 != null) {
                        buffer.setSpan(getColor(sub5, z), iArr[i + 1], iArr[i + 2] + 1, 33);
                    }
                    String sub6 = subtag(tag, ";face=");
                    if (sub6 != null) {
                        buffer.setSpan(new TypefaceSpan(sub6), iArr[i + 1], iArr[i + 2] + 1, 33);
                    }
                } else if (tag.startsWith("a;")) {
                    String sub7 = subtag(tag, ";href=");
                    if (sub7 != null) {
                        buffer.setSpan(new URLSpan(sub7), iArr[i + 1], iArr[i + 2] + 1, 33);
                    }
                } else if (tag.startsWith("annotation;")) {
                    int len = tag.length();
                    int i2 = 59;
                    int t = tag.indexOf(59);
                    while (t < len) {
                        int eq = tag.indexOf(61, t);
                        if (eq < 0) {
                            break;
                        }
                        int next = tag.indexOf(i2, eq);
                        if (next < 0) {
                            next = len;
                        }
                        buffer.setSpan(new Annotation(tag.substring(t + 1, eq), tag.substring(eq + 1, next)), iArr[i + 1], iArr[i + 2] + 1, 33);
                        t = next;
                        String str3 = str;
                        i2 = 59;
                    }
                }
                i += 3;
                String str4 = str;
                z = false;
            }
            i += 3;
            String str42 = str;
            z = false;
        }
        return new SpannedString(buffer);
    }

    private static CharacterStyle getColor(String color, boolean foreground) {
        int c = -16777216;
        if (!TextUtils.isEmpty(color)) {
            if (color.startsWith("@")) {
                Resources res = Resources.getSystem();
                int colorRes = res.getIdentifier(color.substring(1), "color", "android");
                if (colorRes != 0) {
                    ColorStateList colors = res.getColorStateList(colorRes, (Resources.Theme) null);
                    if (foreground) {
                        return new TextAppearanceSpan((String) null, 0, 0, colors, (ColorStateList) null);
                    }
                    c = colors.getDefaultColor();
                }
            } else {
                try {
                    c = Color.parseColor(color);
                } catch (IllegalArgumentException e) {
                    c = -16777216;
                }
            }
        }
        if (foreground) {
            return new ForegroundColorSpan(c);
        }
        return new BackgroundColorSpan(c);
    }

    private static void addParagraphSpan(Spannable buffer, Object what, int start, int end) {
        int len = buffer.length();
        if (start != 0 && start != len && buffer.charAt(start - 1) != 10) {
            do {
                start--;
                if (start <= 0) {
                    break;
                }
            } while (buffer.charAt(start - 1) == 10);
        }
        if (end != 0 && end != len && buffer.charAt(end - 1) != 10) {
            do {
                end++;
                if (end >= len) {
                    break;
                }
            } while (buffer.charAt(end - 1) == 10);
        }
        buffer.setSpan(what, start, end, 51);
    }

    private static String subtag(String full, String attribute) {
        int start = full.indexOf(attribute);
        if (start < 0) {
            return null;
        }
        int start2 = start + attribute.length();
        int end = full.indexOf(59, start2);
        if (end < 0) {
            return full.substring(start2);
        }
        return full.substring(start2, end);
    }

    private static class Height implements LineHeightSpan.WithDensity {
        private static float sProportion = 0.0f;
        private int mSize;

        public Height(int size) {
            this.mSize = size;
        }

        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
            chooseHeight(text, start, end, spanstartv, v, fm, (TextPaint) null);
        }

        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm, TextPaint paint) {
            int size = this.mSize;
            if (paint != null) {
                size = (int) (((float) size) * paint.density);
            }
            if (fm.bottom - fm.top < size) {
                fm.top = fm.bottom - size;
                fm.ascent -= size;
                return;
            }
            if (sProportion == 0.0f) {
                Paint p = new Paint();
                p.setTextSize(100.0f);
                Rect r = new Rect();
                p.getTextBounds("ABCDEFG", 0, 7, r);
                sProportion = ((float) r.top) / p.ascent();
            }
            int need = (int) Math.ceil((double) (((float) (-fm.top)) * sProportion));
            if (size - fm.descent >= need) {
                fm.top = fm.bottom - size;
                fm.ascent = fm.descent - size;
            } else if (size >= need) {
                int i = -need;
                fm.ascent = i;
                fm.top = i;
                int i2 = fm.top + size;
                fm.descent = i2;
                fm.bottom = i2;
            } else {
                int i3 = -size;
                fm.ascent = i3;
                fm.top = i3;
                fm.descent = 0;
                fm.bottom = 0;
            }
        }
    }

    @UnsupportedAppUsage
    StringBlock(long obj, boolean useSparse) {
        this.mNative = obj;
        this.mUseSparse = useSparse;
        this.mOwnsNative = false;
    }
}
