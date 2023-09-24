package android.media.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.ISoundTriggerDetectionServiceClient;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelUuid;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundTriggerDetectionService extends IInterface {
    void onError(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    void onGenericRecognitionEvent(ParcelUuid parcelUuid, int i, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException;

    void onStopOperation(ParcelUuid parcelUuid, int i) throws RemoteException;

    void removeClient(ParcelUuid parcelUuid) throws RemoteException;

    void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ISoundTriggerDetectionService {
        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void setClient(ParcelUuid uuid, Bundle params, ISoundTriggerDetectionServiceClient client) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void removeClient(ParcelUuid uuid) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onGenericRecognitionEvent(ParcelUuid uuid, int opId, SoundTrigger.GenericRecognitionEvent event) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onError(ParcelUuid uuid, int opId, int status) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onStopOperation(ParcelUuid uuid, int opId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISoundTriggerDetectionService {
        private static final String DESCRIPTOR = "android.media.soundtrigger.ISoundTriggerDetectionService";
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onGenericRecognitionEvent = 3;
        static final int TRANSACTION_onStopOperation = 5;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_setClient = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerDetectionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISoundTriggerDetectionService)) {
                return (ISoundTriggerDetectionService) iin;
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
                    return "setClient";
                case 2:
                    return "removeClient";
                case 3:
                    return "onGenericRecognitionEvent";
                case 4:
                    return "onError";
                case 5:
                    return "onStopOperation";
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
            ParcelUuid _arg0;
            ParcelUuid _arg02;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = ParcelUuid.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    Bundle _arg1 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    ISoundTriggerDetectionServiceClient _arg2 = ISoundTriggerDetectionServiceClient.Stub.asInterface(data.readStrongBinder());
                    setClient(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelUuid _arg03 = data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null;
                    removeClient(_arg03);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = ParcelUuid.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    int _arg12 = data.readInt();
                    SoundTrigger.GenericRecognitionEvent _arg22 = data.readInt() != 0 ? SoundTrigger.GenericRecognitionEvent.CREATOR.createFromParcel(data) : null;
                    onGenericRecognitionEvent(_arg02, _arg12, _arg22);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelUuid _arg04 = data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null;
                    int _arg13 = data.readInt();
                    int _arg23 = data.readInt();
                    onError(_arg04, _arg13, _arg23);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelUuid _arg05 = data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null;
                    int _arg14 = data.readInt();
                    onStopOperation(_arg05, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ISoundTriggerDetectionService {
            public static ISoundTriggerDetectionService sDefaultImpl;
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

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void setClient(ParcelUuid uuid, Bundle params, ISoundTriggerDetectionServiceClient client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setClient(uuid, params, client);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void removeClient(ParcelUuid uuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeClient(uuid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onGenericRecognitionEvent(ParcelUuid uuid, int opId, SoundTrigger.GenericRecognitionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(opId);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGenericRecognitionEvent(uuid, opId, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onError(ParcelUuid uuid, int opId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(opId);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(uuid, opId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onStopOperation(ParcelUuid uuid, int opId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(opId);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStopOperation(uuid, opId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISoundTriggerDetectionService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISoundTriggerDetectionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
