package android.telephony.data;

import android.net.LinkProperties;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IDataService extends IInterface {
    void createDataServiceProvider(int i) throws RemoteException;

    void deactivateDataCall(int i, int i2, int i3, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void registerForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void removeDataServiceProvider(int i) throws RemoteException;

    void requestDataCallList(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void setDataProfile(int i, List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void setInitialAttachApn(int i, DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void setupDataCall(int i, int i2, DataProfile dataProfile, boolean z, boolean z2, int i3, LinkProperties linkProperties, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void unregisterForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    public static class Default implements IDataService {
        public void createDataServiceProvider(int slotId) throws RemoteException {
        }

        public void removeDataServiceProvider(int slotId) throws RemoteException {
        }

        public void setupDataCall(int slotId, int accessNetwork, DataProfile dataProfile, boolean isRoaming, boolean allowRoaming, int reason, LinkProperties linkProperties, IDataServiceCallback callback) throws RemoteException {
        }

        public void deactivateDataCall(int slotId, int cid, int reason, IDataServiceCallback callback) throws RemoteException {
        }

        public void setInitialAttachApn(int slotId, DataProfile dataProfile, boolean isRoaming, IDataServiceCallback callback) throws RemoteException {
        }

        public void setDataProfile(int slotId, List<DataProfile> list, boolean isRoaming, IDataServiceCallback callback) throws RemoteException {
        }

        public void requestDataCallList(int slotId, IDataServiceCallback callback) throws RemoteException {
        }

        public void registerForDataCallListChanged(int slotId, IDataServiceCallback callback) throws RemoteException {
        }

        public void unregisterForDataCallListChanged(int slotId, IDataServiceCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDataService {
        private static final String DESCRIPTOR = "android.telephony.data.IDataService";
        static final int TRANSACTION_createDataServiceProvider = 1;
        static final int TRANSACTION_deactivateDataCall = 4;
        static final int TRANSACTION_registerForDataCallListChanged = 8;
        static final int TRANSACTION_removeDataServiceProvider = 2;
        static final int TRANSACTION_requestDataCallList = 7;
        static final int TRANSACTION_setDataProfile = 6;
        static final int TRANSACTION_setInitialAttachApn = 5;
        static final int TRANSACTION_setupDataCall = 3;
        static final int TRANSACTION_unregisterForDataCallListChanged = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDataService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IDataService)) {
                return new Proxy(obj);
            }
            return (IDataService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createDataServiceProvider";
                case 2:
                    return "removeDataServiceProvider";
                case 3:
                    return "setupDataCall";
                case 4:
                    return "deactivateDataCall";
                case 5:
                    return "setInitialAttachApn";
                case 6:
                    return "setDataProfile";
                case 7:
                    return "requestDataCallList";
                case 8:
                    return "registerForDataCallListChanged";
                case 9:
                    return "unregisterForDataCallListChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.telephony.data.DataProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.telephony.data.DataProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: android.net.LinkProperties} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.telephony.data.DataProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: android.telephony.data.DataProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.telephony.data.DataProfile} */
        /* JADX WARNING: type inference failed for: r0v10, types: [android.net.LinkProperties] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r9 = r18
                r10 = r19
                r11 = r20
                java.lang.String r12 = "android.telephony.data.IDataService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r10 == r0) goto L_0x0125
                r0 = 0
                r1 = 0
                switch(r10) {
                    case 1: goto L_0x011a;
                    case 2: goto L_0x010f;
                    case 3: goto L_0x00b8;
                    case 4: goto L_0x009d;
                    case 5: goto L_0x0072;
                    case 6: goto L_0x0051;
                    case 7: goto L_0x003e;
                    case 8: goto L_0x002b;
                    case 9: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x0018:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r1 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r1)
                r9.unregisterForDataCallListChanged(r0, r1)
                return r13
            L_0x002b:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r1 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r1)
                r9.registerForDataCallListChanged(r0, r1)
                return r13
            L_0x003e:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r1 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r1)
                r9.requestDataCallList(r0, r1)
                return r13
            L_0x0051:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                android.os.Parcelable$Creator<android.telephony.data.DataProfile> r2 = android.telephony.data.DataProfile.CREATOR
                java.util.ArrayList r2 = r11.createTypedArrayList(r2)
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0066
                r1 = r13
            L_0x0066:
                android.os.IBinder r3 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r3 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r3)
                r9.setDataProfile(r0, r2, r1, r3)
                return r13
            L_0x0072:
                r11.enforceInterface(r12)
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0088
                android.os.Parcelable$Creator<android.telephony.data.DataProfile> r0 = android.telephony.data.DataProfile.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.telephony.data.DataProfile r0 = (android.telephony.data.DataProfile) r0
                goto L_0x0089
            L_0x0088:
            L_0x0089:
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0091
                r1 = r13
            L_0x0091:
                android.os.IBinder r3 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r3 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r3)
                r9.setInitialAttachApn(r2, r0, r1, r3)
                return r13
            L_0x009d:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r3 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r3)
                r9.deactivateDataCall(r0, r1, r2, r3)
                return r13
            L_0x00b8:
                r11.enforceInterface(r12)
                int r14 = r20.readInt()
                int r15 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00d3
                android.os.Parcelable$Creator<android.telephony.data.DataProfile> r2 = android.telephony.data.DataProfile.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r11)
                android.telephony.data.DataProfile r2 = (android.telephony.data.DataProfile) r2
                r3 = r2
                goto L_0x00d4
            L_0x00d3:
                r3 = r0
            L_0x00d4:
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00dc
                r4 = r13
                goto L_0x00dd
            L_0x00dc:
                r4 = r1
            L_0x00dd:
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00e5
                r5 = r13
                goto L_0x00e6
            L_0x00e5:
                r5 = r1
            L_0x00e6:
                int r16 = r20.readInt()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x00fa
                android.os.Parcelable$Creator<android.net.LinkProperties> r0 = android.net.LinkProperties.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.net.LinkProperties r0 = (android.net.LinkProperties) r0
            L_0x00f8:
                r7 = r0
                goto L_0x00fb
            L_0x00fa:
                goto L_0x00f8
            L_0x00fb:
                android.os.IBinder r0 = r20.readStrongBinder()
                android.telephony.data.IDataServiceCallback r17 = android.telephony.data.IDataServiceCallback.Stub.asInterface(r0)
                r0 = r18
                r1 = r14
                r2 = r15
                r6 = r16
                r8 = r17
                r0.setupDataCall(r1, r2, r3, r4, r5, r6, r7, r8)
                return r13
            L_0x010f:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                r9.removeDataServiceProvider(r0)
                return r13
            L_0x011a:
                r11.enforceInterface(r12)
                int r0 = r20.readInt()
                r9.createDataServiceProvider(r0)
                return r13
            L_0x0125:
                r0 = r21
                r0.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.data.IDataService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IDataService {
            public static IDataService sDefaultImpl;
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

            public void createDataServiceProvider(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createDataServiceProvider(slotId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeDataServiceProvider(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeDataServiceProvider(slotId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setupDataCall(int slotId, int accessNetwork, DataProfile dataProfile, boolean isRoaming, boolean allowRoaming, int reason, LinkProperties linkProperties, IDataServiceCallback callback) throws RemoteException {
                DataProfile dataProfile2 = dataProfile;
                LinkProperties linkProperties2 = linkProperties;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(slotId);
                        try {
                            _data.writeInt(accessNetwork);
                            if (dataProfile2 != null) {
                                _data.writeInt(1);
                                dataProfile2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            boolean z = isRoaming;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(isRoaming ? 1 : 0);
                            _data.writeInt(allowRoaming ? 1 : 0);
                            _data.writeInt(reason);
                            if (linkProperties2 != null) {
                                _data.writeInt(1);
                                linkProperties2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                            if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setupDataCall(slotId, accessNetwork, dataProfile, isRoaming, allowRoaming, reason, linkProperties, callback);
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i = accessNetwork;
                        boolean z2 = isRoaming;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i2 = slotId;
                    int i3 = accessNetwork;
                    boolean z22 = isRoaming;
                    _data.recycle();
                    throw th;
                }
            }

            public void deactivateDataCall(int slotId, int cid, int reason, IDataServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(cid);
                    _data.writeInt(reason);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().deactivateDataCall(slotId, cid, reason, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setInitialAttachApn(int slotId, DataProfile dataProfile, boolean isRoaming, IDataServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (dataProfile != null) {
                        _data.writeInt(1);
                        dataProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isRoaming);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setInitialAttachApn(slotId, dataProfile, isRoaming, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setDataProfile(int slotId, List<DataProfile> dps, boolean isRoaming, IDataServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeTypedList(dps);
                    _data.writeInt(isRoaming);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDataProfile(slotId, dps, isRoaming, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestDataCallList(int slotId, IDataServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestDataCallList(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void registerForDataCallListChanged(int slotId, IDataServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().registerForDataCallListChanged(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unregisterForDataCallListChanged(int slotId, IDataServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterForDataCallListChanged(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDataService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IDataService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
