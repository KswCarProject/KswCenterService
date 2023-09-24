package com.android.internal.appwidget;

import android.appwidget.AppWidgetProviderInfo;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes4.dex */
public interface IAppWidgetHost extends IInterface {
    void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException;

    void providersChanged() throws RemoteException;

    void updateAppWidget(int i, RemoteViews remoteViews) throws RemoteException;

    void viewDataChanged(int i, int i2) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IAppWidgetHost {
        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidget(int appWidgetId, RemoteViews views) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providerChanged(int appWidgetId, AppWidgetProviderInfo info) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providersChanged() throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void viewDataChanged(int appWidgetId, int viewId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IAppWidgetHost {
        private static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetHost";
        static final int TRANSACTION_providerChanged = 2;
        static final int TRANSACTION_providersChanged = 3;
        static final int TRANSACTION_updateAppWidget = 1;
        static final int TRANSACTION_viewDataChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetHost asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAppWidgetHost)) {
                return (IAppWidgetHost) iin;
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
                    return "updateAppWidget";
                case 2:
                    return "providerChanged";
                case 3:
                    return "providersChanged";
                case 4:
                    return "viewDataChanged";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    RemoteViews _arg1 = data.readInt() != 0 ? RemoteViews.CREATOR.createFromParcel(data) : null;
                    updateAppWidget(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    AppWidgetProviderInfo _arg12 = data.readInt() != 0 ? AppWidgetProviderInfo.CREATOR.createFromParcel(data) : null;
                    providerChanged(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    providersChanged();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    viewDataChanged(_arg03, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IAppWidgetHost {
            public static IAppWidgetHost sDefaultImpl;
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

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void updateAppWidget(int appWidgetId, RemoteViews views) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    if (views != null) {
                        _data.writeInt(1);
                        views.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateAppWidget(appWidgetId, views);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providerChanged(int appWidgetId, AppWidgetProviderInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().providerChanged(appWidgetId, info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providersChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().providersChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void viewDataChanged(int appWidgetId, int viewId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(viewId);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().viewDataChanged(appWidgetId, viewId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAppWidgetHost impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAppWidgetHost getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
