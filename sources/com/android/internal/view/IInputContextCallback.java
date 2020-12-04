package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.inputmethod.ExtractedText;

public interface IInputContextCallback extends IInterface {
    void setCommitContentResult(boolean z, int i) throws RemoteException;

    void setCursorCapsMode(int i, int i2) throws RemoteException;

    void setExtractedText(ExtractedText extractedText, int i) throws RemoteException;

    void setRequestUpdateCursorAnchorInfoResult(boolean z, int i) throws RemoteException;

    void setSelectedText(CharSequence charSequence, int i) throws RemoteException;

    void setTextAfterCursor(CharSequence charSequence, int i) throws RemoteException;

    void setTextBeforeCursor(CharSequence charSequence, int i) throws RemoteException;

    public static class Default implements IInputContextCallback {
        public void setTextBeforeCursor(CharSequence textBeforeCursor, int seq) throws RemoteException {
        }

        public void setTextAfterCursor(CharSequence textAfterCursor, int seq) throws RemoteException {
        }

        public void setCursorCapsMode(int capsMode, int seq) throws RemoteException {
        }

        public void setExtractedText(ExtractedText extractedText, int seq) throws RemoteException {
        }

        public void setSelectedText(CharSequence selectedText, int seq) throws RemoteException {
        }

        public void setRequestUpdateCursorAnchorInfoResult(boolean result, int seq) throws RemoteException {
        }

        public void setCommitContentResult(boolean result, int seq) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputContextCallback {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputContextCallback";
        static final int TRANSACTION_setCommitContentResult = 7;
        static final int TRANSACTION_setCursorCapsMode = 3;
        static final int TRANSACTION_setExtractedText = 4;
        static final int TRANSACTION_setRequestUpdateCursorAnchorInfoResult = 6;
        static final int TRANSACTION_setSelectedText = 5;
        static final int TRANSACTION_setTextAfterCursor = 2;
        static final int TRANSACTION_setTextBeforeCursor = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputContextCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputContextCallback)) {
                return new Proxy(obj);
            }
            return (IInputContextCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setTextBeforeCursor";
                case 2:
                    return "setTextAfterCursor";
                case 3:
                    return "setCursorCapsMode";
                case 4:
                    return "setExtractedText";
                case 5:
                    return "setSelectedText";
                case 6:
                    return "setRequestUpdateCursorAnchorInfoResult";
                case 7:
                    return "setCommitContentResult";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.view.inputmethod.ExtractedText} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: java.lang.CharSequence} */
        /* JADX WARNING: type inference failed for: r3v13, types: [android.view.inputmethod.ExtractedText] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "com.android.internal.view.IInputContextCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x00bb
                r1 = 0
                r3 = 0
                switch(r5) {
                    case 1: goto L_0x009e;
                    case 2: goto L_0x0081;
                    case 3: goto L_0x0072;
                    case 4: goto L_0x0055;
                    case 5: goto L_0x0038;
                    case 6: goto L_0x0025;
                    case 7: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0012:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x001d
                r1 = r2
            L_0x001d:
                int r3 = r6.readInt()
                r4.setCommitContentResult(r1, r3)
                return r2
            L_0x0025:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0030
                r1 = r2
            L_0x0030:
                int r3 = r6.readInt()
                r4.setRequestUpdateCursorAnchorInfoResult(r1, r3)
                return r2
            L_0x0038:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x004b
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                goto L_0x004c
            L_0x004b:
            L_0x004c:
                r1 = r3
                int r3 = r6.readInt()
                r4.setSelectedText(r1, r3)
                return r2
            L_0x0055:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0068
                android.os.Parcelable$Creator<android.view.inputmethod.ExtractedText> r1 = android.view.inputmethod.ExtractedText.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.view.inputmethod.ExtractedText r3 = (android.view.inputmethod.ExtractedText) r3
                goto L_0x0069
            L_0x0068:
            L_0x0069:
                r1 = r3
                int r3 = r6.readInt()
                r4.setExtractedText(r1, r3)
                return r2
            L_0x0072:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                int r3 = r6.readInt()
                r4.setCursorCapsMode(r1, r3)
                return r2
            L_0x0081:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0094
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                goto L_0x0095
            L_0x0094:
            L_0x0095:
                r1 = r3
                int r3 = r6.readInt()
                r4.setTextAfterCursor(r1, r3)
                return r2
            L_0x009e:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00b1
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                goto L_0x00b2
            L_0x00b1:
            L_0x00b2:
                r1 = r3
                int r3 = r6.readInt()
                r4.setTextBeforeCursor(r1, r3)
                return r2
            L_0x00bb:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.IInputContextCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputContextCallback {
            public static IInputContextCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void setTextBeforeCursor(CharSequence textBeforeCursor, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textBeforeCursor != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(textBeforeCursor, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setTextBeforeCursor(textBeforeCursor, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setTextAfterCursor(CharSequence textAfterCursor, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textAfterCursor != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(textAfterCursor, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setTextAfterCursor(textAfterCursor, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setCursorCapsMode(int capsMode, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capsMode);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCursorCapsMode(capsMode, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setExtractedText(ExtractedText extractedText, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extractedText != null) {
                        _data.writeInt(1);
                        extractedText.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setExtractedText(extractedText, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSelectedText(CharSequence selectedText, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (selectedText != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(selectedText, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSelectedText(selectedText, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRequestUpdateCursorAnchorInfoResult(boolean result, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(result);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRequestUpdateCursorAnchorInfoResult(result, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setCommitContentResult(boolean result, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(result);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCommitContentResult(result, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputContextCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputContextCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
