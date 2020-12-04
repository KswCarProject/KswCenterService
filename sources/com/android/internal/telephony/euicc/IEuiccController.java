package com.android.internal.telephony.euicc;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;

public interface IEuiccController extends IInterface {
    void continueOperation(int i, Intent intent, Bundle bundle) throws RemoteException;

    void deleteSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException;

    void downloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, String str, Bundle bundle, PendingIntent pendingIntent) throws RemoteException;

    void eraseSubscriptions(int i, PendingIntent pendingIntent) throws RemoteException;

    void getDefaultDownloadableSubscriptionList(int i, String str, PendingIntent pendingIntent) throws RemoteException;

    void getDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, String str, PendingIntent pendingIntent) throws RemoteException;

    String getEid(int i, String str) throws RemoteException;

    EuiccInfo getEuiccInfo(int i) throws RemoteException;

    int getOtaStatus(int i) throws RemoteException;

    void retainSubscriptionsForFactoryReset(int i, PendingIntent pendingIntent) throws RemoteException;

    void switchToSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException;

    void updateSubscriptionNickname(int i, int i2, String str, String str2, PendingIntent pendingIntent) throws RemoteException;

    public static class Default implements IEuiccController {
        public void continueOperation(int cardId, Intent resolutionIntent, Bundle resolutionExtras) throws RemoteException {
        }

        public void getDownloadableSubscriptionMetadata(int cardId, DownloadableSubscription subscription, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        public void getDefaultDownloadableSubscriptionList(int cardId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        public String getEid(int cardId, String callingPackage) throws RemoteException {
            return null;
        }

        public int getOtaStatus(int cardId) throws RemoteException {
            return 0;
        }

        public void downloadSubscription(int cardId, DownloadableSubscription subscription, boolean switchAfterDownload, String callingPackage, Bundle resolvedBundle, PendingIntent callbackIntent) throws RemoteException {
        }

        public EuiccInfo getEuiccInfo(int cardId) throws RemoteException {
            return null;
        }

        public void deleteSubscription(int cardId, int subscriptionId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        public void switchToSubscription(int cardId, int subscriptionId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        public void updateSubscriptionNickname(int cardId, int subscriptionId, String nickname, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        public void eraseSubscriptions(int cardId, PendingIntent callbackIntent) throws RemoteException {
        }

        public void retainSubscriptionsForFactoryReset(int cardId, PendingIntent callbackIntent) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEuiccController {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccController";
        static final int TRANSACTION_continueOperation = 1;
        static final int TRANSACTION_deleteSubscription = 8;
        static final int TRANSACTION_downloadSubscription = 6;
        static final int TRANSACTION_eraseSubscriptions = 11;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 3;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 4;
        static final int TRANSACTION_getEuiccInfo = 7;
        static final int TRANSACTION_getOtaStatus = 5;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 12;
        static final int TRANSACTION_switchToSubscription = 9;
        static final int TRANSACTION_updateSubscriptionNickname = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEuiccController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IEuiccController)) {
                return new Proxy(obj);
            }
            return (IEuiccController) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "continueOperation";
                case 2:
                    return "getDownloadableSubscriptionMetadata";
                case 3:
                    return "getDefaultDownloadableSubscriptionList";
                case 4:
                    return "getEid";
                case 5:
                    return "getOtaStatus";
                case 6:
                    return "downloadSubscription";
                case 7:
                    return "getEuiccInfo";
                case 8:
                    return "deleteSubscription";
                case 9:
                    return "switchToSubscription";
                case 10:
                    return "updateSubscriptionNickname";
                case 11:
                    return "eraseSubscriptions";
                case 12:
                    return "retainSubscriptionsForFactoryReset";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.app.PendingIntent} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v34 */
        /* JADX WARNING: type inference failed for: r1v35 */
        /* JADX WARNING: type inference failed for: r1v36 */
        /* JADX WARNING: type inference failed for: r1v37 */
        /* JADX WARNING: type inference failed for: r1v38 */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: type inference failed for: r1v40 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
            /*
                r16 = this;
                r7 = r16
                r8 = r17
                r9 = r18
                r10 = r19
                java.lang.String r11 = "com.android.internal.telephony.euicc.IEuiccController"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x01ce
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x01a3;
                    case 2: goto L_0x0174;
                    case 3: goto L_0x0155;
                    case 4: goto L_0x013f;
                    case 5: goto L_0x012d;
                    case 6: goto L_0x00df;
                    case 7: goto L_0x00c4;
                    case 8: goto L_0x00a1;
                    case 9: goto L_0x007e;
                    case 10: goto L_0x0050;
                    case 11: goto L_0x0035;
                    case 12: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r17, r18, r19, r20)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x0030
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0031
            L_0x0030:
            L_0x0031:
                r7.retainSubscriptionsForFactoryReset(r0, r1)
                return r12
            L_0x0035:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x004b
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x004c
            L_0x004b:
            L_0x004c:
                r7.eraseSubscriptions(r0, r1)
                return r12
            L_0x0050:
                r9.enforceInterface(r11)
                int r6 = r18.readInt()
                int r13 = r18.readInt()
                java.lang.String r14 = r18.readString()
                java.lang.String r15 = r18.readString()
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x0073
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r5 = r0
                goto L_0x0074
            L_0x0073:
                r5 = r1
            L_0x0074:
                r0 = r16
                r1 = r6
                r2 = r13
                r3 = r14
                r4 = r15
                r0.updateSubscriptionNickname(r1, r2, r3, r4, r5)
                return r12
            L_0x007e:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                java.lang.String r3 = r18.readString()
                int r4 = r18.readInt()
                if (r4 == 0) goto L_0x009c
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x009d
            L_0x009c:
            L_0x009d:
                r7.switchToSubscription(r0, r2, r3, r1)
                return r12
            L_0x00a1:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                java.lang.String r3 = r18.readString()
                int r4 = r18.readInt()
                if (r4 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x00c0
            L_0x00bf:
            L_0x00c0:
                r7.deleteSubscription(r0, r2, r3, r1)
                return r12
            L_0x00c4:
                r9.enforceInterface(r11)
                int r1 = r18.readInt()
                android.telephony.euicc.EuiccInfo r2 = r7.getEuiccInfo(r1)
                r19.writeNoException()
                if (r2 == 0) goto L_0x00db
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x00de
            L_0x00db:
                r10.writeInt(r0)
            L_0x00de:
                return r12
            L_0x00df:
                r9.enforceInterface(r11)
                int r13 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x00f5
                android.os.Parcelable$Creator<android.telephony.euicc.DownloadableSubscription> r2 = android.telephony.euicc.DownloadableSubscription.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.telephony.euicc.DownloadableSubscription r2 = (android.telephony.euicc.DownloadableSubscription) r2
                goto L_0x00f6
            L_0x00f5:
                r2 = r1
            L_0x00f6:
                int r3 = r18.readInt()
                if (r3 == 0) goto L_0x00fe
                r3 = r12
                goto L_0x00ff
            L_0x00fe:
                r3 = r0
            L_0x00ff:
                java.lang.String r14 = r18.readString()
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x0113
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r5 = r0
                goto L_0x0114
            L_0x0113:
                r5 = r1
            L_0x0114:
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x0124
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r6 = r0
                goto L_0x0125
            L_0x0124:
                r6 = r1
            L_0x0125:
                r0 = r16
                r1 = r13
                r4 = r14
                r0.downloadSubscription(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x012d:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r1 = r7.getOtaStatus(r0)
                r19.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x013f:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                java.lang.String r1 = r18.readString()
                java.lang.String r2 = r7.getEid(r0, r1)
                r19.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x0155:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                java.lang.String r2 = r18.readString()
                int r3 = r18.readInt()
                if (r3 == 0) goto L_0x016f
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0170
            L_0x016f:
            L_0x0170:
                r7.getDefaultDownloadableSubscriptionList(r0, r2, r1)
                return r12
            L_0x0174:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x018a
                android.os.Parcelable$Creator<android.telephony.euicc.DownloadableSubscription> r2 = android.telephony.euicc.DownloadableSubscription.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.telephony.euicc.DownloadableSubscription r2 = (android.telephony.euicc.DownloadableSubscription) r2
                goto L_0x018b
            L_0x018a:
                r2 = r1
            L_0x018b:
                java.lang.String r3 = r18.readString()
                int r4 = r18.readInt()
                if (r4 == 0) goto L_0x019e
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x019f
            L_0x019e:
            L_0x019f:
                r7.getDownloadableSubscriptionMetadata(r0, r2, r3, r1)
                return r12
            L_0x01a3:
                r9.enforceInterface(r11)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x01b9
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.content.Intent r2 = (android.content.Intent) r2
                goto L_0x01ba
            L_0x01b9:
                r2 = r1
            L_0x01ba:
                int r3 = r18.readInt()
                if (r3 == 0) goto L_0x01c9
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x01ca
            L_0x01c9:
            L_0x01ca:
                r7.continueOperation(r0, r2, r1)
                return r12
            L_0x01ce:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.euicc.IEuiccController.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IEuiccController {
            public static IEuiccController sDefaultImpl;
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

            public void continueOperation(int cardId, Intent resolutionIntent, Bundle resolutionExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    if (resolutionIntent != null) {
                        _data.writeInt(1);
                        resolutionIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (resolutionExtras != null) {
                        _data.writeInt(1);
                        resolutionExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().continueOperation(cardId, resolutionIntent, resolutionExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getDownloadableSubscriptionMetadata(int cardId, DownloadableSubscription subscription, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    if (subscription != null) {
                        _data.writeInt(1);
                        subscription.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getDownloadableSubscriptionMetadata(cardId, subscription, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getDefaultDownloadableSubscriptionList(int cardId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    _data.writeString(callingPackage);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getDefaultDownloadableSubscriptionList(cardId, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public String getEid(int cardId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEid(cardId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getOtaStatus(int cardId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOtaStatus(cardId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void downloadSubscription(int cardId, DownloadableSubscription subscription, boolean switchAfterDownload, String callingPackage, Bundle resolvedBundle, PendingIntent callbackIntent) throws RemoteException {
                DownloadableSubscription downloadableSubscription = subscription;
                Bundle bundle = resolvedBundle;
                PendingIntent pendingIntent = callbackIntent;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(cardId);
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
                            String str = callingPackage;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        boolean z = switchAfterDownload;
                        String str2 = callingPackage;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(callingPackage);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (pendingIntent != null) {
                            _data.writeInt(1);
                            pendingIntent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().downloadSubscription(cardId, subscription, switchAfterDownload, callingPackage, resolvedBundle, callbackIntent);
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
                    int i = cardId;
                    boolean z2 = switchAfterDownload;
                    String str22 = callingPackage;
                    _data.recycle();
                    throw th;
                }
            }

            public EuiccInfo getEuiccInfo(int cardId) throws RemoteException {
                EuiccInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEuiccInfo(cardId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = EuiccInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    EuiccInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteSubscription(int cardId, int subscriptionId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    _data.writeInt(subscriptionId);
                    _data.writeString(callingPackage);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().deleteSubscription(cardId, subscriptionId, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void switchToSubscription(int cardId, int subscriptionId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    _data.writeInt(subscriptionId);
                    _data.writeString(callingPackage);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().switchToSubscription(cardId, subscriptionId, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateSubscriptionNickname(int cardId, int subscriptionId, String nickname, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    _data.writeInt(subscriptionId);
                    _data.writeString(nickname);
                    _data.writeString(callingPackage);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateSubscriptionNickname(cardId, subscriptionId, nickname, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void eraseSubscriptions(int cardId, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().eraseSubscriptions(cardId, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void retainSubscriptionsForFactoryReset(int cardId, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().retainSubscriptionsForFactoryReset(cardId, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IEuiccController impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IEuiccController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
