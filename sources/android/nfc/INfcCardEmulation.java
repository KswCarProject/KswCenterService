package android.nfc;

import android.content.ComponentName;
import android.nfc.cardemulation.AidGroup;
import android.nfc.cardemulation.ApduServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface INfcCardEmulation extends IInterface {
    AidGroup getAidGroupForService(int i, ComponentName componentName, String str) throws RemoteException;

    List<ApduServiceInfo> getServices(int i, String str) throws RemoteException;

    boolean isDefaultServiceForAid(int i, ComponentName componentName, String str) throws RemoteException;

    boolean isDefaultServiceForCategory(int i, ComponentName componentName, String str) throws RemoteException;

    boolean registerAidGroupForService(int i, ComponentName componentName, AidGroup aidGroup) throws RemoteException;

    boolean removeAidGroupForService(int i, ComponentName componentName, String str) throws RemoteException;

    boolean setDefaultForNextTap(int i, ComponentName componentName) throws RemoteException;

    boolean setDefaultServiceForCategory(int i, ComponentName componentName, String str) throws RemoteException;

    boolean setOffHostForService(int i, ComponentName componentName, String str) throws RemoteException;

    boolean setPreferredService(ComponentName componentName) throws RemoteException;

    boolean supportsAidPrefixRegistration() throws RemoteException;

    boolean unsetOffHostForService(int i, ComponentName componentName) throws RemoteException;

    boolean unsetPreferredService() throws RemoteException;

    public static class Default implements INfcCardEmulation {
        public boolean isDefaultServiceForCategory(int userHandle, ComponentName service, String category) throws RemoteException {
            return false;
        }

        public boolean isDefaultServiceForAid(int userHandle, ComponentName service, String aid) throws RemoteException {
            return false;
        }

        public boolean setDefaultServiceForCategory(int userHandle, ComponentName service, String category) throws RemoteException {
            return false;
        }

        public boolean setDefaultForNextTap(int userHandle, ComponentName service) throws RemoteException {
            return false;
        }

        public boolean registerAidGroupForService(int userHandle, ComponentName service, AidGroup aidGroup) throws RemoteException {
            return false;
        }

        public boolean setOffHostForService(int userHandle, ComponentName service, String offHostSecureElement) throws RemoteException {
            return false;
        }

        public boolean unsetOffHostForService(int userHandle, ComponentName service) throws RemoteException {
            return false;
        }

        public AidGroup getAidGroupForService(int userHandle, ComponentName service, String category) throws RemoteException {
            return null;
        }

        public boolean removeAidGroupForService(int userHandle, ComponentName service, String category) throws RemoteException {
            return false;
        }

        public List<ApduServiceInfo> getServices(int userHandle, String category) throws RemoteException {
            return null;
        }

        public boolean setPreferredService(ComponentName service) throws RemoteException {
            return false;
        }

        public boolean unsetPreferredService() throws RemoteException {
            return false;
        }

        public boolean supportsAidPrefixRegistration() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INfcCardEmulation {
        private static final String DESCRIPTOR = "android.nfc.INfcCardEmulation";
        static final int TRANSACTION_getAidGroupForService = 8;
        static final int TRANSACTION_getServices = 10;
        static final int TRANSACTION_isDefaultServiceForAid = 2;
        static final int TRANSACTION_isDefaultServiceForCategory = 1;
        static final int TRANSACTION_registerAidGroupForService = 5;
        static final int TRANSACTION_removeAidGroupForService = 9;
        static final int TRANSACTION_setDefaultForNextTap = 4;
        static final int TRANSACTION_setDefaultServiceForCategory = 3;
        static final int TRANSACTION_setOffHostForService = 6;
        static final int TRANSACTION_setPreferredService = 11;
        static final int TRANSACTION_supportsAidPrefixRegistration = 13;
        static final int TRANSACTION_unsetOffHostForService = 7;
        static final int TRANSACTION_unsetPreferredService = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INfcCardEmulation asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INfcCardEmulation)) {
                return new Proxy(obj);
            }
            return (INfcCardEmulation) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isDefaultServiceForCategory";
                case 2:
                    return "isDefaultServiceForAid";
                case 3:
                    return "setDefaultServiceForCategory";
                case 4:
                    return "setDefaultForNextTap";
                case 5:
                    return "registerAidGroupForService";
                case 6:
                    return "setOffHostForService";
                case 7:
                    return "unsetOffHostForService";
                case 8:
                    return "getAidGroupForService";
                case 9:
                    return "removeAidGroupForService";
                case 10:
                    return "getServices";
                case 11:
                    return "setPreferredService";
                case 12:
                    return "unsetPreferredService";
                case 13:
                    return "supportsAidPrefixRegistration";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.nfc.cardemulation.AidGroup} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v54 */
        /* JADX WARNING: type inference failed for: r1v55 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.nfc.INfcCardEmulation"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x01c5
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x019f;
                    case 2: goto L_0x0179;
                    case 3: goto L_0x0153;
                    case 4: goto L_0x0131;
                    case 5: goto L_0x00ff;
                    case 6: goto L_0x00d9;
                    case 7: goto L_0x00b7;
                    case 8: goto L_0x0087;
                    case 9: goto L_0x0061;
                    case 10: goto L_0x004b;
                    case 11: goto L_0x002d;
                    case 12: goto L_0x001f;
                    case 13: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                boolean r1 = r7.supportsAidPrefixRegistration()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x001f:
                r9.enforceInterface(r0)
                boolean r1 = r7.unsetPreferredService()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x002d:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x003f
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0040
            L_0x003f:
            L_0x0040:
                boolean r3 = r7.setPreferredService(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x004b:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                java.lang.String r3 = r9.readString()
                java.util.List r4 = r7.getServices(r1, r3)
                r10.writeNoException()
                r10.writeTypedList(r4)
                return r2
            L_0x0061:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0077
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0078
            L_0x0077:
            L_0x0078:
                java.lang.String r4 = r9.readString()
                boolean r5 = r7.removeAidGroupForService(r3, r1, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x0087:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x009d
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x009e
            L_0x009d:
            L_0x009e:
                java.lang.String r4 = r9.readString()
                android.nfc.cardemulation.AidGroup r5 = r7.getAidGroupForService(r3, r1, r4)
                r10.writeNoException()
                if (r5 == 0) goto L_0x00b2
                r10.writeInt(r2)
                r5.writeToParcel(r10, r2)
                goto L_0x00b6
            L_0x00b2:
                r6 = 0
                r10.writeInt(r6)
            L_0x00b6:
                return r2
            L_0x00b7:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00cd
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x00ce
            L_0x00cd:
            L_0x00ce:
                boolean r4 = r7.unsetOffHostForService(r3, r1)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x00d9:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00ef
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x00f0
            L_0x00ef:
            L_0x00f0:
                java.lang.String r4 = r9.readString()
                boolean r5 = r7.setOffHostForService(r3, r1, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x00ff:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0115
                android.os.Parcelable$Creator<android.content.ComponentName> r4 = android.content.ComponentName.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.content.ComponentName r4 = (android.content.ComponentName) r4
                goto L_0x0116
            L_0x0115:
                r4 = r1
            L_0x0116:
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0125
                android.os.Parcelable$Creator<android.nfc.cardemulation.AidGroup> r1 = android.nfc.cardemulation.AidGroup.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.nfc.cardemulation.AidGroup r1 = (android.nfc.cardemulation.AidGroup) r1
                goto L_0x0126
            L_0x0125:
            L_0x0126:
                boolean r5 = r7.registerAidGroupForService(r3, r4, r1)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x0131:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0147
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0148
            L_0x0147:
            L_0x0148:
                boolean r4 = r7.setDefaultForNextTap(r3, r1)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x0153:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0169
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x016a
            L_0x0169:
            L_0x016a:
                java.lang.String r4 = r9.readString()
                boolean r5 = r7.setDefaultServiceForCategory(r3, r1, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x0179:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x018f
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0190
            L_0x018f:
            L_0x0190:
                java.lang.String r4 = r9.readString()
                boolean r5 = r7.isDefaultServiceForAid(r3, r1, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x019f:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x01b5
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x01b6
            L_0x01b5:
            L_0x01b6:
                java.lang.String r4 = r9.readString()
                boolean r5 = r7.isDefaultServiceForCategory(r3, r1, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x01c5:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.nfc.INfcCardEmulation.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INfcCardEmulation {
            public static INfcCardEmulation sDefaultImpl;
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

            public boolean isDefaultServiceForCategory(int userHandle, ComponentName service, String category) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(category);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDefaultServiceForCategory(userHandle, service, category);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isDefaultServiceForAid(int userHandle, ComponentName service, String aid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(aid);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDefaultServiceForAid(userHandle, service, aid);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setDefaultServiceForCategory(int userHandle, ComponentName service, String category) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(category);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDefaultServiceForCategory(userHandle, service, category);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setDefaultForNextTap(int userHandle, ComponentName service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDefaultForNextTap(userHandle, service);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean registerAidGroupForService(int userHandle, ComponentName service, AidGroup aidGroup) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (aidGroup != null) {
                        _data.writeInt(1);
                        aidGroup.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerAidGroupForService(userHandle, service, aidGroup);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setOffHostForService(int userHandle, ComponentName service, String offHostSecureElement) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(offHostSecureElement);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setOffHostForService(userHandle, service, offHostSecureElement);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unsetOffHostForService(int userHandle, ComponentName service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unsetOffHostForService(userHandle, service);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AidGroup getAidGroupForService(int userHandle, ComponentName service, String category) throws RemoteException {
                AidGroup _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(category);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAidGroupForService(userHandle, service, category);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AidGroup.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AidGroup _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeAidGroupForService(int userHandle, ComponentName service, String category) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(category);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeAidGroupForService(userHandle, service, category);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ApduServiceInfo> getServices(int userHandle, String category) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeString(category);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServices(userHandle, category);
                    }
                    _reply.readException();
                    List<ApduServiceInfo> _result = _reply.createTypedArrayList(ApduServiceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPreferredService(ComponentName service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPreferredService(service);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unsetPreferredService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unsetPreferredService();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supportsAidPrefixRegistration() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supportsAidPrefixRegistration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INfcCardEmulation impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INfcCardEmulation getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
