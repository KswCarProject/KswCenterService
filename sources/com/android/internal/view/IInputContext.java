package com.android.internal.view;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputContentInfo;

public interface IInputContext extends IInterface {
    void beginBatchEdit() throws RemoteException;

    void clearMetaKeyStates(int i) throws RemoteException;

    void commitCompletion(CompletionInfo completionInfo) throws RemoteException;

    void commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle, int i2, IInputContextCallback iInputContextCallback) throws RemoteException;

    void commitCorrection(CorrectionInfo correctionInfo) throws RemoteException;

    void commitText(CharSequence charSequence, int i) throws RemoteException;

    void deleteSurroundingText(int i, int i2) throws RemoteException;

    void deleteSurroundingTextInCodePoints(int i, int i2) throws RemoteException;

    void endBatchEdit() throws RemoteException;

    void finishComposingText() throws RemoteException;

    void getCursorCapsMode(int i, int i2, IInputContextCallback iInputContextCallback) throws RemoteException;

    void getExtractedText(ExtractedTextRequest extractedTextRequest, int i, int i2, IInputContextCallback iInputContextCallback) throws RemoteException;

    void getSelectedText(int i, int i2, IInputContextCallback iInputContextCallback) throws RemoteException;

    void getTextAfterCursor(int i, int i2, int i3, IInputContextCallback iInputContextCallback) throws RemoteException;

    void getTextBeforeCursor(int i, int i2, int i3, IInputContextCallback iInputContextCallback) throws RemoteException;

    void performContextMenuAction(int i) throws RemoteException;

    void performEditorAction(int i) throws RemoteException;

    void performPrivateCommand(String str, Bundle bundle) throws RemoteException;

    void requestUpdateCursorAnchorInfo(int i, int i2, IInputContextCallback iInputContextCallback) throws RemoteException;

    void sendKeyEvent(KeyEvent keyEvent) throws RemoteException;

    void setComposingRegion(int i, int i2) throws RemoteException;

    void setComposingText(CharSequence charSequence, int i) throws RemoteException;

    void setSelection(int i, int i2) throws RemoteException;

    public static class Default implements IInputContext {
        public void getTextBeforeCursor(int length, int flags, int seq, IInputContextCallback callback) throws RemoteException {
        }

        public void getTextAfterCursor(int length, int flags, int seq, IInputContextCallback callback) throws RemoteException {
        }

        public void getCursorCapsMode(int reqModes, int seq, IInputContextCallback callback) throws RemoteException {
        }

        public void getExtractedText(ExtractedTextRequest request, int flags, int seq, IInputContextCallback callback) throws RemoteException {
        }

        public void deleteSurroundingText(int beforeLength, int afterLength) throws RemoteException {
        }

        public void deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) throws RemoteException {
        }

        public void setComposingText(CharSequence text, int newCursorPosition) throws RemoteException {
        }

        public void finishComposingText() throws RemoteException {
        }

        public void commitText(CharSequence text, int newCursorPosition) throws RemoteException {
        }

        public void commitCompletion(CompletionInfo completion) throws RemoteException {
        }

        public void commitCorrection(CorrectionInfo correction) throws RemoteException {
        }

        public void setSelection(int start, int end) throws RemoteException {
        }

        public void performEditorAction(int actionCode) throws RemoteException {
        }

        public void performContextMenuAction(int id) throws RemoteException {
        }

        public void beginBatchEdit() throws RemoteException {
        }

        public void endBatchEdit() throws RemoteException {
        }

        public void sendKeyEvent(KeyEvent event) throws RemoteException {
        }

        public void clearMetaKeyStates(int states) throws RemoteException {
        }

        public void performPrivateCommand(String action, Bundle data) throws RemoteException {
        }

        public void setComposingRegion(int start, int end) throws RemoteException {
        }

        public void getSelectedText(int flags, int seq, IInputContextCallback callback) throws RemoteException {
        }

        public void requestUpdateCursorAnchorInfo(int cursorUpdateMode, int seq, IInputContextCallback callback) throws RemoteException {
        }

        public void commitContent(InputContentInfo inputContentInfo, int flags, Bundle opts, int sec, IInputContextCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputContext {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputContext";
        static final int TRANSACTION_beginBatchEdit = 15;
        static final int TRANSACTION_clearMetaKeyStates = 18;
        static final int TRANSACTION_commitCompletion = 10;
        static final int TRANSACTION_commitContent = 23;
        static final int TRANSACTION_commitCorrection = 11;
        static final int TRANSACTION_commitText = 9;
        static final int TRANSACTION_deleteSurroundingText = 5;
        static final int TRANSACTION_deleteSurroundingTextInCodePoints = 6;
        static final int TRANSACTION_endBatchEdit = 16;
        static final int TRANSACTION_finishComposingText = 8;
        static final int TRANSACTION_getCursorCapsMode = 3;
        static final int TRANSACTION_getExtractedText = 4;
        static final int TRANSACTION_getSelectedText = 21;
        static final int TRANSACTION_getTextAfterCursor = 2;
        static final int TRANSACTION_getTextBeforeCursor = 1;
        static final int TRANSACTION_performContextMenuAction = 14;
        static final int TRANSACTION_performEditorAction = 13;
        static final int TRANSACTION_performPrivateCommand = 19;
        static final int TRANSACTION_requestUpdateCursorAnchorInfo = 22;
        static final int TRANSACTION_sendKeyEvent = 17;
        static final int TRANSACTION_setComposingRegion = 20;
        static final int TRANSACTION_setComposingText = 7;
        static final int TRANSACTION_setSelection = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputContext asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputContext)) {
                return new Proxy(obj);
            }
            return (IInputContext) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getTextBeforeCursor";
                case 2:
                    return "getTextAfterCursor";
                case 3:
                    return "getCursorCapsMode";
                case 4:
                    return "getExtractedText";
                case 5:
                    return "deleteSurroundingText";
                case 6:
                    return "deleteSurroundingTextInCodePoints";
                case 7:
                    return "setComposingText";
                case 8:
                    return "finishComposingText";
                case 9:
                    return "commitText";
                case 10:
                    return "commitCompletion";
                case 11:
                    return "commitCorrection";
                case 12:
                    return "setSelection";
                case 13:
                    return "performEditorAction";
                case 14:
                    return "performContextMenuAction";
                case 15:
                    return "beginBatchEdit";
                case 16:
                    return "endBatchEdit";
                case 17:
                    return "sendKeyEvent";
                case 18:
                    return "clearMetaKeyStates";
                case 19:
                    return "performPrivateCommand";
                case 20:
                    return "setComposingRegion";
                case 21:
                    return "getSelectedText";
                case 22:
                    return "requestUpdateCursorAnchorInfo";
                case 23:
                    return "commitContent";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.view.inputmethod.ExtractedTextRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.view.inputmethod.CompletionInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.view.inputmethod.CorrectionInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.view.KeyEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v11, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v15, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v42 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v54 */
        /* JADX WARNING: type inference failed for: r1v55 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r12, android.os.Parcel r13, android.os.Parcel r14, int r15) throws android.os.RemoteException {
            /*
                r11 = this;
                java.lang.String r0 = "com.android.internal.view.IInputContext"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r12 == r1) goto L_0x01f8
                r1 = 0
                switch(r12) {
                    case 1: goto L_0x01dd;
                    case 2: goto L_0x01c2;
                    case 3: goto L_0x01ab;
                    case 4: goto L_0x0184;
                    case 5: goto L_0x0175;
                    case 6: goto L_0x0166;
                    case 7: goto L_0x014b;
                    case 8: goto L_0x0144;
                    case 9: goto L_0x0129;
                    case 10: goto L_0x0112;
                    case 11: goto L_0x00fb;
                    case 12: goto L_0x00ec;
                    case 13: goto L_0x00e1;
                    case 14: goto L_0x00d6;
                    case 15: goto L_0x00cf;
                    case 16: goto L_0x00c8;
                    case 17: goto L_0x00b1;
                    case 18: goto L_0x00a6;
                    case 19: goto L_0x008b;
                    case 20: goto L_0x007c;
                    case 21: goto L_0x0065;
                    case 22: goto L_0x004e;
                    case 23: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r12, r13, r14, r15)
                return r1
            L_0x0011:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0024
                android.os.Parcelable$Creator<android.view.inputmethod.InputContentInfo> r3 = android.view.inputmethod.InputContentInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.view.inputmethod.InputContentInfo r3 = (android.view.inputmethod.InputContentInfo) r3
                r5 = r3
                goto L_0x0025
            L_0x0024:
                r5 = r1
            L_0x0025:
                int r3 = r13.readInt()
                int r4 = r13.readInt()
                if (r4 == 0) goto L_0x0039
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0037:
                r7 = r1
                goto L_0x003a
            L_0x0039:
                goto L_0x0037
            L_0x003a:
                int r1 = r13.readInt()
                android.os.IBinder r4 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r10 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r4)
                r4 = r11
                r6 = r3
                r8 = r1
                r9 = r10
                r4.commitContent(r5, r6, r7, r8, r9)
                return r2
            L_0x004e:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                android.os.IBinder r4 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r4 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r4)
                r11.requestUpdateCursorAnchorInfo(r1, r3, r4)
                return r2
            L_0x0065:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                android.os.IBinder r4 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r4 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r4)
                r11.getSelectedText(r1, r3, r4)
                return r2
            L_0x007c:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                r11.setComposingRegion(r1, r3)
                return r2
            L_0x008b:
                r13.enforceInterface(r0)
                java.lang.String r3 = r13.readString()
                int r4 = r13.readInt()
                if (r4 == 0) goto L_0x00a1
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00a2
            L_0x00a1:
            L_0x00a2:
                r11.performPrivateCommand(r3, r1)
                return r2
            L_0x00a6:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                r11.clearMetaKeyStates(r1)
                return r2
            L_0x00b1:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x00c3
                android.os.Parcelable$Creator<android.view.KeyEvent> r1 = android.view.KeyEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.view.KeyEvent r1 = (android.view.KeyEvent) r1
                goto L_0x00c4
            L_0x00c3:
            L_0x00c4:
                r11.sendKeyEvent(r1)
                return r2
            L_0x00c8:
                r13.enforceInterface(r0)
                r11.endBatchEdit()
                return r2
            L_0x00cf:
                r13.enforceInterface(r0)
                r11.beginBatchEdit()
                return r2
            L_0x00d6:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                r11.performContextMenuAction(r1)
                return r2
            L_0x00e1:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                r11.performEditorAction(r1)
                return r2
            L_0x00ec:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                r11.setSelection(r1, r3)
                return r2
            L_0x00fb:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x010d
                android.os.Parcelable$Creator<android.view.inputmethod.CorrectionInfo> r1 = android.view.inputmethod.CorrectionInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.view.inputmethod.CorrectionInfo r1 = (android.view.inputmethod.CorrectionInfo) r1
                goto L_0x010e
            L_0x010d:
            L_0x010e:
                r11.commitCorrection(r1)
                return r2
            L_0x0112:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0124
                android.os.Parcelable$Creator<android.view.inputmethod.CompletionInfo> r1 = android.view.inputmethod.CompletionInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.view.inputmethod.CompletionInfo r1 = (android.view.inputmethod.CompletionInfo) r1
                goto L_0x0125
            L_0x0124:
            L_0x0125:
                r11.commitCompletion(r1)
                return r2
            L_0x0129:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x013b
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x013c
            L_0x013b:
            L_0x013c:
                int r3 = r13.readInt()
                r11.commitText(r1, r3)
                return r2
            L_0x0144:
                r13.enforceInterface(r0)
                r11.finishComposingText()
                return r2
            L_0x014b:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x015d
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x015e
            L_0x015d:
            L_0x015e:
                int r3 = r13.readInt()
                r11.setComposingText(r1, r3)
                return r2
            L_0x0166:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                r11.deleteSurroundingTextInCodePoints(r1, r3)
                return r2
            L_0x0175:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                r11.deleteSurroundingText(r1, r3)
                return r2
            L_0x0184:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0196
                android.os.Parcelable$Creator<android.view.inputmethod.ExtractedTextRequest> r1 = android.view.inputmethod.ExtractedTextRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.view.inputmethod.ExtractedTextRequest r1 = (android.view.inputmethod.ExtractedTextRequest) r1
                goto L_0x0197
            L_0x0196:
            L_0x0197:
                int r3 = r13.readInt()
                int r4 = r13.readInt()
                android.os.IBinder r5 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r5 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r5)
                r11.getExtractedText(r1, r3, r4, r5)
                return r2
            L_0x01ab:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                android.os.IBinder r4 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r4 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r4)
                r11.getCursorCapsMode(r1, r3, r4)
                return r2
            L_0x01c2:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                int r4 = r13.readInt()
                android.os.IBinder r5 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r5 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r5)
                r11.getTextAfterCursor(r1, r3, r4, r5)
                return r2
            L_0x01dd:
                r13.enforceInterface(r0)
                int r1 = r13.readInt()
                int r3 = r13.readInt()
                int r4 = r13.readInt()
                android.os.IBinder r5 = r13.readStrongBinder()
                com.android.internal.view.IInputContextCallback r5 = com.android.internal.view.IInputContextCallback.Stub.asInterface(r5)
                r11.getTextBeforeCursor(r1, r3, r4, r5)
                return r2
            L_0x01f8:
                r14.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.IInputContext.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputContext {
            public static IInputContext sDefaultImpl;
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

            public void getTextBeforeCursor(int length, int flags, int seq, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(length);
                    _data.writeInt(flags);
                    _data.writeInt(seq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getTextBeforeCursor(length, flags, seq, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getTextAfterCursor(int length, int flags, int seq, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(length);
                    _data.writeInt(flags);
                    _data.writeInt(seq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getTextAfterCursor(length, flags, seq, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getCursorCapsMode(int reqModes, int seq, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reqModes);
                    _data.writeInt(seq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getCursorCapsMode(reqModes, seq, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getExtractedText(ExtractedTextRequest request, int flags, int seq, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    _data.writeInt(seq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getExtractedText(request, flags, seq, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void deleteSurroundingText(int beforeLength, int afterLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(beforeLength);
                    _data.writeInt(afterLength);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().deleteSurroundingText(beforeLength, afterLength);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(beforeLength);
                    _data.writeInt(afterLength);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().deleteSurroundingTextInCodePoints(beforeLength, afterLength);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setComposingText(CharSequence text, int newCursorPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (text != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(text, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(newCursorPosition);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setComposingText(text, newCursorPosition);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void finishComposingText() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().finishComposingText();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void commitText(CharSequence text, int newCursorPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (text != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(text, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(newCursorPosition);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().commitText(text, newCursorPosition);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void commitCompletion(CompletionInfo completion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (completion != null) {
                        _data.writeInt(1);
                        completion.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().commitCompletion(completion);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void commitCorrection(CorrectionInfo correction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (correction != null) {
                        _data.writeInt(1);
                        correction.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().commitCorrection(correction);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSelection(int start, int end) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(start);
                    _data.writeInt(end);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSelection(start, end);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void performEditorAction(int actionCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(actionCode);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().performEditorAction(actionCode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void performContextMenuAction(int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().performContextMenuAction(id);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void beginBatchEdit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().beginBatchEdit();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void endBatchEdit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().endBatchEdit();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendKeyEvent(KeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendKeyEvent(event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void clearMetaKeyStates(int states) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(states);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clearMetaKeyStates(states);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void performPrivateCommand(String action, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().performPrivateCommand(action, data);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setComposingRegion(int start, int end) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(start);
                    _data.writeInt(end);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setComposingRegion(start, end);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getSelectedText(int flags, int seq, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(seq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getSelectedText(flags, seq, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestUpdateCursorAnchorInfo(int cursorUpdateMode, int seq, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cursorUpdateMode);
                    _data.writeInt(seq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestUpdateCursorAnchorInfo(cursorUpdateMode, seq, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void commitContent(InputContentInfo inputContentInfo, int flags, Bundle opts, int sec, IInputContextCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputContentInfo != null) {
                        _data.writeInt(1);
                        inputContentInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (opts != null) {
                        _data.writeInt(1);
                        opts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sec);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().commitContent(inputContentInfo, flags, opts, sec, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputContext impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputContext getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
