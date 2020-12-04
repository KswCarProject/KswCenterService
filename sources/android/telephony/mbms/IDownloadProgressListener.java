package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDownloadProgressListener extends IInterface {
    void onProgressUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int i, int i2, int i3, int i4) throws RemoteException;

    public static class Default implements IDownloadProgressListener {
        public void onProgressUpdated(DownloadRequest request, FileInfo fileInfo, int currentDownloadSize, int fullDownloadSize, int currentDecodedSize, int fullDecodedSize) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDownloadProgressListener {
        private static final String DESCRIPTOR = "android.telephony.mbms.IDownloadProgressListener";
        static final int TRANSACTION_onProgressUpdated = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadProgressListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IDownloadProgressListener)) {
                return new Proxy(obj);
            }
            return (IDownloadProgressListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode != 1) {
                return null;
            }
            return "onProgressUpdated";
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            DownloadRequest _arg0;
            int i = code;
            Parcel parcel = data;
            if (i == 1) {
                Parcel parcel2 = reply;
                parcel.enforceInterface(DESCRIPTOR);
                FileInfo _arg1 = null;
                if (data.readInt() != 0) {
                    _arg0 = DownloadRequest.CREATOR.createFromParcel(parcel);
                } else {
                    _arg0 = null;
                }
                if (data.readInt() != 0) {
                    _arg1 = FileInfo.CREATOR.createFromParcel(parcel);
                }
                onProgressUpdated(_arg0, _arg1, data.readInt(), data.readInt(), data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IDownloadProgressListener {
            public static IDownloadProgressListener sDefaultImpl;
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

            public void onProgressUpdated(DownloadRequest request, FileInfo fileInfo, int currentDownloadSize, int fullDownloadSize, int currentDecodedSize, int fullDecodedSize) throws RemoteException {
                DownloadRequest downloadRequest = request;
                FileInfo fileInfo2 = fileInfo;
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
                    if (fileInfo2 != null) {
                        _data.writeInt(1);
                        fileInfo2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(currentDownloadSize);
                    } catch (Throwable th) {
                        th = th;
                        int i = fullDownloadSize;
                        int i2 = currentDecodedSize;
                        int i3 = fullDecodedSize;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(fullDownloadSize);
                    } catch (Throwable th2) {
                        th = th2;
                        int i22 = currentDecodedSize;
                        int i32 = fullDecodedSize;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(currentDecodedSize);
                        try {
                            _data.writeInt(fullDecodedSize);
                            if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onProgressUpdated(request, fileInfo, currentDownloadSize, fullDownloadSize, currentDecodedSize, fullDecodedSize);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i322 = fullDecodedSize;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i4 = currentDownloadSize;
                    int i5 = fullDownloadSize;
                    int i222 = currentDecodedSize;
                    int i3222 = fullDecodedSize;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }
        }

        public static boolean setDefaultImpl(IDownloadProgressListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IDownloadProgressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
