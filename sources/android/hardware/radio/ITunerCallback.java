package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

public interface ITunerCallback extends IInterface {
    void onAntennaState(boolean z) throws RemoteException;

    void onBackgroundScanAvailabilityChange(boolean z) throws RemoteException;

    void onBackgroundScanComplete() throws RemoteException;

    void onConfigurationChanged(RadioManager.BandConfig bandConfig) throws RemoteException;

    void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) throws RemoteException;

    void onEmergencyAnnouncement(boolean z) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onParametersUpdated(Map map) throws RemoteException;

    void onProgramListChanged() throws RemoteException;

    void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException;

    void onTrafficAnnouncement(boolean z) throws RemoteException;

    void onTuneFailed(int i, ProgramSelector programSelector) throws RemoteException;

    public static class Default implements ITunerCallback {
        public void onError(int status) throws RemoteException {
        }

        public void onTuneFailed(int result, ProgramSelector selector) throws RemoteException {
        }

        public void onConfigurationChanged(RadioManager.BandConfig config) throws RemoteException {
        }

        public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo info) throws RemoteException {
        }

        public void onTrafficAnnouncement(boolean active) throws RemoteException {
        }

        public void onEmergencyAnnouncement(boolean active) throws RemoteException {
        }

        public void onAntennaState(boolean connected) throws RemoteException {
        }

        public void onBackgroundScanAvailabilityChange(boolean isAvailable) throws RemoteException {
        }

        public void onBackgroundScanComplete() throws RemoteException {
        }

        public void onProgramListChanged() throws RemoteException {
        }

        public void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException {
        }

        public void onParametersUpdated(Map parameters) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITunerCallback {
        private static final String DESCRIPTOR = "android.hardware.radio.ITunerCallback";
        static final int TRANSACTION_onAntennaState = 7;
        static final int TRANSACTION_onBackgroundScanAvailabilityChange = 8;
        static final int TRANSACTION_onBackgroundScanComplete = 9;
        static final int TRANSACTION_onConfigurationChanged = 3;
        static final int TRANSACTION_onCurrentProgramInfoChanged = 4;
        static final int TRANSACTION_onEmergencyAnnouncement = 6;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onParametersUpdated = 12;
        static final int TRANSACTION_onProgramListChanged = 10;
        static final int TRANSACTION_onProgramListUpdated = 11;
        static final int TRANSACTION_onTrafficAnnouncement = 5;
        static final int TRANSACTION_onTuneFailed = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITunerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITunerCallback)) {
                return new Proxy(obj);
            }
            return (ITunerCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onError";
                case 2:
                    return "onTuneFailed";
                case 3:
                    return "onConfigurationChanged";
                case 4:
                    return "onCurrentProgramInfoChanged";
                case 5:
                    return "onTrafficAnnouncement";
                case 6:
                    return "onEmergencyAnnouncement";
                case 7:
                    return "onAntennaState";
                case 8:
                    return "onBackgroundScanAvailabilityChange";
                case 9:
                    return "onBackgroundScanComplete";
                case 10:
                    return "onProgramListChanged";
                case 11:
                    return "onProgramListUpdated";
                case 12:
                    return "onParametersUpdated";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.hardware.radio.RadioManager$ProgramInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v15, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: android.hardware.radio.ProgramList$Chunk} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX WARNING: type inference failed for: r1v5, types: [android.hardware.radio.RadioManager$BandConfig] */
        /* JADX WARNING: type inference failed for: r3v7, types: [android.hardware.radio.RadioManager$BandConfig] */
        /* JADX WARNING: type inference failed for: r3v10, types: [android.hardware.radio.RadioManager$ProgramInfo] */
        /* JADX WARNING: type inference failed for: r3v17, types: [android.hardware.radio.ProgramList$Chunk] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.hardware.radio.ITunerCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00e0
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x00d5;
                    case 2: goto L_0x00ba;
                    case 3: goto L_0x00a1;
                    case 4: goto L_0x0088;
                    case 5: goto L_0x0079;
                    case 6: goto L_0x006a;
                    case 7: goto L_0x005b;
                    case 8: goto L_0x004c;
                    case 9: goto L_0x0045;
                    case 10: goto L_0x003e;
                    case 11: goto L_0x0025;
                    case 12: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                java.lang.Class r1 = r5.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.HashMap r3 = r7.readHashMap(r1)
                r5.onParametersUpdated(r3)
                return r2
            L_0x0025:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0038
                android.os.Parcelable$Creator<android.hardware.radio.ProgramList$Chunk> r1 = android.hardware.radio.ProgramList.Chunk.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.hardware.radio.ProgramList$Chunk r3 = (android.hardware.radio.ProgramList.Chunk) r3
                goto L_0x0039
            L_0x0038:
            L_0x0039:
                r1 = r3
                r5.onProgramListUpdated(r1)
                return r2
            L_0x003e:
                r7.enforceInterface(r0)
                r5.onProgramListChanged()
                return r2
            L_0x0045:
                r7.enforceInterface(r0)
                r5.onBackgroundScanComplete()
                return r2
            L_0x004c:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0057
                r1 = r2
            L_0x0057:
                r5.onBackgroundScanAvailabilityChange(r1)
                return r2
            L_0x005b:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0066
                r1 = r2
            L_0x0066:
                r5.onAntennaState(r1)
                return r2
            L_0x006a:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0075
                r1 = r2
            L_0x0075:
                r5.onEmergencyAnnouncement(r1)
                return r2
            L_0x0079:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0084
                r1 = r2
            L_0x0084:
                r5.onTrafficAnnouncement(r1)
                return r2
            L_0x0088:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x009b
                android.os.Parcelable$Creator<android.hardware.radio.RadioManager$ProgramInfo> r1 = android.hardware.radio.RadioManager.ProgramInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.hardware.radio.RadioManager$ProgramInfo r3 = (android.hardware.radio.RadioManager.ProgramInfo) r3
                goto L_0x009c
            L_0x009b:
            L_0x009c:
                r1 = r3
                r5.onCurrentProgramInfoChanged(r1)
                return r2
            L_0x00a1:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x00b4
                android.os.Parcelable$Creator<android.hardware.radio.RadioManager$BandConfig> r1 = android.hardware.radio.RadioManager.BandConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.hardware.radio.RadioManager$BandConfig r3 = (android.hardware.radio.RadioManager.BandConfig) r3
                goto L_0x00b5
            L_0x00b4:
            L_0x00b5:
                r1 = r3
                r5.onConfigurationChanged(r1)
                return r2
            L_0x00ba:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00d0
                android.os.Parcelable$Creator<android.hardware.radio.ProgramSelector> r3 = android.hardware.radio.ProgramSelector.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.hardware.radio.ProgramSelector r3 = (android.hardware.radio.ProgramSelector) r3
                goto L_0x00d1
            L_0x00d0:
            L_0x00d1:
                r5.onTuneFailed(r1, r3)
                return r2
            L_0x00d5:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.onError(r1)
                return r2
            L_0x00e0:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.ITunerCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITunerCallback {
            public static ITunerCallback sDefaultImpl;
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

            public void onError(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTuneFailed(int result, ProgramSelector selector) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(result);
                    if (selector != null) {
                        _data.writeInt(1);
                        selector.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTuneFailed(result, selector);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onConfigurationChanged(RadioManager.BandConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConfigurationChanged(config);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCurrentProgramInfoChanged(info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTrafficAnnouncement(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTrafficAnnouncement(active);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onEmergencyAnnouncement(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onEmergencyAnnouncement(active);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAntennaState(boolean connected) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(connected);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAntennaState(connected);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBackgroundScanAvailabilityChange(boolean isAvailable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isAvailable);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBackgroundScanAvailabilityChange(isAvailable);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBackgroundScanComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBackgroundScanComplete();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProgramListChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProgramListChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (chunk != null) {
                        _data.writeInt(1);
                        chunk.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProgramListUpdated(chunk);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onParametersUpdated(Map parameters) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(parameters);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onParametersUpdated(parameters);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITunerCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITunerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
