package android.media.tv;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

public interface ITvInputService extends IInterface {
    void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str) throws RemoteException;

    void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str) throws RemoteException;

    void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException;

    void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException;

    void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException;

    void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException;

    public static class Default implements ITvInputService {
        public void registerCallback(ITvInputServiceCallback callback) throws RemoteException {
        }

        public void unregisterCallback(ITvInputServiceCallback callback) throws RemoteException {
        }

        public void createSession(InputChannel channel, ITvInputSessionCallback callback, String inputId) throws RemoteException {
        }

        public void createRecordingSession(ITvInputSessionCallback callback, String inputId) throws RemoteException {
        }

        public void notifyHardwareAdded(TvInputHardwareInfo hardwareInfo) throws RemoteException {
        }

        public void notifyHardwareRemoved(TvInputHardwareInfo hardwareInfo) throws RemoteException {
        }

        public void notifyHdmiDeviceAdded(HdmiDeviceInfo deviceInfo) throws RemoteException {
        }

        public void notifyHdmiDeviceRemoved(HdmiDeviceInfo deviceInfo) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInputService {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputService";
        static final int TRANSACTION_createRecordingSession = 4;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_notifyHardwareAdded = 5;
        static final int TRANSACTION_notifyHardwareRemoved = 6;
        static final int TRANSACTION_notifyHdmiDeviceAdded = 7;
        static final int TRANSACTION_notifyHdmiDeviceRemoved = 8;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITvInputService)) {
                return new Proxy(obj);
            }
            return (ITvInputService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "createSession";
                case 4:
                    return "createRecordingSession";
                case 5:
                    return "notifyHardwareAdded";
                case 6:
                    return "notifyHardwareRemoved";
                case 7:
                    return "notifyHdmiDeviceAdded";
                case 8:
                    return "notifyHdmiDeviceRemoved";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.view.InputChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.media.tv.TvInputHardwareInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.media.tv.TvInputHardwareInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.hardware.hdmi.HdmiDeviceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: android.hardware.hdmi.HdmiDeviceInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.media.tv.ITvInputService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00c1
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x00b2;
                    case 2: goto L_0x00a3;
                    case 3: goto L_0x0080;
                    case 4: goto L_0x006d;
                    case 5: goto L_0x0056;
                    case 6: goto L_0x003f;
                    case 7: goto L_0x0028;
                    case 8: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.hardware.hdmi.HdmiDeviceInfo> r1 = android.hardware.hdmi.HdmiDeviceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.hardware.hdmi.HdmiDeviceInfo r1 = (android.hardware.hdmi.HdmiDeviceInfo) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r5.notifyHdmiDeviceRemoved(r1)
                return r2
            L_0x0028:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.hardware.hdmi.HdmiDeviceInfo> r1 = android.hardware.hdmi.HdmiDeviceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.hardware.hdmi.HdmiDeviceInfo r1 = (android.hardware.hdmi.HdmiDeviceInfo) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                r5.notifyHdmiDeviceAdded(r1)
                return r2
            L_0x003f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0051
                android.os.Parcelable$Creator<android.media.tv.TvInputHardwareInfo> r1 = android.media.tv.TvInputHardwareInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.tv.TvInputHardwareInfo r1 = (android.media.tv.TvInputHardwareInfo) r1
                goto L_0x0052
            L_0x0051:
            L_0x0052:
                r5.notifyHardwareRemoved(r1)
                return r2
            L_0x0056:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0068
                android.os.Parcelable$Creator<android.media.tv.TvInputHardwareInfo> r1 = android.media.tv.TvInputHardwareInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.tv.TvInputHardwareInfo r1 = (android.media.tv.TvInputHardwareInfo) r1
                goto L_0x0069
            L_0x0068:
            L_0x0069:
                r5.notifyHardwareAdded(r1)
                return r2
            L_0x006d:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.media.tv.ITvInputSessionCallback r1 = android.media.tv.ITvInputSessionCallback.Stub.asInterface(r1)
                java.lang.String r3 = r7.readString()
                r5.createRecordingSession(r1, r3)
                return r2
            L_0x0080:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0092
                android.os.Parcelable$Creator<android.view.InputChannel> r1 = android.view.InputChannel.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.InputChannel r1 = (android.view.InputChannel) r1
                goto L_0x0093
            L_0x0092:
            L_0x0093:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.media.tv.ITvInputSessionCallback r3 = android.media.tv.ITvInputSessionCallback.Stub.asInterface(r3)
                java.lang.String r4 = r7.readString()
                r5.createSession(r1, r3, r4)
                return r2
            L_0x00a3:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.media.tv.ITvInputServiceCallback r1 = android.media.tv.ITvInputServiceCallback.Stub.asInterface(r1)
                r5.unregisterCallback(r1)
                return r2
            L_0x00b2:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.media.tv.ITvInputServiceCallback r1 = android.media.tv.ITvInputServiceCallback.Stub.asInterface(r1)
                r5.registerCallback(r1)
                return r2
            L_0x00c1:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.tv.ITvInputService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITvInputService {
            public static ITvInputService sDefaultImpl;
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

            public void registerCallback(ITvInputServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().registerCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unregisterCallback(ITvInputServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void createSession(InputChannel channel, ITvInputSessionCallback callback, String inputId) throws RemoteException {
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
                    _data.writeString(inputId);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createSession(channel, callback, inputId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void createRecordingSession(ITvInputSessionCallback callback, String inputId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(inputId);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createRecordingSession(callback, inputId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyHardwareAdded(TvInputHardwareInfo hardwareInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hardwareInfo != null) {
                        _data.writeInt(1);
                        hardwareInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyHardwareAdded(hardwareInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyHardwareRemoved(TvInputHardwareInfo hardwareInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hardwareInfo != null) {
                        _data.writeInt(1);
                        hardwareInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyHardwareRemoved(hardwareInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyHdmiDeviceAdded(HdmiDeviceInfo deviceInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (deviceInfo != null) {
                        _data.writeInt(1);
                        deviceInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyHdmiDeviceAdded(deviceInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo deviceInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (deviceInfo != null) {
                        _data.writeInt(1);
                        deviceInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyHdmiDeviceRemoved(deviceInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITvInputService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
