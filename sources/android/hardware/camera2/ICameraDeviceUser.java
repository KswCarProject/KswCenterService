package android.hardware.camera2;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

public interface ICameraDeviceUser extends IInterface {
    public static final int CONSTRAINED_HIGH_SPEED_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int NO_IN_FLIGHT_REPEATING_FRAMES = -1;
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;
    public static final int VENDOR_MODE_START = 32768;

    void beginConfigure() throws RemoteException;

    long cancelRequest(int i) throws RemoteException;

    CameraMetadataNative createDefaultRequest(int i) throws RemoteException;

    int createInputStream(int i, int i2, int i3) throws RemoteException;

    int createStream(OutputConfiguration outputConfiguration) throws RemoteException;

    void deleteStream(int i) throws RemoteException;

    void disconnect() throws RemoteException;

    void endConfigure(int i, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    void finalizeOutputConfigurations(int i, OutputConfiguration outputConfiguration) throws RemoteException;

    long flush() throws RemoteException;

    CameraMetadataNative getCameraInfo() throws RemoteException;

    Surface getInputSurface() throws RemoteException;

    boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException;

    void prepare(int i) throws RemoteException;

    void prepare2(int i, int i2) throws RemoteException;

    SubmitInfo submitRequest(CaptureRequest captureRequest, boolean z) throws RemoteException;

    SubmitInfo submitRequestList(CaptureRequest[] captureRequestArr, boolean z) throws RemoteException;

    void tearDown(int i) throws RemoteException;

    void updateOutputConfiguration(int i, OutputConfiguration outputConfiguration) throws RemoteException;

    void waitUntilIdle() throws RemoteException;

    public static class Default implements ICameraDeviceUser {
        public void disconnect() throws RemoteException {
        }

        public SubmitInfo submitRequest(CaptureRequest request, boolean streaming) throws RemoteException {
            return null;
        }

        public SubmitInfo submitRequestList(CaptureRequest[] requestList, boolean streaming) throws RemoteException {
            return null;
        }

        public long cancelRequest(int requestId) throws RemoteException {
            return 0;
        }

        public void beginConfigure() throws RemoteException {
        }

        public void endConfigure(int operatingMode, CameraMetadataNative sessionParams) throws RemoteException {
        }

        public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
            return false;
        }

        public void deleteStream(int streamId) throws RemoteException {
        }

        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            return 0;
        }

        public int createInputStream(int width, int height, int format) throws RemoteException {
            return 0;
        }

        public Surface getInputSurface() throws RemoteException {
            return null;
        }

        public CameraMetadataNative createDefaultRequest(int templateId) throws RemoteException {
            return null;
        }

        public CameraMetadataNative getCameraInfo() throws RemoteException {
            return null;
        }

        public void waitUntilIdle() throws RemoteException {
        }

        public long flush() throws RemoteException {
            return 0;
        }

        public void prepare(int streamId) throws RemoteException {
        }

        public void tearDown(int streamId) throws RemoteException {
        }

        public void prepare2(int maxCount, int streamId) throws RemoteException {
        }

        public void updateOutputConfiguration(int streamId, OutputConfiguration outputConfiguration) throws RemoteException {
        }

        public void finalizeOutputConfigurations(int streamId, OutputConfiguration outputConfiguration) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICameraDeviceUser {
        private static final String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceUser";
        static final int TRANSACTION_beginConfigure = 5;
        static final int TRANSACTION_cancelRequest = 4;
        static final int TRANSACTION_createDefaultRequest = 12;
        static final int TRANSACTION_createInputStream = 10;
        static final int TRANSACTION_createStream = 9;
        static final int TRANSACTION_deleteStream = 8;
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_endConfigure = 6;
        static final int TRANSACTION_finalizeOutputConfigurations = 20;
        static final int TRANSACTION_flush = 15;
        static final int TRANSACTION_getCameraInfo = 13;
        static final int TRANSACTION_getInputSurface = 11;
        static final int TRANSACTION_isSessionConfigurationSupported = 7;
        static final int TRANSACTION_prepare = 16;
        static final int TRANSACTION_prepare2 = 18;
        static final int TRANSACTION_submitRequest = 2;
        static final int TRANSACTION_submitRequestList = 3;
        static final int TRANSACTION_tearDown = 17;
        static final int TRANSACTION_updateOutputConfiguration = 19;
        static final int TRANSACTION_waitUntilIdle = 14;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraDeviceUser asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICameraDeviceUser)) {
                return new Proxy(obj);
            }
            return (ICameraDeviceUser) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "disconnect";
                case 2:
                    return "submitRequest";
                case 3:
                    return "submitRequestList";
                case 4:
                    return "cancelRequest";
                case 5:
                    return "beginConfigure";
                case 6:
                    return "endConfigure";
                case 7:
                    return "isSessionConfigurationSupported";
                case 8:
                    return "deleteStream";
                case 9:
                    return "createStream";
                case 10:
                    return "createInputStream";
                case 11:
                    return "getInputSurface";
                case 12:
                    return "createDefaultRequest";
                case 13:
                    return "getCameraInfo";
                case 14:
                    return "waitUntilIdle";
                case 15:
                    return "flush";
                case 16:
                    return "prepare";
                case 17:
                    return "tearDown";
                case 18:
                    return "prepare2";
                case 19:
                    return "updateOutputConfiguration";
                case 20:
                    return "finalizeOutputConfigurations";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.hardware.camera2.CaptureRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.hardware.camera2.impl.CameraMetadataNative} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.hardware.camera2.params.SessionConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.hardware.camera2.params.OutputConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.hardware.camera2.params.OutputConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.hardware.camera2.params.OutputConfiguration} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: type inference failed for: r1v40 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v42 */
        /* JADX WARNING: type inference failed for: r1v43 */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.hardware.camera2.ICameraDeviceUser"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x01dd
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x01d3;
                    case 2: goto L_0x01a3;
                    case 3: goto L_0x017b;
                    case 4: goto L_0x0169;
                    case 5: goto L_0x015f;
                    case 6: goto L_0x0141;
                    case 7: goto L_0x0123;
                    case 8: goto L_0x0115;
                    case 9: goto L_0x00f7;
                    case 10: goto L_0x00dd;
                    case 11: goto L_0x00c6;
                    case 12: goto L_0x00ab;
                    case 13: goto L_0x0094;
                    case 14: goto L_0x008a;
                    case 15: goto L_0x007c;
                    case 16: goto L_0x006e;
                    case 17: goto L_0x0060;
                    case 18: goto L_0x004e;
                    case 19: goto L_0x0030;
                    case 20: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0028
                android.os.Parcelable$Creator<android.hardware.camera2.params.OutputConfiguration> r1 = android.hardware.camera2.params.OutputConfiguration.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.camera2.params.OutputConfiguration r1 = (android.hardware.camera2.params.OutputConfiguration) r1
                goto L_0x0029
            L_0x0028:
            L_0x0029:
                r6.finalizeOutputConfigurations(r3, r1)
                r9.writeNoException()
                return r2
            L_0x0030:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0046
                android.os.Parcelable$Creator<android.hardware.camera2.params.OutputConfiguration> r1 = android.hardware.camera2.params.OutputConfiguration.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.camera2.params.OutputConfiguration r1 = (android.hardware.camera2.params.OutputConfiguration) r1
                goto L_0x0047
            L_0x0046:
            L_0x0047:
                r6.updateOutputConfiguration(r3, r1)
                r9.writeNoException()
                return r2
            L_0x004e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.prepare2(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0060:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.tearDown(r1)
                r9.writeNoException()
                return r2
            L_0x006e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.prepare(r1)
                r9.writeNoException()
                return r2
            L_0x007c:
                r8.enforceInterface(r0)
                long r3 = r6.flush()
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x008a:
                r8.enforceInterface(r0)
                r6.waitUntilIdle()
                r9.writeNoException()
                return r2
            L_0x0094:
                r8.enforceInterface(r0)
                android.hardware.camera2.impl.CameraMetadataNative r1 = r6.getCameraInfo()
                r9.writeNoException()
                if (r1 == 0) goto L_0x00a7
                r9.writeInt(r2)
                r1.writeToParcel(r9, r2)
                goto L_0x00aa
            L_0x00a7:
                r9.writeInt(r3)
            L_0x00aa:
                return r2
            L_0x00ab:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                android.hardware.camera2.impl.CameraMetadataNative r4 = r6.createDefaultRequest(r1)
                r9.writeNoException()
                if (r4 == 0) goto L_0x00c2
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x00c5
            L_0x00c2:
                r9.writeInt(r3)
            L_0x00c5:
                return r2
            L_0x00c6:
                r8.enforceInterface(r0)
                android.view.Surface r1 = r6.getInputSurface()
                r9.writeNoException()
                if (r1 == 0) goto L_0x00d9
                r9.writeInt(r2)
                r1.writeToParcel(r9, r2)
                goto L_0x00dc
            L_0x00d9:
                r9.writeInt(r3)
            L_0x00dc:
                return r2
            L_0x00dd:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                int r5 = r6.createInputStream(r1, r3, r4)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x00f7:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0109
                android.os.Parcelable$Creator<android.hardware.camera2.params.OutputConfiguration> r1 = android.hardware.camera2.params.OutputConfiguration.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.camera2.params.OutputConfiguration r1 = (android.hardware.camera2.params.OutputConfiguration) r1
                goto L_0x010a
            L_0x0109:
            L_0x010a:
                int r3 = r6.createStream(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0115:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.deleteStream(r1)
                r9.writeNoException()
                return r2
            L_0x0123:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0135
                android.os.Parcelable$Creator<android.hardware.camera2.params.SessionConfiguration> r1 = android.hardware.camera2.params.SessionConfiguration.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.camera2.params.SessionConfiguration r1 = (android.hardware.camera2.params.SessionConfiguration) r1
                goto L_0x0136
            L_0x0135:
            L_0x0136:
                boolean r3 = r6.isSessionConfigurationSupported(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0141:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0157
                android.os.Parcelable$Creator<android.hardware.camera2.impl.CameraMetadataNative> r1 = android.hardware.camera2.impl.CameraMetadataNative.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.camera2.impl.CameraMetadataNative r1 = (android.hardware.camera2.impl.CameraMetadataNative) r1
                goto L_0x0158
            L_0x0157:
            L_0x0158:
                r6.endConfigure(r3, r1)
                r9.writeNoException()
                return r2
            L_0x015f:
                r8.enforceInterface(r0)
                r6.beginConfigure()
                r9.writeNoException()
                return r2
            L_0x0169:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                long r3 = r6.cancelRequest(r1)
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x017b:
                r8.enforceInterface(r0)
                android.os.Parcelable$Creator<android.hardware.camera2.CaptureRequest> r1 = android.hardware.camera2.CaptureRequest.CREATOR
                java.lang.Object[] r1 = r8.createTypedArray(r1)
                android.hardware.camera2.CaptureRequest[] r1 = (android.hardware.camera2.CaptureRequest[]) r1
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x018e
                r4 = r2
                goto L_0x018f
            L_0x018e:
                r4 = r3
            L_0x018f:
                android.hardware.camera2.utils.SubmitInfo r5 = r6.submitRequestList(r1, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x019f
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x01a2
            L_0x019f:
                r9.writeInt(r3)
            L_0x01a2:
                return r2
            L_0x01a3:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01b5
                android.os.Parcelable$Creator<android.hardware.camera2.CaptureRequest> r1 = android.hardware.camera2.CaptureRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.camera2.CaptureRequest r1 = (android.hardware.camera2.CaptureRequest) r1
                goto L_0x01b6
            L_0x01b5:
            L_0x01b6:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01be
                r4 = r2
                goto L_0x01bf
            L_0x01be:
                r4 = r3
            L_0x01bf:
                android.hardware.camera2.utils.SubmitInfo r5 = r6.submitRequest(r1, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x01cf
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x01d2
            L_0x01cf:
                r9.writeInt(r3)
            L_0x01d2:
                return r2
            L_0x01d3:
                r8.enforceInterface(r0)
                r6.disconnect()
                r9.writeNoException()
                return r2
            L_0x01dd:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.ICameraDeviceUser.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ICameraDeviceUser {
            public static ICameraDeviceUser sDefaultImpl;
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

            public void disconnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disconnect();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SubmitInfo submitRequest(CaptureRequest request, boolean streaming) throws RemoteException {
                SubmitInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(streaming);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().submitRequest(request, streaming);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SubmitInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SubmitInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SubmitInfo submitRequestList(CaptureRequest[] requestList, boolean streaming) throws RemoteException {
                SubmitInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(requestList, 0);
                    _data.writeInt(streaming);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().submitRequestList(requestList, streaming);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SubmitInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SubmitInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long cancelRequest(int requestId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(requestId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelRequest(requestId);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void beginConfigure() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().beginConfigure();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void endConfigure(int operatingMode, CameraMetadataNative sessionParams) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(operatingMode);
                    if (sessionParams != null) {
                        _data.writeInt(1);
                        sessionParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().endConfigure(operatingMode, sessionParams);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (sessionConfiguration != null) {
                        _data.writeInt(1);
                        sessionConfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSessionConfigurationSupported(sessionConfiguration);
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

            public void deleteStream(int streamId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteStream(streamId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (outputConfiguration != null) {
                        _data.writeInt(1);
                        outputConfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createStream(outputConfiguration);
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

            public int createInputStream(int width, int height, int format) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(format);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createInputStream(width, height, format);
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

            public Surface getInputSurface() throws RemoteException {
                Surface _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputSurface();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Surface.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Surface _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CameraMetadataNative createDefaultRequest(int templateId) throws RemoteException {
                CameraMetadataNative _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(templateId);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createDefaultRequest(templateId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = CameraMetadataNative.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CameraMetadataNative _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CameraMetadataNative getCameraInfo() throws RemoteException {
                CameraMetadataNative _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCameraInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = CameraMetadataNative.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CameraMetadataNative _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void waitUntilIdle() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().waitUntilIdle();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long flush() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().flush();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepare(int streamId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamId);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepare(streamId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tearDown(int streamId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().tearDown(streamId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepare2(int maxCount, int streamId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxCount);
                    _data.writeInt(streamId);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepare2(maxCount, streamId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateOutputConfiguration(int streamId, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamId);
                    if (outputConfiguration != null) {
                        _data.writeInt(1);
                        outputConfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateOutputConfiguration(streamId, outputConfiguration);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finalizeOutputConfigurations(int streamId, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamId);
                    if (outputConfiguration != null) {
                        _data.writeInt(1);
                        outputConfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finalizeOutputConfigurations(streamId, outputConfiguration);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICameraDeviceUser impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICameraDeviceUser getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
