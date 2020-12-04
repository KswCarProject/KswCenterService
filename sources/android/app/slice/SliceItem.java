package android.app.slice;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public final class SliceItem implements Parcelable {
    public static final Parcelable.Creator<SliceItem> CREATOR = new Parcelable.Creator<SliceItem>() {
        public SliceItem createFromParcel(Parcel in) {
            return new SliceItem(in);
        }

        public SliceItem[] newArray(int size) {
            return new SliceItem[size];
        }
    };
    public static final String FORMAT_ACTION = "action";
    public static final String FORMAT_BUNDLE = "bundle";
    public static final String FORMAT_IMAGE = "image";
    public static final String FORMAT_INT = "int";
    public static final String FORMAT_LONG = "long";
    public static final String FORMAT_REMOTE_INPUT = "input";
    public static final String FORMAT_SLICE = "slice";
    public static final String FORMAT_TEXT = "text";
    @Deprecated
    public static final String FORMAT_TIMESTAMP = "long";
    private static final String TAG = "SliceItem";
    private final String mFormat;
    protected String[] mHints;
    private final Object mObj;
    private final String mSubType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SliceType {
    }

    public SliceItem(Object obj, String format, String subType, List<String> hints) {
        this(obj, format, subType, (String[]) hints.toArray(new String[hints.size()]));
    }

    public SliceItem(Object obj, String format, String subType, String[] hints) {
        this.mHints = hints;
        this.mFormat = format;
        this.mSubType = subType;
        this.mObj = obj;
    }

    public SliceItem(PendingIntent intent, Slice slice, String format, String subType, String[] hints) {
        this((Object) new Pair(intent, slice), format, subType, hints);
    }

    public List<String> getHints() {
        return Arrays.asList(this.mHints);
    }

    public String getFormat() {
        return this.mFormat;
    }

    public String getSubType() {
        return this.mSubType;
    }

    public CharSequence getText() {
        return (CharSequence) this.mObj;
    }

    public Bundle getBundle() {
        return (Bundle) this.mObj;
    }

    public Icon getIcon() {
        return (Icon) this.mObj;
    }

    public PendingIntent getAction() {
        return (PendingIntent) ((Pair) this.mObj).first;
    }

    public RemoteViews getRemoteView() {
        return (RemoteViews) this.mObj;
    }

    public RemoteInput getRemoteInput() {
        return (RemoteInput) this.mObj;
    }

    public int getInt() {
        return ((Integer) this.mObj).intValue();
    }

    public Slice getSlice() {
        if ("action".equals(getFormat())) {
            return (Slice) ((Pair) this.mObj).second;
        }
        return (Slice) this.mObj;
    }

    public long getLong() {
        return ((Long) this.mObj).longValue();
    }

    @Deprecated
    public long getTimestamp() {
        return ((Long) this.mObj).longValue();
    }

    public boolean hasHint(String hint) {
        return ArrayUtils.contains((T[]) this.mHints, hint);
    }

    public SliceItem(Parcel in) {
        this.mHints = in.readStringArray();
        this.mFormat = in.readString();
        this.mSubType = in.readString();
        this.mObj = readObj(this.mFormat, in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.mHints);
        dest.writeString(this.mFormat);
        dest.writeString(this.mSubType);
        writeObj(dest, flags, this.mObj, this.mFormat);
    }

    public boolean hasHints(String[] hints) {
        if (hints == null) {
            return true;
        }
        for (String hint : hints) {
            if (!TextUtils.isEmpty(hint) && !ArrayUtils.contains((T[]) this.mHints, hint)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyHints(String[] hints) {
        if (hints == null) {
            return false;
        }
        for (String hint : hints) {
            if (ArrayUtils.contains((T[]) this.mHints, hint)) {
                return true;
            }
        }
        return false;
    }

    private static String getBaseType(String type) {
        int index = type.indexOf(47);
        if (index >= 0) {
            return type.substring(0, index);
        }
        return type;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void writeObj(android.os.Parcel r2, int r3, java.lang.Object r4, java.lang.String r5) {
        /*
            java.lang.String r0 = getBaseType(r5)
            int r1 = r0.hashCode()
            switch(r1) {
                case -1422950858: goto L_0x0054;
                case -1377881982: goto L_0x004a;
                case 104431: goto L_0x0040;
                case 3327612: goto L_0x0036;
                case 3556653: goto L_0x002b;
                case 100313435: goto L_0x0021;
                case 100358090: goto L_0x0017;
                case 109526418: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x005e
        L_0x000c:
            java.lang.String r1 = "slice"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 0
            goto L_0x005f
        L_0x0017:
            java.lang.String r1 = "input"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 2
            goto L_0x005f
        L_0x0021:
            java.lang.String r1 = "image"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 1
            goto L_0x005f
        L_0x002b:
            java.lang.String r1 = "text"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 5
            goto L_0x005f
        L_0x0036:
            java.lang.String r1 = "long"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 7
            goto L_0x005f
        L_0x0040:
            java.lang.String r1 = "int"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 6
            goto L_0x005f
        L_0x004a:
            java.lang.String r1 = "bundle"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 3
            goto L_0x005f
        L_0x0054:
            java.lang.String r1 = "action"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 4
            goto L_0x005f
        L_0x005e:
            r0 = -1
        L_0x005f:
            switch(r0) {
                case 0: goto L_0x0095;
                case 1: goto L_0x0095;
                case 2: goto L_0x0095;
                case 3: goto L_0x0095;
                case 4: goto L_0x0080;
                case 5: goto L_0x0079;
                case 6: goto L_0x006e;
                case 7: goto L_0x0063;
                default: goto L_0x0062;
            }
        L_0x0062:
            goto L_0x009c
        L_0x0063:
            r0 = r4
            java.lang.Long r0 = (java.lang.Long) r0
            long r0 = r0.longValue()
            r2.writeLong(r0)
            goto L_0x009c
        L_0x006e:
            r0 = r4
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r2.writeInt(r0)
            goto L_0x009c
        L_0x0079:
            r0 = r4
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            android.text.TextUtils.writeToParcel(r0, r2, r3)
            goto L_0x009c
        L_0x0080:
            r0 = r4
            android.util.Pair r0 = (android.util.Pair) r0
            F r0 = r0.first
            android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            r0.writeToParcel(r2, r3)
            r0 = r4
            android.util.Pair r0 = (android.util.Pair) r0
            S r0 = r0.second
            android.app.slice.Slice r0 = (android.app.slice.Slice) r0
            r0.writeToParcel(r2, r3)
            goto L_0x009c
        L_0x0095:
            r0 = r4
            android.os.Parcelable r0 = (android.os.Parcelable) r0
            r0.writeToParcel(r2, r3)
        L_0x009c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceItem.writeObj(android.os.Parcel, int, java.lang.Object, java.lang.String):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object readObj(java.lang.String r3, android.os.Parcel r4) {
        /*
            java.lang.String r0 = getBaseType(r3)
            int r1 = r0.hashCode()
            switch(r1) {
                case -1422950858: goto L_0x0054;
                case -1377881982: goto L_0x004a;
                case 104431: goto L_0x0040;
                case 3327612: goto L_0x0036;
                case 3556653: goto L_0x002b;
                case 100313435: goto L_0x0021;
                case 100358090: goto L_0x0017;
                case 109526418: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x005e
        L_0x000c:
            java.lang.String r1 = "slice"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 0
            goto L_0x005f
        L_0x0017:
            java.lang.String r1 = "input"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 6
            goto L_0x005f
        L_0x0021:
            java.lang.String r1 = "image"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 2
            goto L_0x005f
        L_0x002b:
            java.lang.String r1 = "text"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 1
            goto L_0x005f
        L_0x0036:
            java.lang.String r1 = "long"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 5
            goto L_0x005f
        L_0x0040:
            java.lang.String r1 = "int"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 4
            goto L_0x005f
        L_0x004a:
            java.lang.String r1 = "bundle"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 7
            goto L_0x005f
        L_0x0054:
            java.lang.String r1 = "action"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005e
            r0 = 3
            goto L_0x005f
        L_0x005e:
            r0 = -1
        L_0x005f:
            switch(r0) {
                case 0: goto L_0x00bd;
                case 1: goto L_0x00b6;
                case 2: goto L_0x00af;
                case 3: goto L_0x0099;
                case 4: goto L_0x0090;
                case 5: goto L_0x0087;
                case 6: goto L_0x0080;
                case 7: goto L_0x0079;
                default: goto L_0x0062;
            }
        L_0x0062:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unsupported type "
            r1.append(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0079:
            android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
            java.lang.Object r0 = r0.createFromParcel(r4)
            return r0
        L_0x0080:
            android.os.Parcelable$Creator<android.app.RemoteInput> r0 = android.app.RemoteInput.CREATOR
            java.lang.Object r0 = r0.createFromParcel(r4)
            return r0
        L_0x0087:
            long r0 = r4.readLong()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            return r0
        L_0x0090:
            int r0 = r4.readInt()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            return r0
        L_0x0099:
            android.util.Pair r0 = new android.util.Pair
            android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
            java.lang.Object r1 = r1.createFromParcel(r4)
            android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            android.os.Parcelable$Creator<android.app.slice.Slice> r2 = android.app.slice.Slice.CREATOR
            java.lang.Object r2 = r2.createFromParcel(r4)
            android.app.slice.Slice r2 = (android.app.slice.Slice) r2
            r0.<init>(r1, r2)
            return r0
        L_0x00af:
            android.os.Parcelable$Creator<android.graphics.drawable.Icon> r0 = android.graphics.drawable.Icon.CREATOR
            java.lang.Object r0 = r0.createFromParcel(r4)
            return r0
        L_0x00b6:
            android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
            java.lang.Object r0 = r0.createFromParcel(r4)
            return r0
        L_0x00bd:
            android.os.Parcelable$Creator<android.app.slice.Slice> r0 = android.app.slice.Slice.CREATOR
            java.lang.Object r0 = r0.createFromParcel(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceItem.readObj(java.lang.String, android.os.Parcel):java.lang.Object");
    }
}
