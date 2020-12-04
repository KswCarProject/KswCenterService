package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.VideoProfile;
import android.view.Surface;

public interface IVideoProvider extends IInterface {
    void addVideoCallback(IBinder iBinder) throws RemoteException;

    void removeVideoCallback(IBinder iBinder) throws RemoteException;

    void requestCallDataUsage() throws RemoteException;

    void requestCameraCapabilities() throws RemoteException;

    void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException;

    void sendSessionModifyResponse(VideoProfile videoProfile) throws RemoteException;

    void setCamera(String str, String str2, int i) throws RemoteException;

    void setDeviceOrientation(int i) throws RemoteException;

    void setDisplaySurface(Surface surface) throws RemoteException;

    void setPauseImage(Uri uri) throws RemoteException;

    void setPreviewSurface(Surface surface) throws RemoteException;

    void setZoom(float f) throws RemoteException;

    public static class Default implements IVideoProvider {
        public void addVideoCallback(IBinder videoCallbackBinder) throws RemoteException {
        }

        public void removeVideoCallback(IBinder videoCallbackBinder) throws RemoteException {
        }

        public void setCamera(String cameraId, String mCallingPackageName, int targetSdkVersion) throws RemoteException {
        }

        public void setPreviewSurface(Surface surface) throws RemoteException {
        }

        public void setDisplaySurface(Surface surface) throws RemoteException {
        }

        public void setDeviceOrientation(int rotation) throws RemoteException {
        }

        public void setZoom(float value) throws RemoteException {
        }

        public void sendSessionModifyRequest(VideoProfile fromProfile, VideoProfile toProfile) throws RemoteException {
        }

        public void sendSessionModifyResponse(VideoProfile responseProfile) throws RemoteException {
        }

        public void requestCameraCapabilities() throws RemoteException {
        }

        public void requestCallDataUsage() throws RemoteException {
        }

        public void setPauseImage(Uri uri) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVideoProvider {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IVideoProvider";
        static final int TRANSACTION_addVideoCallback = 1;
        static final int TRANSACTION_removeVideoCallback = 2;
        static final int TRANSACTION_requestCallDataUsage = 11;
        static final int TRANSACTION_requestCameraCapabilities = 10;
        static final int TRANSACTION_sendSessionModifyRequest = 8;
        static final int TRANSACTION_sendSessionModifyResponse = 9;
        static final int TRANSACTION_setCamera = 3;
        static final int TRANSACTION_setDeviceOrientation = 6;
        static final int TRANSACTION_setDisplaySurface = 5;
        static final int TRANSACTION_setPauseImage = 12;
        static final int TRANSACTION_setPreviewSurface = 4;
        static final int TRANSACTION_setZoom = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IVideoProvider)) {
                return new Proxy(obj);
            }
            return (IVideoProvider) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addVideoCallback";
                case 2:
                    return "removeVideoCallback";
                case 3:
                    return "setCamera";
                case 4:
                    return "setPreviewSurface";
                case 5:
                    return "setDisplaySurface";
                case 6:
                    return "setDeviceOrientation";
                case 7:
                    return "setZoom";
                case 8:
                    return "sendSessionModifyRequest";
                case 9:
                    return "sendSessionModifyResponse";
                case 10:
                    return "requestCameraCapabilities";
                case 11:
                    return "requestCallDataUsage";
                case 12:
                    return "setPauseImage";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.telecom.VideoProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.telecom.VideoProfile} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v5, types: [android.view.Surface] */
        /* JADX WARNING: type inference failed for: r1v9, types: [android.view.Surface] */
        /* JADX WARNING: type inference failed for: r1v23, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "com.android.internal.telecom.IVideoProvider"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00e1
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x00d6;
                    case 2: goto L_0x00cb;
                    case 3: goto L_0x00b8;
                    case 4: goto L_0x00a1;
                    case 5: goto L_0x008a;
                    case 6: goto L_0x007f;
                    case 7: goto L_0x0074;
                    case 8: goto L_0x004d;
                    case 9: goto L_0x0036;
                    case 10: goto L_0x002f;
                    case 11: goto L_0x0028;
                    case 12: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r5.setPauseImage(r1)
                return r2
            L_0x0028:
                r7.enforceInterface(r0)
                r5.requestCallDataUsage()
                return r2
            L_0x002f:
                r7.enforceInterface(r0)
                r5.requestCameraCapabilities()
                return r2
            L_0x0036:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0048
                android.os.Parcelable$Creator<android.telecom.VideoProfile> r1 = android.telecom.VideoProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telecom.VideoProfile r1 = (android.telecom.VideoProfile) r1
                goto L_0x0049
            L_0x0048:
            L_0x0049:
                r5.sendSessionModifyResponse(r1)
                return r2
            L_0x004d:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x005f
                android.os.Parcelable$Creator<android.telecom.VideoProfile> r3 = android.telecom.VideoProfile.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telecom.VideoProfile r3 = (android.telecom.VideoProfile) r3
                goto L_0x0060
            L_0x005f:
                r3 = r1
            L_0x0060:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x006f
                android.os.Parcelable$Creator<android.telecom.VideoProfile> r1 = android.telecom.VideoProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telecom.VideoProfile r1 = (android.telecom.VideoProfile) r1
                goto L_0x0070
            L_0x006f:
            L_0x0070:
                r5.sendSessionModifyRequest(r3, r1)
                return r2
            L_0x0074:
                r7.enforceInterface(r0)
                float r1 = r7.readFloat()
                r5.setZoom(r1)
                return r2
            L_0x007f:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.setDeviceOrientation(r1)
                return r2
            L_0x008a:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x009c
                android.os.Parcelable$Creator<android.view.Surface> r1 = android.view.Surface.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.Surface r1 = (android.view.Surface) r1
                goto L_0x009d
            L_0x009c:
            L_0x009d:
                r5.setDisplaySurface(r1)
                return r2
            L_0x00a1:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00b3
                android.os.Parcelable$Creator<android.view.Surface> r1 = android.view.Surface.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.Surface r1 = (android.view.Surface) r1
                goto L_0x00b4
            L_0x00b3:
            L_0x00b4:
                r5.setPreviewSurface(r1)
                return r2
            L_0x00b8:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                java.lang.String r3 = r7.readString()
                int r4 = r7.readInt()
                r5.setCamera(r1, r3, r4)
                return r2
            L_0x00cb:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.removeVideoCallback(r1)
                return r2
            L_0x00d6:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.addVideoCallback(r1)
                return r2
            L_0x00e1:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telecom.IVideoProvider.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IVideoProvider {
            public static IVideoProvider sDefaultImpl;
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

            public void addVideoCallback(IBinder videoCallbackBinder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(videoCallbackBinder);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addVideoCallback(videoCallbackBinder);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeVideoCallback(IBinder videoCallbackBinder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(videoCallbackBinder);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeVideoCallback(videoCallbackBinder);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setCamera(String cameraId, String mCallingPackageName, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeString(mCallingPackageName);
                    _data.writeInt(targetSdkVersion);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCamera(cameraId, mCallingPackageName, targetSdkVersion);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPreviewSurface(Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPreviewSurface(surface);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setDisplaySurface(Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDisplaySurface(surface);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setDeviceOrientation(int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDeviceOrientation(rotation);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setZoom(float value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(value);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setZoom(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendSessionModifyRequest(VideoProfile fromProfile, VideoProfile toProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fromProfile != null) {
                        _data.writeInt(1);
                        fromProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (toProfile != null) {
                        _data.writeInt(1);
                        toProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendSessionModifyRequest(fromProfile, toProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendSessionModifyResponse(VideoProfile responseProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (responseProfile != null) {
                        _data.writeInt(1);
                        responseProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendSessionModifyResponse(responseProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestCameraCapabilities() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestCameraCapabilities();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestCallDataUsage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestCallDataUsage();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPauseImage(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPauseImage(uri);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVideoProvider impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IVideoProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
