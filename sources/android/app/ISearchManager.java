package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface ISearchManager extends IInterface {
    List<ResolveInfo> getGlobalSearchActivities() throws RemoteException;

    @UnsupportedAppUsage
    ComponentName getGlobalSearchActivity() throws RemoteException;

    SearchableInfo getSearchableInfo(ComponentName componentName) throws RemoteException;

    List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException;

    ComponentName getWebSearchActivity() throws RemoteException;

    void launchAssist(Bundle bundle) throws RemoteException;

    boolean launchLegacyAssist(String str, int i, Bundle bundle) throws RemoteException;

    public static class Default implements ISearchManager {
        public SearchableInfo getSearchableInfo(ComponentName launchActivity) throws RemoteException {
            return null;
        }

        public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException {
            return null;
        }

        public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException {
            return null;
        }

        public ComponentName getGlobalSearchActivity() throws RemoteException {
            return null;
        }

        public ComponentName getWebSearchActivity() throws RemoteException {
            return null;
        }

        public void launchAssist(Bundle args) throws RemoteException {
        }

        public boolean launchLegacyAssist(String hint, int userHandle, Bundle args) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISearchManager {
        private static final String DESCRIPTOR = "android.app.ISearchManager";
        static final int TRANSACTION_getGlobalSearchActivities = 3;
        static final int TRANSACTION_getGlobalSearchActivity = 4;
        static final int TRANSACTION_getSearchableInfo = 1;
        static final int TRANSACTION_getSearchablesInGlobalSearch = 2;
        static final int TRANSACTION_getWebSearchActivity = 5;
        static final int TRANSACTION_launchAssist = 6;
        static final int TRANSACTION_launchLegacyAssist = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISearchManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISearchManager)) {
                return new Proxy(obj);
            }
            return (ISearchManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getSearchableInfo";
                case 2:
                    return "getSearchablesInGlobalSearch";
                case 3:
                    return "getGlobalSearchActivities";
                case 4:
                    return "getGlobalSearchActivity";
                case 5:
                    return "getWebSearchActivity";
                case 6:
                    return "launchAssist";
                case 7:
                    return "launchLegacyAssist";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v7 */
        /* JADX WARNING: type inference failed for: r3v14 */
        /* JADX WARNING: type inference failed for: r3v15 */
        /* JADX WARNING: type inference failed for: r3v16 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.app.ISearchManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00c5
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x009e;
                    case 2: goto L_0x0090;
                    case 3: goto L_0x0082;
                    case 4: goto L_0x006b;
                    case 5: goto L_0x0054;
                    case 6: goto L_0x0038;
                    case 7: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x002c
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x002d
            L_0x002c:
            L_0x002d:
                boolean r5 = r6.launchLegacyAssist(r1, r4, r3)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x0038:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x004b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x004c
            L_0x004b:
            L_0x004c:
                r1 = r3
                r6.launchAssist(r1)
                r9.writeNoException()
                return r2
            L_0x0054:
                r8.enforceInterface(r0)
                android.content.ComponentName r3 = r6.getWebSearchActivity()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0067
                r9.writeInt(r2)
                r3.writeToParcel((android.os.Parcel) r9, (int) r2)
                goto L_0x006a
            L_0x0067:
                r9.writeInt(r1)
            L_0x006a:
                return r2
            L_0x006b:
                r8.enforceInterface(r0)
                android.content.ComponentName r3 = r6.getGlobalSearchActivity()
                r9.writeNoException()
                if (r3 == 0) goto L_0x007e
                r9.writeInt(r2)
                r3.writeToParcel((android.os.Parcel) r9, (int) r2)
                goto L_0x0081
            L_0x007e:
                r9.writeInt(r1)
            L_0x0081:
                return r2
            L_0x0082:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getGlobalSearchActivities()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x0090:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getSearchablesInGlobalSearch()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x009e:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00b0
                android.os.Parcelable$Creator<android.content.ComponentName> r3 = android.content.ComponentName.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.content.ComponentName r3 = (android.content.ComponentName) r3
                goto L_0x00b1
            L_0x00b0:
            L_0x00b1:
                android.app.SearchableInfo r4 = r6.getSearchableInfo(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x00c1
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x00c4
            L_0x00c1:
                r9.writeInt(r1)
            L_0x00c4:
                return r2
            L_0x00c5:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.ISearchManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISearchManager {
            public static ISearchManager sDefaultImpl;
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

            public SearchableInfo getSearchableInfo(ComponentName launchActivity) throws RemoteException {
                SearchableInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (launchActivity != null) {
                        _data.writeInt(1);
                        launchActivity.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSearchableInfo(launchActivity);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SearchableInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SearchableInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSearchablesInGlobalSearch();
                    }
                    _reply.readException();
                    List<SearchableInfo> _result = _reply.createTypedArrayList(SearchableInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalSearchActivities();
                    }
                    _reply.readException();
                    List<ResolveInfo> _result = _reply.createTypedArrayList(ResolveInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getGlobalSearchActivity() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalSearchActivity();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getWebSearchActivity() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWebSearchActivity();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void launchAssist(Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().launchAssist(args);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean launchLegacyAssist(String hint, int userHandle, Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(hint);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().launchLegacyAssist(hint, userHandle, args);
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
        }

        public static boolean setDefaultImpl(ISearchManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISearchManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
