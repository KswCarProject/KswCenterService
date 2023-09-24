package com.android.internal.telephony.euicc;

import android.app.PendingIntent;
import android.content.Intent;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;

/* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    public static class Default implements IEuiccController {
        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void continueOperation(int cardId, Intent resolutionIntent, Bundle resolutionExtras) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void getDownloadableSubscriptionMetadata(int cardId, DownloadableSubscription subscription, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void getDefaultDownloadableSubscriptionList(int cardId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public String getEid(int cardId, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public int getOtaStatus(int cardId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void downloadSubscription(int cardId, DownloadableSubscription subscription, boolean switchAfterDownload, String callingPackage, Bundle resolvedBundle, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public EuiccInfo getEuiccInfo(int cardId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void deleteSubscription(int cardId, int subscriptionId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void switchToSubscription(int cardId, int subscriptionId, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void updateSubscriptionNickname(int cardId, int subscriptionId, String nickname, String callingPackage, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void eraseSubscriptions(int cardId, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void retainSubscriptionsForFactoryReset(int cardId, PendingIntent callbackIntent) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
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
            if (iin != null && (iin instanceof IEuiccController)) {
                return (IEuiccController) iin;
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Intent _arg1;
            DownloadableSubscription _arg12;
            DownloadableSubscription _arg13;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg1 = Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    Bundle _arg2 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    continueOperation(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg12 = DownloadableSubscription.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    String _arg22 = data.readString();
                    PendingIntent _arg3 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    getDownloadableSubscriptionMetadata(_arg02, _arg12, _arg22, _arg3);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    String _arg14 = data.readString();
                    PendingIntent _arg23 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    getDefaultDownloadableSubscriptionList(_arg03, _arg14, _arg23);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    String _arg15 = data.readString();
                    String _result = getEid(_arg04, _arg15);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    int _result2 = getOtaStatus(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg13 = DownloadableSubscription.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    boolean _arg24 = data.readInt() != 0;
                    String _arg32 = data.readString();
                    Bundle _arg4 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    PendingIntent _arg5 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    downloadSubscription(_arg06, _arg13, _arg24, _arg32, _arg4, _arg5);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    EuiccInfo _result3 = getEuiccInfo(_arg07);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg08 = data.readInt();
                    int _arg16 = data.readInt();
                    String _arg25 = data.readString();
                    PendingIntent _arg33 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    deleteSubscription(_arg08, _arg16, _arg25, _arg33);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg09 = data.readInt();
                    int _arg17 = data.readInt();
                    String _arg26 = data.readString();
                    PendingIntent _arg34 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    switchToSubscription(_arg09, _arg17, _arg26, _arg34);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg010 = data.readInt();
                    int _arg18 = data.readInt();
                    String _arg27 = data.readString();
                    String _arg35 = data.readString();
                    PendingIntent _arg42 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    updateSubscriptionNickname(_arg010, _arg18, _arg27, _arg35, _arg42);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg011 = data.readInt();
                    PendingIntent _arg19 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    eraseSubscriptions(_arg011, _arg19);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg012 = data.readInt();
                    PendingIntent _arg110 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    retainSubscriptionsForFactoryReset(_arg012, _arg110);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IEuiccController {
            public static IEuiccController sDefaultImpl;
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

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().continueOperation(cardId, resolutionIntent, resolutionExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getDownloadableSubscriptionMetadata(cardId, subscription, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getDefaultDownloadableSubscriptionList(cardId, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public String getEid(int cardId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEid(cardId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public int getOtaStatus(int cardId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOtaStatus(cardId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void downloadSubscription(int cardId, DownloadableSubscription subscription, boolean switchAfterDownload, String callingPackage, Bundle resolvedBundle, PendingIntent callbackIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(cardId);
                        if (subscription != null) {
                            _data.writeInt(1);
                            subscription.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(switchAfterDownload ? 1 : 0);
                        try {
                            _data.writeString(callingPackage);
                            if (resolvedBundle != null) {
                                _data.writeInt(1);
                                resolvedBundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (callbackIntent != null) {
                                _data.writeInt(1);
                                callbackIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        boolean _status = this.mRemote.transact(6, _data, null, 1);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().downloadSubscription(cardId, subscription, switchAfterDownload, callingPackage, resolvedBundle, callbackIntent);
                            _data.recycle();
                            return;
                        }
                        _data.recycle();
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public EuiccInfo getEuiccInfo(int cardId) throws RemoteException {
                EuiccInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cardId);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEuiccInfo(cardId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = EuiccInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteSubscription(cardId, subscriptionId, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().switchToSubscription(cardId, subscriptionId, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSubscriptionNickname(cardId, subscriptionId, nickname, callingPackage, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().eraseSubscriptions(cardId, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
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
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().retainSubscriptionsForFactoryReset(cardId, callbackIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IEuiccController impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IEuiccController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
