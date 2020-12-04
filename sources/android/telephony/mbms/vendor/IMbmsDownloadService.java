package android.telephony.mbms.vendor;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import java.util.List;

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

    public static class Default implements IMbmsDownloadService {
        public int initialize(int subId, IMbmsDownloadSessionCallback listener) throws RemoteException {
            return 0;
        }

        public int requestUpdateFileServices(int subId, List<String> list) throws RemoteException {
            return 0;
        }

        public int setTempFileRootDirectory(int subId, String rootDirectoryPath) throws RemoteException {
            return 0;
        }

        public int download(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        public int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
            return 0;
        }

        public int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
            return 0;
        }

        public int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
            return 0;
        }

        public int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
            return 0;
        }

        public List<DownloadRequest> listPendingDownloads(int subscriptionId) throws RemoteException {
            return null;
        }

        public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
            return 0;
        }

        public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        public void dispose(int subId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IMbmsDownloadService)) {
                return new Proxy(obj);
            }
            return (IMbmsDownloadService) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.telephony.mbms.FileInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.telephony.mbms.DownloadRequest} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v40 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v42 */
        /* JADX WARNING: type inference failed for: r1v43 */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.telephony.mbms.vendor.IMbmsDownloadService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0197
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x017d;
                    case 2: goto L_0x0167;
                    case 3: goto L_0x0151;
                    case 4: goto L_0x0133;
                    case 5: goto L_0x010d;
                    case 6: goto L_0x00e7;
                    case 7: goto L_0x00c1;
                    case 8: goto L_0x009b;
                    case 9: goto L_0x0089;
                    case 10: goto L_0x006b;
                    case 11: goto L_0x003d;
                    case 12: goto L_0x001f;
                    case 13: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.dispose(r1)
                r8.writeNoException()
                return r2
            L_0x001f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0031
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x0032
            L_0x0031:
            L_0x0032:
                int r3 = r5.resetDownloadKnowledge(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x003d:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x004f
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r3 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r3 = (android.telephony.mbms.DownloadRequest) r3
                goto L_0x0050
            L_0x004f:
                r3 = r1
            L_0x0050:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x005f
                android.os.Parcelable$Creator<android.telephony.mbms.FileInfo> r1 = android.telephony.mbms.FileInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.FileInfo r1 = (android.telephony.mbms.FileInfo) r1
                goto L_0x0060
            L_0x005f:
            L_0x0060:
                int r4 = r5.requestDownloadState(r3, r1)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x006b:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x007d
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x007e
            L_0x007d:
            L_0x007e:
                int r3 = r5.cancelDownload(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0089:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                java.util.List r3 = r5.listPendingDownloads(r1)
                r8.writeNoException()
                r8.writeTypedList(r3)
                return r2
            L_0x009b:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00ad
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x00ae
            L_0x00ad:
            L_0x00ae:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.telephony.mbms.IDownloadProgressListener r3 = android.telephony.mbms.IDownloadProgressListener.Stub.asInterface(r3)
                int r4 = r5.removeProgressListener(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x00c1:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00d3
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x00d4
            L_0x00d3:
            L_0x00d4:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.telephony.mbms.IDownloadProgressListener r3 = android.telephony.mbms.IDownloadProgressListener.Stub.asInterface(r3)
                int r4 = r5.addProgressListener(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x00e7:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00f9
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x00fa
            L_0x00f9:
            L_0x00fa:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.telephony.mbms.IDownloadStatusListener r3 = android.telephony.mbms.IDownloadStatusListener.Stub.asInterface(r3)
                int r4 = r5.removeStatusListener(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x010d:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x011f
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x0120
            L_0x011f:
            L_0x0120:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.telephony.mbms.IDownloadStatusListener r3 = android.telephony.mbms.IDownloadStatusListener.Stub.asInterface(r3)
                int r4 = r5.addStatusListener(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x0133:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0145
                android.os.Parcelable$Creator<android.telephony.mbms.DownloadRequest> r1 = android.telephony.mbms.DownloadRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.mbms.DownloadRequest r1 = (android.telephony.mbms.DownloadRequest) r1
                goto L_0x0146
            L_0x0145:
            L_0x0146:
                int r3 = r5.download(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0151:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                java.lang.String r3 = r7.readString()
                int r4 = r5.setTempFileRootDirectory(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x0167:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                java.util.ArrayList r3 = r7.createStringArrayList()
                int r4 = r5.requestUpdateFileServices(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x017d:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                android.os.IBinder r3 = r7.readStrongBinder()
                android.telephony.mbms.IMbmsDownloadSessionCallback r3 = android.telephony.mbms.IMbmsDownloadSessionCallback.Stub.asInterface(r3)
                int r4 = r5.initialize(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x0197:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.mbms.vendor.IMbmsDownloadService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMbmsDownloadService {
            public static IMbmsDownloadService sDefaultImpl;
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

            public int initialize(int subId, IMbmsDownloadSessionCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initialize(subId, listener);
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

            public int requestUpdateFileServices(int subId, List<String> serviceClasses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStringList(serviceClasses);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestUpdateFileServices(subId, serviceClasses);
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

            public int setTempFileRootDirectory(int subId, String rootDirectoryPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(rootDirectoryPath);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setTempFileRootDirectory(subId, rootDirectoryPath);
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
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().download(downloadRequest);
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
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addStatusListener(downloadRequest, listener);
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
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeStatusListener(downloadRequest, listener);
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
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addProgressListener(downloadRequest, listener);
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
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeProgressListener(downloadRequest, listener);
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

            public List<DownloadRequest> listPendingDownloads(int subscriptionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subscriptionId);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listPendingDownloads(subscriptionId);
                    }
                    _reply.readException();
                    List<DownloadRequest> _result = _reply.createTypedArrayList(DownloadRequest.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelDownload(downloadRequest);
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
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestDownloadState(downloadRequest, fileInfo);
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
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetDownloadKnowledge(downloadRequest);
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

            public void dispose(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dispose(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMbmsDownloadService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMbmsDownloadService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
