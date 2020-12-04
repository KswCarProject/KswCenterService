package android.view.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.AssistStructure;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.Preconditions;

@SystemApi
public final class ViewNode extends AssistStructure.ViewNode {
    private static final long FLAGS_ACCESSIBILITY_FOCUSED = 131072;
    private static final long FLAGS_ACTIVATED = 2097152;
    private static final long FLAGS_ASSIST_BLOCKED = 1024;
    private static final long FLAGS_CHECKABLE = 262144;
    private static final long FLAGS_CHECKED = 524288;
    private static final long FLAGS_CLICKABLE = 4096;
    private static final long FLAGS_CONTEXT_CLICKABLE = 16384;
    private static final long FLAGS_DISABLED = 2048;
    private static final long FLAGS_FOCUSABLE = 32768;
    private static final long FLAGS_FOCUSED = 65536;
    private static final long FLAGS_HAS_AUTOFILL_HINTS = 8589934592L;
    private static final long FLAGS_HAS_AUTOFILL_ID = 32;
    private static final long FLAGS_HAS_AUTOFILL_OPTIONS = 17179869184L;
    private static final long FLAGS_HAS_AUTOFILL_PARENT_ID = 64;
    private static final long FLAGS_HAS_AUTOFILL_TYPE = 2147483648L;
    private static final long FLAGS_HAS_AUTOFILL_VALUE = 4294967296L;
    private static final long FLAGS_HAS_CLASSNAME = 16;
    private static final long FLAGS_HAS_COMPLEX_TEXT = 2;
    private static final long FLAGS_HAS_CONTENT_DESCRIPTION = 8388608;
    private static final long FLAGS_HAS_EXTRAS = 16777216;
    private static final long FLAGS_HAS_ID = 128;
    private static final long FLAGS_HAS_INPUT_TYPE = 67108864;
    private static final long FLAGS_HAS_LARGE_COORDS = 256;
    private static final long FLAGS_HAS_LOCALE_LIST = 33554432;
    private static final long FLAGS_HAS_MAX_TEXT_EMS = 268435456;
    private static final long FLAGS_HAS_MAX_TEXT_LENGTH = 536870912;
    private static final long FLAGS_HAS_MIN_TEXT_EMS = 134217728;
    private static final long FLAGS_HAS_SCROLL = 512;
    private static final long FLAGS_HAS_TEXT = 1;
    private static final long FLAGS_HAS_TEXT_ID_ENTRY = 1073741824;
    private static final long FLAGS_LONG_CLICKABLE = 8192;
    private static final long FLAGS_OPAQUE = 4194304;
    private static final long FLAGS_SELECTED = 1048576;
    private static final long FLAGS_VISIBILITY_MASK = 12;
    /* access modifiers changed from: private */
    public static final String TAG = ViewNode.class.getSimpleName();
    /* access modifiers changed from: private */
    public String[] mAutofillHints;
    /* access modifiers changed from: private */
    public AutofillId mAutofillId;
    /* access modifiers changed from: private */
    public CharSequence[] mAutofillOptions;
    /* access modifiers changed from: private */
    public int mAutofillType;
    /* access modifiers changed from: private */
    public AutofillValue mAutofillValue;
    /* access modifiers changed from: private */
    public String mClassName;
    /* access modifiers changed from: private */
    public CharSequence mContentDescription;
    /* access modifiers changed from: private */
    public Bundle mExtras;
    /* access modifiers changed from: private */
    public long mFlags;
    /* access modifiers changed from: private */
    public int mHeight;
    /* access modifiers changed from: private */
    public int mId;
    /* access modifiers changed from: private */
    public String mIdEntry;
    /* access modifiers changed from: private */
    public String mIdPackage;
    /* access modifiers changed from: private */
    public String mIdType;
    /* access modifiers changed from: private */
    public int mInputType;
    /* access modifiers changed from: private */
    public LocaleList mLocaleList;
    /* access modifiers changed from: private */
    public int mMaxEms;
    /* access modifiers changed from: private */
    public int mMaxLength;
    /* access modifiers changed from: private */
    public int mMinEms;
    /* access modifiers changed from: private */
    public AutofillId mParentAutofillId;
    /* access modifiers changed from: private */
    public int mScrollX;
    /* access modifiers changed from: private */
    public int mScrollY;
    /* access modifiers changed from: private */
    public ViewNodeText mText;
    /* access modifiers changed from: private */
    public String mTextIdEntry;
    /* access modifiers changed from: private */
    public int mWidth;
    /* access modifiers changed from: private */
    public int mX;
    /* access modifiers changed from: private */
    public int mY;

    public ViewNode() {
        this.mId = -1;
        this.mMinEms = -1;
        this.mMaxEms = -1;
        this.mMaxLength = -1;
        this.mAutofillType = 0;
    }

    private ViewNode(long nodeFlags, Parcel parcel) {
        this.mId = -1;
        this.mMinEms = -1;
        this.mMaxEms = -1;
        this.mMaxLength = -1;
        boolean z = false;
        this.mAutofillType = 0;
        this.mFlags = nodeFlags;
        if ((32 & nodeFlags) != 0) {
            this.mAutofillId = (AutofillId) parcel.readParcelable((ClassLoader) null);
        }
        if ((64 & nodeFlags) != 0) {
            this.mParentAutofillId = (AutofillId) parcel.readParcelable((ClassLoader) null);
        }
        if ((1 & nodeFlags) != 0) {
            this.mText = new ViewNodeText(parcel, (2 & nodeFlags) == 0 ? true : z);
        }
        if ((16 & nodeFlags) != 0) {
            this.mClassName = parcel.readString();
        }
        if ((128 & nodeFlags) != 0) {
            this.mId = parcel.readInt();
            if (this.mId != -1) {
                this.mIdEntry = parcel.readString();
                if (this.mIdEntry != null) {
                    this.mIdType = parcel.readString();
                    this.mIdPackage = parcel.readString();
                }
            }
        }
        if ((256 & nodeFlags) != 0) {
            this.mX = parcel.readInt();
            this.mY = parcel.readInt();
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
        } else {
            int val = parcel.readInt();
            this.mX = val & 32767;
            this.mY = (val >> 16) & 32767;
            int val2 = parcel.readInt();
            this.mWidth = val2 & 32767;
            this.mHeight = (val2 >> 16) & 32767;
        }
        if ((512 & nodeFlags) != 0) {
            this.mScrollX = parcel.readInt();
            this.mScrollY = parcel.readInt();
        }
        if ((8388608 & nodeFlags) != 0) {
            this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if ((16777216 & nodeFlags) != 0) {
            this.mExtras = parcel.readBundle();
        }
        if ((33554432 & nodeFlags) != 0) {
            this.mLocaleList = (LocaleList) parcel.readParcelable((ClassLoader) null);
        }
        if ((67108864 & nodeFlags) != 0) {
            this.mInputType = parcel.readInt();
        }
        if ((FLAGS_HAS_MIN_TEXT_EMS & nodeFlags) != 0) {
            this.mMinEms = parcel.readInt();
        }
        if ((FLAGS_HAS_MAX_TEXT_EMS & nodeFlags) != 0) {
            this.mMaxEms = parcel.readInt();
        }
        if ((FLAGS_HAS_MAX_TEXT_LENGTH & nodeFlags) != 0) {
            this.mMaxLength = parcel.readInt();
        }
        if ((1073741824 & nodeFlags) != 0) {
            this.mTextIdEntry = parcel.readString();
        }
        if ((FLAGS_HAS_AUTOFILL_TYPE & nodeFlags) != 0) {
            this.mAutofillType = parcel.readInt();
        }
        if ((8589934592L & nodeFlags) != 0) {
            this.mAutofillHints = parcel.readStringArray();
        }
        if ((4294967296L & nodeFlags) != 0) {
            this.mAutofillValue = (AutofillValue) parcel.readParcelable((ClassLoader) null);
        }
        if ((17179869184L & nodeFlags) != 0) {
            this.mAutofillOptions = parcel.readCharSequenceArray();
        }
    }

    public AutofillId getParentAutofillId() {
        return this.mParentAutofillId;
    }

    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    public CharSequence getText() {
        if (this.mText != null) {
            return this.mText.mText;
        }
        return null;
    }

    public String getClassName() {
        return this.mClassName;
    }

    public int getId() {
        return this.mId;
    }

    public String getIdPackage() {
        return this.mIdPackage;
    }

    public String getIdType() {
        return this.mIdType;
    }

    public String getIdEntry() {
        return this.mIdEntry;
    }

    public int getLeft() {
        return this.mX;
    }

    public int getTop() {
        return this.mY;
    }

    public int getScrollX() {
        return this.mScrollX;
    }

    public int getScrollY() {
        return this.mScrollY;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean isAssistBlocked() {
        return (this.mFlags & 1024) != 0;
    }

    public boolean isEnabled() {
        return (this.mFlags & 2048) == 0;
    }

    public boolean isClickable() {
        return (this.mFlags & 4096) != 0;
    }

    public boolean isLongClickable() {
        return (this.mFlags & 8192) != 0;
    }

    public boolean isContextClickable() {
        return (this.mFlags & 16384) != 0;
    }

    public boolean isFocusable() {
        return (this.mFlags & 32768) != 0;
    }

    public boolean isFocused() {
        return (this.mFlags & 65536) != 0;
    }

    public boolean isAccessibilityFocused() {
        return (this.mFlags & 131072) != 0;
    }

    public boolean isCheckable() {
        return (this.mFlags & 262144) != 0;
    }

    public boolean isChecked() {
        return (this.mFlags & 524288) != 0;
    }

    public boolean isSelected() {
        return (this.mFlags & 1048576) != 0;
    }

    public boolean isActivated() {
        return (this.mFlags & 2097152) != 0;
    }

    public boolean isOpaque() {
        return (this.mFlags & 4194304) != 0;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getHint() {
        if (this.mText != null) {
            return this.mText.mHint;
        }
        return null;
    }

    public int getTextSelectionStart() {
        if (this.mText != null) {
            return this.mText.mTextSelectionStart;
        }
        return -1;
    }

    public int getTextSelectionEnd() {
        if (this.mText != null) {
            return this.mText.mTextSelectionEnd;
        }
        return -1;
    }

    public int getTextColor() {
        if (this.mText != null) {
            return this.mText.mTextColor;
        }
        return 1;
    }

    public int getTextBackgroundColor() {
        if (this.mText != null) {
            return this.mText.mTextBackgroundColor;
        }
        return 1;
    }

    public float getTextSize() {
        if (this.mText != null) {
            return this.mText.mTextSize;
        }
        return 0.0f;
    }

    public int getTextStyle() {
        if (this.mText != null) {
            return this.mText.mTextStyle;
        }
        return 0;
    }

    public int[] getTextLineCharOffsets() {
        if (this.mText != null) {
            return this.mText.mLineCharOffsets;
        }
        return null;
    }

    public int[] getTextLineBaselines() {
        if (this.mText != null) {
            return this.mText.mLineBaselines;
        }
        return null;
    }

    public int getVisibility() {
        return (int) (this.mFlags & FLAGS_VISIBILITY_MASK);
    }

    public int getInputType() {
        return this.mInputType;
    }

    public int getMinTextEms() {
        return this.mMinEms;
    }

    public int getMaxTextEms() {
        return this.mMaxEms;
    }

    public int getMaxTextLength() {
        return this.mMaxLength;
    }

    public String getTextIdEntry() {
        return this.mTextIdEntry;
    }

    public int getAutofillType() {
        return this.mAutofillType;
    }

    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public AutofillValue getAutofillValue() {
        return this.mAutofillValue;
    }

    public CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    public LocaleList getLocaleList() {
        return this.mLocaleList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0059, code lost:
        if ((((r0.mWidth & -32768) != 0) | ((r0.mHeight & -32768) != 0)) != false) goto L_0x005b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeSelfToParcel(android.os.Parcel r29, int r30) {
        /*
            r28 = this;
            r0 = r28
            r1 = r29
            r2 = r30
            long r3 = r0.mFlags
            android.view.autofill.AutofillId r5 = r0.mAutofillId
            r6 = 32
            if (r5 == 0) goto L_0x000f
            long r3 = r3 | r6
        L_0x000f:
            android.view.autofill.AutofillId r5 = r0.mParentAutofillId
            r8 = 64
            if (r5 == 0) goto L_0x0016
            long r3 = r3 | r8
        L_0x0016:
            android.view.contentcapture.ViewNode$ViewNodeText r5 = r0.mText
            r10 = 2
            r12 = 1
            if (r5 == 0) goto L_0x0028
            long r3 = r3 | r12
            android.view.contentcapture.ViewNode$ViewNodeText r5 = r0.mText
            boolean r5 = r5.isSimple()
            if (r5 != 0) goto L_0x0028
            long r3 = r3 | r10
        L_0x0028:
            java.lang.String r5 = r0.mClassName
            r14 = 16
            if (r5 == 0) goto L_0x002f
            long r3 = r3 | r14
        L_0x002f:
            int r5 = r0.mId
            r16 = 128(0x80, double:6.32E-322)
            r14 = -1
            if (r5 == r14) goto L_0x0038
            long r3 = r3 | r16
        L_0x0038:
            int r5 = r0.mX
            r5 = r5 & -32768(0xffffffffffff8000, float:NaN)
            r18 = 256(0x100, double:1.265E-321)
            if (r5 != 0) goto L_0x005b
            int r5 = r0.mY
            r5 = r5 & -32768(0xffffffffffff8000, float:NaN)
            if (r5 != 0) goto L_0x005b
            int r5 = r0.mWidth
            r5 = r5 & -32768(0xffffffffffff8000, float:NaN)
            if (r5 == 0) goto L_0x004e
            r5 = 1
            goto L_0x004f
        L_0x004e:
            r5 = 0
        L_0x004f:
            int r15 = r0.mHeight
            r15 = r15 & -32768(0xffffffffffff8000, float:NaN)
            if (r15 == 0) goto L_0x0057
            r15 = 1
            goto L_0x0058
        L_0x0057:
            r15 = 0
        L_0x0058:
            r5 = r5 | r15
            if (r5 == 0) goto L_0x005d
        L_0x005b:
            long r3 = r3 | r18
        L_0x005d:
            int r5 = r0.mScrollX
            r20 = 512(0x200, double:2.53E-321)
            if (r5 != 0) goto L_0x0067
            int r5 = r0.mScrollY
            if (r5 == 0) goto L_0x0069
        L_0x0067:
            long r3 = r3 | r20
        L_0x0069:
            java.lang.CharSequence r5 = r0.mContentDescription
            r22 = 8388608(0x800000, double:4.144523E-317)
            if (r5 == 0) goto L_0x0072
            long r3 = r3 | r22
        L_0x0072:
            android.os.Bundle r5 = r0.mExtras
            r24 = 16777216(0x1000000, double:8.289046E-317)
            if (r5 == 0) goto L_0x007b
            long r3 = r3 | r24
        L_0x007b:
            android.os.LocaleList r5 = r0.mLocaleList
            if (r5 == 0) goto L_0x0084
            r26 = 33554432(0x2000000, double:1.6578092E-316)
            long r3 = r3 | r26
        L_0x0084:
            int r5 = r0.mInputType
            if (r5 == 0) goto L_0x008d
            r26 = 67108864(0x4000000, double:3.31561842E-316)
            long r3 = r3 | r26
        L_0x008d:
            int r5 = r0.mMinEms
            if (r5 <= r14) goto L_0x0096
            r26 = 134217728(0x8000000, double:6.63123685E-316)
            long r3 = r3 | r26
        L_0x0096:
            int r5 = r0.mMaxEms
            if (r5 <= r14) goto L_0x009f
            r26 = 268435456(0x10000000, double:1.32624737E-315)
            long r3 = r3 | r26
        L_0x009f:
            int r5 = r0.mMaxLength
            if (r5 <= r14) goto L_0x00a8
            r26 = 536870912(0x20000000, double:2.652494739E-315)
            long r3 = r3 | r26
        L_0x00a8:
            java.lang.String r5 = r0.mTextIdEntry
            if (r5 == 0) goto L_0x00b1
            r26 = 1073741824(0x40000000, double:5.304989477E-315)
            long r3 = r3 | r26
        L_0x00b1:
            android.view.autofill.AutofillValue r5 = r0.mAutofillValue
            if (r5 == 0) goto L_0x00bc
            r26 = 4294967296(0x100000000, double:2.121995791E-314)
            long r3 = r3 | r26
        L_0x00bc:
            int r5 = r0.mAutofillType
            if (r5 == 0) goto L_0x00c7
            r26 = 2147483648(0x80000000, double:1.0609978955E-314)
            long r3 = r3 | r26
        L_0x00c7:
            java.lang.String[] r5 = r0.mAutofillHints
            if (r5 == 0) goto L_0x00d2
            r26 = 8589934592(0x200000000, double:4.243991582E-314)
            long r3 = r3 | r26
        L_0x00d2:
            java.lang.CharSequence[] r5 = r0.mAutofillOptions
            if (r5 == 0) goto L_0x00dd
            r26 = 17179869184(0x400000000, double:8.4879831639E-314)
            long r3 = r3 | r26
        L_0x00dd:
            r1.writeLong(r3)
            long r5 = r3 & r6
            r26 = 0
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x00ed
            android.view.autofill.AutofillId r5 = r0.mAutofillId
            r1.writeParcelable(r5, r2)
        L_0x00ed:
            long r5 = r3 & r8
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x00f8
            android.view.autofill.AutofillId r5 = r0.mParentAutofillId
            r1.writeParcelable(r5, r2)
        L_0x00f8:
            long r5 = r3 & r12
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x010c
            android.view.contentcapture.ViewNode$ViewNodeText r5 = r0.mText
            long r6 = r3 & r10
            int r6 = (r6 > r26 ? 1 : (r6 == r26 ? 0 : -1))
            if (r6 != 0) goto L_0x0108
            r6 = 1
            goto L_0x0109
        L_0x0108:
            r6 = 0
        L_0x0109:
            r5.writeToParcel(r1, r6)
        L_0x010c:
            r5 = 16
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x0118
            java.lang.String r5 = r0.mClassName
            r1.writeString(r5)
        L_0x0118:
            long r5 = r3 & r16
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x013a
            int r5 = r0.mId
            r1.writeInt(r5)
            int r5 = r0.mId
            if (r5 == r14) goto L_0x013a
            java.lang.String r5 = r0.mIdEntry
            r1.writeString(r5)
            java.lang.String r5 = r0.mIdEntry
            if (r5 == 0) goto L_0x013a
            java.lang.String r5 = r0.mIdType
            r1.writeString(r5)
            java.lang.String r5 = r0.mIdPackage
            r1.writeString(r5)
        L_0x013a:
            long r5 = r3 & r18
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x0155
            int r5 = r0.mX
            r1.writeInt(r5)
            int r5 = r0.mY
            r1.writeInt(r5)
            int r5 = r0.mWidth
            r1.writeInt(r5)
            int r5 = r0.mHeight
            r1.writeInt(r5)
            goto L_0x0169
        L_0x0155:
            int r5 = r0.mY
            int r5 = r5 << 16
            int r6 = r0.mX
            r5 = r5 | r6
            r1.writeInt(r5)
            int r5 = r0.mHeight
            int r5 = r5 << 16
            int r6 = r0.mWidth
            r5 = r5 | r6
            r1.writeInt(r5)
        L_0x0169:
            long r5 = r3 & r20
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x0179
            int r5 = r0.mScrollX
            r1.writeInt(r5)
            int r5 = r0.mScrollY
            r1.writeInt(r5)
        L_0x0179:
            long r5 = r3 & r22
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x0185
            java.lang.CharSequence r5 = r0.mContentDescription
            r6 = 0
            android.text.TextUtils.writeToParcel(r5, r1, r6)
        L_0x0185:
            long r5 = r3 & r24
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x0190
            android.os.Bundle r5 = r0.mExtras
            r1.writeBundle(r5)
        L_0x0190:
            r5 = 33554432(0x2000000, double:1.6578092E-316)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x019e
            android.os.LocaleList r5 = r0.mLocaleList
            r6 = 0
            r1.writeParcelable(r5, r6)
        L_0x019e:
            r5 = 67108864(0x4000000, double:3.31561842E-316)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01ab
            int r5 = r0.mInputType
            r1.writeInt(r5)
        L_0x01ab:
            r5 = 134217728(0x8000000, double:6.63123685E-316)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01b8
            int r5 = r0.mMinEms
            r1.writeInt(r5)
        L_0x01b8:
            r5 = 268435456(0x10000000, double:1.32624737E-315)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01c5
            int r5 = r0.mMaxEms
            r1.writeInt(r5)
        L_0x01c5:
            r5 = 536870912(0x20000000, double:2.652494739E-315)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01d2
            int r5 = r0.mMaxLength
            r1.writeInt(r5)
        L_0x01d2:
            r5 = 1073741824(0x40000000, double:5.304989477E-315)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01df
            java.lang.String r5 = r0.mTextIdEntry
            r1.writeString(r5)
        L_0x01df:
            r5 = 2147483648(0x80000000, double:1.0609978955E-314)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01ee
            int r5 = r0.mAutofillType
            r1.writeInt(r5)
        L_0x01ee:
            r5 = 8589934592(0x200000000, double:4.243991582E-314)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x01fd
            java.lang.String[] r5 = r0.mAutofillHints
            r1.writeStringArray(r5)
        L_0x01fd:
            r5 = 4294967296(0x100000000, double:2.121995791E-314)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x020d
            android.view.autofill.AutofillValue r5 = r0.mAutofillValue
            r6 = 0
            r1.writeParcelable(r5, r6)
        L_0x020d:
            r5 = 17179869184(0x400000000, double:8.4879831639E-314)
            long r5 = r5 & r3
            int r5 = (r5 > r26 ? 1 : (r5 == r26 ? 0 : -1))
            if (r5 == 0) goto L_0x021c
            java.lang.CharSequence[] r5 = r0.mAutofillOptions
            r1.writeCharSequenceArray(r5)
        L_0x021c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.contentcapture.ViewNode.writeSelfToParcel(android.os.Parcel, int):void");
    }

    public static void writeToParcel(Parcel parcel, ViewNode node, int flags) {
        if (node == null) {
            parcel.writeLong(0);
        } else {
            node.writeSelfToParcel(parcel, flags);
        }
    }

    public static ViewNode readFromParcel(Parcel parcel) {
        long nodeFlags = parcel.readLong();
        if (nodeFlags == 0) {
            return null;
        }
        return new ViewNode(nodeFlags, parcel);
    }

    public static final class ViewStructureImpl extends ViewStructure {
        final ViewNode mNode = new ViewNode();

        public ViewStructureImpl(View view) {
            AutofillId unused = this.mNode.mAutofillId = ((View) Preconditions.checkNotNull(view)).getAutofillId();
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                AutofillId unused2 = this.mNode.mParentAutofillId = ((View) parent).getAutofillId();
            }
        }

        public ViewStructureImpl(AutofillId parentId, long virtualId, int sessionId) {
            AutofillId unused = this.mNode.mParentAutofillId = (AutofillId) Preconditions.checkNotNull(parentId);
            AutofillId unused2 = this.mNode.mAutofillId = new AutofillId(parentId, virtualId, sessionId);
        }

        public ViewNode getNode() {
            return this.mNode;
        }

        public void setId(int id, String packageName, String typeName, String entryName) {
            int unused = this.mNode.mId = id;
            String unused2 = this.mNode.mIdPackage = packageName;
            String unused3 = this.mNode.mIdType = typeName;
            String unused4 = this.mNode.mIdEntry = entryName;
        }

        public void setDimens(int left, int top, int scrollX, int scrollY, int width, int height) {
            int unused = this.mNode.mX = left;
            int unused2 = this.mNode.mY = top;
            int unused3 = this.mNode.mScrollX = scrollX;
            int unused4 = this.mNode.mScrollY = scrollY;
            int unused5 = this.mNode.mWidth = width;
            int unused6 = this.mNode.mHeight = height;
        }

        public void setTransformation(Matrix matrix) {
            Log.w(ViewNode.TAG, "setTransformation() is not supported");
        }

        public void setElevation(float elevation) {
            Log.w(ViewNode.TAG, "setElevation() is not supported");
        }

        public void setAlpha(float alpha) {
            Log.w(ViewNode.TAG, "setAlpha() is not supported");
        }

        public void setVisibility(int visibility) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -13) | (((long) visibility) & ViewNode.FLAGS_VISIBILITY_MASK);
        }

        public void setAssistBlocked(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -1025) | (state ? 1024 : 0);
        }

        public void setEnabled(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -2049) | (state ? 0 : 2048);
        }

        public void setClickable(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -4097) | (state ? 4096 : 0);
        }

        public void setLongClickable(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -8193) | (state ? 8192 : 0);
        }

        public void setContextClickable(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -16385) | (state ? 16384 : 0);
        }

        public void setFocusable(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -32769) | (state ? 32768 : 0);
        }

        public void setFocused(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -65537) | (state ? 65536 : 0);
        }

        public void setAccessibilityFocused(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -131073) | (state ? 131072 : 0);
        }

        public void setCheckable(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -262145) | (state ? 262144 : 0);
        }

        public void setChecked(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -524289) | (state ? 524288 : 0);
        }

        public void setSelected(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -1048577) | (state ? 1048576 : 0);
        }

        public void setActivated(boolean state) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -2097153) | (state ? 2097152 : 0);
        }

        public void setOpaque(boolean opaque) {
            long unused = this.mNode.mFlags = (this.mNode.mFlags & -4194305) | (opaque ? 4194304 : 0);
        }

        public void setClassName(String className) {
            String unused = this.mNode.mClassName = className;
        }

        public void setContentDescription(CharSequence contentDescription) {
            CharSequence unused = this.mNode.mContentDescription = contentDescription;
        }

        public void setText(CharSequence text) {
            ViewNodeText t = getNodeText();
            t.mText = TextUtils.trimNoCopySpans(text);
            t.mTextSelectionEnd = -1;
            t.mTextSelectionStart = -1;
        }

        public void setText(CharSequence text, int selectionStart, int selectionEnd) {
            ViewNodeText t = getNodeText();
            t.mText = TextUtils.trimNoCopySpans(text);
            t.mTextSelectionStart = selectionStart;
            t.mTextSelectionEnd = selectionEnd;
        }

        public void setTextStyle(float size, int fgColor, int bgColor, int style) {
            ViewNodeText t = getNodeText();
            t.mTextColor = fgColor;
            t.mTextBackgroundColor = bgColor;
            t.mTextSize = size;
            t.mTextStyle = style;
        }

        public void setTextLines(int[] charOffsets, int[] baselines) {
            ViewNodeText t = getNodeText();
            t.mLineCharOffsets = charOffsets;
            t.mLineBaselines = baselines;
        }

        public void setTextIdEntry(String entryName) {
            String unused = this.mNode.mTextIdEntry = (String) Preconditions.checkNotNull(entryName);
        }

        public void setHint(CharSequence hint) {
            getNodeText().mHint = hint != null ? hint.toString() : null;
        }

        public CharSequence getText() {
            return this.mNode.getText();
        }

        public int getTextSelectionStart() {
            return this.mNode.getTextSelectionStart();
        }

        public int getTextSelectionEnd() {
            return this.mNode.getTextSelectionEnd();
        }

        public CharSequence getHint() {
            return this.mNode.getHint();
        }

        public Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            Bundle unused = this.mNode.mExtras = new Bundle();
            return this.mNode.mExtras;
        }

        public boolean hasExtras() {
            return this.mNode.mExtras != null;
        }

        public void setChildCount(int num) {
            Log.w(ViewNode.TAG, "setChildCount() is not supported");
        }

        public int addChildCount(int num) {
            Log.w(ViewNode.TAG, "addChildCount() is not supported");
            return 0;
        }

        public int getChildCount() {
            Log.w(ViewNode.TAG, "getChildCount() is not supported");
            return 0;
        }

        public ViewStructure newChild(int index) {
            Log.w(ViewNode.TAG, "newChild() is not supported");
            return null;
        }

        public ViewStructure asyncNewChild(int index) {
            Log.w(ViewNode.TAG, "asyncNewChild() is not supported");
            return null;
        }

        public AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        public void setAutofillId(AutofillId id) {
            AutofillId unused = this.mNode.mAutofillId = (AutofillId) Preconditions.checkNotNull(id);
        }

        public void setAutofillId(AutofillId parentId, int virtualId) {
            AutofillId unused = this.mNode.mParentAutofillId = (AutofillId) Preconditions.checkNotNull(parentId);
            AutofillId unused2 = this.mNode.mAutofillId = new AutofillId(parentId, virtualId);
        }

        public void setAutofillType(int type) {
            int unused = this.mNode.mAutofillType = type;
        }

        public void setAutofillHints(String[] hints) {
            String[] unused = this.mNode.mAutofillHints = hints;
        }

        public void setAutofillValue(AutofillValue value) {
            AutofillValue unused = this.mNode.mAutofillValue = value;
        }

        public void setAutofillOptions(CharSequence[] options) {
            CharSequence[] unused = this.mNode.mAutofillOptions = options;
        }

        public void setInputType(int inputType) {
            int unused = this.mNode.mInputType = inputType;
        }

        public void setMinTextEms(int minEms) {
            int unused = this.mNode.mMinEms = minEms;
        }

        public void setMaxTextEms(int maxEms) {
            int unused = this.mNode.mMaxEms = maxEms;
        }

        public void setMaxTextLength(int maxLength) {
            int unused = this.mNode.mMaxLength = maxLength;
        }

        public void setDataIsSensitive(boolean sensitive) {
            Log.w(ViewNode.TAG, "setDataIsSensitive() is not supported");
        }

        public void asyncCommit() {
            Log.w(ViewNode.TAG, "asyncCommit() is not supported");
        }

        public Rect getTempRect() {
            Log.w(ViewNode.TAG, "getTempRect() is not supported");
            return null;
        }

        public void setWebDomain(String domain) {
            Log.w(ViewNode.TAG, "setWebDomain() is not supported");
        }

        public void setLocaleList(LocaleList localeList) {
            LocaleList unused = this.mNode.mLocaleList = localeList;
        }

        public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String tagName) {
            Log.w(ViewNode.TAG, "newHtmlInfoBuilder() is not supported");
            return null;
        }

        public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
            Log.w(ViewNode.TAG, "setHtmlInfo() is not supported");
        }

        private ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            ViewNodeText unused = this.mNode.mText = new ViewNodeText();
            return this.mNode.mText;
        }
    }

    static final class ViewNodeText {
        String mHint;
        int[] mLineBaselines;
        int[] mLineCharOffsets;
        CharSequence mText;
        int mTextBackgroundColor = 1;
        int mTextColor = 1;
        int mTextSelectionEnd;
        int mTextSelectionStart;
        float mTextSize;
        int mTextStyle;

        ViewNodeText() {
        }

        /* access modifiers changed from: package-private */
        public boolean isSimple() {
            return this.mTextBackgroundColor == 1 && this.mTextSelectionStart == 0 && this.mTextSelectionEnd == 0 && this.mLineCharOffsets == null && this.mLineBaselines == null && this.mHint == null;
        }

        ViewNodeText(Parcel in, boolean simple) {
            this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.mTextSize = in.readFloat();
            this.mTextStyle = in.readInt();
            this.mTextColor = in.readInt();
            if (!simple) {
                this.mTextBackgroundColor = in.readInt();
                this.mTextSelectionStart = in.readInt();
                this.mTextSelectionEnd = in.readInt();
                this.mLineCharOffsets = in.createIntArray();
                this.mLineBaselines = in.createIntArray();
                this.mHint = in.readString();
            }
        }

        /* access modifiers changed from: package-private */
        public void writeToParcel(Parcel out, boolean simple) {
            TextUtils.writeToParcel(this.mText, out, 0);
            out.writeFloat(this.mTextSize);
            out.writeInt(this.mTextStyle);
            out.writeInt(this.mTextColor);
            if (!simple) {
                out.writeInt(this.mTextBackgroundColor);
                out.writeInt(this.mTextSelectionStart);
                out.writeInt(this.mTextSelectionEnd);
                out.writeIntArray(this.mLineCharOffsets);
                out.writeIntArray(this.mLineBaselines);
                out.writeString(this.mHint);
            }
        }
    }
}
