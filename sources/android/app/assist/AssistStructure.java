package android.app.assist;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.TrafficStats;
import android.net.Uri;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PooledStringReader;
import android.os.PooledStringWriter;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.TimedRemoteCaller;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssistStructure implements Parcelable {
    public static final Parcelable.Creator<AssistStructure> CREATOR = new Parcelable.Creator<AssistStructure>() {
        public AssistStructure createFromParcel(Parcel in) {
            return new AssistStructure(in);
        }

        public AssistStructure[] newArray(int size) {
            return new AssistStructure[size];
        }
    };
    private static final boolean DEBUG_PARCEL = false;
    private static final boolean DEBUG_PARCEL_CHILDREN = false;
    private static final boolean DEBUG_PARCEL_TREE = false;
    private static final String DESCRIPTOR = "android.app.AssistStructure";
    private static final String TAG = "AssistStructure";
    private static final int TRANSACTION_XFER = 2;
    private static final int VALIDATE_VIEW_TOKEN = 572662306;
    private static final int VALIDATE_WINDOW_TOKEN = 286331153;
    /* access modifiers changed from: private */
    public long mAcquisitionEndTime;
    /* access modifiers changed from: private */
    public long mAcquisitionStartTime;
    private ComponentName mActivityComponent;
    /* access modifiers changed from: private */
    public int mAutofillFlags;
    /* access modifiers changed from: private */
    public int mFlags;
    private boolean mHaveData;
    private boolean mIsHomeActivity;
    /* access modifiers changed from: private */
    public final ArrayList<ViewNodeBuilder> mPendingAsyncChildren;
    private IBinder mReceiveChannel;
    /* access modifiers changed from: private */
    public boolean mSanitizeOnWrite;
    private SendChannel mSendChannel;
    private int mTaskId;
    /* access modifiers changed from: private */
    public Rect mTmpRect;
    /* access modifiers changed from: private */
    public final ArrayList<WindowNode> mWindowNodes;

    public static class AutofillOverlay {
        public boolean focused;
        public AutofillValue value;
    }

    public void setAcquisitionStartTime(long acquisitionStartTime) {
        this.mAcquisitionStartTime = acquisitionStartTime;
    }

    public void setAcquisitionEndTime(long acquisitionEndTime) {
        this.mAcquisitionEndTime = acquisitionEndTime;
    }

    public void setHomeActivity(boolean isHomeActivity) {
        this.mIsHomeActivity = isHomeActivity;
    }

    public long getAcquisitionStartTime() {
        ensureData();
        return this.mAcquisitionStartTime;
    }

    public long getAcquisitionEndTime() {
        ensureData();
        return this.mAcquisitionEndTime;
    }

    static final class SendChannel extends Binder {
        volatile AssistStructure mAssistStructure;

        SendChannel(AssistStructure as) {
            this.mAssistStructure = as;
        }

        /* access modifiers changed from: protected */
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 2) {
                return super.onTransact(code, data, reply, flags);
            }
            AssistStructure as = this.mAssistStructure;
            if (as == null) {
                return true;
            }
            data.enforceInterface(AssistStructure.DESCRIPTOR);
            IBinder token = data.readStrongBinder();
            if (token == null) {
                new ParcelTransferWriter(as, reply).writeToParcel(as, reply);
                return true;
            } else if (token instanceof ParcelTransferWriter) {
                ((ParcelTransferWriter) token).writeToParcel(as, reply);
                return true;
            } else {
                Log.w(AssistStructure.TAG, "Caller supplied bad token type: " + token);
                return true;
            }
        }
    }

    static final class ViewStackEntry {
        int curChild;
        ViewNode node;
        int numChildren;

        ViewStackEntry() {
        }
    }

    static final class ParcelTransferWriter extends Binder {
        ViewStackEntry mCurViewStackEntry;
        int mCurViewStackPos;
        int mCurWindow;
        int mNumWindows;
        int mNumWrittenViews;
        int mNumWrittenWindows;
        final boolean mSanitizeOnWrite;
        final float[] mTmpMatrix = new float[9];
        final ArrayList<ViewStackEntry> mViewStack = new ArrayList<>();
        final boolean mWriteStructure;

        ParcelTransferWriter(AssistStructure as, Parcel out) {
            this.mSanitizeOnWrite = as.mSanitizeOnWrite;
            this.mWriteStructure = as.waitForReady();
            out.writeInt(as.mFlags);
            out.writeInt(as.mAutofillFlags);
            out.writeLong(as.mAcquisitionStartTime);
            out.writeLong(as.mAcquisitionEndTime);
            this.mNumWindows = as.mWindowNodes.size();
            if (!this.mWriteStructure || this.mNumWindows <= 0) {
                out.writeInt(0);
            } else {
                out.writeInt(this.mNumWindows);
            }
        }

        /* access modifiers changed from: package-private */
        public void writeToParcel(AssistStructure as, Parcel out) {
            int start = out.dataPosition();
            this.mNumWrittenWindows = 0;
            this.mNumWrittenViews = 0;
            boolean more = writeToParcelInner(as, out);
            StringBuilder sb = new StringBuilder();
            sb.append("Flattened ");
            sb.append(more ? Slice.HINT_PARTIAL : "final");
            sb.append(" assist data: ");
            sb.append(out.dataPosition() - start);
            sb.append(" bytes, containing ");
            sb.append(this.mNumWrittenWindows);
            sb.append(" windows, ");
            sb.append(this.mNumWrittenViews);
            sb.append(" views");
            Log.i(AssistStructure.TAG, sb.toString());
        }

        /* access modifiers changed from: package-private */
        public boolean writeToParcelInner(AssistStructure as, Parcel out) {
            if (this.mNumWindows == 0) {
                return false;
            }
            PooledStringWriter pwriter = new PooledStringWriter(out);
            while (writeNextEntryToParcel(as, out, pwriter)) {
                if (out.dataSize() > 65536) {
                    out.writeInt(0);
                    out.writeStrongBinder(this);
                    pwriter.finish();
                    return true;
                }
            }
            pwriter.finish();
            this.mViewStack.clear();
            return false;
        }

        /* access modifiers changed from: package-private */
        public void pushViewStackEntry(ViewNode node, int pos) {
            ViewStackEntry entry;
            if (pos >= this.mViewStack.size()) {
                entry = new ViewStackEntry();
                this.mViewStack.add(entry);
            } else {
                entry = this.mViewStack.get(pos);
            }
            entry.node = node;
            entry.numChildren = node.getChildCount();
            entry.curChild = 0;
            this.mCurViewStackEntry = entry;
        }

        /* access modifiers changed from: package-private */
        public void writeView(ViewNode child, Parcel out, PooledStringWriter pwriter, int levelAdj) {
            out.writeInt(AssistStructure.VALIDATE_VIEW_TOKEN);
            int flags = child.writeSelfToParcel(out, pwriter, this.mSanitizeOnWrite, this.mTmpMatrix);
            this.mNumWrittenViews++;
            if ((1048576 & flags) != 0) {
                out.writeInt(child.mChildren.length);
                int pos = this.mCurViewStackPos + 1;
                this.mCurViewStackPos = pos;
                pushViewStackEntry(child, pos);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean writeNextEntryToParcel(AssistStructure as, Parcel out, PooledStringWriter pwriter) {
            if (this.mCurViewStackEntry == null) {
                int pos = this.mCurWindow;
                if (pos >= this.mNumWindows) {
                    return false;
                }
                WindowNode win = (WindowNode) as.mWindowNodes.get(pos);
                this.mCurWindow++;
                out.writeInt(AssistStructure.VALIDATE_WINDOW_TOKEN);
                win.writeSelfToParcel(out, pwriter, this.mTmpMatrix);
                this.mNumWrittenWindows++;
                ViewNode root = win.mRoot;
                this.mCurViewStackPos = 0;
                writeView(root, out, pwriter, 0);
                return true;
            } else if (this.mCurViewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
                ViewNode child = this.mCurViewStackEntry.node.mChildren[this.mCurViewStackEntry.curChild];
                this.mCurViewStackEntry.curChild++;
                writeView(child, out, pwriter, 1);
                return true;
            } else {
                while (true) {
                    int pos2 = this.mCurViewStackPos - 1;
                    this.mCurViewStackPos = pos2;
                    if (pos2 >= 0) {
                        this.mCurViewStackEntry = this.mViewStack.get(pos2);
                        if (this.mCurViewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
                            break;
                        }
                    } else {
                        this.mCurViewStackEntry = null;
                        break;
                    }
                }
                return true;
            }
        }
    }

    final class ParcelTransferReader {
        private final IBinder mChannel;
        private Parcel mCurParcel;
        int mNumReadViews;
        int mNumReadWindows;
        PooledStringReader mStringReader;
        final float[] mTmpMatrix = new float[9];
        private IBinder mTransferToken;

        ParcelTransferReader(IBinder channel) {
            this.mChannel = channel;
        }

        /* access modifiers changed from: package-private */
        public void go() {
            fetchData();
            int unused = AssistStructure.this.mFlags = this.mCurParcel.readInt();
            int unused2 = AssistStructure.this.mAutofillFlags = this.mCurParcel.readInt();
            long unused3 = AssistStructure.this.mAcquisitionStartTime = this.mCurParcel.readLong();
            long unused4 = AssistStructure.this.mAcquisitionEndTime = this.mCurParcel.readLong();
            int N = this.mCurParcel.readInt();
            if (N > 0) {
                this.mStringReader = new PooledStringReader(this.mCurParcel);
                for (int i = 0; i < N; i++) {
                    AssistStructure.this.mWindowNodes.add(new WindowNode(this));
                }
            }
            this.mCurParcel.recycle();
            this.mCurParcel = null;
        }

        /* access modifiers changed from: package-private */
        public Parcel readParcel(int validateToken, int level) {
            int token = this.mCurParcel.readInt();
            if (token == 0) {
                this.mTransferToken = this.mCurParcel.readStrongBinder();
                if (this.mTransferToken != null) {
                    fetchData();
                    this.mStringReader = new PooledStringReader(this.mCurParcel);
                    this.mCurParcel.readInt();
                    return this.mCurParcel;
                }
                throw new IllegalStateException("Reached end of partial data without transfer token");
            } else if (token == validateToken) {
                return this.mCurParcel;
            } else {
                throw new BadParcelableException("Got token " + Integer.toHexString(token) + ", expected token " + Integer.toHexString(validateToken));
            }
        }

        private void fetchData() {
            Parcel data = Parcel.obtain();
            try {
                data.writeInterfaceToken(AssistStructure.DESCRIPTOR);
                data.writeStrongBinder(this.mTransferToken);
                if (this.mCurParcel != null) {
                    this.mCurParcel.recycle();
                }
                this.mCurParcel = Parcel.obtain();
                this.mChannel.transact(2, data, this.mCurParcel, 0);
                data.recycle();
                this.mNumReadViews = 0;
                this.mNumReadWindows = 0;
            } catch (RemoteException e) {
                Log.w(AssistStructure.TAG, "Failure reading AssistStructure data", e);
                throw new IllegalStateException("Failure reading AssistStructure data: " + e);
            } catch (Throwable e2) {
                data.recycle();
                throw e2;
            }
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
        public void writeToParcel(Parcel out, boolean simple, boolean writeSensitive) {
            TextUtils.writeToParcel(writeSensitive ? this.mText : "", out, 0);
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

    public static class WindowNode {
        final int mDisplayId;
        final int mHeight;
        final ViewNode mRoot;
        final CharSequence mTitle;
        final int mWidth;
        final int mX;
        final int mY;

        WindowNode(AssistStructure assist, ViewRootImpl root, boolean forAutoFill, int flags) {
            View view = root.getView();
            Rect rect = new Rect();
            view.getBoundsOnScreen(rect);
            this.mX = rect.left - view.getLeft();
            this.mY = rect.top - view.getTop();
            this.mWidth = rect.width();
            this.mHeight = rect.height();
            this.mTitle = root.getTitle();
            this.mDisplayId = root.getDisplayId();
            this.mRoot = new ViewNode();
            ViewNodeBuilder builder = new ViewNodeBuilder(assist, this.mRoot, false);
            if ((root.getWindowFlags() & 8192) != 0) {
                if (forAutoFill) {
                    view.onProvideAutofillStructure(builder, resolveViewAutofillFlags(view.getContext(), flags));
                } else {
                    view.onProvideStructure(builder);
                    builder.setAssistBlocked(true);
                    return;
                }
            }
            if (forAutoFill) {
                view.dispatchProvideAutofillStructure(builder, resolveViewAutofillFlags(view.getContext(), flags));
            } else {
                view.dispatchProvideStructure(builder);
            }
        }

        WindowNode(ParcelTransferReader reader) {
            Parcel in = reader.readParcel(AssistStructure.VALIDATE_WINDOW_TOKEN, 0);
            reader.mNumReadWindows++;
            this.mX = in.readInt();
            this.mY = in.readInt();
            this.mWidth = in.readInt();
            this.mHeight = in.readInt();
            this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.mDisplayId = in.readInt();
            this.mRoot = new ViewNode(reader, 0);
        }

        /* access modifiers changed from: package-private */
        public int resolveViewAutofillFlags(Context context, int fillRequestFlags) {
            if ((fillRequestFlags & 1) != 0 || context.isAutofillCompatibilityEnabled()) {
                return 1;
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public void writeSelfToParcel(Parcel out, PooledStringWriter pwriter, float[] tmpMatrix) {
            out.writeInt(this.mX);
            out.writeInt(this.mY);
            out.writeInt(this.mWidth);
            out.writeInt(this.mHeight);
            TextUtils.writeToParcel(this.mTitle, out, 0);
            out.writeInt(this.mDisplayId);
        }

        public int getLeft() {
            return this.mX;
        }

        public int getTop() {
            return this.mY;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public CharSequence getTitle() {
            return this.mTitle;
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public ViewNode getRootViewNode() {
            return this.mRoot;
        }
    }

    public static class ViewNode {
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_HINTS = 16;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_OPTIONS = 32;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_SESSION_ID = 2048;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_TYPE = 8;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VALUE = 4;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIEW_ID = 1;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIRTUAL_VIEW_ID = 2;
        static final int AUTOFILL_FLAGS_HAS_HTML_INFO = 64;
        static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_EMS = 512;
        static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_LENGTH = 1024;
        static final int AUTOFILL_FLAGS_HAS_MIN_TEXT_EMS = 256;
        static final int AUTOFILL_FLAGS_HAS_TEXT_ID_ENTRY = 128;
        static final int FLAGS_ACCESSIBILITY_FOCUSED = 4096;
        static final int FLAGS_ACTIVATED = 8192;
        static final int FLAGS_ALL_CONTROL = -1048576;
        static final int FLAGS_ASSIST_BLOCKED = 128;
        static final int FLAGS_CHECKABLE = 256;
        static final int FLAGS_CHECKED = 512;
        static final int FLAGS_CLICKABLE = 1024;
        static final int FLAGS_CONTEXT_CLICKABLE = 16384;
        static final int FLAGS_DISABLED = 1;
        static final int FLAGS_FOCUSABLE = 16;
        static final int FLAGS_FOCUSED = 32;
        static final int FLAGS_HAS_ALPHA = 536870912;
        static final int FLAGS_HAS_CHILDREN = 1048576;
        static final int FLAGS_HAS_COMPLEX_TEXT = 8388608;
        static final int FLAGS_HAS_CONTENT_DESCRIPTION = 33554432;
        static final int FLAGS_HAS_ELEVATION = 268435456;
        static final int FLAGS_HAS_EXTRAS = 4194304;
        static final int FLAGS_HAS_ID = 2097152;
        static final int FLAGS_HAS_INPUT_TYPE = 262144;
        static final int FLAGS_HAS_LARGE_COORDS = 67108864;
        static final int FLAGS_HAS_LOCALE_LIST = 65536;
        static final int FLAGS_HAS_MATRIX = 1073741824;
        static final int FLAGS_HAS_SCROLL = 134217728;
        static final int FLAGS_HAS_TEXT = 16777216;
        static final int FLAGS_HAS_URL = 524288;
        static final int FLAGS_LONG_CLICKABLE = 2048;
        static final int FLAGS_OPAQUE = 32768;
        static final int FLAGS_SELECTED = 64;
        static final int FLAGS_VISIBILITY_MASK = 12;
        public static final int TEXT_COLOR_UNDEFINED = 1;
        public static final int TEXT_STYLE_BOLD = 1;
        public static final int TEXT_STYLE_ITALIC = 2;
        public static final int TEXT_STYLE_STRIKE_THRU = 8;
        public static final int TEXT_STYLE_UNDERLINE = 4;
        float mAlpha;
        int mAutofillFlags;
        String[] mAutofillHints;
        AutofillId mAutofillId;
        CharSequence[] mAutofillOptions;
        AutofillOverlay mAutofillOverlay;
        int mAutofillType;
        AutofillValue mAutofillValue;
        ViewNode[] mChildren;
        String mClassName;
        CharSequence mContentDescription;
        float mElevation;
        Bundle mExtras;
        int mFlags;
        int mHeight;
        ViewStructure.HtmlInfo mHtmlInfo;
        int mId;
        String mIdEntry;
        String mIdPackage;
        String mIdType;
        int mImportantForAutofill;
        int mInputType;
        LocaleList mLocaleList;
        Matrix mMatrix;
        int mMaxEms;
        int mMaxLength;
        int mMinEms;
        boolean mSanitized;
        int mScrollX;
        int mScrollY;
        ViewNodeText mText;
        String mTextIdEntry;
        String mWebDomain;
        String mWebScheme;
        int mWidth;
        int mX;
        int mY;

        @SystemApi
        public ViewNode() {
            this.mId = -1;
            this.mAutofillType = 0;
            this.mMinEms = -1;
            this.mMaxEms = -1;
            this.mMaxLength = -1;
            this.mAlpha = 1.0f;
        }

        ViewNode(ParcelTransferReader reader, int nestingLevel) {
            this.mId = -1;
            this.mAutofillType = 0;
            this.mMinEms = -1;
            this.mMaxEms = -1;
            this.mMaxLength = -1;
            this.mAlpha = 1.0f;
            Parcel in = reader.readParcel(AssistStructure.VALIDATE_VIEW_TOKEN, nestingLevel);
            boolean z = true;
            reader.mNumReadViews++;
            PooledStringReader preader = reader.mStringReader;
            this.mClassName = preader.readString();
            this.mFlags = in.readInt();
            int flags = this.mFlags;
            this.mAutofillFlags = in.readInt();
            int autofillFlags = this.mAutofillFlags;
            if ((2097152 & flags) != 0) {
                this.mId = in.readInt();
                if (this.mId != -1) {
                    this.mIdEntry = preader.readString();
                    if (this.mIdEntry != null) {
                        this.mIdType = preader.readString();
                        this.mIdPackage = preader.readString();
                    }
                }
            }
            if (autofillFlags != 0) {
                this.mSanitized = in.readInt() == 1;
                this.mImportantForAutofill = in.readInt();
                if ((autofillFlags & 1) != 0) {
                    int autofillViewId = in.readInt();
                    if ((autofillFlags & 2) != 0) {
                        this.mAutofillId = new AutofillId(autofillViewId, in.readInt());
                    } else {
                        this.mAutofillId = new AutofillId(autofillViewId);
                    }
                    if ((autofillFlags & 2048) != 0) {
                        this.mAutofillId.setSessionId(in.readInt());
                    }
                }
                if ((autofillFlags & 8) != 0) {
                    this.mAutofillType = in.readInt();
                }
                if ((autofillFlags & 16) != 0) {
                    this.mAutofillHints = in.readStringArray();
                }
                if ((autofillFlags & 4) != 0) {
                    this.mAutofillValue = (AutofillValue) in.readParcelable((ClassLoader) null);
                }
                if ((autofillFlags & 32) != 0) {
                    this.mAutofillOptions = in.readCharSequenceArray();
                }
                if ((autofillFlags & 64) != 0) {
                    this.mHtmlInfo = (ViewStructure.HtmlInfo) in.readParcelable((ClassLoader) null);
                }
                if ((autofillFlags & 256) != 0) {
                    this.mMinEms = in.readInt();
                }
                if ((autofillFlags & 512) != 0) {
                    this.mMaxEms = in.readInt();
                }
                if ((autofillFlags & 1024) != 0) {
                    this.mMaxLength = in.readInt();
                }
                if ((autofillFlags & 128) != 0) {
                    this.mTextIdEntry = preader.readString();
                }
            }
            if ((67108864 & flags) != 0) {
                this.mX = in.readInt();
                this.mY = in.readInt();
                this.mWidth = in.readInt();
                this.mHeight = in.readInt();
            } else {
                int val = in.readInt();
                this.mX = val & 32767;
                this.mY = (val >> 16) & 32767;
                int val2 = in.readInt();
                this.mWidth = val2 & 32767;
                this.mHeight = (val2 >> 16) & 32767;
            }
            if ((134217728 & flags) != 0) {
                this.mScrollX = in.readInt();
                this.mScrollY = in.readInt();
            }
            if ((1073741824 & flags) != 0) {
                this.mMatrix = new Matrix();
                in.readFloatArray(reader.mTmpMatrix);
                this.mMatrix.setValues(reader.mTmpMatrix);
            }
            if ((268435456 & flags) != 0) {
                this.mElevation = in.readFloat();
            }
            if ((536870912 & flags) != 0) {
                this.mAlpha = in.readFloat();
            }
            if ((33554432 & flags) != 0) {
                this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            }
            if ((16777216 & flags) != 0) {
                this.mText = new ViewNodeText(in, (8388608 & flags) != 0 ? false : z);
            }
            if ((262144 & flags) != 0) {
                this.mInputType = in.readInt();
            }
            if ((524288 & flags) != 0) {
                this.mWebScheme = in.readString();
                this.mWebDomain = in.readString();
            }
            if ((65536 & flags) != 0) {
                this.mLocaleList = (LocaleList) in.readParcelable((ClassLoader) null);
            }
            if ((4194304 & flags) != 0) {
                this.mExtras = in.readBundle();
            }
            if ((1048576 & flags) != 0) {
                int NCHILDREN = in.readInt();
                this.mChildren = new ViewNode[NCHILDREN];
                for (int i = 0; i < NCHILDREN; i++) {
                    this.mChildren[i] = new ViewNode(reader, nestingLevel + 1);
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x003a, code lost:
            if ((((r0.mWidth & -32768) != 0) | ((r0.mHeight & -32768) != 0)) != false) goto L_0x003c;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int writeSelfToParcel(android.os.Parcel r21, android.os.PooledStringWriter r22, boolean r23, float[] r24) {
            /*
                r20 = this;
                r0 = r20
                r1 = r21
                r2 = r22
                r3 = r24
                r4 = 1
                int r5 = r0.mFlags
                r6 = 1048575(0xfffff, float:1.469367E-39)
                r5 = r5 & r6
                r6 = 0
                int r7 = r0.mId
                r8 = 2097152(0x200000, float:2.938736E-39)
                r9 = -1
                if (r7 == r9) goto L_0x0018
                r5 = r5 | r8
            L_0x0018:
                int r7 = r0.mX
                r7 = r7 & -32768(0xffffffffffff8000, float:NaN)
                r10 = 67108864(0x4000000, float:1.5046328E-36)
                r12 = 0
                if (r7 != 0) goto L_0x003c
                int r7 = r0.mY
                r7 = r7 & -32768(0xffffffffffff8000, float:NaN)
                if (r7 != 0) goto L_0x003c
                int r7 = r0.mWidth
                r7 = r7 & -32768(0xffffffffffff8000, float:NaN)
                if (r7 == 0) goto L_0x002f
                r7 = 1
                goto L_0x0030
            L_0x002f:
                r7 = r12
            L_0x0030:
                int r13 = r0.mHeight
                r13 = r13 & -32768(0xffffffffffff8000, float:NaN)
                if (r13 == 0) goto L_0x0038
                r13 = 1
                goto L_0x0039
            L_0x0038:
                r13 = r12
            L_0x0039:
                r7 = r7 | r13
                if (r7 == 0) goto L_0x003d
            L_0x003c:
                r5 = r5 | r10
            L_0x003d:
                int r7 = r0.mScrollX
                r13 = 134217728(0x8000000, float:3.85186E-34)
                if (r7 != 0) goto L_0x0047
                int r7 = r0.mScrollY
                if (r7 == 0) goto L_0x0048
            L_0x0047:
                r5 = r5 | r13
            L_0x0048:
                android.graphics.Matrix r7 = r0.mMatrix
                r14 = 1073741824(0x40000000, float:2.0)
                if (r7 == 0) goto L_0x004f
                r5 = r5 | r14
            L_0x004f:
                float r7 = r0.mElevation
                r15 = 0
                int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
                r15 = 268435456(0x10000000, float:2.5243549E-29)
                if (r7 == 0) goto L_0x0059
                r5 = r5 | r15
            L_0x0059:
                float r7 = r0.mAlpha
                r16 = 1065353216(0x3f800000, float:1.0)
                int r7 = (r7 > r16 ? 1 : (r7 == r16 ? 0 : -1))
                r16 = 536870912(0x20000000, float:1.0842022E-19)
                if (r7 == 0) goto L_0x0065
                r5 = r5 | r16
            L_0x0065:
                java.lang.CharSequence r7 = r0.mContentDescription
                r17 = 33554432(0x2000000, float:9.403955E-38)
                if (r7 == 0) goto L_0x006d
                r5 = r5 | r17
            L_0x006d:
                android.app.assist.AssistStructure$ViewNodeText r7 = r0.mText
                r18 = 8388608(0x800000, float:1.17549435E-38)
                r19 = 16777216(0x1000000, float:2.3509887E-38)
                if (r7 == 0) goto L_0x0081
                r5 = r5 | r19
                android.app.assist.AssistStructure$ViewNodeText r7 = r0.mText
                boolean r7 = r7.isSimple()
                if (r7 != 0) goto L_0x0081
                r5 = r5 | r18
            L_0x0081:
                int r7 = r0.mInputType
                if (r7 == 0) goto L_0x0088
                r7 = 262144(0x40000, float:3.67342E-40)
                r5 = r5 | r7
            L_0x0088:
                java.lang.String r7 = r0.mWebScheme
                if (r7 != 0) goto L_0x0090
                java.lang.String r7 = r0.mWebDomain
                if (r7 == 0) goto L_0x0093
            L_0x0090:
                r7 = 524288(0x80000, float:7.34684E-40)
                r5 = r5 | r7
            L_0x0093:
                android.os.LocaleList r7 = r0.mLocaleList
                if (r7 == 0) goto L_0x009a
                r7 = 65536(0x10000, float:9.18355E-41)
                r5 = r5 | r7
            L_0x009a:
                android.os.Bundle r7 = r0.mExtras
                if (r7 == 0) goto L_0x00a1
                r7 = 4194304(0x400000, float:5.877472E-39)
                r5 = r5 | r7
            L_0x00a1:
                android.app.assist.AssistStructure$ViewNode[] r7 = r0.mChildren
                if (r7 == 0) goto L_0x00a8
                r7 = 1048576(0x100000, float:1.469368E-39)
                r5 = r5 | r7
            L_0x00a8:
                android.view.autofill.AutofillId r7 = r0.mAutofillId
                if (r7 == 0) goto L_0x00c2
                r6 = r6 | 1
                android.view.autofill.AutofillId r7 = r0.mAutofillId
                boolean r7 = r7.isVirtualInt()
                if (r7 == 0) goto L_0x00b8
                r6 = r6 | 2
            L_0x00b8:
                android.view.autofill.AutofillId r7 = r0.mAutofillId
                boolean r7 = r7.hasSession()
                if (r7 == 0) goto L_0x00c2
                r6 = r6 | 2048(0x800, float:2.87E-42)
            L_0x00c2:
                android.view.autofill.AutofillValue r7 = r0.mAutofillValue
                if (r7 == 0) goto L_0x00c8
                r6 = r6 | 4
            L_0x00c8:
                int r7 = r0.mAutofillType
                if (r7 == 0) goto L_0x00ce
                r6 = r6 | 8
            L_0x00ce:
                java.lang.String[] r7 = r0.mAutofillHints
                if (r7 == 0) goto L_0x00d4
                r6 = r6 | 16
            L_0x00d4:
                java.lang.CharSequence[] r7 = r0.mAutofillOptions
                if (r7 == 0) goto L_0x00da
                r6 = r6 | 32
            L_0x00da:
                android.view.ViewStructure$HtmlInfo r7 = r0.mHtmlInfo
                boolean r7 = r7 instanceof android.os.Parcelable
                if (r7 == 0) goto L_0x00e2
                r6 = r6 | 64
            L_0x00e2:
                int r7 = r0.mMinEms
                if (r7 <= r9) goto L_0x00e8
                r6 = r6 | 256(0x100, float:3.59E-43)
            L_0x00e8:
                int r7 = r0.mMaxEms
                if (r7 <= r9) goto L_0x00ee
                r6 = r6 | 512(0x200, float:7.175E-43)
            L_0x00ee:
                int r7 = r0.mMaxLength
                if (r7 <= r9) goto L_0x00f4
                r6 = r6 | 1024(0x400, float:1.435E-42)
            L_0x00f4:
                java.lang.String r7 = r0.mTextIdEntry
                if (r7 == 0) goto L_0x00fa
                r6 = r6 | 128(0x80, float:1.794E-43)
            L_0x00fa:
                java.lang.String r7 = r0.mClassName
                r2.writeString(r7)
                r7 = r5
                if (r6 == 0) goto L_0x010a
                boolean r11 = r0.mSanitized
                if (r11 != 0) goto L_0x0108
                if (r23 != 0) goto L_0x010a
            L_0x0108:
                r7 = r5 & -513(0xfffffffffffffdff, float:NaN)
            L_0x010a:
                android.app.assist.AssistStructure$AutofillOverlay r11 = r0.mAutofillOverlay
                if (r11 == 0) goto L_0x0119
                android.app.assist.AssistStructure$AutofillOverlay r11 = r0.mAutofillOverlay
                boolean r11 = r11.focused
                if (r11 == 0) goto L_0x0117
                r7 = r7 | 32
                goto L_0x0119
            L_0x0117:
                r7 = r7 & -33
            L_0x0119:
                r1.writeInt(r7)
                r1.writeInt(r6)
                r8 = r8 & r5
                if (r8 == 0) goto L_0x013e
                int r8 = r0.mId
                r1.writeInt(r8)
                int r8 = r0.mId
                if (r8 == r9) goto L_0x013e
                java.lang.String r8 = r0.mIdEntry
                r2.writeString(r8)
                java.lang.String r8 = r0.mIdEntry
                if (r8 == 0) goto L_0x013e
                java.lang.String r8 = r0.mIdType
                r2.writeString(r8)
                java.lang.String r8 = r0.mIdPackage
                r2.writeString(r8)
            L_0x013e:
                if (r6 == 0) goto L_0x01e2
                boolean r8 = r0.mSanitized
                r1.writeInt(r8)
                int r8 = r0.mImportantForAutofill
                r1.writeInt(r8)
                boolean r8 = r0.mSanitized
                if (r8 != 0) goto L_0x0153
                if (r23 != 0) goto L_0x0151
                goto L_0x0153
            L_0x0151:
                r8 = r12
                goto L_0x0154
            L_0x0153:
                r8 = 1
            L_0x0154:
                r4 = r8
                r8 = r6 & 1
                if (r8 == 0) goto L_0x017c
                android.view.autofill.AutofillId r8 = r0.mAutofillId
                int r8 = r8.getViewId()
                r1.writeInt(r8)
                r8 = r6 & 2
                if (r8 == 0) goto L_0x016f
                android.view.autofill.AutofillId r8 = r0.mAutofillId
                int r8 = r8.getVirtualChildIntId()
                r1.writeInt(r8)
            L_0x016f:
                r8 = r6 & 2048(0x800, float:2.87E-42)
                if (r8 == 0) goto L_0x017c
                android.view.autofill.AutofillId r8 = r0.mAutofillId
                int r8 = r8.getSessionId()
                r1.writeInt(r8)
            L_0x017c:
                r8 = r6 & 8
                if (r8 == 0) goto L_0x0185
                int r8 = r0.mAutofillType
                r1.writeInt(r8)
            L_0x0185:
                r8 = r6 & 16
                if (r8 == 0) goto L_0x018e
                java.lang.String[] r8 = r0.mAutofillHints
                r1.writeStringArray(r8)
            L_0x018e:
                r8 = r6 & 4
                if (r8 == 0) goto L_0x01aa
                if (r4 == 0) goto L_0x0197
                android.view.autofill.AutofillValue r8 = r0.mAutofillValue
                goto L_0x01a7
            L_0x0197:
                android.app.assist.AssistStructure$AutofillOverlay r8 = r0.mAutofillOverlay
                if (r8 == 0) goto L_0x01a6
                android.app.assist.AssistStructure$AutofillOverlay r8 = r0.mAutofillOverlay
                android.view.autofill.AutofillValue r8 = r8.value
                if (r8 == 0) goto L_0x01a6
                android.app.assist.AssistStructure$AutofillOverlay r8 = r0.mAutofillOverlay
                android.view.autofill.AutofillValue r8 = r8.value
                goto L_0x01a7
            L_0x01a6:
                r8 = 0
            L_0x01a7:
                r1.writeParcelable(r8, r12)
            L_0x01aa:
                r8 = r6 & 32
                if (r8 == 0) goto L_0x01b3
                java.lang.CharSequence[] r8 = r0.mAutofillOptions
                r1.writeCharSequenceArray(r8)
            L_0x01b3:
                r8 = r6 & 64
                if (r8 == 0) goto L_0x01be
                android.view.ViewStructure$HtmlInfo r8 = r0.mHtmlInfo
                android.os.Parcelable r8 = (android.os.Parcelable) r8
                r1.writeParcelable(r8, r12)
            L_0x01be:
                r8 = r6 & 256(0x100, float:3.59E-43)
                if (r8 == 0) goto L_0x01c7
                int r8 = r0.mMinEms
                r1.writeInt(r8)
            L_0x01c7:
                r8 = r6 & 512(0x200, float:7.175E-43)
                if (r8 == 0) goto L_0x01d0
                int r8 = r0.mMaxEms
                r1.writeInt(r8)
            L_0x01d0:
                r8 = r6 & 1024(0x400, float:1.435E-42)
                if (r8 == 0) goto L_0x01d9
                int r8 = r0.mMaxLength
                r1.writeInt(r8)
            L_0x01d9:
                r8 = r6 & 128(0x80, float:1.794E-43)
                if (r8 == 0) goto L_0x01e2
                java.lang.String r8 = r0.mTextIdEntry
                r2.writeString(r8)
            L_0x01e2:
                r8 = r5 & r10
                if (r8 == 0) goto L_0x01fb
                int r8 = r0.mX
                r1.writeInt(r8)
                int r8 = r0.mY
                r1.writeInt(r8)
                int r8 = r0.mWidth
                r1.writeInt(r8)
                int r8 = r0.mHeight
                r1.writeInt(r8)
                goto L_0x020f
            L_0x01fb:
                int r8 = r0.mY
                int r8 = r8 << 16
                int r9 = r0.mX
                r8 = r8 | r9
                r1.writeInt(r8)
                int r8 = r0.mHeight
                int r8 = r8 << 16
                int r9 = r0.mWidth
                r8 = r8 | r9
                r1.writeInt(r8)
            L_0x020f:
                r8 = r5 & r13
                if (r8 == 0) goto L_0x021d
                int r8 = r0.mScrollX
                r1.writeInt(r8)
                int r8 = r0.mScrollY
                r1.writeInt(r8)
            L_0x021d:
                r8 = r5 & r14
                if (r8 == 0) goto L_0x0229
                android.graphics.Matrix r8 = r0.mMatrix
                r8.getValues(r3)
                r1.writeFloatArray(r3)
            L_0x0229:
                r8 = r5 & r15
                if (r8 == 0) goto L_0x0232
                float r8 = r0.mElevation
                r1.writeFloat(r8)
            L_0x0232:
                r8 = r5 & r16
                if (r8 == 0) goto L_0x023b
                float r8 = r0.mAlpha
                r1.writeFloat(r8)
            L_0x023b:
                r8 = r5 & r17
                if (r8 == 0) goto L_0x0244
                java.lang.CharSequence r8 = r0.mContentDescription
                android.text.TextUtils.writeToParcel(r8, r1, r12)
            L_0x0244:
                r8 = r5 & r19
                if (r8 == 0) goto L_0x0254
                android.app.assist.AssistStructure$ViewNodeText r8 = r0.mText
                r9 = r5 & r18
                if (r9 != 0) goto L_0x0250
                r9 = 1
                goto L_0x0251
            L_0x0250:
                r9 = r12
            L_0x0251:
                r8.writeToParcel(r1, r9, r4)
            L_0x0254:
                r8 = 262144(0x40000, float:3.67342E-40)
                r8 = r8 & r5
                if (r8 == 0) goto L_0x025e
                int r8 = r0.mInputType
                r1.writeInt(r8)
            L_0x025e:
                r8 = 524288(0x80000, float:7.34684E-40)
                r8 = r8 & r5
                if (r8 == 0) goto L_0x026d
                java.lang.String r8 = r0.mWebScheme
                r1.writeString(r8)
                java.lang.String r8 = r0.mWebDomain
                r1.writeString(r8)
            L_0x026d:
                r8 = 65536(0x10000, float:9.18355E-41)
                r8 = r8 & r5
                if (r8 == 0) goto L_0x0277
                android.os.LocaleList r8 = r0.mLocaleList
                r1.writeParcelable(r8, r12)
            L_0x0277:
                r8 = 4194304(0x400000, float:5.877472E-39)
                r8 = r8 & r5
                if (r8 == 0) goto L_0x0281
                android.os.Bundle r8 = r0.mExtras
                r1.writeBundle(r8)
            L_0x0281:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.assist.AssistStructure.ViewNode.writeSelfToParcel(android.os.Parcel, android.os.PooledStringWriter, boolean, float[]):int");
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

        public AutofillId getAutofillId() {
            return this.mAutofillId;
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

        public void setAutofillOverlay(AutofillOverlay overlay) {
            this.mAutofillOverlay = overlay;
        }

        public CharSequence[] getAutofillOptions() {
            return this.mAutofillOptions;
        }

        public int getInputType() {
            return this.mInputType;
        }

        public boolean isSanitized() {
            return this.mSanitized;
        }

        public void updateAutofillValue(AutofillValue value) {
            this.mAutofillValue = value;
            if (value.isText()) {
                if (this.mText == null) {
                    this.mText = new ViewNodeText();
                }
                this.mText.mText = value.getTextValue();
            }
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

        public Matrix getTransformation() {
            return this.mMatrix;
        }

        public float getElevation() {
            return this.mElevation;
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public int getVisibility() {
            return this.mFlags & 12;
        }

        public boolean isAssistBlocked() {
            return (this.mFlags & 128) != 0;
        }

        public boolean isEnabled() {
            return (this.mFlags & 1) == 0;
        }

        public boolean isClickable() {
            return (this.mFlags & 1024) != 0;
        }

        public boolean isFocusable() {
            return (this.mFlags & 16) != 0;
        }

        public boolean isFocused() {
            return (this.mFlags & 32) != 0;
        }

        public boolean isAccessibilityFocused() {
            return (this.mFlags & 4096) != 0;
        }

        public boolean isCheckable() {
            return (this.mFlags & 256) != 0;
        }

        public boolean isChecked() {
            return (this.mFlags & 512) != 0;
        }

        public boolean isSelected() {
            return (this.mFlags & 64) != 0;
        }

        public boolean isActivated() {
            return (this.mFlags & 8192) != 0;
        }

        public boolean isOpaque() {
            return (this.mFlags & 32768) != 0;
        }

        public boolean isLongClickable() {
            return (this.mFlags & 2048) != 0;
        }

        public boolean isContextClickable() {
            return (this.mFlags & 16384) != 0;
        }

        public String getClassName() {
            return this.mClassName;
        }

        public CharSequence getContentDescription() {
            return this.mContentDescription;
        }

        public String getWebDomain() {
            return this.mWebDomain;
        }

        public void setWebDomain(String domain) {
            if (domain != null) {
                Uri uri = Uri.parse(domain);
                if (uri == null) {
                    Log.w(AssistStructure.TAG, "Failed to parse web domain");
                    return;
                }
                this.mWebScheme = uri.getScheme();
                this.mWebDomain = uri.getHost();
            }
        }

        public String getWebScheme() {
            return this.mWebScheme;
        }

        public ViewStructure.HtmlInfo getHtmlInfo() {
            return this.mHtmlInfo;
        }

        public LocaleList getLocaleList() {
            return this.mLocaleList;
        }

        public CharSequence getText() {
            if (this.mText != null) {
                return this.mText.mText;
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

        public String getTextIdEntry() {
            return this.mTextIdEntry;
        }

        public String getHint() {
            if (this.mText != null) {
                return this.mText.mHint;
            }
            return null;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getChildCount() {
            if (this.mChildren != null) {
                return this.mChildren.length;
            }
            return 0;
        }

        public ViewNode getChildAt(int index) {
            return this.mChildren[index];
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

        public int getImportantForAutofill() {
            return this.mImportantForAutofill;
        }
    }

    static class ViewNodeBuilder extends ViewStructure {
        final AssistStructure mAssist;
        final boolean mAsync;
        final ViewNode mNode;

        ViewNodeBuilder(AssistStructure assist, ViewNode node, boolean async) {
            this.mAssist = assist;
            this.mNode = node;
            this.mAsync = async;
        }

        public void setId(int id, String packageName, String typeName, String entryName) {
            this.mNode.mId = id;
            this.mNode.mIdPackage = packageName;
            this.mNode.mIdType = typeName;
            this.mNode.mIdEntry = entryName;
        }

        public void setDimens(int left, int top, int scrollX, int scrollY, int width, int height) {
            this.mNode.mX = left;
            this.mNode.mY = top;
            this.mNode.mScrollX = scrollX;
            this.mNode.mScrollY = scrollY;
            this.mNode.mWidth = width;
            this.mNode.mHeight = height;
        }

        public void setTransformation(Matrix matrix) {
            if (matrix == null) {
                this.mNode.mMatrix = null;
                return;
            }
            this.mNode.mMatrix = new Matrix(matrix);
        }

        public void setElevation(float elevation) {
            this.mNode.mElevation = elevation;
        }

        public void setAlpha(float alpha) {
            this.mNode.mAlpha = alpha;
        }

        public void setVisibility(int visibility) {
            this.mNode.mFlags = (this.mNode.mFlags & -13) | (visibility & 12);
        }

        public void setAssistBlocked(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -129) | (state ? 128 : 0);
        }

        public void setEnabled(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & true) | (state ^ true) ? 1 : 0;
        }

        public void setClickable(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -1025) | (state ? 1024 : 0);
        }

        public void setLongClickable(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -2049) | (state ? 2048 : 0);
        }

        public void setContextClickable(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -16385) | (state ? 16384 : 0);
        }

        public void setFocusable(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -17) | (state ? 16 : 0);
        }

        public void setFocused(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -33) | (state ? 32 : 0);
        }

        public void setAccessibilityFocused(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -4097) | (state ? 4096 : 0);
        }

        public void setCheckable(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & TrafficStats.TAG_NETWORK_STACK_RANGE_END) | (state ? 256 : 0);
        }

        public void setChecked(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -513) | (state ? 512 : 0);
        }

        public void setSelected(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -65) | (state ? 64 : 0);
        }

        public void setActivated(boolean state) {
            this.mNode.mFlags = (this.mNode.mFlags & -8193) | (state ? 8192 : 0);
        }

        public void setOpaque(boolean opaque) {
            this.mNode.mFlags = (this.mNode.mFlags & -32769) | (opaque ? 32768 : 0);
        }

        public void setClassName(String className) {
            this.mNode.mClassName = className;
        }

        public void setContentDescription(CharSequence contentDescription) {
            this.mNode.mContentDescription = contentDescription;
        }

        private final ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new ViewNodeText();
            return this.mNode.mText;
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
            this.mNode.mTextIdEntry = (String) Preconditions.checkNotNull(entryName);
        }

        public void setHint(CharSequence hint) {
            getNodeText().mHint = hint != null ? hint.toString() : null;
        }

        public CharSequence getText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mText;
            }
            return null;
        }

        public int getTextSelectionStart() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mTextSelectionStart;
            }
            return -1;
        }

        public int getTextSelectionEnd() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mTextSelectionEnd;
            }
            return -1;
        }

        public CharSequence getHint() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mHint;
            }
            return null;
        }

        public Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            this.mNode.mExtras = new Bundle();
            return this.mNode.mExtras;
        }

        public boolean hasExtras() {
            return this.mNode.mExtras != null;
        }

        public void setChildCount(int num) {
            this.mNode.mChildren = new ViewNode[num];
        }

        public int addChildCount(int num) {
            if (this.mNode.mChildren == null) {
                setChildCount(num);
                return 0;
            }
            int start = this.mNode.mChildren.length;
            ViewNode[] newArray = new ViewNode[(start + num)];
            System.arraycopy(this.mNode.mChildren, 0, newArray, 0, start);
            this.mNode.mChildren = newArray;
            return start;
        }

        public int getChildCount() {
            if (this.mNode.mChildren != null) {
                return this.mNode.mChildren.length;
            }
            return 0;
        }

        public ViewStructure newChild(int index) {
            ViewNode node = new ViewNode();
            this.mNode.mChildren[index] = node;
            return new ViewNodeBuilder(this.mAssist, node, false);
        }

        public ViewStructure asyncNewChild(int index) {
            ViewNodeBuilder builder;
            synchronized (this.mAssist) {
                ViewNode node = new ViewNode();
                this.mNode.mChildren[index] = node;
                builder = new ViewNodeBuilder(this.mAssist, node, true);
                this.mAssist.mPendingAsyncChildren.add(builder);
            }
            return builder;
        }

        public void asyncCommit() {
            synchronized (this.mAssist) {
                if (!this.mAsync) {
                    throw new IllegalStateException("Child " + this + " was not created with ViewStructure.asyncNewChild");
                } else if (this.mAssist.mPendingAsyncChildren.remove(this)) {
                    this.mAssist.notifyAll();
                } else {
                    throw new IllegalStateException("Child " + this + " already committed");
                }
            }
        }

        public Rect getTempRect() {
            return this.mAssist.mTmpRect;
        }

        public void setAutofillId(AutofillId id) {
            this.mNode.mAutofillId = id;
        }

        public void setAutofillId(AutofillId parentId, int virtualId) {
            this.mNode.mAutofillId = new AutofillId(parentId, virtualId);
        }

        public AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        public void setAutofillType(int type) {
            this.mNode.mAutofillType = type;
        }

        public void setAutofillHints(String[] hints) {
            this.mNode.mAutofillHints = hints;
        }

        public void setAutofillValue(AutofillValue value) {
            this.mNode.mAutofillValue = value;
        }

        public void setAutofillOptions(CharSequence[] options) {
            this.mNode.mAutofillOptions = options;
        }

        public void setImportantForAutofill(int mode) {
            this.mNode.mImportantForAutofill = mode;
        }

        public void setInputType(int inputType) {
            this.mNode.mInputType = inputType;
        }

        public void setMinTextEms(int minEms) {
            this.mNode.mMinEms = minEms;
        }

        public void setMaxTextEms(int maxEms) {
            this.mNode.mMaxEms = maxEms;
        }

        public void setMaxTextLength(int maxLength) {
            this.mNode.mMaxLength = maxLength;
        }

        public void setDataIsSensitive(boolean sensitive) {
            this.mNode.mSanitized = !sensitive;
        }

        public void setWebDomain(String domain) {
            this.mNode.setWebDomain(domain);
        }

        public void setLocaleList(LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String tagName) {
            return new HtmlInfoNodeBuilder(tagName);
        }

        public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
            this.mNode.mHtmlInfo = htmlInfo;
        }
    }

    private static final class HtmlInfoNode extends ViewStructure.HtmlInfo implements Parcelable {
        public static final Parcelable.Creator<HtmlInfoNode> CREATOR = new Parcelable.Creator<HtmlInfoNode>() {
            public HtmlInfoNode createFromParcel(Parcel parcel) {
                HtmlInfoNodeBuilder builder = new HtmlInfoNodeBuilder(parcel.readString());
                String[] names = parcel.readStringArray();
                String[] values = parcel.readStringArray();
                if (!(names == null || values == null)) {
                    if (names.length != values.length) {
                        Log.w(AssistStructure.TAG, "HtmlInfo attributes mismatch: names=" + names.length + ", values=" + values.length);
                    } else {
                        for (int i = 0; i < names.length; i++) {
                            builder.addAttribute(names[i], values[i]);
                        }
                    }
                }
                return builder.build();
            }

            public HtmlInfoNode[] newArray(int size) {
                return new HtmlInfoNode[size];
            }
        };
        private ArrayList<Pair<String, String>> mAttributes;
        private final String[] mNames;
        private final String mTag;
        private final String[] mValues;

        private HtmlInfoNode(HtmlInfoNodeBuilder builder) {
            this.mTag = builder.mTag;
            if (builder.mNames == null) {
                this.mNames = null;
                this.mValues = null;
                return;
            }
            this.mNames = new String[builder.mNames.size()];
            this.mValues = new String[builder.mValues.size()];
            builder.mNames.toArray(this.mNames);
            builder.mValues.toArray(this.mValues);
        }

        public String getTag() {
            return this.mTag;
        }

        public List<Pair<String, String>> getAttributes() {
            if (this.mAttributes == null && this.mNames != null) {
                this.mAttributes = new ArrayList<>(this.mNames.length);
                for (int i = 0; i < this.mNames.length; i++) {
                    this.mAttributes.add(i, new Pair<>(this.mNames[i], this.mValues[i]));
                }
            }
            return this.mAttributes;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(this.mTag);
            parcel.writeStringArray(this.mNames);
            parcel.writeStringArray(this.mValues);
        }
    }

    private static final class HtmlInfoNodeBuilder extends ViewStructure.HtmlInfo.Builder {
        /* access modifiers changed from: private */
        public ArrayList<String> mNames;
        /* access modifiers changed from: private */
        public final String mTag;
        /* access modifiers changed from: private */
        public ArrayList<String> mValues;

        HtmlInfoNodeBuilder(String tag) {
            this.mTag = tag;
        }

        public ViewStructure.HtmlInfo.Builder addAttribute(String name, String value) {
            if (this.mNames == null) {
                this.mNames = new ArrayList<>();
                this.mValues = new ArrayList<>();
            }
            this.mNames.add(name);
            this.mValues.add(value);
            return this;
        }

        public HtmlInfoNode build() {
            return new HtmlInfoNode(this);
        }
    }

    public AssistStructure(Activity activity, boolean forAutoFill, int flags) {
        this.mWindowNodes = new ArrayList<>();
        this.mPendingAsyncChildren = new ArrayList<>();
        this.mTmpRect = new Rect();
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = flags;
        ArrayList<ViewRootImpl> views = WindowManagerGlobal.getInstance().getRootViews(activity.getActivityToken());
        for (int i = 0; i < views.size(); i++) {
            ViewRootImpl root = views.get(i);
            if (root.getView() == null) {
                Log.w(TAG, "Skipping window with dettached view: " + root.getTitle());
            } else {
                this.mWindowNodes.add(new WindowNode(this, root, forAutoFill, flags));
            }
        }
    }

    public AssistStructure() {
        this.mWindowNodes = new ArrayList<>();
        this.mPendingAsyncChildren = new ArrayList<>();
        this.mTmpRect = new Rect();
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = 0;
    }

    public AssistStructure(Parcel in) {
        this.mWindowNodes = new ArrayList<>();
        this.mPendingAsyncChildren = new ArrayList<>();
        this.mTmpRect = new Rect();
        boolean z = false;
        this.mSanitizeOnWrite = false;
        this.mTaskId = in.readInt();
        this.mActivityComponent = ComponentName.readFromParcel(in);
        this.mIsHomeActivity = in.readInt() == 1 ? true : z;
        this.mReceiveChannel = in.readStrongBinder();
    }

    public void sanitizeForParceling(boolean sanitize) {
        this.mSanitizeOnWrite = sanitize;
    }

    public void dump(boolean showSensitive) {
        String str;
        if (this.mActivityComponent == null) {
            Log.i(TAG, "dump(): calling ensureData() first");
            ensureData();
        }
        Log.i(TAG, "Task id: " + this.mTaskId);
        StringBuilder sb = new StringBuilder();
        sb.append("Activity: ");
        if (this.mActivityComponent != null) {
            str = this.mActivityComponent.flattenToShortString();
        } else {
            str = null;
        }
        sb.append(str);
        Log.i(TAG, sb.toString());
        Log.i(TAG, "Sanitize on write: " + this.mSanitizeOnWrite);
        Log.i(TAG, "Flags: " + this.mFlags);
        int N = getWindowNodeCount();
        for (int i = 0; i < N; i++) {
            WindowNode node = getWindowNodeAt(i);
            Log.i(TAG, "Window #" + i + " [" + node.getLeft() + SmsManager.REGEX_PREFIX_DELIMITER + node.getTop() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + node.getWidth() + "x" + node.getHeight() + "] " + node.getTitle());
            dump("  ", node.getRootViewNode(), showSensitive);
        }
    }

    /* access modifiers changed from: package-private */
    public void dump(String prefix, ViewNode node, boolean showSensitive) {
        String safeText;
        String str = prefix;
        boolean z = showSensitive;
        Log.i(TAG, str + "View [" + node.getLeft() + SmsManager.REGEX_PREFIX_DELIMITER + node.getTop() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + node.getWidth() + "x" + node.getHeight() + "] " + node.getClassName());
        int id = node.getId();
        if (id != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("  ID: #");
            sb.append(Integer.toHexString(id));
            String entry = node.getIdEntry();
            if (entry != null) {
                String type = node.getIdType();
                String pkg = node.getIdPackage();
                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                sb.append(pkg);
                sb.append(SettingsStringUtil.DELIMITER);
                sb.append(type);
                sb.append("/");
                sb.append(entry);
            }
            Log.i(TAG, sb.toString());
        }
        int scrollX = node.getScrollX();
        int scrollY = node.getScrollY();
        if (!(scrollX == 0 && scrollY == 0)) {
            Log.i(TAG, str + "  Scroll: " + scrollX + SmsManager.REGEX_PREFIX_DELIMITER + scrollY);
        }
        Matrix matrix = node.getTransformation();
        if (matrix != null) {
            Log.i(TAG, str + "  Transformation: " + matrix);
        }
        float elevation = node.getElevation();
        if (elevation != 0.0f) {
            Log.i(TAG, str + "  Elevation: " + elevation);
        }
        if (node.getAlpha() != 0.0f) {
            Log.i(TAG, str + "  Alpha: " + elevation);
        }
        CharSequence contentDescription = node.getContentDescription();
        if (contentDescription != null) {
            Log.i(TAG, str + "  Content description: " + contentDescription);
        }
        CharSequence text = node.getText();
        if (text != null) {
            if (node.isSanitized() || z) {
                safeText = text.toString();
            } else {
                safeText = "REDACTED[" + text.length() + " chars]";
            }
            Log.i(TAG, str + "  Text (sel " + node.getTextSelectionStart() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + node.getTextSelectionEnd() + "): " + safeText);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("  Text size: ");
            sb2.append(node.getTextSize());
            sb2.append(" , style: #");
            sb2.append(node.getTextStyle());
            Log.i(TAG, sb2.toString());
            Log.i(TAG, str + "  Text color fg: #" + Integer.toHexString(node.getTextColor()) + ", bg: #" + Integer.toHexString(node.getTextBackgroundColor()));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("  Input type: ");
            sb3.append(node.getInputType());
            Log.i(TAG, sb3.toString());
            Log.i(TAG, str + "  Resource id: " + node.getTextIdEntry());
        }
        String safeText2 = node.getWebDomain();
        if (safeText2 != null) {
            Log.i(TAG, str + "  Web domain: " + safeText2);
        }
        ViewStructure.HtmlInfo htmlInfo = node.getHtmlInfo();
        if (htmlInfo != null) {
            Log.i(TAG, str + "  HtmlInfo: tag=" + htmlInfo.getTag() + ", attr=" + htmlInfo.getAttributes());
        }
        LocaleList localeList = node.getLocaleList();
        if (localeList != null) {
            Log.i(TAG, str + "  LocaleList: " + localeList);
        }
        String hint = node.getHint();
        if (hint != null) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            int i = id;
            sb4.append("  Hint: ");
            sb4.append(hint);
            Log.i(TAG, sb4.toString());
        }
        Bundle extras = node.getExtras();
        if (extras != null) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            int i2 = scrollX;
            sb5.append("  Extras: ");
            sb5.append(extras);
            Log.i(TAG, sb5.toString());
        }
        if (node.isAssistBlocked() != 0) {
            Log.i(TAG, str + "  BLOCKED");
        }
        AutofillId autofillId = node.getAutofillId();
        if (autofillId == null) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            Bundle bundle = extras;
            sb6.append(" NO autofill ID");
            Log.i(TAG, sb6.toString());
        } else {
            Log.i(TAG, str + "  Autofill info: id= " + autofillId + ", type=" + node.getAutofillType() + ", options=" + Arrays.toString(node.getAutofillOptions()) + ", hints=" + Arrays.toString(node.getAutofillHints()) + ", value=" + node.getAutofillValue() + ", sanitized=" + node.isSanitized() + ", important=" + node.getImportantForAutofill());
        }
        int NCHILDREN = node.getChildCount();
        if (NCHILDREN > 0) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str);
            AutofillId autofillId2 = autofillId;
            sb7.append("  Children:");
            Log.i(TAG, sb7.toString());
            String cprefix = str + "    ";
            int i3 = 0;
            while (i3 < NCHILDREN) {
                dump(cprefix, node.getChildAt(i3), z);
                i3++;
                NCHILDREN = NCHILDREN;
                String str2 = prefix;
            }
            ViewNode viewNode = node;
            int i4 = NCHILDREN;
            return;
        }
        ViewNode viewNode2 = node;
        int i5 = NCHILDREN;
        AutofillId autofillId3 = autofillId;
    }

    public void setTaskId(int taskId) {
        this.mTaskId = taskId;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public void setActivityComponent(ComponentName componentName) {
        this.mActivityComponent = componentName;
    }

    public ComponentName getActivityComponent() {
        return this.mActivityComponent;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean isHomeActivity() {
        return this.mIsHomeActivity;
    }

    public int getWindowNodeCount() {
        ensureData();
        return this.mWindowNodes.size();
    }

    public WindowNode getWindowNodeAt(int index) {
        ensureData();
        return this.mWindowNodes.get(index);
    }

    public void ensureDataForAutofill() {
        if (!this.mHaveData) {
            this.mHaveData = true;
            Binder.allowBlocking(this.mReceiveChannel);
            try {
                new ParcelTransferReader(this.mReceiveChannel).go();
            } finally {
                Binder.defaultBlocking(this.mReceiveChannel);
            }
        }
    }

    public void ensureData() {
        if (!this.mHaveData) {
            this.mHaveData = true;
            new ParcelTransferReader(this.mReceiveChannel).go();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean waitForReady() {
        boolean skipStructure = false;
        synchronized (this) {
            long endTime = SystemClock.uptimeMillis() + TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS;
            while (this.mPendingAsyncChildren.size() > 0) {
                long uptimeMillis = SystemClock.uptimeMillis();
                long now = uptimeMillis;
                if (uptimeMillis < endTime) {
                    try {
                        wait(endTime - now);
                    } catch (InterruptedException e) {
                    }
                }
            }
            if (this.mPendingAsyncChildren.size() > 0) {
                Log.w(TAG, "Skipping assist structure, waiting too long for async children (have " + this.mPendingAsyncChildren.size() + " remaining");
                skipStructure = true;
            }
        }
        return !skipStructure;
    }

    public void clearSendChannel() {
        if (this.mSendChannel != null) {
            this.mSendChannel.mAssistStructure = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mTaskId);
        ComponentName.writeToParcel(this.mActivityComponent, out);
        out.writeInt(this.mIsHomeActivity ? 1 : 0);
        if (this.mHaveData) {
            if (this.mSendChannel == null) {
                this.mSendChannel = new SendChannel(this);
            }
            out.writeStrongBinder(this.mSendChannel);
            return;
        }
        out.writeStrongBinder(this.mReceiveChannel);
    }
}
