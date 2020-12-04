package android.media.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

public interface ISoundTriggerDetectionService extends IInterface {
    void onError(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    void onGenericRecognitionEvent(ParcelUuid parcelUuid, int i, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException;

    void onStopOperation(ParcelUuid parcelUuid, int i) throws RemoteException;

    void removeClient(ParcelUuid parcelUuid) throws RemoteException;

    void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException;

    public static class Default implements ISoundTriggerDetectionService {
        public void setClient(ParcelUuid uuid, Bundle params, ISoundTriggerDetectionServiceClient client) throws RemoteException {
        }

        public void removeClient(ParcelUuid uuid) throws RemoteException {
        }

        public void onGenericRecognitionEvent(ParcelUuid uuid, int opId, SoundTrigger.GenericRecognitionEvent event) throws RemoteException {
        }

        public void onError(ParcelUuid uuid, int opId, int status) throws RemoteException {
        }

        public void onStopOperation(ParcelUuid uuid, int opId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof ISoundTriggerDetectionService)) {
                return new Proxy(obj);
            }
            return (ISoundTriggerDetectionService) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.hardware.soundtrigger.SoundTrigger$GenericRecognitionEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.os.ParcelUuid} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: type inference failed for: r1v24 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: type inference failed for: r1v26 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.media.soundtrigger.ISoundTriggerDetectionService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00bc
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x008d;
                    case 2: goto L_0x0076;
                    case 3: goto L_0x004b;
                    case 4: goto L_0x002c;
                    case 5: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                int r3 = r8.readInt()
                r6.onStopOperation(r1, r3)
                return r2
            L_0x002c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                r6.onError(r1, r3, r4)
                return r2
            L_0x004b:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x005d
                android.os.Parcelable$Creator<android.os.ParcelUuid> r3 = android.os.ParcelUuid.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.ParcelUuid r3 = (android.os.ParcelUuid) r3
                goto L_0x005e
            L_0x005d:
                r3 = r1
            L_0x005e:
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0071
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$GenericRecognitionEvent> r1 = android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.soundtrigger.SoundTrigger$GenericRecognitionEvent r1 = (android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) r1
                goto L_0x0072
            L_0x0071:
            L_0x0072:
                r6.onGenericRecognitionEvent(r3, r4, r1)
                return r2
            L_0x0076:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0088
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0089
            L_0x0088:
            L_0x0089:
                r6.removeClient(r1)
                return r2
            L_0x008d:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x009f
                android.os.Parcelable$Creator<android.os.ParcelUuid> r3 = android.os.ParcelUuid.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.ParcelUuid r3 = (android.os.ParcelUuid) r3
                goto L_0x00a0
            L_0x009f:
                r3 = r1
            L_0x00a0:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00af
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00b0
            L_0x00af:
            L_0x00b0:
                android.os.IBinder r4 = r8.readStrongBinder()
                android.media.soundtrigger.ISoundTriggerDetectionServiceClient r4 = android.media.soundtrigger.ISoundTriggerDetectionServiceClient.Stub.asInterface(r4)
                r6.setClient(r3, r1, r4)
                return r2
            L_0x00bc:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.soundtrigger.ISoundTriggerDetectionService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISoundTriggerDetectionService {
            public static ISoundTriggerDetectionService sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setClient(uuid, params, client);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeClient(uuid);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGenericRecognitionEvent(uuid, opId, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(uuid, opId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStopOperation(uuid, opId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISoundTriggerDetectionService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISoundTriggerDetectionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
