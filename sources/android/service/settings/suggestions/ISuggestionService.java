package android.service.settings.suggestions;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISuggestionService extends IInterface {
    void dismissSuggestion(Suggestion suggestion) throws RemoteException;

    List<Suggestion> getSuggestions() throws RemoteException;

    void launchSuggestion(Suggestion suggestion) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ISuggestionService {
        @Override // android.service.settings.suggestions.ISuggestionService
        public List<Suggestion> getSuggestions() throws RemoteException {
            return null;
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void dismissSuggestion(Suggestion suggestion) throws RemoteException {
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void launchSuggestion(Suggestion suggestion) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISuggestionService {
        private static final String DESCRIPTOR = "android.service.settings.suggestions.ISuggestionService";
        static final int TRANSACTION_dismissSuggestion = 3;
        static final int TRANSACTION_getSuggestions = 2;
        static final int TRANSACTION_launchSuggestion = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISuggestionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISuggestionService)) {
                return (ISuggestionService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 2:
                    return "getSuggestions";
                case 3:
                    return "dismissSuggestion";
                case 4:
                    return "launchSuggestion";
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
            Suggestion _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    List<Suggestion> _result = getSuggestions();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? Suggestion.CREATOR.createFromParcel(data) : null;
                    dismissSuggestion(_arg0);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? Suggestion.CREATOR.createFromParcel(data) : null;
                    launchSuggestion(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ISuggestionService {
            public static ISuggestionService sDefaultImpl;
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

            @Override // android.service.settings.suggestions.ISuggestionService
            public List<Suggestion> getSuggestions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSuggestions();
                    }
                    _reply.readException();
                    List<Suggestion> _result = _reply.createTypedArrayList(Suggestion.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void dismissSuggestion(Suggestion suggestion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (suggestion != null) {
                        _data.writeInt(1);
                        suggestion.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissSuggestion(suggestion);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void launchSuggestion(Suggestion suggestion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (suggestion != null) {
                        _data.writeInt(1);
                        suggestion.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().launchSuggestion(suggestion);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISuggestionService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISuggestionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
