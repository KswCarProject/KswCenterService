package android.app.contentsuggestions;

import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import com.android.internal.p016os.IResultReceiver;

/* loaded from: classes.dex */
public interface IContentSuggestionsManager extends IInterface {
    void classifyContentSelections(int i, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException;

    void isEnabled(int i, IResultReceiver iResultReceiver) throws RemoteException;

    void notifyInteraction(int i, String str, Bundle bundle) throws RemoteException;

    void provideContextImage(int i, int i2, Bundle bundle) throws RemoteException;

    void suggestContentSelections(int i, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IContentSuggestionsManager {
        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void provideContextImage(int userId, int taskId, Bundle imageContextRequestExtras) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void suggestContentSelections(int userId, SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void classifyContentSelections(int userId, ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void notifyInteraction(int userId, String requestId, Bundle interaction) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void isEnabled(int userId, IResultReceiver receiver) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IContentSuggestionsManager {
        private static final String DESCRIPTOR = "android.app.contentsuggestions.IContentSuggestionsManager";
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_isEnabled = 5;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentSuggestionsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContentSuggestionsManager)) {
                return (IContentSuggestionsManager) iin;
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
                    return "provideContextImage";
                case 2:
                    return "suggestContentSelections";
                case 3:
                    return "classifyContentSelections";
                case 4:
                    return "notifyInteraction";
                case 5:
                    return "isEnabled";
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
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    Bundle _arg2 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    provideContextImage(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    SelectionsRequest _arg12 = data.readInt() != 0 ? SelectionsRequest.CREATOR.createFromParcel(data) : null;
                    ISelectionsCallback _arg22 = ISelectionsCallback.Stub.asInterface(data.readStrongBinder());
                    suggestContentSelections(_arg02, _arg12, _arg22);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    ClassificationsRequest _arg13 = data.readInt() != 0 ? ClassificationsRequest.CREATOR.createFromParcel(data) : null;
                    IClassificationsCallback _arg23 = IClassificationsCallback.Stub.asInterface(data.readStrongBinder());
                    classifyContentSelections(_arg03, _arg13, _arg23);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    String _arg14 = data.readString();
                    Bundle _arg24 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    notifyInteraction(_arg04, _arg14, _arg24);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    IResultReceiver _arg15 = IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    isEnabled(_arg05, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IContentSuggestionsManager {
            public static IContentSuggestionsManager sDefaultImpl;
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

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void provideContextImage(int userId, int taskId, Bundle imageContextRequestExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(taskId);
                    if (imageContextRequestExtras != null) {
                        _data.writeInt(1);
                        imageContextRequestExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().provideContextImage(userId, taskId, imageContextRequestExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void suggestContentSelections(int userId, SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().suggestContentSelections(userId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void classifyContentSelections(int userId, ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().classifyContentSelections(userId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void notifyInteraction(int userId, String requestId, Bundle interaction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(requestId);
                    if (interaction != null) {
                        _data.writeInt(1);
                        interaction.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyInteraction(userId, requestId, interaction);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void isEnabled(int userId, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isEnabled(userId, receiver);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentSuggestionsManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IContentSuggestionsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
