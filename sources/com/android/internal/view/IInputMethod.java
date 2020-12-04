package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.InputChannel;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;

public interface IInputMethod extends IInterface {
    void bindInput(InputBinding inputBinding) throws RemoteException;

    void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException;

    void createSession(InputChannel inputChannel, IInputSessionCallback iInputSessionCallback) throws RemoteException;

    void hideSoftInput(int i, ResultReceiver resultReceiver) throws RemoteException;

    void initializeInternal(IBinder iBinder, int i, IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) throws RemoteException;

    void revokeSession(IInputMethodSession iInputMethodSession) throws RemoteException;

    void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean z) throws RemoteException;

    void showSoftInput(int i, ResultReceiver resultReceiver) throws RemoteException;

    void startInput(IBinder iBinder, IInputContext iInputContext, int i, EditorInfo editorInfo, boolean z, boolean z2) throws RemoteException;

    void unbindInput() throws RemoteException;

    public static class Default implements IInputMethod {
        public void initializeInternal(IBinder token, int displayId, IInputMethodPrivilegedOperations privOps) throws RemoteException {
        }

        public void bindInput(InputBinding binding) throws RemoteException {
        }

        public void unbindInput() throws RemoteException {
        }

        public void startInput(IBinder startInputToken, IInputContext inputContext, int missingMethods, EditorInfo attribute, boolean restarting, boolean preRenderImeViews) throws RemoteException {
        }

        public void createSession(InputChannel channel, IInputSessionCallback callback) throws RemoteException {
        }

        public void setSessionEnabled(IInputMethodSession session, boolean enabled) throws RemoteException {
        }

        public void revokeSession(IInputMethodSession session) throws RemoteException {
        }

        public void showSoftInput(int flags, ResultReceiver resultReceiver) throws RemoteException {
        }

        public void hideSoftInput(int flags, ResultReceiver resultReceiver) throws RemoteException {
        }

        public void changeInputMethodSubtype(InputMethodSubtype subtype) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputMethod {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethod";
        static final int TRANSACTION_bindInput = 2;
        static final int TRANSACTION_changeInputMethodSubtype = 10;
        static final int TRANSACTION_createSession = 5;
        static final int TRANSACTION_hideSoftInput = 9;
        static final int TRANSACTION_initializeInternal = 1;
        static final int TRANSACTION_revokeSession = 7;
        static final int TRANSACTION_setSessionEnabled = 6;
        static final int TRANSACTION_showSoftInput = 8;
        static final int TRANSACTION_startInput = 4;
        static final int TRANSACTION_unbindInput = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethod asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputMethod)) {
                return new Proxy(obj);
            }
            return (IInputMethod) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "initializeInternal";
                case 2:
                    return "bindInput";
                case 3:
                    return "unbindInput";
                case 4:
                    return "startInput";
                case 5:
                    return "createSession";
                case 6:
                    return "setSessionEnabled";
                case 7:
                    return "revokeSession";
                case 8:
                    return "showSoftInput";
                case 9:
                    return "hideSoftInput";
                case 10:
                    return "changeInputMethodSubtype";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.view.inputmethod.InputBinding} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: android.view.inputmethod.EditorInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.view.InputChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: android.view.inputmethod.InputMethodSubtype} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v31, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v32, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.os.ResultReceiver} */
        /* JADX WARNING: type inference failed for: r1v4, types: [android.view.inputmethod.InputBinding] */
        /* JADX WARNING: type inference failed for: r1v11, types: [android.view.inputmethod.EditorInfo] */
        /* JADX WARNING: type inference failed for: r1v16, types: [android.view.InputChannel] */
        /* JADX WARNING: type inference failed for: r1v29, types: [android.view.inputmethod.InputMethodSubtype] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r7 = r15
                r8 = r16
                r9 = r17
                java.lang.String r10 = "com.android.internal.view.IInputMethod"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x0122
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x010b;
                    case 2: goto L_0x00f2;
                    case 3: goto L_0x00eb;
                    case 4: goto L_0x00ad;
                    case 5: goto L_0x008c;
                    case 6: goto L_0x0075;
                    case 7: goto L_0x0066;
                    case 8: goto L_0x004b;
                    case 9: goto L_0x0030;
                    case 10: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0017:
                r9.enforceInterface(r10)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x002a
                android.os.Parcelable$Creator<android.view.inputmethod.InputMethodSubtype> r0 = android.view.inputmethod.InputMethodSubtype.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r1 = r0
                android.view.inputmethod.InputMethodSubtype r1 = (android.view.inputmethod.InputMethodSubtype) r1
                goto L_0x002b
            L_0x002a:
            L_0x002b:
                r0 = r1
                r15.changeInputMethodSubtype(r0)
                return r11
            L_0x0030:
                r9.enforceInterface(r10)
                int r0 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0046
                android.os.Parcelable$Creator<android.os.ResultReceiver> r1 = android.os.ResultReceiver.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ResultReceiver r1 = (android.os.ResultReceiver) r1
                goto L_0x0047
            L_0x0046:
            L_0x0047:
                r15.hideSoftInput(r0, r1)
                return r11
            L_0x004b:
                r9.enforceInterface(r10)
                int r0 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0061
                android.os.Parcelable$Creator<android.os.ResultReceiver> r1 = android.os.ResultReceiver.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ResultReceiver r1 = (android.os.ResultReceiver) r1
                goto L_0x0062
            L_0x0061:
            L_0x0062:
                r15.showSoftInput(r0, r1)
                return r11
            L_0x0066:
                r9.enforceInterface(r10)
                android.os.IBinder r0 = r17.readStrongBinder()
                com.android.internal.view.IInputMethodSession r0 = com.android.internal.view.IInputMethodSession.Stub.asInterface(r0)
                r15.revokeSession(r0)
                return r11
            L_0x0075:
                r9.enforceInterface(r10)
                android.os.IBinder r1 = r17.readStrongBinder()
                com.android.internal.view.IInputMethodSession r1 = com.android.internal.view.IInputMethodSession.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0088
                r0 = r11
            L_0x0088:
                r15.setSessionEnabled(r1, r0)
                return r11
            L_0x008c:
                r9.enforceInterface(r10)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x009f
                android.os.Parcelable$Creator<android.view.InputChannel> r0 = android.view.InputChannel.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r1 = r0
                android.view.InputChannel r1 = (android.view.InputChannel) r1
                goto L_0x00a0
            L_0x009f:
            L_0x00a0:
                r0 = r1
                android.os.IBinder r1 = r17.readStrongBinder()
                com.android.internal.view.IInputSessionCallback r1 = com.android.internal.view.IInputSessionCallback.Stub.asInterface(r1)
                r15.createSession(r0, r1)
                return r11
            L_0x00ad:
                r9.enforceInterface(r10)
                android.os.IBinder r12 = r17.readStrongBinder()
                android.os.IBinder r2 = r17.readStrongBinder()
                com.android.internal.view.IInputContext r13 = com.android.internal.view.IInputContext.Stub.asInterface(r2)
                int r14 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00d0
                android.os.Parcelable$Creator<android.view.inputmethod.EditorInfo> r1 = android.view.inputmethod.EditorInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.view.inputmethod.EditorInfo r1 = (android.view.inputmethod.EditorInfo) r1
            L_0x00ce:
                r4 = r1
                goto L_0x00d1
            L_0x00d0:
                goto L_0x00ce
            L_0x00d1:
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00d9
                r5 = r11
                goto L_0x00da
            L_0x00d9:
                r5 = r0
            L_0x00da:
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00e2
                r6 = r11
                goto L_0x00e3
            L_0x00e2:
                r6 = r0
            L_0x00e3:
                r0 = r15
                r1 = r12
                r2 = r13
                r3 = r14
                r0.startInput(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x00eb:
                r9.enforceInterface(r10)
                r15.unbindInput()
                return r11
            L_0x00f2:
                r9.enforceInterface(r10)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0105
                android.os.Parcelable$Creator<android.view.inputmethod.InputBinding> r0 = android.view.inputmethod.InputBinding.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r1 = r0
                android.view.inputmethod.InputBinding r1 = (android.view.inputmethod.InputBinding) r1
                goto L_0x0106
            L_0x0105:
            L_0x0106:
                r0 = r1
                r15.bindInput(r0)
                return r11
            L_0x010b:
                r9.enforceInterface(r10)
                android.os.IBinder r0 = r17.readStrongBinder()
                int r1 = r17.readInt()
                android.os.IBinder r2 = r17.readStrongBinder()
                com.android.internal.inputmethod.IInputMethodPrivilegedOperations r2 = com.android.internal.inputmethod.IInputMethodPrivilegedOperations.Stub.asInterface(r2)
                r15.initializeInternal(r0, r1, r2)
                return r11
            L_0x0122:
                r0 = r18
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.IInputMethod.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputMethod {
            public static IInputMethod sDefaultImpl;
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

            public void initializeInternal(IBinder token, int displayId, IInputMethodPrivilegedOperations privOps) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(privOps != null ? privOps.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().initializeInternal(token, displayId, privOps);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void bindInput(InputBinding binding) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (binding != null) {
                        _data.writeInt(1);
                        binding.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().bindInput(binding);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unbindInput() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unbindInput();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startInput(IBinder startInputToken, IInputContext inputContext, int missingMethods, EditorInfo attribute, boolean restarting, boolean preRenderImeViews) throws RemoteException {
                EditorInfo editorInfo = attribute;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(startInputToken);
                        _data.writeStrongBinder(inputContext != null ? inputContext.asBinder() : null);
                        try {
                            _data.writeInt(missingMethods);
                            if (editorInfo != null) {
                                _data.writeInt(1);
                                editorInfo.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            boolean z = restarting;
                            boolean z2 = preRenderImeViews;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i = missingMethods;
                        boolean z3 = restarting;
                        boolean z22 = preRenderImeViews;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(restarting ? 1 : 0);
                        try {
                            _data.writeInt(preRenderImeViews ? 1 : 0);
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().startInput(startInputToken, inputContext, missingMethods, attribute, restarting, preRenderImeViews);
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        boolean z222 = preRenderImeViews;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    IBinder iBinder = startInputToken;
                    int i2 = missingMethods;
                    boolean z32 = restarting;
                    boolean z2222 = preRenderImeViews;
                    _data.recycle();
                    throw th;
                }
            }

            public void createSession(InputChannel channel, IInputSessionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createSession(channel, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSessionEnabled(IInputMethodSession session, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSessionEnabled(session, enabled);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void revokeSession(IInputMethodSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().revokeSession(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showSoftInput(int flags, ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (resultReceiver != null) {
                        _data.writeInt(1);
                        resultReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showSoftInput(flags, resultReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void hideSoftInput(int flags, ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (resultReceiver != null) {
                        _data.writeInt(1);
                        resultReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().hideSoftInput(flags, resultReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void changeInputMethodSubtype(InputMethodSubtype subtype) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (subtype != null) {
                        _data.writeInt(1);
                        subtype.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().changeInputMethodSubtype(subtype);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputMethod impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputMethod getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
