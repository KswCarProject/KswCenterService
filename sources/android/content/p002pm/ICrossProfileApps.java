package android.content.p002pm;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import java.util.List;

/* renamed from: android.content.pm.ICrossProfileApps */
/* loaded from: classes.dex */
public interface ICrossProfileApps extends IInterface {
    List<UserHandle> getTargetUserProfiles(String str) throws RemoteException;

    void startActivityAsUser(IApplicationThread iApplicationThread, String str, ComponentName componentName, int i, boolean z) throws RemoteException;

    /* renamed from: android.content.pm.ICrossProfileApps$Default */
    /* loaded from: classes.dex */
    public static class Default implements ICrossProfileApps {
        @Override // android.content.p002pm.ICrossProfileApps
        public void startActivityAsUser(IApplicationThread caller, String callingPackage, ComponentName component, int userId, boolean launchMainActivity) throws RemoteException {
        }

        @Override // android.content.p002pm.ICrossProfileApps
        public List<UserHandle> getTargetUserProfiles(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.ICrossProfileApps$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ICrossProfileApps {
        private static final String DESCRIPTOR = "android.content.pm.ICrossProfileApps";
        static final int TRANSACTION_getTargetUserProfiles = 2;
        static final int TRANSACTION_startActivityAsUser = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICrossProfileApps asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICrossProfileApps)) {
                return (ICrossProfileApps) iin;
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
                    return "startActivityAsUser";
                case 2:
                    return "getTargetUserProfiles";
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
                    IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg1 = data.readString();
                    ComponentName _arg2 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    int _arg3 = data.readInt();
                    boolean _arg4 = data.readInt() != 0;
                    startActivityAsUser(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    List<UserHandle> _result = getTargetUserProfiles(_arg02);
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.content.pm.ICrossProfileApps$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements ICrossProfileApps {
            public static ICrossProfileApps sDefaultImpl;
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

            @Override // android.content.p002pm.ICrossProfileApps
            public void startActivityAsUser(IApplicationThread caller, String callingPackage, ComponentName component, int userId, boolean launchMainActivity) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(callingPackage);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeInt(launchMainActivity ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startActivityAsUser(caller, callingPackage, component, userId, launchMainActivity);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ICrossProfileApps
            public List<UserHandle> getTargetUserProfiles(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTargetUserProfiles(callingPackage);
                    }
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICrossProfileApps impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ICrossProfileApps getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
