package android.service.euicc;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.DownloadableSubscription;

public interface IEuiccService extends IInterface {
    void deleteSubscription(int i, String str, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException;

    void downloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, Bundle bundle, IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws RemoteException;

    void eraseSubscriptions(int i, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException;

    void getDefaultDownloadableSubscriptionList(int i, boolean z, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException;

    void getDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, boolean z, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException;

    void getEid(int i, IGetEidCallback iGetEidCallback) throws RemoteException;

    void getEuiccInfo(int i, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException;

    void getEuiccProfileInfoList(int i, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException;

    void getOtaStatus(int i, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException;

    void retainSubscriptionsForFactoryReset(int i, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException;

    void startOtaIfNecessary(int i, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException;

    void switchToSubscription(int i, String str, boolean z, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback) throws RemoteException;

    void updateSubscriptionNickname(int i, String str, String str2, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException;

    public static class Default implements IEuiccService {
        public void downloadSubscription(int slotId, DownloadableSubscription subscription, boolean switchAfterDownload, boolean forceDeactivateSim, Bundle resolvedBundle, IDownloadSubscriptionCallback callback) throws RemoteException {
        }

        public void getDownloadableSubscriptionMetadata(int slotId, DownloadableSubscription subscription, boolean forceDeactivateSim, IGetDownloadableSubscriptionMetadataCallback callback) throws RemoteException {
        }

        public void getEid(int slotId, IGetEidCallback callback) throws RemoteException {
        }

        public void getOtaStatus(int slotId, IGetOtaStatusCallback callback) throws RemoteException {
        }

        public void startOtaIfNecessary(int slotId, IOtaStatusChangedCallback statusChangedCallback) throws RemoteException {
        }

        public void getEuiccProfileInfoList(int slotId, IGetEuiccProfileInfoListCallback callback) throws RemoteException {
        }

        public void getDefaultDownloadableSubscriptionList(int slotId, boolean forceDeactivateSim, IGetDefaultDownloadableSubscriptionListCallback callback) throws RemoteException {
        }

        public void getEuiccInfo(int slotId, IGetEuiccInfoCallback callback) throws RemoteException {
        }

        public void deleteSubscription(int slotId, String iccid, IDeleteSubscriptionCallback callback) throws RemoteException {
        }

        public void switchToSubscription(int slotId, String iccid, boolean forceDeactivateSim, ISwitchToSubscriptionCallback callback) throws RemoteException {
        }

        public void updateSubscriptionNickname(int slotId, String iccid, String nickname, IUpdateSubscriptionNicknameCallback callback) throws RemoteException {
        }

        public void eraseSubscriptions(int slotId, IEraseSubscriptionsCallback callback) throws RemoteException {
        }

        public void retainSubscriptionsForFactoryReset(int slotId, IRetainSubscriptionsForFactoryResetCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEuiccService {
        private static final String DESCRIPTOR = "android.service.euicc.IEuiccService";
        static final int TRANSACTION_deleteSubscription = 9;
        static final int TRANSACTION_downloadSubscription = 1;
        static final int TRANSACTION_eraseSubscriptions = 12;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 7;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 3;
        static final int TRANSACTION_getEuiccInfo = 8;
        static final int TRANSACTION_getEuiccProfileInfoList = 6;
        static final int TRANSACTION_getOtaStatus = 4;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 13;
        static final int TRANSACTION_startOtaIfNecessary = 5;
        static final int TRANSACTION_switchToSubscription = 10;
        static final int TRANSACTION_updateSubscriptionNickname = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEuiccService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IEuiccService)) {
                return new Proxy(obj);
            }
            return (IEuiccService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "downloadSubscription";
                case 2:
                    return "getDownloadableSubscriptionMetadata";
                case 3:
                    return "getEid";
                case 4:
                    return "getOtaStatus";
                case 5:
                    return "startOtaIfNecessary";
                case 6:
                    return "getEuiccProfileInfoList";
                case 7:
                    return "getDefaultDownloadableSubscriptionList";
                case 8:
                    return "getEuiccInfo";
                case 9:
                    return "deleteSubscription";
                case 10:
                    return "switchToSubscription";
                case 11:
                    return "updateSubscriptionNickname";
                case 12:
                    return "eraseSubscriptions";
                case 13:
                    return "retainSubscriptionsForFactoryReset";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.telephony.euicc.DownloadableSubscription} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.telephony.euicc.DownloadableSubscription} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: android.telephony.euicc.DownloadableSubscription} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: android.telephony.euicc.DownloadableSubscription} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: android.telephony.euicc.DownloadableSubscription} */
        /* JADX WARNING: type inference failed for: r0v8, types: [android.os.Bundle] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r15, android.os.Parcel r16, android.os.Parcel r17, int r18) throws android.os.RemoteException {
            /*
                r14 = this;
                r7 = r14
                r8 = r15
                r9 = r16
                java.lang.String r10 = "android.service.euicc.IEuiccService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x017b
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0132;
                    case 2: goto L_0x0107;
                    case 3: goto L_0x00f4;
                    case 4: goto L_0x00e1;
                    case 5: goto L_0x00ce;
                    case 6: goto L_0x00bb;
                    case 7: goto L_0x00a0;
                    case 8: goto L_0x008d;
                    case 9: goto L_0x0076;
                    case 10: goto L_0x0057;
                    case 11: goto L_0x003c;
                    case 12: goto L_0x0029;
                    case 13: goto L_0x0016;
                    default: goto L_0x0011;
                }
            L_0x0011:
                boolean r0 = super.onTransact(r15, r16, r17, r18)
                return r0
            L_0x0016:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IRetainSubscriptionsForFactoryResetCallback r1 = android.service.euicc.IRetainSubscriptionsForFactoryResetCallback.Stub.asInterface(r1)
                r14.retainSubscriptionsForFactoryReset(r0, r1)
                return r11
            L_0x0029:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IEraseSubscriptionsCallback r1 = android.service.euicc.IEraseSubscriptionsCallback.Stub.asInterface(r1)
                r14.eraseSubscriptions(r0, r1)
                return r11
            L_0x003c:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                java.lang.String r1 = r16.readString()
                java.lang.String r2 = r16.readString()
                android.os.IBinder r3 = r16.readStrongBinder()
                android.service.euicc.IUpdateSubscriptionNicknameCallback r3 = android.service.euicc.IUpdateSubscriptionNicknameCallback.Stub.asInterface(r3)
                r14.updateSubscriptionNickname(r0, r1, r2, r3)
                return r11
            L_0x0057:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                java.lang.String r2 = r16.readString()
                int r3 = r16.readInt()
                if (r3 == 0) goto L_0x006a
                r1 = r11
            L_0x006a:
                android.os.IBinder r3 = r16.readStrongBinder()
                android.service.euicc.ISwitchToSubscriptionCallback r3 = android.service.euicc.ISwitchToSubscriptionCallback.Stub.asInterface(r3)
                r14.switchToSubscription(r0, r2, r1, r3)
                return r11
            L_0x0076:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                java.lang.String r1 = r16.readString()
                android.os.IBinder r2 = r16.readStrongBinder()
                android.service.euicc.IDeleteSubscriptionCallback r2 = android.service.euicc.IDeleteSubscriptionCallback.Stub.asInterface(r2)
                r14.deleteSubscription(r0, r1, r2)
                return r11
            L_0x008d:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IGetEuiccInfoCallback r1 = android.service.euicc.IGetEuiccInfoCallback.Stub.asInterface(r1)
                r14.getEuiccInfo(r0, r1)
                return r11
            L_0x00a0:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                int r2 = r16.readInt()
                if (r2 == 0) goto L_0x00af
                r1 = r11
            L_0x00af:
                android.os.IBinder r2 = r16.readStrongBinder()
                android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback r2 = android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback.Stub.asInterface(r2)
                r14.getDefaultDownloadableSubscriptionList(r0, r1, r2)
                return r11
            L_0x00bb:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IGetEuiccProfileInfoListCallback r1 = android.service.euicc.IGetEuiccProfileInfoListCallback.Stub.asInterface(r1)
                r14.getEuiccProfileInfoList(r0, r1)
                return r11
            L_0x00ce:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IOtaStatusChangedCallback r1 = android.service.euicc.IOtaStatusChangedCallback.Stub.asInterface(r1)
                r14.startOtaIfNecessary(r0, r1)
                return r11
            L_0x00e1:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IGetOtaStatusCallback r1 = android.service.euicc.IGetOtaStatusCallback.Stub.asInterface(r1)
                r14.getOtaStatus(r0, r1)
                return r11
            L_0x00f4:
                r9.enforceInterface(r10)
                int r0 = r16.readInt()
                android.os.IBinder r1 = r16.readStrongBinder()
                android.service.euicc.IGetEidCallback r1 = android.service.euicc.IGetEidCallback.Stub.asInterface(r1)
                r14.getEid(r0, r1)
                return r11
            L_0x0107:
                r9.enforceInterface(r10)
                int r2 = r16.readInt()
                int r3 = r16.readInt()
                if (r3 == 0) goto L_0x011d
                android.os.Parcelable$Creator<android.telephony.euicc.DownloadableSubscription> r0 = android.telephony.euicc.DownloadableSubscription.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.euicc.DownloadableSubscription r0 = (android.telephony.euicc.DownloadableSubscription) r0
                goto L_0x011e
            L_0x011d:
            L_0x011e:
                int r3 = r16.readInt()
                if (r3 == 0) goto L_0x0126
                r1 = r11
            L_0x0126:
                android.os.IBinder r3 = r16.readStrongBinder()
                android.service.euicc.IGetDownloadableSubscriptionMetadataCallback r3 = android.service.euicc.IGetDownloadableSubscriptionMetadataCallback.Stub.asInterface(r3)
                r14.getDownloadableSubscriptionMetadata(r2, r0, r1, r3)
                return r11
            L_0x0132:
                r9.enforceInterface(r10)
                int r12 = r16.readInt()
                int r2 = r16.readInt()
                if (r2 == 0) goto L_0x0148
                android.os.Parcelable$Creator<android.telephony.euicc.DownloadableSubscription> r2 = android.telephony.euicc.DownloadableSubscription.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.telephony.euicc.DownloadableSubscription r2 = (android.telephony.euicc.DownloadableSubscription) r2
                goto L_0x0149
            L_0x0148:
                r2 = r0
            L_0x0149:
                int r3 = r16.readInt()
                if (r3 == 0) goto L_0x0151
                r3 = r11
                goto L_0x0152
            L_0x0151:
                r3 = r1
            L_0x0152:
                int r4 = r16.readInt()
                if (r4 == 0) goto L_0x015a
                r4 = r11
                goto L_0x015b
            L_0x015a:
                r4 = r1
            L_0x015b:
                int r1 = r16.readInt()
                if (r1 == 0) goto L_0x016b
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0169:
                r5 = r0
                goto L_0x016c
            L_0x016b:
                goto L_0x0169
            L_0x016c:
                android.os.IBinder r0 = r16.readStrongBinder()
                android.service.euicc.IDownloadSubscriptionCallback r13 = android.service.euicc.IDownloadSubscriptionCallback.Stub.asInterface(r0)
                r0 = r14
                r1 = r12
                r6 = r13
                r0.downloadSubscription(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x017b:
                r0 = r17
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.euicc.IEuiccService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IEuiccService {
            public static IEuiccService sDefaultImpl;
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

            public void downloadSubscription(int slotId, DownloadableSubscription subscription, boolean switchAfterDownload, boolean forceDeactivateSim, Bundle resolvedBundle, IDownloadSubscriptionCallback callback) throws RemoteException {
                DownloadableSubscription downloadableSubscription = subscription;
                Bundle bundle = resolvedBundle;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(slotId);
                        if (downloadableSubscription != null) {
                            _data.writeInt(1);
                            downloadableSubscription.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(switchAfterDownload ? 1 : 0);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = forceDeactivateSim;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        boolean z2 = switchAfterDownload;
                        boolean z3 = forceDeactivateSim;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(forceDeactivateSim ? 1 : 0);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                        try {
                            if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().downloadSubscription(slotId, subscription, switchAfterDownload, forceDeactivateSim, resolvedBundle, callback);
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = slotId;
                    boolean z22 = switchAfterDownload;
                    boolean z32 = forceDeactivateSim;
                    _data.recycle();
                    throw th;
                }
            }

            public void getDownloadableSubscriptionMetadata(int slotId, DownloadableSubscription subscription, boolean forceDeactivateSim, IGetDownloadableSubscriptionMetadataCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (subscription != null) {
                        _data.writeInt(1);
                        subscription.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(forceDeactivateSim);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getDownloadableSubscriptionMetadata(slotId, subscription, forceDeactivateSim, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getEid(int slotId, IGetEidCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getEid(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getOtaStatus(int slotId, IGetOtaStatusCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getOtaStatus(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startOtaIfNecessary(int slotId, IOtaStatusChangedCallback statusChangedCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(statusChangedCallback != null ? statusChangedCallback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startOtaIfNecessary(slotId, statusChangedCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getEuiccProfileInfoList(int slotId, IGetEuiccProfileInfoListCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getEuiccProfileInfoList(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getDefaultDownloadableSubscriptionList(int slotId, boolean forceDeactivateSim, IGetDefaultDownloadableSubscriptionListCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(forceDeactivateSim);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getDefaultDownloadableSubscriptionList(slotId, forceDeactivateSim, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getEuiccInfo(int slotId, IGetEuiccInfoCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getEuiccInfo(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void deleteSubscription(int slotId, String iccid, IDeleteSubscriptionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeString(iccid);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().deleteSubscription(slotId, iccid, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void switchToSubscription(int slotId, String iccid, boolean forceDeactivateSim, ISwitchToSubscriptionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeString(iccid);
                    _data.writeInt(forceDeactivateSim);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().switchToSubscription(slotId, iccid, forceDeactivateSim, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateSubscriptionNickname(int slotId, String iccid, String nickname, IUpdateSubscriptionNicknameCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeString(iccid);
                    _data.writeString(nickname);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateSubscriptionNickname(slotId, iccid, nickname, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void eraseSubscriptions(int slotId, IEraseSubscriptionsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().eraseSubscriptions(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void retainSubscriptionsForFactoryReset(int slotId, IRetainSubscriptionsForFactoryResetCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().retainSubscriptionsForFactoryReset(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IEuiccService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IEuiccService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
