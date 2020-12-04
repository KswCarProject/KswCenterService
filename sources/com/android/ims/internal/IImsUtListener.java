package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;

public interface IImsUtListener extends IInterface {
    void onSupplementaryServiceIndication(ImsSsData imsSsData) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationCallBarringQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationCallForwardQueried(IImsUt iImsUt, int i, ImsCallForwardInfo[] imsCallForwardInfoArr) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationCallWaitingQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationQueried(IImsUt iImsUt, int i, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationQueryFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationUpdateFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void utConfigurationUpdated(IImsUt iImsUt, int i) throws RemoteException;

    public static class Default implements IImsUtListener {
        public void utConfigurationUpdated(IImsUt ut, int id) throws RemoteException {
        }

        public void utConfigurationUpdateFailed(IImsUt ut, int id, ImsReasonInfo error) throws RemoteException {
        }

        public void utConfigurationQueried(IImsUt ut, int id, Bundle ssInfo) throws RemoteException {
        }

        public void utConfigurationQueryFailed(IImsUt ut, int id, ImsReasonInfo error) throws RemoteException {
        }

        public void utConfigurationCallBarringQueried(IImsUt ut, int id, ImsSsInfo[] cbInfo) throws RemoteException {
        }

        public void utConfigurationCallForwardQueried(IImsUt ut, int id, ImsCallForwardInfo[] cfInfo) throws RemoteException {
        }

        public void utConfigurationCallWaitingQueried(IImsUt ut, int id, ImsSsInfo[] cwInfo) throws RemoteException {
        }

        public void onSupplementaryServiceIndication(ImsSsData ssData) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsUtListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsUtListener";
        static final int TRANSACTION_onSupplementaryServiceIndication = 8;
        static final int TRANSACTION_utConfigurationCallBarringQueried = 5;
        static final int TRANSACTION_utConfigurationCallForwardQueried = 6;
        static final int TRANSACTION_utConfigurationCallWaitingQueried = 7;
        static final int TRANSACTION_utConfigurationQueried = 3;
        static final int TRANSACTION_utConfigurationQueryFailed = 4;
        static final int TRANSACTION_utConfigurationUpdateFailed = 2;
        static final int TRANSACTION_utConfigurationUpdated = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsUtListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsUtListener)) {
                return new Proxy(obj);
            }
            return (IImsUtListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "utConfigurationUpdated";
                case 2:
                    return "utConfigurationUpdateFailed";
                case 3:
                    return "utConfigurationQueried";
                case 4:
                    return "utConfigurationQueryFailed";
                case 5:
                    return "utConfigurationCallBarringQueried";
                case 6:
                    return "utConfigurationCallForwardQueried";
                case 7:
                    return "utConfigurationCallWaitingQueried";
                case 8:
                    return "onSupplementaryServiceIndication";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.telephony.ims.ImsSsData} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.ims.internal.IImsUtListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00f5
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x00e2;
                    case 2: goto L_0x00bf;
                    case 3: goto L_0x009c;
                    case 4: goto L_0x0079;
                    case 5: goto L_0x005e;
                    case 6: goto L_0x0043;
                    case 7: goto L_0x0028;
                    case 8: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.telephony.ims.ImsSsData> r1 = android.telephony.ims.ImsSsData.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsSsData r1 = (android.telephony.ims.ImsSsData) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r6.onSupplementaryServiceIndication(r1)
                return r2
            L_0x0028:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r1 = com.android.ims.internal.IImsUt.Stub.asInterface(r1)
                int r3 = r8.readInt()
                android.os.Parcelable$Creator<android.telephony.ims.ImsSsInfo> r4 = android.telephony.ims.ImsSsInfo.CREATOR
                java.lang.Object[] r4 = r8.createTypedArray(r4)
                android.telephony.ims.ImsSsInfo[] r4 = (android.telephony.ims.ImsSsInfo[]) r4
                r6.utConfigurationCallWaitingQueried(r1, r3, r4)
                return r2
            L_0x0043:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r1 = com.android.ims.internal.IImsUt.Stub.asInterface(r1)
                int r3 = r8.readInt()
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallForwardInfo> r4 = android.telephony.ims.ImsCallForwardInfo.CREATOR
                java.lang.Object[] r4 = r8.createTypedArray(r4)
                android.telephony.ims.ImsCallForwardInfo[] r4 = (android.telephony.ims.ImsCallForwardInfo[]) r4
                r6.utConfigurationCallForwardQueried(r1, r3, r4)
                return r2
            L_0x005e:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r1 = com.android.ims.internal.IImsUt.Stub.asInterface(r1)
                int r3 = r8.readInt()
                android.os.Parcelable$Creator<android.telephony.ims.ImsSsInfo> r4 = android.telephony.ims.ImsSsInfo.CREATOR
                java.lang.Object[] r4 = r8.createTypedArray(r4)
                android.telephony.ims.ImsSsInfo[] r4 = (android.telephony.ims.ImsSsInfo[]) r4
                r6.utConfigurationCallBarringQueried(r1, r3, r4)
                return r2
            L_0x0079:
                r8.enforceInterface(r0)
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r3 = com.android.ims.internal.IImsUt.Stub.asInterface(r3)
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0097
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x0098
            L_0x0097:
            L_0x0098:
                r6.utConfigurationQueryFailed(r3, r4, r1)
                return r2
            L_0x009c:
                r8.enforceInterface(r0)
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r3 = com.android.ims.internal.IImsUt.Stub.asInterface(r3)
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00ba
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00bb
            L_0x00ba:
            L_0x00bb:
                r6.utConfigurationQueried(r3, r4, r1)
                return r2
            L_0x00bf:
                r8.enforceInterface(r0)
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r3 = com.android.ims.internal.IImsUt.Stub.asInterface(r3)
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00dd
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x00de
            L_0x00dd:
            L_0x00de:
                r6.utConfigurationUpdateFailed(r3, r4, r1)
                return r2
            L_0x00e2:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                com.android.ims.internal.IImsUt r1 = com.android.ims.internal.IImsUt.Stub.asInterface(r1)
                int r3 = r8.readInt()
                r6.utConfigurationUpdated(r1, r3)
                return r2
            L_0x00f5:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.IImsUtListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsUtListener {
            public static IImsUtListener sDefaultImpl;
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

            public void utConfigurationUpdated(IImsUt ut, int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationUpdated(ut, id);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void utConfigurationUpdateFailed(IImsUt ut, int id, ImsReasonInfo error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    if (error != null) {
                        _data.writeInt(1);
                        error.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationUpdateFailed(ut, id, error);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void utConfigurationQueried(IImsUt ut, int id, Bundle ssInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    if (ssInfo != null) {
                        _data.writeInt(1);
                        ssInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationQueried(ut, id, ssInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void utConfigurationQueryFailed(IImsUt ut, int id, ImsReasonInfo error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    if (error != null) {
                        _data.writeInt(1);
                        error.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationQueryFailed(ut, id, error);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void utConfigurationCallBarringQueried(IImsUt ut, int id, ImsSsInfo[] cbInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    _data.writeTypedArray(cbInfo, 0);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationCallBarringQueried(ut, id, cbInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void utConfigurationCallForwardQueried(IImsUt ut, int id, ImsCallForwardInfo[] cfInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    _data.writeTypedArray(cfInfo, 0);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationCallForwardQueried(ut, id, cfInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void utConfigurationCallWaitingQueried(IImsUt ut, int id, ImsSsInfo[] cwInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(ut != null ? ut.asBinder() : null);
                    _data.writeInt(id);
                    _data.writeTypedArray(cwInfo, 0);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().utConfigurationCallWaitingQueried(ut, id, cwInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSupplementaryServiceIndication(ImsSsData ssData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ssData != null) {
                        _data.writeInt(1);
                        ssData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSupplementaryServiceIndication(ssData);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsUtListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsUtListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
