package android.view.accessibility;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAccessibilityInteractionConnectionCallback extends IInterface {
    @UnsupportedAppUsage
    void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int i) throws RemoteException;

    @UnsupportedAppUsage
    void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException;

    @UnsupportedAppUsage
    void setPerformAccessibilityActionResult(boolean z, int i) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IAccessibilityInteractionConnectionCallback {
        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo info, int interactionId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> infos, int interactionId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setPerformAccessibilityActionResult(boolean succeeded, int interactionId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IAccessibilityInteractionConnectionCallback {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnectionCallback";
        static final int TRANSACTION_setFindAccessibilityNodeInfoResult = 1;
        static final int TRANSACTION_setFindAccessibilityNodeInfosResult = 2;
        static final int TRANSACTION_setPerformAccessibilityActionResult = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAccessibilityInteractionConnectionCallback)) {
                return (IAccessibilityInteractionConnectionCallback) iin;
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
                    return "setFindAccessibilityNodeInfoResult";
                case 2:
                    return "setFindAccessibilityNodeInfosResult";
                case 3:
                    return "setPerformAccessibilityActionResult";
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
            AccessibilityNodeInfo _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = AccessibilityNodeInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    int _arg1 = data.readInt();
                    setFindAccessibilityNodeInfoResult(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    List<AccessibilityNodeInfo> _arg02 = data.createTypedArrayList(AccessibilityNodeInfo.CREATOR);
                    int _arg12 = data.readInt();
                    setFindAccessibilityNodeInfosResult(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg03 = data.readInt() != 0;
                    int _arg13 = data.readInt();
                    setPerformAccessibilityActionResult(_arg03, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IAccessibilityInteractionConnectionCallback {
            public static IAccessibilityInteractionConnectionCallback sDefaultImpl;
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

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo info, int interactionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(interactionId);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFindAccessibilityNodeInfoResult(info, interactionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> infos, int interactionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(infos);
                    _data.writeInt(interactionId);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFindAccessibilityNodeInfosResult(infos, interactionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setPerformAccessibilityActionResult(boolean succeeded, int interactionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(succeeded ? 1 : 0);
                    _data.writeInt(interactionId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPerformAccessibilityActionResult(succeeded, interactionId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccessibilityInteractionConnectionCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAccessibilityInteractionConnectionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
