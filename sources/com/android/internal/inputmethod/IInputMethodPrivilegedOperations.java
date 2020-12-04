package com.android.internal.inputmethod;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.IInputContentUriToken;

public interface IInputMethodPrivilegedOperations extends IInterface {
    void applyImeVisibility(boolean z) throws RemoteException;

    IInputContentUriToken createInputContentUriToken(Uri uri, String str) throws RemoteException;

    void hideMySoftInput(int i) throws RemoteException;

    void notifyUserAction() throws RemoteException;

    void reportFullscreenMode(boolean z) throws RemoteException;

    void reportPreRendered(EditorInfo editorInfo) throws RemoteException;

    void reportStartInput(IBinder iBinder) throws RemoteException;

    void setImeWindowStatus(int i, int i2) throws RemoteException;

    void setInputMethod(String str) throws RemoteException;

    void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype) throws RemoteException;

    boolean shouldOfferSwitchingToNextInputMethod() throws RemoteException;

    void showMySoftInput(int i) throws RemoteException;

    boolean switchToNextInputMethod(boolean z) throws RemoteException;

    boolean switchToPreviousInputMethod() throws RemoteException;

    void updateStatusIcon(String str, int i) throws RemoteException;

    public static class Default implements IInputMethodPrivilegedOperations {
        public void setImeWindowStatus(int vis, int backDisposition) throws RemoteException {
        }

        public void reportStartInput(IBinder startInputToken) throws RemoteException {
        }

        public IInputContentUriToken createInputContentUriToken(Uri contentUri, String packageName) throws RemoteException {
            return null;
        }

        public void reportFullscreenMode(boolean fullscreen) throws RemoteException {
        }

        public void setInputMethod(String id) throws RemoteException {
        }

        public void setInputMethodAndSubtype(String id, InputMethodSubtype subtype) throws RemoteException {
        }

        public void hideMySoftInput(int flags) throws RemoteException {
        }

        public void showMySoftInput(int flags) throws RemoteException {
        }

        public void updateStatusIcon(String packageName, int iconId) throws RemoteException {
        }

        public boolean switchToPreviousInputMethod() throws RemoteException {
            return false;
        }

        public boolean switchToNextInputMethod(boolean onlyCurrentIme) throws RemoteException {
            return false;
        }

        public boolean shouldOfferSwitchingToNextInputMethod() throws RemoteException {
            return false;
        }

        public void notifyUserAction() throws RemoteException {
        }

        public void reportPreRendered(EditorInfo info) throws RemoteException {
        }

        public void applyImeVisibility(boolean setVisible) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputMethodPrivilegedOperations {
        private static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodPrivilegedOperations";
        static final int TRANSACTION_applyImeVisibility = 15;
        static final int TRANSACTION_createInputContentUriToken = 3;
        static final int TRANSACTION_hideMySoftInput = 7;
        static final int TRANSACTION_notifyUserAction = 13;
        static final int TRANSACTION_reportFullscreenMode = 4;
        static final int TRANSACTION_reportPreRendered = 14;
        static final int TRANSACTION_reportStartInput = 2;
        static final int TRANSACTION_setImeWindowStatus = 1;
        static final int TRANSACTION_setInputMethod = 5;
        static final int TRANSACTION_setInputMethodAndSubtype = 6;
        static final int TRANSACTION_shouldOfferSwitchingToNextInputMethod = 12;
        static final int TRANSACTION_showMySoftInput = 8;
        static final int TRANSACTION_switchToNextInputMethod = 11;
        static final int TRANSACTION_switchToPreviousInputMethod = 10;
        static final int TRANSACTION_updateStatusIcon = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodPrivilegedOperations asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputMethodPrivilegedOperations)) {
                return new Proxy(obj);
            }
            return (IInputMethodPrivilegedOperations) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setImeWindowStatus";
                case 2:
                    return "reportStartInput";
                case 3:
                    return "createInputContentUriToken";
                case 4:
                    return "reportFullscreenMode";
                case 5:
                    return "setInputMethod";
                case 6:
                    return "setInputMethodAndSubtype";
                case 7:
                    return "hideMySoftInput";
                case 8:
                    return "showMySoftInput";
                case 9:
                    return "updateStatusIcon";
                case 10:
                    return "switchToPreviousInputMethod";
                case 11:
                    return "switchToNextInputMethod";
                case 12:
                    return "shouldOfferSwitchingToNextInputMethod";
                case 13:
                    return "notifyUserAction";
                case 14:
                    return "reportPreRendered";
                case 15:
                    return "applyImeVisibility";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.view.inputmethod.InputMethodSubtype} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v2, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v12 */
        /* JADX WARNING: type inference failed for: r3v16 */
        /* JADX WARNING: type inference failed for: r3v17 */
        /* JADX WARNING: type inference failed for: r3v18 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.internal.inputmethod.IInputMethodPrivilegedOperations"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0131
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x011f;
                    case 2: goto L_0x0111;
                    case 3: goto L_0x00e8;
                    case 4: goto L_0x00d6;
                    case 5: goto L_0x00c8;
                    case 6: goto L_0x00aa;
                    case 7: goto L_0x009c;
                    case 8: goto L_0x008e;
                    case 9: goto L_0x007c;
                    case 10: goto L_0x006e;
                    case 11: goto L_0x0058;
                    case 12: goto L_0x004a;
                    case 13: goto L_0x0040;
                    case 14: goto L_0x0024;
                    case 15: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x001d
                r1 = r2
            L_0x001d:
                r6.applyImeVisibility(r1)
                r9.writeNoException()
                return r2
            L_0x0024:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0037
                android.os.Parcelable$Creator<android.view.inputmethod.EditorInfo> r1 = android.view.inputmethod.EditorInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.view.inputmethod.EditorInfo r3 = (android.view.inputmethod.EditorInfo) r3
                goto L_0x0038
            L_0x0037:
            L_0x0038:
                r1 = r3
                r6.reportPreRendered(r1)
                r9.writeNoException()
                return r2
            L_0x0040:
                r8.enforceInterface(r0)
                r6.notifyUserAction()
                r9.writeNoException()
                return r2
            L_0x004a:
                r8.enforceInterface(r0)
                boolean r1 = r6.shouldOfferSwitchingToNextInputMethod()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x0058:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0063
                r1 = r2
            L_0x0063:
                boolean r3 = r6.switchToNextInputMethod(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x006e:
                r8.enforceInterface(r0)
                boolean r1 = r6.switchToPreviousInputMethod()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x007c:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.updateStatusIcon(r1, r3)
                r9.writeNoException()
                return r2
            L_0x008e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.showMySoftInput(r1)
                r9.writeNoException()
                return r2
            L_0x009c:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.hideMySoftInput(r1)
                r9.writeNoException()
                return r2
            L_0x00aa:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00c0
                android.os.Parcelable$Creator<android.view.inputmethod.InputMethodSubtype> r3 = android.view.inputmethod.InputMethodSubtype.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.view.inputmethod.InputMethodSubtype r3 = (android.view.inputmethod.InputMethodSubtype) r3
                goto L_0x00c1
            L_0x00c0:
            L_0x00c1:
                r6.setInputMethodAndSubtype(r1, r3)
                r9.writeNoException()
                return r2
            L_0x00c8:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setInputMethod(r1)
                r9.writeNoException()
                return r2
            L_0x00d6:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00e1
                r1 = r2
            L_0x00e1:
                r6.reportFullscreenMode(r1)
                r9.writeNoException()
                return r2
            L_0x00e8:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00fa
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x00fb
            L_0x00fa:
                r1 = r3
            L_0x00fb:
                java.lang.String r4 = r8.readString()
                com.android.internal.inputmethod.IInputContentUriToken r5 = r6.createInputContentUriToken(r1, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x010d
                android.os.IBinder r3 = r5.asBinder()
            L_0x010d:
                r9.writeStrongBinder(r3)
                return r2
            L_0x0111:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                r6.reportStartInput(r1)
                r9.writeNoException()
                return r2
            L_0x011f:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.setImeWindowStatus(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0131:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.inputmethod.IInputMethodPrivilegedOperations.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputMethodPrivilegedOperations {
            public static IInputMethodPrivilegedOperations sDefaultImpl;
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

            public void setImeWindowStatus(int vis, int backDisposition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vis);
                    _data.writeInt(backDisposition);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setImeWindowStatus(vis, backDisposition);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportStartInput(IBinder startInputToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(startInputToken);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportStartInput(startInputToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IInputContentUriToken createInputContentUriToken(Uri contentUri, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (contentUri != null) {
                        _data.writeInt(1);
                        contentUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createInputContentUriToken(contentUri, packageName);
                    }
                    _reply.readException();
                    IInputContentUriToken _result = IInputContentUriToken.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportFullscreenMode(boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fullscreen);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportFullscreenMode(fullscreen);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInputMethod(String id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInputMethod(id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInputMethodAndSubtype(String id, InputMethodSubtype subtype) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    if (subtype != null) {
                        _data.writeInt(1);
                        subtype.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInputMethodAndSubtype(id, subtype);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void hideMySoftInput(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().hideMySoftInput(flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showMySoftInput(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showMySoftInput(flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateStatusIcon(String packageName, int iconId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(iconId);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateStatusIcon(packageName, iconId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean switchToPreviousInputMethod() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchToPreviousInputMethod();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean switchToNextInputMethod(boolean onlyCurrentIme) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(onlyCurrentIme);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchToNextInputMethod(onlyCurrentIme);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldOfferSwitchingToNextInputMethod() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldOfferSwitchingToNextInputMethod();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyUserAction() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyUserAction();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportPreRendered(EditorInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportPreRendered(info);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyImeVisibility(boolean setVisible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(setVisible);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyImeVisibility(setVisible);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputMethodPrivilegedOperations impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputMethodPrivilegedOperations getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
