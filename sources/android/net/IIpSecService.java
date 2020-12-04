package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IIpSecService extends IInterface {
    void addAddressToTunnelInterface(int i, LinkAddress linkAddress, String str) throws RemoteException;

    IpSecSpiResponse allocateSecurityParameterIndex(String str, int i, IBinder iBinder) throws RemoteException;

    void applyTransportModeTransform(ParcelFileDescriptor parcelFileDescriptor, int i, int i2) throws RemoteException;

    void applyTunnelModeTransform(int i, int i2, int i3, String str) throws RemoteException;

    void closeUdpEncapsulationSocket(int i) throws RemoteException;

    IpSecTransformResponse createTransform(IpSecConfig ipSecConfig, IBinder iBinder, String str) throws RemoteException;

    IpSecTunnelInterfaceResponse createTunnelInterface(String str, String str2, Network network, IBinder iBinder, String str3) throws RemoteException;

    void deleteTransform(int i) throws RemoteException;

    void deleteTunnelInterface(int i, String str) throws RemoteException;

    IpSecUdpEncapResponse openUdpEncapsulationSocket(int i, IBinder iBinder) throws RemoteException;

    void releaseSecurityParameterIndex(int i) throws RemoteException;

    void removeAddressFromTunnelInterface(int i, LinkAddress linkAddress, String str) throws RemoteException;

    void removeTransportModeTransforms(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static class Default implements IIpSecService {
        public IpSecSpiResponse allocateSecurityParameterIndex(String destinationAddress, int requestedSpi, IBinder binder) throws RemoteException {
            return null;
        }

        public void releaseSecurityParameterIndex(int resourceId) throws RemoteException {
        }

        public IpSecUdpEncapResponse openUdpEncapsulationSocket(int port, IBinder binder) throws RemoteException {
            return null;
        }

        public void closeUdpEncapsulationSocket(int resourceId) throws RemoteException {
        }

        public IpSecTunnelInterfaceResponse createTunnelInterface(String localAddr, String remoteAddr, Network underlyingNetwork, IBinder binder, String callingPackage) throws RemoteException {
            return null;
        }

        public void addAddressToTunnelInterface(int tunnelResourceId, LinkAddress localAddr, String callingPackage) throws RemoteException {
        }

        public void removeAddressFromTunnelInterface(int tunnelResourceId, LinkAddress localAddr, String callingPackage) throws RemoteException {
        }

        public void deleteTunnelInterface(int resourceId, String callingPackage) throws RemoteException {
        }

        public IpSecTransformResponse createTransform(IpSecConfig c, IBinder binder, String callingPackage) throws RemoteException {
            return null;
        }

        public void deleteTransform(int transformId) throws RemoteException {
        }

        public void applyTransportModeTransform(ParcelFileDescriptor socket, int direction, int transformId) throws RemoteException {
        }

        public void applyTunnelModeTransform(int tunnelResourceId, int direction, int transformResourceId, String callingPackage) throws RemoteException {
        }

        public void removeTransportModeTransforms(ParcelFileDescriptor socket) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IIpSecService {
        private static final String DESCRIPTOR = "android.net.IIpSecService";
        static final int TRANSACTION_addAddressToTunnelInterface = 6;
        static final int TRANSACTION_allocateSecurityParameterIndex = 1;
        static final int TRANSACTION_applyTransportModeTransform = 11;
        static final int TRANSACTION_applyTunnelModeTransform = 12;
        static final int TRANSACTION_closeUdpEncapsulationSocket = 4;
        static final int TRANSACTION_createTransform = 9;
        static final int TRANSACTION_createTunnelInterface = 5;
        static final int TRANSACTION_deleteTransform = 10;
        static final int TRANSACTION_deleteTunnelInterface = 8;
        static final int TRANSACTION_openUdpEncapsulationSocket = 3;
        static final int TRANSACTION_releaseSecurityParameterIndex = 2;
        static final int TRANSACTION_removeAddressFromTunnelInterface = 7;
        static final int TRANSACTION_removeTransportModeTransforms = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIpSecService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IIpSecService)) {
                return new Proxy(obj);
            }
            return (IIpSecService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "allocateSecurityParameterIndex";
                case 2:
                    return "releaseSecurityParameterIndex";
                case 3:
                    return "openUdpEncapsulationSocket";
                case 4:
                    return "closeUdpEncapsulationSocket";
                case 5:
                    return "createTunnelInterface";
                case 6:
                    return "addAddressToTunnelInterface";
                case 7:
                    return "removeAddressFromTunnelInterface";
                case 8:
                    return "deleteTunnelInterface";
                case 9:
                    return "createTransform";
                case 10:
                    return "deleteTransform";
                case 11:
                    return "applyTransportModeTransform";
                case 12:
                    return "applyTunnelModeTransform";
                case 13:
                    return "removeTransportModeTransforms";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: android.net.IpSecConfig} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v6 */
        /* JADX WARNING: type inference failed for: r0v26, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v31, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: type inference failed for: r0v37 */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r0v39 */
        /* JADX WARNING: type inference failed for: r0v40 */
        /* JADX WARNING: type inference failed for: r0v41 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.net.IIpSecService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x01a0
                r12 = 0
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x017d;
                    case 2: goto L_0x016f;
                    case 3: goto L_0x0150;
                    case 4: goto L_0x0142;
                    case 5: goto L_0x0103;
                    case 6: goto L_0x00e1;
                    case 7: goto L_0x00bf;
                    case 8: goto L_0x00ad;
                    case 9: goto L_0x007e;
                    case 10: goto L_0x0070;
                    case 11: goto L_0x004e;
                    case 12: goto L_0x0034;
                    case 13: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x002c
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                goto L_0x002d
            L_0x002c:
            L_0x002d:
                r6.removeTransportModeTransforms(r0)
                r20.writeNoException()
                return r11
            L_0x0034:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                java.lang.String r3 = r19.readString()
                r6.applyTunnelModeTransform(r0, r1, r2, r3)
                r20.writeNoException()
                return r11
            L_0x004e:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0060
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                goto L_0x0061
            L_0x0060:
            L_0x0061:
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                r6.applyTransportModeTransform(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0070:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.deleteTransform(r0)
                r20.writeNoException()
                return r11
            L_0x007e:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0090
                android.os.Parcelable$Creator<android.net.IpSecConfig> r0 = android.net.IpSecConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.IpSecConfig r0 = (android.net.IpSecConfig) r0
                goto L_0x0091
            L_0x0090:
            L_0x0091:
                android.os.IBinder r1 = r19.readStrongBinder()
                java.lang.String r2 = r19.readString()
                android.net.IpSecTransformResponse r3 = r6.createTransform(r0, r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x00a9
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x00ac
            L_0x00a9:
                r9.writeInt(r12)
            L_0x00ac:
                return r11
            L_0x00ad:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.lang.String r1 = r19.readString()
                r6.deleteTunnelInterface(r0, r1)
                r20.writeNoException()
                return r11
            L_0x00bf:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x00d5
                android.os.Parcelable$Creator<android.net.LinkAddress> r0 = android.net.LinkAddress.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.LinkAddress r0 = (android.net.LinkAddress) r0
                goto L_0x00d6
            L_0x00d5:
            L_0x00d6:
                java.lang.String r2 = r19.readString()
                r6.removeAddressFromTunnelInterface(r1, r0, r2)
                r20.writeNoException()
                return r11
            L_0x00e1:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x00f7
                android.os.Parcelable$Creator<android.net.LinkAddress> r0 = android.net.LinkAddress.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.LinkAddress r0 = (android.net.LinkAddress) r0
                goto L_0x00f8
            L_0x00f7:
            L_0x00f8:
                java.lang.String r2 = r19.readString()
                r6.addAddressToTunnelInterface(r1, r0, r2)
                r20.writeNoException()
                return r11
            L_0x0103:
                r8.enforceInterface(r10)
                java.lang.String r13 = r19.readString()
                java.lang.String r14 = r19.readString()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x011e
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.Network r0 = (android.net.Network) r0
            L_0x011c:
                r3 = r0
                goto L_0x011f
            L_0x011e:
                goto L_0x011c
            L_0x011f:
                android.os.IBinder r15 = r19.readStrongBinder()
                java.lang.String r16 = r19.readString()
                r0 = r17
                r1 = r13
                r2 = r14
                r4 = r15
                r5 = r16
                android.net.IpSecTunnelInterfaceResponse r0 = r0.createTunnelInterface(r1, r2, r3, r4, r5)
                r20.writeNoException()
                if (r0 == 0) goto L_0x013e
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x0141
            L_0x013e:
                r9.writeInt(r12)
            L_0x0141:
                return r11
            L_0x0142:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.closeUdpEncapsulationSocket(r0)
                r20.writeNoException()
                return r11
            L_0x0150:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.net.IpSecUdpEncapResponse r2 = r6.openUdpEncapsulationSocket(r0, r1)
                r20.writeNoException()
                if (r2 == 0) goto L_0x016b
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x016e
            L_0x016b:
                r9.writeInt(r12)
            L_0x016e:
                return r11
            L_0x016f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.releaseSecurityParameterIndex(r0)
                r20.writeNoException()
                return r11
            L_0x017d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.net.IpSecSpiResponse r3 = r6.allocateSecurityParameterIndex(r0, r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x019c
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x019f
            L_0x019c:
                r9.writeInt(r12)
            L_0x019f:
                return r11
            L_0x01a0:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.IIpSecService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IIpSecService {
            public static IIpSecService sDefaultImpl;
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

            public IpSecSpiResponse allocateSecurityParameterIndex(String destinationAddress, int requestedSpi, IBinder binder) throws RemoteException {
                IpSecSpiResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(destinationAddress);
                    _data.writeInt(requestedSpi);
                    _data.writeStrongBinder(binder);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().allocateSecurityParameterIndex(destinationAddress, requestedSpi, binder);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IpSecSpiResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IpSecSpiResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseSecurityParameterIndex(int resourceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resourceId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseSecurityParameterIndex(resourceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IpSecUdpEncapResponse openUdpEncapsulationSocket(int port, IBinder binder) throws RemoteException {
                IpSecUdpEncapResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(port);
                    _data.writeStrongBinder(binder);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openUdpEncapsulationSocket(port, binder);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IpSecUdpEncapResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IpSecUdpEncapResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeUdpEncapsulationSocket(int resourceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resourceId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeUdpEncapsulationSocket(resourceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IpSecTunnelInterfaceResponse createTunnelInterface(String localAddr, String remoteAddr, Network underlyingNetwork, IBinder binder, String callingPackage) throws RemoteException {
                IpSecTunnelInterfaceResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(localAddr);
                    _data.writeString(remoteAddr);
                    if (underlyingNetwork != null) {
                        _data.writeInt(1);
                        underlyingNetwork.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(binder);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createTunnelInterface(localAddr, remoteAddr, underlyingNetwork, binder, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IpSecTunnelInterfaceResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IpSecTunnelInterfaceResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addAddressToTunnelInterface(int tunnelResourceId, LinkAddress localAddr, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tunnelResourceId);
                    if (localAddr != null) {
                        _data.writeInt(1);
                        localAddr.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addAddressToTunnelInterface(tunnelResourceId, localAddr, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeAddressFromTunnelInterface(int tunnelResourceId, LinkAddress localAddr, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tunnelResourceId);
                    if (localAddr != null) {
                        _data.writeInt(1);
                        localAddr.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeAddressFromTunnelInterface(tunnelResourceId, localAddr, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteTunnelInterface(int resourceId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resourceId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteTunnelInterface(resourceId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IpSecTransformResponse createTransform(IpSecConfig c, IBinder binder, String callingPackage) throws RemoteException {
                IpSecTransformResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (c != null) {
                        _data.writeInt(1);
                        c.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(binder);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createTransform(c, binder, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IpSecTransformResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IpSecTransformResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteTransform(int transformId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(transformId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteTransform(transformId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyTransportModeTransform(ParcelFileDescriptor socket, int direction, int transformId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (socket != null) {
                        _data.writeInt(1);
                        socket.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(direction);
                    _data.writeInt(transformId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyTransportModeTransform(socket, direction, transformId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyTunnelModeTransform(int tunnelResourceId, int direction, int transformResourceId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tunnelResourceId);
                    _data.writeInt(direction);
                    _data.writeInt(transformResourceId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyTunnelModeTransform(tunnelResourceId, direction, transformResourceId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeTransportModeTransforms(ParcelFileDescriptor socket) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (socket != null) {
                        _data.writeInt(1);
                        socket.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeTransportModeTransforms(socket);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IIpSecService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IIpSecService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
