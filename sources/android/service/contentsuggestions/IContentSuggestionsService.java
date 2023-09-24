package android.service.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.graphics.GraphicBuffer;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IContentSuggestionsService extends IInterface {
    void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException;

    void notifyInteraction(String str, Bundle bundle) throws RemoteException;

    void provideContextImage(int i, GraphicBuffer graphicBuffer, int i2, Bundle bundle) throws RemoteException;

    void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IContentSuggestionsService {
        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void provideContextImage(int taskId, GraphicBuffer contextImage, int colorSpaceId, Bundle imageContextRequestExtras) throws RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void suggestContentSelections(SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void classifyContentSelections(ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void notifyInteraction(String requestId, Bundle interaction) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IContentSuggestionsService {
        private static final String DESCRIPTOR = "android.service.contentsuggestions.IContentSuggestionsService";
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentSuggestionsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContentSuggestionsService)) {
                return (IContentSuggestionsService) iin;
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
            GraphicBuffer _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg1 = GraphicBuffer.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    int _arg2 = data.readInt();
                    Bundle _arg3 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    provideContextImage(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    SelectionsRequest _arg02 = data.readInt() != 0 ? SelectionsRequest.CREATOR.createFromParcel(data) : null;
                    ISelectionsCallback _arg12 = ISelectionsCallback.Stub.asInterface(data.readStrongBinder());
                    suggestContentSelections(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    ClassificationsRequest _arg03 = data.readInt() != 0 ? ClassificationsRequest.CREATOR.createFromParcel(data) : null;
                    IClassificationsCallback _arg13 = IClassificationsCallback.Stub.asInterface(data.readStrongBinder());
                    classifyContentSelections(_arg03, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    Bundle _arg14 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    notifyInteraction(_arg04, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IContentSuggestionsService {
            public static IContentSuggestionsService sDefaultImpl;
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

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void provideContextImage(int taskId, GraphicBuffer contextImage, int colorSpaceId, Bundle imageContextRequestExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (contextImage != null) {
                        _data.writeInt(1);
                        contextImage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(colorSpaceId);
                    if (imageContextRequestExtras != null) {
                        _data.writeInt(1);
                        imageContextRequestExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().provideContextImage(taskId, contextImage, colorSpaceId, imageContextRequestExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void suggestContentSelections(SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().suggestContentSelections(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void classifyContentSelections(ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().classifyContentSelections(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void notifyInteraction(String requestId, Bundle interaction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(requestId);
                    if (interaction != null) {
                        _data.writeInt(1);
                        interaction.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyInteraction(requestId, interaction);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentSuggestionsService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IContentSuggestionsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
