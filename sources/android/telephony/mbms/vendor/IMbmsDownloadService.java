package android.telephony.mbms.vendor;

import android.content.ContentResolver;
import android.content.Context;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMbmsDownloadService extends IInterface {
    int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException;

    int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException;

    int cancelDownload(DownloadRequest downloadRequest) throws RemoteException;

    void dispose(int i) throws RemoteException;

    int download(DownloadRequest downloadRequest) throws RemoteException;

    int initialize(int i, IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws RemoteException;

    List<DownloadRequest> listPendingDownloads(int i) throws RemoteException;

    int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException;

    int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException;

    int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException;

    int requestUpdateFileServices(int i, List<String> list) throws RemoteException;

    int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException;

    int setTempFileRootDirectory(int i, String str) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IMbmsDownloadService {
        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int initialize(int subId, IMbmsDownloadSessionCallback listener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int requestUpdateFileServices(int subId, List<String> serviceClasses) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int setTempFileRootDirectory(int subId, String rootDirectoryPath) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int download(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public List<DownloadRequest> listPendingDownloads(int subscriptionId) throws RemoteException {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public void dispose(int subId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMbmsDownloadService {
        private static final String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsDownloadService";
        static final int TRANSACTION_addProgressListener = 7;
        static final int TRANSACTION_addStatusListener = 5;
        static final int TRANSACTION_cancelDownload = 10;
        static final int TRANSACTION_dispose = 13;
        static final int TRANSACTION_download = 4;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_listPendingDownloads = 9;
        static final int TRANSACTION_removeProgressListener = 8;
        static final int TRANSACTION_removeStatusListener = 6;
        static final int TRANSACTION_requestDownloadState = 11;
        static final int TRANSACTION_requestUpdateFileServices = 2;
        static final int TRANSACTION_resetDownloadKnowledge = 12;
        static final int TRANSACTION_setTempFileRootDirectory = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsDownloadService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMbmsDownloadService)) {
                return (IMbmsDownloadService) iin;
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
                    return ContentResolver.SYNC_EXTRAS_INITIALIZE;
                case 2:
                    return "requestUpdateFileServices";
                case 3:
                    return "setTempFileRootDirectory";
                case 4:
                    return Context.DOWNLOAD_SERVICE;
                case 5:
                    return "addStatusListener";
                case 6:
                    return "removeStatusListener";
                case 7:
                    return "addProgressListener";
                case 8:
                    return "removeProgressListener";
                case 9:
                    return "listPendingDownloads";
                case 10:
                    return "cancelDownload";
                case 11:
                    return "requestDownloadState";
                case 12:
                    return "resetDownloadKnowledge";
                case 13:
                    return "dispose";
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
            DownloadRequest _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    IMbmsDownloadSessionCallback _arg1 = IMbmsDownloadSessionCallback.Stub.asInterface(data.readStrongBinder());
                    int _result = initialize(_arg02, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    List<String> _arg12 = data.createStringArrayList();
                    int _result2 = requestUpdateFileServices(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    String _arg13 = data.readString();
                    int _result3 = setTempFileRootDirectory(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg05 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    int _result4 = download(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg06 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    IDownloadStatusListener _arg14 = IDownloadStatusListener.Stub.asInterface(data.readStrongBinder());
                    int _result5 = addStatusListener(_arg06, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg07 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    IDownloadStatusListener _arg15 = IDownloadStatusListener.Stub.asInterface(data.readStrongBinder());
                    int _result6 = removeStatusListener(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg08 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    IDownloadProgressListener _arg16 = IDownloadProgressListener.Stub.asInterface(data.readStrongBinder());
                    int _result7 = addProgressListener(_arg08, _arg16);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg09 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    IDownloadProgressListener _arg17 = IDownloadProgressListener.Stub.asInterface(data.readStrongBinder());
                    int _result8 = removeProgressListener(_arg09, _arg17);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg010 = data.readInt();
                    List<DownloadRequest> _result9 = listPendingDownloads(_arg010);
                    reply.writeNoException();
                    reply.writeTypedList(_result9);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg011 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    int _result10 = cancelDownload(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = DownloadRequest.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    FileInfo _arg18 = data.readInt() != 0 ? FileInfo.CREATOR.createFromParcel(data) : null;
                    int _result11 = requestDownloadState(_arg0, _arg18);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    DownloadRequest _arg012 = data.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(data) : null;
                    int _result12 = resetDownloadKnowledge(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg013 = data.readInt();
                    dispose(_arg013);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IMbmsDownloadService {
            public static IMbmsDownloadService sDefaultImpl;
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

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int initialize(int subId, IMbmsDownloadSessionCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initialize(subId, listener);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int requestUpdateFileServices(int subId, List<String> serviceClasses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStringList(serviceClasses);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestUpdateFileServices(subId, serviceClasses);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int setTempFileRootDirectory(int subId, String rootDirectoryPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(rootDirectoryPath);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setTempFileRootDirectory(subId, rootDirectoryPath);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int download(DownloadRequest downloadRequest) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().download(downloadRequest);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addStatusListener(downloadRequest, listener);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeStatusListener(downloadRequest, listener);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addProgressListener(downloadRequest, listener);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeProgressListener(downloadRequest, listener);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public List<DownloadRequest> listPendingDownloads(int subscriptionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subscriptionId);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listPendingDownloads(subscriptionId);
                    }
                    _reply.readException();
                    List<DownloadRequest> _result = _reply.createTypedArrayList(DownloadRequest.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelDownload(downloadRequest);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (fileInfo != null) {
                        _data.writeInt(1);
                        fileInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestDownloadState(downloadRequest, fileInfo);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        _data.writeInt(1);
                        downloadRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetDownloadKnowledge(downloadRequest);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public void dispose(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispose(subId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMbmsDownloadService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMbmsDownloadService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
