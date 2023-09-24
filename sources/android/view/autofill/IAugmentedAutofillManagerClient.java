package android.view.autofill;

import android.graphics.Rect;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.autofill.IAutofillWindowPresenter;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAugmentedAutofillManagerClient extends IInterface {
    void autofill(int i, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException;

    Rect getViewCoordinates(AutofillId autofillId) throws RemoteException;

    void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException;

    void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IAugmentedAutofillManagerClient {
        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public Rect getViewCoordinates(AutofillId id) throws RemoteException {
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) throws RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
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
            if (iin != null && (iin instanceof IAugmentedAutofillManagerClient)) {
                return (IAugmentedAutofillManagerClient) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    AutofillId _arg0 = data.readInt() != 0 ? AutofillId.CREATOR.createFromParcel(data) : null;
                    Rect _result = getViewCoordinates(_arg0);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    List<AutofillId> _arg1 = data.createTypedArrayList(AutofillId.CREATOR);
                    List<AutofillValue> _arg2 = data.createTypedArrayList(AutofillValue.CREATOR);
                    autofill(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    AutofillId _arg12 = data.readInt() != 0 ? AutofillId.CREATOR.createFromParcel(data) : null;
                    int _arg22 = data.readInt();
                    int _arg3 = data.readInt();
                    Rect _arg4 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    IAutofillWindowPresenter _arg5 = IAutofillWindowPresenter.Stub.asInterface(data.readStrongBinder());
                    requestShowFillUi(_arg03, _arg12, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    AutofillId _arg13 = data.readInt() != 0 ? AutofillId.CREATOR.createFromParcel(data) : null;
                    requestHideFillUi(_arg04, _arg13);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IAugmentedAutofillManagerClient {
            public static IAugmentedAutofillManagerClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
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
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getViewCoordinates(id);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Rect.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedList(ids);
                    _data.writeTypedList(values);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().autofill(sessionId, ids, values);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(sessionId);
                        if (id != null) {
                            _data.writeInt(1);
                            id.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(width);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(height);
                        if (anchorBounds != null) {
                            _data.writeInt(1);
                            anchorBounds.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(presenter != null ? presenter.asBinder() : null);
                        boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().requestShowFillUi(sessionId, id, width, height, anchorBounds, presenter);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
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
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
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
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestHideFillUi(sessionId, id);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAugmentedAutofillManagerClient impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAugmentedAutofillManagerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
