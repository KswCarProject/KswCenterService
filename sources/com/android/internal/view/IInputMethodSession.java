package com.android.internal.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;

public interface IInputMethodSession extends IInterface {
    void appPrivateCommand(String str, Bundle bundle) throws RemoteException;

    void displayCompletions(CompletionInfo[] completionInfoArr) throws RemoteException;

    void finishSession() throws RemoteException;

    void notifyImeHidden() throws RemoteException;

    void toggleSoftInput(int i, int i2) throws RemoteException;

    void updateCursor(Rect rect) throws RemoteException;

    void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException;

    void updateExtractedText(int i, ExtractedText extractedText) throws RemoteException;

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void viewClicked(boolean z) throws RemoteException;

    public static class Default implements IInputMethodSession {
        public void updateExtractedText(int token, ExtractedText text) throws RemoteException {
        }

        public void updateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) throws RemoteException {
        }

        public void viewClicked(boolean focusChanged) throws RemoteException {
        }

        public void updateCursor(Rect newCursor) throws RemoteException {
        }

        public void displayCompletions(CompletionInfo[] completions) throws RemoteException {
        }

        public void appPrivateCommand(String action, Bundle data) throws RemoteException {
        }

        public void toggleSoftInput(int showFlags, int hideFlags) throws RemoteException {
        }

        public void finishSession() throws RemoteException {
        }

        public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
        }

        public void notifyImeHidden() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputMethodSession {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethodSession";
        static final int TRANSACTION_appPrivateCommand = 6;
        static final int TRANSACTION_displayCompletions = 5;
        static final int TRANSACTION_finishSession = 8;
        static final int TRANSACTION_notifyImeHidden = 10;
        static final int TRANSACTION_toggleSoftInput = 7;
        static final int TRANSACTION_updateCursor = 4;
        static final int TRANSACTION_updateCursorAnchorInfo = 9;
        static final int TRANSACTION_updateExtractedText = 1;
        static final int TRANSACTION_updateSelection = 2;
        static final int TRANSACTION_viewClicked = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputMethodSession)) {
                return new Proxy(obj);
            }
            return (IInputMethodSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "updateExtractedText";
                case 2:
                    return "updateSelection";
                case 3:
                    return "viewClicked";
                case 4:
                    return "updateCursor";
                case 5:
                    return "displayCompletions";
                case 6:
                    return "appPrivateCommand";
                case 7:
                    return "toggleSoftInput";
                case 8:
                    return "finishSession";
                case 9:
                    return "updateCursorAnchorInfo";
                case 10:
                    return "notifyImeHidden";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.view.inputmethod.ExtractedText} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.view.inputmethod.CursorAnchorInfo} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: type inference failed for: r0v30 */
        /* JADX WARNING: type inference failed for: r0v31 */
        /* JADX WARNING: type inference failed for: r0v32 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "com.android.internal.view.IInputMethodSession"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x00e0
                r0 = 0
                switch(r8) {
                    case 1: goto L_0x00c5;
                    case 2: goto L_0x009c;
                    case 3: goto L_0x008c;
                    case 4: goto L_0x0075;
                    case 5: goto L_0x0066;
                    case 6: goto L_0x004b;
                    case 7: goto L_0x003c;
                    case 8: goto L_0x0035;
                    case 9: goto L_0x001e;
                    case 10: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x0017:
                r9.enforceInterface(r10)
                r18.notifyImeHidden()
                return r11
            L_0x001e:
                r9.enforceInterface(r10)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0030
                android.os.Parcelable$Creator<android.view.inputmethod.CursorAnchorInfo> r0 = android.view.inputmethod.CursorAnchorInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.inputmethod.CursorAnchorInfo r0 = (android.view.inputmethod.CursorAnchorInfo) r0
                goto L_0x0031
            L_0x0030:
            L_0x0031:
                r7.updateCursorAnchorInfo(r0)
                return r11
            L_0x0035:
                r9.enforceInterface(r10)
                r18.finishSession()
                return r11
            L_0x003c:
                r9.enforceInterface(r10)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                r7.toggleSoftInput(r0, r1)
                return r11
            L_0x004b:
                r9.enforceInterface(r10)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0061
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0062
            L_0x0061:
            L_0x0062:
                r7.appPrivateCommand(r1, r0)
                return r11
            L_0x0066:
                r9.enforceInterface(r10)
                android.os.Parcelable$Creator<android.view.inputmethod.CompletionInfo> r0 = android.view.inputmethod.CompletionInfo.CREATOR
                java.lang.Object[] r0 = r9.createTypedArray(r0)
                android.view.inputmethod.CompletionInfo[] r0 = (android.view.inputmethod.CompletionInfo[]) r0
                r7.displayCompletions(r0)
                return r11
            L_0x0075:
                r9.enforceInterface(r10)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0087
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x0088
            L_0x0087:
            L_0x0088:
                r7.updateCursor(r0)
                return r11
            L_0x008c:
                r9.enforceInterface(r10)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0097
                r0 = r11
                goto L_0x0098
            L_0x0097:
                r0 = 0
            L_0x0098:
                r7.viewClicked(r0)
                return r11
            L_0x009c:
                r9.enforceInterface(r10)
                int r12 = r20.readInt()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                int r15 = r20.readInt()
                int r16 = r20.readInt()
                int r17 = r20.readInt()
                r0 = r18
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r6 = r17
                r0.updateSelection(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x00c5:
                r9.enforceInterface(r10)
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00db
                android.os.Parcelable$Creator<android.view.inputmethod.ExtractedText> r0 = android.view.inputmethod.ExtractedText.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.inputmethod.ExtractedText r0 = (android.view.inputmethod.ExtractedText) r0
                goto L_0x00dc
            L_0x00db:
            L_0x00dc:
                r7.updateExtractedText(r1, r0)
                return r11
            L_0x00e0:
                r0 = r21
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.IInputMethodSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputMethodSession {
            public static IInputMethodSession sDefaultImpl;
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

            public void updateExtractedText(int token, ExtractedText text) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    if (text != null) {
                        _data.writeInt(1);
                        text.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateExtractedText(token, text);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(oldSelStart);
                    } catch (Throwable th) {
                        th = th;
                        int i = oldSelEnd;
                        int i2 = newSelStart;
                        int i3 = newSelEnd;
                        int i4 = candidatesStart;
                        int i5 = candidatesEnd;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(oldSelEnd);
                        try {
                            _data.writeInt(newSelStart);
                            try {
                                _data.writeInt(newSelEnd);
                            } catch (Throwable th2) {
                                th = th2;
                                int i42 = candidatesStart;
                                int i52 = candidatesEnd;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i32 = newSelEnd;
                            int i422 = candidatesStart;
                            int i522 = candidatesEnd;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i22 = newSelStart;
                        int i322 = newSelEnd;
                        int i4222 = candidatesStart;
                        int i5222 = candidatesEnd;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(candidatesStart);
                        try {
                            _data.writeInt(candidatesEnd);
                            try {
                                if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().updateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
                                _data.recycle();
                            } catch (Throwable th5) {
                                th = th5;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        int i52222 = candidatesEnd;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    int i6 = oldSelStart;
                    int i7 = oldSelEnd;
                    int i222 = newSelStart;
                    int i3222 = newSelEnd;
                    int i42222 = candidatesStart;
                    int i522222 = candidatesEnd;
                    _data.recycle();
                    throw th;
                }
            }

            public void viewClicked(boolean focusChanged) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(focusChanged);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().viewClicked(focusChanged);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateCursor(Rect newCursor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (newCursor != null) {
                        _data.writeInt(1);
                        newCursor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateCursor(newCursor);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void displayCompletions(CompletionInfo[] completions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(completions, 0);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().displayCompletions(completions);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void appPrivateCommand(String action, Bundle data) throws RemoteException {
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
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().appPrivateCommand(action, data);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void toggleSoftInput(int showFlags, int hideFlags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showFlags);
                    _data.writeInt(hideFlags);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().toggleSoftInput(showFlags, hideFlags);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void finishSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().finishSession();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cursorAnchorInfo != null) {
                        _data.writeInt(1);
                        cursorAnchorInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateCursorAnchorInfo(cursorAnchorInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyImeHidden() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyImeHidden();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputMethodSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputMethodSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
