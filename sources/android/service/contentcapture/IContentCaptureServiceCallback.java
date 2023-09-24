package android.service.contentcapture;

import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.contentcapture.ContentCaptureCondition;
import java.util.List;

/* loaded from: classes3.dex */
public interface IContentCaptureServiceCallback extends IInterface {
    void disableSelf() throws RemoteException;

    void setContentCaptureConditions(String str, List<ContentCaptureCondition> list) throws RemoteException;

    void setContentCaptureWhitelist(List<String> list, List<ComponentName> list2) throws RemoteException;

    void writeSessionFlush(int i, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int i2) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IContentCaptureServiceCallback {
        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void setContentCaptureWhitelist(List<String> packages, List<ComponentName> activities) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void setContentCaptureConditions(String packageName, List<ContentCaptureCondition> conditions) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void disableSelf() throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void writeSessionFlush(int sessionId, ComponentName app, FlushMetrics flushMetrics, ContentCaptureOptions options, int flushReason) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IContentCaptureServiceCallback {
        private static final String DESCRIPTOR = "android.service.contentcapture.IContentCaptureServiceCallback";
        static final int TRANSACTION_disableSelf = 3;
        static final int TRANSACTION_setContentCaptureConditions = 2;
        static final int TRANSACTION_setContentCaptureWhitelist = 1;
        static final int TRANSACTION_writeSessionFlush = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureServiceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContentCaptureServiceCallback)) {
                return (IContentCaptureServiceCallback) iin;
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
                    return "setContentCaptureWhitelist";
                case 2:
                    return "setContentCaptureConditions";
                case 3:
                    return "disableSelf";
                case 4:
                    return "writeSessionFlush";
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
                    List<String> _arg0 = data.createStringArrayList();
                    List<ComponentName> _arg1 = data.createTypedArrayList(ComponentName.CREATOR);
                    setContentCaptureWhitelist(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    List<ContentCaptureCondition> _arg12 = data.createTypedArrayList(ContentCaptureCondition.CREATOR);
                    setContentCaptureConditions(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    disableSelf();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    ComponentName _arg13 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    FlushMetrics _arg2 = data.readInt() != 0 ? FlushMetrics.CREATOR.createFromParcel(data) : null;
                    ContentCaptureOptions _arg3 = data.readInt() != 0 ? ContentCaptureOptions.CREATOR.createFromParcel(data) : null;
                    int _arg4 = data.readInt();
                    writeSessionFlush(_arg03, _arg13, _arg2, _arg3, _arg4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IContentCaptureServiceCallback {
            public static IContentCaptureServiceCallback sDefaultImpl;
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

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void setContentCaptureWhitelist(List<String> packages, List<ComponentName> activities) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packages);
                    _data.writeTypedList(activities);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setContentCaptureWhitelist(packages, activities);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void setContentCaptureConditions(String packageName, List<ContentCaptureCondition> conditions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedList(conditions);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setContentCaptureConditions(packageName, conditions);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void disableSelf() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableSelf();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void writeSessionFlush(int sessionId, ComponentName app, FlushMetrics flushMetrics, ContentCaptureOptions options, int flushReason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (app != null) {
                        _data.writeInt(1);
                        app.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (flushMetrics != null) {
                        _data.writeInt(1);
                        flushMetrics.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flushReason);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().writeSessionFlush(sessionId, app, flushMetrics, options, flushReason);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentCaptureServiceCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IContentCaptureServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
