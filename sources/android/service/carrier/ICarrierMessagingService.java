package android.service.carrier;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface ICarrierMessagingService extends IInterface {
    void downloadMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void filterSms(MessagePdu messagePdu, String str, int i, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendDataSms(byte[] bArr, int i, String str, int i2, int i3, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendMultipartTextSms(List<String> list, int i, String str, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendTextSms(String str, int i, String str2, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    public static class Default implements ICarrierMessagingService {
        public void filterSms(MessagePdu pdu, String format, int destPort, int subId, ICarrierMessagingCallback callback) throws RemoteException {
        }

        public void sendTextSms(String text, int subId, String destAddress, int sendSmsFlag, ICarrierMessagingCallback callback) throws RemoteException {
        }

        public void sendDataSms(byte[] data, int subId, String destAddress, int destPort, int sendSmsFlag, ICarrierMessagingCallback callback) throws RemoteException {
        }

        public void sendMultipartTextSms(List<String> list, int subId, String destAddress, int sendSmsFlag, ICarrierMessagingCallback callback) throws RemoteException {
        }

        public void sendMms(Uri pduUri, int subId, Uri location, ICarrierMessagingCallback callback) throws RemoteException {
        }

        public void downloadMms(Uri pduUri, int subId, Uri location, ICarrierMessagingCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICarrierMessagingService {
        private static final String DESCRIPTOR = "android.service.carrier.ICarrierMessagingService";
        static final int TRANSACTION_downloadMms = 6;
        static final int TRANSACTION_filterSms = 1;
        static final int TRANSACTION_sendDataSms = 3;
        static final int TRANSACTION_sendMms = 5;
        static final int TRANSACTION_sendMultipartTextSms = 4;
        static final int TRANSACTION_sendTextSms = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierMessagingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICarrierMessagingService)) {
                return new Proxy(obj);
            }
            return (ICarrierMessagingService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "filterSms";
                case 2:
                    return "sendTextSms";
                case 3:
                    return "sendDataSms";
                case 4:
                    return "sendMultipartTextSms";
                case 5:
                    return "sendMms";
                case 6:
                    return "downloadMms";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.service.carrier.MessagePdu} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: android.net.Uri} */
        /* JADX WARNING: type inference failed for: r0v8, types: [android.service.carrier.MessagePdu] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.service.carrier.ICarrierMessagingService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x0128
                r0 = 0
                switch(r8) {
                    case 1: goto L_0x00f6;
                    case 2: goto L_0x00d0;
                    case 3: goto L_0x00a3;
                    case 4: goto L_0x007d;
                    case 5: goto L_0x004a;
                    case 6: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x0017:
                r9.enforceInterface(r10)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0029
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x002a
            L_0x0029:
                r1 = r0
            L_0x002a:
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x003d
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x003e
            L_0x003d:
            L_0x003e:
                android.os.IBinder r3 = r20.readStrongBinder()
                android.service.carrier.ICarrierMessagingCallback r3 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(r3)
                r7.downloadMms(r1, r2, r0, r3)
                return r11
            L_0x004a:
                r9.enforceInterface(r10)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x005c
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x005d
            L_0x005c:
                r1 = r0
            L_0x005d:
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0070
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0071
            L_0x0070:
            L_0x0071:
                android.os.IBinder r3 = r20.readStrongBinder()
                android.service.carrier.ICarrierMessagingCallback r3 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(r3)
                r7.sendMms(r1, r2, r0, r3)
                return r11
            L_0x007d:
                r9.enforceInterface(r10)
                java.util.ArrayList r6 = r20.createStringArrayList()
                int r12 = r20.readInt()
                java.lang.String r13 = r20.readString()
                int r14 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.carrier.ICarrierMessagingCallback r15 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(r0)
                r0 = r18
                r1 = r6
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r0.sendMultipartTextSms(r1, r2, r3, r4, r5)
                return r11
            L_0x00a3:
                r9.enforceInterface(r10)
                byte[] r12 = r20.createByteArray()
                int r13 = r20.readInt()
                java.lang.String r14 = r20.readString()
                int r15 = r20.readInt()
                int r16 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.carrier.ICarrierMessagingCallback r17 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(r0)
                r0 = r18
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r6 = r17
                r0.sendDataSms(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x00d0:
                r9.enforceInterface(r10)
                java.lang.String r6 = r20.readString()
                int r12 = r20.readInt()
                java.lang.String r13 = r20.readString()
                int r14 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.carrier.ICarrierMessagingCallback r15 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(r0)
                r0 = r18
                r1 = r6
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r0.sendTextSms(r1, r2, r3, r4, r5)
                return r11
            L_0x00f6:
                r9.enforceInterface(r10)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0109
                android.os.Parcelable$Creator<android.service.carrier.MessagePdu> r0 = android.service.carrier.MessagePdu.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.service.carrier.MessagePdu r0 = (android.service.carrier.MessagePdu) r0
            L_0x0107:
                r1 = r0
                goto L_0x010a
            L_0x0109:
                goto L_0x0107
            L_0x010a:
                java.lang.String r6 = r20.readString()
                int r12 = r20.readInt()
                int r13 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.carrier.ICarrierMessagingCallback r14 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(r0)
                r0 = r18
                r2 = r6
                r3 = r12
                r4 = r13
                r5 = r14
                r0.filterSms(r1, r2, r3, r4, r5)
                return r11
            L_0x0128:
                r0 = r21
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.carrier.ICarrierMessagingService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ICarrierMessagingService {
            public static ICarrierMessagingService sDefaultImpl;
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

            public void filterSms(MessagePdu pdu, String format, int destPort, int subId, ICarrierMessagingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pdu != null) {
                        _data.writeInt(1);
                        pdu.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(format);
                    _data.writeInt(destPort);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().filterSms(pdu, format, destPort, subId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendTextSms(String text, int subId, String destAddress, int sendSmsFlag, ICarrierMessagingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(text);
                    _data.writeInt(subId);
                    _data.writeString(destAddress);
                    _data.writeInt(sendSmsFlag);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendTextSms(text, subId, destAddress, sendSmsFlag, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendDataSms(byte[] data, int subId, String destAddress, int destPort, int sendSmsFlag, ICarrierMessagingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeByteArray(data);
                    } catch (Throwable th) {
                        th = th;
                        int i = subId;
                        String str = destAddress;
                        int i2 = destPort;
                        int i3 = sendSmsFlag;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(destAddress);
                            try {
                                _data.writeInt(destPort);
                            } catch (Throwable th2) {
                                th = th2;
                                int i32 = sendSmsFlag;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = destPort;
                            int i322 = sendSmsFlag;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str2 = destAddress;
                        int i222 = destPort;
                        int i3222 = sendSmsFlag;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(sendSmsFlag);
                        _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().sendDataSms(data, subId, destAddress, destPort, sendSmsFlag, callback);
                        _data.recycle();
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    byte[] bArr = data;
                    int i4 = subId;
                    String str22 = destAddress;
                    int i2222 = destPort;
                    int i32222 = sendSmsFlag;
                    _data.recycle();
                    throw th;
                }
            }

            public void sendMultipartTextSms(List<String> parts, int subId, String destAddress, int sendSmsFlag, ICarrierMessagingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(parts);
                    _data.writeInt(subId);
                    _data.writeString(destAddress);
                    _data.writeInt(sendSmsFlag);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendMultipartTextSms(parts, subId, destAddress, sendSmsFlag, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendMms(Uri pduUri, int subId, Uri location, ICarrierMessagingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pduUri != null) {
                        _data.writeInt(1);
                        pduUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(subId);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendMms(pduUri, subId, location, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void downloadMms(Uri pduUri, int subId, Uri location, ICarrierMessagingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pduUri != null) {
                        _data.writeInt(1);
                        pduUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(subId);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().downloadMms(pduUri, subId, location, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICarrierMessagingService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICarrierMessagingService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
