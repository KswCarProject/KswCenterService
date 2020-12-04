package com.android.internal.appwidget;

import android.appwidget.AppWidgetProviderInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

public interface IAppWidgetHost extends IInterface {
    void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException;

    void providersChanged() throws RemoteException;

    void updateAppWidget(int i, RemoteViews remoteViews) throws RemoteException;

    void viewDataChanged(int i, int i2) throws RemoteException;

    public static class Default implements IAppWidgetHost {
        public void updateAppWidget(int appWidgetId, RemoteViews views) throws RemoteException {
        }

        public void providerChanged(int appWidgetId, AppWidgetProviderInfo info) throws RemoteException {
        }

        public void providersChanged() throws RemoteException {
        }

        public void viewDataChanged(int appWidgetId, int viewId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IAppWidgetHost)) {
                return new Proxy(obj);
            }
            return (IAppWidgetHost) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.appwidget.AppWidgetProviderInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v2, types: [android.widget.RemoteViews] */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: type inference failed for: r1v13 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "com.android.internal.appwidget.IAppWidgetHost"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x005d
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x0042;
                    case 2: goto L_0x0027;
                    case 3: goto L_0x0020;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r3 = r7.readInt()
                r5.viewDataChanged(r1, r3)
                return r2
            L_0x0020:
                r7.enforceInterface(r0)
                r5.providersChanged()
                return r2
            L_0x0027:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x003d
                android.os.Parcelable$Creator<android.appwidget.AppWidgetProviderInfo> r1 = android.appwidget.AppWidgetProviderInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.appwidget.AppWidgetProviderInfo r1 = (android.appwidget.AppWidgetProviderInfo) r1
                goto L_0x003e
            L_0x003d:
            L_0x003e:
                r5.providerChanged(r3, r1)
                return r2
            L_0x0042:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0058
                android.os.Parcelable$Creator<android.widget.RemoteViews> r1 = android.widget.RemoteViews.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.widget.RemoteViews r1 = (android.widget.RemoteViews) r1
                goto L_0x0059
            L_0x0058:
            L_0x0059:
                r5.updateAppWidget(r3, r1)
                return r2
            L_0x005d:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.appwidget.IAppWidgetHost.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAppWidgetHost {
            public static IAppWidgetHost sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateAppWidget(appWidgetId, views);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().providerChanged(appWidgetId, info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void providersChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().providersChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void viewDataChanged(int appWidgetId, int viewId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(viewId);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().viewDataChanged(appWidgetId, viewId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAppWidgetHost impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAppWidgetHost getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
