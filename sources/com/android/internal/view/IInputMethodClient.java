package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;

public interface IInputMethodClient extends IInterface {
    void applyImeVisibility(boolean z) throws RemoteException;

    void onBindMethod(InputBindResult inputBindResult) throws RemoteException;

    void onUnbindMethod(int i, int i2) throws RemoteException;

    void reportFullscreenMode(boolean z) throws RemoteException;

    void reportPreRendered(EditorInfo editorInfo) throws RemoteException;

    void setActive(boolean z, boolean z2) throws RemoteException;

    void updateActivityViewToScreenMatrix(int i, float[] fArr) throws RemoteException;

    public static class Default implements IInputMethodClient {
        public void onBindMethod(InputBindResult res) throws RemoteException {
        }

        public void onUnbindMethod(int sequence, int unbindReason) throws RemoteException {
        }

        public void setActive(boolean active, boolean fullscreen) throws RemoteException {
        }

        public void reportFullscreenMode(boolean fullscreen) throws RemoteException {
        }

        public void reportPreRendered(EditorInfo info) throws RemoteException {
        }

        public void applyImeVisibility(boolean setVisible) throws RemoteException {
        }

        public void updateActivityViewToScreenMatrix(int bindSequence, float[] matrixValues) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputMethodClient {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethodClient";
        static final int TRANSACTION_applyImeVisibility = 6;
        static final int TRANSACTION_onBindMethod = 1;
        static final int TRANSACTION_onUnbindMethod = 2;
        static final int TRANSACTION_reportFullscreenMode = 4;
        static final int TRANSACTION_reportPreRendered = 5;
        static final int TRANSACTION_setActive = 3;
        static final int TRANSACTION_updateActivityViewToScreenMatrix = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputMethodClient)) {
                return new Proxy(obj);
            }
            return (IInputMethodClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onBindMethod";
                case 2:
                    return "onUnbindMethod";
                case 3:
                    return "setActive";
                case 4:
                    return "reportFullscreenMode";
                case 5:
                    return "reportPreRendered";
                case 6:
                    return "applyImeVisibility";
                case 7:
                    return "updateActivityViewToScreenMatrix";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: com.android.internal.view.InputBindResult} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.view.inputmethod.EditorInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "com.android.internal.view.IInputMethodClient"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0096
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x007f;
                    case 2: goto L_0x0070;
                    case 3: goto L_0x0058;
                    case 4: goto L_0x0048;
                    case 5: goto L_0x0031;
                    case 6: goto L_0x0021;
                    case 7: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                float[] r3 = r7.createFloatArray()
                r5.updateActivityViewToScreenMatrix(r1, r3)
                return r2
            L_0x0021:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x002c
                r3 = r2
            L_0x002c:
                r1 = r3
                r5.applyImeVisibility(r1)
                return r2
            L_0x0031:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0043
                android.os.Parcelable$Creator<android.view.inputmethod.EditorInfo> r1 = android.view.inputmethod.EditorInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.inputmethod.EditorInfo r1 = (android.view.inputmethod.EditorInfo) r1
                goto L_0x0044
            L_0x0043:
            L_0x0044:
                r5.reportPreRendered(r1)
                return r2
            L_0x0048:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0053
                r3 = r2
            L_0x0053:
                r1 = r3
                r5.reportFullscreenMode(r1)
                return r2
            L_0x0058:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0063
                r1 = r2
                goto L_0x0064
            L_0x0063:
                r1 = r3
            L_0x0064:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x006c
                r3 = r2
            L_0x006c:
                r5.setActive(r1, r3)
                return r2
            L_0x0070:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r3 = r7.readInt()
                r5.onUnbindMethod(r1, r3)
                return r2
            L_0x007f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0091
                android.os.Parcelable$Creator<com.android.internal.view.InputBindResult> r1 = com.android.internal.view.InputBindResult.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                com.android.internal.view.InputBindResult r1 = (com.android.internal.view.InputBindResult) r1
                goto L_0x0092
            L_0x0091:
            L_0x0092:
                r5.onBindMethod(r1)
                return r2
            L_0x0096:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.IInputMethodClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputMethodClient {
            public static IInputMethodClient sDefaultImpl;
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

            public void onBindMethod(InputBindResult res) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (res != null) {
                        _data.writeInt(1);
                        res.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBindMethod(res);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onUnbindMethod(int sequence, int unbindReason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    _data.writeInt(unbindReason);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUnbindMethod(sequence, unbindReason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setActive(boolean active, boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active);
                    _data.writeInt(fullscreen);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setActive(active, fullscreen);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void reportFullscreenMode(boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fullscreen);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().reportFullscreenMode(fullscreen);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void reportPreRendered(EditorInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().reportPreRendered(info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void applyImeVisibility(boolean setVisible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(setVisible);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().applyImeVisibility(setVisible);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateActivityViewToScreenMatrix(int bindSequence, float[] matrixValues) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bindSequence);
                    _data.writeFloatArray(matrixValues);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateActivityViewToScreenMatrix(bindSequence, matrixValues);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputMethodClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputMethodClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
