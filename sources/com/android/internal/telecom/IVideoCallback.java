package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.VideoProfile;

public interface IVideoCallback extends IInterface {
    void changeCallDataUsage(long j) throws RemoteException;

    void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException;

    void changePeerDimensions(int i, int i2) throws RemoteException;

    void changeVideoQuality(int i) throws RemoteException;

    void handleCallSessionEvent(int i) throws RemoteException;

    void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException;

    void receiveSessionModifyResponse(int i, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException;

    public static class Default implements IVideoCallback {
        public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
        }

        public void receiveSessionModifyResponse(int status, VideoProfile requestedProfile, VideoProfile responseProfile) throws RemoteException {
        }

        public void handleCallSessionEvent(int event) throws RemoteException {
        }

        public void changePeerDimensions(int width, int height) throws RemoteException {
        }

        public void changeCallDataUsage(long dataUsage) throws RemoteException {
        }

        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
        }

        public void changeVideoQuality(int videoQuality) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVideoCallback {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IVideoCallback";
        static final int TRANSACTION_changeCallDataUsage = 5;
        static final int TRANSACTION_changeCameraCapabilities = 6;
        static final int TRANSACTION_changePeerDimensions = 4;
        static final int TRANSACTION_changeVideoQuality = 7;
        static final int TRANSACTION_handleCallSessionEvent = 3;
        static final int TRANSACTION_receiveSessionModifyRequest = 1;
        static final int TRANSACTION_receiveSessionModifyResponse = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IVideoCallback)) {
                return new Proxy(obj);
            }
            return (IVideoCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "receiveSessionModifyRequest";
                case 2:
                    return "receiveSessionModifyResponse";
                case 3:
                    return "handleCallSessionEvent";
                case 4:
                    return "changePeerDimensions";
                case 5:
                    return "changeCallDataUsage";
                case 6:
                    return "changeCameraCapabilities";
                case 7:
                    return "changeVideoQuality";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.telecom.VideoProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.telecom.VideoProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.telecom.VideoProfile$CameraCapabilities} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v18 */
        /* JADX WARNING: type inference failed for: r1v19 */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.internal.telecom.IVideoCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x009a
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0083;
                    case 2: goto L_0x0058;
                    case 3: goto L_0x004d;
                    case 4: goto L_0x003e;
                    case 5: goto L_0x0033;
                    case 6: goto L_0x001c;
                    case 7: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.changeVideoQuality(r1)
                return r2
            L_0x001c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x002e
                android.os.Parcelable$Creator<android.telecom.VideoProfile$CameraCapabilities> r1 = android.telecom.VideoProfile.CameraCapabilities.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telecom.VideoProfile$CameraCapabilities r1 = (android.telecom.VideoProfile.CameraCapabilities) r1
                goto L_0x002f
            L_0x002e:
            L_0x002f:
                r6.changeCameraCapabilities(r1)
                return r2
            L_0x0033:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                r6.changeCallDataUsage(r3)
                return r2
            L_0x003e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.changePeerDimensions(r1, r3)
                return r2
            L_0x004d:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.handleCallSessionEvent(r1)
                return r2
            L_0x0058:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x006e
                android.os.Parcelable$Creator<android.telecom.VideoProfile> r4 = android.telecom.VideoProfile.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.telecom.VideoProfile r4 = (android.telecom.VideoProfile) r4
                goto L_0x006f
            L_0x006e:
                r4 = r1
            L_0x006f:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x007e
                android.os.Parcelable$Creator<android.telecom.VideoProfile> r1 = android.telecom.VideoProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telecom.VideoProfile r1 = (android.telecom.VideoProfile) r1
                goto L_0x007f
            L_0x007e:
            L_0x007f:
                r6.receiveSessionModifyResponse(r3, r4, r1)
                return r2
            L_0x0083:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0095
                android.os.Parcelable$Creator<android.telecom.VideoProfile> r1 = android.telecom.VideoProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telecom.VideoProfile r1 = (android.telecom.VideoProfile) r1
                goto L_0x0096
            L_0x0095:
            L_0x0096:
                r6.receiveSessionModifyRequest(r1)
                return r2
            L_0x009a:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telecom.IVideoCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IVideoCallback {
            public static IVideoCallback sDefaultImpl;
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

            public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (videoProfile != null) {
                        _data.writeInt(1);
                        videoProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().receiveSessionModifyRequest(videoProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void receiveSessionModifyResponse(int status, VideoProfile requestedProfile, VideoProfile responseProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (requestedProfile != null) {
                        _data.writeInt(1);
                        requestedProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (responseProfile != null) {
                        _data.writeInt(1);
                        responseProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().receiveSessionModifyResponse(status, requestedProfile, responseProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void handleCallSessionEvent(int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(event);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().handleCallSessionEvent(event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void changePeerDimensions(int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().changePeerDimensions(width, height);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void changeCallDataUsage(long dataUsage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(dataUsage);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().changeCallDataUsage(dataUsage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cameraCapabilities != null) {
                        _data.writeInt(1);
                        cameraCapabilities.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().changeCameraCapabilities(cameraCapabilities);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void changeVideoQuality(int videoQuality) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(videoQuality);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().changeVideoQuality(videoQuality);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVideoCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IVideoCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
