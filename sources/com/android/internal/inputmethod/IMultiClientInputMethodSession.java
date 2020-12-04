package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import com.android.internal.view.IInputContext;

public interface IMultiClientInputMethodSession extends IInterface {
    void hideSoftInput(int i, ResultReceiver resultReceiver) throws RemoteException;

    void showSoftInput(int i, ResultReceiver resultReceiver) throws RemoteException;

    void startInputOrWindowGainedFocus(IInputContext iInputContext, int i, EditorInfo editorInfo, int i2, int i3, int i4) throws RemoteException;

    public static class Default implements IMultiClientInputMethodSession {
        public void startInputOrWindowGainedFocus(IInputContext inputContext, int missingMethods, EditorInfo attribute, int controlFlags, int softInputMode, int targetWindowHandle) throws RemoteException {
        }

        public void showSoftInput(int flags, ResultReceiver resultReceiver) throws RemoteException {
        }

        public void hideSoftInput(int flags, ResultReceiver resultReceiver) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMultiClientInputMethodSession {
        private static final String DESCRIPTOR = "com.android.internal.inputmethod.IMultiClientInputMethodSession";
        static final int TRANSACTION_hideSoftInput = 3;
        static final int TRANSACTION_showSoftInput = 2;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMultiClientInputMethodSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IMultiClientInputMethodSession)) {
                return new Proxy(obj);
            }
            return (IMultiClientInputMethodSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startInputOrWindowGainedFocus";
                case 2:
                    return "showSoftInput";
                case 3:
                    return "hideSoftInput";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.view.inputmethod.EditorInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.os.ResultReceiver} */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.view.inputmethod.EditorInfo] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r7 = r17
                r8 = r18
                r9 = r19
                java.lang.String r10 = "com.android.internal.inputmethod.IMultiClientInputMethodSession"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x0085
                r0 = 0
                switch(r8) {
                    case 1: goto L_0x004d;
                    case 2: goto L_0x0032;
                    case 3: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x0017:
                r9.enforceInterface(r10)
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x002d
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x002e
            L_0x002d:
            L_0x002e:
                r7.hideSoftInput(r1, r0)
                return r11
            L_0x0032:
                r9.enforceInterface(r10)
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0048
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x0049
            L_0x0048:
            L_0x0049:
                r7.showSoftInput(r1, r0)
                return r11
            L_0x004d:
                r9.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                com.android.internal.view.IInputContext r12 = com.android.internal.view.IInputContext.Stub.asInterface(r1)
                int r13 = r19.readInt()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x006c
                android.os.Parcelable$Creator<android.view.inputmethod.EditorInfo> r0 = android.view.inputmethod.EditorInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.inputmethod.EditorInfo r0 = (android.view.inputmethod.EditorInfo) r0
            L_0x006a:
                r3 = r0
                goto L_0x006d
            L_0x006c:
                goto L_0x006a
            L_0x006d:
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r4 = r14
                r5 = r15
                r6 = r16
                r0.startInputOrWindowGainedFocus(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x0085:
                r0 = r20
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.inputmethod.IMultiClientInputMethodSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMultiClientInputMethodSession {
            public static IMultiClientInputMethodSession sDefaultImpl;
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

            public void startInputOrWindowGainedFocus(IInputContext inputContext, int missingMethods, EditorInfo attribute, int controlFlags, int softInputMode, int targetWindowHandle) throws RemoteException {
                EditorInfo editorInfo = attribute;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(inputContext != null ? inputContext.asBinder() : null);
                    try {
                        _data.writeInt(missingMethods);
                        if (editorInfo != null) {
                            _data.writeInt(1);
                            editorInfo.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(controlFlags);
                        } catch (Throwable th) {
                            th = th;
                            int i = softInputMode;
                            int i2 = targetWindowHandle;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(softInputMode);
                            try {
                                _data.writeInt(targetWindowHandle);
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                            try {
                                if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().startInputOrWindowGainedFocus(inputContext, missingMethods, attribute, controlFlags, softInputMode, targetWindowHandle);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = targetWindowHandle;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = controlFlags;
                        int i4 = softInputMode;
                        int i222 = targetWindowHandle;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i5 = missingMethods;
                    int i32 = controlFlags;
                    int i42 = softInputMode;
                    int i2222 = targetWindowHandle;
                    _data.recycle();
                    throw th;
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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().hideSoftInput(flags, resultReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMultiClientInputMethodSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMultiClientInputMethodSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
