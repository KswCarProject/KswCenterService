package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import java.util.List;

public interface IInputMethodManager extends IInterface {
    void addClient(IInputMethodClient iInputMethodClient, IInputContext iInputContext, int i) throws RemoteException;

    InputMethodSubtype getCurrentInputMethodSubtype() throws RemoteException;

    List<InputMethodInfo> getEnabledInputMethodList(int i) throws RemoteException;

    List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z) throws RemoteException;

    List<InputMethodInfo> getInputMethodList(int i) throws RemoteException;

    int getInputMethodWindowVisibleHeight() throws RemoteException;

    InputMethodSubtype getLastInputMethodSubtype() throws RemoteException;

    boolean hideSoftInput(IInputMethodClient iInputMethodClient, int i, ResultReceiver resultReceiver) throws RemoteException;

    boolean isInputMethodPickerShownForTest() throws RemoteException;

    void reportActivityView(IInputMethodClient iInputMethodClient, int i, float[] fArr) throws RemoteException;

    void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr) throws RemoteException;

    void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient iInputMethodClient, String str) throws RemoteException;

    void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) throws RemoteException;

    void showInputMethodPickerFromSystem(IInputMethodClient iInputMethodClient, int i, int i2) throws RemoteException;

    boolean showSoftInput(IInputMethodClient iInputMethodClient, int i, ResultReceiver resultReceiver) throws RemoteException;

    InputBindResult startInputOrWindowGainedFocus(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IInputContext iInputContext, int i5, int i6) throws RemoteException;

    public static class Default implements IInputMethodManager {
        public void addClient(IInputMethodClient client, IInputContext inputContext, int untrustedDisplayId) throws RemoteException {
        }

        public List<InputMethodInfo> getInputMethodList(int userId) throws RemoteException {
            return null;
        }

        public List<InputMethodInfo> getEnabledInputMethodList(int userId) throws RemoteException {
            return null;
        }

        public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String imiId, boolean allowsImplicitlySelectedSubtypes) throws RemoteException {
            return null;
        }

        public InputMethodSubtype getLastInputMethodSubtype() throws RemoteException {
            return null;
        }

        public boolean showSoftInput(IInputMethodClient client, int flags, ResultReceiver resultReceiver) throws RemoteException {
            return false;
        }

        public boolean hideSoftInput(IInputMethodClient client, int flags, ResultReceiver resultReceiver) throws RemoteException {
            return false;
        }

        public InputBindResult startInputOrWindowGainedFocus(int startInputReason, IInputMethodClient client, IBinder windowToken, int startInputFlags, int softInputMode, int windowFlags, EditorInfo attribute, IInputContext inputContext, int missingMethodFlags, int unverifiedTargetSdkVersion) throws RemoteException {
            return null;
        }

        public void showInputMethodPickerFromClient(IInputMethodClient client, int auxiliarySubtypeMode) throws RemoteException {
        }

        public void showInputMethodPickerFromSystem(IInputMethodClient client, int auxiliarySubtypeMode, int displayId) throws RemoteException {
        }

        public void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient client, String topId) throws RemoteException {
        }

        public boolean isInputMethodPickerShownForTest() throws RemoteException {
            return false;
        }

        public InputMethodSubtype getCurrentInputMethodSubtype() throws RemoteException {
            return null;
        }

        public void setAdditionalInputMethodSubtypes(String id, InputMethodSubtype[] subtypes) throws RemoteException {
        }

        public int getInputMethodWindowVisibleHeight() throws RemoteException {
            return 0;
        }

        public void reportActivityView(IInputMethodClient parentClient, int childDisplayId, float[] matrixValues) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputMethodManager {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethodManager";
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_getCurrentInputMethodSubtype = 13;
        static final int TRANSACTION_getEnabledInputMethodList = 3;
        static final int TRANSACTION_getEnabledInputMethodSubtypeList = 4;
        static final int TRANSACTION_getInputMethodList = 2;
        static final int TRANSACTION_getInputMethodWindowVisibleHeight = 15;
        static final int TRANSACTION_getLastInputMethodSubtype = 5;
        static final int TRANSACTION_hideSoftInput = 7;
        static final int TRANSACTION_isInputMethodPickerShownForTest = 12;
        static final int TRANSACTION_reportActivityView = 16;
        static final int TRANSACTION_setAdditionalInputMethodSubtypes = 14;
        static final int TRANSACTION_showInputMethodAndSubtypeEnablerFromClient = 11;
        static final int TRANSACTION_showInputMethodPickerFromClient = 9;
        static final int TRANSACTION_showInputMethodPickerFromSystem = 10;
        static final int TRANSACTION_showSoftInput = 6;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputMethodManager)) {
                return new Proxy(obj);
            }
            return (IInputMethodManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addClient";
                case 2:
                    return "getInputMethodList";
                case 3:
                    return "getEnabledInputMethodList";
                case 4:
                    return "getEnabledInputMethodSubtypeList";
                case 5:
                    return "getLastInputMethodSubtype";
                case 6:
                    return "showSoftInput";
                case 7:
                    return "hideSoftInput";
                case 8:
                    return "startInputOrWindowGainedFocus";
                case 9:
                    return "showInputMethodPickerFromClient";
                case 10:
                    return "showInputMethodPickerFromSystem";
                case 11:
                    return "showInputMethodAndSubtypeEnablerFromClient";
                case 12:
                    return "isInputMethodPickerShownForTest";
                case 13:
                    return "getCurrentInputMethodSubtype";
                case 14:
                    return "setAdditionalInputMethodSubtypes";
                case 15:
                    return "getInputMethodWindowVisibleHeight";
                case 16:
                    return "reportActivityView";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: android.view.inputmethod.EditorInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v37, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.os.ResultReceiver} */
        /* JADX WARNING: type inference failed for: r0v22, types: [android.view.inputmethod.EditorInfo] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r26, android.os.Parcel r27, android.os.Parcel r28, int r29) throws android.os.RemoteException {
            /*
                r25 = this;
                r11 = r25
                r12 = r26
                r13 = r27
                r14 = r28
                java.lang.String r15 = "com.android.internal.view.IInputMethodManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r12 == r0) goto L_0x01fe
                r0 = 0
                r9 = 0
                switch(r12) {
                    case 1: goto L_0x01df;
                    case 2: goto L_0x01cc;
                    case 3: goto L_0x01b9;
                    case 4: goto L_0x019d;
                    case 5: goto L_0x0184;
                    case 6: goto L_0x0159;
                    case 7: goto L_0x012e;
                    case 8: goto L_0x00c3;
                    case 9: goto L_0x00ad;
                    case 10: goto L_0x0093;
                    case 11: goto L_0x007d;
                    case 12: goto L_0x006f;
                    case 13: goto L_0x0058;
                    case 14: goto L_0x0042;
                    case 15: goto L_0x0034;
                    case 16: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r26, r27, r28, r29)
                return r0
            L_0x001a:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r0 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r0)
                int r1 = r27.readInt()
                float[] r2 = r27.createFloatArray()
                r11.reportActivityView(r0, r1, r2)
                r28.writeNoException()
                return r10
            L_0x0034:
                r13.enforceInterface(r15)
                int r0 = r25.getInputMethodWindowVisibleHeight()
                r28.writeNoException()
                r14.writeInt(r0)
                return r10
            L_0x0042:
                r13.enforceInterface(r15)
                java.lang.String r0 = r27.readString()
                android.os.Parcelable$Creator<android.view.inputmethod.InputMethodSubtype> r1 = android.view.inputmethod.InputMethodSubtype.CREATOR
                java.lang.Object[] r1 = r13.createTypedArray(r1)
                android.view.inputmethod.InputMethodSubtype[] r1 = (android.view.inputmethod.InputMethodSubtype[]) r1
                r11.setAdditionalInputMethodSubtypes(r0, r1)
                r28.writeNoException()
                return r10
            L_0x0058:
                r13.enforceInterface(r15)
                android.view.inputmethod.InputMethodSubtype r0 = r25.getCurrentInputMethodSubtype()
                r28.writeNoException()
                if (r0 == 0) goto L_0x006b
                r14.writeInt(r10)
                r0.writeToParcel(r14, r10)
                goto L_0x006e
            L_0x006b:
                r14.writeInt(r9)
            L_0x006e:
                return r10
            L_0x006f:
                r13.enforceInterface(r15)
                boolean r0 = r25.isInputMethodPickerShownForTest()
                r28.writeNoException()
                r14.writeInt(r0)
                return r10
            L_0x007d:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r0 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r0)
                java.lang.String r1 = r27.readString()
                r11.showInputMethodAndSubtypeEnablerFromClient(r0, r1)
                r28.writeNoException()
                return r10
            L_0x0093:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r0 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r0)
                int r1 = r27.readInt()
                int r2 = r27.readInt()
                r11.showInputMethodPickerFromSystem(r0, r1, r2)
                r28.writeNoException()
                return r10
            L_0x00ad:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r0 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r0)
                int r1 = r27.readInt()
                r11.showInputMethodPickerFromClient(r0, r1)
                r28.writeNoException()
                return r10
            L_0x00c3:
                r13.enforceInterface(r15)
                int r16 = r27.readInt()
                android.os.IBinder r1 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r17 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r1)
                android.os.IBinder r18 = r27.readStrongBinder()
                int r19 = r27.readInt()
                int r20 = r27.readInt()
                int r21 = r27.readInt()
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x00f2
                android.os.Parcelable$Creator<android.view.inputmethod.EditorInfo> r0 = android.view.inputmethod.EditorInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.inputmethod.EditorInfo r0 = (android.view.inputmethod.EditorInfo) r0
            L_0x00f0:
                r7 = r0
                goto L_0x00f3
            L_0x00f2:
                goto L_0x00f0
            L_0x00f3:
                android.os.IBinder r0 = r27.readStrongBinder()
                com.android.internal.view.IInputContext r22 = com.android.internal.view.IInputContext.Stub.asInterface(r0)
                int r23 = r27.readInt()
                int r24 = r27.readInt()
                r0 = r25
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r5 = r20
                r6 = r21
                r8 = r22
                r12 = r9
                r9 = r23
                r12 = r10
                r10 = r24
                com.android.internal.view.InputBindResult r0 = r0.startInputOrWindowGainedFocus(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r28.writeNoException()
                if (r0 == 0) goto L_0x0129
                r14.writeInt(r12)
                r0.writeToParcel(r14, r12)
                goto L_0x012d
            L_0x0129:
                r1 = 0
                r14.writeInt(r1)
            L_0x012d:
                return r12
            L_0x012e:
                r12 = r10
                r13.enforceInterface(r15)
                android.os.IBinder r1 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r1 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r1)
                int r2 = r27.readInt()
                int r3 = r27.readInt()
                if (r3 == 0) goto L_0x014d
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x014e
            L_0x014d:
            L_0x014e:
                boolean r3 = r11.hideSoftInput(r1, r2, r0)
                r28.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x0159:
                r12 = r10
                r13.enforceInterface(r15)
                android.os.IBinder r1 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r1 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r1)
                int r2 = r27.readInt()
                int r3 = r27.readInt()
                if (r3 == 0) goto L_0x0178
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x0179
            L_0x0178:
            L_0x0179:
                boolean r3 = r11.showSoftInput(r1, r2, r0)
                r28.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x0184:
                r12 = r10
                r13.enforceInterface(r15)
                android.view.inputmethod.InputMethodSubtype r0 = r25.getLastInputMethodSubtype()
                r28.writeNoException()
                if (r0 == 0) goto L_0x0198
                r14.writeInt(r12)
                r0.writeToParcel(r14, r12)
                goto L_0x019c
            L_0x0198:
                r1 = 0
                r14.writeInt(r1)
            L_0x019c:
                return r12
            L_0x019d:
                r1 = r9
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r27.readString()
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x01ae
                r1 = r12
            L_0x01ae:
                java.util.List r2 = r11.getEnabledInputMethodSubtypeList(r0, r1)
                r28.writeNoException()
                r14.writeTypedList(r2)
                return r12
            L_0x01b9:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                java.util.List r1 = r11.getEnabledInputMethodList(r0)
                r28.writeNoException()
                r14.writeTypedList(r1)
                return r12
            L_0x01cc:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                java.util.List r1 = r11.getInputMethodList(r0)
                r28.writeNoException()
                r14.writeTypedList(r1)
                return r12
            L_0x01df:
                r12 = r10
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                com.android.internal.view.IInputMethodClient r0 = com.android.internal.view.IInputMethodClient.Stub.asInterface(r0)
                android.os.IBinder r1 = r27.readStrongBinder()
                com.android.internal.view.IInputContext r1 = com.android.internal.view.IInputContext.Stub.asInterface(r1)
                int r2 = r27.readInt()
                r11.addClient(r0, r1, r2)
                r28.writeNoException()
                return r12
            L_0x01fe:
                r12 = r10
                r14.writeString(r15)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.IInputMethodManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputMethodManager {
            public static IInputMethodManager sDefaultImpl;
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

            public void addClient(IInputMethodClient client, IInputContext inputContext, int untrustedDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (inputContext != null) {
                        iBinder = inputContext.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(untrustedDisplayId);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addClient(client, inputContext, untrustedDisplayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<InputMethodInfo> getInputMethodList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputMethodList(userId);
                    }
                    _reply.readException();
                    List<InputMethodInfo> _result = _reply.createTypedArrayList(InputMethodInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<InputMethodInfo> getEnabledInputMethodList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledInputMethodList(userId);
                    }
                    _reply.readException();
                    List<InputMethodInfo> _result = _reply.createTypedArrayList(InputMethodInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String imiId, boolean allowsImplicitlySelectedSubtypes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(imiId);
                    _data.writeInt(allowsImplicitlySelectedSubtypes);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledInputMethodSubtypeList(imiId, allowsImplicitlySelectedSubtypes);
                    }
                    _reply.readException();
                    List<InputMethodSubtype> _result = _reply.createTypedArrayList(InputMethodSubtype.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public InputMethodSubtype getLastInputMethodSubtype() throws RemoteException {
                InputMethodSubtype _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastInputMethodSubtype();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InputMethodSubtype.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    InputMethodSubtype _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean showSoftInput(IInputMethodClient client, int flags, ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(flags);
                    boolean _result = true;
                    if (resultReceiver != null) {
                        _data.writeInt(1);
                        resultReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().showSoftInput(client, flags, resultReceiver);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hideSoftInput(IInputMethodClient client, int flags, ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(flags);
                    boolean _result = true;
                    if (resultReceiver != null) {
                        _data.writeInt(1);
                        resultReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hideSoftInput(client, flags, resultReceiver);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public InputBindResult startInputOrWindowGainedFocus(int startInputReason, IInputMethodClient client, IBinder windowToken, int startInputFlags, int softInputMode, int windowFlags, EditorInfo attribute, IInputContext inputContext, int missingMethodFlags, int unverifiedTargetSdkVersion) throws RemoteException {
                EditorInfo editorInfo = attribute;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(startInputReason);
                        InputBindResult _result = null;
                        _data.writeStrongBinder(client != null ? client.asBinder() : null);
                        _data.writeStrongBinder(windowToken);
                        _data.writeInt(startInputFlags);
                        _data.writeInt(softInputMode);
                        _data.writeInt(windowFlags);
                        if (editorInfo != null) {
                            _data.writeInt(1);
                            editorInfo.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(inputContext != null ? inputContext.asBinder() : null);
                        _data.writeInt(missingMethodFlags);
                        _data.writeInt(unverifiedTargetSdkVersion);
                        if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                _result = InputBindResult.CREATOR.createFromParcel(_reply);
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        InputBindResult startInputOrWindowGainedFocus = Stub.getDefaultImpl().startInputOrWindowGainedFocus(startInputReason, client, windowToken, startInputFlags, softInputMode, windowFlags, attribute, inputContext, missingMethodFlags, unverifiedTargetSdkVersion);
                        _reply.recycle();
                        _data.recycle();
                        return startInputOrWindowGainedFocus;
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    int i = startInputReason;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void showInputMethodPickerFromClient(IInputMethodClient client, int auxiliarySubtypeMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(auxiliarySubtypeMode);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showInputMethodPickerFromClient(client, auxiliarySubtypeMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showInputMethodPickerFromSystem(IInputMethodClient client, int auxiliarySubtypeMode, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(auxiliarySubtypeMode);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showInputMethodPickerFromSystem(client, auxiliarySubtypeMode, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient client, String topId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeString(topId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showInputMethodAndSubtypeEnablerFromClient(client, topId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInputMethodPickerShownForTest() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInputMethodPickerShownForTest();
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

            public InputMethodSubtype getCurrentInputMethodSubtype() throws RemoteException {
                InputMethodSubtype _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentInputMethodSubtype();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InputMethodSubtype.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    InputMethodSubtype _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAdditionalInputMethodSubtypes(String id, InputMethodSubtype[] subtypes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeTypedArray(subtypes, 0);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAdditionalInputMethodSubtypes(id, subtypes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getInputMethodWindowVisibleHeight() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputMethodWindowVisibleHeight();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportActivityView(IInputMethodClient parentClient, int childDisplayId, float[] matrixValues) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(parentClient != null ? parentClient.asBinder() : null);
                    _data.writeInt(childDisplayId);
                    _data.writeFloatArray(matrixValues);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportActivityView(parentClient, childDisplayId, matrixValues);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputMethodManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputMethodManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
