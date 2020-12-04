package android.view.autofill;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IAugmentedAutofillManagerClient extends IInterface {
    void autofill(int i, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException;

    Rect getViewCoordinates(AutofillId autofillId) throws RemoteException;

    void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException;

    void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException;

    public static class Default implements IAugmentedAutofillManagerClient {
        public Rect getViewCoordinates(AutofillId id) throws RemoteException {
            return null;
        }

        public void autofill(int sessionId, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException {
        }

        public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
        }

        public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAugmentedAutofillManagerClient {
        private static final String DESCRIPTOR = "android.view.autofill.IAugmentedAutofillManagerClient";
        static final int TRANSACTION_autofill = 2;
        static final int TRANSACTION_getViewCoordinates = 1;
        static final int TRANSACTION_requestHideFillUi = 4;
        static final int TRANSACTION_requestShowFillUi = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAugmentedAutofillManagerClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAugmentedAutofillManagerClient)) {
                return new Proxy(obj);
            }
            return (IAugmentedAutofillManagerClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getViewCoordinates";
                case 2:
                    return "autofill";
                case 3:
                    return "requestShowFillUi";
                case 4:
                    return "requestHideFillUi";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: android.view.autofill.AutofillId} */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.graphics.Rect] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r7 = r17
                r8 = r18
                r9 = r19
                r10 = r20
                java.lang.String r11 = "android.view.autofill.IAugmentedAutofillManagerClient"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x00c0
                r0 = 0
                switch(r8) {
                    case 1: goto L_0x0098;
                    case 2: goto L_0x007e;
                    case 3: goto L_0x0037;
                    case 4: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x0019:
                r9.enforceInterface(r11)
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x002f
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r0 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.autofill.AutofillId r0 = (android.view.autofill.AutofillId) r0
                goto L_0x0030
            L_0x002f:
            L_0x0030:
                r7.requestHideFillUi(r1, r0)
                r20.writeNoException()
                return r12
            L_0x0037:
                r9.enforceInterface(r11)
                int r13 = r19.readInt()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x004e
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.view.autofill.AutofillId r1 = (android.view.autofill.AutofillId) r1
                r2 = r1
                goto L_0x004f
            L_0x004e:
                r2 = r0
            L_0x004f:
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0067
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x0065:
                r5 = r0
                goto L_0x0068
            L_0x0067:
                goto L_0x0065
            L_0x0068:
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.autofill.IAutofillWindowPresenter r16 = android.view.autofill.IAutofillWindowPresenter.Stub.asInterface(r0)
                r0 = r17
                r1 = r13
                r3 = r14
                r4 = r15
                r6 = r16
                r0.requestShowFillUi(r1, r2, r3, r4, r5, r6)
                r20.writeNoException()
                return r12
            L_0x007e:
                r9.enforceInterface(r11)
                int r0 = r19.readInt()
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.util.ArrayList r1 = r9.createTypedArrayList(r1)
                android.os.Parcelable$Creator<android.view.autofill.AutofillValue> r2 = android.view.autofill.AutofillValue.CREATOR
                java.util.ArrayList r2 = r9.createTypedArrayList(r2)
                r7.autofill(r0, r1, r2)
                r20.writeNoException()
                return r12
            L_0x0098:
                r9.enforceInterface(r11)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x00aa
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r0 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.autofill.AutofillId r0 = (android.view.autofill.AutofillId) r0
                goto L_0x00ab
            L_0x00aa:
            L_0x00ab:
                android.graphics.Rect r1 = r7.getViewCoordinates(r0)
                r20.writeNoException()
                if (r1 == 0) goto L_0x00bb
                r10.writeInt(r12)
                r1.writeToParcel(r10, r12)
                goto L_0x00bf
            L_0x00bb:
                r2 = 0
                r10.writeInt(r2)
            L_0x00bf:
                return r12
            L_0x00c0:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.IAugmentedAutofillManagerClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAugmentedAutofillManagerClient {
            public static IAugmentedAutofillManagerClient sDefaultImpl;
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

            public Rect getViewCoordinates(AutofillId id) throws RemoteException {
                Rect _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (id != null) {
                        _data.writeInt(1);
                        id.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getViewCoordinates(id);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Rect.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Rect _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedList(ids);
                    _data.writeTypedList(values);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().autofill(sessionId, ids, values);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
                AutofillId autofillId = id;
                Rect rect = anchorBounds;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(sessionId);
                        if (autofillId != null) {
                            _data.writeInt(1);
                            autofillId.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(width);
                        } catch (Throwable th) {
                            th = th;
                            int i = height;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i2 = width;
                        int i3 = height;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(height);
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(presenter != null ? presenter.asBinder() : null);
                        if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().requestShowFillUi(sessionId, id, width, height, anchorBounds, presenter);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i4 = sessionId;
                    int i22 = width;
                    int i32 = height;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (id != null) {
                        _data.writeInt(1);
                        id.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestHideFillUi(sessionId, id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAugmentedAutofillManagerClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAugmentedAutofillManagerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
